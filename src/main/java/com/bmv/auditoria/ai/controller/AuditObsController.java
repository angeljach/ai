package com.bmv.auditoria.ai.controller;

import com.bmv.auditoria.ai.db.AiDbObjectFromString;
import com.bmv.auditoria.ai.login.InfoUsuario;
import com.bmv.auditoria.ai.persistent.Auditors;
import com.bmv.auditoria.ai.persistent.Audits;
import com.bmv.auditoria.ai.persistent.ObsComplexities;
import com.bmv.auditoria.ai.persistent.ObsFindings;
import com.bmv.auditoria.ai.persistent.ObsImpacts;
import com.bmv.auditoria.ai.persistent.ObsStatus;
import com.bmv.auditoria.ai.persistent.Observations;
import com.bmv.auditoria.ai.persistent.Users;
import com.jach.jachtoolkit.log.Movements;
import java.util.Date;
import org.apache.cayenne.ObjectContext;
import org.apache.log4j.Logger;

/**
 *
 * @author jach
 */
public class AuditObsController {
    
    static Logger logger = Logger.getLogger(AuditObsController.class);
    
    public static void update(Observations auditObs, Movements m) 
            throws NullPointerException, Exception {
        String tmpMsg;
        if (auditObs == null) {
            tmpMsg = "El objeto Observations auditObs es nulo. La "
                    + "actualización no puede realizarse.";
            logger.error(tmpMsg);
            throw new NullPointerException(tmpMsg);
        }
        //TODO Validar si habilito el siguiente código.
//        if (m == null) {
//            logger.error("El objeto Movements m es nulo. La actualización no puede realizarse.");
//            throw new NullPointerException();
//        }
        
        String tmpAudit = auditObs.getToAudits().getAuditName();
        String tmpObs = auditObs.getObservation();
        
        logger.debug(String.format("Tratando de actualizar la observación '%s' "
                + "de la auditoría '%s'.", tmpObs, tmpAudit));
        try {
            (auditObs.getObjectContext()).commitChanges();
            tmpMsg = String.format("Actualización de la observación '%s' de la "
                    + "auditoría '%s'.", tmpObs, tmpAudit);
            m.save(tmpMsg);
            logger.info(tmpMsg);
        } catch (Exception ex) {
            tmpMsg = String.format("Ocurrió un error al tratar de actualizar la "
                    + "observación '%s' de la auditoría '%s'.", tmpObs, tmpAudit);
            logger.error(tmpMsg);
            logger.error(ex.getMessage());
            //TODO Validar si pasar el mensaje de error es buena práctica.
            throw new Exception(tmpMsg);
        }
    }
    
    public static void cancelUpdate(Observations auditObs) 
            throws NullPointerException, Exception {
        String tmpMsg = "";
        if (auditObs == null) {
            tmpMsg = "El objeto Observations auditObs es nulo.";
            logger.error(tmpMsg);
            throw new NullPointerException(tmpMsg);
        }
        
        String tmpAudit = auditObs.getToAudits().getAuditName();
        String tmpObs = auditObs.getObservation();
        
        tmpMsg = String.format("Cancelando la edición de la observación '%s' "
                + "de la auditoría '%s'.", tmpObs, tmpAudit);
        logger.debug(tmpMsg);
        try {
            auditObs.getObjectContext().rollbackChanges();
        } catch (Exception e) {
            tmpMsg = String.format("Ocurrió un error al tratar de hacer "
                    + "rollback a los cambios para la modificación de la "
                    + "observación '%s' de la auditoría '%s'.", tmpObs, tmpAudit);
            logger.error(tmpMsg);
            logger.error(e.getMessage());
            //TODO Validar si pasar el mensaje de error es buena práctica.
            throw new Exception(tmpMsg);
        }
    }
    
    public static void create(Observations auditObs, Movements m, Audits audits,
            String obsStatusName, String responsibleUserName, String obsFindingName,
                    String obsImpactName, String obsComplexityName) 
            throws NullPointerException, Exception {
        String tmpMsg = "";
        if (auditObs == null) {
            tmpMsg = "El objeto Observations auditObs es nulo. "
                    + "La creación no puede realizarse.";
            logger.error(tmpMsg);
            throw new NullPointerException(tmpMsg);
        }
        //TODO Validar si habilito el siguiente código.
//        if (m == null) {
//            logger.error("El objeto Movements m es nulo. La actualización no puede realizarse.");
//            throw new NullPointerException();
//        }
        
        String tmpAuditor = (new InfoUsuario()).getUserName();
        String tmpAudit = "", tmpObs = "";
        
        try {
            ObjectContext context = audits.getObjectContext();
            
            Auditors objAud = AiDbObjectFromString.getAuditorsObjectFromString(context, tmpAuditor);
            ObsStatus objObsStat = AiDbObjectFromString.getObsStatusFromString(context, obsStatusName);
            Users objUser = AiDbObjectFromString.getUsersObjectFromString(context, responsibleUserName);
            ObsFindings objObsFinding = AiDbObjectFromString.getObsFindingsFromString(context, obsFindingName);
            ObsImpacts objObsImpact = AiDbObjectFromString.getObsImpactsFromString(context, obsImpactName);
            ObsComplexities objObsComplexity = AiDbObjectFromString.getObsComplexitiesFromString(context, obsComplexityName);
            
            auditObs.setToAuditors(objAud);
            auditObs.setToAudits(audits);
            auditObs.setCreationDate(new Date());   //Fecha de Creación
            auditObs.setToObsStatus(objObsStat);
            auditObs.setToUsers(objUser);
            auditObs.setToObsFindings(objObsFinding);
            auditObs.setToObsImpacts(objObsImpact);
            auditObs.setToObsComplexities(objObsComplexity);
            
            tmpAudit = audits.getAuditName();
            tmpObs = auditObs.getObservation();
            logger.debug(String.format("Tratando de crear la observación '%s' "
                    + "de la auditoría '%s'", tmpObs, tmpAudit));
            
            context.commitChanges();

            tmpMsg = String.format("Creación de la observación '%s' de la "
                    + "auditoría '%s'", tmpObs, tmpAudit);
            m.save(tmpMsg);
            logger.info(tmpMsg);
        } catch (Exception ex) {
            tmpMsg = String.format("Ocurrió un error al tratar de crear la "
                    + "observación '%s' de la auditoría '%s'", tmpObs, tmpAudit);
            logger.error(tmpMsg);
            logger.error(ex.getMessage());
            //TODO Validar si pasar el mensaje de error es buena práctica.
            throw new Exception(tmpMsg);
        }
    }
    
}
