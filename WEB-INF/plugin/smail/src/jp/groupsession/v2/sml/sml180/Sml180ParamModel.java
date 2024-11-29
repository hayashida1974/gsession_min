package jp.groupsession.v2.sml.sml180;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.sml.sml100.Sml100ParamModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール 管理者設定 メール転送一括設定画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml180ParamModel extends Sml100ParamModel {
    //対象
    /** 対象区分 全=0 指定=1*/
    private int sml180ObjKbn__ = 0;
    /** アドレス未登録ユーザパス区分 0:エラーとする 1:登録しない */
    private int sml180PassKbn__ = 0;
    /** グループ */
    private String sml180groupSid__ = null;
    /** 追加用メンバー(選択中) */
    private String[] sml180addUserSid__ = null;
    /** 現在選択中のメンバー(コンボ表示に使用する値) */
    private String[] sml180userSid__ = null;
    /** 現在選択中のメンバー(コンボで選択中) */
    private String[] sml180selectUserSid__ = null;
    /** グループコンボ */
    private ArrayList<LabelValueBean> sml180GpLabelList__ = null;
    /** 現在選択中のメンバーコンボ */
    private ArrayList<UsrLabelValueBean> sml180MbLabelList__ = null;
    /** 追加用メンバーコンボ */
    private ArrayList<UsrLabelValueBean> sml180AdLabelList__ = null;

    //メール転送設定
    /** メール転送設定の利用有無 */
    private String sml180MailFw__ = "0";
    /** メール転送デフォルトメールアドレス */
    private String sml180MailDf__;
    /** デフォルトメールアドレス登録済みメールアドレスコンボの選択値 */
    private String sml180MailDfSelected__ = "1";
    /** メール転送後のショートメール開封状況 */
    private String sml180SmailOp__ = "0";

    /** 在席状況毎にメール振分有無区分 */
    private String sml180HuriwakeKbn__ = "0";
    /** メール転送在席メールアドレスTEXT */
    private String sml180Zmail1__;
    /** メール転送不在メールアドレスTEXT */
    private String sml180Zmail2__;
    /** メール転送その他メールアドレスTEXT */
    private String sml180Zmail3__;

    /** 登録済み在席メールアドレスコンボの選択値 */
    private String sml180Zmail1Selected__ = "1";
    /** 登録済み不在メールアドレスコンボの選択値 */
    private String sml180Zmail2Selected__ = "1";
    /** 登録済みその他メールアドレスコンボの選択値 */
    private String sml180Zmail3Selected__ = "1";

    /** 登録済みメールアドレスコンボ */
    private List < LabelValueBean > sml180MailList__ = null;

    /** 在席管理有効フラグ */
    private int sml180ZaisekiPlugin__ = GSConst.PLUGIN_USE;

    /**
     * <p>sml180MailFw を取得します。
     * @return sml180MailFw
     */
    public String getSml180MailFw() {
        return sml180MailFw__;
    }
    /**
     * <p>sml180MailFw をセットします。
     * @param sml180MailFw sml180MailFw
     */
    public void setSml180MailFw(String sml180MailFw) {
        sml180MailFw__ = sml180MailFw;
    }
    /**
     * <p>sml180MailDf を取得します。
     * @return sml180MailDf
     */
    public String getSml180MailDf() {
        return sml180MailDf__;
    }
    /**
     * <p>sml180MailDf をセットします。
     * @param sml180MailDf sml180MailDf
     */
    public void setSml180MailDf(String sml180MailDf) {
        sml180MailDf__ = sml180MailDf;
    }
    /**
     * <p>sml180MailDfSelected を取得します。
     * @return sml180MailDfSelected
     */
    public String getSml180MailDfSelected() {
        return sml180MailDfSelected__;
    }
    /**
     * <p>sml180MailDfSelected をセットします。
     * @param sml180MailDfSelected sml180MailDfSelected
     */
    public void setSml180MailDfSelected(String sml180MailDfSelected) {
        sml180MailDfSelected__ = sml180MailDfSelected;
    }
    /**
     * <p>sml180SmailOp を取得します。
     * @return sml180SmailOp
     */
    public String getSml180SmailOp() {
        return sml180SmailOp__;
    }
    /**
     * <p>sml180SmailOp をセットします。
     * @param sml180SmailOp sml180SmailOp
     */
    public void setSml180SmailOp(String sml180SmailOp) {
        sml180SmailOp__ = sml180SmailOp;
    }
    /**
     * <p>sml180HuriwakeKbn を取得します。
     * @return sml180HuriwakeKbn
     */
    public String getSml180HuriwakeKbn() {
        return sml180HuriwakeKbn__;
    }
    /**
     * <p>sml180HuriwakeKbn をセットします。
     * @param sml180HuriwakeKbn sml180HuriwakeKbn
     */
    public void setSml180HuriwakeKbn(String sml180HuriwakeKbn) {
        sml180HuriwakeKbn__ = sml180HuriwakeKbn;
    }
    /**
     * <p>sml180Zmail1 を取得します。
     * @return sml180Zmail1
     */
    public String getSml180Zmail1() {
        return sml180Zmail1__;
    }
    /**
     * <p>sml180Zmail1 をセットします。
     * @param sml180Zmail1 sml180Zmail1
     */
    public void setSml180Zmail1(String sml180Zmail1) {
        sml180Zmail1__ = sml180Zmail1;
    }
    /**
     * <p>sml180Zmail2 を取得します。
     * @return sml180Zmail2
     */
    public String getSml180Zmail2() {
        return sml180Zmail2__;
    }
    /**
     * <p>sml180Zmail2 をセットします。
     * @param sml180Zmail2 sml180Zmail2
     */
    public void setSml180Zmail2(String sml180Zmail2) {
        sml180Zmail2__ = sml180Zmail2;
    }
    /**
     * <p>sml180Zmail3 を取得します。
     * @return sml180Zmail3
     */
    public String getSml180Zmail3() {
        return sml180Zmail3__;
    }
    /**
     * <p>sml180Zmail3 をセットします。
     * @param sml180Zmail3 sml180Zmail3
     */
    public void setSml180Zmail3(String sml180Zmail3) {
        sml180Zmail3__ = sml180Zmail3;
    }
    /**
     * <p>sml180Zmail1Selected を取得します。
     * @return sml180Zmail1Selected
     */
    public String getSml180Zmail1Selected() {
        return sml180Zmail1Selected__;
    }
    /**
     * <p>sml180Zmail1Selected をセットします。
     * @param sml180Zmail1Selected sml180Zmail1Selected
     */
    public void setSml180Zmail1Selected(String sml180Zmail1Selected) {
        sml180Zmail1Selected__ = sml180Zmail1Selected;
    }
    /**
     * <p>sml180Zmail2Selected を取得します。
     * @return sml180Zmail2Selected
     */
    public String getSml180Zmail2Selected() {
        return sml180Zmail2Selected__;
    }
    /**
     * <p>sml180Zmail2Selected をセットします。
     * @param sml180Zmail2Selected sml180Zmail2Selected
     */
    public void setSml180Zmail2Selected(String sml180Zmail2Selected) {
        sml180Zmail2Selected__ = sml180Zmail2Selected;
    }
    /**
     * <p>sml180Zmail3Selected を取得します。
     * @return sml180Zmail3Selected
     */
    public String getSml180Zmail3Selected() {
        return sml180Zmail3Selected__;
    }
    /**
     * <p>sml180Zmail3Selected をセットします。
     * @param sml180Zmail3Selected sml180Zmail3Selected
     */
    public void setSml180Zmail3Selected(String sml180Zmail3Selected) {
        sml180Zmail3Selected__ = sml180Zmail3Selected;
    }
    /**
     * <p>sml180MailList を取得します。
     * @return sml180MailList
     */
    public List<LabelValueBean> getSml180MailList() {
        return sml180MailList__;
    }
    /**
     * <p>sml180MailList をセットします。
     * @param sml180MailList sml180MailList
     */
    public void setSml180MailList(List<LabelValueBean> sml180MailList) {
        sml180MailList__ = sml180MailList;
    }
    /**
     * <p>sml180ZaisekiPlugin を取得します。
     * @return sml180ZaisekiPlugin
     */
    public int getSml180ZaisekiPlugin() {
        return sml180ZaisekiPlugin__;
    }
    /**
     * <p>sml180ZaisekiPlugin をセットします。
     * @param sml180ZaisekiPlugin sml180ZaisekiPlugin
     */
    public void setSml180ZaisekiPlugin(int sml180ZaisekiPlugin) {
        sml180ZaisekiPlugin__ = sml180ZaisekiPlugin;
    }
    /**
     * <p>sml180ObjKbn を取得します。
     * @return sml180ObjKbn
     */
    public int getSml180ObjKbn() {
        return sml180ObjKbn__;
    }
    /**
     * <p>sml180ObjKbn をセットします。
     * @param sml180ObjKbn sml180ObjKbn
     */
    public void setSml180ObjKbn(int sml180ObjKbn) {
        sml180ObjKbn__ = sml180ObjKbn;
    }
    /**
     * <p>sml180groupSid を取得します。
     * @return sml180groupSid
     */
    public String getSml180groupSid() {
        return sml180groupSid__;
    }
    /**
     * <p>sml180groupSid をセットします。
     * @param sml180groupSid sml180groupSid
     */
    public void setSml180groupSid(String sml180groupSid) {
        sml180groupSid__ = sml180groupSid;
    }
    /**
     * <p>sml180addUserSid を取得します。
     * @return sml180addUserSid
     */
    public String[] getSml180addUserSid() {
        return sml180addUserSid__;
    }
    /**
     * <p>sml180addUserSid をセットします。
     * @param sml180addUserSid sml180addUserSid
     */
    public void setSml180addUserSid(String[] sml180addUserSid) {
        sml180addUserSid__ = sml180addUserSid;
    }
    /**
     * <p>sml180userSid を取得します。
     * @return sml180userSid
     */
    public String[] getSml180userSid() {
        return sml180userSid__;
    }
    /**
     * <p>sml180userSid をセットします。
     * @param sml180userSid sml180userSid
     */
    public void setSml180userSid(String[] sml180userSid) {
        sml180userSid__ = sml180userSid;
    }
    /**
     * <p>sml180selectUserSid を取得します。
     * @return sml180selectUserSid
     */
    public String[] getSml180selectUserSid() {
        return sml180selectUserSid__;
    }
    /**
     * <p>sml180selectUserSid をセットします。
     * @param sml180selectUserSid sml180selectUserSid
     */
    public void setSml180selectUserSid(String[] sml180selectUserSid) {
        sml180selectUserSid__ = sml180selectUserSid;
    }
    /**
     * <p>sml180GpLabelList を取得します。
     * @return sml180GpLabelList
     */
    public ArrayList<LabelValueBean> getSml180GpLabelList() {
        return sml180GpLabelList__;
    }
    /**
     * <p>sml180GpLabelList をセットします。
     * @param sml180GpLabelList sml180GpLabelList
     */
    public void setSml180GpLabelList(ArrayList<LabelValueBean> sml180GpLabelList) {
        sml180GpLabelList__ = sml180GpLabelList;
    }
    /**
     * <p>sml180MbLabelList を取得します。
     * @return sml180MbLabelList
     */
    public ArrayList<UsrLabelValueBean> getSml180MbLabelList() {
        return sml180MbLabelList__;
    }
    /**
     * <p>sml180MbLabelList をセットします。
     * @param sml180MbLabelList sml180MbLabelList
     */
    public void setSml180MbLabelList(ArrayList<UsrLabelValueBean> sml180MbLabelList) {
        sml180MbLabelList__ = sml180MbLabelList;
    }
    /**
     * <p>sml180AdLabelList を取得します。
     * @return sml180AdLabelList
     */
    public ArrayList<UsrLabelValueBean> getSml180AdLabelList() {
        return sml180AdLabelList__;
    }
    /**
     * <p>sml180AdLabelList をセットします。
     * @param sml180AdLabelList sml180AdLabelList
     */
    public void setSml180AdLabelList(ArrayList<UsrLabelValueBean> sml180AdLabelList) {
        sml180AdLabelList__ = sml180AdLabelList;
    }
    /**
     * <p>sml180PassKbn を取得します。
     * @return sml180PassKbn
     */
    public int getSml180PassKbn() {
        return sml180PassKbn__;
    }
    /**
     * <p>sml180PassKbn をセットします。
     * @param sml180PassKbn sml180PassKbn
     */
    public void setSml180PassKbn(int sml180PassKbn) {
        sml180PassKbn__ = sml180PassKbn;
    }
}