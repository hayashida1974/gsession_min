package jp.groupsession.v2.man.man110kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.man.man100.Man100Biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 役職登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man110knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man110knBiz.class);

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void getInitData(Man110knParamModel paramMdl) {

        //備考(表示用)をセット
        paramMdl.setBikou(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getMan110bikou()), ""));
    }

    /**
     * <br>[機  能] 登録、または更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doAddEdit(
        Man110knParamModel paramMdl,
        Connection con,
        MlCountMtController cntCon,
        int userSid) throws SQLException {

        int procMode = paramMdl.getMan100ProcMode();
        if (procMode == Man100Biz.MODE_ADD) {
            //登録
            doInsert(paramMdl, con, cntCon, userSid);
        } else if (procMode == Man100Biz.MODE_EDIT) {
            //更新
            doUpdate(paramMdl, con, userSid);
        }
    }

    /**
     * <br>[機  能] 登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doInsert(
        Man110knParamModel paramMdl,
        Connection con,
        MlCountMtController cntCon,
        int userSid) throws SQLException {

        boolean commitFlg = false;

        try {

            con.setAutoCommit(false);

            //役職SID採番
            int posSid = (int) cntCon.getSaibanNumber(SaibanModel.SBNSID_USER,
                                                           SaibanModel.SBNSID_SUB_POS,
                                                           userSid);
            //登録用Model作成
            CmnPositionModel cpMdl = __getUpdateModel(posSid, paramMdl, userSid);

            //insert
            CmnPositionDao cpDao = new CmnPositionDao(con);
            cpDao.insertPos(cpMdl);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doUpdate(Man110knParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        boolean commitFlg = false;

        try {

            con.setAutoCommit(false);

            //役職SID
            int editPosSid = paramMdl.getMan100EditPosSid();

            //登録用Model作成
            CmnPositionModel cpMdl = __getUpdateModel(editPosSid, paramMdl, userSid);

            //update
            CmnPositionDao cpDao = new CmnPositionDao(con);
            cpDao.updatePos(cpMdl);
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能] 登録・更新用Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posSid 役職SID
     * @param paramMdl パラメータ情報
     * @param userSid ログインユーザSID
     * @return CmnPositionModel 登録・更新用Model
     */
    private CmnPositionModel __getUpdateModel(int posSid, Man110knParamModel paramMdl,
                                            int userSid) {

        UDate now = new UDate();

        CmnPositionModel cpMdl = new CmnPositionModel();
        cpMdl.setPosSid(posSid);
        cpMdl.setPosCode(NullDefault.getString(paramMdl.getMan110posCode(), ""));
        cpMdl.setPosName(NullDefault.getString(paramMdl.getMan110posName(), ""));
        cpMdl.setPosBiko(NullDefault.getString(paramMdl.getMan110bikou(), ""));
        cpMdl.setPosSort(posSid);
        cpMdl.setPosAuid(userSid);
        cpMdl.setPosAdate(now);
        cpMdl.setPosEuid(userSid);
        cpMdl.setPosEdate(now);

        return cpMdl;
    }
}
