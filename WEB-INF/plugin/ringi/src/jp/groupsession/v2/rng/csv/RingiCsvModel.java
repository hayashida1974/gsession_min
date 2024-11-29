package jp.groupsession.v2.rng.csv;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 稟議検索結果からCSV出力する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RingiCsvModel extends AbstractModel {

    /** 稟議SID */
    private int rngSid__;

    /** 稟議ID */
    private String rngId__;

    /** 稟議タイトル */
    private String rngTitle__;

    /** 申請者氏名 */
    private String apprUser__ = null;

    /** 申請日(文字列) */
    private String strRngAppldate__ = null;

    /** 内容 */
    private String rngContent__;

    /** 状態 */
    private int rngStatus__;

    /** コメント */
    private String rngAdmcomment__;

    /** 最終更新日時(文字列) */
    private String strLastManageDate__ = null;

    /** 入力フォーム一覧 */
    private ArrayList<LabelValueBean> formDataList__;

    /** 経路一覧 */
    private ArrayList<LabelValueBean> keiroList__;

    /** 項目最大数 */
    private int maxSize__ = 0;

    /**
     * <p>get RNG_SID value
     * @return RNG_SID value
     */
    public int getRngSid() {
        return rngSid__;
    }
    /**
     * <p>set RNG_SID value
     * @param rngSid RNG_SID value
     */
    public void setRngSid(int rngSid) {
        rngSid__ = rngSid;
    }

    /**
     * <p>get RNG_TITLE value
     * @return RNG_TITLE value
     */
    public String getRngTitle() {
        return rngTitle__;
    }
    /**
     * <p>set RNG_TITLE value
     * @param rngTitle RNG_TITLE value
     */
    public void setRngTitle(String rngTitle) {
        rngTitle__ = rngTitle;
    }

    /**
     * <p>apprUser を取得します。
     * @return apprUser
     */
    public String getApprUser() {
        return apprUser__;
    }
    /**
     * <p>apprUser をセットします。
     * @param apprUser apprUser
     */
    public void setApprUser(String apprUser) {
        apprUser__ = apprUser;
    }

    /**
     * <p>strRngAppldate を取得します。
     * @return strRngAppldate
     */
    public String getStrRngAppldate() {
        return strRngAppldate__;
    }
    /**
     * <p>strRngAppldate をセットします。
     * @param strRngAppldate strRngAppldate
     */
    public void setStrRngAppldate(String strRngAppldate) {
        strRngAppldate__ = strRngAppldate;
    }

    /**
     * <p>get RNG_CONTENT value
     * @return RNG_CONTENT value
     */
    public String getRngContent() {
        return rngContent__;
    }
    /**
     * <p>set RNG_CONTENT value
     * @param rngContent RNG_CONTENT value
     */
    public void setRngContent(String rngContent) {
        rngContent__ = rngContent;
    }

    /**
     * <p>get RNG_STATUS value
     * @return RNG_STATUS value
     */
    public int getRngStatus() {
        return rngStatus__;
    }
    /**
     * <p>set RNG_STATUS value
     * @param rngStatus RNG_STATUS value
     */
    public void setRngStatus(int rngStatus) {
        rngStatus__ = rngStatus;
    }

    /**
     * <p>get RNG_ADMCOMMENT value
     * @return RNG_ADMCOMMENT value
     */
    public String getRngAdmcomment() {
        return rngAdmcomment__;
    }
    /**
     * <p>set RNG_ADMCOMMENT value
     * @param rngAdmcomment RNG_ADMCOMMENT value
     */
    public void setRngAdmcomment(String rngAdmcomment) {
        rngAdmcomment__ = rngAdmcomment;
    }

    /**
     * <p>strLastManageDate を取得します。
     * @return strLastManageDate
     */
    public String getStrLastManageDate() {
        return strLastManageDate__;
    }
    /**
     * <p>strLastManageDate をセットします。
     * @param strLastManageDate strLastManageDate
     */
    public void setStrLastManageDate(String strLastManageDate) {
        strLastManageDate__ = strLastManageDate;
    }

    /**
     * <p>formDataList を取得します。
     * @return formDataList
     */
    public ArrayList<LabelValueBean> getFormDataList() {
        return formDataList__;
    }
    /**
     * <p>formDataList をセットします。
     * @param formDataList formDataList
     */
    public void setFormDataList(ArrayList<LabelValueBean> formDataList) {
        formDataList__ = formDataList;
    }

    /**
     * <p>keiroList を取得します。
     * @return keiroList
     */
    public ArrayList<LabelValueBean> getKeiroList() {
        return keiroList__;
    }
    /**
     * <p>keiroList へセットします。
     * @param keiroList keiroList
     */
    public void setKeiroList(ArrayList<LabelValueBean> keiroList) {
        keiroList__ = keiroList;
    }

    /**
     * <p>maxSize を取得します。
     * @return maxSize
     */
    public int getMaxSize() {
        return maxSize__;
    }
    /**
     * <p>maxSize をセットします。
     * @param maxSize maxSize
     */
    public void setMaxSize(int maxSize) {
        maxSize__ = maxSize;
    }
    /**
     * <p>rngId を取得します。
     * @return rngId
     * @see jp.groupsession.v2.rng.csv.RingiCsvModel#rngId__
     */
    public String getRngId() {
        return rngId__;
    }
    /**
     * <p>rngId をセットします。
     * @param rngId rngId
     * @see jp.groupsession.v2.rng.csv.RingiCsvModel#rngId__
     */
    public void setRngId(String rngId) {
        rngId__ = rngId;
    }
}