package jp.groupsession.v2.bbs.bbs040;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.model.BulletinSearchModel;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 掲示板 詳細検索画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs040Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs040Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admin 管理者か否か true:管理者, false:一般ユーザ
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     */
    public void setInitData(
            Bbs040ParamModel paramMdl, Connection con, int userSid,
            boolean admin, RequestModel reqMdl)
                    throws Exception {
        log__.debug("START");

        //フォーラムコンボを設定
        BulletinDao bbsDao = new BulletinDao(con);
        paramMdl.setBbs040forumList(
                __getForumLabelList(con, userSid));

        //年月日コンボを設定
        int sysYear = (new UDate()).getYear();
        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> bfiMdlList = bbsBiz.getForumListOfUser(con, userSid);
        List<Integer> bfiSidList = new ArrayList<Integer>();
        for (BbsForInfModel bfiMdl : bfiMdlList) {
            bfiSidList.add(bfiMdl.getBfiSid());
        }
        UDate oldWriteDate = bbsDao.getOldestWriteDate(bfiSidList, false);
        int writeOldYear = sysYear;
        if (oldWriteDate != null) {
            writeOldYear = oldWriteDate.getYear();
        }
        UDate now = new UDate();
        paramMdl.setBbs040oldYear(now.getYear() - writeOldYear);
        
        //開始日付はコンストラクタによって定義されないため、こちらで行う
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getBbs040fromDate() == null) {
            dateBiz.setDateParam(paramMdl, "bbs040fromDate", "bbs040fromYear",
                    "bbs040fromMonth", "bbs040fromDay", null);
            if (paramMdl.getBbs040fromDate() == null) {
                //各パラメータが空の場合、1月1日を設定
                UDate firstDate = new UDate();
                firstDate.setMonth(1);
                firstDate.setDay(1);
                paramMdl.setBbs040fromDate(firstDate.getDateString("/"));
            }
        }
        
        if (paramMdl.getBbs040toDate() == null) {
            dateBiz.setDateParam(paramMdl, "bbs040toDate", "bbs040toYear",
                    "bbs040toMonth", "bbs040toDay", null);
        }

        log__.debug("End");
    }

    /**
     * <br>[機  能] フォーラムコンボを作成します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return フォーラムコンボ
     */
    private List<LabelValueBean> __getForumLabelList(
            Connection con, int userSid)
                    throws SQLException {
        List<LabelValueBean> forumLabelList = new ArrayList<LabelValueBean>();

        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> usersForumWrite =
                bbsBiz.getForumListOfUser(con, userSid, GSConstBulletin.ACCESS_KBN_WRITE);
        List<BbsForInfModel> usersForumAll = bbsBiz.getForumListOfUser(con, userSid);

        BulletinDao bbsDao = new BulletinDao(con);
        List<BulletinDspModel> bdMdlList = bbsDao.getForumListWithHierarchy(
                userSid, new UDate(), 0, 0, 0,  false, usersForumWrite, usersForumAll);

        for (BulletinDspModel mdl : bdMdlList) {
            int forumLevel = mdl.getForumLevel();
            String bfiName = "";
            int i = 1;
            while (i < forumLevel) {
                bfiName += "　";
                ++i;
            }
            bfiName += mdl.getBfiName();
            String bfiSid = String.valueOf(mdl.getBfiSid());
            LabelValueBean bbsForLabel =
                    new LabelValueBean(bfiName, bfiSid);
            forumLabelList.add(bbsForLabel);
        }

        return forumLabelList;
    }

    /**
     * <br>[機  能] 検索結果件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admin 管理者か否か true:管理者, false:一般ユーザ
     * @param now 現在日時
     * @return 検索結果件数
     * @throws Exception 実行例外
     */
    public int getSearchCount(Bbs040ParamModel paramMdl, Connection con, int userSid,
            boolean admin, UDate now)
                    throws Exception {

        //検索条件を設定
        BulletinSearchModel searchMdl = createSearchModel(paramMdl, userSid, admin, now);

        //ユーザがメンバーに含まれているフォーラムリスト
        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> bfiMdlList = bbsBiz.getForumListOfUser(con, searchMdl.getUserSid());
        List<Integer> forumList = new ArrayList<Integer>();
        for (BbsForInfModel bfiMdl : bfiMdlList) {
            forumList.add(bfiMdl.getBfiSid());
        }

        BulletinDao bbsDao = new BulletinDao(con);
        return bbsDao.getSearchDtlCnt(searchMdl, forumList);
    }

    /**
     * <br>[機  能] 検索条件を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param admin 管理者か否か true:管理者, false:一般ユーザ
     * @param now 現在日時
     * @return 検索条件
     */
    public BulletinSearchModel createSearchModel(Bbs040ParamModel paramMdl, int userSid,
                                                boolean admin, UDate now) {

        //検索条件を設定
        BulletinSearchModel searchMdl = new BulletinSearchModel();
        searchMdl.setBfiSid(paramMdl.getBbs040forumSid());
        searchMdl.setUserSid(userSid);
        searchMdl.setAdmin(admin);
        searchMdl.setNow(now);

        searchMdl.setKeyword(paramMdl.getS_key());
        searchMdl.setKeywordKbn(paramMdl.getBbs040keyKbn());
        searchMdl.setSearchThreTitleFlg(paramMdl.getBbs040taisyouThread() == 1);
        searchMdl.setSearchWriteValueFlg(paramMdl.getBbs040taisyouNaiyou() == 1);
        searchMdl.setContributorName(paramMdl.getBbs040userName());
        searchMdl.setReadKbn(paramMdl.getBbs040readKbn());
        searchMdl.setPublicStatusOngoing(paramMdl.getBbs040publicStatusOngoing());
        searchMdl.setPublicStatusScheduled(paramMdl.getBbs040publicStatusScheduled());
        searchMdl.setPublicStatusOver(paramMdl.getBbs040publicStatusOver());
        searchMdl.setWriteDateFlg(paramMdl.getBbs040dateNoKbn() != 1);
        if (searchMdl.isWriteDateFlg()) {
            UDate fromDate = new UDate();
            fromDate.setTime(0);
            fromDate.setYear(paramMdl.getBbs040fromYear());
            fromDate.setMonth(paramMdl.getBbs040fromMonth());
            fromDate.setDay(paramMdl.getBbs040fromDay());
            fromDate.setHour(0);
            fromDate.setMinute(0);
            fromDate.setSecond(0);
            fromDate.setMilliSecond(0);
            searchMdl.setWriteDateFrom(fromDate);

            UDate toDate = new UDate();
            toDate.setTime(0);
            toDate.setYear(paramMdl.getBbs040toYear());
            toDate.setMonth(paramMdl.getBbs040toMonth());
            toDate.setDay(paramMdl.getBbs040toDay());
            toDate.setHour(23);
            toDate.setMinute(59);
            toDate.setSecond(59);
            toDate.setMilliSecond(999);
            searchMdl.setWriteDateTo(toDate);
        }

        return searchMdl;
    }
}
