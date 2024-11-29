package jp.groupsession.v2.usr.usr033kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.usr033.Usr033Form;

/**
 * <br>[機  能] メイン 管理者設定 ユーザ一括削除確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr033knForm extends Usr033Form {

    /** 取込みファイル名 */
    private String usr033knFileName__ = null;
    /** 取込み予定ユーザ情報 */
    private List<CmnUsrmInfModel> usr033knImpList__ = null;
    /** 取り込みユーザ件数*/
    private int usr033knCount__ = 0;
    /** 自ユーザ削除フラグ */
    private boolean usr033knDelSelfFlg__ = false;

    /**
     * <p>usr033knFileName を取得します。
     * @return usr033knFileName
     */
    public String getUsr033knFileName() {
        return usr033knFileName__;
    }

    /**
     * <p>usr033knFileName をセットします。
     * @param usr033knFileName usr033knFileName
     */
    public void setUsr033knFileName(String usr033knFileName) {
        usr033knFileName__ = usr033knFileName;
    }

    /**
     * <p>usr033knImpList を取得します。
     * @return usr033knImpList
     */
    public List<CmnUsrmInfModel> getUsr033knImpList() {
        return usr033knImpList__;
    }

    /**
     * <p>usr033knImpList をセットします。
     * @param usr033knImpList usr033knImpList
     */
    public void setUsr033knImpList(List<CmnUsrmInfModel> usr033knImpList) {
        usr033knImpList__ = usr033knImpList;
    }

    /**
     * <p>usr033knCount を取得します。
     * @return usr033knCount
     * @see jp.groupsession.v2.usr.usr033kn.Usr033knForm#usr033knCount__
     */
    public int getUsr033knCount() {
        return usr033knCount__;
    }

    /**
     * <p>usr033knCount をセットします。
     * @param usr033knCount usr033knCount
     * @see jp.groupsession.v2.usr.usr033kn.Usr033knForm#usr033knCount__
     */
    public void setUsr033knCount(int usr033knCount) {
        usr033knCount__ = usr033knCount;
    }

    /**
     * <p>usr033knDelSelfFlg を取得します。
     * @return usr033knDelSelfFlg
     * @see jp.groupsession.v2.usr.usr033kn.Usr033knForm#usr033knDelSelfFlg__
     */
    public boolean isUsr033knDelSelfFlg() {
        return usr033knDelSelfFlg__;
    }

    /**
     * <p>usr033knDelSelfFlg をセットします。
     * @param usr033knDelSelfFlg usr033knDelSelfFlg
     * @see jp.groupsession.v2.usr.usr033kn.Usr033knForm#usr033knDelSelfFlg__
     */
    public void setUsr033knDelSelfFlg(boolean usr033knDelSelfFlg) {
        usr033knDelSelfFlg__ = usr033knDelSelfFlg;
    }
}
