/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmv.auditoria.ai.controller;

import com.bmv.auditoria.ai.db.AiDbObjectFromString;
import com.bmv.auditoria.ai.login.InfoUsuario;
import com.bmv.auditoria.ai.persistent.AuditDocuments;
import com.bmv.auditoria.ai.persistent.Auditors;
import com.bmv.auditoria.ai.persistent.Audits;
import com.jach.jachtoolkit.log.Movements;
import org.apache.cayenne.ObjectContext;
import org.apache.log4j.Logger;

/**
 *
 * @author jach
 */
public class AuditFileController {
    
    static Logger logger = Logger.getLogger(AuditFileController.class);
    
    public static void update(AuditDocuments auditFile, Movements m) throws NullPointerException, Exception {
        String tmpMsg;
        if (auditFile == null) {
            tmpMsg = "El objeto AuditDocuments auditFile es nulo. La actualización no puede realizarse.";
            logger.error(tmpMsg);
            throw new NullPointerException(tmpMsg);
        }
        //TODO Validar si habilito el siguiente código.
//        if (m == null) {
//            logger.error("El objeto Movements m es nulo. La actualización no puede realizarse.");
//            throw new NullPointerException();
//        }
        
        String tmpAudit = auditFile.getToAudits().getAuditName();
        String tmpFile = auditFile.getPathFile();
        
        logger.debug(String.format("Tratando de actualizar el archivo '%s' de "
                + "la auditoría '%s'.", tmpFile, tmpAudit));
        try {
            (auditFile.getObjectContext()).commitChanges();
            tmpMsg = String.format("Actualización del archivo '%s' de la "
                    + "auditoría '%s'.", tmpFile, tmpAudit);
            m.save(tmpMsg);
            logger.info(tmpMsg);
        } catch (Exception ex) {
            tmpMsg = String.format("Ocurrió un error al tratar de actualizar "
                    + "el archivo '%s' de la auditoría '%s'.", tmpFile, tmpAudit);
            logger.error(tmpMsg);
            logger.error(ex.getMessage());
            //TODO Validar si pasar el mensaje de error es buena práctica.
            throw new Exception(tmpMsg);
        }
    }
    
    public static void cancelUpdate(AuditDocuments auditFile) throws NullPointerException, Exception {
        String tmpMsg = "";
        if (auditFile == null) {
            tmpMsg = "El objeto AuditDocuments audit es nulo.";
            logger.error(tmpMsg);
            throw new NullPointerException(tmpMsg);
        }
        
        String tmpAudit = auditFile.getToAudits().getAuditName();
        String tmpFile = auditFile.getPathFile();
        
        tmpMsg = String.format("Cancelando la edición del archivo '%s' de "
                + "la auditoría '%s'.", tmpFile, tmpAudit);
        logger.debug(tmpMsg);
        try {
            auditFile.getObjectContext().rollbackChanges();
        } catch (Exception e) {
            tmpMsg = String.format("Ocurrió un error al tratar de hacer "
                    + "rollback a los cambios para la modificación del archivo '%s' de "
                + "la auditoría '%s'.", tmpFile, tmpAudit);
            logger.error(tmpMsg);
            logger.error(e.getMessage());
            //TODO Validar si pasar el mensaje de error es buena práctica.
            throw new Exception(tmpMsg);
        }
    }
    
    public static void create(AuditDocuments auditFile, Movements m, Audits audits) 
            throws NullPointerException, Exception {
        String tmpMsg = "";
        if (auditFile == null) {
            tmpMsg = "El objeto Audits audit es nulo. La creación no puede realizarse.";
            logger.error(tmpMsg);
            throw new NullPointerException(tmpMsg);
        }
        //TODO Validar si habilito el siguiente código.
//        if (m == null) {
//            logger.error("El objeto Movements m es nulo. La actualización no puede realizarse.");
//            throw new NullPointerException();
//        }
        
        try {
            //ObjectContext context = DataContext.createDataContext();
            ObjectContext context = audits.getObjectContext();
            
            String tmpAuditor = (new InfoUsuario()).getUserName();
            
            Auditors objAud = 
                    AiDbObjectFromString.getAuditorsObjectFromString(context, tmpAuditor);
            
            auditFile.setToAuditors(objAud);
            auditFile.setToAudits(audits);
            auditFile.setBitsSize(100l);
            auditFile.setUpdateDate(new java.util.Date());
            
//            tmpAudit = auditFile.getAuditName();
//            tmpCia = auditFile.getToCompanyDepartments().getToCompanies().getShortName();
//            tmpDepto = auditFile.getToCompanyDepartments().getDepartment();
//            logger.debug(String.format("Tratando de crear la auditoría '%s' "
//                    + "del departamento '%s' de la empresa '%s'", 
//                    tmpAudit, tmpDepto, tmpCia));
            
            context.commitChanges();

//            tmpMsg = String.format("Creación de la auditoría '%s' "
//                    + "del departamento '%s' de la empresa '%s'", 
//                    tmpAudit, tmpDepto, tmpCia);
//            m.save(tmpMsg);
//            logger.info(tmpMsg);
        } catch (Exception ex) {
//            tmpMsg = String.format("Ocurrió un error al tratar de crear la "
//                    + "auditoría '%s' del departamento '%s' de la empresa '%s'", 
//                    tmpAudit, tmpDepto, tmpCia);
//            logger.error(tmpMsg);
//            logger.error(ex.getMessage());
//            //TODO Validar si pasar el mensaje de error es buena práctica.
            throw new Exception(tmpMsg);
        }
    }
//
//    public static void main(String[] args) {
//        ObjectContext c = DataContext.createDataContext();
//        
//        Expression e = ExpressionFactory.matchExp(Auditors.AUDITOR_NAME_PROPERTY, "acruzh");
//        SelectQuery sel = new SelectQuery(Auditors.class, e);
//        Auditors auditor = (Auditors) Cayenne.objectForQuery(c, sel);
//        
//        e = ExpressionFactory.matchExp(Audits.AUDIT_NAME_PROPERTY, "Auditoría Fábrica de Software");
//        sel = new SelectQuery(Audits.class, e);
//        Audits audit = (Audits) Cayenne.objectForQuery(c, sel);
//        
//        AuditDocuments auditFile = new AuditDocuments();
//        
//        auditFile.setToAuditors(auditor);
//        auditFile.setToAudits(audit);
//        auditFile.setPathFile("abc.exe");
//        auditFile.setDescription("La descripción");
//        auditFile.setBitsSize(100l);
//        auditFile.setUpdateDate(new java.util.Date());
//
//        c.commitChanges();        
//    }
    
}
