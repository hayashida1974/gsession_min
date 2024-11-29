package jp.groupsession.v2.rng.biz;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.AccessUrlBiz;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.LoggingBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginControlDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginControlMemberDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormAccesser;
import jp.groupsession.v2.cmn.formbuilder.FormCell;
import jp.groupsession.v2.cmn.formbuilder.FormInputBuilder;
import jp.groupsession.v2.cmn.formmodel.AbstractFormModel;
import jp.groupsession.v2.cmn.formmodel.Block;
import jp.groupsession.v2.cmn.formmodel.BlockList;
import jp.groupsession.v2.cmn.formmodel.Calc;
import jp.groupsession.v2.cmn.formmodel.CheckBox;
import jp.groupsession.v2.cmn.formmodel.ComboBox;
import jp.groupsession.v2.cmn.formmodel.Comment;
import jp.groupsession.v2.cmn.formmodel.DateBox;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.formmodel.NumberBox;
import jp.groupsession.v2.cmn.formmodel.RadioButton;
import jp.groupsession.v2.cmn.formmodel.SimpleUserSelect;
import jp.groupsession.v2.cmn.formmodel.Sum;
import jp.groupsession.v2.cmn.formmodel.Temp;
import jp.groupsession.v2.cmn.formmodel.Textarea;
import jp.groupsession.v2.cmn.formmodel.Textbox;
import jp.groupsession.v2.cmn.formmodel.TimeBox;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnLogModel;
import jp.groupsession.v2.cmn.model.base.CmnPluginControlModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rng.IRingiListener;
import jp.groupsession.v2.rng.RingiListenerModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.dao.RngAconfDao;
import jp.groupsession.v2.rng.dao.RngBinDao;
import jp.groupsession.v2.rng.dao.RngCopyKeiroStepDao;
import jp.groupsession.v2.rng.dao.RngCopyKeirostepSelectDao;
import jp.groupsession.v2.rng.dao.RngFormdataDao;
import jp.groupsession.v2.rng.dao.RngIdDao;
import jp.groupsession.v2.rng.dao.RngKeiroStepDao;
import jp.groupsession.v2.rng.dao.RngKeirostepSelectDao;
import jp.groupsession.v2.rng.dao.RngRndataDao;
import jp.groupsession.v2.rng.dao.RngSingiDao;
import jp.groupsession.v2.rng.dao.RngTemplateBinDao;
import jp.groupsession.v2.rng.dao.RngTemplateCategoryDao;
import jp.groupsession.v2.rng.dao.RngTemplateDao;
import jp.groupsession.v2.rng.dao.RngTemplatecategoryAdmDao;
import jp.groupsession.v2.rng.dao.RngTemplatecategoryUseDao;
import jp.groupsession.v2.rng.dao.RngUconfDao;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.model.RingiRequestModel;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngBinModel;
import jp.groupsession.v2.rng.model.RngDeleteModel;
import jp.groupsession.v2.rng.model.RngFormdataModel;
import jp.groupsession.v2.rng.model.RngKeirostepSelectModel;
import jp.groupsession.v2.rng.model.RngRndataModel;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.rng.model.RngUconfModel;
import jp.groupsession.v2.rng.rng210.Rng210ListModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 稟議プラグインで使用される共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngBiz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(RngBiz.class);

    /** 処理モード 申請 */
    public static final int ENTRYMODE_SINSEI = 0;
    /** 処理モード 草稿 */
    public static final int ENTRYMODE_SOUKOU = 1;
    /** 申請モード 申請 */
    public static final int APPLMODE_APPL = 0;
    /** 申請モード 草稿 */
    public static final int APPLMODE_REAPPL = 1;

    /** DBコネクション */
    private Connection con__ = null;
    /** 採番コントローラー */
    private MlCountMtController cntCon__ = null;

    /**
     * <p>コンストラクタ
     * @param con Connection
     */
    public RngBiz(Connection con) {
        con__ = con;
    }

    /**
     * <p>コンストラクタ
     * @param con Connection
     * @param cntCon MlCountMtController
     */
    public RngBiz(Connection con, MlCountMtController cntCon) {
        con__ = con;
        cntCon__ = cntCon;
    }

    /**
     * <br>[機  能] 稟議添付ファイル情報を元に添付ファイルを指定したテンポラリディレクトリに作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rngSid 稟議SID
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param now 現在日時
     * @param reqMdl リクエスト情報
     * @throws SQLException 実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setRingiTempFileData(Connection con, int rngSid,
                                    String appRoot, String tempDir, UDate now,
                                    RequestModel reqMdl)
    throws SQLException, IOException, IOToolsException, TempFileException {

        setRingiTempFileData(con, rngSid, 0, appRoot, tempDir, now, reqMdl);

    }

    /**
     * <br>[機  能] 稟議添付ファイル情報を元に添付ファイルを指定したテンポラリディレクトリに作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rngSid 稟議SID
     * @param userSid ユーザSID
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param now 現在日時
     * @param reqMdl リクエスト情報
     * @throws SQLException 実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setRingiTempFileData(Connection con, int rngSid, int userSid,
                                    String appRoot, String tempDir, UDate now,
                                    RequestModel reqMdl)
    throws SQLException, IOException, IOToolsException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        String dateStr = now.getDateString(); //現在日付の文字列(YYYYMMDD)
        RingiDao ringiDao = new RingiDao(con);
        List <CmnBinfModel> rngBinList = ringiDao.getRingiTmpFileList(rngSid, userSid);

        if (rngBinList != null && rngBinList.size() > 0) {
            String[] binSids = new String[rngBinList.size()];

            //バイナリSIDの取得
            for (int i = 0; i < rngBinList.size(); i++) {
                binSids[i] = String.valueOf(rngBinList.get(i).getBinSid());
            }

            //取得したバイナリSIDからバイナリ情報を取得
            List<CmnBinfModel> cmnBinList = cmnBiz.getBinInfo(con, binSids,
                                                            reqMdl.getDomain());

            int fileNum = 1;
            for (CmnBinfModel binData : cmnBinList) {
                cmnBiz.saveTempFile(dateStr, binData, appRoot, tempDir, fileNum);
                fileNum++;
            }
        }
    }

    /**
     * <br>[機  能] 稟議テンプレート添付ファイル情報を元に添付ファイルを指定したテンポラリディレクトリに作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rngTmpSid 稟議テンプレートSID
     * @param rngTmpVer 稟議テンプレートバージョン
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param reqMdl リクエスト情報
     * @throws SQLException 実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setRingiTemplateTempFileData(Connection con, int rngTmpSid,
                                    int rngTmpVer,
                                    String appRoot, String tempDir,
                                    RequestModel reqMdl)
    throws SQLException, IOException, IOToolsException, TempFileException {

        UDate now = new UDate();
        String dateStr = now.getDateString(); //現在日付の文字列(YYYYMMDD)
        RingiDao ringiDao = new RingiDao(con);
        List <CmnBinfModel> rngTmpBinList =
                ringiDao.getRingiTemplateTmpFileList(rngTmpSid, rngTmpVer);
        CommonBiz cmnBiz = new CommonBiz();

        if (rngTmpBinList != null && rngTmpBinList.size() > 0) {
            String[] binSids = new String[rngTmpBinList.size()];

            //バイナリSIDの取得
            for (int i = 0; i < rngTmpBinList.size(); i++) {
                binSids[i] = String.valueOf(rngTmpBinList.get(i).getBinSid());
            }

            //取得したバイナリSIDからバイナリ情報を取得
            List<CmnBinfModel> cmnBinList = cmnBiz.getBinInfo(con, binSids,
                                                            reqMdl.getDomain());

            int fileNum = 1;
            for (CmnBinfModel binData : cmnBinList) {
                cmnBiz.saveTempFile(dateStr, binData, appRoot, tempDir, fileNum);
                fileNum++;
            }
        }
    }

    /**
     * <br>[機  能] 添付ファイルのダウンロードを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     * @param tempDir テンポラリディレクトリ
     * @param fileId 添付ファイルID
     * @throws Exception 添付ファイルのダウンロードに失敗
     */
    public void downloadTempFile(HttpServletRequest req, HttpServletResponse res,
                                String tempDir, String fileId)
    throws Exception {

        //オブジェクトファイルを取得
        ObjectFile objFile = new ObjectFile(tempDir, fileId.concat(GSConstCommon.ENDSTR_OBJFILE));
        Object fObj = objFile.load();
        Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
        //添付ファイル保存用のパスを取得する(フルパス)
        String filePath = tempDir + fileId.concat(GSConstCommon.ENDSTR_SAVEFILE);
        filePath = IOTools.replaceFileSep(filePath);
        //ファイルをダウンロードする
        TempFileUtil.downloadAtachment(req, res, filePath, fMdl.getFileName(), Encoding.UTF_8);
    }

    /**
     * <br>[機  能] 稟議の申請を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議申請情報
     * @param pluginConfig プラグイン情報
     * @param smlPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエスト情報
     * @return 申請した稟議の稟議SID
     * @throws Exception 実行時例外
     */
    public int applyRingi(RingiRequestModel model,
                            PluginConfig pluginConfig, boolean smlPluginUseFlg,
                            RequestModel reqMdl)
    throws Exception {

        return entryRingiData(model,
                            ENTRYMODE_SINSEI, RngConst.RNG_CMDMODE_ADD,
                            pluginConfig, smlPluginUseFlg, reqMdl);
    }

    /**
     * <br>[機  能] 稟議の申請を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議申請情報
     * @param mode 処理モード 0:申請 1:草稿
     * @param cmdMode 登録モード 0:登録 1:更新
     * @param pluginConfig プラグイン情報
     * @param smlPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエスト情報
     * @return 申請した稟議の稟議SID
     * @throws Exception 実行時例外
     */
    public int entryRingiData(
            RingiRequestModel model,
            int mode,
            int cmdMode,
            PluginConfig pluginConfig,
            boolean smlPluginUseFlg,
            RequestModel reqMdl)
    throws Exception {
        log__.debug("start");

        //稟議情報の作成
        RngRndataModel rngMdl = __createRngData(cntCon__, model, mode, cmdMode);

        //稟議情報の登録
        RngRndataDao rngDao = new RngRndataDao(con__);
        if (cmdMode == RngConst.RNG_CMDMODE_ADD) {
            rngDao.insert(rngMdl);
        } else if (cmdMode == RngConst.RNG_CMDMODE_EDIT) {
            rngDao.update(rngMdl);
        }

        log__.debug("end");

        return rngMdl.getRngSid();

    }

    /**
     * 稟議リスナーの処理を実行します
     * @param model 稟議申請情報
     * @param mode 処理モード 0:申請 1:草稿
     * @param rngSid 稟議SID
     * @param pluginConfig プラグイン情報
     * @param smlPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     * */
    public void manageRingiLisner(RingiRequestModel model, int mode, int rngSid,
            PluginConfig pluginConfig, boolean smlPluginUseFlg, RequestModel reqMdl)
    throws Exception {
        if (mode == ENTRYMODE_SINSEI) {
            IRingiListener[] listenerList = getRingiListeners(pluginConfig);
            RingiListenerModel listenerMdl = new RingiListenerModel();
            listenerMdl.setCon(con__);
            listenerMdl.setCntCon(cntCon__);
            listenerMdl.setAppRootPath(model.getAppRootPath());
            listenerMdl.setRngSid(rngSid);
            listenerMdl.setPluginConfig(pluginConfig);
            listenerMdl.setSmailPluginFlg(smlPluginUseFlg);
            String url = createThreadUrl(reqMdl, rngSid);
            //URLを設定
            listenerMdl.setRngUrl(url);

            //申請通知設定
            for (IRingiListener listener : listenerList) {
                listener.sendSmlMain(listenerMdl, reqMdl, RngConst.STATUS_SOURCE_APPLY_SML);
            }
        }
    }
    /**
     *
     * <br>[機  能] 稟議登録内容の共通部の複製
     * <br>[解  説]
     * <br>[備  考]
     * @param rfdModel rfdModel
     * @return RngFormdataModel
     */
    private RngFormdataModel __cloneFormData(RngFormdataModel rfdModel) {
        RngFormdataModel ret = new RngFormdataModel();
        //稟議SID
        ret.setRngSid(rfdModel.getRngSid());
        //フォームSID
        ret.setRfdSid(rfdModel.getRfdSid());
        //フォームID
        ret.setRfdId(rfdModel.getRfdId());
        //行番号
        ret.setRfdRowno(rfdModel.getRfdRowno());
        //登録者ID
        ret.setRfdAuid(rfdModel.getRfdAuid());
        //登録日
        ret.setRfdAdate(rfdModel.getRfdAdate());
        //更新者ID
        ret.setRfdEuid(rfdModel.getRfdEuid());
        //更新日
        ret.setRfdEdate(rfdModel.getRfdEdate());
        return ret;
    }
    /**
     *
     * <br>[機  能] 稟議申請画面におけるフォームを登録する
     * <br>[解  説]
     * <br>[備  考]3
     * @param rngReqMdl 稟議リクエストモデル
     * @param formModel フォームの入力値
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void entryFormData(RingiRequestModel rngReqMdl, FormInputBuilder formModel)
           throws SQLException, IOException, IOToolsException, TempFileException {

       if (rngReqMdl == null) {
           return;
       }

       int rngSid = rngReqMdl.getRngSid();

       new ArrayList<RngFormdataModel>();
       UDate now = new UDate();

       // 添付ファイル対応の為、申請者(=0)が登録したバイナリー情報の論理削除＋稟議添付情報の削除を一括で行う
       RingiDao ringiDao = new RingiDao(con__);
       RngBinDao binDao = new RngBinDao(con__);
       ringiDao.removeRngBinData(rngSid, 0, 0, now);
       binDao.delete(rngSid, 0);

       //フォームモデル作成
       List<RngFormdataModel> rfdMdlList = createTemplateFormList(rngReqMdl, formModel, now, true);

       RngFormdataDao rfdDao = new RngFormdataDao(con__);
       //旧データの削除
       rfdDao.deleteRngSid(rngSid);

       //登録
       rfdDao.insert(rfdMdlList);
    }

    /**
     *
     * <br>[機  能] 稟議テンプレート情報Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rngReqMdl 稟議リクエストモデル
     * @param formModel フォームの入力値
     * @param now 現在日時
     * @param entryFlg true: 添付ファイル情報の登録、 false: 添付ファイル情報を登録しない
     * @return 稟議テンプレート情報Model
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<RngFormdataModel> createTemplateFormList(
            RingiRequestModel rngReqMdl, FormInputBuilder formModel, UDate now,
            boolean entryFlg)
    throws SQLException, IOException, IOToolsException, TempFileException {

        int rngSid = rngReqMdl.getRngSid();
        int usrSid = rngReqMdl.getUserSid();

        RngFormdataModel rfdModel = null;
        List<RngFormdataModel> rfdMdlList = new ArrayList<RngFormdataModel>();

        //フォームモデル作成
        Map<FormAccesser, FormCell> inputedForm = formModel.getFormMap();
        for (Map.Entry<FormAccesser, FormCell> entry : inputedForm.entrySet()) {
            rfdModel = new RngFormdataModel();
            FormAccesser accesser = entry.getKey();
            FormCell cell = entry.getValue();

            //稟議SID
            rfdModel.setRngSid(rngSid);
            //フォームSID
            rfdModel.setRfdSid(accesser.getFormSid());
            //フォームID
            rfdModel.setRfdId(cell.getFormID());
            //行番号
            rfdModel.setRfdRowno(accesser.getRowNo());
            //登録者ID
            rfdModel.setRfdAuid(usrSid);
            //登録日
            rfdModel.setRfdAdate(now);
            //更新者ID
            rfdModel.setRfdEuid(usrSid);
            //更新日
            rfdModel.setRfdEdate(now);

            //入力内容
            if (entry.getValue().getType() != null) {
                EnumFormModelKbn type = cell.getType();
                AbstractFormModel instance = cell.getBody();
                String value = null;

                switch (type) {
                case label:
                    Comment comment = (Comment) instance;
                    value = comment.getValue();
                    rfdModel.setRfdValue(value);
                    rfdMdlList.add(rfdModel);
                    break;
                case textbox:
                    Textbox textbox = (Textbox) instance;
                    value = textbox.getValue();
                    rfdModel.setRfdValue(value);
                    rfdMdlList.add(rfdModel);
                    break;
                case textarea:
                    Textarea textarea = (Textarea) instance;
                    value = textarea.getValue();
                    rfdModel.setRfdValue(value);
                    rfdMdlList.add(rfdModel);
                    break;
                case date:
                    DateBox dateBox = (DateBox) instance;
                    value = dateBox.getValue();
                    rfdModel.setRfdValue(value);
                    rfdMdlList.add(rfdModel);
                    break;
                case time:
                    TimeBox timeBox = (TimeBox) instance;
                    value = timeBox.getValue();
                    rfdModel.setRfdValue(value);
                    rfdMdlList.add(rfdModel);
                    break;
                case number:
                    NumberBox numberBox = (NumberBox) instance;
                    value = numberBox.getValue();
                    rfdModel.setRfdValue(value);
                    rfdMdlList.add(rfdModel);
                    break;
                case radio:
                    RadioButton radioButton = (RadioButton) instance;
                    value = radioButton.getSelected();
                    rfdModel.setRfdValue(value);
                    rfdMdlList.add(rfdModel);
                    break;
                case combo:
                    ComboBox comboBox = (ComboBox) instance;
                    value = comboBox.getSelected();
                    rfdModel.setRfdValue(value);
                    rfdMdlList.add(rfdModel);
                    break;
                case check:
                    CheckBox checkBox = (CheckBox) instance;
                    for (String selected : checkBox.getSelected()) {
                        rfdModel.setRfdValue(selected);
                        rfdMdlList.add(rfdModel);
                        rfdModel = __cloneFormData(rfdModel);
                    }
                    break;
                case sum:
                    Sum sum = (Sum) instance;
                    value = sum.getValue();
                    rfdModel.setRfdValue(value);
                    rfdMdlList.add(rfdModel);
                    break;
                case calc:
                    Calc calc = (Calc) instance;
                    value = calc.getValue();
                    rfdModel.setRfdValue(value);
                    rfdMdlList.add(rfdModel);
                    break;
                case user:
                    SimpleUserSelect simple = (SimpleUserSelect) instance;
                    for (String selected : simple.getSelected()) {
                        rfdModel.setRfdValue(selected);
                        rfdMdlList.add(rfdModel);
                        rfdModel = __cloneFormData(rfdModel);
                    }
                    break;
                case group:
                    GroupComboModel group = (GroupComboModel) instance;
                    for (String selected : group.getSelectedNoselisVoid()) {
                        rfdModel.setRfdValue(selected);
                        rfdMdlList.add(rfdModel);
                        rfdModel = __cloneFormData(rfdModel);
                    }
                    break;
                case file:
                    if (entryFlg) {
                        Temp temp = (Temp) instance;
                        //稟議添付情報の登録
                        List < String > binSidList = insertRngBin(
                                rngReqMdl.getRngSid(), 0, now,
                                rngReqMdl.getAppRootPath(), temp.getTempPath().getTempPath(),
                                RngConst.RNG_CMDMODE_ADD);
                        // ログ出力時の為、一旦NULLをセット
                        temp.setSample(null);
                        temp.setFiles(null);
                        if (binSidList != null) {
                            // フォーム情報に登録したバイナリSID一覧をセットする
                            for (String binSid : binSidList) {
                                rfdModel.setRfdValue(binSid);
                                rfdMdlList.add(rfdModel);
                                rfdModel = __cloneFormData(rfdModel);
                            }
                            // ログ出力させる為にここでセット
                            temp.setFiles(binSidList.toArray(new String[binSidList.size()]));
                        }
                    }
                    break;
                case blocklist:
                    BlockList bl = (BlockList) instance;
                    value = String.valueOf(bl.getLength());
                    rfdModel.setRfdValue(value);
                    rfdMdlList.add(rfdModel);
                    break;
                default:
                  break;
                }
            }
        }

        return rfdMdlList;
    }

   /**
    *
    * <br>[機  能] 稟議申請画面におけるフォームをログ出力
    * <br>[解  説]
    * <br>[備  考]
    * @param formModel フォームの入力値
    * @param isDef デフォルト値使用フラグ
    * @return 出力されるフォームログ
    */
   public ArrayList<LabelValueBean> outputFormData(FormInputBuilder formModel, boolean isDef) {
       ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>();

       Map<FormAccesser, FormCell> inputedForm = formModel.getFormMap();
       for (Map.Entry<FormAccesser, FormCell> entry : inputedForm.entrySet()) {
           ret.add(outputFormCell(entry.getValue(), isDef));
       }
       return ret;
   }

   /**
    * <br>[機  能] フォームセルのログ出力
    * <br>[解  説]
    * <br>[備  考]
    * @param cell フォームセル情報
    * @param isDef デフォルト値使用フラグ
    * @return 出力されるフォームセルのログ
    */
   public LabelValueBean outputFormCell(FormCell cell, boolean isDef) {
       //入力内容
       String title  = "";
       String value  = "";
       String defval = "";
       if (cell != null && cell.getType() != null) {
           AbstractFormModel instance = cell.getBody();
           title = cell.getTitle();

           if (instance != null) {
               switch (cell.getType()) {
               case label:
                   value = ((Comment) instance).getValue();
                   defval = value;
                   break;
               case textbox:
                   value  = ((Textbox) instance).getValue();
                   defval = ((Textbox) instance).getDefaultValue();
                   break;
               case textarea:
                   value  = ((Textarea) instance).getValue();
                   defval = ((Textarea) instance).getDefaultValue();
                   break;
               case date:
                   value  = ((DateBox) instance).getValue();
                   break;
               case number:
                   value  = ((NumberBox) instance).getValue();
                   defval = ((NumberBox) instance).getDefaultValue();
                   break;
               case radio:
                   RadioButton radioBtn = (RadioButton) instance;
                   if (radioBtn.getSelected() != null) {
                       value = radioBtn.getSelected();
                   }
                   if (radioBtn.getDefaultValue() != null) {
                       defval = radioBtn.getDefaultValue();
                   }
                   break;
               case combo:
                   ComboBox comboBox = (ComboBox) instance;
                   if (comboBox.getSelected() != null) {
                       value = comboBox.getSelected();
                   }
                   if (comboBox.getDefaultValue() != null) {
                       defval = comboBox.getDefaultValue();
                   }
                   break;
               case check:
                   CheckBox checkBox = (CheckBox) instance;
                   if (checkBox.getSelected() != null) {
                       String[] selectList = checkBox.getSelected();
                       for (int i = 0; i < selectList.length; i++) {
                           if (value.length() > 0) {
                               value += "," + selectList[i];
                           } else {
                               value += selectList[i];
                           }
                       }
                   }
                   if (checkBox.getDefaultValue() != null) {
                       List<String> selectList = checkBox.getDefaultValue();
                       for (int i = 0; i < selectList.size(); i++) {
                           if (defval.length() > 0) {
                               defval += "," + selectList.get(i);
                           } else {
                               defval += selectList.get(i);
                           }
                       }
                   }
                   break;
               case sum:
                   Sum sum = (Sum) instance;
                   if (sum.getValue() != null) {
                       value = sum.getValue();
                   }
                   break;
               case calc:
                   Calc calc = (Calc) instance;
                   if (calc.getValue() != null) {
                       value = calc.getValue();
                   }
                   break;
               case user:
                   SimpleUserSelect simple = (SimpleUserSelect) instance;
                   if (simple.getSelected() != null) {
                       String[] selectList = simple.getSelected();
                       for (int i = 0; i < selectList.length; i++) {
                           if (value.length() > 0) {
                               value += "," + selectList[i];
                           } else {
                               value += selectList[i];
                           }
                       }
                   }
                   if (simple.getDefaultValue() != null) {
                       List<String> selectList = simple.getDefaultValue();
                       for (int i = 0; i < selectList.size(); i++) {
                           if (defval.length() > 0) {
                               defval += "," + selectList.get(i);
                           } else {
                               defval += selectList.get(i);
                           }
                       }
                   }
                   break;
               case group:
                   GroupComboModel group = (GroupComboModel) instance;
                   if (group.getSelected() != null) {
                       String[] selectList = group.getSelected();
                       for (int i = 0; i < selectList.length; i++) {
                           if (value.length() > 0) {
                               value += "," + selectList[i];
                           } else {
                               value += selectList[i];
                           }
                       }
                   }
                   if (group.getDefaultValue() != null) {
                       List<String> selectList = group.getDefaultValue();
                       for (int i = 0; i < selectList.size(); i++) {
                           if (defval.length() > 0) {
                               defval += "," + selectList.get(i);
                           } else {
                               defval += selectList.get(i);
                           }
                       }
                   }
                   break;
               case file:
                   Temp temp = (Temp) instance;
                   if (temp.getFiles() != null) {
                       // 添付ファイル登録のログ出力(稟議申請or草稿保存時)
                       String[] fileList = temp.getFiles();
                       for (int i = 0; i < fileList.length; i++) {
                           if (value.length() > 0) {
                               value += "," + fileList[i];
                           } else {
                               value += fileList[i];
                           }
                       }
                   }
                   if (temp.getSample() != null) {
                       // サンプルファイル登録のログ出力(テンプレート登録時)
                       String[] fileList = temp.getSample();
                       for (int i = 0; i < fileList.length; i++) {
                           if (value.length() > 0) {
                               value += "," + fileList[i];
                           } else {
                               value += fileList[i];
                           }
                       }
                   }
                   break;
               case block:
                   value += __outputFormBlock((Block) instance, isDef);
                   break;
               case blocklist:
                   BlockList blockTable = (BlockList) instance;

                   value += __outputFormBlock(blockTable.getHeader(), isDef);

                   List<Block> blockBodys  = blockTable.getBodyList();
                   for (Block blockBody : blockBodys) {
                       value += __outputFormBlock(blockBody, isDef);
                   }

                   value += __outputFormBlock(blockTable.getFooter(), isDef);
                   break;
               default:
                 break;
               }
           }
       }
       return new LabelValueBean(title, (isDef ? defval : value));
   }

   /**
    * <br>[機  能] フォームセル(ブロック)のログ出力
    * <br>[解  説]
    * <br>[備  考]
    * @param block フォームセル(ブロック)情報
    * @param isDef デフォルト値使用フラグ
    * @return 出力されるフォームセル(ブロック)のログ
    */
   private String __outputFormBlock(Block block, boolean isDef) {
       String value = "";
       for (List<FormCell> blockList : block.getFormTable()) {
           if (value.length() > 0) {
               value += ",(";
           } else {
               value += "(";
           }
           for (FormCell blockCell : blockList) {
               LabelValueBean bean = outputFormCell(blockCell, isDef);
               if (bean.getLabel().length() > 0) {
                   value += "[" + bean.getLabel() + "] ";
               }
               value += bean.getValue();
           }
           value += ")";
       }
       return value;
   }

    /**
     * <br>[機  能] 稟議添付情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SIDS
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param cmdMode 処理モード 0:新規登録 1:更新
     * @return 登録したバイナリーSIDのリスト
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<String> insertRngBin(
                            int rngSid, int userSid, UDate now,
                            String appRootPath, String tempDir, int cmdMode)
    throws SQLException, IOException, IOToolsException, TempFileException {

        return insertRngBin(rngSid, userSid, now,
                appRootPath, tempDir, cmdMode, APPLMODE_APPL);
    }

    /**
     * <br>[機  能] 稟議添付情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SIDS
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param cmdMode 処理モード 0:新規登録 1:更新
     * @param applMode 申請モード 0:申請 1:再申請
     * @return 登録したバイナリーSIDのリスト
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<String> insertRngBin(
                            int rngSid, int userSid, UDate now,
                            String appRootPath, String tempDir, int cmdMode, int applMode)
    throws SQLException, IOException, IOToolsException, TempFileException {

        //稟議添付情報の登録
        CommonBiz cmnBiz = new CommonBiz();
        RngBinDao binDao = new RngBinDao(con__);
        if (cmdMode == RngConst.RNG_CMDMODE_EDIT) {
            //更新の場合はバイナリー情報の論理削除、稟議添付情報の削除を行う
            RingiDao ringiDao = new RingiDao(con__);
            if (applMode == APPLMODE_APPL) {
                ringiDao.removeRngBinData(rngSid, userSid, now);
                binDao.delete(rngSid);
            } else {
                ringiDao.removeRngBinData(rngSid, userSid, userSid, now);
                binDao.delete(rngSid, userSid);
            }
        }

        //バイナリー情報の登録
        List < String > binSidList = cmnBiz.insertBinInfo(con__,
                                                    tempDir, appRootPath,
                                                    cntCon__, userSid, now);

        //稟議添付情報の登録
        if (binSidList != null && !binSidList.isEmpty()) {
            RngBinModel binMdl = new RngBinModel();
            binMdl.setRngSid(rngSid);
            binMdl.setUsrSid(userSid);

            for (String binSid : binSidList) {
                binMdl.setBinSid(Long.parseLong(binSid));
                binDao.insert(binMdl);
            }
        }
        return binSidList;
    }

    /**
     * <br>[機  能] 稟議情報Modelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param cntCon MlCountMtController
     * @param model 稟議申請情報
     * @param mode 処理モード 0:申請 1:草稿
     * @param cmdMode 登録モード 0:登録 1:更新
     * @return 稟議情報Model
     * @throws SQLException 稟議SIDの採番に失敗
     */
    private RngRndataModel __createRngData(MlCountMtController cntCon, RingiRequestModel model,
                                            int mode, int cmdMode)
    throws SQLException {
        RngRndataModel rngMdl = new RngRndataModel();

        //稟議SID
        int rngSid = model.getRngSid();
        if (cmdMode == RngConst.RNG_CMDMODE_ADD) {
            rngSid = (int) cntCon.getSaibanNumber(RngConst.SBNSID_RINGI,
                                                RngConst.SBNSID_SUB_RINGI_ID,
                                                model.getUserSid());
        }
        rngMdl.setRngSid(rngSid);

        rngMdl.setRngTitle(model.getRngTitle());
        rngMdl.setRngContent(model.getRngContent());
        rngMdl.setRngMakedate(model.getDate());

        if (mode == ENTRYMODE_SINSEI) {
            rngMdl.setRngApplicate(model.getUserSid());
            rngMdl.setRngAppldate(model.getDate());
            rngMdl.setRngStatus(RngConst.RNG_STATUS_REQUEST);
        } else {
            rngMdl.setRngStatus(RngConst.RNG_STATUS_DRAFT);
        }

        rngMdl.setRngCompflg(0);
        rngMdl.setRngAdmcomment(null);
        rngMdl.setRngAuid(model.getUserSid());
        rngMdl.setRngAdate(model.getDate());
        rngMdl.setRngEuid(model.getUserSid());
        rngMdl.setRngEdate(model.getDate());
        rngMdl.setRngId(model.getRngId());
        rngMdl.setRtpSid(model.getRtpSid());
        rngMdl.setRtpVer(model.getRtpVer());
        rngMdl.setRctVer(model.getRctVer());
        return rngMdl;
    }

    /**
     * <p>稟議リスナー実装クラスのリストを返す
     * @param pluginConfig プラグイン情報
     * @throws ClassNotFoundException 指定された稟議リスナークラスが存在しない
     * @throws IllegalAccessException 稟議リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException 稟議リスナー実装クラスのインスタンス生成に失敗
     * @return バッチリスナー
     */
    public IRingiListener[] getRingiListeners(PluginConfig pluginConfig)
    throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        String[] listenerClass = pluginConfig.getListeners(RngConst.RNG_LISTENER_ID);
        IRingiListener[] lis = new IRingiListener[listenerClass.length];
        for (int i = 0; i < listenerClass.length; i++) {

            Class<?> cls = Class.forName(listenerClass[i]);
            try {
                lis[i] = (IRingiListener) cls.getDeclaredConstructor().newInstance();
            } catch (InvocationTargetException e) {
                log__.error(e);
            } catch (NoSuchMethodException e) {
                log__.error(e);
            }
        }

        return lis;
    }

    /**
     * <br>[機  能] １ページの最大表示件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return １ページの最大表示件数
     * @throws SQLException SQL実行時例外
     */
    public int getViewCount(Connection con, int userSid) throws SQLException {
        RngUconfModel confMdl = getUConfData(con, userSid);
        return confMdl.getRurViewCnt();
    }

    /**
     * <br>[機  能] 稟議個人情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @return １ページの最大表示件数
     * @throws SQLException SQL実行時例外
     */
    public RngUconfModel getUConfData(Connection con, int userSid) throws SQLException {
        RngUconfDao confDao = new RngUconfDao(con);
        RngUconfModel confMdl = confDao.select(userSid);

        if (confMdl == null) {
            confMdl = new RngUconfModel();
            confMdl.setRurSmlNtf(RngConst.RNG_SMAIL_TSUUCHI);
            confMdl.setRurViewCnt(RngConst.RNG_PAGE_VIEWCNT);
        }

        return confMdl;
    }

    /**
     * <br>[機  能] 選択中のメンバーの順序を1つ繰り上げる
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param upSelectPos コンボで選択中の位置
     * @param memberSid メンバーリスト
     * @return 並び替え済みのメンバーリスト
     */
    public String[] getUpMember(String[] upSelectPos, String[] memberSid) {

        if (upSelectPos == null || upSelectPos.length < 1
        || memberSid == null || upSelectPos.length >= memberSid.length) {
            return memberSid;
        }

        ArrayList<String> userList = new ArrayList<String>();
        userList.addAll(Arrays.asList(memberSid));

        for (String idxStr : upSelectPos) {
            int index = Integer.parseInt(idxStr);
            if (index > 0) {
                String userSid = userList.get(index);
                userList.remove(index);
                userList.add(index - 1, userSid);
            }
        }

        return userList.toArray(new String[userList.size()]);
    }

    /**
     * <br>[機  能] 選択中のメンバーの順序を1つ繰り下げる
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param downSelectPos コンボで選択中の値
     * @param memberSid メンバーリスト
     * @return 並び替え済みのメンバーリスト
     */
    public String[] getDownMember(String[] downSelectPos, String[] memberSid) {

        if (downSelectPos == null || downSelectPos.length < 1
        || memberSid == null || downSelectPos.length >= memberSid.length) {
            return memberSid;
        }

        ArrayList<String> userList = new ArrayList<String>();
        userList.addAll(Arrays.asList(memberSid));
        int lastIndex = userList.size() - 1;

        for (int i = downSelectPos.length - 1; i >= 0; i--) {
            int index = Integer.parseInt(downSelectPos[i]);
            if (index < lastIndex) {
                String userSid = userList.get(index);
                userList.remove(index);
                userList.add(index + 1, userSid);
            }
        }

        return userList.toArray(new String[userList.size()]);
    }

    /**
     * <br>[機  能] キーワード一覧を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param keyword キーワード
     * @return キーワード一覧
     */
    public static List<String> createKeywordList(String keyword) {

        if (StringUtil.isNullZeroString(keyword)) {
            return null;
        }

        List <String> keywordList = new ArrayList <String>();
        String searchKey = StringUtil.substitute(keyword, "　", " ");
        StringTokenizer st = new StringTokenizer(searchKey, " ");
        while (st.hasMoreTokens()) {
            String str = st.nextToken();
            if (!StringUtil.isNullZeroString(str)) {
                keywordList.add(str);
            }
        }

        return keywordList;
    }

    /**
     * <br>[機  能] ラベルリストを指定した値順に並び替える
     * <br>[解  説]
     * <br>[備  考]
     * @param labelList ラベルリスト
     * @param values 並び順
     * @return 並び替え後のラベルリスト
     */
    public List<UsrLabelValueBean> sortLabelList(List<UsrLabelValueBean> labelList,
            String[] values) {

        List<UsrLabelValueBean> sortLabelList = new ArrayList<UsrLabelValueBean>();

        if (values == null || values.length <= 0) {
            return labelList;
        }

        for (String value : values) {
            int intValue = Integer.parseInt(value);
            for (UsrLabelValueBean label : labelList) {
                if (intValue == Integer.parseInt(label.getValue())) {
                    sortLabelList.add(label);
                }
            }
        }

        return sortLabelList;
    }

    /**
     * <br>[機  能] 稟議内容確認URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param rngSid 稟議SID
     * @return 稟議内容確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public String createThreadUrl(RequestModel reqMdl, int rngSid)
    throws UnsupportedEncodingException {
        return createThreadUrl(reqMdl, rngSid, null);
    }

    /**
     * <br>[機  能] 稟議内容確認URLを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param rngSid 稟議SID
     * @param sidList 通知対象ユーザSID
     * @return 稟議内容確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public String createThreadUrl(RequestModel reqMdl, int rngSid, List<Integer> sidList)
    throws UnsupportedEncodingException {

        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl);
            paramUrl += "/" + GSConst.PLUGIN_ID_RINGI;
            paramUrl += "/rng030.do";
            paramUrl += "?rngSid=" + rngSid;
            paramUrl += "&CMD=fromSmail";
            if (sidList != null && !sidList.isEmpty() && sidList.size() > 0) {
                for (int usrSid : sidList) {
                    paramUrl += "&rng010ViewAccount=" + usrSid;
                }
            }

            return urlBiz.getAccessUrl(reqMdl, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }

    }

    /**
     * <br>[機  能] 稟議内容確認URLを取得する(差し戻し)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @param rngSid 稟議SID
     * @return 稟議内容確認URL
     * @throws UnsupportedEncodingException URLのエンコードに失敗
     */
    public String createThreadUrlRef(RequestModel reqMdl, int rngSid)
    throws UnsupportedEncodingException {

        AccessUrlBiz urlBiz = AccessUrlBiz.getInstance();
        try {

            String paramUrl = "/" + urlBiz.getContextPath(reqMdl);
            paramUrl += "/" + GSConst.PLUGIN_ID_RINGI;
            paramUrl += "/rng030.do";
            paramUrl += "?CMD=rng030";
            paramUrl += "&rngCmdMode=0";
            paramUrl += "&rngSid=" + rngSid;
            paramUrl += "&rngApprMode=" + RngConst.RNG_APPRMODE_APPL;
            return urlBiz.getAccessUrl(reqMdl, paramUrl);
        } catch (URISyntaxException e) {
            return null;
        }

    }

    /**
     * 稟議全般のログ出力を行う
     * @param map マップ
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param reqMdl リクエスト情報
     */
    public void outPutLog(
            ActionMapping map,
            String opCode,
            String level,
            String value,
            RequestModel reqMdl) {
        outPutLog(map, opCode, level, value, reqMdl, null);
    }

    /**
     * 稟議全般のログ出力を行う
     * @param map マップ
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param reqMdl リクエスト情報
     * @param fileId 添付ファイルID
     */
    public void outPutLog(
            ActionMapping map,
            String opCode,
            String level,
            String value,
            RequestModel reqMdl,
            String fileId) {
        outPutLog(map, opCode, level, value, reqMdl, fileId, 0);
    }

    /**
     * 稟議全般のログ出力を行う
     * @param map マップ
     * @param opCode 操作コード
     * @param level ログレベル
     * @param value 内容
     * @param reqMdl リクエスト情報
     * @param fileId 添付ファイルID
     * @param tplType テンプレート種別(0:なし / 1:共有 / 2:個人)
     */
    public void outPutLog(
            ActionMapping map,
            String opCode,
            String level,
            String value,
            RequestModel reqMdl,
            String fileId,
            int tplType
            ) {

        BaseUserModel usModel = reqMdl.getSmodel();
        int usrSid = -1;
        if (usModel != null) {
            usrSid = usModel.getUsrsid(); //セッションユーザSID
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = gsMsg.getMessage("rng.62");

        UDate now = new UDate();
        CmnLogModel logMdl = new CmnLogModel();
        logMdl.setLogDate(now);
        logMdl.setUsrSid(usrSid);
        logMdl.setLogLevel(level);
        logMdl.setLogPlugin(RngConst.PLUGIN_ID_RINGI);
        logMdl.setLogPluginName(msg);
        String type = map.getType();
        logMdl.setLogPgId(StringUtil.trimRengeString(type, 100));
        logMdl.setLogPgName(getPgName(map.getType(), tplType));
        logMdl.setLogOpCode(opCode);
        logMdl.setLogOpValue(value);
        logMdl.setLogIp(reqMdl.getRemoteAddr());
        logMdl.setVerVersion(GSConst.VERSION);
        if (fileId != null) {
            logMdl.setLogCode("binSid：" + fileId);
        }

        LoggingBiz logBiz = new LoggingBiz(con__);
        String domain = reqMdl.getDomain();
        logBiz.outPutLog(logMdl, domain);
    }

    /**
     * プログラムIDからプログラム名称を取得する
     * @param id アクションID
     * @param tplType テンプレート種別(0:なし / 1:共有 / 2:個人)
     * @return String
     */
    public String getPgName(String id, int tplType) {
        String ret = new String();
        if (id == null) {
            return ret;
        }
        log__.info("プログラムID==>" + id);

        GsMessage gsMsg = new GsMessage();
        String logName = "";

        if (id.equals("jp.groupsession.v2.rng.rng010.Rng010Action")) {
            logName = gsMsg.getMessage("rng.94");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng020.Rng020Action")) {
            logName = gsMsg.getMessage("rng.63");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng020kn.Rng020knAction")) {
            logName = gsMsg.getMessage("rng.99");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng030.Rng030Action")) {
            logName = gsMsg.getMessage("rng.104");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng050.Rng050Action")) {
            logName = gsMsg.getMessage("rng.108");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng060.Rng060Action")) {
            logName = gsMsg.getMessage("rng.101");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng070.Rng070Action")) {
            logName = gsMsg.getMessage("rng.73");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng090.Rng090Action")) {
            logName = gsMsg.getMessage("rng.102");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng090kn.Rng090knAction")) {
            logName = gsMsg.getMessage("rng.103");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng110.Rng110Action")) {
            logName = gsMsg.getMessage("rng.76");
            return logName;
        }

        if (id.equals("jp.groupsession.v2.rng.rng120.Rng120Action")) {
            logName = gsMsg.getMessage("cmn.preferences2") + " "
                    + gsMsg.getMessage("cmn.preferences");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng130.Rng130Action")) {
            logName = gsMsg.getMessage("rng.rng130.10");
            return logName;
        }

        if (id.equals("jp.groupsession.v2.rng.rng140.Rng140Action")) { // カテゴリ登録
            if (tplType == RngConst.RNG_TEMPLATE_SHARE) {
                logName = gsMsg.getMessage("rng.23");
            } else if (tplType == RngConst.RNG_TEMPLATE_PRIVATE) {
                logName = gsMsg.getMessage("rng.33");
            }
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng140kn.Rng140knAction")) { // カテゴリ登録確認
            if (tplType == RngConst.RNG_TEMPLATE_SHARE) {
                logName = gsMsg.getMessage("rng.23");
            } else if (tplType == RngConst.RNG_TEMPLATE_PRIVATE) {
                logName = gsMsg.getMessage("rng.33");
            }
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng150.Rng150Action")) { // カテゴリ一覧
            if (tplType == RngConst.RNG_TEMPLATE_SHARE) {
                logName = gsMsg.getMessage("rng.rng150.03");
            } else if (tplType == RngConst.RNG_TEMPLATE_PRIVATE) {
                logName = gsMsg.getMessage("rng.rng150.04");
            }
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng160kn.Rng160knAction")) { // 管理者設定 自動削除設定確認
            logName = gsMsg.getMessage("cmn.admin.setting") + " "
                    + gsMsg.getMessage("cmn.automatic.delete.setting");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng170kn.Rng170knAction")) { // 管理者設定 手動削除確認
            logName = gsMsg.getMessage("cmn.admin.setting") + " "
                    + gsMsg.getMessage("cmn.manual.delete");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng180kn.Rng180knAction")) { // 管理者設定 基本設定確認
            logName = gsMsg.getMessage("cmn.admin.setting") + " "
                    + gsMsg.getMessage("cmn.preferences.kn");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng190.Rng190Action")) { // 管理者設定 ショートメール通知設定
            logName = gsMsg.getMessage("cmn.admin.setting") + " "
                    + gsMsg.getMessage("cmn.sml.notification.setting");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng210.Rng210Action")) { // 管理者設定 申請IDフォーマット設定
            logName = gsMsg.getMessage("rng.rng040.10");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng210kn.Rng210knAction")) { // 管理者設定 申請IDフォーマット設定確認
            logName = gsMsg.getMessage("rng.rng040.10");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng240.Rng240Action")) { // 管理者設定 経路テンプレート管理
            logName = gsMsg.getMessage("rng.106");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng250.Rng250Action")) { // 個人設定 ショートメール通知設定
            logName = gsMsg.getMessage("cmn.preferences2") + " "
                    + gsMsg.getMessage("cmn.sml.notification.setting");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng260.Rng260Action")) { // 管理者設定 代理人設定
            logName = gsMsg.getMessage("cmn.admin.setting") + " "
                    + gsMsg.getMessage("rng.rng080.05");
            return logName;
        }
        if (id.equals("jp.groupsession.v2.rng.rng270.Rng270Action")) { // 個人設定 代理人設定
            logName = gsMsg.getMessage("cmn.preferences2") + " "
                    + gsMsg.getMessage("rng.rng080.05");
            return logName;
        }

        return ret;
    }

    /**
     * <br>[機  能] リスナー情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param rngSid 稟議SID
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig PluginConfig
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @return リスナー情報
     */
    public RingiListenerModel createListenerModel(Connection con,
                                                MlCountMtController cntCon,
                                                int rngSid,
                                                String appRootPath,
                                                PluginConfig pluginConfig,
                                                boolean smailPluginUseFlg) {

        RingiListenerModel listenerMdl = new RingiListenerModel();
        listenerMdl.setCon(con);
        listenerMdl.setCntCon(cntCon);
        listenerMdl.setAppRootPath(appRootPath);
        listenerMdl.setRngSid(rngSid);
        listenerMdl.setPluginConfig(pluginConfig);
        listenerMdl.setSmailPluginFlg(smailPluginUseFlg);

        return listenerMdl;
    }

    /**
     * <br>[機  能] 稟議プラグインが指定されたグループ/ユーザのみ使用可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true: グループ/ユーザのみ使用可能 false: 制限無し
     * @throws SQLException SQL実行時例外
     */
    public boolean isPluginMemberControl(Connection con) throws SQLException {
        boolean rngControl = false;
        CmnPluginControlDao pcontrolDao = new CmnPluginControlDao(con);
        CmnPluginControlModel pcontrolMdl = pcontrolDao.select(RngConst.PLUGIN_ID_RINGI);
        rngControl = (pcontrolMdl != null && pcontrolMdl.getPctKbn() == GSConstMain.PCT_KBN_MEMBER);

        return rngControl;
    }

    /**
     * <br>[機  能] ユーザ情報一覧を指定されたユーザSID一覧の順に並び替える
     * <br>[解  説]
     * <br>[備  考]
     * @param userList ユーザ情報一覧
     * @param userSidList ユーザSID一覧
     * @return ユーザ情報一覧
     */
    public List<CmnUsrmInfModel> sortUserList(List<CmnUsrmInfModel> userList,
                                                String[] userSidList) {

        if (userList == null || userList.isEmpty()) {
            return userList;
        }

        //経路順に並び替える
        List<CmnUsrmInfModel> sortUserList = new ArrayList<CmnUsrmInfModel>(userList.size());

        for (String userSid : userSidList) {
            for (CmnUsrmInfModel userMdl : userList) {
                if (Integer.parseInt(userSid) == userMdl.getUsrSid()) {
                    sortUserList.add(userMdl);
                }
            }
        }

        return sortUserList;
    }

    /**
     * <br>[機  能] 削除設定画面の年コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 年コンボ
     */
    public static List<LabelValueBean> createDelYearCombo(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        //年ラベル
        ArrayList<LabelValueBean> yearCombo = new ArrayList<LabelValueBean>();
        for (int nLabel : GSConst.DEL_YEAR_DATE) {
            String label = String.valueOf(nLabel);
            yearCombo.add(
                    new LabelValueBean(gsMsg.getMessage("cmn.year", new String[] {label}), label));
        }
        return yearCombo;
    }

    /**
     * <br>[機  能] 削除設定画面の月コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 月コンボ
     */
    public static List<LabelValueBean> createDelMonthCombo(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<LabelValueBean> monthCombo = new ArrayList<LabelValueBean>();
        for (int month : GSConst.DEL_MONTH_DATE) {
            monthCombo.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {String.valueOf(month)}),
                    Integer.toString(month)));
        }

        return monthCombo;
    }

    /**
     * <br>[機  能] 削除設定画面の日コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 日コンボ
     */
    public static List<LabelValueBean> createDelDayCombo(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        ArrayList<LabelValueBean> dayCombo = new ArrayList<LabelValueBean>();
        for (int day : GSConst.DEL_DAY_DATE) {
            dayCombo.add(new LabelValueBean(day + gsMsg.getMessage("cmn.day"),
                    String.valueOf(day)));
        }

        return dayCombo;
    }

    /**
     * <br>[機  能] 指定した削除条件に従い稟議の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param delList 削除条件
     * @param userSid 削除ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteRngData(Connection con, List<RngDeleteModel> delList, int userSid)
    throws SQLException {
        //削除対象が存在しない場合、処理を終了する
        if (delList == null || delList.isEmpty()) {
            return;
        }

        //削除対象の稟議を取得する
        RingiDao rngDao = new RingiDao(con);
        for (RngDeleteModel delMdl : delList) {
            List<Integer> delRngList = rngDao.getUpdateRingilList(delMdl);
            for (int rngSid : delRngList) {
                deleteRngData(con, rngSid, userSid);
            }
        }
    }

    /**
     * <br>[機  能] 指定した稟議(関連情報含む)の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rngSid 稟議SID
     * @param userSid 削除ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteRngData(Connection con, int rngSid, int userSid)
    throws SQLException {
        UDate now = new UDate();

        //稟議申請情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        RngUsedDataBiz usedDataBiz = new RngUsedDataBiz(con);
        usedDataBiz.insertSinseiDataSize(rngSid, false);

        //複写用稟議経路ステップ情報の削除
        RngCopyKeiroStepDao rckDao = new RngCopyKeiroStepDao(con);
        rckDao.deleteRng(rngSid);

        //複写用稟議経路ステップ選択情報の削除
        RngCopyKeirostepSelectDao rcsDao = new RngCopyKeirostepSelectDao(con);
        rcsDao.deleteRng(rngSid);

        //稟議情報の削除
        RngRndataDao rngDao = new RngRndataDao(con);
        rngDao.delete(rngSid);

        // 稟議経路選択ユーザ情報の削除
        RngKeirostepSelectDao selectDao = new RngKeirostepSelectDao(con);
        selectDao.deleteRng(rngSid);

        //稟議経路情報の削除
        RngKeiroStepDao keiroDao = new RngKeiroStepDao(con);
        keiroDao.deleteRngSid(rngSid);

        // 稟議審議情報の削除
        RngSingiDao singiDao = new RngSingiDao(con);
        singiDao.deleteSelectedRingi(rngSid);

        // 稟議フォーム入力値情報の取得
        RngFormdataDao formDao = new RngFormdataDao(con);
        formDao.deleteRngSid(rngSid);

        //バイナリー情報の論理削除
        RingiDao ringiDao = new RingiDao(con);
        ringiDao.removeRngBinData(rngSid, userSid, now);

        //稟議添付情報の削除
        RngBinDao binDao = new RngBinDao(con);
        binDao.delete(rngSid);

    }


    /**
     * 稟議の削除を実行可能か判定
     * @param uModel セッションユーザモデル
     * @param con コネクション
     * @return true:削除可 false:削除不可
     * @throws SQLException SQL実行例外
     * */
    public boolean isDeleteRingi(BaseUserModel uModel, Connection con) throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();

        RngAconfModel aconfMdl = getRngAconf(con__);

        if (aconfMdl.getRarDelAuth() == RngConst.RAR_DEL_AUTH_UNRESTRICTED) {
            return true;
        } else if (cmnBiz.isPluginAdmin(con, uModel, RngConst.PLUGIN_ID_RINGI)) {
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 稟議 管理者設定を取得する
     * <br>[解  説] 管理者設定が未登録の場合、初期値を返す
     * <br>[備  考]
     * @param con コネクション
     * @return 稟議 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public RngAconfModel getRngAconf(Connection con) throws SQLException {
        RngAconfModel aconfMdl = null;

        RngAconfDao aconfDao = new RngAconfDao(con);
        List<RngAconfModel> aconfList = aconfDao.select();
        if (aconfList != null && !aconfList.isEmpty()) {
            aconfMdl = aconfList.get(0);
        } else {
            aconfMdl = new RngAconfModel();
            aconfMdl.initData();
        }

        return aconfMdl;
    }

    /**
     * <br>[機  能] 稟議 個人設定を取得する
     * <br>[解  説] 個人設定が未登録の場合、初期値を返す
     * <br>[備  考]
     * @param con コネクション
     * @return 稟議 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public RngUconfModel getRngUconf(Connection con) throws SQLException {
        RngUconfModel uconfMdl = null;

        RngUconfDao uconfDao = new RngUconfDao(con);
        List<RngUconfModel> uconfList = uconfDao.select();
        if (uconfList != null && !uconfList.isEmpty()) {
            uconfMdl = uconfList.get(0);
        } else {
            uconfMdl = new RngUconfModel();
            uconfMdl.setRurSmlNtf(0);
            uconfMdl.setRurViewCnt(1);
            uconfMdl.setRurSmlJusin(0);
            uconfMdl.setRurSmlKoetu(0);
        }
        return uconfMdl;
    }

    /**
     * <br>[機  能] 個人設定 ショートメール通知設定で代理人通知を「通知する」ユーザを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return 稟議 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getRngUconfDairi(Connection con,
            List<Integer> usrSid) throws SQLException {

        RngUconfDao uconfDao = new RngUconfDao(con);
        List<Integer> usrSidList = uconfDao.selectDairi(usrSid);

        return usrSidList;
    }

    /**
     * <br>[機  能] 稟議 個人設定のUSRSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return 稟議 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getRngUconfSinsei(Connection con,
            List<Integer> usrSid) throws SQLException {

        RngUconfDao uconfDao = new RngUconfDao(con);
        List<Integer> usrSidList = uconfDao.selectSinsei(usrSid);

        return usrSidList;
    }

    /**
     * <br>[機  能] 稟議 個人設定のUSRSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return 稟議 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getRngUconfZyusin(Connection con,
            List<Integer> usrSid) throws SQLException {

        RngUconfDao uconfDao = new RngUconfDao(con);
        List<Integer> usrSidList = uconfDao.selectZyusin(usrSid);

        return usrSidList;
    }

    /**
     * <br>[機  能] 稟議 管理者設定で結果通知「通知しない」に設定されているかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定されている false:設定されていない
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckRngAconfSmlNot(Connection con) throws SQLException {

        RngBiz biz = new RngBiz(con);
        RngAconfModel mdl = biz.getRngAconf(con);

        if (mdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_ADMIN
                && mdl.getRarSmlNtfKbn() == RngConst.RAR_SML_NTF_KBN_NO) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 稟議 管理者設定で受信通知「通知しない」に設定されているかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定されている false:設定されていない
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckRngJusinSmlNot(Connection con) throws SQLException {

        RngBiz biz = new RngBiz(con);
        RngAconfModel mdl = biz.getRngAconf(con);

        if (mdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_ADMIN
                && mdl.getRarSmlJusinKbn() == RngConst.RAR_SML_NTF_KBN_NO) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 稟議 管理者設定で後閲機能「通知しない」に設定されているかチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true:設定されている false:設定されていない
     * @throws SQLException SQL実行例外
     */
    public boolean isCheckRngKoetuSmlNot(Connection con) throws SQLException {

        RngBiz biz = new RngBiz(con);
        RngAconfModel mdl = biz.getRngAconf(con);

        if (mdl.getRarSmlNtf() == RngConst.RAR_SML_NTF_KUT_ADMIN
                && mdl.getRarSmlKoetuKbn() == RngConst.RAR_SML_NTF_KUT_KBN_NO) {
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] 申請された稟議の添付ファイルをダウンロード可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rngSid 稟議SID
     * @param usrSid ユーザSID
     * @param admin 管理者フラグ
     * @param binSid バイナリSID
     * @return true:可  false:不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckDLRngTemp(
            Connection con, int rngSid, int usrSid, boolean admin, Long binSid)
            throws SQLException {

        RngBinDao dao = new RngBinDao(con);

        //管理者ユーザの場合は経路内ユーザチェックはせず、
        //指定稟議内のバイナリSIDかチェックのみを行う
        if (admin && dao.isCheckRngTemp(rngSid, binSid)) {
            return true;
        }
        //稟議経路内の受信済みユーザSIDを取得する
        ArrayList<Integer> receiveUsrSids = getReceiveUserSids(con, rngSid);
        boolean userFlg = false;
        for (Integer sid : receiveUsrSids) {
            if (usrSid == sid) {
                userFlg = true;
                break;
            }
        }
        if (!userFlg) {
            return false;
        }
        //稟議経路内の受信済みユーザ 且つ バイナリSIDが指定稟議の添付ファイルのもの
        if (dao.isCheckRngTemp(rngSid, binSid)) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機  能] 指定された稟議の経路内で稟議閲覧可能（受信済み）ユーザ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rngSid 稟議SID
     * @return ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<Integer> getReceiveUserSids(Connection con, int rngSid) throws SQLException {
        RngSingiDao singiDao = new RngSingiDao(con);
        return singiDao.getReceiveUserSids(rngSid);
    }

    /**
     * <br>[機  能] 稟議テンプレートの添付ファイルをダウンロード可能かチェックする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param tplSid テンプレートSID
     * @param binSid バイナリSID
     * @param usrSid ユーザSID
     * @return true:可  false:不可
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckDLTemplateTemp(Connection con, int tplSid, Long binSid, int usrSid)
            throws SQLException {

        RngTemplateBinDao binDao = new RngTemplateBinDao(con);
        //バイナリSIDがテンプレート用のバイナリSIDかチェックする
        if (!binDao.isCheckRngTemplateBin(tplSid, binSid)) {
            return false;
        }

        RngTemplateDao tempDao = new RngTemplateDao(con);
        RngTemplateModel tmpMdl = tempDao.select(tplSid);

        if (tmpMdl != null) {
            //共通テンプレートの添付ファイルであれば誰でも取得可能
            if (tmpMdl.getRtpType() == RngConst.RNG_TEMPLATE_SHARE) {
                return true;

            //個人テンプレートの添付ファイルは本人のみ取得可能
            } else if (tmpMdl.getRtpType() == RngConst.RNG_TEMPLATE_PRIVATE) {
                if (tmpMdl.getUsrSid() == usrSid) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * <p>マイグループ又はグループに所属するユーザ情報一覧を取得する。
     * @param con コネクション
     * @param gpSid グループSID
     * @param usrSids 除外するユーザSID
     * @param sessionUsrSid セッションユーザSID
     * @param myGroupFlg マイグループ選択フラグ
     * @return ArrayList 検索にヒットしたユーザデータ(CmnUsrmInfModel)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsrmInfModel> getBelongUserList(Connection con, int gpSid,
            ArrayList<Integer> usrSids, int sessionUsrSid, boolean myGroupFlg) throws SQLException {

        UserBiz userBiz = new UserBiz();
        return userBiz.getBelongUserList(con, gpSid, usrSids, sessionUsrSid, myGroupFlg);
    }

    /**
     * <p>稟議申請ID を取得
     * @param tplSid 稟議テンプレートSID
     * @param rngid  入力された申請ID(手動入力時のみ使用)
     * @param isUpdate 申請ID 更新フラグ(自動入力時のみ使用)
     * @param banRngSid 重複チェックで除外する稟議SID(自動入力時のみ使用)
     * @param reqMdl リクエストモデル
     * @return 発行された稟議ID登録
     * @throws SQLException SQL実行時例外
     */
    public String getNewRngid(int tplSid, String rngid, boolean isUpdate,
            int banRngSid, RequestModel reqMdl)
            throws SQLException {
        int rngidSid = 0;
        if (tplSid > 0) {
            RngTemplateDao rtpDao = new RngTemplateDao(con__);
            RngTemplateModel rtpMdl = rtpDao.select(tplSid);
            rngidSid = rtpMdl.getRtpIdformatSid();
        }
        //申請ID設定
        RingiIdModel rngidMdl = this.getRngidModel(rngidSid);

        return this.getNewRngid(rngidMdl, rngid, isUpdate, banRngSid, reqMdl);
    }
    /**
     *
     * <br>[機  能] 稟議IDの重複チェック
     * <br>[解  説]
     * <br>[備  考] 稟議IDの重複を許可される設定の場合 trueを返す
     * @param rngid 稟議ID
     * @param rngSid 計算対象から除外する稟議SID
     * @return 重複する場合 false
     * @throws SQLException SQL実行時例外
     */
    public boolean chkOverRapErrorRngId(String rngid, int rngSid) throws SQLException {
        RngAconfModel aconfMdl = getRngAconf(con__);
        if (aconfMdl.getRarOverlap() == RngConst.RAR_SINSEI_KYOKA) {
            return true;
        }
        RngIdDao rngIdDao = new RngIdDao(con__);
        if (rngIdDao.getOverlapCount(rngid, rngSid) == 0) {
            return true;
        }
        return false;
    }
    /**
     * <p>稟議申請ID を取得
     * @param idMdl 申請IDデータ
     * @param rngid  入力された申請ID(手動入力時のみ使用)
     * @param isUpdate 申請ID 更新フラグ(自動入力時のみ使用)
     * @param banRngSid 重複チェックで除外する稟議SID(自動入力時のみ使用)
     * @param reqMdl リクエストモデル
     * @return 発行された稟議ID登録
     * @throws SQLException SQL実行時例外
     */
    public String getNewRngid(RingiIdModel idMdl, String rngid,
            boolean isUpdate, int banRngSid, RequestModel reqMdl)
            throws SQLException {
        String ret = null;

        // 管理者設定を取得
        RngAconfModel aconfMdl = getRngAconf(con__);
        if (idMdl != null) {
            //手動でIDを設定した場合
            if (!StringUtil.isNullZeroString(rngid)
                    && idMdl.getRngManual() == RngConst.RAR_SINSEI_KYOKA) {
                RngIdDao rngIdDao = new RngIdDao(con__);
                // 重複チェックなし
                if (aconfMdl.getRarOverlap() == RngConst.RAR_SINSEI_KYOKA) {
                    ret = rngid;
                // 重複しない
                } else if (rngIdDao.getOverlapCount(rngid, idMdl.getRngSid()) == 0) {
                    ret = rngid;
                }
            // 自動入力
            } else {
                // 稟議申請ID設定にあるルールを配列で取得
                ArrayList<Rng210ListModel> list =
                        this.getRngidFormatList(idMdl.getRngFormat());
                RngIdSaibanControler saiCon = RngIdSaibanControler.getInstance(
                        idMdl,
                        list,
                        con__,
                        reqMdl, aconfMdl);

                ret = saiCon.createAutoRngId(rngid, isUpdate, banRngSid);
            }
        }

        return ret;
    }
    /**
     *
     * <br>[機  能] 稟議申請ID設定情報を取得
     * <br>[解  説] 管理者設定を反映して稟議申請ID設定情報を返す
     * <br>[備  考] 稟議IDを使用しない設定の場合nullが返る 統一設定の場合引数に影響せず稟議ID設定情報を返す
     * @param rngFormatSid テンプレートに設定された稟議申請ID
     * @return 稟議申請ID設定情報
     * @throws SQLException SQL実行例外
     */
    public RingiIdModel getRngidModel(int rngFormatSid) throws SQLException {

        // 管理者設定情報
        RngAconfModel aconfMdl = this.getRngAconf(con__);
        int rngidSid = 0; // デフォルト値

        if (aconfMdl.getRarRngid() == RngConst.RAR_SINSEI_TOUITU) {
            // 全稟議統一 → デフォルト設定している設定SIDを取得
            rngidSid = aconfMdl.getRarRngidDefSid();
        } else if (aconfMdl.getRarRngid() == RngConst.RAR_SINSEI_TEMP) {
            // テンプレート毎に設定
            rngidSid = rngFormatSid;
        } else {
            // 使用しない
            return null;
        }

        // 設定SIDから稟議申請ID設定情報を取得
        RngIdDao rngidDao = new RngIdDao(con__);
        RingiIdModel ret =  rngidDao.selectData(rngidSid);
        if (ret == null) {
            ret =  rngidDao.selectData(0);
        }
        return ret;
    }
    /**
     * <br>[機  能] フォーマット値の分割を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param format 1行フォーマット値
     * @return フォーマットモデル配列
     */
    public ArrayList<Rng210ListModel> getRngidFormatList(String format) {
        ArrayList<Rng210ListModel> ret = new ArrayList<Rng210ListModel>();
        Rng210ListModel lMdl = new Rng210ListModel();
        if (StringUtil.isNullZeroString(format)) {
            lMdl = new Rng210ListModel();
            lMdl.setRng210FormatWord("");
            lMdl.setRng210SelectFormat("1");
            ret.add(lMdl);
            return ret;
        }
        //終端文字を挿入
        //splitの末端の空文字列が除去されるのを防ぐ
        String addEndStr = format + "${END}";
        String[] formatList = addEndStr.split("\\$", 0);
        UDate now = new UDate();
        if (formatList.length == 0) {
           return ret;
        }
        // 0:未処理モード 1:挿入モード 2:文字入力挿入モード
        int mode = 0;
        StringBuilder setWord = null;
        for (int idx = 1; idx < formatList.length; idx++) {
            lMdl = new Rng210ListModel();
            String workStr = formatList[idx];
            //文字入力以外はそのまま設定
            if (mode == 0) {
                switch (formatList[idx]) {
                case "{NO}":
                    lMdl.setRng210FormatWord(formatList[idx]);
                    lMdl.setRng210SelectFormat("2");
                    ret.add(lMdl);
                    break;
                case "{YEAR4}":
                    lMdl.setRng210FormatWord(now.getStrYear());
                    lMdl.setRng210SelectFormat("3");
                    ret.add(lMdl);
                    break;
                case "{YEAR2}":
                    lMdl.setRng210FormatWord(now.getStrYear().substring(2, 4));
                    lMdl.setRng210SelectFormat("4");
                    ret.add(lMdl);
                    break;
                case "{MON}":
                    lMdl.setRng210FormatWord(now.getStrMonth());
                    lMdl.setRng210SelectFormat("5");
                    ret.add(lMdl);
                    break;
                case "{DAY}":
                    lMdl.setRng210FormatWord(now.getStrDay());
                    lMdl.setRng210SelectFormat("6");
                    ret.add(lMdl);
                    break;
                default:
                   break;
                }
                mode = 0;
                //文字入力を設定する
                if (!StringUtil.isNullZeroString(formatList[idx])
                        && formatList[idx].startsWith("{}")) {
                     mode = 2;
                     setWord = new StringBuilder();
                     workStr = formatList[idx].substring(2);
                }
            }
            if (mode == 2) {
                setWord.append(workStr);
                //「$」の数を計算して文字列に追加
                int dullerCnt = 0;
                //現在行の後ろに「$」があったと考える
                dullerCnt++;
                for (int next = idx + 1; next < formatList.length - 1; next++) {
                    if (formatList[next].equals("")) {
                        if (idx < formatList.length - 1) {
                            dullerCnt++;
                            idx = next;
                        }
                    } else {
                        break;
                    }
                }
                // dullerCnt/2 がユーザ入力の$の数
                for (int dIdx = 0; dIdx < dullerCnt / 2; dIdx++) {
                    setWord.append("$");
                }
                // dullerCnt が奇数の場合 システムパラメータの$が最後に読み込まれているので
                // 文字列挿入モードを終了
                boolean appendFlg = false;
                if (dullerCnt % 2 == 1) {
                    appendFlg = true;
                }
                if (appendFlg) {
                    lMdl.setRng210FormatWord(setWord.toString());
                    lMdl.setRng210SelectFormat("1");
                    setWord = new StringBuilder();
                    mode = 0;
                    ret.add(lMdl);
                }
            }
        }
        return ret;
    }


    /**
     * <br>[機  能] テンプレート情報一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @param tplMode テンプレートモード(0:両方 / 1:共有 / 2:個人)
     * @param userSid セッションユーザSID
     * @param isAdmin 管理者フラグ(システム管理者 or プラグイン管理者)
     * @param mokuteki 0：使用 1：管理
     * @return テンプレート情報一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<RngTemplateCategoryModel> getTemplateCategoryList(
            int tplMode, int userSid, boolean isAdmin, int mokuteki)
        throws SQLException {

        RngTemplateCategoryDao categoryDao = new RngTemplateCategoryDao(con__);

        ArrayList<RngTemplateCategoryModel> categoryList
                                    = new ArrayList<RngTemplateCategoryModel>();

        if (tplMode == RngConst.RNG_TEMPLATE_ALL
         || tplMode == RngConst.RNG_TEMPLATE_SHARE) {
            //共有のカテゴリを取得する
            categoryList.addAll(categoryDao.selectAdmin());
            // カテゴリ管理者権限のあるカテゴリSID一覧を取得
            RngTemplatecategoryAdmDao rtcAdmDao = new RngTemplatecategoryAdmDao(con__);
            ArrayList<Integer> amdSidList = rtcAdmDao.getRngTemplatecategorySidList(userSid);

            // 許可or制限のあるカテゴリSID一覧を取得
            RngTemplatecategoryUseDao rtcUseDao = new RngTemplatecategoryUseDao(con__);
            ArrayList<Integer> useSidList = rtcUseDao.getRngTemplatecategorySidList(userSid);

            // 共有テンプレートカテゴリのみ使用権限チェックを行う
            Iterator<RngTemplateCategoryModel> rtcIt = categoryList.iterator();
            while (rtcIt.hasNext()) {
                RngTemplateCategoryModel mdl = rtcIt.next();
                Integer key = Integer.valueOf(mdl.getRtcSid());
                boolean isAdmSid = amdSidList.contains(key);
                boolean isUseSid = useSidList.contains(key);

                if (mokuteki == RngConst.RTPLIST_MOKUTEKI_USE
                        && mdl.getRtcUseLimit() == RngConst.LIMIT_USE
                        && !isAdmSid
                        && ((mdl.getRtcLimitType() == RngConst.LIMIT_TYPE_LIMIT
                                && isUseSid)
                            || (mdl.getRtcLimitType() == RngConst.LIMIT_TYPE_ACCEPT
                                && !isUseSid))) {
                    rtcIt.remove(); // 使用制限されている為、リストから除外
                } else if (mokuteki == RngConst.RTPLIST_MOKUTEKI_KANRI
                        && !isAdmin && !isAdmSid) {
                    rtcIt.remove(); // 使用制限されている為、リストから除外
                }
            }

        }

        if (tplMode == RngConst.RNG_TEMPLATE_ALL
         || tplMode == RngConst.RNG_TEMPLATE_PRIVATE) {
            //個人のカテゴリを取得する
            categoryList.addAll(categoryDao.selectUser(userSid));
        }
        return categoryList;
    }

    /**
     * <br>[機  能] テンプレートカテゴリコンボリスト取得
     * <br>[解  説]
     * <br>[備  考] 個人テンプレートのコンボの場合 isAdminをtrueにすること
     * @param reqMdl リクエスト情報
     * @param categoryList テンプレートカテゴリ情報一覧
     * @param isAllTpl [全て]項目フラグ
     * @param isAdmin 管理者フラグ(システム管理者 or プラグイン管理者)
     * @param mokuteki 0：使用 1：管理
     * @return テンプレートカテゴリコンボリスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> createCategoryComb(
            RequestModel reqMdl,
            ArrayList<RngTemplateCategoryModel> categoryList,
            boolean isAllTpl, boolean isAdmin, int mokuteki)
        throws SQLException {

        ArrayList<LabelValueBean> list = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);

        if (isAllTpl) {
            // 追加項目 => [全て]
            String all = gsMsg.getMessage("cmn.all");
            list.add(new LabelValueBean(all, String.valueOf(RngConst.RNG_RTC_SID_ALL)));
        }
        //カテゴリなしが使用できるのは目的：使用の時または管理者ユーザの場合のみ
        if (mokuteki == RngConst.RTPLIST_MOKUTEKI_USE || isAdmin) {
            // 追加項目 => [カテゴリなし]
            String noCategory = gsMsg.getMessage("cmn.category.no");
            list.add(new LabelValueBean(noCategory, String.valueOf(RngConst.RNG_RTC_SID_NONE)));
        }

        if (categoryList != null) {
            for (RngTemplateCategoryModel model : categoryList) {
                String strName = model.getRtcName();
                String strSid = Integer.toString(model.getRtcSid());
                list.add(new LabelValueBean(strName, strSid));
            }
        }
        return list;
    }

    /**
     * <br>[機  能] テンプレート一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param tplMode テンプレートモード(0:両方 / 1:共有 / 2:個人)
     * @param categoryList テンプレートカテゴリ一覧
     * @param rtcSid 選択テンプレートカテゴリSID(-1:全て / 0:カテゴリなし / 1～:カテゴリSID指定)
     * @param isAdmin 管理者フラグ(システム管理者 or プラグイン管理者)
     * @param mokuteki 0：使用 1：管理
     * @return テンプレート情報一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<RngTemplateModel> getTemplateList(RequestModel reqMdl,
            int tplMode, ArrayList<RngTemplateCategoryModel> categoryList,
            int rtcSid, boolean isAdmin, int mokuteki)
        throws SQLException {

        RngTemplateDao dao = new RngTemplateDao(con__);

        ArrayList<RngTemplateModel> tmplateList = new ArrayList<RngTemplateModel>();
        ArrayList<Integer> authSidList = new ArrayList<Integer>(); // 使用可能なカテゴリSID一覧
        //カテゴリなしが使用できるのは目的：使用の時または管理者ユーザの場合のみ
        if (mokuteki == RngConst.RTPLIST_MOKUTEKI_USE || isAdmin) {
            authSidList.add(Integer.valueOf(RngConst.RNG_RTC_SID_NONE)); // [カテゴリなし]を追加
        }
        if (categoryList != null && categoryList.size() > 0) {
            // 共有テンプレートカテゴリのみ使用権限チェックを行う
            Iterator<RngTemplateCategoryModel> rtcIt = categoryList.iterator();
            while (rtcIt.hasNext()) {
                RngTemplateCategoryModel mdl = rtcIt.next();
                Integer key = Integer.valueOf(mdl.getRtcSid());
                authSidList.add(key); // 使用可能カテゴリSIDへ追加
            }
        }

        if (rtcSid < 0 || authSidList.contains(Integer.valueOf(rtcSid))) {
            if (tplMode == RngConst.RNG_TEMPLATE_ALL
             || tplMode == RngConst.RNG_TEMPLATE_SHARE) {
                //共有のテンプレートを取得する
                tmplateList.addAll(
                        dao.selectTplList(RngConst.RNG_TEMPLATE_SHARE, -1, rtcSid, reqMdl));
            }
            if (tplMode == RngConst.RNG_TEMPLATE_ALL
             || tplMode == RngConst.RNG_TEMPLATE_PRIVATE) {
                //個人のテンプレートを取得する
                int userSid = reqMdl.getSmodel().getUsrsid();
                tmplateList.addAll(
                   dao.selectTplList(RngConst.RNG_TEMPLATE_PRIVATE, userSid, rtcSid, reqMdl));
            }
        }
        // 取得したテンプレートの内、使用制限があるものをリストから削除
        Iterator<RngTemplateModel> rtpIt = tmplateList.iterator();
        while (rtpIt.hasNext()) {
            RngTemplateModel mdl = rtpIt.next();
            Integer key = Integer.valueOf(mdl.getRtcSid());
            if (!authSidList.contains(key)) {
                // テンプレートのカテゴリが使用可能カテゴリ一覧に存在しない場合、リストから削除
                rtpIt.remove();
            }
        }

        RngAconfModel aconfMdl = getRngAconf(con__);
        if (tplMode != RngConst.RNG_TEMPLATE_PRIVATE
                && rtcSid <= 0
                && mokuteki == RngConst.RTPLIST_MOKUTEKI_USE
                && aconfMdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_YES) {
            // tplMode が個人テンプレート以外　＋　選択カテゴリ:「カテゴリなし or 全て」 + 汎用稟議を使用するであるならば、先頭に汎用テンプレート情報を追加
            GsMessage gsMsg = new GsMessage(reqMdl);
            RngTemplateModel mdl = dao.createCommonTemplateModel(gsMsg);
            tmplateList.add(0, mdl);
        }

        return tmplateList;
    }

    /**
     * <br>[機  能] 審議者一覧(ユーザorグループor役職)から該当するユーザSID一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param selectList 審議者情報一覧
     * @return ユーザSID一覧
     * @throws SQLException SQL実行時例外
     */
    public List<Integer> getSingiUserList(List<RngKeirostepSelectModel> selectList)
    throws SQLException {

        //ユーザSIDセット （重複させないためSet型を使用）
        Set<Integer> userSidSet = new HashSet<Integer>();

        if (selectList != null) {
            //経路ステップ選択情報モデルからユーザSID一覧を生成
            for (RngKeirostepSelectModel selectMdl : selectList) {
                // ユーザの取得
                if (selectMdl.getUsrSid() > 1) {
                    userSidSet.add(selectMdl.getUsrSid());
                // グループ指定の場合
                } else if (selectMdl.getGrpSid() >= 0) {
                    ArrayList<Integer> usrList = null;
                    // 役職も指定されている場合
                    if (selectMdl.getPosSid() >= 0) {
                        CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
                        usrList = usrDao.getBelongUsrsFromPosition(
                                selectMdl.getGrpSid(), selectMdl.getPosSid());
                    } else {
                        CmnBelongmDao belongDao = new CmnBelongmDao(con__);
                        usrList = belongDao.selectBelongLiveUserSid(selectMdl.getGrpSid());
                    }
                    userSidSet.addAll(usrList);
                // 役職指定の場合
                } else if (selectMdl.getPosSid() >= 0) {
                    ArrayList<Integer> usrList = null;
                    CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
                    usrList = usrDao.getBelongUsrsFromPosition(
                            selectMdl.getGrpSid(), selectMdl.getPosSid());
                    userSidSet.addAll(usrList);
                }
            }
        }

        List<Integer> iUserSidList = new ArrayList<Integer>(userSidSet);
        if (userSidSet.size() == 0) {
            return iUserSidList;
        }

        CommonBiz cmnBiz = new CommonBiz();
        List<Integer> cantUseSidList =
                cmnBiz.getCantUsePluginUser(con__, RngConst.PLUGIN_ID_RINGI, iUserSidList);

        //ユーザSIDの順序
        List<Integer> canUseUserList = new ArrayList<Integer>(userSidSet);
        canUseUserList.removeAll(cantUseSidList);

        return canUseUserList;
    }


    /**
     * <br>[機  能] 指定したグループ内に稟議の使用権限があるユーザを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpList グループリスト
     * @param banUsrList 結果に含めないユーザリスト
     * @return ret ユーザリスト
     * @throws SQLException SQLException
     */
    public List<Integer> getUserList(ArrayList<Integer> grpList,
            ArrayList<Integer> banUsrList) throws SQLException {

        List<Integer> ret = new ArrayList<Integer>();
        UserSearchDao usDao = new UserSearchDao(con__);
        CommonBiz cmnBiz = new CommonBiz();
        ArrayList<Integer> usrList = usDao.getBelongUserSids(grpList, banUsrList);
        ret = cmnBiz.getCanUsePluginUser(con__, GSConst.PLUGIN_ID_RINGI, usrList);

        return ret;
    }

    /**
     * <br>[機  能] 指定したグループ内に稟議の使用権限があるユーザ数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpList グループリスト
     * @return ret ユーザリスト
     * @throws SQLException SQLException
     */
    public Map<String, Integer> getUserListCnt(ArrayList<String> grpList) throws SQLException {

        Map<String, Integer> ret = new HashMap<String, Integer>();
        CmnPluginControlDao ctrDao = new CmnPluginControlDao(con__);
        CmnPluginControlModel ctrData = ctrDao.select(RngConst.PLUGIN_ID_RINGI);
        int mode = 0;
        if (ctrData == null || ctrData.getPctKbn() == GSConstMain.PCT_KBN_ALLOK) {
            //制限なし
            mode = 0;

        } else if (ctrData.getPctKbn() == GSConstMain.PCT_KBN_MEMBER
                && ctrData.getPctType() == GSConstMain.PCT_TYPE_PERMIT) {
            //制限ユーザ指定
            mode = 1;

        } else if (ctrData.getPctKbn() == GSConstMain.PCT_KBN_MEMBER
                && ctrData.getPctType() == GSConstMain.PCT_TYPE_LIMIT) {
            //許可ユーザ指定
            mode = 2;
        }
        RingiDao rDao = new RingiDao(con__);
        ret = rDao.getGroupListCnt(grpList, mode);
        return ret;
    }

    /**
     * <br>[機  能] 指定したグループ内に稟議の使用権限があるユーザが存在するグループを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param grpList グループリスト
     * @param banUserList 除外ユーザリスト
     * @return グループSIDリスト
     * @throws SQLException SQLException
     */
    public ArrayList<Integer> getGroupList(ArrayList<Integer> grpList,
            ArrayList<Integer> banUserList) throws SQLException {

        ArrayList<Integer> ret = new ArrayList<Integer>();
        CmnPluginControlDao ctrDao = new CmnPluginControlDao(con__);
        CmnPluginControlModel ctrData = ctrDao.select(RngConst.PLUGIN_ID_RINGI);
        int mode = 0;
        if (ctrData == null || ctrData.getPctKbn() == GSConstMain.PCT_KBN_ALLOK) {
            //制限なし
            mode = 0;

        } else if (ctrData.getPctKbn() == GSConstMain.PCT_KBN_MEMBER
                && ctrData.getPctType() == GSConstMain.PCT_TYPE_PERMIT) {
            //制限ユーザ指定
            mode = 1;

        } else if (ctrData.getPctKbn() == GSConstMain.PCT_KBN_MEMBER
                && ctrData.getPctType() == GSConstMain.PCT_TYPE_LIMIT) {
            //許可ユーザ指定
            mode = 2;
        }
        RingiDao rDao = new RingiDao(con__);
        ret = rDao.getGroupList(grpList, banUserList, mode);

        return ret;
    }

    /**
     * <br>[機  能] ユーザリストから削除ユーザと稟議の使用権限がないユーザを省き取得
     * <br>[解  説]
     * <br>[備  考]
     * @param usrList ユーザリスト
     * @return 削除稟議使用権限チェック済みユーザリスト
     * @throws SQLException SQLException
     */
    public ArrayList<Integer> getUserList(ArrayList<Integer> usrList)
            throws SQLException {

        ArrayList<Integer> ret = new ArrayList<Integer>();
        CmnUsrmDao cuDao = new CmnUsrmDao(con__);
        ret = cuDao.getNoDeleteUser(usrList);
        CommonBiz cmnBiz = new CommonBiz();
        ret = (ArrayList<Integer>) cmnBiz.getCanUsePluginUser(con__,
                GSConst.PLUGIN_ID_RINGI, ret);
        return ret;
    }

    /**
     * <br>[機  能] 指定したグループと役職から稟議を使用できるユーザが存在するか取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posMap key:POS_SID value:GRP_SID
     * @return グループSIDリスト
     * @throws SQLException SQLException
     */
    public Map<String, Integer> getPostUsrCnt(Map<String, String> posMap) throws SQLException {

        Map<String, Integer> ret = new HashMap<String, Integer>();
        CmnPluginControlDao ctrDao = new CmnPluginControlDao(con__);
        CmnPluginControlModel ctrData = ctrDao.select(RngConst.PLUGIN_ID_RINGI);
        int mode = 0;
        if (ctrData == null || ctrData.getPctKbn() == GSConstMain.PCT_KBN_ALLOK) {
            //制限なし
            mode = 0;

        } else if (ctrData.getPctKbn() == GSConstMain.PCT_KBN_MEMBER
                && ctrData.getPctType() == GSConstMain.PCT_TYPE_PERMIT) {
            //制限ユーザ指定
            mode = 1;

        } else if (ctrData.getPctKbn() == GSConstMain.PCT_KBN_MEMBER
                && ctrData.getPctType() == GSConstMain.PCT_TYPE_LIMIT) {
            //許可ユーザ指定
            mode = 2;
        }
        List<Integer> seigenList = new ArrayList<Integer>();
        if (mode != 0) {
            CmnPluginControlMemberDao memDao = new CmnPluginControlMemberDao(con__);
            seigenList = memDao.selectUsrList(RngConst.PLUGIN_ID_RINGI);
        }

        RingiDao rDao = new RingiDao(con__);
        ret = rDao.getGroupListCnt(posMap, mode, seigenList);

        return ret;
    }

    /**
     * <br>[機  能] ショートメール設定が利用可能かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param confKbn 管理者設定 or 個人設定
     * @throws SQLException SQL実行時例外
     * @return true ショートメール使用可能
     */
    public  boolean canUseSmlConf(
            RequestModel reqMdl, Connection con, int confKbn) throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        //プラグイン設定を取得する
        PluginConfig pconfig = null;
        if (confKbn == RngConst.CONF_KBN_ADM) {
            pconfig = cmnBiz.getPluginConfigForMain(cmnBiz.getPluginConfig(reqMdl), con);
        }
        if (confKbn == RngConst.CONF_KBN_PRI) {
            pconfig = cmnBiz.getPluginConfigForUser(con, reqMdl, reqMdl.getSmodel().getUsrsid());
        }
        //ショートメールは利用可能か判定
        return cmnBiz.isCanUsePlugin(GSConst.PLUGINID_SML, pconfig);
    }
}