package com.bmv.auditoria.ai.login;

import com.bmv.auditoria.ai.persistent.Auditors;
import com.bmv.auditoria.ai.persistent.Users;
import com.bmv.auditoria.ai.util.Constants;
import com.bmv.auditoria.ai.vo.UsrInfoVO;
import com.jach.jachtoolkit.ldap.LdapTools;
import java.util.ResourceBundle;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author acruz
 */
public class Login {

    /**
     * Logger static variable.
     */
    static Logger logger = Logger.getLogger(Login.class);
    /**
     * ResourceBunde object of properties file.
     */
    private static ResourceBundle rbText = ResourceBundle.getBundle("ai");

    /**
     * Validate the application credentials through the DB information.
     * @param usr User name.
     * @param pwd Password.
     * @return Value Object with session information.
     */
    public UsrInfoVO getInfoInicialSesion(String usr, String pwd,
            boolean isAuditor, boolean isLocal) {
        logger.debug("Llamado al método getInfoInicialSesion(usr, pwd)");

        UsrInfoVO usrInfo = null;

        if (isLocal) {
            //Por definición de la aplicación:
            //Un Auditor NO puede ser usuario local.
            usrInfo = this.getInfoInitialSessionFromDataBaseToLocalUsers(usr, pwd);
        } else {
            //No es usuario local. Valido las credenciales en el LDAP.
            usrInfo = this.getInfoInitialSessionFromLDAP(usr, pwd, isAuditor);
        }


        //TODO Validar si esta escritura del log aún es necesaria.
        if (usrInfo != null) {
            logger.info(String.format("Acceso concedido al usuario '%s'", usr));
        } else {
            logger.info(String.format("Acceso denegado al usuario '%s'", usr));
        }
        return usrInfo;
    }

    private UsrInfoVO getInfoInitialSessionFromDataBaseToLocalUsers(String usr, String pwd) {
        logger.debug("Llamado al método getInfoInitialSessionFromDataBase(usr, pwd)");

        //Por definición de la aplicación:
        //Un Auditor NO puede ser usuario local.

        //---|| Obtengo el rol.
        ObjectContext context = DataContext.getThreadObjectContext();
        
        Expression e = ExpressionFactory.matchExp(Users.USER_NAME_PROPERTY, usr);
        SelectQuery select = new SelectQuery(Users.class, e);

        Users tmpUser = (Users) Cayenne.objectForQuery(context, select);

        if (tmpUser != null) {
            logger.trace(String.format("El usuario '%s' existe en la BD.", usr));

            //---|| Valido que el usuario esté definido como LOCAL.
            if ( ! tmpUser.getIsLocal()) {
                logger.info(String.format("El usuario '%s' existe en la BD "
                        + "pero NO está definido como local. El acceso a la "
                        + "aplicación será denegado.", usr));
                return null;
            } else {
                logger.info(String.format("El usuario '%s' existe en la BD "
                        + "y SI está definido como local. Se validará la "
                        + "contraseña.", usr));
            }
            
            //---|| Valido la contraseña.
            if ( ! tmpUser.getPassword().equals(pwd)) {
                logger.info(String.format("La contraseña del usuario '%s' NO "
                        + "es válida. El acceso a la aplicación será denegado.", usr));
                return null;
            } else {
                logger.info(String.format("La contraseña del usuario '%s' SI "
                        + "es válida. Otorgando acceso a la aplicación.", usr));
                
                //---|| Al ser un usuario local, no se está almacenando el
                //---|| nombre completo del mismo, se dejará el valor de 'usr'.
                String displayName = usr;
                boolean isAuditor = false;
                
                return (new UsrInfoVO(
                    usr,
                    tmpUser.getToUserRoles().getName(),
                    displayName,
                    isAuditor,
                    true));
            }            
        } else {
            logger.info(String.format("El usuario '%s' no fue encontrado en la BD.", usr));
        }
        
        return null;
    }

    private UsrInfoVO getInfoInitialSessionFromLDAP(String usr, String pwd,
            boolean isAuditor) {
        logger.debug("Llamado al método getInfoInicialSesionDesdeLDAP(usr, pwd, isAuditor)");

        //---|| Debido a que validare desde LDAP, no es usuario local.
        boolean bLocal = false;

        boolean bCredValidas = false;
        String displayName = "";

        logger.info(String.format("Validando las credenciales del usuario '%s'", usr));

        //---|| Valido si es ambiente de desarrollo o producción.
        if (Constants.PRODUCTION_ENVIRONMENT_NAME.equals(rbText.getString("app.env"))) {
            logger.info("AMBIENTE DE PRODUCCION");
            logger.debug(String.format("Validando credenciales de '%s' por LDAP.", usr));
            LdapTools ldapT = new LdapTools(
                    usr, pwd,
                    rbText.getString("ldap.domain"),
                    rbText.getString("ldap.hostport"),
                    rbText.getString("ldap.dn"));

            bCredValidas = ldapT.isValidUser();

            if (bCredValidas) {
                String attArr[] = {"displayName"};
                try {
                    Attributes att = ldapT.getUserAttributes(attArr);
                    displayName = ((att == null)
                            ? "ATRIBUTO NO ENCONTRADO"
                            : (String) att.get("displayName").get());
                } catch (NamingException ex) {
                    displayName = "ERROR";
                    logger.error(ex.getMessage());
                }
            }
        } else {
            logger.info("AMBIENTE DE DESARROLLO");
            logger.debug(String.format("La contraseña de '%s' no será validada.", usr));
            displayName = "AMBIENTE DE DESARROLLO";
            bCredValidas = true;
        }

        if (bCredValidas) {
            logger.debug(String.format("Credenciales de '%s' validadas. Obteniendo rol.", usr));

            //---|| Obtengo el rol.
            ObjectContext context = DataContext.getThreadObjectContext();
            Expression e;
            SelectQuery select;

            if (isAuditor) {
                e = ExpressionFactory.matchExp(Auditors.AUDITOR_NAME_PROPERTY, usr);
                select = new SelectQuery(Auditors.class, e);
                Auditors tmpUser = (Auditors) Cayenne.objectForQuery(context, select);

                if (tmpUser != null) {
                    //---|| Si es un usuario valido, regreso un objeto UsrInfoVO
                    return (new UsrInfoVO(
                            usr,
                            tmpUser.getToAuditorRoles().getName(),
                            displayName,
                            isAuditor,
                            bLocal));
                } else {
                    logger.info(String.format("El usuario '%s' no fue encontrado en la BD.", usr));
                }

            } else {
                e = ExpressionFactory.matchExp(Users.USER_NAME_PROPERTY, usr);
                select = new SelectQuery(Users.class, e);
                Users tmpUser = (Users) Cayenne.objectForQuery(context, select);

                if (tmpUser != null) {
                    //---|| Si es un usuario valido, regreso un objeto UsrInfoVO
                    return (new UsrInfoVO(
                            usr,
                            tmpUser.getToUserRoles().getName(),
                            displayName,
                            isAuditor,
                            bLocal));
                } else {
                    logger.info(String.format("El usuario '%s' no fue encontrado en la BD.", usr));
                }
            }


        }
        return null;
    }
//    /**
//     * Create cookies in the system with username and role information.
//     * @param username Username.
//     * @param role Role id.
//     * @return True if is a valid user, false if not.
//     */
//    private boolean createLoginCookies(int idUser, String cbCveCorta, int perfil) {
//        //Creación de cookie.
//        logger.log(Level.INFO, "Tratando de crear las cookies de la aplicación.");
//        try {
//            FacesContext fceContext = FacesContext.getCurrentInstance();
//            Cookie cokUser = new Cookie("cpbmvusr", Integer.toString(idUser));    //CalendarioPruebas=cp
//            Cookie cokCb = new Cookie("cpbmvcb", cbCveCorta);
//            Cookie cokRole = new Cookie("cpbmvperf",Integer.toString(perfil));
//            cokUser.setMaxAge(86400);  //86400 //24 hrs.
//            cokCb.setMaxAge(86400);    //86400 //24 hrs.
//            cokRole.setMaxAge(86400);  //86400 //24 hrs.
//            ((HttpServletResponse)fceContext.getExternalContext().getResponse()).addCookie(cokUser);
//            ((HttpServletResponse)fceContext.getExternalContext().getResponse()).addCookie(cokCb);
//            ((HttpServletResponse)fceContext.getExternalContext().getResponse()).addCookie(cokRole);
//        } catch (Exception e) {
//            //TODO Mandar a una página que indique que no se tienen las cookies habilitadas
//            //TODO Poner en la página principal la restricción de que esta app necesita que están las cookies activadas.
//            logger.log(Level.SEVERE,"Se presentó un error al tratar de registrar las cookies de sesión. "
//                    + "{0}", e.getMessage());
//            return false;
//        }
//        logger.log(Level.INFO, "Creación de cookie exitosa.");
//        return true;
//    }
}
