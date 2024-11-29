package jp.groupsession.v2.rng.rng140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngTemplateCategoryDao;
import jp.groupsession.v2.rng.dao.RngTemplateDao;
import jp.groupsession.v2.rng.dao.RngTemplatecategoryAdmDao;
import jp.groupsession.v2.rng.dao.RngTemplatecategoryUseDao;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.rng.model.RngTemplatecategoryAdmModel;
import jp.groupsession.v2.rng.model.RngTemplatecategoryUseModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 テンプレートカテゴリ登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng140Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng140Biz.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <p>コンストラクタ
     * @param reqMdl リクエスト情報
     */
    public Rng140Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @param cmd コマンド
     * @throws Exception 例外
     */
    public void init(Rng140ParamModel paramMdl,
                     Connection con,
                     int sessionUserSid,
                     String cmd)
                     throws Exception {

        //編集の場合、データ取得
        if (paramMdl.getRng140ProcMode() == RngConst.RNG_CMDMODE_EDIT
        && cmd.equals("addeditcategory")) {
            RngTemplateCategoryDao dao = new RngTemplateCategoryDao(con);
            RngTemplateCategoryModel model = dao.select(paramMdl.getRng140CatSid());
            paramMdl.setRng140CatName(model.getRtcName());

            //カテゴリ管理者のセット
            HashMap<String, String[]> admList = getAdmList(con, model);
            if (admList != null) {
                paramMdl.getRng140UserAdmList().setSelected(admList);
            }
            //カテゴリ使用制限のセット
            HashMap<String, String[]> useList = getUseLimitList(con, model);
            if (useList != null) {
                paramMdl.getRng140UserLimitList().setSelected(useList);
            }

            paramMdl.setRng140UserLimit(model.getRtcUseLimit());
            paramMdl.setRng140UserLimitType(model.getRtcLimitType());
        }

        GsMessage gsmsg = new GsMessage(reqMdl__);
        UserGroupSelectModel ugsAdmModel = paramMdl.getRng140UserAdmList();
        ugsAdmModel.init(con, reqMdl__,
                new String[] {RngConst.CATEGORY_ADMIN},
                new String[] {gsmsg.getMessage("cmn.admin")}, "", null, null);

        UserGroupSelectModel ugsLimitModel = paramMdl.getRng140UserLimitList();
        ugsLimitModel.init(con, reqMdl__,
                new String[] {RngConst.CATEGORY_USE_LIMIT},
                new String[] {gsmsg.getMessage("cmn.use") + gsmsg.getMessage("anp.syusya.no"), },
                "", null, null);

    }

    /**
     * <br>[機  能]DBからカテゴリの管理者を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param model 稟議カテゴリモデル
     * @return カテゴリ管理者
     * @throws SQLException SQL実行時例外
     */
    private HashMap<String, String[]> getAdmList(Connection con,
            RngTemplateCategoryModel model) throws SQLException {
        //稟議カテゴリーの管理者設定取得
        RngTemplatecategoryAdmDao admDao = new RngTemplatecategoryAdmDao(con);
        ArrayList<RngTemplatecategoryAdmModel> admMdlList = admDao.select(model.getRtcSid());

        if (admMdlList == null) {
            return null;
        }


        //グループとユーザのSIDを判別して取得
        String[] admUsrGrpList = new String[admMdlList.size()];
        int admIndex = 0;
        for (RngTemplatecategoryAdmModel adm:admMdlList) {
            if (adm.getGrpSid() == -1) {
                admUsrGrpList[admIndex] = String.valueOf(adm.getUsrSid());
            } else {
                admUsrGrpList[admIndex] = String.valueOf("G" + adm.getGrpSid());
            }
            admIndex++;
        }

        HashMap<String, String[]> admList = new HashMap<String, String[]>();
        admList.put(RngConst.CATEGORY_ADMIN, admUsrGrpList);
        return admList;
    }

    /**
     * <br>[機  能]DBからカテゴリの使用制限を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param model 稟議カテゴリモデル
     * @return カテゴリ管理者
     * @throws SQLException SQL実行時例外
     */
    private HashMap<String, String[]> getUseLimitList(Connection con,
            RngTemplateCategoryModel model) throws SQLException {
        //稟議カテゴリーの使用権限登録
        RngTemplatecategoryUseDao useDao = new RngTemplatecategoryUseDao(con);
        ArrayList<RngTemplatecategoryUseModel> useMdlList = useDao.select(model.getRtcSid());

        if (useMdlList == null) {
            return null;
        }

        //グループとユーザのSIDを判別して取得
        String[] useUsrGrpList = new String[useMdlList.size()];
        int useIndex = 0;
        for (RngTemplatecategoryUseModel use:useMdlList) {
            if (use.getGrpSid() == -1) {
                useUsrGrpList[useIndex] = String.valueOf(use.getUsrSid());
            } else {
                useUsrGrpList[useIndex] = String.valueOf("G" + use.getGrpSid());
            }
            useIndex++;
        }

        HashMap<String, String[]> useList = new HashMap<String, String[]>();
        useList.put(RngConst.CATEGORY_USE_LIMIT, useUsrGrpList);
        return useList;
    }

    /**
     * <br>[機  能] カテゴリSIDからテンプレート情報一覧を取得し、一覧のlistを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUserSid セッションユーザSID
     * @return ArrayList テンプレートリスト
     * @throws Exception 例外
     */
    public ArrayList<String> getDeleteTmpList(Rng140ParamModel paramMdl,
                                 Connection con,
                                 int sessionUserSid)
                                 throws Exception {

        //テンプレート一覧取得
        RngTemplateDao dao = new RngTemplateDao(con);
        int kbn = paramMdl.getRngTemplateMode();
        int catSid = 0;
        if (kbn == RngConst.RNG_TEMPLATE_SHARE) {
            catSid = paramMdl.getRng140CatSid();
        } else if (kbn == RngConst.RNG_TEMPLATE_PRIVATE) {
            catSid = paramMdl.getRng140CatSid();
        }
        ArrayList<RngTemplateModel> list = dao.selectTplList(kbn, sessionUserSid, catSid, reqMdl__);

        ArrayList<String> tmpList = new ArrayList<String>();
        for (RngTemplateModel model : list) {
            tmpList.add(model.getRtpTitle());
        }
        return tmpList;
    }

    /**
     * <br>[機  能] カテゴリSIDからカテゴリ名を取得し、テンプレート一覧を追加したメッセージを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param catSid ラベルSID
     * @param msgRes MessageResources
     * @param delMsg html表示用ラベル一覧
     * @return String 削除確認メッセージ
     * @throws SQLException SQL実行例外
     */
    public String getDeleteTmpAndCatMsg(Connection con,
                                        int catSid,
                                        MessageResources msgRes,
                                        String delMsg)
                                        throws SQLException {

        String msg = "";

        //カテゴリ名取得
        RngTemplateCategoryDao catDao = new RngTemplateCategoryDao(con);
        RngTemplateCategoryModel catMdl = catDao.select(catSid);
        String catName = NullDefault.getString(catMdl.getRtcName(), "");

        msg = msgRes.getMessage("error.ringitmp.category", catName, delMsg);

        return msg;
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

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String category = gsMsg.getMessage("cmn.category");

        //カテゴリ名取得
        RngTemplateCategoryDao catDao = new RngTemplateCategoryDao(con);
        RngTemplateCategoryModel catMdl = catDao.select(catSid);
        String catName = NullDefault.getString(catMdl.getRtcName(), "");

        msg = msgRes.getMessage("sakujo.kakunin.list", category,
                StringUtilHtml.transToHTmlPlusAmparsant(catName));

        return msg;
    }

    /**
     * <br>[機  能] 稟議テンプレートカテゴリの削除を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param rtcSid 稟議テンプレートカテゴリSID
     * @param usrSid ユーザSID
     * @param con Connection
     * @throws Exception 例外
     */
    public void deleteTplCat(Rng140ParamModel paramMdl,
                             int rtcSid, int usrSid, Connection con) throws Exception {

        RngTemplateCategoryDao rtcDao = new RngTemplateCategoryDao(con);
        RngTemplatecategoryAdmDao admDao = new RngTemplatecategoryAdmDao(con);
        RngTemplatecategoryUseDao useDao = new RngTemplatecategoryUseDao(con);
        UDate now = new UDate();

        int sort = rtcDao.getSort(rtcSid);

        //ソート順の更新を行う
        rtcDao.updateSortAll(paramMdl.getRngTemplateMode(), usrSid, now, sort);
        //カテゴリの削除
        rtcDao.delete(rtcSid);
        //カテゴリ管理者情報の削除
        admDao.delete(rtcSid);
        //カテゴリ使用制限情報の削除
        useDao.delete(rtcSid);

    }

}
