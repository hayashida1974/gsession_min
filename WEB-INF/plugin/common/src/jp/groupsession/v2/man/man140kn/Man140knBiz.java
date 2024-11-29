package jp.groupsession.v2.man.man140kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 セッション保持時間設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man140knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man140knBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param reqMdl リクエスト情報
     */
    public Man140knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示の設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Man140knParamModel paramMdl) throws SQLException {

        //表示用のセッション保持時間を設定する。
        paramMdl.setMan140knSessionTime(__getSessionTimeDsp(paramMdl));
    }

    /**
     * <br>[機  能] 表示用のセッション保持時間を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return String セッション保持時間(表示用)
     */
    private String __getSessionTimeDsp(Man140knParamModel paramMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (paramMdl.getMan140SessionTime() >= 60) {
            return gsMsg.getMessage(
                    "cmn.hours",
                    new String[] {String.valueOf((paramMdl.getMan140SessionTime()) / 60)});
        }
        return paramMdl.getMan140SessionTime() + gsMsg.getMessage("cmn.minute");
    }

    /**
     * <br>[機  能] セッション保持時間の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void update(Man140knParamModel paramMdl) throws SQLException {

        boolean commitFlg = false;

        //セッション保持時間の設定の更新を行う
        try {

            con__.setAutoCommit(false);
            CmnContmDao dao = new CmnContmDao(con__);
            dao.updateSessionTime(paramMdl.getMan140SessionTime());

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