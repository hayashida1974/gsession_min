package jp.groupsession.v2.adr.adr320;

import jp.groupsession.v2.adr.adr030.Adr030ParamModel;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

/**
 * <br>[機  能] アドレス帳 管理者設定 権限設定画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr320ParamModel extends Adr030ParamModel {

    /** 初期化フラグ*/
    private int adr320initFlg__ = 0;
    /** 画面項目登録者制限権限有無 */
    private int adr320AacArestKbn__ = 0;
    /** アンケート作成可能ユーザリスト */
    private String[] adr320AdrArestList__ = null;

    /** グループSID */
    private String adr320GroupSid__ = null;

    /** アンケート作成可能ユーザ UI */
    private UserGroupSelector adr320AdrArestListUI__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Adr320ParamModel() {

    }
    /**
     * <p>adr320initFlg を取得します。
     * @return adr320initFlg
     */
    public int getAdr320initFlg() {
        return adr320initFlg__;
    }

    /**
     * <p>adr320initFlg をセットします。
     * @param adr320initFlg adr320initFlg
     */
    public void setAdr320initFlg(int adr320initFlg) {
        adr320initFlg__ = adr320initFlg;
    }

    /**
     * <p>adr320AacArestKbn を取得します。
     * @return adr320AacArestKbn
     */
    public int getAdr320AacArestKbn() {
        return adr320AacArestKbn__;
    }

    /**
     * <p>adr320AacArestKbn をセットします。
     * @param adr320AacArestKbn adr320AacArestKbn
     */
    public void setAdr320AacArestKbn(int adr320AacArestKbn) {
        adr320AacArestKbn__ = adr320AacArestKbn;
    }

    /**
     * <p>adr320AdrArestList を取得します。
     * @return adr320AdrArestList
     */
    public String[] getAdr320AdrArestList() {
        return adr320AdrArestList__;
    }

    /**
     * <p>adr320AdrArestList をセットします。
     * @param adr320AdrArestList adr320AdrArestList
     */
    public void setAdr320AdrArestList(String[] adr320AdrArestList) {
        adr320AdrArestList__ = adr320AdrArestList;
    }

    /**
     * <p>adr320GroupSid を取得します。
     * @return adr320GroupSid
     */
    public String getAdr320GroupSid() {
        return adr320GroupSid__;
    }

    /**
     * <p>adr320GroupSid をセットします。
     * @param adr320GroupSid adr320GroupSid
     */
    public void setAdr320GroupSid(String adr320GroupSid) {
        adr320GroupSid__ = adr320GroupSid;
    }

    /**
     * <p>adr320AdrArestListUI を取得します。
     * @return adr320AdrArestListUI
     */
    public UserGroupSelector getAdr320AdrArestListUI() {
        return adr320AdrArestListUI__;
    }

    /**
     * <p>adr320AdrArestListUI をセットします。
     * @param adr320AdrArestListUI adr320AdrArestListUI
     */
    public void setAdr320AdrArestListUI(UserGroupSelector adr320AdrArestListUI) {
        adr320AdrArestListUI__ = adr320AdrArestListUI;
    }
}