package com.bmv.auditoria.ai.persistent.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import com.bmv.auditoria.ai.persistent.AuditJobs;

/**
 * Class _AuditPhasesTypes was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _AuditPhasesTypes extends CayenneDataObject {

    public static final String NAME_PROPERTY = "name";
    public static final String AUDIT_JOBS_ARRAY_PROPERTY = "auditJobsArray";

    public static final String ID_AUDIT_PHASES_TYPE_PK_COLUMN = "id_audit_phases_type";

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void addToAuditJobsArray(AuditJobs obj) {
        addToManyTarget("auditJobsArray", obj, true);
    }
    public void removeFromAuditJobsArray(AuditJobs obj) {
        removeToManyTarget("auditJobsArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<AuditJobs> getAuditJobsArray() {
        return (List<AuditJobs>)readProperty("auditJobsArray");
    }


}
