package jp.groupsession.v2.sml.sml140kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.sml240.Sml240AccountModel;
import jp.groupsession.v2.sml.sml240.Sml240Dao;
import jp.groupsession.v2.sml.sml240.Sml240SearchModel;

/**
 * <br>[機  能] ショートメール 個人設定 手動削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml140knBiz {

//    /** Logging インスタンス */
//    private static Log log__ = LogFactory.getLog(Sml140knBiz.class);

    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Sml140knBiz(Connection con) {
        con__ = con;
    }


    /**
     * <br>[機  能] 初期処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void getInitData(RequestModel reqMdl,
                                       Sml140knParamModel paramMdl)
        throws SQLException {


        //アカウントSID
        List<Sml240AccountModel> accountList = new ArrayList<Sml240AccountModel>();

        if (paramMdl.getSml140SelKbn() == GSConstSmail.ACCOUNT_SEL) {
            //指定アカウント
            Sml240AccountModel smlMdl = new Sml240AccountModel(reqMdl);
            smlMdl.setAccountSid(paramMdl.getSml140AccountSid());
            smlMdl.setAccountName(paramMdl.getSml140AccountName());
            accountList.add(smlMdl);
            paramMdl.setSml140knAccountList(accountList);
        } else {
            accountList = getAllUseAccount(
                    reqMdl, reqMdl.getSmodel().getUsrsid(), con__);
            paramMdl.setSml140knAccountList(accountList);

        }
    }

    /**
     * <br>[機  能] ショートメール手動削除
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void updateSyudoDelSetting(RequestModel reqMdl,
                                       Sml140knParamModel paramMdl)
        throws SQLException {


        //アカウントSID
        List<Sml240AccountModel> accountList = new ArrayList<Sml240AccountModel>();

        if (paramMdl.getSml140SelKbn() == GSConstSmail.ACCOUNT_SEL) {
            //指定アカウント
            Sml240AccountModel smlMdl = new Sml240AccountModel(reqMdl);
            smlMdl.setAccountSid(paramMdl.getSml140AccountSid());
            smlMdl.setAccountName(paramMdl.getSml140AccountName());
            accountList.add(smlMdl);
            paramMdl.setSml140knAccountList(accountList);
        } else {
            accountList = getAllUseAccount(
                    reqMdl, reqMdl.getSmodel().getUsrsid(), con__);

            paramMdl.setSml140knAccountList(accountList);

        }

        ArrayList<SmlAdelModel> jdelList = new ArrayList<SmlAdelModel>();
        ArrayList<SmlAdelModel> sdelList = new ArrayList<SmlAdelModel>();
        ArrayList<SmlAdelModel> wdelList = new ArrayList<SmlAdelModel>();
        ArrayList<SmlAdelModel> ddelList = new ArrayList<SmlAdelModel>();

        SmlJmeisDao jmsDao = new SmlJmeisDao(con__);
        SmlSmeisDao smsDao = new SmlSmeisDao(con__);
        SmlWmeisDao wmsDao = new SmlWmeisDao(con__);

        int jdelKbn =
            NullDefault.getInt(paramMdl.getSml140JdelKbn(), GSConstSmail.SML_AUTO_DEL_NO);

        if (jdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {

            for (Sml240AccountModel mdl : paramMdl.getSml140knAccountList()) {
                SmlAdelModel jmdl = new SmlAdelModel();
                jmdl.setSadJdelYear(Integer.parseInt(paramMdl.getSml140JYear()));
                jmdl.setSadJdelMonth(Integer.parseInt(paramMdl.getSml140JMonth()));
                jmdl.setSacSid(mdl.getAccountSid());
                jdelList.add(jmdl);
            }

        }

        int sdelKbn =
            NullDefault.getInt(paramMdl.getSml140SdelKbn(), GSConstSmail.SML_AUTO_DEL_NO);

        if (sdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {

            for (Sml240AccountModel mdl : paramMdl.getSml140knAccountList()) {
                SmlAdelModel smdl = new SmlAdelModel();
                smdl.setSadSdelYear(Integer.parseInt(paramMdl.getSml140SYear()));
                smdl.setSadSdelMonth(Integer.parseInt(paramMdl.getSml140SMonth()));
                smdl.setSacSid(mdl.getAccountSid());
                sdelList.add(smdl);
            }
        }

        int wdelKbn =
            NullDefault.getInt(paramMdl.getSml140WdelKbn(), GSConstSmail.SML_AUTO_DEL_NO);

        if (wdelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {

            for (Sml240AccountModel mdl : paramMdl.getSml140knAccountList()) {
                SmlAdelModel wmdl = new SmlAdelModel();
                wmdl.setSadWdelYear(Integer.parseInt(paramMdl.getSml140WYear()));
                wmdl.setSadWdelMonth(Integer.parseInt(paramMdl.getSml140WMonth()));
                wmdl.setSacSid(mdl.getAccountSid());
                wdelList.add(wmdl);
            }
        }

        int ddelKbn =
            NullDefault.getInt(paramMdl.getSml140DdelKbn(), GSConstSmail.SML_AUTO_DEL_NO);

        if (ddelKbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {

            for (Sml240AccountModel mdl : paramMdl.getSml140knAccountList()) {
                SmlAdelModel dmdl = new SmlAdelModel();
                dmdl.setSadJdelYear(Integer.parseInt(paramMdl.getSml140DYear()));
                dmdl.setSadJdelMonth(Integer.parseInt(paramMdl.getSml140DMonth()));
                dmdl.setSadSdelYear(Integer.parseInt(paramMdl.getSml140DYear()));
                dmdl.setSadSdelMonth(Integer.parseInt(paramMdl.getSml140DMonth()));
                dmdl.setSadWdelYear(Integer.parseInt(paramMdl.getSml140DYear()));
                dmdl.setSadWdelMonth(Integer.parseInt(paramMdl.getSml140DMonth()));
                dmdl.setSadDdelYear(Integer.parseInt(paramMdl.getSml140DYear()));
                dmdl.setSadDdelMonth(Integer.parseInt(paramMdl.getSml140DMonth()));
                dmdl.setSacSid(mdl.getAccountSid());
                ddelList.add(dmdl);
            }
        }


        //ゴミ箱データ削除
        if (!ddelList.isEmpty()) {
            jmsDao.delete(ddelList, 2);
            smsDao.delete(ddelList, 2);
            wmsDao.delete(ddelList, 2);
        }

        if (!jdelList.isEmpty()) {
            //受信タブデータ削除
            jmsDao.delete(jdelList, 1);
        }

        if (!sdelList.isEmpty()) {
            //送信タブデータ削除
            smsDao.delete(sdelList, 1);
        }

        if (!wdelList.isEmpty()) {
            //草稿タブデータ削除
            wmsDao.delete(wdelList, 1);
        }
    }

    /**
     * <br>[機  能] 指定ユーザの利用可能な全アカウントを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return accountList
     */
    public List<Sml240AccountModel> getAllUseAccount(
                                 RequestModel reqMdl,
                                 int usrSid,
                                 Connection con)
        throws SQLException {

        Sml240SearchModel searchMdl = new Sml240SearchModel();
        searchMdl.setUserSid(usrSid);
        searchMdl.setMaxCount(-1);
        Sml240Dao dao = new Sml240Dao(con);
        List<Sml240AccountModel> accountList = dao.getAccountList(searchMdl, reqMdl);

        return accountList;
    }

    /**
     * <br>[機  能] 選択した年月日に不正な値が入った場合に空白文字に置き換える
     * <br>[解  説]
     * <br>[備  考]
     * @param paramModel Sml140knParamModel
     */
    public void updateEgnoreYearMonth(Sml140knParamModel paramModel) {
        if (!ValidateUtil.isNumber(paramModel.getSml140JYear())) {
            paramModel.setSml140JYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml140JMonth())) {
            paramModel.setSml140JMonth("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml140SYear())) {
            paramModel.setSml140SYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml140SMonth())) {
            paramModel.setSml140SMonth("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml140WYear())) {
            paramModel.setSml140WYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml140WMonth())) {
            paramModel.setSml140WMonth("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml140DYear())) {
            paramModel.setSml140DYear("");
        }
        if (!ValidateUtil.isNumber(paramModel.getSml140DMonth())) {
            paramModel.setSml140DMonth("");
        }
    }
}