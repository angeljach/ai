package com.bmv.auditoria.ai.db;

import com.bmv.auditoria.ai.persistent.AuditDocuments;
import com.bmv.auditoria.ai.persistent.AuditJobs;
import com.bmv.auditoria.ai.persistent.AuditStatus;
import com.bmv.auditoria.ai.persistent.AuditTypes;
import com.bmv.auditoria.ai.persistent.AuditorTeamMembers;
import com.bmv.auditoria.ai.persistent.AuditorTeams;
import com.bmv.auditoria.ai.persistent.Auditors;
import com.bmv.auditoria.ai.persistent.Audits;
import com.bmv.auditoria.ai.persistent.Companies;
import com.bmv.auditoria.ai.persistent.CompanyDepartments;
import com.bmv.auditoria.ai.persistent.Observations;
import com.bmv.auditoria.ai.persistent.RequirementsInformation;
import com.bmv.auditoria.ai.persistent.Subprocesses;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.CayenneContext;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.apache.log4j.Logger;

/**
 *
 * @author acruzh
 */
public class AiDbUtil implements Serializable {

    private ObjectContext context;
    static Logger logger = Logger.getLogger(AiDbUtil.class);

    public AiDbUtil() {
        logger.trace("Entrando a constructor de CounselorsDbUtil");
        context = DataContext.getThreadObjectContext();
    }

    /**
     * Get a list of all registered Auditor Teams in Data Base from table 
     * 'auditor_teams'.
     * @return AuditorTeams list.
     */
    public List<AuditorTeams> getAuditorTeamList() {
        logger.debug("Llamado al método getAuditorTeamList");
        SelectQuery sel = new SelectQuery(AuditorTeams.class);
        sel.addOrdering(new Ordering("teamName", SortOrder.ASCENDING));
        return (List<AuditorTeams>) context.performQuery(sel);
    }

    public List<Companies> getCompanyList() {
        logger.debug("Llamado al método getCompanyList");
        SelectQuery sel = new SelectQuery(Companies.class);
        sel.addOrdering(new Ordering("shortName", SortOrder.ASCENDING));
        return (List<Companies>) context.performQuery(sel);
    }

    public List<CompanyDepartments> getCompanyDepartmentList() {
        logger.debug("Llamado al método getCompanyDepartmentList");
        SelectQuery sel = new SelectQuery(CompanyDepartments.class);
        sel.addOrdering(new Ordering("department", SortOrder.ASCENDING));
        return (List<CompanyDepartments>) context.performQuery(sel);
    }

    public List<AuditTypes> getAuditTypeList() {
        logger.debug("Llamado al método getAuditTypeList");
        SelectQuery sel = new SelectQuery(AuditTypes.class);
        sel.addOrdering(new Ordering("name", SortOrder.ASCENDING));
        return (List<AuditTypes>) context.performQuery(sel);
    }

    public List<AuditStatus> getAuditStatusList() {
        logger.debug("Llamado al método getAuditStatusList");
        SelectQuery sel = new SelectQuery(AuditStatus.class);
        //sel.addOrdering(new Ordering("ID_AUDIT_STATUS_PK_COLUMN", SortOrder.ASCENDING));
        return (List<AuditStatus>) context.performQuery(sel);
    }

    public List<Audits> getAuditList(String userName, boolean isAuditor, String role) {
        logger.debug(String.format("Llamado al método getAuditorTeamList (isAuditor=%s, %s)",
                isAuditor ? "True" : "False",
                role));

        //---|| Si es usuario, valido que sea administrador.
        if ((!isAuditor) && role.equals("administrador")) {
            //---|| Devuelvo TODAS las auditorías
            SelectQuery sel = new SelectQuery(Audits.class);

            //TODO Validar si requiero ordenar por planningStartDate. Si es así, hacer el campo como NOT NULL en la BD.
            sel.addOrdering(new Ordering("auditName", SortOrder.ASCENDING));
            return (List<Audits>) context.performQuery(sel);
        }

        //---|| Si es auditor
        if (isAuditor) {
            //---|| Devuelvo aquellas en las cuales sea líder.
            Expression e = ExpressionFactory.matchExp(Auditors.AUDITOR_NAME_PROPERTY, userName);
            SelectQuery sel = new SelectQuery(Auditors.class, e);

            Auditors objUser = (Auditors) Cayenne.objectForQuery(context, sel);

            if (objUser != null) {
                //donde el auditor sea miembro
                e = ExpressionFactory.matchDbExp(AuditorTeamMembers.TO_AUDITORS_PROPERTY, objUser);

                sel = new SelectQuery(AuditorTeamMembers.class, e);
                List<AuditorTeamMembers> atListMemb = (List<AuditorTeamMembers>) context.performQuery(sel);



                //donde el auditor sea lider o miembro
                e = ExpressionFactory.matchDbExp(AuditorTeams.TO_AUDITORS_PROPERTY, objUser);
                e = e.orExp(ExpressionFactory.inDbExp(AuditorTeams.AUDITOR_TEAM_MEMBERS_ARRAY_PROPERTY, atListMemb));

                sel = new SelectQuery(AuditorTeams.class, e);
                List<AuditorTeams> atList = (List<AuditorTeams>) context.performQuery(sel);


                if (!atList.isEmpty()) {
                    e = ExpressionFactory.inDbExp(Audits.TO_AUDITOR_TEAMS_PROPERTY, atList);

                    sel = new SelectQuery(Audits.class, e);
                    return (List<Audits>) context.performQuery(sel);
                }
            }
        }
        return null;
    }

    /**
     * Get a Company Short Name list of all registered companies in DB from table 'companies'.
     * @return Company Short Name List.
     */
    public List<String> getCompanyShortNameList() {
        logger.debug("Llamado al método getCompanyShortNameList");
        List<String> tmpList = new ArrayList<String>();
        for (Companies objTemp : this.getCompanyList()) {
            tmpList.add(objTemp.getShortName());
        }
        return tmpList;
    }

    /**
     * Get an Auditor Team Name list of all registered companies in DB from table 'auditor_teams'.
     * @return Auditor Team Name List.
     */
    public List<String> getAuditorTeamNameList() {
        logger.debug("Llamado al método getAuditorTeamNameList");
        List<String> tmpList = new ArrayList<String>();
        for (AuditorTeams objTemp : this.getAuditorTeamList()) {
            tmpList.add(objTemp.getTeamName());
        }
        return tmpList;
    }

    /**
     * Get a Department Name list of all registered companies in DB from table 'company_departments'.
     * @return Department Name List.
     */
    public List<String> getDepartmentNameList() {
        logger.debug("Llamado al método getDepartmentNameList");
        List<String> tmpList = new ArrayList<String>();
        for (CompanyDepartments objTemp : this.getCompanyDepartmentList()) {
            tmpList.add(objTemp.getDepartment());
        }
        return tmpList;
    }

    /**
     * Get a Audit Type Name list of all registered companies in DB from table 'audit_types'.
     * @return Audit Type Name List.
     */
    public List<String> getAuditTypeNameList() {
        logger.debug("Llamado al método getAuditTypeNameList");
        List<String> tmpList = new ArrayList<String>();
        for (AuditTypes objTemp : this.getAuditTypeList()) {
            tmpList.add(objTemp.getName());
        }
        return tmpList;
    }

    /**
     * Get a Audit Status Name list of all registered companies in DB from table 'audit_types'.
     * @return Audit Status Name List.
     */
    public List<String> getAuditStatusNameList() {
        logger.debug("Llamado al método getAuditStatusNameList");
        List<String> tmpList = new ArrayList<String>();
        for (AuditStatus objTemp : this.getAuditStatusList()) {
            tmpList.add(objTemp.getName());
        }
        return tmpList;
    }

    
    
    
    
    
    
    public List<AuditDocuments> getDocumentsFromAudit(Audits audit) {
        Expression e = ExpressionFactory.matchExp(AuditDocuments.TO_AUDITS_PROPERTY, audit);
        SelectQuery sel = new SelectQuery(AuditDocuments.class, e);

        return (List<AuditDocuments>) audit.getObjectContext().performQuery(sel);
    }
    
    public List<RequirementsInformation> getRequirementsInformationFromAudit(Audits audit) {
        Expression e = ExpressionFactory.matchExp(RequirementsInformation.TO_AUDITS_PROPERTY, audit);
        SelectQuery sel = new SelectQuery(RequirementsInformation.class, e);

        return (List<RequirementsInformation>) audit.getObjectContext().performQuery(sel);
    }
    
    public List<Observations> getObservationsFromAudit(Audits audit) {
        Expression e = ExpressionFactory.matchExp(Observations.TO_AUDITS_PROPERTY, audit);
        SelectQuery sel = new SelectQuery(Observations.class, e);

        return (List<Observations>) audit.getObjectContext().performQuery(sel);
    }
    
    public List<AuditJobs> getJobsFromAudit(Audits audit) {
        Expression e = ExpressionFactory.matchExp(AuditJobs.TO_AUDITS_PROPERTY, audit);
        SelectQuery sel = new SelectQuery(AuditJobs.class, e);

        return (List<AuditJobs>) audit.getObjectContext().performQuery(sel);
    }
    
    public List<Subprocesses> getSubprocessesFromAudit(Audits audit) {
        Expression e = ExpressionFactory.matchExp(Subprocesses.TO_AUDITS_PROPERTY, audit);
        SelectQuery sel = new SelectQuery(Subprocesses.class, e);

        return (List<Subprocesses>) audit.getObjectContext().performQuery(sel);
    }
    
}
