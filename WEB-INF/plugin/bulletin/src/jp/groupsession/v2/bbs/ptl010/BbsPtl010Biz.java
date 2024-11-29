package jp.groupsession.v2.bbs.ptl010;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsUserModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.ptl020.BbsPtl020Biz;
import jp.groupsession.v2.cmn.biz.PortletBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ポートレット スレッド一覧のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsPtl010Biz implements PortletBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsPtl010Biz.class);


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public BbsPtl010Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param buMdl ユーザ情報
     * @param forumAuth 閲覧権限
     * @throws Exception 実行例外
     */
    public void setInitData(BbsPtl010ParamModel paramMdl, Connection con,
            BaseUserModel buMdl, boolean forumAuth)
    throws Exception {
        log__.debug("START");
        List<BulletinDspModel> threadList = new ArrayList<BulletinDspModel>();

        if (!forumAuth) {
            paramMdl.setThreadList(threadList);
            return;
        }

        BulletinDao bbsDao = new BulletinDao(con);
        BbsBiz bbsBiz = new BbsBiz();
        UDate now = new UDate();

        int bfiSid = paramMdl.getBbsPtlBfiSid();
        if (paramMdl.getBbsPtlBfiSid() < 1) {
            return;
        }

        //最大件数
        int threCnt = bbsDao.getThreadCount(bfiSid, now);
        if (threCnt < 1) {
            return;
        }
        int maxThreCnt = GSConstBulletin.BBS_PORTLET_THRE_LIST_CNT;

        //ページ調整
        int maxPage = threCnt / maxThreCnt;
        if ((threCnt % maxThreCnt) > 0) {
            maxPage++;
        }
        int page = paramMdl.getBbsPtl010page1();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }

        paramMdl.setBbsPtl010page1(page);
        paramMdl.setBbsPtl010page2(page);
        //ページコンボ設定
        paramMdl.setBbsPageLabel(PageUtil.createPageOptions(threCnt, maxThreCnt));

        int start = (page - 1) * maxThreCnt + 1;
        int end = start + maxThreCnt - 1;

        int sortKey = GSConstBulletin.SORT_KEY_SAISHIN;
        int orderKey = GSConstBulletin.ORDER_KEY_ASC;

        //メイン画面スレッド表示件数を取得
        BbsUserModel bbsUsrMdl = bbsBiz.getBbsUserData(con, buMdl.getUsrsid());

        //スレッド一覧を取得する。
        threadList = bbsDao.getPtlThreadList(bfiSid, buMdl.getUsrsid(), now,
                bbsUsrMdl.getBurNewCnt(), start, end, sortKey, orderKey);
        paramMdl.setThreadList(threadList);

        if (!threadList.isEmpty()) {
            BbsForInfDao forumDao = new BbsForInfDao(con);
            BbsForInfModel searchModel = new BbsForInfModel();
            searchModel.setBfiSid(paramMdl.getBbsPtlBfiSid());
            BbsForInfModel result = forumDao.select(searchModel);
            if (result == null) {
                result = new BbsForInfModel();
            }
            paramMdl.setBbsPtlBfiName(result.getBfiName());
        }
        log__.debug("End");
    }

    /**
     * <br>プラグインポートレットタイトルを取得する。
     * @param con コネクション
     * @param paramMap パラメータマップ
     * @return title ポートレットプラグインタイトル
     * @throws Exception 実行時例外
     */
    public String getPortletTitle(Connection con, HashMap<String, String> paramMap)
    throws Exception {

        String title = "";

        if (paramMap == null) {
            return title;
        }

        BbsBiz bbsBiz = new BbsBiz();

        //マップからパラメータを取得
        String paramFrmSid = paramMap.get(BbsPtl020Biz.BBS_PORTLET_PARAM1);

        //フォーラム名
        BulletinDspModel forumData = bbsBiz.getForumData(con, NullDefault.getInt(paramFrmSid, 0));
        if (forumData != null) {
            title = forumData.getBfiName();
        }

        return title;
    }

}
