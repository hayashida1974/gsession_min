package jp.groupsession.v2.wml.wml070;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlBiz;

/**
 * <br>[機  能] WEBメール 送受信ログ管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml070Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml070Biz.class);

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
            Wml070ParamModel paramMdl,
            RequestModel reqMdl)
        throws SQLException {

        //画面表示情報を設定
        setDsp(con, paramMdl, reqMdl);

        //メール送受信ログ一覧を取得する(検索を行う)
        return __getResvSendList(paramMdl, con);
    }

    /**
     * <br>[機  能] メール送受信ログ一覧を取得する(検索を行う)
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    private ActionErrors __getResvSendList(Wml070ParamModel paramMdl, Connection con)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        //1ページ最大50件
        int limit = GSConstWebmail.LIMIT_DSP_MAILLOG;

        Wml070SearchParameterModel prmModel = __getSvSearchParameter(paramMdl);

        prmModel.setLimit(limit);
        long maxCount = 0;

        //件数カウント
        Wml070Dao dao = new Wml070Dao(con);
        maxCount = dao.recordCount(prmModel);
        log__.debug("表示件数 = " + maxCount);

        //現在ページ（ページコンボのvalueを設定）
        int nowPage = paramMdl.getWml070pageTop();
        //結果取得開始カーソル位置
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        if (maxPageNum > 1) {
            paramMdl.setWml070pageDspFlg(true);
        }
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        prmModel.setStart(start);

        //ページング
        paramMdl.setWml070pageTop(nowPage);
        paramMdl.setWml070pageBottom(nowPage);
        paramMdl.setPageList(PageUtil.createPageOptions(maxCount, limit));

        //検索結果取得
        List<Wml070MailDataModel> mDataMdlList = new ArrayList<Wml070MailDataModel>();
        mDataMdlList = dao.getSearchData(prmModel);

        //表示用ModelList
        List<Wml070MaildataDspModel> mDataDspList = new ArrayList<Wml070MaildataDspModel>();

        Wml070MaildataDspModel mDataDspMdl = null;

        for (Wml070MailDataModel mDataMdl : mDataMdlList) {

            mDataDspMdl = new Wml070MaildataDspModel();
            //メッセージ番号
            mDataDspMdl.setWmdMailnum(String.valueOf(mDataMdl.getWmdMailnum()));
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
            //メール種別
            mDataDspMdl.setWlgMailType(String.valueOf(mDataMdl.getWlgMailType()));
            //種別
            mDataDspMdl.setWlsType(String.valueOf(mDataMdl.getWlsType()));
            //メールアドレス
            mDataDspMdl.setWlsAddress(String.valueOf(mDataMdl.getWlsAddress()));

            mDataDspList.add(mDataDspMdl);
        }

        paramMdl.setWml070SendResvList(mDataDspList);

        return errors;
    }

    /**
     * <br>[機  能] 画面表示情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setDsp(Connection con, Wml070ParamModel paramMdl, RequestModel reqMdl)
        throws SQLException {

        //送受信ログの登録 を設定
        WmlBiz wmlBiz = new WmlBiz();
        paramMdl.setWmlEntryMailLogFlg(wmlBiz.isEntryMailLog(con));
    }

    /**
     * <br>[機  能] パラメータモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return Wml070SearchParameterModel
     */
    private Wml070SearchParameterModel __getSvSearchParameter(Wml070ParamModel paramMdl) {

        Wml070SearchParameterModel prmModel = new Wml070SearchParameterModel();

        prmModel.setTextWordTitle(NullDefault.getString(paramMdl.getWml070svTitle(), ""));
        prmModel.setWml070Address(NullDefault.getString(paramMdl.getWml070svAddress(), ""));
        prmModel.setWml070AddressFrom(paramMdl.getWml070svAddressFrom());
        prmModel.setWml070AddressTo(paramMdl.getWml070svAddressTo());
        prmModel.setWml070SendDateYear(paramMdl.getWml070svSendDateYear());
        prmModel.setWml070SendDateMonth(paramMdl.getWml070svSendDateMonth());
        prmModel.setWml070SendDateDay(paramMdl.getWml070svSendDateDay());
        prmModel.setWml070SendDateCondition(paramMdl.getWml070svSendDateCondition());
        prmModel.setWml070SendDateYearTo(paramMdl.getWml070svSendDateYearTo());
        prmModel.setWml070SendDateMonthTo(paramMdl.getWml070svSendDateMonthTo());
        prmModel.setWml070SendDateDayTo(paramMdl.getWml070svSendDateDayTo());
        prmModel.setWml070SendDateCondition(paramMdl.getWml070svSendDateCondition());
        prmModel.setWml070Type(paramMdl.getWml070svType());
        prmModel.setSortKey(paramMdl.getWml070sortKey());
        prmModel.setOrder(paramMdl.getWml070order());

        UDate sendDate = new UDate();
        //一致する or 以降 or 範囲指定
        if (paramMdl.getWml070SendDateCondition() != GSConstWebmail.DATE_KBN_BEFORE) {
            sendDate.setTime(0);
            sendDate.setYear(paramMdl.getWml070svSendDateYear());
            sendDate.setMonth(paramMdl.getWml070svSendDateMonth());
            sendDate.setDay(paramMdl.getWml070svSendDateDay());
            sendDate.setHour(0);
            sendDate.setMinute(0);
            sendDate.setSecond(0);
            sendDate.setMilliSecond(0);
            prmModel.setWriteDate(sendDate);

        //以前
        } else {
            sendDate.setTime(0);
            sendDate.setYear(paramMdl.getWml070svSendDateYear());
            sendDate.setMonth(paramMdl.getWml070svSendDateMonth());
            sendDate.setDay(paramMdl.getWml070svSendDateDay());
            sendDate.setHour(23);
            sendDate.setMinute(59);
            sendDate.setSecond(59);
            sendDate.setMilliSecond(999);
            prmModel.setWriteDate(sendDate);
        }

        //範囲指定
        if (paramMdl.getWml070SendDateCondition() == GSConstWebmail.DATE_KBN_DATEAREA) {
            UDate sendDateTo = new UDate();
            sendDateTo.setTime(0);
            sendDateTo.setYear(paramMdl.getWml070svSendDateYearTo());
            sendDateTo.setMonth(paramMdl.getWml070svSendDateMonthTo());
            sendDateTo.setDay(paramMdl.getWml070svSendDateDayTo());
            sendDateTo.setHour(23);
            sendDateTo.setMinute(59);
            sendDateTo.setSecond(59);
            sendDateTo.setMilliSecond(999);
            prmModel.setWriteDateTo(sendDateTo);
        }

        return prmModel;
    }
}
