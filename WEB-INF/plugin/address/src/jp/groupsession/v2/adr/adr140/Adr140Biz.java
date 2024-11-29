package jp.groupsession.v2.adr.adr140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrBelongLabelDao;
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
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] アドレス帳 ラベル登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr140Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr140Biz.class);

    /** 確認画面からの遷移 */
    private static final int KAKUNIN_BACK = 1;

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

//    /**
//     * <br>[機  能] デフォルトコンストラクタ
//     * <br>[解  説]
//     * <br>[備  考]
//     */
//    public Adr140Biz() {
//    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr140Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr140ParamModel
     * @param kbn 遷移元画面
     * @throws SQLException SQL実行例外
     */
    public void getInitData(
            Connection con, Adr140ParamModel paramMdl, int kbn) throws SQLException {

        //カテゴリコンボ作成
        ArrayList<LabelValueBean> adrCatList = new ArrayList<LabelValueBean>();
        AdrLabelCategoryDao catDao = new AdrLabelCategoryDao(con);
        List<AdrLabelCategoryModel> catMdlList = catDao.select();
        for (AdrLabelCategoryModel mdl : catMdlList) {
            String catName = mdl.getAlcName();
            String catSid = String.valueOf(mdl.getAlcSid());
            adrCatList.add(new LabelValueBean(catName, catSid));
        }

        //新規作成か編集か
        if (paramMdl.getAdr130ProcMode() == GSConstAddress.PROCMODE_ADD) {
            paramMdl.setAdr140catList(adrCatList);
            //遷移元が確認画面か
            if (kbn == KAKUNIN_BACK) {
                return;
            }
            paramMdl.setAdr140CatSid(paramMdl.getAdr280EditSid());
            return;
        } else if (paramMdl.getAdr130ProcMode() == GSConstAddress.PROCMODE_EDIT) {
            paramMdl.setAdr140catList(adrCatList);
            //遷移元が確認画面か
            if (kbn == KAKUNIN_BACK) {
                return;
            }
            paramMdl.setAdr140CatSid(paramMdl.getAdr280EditSid());
        }

        //ラベル情報取得
        int editAlbSid = paramMdl.getAdr130EditAlbSid();
        AdrLabelDao albDao = new AdrLabelDao(con);
        AdrLabelModel albMdl = albDao.select(editAlbSid);
        if (albMdl == null) {
            return;
        }

        //ラベル情報を画面にセット
        paramMdl.setAdr140albName(albMdl.getAlbName());
        paramMdl.setAdr140bikou(albMdl.getAlbBiko());
        paramMdl.setAdr140catList(adrCatList);
    }


    /**
     * <br>[機  能] ラベルSIDからラベル情報を取得し、削除確認メッセージを返す
     * <br>[解  説] ラベルが付与されているアドレス帳が存在する場合は、そのアドレス帳数を表示
     * <br>[備  考]
     * @param con コネクション
     * @param albSid ラベルSID
     * @param msgRes MessageResources
     * @return String 削除確認メッセージ
     * @throws SQLException SQL実行例外
     */
    public String getDeletePosMsg(Connection con, int albSid, MessageResources msgRes)
    throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl_);
        String msg = "";

        //ラベル名取得
        AdrLabelDao albDao = new AdrLabelDao(con);
        AdrLabelModel albMdl = albDao.select(albSid);
        String albName = NullDefault.getString(albMdl.getAlbName(), "");
        msg = msgRes.getMessage("sakujo.kakunin.list",
                gsMsg.getMessage("cmn.label"), StringUtilHtml.transToHTmlPlusAmparsant(albName));

        return msg;
    }

    /**
     * <br>[機  能] ラベルが付与されているアドレス帳の件数を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param albSid ラベルSID
     * @return int 所属する会社の件数
     * @throws SQLException SQL実行例外
     */
    public int getBelongCount(Connection con, int albSid)
    throws SQLException {

        AdrBelongLabelDao ablDao = new AdrBelongLabelDao(con);
        return ablDao.getIndCount(albSid);
    }

    /**
     * <br>[機  能] ラベルを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr140ParamModel
     * @throws SQLException SQL実行例外
     */
    public void deleteAlb(Connection con, Adr140ParamModel paramMdl) throws SQLException {

        int editAlbSid = paramMdl.getAdr130EditAlbSid();
        boolean commitFlg = false;

        try {

            //ラベル情報を物理削除する
            AdrLabelDao albDao = new AdrLabelDao(con);
            albDao.delete(editAlbSid);

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
     * @param paramMdl Adr140ParamModel
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doAddEdit(
        Adr140ParamModel paramMdl,
        Connection con,
        MlCountMtController cntCon,
        int userSid) throws SQLException {

        int procMode = paramMdl.getAdr130ProcMode();
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
     * @param paramMdl Adr140ParamModel
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doInsert(
        Adr140ParamModel paramMdl,
        Connection con,
        MlCountMtController cntCon,
        int userSid) throws SQLException {

        boolean commitFlg = false;

        try {

            con.setAutoCommit(false);

            //ラベルSID採番
            int albSid = (int) cntCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                                       GSConstAddress.SBNSID_SUB_LABEL,
                                                       userSid);
            //登録用Model作成
            AdrLabelModel cpMdl = __getUpdateModel(con, albSid, paramMdl, userSid);

            //insert
            AdrLabelDao cpDao = new AdrLabelDao(con);
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
     * @param paramMdl Adr140ParamModel
     * @param con コネクション
     * @param userSid ログインユーザSID
     * @throws SQLException SQL実行例外
     */
    public void doUpdate(Adr140ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {

        boolean commitFlg = false;

        try {

            con.setAutoCommit(false);

            //ラベルSID
            int editAlbSid = paramMdl.getAdr130EditAlbSid();

            //登録用Model作成
            AdrLabelModel mdl = __getUpdateModel(con, editAlbSid, paramMdl, userSid);

            //update
            AdrLabelDao dao = new AdrLabelDao(con);
            //カテゴリ間の移動があるか
            if (paramMdl.getAdr280EditSid() == paramMdl.getAdr140CatSid()) {
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
     * @param albSid ラベルSID
     * @param paramMdl Adr140ParamModel
     * @param userSid ログインユーザSID
     * @return AdrLabelModel 登録・更新用Model
     * @throws SQLException SQL実行例外
     */
    private AdrLabelModel __getUpdateModel(Connection con,
                                                   int albSid,
                                                   Adr140ParamModel paramMdl,
                                                   int userSid) throws SQLException {

        UDate now = new UDate();

        AdrLabelModel mdl = new AdrLabelModel();
        //ラベルSID
        mdl.setAlbSid(albSid);
        //ラベル名
        mdl.setAlbName(NullDefault.getString(paramMdl.getAdr140albName(), ""));
        //備考
        mdl.setAlbBiko(NullDefault.getString(paramMdl.getAdr140bikou(), ""));
        //表示順
        AdrLabelDao dao = new AdrLabelDao(con);
        mdl.setAlbSort(dao.getSortMax(paramMdl.getAdr140CatSid()) + 1);
        //カテゴリSID
        mdl.setAlcSid(paramMdl.getAdr140CatSid());

        mdl.setAlbAuid(userSid);
        mdl.setAlbAdate(now);
        mdl.setAlbEuid(userSid);
        mdl.setAlbEdate(now);

        return mdl;
    }
}
