package jp.groupsession.v2.wml.wml170kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.dao.base.WmlAutodeleteLogDao;
import jp.groupsession.v2.wml.model.base.WmlAutodeleteLogModel;

/**
 * <br>[機  能] WEBメール 送受信ログ自動削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml170knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml170knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクター
     */
    public Wml170knBiz() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Wml170knBiz(Connection con) {
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
            Wml170knParamModel paramMdl)
    throws Exception {

        GsMessage gsMsg = new GsMessage(reqMdl);
        //自動削除設定
        //設定しない
        if (paramMdl.getWml170delKbn().equals(String.valueOf(GSConstWebmail.WAL_KBN_NOSET))) {
            paramMdl.setDelSetUp(gsMsg.getMessage(GSConstWebmail.SETUP_NO));

        //自動削除
        } else {
            int delYear = paramMdl.getWml170delYear();
            paramMdl.setDelSetUp(gsMsg.getMessage(GSConstWebmail.SETUP_AUTO));
            paramMdl.setDelSetUpYear(String.valueOf(delYear));
            paramMdl.setDelSetUpMonth(String.valueOf(paramMdl.getWml170delMonth()));
            paramMdl.setDelSetUpDay(String.valueOf(paramMdl.getWml170delDay()));
        }

    }

    /**
     * <br>[機  能] メール自動削除情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setData(
            Wml170knParamModel paramMdl,
            int userSid)
    throws SQLException {

        boolean commitFlg = false;
        UDate now = new UDate();
        try {

            con__.setAutoCommit(false);

            //登録情報をModelに設定する
            WmlAutodeleteLogModel waMdl = new WmlAutodeleteLogModel();
            waMdl.setWalDelKbn(Integer.parseInt(paramMdl.getWml170delKbn()));
            //削除区分が自動削除の時
            if (paramMdl.getWml170delKbn().equals(
                    String.valueOf(GSConstWebmail.WAL_KBN_AUTODELETE))) {
                waMdl.setWalDelYear(paramMdl.getWml170delYear());
                waMdl.setWalDelMonth(paramMdl.getWml170delMonth());
                waMdl.setWalDelDay(paramMdl.getWml170delDay());
            }

            //ユーザSID、日付
            waMdl.setWalEuid(userSid);
            waMdl.setWalEdate(now);

            WmlAutodeleteLogDao waDao = new WmlAutodeleteLogDao(con__);
            if (waDao.update(waMdl) == 0) {
                waMdl.setWalAuid(userSid);
                waMdl.setWalAdate(now);
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
