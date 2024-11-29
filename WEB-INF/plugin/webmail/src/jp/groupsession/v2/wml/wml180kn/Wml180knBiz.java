package jp.groupsession.v2.wml.wml180kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlUsedDataBiz;
import jp.groupsession.v2.wml.dao.WebmailDao;

/**
 * <br>[機  能] WEBメール 送受信ログ手動削除確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml180knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml180knBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクター
     */
    public Wml180knBiz() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Wml180knBiz(Connection con) {
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
            Wml180knParamModel paramMdl)
    throws Exception {

        log__.debug("START");

        GsMessage gsMsg = new GsMessage(reqMdl);
        int yearDust = paramMdl.getWml180delYear();
        paramMdl.setManuDelSetUp(gsMsg.getMessage(GSConstWebmail.MANU_SETUP_OK));
        paramMdl.setManuDelSetUpYear(String.valueOf(yearDust));
        paramMdl.setManuDelSetUpMonth(String.valueOf(paramMdl.getWml180delMonth()));
        paramMdl.setManuDelSetUpDay(String.valueOf(paramMdl.getWml180delDay()));

        log__.debug("END");
    }

    /**
     * <br>[機  能] メール手動削除情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setData(
            Wml180knParamModel paramMdl,
            int userSid)
    throws SQLException {

        boolean commitFlg = false;

        try {
            con__.setAutoCommit(false);

            WebmailDao dao = new WebmailDao(con__);
            WmlUsedDataBiz usedDataBiz = new WmlUsedDataBiz(con__);

            if (paramMdl.getWml180delKbn() == Wml180knForm.DEL_KBN_DATEAREA) {

                UDate frDate = new UDate();
                frDate.setDate(paramMdl.getWml180delYearFr(),
                            paramMdl.getWml180delMonthFr(),
                            paramMdl.getWml180delDayFr());
                frDate.setZeroHhMmSs();
                frDate.setMilliSecond(0);

                UDate toDate = new UDate();
                toDate.setTimeStamp(paramMdl.getWml180delYearTo(),
                                    paramMdl.getWml180delMonthTo(),
                                    paramMdl.getWml180delDayTo(),
                                    GSConstMain.DAY_END_HOUR,
                                    GSConstMain.DAY_END_MINUTES,
                                    GSConstMain.DAY_END_SECOND);
                toDate.setMilliSecond(GSConstMain.DAY_END_MILLISECOND);

                //メール送受信ログのデータ使用量を登録(削除対象のデータ使用量を減算)
                List<Long> delMessageNumList
                                    = dao.getMailLogToDelete(frDate, toDate);
                usedDataBiz.insertMaillogSize(delMessageNumList, false);

                //メール送受信ログを削除する
                dao.deleteMailLogSend(frDate, toDate);
                dao.deleteMailLog(frDate, toDate);

            } else if (paramMdl.getWml180delKbn() == Wml180knForm.DEL_KBN_ALL) {
                //メール送受信ログのデータ使用量を登録(削除対象のデータ使用量を減算)
                usedDataBiz.removeAllMailLogSize();

                dao.deleteMailLogSend(null, null);
                dao.deleteMailLog(null, null);

            } else {
                int year = paramMdl.getWml180delYear();
                int month = paramMdl.getWml180delMonth();
                int day = paramMdl.getWml180delDay();

                //メール送受信ログのデータ使用量を登録(削除対象のデータ使用量を減算)
                List<Long> delMessageNumList
                                    = dao.getMailLogToDelete(year, month, day);
                usedDataBiz.insertMaillogSize(delMessageNumList, false);

                //メール送受信ログを削除する
                dao.deleteMailLogSend(year, month, day);
                dao.deleteMailLog(year, month, day);
            }

            commitFlg = true;
            con__.commit();

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;

        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con__);
            }
        }
    }
}
