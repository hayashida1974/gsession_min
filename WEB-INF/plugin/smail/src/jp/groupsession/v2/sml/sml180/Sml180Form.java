package jp.groupsession.v2.sml.sml180;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlAdminDao;
import jp.groupsession.v2.sml.dao.SmlFwlmtDao;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.model.SmlFwlmtModel;
import jp.groupsession.v2.sml.sml100.Sml100Form;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール 管理者設定 メール転送一括設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml180Form extends Sml100Form {

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
    private ArrayList<LabelValueBean> sml180MbLabelList__ = null;
    /** 追加用メンバーコンボ */
    private ArrayList<LabelValueBean> sml180AdLabelList__ = null;

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
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(
            ActionMapping map,
            HttpServletRequest req,
            Connection con) throws SQLException {

        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;

        GsMessage gsMsg = new GsMessage();
        String taisyou = gsMsg.getMessage(req, "sml.155");
        String toMailAdr = gsMsg.getMessage(req, "sml.81");
        String zskMailAdr = gsMsg.getMessage(req, "sml.44");
        String fziMailAdr = gsMsg.getMessage(req, "sml.89");
        String otherMailAdr = gsMsg.getMessage(req, "sml.12");

        //対象
        if (sml180ObjKbn__ == 1) {
            if (sml180userSid__ == null || sml180userSid__.length < 1) {
                // 未選択チェック
                msg = new ActionMessage("error.select.required.text", taisyou);
                StrutsUtil.addMessage(errors, msg, "sml180userSid"
                        + "error.select.required.text");
            }
        }

        //転送先メールアドレスのチェック
        if (sml180MailFw__.equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))) {

            //基本メール転送先アドレス
            if (sml180MailDfSelected__.equals("0")) {

                String dfMail = sml180MailDf__;
                if (StringUtil.isNullZeroString(dfMail)) {
                    // 未入力チェック
                    msg = new ActionMessage("error.input.required.text", toMailAdr);
                    StrutsUtil.addMessage(errors, msg, "sml180MailDf"
                            + "error.input.required.text");
                } else {
                    errors = validateMail(
                            con,
                            errors,
                            dfMail,
                            "sml180MailDf",
                            toMailAdr);
                }
            }
            //在席状況別の転送先アドレス
            if (sml180HuriwakeKbn__.equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))) {

                //在席時メール転送先アドレス
                if (sml180Zmail1Selected__.equals("0")) {
                    String zMail1 = sml180Zmail1__;
                    if (StringUtil.isNullZeroString(zMail1)) {
                        // 未入力チェック
                        msg = new ActionMessage("error.input.required.text", zskMailAdr);
                        StrutsUtil.addMessage(errors, msg, "sml180Zmail1"
                                + "error.input.required.text");
                    } else {
                        errors = validateMail(
                                con,
                                errors,
                                zMail1,
                                "sml180Zmail1",
                                zskMailAdr);
                    }
                }
                //不在時メール転送先アドレス
                if (sml180Zmail2Selected__.equals("0")) {
                    String zMail2 = sml180Zmail2__;
                    if (StringUtil.isNullZeroString(zMail2)) {
                        // 未入力チェック
                        msg = new ActionMessage("error.input.required.text", fziMailAdr);
                        StrutsUtil.addMessage(errors, msg, "sml180Zmail2"
                                + "error.input.required.text");
                    } else {
                        errors = validateMail(
                                con,
                                errors,
                                zMail2,
                                "sml180Zmail2",
                                fziMailAdr);
                    }

                }
                //その他メール転送先アドレス
                if (sml180Zmail3Selected__.equals("0")) {
                    String zMail3 = sml180Zmail3__;
                    if (StringUtil.isNullZeroString(zMail3)) {
                        // 未入力チェック
                        msg = new ActionMessage("error.input.required.text", otherMailAdr);
                        StrutsUtil.addMessage(errors, msg, "sml180Zmail3"
                                + "error.input.required.text");
                    } else {
                        errors = validateMail(
                                con,
                                errors,
                                zMail3,
                                "sml180Zmail3",
                                otherMailAdr);
                    }
                }
            } else if (sml180HuriwakeKbn__.equals(
                    String.valueOf(GSConstSmail.MAIL_FORWARD_FUZAI_OK))) {
              //不在時メール転送先アドレス
                if (sml180Zmail2Selected__.equals("0")) {
                    String zMail2 = sml180Zmail2__;
                    if (StringUtil.isNullZeroString(zMail2)) {
                        // 未入力チェック
                        msg = new ActionMessage("error.input.required.text", fziMailAdr);
                        StrutsUtil.addMessage(errors, msg, "sml180Zmail2"
                                + "error.input.required.text");
                    } else {
                        errors = validateMail(
                                con,
                                errors,
                                zMail2,
                                "sml180Zmail2",
                                fziMailAdr);
                    }

                }
            }

            if (sml180PassKbn__ == 1
                  && (sml180MailFw__.equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))
                  || sml180MailFw__.equals(String.valueOf(GSConstSmail.MAIL_FORWARD_FUZAI_OK)))) {
                //有効データ件数チェック
                __validateCheckUsrCount(con, errors, req);
            }

            if (sml180PassKbn__ != 1
                  && (sml180MailFw__.equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))
                  || sml180MailFw__.equals(String.valueOf(GSConstSmail.MAIL_FORWARD_FUZAI_OK)))) {
                //各ユーザのメールアドレス登録チェック
                errors = __validateCheckUsrAddress(con, errors, req);

            }
        }

        return errors;
    }

    /**
     * <p>メールアドレスの入力チェックを行う
     * @param con コネクション
     * @param errors ActionErrors
     * @param mail メールアドレス
     * @param eprefix メッセージサフィックス
     * @param text 項目名
     * @return ActionErrors
     * @throws SQLException SQL実行時例外
     */
    public static ActionErrors validateMail(Connection con, ActionErrors errors,
            String mail, String eprefix, String text)
    throws SQLException {
        ActionMessage msg = null;

        if (!StringUtil.isNullZeroString(mail)) {
            if (mail.length() > GSConstUser.MAX_LENGTH_MAIL) {
                // MAX桁チェック
                msg = new ActionMessage("error.input.length.text",
                        text,
                        GSConstUser.MAX_LENGTH_MAIL);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.length.text");

            } else if (!GSValidateUtil.isMailFormat(mail)) {
                //メールフォーマットチェック
                msg = new ActionMessage("error.input.format.text", text);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.format.text");

            } else {
                //転送先制限チェック
                SmlAdminDao admDao = new SmlAdminDao(con);
                SmlAdminModel admModel = admDao.select();

                if (String.valueOf(admModel.getSmaFwlmtKbn()).equals(
                        GSConstSmail.MAIL_FORWARD_LIMIT)
                        && admModel.getSmaMailfw() == GSConstSmail.MAIL_FORWARD_OK) {

                    SmlFwlmtDao lmtDao = new SmlFwlmtDao(con);
                    List<SmlFwlmtModel> fwLmtTxtList = lmtDao.select();

                    boolean errorFlg = true;
                    for (SmlFwlmtModel model : fwLmtTxtList) {
                        if (mail.indexOf(model.getSflText()) != -1) {
                            errorFlg = false;
                            break;
                        }
                    }

                    if (errorFlg) {
                        msg = new ActionMessage("error.input.add.limit", text);
                        StrutsUtil.addMessage(errors, msg, eprefix
                                + "error.input.add.limit");
                    }

                }
            }

        }
        return errors;
    }

    /**
     * <br>[機  能] 各ユーザのメールアドレスチェックを行う
     * <br>[解  説]
     * <br>[備  考]

     * @param con コネクション
     * @param errors アクションエラー
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __validateCheckUsrAddress(
            Connection con,
            ActionErrors errors,
            HttpServletRequest req) throws SQLException {

        ActionMessage msg = null;
        ArrayList<String> checkList = new ArrayList<String>();
        boolean checkFlg = false;

        checkList.add(sml180MailDfSelected__);
        if (sml180HuriwakeKbn__.equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))) {
            if (checkList.indexOf(sml180Zmail1Selected__) == -1) {
                checkList.add(sml180Zmail1Selected__);
            }
            if (checkList.indexOf(sml180Zmail2Selected__) == -1) {
                checkList.add(sml180Zmail2Selected__);
            }
            if (checkList.indexOf(sml180Zmail3Selected__) == -1) {
                checkList.add(sml180Zmail3Selected__);
            }
        } else if (sml180HuriwakeKbn__.equals(
                                 String.valueOf(GSConstSmail.MAIL_FORWARD_FUZAI_OK))) {
            if (checkList.indexOf(sml180Zmail2Selected__) == -1) {
                checkList.add(sml180Zmail2Selected__);
            }
        }

        //対象ユーザ情報を取得する。
        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
        ArrayList<CmnUsrmInfModel> usrmInfList = null;
        String[] usrSids = null;
        if (sml180ObjKbn__ == 1) {
            //ユーザ指定
            usrSids = sml180userSid__;
        }

        usrmInfList = usrmInfDao.getUserList(usrSids);
        if (usrmInfList == null || usrmInfList.size() < 1) {
            return errors;
        }

        for (String mKbn : checkList) {
            if (!mKbn.equals("0")) {
                checkFlg = true;
                break;
            }
        }

        if (!checkFlg) {
            //メールアドレス1・2・3を選択していない場合、チェックを行わない。
            return errors;
        }

        int count = 0;
        String text = "";
        GsMessage gsMsg = new GsMessage();

        for (CmnUsrmInfModel model : usrmInfList) {

            if (model.getUsrSid() < 100) {
                continue;
            }
            for (String mailKbn : checkList) {

                if (mailKbn.equals("1")) {
                    if (StringUtil.isNullZeroString(model.getUsiMail1())) {
                        text = gsMsg.getMessage(req, "cmn.mailaddress1.user",
                                model.getUsiSei() + " " + model.getUsiMei());
                        msg = new ActionMessage("error.touroku.required.data", text);
                        StrutsUtil.addMessage(errors, msg, count + "error.touroku.required.data");
                    }

                } else if (mailKbn.equals("2")) {
                    if (StringUtil.isNullZeroString(model.getUsiMail2())) {
                        text = gsMsg.getMessage(req, "cmn.mailaddress2.user",
                                model.getUsiSei() + " " + model.getUsiMei());
                        msg = new ActionMessage("error.touroku.required.data", text);
                        StrutsUtil.addMessage(errors, msg, count + "error.touroku.required.data");
                    }

                } else if (mailKbn.equals("3")) {
                    if (StringUtil.isNullZeroString(model.getUsiMail3())) {
                        text = gsMsg.getMessage(req, "cmn.mailaddress3.user",
                               model.getUsiSei() + " " + model.getUsiMei());
                        msg = new ActionMessage("error.touroku.required.data", text);
                        StrutsUtil.addMessage(errors, msg, count + "error.touroku.required.data");
                    }
                }
                count++;
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] 有効データ件数のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param errors アクションエラー
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __validateCheckUsrCount(
            Connection con,
            ActionErrors errors,
            HttpServletRequest req) throws SQLException {

        ActionMessage msg = null;
        ArrayList<String> checkList = new ArrayList<String>();
        boolean checkFlg = false;

        checkList.add(sml180MailDfSelected__);
        if (sml180HuriwakeKbn__.equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))) {
            if (checkList.indexOf(sml180Zmail1Selected__) == -1) {
                checkList.add(sml180Zmail1Selected__);
            }
            if (checkList.indexOf(sml180Zmail2Selected__) == -1) {
                checkList.add(sml180Zmail2Selected__);
            }
            if (checkList.indexOf(sml180Zmail3Selected__) == -1) {
                checkList.add(sml180Zmail3Selected__);
            }
        } else if (sml180HuriwakeKbn__.equals(
                String.valueOf(GSConstSmail.MAIL_FORWARD_FUZAI_OK))) {
            checkList.add(sml180Zmail2Selected__);
        }

        //対象ユーザ情報を取得する。
        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
        ArrayList<CmnUsrmInfModel> usrmInfList = null;
        String[] usrSids = null;
        if (sml180ObjKbn__ == 1) {
            //ユーザ指定
            usrSids = sml180userSid__;
        }

        usrmInfList = usrmInfDao.getUserList(usrSids);
        if (usrmInfList == null || usrmInfList.size() < 1) {
            return errors;
        }

        for (String mKbn : checkList) {
            if (!mKbn.equals("0")) {
                checkFlg = true;
                break;
            }
        }

        if (!checkFlg) {
            //メールアドレス1・2・3を選択していない場合、チェックを行わない。
            return errors;
        }

        int count = 0;
        boolean okFlg = true;
        for (CmnUsrmInfModel model : usrmInfList) {

            if (model.getUsrSid() < 100) {
                continue;
            }
            okFlg = true;
            for (String mailKbn : checkList) {

                if (mailKbn.equals("1")) {
                    if (StringUtil.isNullZeroString(model.getUsiMail1())) {
                        okFlg = false;
                        break;
                    }

                } else if (mailKbn.equals("2")) {
                    if (StringUtil.isNullZeroString(model.getUsiMail2())) {
                        okFlg = false;
                        break;
                    }

                } else if (mailKbn.equals("3")) {
                    if (StringUtil.isNullZeroString(model.getUsiMail3())) {
                        okFlg = false;
                        break;
                    }
                }
            }
            if (okFlg) {
                count++;
            }
        }
        GsMessage gsMsg = new GsMessage();
        String taisyou = gsMsg.getMessage(req, "sml.155");

        if (count < 1) {
            //対象ユーザなし
            msg = new ActionMessage("search.data.notfound", taisyou);
            StrutsUtil.addMessage(errors, msg, "search.data.notfound");

        }
        return errors;
    }

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
    public ArrayList<LabelValueBean> getSml180MbLabelList() {
        return sml180MbLabelList__;
    }
    /**
     * <p>sml180MbLabelList をセットします。
     * @param sml180MbLabelList sml180MbLabelList
     */
    public void setSml180MbLabelList(ArrayList<LabelValueBean> sml180MbLabelList) {
        sml180MbLabelList__ = sml180MbLabelList;
    }
    /**
     * <p>sml180AdLabelList を取得します。
     * @return sml180AdLabelList
     */
    public ArrayList<LabelValueBean> getSml180AdLabelList() {
        return sml180AdLabelList__;
    }
    /**
     * <p>sml180AdLabelList をセットします。
     * @param sml180AdLabelList sml180AdLabelList
     */
    public void setSml180AdLabelList(ArrayList<LabelValueBean> sml180AdLabelList) {
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