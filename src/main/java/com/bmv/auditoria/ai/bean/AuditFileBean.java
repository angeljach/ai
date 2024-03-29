package com.bmv.auditoria.ai.bean;

import com.bmv.auditoria.ai.controller.AuditFileController;
import com.bmv.auditoria.ai.db.AiDbUtil;
import com.bmv.auditoria.ai.log.MovementsFromAuditor;
import com.bmv.auditoria.ai.log.MovementsFromUser;
import com.bmv.auditoria.ai.login.InfoUsuario;
import com.bmv.auditoria.ai.persistent.AuditDocuments;
import com.bmv.auditoria.ai.persistent.Audits;
import com.jach.jachtoolkit.log.Movements;
import com.jach.jachtoolkit.persistence.Crud;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.apache.log4j.Logger;

/**
 * Allow to manage the CRUD of the Audit Files.
 * <br>Considerations: Only for Users with the 'Administrador' role (except to 
 * add files), and Auditors.
 * <br>Bean Name = auditorFile
 * <br>Scope Type = Session
 * @author acruzh
 */
@ManagedBean(name = "auditFile")
@SessionScoped
public class AuditFileBean implements Crud, Serializable{
    
    /**
     * The object of the current audit.
     */
    private Audits audit;
    
    private AuditDocuments current;
    private DataModel items = new ListDataModel();
    
    private InfoUsuario infoUsr;
    
    private AiDbUtil aiDbUtil;
    
    private Movements mov;
    static Logger logger = Logger.getLogger(AuditFileBean.class);
    private boolean edit;
    
    private static final String NEW_PAGE = "auditfilenew";
    private static final String LIST_PAGE = "audit";
    private static final String EDIT_PAGE = "auditfilenew";

    public AuditFileBean() {
        infoUsr = new InfoUsuario();
        
        //---|| Valido si es un usuario o un auditor el que se est� conectando.
        mov = (infoUsr.isAuditor()) ? new MovementsFromAuditor() : new MovementsFromUser();
        
        aiDbUtil = new AiDbUtil();
        current = new AuditDocuments();
        
        edit = false;
    }
    
    public AuditDocuments getSelected() {
        return (current == null) ? (new AuditDocuments()) : current;
    }
    
    @Override
    public String prepareEdit() {
        throw new UnsupportedOperationException("Implementaci�n no necesaria.");
    }

    @Override
    public String update() {
        FacesContext notifyCntx = FacesContext.getCurrentInstance();
        try {
            AuditFileController.update(current, mov);
            edit = false;
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Modificaci�n exitosa", 
                    "La Auditor�a fue modificada de manera exitosa"));
            return LIST_PAGE;
        } catch(Exception ex) {
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Problema", ex.getMessage()));
            return null;
        }
    }

    @Override
    public String cancelUpdate() {
        FacesContext notifyCntx = FacesContext.getCurrentInstance();
        try {
            AuditFileController.cancelUpdate(current);
            edit = false;
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Cancelaci�n exitosa", 
                    "La modificaci�n de la Auditor�a ha sido cancelada"));
            return LIST_PAGE;
        } catch(Exception ex) {
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Problema", ex.getMessage()));
            return null;
        }
    }

    @Override
    public String prepareCreate() {
        logger.debug(String.format("Llamado a p�gina de alta de archivos '%s'", NEW_PAGE));
        current = new AuditDocuments();
        edit = false;
        return NEW_PAGE;
    }

    @Override
    public String create() {
        FacesContext notifyCntx = FacesContext.getCurrentInstance();
         try {
            AuditFileController.create(current, mov, audit);
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Creaci�n exitosa", 
                    "El archivo fue agregado de manera exitosa"));
            return LIST_PAGE;
        } catch(Exception ex) {
            notifyCntx.addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Problema", ex.getMessage()));
            return null;
        }
    }

    @Override
    public String cancelCreate() {
        return LIST_PAGE;
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
    
    //---|| GETTERS AND SETTERS
    public AuditDocuments getCurrent() {
        return current;
    }

    public void setCurrent(AuditDocuments current) {
        this.current = current;
    }
    
    public DataModel getItems() {
        items.setWrappedData(aiDbUtil.getDocumentsFromAudit(audit));
        return items;
    }
    
    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
    
    
    
    //---|| Audit
    public Audits getAudit() {
        return audit;
    }

    public void setAudit(Audits audit) {
        this.audit = audit;
    }
        
}
