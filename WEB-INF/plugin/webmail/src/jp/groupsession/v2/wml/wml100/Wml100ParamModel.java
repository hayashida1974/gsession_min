package jp.groupsession.v2.wml.wml100;

import java.util.List;

import jp.groupsession.v2.wml.wml090.Wml090ParamModel;

/**
 * <br>[機  能] WEBメール アカウントの管理画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml100ParamModel extends Wml090ParamModel {
    /** アカウントリスト */
    private List<Wml100AccountDataDspModel> accountList__ = null;
    /** チェックされている並び順 */
    private String wml100sortAccount__ = null;
    /** 並び替え対象のラベル */
    private String[] wml100sortLabel__ = null;
    /** 非管理者のアカウント登録判定 */
    private int wml100MakeAcntHnt__ = -1;
    /** メールテンプレート区分 */
    private int wmlMailTemplateKbn__ = 0;

    /**
     * <p>accountList を取得します。
     * @return accountList
     */
    public List<Wml100AccountDataDspModel> getAccountList() {
        return accountList__;
    }

    /**
     * <p>accountList をセットします。
     * @param accountList accountList
     */
    public void setAccountList(List<Wml100AccountDataDspModel> accountList) {
        accountList__ = accountList;
    }

    /**
     * <p>wml100sortAccount を取得します。
     * @return wml100sortAccount
     */
    public String getWml100sortAccount() {
        return wml100sortAccount__;
    }

    /**
     * <p>wml100sortAccount をセットします。
     * @param wml100sortAccount wml100sortAccount
     */
    public void setWml100sortAccount(String wml100sortAccount) {
        wml100sortAccount__ = wml100sortAccount;
    }

    /**
     * <p>wml100sortLabel を取得します。
     * @return wml100sortLabel
     */
    public String[] getWml100sortLabel() {
        return wml100sortLabel__;
    }

    /**
     * <p>wml100sortLabel をセットします。
     * @param wml100sortLabel wml100sortLabel
     */
    public void setWml100sortLabel(String[] wml100sortLabel) {
        wml100sortLabel__ = wml100sortLabel;
    }

    /**
     * @return wml100MakeAcntHnt
     */
    public int getWml100MakeAcntHnt() {
        return wml100MakeAcntHnt__;
    }

    /**
     * @param wml100MakeAcntHnt 設定する wml100MakeAcntHnt
     */
    public void setWml100MakeAcntHnt(int wml100MakeAcntHnt) {
        wml100MakeAcntHnt__ = wml100MakeAcntHnt;
    }

    /**
     * <p>wmlMailTemplateKbn を取得します。
     * @return wmlMailTemplateKbn
     */
    public int getWmlMailTemplateKbn() {
        return wmlMailTemplateKbn__;
    }

    /**
     * <p>wmlMailTemplateKbn をセットします。
     * @param wmlMailTemplateKbn wmlMailTemplateKbn
     */
    public void setWmlMailTemplateKbn(int wmlMailTemplateKbn) {
        wmlMailTemplateKbn__ = wmlMailTemplateKbn;
    }
}