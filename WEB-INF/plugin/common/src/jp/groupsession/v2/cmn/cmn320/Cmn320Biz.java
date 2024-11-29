package jp.groupsession.v2.cmn.cmn320;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnDispAconfDao;
import jp.groupsession.v2.cmn.model.base.CmnDispAconfModel;

/**
 * <br>[機  能] 表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn320Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn320Biz.class);
    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     */
    public Cmn320Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cmn320ParamModel
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Cmn320ParamModel paramMdl)
            throws SQLException {

        CmnDispAconfDao dao = new CmnDispAconfDao(con__);
        CmnDispAconfModel model = dao.select();

        if (model == null) {
            model = new CmnDispAconfModel();
            //データが存在しないときはデフォルトの値を設定
            model.setCdaRokuyouKbn(GSConst.SETTING_ADM);
            model.setCdaRokuyou(GSConst.DSP_NOT);
            dao.insert(model);
        }
        paramMdl.setCmn320RokuyoDspKbn(model.getCdaRokuyouKbn());
        paramMdl.setCmn320RokuyoDsp(model.getCdaRokuyou());
    }

    /**
     * <br>[機  能] 表示設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cmn320ParamModel
     * @throws SQLException SQL実行エラー
     */
    public void setDspAdmConfig(Cmn320ParamModel paramMdl)
            throws SQLException {

        boolean commitFlg = false;

        try {
            CmnDispAconfModel model = new CmnDispAconfModel();
            CmnDispAconfDao dao = new CmnDispAconfDao(con__);

            model.setCdaRokuyouKbn(paramMdl.getCmn320RokuyoDspKbn());
            model.setCdaRokuyou(paramMdl.getCmn320RokuyoDsp());
            dao.update(model);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("編集失敗", e);
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
