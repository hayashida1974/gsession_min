package jp.groupsession.v2.cmn.model;

import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] スケジュールから他のプラグインのデータを取得する時に使うパラメータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SchAppendDataParam extends AbstractModel {

    /** ユーザSid リスト*/
    private List<Integer> usrSidList__;
    /** 選択グループSid */
    private String grpSid__;
    /** 開始日付 */
    private UDate frDate__;
    /** 終了日付 */
    private UDate toDate__;
    /** 戻り先URL*/
    private String returnUrl__;


    /**
     * <p>returnUrl を取得します。
     * @return returnUrl
     */
    public String getReturnUrl() {
        return returnUrl__;
    }
    /**
     * <p>returnUrl をセットします。
     * @param returnUrl returnUrl
     */
    public void setReturnUrl(String returnUrl) {
        returnUrl__ = returnUrl;
    }
    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public String getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(String grpSid) {
        grpSid__ = grpSid;
    }
    /**
     * <p>dspDate を取得します。
     * @return dspDate
     */
    public String getDspDate() {
        return dspDate__;
    }
    /**
     * <p>dspDate をセットします。
     * @param dspDate dspDate
     */
    public void setDspDate(String dspDate) {
        dspDate__ = dspDate;
    }
    /** 参照元画面ID */
    private String srcId__;
    /** 参照元画面選択日付 */
    private String dspDate__;

    /**
     * <p>srcId を取得します。
     * @return srcId
     */
    public String getSrcId() {
        return srcId__;
    }
    /**
     * <p>srcId をセットします。
     * @param srcId srcId
     */
    public void setSrcId(String srcId) {
        srcId__ = srcId;
    }
    /**
     * <p>usrSid を取得します。
     * <p>usrSidList の先頭を返します。
     * <p>未定義の場合 0
     * @return usrSid
     */
    public int getUsrSid() {
        if (usrSidList__ == null) {
            return 0;
        }
        return usrSidList__.get(0);
    }
    /**
     * <p>usrSidListに
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        if (usrSidList__ ==  null) {
            usrSidList__ = new ArrayList<>();
        }
        usrSidList__.add(0, usrSid);
    }
    /**
     * <p>frDate を取得します。
     * @return frDate
     */
    public UDate getFrDate() {
        return frDate__;
    }
    /**
     * <p>frDate をセットします。
     * @param frDate frDate
     */
    public void setFrDate(UDate frDate) {
        frDate__ = frDate;
    }
    /**
     * <p>toDate を取得します。
     * @return toDate
     */
    public UDate getToDate() {
        return toDate__;
    }
    /**
     * <p>toDate をセットします。
     * @param toDate toDate
     */
    public void setToDate(UDate toDate) {
        toDate__ = toDate;
    }
    /**
     * <p>usrSidList を取得します。
     * @return usrSidList
     * @see jp.groupsession.v2.cmn.model.SchAppendDataParam#usrSidList__
     */
    public List<Integer> getUsrSidList() {
        return usrSidList__;
    }
    /**
     * <p>usrSidList をセットします。
     * @param usrSidList usrSidList
     * @see jp.groupsession.v2.cmn.model.SchAppendDataParam#usrSidList__
     */
    public void setUsrSidList(List<Integer> usrSidList) {
        usrSidList__ = usrSidList;
    }


}