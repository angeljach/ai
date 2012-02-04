package com.bmv.auditoria.ai.persistent.auto;

import java.util.Date;

import org.apache.cayenne.CayenneDataObject;

import com.bmv.auditoria.ai.persistent.Audits;
import com.bmv.auditoria.ai.persistent.Users;

/**
 * Class _AuditCloseReport was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _AuditCloseReport extends CayenneDataObject {

    public static final String CCP_TAIL_PROPERTY = "ccpTail";
    public static final String REPORT_BODY_PROPERTY = "reportBody";
    public static final String REPORT_DATE_PROPERTY = "reportDate";
    public static final String SUBJECT_PROPERTY = "subject";
    public static final String TO_JOB_TITLE_PROPERTY = "toJobTitle";
    public static final String TO_AUDITS_PROPERTY = "toAudits";
    public static final String TO_USERS_PROPERTY = "toUsers";

    public static final String ID_CLOSE_REPORT_PK_COLUMN = "id_close_report";

    public void setCcpTail(String ccpTail) {
        writeProperty("ccpTail", ccpTail);
    }
    public String getCcpTail() {
        return (String)readProperty("ccpTail");
    }

    public void setReportBody(String reportBody) {
        writeProperty("reportBody", reportBody);
    }
    public String getReportBody() {
        return (String)readProperty("reportBody");
    }

    public void setReportDate(Date reportDate) {
        writeProperty("reportDate", reportDate);
    }
    public Date getReportDate() {
        return (Date)readProperty("reportDate");
    }

    public void setSubject(String subject) {
        writeProperty("subject", subject);
    }
    public String getSubject() {
        return (String)readProperty("subject");
    }

    public void setToJobTitle(String toJobTitle) {
        writeProperty("toJobTitle", toJobTitle);
    }
    public String getToJobTitle() {
        return (String)readProperty("toJobTitle");
    }

    public void setToAudits(Audits toAudits) {
        setToOneTarget("toAudits", toAudits, true);
    }

    public Audits getToAudits() {
        return (Audits)readProperty("toAudits");
    }


    public void setToUsers(Users toUsers) {
        setToOneTarget("toUsers", toUsers, true);
    }

    public Users getToUsers() {
        return (Users)readProperty("toUsers");
    }


}
