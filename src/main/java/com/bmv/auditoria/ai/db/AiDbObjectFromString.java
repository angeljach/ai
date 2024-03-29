package com.bmv.auditoria.ai.db;

import com.bmv.auditoria.ai.persistent.AuditStatus;
import com.bmv.auditoria.ai.persistent.AuditTypes;
import com.bmv.auditoria.ai.persistent.AuditorTeams;
import com.bmv.auditoria.ai.persistent.Auditors;
import com.bmv.auditoria.ai.persistent.Companies;
import com.bmv.auditoria.ai.persistent.CompanyDepartments;
import com.bmv.auditoria.ai.persistent.ObsComplexities;
import com.bmv.auditoria.ai.persistent.ObsFindings;
import com.bmv.auditoria.ai.persistent.ObsImpacts;
import com.bmv.auditoria.ai.persistent.ObsStatus;
import com.bmv.auditoria.ai.persistent.Users;
import org.apache.cayenne.Cayenne;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.SelectQuery;
import org.apache.log4j.Logger;

/**
 *
 * @author jach
 */
public class AiDbObjectFromString {
    
    static Logger logger = Logger.getLogger(AiDbObjectFromString.class);
    
    public static Auditors getAuditorsObjectFromString(ObjectContext c, String auditorName) {
        logger.debug(String.format("Obteniendo el objeto AuditorTeams de '%s'", auditorName));
        Expression e = ExpressionFactory.matchExp(Auditors.AUDITOR_NAME_PROPERTY, auditorName);
        SelectQuery sel = new SelectQuery(Auditors.class, e);
        return (Auditors) Cayenne.objectForQuery(c, sel);
    }
    
    public static Users getUsersObjectFromString(ObjectContext c, String userName) {
        logger.debug(String.format("Obteniendo el objeto AuditorTeams de '%s'", userName));
        Expression e = ExpressionFactory.matchExp(Users.USER_NAME_PROPERTY, userName);
        SelectQuery sel = new SelectQuery(Users.class, e);
        return (Users) Cayenne.objectForQuery(c, sel);
    }
    
    public static AuditorTeams getAuditorTeamsObjectFromString(ObjectContext c, String teamName) {
        logger.debug(String.format("Obteniendo el objeto AuditorTeams de '%s'", teamName));
        Expression e = ExpressionFactory.matchExp(AuditorTeams.TEAM_NAME_PROPERTY, teamName);
        SelectQuery sel = new SelectQuery(AuditorTeams.class, e);
        return (AuditorTeams) Cayenne.objectForQuery(c, sel);
    }
    
    public static CompanyDepartments getCompanyDepartmentsObjectFromString(ObjectContext c, String department) {
        logger.debug(String.format("Obteniendo el objeto CompanyDepartments de '%s'", department));
        Expression e = ExpressionFactory.matchExp(CompanyDepartments.DEPARTMENT_PROPERTY, department);
        SelectQuery sel = new SelectQuery(CompanyDepartments.class, e);
        return (CompanyDepartments) Cayenne.objectForQuery(c, sel);
    }
    
    public static Companies getCompaniesObjectFromString(ObjectContext c, String company) {
        logger.debug(String.format("Obteniendo el objeto Companies de '%s'", company));
        Expression e = ExpressionFactory.matchExp(Companies.COMPLETE_NAME_PROPERTY, company);
        SelectQuery sel = new SelectQuery(Companies.class, e);
        return (Companies) Cayenne.objectForQuery(c, sel);
    }
    
    public static AuditTypes getAuditTypesFromString(ObjectContext c, String auditType) {
        logger.debug(String.format("Obteniendo el objeto AuditTypes de '%s'", auditType));
        Expression e = ExpressionFactory.matchExp(AuditTypes.NAME_PROPERTY, auditType);
        SelectQuery sel = new SelectQuery(AuditTypes.class, e);
        return (AuditTypes) Cayenne.objectForQuery(c, sel);
    }
    
    public static AuditStatus getAuditStatusFromString(ObjectContext c, String auditStatus) {
        logger.debug(String.format("Obteniendo el objeto AuditStatus de '%s'", auditStatus));
        Expression e = ExpressionFactory.matchExp(AuditStatus.NAME_PROPERTY, auditStatus);
        SelectQuery sel = new SelectQuery(AuditStatus.class, e);
        return (AuditStatus) Cayenne.objectForQuery(c, sel);
    }
    
    
    
    /*Observations*/
    
    public static ObsStatus getObsStatusFromString(ObjectContext c, String obsStatus) {
        logger.debug(String.format("Obteniendo el objeto ObsStatus de '%s'", obsStatus));
        Expression e = ExpressionFactory.matchExp(ObsStatus.NAME_PROPERTY, obsStatus);
        SelectQuery sel = new SelectQuery(ObsStatus.class, e);
        return (ObsStatus) Cayenne.objectForQuery(c, sel);
    }
    
    public static ObsFindings getObsFindingsFromString(ObjectContext c, String obsFindings) {
        logger.debug(String.format("Obteniendo el objeto ObsFindings de '%s'", obsFindings));
        Expression e = ExpressionFactory.matchExp(ObsFindings.NAME_PROPERTY, obsFindings);
        SelectQuery sel = new SelectQuery(ObsFindings.class, e);
        return (ObsFindings) Cayenne.objectForQuery(c, sel);
    }
    
    public static ObsImpacts getObsImpactsFromString(ObjectContext c, String obsImpacts) {
        logger.debug(String.format("Obteniendo el objeto ObsImpacts de '%s'", obsImpacts));
        Expression e = ExpressionFactory.matchExp(ObsImpacts.NAME_PROPERTY, obsImpacts);
        SelectQuery sel = new SelectQuery(ObsImpacts.class, e);
        return (ObsImpacts) Cayenne.objectForQuery(c, sel);
    }
    
    public static ObsComplexities getObsComplexitiesFromString(ObjectContext c, String obsComplexities) {
        logger.debug(String.format("Obteniendo el objeto ObsComplexities de '%s'", obsComplexities));
        Expression e = ExpressionFactory.matchExp(ObsComplexities.NAME_PROPERTY, obsComplexities);
        SelectQuery sel = new SelectQuery(ObsComplexities.class, e);
        return (ObsComplexities) Cayenne.objectForQuery(c, sel);
    }
    
}
