package jp.groupsession.v2.sml.sml010;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.internet.MimeBodyPart;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.archive.ZipUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.CrlfTerminatedWriter;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.mail.MailUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.dao.base.CmnThemeDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnThemeModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.biz.SmlUsedDataBiz;
import jp.groupsession.v2.sml.biz.SmlViewHtmlConverter;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAccountDiskDao;
import jp.groupsession.v2.sml.dao.SmlAdelDao;
import jp.groupsession.v2.sml.dao.SmlAsakDao;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlBodyBinDao;
import jp.groupsession.v2.sml.dao.SmlHinaDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlJmeisLabelDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlSmeisLabelDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.dao.SmlWmeisLabelDao;
import jp.groupsession.v2.sml.model.AccountDataModel;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.model.SmailUsrModel;
import jp.groupsession.v2.sml.model.SmlAccountDiskModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.model.SmlHinaModel;
import jp.groupsession.v2.sml.model.SmlJmeisLabelModel;
import jp.groupsession.v2.sml.model.SmlJmeisModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.sml.model.SmlSmeisLabelModel;
import jp.groupsession.v2.sml.model.SmlSmeisModel;
import jp.groupsession.v2.sml.model.SmlUserModel;
import jp.groupsession.v2.sml.model.SmlWmeisLabelModel;
import jp.groupsession.v2.sml.model.SmlWmeisModel;
import jp.groupsession.v2.sml.pdf.SmlPdfModel;
import jp.groupsession.v2.sml.pdf.SmlPdfUtil;
import jp.groupsession.v2.sml.sml030.Sml030Biz;
import jp.groupsession.v2.sml.sml270.Sml270Dao;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] ショートメール一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml010Biz {

    /** 画面ID */
    public static final String SCR_ID = "sml010";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml010Biz.class);

    /**
     * <br>[機  能] 初期表示データをセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param procMode 処理モード
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Sml010ParamModel paramMdl,
                             String procMode,
                             RequestModel reqMdl,
                             Connection con,
                             String appRootPath)
        throws SQLException {

        log__.debug("初期表示データセット");

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();

        //アカウント情報を設定
        __setAccountInf(paramMdl, con, reqMdl);

        BaseUserModel baseMdl = reqMdl.getSmodel();
        if (baseMdl != null) {
            CommonBiz  commonBiz = new CommonBiz();
            boolean adminUser = commonBiz.isPluginAdmin(con, baseMdl, GSConstMain.PLUGIN_ID_SMAIL);

            if (adminUser) {
                paramMdl.setAdminFlg(GSConst.USER_ADMIN);
            } else {
                paramMdl.setAdminFlg(GSConst.USER_NOT_ADMIN);
            }
        }
        paramMdl.setSml010Reload(__getReloadTime(con, sessionUsrSid, reqMdl));

        //削除日メッセージを設定
        __setDeleteMessage(con, paramMdl, sessionUsrSid);

        //ユーザ一覧設定
        __setLeftMenu(paramMdl, con, baseMdl.getUsrsid(), reqMdl);
        setGroupUserCombo(paramMdl, sessionUsrSid, con, reqMdl);

        paramMdl.setSml090SortKeyLabelList(SmlCommonBiz.getSortLabelList(
                GSConstSmail.TAB_DSP_MODE_JUSIN, reqMdl));

        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl);
        SmlAdminModel smlAdmMdl = smlCmnBiz.getSmailAdminConf(sessionUsrSid, con);
        SmlUserModel smlUsrMdl = smlCmnBiz.getSmailUserConf(sessionUsrSid, con);

        //写真表示フラグ
        int photoDspFlg = getPhotoDspFlg(reqMdl, con);
        paramMdl.setPhotoDspFlg(photoDspFlg);

        //添付画像表示フラグ
        int attachImgFlg = GSConstSmail.SML_IMAGE_TEMP_DSP;
        if (smlAdmMdl.getSmaAttachDspStype() == GSConstSmail.DISP_CONF_ADMIN) {
            //管理者設定の表示設定を反映する
            attachImgFlg = smlAdmMdl.getSmaAttachDsp();
        } else {
            //個人設定の表示設定を反映する
            attachImgFlg = smlUsrMdl.getSmlTempDsp();
        }
        paramMdl.setTempDspFlg(attachImgFlg);

        //メール最大文字数取得
        int maxlength = 0;
        maxlength = SmlCommonBiz.getBodyLimitLength(appRootPath);
        paramMdl.setSml010MailBodyLimit(maxlength);
    }

    /**
     * <br>[機  能] 取得結果を変換する(受信データ、ゴミ箱データ)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramList 取得結果リスト
     * @return ret 変換後リスト
     */
    public ArrayList<SmailModel> __convertJmeisData(ArrayList<SmailModel> paramList) {

        ArrayList<SmailModel> ret = new ArrayList<SmailModel>();

        for (SmailModel paramMdl : paramList) {
            SmailModel retMdl = new SmailModel();

            //削除処理時に使用するメール判断キー作成
            //メール区分 + メールSID(10桁フォーマット)
            retMdl.setMailKey(
                    paramMdl.getMailKbn()
                    + StringUtil.toDecFormat(
                            paramMdl.getSmlSid(), GSConstSmail.MAIL_KEY_FORMAT));
            retMdl.setMailKbn(paramMdl.getMailKbn());
            retMdl.setSmlSid(paramMdl.getSmlSid());
            retMdl.setSmjOpkbn(paramMdl.getSmjOpkbn());
            retMdl.setSmsMark(paramMdl.getSmsMark());
            retMdl.setSmsTitle(StringUtilHtml.transToHTmlPlusAmparsant(
                    NullDefault.getString(paramMdl.getSmsTitle(), "")));

            retMdl.setSmsSize(paramMdl.getSmsSize());
            retMdl.setSmlSizeStr(CommonBiz.formatByteSizeString(paramMdl.getSmsSize()));

            //日付yyyy/MM/dd hh:mm:ss形式に変換
            if (paramMdl.getSmsSdate() != null) {
                String strSdate =
                    UDateUtil.getSlashYYMD(paramMdl.getSmsSdate())
                    + "  "
                    + UDateUtil.getSeparateHMS(paramMdl.getSmsSdate());
                retMdl.setStrSdate(strSdate);
            }

            retMdl.setUsrSid(paramMdl.getUsrSid());

            retMdl.setAccountJkbn(paramMdl.getAccountJkbn());
            retMdl.setAccountSid(paramMdl.getAccountSid());
            retMdl.setAccountName(paramMdl.getAccountName());

            if (paramMdl.getUsrSid() <= 0) {
                retMdl.setUsrJkbn(paramMdl.getAccountJkbn());
                retMdl.setUsiSei(NullDefault.getString(
                        paramMdl.getUsiSei(), paramMdl.getAccountName()));
                retMdl.setUsiMei(NullDefault.getString(paramMdl.getUsiMei(), ""));
            } else {
                retMdl.setUsrJkbn(paramMdl.getUsrJkbn());
                retMdl.setUsrUkoFlg(paramMdl.getUsrUkoFlg());
                retMdl.setUsiSei(NullDefault.getString(paramMdl.getUsiSei(), ""));
                retMdl.setUsiMei(NullDefault.getString(paramMdl.getUsiMei(), ""));
            }


            retMdl.setBinCnt(paramMdl.getBinCnt());
            retMdl.setBinFileSid(paramMdl.getBinFileSid());
            retMdl.setPhotoFileDsp(paramMdl.getPhotoFileDsp());
            retMdl.setReturnKbn(paramMdl.getReturnKbn());
            retMdl.setFwKbn(paramMdl.getFwKbn());

            retMdl.setLabelList(paramMdl.getLabelList());

            ret.add(retMdl);
        }

        return ret;
    }

    /**
     * <br>[機  能] ゴミ箱のデータ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return cnt 件数
     * @throws SQLException SQL実行時例外
     */
    public int getGomibakoCnt(Sml010ParamModel paramMdl,
                                RequestModel reqMdl,
                                Connection con)
        throws SQLException {

        SmailDao sDao = new SmailDao(con);
        int cnt = sDao.getGomibakoCount(paramMdl.getSmlViewAccount());

        return cnt;
    }

    /**
     * <br>[機  能] ゴミ箱のデータ件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sessionUsrSid ユーザSID
     * @param reqMdl リクエスト情報
     * @return int リロード時間
     * @throws SQLException SQL実行時例外
     */
    private int __getReloadTime(Connection con, int sessionUsrSid, RequestModel reqMdl)
            throws SQLException {
        int reloadTime = 0;
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl);
        SmlAdminModel adminModel = smlCmnBiz.getSmailAdminConf(sessionUsrSid, con);

        if (adminModel.getSmaReloadDspStype() == GSConstSmail.DISP_CONF_ADMIN) {
            //管理者設定の表示設定を反映する
            reloadTime = adminModel.getSmaReloadDsp();
        } else {
            //個人設定の表示設定を反映する
            SmlUserModel userModel = smlCmnBiz.getSmailUserConf(sessionUsrSid, con);
            reloadTime = userModel.getSmlReload();
        }

        return reloadTime;
    }

    /**
     * <br>[機  能] 取得結果を変換する(送信データ)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramList 取得結果リスト
     * @return ret 変換後リスト
     */
    public ArrayList<SmailModel> __convertSmeisData(ArrayList<SmailModel> paramList) {

        ArrayList<SmailModel> ret = new ArrayList<SmailModel>();

        for (SmailModel paramMdl : paramList) {
            SmailModel retMdl = new SmailModel();

            //削除処理時に使用するメール判断キー作成
            //メール区分 + メールSID(10桁フォーマット)
            retMdl.setMailKey(
                    paramMdl.getMailKbn()
                    + StringUtil.toDecFormat(
                            paramMdl.getSmlSid(), GSConstSmail.MAIL_KEY_FORMAT));
            retMdl.setMailKbn(paramMdl.getMailKbn());
            retMdl.setSmlSid(paramMdl.getSmlSid());
            retMdl.setSmsMark(paramMdl.getSmsMark());
            retMdl.setSmsTitle(StringUtilHtml.transToHTmlPlusAmparsant(
                    NullDefault.getString(paramMdl.getSmsTitle(), "")));

            retMdl.setSmsSize(paramMdl.getSmsSize());
            retMdl.setSmlSizeStr(CommonBiz.formatByteSizeString(paramMdl.getSmsSize()));

            //日付yyyy/MM/dd hh:mm:ss形式に変換
            if (paramMdl.getSmsSdate() != null) {
                String strSdate =
                    UDateUtil.getSlashYYMD(paramMdl.getSmsSdate())
                    + "  "
                    + UDateUtil.getSeparateHMS(paramMdl.getSmsSdate());
                retMdl.setStrSdate(strSdate);
            }
            retMdl.setAtesakiList(paramMdl.getAtesakiList());
            if (!retMdl.getAtesakiList().isEmpty()) {
                retMdl.setListSize(retMdl.getAtesakiList().size() - 1);
            }
            retMdl.setBinCnt(paramMdl.getBinCnt());

            retMdl.setLabelList(paramMdl.getLabelList());

            ret.add(retMdl);
        }

        return ret;
    }

    /**
     * <br>[機  能] 取得結果を変換する(草稿データ)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramList 取得結果リスト
     * @return ret 変換後リスト
     */
    public ArrayList<SmailModel> __convertWmeisData(ArrayList<SmailModel> paramList) {

        ArrayList<SmailModel> ret = new ArrayList<SmailModel>();

        for (SmailModel paramMdl : paramList) {
            SmailModel retMdl = new SmailModel();

            //削除処理時に使用するメール判断キー作成
            //メール区分 + メールSID(10桁フォーマット)
            retMdl.setMailKey(
                    paramMdl.getMailKbn()
                    + StringUtil.toDecFormat(
                            paramMdl.getSmlSid(), GSConstSmail.MAIL_KEY_FORMAT));
            retMdl.setMailKbn(paramMdl.getMailKbn());
            retMdl.setSmlSid(paramMdl.getSmlSid());
            retMdl.setSmsMark(paramMdl.getSmsMark());
            retMdl.setSmsTitle(StringUtilHtml.transToHTmlPlusAmparsant(
                    NullDefault.getString(paramMdl.getSmsTitle(), "")));

            retMdl.setSmsSize(paramMdl.getSmsSize());
            retMdl.setSmlSizeStr(CommonBiz.formatByteSizeString(paramMdl.getSmsSize()));

            //日付yyyy/MM/dd hh:mm:ss形式に変換
            if (paramMdl.getSmsSdate() != null) {
                String strSdate =
                    UDateUtil.getSlashYYMD(paramMdl.getSmsSdate())
                    + "  "
                    + UDateUtil.getSeparateHMS(paramMdl.getSmsSdate());
                retMdl.setStrSdate(strSdate);
            }
            retMdl.setAtesakiList(paramMdl.getAtesakiList());
            if (!retMdl.getAtesakiList().isEmpty()) {
                retMdl.setListSize(retMdl.getAtesakiList().size() - 1);
            }
            retMdl.setBinCnt(paramMdl.getBinCnt());

            retMdl.setLabelList(paramMdl.getLabelList());

            ret.add(retMdl);
        }

        return ret;
    }

    /**
     * <br>[機  能] チェック中のhiddenリストを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param checkAry 現在まで入力されているチェックボックスの値
     * @param viewList 一覧データ（最大20件まで）
     * @return hiddenリスト
     */
    private ArrayList<String> __setHiddenSelectedList(String[] checkAry,
                                                       ArrayList<SmailModel> viewList) {

        //ハッシュリストの作成
        HashSet<String> map = new HashSet<String>();

        if (viewList != null) {
            for (SmailModel viewMdl : viewList) {
                String key =
                    viewMdl.getMailKbn()
                        + StringUtil.toDecFormat(
                                viewMdl.getSmlSid(), GSConstSmail.MAIL_KEY_FORMAT);
                map.add(key);
            }
        }

        ArrayList<String> hiddenList = new ArrayList<String>();
        if (checkAry != null) {
            //入力値(hidden値)から表示データのパラメータを除外して登録
            for (int i = 0; i < checkAry.length; i++) {
                if (!map.contains(checkAry[i])) {
                    hiddenList.add(checkAry[i]);
                }
            }
        }

        return hiddenList;
    }

    /**
     * <br>[機  能] 削除・復旧の対象メールタイトル取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return ret 削除対象メッセージリスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<SmailDetailModel> getTargetMailList(Sml010ParamModel paramMdl,
                                                          RequestModel reqMdl,
                                                          Connection con)
        throws SQLException {

        String mode = paramMdl.getSml010ProcMode();
        ArrayList<SmailDetailModel> ret = null;

        //処理モード = 受信モード
        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
            SmlJmeisDao jDao = new SmlJmeisDao(con);
            ret = jDao.selectTargetJDetail(
                    paramMdl.getSmlViewAccount(), paramMdl.getSml010DelSid());
        //処理モード = 送信モード
        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            SmlSmeisDao sDao = new SmlSmeisDao(con);
            ret = sDao.selectTargetSDetail(
                    paramMdl.getSmlViewAccount(), paramMdl.getSml010DelSid());
        //処理モード = 草稿モード
        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
            SmlWmeisDao wDao = new SmlWmeisDao(con);
            ret = wDao.selectTargetWDetail(
                    paramMdl.getSmlViewAccount(), paramMdl.getSml010DelSid());
        //処理モード = ゴミ箱モード
        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
            SmailDao sDao = new SmailDao(con);
            ret = sDao.selectTargetGDetail(
                    paramMdl.getSmlViewAccount(), paramMdl.getSml010DelSid());
        //処理モード = ラベルモード
        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            SmailDao sDao = new SmailDao(con);
            ret = sDao.selectTargetLDetail(
                    paramMdl.getSmlViewAccount(), paramMdl.getSml010DelSid());
        }

        return ret;
    }

    /**
     * <br>[機  能] 削除処理実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void deleteMessage(Sml010ParamModel paramMdl, RequestModel reqMdl, Connection con)
        throws SQLException {

        String procMode = paramMdl.getSml010ProcMode();
        String[] delSidList = paramMdl.getSml010DelSid();

        //処理モード = 受信モード
        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
            log__.debug("受信メッセージ削除(ゴミ箱へ移動)");
            SmlJmeisDao jdao = new SmlJmeisDao(con);
            jdao.moveJmeis(
                    sessionUserSid,
                    paramMdl.getSmlViewAccount(),
                    GSConstSmail.SML_JTKBN_GOMIBAKO,
                    new UDate(),
                    delSidList);
        //処理モード = 送信モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            log__.debug("送信メッセージ削除(ゴミ箱へ移動)");
            SmlSmeisDao sdao = new SmlSmeisDao(con);
            sdao.moveSmeis(
                    sessionUserSid,
                    paramMdl.getSmlViewAccount(),
                    GSConstSmail.SML_JTKBN_GOMIBAKO,
                    new UDate(),
                    delSidList);
        //処理モード = 下書きモード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
            log__.debug("草稿メッセージ削除(ゴミ箱へ移動)");
            SmlWmeisDao wdao = new SmlWmeisDao(con);
            wdao.moveWmeis(
                    sessionUserSid,
                    paramMdl.getSmlViewAccount(),
                    GSConstSmail.SML_JTKBN_GOMIBAKO,
                    new UDate(),
                    delSidList);
        //処理モード = ゴミ箱
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)
                || procMode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            log__.debug("ゴミ箱メッセージ削除");

            //メールSIDの区分を解析し分解する
            ArrayList<String> jMeis = new ArrayList<String>();
            ArrayList<String> sMeis = new ArrayList<String>();
            ArrayList<String> wMeis = new ArrayList<String>();

            for (String mailKey : delSidList) {
                String mailKbn = mailKey.substring(0, 1);
                if (mailKbn.startsWith(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                    jMeis.add(mailKey);
                } else if (mailKbn.startsWith(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                    sMeis.add(mailKey);
                } else if (mailKbn.startsWith(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                    wMeis.add(mailKey);
                }
            }

           /************************************************************************
            *
            * 受信、送信の場合は他のユーザのデータと参照しあうため論理削除とする。
            * 草稿に関しては自分のみのデータなので物理削除とする。
            *
            ************************************************************************/

            if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                //アカウントディスク使用量の再計算を行う
                SmlCommonBiz smlBiz = new SmlCommonBiz();
                long sum = 0;
                List<Integer> desSidListInt = Stream.of(delSidList)
                        .map(sid -> Integer.parseInt(sid.substring(1)))
                        .collect(Collectors.toList());

                //受信メッセージ(論理削除)
                if (!jMeis.isEmpty()) {
                    SmlJmeisDao jdao = new SmlJmeisDao(con);
                    jdao.moveJmeis(
                            sessionUserSid,
                            paramMdl.getSmlViewAccount(),
                            GSConstSmail.SML_JTKBN_DELETE,
                            new UDate(),
                            delSidList);

                    Map<Integer, Long> delMailList = jdao.getDeleteMail(
                            desSidListInt, 2, desSidListInt.size(), 0);

                    for (Map.Entry<Integer, Long> map : delMailList.entrySet()) {
                        sum += map.getValue();
                    }
                }
                //送信メッセージ(論理削除)
                if (!sMeis.isEmpty()) {
                    SmlSmeisDao sdao = new SmlSmeisDao(con);
                    sdao.moveSmeis(
                            sessionUserSid,
                            paramMdl.getSmlViewAccount(),
                            GSConstSmail.SML_JTKBN_DELETE,
                            new UDate(),
                            delSidList);

                    Map<String, Long> delMailList = sdao.getDeleteMail(
                            desSidListInt, 2, desSidListInt.size(), 0);

                    for (Map.Entry<String, Long> map : delMailList.entrySet()) {
                        sum += map.getValue();
                    }
                }
                //草稿メッセージ(物理削除)
                if (!wMeis.isEmpty()) {

                    //ショートメール情報(草稿)のデータ使用量を登録(削除対象のデータ使用量を減算)
                    List<Integer> smwSidList = new ArrayList<Integer>();
                    ArrayList<String> targetStr = new ArrayList<String>();
                    for (String delSid : delSidList) {
                        smwSidList.add(Integer.parseInt(delSid.substring(1)));
                        if (delSid.substring(0, 1).equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                            int target = Integer.parseInt(delSid.substring(1));
                            targetStr.add(String.valueOf(target));
                        }

                    }
                    SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(con);
                    usedDataBiz.insertSoukouDataSize(smwSidList, false);

                    //ラベル
                    SmailDao smlDao = new SmailDao(con);
                    smlDao.deleteListLabel(paramMdl.getSmlViewAccount(), delSidList);
                    //添付情報(論理削除)
                    ArrayList<Integer> arraySidList = new ArrayList<Integer>(smwSidList);
                    SmlWmeisDao wdao = new SmlWmeisDao(con);
                    Map<SmlWmeisModel, Long> delMailList = wdao.getDeleteMail(
                            desSidListInt, 2, desSidListInt.size(), 0);

                    for (Map.Entry<SmlWmeisModel, Long> map : delMailList.entrySet()) {
                        sum += map.getValue();
                    }

                    wdao.deleteSoukouBin(paramMdl.getSmlViewAccount(), delSidList);
                    wdao.deleteGomibakoBodyBin(paramMdl.getSmlViewAccount(), arraySidList);
                    SmlBinDao sbinDao = new SmlBinDao(con);
                    sbinDao.deleteSmlBin(targetStr);
                    SmlBodyBinDao sbbDao = new SmlBodyBinDao(con);
                    sbbDao.delete(targetStr);

                    //草稿
                    wdao.deleteMsgButuri(paramMdl.getSmlViewAccount(), delSidList);
                    SmlAsakDao adao = new SmlAsakDao(con);
                    adao.deleteMsgButuri(paramMdl.getSmlViewAccount(), delSidList);
                }

                //データ容量の計算
                smlBiz.updateAccountDiskSize(con, paramMdl.getSmlViewAccount(), -sum);
            } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
                //受信メッセージ
                if (!jMeis.isEmpty()) {
                    SmlJmeisDao jdao = new SmlJmeisDao(con);
                    jdao.moveJmeis(
                            sessionUserSid,
                            paramMdl.getSmlViewAccount(),
                            GSConstSmail.SML_JTKBN_GOMIBAKO,
                            new UDate(),
                            delSidList);
                }
                //送信メッセージ
                if (!sMeis.isEmpty()) {
                    SmlSmeisDao sdao = new SmlSmeisDao(con);
                    sdao.moveSmeis(
                            sessionUserSid,
                            paramMdl.getSmlViewAccount(),
                            GSConstSmail.SML_JTKBN_GOMIBAKO,
                            new UDate(),
                            delSidList);
                }
                //草稿メッセージ
                if (!wMeis.isEmpty()) {
                    SmlWmeisDao wdao = new SmlWmeisDao(con);
                    wdao.moveWmeis(
                            sessionUserSid,
                            paramMdl.getSmlViewAccount(),
                            GSConstSmail.SML_JTKBN_GOMIBAKO,
                            new UDate(),
                            delSidList);

                }
            }
        }
    }

    /**
     * <br>[機  能] ゴミ箱を空にする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void clearGomibako(Sml010ParamModel paramMdl, RequestModel reqMdl, Connection con)
        throws SQLException {

        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        long sum = 0;

        //受信メッセージ(論理削除)
        SmlJmeisDao jdao = new SmlJmeisDao(con);
        //受信削除容量の取得
        List<SmlJmeisModel> jdelList = jdao.getGomibakoMailList(paramMdl.getSmlViewAccount());
        List<Integer> jdelSidList = jdelList.stream().map(
                mdl -> mdl.getSmjSid()).collect(Collectors.toList());
        Map<Integer, Long> jmeisMap = jdao.getDeleteMail(
                jdelSidList, GSConstSmail.DEL_KBN_DELETED, Integer.MAX_VALUE, 0);
        for (Map.Entry<Integer, Long> map : jmeisMap.entrySet()) {
            sum += map.getValue();
        }

        jdao.moveGomibakoJmeis(sessionUserSid, paramMdl.getSmlViewAccount(), new UDate());

        //送信メッセージ(論理削除)
        SmlSmeisDao sdao = new SmlSmeisDao(con);
        //送信削除容量の取得
        List<SmlSmeisModel> sdelList = sdao.getGomibakoMailList(paramMdl.getSmlViewAccount());
        for (SmlSmeisModel smeisMdl : sdelList) {
            sum += smeisMdl.getSmsSize();
        }
        sdao.moveGomibakoSmeis(sessionUserSid, paramMdl.getSmlViewAccount(), new UDate());

        //物理削除対象のメールSIDを取得
        SmlWmeisDao wdao = new SmlWmeisDao(con);
        List<SmlWmeisModel> wdelList = wdao.getGomibakoMailList(paramMdl.getSmlViewAccount());
        for (SmlWmeisModel wmeisMdl : wdelList) {
            sum += wmeisMdl.getSmwSize();
        }

        List<Integer> targetList = wdelList.stream().map(
                mdl -> mdl.getSmwSid()).collect(Collectors.toList());
        ArrayList<Integer> target = new ArrayList<Integer>(targetList);

        if (!target.isEmpty()) {
            //ショートメール情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(con);
            usedDataBiz.insertSoukouDataSize(target, false);

            //草稿メッセージ(物理削除)
            //ラベル
            SmailDao smlDao = new SmailDao(con);
            smlDao.deletelGomibakoLabel(target);
            //添付情報(論理削除)
            wdao.deleteGomibakoBin(paramMdl.getSmlViewAccount(), target);
            wdao.deleteGomibakoBodyBin(paramMdl.getSmlViewAccount(), target);

            //添付情報(ショートメールテーブル削除)
            SmlBinDao sbinDao = new SmlBinDao(con);
            ArrayList<String> targetStr = new ArrayList<String>();
            for (int sid : target) {
                targetStr.add(String.valueOf(sid));
            }
            sbinDao.deleteSmlBin(targetStr);
            SmlBodyBinDao sbbDao = new SmlBodyBinDao(con);
            sbbDao.delete(targetStr);

            //草稿
            wdao.deleteGomibakoMsgButuri(paramMdl.getSmlViewAccount());
            SmlAsakDao adao = new SmlAsakDao(con);
            adao.deleteGomibakoMsgButuri(paramMdl.getSmlViewAccount(), target);
        }

        //アカウントディスク容量の再計算を行う
        SmlCommonBiz smlBiz = new SmlCommonBiz();
        smlBiz.updateAccountDiskSize(con, paramMdl.getSmlViewAccount(), -sum);
    }

    /**
     * <br>[機  能] 復旧処理実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void revivedMessage(Sml010ParamModel paramMdl, RequestModel reqMdl, Connection con)
        throws SQLException {

        log__.debug("ゴミ箱メッセージ復旧");

        String[] revivedSidList = paramMdl.getSml010DelSid();

        //メールSIDの区分を解析し分解する
        ArrayList<Integer> jMeis = new ArrayList<Integer>();
        ArrayList<Integer> sMeis = new ArrayList<Integer>();
        ArrayList<Integer> wMeis = new ArrayList<Integer>();

        for (String mailKey : revivedSidList) {
            String mailKbn = mailKey.substring(0, 1);
            if (mailKbn.startsWith(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                jMeis.add(Integer.valueOf(mailKey.substring(0, 1)));
            } else if (mailKbn.startsWith(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                sMeis.add(Integer.valueOf(mailKey.substring(0, 1)));
            } else if (mailKbn.startsWith(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                wMeis.add(Integer.valueOf(mailKey.substring(0, 1)));
            }
        }

       /************************************************************************
        *
        * 選択されたメールを種類毎(受信、送信、草稿)に復旧していく。
        *
        ************************************************************************/

        int sessionUserSid = reqMdl.getSmodel().getUsrsid();

        //受信メッセージ
        if (!jMeis.isEmpty()) {
            SmlJmeisDao jdao = new SmlJmeisDao(con);
            jdao.moveJmeis(
                    sessionUserSid,
                    paramMdl.getSmlViewAccount(),
                    GSConstSmail.SML_JTKBN_TOROKU,
                    new UDate(),
                    revivedSidList);
        }
        //送信メッセージ
        if (!sMeis.isEmpty()) {
            SmlSmeisDao sdao = new SmlSmeisDao(con);
            sdao.moveSmeis(
                    sessionUserSid,
                    paramMdl.getSmlViewAccount(),
                    GSConstSmail.SML_JTKBN_TOROKU,
                    new UDate(),
                    revivedSidList);
        }
        //草稿メッセージ
        if (!wMeis.isEmpty()) {
            SmlWmeisDao wdao = new SmlWmeisDao(con);
            wdao.moveWmeis(
                    sessionUserSid,
                    paramMdl.getSmlViewAccount(),
                    GSConstSmail.SML_JTKBN_TOROKU,
                    new UDate(),
                    revivedSidList);
        }
    }

    /**
     * <br>[機  能] 宛先一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid メールSID
     * @param sendkbn 送信区分
     * @param con コネクション
     * @return ret 宛先一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<AtesakiModel> getAtesaki(int mailSid, int sendkbn, Connection con)
        throws SQLException {

        SmailDao sdao = new SmailDao(con);
        ArrayList<AtesakiModel> ret = sdao.getSitagakiAtesakiList(mailSid, sendkbn);

        return ret;
    }

    /**
     * <br>[機  能] 宛先一覧を取得(草稿タブ時)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mailSid メールSID
     * @param sendkbn 送信区分
     * @param con コネクション
     * @return ret 宛先一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<AtesakiModel> getAtesaki2(int mailSid, int sendkbn, Connection con)
        throws SQLException {

        SmailDao sdao = new SmailDao(con);
        ArrayList<AtesakiModel> ret = sdao.getSitagakiAtesakiList2(mailSid, sendkbn);

        return ret;
    }

    /**
     * <br>[機  能] ショートメール自動削除の期間メッセージを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param sacSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    private void __setDeleteMessage(Connection con, Sml010ParamModel paramMdl, int sacSid)
    throws SQLException {
        String procMode = paramMdl.getSml010ProcMode();

        SmlAdelDao smlDelDao = new SmlAdelDao(con);

        SmlAdelModel smlDelMdl = smlDelDao.getAdminAutoDelData();

        if (smlDelMdl == null
        || smlDelMdl.getSadDelKbn() == GSConstSmail.SML_DEL_KBN_USER_SETTING) {
            smlDelMdl = smlDelDao.select(sacSid);
        }

        if (smlDelMdl != null) {

            //該当データ取得(受信モード)
            if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                paramMdl.setSml010autoDelKbn(smlDelMdl.getSadJdelKbn());
                paramMdl.setSml010autoDelYear(smlDelMdl.getSadJdelYear());
                paramMdl.setSml010autoDelMonth(smlDelMdl.getSadJdelMonth());

            //該当データ取得(送信モード)
            } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                paramMdl.setSml010autoDelKbn(smlDelMdl.getSadSdelKbn());
                paramMdl.setSml010autoDelYear(smlDelMdl.getSadSdelYear());
                paramMdl.setSml010autoDelMonth(smlDelMdl.getSadSdelMonth());

            //該当データ取得(草稿モード)
            } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                paramMdl.setSml010autoDelKbn(smlDelMdl.getSadWdelKbn());
                paramMdl.setSml010autoDelYear(smlDelMdl.getSadWdelYear());
                paramMdl.setSml010autoDelMonth(smlDelMdl.getSadWdelMonth());

            //該当データ取得(ゴミ箱モード)
            } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
                paramMdl.setSml010autoDelKbn(smlDelMdl.getSadDdelKbn());
                paramMdl.setSml010autoDelYear(smlDelMdl.getSadDdelYear());
                paramMdl.setSml010autoDelMonth(smlDelMdl.getSadDdelMonth());

            }
        }
    }

    /**
     * <br>[機  能] ユーザ一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param sessionUsrSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    private void __setLeftMenu(
            Sml010ParamModel paramMdl, Connection con, int sessionUsrSid, RequestModel reqMdl
            ) throws SQLException {


        //使用可能なアカウントの一覧を取得する
        Sml270Dao dao = new Sml270Dao(con);
        List<AccountDataModel> accountList = dao.getAccountList(sessionUsrSid);
        paramMdl.setSml010AccountList(accountList);

        String groupSid = paramMdl.getSml010groupSid();

        if (StringUtil.isNullZeroString(groupSid)) {
            GroupBiz grpBiz = new GroupBiz();
            groupSid = String.valueOf(grpBiz.getDefaultGroupSid(sessionUsrSid, con));
            paramMdl.setSml010groupSid(groupSid);
        }

        //グループ一覧を取得する
        GroupBiz gpBiz = new GroupBiz();

        //ユーザSIDからマイグループ情報を取得する
        CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
        List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(sessionUsrSid);
        List<CmnLabelValueModel> dspGrpList = new ArrayList<CmnLabelValueModel>();

        //代表アカウント
        dspGrpList.add(
                new CmnLabelValueModel("代表アカウント", GSConstSmail.SML_ACCOUNT_STR,
                        "2", false));

        //マイグループリストをセット
        for (CmnMyGroupModel cmgMdl : cmgList) {
            dspGrpList.add(
                    new CmnLabelValueModel(
                            cmgMdl.getMgpName(), "M" + String.valueOf(cmgMdl.getMgpSid()),
                            "1", false));
        }

        GsMessage gsMsg = new GsMessage(reqMdl);

        List<LabelValueBean> grpLabelList = gpBiz.getGroupCombLabelList(con, true, gsMsg);
        List<String> banGrpList = __getBanDestUser(reqMdl, con);
        for (LabelValueBean bean : grpLabelList) {
            if (banGrpList.contains(bean.getValue())) {
                dspGrpList.add(
                        new CmnLabelValueModel(bean.getLabel(), bean.getValue(),
                                "0", true));
            } else {
                dspGrpList.add(
                        new CmnLabelValueModel(bean.getLabel(), bean.getValue(),
                                "0", false));
            }
        }

        paramMdl.setSml010groupList(dspGrpList);

        List<SmailUsrModel> smlUsrList = new ArrayList<SmailUsrModel>();

        if (isSmlAccount(groupSid)) {
            //代表アカウントを取得
            SmlAccountDao sacDao = new SmlAccountDao(con);
            List<Integer> sidList = sacDao.getSmlAccountSidList();
            if (sidList.size() > 0) {
                //送信先制限されたアカウントを除外する
                SmlCommonBiz smlCommonBiz = new SmlCommonBiz(con, reqMdl);
                sidList = smlCommonBiz.getValiableDestAccSid(con, sessionUsrSid, sidList);
                if (sidList.size() > 0) {
                    smlUsrList = sacDao.selectSmlAccount(sidList);
                }
            }

        } else {

            //グループSIDから所属ユーザ一覧を作成
            int grpSid = getDspGroupSid(groupSid);
            List<Integer> users = new ArrayList<Integer>();

            if (isMyGroupSid(groupSid)) {
                //マイグループから作成
                CmnMyGroupMsDao mgmsDao = new CmnMyGroupMsDao(con);
                users = mgmsDao.selectMyGroupUsers(sessionUsrSid, grpSid);

            } else {
                //通常グループから作成
                CmnBelongmDao cmnbDao = new CmnBelongmDao(con);
                users = cmnbDao.selectBelongUserSid(getDspGroupSid(groupSid));
            }

            //ショートメールプラグインを使用していないユーザを除外する。
            CommonBiz cmnBiz = new CommonBiz();
            List<Integer> usrList = (ArrayList<Integer>) cmnBiz.getCanUseSmailUser(con, users);

            //システムメールとGS管理者を除外する
            List<Integer> usrDspList = new ArrayList<Integer>();
            for (Integer usid : usrList) {
                if (usid != GSConstUser.SID_ADMIN && usid != GSConstUser.SID_SYSTEM_MAIL) {
                    usrDspList.add(usid);
                }
            }

            //送信先制限されたユーザを除外する
            SmlCommonBiz smlCommonBiz = new SmlCommonBiz(con, reqMdl);
            usrDspList = smlCommonBiz.getValiableDestUsrSid(con, sessionUsrSid, usrDspList);

            //ユーザ情報を取得
            List<CmnUsrmInfModel> uList = null;
            if (users != null && users.size() > 0) {
                //ユーザ情報一覧を作成
                UserBiz usrBiz = new UserBiz();
                uList = usrBiz.getUserList(con, usrDspList);

                try {

                    if (uList != null && !uList.isEmpty()) {
                        for (CmnUsrmInfModel usrMdl : uList) {
                            SmailUsrModel smlUsrMdl = new SmailUsrModel();
                            BeanUtils.copyProperties(smlUsrMdl, usrMdl);
                            smlUsrList.add(smlUsrMdl);
                        }
                    }

                } catch (Exception e) {
                    log__.error("ユーザリスト(選択用)の作成に失敗");
                }
            }
        }

        paramMdl.setSml010userList(smlUsrList);

        //他プラグインから遷移時に作成するメールの宛先ユーザの情報
        if (!StringUtil.isNullZeroStringSpace(paramMdl.getSml010scriptSelUsrSid())
                && GSValidateUtil.isNumber(paramMdl.getSml010scriptSelUsrSid())) {
            CmnUsrmInfModel usrMdl = new CmnUsrmInfModel();
            CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con);
            usrMdl = usrDao.select(
                    Integer.parseInt(paramMdl.getSml010scriptSelUsrSid()));
            if (usrMdl != null) {
                paramMdl.setSml010scriptSelUsrName(
                        usrMdl.getUsiSei() + " " + usrMdl.getUsiMei());
            }
        } else if (!StringUtil.isNullZeroStringSpace(paramMdl.getSml010scriptSelSacSid())
                      && GSValidateUtil.isNumber(paramMdl.getSml010scriptSelSacSid())) {
            SmlAccountDao sacDao = new SmlAccountDao(con);
            SmlAccountModel sacMdl = null;
            sacMdl = sacDao.select(Integer.valueOf(paramMdl.getSml010scriptSelSacSid()));
            if (sacMdl != null) {
                if (sacMdl.getUsrSid() > 0) {
                    paramMdl.setSml010scriptSelSacSid("");
                    paramMdl.setSml010scriptSelUsrSid(String.valueOf(sacMdl.getUsrSid()));
                } else {
                    paramMdl.setSml010scriptSelUsrSid("");
                }
                paramMdl.setSml010scriptSelUsrName(sacMdl.getSacName());
            }
        }
    }

    /**
     * パラメータ.グループコンボ値からグループSID又はマイグループSIDを取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return int グループSID又はマイグループSID
     */
    public static int getDspGroupSid(String gpSid) {
        int ret = 0;
        if (gpSid == null) {
            return ret;
        }

        if (isMyGroupSid(gpSid)) {
            return Integer.parseInt(gpSid.substring(1));
        } else {
            return Integer.parseInt(gpSid);
        }
    }

    /**
     * パラメータ.グループコンボ値がグループSIDかマイグループSIDかを判定する
     * <br>[機  能]先頭文字に"M"が有る場合、マイグループSID
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isMyGroupSid(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf("M");

        // 先頭文字に"M"が有る場合はマイグループ
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }

    /**
     * パラメータ.グループコンボ値が代表アカウントかを判定する
     * <br>[機  能]先頭文字に"sac"が有る場合は代表アカウント
     * <br>[解  説]
     * <br>[備  考]
     * @param gpSid グループSID
     * @return boolean true:マイグループ false=通常のグループ
     */
    public static boolean isSmlAccount(String gpSid) {
        boolean ret = false;
        if (gpSid == null) {
            return ret;
        }
        // 置換対象文字列が存在する場所を取得
        int index = gpSid.indexOf(GSConstSmail.SML_ACCOUNT_STR);

        // 先頭文字に"sac"が有る場合は代表アカウント
        if (index == 0) {
            return true;
        } else {
            return ret;
        }
    }



    /**   --------------------以下新規関数 --------------------        */


    /**
     * <br>[機  能] 初期表示データをセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param procMode 処理モード
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return jsonData JSONObject
     */
    public JSONObject getInitData(Sml010ParamModel paramMdl,
                             String procMode,
                             RequestModel reqMdl,
                             Connection con)
        throws SQLException {

        log__.debug("初期表示データセット");

        //アカウント情報を設定
        __setAccountInf(paramMdl, con, reqMdl);

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        long maxCount = 0;
        SmailDao sDao = new SmailDao(con);

       /************************************************************************
        *
        * 処理モード毎に対象データ件数を取得する
        *
        ************************************************************************/

        //処理モード = 受信モード
        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
            maxCount = sDao.getJmeisCount(paramMdl.getSmlViewAccount());
        //処理モード = 送信モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            maxCount = sDao.getSmeisCount(paramMdl.getSmlViewAccount());
        //処理モード = 草稿モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
            maxCount = sDao.getWmeisCount(paramMdl.getSmlViewAccount());
        //処理モード = ゴミ箱モード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
            maxCount = sDao.getGomibakoCount(paramMdl.getSmlViewAccount());
        //処理モード = ラベルモード
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            maxCount = sDao.getLabelCount(paramMdl.getSmlViewAccount(),
                                       paramMdl.getSml010SelectLabelSid());
        }



       /************************************************************************
        *
        * ページの設定
        *
        ************************************************************************/

        //設定値を取得する
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl);
        SmlAdminModel smlAdmMdl = smlCmnBiz.getSmailAdminConf(sessionUsrSid, con);
        SmlUserModel smlMdl = smlCmnBiz.getSmailUserConf(sessionUsrSid, con);

        //表示最大件数を取得する
        int limit = 0;
        if (smlAdmMdl.getSmaMaxDspStype() == GSConstSmail.DISP_CONF_ADMIN) {
            //管理者設定の表示設定を反映する
            limit = smlAdmMdl.getSmaMaxDsp();
        } else {
            //個人設定の表示設定を反映する
            limit = smlMdl.getSmlMaxDsp();
        }

        //現在ページ、スタート行
        int nowPage = paramMdl.getSml010PageNum();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }

        //写真表示フラグ
        int photoDspFlg = getPhotoDspFlg(reqMdl, con);
        paramMdl.setPhotoDspFlg(photoDspFlg);

        //添付画像表示フラグ
        int attachImgFlg = GSConstSmail.SML_IMAGE_TEMP_DSP;
        if (smlAdmMdl.getSmaAttachDspStype() == GSConstSmail.DISP_CONF_ADMIN) {
            //管理者設定の表示設定を反映する
            attachImgFlg = smlAdmMdl.getSmaAttachDsp();
        } else {
            //個人設定の表示設定を反映する
            attachImgFlg = smlMdl.getSmlTempDsp();
        }
        paramMdl.setTempDspFlg(attachImgFlg);


       /************************************************************************
        *
        * 処理モード毎に該当データ一覧を取得する
        *
        ************************************************************************/

        ArrayList<SmailModel> resultList = null;
        ArrayList<SmailModel> convList = null;

        //該当データ取得(受信モード)
        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
            resultList =
                sDao.selectJmeisList(
                    paramMdl.getSmlViewAccount(),
                    start,
                    limit,
                    paramMdl.getSml010Sort_key(),
                    paramMdl.getSml010Order_key());

            //取得データを表示形式に変換
            convList = __convertJmeisData(resultList);

        //該当データ取得(送信モード)
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            resultList =
                sDao.selectSmeisList(
                    paramMdl.getSmlViewAccount(),
                    start,
                    limit,
                    paramMdl.getSml010Sort_key(),
                    paramMdl.getSml010Order_key());

            //取得データを表示形式に変換
            convList = __convertSmeisData(resultList);

        //該当データ取得(草稿モード)
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
            resultList =
                sDao.selectWmeisList(
                    paramMdl.getSmlViewAccount(),
                    start,
                    limit,
                    paramMdl.getSml010Sort_key(),
                    paramMdl.getSml010Order_key());

            //取得データを表示形式に変換
            convList = __convertWmeisData(resultList);

        //該当データ取得(ゴミ箱モード)
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
            resultList =
                sDao.selectGomibakoList(
                    paramMdl.getSmlViewAccount(),
                    start,
                    limit,
                    paramMdl.getSml010Sort_key(),
                    paramMdl.getSml010Order_key());

            //取得データを表示形式に変換
            convList = __convertJmeisData(resultList);
        //該当データ取得(ラベルモード)
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            resultList =
                sDao.selectLabelList(
                    paramMdl.getSmlViewAccount(),
                    paramMdl.getSml010SelectLabelSid(),
                    start,
                    limit,
                    paramMdl.getSml010Sort_key(),
                    paramMdl.getSml010Order_key());

            //取得データを表示形式に変換
            convList = __convertJmeisData(resultList);
        }

       /************************************************************************
        *
        * 取得・生成した値をセットする
        *
        ************************************************************************/

        paramMdl.setSml010PageNum(nowPage);
        paramMdl.setSml010Slt_page1(nowPage);
        paramMdl.setSml010Slt_page2(nowPage);
        paramMdl.setSml010PageLabel(
            PageUtil.createPageOptions(maxCount, limit));
        paramMdl.setSml010SmlList(convList);
        paramMdl.setSml010SelectedDelSid(
                __setHiddenSelectedList(
                        paramMdl.getSml010DelSid(),
                        convList));
        BaseUserModel baseMdl = reqMdl.getSmodel();
        if (baseMdl != null) {
            CommonBiz  commonBiz = new CommonBiz();
            boolean adminUser = commonBiz.isPluginAdmin(con, baseMdl, GSConstMain.PLUGIN_ID_SMAIL);

            if (adminUser) {
                paramMdl.setAdminFlg(GSConst.USER_ADMIN);
            } else {
                paramMdl.setAdminFlg(GSConst.USER_NOT_ADMIN);
            }
        }
        paramMdl.setSml010Reload(__getReloadTime(con, sessionUsrSid, reqMdl));

        //削除日メッセージを設定
        __setDeleteMessage(con, paramMdl, paramMdl.getSmlViewAccount());

//        //ユーザ一覧設定
//        __setLeftMenu(paramMdl, con, baseMdl.getUsrsid(), reqMdl);

        //未読メール件数取得
        paramMdl.setSml010MidokuCnt(smlCmnBiz.getUnopenedMsgCnt(
                                           paramMdl.getSmlViewAccount(), con));

        //草稿メール件数取得
        paramMdl.setSml010SokoCnt(smlCmnBiz.getSokoMsgCnt(paramMdl.getSmlViewAccount(), con));

        //ゴミ箱メール未読件数取得
        paramMdl.setSml010GomiMidokuCnt(smlCmnBiz.getUnopenedGomiMsgCnt(
                                           paramMdl.getSmlViewAccount(), con));



        JSONObject jsonData = new JSONObject();
        jsonData = JSONObject.fromObject(paramMdl);

        return jsonData;
    }
    /**
     *
     * <br>[機  能] セッションユーザの利用制限されている送信先を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return 利用制限されている送信先
     * @throws SQLException SQL実行時例外
     */
    private List<String> __getBanDestUser(RequestModel reqMdl,
            Connection con) throws SQLException {
        SmlBanDestDao sbdDao = new SmlBanDestDao(con);
        List<Integer> banUsrList = sbdDao.getBanDestUsrSidList(reqMdl.getSmodel().getUsrsid());
        List<Integer> banAccList = sbdDao.getBanDestAccSidList(reqMdl.getSmodel().getUsrsid());
        List<String> banSidList = new ArrayList<String>();
        for (Integer sid : banUsrList) {
            banSidList.add(sid.toString());
        }
        for (Integer sid : banAccList) {
            banSidList.add(GSConstSmail.SML_ACCOUNT_STR + sid.toString());
        }
        return banSidList;
    }

    /**
     * <br>[機  能] グループ所属ユーザ取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return jsonData JSONObject
     */
    public JSONObject getGroupUsrData(Sml010ParamModel paramMdl,
                             RequestModel reqMdl,
                             Connection con)
        throws SQLException {

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();

        //ユーザ一覧設定
        __setLeftMenu(paramMdl, con, sessionUsrSid, reqMdl);

        //写真表示フラグ
        int photoDspFlg = getPhotoDspFlg(reqMdl, con);
        paramMdl.setPhotoDspFlg(photoDspFlg);

        JSONObject jsonData = new JSONObject();
        jsonData = JSONObject.fromObject(paramMdl);

        return jsonData;
    }

    /**
     * <br>[機  能] ひな形リストを作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setHinagataList(Sml010ParamModel paramMdl,
                                 RequestModel reqMdl,
                                 Connection con)
        throws SQLException {

        log__.debug("ひな形リスト設定");

        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = new SmlAccountModel();

        if (paramMdl.getSmlViewAccount() <= 0) {
            sacMdl = sacDao.selectFromUsrSid(reqMdl.getSmodel().getUsrsid());
        } else {
            sacMdl = sacDao.select(paramMdl.getSmlViewAccount());
        }

        paramMdl.setSmlViewAccount(sacMdl.getSacSid());

        SmlHinaDao hdao = new SmlHinaDao(con);
        List<SmlHinaModel> ret = hdao.getHinaList(paramMdl.getSmlViewAccount());
        List<SmlHinaModel> cmnList = new ArrayList<SmlHinaModel>();
        List<SmlHinaModel> kojinList = new ArrayList<SmlHinaModel>();
        if (ret != null && ret.size() > 0) {
            for (SmlHinaModel model : ret) {

                if (model.getShnHname().length() > 10) {
                    model.setShnHnameDsp(model.getShnHname().substring(0, 10) + "…");
                } else {
                    model.setShnHnameDsp(model.getShnHname());
                }

                if (model.getShnCkbn() == GSConstSmail.HINA_KBN_CMN) {
                    cmnList.add(model);
                } else if (model.getShnCkbn() == GSConstSmail.HINA_KBN_PRI) {
                    kojinList.add(model);
                }
            }
        }

        paramMdl.setSml010HinaList(cmnList);
        paramMdl.setSml010HinaListKjn(kojinList);
    }


    /**
     * <br>[機  能] ラベル一覧を作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setLabelList(Sml010ParamModel paramMdl,
                                 RequestModel reqMdl,
                                 Connection con)
        throws SQLException {

        log__.debug("ラベルリスト設定");
        SmlLabelDao labelDao = new SmlLabelDao(con);
        List<SmlLabelModel> labelList = labelDao.getLabelList(paramMdl.getSmlViewAccount());
        paramMdl.setSml010LabelList(labelList);

    }

    /**
     * 検索条件部分のグループ、ユーザコンボを生成する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setGroupUserCombo(
            Sml010ParamModel paramMdl,
            int sessionUsrSid,
            Connection con,
            RequestModel reqMdl)
    throws SQLException {

        GroupBiz cmnBiz = new GroupBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> groupLabelList = new ArrayList<LabelValueBean>();

        paramMdl.setSml090SltGroup(
                NullDefault.getString(paramMdl.getSml090SltGroup(), "-1"));

        //代表アカウント
        groupLabelList.add(
                new LabelValueBean("代表アカウント", GSConstSmail.SML_ACCOUNT_STR));

        groupLabelList.addAll(cmnBiz.getGroupCombLabelList(con, true, gsMsg));

        List<UsrLabelValueBean> userLabel = new ArrayList<UsrLabelValueBean>();

        if (paramMdl.getSml090SltGroup() != null
                && paramMdl.getSml090SltGroup().equals(GSConstSmail.SML_ACCOUNT_STR)) {
            //代表アカウントを取得
            SmlAccountDao sacDao = new SmlAccountDao(con);
            userLabel = sacDao.selectSmlAccountLv();

            //指定無し
            String textSiteiNasi = gsMsg.getMessage("cmn.without.specifying");
            userLabel.add(0, new UsrLabelValueBean(textSiteiNasi, "-1"));

        } else {
            //表示グループ・ユーザ
            int dspGpSid = NullDefault.getInt(paramMdl.getSml090SltGroup(), -1);

            //ユーザコンボ
            UserBiz uBiz = new UserBiz();
            userLabel = uBiz.getUserLabelList(con, gsMsg, dspGpSid);

        }

        paramMdl.setSml090GroupLabel(groupLabelList);
        paramMdl.setSml090UserLabel(userLabel);

    }

    /**
     * アカウント情報を設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    private void __setAccountInf(
                                Sml010ParamModel paramMdl,
                                Connection con,
                                RequestModel reqMdl)
    throws SQLException {

        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountDiskDao diskDao = new SmlAccountDiskDao(con);
        SmlAccountModel sacMdl = null;
        SmlAccountDiskModel sadMdl = null;

        //アカウントを取得
        if (paramMdl.getSmlViewAccount() <= 0) {
            //デフォルトのアカウントを取得
            sacMdl = sacDao.selectFromUsrSid(reqMdl.getSmodel().getUsrsid());
        } else {
            //選択されたアカウントを取得
            sacMdl = sacDao.select(paramMdl.getSmlViewAccount());
        }

        if (sacMdl != null) {
            //アカウント名
            paramMdl.setSmlViewAccount(sacMdl.getSacSid());

            if (sacMdl.getSacTheme() != GSConstSmail.SAC_THEME_NOSET) {
                CmnThemeDao dao = new CmnThemeDao(con);
                CmnThemeModel model = dao.select(sacMdl.getSacTheme());
                if (model.getCtmPath() != null) {
                    //アカウントテーマ
                    paramMdl.setSml010AccountTheme(model.getCtmPath());
                }
            }

            //送信メール形式
            paramMdl.setSml010AccountSendMailType(sacMdl.getSacSendMailtype());

            //アカウントディスク使用量
            sadMdl = diskDao.select(sacMdl.getSacSid());
            if (sadMdl != null) {
                paramMdl.setSml010AccountDisk(
                        String.valueOf(BigDecimal.valueOf(sadMdl.getSdsSize()).divide(
                        new BigDecimal(1024 * 1024), 1, RoundingMode.HALF_UP)));
            }
            paramMdl.setSmlViewAccount(sacMdl.getSacSid());
        }
    }

    /**
     * アカウント情報を設定
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSid ユーザSID
     * @return sacSid アカウントSID
     * @throws SQLException SQL実行時例外
     */
    public int getDefaultAccount(Connection con, int usrSid) throws SQLException {

        int accountSid = -1;

        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = null;

        //デフォルトのアカウントを取得
        sacMdl = sacDao.selectFromUsrSid(usrSid);

        if (sacMdl != null) {
            accountSid = sacMdl.getSacSid();
        }

        return accountSid;

    }

    /**
     * <br>[機  能] すべて既読・未読にする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param kbn 0:既読 1:未読
     * @throws SQLException SQL実行時例外
     */
    public void allRead(Sml010ParamModel paramMdl, RequestModel reqMdl, Connection con, int kbn)
        throws SQLException {

        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        //受信メッセージの開封区分を変更
        SmlJmeisDao jdao = new SmlJmeisDao(con);
        jdao.updateOpkbn(sessionUserSid, paramMdl.getSmlViewAccount(), kbn, new UDate());

    }

    /**
     * <br>[機  能] 選択したメールを既読・未読にする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param kbn 0:既読 1:未読
     * @throws SQLException SQL実行時例外
     */
    public void selsRead(Sml010ParamModel paramMdl, RequestModel reqMdl, Connection con, int kbn)
        throws SQLException {

        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        //受信メッセージの開封区分を変更
        SmlJmeisDao jdao = new SmlJmeisDao(con);

        if (paramMdl.getSml010DelSid() != null && paramMdl.getSml010DelSid().length > 0) {
            jdao.updateOpkbn(paramMdl.getSml010DelSid(), sessionUserSid,
                    paramMdl.getSmlViewAccount(), kbn, new UDate());
        }

    }

    /**
     * <br>[機  能] 指定したメールを既読・未読にする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param kbn 0:既読 1:未読
     * @throws SQLException SQL実行時例外
     */
    public void selRead(Sml010ParamModel paramMdl, RequestModel reqMdl, Connection con, int kbn)
        throws SQLException {

        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        //受信メッセージの開封区分を変更
        SmlJmeisDao jdao = new SmlJmeisDao(con);
        if (paramMdl.getSml010SelectedSid() > 0) {
            jdao.updateOpkbn(paramMdl.getSml010SelectedSid(),
                    sessionUserSid, paramMdl.getSmlViewAccount(), kbn, new UDate());
        }

    }

    /**
     * <br>[機  能] 指定したメールを既読・未読にする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void getLabelData(
            Sml010ParamModel paramMdl, Connection con)
        throws SQLException {
        SmlLabelDao labelDao = new SmlLabelDao(con);
        List<SmlLabelModel> labelList = labelDao.getLabelList(paramMdl.getSmlViewAccount());
        paramMdl.setSml010LabelList(labelList);
        List<LabelValueBean> labelCombo = new ArrayList<LabelValueBean>();
        for (SmlLabelModel labelData : labelList) {
            labelCombo.add(new LabelValueBean(labelData.getSlbName(),
                        String.valueOf(labelData.getSlbSid())));
        }
        paramMdl.setLabelCombo(labelCombo);
    }


    /**
     * <br>[機  能] メッセージにラベルを付加する
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl リクエスト情報
     * @param res レスポンス
     * @param con コネクション
     * @param paramMdl リクエスト情報
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setLabelForMessage(ActionMapping map,
                                    RequestModel reqMdl,
                                    HttpServletResponse res,
                                    Connection con, Sml010ParamModel paramMdl,
                                    MlCountMtController mtCon, int sessionUserSid)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String message = null;

        int type = paramMdl.getSml010addLabelType();


        int sacSid = paramMdl.getSmlViewAccount();

        List<String> errorList = new ArrayList<String>();

        try {
            String[] messageNum = paramMdl.getSml010DelSid();

            if (messageNum != null && messageNum.length != 0) {

                int labelSid = paramMdl.getSml010addLabel();
                if (type == GSConstSmail.ADDLABEL_NEW) {
                    String labelName = paramMdl.getSml010addLabelName();

                    if (StringUtil.isNullZeroString(labelName)) {
                        errorList.add(gsMsg.getMessage("wml.171"));
                    } else if (!GSValidateUtil.isGsJapaneaseString(labelName)) {
                    //JIS第2水準チェック
                        //利用不可能な文字を入力した場合
                        String nstr = GSValidateUtil.getNotGsJapaneaseString(labelName);
                        errorList.add(gsMsg.getMessage("wml.168")
                                + gsMsg.getMessage("wml.118")
                                + gsMsg.getMessage("wml.213") + nstr);
                    } else if (labelName.length() > GSConstSmail.MAXLEN_SEARCH_KEYWORD) {
                        //MAX桁チェック
                        errorList.add(
                                gsMsg.getMessage("wml.170",
                                                new String[] {String.valueOf(
                                                        GSConstSmail.MAXLEN_SEARCH_KEYWORD)}));
                    } else if (ValidateUtil.isSpace(labelName)) {
                        //スペースのみ
                        errorList.add(gsMsg.getMessage("wml.167"));
                    } else if (ValidateUtil.isSpaceStart(labelName)) {
                        //先頭スペース
                        errorList.add(gsMsg.getMessage("wml.169"));
                    } else if (ValidateUtil.isTab(labelName)) {
                        //タブ文字が含まれている
                        errorList.add(gsMsg.getMessage("cmn.notinput.tab.label"));
                    }

                    if (errorList.isEmpty()) {

                        SmlLabelDao labelDao = new SmlLabelDao(con);
                        boolean commit = false;
                        try {
                            labelSid = (int) mtCon.getSaibanNumber(GSConstSmail.SAIBAN_SML_SID,
                                                                    GSConstSmail.SBNSID_SUB_LABEL,
                                                                sessionUserSid);

                            int viewSacSid = paramMdl.getSmlViewAccount();
                            SmlLabelModel labelMdl = new SmlLabelModel();
                            labelMdl.setSlbSid(labelSid);
                            labelMdl.setUsrSid(sessionUserSid);
                            labelMdl.setSlbName(labelName);
                            labelMdl.setSlbType(GSConstSmail.LABELTYPE_ONES);
                            labelMdl.setSlbOrder(labelDao.maxSortNumber(viewSacSid) + 1);
                            labelMdl.setSacSid(viewSacSid);

                            labelDao.insert(labelMdl);
                            con.commit();
                            commit = true;

                        } catch (Exception e) {
                            log__.error("ラベルの登録に失敗", e);
                            errorList.add(gsMsg.getMessage("wml.161"));
                        } finally {
                            if (!commit) {
                                con.rollback();
                            }
                        }

                        //ログ出力
                        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
                        smlBiz.outPutLog(map, reqMdl,
                                         gsMsg.getMessage("cmn.entry"), GSConstLog.LEVEL_INFO,
                                        "[name]" + labelName);
                    }


                } else {
                    if (labelSid <= 0) {
                        errorList.add(gsMsg.getMessage("cmn.select.a.label"));
                    }

                    SmailDao smailDao = new SmailDao(con);
                    if (!smailDao.existLabel(labelSid)) {
                        errorList.add(gsMsg.getMessage("wml.192"));
                    }
                }

                if (errorList.isEmpty()) {

                    String mode = paramMdl.getSml010ProcMode();

                    //処理モード = 受信モード
                    if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                        for (String msgNum : messageNum) {
                            setLabelJushin(msgNum, labelSid, sacSid, con);
                        }

                    //処理モード = 送信モード
                    } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {

                        for (String msgNum : messageNum) {
                            setLabelSoshin(msgNum, labelSid, sacSid, con);
                        }

                    //処理モード = 草稿モード
                    } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

                        for (String msgNum : messageNum) {
                            setLabelSoko(msgNum, labelSid, sacSid, con);
                        }

                    //処理モード = ゴミ箱モード
                    } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {

                    //処理モード = ラベルモード
                    } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
                        if (paramMdl.getSml010LabelDelSid() != null
                                && paramMdl.getSml010LabelDelSid().length > 0) {
                            for (String delStr : paramMdl.getSml010LabelDelSid()) {
                                String [] delArrayStr = delStr.split(":");

                                if (delArrayStr[0].equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                                    setLabelJushin(delArrayStr[1], labelSid, sacSid, con);
                                } else if (delArrayStr[0].equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                                    setLabelSoshin("1000" + delArrayStr[1], labelSid, sacSid, con);
                                } else if (delArrayStr[0].equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                                    setLabelSoko("2000" + delArrayStr[1], labelSid, sacSid, con);
                                }
                            }
                        }
                    }
                    message = "success";
                }
            }

        } catch (Exception e) {
            log__.error("メールへのラベル追加に失敗しました。", e);
        } finally {
            if (message == null) {
                log__.debug("メッセージの追加：（ラベルの追加に失敗しました。）");
                log__.debug("登録区分：" + type);
                log__.debug("選択ラベルSID：" + paramMdl.getSml010addLabel());
                log__.debug("プロセスモード：" + paramMdl.getSml010ProcMode());
                log__.debug("選択したSID配列(length)：" + paramMdl.getSml010DelSid().length);
                log__.debug("エラーリストサイズ：" + errorList.size());
                errorList.add(gsMsg.getMessage("wml.failed.addlabel"));
            }

            if (!errorList.isEmpty()) {
                paramMdl.setErrorsList(errorList);
            }
        }
    }


    /**
     * <br>[機  能] メッセージからラベルを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl リクエスト情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void deleteLabelForMessage(Connection con, Sml010ParamModel paramMdl,
                                        RequestModel reqMdl) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);
        String message = null;

        List<String> errorList = new ArrayList<String>();

        try {
            String[] messageNum = paramMdl.getSml010DelSid();
            int labelSid = paramMdl.getSml010delLabel();

            if (messageNum == null || messageNum.length == 0) {
                errorList.add(gsMsg.getMessage("wml.plz.select.mail"));

            } else if (labelSid <= 0) {
                errorList.add(gsMsg.getMessage("cmn.select.a.label"));
            }

            SmailDao smailDao = new SmailDao(con);
            if (!smailDao.existLabel(labelSid)) {
                errorList.add(gsMsg.getMessage("wml.192"));
            }

            if (errorList.isEmpty()) {

                String mode = paramMdl.getSml010ProcMode();

                //処理モード = 受信モード
                if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                    for (String msgNum : messageNum) {
                        delLabelJushin(msgNum, labelSid, con);
                    }

                //処理モード = 送信モード
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {

                    for (String msgNum : messageNum) {
                        delLabelSoshin(msgNum, labelSid, con);
                    }

                //処理モード = 草稿モード
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

                    for (String msgNum : messageNum) {
                        delLabelSoko(msgNum, labelSid, con);
                    }

                //処理モード = ゴミ箱モード
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {

                    //処理モード = ラベルモード
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {

                    if (paramMdl.getSml010LabelDelSid() != null
                            && paramMdl.getSml010LabelDelSid().length > 0) {
                        for (String delStr : paramMdl.getSml010LabelDelSid()) {
                            String [] delArrayStr = delStr.split(":");
                            if (delArrayStr[0].equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                                delLabelJushin(delArrayStr[1], labelSid, con);
                            } else if (delArrayStr[0].equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                                delLabelSoshin("1000" + delArrayStr[1], labelSid, con);
                            } else if (delArrayStr[0].equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                                delLabelSoko("2000" + delArrayStr[1], labelSid, con);
                            }
                        }
                    }
                }
                message = "success";
            }

        } catch (Exception e) {
            log__.error("メールのラベル削除に失敗しました。", e);
        } finally {
            if (message == null) {
                errorList.add(gsMsg.getMessage("failed.deletelabel"));
            }

            if (!errorList.isEmpty()) {
                paramMdl.setErrorsList(errorList);
            }

        }
    }

    /**
     * <br>[機  能] メッセージにラベルを付加する(受信)
     * <br>[解  説]
     * <br>[備  考]
     * @param msgNum メールSID
     * @param labelSid ラベルSID
     * @param sacSid アカウントSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setLabelJushin(String msgNum, int labelSid, int sacSid, Connection con)
    throws SQLException {

        if (ValidateUtil.isNumber(msgNum) && Integer.valueOf(msgNum) > 0) {
            SmlJmeisLabelDao smlJmeisDao = new SmlJmeisLabelDao(con);
            if (!smlJmeisDao.existsJmeisLabel(Integer.valueOf(msgNum), labelSid, sacSid)) {
                SmlJmeisLabelModel smlJmeisModel = new SmlJmeisLabelModel();
                smlJmeisModel.setSlbSid(labelSid);
                smlJmeisModel.setSmjSid(Integer.valueOf(msgNum));
                smlJmeisModel.setSacSid(sacSid);
                smlJmeisDao.insert(smlJmeisModel);
            }
        }
    }

    /**
     * <br>[機  能] メッセージにラベルを付加する(送信)
     * <br>[解  説]
     * <br>[備  考]
     * @param msgNum メールSID
     * @param labelSid ラベルSID
     * @param sacSid アカウントSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setLabelSoshin(String msgNum, int labelSid, int sacSid, Connection con)
    throws SQLException {

        SmlSmeisLabelModel smlSmeisModel = new SmlSmeisLabelModel();
        SmlSmeisLabelDao smlSmeisDao = new SmlSmeisLabelDao(con);

        if (ValidateUtil.isNumber(msgNum)
                && Integer.parseInt(msgNum.substring(1)) > 0) {

            smlSmeisModel.setSlbSid(labelSid);
            smlSmeisModel.setSmsSid(Integer.parseInt(msgNum.substring(1)));
            smlSmeisModel.setSacSid(sacSid);

            smlSmeisDao.delete(Integer.parseInt(msgNum.substring(1)), labelSid);
            smlSmeisDao.insert(smlSmeisModel);
        }
    }

    /**
     * <br>[機  能] メッセージにラベルを付加する(草稿)
     * <br>[解  説]
     * <br>[備  考]
     * @param msgNum メールSID
     * @param labelSid ラベルSID
     * @param sacSid アカウントSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setLabelSoko(String msgNum, int labelSid, int sacSid, Connection con)
    throws SQLException {

        SmlWmeisLabelModel smlWmeisModel = new SmlWmeisLabelModel();
        SmlWmeisLabelDao smlWmeisDao = new SmlWmeisLabelDao(con);
        if (ValidateUtil.isNumber(msgNum)) {
            smlWmeisModel.setSlbSid(labelSid);
            smlWmeisModel.setSmwSid(Integer.parseInt(msgNum.substring(1)));
            smlWmeisModel.setSacSid(sacSid);

            smlWmeisDao.delete(Integer.parseInt(msgNum.substring(1)), labelSid);
            smlWmeisDao.insert(smlWmeisModel);
        }
    }

    /**
     * <br>[機  能] メッセージのラベルを削除する(受信)
     * <br>[解  説]
     * <br>[備  考]
     * @param msgNum メールSID
     * @param labelSid ラベルSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void delLabelJushin(String msgNum, int labelSid, Connection con)
    throws SQLException {

        SmlJmeisLabelDao smlJmeisDao = new SmlJmeisLabelDao(con);

        if (ValidateUtil.isNumber(msgNum)
                && Integer.valueOf(msgNum) > 0) {
            smlJmeisDao.delete(Integer.valueOf(msgNum), labelSid);
        }
    }

    /**
     * <br>[機  能] メッセージのラベルを削除する(送信)
     * <br>[解  説]
     * <br>[備  考]
     * @param msgNum メールSID
     * @param labelSid ラベルSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void delLabelSoshin(String msgNum, int labelSid, Connection con)
    throws SQLException {

        SmlSmeisLabelDao smlSmeisDao = new SmlSmeisLabelDao(con);

        if (ValidateUtil.isNumber(msgNum)
                && Integer.parseInt(msgNum.substring(1)) > 0) {
            smlSmeisDao.delete(Integer.parseInt(msgNum.substring(1)), labelSid);
        }
    }

    /**
     * <br>[機  能] メッセージのラベルを削除する(草稿)
     * <br>[解  説]
     * <br>[備  考]
     * @param msgNum メールSID
     * @param labelSid ラベルSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void delLabelSoko(String msgNum, int labelSid, Connection con)
    throws SQLException {

        SmlWmeisLabelDao smlWmeisDao = new SmlWmeisLabelDao(con);

        if (ValidateUtil.isNumber(msgNum)
                && Integer.parseInt(msgNum.substring(1)) > 0) {

            smlWmeisDao.delete(Integer.parseInt(msgNum.substring(1)), labelSid);
        }
    }


    /**
     * <br>[機  能] メール詳細設定(受信モード)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitDataJusin(Sml010ParamModel paramMdl,
                                  RequestModel reqMdl,
                                  Connection con)
        throws SQLException {

        log__.debug("初期表示データ(受信モード)取得");

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        SmailDao sDao = new SmailDao(con);

        List<Sml010ExportFileModel> exportList = new ArrayList<Sml010ExportFileModel>();
        Sml010ExportFileModel exportMdl = null;

        if (paramMdl.getSml010DelSid() != null
                && paramMdl.getSml010DelSid().length > 0) {

            for (String smlSid : paramMdl.getSml010DelSid()) {

                exportMdl = new Sml010ExportFileModel();

                if (GSValidateUtil.isNumber(smlSid)) {
                    //データ取得
                    ArrayList<SmailDetailModel> resultList =
                        sDao.selectJmeisDetail(
                            paramMdl.getSmlViewAccount(),
                            Integer.parseInt(smlSid),
                            GSConstSmail.SML_JTKBN_TOROKU);

                    if (!resultList.isEmpty()) {
                        //取得データを表示形式に変換
                        ArrayList<SmailDetailModel> ret =
                                       __convertMeisData(resultList, sessionUsrSid, false, con);
                        exportMdl.setSmlList(ret);

                        //送付ファイル情報を取得
                        SmailDetailModel retMl = resultList.get(0);
                        SmlBinDao binDao = new SmlBinDao(con);
                        ArrayList<CmnBinfModel> retBin = binDao.getFileList(retMl.getSmlSid());
                        exportMdl.setSmlFileList(retBin);
                    }
                    exportList.add(exportMdl);
                }
            }
            paramMdl.setSml010ExportMailList(exportList);
        }
    }

    /**
     * <br>[機  能] メール詳細設定(送信モード)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitDataSosin(Sml010ParamModel paramMdl,
                                RequestModel reqMdl,
                                Connection con)
        throws SQLException {

        log__.debug("初期表示データ(送信モード)取得");

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        SmailDao sDao = new SmailDao(con);

        List<Sml010ExportFileModel> exportList = new ArrayList<Sml010ExportFileModel>();
        Sml010ExportFileModel exportMdl = null;

        if (paramMdl.getSml010DelSid() != null
                && paramMdl.getSml010DelSid().length > 0) {

            for (String smlSid : paramMdl.getSml010DelSid()) {

                exportMdl = new Sml010ExportFileModel();

                if (GSValidateUtil.isNumber(smlSid.substring(1))) {

                    //データ取得
                    ArrayList<SmailDetailModel> resultList =
                        sDao.selectSmeisDetail(
                            paramMdl.getSmlViewAccount(),
                            Integer.parseInt(smlSid.substring(1)),
                            GSConstSmail.SML_JTKBN_TOROKU);

                    if (!resultList.isEmpty()) {
                        //取得データを表示形式に変換
                        ArrayList<SmailDetailModel> ret =
                                          __convertMeisData(resultList, sessionUsrSid, true, con);
                        exportMdl.setSmlList(ret);

                        //送付ファイル情報を取得
                        SmailDetailModel retMl = resultList.get(0);
                        paramMdl.setSml010SelectedMailKbn(retMl.getMailKbn());
                        SmlBinDao binDao = new SmlBinDao(con);
                        ArrayList<CmnBinfModel> retBin = binDao.getFileList(retMl.getSmlSid());
                        exportMdl.setSmlFileList(retBin);
                    }
                    exportList.add(exportMdl);
                }
            }
            paramMdl.setSml010ExportMailList(exportList);
        }
    }


    /**
     * <br>[機  能] メール詳細設定(ゴミ箱モード)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitDataSoko(Sml010ParamModel paramMdl,
                                RequestModel reqMdl,
                                Connection con)
        throws SQLException {

        log__.debug("初期表示データ(草稿モード)取得");

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        SmailDao sDao = new SmailDao(con);
        ArrayList<SmailDetailModel> resultList = new ArrayList<SmailDetailModel>();

        List<Sml010ExportFileModel> exportList = new ArrayList<Sml010ExportFileModel>();
        Sml010ExportFileModel exportMdl = null;

        int jtkbn = GSConstSmail.SML_JTKBN_TOROKU;

        if (paramMdl.getSml010DelSid() != null
                && paramMdl.getSml010DelSid().length > 0) {

            for (String smlSid : paramMdl.getSml010DelSid()) {
                boolean sosinFlg = false;

                exportMdl = new Sml010ExportFileModel();

                if (GSValidateUtil.isNumber(smlSid.substring(1))) {

                    //草稿
                    resultList =
                        sDao.selectWmeisDetail(
                                paramMdl.getSmlViewAccount(),
                                Integer.parseInt(smlSid.substring(1)),
                                jtkbn);
                    sosinFlg = true;
                    if (!resultList.isEmpty()) {
                        //取得データを表示形式に変換
                        ArrayList<SmailDetailModel> ret
                            = __convertMeisData(resultList, sessionUsrSid, sosinFlg, con);

                        exportMdl.setSmlList(ret);

                        //送付ファイル情報を取得
                        SmailDetailModel retMl = resultList.get(0);
                        SmlBinDao binDao = new SmlBinDao(con);
                        ArrayList<CmnBinfModel> retBin = binDao.getFileList(retMl.getSmlSid());
                        exportMdl.setSmlFileList(retBin);
                    }

                    exportList.add(exportMdl);
                }
            }
            paramMdl.setSml010ExportMailList(exportList);
        }
    }


    /**
     * <br>[機  能] メール詳細設定(ゴミ箱モード)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitDataGomi(Sml010ParamModel paramMdl,
                                RequestModel reqMdl,
                                Connection con)
        throws SQLException {

        log__.debug("初期表示データ(ゴミ箱モード)取得");

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        SmailDao sDao = new SmailDao(con);
        ArrayList<SmailDetailModel> resultList = new ArrayList<SmailDetailModel>();

        List<Sml010ExportFileModel> exportList = new ArrayList<Sml010ExportFileModel>();
        Sml010ExportFileModel exportMdl = null;

        int jtkbn = GSConstSmail.SML_JTKBN_GOMIBAKO;

        if (paramMdl.getSml010ProcMode().equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            jtkbn = GSConstSmail.SML_JTKBN_TOROKU;
        }

        if (paramMdl.getSml010DelSid() != null
                && paramMdl.getSml010DelSid().length > 0) {

            for (String smlSid : paramMdl.getSml010DelSid()) {

                String mailKbn = smlSid.substring(0, 1);
                boolean sosinFlg = false;

                exportMdl = new Sml010ExportFileModel();

                if (GSValidateUtil.isNumber(smlSid.substring(1))) {

                    //受信メール
                    if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
                        //データ取得
                        resultList =
                            sDao.selectJmeisDetail(
                                paramMdl.getSmlViewAccount(),
                                Integer.parseInt(smlSid.substring(1)),
                                jtkbn);
                    //送信メール
                    } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                        //データ取得
                        resultList =
                            sDao.selectSmeisDetail(
                                paramMdl.getSmlViewAccount(),
                                Integer.parseInt(smlSid.substring(1)),
                                jtkbn);
                        sosinFlg = true;

                    //草稿
                    } else if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                        resultList =
                            sDao.selectWmeisDetail(
                                    paramMdl.getSmlViewAccount(),
                                    Integer.parseInt(smlSid.substring(1)),
                                    jtkbn);
                        sosinFlg = true;

                    }

                    if (!resultList.isEmpty()) {
                        //取得データを表示形式に変換
                        ArrayList<SmailDetailModel> ret
                            = __convertMeisData(resultList, sessionUsrSid, sosinFlg, con);
                        exportMdl.setSmlList(ret);

                        //送付ファイル情報を取得
                        SmailDetailModel retMl = resultList.get(0);
                        SmlBinDao binDao = new SmlBinDao(con);
                        ArrayList<CmnBinfModel> retBin = binDao.getFileList(retMl.getSmlSid());
                        exportMdl.setSmlFileList(retBin);
                    }

                    exportList.add(exportMdl);
                }
            }
            paramMdl.setSml010ExportMailList(exportList);
        }
    }


    /**
     * <br>[機  能] 取得結果を変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramList 取得結果リスト
     * @param sessionUsrSid セッションユーザSID
     * @param sosinFlg 送信モードか、草稿モードならばtrue
     * @param con コネクション
     * @return 変換後リスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<SmailDetailModel> __convertMeisData(
            ArrayList<SmailDetailModel> paramList,
            int sessionUsrSid, boolean sosinFlg, Connection con)
            throws SQLException {

        ArrayList<SmailDetailModel> ret = new ArrayList<SmailDetailModel>();

        for (SmailDetailModel paramMdl : paramList) {
            SmailDetailModel retMdl = new SmailDetailModel();
            retMdl.setMailKbn(paramMdl.getMailKbn());
            retMdl.setSmlSid(paramMdl.getSmlSid());
            retMdl.setSmjOpkbn(paramMdl.getSmjOpkbn());
            retMdl.setSmsMark(paramMdl.getSmsMark());
            retMdl.setSmsTitle(
                    StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(paramMdl.getSmsTitle(), "")));
            retMdl.setSmsSdate(paramMdl.getSmsSdate());
            if (paramMdl.getSmsSdate() != null) {
                String strSdate =
                    UDateUtil.getSlashYYMD(paramMdl.getSmsSdate())
                    + "  "
                    + UDateUtil.getSeparateHMS(paramMdl.getSmsSdate());
                retMdl.setSmsSdateStr(strSdate);
            }
            String tmpBody = NullDefault.getString(paramMdl.getSmsBody(), "");
            if (paramMdl.getSmsType() == GSConstSmail.SAC_SEND_MAILTYPE_NORMAL) {
                tmpBody = StringUtilHtml.transToHTmlPlusAmparsantAndLink(tmpBody);
                tmpBody = StringUtilHtml.replaceSpecialChar(tmpBody);
                tmpBody = StringUtilHtml.removeIllegalTag(tmpBody);
            } else {
                tmpBody = SmlViewHtmlConverter.convert(tmpBody);
            }

            log__.debug("実際に書かれるurlです。" + tmpBody);
            retMdl.setSmsBody(tmpBody);
            retMdl.setSmsType(paramMdl.getSmsType());
            retMdl.setSmsEdate(paramMdl.getSmsEdate());

            retMdl.setAccountSid(paramMdl.getAccountSid());
            retMdl.setAccountName(paramMdl.getAccountName());
            retMdl.setAccountJkbn(paramMdl.getAccountJkbn());

            retMdl.setUsrSid(paramMdl.getUsrSid());
            if (paramMdl.getUsrSid() <= 0) {
                retMdl.setUsrJkbn(paramMdl.getAccountJkbn());
                retMdl.setUsiSei(NullDefault.getString(paramMdl.getUsiSei(),
                        paramMdl.getAccountName()));
                retMdl.setUsiMei(NullDefault.getString(paramMdl.getUsiMei(), ""));

            } else {
                retMdl.setUsrJkbn(paramMdl.getUsrJkbn());
                retMdl.setUsrUkoFlg(paramMdl.getUsrUkoFlg());
                retMdl.setUsiSei(NullDefault.getString(paramMdl.getUsiSei(), ""));
                retMdl.setUsiMei(NullDefault.getString(paramMdl.getUsiMei(), ""));
            }


            ArrayList<AtesakiModel> atskList = paramMdl.getAtesakiList();
            ArrayList<AtesakiModel> retAtskList = new ArrayList<AtesakiModel>();
            ArrayList<AtesakiModel> retCcList = new ArrayList<AtesakiModel>();
            ArrayList<AtesakiModel> retBccList = new ArrayList<AtesakiModel>();
            if (!atskList.isEmpty()) {
                for (AtesakiModel atskMdl : atskList) {
                    AtesakiModel dbatskMdl = new AtesakiModel();
                    if (atskMdl.getSmjOpdate() != null) {
                        String strOpdate =
                            UDateUtil.getSlashYYMD(atskMdl.getSmjOpdate())
                        + "  "
                        + UDateUtil.getSeparateHMS(atskMdl.getSmjOpdate());
                        dbatskMdl.setSmlOpdateStr(strOpdate);
                    }
                    dbatskMdl.setUsrSid(atskMdl.getUsrSid());


                    if (atskMdl.getUsrSid() > 0) {
                        dbatskMdl.setUsrJkbn(atskMdl.getUsrJkbn());
                        dbatskMdl.setUsrUkoFlg(atskMdl.getUsrUkoFlg());
                        dbatskMdl.setUsiSei(NullDefault.getString(atskMdl.getUsiSei(), ""));
                        dbatskMdl.setUsiMei(NullDefault.getString(atskMdl.getUsiMei(), ""));
                    } else {
                        dbatskMdl.setUsrJkbn(atskMdl.getAccountJkbn());
                        dbatskMdl.setUsiSei(NullDefault.getString(
                                atskMdl.getUsiSei(), atskMdl.getAccountName()));
                        dbatskMdl.setUsiMei(NullDefault.getString(atskMdl.getUsiMei(), ""));

                    }


                    dbatskMdl.setSmjFwkbn(atskMdl.getSmjFwkbn());
                    dbatskMdl.setBinFileSid(atskMdl.getBinFileSid());
                    dbatskMdl.setPhotoFileDsp(atskMdl.getPhotoFileDsp());

                    dbatskMdl.setAccountSid(atskMdl.getAccountSid());
                    dbatskMdl.setAccountName(atskMdl.getAccountName());
                    dbatskMdl.setAccountJkbn(atskMdl.getAccountJkbn());

                    if (atskMdl.getSmjSendkbn() == GSConstSmail.SML_SEND_KBN_ATESAKI) {
                        retAtskList.add(dbatskMdl);
                    } else if (atskMdl.getSmjSendkbn() == GSConstSmail.SML_SEND_KBN_CC) {
                        retCcList.add(dbatskMdl);
                    } else if (atskMdl.getSmjSendkbn() == GSConstSmail.SML_SEND_KBN_BCC) {
                        if (sosinFlg || sessionUsrSid == atskMdl.getUsrSid()) {
                            retBccList.add(dbatskMdl);
                        }
                    }
                }
            }

            retMdl.setAtesakiList(retAtskList);
            retMdl.setCcList(retCcList);
            retMdl.setBccList(retBccList);
            if (!retAtskList.isEmpty()) {
                retMdl.setListSize(retAtskList.size() - 1);
            }
            if (!retCcList.isEmpty()) {
                retMdl.setCcListSize(retCcList.size() - 1);
            }
            if (!retBccList.isEmpty()) {
                retMdl.setBccListSize(retBccList.size() - 1);
            }

            retMdl.setBinFileSid(paramMdl.getBinFileSid());
            retMdl.setPhotoFileDsp(paramMdl.getPhotoFileDsp());
            retMdl.setReturnKbn(paramMdl.getReturnKbn());
            retMdl.setFwKbn(paramMdl.getFwKbn());

            ret.add(retMdl);
        }

        return ret;
    }

    /**
     * <br>[機  能] メール内容をeml出力します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @param zipDir テンポラリディレクトパス
     * @param reqMdl リクエストモデル
     * @param topStr 先頭文字列
     * @return pdfModel SmlPdfModel
     * @throws Exception
     */
    public List<SmlPdfModel> createSmlEml(
            Sml010ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl,
            String appRootPath,
            String outTempDir,
            String zipDir,
            String topStr)
        throws Exception {

//        BaseUserModel usModel = reqMdl.getSmodel();
//        SmlPdfModel pdfModel = new SmlPdfModel();
        List<Sml010ExportFileModel> exportList = paramMdl.getSml010ExportMailList();
        List<SmlPdfModel> pdfList = new ArrayList<SmlPdfModel>();

        Sml030Biz sml030Biz = new Sml030Biz();

        if (exportList != null && !exportList.isEmpty()) {

            //重複名チェックMap
            HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
//
//            //アカウント名
//            String accName = usModel.getUsiseimei();

            String procMode = paramMdl.getSml010ProcMode();
            SmailDao smailDao = new SmailDao(con);
            String sender = null;
            for (Sml010ExportFileModel exportMdl : exportList) {

                ArrayList<SmailDetailModel> smlList = exportMdl.getSmlList();

                if (smlList != null && !smlList.isEmpty()) {
                    CrlfTerminatedWriter pw = null;
                    FileOutputStream fos = null;

                    try {

//                        //アカウント名
//                        String accName = usModel.getUsiseimei();
//                        //メールSID
//                        int smlSid = smlList.get(0).getSmlSid();
                        //件名
                        String title = StringUtilHtml.transToText(
                                smlList.get(0).getSmsTitle());
                        //差出人
                        sender = smlList.get(0).getUsiSei()
                                + " " + smlList.get(0).getUsiMei();
                        //日時
                        UDate date = smlList.get(0).getSmsSdate();
                        if (date == null) {
                            date = smlList.get(0).getSmsEdate();
                            if (date == null) {
                                date = new UDate();
                            }
                        }

                        //宛先
                        ArrayList<AtesakiModel> toList = smlList.get(0).getAtesakiList();
                        //CC
                        ArrayList<AtesakiModel> ccList = smlList.get(0).getCcList();
                        //BCC
                        ArrayList<AtesakiModel> bccList = null;

                        String mailKbn = smlList.get(0).getMailKbn();

                        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                        || mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                            bccList = smlList.get(0).getBccList();
                        }

                        if (procMode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)
                        || mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
                            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

                            ccList = smailDao.getSitagakiAtesakiList(
                                    smlList.get(0).getSmlSid(),
                                    GSConstSmail.SML_SEND_KBN_CC, sortMdl);
                            bccList = smailDao.getSitagakiAtesakiList(
                                    smlList.get(0).getSmlSid(),
                                    GSConstSmail.SML_SEND_KBN_BCC, sortMdl);
                        }
                        //添付
                        String tempFile = new String();
                        for (int i = 0; i < exportMdl.getSmlFileList().size(); i++) {
                            tempFile += exportMdl.getSmlFileList().get(i).getBinFileName()
                                    + exportMdl.getSmlFileList().get(i).getBinFileSizeDsp();
                            if (i != exportMdl.getSmlFileList().size() - 1) {
                                tempFile += " , ";
                            }
                        }
                        //本文
                        String main = smlList.get(0).getSmsBody();
//                        StringUtilHtml.transToText(main);
//                        StringUtilHtml.deleteHtmlTag(main);
//                        String convertMain = paramMdl.getSml030SmlList().get(0).getSmsBody();

                        String mailDate = UDateUtil.getSlashYYMD(date)
                                + "  "
                                + UDateUtil.getSeparateHMS(date);

                        //PDF用モデルにデータセット
                        SmlPdfModel pdfModel = new SmlPdfModel();
                        pdfModel.setTitle(title);
                        pdfModel.setSender(sender);
                        pdfModel.setDate(mailDate);
                        pdfModel.setAtesaki(__getAtesakiCV(toList, false));
                        pdfModel.setAtesakiCC(__getAtesakiCV(ccList, false));
                        pdfModel.setAtesakiBCC(__getAtesakiCV(bccList, false));
                        pdfModel.setTempFile(tempFile);
                        pdfModel.setMain(main);
                        pdfList.add(pdfModel);

                        String mailDateFile = UDateUtil.getYYMD(date) + "_"
                                + UDateUtil.getSeparateHMS(date);
                        String fileName = mailDateFile + "_";
                        String subject = title;
                        if (StringUtil.isNullZeroString(subject)) {
                            subject = "message";
                        }

                        fileName += subject;
                        //使用可能なファイル名かチェック
                        fileName = fileNameCheck(fileName);

                        if (nameMap.get(fileName.toUpperCase()) != null) {
                            int fileNum = nameMap.get(fileName.toUpperCase());
                            fileNum++;
                            nameMap.put(fileName.toUpperCase(), fileNum);
                            fileName = fileName + "(" + fileNum + ")";
                        } else {
                            nameMap.put(fileName.toUpperCase(), 0);
                        }

//                        File exportFilePath = new File(outTempDir + "/smail/"
//                                                    + reqMdl.getSession().getId() + "/export/"
//                                                    + smlSid + ".eml");
                        File exportFilePath = new File(outTempDir + fileName + ".eml");

                        String charset = Encoding.ISO_2022_JP;
                        boolean multiPart = exportMdl.getSmlFileList().size() > 0;
                        boolean mimeTypeHtml =
                            smlList.get(0).getSmsType()
                                                           == GSConstSmail.SAC_SEND_MAILTYPE_HTML;

                        try {
                            IOTools.isDirCheck(exportFilePath.getParent(), true);

                            fos = new FileOutputStream(exportFilePath);
                            pw = new CrlfTerminatedWriter(new OutputStreamWriter(fos, charset));

                            //メールヘッダ情報をファイルに書き込み
                            pw.println("Date: " + date.getIntDay()
                                        + " " + sml030Biz.getMonthStr(date.getMonth()) + " "
                                        + date.getYear() + " " + date.getIntHour() + ":"
                                        + date.getIntMinute());
                            pw.println("From: " + sml030Biz.mimeEncode(sender, "UTF-8") + " @");
                            pw.println("To: " + __getAtesakiCV(toList, true));
                            pw.println("Cc: " + __getAtesakiCV(ccList, true));

                            if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)
                            || mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                                if (bccList != null && bccList.size() > 0) {
                                    pw.println("Bcc: " + __getAtesakiCV(bccList, true));
                                }
                            }

                            pw.println("Subject: " + sml030Biz.mimeEncode(title, charset));
                            pw.println("MIME-Version: 1.0 ");

                            //メール本文を書き込み
                            if (!mimeTypeHtml && !multiPart) {
                                main = StringUtilHtml.transToText(
                                        StringUtilHtml.deleteHtmlTagAndScriptStyleBlock(
                                                       StringUtilHtml.transBRtoCRLF(main)));
                                pw.println("Content-Type: text/plain; charset=ISO-2022-JP");
                                pw.println("Content-Transfer-Encoding: 7bit");
                                pw.println("");
                                pw.println(main);
                            } else {
                                main = StringUtilHtml.transToText(main);
                                pw.println("Content-Type: multipart/mixed; ");
                                pw.println("    boundary=\"----=_Part_1");
                                pw.println("Content-Transfer-Encoding: 7bit");
                                pw.println("");

                                if (mimeTypeHtml) {
                                    if (!multiPart) {
                                        pw.println("------=_Part_1");
                                        pw.println("Content-Type: text/plain; charset=ISO-2022-JP");
                                        pw.println("Content-Transfer-Encoding: 7bit");
                                        pw.println("");
                                        pw.println(StringUtilHtml.deleteHtmlTag(
                                                             StringUtilHtml.transBRtoCRLF(main)));
                                    }
                                    pw.println("");
                                    pw.println("------=_Part_1");
                                    pw.println("Content-Type: text/html; charset=ISO-2022-JP");
                                    pw.println("Content-Transfer-Encoding: 7bit");
                                    pw.println("");
                                    pw.println(main);
                                    pw.println("");
                                } else {
                                    pw.println("------=_Part_1");
                                    pw.println("Content-Type: text/plain; charset=ISO-2022-JP");
                                    pw.println("Content-Transfer-Encoding: 7bit");
                                    pw.println("");
                                    pw.println(StringUtilHtml.deleteHtmlTag(main));
                                    pw.println("");
                                }

                                if (!multiPart) {
                                    pw.println("------=_Part_1--");
                                }
                            }

                            List<CmnBinfModel> tempFileList = new ArrayList<CmnBinfModel>();
                            tempFileList = exportMdl.getSmlFileList();


                            //添付ファイル情報を書き込み
                            if (!tempFileList.isEmpty()) {
                                ITempFileUtil tempUtil
                                    = (ITempFileUtil) GroupSession.getContext().get(
                                                                 GSContext.TEMP_FILE_UTIL);
                                String filePath = null;
                                List<CmnBinfModel> smlTmpFileList = new ArrayList<CmnBinfModel>();

//                                int fileIdx = 1;
                                for (CmnBinfModel fileData : tempFileList) {

                                    CommonBiz cmnBiz = new CommonBiz();
                                    CmnBinfModel smlTmpFileMdl;
                                    smlTmpFileMdl = cmnBiz.getBinInfo(con, fileData.getBinSid(),
                                            reqMdl.getDomain());

                                    smlTmpFileList.add(smlTmpFileMdl);

                                    filePath
                                        = tempUtil.getDownloadFile(
                                                smlTmpFileMdl, appRootPath).getPath();

                                    pw.println("");


                                    pw.println("------=_Part_1");
                                    String filename = fileData.getBinFileName();

                                    MimeBodyPart body = MailUtil.createTempFileBody(
                                            new File(filePath), filename, charset);

                                    pw.print("Content-Type: ");
                                    String ct = body.getContentType();
                                    if (ct != null) {
                                        pw.println(ct);
                                    }
                                    pw.println("Content-Transfer-Encoding: base64");
                                    pw.print("Content-Disposition: ");

                                    String cd = body.getHeader("Content-Disposition", ":");
                                    if (cd != null) {
                                        pw.println(cd);
                                    }

                                    pw.println("");

                                    FileInputStream fis = null;
                                    try {
                                        fis = new FileInputStream(filePath);
                                        byte[] buff = new byte[54];
                                        int len = 0;
                                        while ((len = fis.read(buff, 0, buff.length)) != -1) {
                                            if (buff.length > len) {
                                                byte[] newBuff = new byte[len];
                                                System.arraycopy(buff, 0, newBuff, 0, len);
                                                buff = newBuff;
                                                newBuff = null;
                                            }
                                            pw.println(
                                                new String(Base64.encodeBase64(buff)));
                                        }

                                    } finally {
                                        if (fis != null) {
                                            fis.close();
                                        }
                                    }

//                                    fileIdx++;
                                }

                            if (multiPart) {
                                pw.println("------=_Part_1--");
                            }

                                for (CmnBinfModel smlTmpFileMdl : smlTmpFileList) {
                                    smlTmpFileMdl.removeTempFile();
                                }
                                smlTmpFileList = null;
                            }

                            pw.flush();

                        } catch (IOToolsException e) {
                            log__.error("ファイルの書き込みに失敗(Eml)", e);
                        } catch (TempFileException e) {
                            log__.error("添付ファイルの書き込みに失敗(Eml)", e);
                        }

                    } finally {
                        if (pw != null) {
                            pw.close();
                        }
                        if (fos != null)  {
                            fos.close();
                        }
                    }

                }
            }


            IOTools.isDirCheck(zipDir, true);
            String saveFilePath = zipDir + "/" + topStr + "smailEml.zip";
            ZipUtil.zipDir(outTempDir + "/", saveFilePath);
            SmlPdfModel first = pdfList.get(0);
            first.setFileName(topStr + "smailEml.zip");
            first.setSaveFileName(saveFilePath);


        }
        return pdfList;
    }

    /**
     * <br>[機  能] メール内容をPDF出力します。
     * <br>[解  説] 先頭文字列にのみファイル名を格納
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param outTempDir テンポラリディレクトパス
     * @param zipDir テンポラリディレクトパス
     * @param reqMdl リクエストモデル
     * @param topStr 先頭文字列
     * @return pdfList List<SmlPdfModel>
     * @throws IOException IO実行時例外
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ZIP作成時例外
     */
    public List<SmlPdfModel> createSmlPdfList(
            Sml010ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl,
            String appRootPath,
            String outTempDir,
            String zipDir,
            String topStr)
        throws IOException, SQLException, IOToolsException {

        BaseUserModel usModel = reqMdl.getSmodel();
        List<Sml010ExportFileModel> exportList = paramMdl.getSml010ExportMailList();
        List<SmlPdfModel> pdfList = new ArrayList<SmlPdfModel>();

        if (exportList != null && !exportList.isEmpty()) {

            //重複名チェックMap
            HashMap<String, Integer> nameMap = new HashMap<String, Integer>();

            //アカウント名
            String accName = usModel.getUsiseimei();

            SmailDao smailDao = new SmailDao(con);
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

            for (Sml010ExportFileModel exportMdl : exportList) {

                OutputStream oStream = null;

                ArrayList<SmailDetailModel> smlList = exportMdl.getSmlList();

                if (smlList != null && !smlList.isEmpty()) {
                    //件名
                    String title = StringUtilHtml.transToText(
                            smlList.get(0).getSmsTitle());
                    //差出人
                    String sender = smlList.get(0).getUsiSei()
                            + " " + smlList.get(0).getUsiMei();
                    //日時
                    String date = smlList.get(0).getSmsSdateStr();
                    UDate bookDate = smlList.get(0).getSmsSdate();
                    if (bookDate == null) {
                        bookDate = smlList.get(0).getSmsEdate();
                        if (bookDate != null) {
                            date = UDateUtil.getSlashYYMD(bookDate)
                            + "  "
                            + UDateUtil.getSeparateHMS(bookDate);
                        }
                    }

                    String atesaki = __getAtesakiCV(smlList.get(0).getAtesakiList(), false);
                    //CC
                    String atesakiCC = __getAtesakiCV(smlList.get(0).getCcList(), false);

                    String atesakiBCC = null;
                    String mailKbn = smlList.get(0).getMailKbn();
                    //送信区分のみ
                    if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                        //BCC
                        atesakiBCC = __getAtesakiCV(smlList.get(0).getBccList(), false);
                    }

                    //草稿の場合、CCとBCCを設定
                    if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                        ArrayList<AtesakiModel> ccList
                            = smailDao.getSitagakiAtesakiList(
                                    smlList.get(0).getSmlSid(),
                                    GSConstSmail.SML_SEND_KBN_CC,
                                    sortMdl);
                        ArrayList<AtesakiModel> bccList
                            = smailDao.getSitagakiAtesakiList(
                                    smlList.get(0).getSmlSid(),
                                    GSConstSmail.SML_SEND_KBN_BCC,
                                    sortMdl);
                        //CC
                        atesakiCC = __getAtesakiCV(ccList, false);
                        //BCC
                        atesakiBCC = __getAtesakiCV(bccList, false);
                    }

                    //マーク
                    int mark = smlList.get(0).getSmsMark();
                    //添付
                    String tempFile = new String();
                    for (int i = 0; i < exportMdl.getSmlFileList().size(); i++) {
                        tempFile += exportMdl.getSmlFileList().get(i).getBinFileName()
                                + exportMdl.getSmlFileList().get(i).getBinFileSizeDsp();
                        if (i != exportMdl.getSmlFileList().size() - 1) {
                            tempFile += " , ";
                        }
                    }
                    //本文
                    String main = smlList.get(0).getSmsBody();
//                    StringUtilHtml.transToText(main);
//                    StringUtilHtml.deleteHtmlTag(main);
//                    String convertMain = paramMdl.getSml030SmlList().get(0).getSmsBody();

                    main = StringUtilHtml.transToText(
                            StringUtilHtml.deleteHtmlTagAndScriptStyleBlock(
                                    StringUtilHtml.transBRtoCRLF(main)));


                    //PDF用モデルにデータセット
                    SmlPdfModel pdfModel = new SmlPdfModel();
                    pdfModel.setAccName(accName);
                    pdfModel.setTitle(title);
                    pdfModel.setSender(sender);
                    pdfModel.setDate(date);
                    pdfModel.setAtesaki(atesaki);
                    pdfModel.setAtesakiCC(atesakiCC);
                    pdfModel.setAtesakiBCC(atesakiBCC);
                    pdfModel.setMark(mark);
                    pdfModel.setTempFile(tempFile);
                    pdfModel.setMain(main);

                    String bookName = UDateUtil.getYYMD(bookDate)
                            + "_" + UDateUtil.getSeparateHMS(bookDate)
                            + "_" + pdfModel.getTitle();

                    //使用可能なファイル名かチェック
                    bookName = fileNameCheck(bookName);

                    if (nameMap.get(bookName.toUpperCase()) != null) {
                        int fileNum = nameMap.get(bookName.toUpperCase());
                        fileNum++;
                        nameMap.put(bookName.toUpperCase(), fileNum);
                        bookName = bookName + "(" + fileNum + ")";
                    } else {
                        nameMap.put(bookName.toUpperCase(), 0);
                    }

                    String outBookName = bookName + ".pdf";
                    pdfModel.setFileName(outBookName);

//                    String saveFileName = String.valueOf(
//                            smlList.get(0).getSmlSid()) + ".pdf";
                    String saveFileName = String.valueOf(
                            outBookName);
                    pdfModel.setSaveFileName(saveFileName);

                    try {
                        IOTools.isDirCheck(outTempDir, true);
                        oStream = new FileOutputStream(outTempDir + saveFileName);
                        SmlPdfUtil util = new SmlPdfUtil();
                        util.createSmalPdf(pdfModel, appRootPath, oStream);
                    } catch (Exception e) {
                        log__.error("メール内容PDF出力に失敗しました。", e);
                    } finally {
                        if (oStream != null) {
                            oStream.flush();
                            oStream.close();
                        }
                    }
                    pdfList.add(pdfModel);
                    log__.debug("メール内容PDF出力を終了します。");
                }
            }


            IOTools.isDirCheck(zipDir, true);
            String saveFilePath = zipDir + "/" + topStr + "smailPdf.zip";
            ZipUtil.zipDir(outTempDir + "/", saveFilePath);
            SmlPdfModel first = pdfList.get(0);
            first.setFileName(topStr + "smailPdf.zip");
            first.setSaveFileName(saveFilePath);


        }
        return pdfList;
    }
    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考] OSチェック未実装
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    public String fileNameCheck(String fileName) {
            String escName = fileName;

            escName = StringUtilHtml.replaceString(escName, "/", "");
            escName = StringUtilHtml.replaceString(escName, "\\", ""); //\
            escName = StringUtilHtml.replaceString(escName, "?", "");
            escName = StringUtilHtml.replaceString(escName, "*", "");
            escName = StringUtilHtml.replaceString(escName, ":", "");
            escName = StringUtilHtml.replaceString(escName, "|", "");
            escName = StringUtilHtml.replaceString(escName, "\"", ""); //"
            escName = StringUtilHtml.replaceString(escName, "<", "");
            escName = StringUtilHtml.replaceString(escName, ">", "");
            escName = StringUtilHtml.replaceString(escName, ".", "");
            escName = StringUtil.trimRengeString(escName, 251); //ファイル名＋拡張子=255文字以内

        return escName;
    }

    /**
     * <br>[機  能] 写真表示フラグを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return 写真表示フラグ 0:表示 1:非表示
     */
    public int getPhotoDspFlg(
            RequestModel reqMdl,
            Connection con)
                    throws SQLException {
        //写真表示フラグ
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl);
        SmlAdminModel smlAdmMdl = smlCmnBiz.getSmailAdminConf(sessionUsrSid, con);
        SmlUserModel smlUsrMdl = smlCmnBiz.getSmailUserConf(sessionUsrSid, con);
        int photoDspFlg = GSConstSmail.SML_PHOTO_DSP_DSP;
        if (smlAdmMdl.getSmaPhotoDspStype() == GSConstSmail.DISP_CONF_ADMIN) {
            //管理者設定の表示設定を反映する
            photoDspFlg = smlAdmMdl.getSmaPhotoDsp();
        } else {
            //個人設定の表示設定を反映する
            photoDspFlg = smlUsrMdl.getSmlPhotoDsp();
        }
        return photoDspFlg;
    }
    /**
    *
    * <br>[機  能] 宛先情報からユーザアカウント、代表アカウントを識別してアカウント名を取り出す
    * <br>[解  説]
    * <br>[備  考]
    * @param atk 宛先情報
    * @param mimeEncode エンコード
    * @return アカウント名
     * @throws UnsupportedEncodingException エンコード失敗時例外
    */
    private String __getAccountName(AtesakiModel atk,
            boolean mimeEncode) throws UnsupportedEncodingException {
        String ret = "";
        if (atk == null) {
            return ret;
        }
        Sml030Biz sml030Biz = new Sml030Biz();

        if (atk.getUsrSid() > 0) {
            ret = NullDefault.getString(atk.getUsiSei(), "")
                    + " " + NullDefault.getString(atk.getUsiMei(), "");
            if (mimeEncode) {
                ret = sml030Biz.mimeEncode(ret, "UTF-8");
            }
            return ret;
        }
        ret = NullDefault.getString(atk.getAccountName(), "");
        if (mimeEncode) {
            ret = sml030Biz.mimeEncode(ret, "UTF-8");
        }
        return ret;
    }
    /**
    *
    * <br>[機  能] 宛先情報からユーザアカウント、代表アカウントを識別してアカウント名を取り出す
    * <br>[解  説] カンマ区切りで連結される
    * <br>[備  考]
    * @param atkList 宛先情報
    * @param mimeEncode エンコード
    * @return アカウント名
     * @throws UnsupportedEncodingException エンコード失敗時例外
    */
    private String __getAtesakiCV(List<AtesakiModel> atkList,
            boolean mimeEncode) throws UnsupportedEncodingException {
       StringBuilder sb = new StringBuilder("");
       boolean notFirst = false;
       if (atkList == null) {
           return sb.toString();
       }
       for (AtesakiModel atk : atkList) {
           if (notFirst) {
               sb.append(" , ");
           }
           sb.append(__getAccountName(atk, mimeEncode));
           notFirst = true;
       }
       return sb.toString();
   }

    /**
     * <br>[機  能] 指定したプラグインが使用可否を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Sml010ParamModel
     * @param reqMdl RequestModel
     * @param pconfig PluginConfig
     * @param con コネクション
     * @param pluginId プラグインID
     * @throws SQLException SQL実行時例外
     */
    public void getSharePluginCheck(Sml010ParamModel paramMdl, RequestModel reqMdl,
            PluginConfig pconfig, Connection con, String pluginId)
    throws SQLException {

        int pluginUse = GSConst.PLUGIN_NOT_USE;
        if (pconfig.getPlugin(pluginId) != null) {
            pluginUse = GSConst.PLUGIN_USE;
        }
        WmlDao wmlDao = new WmlDao(con);
        int userSid =  reqMdl.getSmodel().getUsrsid();
        boolean accountHave = wmlDao.getCanUseAccount(userSid);
        if (!accountHave) {
            accountHave = wmlDao.getCanUseAccountDairi(userSid);
        }
        if (pluginUse == 0 && accountHave) {
            paramMdl.setSml010webmailShareFlg(0);
        }
    }

    /**
     * <br>[機  能] ログ用メッセージ取得 既読未読
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sid メールSID
     * @param con コネクション
     * @return メッセージ
     * @throws SQLException SQL実行時例外
     */
    public String getLogReadMessage(
            int sid, Connection con)
        throws SQLException {

        String msg = "";
        //受信メッセージの開封区分を変更
        SmlSmeisDao sdao = new SmlSmeisDao(con);
        if (sid > 0) {
            SmlSmeisModel mdl = new SmlSmeisModel();
            mdl.setSmsSid(sid);
            SmlSmeisModel rs = sdao.select(mdl);
            msg += rs.getSmsTitle();
        }
        return msg;

    }

    /**
     * <br>[機  能] ログ用メッセージ取得 既読未読
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sidList メールSIDリスト
     * @param con コネクション
     * @return メッセージ
     * @throws SQLException SQL実行時例外
     */
    public String getLogReadMultiMessage(
            String[] sidList, Connection con)
        throws SQLException {

        String msg = "";
        //受信メッセージの開封区分を変更
        SmlSmeisDao sdao = new SmlSmeisDao(con);
        for (String sid : sidList) {
            SmlSmeisModel mdl = new SmlSmeisModel();
            mdl.setSmsSid(Integer.parseInt(sid));
            SmlSmeisModel rs = sdao.select(mdl);
            if (msg.length() > 0) {
                msg += "\r\n";
            }
            msg += rs.getSmsTitle();
        }
        return msg;

    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl) {
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        return smlCmnBiz.getTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void deleteTempDir(RequestModel reqMdl) {
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        smlCmnBiz.deleteTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを初期化
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void clearTempDir(RequestModel reqMdl) throws IOToolsException {
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        smlCmnBiz.clearTempDir(reqMdl, SCR_ID);
    }
}