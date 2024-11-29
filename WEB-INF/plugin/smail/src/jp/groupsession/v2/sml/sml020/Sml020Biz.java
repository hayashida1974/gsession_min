package jp.groupsession.v2.sml.sml020;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnMyGroupMsDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.CmnLabelValueModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.WmlMailDataModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.biz.SmlUsedDataBiz;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAsakDao;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlBodyBinDao;
import jp.groupsession.v2.sml.dao.SmlHinaDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlUserSearchDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAsakModel;
import jp.groupsession.v2.sml.model.SmlBinModel;
import jp.groupsession.v2.sml.model.SmlBodyBinModel;
import jp.groupsession.v2.sml.model.SmlHinaModel;
import jp.groupsession.v2.sml.model.SmlSmeisModel;
import jp.groupsession.v2.sml.model.SmlWmeisModel;
import jp.groupsession.v2.sml.sml010.Sml010Action;
import jp.groupsession.v2.sml.sml010.Sml010Biz;
import jp.groupsession.v2.sml.sml240.Sml240Dao;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;


/**
 * <br>[機  能] ショートメール作成画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml020Biz {

    /** 画面ID */
    public static final String SCR_ID = "sml020";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml020Biz.class);

    /** ユーザ選択モード ユーザ名クリック */
    public static final int SELECT_USR_MODE_USRNAME = 1;
    /** ユーザ選択モード 宛先追加クリック */
    public static final int SELECT_USR_MODE_ATESAKI = 2;
    /** ユーザ選択モード CC追加クリック */
    public static final int SELECT_USR_MODE_CC = 3;
    /** ユーザ選択モード BCC追加クリック */
    public static final int SELECT_USR_MODE_BCC = 4;

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Sml020Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     *
     * <br>[機  能] 自動送信先設定を反映
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setAutoDest(Sml020ParamModel paramMdl,
            RequestModel reqMdl,
            Connection con)
        throws SQLException {
        ArrayList<String> askList = new ArrayList<String>();
        ArrayList<String> ccList = new ArrayList<String>();
        ArrayList<String> bccList = new ArrayList<String>();
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(con, reqMdl);
        SmlBanDestDao sbdDao = new SmlBanDestDao(con);
        List<Integer> banUsrList = sbdDao.getBanDestUsrSidList(
                reqMdl__.getSmodel().getUsrsid());
        List<Integer> banAccList = sbdDao.getBanDestAccSidList(
                reqMdl__.getSmodel().getUsrsid());
        List<AtesakiModel> banAtesaki = new ArrayList<AtesakiModel>();

        List<AtesakiModel> atsk = smlCmnBiz.getAutoDestList(con,
                paramMdl.getSmlViewAccount(),
                GSConstSmail.SML_SEND_KBN_ATESAKI);
        for (int i = 0; i < atsk.size(); i++) {
            AtesakiModel aMdl = (AtesakiModel) atsk.get(i);
            if (aMdl.getUsrSid() > 0) {
                if (!banUsrList.contains(aMdl.getUsrSid())) {
                    askList.add(String.valueOf(aMdl.getUsrSid()));
                }
            } else {
                if (!banAccList.contains(aMdl.getAccountSid())) {
                    askList.add(GSConstSmail.SML_ACCOUNT_STR
                                        + String.valueOf(aMdl.getAccountSid()));
                }
            }
        }
        if (!askList.isEmpty()) {
            paramMdl.setSml020userSid(askList.toArray(new String[askList.size()]));
        }

        atsk = smlCmnBiz.getAutoDestList(con,
                paramMdl.getSmlViewAccount(),
                GSConstSmail.SML_SEND_KBN_CC);
        for (int i = 0; i < atsk.size(); i++) {
            AtesakiModel aMdl = (AtesakiModel) atsk.get(i);
            if (aMdl.getUsrSid() > 0) {
                if (!banUsrList.contains(aMdl.getUsrSid())) {
                    ccList.add(String.valueOf(aMdl.getUsrSid()));
                }
            } else {
                if (!banAccList.contains(aMdl.getAccountSid())) {
                    ccList.add(GSConstSmail.SML_ACCOUNT_STR
                                        + String.valueOf(aMdl.getAccountSid()));
                }
            }
        }
        if (!ccList.isEmpty()) {
            paramMdl.setSml020userSidCc(ccList.toArray(new String[ccList.size()]));
        }
        atsk = smlCmnBiz.getAutoDestList(con,
                paramMdl.getSmlViewAccount(),
                GSConstSmail.SML_SEND_KBN_BCC);
        for (int i = 0; i < atsk.size(); i++) {
            AtesakiModel aMdl = (AtesakiModel) atsk.get(i);
            if (aMdl.getUsrSid() > 0) {
                if (!banUsrList.contains(aMdl.getUsrSid())) {
                    bccList.add(String.valueOf(aMdl.getUsrSid()));
                }
            } else {
                if (!banAccList.contains(aMdl.getAccountSid())) {
                    bccList.add(GSConstSmail.SML_ACCOUNT_STR
                                        + String.valueOf(aMdl.getAccountSid()));
                }
            }
        }
        if (!bccList.isEmpty()) {
            paramMdl.setSml020userSidBcc(bccList.toArray(new String[bccList.size()]));
        }
        paramMdl.setSml020AtesakiDeletedMessage(
                __getMessageForDeleteAtesaki(banAtesaki,
                        new GsMessage(reqMdl__)));

    }
    /**
     * <br>[機  能] 宛先名称一覧を設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setAtesaki(Sml020ParamModel paramMdl,
                            RequestModel reqMdl,
                            Connection con)
        throws SQLException {

        log__.debug("宛先名称設定");

        String[] userSid = paramMdl.getSml020userSid();

        if (userSid == null || userSid.length < 1) {
            return;
        }
        SmlUserSearchDao udao = new SmlUserSearchDao(con);
        ArrayList<AtesakiModel> ret = new ArrayList<AtesakiModel>();

            //全返信時以外
            List<String> gsUsers = new ArrayList<String>();
            List<String> smlUsers = new ArrayList<String>();

            for (String usid : userSid) {
                if (usid.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                    smlUsers.add(usid.substring(GSConstSmail.SML_ACCOUNT_STR.length()));
                } else {
                    gsUsers.add(usid);
                }
            }

            if (!gsUsers.isEmpty()) {
                ret.addAll(udao.getUserDataFromSidList(
                        (String[]) gsUsers.toArray(new String[gsUsers.size()])));
            }

            if (!smlUsers.isEmpty()) {
                ret.addAll(udao.getAccountDataFromSidList(
                        (String[]) smlUsers.toArray(new String[smlUsers.size()])));
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
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setAtesakiCc(Sml020ParamModel paramMdl, RequestModel reqMdl, Connection con)
        throws SQLException {

        log__.debug("CC名称設定");

        String[] userSid = paramMdl.getSml020userSidCc();

        if (userSid == null || userSid.length < 1) {
            return;
        }
        SmlUserSearchDao udao = new SmlUserSearchDao(con);
        ArrayList<AtesakiModel> ret = new ArrayList<AtesakiModel>();

            //全返信時以外
            List<String> gsUsers = new ArrayList<String>();
            List<String> smlUsers = new ArrayList<String>();

            for (String usid : userSid) {
                if (usid.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                    smlUsers.add(usid.substring(GSConstSmail.SML_ACCOUNT_STR.length()));
                } else {
                    gsUsers.add(usid);
                }
            }

            if (!gsUsers.isEmpty()) {
                ret.addAll(udao.getUserDataFromSidList(
                        (String[]) gsUsers.toArray(new String[gsUsers.size()])));
            }

            if (!smlUsers.isEmpty()) {
                ret.addAll(udao.getAccountDataFromSidList(
                        (String[]) smlUsers.toArray(new String[smlUsers.size()])));
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
    public void setAtesakiBcc(Sml020ParamModel paramMdl, Connection con)
        throws SQLException {

        log__.debug("BCC名称設定");

        String[] userSid = paramMdl.getSml020userSidBcc();

        if (userSid == null || userSid.length < 1) {
            return;
        }
        SmlUserSearchDao udao = new SmlUserSearchDao(con);
        ArrayList<AtesakiModel> ret = new ArrayList<AtesakiModel>();

        List<String> gsUsers = new ArrayList<String>();
        List<String> smlUsers = new ArrayList<String>();

        for (String usid : userSid) {
            if (usid.indexOf(GSConstSmail.SML_ACCOUNT_STR) != -1) {
                smlUsers.add(usid.substring(GSConstSmail.SML_ACCOUNT_STR.length()));
            } else {
                gsUsers.add(usid);
            }
        }

        if (!gsUsers.isEmpty()) {
            ret.addAll(udao.getUserDataFromSidList(
                    (String[]) gsUsers.toArray(new String[gsUsers.size()])));
        }

        if (!smlUsers.isEmpty()) {
            ret.addAll(udao.getAccountDataFromSidList(
                    (String[]) smlUsers.toArray(new String[smlUsers.size()])));
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
    public void setHinagataData(Sml020ParamModel paramMdl,
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
    public void setHinagataList(Sml020ParamModel paramMdl,
                                 RequestModel reqMdl,
                                 Connection con)
        throws SQLException {

        log__.debug("ひな形リスト設定");

        SmlHinaDao hdao = new SmlHinaDao(con);
        List<SmlHinaModel> ret = hdao.getHinaList(paramMdl.getSml020SendAccount());
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

        paramMdl.setSml020HinaList(cmnList);
        paramMdl.setSml020HinaListKjn(kojinList);
    }

    /**
     * <br>[機  能] 添付ファイル情報をセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setTempFiles(Sml020ParamModel paramMdl, Connection con)
    throws IOToolsException {

        /** 画面に表示するファイルのリストを作成、セット **********************/
        String tempDir = getTempDir();
        CommonBiz commonBiz = new CommonBiz();
        log__.debug("添付ファイルのディレクトリ！＝" + tempDir);
        paramMdl.setSml020FileLabelList(commonBiz.getTempFileLabelList(tempDir));
    }

    /**
     * <br>[機  能] メールSIDから引用するメールデータを設定する
     * <br>[解  説]
     * <br>[備  考] 返信、全返信、転送モードの処理
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param appRootPath アプリケーションルート
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setMailData(Sml020ParamModel paramMdl,
                             RequestModel reqMdl,
                             Connection con,
                             String appRootPath,
                             String domain)
        throws SQLException, IOToolsException, IOException, TempFileException {

        log__.debug("引用するメールデータ設定(返信、全返信、転送モード)");

        SmlAccountDao sacDao = new SmlAccountDao(con);
        SmlAccountModel sacMdl = new SmlAccountModel();
        sacMdl = sacDao.select(paramMdl.getSmlViewAccount());

        SmailDao sdao = new SmailDao(con);
        ArrayList<SmailDetailModel> ret =
            sdao.selectSmeisDetailFromSid2(
                    paramMdl.getSmlViewAccount(),
                    paramMdl.getSml010SelectedSid(),
                    paramMdl.getSml020ProcMode() == GSConstSmail.MSG_CREATE_MODE_TENSO);

        if (!ret.isEmpty()) {
            SmailDetailModel sMdl = ret.get(0);

            //件名
            paramMdl.setSml020Title(
                    __convertTitle(
                            paramMdl.getSml020ProcMode(),
                            NullDefault.getString(sMdl.getSmsTitle(), "")));
            //マーク
            paramMdl.setSml020Mark(sMdl.getSmsMark());
            //本文
            paramMdl.setSml020Body(
                    __convertBody(
                            paramMdl.getSml020ProcMode(), ret, reqMdl, sacMdl));

            //メール形式
            paramMdl.setSml020MailType(sMdl.getSmsType());

            if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_TENSO)
                    || paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_COPY)) {

                //添付ファイル情報
                SmlBinDao sbinDao = new SmlBinDao(con);
                List<SmlBinModel> binList = sbinDao.getBinList(sMdl.getSmlSid());

                //添付ファイルがあるなるならばテンポラリにコピー
                if (!binList.isEmpty()) {
                    String tempDir = getTempDir();
                    __tempFileCopy(binList, appRootPath, tempDir, con, domain);
                }
            }
            //複写して新規作成時にメール本文内の添付ファイルへの対応
            if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_COPY)) {
                //添付ファイル情報
                SmlBodyBinDao sbbDao = new SmlBodyBinDao(con);
                List<SmlBodyBinModel> sbbList = sbbDao.getBinList(sMdl.getSmlSid());
                //添付ファイルがあればテンポラリにコピー
                if (!sbbList.isEmpty()) {
                    String tempDir = getTempDir();
                    __bodyTempFileCopy(sbbList, appRootPath, tempDir, con, domain);
                    UDate now = new UDate();
                    //添付ファイルがあった場合に、本文のimgタグの書き換えが発生
                    String bodyMail = paramMdl.getSml020Body();
                    for (SmlBodyBinModel sbbMdl : sbbList) {
                        String beforeBody = "<img src=\"../smail/sml030.do?"
                                + "CMD=getBodyFile&amp;sml030SmlSid=";
                        beforeBody += (sMdl.getSmlSid());
                        beforeBody += ("&amp;sml030BodyFileSid=");
                        beforeBody += sbbMdl.getSbbSid();
                        beforeBody += "\"/>";
                        String afterBody = "<img data-mce-src=\"sml020.do?"
                                + "CMD=getBodyFile&amp;sml020TempSaveId=";
                        afterBody += sbbMdl.getSbbSid();
                        afterBody += "\" src=\"sml020.do?CMD=getBodyFile&amp;sml020TempSaveId=";
                        afterBody += sbbMdl.getSbbSid();
                        afterBody += "&amp;nowTime";
                        afterBody += now;
                        afterBody += "\">";
                        bodyMail = bodyMail.replace(beforeBody, afterBody);
                    }
                    paramMdl.setSml020Body(bodyMail);
                }
            }
            //宛先
            __setAtesaki(paramMdl.getSml020ProcMode(), ret, paramMdl, con);
        }
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
     * @param domain ドメイン
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイルアクセス時例外
     * @throws IOException 入出力時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void setMailDataFromSitagaki(Sml020ParamModel paramMdl,
                                         RequestModel reqMdl,
                                         Connection con,
                                         String appRootPath,
                                         String domain)
        throws SQLException, IOToolsException, IOException, TempFileException {
        log__.debug("引用するメールデータ設定(草稿から作成モード)");

        //下書きモード時は宛先を生成する
        Sml010Biz sml010biz = new Sml010Biz();
        ArrayList<AtesakiModel> retTo =
            sml010biz.getAtesaki2(paramMdl.getSml010SelectedSid(),
                            GSConstSmail.SML_SEND_KBN_ATESAKI, con);
        ArrayList<AtesakiModel> retCc =
            sml010biz.getAtesaki2(
                    paramMdl.getSml010SelectedSid(), GSConstSmail.SML_SEND_KBN_CC, con);
        ArrayList<AtesakiModel> retBcc =
            sml010biz.getAtesaki2(
                    paramMdl.getSml010SelectedSid(), GSConstSmail.SML_SEND_KBN_BCC, con);

        String[] sml020userSid = null;
        String[] sml020userSidCc = null;
        String[] sml020userSidBcc = null;
        SmlBanDestDao sbdDao = new SmlBanDestDao(con);
        List<Integer> banUsrList = sbdDao.getBanDestUsrSidList(reqMdl.getSmodel().getUsrsid());
        List<Integer> banAccList = sbdDao.getBanDestAccSidList(reqMdl.getSmodel().getUsrsid());
        List<AtesakiModel> banAtesaki = new ArrayList<AtesakiModel>();

        if (retTo.isEmpty()) {
            sml020userSid = new String[0];
        } else {
            List<String> toList = new ArrayList<String>();
            for (int i = 0; i < retTo.size(); i++) {
                AtesakiModel retMdl = (AtesakiModel) retTo.get(i);
                if (retMdl.getUsrSid() > 0) {
                    if (!banUsrList.contains(retMdl.getUsrSid())) {
                        toList.add(String.valueOf(retMdl.getUsrSid()));
                    } else {
                        banAtesaki.add(retMdl);
                    }
                } else {
                    if (!banAccList.contains(retMdl.getAccountSid())) {
                        toList.add("sac" + String.valueOf(retMdl.getAccountSid()));
                    } else {
                        banAtesaki.add(retMdl);
                    }
                }
            }
            sml020userSid = toList.toArray(new String[toList.size()]);
        }

        if (retCc.isEmpty()) {
            sml020userSidCc = new String[0];
        } else {
            List<String> toList = new ArrayList<String>();
            for (int i = 0; i < retCc.size(); i++) {
                AtesakiModel retMdl = (AtesakiModel) retCc.get(i);
                if (retMdl.getUsrSid() > 0) {
                    if (!banUsrList.contains(retMdl.getUsrSid())) {
                        toList.add(String.valueOf(retMdl.getUsrSid()));
                    } else {
                        banAtesaki.add(retMdl);
                    }
                } else {
                    if (!banAccList.contains(retMdl.getAccountSid())) {
                        toList.add("sac" + String.valueOf(retMdl.getAccountSid()));
                    } else {
                        banAtesaki.add(retMdl);
                    }
                }
            }
            sml020userSidCc = toList.toArray(new String[toList.size()]);
        }
        if (retBcc.isEmpty()) {
            sml020userSidBcc = new String[0];
        } else {
            List<String> toList = new ArrayList<String>();
            for (int i = 0; i < retBcc.size(); i++) {
                AtesakiModel retMdl = (AtesakiModel) retBcc.get(i);
                if (retMdl.getUsrSid() > 0) {
                    if (!banUsrList.contains(retMdl.getUsrSid())) {
                        toList.add(String.valueOf(retMdl.getUsrSid()));
                    } else {
                        banAtesaki.add(retMdl);
                    }
                } else {
                    if (!banAccList.contains(retMdl.getAccountSid())) {
                        toList.add("sac" + String.valueOf(retMdl.getAccountSid()));
                    } else {
                        banAtesaki.add(retMdl);
                    }
                }
            }
            sml020userSidBcc = toList.toArray(new String[toList.size()]);
        }
        paramMdl.setSml020userSid(sml020userSid);
        paramMdl.setSml020userSidCc(sml020userSidCc);
        paramMdl.setSml020userSidBcc(sml020userSidBcc);
        paramMdl.setSml020AtesakiDeletedMessage(
                __getMessageForDeleteAtesaki(banAtesaki,
                        new GsMessage(reqMdl__)));

        SmailDao sdao = new SmailDao(con);
        ArrayList<SmailDetailModel> ret =
            sdao.selectWmeisDetail(
                    paramMdl.getSmlViewAccount(),
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
            
            String tempDir = getTempDir();
            //添付ファイルがあるなるならばテンポラリにコピー
            if (!binList.isEmpty()) {
                
                __tempFileCopy(binList, appRootPath, tempDir, con, domain);
            }
            
            //ボディ内添付ファイル情報
            SmlBodyBinDao sbbDao = new SmlBodyBinDao(con);
            List<SmlBodyBinModel> sbbList = sbbDao.getBinList(sMdl.getSmlSid());
            if (!sbbList.isEmpty()) {
                __bodyTempFileCopy(sbbList, appRootPath, tempDir, con, domain);
                UDate now = new UDate();
                //添付ファイルがあった場合に、本文のimgタグの書き換えが発生
                String bodyMail = paramMdl.getSml020Body();
                for (SmlBodyBinModel sbbMdl : sbbList) {
                    String beforeBody = "<img src=\"../smail/sml030.do?"
                            + "CMD=getBodyFile&amp;sml030SmlSid=";
                    beforeBody += (sMdl.getSmlSid());
                    beforeBody += ("&amp;sml030BodyFileSid=");
                    beforeBody += sbbMdl.getSbbSid();
                    beforeBody += "\"/>";
                    String afterBody = "<img data-mce-src=\"sml020.do?"
                            + "CMD=getBodyFile&amp;sml020TempSaveId=";
                    afterBody += sbbMdl.getSbbSid();
                    afterBody += "\" src=\"sml020.do?CMD=getBodyFile&amp;sml020TempSaveId=";
                    afterBody += sbbMdl.getSbbSid();
                    afterBody += "&amp;nowTime";
                    afterBody += now;
                    afterBody += "\">";
                    bodyMail = bodyMail.replace(beforeBody, afterBody);
                }
                paramMdl.setSml020Body(bodyMail);
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

                //添付ファイルをテンポラリディレクトリにコピーする。
                cmnBiz.saveTempFile(dateStr, binMdl, appRootPath, tempDir, i);
                i++;
            }
        }
    }
    
    /**
     * <br>[機  能] ボディ要素内の添付ファイルをテンポラリディレクトリにコピーする
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
    private void __bodyTempFileCopy(List<SmlBodyBinModel> binList,
                                 String appRootPath,
                                 String tempDir,
                                 Connection con,
                                 String domain)
        throws SQLException, IOToolsException, IOException, TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        UDate now = new UDate();
        String dateStr = now.getDateString();
        int i = 1;
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        for (SmlBodyBinModel retBinMdl : binList) {
            tempPathUtil.createTempDir(reqMdl__,
                    GSConstSmail.PLUGIN_ID_SMAIL,
                    SCR_ID,
                    GSConstCommon.EDITOR_BODY_FILE,
                    String.valueOf(retBinMdl.getSbbSid()));
            String dir = tempDir 
                    + GSConstCommon.EDITOR_BODY_FILE 
                    + File.separator 
                    + retBinMdl.getSbbSid() 
                    + File.separator;
            CmnBinfModel binMdl = cmnBiz.getBinInfo(con, retBinMdl.getBinSid(), domain);
            if (binMdl != null) {

                //添付ファイルをテンポラリディレクトリにコピーする。
                cmnBiz.saveTempFile(dateStr, binMdl, appRootPath, dir, i);
                i++;
            }
        }
    }

    /**
     * <br>[機  能] 処理モードに応じ、件名を変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mode 処理モード
     * @param title 件名
     * @return cnvTitle 変換後タイトル
     */
    private String __convertTitle(String mode, String title) {

        String cnvTitle = "";

        GsMessage gsMsg = new GsMessage(reqMdl__);
        String msg = gsMsg.getMessage("wml.215");

        //返信モード
        if (mode.equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)) {
            cnvTitle = "Re" + msg + title;
        //全返信モード
        } else if (mode.equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)) {
            cnvTitle = "Re" + msg + title;
        //転送モード
        } else if (mode.equals(GSConstSmail.MSG_CREATE_MODE_TENSO)) {
            cnvTitle = "Fw" + msg + title;
        //複写して新規作成
        } else if (mode.equals(GSConstSmail.MSG_CREATE_MODE_COPY)) {
            cnvTitle = title;
        }

        return cnvTitle;
    }

    /**
     * <br>[機  能] 処理モードに応じ、本文を変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mode 処理モード
     * @param bean 検索結果
     * @param reqMdl リクエスト情報
     * @param sacMdl アカウントモデル
     * @return cnvBody 変換後本文
     */
    private String __convertBody(String mode, ArrayList<SmailDetailModel> bean,
                                RequestModel reqMdl, SmlAccountModel sacMdl) {

        String newLine = "\r\n";
        int atesakiLimit = 4;

        String cnvBody = "";
        SmailDetailModel sMdl = bean.get(0);

        //複写して新規作成モード
        if (mode.equals(GSConstSmail.MSG_CREATE_MODE_COPY)) {
            return sMdl.getSmsBody();
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        String henshin = gsMsg.getMessage("sml.98");
        String tensou = gsMsg.getMessage("sml.97");
        String colon = gsMsg.getMessage("wml.215");
        String sender = gsMsg.getMessage("cmn.sendfrom") + colon;
        String sendTo = gsMsg.getMessage("cmn.from") + colon;
        String sendDate = gsMsg.getMessage("sml.154");
        String subject = gsMsg.getMessage("cmn.subject") + colon;
        String mark = gsMsg.getMessage("cmn.mark") + colon;

        /******************** 見出し設定 ********************/
        //返信モード
        if (mode.equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)) {
            cnvBody = henshin;
        //全返信モード
        } else if (mode.equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)) {
            cnvBody = henshin;
        //転送モード
        } else if (mode.equals(GSConstSmail.MSG_CREATE_MODE_TENSO)) {
            cnvBody = tensou;
        }
        cnvBody += newLine;

        /******************** 差出人設定 ********************/
        cnvBody += sender;
        String sousinsya = "";

        if (sMdl.getUsrSid() > 0) {
            sousinsya = sMdl.getUsiSei() + "　" + sMdl.getUsiMei();
        } else {
            sousinsya = sMdl.getAccountName();
        }

        cnvBody += sousinsya;
        cnvBody += newLine;

        /******************** 宛先設定 ********************/
        cnvBody += sendTo;
        ArrayList<AtesakiModel> atsk = sMdl.getAtesakiList();
        int limCount = 0;
        boolean newLineFlg = false;
        for (int i = 0; i < atsk.size(); i++) {
            AtesakiModel aMdl = (AtesakiModel) atsk.get(i);

            if (aMdl.getSmjSendkbn() == GSConstSmail.SML_SEND_KBN_ATESAKI) {
                String atesakiName = "";

                if (aMdl.getUsrSid() > 0) {
                    atesakiName = aMdl.getUsiSei() + "　" + aMdl.getUsiMei();
                } else {
                    atesakiName = aMdl.getAccountName();
                }


                if (newLineFlg) {
                    cnvBody += "　　　　";
                    newLineFlg = false;
                }
                cnvBody += atesakiName;

                if (i < atsk.size() - 1) {
                    cnvBody += "; ";
                }

                limCount += 1;
                if (limCount == atesakiLimit
                    && i != atsk.size() - 1) {
                    limCount = 0;
                    newLineFlg = true;
                    cnvBody += newLine;
                }
            }
        }

        /******************** CC設定 ********************/
        //CCの件数を取得
        int cCcnt = 0;
        for (int i = 0; i < atsk.size(); i++) {
            AtesakiModel aMdl = (AtesakiModel) atsk.get(i);
            if (aMdl.getSmjSendkbn() == GSConstSmail.SML_SEND_KBN_CC) {
                cCcnt++;
            }
        }
        //CCを取得
        if (cCcnt > 0) {
            cnvBody += newLine;
            cnvBody += "CC" + colon;
            limCount = 0;
            newLineFlg = false;
            for (int i = 0; i < atsk.size(); i++) {
                AtesakiModel aMdl = (AtesakiModel) atsk.get(i);

                if (aMdl.getSmjSendkbn() == GSConstSmail.SML_SEND_KBN_CC) {
                    String atesakiName = "";

                    if (aMdl.getUsrSid() > 0) {
                        atesakiName = aMdl.getUsiSei() + "　" + aMdl.getUsiMei();
                    } else {
                        atesakiName = aMdl.getAccountName();
                    }

                    if (newLineFlg) {
                        cnvBody += "　　　　";
                        newLineFlg = false;
                    }
                    cnvBody += atesakiName;

                    if (i < atsk.size() - 1) {
                        cnvBody += "; ";
                    }

                    limCount += 1;
                    if (limCount == atesakiLimit
                        && i != atsk.size() - 1) {
                        limCount = 0;
                        newLineFlg = true;
                        cnvBody += newLine;
                    }
                }
            }
        }

        /******************** BCC設定 ********************/
        limCount = 0;
        newLineFlg = false;
        for (int i = 0; i < atsk.size(); i++) {
            AtesakiModel aMdl = (AtesakiModel) atsk.get(i);
            if (getSessionUserSid(reqMdl) == aMdl.getUsrSid()) {
                if (aMdl.getSmjSendkbn() == GSConstSmail.SML_SEND_KBN_BCC) {
                    String atesakiName = "";

                    if (aMdl.getUsrSid() > 0) {
                        atesakiName = aMdl.getUsiSei() + "　" + aMdl.getUsiMei();
                    } else {
                        atesakiName = aMdl.getAccountName();
                    }

                    if (newLineFlg) {
                        cnvBody += "　　　　";
                        newLineFlg = false;
                    }
                    cnvBody += newLine;
                    cnvBody += "BCC" + colon;
                    cnvBody += atesakiName;

                    if (i < atsk.size() - 1) {
                        cnvBody += "; ";
                    }

                    limCount += 1;
                    if (limCount == atesakiLimit
                        && i != atsk.size() - 1) {
                        limCount = 0;
                        newLineFlg = true;
                        cnvBody += newLine;
                    }
                }
            }
        }

        cnvBody += newLine;
        /******************** 送信日設定 ********************/
        cnvBody += sendDate;
        UDate sDate = sMdl.getSmsSdate();
        if (sDate != null) {
            String strSdate =
                UDateUtil.getSlashYYMD(sDate)
                + " "
                + UDateUtil.getSeparateHMS(sDate);
            cnvBody += strSdate;
        }
        cnvBody += newLine;

        /******************** 件名設定 ********************/
        cnvBody += " " + subject;
        cnvBody += sMdl.getSmsTitle();
        cnvBody += newLine;

        /******************** マーク設定 ********************/
        cnvBody += " " + mark;
        SmlCommonBiz schCmnBiz = new SmlCommonBiz(reqMdl);
        String markStr = schCmnBiz.convertMark2(sMdl.getSmsMark());
        cnvBody += markStr;
        cnvBody += newLine;
        cnvBody += newLine;

        /******************** 本文設定 ********************/


        String bodyStr = NullDefault.getString(sMdl.getSmsBody(), "");
        if (sMdl.getSmsType() != GSConstSmail.SAC_SEND_MAILTYPE_NORMAL) {
            bodyStr = StringUtilHtml.transToText(
                    StringUtilHtml.deleteHtmlTagAndScriptStyleBlock(
                            StringUtilHtml.transBRtoCRLF(bodyStr)));
        }

        //HTML特殊文字を変換
        bodyStr = StringUtilHtml.replaceSpecialChar(bodyStr);

        //HTML特殊文字を変換
        bodyStr = StringUtilHtml.replaceSpecialChar(bodyStr);

        String[] splStr = bodyStr.split(newLine);
        if (splStr != null && splStr.length > 0) {

            String quotes = ">";

            if (sacMdl != null) {
                if (sacMdl.getSacQuotes() != GSConstSmail.SAC_QUOTES_NONE) {
                    quotes = SmlCommonBiz.getViewMailQuotes(sacMdl.getSacQuotes(), reqMdl);
                } else {
                    quotes = "";
                }
            }

            for (int j = 0; j < splStr.length; j++) {
                cnvBody += quotes;
                cnvBody += splStr[j];
                cnvBody += newLine;
            }
        }
        return cnvBody;
    }

    /**
     * <br>[機  能] 作成されたメールデータを下書きとして登録する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param ctrl 採番用コネクション
     * @param appRootPath アプリケーションルート
     * @param dirId ディレクトリID
     * @throws Exception 
     */
    public void insertSitagakiData(Sml020ParamModel paramMdl,
                                    RequestModel reqMdl,
                                    Connection con,
                                    MlCountMtController ctrl,
                                    String appRootPath,
                                    String dirId)
        throws Exception {

        log__.debug("DBに下書き登録");

        int mailSid = -1;
        int usrSid = getSessionUserSid(reqMdl);
        UDate now = new UDate();

        int svEditSid = 0;
        int svEditKbn = 0;

        //テンポラリディレクトリパスを取得
        String tempDir = getTempDir(dirId);

        //添付ファイルを登録
        CommonBiz biz = new CommonBiz();
        List<String> binList =
            biz.insertBinInfo(con, tempDir, appRootPath, ctrl, usrSid, now);

        //下書きから作成 → 再度下書き保存 の場合(つまり下書きデータの更新)
        SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(con);
        long delSize = 0;
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_SOKO)) {

            mailSid = paramMdl.getSml010EditSid();

            //ショートメール情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            usedDataBiz.insertSoukouDataSize(Arrays.asList(mailSid), false);

            //下書きを削除
            SmlWmeisModel wparam = new SmlWmeisModel();
            wparam.setSmwSid(mailSid);
            SmlWmeisDao wdao = new SmlWmeisDao(con);
            
            //削除前に編集元メールSID、編集区分を保持しておく
            wparam = wdao.select(mailSid);
            if (wparam != null && wparam.getSmwOrigin() > 0) {
                svEditSid = wparam.getSmwOrigin();
                svEditKbn = wparam.getSmwEditKbn();
            }
            //削除前の容量を取得
            Map<SmlWmeisModel, Long> wMap = wdao.getDeleteMail(Arrays.asList(mailSid), 1, 1, 0);
            for (Map.Entry<SmlWmeisModel, Long> map : wMap.entrySet()) {
                delSize = map.getValue();
            }
            
            wdao.delete(wparam);

            //下書き宛先を削除
            SmlAsakModel aparam = new SmlAsakModel();
            aparam.setSmsSid(mailSid);
            SmlAsakDao adao = new SmlAsakDao(con);
            adao.deleteFromMailSid(aparam);

            //ショートメールに送付されているバイナリSID一覧取得
            SmlBinDao sbinDao = new SmlBinDao(con);
            String[] mailSidList = new String[1];
            mailSidList[0] = String.valueOf(mailSid);
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
            sbinDao.deleteSmlBin(mailSid);
            sbbDao.delete(mailSid);
        }

        //SID採番
        mailSid =
            (int) ctrl.getSaibanNumber(
                    GSConstSmail.SAIBAN_SML_SID,
                    GSConstSmail.SAIBAN_SUB_MAIL_SID,
                    usrSid);

        //メールサイズ取得
        Long titile_byte = new Long(0);
        Long body_byte = new Long(0);
        Long file_byte = new Long(0);
        Long bodyFile_byte = new Long(0);

        try {
            if (NullDefault.getString(
                    paramMdl.getSml020Title(), "").getBytes("UTF-8").length != 0) {
                titile_byte = Long.valueOf(
                        NullDefault.getString(
                                paramMdl.getSml020Title(), "").getBytes("UTF-8").length);
            }
        } catch (UnsupportedEncodingException e) {
            log__.error("文字のバイト数取得に失敗");
            titile_byte = Long.valueOf(
                    NullDefault.getString(
                            paramMdl.getSml020Title(), "").getBytes().length);
        }

        try {
            if (NullDefault.getString(
                    paramMdl.getSml020Body(), "").getBytes("UTF-8").length != 0) {
                body_byte = Long.valueOf(
                        NullDefault.getString(
                                paramMdl.getSml020Body(), "").getBytes("UTF-8").length);
            }
        } catch (UnsupportedEncodingException e) {
            log__.error("文字のバイト数取得に失敗");
            body_byte = Long.valueOf(
                    NullDefault.getString(
                            paramMdl.getSml020Body(), "").getBytes().length);
        }

        file_byte = biz.getTempFileSize(tempDir);


        //下書きテーブルにデータ作成
        SmlWmeisModel wparam = new SmlWmeisModel();
        wparam.setSacSid(paramMdl.getSml020SendAccount());
        wparam.setSmwSid(mailSid);
        wparam.setSmwTitle(NullDefault.getString(paramMdl.getSml020Title(), ""));
        wparam.setSmwMark(paramMdl.getSml020Mark());
        if (paramMdl.getSml020MailType() == GSConstSmail.SAC_SEND_MAILTYPE_NORMAL) {
            wparam.setSmwBody(NullDefault.getString(paramMdl.getSml020Body(), ""));
            wparam.setSmwBodyPlain("");
        } else {
            String bodyStr = NullDefault.getString(paramMdl.getSml020BodyHtml(), "");
            bodyStr = paramMdl.getSml020BodyHtml();
            bodyStr = StringUtilHtml.replaceString(bodyStr, "&", "&amp;");
            bodyStr = StringUtilHtml.replaceString(bodyStr, "&amp;amp;", "&amp;");
            bodyStr = StringUtilHtml.replaceString(bodyStr, "&amp;lt;", "&lt;");
            bodyStr = StringUtilHtml.replaceString(bodyStr, "&amp;gt;", "&gt;");
            bodyStr = StringUtilHtml.replaceString(bodyStr, "&amp;quot;", "&quot;");
            ArrayList<String> fileDirList = new ArrayList<String>();
            ArrayList<String> tempDirList = new ArrayList<String>();
            bodyStr = getSmlBodyFileList(mailSid,
                    bodyStr, con, ctrl, appRootPath, tempDir, fileDirList, tempDirList);
            wparam.setSmwBody(bodyStr);
            wparam.setSmwBodyPlain(StringUtilHtml.deleteHtmlTag(bodyStr));
            for (String dir : tempDirList) {
                bodyFile_byte += biz.getTempFileSize(dir);
            }
        }
        int editKbn = 0;
        int editSid = 0;
        /** 返信・全返信・転送時 **/
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)
                || paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)
                || paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_TENSO)) {
            editKbn = Integer.parseInt(paramMdl.getSml020ProcMode());
            editSid = paramMdl.getSml010SelectedSid();
        }
        /** 草稿から編集の場合 */
        if (paramMdl.getSml020ProcMode().equals(GSConstSmail.MSG_CREATE_MODE_SOKO)) {
            editKbn = svEditKbn;
            editSid = svEditSid;
        }
        wparam.setSmwType(paramMdl.getSml020MailType());
        wparam.setSmwSize(titile_byte + body_byte + file_byte + bodyFile_byte);
        wparam.setSmwJkbn(GSConst.JTKBN_TOROKU);
        wparam.setSmwOrigin(editSid);
        wparam.setSmwEditKbn(editKbn);
        wparam.setSmwAuid(usrSid);
        wparam.setSmwAdate(now);
        wparam.setSmwEuid(usrSid);
        wparam.setSmwEdate(now);
        SmlWmeisDao wdao = new SmlWmeisDao(con);
        wdao.insert(wparam);

        //ショートメール送付情報を登録
        SmlBinDao sbinDao = new SmlBinDao(con);
        SmlSmeisModel sparam = new SmlSmeisModel();
        sparam.setSmsSid(mailSid);
        sbinDao.insertSmlBin(sparam, binList);

        SmlCommonBiz smlBiz = new SmlCommonBiz();

        //宛先テーブルにデータ作成
        String[] userSids = paramMdl.getSml020userSid();
        //ユーザSIDからアカウントのSIDを取得
        String[] accountSids = null;
        accountSids = smlBiz.getAccountSidFromUsr(con, userSids);

        SmlAsakDao adao = new SmlAsakDao(con);
        SmlAsakModel aparam = null;
        if (accountSids != null && accountSids.length > 0) {
            for (int i = 0; i < accountSids.length; i++) {
                aparam = new SmlAsakModel();
                aparam.setSacSid(Integer.parseInt(accountSids[i]));
                aparam.setSmsSid(mailSid);
                aparam.setSmjSendkbn(GSConstSmail.SML_SEND_KBN_ATESAKI);
                aparam.setSmsAuid(usrSid);
                aparam.setSmsAdate(now);
                aparam.setSmsEuid(usrSid);
                aparam.setSmsEdate(now);
                adao.insert(aparam);
            }
        }

        //CC
        String[] userSidsCc = paramMdl.getSml020userSidCc();
        //ユーザSIDからアカウントのSIDを取得
        String[] accountSidsCc = null;
        accountSidsCc = smlBiz.getAccountSidFromUsr(con, userSidsCc);

        if (accountSidsCc != null && accountSidsCc.length > 0) {
            for (int i = 0; i < accountSidsCc.length; i++) {
                aparam = new SmlAsakModel();
                aparam.setSacSid(Integer.parseInt(accountSidsCc[i]));
                aparam.setSmsSid(mailSid);
                aparam.setSmjSendkbn(GSConstSmail.SML_SEND_KBN_CC);
                aparam.setSmsAuid(usrSid);
                aparam.setSmsAdate(now);
                aparam.setSmsEuid(usrSid);
                aparam.setSmsEdate(now);
                adao.insert(aparam);
            }
        }

        //BCC
        String[] userSidBcc = paramMdl.getSml020userSidBcc();
        //ユーザSIDからアカウントのSIDを取得
        String[] accountSidsBcc = null;
        accountSidsBcc = smlBiz.getAccountSidFromUsr(con, userSidBcc);

        if (accountSidsBcc != null && accountSidsBcc.length > 0) {
            for (int i = 0; i < accountSidsBcc.length; i++) {
                aparam = new SmlAsakModel();
                aparam.setSacSid(Integer.parseInt(accountSidsBcc[i]));
                aparam.setSmsSid(mailSid);
                aparam.setSmjSendkbn(GSConstSmail.SML_SEND_KBN_BCC);
                aparam.setSmsAuid(usrSid);
                aparam.setSmsAdate(now);
                aparam.setSmsEuid(usrSid);
                aparam.setSmsEdate(now);
                adao.insert(aparam);
            }
        }

        //ディスク容量を更新
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        long sum = titile_byte + body_byte + file_byte - delSize; 
        smlCmnBiz.updateAccountDiskSize(con, paramMdl.getSmlViewAccount(), sum);

        //ショートメール情報のデータ使用量を登録
        usedDataBiz.insertSoukouDataSize(Arrays.asList(mailSid), true);

        //テンポラリディレクトリのファイルを削除する
        deleteTempDir(dirId);

        log__.debug("テンポラリディレクトリのファイル削除");
    }
    /**
     * <br>[機  能] メッセージ件名取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return retTitle 件名
     * @throws SQLException SQL実行時例外
     */
    public String getDelMsgTitle(Sml020ParamModel paramMdl, Connection con)
        throws SQLException {

        String retTitle = "";

        SmlSmeisDao sdao = new SmlSmeisDao(con);
        SmlSmeisModel param = new SmlSmeisModel();
        param.setSmsSid(paramMdl.getSml010SelectedSid());
        SmlSmeisModel sret = sdao.select(param);
        if (sret != null) {
            retTitle = sret.getSmsTitle();
        }

        return retTitle;
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
    public void deleteMessage(Sml020ParamModel paramMdl, RequestModel reqMdl, Connection con)
        throws SQLException {

        int selectedSid = paramMdl.getSml010SelectedSid();
        int sessionUsrSid = getSessionUserSid(reqMdl);

        log__.debug("草稿メッセージ削除(ゴミ箱へ移動)");
        SmlWmeisDao wdao = new SmlWmeisDao(con);
        wdao.moveWmeis(
                sessionUsrSid,
                paramMdl.getSmlViewAccount(),
                GSConstSmail.SML_JTKBN_GOMIBAKO,
                new UDate(),
                selectedSid);
    }

    /**
     * <br>[機  能] セッションユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     * @return sessionUsrSid セッションユーザSID
     */
    public int getSessionUserSid(RequestModel reqMdl) {

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
     * <br>[機  能] 削除・復旧の対象メールタイトル取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return ret 削除対象メッセージリスト
     * @throws SQLException SQL実行時例外
     */
    public String getMailTitle(Sml020ParamModel paramMdl, Connection con)
        throws SQLException {

        String smwTitle = "";

        SmlWmeisModel bean = new SmlWmeisModel();
        bean.setSmwSid(paramMdl.getSml010SelectedSid());

        SmlWmeisDao wDao = new SmlWmeisDao(con);
        SmlWmeisModel wmlModel = wDao.select(bean);

        if (wmlModel != null) {
            smwTitle = wmlModel.getSmwTitle();
        }
        return smwTitle;
    }

    /**
     * <br>[機  能] ユーザ一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行例外
     */
    public void setLeftMenu(
            Sml020ParamModel paramMdl, Connection con, RequestModel reqMdl) throws SQLException {

        String groupSid = paramMdl.getSml010groupSid();
        int sessionUsrSid = getSessionUserSid(reqMdl);

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

        //マイグループリストをセット
        List<CmnLabelValueModel> dspGrpList = new ArrayList<CmnLabelValueModel>();
        for (CmnMyGroupModel cmgMdl : cmgList) {
            dspGrpList.add(
                    new CmnLabelValueModel(
                            cmgMdl.getMgpName(), "M" + String.valueOf(cmgMdl.getMgpSid()), "1"));
        }

        GsMessage gsMsg = new GsMessage(reqMdl);

        List<LabelValueBean> grpLabelList = gpBiz.getGroupCombLabelList(con, true, gsMsg);
        for (LabelValueBean bean : grpLabelList) {
            dspGrpList.add(
                    new CmnLabelValueModel(bean.getLabel(), bean.getValue(), "0"));

        }
        paramMdl.setSml020groupList(dspGrpList);


        //グループSIDから所属ユーザ一覧を作成
        int grpSid = getDspGroupSid(groupSid);
        ArrayList<Integer> users = new ArrayList<Integer>();

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
        ArrayList<Integer> usrList = (ArrayList<Integer>) cmnBiz.getCanUseSmailUser(con, users);

        //システムメールとGS管理者を除外する
        ArrayList<Integer> usrDspList = new ArrayList<Integer>();
        for (Integer usid : usrList) {
            if (usid != GSConstUser.SID_ADMIN && usid != GSConstUser.SID_SYSTEM_MAIL) {
                usrDspList.add(usid);
            }

        }

        //ユーザ情報を取得
        List<CmnUsrmInfModel> uList = null;
        if (users != null && users.size() > 0) {
            //ユーザ情報一覧を作成
            UserBiz usrBiz = new UserBiz();
            uList = usrBiz.getUserList(con, usrDspList);

        }

        paramMdl.setSml020userList(uList);
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
     * <br>[機  能] ユーザ一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param mode モード
     * @throws SQLException SQL実行例外
     */
    public void addUserAtesaki(Sml020ParamModel paramMdl, int mode)
    throws SQLException {

        ArrayList<String> addList = new ArrayList<String>();

        if (mode == Sml010Action.SELECT_USR_MODE_USRNAME) {
            addList.add(String.valueOf(paramMdl.getSml010usrSid()));

        } else {
            String[] addUser = paramMdl.getSml010usrSids();
            if (addUser != null && addUser.length > 0) {
                for (int i = 0; i < addUser.length; i++) {
                    addList.add(addUser[i]);
                }
            }
        }


        ArrayList<String> selectList = new ArrayList<String>();

        if (mode == Sml010Action.SELECT_USR_MODE_USRNAME) {
            String[] sltUser = paramMdl.getSml020userSid();
            if (sltUser != null && sltUser.length > 0) {
                for (int i = 0; i < sltUser.length; i++) {
                    selectList.add(sltUser[i]);
                }
            }

        } else if (mode == Sml010Action.SELECT_USR_MODE_ATESAKI) {
            String[] sltUser = paramMdl.getSml020userSid();
            if (sltUser != null && sltUser.length > 0) {
                for (int i = 0; i < sltUser.length; i++) {
                    selectList.add(sltUser[i]);
                }
            }

        } else if (mode == Sml010Action.SELECT_USR_MODE_CC) {
            String[] sltUser = paramMdl.getSml020userSidCc();
            if (sltUser != null && sltUser.length > 0) {
                for (int i = 0; i < sltUser.length; i++) {
                    selectList.add(sltUser[i]);
                }
            }

        } else if (mode == Sml010Action.SELECT_USR_MODE_BCC) {
            String[] sltUser = paramMdl.getSml020userSidBcc();
            if (sltUser != null && sltUser.length > 0) {
                for (int i = 0; i < sltUser.length; i++) {
                    selectList.add(sltUser[i]);
                }
            }

        }

        if (addList.size() > 0) {
            for (String usrSid : addList) {
                if (!selectList.contains(usrSid)) {
                    selectList.add(usrSid);
                }
            }
        }
        if (selectList.size() == 0) {
            return;
        }
        String[] retSid = new String[selectList.size()];
        for (int n = 0; n < selectList.size(); n++) {
            retSid[n] = selectList.get(n);
        }

        if (mode == Sml010Action.SELECT_USR_MODE_USRNAME
                || mode == Sml010Action.SELECT_USR_MODE_ATESAKI) {
            paramMdl.setSml020userSid(retSid);

        } else if (mode == Sml010Action.SELECT_USR_MODE_CC) {
            paramMdl.setSml020userSidCc(retSid);

        } else if (mode == Sml010Action.SELECT_USR_MODE_BCC) {
            paramMdl.setSml020userSidBcc(retSid);

        }
    }

    /**
     * <br>[機  能] ユーザ一覧を設定する。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void deleteUserAtesaki(Sml020ParamModel paramMdl)
    throws SQLException {

        int sendKbn = paramMdl.getSml020DelSendKbn();
        String[] userList = null;
        if (sendKbn == GSConstSmail.SML_SEND_KBN_ATESAKI) {
            userList = paramMdl.getSml020userSid();
        } else if (sendKbn == GSConstSmail.SML_SEND_KBN_CC) {
            userList = paramMdl.getSml020userSidCc();
        } else if (sendKbn == GSConstSmail.SML_SEND_KBN_BCC) {
            userList = paramMdl.getSml020userSidBcc();
        }

        String[] delList = new String[1];
        delList[0] = String.valueOf(paramMdl.getSml020DelUsrSid());

        CommonBiz biz = new CommonBiz();
        String[] retList = biz.getDeleteMember(delList, userList);

        if (sendKbn == GSConstSmail.SML_SEND_KBN_ATESAKI) {
            paramMdl.setSml020userSid(retList);
        } else if (sendKbn == GSConstSmail.SML_SEND_KBN_CC) {
            paramMdl.setSml020userSidCc(retList);
        } else if (sendKbn == GSConstSmail.SML_SEND_KBN_BCC) {
            paramMdl.setSml020userSidBcc(retList);
        }
    }

    /**
     * <br>[機  能] 宛先を設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param mode 処理モード
     * @param bean 検索結果
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __setAtesaki(String mode, ArrayList<SmailDetailModel> bean,
                            Sml020ParamModel paramMdl, Connection con) throws SQLException {

        SmailDetailModel sMdl = bean.get(0);
        ArrayList<AtesakiModel> atsk = sMdl.getAtesakiList();
        ArrayList<String> askList = new ArrayList<String>();
        ArrayList<String> ccList = new ArrayList<String>();
        ArrayList<String> bccList = new ArrayList<String>();
        SmlBanDestDao sbdDao = new SmlBanDestDao(con);
        List<Integer> banUsrList = sbdDao.getBanDestUsrSidList(
                reqMdl__.getSmodel().getUsrsid());
        List<Integer> banAccList = sbdDao.getBanDestAccSidList(
                reqMdl__.getSmodel().getUsrsid());
        List<AtesakiModel> banAtesaki = new ArrayList<AtesakiModel>();


        //返信モード
        if (mode.equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)) {

            //差出人
            __createSendUser(con, sMdl, askList, banUsrList, banAccList,
                    banAtesaki, true, true);

        //全返信モード
        //ログインユーザもしくは選択アカウントは返信対象に加えない
        } else if (mode.equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)) {
            //自身は返信対象に加えない
            SmlAccountDao sacDao = new SmlAccountDao(con);
            SmlAccountModel sacMdl = sacDao.select(paramMdl.getSmlViewAccount());
            String mySid = null;
            if (sacMdl != null) {
                mySid = String.valueOf(sacMdl.getUsrSid());
            }
            //差出人
            Boolean mySidCheck = !String.valueOf(sMdl.getUsrSid()).equals(mySid);
            Boolean viewAccountCheck = (sMdl.getAccountSid() != paramMdl.getSmlViewAccount());
            __createSendUser(con, sMdl, askList, banUsrList, banAccList,
                    banAtesaki, mySidCheck, viewAccountCheck);

            for (AtesakiModel aMdl : atsk) {
                mySidCheck = (!String.valueOf(aMdl.getUsrSid()).equals(mySid));
                viewAccountCheck = (aMdl.getAccountSid() != paramMdl.getSmlViewAccount());
                switch (aMdl.getSmjSendkbn()) {
                    case GSConstSmail.SML_SEND_KBN_ATESAKI:
                        //宛先
                        Boolean usrSidcheck = (!banUsrList.contains(aMdl.getUsrSid()));
                        String addData = (String.valueOf(aMdl.getUsrSid()));
                        __createAtskList(askList, banAccList, banAtesaki, aMdl, mySidCheck,
                                viewAccountCheck, usrSidcheck, addData);
                        break;

                    case GSConstSmail.SML_SEND_KBN_CC:
                        //CC
                        __createList(ccList, banUsrList, banAccList, banAtesaki,
                                aMdl, mySidCheck, viewAccountCheck, true);
                        break;

                    default:
                }
            }
        //複写モード
        //ログインユーザ、選択アカウントを含む全ての宛先を返信対象に加える
        } else if (mode.equals(GSConstSmail.MSG_CREATE_MODE_COPY)) {
              for (AtesakiModel aMdl : atsk) {

                switch (aMdl.getSmjSendkbn()) {
                    case GSConstSmail.SML_SEND_KBN_ATESAKI:
                        //宛先
                        Boolean usrSidcheck = (!banUsrList.contains(aMdl.getUsrSid()));
                        String addData = (String.valueOf(aMdl.getUsrSid()));
                        __createAtskList(askList, banAccList, banAtesaki, aMdl, true, true,
                                usrSidcheck, addData);
                        break;

                    case GSConstSmail.SML_SEND_KBN_CC:
                        //CC
                        __createList(ccList, banUsrList, banAccList, banAtesaki,
                                aMdl, true, true, true);
                        break;

                    case GSConstSmail.SML_SEND_KBN_BCC:
                        //BCC
                        __createList(bccList, banUsrList, banAccList, banAtesaki,
                                aMdl, true, true, true);
                        break;

                    default:
                }
            }
        }

        //自動送信先を反映
        SmlCommonBiz smlCommonBiz = new SmlCommonBiz(con, reqMdl__);

        if (mode.equals(GSConstSmail.MSG_CREATE_MODE_HENSIN)
                || mode.equals(GSConstSmail.MSG_CREATE_MODE_ZENHENSIN)) {
            //自動CC
            //ログインユーザ、選択アカウントを含む全ての宛先を返信対象に加えるが、
            //利用禁止ユーザだった場合の処理は行わない
            List<AtesakiModel> ccMdlList = smlCommonBiz.getAutoDestList(con,
                            paramMdl.getSmlViewAccount(),
                            GSConstSmail.SML_SEND_KBN_CC);
            for (int i = 0; i < ccMdlList.size(); i++) {
                AtesakiModel aMdl = (AtesakiModel) ccMdlList.get(i);

                __createList(ccList, banUsrList, banAccList, banAtesaki,
                        aMdl, true, true, false);

            }
            //自動BCC
            //ログインユーザ、選択アカウントを含む全ての宛先を返信対象に加えるが、
            //利用禁止ユーザだった場合の処理は行わない
            List<AtesakiModel> bccMdlList = smlCommonBiz.getAutoDestList(con,
                    paramMdl.getSmlViewAccount(),
                    GSConstSmail.SML_SEND_KBN_BCC);
            for (int i = 0; i < bccMdlList.size(); i++) {
                AtesakiModel aMdl = (AtesakiModel) bccMdlList.get(i);

                __createList(bccList, banUsrList, banAccList, banAtesaki,
                        aMdl, true, true, false);

            }
        }
        paramMdl.setSml020userSid(askList.toArray(new String[askList.size()]));
        paramMdl.setSml020userSidCc(ccList.toArray(new String[ccList.size()]));
        paramMdl.setSml020userSidBcc(bccList.toArray(new String[bccList.size()]));

        paramMdl.setSml020AtesakiDeletedMessage(
                __getMessageForDeleteAtesaki(banAtesaki,
                        new GsMessage(reqMdl__)));

    }

    /**
     * <br>[機  能] 差出人情報を宛先リストに追加する
     * <br>[解  説]
     * <br>[備  考] モードによって条件が変わる
     * @param con コネクション
     * @param sMdl 宛先情報
     * @param askList 宛先リスト
     * @param banUsrList 利用禁止ユーザSIDリスト
     * @param banAccList 利用禁止代表アカウントSIDリスト
     * @param banAtesaki 利用禁止リスト
     * @param mySidCheck 対象のSIDがログインユーザSIDかのチェック結果(必要がない場合はtrueが渡される)
     * @param viewAccountCheck 対象アカウントSIDが選択アカウントかのチェック結果(必要がない場合はtrueが渡される)
     * @throws SQLException 例外処理
     */
    private void __createSendUser(Connection con, SmailDetailModel sMdl,
            ArrayList<String> askList, List<Integer> banUsrList,
            List<Integer> banAccList, List<AtesakiModel> banAtesaki,
            Boolean mySidCheck, Boolean viewAccountCheck)
            throws SQLException {
        if (sMdl.getUsrSid() > 0 && mySidCheck) {
            if (!banUsrList.contains(sMdl.getUsrSid())) {
                askList.add(String.valueOf(sMdl.getUsrSid()));
            } else {
                SmlUserSearchDao udao = new SmlUserSearchDao(con);
                List<AtesakiModel> atList
                = udao.getUserDataFromSidList(
                        new String[] { String.valueOf(sMdl.getUsrSid()) });
                if (atList.size() > 0) {
                    banAtesaki.add(atList.get(0));
                }
            }
        } else if (viewAccountCheck) {
            if (!banAccList.contains(sMdl.getAccountSid())) {
                askList.add(GSConstSmail.SML_ACCOUNT_STR
                                + String.valueOf(sMdl.getAccountSid()));
            } else {
                SmlUserSearchDao udao = new SmlUserSearchDao(con);
                List<AtesakiModel> atList
                = udao.getAccountDataFromSidList(
                        new String[] { String.valueOf(sMdl.getAccountSid()) });
                if (atList.size() > 0) {
                    banAtesaki.add(atList.get(0));
                }
            }
        }
    }

    /**
     * <br>[機  能] 宛先情報(CC)を宛先リストに追加する
     * <br>[解  説]
     * <br>[備  考] モードによって条件が変わる
     * @param askList 宛先リスト
     * @param banAccList 利用禁止代表アカウントSIDリスト
     * @param banAtesaki 利用禁止リスト
     * @param aMdl 宛先情報
     * @param mySidCheck 対象のSIDがログインユーザSIDかのチェック結果(必要がない場合はtrueが渡される)
     * @param viewAccountCheck 対象アカウントSIDが選択アカウントかのチェック結果(必要がない場合はtrueが渡される)
     * @param check 分岐条件文
     * @param addDate 格納するデータ
     */
    private void __createAtskList(ArrayList<String> askList,
            List<Integer> banAccList, List<AtesakiModel> banAtesaki,
            AtesakiModel aMdl, Boolean mySidCheck, Boolean viewAccountCheck,
            Boolean check, String addDate) {
        if (aMdl.getUsrSid() > 0 && mySidCheck) {
            if (check) {
                askList.add(addDate);
            } else {
                banAtesaki.add(aMdl);
            }
        } else if (viewAccountCheck) {
            if (!banAccList.contains(aMdl.getAccountSid())) {
                askList.add(GSConstSmail.SML_ACCOUNT_STR
                        + String.valueOf(aMdl.getAccountSid()));
            } else {
                banAtesaki.add(aMdl);
            }
        }
    }

    /**
     * <br>[機  能] 宛先情報(BCC)を宛先リストに追加する
     * <br>[解  説]
     * <br>[備  考] モードによって条件が変わる
     * @param list 格納リスト
     * @param banUsrList 利用禁止ユーザSIDリスト
     * @param banAccList 利用禁止代表アカウントSIDリスト
     * @param banAtesaki 利用禁止リスト
     * @param aMdl 宛先情報
     * @param mySidCheck 対象のSIDがログインユーザSIDかのチェック結果(必要がない場合はtrueが渡される)
     * @param viewAccountCheck 対象アカウントSIDが選択アカウントかのチェック結果(必要がない場合はtrueが渡される)
     * @param elseFlg else処理実行フラグ
    */
    private void __createList(ArrayList<String> list,
            List<Integer> banUsrList, List<Integer> banAccList,
            List<AtesakiModel> banAtesaki, AtesakiModel aMdl,
            Boolean mySidCheck, Boolean viewAccountCheck,
            Boolean elseFlg) {
        if (aMdl.getUsrSid() > 0 && mySidCheck) {
            if (!banUsrList.contains(aMdl.getUsrSid())) {
                list.add(String.valueOf(aMdl.getUsrSid()));
            } else if (elseFlg) {
                banAtesaki.add(aMdl);
            }
        } else if (viewAccountCheck) {
            if (!banAccList.contains(aMdl.getAccountSid())) {
                list.add(GSConstSmail.SML_ACCOUNT_STR
                        + String.valueOf(aMdl.getAccountSid()));
            } else  if (elseFlg) {
                banAtesaki.add(aMdl);
            }
        }
    }

    /**
     * <br>[機  能] WEBメール メール情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void setWebmailData(
        Sml020ParamModel paramMdl,
        Connection con,
        String appRootPath,
        String tempDir,
        RequestModel reqMdl) throws Exception {

        long mailNum = paramMdl.getSml020webmailId();
        WmlDao wmlDao = new WmlDao(con);
        WmlMailDataModel mailData = wmlDao.getMailData(mailNum, reqMdl__.getDomain());
        paramMdl.setSml020Title(mailData.getSubject());

        GsMessage gsMsg = new GsMessage(reqMdl);
        String body = gsMsg.getMessage("cmn.subject") + ": "
                            + NullDefault.getString(mailData.getSubject(), "")
                            + "\r\n";
        body += gsMsg.getMessage("cmn.sendfrom") + ": "
                    + NullDefault.getString(mailData.getFromAddress(), "")
                    + "\r\n";

        body += gsMsg.getMessage("cmn.send.date") + ": ";
        UDate sendDate = mailData.getSendDate();
        if (sendDate != null) {
            body += UDateUtil.getSlashYYMD(sendDate)
                    + " "
                    + UDateUtil.getSeparateHMS(sendDate);
        }
        body += "\r\n";

        body += gsMsg.getMessage("cmn.from") + ": "
                    + __createAddressStr(mailData.getToList())
                    + "\r\n";
        body += "CC: " + __createAddressStr(mailData.getCcList()) + "\r\n";
        body += "\r\n" + mailData.getBody();

        paramMdl.setSml020Body(body);

        setAutoDest(paramMdl, reqMdl__, con);

        if (mailData.getTempFileList() != null) {
            UDate now = new UDate();
            String dateStr = now.getDateString();
            CommonBiz cmnBiz = new CommonBiz();
            int fileNum = 1;
            for (WmlTempfileModel fileMdl : mailData.getTempFileList()) {
                cmnBiz.saveTempFileForWebmail(dateStr, fileMdl, appRootPath, tempDir, fileNum);
                fileNum++;
            }
            CommonBiz commonBiz = new CommonBiz();
            log__.debug("添付ファイルのディレクトリ！＝" + tempDir);
            paramMdl.setSml020FileLabelList(commonBiz.getTempFileLabelList(tempDir));
        }
    }
    /**
     * <br>[機  能] メールアドレス一覧から本文設定用メールアドレス文字列を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param addressList メールアドレス一覧
     * @return 本文設定用メールアドレス文字列
     */
    private String __createAddressStr(List<String> addressList) {
        String address = "";
        if (addressList != null && !addressList.isEmpty()) {
            address = addressList.get(0);
            for (int adrIdx = 1; adrIdx < addressList.size(); adrIdx++) {
                address += "," + addressList.get(adrIdx);
            }
        }
        return address;
    }
    /**
     *
     * <br>[機  能] 送信先が制限されている場合の削除メッセージを作成
     * <br>[解  説]
     * <br>[備  考]
     * @param atkList 制限された宛先一覧
     * @param gsMsg メッセージクラス
     * @return エラーメッセージ文言
     */
    private String __getMessageForDeleteAtesaki(List<AtesakiModel> atkList, GsMessage gsMsg) {
        if (atkList == null || atkList.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(gsMsg.getMessage("sml.sml020.08"));
        List<Integer> useSidList = new ArrayList<Integer>();
        for (AtesakiModel atk : atkList) {
            if (useSidList.contains(atk.getAccountSid())) {
                continue;
            }

            sb.append("<br />・");
            if (atk.getUsrSid() > 0) {
                sb.append(atk.getUsiSei());
                sb.append(" ");
                sb.append(atk.getUsiMei());
            } else {
                sb.append(atk.getAccountName());
            }
            useSidList.add(atk.getAccountSid());
        }
        return sb.toString();
    }

    /**
     * <br>[機  能] オペレーションログ 操作内容を作成します
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエストモデル
     * @param paramMdl Sml020knParamModel
     * @param con コネクション
     * @return オペレーションログの操作内容
     * @throws SQLException SQL実行時例外
     */
    public String createSendLogValue(
            HttpServletRequest req,
            Sml020ParamModel paramMdl,
            Connection con) throws SQLException {
        StringBuilder opValue = new StringBuilder();

        GsMessage gsMsg = new GsMessage(req);
        SmlAccountModel sacMdl = new SmlAccountModel();
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sacMdl = sacDao.select(paramMdl.getSml020SendAccount());

        opValue.append(gsMsg.getMessage("cmn.save.draft3"));
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

    /**
     * <br>[機  能] 最大文字数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param appRootPath アプリケーションルートパス
     */
    public void getSendMaxLength(Sml020ParamModel paramMdl, String appRootPath) {
        int maxlength = 0;
        maxlength = SmlCommonBiz.getBodyLimitLength(appRootPath);
        paramMdl.setSml010MailBodyLimit(maxlength);
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return テンポラリディレクトリパス
     */
    public String getTempDir() {
        return getTempDir(SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dirId ディレクトリID
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(String dirId) {
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        return smlCmnBiz.getTempDir(reqMdl__, dirId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void deleteTempDir() {
        deleteTempDir(SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param dirId ディレクトリID
     */
    public void deleteTempDir(String dirId) {
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        smlCmnBiz.deleteTempDir(reqMdl__, dirId);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを初期化
     * <br>[解  説]
     * <br>[備  考]
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void clearTempDir() throws IOToolsException {
        deleteTempDir(SCR_ID);
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.createTempDir(reqMdl__,
                                GSConstSmail.PLUGIN_ID_SMAIL,
                                SCR_ID,
                                GSConstCommon.EDITOR_BODY_FILE);
    }
    
    /**
     * <br>[機  能] 本文中に記述されているユーザのアップロードファイルを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param smlSid smlSID
     * @param bodyValue 本文
     * @param con コネクション
     * @param ctrl MlCountMtController
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリ
     * @param bodyFileDirList ファイルディレクトリリスト
     * @param tempDirList テンポラリディレクトリリスト
     * @return 本文中で指定されているユーザのアップロードファイルのリスト
     * @throws IOException 
     * @throws Exception 実行時例外
     */
    public String getSmlBodyFileList(
            int smlSid, String bodyValue, Connection con,
            MlCountMtController ctrl,
            String appRootPath,
            String tempDir,
            ArrayList<String> bodyFileDirList,
            ArrayList<String> tempDirList)
                    throws IOException,
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
        
        String ret = "";

        if (bodyValue == null
                || bodyValue.length() < 1) {
            return ret;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html[");

        //Entityを設定
        __setEntity(sb);

        sb.append("]>");

        String bodyHeader = "<root>";
        String bodyFooter = "</root>";
        if (!StringUtil.isNullZeroString(bodyValue)) {
            StringBuffer bodySb = new StringBuffer();
            for (int index = 0; index < bodyValue.length(); index++) {
                char c = bodyValue.charAt(index);
                if ((c >= 0x00 && c <= 0x08)
                        || (c >= 0x0B && c <= 0x0C)
                        || (c >= 0x0E && c <= 0x1F)) {
                    
                    bodySb.append("");
                } else {
                    bodySb.append(c);
                }
            }
            bodyValue = bodySb.toString();
        }

        sb.append(bodyHeader);
        sb.append(bodyValue);
        sb.append(bodyFooter);
        bodyValue = sb.toString();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        ByteArrayInputStream baiStream = null;
        StringWriter strWriter = null;
        try {
            baiStream = new ByteArrayInputStream(bodyValue.getBytes("UTF-8"));
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(baiStream);

            //<img>タグのsrcを取得
            NodeList imgNodeList =  doc.getElementsByTagName("img");

            if (imgNodeList != null && imgNodeList.getLength() > 0) {
                List <String> bodyBinSidList = new ArrayList<String>();
                String bodyFileTempDir = null;
                CommonBiz cmnBiz = new CommonBiz();
                UDate now = new UDate();
                String startKey =  "sml020.do?CMD=getBodyFile";
                for (int i = 0; i < imgNodeList.getLength(); ++i) {
                    Node imgNode = imgNodeList.item(i);
                    if (imgNode.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    NamedNodeMap imgNodeAttrMap = imgNode.getAttributes();
                    Node srcAttr = imgNodeAttrMap.getNamedItem("src");
                    if (srcAttr == null) {
                        continue;
                    }
                    String srcStr = srcAttr.getNodeValue();
                    if (srcStr == null || srcStr.length() < 1) {
                        continue;
                    }
                    if (!srcStr.startsWith(startKey)) {
                        continue;
                    }
                    int idxOfIdStart = srcStr.lastIndexOf("=");
                    String idStr = srcStr.substring(idxOfIdStart + 1);
                    //本文添付情報を取得し、バイナリー情報に登録
                    bodyFileTempDir =
                            tempDir + GSConstCommon.EDITOR_BODY_FILE
                            + File.separator + idStr + File.separator;
                    bodyFileDirList.add(idStr);
                    tempDirList.add(bodyFileTempDir);
                    List <String> bodyBinSid = cmnBiz.insertBinInfo(
                            con, bodyFileTempDir, appRootPath, ctrl, 
                            reqMdl__.getSmodel().getUsrsid(), now);
                    if (bodyBinSid != null && bodyBinSid.size() > 0) {
                        bodyBinSidList.add(bodyBinSid.get(0));
                        srcAttr.setNodeValue("../smail/sml030.do?"
                                + "CMD=getBodyFile"
                                + "&sml030SmlSid=" + String.valueOf(smlSid)
                                + "&sml030BodyFileSid=" + idStr);
                    }
                }
                //投稿本文添付情報テーブルへの登録
                SmlBodyBinDao sbbDao = new SmlBodyBinDao(con);
                sbbDao.insertSmlBodyBinData(smlSid, bodyFileDirList, bodyBinSidList);
            }
            //解析・変更した文を本文に戻して登録
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            strWriter = new StringWriter();
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, new StreamResult(strWriter));
            String strDoc = strWriter.toString();

            int valueStart = strDoc.indexOf(bodyHeader);
            int valueEnd = strDoc.lastIndexOf(bodyFooter);
            strDoc = strDoc.substring(valueStart + bodyHeader.length(), valueEnd);

            ret = strDoc;

        } catch (Exception e) {
            log__.error("HTMLの読み取りに失敗しました。", e);
            throw e;
        } finally {
            if (strWriter != null) {
                strWriter.close();
            }
            if (baiStream != null) {
                baiStream.close();
            }
        }

        return ret;
    }
    
    /**
     * <br>[機  能] Entityの記述を追加します。
     * <br>[解  説]
     * <br>[備  考]
     * @param sb 文字列
     * @return Entityを記述した文字列
     */
    private Object __setEntity(StringBuilder sb) {

        //ISO 8859-1 characters
        sb.append("<!ENTITY nbsp   \"&#160;\">");
        sb.append("<!ENTITY iexcl  \"&#161;\">");
        sb.append("<!ENTITY cent   \"&#162;\">");
        sb.append("<!ENTITY pound  \"&#163;\">");
        sb.append("<!ENTITY curren \"&#164;\">");
        sb.append("<!ENTITY yen    \"&#165;\">");
        sb.append("<!ENTITY brvbar \"&#166;\">");
        sb.append("<!ENTITY sect   \"&#167;\">");
        sb.append("<!ENTITY uml    \"&#168;\">");
        sb.append("<!ENTITY copy   \"&#169;\">");
        sb.append("<!ENTITY ordf   \"&#170;\">");
        sb.append("<!ENTITY laquo  \"&#171;\">");
        sb.append("<!ENTITY not    \"&#172;\">");
        sb.append("<!ENTITY shy    \"&#173;\">");
        sb.append("<!ENTITY reg    \"&#174;\">");
        sb.append("<!ENTITY macr   \"&#175;\">");
        sb.append("<!ENTITY deg    \"&#176;\">");
        sb.append("<!ENTITY plusmn \"&#177;\">");
        sb.append("<!ENTITY sup2   \"&#178;\">");
        sb.append("<!ENTITY sup3   \"&#179;\">");
        sb.append("<!ENTITY acute  \"&#180;\">");
        sb.append("<!ENTITY micro  \"&#181;\">");
        sb.append("<!ENTITY para   \"&#182;\">");
        sb.append("<!ENTITY middot \"&#183;\">");
        sb.append("<!ENTITY cedil  \"&#184;\">");
        sb.append("<!ENTITY sup1   \"&#185;\">");
        sb.append("<!ENTITY ordm   \"&#186;\">");
        sb.append("<!ENTITY raquo  \"&#187;\">");
        sb.append("<!ENTITY frac14 \"&#188;\">");
        sb.append("<!ENTITY frac12 \"&#189;\">");
        sb.append("<!ENTITY frac34 \"&#190;\">");
        sb.append("<!ENTITY iquest \"&#191;\">");
        sb.append("<!ENTITY Agrave \"&#192;\">");
        sb.append("<!ENTITY Aacute \"&#193;\">");
        sb.append("<!ENTITY Acirc  \"&#194;\">");
        sb.append("<!ENTITY Atilde \"&#195;\">");
        sb.append("<!ENTITY Auml   \"&#196;\">");
        sb.append("<!ENTITY Aring  \"&#197;\">");
        sb.append("<!ENTITY AElig  \"&#198;\">");
        sb.append("<!ENTITY Ccedil \"&#199;\">");
        sb.append("<!ENTITY Egrave \"&#200;\">");
        sb.append("<!ENTITY Eacute \"&#201;\">");
        sb.append("<!ENTITY Ecirc  \"&#202;\">");
        sb.append("<!ENTITY Euml   \"&#203;\">");
        sb.append("<!ENTITY Igrave \"&#204;\">");
        sb.append("<!ENTITY Iacute \"&#205;\">");
        sb.append("<!ENTITY Icirc  \"&#206;\">");
        sb.append("<!ENTITY Iuml   \"&#207;\">");
        sb.append("<!ENTITY ETH    \"&#208;\">");
        sb.append("<!ENTITY Ntilde \"&#209;\">");
        sb.append("<!ENTITY Ograve \"&#210;\">");
        sb.append("<!ENTITY Oacute \"&#211;\">");
        sb.append("<!ENTITY Ocirc  \"&#212;\">");
        sb.append("<!ENTITY Otilde \"&#213;\">");
        sb.append("<!ENTITY Ouml   \"&#214;\">");
        sb.append("<!ENTITY times  \"&#215;\">");
        sb.append("<!ENTITY Oslash \"&#216;\">");
        sb.append("<!ENTITY Ugrave \"&#217;\">");
        sb.append("<!ENTITY Uacute \"&#218;\">");
        sb.append("<!ENTITY Ucirc  \"&#219;\">");
        sb.append("<!ENTITY Uuml   \"&#220;\">");
        sb.append("<!ENTITY Yacute \"&#221;\">");
        sb.append("<!ENTITY THORN  \"&#222;\">");
        sb.append("<!ENTITY szlig  \"&#223;\">");
        sb.append("<!ENTITY agrave \"&#224;\">");
        sb.append("<!ENTITY aacute \"&#225;\">");
        sb.append("<!ENTITY acirc  \"&#226;\">");
        sb.append("<!ENTITY atilde \"&#227;\">");
        sb.append("<!ENTITY auml   \"&#228;\">");
        sb.append("<!ENTITY aring  \"&#229;\">");
        sb.append("<!ENTITY aelig  \"&#230;\">");
        sb.append("<!ENTITY ccedil \"&#231;\">");
        sb.append("<!ENTITY egrave \"&#232;\">");
        sb.append("<!ENTITY eacute \"&#233;\">");
        sb.append("<!ENTITY ecirc  \"&#234;\">");
        sb.append("<!ENTITY euml   \"&#235;\">");
        sb.append("<!ENTITY igrave \"&#236;\">");
        sb.append("<!ENTITY iacute \"&#237;\">");
        sb.append("<!ENTITY icirc  \"&#238;\">");
        sb.append("<!ENTITY iuml   \"&#239;\">");
        sb.append("<!ENTITY eth    \"&#240;\">");
        sb.append("<!ENTITY ntilde \"&#241;\">");
        sb.append("<!ENTITY ograve \"&#242;\">");
        sb.append("<!ENTITY oacute \"&#243;\">");
        sb.append("<!ENTITY ocirc  \"&#244;\">");
        sb.append("<!ENTITY otilde \"&#245;\">");
        sb.append("<!ENTITY ouml   \"&#246;\">");
        sb.append("<!ENTITY divide \"&#247;\">");
        sb.append("<!ENTITY oslash \"&#248;\">");
        sb.append("<!ENTITY ugrave \"&#249;\">");
        sb.append("<!ENTITY uacute \"&#250;\">");
        sb.append("<!ENTITY ucirc  \"&#251;\">");
        sb.append("<!ENTITY uuml   \"&#252;\">");
        sb.append("<!ENTITY yacute \"&#253;\">");
        sb.append("<!ENTITY thorn  \"&#254;\">");
        sb.append("<!ENTITY yuml   \"&#255;\">");

        //symbols, mathematical symbols, and Greek letters
        sb.append("<!ENTITY fnof     \"&#402;\">");
        sb.append("<!ENTITY Alpha    \"&#913;\">");
        sb.append("<!ENTITY Beta     \"&#914;\">");
        sb.append("<!ENTITY Gamma    \"&#915;\">");
        sb.append("<!ENTITY Delta    \"&#916;\">");
        sb.append("<!ENTITY Epsilon  \"&#917;\">");
        sb.append("<!ENTITY Zeta     \"&#918;\">");
        sb.append("<!ENTITY Eta      \"&#919;\">");
        sb.append("<!ENTITY Theta    \"&#920;\">");
        sb.append("<!ENTITY Iota     \"&#921;\">");
        sb.append("<!ENTITY Kappa    \"&#922;\">");
        sb.append("<!ENTITY Lambda   \"&#923;\">");
        sb.append("<!ENTITY Mu       \"&#924;\">");
        sb.append("<!ENTITY Nu       \"&#925;\">");
        sb.append("<!ENTITY Xi       \"&#926;\">");
        sb.append("<!ENTITY Omicron  \"&#927;\">");
        sb.append("<!ENTITY Pi       \"&#928;\">");
        sb.append("<!ENTITY Rho      \"&#929;\">");
        sb.append("<!ENTITY Sigma    \"&#931;\">");
        sb.append("<!ENTITY Tau      \"&#932;\">");
        sb.append("<!ENTITY Upsilon  \"&#933;\">");
        sb.append("<!ENTITY Phi      \"&#934;\">");
        sb.append("<!ENTITY Chi      \"&#935;\">");
        sb.append("<!ENTITY Psi      \"&#936;\">");
        sb.append("<!ENTITY Omega    \"&#937;\">");
        sb.append("<!ENTITY alpha    \"&#945;\">");
        sb.append("<!ENTITY beta     \"&#946;\">");
        sb.append("<!ENTITY gamma    \"&#947;\">");
        sb.append("<!ENTITY delta    \"&#948;\">");
        sb.append("<!ENTITY epsilon  \"&#949;\">");
        sb.append("<!ENTITY zeta     \"&#950;\">");
        sb.append("<!ENTITY eta      \"&#951;\">");
        sb.append("<!ENTITY theta    \"&#952;\">");
        sb.append("<!ENTITY iota     \"&#953;\">");
        sb.append("<!ENTITY kappa    \"&#954;\">");
        sb.append("<!ENTITY lambda   \"&#955;\">");
        sb.append("<!ENTITY mu       \"&#956;\">");
        sb.append("<!ENTITY nu       \"&#957;\">");
        sb.append("<!ENTITY xi       \"&#958;\">");
        sb.append("<!ENTITY omicron  \"&#959;\">");
        sb.append("<!ENTITY pi       \"&#960;\">");
        sb.append("<!ENTITY rho      \"&#961;\">");
        sb.append("<!ENTITY sigmaf   \"&#962;\">");
        sb.append("<!ENTITY sigma    \"&#963;\">");
        sb.append("<!ENTITY tau      \"&#964;\">");
        sb.append("<!ENTITY upsilon  \"&#965;\">");
        sb.append("<!ENTITY phi      \"&#966;\">");
        sb.append("<!ENTITY chi      \"&#967;\">");
        sb.append("<!ENTITY psi      \"&#968;\">");
        sb.append("<!ENTITY omega    \"&#969;\">");
        sb.append("<!ENTITY thetasym \"&#977;\">");
        sb.append("<!ENTITY upsih    \"&#978;\">");
        sb.append("<!ENTITY piv      \"&#982;\">");

        sb.append("<!ENTITY bull     \"&#8226;\">");
        sb.append("<!ENTITY hellip   \"&#8230;\">");
        sb.append("<!ENTITY prime    \"&#8242;\">");
        sb.append("<!ENTITY Prime    \"&#8243;\">");
        sb.append("<!ENTITY oline    \"&#8254;\">");
        sb.append("<!ENTITY frasl    \"&#8260;\">");
        sb.append("<!ENTITY weierp   \"&#8472;\">");
        sb.append("<!ENTITY image    \"&#8465;\">");
        sb.append("<!ENTITY real     \"&#8476;\">");
        sb.append("<!ENTITY trade    \"&#8482;\">");
        sb.append("<!ENTITY alefsym  \"&#8501;\">");
        sb.append("<!ENTITY larr     \"&#8592;\">");
        sb.append("<!ENTITY uarr     \"&#8593;\">");
        sb.append("<!ENTITY rarr     \"&#8594;\">");
        sb.append("<!ENTITY darr     \"&#8595;\">");
        sb.append("<!ENTITY harr     \"&#8596;\">");
        sb.append("<!ENTITY crarr    \"&#8629;\">");
        sb.append("<!ENTITY lArr     \"&#8656;\">");
        sb.append("<!ENTITY uArr     \"&#8657;\">");
        sb.append("<!ENTITY rArr     \"&#8658;\">");
        sb.append("<!ENTITY dArr     \"&#8659;\">");
        sb.append("<!ENTITY hArr     \"&#8660;\">");
        sb.append("<!ENTITY forall   \"&#8704;\">");
        sb.append("<!ENTITY part     \"&#8706;\">");
        sb.append("<!ENTITY exist    \"&#8707;\">");
        sb.append("<!ENTITY empty    \"&#8709;\">");
        sb.append("<!ENTITY nabla    \"&#8711;\">");
        sb.append("<!ENTITY isin     \"&#8712;\">");
        sb.append("<!ENTITY notin    \"&#8713;\">");
        sb.append("<!ENTITY ni       \"&#8715;\">");
        sb.append("<!ENTITY prod     \"&#8719;\">");
        sb.append("<!ENTITY sum      \"&#8721;\">");
        sb.append("<!ENTITY minus    \"&#8722;\">");
        sb.append("<!ENTITY lowast   \"&#8727;\">");
        sb.append("<!ENTITY radic    \"&#8730;\">");
        sb.append("<!ENTITY prop     \"&#8733;\">");
        sb.append("<!ENTITY infin    \"&#8734;\">");
        sb.append("<!ENTITY ang      \"&#8736;\">");
        sb.append("<!ENTITY and      \"&#8743;\">");
        sb.append("<!ENTITY or       \"&#8744;\">");
        sb.append("<!ENTITY cap      \"&#8745;\">");
        sb.append("<!ENTITY cup      \"&#8746;\">");
        sb.append("<!ENTITY int      \"&#8747;\">");
        sb.append("<!ENTITY there4   \"&#8756;\">");
        sb.append("<!ENTITY sim      \"&#8764;\">");
        sb.append("<!ENTITY cong     \"&#8773;\">");
        sb.append("<!ENTITY asymp    \"&#8776;\">");
        sb.append("<!ENTITY ne       \"&#8800;\">");
        sb.append("<!ENTITY equiv    \"&#8801;\">");
        sb.append("<!ENTITY le       \"&#8804;\">");
        sb.append("<!ENTITY ge       \"&#8805;\">");
        sb.append("<!ENTITY sub      \"&#8834;\">");
        sb.append("<!ENTITY sup      \"&#8835;\">");
        sb.append("<!ENTITY nsub     \"&#8836;\">");
        sb.append("<!ENTITY sube     \"&#8838;\">");
        sb.append("<!ENTITY supe     \"&#8839;\">");
        sb.append("<!ENTITY oplus    \"&#8853;\">");
        sb.append("<!ENTITY otimes   \"&#8855;\">");
        sb.append("<!ENTITY perp     \"&#8869;\">");
        sb.append("<!ENTITY sdot     \"&#8901;\">");
        sb.append("<!ENTITY lceil    \"&#8968;\">");
        sb.append("<!ENTITY rceil    \"&#8969;\">");
        sb.append("<!ENTITY lfloor   \"&#8970;\">");
        sb.append("<!ENTITY rfloor   \"&#8971;\">");
        sb.append("<!ENTITY lang     \"&#9001;\">");
        sb.append("<!ENTITY rang     \"&#9002;\">");
        sb.append("<!ENTITY loz      \"&#9674;\">");
        sb.append("<!ENTITY spades   \"&#9824;\">");
        sb.append("<!ENTITY clubs    \"&#9827;\">");
        sb.append("<!ENTITY hearts   \"&#9829;\">");
        sb.append("<!ENTITY diams    \"&#9830;\">");

        //markup-significant and internationalization characters
        sb.append("<!ENTITY quot    \"&#34;\"  >");
        sb.append("<!ENTITY amp     \"&#38;\"  >");
        sb.append("<!ENTITY lt      \"&#60;\"  >");
        sb.append("<!ENTITY gt      \"&#62;\"  >");
        sb.append("<!ENTITY OElig   \"&#338;\" >");
        sb.append("<!ENTITY oelig   \"&#339;\" >");
        sb.append("<!ENTITY Scaron  \"&#352;\" >");
        sb.append("<!ENTITY scaron  \"&#353;\" >");
        sb.append("<!ENTITY Yuml    \"&#376;\" >");
        sb.append("<!ENTITY circ    \"&#710;\" >");
        sb.append("<!ENTITY tilde   \"&#732;\" >");
        sb.append("<!ENTITY ensp    \"&#8194;\">");
        sb.append("<!ENTITY emsp    \"&#8195;\">");
        sb.append("<!ENTITY thinsp  \"&#8201;\">");
        sb.append("<!ENTITY zwnj    \"&#8204;\">");
        sb.append("<!ENTITY zwj     \"&#8205;\">");
        sb.append("<!ENTITY lrm     \"&#8206;\">");
        sb.append("<!ENTITY rlm     \"&#8207;\">");
        sb.append("<!ENTITY ndash   \"&#8211;\">");
        sb.append("<!ENTITY mdash   \"&#8212;\">");
        sb.append("<!ENTITY lsquo   \"&#8216;\">");
        sb.append("<!ENTITY rsquo   \"&#8217;\">");
        sb.append("<!ENTITY sbquo   \"&#8218;\">");
        sb.append("<!ENTITY ldquo   \"&#8220;\">");
        sb.append("<!ENTITY rdquo   \"&#8221;\">");
        sb.append("<!ENTITY bdquo   \"&#8222;\">");
        sb.append("<!ENTITY dagger  \"&#8224;\">");
        sb.append("<!ENTITY Dagger  \"&#8225;\">");
        sb.append("<!ENTITY permil  \"&#8240;\">");
        sb.append("<!ENTITY lsaquo  \"&#8249;\">");
        sb.append("<!ENTITY rsaquo  \"&#8250;\">");
        sb.append("<!ENTITY euro    \"&#8364;\">");

        return sb;
    }
}