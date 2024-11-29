package jp.groupsession.v2.wml.restapi.accounts.mails.query;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.dao.base.WmlMaildataDao;
import jp.groupsession.v2.wml.model.mail.WmlMailResultModel;
import jp.groupsession.v2.wml.model.mail.WmlMailSearchModel;
import jp.groupsession.v2.wml.restapi.accounts.mails.WmlAccountsMailsResultModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.biz.WmlRestMailDataBiz;

/**
 * <br>[機  能] WEBメール メール一覧取得API ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccountsMailsQueryPostBiz {

    /** 実行結果*/
    private final List<WmlAccountsMailsResultModel> result__ = new ArrayList<>();
    /** コンテキスト */
    private final RestApiContext ctx__;
    /** コネクション */
    private final Connection con__;

    /**
     * 
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public WmlAccountsMailsQueryPostBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] 送信先リストの取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void execute(WmlAccountsMailsQueryPostParamModel paramMdl) throws SQLException {

        //アカウントSIDを取得
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        int wacSid = wraBiz.getWmlAccountSid(con__, paramMdl.getAccountId());

        //メール情報一覧を取得
        List<WmlMailResultModel> mailList = null;

        WmlDirectoryDao dirDao = new WmlDirectoryDao(con__);

        // 検索条件
        WmlMailSearchModel searchMdl = new WmlMailSearchModel();
        searchMdl.setAccountSid(wacSid);      // アカウントSID

        //ディレクトリ区分 or ラベル
        int wdrType = -1;
        switch (NullDefault.getString(paramMdl.getDirectoryType(), "")) {
            case "all":
                break;
            case "inbox":
                wdrType = GSConstWebmail.DIR_TYPE_RECEIVE;
                break;
            case "sent":
                wdrType = GSConstWebmail.DIR_TYPE_SENDED;
                break;
            case "future":
                wdrType = GSConstWebmail.DIR_TYPE_NOSEND;
                break;
            case "draft":
                wdrType = GSConstWebmail.DIR_TYPE_DRAFT;
                break;
            case "trash":
                wdrType = GSConstWebmail.DIR_TYPE_DUST;
                break;
            case "keep":
                wdrType = GSConstWebmail.DIR_TYPE_STORAGE;
                break;
            case "label":
                //ラベル指定
                searchMdl.setLabelSid(paramMdl.getLabelSid());
                break;
            default:
                break;
        }

        if (wdrType != -1) {
            searchMdl.setDirectorySid(dirDao.getDirSid(wacSid, wdrType));
            searchMdl.setDirectoryType(wdrType);
        }

        //キーワード
        searchMdl.setKeyword(paramMdl.getKeywordText());   

        //日時検索
        searchMdl.setResvDateFrom(paramMdl.getFromDateTime());
        searchMdl.setResvDateTo(paramMdl.getToDateTime());

        //送信者検索
        searchMdl.setFrom(paramMdl.getFromAddressText());

        //送信先検索
        searchMdl.setDestinationTo(paramMdl.getSendToFlg() == 1);
        searchMdl.setDestinationCc(paramMdl.getSendCcFlg() == 1);
        searchMdl.setDestinationBcc(paramMdl.getSendBccFlg() == 1);
        if (searchMdl.getDestinationTo()
        || searchMdl.getDestinationCc()
        || searchMdl.getDestinationBcc()) {
            searchMdl.setDestination(paramMdl.getSendAddressText());
        }

        //添付ファイル検索
        searchMdl.setTempFile(paramMdl.getUseTmpFileFlg() == 1);

        //未読・既読状態
        searchMdl.setReadKbn(paramMdl.getOpenStatusType());

        // 並び順 (日付降順で固定)
        searchMdl.setSortKey(GSConstWebmail.SORTKEY_SDATE);
        searchMdl.setOrder(GSConstWebmail.ORDER_DESC);

        //読み込み開始位置 > 検索結果件数の場合、空の検索結果を返す
        int start = paramMdl.getOffset() + 1;
        WmlMaildataDao maildataDao = new WmlMaildataDao(con__);
        long maxCount = maildataDao.getMailCount(searchMdl);
        if (start > maxCount) {
            return;
        }

        searchMdl.setStart(start); // 読み込み開始位置
        searchMdl.setMaxCount(paramMdl.getLimit());   // 最大表示件数

        //メッセージ一覧取得
        mailList = maildataDao.getMailList(searchMdl,
                                        WmlBiz.getBodyLimitLength(this.ctx__.getAppRootPath()));

        WmlRestMailDataBiz wrmBiz = new WmlRestMailDataBiz();
        result__.addAll(wrmBiz.getMailsResult(con__, mailList));
    }

    /**
     *
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public List<WmlAccountsMailsResultModel> getResult() {
        return result__;
    }
}
