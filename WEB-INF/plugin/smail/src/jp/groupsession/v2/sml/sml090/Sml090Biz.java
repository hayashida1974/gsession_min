package jp.groupsession.v2.sml.sml090;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.mail.internet.MimeBodyPart;
import javax.servlet.http.HttpServletResponse;

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
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.mail.MailUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.ITempFileUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.biz.SmlUsedDataBiz;
import jp.groupsession.v2.sml.biz.SmlViewHtmlConverter;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.dao.SmailSearchDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAsakDao;
import jp.groupsession.v2.sml.dao.SmlBinDao;
import jp.groupsession.v2.sml.dao.SmlJmeisDao;
import jp.groupsession.v2.sml.dao.SmlJmeisLabelDao;
import jp.groupsession.v2.sml.dao.SmlLabelDao;
import jp.groupsession.v2.sml.dao.SmlSmeisDao;
import jp.groupsession.v2.sml.dao.SmlSmeisLabelDao;
import jp.groupsession.v2.sml.dao.SmlUserSearchDao;
import jp.groupsession.v2.sml.dao.SmlWmeisDao;
import jp.groupsession.v2.sml.dao.SmlWmeisLabelDao;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailDetailModel;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.model.SmlJmeisLabelModel;
import jp.groupsession.v2.sml.model.SmlLabelModel;
import jp.groupsession.v2.sml.model.SmlSmeisLabelModel;
import jp.groupsession.v2.sml.model.SmlUserModel;
import jp.groupsession.v2.sml.model.SmlWmeisLabelModel;
import jp.groupsession.v2.sml.model.SmlWmeisModel;
import jp.groupsession.v2.sml.pdf.SmlPdfModel;
import jp.groupsession.v2.sml.pdf.SmlPdfUtil;
import jp.groupsession.v2.sml.sml010.Sml010Biz;
import jp.groupsession.v2.sml.sml010.Sml010ExportFileModel;
import jp.groupsession.v2.sml.sml030.Sml030Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] ショートメール詳細検索画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml090Biz {

    /** 画面ID */
    public static final String SCR_ID = "sml090";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml090Biz.class);

    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト情報
     */
    public Sml090Biz(RequestModel reqMdl) {
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
     * <br>[機  能] ショートメール検索初期データセット
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Sml090ParamModel paramMdl,
                            RequestModel reqMdl,
                            Connection con)
        throws SQLException {

        log__.debug("初期表示データ取得");

        int sessionUsrSid = __getSessionUserSid(reqMdl);


        //ラベルリスト設定
        __setGroupUserCombo(paramMdl, sessionUsrSid, con, reqMdl);
        paramMdl.setSml090SortKeyLabelList(SmlCommonBiz.getSortLabelList(
                                       paramMdl.getSml090MailSyubetsu(), reqMdl));
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
    public void setAtesaki(Sml090ParamModel paramMdl,
                        Connection con)
        throws SQLException {

        log__.debug("宛先名称設定");

        //受信モード時はセットしない
        if (GSConstSmail.TAB_DSP_MODE_JUSIN.equals(paramMdl.getSml090MailSyubetsu())) {
            paramMdl.setCmn120userSid(null);
            return;
        }

        String[] userSids = paramMdl.getCmn120userSid();

        if (userSids == null || userSids.length < 1) {
            return;
        }

        SmlUserSearchDao udao = new SmlUserSearchDao(con);
        ArrayList<AtesakiModel> ret =
            udao.getUserDataFromSidList(userSids);

        SmailModel sMdl = new SmailModel();
        sMdl.setAtesakiList(ret);
        sMdl.setListSize(ret.size() - 1);

        paramMdl.setSml090AtesakiModel(sMdl);
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
    private void __setGroupUserCombo(
            Sml090ParamModel paramMdl,
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
     * 検索結果情報を取得します
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return int 検索結果件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchResult(Sml090ParamModel paramMdl,
            RequestModel reqMdl, Connection con) throws SQLException {
        int sessionUsrSid = __getSessionUserSid(reqMdl);
        SmailSearchDao searchDao = new SmailSearchDao(con);

        int searchFlg = paramMdl.getSearchFlg();
        if (GSConstSmail.SEARCH_EXECUTE_FALSE == searchFlg) {
            return 0;
        }

        //1ページ当りの表示件数取得
        int limit = __getDspPageCount(sessionUsrSid, con);
        //検索パラメータ取得
        Sml090SearchParameterModel prmModel = __getSearchParameter(sessionUsrSid, paramMdl);

        //全データ件数取得
        int maxCount = __getSmailSearchCount(searchDao, prmModel);
        int nowPage = paramMdl.getSml090page1();
        int offset = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        prmModel.setOffset(offset);
        prmModel.setLimit(limit);

        //検索結果取得
        ArrayList<SmailModel> resList = __getSmailSearchResult(searchDao, prmModel);
        paramMdl.setSml090SearchResultList(resList);

        //ページング
        paramMdl.setSml090page1(nowPage);
        paramMdl.setSml090page2(nowPage);
        paramMdl.setSmlPageLabel(
            PageUtil.createPageOptions(maxCount, limit));
        return maxCount;
    }

    /**
     * 検索結果情報を取得します
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return int 検索結果件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchResultData(Sml090ParamModel paramMdl,
            RequestModel reqMdl, Connection con) throws SQLException {
        int sessionUsrSid = __getSessionUserSid(reqMdl);
        SmailSearchDao searchDao = new SmailSearchDao(con);

        int searchFlg = paramMdl.getSearchFlg();
        if (GSConstSmail.SEARCH_EXECUTE_FALSE == searchFlg) {
            return 0;
        }

        //1ページ当りの表示件数取得
        int limit = __getDspPageCount(sessionUsrSid, con);
        //検索パラメータ取得
        Sml090SearchParameterModel prmModel = __getSearchParameterData(sessionUsrSid, paramMdl);

        //写真表示フラグ
        Sml010Biz sml010Biz = new Sml010Biz();
        int photoDspFlg = sml010Biz.getPhotoDspFlg(reqMdl, con);
        paramMdl.setPhotoSearchDspFlg(photoDspFlg);

        //全データ件数取得
        int maxCount = __getSmailSearchCount(searchDao, prmModel);
        int nowPage = paramMdl.getSml090page1();
        int offset = PageUtil.getRowNumber(nowPage, limit);
        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < offset) {
            nowPage = maxPageNum;
            offset = maxPageStartRow;
        }
        prmModel.setOffset(offset);
        prmModel.setLimit(limit);

        //検索結果取得
        ArrayList<SmailModel> resList = __getSmailSearchResult(searchDao, prmModel);
        paramMdl.setSml090SearchResultList(resList);

        //ページング
        paramMdl.setSml090page1(nowPage);
        paramMdl.setSml090page2(nowPage);
        paramMdl.setSmlPageLabel(
            PageUtil.createPageOptions(maxCount, limit));
        return maxCount;
    }


    /**
     * <br>[機  能] 検索用パラメータモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @param paramMdl パラメータ情報
     * @return Sml090SearchParameterModel
     */
    private Sml090SearchParameterModel __getSearchParameter(int sessionUsrSid,
                                                            Sml090ParamModel paramMdl) {

        Sml090SearchParameterModel prmModel = new Sml090SearchParameterModel();
        prmModel.setMySid(sessionUsrSid);
        prmModel.setMailMode(paramMdl.getSml010ProcMode());
        prmModel.setAtesaki(paramMdl.getSml090SvAtesaki());
        prmModel.setKeyword(NullDefault.getString(paramMdl.getSml090SvKeyWord(), ""));
        prmModel.setKeyWordkbn(Integer.parseInt(paramMdl.getSml090SvKeyWordkbn()));
        prmModel.setMailMark(Integer.parseInt(paramMdl.getSml090SvMailMark()));
        prmModel.setMailSyubetsu(paramMdl.getSml090SvMailSyubetsu());

        prmModel.setSearchOrderKey1(Integer.parseInt(paramMdl.getSml090SvSearchOrderKey1()));
        prmModel.setSearchOrderKey2(Integer.parseInt(paramMdl.getSml090SvSearchOrderKey2()));
        prmModel.setSearchSortKey1(Integer.parseInt(paramMdl.getSml090SvSearchSortKey1()));
        prmModel.setSearchSortKey2(Integer.parseInt(paramMdl.getSml090SvSearchSortKey2()));
        prmModel.setSearchTarget(paramMdl.getSml090SvSearchTarget());
        prmModel.setSltGroup(
                NullDefault.getString(
                        paramMdl.getSml090SvSltGroup(), String.valueOf(GSConstCommon.NUM_INIT)));
        prmModel.setSltUser(
                NullDefault.getString(
                        paramMdl.getSml090SvSltUser(), String.valueOf(GSConstCommon.NUM_INIT)));
        prmModel.setUserSid(paramMdl.getCmn120SvuserSid());

        return prmModel;
    }

    /**
     * <br>[機  能] 検索用パラメータモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID
     * @param paramMdl パラメータ情報
     * @return Sml090SearchParameterModel
     */
    private Sml090SearchParameterModel __getSearchParameterData(int sessionUsrSid,
                                                            Sml090ParamModel paramMdl) {

        Sml090SearchParameterModel prmModel = new Sml090SearchParameterModel();
        prmModel.setMySid(paramMdl.getSmlViewAccount());
        prmModel.setMailMode(paramMdl.getSml010ProcMode());
        prmModel.setAtesaki(paramMdl.getSml090SvAtesaki());
        prmModel.setKeyword(NullDefault.getString(paramMdl.getSml090SvKeyWord(), ""));
        prmModel.setKeyWordkbn(Integer.parseInt(paramMdl.getSml090SvKeyWordkbn()));
        prmModel.setMailMark(Integer.parseInt(paramMdl.getSml090SvMailMark()));
        prmModel.setMailSyubetsu(paramMdl.getSml090SvMailSyubetsu());

        prmModel.setSearchOrderKey1(Integer.parseInt(paramMdl.getSml090SvSearchOrderKey1()));
        prmModel.setSearchOrderKey2(Integer.parseInt(paramMdl.getSml090SvSearchOrderKey2()));
        prmModel.setSearchSortKey1(Integer.parseInt(paramMdl.getSml090SvSearchSortKey1()));
        prmModel.setSearchSortKey2(Integer.parseInt(paramMdl.getSml090SvSearchSortKey2()));
        prmModel.setSearchTarget(paramMdl.getSml090SvSearchTarget());
        prmModel.setSltGroup(
                NullDefault.getString(
                        paramMdl.getSml090SvSltGroup(), String.valueOf(GSConstCommon.NUM_INIT)));
        prmModel.setSltUser(
                NullDefault.getString(
                        paramMdl.getSml090SvSltUser(), String.valueOf(GSConstCommon.NUM_INIT)));


        return prmModel;
    }

    /**
     * <br>[機  能] ショートメールの件数取得
     * <br>[解  説]
     * <br>[備  考]
     * @param searchDao ショートメール検索Dao
     * @param prmModel パラメータモデル
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    private int __getSmailSearchCount(
            SmailSearchDao searchDao, Sml090SearchParameterModel prmModel) throws SQLException {
        String mailShubetsu = prmModel.getMailSyubetsu();
        if (GSConstSmail.TAB_DSP_MODE_JUSIN.equals(mailShubetsu)) {
            log__.debug("件数検索モード : [受信]");
            return searchDao.getSearchDataCountJushin(prmModel);

        } else if (GSConstSmail.TAB_DSP_MODE_SOSIN.equals(mailShubetsu)) {
            log__.debug("件数検索モード : [送信]");
            return searchDao.getSearchDataCountSoushin(prmModel);

        } else if (GSConstSmail.TAB_DSP_MODE_SOKO.equals(mailShubetsu)) {
            log__.debug("件数検索モード : [草稿]");
            return searchDao.getSearchDataCountSoukou(prmModel);

        } else if (GSConstSmail.TAB_DSP_MODE_GOMIBAKO.equals(mailShubetsu)) {
            log__.debug("件数検索モード : [ゴミ箱]");
            return searchDao.getSearchDataCountGomiBako(prmModel);

        }

        log__.debug("件数検索モード : [該当なし]");
        return 0;
    }

    /**
     * <br>[機  能] ショートメールの検索結果取得
     * <br>[解  説]
     * <br>[備  考]
     * @param searchDao ショートメール検索Dao
     * @param prmModel パラメータモデル
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<SmailModel> __getSmailSearchResult(
            SmailSearchDao searchDao, Sml090SearchParameterModel prmModel) throws SQLException {
        String mailShubetsu = prmModel.getMailSyubetsu();

        ArrayList<SmailModel> resultList = new ArrayList<SmailModel>();

        Sml010Biz sml010biz = new Sml010Biz();

        if (GSConstSmail.TAB_DSP_MODE_JUSIN.equals(mailShubetsu)) {
            log__.debug("検索モード : [受信]");
            resultList = searchDao.getSearchDataJushin(prmModel);
            return sml010biz.__convertJmeisData(resultList);

        } else if (GSConstSmail.TAB_DSP_MODE_SOSIN.equals(mailShubetsu)) {
            log__.debug("データ検索モード : [送信]");
            resultList = searchDao.getSearchDataSoushin(prmModel);
             return sml010biz.__convertSmeisData(resultList);

        } else if (GSConstSmail.TAB_DSP_MODE_SOKO.equals(mailShubetsu)) {
            log__.debug("データ検索モード : [草稿]");
            resultList = searchDao.getSearchDataSoukou(prmModel);
            return sml010biz.__convertWmeisData(resultList);

        } else if (GSConstSmail.TAB_DSP_MODE_GOMIBAKO.equals(mailShubetsu)) {
            log__.debug("データ検索モード : [ゴミ箱]");
            resultList = searchDao.getSearchDataGomiBako(prmModel);
            return sml010biz.__convertJmeisData(resultList);
        }

        log__.debug("データ検索モード : [該当なし]");
        return null;
    }

    /**
     * <br>[機  能] 1ページ当りの表示件数取得
     * <br>[解  説] 拡張する時はこのメソッドで個人設定を読む。
     * <br>[備  考]
     * @param sessionUsrSid セッションユーザSID（現在未使用）
     * @param con コネクション
     * @return 1ページ当りの件数
     * @throws SQLException SQL実行時例外
     */
    private int __getDspPageCount(int sessionUsrSid, Connection con) throws SQLException {
        int limit = 0;
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz(reqMdl__);
        SmlAdminModel smlAdmMdl = smlCmnBiz.getSmailAdminConf(sessionUsrSid, con);

        if (smlAdmMdl.getSmaMaxDspStype() == GSConstSmail.DISP_CONF_ADMIN) {
            //管理者設定の表示設定を反映する
            limit = smlAdmMdl.getSmaMaxDsp();
        } else {
            //個人設定の表示設定を反映する
            SmlUserModel smlUsrMdl = smlCmnBiz.getSmailUserConf(sessionUsrSid, con);
            limit = smlUsrMdl.getSmlMaxDsp();
        }

        return limit;
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
    public ArrayList<SmailDetailModel> getTargetMailList(Sml090ParamModel paramMdl,
                                                        RequestModel reqMdl,
                                                        Connection con)
        throws SQLException {

        String mode = paramMdl.getSml090SvMailSyubetsu();
        //int sessionUserSid = __getSessionUserSid(reqMdl);
        ArrayList<SmailDetailModel> ret = null;
        //処理モード = 受信モード
        if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {
            SmlJmeisDao jDao = new SmlJmeisDao(con);
            ret = jDao.selectTargetJDetail(
                    paramMdl.getSmlViewAccount(), paramMdl.getSml090DelSid());
        //処理モード = 送信モード
        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
            SmlSmeisDao sDao = new SmlSmeisDao(con);
            ret = sDao.selectTargetSDetail(
                    paramMdl.getSmlViewAccount(), paramMdl.getSml090DelSid());
        //処理モード = 草稿モード
        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
            SmlWmeisDao wDao = new SmlWmeisDao(con);
            ret = wDao.selectTargetWDetail(
                    paramMdl.getSmlViewAccount(), paramMdl.getSml090DelSid());
        //処理モード = ゴミ箱モード
        } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
            SmailDao sDao = new SmailDao(con);
            ret = sDao.selectTargetGDetail(
                    paramMdl.getSmlViewAccount(), paramMdl.getSml090DelSid());
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
    public void deleteMessage(Sml090ParamModel paramMdl, RequestModel reqMdl, Connection con)
        throws SQLException {

        int sessionUserSid = __getSessionUserSid(reqMdl);
        String procMode = paramMdl.getSml090SvMailSyubetsu();
        String[] delSidList = paramMdl.getSml090DelSid();

        //処理モード = 受信モード
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
        } else if (procMode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {
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
            
            SmlCommonBiz smlBiz = new SmlCommonBiz();
            long delSize = 0;
            List<Integer> desSidListInt = Stream.of(delSidList)
                    .map(sid -> Integer.parseInt(sid.substring(1))).collect(Collectors.toList());

           /************************************************************************
            *
            * 受信、送信の場合は他のユーザのデータと参照しあうため論理削除とする。
            * 草稿に関しては自分のみのデータなので物理削除とする。
            *
            ************************************************************************/

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
                    delSize += map.getValue();
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
                    delSize += map.getValue();
                }
            }
            //草稿メッセージ(物理削除)
            if (!wMeis.isEmpty()) {
                //ショートメール情報(草稿)のデータ使用量を登録(削除対象のデータ使用量を減算)
                List<Integer> smwSidList
                    = Arrays.asList(delSidList).stream()
                        .filter(Objects::nonNull)
                        .map(sid -> sid.substring(1))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                SmlUsedDataBiz usedDataBiz = new SmlUsedDataBiz(con);
                usedDataBiz.insertSoukouDataSize(smwSidList, false);

                SmlWmeisDao wdao = new SmlWmeisDao(con);
                
                Map<SmlWmeisModel, Long> delMailList = wdao.getDeleteMail(
                        desSidListInt, 2, desSidListInt.size(), 0);
                for (Map.Entry<SmlWmeisModel, Long> map : delMailList.entrySet()) {
                    delSize += map.getValue();
                }
                
                wdao.deleteMsgButuri(paramMdl.getSmlViewAccount(), delSidList);
                SmlAsakDao adao = new SmlAsakDao(con);
                adao.deleteMsgButuri(sessionUserSid, delSidList);
            }
            smlBiz.updateAccountDiskSize(con, paramMdl.getSmlViewAccount(), -delSize);
        }
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
                                    Connection con, Sml090ParamModel paramMdl,
                                    MlCountMtController mtCon, int sessionUserSid)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String message = null;

        int type = paramMdl.getSml010addLabelType();


        int sacSid = paramMdl.getSmlViewAccount();

        List<String> errorList = new ArrayList<String>();

        try {
            String[] messageNum = paramMdl.getSml090DelSid();

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

                    String mode = paramMdl.getSml090MailSyubetsu();

                    //処理モード = 受信モード
                    if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                        SmlJmeisLabelModel smlJmeisModel = new SmlJmeisLabelModel();
                        SmlJmeisLabelDao smlJmeisDao = new SmlJmeisLabelDao(con);

                        for (String msgNum : messageNum) {

                            if (ValidateUtil.isNumber(msgNum)
                                    && Integer.valueOf(msgNum) > 0) {

                                smlJmeisModel.setSlbSid(labelSid);
                                smlJmeisModel.setSmjSid(Integer.valueOf(msgNum));
                                smlJmeisModel.setSacSid(sacSid);

                                smlJmeisDao.delete(Integer.valueOf(msgNum), labelSid);
                                smlJmeisDao.insert(smlJmeisModel);
                            }

                        }

                    //処理モード = 送信モード
                    } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {

                        SmlSmeisLabelModel smlSmeisModel = new SmlSmeisLabelModel();
                        SmlSmeisLabelDao smlSmeisDao = new SmlSmeisLabelDao(con);

                        for (String msgNum : messageNum) {

                            if (ValidateUtil.isNumber(msgNum)
                                    && Integer.parseInt(msgNum.substring(1)) > 0) {

                                smlSmeisModel.setSlbSid(labelSid);
                                smlSmeisModel.setSmsSid(Integer.parseInt(msgNum.substring(1)));
                                smlSmeisModel.setSacSid(sacSid);

                                smlSmeisDao.delete(Integer.parseInt(msgNum.substring(1)), labelSid);
                                smlSmeisDao.insert(smlSmeisModel);
                            }

                        }

                    //処理モード = 草稿モード
                    } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

                        SmlWmeisLabelModel smlWmeisModel = new SmlWmeisLabelModel();
                        SmlWmeisLabelDao smlWmeisDao = new SmlWmeisLabelDao(con);

                        for (String msgNum : messageNum) {

                            if (ValidateUtil.isNumber(msgNum)
                                    && Integer.parseInt(msgNum.substring(1)) > 0) {

                                smlWmeisModel.setSlbSid(labelSid);
                                smlWmeisModel.setSmwSid(Integer.parseInt(msgNum.substring(1)));
                                smlWmeisModel.setSacSid(sacSid);

                                smlWmeisDao.delete(Integer.parseInt(msgNum.substring(1)), labelSid);
                                smlWmeisDao.insert(smlWmeisModel);
                            }

                        }

                    //処理モード = ゴミ箱モード
                    } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {

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
                log__.debug("プロセスモード：" + paramMdl.getSml090MailSyubetsu());
                log__.debug("選択したSID配列(length)：" + paramMdl.getSml090DelSid().length);
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
    public void deleteLabelForMessage(Connection con, Sml090ParamModel paramMdl,
                                        RequestModel reqMdl) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);
        String message = null;

        List<String> errorList = new ArrayList<String>();

        try {
            String[] messageNum = paramMdl.getSml090DelSid();
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

                String mode = paramMdl.getSml090MailSyubetsu();

                //処理モード = 受信モード
                if (mode.equals(GSConstSmail.TAB_DSP_MODE_JUSIN)) {

                    SmlJmeisLabelDao smlJmeisDao = new SmlJmeisLabelDao(con);

                    for (String msgNum : messageNum) {

                        if (ValidateUtil.isNumber(msgNum)
                                && Integer.valueOf(msgNum) > 0) {

                            smlJmeisDao.delete(Integer.valueOf(msgNum), labelSid);
                        }
                    }

                //処理モード = 送信モード
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {

                    SmlSmeisLabelDao smlSmeisDao = new SmlSmeisLabelDao(con);

                    for (String msgNum : messageNum) {

                        if (ValidateUtil.isNumber(msgNum)
                                && Integer.parseInt(msgNum.substring(1)) > 0) {

                            smlSmeisDao.delete(Integer.parseInt(msgNum.substring(1)), labelSid);
                        }

                    }

                //処理モード = 草稿モード
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {

                    SmlWmeisLabelDao smlWmeisDao = new SmlWmeisLabelDao(con);

                    for (String msgNum : messageNum) {

                        if (ValidateUtil.isNumber(msgNum)
                                && Integer.parseInt(msgNum.substring(1)) > 0) {

                            smlWmeisDao.delete(Integer.parseInt(msgNum.substring(1)), labelSid);
                        }

                    }

                //処理モード = ゴミ箱モード
                } else if (mode.equals(GSConstSmail.TAB_DSP_MODE_GOMIBAKO)) {

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
     * <br>[機  能] メール詳細設定(受信モード)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitDataJusin(Sml090ParamModel paramMdl,
                                  RequestModel reqMdl,
                                  Connection con)
        throws SQLException {

        log__.debug("初期表示データ(受信モード)取得");

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        SmailDao sDao = new SmailDao(con);

        List<Sml010ExportFileModel> exportList = new ArrayList<Sml010ExportFileModel>();
        Sml010ExportFileModel exportMdl = null;

        if (paramMdl.getSml090DelSid() != null
                && paramMdl.getSml090DelSid().length > 0) {

            for (String smlSid : paramMdl.getSml090DelSid()) {

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
    public void setInitDataSosin(Sml090ParamModel paramMdl,
                                RequestModel reqMdl,
                                Connection con)
        throws SQLException {

        log__.debug("初期表示データ(送信モード)取得");

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        SmailDao sDao = new SmailDao(con);

        List<Sml010ExportFileModel> exportList = new ArrayList<Sml010ExportFileModel>();
        Sml010ExportFileModel exportMdl = null;

        if (paramMdl.getSml090DelSid() != null
                && paramMdl.getSml090DelSid().length > 0) {

            for (String smlSid : paramMdl.getSml090DelSid()) {

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
    public void setInitDataSoko(Sml090ParamModel paramMdl,
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

        if (paramMdl.getSml090DelSid() != null
                && paramMdl.getSml090DelSid().length > 0) {

            for (String smlSid : paramMdl.getSml090DelSid()) {

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
    public void setInitDataGomi(Sml090ParamModel paramMdl,
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

        if (paramMdl.getSml090SvMailSyubetsu().equals(GSConstSmail.TAB_DSP_MODE_LABEL)) {
            jtkbn = GSConstSmail.SML_JTKBN_TOROKU;
        }

        if (paramMdl.getSml090DelSid() != null
                && paramMdl.getSml090DelSid().length > 0) {

            for (String smlSid : paramMdl.getSml090DelSid()) {

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
                retMdl.setUsiSei(NullDefault.getString(
                        paramMdl.getUsiSei(), paramMdl.getAccountName()));
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
     * <br>[機  能] メール内容をPDF出力します。
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
     * @throws IOException IO実行時例外
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ZIP実行時例外
     */
    public SmlPdfModel createSmlPdf(
            Sml090ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl,
            String appRootPath,
            String outTempDir,
            String zipDir,
            String topStr)
        throws IOException, SQLException, IOToolsException {

        BaseUserModel usModel = reqMdl.getSmodel();
        SmlPdfModel pdfModel = new SmlPdfModel();
        List<Sml010ExportFileModel> exportList = paramMdl.getSml010ExportMailList();

        if (exportList != null && !exportList.isEmpty()) {

            //重複名チェックMap
            HashMap<String, Integer> nameMap = new HashMap<String, Integer>();

            //アカウント名
            String accName = usModel.getUsiseimei();

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
                    //宛先
                    String atesaki = __getAtesakiCV(smlList.get(0).getAtesakiList(), false);
                    //CC
                    String atesakiCC =  __getAtesakiCV(smlList.get(0).getCcList(), false);

                    String atesakiBCC = null;
                    String mailKbn = smlList.get(0).getMailKbn();
                    //送信区分のみ
                    if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                        //BCC
                        atesakiBCC = __getAtesakiCV(smlList.get(0).getBccList(), false);
                    }
                    if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                        SmailDao smailDao = new SmailDao(con);
                        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
                        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

                        ArrayList<AtesakiModel> ccList = null;
                        ArrayList<AtesakiModel> bccList = null;
                        ccList = smailDao.getSitagakiAtesakiList(
                                smlList.get(0).getSmlSid(),
                                GSConstSmail.SML_SEND_KBN_CC, sortMdl);
                        bccList = smailDao.getSitagakiAtesakiList(
                                smlList.get(0).getSmlSid(),
                                GSConstSmail.SML_SEND_KBN_BCC, sortMdl);
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
                    pdfModel = new SmlPdfModel();
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
                    log__.debug("メール内容PDF出力を終了します。");
                }
            }


            IOTools.isDirCheck(zipDir, true);
            String saveFilePath = zipDir + "/" + topStr + "smailPdf.zip";
            ZipUtil.zipDir(outTempDir + "/", saveFilePath);

            pdfModel.setFileName(topStr + "smailPdf.zip");
            pdfModel.setSaveFileName(saveFilePath);


        }
        return pdfModel;
    }
    /**
     * <br>[機  能] メール内容をPDF出力します。
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
    public SmlPdfModel createSmlEml(
            Sml090ParamModel paramMdl,
            Connection con,
            RequestModel reqMdl,
            String appRootPath,
            String outTempDir,
            String zipDir,
            String topStr)
        throws Exception {

//        BaseUserModel usModel = reqMdl.getSmodel();
        SmlPdfModel pdfModel = new SmlPdfModel();
        List<Sml010ExportFileModel> exportList = paramMdl.getSml010ExportMailList();

        Sml030Biz sml030Biz = new Sml030Biz();

        if (exportList != null && !exportList.isEmpty()) {

            //重複名チェックMap
            HashMap<String, Integer> nameMap = new HashMap<String, Integer>();
//
//            //アカウント名
//            String accName = usModel.getUsiseimei();

            for (Sml010ExportFileModel exportMdl : exportList) {

                ArrayList<SmailDetailModel> smlList = exportMdl.getSmlList();

                if (smlList != null && !smlList.isEmpty()) {
                    PrintWriter pw = null;
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
                        String sender = smlList.get(0).getUsiSei()
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


                        String mailKbn = NullDefault.getString(smlList.get(0).getMailKbn(), "");

                        if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOSIN)) {
                            bccList = smlList.get(0).getBccList();
                        }

                        if (mailKbn.equals(GSConstSmail.TAB_DSP_MODE_SOKO)) {
                            SmailDao smailDao = new SmailDao(con);
                            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
                            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();

                            ccList = smailDao.getSitagakiAtesakiList(
                                    smlList.get(0).getSmlSid(),
                                    GSConstSmail.SML_SEND_KBN_CC, sortMdl);
                            bccList = smailDao.getSitagakiAtesakiList(
                                    smlList.get(0).getSmlSid(),
                                    GSConstSmail.SML_SEND_KBN_BCC, sortMdl);
                        }


                        //本文
                        String main = smlList.get(0).getSmsBody();

                        String mailDate = UDateUtil.getYYMD(date) + "_"
                        + UDateUtil.getSeparateHMS(date);
                        String fileName = mailDate + "_";
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
                            pw = new PrintWriter(new OutputStreamWriter(fos, charset));

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

            pdfModel.setFileName(topStr + "smailEml.zip");
            pdfModel.setSaveFileName(saveFilePath);


        }
        return pdfModel;
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
    public void selsRead(Sml090ParamModel paramMdl, RequestModel reqMdl, Connection con, int kbn)
        throws SQLException {

        int sessionUserSid = reqMdl.getSmodel().getUsrsid();
        //受信メッセージの開封区分を変更
        SmlJmeisDao jdao = new SmlJmeisDao(con);

        if (paramMdl.getSml090DelSid() != null && paramMdl.getSml090DelSid().length > 0) {
            jdao.updateOpkbn(paramMdl.getSml090DelSid(), sessionUserSid,
                    paramMdl.getSmlViewAccount(), kbn, new UDate());
        }

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
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return テンポラリディレクトリパス
     */
    public String getTempDir() {
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        return smlCmnBiz.getTempDir(reqMdl__, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void deleteTempDir() {
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        smlCmnBiz.deleteTempDir(reqMdl__, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを初期化
     * <br>[解  説]
     * <br>[備  考]
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void clearTempDir() throws IOToolsException {
        SmlCommonBiz smlCmnBiz = new SmlCommonBiz();
        smlCmnBiz.clearTempDir(reqMdl__, SCR_ID);
    }
}