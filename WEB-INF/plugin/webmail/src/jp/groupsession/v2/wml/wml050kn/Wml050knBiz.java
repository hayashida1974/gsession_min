package jp.groupsession.v2.wml.wml050kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlAutodeleteDao;
import jp.groupsession.v2.wml.model.base.WmlAutodeleteModel;

/**
 * <br>[機  能] WEBメール 自動削除設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml050knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml050knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクター
     */
    public Wml050knBiz() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Wml050knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws Exception 実行例外
     */
    public void setInitData(
            RequestModel reqMdl,
            Wml050knParamModel paramMdl)
    throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String txt_setupNo = gsMsg.getMessage(GSConstWebmail.SETUP_NO);
        String txt_setupLogout = gsMsg.getMessage(GSConstWebmail.SETUP_LOGOUT);
        String txt_setupAuto = gsMsg.getMessage(GSConstWebmail.SETUP_AUTO);

        //ゴミ箱自動削除設定
        //設定しない
        if (paramMdl.getWml050dustKbn().equals(String.valueOf(GSConstWebmail.WAD_DUST_NO))) {
            paramMdl.setDustDelSetUp(txt_setupNo);

        //ログアウト毎に削除
        } else if (paramMdl.getWml050dustKbn().equals(
                        String.valueOf(GSConstWebmail.WAD_DUST_LOGOUT))) {
            paramMdl.setDustDelSetUp(txt_setupLogout);

        //自動削除
        } else {
            int yearDust = paramMdl.getWml050dustYear();
            paramMdl.setDustDelSetUp(txt_setupAuto);
            paramMdl.setDustDelSetUpYear(String.valueOf(yearDust));
            paramMdl.setDustDelSetUpMonth(String.valueOf(paramMdl.getWml050dustMonth()));
            paramMdl.setDustDelSetUpDay(String.valueOf(paramMdl.getWml050dustDay()));
        }

        //送信済み自動削除設定
        //設定しない
        if (paramMdl.getWml050sendKbn().equals(String.valueOf(GSConstWebmail.WAD_SEND_NO))) {
            paramMdl.setSendDelSetUp(txt_setupNo);

        //ログアウト毎に削除
        } else if (paramMdl.getWml050sendKbn().equals(
                        String.valueOf(GSConstWebmail.WAD_SEND_LOGOUT))) {
            paramMdl.setSendDelSetUp(txt_setupLogout);

        //自動削除
        } else {
            int yearSend = paramMdl.getWml050sendYear();
            paramMdl.setSendDelSetUp(txt_setupAuto);
            paramMdl.setSendDelSetUpYear(String.valueOf(yearSend));
            paramMdl.setSendDelSetUpMonth(String.valueOf(paramMdl.getWml050sendMonth()));
            paramMdl.setSendDelSetUpDay(String.valueOf(paramMdl.getWml050sendDay()));
        }

        //草稿自動削除設定
        //設定しない
        if (paramMdl.getWml050draftKbn().equals(String.valueOf(GSConstWebmail.WAD_DRAFT_NO))) {
            paramMdl.setDraftDelSetUp(txt_setupNo);

        //ログアウト毎に削除
        } else if (paramMdl.getWml050draftKbn().equals(
                String.valueOf(GSConstWebmail.WAD_DRAFT_LOGOUT))) {
            paramMdl.setDraftDelSetUp(txt_setupLogout);

        //自動削除
        } else {
            int yearDraft = paramMdl.getWml050draftYear();
            paramMdl.setDraftDelSetUp(txt_setupAuto);
            paramMdl.setDraftDelSetUpYear(String.valueOf(yearDraft));
            paramMdl.setDraftDelSetUpMonth(String.valueOf(paramMdl.getWml050draftMonth()));
            paramMdl.setDraftDelSetUpDay(String.valueOf(paramMdl.getWml050draftDay()));
        }

        //受信箱自動削除設定
        //設定しない
        if (paramMdl.getWml050resvKbn().equals(String.valueOf(GSConstWebmail.WAD_RESV_NO))) {
            paramMdl.setResvDelSetUp(txt_setupNo);

        //ログアウト毎に削除
        } else if (paramMdl.getWml050resvKbn().equals(
                 String.valueOf(GSConstWebmail.WAD_RESV_LOGOUT))) {
             paramMdl.setResvDelSetUp(txt_setupLogout);

        //自動削除
        } else {
            int yearResv = paramMdl.getWml050resvYear();
            paramMdl.setResvDelSetUp(txt_setupAuto);
            paramMdl.setResvDelSetUpYear(String.valueOf(yearResv));
            paramMdl.setResvDelSetUpMonth(String.valueOf(paramMdl.getWml050resvMonth()));
            paramMdl.setResvDelSetUpDay(String.valueOf(paramMdl.getWml050resvDay()));
        }

        //保管自動削除設定
        //設定しない
        if (paramMdl.getWml050keepKbn().equals(String.valueOf(GSConstWebmail.WAD_KEEP_NO))) {
            paramMdl.setKeepDelSetUp(txt_setupNo);

        //ログアウト毎に削除
        } else if (paramMdl.getWml050keepKbn().equals(
                 String.valueOf(GSConstWebmail.WAD_KEEP_LOGOUT))) {
             paramMdl.setKeepDelSetUp(txt_setupLogout);

        //自動削除
        } else {
            int yearKeep = paramMdl.getWml050keepYear();
            paramMdl.setKeepDelSetUp(txt_setupAuto);
            paramMdl.setKeepDelSetUpYear(String.valueOf(yearKeep));
            paramMdl.setKeepDelSetUpMonth(String.valueOf(paramMdl.getWml050keepMonth()));
            paramMdl.setKeepDelSetUpDay(String.valueOf(paramMdl.getWml050keepDay()));
        }
    }

    /**
     * <br>[機  能] 自動削除情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setData(
            Wml050knParamModel paramMdl,
            int userSid)
    throws SQLException {

        int setupCount = -1;
        boolean commitFlg = false;
        UDate now = new UDate();
        try {

            con__.setAutoCommit(false);

            //登録情報をModelに設定する
            WmlAutodeleteModel waMdl = new WmlAutodeleteModel();
            //ゴミ箱
            waMdl.setWadDustKbn(Integer.parseInt(paramMdl.getWml050dustKbn()));
            //削除区分が自動削除の時
            if (paramMdl.getWml050dustKbn().equals(String.valueOf(GSConstWebmail.WAD_DUST_AUTO))) {
                waMdl.setWadDustYear(paramMdl.getWml050dustYear());
                waMdl.setWadDustMonth(paramMdl.getWml050dustMonth());
                waMdl.setWadDustDay(paramMdl.getWml050dustDay());
            }

            //送信済み
            waMdl.setWadSendKbn(Integer.parseInt(paramMdl.getWml050sendKbn()));
            //削除区分が自動削除の時
            if (paramMdl.getWml050sendKbn().equals(String.valueOf(GSConstWebmail.WAD_SEND_AUTO))) {
                waMdl.setWadSendYear(paramMdl.getWml050sendYear());
                waMdl.setWadSendMonth(paramMdl.getWml050sendMonth());
                waMdl.setWadSendDay(paramMdl.getWml050sendDay());
            }

            //草稿
            waMdl.setWadDraftKbn(Integer.parseInt(paramMdl.getWml050draftKbn()));
            //削除区分が自動削除の時
            if (paramMdl.getWml050draftKbn().equals(
                            String.valueOf(GSConstWebmail.WAD_DRAFT_AUTO))) {
                waMdl.setWadDraftYear(paramMdl.getWml050draftYear());
                waMdl.setWadDraftMonth(paramMdl.getWml050draftMonth());
                waMdl.setWadDraftDay(paramMdl.getWml050draftDay());
            }

            //受信箱
            waMdl.setWadResvKbn(Integer.parseInt(paramMdl.getWml050resvKbn()));
            //削除区分が自動削除の時
            if (paramMdl.getWml050resvKbn().equals(String.valueOf(GSConstWebmail.WAD_RESV_AUTO))) {
                waMdl.setWadResvYear(paramMdl.getWml050resvYear());
                waMdl.setWadResvMonth(paramMdl.getWml050resvMonth());
                waMdl.setWadResvDay(paramMdl.getWml050resvDay());
            }

            //保管
            waMdl.setWadKeepKbn(Integer.parseInt(paramMdl.getWml050keepKbn()));
            //削除区分が自動削除の時
            if (paramMdl.getWml050keepKbn().equals(String.valueOf(GSConstWebmail.WAD_KEEP_AUTO))) {
                waMdl.setWadKeepYear(paramMdl.getWml050keepYear());
                waMdl.setWadKeepMonth(paramMdl.getWml050keepMonth());
                waMdl.setWadKeepDay(paramMdl.getWml050keepDay());
            }

            //ユーザSID、日付
            waMdl.setWadEuid(userSid);
            waMdl.setWadEdate(now);

            //登録件数を取得する
            WmlAutodeleteDao waDao = new WmlAutodeleteDao(con__);
            setupCount = waDao.getAutoDelSetUpCnt();

            //登録件数が1件の場合
            if (setupCount == 1) {
                waDao.update(waMdl);

            //登録件数が0件の場合
            } else {
                waMdl.setWadAuid(userSid);
                waMdl.setWadAdate(now);
                waDao.insert(waMdl);
            }
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
        throw e;

        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }
}
