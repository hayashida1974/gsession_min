package jp.groupsession.v2.rng.rng110keiro;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.lang.ArrayUtils;

import jp.co.sjts.util.EnumUtil;
import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroConditionDao;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao.ISearchParam;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao.SearchParamForRCT;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao.SearchParamForRTP;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroUserDao;
import jp.groupsession.v2.rng.model.RngTemplateKeiroConditionModel;
import jp.groupsession.v2.rng.model.RngTemplateKeiroModel;
import jp.groupsession.v2.rng.model.RngTemplateKeiroUserModel;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm.TargetPosSel;
/**
 *
 * <br>[機  能] 経路テンプレートの保管と読み込み処理を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngTemplateKeiroSave {
    /**採番コントローラ*/
    private MlCountMtController cntCon__;
    /**リクエストモデル*/
    private RequestModel reqMdl__;
    /**経路テンプレート経路ステップ情報 DAO*/
    private RngTemplateKeiroDao stepDao__;
    /**経路テンプレート経路条件情報 DAO*/
    private RngTemplateKeiroConditionDao inCondDao__;
    /**経路テンプレートユーザ選択情報 DAO*/
    private RngTemplateKeiroUserDao keiroUsrDao__;
    /**コネクション*/
    private Connection con__;

    /** 審議者経路設定 保管対象 かつ ロード後格納箇所*/
    private TreeMap<Integer, Rng110KeiroDialogParamModel> keiro__;
    /** 確認者経路設定 保管対象 かつ ロード後格納箇所*/
    private TreeMap<Integer, Rng110KeiroDialogParamModel> finalKeiro__;
    /** 検索用オブジェクト*/
    ISearchParam searchParam__;



    /** 経路区分 Utilクラス*/
    private EnumUtil<EnumKeiroKbn> keiroKbnUtil__ = new EnumUtil<EnumKeiroKbn>(EnumKeiroKbn.class);
    /** 経路区分 Utilクラス*/
    private EnumUtil<EnumKeiroInConditionKbn> inCondKbnUtil__ =
            new EnumUtil<EnumKeiroInConditionKbn>(EnumKeiroInConditionKbn.class);

    /**
     * コンストラクタを非公開
     */
    private RngTemplateKeiroSave() {
    }

    /**
     * イニシャライザ稟議経路テンプレート登録画面用
     * @param rctSid テンプレートSID
     * @param usrSid ユーザSID
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return RngTemplateKeiroSave
     */
    public static RngTemplateKeiroSave createInstanceForRCT(int rctSid, int usrSid,
             RequestModel reqMdl, Connection con) {
        RngTemplateKeiroSave ret = new RngTemplateKeiroSave();

        ret.searchParam__ = new SearchParamForRCT(rctSid, usrSid);
        ret.reqMdl__ = reqMdl;
        ret.con__ = con;
        ret.__daoInit();


        return ret;
    }

    /**
     * イニシャライザ稟議テンプレート登録画面用
     * @param rtpSid テンプレートSID
     * @param rtpVer テンプレートバージョン
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return RngTemplateKeiroSave
     */
    public static RngTemplateKeiroSave createInstanceForRTP(int rtpSid, int rtpVer,
            RequestModel reqMdl, Connection con) {
        RngTemplateKeiroSave ret = new RngTemplateKeiroSave();

        ret.searchParam__ = new SearchParamForRTP(rtpSid, rtpVer);
        ret.reqMdl__ = reqMdl;
        ret.con__ = con;
        ret.__daoInit();

        return ret;
    }
    /**
     *
     * <br>[機  能] イニシャライザ
     * <br>[解  説] convertParamModelメソッドのみ使用する場合用
     * <br>[備  考] load,saveメソッドは実行不可です
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return RngTemplateKeiroSave
     * @return
     */
    public static RngTemplateKeiroSave createInstanceForConvert(
            RequestModel reqMdl, Connection con) {
        RngTemplateKeiroSave ret = new RngTemplateKeiroSave();
        ret.reqMdl__ = reqMdl;
        ret.con__ = con;
        ret.__daoInit();
        return ret;
    }


    /**
     * <p>keiro を取得します。
     * @return keiro
     */
    public TreeMap<Integer, Rng110KeiroDialogParamModel> getKeiro() {
        return keiro__;
    }

    /**
     * <p>keiro をセットします。
     * @param keiro keiro
     */
    public void setKeiro(TreeMap<Integer, Rng110KeiroDialogParamModel> keiro) {
        keiro__ = keiro;
    }

    /**
     * <p>finalKeiro を取得します。
     * @return finalKeiro
     */
    public TreeMap<Integer, Rng110KeiroDialogParamModel> getFinalKeiro() {
        return finalKeiro__;
    }

    /**
     * <p>finalKeiro をセットします。
     * @param finalKeiro finalKeiro
     */
    public void setFinalKeiro(
            TreeMap<Integer, Rng110KeiroDialogParamModel> finalKeiro) {
        finalKeiro__ = finalKeiro;
    }

    /**
     *
     * <br>[機  能] 必要なDAOクラスの初期化
     * <br>[解  説]
     * <br>[備  考]
     */
    private void __daoInit() {
        stepDao__ = new RngTemplateKeiroDao(con__);
        inCondDao__ = new RngTemplateKeiroConditionDao(con__);
        keiroUsrDao__ = new RngTemplateKeiroUserDao(con__);

    }
    /**
    *
    * <br>[機  能] 保管処理
    * <br>[解  説]
    * <br>[備  考]
    * @param keiro 経路設定フォームオブジェクト
    * @param doDelete 既存データの削除
    * @param cntCon 採番コントローラ
    * @throws SQLException SQL実行時例外
    */
   public void save(Rng110Keiro keiro, boolean doDelete,
           MlCountMtController cntCon) throws SQLException {
       keiro__ = keiro.getKeiroMap();
       finalKeiro__ = keiro.getFinalKeiroMap();
       save(doDelete, cntCon);
   }
    /**
     *
     * <br>[機  能] 保管処理
     * <br>[解  説]
     * <br>[備  考]
     * @param doDelete 既存データの削除
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    public void save(boolean doDelete, MlCountMtController cntCon) throws SQLException {
        if (searchParam__ == null) {
            return;
        }
        cntCon__ = cntCon;

        //既存データの削除
        if (doDelete) {
            delete();
        }
        if (keiro__ != null) {
            int idx = 0;
            for (Entry<Integer, Rng110KeiroDialogParamModel> entry : keiro__.entrySet()) {
                Rng110KeiroDialogParamModel model = entry.getValue();
                __saveStep(model, idx, RngConst.RNG_RNCTYPE_APPR);
                idx++;
            }
            idx = 0;
        }
        if (finalKeiro__ != null) {
            int idx = 0;
            for (Entry<Integer, Rng110KeiroDialogParamModel> entry : finalKeiro__.entrySet()) {
                Rng110KeiroDialogParamModel model = entry.getValue();
                __saveStep(model, idx, RngConst.RNG_RNCTYPE_CONFIRM);
                idx++;
            }
        }
    }
    /**
    *
    * <br>[機  能] 経路情報のロード
    * <br>[解  説]
    * <br>[備  考]
     * @throws SQLException SQL実行時例外
     * @return Rng110Keiro
    */
    public Rng110Keiro loadRng110Keiro() throws SQLException {
        load();
        Rng110Keiro ret = new Rng110Keiro();
        ret.setFinalKeiroMap(finalKeiro__);
        ret.setKeiroMap(keiro__);
        return ret;
    }
    /**
     *
     * <br>[機  能] 経路ステップリストから経路ステップパラメータモデルリストを生成
     * <br>[解  説]
     * <br>[備  考]
     * @param stepList 経路ステップリスト
     * @return 経路ステップパラメータモデルリスト
     * @throws SQLException SQL実行時例外
     *
     */
    public List<Rng110KeiroDialogParamModel> convertParamModel(
             List<RngTemplateKeiroModel> stepList
            ) throws SQLException {
        Map<Integer, Rng110KeiroDialogParamModel> rtkSidStepMap =
                new HashMap<Integer, Rng110KeiroDialogParamModel>();
        List<Rng110KeiroDialogParamModel> ret = new ArrayList<>();

        for (RngTemplateKeiroModel model : stepList) {
            Rng110KeiroDialogParamModel step = __createStep(model);
            rtkSidStepMap.put(model.getRtkSid(), step);
            ret.add(step);
        }
        __loadInCond(rtkSidStepMap);
        __loadKeiroSelect(rtkSidStepMap);
        return ret;

    }
    /**
    *
    * <br>[機  能] 経路ステップリストから経路ステップパラメータモデルリストを生成
    * <br>[解  説]
    * <br>[備  考]
    * @param src 経路ステップ
    * @return 経路ステップパラメータモデルリスト
    * @throws SQLException SQL実行時例外
    *
    */
   public Rng110KeiroDialogParamModel convertParamModel(
            RngTemplateKeiroModel src
           ) throws SQLException {
       Rng110KeiroDialogParamModel step = __createStep(src);
       //経路使用条件情報をロード
       List < RngTemplateKeiroConditionModel > condList =
               inCondDao__.select(src.getRtkSid());
       //経路使用条件をステップごとにマップ
       Map<Integer, List<RngTemplateKeiroConditionModel>> rtkSidInCondMap =
               new HashMap<Integer, List<RngTemplateKeiroConditionModel>>();
       for (RngTemplateKeiroConditionModel condModel : condList) {
           int rtkSid = condModel.getRtkSid();
           List<RngTemplateKeiroConditionModel> list =
                   rtkSidInCondMap.get(Integer.valueOf(rtkSid));
           if (list == null) {
               list = new ArrayList<RngTemplateKeiroConditionModel>();
           }
           list.add(condModel);
           rtkSidInCondMap.put(rtkSid, list);
       }
       for (Entry<Integer, List<RngTemplateKeiroConditionModel>> entry
               : rtkSidInCondMap.entrySet()) {
           __setInCond(step, entry.getValue());
       }

       //経路選択ユーザをロード
       List < RngTemplateKeiroUserModel > usrList =
               keiroUsrDao__.select(src.getRtkSid());
       //経路使用条件をステップごとにマップ
       Map<Integer, List <RngTemplateKeiroUserModel>> rtkSidUserMap =
               new HashMap<Integer, List<RngTemplateKeiroUserModel>>();
       for (RngTemplateKeiroUserModel userModel : usrList) {
           int rtkSid = userModel.getRtkSid();
           List<RngTemplateKeiroUserModel> list = rtkSidUserMap.get(rtkSid);
           if (list == null) {
               list = new ArrayList<RngTemplateKeiroUserModel>();
           }
           list.add(userModel);
           rtkSidUserMap.put(rtkSid, list);
       }
       for (Entry<Integer, List<RngTemplateKeiroUserModel>> entry : rtkSidUserMap.entrySet()) {
           __setUser(step, entry.getValue());
       }
       return step;

   }

    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param rtkSidStepMap rtkSid 経路ステップマップ
     * @throws SQLException SQL実行時例外
     */
    private void __loadInCond(
            Map<Integer, Rng110KeiroDialogParamModel> rtkSidStepMap) throws SQLException {
        //経路使用条件情報をロード
        List < RngTemplateKeiroConditionModel > condList =
                inCondDao__.select(stepDao__, searchParam__, RngConst.JKBN_ALIVE);
        //経路使用条件をステップごとにマップ
        Map<Integer, List<RngTemplateKeiroConditionModel>> rtkSidInCondMap =
                new HashMap<Integer, List<RngTemplateKeiroConditionModel>>();
        for (RngTemplateKeiroConditionModel condModel : condList) {
            int rtkSid = condModel.getRtkSid();
            List<RngTemplateKeiroConditionModel> list =
                    rtkSidInCondMap.get(Integer.valueOf(rtkSid));
            if (list == null) {
                list = new ArrayList<RngTemplateKeiroConditionModel>();
            }
            list.add(condModel);
            rtkSidInCondMap.put(rtkSid, list);
        }
        for (Entry<Integer, List<RngTemplateKeiroConditionModel>> entry
                : rtkSidInCondMap.entrySet()) {
            Rng110KeiroDialogParamModel step = rtkSidStepMap.get(entry.getKey());
            if (step == null) {
                continue;
            }
            __setInCond(step, entry.getValue());
        }
    }
    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param rtkSidStepMap rtkSid経路ステップマップ
     * @throws SQLException SQL実行時例外
     */
    private void __loadKeiroSelect(
            Map<Integer, Rng110KeiroDialogParamModel> rtkSidStepMap) throws SQLException {

        //経路選択ユーザをロード
        List < RngTemplateKeiroUserModel > usrList =
                keiroUsrDao__.select(stepDao__, searchParam__, RngConst.JKBN_ALIVE);
        //経路使用条件をステップごとにマップ
        Map<Integer, List <RngTemplateKeiroUserModel>> rtkSidUserMap =
                new HashMap<Integer, List<RngTemplateKeiroUserModel>>();
        for (RngTemplateKeiroUserModel userModel : usrList) {
            int rtkSid = userModel.getRtkSid();
            List<RngTemplateKeiroUserModel> list = rtkSidUserMap.get(rtkSid);
            if (list == null) {
                list = new ArrayList<RngTemplateKeiroUserModel>();
            }
            list.add(userModel);
            rtkSidUserMap.put(rtkSid, list);
        }
        for (Entry<Integer, List<RngTemplateKeiroUserModel>> entry : rtkSidUserMap.entrySet()) {
            Rng110KeiroDialogParamModel step = rtkSidStepMap.get(entry.getKey());
            if (step == null) {
                continue;
            }
            __setUser(step, entry.getValue());
        }
    }
    /**
    *
    * <br>[機  能] 経路情報のロード
    * <br>[解  説]
    * <br>[備  考]
     * @throws SQLException SQL実行時例外
    */
    public void load() throws SQLException {
        keiro__ = new TreeMap<Integer, Rng110KeiroDialogParamModel>();
        finalKeiro__ = new TreeMap<Integer, Rng110KeiroDialogParamModel>();

        if (searchParam__ == null) {
            return;
        }

        //テンプレート経路ステップ情報ロード
        List<RngTemplateKeiroModel> stepList = null;
        stepList = stepDao__.select(searchParam__, RngConst.JKBN_ALIVE);
        if (stepList == null) {
            return;
        }
        keiro__ = new TreeMap<Integer, Rng110KeiroDialogParamModel>();
        finalKeiro__ = new TreeMap<Integer, Rng110KeiroDialogParamModel>();
        Map<Integer, Rng110KeiroDialogParamModel> rtkSidStepMap =
                new HashMap<Integer, Rng110KeiroDialogParamModel>();
        for (RngTemplateKeiroModel model : stepList) {
            Rng110KeiroDialogParamModel step = __createStep(model);
            rtkSidStepMap.put(model.getRtkSid(), step);
            if (model.getRtkRollType() == RngConst.RNG_RNCTYPE_APPR) {
                keiro__.put(keiro__.size(), step);
            }
            if (model.getRtkRollType() == RngConst.RNG_RNCTYPE_CONFIRM) {
                finalKeiro__.put(finalKeiro__.size(), step);
            }
        }
        __loadInCond(rtkSidStepMap);
        __loadKeiroSelect(rtkSidStepMap);
    }
    /**
    *
    * <br>[機  能] 経路ユーザ選択を設定
    * <br>[解  説]
    * <br>[備  考]
    * @param step 経路ステップフォームモデル
    * @param userList 経路選択ユーザデータリスト
    */
   private void __setUser(Rng110KeiroDialogParamModel step,
           List<RngTemplateKeiroUserModel> userList) {
       List<String> selected = null;
        int idx = 0;
       try {
           switch (keiroKbnUtil__.valueOf(step.getKeiroKbn())) {
           case FREESET:
           case USERSEL:
               selected = new ArrayList<String>();
               for (RngTemplateKeiroUserModel user:userList) {
                   if (user.getGrpSid() >= 0) {
                       selected.add(UserGroupSelectBiz.GROUP_PREFIX + user.getGrpSid());
                   } else {
                       selected.add(String.valueOf(user.getUsrSid()));
                   }
               }
               step.getUsrgroupselect().setSelected("target",
                       selected.toArray(new String[selected.size()]));
               break;
           case USERTARGET:
               selected = new ArrayList<String>();
               for (RngTemplateKeiroUserModel user:userList) {
                   if (user.getGrpSid() >= 0) {
                       selected.add(UserGroupSelectBiz.GROUP_PREFIX + user.getGrpSid());
                   } else {
                       selected.add(String.valueOf(user.getUsrSid()));
                   }
               }
               step.getUsrgrouptarget().setSelected("target",
                       selected.toArray(new String[selected.size()]));
               break;
           case POSTARGET:
               idx = 0;
               for (RngTemplateKeiroUserModel user:userList) {
                   TargetPosSel targetPos = new TargetPosSel();
                   if (user.getGrpSid() >= 0) {
                       targetPos.getGrpSel().setSelected(String.valueOf(user.getGrpSid()));
                   }
                   if (user.getPosSid() >= 0) {
                       targetPos.getPosSel().setSelected(String.valueOf(user.getPosSid()));
                   }
                   step.setTargetpos(String.valueOf(idx), targetPos);
                   idx++;
               }
               break;
           case GROUPSEL:
               selected = new ArrayList<String>();
               for (RngTemplateKeiroUserModel user:userList) {
                   selected.add(String.valueOf(user.getGrpSid()));
               }
               step.getGroupSel().setSelected(
                       selected.toArray(new String[selected.size()]));
               break;
           case BOSSTARGET:
           default:
               break;
           }
       } catch (EnumOutRangeException e) {

       }
   }

    /**
     *
     * <br>[機  能] 経路使用条件を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param step 経路ステップフォームモデル
     * @param condList 経路使用条件データリスト
     */
    private void __setInCond(Rng110KeiroDialogParamModel step,
            List<RngTemplateKeiroConditionModel> condList) {
        Map<Integer, List<RngTemplateKeiroConditionModel>> oridInCondMap =
                new TreeMap<Integer, List<RngTemplateKeiroConditionModel>>();
        //経路使用条件データリストをoridごとに分割
        for (RngTemplateKeiroConditionModel condModel : condList) {
            int orid = condModel.getRkcOrid();
            List<RngTemplateKeiroConditionModel> list = oridInCondMap.get(orid);
            if (list == null) {
                list = new ArrayList<RngTemplateKeiroConditionModel>();
            }
            list.add(condModel);
            oridInCondMap.put(orid, list);
        }
        //oridごとに経路条件モデルに設定
        for (Entry<Integer, List<RngTemplateKeiroConditionModel>> entry
                : oridInCondMap.entrySet()) {
            List<RngTemplateKeiroConditionModel> list = entry.getValue();
            if (list == null || list.size() <= 0) {
                continue;
            }
            RngTemplateKeiroConditionModel top = list.get(0);
            KeiroInCondition inCond = new KeiroInCondition();
            step.setInCond(String.valueOf(entry.getKey()), inCond);
            EnumKeiroInConditionKbn kbn = null;
            if (top.getRkcIfgroup() >= 0) {
                kbn = EnumKeiroInConditionKbn.CONDKBN_GROUP;
                inCond.getCondKbn().setSelected(String.valueOf(kbn.getValue()));
                String[] arr = new String[list.size()];
                int idx = 0;
                for (RngTemplateKeiroConditionModel model:list) {
                    arr[idx] = String.valueOf(model.getRkcIfgroup());
                    idx++;
                }
                inCond.getSelGrp().setSelected(arr);

            } else if (top.getRkcIfpos() >= 0) {
                kbn = EnumKeiroInConditionKbn.CONDKBN_POS;
                inCond.getCondKbn().setSelected(String.valueOf(kbn.getValue()));
                String[] arr = new String[list.size()];
                int idx = 0;
                for (RngTemplateKeiroConditionModel model:list) {
                    arr[idx] = String.valueOf(model.getRkcIfpos());
                    idx++;
                }
                inCond.getSelPos().setSelected(arr);
            } else {
                kbn = EnumKeiroInConditionKbn.CONDKBN_INPUT;
                inCond.getCondKbn().setSelected(String.valueOf(kbn.getValue()));
                inCond.setFormId(top.getRkcIfform());
                inCond.setFormValue(top.getRkcIfformValue());
                inCond.getComp().setSelected(top.getRkcIfformOpr());
            }
        }
    }
    /**
     *
     * <br>[機  能] 対応する経路ステップを設定
     * <br>[解  説] 関連テーブル（経路選択ユーザ情報、経路条件設定情報はロードされない）
     * <br>[備  考]
     * @param paramMdl ロードしたテンプレート経路情報
     * @return Rng110KeiroDialogParamModel
     */
    private Rng110KeiroDialogParamModel __createStep(RngTemplateKeiroModel paramMdl) {
        Rng110KeiroDialogParamModel ret = new Rng110KeiroDialogParamModel();
        ret.setRtkSid(paramMdl.getRtkSid());
//      稟議経路名
        ret.setKeiroName(paramMdl.getRtkName());
//      経路種別
        ret.setKeiroKbn(paramMdl.getRtkType());
//      経路コメント
        ret.setKeiroComment(paramMdl.getRtkKeiroComment());

 //     経路進行条件
        ret.setOutcondition(paramMdl.getRtkOutcondition());
 //     経路進行条件閾値
        if (paramMdl.getRtkOutcondBorder() > 0) {
            ret.setOutcond_threshold(String.valueOf(paramMdl.getRtkOutcondBorder()));
        }
//      審議無し進行許可フラグ
        ret.setNonuser(paramMdl.getRtkNouser());
//     任意経路追加フラグ
        ret.setAddkeiro(paramMdl.getRtkAddstep());
//      後閲許可フラグ
        ret.setKouetu(paramMdl.getRtkKeiroKoetu());
//      後閲指示許可フラグ
        ret.setKouetuSiji(paramMdl.getRtkKoetuSizi());
        //経路タイプ（審議者ｏｒ最終確認者）
        ret.setKeiroRootType(paramMdl.getRtkRollType());

        try {
            switch (keiroKbnUtil__.valueOf(paramMdl.getRtkType())) {
            case FREESET:
                break;
            case USERTARGET:
//               経路スキップフラグ
                ret.setSkip(paramMdl.getRtkKeiroSkip());
                break;
            case POSTARGET:
//              経路スキップフラグ
               ret.setSkip(paramMdl.getRtkKeiroSkip());
                break;
            case USERSEL:
//              複数選択フラグ
                ret.setMultisel(paramMdl.getRtkMultiselFlg());
                ret.setOwn(paramMdl.getRtkOwnsingi());
                break;
            case GROUPSEL:
//              複数選択フラグ
                ret.setMultisel(paramMdl.getRtkMultiselFlg());
                break;
            case BOSSTARGET:
//              上長階層数
                ret.setBossStepCnt(paramMdl.getRtkBossstepCnt());
//              必須上長階層数
                ret.setBossStepCntMin(paramMdl.getRtkBossstepMastcnt());
                break;
            default:
            }
        } catch (EnumOutRangeException e) {

        }
        return ret;


    }
    /**
     *
     * <br>[機  能] 経路ステップ情報の保管
     * <br>[解  説]
     * <br>[備  考]
     * @param model 経路ステップ設定フォームモデル
     * @param sort 経路ステップ順
     * @param rollType 役割タイプ 審議者or最終確認
     * @throws SQLException SQL実行時例外
     */
    private void __saveStep(Rng110KeiroDialogParamModel model,
            int sort, int rollType) throws SQLException {

        int rtkSid = (int) cntCon__.getSaibanNumber("ringi", "rtkSid",
                reqMdl__.getSmodel().getUsrsid());
        RngTemplateKeiroModel saveStepMdl = __createSaveModel(model, rtkSid, sort, rollType);
        stepDao__.insert(saveStepMdl);

        //経路ステップ使用条件
        Map<String, KeiroInCondition> inCondMap = model.getInCondMap();
        int idx = 0;
        for (Entry<String, KeiroInCondition> entry : inCondMap.entrySet()) {
            if (entry.getKey().equals("template")) {
                continue;
            }
            __saveStepIncond(entry.getValue(), rtkSid, idx);
            idx++;
        }
        idx = 0;
        try {
            switch (keiroKbnUtil__.valueOf(model.getKeiroKbn())) {
            case FREESET:
            case USERSEL:
                for (String sid :model.getUsrgroupselect().getSelected("target")) {
                    RngTemplateKeiroUserModel rkuModel = __createDefaultKeiroUser(rtkSid);
                    if (sid.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                        rkuModel.setGrpSid(
                                Integer.parseInt(
                                        sid.substring(
                                                UserGroupSelectBiz.GROUP_PREFIX.length())));
                    } else {
                        rkuModel.setUsrSid(Integer.parseInt(sid));
                    }
                    keiroUsrDao__.insert(rkuModel);
                }
                break;
            case USERTARGET:
                for (String sid :model.getUsrgrouptarget().getSelected("target")) {
                    RngTemplateKeiroUserModel rkuModel = __createDefaultKeiroUser(rtkSid);
                    if (sid.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                        rkuModel.setGrpSid(
                                Integer.parseInt(
                                        sid.substring(
                                                UserGroupSelectBiz.GROUP_PREFIX.length())));
                    } else {
                        rkuModel.setUsrSid(Integer.parseInt(sid));
                    }
                    keiroUsrDao__.insert(rkuModel);
                }
                break;
            case POSTARGET:
                for (Entry<String, TargetPosSel> entry : model.getTargetposMap().entrySet()) {
                    if (entry.getKey().equals("template")) {
                        continue;
                    }
                    RngTemplateKeiroUserModel rkuModel = __createDefaultKeiroUser(rtkSid);
                    TargetPosSel targetPos = entry.getValue();

                    rkuModel.setGrpSid(
                            NullDefault.getInt(targetPos.getGrpSel().getSelected(), -1));
                    rkuModel.setPosSid(
                            NullDefault.getInt(targetPos.getPosSel().getSelected(), -1));
                    keiroUsrDao__.insert(rkuModel);
                }
                break;
            case GROUPSEL:
                if (!ArrayUtils.isEmpty(model.getGroupSel().getSelected())) {
                    for (String sid : model.getGroupSel().getSelected()) {
                        RngTemplateKeiroUserModel rkuModel = __createDefaultKeiroUser(rtkSid);
                        rkuModel.setGrpSid(
                                Integer.parseInt(sid));
                        keiroUsrDao__.insert(rkuModel);
                    }
                }
                break;
            case BOSSTARGET:
            default:
                break;
            }
        } catch (EnumOutRangeException e) {

        }


    }
    /**
    *
    * <br>[機  能] 経路ステップユーザ情報初期値を作成
    * <br>[解  説]
    * <br>[備  考]
    * @param rtkSid 経路ステップSID
    * @return RngTemplateKeiroUserModel
    */
    private RngTemplateKeiroUserModel __createDefaultKeiroUser(int rtkSid) {
        RngTemplateKeiroUserModel rkuModel = new RngTemplateKeiroUserModel();
        rkuModel.setRtkSid(rtkSid);
        UDate now = new UDate();
        rkuModel.setRkuAuid(reqMdl__.getSmodel().getUsrsid());
        rkuModel.setRkuAdate(now);
        rkuModel.setRkuEuid(reqMdl__.getSmodel().getUsrsid());
        rkuModel.setRkuEdate(now);
        return rkuModel;
    }

    /**
    *
    * <br>[機  能] 経路ステップ利用条件を保管
    * <br>[解  説]
    * <br>[備  考]
    * @param inCond 経路ステップ利用条件フォームモデル
    * @param rtkSid 経路ステップSID
    * @param idx 経路ステップ利用条件行番号
    * @throws SQLException SQL実行時例外
    */

    private void __saveStepIncond(KeiroInCondition inCond,
            int rtkSid, int idx) throws SQLException {
        try {
            switch (inCondKbnUtil__.valueOf(Integer.parseInt(inCond.getCondKbn().getSelected()))) {
            case CONDKBN_INPUT:
                __saveStepIncondValue(inCond, rtkSid, idx);
                break;
            case CONDKBN_POS:
                __saveStepIncondPos(inCond, rtkSid, idx);
                break;
            case CONDKBN_GROUP:
                __saveStepIncondGroup(inCond, rtkSid, idx);
                break;
             default:
            }
        } catch (EnumOutRangeException e) {
        }
    }
    /**
     *
     * <br>[機  能] 経路ステップ利用条件を保管
     * <br>[解  説] グループによる制限
     * <br>[備  考]
     * @param inCond 経路ステップ利用条件フォームモデル
     * @param rtkSid 経路ステップSID
     * @param idx 経路ステップ利用条件行番号
     * @throws SQLException SQL実行時例外
     */
    private void __saveStepIncondGroup(KeiroInCondition inCond,
            int rtkSid, int idx) throws SQLException {
        if (inCond.getSelGrp().getSelected() != null) {
            for (String  sid : inCond.getSelGrp().getSelected()) {
                RngTemplateKeiroConditionModel mdl = new RngTemplateKeiroConditionModel();
                mdl.setRtkSid(rtkSid);
                mdl.setRkcOrid(idx);
                mdl.setRkcIfgroup(Integer.parseInt(sid));
                inCondDao__.insert(mdl);
            }
        }
    }
    /**
    *
    * <br>[機  能] 経路ステップ利用条件を保管
    * <br>[解  説] 役職による制限
    * <br>[備  考]
    * @param inCond 経路ステップ利用条件フォームモデル
    * @param rtkSid 経路ステップSID
    * @param idx 経路ステップ利用条件行番号
    * @throws SQLException SQL実行時例外
    */
    private void __saveStepIncondPos(KeiroInCondition inCond,
            int rtkSid, int idx) throws SQLException {
        if (inCond.getSelPos().getSelected() != null) {
            for (String  sid : inCond.getSelPos().getSelected()) {
                RngTemplateKeiroConditionModel mdl = new RngTemplateKeiroConditionModel();
                mdl.setRtkSid(rtkSid);
                mdl.setRkcOrid(idx);
                mdl.setRkcIfpos(Integer.parseInt(sid));
                inCondDao__.insert(mdl);
            }
        }
    }
    /**
    *
    * <br>[機  能] 経路ステップ利用条件を保管
    * <br>[解  説] 入力値による制限
    * <br>[備  考]
    * @param inCond 経路ステップ利用条件フォームモデル
    * @param rtkSid 経路ステップSID
    * @param idx 経路ステップ利用条件行番号
    * @throws SQLException SQL実行時例外
    */
    private void __saveStepIncondValue(KeiroInCondition inCond,
            int rtkSid, int idx) throws SQLException {
        RngTemplateKeiroConditionModel mdl = new RngTemplateKeiroConditionModel();
        mdl.setRtkSid(rtkSid);
        mdl.setRkcOrid(idx);
        mdl.setRkcIfform(inCond.getFormId());
        mdl.setRkcIfformOpr(inCond.getComp().getSelected());
        mdl.setRkcIfformValue(inCond.getFormValue());
        inCondDao__.insert(mdl);
    }

    /**
     *
     * <br>[機  能] 論理削除処理
     * <br>[解  説] 経路テンプレートステップを論理削除する
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    public void delete() throws SQLException {
        if (searchParam__ == null) {
            return;
        }
        if (searchParam__ instanceof SearchParamForRCT) {
            stepDao__.deleteRonri((SearchParamForRCT) searchParam__);
        }
    }
    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl 経路のパラメータモデル
     * @param rtkSid 経路ステップSID
     * @param index 経路の表示位置
     * @param rollType 審議者用か最終確認用か
     * @return RngTemplateKeiroModel
     */
    public RngTemplateKeiroModel __createSaveModel(IRng110KeiroDialogParam paramMdl,
            int rtkSid, int index, int rollType) {
        RngTemplateKeiroModel ret = new RngTemplateKeiroModel();
        ret.setRtkSid(rtkSid);
        if (searchParam__ instanceof SearchParamForRCT) {
            SearchParamForRCT param = (SearchParamForRCT) searchParam__;

//          経路テンプレートSID
            ret.setRctSid(param.getRctSid());
//          ユーザSID
            ret.setUsrSid(param.getUsrSid());
        }
        if (searchParam__ instanceof SearchParamForRTP) {
            SearchParamForRTP param = (SearchParamForRTP) searchParam__;
//           稟議テンプレートSID
            ret.setRtpSid(param.getRtpSid());
//           論議テンプレートバージョン
            ret.setRtpVer(param.getRtpVer());
        }
//        経路順
        ret.setRtkSort(index);
//       稟議経路名
        ret.setRtkName(paramMdl.getKeiroName());
//        経路種別
        ret.setRtkType(paramMdl.getKeiroKbn());

//      経路コメント
        ret.setRtkKeiroComment(paramMdl.getKeiroComment());
//      審議種別
        ret.setRtkRollType(rollType);
//      審議無し進行許可フラグ
        ret.setRtkNouser(paramMdl.getNonuser());
        //          後閲許可フラグ
        ret.setRtkKeiroKoetu(paramMdl.getKouetu());
//          後閲指示許可フラグ
        ret.setRtkKoetuSizi(paramMdl.getKouetuSiji());

        UDate now = new UDate();
        ret.setRtkAuid(reqMdl__.getSmodel().getUsrsid());
        ret.setRtkAdate(now);
        ret.setRtkEuid(reqMdl__.getSmodel().getUsrsid());
        ret.setRtkEdate(now);

        try {
            EnumKeiroKbn keiroKbn = keiroKbnUtil__.valueOf(paramMdl.getKeiroKbn());
            if (keiroKbn != EnumKeiroKbn.FREESET

                 ) {
                //     経路進行条件
                ret.setRtkOutcondition(paramMdl.getOutcondition());
         //     経路進行条件閾値
                if (paramMdl.getOutcondition() == 2 || paramMdl.getOutcondition() == 3) {
                    ret.setRtkOutcondBorder(NullDefault.getInt(paramMdl.getOutcond_threshold(), 0));
                } else {
                    ret.setRtkOutcondBorder(0);
                }
            }

            switch (keiroKbn) {
            case FREESET:
//              任意経路追加フラグ
                ret.setRtkAddstep(paramMdl.getAddkeiro());
                break;
            case USERTARGET:
//               経路スキップフラグ
                ret.setRtkKeiroSkip(paramMdl.getSkip());
                break;
            case POSTARGET:
//               経路スキップフラグ
                ret.setRtkKeiroSkip(paramMdl.getSkip());
                break;
            case USERSEL:
//              複数選択フラグ
                ret.setRtkMultiselFlg(paramMdl.getMultisel());
                ret.setRtkOwnsingi(paramMdl.getOwn());
                break;
            case GROUPSEL:
//              複数選択フラグ
              ret.setRtkMultiselFlg(paramMdl.getMultisel());
                break;
            case BOSSTARGET:
//              上長階層数
                ret.setRtkBossstepCnt(paramMdl.getBossStepCnt());
//              必須上長階層数
                ret.setRtkBossstepMastcnt(paramMdl.getBossStepCntMin());
                break;
            default:
            }
        } catch (EnumOutRangeException e) {

        }

        return ret;
    }


}
