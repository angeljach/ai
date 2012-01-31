package com.bmv.auditoria.ai.login;

import com.bmv.auditoria.ai.bean.LoginBean;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

/**
 * Allow the application to obtain Session information of the user.
 * @author acruzh
 */
public class InfoUsuario {

    /**
     * Faces Context object.
     */
    private FacesContext fc;
    
    //private int perfil;
    
    /**
     * The User Name.
     */
    private String userName;
    
    /**
     * Role Name of the User.
     */
    private String role;
    
    /**
     * Display Name of the User.
     * <br>If is a local user, the displayName gonna be equal to the userName.
     */
    private String displayName;
    
    /**
     * Flag to determine if is an auditor or an user.
     */
    private Boolean auditor;
    
    /**
     * Flag to determine if the user is local or not.
     * <br>This is used only for users, not for auditors.
     */
    private Boolean local;
    
    /**
     * Name of the Bean that holds the informetion of the user.
     */
    private final static String BEAN_NAME = "login";
    
    /**
    * Logger static var.
    */
    static Logger logger = Logger.getLogger(InfoUsuario.class);

    public InfoUsuario() {
        logger.trace("Entrando a constructor de InfoUsuario");
        fc = FacesContext.getCurrentInstance();
        userName = "";
        role = "";
        displayName = "";
        auditor = null;
        local = null;
        //perfil = -1;
    }

//    public int getPerfil() {
//        if (perfil == -1) {
//            perfil = ((LoginBean)fc.getELContext().getELResolver().getValue(
//                  fc.getELContext(), null, BEAN_NAME)).getPerfil();
//        }
//        return perfil;
//    }

    /**
     * Query the session bean, trying to obtain the user name.
     * @return User Name.
     */
    public String getUserName() {
        logger.debug("Llamado al método getUserName");
        if (userName.equals("")) {
            userName = ((LoginBean)fc.getELContext().getELResolver().getValue(
                    fc.getELContext(), null, BEAN_NAME)).getUserName();
            if (userName == null) { 
                logger.info("El 'UserName' obtenido fue un valor nulo, se inicializará a ''.");
                userName = "";
            }
        }
        return userName;
    }

    /**
     * Query the session bean, trying to obtain the role name.
     * @return Role Name.
     */
    public String getRole() {
        logger.debug("Llamado al método getRole");
        if (role.equals("")) {
            role = ((LoginBean)fc.getELContext().getELResolver().getValue(
                    fc.getELContext(), null, BEAN_NAME)).getRole();
            if (role == null) {
                logger.info("El 'Role' obtenido fue un valor nulo, se inicializará a ''.");
                role = "";
            }
        }
        return role;
    }

    public String getDisplayName() {
        logger.debug("Llamado al método getDisplayName");
        if (displayName.equals("")) {
            displayName = ((LoginBean)fc.getELContext().getELResolver().getValue(
                    fc.getELContext(), null, BEAN_NAME)).getDisplayName();
            if (displayName == null) {
                logger.info("El 'DisplayName' obtenido fue un valor nulo, se inicializará a ''.");
                displayName = "";
            }
        }
        return displayName;
    }
    
    public boolean isAuditor() {
        if (auditor == null) {
            auditor = ((LoginBean)fc.getELContext().getELResolver().getValue(
                  fc.getELContext(), null, BEAN_NAME)).isAuditor();
        }
        return auditor;
    }
    
    public boolean isLocal() {
        if (local == null) {
            local = ((LoginBean)fc.getELContext().getELResolver().getValue(
                  fc.getELContext(), null, BEAN_NAME)).isLocal();
        }
        return local;
    }

}
