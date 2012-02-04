package com.bmv.auditoria.ai.persistent.auto;

import java.util.Date;

import org.apache.cayenne.CayenneDataObject;

import com.bmv.auditoria.ai.persistent.Auditors;
import com.bmv.auditoria.ai.persistent.RequirementsInformation;

/**
 * Class _ReqDocuments was generated by Cayenne.
 * It is probably a good idea to avoid changing this class manually,
 * since it may be overwritten next time code is regenerated.
 * If you need to make any customizations, please use subclass.
 */
public abstract class _ReqDocuments extends CayenneDataObject {

    public static final String BITS_SIZE_PROPERTY = "bitsSize";
    public static final String DESCRIPTION_PROPERTY = "description";
    public static final String PATH_FILE_PROPERTY = "pathFile";
    public static final String UPDATE_DATE_PROPERTY = "updateDate";
    public static final String TO_AUDITORS_PROPERTY = "toAuditors";
    public static final String TO_REQUIREMENTS_INFORMATION_PROPERTY = "toRequirementsInformation";

    public static final String ID_DOC_PK_COLUMN = "id_doc";

    public void setBitsSize(Long bitsSize) {
        writeProperty("bitsSize", bitsSize);
    }
    public Long getBitsSize() {
        return (Long)readProperty("bitsSize");
    }

    public void setDescription(String description) {
        writeProperty("description", description);
    }
    public String getDescription() {
        return (String)readProperty("description");
    }

    public void setPathFile(String pathFile) {
        writeProperty("pathFile", pathFile);
    }
    public String getPathFile() {
        return (String)readProperty("pathFile");
    }

    public void setUpdateDate(Date updateDate) {
        writeProperty("updateDate", updateDate);
    }
    public Date getUpdateDate() {
        return (Date)readProperty("updateDate");
    }

    public void setToAuditors(Auditors toAuditors) {
        setToOneTarget("toAuditors", toAuditors, true);
    }

    public Auditors getToAuditors() {
        return (Auditors)readProperty("toAuditors");
    }


    public void setToRequirementsInformation(RequirementsInformation toRequirementsInformation) {
        setToOneTarget("toRequirementsInformation", toRequirementsInformation, true);
    }

    public RequirementsInformation getToRequirementsInformation() {
        return (RequirementsInformation)readProperty("toRequirementsInformation");
    }


}
