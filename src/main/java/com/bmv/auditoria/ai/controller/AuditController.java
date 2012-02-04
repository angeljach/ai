package com.bmv.auditoria.ai.controller;

import com.bmv.auditoria.ai.db.AiDbObjectFromString;
import com.bmv.auditoria.ai.persistent.AuditStatus;
import com.bmv.auditoria.ai.persistent.AuditTypes;
import com.bmv.auditoria.ai.persistent.AuditorTeams;
import com.bmv.auditoria.ai.persistent.Audits;
import com.bmv.auditoria.ai.persistent.CompanyDepartments;
import com.jach.jachtoolkit.log.Movements;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.access.DataContext;
import org.apache.log4j.Logger;

/**
 *
 * @author acruzh
 */
public class AuditController {
    
    static Logger logger = Logger.getLogger(AuditController.class);
    
    public static void update(Audits audit, Movements m) throws NullPointerException, Exception {
        String tmpMsg;
        if (audit == null) {
            tmpMsg = "El objeto Audits audit es nulo. La actualización no puede realizarse.";
            logger.error(tmpMsg);
            throw new NullPointerException(tmpMsg);
        }
        //TODO Validar si habilito el siguiente código.
//        if (m == null) {
//            logger.error("El objeto Movements m es nulo. La actualización no puede realizarse.");
//            throw new NullPointerException();
//        }
        
        String tmpAudit = audit.getAuditName();
        String tmpCia = audit.getToCompanyDepartments().getToCompanies().getShortName();
        String tmpDepto = audit.getToCompanyDepartments().getDepartment();
        
        logger.debug(String.format("Tratando de actualizar la información de la "
                + "auditoría '%s' del departamento '%s' de la empresa '%s'", tmpAudit, tmpDepto, tmpCia));
        try {
            (audit.getObjectContext()).commitChanges();
            tmpMsg = String.format("Modificación de la información de la "
                + "auditoría '%s' del departamento '%s' de la empresa '%s'", 
                tmpAudit, tmpDepto, tmpCia);
            m.save(tmpMsg);
            logger.info(tmpMsg);
        } catch (Exception ex) {
            tmpMsg = String.format("Ocurrió un error al tratar de modificar "
                    + "la información de la auditoría '%s' del departamento "
                    + "'%s' de la empresa '%s'", tmpAudit, tmpDepto, tmpCia);
            logger.error(tmpMsg);
            logger.error(ex.getMessage());
            //TODO Validar si pasar el mensaje de error es buena práctica.
            throw new Exception(tmpMsg);
        }
    }
    
    public static void cancelUpdate(Audits audit) throws NullPointerException, Exception {
        String tmpMsg = "";
        if (audit == null) {
            tmpMsg = "El objeto Audits audit es nulo.";
            logger.error(tmpMsg);
            throw new NullPointerException(tmpMsg);
        }
        
        String tmpAudit = audit.getAuditName();
        String tmpCia = audit.getToCompanyDepartments().getToCompanies().getShortName();
        String tmpDepto = audit.getToCompanyDepartments().getDepartment();
        
        tmpMsg = String.format("Cancelando la edición de la auditoría '%s' "
                + "del departamento '%s' de la empresa '%s'", 
                tmpAudit, tmpDepto, tmpCia);
        logger.debug(tmpMsg);
        try {
            audit.getObjectContext().rollbackChanges();
        } catch (Exception e) {
            tmpMsg = String.format("Ocurrió un error al tratar de hacer "
                    + "rollback a los cambios para la modificación de la "
                    + "auditoría '%s' del departamento '%s' de la empresa '%s'", 
                    tmpAudit, tmpDepto, tmpCia);
            logger.error(tmpMsg);
            logger.error(e.getMessage());
            //TODO Validar si pasar el mensaje de error es buena práctica.
            throw new Exception(tmpMsg);
        }
    }
    
    public static void create(Audits audit, Movements m, String departmentName, 
            String auditTypeName, String auditStatusName, String auditorTeamName) 
            throws NullPointerException, Exception {
        String tmpMsg = "";
        if (audit == null) {
            tmpMsg = "El objeto Audits audit es nulo. La creación no puede realizarse.";
            logger.error(tmpMsg);
            throw new NullPointerException(tmpMsg);
        }
        //TODO Validar si habilito el siguiente código.
//        if (m == null) {
//            logger.error("El objeto Movements m es nulo. La actualización no puede realizarse.");
//            throw new NullPointerException();
//        }
        
        String tmpAudit = "", tmpCia = "", tmpDepto = "";
        try {
            ObjectContext context = DataContext.createDataContext();
            
            CompanyDepartments objDep 
                    = AiDbObjectFromString.getCompanyDepartmentsObjectFromString(context, departmentName);
            AuditTypes objAtypes 
                    = AiDbObjectFromString.getAuditTypesFromString(context, auditTypeName);
            AuditStatus objAstatus 
                    = AiDbObjectFromString.getAuditStatusFromString(context, auditStatusName);
            AuditorTeams objAteam 
                    = AiDbObjectFromString.getAuditorTeamsObjectFromString(context, auditorTeamName);

            audit.setToCompanyDepartments(objDep);
            audit.setToAuditTypes(objAtypes);
            audit.setToAuditStatus(objAstatus);
            audit.setToAuditorTeams(objAteam);            
            
            tmpAudit = audit.getAuditName();
            tmpCia = audit.getToCompanyDepartments().getToCompanies().getShortName();
            tmpDepto = audit.getToCompanyDepartments().getDepartment();
            logger.debug(String.format("Tratando de crear la auditoría '%s' "
                    + "del departamento '%s' de la empresa '%s'", 
                    tmpAudit, tmpDepto, tmpCia));
            
            context.commitChanges();

            tmpMsg = String.format("Creación de la auditoría '%s' "
                    + "del departamento '%s' de la empresa '%s'", 
                    tmpAudit, tmpDepto, tmpCia);
            m.save(tmpMsg);
            logger.info(tmpMsg);
        } catch (Exception ex) {
            tmpMsg = String.format("Ocurrió un error al tratar de crear la "
                    + "auditoría '%s' del departamento '%s' de la empresa '%s'", 
                    tmpAudit, tmpDepto, tmpCia);
            logger.error(tmpMsg);
            logger.error(ex.getMessage());
            //TODO Validar si pasar el mensaje de error es buena práctica.
            throw new Exception(tmpMsg);
        }
    }
    
}
