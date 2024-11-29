package jp.groupsession.v2.man.man050;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupDao;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnLoginHistoryDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.LoginHistorySearchModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 最終ログイン時間画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man050Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man050Biz.class);

    /** リクエスト */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Man050Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] ユーザのデフォルトグループSIDをセットします
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param sessionSid ユーザSID
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setDefaultGroupSid(
                Connection con,
                int sessionSid,
                Man050ParamModel paramMdl) throws SQLException {

        CmnBelongmDao dao = new CmnBelongmDao(con);
        //ユーザSIDを渡してデフォルトグループ情報を得ます
        int gsid = dao.selectUserBelongGroupDef(sessionSid);
        //デフォルトグループIDをセットします
        paramMdl.setMan050grpSid(gsid);
    }

    /**
     * <br>[機  能] グループを全件セットします
     * <br>[解  説] 管理者の場合全グループを返し、一般ユーザの場合所属グループのみを返します
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param adminFlg 管理者かどうかのフラグ true:管理者, false:一般ユーザ
     * @param usid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void setGroupModel(
            Connection con,
            Man050ParamModel paramMdl,
            boolean adminFlg,
            int usid) throws SQLException {

        //グループ一覧を取得する
        List <GroupModel> groupList = null;
        GroupBiz groupBiz = new GroupBiz();
        if (adminFlg) {
            //管理者用
            groupList = groupBiz.getGroupCombList(con);
        } else {
            //一般ユーザ用
            //管理者用
            groupList = groupBiz.getGroupCombList(con, usid);
        }

        //グループ一覧をセット
        List<LabelValueBean> grpLabelList = new ArrayList<LabelValueBean>();
        for (GroupModel grpMdl : groupList) {
            grpLabelList.add(
                    new LabelValueBean(grpMdl.getGroupName(),
                                        String.valueOf(grpMdl.getGroupSid())));
        }

        paramMdl.setMan050GroupList(grpLabelList);
    }
    /**
     * <br>[機  能] 表示モードに合わせて表示データをセットします。
     * <br>[解  説] タブの状態に合わせて、データをセット
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setMan050List(
            Connection con,
            Man050ParamModel paramMdl) throws SQLException {

        //インスタンスの生成
        SltUserPerGroupDao supgDao = new SltUserPerGroupDao(con);

        //グループSID、ソートキー、オーダーキーを取得
        int grpSid = paramMdl.getMan050grpSid();
        int sortKey = paramMdl.getMan050SortKey();
        int orderKey = paramMdl.getMan050OrderKey();

        // 表示モードに合わせてデータ取得
        // 最終ログイン一覧
        if (paramMdl.getMan050cmdMode().equals(GSConstMain.MODE_LIST)) {
            //グループSIDの検索結果をListに格納
            List < SltUserPerGroupModel > userNmList =
                supgDao.selectGroupListSort(grpSid, sortKey, orderKey);

            //JSP表示用モデルに値を格納
            List<Man050ViewModel> userViewList = new ArrayList<Man050ViewModel>();
            for (SltUserPerGroupModel userMdl : userNmList) {
                Man050ViewModel model = new Man050ViewModel();

                model.setUsrlgid(userMdl.getUsrlgid());
                model.setUsrsid(userMdl.getUsrsid());
                model.setFullName(userMdl.getFullName());
                model.setSyainno(userMdl.getSyainno());
                model.setYakusyoku(userMdl.getYakusyoku());
                model.setStrLgintime(convertUDateGS2(userMdl.getLgintime()));
                model.setLgintimeFg(__getLginBgFrag(userMdl.getLgintime()));

                userViewList.add(model);
            }

            //検索結果をセット
            paramMdl.setMan050UserList(userViewList);

        // 詳細検索
        } else {
            int uSid = paramMdl.getMan050usrSid();
            int terminal = paramMdl.getMan050Terminal();
            int car = paramMdl.getMan050Car();

            String strFrDate = null;
            String strToDate = null;
            UDate frDate = new UDate();
            UDate toDate = new UDate();

            if (!StringUtil.isNullZeroString(paramMdl.getMan050FrYear())
                    && !StringUtil.isNullZeroString(paramMdl.getMan050FrMonth())
                    && !StringUtil.isNullZeroString(paramMdl.getMan050FrMonth())) {
                strFrDate = paramMdl.getMan050FrYear() + "/";
                strFrDate += paramMdl.getMan050FrMonth() + "/";
                strFrDate += paramMdl.getMan050FrDay();
                frDate = UDate.getInstanceStr(strFrDate);
            }
            frDate.setZeroHhMmSs();

            if (!StringUtil.isNullZeroString(paramMdl.getMan050ToYear())
                    && !StringUtil.isNullZeroString(paramMdl.getMan050ToMonth())
                    && !StringUtil.isNullZeroString(paramMdl.getMan050ToDay())) {
                strToDate = paramMdl.getMan050ToYear() + "/";
                strToDate += paramMdl.getMan050ToMonth() + "/";
                strToDate += paramMdl.getMan050ToDay();
                toDate = UDate.getInstanceStr(strToDate);
            }
            toDate.setMaxHhMmSs();

            CmnLoginHistoryDao loginHisDao = new CmnLoginHistoryDao(con);

            //検索結果件数
            int searchCnt = loginHisDao.selectSearchLoginListCnt(uSid,
                    grpSid, terminal, car, frDate, toDate);

            //最大表示件数
            int maxDsp = Man050Form.PAGE_MAX_DATA_CMT;

            //ページ調整
            int maxPage = searchCnt / maxDsp;
            if ((searchCnt % maxDsp) > 0) {
                maxPage++;
            }

            int page = paramMdl.getMan050PageTop();
            if (page < 1) {
                page = 1;
            } else if (page > maxPage) {
                page = maxPage;
            }

            paramMdl.setMan050PageTop(page);
            paramMdl.setMan050PageBottom(page);

            //ページコンボ設定
            paramMdl.setMan050PageList(
                    PageUtil.createPageOptions(searchCnt, maxDsp));

            //検索結果をListに格納
            LoginHistorySearchModel searchModel = new LoginHistorySearchModel();
            searchModel.setUsid(uSid);
            searchModel.setGsid(grpSid);
            searchModel.setTerminal(terminal);
            searchModel.setCar(car);
            searchModel.setFrDate(frDate);
            searchModel.setToDate(toDate);
            searchModel.setSortKey(sortKey);
            searchModel.setOrderKey(orderKey);
            searchModel.setPage(page);
            searchModel.setMaxCnt(maxDsp);
            List < Man050ViewModel > userNmList
                = loginHisDao.selectSearchLoginList(searchModel, reqMdl__);

            //JSP表示用モデルに値を格納
            List<Man050ViewModel> userViewList = new ArrayList<Man050ViewModel>();

            for (Man050ViewModel userMdl : userNmList) {
                Man050ViewModel model = new Man050ViewModel();

                model.setUsrlgid(userMdl.getUsrlgid());
                model.setUsrsid(userMdl.getUsrsid());
                model.setFullName(userMdl.getFullName());
                model.setSyainno(userMdl.getSyainno());
                model.setYakusyoku(userMdl.getYakusyoku());
                model.setStrLgintime(convertUDateGS2(userMdl.getLgintime()));
                model.setLgintimeFg(__getLginBgFrag(userMdl.getLgintime()));
                model.setTerminalName(userMdl.getTerminalName());
                model.setCarName(userMdl.getCarName());
                model.setClhUid(userMdl.getClhUid());

                userViewList.add(model);
            }

            //検索結果をセット
            paramMdl.setMan050UserList(userViewList);
        }
    }

    /**
     * <br>[機  能] 検索タブ時の初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @throws NoSuchMethodException 日時設定時例外
     * @throws InvocationTargetException 日時設定時例外
     * @throws IllegalAccessException 日時設定時例外
     */
    public void setSearchInitData(Man050ParamModel paramMdl,
                                Connection con)
    throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        UDate uDate = new UDate();

        // 開始日付の初期値設定（現在日）
        paramMdl.setMan050FrYear(
                NullDefault.getString(paramMdl.getMan050FrYear(),
                        String.valueOf(uDate.getYear())));
        paramMdl.setMan050FrMonth(
                NullDefault.getString(paramMdl.getMan050FrMonth(),
                        String.valueOf(uDate.getMonth())));
        paramMdl.setMan050FrDay(
                NullDefault.getString(paramMdl.getMan050FrDay(),
                        String.valueOf(uDate.getIntDay())));
        // 終了日付の初期値設定（現在日）
        paramMdl.setMan050ToYear(
                NullDefault.getString(paramMdl.getMan050ToYear(),
                        String.valueOf(uDate.getYear())));
        paramMdl.setMan050ToMonth(
                NullDefault.getString(paramMdl.getMan050ToMonth(),
                        String.valueOf(uDate.getMonth())));
        paramMdl.setMan050ToDay(
                NullDefault.getString(paramMdl.getMan050ToDay(),
                        String.valueOf(uDate.getIntDay())));
        
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (paramMdl.getMan050FrDate() == null) {
            dateBiz.setDateParam(paramMdl, "man050FrDate", "man050FrYear",
                "man050FrMonth", "man050FrDay", null);
        }
        
        if (paramMdl.getMan050ToDate() == null) {
            dateBiz.setDateParam(paramMdl, "man050ToDate", "man050ToYear",
                "man050ToMonth", "man050ToDay", null);
        }

        UserBiz userBiz = new UserBiz();
        int gsid = paramMdl.getMan050grpSid();

        //グループ所属ユーザラベル
        GsMessage gsMsg = new GsMessage(reqMdl__);
        paramMdl.setMan050BelongUserList(
                        userBiz.getNormalUserLabelList(con, gsid, null, true, gsMsg));
    }

    /**
     * <br>[機  能] 背景色フラグを返します
     * <br>[解  説] 1:今日、2:昨日、3:2日前、4:3日前、5:4日前、6:5日前、7:10日前、8:20日前以降
     * <br>[備  考]
     * @param lgDate 背景色フラグ
     * @return 背景色フラグ
     */
    private int __getLginBgFrag(UDate lgDate) {
        if (lgDate == null) {
            return -1;
        }

        UDate today = new UDate();
        today.setHour(0);
        today.setMinute(0);
        today.setSecond(0);
        today.setMilliSecond(0);

        UDate loginDate = new UDate();
        loginDate.setYear(lgDate.getYear());
        loginDate.setMonth(lgDate.getMonth());
        loginDate.setDay(lgDate.getIntDay());
        loginDate.setHour(0);
        loginDate.setMinute(0);
        loginDate.setSecond(0);
        loginDate.setMilliSecond(0);

        long date = UDateUtil.diffDay(today, loginDate);

        int ltlgFrag = 0;

        //背景色フラグを生成
        if (date == 0) {
            ltlgFrag = 1;   //今日
        } else if (date == 1) {
            ltlgFrag = 2;   //昨日
        } else if (date == 2) {
            ltlgFrag = 3;   //２日前
        } else if (date == 3) {
            ltlgFrag = 4;   //３日前
        } else if (date == 4) {
            ltlgFrag = 5;   //４日前
        } else if (date > 4 && date < 10) {
            ltlgFrag = 6;   //５日前
        } else if (date > 9 && date < 20) {
            ltlgFrag = 7;   //１０日前
        } else if (date >= 20) {
            ltlgFrag = 8;   //２０日前以降
        } else {
            ltlgFrag = 8;   //有り得ないがそれ以外
        }
        return ltlgFrag;
    }

    /**
     * <br>[機  能] UDate形式の時間を表示形式に変換します。
     * <br>[解  説] 例）20060101235959 → 2006/01/01 23:59:59
     * <br>[備  考]
     * @param udate UDate
     * @return String
     */
    public String convertUDateGS2(UDate udate) {
        String strSdate = null;
        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (udate == null) {
            return gsMsg.getMessage("main.src.man050.4");
        }
        strSdate = UDateUtil.getSlashYYMD(udate)
                 + "  "
                 + UDateUtil.getSeparateHMS(udate);
        log__.debug("生成結果：" + strSdate);
        return strSdate;
    }
}
