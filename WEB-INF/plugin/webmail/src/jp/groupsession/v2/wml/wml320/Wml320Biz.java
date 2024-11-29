package jp.groupsession.v2.wml.wml320;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlDelmailListDao;
import jp.groupsession.v2.wml.dao.base.WmlDelmailTempDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.dao.base.WmlSendaddressDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;
import jp.groupsession.v2.wml.wml010.Wml010Biz;

/**
 * <br>[機  能] WEBメール 個人設定 メール情報一括削除画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class Wml320Biz {

    /** コネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     * @param con コネクション
     */
    public Wml320Biz(Connection con,
                     RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Wml320knParamModel
     * @param buMdl セッションユーザモデル
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     */
    protected void _setInitData(
            Wml320ParamModel paramMdl,
            BaseUserModel buMdl,
            String appRootPath)
                    throws SQLException {

        //初期表示時、一時保管情報を削除する
        if (paramMdl.getWml320initFlg() != GSConstWebmail.NOT_INIT_FLG) {
            con__.setAutoCommit(false);
            boolean commit = false;
            try {
                _deleteWmlDelMail(paramMdl, buMdl);
                paramMdl.setWml320initFlg(GSConstWebmail.NOT_INIT_FLG);
                con__.commit();
                commit = true;
            } finally {
                if (!commit) {
                    JDBCUtil.rollback(con__);
                }
                con__.setAutoCommit(true);
            }
        }

        //アカウント情報を取得する
        __getAccountData(buMdl, paramMdl);

        //ラベル一覧を取得する
        __getLabelData(paramMdl);

        //検索実行時は削除対象メール一覧を取得する
        if (paramMdl.isWml320searchFlg()) {
            __getSearchResultData(buMdl, paramMdl);

        }

        //ソートキー一覧を取得する
        __setSortkeyCombo(paramMdl);

        //削除件数上限を設定
        paramMdl.setWml320delLimit(getDeleteMailLimit(appRootPath));

        //選択アカウントの一括削除実行中メール件数を設定
        int wacSid = paramMdl.getWml320account();
        paramMdl.setDeleteMailCount(
                getDeleteMailCount(wacSid));

        //選択アカウントの名称を設定
        WmlBiz wmlBiz = new WmlBiz();
        paramMdl.setAccountName(wmlBiz.getAccountName(con__, wacSid));
    }

    /**
     * <br>検索結果情報を取得
     * @param buMdl セッションユーザーモデル
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    private void __getSearchResultData(BaseUserModel buMdl,
            Wml320ParamModel paramMdl) throws SQLException {

        WmlDelmailTempDao delMailTempDao = new WmlDelmailTempDao(con__);

        //件数
        int mailCnt = delMailTempDao.getDataCount(buMdl.getUsrsid());

        if (mailCnt > 0) {
            //ページ調整
            int page = paramMdl.getWml320pageTop();
            if (page <= 0) {
                page = 1;
            }
            int start = PageUtil.getRowNumber(page, GSConstWebmail.LIMIT_DSP_DELETELIST);
            int maxPageNum = PageUtil.getPageCount(mailCnt, GSConstWebmail.LIMIT_DSP_DELETELIST);
            int maxPageStartRow
                = PageUtil.getRowNumber(maxPageNum, GSConstWebmail.LIMIT_DSP_DELETELIST);
            if (maxPageStartRow < start) {
                page = maxPageNum;
                start = maxPageStartRow;
            }
            if (maxPageNum > 1) {
                paramMdl.setWml320pageCombo(
                            PageUtil.createPageOptions(mailCnt, Wml010Biz.MAILLIST_MAXCOUNT));
            }
            paramMdl.setWml320pageTop(page);
            paramMdl.setWml320pageBottom(page);

            Wml320Dao wml320Dao = new Wml320Dao(con__);
            WmlSendaddressDao sendDao = new WmlSendaddressDao(con__);
            List <Wml320DspModel> mailList = wml320Dao.getMailList(
                    buMdl.getUsrsid(),
                    paramMdl.getWml320svSortKey(),
                    paramMdl.getWml320svOrder(),
                    start);

            if (mailList != null && !mailList.isEmpty()) {
                for (Wml320DspModel mdl : mailList) {
                    String dest
                        = sendDao.getSendAdress(paramMdl.getWml320account(), mdl.getMailNum());
                    mdl.setDest(dest);
                }
            }
            paramMdl.setWml320mailList(mailList);
        }
    }

    /**
     * <br>[機  能] ラベルコンボボックスの選択値を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    private void __getLabelData(Wml320ParamModel paramMdl) throws SQLException {

        WmlLabelDao labelDao = new WmlLabelDao(con__);
        List<WmlLabelModel> labelList = labelDao.getLabelList(paramMdl.getWml320account());
        List<LabelValueBean> labelcombo = new ArrayList<LabelValueBean>();
        labelcombo.add(
                new LabelValueBean("-", "-1"));
        for (WmlLabelModel labelData : labelList) {
            LabelValueBean label
                    = new LabelValueBean(labelData.getWlbName(),
                                        String.valueOf(labelData.getWlbSid()));
            labelcombo.add(label);
        }
        paramMdl.setWml320labelCombo(labelcombo);
    }

    /**
     * <br>[機  能] アカウント情報を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param buMdl セッションユーザーモデル
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    private void __getAccountData(BaseUserModel buMdl,
            Wml320ParamModel paramMdl) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        WmlAccountDao accountDao = new WmlAccountDao(con__);
        List<WmlAccountModel> accountList = accountDao.getAccountList(buMdl.getUsrsid());

        List<LabelValueBean> accountCombo = new ArrayList<LabelValueBean>();
        for (WmlAccountModel accountData : accountList) {
            LabelValueBean accountLabel
                    = new LabelValueBean(accountData.getWacName(),
                                        String.valueOf(accountData.getWacSid()));
            accountCombo.add(accountLabel);
        }
        if (accountCombo.isEmpty()) {
            accountCombo.add(
                    new LabelValueBean(gsMsg.getMessage("wml.132"), "-1"));
        }
        paramMdl.setAccountCombo(accountCombo);
        //アカウント未設定の場合デフォルトアカウントSIDを設定する
        if (paramMdl.getWml320account() <= 0) {
            paramMdl.setWml320account(accountDao.getDefaultAccountSid(buMdl.getUsrsid()));
        }
    }

    /**
     * <br>[機  能] ソートキーコンボボックスの選択値を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     */
    private void __setSortkeyCombo(Wml320ParamModel paramMdl) {

        List<LabelValueBean> combo = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        combo.add(
                new LabelValueBean(gsMsg.getMessage("cmn.date"),
                                String.valueOf(Wml320Form.WML320_SKEY_DATE)));
        combo.add(
                new LabelValueBean(gsMsg.getMessage("cmn.sendfrom"),
                                String.valueOf(Wml320Form.WML320_SKEY_FROM)));
        combo.add(
                new LabelValueBean(gsMsg.getMessage("cmn.subject"),
                                String.valueOf(Wml320Form.WML320_SKEY_SUBJECT)));

        paramMdl.setWml320sortKeyCombo(combo);
    }

    /**
     * <br>[機  能] wmlDelMailDao削除処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Wml320knParamModel
     * @param buMdl BaseUserModel
     * @throws SQLException SQLException
     */
    protected void _deleteWmlDelMail(Wml320ParamModel paramMdl,
            BaseUserModel buMdl)
            throws SQLException {

        //一括削除対象メール_一時保管の削除を行う
        WmlDelmailTempDao delMailTempDao = new WmlDelmailTempDao(con__);
        delMailTempDao.delete(buMdl.getUsrsid());
    }

    /**
     * <br>[機  能] 検索実行時処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Wml320ParamModel
     * @param buMdl セッションユーザモデル
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     */
    protected void _getSearchData(Wml320ParamModel paramMdl,
            BaseUserModel buMdl,
            String appRootPath) throws SQLException {

        //検索条件保持用パラメータを設定
        __setParamModel(paramMdl);

        //一括削除対象メールの削除をおこなう
        _deleteWmlDelMail(paramMdl, buMdl);

        //検索モデルを作成
        Wml320SearchModel searchMdl = __getSearchModel(paramMdl, appRootPath);
        searchMdl.setUsrSid(buMdl.getUsrsid());

        //検索条件と一致するメール情報を一括削除対象メールに登録する
        Wml320Dao wml320dao = new Wml320Dao(con__);
        wml320dao.insertDelMailList(searchMdl);
    }

    /**
     * <br>[機  能] メール情報一括削除処理の最大メール件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param appRootPath アプリケーションルートパス
     * @return 最大メール件数
     */
    public int getDeleteMailLimit(String appRootPath) {
        return WmlBiz.getConfValue(appRootPath,
                GSConstWebmail.MAILCONF_DELETE_MAIL_LIMIT,
                GSConstWebmail.DELETE_MAIL_LIMIT_DEFAULT);
    }

    /**
     * <br>[機  能] 指定したアカウントの「削除中」メール件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @return 削除中メール件数
     * @throws SQLException SQL実行時例外
     */
    public int getDeleteMailCount(int wacSid)
    throws SQLException {
        WmlDelmailListDao delListDao = new WmlDelmailListDao(con__);
        return delListDao.getDelmailCount(wacSid);
    }

    /**
     * <br>[機  能] UDateのインスタンスを生成します。
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param year 年
     * @param month 月
     * @param day 日
     * @return UDate
     */
    private UDate __createUDate(int year, int month, int day) {
        if (year <= 0 || month <= 0 || day <= 0) {
            return null;
        }

        UDate date = UDate.getInstance(0);
        date.setDate(year, month, day);

        return date;
    }

    /**
     * <br>[機  能] 検索保持用モデルを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Wml320ParamModel
     * @return paramMdl Wml320ParamModel
     */
    private Wml320ParamModel __setParamModel(Wml320ParamModel paramMdl) {

        paramMdl.setWml320svAccount(paramMdl.getWml320account());
        paramMdl.setWml320svDirectory(paramMdl.getWml320directory());
        paramMdl.setWml320svKeyword(paramMdl.getWml320keyword());
        paramMdl.setWml320svFrom(paramMdl.getWml320from());
        paramMdl.setWml320svReaded(paramMdl.getWml320readed());
        paramMdl.setWml320svDest(paramMdl.getWml320dest());
        paramMdl.setWml320svDestTypeTo(paramMdl.getWml320destTypeTo());
        paramMdl.setWml320svDestTypeBcc(paramMdl.getWml320destTypeBcc());
        paramMdl.setWml320svDestTypeCc(paramMdl.getWml320destTypeCc());
        paramMdl.setWml320svAttach(paramMdl.getWml320attach());
        paramMdl.setWml320svDateType(paramMdl.getWml320dateType());
        paramMdl.setWml320svDateYearFr(paramMdl.getWml320dateYearFr());
        paramMdl.setWml320svDateMonthFr(paramMdl.getWml320dateMonthFr());
        paramMdl.setWml320svDateDayFr(paramMdl.getWml320dateDayFr());
        paramMdl.setWml320svDateYearTo(paramMdl.getWml320dateYearTo());
        paramMdl.setWml320svDateMonthTo(paramMdl.getWml320dateMonthTo());
        paramMdl.setWml320svDateDayTo(paramMdl.getWml320dateDayTo());
        paramMdl.setWml320svLabel(paramMdl.getWml320label());
        paramMdl.setWml320svSize(paramMdl.getWml320size());
        paramMdl.setWml320svSortKey(paramMdl.getWml320sortKey());
        paramMdl.setWml320svOrder(paramMdl.getWml320order());

        return paramMdl;
    }

    /**
     * <br>[機  能] 検索モデルを設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl Wml320ParamModel
     * @param appRootPath アプリケーションルートパス
     * @return searchMdl 検索モデル
     */
    private Wml320SearchModel __getSearchModel(Wml320ParamModel paramMdl, String appRootPath) {

        Wml320SearchModel searchMdl = new Wml320SearchModel();
        searchMdl.setAccount(paramMdl.getWml320account());
        searchMdl.setDirectory(paramMdl.getWml320directory());
        searchMdl.setKeyword(paramMdl.getWml320keyword());
        searchMdl.setFrom(paramMdl.getWml320from());
        searchMdl.setReaded(paramMdl.getWml320readed());
        searchMdl.setDest(paramMdl.getWml320dest());
        searchMdl.setDestTypeTo(paramMdl.getWml320destTypeTo());
        searchMdl.setDestTypeBcc(paramMdl.getWml320destTypeBcc());
        searchMdl.setDestTypeCc(paramMdl.getWml320destTypeCc());
        searchMdl.setDateFr(__createUDate(paramMdl.getWml320dateYearFr(),
                paramMdl.getWml320dateMonthFr(),
                paramMdl.getWml320dateDayFr()));
        searchMdl.setDateTo(__createUDate(paramMdl.getWml320dateYearTo(),
                paramMdl.getWml320dateMonthTo(),
                paramMdl.getWml320dateDayTo()));
        searchMdl.setAttach(paramMdl.getWml320attach());
        searchMdl.setDateType(paramMdl.getWml320dateType());
        searchMdl.setLabel(paramMdl.getWml320label());
        if (ValidateUtil.isEmpty(paramMdl.getWml320size()) == false) {
            searchMdl.setSize(Integer.valueOf(paramMdl.getWml320size()));
        }
        searchMdl.setSortKey(paramMdl.getWml320sortKey());
        searchMdl.setOrder(paramMdl.getWml320sortKey());
        searchMdl.setDelLimit(getDeleteMailLimit(appRootPath));

        return searchMdl;
    }
}