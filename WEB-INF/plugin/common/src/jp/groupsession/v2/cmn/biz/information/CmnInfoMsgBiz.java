package jp.groupsession.v2.cmn.biz.information;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoMsgDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoTagDao;
import jp.groupsession.v2.cmn.dao.base.TcdAdmConfDao;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoMsgModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoTagModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MaintenanceUtil;

/**
 * <br>[機  能] CmnInfoMsgModelを取得するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CmnInfoMsgBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CmnInfoMsgBiz.class);

     /**
     * <br>[機  能] ユーザSID、日時を指定して公開するインフォメーション情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid 対象ユーザSID
     * @param date 抽出条件の日時
     * @param con コネクション
     * @return インフォメーション情報一覧
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<CmnInfoMsgModel> getActiveInformationMsg(
            int userSid, UDate date, Connection con) throws SQLException {
        ArrayList<CmnInfoMsgModel> ret = new ArrayList<CmnInfoMsgModel>();

        //閲覧可能なインフォメーションSIDの一覧をmapに取得する
        CmnInfoTagDao tagDao = new CmnInfoTagDao(con);
        HashMap<Integer, CmnInfoTagModel> map = new HashMap<Integer, CmnInfoTagModel>();
        map = tagDao.getTargetInfoForUser(map, userSid);
        //所属グループ一覧
        CmnBelongmDao beDao = new CmnBelongmDao(con);
        ArrayList<Integer> gpSidList = beDao.selectUserBelongGroupSid(userSid);
        map = tagDao.getTargetInfoForGroup(map, gpSidList);

        //公開対象のインフォメーション一覧を取得
        CmnInfoMsgDao msgDao = new CmnInfoMsgDao(con);
        ArrayList<CmnInfoMsgModel> infoList = msgDao.getActiveMsg(
                new ArrayList<CmnInfoTagModel>(map.values()));
        //抽出条件の日時から条件に合わないものを除外
        for (CmnInfoMsgModel bean : infoList) {
            log__.debug("判定対象==>" + bean.getImsMsg());
            if (__isActiveInformationMsg(bean, date, con)) {
                log__.debug("公開OK==>" + bean.getImsMsg());
                if (__isActiveInformationMsgInHoliday(bean, date, con, userSid)) {
                    ret.add(bean);
                }
            //休日振替判定
            } else {
                if (__isActiveInfoMsgInHolFurikae(bean, date, con, userSid)) {
                    log__.debug("公開OK(振替のため)==>" + bean.getImsMsg());
                    ret.add(bean);
                }
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] CmnInfoMsgModelが抽出条件の日時に公開するものか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CmnInfoMsgModel
     * @param date 抽出条件の日時
     * @param con  コネクション
     * @return true:公開 false:非公開
     * @throws SQLException SQL実行例外
     */
    private boolean __isActiveInformationMsg(CmnInfoMsgModel bean,
                                               UDate date,
                                               Connection con) throws SQLException {

        UDate frDate = bean.getImsFrDate();
        if (date.compareDateYMDHM(frDate) == UDate.LARGE) {
            //公開前
            log__.debug("公開前");
            return false;
        }
        UDate toDate = bean.getImsToDate();
        if (date.compareDateYMDHM(toDate) == UDate.SMALL) {
            //公開後
            log__.debug("公開期限後");
            return false;
        }
        //
        if (bean.getImsKbn() == GSConstMain.INFO_KBN_WEEK) {
            //毎週？曜日に公開するか
            return __isOpenWeek(bean, date, con);

        } else if (bean.getImsKbn() == GSConstMain.INFO_KBN_MONTH) {

            //毎月
            if (bean.getImsWeek() > 0) {
                //第何週を指定
                int wkWeekOfMonth
                    = MaintenanceUtil.getAccurateWeekOfMonth(date, bean.getImsWeek());
                if (wkWeekOfMonth != bean.getImsWeek()) {
                    return false;
                }
                //曜日を指定
                return __isOpenWeek(bean, date, con);

            } else if (bean.getImsDay() > 0) {
                //日付指定
                if (date.getIntDay() != CommonBiz.getExDay(date, bean.getImsDay())) {
                    return false;
                }

                return true;
            }
        }
        log__.debug("公開 true");
        return true;
    }

    /**
     * <br>[機  能] 指定日付が公開曜日かを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CmnInfoMsgModel
     * @param date 指定日付
     * @param con コネクション
     * @return true:公開　false:非公開
     * @throws SQLException SQL実行例外
     */
    private boolean __isOpenWeek(CmnInfoMsgModel bean,
                                   UDate date,
                                   Connection con) throws SQLException {
        log__.debug("date.getWeek()==>" + date.getWeek());
        switch (date.getWeek()) {
        case UDate.SUNDAY:
            //日曜日に公開しない
            if (bean.getImsDweek1() == 0) {
                return false;
            }
            break;
        case UDate.MONDAY:
            //月曜日に公開しない
            if (bean.getImsDweek2() == 0) {
                return false;
            }
            break;
        case UDate.TUESDAY:
            //火曜日に公開しない
            if (bean.getImsDweek3() == 0) {
                return false;
            }
            break;
        case UDate.WEDNESDAY:
            //水曜日に公開しない
            if (bean.getImsDweek4() == 0) {
                return false;
            }
            break;
        case UDate.THURSDAY:
            //木曜日に公開しない
            if (bean.getImsDweek5() == 0) {
                return false;
            }
            break;
        case UDate.FRIDAY:
            //金曜日に公開しない
            if (bean.getImsDweek6() == 0) {
                return false;
            }
            break;
        case UDate.SATURDAY:
            //土曜日に公開しない
            if (bean.getImsDweek7() == 0) {
                return false;
            }
            break;
        default:
            break;
        }
        return true;
    }

    /**
     * <br>[機  能] CmnInfoMsgModelが休日に公開するものか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CmnInfoMsgModel
     * @param date 抽出条件の日時
     * @param con  コネクション
     * @param userSid ユーザSID
     * @return true:公開 false:非公開
     * @throws SQLException SQL実行例外
     */
    private boolean __isActiveInformationMsgInHoliday(CmnInfoMsgModel bean,
                                                        UDate date,
                                                        Connection con,
                                                        int userSid) throws SQLException {
        //休日表示判定
        //タイムカードで休日扱いにする日付取得
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        CmnHolidayModel model = new CmnHolidayModel();
        model.setHolDate(date);
        CmnHolidayModel holMdl = holDao.select(model);
        TcdAdmConfModel acMdl = getTcdAdmConfModel(userSid, con);

        boolean kbn = false;

        if (holMdl != null || __isMatchWeek(date.getWeek(), acMdl)) {
            //休日
            if (bean.getImsHolkbn() == GSConstMain.INFO_HOL_KBN_DEFO) {
                kbn = true;
            } else if (bean.getImsHolkbn() == GSConstMain.INFO_HOL_KBN_NO) {
                kbn = false;
            } else {
                kbn = false;
            }
        } else {
            kbn = true;
        }
        return kbn;
    }


    /**
     * <br>[機  能] CmnInfoMsgModelが振替休日に公開するものか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param bean CmnInfoMsgModel
     * @param date 抽出条件の日時
     * @param con  コネクション
     * @param userSid  ユーザSID
     * @return true:公開 false:非公開
     * @throws SQLException SQL実行例外
     */
    private boolean __isActiveInfoMsgInHolFurikae(CmnInfoMsgModel bean,
                                                    UDate date,
                                                    Connection con,
                                                    int userSid) throws SQLException {

        CmnHolidayDao holDao = new CmnHolidayDao(con);
        CmnHolidayModel model = new CmnHolidayModel();
        CmnHolidayModel holMdl = new CmnHolidayModel();
        UDate nexDate = date.cloneUDate();
        UDate befDate = date.cloneUDate();
        ArrayList<UDate> dateList = new ArrayList<UDate>();
        TcdAdmConfModel acMdl = getTcdAdmConfModel(userSid, con);
        nexDate.addDay(1);
        befDate.addDay(-1);

        //前営業日振替
        if (bean.getImsHolkbn() == GSConstMain.INFO_HOL_KBN_BEFORE) {
            boolean holEnd = false;
            //今日以降が休日の場合、設定されているインフォメーションがあるか判定
            while (holEnd == false) {
                model.setHolDate(nexDate);
                holMdl = holDao.select(model);
                if (holMdl != null || __isMatchWeek(nexDate.getWeek(), acMdl)) {
                    if (__isActiveInformationMsg(bean, nexDate, con)) {
                        dateList.add(nexDate);
                    }
                } else {
                    holEnd = true;
                }
                nexDate.addDay(1);
            }
        //翌営業日振替
        } else if (bean.getImsHolkbn() == GSConstMain.INFO_HOL_KBN_AFTER) {
            boolean holEnd = false;
            //今日以前が休日の場合、設定されているインフォメーションがあるか判定
            while (holEnd == false) {
                model.setHolDate(befDate);
                holMdl = holDao.select(model);
                if (holMdl != null || __isMatchWeek(befDate.getWeek(), acMdl)) {
                    if (__isActiveInformationMsg(bean, befDate, con)) {
                        dateList.add(befDate);
                    }
                } else {
                    holEnd = true;
                }
                befDate.addDay(-1);
            }
        }

        if (dateList.size() > 0) {
            //今日が休日か判定
            if (holMdl != null || __isMatchWeek(date.getWeek(), acMdl)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * <br>[機  能] タイムカード管理者設定を取得する。
     * <br>[解  説] レコードが存在しない場合、デフォルト値で作成します。
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param con コネクション
     * @return TcdAdmConfModel
     * @throws SQLException SQL実行時例外
     */
    public TcdAdmConfModel getTcdAdmConfModel(int usrSid, Connection con) throws SQLException {
        log__.debug("タイムカード管理者設定取得");
        TcdAdmConfDao dao = new TcdAdmConfDao(con);
        TcdAdmConfModel model = dao.select();
        if (model == null) {
            boolean commit = false;
            try {
                model = new TcdAdmConfModel(usrSid);
                dao.insert(model);
                commit = true;
            } catch (SQLException e) {
                log__.error("タイムカード管理者設定の取得に失敗しました。");
                throw e;
            } finally {
                if (commit) {
                    con.commit();
                }

            }
        }
        return model;
    }

    /**
     * <br>[機  能] 指定した曜日が指定されているか判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param week 週
     * @param acMdl 管理設定
     * @return true:休日曜日　false:休日曜日以外
     */
    private boolean __isMatchWeek(int week, TcdAdmConfModel acMdl) {
        switch (week) {
        case UDate.SUNDAY:
            if (acMdl.getTacHolSun() == 0) {
                return false;
            }
            break;
        case UDate.MONDAY:
            if (acMdl.getTacHolMon() == 0) {
                return false;
            }
            break;
        case UDate.TUESDAY:
            if (acMdl.getTacHolTue() == 0) {
                return false;
            }
            break;
        case UDate.WEDNESDAY:
            if (acMdl.getTacHolWed() == 0) {
                return false;
            }
            break;
        case UDate.THURSDAY:
            if (acMdl.getTacHolThu() == 0) {
                return false;
            }
            break;
        case UDate.FRIDAY:
            if (acMdl.getTacHolFri() == 0) {
                return false;
            }
            break;
        case UDate.SATURDAY:
            if (acMdl.getTacHolSat() == 0) {
                return false;
            }
            break;
        default:
            return false;
        }
        return true;
    }
}
