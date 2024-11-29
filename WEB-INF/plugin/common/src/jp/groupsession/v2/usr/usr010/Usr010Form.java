package jp.groupsession.v2.usr.usr010;

import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] メイン 管理者設定 グループマネージャー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr010Form extends AbstractGsForm {

    /** グループSID */
    private int usr010grpSid__ = -1;
    /** グループ処理モード */
    private String usr010grpmode__ = null;
    /** CSV出力フラグ */
    private int grpCsvOut__ = 0;

    /**
     * @return usr010grpmode を戻します。
     */
    public String getUsr010grpmode() {
        return usr010grpmode__;
    }

    /**
     * @param usr010grpmode 設定する usr010grpmode。
     */
    public void setUsr010grpmode(String usr010grpmode) {
        usr010grpmode__ = usr010grpmode;
    }

    /**
     * @return usr010grpSid__ を戻します。
     */
    public int getUsr010grpSid() {
        return usr010grpSid__;
    }

    /**
     * @param usr010grpSid 設定する usr010grpSid__。
     */
    public void setUsr010grpSid(int usr010grpSid) {
        usr010grpSid__ = usr010grpSid;
    }

    /**
     * <p>grpCsvOut を取得します。
     * @return grpCsvOut
     */
    public int getGrpCsvOut() {
        return grpCsvOut__;
    }

    /**
     * <p>grpCsvOut をセットします。
     * @param grpCsvOut grpCsvOut
     */
    public void setGrpCsvOut(int grpCsvOut) {
        grpCsvOut__ = grpCsvOut;
    }

}
