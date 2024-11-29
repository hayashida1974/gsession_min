package jp.groupsession.v2.cmn;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.ErrorBinfModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.RssInfomModel;
import jp.groupsession.v2.cmn.model.base.WmlMailFileModel;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;

/**
 * <br>[機  能] バイナリファイルを扱う操作を行う抽象クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface ITempFileUtil {

    /**
     * <br>[機  能] 添付ファイル保存先区分を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @return fileSaveKbn ファイル保存先区分 0:ファイルシステム 1:H2 2:PostgreSQL
     */
    public int getTempFileHozonKbn();

    /**
     * <br>[機  能] 添付ファイル保存先PATHを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return fileSavePath ファイル保存先
     */
    public String getTempFilePath(RequestModel reqMdl);

    /**
     * <br>[機  能] 添付ファイル保存先PATHを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param domain ドメイン
     * @return fileSavePath ファイル保存先
     */
    public String getTempFilePath(String domain);

    /**
     * <br>[機  能] 添付ファイルデータのフィールド(Objectを格納)の読み込みを行う
     * <br>[解  説]
     * <br>[備  考] 取得できない場合エラーログがwarnレベルで記録されます。
     * @param rs ResultSet
     * @param dataFieldName フィールド名（ファイルデータ）
     * @param tempDir TEMPDIR
     * @param fileName ファイル名
     * @param fileSize ファイルサイズ
     * @param domain ドメイン
     * @return ファイル ファイル保存先がファイルシステムの場合はNullを返す。
     * @throws IOException 添付ファイル操作時例外
     */
    public File readTempFileDataField(
            ResultSet rs, String dataFieldName, String tempDir, String fileName, long fileSize,
            String domain)
    throws IOException;


    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーSIDをListで返す
     * <br>[解  説] ファイル本体の保存先を振り分ける
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
        UDate now) throws TempFileException;

    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーSIDをListで返す
     * <br>[解  説] ファイル本体の保存先を振り分ける
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
        UDate now) throws TempFileException;


    /**
     * <br>[機  能] 指定した添付ファイルを登録し、登録時のバイナリーSIDを返す
     * <br>[解  説] ファイル本体の保存先を振り分ける
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
        String fileName) throws TempFileException;

    /**
     * <br>[機  能] 指定した添付ファイルを更新し、登録時のバイナリーSIDを返す
     * <br>[解  説] ファイル本体の保存先を振り分ける
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
        MlCountMtController cntCon) throws TempFileException;

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
        ) throws TempFileException;


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
    public CmnBinfModel getBinInfo(Connection con, Long binSid, String domain)
                                                              throws TempFileException;


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
    public List<CmnBinfModel> getBinInfo(Connection con, String[] binSids, String domain)
                                                                         throws TempFileException;

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
                                                              throws TempFileException;


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
                                                                         throws TempFileException;

    /**
     * [機  能] ダウンロードするファイルを取得する。<br>
     * [解  説]<br>
     * [備  考]<br>
     * @param cbMdl 添付ファイルモデル
     * @param appRootPath アプリケーションルートパス
     * @return file ダウンロードファイル
     */
    public File getDownloadFile(CmnBinfModel cbMdl, String appRootPath);

    /**
     * [機  能] ダウンロードするファイルを取得する。<br>
     * [解  説]<br>
     * [備  考] ウェブメールで使用する。<br>
     * @param wtfMdl 添付ファイルモデル
     * @param appRootPath アプリケーションルートパス
     * @return file ダウンロードファイル
     */
    public File getDownloadFileForWebmail(WmlTempfileModel wtfMdl, String appRootPath);


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
    throws IOException, IOToolsException;

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
    throws IOException, IOToolsException;

    /**
     * <br>[機  能] バイナリデータを元にテンポラリディレクトリ内に添付ファイルを作成する
     * <br>[解  説] オブジェクトファイル生成しない
     * <br>[備  考]
     * @param binData バイナリデータ
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @return filePath 添付ファイルパス
     * @throws IOException 添付ファイルの作成に失敗
     * @throws IOToolsException 添付ファイルの作成に失敗
     */
    public String saveTempFile(CmnBinfModel binData, String appRoot, String tempDir)
    throws IOException, IOToolsException;


    /**
     * <br>[機  能] 添付ファイルの実体と添付ファイル情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cbMdl CmnBinfModel
     * @param appRootPath アプリケーションのルートパス
     * @param con コネクション
     * @throws SQLException SQL例外処理
     */
    public void deleteFileComp(CmnBinfModel cbMdl, String appRootPath, Connection con
            ) throws SQLException;


    /**
     * <br>[機  能] 添付ファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param cbMdl CmnBinfModel
     * @param appRootPath アプリケーションのルートパス
     * @throws IOToolsException ファイル操作時例外
     */
    public void deleteFile(CmnBinfModel cbMdl, String appRootPath) throws IOToolsException;

    /**
     * <br>[機  能] 添付ファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param binSids バイナリーSID(複数)
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteBinInf(Connection con, String[] binSids) throws SQLException;

    /**
     * <br>[機  能] 論理削除済み添付ファイルを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rootPath アプリケーションのルートパス
     * @return 更新件数
     * @throws SQLException SQL実行時例外
     */
    public int deleteAllLogicalDeletedBinInf(Connection con, String rootPath) throws SQLException;
    
    /**
     * <br>[機  能] 削除済みRSSフィードデータを削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rssSid RSSSID
     * @throws Exception 実行時例外
     */
    public void deleteAllLogicalDeletedRssFeedData(Connection con, int rssSid) 
            throws Exception;

    /**
     * <br>[機  能] 添付ファイルを全て登録し、登録時のバイナリーSIDをListで返す
     * <br>[解  説] ファイル本体の保存先を振り分ける
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param fileDataList ファイルデータリスト
     * @param mailNum メール番号
     * @return 登録したバイナリーSIDのリスト
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public List<Long> insertBinInfoForWebmail(
        Connection con,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now,
        List<WmlMailFileModel> fileDataList,
        long mailNum) throws TempFileException;

    /**
     * <br>[機  能] 指定したバイナリSIDのバイナリ情報を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wmdMailnum メッセージ番号
     * @param wtfSid バイナリSID
     * @param domain ドメイン
     * @return バイナリ情報
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public WmlTempfileModel getBinInfoForWebmail(Connection con, long wmdMailnum, long wtfSid,
                                                String domain) throws TempFileException;

    /**
     * <br>[機  能] バイナリデータを元にテンポラリディレクトリ内に添付ファイルを作成する
     * <br>[解  説] オブジェクトファイル生成する
     * <br>[備  考] ウェブメールで使用する。
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
    throws IOException, IOToolsException;

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
    throws SQLException, IOToolsException;

    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録する。
     * <br>[解  説] ファイル本体の保存先を振り分ける
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
    public List<String> insertBinInfoForFilekanri(
        Connection con,
        String tempDir,
        String appRootPath,
        MlCountMtController cntCon,
        int userSid,
        UDate now) throws TempFileException;

    /**
     * <br>[機  能] 指定した添付ファイルを登録し、登録時のバイナリーSIDを返す
     * <br>[解  説] ファイル本体をファイルシステムに保存する。
     * <br>[備  考] ファイル管理で使用する。
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param userSid ログインユーザSID
     * @param now システム日付
     * @param binSid ファイルSID
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
        long binSid,
        String fileName,
        String domain) throws TempFileException;



    /**
     * <br>[機  能] テンポラリディレクトリパスにある添付ファイルを全て登録し、
     *              登録時のバイナリーモデルをListで返す
     * <br>[解  説] ファイル本体の保存先を振り分ける
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
        UDate now) throws TempFileException;

    /**
     * Util終了時処理
     * @throws TempFileException 終了処理に失敗
     */
    public void utilDestroy() throws TempFileException;

    /**
     * <p>Update RSS_INFOM Data Bindding JavaBean
     * @param con コネクション
     * @param bean RSS_INFOM Data Bindding JavaBean
     * @param file RSSフィード情報ファイル
     * @param cntCon MlCountMtController
     * @throws Exception 実行例外
     */
    public void updateFeedData(Connection con, RssInfomModel bean, File file,
                                             MlCountMtController cntCon) throws Exception;

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
    throws Exception;

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
    throws Exception;

    /**
     * <br>[機  能] RSSフィード情報のデータサイズ合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return RSSフィード情報のデータサイズ合計
     * @throws SQLException 添付ファイルUtil内での例外
     */
    public long getTotalFeedDataSize(Connection con) throws SQLException;

    /**
     * <br>[機  能] 添付ファイルを全て登録に失敗した場合のエラーメッセージファイルを登録する
     * <br>[解  説] ファイル本体の保存先を振り分ける
     * <br>[備  考]
     * @param con コネクション
     * @param appRootPath アプリケーションのルートパス
     * @param cntCon MlCountMtController
     * @param tempFileModel 添付ファイル情報
     * @param binfMdl エラー添付情報
     * @throws TempFileException 添付ファイルUtil内での例外
     */
    public void insertBinInfoErrorForWebmail(Connection con,
            String appRootPath,
            MlCountMtController  cntCon,
            WmlTempfileModel tempFileModel,
            ErrorBinfModel binfMdl) throws TempFileException;
}