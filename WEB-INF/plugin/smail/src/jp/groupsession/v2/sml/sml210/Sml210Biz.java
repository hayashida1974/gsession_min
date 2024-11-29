package jp.groupsession.v2.sml.sml210;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.PageUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlHinaDao;
import jp.groupsession.v2.sml.model.SmlHinaModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール ひな形一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml210Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml210Biz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     */
    public Sml210Biz() {
    }

    /**
     * <br>[機  能] 初期表示データ取得
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Sml210ParamModel paramMdl,
                            RequestModel reqMdl,
                            Connection con)
        throws SQLException {

        log__.debug("初期表示データ取得");

        SmlHinaDao hinaDao = new SmlHinaDao(con);

        //該当データ最大件数取得
        int hinaKbn = paramMdl.getSml050HinaKbn();
        long maxCount = hinaDao.getHinaCount(paramMdl.getSmlAccountSid(), hinaKbn);

        //ページ設定用
        int nowPage = paramMdl.getSml050PageNum();
        int limit = GSConstSmail.MAX_RECORD_COUNT;
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }

        //データ取得
        ArrayList<SmlHinaModel> resultList =
            hinaDao.selectHinaList(
                paramMdl.getSmlAccountSid(),
                start,
                limit,
                paramMdl.getSml050Sort_key(),
                paramMdl.getSml050Order_key(),
                hinaKbn);

        paramMdl.setSml050PageNum(nowPage);
        paramMdl.setSml050Slt_page1(nowPage);
        paramMdl.setSml050Slt_page2(nowPage);
        paramMdl.setSml050PageLabel(
            PageUtil.createPageOptions(maxCount, GSConstSmail.MAX_RECORD_COUNT));
        paramMdl.setSml050HinaList(resultList);

    }
}