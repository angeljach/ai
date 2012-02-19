package com.bmv.auditoria.ai.bean;

import com.bmv.auditoria.ai.db.AiDbUtil;
import com.bmv.auditoria.ai.log.MovementsFromAuditor;
import com.bmv.auditoria.ai.log.MovementsFromUser;
import com.bmv.auditoria.ai.login.InfoUsuario;
import com.bmv.auditoria.ai.persistent.Observations;
import com.bmv.auditoria.ai.vo.ObservationCommentsVO;
import com.jach.jachtoolkit.log.Movements;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author acruzh
 */
@ManagedBean(name = "myObservationComments")
@SessionScoped
public class MyObservationCommentsBean {

    /**
     * The object of the current audit.
     */
    private Observations observation;
    
    private InfoUsuario infoUsr;
    
    private AiDbUtil aiDbUtil;

    private Movements mov;
    static Logger logger = Logger.getLogger(AuditFileBean.class);
    
    List<ObservationCommentsVO> lstObservationComments;
    
    public MyObservationCommentsBean() {
        infoUsr = new InfoUsuario();
        
        //---|| Valido si es un usuario o un auditor el que se está conectando.
        mov = (infoUsr.isAuditor()) ? new MovementsFromAuditor() : new MovementsFromUser();
        
        aiDbUtil = new AiDbUtil();
    }

    public List<ObservationCommentsVO> getLstObservationComments() {
        lstObservationComments = aiDbUtil.getObservationComments(observation);
        return lstObservationComments;
    }

    
    
    
    //---|| Observation
    public Observations getObservation() {
        return observation;
    }

    public void setObservation(Observations observation) {
        this.observation = observation;
    }
    
}
