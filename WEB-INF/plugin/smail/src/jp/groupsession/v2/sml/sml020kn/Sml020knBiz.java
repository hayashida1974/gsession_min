package jp.groupsession.v2.sml.sml020kn;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.xml.sax.SAXException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.TempFileModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.SmlMailFileModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.biz.SmlUsedDataBiz;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAsakDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlBodyBinDao;
import jp.groupsession.v2.sml.dao.SmlHinaDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlUserSearchDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.model.SmailSendModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.model.SmlAsakModel;
import jp.groupsession.v2.sml.model.SmlBinModel;
import jp.groupsession.v2.sml.model.SmlHinaModel;
import jp.groupsession.v2.sml.model.SmlJmeisModel;
import jp.groupsession.v2.sml.model.SmlSmeisModel;
import jp.groupsession.v2.sml.model.SmlWmeisModel;
import jp.groupsession.v2.sml.sml010.Sml010Biz;
import jp.groupsession.v2.sml.sml020.Sml020Biz;
import jp.groupsession.v2.sml.sml240.Sml240Dao;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] ショートメール作成確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml020knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml020knBiz.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     *@param reqMdl リクエスト情報
     */
    public Sml020knBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] セッションユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @return sessionUsrSid セッションユーザSID
     */
    private int __getSessionUserSid(RequestModel reqMdl) {

        log__.debug("セッションユーザSID取得");

        int sessionUsrSid = -1;

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        if (usModel != null) {
            sessionUsrSid = usModel.getUsrsid();
        }

        return sessionUsrSid;
    }

    /**
     * <br>[機  能] 差出人名称を設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setSendAccount(Sml020knParamModel paramMdl,
            Connection con)
                    throws SQLException {

        log__.debug("差出人名称設定");

        int selectAccountSid = paramMdl.getSml020SendAccount();
        String accountName = "";
        SmlAccountDao aDao = new SmlAccountDao(con);
        SmlAccountModel accountMdl = aDao.select(selectAccountSid);
        accountName = accountMdl.getSacName();

        paramMdl.setSml020knSendAccount(accountName);
    }


    /**
     * <br>[機  能] 宛先名称一覧を設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setAtesaki(Sml020knParamModel paramMdl,
            Connection con)
                    throws SQLException {

        log__.debug("宛先名称設定");

        String[] userSid = paramMdl.getSml020userSid();
        if (userSid == null || userSid.length < 1) {
            return;
        }

        ArrayList<AtesakiModel> ret = new ArrayList<AtesakiModel>();

        List<String> newUserSid = new ArrayList<String>();
        List<String> accountUserSid = new ArrayList<String>();

        for (String usid : userSid) {
            if (usid.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                //作成アカウント
                accountUserSid.add(usid.substring(GSConstSmail.SML_ACCOUNT_STR.length()));
            } else {
                //GSユーザ
                newUserSid.add(usid);
            }
        }

        if (!newUserSid.isEmpty()) {
            SmlUserSearchDao udao = new SmlUserSearchDao(con);
            ret.addAll(udao.getUserDataFromSidList(
                    (String[]) newUserSid.toArray(new String[newUserSid.size()])));
        }

        if (!accountUserSid.isEmpty()) {
            SmlUserSearchDao udao = new SmlUserSearchDao(con);
            ret.addAll(udao.getAccountDataFromSidList(
                    (String[]) accountUserSid.toArray(new String[accountUserSid.size()])));
        }


        SmailModel sMdl = new SmailModel();
        sMdl.setAtesakiList(ret);
        sMdl.setListSize(ret.size() - 1);

        paramMdl.setSml020Atesaki(sMdl);
    }

    /**
     * <br>[機  能] CC名称一覧を設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setAtesakiCc(Sml020knParamModel paramMdl, Connection con)
            throws SQLException {

        log__.debug("CC名称設定");

        String[] userSid = paramMdl.getSml020userSidCc();

        if (userSid == null || userSid.length < 1) {
            return;
        }
        ArrayList<AtesakiModel> ret = new ArrayList<AtesakiModel>();

        List<String> newUserSid = new ArrayList<String>();
        List<String> accountUserSid = new ArrayList<String>();

        for (String usid : userSid) {
            if (usid.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                //作成アカウント
                accountUserSid.add(usid.substring(GSConstSmail.SML_ACCOUNT_STR.length()));
            } else {
                //GSユーザ
                newUserSid.add(usid);
            }
        }

        if (!newUserSid.isEmpty()) {
            SmlUserSearchDao udao = new SmlUserSearchDao(con);
            ret.addAll(udao.getUserDataFromSidList(
                    (String[]) newUserSid.toArray(new String[newUserSid.size()])));
        }

        if (!accountUserSid.isEmpty()) {
            SmlUserSearchDao udao = new SmlUserSearchDao(con);
            ret.addAll(udao.getAccountDataFromSidList(
                    (String[]) accountUserSid.toArray(new String[accountUserSid.size()])));
        }

        SmailModel sMdl = new SmailModel();
        sMdl.setAtesakiList(ret);
        sMdl.setListSize(ret.size() - 1);

        paramMdl.setSml020AtesakiCc(sMdl);
    }

    /**
     * <br>[機  能] BCC名称一覧を設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setAtesakiBcc(Sml020knParamModel paramMdl, Connection con)
            throws SQLException {

        log__.debug("BCC名称設定");

        String[] userSid = paramMdl.getSml020userSidBcc();

        if (userSid == null || userSid.length < 1) {
            return;
        }
        ArrayList<AtesakiModel> ret = new ArrayList<AtesakiModel>();

        List<String> newUserSid = new ArrayList<String>();
        List<String> accountUserSid = new ArrayList<String>();

        for (String usid : userSid) {
            if (usid.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                //作成アカウント
                accountUserSid.add(usid.substring(GSConstSmail.SML_ACCOUNT_STR.length()));
            } else {
                //GSユーザ
                newUserSid.add(usid);
            }
        }

        if (!newUserSid.isEmpty()) {
            SmlUserSearchDao udao = new SmlUserSearchDao(con);
            ret.addAll(udao.getUserDataFromSidList(
                    (String[]) newUserSid.toArray(new String[newUserSid.size()])));
        }

        if (!accountUserSid.isEmpty()) {
            SmlUserSearchDao udao = new SmlUserSearchDao(con);
            ret.addAll(udao.getAccountDataFromSidList(
                    (String[]) accountUserSid.toArray(new String[accountUserSid.size()])));
        }

        SmailModel sMdl = new SmailModel();
        sMdl.setAtesakiList(ret);
        sMdl.setListSize(ret.size() - 1);

        paramMdl.setSml020AtesakiBcc(sMdl);
    }

    /**
     * <br>[機  能] ひな形SIDからひな形データを取得し設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setHinagataData(Sml020knParamModel paramMdl,
            Connection con)
                    throws SQLException {

        log__.debug("ひな形データ設定");

        SmlHinaModel param = new SmlHinaModel();
        param.setShnSid(paramMdl.getSml020SelectHinaId());

        SmlHinaDao hdao = new SmlHinaDao(con);
        SmlHinaModel ret = hdao.select(param);

        if (ret != null) {
            //件名
            paramMdl.setSml020Title(NullDefault.getString(ret.getShnTitle(), ""));
            //マーク
            paramMdl.setSml020Mark(ret.getShnMark());
            //本文
            paramMdl.setSml020Body(NullDefault.getString(ret.getShnBody(), ""));
        }
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
    public void setHinagataList(Sml020knParamModel paramMdl,
            RequestModel reqMdl,
            Connection con)
                    throws SQLException {

        log__.debug("ひな形リスト設定");

        SmlHinaDao hdao = new SmlHinaDao(con);
        List<SmlHinaModel> ret = hdao.select(paramMdl.getSmlViewAccount());
        paramMdl.setSml020HinaList(ret);
    }

    /**
     * <br>[機  能] 添付ファイル情報をセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリ
     * @param con コネクション
     * @throws IOToolsException
     * @throws IOToolsException ファイルアクセス時例外
     */
    @SuppressWarnings("unchecked")
    public void setTempFiles(Sml020knParamModel paramMdl, String tempDir, Connection con)
            throws IOToolsException {

        /** 画面に表示するファイルのリストを作成、セット **********************/
        CommonBiz commonBiz = new CommonBiz();
        List<LabelValueBean> sortList = commonBiz.getTempFileLabelList(tempDir);
        Collections.sort(sortList);
        paramMdl.setSml020FileLabelList(sortList);

    }


    /**
     * <br>[機  能] メールSIDから引用するメールデータを設定する
     * <br>[解  説]
     * <br>[備  考] 下書きから作成モードの処理
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param appRootPath アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setMailDataFromSitagaki(Sml020knParamModel paramMdl,
            RequestModel reqMdl,
            Connection con,
            String appRootPath,
            String tempDir,
            String domain)
                    throws SQLException, IOToolsException, IOException, TempFileException {

        log__.error("引用するメールデータ設定(草稿から作成モード)");

        SmailDao sdao = new SmailDao(con);
        ArrayList<SmailDetailModel> ret =
                sdao.selectWmeisDetail(
                        __getSessionUserSid(reqMdl),
                        paramMdl.getSml010SelectedSid(),
                        GSConst.JTKBN_TOROKU);

        if (!ret.isEmpty()) {
            SmailDetailModel sMdl = ret.get(0);
            //件名
            paramMdl.setSml020Title(NullDefault.getString(sMdl.getSmsTitle(), ""));
            //マーク
            paramMdl.setSml020Mark(sMdl.getSmsMark());
            //本文
            paramMdl.setSml020Body(NullDefault.getString(sMdl.getSmsBody(), ""));
            //メール形式
            paramMdl.setSml020MailType((NullDefault.getInt(String.valueOf(sMdl.getSmsType()),
                    GSConstSmail.SAC_SEND_MAILTYPE_NORMAL)));

            //添付ファイル情報
            SmlBinDao sbinDao = new SmlBinDao(con);
            List<SmlBinModel> binList = sbinDao.getBinList(sMdl.getSmlSid());

            //添付ファイルがあるなるならばテンポラリにコピー
            if (!binList.isEmpty()) {
                __tempFileCopy(binList, appRootPath, tempDir, con, domain);
            }
        }
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
    private void __tempFileCopy(List<SmlBinModel> binList,
            String appRootPath,
            String tempDir,
            Connection con,
            String domain)
                    throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        UDate now = new UDate();
        String dateStr = now.getDateString();
        int i = 1;
        for (SmlBinModel retBinMdl : binList) {
            CmnBinfModel binMdl = cmnBiz.getBinInfo(con, retBinMdl.getBinSid(), domain);
            if (binMdl != null) {
                cmnBiz.saveTempFile(dateStr, binMdl, appRootPath, tempDir, i);
                i++;
            }
        }
    }


    /**
     * <br>[機  能] 作成されたメールデータを登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param ctrl 採番用コネクション
     * @param appRootPath アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param pluginConfig PluginConfig
     * @return sendMdl 送信情報
     * @throws ClassNotFoundException 指定されたリスナークラスが存在しない
     * @throws IllegalAccessException リスナー実装クラスのインスタンス生成に失敗
     * @throws InstantiationException リスナー実装クラスのインスタンス生成に失敗
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public SmailSendModel insertMailData(
            Sml020knParamModel paramMdl,
            RequestModel reqMdl,
            Connection con,
            MlCountMtController ctrl,
            String appRootPath,
            String tempDir,
            PluginConfig pluginConfig)
                    throws
                    ClassNotFoundException,
                    IllegalAccessException,
                    InstantiationException,
                    SQLException,
                    IOToolsException,
                    IOException,
                    TempFileException,
                    TransformerException,
                    SAXException,
                    ParserConfigurationException,
                    TransformerConfigurationException {

        SmailSendModel sendMdl = new SmailSendModel();


        log__.debug("DBに登録");

        int usrSid = __getSessionUserSid(reqMdl);
        UDate now = new UDate();
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl__);
        SmlAdminModel adminConf = smlCmnBiz.getSmailAdminConf(usrSid, con);
        CommonBiz biz = new CommonBiz();

        SmlAccountDao sacDao = new SmlAccountDao(con);
        sendMdl.setSacMdl(
            sacDao.select(
                paramMdl.getSml020SendAccount()
                )
            );
    

        //添付ファイルを登録
        List<String> binList =
                biz.insertBinInfo(con, tempDir, appRootPath, ctrl, usrSid, now);

        //SID採番
        int mailSid =
                (int) ctrl.getSaibanNumber(
                        GSConstSmail.SAIBAN_SML_SID,
                        GSConstSmail.SAIBAN_SUB_MAIL_SID,
                        usrSid);

        //メールサイズ取得
        Long titile_byte = Long.valueOf(0);
        Long body_byte = Long.valueOf(0);
        Long file_byte = Long.valueOf(0);
        Long bodyFile_byte = Long.valueOf(0);

        String bodyStr = "";
        String bodyPlainStr = "";

        if (paramMdl.getSml020MailType() == GSConstSmail.SAC_SEND_MAILTYPE_NORMAL) {
            bodyStr = paramMdl.getSml020Body();
        } else {
            bodyStr = paramMdl.getSml020BodyHtml();
            bodyStr = StringUtilHtml.replaceString(bodyStr, "&", "&amp;");
            bodyStr = StringUtilHtml.replaceString(bodyStr, "&amp;amp;", "&amp;");
            bodyStr = StringUtilHtml.replaceString(bodyStr, "&amp;lt;", "&lt;");
            bodyStr = StringUtilHtml.replaceString(bodyStr, "&amp;gt;", "&gt;");
            bodyStr = StringUtilHtml.replaceString(bodyStr, "&amp;quot;", "&quot;");
            Sml020Biz sml020Biz = new Sml020Biz(reqMdl);
            ArrayList<String> fileDirList = new ArrayList<String>();
            ArrayList<String> tempDirList = new ArrayList<String>();
            bodyStr = sml020Biz.getSmlBodyFileList(mailSid,
                    bodyStr, con, ctrl, appRootPath, tempDir, fileDirList, tempDirList);
            bodyPlainStr = StringUtilHtml.deleteHtmlTag(bodyStr);
            for (String dir : tempDirList) {
                bodyFile_byte += biz.getTempFileSize(dir);
            }
        }

        try {
            if (paramMdl.getSml020Title().getBytes("UTF-8").length != 0) {
                titile_byte = Long.valueOf(
                        paramMdl.getSml020Title().getBytes("UTF-8").length);
            }
        } catch (UnsupportedEncodingException e) {
            log__.error("文字のバイト数取得に失敗");
            titile_byte = Long.valueOf(
                    paramMdl.getSml020Title().getBytes().length);
        }

        try {
            if (bodyStr.getBytes("UTF-8").length != 0) {
                body_byte = Long.valueOf(
                        bodyStr.getBytes("UTF-8").length);
            }
        } catch (UnsupportedEncodingException e) {
            log__.error("文字のバイト数取得に失敗");
            body_byte = Long.valueOf(
                    bodyStr.getBytes().length);
        }

        file_byte = biz.getTempFileSize(tempDir);

        //送信テーブルにデータ作成
        SmlSmeisModel sparam = new SmlSmeisModel();
        sparam.setSacSid(paramMdl.getSml020SendAccount());
        sparam.setSmsSid(mailSid);
        sparam.setSmsSdate(now);
        sparam.setSmsTitle(paramMdl.getSml020Title());
        sparam.setSmsMark(paramMdl.getSml020Mark());
        sparam.setSmsBody(bodyStr);
        sparam.setSmsBodyPlain(bodyPlainStr);
        sparam.setSmsType(paramMdl.getSml020MailType());
        long addSize = titile_byte + body_byte + file_byte + bodyFile_byte;
        sparam.setSmsSize(addSize);
        sparam.setSmsType(paramMdl.getSml020MailType());
        sparam.setSmsJkbn(GSConst.JTKBN_TOROKU);
        sparam.setSmsAuid(usrSid);
        sparam.setSmsAdate(now);
        sparam.setSmsEuid(usrSid);
        sparam.setSmsEdate(now);
        SmlSmeisDao sdao = new SmlSmeisDao(con);
        sdao.insert(sparam);

        SmlJmeisDao jdao = new SmlJmeisDao(con);

        /** 返信・全返信時には受信メールにわかるようにフィールドデータ変更  **/
        int editMailSid = paramMdl.getSml010EditSid();
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)
                || paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)) {
            int kbn = Integer.parseInt(paramMdl.getSml020ProcMode());
            jdao.updateHenshin(kbn, paramMdl.getSmlViewAccount(), editMailSid);
        }

        /** 転送時には受信メールにわかるようにフィールドデータ変更  **/
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_TENSO)) {
            int kbn = GSConstSmail.SML_FW;
            jdao.updateFw(kbn, paramMdl.getSmlViewAccount(), editMailSid);
        }

        /** 草稿メールからの編集且つ返信・転送メールの場合も受信メール情報を変更 */
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_SOKO)) {
            SmlWmeisDao wDao = new SmlWmeisDao(con);
            SmlWmeisModel wMdl = wDao.select(editMailSid);

            if (wMdl != null && wMdl.getSmwOrigin() > 0) {
                String editKbn = String.valueOf(wMdl.getSmwEditKbn());

                if (editKbn.equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)
                        || editKbn.equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)) {
                    jdao.updateHenshin(
                            wMdl.getSmwEditKbn(),
                            paramMdl.getSmlViewAccount(),
                            wMdl.getSmwOrigin());
                }
                if (editKbn.equals(GSConstSmail.MSG_CREATE_MODE_TENSO)) {
                    jdao.updateFw(
                            GSConstSmail.SML_FW,
                            paramMdl.getSmlViewAccount(),
                            wMdl.getSmwOrigin());
                }
            }
        }

        //受信テーブルにデータ作成
        String[] accountSidAtesaki = smlCmnBiz.getAccountSidFromUsr(
                con, paramMdl.getSml020userSid());

        String[] accountSidCc = smlCmnBiz.getAccountSidFromUsr(
                con, paramMdl.getSml020userSidCc());
        String[] accountSidBcc = smlCmnBiz.getAccountSidFromUsr(
                con, paramMdl.getSml020userSidBcc());

        ArrayList<String[]> accountSidList = new ArrayList<String[]>();
        ArrayList<Integer> sendKbnList = new ArrayList<Integer>();
        accountSidList.add(accountSidAtesaki);
        sendKbnList.add(GSConstSmail.SML_SEND_KBN_ATESAKI);

        if (accountSidCc != null && accountSidCc.length > 0) {
            accountSidList.add(accountSidCc);
            sendKbnList.add(GSConstSmail.SML_SEND_KBN_CC);
        }
        if (accountSidBcc != null && accountSidBcc.length > 0) {
            accountSidList.add(accountSidBcc);
            sendKbnList.add(GSConstSmail.SML_SEND_KBN_BCC);
        }

        //受信メール登録前に送信メールの集計データを登録する
        String[] cntAccountSid = null;
        ArrayList<String> cntAllAccountSidList = new ArrayList<String>();
        int cntAtesaki = 0;
        int cntCc = 0;
        int cntBcc = 0;
        for (int n = 0; n < accountSidList.size(); n++) {
            cntAccountSid = accountSidList.get(n);
            for (int i = 0; i < cntAccountSid.length; i++) {
                if (cntAllAccountSidList.contains(cntAccountSid[i])) {
                    continue;
                }
                cntAllAccountSidList.add(cntAccountSid[i]);
                if (sendKbnList.get(n) == GSConstSmail.SML_SEND_KBN_ATESAKI) {
                    cntAtesaki++;
                } else if (sendKbnList.get(n) == GSConstSmail.SML_SEND_KBN_CC) {
                    cntCc++;
                } else if (sendKbnList.get(n) == GSConstSmail.SML_SEND_KBN_BCC) {
                    cntBcc++;
                }
            }
        }

        SmlCommonBiz smlBiz = new SmlCommonBiz();
        smlBiz.regSmeisLogCnt(con, paramMdl.getSmlViewAccount(), cntAtesaki, cntCc, cntBcc, now);

        String[] accountSid = null;
        ArrayList<String> allAccountSidList = new ArrayList<String>();
        List<Integer> sendAccountList = new ArrayList<Integer>();

        ArrayList<SmlJmeisModel> jparamList = new ArrayList<SmlJmeisModel>();
        for (int n = 0; n < accountSidList.size(); n++) {

            accountSid = accountSidList.get(n);
            for (int i = 0; i < accountSid.length; i++) {
                if (allAccountSidList.contains(accountSid[i])) {
                    //一度送信したユーザを除く
                    continue;
                }
                allAccountSidList.add(accountSid[i]);

                SmlJmeisModel jparam = new SmlJmeisModel();
                jparam.setSacSid(Integer.parseInt(accountSid[i]));
                jparam.setSmjSid(mailSid);
                jparam.setSmjOpkbn(GSConstSmail.OPKBN_UNOPENED);
                jparam.setSmjFwkbn(GSConstSmail.FWKBN_NO);
                jparam.setSmjOpdate(null);
                jparam.setSmjJkbn(GSConst.JTKBN_TOROKU);
                jparam.setSmjSendkbn(sendKbnList.get(n));
                jparam.setSmjAuid(usrSid);
                jparam.setSmjAdate(now);
                jparam.setSmjEuid(usrSid);
                jparam.setSmjEdate(now);
                jparamList.add(jparam);
            }
        }

        //メール送信、それに伴う処理でデッドロックが発生しないよう
        //SIDの降順に並び替え実行
        jparamList = smlCmnBiz.setOrderBySidDescMdl(jparamList);
        for (SmlJmeisModel jparam:jparamList) {
            //受信メール登録
            jdao.insert(jparam);
            sendAccountList.add(jparam.getSacSid());
            //アカウントディスク使用量の再計算を行う
            smlBiz.updateAccountDiskSize(con, jparam.getSacSid(), addSize);
            //受信メールの集計データを登録する
            smlBiz.regJmeisLogCnt(con, jparam.getSacSid(), jparam.getSmjSendkbn(), now);
        }

        sendMdl.setSmjSid(mailSid);
        sendMdl.setAccountSidList(sendAccountList);

        //転送設定を取得し必要に応じてE-mailにて転送
        List<TempFileModel> fileList = biz.getTempFiles(tempDir);
        SmailDao smaildao = new SmailDao(con);
        ArrayList<SmailDetailModel> sdList =
                smaildao.selectSmeisDetailFromSid(sparam.getSmsSid());
        int fwkbn = 0;
        SmlJmeisModel jparam = null;

        if (sparam.getSmsType() == GSConstSmail.SAC_SEND_MAILTYPE_HTML) {
            //HTML形式の場合はattach.htmlを作成
            SmlMailFileModel fileMdl = new SmlMailFileModel();
            fileMdl.setFileName(GSConstSmail.HTMLMAIL_FILENAME);
            fileMdl.setContentType(
                    "Content-Type: text/html; charset=" + GSConstSmail.ATTACH_ENCODE);
            fileMdl.setFilePath(tempDir + "/" + GSConstSmail.HTMLMAIL_FILENAME);
            fileMdl.setHtmlMail(true);

            PrintWriter pw = null;
            try {
                IOTools.isDirCheck(tempDir, true);
                pw = new PrintWriter(new BufferedOutputStream(
                        new FileOutputStream(fileMdl.getFilePath())));
                pw.print(sparam.getSmsBody());
                pw.flush();
            } catch (Exception e) {
                log__.error("HTMLメールの保存に失敗", e);
                throw new IOToolsException("HTMLメールの保存に失敗", e);
            } finally {
                if (pw != null) {
                    pw.close();
                }
            }

            if (fileList == null || fileList.isEmpty()) {
                fileList = new ArrayList<TempFileModel>();
            }

            TempFileModel tempFileMdl = new TempFileModel();
            File file = new File(tempDir, GSConstSmail.HTMLMAIL_FILENAME);
            tempFileMdl.setFile(file);
            tempFileMdl.setFileName(GSConstSmail.HTMLMAIL_FILENAME);
            fileList.add(tempFileMdl);

            //メール送信用のテキストに変換
            bodyStr = StringUtilHtml.transToText(
                    StringUtilHtml.deleteHtmlTagAndScriptStyleBlock(
                            StringUtilHtml.transBRtoCRLF(bodyStr)));
            if (sdList != null && !sdList.isEmpty()) {
                sdList.get(0).setSmsBody(bodyStr);
            }
        }

        //デッドロックが発生しないようアカウントSIDを降順に統一
        allAccountSidList = smlCmnBiz.setOrderBySidDescStr(allAccountSidList);
        for (int i = 0; i < allAccountSidList.size(); i++) {

            fwkbn = smlCmnBiz.sendSmailForward(
                    sparam,
                    sdList,
                    Integer.parseInt(allAccountSidList.get(i)),
                    fileList,
                    adminConf,
                    pluginConfig,
                    con);

            if (fwkbn == GSConstSmail.ERROR_KBN) {
                continue;
            }

            if (fwkbn > 0) {
                jparam = new SmlJmeisModel();
                jparam.setSacSid(Integer.parseInt(allAccountSidList.get(i)));
                jparam.setSmjSid(mailSid);
                jparam.setSmjFwkbn(GSConstSmail.FWKBN_OK);
                jdao.updateFwkbn(jparam);
            }

        }

        SmlBinDao sbinDao = new SmlBinDao(con);

        //草稿モードの場合、草稿データと添付情報を削除(物理)
        SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(con);
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_SOKO)
                && editMailSid > 0) {

            //ショートメール情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            usedDataBiz.insertSoukouDataSize(Arrays.asList(editMailSid), false);

            //下書きを削除
            SmlWmeisModel wparam = new SmlWmeisModel();
            wparam.setSmwSid(editMailSid);
            SmlWmeisDao wdao = new SmlWmeisDao(con);
            //削除前に容量を取得し、集計
            Map<SmlWmeisModel, Long> wMap = wdao.getDeleteMail(Arrays.asList(editMailSid), 1, 1, 0);
            long delSize = 0;
            for (Map.Entry<SmlWmeisModel, Long> map : wMap.entrySet()) {
                delSize = map.getValue();
            }
            smlCmnBiz.updateAccountDiskSize(con, paramMdl.getSmlViewAccount(), -delSize);

            wdao.delete(wparam);

            //下書き宛先を削除
            SmlAsakModel aparam = new SmlAsakModel();
            aparam.setSmsSid(editMailSid);
            SmlAsakDao adao = new SmlAsakDao(con);
            adao.deleteFromMailSid(aparam);

            //ショートメールに送付されているバイナリSID一覧取得
            String[] mailSidList = new String[1];
            mailSidList[0] = String.valueOf(editMailSid);
            List<Long> binSidList =
                    sbinDao.selectBinSidList(mailSidList);

            //本文中のバイナリSID一覧取得
            SmlBodyBinDao sbbDao = new SmlBodyBinDao(con);
            List<Long> bodyBinSidList =
                    sbbDao.selectBinSidList(mailSidList);

            //バイナリ情報を論理削除
            CmnBinfDao binDao = new CmnBinfDao(con);
            CmnBinfModel cbMdl = new CmnBinfModel();
            cbMdl.setBinJkbn(GSConst.JTKBN_DELETE);
            cbMdl.setBinUpuser(usrSid);
            cbMdl.setBinUpdate(new UDate());
            binDao.updateJKbn(cbMdl, binSidList);
            binDao.updateJKbn(cbMdl, bodyBinSidList);

            //添付情報を削除
            sbinDao.deleteSmlBin(editMailSid);
            sbbDao.delete(editMailSid);
        }

        //ショートメール送付情報を登録
        sbinDao.insertSmlBin(sparam, binList);

        //ショートメール情報のデータ使用量を登録
        usedDataBiz.insertSendDataSize(mailSid, true);

        //ディスク容量を更新
        smlCmnBiz.updateAccountDiskSize(con, paramMdl.getSmlViewAccount(), addSize);



        return sendMdl;
    }

    /**
     * <br>[機  能] 写真表示フラグを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setPhotoDsp(
            Sml020knParamModel paramMdl,
            RequestModel reqMdl,
            Connection con)
                    throws SQLException {
        //写真表示フラグ
        Sml010Biz sml010Biz = new Sml010Biz();
        int photoDspFlg = sml010Biz.getPhotoDspFlg(reqMdl, con);
        paramMdl.setPhotoDspFlg(photoDspFlg);
    }

    /**
     * <br>[機  能] オペレーションログ 操作内容を作成します
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエストモデル
     * @param paramMdl Sml020knParamModel
     * @param con コネクション
     * @param smjSid ショートメールSid
     * @return オペレーションログの操作内容
     * @throws SQLException SQL実行時例外
     */
    public String createSendLogValue(
            HttpServletRequest req,
            Sml020knParamModel paramMdl,
            Connection con,
            int smjSid) throws SQLException {
        StringBuilder opValue = new StringBuilder();

        GsMessage gsMsg = new GsMessage(req);
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(paramMdl.getSml020SendAccount());
        SmlBinDao sbDao = new SmlBinDao(con);
        paramMdl.setSml020knFileList(sbDao.getFileList(smjSid));

        opValue.append(gsMsg.getMessage("cmn.mail.send"));
        opValue.append(":");
        opValue.append("\n");
        opValue.append("[");
        opValue.append(gsMsg.getMessage("cmn.sendfrom"));
        opValue.append("]");
        opValue.append(sacMdl.getSacName());
        opValue.append("\n");
        opValue.append("[");
        opValue.append(gsMsg.getMessage("cmn.subject"));
        opValue.append("]");
        opValue.append(paramMdl.getSml020Title());


        //To
        String userNameTo = __getDestinationForLog(con, paramMdl.getSml020userSid());
        opValue.append("\n[");
        opValue.append(gsMsg.getMessage("cmn.from"));
        opValue.append("]");
        if (userNameTo.length() <= GSConstSmail.MAXLEN_OPLOG_SENDERTO) {
            opValue.append(userNameTo);
        } else {
            //最大文字数を超える場合、人数と「...」を表示する
            opValue.append("(");
            opValue.append(paramMdl.getSml020userSid().length);
            opValue.append(gsMsg.getMessage("anp.count.people"));
            opValue.append(")");
            opValue.append(StringUtil.trimRengeString(
                    userNameTo, GSConstSmail.MAXLEN_OPLOG_SENDERTO));
            opValue.append("...");
        }

        //Cc
        if (paramMdl.getSml020userSidCc() != null && paramMdl.getSml020userSidCc().length > 0) {
            opValue.append("\n[Cc]");
            String userNameCc = __getDestinationForLog(con, paramMdl.getSml020userSidCc());
            if (userNameCc.length() <= GSConstSmail.MAXLEN_OPLOG_SENDERCC) {
                opValue.append(userNameCc);
            } else {
                //最大文字数を超える場合、人数と「...」を表示する
                opValue.append("(");
                opValue.append(paramMdl.getSml020userSidCc().length);
                opValue.append(gsMsg.getMessage("anp.count.people"));
                opValue.append(")");
                opValue.append(StringUtil.trimRengeString(
                        userNameCc, GSConstSmail.MAXLEN_OPLOG_SENDERCC));
                opValue.append("...");
            }
        }

        //Bcc
        if (paramMdl.getSml020userSidBcc() != null && paramMdl.getSml020userSidBcc().length > 0) {
            opValue.append("\n[Bcc]");
            String userNameBcc = __getDestinationForLog(con, paramMdl.getSml020userSidBcc());
            if (userNameBcc.length() <= GSConstSmail.MAXLEN_OPLOG_SENDERCC) {
                opValue.append(userNameBcc);
            } else {
                //最大文字数を超える場合、人数と「...」を表示する
                opValue.append("(");
                opValue.append(paramMdl.getSml020userSidBcc().length);
                opValue.append(gsMsg.getMessage("anp.count.people"));
                opValue.append(")");
                opValue.append(StringUtil.trimRengeString(
                        userNameBcc, GSConstSmail.MAXLEN_OPLOG_SENDERCC));
                opValue.append("...");
            }
        }
        return opValue.toString();
    }

    /**
     * <br>[機  能] ログ用の宛先一覧を返します
     * <br>[解  説] カンマで区切られた名前一覧を返します
     * <br>[備  考] オペレーションログ用
     * @param con コネクション
     * @param sids ユーザSID
     * @return カンマで区切られた名前一覧
     * @throws SQLException SQL実行時例外
     */
    private String __getDestinationForLog(Connection con, String[] sids) throws SQLException {
        ArrayList<String> usrAccountSidList = new ArrayList<String>();
    ArrayList<String> sacAccountSidList = new ArrayList<String>();
    for (int i = 0; i < sids.length; i++) {
        if (sids[i].indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
            //作成アカウント
            sacAccountSidList.add(sids[i].substring(GSConstSmail.SML_ACCOUNT_STR.length()));
        } else {
            //GSユーザ
            usrAccountSidList.add(sids[i]);
        }
    }

    StringBuilder ret = new StringBuilder();
    //宛先 ユーザ名
    String ustAccountName = null;
    if (usrAccountSidList.size() > 0) {
        ustAccountName = __getDestinationUsrName(con, usrAccountSidList);
        ret.append(ustAccountName);
    }

    //宛先 代表アカウント
    String sacAccountName = null;
    if (sacAccountSidList.size() > 0) {
        sacAccountName = __getDestinationSacName(con, sacAccountSidList);
        if (ustAccountName != null && sacAccountName != null) {
            ret.append(",");
        }
        ret.append(sacAccountName);
    }

    return ret.toString();
    }

    /**
     * <br>[機  能] 宛先ユーザ名一覧を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param usrSidList ユーザSID
     * @throws SQLException SQL実行例外
     * @return 宛先ユーザ名一覧
     */
    private String __getDestinationUsrName(Connection con, ArrayList<String> usrSidList)
            throws SQLException {
        String[] usrSids = new String[usrSidList.size()];
        for (int i = 0; i < usrSidList.size(); i++) {
            usrSids[i] = usrSidList.get(i);
        }

        StringBuilder userNames = new StringBuilder();
        CmnUsrmDao usrmDao = new CmnUsrmDao(con);
        ArrayList<BaseUserModel> userModelList = usrmDao.getSelectedUserList(usrSids);

        boolean firstFlg = true;
        for (BaseUserModel userMdl : userModelList) {
            if (firstFlg) {
                firstFlg = false;
            } else {
                userNames.append(",");
            }
            userNames.append(userMdl.getUsisei() + " " + userMdl.getUsimei());
        }
        return userNames.toString();
    }

    /**
     * <br>[機  能] 宛先代表アカウント一覧を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sacSidList 代表アカウントリスト
     * @throws SQLException SQL実行例外
     * @return 宛先代表アカウント一覧
     */
    private String __getDestinationSacName(Connection con, ArrayList<String> sacSidList)
            throws SQLException {
        String[] sacSids = new String[sacSidList.size()];
        for (int i = 0; i < sacSidList.size(); i++) {
            sacSids[i] = sacSidList.get(i);
        }

        Sml240Dao dao = new Sml240Dao(con);
        List<String> sacUserList = dao.getAccountNameList(sacSids);

        StringBuilder sacNameSb = new StringBuilder();
        boolean firstFlg = true;
        for (int i = 0; i < sacUserList.size(); i++) {
            if (firstFlg) {
                firstFlg = false;
            } else {
                sacNameSb.append(",");
            }
            sacNameSb.append(sacUserList.get(i));
        }
        return sacNameSb.toString();
    }

}