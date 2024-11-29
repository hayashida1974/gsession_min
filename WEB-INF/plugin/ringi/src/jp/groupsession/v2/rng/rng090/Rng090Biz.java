package jp.groupsession.v2.rng.rng090;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormBuilder;
import jp.groupsession.v2.cmn.formbuilder.FormCell;
import jp.groupsession.v2.cmn.formbuilder.FormCellPrefarence;
import jp.groupsession.v2.cmn.formmodel.Temp;
import jp.groupsession.v2.cmn.formmodel.TextInput;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.biz.RngTemplateBiz;
import jp.groupsession.v2.rng.dao.RngChannelTemplateDao;
import jp.groupsession.v2.rng.dao.RngTemplateCategoryDao;
import jp.groupsession.v2.rng.dao.RngTemplateDao;
import jp.groupsession.v2.rng.dao.RngTemplatecategoryAdmDao;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngChannelTemplateModel;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.rng.rng110keiro.EnumKeiroKbn;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogParamModel;
import jp.groupsession.v2.rng.rng110keiro.RngTemplateKeiroSave;

/**
 * <br>[機  能] 稟議 テンプレート登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng090Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng090Biz.class);

    /** Connection */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;



    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param reqMdl リクエスト情報
     */
    public Rng090Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     *
     * <br>[機  能] DBから登録済みデータを読み込み
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param rtModel 稟議テンプレート情報
     * @throws SQLException SQL実行時例外
     */
    public void loadData(Rng090ParamModel paramMdl,
            RngTemplateModel rtModel) throws SQLException {
        paramMdl.setRng090title(rtModel.getRtpTitle());
        paramMdl.setRng090rngTitle(rtModel.getRtpRngTitle());
        paramMdl.setRng090CatSid(rtModel.getRtcSid());
        paramMdl.setRng090templateJSON(rtModel.getRtpForm());
        paramMdl.setRng090biko(rtModel.getRtpBiko());
        paramMdl.setRng090idSid(rtModel.getRtpIdformatSid());
        paramMdl.setRng090idPrefManual(rtModel.getRtpIdmanual());
        paramMdl.setRng090rtpSpecVer(rtModel.getRtpSpecVer());
        paramMdl.setRng090KeiroVer(rtModel.getRctVer());
        if (rtModel.getRctSid() == 0) {
            paramMdl.setRng090useKeiroTemplate(1);
            RngTemplateKeiroSave saveBiz =
                    RngTemplateKeiroSave.createInstanceForRTP(
                            rtModel.getRtpSid(), rtModel.getRtpVer(), reqMdl__, con__);
            paramMdl.setRng090keiro(saveBiz.loadRng110Keiro());

        } else {
            paramMdl.setRng090useKeiroTemplate(0);
            RngChannelTemplateDao rctDao = new RngChannelTemplateDao(con__);
            RngChannelTemplateModel rctModel =
                    rctDao.select(rtModel.getRctSid(), rtModel.getRctUsrSid());
            if (rctModel != null) {
                RngTemplateKeiroSave saveBiz =
                        RngTemplateKeiroSave.createInstanceForRCT(
                                rtModel.getRctSid(), rtModel.getRctUsrSid(),
                                reqMdl__, con__);
                paramMdl.setRng090keiro(saveBiz.loadRng110Keiro());
                paramMdl.setRng090KeiroTemplateName(rctModel.getRctName());
                paramMdl.setRng090KeiroTemplateSid(rtModel.getRctSid());
                paramMdl.setRng090KeiroTemplateUsrSid(rtModel.getRctUsrSid());
            }
        }
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説] 変更点確認に不要な添付ファイルの展開やカテゴリ一覧の初期化を含まない
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tempPath
     * @param mres Strutsメッセージリソース
     * @throws IOToolsException ファイル生成実行例外
     * @throws SQLException SQL実行時例外
     * @throws IOException 生成実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void initDsp(
            Rng090ParamModel paramMdl,
            GSTemporaryPathModel tempPath,
            MessageResources mres) throws SQLException {
        RngBiz rBiz = new RngBiz(con__);

        RngAconfModel aconf = rBiz.getRngAconf(con__);
        paramMdl.setIdSelectable(aconf.getRarRngid());
        if (aconf.getRarRngid() != RngConst.RAR_SINSEI_NONE) {

            RingiIdModel model = rBiz.getRngidModel(paramMdl.getRng090idSid());
            if (model == null) {
                paramMdl.setIdSelectable(RngConst.RAR_SINSEI_NONE);
            } else {
                paramMdl.setRng090idSid(model.getRngSid());
                paramMdl.setRng090idTitle(model.getRngTitle());
                paramMdl.setIdPrefManualEditable(model.getRngManual());
            }
        }

        //経路情報の所為表示を行う。
        paramMdl.getRng090keiro().dspInit(reqMdl__, con__);
        if (paramMdl.getRng090rtpSpecVer() == RngConst.RNG_RTP_SPEC_VER_INIT) {
            List<Rng110KeiroDialogParamModel> basics = paramMdl.getRng090keiro().getBasicDrags();
            for (ListIterator<Rng110KeiroDialogParamModel> it = basics.listIterator();
                    it.hasNext();) {
                Rng110KeiroDialogParamModel drag = it.next();
                if (drag.getKeiroKbn() != EnumKeiroKbn.FREESET_VAL) {
                    it.remove();
                }
            }
        }
        //フォーム情報の初期表示を行う。
        paramMdl.getRng090template().setFormTable(paramMdl.getRng090templateJSON());
        paramMdl.getRng090template().dspInit(reqMdl__, con__, tempPath);

    }
    /**
     *
     * <br>[機  能] 編集時の初回アクセスか判定
     * <br>[解  説]
     * <br>[備  考]
     * @param rtMode テンプレートモード
     * @param cmd リクエストコマンド
     * @return 編集モードかつ初回アクセスかどうか
     */
    public boolean isEditFirstAccess(int rtMode, String cmd) {
        if (rtMode == RngConst.RNG_CMDMODE_EDIT
                && cmd.equals("060title")) {
            return true;
        }
        return false;
    }

    /**
     *
     * <br>[機  能] テンポラリディレクトリ作成判定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl paramMdl
     * @param appRoot アプリケーションルート
     * @param tempPath テンポラリディレクトリ
     * @throws TempFileException TempFileException
     * @throws IOToolsException IOToolsException
     * @throws IOException IOException
     * @throws SQLException SQLException
     */
    public void formTempCheck(
            Rng090ParamModel paramMdl,
            String appRoot,
            GSTemporaryPathModel tempPath)
            throws TempFileException, IOException, IOToolsException, SQLException {

        FormBuilder fb = paramMdl.getRng090template();
        fb.setFormTable(paramMdl.getRng090templateJSON());
        Collection<FormCellPrefarence> cells = fb.getFormMap().values();
        UDate now = new UDate();
        for (FormCellPrefarence fp : cells) {
            if (fp.getType().equals(EnumFormModelKbn.file)) {
                GSTemporaryPathModel formPath =
                        new GSTemporaryPathModel(
                                tempPath,
                                String.valueOf(
                                        fp.getSid()
                                        )
                                );
                Path fPath = Paths.get(formPath.getTempPath());
                if (Files.exists(fPath)) {
                    continue;
                }
                GSTemporaryPathUtil.getInstance().createTempDir(formPath);

                //formSid毎のディレクトリが作成されていない場合
                Temp temp = (Temp) fp.getBody();
                formTempCreate(temp, appRoot, now, formPath.getTempPath());
                temp.setTempPath(formPath);
                temp.dspInit(reqMdl__, con__);
            }
        }
    }

   /**
   *
   * <br>[機  能] テンポラリディレクトリ作成
   * <br>[解  説]
   * <br>[備  考]
   * @param temp Temp
   * @param appRoot アプリケーションルート
   * @param now 現在日時
   * @param tempPath テンポラリディレクトリ
   * @throws TempFileException TempFileException
   * @throws IOToolsException IOToolsException
   * @throws IOException IOException
   * @throws SQLException SQLException
   */
  public void formTempCreate(Temp temp, String appRoot, UDate now, String tempPath)
          throws TempFileException, IOException, IOToolsException, SQLException {

      CommonBiz cmnBiz = new CommonBiz();
      if (temp == null) {
          return;
      }

      if (temp.getSample() == null) {
          return;
      }
      if (temp.getSample().length == 0) {
          return;
      }
      String dateStr = now.getDateString(); //現在日付の文字列(YYYYMMDD)
      //取得したバイナリSIDからバイナリ情報を取得
      List<CmnBinfModel> cmnBinList = cmnBiz.getBinInfo(con__,
              temp.getSample(), reqMdl__.getDomain());
      int fileNum = 1;
      String path = IOTools.replaceFileSep(tempPath + "/");
      for (CmnBinfModel binData : cmnBinList) {
          cmnBiz.saveTempFile(dateStr, binData,
                  appRoot, path, fileNum);

          //ファイル情報(objファイル)にバイナリSIDを設定
          File objFilePath = Cmn110Biz.getObjFilePath(path, dateStr, fileNum);
          ObjectFile objFile = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
          Object fObj = objFile.load();
          Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
          fMdl.setBinSid(binData.getBinSid());
          objFile.save(fObj);

          fileNum++;
      }
  }

    /**
     *
     * <br>[機  能]4.8.0以前のテンプレートか等のモードの設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl paramMdl
     * @param cmd cmd
     * @throws SQLException SQLException
     */
    public void modeSet(Rng090ParamModel paramMdl, String cmd) throws SQLException {
        //編集モード時、テンプレート情報を取得する
        if (paramMdl.getRngTplCmdMode() == RngConst.RNG_CMDMODE_EDIT) {
            if (cmd.equals("060title")) {
                RngTemplateBiz rtBiz = new RngTemplateBiz();
                int rstSid = paramMdl.getRngSelectTplSid();
                RngTemplateModel rtModel = rtBiz.getRtpModel(rstSid, con__);
                if (rtModel != null) {
                    log__.debug("テンプレート情報をセットします。");
                    loadData(paramMdl, rtModel);
                } else {
                    log__.debug("テンプレート情報をセットしません。");
                    if (paramMdl.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
                        paramMdl.setRng090rtpSpecVer(RngConst.RNG_RTP_SPEC_VER_INIT);
                    }
                }
            }
        } else {
            log__.debug("追加モードです。");
            if (paramMdl.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
                paramMdl.setRng090rtpSpecVer(RngConst.RNG_RTP_SPEC_VER_INIT);
            } else {
                paramMdl.setRng090rtpSpecVer(RngConst.RNG_RTP_SPEC_VER_A480);
            }
        }
    }

    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param appRoot アプリケーションルート
     * @param cmd コマンド
     * @param userSid ユーザSID
     * @param tempDir テンポラリディレクトリ
     * @param mres Strutsメッセージリソース
     * @throws IOToolsException ファイル生成実行例外
     * @throws SQLException SQL実行時例外
     * @throws IOException 生成実行例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void initDsp(
            Rng090ParamModel paramMdl,
            String appRoot,
            String cmd,
            int userSid,
            GSTemporaryPathModel tempDir,
            MessageResources mres)
    throws SQLException, IOException, IOToolsException, TempFileException {

        UDate now = new UDate();
        paramMdl.setAccessDateString(now.getTimeStamp());
        initDsp(paramMdl, tempDir, mres);
        GSTemporaryPathUtil.getInstance().createTempDir(tempDir);
        if (paramMdl.getRng090rtpSpecVer() == RngConst.RNG_RTP_SPEC_VER_A480) {
            //添付ファイル生成
            formTempCheck(paramMdl, appRoot, tempDir);
        } else if (paramMdl.getRng090rtpSpecVer() == RngConst.RNG_RTP_SPEC_VER_INIT) {
            //編集モードの初期表示時jsonから内容を設定
            if (paramMdl.getRngTplCmdMode() == RngConst.RNG_CMDMODE_EDIT
                    && cmd.equals("060title")) {
                FormCell cell = paramMdl.getRng090template().getFormCell(0, 0);
                String defValue = ((TextInput) cell.getBody()).getDefaultValue();
                paramMdl.setRng090content(defValue);
                cell = paramMdl.getRng090template().getFormCell(1, 0);
                Temp temp = (Temp) cell.getBody();

                formTempCreate(temp, appRoot, now, tempDir.getTempPath());
            }
        }
        //カテゴリ情報の取得。
        __setCategoryLabelList(paramMdl, userSid);

        setFileLabelList(paramMdl, tempDir.getTempPath());



    }

    /**
     * <br>[機  能] 経路部分の描画設定 再描画用
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void initLoadKeiro(Rng090ParamModel paramMdl) throws SQLException {
        if (paramMdl.getRng090useKeiroTemplate() == 0) {
            RngChannelTemplateDao rctDao = new RngChannelTemplateDao(con__);
            RngChannelTemplateModel rctModel =
                    rctDao.select(
                            paramMdl.getRng090KeiroTemplateSid(),
                            paramMdl.getRng090KeiroTemplateUsrSid()
                            );
            if (rctModel != null) {
                RngTemplateKeiroSave saveBiz =
                        RngTemplateKeiroSave.createInstanceForRCT(
                                paramMdl.getRng090KeiroTemplateSid(),
                                paramMdl.getRng090KeiroTemplateUsrSid(),
                                reqMdl__, con__);
                paramMdl.setRng090keiro(saveBiz.loadRng110Keiro());
                paramMdl.setRng090KeiroTemplateName(rctModel.getRctName());
            }

        }
        //経路情報の所為表示を行う。
        paramMdl.getRng090keiro().dspInit(reqMdl__, con__);

    }

    /**
     * <br>[機  能] 添付ファイル一覧を生成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリ
     * @throws IOToolsException 例外
     */
    public void setFileLabelList(Rng090ParamModel paramMdl, String tempDir)
    throws IOToolsException {
        log__.debug("添付ファイル一覧を設定");
        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setRng090FileLabelList(cmnBiz.getTempFileLabelList(tempDir));
    }

    /**
     * <br>[機  能] 稟議テンプレートの削除を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid userSid
     * @param rtpSid 稟議テンプレートSID
     * @throws Exception 例外
     */
    public void deleteTpl(Rng090ParamModel paramMdl, int userSid, int rtpSid) throws Exception {

        RngTemplateDao rtdao = new RngTemplateDao(con__);
        //現在日時
        UDate now = new UDate();

        int sort = rtdao.select(rtpSid).getRtpSort();
        int catSid = rtdao.select(rtpSid).getRtcSid();

        //ソート順の更新を行う
        rtdao.updateSortAll2(paramMdl.getRngTemplateMode(), userSid, now, sort, catSid);

        //指定した稟議テンプレートSIDのテンプレートを論理削除
        rtdao.updateJkbn(rtpSid, RngConst.JKBN_DELETE,
                reqMdl__.getSmodel().getUsrsid());
    }

    /**
     * <br>[機  能] カテゴリ一覧を生成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void __setCategoryLabelList(Rng090ParamModel paramMdl, int usrSid) throws SQLException {
        log__.debug("カテゴリ一覧を設定");

        ArrayList<RngTemplateCategoryModel> catList = null;

        RngBiz biz = new RngBiz(con__);
        boolean adminUser = true;
        if (paramMdl.getRngTemplateMode() == RngConst.RNG_TEMPLATE_SHARE) {
            CommonBiz cmnBiz = new CommonBiz();
            adminUser = cmnBiz.isPluginAdmin(con__,
                    reqMdl__.getSmodel(), RngConst.PLUGIN_ID_RINGI); //プラグイン管理者

            //共有のカテゴリを取得する
            catList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_SHARE,
                    usrSid, adminUser, RngConst.RTPLIST_MOKUTEKI_KANRI);

        } else if (paramMdl.getRngTemplateMode() == RngConst.RNG_TEMPLATE_PRIVATE) {
            //個人のカテゴリを取得する
            catList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_PRIVATE,
                    usrSid, adminUser, RngConst.RTPLIST_MOKUTEKI_KANRI);
        }

        ArrayList<LabelValueBean> catLabel = new ArrayList<LabelValueBean>();
        if (catList != null) {
            catLabel = biz.createCategoryComb(reqMdl__, catList, false,
                    adminUser, RngConst.RTPLIST_MOKUTEKI_KANRI);
        }

        paramMdl.setRng090CategoryList(catLabel);
    }
    /**
    *
    * <br>[機  能] カテゴリアクセス権限チェック
    * <br>[解  説]
    * <br>[備  考]
    * @param tFlg テンプレート種別フラグ 0：個人 1：共有;
    * @param rtcSid カテゴリSID
    * @param isAdmin 管理者かどうか
    * @return チェック結果 true 権限あり
    * @throws SQLException SQL実行時例外
    */
    public boolean categoriAuthChk(int tFlg,
           int rtcSid,
           boolean isAdmin
           ) throws SQLException {
        //共有テンプレート カテゴリなしの場合
        if (rtcSid == 0 && tFlg == RngConst.RNG_TEMPLATE_SHARE) {
            return isAdmin;
        }
        //個人テンプレート カテゴリなしの場合
        if (rtcSid == 0 && tFlg == RngConst.RNG_TEMPLATE_PRIVATE) {
            return true;
        }

        RngTemplateCategoryDao rtcDao = new RngTemplateCategoryDao(con__);
        RngTemplateCategoryModel rtcModel = rtcDao.select(rtcSid);
        if (rtcModel.getRtcSid() == 0) {
            return false;
        }

        if (tFlg == RngConst.RNG_TEMPLATE_SHARE) {
            if (rtcModel.getUsrSid() != 0) {
                return false;
            }
            CommonBiz cmnBiz = new CommonBiz();
            boolean adminUser = cmnBiz.isPluginAdmin(con__,
                   reqMdl__.getSmodel(), RngConst.PLUGIN_ID_RINGI);

            if (adminUser) {
                return true;
            }
            RngTemplatecategoryAdmDao rtcAdmDao = new RngTemplatecategoryAdmDao(con__);
            ArrayList<Integer> amdSidList = rtcAdmDao.getRngTemplatecategorySidList(
                    reqMdl__.getSmodel().getUsrsid());
            if (!amdSidList.contains(rtcSid)) {
                return false;
            }
            //個人稟議テンプレート のユーザSIDが違う場合
        } else {
            if (rtcModel.getUsrSid() != reqMdl__.getSmodel().getUsrsid()) {
                return false;
            }

        }
        return true;

    }
}
