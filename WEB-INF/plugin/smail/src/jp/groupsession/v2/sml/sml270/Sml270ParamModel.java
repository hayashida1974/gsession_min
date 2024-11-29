package jp.groupsession.v2.sml.sml270;

import java.util.List;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml120.Sml120ParamModel;

/**
 * <br>[機  能] ショートメール アカウントの管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml270ParamModel extends Sml120ParamModel {
    /** アカウントリスト */
    private List<Sml270AccountDataDspModel> accountList__ = null;
    /** チェックされている並び順 */
    private String sml270sortAccount__ = null;
    /** 並び替え対象のラベル */
    private String[] sml270sortLabel__ = null;
    /** アカウント作成判定 */
    private int sml270MakeAcntHnt__ = GSConstSmail.ACCOUNT_ADD_OK;

    /**
     * <p>accountList を取得します。
     * @return accountList
     */
    public List<Sml270AccountDataDspModel> getAccountList() {
        return accountList__;
    }

    /**
     * <p>accountList をセットします。
     * @param accountList accountList
     */
    public void setAccountList(List<Sml270AccountDataDspModel> accountList) {
        accountList__ = accountList;
    }

    /**
     * <p>sml270sortAccount を取得します。
     * @return sml270sortAccount
     */
    public String getSml270sortAccount() {
        return sml270sortAccount__;
    }

    /**
     * <p>sml270sortAccount をセットします。
     * @param sml270sortAccount sml270sortAccount
     */
    public void setSml270sortAccount(String sml270sortAccount) {
        sml270sortAccount__ = sml270sortAccount;
    }

    /**
     * <p>sml270sortLabel を取得します。
     * @return sml270sortLabel
     */
    public String[] getSml270sortLabel() {
        return sml270sortLabel__;
    }

    /**
     * <p>sml270sortLabel をセットします。
     * @param sml270sortLabel sml270sortLabel
     */
    public void setSml270sortLabel(String[] sml270sortLabel) {
        sml270sortLabel__ = sml270sortLabel;
    }

    /**
     * @return sml270MakeAcntHnt
     */
    public int getSml270MakeAcntHnt() {
        return sml270MakeAcntHnt__;
    }

    /**
     * @param sml270MakeAcntHnt 設定する sml270MakeAcntHnt
     */
    public void setSml270MakeAcntHnt(int sml270MakeAcntHnt) {
        sml270MakeAcntHnt__ = sml270MakeAcntHnt;
    }


}