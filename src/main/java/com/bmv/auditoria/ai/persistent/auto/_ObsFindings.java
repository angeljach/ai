package com.bmv.auditoria.ai.persistent.auto;

import java.util.List;

import org.apache.cayenne.CayenneDataObject;

import com.bmv.auditoria.ai.persistent.Observations;

/**
 * Class _ObsFindings was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _ObsFindings extends CayenneDataObject {

    public static final String ICON_PROPERTY = "icon";
    public static final String NAME_PROPERTY = "name";
    public static final String OBSERVATIONS_ARRAY_PROPERTY = "observationsArray";

    public static final String ID_FINDING_PK_COLUMN = "id_finding";

    public void setIcon(String icon) {
        writeProperty("icon", icon);
    }
    public String getIcon() {
        return (String)readProperty("icon");
    }

    public void setName(String name) {
        writeProperty("name", name);
    }
    public String getName() {
        return (String)readProperty("name");
    }

    public void addToObservationsArray(Observations obj) {
        addToManyTarget("observationsArray", obj, true);
    }
    public void removeFromObservationsArray(Observations obj) {
        removeToManyTarget("observationsArray", obj, true);
    }
    @SuppressWarnings("unchecked")
    public List<Observations> getObservationsArray() {
        return (List<Observations>)readProperty("observationsArray");
    }


}
