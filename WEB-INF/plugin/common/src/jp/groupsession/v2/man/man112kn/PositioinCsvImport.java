package jp.groupsession.v2.man.man112kn;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.csv.AbstractCsvRecordReader;
import jp.co.sjts.util.csv.CsvTokenizer;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 役職CSVファイルの取り込み処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PositioinCsvImport extends AbstractCsvRecordReader {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PositioinCsvImport.class);

    /** 実行モード インポート */
    public static final int MODE_IMPORT = 0;
    /** 実行モード インポート情報を取得 */
    public static final int MODE_GETDATA = 1;

    /** 実行モード */
    private int mode__;
    /** コネクション */
    private Connection con__ = null;

    /** 採番コントローラ */
    private MlCountMtController cntCon__ = null;
    /** セッションユーザ */
    private int sessionUser__;
    /** システム日付 */
    private UDate sysUd__;
    /** 取込み役職情報リスト*/
    private ArrayList<CmnPositionModel> posiList__ = null;
    /** 既存の役職情報更新フラグ */
    private int updateFlg__ = 0;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param cntCon 採番コントローラ
     * @param userSid セッションユーザSID
     * @param mode 実行モード 0=インポート 1=インポート情報を取得
     * @param con コネクション
     * @param updateFlg 既存のユーザ情報更新フラグ
     */
    public PositioinCsvImport(MlCountMtController cntCon,
                              int userSid,
                              int mode,
                              Connection con,
                              int updateFlg) {

        setCntCon(cntCon);
        setSessionUser(userSid);
        setMode(mode);
        setCon(con);
        setSysUd(new UDate());
        setPosiList(new ArrayList<CmnPositionModel>());
        setUpdateFlg(updateFlg);
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param mode 実行モード 0=インポート 1=インポート情報を取得
     */
    public PositioinCsvImport(int mode) {

        setMode(mode);
        setPosiList(new ArrayList<CmnPositionModel>());
    }

    /**
     * <br>[機　能] CSVファイルを取り込む
     * <br>[解　説]
     * <br>[備  考]
     * @param tmpFileDir テンポラリディレクトリ
     * @return posSidList__ 役職情報リスト
     * @throws  Exception   実行時例外
     */
    public ArrayList<CmnPositionModel> importCsv(String tmpFileDir)
        throws Exception {

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
        readFile(new File(csvFile), Encoding.WINDOWS_31J);

        //表示順の更新
        if (mode__ == MODE_IMPORT) {
            __sortPosition();
        }

        //登録・登録予定内容を設定
        return posiList__;
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

        log__.debug("PositioinCsvImport Start");
        //ヘッダ文字列読み飛ばし
        if (num == 1) {
            return;
        }

        //処理モード
        if (mode__ == MODE_IMPORT) {
            __import(num, lineStr);
        } else {
            //登録処理無し
            __getData(num, lineStr);
        }

        log__.debug("PositioinCsvImport End");
    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    private void __getData(long num, String lineStr) throws Exception {

        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        //役職情報
        CmnPositionModel posiModel = new CmnPositionModel();
        while (stringTokenizer.hasMoreTokens()) {
            j++;
            buff = stringTokenizer.nextToken();

            //役職コード
            if (j == 1) {
                posiModel.setPosCode(NullDefault.getString(buff, ""));
            }
            //役職
            if (j == 2) {
                posiModel.setPosName(NullDefault.getString(buff, ""));
            }
            //表示順
            if (j == 3) {
                posiModel.setPosSort(NullDefault.getInt(buff, 0));
            }
            //備考
            if (j == 4) {
                posiModel.setPosBiko(NullDefault.getString(buff, ""));
            }
        }
        posiList__.add(posiModel);
    }

    /**
     * <br>[機  能] csvファイル一行の処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param num 行番号
     * @param lineStr 行データ
     * @throws Exception csv読込時例外
     */
    private void __import(long num, String lineStr) throws Exception {

        int j = 0;
        String buff;
        CsvTokenizer stringTokenizer = new CsvTokenizer(lineStr, ",");

        CmnPositionModel posiModel = new CmnPositionModel();
        CmnPositionDao posiDao = new CmnPositionDao(con__);

        while (stringTokenizer.hasMoreTokens()) {
            j++;
            buff = stringTokenizer.nextToken();

            //役職コード
            if (j == 1) {
                posiModel.setPosCode(NullDefault.getString(buff, ""));
            }
            //役職名
            if (j == 2) {
                posiModel.setPosName(NullDefault.getString(buff, ""));
            }
            //表示順
            if (j == 3) {
                posiModel.setPosSort(NullDefault.getInt(buff, 0));
            }

            //備考
            if (j == 4) {
                posiModel.setPosBiko(NullDefault.getString(buff, ""));
            }

        }
        int positionSid = 0;

        posiModel.setPosAuid(sessionUser__);
        posiModel.setPosAdate(sysUd__);
        posiModel.setPosEuid(sessionUser__);
        posiModel.setPosEdate(sysUd__);
        boolean insertFlg = true;

        if (updateFlg__ == GSConstUser.IMPORT_MODE_UPDATE) {

            CmnPositionModel positionModel = posiDao.getPosInfo(posiModel.getPosName());
            if (positionModel != null) {
                positionSid = positionModel.getPosSid();
                positionModel.setPosSid(positionSid);
                positionModel.setPosCode(posiModel.getPosCode());
                positionModel.setPosBiko(posiModel.getPosBiko());
                positionModel.setPosSort(posiModel.getPosSort());

                //役職更新処理
                posiDao.updatePosition(positionModel);
                insertFlg = false;
            }

        }

        if (insertFlg) {

            //役職SIDを採番する。
            positionSid = (int) cntCon__.getSaibanNumber(SaibanModel.SBNSID_USER,
                    SaibanModel.SBNSID_SUB_POS,
                    sessionUser__);
            posiModel.setPosSid(positionSid);

            //役職登録処理
            posiDao.insertPos(posiModel);
        }

        posiList__.add(posiModel);
    }

    /**
     * <br>[機  能] 表示順を更新する。
     * <br>[解  説] 同じ表示順を回避する。
     * <br>[備  考]
     * @throws Exception csv読込時例外
     */
    private void __sortPosition() throws Exception {

        CmnPositionDao posiDao = new CmnPositionDao(con__);

        //役職一覧を取得する。
        List<CmnPositionModel> allPosiList = posiDao.getPosListSort(false);

        int sort = 1;
        for (CmnPositionModel model : allPosiList) {
            //表示順のみを更新する
            posiDao.updatePosSort(model.getPosSid(), sort);
            ++sort;
        }
    }
    /**
     * <p>updateFlg を取得します。
     * @return updateFlg
     */
    public int getUpdateFlg() {
        return updateFlg__;
    }
    /**
     * <p>updateFlg をセットします。
     * @param updateFlg updateFlg
     */
    public void setUpdateFlg(int updateFlg) {
        updateFlg__ = updateFlg;
    }

    /**
     * <p>cntCon を取得します。
     * @return cntCon
     */
    public MlCountMtController getCntCon() {
        return cntCon__;
    }

    /**
     * <p>cntCon をセットします。
     * @param cntCon cntCon
     */
    public void setCntCon(MlCountMtController cntCon) {
        cntCon__ = cntCon;
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }

    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <p>posiList を取得します。
     * @return posiList
     */
    public ArrayList<CmnPositionModel> getPosiList() {
        return posiList__;
    }

    /**
     * <p>grpmList をセットします。
     * @param posiList posoList
     */
    public void setPosiList(ArrayList<CmnPositionModel> posiList) {
        posiList__ = posiList;
    }

    /**
     * <p>sessionUser を取得します。
     * @return sessionUser
     */
    public int getSessionUser() {
        return sessionUser__;
    }

    /**
     * <p>sessionUser をセットします。
     * @param sessionUser sessionUser
     */
    public void setSessionUser(int sessionUser) {
        sessionUser__ = sessionUser;
    }

    /**
     * <p>sysUd を取得します。
     * @return sysUd
     */
    public UDate getSysUd() {
        return sysUd__;
    }

    /**
     * <p>sysUd をセットします。
     * @param sysUd sysUd
     */
    public void setSysUd(UDate sysUd) {
        sysUd__ = sysUd;
    }

    /**
     * <p>mode を取得します。
     * @return mode
     */
    public int getMode() {
        return mode__;
    }

    /**
     * <p>mode をセットします。
     * @param mode mode
     */
    public void setMode(int mode) {
        mode__ = mode;
    }
}