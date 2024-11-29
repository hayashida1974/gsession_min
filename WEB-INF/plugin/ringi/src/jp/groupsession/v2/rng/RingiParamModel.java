package jp.groupsession.v2.rng;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

/**
 * <br>[機  能] 稟議プラグインで共通使用する情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RingiParamModel extends AbstractParamModel {
    /** 稟議SID */
    private int rngSid__ = -1;
    /** 稟議テンプレートSID */
    private int rngSelectTplSid__ = 0;
    /** テンプレートモード */
    private int rngTemplateMode__ = -1;
    /** 処理モード */
    private int rngProcMode__ = RngConst.RNG_MODE_JYUSIN;
    /** 処理モード */
    private int rngCmdMode__ = RngConst.RNG_CMDMODE_ADD;
    /** 申請モード */
    private int rngApprMode__ = RngConst.RNG_APPRMODE_APPR;
    /** 画面モード */
    private int rngDspMode__ = RngConst.RNG_MODE_NOT_MAIN;
    /** 検索条件 キーワード */
    private String rngSearchKeyword__ = null;
    /** 検索条件 グループ */
    private int rngSearchGroup__ = -1;
    /** 検索条件 申請者 */
    private int rngSearchUser__ = -1;

    /**
     * <p>rngSid を取得します。
     * @return rngSid
     */
    public int getRngSid() {
        return rngSid__;
    }
    /**
     * <p>rngSid をセットします。
     * @param rngSid rngSid
     */
    public void setRngSid(int rngSid) {
        rngSid__ = rngSid;
    }
    /**
     * <p>rngSelectTplSid を取得します。
     * @return rngSelectTplSid
     */
    public int getRngSelectTplSid() {
        return rngSelectTplSid__;
    }
    /**
     * <p>rngSelectTplSid をセットします。
     * @param rngSelectTplSid rngSelectTplSid
     */
    public void setRngSelectTplSid(int rngSelectTplSid) {
        rngSelectTplSid__ = rngSelectTplSid;
    }
    /**
     * <p>rngProcMode を取得します。
     * @return rngProcMode
     */
    public int getRngProcMode() {
        return rngProcMode__;
    }
    /**
     * <p>rngProcMode をセットします。
     * @param rngProcMode rngProcMode
     */
    public void setRngProcMode(int rngProcMode) {
        rngProcMode__ = rngProcMode;
    }
    /**
     * <p>rngTemplateMode を取得します。
     * @return rngTemplateMode
     */
    public int getRngTemplateMode() {
        return rngTemplateMode__;
    }
    /**
     * <p>rngTemplateMode をセットします。
     * @param rngTemplateMode rngTemplateMode
     */
    public void setRngTemplateMode(int rngTemplateMode) {
        rngTemplateMode__ = rngTemplateMode;
    }
    /**
     * <p>rngCmdMode を取得します。
     * @return rngCmdMode
     */
    public int getRngCmdMode() {
        return rngCmdMode__;
    }
    /**
     * <p>rngCmdMode をセットします。
     * @param rngCmdMode rngCmdMode
     */
    public void setRngCmdMode(int rngCmdMode) {
        rngCmdMode__ = rngCmdMode;
    }
    /**
     * <p>rngSearchGroup を取得します。
     * @return rngSearchGroup
     */
    public int getRngSearchGroup() {
        return rngSearchGroup__;
    }
    /**
     * <p>rngSearchGroup をセットします。
     * @param rngSearchGroup rngSearchGroup
     */
    public void setRngSearchGroup(int rngSearchGroup) {
        rngSearchGroup__ = rngSearchGroup;
    }
    /**
     * <p>rngSearchKeyword を取得します。
     * @return rngSearchKeyword
     */
    public String getRngSearchKeyword() {
        return rngSearchKeyword__;
    }
    /**
     * <p>rngSearchKeyword をセットします。
     * @param rngSearchKeyword rngSearchKeyword
     */
    public void setRngSearchKeyword(String rngSearchKeyword) {
        rngSearchKeyword__ = rngSearchKeyword;
    }
    /**
     * <p>rngSearchUser を取得します。
     * @return rngSearchUser
     */
    public int getRngSearchUser() {
        return rngSearchUser__;
    }
    /**
     * <p>rngSearchUser をセットします。
     * @param rngSearchUser rngSearchUser
     */
    public void setRngSearchUser(int rngSearchUser) {
        rngSearchUser__ = rngSearchUser;
    }
    /**
     * <p>rngApprMode を取得します。
     * @return rngApprMode
     */
    public int getRngApprMode() {
        return rngApprMode__;
    }
    /**
     * <p>rngApprMode をセットします。
     * @param rngApprMode rngApprMode
     */
    public void setRngApprMode(int rngApprMode) {
        rngApprMode__ = rngApprMode;
    }
    /**
     * <p>rngDspMode を取得します。
     * @return rngDspMode
     */
    public int getRngDspMode() {
        return rngDspMode__;
    }
    /**
     * <p>rngDspMode をセットします。
     * @param rngDspMode rngDspMode
     */
    public void setRngDspMode(int rngDspMode) {
        rngDspMode__ = rngDspMode;
    }
}