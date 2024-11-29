package jp.groupsession.v2.sml.sml280kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAdminDao;
import jp.groupsession.v2.sml.model.SmlAdminModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール アカウント基本設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml280knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml280knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Sml280knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws Exception 実行例外
     */
    public void setData(Sml280knParamModel paramMdl) throws Exception {

        boolean commitFlg = false;
        try {

            SmlCommonBiz cmnBiz = new SmlCommonBiz(con__, reqMdl__);

            con__.setAutoCommit(false);
            SmlAdminDao sacDao = new SmlAdminDao(con__);
            SmlAdminModel sacMdl = new SmlAdminModel();

            sacMdl = cmnBiz.getSmailAdminConf(reqMdl__.getSmodel().getUsrsid(), con__);

            //アカウント作成区分設定
            sacMdl.setSmaAcntMake(paramMdl.getSml280acntMakeKbn());
            //自動削除区分
            sacMdl.setSmaAutoDelKbn(paramMdl.getSml280autoDelKbn());
            //アカウント使用者制限
            sacMdl.setSmaAcntUser(paramMdl.getSml280acntUser());

            //テーブルにデータが存在するかチェックする
            int count = sacDao.selectCount();

            if (count > 0) {
                //更新処理
                sacDao.updateActSetting(sacMdl);
            } else {
                sacDao.insert(sacMdl);
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