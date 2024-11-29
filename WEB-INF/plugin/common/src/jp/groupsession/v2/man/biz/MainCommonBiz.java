package jp.groupsession.v2.man.biz;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.encryption.Sha;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.dao.base.CmnPswdConfDao;
import jp.groupsession.v2.cmn.dao.base.SaibanDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.cmn.model.base.CmnPswdConfModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.dao.base.CmnImpTimeDao;
import jp.groupsession.v2.man.dao.base.CmnPconfEditDao;
import jp.groupsession.v2.man.model.CmnImpTimeModel;
import jp.groupsession.v2.man.model.base.CmnPconfEditModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 共通ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MainCommonBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MainCommonBiz.class);

    /**
     * <p>パスフレーズ定数
     * <p>echo -n "GS backup_auto" | sha256sum の結果
     * <p>[d9dd047e392a218ddcfff577373e00a5c843fed97986688586864f8a075a8036]
     * <p>パスフレーズは最大16桁までなので先頭から16文字
     */
    public static final String BACKUP_AUTO_PHRASE = "d9dd047e392a218d";

    /**
     * <p>パスフレーズ定数
     * <p>echo -n "GS backup_self" | sha256sum の結果
     * <p>[4aef2d11b117f6ea59dc86b5db7bb912dfc41bf593018ae388f5607f98250bf8]
     * <p>パスフレーズは最大16桁までなので先頭から16文字
     */
    public static final String BACKUP_SELF_PHRASE = "4aef2d11b117f6ea";

    /**
     * <br>[機  能] 自動バックアップファイル名のハッシュ値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param text ファイル名
     * @return ハッシュ値
     */
    public static String getAutoBackUpHashName(String text) {

        log__.debug("ファイル名よりハッシュ値を取得（自動バックアップ）");

        String fileName = Sha.encryptSha256(BACKUP_AUTO_PHRASE, text);
        fileName = NullDefault.getString(fileName, "");

        if (fileName.length() < 1) {
            log__.debug("ハッシュ値を取得失敗（自動バックアップ）");
        }

        return fileName;
    }

    /**
     * <br>[機  能] 手動バックアップファイル名のハッシュ値を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param text ファイル名
     * @return ハッシュ値
     */
    public static String getSelfBackUpHashName(String text) {

        log__.debug("ファイル名よりハッシュ値を取得（手動バックアップ）");

        String fileName = Sha.encryptSha256(BACKUP_SELF_PHRASE, text);
        fileName = NullDefault.getString(fileName, "");

        if (fileName.length() < 1) {
            log__.debug("ハッシュ値を取得失敗（手動バックアップ）");
        }

        return fileName;
    }

    /**
     * <br>[機  能] 個人情報編集権限の設定値を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid セッションユーザSID
     * @param con コネクション
     * @return CmnPconfEditModel 管理者設定
     * @throws SQLException SQL実行時例外
     */
    public CmnPconfEditModel getCpeConf(int userSid, Connection con) throws SQLException {

        CmnPconfEditDao dao = new CmnPconfEditDao(con);
        CmnPconfEditModel ret = dao.selectConf();

        //未設定の場合、初期値を設定する
        if (ret == null) {
            UDate now = new UDate();

            ret = new CmnPconfEditModel();
            ret.setCpePconfKbn(GSConstMain.PCONF_EDIT_USER);
            ret.setCpePasswordKbn(GSConstMain.PASSWORD_EDIT_USER);
            ret.setCpeAuid(userSid);
            ret.setCpeAdate(now);
            ret.setCpeEuid(userSid);
            ret.setCpeEdate(now);
            ret.setCpeOtpsendKbn(GSConstMain.PCONF_EDIT_USER);
        }

        return ret;
    }

    /**
     * <br>[機  能] パスワード有効期限設定値を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return パスワード有効期限設定値（0：無制限、1：有効期限あり）
     * @throws SQLException SQL実行例外
     */
    public int checkPassLimit(Connection con) throws SQLException {

        int limit  = -1;
        CmnPswdConfDao dao = new CmnPswdConfDao(con);
        CmnPswdConfModel model = dao.select();

        if (model != null) {
            //設定
            if (model.getPwcLimitDay() == -1) {
                limit = GSConstMain.PWC_LIMITKBN_OFF;
            } else {
                limit = GSConstMain.PWC_LIMITKBN_ON;
            }
        } else {
            //初期
            limit = GSConstMain.PWC_LIMITKBN_OFF;
        }
        return limit;
    }


    /**
     * <br>[機  能] 役職コードを自動生成し取得する
     * <br>[解  説] 役職コード生成ルール
     *                     1. 役職SIDと同じ値を返す
     *                     2. 1で発行したコードが既に使用されていた場合は役職SIDの最大＋１を返す
     *                     3. 2で取得したコードが既に使用されていた場合は重複しないまで＋１を繰り替えす
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @return 役職コード
     */
    public String getAutoPosCode(Connection con) throws SQLException {

        SaibanDao sbDao = new SaibanDao(con);

        //采番マスタ検索モデル
        SaibanModel searchMdl = new SaibanModel();
        searchMdl.setSbnSid(SaibanModel.SBNSID_USER);
        searchMdl.setSbnSidSub(SaibanModel.SBNSID_SUB_POS);

        //采番マスタより役職SIDの現在のMAX採番値を取得する
        SaibanModel mdl = sbDao.getSaibanData(searchMdl);

        //采番値として出力される予定の役職SID
        long sbPosSid = 1;
        if (mdl != null) {
            sbPosSid = mdl.getSbnNumber() + 1;
        }

        return getAutoPosCode(con, (int) sbPosSid);
    }

    /**
     * <br>[機  能] 役職コードを自動生成し取得する
     * <br>[解  説] 役職コード生成ルール
     *                     1. 役職SIDと同じ値を返す
     *                     2. 1で発行したコードが既に使用されていた場合は役職SIDの最大＋１を返す
     *                     3. 2で取得したコードが既に使用されていた場合は重複しないまで＋１を繰り替えす
     * <br>[備  考]
     * @param con コネクション
     * @param sbPosSid 采番値として出力される予定の役職SID
     * @throws SQLException SQL実行例外
     * @return 役職コード
     */
    public String getAutoPosCode(Connection con, int sbPosSid) throws SQLException {

        CmnPositionDao cpDao = new CmnPositionDao(con);
        String code = String.valueOf(sbPosSid);

        //重複チェック
        while (cpDao.isExistPositionCode(code, -1)) {
            sbPosSid += 1;
            code = String.valueOf(sbPosSid);
        }

        return code;
    }

    /**
     *
     * <br>[機  能]ライセンスの残り有効期限を区分で返します
     * <br>[解  説]
     * <br>[備  考]
     * @param limitTime 有効期限
     * @return 残り期限 30日以上:1 30日未満:0 期限切れ:-1
     */
    public int licenseCheck(String limitTime) {
        UDate now = new UDate();
        UDate limit = UDate.getInstanceStr(limitTime);

        if (now.getTimeMillis() > limit.getTimeMillis()) {
            return GSConstMain.EXPIRATION;
        }

        int remainingDay = UDateUtil.diffDay(now, limit);

        if (remainingDay > GSConstMain.WARNING_PERIOD) {
            return GSConstMain.OUTSIDE_OF_WARNING_PERIOD;
        } else {
            return GSConstMain.WITHIN_WARNING_PERIOD;
        }

    }

    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return CmnImpTimeModel
     * @throws SQLException SQL実行時例外
     */
    public CmnImpTimeModel getCmnImpTimeModel(Connection con) throws SQLException {
        CmnImpTimeDao citDao = new CmnImpTimeDao(con);
        CmnImpTimeModel citMdl = citDao.select();

        if (citMdl == null) {
            citMdl = new CmnImpTimeModel();
            UDate day = new UDate();
            day.setHour(6);

            citMdl.setCitUsrFlg(GSConstUser.AUTO_IMPORT_NO);
            citMdl.setCitUsrTimeFlg(GSConstUser.IMPORT_TIMING_MIN);
            citMdl.setCitUsrTime(day);

            citMdl.setCitGrpFlg(GSConstUser.AUTO_IMPORT_NO);
            citMdl.setCitGrpTimeFlg(GSConstUser.IMPORT_TIMING_MIN);
            citMdl.setCitGrpTime(day);

            citMdl.setCitBegFlg(GSConstUser.AUTO_IMPORT_NO);
            citMdl.setCitBegTimeFlg(GSConstUser.IMPORT_TIMING_MIN);
            citMdl.setCitBegTime(day);

            citDao.insert(citMdl);
        }

        return citMdl;
    }

    /**
     * <br>[機  能] サーバのOS情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return OS情報
     */
    public String getServerOS() {
        //OS
        StringBuilder osbuf = new StringBuilder();
        Properties props = System.getProperties();
        osbuf.append(props.getProperty("os.name", ""));
        //OSビット数(不明な場合は空白)
        String cpuBit = "";
        String os = props.getProperty("os.arch", "");
        if (os != null) {
            if (os.endsWith("86")) {
                cpuBit = " 32bit";
            } else if (os.endsWith("64")) {
                cpuBit = " 64bit";
            }
        }
        osbuf.append(cpuBit);
        osbuf.append(" " + props.getProperty("os.version", ""));

        return osbuf.toString();
    }

    /**
     * <br>[機  能] CPUのコア数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return CPUのコア数
     */
    public String getServerCpuCore() {
        return String.valueOf(Runtime.getRuntime().availableProcessors());
    }

    /**
     * <br>[機  能] JVMビットモードを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return JVMビットモード
     */
    public String getServerJVM() {
        Properties props = System.getProperties();
        return props.getProperty("os.arch", "");
    }

    /**
     * <br>[機  能] サーブレットコンテナバージョンを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param context コンテキスト
     * @return サーブレットコンテナバージョン
     */
    public String getServerJ2ee(ServletContext context) {
        return context.getServerInfo();
    }

    /**
     * <br>[機  能] JAVAのバージョンを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return JAVAバージョン
     */
    public String getServerJava() {

        //JVMビットモード
        Properties props = System.getProperties();

        //Javaバージョン
        StringBuilder jsb = new StringBuilder();
        jsb.append(props.getProperty("java.version", ""));
        jsb.append(" ");
        jsb.append(props.getProperty("java.vendor", ""));
        jsb.append(" (");
        jsb.append(props.getProperty("java.vm.name", ""));
        jsb.append(")");
        return jsb.toString();
    }

    /**
     * <br>[機  能] ディスクの空き容量を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param dbDirPath DBディレクトリパス
     * @return ディスクの空き容量
     */
    public String getServerFreeSpace(String dbDirPath) {

        //ディスクの空き容量
        BigDecimal freeSpace = new BigDecimal(new File(dbDirPath).getFreeSpace() / 1024);
        StringBuilder strFreeSpace = new StringBuilder("");
        strFreeSpace.append(freeSpace.divide(new BigDecimal(1024 * 1024), 1).toString());
        strFreeSpace.append("GB");
        strFreeSpace.append(" (");
        strFreeSpace.append(freeSpace.divide(new BigDecimal(1024), 1).toString());
        strFreeSpace.append("MB)");

        return strFreeSpace.toString();
    }

    /**
     * <br>[機  能] コネクション件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return ディスクの空き容量
     * @throws Exception 実行例外
     */
    public String getServerConnection(RequestModel reqMdl) throws Exception {

        String connectionCount = new String();
        DataSource ds = GroupSession.getResourceManager().getDataSource(reqMdl);
        if (ds instanceof org.apache.commons.dbcp2.BasicDataSource) {
            org.apache.commons.dbcp2.BasicDataSource bds
            = (org.apache.commons.dbcp2.BasicDataSource) ds;
            int aconcnt = bds.getNumActive();
            int iconcnt = bds.getNumIdle();
            connectionCount = "ACTIVE=" + aconcnt + " IDLE=" + iconcnt;
        }
        return connectionCount;
    }

    /**
     * <p>休日設定時の日付を取得
     * @param reqMdl リクエストモデル
     * @param holTempMdl 休日テンプレートモデル
     * @return 日付
     * */
    public String getHolidayDate(RequestModel reqMdl, CmnHolidayTemplateModel holTempMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        int month = holTempMdl.getHltDateMonth();
        int day = holTempMdl.getHltDateDay();
        int exMonth = holTempMdl.getHltExMonth();
        int exWeekMonth = holTempMdl.getHltExWeekMonth();
        int exDayMonth = holTempMdl.getHltExDayWeek();
        int exFlg = holTempMdl.getHltExflg();
        String date = "";
        String order = gsMsg.getMessage("cmn.no." + exWeekMonth);
        String week = "";
        if (exFlg == GSConstMain.HOLIDAY_TEMPLATE_EX_NO) {
            date = month + "月 " + day + "日";
        } else {
            date = exMonth + "月 " + order;
            // 曜日
            if (exDayMonth == GSConstMain.WEEK_MON) {
                week = gsMsg.getMessage("cmn.monday3");
            } else if (exDayMonth == GSConstMain.WEEK_TUE) {
                week = gsMsg.getMessage("cmn.tuesday3");
            } else if (exDayMonth == GSConstMain.WEEK_WED) {
                week = gsMsg.getMessage("cmn.wednesday3");
            } else if (exDayMonth == GSConstMain.WEEK_THU) {
                week = gsMsg.getMessage("cmn.thursday3");
            } else if (exDayMonth == GSConstMain.WEEK_FRI) {
                week = gsMsg.getMessage("cmn.friday3");
            } else if (exDayMonth == GSConstMain.WEEK_SAT) {
                week = gsMsg.getMessage("cmn.saturday3");
            } else if (exDayMonth == GSConstMain.WEEK_SUN) {
                week = gsMsg.getMessage("cmn.sunday3");
            }
            date += week;
        }
        return date;
    }

    /**
     * <br>[機  能] システムプロパティからOS名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return OS名称
     */
    public String getOSName() {
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName == null) {
            osName = "";
        }
        return osName;
    }

    /**
     * <br>[機  能] OSがWindowsかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return 判定結果
     */
    public boolean isWindows() {
        return (getOSName().indexOf("win") >= 0);
    }

    /**
     * <br>[機  能] OSがUnix / Linuxかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return 判定結果
     */
    public boolean isUnix() {
        String osName = getOSName();
        return (osName.indexOf("nix") >= 0 || osName.indexOf("nux") >= 0);
    }

    /**
     * <br>[機  能] CPUプロセッサを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return CPUプロセッサ
     */
    public String getCPUProcesser() throws IOException {
        String command = null;

        if (isUnix()) {
            command = "lscpu";
        } else if (isWindows()) {
            command = "cmd /C WMIC CPU Get /Format:List";
        }

        BufferedReader reader = null;
        String cpuModel = null;

        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line = null;
            while ((line = reader.readLine()) != null) {
                if (isUnix()) {
                    if (line.contains("Model name:")) {
                        cpuModel = line.replaceAll("\\s+", " ").replace("Model name: ", "");
                        break;
                    }
                } else if (isWindows()) {
                    if (line.startsWith("Name")) {
                        cpuModel = line.split("=")[1];
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log__.error("CPUプロセッサの取得に失敗", e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return cpuModel;
    }

    /**
     * <br>[機  能] メモリサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return メモリサイズ
     */
    public String getMemorySize() throws IOException {
        String command = null;

        if (isUnix()) {
            command = "free -h";
        } else if (isWindows()) {
            command = "cmd /C WMIC computersystem get Totalphysicalmemory";
        }

        BufferedReader reader = null;
        String memSize = null;

        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line = null;
            while ((line = reader.readLine()) != null) {
                String newLine = line.strip();
                if (isUnix()) {
                    if (line.contains("Mem:")) {
                        memSize = line.split("\\s+")[1];
                        if (!memSize.endsWith("B")) {
                            memSize += "B";
                        }
                    }
                } else if (isWindows()) {
                    if(newLine.length() > 0 && ValidateUtil.isNumber(newLine)) {
                        BigDecimal totalMem = new BigDecimal(newLine);
                        BigDecimal bdMemSize
                            = totalMem.divide(new BigDecimal(1024 * 1024 * 1024),
                                            RoundingMode.UP);
                        memSize = bdMemSize.toString() + "GB";
                    }
                }
            }
        } catch (Exception e) {
            log__.error("メモリサイズの取得に失敗", e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }
        return memSize;
    }

    /**
     * <br>[機  能] ディスク情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ディスク情報
     */
    public List<List<String>> getDiskInfo() throws IOException {
        List<List<String>> diskInfoList = new ArrayList<List<String>>();

        String command = null;
        if (isUnix()) {
            command = "df -h";
        } else if (isWindows()) {
            command = "cmd /C WMIC logicaldisk get caption, freespace, size";
        }

        Process process = null;
        BufferedReader reader = null;

        try {
            process = Runtime.getRuntime().exec(command);
            reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            int headElmCnt = 0;
            while ((line = reader.readLine()) != null) {
                //半角スペースで分割
                line = line.replaceAll("\\s+", " ");

                if (line.trim().length() > 0) { //空白行は除外
                    List<String> lineList
                        = (List<String>) Arrays.asList(line.split(" "));
                    if (diskInfoList.isEmpty()) {
                        //ヘッダ行の項目数を取得
                        headElmCnt = lineList.size();
                    } else if (headElmCnt > lineList.size()) {
                        List<String> meisaiList = new ArrayList<String>();
                        meisaiList.addAll(lineList);
                        int addCnt = headElmCnt - lineList.size();
                        while (addCnt-- > 0) {
                            meisaiList.add(" ");
                        }
                        lineList = meisaiList;
                    }

                    diskInfoList.add(lineList);
                }
            }

        } catch (Exception e) {
            log__.error("ディスク情報の取得に失敗", e);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        return diskInfoList;
    }
}
