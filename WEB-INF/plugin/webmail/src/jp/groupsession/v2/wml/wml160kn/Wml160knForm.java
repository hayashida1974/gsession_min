package jp.groupsession.v2.wml.wml160kn;

import java.util.List;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.wml.wml160.Wml160Form;

/**
 * <br>[機  能] WEBメール アカウントインポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml160knForm extends Wml160Form {

    /** 取込ファイル名 */
    private String wml160knFileName__ = null;
    /** 使用者 代理人リスト */
    private List<Wml160knUseUsrModel> wml160knUseUserList__ = null;

    /**
     * <p>wml160knUseUserList を取得します。
     * @return wml160knUseUserList
     */
    public List<Wml160knUseUsrModel> getWml160knUseUserList() {
        return wml160knUseUserList__;
    }
    /**
     * <p>wml160knUseUserList をセットします。
     * @param wml160knUseUserList wml160knUseUserList
     */
    public void setWml160knUseUserList(List<Wml160knUseUsrModel> wml160knUseUserList) {
        wml160knUseUserList__ = wml160knUseUserList;
    }
    /**
     * <p>wml160knFileName を取得します。
     * @return wml160knFileName
     */
    public String getWml160knFileName() {
        return wml160knFileName__;
    }
    /**
     * <p>wml160knFileName をセットします。
     * @param wml160knFileName wml160knFileName
     */
    public void setWml160knFileName(String wml160knFileName) {
        wml160knFileName__ = wml160knFileName;
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);
        msgForm.addHiddenParam("wml030keyword", getWml030keyword());
        msgForm.addHiddenParam("wml030group", getWml030group());
        msgForm.addHiddenParam("wml030user", getWml030user());
        msgForm.addHiddenParam("wml030ninsyo", getWml030ninsyo());
        msgForm.addHiddenParam("wml030ninsyoStatus", getWml030ninsyoStatus());
        msgForm.addHiddenParam("wml030pageTop", getWml030pageTop());
        msgForm.addHiddenParam("wml030pageBottom", getWml030pageBottom());
        msgForm.addHiddenParam("wml030svKeyword", getWml030svKeyword());
        msgForm.addHiddenParam("wml030svGroup", getWml030svGroup());
        msgForm.addHiddenParam("wml030svUser", getWml030svUser());
        msgForm.addHiddenParam("wml030svNinsyo", getWml030svNinsyo());
        msgForm.addHiddenParam("wml030svNinsyoStatus", getWml030svNinsyoStatus());
        msgForm.addHiddenParam("wml030sortKey", getWml030sortKey());
        msgForm.addHiddenParam("wml030order", getWml030order());
        msgForm.addHiddenParam("wml030searchFlg", getWml030searchFlg());
    }
}
