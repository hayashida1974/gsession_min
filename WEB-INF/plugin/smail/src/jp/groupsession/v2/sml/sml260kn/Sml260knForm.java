package jp.groupsession.v2.sml.sml260kn;

import java.util.List;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.sml.sml260.Sml260Form;

/**
 * <br>[機  能] ショートメール アカウントインポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml260knForm extends Sml260Form {

    /** 取込ファイル名 */
    private String sml260knFileName__ = null;
    /** 使用者リスト */
    private List<Sml260knUseUsrModel> sml260knUseUserList__ = null;

    /**
     * <p>sml260knUseUserList を取得します。
     * @return sml260knUseUserList
     */
    public List<Sml260knUseUsrModel> getSml260knUseUserList() {
        return sml260knUseUserList__;
    }
    /**
     * <p>sml260knUseUserList をセットします。
     * @param sml260knUseUserList sml260knUseUserList
     */
    public void setSml260knUseUserList(List<Sml260knUseUsrModel> sml260knUseUserList) {
        sml260knUseUserList__ = sml260knUseUserList;
    }
    /**
     * <p>sml260knFileName を取得します。
     * @return sml260knFileName
     */
    public String getSml260knFileName() {
        return sml260knFileName__;
    }
    /**
     * <p>sml260knFileName をセットします。
     * @param sml260knFileName sml260knFileName
     */
    public void setSml260knFileName(String sml260knFileName) {
        sml260knFileName__ = sml260knFileName;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("sml240keyword", getSml240keyword());
        msgForm.addHiddenParam("sml240group", getSml240group());
        msgForm.addHiddenParam("sml240user", getSml240user());
        msgForm.addHiddenParam("sml240pageTop", getSml240pageTop());
        msgForm.addHiddenParam("sml240pageBottom", getSml240pageBottom());
        msgForm.addHiddenParam("sml240svKeyword", getSml240svKeyword());
        msgForm.addHiddenParam("sml240svGroup", getSml240svGroup());
        msgForm.addHiddenParam("sml240svUser", getSml240svUser());
        msgForm.addHiddenParam("sml240sortKey", getSml240sortKey());
        msgForm.addHiddenParam("sml240order", getSml240order());
        msgForm.addHiddenParam("sml240searchFlg", getSml240searchFlg());
    }
}
