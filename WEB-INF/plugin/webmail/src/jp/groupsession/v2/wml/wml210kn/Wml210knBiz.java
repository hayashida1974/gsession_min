package jp.groupsession.v2.wml.wml210kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.wml.biz.WmlLabelBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEBメール 管理者設定 ラベル登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml210knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml210knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクター
     */
    public Wml210knBiz() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Wml210knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,  Wml210knParamModel paramMdl) throws SQLException {
        //アカウント名取得
        WmlAccountDao wacDao = new WmlAccountDao(con);
        WmlAccountModel wacMdl = wacDao.select(paramMdl.getWmlAccountSid());
        paramMdl.setWml200accountName(wacMdl.getWacName());
    }

    /**
     * <br>[機  能] 登録、または更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     */
    public void doAddEdit(
        Wml210knParamModel paramMdl,
        int userSid,
        MlCountMtController cntCon) throws SQLException, IOToolsException {

        //処理モード(新規登録）
        if (paramMdl.getWmlLabelCmdMode() == GSConstWebmail.CMDMODE_ADD) {
            //新規登録
            log__.debug("新規登録");
            doInsert(paramMdl, userSid, cntCon);

        //編集
        } else if (paramMdl.getWmlLabelCmdMode() == GSConstWebmail.CMDMODE_EDIT) {
            log__.debug("編集");
            doUpdate(paramMdl, userSid);
        }
    }

    /**
     * <br>[機  能] 新規登録画面処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     */
    public void doInsert(
        Wml210knParamModel paramMdl,
        int userSid,
        MlCountMtController cntCon) throws SQLException {

        boolean commitFlg = false;

        try {

            con__.setAutoCommit(false);
            
            //ラベルの登録
            WmlLabelBiz labelBiz = new WmlLabelBiz();
            labelBiz.doInsertLabel(cntCon, con__, userSid,
                paramMdl.getWml210LabelName(), paramMdl.getWmlAccountSid());

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
     * <br>[機  能] 編集画面登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doUpdate(
        Wml210knParamModel paramMdl,
        int userSid) throws SQLException {

        boolean commitFlg = false;

        try {

            con__.setAutoCommit(false);

            //ラベル更新Model
            WmlLabelModel wlMdl  = __getLabelUpdateMdl(paramMdl, userSid);

            //ラベルを更新する
            Wml210knDao wldao = new Wml210knDao(con__);
            wldao.update(wlMdl);

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
     * <br>[機  能] 更新のWmlLabelModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザーSID
     * @return WmlLabelModel
     * @throws SQLException SQL実行例外
     */
    private WmlLabelModel __getLabelUpdateMdl(
        Wml210knParamModel paramMdl,
        int userSid) throws SQLException {

        WmlLabelModel wlMdl = new WmlLabelModel();

        wlMdl.setWlbSid(paramMdl.getWmlEditLabelId());
        wlMdl.setWlbName(paramMdl.getWml210LabelName());
        wlMdl.setWacSid(paramMdl.getWmlAccountSid());

        return wlMdl;
    }
}
