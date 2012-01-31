package com.bmv.auditoria.ai.bean;

import com.bmv.auditoria.ai.db.AiDbUtil;
import com.bmv.auditoria.ai.log.MovementsFromAuditor;
import com.bmv.auditoria.ai.persistent.AuditorTeams;
import com.jach.jachtoolkit.log.Movements;
import com.jach.jachtoolkit.persistence.Crud;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import org.apache.log4j.Logger;

/**
 * Allow to manage the CRUD of Auditors Teams.
 * <br>Considerations: Only for Users with the 'Administrador' role.
 * <br>Bean Name = auditorTeam
 * <br>Scope Type = Session
 * @author acruzh
 */
@ManagedBean(name = "auditorTeam")
@SessionScoped
public class AuditorTeamBean implements Crud {
    
    private AuditorTeams current;
    private DataModel items = new ListDataModel();

    private AiDbUtil aiDbUtil;
    private Movements mov;
    static Logger logger = Logger.getLogger(AuditorTeamBean.class);
    private boolean edit;
    
    private static final String INDEX_PAGE = "index";
    private static final String NEW_PAGE = "auditorteamnew";
    private static final String LIST_PAGE = "auditorteamlist";
    //private static final String EDIT_PAGE = "edituser";
    private static final String EDIT_PAGE = "auditorteamnew";

    public AuditorTeamBean() {
        mov = new MovementsFromAuditor();
        aiDbUtil = new AiDbUtil();
        current = new AuditorTeams();
        
        edit = false;
    }
    
    public AuditorTeams getSelected() {
        return (current == null) ? (new AuditorTeams()) : current;
    }
    
    @Override
    public String prepareEdit() {
        logger.debug(String.format("Llamado a página de edición '%s'", EDIT_PAGE));
        current = (AuditorTeams) items.getRowData();
        edit = true;
        return EDIT_PAGE;
    }

    @Override
    public String update() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String cancelUpdate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String prepareCreate() {
        logger.debug(String.format("Llamado a página de alta de usuario '%s'", NEW_PAGE));
        current = new AuditorTeams();
        edit = false;
        return NEW_PAGE;
    }

    @Override
    public String create() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String cancelCreate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void destroy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    
    /* GETTERS AND SETTERS */

    public AuditorTeams getCurrent() {
        return current;
    }

    public void setCurrent(AuditorTeams current) {
        this.current = current;
    }
    
    public DataModel getItems() {
        items.setWrappedData(aiDbUtil.getAuditorTeamList());
        return items;
    }
    
    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
    
}
