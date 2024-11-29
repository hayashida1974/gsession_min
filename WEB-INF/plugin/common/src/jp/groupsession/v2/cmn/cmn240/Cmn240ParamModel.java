package jp.groupsession.v2.cmn.cmn240;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] ニュース一覧(メイン)のパラメータ、出力値を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn240ParamModel extends AbstractParamModel {
    /** ニュース URL */
    private String cmn240newsUrl__ = null;
    /** ニュース ガジェット URL */
    private String cmn240newsGadgetUrl__ = null;
    /** 現在日時 */
    private String cmn240nowTime__ = null;
    /** テーマSID */
    private Integer cmn240themeSid__ = 0;
    /**
     * <p>cmn240newsUrl を取得します。
     * @return cmn240newsUrl
     */
    public String getCmn240newsUrl() {
        return cmn240newsUrl__;
    }
    /**
     * <p>cmn240newsUrl をセットします。
     * @param cmn240newsUrl cmn240newsUrl
     */
    public void setCmn240newsUrl(String cmn240newsUrl) {
        cmn240newsUrl__ = cmn240newsUrl;
    }
    /**
     * <p>cmn240newsGadgetUrl を取得します。
     * @return cmn240newsGadgetUrl
     */
    public String getCmn240newsGadgetUrl() {
        return cmn240newsGadgetUrl__;
    }
    /**
     * <p>cmn240newsGadgetUrl をセットします。
     * @param cmn240newsGadgetUrl cmn240newsGadgetUrl
     */
    public void setCmn240newsGadgetUrl(String cmn240newsGadgetUrl) {
        cmn240newsGadgetUrl__ = cmn240newsGadgetUrl;
    }
    /**
     * <p>cmn240nowTime を取得します。
     * @return cmn240nowTime
     */
    public String getCmn240nowTime() {
        return cmn240nowTime__;
    }
    /**
     * <p>cmn240nowTime をセットします。
     * @param cmn240nowTime cmn240nowTime
     */
    public void setCmn240nowTime(String cmn240nowTime) {
        cmn240nowTime__ = cmn240nowTime;
    }
    /**
     * <p>cmn240themeSid を取得します。
     * @return cmn240themeSid
     * @see jp.groupsession.v2.cmn.cmn240.Cmn240ParamModel#cmn240themeSid__
     */
    public Integer getCmn240themeSid() {
        return cmn240themeSid__;
    }
    /**
     * <p>cmn240themeSid をセットします。
     * @param cmn240themeSid cmn240themeSid
     * @see jp.groupsession.v2.cmn.cmn240.Cmn240ParamModel#cmn240themeSid__
     */
    public void setCmn240themeSid(Integer cmn240themeSid) {
        cmn240themeSid__ = cmn240themeSid;
    }
}
