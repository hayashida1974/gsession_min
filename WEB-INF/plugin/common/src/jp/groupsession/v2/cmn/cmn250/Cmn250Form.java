package jp.groupsession.v2.cmn.cmn250;

import java.util.List;

import jp.co.sjts.util.struts.AbstractForm;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;

/**
 * <br>[機  能] Webメール OAuth認証情報管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn250Form extends AbstractForm {
    /** 認証情報編集モード */
    private int cmn250CmdMode__ = GSConstCommon.MODE_ADD;
    /** 編集対象認証SID */
    private int cmnAuthSid__ = 0;
    /** 認証情報リスト */
    private List<Cmn250DisplayModel> authList__ = null;

    /** 戻り先画面 */
    private int cmn250backScreen__ = 0;

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        msgForm.addHiddenParam("cmn250backScreen", cmn250backScreen__);
    }

    /**
     * <p>cmn250CmdMode を取得します。
     * @return cmn250CmdMode
     * @see jp.groupsession.v2.cmn.cmn250.Cmn250Form#cmn250CmdMode__
     */
    public int getCmn250CmdMode() {
        return cmn250CmdMode__;
    }

    /**
     * <p>cmn250CmdMode をセットします。
     * @param cmn250CmdMode cmn250CmdMode
     * @see jp.groupsession.v2.cmn.cmn250.Cmn250Form#cmn250CmdMode__
     */
    public void setCmn250CmdMode(int cmn250CmdMode) {
        cmn250CmdMode__ = cmn250CmdMode;
    }

    /**
     * <p>cmnAuthSid を取得します。
     * @return cmnAuthSid
     * @see jp.groupsession.v2.cmn.cmn250.Cmn250Form#cmnAuthSid__
     */
    public int getCmnAuthSid() {
        return cmnAuthSid__;
    }

    /**
     * <p>cmnAuthSid をセットします。
     * @param cmnAuthSid cmnAuthSid
     * @see jp.groupsession.v2.cmn.cmn250.Cmn250Form#cmnAuthSid__
     */
    public void setCmnAuthSid(int cmnAuthSid) {
        cmnAuthSid__ = cmnAuthSid;
    }

    /**
     * <p>authList を取得します。
     * @return authList
     * @see jp.groupsession.v2.cmn.cmn250.Cmn250Form#authList__
     */
    public List<Cmn250DisplayModel> getAuthList() {
        return authList__;
    }

    /**
     * <p>authList をセットします。
     * @param authList authList
     * @see jp.groupsession.v2.cmn.cmn250.Cmn250Form#authList__
     */
    public void setAuthList(List<Cmn250DisplayModel> authList) {
        authList__ = authList;
    }

    /**
     * <p>cmn250backScreen を取得します。
     * @return cmn250backScreen
     * @see jp.groupsession.v2.cmn.cmn250.Cmn250Form#cmn250backScreen__
     */
    public int getCmn250backScreen() {
        return cmn250backScreen__;
    }

    /**
     * <p>cmn250backScreen をセットします。
     * @param cmn250backScreen cmn250backScreen
     * @see jp.groupsession.v2.cmn.cmn250.Cmn250Form#cmn250backScreen__
     */
    public void setCmn250backScreen(int cmn250backScreen) {
        cmn250backScreen__ = cmn250backScreen;
    }
}