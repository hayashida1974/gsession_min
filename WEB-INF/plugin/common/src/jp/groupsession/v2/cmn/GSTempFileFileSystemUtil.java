package jp.groupsession.v2.cmn;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110Biz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.WmlTempfileDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.ErrorBinfModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.RssInfomModel;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;

/**
 * <br>[機  能] 添付ファイルの登録、ダウンロードを行う。
 * <br>[解  説] 本クラスを使用することによって添付ファイルの保存先を振り分けることが出来ます。
 * <br>[備  考] ※本クラスは検証中であり、本稼動で使用することはお勧めしません。
 *
 * @author JTS
 */
public class GSTempFileFileSystemUtil implements ITempFileUtil {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(GSTempFileFileSystemUtil.class);
    /** テンポラリディレクトリ*/
    private String tempDir__ = null;
    /**
     * コンストラクタ
     */
    public GSTempFileFileSystemUtil() {
    }

    /**
     * 通常使用するコンストラクタ
     * @param tempDir テンポラリを指定
     */
    public GSTempFileFileSystemUtil(String tempDir) {
        tempDir__ = tempDir;
    }

    /**
     * <br>[機  能] ファイル保存先区分を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @return ファイル保存先区分
     */
    public int getTempFileHozonKbn() {
        return GSConst.FILE_HOZON_KBN_FILESYSTEM;
    }
    /**
     * <br>[機  能] ファイル保存先区分を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return ファイル保存先区分
     */
    public String getTempFilePath(RequestModel reqMdl) {
        return tempDir__;
    }
    /**
     * <br>[機  能] ファイル保存先区分を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return ファイル保存先区分
     */
    public String getTempFilePath(String domain) {
        return tempDir__;
    }
    /**
     * <br>[機  能] 添付ファイルデータのフィールド(Objectを格納)の読み込みを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param rs ResultSet
     * @param dataFieldName フィールド名（ファイルデータ）
     * @param tempDir テンプディレクトリ
     * @param fileFullPath ファイル名
     * @param fileSize ファイルサイズ
     * @param domain ドメイン
     * @return ファイル ファイル保存先がファイルシステムの場合はNullを返す。
     */
    public File readTempFileDataField(
            ResultSet rs, String dataFieldName,
            String tempDir,  String fileFullPath, long fileSize,
            String domain) {

        return null;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーSIDをListで返す
     * <br>[解  説] ファイル本体をファイルシステムに保存する。
     * <br>[備  考]
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<String> insertBinInfo(
        Connection con,
        String tempDir,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now) throws TempFileException {

        CommonBiz biz = new CommonBiz();
        CmnBinfDao cbDao = new CmnBinfDao(con);

        List<String> binList = new ArrayList<String>();

        try {
            //テンポラリディレクトリにあるファイル名称を取得
            List<String>fileList = IOTools.getFileNames(tempDir);

            if (fileList != null) {

                log__.debug("ファイルの数×２(オブジェクトと本体) = " + fileList.size());

                for (int i = 0; i < fileList.size(); i++) {

                    //ファイル名を取得
                    String fileName = fileList.get(i);
                    if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                        continue;
                    }

                    //オブジェクトファイルを取得
                    ObjectFile objFile = new ObjectFile(tempDir, fileName);
                    Object fObj = objFile.load();
                    if (fObj == null) {
                        continue;
                    }

                    Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                    log__.debug("ファイル名 = " + fMdl.getFileName());
                    log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());

                    Long binSid = fMdl.getBinSid();
                    if (binSid < 1) {

                        log__.debug("ファイル登録");
                        //バイナリーSID採番
                        binSid = cntCon.getSaibanNumber(GSConst.SBNSID_BIN,
                                                                   GSConst.SBNSID_SUB_BIN,
                                                                   userSid);
                        //添付ファイル保存用パス(DB登録用)
                        String savePath = biz.getSavePathForDb(now, binSid);

                        //テンポラリファイルのフルパス
                        String tempFullPath = tempDir + "/" + fMdl.getSaveFileName();
                        tempFullPath = IOTools.replaceFileSep(tempFullPath);

                        //添付ファイル保存用パス(フルパス)
                        String fileSavePath = biz.getSaveFullPath(now, binSid, appRootPath);

                        //ファイルの有効性チェック(ない場合に作成)
                        IOTools.isFileCheck(
                                biz.getSaveDirPath(now, appRootPath), String.valueOf(binSid), true);

                        //添付ファイルを保存
                        IOTools.copyBinFile(tempFullPath, fileSavePath);

                        //バイナリー情報を登録する
                        CmnBinfModel cbMdl = new CmnBinfModel();
                        cbMdl.setBinSid(binSid);
                        cbMdl.setBinFileName(fMdl.getFileName());
                        cbMdl.setBinFilePath(savePath);
                        cbMdl.setBinAduser(userSid);
                        cbMdl.setBinAddate(now);
                        cbMdl.setBinUpuser(userSid);
                        cbMdl.setBinUpdate(now);
                        cbMdl.setBinJkbn(GSConst.JTKBN_TOROKU);
                        cbDao.insertBinInfo(cbMdl, fileSavePath);
                    }

                    //バイナリーSIDをリストに追加
                    binList.add(String.valueOf(binSid));
                }
            }

        } catch (Exception e) {
            throw new TempFileException(e);
        }
        return binList;
    }

    /**
     * <br>[機  能] 指定した添付ファイルを登録し、登録時のバイナリーSIDを返す
     * <br>[解  説] ファイル本体をファイルシステムに保存する。
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param filePath ファイルパス
     * @param fileName ファイル名
     * @return 登録したバイナリーSID
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public Long insertBinInfo(
        Connection con,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now,
        String filePath,
        String fileName) throws TempFileException {

        CommonBiz biz = new CommonBiz();
        CmnBinfDao cbDao = new CmnBinfDao(con);

        Long binSid = Long.valueOf(0);
        try {

            File file = new File(filePath);

            if (file.exists()) {

                log__.debug("ファイル登録");
                //バイナリーSID採番
                binSid = cntCon.getSaibanNumber(GSConst.SBNSID_BIN,
                                                               GSConst.SBNSID_SUB_BIN,
                                                               userSid);
                //添付ファイル保存用パス(DB登録用)
                String savePath = biz.getSavePathForDb(now, binSid);

                //添付ファイル保存用パス(フルパス)
                String fileSavePath = biz.getSaveFullPath(now, binSid, appRootPath);

                //ファイルの有効性チェック(ない場合に作成)
                IOTools.isFileCheck(
                        biz.getSaveDirPath(now, appRootPath), String.valueOf(binSid), true);

                //添付ファイルを保存
                IOTools.copyBinFile(filePath, fileSavePath);

                //バイナリー情報を登録する
                CmnBinfModel cbMdl = new CmnBinfModel();
                cbMdl.setBinSid(binSid);
                cbMdl.setBinFileName(fileName);
                cbMdl.setBinFilePath(savePath);
                cbMdl.setBinAduser(userSid);
                cbMdl.setBinAddate(now);
                cbMdl.setBinUpuser(userSid);
                cbMdl.setBinUpdate(now);
                cbMdl.setBinJkbn(GSConst.JTKBN_TOROKU);
                cbDao.insertBinInfo(cbMdl, fileSavePath);

            }

        } catch (Exception e) {
            throw new TempFileException(e);
        }
        return binSid;
    }

    /**
     * <br>[機  能] 指定した添付ファイルを登録し、登録時のバイナリーSIDを返す
     * <br>[解  説] ファイル本体をファイルシステムに保存する。
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param filePath ファイルパス
     * @param binSid バイナリーSID
     * @param fileName ファイル名
     * @param cntCon MlCountMtController
     * @return 更新件数
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public int updateBinInfo(
        Connection con,
        String appRootPath,
        int userSid,
        UDate now,
        String filePath,
        Long binSid,
        String fileName,
        MlCountMtController cntCon) throws TempFileException {

        CommonBiz biz = new CommonBiz();
        CmnBinfDao cbDao = new CmnBinfDao(con);

        int count = 0;
        try {

            File file = new File(filePath);

            if (file.exists()) {

                //ファイル名を取得

                log__.debug("ファイル更新");
                //添付ファイル保存用パス(DB登録用)
                String savePath = biz.getSavePathForDb(now, binSid);

                //添付ファイル保存用パス(フルパス)
                String fileSavePath = biz.getSaveFullPath(now, binSid, appRootPath);

                //ファイルの有効性チェック(ない場合に作成)
                IOTools.isFileCheck(
                        biz.getSaveDirPath(now, appRootPath), String.valueOf(binSid), true);

                //添付ファイルを保存
                IOTools.copyBinFile(filePath, fileSavePath);

                //バイナリー情報を更新する
                CmnBinfModel cbMdl = new CmnBinfModel();
                cbMdl.setBinSid(binSid);
                cbMdl.setBinFileName(fileName);
                cbMdl.setBinFilePath(savePath);
                cbMdl.setBinUpuser(userSid);
                cbMdl.setBinUpdate(now);
                count = cbDao.updateDirBin(cbMdl, fileSavePath);

            }

        } catch (Exception e) {
            throw new TempFileException(e);
        }
        return count;
    }

    /**
     * <br>[機  能] 指定したバイナリSIDのファイルをコピーする。
     * <br>[解  説]
     * <br>[備  考] ファイル管理で使用する。
     * @param appRoot アプリケーションのルートパス
     * @param binSid バイナリSID
     * @param usrSid ユーザSID
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param domain ドメイン
     * @return newBinSid 採番バイナリSID
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public Long copyFile(
        String appRoot,
        Long binSid,
        int usrSid,
        Connection con,
        MlCountMtController cntCon,
        String domain
        ) throws TempFileException {

        CommonBiz cmnbiz = new CommonBiz();
        CmnBinfDao cbDao = new CmnBinfDao(con);
        UDate now = new UDate();
        Long newBinSid = Long.valueOf(-1);

        try {
            //バイナリ情報を取得する。
            CmnBinfModel cbMdl = cbDao.getBinInfo(binSid);

            if (cbMdl == null) {
                return newBinSid;
            }
            //バイナリーSID採番
            newBinSid = cntCon.getSaibanNumber(GSConst.SBNSID_BIN,
                                                           GSConst.SBNSID_SUB_BIN,
                                                           usrSid);

            //添付ファイル保存用パス(DB登録用)
            String savePath = cmnbiz.getSavePathForDb(now, newBinSid);

            //コピー元ファイルパス(フルパス)
            String filePath = cmnbiz.getSaveFullPathForFileKanri(appRoot, cbMdl.getBinFilePath());

            //コピー先パス(フルパス)
            String fileSavePath = cmnbiz.getSaveFullPathForFileKanri(now, newBinSid, appRoot);

            log__.debug("filePath = " + filePath);
            log__.debug("fileSavePath = " + fileSavePath);

            //ファイルの有効性チェック(ない場合に作成)
            IOTools.isFileCheck(
                    cmnbiz.getSaveDirPathForFileKanri(
                            now, appRoot), String.valueOf(binSid), true);

            //添付ファイルを保存
            IOTools.copyBinFile(filePath, fileSavePath);

            //バイナリー情報を登録する
            CmnBinfModel newCbMdl = new CmnBinfModel();
            newCbMdl.setBinSid(newBinSid);
            newCbMdl.setBinFileName(cbMdl.getBinFileName());
            newCbMdl.setBinFileSize(cbMdl.getBinFileSize());
            newCbMdl.setBinFilePath(savePath);
            newCbMdl.setBinAduser(usrSid);
            newCbMdl.setBinAddate(now);
            newCbMdl.setBinUpuser(usrSid);
            newCbMdl.setBinUpdate(now);
            newCbMdl.setBinJkbn(GSConst.JTKBN_TOROKU);
            newCbMdl.setBinFilekbn(GSConst.FILEKBN_FILE);
            cbDao.insertBinInfo(newCbMdl, fileSavePath);

        } catch (SQLException e) {
            throw new TempFileException(e);
        } catch (IOToolsException e) {
            throw new TempFileException(e);
        } catch (IOException e) {
            throw new TempFileException(e);
        }

        return newBinSid;
    }

    /**
     * <br>[機  能] 指定したバイナリSIDのバイナリ情報リストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSids バイナリSIDリスト
     * @param domain ドメイン
     * @return バイナリ情報
     * @throws TempFileException 添付ファイル操作時例外
     */
    public List<CmnBinfModel> getBinInfo(Connection con, String[] binSids, String domain)
    throws TempFileException {

        CmnBinfDao binDao = new CmnBinfDao(con);
        List<CmnBinfModel> binMdlList = null;

        try {

            binMdlList = binDao.select(binSids);

        } catch (SQLException e) {
            throw new TempFileException(e);
        }
        return binMdlList;
    }

    /**
     * <br>[機  能] 添付ファイル情報を取得する。
     * <br>[解  説] ファイル本体の保存先を振り分ける
     * <br>[備  考]
     * @param con コネクション
     * @param binSid バイナリSID
     * @param domain ドメイン
     * @return バイナリ情報
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public CmnBinfModel getBinInfo(Connection con, Long binSid, String domain)
                                                             throws TempFileException {

        CmnBinfDao cmnDao = new CmnBinfDao(con);
        return cmnDao.getBinInfo(binSid);
    }

    /**
     * <br>[機  能] 指定したバイナリSIDのバイナリ情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSid バイナリSID
     * @param domain ドメイン
     * @return バイナリ情報
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public CmnBinfModel getBinInfoToDomain(Connection con, Long binSid, String domain)
                                                              throws TempFileException {
        return getBinInfo(con, binSid, null);
    }


    /**
     * <br>[機  能] 指定したバイナリSIDのバイナリ情報リストを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSids バイナリSIDリスト
     * @param domain ドメイン
     * @return バイナリ情報
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<CmnBinfModel> getBinInfoToDomain(Connection con, String[] binSids, String domain)
                                                                         throws TempFileException {
        return getBinInfo(con, binSids, null);
    }

    /**
     * [機  能] ダウンロードするファイルを取得する。<br>
     * [解  説]<br>
     * [備  考]<br>
     * @param cbMdl 添付ファイルモデル
     * @param appRootPath アプリケーションルートパス
     * @return file ダウンロードファイル
     */
    public File getDownloadFile(CmnBinfModel cbMdl, String appRootPath) {

        //添付ファイル保存用のパスを取得する(フルパス)
        CommonBiz cmnBiz = new CommonBiz();
        String filePath = "";
        if (cbMdl.getBinFilekbn() == GSConst.FILEKBN_COMMON) {
            filePath = cmnBiz.getSaveFullPath(appRootPath, cbMdl.getBinFilePath());
        } else if (cbMdl.getBinFilekbn() == GSConst.FILEKBN_FILE) {
            filePath = cmnBiz.getSaveFullPathForFileKanri(appRootPath, cbMdl.getBinFilePath());
        }
        File file = new File(filePath);

        return file;
    }

    /**
     * <br>[機  能] バイナリデータを元にテンポラリディレクトリ内に添付ファイルを作成する
     * <br>[解  説] オブジェクトファイル生成する
     * <br>[備  考]
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param binData バイナリデータ
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param fileNum ファイルの連番
     * @return filePath 添付ファイルパス
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     */
    public String saveTempFile(String dateStr, CmnBinfModel binData,
                            String appRoot, String tempDir, int fileNum)
    throws IOException, IOToolsException {

        CommonBiz cmnBiz = new CommonBiz();
        String filePath = null;

        //添付ファイル(本体)のパスを取得
        File saveFilePath = Cmn110Biz.getSaveFilePath(tempDir, dateStr, fileNum);

        //添付ファイル保存用のパスを取得する(フルパス)
        if (binData.getBinFilekbn() == GSConst.FILEKBN_COMMON) {
            filePath = cmnBiz.getSaveFullPath(appRoot, binData.getBinFilePath());
        } else if (binData.getBinFilekbn() == GSConst.FILEKBN_FILE) {
            filePath = cmnBiz.getSaveFullPathForFileKanri(appRoot, binData.getBinFilePath());
        } else {
            return null;
        }

        //ファイルの有効性チェック(ない場合に作成)
        File checkFile = new File(filePath);
        if (!checkFile.exists()) {
            return null;
        }

        //ファイルの有効性チェック(ない場合に作成)
        IOTools.isFileCheck(tempDir, saveFilePath.getName(), true);

        //添付ファイルを保存
        IOTools.copyBinFile(filePath, saveFilePath.getPath());

        //オブジェクトファイルを設定
        File objFilePath = Cmn110Biz.getObjFilePath(tempDir, dateStr, fileNum);
        Cmn110FileModel fileMdl = new Cmn110FileModel();
        fileMdl.setFileName(binData.getBinFileName());
        fileMdl.setSaveFileName(saveFilePath.getName());

        ObjectFile objFile = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
        objFile.save(fileMdl);

        return filePath;
    }

    /**
     * <br>[機  能] バイナリデータを元にテンポラリディレクトリ内に添付ファイルを作成する
     * <br>[解  説] 単一ファイルの場合（オブジェクトファイル生成しない）
     * <br>[備  考]
     * @param binData バイナリデータ
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @return filePath 添付ファイルパス
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     */
    public String saveTempFile(CmnBinfModel binData, String appRoot, String tempDir)
    throws IOException, IOToolsException {

        CommonBiz cmnBiz = new CommonBiz();

        //添付ファイル名
        String fileName = binData.getBinFileName();
        //添付ファイル保存用パス(フルパス)
        String filePath = null;

        //添付ファイル保存用のパスを取得する(フルパス)
        if (binData.getBinFilekbn() == GSConst.FILEKBN_COMMON) {
            filePath = cmnBiz.getSaveFullPath(appRoot, binData.getBinFilePath());
        } else if (binData.getBinFilekbn() == GSConst.FILEKBN_FILE) {
            filePath = cmnBiz.getSaveFullPathForFileKanri(appRoot, binData.getBinFilePath());
        } else {
            return null;
        }

        File file = new File(filePath);
        if (!file.exists()) {
            return null;
        }

        //ファイルの有効性チェック(ない場合に作成)
        IOTools.isFileCheck(tempDir, fileName, true);

        //添付ファイルを保存
        IOTools.copyBinFile(filePath, tempDir + fileName);

         return filePath;
    }

    /**
     * <br>[機  能] バイナリデータを元にテンポラリディレクトリ内に添付ファイルを作成する
     * <br>[解  説] オブジェクトファイル生成する
     * <br>[備  考]
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param binData バイナリデータ
     * @param fileMdl 添付ファイル情報モデル
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param fileNum ファイルの連番
     * @return filePath 添付ファイルパス
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     */
    public String saveTempFile(String dateStr, CmnBinfModel binData, Cmn110FileModel fileMdl,
                            String appRoot, String tempDir, int fileNum)
    throws IOException, IOToolsException {

        CommonBiz cmnBiz = new CommonBiz();
        String filePath = null;

        //添付ファイル(本体)のパスを取得
        File saveFilePath = Cmn110Biz.getSaveFilePath(tempDir, dateStr, fileNum);

        //添付ファイル保存用のパスを取得する(フルパス)
        if (binData.getBinFilekbn() == GSConst.FILEKBN_COMMON) {
            filePath = cmnBiz.getSaveFullPath(appRoot, binData.getBinFilePath());
        } else if (binData.getBinFilekbn() == GSConst.FILEKBN_FILE) {
            filePath = cmnBiz.getSaveFullPathForFileKanri(appRoot, binData.getBinFilePath());
        } else {
            return null;
        }

        //ファイルの有効性チェック(ない場合に作成)
        File checkFile = new File(filePath);
        if (!checkFile.exists()) {
            return null;
        }

        //ファイルの有効性チェック(ない場合に作成)
        IOTools.isFileCheck(tempDir, saveFilePath.getName(), true);

        //添付ファイルを保存
        IOTools.copyBinFile(filePath, saveFilePath.getPath());

        //オブジェクトファイルを設定
        File objFilePath = Cmn110Biz.getObjFilePath(tempDir, dateStr, fileNum);

        String[] objVal = objFilePath.getName().split(GSConstCommon.ENDSTR_OBJFILE);
        fileMdl.setSplitObjName(objVal[0]);
        fileMdl.setSaveFileName(saveFilePath.getName());

        ObjectFile objFile = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
        objFile.save(fileMdl);

        return filePath;
    }

    /**
     * <br>[機  能] ファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cbMdl CmnBinfModel
     * @param appRootPath アプリケーションのルートパス
     * @throws IOToolsException ファイル操作時例外
     */
    public void deleteFile(CmnBinfModel cbMdl, String appRootPath) throws IOToolsException {

        CommonBiz cmnBiz = new CommonBiz();

        //添付ファイル保存用のパスを取得する(フルパス)
        String savePath = "";
        if (cbMdl.getBinFilekbn() == GSConst.FILEKBN_FILE) {
            //ファイル管理用のバイナリー情報
            savePath = cmnBiz.getSaveFullPathForFileKanri(appRootPath, cbMdl.getBinFilePath());
        } else {
            //通常のバイナリー情報
            savePath = cmnBiz.getSaveFullPath(appRootPath, cbMdl.getBinFilePath());
        }

        log__.debug("削除するファイル = " + savePath);

        //ファイルを削除
        File saveFile = new File(savePath);
        if (saveFile.exists()) {
            IOTools.deleteFile(savePath);
        }
    }

    /**
     * <br>[機  能] 添付ファイル情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSids バイナリーSID(複数)
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteBinInf(Connection con, String[] binSids) throws SQLException {
        CmnBinfDao cbDao = new CmnBinfDao(con);
        return cbDao.deleteBinInf(binSids);
    }

    /**
     * <br>[機  能] 論理削除済み添付ファイルと添付ファイルの実体を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cbMdl CmnBinfModel
     * @param appRootPath アプリケーションのルートパス
     * @param con コネクション
     * @throws SQLException SQL例外処理
     */
    public void deleteFileComp(CmnBinfModel cbMdl, String appRootPath, Connection con
            ) throws SQLException {
        CommonBiz cmnBiz = new CommonBiz();

        //実体ファイルを削除
        //添付ファイル保存用のパスを取得する(フルパス)
        String savePath = "";
        if (cbMdl.getBinFilekbn() == GSConst.FILEKBN_FILE) {
            //ファイル管理用のバイナリー情報
            savePath = cmnBiz.getSaveFullPathForFileKanri(appRootPath, cbMdl.getBinFilePath());
        } else {
            //通常のバイナリー情報
            savePath = cmnBiz.getSaveFullPath(appRootPath, cbMdl.getBinFilePath());
        }

        log__.debug("削除するファイル = " + savePath);

        //ファイルを削除
        File saveFile = new File(savePath);
        if (saveFile.exists()) {
            IOTools.deleteFile(savePath);
        }

        CmnBinfDao cbDao = new CmnBinfDao(con);
        //添付ファイル情報を削除
        cbDao.deleteBinInf(cbMdl.getBinSid());
    }

    /**
     * <br>[機  能] 削除済みRSSフィードデータを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rssSid RSSSID
     * @throws Exception 実行時例外
     */
    public void deleteAllLogicalDeletedRssFeedData(Connection con, int rssSid)
            throws Exception {
    }

    /**
     * <br>[機  能] 論理削除済み添付ファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rootPath アプリケーションルートパス
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteAllLogicalDeletedBinInf(Connection con, String rootPath)
            throws SQLException {

        int count = 0;
        //論理削除済みのバイナリー情報を取得する
        CmnBinfDao cbDao = new CmnBinfDao(con);
        List < CmnBinfModel > cbList = cbDao.getDeleteFile();

        log__.debug("ファイルを削除");
        for (int i = 0; i < cbList.size(); i++) {
            count++;
            CmnBinfModel cbMdl = cbList.get(i);
            deleteFileComp(cbMdl, rootPath, con);
            con.commit();
        }
        return count;
    }

    /**
     * <br>[機  能] 指定した添付ファイルを登録し、登録時のバイナリーSIDを返す
     * <br>[解  説] ファイル本体をファイルシステムに保存する。
     * <br>[備  考] ウェブメールの添付ファイル用
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param fileDataList ファイルデータリスト
     * @param mailNum メール番号
     * @return 登録したバイナリーSID
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<Long> insertBinInfoForWebmail(
        Connection con,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now,
        List<WmlMailFileModel> fileDataList,
        long mailNum) throws TempFileException {

        List<Long> binList = new ArrayList<Long>();

        CommonBiz cmnBiz = new CommonBiz();
        WmlTempfileDao tempFileDao = new WmlTempfileDao(con);
        WmlTempfileModel tempFileModel = new WmlTempfileModel();

        try {

            tempFileModel.setWmdMailnum(mailNum);
            tempFileModel.setWtfAuid(userSid);
            tempFileModel.setWtfAdate(now);
            tempFileModel.setWtfEuid(userSid);
            tempFileModel.setWtfEdate(now);

            String fileExtension = null;
            for (WmlMailFileModel fileData : fileDataList) {

                //バイナリーSID採番
                long wtfSid = cntCon.getSaibanNumber(GSConstWebmail.SBNSID_WEBMAIL,
                                                   GSConstWebmail.SBNSID_SUB_BINARY,
                                                   userSid);

                //添付ファイル保存用パス(DB登録用)
                String savePath = cmnBiz.getSavePathForDb(now, wtfSid);

                //テンポラリファイルのフルパス
                String tempFullPath = IOTools.replaceFileSep(fileData.getFilePath());

                //添付ファイル保存用パス(フルパス)
                String fileSavePath = cmnBiz.getSaveFullPathForWebmail(now, wtfSid, appRootPath);

                //保存先ディレクトリの有効性チェック(ない場合に作成)
                IOTools.isDirCheck(
                        cmnBiz.getSaveDirPathForFileWebmail(now, appRootPath), true);

                //添付ファイルを保存
//                IOTools.copyBinFile(tempFullPath, fileSavePath);
                IOTools.moveBinFile(tempFullPath, fileSavePath);

                //バイナリー情報を登録する
                tempFileModel.setWtfSid(wtfSid);


                fileExtension = StringUtil.getExtension(fileData.getFileName());
                if (fileExtension != null
                && fileExtension.length() > GSConstWebmail.MAXLEN_WTF_FILE_EXTENSION) {
                    log__.warn("ファイル拡張子の桁数調整: " + fileExtension);
                    fileExtension
                        = fileExtension.substring(0, GSConstWebmail.MAXLEN_WTF_FILE_EXTENSION);
                }
                tempFileModel.setWtfFileExtension(fileExtension);

                String fileName = fileData.getFileName();
                if (fileExtension != null) {
                    fileName = fileData.getFileName().substring(
                                                   0, fileData.getFileName().lastIndexOf('.'));
                } else {
                    fileExtension = "";
                }

                if ((fileName.length() + fileExtension.length())
                                        > GSConstWebmail.MAXLEN_WTF_FILE_NAME) {
                    int overLength = (fileName.length() + fileExtension.length())
                                               - GSConstWebmail.MAXLEN_WTF_FILE_NAME;
                    fileName = fileName.substring(0, (fileName.length() - overLength));
                }

                tempFileModel.setWtfFileName(fileName + fileExtension);
                tempFileModel.setWtfFilePath(savePath);

                tempFileModel.setWtfFileSize((new File(fileSavePath)).length());
                tempFileModel.setWtfJkbn(GSConst.JTKBN_TOROKU);
                if (fileData.isHtmlMail()) {
                    tempFileModel.setWtfHtmlmail(GSConstWebmail.TEMPFILE_HTMLMAIL_HTML);
                    tempFileModel.setWtfCharset(Encoding.ISO_2022_JP);
                    if (!StringUtil.isNullZeroString(fileData.getContentType())) {
                        String charset = CommonBiz.getHeaderCharset(fileData.getContentType());
                        if (!StringUtil.isNullZeroString(charset)) {
                            tempFileModel.setWtfCharset(charset);
                        }
                    }
                } else {
                    tempFileModel.setWtfHtmlmail(GSConstWebmail.TEMPFILE_HTMLMAIL_NORMAL);
                    tempFileModel.setWtfCharset(null);
                }
                tempFileDao.insert(tempFileModel);

                //バイナリーSIDをリストに追加
                binList.add(wtfSid);
            }

            tempFileModel = null;
            tempFileDao = null;

        } catch (SQLException e) {
            throw new TempFileException(e);
        } catch (IOException e) {
            throw new TempFileException(e);
        } catch (IOToolsException e) {
            throw new TempFileException(e);
        }

        return binList;
    }
    @Override
    public void insertBinInfoErrorForWebmail(Connection con, String appRootPath,
            MlCountMtController cntCon, WmlTempfileModel tempFileModel,
            ErrorBinfModel binfMdl) throws TempFileException {
        try {
            CommonBiz cmnBiz = new CommonBiz();
            //添付ファイル保存用パス(DB登録用)
            String savePath = cmnBiz.getSavePathForDb(tempFileModel.getWtfAdate(),
                    tempFileModel.getWtfSid());


            //添付ファイル保存用パス(フルパス)
            String fileSavePath = cmnBiz.getSaveFullPathForWebmail(tempFileModel.getWtfAdate(),
                    tempFileModel.getWtfSid(), appRootPath);

            //保存先ディレクトリの有効性チェック(ない場合に作成)
            IOTools.isDirCheck(
                    cmnBiz.getSaveDirPathForFileWebmail(tempFileModel.getWtfAdate(),
                          String.valueOf(tempFileModel.getWtfSid())),
                    true);

            //添付ファイルを保存
    //        IOTools.copyBinFile(tempFullPath, fileSavePath);
            IOTools.writeText(fileSavePath, Encoding.UTF_8, binfMdl.getErrorMsgBody());

            //バイナリー情報を登録する
            tempFileModel.setWtfFilePath(savePath);

            WmlTempfileDao tempFileDao = new WmlTempfileDao(con);

            tempFileDao.insert(tempFileModel);

        } catch (SQLException | IOException | IOToolsException  e) {
            log__.error("添付ファイルの登録に失敗しました。", e);
            throw new TempFileException("添付ファイルの登録に失敗しました。", e);
        }

    }

    /**
     * <br>[機  能] 添付ファイル情報を取得する。
     * <br>[解  説] ファイル本体の保存先を振り分ける
     * <br>[備  考]
     * @param con コネクション
     * @param wmdMailnum メッセージ番号
     * @param wtfSid バイナリSID
     * @param domain ドメイン
     * @return バイナリ情報
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public WmlTempfileModel getBinInfoForWebmail(Connection con,
                                                 long wmdMailnum,
                                                 long wtfSid,
                                                 String domain)
                                                 throws TempFileException {

        WmlTempfileDao wtfDao = new WmlTempfileDao(con);
        WmlTempfileModel model = null;
        try {
            if (wmdMailnum > 0) {
                model = wtfDao.select(wmdMailnum, wtfSid);
            } else {
                model = wtfDao.select(wtfSid);
            }
        } catch (SQLException e) {
            throw new TempFileException(e);
        }

        return model;
    }

    /**
     * [機  能] ダウンロードするファイルを取得する。<br>
     * [解  説]<br>
     * [備  考]<br>ウェブメールで使用する。
     * @param wtfMdl 添付ファイルモデル
     * @param appRootPath アプリケーションルートパス
     * @return file ダウンロードファイル
     */
    public File getDownloadFileForWebmail(WmlTempfileModel wtfMdl, String appRootPath) {

        //添付ファイル保存用のパスを取得する(フルパス)
        CommonBiz cmnBiz = new CommonBiz();
        String filePath = cmnBiz.getSaveFullPathForWebmail(appRootPath, wtfMdl.getWtfFilePath());

        File file = new File(filePath);

        return file;
    }

    /**
     * <br>[機  能] バイナリデータを元にテンポラリディレクトリ内に添付ファイルを作成する
     * <br>[解  説] オブジェクトファイル生成する
     * <br>[備  考]
     * @param dateStr 日付文字列(YYYYMMDD)
     * @param binData バイナリデータ
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param fileNum ファイルの連番
     * @return filePath 添付ファイルパス
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     */
    public String saveTempFileForWebmail(String dateStr, WmlTempfileModel binData,
                            String appRoot, String tempDir, int fileNum)
    throws IOException, IOToolsException {

        CommonBiz cmnBiz = new CommonBiz();
        String filePath = null;

        //添付ファイル(本体)のパスを取得
        File saveFilePath = Cmn110Biz.getSaveFilePath(tempDir, dateStr, fileNum);

        //添付ファイル保存用のパスを取得する(フルパス)
        filePath = cmnBiz.getSaveFullPathForWebmail(appRoot, binData.getWtfFilePath());

        //ファイルの有効性チェック(ない場合に作成)
        File checkFile = new File(filePath);
        if (!checkFile.exists()) {
            return null;
        }

        //ファイルの有効性チェック(ない場合に作成)
        IOTools.isFileCheck(tempDir, saveFilePath.getName(), true);

        //添付ファイルを保存
        long time = System.currentTimeMillis();
        IOTools.copyBinFile(filePath, saveFilePath.getPath());
        log__.debug("WebMail添付ファイルコピー時間" + (System.currentTimeMillis() - time));

        //オブジェクトファイルを設定
        File objFilePath = Cmn110Biz.getObjFilePath(tempDir, dateStr, fileNum);
        Cmn110FileModel fileMdl = new Cmn110FileModel();
        fileMdl.setFileName(binData.getWtfFileName());
        fileMdl.setSaveFileName(saveFilePath.getName());

        time = System.currentTimeMillis();
        ObjectFile objFile = new ObjectFile(objFilePath.getParent(), objFilePath.getName());
        objFile.save(fileMdl);
        log__.debug("WebMail添付ファイルコピー時間(オブジェクト)" + (System.currentTimeMillis() - time));

        return filePath;
    }

    /**
     * <br>[機  能] WEBメール 添付ファイル情報を削除する。
     * <br>[解  説] 添付ファイルの実体を削除する。
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param binSidList 削除対象のバイナリSID一覧
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException ファイル操作時例外
     */
    public void deleteFileForWebmail(Connection con, String appRootPath,
                                    List<Long> binSidList)
    throws SQLException, IOToolsException {

        CommonBiz cmnBiz = new CommonBiz();

        WmlTempfileDao tempfileDao = new WmlTempfileDao(con);
        List<String> filePathList = tempfileDao.getTempFilePathList(binSidList);
        tempfileDao = null;

        String savePath = null;
        for (String filePath : filePathList) {
            try {
                //添付ファイル保存用のパスを取得する(フルパス)
                savePath = cmnBiz.getSaveFullPathForWebmail(appRootPath, filePath);
                log__.debug("削除するファイル = " + savePath);

                //ファイルを削除
                File saveFile = new File(savePath);
                if (saveFile.exists()) {
                    IOTools.deleteFile(savePath);
                }
            } catch (Throwable t) {
                log__.error("メール添付ファイル(実体)の削除に失敗", t);
            }
        }
        savePath = null;

    }

    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーSIDをListで返す
     * <br>[解  説] ファイル本体をファイルシステムに保存する。
     * <br>[備  考] ファイル管理で使用
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<String> insertBinInfoForFilekanri(
        Connection con,
        String tempDir,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now) throws TempFileException {

        CommonBiz biz = new CommonBiz();
        CmnBinfDao cbDao = new CmnBinfDao(con);

        List<String> binList = new ArrayList<String>();

        try {
            //テンポラリディレクトリにあるファイル名称を取得
            List<String>fileList = IOTools.getFileNames(tempDir);

            if (fileList != null) {

                log__.debug("ファイルの数×２(オブジェクトと本体) = " + fileList.size());

                for (int i = 0; i < fileList.size(); i++) {

                    //ファイル名を取得
                    String fileName = fileList.get(i);
                    if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                        continue;
                    }

                    //オブジェクトファイルを取得
                    ObjectFile objFile = new ObjectFile(tempDir, fileName);
                    Object fObj = objFile.load();
                    if (fObj == null) {
                        continue;
                    }

                    Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                    log__.debug("ファイル名 = " + fMdl.getFileName());
                    log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());

                    Long binSid = fMdl.getBinSid();
                    if (binSid < 1) {

                        log__.debug("ファイル登録");
                        //バイナリーSID採番
                        binSid = cntCon.getSaibanNumber(GSConst.SBNSID_BIN,
                                                                   GSConst.SBNSID_SUB_BIN,
                                                                   userSid);
                        //添付ファイル保存用パス(DB登録用)
                        String savePath = biz.getSavePathForDb(now, binSid);

                        //テンポラリファイルのフルパス
                        String tempFullPath = tempDir + "/" + fMdl.getSaveFileName();
                        tempFullPath = IOTools.replaceFileSep(tempFullPath);

                        //添付ファイル保存用パス(フルパス)
                        String fileSavePath
                            = biz.getSaveFullPathForFileKanri(now, binSid, appRootPath);

                        //ファイルの有効性チェック(ない場合に作成)
                        IOTools.isFileCheck(biz.getSaveDirPathForFileKanri(now, appRootPath),
                                            String.valueOf(binSid), true);

                        //添付ファイルを保存
                        IOTools.copyBinFile(tempFullPath, fileSavePath);

                        //バイナリー情報を登録する
                        CmnBinfModel cbMdl = new CmnBinfModel();
                        cbMdl.setBinSid(binSid);
                        cbMdl.setBinFileName(fMdl.getFileName());
                        cbMdl.setBinFilePath(savePath);
                        cbMdl.setBinAduser(userSid);
                        cbMdl.setBinAddate(now);
                        cbMdl.setBinUpuser(userSid);
                        cbMdl.setBinUpdate(now);
                        cbMdl.setBinFilekbn(GSConst.FILEKBN_FILE);
                        cbMdl.setBinJkbn(GSConst.JTKBN_TOROKU);
                        cbDao.insertBinInfo(cbMdl, fileSavePath);

                    }

                    //バイナリーSIDをリストに追加
                    binList.add(String.valueOf(binSid));
                }
            }

        } catch (SQLException e) {
            throw new TempFileException(e);
        } catch (IOException e) {
            throw new TempFileException(e);
        } catch (IOToolsException e) {
            throw new TempFileException(e);
        }

        return binList;
    }

    /**
     * <br>[機  能] 指定した添付ファイルを登録し、登録時のバイナリーSIDを返す
     * <br>[解  説] ファイル本体をファイルシステムに保存する。
     * <br>[備  考] ファイル管理で使用する。
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param copyBinSid コピー元のファイルSID
     * @param fileName ファイル名
     * @param domain ドメイン
     * @return 登録したバイナリーSID
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public Long insertBinInfoForFilekanri(
        Connection con,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now,
        long copyBinSid,
        String fileName,
        String domain) throws TempFileException {

        CommonBiz biz = new CommonBiz();
        CmnBinfDao cbDao = new CmnBinfDao(con);

        Long binSid = Long.valueOf(0);
        try {
            CmnBinfModel binfMdl = cbDao.getBinInfo(copyBinSid);
            String copyFilePath =
                    biz.getFileRootPathForFileKanri(appRootPath) + binfMdl.getBinFilePath();
            File file = new File(copyFilePath);
            if (file.exists()) {

                log__.debug("ファイル登録");
                //バイナリーSID採番
                binSid = cntCon.getSaibanNumber(GSConst.SBNSID_BIN,
                                                               GSConst.SBNSID_SUB_BIN,
                                                               userSid);
                //添付ファイル保存用パス(DB登録用)
                String savePath = biz.getSavePathForDb(now, binSid);

                //添付ファイル保存用パス(フルパス)
                String fileSavePath
                    = biz.getSaveFullPathForFileKanri(now, binSid, appRootPath);

                //ファイルの有効性チェック(ない場合に作成)
                IOTools.isFileCheck(biz.getSaveDirPathForFileKanri(now, appRootPath),
                        String.valueOf(binSid), true);

                //添付ファイルを保存
                String filePath =
                        biz.getFileRootPathForFileKanri(appRootPath) + binfMdl.getBinFilePath();
                log__.error("filePath:" + filePath);
                IOTools.copyBinFile(filePath, fileSavePath);

                //バイナリー情報を登録する
                CmnBinfModel cbMdl = new CmnBinfModel();
                cbMdl.setBinSid(binSid);
                cbMdl.setBinFileName(fileName);
                cbMdl.setBinFilePath(savePath);
                cbMdl.setBinAduser(userSid);
                cbMdl.setBinAddate(now);
                cbMdl.setBinUpuser(userSid);
                cbMdl.setBinUpdate(now);
                cbMdl.setBinFilekbn(GSConst.FILEKBN_FILE);
                cbMdl.setBinJkbn(GSConst.JTKBN_TOROKU);
                cbDao.insertBinInfo(cbMdl, fileSavePath);

            }

        } catch (SQLException e) {
            throw new TempFileException(e);
        } catch (IOException e) {
            throw new TempFileException(e);
        } catch (IOToolsException e) {
            throw new TempFileException(e);
        }

        return binSid;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーSIDをListで返す
     * <br>[解  説] ファイルシステムに保存する。
     * <br>[備  考]
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<String> insertSameBinInfo(
        Connection con,
        String tempDir,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now) throws TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfDao cbDao = new CmnBinfDao(con);

        List<String> binList = new ArrayList<String>();
        try {

            //テンポラリディレクトリにあるファイル名称を取得
            List<String> fileList = IOTools.getFileNames(tempDir);

            if (fileList != null) {

                log__.debug("ファイルの数×２(オブジェクトと本体) = " + fileList.size());

                for (int i = 0; i < fileList.size(); i++) {

                    //ファイル名を取得
                    String fileName = fileList.get(i);
                    if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                        continue;
                    }

                    //オブジェクトファイルを取得
                    ObjectFile objFile = new ObjectFile(tempDir, fileName);
                    Object fObj = objFile.load();
                    if (fObj == null) {
                        continue;
                    }

                    Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                    log__.debug("ファイル名 = " + fMdl.getFileName());
                    log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());

                    Long binSid =
                        cntCon.getSaibanNumber(GSConst.SBNSID_BIN,
                                                     GSConst.SBNSID_SUB_BIN,
                                                     userSid);
                    //添付ファイル保存用パス(DB登録用)
                    String savePath = cmnBiz.getSavePathForDb(now, binSid);

                    //テンポラリファイルのフルパス
                    String tempFullPath = tempDir + "/" + fMdl.getSaveFileName();
                    tempFullPath = IOTools.replaceFileSep(tempFullPath);

                    //添付ファイル保存用パス(フルパス)
                    String fileSavePath = cmnBiz.getSaveFullPath(now, binSid, appRootPath);

                    //ファイルの有効性チェック(ない場合に作成)
                    IOTools.isFileCheck(
                            cmnBiz.getSaveDirPath(now, appRootPath), String.valueOf(binSid), true);

                    //添付ファイルを保存
                    IOTools.copyBinFile(tempFullPath, fileSavePath);

                    //バイナリー情報を登録する
                    CmnBinfModel cbMdl = new CmnBinfModel();
                    cbMdl.setBinSid(binSid);
                    cbMdl.setBinFileName(fMdl.getFileName());
                    cbMdl.setBinFilePath(savePath);
                    cbMdl.setBinAduser(userSid);
                    cbMdl.setBinAddate(now);
                    cbMdl.setBinUpuser(userSid);
                    cbMdl.setBinUpdate(now);
                    cbMdl.setBinJkbn(GSConst.JTKBN_TOROKU);
                    cbDao.insertBinInfo(cbMdl, fileSavePath);

                    //バイナリーSIDをリストに追加
                    binList.add(String.valueOf(binSid));
                }
            }
        } catch (SQLException e) {
            throw new TempFileException(e);
        } catch (IOException e) {
            throw new TempFileException(e);
        } catch (IOToolsException e) {
            throw new TempFileException(e);
        }

        return binList;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーモデルをListで返す
     * <br>[解  説] ファイルシステムに保存する。
     * <br>[備  考] ファイル管理で使用する。
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<CmnBinfModel> insertSameBinInfoForFileKanri(
        Connection con,
        String tempDir,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now) throws TempFileException {

        CommonBiz cmnBiz = new CommonBiz();
        CmnBinfDao cbDao = new CmnBinfDao(con);

        List<CmnBinfModel> binList = new ArrayList<CmnBinfModel>();

        try {
            //テンポラリディレクトリにあるファイル名称を取得
            List<String>fileList = IOTools.getFileNames(tempDir);

            if (fileList != null) {

                log__.debug("ファイルの数×２(オブジェクトと本体) = " + fileList.size());
                CmnBinfModel cbMdl = null;
                for (int i = 0; i < fileList.size(); i++) {

                    //ファイル名を取得
                    String fileName = fileList.get(i);
                    if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                        continue;
                    }

                    //オブジェクトファイルを取得
                    ObjectFile objFile = new ObjectFile(tempDir, fileName);
                    Object fObj = objFile.load();
                    if (fObj == null) {
                        continue;
                    }

                    Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                    log__.debug("ファイル名 = " + fMdl.getFileName());
                    log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
                    log__.debug("ファイル登録");
                    //バイナリーSID採番
                    Long binSid = cntCon.getSaibanNumber(GSConst.SBNSID_BIN,
                                                                   GSConst.SBNSID_SUB_BIN,
                                                                   userSid);
                    //添付ファイル保存用パス(DB登録用)
                    String savePath = cmnBiz.getSavePathForDb(now, binSid);

                    //テンポラリファイルのフルパス
                    String tempFullPath = tempDir + "/" + fMdl.getSaveFileName();
                    tempFullPath = IOTools.replaceFileSep(tempFullPath);

                    //添付ファイル保存用パス(フルパス)
                    String fileSavePath
                            = cmnBiz.getSaveFullPathForFileKanri(now, binSid, appRootPath);

                    //ファイルの有効性チェック(ない場合に作成)
                    IOTools.isFileCheck(
                            cmnBiz.getSaveDirPathForFileKanri(
                                    now, appRootPath), String.valueOf(binSid), true);

                    //添付ファイルを保存
                    IOTools.copyBinFile(tempFullPath, fileSavePath);

                    //バイナリー情報を登録する
                    cbMdl = new CmnBinfModel();
                    cbMdl.setBinSid(binSid);
                    cbMdl.setBinFileName(fMdl.getFileName());
                    cbMdl.setBinFilePath(savePath);
                    cbMdl.setBinAduser(userSid);
                    cbMdl.setBinAddate(now);
                    cbMdl.setBinUpuser(userSid);
                    cbMdl.setBinUpdate(now);
                    cbMdl.setBinJkbn(GSConst.JTKBN_TOROKU);
                    cbMdl.setBinFilekbn(GSConst.FILEKBN_FILE);
                    cbDao.insertBinInfo(cbMdl, fileSavePath);

                    //バイナリーSIDをリストに追加
                    binList.add(cbMdl);
                }
            }
        } catch (SQLException e) {
            throw new TempFileException(e);
        } catch (IOException e) {
            throw new TempFileException(e);
        } catch (IOToolsException e) {
            throw new TempFileException(e);
        }
       return binList;
    }

    /**
     * finalizeメソッド
     * @throws Throwable 例外発生
     */
    protected void finalize() throws Throwable {
        super.finalize();
    }

    /**
     * Util終了時処理
     * @throws TempFileException 終了処理に失敗
     */
    public void utilDestroy() throws TempFileException {
        try {
            finalize();
        } catch (Throwable e) {
            throw new TempFileException("添付ファイル一時ファイルの削除に失敗しました。", e);
        }
    }

    /**
     * <p>Update RSS_INFOM Data Bindding JavaBean
     * @param con コネクション
     * @param bean RSS_INFOM Data Bindding JavaBean
     * @param file RSSフィード情報ファイル
     * @param cntCon MlCountMtController
     * @throws Exception 実行例外
     */
    public void updateFeedData(Connection con, RssInfomModel bean, File file,
                               MlCountMtController cntCon) throws Exception {

        PreparedStatement pstmt = null;

        FileInputStream fis = null;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" set ");
            sql.addSql("   RSM_FEEDDATA=?,");
            sql.addSql("   RSM_UPDATE_TIME=?,");
            sql.addSql("   RSM_EUID=?,");
            sql.addSql("   RSM_EDATE=?");
            sql.addSql(" where ");
            sql.addSql("   RSS_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());

            sql.addStrValue("bytea");
            sql.addDateValue(bean.getRsmUpdateTime());
            sql.addIntValue(bean.getRsmEuid());
            sql.addDateValue(bean.getRsmEdate());
            //where
            sql.addIntValue(bean.getRssSid());

            log__.info(sql.toLogString());

            fis = new FileInputStream(file);
            pstmt.setBinaryStream(1, fis, (int) file.length());
            pstmt.setTimestamp(2, JDBCUtil.getTimestamp(bean.getRsmUpdateTime()));
            pstmt.setInt(3, bean.getRsmEuid());
            pstmt.setTimestamp(4, JDBCUtil.getTimestamp(bean.getRsmEdate()));
            pstmt.setInt(5, bean.getRssSid());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);

            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * <br>[機  能] ロブファイルを結果セットからメモリ上に直接読み込む(RSS用)
     * <br>[解  説]
     * <br>[備  考] 取得できない場合エラーログがwarnレベルで記録されます。
     * @param rs ResultSet
     * @param dataFieldName フィールド名（ファイルデータ）
     * @return フィールドオブジェクト(java.lang.Object)
     * @throws Exception 添付ファイルUtil内での例外
     */
    public Object readLobObjectFieldInMem(
            ResultSet rs, String dataFieldName)
    throws Exception {
        Object value = null;
        ObjectInputStream ois = null;
        try {
            InputStream is = rs.getBinaryStream(dataFieldName);
            if (is != null) {
                ois = new ObjectInputStream(is);
                value = ois.readObject();
            }
        } catch (Exception e) {
            log__.warn("Byteaフィールドの読み込みに失敗", e);
            return value;
        } finally {
            if (ois != null) {
                ois.close();
            }
        }

        return value;
    }

    /**
     * <br>[機  能] フィード情報をクリアする
     * <br>[解  説] RSSフィード情報(RSM_FEEDDATA)を初期化する
     * <br>[備  考]
     * @param con コネクション
     * @param rsmUpdateTime RSSフィード更新時間
     * @param userSid 更新者
     * @param date 更新日時
     * @throws Exception 実行例外
     */
    public void clearFeedData(Connection con, UDate rsmUpdateTime, int userSid, UDate date)
    throws Exception {

        PreparedStatement pstmt = null;
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSS_INFOM");
            sql.addSql(" set ");
            sql.addSql("   RSM_FEEDDATA=null,");
            sql.addSql("   RSM_UPDATE_TIME=?,");
            sql.addSql("   RSM_EUID=?,");
            sql.addSql("   RSM_EDATE=?");

            sql.addDateValue(rsmUpdateTime);
            sql.addIntValue(userSid);
            sql.addDateValue(date);
            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] RSSフィード情報のデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return RSSフィード情報のデータサイズ合計
     * @throws SQLException 添付ファイルUtil内での例外
     */
    public long getTotalFeedDataSize(Connection con) throws SQLException {
        long totalFeedSize = 0;

        Statement stmt = null;
        ResultSet rs = null;
        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(RSM_FEEDDATA)) as TOTAL_FIELD_LENGTH");
            sql.addSql(" from ");
            sql.addSql("   RSS_INFOM");

            stmt = con.createStatement();
            log__.info(sql.toLogString());
            rs = stmt.executeQuery(sql.toSqlString());

            if (rs.next()) {
                totalFeedSize = rs.getLong("TOTAL_FIELD_LENGTH");
            }
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(stmt);
        }

        return totalFeedSize;
    }
}