package jp.groupsession.v2.rng.rng020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.biz.RngDoNextBiz;
import jp.groupsession.v2.rng.dao.RngCopyKeiroStepDao;
import jp.groupsession.v2.rng.dao.RngCopyKeirostepSelectDao;
import jp.groupsession.v2.rng.dao.RngKeiroStepDao;
import jp.groupsession.v2.rng.dao.RngKeirostepSelectDao;
import jp.groupsession.v2.rng.dao.RngSingiDao;
import jp.groupsession.v2.rng.model.RingiRequestModel;
import jp.groupsession.v2.rng.model.RngCopyKeiroStepModel;
import jp.groupsession.v2.rng.model.RngCopyKeirostepSelectModel;
import jp.groupsession.v2.rng.model.RngKeiroStepModel;
import jp.groupsession.v2.rng.model.RngKeirostepSelectModel;
import jp.groupsession.v2.rng.model.RngSingiModel;
import jp.groupsession.v2.rng.rng110keiro.EnumKeiroKbn;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm.TargetPosSel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
 *
 * <br>[機  能] 稟議申請画面 経路登録処理 ロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng020EntryKeiroBiz {
    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Rng020EntryKeiroBiz.class);

    /** コネクション */
    protected Connection con__ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl__ = null;
    /** 採番コントローラ*/
    MlCountMtController cntCon__ = null;
    /** 稟議登録リクエストモデル*/
    private RingiRequestModel model__;
    /** 処理モード 0:申請 1:草稿 2：アクセス制限チェック*/
    private int mode__;
    /**ルートパス*/
    private String appRootPath__ = null;
    /**プラグインコンフィグ*/
    private PluginConfig pluginConfig__ = null;
    /**ショートメール使用フラグ*/
    boolean smailPluginUseFlg__ = false;

    /** 経路ステップDAO*/
    private RngKeiroStepDao rksDao__;
    /** 経路ステップ選択DAO*/
    private RngKeirostepSelectDao selectDao__;
    /** 審議情報DAO*/
    private RngSingiDao rssDao__;

    /**経路ステップ登録情報保管 フィールド*/
    private List<RngKeiroStepModel> rksList__;
    /**スキップ実行フラグ*/
    private boolean skipExe__ = false;
    /**申請者が承認者になっている経路SID*/
    private int rksSid__  = 0;


    /**
     * コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param mode 処理モード 0:申請 1:草稿 2:申請画面の表示
     */
    public Rng020EntryKeiroBiz(Connection con, RequestModel reqMdl,
             int mode) {
        con__ = con;
        reqMdl__ = reqMdl;
        mode__ = mode;
        rksDao__ = new RngKeiroStepDao(con__);
        selectDao__ = new RngKeirostepSelectDao(con__);
        rssDao__ = new RngSingiDao(con__);
        rksList__ = new ArrayList<RngKeiroStepModel>();
    }

    /**
     * コンストラクタ
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param mode 処理モード 0:申請 1:草稿 2:申請画面の表示
     * @param appRootPath ルートパス
     * @param pluginConfig プラグインコンフィグ
     * @param smailPluginUseFlg ショートメール使用フラグ
     */
    public Rng020EntryKeiroBiz(Connection con, RequestModel reqMdl,
             int mode, String appRootPath, PluginConfig pluginConfig,
             boolean smailPluginUseFlg) {
        con__ = con;
        reqMdl__ = reqMdl;
        mode__ = mode;
        rksDao__ = new RngKeiroStepDao(con__);
        selectDao__ = new RngKeirostepSelectDao(con__);
        rssDao__ = new RngSingiDao(con__);
        rksList__ = new ArrayList<RngKeiroStepModel>();
        appRootPath__ = appRootPath;
        pluginConfig__ = pluginConfig;
        smailPluginUseFlg__ = smailPluginUseFlg;
    }

    /**
     *
     * <br>[機  能] 経路情報の登録処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パタメータモデル
     * @param rngSid 登録稟議SID
     * @param cntCon 採番コントローラ
     * @param model 稟議リクエストモデル
     * @throws Exception Exception
     */
    public void entry(IRng020PeronalParam paramMdl, int rngSid,
            MlCountMtController cntCon, RingiRequestModel model
          )
    throws Exception {
        cntCon__ = cntCon;
        model__ = model;

        int rksSid = 0;
        if (paramMdl.getRngCmdMode() == RngConst.RNG_CMDMODE_EDIT) {
            //更新の場合は登録済みの経路情報を削除
            rksDao__.deleteRngSid(rngSid);
            selectDao__.deleteRng(rngSid);
        }
        //申請時は申請者の経路情報および審議情報を登録する
        if (mode__ == RngBiz.ENTRYMODE_SINSEI) {
            RngKeiroStepModel rksMdl = new RngKeiroStepModel();
            // 経路情報
            rksSid = (int) cntCon__.getSaibanNumber(RngConst.SBNSID_RINGI,
                    RngConst.SBNSID_SUB_RINGI_KEIRO_STEP, model__.getUserSid());
            rksMdl = __initKeiroStepModel(rksSid, 0, model__, RngConst.RNG_RNCTYPE_APPL);
            rksMdl.setRtkSid(-1);
            rksMdl.setRksRollType(RngConst.RNG_RNCTYPE_APPL);
            rksMdl.setRksStatus(RngConst.RNG_RNCSTATUS_NOSET);
            rksMdl.setRksBelongSid(-1);
            rksList__.add(rksMdl);
            // 審議情報
            RngSingiModel rssMdl = new RngSingiModel();
            rssMdl.setRksSid(rksSid);
            rssMdl.setUsrSid(model__.getUserSid());
            rssMdl.setRngSid(model__.getRngSid());
            rssMdl.setUsrSidKoetu(-1);
            rssMdl.setUsrSidKoetu(-1);
            rssMdl.setRssStatus(RngConst.RNG_RNCSTATUS_NOSET);
            rssMdl.setRssAuid(model__.getUserSid());
            rssMdl.setRssAdate(model__.getDate());
            rssMdl.setRssEuid(model__.getUserSid());
            rssMdl.setRssEdate(model__.getDate());
            rssDao__.insert(rssMdl);
        }

        // 承認経路の登録
        __entryKeiroBlock(paramMdl.getRng020keiroMap(), RngConst.RNG_RNCTYPE_APPR);
        // 最終確認経路の登録
        __entryKeiroBlock(paramMdl.getRng020kakuninKeiroMap(), RngConst.RNG_RNCTYPE_CONFIRM);

        // 経路ステップを登録(__entryKeiroBlock内で経路ステップは登録されていない)
        rksDao__.insert(rksList__);

        if (mode__ == RngBiz.ENTRYMODE_SOUKOU) {
            return;
        }

        if (paramMdl.isRng020useCopyKeiro()) {
            // 申請時のみ最終確認経路を複写用経路データに登録
            int copyRksSid = -1;
            for (RngKeiroStepModel rksMdl : rksList__) {
                if (rksMdl.getRksRollType() == RngConst.RNG_RNCTYPE_CONFIRM) {
                    copyRksSid = rksMdl.getRksSid();
                    break;
                }
            }
            if (copyRksSid >= 0) {
                __entryCopyKeiroBlock(paramMdl.getRng020kakuninSvKeiroMap(),
                                      RngConst.RNG_RNCTYPE_CONFIRM, copyRksSid);
            }
        }

        // 申請者が承認者でスキップを行った際に申請者の経路を承認済みにする
        if (rksSid__ > 0) {
            RngSingiModel sMdl = new RngSingiModel();
            UDate now = new UDate();

            sMdl.setRssStatus(RngConst.RNG_RNCSTATUS_APPR);
            sMdl.setRngSid(rngSid);
            sMdl.setRssEuid(rngSid);
            sMdl.setRssEdate(now);
            sMdl.setRksSid(rksSid__);
            sMdl.setUsrSid(model__.getUserSid());
            rssDao__.updateSingi(sMdl);
            RngDoNextBiz nextBiz = new RngDoNextBiz(con__, reqMdl__, rssDao__, rksDao__, rngSid);
            nextBiz.doNext(cntCon, rksSid__, appRootPath__, pluginConfig__,
                    smailPluginUseFlg__, model__.getUserSid(), 0, true);
        } else {
            //稟議リスナーの処理を実行する。
            RngBiz rngBiz = new RngBiz(con__, cntCon);
            rngBiz.manageRingiLisner(model, 0, rngSid,
                    pluginConfig__, smailPluginUseFlg__, reqMdl__);
        }
    }
    /**
     *
     * <br>[機  能] 登録ロジックのサブロジック
     * <br>[解  説] 経路マップごとの処理をまとめる
     * <br>[備  考]
     * @param keiroBlock 経路ブロックマップ
     * @param rollType   審議種別
     * @throws SQLException SQL実行時例外
     * @throws EnumOutRangeException 経路区分範囲外例外
     */
    private void __entryKeiroBlock(
            Map<Integer, Rng020KeiroBlock> keiroBlock,
            int rollType
            ) throws SQLException, EnumOutRangeException {
        if (!keiroBlock.isEmpty() && keiroBlock.size() > 0) {
            RngKeiroStepModel prevRks = null;
            for (Map.Entry<Integer, Rng020KeiroBlock> block : keiroBlock.entrySet()) {
                int rtkSid = block.getValue().getRtkSid();
                int apprFlg = block.getValue().getApprFlg();
                if (mode__ == RngBiz.ENTRYMODE_SINSEI && block.getValue().getHidden() == 1) {
                    //申請時かつ非表示の経路は処理を行わない
                    continue;
                }
                //経路追加可能経路ステップ 直前の経路に経路追加ボタン表示フラグ（テンプレート経路ステップSID）を設定
                if (prevRks != null
                    && EnumKeiroKbn.valueOf(block.getValue().getKeiroKbn()) == EnumKeiroKbn.FREESET
                    && block.getValue().getPref().getAddkeiro() == RngConst.RNG_ABLE_ADDKEIRO) {
                    prevRks.setRksAddstep(rtkSid);
                }
                for (Map.Entry<Integer, Rng020Keiro> keiroEtr
                                    : block.getValue().getKeiroMap().entrySet()) {
                    Rng020Keiro keiro = keiroEtr.getValue();
                    //経路ステップ登録処理
                    RngKeiroStepModel rksMdl = __subLogicEntryKeiroStep(keiro, block.getValue(),
                            rksList__.size(), apprFlg, rtkSid, rollType);
                    if (rksMdl != null) {
                        prevRks = rksMdl;
                    }
                    //申請時かつ上長指定経路は指定グループ次の階層の経路ステップも追加登録する
                    if (EnumKeiroKbn.valueOf(keiro.getKeiroKbn()) == EnumKeiroKbn.BOSSTARGET
                            && mode__ == RngBiz.ENTRYMODE_SINSEI) {
                        String bossSid = keiro.getGrpSel().getSelectedSingle();
                        int bossStep = keiro.getPref().getBossStepCnt();

                        List<String> hiGrpSids =
                                __getHigherRankGrp(con__, bossSid, bossStep, false);
                        // 上長階層数分経路登録
                        for (String grpSid : hiGrpSids) {
                            Rng020Keiro subKeiro = new Rng020Keiro();
                            try {
                                BeanUtils.copyProperties(subKeiro, keiro);
                            } catch (Exception e) {
                                log__.error("フォームパラメータのコピーに失敗", e);
                            }
                            //グループ選択要素を上長階層を選択した経路を設定する
                            subKeiro.setGrpSel(new GroupComboModel());
                            subKeiro.getGrpSel().setSelectedSingle(grpSid);
                            //経路ステップ登録処理
                            rksMdl = __subLogicEntryKeiroStep(
                                    subKeiro,
                                    block.getValue(),
                                    rksList__.size(),
                                    apprFlg,
                                    rtkSid,
                                    rollType);
                            if (rksMdl != null) {
                                prevRks = rksMdl;
                            }
                        }

                    }
                }
            }
        }

    }

    /**
     *
     * <br>[機  能] 複写用データの登録ロジックのサブロジック
     * <br>[解  説] 経路マップごとの処理をまとめる
     * <br>[備  考]
     * @param keiroBlock 経路ブロックマップ
     * @param rollType   審議種別
     * @param rksSid     紐付く経路ステップSID
     * @throws SQLException SQL実行時例外
     * @throws EnumOutRangeException 経路区分範囲外例外
     */
    private void __entryCopyKeiroBlock(
            Map<Integer, Rng020KeiroBlock> keiroBlock,
            int rollType,
            int rksSid
            ) throws SQLException, EnumOutRangeException {

        RngKeiroStepModel rksMdl = null;
        List<RngKeirostepSelectModel> selectList = null;
        ArrayList<RngCopyKeiroStepModel> svRckList = new ArrayList<RngCopyKeiroStepModel>();
        ArrayList<RngCopyKeirostepSelectModel> svRcsList =
                                                    new ArrayList<RngCopyKeirostepSelectModel>();
        if (!keiroBlock.isEmpty() && keiroBlock.size() > 0) {
            for (Map.Entry<Integer, Rng020KeiroBlock> block : keiroBlock.entrySet()) {
                int rtkSid = block.getValue().getRtkSid();
                int apprFlg = block.getValue().getApprFlg();
                if (mode__ == RngBiz.ENTRYMODE_SINSEI && block.getValue().getHidden() == 1) {
                    //申請時かつ非表示の経路は処理を行わない
                    continue;
                }
                for (Map.Entry<Integer, Rng020Keiro> keiroEtr
                                    : block.getValue().getKeiroMap().entrySet()) {
                    Rng020Keiro keiro = keiroEtr.getValue();

                    // 経路ステップ選択一覧取得
                    selectList = keiro.createRngKeiroSelect(con__, rksSid);

                    // 経路ステップ情報作成
                    rksMdl = __getEntryKeiroModel(keiro, block.getValue(),
                                                  svRckList.size(), apprFlg,
                                                  rtkSid, rollType, rksSid, selectList);

                    if (rksMdl != null) {
                        RngCopyKeiroStepModel rckMdl = new RngCopyKeiroStepModel();
                        rckMdl.setRksMdl(rksMdl);
                        svRckList.add(rckMdl);

                        for (RngKeirostepSelectModel rssMdl : selectList) {
                            RngCopyKeirostepSelectModel rcsMdl = new RngCopyKeirostepSelectModel();
                            rcsMdl.setRssMdl(rssMdl, rksMdl.getRksSort());
                            svRcsList.add(rcsMdl);
                        }
                    }

                    //申請時かつ上長指定経路は指定グループ次の階層の経路ステップも追加登録する
                    if (EnumKeiroKbn.valueOf(keiro.getKeiroKbn()) == EnumKeiroKbn.BOSSTARGET
                            && mode__ == RngBiz.ENTRYMODE_SINSEI) {
                        String bossSid = keiro.getGrpSel().getSelectedSingle();
                        int bossStep = keiro.getPref().getBossStepCnt();

                        List<String> hiGrpSids =
                                __getHigherRankGrp(con__, bossSid, bossStep, false);
                        // 上長階層数分経路登録
                        for (String grpSid : hiGrpSids) {
                            Rng020Keiro subKeiro = new Rng020Keiro();
                            try {
                                BeanUtils.copyProperties(subKeiro, keiro);
                            } catch (Exception e) {
                                log__.error("フォームパラメータのコピーに失敗", e);
                            }
                            //グループ選択要素を上長階層を選択した経路を設定する
                            subKeiro.setGrpSel(new GroupComboModel());
                            subKeiro.getGrpSel().setSelectedSingle(grpSid);

                            // 経路ステップ選択一覧取得
                            selectList = keiro.createRngKeiroSelect(con__, rksSid);

                            // 経路ステップ情報作成
                            rksMdl = __getEntryKeiroModel(keiro, block.getValue(),
                                                          svRckList.size(), apprFlg,
                                                          rtkSid, rollType, rksSid, selectList);

                            if (rksMdl != null) {
                                RngCopyKeiroStepModel rckMdl = new RngCopyKeiroStepModel();
                                rckMdl.setRksMdl(rksMdl);
                                svRckList.add(rckMdl);
                                for (RngKeirostepSelectModel rssMdl : selectList) {
                                    RngCopyKeirostepSelectModel rcsMdl =
                                            new RngCopyKeirostepSelectModel();
                                    rcsMdl.setRssMdl(rssMdl, rksMdl.getRksSort());
                                    svRcsList.add(rcsMdl);
                                }
                            }
                        }
                    }
                }
            }

            // 最終確認者の複写用データを作成
            RngCopyKeirostepSelectDao rcsDao = new RngCopyKeirostepSelectDao(con__);
            rcsDao.insert(svRcsList);

            RngCopyKeiroStepDao rckDao = new RngCopyKeiroStepDao(con__);
            rckDao.insert(svRckList);
        }
    }

    /**
     *
     * <br>[機  能] 経路情報(Rng020Keiro)ごとの処理をまとめたサブロジック
     * <br>[解  説]
     * <br>[備  考] この処理内でRngKeiroStepSelectとRngSingiデータはインサートされる
     * @param keiro 経路情報
     * @param block 経路ブロック
     * @param rncSort 経路ステップ順
     * @param apprFlg スキップされる経路フラグ
     * @param rtkSid 経路ステップSID
     * @param rollType 審議種別
     * @throws SQLException SQL実行時例外
     * @throws EnumOutRangeException 経路区分範囲外例外
     * @return 追加された経路ステップ
     */
    private RngKeiroStepModel __subLogicEntryKeiroStep(
            Rng020Keiro keiro,
            Rng020KeiroBlock block,
            int rncSort,
            int apprFlg,
            int rtkSid,
            int rollType
            ) throws SQLException, EnumOutRangeException {
        int rksSid = (int) cntCon__.getSaibanNumber(RngConst.SBNSID_RINGI,
                RngConst.SBNSID_SUB_RINGI_KEIRO_STEP, model__.getUserSid());

        // 経路選択情報の登録
        List<RngKeirostepSelectModel> selectList
                               = new ArrayList<RngKeirostepSelectModel>();
        selectList.addAll(keiro.createRngKeiroSelect(con__, rksSid));

        RngKeiroStepModel addMdl = __getEntryKeiroModel(keiro, block, rncSort, apprFlg, rtkSid,
                                                        rollType, rksSid, selectList);
        if (addMdl == null) {
            return null;
        }

        // 申請時は審議情報を登録
        if (mode__ == RngBiz.ENTRYMODE_SINSEI) {
            int ownSingi = RngConst.RNG_OWNSINGI_YES;
            if (block.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL
                || block.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL) {
                ownSingi = block.getOwnSingi();
            }

            int cnt = __insertSingi(selectList,
                        addMdl,
                        model__,
                        rncSort,
                        keiro.getKeiroKbn(),
                        ownSingi);

            //審議者がいない経路ステップは保管対象外
            if (cnt == 0) {
                if (rksList__.size() == 1) {
                    skipExe__ = false;
                }

                return null;
            } else {
                if (!selectList.isEmpty() && selectList.size() > 0) {
                    selectDao__.insert(selectList);
                }
            }
        } else {
            if (!selectList.isEmpty() && selectList.size() > 0) {
                selectDao__.insert(selectList);
            }
        }
        rksList__.add(addMdl);
        return addMdl;
    }

    /**
     *
     * <br>[機  能] 経路情報(Rng020Keiro)ごとの処理をまとめたサブロジック
     * <br>[解  説]
     * <br>[備  考] この処理内でRngKeiroStepSelectとRngSingiデータはインサートされる
     * @param keiro 経路情報
     * @param block 経路ブロック
     * @param rncSort 経路ステップ順
     * @param apprFlg スキップされる経路フラグ
     * @param rtkSid テンプレート経路ステップSID
     * @param rollType 審議種別
     * @param rksSid 経路ステップSID
     * @param selectList 経路ステップ選択一覧
     * @throws SQLException SQL実行時例外
     * @throws EnumOutRangeException 経路区分範囲外例外
     * @return 追加された経路ステップ
     */
    private RngKeiroStepModel __getEntryKeiroModel(
            Rng020Keiro keiro,
            Rng020KeiroBlock block,
            int rncSort,
            int apprFlg,
            int rtkSid,
            int rollType,
            int rksSid,
            List<RngKeirostepSelectModel> selectList
            ) throws SQLException, EnumOutRangeException {

        //申請時スキップされる経路かつ未選択は保管対象外
        if (mode__ == RngBiz.ENTRYMODE_SINSEI
         && selectList.isEmpty()
         && keiro.getSkipKyoka() == RngConst.RNG_ABLE_SKIP) {
            return null;
        }

        RngKeiroStepModel rksMdl = __initKeiroStepModel(rksSid, rncSort, model__, rollType);

        return  __createKeiroStep(keiro, block, rksMdl, cntCon__, model__, rtkSid, apprFlg);
    }

    /**
     * 経路ステップモデルを初期化します
     *@param rksSid 経路ステップSID
     *@param rncSort 経路ステップソート順
     *@param model 稟議リクエストモデル
     *@param type 審議種別
     *@return 経路ステップモデル
     * */
    private RngKeiroStepModel __initKeiroStepModel(
            int rksSid,
            int rncSort,
            RingiRequestModel model,
            int type) {
        RngKeiroStepModel rksMdl = new RngKeiroStepModel();
        rksMdl.setRksSid(rksSid);
        rksMdl.setRksSort(rncSort);
        rksMdl.setRngSid(model.getRngSid());
        rksMdl.setRksAuid(model.getUserSid());
        rksMdl.setRksAdate(model.getDate());
        rksMdl.setRksEuid(model.getUserSid());
        rksMdl.setRksEdate(model.getDate());
        rksMdl.setRksRollType(type);
        rksMdl.setRksSort(rncSort);

        return rksMdl;
    }


    /**
     * 承認または最終確認の経路情報を取得します。
     * @param row 経路ステップ
     * @param block 経路ブロック
     * @param rksMdl 経路ステップモデル
     * @param cntCon 採番コントローラー
     * @param model 稟議リクエストモデル
     * @param rtkSid 経路ブロックの経路テンプレートSID
     * @throws SQLException SQL実行例外
     * @param apprFlg 申請時にスキップ処理を行う場合、経路ブロックに申請者を含むかを表す
     * @return 稟議経路ステップモデル
     * @throws EnumOutRangeException 列挙型範囲外例外
     * */
    private RngKeiroStepModel __createKeiroStep(
            Rng020Keiro row,
            Rng020KeiroBlock block,
            RngKeiroStepModel rksMdl,
            MlCountMtController cntCon,
            RingiRequestModel model,
            int rtkSid,
            int apprFlg
            ) throws SQLException, EnumOutRangeException {

        try {
            //経路テンプレートを使用している経路ステップの場合
            if (row.getRtkSid() > 0) {
                rksMdl.setRtkSid(row.getRtkSid());
            } else {
                rksMdl.setRtkSid(-1);
            }
            // 承認経路の場合、スキップ処理の判定
            if (rksMdl.getRksRollType() == RngConst.RNG_RNCTYPE_APPR) {
                // スキップされる経路
                if (row.getSkipKyoka() == RngConst.RNG_ABLE_SKIP) {
                    rksMdl.setRksRcvdate(model.getDate());
                    rksMdl.setRksStatus(RngConst.RNG_RNCSTATUS_KOETU);
                    skipExe__ = true;
                // 申請者を含む経路
                } else if (apprFlg == RngConst.RNG_RNCSTATUS_APPR) {
                    rksMdl.setRksRcvdate(model.getDate());
                    rksMdl.setRksStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
                    rksSid__ = rksMdl.getRksSid();
                // スキップされない経路
                } else {
                    if (skipExe__) {
                        rksMdl.setRksRcvdate(null);
                        rksMdl.setRksStatus(RngConst.RNG_RNCSTATUS_NOSET);
                    } else {
                        rksMdl.setRksRcvdate(model.getDate());
                        rksMdl.setRksStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
                        skipExe__ = true;
                    }
                }
            }
            //任意設定の場合、上長指定経路の場合
            if (EnumKeiroKbn.valueOf(block.getKeiroKbn())
                    == EnumKeiroKbn.FREESET || EnumKeiroKbn.valueOf(row.getKeiroKbn())
                    == EnumKeiroKbn.BOSSTARGET) {
                //関連SIDを経路ブロックの経路テンプレートSIDにする
                rksMdl.setRksBelongSid(rtkSid);
            } else {
                rksMdl.setRksBelongSid(-1);
            }
            // 後閲許可フラグを設定
            rksMdl.setRksKeiroKoetu(block.getKoetuKyoka());
            // 後閲指示許可フラグを設定
            rksMdl.setRksKoetuSizi(block.getKouetuSiji());
            // 経路追加フラグを設定
            if (block.getPref().getAddkeiro() == RngConst.RNG_ABLE_ADDKEIRO) {
                rksMdl.setRksAddstep(rtkSid);
            }

        } catch (EnumOutRangeException e) {
            throw e;
        }
        return rksMdl;
    }
    /**
     * 経路選択情報モデルに値を入力します。
     * @param rksSid 経路テンプレートSID
     * @param usrSid ユーザSID
     * @param grpSid グループSID
     * @param posSid 役職SID
     * @return 経路選択情報モデル
     * */
    private RngKeirostepSelectModel __inputSelectMdl(
            int rksSid, String usrSid, String grpSid, String posSid) {
        RngKeirostepSelectModel ret = new RngKeirostepSelectModel();
        ret.setRksSid(rksSid);
        ret.setUsrSid(NullDefault.getInt(usrSid, -1));
        ret.setGrpSid(NullDefault.getInt(grpSid, -1));
        ret.setPosSid(NullDefault.getInt(posSid, -1));

        return ret;
    }

    /**
     * 審議情報の登録を行います。
     * @param selectList 経路ステップ選択情報モデル
     * @param rksMdl 経路ステップモデル
     * @param model 稟議リクエストモデル
     * @param rncSort 経路ステップソート順
     * @param keiroKbn 経路区分
     * @param ownSingi 自己審議の許可
     * @throws SQLException SQL実行例外
     * @throws EnumOutRangeException 経路区分範囲外例外
     * @return 登録審議者数
     * */
    private int __insertSingi(
            List<RngKeirostepSelectModel> selectList,
            RngKeiroStepModel rksMdl,
            RingiRequestModel model,
            int rncSort, int keiroKbn, int ownSingi)
    throws SQLException, EnumOutRangeException {
        //上長指定の場合 選択グループの管理者ユーザを選択情報から取り出す
        if (EnumKeiroKbn.valueOf(keiroKbn) == EnumKeiroKbn.BOSSTARGET) {
            List<RngKeirostepSelectModel> selectBossList = new ArrayList<RngKeirostepSelectModel>();
            // グループ管理者ユーザを取得
            CmnBelongmDao begDao = new CmnBelongmDao(con__);
            List<CmnBelongmModel> bossList = new ArrayList<CmnBelongmModel>();
            for (RngKeirostepSelectModel selectMdl : selectList) {
                if (selectMdl.getGrpSid() >= 0) {
                    bossList.addAll(begDao.selectBossModel(selectMdl.getGrpSid()));
                }
            }
            for (CmnBelongmModel bossMdl : bossList) {
                RngKeirostepSelectModel selectMdl = __inputSelectMdl(
                        rksMdl.getRksSid(), String.valueOf(bossMdl.getUsrSid()), "-1", "-1");
                selectBossList.add(selectMdl);
            }
            selectList = selectBossList;
        }

        RngSingiDao rssDao = new RngSingiDao(con__);

        //審議者ユーザSID一覧を取得(プラグイン使用不可ユーザは除外)
        RngBiz rngBiz = new RngBiz(con__);
        List<Integer> userSidList = rngBiz.getSingiUserList(selectList);
        if (ownSingi == RngConst.RNG_OWNSINGI_NO) {
            userSidList.remove(Integer.valueOf(reqMdl__.getSmodel().getUsrsid()));

        }
        //審議者情報を登録
        for (int usrSid : userSidList) {
            RngSingiModel rssMdl = __initSingiModel(usrSid, model, rksMdl);
            rssDao.insert(rssMdl);
        }
        return userSidList.size();
    }


    /**
     * 稟議審議モデルを初期化します。
     *@param usrSid 経路選択情報モデル
     *@param model 稟議リクエストモデル
     *@param rksMdl 経路ステップモデル
     *@return 稟議審議モデル
     * */
    private RngSingiModel __initSingiModel(
            int usrSid,
            RingiRequestModel model,
            RngKeiroStepModel rksMdl
            ) {
        RngSingiModel rssMdl = new RngSingiModel();
        rssMdl.setUsrSid(usrSid);
        rssMdl.setRksSid(rksMdl.getRksSid());
        rssMdl.setRngSid(model.getRngSid());
        rssMdl.setRssAuid(model.getUserSid());
        rssMdl.setRssAdate(model.getDate());
        rssMdl.setRssEuid(model.getUserSid());
        rssMdl.setRssEdate(model.getDate());
        rssMdl.setRssStatus(rksMdl.getRksStatus());
        // 経路がスキップされている場合
        if (rksMdl.getRksStatus() == RngConst.RNG_RNCSTATUS_KOETU) {
            rssMdl.setUsrSidKoetu(model.getUserSid());
        }
        return rssMdl;
    }
    /**
     * 指定したグループの上位階層グループを取得する
     * @param con コネクション
     * @param grpSid グループSID
     * @param bossStepCnt 上位階層数 -1 指定時は最大数
     * @param selfAdd 指定グループを結果に含めるか
     *@return 上位階層グループのSID
     * @throws SQLException SQL実行例外
     * */
    private ArrayList<String> __getHigherRankGrp(Connection con, String grpSid,
            int bossStepCnt,
            boolean selfAdd)
            throws SQLException {
        GroupDao grpDao = new GroupDao(con);
        CmnGroupClassModel gclMdl
            = grpDao.getGroupClassModel(Integer.parseInt(grpSid));
        ArrayList<String> ret = new ArrayList<String>();
        if (gclMdl == null) {
            return ret;
        }
        // 指定されたグループが何階層目にいるか
        int classNum = 0;
        if (gclMdl.getGclSid1() == Integer.parseInt(grpSid)) {
            classNum = 1;
        } else if (gclMdl.getGclSid2() == Integer.parseInt(grpSid)) {
            classNum = 2;
        } else if (gclMdl.getGclSid3() == Integer.parseInt(grpSid)) {
            classNum = 3;
        } else if (gclMdl.getGclSid4() == Integer.parseInt(grpSid)) {
            classNum = 4;
        } else if (gclMdl.getGclSid5() == Integer.parseInt(grpSid)) {
            classNum = 5;
        } else if (gclMdl.getGclSid6() == Integer.parseInt(grpSid)) {
            classNum = 6;
        } else if (gclMdl.getGclSid7() == Integer.parseInt(grpSid)) {
            classNum = 7;
        } else if (gclMdl.getGclSid8() == Integer.parseInt(grpSid)) {
            classNum = 8;
        } else if (gclMdl.getGclSid9() == Integer.parseInt(grpSid)) {
            classNum = 9;
        } else if (gclMdl.getGclSid10() == Integer.parseInt(grpSid)) {
            classNum = 10;
        }

        int maxRank = 0;
        if (bossStepCnt >= 0) {
            maxRank = classNum - bossStepCnt;
        }
        if (maxRank <= 0) {
            maxRank = 0;
        }
        if (selfAdd) {
            classNum++;
        }
        for (classNum--; (classNum > maxRank && classNum > 0); classNum--) {
            switch (classNum) {
            case 1 :
                ret.add(String.valueOf(gclMdl.getGclSid1()));
                break;
            case 2 :
                ret.add(String.valueOf(gclMdl.getGclSid2()));
                break;
            case 3 :
                ret.add(String.valueOf(gclMdl.getGclSid3()));
                break;
            case 4 :
                ret.add(String.valueOf(gclMdl.getGclSid4()));
                break;
            case 5 :
                ret.add(String.valueOf(gclMdl.getGclSid5()));
                break;
            case 6 :
                ret.add(String.valueOf(gclMdl.getGclSid6()));
                break;
            case 7 :
                ret.add(String.valueOf(gclMdl.getGclSid7()));
                break;
            case 8 :
                ret.add(String.valueOf(gclMdl.getGclSid8()));
                break;
            case 9 :
                ret.add(String.valueOf(gclMdl.getGclSid9()));
                break;
            case 10 :
                ret.add(String.valueOf(gclMdl.getGclSid10()));
                break;
            default :
                break;
            }
        }
        log__.debug("KEIRO CHECK10: " + ret.size());
        return ret;
    }
    /**
    *
    * <br>[機  能] 経路入力チェック
    * <br>[解  説]
    * <br>[備  考]
    * @param errors エラー
    * @param entry 経路要素ブロック
    * @param keiroStr 経路名
    * @param keiroCnt 経路ステップ数
    * @throws SQLException SQL実行時例外
    * @return 追加される予定の経路ステップ数
    */
    public int  validateKeiro(ActionErrors errors,
           Entry<Integer, Rng020KeiroBlock> entry,
           String keiroStr,
           int keiroCnt
           ) throws SQLException {
       int ret = 0;
       GsMessage gsMsg = new GsMessage(reqMdl__);
       Rng020KeiroBlock block = entry.getValue();
       ActionMessage msg = null;
       Map<Integer, Rng020Keiro> keiroMap = block.getKeiroMap();
       EnumKeiroKbn blockKbn = null;
       try {
           blockKbn = EnumKeiroKbn.valueOf(block.getKeiroKbn());
       } catch (EnumOutRangeException e) {

       }
       int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
       if (mode__ != 2) {
               if (blockKbn != EnumKeiroKbn.FREESET && (keiroMap == null || keiroMap.size() == 0)) {
                   msg = new ActionMessage("error.select.required.text", keiroStr);
                   errors.add("rng020apprUser", msg);

                   return 0;
               }
       }

       if (block.getHidden() == 1) {
           return 0;
       }
       ret = block.getKeiroMap().size();

       if (mode__ == 2 && blockKbn == EnumKeiroKbn.FREESET) {
           //任意経路要素内へのアクセス時のアクセス制限チェックは不要
           return ret;
       }
       Collection<Rng020Keiro> keiroSet = block.getKeiroMap().values();

       int sort = keiroCnt;
       for (Rng020Keiro keiroStep : keiroSet) {
           sort++;
           try {
               EnumKeiroKbn keiroKbn = EnumKeiroKbn.valueOf(keiroStep.getKeiroKbn());
               StringBuilder sb = new StringBuilder();
               sb.append(keiroStr);
               sb.append(gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(sort)}));
               sb.append(gsMsg.getMessage(keiroKbn.getMsgKey()));
               String keiroName = sb.toString();
               ArrayList<Integer> usrList = new ArrayList<Integer>();
               CmnBelongmDao belongDao = new CmnBelongmDao(con__);
               CommonBiz cmnBiz = new CommonBiz();

               int threshould = 1;
               if (keiroStep.getPref().getOutcondition() == RngConst.RNG_OUT_CONDITION_NUMBER) {
                   threshould = NullDefault.getInt(
                           keiroStep.getPref().getOutcond_threshold(), 1);
               }
               //選択要素に対する不正入力チェック
               ValidateInfo vi = new ValidateInfo();
               vi.setTitle(keiroName);
               if (mode__ != 2) {
                   if (keiroKbn != EnumKeiroKbn.FREESET && mode__ != 1) {
                       keiroStep.getUsrgrpSel().validateCheck(errors, reqMdl__, vi);
                   }
                   keiroStep.getGrpSel().validateCheck(errors, reqMdl__, vi);
                   keiroStep.getPosSel().validateCheck(errors, reqMdl__, vi);
               }
               if (mode__ == 2) {
                   //アクセス制限チェック
                   switch (keiroKbn) {
                   case USERTARGET:
                       String[] usrTargetList = null;
                       for (String[] value : keiroStep.getUsrgrpSel().getSelected().values()) {
                           usrTargetList = value;
                           break;
                       }
                       //ユーザが1件もいない
                       if (usrTargetList == null || usrTargetList.length == 0) {
                           msg = new ActionMessage("error.keiro.undefined.targetuser", keiroName);
                           errors.add("rng020apprUser", msg);
                       }
                       //承認者数を満たしているか
                       __checkSingisya(errors,
                               usrTargetList, keiroName, threshould, RngConst.RNG_OWNSINGI_YES);
                       break;
                   case USERSEL:
                       String[] selectable = keiroStep.getUsrgrpSel().getSelectable();

                       //ユーザグループが1件も存在しない
                       if (selectable != null && selectable.length == 0) {
                           msg = new ActionMessage("error.keiro.undefined.targetuser", keiroName);
                           errors.add("rng020apprUser", msg);
                       }
                       //承認者数を満たしているか
                       __checkSingisya(errors,
                               selectable, keiroName, threshould, keiroStep.getPref().getOwn());
                       break;
                   case GROUPSEL:
                       List<String> selecGroup = keiroStep.getGrpSel().getSelectable();
                       if (selecGroup != null) {
                           for (String sid : selecGroup) {
                               if (StringUtil.isNullZeroString(sid)) {
                                   continue;
                               }
                               usrList.addAll(belongDao.selectBelongLiveUserSid(
                                       NullDefault.getInt(
                                          sid, -1)));
                           }
                           if (block.getSelectUserNone() != 0) {
                               //承認者数を満たしているか
                               int usrcnt = __getUserInGroup(errors,
                                       usrList, keiroName, threshould);
                               if (usrcnt == 0) {
                                   msg = new ActionMessage("error.keiro.undefined.targetgroup2",
                                           keiroName);
                                   errors.add("rng020apprUser", msg);
                               }
                           }
                       }
                       break;
                   case POSTARGET:
                       // 役職者の存在確認
                       CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
                       boolean sonzaiChk = false;
                       for (Entry<String, TargetPosSel> entryPos
                               : keiroStep.getPref().getTargetposMap().entrySet()) {
                           sonzaiChk = true;
                           int posSid = NullDefault.getInt(
                                   entryPos.getValue().getPosSel().getSelected(), 0);
                           int grpSid = NullDefault.getInt(
                                   entryPos.getValue().getGrpSel().getSelected(), -1);
                           usrList.addAll(usrDao.getBelongUsrsFromPosition(
                                   grpSid, posSid));
                       }
                       List<Integer> posUseSidList =
                               cmnBiz.getCantUsePluginUser(con__,
                                       RngConst.PLUGIN_ID_RINGI, usrList);
                       usrList.removeAll(posUseSidList);
                       //任意経路の場合 申請者のSIDを除外
                       if (blockKbn == EnumKeiroKbn.FREESET
                                 && block.getOwnSingi() == RngConst.RNG_OWNSINGI_NO) {
                           usrList.remove(Integer.valueOf(sessionUsrSid));
                       }

                       //選択した役職が存在するか
                       if (!sonzaiChk) {
                           msg = new ActionMessage("error.keiro.undefined.targetpost2",
                                   keiroName);
                           errors.add("rng020apprUser", msg);
                       }
                       //選択した役職にユーザが存在するか
                       if (usrList.size() == 0) {
                           msg = new ActionMessage("error.keiro.undefined.targetpost",
                                   keiroName);
                           errors.add("rng020apprUser", msg);
                       }
                       //承認者数を満たしているか
                       __getUserInGroup(errors, usrList, keiroName, threshould);
                       break;
                   case BOSSTARGET:
                       //選択可能なグループが存在しない
                       if (keiroStep.getGrpSel().getList().size() <= 1) {
                           msg = new ActionMessage("error.keiro.nonselectable.bossgroup",
                                   keiroName,
                                   String.valueOf(keiroStep.getPref().getBossStepCntMin()));
                           errors.add("rng020apprUser", msg);
                           break;
                       }
                       break;
                       default:
                   }
               } else if (mode__ == 0) {
                   //申請時チェック
                   switch (keiroKbn) {
                   case FREESET:
                   case USERSEL:
                   case USERTARGET:
                       //自己審議によるスキップ
                       if (ArrayUtils.isEmpty(keiroStep.getUsrgrpSel().getSelectedSimple())) {
                           if (keiroStep.getSkipKyoka() == RngConst.RNG_ABLE_SKIP) {
                               break;
                           }
                           msg = new ActionMessage("error.select.required.text", keiroName);
                           errors.add("rng020apprUser", msg);
                           break;
                       }
                       for (UsrLabelValueBean userLabel
                               : keiroStep.getUsrgrpSel().getSelectedListAll()) {
                           String sidStr = userLabel.getValue();
                           if (sidStr.startsWith(UserGroupSelectBiz.GROUP_PREFIX)
                                   && ValidateUtil.isNumberHaifun(sidStr.substring(
                                           UserGroupSelectBiz.GROUP_PREFIX.length()))) {
                               //グループで指定されているユーザの取得
                               List<Integer> addSidList = belongDao.selectBelongLiveUserSid(
                                       NullDefault.getInt(
                                               sidStr.substring(
                                                  UserGroupSelectBiz.GROUP_PREFIX.length()), -1));

                               //任意経路の場合とユーザ選択の設定を確認し申請者のSIDを除外
                               if ((blockKbn == EnumKeiroKbn.FREESET
                                       && block.getOwnSingi() == RngConst.RNG_OWNSINGI_NO)
                                   || (keiroKbn == EnumKeiroKbn.USERSEL
                                       && keiroStep.getPref().getOwn()
                                       == RngConst.RNG_OWNSINGI_NO)) {
                                   addSidList.remove(Integer.valueOf(sessionUsrSid));
                               }
                               if (addSidList.size() <= 0 && keiroKbn == EnumKeiroKbn.USERSEL) {
                                   //ユーザのいないグループが選択されている
                                   GroupDao grpDao = new GroupDao(con__);
                                   CmnGroupmModel grpMdl =  grpDao.getGroup(NullDefault.getInt(
                                               sidStr.substring(
                                                  UserGroupSelectBiz.GROUP_PREFIX.length()), -1));
                                   String grpName = "";
                                   if (grpMdl != null) {
                                       grpName = grpMdl.getGrpName();
                                   }
                                   msg = new ActionMessage("error.keiro.undefined.targetgroup",
                                           keiroName, grpName);
                                   errors.add("rng020apprUser", msg);

                               } else {
                                   usrList.addAll(addSidList);
                               }
                           } else {
                               usrList.add(NullDefault.getInt(sidStr, -1));
                           }
                       }
                       List<Integer> cantUseSidList = cmnBiz.getCantUsePluginUser(con__,
                               RngConst.PLUGIN_ID_RINGI, usrList);
                       usrList.removeAll(cantUseSidList);
                       //承認者数を満たしているか
                       __getUserInGroup(errors, usrList, keiroName, threshould);
                       break;
                   case GROUPSEL:
                       String[] grpSids = keiroStep.getGrpSel().getSelected();
                       List<Integer> grpSidList = new ArrayList<Integer>();
                       if (!ArrayUtils.isEmpty(grpSids)) {
                           // グループSIDが無いものを除外してSID一覧作成
                           for (String sidStr : grpSids) {
                               Integer grpSid = NullDefault.getInt(sidStr, -1);
                               if (grpSid >= 0) {
                                   grpSidList.add(grpSid);
                               }
                           }
                       }
                       if (grpSidList.isEmpty()) {
                           if (keiroStep.getSkipKyoka() == RngConst.RNG_ABLE_SKIP) {
                               //自己審議によるスキップ
                               break;
                           }
                           msg = new ActionMessage("error.select.required.text", keiroName);
                           errors.add("rng020apprUser", msg);
                           break;
                       }

                       for (Integer grpSid : grpSidList) {
                           List<Integer> addSidList = belongDao.selectBelongLiveUserSid(
                                                                       grpSid.intValue());

                           //任意経路の場合 申請者のSIDを除外(個人テンプレートの使用時)
                           if (blockKbn == EnumKeiroKbn.FREESET
                                     && block.getOwnSingi() == RngConst.RNG_OWNSINGI_NO) {
                               addSidList.remove(Integer.valueOf(sessionUsrSid));
                           }

                           if (addSidList.size() <= 0) {
                               //ユーザのいない経路が選択されている
                               // → エラーメッセージにグループ名を付けて表示
                               GroupDao grpDao = new GroupDao(con__);
                               CmnGroupmModel grpMdl =  grpDao.getGroup(grpSid.intValue());
                               String grpName = "";
                               if (grpMdl != null && grpMdl.getGrpName() != null) {
                                   grpName = grpMdl.getGrpName();    // 表示するグループ名
                               } else {
                                   grpName = String.valueOf(grpSid); // データがない場合、ひとまずグループSID表示
                               }
                               msg = new ActionMessage("error.keiro.undefined.targetgroup",
                                       keiroName, grpName);
                               errors.add("rng020apprUser", msg);

                           } else {
                               usrList.addAll(addSidList);
                           }
                       }
                       cantUseSidList = cmnBiz.getCantUsePluginUser(con__,
                               RngConst.PLUGIN_ID_RINGI, usrList);
                       usrList.removeAll(cantUseSidList);
                       //承認者数を満たしているか
                       __getUserInGroup(errors, usrList, keiroName, threshould);
                       break;
                   case BOSSTARGET:
                       if (ArrayUtils.isEmpty(keiroStep.getGrpSel().getSelected())) {
                           msg = new ActionMessage("error.select.required.text", keiroName);
                           errors.add("rng020apprUser", msg);
                           break;
                       }
                       //上長指定より後の経路に自己審議までスキップ可能な経路が存在する場合経路は無選択を許可
                       if (keiroStep.getGrpSel().getSelectedSingle().equals("-1")) {
                           if (keiroStep.getSkipKyoka() == 0) {
                               msg = new ActionMessage("error.select.required.text", keiroName);
                               errors.add("rng020apprUser", msg);
                           }
                           break;
                       }
                       String bossSid = keiroStep.getGrpSel().getSelectedSingle();
                       List<String> hiGrpSids = __getHigherRankGrp(con__, bossSid, -1, true);
                       if (hiGrpSids.size() < keiroStep.getPref().getBossStepCntMin()) {
                           //必須上長階層数が足りない
                           //必須上長階層以下のグループを選ぶ必要がある
                           msg = new ActionMessage("error.keiro.less.bossstepcnt",
                                   keiroName,
                                   String.valueOf(keiroStep.getPref().getBossStepCntMin()));
                           errors.add("rng020apprUser", msg);
                           break;
                       }
                       // 上長階層の管理者の存在確認
                       boolean noZyoutyou = true;
                       for (String grpSidStr : hiGrpSids) {
                           int grpSid = NullDefault.getInt(grpSidStr, -1);
                           if (grpSid > 0) {
                               // グループ管理者ユーザを取得
                               CmnBelongmDao begDao = new CmnBelongmDao(con__);
                               List<CmnBelongmModel> bossList = begDao.selectBossModel(grpSid);
                               usrList = new ArrayList<Integer>();
                               for (CmnBelongmModel boss : bossList) {
                                   //任意経路の場合 申請者のSIDを除外
                                   if (blockKbn == EnumKeiroKbn.FREESET
                                         && block.getOwnSingi() == RngConst.RNG_OWNSINGI_NO
                                         && boss.getUsrSid() == sessionUsrSid) {
                                       continue;
                                   }
                                   usrList.add(boss.getUsrSid());
                               }
                               cantUseSidList = cmnBiz.getCantUsePluginUser(
                                               con__, RngConst.PLUGIN_ID_RINGI, usrList);
                               usrList.removeAll(cantUseSidList);

                               if (usrList.size() > 0) {
                                   noZyoutyou = false;
                                   //承認者数を満たしているか
                                   __getUserInGroup(errors, usrList, keiroName, threshould);
                               }
                           }
                       }
                       if (noZyoutyou) {
                           //グループ管理者が1人以上存在するか
                           msg = new ActionMessage("error.keiro.undefined.bossgroup",
                                   keiroName);
                           errors.add("rng020apprUser", msg);
                       }
                       break;
                   case POSTARGET:
                       // 役職者の存在確認
                       CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
                       boolean sonzaiChk = false;
                       for (Entry<String, TargetPosSel> entryPos
                               : keiroStep.getPref().getTargetposMap().entrySet()) {
                           sonzaiChk = true;
                           int posSid = NullDefault.getInt(
                                   entryPos.getValue().getPosSel().getSelected(), 0);
                           int grpSid = NullDefault.getInt(
                                   entryPos.getValue().getGrpSel().getSelected(), -1);
                           usrList.addAll(usrDao.getBelongUsrsFromPosition(
                                   grpSid, posSid));
                       }

                       cantUseSidList = cmnBiz.getCantUsePluginUser(con__,
                               RngConst.PLUGIN_ID_RINGI, usrList);
                       usrList.removeAll(cantUseSidList);

                       //任意経路の場合 申請者のSIDを除外
                       if (blockKbn == EnumKeiroKbn.FREESET
                                 && block.getOwnSingi() == RngConst.RNG_OWNSINGI_NO) {
                           usrList.remove(Integer.valueOf(sessionUsrSid));
                       }
                       //選択した役職が存在するか
                       if (!sonzaiChk) {
                           msg = new ActionMessage("error.keiro.undefined.targetpost2",
                                   keiroName);
                           errors.add("rng020apprUser", msg);
                       }
                       //選択した役職にユーザが存在するか
                       if (usrList.size() == 0) {
                           msg = new ActionMessage("error.keiro.undefined.targetpost",
                                   keiroName);
                           errors.add("rng020apprUser", msg);
                       }
                       //承認者数を満たしているか
                       __getUserInGroup(errors, usrList, keiroName, threshould);
                       break;
                   default:
                   }
               }
           } catch (EnumOutRangeException e) {
           }
           if (mode__ == 2) {
               //申請画面の表示時の判定ではブロック内の最初の要素のみチェックする
               //2行目以降の設定は統一されるため
               if (block.getSkipNonUser() == 1) {
                   ret = 0;
               }
               break;
           }
       }
       return ret;
    }

    /**
     * <br>[機  能] 必要な審議者数を満たしているか
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param selectSid 選択ユーザSID
     * @param keiroName 経路名
     * @param threshould 承認数閾値
     * @param own 自己承認
     * @throws SQLException SQLException
     * @return ret ユーザ数
     */
    private int __checkSingisya(ActionErrors errors,
            String[] selectSid,
            String keiroName,
            int threshould,
            int own)
            throws SQLException {

        int ret = 1;
        ArrayList<Integer> usrList = new ArrayList<Integer>();

        CmnBelongmDao belongDao = new CmnBelongmDao(con__);
        if (selectSid != null && selectSid.length > 0) {
            for (String sid : selectSid) {
                if (StringUtil.isNullZeroString(sid)) {
                    continue;
                }
                if (sid.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                    //グループ指定されている場合、そのグループに所属するユーザを取得する
                    usrList.addAll(belongDao.selectBelongLiveUserSid(
                            NullDefault.getInt(
                               sid.substring(
                                  UserGroupSelectBiz.GROUP_PREFIX.length()), -1))
                            );
                } else {
                    usrList.add(NullDefault.getInt(sid, -1));
                }
            }
            RngBiz rngBiz = new RngBiz(con__);
            usrList = rngBiz.getUserList(usrList);
            if (usrList.size() > 0 && own == RngConst.RNG_OWNSINGI_NO) {
                int idxNo = usrList.indexOf(reqMdl__.getSmodel().getUsrsid());
                if (idxNo != -1) {
                    usrList.remove(idxNo);
                }
            }
            ret = __getUserInGroup(errors, usrList, keiroName, threshould);
        }
        return ret;
    }

    /**
     * <br>[機  能] 必要な審議者数を満たしているか（エラー作成部）
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param usrList ユーザリスト
     * @param keiroName 経路名
     * @param threshould 閾値
     * @return ユーザ数
     */
    private int __getUserInGroup(ActionErrors errors,
            ArrayList<Integer> usrList,
            String keiroName,
            int threshould) {

        ActionMessage msg = null;
        //重複SIDを除去
        HashSet<Integer> usrSet = new HashSet<Integer>(usrList);
        //ユーザ選択承認人数エラーチェック
        if (usrSet.size() < threshould) {
            msg = new ActionMessage("error.keiro.canreach.outcondition",
                    keiroName);
            errors.add("rng020apprUser", msg);
        }
        return usrSet.size();
    }

}
