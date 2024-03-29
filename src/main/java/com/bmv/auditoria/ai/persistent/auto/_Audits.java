package com.bmv.auditoria.ai.persistent.auto;

import java.util.Date;
import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import com.bmv.auditoria.ai.persistent.AuditCloseReport;
import com.bmv.auditoria.ai.persistent.AuditDocuments;
import com.bmv.auditoria.ai.persistent.AuditInitialReport;
import com.bmv.auditoria.ai.persistent.AuditJobs;
import com.bmv.auditoria.ai.persistent.AuditStatus;
import com.bmv.auditoria.ai.persistent.AuditTypes;
import com.bmv.auditoria.ai.persistent.AuditorTeams;
import com.bmv.auditoria.ai.persistent.CompanyDepartments;
import com.bmv.auditoria.ai.persistent.Observations;
import com.bmv.auditoria.ai.persistent.RequirementsInformation;
import com.bmv.auditoria.ai.persistent.Subprocesses;

/**
 * Class _Audits was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _Audits extends CayenneDataObject {

    public static final String AUDIT_NAME_PROPERTY = "auditName";
    public static final String AUDIT_SCOPE_PROPERTY = "auditScope";
    public static final String FIELDWORK_END_DATE_PROPERTY = "fieldworkEndDate";
    public static final String FIELDWORK_START_DATE_PROPERTY = "fieldworkStartDate";
    public static final String OBJECTIVE_PROPERTY = "objective";
    public static final String PLANNING_END_DATE_PROPERTY = "planningEndDate";
    public static final String PLANNING_START_DATE_PROPERTY = "planningStartDate";
    public static final String RECOMMENDATION_END_DATE_PROPERTY = "recommendationEndDate";
    public static final String RECOMMENDATION_START_DATE_PROPERTY = "recommendationStartDate";
    public static final String SPENT_HOURS_PROPERTY = "spentHours";
    public static final String AUDIT_CLOSE_REPORT_ARRAY_PROPERTY = "auditCloseReportArray";
    public static final String AUDIT_DOCUMENTS_ARRAY_PROPERTY = "auditDocumentsArray";
    public static final String AUDIT_INITIAL_REPORT_ARRAY_PROPERTY = "auditInitialReportArray";
    public static final String AUDIT_JOBS_ARRAY_PROPERTY = "auditJobsArray";
    public static final String OBSERVATIONS_ARRAY_PROPERTY = "observationsArray";
    public static final String REQUIREMENTS_INFORMATION_ARRAY_PROPERTY = "requirementsInformationArray";
    public static final String SUBPROCESSES_ARRAY_PROPERTY = "subprocessesArray";
    public static final String TO_AUDIT_STATUS_PROPERTY = "toAuditStatus";
    public static final String TO_AUDIT_TYPES_PROPERTY = "toAuditTypes";
    public static final String TO_AUDITOR_TEAMS_PROPERTY = "toAuditorTeams";
    public static final String TO_COMPANY_DEPARTMENTS_PROPERTY = "toCompanyDepartments";

    public static final String ID_AUDIT_PK_COLUMN = "id_audit";

    public void setAuditName(String auditName) {
        writeProperty("auditName", auditName);
    }
    public String getAuditName() {
        return (String)readProperty("auditName");
    }

    public void setAuditScope(String auditScope) {
        writeProperty("auditScope", auditScope);
    }
    public String getAuditScope() {
        return (String)readProperty("auditScope");
    }

    public void setFieldworkEndDate(Date fieldworkEndDate) {
        writeProperty("fieldworkEndDate", fieldworkEndDate);
    }
    public Date getFieldworkEndDate() {
        return (Date)readProperty("fieldworkEndDate");
    }

    public void setFieldworkStartDate(Date fieldworkStartDate) {
        writeProperty("fieldworkStartDate", fieldworkStartDate);
    }
    public Date getFieldworkStartDate() {
        return (Date)readProperty("fieldworkStartDate");
    }

    public void setObjective(String objective) {
        writeProperty("objective", objective);
    }
    public String getObjective() {
        return (String)readProperty("objective");
    }

    public void setPlanningEndDate(Date planningEndDate) {
        writeProperty("planningEndDate", planningEndDate);
    }
    public Date getPlanningEndDate() {
        return (Date)readProperty("planningEndDate");
    }

    public void setPlanningStartDate(Date planningStartDate) {
        writeProperty("planningStartDate", planningStartDate);
    }
    public Date getPlanningStartDate() {
        return (Date)readProperty("planningStartDate");
    }

    public void setRecommendationEndDate(Date recommendationEndDate) {
        writeProperty("recommendationEndDate", recommendationEndDate);
    }
    public Date getRecommendationEndDate() {
        return (Date)readProperty("recommendationEndDate");
    }

    public void setRecommendationStartDate(Date recommendationStartDate) {
        writeProperty("recommendationStartDate", recommendationStartDate);
    }
    public Date getRecommendationStartDate() {
        return (Date)readProperty("recommendationStartDate");
    }

    public void setSpentHours(Integer spentHours) {
        writeProperty("spentHours", spentHours);
    }
    public Integer getSpentHours() {
        return (Integer)readProperty("spentHours");
    }

    public void addToAuditCloseReportArray(AuditCloseReport obj) {
        addToManyTarget("auditCloseReportArray", obj, true);
    }
    public void removeFromAuditCloseReportArray(AuditCloseReport obj) {
        removeToManyTarget("auditCloseReportArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<AuditCloseReport> getAuditCloseReportArray() {
        return (List<AuditCloseReport>)readProperty("auditCloseReportArray");
    }


    public void addToAuditDocumentsArray(AuditDocuments obj) {
        addToManyTarget("auditDocumentsArray", obj, true);
    }
    public void removeFromAuditDocumentsArray(AuditDocuments obj) {
        removeToManyTarget("auditDocumentsArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<AuditDocuments> getAuditDocumentsArray() {
        return (List<AuditDocuments>)readProperty("auditDocumentsArray");
    }


    public void addToAuditInitialReportArray(AuditInitialReport obj) {
        addToManyTarget("auditInitialReportArray", obj, true);
    }
    public void removeFromAuditInitialReportArray(AuditInitialReport obj) {
        removeToManyTarget("auditInitialReportArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<AuditInitialReport> getAuditInitialReportArray() {
        return (List<AuditInitialReport>)readProperty("auditInitialReportArray");
    }


    public void addToAuditJobsArray(AuditJobs obj) {
        addToManyTarget("auditJobsArray", obj, true);
    }
    public void removeFromAuditJobsArray(AuditJobs obj) {
        removeToManyTarget("auditJobsArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<AuditJobs> getAuditJobsArray() {
        return (List<AuditJobs>)readProperty("auditJobsArray");
    }


    public void addToObservationsArray(Observations obj) {
        addToManyTarget("observationsArray", obj, true);
    }
    public void removeFromObservationsArray(Observations obj) {
        removeToManyTarget("observationsArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Observations> getObservationsArray() {
        return (List<Observations>)readProperty("observationsArray");
    }


    public void addToRequirementsInformationArray(RequirementsInformation obj) {
        addToManyTarget("requirementsInformationArray", obj, true);
    }
    public void removeFromRequirementsInformationArray(RequirementsInformation obj) {
        removeToManyTarget("requirementsInformationArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<RequirementsInformation> getRequirementsInformationArray() {
        return (List<RequirementsInformation>)readProperty("requirementsInformationArray");
    }


    public void addToSubprocessesArray(Subprocesses obj) {
        addToManyTarget("subprocessesArray", obj, true);
    }
    public void removeFromSubprocessesArray(Subprocesses obj) {
        removeToManyTarget("subprocessesArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Subprocesses> getSubprocessesArray() {
        return (List<Subprocesses>)readProperty("subprocessesArray");
    }


    public void setToAuditStatus(AuditStatus toAuditStatus) {
        setToOneTarget("toAuditStatus", toAuditStatus, true);
    }

    public AuditStatus getToAuditStatus() {
        return (AuditStatus)readProperty("toAuditStatus");
    }


    public void setToAuditTypes(AuditTypes toAuditTypes) {
        setToOneTarget("toAuditTypes", toAuditTypes, true);
    }

    public AuditTypes getToAuditTypes() {
        return (AuditTypes)readProperty("toAuditTypes");
    }


    public void setToAuditorTeams(AuditorTeams toAuditorTeams) {
        setToOneTarget("toAuditorTeams", toAuditorTeams, true);
    }

    public AuditorTeams getToAuditorTeams() {
        return (AuditorTeams)readProperty("toAuditorTeams");
    }


    public void setToCompanyDepartments(CompanyDepartments toCompanyDepartments) {
        setToOneTarget("toCompanyDepartments", toCompanyDepartments, true);
    }

    public CompanyDepartments getToCompanyDepartments() {
        return (CompanyDepartments)readProperty("toCompanyDepartments");
    }


}
