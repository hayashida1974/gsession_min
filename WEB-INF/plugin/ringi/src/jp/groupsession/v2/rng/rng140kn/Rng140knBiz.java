package jp.groupsession.v2.rng.rng140kn;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngTemplateCategoryDao;
import jp.groupsession.v2.rng.dao.RngTemplatecategoryAdmDao;
import jp.groupsession.v2.rng.dao.RngTemplatecategoryUseDao;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.model.RngTemplatecategoryAdmModel;
import jp.groupsession.v2.rng.model.RngTemplatecategoryUseModel;

/**
 * <br>[機  能] 稟議 テンプレートカテゴリ登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng140knBiz {

    /**
     * <br>[機  能] 登録処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @throws Exception 例外
     */
    public void insert(Rng140knParamModel paramMdl,
                       Connection con,
                       int usrSid,
                       MlCountMtController cntCon)
                       throws Exception {

        //稟議カテゴリーの登録
        RngTemplateCategoryDao dao = new RngTemplateCategoryDao(con);
        RngTemplateCategoryModel insertMdl = createCategoryModel(paramMdl, con, usrSid, cntCon);
        dao.insert(insertMdl);

        //個人テンプレートの場合は管理者設定等を行わない
        if (paramMdl.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
            return;
        }

        //稟議カテゴリーの管理者設定登録
        RngTemplatecategoryAdmDao admDao = new RngTemplatecategoryAdmDao(con);
        ArrayList<RngTemplatecategoryAdmModel> admMdlList
        = createCategoryAdmMdl(paramMdl, insertMdl);
        if (admMdlList != null) {
            for (RngTemplatecategoryAdmModel admMdl:admMdlList) {
                admDao.insert(admMdl);
            }
        }

        //稟議カテゴリーの使用権限登録
        if (paramMdl.getRng140UserLimit() == RngConst.LIMIT_USE) {
            RngTemplatecategoryUseDao useDao = new RngTemplatecategoryUseDao(con);
            //稟議カテゴリーの使用権限登録
            ArrayList<RngTemplatecategoryUseModel> useMdlList =
            createCategoryUseMdl(paramMdl, insertMdl);
            if (useMdlList != null) {
                for (RngTemplatecategoryUseModel useMdl:useMdlList) {
                    useDao.insert(useMdl);
                }
            }
        }
    }

    /**
     * <br>[機  能] 更新処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @throws Exception 例外
     */
    public void update(Rng140knParamModel paramMdl,
                       Connection con,
                       int usrSid,
                       MlCountMtController cntCon)
                       throws Exception {

        RngTemplateCategoryDao dao = new RngTemplateCategoryDao(con);

        RngTemplateCategoryModel updateMdl = createCategoryModel(paramMdl, con, usrSid, cntCon);
        dao.update(updateMdl);

        //個人テンプレートの場合は管理者設定等を行わない
        if (paramMdl.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
            return;
        }

        //一度カテゴリ管理者をすべて削除する
        RngTemplatecategoryAdmDao admDao = new RngTemplatecategoryAdmDao(con);
        admDao.delete(updateMdl.getRtcSid());
        //稟議カテゴリーの管理者設定登録
        ArrayList<RngTemplatecategoryAdmModel> admMdlList
        = createCategoryAdmMdl(paramMdl, updateMdl);

        if (admMdlList != null) {
            for (RngTemplatecategoryAdmModel admMdl:admMdlList) {
                admDao.insert(admMdl);
            }
        }

        //一度カテゴリ使用制限をすべて削除する
        RngTemplatecategoryUseDao useDao = new RngTemplatecategoryUseDao(con);
        useDao.delete(updateMdl.getRtcSid());


        if (paramMdl.getRng140UserLimit() == RngConst.LIMIT_USE) {
            //稟議カテゴリーの使用権限登録
            ArrayList<RngTemplatecategoryUseModel> useMdlList =
            createCategoryUseMdl(paramMdl, updateMdl);
            if (useMdlList != null) {
                for (RngTemplatecategoryUseModel useMdl:useMdlList) {
                    useDao.insert(useMdl);
                }
            }
        }

    }

    /**
     * <br>[機  能] 登録・更新用のmodel作成を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param usrSid ユーザSID
     * @param cntCon MlCountMtController
     * @return RngTemplateCategoryModel 登録model
     * @throws Exception 例外
     */
    public RngTemplateCategoryModel createCategoryModel(Rng140knParamModel paramMdl,
                                                Connection con,
                                                int usrSid,
                                                MlCountMtController cntCon)
                                                throws Exception {

        RngTemplateCategoryDao dao = new RngTemplateCategoryDao(con);
        RngTemplateCategoryModel model = new RngTemplateCategoryModel();
        UDate date = new UDate();

        //登録
        if (paramMdl.getRng140ProcMode() == RngConst.RNG_CMDMODE_ADD) {
            //カテゴリSID採番
            int catSid = (int) cntCon.getSaibanNumber(RngConst.SBNSID_RINGI,
                                                       RngConst.SBNSID_SUB_RINGI_TEMPLATE_CATEGORY,
                                                       usrSid);
            model.setRtcSid(catSid);

            //ソートの最大値+1
            if (paramMdl.getRngTemplateMode() == RngConst.RNG_TEMPLATE_SHARE) {
                model.setRtcSort(dao.getMaxSortShare() + 1);
            } else if (paramMdl.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
                model.setRtcSort(dao.getMaxSortPrivate(usrSid) + 1);
            }
        //更新
        } else if (paramMdl.getRng140ProcMode() == RngConst.RNG_CMDMODE_EDIT) {
            model.setRtcSid(paramMdl.getRng140CatSid());
        }

        model.setRtcType(paramMdl.getRngTemplateMode());
        if (paramMdl.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
            model.setUsrSid(usrSid);
        }
        model.setRtcName(paramMdl.getRng140CatName());
        model.setRtcUseLimit(paramMdl.getRng140UserLimit());
        model.setRtcLimitType(paramMdl.getRng140UserLimitType());
        model.setRtcAdate(date);
        model.setRtcEdate(date);
        model.setRtcAuid(usrSid);
        model.setRtcEuid(usrSid);


        return model;
    }

    /**
     *
     * <br>[機  能]稟議カテゴリ管理者モデルのリストを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rng140knParamModel
     * @param insertMdl カテゴリモデル
     *@return 稟議カテゴリ管理者モデルのリスト
     */
    public ArrayList<RngTemplatecategoryAdmModel> createCategoryAdmMdl(
            Rng140knParamModel paramMdl,
            RngTemplateCategoryModel insertMdl) {

        //管理者に選択済みのユーザ・グループSIDを取得
        UserGroupSelectModel admSelectMdl = paramMdl.getRng140UserAdmList();
        HashMap<String, String[]> admListMap = admSelectMdl.getSelected();
        String[] admList = admListMap.get(RngConst.CATEGORY_ADMIN);

        if (admList == null) {
            return null;
        }

        ArrayList<RngTemplatecategoryAdmModel> admMdlList
        = new ArrayList<RngTemplatecategoryAdmModel>();

        //カテゴリ管理者モデルリストを作成
        for (String admSidStr:admList) {
            RngTemplatecategoryAdmModel admMdl =  new RngTemplatecategoryAdmModel();

            admMdl.setRtcSid(insertMdl.getRtcSid());

            if (admSidStr.startsWith("G")) {
                int admSid = NullDefault.getInt(admSidStr.substring(1), -1);
                admMdl.setGrpSid(admSid);
                admMdl.setUsrSid(-1);
            } else {
                int admSid = NullDefault.getInt(admSidStr, -1);
                admMdl.setUsrSid(admSid);
                admMdl.setGrpSid(-1);
            }
            admMdlList.add(admMdl);

        }

        return admMdlList;
    }

    /**
    *
    * <br>[機  能]稟議カテゴリ使用者モデルのリストを作成する
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl Rng140knParamModel
    * @param insertMdl カテゴリモデル
    *@return 稟議カテゴリ使用者モデルのリスト
    */
   public ArrayList<RngTemplatecategoryUseModel> createCategoryUseMdl(
           Rng140knParamModel paramMdl,
           RngTemplateCategoryModel insertMdl) {

       //使用制限対象ユーザ・グループのSIDを取得
       UserGroupSelectModel useSelectMdl = paramMdl.getRng140UserLimitList();
       HashMap<String, String[]> useListMap = useSelectMdl.getSelected();
       String[] useList = useListMap.get(RngConst.CATEGORY_USE_LIMIT);

       if (useList == null) {
           return null;
       }

       ArrayList<RngTemplatecategoryUseModel> useMdlList
       = new ArrayList<RngTemplatecategoryUseModel>();

       //カテゴリ管理者モデルリストを作成
       for (String useSidStr:useList) {
           RngTemplatecategoryUseModel useMdl =  new RngTemplatecategoryUseModel();

           useMdl.setRtcSid(insertMdl.getRtcSid());
           if (useSidStr.startsWith("G")) {
               int useSid = NullDefault.getInt(useSidStr.substring(1), -1);
               useMdl.setGrpSid(useSid);
               useMdl.setUsrSid(-1);
           } else {
               int useSid = NullDefault.getInt(useSidStr, -1);
               useMdl.setUsrSid(useSid);
               useMdl.setGrpSid(-1);
           }
           useMdlList.add(useMdl);

       }

       return useMdlList;
   }

}
