package jp.groupsession.v2.usr.usr046;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrCategoryDao;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmLabelDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrCategoryModel;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] ユーザ情報 ラベル登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr046Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr046Biz.class);

    /** 確認画面からの遷移 */
    private static final int KAKUNIN_BACK = 1;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Usr046Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Usr046ParamModel
     * @param kbn 遷移元区分
     * @throws SQLException SQL実行例外
     */
    public void getInitData(
              Connection con, Usr046ParamModel paramMdl, int kbn) throws SQLException {

        //カテゴリコンボ作成
        ArrayList<LabelValueBean> catCmbList = new ArrayList<LabelValueBean>();
        CmnLabelUsrCategoryDao catDao = new CmnLabelUsrCategoryDao(con);
        ArrayList<CmnLabelUsrCategoryModel> modelList = catDao.select();
        for (CmnLabelUsrCategoryModel catMdl : modelList) {
            String catName = catMdl.getLucName();
            String catSid = Integer.toString(catMdl.getLucSid());
            catCmbList.add(new LabelValueBean(catName, catSid));
        }

        //新規登録か、編集か
        if (paramMdl.getUsr044ProcMode() == GSConstUser.PROCMODE_ADD) {
            paramMdl.setUsr046CatCmbList(catCmbList);
            //遷移元が確認画面か
            if (kbn == KAKUNIN_BACK) {
                return;
            }
            paramMdl.setUsr046CatSid(paramMdl.getUsr043EditSid());
            return;
        } else if (paramMdl.getUsr044ProcMode() == GSConstUser.PROCMODE_EDIT) {
            //遷移元が確認画面か
            if (kbn == KAKUNIN_BACK) {
                paramMdl.setUsr046CatCmbList(catCmbList);
                return;
            }
            paramMdl.setUsr046CatSid(paramMdl.getUsr043EditSid());
        }

        //ラベル情報取得
        int editSid = paramMdl.getLabelEditSid();
        CmnLabelUsrDao dao = new CmnLabelUsrDao(con);
        CmnLabelUsrModel model = dao.selectOneLabel(editSid);
        if (model == null) {
            return;
        }

        //画面にセット
        paramMdl.setUsr046LabelName(model.getLabName());
        paramMdl.setUsr046bikou(model.getLabBiko());
        paramMdl.setUsr046CatCmbList(catCmbList);
    }


    /**
     * <br>[機  能] ラベルSIDからラベル情報を取得し、削除確認メッセージを返す
     * <br>[解  説] ラベルが付与されているユーザ情報が存在する場合は、そのユーザ情報数を表示
     * <br>[備  考]
     * @param con コネクション
     * @param labSid ラベルSID
     * @param msgRes MessageResources
     * @return String 削除確認メッセージ
     * @throws SQLException SQL実行例外
     */
    public String getDeletePosMsg(Connection con, int labSid, MessageResources msgRes)
    throws SQLException {

        String msg = "";

        //ラベル名取得
        CmnLabelUsrDao labDao = new CmnLabelUsrDao(con);
        CmnLabelUsrModel labMdl = labDao.selectOneLabel(labSid);
        String labName = NullDefault.getString(labMdl.getLabName(), "");
        GsMessage gsMsg = new GsMessage(reqMdl__);
        //ラベル
        String textLabel = gsMsg.getMessage("cmn.label");
        msg = msgRes.getMessage("sakujo.kakunin.list", textLabel,
                StringUtilHtml.transToHTmlPlusAmparsant(labName));

        return msg;
    }

    /**
     * <br>[機  能] ラベルが付与されているユーザの件数を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param labSid ラベルSID
     * @return int 所属する会社の件数
     * @throws SQLException SQL実行例外
     */
    public int getBelongCount(Connection con, int labSid)
    throws SQLException {

        CmnUsrmLabelDao belDao = new CmnUsrmLabelDao(con);
        return belDao.getIndCount(labSid);
    }

    /**
     * <br>[機  能] ラベルを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Usr046ParamModel
     * @throws SQLException SQL実行例外
     */
    public void deleteAlb(Connection con, Usr046ParamModel paramMdl) throws SQLException {

        int editLabSid = paramMdl.getLabelEditSid();
        boolean commitFlg = false;

        try {

            //ラベル情報を物理削除する
            CmnLabelUsrDao uslDao = new CmnLabelUsrDao(con);
            uslDao.deleteSort(editLabSid);

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
     * <br>[機  能] 登録、または更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doAddEdit(
        Usr046ParamModel paramMdl,
        Connection con,
        MlCountMtController cntCon,
        int userSid) throws SQLException {

        int procMode = paramMdl.getUsr044ProcMode();
        if (procMode == GSConstUser.PROCMODE_ADD) {
            //登録
            doInsert(paramMdl, con, cntCon, userSid);
        } else if (procMode == GSConstUser.PROCMODE_EDIT) {
            //更新
            doUpdate(paramMdl, con, userSid);
        }
    }

    /**
     * <br>[機  能] 登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doInsert(
        Usr046ParamModel paramMdl,
        Connection con,
        MlCountMtController cntCon,
        int userSid) throws SQLException {

        boolean commitFlg = false;

        try {

            con.setAutoCommit(false);

            //ラベルSID採番
            int labSid = (int) cntCon.getSaibanNumber(GSConst.SBNSID_USER,
                                                       GSConstUser.SBNSID_SUB_LABEL,
                                                       userSid);
            //登録用Model作成
            CmnLabelUsrModel cpMdl = __getUpdateModel(con, labSid, paramMdl, userSid);

            //insert
            CmnLabelUsrDao cpDao = new CmnLabelUsrDao(con);
            cpDao.insert(cpMdl);
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
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doUpdate(Usr046ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        boolean commitFlg = false;

        try {

            con.setAutoCommit(false);

            //ラベルSID
            int editLabSid = paramMdl.getLabelEditSid();

            //登録用Model作成
            CmnLabelUsrModel mdl = __getUpdateModel(con, editLabSid, paramMdl, userSid);

            //update
            CmnLabelUsrDao dao = new CmnLabelUsrDao(con);

            //カテゴリ間の移動があるか
            if (mdl.getLucSid() == paramMdl.getUsr043EditSid()) {
                dao.update(mdl);
            } else {
                dao.updateCatMove(mdl);
            }
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
     * @param con コネクション
     * @param labSid ラベルSID
     * @param paramMdl パラメータモデル
     * @param userSid ログインユーザSID
     * @return AdrLabelModel 登録・更新用Model
     * @throws SQLException SQL実行例外
     */
    private CmnLabelUsrModel __getUpdateModel(Connection con,
                                                   int labSid,
                                                   Usr046ParamModel paramMdl,
                                                   int userSid) throws SQLException {

        UDate now = new UDate();

        CmnLabelUsrModel mdl = new CmnLabelUsrModel();
        //カテゴリSID
        mdl.setLucSid(paramMdl.getUsr046CatSid());
        //ラベルSID
        mdl.setLabSid(labSid);
        //ラベル名
        mdl.setLabName(NullDefault.getString(paramMdl.getUsr046LabelName(), ""));
        //備考
        mdl.setLabBiko(NullDefault.getString(paramMdl.getUsr046bikou(), ""));
        //表示順
        CmnLabelUsrDao dao = new CmnLabelUsrDao(con);
        mdl.setLabSort(dao.getSortMax(paramMdl.getUsr046CatSid()) + 1);

        mdl.setLabAuid(userSid);
        mdl.setLabAdate(now);
        mdl.setLabEuid(userSid);
        mdl.setLabEdate(now);

        return mdl;
    }
}
