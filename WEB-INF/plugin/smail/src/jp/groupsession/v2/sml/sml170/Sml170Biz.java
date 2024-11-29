package jp.groupsession.v2.sml.sml170;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAccountForwardDao;
import jp.groupsession.v2.sml.model.SmlAccountForwardModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.sml240.Sml240AccountModel;
import jp.groupsession.v2.sml.sml240.Sml240Dao;
import jp.groupsession.v2.sml.sml240.Sml240SearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール 個人設定 メール転送設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml170Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(RequestModel reqMdl, Sml170ParamModel paramMdl, Connection con)
        throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();

        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = new SmlAccountModel();

        if (paramMdl.getSml170InitFlg() == GSConstSmail.DSP_FIRST) {

            if (paramMdl.getSml170AccountSid() > 0) {
                sacMdl = sacDao.select(paramMdl.getSml170AccountSid());
            } else {
                //デフォルトのアカウントを所得
                sacMdl = sacDao.selectFromUsrSid(sessionUsrSid);
            }

            paramMdl.setSml170AccountSid(sacMdl.getSacSid());
            paramMdl.setSml170AccountName(sacMdl.getSacName());


            //転送設定を取得
            SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl);
            SmlAccountForwardModel result = smlCmnBiz.getSmailAccountForward(
                    paramMdl.getSml170AccountSid(), sessionUsrSid, con);

            paramMdl.setSml170MailFw(
                    NullDefault.getString(
                            String.valueOf(result.getSafMailfw()), String.valueOf(0)));
            paramMdl.setSml170MailDfSelected(String.valueOf(0));
            paramMdl.setSml170MailDf(
                    NullDefault.getString(result.getSafMailDf(), ""));
            paramMdl.setSml170SmailOp(
                    NullDefault.getString(
                            String.valueOf(result.getSafSmailOp()), String.valueOf(0)));

            paramMdl.setSml170HuriwakeKbn(
                    NullDefault.getString(
                            String.valueOf(result.getSafHuriwake()), String.valueOf(0)));

            paramMdl.setSml170Zmail1Selected(String.valueOf(0));
            paramMdl.setSml170Zmail1(
                    NullDefault.getString(result.getSafZmail1(), ""));
            paramMdl.setSml170Zmail2Selected(String.valueOf(0));
            paramMdl.setSml170Zmail2(
                    NullDefault.getString(result.getSafZmail2(), ""));
            paramMdl.setSml170Zmail3Selected(String.valueOf(0));
            paramMdl.setSml170Zmail3(
                    NullDefault.getString(result.getSafZmail3(), ""));

        }

        paramMdl.setSml170MailList(getMailLavel(sessionUsrSid, con, reqMdl));
        //転送管理者設定
        //DBより現在の設定を取得する。(なければデフォルト)
        SmlCommonBiz cmnBiz = new SmlCommonBiz(reqMdl);
        SmlAdminModel admConf = cmnBiz.getSmailAdminConf(sessionUsrSid, con);
        paramMdl.setSml170MailFwAdminConf(admConf.getSmaMailfw());



        paramMdl.setSml170InitFlg(GSConstSmail.DSP_ALREADY);

    }

    /**
     * <br>[機  能] メールアドレスコンボを生成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userSid ユーザSID
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return List (in LabelValueBean)  メールアドレスコンボ
     * @throws SQLException SQL実行時例外
     */
    public List<LabelValueBean> getMailLavel(int userSid, Connection con, RequestModel reqMdl)
    throws SQLException {

        CmnUsrmInfDao dao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel model = new CmnUsrmInfModel();
        //ユーザSIDをセット
        model.setUsrSid(userSid);
        model = dao.select(model);

        GsMessage gsMsg = new GsMessage(reqMdl);
        String otherAdr = gsMsg.getMessage("sml.122");

        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        labelList.add(
                new LabelValueBean(otherAdr, String.valueOf(0)));

        if (NullDefault.getString(model.getUsiMail1(), "").length() > 0) {
            labelList.add(
                    new LabelValueBean(model.getUsiMail1(), model.getUsiMail1()));
        }
        if (NullDefault.getString(model.getUsiMail2(), "").length() > 0) {
            labelList.add(
                    new LabelValueBean(model.getUsiMail2(), model.getUsiMail2()));
        }
        if (NullDefault.getString(model.getUsiMail3(), "").length() > 0) {
            labelList.add(
                    new LabelValueBean(model.getUsiMail3(), model.getUsiMail3()));
        }

        return labelList;
    }

    /**
     * <br>[機  能] メール転送設定の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラm－得た情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void updateDspCount(RequestModel reqMdl,
                                Sml170ParamModel paramMdl,
                                Connection con)
        throws SQLException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = usModel.getUsrsid();
        UDate nowDate = new UDate();

        SmlAccountForwardModel smlMdl = new SmlAccountForwardModel();

        smlMdl.setUsrSid(sessionUsrSid);

        smlMdl.setSafMailfw(
                NullDefault.getInt(paramMdl.getSml170MailFw(), GSConstSmail.MAIL_FORWARD_NG));
        smlMdl.setSafSmailOp(GSConstSmail.OPKBN_UNOPENED);
        if (smlMdl.getSafMailfw() == GSConstSmail.MAIL_FORWARD_OK) {
            if (paramMdl.getSml170MailDfSelected().equals("0")) {
                smlMdl.setSafMailDf(
                        NullDefault.getString(paramMdl.getSml170MailDf(), ""));
            } else {
                smlMdl.setSafMailDf(
                        NullDefault.getString(paramMdl.getSml170MailDfSelected(), ""));
            }
            smlMdl.setSafSmailOp(
                    NullDefault.getInt(paramMdl.getSml170SmailOp(), GSConstSmail.OPKBN_UNOPENED));
        }

        smlMdl.setSafHuriwake(
                NullDefault.getInt(paramMdl.getSml170HuriwakeKbn(), GSConstSmail.MAIL_FORWARD_NG));
        if (smlMdl.getSafHuriwake() == GSConstSmail.MAIL_FORWARD_OK) {

            if (paramMdl.getSml170Zmail1Selected().equals("0")) {
                smlMdl.setSafZmail1(
                        NullDefault.getString(paramMdl.getSml170Zmail1(), ""));
            } else {
                smlMdl.setSafZmail1(
                        NullDefault.getString(paramMdl.getSml170Zmail1Selected(), ""));
            }
            if (paramMdl.getSml170Zmail2Selected().equals("0")) {
                smlMdl.setSafZmail2(
                        NullDefault.getString(paramMdl.getSml170Zmail2(), ""));
            } else {
                smlMdl.setSafZmail2(
                        NullDefault.getString(paramMdl.getSml170Zmail2Selected(), ""));
            }
            if (paramMdl.getSml170Zmail3Selected().equals("0")) {
                smlMdl.setSafZmail3(
                        NullDefault.getString(paramMdl.getSml170Zmail3(), ""));
            } else {
                smlMdl.setSafZmail3(
                        NullDefault.getString(paramMdl.getSml170Zmail3Selected(), ""));
            }
        } else if (smlMdl.getSafHuriwake() == GSConstSmail.MAIL_FORWARD_FUZAI_OK) {
            if (paramMdl.getSml170Zmail2Selected().equals("0")) {
                smlMdl.setSafZmail2(
                        NullDefault.getString(paramMdl.getSml170Zmail2(), ""));
            } else {
                smlMdl.setSafZmail2(
                        NullDefault.getString(paramMdl.getSml170Zmail2Selected(), ""));
            }
        }

        smlMdl.setSafEuid(sessionUsrSid);
        smlMdl.setSafEdate(nowDate);

        if (paramMdl.getSml170SelKbn() == GSConstSmail.ACCOUNT_SEL) {
            //指定アカウント
            smlMdl.setSacSid(paramMdl.getSml170AccountSid());
            updateAccountForward(smlMdl, sessionUsrSid, nowDate, con);

        } else {

            List<Sml240AccountModel> accountList = getAllUseAccount(
                                    paramMdl, reqMdl, sessionUsrSid, con);
            //全アカウント
            if (accountList != null && !accountList.isEmpty()) {
                for (Sml240AccountModel mdl : accountList) {
                    smlMdl.setSacSid(mdl.getAccountSid());
                    updateAccountForward(smlMdl, sessionUsrSid, nowDate, con);
                }
            }
        }
    }

    /**
     * <br>[機  能] メール転送設定の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param smlMdl SmlAccountForwardModel
     * @param sessionUsrSid ユーザSID
     * @param nowDate 現在時刻
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void updateAccountForward(SmlAccountForwardModel smlMdl,
                                int sessionUsrSid,
                                UDate nowDate,
                                Connection con)
        throws SQLException {
        SmlAccountForwardDao smlDao = new SmlAccountForwardDao(con);

        int updateCnt = smlDao.updateSmlForward(smlMdl);

        //更新件数が0件の場合は追加
        if (updateCnt == 0) {
            smlMdl.setSafAuid(sessionUsrSid);
            smlMdl.setSafAdate(nowDate);
            smlDao.insert(smlMdl);
        }
    }

    /**
     * <br>[機  能] 指定ユーザの利用可能な全アカウントを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Sml170ParamModel
     * @param reqMdl RequestModel
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return accountList
     */
    public List<Sml240AccountModel> getAllUseAccount(Sml170ParamModel paramMdl,
                                 RequestModel reqMdl,
                                 int usrSid,
                                 Connection con)
        throws SQLException {

        Sml240SearchModel searchMdl = new Sml240SearchModel();
        searchMdl.setUserSid(usrSid);
        searchMdl.setMaxCount(-1);
        Sml240Dao dao = new Sml240Dao(con);
        List<Sml240AccountModel> accountList = dao.getAccountList(searchMdl, reqMdl);
        if (accountList != null && !accountList.isEmpty()) {
            paramMdl.setSml170AllUseAccount(accountList);
        }
        return accountList;
    }
}