package jp.groupsession.v2.man.man290kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoMsgDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoTagDao;
import jp.groupsession.v2.cmn.dao.base.TcdAdmConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoMsgModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoTagModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.cmn.model.base.TcdAdmConfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MaintenanceUtil;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン インフォメーション登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man290knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man290knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Man290knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリ
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイル処理例外
     */
    public void setInitData(Man290knParamModel paramMdl,
                            String tempDir) throws SQLException, IOToolsException {

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        //セッションユーザSID
        int sessionUsrSid = usModel.getUsrsid();

        //公開設定日付
        HashMap<String, UDate> addDateMap = getInsertDateList(paramMdl, sessionUsrSid);
        paramMdl.setMan290knDateList(__getStrDateListFromMap(addDateMap));

        //公開設定文字列生成
        paramMdl.setMan290knKoukaiString(__getKoukaiSettingString(paramMdl));
        //休日表示設定文字列生成
        paramMdl.setMan290knSyukuString(__getSyukuSettingString(paramMdl));

        //公開開始・終了文字列生成
        UDate frDate = new UDate();
        frDate.setDate(NullDefault.getInt(paramMdl.getMan290FrYear(), -1),
                NullDefault.getInt(paramMdl.getMan290FrMonth(), -1),
                NullDefault.getInt(paramMdl.getMan290FrDay(), -1));
        frDate.setHour(NullDefault.getInt(paramMdl.getMan290FrHour(), -1));
        frDate.setMinute(NullDefault.getInt(paramMdl.getMan290FrMin(), -1));
        UDate toDate = new UDate();
        toDate.setDate(NullDefault.getInt(paramMdl.getMan290ToYear(), -1),
                NullDefault.getInt(paramMdl.getMan290ToMonth(), -1),
                NullDefault.getInt(paramMdl.getMan290ToDay(), -1));
        toDate.setHour(NullDefault.getInt(paramMdl.getMan290ToHour(), -1));
        toDate.setMinute(NullDefault.getInt(paramMdl.getMan290ToMin(), -1));

        paramMdl.setMan290knFrDate(UDateUtil.getYymdJ(frDate, reqMdl__)
                + " " + UDateUtil.getSeparateHM(frDate, reqMdl__));
        paramMdl.setMan290knToDate(UDateUtil.getYymdJ(toDate, reqMdl__)
                + " " + UDateUtil.getSeparateHM(toDate, reqMdl__));
        //メッセージ・内容文字生成
        paramMdl.setMan290knMsg(paramMdl.getMan290Msg());
        paramMdl.setMan290knValue(StringUtilHtml.transToHTmlPlusAmparsantAndLink(paramMdl.getMan290Value()));
        //公開対象生成
        String[] leftFull = paramMdl.getMan290memberSid();
        paramMdl.setMan290knKoukaiList(__getKoukaiLabel(leftFull));

        //添付ファイル一覧を設定
        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setMan290FileLabelList(cmnBiz.getTempFileLabelList(tempDir));

        //シングルコーテーションのエスケープを行う
        paramMdl.setCmd(StringUtil.toSingleCortationEscape(paramMdl.getCmd()));
    }

    /**
     * <br>[機  能] 画面パラメータからインフォメーションを登録する日付リストを取得します。
     * <br>[解  説] 営業日の判定にはタイムカード基本設定を使用します
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param sessionUsrSid セッションユーザSID
     * @return HashMap ケジュールを登録する日付MAP
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, UDate> getInsertDateList(
            Man290knParamModel paramMdl, int sessionUsrSid)
    throws SQLException {

        Man290knDateSearchModel model = new Man290knDateSearchModel();
        //抽出区分
        model.setDateKbn(paramMdl.getMan290ExtKbn());

        //抽出区分：毎日
        if (model.getDateKbn().equals(String.valueOf(GSConstMain.INFO_KBN_DAY))) {
            //公開日が休日の場合の区分設定
            model.setTranKbn(String.valueOf(paramMdl.getMan290HolKbn()));

        //抽出区分：毎週
        } else if (model.getDateKbn().equals(String.valueOf(GSConstMain.INFO_KBN_WEEK))) {
            __setWeekOfWeekly(paramMdl.getMan290Dweek(), model);
            //公開日が休日の場合の区分設定
            model.setTranKbn(String.valueOf(paramMdl.getMan290HolKbn()));

        //抽出区分：毎月
        } else if (model.getDateKbn().equals(String.valueOf(GSConstMain.INFO_KBN_MONTH))) {
            if (!paramMdl.getMan290Week().equals("0")) {
                //週設定
                __setWeekOfWeekly(paramMdl.getMan290Dweek(), model);
                model.setWeekOfMonthly(paramMdl.getMan290Week());
            } else {
                //日設定
                model.setDayOfMonthly(paramMdl.getMan290Day());
            }
            //公開日が休日の場合の区分設定
            model.setTranKbn(String.valueOf(paramMdl.getMan290HolKbn()));
        }
        //抽出期間
        UDate frDate = new UDate();
        frDate.setYear(Integer.parseInt(paramMdl.getMan290FrYear()));
        frDate.setMonth(Integer.parseInt(paramMdl.getMan290FrMonth()));
        frDate.setDay(Integer.parseInt(paramMdl.getMan290FrDay()));
        frDate.setZeroHhMmSs();
        model.setFromDate(frDate);
        UDate toDate = new UDate();
        toDate.setYear(Integer.parseInt(paramMdl.getMan290ToYear()));
        toDate.setMonth(Integer.parseInt(paramMdl.getMan290ToMonth()));
        toDate.setDay(Integer.parseInt(paramMdl.getMan290ToDay()));
        toDate.setZeroHhMmSs();
        model.setToDate(toDate);
        //日付リストを取得
        HashMap<String, UDate> dateMap = getDateList(model, sessionUsrSid);

        return dateMap;
    }

    /**
     * <br>[機  能] 日付指定条件に該当する日付リストを取得します。
     * <br>[解  説] 営業日の判定にはタイムカード基本設定を使用します
     * <br>[備  考]
     * @param model 抽出条件
     * @param sessionUsrSid セッションユーザ
     * @return HashMap ケジュールを登録する日付MAP
     * @throws SQLException SQL実行時例外
     */
    public HashMap<String, UDate> getDateList(
            Man290knDateSearchModel model, int sessionUsrSid)
    throws SQLException {

        UDate frDate = model.getFromDate();
        UDate toDate = model.getToDate();
        //営業日情報を取得する
        CmnHolidayDao holDao = new CmnHolidayDao(con__);
        HashMap<String, CmnHolidayModel> holMap = holDao.getHoliDayListFotTcd(frDate, toDate);
        TcdAdmConfModel acMdl = getTcdAdmConfModel(sessionUsrSid);

        HashMap<String, UDate> dateMap = new HashMap<String, UDate>();
        UDate setDate = null;
        log__.debug("frDate.compareDateYMD(toDate)=>" + frDate.compareDateYMD(toDate));
        while (frDate.compareDateYMD(toDate) != UDate.SMALL) {
            //抽出条件に該当するか判定
            if (__isMatchCondition(frDate, model)) {
                //振替判定
                setDate = __getConvertDate(frDate, model, holMap, acMdl);
                if (setDate != null) {
                    dateMap.put(setDate.getDateString(), setDate);
                    log__.debug("登録日付==>" + setDate.getDateString());
                }
            }
            frDate.addDay(1);
        }

        return dateMap;
    }

    /**
     * <br>[機  能] タイムカード管理者設定を取得する。
     * <br>[解  説] レコードが存在しない場合、デフォルト値で作成します。
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @return TcdAdmConfModel
     * @throws SQLException SQL実行時例外
     */
    public TcdAdmConfModel getTcdAdmConfModel(int usrSid) throws SQLException {
        log__.debug("タイムカード管理者設定取得");
        TcdAdmConfDao dao = new TcdAdmConfDao(con__);
        TcdAdmConfModel model = dao.select();
        if (model == null) {
            boolean commit = false;
            try {
                model = new TcdAdmConfModel(usrSid);
                dao.insert(model);
                commit = true;
            } catch (SQLException e) {
                log__.error("タイムカード管理者設定の登録に失敗しました。");
                throw e;
            } finally {
                if (commit) {
                    con__.commit();
                }

            }
        }
        return model;
    }

    /**
     * <br>[機  能] インフォメーションメッセージを登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     * @return int インフォメーションSID
     */
    public int insertMsg(Man290knParamModel paramMdl, int userSid,
            MlCountMtController cntCon) throws SQLException {

        CmnInfoMsgDao msgDao = new CmnInfoMsgDao(con__);
        CmnInfoMsgModel msgMdl = new CmnInfoMsgModel();
        UDate now = new UDate();

        int imsSid = (int) cntCon.getSaibanNumber(
                SaibanModel.SBNSID_MAIN, SaibanModel.SBNSID_SUB_INFO, userSid);
        msgMdl.setImsSid(imsSid);
        msgMdl.setImsKbn(NullDefault.getInt(paramMdl.getMan290ExtKbn(), 1));
        msgMdl.setImsWeek(NullDefault.getInt(paramMdl.getMan290Week(), 0));
        msgMdl.setImsDay(NullDefault.getInt(paramMdl.getMan290Day(), 0));

        String[] weeks = paramMdl.getMan290Dweek();
        if (weeks != null) {
            for (String week : weeks) {
                int intWeek = Integer.parseInt(week);
                switch (intWeek) {
                case UDate.SUNDAY:
                    msgMdl.setImsDweek1(1);
                    break;
                case UDate.MONDAY:
                    msgMdl.setImsDweek2(1);
                    break;
                case UDate.TUESDAY:
                    msgMdl.setImsDweek3(1);
                    break;
                case UDate.WEDNESDAY:
                    msgMdl.setImsDweek4(1);
                    break;
                case UDate.THURSDAY:
                    msgMdl.setImsDweek5(1);
                    break;
                case UDate.FRIDAY:
                    msgMdl.setImsDweek6(1);
                    break;
                case UDate.SATURDAY:
                    msgMdl.setImsDweek7(1);
                    break;
                default:
                    break;
                }
            }
        }
        UDate frDate = new UDate();
        frDate.setDate(NullDefault.getInt(paramMdl.getMan290FrYear(), -1),
                NullDefault.getInt(paramMdl.getMan290FrMonth(), -1),
                NullDefault.getInt(paramMdl.getMan290FrDay(), -1));
        frDate.setZeroHhMmSs();
        frDate.setHour(NullDefault.getInt(paramMdl.getMan290FrHour(), -1));
        frDate.setMinute(NullDefault.getInt(paramMdl.getMan290FrMin(), -1));

        UDate toDate = new UDate();
        toDate.setDate(NullDefault.getInt(paramMdl.getMan290ToYear(), -1),
                NullDefault.getInt(paramMdl.getMan290ToMonth(), -1),
                NullDefault.getInt(paramMdl.getMan290ToDay(), -1));
        toDate.setMaxHhMmSs();
        toDate.setHour(NullDefault.getInt(paramMdl.getMan290ToHour(), -1));
        toDate.setMinute(NullDefault.getInt(paramMdl.getMan290ToMin(), -1));
        msgMdl.setImsFrDate(frDate);
        msgMdl.setImsToDate(toDate);

        msgMdl.setImsMsg(paramMdl.getMan290Msg());
        msgMdl.setImsValue(paramMdl.getMan290Value());
        msgMdl.setImsJtkb(0);
        msgMdl.setImsAuid(userSid);
        msgMdl.setImsAdate(now);
        msgMdl.setImsEuid(userSid);
        msgMdl.setImsEdate(now);;
        msgMdl.setImsHolkbn(paramMdl.getMan290HolKbn());
        //登録
        msgDao.insert(msgMdl);
        return imsSid;
    }

    /**
     * <br>[機  能] インフォメーション公開対象を登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param imsSid インフォメーションSID
     * @param userSid ユーザSID
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行時例外
     */
    public void insertTag(Man290knParamModel paramMdl, int imsSid, int userSid,
            MlCountMtController cntCon) throws SQLException {

        CmnInfoTagDao tagDao = new CmnInfoTagDao(con__);
        CmnInfoTagModel tagMdl = null;
        String[] left = paramMdl.getMan290memberSid();
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<Integer> usrSids = new ArrayList<Integer>();

        //ユーザSIDとグループSIDを分離
        if (left != null) {
            for (int i = 0; i < left.length; i++) {
                String str = NullDefault.getString(left[i], "-1");
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(new Integer(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(new Integer(str));
                }
            }
        }
        //事前削除
        tagDao.delete(imsSid);
        //公開グループを登録
        for (Integer gSid : grpSids) {
            tagMdl = new CmnInfoTagModel();
            tagMdl.setImsSid(imsSid);
            tagMdl.setUsrSid(-1);
            tagMdl.setGrpSid(gSid);
            tagDao.insert(tagMdl);
        }
        //公開ユーザを登録
        for (Integer uSid : usrSids) {
            tagMdl = new CmnInfoTagModel();
            tagMdl.setImsSid(imsSid);
            tagMdl.setUsrSid(uSid);
            tagMdl.setGrpSid(-1);
            tagDao.insert(tagMdl);
        }
    }

    /**
     * <br>[機  能] インフォメーションメッセージを登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     * @return int 更新件数
     */
    public int updateMsg(Man290knParamModel paramMdl, int userSid)
    throws SQLException {

        CmnInfoMsgDao msgDao = new CmnInfoMsgDao(con__);
        CmnInfoMsgModel msgMdl = new CmnInfoMsgModel();
        UDate now = new UDate();

        int imsSid = NullDefault.getInt(paramMdl.getMan320SelectedSid(), -1);
        msgMdl.setImsSid(imsSid);
        msgMdl.setImsKbn(NullDefault.getInt(paramMdl.getMan290ExtKbn(), 1));
        msgMdl.setImsWeek(NullDefault.getInt(paramMdl.getMan290Week(), 0));
        msgMdl.setImsDay(NullDefault.getInt(paramMdl.getMan290Day(), 0));

        String[] weeks = paramMdl.getMan290Dweek();
        if (weeks != null) {
            for (String week : weeks) {
                int intWeek = Integer.parseInt(week);
                switch (intWeek) {
                case UDate.SUNDAY:
                    msgMdl.setImsDweek1(1);
                    break;
                case UDate.MONDAY:
                    msgMdl.setImsDweek2(1);
                    break;
                case UDate.TUESDAY:
                    msgMdl.setImsDweek3(1);
                    break;
                case UDate.WEDNESDAY:
                    msgMdl.setImsDweek4(1);
                    break;
                case UDate.THURSDAY:
                    msgMdl.setImsDweek5(1);
                    break;
                case UDate.FRIDAY:
                    msgMdl.setImsDweek6(1);
                    break;
                case UDate.SATURDAY:
                    msgMdl.setImsDweek7(1);
                    break;
                default:
                    break;
                }
            }
        }
        UDate frDate = new UDate();
        frDate.setDate(NullDefault.getInt(paramMdl.getMan290FrYear(), -1),
                NullDefault.getInt(paramMdl.getMan290FrMonth(), -1),
                NullDefault.getInt(paramMdl.getMan290FrDay(), -1));
        frDate.setZeroHhMmSs();
        frDate.setHour(NullDefault.getInt(paramMdl.getMan290FrHour(), -1));
        frDate.setMinute(NullDefault.getInt(paramMdl.getMan290FrMin(), -1));

        UDate toDate = new UDate();
        toDate.setDate(NullDefault.getInt(paramMdl.getMan290ToYear(), -1),
                NullDefault.getInt(paramMdl.getMan290ToMonth(), -1),
                NullDefault.getInt(paramMdl.getMan290ToDay(), -1));
        toDate.setMaxHhMmSs();
        toDate.setHour(NullDefault.getInt(paramMdl.getMan290ToHour(), -1));
        toDate.setMinute(NullDefault.getInt(paramMdl.getMan290ToMin(), -1));
        msgMdl.setImsFrDate(frDate);
        msgMdl.setImsToDate(toDate);

        msgMdl.setImsMsg(paramMdl.getMan290Msg());
        msgMdl.setImsValue(paramMdl.getMan290Value());
        msgMdl.setImsJtkb(0);
        msgMdl.setImsEuid(userSid);
        msgMdl.setImsEdate(now);;
        msgMdl.setImsHolkbn(paramMdl.getMan290HolKbn());
        //更新
        int count = msgDao.update(msgMdl);
        return count;
    }

    /**
     * <br>[機  能] インフォメーションメッセージを物理削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     * @return int 削除件数
     */
    public int deleteMsg(Man290knParamModel paramMdl) throws SQLException {

        CmnInfoMsgDao msgDao = new CmnInfoMsgDao(con__);
        int imsSid = NullDefault.getInt(paramMdl.getMan320SelectedSid(), -1);
        int count = msgDao.delete(imsSid);
        return count;
    }

    /**
     * <br>[機  能] インフォメーション公開対象を物理削除します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     * @return int 削除件数
     */
    public int deleteTag(Man290knParamModel paramMdl) throws SQLException {

        CmnInfoTagDao msgDao = new CmnInfoTagDao(con__);
        int imsSid = NullDefault.getInt(paramMdl.getMan320SelectedSid(), -1);
        int count = msgDao.delete(imsSid);
        return count;
    }

    /**
     * <br>[機  能] インフォメーションの拡張設定文字を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return String 拡張設定文字
     */
    private String __getKoukaiSettingString(Man290knParamModel paramMdl) {
        StringBuilder buf = new StringBuilder();
        String kbn = NullDefault.getString(
                paramMdl.getMan290ExtKbn(), String.valueOf(GSConstMain.INFO_KBN_DAY));

        GsMessage gsMsg = new GsMessage(reqMdl__);
        if (kbn.equals(String.valueOf(GSConstMain.INFO_KBN_DAY))) {
            buf.append(gsMsg.getMessage(GSConstMain.INFO_KBN_DAYSTRING));

        } else if (kbn.equals(String.valueOf(GSConstMain.INFO_KBN_WEEK))) {
            buf.append(gsMsg.getMessage(GSConstMain.INFO_KBN_WEEKSTRING));
            buf.append(" ");

            String[] weeks = paramMdl.getMan290Dweek();
            if (weeks != null) {
                for (String week : weeks) {
                    int intWeek = Integer.parseInt(week);
                    switch (intWeek) {
                    case UDate.SUNDAY:
                        buf.append(gsMsg.getMessage("cmn.sunday2") + " ");
                        break;
                    case UDate.MONDAY:
                        buf.append(gsMsg.getMessage("cmn.monday2") + " ");
                        break;
                    case UDate.TUESDAY:
                        buf.append(gsMsg.getMessage("cmn.tuesday2") + " ");
                        break;
                    case UDate.WEDNESDAY:
                        buf.append(gsMsg.getMessage("cmn.wednesday2") + " ");
                        break;
                    case UDate.THURSDAY:
                        buf.append(gsMsg.getMessage("cmn.thursday2") + " ");
                        break;
                    case UDate.FRIDAY:
                        buf.append(gsMsg.getMessage("main.src.man290kn.7") + " ");
                        break;
                    case UDate.SATURDAY:
                        buf.append(gsMsg.getMessage("cmn.saturday2") + " ");
                        break;
                    default:
                        break;
                    }
                }
            }

        } else if (kbn.equals(String.valueOf(GSConstMain.INFO_KBN_MONTH))) {
            buf.append(gsMsg.getMessage(GSConstMain.INFO_KBN_MONTHSTRING));
            buf.append(" ");
            int day = NullDefault.getInt(paramMdl.getMan290Day(), 0);
            if (day > 0) {
                if (day == GSConstCommon.LAST_DAY_OF_MONTH) {
                    buf.append(gsMsg.getMessage("tcd.tcd050kn.01"));
                } else {
                    buf.append(day + gsMsg.getMessage("cmn.day"));
                }
            } else {
                buf.append(MaintenanceUtil.getWeek(paramMdl.getMan290Week(), reqMdl__));
                buf.append(" ");

                String[] weeks = paramMdl.getMan290Dweek();
                if (weeks != null) {
                    for (String week : weeks) {
                        int intWeek = Integer.parseInt(week);
                        switch (intWeek) {
                        case UDate.SUNDAY:
                            buf.append(gsMsg.getMessage("cmn.sunday2") + " ");
                            break;
                        case UDate.MONDAY:
                            buf.append(gsMsg.getMessage("cmn.monday2") + " ");
                            break;
                        case UDate.TUESDAY:
                            buf.append(gsMsg.getMessage("cmn.tuesday2") + " ");
                            break;
                        case UDate.WEDNESDAY:
                            buf.append(gsMsg.getMessage("cmn.wednesday2") + " ");
                            break;
                        case UDate.THURSDAY:
                            buf.append(gsMsg.getMessage("cmn.thursday2") + " ");
                            break;
                        case UDate.FRIDAY:
                            buf.append(gsMsg.getMessage("main.src.man290kn.7") + " ");
                            break;
                        case UDate.SATURDAY:
                            buf.append(gsMsg.getMessage("cmn.saturday2") + " ");
                            break;
                        default:
                            break;
                        }
                    }
                }
            }

        }

        return buf.toString();
    }

    /**
     * <br>[機  能] Mapに格納されている日付情報をArrayList(in String(yyyy年mm月dd日形式))に置き換えます。
     * <br>[解  説]
     * <br>[備  考]
     * @param map 日付情報
     * @return ArrayList 格納しなおした日付情報
     */
    private ArrayList<String> __getStrDateListFromMap(HashMap<String, UDate> map) {

        ArrayList<UDate> col = new ArrayList<UDate>(map.values());
        ArrayList<Man290knDateSortModel> sort = new ArrayList<Man290knDateSortModel>();
        Man290knDateSortModel sortMdl = null;
        for (UDate date : col) {
            sortMdl = new Man290knDateSortModel();
            sortMdl.setUdate(date);
            sort.add(sortMdl);
        }
        Collections.sort(sort);

        ArrayList<String> ret = new ArrayList<String>();
        for (Man290knDateSortModel model : sort) {
            ret.add(UDateUtil.getYymdJ(model.getUdate(), reqMdl__));
        }

        return ret;
    }

    /**
     * <br>[機  能] 曜日を抽出用モデルに設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param weeks 曜日指定
     * @param model 抽出用モデル
     */
    private void __setWeekOfWeekly(String[] weeks, Man290knDateSearchModel model) {
        if (weeks == null) {
            return;
        }

        for (String week : weeks) {
            int intWeek = Integer.parseInt(week);
            switch (intWeek) {
            case 1:
                model.setWeekOfWeekly1("1");
                break;
            case 2:
                model.setWeekOfWeekly2("1");
                break;
            case 3:
                model.setWeekOfWeekly3("1");
                break;
            case 4:
                model.setWeekOfWeekly4("1");
                break;
            case 5:
                model.setWeekOfWeekly5("1");
                break;
            case 6:
                model.setWeekOfWeekly6("1");
                break;
            case 7:
                model.setWeekOfWeekly7("1");
                break;
            default:
                break;
            }
        }
    }

    /**
     * <br>[機  能] 抽出条件に該当する日付か判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param date 判定対象の日付
     * @param model 抽出条件
     * @return boolean true:該当 false:該当しない
     */
    private boolean __isMatchCondition(
            UDate date,
            Man290knDateSearchModel model) {

        if (model.getDateKbn().equals(String.valueOf(GSConstMain.INFO_KBN_DAY))) {
            //毎日
            return true;
        } else if (model.getDateKbn().equals(String.valueOf(GSConstMain.INFO_KBN_WEEK))) {
            //毎週
            return __isMatchWeek(date.getWeek(), model);

        } else if (model.getDateKbn().equals(String.valueOf(GSConstMain.INFO_KBN_MONTH))) {
            //毎月
            if (model.getWeekOfMonthly() != null) {
                //週・曜日指定
                int weekNo = Integer.parseInt(model.getWeekOfMonthly());
                if (__isMatchWeek(date.getWeek(), model)) {
                    UDate wkDate = date.cloneUDate();
                    int wkWeekOfMonth
                        = MaintenanceUtil.getAccurateWeekOfMonth(
                            wkDate, wkDate.getWeek());
                    log__.debug("wkDate==>" + wkDate.getDateString());
                    log__.debug("weekNo==>" + weekNo);
                    log__.debug("wkWeekOfMonth==>" + wkWeekOfMonth);
                    if (weekNo == wkWeekOfMonth) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                //日指定
                String monthly = NullDefault.getString(model.getDayOfMonthly(), "");
                return CommonBiz.getExDay(date, monthly).equals(String.valueOf(date.getIntDay()));
            }
        } else {
            return false;
        }

    }

    /**
     * <br>[機  能] 指定した曜日が指定されているか判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param week 週
     * @param model 抽出条件
     * @return true:指定されている　false:指定されていない
     */
    private boolean __isMatchWeek(int week, Man290knDateSearchModel model) {
        switch (week) {
        case UDate.SUNDAY:
            if (model.getWeekOfWeekly1() == null) {
                return false;
            }
            break;
        case UDate.MONDAY:
            if (model.getWeekOfWeekly2() == null) {
                return false;
            }
            break;
        case UDate.TUESDAY:
            if (model.getWeekOfWeekly3() == null) {
                return false;
            }
            break;
        case UDate.WEDNESDAY:
            if (model.getWeekOfWeekly4() == null) {
                return false;
            }
            break;
        case UDate.THURSDAY:
            if (model.getWeekOfWeekly5() == null) {
                return false;
            }
            break;
        case UDate.FRIDAY:
            if (model.getWeekOfWeekly6() == null) {
                return false;
            }
            break;
        case UDate.SATURDAY:
            if (model.getWeekOfWeekly7() == null) {
                return false;
            }
            break;
        default:
            return false;
        }
        return true;
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

    /**
     * <br>[機  能] 営業日判定を行い非営業日の場合、振替設定によって日付をコンバートします。
     * <br>[解  説]
     * <br>[備  考]
     * @param date 日付
     * @param model 抽出条件
     * @param holMap 休日情報
     * @param acMdl タイムカード管理設定
     * @return UDate コンバート結果
     */
    private UDate __getConvertDate(
            UDate date,
            Man290knDateSearchModel model,
            HashMap<String, CmnHolidayModel> holMap,
            TcdAdmConfModel acMdl) {

        UDate ret = date.cloneUDate();
        int tran = Integer.parseInt(model.getTranKbn());
        log__.debug("振替区分==>" + tran);
        int addDay = 0;

        //休日振替区分：翌営業日
        if (tran == GSConstMain.INFO_HOL_KBN_AFTER) {
            addDay = 1;

        //休日振替区分：前営業日
        } else if (tran == GSConstMain.INFO_HOL_KBN_BEFORE) {
            addDay = -1;

        //休日振替区分：表示しない
        } else if (tran == GSConstMain.INFO_HOL_KBN_NO) {
            addDay = 0;

        //休日振替区分：そのまま表示
        } else {
            return ret;
        }

        //休日として扱う曜日か判定
        boolean fin = true;
        while (fin) {
            if (__isMatchWeek(ret.getWeek(), acMdl)
             || holMap.containsKey(ret.getDateString())) {
                log__.debug("休日として認識==>" + ret.getDateString());
                //休日は登録しない場合
                if (tran == GSConstMain.INFO_HOL_KBN_NO) {
                    //非表示
                    return null;
                }
                ret.addDay(addDay);
            } else {
                fin = false;
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] インフォメーションの拡張設定文字(休日表示)を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return String 拡張設定文字
     */
    private String __getSyukuSettingString(Man290knParamModel paramMdl) {
        StringBuilder buf = new StringBuilder();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        buf.append(gsMsg.getMessage("cmn.holiday.release.date") + " ");

        if (paramMdl.getMan290HolKbn() == GSConstMain.INFO_HOL_KBN_NO) {
            buf.append(gsMsg.getMessage("cmn.dont.show"));
        } else if (paramMdl.getMan290HolKbn() == GSConstMain.INFO_HOL_KBN_BEFORE) {
            buf.append(gsMsg.getMessage("main.man290.6"));
        } else if (paramMdl.getMan290HolKbn() == GSConstMain.INFO_HOL_KBN_AFTER) {
            buf.append(gsMsg.getMessage("main.man290.7"));
        } else if (paramMdl.getMan290HolKbn() == GSConstMain.INFO_HOL_KBN_DEFO) {
            buf.append(gsMsg.getMessage("cmn.displayed.as"));
        }

        return buf.toString();

    }
    /**
     * <br>[機  能] 公開対象一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param left 取得するユーザSID・グループSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getKoukaiLabel(String[] left)
    throws SQLException {

        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();

        //
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (left != null) {
            for (int i = 0; i < left.length; i++) {
                String str = NullDefault.getString(left[i], "-1");
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(new Integer(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }
        //グループ情報取得
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con__);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
        UsrLabelValueBean labelBean = null;
        for (GroupModel gmodel : glist) {
            labelBean = new UsrLabelValueBean();
            labelBean.setLabel(gmodel.getGroupName());
            labelBean.setValue("G" + String.valueOf(gmodel.getGroupSid()));
            ret.add(labelBean);
        }

        //ユーザ情報取得
        UserBiz userBiz = new UserBiz();
        List<CmnUsrmInfModel> ulist
                = userBiz.getUserList(con__,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        for (CmnUsrmInfModel umodel : ulist) {
            labelBean = new UsrLabelValueBean(umodel);
            ret.add(labelBean);
        }

        return ret;
    }

}
