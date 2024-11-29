package jp.groupsession.v2.wml.wml030;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.biz.oauth.OAuthBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.model.OauthDataModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;

/**
 * <br>[機  能] WEBメール アカウントマネージャー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml030Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml030Biz.class);

    /** 画面ID */
    public static final String SCR_ID = "wml030";

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     * @throws Exception 認証情報取得時に例外発生
     */
    public void setInitData(Connection con, Wml030ParamModel paramMdl,
                            RequestModel reqMdl)
    throws SQLException, Exception {
        //検索結果を取得する
        //1ページ最大30件
        int limit = GSConstWebmail.LIMIT_DSP_ACCOUNT;
        Wml030SearchModel searchMdl = __createSearchModel(paramMdl);
        searchMdl.setMaxCount(limit);

        //件数カウント
        Wml030Dao dao = new Wml030Dao(con);
        long maxCount = dao.recordCount(searchMdl);
        log__.debug("表示件数 = " + maxCount);

        //現在ページ（ページコンボのvalueを設定）
        int nowPage = paramMdl.getWml030pageTop();
        //結果取得開始カーソル位置
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        if (maxPageNum > 1) {
            paramMdl.setWml030pageDspFlg(true);
        }
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        searchMdl.setPage(start);

        //ページング
        paramMdl.setWml030pageTop(nowPage);
        paramMdl.setWml030pageBottom(nowPage);
        paramMdl.setPageCombo(PageUtil.createPageOptions(maxCount, limit));

        //アカウント一覧取得
        List<Wml030AccountModel> accountList = dao.getAccountList(searchMdl, reqMdl);
        paramMdl.setAccountList(accountList);

        //グループコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        GroupBiz grpBiz = new GroupBiz();
        List<LabelValueBean> groupCombo = new ArrayList<LabelValueBean>();
        groupCombo.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));

        ArrayList<GroupModel> grpList = grpBiz.getGroupCombList(con);
        for (GroupModel grpMdl : grpList) {
            LabelValueBean label = new LabelValueBean(grpMdl.getGroupName(),
                                                    String.valueOf(grpMdl.getGroupSid()));
            groupCombo.add(label);
        }
        paramMdl.setGroupCombo(groupCombo);

        //ユーザコンボを設定
        List<UsrLabelValueBean> userCombo = new ArrayList<UsrLabelValueBean>();

        UserBiz userBiz = new UserBiz();
        userCombo.addAll(userBiz.getUserLabelList(con, gsMsg, paramMdl.getWml030group()));
        paramMdl.setUserCombo(userCombo);

        //認証コンボを設定
        paramMdl.setNinsyoCombo(__createNinsyoCombo(reqMdl));
    }

    /**
     * <br>[機  能]認証コンボの生成
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return ninsyoCombo
     */
    private List<LabelValueBean> __createNinsyoCombo(RequestModel reqMdl) {

        List<LabelValueBean> ninsyoCombo = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        //指定なし
        ninsyoCombo.add(new LabelValueBean(gsMsg.getMessage("cmn.without.specifying"), "-1"));

        //基本認証
        String normal = String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_NORMAL);
        ninsyoCombo.add(new LabelValueBean(gsMsg.getMessage("wml.309"), normal));

        //OAuth
        String oauth = String.valueOf(GSConstWebmail.WAC_AUTH_TYPE_OAUTH);
        ninsyoCombo.add(new LabelValueBean(gsMsg.getMessage("wml.310"), oauth));

        return ninsyoCombo;
    }

    /**
     * <br>[機  能] メッセージに表示するアカウント名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param accountSidList アカウントSID
     * @return アカウント名
     * @throws SQLException SQL実行時例外
     */
    public String getMsgAccountTitle(Connection con, String[] accountSidList)
    throws SQLException {

        Wml030Dao wml030Dao = new Wml030Dao(con);
        List<String> titleList = wml030Dao.getAccountNameList(accountSidList);

        String msgTitle = "";
        for (int idx = 0; idx < titleList.size(); idx++) {

            //最初の要素以外は改行を挿入
            if (idx > 0) {
                msgTitle += "<br>";
            }

            msgTitle += "・ " + StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(titleList.get(idx), ""));
        }

        return msgTitle;
    }

    /**
     * <br>[機  能] アカウントの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteAccount(Connection con, Wml030ParamModel paramMdl, int userSid)
    throws SQLException {

        log__.debug("アカウント削除開始");

        boolean commit = false;
        try {
            WmlAccountDao accountDao = new WmlAccountDao(con);
            accountDao.updateJkbn(paramMdl.getWml030selectAcount(), GSConstWebmail.WAC_JKBN_DELETE);
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.error("アカウントの削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        log__.debug("アカウント削除終了");
    }

    /**
     * <br>[機  能] エクスポート情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @return エクスポート情報
     * @throws SQLException SQL実行時例外
     */
    public List<Wml030ExportModel> getExportData(Connection con, Wml030ParamModel paramMdl)
    throws SQLException {
        Wml030SearchModel searchMdl = __createSearchModel(paramMdl);

        Wml030Dao dao = new Wml030Dao(con);
        return dao.getExportData(searchMdl);
    }

    /**
     * <br>[機  能] 検索条件を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl 検索条件
     * @return 検索条件
     */
    private Wml030SearchModel __createSearchModel(Wml030ParamModel paramMdl) {
        //検索結果を取得する
        Wml030SearchModel searchMdl = new Wml030SearchModel();
        searchMdl.setKeyword(paramMdl.getWml030svKeyword());
        searchMdl.setGrpSid(paramMdl.getWml030svGroup());
        searchMdl.setUserSid(paramMdl.getWml030svUser());
        searchMdl.setNinsyoKbn(paramMdl.getWml030svNinsyo());
        searchMdl.setNinsyoStatusKbn(paramMdl.getWml030svNinsyoStatus());
        searchMdl.setSortKey(paramMdl.getWml030sortKey());
        searchMdl.setOrder(paramMdl.getWml030order());

        return searchMdl;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl) {
        WmlBiz wmlBiz = new WmlBiz();
        return wmlBiz.getTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void deleteTempDir(RequestModel reqMdl) {
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.deleteTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを初期化する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @throws IOToolsException テンポラリディレクトリの作成に失敗
     */
    public void clearTempDir(RequestModel reqMdl)
    throws IOToolsException {
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.clearTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] 認証状態の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     * @throws Exception 認証情報取得時に例外発生
     */
    public void reloadAuthStatus(Connection con, Wml030ParamModel paramMdl,
                            RequestModel reqMdl)
    throws SQLException, Exception {
        //検索結果を取得する
        //1ページ最大30件
        int limit = GSConstWebmail.LIMIT_DSP_ACCOUNT;
        Wml030SearchModel searchMdl = __createSearchModel(paramMdl);
        searchMdl.setMaxCount(limit);

        //現在ページ（ページコンボのvalueを設定）
        int nowPage = paramMdl.getWml030pageTop();
        //結果取得開始カーソル位置
        int start = PageUtil.getRowNumber(nowPage, limit);
        searchMdl.setPage(start);

        Wml030Dao dao = new Wml030Dao(con);
        CmnOauthDao couDao = new CmnOauthDao(con);
        OAuthBiz authBiz = new OAuthBiz();
        List<Wml030AccountModel> accountList = dao.getAccountList(searchMdl, reqMdl);
        for (Wml030AccountModel accountMdl : accountList) {
            //認証形式 = OAuthの場合、認証状態の更新を行う
            if (accountMdl.getAuthType() == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {
                CmnOauthModel wouMdl = couDao.select(accountMdl.getCouSid());
                OauthDataModel authData = new OauthDataModel();
                authData.setProvider(wouMdl.getCouProvider());
                authData.setClientId(wouMdl.getCouAuthId());
                authData.setSecretKey(wouMdl.getCouAuthSecret());
                authData.setRefreshToken(accountMdl.getRefreshToken());
                authBiz.getAccessToken(con, authData, accountMdl.getCotSid() , accountMdl.getCouSid());
            }
        }
    }
}
