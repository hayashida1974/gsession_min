package jp.groupsession.v2.rng.rng090kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.json.JSONArray;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormAccesser;
import jp.groupsession.v2.cmn.formbuilder.FormBuilder;
import jp.groupsession.v2.cmn.formbuilder.FormCell;
import jp.groupsession.v2.cmn.formbuilder.FormCellPrefarence;
import jp.groupsession.v2.cmn.formmodel.Temp;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.biz.RngTemplateBiz;
import jp.groupsession.v2.rng.biz.RngUsedDataBiz;
import jp.groupsession.v2.rng.dao.RngChannelTemplateDao;
import jp.groupsession.v2.rng.dao.RngIdDao;
import jp.groupsession.v2.rng.dao.RngTemplateBinDao;
import jp.groupsession.v2.rng.dao.RngTemplateCategoryDao;
import jp.groupsession.v2.rng.dao.RngTemplateDao;
import jp.groupsession.v2.rng.dao.RngTemplateFormDao;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngChannelTemplateModel;
import jp.groupsession.v2.rng.model.RngTemplateBinModel;
import jp.groupsession.v2.rng.model.RngTemplateFormModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.rng.rng090.Rng090Action;
import jp.groupsession.v2.rng.rng090.Rng090Biz;
import jp.groupsession.v2.rng.rng090.Rng090ParamModel;
import jp.groupsession.v2.rng.rng110keiro.RngTemplateKeiroSave;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 テンプレート登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng090knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng090knBiz.class);
    /** Connection */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * @param con Connection
     * @param reqMdl リクエストモデル
     */
    Rng090knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tempDir 添付ファイルディレクトリ
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     */
    public void initDsp(
            Rng090knParamModel paramMdl,
            GSTemporaryPathModel tempDir,
            Connection con,
            RequestModel reqMdl) throws Exception {

        RngBiz rBiz = new RngBiz(con__);

        //内容の改行を反映し、設定
        paramMdl.setRng090knViewContent(StringUtilHtml.transToHTml(
                        NullDefault.getString(paramMdl.getRng090content(), "")));
        paramMdl.setRng090knViewBiko(StringUtilHtml.transToHTml(
                NullDefault.getString(paramMdl.getRng090biko(), "")));

        //添付ファイル一覧を設定
        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setRng090FileLabelList(cmnBiz.getTempFileLabelList(tempDir.getTempPath()));
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("cmn.category.no");

        RngAconfModel aconf = rBiz.getRngAconf(con__);
        paramMdl.setIdSelectable(aconf.getRarRngid());
        if (aconf.getRarRngid() != RngConst.RAR_SINSEI_NONE) {
            int rngIdSid = paramMdl.getRng090idSid();
            //稟議ID情報を取得
            RngIdDao idDao = new RngIdDao(con__);
            RingiIdModel model = null;
            if (rngIdSid != -1) {
                model = idDao.selectData(rngIdSid);
            }
            if (model == null) {
                rngIdSid = aconf.getRarRngidDefSid();
                model = idDao.selectData(rngIdSid);
            }
            if (model == null) {
                rngIdSid = aconf.getRarRngidDefSid();
                model = idDao.selectData(rngIdSid);
            }
            if (model == null) {
                paramMdl.setIdSelectable(RngConst.RAR_SINSEI_NONE);
                paramMdl.setRng090idSid(-1);
            } else {
                paramMdl.setRng090idSid(model.getRngSid());
                paramMdl.setRng090idTitle(model.getRngTitle());
                paramMdl.setIdPrefManualEditable(model.getRngManual());
            }
        }

        //カテゴリ名を設定
        RngTemplateCategoryDao dao = new RngTemplateCategoryDao(con);
        paramMdl.setRng090knCatName(NullDefault.getString(
                dao.select(paramMdl.getRng090CatSid()).getRtcName(), msg));

        //経路情報の所為表示を行う。
        RngChannelTemplateModel rctModel = null;
        RngChannelTemplateDao rctDao = new RngChannelTemplateDao(con__);
        if (paramMdl.getRng090useKeiroTemplate() == 0) {
            rctModel = rctDao.select(
                            paramMdl.getRng090KeiroTemplateSid(),
                            paramMdl.getRng090KeiroTemplateUsrSid()
                            );
        }
        if (rctModel != null) {
            RngTemplateKeiroSave saveBiz =
                    RngTemplateKeiroSave.createInstanceForRCT(
                            paramMdl.getRng090KeiroTemplateSid(),
                            paramMdl.getRng090KeiroTemplateUsrSid(),
                            reqMdl__, con__);
            paramMdl.setRng090keiro(saveBiz.loadRng110Keiro());
            paramMdl.setRng090KeiroTemplateName(rctModel.getRctName());
        }
        paramMdl.getRng090keiro().dspInit(reqMdl, con__);

        //フォーム情報の初期表示を行う。
        paramMdl.getRng090template().setFormTable(paramMdl.getRng090templateJSON());
        paramMdl.getRng090template().dspInit(reqMdl, con__, tempDir);
    }

    /**
     *
     * <br>[機  能] 変更前データモデルを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl 変更後データモデル
     * @param mres メッセージリソース
     * @return 変更前データモデル
     * @throws SQLException SQL実行時例外
     */
    private Rng090ParamModel getOldTemplateModel(Rng090knParamModel paramMdl, MessageResources mres)
            throws SQLException {

        Rng090ParamModel oldModel = new Rng090ParamModel();
        oldModel.setRngSelectTplSid(paramMdl.getRngSelectTplSid());
        //変更後データのSIDから変更前データをロードする
        Rng090Biz rng090Biz = new Rng090Biz(con__, reqMdl__);
        RngTemplateBiz rtBiz = new RngTemplateBiz();
        int rstSid = paramMdl.getRngSelectTplSid();
        RngTemplateModel rtModel = rtBiz.getRtpModel(rstSid, con__);
        if (rtModel != null) {
            rng090Biz.loadData(oldModel, rtModel);
        }
        rng090Biz.initDsp(oldModel,
                GSTemporaryPathModel.getInstance(reqMdl__, RngConst.PLUGIN_ID_RINGI,
                                                Rng090Action.DIRID), mres);

        return oldModel;
    }
    /**
     * <br>[機  能] 稟議テンプレートの登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param controller MlCountMtController
     * @param userSid ログインユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param mres メッセージリソース
     * @throws Exception 実行時例外
     */
    public void registRngTpl(
            Rng090knParamModel paramMdl,
            MlCountMtController controller,
            int userSid,
            String appRootPath,
            GSTemporaryPathModel tempDir,
            MessageResources mres) throws Exception {

        int procMode = paramMdl.getRngTplCmdMode();
        Rng090Biz rng090Biz = new Rng090Biz(con__, reqMdl__);
        //初期化処理
        rng090Biz.initDsp(paramMdl, tempDir, mres);
        RngTemplateDao dao = new RngTemplateDao(con__);
        final int rtpSid = paramMdl.getRngSelectTplSid();
        final int rtpVersion = dao.getMaxVerNo(rtpSid);

        //ファイルの変更判定
        CommonBiz cmnBiz = new CommonBiz();
        UDate accsessDate = new UDate();
        accsessDate.setTimeStamp(paramMdl.getAccessDateString());
        if (cmnBiz.isHaveNewFile(tempDir.getTempPath(), accsessDate)) {
            paramMdl.setFlgFileChange(1);
        }

        //編集の場合変更の有無を確認し、変更がなければ更新しない。
        Rng090ParamModel oldModel = null;
        if (procMode == RngConst.RNG_CMDMODE_EDIT) {
            oldModel = this.getOldTemplateModel(paramMdl, mres);
            paramMdl.setRng090KeiroVer(oldModel.getRng090KeiroVer()); // 最新の経路バージョンをセット
            if (!paramMdl.equalKeiroSaveData(oldModel)) {
                // 経路変更あり + 経路テンプレート使用しない場合 → 経路バージョンを上げる
                if (paramMdl.getRng090useKeiroTemplate() != 0) {
                    paramMdl.setRng090KeiroVer(oldModel.getRng090KeiroVer() + 1);
                }
            } else if (paramMdl.equalSaveData(oldModel)) {
                return; // テンプレート変更が無い為、更新終了
            }
        }

        //稟議テンプレートモデルを作成
        RngTemplateModel rtMdl = __createRTModel(
                paramMdl, controller, userSid, appRootPath, procMode);

        UDate now = new UDate();
        //フォームに対する添付
        FormBuilder fb = paramMdl.getRng090template();
        fb.setFormTable(paramMdl.getRng090templateJSON());
        Collection<FormCellPrefarence> cells = fb.getFormMap().values();

        boolean bEdit = false;
        for (FormCell cell : cells) {
            if (cell.getType() == EnumFormModelKbn.file) {
                Temp temp = (Temp) cell.getBody();
                GSTemporaryPathModel cellTempMdl = tempDir;
                if (paramMdl.getRng090rtpSpecVer() == RngConst.RNG_RTP_SPEC_VER_A480) {
                    cellTempMdl = new GSTemporaryPathModel(tempDir, String.valueOf(cell.getSid()));
                }
                List < String > binSidList = cmnBiz.insertBinInfo(
                        con__, cellTempMdl.getTempPath(), appRootPath, controller, userSid, now);
                //稟議添付情報の登録
                //もし、バイナリSIDがnullじゃなければ、
                if (binSidList != null) {
                    String[] sample = binSidList.toArray(new String[binSidList.size()]);
                    temp.setSample(sample);
                    bEdit = true;
                    //バイナリSIDの数だけinsertを行う
                    RngTemplateBinModel binMdl = new RngTemplateBinModel();
                    RngTemplateBinDao binDao = new RngTemplateBinDao(con__);
                    for (String binSid : binSidList) {
                        binMdl.setRtpSid(rtMdl.getRtpSid());
                        binMdl.setBinSid(Long.parseLong(binSid));
                        binMdl.setRtpVer(rtMdl.getRtpVer());
                        binDao.insert(binMdl);
                    }
                } else {
                    log__.debug("// 添付情報は更新しませんでした。");
                }
            }
        }
        if (bEdit) {
            JSONArray jsonArray = JSONArray.fromObject(fb.getFormTable());
            String json = jsonArray.toString();
            paramMdl.setRng090templateJSON(json);
            rtMdl.setRtpForm(paramMdl.getRng090templateJSON());
        }

        RngTemplateDao rtDao = new RngTemplateDao(con__);
        //稟議テンプレートを登録or更新
        if (procMode == RngConst.RNG_CMDMODE_EDIT) {

            log__.debug("// 稟議テンプレートを更新しました。");
            //カテゴリ変更があった場合はソート値の更新も行う
            if (__moveCategory(paramMdl, rtMdl.getRtpSid())) {
                rtDao.updateChangeCategory(rtMdl);
            } else {
                //カテゴリ変更がなかった場合はソート値は一切変更しない
                rtDao.updateNotChangeCategory(rtMdl);
            }
            //旧バージョンテンプレートから最新フラグの除去
            rtDao.removeMaxverKbn(rtMdl.getRtpSid());
        }
        rtDao.insert(rtMdl);
        //フォーム情報の登録を行う。
        paramMdl.getRng090template().setFormTable(paramMdl.getRng090templateJSON());
        __saveFormTable(paramMdl.getRng090template(),
                rtMdl.getRtpSid(), rtMdl.getRtpVer());

        //経路情報の登録を行う。
        if (paramMdl.getRng090useKeiroTemplate() == 1) {
            RngTemplateKeiroSave saveBiz =
                    RngTemplateKeiroSave.createInstanceForRTP(
                            rtMdl.getRtpSid(), rtMdl.getRtpVer(), reqMdl__, con__);
            saveBiz.save(paramMdl.getRng090keiro(), true, controller);
        }

        //稟議テンプレート情報のデータ使用量を登録
        RngUsedDataBiz usedDataBiz = new RngUsedDataBiz(con__);
        usedDataBiz.insertTemplateDataSize(Arrays.asList(rtMdl), true);
    }

    /**
     *
     * <br>[機  能] 稟議テンプレートフォーム情報の保管
     * <br>[解  説]
     * <br>[備  考]
     * @param builder FormBuilder
     * @param rtpSid rtpSid
     * @param rtpVersion rtpVersion
     * @throws SQLException SQL実行時例外
     */
    private void __saveFormTable(FormBuilder builder,
            int rtpSid,
            int rtpVersion) throws SQLException {
        Map<FormAccesser, FormCell> formMap = new HashMap<FormAccesser, FormCell>();
        builder.makeFormMap(formMap, 0);
        RngTemplateFormDao dao = new RngTemplateFormDao(con__);

        for (Entry<FormAccesser, FormCell> entry : formMap.entrySet()) {
            RngTemplateFormModel model = new RngTemplateFormModel();
            FormAccesser access = entry.getKey();
            int sid = access.getFormSid();
            if (access.getRowNo() > 1) {
                continue;
            }
            FormCell cell = entry.getValue();
            model.setRtpSid(rtpSid);
            model.setRtpVer(rtpVersion);
            model.setRtfSid(sid);
            model.setRtfTitle(cell.getTitle());
            model.setRtfType(cell.getType().getValue());
            model.setRtfId(cell.getFormID());
            model.setRtfRequire(cell.getRequire());
            dao.insert(model);
        }
    }

    /**
     * <br>[機  能] 稟議テンプレートモデルを作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param controller MlCountMtController
     * @param userSid ログインユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param mode 処理モード
     * @return RngTemplateModel
     * @throws SQLException SQL実行時例外
     */
    private RngTemplateModel __createRTModel(
            Rng090knParamModel paramMdl,
            MlCountMtController controller,
            int userSid,
            String appRootPath,
            int mode) throws SQLException {

        log__.debug("稟議テンプレートモデルを作成します。");
        RngTemplateModel mdl = new RngTemplateModel();
        RngTemplateDao dao = new RngTemplateDao(con__);
        int rngTemplateMode = paramMdl.getRngTemplateMode();

        int rngSid;
        //もし処理モードが追加なら、
        if (mode == RngConst.RNG_CMDMODE_ADD) {
            log__.debug("//採番マスタから稟議SIDを取得。");
            rngSid = (int) controller.getSaibanNumber(RngConst.SBNSID_RINGI,
                                                RngConst.SBNSID_SUB_RINGI_TEMPLATE,
                                                userSid);
            int maxSort = dao.getMaxSort(rngTemplateMode, userSid, paramMdl.getRng090CatSid());
            mdl.setRtpSort(maxSort + 1);
        //そうでないなら、
        } else {
            log__.debug("//現在選択されている稟議テンプレートのSIDを取得。");
            rngSid = paramMdl.getRngSelectTplSid();
            //カテゴリ間の移動がある場合、移動先のソート最大値+1をセット
            if (__moveCategory(paramMdl, rngSid)) {
                int maxSort = dao.getMaxSort(rngTemplateMode, userSid, paramMdl.getRng090CatSid());
                mdl.setRtpSort(maxSort + 1);
            } else {
                int sort = dao.getSort(rngSid);
                mdl.setRtpSort(sort);
            }
        }

        UDate now = new UDate();
        mdl.setRtpSid(rngSid);
        mdl.setRtpTitle(paramMdl.getRng090title());
        mdl.setRtpRngTitle(paramMdl.getRng090rngTitle());
        if (rngTemplateMode != RngConst.RNG_TEMPLATE_SHARE) {
            mdl.setUsrSid(userSid);
        }
        mdl.setRtpType(rngTemplateMode);
        mdl.setRtpAuid(userSid);
        mdl.setRtpAdate(now);
        mdl.setRtpEuid(userSid);
        mdl.setRtpEdate(now);
        mdl.setRtcSid(paramMdl.getRng090CatSid());
        mdl.setRtpVer(dao.getMaxVerNo(rngSid) + 1);
        mdl.setRtpMaxverKbn(RngTemplateDao.MAXVER_KBN_ON);
        mdl.setRtpForm(paramMdl.getRng090templateJSON());
        if (paramMdl.getRng090useKeiroTemplate() == 0) {
            mdl.setRctSid(paramMdl.getRng090KeiroTemplateSid());
            mdl.setRctUsrSid(paramMdl.getRng090KeiroTemplateUsrSid());
        }
        mdl.setRtpBiko(paramMdl.getRng090biko());
        if (paramMdl.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE
              || paramMdl.getIdSelectable() != RngConst.RAR_SINSEI_TEMP) {
            mdl.setRtpIdformatSid(-1);
        } else {
            mdl.setRtpIdformatSid(paramMdl.getRng090idSid());
        }

        if (paramMdl.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
            mdl.setRtpIdmanual(RngConst.RAR_SINSEI_MANUAL_KYOKA);
        } else {
            mdl.setRtpIdmanual(paramMdl.getRng090idPrefManual());
        }
        mdl.setRtpSpecVer(paramMdl.getRng090rtpSpecVer());
        mdl.setRctVer(paramMdl.getRng090KeiroVer());
        mdl.setRtpUseApiconnect(0);
        mdl.setRtpApiconnectComment(null);

        return mdl;
    }


    /**
     * <br>[機  能] カテゴリ間の移動があるか判断
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tplSid テンプレートSID
     * @return カテゴリ間の移動があるか true=あり false=なし
     * @throws SQLException SQL実行時例外
     */
    private boolean __moveCategory(Rng090knParamModel paramMdl, int tplSid)
    throws SQLException {

        RngTemplateDao dao = new RngTemplateDao(con__);
        //編集mode以外に、カテゴリ間の移動はありえない
        if (paramMdl.getRngTplCmdMode() == RngConst.RNG_CMDMODE_EDIT) {
            if (paramMdl.getRng090CatSid() != dao.select(tplSid).getRtcSid()) {
                return true;
            }
        }
        return false;
    }

}
