package jp.groupsession.v2.wml.wml260;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlBiz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] WEBメール 送信予定メール管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml260Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml260Biz.class);

    /**
     * <br>[機  能] 予約送信メール一覧を取得する
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
            Wml260ParamModel paramMdl,
            RequestModel reqMdl)
        throws SQLException {

        //画面表示情報を設定
        setDsp(con, paramMdl, reqMdl);

        //予約送信メール一覧を取得する(検索を行う)
        return __getResvSendList(paramMdl, con);
    }

    /**
     * <br>[機  能] 予約送信メール一覧を取得する(検索を行う)
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    private ActionErrors __getResvSendList(Wml260ParamModel paramMdl, Connection con)
    throws SQLException {

        ActionErrors errors = new ActionErrors();
        //1ページ最大50件
        int limit = GSConstWebmail.LIMIT_DSP_MAILLOG;

        Wml260SearchParameterModel prmModel = __getSvSearchParameter(paramMdl);

        prmModel.setLimit(limit);
        long maxCount = 0;

        //件数カウント
        Wml260Dao dao = new Wml260Dao(con);
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
     * <br>[機  能] 画面表示情報を設定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setDsp(Connection con, Wml260ParamModel paramMdl, RequestModel reqMdl)
        throws SQLException {

        //年月日コンボの値をセット
        WmlBiz biz = new WmlBiz();
        UDate now = new UDate();
        paramMdl.setYearLabel(biz.getYearList(reqMdl, now.getYear()));
        paramMdl.setMonthLabel(biz.getMonthList(reqMdl));
        paramMdl.setDayLabel(biz.getDayList(reqMdl));

        //予約送信メールの登録 を設定
        WmlBiz wmlBiz = new WmlBiz();
        paramMdl.setWmlEntryMailLogFlg(wmlBiz.isEntryMailLog(con));
    }

    /**
     * <br>[機  能] パラメータモデルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return Wml260SearchParameterModel
     */
    private Wml260SearchParameterModel __getSvSearchParameter(Wml260ParamModel paramMdl) {

        Wml260SearchParameterModel prmModel = new Wml260SearchParameterModel();

        prmModel.setWml260AccountName(paramMdl.getWml260svAccountName());
        prmModel.setTextWordTitle(NullDefault.getString(paramMdl.getWml260svTitle(), ""));
        prmModel.setWml260Address(NullDefault.getString(paramMdl.getWml260svAddress(), ""));
        prmModel.setWml260AddressFrom(paramMdl.getWml260svAddressFrom());
        prmModel.setWml260AddressTo(paramMdl.getWml260svAddressTo());
        prmModel.setWml260SendDateYear(paramMdl.getWml260svSendDateYear());
        prmModel.setWml260SendDateMonth(paramMdl.getWml260svSendDateMonth());
        prmModel.setWml260SendDateDay(paramMdl.getWml260svSendDateDay());
        prmModel.setWml260SendDateCondition(paramMdl.getWml260svSendDateCondition());
        prmModel.setWml260SendDateYearTo(paramMdl.getWml260svSendDateYearTo());
        prmModel.setWml260SendDateMonthTo(paramMdl.getWml260svSendDateMonthTo());
        prmModel.setWml260SendDateDayTo(paramMdl.getWml260svSendDateDayTo());
        prmModel.setWml260SendDateCondition(paramMdl.getWml260svSendDateCondition());
        prmModel.setSortKey(paramMdl.getWml260sortKey());
        prmModel.setOrder(paramMdl.getWml260order());

        UDate sendDate = new UDate();
        //一致する or 以降 or 範囲指定
        if (paramMdl.getWml260SendDateCondition() != GSConstWebmail.DATE_KBN_BEFORE) {
            sendDate.setTime(0);
            sendDate.setYear(paramMdl.getWml260svSendDateYear());
            sendDate.setMonth(paramMdl.getWml260svSendDateMonth());
            sendDate.setDay(paramMdl.getWml260svSendDateDay());
            sendDate.setHour(0);
            sendDate.setMinute(0);
            sendDate.setSecond(0);
            sendDate.setMilliSecond(0);
            prmModel.setWriteDate(sendDate);

        //以前
        } else {
            sendDate.setTime(0);
            sendDate.setYear(paramMdl.getWml260svSendDateYear());
            sendDate.setMonth(paramMdl.getWml260svSendDateMonth());
            sendDate.setDay(paramMdl.getWml260svSendDateDay());
            sendDate.setHour(23);
            sendDate.setMinute(59);
            sendDate.setSecond(59);
            sendDate.setMilliSecond(999);
            prmModel.setWriteDate(sendDate);
        }

        //範囲指定
        if (paramMdl.getWml260SendDateCondition() == GSConstWebmail.DATE_KBN_DATEAREA) {
            UDate sendDateTo = new UDate();
            sendDateTo.setTime(0);
            sendDateTo.setYear(paramMdl.getWml260svSendDateYearTo());
            sendDateTo.setMonth(paramMdl.getWml260svSendDateMonthTo());
            sendDateTo.setDay(paramMdl.getWml260svSendDateDayTo());
            sendDateTo.setHour(23);
            sendDateTo.setMinute(59);
            sendDateTo.setSecond(59);
            sendDateTo.setMilliSecond(999);
            prmModel.setWriteDateTo(sendDateTo);
        }

        return prmModel;
    }
}
