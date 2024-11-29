package jp.groupsession.v2.sml.sml120;

import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.sml010.Sml010ParamModel;

/**
 * <br>[機  能] ショートメール 個人設定メニュー画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml120ParamModel extends Sml010ParamModel {
    /** ショートメール表示設定利用可能フラグ*/
    private int sml120DispAdminConf__ = GSConstSmail.DISP_CONF_USER;
    /** 転送設定利用可能フラグ */
    private int sml120MailFwAdminConf__ = GSConstSmail.MAIL_FORWARD_NG;
    /** ショートメール自動削除フラグ*/
    private int sml120MailDelAdminConf__ = GSConstSmail.SML_DEL_KBN_ADM_SETTING;

    /**
     * <p>sml120DispAdminConf を取得します。
     * @return sml120DispAdminConf
     */
    public int getSml120DispAdminConf() {
        return sml120DispAdminConf__;
    }
    /**
     * <p>sml120DispAdminConf をセットします。
     * @param sml120DispAdminConf sml120DispAdminConf
     */
    public void setSml120DispAdminConf(int sml120DispAdminConf) {
        sml120DispAdminConf__ = sml120DispAdminConf;
    }
    /**
     * <p>sml120MailFwAdminConf を取得します。
     * @return sml120MailFwAdminConf
     */
    public int getSml120MailFwAdminConf() {
        return sml120MailFwAdminConf__;
    }
    /**
     * <p>sml120MailFwAdminConf をセットします。
     * @param sml120MailFwAdminConf sml120MailFwAdminConf
     */
    public void setSml120MailFwAdminConf(int sml120MailFwAdminConf) {
        sml120MailFwAdminConf__ = sml120MailFwAdminConf;
    }
    /**
     * <p>sml120MailDelAdminConf を取得します。
     * @return sml120MailDelAdminConf
     */
    public int getSml120MailDelAdminConf() {
        return sml120MailDelAdminConf__;
    }
    /**
     * <p>sml120MailDelAdminConf をセットします。
     * @param sml120MailDelAdminConf sml120MailDelAdminConf
     */
    public void setSml120MailDelAdminConf(int sml120MailDelAdminConf) {
        sml120MailDelAdminConf__ = sml120MailDelAdminConf;
    }
}