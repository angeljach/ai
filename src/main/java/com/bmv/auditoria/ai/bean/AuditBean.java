package com.bmv.auditoria.ai.bean;

import com.bmv.auditoria.ai.db.AiDbObjectFromString;
import com.bmv.auditoria.ai.db.AiDbUtil;
import com.bmv.auditoria.ai.log.MovementsFromAuditor;
import com.bmv.auditoria.ai.log.MovementsFromUser;
import com.bmv.auditoria.ai.login.InfoUsuario;
import com.bmv.auditoria.ai.persistent.AuditCloseReport;
import com.bmv.auditoria.ai.persistent.AuditDocuments;
import com.bmv.auditoria.ai.persistent.AuditInitialReport;
import com.bmv.auditoria.ai.persistent.AuditJobs;
import com.bmv.auditoria.ai.persistent.AuditStatus;
import com.bmv.auditoria.ai.persistent.AuditTypes;
import com.bmv.auditoria.ai.persistent.AuditorTeams;
import com.bmv.auditoria.ai.persistent.Audits;
import com.bmv.auditoria.ai.persistent.CompanyDepartments;
import com.bmv.auditoria.ai.persistent.Observations;
import com.bmv.auditoria.ai.persistent.RequirementsInformation;
import com.bmv.auditoria.ai.persistent.Subprocesses;
import com.jach.jachtoolkit.log.Movements;
import com.jach.jachtoolkit.persistence.Crud;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.resource.NotSupportedException;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
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
public class AuditBean implements Crud {
    
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
        String tmpAudit = current.getAuditName();
        String tmpCia = current.getToCompanyDepartments().getToCompanies().getShortName();
        String tmpDepto = current.getToCompanyDepartments().getDepartment();
        logger.debug(String.format("Tratando de actualizar la información de la "
                + "auditoría '%s' del departamento '%s' de la empresa '%s'", tmpAudit, tmpDepto, tmpCia));
        try {
            (current.getObjectContext()).commitChanges();
            String tmpMsg = String.format("Modificación de la información de la "
                + "auditoría '%s' del departamento '%s' de la empresa '%s'", 
                tmpAudit, tmpDepto, tmpCia);
            mov.save(tmpMsg);
            logger.info(tmpMsg);
        } catch (Exception ex) {
            logger.error(String.format("Ocurrió un error al tratar de modificar "
                    + "la información de la auditoría '%s' del departamento "
                    + "'%s' de la empresa '%s'", tmpAudit, tmpDepto, tmpCia));
            logger.error(ex.getMessage());
            return null;
        }
        edit = false;
        return LIST_PAGE;
    }

    @Override
    public String cancelUpdate() {
        String tmpAudit = current.getAuditName();
        String tmpCia = current.getToCompanyDepartments().getToCompanies().getShortName();
        String tmpDepto = current.getToCompanyDepartments().getDepartment();
        logger.debug(String.format("Cancelando la edición de la auditoría '%s' "
                + "del departamento '%s' de la empresa '%s'", 
                tmpAudit, tmpDepto, tmpCia));
        try {
            current.getObjectContext().rollbackChanges();
        } catch (Exception e) {
            logger.error(String.format("Ocurrió un error al tratar de hacer "
                    + "rollback a los cambios para la modificación de la "
                    + "auditoría '%s' del departamento '%s' de la empresa '%s'", 
                    tmpAudit, tmpDepto, tmpCia));
            logger.error(e.getMessage());
        }
        edit = false;
        return LIST_PAGE;
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
        String tmpAudit = "", tmpCia = "", tmpDepto = "", tmpMsg = "";
        try {
            ObjectContext context = DataContext.createDataContext();
            
            CompanyDepartments objDep 
                    = AiDbObjectFromString.getCompanyDepartmentsObjectFromString(context, selectedDepartmentName);
            AuditTypes objAtypes 
                    = AiDbObjectFromString.getAuditTypesFromString(context, selectedAuditTypeName);
            AuditStatus objAstatus 
                    = AiDbObjectFromString.getAuditStatusFromString(context, selectedAuditStatusName);
            AuditorTeams objAteam 
                    = AiDbObjectFromString.getAuditorTeamsObjectFromString(context, selectedAuditorTeamName);

            current.setToCompanyDepartments(objDep);
            current.setToAuditTypes(objAtypes);
            current.setToAuditStatus(objAstatus);
            current.setToAuditorTeams(objAteam);            
            
            tmpAudit = current.getAuditName();
            tmpCia = current.getToCompanyDepartments().getToCompanies().getShortName();
            tmpDepto = current.getToCompanyDepartments().getDepartment();
            logger.debug(String.format("Tratando de crear la auditoría '%s' "
                    + "del departamento '%s' de la empresa '%s'", 
                    tmpAudit, tmpDepto, tmpCia));
            
            context.commitChanges();

            tmpMsg = String.format("Creación de la auditoría '%s' "
                    + "del departamento '%s' de la empresa '%s'", 
                    tmpAudit, tmpDepto, tmpCia);
            mov.save(tmpMsg);
            logger.info(tmpMsg);
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Creación exitosa", tmpMsg));

            //return prepareCreate();
            return LIST_PAGE;
        } catch (Exception ex) {
            tmpMsg = String.format("Ocurrió un error al tratar de crear la "
                    + "auditoría '%s' del departamento '%s' de la empresa '%s'", 
                    tmpAudit, tmpDepto, tmpCia);
            logger.error(tmpMsg);
            logger.error(ex.getMessage());
            notifyCntx.addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Problema", tmpMsg));
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
    
    public List<AuditDocuments> getDocumentsFromAudit() {
        return this.aiDbUtil.getDocumentsFromAudit(current);
    }
    
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
