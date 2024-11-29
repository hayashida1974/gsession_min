package jp.groupsession.v2.rng.rng030;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.formbuilder.FormInputBuilder;
import jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rng.IRingiListener;
import jp.groupsession.v2.rng.RingiListenerModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RtpNotfoundException;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.biz.RngDoNextBiz;
import jp.groupsession.v2.rng.biz.RngFormBuildBiz;
import jp.groupsession.v2.rng.biz.RngUsedDataBiz;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.dao.RngBinDao;
import jp.groupsession.v2.rng.dao.RngDairiUserDao;
import jp.groupsession.v2.rng.dao.RngKeiroStepDao;
import jp.groupsession.v2.rng.dao.RngKeirostepSelectDao;
import jp.groupsession.v2.rng.dao.RngRndataDao;
import jp.groupsession.v2.rng.dao.RngSingiDao;
import jp.groupsession.v2.rng.model.AccountDataModel;
import jp.groupsession.v2.rng.model.RingiChannelDataModel;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RingiRequestModel;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngBinModel;
import jp.groupsession.v2.rng.model.RngDairiUserModel;
import jp.groupsession.v2.rng.model.RngKeiroStepModel;
import jp.groupsession.v2.rng.model.RngKeirostepSelectModel;
import jp.groupsession.v2.rng.model.RngRndataModel;
import jp.groupsession.v2.rng.model.RngSingiModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.rng.pdf.RngPdfWriter;
import jp.groupsession.v2.rng.pdf.RngTanPdfModel;
import jp.groupsession.v2.rng.pdf.RngTanPdfUtil;
import jp.groupsession.v2.rng.rng020.Rng020Biz;
import jp.groupsession.v2.rng.rng020.Rng020Keiro;
import jp.groupsession.v2.rng.rng020.Rng020KeiroBlock;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import net.sf.json.JSONArray;

/**
 * <br>[機  能] 稟議内容確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng030Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng030Biz.class);

    /** 確認用添付ファイルダウンロードURL */
    private static final String TEMPFILE_DOWNLOAD_URL  =
            "../ringi/rng030.do?CMD=fileDownload&rng030fileId={binSid}";

    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Rng030Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 表示情報の設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param userSid ユーザSID
     * @param req リクエスト
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return 稟議が存在するか true:存在する false:存在しない
     * @throws RtpNotfoundException テンプレート削除済み例外
     */
    public boolean setDspData(RequestModel reqMdl, Rng030ParamModel paramMdl, Connection con,
                            String appRoot, GSTemporaryPathModel tempDir, int userSid,
                            HttpServletRequest req)
    throws Exception {

        int apprMode = paramMdl.getRngApprMode();
        int rngSid = paramMdl.getRngSid();

        int rksSid = viewKeiroSid(rngSid, userSid, paramMdl.getRngProcMode());
        paramMdl.setRng030RksSid(rksSid);

        //稟議情報を設定
        RingiDao ringiDao = new RingiDao(con);
        RingiDataModel model = ringiDao.getRingiData(rngSid, userSid, rksSid);

        //タイトル、内容がnullであれば存在しない
        if (model.getRngTitle() == null) {
            return false;
        }

        paramMdl.setRng030Status(model.getRngStatus());
        paramMdl.setRng030apprUser(model.getApprUser());
        if (model.isApprUserDelFlg()) {
            paramMdl.setRng030apprUsrJkbn(GSConstUser.USER_JTKBN_DELETE);
        }
        paramMdl.setRng030apprUsrUkoFlg(model.getUsrUkoFlg());
        paramMdl.setRng030makeDate(model.getStrRngAppldate());
        paramMdl.setRng030ViewTitle(model.getRngTitle());
        paramMdl.setRng030ID(model.getRngId());

        paramMdl.setRng030completeFlg(model.getRngCompflg());

        //処理モード設定
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        //再申請フラグ
        switch (apprMode) {
            case RngConst.RNG_APPRMODE_DISCUSSING :
                //申請中案件管理画面からの遷移の場合は管理者承認モード
                paramMdl.setRng030CmdMode(Rng030Form.CMDMODE_ADMINAPPR);
                break;

            case RngConst.RNG_APPRMODE_COMPLETE :
                //完了案件管理画面からの遷移の場合は閲覧モード
                paramMdl.setRng030CmdMode(Rng030Form.CMDMODE_VIEW);
                break;

            default :
                ringiModeSet(paramMdl, userSid, model, cmd, rksSid, appRoot, tempDir.getTempPath());
                if (model.getRngApplicate() == userSid) {
                    if (model.getRssStatus() == RngConst.RNG_RNCSTATUS_CONFIRM
                            && model.getRncType() == RngConst.RNG_RNCTYPE_APPL) {
                        paramMdl.setRng030CmdMode(Rng030Form.CMDMODE_VIEW);
                        paramMdl.setRngApprMode(RngConst.RNG_APPRMODE_APPL);
                        ringiSaisinsei(paramMdl, model, con, rngSid, appRoot);
                    }
                }
        }

        RngTemplateModel rptMdl = templateRoad(paramMdl, rngSid, userSid, appRoot, tempDir,
                    (paramMdl.getRng030InitFlg() == 0));
        paramMdl.setRng030UseApiConnect(rptMdl.getRtpUseApiconnect());
        paramMdl.setRng030ApiComment(rptMdl.getRtpApiconnectComment());


        //処理モード=再申請以外の場合は添付情報を設定
        if (paramMdl.getRngApprMode() != RngConst.RNG_APPRMODE_APPL) {
            paramMdl.setTmpFileList(ringiDao.getRingiTmpFileList(rngSid, 0));
        }

        //タイトル
        if (paramMdl.getRng030InitFlg() == 0
                && paramMdl.getRng030Title() == null) {
            if (model.getRngApplicate() == userSid
                    && model.getRngCompflg() == 0) {
                paramMdl.setRng030Title(model.getRngTitle());
            }
        }

        //添付ファイル一覧を設定
        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setRng030fileList(cmnBiz.getTempFileLabelList(tempDir.getTempPath()));

        //追加可能経路SIDキャッシュの初期化

        //経路追加可能判定クラスの生成
        Rng030KeiroAddBiz keiroAddBiz = null;
        keiroAddBiz = new Rng030KeiroAddBiz(
                con__, reqMdl__, model.getRtpSid(), model.getRtpVer(),
                paramMdl.getRng030template());

        //稟議経路情報を設定
        List<Rng030KeiroParam> keiroList = getKerio(ringiDao, rngSid);

        //経路↓のカウントなど取得
        int listCount = 0;
        int singedFlg = RngConst.RNG_RNCSTATUS_APPR;
        for (Rng030KeiroParam kMdl : keiroList) {
            if (singedFlg == RngConst.RNG_RNCSTATUS_CONFIRM) {
                singedFlg = RngConst.RNG_RNCSTATUS_NOSET;
            }
            //審議種別 0:審議者 1:最終確認者 2:申請者
            if (kMdl.getKeiroSingi() == RngConst.RNG_RNCTYPE_APPR) {
                listCount += 1;
            }

            //差し戻し位置選択
            if (kMdl.getKeiroStatus() == RngConst.RNG_RNCSTATUS_CONFIRM) {
                boolean sasiOk = false;
                int sasiSortNo = -1;
                for (Rng030KeiroParam kloop :keiroList) {
                    if (kloop.getKeiroStepSid() == rksSid) {
                        break;
                    }
                    if (kloop.getKeiroDelFlg() == 0) {
                        sasiOk = true;
                        sasiSortNo = kloop.getKeiroStepSid();
                    }
                }
                if (sasiOk) {
                    paramMdl.setRng030SasiNo(sasiSortNo);
                }
                singedFlg = RngConst.RNG_RNCSTATUS_CONFIRM;

                //後閲指示先初期位置
                paramMdl.setRng030koetuNo(kMdl.getKeiroStepSid());
            }

            //追加可能フラグ設定
            if (kMdl.getKeiroSingi() == 0) {

                keiroAddBiz.prefStepAddibleFlag(
                        kMdl, singedFlg);
            }
        }

        //ボタン表示設定
        paramMdl.setRng030BtnDisp(buttonDisp(paramMdl, apprMode, userSid,
                keiroAddBiz.getAddibleRowCnt(), keiroList, rptMdl));

        if (model.getRngApplicate() == userSid) {
            paramMdl.setRng030InUsrApprFlg(1);
        }
        if (paramMdl.getRng030BtnDisp().getBtnKoetuDispFlg() == 1) {
            paramMdl.setRng030KoetuFlg(0);
        }

        paramMdl.setRng030keiroListForm(keiroList);

        List<RingiChannelDataModel> channelList = ringiDao.getChannelList(rngSid);
        List<RingiChannelDataModel> apprUser = new ArrayList<RingiChannelDataModel>();
        List<RingiChannelDataModel> confirmUser = new ArrayList<RingiChannelDataModel>();

        for (RingiChannelDataModel channelMdl : channelList) {
            if (channelMdl.getRncType() == RngConst.RNG_RNCTYPE_APPR) {
                apprUser.add(channelMdl);
            } else if (channelMdl.getRncType() == RngConst.RNG_RNCTYPE_CONFIRM) {
                confirmUser.add(channelMdl);
            } else if (channelMdl.getRncType() == RngConst.RNG_RNCTYPE_APPL) {
                //申請者の経路情報は一番上に設定
                apprUser.add(0, channelMdl);
            }
        }

        if (paramMdl.getRng030AddKeiroMode() != 0) {
            keiroAddBiz.dspInitAddibleRow(paramMdl.getRng030addKeiroMap());
        }

        paramMdl.setChannelList(apprUser);
        paramMdl.setConfirmChannelList(confirmUser);
        paramMdl.setChannelListCount(String.valueOf(listCount));
        paramMdl.setConfirmChannelListCount(String.valueOf(confirmUser.size()));

        log__.debug("End");

        return true;
    }

    /**
    *
    * <br>[機  能] テンプレートデータのロード
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータモデル
    * @param rngSid 稟議SID
    * @param userSid ユーザSID
    * @param appRoot  アプリケーションのルートパス
    * @param tempDir  テンポラリディレクトリパス
    * @param first   初回フラグ
    * @return テンプレート情報モデル
    * @throws SQLException SQLException
    * @throws RtpNotfoundException RtpNotfoundException
    */
   public RngTemplateModel templateRoad(Rng030ParamModel paramMdl, int rngSid, int userSid,
           String appRoot, GSTemporaryPathModel tempDir, boolean first)
           throws SQLException, RtpNotfoundException, Exception {


       RingiDao ringiDao = new RingiDao(con__);
       RingiDataModel model = ringiDao.getRingiData(rngSid, userSid,
               paramMdl.getRng030RksSid());
       FormInputBuilder ib = paramMdl.getRng030template();
       RngFormBuildBiz rngFormBiz = new RngFormBuildBiz(reqMdl__);
       //ib.getFormMap()の中身が空でない場合一度編集モードを開いてからの表示のため
       //ロードを行わず前画面の値を引き継ぐ

       if (paramMdl.getRngApprMode() != RngConst.RNG_APPRMODE_APPL
        || ib.getFormMap().isEmpty()) {
           //フォーム入力ビルダーに保存データをロード
           rngFormBiz.loadInputData(con__, paramMdl.getRng030template(), model);
       }

       //フォーム入力ビルダーにテンプレート情報をロード
       rngFormBiz.loadTenplateData(con__,  paramMdl.getRng030template(),
               model.getRtpSid(), model.getRtpVer());

       if (paramMdl.getRngApprMode() != RngConst.RNG_APPRMODE_APPL) {
           // 再申請時以外 → ダウンロードURLが付いたファイル名一覧
           String downloadUrl = TEMPFILE_DOWNLOAD_URL;
           downloadUrl += ("&rngSid=" + rngSid);
           downloadUrl += ("&rngApprMode=" + paramMdl.getRngApprMode());
           downloadUrl += ("&rng010ViewAccount=" + paramMdl.getRng010ViewAccount());

           FormInputInitPrefarence pref = new FormInputInitPrefarence();
           pref.setAppRoot(appRoot);
           pref.setLoad(false);
           pref.setMode(FormInputBuilder.INITMODE_DSP);
           pref.setUrl(downloadUrl);
           pref.setTempDir(tempDir);
           paramMdl.getRng030template().setInitPrefarence(pref);
           paramMdl.getRng030template().dspInit(reqMdl__, con__);
       } else {
           // 再申請時 → 入力表示対応(添付ファイル要素にサンプル表示なし)
           FormInputInitPrefarence pref = new FormInputInitPrefarence();
           pref.setAppRoot(appRoot);
           pref.setLoad(first);
           pref.setMode(FormInputBuilder.INITMODE_INPUT);
           pref.setUrl(null);
           pref.setTempDir(tempDir);
           paramMdl.getRng030template().setInitPrefarence(pref);
           paramMdl.getRng030template().dspInit(reqMdl__, con__);
       }

       RngTemplateModel rptMdl = rngFormBiz.getRtpModel(con__,
                                   model.getRtpSid(), model.getRtpVer());

       return rptMdl;
   }
   /**
    *
    * <br>[機  能] 確認時コメントを編集可能かチェック
    * <br>[解  説]
    * <br>[備  考]
    * @param userSid ユーザSID
    * @param rksSid 経路ステップSID
    * @return false 経路ステップにユーザがいないためコメント編集不可
    * @throws SQLException SQL実行時例外
    */
   public boolean chkCommentEdit(int userSid, int rksSid) throws SQLException {
       RngSingiDao rssDao = new RngSingiDao(con__);
       List<Integer> usrSids = rssDao.getSingiUser(rksSid);
       return usrSids.contains(userSid);
   }
   /**
   *
   * <br>[機  能] 代理人のアクセス権限チェック
   * <br>[解  説]
   * <br>[備  考]
   * @param userSid アクセス対象アカウント
   * @return false 代理人のアクセス権限が不正
   * @throws SQLException SQL実行時例外
   */
   public boolean chkViewAccount(int userSid) throws SQLException {
      CommonBiz cmnBiz = new CommonBiz();

      int sUserSid = reqMdl__.getSmodel().getUsrsid();
      if (userSid == 0) {
          return cmnBiz.isPluginAdmin(con__, reqMdl__.getSmodel(), RngConst.PLUGIN_ID_RINGI);
      }
      if (userSid == sUserSid) {
          return true;
      }
      RngDairiUserDao dairiDao = new RngDairiUserDao(con__);
      UDate now = new UDate();
      List<AccountDataModel> dairiList = dairiDao.select(sUserSid, now, now);
      for (AccountDataModel acMdl : dairiList) {
          if (acMdl.getAccountSid() == userSid) {
              return true;
          }
      }
      return false;
   }

   /**
   *
   * <br>[機  能] 確認時コメントを編集可能かチェック
   * <br>[解  説]
   * <br>[備  考]
   * @param rngSid 稟議SID
   * @param userSid ユーザSID
   * @param con コネクション
   * @return nRtn モード
   * @throws SQLException SQL実行時例外
   */
  public int getProcMode(int rngSid, int userSid, Connection con) throws SQLException {

      int nRtn = RngConst.RNG_MODE_JYUSIN;

      RngSingiDao sDao = new RngSingiDao(con);
      int rksSid = sDao.getRksSid(rngSid, userSid, RngConst.RNG_RNCSTATUS_CONFIRM);
      if (rksSid == 0) {
          RngRndataDao rDao = new RngRndataDao(con);
          RngRndataModel rMdl = rDao.select(rngSid);
          if (rMdl == null) {
              return nRtn;
          }
          if (rMdl.getRngCompflg() == 0) {
              nRtn = RngConst.RNG_MODE_SINSEI;
          } else {
              nRtn = RngConst.RNG_MODE_KANRYO;
          }
      }
      return nRtn;
  }

   /**
    *
    * <br>[機  能] 稟議の閲覧権限チェック
    * <br>[解  説] 指定のアカウントで稟議が閲覧できるかを返す
    * <br>[備  考] 指定のアカウントの使用権限はchkViewAccountで確認すること
    * @param userSid アクセスするアカウント
    * @param rngSid 稟議SID
    * @return false:稟議へのアクセス権限が不正
    * @throws SQLException SQL実行時例外
    */
   public boolean chkViewRingi(int userSid, int rngSid) throws SQLException {
       RingiDao rngDao = new RingiDao(con__);
       //稟議経路情報を設定
       List<Rng030KeiroParam> keiroList = getKerio(rngDao, rngSid);
       if (userSid == 0) {
           if (keiroList.size() <= 0) {
               return false;
           }
           return true;
       }
       RingiDataModel rngMdl = rngDao.getRingiData(rngSid, userSid);
       if (rngMdl.getRngApplicate() == userSid) {
           return true;
       }
       //confedによる判定はフェーズ2で差し戻し時のユーザ閲覧制限で使う
       //boolean confed = false;
       for (Rng030KeiroParam kMdl : keiroList) {
           List<Rng030SingiParam> singList = kMdl.getSingiList();
           for (Rng030SingiParam sMdl : singList) {
               if (sMdl.getUserSid() == userSid
                   && (
                       //!confed
                       kMdl.getKeiroStatus() != RngConst.RNG_RNCSTATUS_NOSET
                       || kMdl.getKeiroKoetuSizi() == RngConst.RNG_KOETU_YES
                       || kMdl.getKeiroSingi() == RngConst.RNG_RNCTYPE_CONFIRM)
                       ) {
                   return true;
               }
           }
           /*if (kMdl.getKeiroStatus() == RngConst.RNG_RNCSTATUS_CONFIRM) {
               confed = true;
           }*/

       }
       return false;
   }
   /**
   *
   * <br>[機  能] どこの経路で確認しているのかを取得する
   * <br>[解  説]
   * <br>[備  考]
   * @param rngSid 稟議SID
   * @param userSid ユーザSID
   * @param procMode 稟議モード
   * @return 経路SID
   * @throws SQLException SQLException
   */
  public int viewKeiroSid(int rngSid, int userSid, int procMode)
          throws SQLException {

      //どこの経路なのか確認
      RngKeiroStepDao kDao = new RngKeiroStepDao(con__);
      List<Rng030KeiroParam> kList = kDao.getRksSid(rngSid, userSid);
      int rksSid = 0;
      int maxRksSid = 0;
      for (Rng030KeiroParam kMdl : kList) {
          if (procMode == RngConst.RNG_MODE_JYUSIN) {
              if (kMdl.getKeiroStatus() == RngConst.RNG_RNCSTATUS_CONFIRM) {
                  rksSid = kMdl.getKeiroStepSid();
              }
          } else if (procMode == RngConst.RNG_MODE_SINSEI) {
              if (kMdl.getKeiroStatus() == RngConst.RNG_RNCSTATUS_CONFIRM) {
                  rksSid = kMdl.getKeiroStepSid();
              }
          } else if (procMode == RngConst.RNG_MODE_KANRYO) {
              rksSid = kMdl.getKeiroStepSid();
          } else if (procMode == RngConst.RNG_MODE_KOETU) {
              if (kMdl.getKeiroStatus() != RngConst.RNG_RNCSTATUS_CONFIRM) {
                  if (kMdl.getKeiroSingi() == RngConst.RNG_RNCTYPE_APPR
                          && kMdl.getKeiroKoetuSizi() == RngConst.RNG_KOETU_YES) {
                      rksSid = kMdl.getKeiroStepSid();
                  }
              }
          }
          maxRksSid = kMdl.getKeiroStepSid();
      }
      if (rksSid == 0) {
          rksSid = maxRksSid;
      }
      return rksSid;
  }


    /**
     *
     * <br>[機  能]経路リスト取得
     * <br>[解  説]
     * <br>[備  考]
     * @param ringiDao RingiDao
     * @param rngSid 稟議SID
     * @throws SQLException SQLException
     * @return keiroList List<Rng030KeiroParam>
     */
    public List<Rng030KeiroParam> getKerio(RingiDao ringiDao, int rngSid)
            throws SQLException {
        //稟議経路情報を設定
        List<Rng030KeiroParam> keiroList = ringiDao.getKeiroList(rngSid);
        List<Rng030SingiParam> singiList = ringiDao.getSingiList(rngSid);

        //審議リストを経路SIDごとのMapを生成
        Map<Integer, List<Rng030SingiParam>> rksSidSingiListMap
          = new HashMap<Integer, List<Rng030SingiParam>>();
        for (Rng030SingiParam sMdl : singiList) {
            Integer rksSid = Integer.valueOf(sMdl.getKeiroSid());
            List<Rng030SingiParam> sList;
            if (rksSidSingiListMap.containsKey(rksSid)) {
                sList = rksSidSingiListMap.get(rksSid);
            } else {
                sList = new ArrayList<Rng030SingiParam>();
                rksSidSingiListMap.put(rksSid, sList);
            }
            sList.add(sMdl);
        }


        for (Rng030KeiroParam kMdl : keiroList) {
            Integer rksSid = Integer.valueOf(kMdl.getKeiroStepSid());
            List<Rng030SingiParam> sList;
            if (rksSidSingiListMap.containsKey(rksSid)) {
                sList = rksSidSingiListMap.get(rksSid);
            } else {
                sList = new ArrayList<Rng030SingiParam>();
            }
            int keiroCnt = 0;
            int delUserCnt = 0;
            String sKoetuUserMei = null;
            for (Rng030SingiParam sMdl : sList) {
                sKoetuUserMei = sMdl.getSingiKoetu();
                keiroCnt += 1;
                if (sMdl.getUserJkbn() != 0) {
                    delUserCnt += 1;
                }
            }
            if (keiroCnt == delUserCnt) {
                kMdl.setKeiroDelFlg(1);
            }

            kMdl.setSingiList(sList);
            kMdl.setKeiroCount(keiroCnt);

            if (keiroCnt > 0) {
                int nPro = kMdl.getKeiroProgress();
                int nMes = 0;
                if (nPro == 0) {
                    // 全員の審議
                    nMes = RngConst.RNG_OUT_CONDITION_DELIBERATION;
                } else if (nPro == 1) {
                    // 全員の承認
                    nMes = RngConst.RNG_OUT_CONDITION_APPROVAL;
                } else if (nPro == 2) {
                    // 人かの承認
                    nMes = RngConst.RNG_OUT_CONDITION_NUMBER;
                } else {
                    // 割かの承認
                    nMes = RngConst.RNG_OUT_CONDITION_RATE;
                }
                kMdl.setKeiroMessage(nMes);
            }
            if (kMdl.getKeiroStatus() == 6) {
                kMdl.setKeiroKoetuMei(sKoetuUserMei);
            }
        }
        return keiroList;
    }

    /**
     *
     * <br>[機  能] ボタンの表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ
     * @param apprMode 遷移元画面
     * @param userSid ユーザSID
     * @param keiroAddibleStepCnt 経路追加可能なステップ数
     * @param kParam 経路パラメータ
     * @param rtpMdl テンプレート情報
     * @throws SQLException SQLException
     * @return ret Rng030ButtonDispParam
     */
    public Rng030ButtonDispParam buttonDisp(Rng030ParamModel paramMdl,
            int apprMode, int userSid, int keiroAddibleStepCnt,
            List<Rng030KeiroParam> kParam, RngTemplateModel rtpMdl)
                    throws SQLException {

        int procmode = paramMdl.getRngProcMode();    // 受信,進行中,後閲
        int rngSid   = paramMdl.getRngSid();         // 稟議SID
        int rksSid   = paramMdl.getRng030RksSid();   // 経路SID
        int dairiFlg = paramMdl.getRng010DairiFlg(); // 代理フラグ 0:正規ユーザ 1:代理人

        RingiDao ringiDao = new RingiDao(con__);
        RingiDataModel ringiMdl = ringiDao.getRingiData(rngSid, userSid, rksSid);
        Rng030ButtonDispParam ret = new Rng030ButtonDispParam();

        RngKeiroStepDao kDao = new RngKeiroStepDao(con__);
        RngKeiroStepModel kMdl = kDao.select(rksSid);

        //承認・却下
        /* 1 未完了のもの
         * 2 承認者であること
         * 3 稟議が申請中であること（差し戻し時は却下中になるためこの条件はなし）
         * 4 自分が現在確認中であること
         * 5 受信または後閲できたこと
         * 6 一覧画面から遷移したこと
         * なお後閲で入ってきたユーザは4ではなく
         * 7 後閲可能ユーザであること
         * 8 確認中以外であること
         * 9 後閲可能ユーザが存在すること*/

        if (ringiMdl.getRngCompflg() == RngConst.RNG_COMPFLG_UNDECIDED
                && ringiMdl.getRncType() == RngConst.RNG_RNCTYPE_APPR
                //&& ringiMdl.getRngStatus() == RngConst.RNG_STATUS_REQUEST
                && apprMode == RngConst.RNG_APPRMODE_APPR
                && (procmode == RngConst.RNG_MODE_JYUSIN
                ||  procmode == RngConst.RNG_MODE_KOETU)) {
            if (ringiMdl.getRncStatus() == RngConst.RNG_RNCSTATUS_CONFIRM
                    || (ringiMdl.getRncStatus() != RngConst.RNG_RNCSTATUS_CONFIRM
                    && kMdl != null
                    && kMdl.getRksKoetuSizi() == 0
                    && kDao.getApprButtonFlg(rksSid, rngSid))) {
                ret.setBtnApprovalDispFlg(1);
                ret.setBtnDismissalDispFlg(1);
            }
        }

        //経路に追加
        /* 1 未完了のもの
         * 2 承認者であること
         * 3 自分が現在確認中であること
         * 4 受信できたこと
         * 5 一覧画面から遷移したこと
         * 6 経路に追加できるステップ*/

        if (ringiMdl.getRngCompflg() == RngConst.RNG_COMPFLG_UNDECIDED
                && ringiMdl.getRncType() == RngConst.RNG_RNCTYPE_APPR
                && apprMode == RngConst.RNG_APPRMODE_APPR
                && procmode == RngConst.RNG_MODE_JYUSIN
                && ringiMdl.getRncStatus() == RngConst.RNG_RNCSTATUS_CONFIRM
                && keiroAddibleStepCnt > 0) {
            ret.setBtnPathAddDispFlg(1);
        }

        //差し戻し
        /*
         * 1 未完了のもの
         * 2 承認者であること
         * 3 自分が現在確認中であること
         * 4 受信できたこと
         * 5 一覧画面から遷移してきたこと
         * 6 差し戻し可能な経路があるか*/
        if (ringiMdl.getRngCompflg() == RngConst.RNG_COMPFLG_UNDECIDED
                && ringiMdl.getRncType() == RngConst.RNG_RNCTYPE_APPR
                && ringiMdl.getRncStatus() == RngConst.RNG_RNCSTATUS_CONFIRM
                && procmode == RngConst.RNG_MODE_JYUSIN
                && apprMode == RngConst.RNG_APPRMODE_APPR) {
            boolean sasiOk = false;
            for (Rng030KeiroParam kloop :kParam) {
                if (kloop.getKeiroStepSid() == rksSid) {
                    break;
                }
                if (kloop.getKeiroDelFlg() == 0) {
                    sasiOk = true;
                    break;
                }
            }
            if (!sasiOk) {
                sasiOk = ringiDao.getDelApprUser(rngSid);
            }
            if (sasiOk) {
                ret.setBtnRemandDispFlg(1);
            }
        }

        //再申請
        /*
         * 1 未完了のもの
         * 2 申請者であること
         * 3 稟議が却下であること
         * 4 一覧画面から遷移してきたこと
         * 5 代理人ではないこと*/
        if (ringiMdl.getRngCompflg() == RngConst.RNG_COMPFLG_UNDECIDED
                && ringiMdl.getRncType() == RngConst.RNG_RNCTYPE_APPL
                && ringiMdl.getRngStatus() == RngConst.RNG_STATUS_REJECT
                && ringiMdl.getRncStatus() == RngConst.RNG_RNCSTATUS_CONFIRM
                && dairiFlg == 0) {
            ret.setBtnReapplyDispFlg(1);
            //再計算ボタン表示設定
            if (paramMdl.getRng030template().isHaveAutoCalc()) {
                ret.setBtnRecalcDispFlg(1);
            }
        }

        //後閲
        /* 1 未完了のもの
         * 2 承認者であること
         * 3 確認中以外であること
         * 4 後閲権限をもっていること
         * 5 後閲できたこと
         * 6 一覧画面から遷移してきたこと
         * 7 後閲可能ユーザが存在すること
         * */
        if (ringiMdl.getRngCompflg() == RngConst.RNG_COMPFLG_UNDECIDED
                && ringiMdl.getRncType() == RngConst.RNG_RNCTYPE_APPR
                && ringiMdl.getRncStatus() != RngConst.RNG_RNCSTATUS_CONFIRM
                && procmode == RngConst.RNG_MODE_KOETU
                && apprMode == RngConst.RNG_APPRMODE_APPR) {
            if (kMdl != null
                    && kMdl.getRksKoetuSizi() == 0
                    && kDao.getKoetuButtonFlg(rngSid, kDao.getSortNoKeiro(rksSid, rngSid))) {
                ret.setBtnKoetuDispFlg(1);
            }
        }

        //確認
        /* 1 完了済み
         * 2 最終確認者の場合
         * 3 確認中
         * 4 一覧画面から遷移してきたこと
         * 5 稟議が決裁であること
         * 6 受信又は完了できたこと
         */
        if (ringiMdl.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE
                && ringiMdl.getRncType() == RngConst.RNG_RNCTYPE_CONFIRM
                && ringiMdl.getRncStatus() == RngConst.RNG_RNCSTATUS_CONFIRM
                && ringiMdl.getRssStatus() == RngConst.RNG_RNCSTATUS_NOSET
                && apprMode == RngConst.RNG_APPRMODE_APPR
                && ringiMdl.getRngStatus() == RngConst.RNG_STATUS_SETTLED
                && (procmode == RngConst.RNG_MODE_JYUSIN
                || procmode == RngConst.RNG_MODE_KANRYO)) {
            ret.setBtnConfDispFlg(1);
        }

        //完了
        /* 1 稟議の状態 = 否認の時
         * 2 受信していること
         * 3 一覧画面から遷移してきたこと
         * 4 ログインユーザが承認者の場合
         * 5 個人承認であること*/
        if (ringiMdl.getRngStatus() == RngConst.RNG_STATUS_REJECT
                && procmode == RngConst.RNG_MODE_JYUSIN
                && apprMode == RngConst.RNG_APPRMODE_APPR
                && userSid != ringiMdl.getRngApplicate()) {
            int keiroCount = ringiDao.getSingiCount(rksSid);
            if (keiroCount == 1) {
                ret.setBtnCompDispFlg(1);
            }
        }

        //強制完了・強制削除
        /*1 管理者設定からきたか*/
        if (apprMode == RngConst.RNG_APPRMODE_DISCUSSING) {
            ret.setBtnForcedCompDispFlg(1);
            ret.setBtnForcedDelDispFlg(1);
        }

        //スキップ
        /* 1 管理者設定からきたか
         * 2 現在確認中のユーザが申請者、最後の承認者以外の場合、スキップボタンを表示する*/
        if (apprMode == RngConst.RNG_APPRMODE_DISCUSSING) {

            RngKeiroStepModel keirostepMdl = new RngKeiroStepModel();
            keirostepMdl.setRksSid(kDao.getApprovalKeiro(rngSid));
            keirostepMdl.setRngSid(rngSid);
            int nextRksSid = kDao.lastAuthorizer(keirostepMdl, 1);
            if (keirostepMdl.getRksStatus() != RngConst.RNG_RNCSTATUS_CONFIRM
            && nextRksSid != 0) {

                ret.setBtnSkipDispFlg(1);
            }
        }

        //複写して新規申請
        /* 1 一覧画面から遷移してきたこと
         * 2 代理人以外(申請が可能なユーザ)
         * 3 稟議で使用されているテンプレートが使用可能 */
        if ((apprMode == RngConst.RNG_APPRMODE_APPR || apprMode == RngConst.RNG_APPRMODE_APPL)
                && dairiFlg == 0) {

            Rng020Biz rngBiz = new Rng020Biz(con__, reqMdl__);

            // テンプレート使用権限があるか判定
            if (rngBiz.isUseableTemplate(rtpMdl)) {
                ret.setBtnReproductionDispFlg(1);
            }
        }

        //取り下げ
        /* 1 申請者
         * 2 未完了
         * 3 一覧画面からの遷移の場合*/
        if (ringiMdl.getRngApplicate() == userSid
                && ringiMdl.getRngCompflg() == RngConst.RNG_COMPFLG_UNDECIDED) {
            ret.setBtnTorisageDispFlg(1);
        }

        //コメント編集ボタンフラグ
        /* 1 進行中、又は完了
         * 2 一覧画面から遷移してきたこと*/
        if ((procmode == RngConst.RNG_MODE_SINSEI
                || procmode == RngConst.RNG_MODE_KANRYO)
                && apprMode == RngConst.RNG_APPRMODE_APPR) {
            ret.setBtnCommentDispFlg(1);
        }
        return ret;
    }

    /**
     *
     * <br>[機  能] 再申請設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rng030ParamModel
     * @param model    RngDataModel
     * @param con      Connection
     * @param rngSid   稟議SID
     * @param appRoot  アプリケーションのルートパス
     * @throws TempFileException TempFileException
     * @throws IOToolsException IOToolsException
     * @throws IOException IOException
     * @throws SQLException SQLException
     */

    private void ringiSaisinsei(Rng030ParamModel paramMdl, RingiDataModel model, Connection con,
            int rngSid, String appRoot)
                    throws SQLException, IOException, IOToolsException, TempFileException {
        //稟議一覧からの遷移
        //またはメインからの遷移の場合はタイトル、内容、添付ファイルを設定
        if (paramMdl.getRng030InitFlg() == 0
                && paramMdl.getRng030Title() == null) {
            paramMdl.setRng030Title(model.getRngTitle());
        }

        paramMdl.setRng030Content(model.getRngContent());
    }

    /**
     *
     * <br>[機  能]モードをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @param model 稟議データモデル
     * @param cmd コマンド
     * @param rksSid 経路SID
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行時エラー
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    private void ringiModeSet(Rng030ParamModel paramMdl, int userSid,
            RingiDataModel model, String cmd, int rksSid,
            String appRoot, String tempDir)
    throws IOException, IOToolsException, SQLException, TempFileException {
        //稟議が完了している場合
        if (model.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE) {
            if (model.getRncType() == RngConst.RNG_RNCTYPE_APPR) {
                //承認者の場合は閲覧モード
                paramMdl.setRng030CmdMode(Rng030Form.CMDMODE_VIEW);
            } else if (model.getRngStatus() == RngConst.RNG_STATUS_SETTLED) {
                // 決裁された場合
                int rssStatus = model.getRssStatus();
                //最終確認者
                if (model.getRncType() == RngConst.RNG_RNCTYPE_CONFIRM
                && (rssStatus == RngConst.RNG_RNCSTATUS_NOSET
                || rssStatus == RngConst.RNG_RNCSTATUS_CONFIRM)) {
                    //未確認の場合は確認モード
                    paramMdl.setRng030CmdMode(Rng030Form.CMDMODE_CONFIRM);
                } else {
                    //確認済みの場合は閲覧モード
                    paramMdl.setRng030CmdMode(Rng030Form.CMDMODE_VIEW);
                }
            } else {
                // 却下および強制完了された場合は閲覧モード
                paramMdl.setRng030CmdMode(Rng030Form.CMDMODE_VIEW);
            }

            //申請者 = ログインユーザの場合、複写して申請ボタンを表示する
            paramMdl.setRng030copyApplBtn(model.getRngApplicate() == userSid);
        } else {

            // 申請者 != ログインユーザ
            // かつ稟議一覧またはメインからの遷移の場合はコメントを設定
            if ((cmd.equals("rng030")
            || paramMdl.getRngDspMode() == RngConst.RNG_MODE_MAIN)
            && model.getRngApplicate() != userSid) {
                RngSingiDao singiDao = new RngSingiDao(con__);
                RngSingiModel singiMdl = singiDao.singiSelect(rksSid, userSid);
                if (singiMdl != null && paramMdl.getRng030Comment() == null) {
                    paramMdl.setRng030Comment(singiMdl.getRssComment());
                }

                //添付ファイル情報取得
                this.copySingiBinList(rksSid, userSid, appRoot, tempDir);
            }

            //稟議の承認順がログインユーザ かつ 申請者がログインユーザではない場合
            //承認モードに設定する
            if (model.getRssStatus() == RngConst.RNG_RNCSTATUS_CONFIRM
            && paramMdl.getRngProcMode() != RngConst.RNG_MODE_SINSEI) {
                paramMdl.setRng030CmdMode(Rng030Form.CMDMODE_APPR);
            } else {
                paramMdl.setRng030CmdMode(Rng030Form.CMDMODE_VIEW);
            }

         }
    }
    /**
     *
     * <br>[機  能] 経路追加実行
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     * @throws EnumOutRangeException 列挙型範囲外
     */
    public void registAddKeiro(Rng030ParamModel paramMdl,
            MlCountMtController cntCon) throws SQLException, EnumOutRangeException {
        int rngSid = paramMdl.getRngSid();
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();

        //稟議申請情報のデータ使用量を登録(変更前情報のデータ使用量を減算)
        RngUsedDataBiz usedDataBiz = new RngUsedDataBiz(con__);
        usedDataBiz.insertSinseiDataSize(rngSid, false);

        Map<Integer, Rng020KeiroBlock> addKeiro = paramMdl.getRng030addKeiroMap();
        RingiDao ringiDao = new RingiDao(con__);
        RingiDataModel model = ringiDao.getRingiData(rngSid, paramMdl.getRng010ViewAccount(),
                paramMdl.getRng030RksSid());
        List<Rng030KeiroParam> keiroList = ringiDao.getKeiroList(rngSid);
        boolean sortInit = true;
        int sort = 0;
        boolean sortChange = false;
        RngKeiroStepDao rksDao = new RngKeiroStepDao(con__);
        Rng030KeiroAddBiz keiroAddBiz = null;
        keiroAddBiz = new Rng030KeiroAddBiz(
                con__, reqMdl__, model.getRtpSid(), model.getRtpVer(),
                paramMdl.getRng030template());
        int singedFlg = RngConst.RNG_RNCSTATUS_APPR;

        for (Rng030KeiroParam keiroParam : keiroList) {
            if (sortInit) {
                sortInit = false;
                sort = keiroParam.getKeiroSort();
            }
            if (singedFlg == RngConst.RNG_RNCSTATUS_CONFIRM) {
                singedFlg = RngConst.RNG_RNCSTATUS_NOSET;
            }
            if (keiroParam.getKeiroStatus() == RngConst.RNG_RNCSTATUS_CONFIRM) {
                singedFlg = RngConst.RNG_RNCSTATUS_CONFIRM;
            }
            int rksSid = keiroParam.getKeiroStepSid();
            keiroAddBiz.prefStepAddibleFlag(keiroParam, singedFlg);

            if (sortChange) {
                rksDao.updateKeiroSort(rngSid, rksSid, sort);
            }
            sort++;
            if (addKeiro.containsKey(rksSid)) {
                sortChange = true;
                Rng020KeiroBlock block = addKeiro.get(rksSid);
                Map<Integer, Rng110KeiroDialogParamModel> rowTempMap
                = keiroAddBiz.getAddRowTemplateMap();
                Rng110KeiroDialogParamModel rktModel = rowTempMap.get(rksSid);
                for (Entry<Integer, Rng020Keiro> entryKeiro:block.getKeiroMap().entrySet()) {
                    Rng020Keiro keiro = entryKeiro.getValue();
                    int addRksSid = (int) cntCon.getSaibanNumber(RngConst.SBNSID_RINGI,
                            RngConst.SBNSID_SUB_RINGI_KEIRO_STEP,
                            sessionUsrSid);

                    RngKeiroStepModel rksMdl = new RngKeiroStepModel();
                    rksMdl.setRngSid(rngSid);
                    rksMdl.setRksAuid(sessionUsrSid);
                    rksMdl.setRksAdate(new UDate());
                    rksMdl.setRksEuid(sessionUsrSid);
                    rksMdl.setRksEdate(new UDate());
                    rksMdl.setRksSid(addRksSid);
                    rksMdl.setRksRollType(RngConst.RNG_RNCTYPE_APPR);
                    rksMdl.setRksSort(sort);
                    rksMdl.setRtkSid(-1);
                    rksMdl.setRksRcvdate(new UDate());
                    rksMdl.setRksBelongSid(rktModel.getRtkSid());
                    rksMdl.setRksKeiroKoetu(rktModel.getKouetu());
                    rksMdl.setRksKoetuSizi(rktModel.getKouetuSiji());
                    rksMdl.setRksAddstep(keiroParam.getAddibleRtkSid());
                    rksMdl.setRksStatus(RngConst.RNG_RNCSTATUS_NOSET);
                    rksMdl.setRksRcvdate(null);
                    //経路ステップの登録
                    rksDao.insert(rksMdl);

                    RngKeirostepSelectDao selectDao = new RngKeirostepSelectDao(con__);
                    List<RngKeirostepSelectModel> selectList
                                    = keiro.createRngKeiroSelect(con__, addRksSid);

                    if (!selectList.isEmpty() && selectList.size() > 0) {
                        selectDao.insert(selectList);
                    }
                    __insertSingi(selectList, rksMdl, rngSid, addRksSid, sort);
                    sort++;
                }
            }
        }

        //稟議申請情報のデータ使用量を登録
        usedDataBiz.insertSinseiDataSize(rngSid, true);
    }

    /**
     * 審議情報の登録を行います。
     * @param selectList 経路ステップ選択情報モデル
     * @param rksMdl 経路ステップモデル
     * @param rngSid 稟議SID
     * @param rksSid 経路ステップSID
     * @param rncSort 経路ステップソート順
     * @return 登録審議数
     * @throws SQLException SQL実行例外
     * */
    private int __insertSingi(
            List<RngKeirostepSelectModel> selectList,
            RngKeiroStepModel rksMdl,
            int rngSid,
            int rksSid,
            int rncSort)
    throws SQLException {

        RngSingiDao rssDao = new RngSingiDao(con__);
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        UDate now = new UDate();
        int cnt = 0;

        //審議者ユーザSID一覧を取得(プラグイン使用不可ユーザは除外)
        RngBiz rngBiz = new RngBiz(con__);
        List<Integer> userSidList = rngBiz.getSingiUserList(selectList);
        if (userSidList == null || userSidList.size() <= 0) {
            return cnt;
        }
        //審議者情報を登録
        for (int usrSid : userSidList) {
            RngSingiModel rssMdl = new RngSingiModel();
            rssMdl.setRksSid(rksSid);
            rssMdl.setRngSid(rngSid);
            rssMdl.setRssAuid(sessionUsrSid);
            rssMdl.setRssAdate(now);
            rssMdl.setRssEuid(sessionUsrSid);
            rssMdl.setRssEdate(now);
            rssMdl.setRssStatus(RngConst.RNG_RNCSTATUS_NOSET);
            rssMdl.setUsrSid(usrSid);
            rssDao.insert(rssMdl);
            cnt++;
        }
        return cnt;
    }

    /**
     * <br>[機  能] 稟議の承認を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param userSid コンボボックス選択ユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig プラグイン情報
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void approvalRingi(Rng030ParamModel paramMdl,
                                MlCountMtController cntCon,
                                int userSid,
                                String appRootPath,
                                String tempDir,
                                PluginConfig pluginConfig,
                                boolean smailPluginUseFlg,
                                RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        //現在時刻
        UDate now = new UDate();
        int rngSid = paramMdl.getRngSid();

        //稟議申請情報のデータ使用量を登録(変更前情報のデータ使用量を減算)
        RngUsedDataBiz usedDataBiz = new RngUsedDataBiz(con__);
        usedDataBiz.insertSinseiDataSize(rngSid, false);

        //差し戻しされた場合状態が否認のため申請中に戻す
        RngRndataDao rngDao = new RngRndataDao(con__);
        RngRndataModel rngData = __createRndataModel(paramMdl, userSid, now);
        rngData.setRngStatus(RngConst.RNG_STATUS_REQUEST);
        rngDao.updateRingiStatus(rngData);

        //稟議経路情報の更新
        RngKeiroStepDao keiroDao = new RngKeiroStepDao(con__);
        RngSingiDao singiDao = new RngSingiDao(con__);
        RngSingiModel singiMdl = __createSingiModel(paramMdl, userSid, now);
        RngKeiroStepModel keirostepMdl = __createKeiroStepModel(paramMdl, userSid, now);

        int finishSortNo = 0;
        int startSortNo = 0;
        boolean smlSendFlg = true;
        //後閲可能ユーザの承認による後閲指示
        if (paramMdl.getRng030KoetuFlg() == 0) {
            //後閲指示を受けるユーザ
            singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_KOETU);
            singiMdl.setRssChkdate(now);
            keirostepMdl.setRksChkdate(now);
            keirostepMdl.setRksEdate(now);
            keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_KOETU);
            finishSortNo = keiroDao.getSortNoKeiro(paramMdl.getRng030RksSid(), rngSid);
            startSortNo = keiroDao.getSortNo(rngSid);
            keiroDao.updateKoetuKeiroStep(keirostepMdl, startSortNo, finishSortNo - 1);
            singiDao.updateKoetuSingi(singiMdl, startSortNo, finishSortNo - 1);

            //指示を出しているユーザの経路情報を変更する
            keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
            keirostepMdl.setRksRcvdate(now);
            keirostepMdl.setRksChkdate(null);
            keirostepMdl.setRksSid(paramMdl.getRng030RksSid());
            keiroDao.updateApprovalKeiroStep(keirostepMdl);
            singiMdl.setRssChkdate(null);
            singiMdl.setUsrSidKoetu(-1);
            singiMdl.setRksSid(paramMdl.getRng030RksSid());
            singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
            singiDao.updateApprovalSingi(singiMdl);
            smlSendFlg = false;

            //削除されているユーザの場合承認にする
            int sinkoFlg = keiroDao.getNoUser(paramMdl.getRng030RksSid());
            if (sinkoFlg == 0) {
                List<Integer> delList = keiroDao.getDelUser(paramMdl.getRng030RksSid());
                if (delList.size() > 0) {
                    singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_APPR);
                    singiDao.updateSingiDelUser(singiMdl, delList);
                }
            }
        }

        singiMdl.setRssComment(paramMdl.getRng030Comment());
        singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_APPR);
        singiMdl.setRssChkdate(now);

        //代理人ユーザによる承認
        if (paramMdl.getRng010DairiFlg() == 1) {
            singiMdl.setUsrSid(userSid);
            singiMdl.setUsrSidDairi(reqMdl.getSmodel().getUsrsid());
        }

        singiDao.updateSingi(singiMdl);

        //稟議添付情報の更新
        __updateRngBin(con__, cntCon, rngSid, userSid, appRootPath, tempDir, now,
                paramMdl.getRng030RksSid());

        RngDoNextBiz nextBiz = new RngDoNextBiz(con__, reqMdl, singiDao, keiroDao, rngSid);

        nextBiz.doNext(cntCon, paramMdl.getRng030RksSid(),
                appRootPath, pluginConfig, smailPluginUseFlg, userSid, 0, smlSendFlg);

        if (paramMdl.getRng030KoetuFlg() == 0) {
            int[] sort = new int[2];
            sort[0] = startSortNo;
            sort[1] = finishSortNo;
            RngBiz rngBiz = new RngBiz(con__, cntCon);
            rngBiz.getRingiListeners(pluginConfig);
            //リスナーに定義された稟議完了時の処理を行う
            RingiListenerModel listenerMdl =
                    rngBiz.createListenerModel(con__, cntCon, rngSid, appRootPath,
                            pluginConfig, smailPluginUseFlg);
            IRingiListener[] listenerList = rngBiz.getRingiListeners(pluginConfig);

            //URLを設定
            String url = rngBiz.createThreadUrl(reqMdl, rngData.getRngSid());
            listenerMdl.setRngUrl(url);
            listenerMdl.setUserSid(rngData.getRngApplicate());
            for (IRingiListener listener : listenerList) {
                listener.sendSmlMain(listenerMdl, reqMdl, RngConst.STATUS_SOURCE_KOETU_SML, sort);
            }
        }

        //稟議申請情報のデータ使用量を登録
        usedDataBiz.insertSinseiDataSize(rngSid, true);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 稟議の却下を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param userSid コンボボックス選択ユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig プラグイン情報
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void denialRingi(Rng030ParamModel paramMdl,
                            MlCountMtController cntCon,
                            int userSid,
                            String appRootPath,
                            String tempDir,
                            PluginConfig pluginConfig,
                            boolean smailPluginUseFlg,
                            RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        //現在時刻
        UDate now = new UDate();

        int rngSid = paramMdl.getRngSid();

        //稟議申請情報のデータ使用量を登録(変更前情報のデータ使用量を減算)
        RngUsedDataBiz usedDataBiz = new RngUsedDataBiz(con__);
        usedDataBiz.insertSinseiDataSize(rngSid, false);

        //稟議経路情報の更新

        RngKeiroStepDao keiroDao = new RngKeiroStepDao(con__);
        RngSingiDao singiDao = new RngSingiDao(con__);
        RngSingiModel singiMdl = __createSingiModel(paramMdl, userSid, now);
        RngKeiroStepModel keirostepMdl = __createKeiroStepModel(paramMdl, userSid, now);

        //後閲可能ユーザの承認による後閲指示
        if (paramMdl.getRng030KoetuFlg() == 0) {
            singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_KOETU);
            singiMdl.setRssChkdate(now);
            keirostepMdl.setRksChkdate(now);
            keirostepMdl.setRksEdate(now);
            keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_KOETU);
            int finishSortNo = keiroDao.getSortNoKeiro(paramMdl.getRng030RksSid(), rngSid);
            int startSortNo = keiroDao.getSortNo(rngSid);
            keiroDao.updateKoetuKeiroStep(keirostepMdl, startSortNo, finishSortNo - 1);
            singiDao.updateKoetuSingi(singiMdl, startSortNo, finishSortNo - 1);

            //指示を出しているユーザの経路情報を変更する
            keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
            keirostepMdl.setRksRcvdate(now);
            keirostepMdl.setRksChkdate(null);
            keirostepMdl.setRksSid(paramMdl.getRng030RksSid());
            keiroDao.updateApprovalKeiroStep(keirostepMdl);
            singiMdl.setRssChkdate(null);
            singiMdl.setUsrSidKoetu(-1);
            singiMdl.setRksSid(paramMdl.getRng030RksSid());
            singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
            singiDao.updateApprovalSingi(singiMdl);

            //削除されているユーザの場合承認にする
            int sinkoFlg = keiroDao.getNoUser(paramMdl.getRng030RksSid());
            if (sinkoFlg == 0) {
                List<Integer> delList = keiroDao.getDelUser(paramMdl.getRng030RksSid());
                if (delList.size() > 0) {
                    singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_APPR);
                    singiDao.updateSingiDelUser(singiMdl, delList);
                }
            }
        }

        singiMdl.setRssComment(paramMdl.getRng030Comment());
        singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_DENIAL);
        singiMdl.setRssChkdate(now);
        //代理人ユーザによる承認
        if (paramMdl.getRng010DairiFlg() == 1) {
            singiMdl.setUsrSid(userSid);
            singiMdl.setUsrSidDairi(reqMdl.getSmodel().getUsrsid());
        }

        singiDao.updateSingi(singiMdl);

        //稟議添付情報の更新
        __updateRngBin(con__, cntCon, rngSid, userSid, appRootPath, tempDir, now,
                paramMdl.getRng030RksSid());

        RngBiz rngBiz = new RngBiz(con__, cntCon);
        rngBiz.getRingiListeners(pluginConfig);

        //リスナーに定義された稟議完了時の処理を行う
        RingiListenerModel listenerMdl =
                rngBiz.createListenerModel(con__, cntCon, rngSid, appRootPath,
                        pluginConfig, smailPluginUseFlg);
        IRingiListener[] listenerList = rngBiz.getRingiListeners(pluginConfig);


        // 0:未達成 1:承認達成 2:否認達成
        int nTassei = keiroDao.checkApproval(keirostepMdl, RngConst.RNG_RNCSTATUS_DENIAL);
        if (nTassei == 2) {
            keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_DENIAL);
            keirostepMdl.setRksChkdate(now);
            keirostepMdl.setRksEdate(now);
            keiroDao.updateKeiroStep(keirostepMdl);
            //稟議を却下状態で完了する
            RngRndataDao rngDao = new RngRndataDao(con__);
            RngRndataModel rngData = __createRndataModel(paramMdl, userSid, now);
            rngData.setRngStatus(RngConst.RNG_STATUS_REJECT);
            rngData.setRngAdmcomment(null);
            rngDao.completeRingi(rngData, true);

            //最終確認者の受信日を更新する
            //keiroDao.updateRcvdateForConfirmUser(rngSid, userSid, now);

            String url = rngBiz.createThreadUrl(reqMdl, rngData.getRngSid());
            //URLを設定
            listenerMdl.setRngUrl(url);
            listenerMdl.setUserSid(rngData.getRngApplicate());
            for (IRingiListener listener : listenerList) {
                listener.sendSmlMain(listenerMdl, reqMdl, RngConst.STATUS_SOURCE_REJECT_SML, null);
            }

        } else if (nTassei == 1) {
            RngDoNextBiz nextBiz = new RngDoNextBiz(con__, reqMdl, singiDao, keiroDao, rngSid);

            nextBiz.doNext(cntCon, keirostepMdl.getRksSid(),
                    appRootPath, pluginConfig, smailPluginUseFlg, userSid, 0, true);
        }

        //稟議申請情報のデータ使用量を登録
        usedDataBiz.insertSinseiDataSize(rngSid, true);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 稟議の差し戻しを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param userSid コンボボックス選択ユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig プラグイン情報
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエスト情報
     * @return 差し戻し先経路SID
     * @throws Exception 実行例外
     */
    public int reflectionRingi(Rng030ParamModel paramMdl,
                                MlCountMtController cntCon,
                                int userSid,
                                String appRootPath,
                                String tempDir,
                                PluginConfig pluginConfig,
                                boolean smailPluginUseFlg,
                                RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        //現在時刻
        UDate now = new UDate();

        int rngSid = paramMdl.getRngSid();

        //稟議申請情報のデータ使用量を登録(変更前情報のデータ使用量を減算)
        RngUsedDataBiz usedDataBiz = new RngUsedDataBiz(con__);
        usedDataBiz.insertSinseiDataSize(rngSid, false);

        //稟議経路情報の更新
        RngKeiroStepDao keiroDao = new RngKeiroStepDao(con__);
        RngSingiDao singiDao = new RngSingiDao(con__);
        RngSingiModel singiMdl = __createSingiModel(paramMdl, userSid, now);
        singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_DENIAL);
        singiMdl.setRssComment(paramMdl.getRng030Comment());
        singiMdl.setRssChkdate(now);
        singiMdl.setRssEdate(now);
        //代理人ユーザによる承認
        if (paramMdl.getRng010DairiFlg() == 1) {
            singiMdl.setUsrSidDairi(reqMdl.getSmodel().getUsrsid());
        }
        int nNowSort = keiroDao.getSortNo(rngSid);
        singiDao.updateSingi(singiMdl);

        //稟議添付情報の更新
        __updateRngBin(con__, cntCon, rngSid, userSid, appRootPath, tempDir, now,
                paramMdl.getRng030RksSid());

        RngKeiroStepModel keirostepMdl = __createKeiroStepModel(paramMdl, userSid, now);
        keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_DENIAL);
        keirostepMdl.setRksChkdate(now);
        keirostepMdl.setRksEdate(now);
        keiroDao.updateKeiroStep(keirostepMdl);

        RngRndataDao rngDao = new RngRndataDao(con__);
        RngRndataModel rngData = __createRndataModel(paramMdl, userSid, now);
        int sortNo;
        //申請者への差し戻しの場合経路ステップSIDが-1
        if (paramMdl.getRng030SasiNo() == -1) {
            sortNo = 0;
        } else {
            sortNo = keiroDao.getSortNoKeiro(paramMdl.getRng030SasiNo(), rngSid);
        }
        rngData.setRngStatus(RngConst.RNG_STATUS_REJECT);
        rngDao.updateRingiStatus(rngData);

        //次に確認するユーザの経路情報を更新
        int beforeRksSid = keiroDao.getSortRksSid(sortNo, rngSid);
        keirostepMdl.setRksSid(beforeRksSid);
        keirostepMdl.setRksRcvdate(now);
        keirostepMdl.setRksRcvdate(now);
        keirostepMdl.setRksChkdate(null);
        keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
        keiroDao.updateApprovalKeiroStep(keirostepMdl);
        singiMdl.setRssChkdate(null);
        singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
        singiMdl.setRksSid(beforeRksSid);
        if (sortNo == 0) {
            //申請者の経路情報を更新する
            int usrSid = singiDao.selectUserSid(keirostepMdl.getRngSid());
            singiMdl.setUsrSid(usrSid);
            singiDao.updateSingi(singiMdl);
        } else {
            //選択した承認者の稟議経路情報を更新する
            singiMdl.setRksSid(beforeRksSid);
            singiMdl.setUsrSidDairi(-1);
            singiMdl.setUsrSidKoetu(-1);
            singiDao.updateSingiNotDelUser(singiMdl);
        }
        RngBiz rngBiz = new RngBiz(con__);
        rngBiz.getRingiListeners(pluginConfig);

        //稟議申請情報のデータ使用量を登録
        usedDataBiz.insertSinseiDataSize(rngSid, true);

        //ショートメール通知
        RingiListenerModel listenerMdl =
                rngBiz.createListenerModel(con__, cntCon, rngSid, appRootPath,
                        pluginConfig, smailPluginUseFlg);
        IRingiListener[] listenerList = rngBiz.getRingiListeners(pluginConfig);
        listenerMdl.setUserSid(rngData.getRngApplicate());
        //稟議承認(完了)通知を行う
        String url = rngBiz.createThreadUrl(reqMdl, rngData.getRngSid());
        //URLを設定
        listenerMdl.setRngUrl(url);
        listenerMdl.setUserSid(rngData.getRngApplicate());
        int[] sort = new int[2];
        sort[0] = sortNo;
        sort[1] = nNowSort;

        for (IRingiListener listener : listenerList) {
            listener.sendSmlMain(listenerMdl, reqMdl, RngConst.STATUS_SOURCE_REMAND_SML, sort);
        }

        log__.debug("End");
        return beforeRksSid;
    }

    /**
     * <br>[機  能] 稟議の完了を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param appRootPath アプリケーションのルートパス
     * @param userSid コンボボックス選択ユーザSID
     * @param pluginConfig プラグイン情報
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエストモデル
     * @throws Exception 実行例外
     */
    public void completeRingi(Rng030ParamModel paramMdl, Connection con, int userSid,
                            MlCountMtController cntCon,
                            String appRootPath,
                            PluginConfig pluginConfig,
                            boolean smailPluginUseFlg,
                            RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        //現在時刻
        UDate now = new UDate();

        //稟議を完了状態にする
        RngRndataDao rngDao = new RngRndataDao(con);
        RngRndataModel rngData = __createRndataModel(paramMdl, userSid, now);
        rngDao.completeRingi(rngData, false);

        RngBiz rngBiz = new RngBiz(con__, cntCon);
        rngBiz.getRingiListeners(pluginConfig);

        //リスナーに定義された稟議完了時の処理を行う
        int rngSid = paramMdl.getRngSid();
        RingiListenerModel listenerMdl =
                rngBiz.createListenerModel(con__, cntCon, rngSid, appRootPath,
                        pluginConfig, smailPluginUseFlg);
        IRingiListener[] listenerList = rngBiz.getRingiListeners(pluginConfig);


        //URLを設定
        String url = rngBiz.createThreadUrl(reqMdl, rngData.getRngSid());
        listenerMdl.setRngUrl(url);
        listenerMdl.setUserSid(rngData.getRngApplicate());
        for (IRingiListener listener : listenerList) {
            listener.sendSmlMain(listenerMdl, reqMdl, RngConst.STATUS_SOURCE_REJECT_SML, null);
        }

        log__.debug("End");
    }


    /**
     * <br>[機  能] 稟議の確認を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid コンボボックス選択ユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 実行例外
     */
    public void confirmationRingi(Rng030ParamModel paramMdl,
                                Connection con,
                                MlCountMtController cntCon,
                                int userSid,
                                String appRootPath,
                                String tempDir)
    throws Exception {
        log__.debug("START");

        //現在時刻
        UDate now = new UDate();

        //稟議経路情報の更新

        RngSingiDao singiDao = new RngSingiDao(con);
        RngSingiModel singiMdl = __createSingiModel(paramMdl, userSid, now);
        singiMdl.setRssComment(paramMdl.getRng030Comment());
        singiMdl.setRssChkdate(now);
        singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_CONFIRMATION);
        if (paramMdl.getRng010DairiFlg() == 1) {
            singiMdl.setUsrSidDairi(reqMdl__.getSmodel().getUsrsid());
        }
        singiDao.updateConfirm(singiMdl);

        RngKeiroStepDao keiroDao = new RngKeiroStepDao(con);
        RngKeiroStepModel keiroMdl = __createKeiroStepModel(paramMdl, userSid, now);
        List<Integer> rksList = singiDao.checkConfirm(paramMdl.getRngSid(), userSid);
        if (rksList.size() > 0) {
            keiroMdl.setRksChkdate(now);
            keiroMdl.setRksStatus(RngConst.RNG_RNCSTATUS_CONFIRMATION);
            keiroDao.updateConfirm(keiroMdl, rksList);
        }

        //稟議添付情報の更新
        __updateRngBin(con, cntCon, paramMdl.getRngSid(), userSid, appRootPath, tempDir, now,
                paramMdl.getRng030RksSid());

        log__.debug("End");
    }

    /**
     * <br>[機  能] 稟議の強制完了を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param appRootPath アプリケーションのルートパス
     * @param userSid コンボボックス選択ユーザSID
     * @param pluginConfig プラグイン情報
     * @throws Exception 実行例外
     */
    public void compelCompleteRingi(Rng030ParamModel paramMdl, Connection con, int userSid,
                                    MlCountMtController cntCon, String appRootPath,
                                    PluginConfig pluginConfig)
    throws Exception {
        log__.debug("START");

        //現在時刻
        UDate now = new UDate();

        //稟議を完了状態にする
        RngRndataDao rngDao = new RngRndataDao(con);
        RngRndataModel rngData = __createRndataModel(paramMdl, userSid, now);
        rngData.setRngAdmcomment(paramMdl.getRng030Comment());
        rngData.setRngStatus(RngConst.RNG_STATUS_DONE);
        rngDao.completeRingi(rngData, true);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 稟議の強制削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid コンボボックス選択ユーザSID
     * @throws Exception 実行例外
     */
    public void compelDeleteRingi(Rng030ParamModel paramMdl, Connection con, int userSid)
    throws Exception {
        log__.debug("START");

        int rngSid = paramMdl.getRngSid();

        RngBiz rngBiz = new RngBiz(con);

        rngBiz.deleteRngData(con, rngSid, userSid);

        log__.debug("End");
    }

    /**
     * <br>[機  能] 稟議の取り下げを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param userSid コンボボックス選択ユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param pluginConfig プラグイン情報
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエストモデル
     * @throws Exception 実行例外
     */
    public void torisage(Rng030ParamModel paramMdl,
            MlCountMtController cntCon,
            int userSid,
            String appRootPath,
            PluginConfig pluginConfig,
            boolean smailPluginUseFlg,
            RequestModel reqMdl)

    throws Exception {
        log__.debug("START");

        int rngSid = paramMdl.getRngSid();
        UDate now = new UDate();

        //稟議情報の取り下げ
        RngRndataDao rngDao = new RngRndataDao(con__);
        rngDao.updateTorisage(rngSid, userSid, now);

        RngKeiroStepDao kDao = new RngKeiroStepDao(con__);
        int rksSid = kDao.getSortRksSid(0, rngSid);
        RngSingiModel sMdl = __createSingiModel(paramMdl, userSid, now);
        sMdl.setRksSid(rksSid);
        sMdl.setUsrSidKoetu(-1);
        sMdl.setRssChkdate(now);
        sMdl.setRssStatus(RngConst.RNG_RNCSTATUS_NOSET);
        RngSingiDao sDao = new RngSingiDao(con__);
        sDao.updateApprovalSingi(sMdl);

        RngBiz rngBiz = new RngBiz(con__, cntCon);
        rngBiz.getRingiListeners(pluginConfig);

        //リスナーに定義された稟議完了時の処理を行う
        RingiListenerModel listenerMdl =
                rngBiz.createListenerModel(con__, cntCon, rngSid, appRootPath,
                        pluginConfig, smailPluginUseFlg);
        IRingiListener[] listenerList = rngBiz.getRingiListeners(pluginConfig);

        RngRndataModel rngData = __createRndataModel(paramMdl, userSid, now);

        //URLを設定
        String url = rngBiz.createThreadUrl(reqMdl, rngData.getRngSid());
        listenerMdl.setRngUrl(url);
        listenerMdl.setUserSid(rngData.getRngApplicate());
        for (IRingiListener listener : listenerList) {
            listener.sendSmlMain(listenerMdl, reqMdl, RngConst.STATUS_SOURCE_TORISAGE_SML, null);
        }


        log__.debug("End");
    }

    /**
     * <br>[機  能] 稟議の後閲指示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param userSid コンボボックス選択ユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig プラグイン情報
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエスト情報
     * @return 後閲指示対象
     * @throws Exception 実行例外
     */
    public int koetu(Rng030ParamModel paramMdl,
            MlCountMtController cntCon,
            int userSid,
            String appRootPath,
            String tempDir,
            PluginConfig pluginConfig,
            boolean smailPluginUseFlg,
            RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        int rngSid = paramMdl.getRngSid();
        UDate now = new UDate();

        //差し戻しされた場合状態が否認のため申請中に戻す
        RngRndataDao rngDao = new RngRndataDao(con__);
        RngRndataModel rngData = __createRndataModel(paramMdl, userSid, now);
        rngData.setRngStatus(RngConst.RNG_STATUS_REQUEST);
        rngDao.updateRingiStatus(rngData);

        //稟議経路情報の更新
        RngKeiroStepDao keiroDao = new RngKeiroStepDao(con__);
        RngSingiDao singiDao = new RngSingiDao(con__);
        RngSingiModel singiMdl = __createSingiModel(paramMdl, userSid, now);
        RngKeiroStepModel keirostepMdl = __createKeiroStepModel(paramMdl, userSid, now);
        int startSortNo = keiroDao.getSortNo(rngSid);
        int sortNo = keiroDao.getSortNoKeiro(paramMdl.getRng030koetuNo(), rngSid);
        singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_KOETU);
        singiMdl.setRssChkdate(now);
        keirostepMdl.setRksChkdate(now);
        keirostepMdl.setRksEdate(now);
        keirostepMdl.setRksStatus(RngConst.RNG_RNCSTATUS_KOETU);

        int selectRksSid = keiroDao.getSortRksSid(sortNo, rngSid);
        keiroDao.updateKoetuKeiroStep(keirostepMdl, startSortNo, sortNo);
        singiDao.updateKoetuSingi(singiMdl, startSortNo, sortNo);

        int[] sort = new int[2];
        sort[0] = startSortNo;
        sort[1] = sortNo;

        // 次の承認者の稟議経路情報を更新する
        RngDoNextBiz nextBiz = new RngDoNextBiz(con__, reqMdl, singiDao, keiroDao, rngSid);

        nextBiz.doNextKoetu(cntCon, selectRksSid,
                appRootPath, pluginConfig, smailPluginUseFlg, userSid);

        RngBiz rngBiz = new RngBiz(con__, cntCon);
        rngBiz.getRingiListeners(pluginConfig);
        //リスナーに定義された稟議完了時の処理を行う
        RingiListenerModel listenerMdl =
                rngBiz.createListenerModel(con__, cntCon, rngSid, appRootPath,
                        pluginConfig, smailPluginUseFlg);
        IRingiListener[] listenerList = rngBiz.getRingiListeners(pluginConfig);

        //URLを設定
        String url = rngBiz.createThreadUrl(reqMdl, rngData.getRngSid());
        listenerMdl.setRngUrl(url);
        listenerMdl.setUserSid(rngData.getRngApplicate());
        for (IRingiListener listener : listenerList) {
            listener.sendSmlMain(listenerMdl, reqMdl, RngConst.STATUS_SOURCE_KOETU_SML, sort);
        }
        log__.debug("End");
        return selectRksSid;
    }

    /**
     * <br>[機  能] 稟議のコメント編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param rngSid 稟議SID
     * @param userSid コンボボックス選択ユーザSID
     * @param rksSid 経路SID
     * @param appRootPath appRootPath
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 実行例外
     * @return 出力するログ情報一覧
     */
    public ArrayList<String> edit(Rng030ParamModel paramMdl,
            MlCountMtController cntCon,
            int rngSid,
            int userSid,
            int rksSid,
            String appRootPath,
            String tempDir)
    throws Exception {

        log__.debug("START");

        //稟議申請情報のデータ使用量を登録(変更前情報のデータ使用量を減算)
        RngUsedDataBiz usedDataBiz = new RngUsedDataBiz(con__);
        usedDataBiz.insertSinseiDataSize(rngSid, false);

        RngSingiDao singiDao = new RngSingiDao(con__);
        UDate now = new UDate();
        String comment = paramMdl.getRng030Comment().trim();
        singiDao.commentEdit(comment, paramMdl.getRng030RksSid(), userSid);
        List<String> binSidList = __updateRngBin(con__, cntCon, rngSid, userSid,
                                                 appRootPath, tempDir, now,
                                                 paramMdl.getRng030RksSid());

        ArrayList<String> ret = new ArrayList<String>();
        ret.add(comment);
        if (binSidList.size() > 0) {
            CmnBinfDao binDao = new CmnBinfDao(con__);
            List<CmnBinfModel> binList = binDao.select(
                    binSidList.toArray(new String[binSidList.size()]));
            for (CmnBinfModel binMdl : binList) {
                ret.add(binMdl.getBinFileName());
            }
        }

        //稟議申請情報のデータ使用量を登録
        usedDataBiz.insertSinseiDataSize(rngSid, true);

        log__.debug("End");

        return ret;
    }


    /**
     * <br>[機  能] 稟議のスキップを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param appRootPath アプリケーションのルートパス
     * @param userSid コンボボックス選択ユーザSID
     * @param pluginConfig プラグイン情報
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエストモデル
     * @return スキップされた経路SID
     * @throws Exception 実行例外
     */
    public int skipRingi(Rng030ParamModel paramMdl, Connection con, int userSid,
            MlCountMtController cntCon,
            String appRootPath,
            PluginConfig pluginConfig,
            boolean smailPluginUseFlg,
            RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        int rngSid = paramMdl.getRngSid();
        UDate now = new UDate();

        RngKeiroStepDao keiroDao = new RngKeiroStepDao(con);
        int confirmRksSid = keiroDao.getApprovalKeiro(rngSid);

        //差し戻しされた場合状態が否認のため申請中に戻す
        RngRndataDao rngDao = new RngRndataDao(con__);
        RngRndataModel rngData = __createRndataModel(paramMdl, userSid, now);
        rngData.setRngStatus(RngConst.RNG_STATUS_REQUEST);
        rngDao.updateRingiStatus(rngData);

        //稟議経路情報の更新
        RngKeiroStepModel keiroMdl = __createKeiroStepModel(paramMdl, userSid, now);
        keiroMdl.setRksSid(confirmRksSid);
        keiroMdl.setRksStatus(RngConst.RNG_RNCSTATUS_SKIP);
        keiroMdl.setRksChkdate(now);
        keiroDao.updateKeiroStep(keiroMdl);
        RngSingiDao singiDao = new RngSingiDao(con);
        RngSingiModel singiMdl = __createSingiModel(paramMdl, -1, now);
        singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_SKIP);
        singiMdl.setRksSid(confirmRksSid);
        singiMdl.setRssComment(paramMdl.getRng030Comment());
        singiMdl.setRssChkdate(now);
        singiDao.updateApprovalSingi(singiMdl);

        //次の承認者の稟議経路情報を更新する
        int nextRksSid = keiroDao.lastAuthorizer(keiroMdl, 1);
        if (nextRksSid > 0) {
            keiroMdl.setRksSid(nextRksSid);
            keiroMdl.setRksStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
            keiroMdl.setRksRcvdate(now);
            keiroMdl.setRksChkdate(null);
            keiroDao.updateApprovalKeiroStep(keiroMdl);
            singiMdl.setRksSid(nextRksSid);
            singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
            singiMdl.setRssChkdate(null);
            singiDao.updateApprovalSingi(singiMdl);
        } else {
            //次の承認者が存在しない場合、稟議を完了する
            rngData.setRngSid(rngSid);
            rngData.setRngAuid(userSid);
            rngData.setRngAdate(now);
            rngData.setRngEuid(userSid);
            rngData.setRngEdate(now);
            rngDao.completeRingi(rngData, false);

            //最終確認者の受信日を更新する
            keiroDao.updateRcvdateForConfirmUser(rngData.getRngSid(), userSid, now);
        }
        RngBiz rngBiz = new RngBiz(con__, cntCon);
        rngBiz.getRingiListeners(pluginConfig);
        //リスナーに定義された稟議完了時の処理を行う
        RingiListenerModel listenerMdl =
                rngBiz.createListenerModel(con__, cntCon, rngSid, appRootPath,
                        pluginConfig, smailPluginUseFlg);
        IRingiListener[] listenerList = rngBiz.getRingiListeners(pluginConfig);

        //URLを設定
        String url = rngBiz.createThreadUrl(reqMdl, rngData.getRngSid());
        listenerMdl.setRngUrl(url);
        listenerMdl.setUserSid(rngData.getRngApplicate());
        for (IRingiListener listener : listenerList) {
            listener.sendSmlMain(listenerMdl, reqMdl, RngConst.STATUS_SOURCE_APPLY_SML);
        }

        log__.debug("End");
        return confirmRksSid;
    }


    /**
     * <br>[機  能] 稟議の再申請を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param userSid コンボボックス選択ユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig プラグイン情報
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエストモデル
     * @throws Exception 実行例外
     */
    public void applicateRingi(Rng030ParamModel paramMdl,
                                MlCountMtController cntCon,
                                int userSid,
                                String appRootPath,
                                String tempDir,
                                PluginConfig pluginConfig,
                                boolean smailPluginUseFlg,
                                RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        int rngSid = paramMdl.getRngSid();
        UDate now = new UDate();
        RngRndataDao rngDao = new RngRndataDao(con__);

        //稟議申請情報のデータ使用量を登録(変更前情報のデータ使用量を減算)
        RngUsedDataBiz usedDataBiz = new RngUsedDataBiz(con__);
        usedDataBiz.insertSinseiDataSize(rngSid, false);
        
        //稟議情報の更新
        RngRndataModel rngMdl = rngDao.select(rngSid);
        rngMdl.setRngTitle(paramMdl.getRng030Title());
        rngMdl.setRngMakedate(now);
        rngMdl.setRngApplicate(userSid);
        rngMdl.setRngAppldate(now);
        rngMdl.setRngStatus(RngConst.RNG_STATUS_REQUEST);
        rngMdl.setRngCompflg(0);
        rngMdl.setRngAdmcomment(null);
        rngMdl.setRngEuid(userSid);
        rngMdl.setRngEdate(now);
        rngDao.update(rngMdl);

        //フォーム情報の登録
        RngBiz rngBiz = new RngBiz(con__, cntCon);
        RingiRequestModel rngReqMdl = new RingiRequestModel(); // フォーム情報へ渡すのに必要な情報をセット
        rngReqMdl.setRngSid(rngSid);
        rngReqMdl.setAppRootPath(appRootPath);
        rngReqMdl.setTempDir(tempDir);
        rngReqMdl.setUserSid(userSid);
        rngReqMdl.setDate(new UDate());
        rngBiz.entryFormData(rngReqMdl, paramMdl.getRng030template());

        //稟議経路情報の更新
        RngKeiroStepDao keiroDao = new RngKeiroStepDao(con__);
        RngKeiroStepModel keiroMdl = __createKeiroStepModel(paramMdl, userSid, now);
        keiroMdl.setRksStatus(RngConst.RNG_RNCSTATUS_NOSET);
        keiroMdl.setRksChkdate(null);
        keiroDao.updateKeiroStep(keiroMdl);

        RngSingiDao singiDao = new RngSingiDao(con__);
        RngSingiModel singiMdl = __createSingiModel(paramMdl, userSid, now);
        singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_NOSET);
        singiMdl.setRssComment(null);
        singiMdl.setRssChkdate(null);
        singiDao.updateSingi(singiMdl);


        //削除されたユーザを除外する
        int delCnt = keiroDao.deleteKeiroStepForDelUser(rngSid);
        if (delCnt > 0) {
            List<Integer> apprKeiroList = keiroDao.getApprUserList(rngSid);
            int sort = 1;
            for (int apprKeiroSid : apprKeiroList) {
                keiroDao.updateKeiroSort(rngSid, apprKeiroSid, sort);
                sort++;
            }
        }

        int sortNo = 1;
        //最初の承認者の稟議経路情報を更新する
        keiroMdl.setRksStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
        keiroMdl.setRksRcvdate(now);
        keiroMdl.setRksChkdate(null);
        keiroDao.updateSortKeiroStep(keiroMdl, sortNo);
        singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_CONFIRM);
        singiMdl.setRssChkdate(null);
        singiDao.updateSortSingi(singiMdl, sortNo);

        rngBiz.getRingiListeners(pluginConfig);
        //リスナーに定義された稟議完了時の処理を行う
        RingiListenerModel listenerMdl =
                rngBiz.createListenerModel(con__, cntCon, rngSid, appRootPath,
                        pluginConfig, smailPluginUseFlg);
        IRingiListener[] listenerList = rngBiz.getRingiListeners(pluginConfig);

        RngRndataModel rngData = __createRndataModel(paramMdl, userSid, now);
        //URLを設定
        String url = rngBiz.createThreadUrl(reqMdl, rngData.getRngSid());
        listenerMdl.setRngUrl(url);
        listenerMdl.setUserSid(rngData.getRngApplicate());
        for (IRingiListener listener : listenerList) {
            listener.sendSmlMain(listenerMdl, reqMdl, RngConst.STATUS_SOURCE_APPLY_SML);
        }
        usedDataBiz.insertSinseiDataSize(rngSid, true);
        log__.debug("End");
    }

    /**
     * <br>[機  能] 稟議添付情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param rngSid 稟議SID
     * @param userSid コンボボックス選択ユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param now 現在日時
     * @param rksSid 経路SID
     * @throws SQLException SQL実行時例外
     * @throws IOException 添付ファイルの保存に失敗
     * @throws IOToolsException 添付ファイルの保存に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @return 保存したバイナリSID一覧
     */
    private List<String> __updateRngBin(Connection con, MlCountMtController cntCon,
                                int rngSid, int userSid,
                                String appRootPath, String tempDir,
                                UDate now, int rksSid)
    throws SQLException, IOException, IOToolsException, TempFileException {

        //稟議添付情報の登録
        CommonBiz cmnBiz = new CommonBiz();
        RngBinDao binDao = new RngBinDao(con);

        //更新の場合はバイナリー情報の論理削除、稟議添付情報の削除を行う
        RingiDao ringiDao = new RingiDao(con);
        ringiDao.removeRngBinData(rksSid, userSid, userSid, now);
        binDao.delete(rngSid, userSid, rksSid);

        //バイナリー情報の登録
        List < String > binSidList = cmnBiz.insertBinInfo(con, tempDir, appRootPath,
                                                        cntCon, userSid, now);

        //稟議添付情報の登録
        if (binSidList != null && !binSidList.isEmpty()) {
            RngBinModel binMdl = new RngBinModel();
            binMdl.setRngSid(rngSid);
            binMdl.setUsrSid(userSid);
            binMdl.setRksSid(rksSid);

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
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param now 現在日時
     * @return 稟議情報Model
     */
    private RngRndataModel __createRndataModel(Rng030ParamModel paramMdl, int userSid, UDate now) {
        RngRndataModel rngMdl = new RngRndataModel();
        rngMdl.setRngSid(paramMdl.getRngSid());

        rngMdl.setRngAuid(userSid);
        rngMdl.setRngAdate(now);
        rngMdl.setRngEuid(userSid);
        rngMdl.setRngEdate(now);

        return rngMdl;
    }

    /**
     * <br>[機  能] 稟議経路情報Modelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param now 現在日時
     * @return 稟議経路情報Model
     */
    private RngSingiModel __createSingiModel(Rng030ParamModel paramMdl,
                                                int userSid, UDate now) {
        RngSingiModel singiMdl = new RngSingiModel();
        singiMdl.setRksSid(paramMdl.getRng030RksSid());
        singiMdl.setRngSid(paramMdl.getRngSid());
        singiMdl.setRssStatus(RngConst.RNG_RNCSTATUS_APPR);
        singiMdl.setUsrSid(userSid);
        if (paramMdl.getRng030KoetuFlg() == 0) {
            singiMdl.setUsrSidKoetu(userSid);
        }
        if (paramMdl.getRng010DairiFlg() == 0) {
            singiMdl.setUsrSidDairi(-1);
        }
        singiMdl.setRssAuid(userSid);
        singiMdl.setRssAdate(now);
        singiMdl.setRssEuid(userSid);
        singiMdl.setRssEdate(now);

        return singiMdl;
    }

    /**
     * <br>[機  能] 稟議経路ステップ情報Modelを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param now 現在日時
     * @return 稟議経路情報Model
     */
    private RngKeiroStepModel __createKeiroStepModel(Rng030ParamModel paramMdl,
                                                int userSid, UDate now) {
        RngKeiroStepModel keiroMdl = new RngKeiroStepModel();
        keiroMdl.setRksSid(paramMdl.getRng030RksSid());
        keiroMdl.setRngSid(paramMdl.getRngSid());
        keiroMdl.setRksAuid(userSid);
        keiroMdl.setRksAdate(now);
        keiroMdl.setRksEuid(userSid);
        keiroMdl.setRksEdate(now);

        return keiroMdl;
    }

    /**
     * <br>[機  能] 処理モードにより遷移先を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param map マッピング
     * @param paramMdl パラメータ情報
     * @return ActionForward
     */
    public ActionForward getForward(ActionMapping map, Rng030ParamModel paramMdl) {

        ActionForward forward = null;

        if (paramMdl.getRng130searchFlg() == 1) {
            return map.findForward("search");
        }

        if (paramMdl.getRngApprMode() == RngConst.RNG_APPRMODE_DISCUSSING) {
            //申請中案件管理画面へ遷移
            forward = map.findForward("rng050");
        } else if (paramMdl.getRngApprMode() == RngConst.RNG_APPRMODE_COMPLETE) {
            //完了案件管理画面へ遷移
            forward = map.findForward("rng070");
        } else {
            if (paramMdl.getRngDspMode() == RngConst.RNG_MODE_MAIN) {
                //メインへ遷移
                forward = map.findForward("gf_main");
            } else {
                //稟議一覧画面へ遷移
                forward = map.findForward("rng010");
            }
        }
        return forward;
    }

    /**
     *
     * <br>[機  能]受信一覧に指定した稟議が存在するかを判定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータModel
     * @param ringiDao 稟議プラグインで使用するDAOクラス
     * @param userSid ユーザSID
     * @param rngSid 稟議SID
     * @throws SQLException SQL実行時例外
     * @return 稟議が受信されているか
     */
    public boolean rngReceptionConf(
            Rng030ParamModel paramMdl,
            RingiDao ringiDao,
            int userSid,
            int rngSid) throws SQLException {

        //稟議が受信されているか判定
        List <RingiDataModel> rngModelList = ringiDao.getProgressRingiSidList(userSid);

        for (RingiDataModel rngModelData:rngModelList) {
            if (rngSid == rngModelData.getRngSid()) {
                return true;
            }
        }
        //後閲ユーザか判定を行う
        if (ringiDao.getKoetuUserCheck(paramMdl.getRng030RksSid())) {
            return true;
        }
        RngDairiUserDao dairiDao = new RngDairiUserDao(con__);
        RngDairiUserModel rduMdl = new RngDairiUserModel();
        int usrSid = paramMdl.getRng010ViewAccount();
        if (userSid == usrSid) {
            return true;
        }
        //正規代理承認か
        rduMdl = dairiDao.select(usrSid, reqMdl__.getSmodel().getUsrsid());
        if (!(rduMdl == null)) {
            UDate now = new UDate();
            if (rduMdl.getRduEnd() != null) {
                if (!now.betweenYMDHM(rduMdl.getRduStart(), rduMdl.getRduEnd())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    /**
    *
    * <br>[機  能]完了ボタンの実行判定を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param paramMdl パラメータモデル
    * @param userSid ユーザSID
    * @throws SQLException SQL実行時例外
    * @return ボタンの実行可能判定
    */
   public boolean completeBtnConf(Connection con,
           Rng030ParamModel paramMdl,
           int userSid) throws SQLException {
       if (!appRejBtnConf(con, paramMdl, userSid)) {
           return false;
       }
       RngKeiroStepDao keiroDao = new RngKeiroStepDao(con__);
       int fromSortNo =  keiroDao.getSortNo(paramMdl.getRngSid());
       int toSortNo = keiroDao.getSortNoKeiro(
               paramMdl.getRng030SasiNo(), paramMdl.getRngSid());
       if (fromSortNo <= toSortNo) {
           return false;
       }
       return true;
   }

    /**
    *
    * <br>[機  能]差し戻しの実行判定を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param paramMdl パラメータモデル
    * @param userSid ユーザSID
    * @throws SQLException SQL実行時例外
    * @return ボタンの実行可能判定
    */
   public boolean reflectionBtnConf(Connection con,
           Rng030ParamModel paramMdl,
           int userSid) throws SQLException {
       if (!appRejBtnConf(con, paramMdl, userSid)) {
           return false;
       }
       RngKeiroStepDao keiroDao = new RngKeiroStepDao(con__);
       int fromSortNo =  keiroDao.getSortNo(paramMdl.getRngSid());
       int toSortNo = keiroDao.getSortNoKeiro(
               paramMdl.getRng030SasiNo(), paramMdl.getRngSid());
       if (fromSortNo <= toSortNo) {
           return false;
       }

       int sasiRksSid = paramMdl.getRng030SasiNo();
       if (sasiRksSid == -1) {
           sasiRksSid = keiroDao.getSortRksSid(0, paramMdl.getRngSid());
       }
       RngSingiDao singiDao = new RngSingiDao(con__);
       if (singiDao.keiroUserDeleteCount(sasiRksSid) == 0) {
           return false;
       }

       return true;
   }
    /**
     *
     * <br>[機  能]承認、却下、差し戻しの実行判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @return ボタンの実行可能判定
     */
    public boolean appRejBtnConf(Connection con,
            Rng030ParamModel paramMdl,
            int userSid) throws SQLException {
        RingiDao ringiDao = new RingiDao(con);
        RingiDataModel rngModel = ringiDao.getRingiData(paramMdl.getRngSid(), userSid,
                paramMdl.getRng030RksSid());
        //完了済みか判定
        if (rngModel.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE) {
            return false;
        }
        //稟議が削除済みか確認
        if (rngModel.getRngTitle() == null) {
            return false;
        }
        //稟議SID、作業経路SID、審議者SIDの組み合わせが不正でないことを確認
        if (!ringiDao.chkRngParams(paramMdl.getRngSid(), paramMdl.getRng030RksSid(), userSid)) {
            return false;
        }
        //確認中かどうか
        if (rngModel.getRssStatus() == RngConst.RNG_RNCSTATUS_CONFIRM) {
            return true;
        }
        //後閲フラグを持たない（後閲フォルダから来ていない状態の場合）
        if (paramMdl.getRng030KoetuFlg() == 1) {
            return false;
        }
        //後閲指示状態True
        if (ringiDao.getKoetuUserCheck(paramMdl.getRng030RksSid())) {
            RngKeiroStepDao kDao = new RngKeiroStepDao(con__);
            if (kDao.getApprButtonFlg(paramMdl.getRng030RksSid(), userSid)) {
                return true;
            }
        }
        return false;
    }

    /**
    *
    * <br>[機  能]経路に追加ボタンの実行判定を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param paramMdl パラメータモデル
    * @param userSid ユーザSID
    * @throws SQLException SQL実行時例外
    * @return ボタンの実行可能判定
    */
   public boolean appKeiroConf(Connection con,
           Rng030ParamModel paramMdl,
           int userSid) throws SQLException {
       RingiDao ringiDao = new RingiDao(con);
       RingiDataModel rngModel = ringiDao.getRingiData(paramMdl.getRngSid(), userSid,
               paramMdl.getRng030RksSid());
       //完了済みか判定
       if (rngModel.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE) {
           return false;
       }
       //稟議が削除済みか確認
       if (rngModel.getRngTitle() == null) {
           return false;
       }
       //稟議SID、作業経路SID、審議者SIDの組み合わせが不正でないことを確認
       if (!ringiDao.chkRngParams(paramMdl.getRngSid(), paramMdl.getRng030RksSid(), userSid)) {
           return false;
       }
       //確認中かどうか
       if (rngModel.getRncStatus() == RngConst.RNG_RNCSTATUS_CONFIRM
               && rngModel.getRssStatus() == RngConst.RNG_RNCSTATUS_CONFIRM) {
           return true;
       }
       return false;
   }

    /**
     *
     * <br>[機  能]確認ボタンの実行判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @return ボタンの実行可能判定
     */
    public boolean confBtnConf(Connection con,
            Rng030ParamModel paramMdl,
            int userSid) throws SQLException {
        RingiDao ringiDao = new RingiDao(con);
        RingiDataModel rngModel = ringiDao.getRingiData(paramMdl.getRngSid(),
                userSid,
                paramMdl.getRng030RksSid());

        //完了済みか判定(完了済みでないと確認ができない)
        if (rngModel.getRngCompflg() != RngConst.RNG_COMPFLG_COMPLETE) {
            return false;
        }
        //稟議が削除済みか確認
        if (rngModel.getRngTitle() == null) {
            return false;
        }
        //稟議SID、経路SID、審議者SIDの組み合わせが不正
        if (!ringiDao.chkRngParams(paramMdl.getRngSid(), paramMdl.getRng030RksSid(), userSid)) {
            return false;
        }
        //稟議受信者でない場合
        if (rngModel.getRncStatus() != RngConst.RNG_RNCSTATUS_CONFIRM
                || rngModel.getRssStatus() != RngConst.RNG_RNCSTATUS_NOSET) {
            return false;
        }
        //最終確認者か判定
        if (rngModel.getRncType() !=  RngConst.RNG_RNCTYPE_CONFIRM) {
            return false;
        }
        return true;
    }


    /**
     *
     * <br>[機  能]完了ボタンの判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @return ボタンの実行可能判定
     */
    public boolean compBtnConf(Connection con,
            Rng030ParamModel paramMdl,
            int userSid) throws SQLException {

        RingiDao ringiDao = new RingiDao(con);
        RingiDataModel rngModel = ringiDao.getRingiData(paramMdl.getRngSid(), userSid,
                paramMdl.getRng030RksSid());

        //完了済みか判定(ここから下は完了済みで操作できない)
        if (rngModel.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE) {
            return false;
        }

        //稟議が削除済みか確認
        if (rngModel.getRngTitle() == null) {
            return false;
        }

        int rngSid = rngModel.getRngSid();

        //稟議が却下されているか判定
        if (rngModel.getRngStatus() != RngConst.RNG_STATUS_REJECT) {
            return false;
        }
        //稟議SID、経路SID、審議者SIDの組み合わせが不正
        if (!ringiDao.chkRngParams(paramMdl.getRngSid(), paramMdl.getRng030RksSid(), userSid)) {
            return false;
        }
        //稟議受信者でない場合
        if (rngModel.getRncStatus() != RngConst.RNG_RNCSTATUS_CONFIRM
                && rngModel.getRncStatus() != RngConst.RNG_RNCSTATUS_NOSET) {
            return false;
        }

        RngKeiroStepDao keiroDao = new RngKeiroStepDao(con);

        //複数承認ではないこと
        int keiroCount = ringiDao.getSingiCount(paramMdl.getRng030RksSid());
        if (keiroCount != 1) {
            return false;
        }

        //完了ボタン判定
        if (rngModel.getRngApplicate() == userSid) {
            return false;
        } else if (keiroDao.isBeforeApproval(rngSid, userSid)) {
            return true;
        }

        return false;
    }

    /**
     *
     * <br>[機  能]強制完了・削除ボタンの判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @return ボタンの実行可能判定
     */
    public boolean cpAppDelBtnConf(Connection con,
            Rng030ParamModel paramMdl,
            int userSid) throws SQLException {


        RingiDao ringiDao = new RingiDao(con);
        RingiDataModel rngModel = ringiDao.getRingiData(paramMdl.getRngSid(), userSid,
                paramMdl.getRng030RksSid());

        //完了済みか判定
        if (rngModel.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE) {
            return false;
        }

        //稟議が削除済みか確認
        if (rngModel.getRngTitle() == null) {
            return false;
        }

        //管理者ユーザか
        CommonBiz cmnBiz = new CommonBiz();
        GroupDao gdao = new GroupDao(con);
        boolean bAdmin = gdao.isBelongAdmin(reqMdl__.getSmodel().getUsrsid());
        if (!bAdmin
                && !cmnBiz.isPluginAdmin(con, userSid, RngConst.PLUGIN_ID_RINGI)) {
            return false;
        }


        //完了していなければtrue
        return true;
    }

    /**
     *
     * <br>[機  能]スキップボタンの判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @return ボタンの実行可能判定
     */
    public boolean skipBtnConf(Connection con,
            Rng030ParamModel paramMdl,
            int userSid) throws SQLException {


        RingiDao ringiDao = new RingiDao(con);
        RingiDataModel rngModel = ringiDao.getRingiData(paramMdl.getRngSid(), userSid,
                paramMdl.getRng030RksSid());

        //完了済みか判定(ここから下は完了済みで操作できない)
        if (rngModel.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE) {
            return false;
        }

        //稟議が削除済みか確認
        if (rngModel.getRngTitle() == null) {
            return false;
        }

        //管理者ユーザか
        CommonBiz cmnBiz = new CommonBiz();
        GroupDao gdao = new GroupDao(con);
        boolean bAdmin = gdao.isBelongAdmin(reqMdl__.getSmodel().getUsrsid());
        if (!bAdmin
                && !cmnBiz.isPluginAdmin(con, userSid, RngConst.PLUGIN_ID_RINGI)) {
            return false;
        }

        int rngSid = rngModel.getRngSid();

        RngKeiroStepDao kDao = new RngKeiroStepDao(con);
        RngKeiroStepModel kMdl = new RngKeiroStepModel();
        kMdl.setRngSid(rngSid);
        kMdl.setRksSid(kDao.getApprovalKeiro(rngSid));

        int nextRksSid = kDao.lastAuthorizer(kMdl, 1);


        if (nextRksSid != 0) {
            return true;
        }

        return false;
    }


    /**
     *
     * <br>[機  能]再申請ボタンの判定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @return ボタンの実行可能判定
     */
    public boolean applicateBtnConf(Connection con,
            Rng030ParamModel paramMdl,
            int userSid) throws SQLException {

        RingiDao ringiDao = new RingiDao(con);
        RingiDataModel rngModel = ringiDao.getRingiData(paramMdl.getRngSid(), userSid,
                paramMdl.getRng030RksSid());

        //完了済みか判定
        if (rngModel.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE) {
            return false;
        }
        //稟議が削除済みか確認
        if (rngModel.getRngTitle() == null) {
            return false;
        }
        //稟議が申請者まで戻っているか判定
        RngKeiroStepDao keiroDao = new RngKeiroStepDao(con);
        if (keiroDao.getSortNo(paramMdl.getRngSid()) != 0) {
            return false;
        }
        //稟議を開いているユーザが稟議の申請者がどうか
        if (rngModel.getRngApplicate() == userSid) {
            return true;
        }

        return false;
    }

    /**
     *
     * <br>[機  能]複写して申請の実行判定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param userSid ユーザSID
     * @throws Exception 実行時例外
     * @return ボタンの実行可能判定
     */
    public int copyApprBtnConf(Connection con,
            Rng030ParamModel paramMdl,
            int userSid) throws Exception {
        RingiDao ringiDao = new RingiDao(con);
        RingiDataModel rngModel = ringiDao.getRingiData(paramMdl.getRngSid(), userSid,
                paramMdl.getRng030RksSid());

        int sessionSid = reqMdl__.getSmodel().getUsrsid();

        Rng020Biz rngBiz = new Rng020Biz(con__, reqMdl__);
        RngTemplateModel model = rngBiz.getTemplateMaxVer(rngModel.getRtpSid());

        // 汎用稟議・個人テンプレート使用制限確認
        RngBiz biz = new RngBiz(con__);
        RngAconfModel aconfMdl = biz.getRngAconf(con__);
        boolean templateChk = rngBiz.isAcceptTemplate(aconfMdl,
                model.getRtpSid(), model.getRtpType());
        if (!templateChk) {
            return RngConst.RNG_COPY_ERROR_TEMPLATE;
        }

        //稟議が削除済みか確認
        if (rngModel.getRngTitle() == null) {
            return RngConst.RNG_COPY_ERROR_ACCESS;
        }

        //稟議を代理人ではないか確認
        if (sessionSid != userSid) {
            return RngConst.RNG_COPY_ERROR_ACCESS;
        }

        // テンプレート使用権限があるか判定
        if (!rngBiz.isUseableTemplate(model)) {
            return RngConst.RNG_COPY_ERROR_ACCESS;
        }

        return RngConst.RNG_COPY_ERROR_NOTHING;
    }

    /**
    *
    * <br>[機  能]後閲の実行判定を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param paramMdl パラメータモデル
    * @param userSid ユーザSID
    * @throws SQLException SQL実行時例外
    * @return ボタンの実行可能判定
     * @throws RtpNotfoundException
    */
   public boolean koetuBtnConf(Connection con,
           Rng030ParamModel paramMdl,
           int userSid) throws SQLException {
       RingiDao ringiDao = new RingiDao(con);
       RingiDataModel rngModel = ringiDao.getRingiData(paramMdl.getRngSid(), userSid,
               paramMdl.getRng030RksSid());

       //完了済みか判定
       if (rngModel.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE) {
           return false;
       }

       //稟議が削除済みか確認
       if (rngModel.getRngTitle() == null) {
           return false;
       }

       //稟議経路情報を設定
       RngKeiroStepDao rksDao = new RngKeiroStepDao(con__);
       List<RngKeiroStepModel> rksModelList = rksDao.selectFromRngSid(paramMdl.getRngSid());
       /**走査中の経路ステップSID*/
       int rksSid = 0;
       /**走査中の経路順*/
       int sortNo = 0;
       /**現在確認中経路順*/
       int confSortNo = 0;
       /**確認中経路以降の後閲不可経路順*/
       int limitKoetuSortNo = 0;
       /**後閲指示者として登場する最後の経路SID*/
       int maxSingiRksSid = viewKeiroSid(paramMdl.getRngSid(), userSid, RngConst.RNG_MODE_KOETU);
       /**後閲指示者として登場する最後の経路順*/
       int maxSingiSortNo = 0;

       int toRksSid = paramMdl.getRng030koetuNo();
       int toSortNo = 0;


       //経路を走査し、判定に必要な情報を取得する
       for (RngKeiroStepModel rksModel : rksModelList) {
           if (rksModel.getRksRollType() == RngConst.RNG_RNCTYPE_CONFIRM) {
               if (limitKoetuSortNo == 0) {
                   limitKoetuSortNo = sortNo;
               }
               break;
           }
           rksSid = rksModel.getRksSid();
           sortNo = rksModel.getRksSort();

           if (rksModel.getRksStatus() == RngConst.RNG_RNCSTATUS_CONFIRM) {
               confSortNo = sortNo;
               if (confSortNo == 0) {
                   //申請者の再申請待ちは後閲不可
                   return false;
               }
           }
           if (rksSid == maxSingiRksSid) {
               maxSingiSortNo = sortNo;
           }
           if (rksSid == toRksSid) {
               toSortNo = sortNo;
           }
           if (limitKoetuSortNo == 0
                   && confSortNo > 0
                   && rksModel.getRksKeiroKoetu() == RngConst.RNG_KOETU_NO) {
                  limitKoetuSortNo = rksModel.getRksSort();
                  if (limitKoetuSortNo == confSortNo) {
                      //確認中経路が後閲不可
                      return false;
                  }
           }
       }
       if (limitKoetuSortNo == 0) {
           limitKoetuSortNo = rksModelList.size() + 1;
       }
       //後閲指示先経路より先にすでに進行している
       if (toSortNo < confSortNo) {
           return false;
       }
       //自身の審議経路より先にすでに進行している
       if (maxSingiSortNo < confSortNo) {
           return false;
       }
       //自身の審議経路より先には後閲不可
       if (maxSingiSortNo <= toSortNo) {
           return false;
       }
       //後閲不可経路より先には後閲不可
       if (limitKoetuSortNo <= toSortNo) {
           return false;
       }
       return true;
   }
   /**
   *
   * <br>[機  能] 経路追加の実行判定を行う
   * <br>[解  説]
   * <br>[備  考]
   * @param con コネクション
   * @param paramMdl パラメータモデル
   * @param userSid ユーザSID
   * @throws SQLException SQL実行時例外
   * @return ボタンの実行可能判定
    * @throws RtpNotfoundException
   */
   public boolean addKeiroBtnConf(Connection con,
          Rng030ParamModel paramMdl,
          int userSid) throws SQLException {
      RingiDao ringiDao = new RingiDao(con);
      RingiDataModel rngModel = ringiDao.getRingiData(paramMdl.getRngSid(), userSid,
              paramMdl.getRng030RksSid());

      if (!appKeiroConf(con, paramMdl, userSid)) {
          return false;
      }

      //稟議経路情報を設定
      int rngSid = paramMdl.getRngSid();
      Map<Integer, Rng020KeiroBlock> addKeiro = paramMdl.getRng030addKeiroMap();
      List<Rng030KeiroParam> keiroList = ringiDao.getKeiroList(rngSid);
      boolean sortInit = true;
      Rng030KeiroAddBiz keiroAddBiz = null;
      keiroAddBiz = new Rng030KeiroAddBiz(
              con__, reqMdl__, rngModel.getRtpSid(), rngModel.getRtpVer(),
              paramMdl.getRng030template());

      int singedFlg = RngConst.RNG_RNCSTATUS_APPR;
      HashMap<Integer, Rng030KeiroParam> targetMap = new HashMap<Integer, Rng030KeiroParam>();
      for (Rng030KeiroParam keiroParam : keiroList) {
          if (sortInit) {
              sortInit = false;
          }
          if (singedFlg == RngConst.RNG_RNCSTATUS_CONFIRM) {
              singedFlg = RngConst.RNG_RNCSTATUS_NOSET;
          }
          if (keiroParam.getKeiroStatus() == RngConst.RNG_RNCSTATUS_CONFIRM) {
              singedFlg = RngConst.RNG_RNCSTATUS_CONFIRM;
          }
          int rksSid = keiroParam.getKeiroStepSid();

          keiroAddBiz.prefStepAddibleFlag(keiroParam, singedFlg);
          if (addKeiro.containsKey(rksSid)) {
              targetMap.put(rksSid, keiroParam);
          }
      }

      for (Entry<Integer, Rng030KeiroParam> entry : targetMap.entrySet()) {
          if (entry.getValue().getKeiroAddible() != RngConst.RNG_ABLE_ADDKEIRO) {
              return false;
          }
      }
      return true;
   }
   /**
   *
   * <br>[機  能] 経路追加の実行判定を行う
   * <br>[解  説]
   * <br>[備  考]
   * @param con コネクション
   * @param paramMdl パラメータモデル
   * @param userSid ユーザSID
   * @throws SQLException SQL実行時例外
   * @return ボタンの実行可能判定
    * @throws RtpNotfoundException
   */
   public boolean torisageBtnConf(Connection con,
          Rng030ParamModel paramMdl,
          int userSid) throws SQLException {
      RingiDao ringiDao = new RingiDao(con);
      RingiDataModel rngModel = ringiDao.getRingiData(paramMdl.getRngSid(), userSid,
              paramMdl.getRng030RksSid());

      //完了済みか判定
      if (rngModel.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE) {
          return false;
      }
      //稟議が削除済みか確認
      if (rngModel.getRngTitle() == null) {
          return false;
      }
      //申請者かどうか
      if (rngModel.getRngApplicate() != userSid) {
          return false;
      }
      return true;
   }

    /**
     * <br>[機  能] 添付ファイルをテンポラリディレクトリにコピーする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param binList 添付ファイルリスト
     * @param appRootPath アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param con コネクション
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void tempFileCopy(List<CmnBinfModel> binList,
                                 String appRootPath,
                                 String tempDir,
                                 Connection con,
                                 String domain)
        throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        UDate now = new UDate();
        String dateStr = now.getDateString();
        int i = 1;

        for (CmnBinfModel binMdl : binList) {

            //添付ファイルをテンポラリディレクトリにコピーする。
            cmnBiz.saveTempFile(dateStr, binMdl, appRootPath, tempDir, i);
            i++;
        }
    }

    /**
     * <br>[機  能] 添付ファイル情報を取得(json形式)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param tempDir テンポラリディレクトリ
     * @param con コネクション
     * @throws IOToolsException
     * @throws IOToolsException ファイルアクセス時例外
     * @return jsonTempStr
     */
    public String setTempFiles(String tempDir, Connection con)
        throws IOToolsException {

        String jsonTempStr = null;
        JSONArray jsonTempArray = null;
        CommonBiz commonBiz = new CommonBiz();

        List<LabelValueBean> fileLabels = commonBiz.getTempFileLabelList(tempDir);
        if (!fileLabels.isEmpty()) {
            jsonTempArray = JSONArray.fromObject(fileLabels);
            jsonTempStr = jsonTempArray.toString();
        }

        return jsonTempStr;
    }

    /**
     *
     * <br>[機  能] 経路における添付情報を取得(json形式)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rksSid 経路SID
     * @param userSid ユーザSID
     * @throws SQLException SQLException
     * @return json json配列
     */
    public JSONObject setTempSingiFiles(Connection con, int rksSid, int userSid)
            throws SQLException {

        JSONObject jsonData = new JSONObject();

        RingiDao dao = new RingiDao(con);
        List<CmnBinfModel> tempList = dao.getSingiTemp(rksSid, userSid);
        for (int nIdx = 0; nIdx < tempList.size(); nIdx++) {
            jsonData.element("name" + nIdx, tempList.get(nIdx).getBinFileName());
            jsonData.element("size" + nIdx, tempList.get(nIdx).getBinFileSizeDsp());
            jsonData.element("sid" + nIdx, String.valueOf(tempList.get(nIdx).getBinSid()));
        }

        return jsonData;
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから更新通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePathSingi(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/ringi/smail/singi_tsuuchi.txt");
        return ret;
    }

    /**
     * <br>[機  能] アプリケーションのルートパスから更新通知メールのテンプレートパスを返す。
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションのルートパス
     * @return テンプレートファイルのパス文字列
     */
    public String getSmlTemplateFilePathZyusin(String appRootPath) {
        //WEBアプリケーションのパス
        appRootPath = IOTools.setEndPathChar(appRootPath);
        String ret = IOTools.replaceSlashFileSep(appRootPath
                + "/WEB-INF/plugin/ringi/smail/zyusin_tsuuchi.txt");
        return ret;
    }


    /**
     * <br>[機  能]オペレーションログに操作対象を書き出します
     * <br>[解  説]
     * <br>[備  考]
     * @param gsMsg GSメッセージ
     * @param msgValue オペレーションログ本文
     * @param targetRksSid 対象経路
     * @param rngSid 開いている稟議のSID
     * @throws SQLException SQL実行時例外
     */
    public void setOpLogValue(
            GsMessage gsMsg, StringBuilder msgValue,
            int targetRksSid, int rngSid) throws SQLException {

        RngKeiroStepDao ksDao = new RngKeiroStepDao(con__);
        RngSingiDao singiDao = new RngSingiDao(con__);
        List<Integer> userList = singiDao.getSingiUser(targetRksSid);
        UserBiz ubiz = new UserBiz();
        List<CmnUsrmInfModel> bMdlList = ubiz.getUserList(con__, userList);
        int sortNum = ksDao.getSortNoKeiro(targetRksSid, rngSid);
        //対象:
        msgValue.append("\r\n");
        msgValue.append(gsMsg.getMessage("rng.rng110.12"));
        msgValue.append(":");

        //1ユーザのステップ
        if (bMdlList.size() == 1) {
            CmnUsrmInfModel bMdl = bMdlList.get(0);
            msgValue.append(bMdl.getUsiSei());
            msgValue.append(bMdl.getUsiMei());
        //複数ユーザ選択の場合
        } else {
            RngKeirostepSelectDao kssDao = new RngKeirostepSelectDao(con__);
            List<RngKeirostepSelectModel> kssMdlList = kssDao.selectByRksSid(targetRksSid);
            if (kssMdlList.size() > 0 && kssMdlList.get(0).getGrpSid() != -1) {
                //グループ選択の場合
                CmnGroupmDao cgDao = new CmnGroupmDao(con__);
                CmnGroupmModel cgMdl = cgDao.select(kssMdlList.get(0).getGrpSid());
                msgValue.append(cgMdl.getGrpName());
            } else {
                msgValue.append(gsMsg.getMessage("rng.rng050.01"));
            }
        }

        if (sortNum > 0) {
            // 承認者
            msgValue.append("(");
            msgValue.append(sortNum);
            msgValue.append(gsMsg.getMessage("rng.01"));
            msgValue.append(")");
        } else {
            // 申請者
            msgValue.append("(");
            msgValue.append(gsMsg.getMessage("rng.47"));
            msgValue.append(")");
        }

    }

    /**
     * <br>[機  能]経路の添付ファイルをテンポラリディレクトリへコピー
     * <br>[解  説]
     * <br>[備  考]
     * @param rksSid 経路SID
     * @param userSid ユーザSID
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行時エラー
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void copySingiBinList(int rksSid, int userSid, String appRoot, String tempDir)
            throws IOException, IOToolsException, SQLException, TempFileException {

        //添付ファイル情報
        RingiDao rngDao = new RingiDao(con__);
        List<CmnBinfModel> binList = rngDao.getSingiTemp(rksSid, userSid);
        if (binList != null && binList.size() > 0) {
            String[] binSids = new String[binList.size()];

            for (int i = 0; i < binSids.length; i++) {
                binSids[i] = String.valueOf(binList.get(i).getBinSid());
            }
            CommonBiz cmnBiz = new CommonBiz();
            String domain = reqMdl__.getDomain();
            binList = cmnBiz.getBinInfo(con__, binSids, domain);

            if (!binList.isEmpty()) {
                //添付ファイルがあるなるならばテンポラリにコピー(パラメータへの追加は呼び出し元)
                Rng030Biz biz = new Rng030Biz(con__, reqMdl__);
                biz.tempFileCopy(binList, appRoot, tempDir, con__, domain);
            }
        }
    }

    /**
     * <br>[機  能] スケジュール(単票)をPDF出力します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータモデル
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @param userSid セッションユーザSID
     * @return pdfModel 施設予約単票PDFモデル
     * @throws IOException IO実行時例外
     * @throws SQLException SQL実行例外
     */
    public RngTanPdfModel createRngTanPdf(
            Rng030ParamModel paramMdl,
            String appRootPath,
            String outTempDir,
            int userSid)
                    throws IOException, SQLException {
        OutputStream oStream = null;

        //スケジュール(単票)PDF出力用モデル
        RngPdfWriter pdfWriter = new RngPdfWriter(con__, reqMdl__);
        RngTanPdfModel pdfModel = pdfWriter.getRngPdfDataList(
                                paramMdl.getRngSid(),
                                paramMdl.getRngProcMode(),
                                paramMdl.getRngApprMode(),
                                userSid);

        String saveFileName = "rngtan" + reqMdl__.getSmodel().getUsrsid() + ".pdf";
        pdfModel.setSaveFileName(saveFileName);

        try {
            IOTools.isDirCheck(outTempDir, true);
            oStream = new FileOutputStream(outTempDir + saveFileName);
            RngTanPdfUtil pdfUtil = new RngTanPdfUtil(reqMdl__);
            pdfUtil.createRngTanPdf(pdfModel, appRootPath, oStream);
        } catch (Exception e) {
            log__.error("稟議(単票)PDF出力に失敗しました。", e);
        } finally {
            if (oStream != null) {
                oStream.flush();
                oStream.close();
            }
        }
        log__.debug("稟議(単票)PDF出力を終了します。");

        return pdfModel;
    }
    /**
     *
     * <br>[機  能] 経路追加用選択要素に対し入力チェック前に選択済みユーザ情報の取得を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    public void validateInitAddKeiro(Rng030ParamModel paramMdl) throws SQLException {
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        UserGroupSelectBiz usrgrpBiz = new UserGroupSelectBiz();
        GroupBiz grpBiz = new GroupBiz();
        String defGrpSid = String.valueOf(
                grpBiz.getDefaultGroupSid(sessionUsrSid,
                        con__));
        List<UsrLabelValueBean> grplist =
                usrgrpBiz.getGroupLabel(reqMdl__, con__);

        //所属グループリスト取得
        UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con__);
        List<GroupModel> bossTargetList = gpDao.selectGroupNmListOrderbyClass(
                reqMdl__.getSmodel().getUsrsid());

        PosBiz posBiz = new PosBiz();
        //役職選択
        List<LabelValueBean> allPosLabelList = posBiz.getPosLabelList(con__, false);
        //役職のないユーザ指定用に「役職なし」を追加
        allPosLabelList.add(0, new LabelValueBean(gsMsg.getMessage("cmn.nopost"), "0"));
        Map<Integer, LabelValueBean> posLabelMap = new HashMap<>();
        for (LabelValueBean label : allPosLabelList) {
            posLabelMap.put(Integer.valueOf(label.getValue()), label);
        }
        for (Entry<Integer, Rng020KeiroBlock> entry : paramMdl.getRng030addKeiroMap().entrySet()) {
            Rng020KeiroBlock block = entry.getValue();
            Collection<Rng020Keiro> keiroSet = block.getKeiroMap().values();
            //追加経路は自己審議許可
            block.getPref().setOwn(RngConst.RNG_OWNSINGI_YES);
            for (Rng020Keiro keiro : keiroSet) {
                keiro.dspInit(con__, reqMdl__,
                        defGrpSid, grplist, bossTargetList, posLabelMap, block, false, 0);
            }
        }

    }
}
