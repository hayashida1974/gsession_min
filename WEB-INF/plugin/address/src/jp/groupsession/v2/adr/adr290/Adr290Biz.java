package jp.groupsession.v2.adr.adr290;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrLabelCategoryDao;
import jp.groupsession.v2.adr.dao.AdrLabelDao;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] アドレス帳 カテゴリ登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr290Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr290Biz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr290Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }
    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr290ParamModel
     * @throws SQLException SQL実行例外
     */
    public void getInitData(Connection con, Adr290ParamModel paramMdl) throws SQLException {

        if (paramMdl.getAdr280ProcMode() == GSConstAddress.PROCMODE_ADD) {
            return;
        }
        //編集の場合、カテゴリ情報取得
        int editSid = paramMdl.getAdr280EditSid();
        AdrLabelCategoryDao dao = new AdrLabelCategoryDao(con);
        AdrLabelCategoryModel model = dao.select(editSid);
        if (model == null) {
            return;
        }
        //カテゴリ情報を画面にセット
        paramMdl.setAdr290CategoryName(model.getAlcName());
        paramMdl.setAdr290bikou(model.getAlcBiko());
    }


    /**
     * <br>[機  能] カテゴリSIDからカテゴリを取得し、メッセージを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param catSid ラベルSID
     * @param msgRes MessageResources
     * @return String 削除確認メッセージ
     * @throws SQLException SQL実行例外
     */
    public String getDeletePosMsg(Connection con, int catSid, MessageResources msgRes)
    throws SQLException {

        String msg = "";
        GsMessage gsMsg = new GsMessage(reqMdl_);
        //カテゴリ名取得
        AdrLabelCategoryDao catDao = new AdrLabelCategoryDao(con);
        AdrLabelCategoryModel catMdl = catDao.select(catSid);
        String catName = NullDefault.getString(catMdl.getAlcName(), "");

        msg = msgRes.getMessage("sakujo.kakunin.list",
                gsMsg.getMessage("cmn.category"),
                StringUtilHtml.transToHTmlPlusAmparsant(catName));

        return msg;
    }

    /**
     * <br>[機  能] カテゴリSIDからカテゴリ名を取得し、ラベル一覧を追加したメッセージを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param catSid ラベルSID
     * @param msgRes MessageResources
     * @param delMsg html表示用ラベル一覧
     * @return String 削除確認メッセージ
     * @throws SQLException SQL実行例外
     */
    public String getDeleteLabAndCatMsg(Connection con,
                                        int catSid,
                                        MessageResources msgRes,
                                        String delMsg)
                                        throws SQLException {

        String msg = "";

        //カテゴリ名取得
        AdrLabelCategoryDao catDao = new AdrLabelCategoryDao(con);
        AdrLabelCategoryModel catMdl = catDao.select(catSid);
        String catName = NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(catMdl.getAlcName()), "");

        msg = msgRes.getMessage("error.usercategory.label", catName, delMsg);

        return msg;
    }

    /**
     * <br>[機  能] カテゴリSIDからラベル情報一覧を取得し、一覧のlistを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param catSid カテゴリSID
     * @return String 削除確認メッセージ
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getDeleteLabList(Connection con, int catSid)
    throws SQLException {

        //ラベル一覧取得
        AdrLabelDao dao = new AdrLabelDao(con);
        ArrayList<String> labNameList = new ArrayList<String>();
        List<AdrLabelModel> modelList = dao.getLabelInCategory(catSid);
        for (AdrLabelModel model : modelList) {
            labNameList.add(model.getAlbName());
        }

        return labNameList;
    }

    /**
     * <br>[機  能] カテゴリSIDからラベル情報一覧を取得し、
     * <br>[機  能] ラベルがアドレス帳に付加されているか判断
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param catSid カテゴリSID
     * @return boolean true=ラベル付加あり false=ラベル付加なし
     * @throws SQLException SQL実行例外
    public ArrayList<Integer> getBelongLabList(Connection con, int catSid)
    throws SQLException {

        //カテゴリ内ラベルSID一覧取得
        AdrBelongLabelDao dao = new AdrBelongLabelDao(con);
        ArrayList<Integer> labSidList = new ArrayList<Integer>();
        ArrayList<AdrLabelModel> modelList = dao.select(catSid);
        for (CmnLabelUsrModel model : modelList) {
            labSidList.add(model.getLabSid());
        }

        return labSidList;
    }
     */

    /**
     * <br>[機  能] カテゴリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl  Adr290ParamModel
     * @throws SQLException SQL実行例外
     */
    public void deleteCat(Connection con, Adr290ParamModel paramMdl)
                                               throws SQLException {

        int editCatSid = paramMdl.getAdr280EditSid();
        int kbn = paramMdl.getCatKbn();
        boolean commitFlg = false;

        try {

            //ラベル情報を物理削除する
            AdrLabelCategoryDao dao = new AdrLabelCategoryDao(con);

            //カテゴリの削除
            dao.delete(editCatSid);

            if (kbn == GSConstAddress.CATEGORY_EXIST_YES) {
                //カテゴリ内にラベルが存在する場合
                //削除されたカテゴリ内のラベルをカテゴリ「未選択」へ移動
                moveLabelToNotCategory(con, editCatSid);
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

        //カテゴリー開閉フラグの設定フラグを未設定状態にする
        paramMdl.setAdr010CategorySetInitFlg(0);
    }

    /**
     * <br>[機  能] 削除されたカテゴリ内のラベルをカテゴリ「未選択」へ移動する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param catSid カテゴリSID
     * @throws SQLException SQL実行例外
     */
    public void moveLabelToNotCategory(Connection con, int catSid)
    throws SQLException {

        AdrLabelDao dao = new AdrLabelDao(con);

        //カテゴリ「未選択」へ移動するラベルデータを取得
        List<AdrLabelModel> labelDataList = dao.getLabelInCategory(catSid);

        //「未選択」カテゴリの表示順の最大数を取得
        int sortMaxValue = dao.getSortMax(GSConstAddress.LABEL_CATEGORY_NOSET);

        for (AdrLabelModel lblMdl : labelDataList) {
            sortMaxValue++;
            dao.deleteCatAndLab(lblMdl, sortMaxValue);
        }
    }
    
    /**
     * <br>[機  能] 登録、または更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr290ParamModel
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doAddEdit(
        Adr290ParamModel paramMdl,
        Connection con,
        MlCountMtController cntCon,
        int userSid) throws SQLException {

        int procMode = paramMdl.getAdr280ProcMode();
        if (procMode == GSConstAddress.PROCMODE_ADD) {
            //登録
            doInsert(paramMdl, con, cntCon, userSid);
        } else if (procMode == GSConstAddress.PROCMODE_EDIT) {
            //更新
            doUpdate(paramMdl, con, userSid);
        }
    }

    /**
     * <br>[機  能] 登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr290ParamModel
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doInsert(
        Adr290ParamModel paramMdl,
        Connection con,
        MlCountMtController cntCon,
        int userSid) throws SQLException {

        boolean commitFlg = false;

        try {

            con.setAutoCommit(false);

            //カテゴリSID採番
            int catSid = (int) cntCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                       GSConstAddress.SBNSID_SUB_CATEGORY,
                                                       userSid);
            //登録用Model作成
            AdrLabelCategoryModel cpMdl = __getUpdateModel(con, catSid, paramMdl, userSid);

            //insert
            AdrLabelCategoryDao cpDao = new AdrLabelCategoryDao(con);
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
     * @param paramMdl Adr290ParamModel
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doUpdate(Adr290ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        boolean commitFlg = false;

        try {

            con.setAutoCommit(false);

            //カテゴリSID
            int editCatSid = paramMdl.getAdr280EditSid();

            //登録用Model作成
            AdrLabelCategoryModel mdl = __getUpdateModel(con, editCatSid, paramMdl, userSid);

            //update
            AdrLabelCategoryDao dao = new AdrLabelCategoryDao(con);
            dao.update(mdl);
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
     * @param catSid カテゴリSID
     * @param paramMdl Adr290ParamModel
     * @param userSid ログインユーザSID
     * @return AdrLabelCategoryModel 登録・更新用Model
     * @throws SQLException SQL実行例外
     */
    private AdrLabelCategoryModel __getUpdateModel(Connection con,
                                                   int catSid,
                                                   Adr290ParamModel paramMdl,
                                                   int userSid) throws SQLException {

        UDate now = new UDate();

        AdrLabelCategoryModel mdl = new AdrLabelCategoryModel();
        //カテゴリSID
        mdl.setAlcSid(catSid);
        //カテゴリ名
        mdl.setAlcName(NullDefault.getString(paramMdl.getAdr290CategoryName(), ""));
        //備考
        mdl.setAlcBiko(NullDefault.getString(paramMdl.getAdr290bikou(), ""));
        //表示順
        AdrLabelCategoryDao dao = new AdrLabelCategoryDao(con);
        mdl.setAlcSort(dao.getSortMax() + 1);

        mdl.setAlcAuid(userSid);
        mdl.setAlcAdate(now);
        mdl.setAlcEuid(userSid);
        mdl.setAlcEdate(now);

        return mdl;
    }
}
