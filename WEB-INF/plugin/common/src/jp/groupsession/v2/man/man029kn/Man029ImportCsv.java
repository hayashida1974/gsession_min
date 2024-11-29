package jp.groupsession.v2.man.man029kn;

import java.io.File;
import java.sql.Connection;
import java.util.List;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayTemplateDao;
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 休日テンプレートCSVファイルの取り込み処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man029ImportCsv extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man029ImportCsv.class);
    /** コネクション */
    private Connection con__ = null;

    /** ユーザSID */
    private int userSid__ = -1;
    /** 取込み日付 */
    private UDate now__ = null;
    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** 休日テンプレートDAO インスタンス */
    private CmnHolidayTemplateDao hltDao__ = null;

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
     * @return hltDao
     */
    public CmnHolidayTemplateDao getHltDao() {
        return hltDao__;
    }
    /**
     * @param hltDao セットする hltDao
     */
    public void setHltDao(CmnHolidayTemplateDao hltDao) {
        hltDao__ = hltDao;
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
    public Man029ImportCsv(Connection con,
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

        hltDao__ = new CmnHolidayTemplateDao(getCon());

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
        CmnHolidayTemplateModel hltMdl = new CmnHolidayTemplateModel();
        if (num > 1) {

            try {
                int hltSid = 0;
                UDate holDate = new UDate();
                UDate nowDate = new UDate();
                holDate.setMilliSecond(0);

                hltMdl.setHltAddate(nowDate);
                hltMdl.setHltUpdate(nowDate);
                hltMdl.setHltUpuser(userSid__);
                hltMdl.setHltAduser(userSid__);

                int j = 0;

                while (stringTokenizer.hasMoreTokens()) {

                    j++;
                    buff = stringTokenizer.nextToken();

                    //月
                    if (j == 1) {
                        if (StringUtil.isNullZeroString(buff)) {
                            buff = "0";
                        }
                        int month = Integer.parseInt(buff);
                        hltMdl.setHltDateMonth(month);
                    }

                    //日
                    if (j == 2) {
                        if (StringUtil.isNullZeroString(buff)) {
                            buff = "0";
                        }
                        int day = Integer.parseInt(buff);
                        hltMdl.setHltDateDay(day);
                    }

                    //休日名称
                    if (j == 3) {
                        hltMdl.setHltName(buff);
                    }

                    //拡張入力 月
                    if (j == 4) {
                        if (StringUtil.isNullZeroString(buff)) {
                            buff = "0";
                        }
                        int exMonth = Integer.parseInt(buff);
                        hltMdl.setHltExMonth(exMonth);
                    }

                    //拡張入力 週
                    if (j == 5) {
                        if (StringUtil.isNullZeroString(buff)) {
                            buff = "0";
                        }
                        int exWeekMonth = Integer.parseInt(buff);
                        hltMdl.setHltExWeekMonth(exWeekMonth);
                    }

                    //拡張入力 曜日
                    if (j == 6) {
                        if (StringUtil.isNullZeroString(buff)) {
                            buff = "0";
                        }
                        int exDayWeek = Integer.parseInt(buff);
                        hltMdl.setHltExDayWeek(exDayWeek);
                    }

                    //振替設定
                    if (j == 7) {
                        int furikae = Integer.parseInt(buff);
                        hltMdl.setHltExFurikae(furikae);
                    }

                    //拡張フラグ
                    if (hltMdl.getHltDateDay() == 0
                        && hltMdl.getHltDateMonth() == 0) {
                        hltMdl.setHltExflg(1);
                    } else {
                        hltMdl.setHltExflg(0);
                    }
                }


                //同じ日付は上書きする場合
                if (updateFlg__ == 1) {
                    CmnHolidayTemplateModel model = hltDao__.isSelectDate(hltMdl);
                    //登録
                    if (model == null) {
                        hltSid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_MAIN,
                                SaibanModel.SBNSID_SUB_HLT, userSid__);
                        hltMdl.setHltSid(hltSid);
                        hltDao__.insert(hltMdl);
                    //更新
                    } else {
                        hltSid = model.getHltSid();
                        hltMdl.setHltSid(hltSid);
                        hltDao__.updateCmnTemplate(hltMdl);
                    }
                } else {
                    hltSid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_MAIN,
                            SaibanModel.SBNSID_SUB_HLT, userSid__);
                    hltMdl.setHltSid(hltSid);
                    hltDao__.insert(hltMdl);
                }

            } catch (Exception e) {
                log__.error("CSVファイルインポート時例外");
                throw e;
            }

        }
    }
}