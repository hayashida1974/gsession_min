package jp.groupsession.v2.man.man028kn;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 休日設定CSVファイルの取り込み処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 *
 */
public class Man028knImportCsv extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man028knImportCsv.class);
    /** コネクション */
    private Connection con__ = null;

    /** ユーザSID */
    private int userSid__ = -1;
    /** 取込み日付 */
    private UDate now__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** 休日設定DAO インスタンス */
    private CmnHolidayDao holDao__ = null;

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
     * <p>userSid__ を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid__ をセットします。
     * @param userSid userSid__
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>now__ を取得します。
     * @return now
     */
    public UDate getNow() {
        return now__;
    }
    /**
     * <p>now__ をセットします。
     * @param now now__
     */
    public void setNow(UDate now) {
        now__ = now;
    }
    /**
     * <p>cntCon__ を取得します。
     * @return cntCon
     */
    public MlCountMtController getCntCon() {
        return cntCon__;
    }
    /**
     * <p>cntCon__ をセットします。
     * @param cntCon cntCon__
     */
    public void setCntCon(MlCountMtController cntCon) {
        cntCon__ = cntCon;
    }

    /**
     * @return holDao
     */
    public CmnHolidayDao getHolDao() {
        return holDao__;
    }
    /**
     * @param holDao セットする holDao
     */
    public void setHolDao(CmnHolidayDao holDao) {
        holDao__ = holDao;
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
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @param now 取込み日時
     * @param cntCon 採番用コネクション
     * @param flg フラグ
     */
    public Man028knImportCsv(Connection con,
                         int userSid,
                         UDate now,
                         MlCountMtController cntCon,
                         int flg) {
        setCon(con);
        setUserSid(userSid);
        setNow(now);
        setCntCon(cntCon);
        setUpdateFlg(flg);
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     *
     * @param tmpFileDir テンポラリディレクトリ
     * @return num 件数
     * @throws Exception 実行時例外
     */
    public long importCsv(String tmpFileDir) throws Exception {

        holDao__ = new CmnHolidayDao(getCon());

        //テンポラリディレクトリにあるファイル名称を取得
        String saveFileName = "";
        List<String> fileList = IOTools.getFileNames(tmpFileDir);
        for (int i = 0; i < fileList.size(); i++) {
            //ファイル名を取得
            String fileName = fileList.get(i);
            if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                continue;
            }

            //オブジェクトファイルを取得
            ObjectFile objFile = new ObjectFile(tmpFileDir, fileName);
            Object fObj = objFile.load();
            if (fObj == null) {
                continue;
            }
            Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
            saveFileName = fMdl.getSaveFileName();
        }
        String csvFile = tmpFileDir + saveFileName;

        //ファイル取込
        long num = readFile(new File(csvFile), Encoding.WINDOWS_31J);
        return num;
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
        CmnHolidayModel holMdl = new CmnHolidayModel();
        if (num > 1) {

            try {
                UDate holDate = new UDate();
                UDate nowDate = new UDate();
                holDate.setMilliSecond(0);

                holMdl.setHolAddate(nowDate);
                holMdl.setHolUpdate(nowDate);
                holMdl.setHolUpuser(userSid__);
                holMdl.setHolAduser(userSid__);

                int j = 0;

                while (stringTokenizer.hasMoreTokens()) {

                    j++;
                    buff = stringTokenizer.nextToken();

                    //年
                    if (j == 1) {
                        int year = Integer.parseInt(buff);
                        holDate.setYear(year);
                        holMdl.setHolYear(year);
                    }

                    //月
                    if (j == 2) {
                        int month = Integer.parseInt(buff);
                        holDate.setMonth(month);
                    }

                    //日
                    if (j == 3) {
                        int day = Integer.parseInt(buff);
                        holDate.setDay(day);
                    }

                    //利用目的
                    if (j == 4) {
                        holMdl.setHolName(buff);
                        holMdl.setHolDate(holDate);
                    }
                }

                if (updateFlg__ == 1) {
                    CmnHolidayModel mdl = holDao__.select(holMdl);
                    if (mdl == null) {
                        holDao__.insertHoliday(holMdl);
                    } else {
                        holDao__.updateHoliday(holMdl, holDate);
                    }
                } else {
                    holDao__.insertHoliday(holMdl);
                }

            } catch (Exception e) {
                log__.error("CSVファイルインポート時例外");
                throw e;
            }
        }
    }
}