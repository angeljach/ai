package com.bmv.auditoria.ai.bean;

import com.bmv.auditoria.ai.controller.AuditObsController;
import com.bmv.auditoria.ai.db.AiDbUtil;
import com.bmv.auditoria.ai.log.MovementsFromAuditor;
import com.bmv.auditoria.ai.log.MovementsFromUser;
import com.bmv.auditoria.ai.login.InfoUsuario;
import com.bmv.auditoria.ai.persistent.Audits;
import com.bmv.auditoria.ai.persistent.Observations;
import com.jach.jachtoolkit.log.Movements;
import com.jach.jachtoolkit.persistence.Crud;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.apache.log4j.Logger;

/**
 * Allow to manage the CRUD of the Audit Observations.
 * <br>Considerations: Only for Users with the 'Administrador' role (except to 
 * add observations), and Auditors.
 * <br>Bean Name = auditorObs
 * <br>Scope Type = Session
 * @author acruzh
 */
@ManagedBean(name = "auditObs")
@SessionScoped
public class AuditObsBean implements Crud, Serializable {
    
    /**
     * The object of the current audit.
     */
    private Audits audit;
    
    private Observations current;
    private DataModel items = new ListDataModel();
    
    private String selectedObsStatusName;
    private String selectedResponsibleUserName;
    private String selectedObsFindingName;
    private String selectedObsImpactName;
    private String selectedObsComplexityName;
    
    private InfoUsuario infoUsr;
    
    private AiDbUtil aiDbUtil;
    
    private Movements mov;
    static Logger logger = Logger.getLogger(AuditObsBean.class);
    private boolean edit;
    
    private static final String NEW_PAGE = "auditobsnew";
    private static final String LIST_PAGE = "audit";
    private static final String EDIT_PAGE = "auditobsnew";

    public AuditObsBean() {
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
    

    @Override
    public String prepareEdit() {
        throw new UnsupportedOperationException("Implementación no necesaria.");
    }

    @Override
    public String update() {
        FacesContext notifyCntx = FacesContext.getCurrentInstance();
        try {
            AuditObsController.update(current, mov);
            edit = false;
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Modificación exitosa", 
                    "La Observación fue modificada de manera exitosa"));
            return LIST_PAGE;
        } catch(Exception ex) {
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Problema", ex.getMessage()));
            return null;
        }
    }

    @Override
    public String cancelUpdate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String prepareCreate() {
        logger.debug(String.format("Llamado a página de alta de observaciones '%s'", NEW_PAGE));
        current = new Observations();
        edit = false;
        return NEW_PAGE;
    }

    @Override
    public String create() {
        FacesContext notifyCntx = FacesContext.getCurrentInstance();
         try {
            AuditObsController.create(current, mov, audit, 
                    selectedObsStatusName, 
                    selectedResponsibleUserName,
                    selectedObsFindingName,
                    selectedObsImpactName,
                    selectedObsComplexityName);
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Creación exitosa", 
                    "La observación fue agregado de manera exitosa"));
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
    public Observations getCurrent() {
        return current;
    }

    public void setCurrent(Observations current) {
        this.current = current;
    }
    
    public DataModel getItems() {
        items.setWrappedData(aiDbUtil.getObservationsFromAudit(audit));
        return items;
    }
    
    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public List<String> getObsStatusNameList() {
        return this.aiDbUtil.getObsStatusNameList();
    }
    
    public String getSelectedObsStatusName() {
        return selectedObsStatusName;
    }

    public void setSelectedObsStatusName(String selectedObsStatusName) {
        this.selectedObsStatusName = selectedObsStatusName;
    }
    
    public List<String> getResponsibleUserNameList() {
        //---|| Obtengo la lista de usuarios que NO sean locales.
        return this.aiDbUtil.getUserNameList(false);
    }
    
    public String getSelectedResponsibleUserName() {
        return selectedResponsibleUserName;
    }

    public void setSelectedResponsibleUserName(String selectedResponsibleUserName) {
        this.selectedResponsibleUserName = selectedResponsibleUserName;
    }
    
    public List<String> getObsFindingNameList() {
        return this.aiDbUtil.getObsFindingLevelNameList();
    }

    public String getSelectedObsFindingName() {
        return selectedObsFindingName;
    }

    public void setSelectedObsFindingName(String selectedObsFindingName) {
        this.selectedObsFindingName = selectedObsFindingName;
    }
    
    public List<String> getObsImpactNameList() {
        return this.aiDbUtil.getObsImpactLevelNameList();
    }

    public String getSelectedObsImpactName() {
        return selectedObsImpactName;
    }

    public void setSelectedObsImpactName(String selectedObsImpactName) {
        this.selectedObsImpactName = selectedObsImpactName;
    }
    
    public List<String> getObsComplexityNameList() {
        return this.aiDbUtil.getObsComplexityLevelNameList();
    }

    public String getSelectedObsComplexityName() {
        return selectedObsComplexityName;
    }

    public void setSelectedObsComplexityName(String selectedObsComplexityName) {
        this.selectedObsComplexityName = selectedObsComplexityName;
    }
    
    
    
    
    
    //---|| Audit
    public Audits getAudit() {
        return audit;
    }

    public void setAudit(Audits audit) {
        this.audit = audit;
    }
    
}
