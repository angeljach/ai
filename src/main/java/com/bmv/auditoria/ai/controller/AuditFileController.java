/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bmv.auditoria.ai.controller;

import com.bmv.auditoria.ai.persistent.AuditDocuments;
import com.jach.jachtoolkit.log.Movements;
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
            tmpMsg = "El objeto AuditDocuments auditFile es nulo. La actualizaci�n no puede realizarse.";
            logger.error(tmpMsg);
            throw new NullPointerException(tmpMsg);
        }
        //TODO Validar si habilito el siguiente c�digo.
//        if (m == null) {
//            logger.error("El objeto Movements m es nulo. La actualizaci�n no puede realizarse.");
//            throw new NullPointerException();
//        }
        
        String tmpAudit = auditFile.getToAudits().getAuditName();
        String tmpFile = auditFile.getPathFile();
        
        logger.debug(String.format("Tratando de actualizar el archivo '%s' de "
                + "la auditor�a '%s'.", tmpFile, tmpAudit));
        try {
            (auditFile.getObjectContext()).commitChanges();
            tmpMsg = String.format("Actualizaci�n del archivo '%s' de la "
                    + "auditor�a '%s'.", tmpFile, tmpAudit);
            m.save(tmpMsg);
            logger.info(tmpMsg);
        } catch (Exception ex) {
            tmpMsg = String.format("Ocurri� un error al tratar de actualizar "
                    + "el archivo '%s' de la auditor�a '%s'.", tmpFile, tmpAudit);
            logger.error(tmpMsg);
            logger.error(ex.getMessage());
            //TODO Validar si pasar el mensaje de error es buena pr�ctica.
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
        
        tmpMsg = String.format("Cancelando la edici�n del archivo '%s' de "
                + "la auditor�a '%s'.", tmpFile, tmpAudit);
        logger.debug(tmpMsg);
        try {
            auditFile.getObjectContext().rollbackChanges();
        } catch (Exception e) {
            tmpMsg = String.format("Ocurri� un error al tratar de hacer "
                    + "rollback a los cambios para la modificaci�n del archivo '%s' de "
                + "la auditor�a '%s'.", tmpFile, tmpAudit);
            logger.error(tmpMsg);
            logger.error(e.getMessage());
            //TODO Validar si pasar el mensaje de error es buena pr�ctica.
            throw new Exception(tmpMsg);
        }
    }
    
}
