package com.bmv.auditoria.ai.vo;

import java.util.Date;

/**
 *
 * @author acruzh
 */
public class ObservationCommentsVO implements Comparable<ObservationCommentsVO> {
    
    private String observation;
    private String userName;
    private Date commentDate;
    private String comment;

    public ObservationCommentsVO(String observation, String userName, Date commentDate, String comment) {
        this.observation = observation;
        this.userName = userName;
        this.commentDate = commentDate;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Override
    public int compareTo(ObservationCommentsVO o) {
        int lastCmp  = o.commentDate.compareTo(commentDate);
        return (lastCmp);
    }
        
}
