package jp.groupsession.v2.wml.wml260kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.dao.base.WmlMailSendplanDao;
import jp.groupsession.v2.wml.wml260.Wml260MailDataModel;
import jp.groupsession.v2.wml.wml260.Wml260MaildataDspModel;
import jp.groupsession.v2.wml.wml260.Wml260SearchParameterModel;

/**
 * <br>[機  能] WEBメール 送信予定メール管理確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml260knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml260knBiz.class);

    /**
     * <br>[機  能] メール送受信ログ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors setInitData(
            Connection con,
            Wml260knParamModel paramMdl,
            RequestModel reqMdl)
        throws SQLException {

        ActionErrors errors = new ActionErrors();
        //1ページ最大50件
        int limit = GSConstWebmail.LIMIT_DSP_MAILLOG;

        Wml260SearchParameterModel prmModel = new Wml260SearchParameterModel();

        prmModel.setMailNumList(paramMdl.getWml260selectMailNum());
        prmModel.setSortKey(paramMdl.getWml260sortKey());
        prmModel.setOrder(paramMdl.getWml260order());
        prmModel.setLimit(limit);
        long maxCount = 0;

        //件数カウント
        Wml260knDao dao = new Wml260knDao(con);
        maxCount = dao.recordCount(prmModel);
        log__.debug("表示件数 = " + maxCount);

        //現在ページ（ページコンボのvalueを設定）
        int nowPage = paramMdl.getWml260pageTop();
        //結果取得開始カーソル位置
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        if (maxPageNum > 1) {
            paramMdl.setWml260pageDspFlg(true);
        }
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        prmModel.setStart(start);

        //ページング
        paramMdl.setWml260pageTop(nowPage);
        paramMdl.setWml260pageBottom(nowPage);
        paramMdl.setPageList(PageUtil.createPageOptions(maxCount, limit));

        //検索結果取得
        List<Wml260MailDataModel> mDataMdlList = new ArrayList<Wml260MailDataModel>();
        mDataMdlList = dao.getSearchData(prmModel);

        //表示用ModelList
        List<Wml260MaildataDspModel> mDataDspList = new ArrayList<Wml260MaildataDspModel>();

        Wml260MaildataDspModel mDataDspMdl = null;

        for (Wml260MailDataModel mDataMdl : mDataMdlList) {

            mDataDspMdl = new Wml260MaildataDspModel();
            //メッセージ番号
            mDataDspMdl.setWmdMailnum(String.valueOf(mDataMdl.getWmdMailnum()));
            //アカウント名
            mDataDspMdl.setAccountName(mDataMdl.getAccountName());
            //件名
            mDataDspMdl.setWlgTitle(mDataMdl.getWlgTitle());
            //日付
            mDataDspMdl.setWlgDate(UDateUtil.getSlashYYMD(mDataMdl.getWlgDate()));
            //時間
            mDataDspMdl.setWlgTime(UDateUtil.getSeparateHMS(mDataMdl.getWlgDate()));
            //送信元
            mDataDspMdl.setWlgFrom(mDataMdl.getWlgFrom());
            //添付ファイルフラグ
            mDataDspMdl.setWlgTempFlg(String.valueOf(mDataMdl.getWlgTempFlg()));
            //メールアドレス
            mDataDspMdl.setWlsAddress(String.valueOf(mDataMdl.getWlsAddress()));

            mDataDspList.add(mDataDspMdl);
        }

        paramMdl.setWml260SendResvList(mDataDspList);

        return errors;
    }

    /**
     * <br>[機  能] メール送受信ログ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void cancelSendMail(
            Connection con,
            Wml260knParamModel paramMdl,
            RequestModel reqMdl)
        throws SQLException {

        Wml260knDao dao = new Wml260knDao(con);
        Map<Integer, List<Long>> draftDirectoryMap =
                dao.getDraftDirectoryMap(paramMdl.getWml260selectMailNum());

        WebmailDao webmailDao = new WebmailDao(con);
        WmlMailSendplanDao sendplanDao = new WmlMailSendplanDao(con);
        int draftDirSid = 0;
        long[] cancelMailNum = null;
        List<Long> mailNumList = null;
        Iterator<Integer> draftDirIter = draftDirectoryMap.keySet().iterator();
        while (draftDirIter.hasNext()) {
            draftDirSid = draftDirIter.next();
            mailNumList = draftDirectoryMap.get(draftDirSid);
            cancelMailNum = new long[mailNumList.size()];
            for (int mailIdx = 0; mailIdx < mailNumList.size(); mailIdx++) {
                cancelMailNum[mailIdx] = mailNumList.get(mailIdx);
            }
            webmailDao.moveMail(cancelMailNum, draftDirSid);

            sendplanDao.updateSendKbn(cancelMailNum, GSConstWebmail.WSP_SENDKBN_NOSET);
        }
    }

    /**
     * <br>[機  能] メール送受信ログ一覧を取得する(ログ用)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param mailNumList WEBメールNoリスト
     * @return ログリスト
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<String> getLogList(
            Connection con,
            long[] mailNumList)
        throws SQLException {

        Wml260knDao dao = new Wml260knDao(con);
        ArrayList<String> list = dao.getLogList(mailNumList);
        return list;
    }
}
