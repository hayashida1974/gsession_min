package jp.groupsession.v2.man.man029;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayTemplateDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] メイン 管理者設定 休日テンプレートインポート画面 取込みファイルのチェックを行う
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Man029CsvCheck extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man029CsvCheck.class);
    /** エラー行存在フラグ */
    private boolean errorFlg__ = false;
    /** コネクション */
    private Connection con__ = null;
    /** アクションエラー */
    private ActionErrors errors__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;
    /** 有効データカウント */
    private int count__ = 0;
    /** フォーマットエラーフラグ */
    private boolean formatError__ = false;

    /** 休日テンプレート 月 */
    private int holiday_month__ = 0;
    /** 休日テンプレート 日 */
    private int holiday_day__ = 0;
    /** 休日テンプレート 休日名称 */
    private String holiday_name__ = "";
    /** 休日テンプレート 拡張 月 */
    private int holiday_exMonth__ = 0;
    /** 休日テンプレート 拡張 週 */
    private int holiday_exWeekMonth__ = 0;
    /** 休日テンプレート 拡張 曜日 */
    private int holiday_exDayWeek__ = 0;
    /** 休日テンプレート 振替 */
    private int holiday_furikae__ = 0;

    /** 休日のリスト */
    private ArrayList<String> holiday_list__ = null;

    /** 休日のマッピング */
    private HashMap<String, String> holiday_map__ = new HashMap<String, String>();
    /** 休日テンプレートのマッピング */
    private HashMap<String, HolidayTemplateExModel> holiday_tmp_map__ =
            new HashMap<String, HolidayTemplateExModel>();

    /** 上書きフラグ */
    private int updateFlg__ = 0;

    /**
     * <p>con__ を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con__ をセットします。
     * @param con con__
     */
    public void setCon(Connection con) {
        con__ = con;
    }
    /**
     * <p>count__ を取得します。
     * @return count
     */
    public int getCount() {
        return count__;
    }
    /**
     * <p>count__ をセットします。
     * @param count count__
     */
    public void setCount(int count) {
        count__ = count;
    }
    /**
     * <p>errorFlg__ を取得します。
     * @return errorFlg
     */
    public boolean isErrorFlg() {
        return errorFlg__;
    }
    /**
     * <p>errorFlg__ をセットします。
     * @param errorFlg errorFlg__
     */
    public void setErrorFlg(boolean errorFlg) {
        errorFlg__ = errorFlg;
    }
    /**
     * <p>errors__ を取得します。
     * @return errors
     */
    public ActionErrors getErrors() {
        return errors__;
    }
    /**
     * <p>errors__ をセットします。
     * @param errors errors__
     */
    public void setErrors(ActionErrors errors) {
        errors__ = errors;
    }
    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }
    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }
    /**
     * <p>formatError__ を取得します。
     * @return formatError
     */
    public boolean isFormatError() {
        return formatError__;
    }
    /**
     * <p>formatError__ をセットします。
     * @param formatError formatError__
     */
    public void setFormatError(boolean formatError) {
        formatError__ = formatError;
    }

    /**
     * @return holiday_month
     */
    public int getHoliday_month() {
        return holiday_month__;
    }
    /**
     * @param holidayMonth セットする holiday_month
     */
    public void setHoliday_month(int holidayMonth) {
        holiday_month__ = holidayMonth;
    }
    /**
     * @return holiday_day
     */
    public int getHoliday_day() {
        return holiday_day__;
    }
    /**
     * @param holidayDay セットする holiday_day
     */
    public void setHoliday_day(int holidayDay) {
        holiday_day__ = holidayDay;
    }
    /**
     * @return holiday_name
     */
    public String getHoliday_name() {
        return holiday_name__;
    }
    /**
     * @param holidayName セットする holiday_name
     */
    public void setHoliday_name(String holidayName) {
        holiday_name__ = holidayName;
    }
    /**
     * @return holiday_exMonth
     */
    public int getHoliday_exMonth() {
        return holiday_exMonth__;
    }
    /**
     * @param holidayExMonth セットする holiday_exMonth
     */
    public void setHoliday_exMonth(int holidayExMonth) {
        holiday_exMonth__ = holidayExMonth;
    }
    /**
     * @return holiday_exWeekMonth
     */
    public int getHoliday_exWeekMonth() {
        return holiday_exWeekMonth__;
    }
    /**
     * @param holidayExWeekMonth セットする holiday_exWeekMonth
     */
    public void setHoliday_exWeekMonth(int holidayExWeekMonth) {
        holiday_exWeekMonth__ = holidayExWeekMonth;
    }
    /**
     * @return holiday_exDayWeek
     */
    public int getHoliday_exDayWeek() {
        return holiday_exDayWeek__;
    }
    /**
     * @param holidayExDayWeek セットする holiday_exDayWeek
     */
    public void setHoliday_exDayWeek(int holidayExDayWeek) {
        holiday_exDayWeek__ = holidayExDayWeek;
    }
    /**
     * @return holiday_furikae
     */
    public int getHoliday_furikae() {
        return holiday_furikae__;
    }
    /**
     * @param holidayFurikae セットする holiday_furikae
     */
    public void setHoliday_furikae(int holidayFurikae) {
        holiday_furikae__ = holidayFurikae;
    }
    /**
     * @return holiday_list
     */
    public ArrayList<String> getHoliday_list() {
        return holiday_list__;
    }
    /**
     * @param holidaylist セットする holiday_list
     */
    public void setHoliday_list(ArrayList<String> holidaylist) {
        holiday_list__ = holidaylist;
    }
    /**
     * @return holiday_map
     */
    public HashMap<String, String> getHoliday_map() {
        return holiday_map__;
    }
    /**
     * @param holidayMap セットする holiday_map
     */
    public void setHoliday_map(HashMap<String, String> holidayMap) {
        holiday_map__ = holidayMap;
    }
    /**
     * @return holiday_tmp_map
     */
    public HashMap<String, HolidayTemplateExModel> getHoliday_tmp_map() {
        return holiday_tmp_map__;
    }
    /**
     * @param holidayTmpMap セットする holiday_tmp_map
     */
    public void setHoliday_tmp_map(
            HashMap<String, HolidayTemplateExModel> holidayTmpMap) {
        holiday_tmp_map__ = holidayTmpMap;
    }
    /**
     * @return updateFlg
     */
    public int getUpdateFlg() {
        return updateFlg__;
    }
    /**
     * @param updateFlg セットする updateFlg
     */
    public void setUpdateFlg(int updateFlg) {
        updateFlg__ = updateFlg;
    }
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param error アクションエラー
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param flg フラグ
     */
    public Man029CsvCheck(ActionErrors error, RequestModel reqMdl, Connection con, int flg) {
        setErrors(error);
        setCon(con);
        setReqMdl(reqMdl);
        setUpdateFlg(flg);
    }

    /**
     * <br>[機　能] CSVファイルのチェックを行なう
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param csvFile 入力ファイル名
     * @return ture:エラー有 false:エラー無し
     * @throws Exception 実行時例外
     */
     public boolean isCsvDataOk(String csvFile) throws Exception {

         boolean ret = false;

         File file = new File(csvFile);
         if (isOverRowCount(file, Encoding.WINDOWS_31J, AbstractCsvRecordReader.MAX_ROW_COUNT)) {
             if (errors__ != null) {
                 GsMessage gsMsg = new GsMessage(reqMdl__);
                 String eprefix = "inputFile.";
                 String textCaptureFile = gsMsg.getMessage("cmn.capture.file");
                 ActionMessage msg =
                     new ActionMessage("error.over.row.csvdata",
                             textCaptureFile,
                             String.valueOf(AbstractCsvRecordReader.MAX_ROW_COUNT));
                 StrutsUtil.addMessage(errors__, msg, eprefix + "error.over.row.csvdata");
             }
             ret = true;
             return ret;
         }
         //ファイル読込み
         readFile(file, Encoding.WINDOWS_31J);
         log__.debug("有効データ件数 " + getCount());

         ret = isErrorFlg();

         //有効データ無し
         if (getCount() == 0) {
             ret = true;
         }
         return ret;
     }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     * @see jp.co.sjts.util.csv.AbstractCsvRecordReader#processedLine(long, java.lang.String)
     */
    protected void processedLine(long num, String lineStr) throws Exception {

        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        if (num > 1) {
            GsMessage gsMsg = new GsMessage(reqMdl__);

            try {

                int j = 0;
                String eprefix = "inputFile." + num + ".";;
                int ecnt = errors__.size();

                log__.debug("項目数=" + stringTokenizer.length());
                if (stringTokenizer.length() != GSConstMain.IMP_VALUE_SIZE_TEMPLATE) {
                    ActionMessage msg =
                        new ActionMessage(
                                "error.input.format.file",
                                gsMsg.getMessage(GSConstMain.TEXT_SELECT_FILE),
                                gsMsg.getMessage(GSConstMain.TEXT_CSV_VALUE_COUNT)
                                + "("
                                + gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)})
                                + ")");
                    StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
                } else {

                    while (stringTokenizer.hasMoreTokens()) {
                        j++;
                        buff = stringTokenizer.nextToken();

                        //休日テンプレート 月
                        if (j == 1) {
                            __isOkMonth(errors__, reqMdl__, buff, num);
                        }

                        //休日テンプレート 日
                        if (j == 2) {
                            __isOkDay(errors__, reqMdl__, buff, num);
                        }

                        //休日テンプレート 休日名称
                        if (j == 3) {
                            __isOkName(errors__, reqMdl__, buff, num);
                        }
                        //休日テンプレート 拡張 月
                        if (j == 4) {
                            __isOkMonthEx(errors__, reqMdl__, buff, num);
                        }

                        //休日テンプレート 拡張 週
                        if (j == 5) {
                            __isOkWeek(errors__, reqMdl__, buff, num);
                        }

                        //休日テンプレート 拡張 曜日
                        if (j == 6) {
                            __isOkDayWeek(errors__, reqMdl__, buff, num);
                        }

                        //休日テンプレート 振替
                        if (j == 7) {
                            __isOkFurikae(errors__, reqMdl__, buff, num);
                        }
                    }
                }

                //エラー有り
                if (ecnt < errors__.size()) {
                    //エラー存在フラグON
                    setErrorFlg(true);
                } else {
                    //明細データ以降
                    if (num > 1) {
                        //有効データ件数カウントアップ
                        int cnt = getCount();
                        cnt += 1;
                        setCount(cnt);
                    }
                }

            } catch (Exception e) {
                log__.error("CSVファイル読込み時例外");
                throw e;
            }

        }
    }

    /**
     * <br>[機  能] 休日名称のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param name 利用目的
     * @param num 行数
     * @throws SQLException SQL実行時例外
     * @return ActionErrors
     */
    private ActionErrors __isOkName(
            ActionErrors errors,
            RequestModel reqMdl,
            String name,
            long num)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "name.";

        String[] lineNo = new String[] {String.valueOf(num)};

        //休日名称 未入力チェック
        if (StringUtil.isNullZeroString(name)) {
            msg =
                new ActionMessage("error.input.required.text",
                        gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_NAME));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
        //休日名称 桁数チェック
        } else if (name.length() > GSConstMain.MAX_LENGTH_HOL_NAME) {
            msg = new ActionMessage("error.input.length.text",
                    gsMsg.getMessage("cmn.line2", lineNo)
                    + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_NAME),
                    String.valueOf(GSConstMain.MAX_LENGTH_HOL_NAME));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.length.text");

        //休日名称 スペースのみチェック
        } else if (ValidateUtil.isSpace(name)) {
            msg = new ActionMessage("error.input.spase.only",
                    gsMsg.getMessage("cmn.line2", lineNo)
                    + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_NAME));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.only");

        //休日名称 先頭スペースチェック
        } else if (ValidateUtil.isSpaceStart(name)) {
            msg = new ActionMessage("error.input.spase.start",
                    gsMsg.getMessage("cmn.line2", lineNo)
                    + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_NAME));
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.spase.start");

        //休日名称 JIS第2水準チェック
        } else if (!GSValidateUtil.isGsJapaneaseString(name)) {
            String nstr = GSValidateUtil.getNotGsJapaneaseString(name);
            msg =
                new ActionMessage("error.input.njapan.text",
                        gsMsg.getMessage("cmn.line2", lineNo)
                        + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_NAME),
                        nstr);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.njapan.text");
        }

        if (holiday_month__ != 0) {
            //日付のチェックを行う
            __isOkDate(errors, reqMdl, num);
        }

        return errors;
    }

    /**
     * <p>月(通常入力)の入力チェックを行う
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param month 月日
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkMonth(ActionErrors errors, RequestModel reqMdl,
                                       String month, long num) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        String title = "";
        title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
        + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_DATE_MONTH);

        int iBMonth = 0;
        //未入力の場合
        if (StringUtil.isNullZeroString(month)) {
            month = "0";
        }

        //最大2桁入力
        if (month.length() > 2) {
            msg = new ActionMessage("error.input.comp.text",
                    title,
                    gsMsg.getMessage("main.src.man027kn.6"));
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.comp.text");
        } else {

            try {
                iBMonth = Integer.parseInt(month);
            } catch (NumberFormatException e) {
                log__.debug("年月日CSV入力エラー");
                msg = new ActionMessage("error.input.notfound.date", title);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.notfound.date");
            }

            //論理チェック
            if (0 > iBMonth || iBMonth > 12) {
                msg = new ActionMessage("error.input.notfound.date", title);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.notfound.date");
            }
        }
        holiday_month__ = iBMonth;
        return errors;
    }

    /**
     * <p>月(拡張入力)の入力チェックを行う
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param month 月日
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkMonthEx(ActionErrors errors, RequestModel reqMdl,
                                   String month, long num) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        String title = "";
        title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
        + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_DATE_MONTH);

        int iBMonth = 0;
        //未入力の場合
        if (StringUtil.isNullZeroString(month)) {
            month = "0";
        }

        //最大2桁入力
        if (month.length() > 2) {
            msg = new ActionMessage("error.input.comp.text",
                    title,
                    gsMsg.getMessage("main.src.man027kn.6"));
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.comp.text");
        } else {

            try {
                iBMonth = Integer.parseInt(month);
            } catch (NumberFormatException e) {
                log__.debug("年月日(拡張)CSV入力エラー");
                msg = new ActionMessage("error.input.notfound.date", title);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.notfound.date");
            }

            //論理チェック
            //拡張入力の場合
            if (holiday_month__ == 0 && holiday_day__ == 0) {
                if (1 > iBMonth || iBMonth > 12) {
                    log__.debug("年月日(拡張)CSV入力エラー");
                    msg = new ActionMessage("error.input.notfound.date", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                }
            }
        }
        holiday_exMonth__ = iBMonth;
        return errors;
    }

    /**
     * <p>週の入力チェックを行う
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param week 週
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkWeek(ActionErrors errors, RequestModel reqMdl,
                String week, long num) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        String title = "";
        title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
        + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_TEMPLATE_DATE_WEEK);

        int iBWeek = 0;
        //未入力の場合
        if (StringUtil.isNullZeroString(week)) {
            week = "0";
        }
        //最大1桁入力
        if (week.length() > 1) {
            msg = new ActionMessage("error.input.comp.text",
                    title,
                    gsMsg.getMessage("main.src.man029.4"));
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.comp.text");
        } else {

            try {
                iBWeek = Integer.parseInt(week);
            } catch (NumberFormatException e) {
                log__.debug("拡張入力 週CSV入力エラー");
                msg = new ActionMessage("error.input.notfound.date", title);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.notfound.date");
            }

            //論理チェック
            //拡張入力の場合のみ判断
            if (holiday_month__ == 0 && holiday_day__ == 0) {
                if (0 > iBWeek || iBWeek > 5) {
                    msg = new ActionMessage("error.input.notfound.date", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                }
            }
        }
        holiday_exWeekMonth__ = iBWeek;
        return errors;
    }

    /**
     * <p>日の入力チェックを行う
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param day 月日
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkDay(ActionErrors errors,
            RequestModel reqMdl, String day, long num) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        String title = "";
        title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
        + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_DATE_DAY);

        int iBDay = 0;
        //未入力の場合
        if (StringUtil.isNullZeroString(day)) {
            day = "0";
        }
        //最大2桁入力
        if (day.length() > 2) {
            msg = new ActionMessage("error.input.comp.text",
                    title,
                    gsMsg.getMessage("main.src.man027kn.6"));
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.comp.text");
        } else {

            try {
                iBDay = Integer.parseInt(day);
            } catch (NumberFormatException e) {
                log__.debug("拡張入力 日 CSV入力エラー");
                msg = new ActionMessage("error.input.notfound.date", title);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.notfound.date");
            }

            //論理チェック
            if (0 > iBDay || iBDay > 31) {

                msg = new ActionMessage("error.input.notfound.date", title);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.notfound.date");
            }
        }
        holiday_day__ = iBDay;
        return errors;
    }

    /**
     * <p>曜日の入力チェックを行う
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param dayWeek 何週目
     * @param num 行数
     * @return ActionErrors
     */
    private ActionErrors __isOkDayWeek(ActionErrors errors, RequestModel reqMdl,
                                        String dayWeek, long num) {

        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        String title = "";
        title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
        + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_TEMPLATE_DATE_DAY_OF_WEEK);

        int iBDayWeek = 0;
        //未入力の場合
        if (StringUtil.isNullZeroString(dayWeek)) {
            dayWeek = "0";
        }
        //最大1桁入力
        if (dayWeek.length() > 1) {
            msg = new ActionMessage("error.input.comp.text",
                    title,
                    gsMsg.getMessage("main.src.man029.4"));
            StrutsUtil.addMessage(errors, msg, eprefix
                    + "error.input.comp.text");
        } else {

            try {
                iBDayWeek = Integer.parseInt(dayWeek);
            } catch (NumberFormatException e) {
                log__.debug("曜日CSV入力エラー");
                msg = new ActionMessage("error.input.notfound.date", title);
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.notfound.date");
            }

            //論理チェック
            //拡張入力の場合のみ判断
            if (holiday_month__ == 0 && holiday_day__ == 0) {
                if (0 > iBDayWeek || iBDayWeek > 6) {
                    msg = new ActionMessage("error.input.notfound.date", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                }
            }
        }
        holiday_exDayWeek__ = iBDayWeek;
        return errors;
    }

    /**
     * <p>日付のチェックを行う
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param num 行数
     * @return errors アクションエラー
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkDate(
            ActionErrors errors, RequestModel reqMdl, long num) throws SQLException {

        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        String eprefix = String.valueOf(num) + "date.";
        String title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                        + GSConstMain.TEXT_HOLIDAY_DATE;

        UDate holDate = new UDate();

        //日付入力チェック(月の最大日)
        holDate.setMonth(holiday_month__);
        int maxDayOfMonth = holDate.getMaxDayOfMonth();
        if (maxDayOfMonth < holiday_day__) {
            msg = new ActionMessage("error.input.notfound.date", title);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.notfound.date");
        } else {
            holDate.setDay(holiday_day__);
        }

        //日付の重複チェックを行う
        __isDateJuhuku(errors, reqMdl, num, holDate);
        return errors;
    }

    /**
     * <p>振替設定のチェックを行う
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param furikae 振替 0=有効 1=無効
     * @param num 行数
     * @return errors アクションエラー
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkFurikae(
             ActionErrors errors, RequestModel reqMdl, String furikae, long num)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        boolean errorFlg = false;
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        String title = "";
        title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
        + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_TEMPLATE_FURIKAE);

        int intFurikae = 0;
        if (StringUtil.isNullZeroString(furikae)) {
            //未入力チェック
            msg = new ActionMessage("error.input.required.text", title);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.input.required.text");
            errorFlg = true;
        } else {
            //最大1桁入力
            if (furikae.length() > 1) {
                msg = new ActionMessage("error.input.comp.text",
                        title,
                        gsMsg.getMessage("main.src.man029.4"));
                StrutsUtil.addMessage(errors, msg, eprefix
                        + "error.input.comp.text");
                errorFlg = true;
            //0か1
            } else if (!furikae.equals("0")
                    && !furikae.equals("1")) {
                       msg =
                           new ActionMessage(
                                   "error.input.comp.text",
                                   title,
                                   "0～1");
                       StrutsUtil.addMessage(errors, msg, eprefix + "editAuth");
            } else {
                try {
                    intFurikae = Integer.parseInt(furikae);
                } catch (NumberFormatException e) {
                    log__.debug("振替CSV入力エラー");
                    msg = new ActionMessage("error.input.notfound.date", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                            + "error.input.notfound.date");
                    errorFlg = true;
                }
            }
        }
        if (errorFlg) {
            holiday_furikae__ = 0;
        } else {
            holiday_furikae__ = intFurikae;
        }

        //日付(拡張入力)の重複チェックを行う
        if (holiday_month__ == 0 && holiday_day__ == 0) {
            __isKakutyouJuhuku(errors, num, reqMdl);
        }

        //通常入力と拡張入力の同時入力のチェックを行う
        __isOkExAndNormal(errors, reqMdl, num);

        return errors;
    }

    /**
     * <p>日付の重複チェックを行う
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param num 行数
     * @param holDate 入力日付
     * @return errors アクションエラー
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isDateJuhuku(
            ActionErrors errors, RequestModel reqMdl,
            long num, UDate holDate) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        String title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
        + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_DATE);
        List<CmnHolidayTemplateModel> mdlList = new ArrayList<CmnHolidayTemplateModel>();
        String strHolDate = holDate.getDateString();

        //日付の重複チェック
        boolean holFlg = holiday_map__.containsKey(strHolDate);
        if (holFlg) {
            msg = new ActionMessage("error.select.dup.list2", title);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.dup.list2");
        } else {
            holiday_map__.put(strHolDate, "1");
        }

        //上書き判断
        if (updateFlg__ == 0) {
            //DB重複チェック
            CmnHolidayTemplateDao dao = new CmnHolidayTemplateDao(con__);
            mdlList = dao.select();
            for (CmnHolidayTemplateModel model : mdlList) {
                int month = model.getHltDateMonth();
                int day = model.getHltDateDay();
                UDate dbDate = new UDate();
                dbDate.setMonth(month);
                dbDate.setDay(day);
                String strDbDate = dbDate.getDateString();
                //DB内のデータと同一か判断
                if (strHolDate.equals(strDbDate)) {
                    msg = new ActionMessage("error.input.timecard.exist", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                                             + "error.input.timecard.exist");
                }
            }
        }

       return errors;
    }

    /**
     * <p>日付(拡張入力)の重複チェックを行う
     * @param errors ActionErrors
     * @param num 行数
     * @param reqMdl リクエスト情報
     * @return errors アクションエラー
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isKakutyouJuhuku(
            ActionErrors errors, long num, RequestModel reqMdl) throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";
        GsMessage gsMsg = new GsMessage(reqMdl);
        String title = gsMsg.getMessage("cmn.line2", new String[] {String.valueOf(num)})
                      + gsMsg.getMessage(GSConstMain.TEXT_HOLIDAY_DATE)
                      + " "
                      + gsMsg.getMessage("main.src.man029.5");

        List<CmnHolidayTemplateModel> mdlList = new ArrayList<CmnHolidayTemplateModel>();
        String strExMonth = getMonthDay(holiday_exMonth__);
        String strExWeekMonth = getMonthDay(holiday_exWeekMonth__);
        String strExDayWeek = getMonthDay(holiday_exDayWeek__);
        //CSV1行分の拡張入力項目(日付)
        String csvKakutyouData = strExMonth + strExWeekMonth + strExDayWeek;

        HolidayTemplateExModel holMdl = new HolidayTemplateExModel();

        //日付の重複チェック
        boolean holFlg = holiday_tmp_map__.containsKey(strExMonth);
        if (holFlg) {
            holMdl = holiday_tmp_map__.get(strExMonth);
            if (strExWeekMonth.equals(holMdl.getExWeek())
                && strExDayWeek.equals(holMdl.getExDay())) {
                msg = new ActionMessage("error.select.dup.list2", title,
                        gsMsg.getMessage("cmn.date.incapture.file"));
                StrutsUtil.addMessage(errors, msg, eprefix + "error.select.dup.list2");
            }
        } else {
            holMdl.setExWeek(strExWeekMonth);
            holMdl.setExDay(strExDayWeek);
            holiday_tmp_map__.put(strExMonth, holMdl);
        }

        //上書き判断
        if (updateFlg__ == 0) {
            //DB重複チェック
            CmnHolidayTemplateDao dao = new CmnHolidayTemplateDao(con__);
            mdlList = dao.select();
            for (CmnHolidayTemplateModel model : mdlList) {
                int month = model.getHltExMonth();
                int week = model.getHltExWeekMonth();
                int day = model.getHltExDayWeek();
                //文字列加工
                String strMonth = getMonthDay(month);
                String strWeek = getMonthDay(week);
                String strDay = getMonthDay(day);
                String dbKakutyouData = strMonth + strWeek + strDay;
                //DB内のデータと同一か判断
                if (csvKakutyouData.equals(dbKakutyouData)) {
                    msg = new ActionMessage("error.input.timecard.exist", title);
                    StrutsUtil.addMessage(errors, msg, eprefix
                                             + "error.input.timecard.exist");
                }
            }
        }
       return errors;
    }

    /**
     * <p>通常入力と拡張入力の同時登録のチェックを行う
     * @param errors ActionErrors
     * @param reqMdl リクエスト情報
     * @param num 行数
     * @return errors アクションエラー
     * @throws SQLException SQL実行時例外
     */
    private ActionErrors __isOkExAndNormal(
            ActionErrors errors, RequestModel reqMdl, long num) throws SQLException {

        ActionMessage msg = null;
        String eprefix = String.valueOf(num) + "date.";

        if (!isOkNyuryoku()) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            msg = new ActionMessage(
                        "error.input.format.file",
                        gsMsg.getMessage(GSConstMain.TEXT_SELECT_FILE),
                        gsMsg.getMessage(GSConstMain.TEXT_CSV_VALUE_COUNT)
                        + "(" + gsMsg.getMessage("cmn.line", new String[] {String.valueOf(num)})
                        + ")");
            StrutsUtil.addMessage(errors__, msg, eprefix + num + "error.input.format.file");
        }
       return errors;
    }

    /**
     * <p>通常と拡張が同時入力かどうか
     * @return hantei true=正常入力  false=不正入力
     */
    public boolean isOkNyuryoku() {
        boolean hantei = false;
        if (holiday_month__ != 0
            && holiday_exMonth__ != 0) {
            hantei = false;
        } else {
            hantei = true;
        }

        return hantei;
    }

    /**
     * <p>1桁の数値型を2桁の文字列にして返す
     * @param target 数値
     * @return str 文字列
     */
    public String getMonthDay(int target) {
        if (target < 0) {
            target = 0;
        }
        String str = Integer.toString(target);

        if (str.length() <= 1) {
           str = "0" + str;
        }
        return str;
    }
}