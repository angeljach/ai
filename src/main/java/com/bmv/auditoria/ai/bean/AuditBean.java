package com.bmv.auditoria.ai.bean;

import com.bmv.auditoria.ai.controller.AuditController;
import com.bmv.auditoria.ai.db.AiDbUtil;
import com.bmv.auditoria.ai.log.MovementsFromAuditor;
import com.bmv.auditoria.ai.log.MovementsFromUser;
import com.bmv.auditoria.ai.login.InfoUsuario;
import com.bmv.auditoria.ai.persistent.AuditCloseReport;
import com.bmv.auditoria.ai.persistent.AuditInitialReport;
import com.bmv.auditoria.ai.persistent.AuditJobs;
import com.bmv.auditoria.ai.persistent.Audits;
import com.bmv.auditoria.ai.persistent.Observations;
import com.bmv.auditoria.ai.persistent.RequirementsInformation;
import com.bmv.auditoria.ai.persistent.Subprocesses;
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
 * Allow to manage the CRUD of the Audits.
 * <br>Considerations: Only for Users with the 'Administrador' role, and the Team Leader Auditor.
 * <br>Bean Name = auditorTeam
 * <br>Scope Type = Session
 * @author acruzh
 */
@ManagedBean(name = "audit")
@SessionScoped
public class AuditBean implements Crud, Serializable {
    
    private Audits current;
    private DataModel items = new ListDataModel();
    
    private String selectedDepartmentName;
    private String selectedAuditTypeName;
    private String selectedAuditStatusName;
    private String selectedAuditorTeamName;
            
    private InfoUsuario infoUsr;
    
    private AiDbUtil aiDbUtil;
    
    private Movements mov;
    static Logger logger = Logger.getLogger(AuditBean.class);
    private boolean edit;
    
    private static final String INDEX_PAGE = "index";
    private static final String NEW_PAGE = "auditnew";
    private static final String LIST_PAGE = "auditlist";
    //private static final String EDIT_PAGE = "edituser";
    private static final String EDIT_PAGE = "auditnew";
    
    public AuditBean() {
        infoUsr = new InfoUsuario();
        
        //---|| Valido si es un usuario o un auditor el que se está conectando.
        mov = (infoUsr.isAuditor()) ? new MovementsFromAuditor() : new MovementsFromUser();
        
        aiDbUtil = new AiDbUtil();
        current = new Audits();
                
        edit = false;
    }
    
    public Audits getSelected() {
        return (current == null) ? (new Audits()) : current;
    }

    @Override
    public String prepareEdit() {
        logger.debug(String.format("Llamado a página de edición '%s'", EDIT_PAGE));
        current = (Audits) items.getRowData();
        edit = true;
        return EDIT_PAGE;
    }

    @Override
    public String update() {
        FacesContext notifyCntx = FacesContext.getCurrentInstance();
        try {
            AuditController.update(current, mov);
            edit = false;
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Modificación exitosa", 
                    "La Auditoría fue modificada de manera exitosa"));
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
            AuditController.cancelUpdate(current);
            edit = false;
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Cancelación exitosa", 
                    "La modificación de la Auditoría ha sido cancelada"));
            return LIST_PAGE;
        } catch(Exception ex) {
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Problema", ex.getMessage()));
            return null;
        }
    }

    @Override
    public String prepareCreate() {
        logger.debug(String.format("Llamado a página de alta de usuario '%s'", NEW_PAGE));
        current = new Audits();
        edit = false;
        return NEW_PAGE;
    }

    @Override
    public String create() {
        FacesContext notifyCntx = FacesContext.getCurrentInstance();
         try {
            AuditController.create(current, mov, 
                    selectedDepartmentName, 
                    selectedAuditTypeName, 
                    selectedAuditStatusName, 
                    selectedAuditorTeamName);
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Creación exitosa", 
                    "La Auditoría fue creada de manera exitosa"));
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
    
    /* GETTERS AND SETTERS */

    public Audits getCurrent() {
        return current;
    }

    public void setCurrent(Audits current) {
        this.current = current;
    }
    
    public DataModel getItems() {
        items.setWrappedData(aiDbUtil.getAuditList(
                infoUsr.getUserName(),
                infoUsr.isAuditor(), 
                infoUsr.getRole()));
        return items;
    }
    
    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public List<String> getDepartmentNameList() {
        return this.aiDbUtil.getDepartmentNameList();
    }

    public String getSelectedDepartmentName() {
        return selectedDepartmentName;
    }

    public void setSelectedDepartmentName(String selectedDepartmentName) {
        this.selectedDepartmentName = selectedDepartmentName;
    }

    public List<String> getAuditTypeNameList() {
        return this.aiDbUtil.getAuditTypeNameList();
    }

    public String getSelectedAuditTypeName() {
        return selectedAuditTypeName;
    }

    public void setSelectedAuditTypeName(String selectedAuditTypeName) {
        this.selectedAuditTypeName = selectedAuditTypeName;
    }

    public List<String> getAuditStatusNameList() {
        return this.aiDbUtil.getAuditStatusNameList();
    }

    public String getSelectedAuditStatusName() {
        return selectedAuditStatusName;
    }

    public void setSelectedAuditStatusName(String selectedAuditStatusName) {
        this.selectedAuditStatusName = selectedAuditStatusName;
    }

    public List<String> getAuditorTeamNameList() {
        return this.aiDbUtil.getAuditorTeamNameList();
    }

    public String getSelectedAuditorTeamName() {
        return selectedAuditorTeamName;
    }

    public void setSelectedAuditorTeamName(String selectedAuditorTeamName) {
        this.selectedAuditorTeamName = selectedAuditorTeamName;
    }
    
    
    
    // TODO Lo de aqui abajo debera desaparecer vvvvvvvvv
//    
//    public List<AuditDocuments> getDocumentsFromAudit() {
//        return this.aiDbUtil.getDocumentsFromAudit(current);
//    }
    
    public List<RequirementsInformation> getRequirementsInformationFromAudit() {
        return this.aiDbUtil.getRequirementsInformationFromAudit(current);
    }
    
    public List<Observations> getObservationsFromAudit() {
        return this.aiDbUtil.getObservationsFromAudit(current);
    }
    
    public List<AuditJobs> getJobsFromAudit() {
        return this.aiDbUtil.getJobsFromAudit(current);
    }
    
    public List<Subprocesses> getSubprocessesFromAudit() {
        return this.aiDbUtil.getSubprocessesFromAudit(current);
    }
    
    public AuditInitialReport getInitialReportFromAudit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public AuditCloseReport getCloseReportFromAudit() {
        throw new UnsupportedOperationException("Not supported yet.");
    }    
    
}
