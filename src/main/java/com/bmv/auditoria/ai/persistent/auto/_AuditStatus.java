package com.bmv.auditoria.ai.persistent.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import com.bmv.auditoria.ai.persistent.Audits;

/**
 * Class _AuditStatus was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _AuditStatus extends CayenneDataObject {

    public static final String NAME_PROPERTY = "name";
    public static final String AUDITS_ARRAY_PROPERTY = "auditsArray";

    public static final String ID_AUDIT_STATUS_PK_COLUMN = "ID_AUDIT_STATUS";

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void addToAuditsArray(Audits obj) {
        addToManyTarget("auditsArray", obj, true);
    }
    public void removeFromAuditsArray(Audits obj) {
        removeToManyTarget("auditsArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Audits> getAuditsArray() {
        return (List<Audits>)readProperty("auditsArray");
    }


}
