package jp.groupsession.v2.cmn.cmn350;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnDispPconfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnDispPconfModel;

/**
 * <br>[機  能] 表示設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn350Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn350Biz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;
    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl RequestModel
     */
    public Cmn350Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cmn350ParamModel
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Cmn350ParamModel paramMdl)
            throws SQLException {

        //セッション情報を取得
        HttpSession session = reqMdl_.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid();

        CmnDispPconfDao dao = new CmnDispPconfDao(con__);
        CmnDispPconfModel model = dao.select(sessionUsrSid);
        if (model == null) {
            model = new CmnDispPconfModel();
            //データが存在しないときはデフォルトの値を設定
            model.setUsrSid(sessionUsrSid);
            model.setCdpRokuyou(GSConst.DSP_NOT);
            dao.insert(model);
        }
        paramMdl.setCmn350RokuyoDsp(model.getCdpRokuyou());
    }

    /**
     * <br>[機  能] 表示設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Cmn350ParamModel
     * @throws SQLException SQL実行エラー
     */
    public void setDspPriConfig(Cmn350ParamModel paramMdl)
            throws SQLException {

        boolean commitFlg = false;

        try {
            //セッション情報を取得
            HttpSession session = reqMdl_.getSession();
            BaseUserModel usModel =
                (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
            int sessionUsrSid = usModel.getUsrsid();

            CmnDispPconfModel model = new CmnDispPconfModel();
            CmnDispPconfDao dao = new CmnDispPconfDao(con__);

            model.setUsrSid(sessionUsrSid);
            model.setCdpRokuyou(paramMdl.getCmn350RokuyoDsp());
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
