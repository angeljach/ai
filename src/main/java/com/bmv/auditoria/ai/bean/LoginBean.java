package com.bmv.auditoria.ai.bean;

import com.bmv.auditoria.ai.log.MovementsFromAuditor;
import com.bmv.auditoria.ai.log.MovementsFromUser;
import com.bmv.auditoria.ai.login.Login;
import com.bmv.auditoria.ai.util.Navigation;
import com.bmv.auditoria.ai.vo.UsrInfoVO;
import com.jach.jachtoolkit.log.Movements;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;

/**
 *
 * @author acruz
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginBean {

    private String userName;
    private String pwd;
    private String role;
    private String displayName;
    private boolean auditor;
    private boolean local;
    //private int perfil;
    
    private String environment;
    
    private static final Logger logger = Logger.getLogger(LoginBean.class);
    
    /**
     * ResourceBunde object of properties file.
     */
    private static ResourceBundle rbText = ResourceBundle.getBundle("ai");

    public LoginBean() {
        auditor = false;
        local = false;
        environment = rbText.getString("app.env");
    }

    /**
     * Confirm if an user has access or not to the application.
     * @return String success or fail.
     */
    public String checkValidUser() {
        String msg = "";
        UsrInfoVO usrInfo = null;
        
        Movements mov;

        //---|| Valido el si es usuario o auditor.
        if (this.auditor) {
            //Auditor
            this.local = false; //No puede haber auditores locales, solo usuarios
            
            
            usrInfo = (new Login()).getInfoInicialSesion(userName, pwd, auditor, local);
            if (usrInfo == null) {
                logger.info("Las credenciales del auditor no pudieron ser "
                        + "validadas. Rechazando acceso al usuario " + userName);
                return Navigation.LOGIN_FAILED.getUrl();
            }

            mov = new MovementsFromAuditor();
        } else {
            //Usuario
            //this.local = ?; //El valor de local lo determina el usuario.
            
            usrInfo = (new Login()).getInfoInicialSesion(userName, pwd, auditor, local);
            if (usrInfo == null) {
                logger.info("Las credenciales del usuario no pudieron ser "
                        + "validadas. Rechazando acceso al usuario " + userName);
                return Navigation.LOGIN_FAILED.getUrl();
            }

            mov = new MovementsFromUser();
        }


        //---|| init
        //perfil = usrInfo.getIdPerfil();
        this.role = usrInfo.getRole();
        this.displayName = usrInfo.getDisplayName();
        
        //NOTA: this.auditor y this.local ya fueron inicializados previamente.

        msg = String.format("Inicio de sesión validado. "
                + "Otorgando acceso al usuario '%s'", userName);
        logger.info(msg);
        mov.save(msg);

        return Navigation.MENU.getUrl();
    }

    /**
     * <p>When invoked, it will invalidate the user's session
     * and move them to the login view.</p>
     *
     * @return <code>login</code>
     */
    public String logOut() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return Navigation.INDEX.getUrl();
    }
    
    public void abc() {
        String summary = auditor ? "Checked" : "Unchecked"; 
        FacesContext.getCurrentInstance().addMessage(null, new javax.faces.application.FacesMessage(summary));

    }

    //GETTERS
    public String getUserName() {
        return userName;
    }

    public String getPwd() {
        return pwd;
    }

    public String getRole() {
        return role;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isAuditor() {
        return auditor;
    }

    public boolean isLocal() {
        return local;
    }

    public String getEnvironment() {
        return environment;
    }
    
    //public int getIdUser() { return idUser; }
    //public int getPerfil() { return perfil; }
    //SETTERS
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setAuditor(boolean auditor) {
        this.auditor = auditor;
    }
    
    public void setLocal(boolean local) {
        this.local = local;
    }
    
}
