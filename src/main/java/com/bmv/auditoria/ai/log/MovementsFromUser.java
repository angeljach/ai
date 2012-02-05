package com.bmv.auditoria.ai.log;

import com.bmv.auditoria.ai.persistent.MovementsUser;
import com.bmv.auditoria.ai.persistent.Users;
import com.bmv.auditoria.ai.login.InfoUsuario;
import com.jach.jachtoolkit.log.Movements;
import java.util.Date;
import java.util.List;
import org.apache.cayenne.Cayenne;
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
public class MovementsFromUser implements Movements{
    
    /**
     * Cayenne Context Object.
     */
    private ObjectContext context;
    
    /**
     * User Object. Holds the information of the current logged user.
     */    
    private Users userObj;
    
    /**
     * Logger Object.
     */
    static Logger logger = Logger.getLogger(MovementsFromUser.class);
    
    public MovementsFromUser() {
        logger.trace("Entrando a constructor de Movimientos");
        context = DataContext.getThreadObjectContext();
        
        //---|| Obtención del usuario.
        logger.debug("Obtención del nombre del usuario.");
        InfoUsuario infoUsuario = new InfoUsuario();
        String userName = infoUsuario.getUserName();
        
        logger.debug("Obtención del objeto usuario.");
        Expression e1 = ExpressionFactory.matchExp(Users.USER_NAME_PROPERTY,  userName);
        SelectQuery select = new SelectQuery(Users.class, e1);
        userObj = (Users) Cayenne.objectForQuery(context, select);
    }

    /**
     * Register information in movements_users table. Also, put user_name and date of
     * the movement.
     * @param message String to store in data base.
     */
    @Override
    public void save(String message) {
        logger.debug("Llamado al método registra(mensaje)");
        if(userObj == null) {
            logger.error("Error al tratar de crear un registro en la tabla de "
                    + "movimientos, debido a que el usuario con que se trato "
                    + "de realizar no fue encontrado.");
        } else {
            try {
                MovementsUser mov = context.newObject(MovementsUser.class);
                mov.setMovDate(new Date());
                mov.setDetail(message);
                mov.setToUsers(userObj);

                context.commitChanges();
                logger.info(String.format("Registro creado en la tabla "
                        + "movements_auditor: [%s][%s]", 
                        userObj.getUserName(), message));
            } catch (Exception ex) {
                logger.error(ex.getMessage());
                logger.error(String.format("Error al tratar de insertar el "
                        + "mensaje '%s' en la tabla de movements_auditor.", message));
            }
        }
    }
    
    /**
     * Obtain a list of all registers from table movements_user.
     * @return List of MovementsUser objects.
     */
    @Override
    public List<Movements> getAllMovements() {
        logger.debug("Llamado al método getAllMovements");
        return this.getMovementsFromDate(null);
    }
    
    /**
     * Obtain all the movements_user registered.
     * @param dateFrom If null, return all the movements. If isn't, return from dateFrom.
     * @return List of MovementsUser object.
     */
    @Override
    public List<Movements> getMovementsFromDate(Date dateFrom) {
        logger.debug("Llamado al método getMovementsFromDate(dateFrom)");
        SelectQuery selectStatement;
        
        if (dateFrom != null) {
            logger.debug("Devolviendo todos los movimientos de usuarios registrados en el "
                    + "sistema posteriores a la fecha señalada.");
            Expression expr = ExpressionFactory.greaterExp(
                    MovementsUser.MOV_DATE_PROPERTY, dateFrom);
            selectStatement = new SelectQuery(MovementsUser.class, expr);
        } else {
            //---|| Regreso todos los registros.
            logger.debug("Devolviendo todos los movimientos de usuarios "
                    + "registrados en el sistema.");
            selectStatement = new SelectQuery(MovementsUser.class);
        }
        selectStatement.addOrdering(new Ordering("movDate", SortOrder.DESCENDING));
        
        return (List<Movements>) context.performQuery(selectStatement);
    }
    
}
