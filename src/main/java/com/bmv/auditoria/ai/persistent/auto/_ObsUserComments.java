package com.bmv.auditoria.ai.persistent.auto;

import java.util.Date;

import org.apache.cayenne.CayenneDataObject;

import com.bmv.auditoria.ai.persistent.Observations;
import com.bmv.auditoria.ai.persistent.Users;

/**
 * Class _ObsUserComments was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _ObsUserComments extends CayenneDataObject {

    public static final String COMMENT_PROPERTY = "comment";
    public static final String COMMENT_DATE_PROPERTY = "commentDate";
    public static final String TO_OBSERVATIONS_PROPERTY = "toObservations";
    public static final String TO_USERS_PROPERTY = "toUsers";

    public static final String ID_OBS_USER_COM_PK_COLUMN = "id_obs_user_com";

    public void setComment(String comment) {
        writeProperty("comment", comment);
    }
    public String getComment() {
        return (String)readProperty("comment");
    }

    public void setCommentDate(Date commentDate) {
        writeProperty("commentDate", commentDate);
    }
    public Date getCommentDate() {
        return (Date)readProperty("commentDate");
    }

    public void setToObservations(Observations toObservations) {
        setToOneTarget("toObservations", toObservations, true);
    }

    public Observations getToObservations() {
        return (Observations)readProperty("toObservations");
    }


    public void setToUsers(Users toUsers) {
        setToOneTarget("toUsers", toUsers, true);
    }

    public Users getToUsers() {
        return (Users)readProperty("toUsers");
    }


}
