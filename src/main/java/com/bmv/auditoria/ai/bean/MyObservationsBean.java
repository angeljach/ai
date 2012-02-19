package com.bmv.auditoria.ai.bean;

import com.bmv.auditoria.ai.db.AiDbUtil;
import com.bmv.auditoria.ai.log.MovementsFromAuditor;
import com.bmv.auditoria.ai.log.MovementsFromUser;
import com.bmv.auditoria.ai.login.InfoUsuario;
import com.bmv.auditoria.ai.persistent.Observations;
import com.jach.jachtoolkit.log.Movements;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.apache.log4j.Logger;

/**
 * Allow to manage the CRUD of the user observations.
 * <br>Considerations: ???
 * <br>Bean Name = myObservations
 * <br>Scope Type = Session
 * @author acruzh
 */
@ManagedBean(name = "myObservations")
@SessionScoped
public class MyObservationsBean {

    private Observations current;
    private DataModel items = new ListDataModel();

    private InfoUsuario infoUsr;
    
    private AiDbUtil aiDbUtil;
    
    private Movements mov;
    static Logger logger = Logger.getLogger(AuditBean.class);
    private boolean edit;
    
//    private static final String INDEX_PAGE = "index";
//    private static final String NEW_PAGE = "auditnew";
//    private static final String LIST_PAGE = "auditlist";
//    //private static final String EDIT_PAGE = "edituser";
//    private static final String EDIT_PAGE = "auditnew";

    public MyObservationsBean() {
        infoUsr = new InfoUsuario();
        
        //---|| Valido si es un usuario o un auditor el que se está conectando.
        mov = (infoUsr.isAuditor()) ? new MovementsFromAuditor() : new MovementsFromUser();
        
        aiDbUtil = new AiDbUtil();
        current = new Observations();
                
        edit = false;
    }
    
    public Observations getSelected() {
        return (current == null) ? (new Observations()) : current;
    }
    
    
    
    /* GETTERS AND SETTERS */

    public Observations getCurrent() {
        return current;
    }

    public void setCurrent(Observations current) {
        this.current = current;
    }
    
    public DataModel getItems() {
        //---|| Devuelvo la lista de observaciones dependiendo quien las
        //---|| solicita (usuario o auditor).
        if (infoUsr.isAuditor()) {
            items.setWrappedData(
                aiDbUtil.getActiveObservationsFromAuditor(infoUsr.getUserName()));
        } else {
            items.setWrappedData(
                aiDbUtil.getActiveObservationsFromUser(infoUsr.getUserName()));
        }
        
        return items;
    }
    
    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
}
