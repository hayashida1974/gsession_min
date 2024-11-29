package jp.groupsession.v2.sml.main;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlUserDao;
import jp.groupsession.v2.sml.model.AccountDataModel;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.model.SmlUserModel;
import jp.groupsession.v2.sml.sml270.Sml270Dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール(メイン画面表示用)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlMainBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlMainBiz.class);

    /**
     * <br>[機  能] セッションユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @return sessionUsrSid セッションユーザSID
     */
    private int __getSessionUserSid(RequestModel reqMdl) {

        log__.debug("セッションユーザSID取得");

        int sessionUsrSid = -1;

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        if (usModel != null) {
            sessionUsrSid = usModel.getUsrsid();
        }

        return sessionUsrSid;
    }

    /**
     * <br>[機  能] 初期表示データをセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(SmlMainParamModel paramMdl,
            RequestModel reqMdl,
            Connection con)
                    throws SQLException {

        log__.debug("初期表示データセット");

        int sessionUsrSid = __getSessionUserSid(reqMdl);
        SmailDao sDao = new SmailDao(con);
        SmlUserDao smlUsrDao = new SmlUserDao(con);

        //個人 メイン表示設定取得
        SmlUserModel smlUsrMdl = smlUsrDao.getSmlUserInfo(sessionUsrSid);

        int mainDspKbn;
        int mainDspCnt;
        int mainDspSort;
        if (smlUsrMdl == null) {
            mainDspKbn = GSConstSmail.SML_MAIN_KBN_MIDOKU;
            mainDspCnt = GSConstSmail.SML_MAIN_CNT_10;
            mainDspSort = GSConstSmail.SML_MAIN_SORT_KOUJYUN;
        } else {
            mainDspKbn = smlUsrMdl.getSmlMainKbn();
            mainDspCnt = smlUsrMdl.getSmlMainCnt();
            mainDspSort = smlUsrMdl.getSmlMainSort();
        }
        int mailDspKbn;
        if (mainDspKbn == GSConstSmail.SML_MAIN_KBN_MIDOKU) {
            mailDspKbn = mainDspKbn;
        } else {
            mailDspKbn = -1;
        }

        //昇順降順フラグ、DBから取得する値が通常のソート区分と逆のため置き換え処理
        int orderKbn = GSConst.ORDER_KEY_DESC;
        if (mainDspSort != GSConstSmail.SML_MAIN_SORT_KOUJYUN) {
            orderKbn = GSConst.ORDER_KEY_ASC;
        }

        /************************************************************************
         *
         * 処理モード毎に該当データ一覧を取得する
         *
         ************************************************************************/

        Sml270Dao dao = new Sml270Dao(con);

        List<AccountDataModel> accountList = dao.getAccountList(sessionUsrSid);
        ArrayList<SmailModel> convList = new ArrayList<SmailModel>();

        if (accountList != null && !accountList.isEmpty()) {

            for (AccountDataModel mdl : accountList) {
                ArrayList<SmailModel> resultList =
                        sDao.selectJmeisList(
                                mdl.getAccountSid(), 0, mainDspCnt,
                                GSConstSmail.MSG_SORT_KEY_DATE, orderKbn, mailDspKbn);

                //取得データを表示形式に変換
                convList.addAll(__convertJmeisData(resultList, mdl));
            }

        }

        /************************************************************************
         *
         * 取得・生成した値をフォームにセットする
         *
         ************************************************************************/

        ArrayList<SmailModel> convDspList = new ArrayList<SmailModel>();
        if (convList != null && !convList.isEmpty()) {
            SmailModel smlMdl = null;
            int count = mainDspCnt;
            if (convList.size() < count) {
                count = convList.size();
            }
            for (int i = 0; i < count; i++) {
                smlMdl = new SmailModel();
                smlMdl = convList.get(i);
                convDspList.add(smlMdl);
            }
        }


        paramMdl.setSml010SmlList(convDspList);
    }

    /**
     * <br>[機  能] 取得結果を変換する(受信データ、ゴミ箱データ)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramList 取得結果リスト
     * @param smlMdl 受信アカウント情報
     * @return ret 変換後リスト
     */
    private ArrayList<SmailModel> __convertJmeisData(
            ArrayList<SmailModel> paramList, AccountDataModel smlMdl) {

        ArrayList<SmailModel> ret = new ArrayList<SmailModel>();

        for (SmailModel paramMdl : paramList) {
            SmailModel retMdl = new SmailModel();

            //削除処理時に使用するメール判断キー作成
            //メール区分 + メールSID(10桁フォーマット)
            retMdl.setMailKey(
                    paramMdl.getMailKbn()
                    + StringUtil.toDecFormat(
                            paramMdl.getSmlSid(), GSConstSmail.MAIL_KEY_FORMAT));
            retMdl.setMailKbn(paramMdl.getMailKbn());
            retMdl.setSmlSid(paramMdl.getSmlSid());
            retMdl.setSmjOpkbn(paramMdl.getSmjOpkbn());
            retMdl.setSmsMark(paramMdl.getSmsMark());
            retMdl.setBinCnt(paramMdl.getBinCnt());
            retMdl.setSmsTitle(NullDefault.getString(paramMdl.getSmsTitle(), ""));

            //日付yyyy/MM/dd hh:mm:ss形式に変換
            if (paramMdl.getSmsSdate() != null) {
                String strSdate =
                        UDateUtil.getSlashYYMD(paramMdl.getSmsSdate())
                        + "  "
                        + UDateUtil.getSeparateHMS(paramMdl.getSmsSdate());
                retMdl.setStrSdate(strSdate);
            }
            retMdl.setAccountJkbn(paramMdl.getAccountJkbn());
            retMdl.setAccountSid(paramMdl.getAccountSid());
            retMdl.setAccountName(paramMdl.getAccountName());

            if (paramMdl.getUsrSid() <= 0) {
                retMdl.setUsrJkbn(paramMdl.getAccountJkbn());
                retMdl.setUsiSei(paramMdl.getAccountName());
                retMdl.setUsiMei("");
            } else {
                retMdl.setUsrJkbn(paramMdl.getUsrJkbn());
                retMdl.setUsrUkoFlg(paramMdl.getUsrUkoFlg());
                retMdl.setUsiSei(NullDefault.getString(paramMdl.getUsiSei(), ""));
                retMdl.setUsiMei(NullDefault.getString(paramMdl.getUsiMei(), ""));
            }

            retMdl.setSmlAccountData(smlMdl);

            ret.add(retMdl);
        }
        return ret;
    }
}