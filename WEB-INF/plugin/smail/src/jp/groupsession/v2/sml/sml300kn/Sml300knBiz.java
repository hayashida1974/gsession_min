package jp.groupsession.v2.sml.sml300kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール 管理者設定 ラベル登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml300knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml300knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクター
     */
    public Sml300knBiz() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Sml300knBiz(Connection con) {
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
    public void setInitData(Connection con,  Sml300knParamModel paramMdl) throws SQLException {
        //アカウント名取得
        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = sacDao.select(paramMdl.getSmlAccountSid());
        paramMdl.setSml290accountName(sacMdl.getSacName());
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
        Sml300knParamModel paramMdl,
        int userSid,
        MlCountMtController cntCon) throws SQLException, IOToolsException {

        //処理モード(新規登録）
        if (paramMdl.getSmlLabelCmdMode() == GSConstSmail.CMDMODE_ADD) {
            //新規登録
            log__.debug("新規登録");
            doInsert(paramMdl, userSid, cntCon);

        //編集
        } else if (paramMdl.getSmlLabelCmdMode() == GSConstSmail.CMDMODE_EDIT) {
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
        Sml300knParamModel paramMdl,
        int userSid,
        MlCountMtController cntCon) throws SQLException {

        boolean commitFlg = false;

        try {

            con__.setAutoCommit(false);

            //ラベルSID採番
            int lbSaiSid = (int) cntCon.getSaibanNumber(
                    GSConstSmail.SAIBAN_SML_SID,
                    GSConstSmail.SBNSID_SUB_LABEL,
                    userSid);

            //ラベル登録Model
            SmlLabelModel slMdl  = __getLabelInsertMdl(paramMdl, userSid, lbSaiSid);

            //ラベルを登録する
            SmlLabelDao slDao = new SmlLabelDao(con__);
            slDao.insert(slMdl);

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
        Sml300knParamModel paramMdl,
        int userSid) throws SQLException {

        boolean commitFlg = false;

        try {

            con__.setAutoCommit(false);

            //ラベル更新Model
            SmlLabelModel slMdl  = __getLabelUpdateMdl(paramMdl, userSid);

            //ラベルを更新する
            Sml300knDao sldao = new Sml300knDao(con__);
            sldao.update(slMdl);

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
     * <br>[機  能] 新規登録用のSmlLabelModelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザーSID
     * @param lbSaiSid 採番SID
     * @return SmlLabelModel
     * @throws SQLException SQL実行例外
     */
    private SmlLabelModel __getLabelInsertMdl(
        Sml300knParamModel paramMdl,
        int userSid,
        int lbSaiSid) throws SQLException {

        SmlLabelModel slMdl = new SmlLabelModel();
        SmlLabelDao sldao = new SmlLabelDao(con__);

        slMdl.setSlbSid(lbSaiSid);
        slMdl.setUsrSid(userSid);
        slMdl.setSlbName(paramMdl.getSml300LabelName());
        slMdl.setSlbType(GSConstSmail.LABELTYPE_ONES);
        slMdl.setSacSid(paramMdl.getSmlAccountSid());

        //並び順
        slMdl.setSlbOrder(sldao.maxSortNumber(slMdl.getSacSid()) + 1);

        return slMdl;
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
    private SmlLabelModel __getLabelUpdateMdl(
        Sml300knParamModel paramMdl,
        int userSid) throws SQLException {

        SmlLabelModel slMdl = new SmlLabelModel();

        slMdl.setSlbSid(paramMdl.getSmlEditLabelId());
        slMdl.setUsrSid(userSid);
        slMdl.setSlbName(paramMdl.getSml300LabelName());
        slMdl.setSlbType(GSConstSmail.LABELTYPE_ONES);
        slMdl.setSacSid(paramMdl.getSmlAccountSid());

        return slMdl;
    }
}
