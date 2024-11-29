package jp.groupsession.v2.man.man240kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.config.LoggingConfig;
import jp.groupsession.v2.cmn.dao.base.CmnLogConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLogConfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.man240.Man240BaseForm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 オペレーションログ設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man240knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man240knBiz.class);

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Man240knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] オペレーションログ設定情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void update(Man240knParamModel paramMdl, int usrSid, RequestModel reqMdl)
    throws SQLException {

        boolean commitFlg = false;

        try {

            con__.setAutoCommit(false);

            __updateLogConf(paramMdl, usrSid, reqMdl);
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

    /**
     * <br>[機  能] オペレーションログ設定情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    private void __updateLogConf(Man240knParamModel paramMdl,
                                 int usrSid,
                                 RequestModel reqMdl) throws SQLException {

        String domain = reqMdl.getDomain();
        LoggingConfig loggingConfig = new LoggingConfig();

        List<Man240BaseForm> logConfList = paramMdl.getMan240LogConfList();

        if (logConfList == null || logConfList.size() < 1) {

            GroupSession.getResourceManager().setLoggingConfig(domain, loggingConfig);
            return;
        }

        CmnLogConfDao dao = new CmnLogConfDao(con__);
        CmnLogConfModel model = null;
        UDate now = new UDate();

        for (Man240BaseForm baseForm : logConfList) {
            model = new CmnLogConfModel();
            model.setLgcPlugin(baseForm.getLgcPlugin());
            model.setLgcLevelError(NullDefault.getInt(baseForm.getLgcLevelError(),
                    GSConstMain.OPE_LOG_NOTCONF));
            model.setLgcLevelWarn(NullDefault.getInt(baseForm.getLgcLevelWarn(),
                    GSConstMain.OPE_LOG_NOTCONF));
            model.setLgcLevelInfo(NullDefault.getInt(baseForm.getLgcLevelInfo(),
                    GSConstMain.OPE_LOG_NOTCONF));
            model.setLgcLevelTrace(NullDefault.getInt(baseForm.getLgcLevelTrace(),
                    GSConstMain.OPE_LOG_NOTCONF));
            model.setLgcEdate(now);
            model.setLgcEuid(usrSid);

            //オペレーションログ設定情報を更新する。
            int count = dao.update(model);
            if (count == 0) {

                //レコードが存在しない場合は作成する。
                model.setLgcAdate(now);
                model.setLgcAuid(usrSid);
                dao.insert(model);
            }

            loggingConfig.addLogConf(model);

        }

        //GSコンテキストへ設定
        GroupSession.getResourceManager().setLoggingConfig(domain, loggingConfig);
    }
}