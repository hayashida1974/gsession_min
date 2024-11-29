package jp.groupsession.v2.sml.sml170;

import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml120.Sml120ParamModel;
import jp.groupsession.v2.sml.sml240.Sml240AccountModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] シショートメール 個人設定 メール転送設定画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml170ParamModel extends Sml120ParamModel {
    //メール転送設定

    /** メール転送設定のアカウントSID */
    private int sml170AccountSid__;
    /** メール転送設定のアカウント名 */
    private String sml170AccountName__;

    /** 設定アカウント区分 */
    private int sml170SelKbn__ = GSConstSmail.ACCOUNT_SEL;

    /** メール転送設定の利用有無 */
    private String sml170MailFw__;
    /** メール転送デフォルトメールアドレス */
    private String sml170MailDf__;
    /** デフォルトメールアドレス登録済みメールアドレスコンボの選択地 */
    private String sml170MailDfSelected__;
    /** メール転送後のショートメール開封状況 */
    private String sml170SmailOp__;

    /** メール転送設定 初期表示フラグ */
    private int sml170InitFlg__ = GSConstSmail.DSP_FIRST;

    /** 在席状況毎にメール振分有無区分 */
    private String sml170HuriwakeKbn__;
    /** メール転送在席メールアドレスTEXT */
    private String sml170Zmail1__;
    /** メール転送不在メールアドレスTEXT */
    private String sml170Zmail2__;
    /** メール転送その他メールアドレスTEXT */
    private String sml170Zmail3__;

    /** 登録済み在席メールアドレスコンボの選択値 */
    private String sml170Zmail1Selected__;
    /** 登録済み不在メールアドレスコンボの選択値 */
    private String sml170Zmail2Selected__;
    /** 登録済みその他メールアドレスコンボの選択値 */
    private String sml170Zmail3Selected__;

    /** 登録済みメールアドレスコンボ */
    private List < LabelValueBean > sml170MailList__ = null;

    //転送設定利用可能フラグ
    /** 転送設定管理者設定値 */
    private int sml170MailFwAdminConf__ = GSConstSmail.MAIL_FORWARD_NG;
    /** 在席管理有効フラグ */
    private int sml170ZaisekiPlugin__ = GSConst.PLUGIN_USE;

    /** 使用可能 全アカウント */
    private List<Sml240AccountModel> sml170AllUseAccount__ = null;

    /**
     * <p>sml170SmailOp を取得します。
     * @return sml170SmailOp
     */
    public String getSml170SmailOp() {
        return sml170SmailOp__;
    }
    /**
     * <p>sml170SmailOp をセットします。
     * @param sml170SmailOp sml170SmailOp
     */
    public void setSml170SmailOp(String sml170SmailOp) {
        sml170SmailOp__ = sml170SmailOp;
    }
    /**
     * <p>sml170MailFwAdminConf を取得します。
     * @return sml170MailFwAdminConf
     */
    public int getSml170MailFwAdminConf() {
        return sml170MailFwAdminConf__;
    }
    /**
     * <p>sml170MailFwAdminConf をセットします。
     * @param sml170MailFwAdminConf sml170MailFwAdminConf
     */
    public void setSml170MailFwAdminConf(int sml170MailFwAdminConf) {
        sml170MailFwAdminConf__ = sml170MailFwAdminConf;
    }
    /**
     * <p>sml170ZaisekiPlugin を取得します。
     * @return sml170ZaisekiPlugin
     */
    public int getSml170ZaisekiPlugin() {
        return sml170ZaisekiPlugin__;
    }
    /**
     * <p>sml170ZaisekiPlugin をセットします。
     * @param sml170ZaisekiPlugin sml170ZaisekiPlugin
     */
    public void setSml170ZaisekiPlugin(int sml170ZaisekiPlugin) {
        sml170ZaisekiPlugin__ = sml170ZaisekiPlugin;
    }
    /**
     * <p>sml170HuriwakeKbn を取得します。
     * @return sml170HuriwakeKbn
     */
    public String getSml170HuriwakeKbn() {
        return sml170HuriwakeKbn__;
    }
    /**
     * <p>sml170HuriwakeKbn をセットします。
     * @param sml170HuriwakeKbn sml170HuriwakeKbn
     */
    public void setSml170HuriwakeKbn(String sml170HuriwakeKbn) {
        sml170HuriwakeKbn__ = sml170HuriwakeKbn;
    }
    /**
     * <p>sml170MailDf を取得します。
     * @return sml170MailDf
     */
    public String getSml170MailDf() {
        return sml170MailDf__;
    }
    /**
     * <p>sml170MailDf をセットします。
     * @param sml170MailDf sml170MailDf
     */
    public void setSml170MailDf(String sml170MailDf) {
        sml170MailDf__ = sml170MailDf;
    }
    /**
     * <p>sml170MailDfSelected を取得します。
     * @return sml170MailDfSelected
     */
    public String getSml170MailDfSelected() {
        return sml170MailDfSelected__;
    }
    /**
     * <p>sml170MailDfSelected をセットします。
     * @param sml170MailDfSelected sml170MailDfSelected
     */
    public void setSml170MailDfSelected(String sml170MailDfSelected) {
        sml170MailDfSelected__ = sml170MailDfSelected;
    }
    /**
     * <p>sml170MailFw を取得します。
     * @return sml170MailFw
     */
    public String getSml170MailFw() {
        return sml170MailFw__;
    }
    /**
     * <p>sml170MailFw をセットします。
     * @param sml170MailFw sml170MailFw
     */
    public void setSml170MailFw(String sml170MailFw) {
        sml170MailFw__ = sml170MailFw;
    }
    /**
     * <p>sml170MailList を取得します。
     * @return sml170MailList
     */
    public List<LabelValueBean> getSml170MailList() {
        return sml170MailList__;
    }
    /**
     * <p>sml170MailList をセットします。
     * @param sml170MailList sml170MailList
     */
    public void setSml170MailList(List<LabelValueBean> sml170MailList) {
        sml170MailList__ = sml170MailList;
    }
    /**
     * <p>sml170Zmail1 を取得します。
     * @return sml170Zmail1
     */
    public String getSml170Zmail1() {
        return sml170Zmail1__;
    }
    /**
     * <p>sml170Zmail1 をセットします。
     * @param sml170Zmail1 sml170Zmail1
     */
    public void setSml170Zmail1(String sml170Zmail1) {
        sml170Zmail1__ = sml170Zmail1;
    }
    /**
     * <p>sml170Zmail1Selected を取得します。
     * @return sml170Zmail1Selected
     */
    public String getSml170Zmail1Selected() {
        return sml170Zmail1Selected__;
    }
    /**
     * <p>sml170Zmail1Selected をセットします。
     * @param sml170Zmail1Selected sml170Zmail1Selected
     */
    public void setSml170Zmail1Selected(String sml170Zmail1Selected) {
        sml170Zmail1Selected__ = sml170Zmail1Selected;
    }
    /**
     * <p>sml170Zmail2 を取得します。
     * @return sml170Zmail2
     */
    public String getSml170Zmail2() {
        return sml170Zmail2__;
    }
    /**
     * <p>sml170Zmail2 をセットします。
     * @param sml170Zmail2 sml170Zmail2
     */
    public void setSml170Zmail2(String sml170Zmail2) {
        sml170Zmail2__ = sml170Zmail2;
    }
    /**
     * <p>sml170Zmail2Selected を取得します。
     * @return sml170Zmail2Selected
     */
    public String getSml170Zmail2Selected() {
        return sml170Zmail2Selected__;
    }
    /**
     * <p>sml170Zmail2Selected をセットします。
     * @param sml170Zmail2Selected sml170Zmail2Selected
     */
    public void setSml170Zmail2Selected(String sml170Zmail2Selected) {
        sml170Zmail2Selected__ = sml170Zmail2Selected;
    }
    /**
     * <p>sml170Zmail3 を取得します。
     * @return sml170Zmail3
     */
    public String getSml170Zmail3() {
        return sml170Zmail3__;
    }
    /**
     * <p>sml170Zmail3 をセットします。
     * @param sml170Zmail3 sml170Zmail3
     */
    public void setSml170Zmail3(String sml170Zmail3) {
        sml170Zmail3__ = sml170Zmail3;
    }
    /**
     * <p>sml170Zmail3Selected を取得します。
     * @return sml170Zmail3Selected
     */
    public String getSml170Zmail3Selected() {
        return sml170Zmail3Selected__;
    }
    /**
     * <p>sml170Zmail3Selected をセットします。
     * @param sml170Zmail3Selected sml170Zmail3Selected
     */
    public void setSml170Zmail3Selected(String sml170Zmail3Selected) {
        sml170Zmail3Selected__ = sml170Zmail3Selected;
    }
    /**
     * <p>sml170AccountSid を取得します。
     * @return sml170AccountSid
     */
    public int getSml170AccountSid() {
        return sml170AccountSid__;
    }
    /**
     * <p>sml170AccountSid をセットします。
     * @param sml170AccountSid sml170AccountSid
     */
    public void setSml170AccountSid(int sml170AccountSid) {
        sml170AccountSid__ = sml170AccountSid;
    }
    /**
     * <p>sml170InitFlg を取得します。
     * @return sml170InitFlg
     */
    public int getSml170InitFlg() {
        return sml170InitFlg__;
    }
    /**
     * <p>sml170InitFlg をセットします。
     * @param sml170InitFlg sml170InitFlg
     */
    public void setSml170InitFlg(int sml170InitFlg) {
        sml170InitFlg__ = sml170InitFlg;
    }
    /**
     * <p>sml170AccountName を取得します。
     * @return sml170AccountName
     */
    public String getSml170AccountName() {
        return sml170AccountName__;
    }
    /**
     * <p>sml170AccountName をセットします。
     * @param sml170AccountName sml170AccountName
     */
    public void setSml170AccountName(String sml170AccountName) {
        sml170AccountName__ = sml170AccountName;
    }
    /**
     * <p>sml170SelKbn を取得します。
     * @return sml170SelKbn
     */
    public int getSml170SelKbn() {
        return sml170SelKbn__;
    }
    /**
     * <p>sml170SelKbn をセットします。
     * @param sml170SelKbn sml170SelKbn
     */
    public void setSml170SelKbn(int sml170SelKbn) {
        sml170SelKbn__ = sml170SelKbn;
    }
    /**
     * <p>sml170AllUseAccount を取得します。
     * @return sml170AllUseAccount
     */
    public List<Sml240AccountModel> getSml170AllUseAccount() {
        return sml170AllUseAccount__;
    }
    /**
     * <p>sml170AllUseAccount をセットします。
     * @param sml170AllUseAccount sml170AllUseAccount
     */
    public void setSml170AllUseAccount(List<Sml240AccountModel> sml170AllUseAccount) {
        sml170AllUseAccount__ = sml170AllUseAccount;
    }
}