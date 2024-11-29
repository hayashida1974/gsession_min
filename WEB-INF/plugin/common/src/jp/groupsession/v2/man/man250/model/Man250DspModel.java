package jp.groupsession.v2.man.man250.model;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ検索画面 検索結果(表示用)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man250DspModel extends AbstractModel {

    /** 実行ユーザSID */
    private int usrSid__;
    /** 実行ユーザ姓 */
    private String usrNameSei__;
    /** 実行ユーザ名 */
    private String usrNameMei__;
    /** 実行ユーザ 無効フラグ */
    private int usrUkoFlg__;

    /** 日時 */
    private String logDate__;
    /** ログレベル */
    private String logLevel__;
    /** プラグイン名 */
    private String pluginName__;
    /** 画面・機能名 */
    private String pgName__;
    /** 内容 */
    private String value__;
    /** 操作コード */
    private String opCode__;
    /** IPアドレス */
    private String logIp__;

    /** 画面ID */
    private String pgId__;
    /** バージョン */
    private String version__;

    /** ユーザ有効無効フラグ */

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>usrNameSei を取得します。
     * @return usrNameSei
     */
    public String getUsrNameSei() {
        return usrNameSei__;
    }
    /**
     * <p>usrNameSei をセットします。
     * @param usrNameSei usrNameSei
     */
    public void setUsrNameSei(String usrNameSei) {
        usrNameSei__ = usrNameSei;
    }
    /**
     * <p>usrNameMei を取得します。
     * @return usrNameMei
     */
    public String getUsrNameMei() {
        return usrNameMei__;
    }
    /**
     * <p>usrNameMei をセットします。
     * @param usrNameMei usrNameMei
     */
    public void setUsrNameMei(String usrNameMei) {
        usrNameMei__ = usrNameMei;
    }
    /**
     * <p>logDate を取得します。
     * @return logDate
     */
    public String getLogDate() {
        return logDate__;
    }
    /**
     * <p>logDate をセットします。
     * @param logDate logDate
     */
    public void setLogDate(String logDate) {
        logDate__ = logDate;
    }
    /**
     * <p>logLevel を取得します。
     * @return logLevel
     */
    public String getLogLevel() {
        return logLevel__;
    }
    /**
     * <p>logLevel をセットします。
     * @param logLevel logLevel
     */
    public void setLogLevel(String logLevel) {
        logLevel__ = logLevel;
    }
    /**
     * <p>pluginName を取得します。
     * @return pluginName
     */
    public String getPluginName() {
        return pluginName__;
    }
    /**
     * <p>pluginName をセットします。
     * @param pluginName pluginName
     */
    public void setPluginName(String pluginName) {
        pluginName__ = pluginName;
    }
    /**
     * <p>pgName を取得します。
     * @return pgName
     */
    public String getPgName() {
        return pgName__;
    }
    /**
     * <p>pgName をセットします。
     * @param pgName pgName
     */
    public void setPgName(String pgName) {
        pgName__ = pgName;
    }
    /**
     * <p>value を取得します。
     * @return value
     */
    public String getValue() {
        return value__;
    }
    /**
     * <p>value をセットします。
     * @param value value
     */
    public void setValue(String value) {
        value__ = value;
    }
    /**
     * <p>opCode を取得します。
     * @return opCode
     */
    public String getOpCode() {
        return opCode__;
    }
    /**
     * <p>opCode をセットします。
     * @param opCode opCode
     */
    public void setOpCode(String opCode) {
        opCode__ = opCode;
    }
    /**
     * <p>logIp を取得します。
     * @return logIp
     */
    public String getLogIp() {
        return logIp__;
    }
    /**
     * <p>logIp をセットします。
     * @param logIp logIp
     */
    public void setLogIp(String logIp) {
        logIp__ = logIp;
    }
    /**
     * <p>pgId を取得します。
     * @return pgId
     */
    public String getPgId() {
        return pgId__;
    }
    /**
     * <p>pgId をセットします。
     * @param pgId pgId
     */
    public void setPgId(String pgId) {
        pgId__ = pgId;
    }
    /**
     * <p>version を取得します。
     * @return version
     */
    public String getVersion() {
        return version__;
    }
    /**
     * <p>version をセットします。
     * @param version version
     */
    public void setVersion(String version) {
        version__ = version;
    }
    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }
    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }
 }
