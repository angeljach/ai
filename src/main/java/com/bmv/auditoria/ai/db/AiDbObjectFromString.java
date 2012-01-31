package com.bmv.auditoria.ai.db;

import com.bmv.auditoria.ai.persistent.AuditStatus;
import com.bmv.auditoria.ai.persistent.AuditTypes;
import com.bmv.auditoria.ai.persistent.AuditorTeams;
import com.bmv.auditoria.ai.persistent.Companies;
import com.bmv.auditoria.ai.persistent.CompanyDepartments;
import org.apache.cayenne.DataObjectUtils;
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
    
    public static AuditorTeams getAuditorTeamsObjectFromString(ObjectContext c, String teamName) {
        logger.debug(String.format("Obteniendo el objeto AuditorTeams de '%s'", teamName));
        Expression e = ExpressionFactory.matchExp(AuditorTeams.TEAM_NAME_PROPERTY, teamName);
        SelectQuery sel = new SelectQuery(AuditorTeams.class, e);
        return (AuditorTeams) DataObjectUtils.objectForQuery(c, sel);
    }
    
    public static CompanyDepartments getCompanyDepartmentsObjectFromString(ObjectContext c, String department) {
        logger.debug(String.format("Obteniendo el objeto CompanyDepartments de '%s'", department));
        Expression e = ExpressionFactory.matchExp(CompanyDepartments.DEPARTMENT_PROPERTY, department);
        SelectQuery sel = new SelectQuery(CompanyDepartments.class, e);
        return (CompanyDepartments) DataObjectUtils.objectForQuery(c, sel);
    }
    
    public static Companies getCompaniesObjectFromString(ObjectContext c, String company) {
        logger.debug(String.format("Obteniendo el objeto Companies de '%s'", company));
        Expression e = ExpressionFactory.matchExp(Companies.COMPLETE_NAME_PROPERTY, company);
        SelectQuery sel = new SelectQuery(Companies.class, e);
        return (Companies) DataObjectUtils.objectForQuery(c, sel);
    }
    
    public static AuditTypes getAuditTypesFromString(ObjectContext c, String auditType) {
        logger.debug(String.format("Obteniendo el objeto AuditTypes de '%s'", auditType));
        Expression e = ExpressionFactory.matchExp(AuditTypes.NAME_PROPERTY, auditType);
        SelectQuery sel = new SelectQuery(AuditTypes.class, e);
        return (AuditTypes) DataObjectUtils.objectForQuery(c, sel);
    }
    
    public static AuditStatus getAuditStatusFromString(ObjectContext c, String auditStatus) {
        logger.debug(String.format("Obteniendo el objeto AuditStatus de '%s'", auditStatus));
        Expression e = ExpressionFactory.matchExp(AuditStatus.NAME_PROPERTY, auditStatus);
        SelectQuery sel = new SelectQuery(AuditStatus.class, e);
        return (AuditStatus) DataObjectUtils.objectForQuery(c, sel);
    }
    
}
