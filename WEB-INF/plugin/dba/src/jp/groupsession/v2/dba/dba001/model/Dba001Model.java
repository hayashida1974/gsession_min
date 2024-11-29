package jp.groupsession.v2.dba.dba001.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] DBAのDaoModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Dba001Model extends AbstractModel  {
    /** SEQUENCE_CATALOG */
    private String dbaCatalog__ = null;
    /** SEQUENCE_SCHEMA */
    private String dbaSchema__ = null;
    /** SEQUENCE_NAME */
    private String dbaName__ = null;
    /** CURRENT_VALUE */
    private int dbaValue__ = 0;
    /** INCREMENT */
    private int dbaIncrement__ = 0;
    /** IS_GENERATED */
    private boolean dbaGenerated__ = false;
    /** REMARKS */
    private String dbaRemarks__ = null;
    /** CACHE */
    private int dbaCache__ = 0;
    /** ID */
    private int dbaId__ = 0;

    /**
     * <p>dbaCatalog を取得します。
     * @return dbaCatalog
     */
    public String getDbaCatalog() {
        return dbaCatalog__;
    }
    /**
     * <p>dbaCatalog をセットします。
     * @param dbaCatalog dbaCatalog
     */
    public void setDbaCatalog(String dbaCatalog) {
        dbaCatalog__ = dbaCatalog;
    }
    /**
     * <p>dbaSchema を取得します。
     * @return dbaSchema
     */
    public String getDbaSchema() {
        return dbaSchema__;
    }
    /**
     * <p>dbaSchema をセットします。
     * @param dbaSchema dbaSchema
     */
    public void setDbaSchema(String dbaSchema) {
        dbaSchema__ = dbaSchema;
    }
    /**
     * <p>dbaName を取得します。
     * @return dbaName
     */
    public String getDbaName() {
        return dbaName__;
    }
    /**
     * <p>dbaName をセットします。
     * @param dbaName dbaName
     */
    public void setDbaName(String dbaName) {
        dbaName__ = dbaName;
    }
    /**
     * <p>dbaValue を取得します。
     * @return dbaValue
     */
    public int getDbaValue() {
        return dbaValue__;
    }
    /**
     * <p>dbaValue をセットします。
     * @param dbaValue dbaValue
     */
    public void setDbaValue(int dbaValue) {
        dbaValue__ = dbaValue;
    }
    /**
     * <p>dbaIncrement を取得します。
     * @return dbaIncrement
     */
    public int getDbaIncrement() {
        return dbaIncrement__;
    }
    /**
     * <p>dbaIncrement をセットします。
     * @param dbaIncrement dbaIncrement
     */
    public void setDbaIncrement(int dbaIncrement) {
        dbaIncrement__ = dbaIncrement;
    }
    /**
     * <p>dbaGenerated を取得します。
     * @return dbaGenerated
     */
    public boolean isDbaGenerated() {
        return dbaGenerated__;
    }
    /**
     * <p>dbaGenerated をセットします。
     * @param dbaGenerated dbaGenerated
     */
    public void setDbaGenerated(boolean dbaGenerated) {
        dbaGenerated__ = dbaGenerated;
    }
    /**
     * <p>dbaRemarks を取得します。
     * @return dbaRemarks
     */
    public String getDbaRemarks() {
        return dbaRemarks__;
    }
    /**
     * <p>dbaRemarks をセットします。
     * @param dbaRemarks dbaRemarks
     */
    public void setDbaRemarks(String dbaRemarks) {
        dbaRemarks__ = dbaRemarks;
    }
    /**
     * <p>dbaCache を取得します。
     * @return dbaCache
     */
    public int getDbaCache() {
        return dbaCache__;
    }
    /**
     * <p>dbaCache をセットします。
     * @param dbaCache dbaCache
     */
    public void setDbaCache(int dbaCache) {
        dbaCache__ = dbaCache;
    }
    /**
     * <p>dbaId を取得します。
     * @return dbaId
     */
    public int getDbaId() {
        return dbaId__;
    }
    /**
     * <p>dbaId をセットします。
     * @param dbaId dbaId
     */
    public void setDbaId(int dbaId) {
        dbaId__ = dbaId;
    }
}
