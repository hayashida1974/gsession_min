package jp.groupsession.v2.man.man230;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnBackupConfModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.man.man002.Man002ParamModel;
import jp.groupsession.v2.man.man230.model.Man230ConnectionOptionModel;
import jp.groupsession.v2.man.man230.model.Man230GsDataConfModel;
import jp.groupsession.v2.man.man230.model.Man230GsMobileSuiteVarsionModel;
import jp.groupsession.v2.man.man230.model.Man230ServerNameModel;
import jp.groupsession.v2.man.man230.model.Man230WebMailModel;

/**
 * <br>[機  能] メイン 管理者設定 システム情報画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man230ParamModel extends Man002ParamModel {
    /** バージョン(DB) */
    private String man230GSVersionDB__ = null;
    /** OS */
    private String man230Os__ = null;
    /** CPUプロセッサ */
    private String man230CpuProcesser__ = null;
    /** CPUコアの数 */
    private String man230CpuCoreNum__ = null;
    /** サーバメモリ */
    private String man230MemSize__ = null;
    /** サーバディスク容量 */
    private List<List<String>> man230DiskInfo__ = null;
    /** サーバディスク容量 */
    private List<List<String>> man230dataFolderInfo__ = null;
    /** CPUのビット数 */
    private String man230JvmBitMode__ = null;
    /** J2EEコンテナ */
    private String man230J2ee__ = null;
    /** Java */
    private String man230Java__ = null;
    /** メモリ使用 */
    private String man230MemoryUse__ = null;
    /** メモリ使用割合 */
    private String man230MemoryUsePer__ = null;
    /** DB区分 */
    private String man230DbKbn__ = null;
    /** メモリ最大 */
    private String man230MemoryMax__ = null;
    /** 現在の空きディスク容量 */
    private String man230FreeDSpace__ = null;
    /** サポート期限有効フラグ */
    private String man230SupportLimitFlag__ = "-1";

    /** 開発用 */
    private String man230ConnectionCount__ = null;

    /** コネクションオプション */
    private Man230ConnectionOptionModel man230ConnectionOp__ = null;

    /** gsdata.conf */
    private Man230GsDataConfModel man230GsdataConf__ = null;

    /** バックアップ情報 */
    private CmnBackupConfModel man230BackUpConf__ = null;

    /** バッチ処理情報 */
    private CmnBatchJobModel man230CmnBatchJob__ = null;

    /** プラグイン情報 */
    private ArrayList<String> man230PluginList__ = null;

    /** テンポラリディレクトリパス */
    private String man230TempPath__ = null;

    /** GsMobileSuiteVarsionのデータ格納モデル */
    private Man230GsMobileSuiteVarsionModel man230GsMobSuiteVer__ = null;

    /** FIL_ALL_SEARCH_USE */
    private String man230FileSearch__ = null;

    /** MAIL_PORT_NUMBER */
    private String man230MailServer__ = null;

    /** PORTLET_MAXLENGTH */
    private String man230Portal__ = null;

    /** RSV_PRINT_USE */
    private String man230RsvPrintUse__ = null;

    /** servername.conf */
    private Man230ServerNameModel man230ServerName__ = null;

    /** SSO_KEYWORD */
    private String man230SsoKeyWord__ = null;

    /** webmail.conf */
    private Man230WebMailModel man230WebMailModel__ = null;

    /**
     * <p>man230MemoryUsePer を取得します。
     * @return man230MemoryUsePer
     */
    public String getMan230MemoryUsePer() {
        return man230MemoryUsePer__;
    }
    /**
     * <p>man230MemoryUsePer をセットします。
     * @param man230MemoryUsePer man230MemoryUsePer
     */
    public void setMan230MemoryUsePer(String man230MemoryUsePer) {
        man230MemoryUsePer__ = man230MemoryUsePer;
    }
    /**
     * <p>man230FreeDSpace を取得します。
     * @return man230FreeDSpace
     */
    public String getMan230FreeDSpace() {
        return man230FreeDSpace__;
    }
    /**
     * <p>man230FreeDSpace をセットします。
     * @param man230FreeDSpace man230FreeDSpace
     */
    public void setMan230FreeDSpace(String man230FreeDSpace) {
        man230FreeDSpace__ = man230FreeDSpace;
    }
    /**
     * <p>man230J2ee を取得します。
     * @return man230J2ee
     */
    public String getMan230J2ee() {
        return man230J2ee__;
    }
    /**
     * <p>man230J2ee をセットします。
     * @param man230J2ee man230J2ee
     */
    public void setMan230J2ee(String man230J2ee) {
        man230J2ee__ = man230J2ee;
    }
    /**
     * <p>man230Java を取得します。
     * @return man230Java
     */
    public String getMan230Java() {
        return man230Java__;
    }
    /**
     * <p>man230Java をセットします。
     * @param man230Java man230Java
     */
    public void setMan230Java(String man230Java) {
        man230Java__ = man230Java;
    }
    /**
     * <p>man230MemoryMax を取得します。
     * @return man230MemoryMax
     */
    public String getMan230MemoryMax() {
        return man230MemoryMax__;
    }
    /**
     * <p>man230MemoryMax をセットします。
     * @param man230MemoryMax man230MemoryMax
     */
    public void setMan230MemoryMax(String man230MemoryMax) {
        man230MemoryMax__ = man230MemoryMax;
    }
    /**
     * <p>man230MemoryUse を取得します。
     * @return man230MemoryUse
     */
    public String getMan230MemoryUse() {
        return man230MemoryUse__;
    }
    /**
     * <p>man230MemoryUse をセットします。
     * @param man230MemoryUse man230MemoryUse
     */
    public void setMan230MemoryUse(String man230MemoryUse) {
        man230MemoryUse__ = man230MemoryUse;
    }
    /**
     * <p>man230Os を取得します。
     * @return man230Os
     */
    public String getMan230Os() {
        return man230Os__;
    }
    /**
     * <p>man230Os をセットします。
     * @param man230Os man230Os
     */
    public void setMan230Os(String man230Os) {
        man230Os__ = man230Os;
    }
    /**
     * <p>man230CpuProcesser を取得します。
     * @return man230CpuProcesser
     * @see jp.groupsession.v2.man.man230.Man230ParamModel#man230CpuProcesser__
     */
    public String getMan230CpuProcesser() {
        return man230CpuProcesser__;
    }
    /**
     * <p>man230CpuProcesser をセットします。
     * @param man230CpuProcesser man230CpuProcesser
     * @see jp.groupsession.v2.man.man230.Man230ParamModel#man230CpuProcesser__
     */
    public void setMan230CpuProcesser(String man230CpuProcesser) {
        man230CpuProcesser__ = man230CpuProcesser;
    }
    /**
     * <p>man230MemSize を取得します。
     * @return man230MemSize
     * @see jp.groupsession.v2.man.man230.Man230ParamModel#man230MemSize__
     */
    public String getMan230MemSize() {
        return man230MemSize__;
    }
    /**
     * <p>man230MemSize をセットします。
     * @param man230MemSize man230MemSize
     * @see jp.groupsession.v2.man.man230.Man230ParamModel#man230MemSize__
     */
    public void setMan230MemSize(String man230MemSize) {
        man230MemSize__ = man230MemSize;
    }
    /**
     * <p>man230DiskInfo を取得します。
     * @return man230DiskInfo
     * @see jp.groupsession.v2.man.man230.Man230ParamModel#man230DiskInfo__
     */
    public List<List<String>> getMan230DiskInfo() {
        return man230DiskInfo__;
    }
    /**
     * <p>man230DiskInfo をセットします。
     * @param man230DiskInfo man230DiskInfo
     * @see jp.groupsession.v2.man.man230.Man230ParamModel#man230DiskInfo__
     */
    public void setMan230DiskInfo(List<List<String>> man230DiskInfo) {
        man230DiskInfo__ = man230DiskInfo;
    }
    /**
     * <p>man230dataFolderInfo を取得します。
     * @return man230dataFolderInfo
     * @see jp.groupsession.v2.man.man230.Man230Form#man230dataFolderInfo__
     */
    public List<List<String>> getMan230dataFolderInfo() {
        return man230dataFolderInfo__;
    }
    /**
     * <p>man230dataFolderInfo をセットします。
     * @param man230dataFolderInfo man230dataFolderInfo
     * @see jp.groupsession.v2.man.man230.Man230Form#man230dataFolderInfo__
     */
    public void setMan230dataFolderInfo(List<List<String>> man230dataFolderInfo) {
        man230dataFolderInfo__ = man230dataFolderInfo;
    }
    /**
     * <p>man230ConnectionCount を取得します。
     * @return man230ConnectionCount
     */
    public String getMan230ConnectionCount() {
        return man230ConnectionCount__;
    }
    /**
     * <p>man230ConnectionCount をセットします。
     * @param man230ConnectionCount man230ConnectionCount
     */
    public void setMan230ConnectionCount(String man230ConnectionCount) {
        man230ConnectionCount__ = man230ConnectionCount;
    }
    /**
     * <p>man230SupportLimitFlag を取得します。
     * @return man230SupportLimitFlag
     */
    public String getMan230SupportLimitFlag() {
        return man230SupportLimitFlag__;
    }
    /**
     * <p>man230SupportLimitFlag をセットします。
     * @param man230SupportLimitFlag man230SupportLimitFlag
     */
    public void setMan230SupportLimitFlag(String man230SupportLimitFlag) {
        man230SupportLimitFlag__ = man230SupportLimitFlag;
    }
    /**
     * <p>man230GSVersionDB を取得します。
     * @return man230GSVersionDB
     */
    public String getMan230GSVersionDB() {
        return man230GSVersionDB__;
    }
    /**
     * <p>man230GSVersionDB をセットします。
     * @param man230GSVersionDB man230GSVersionDB
     */
    public void setMan230GSVersionDB(String man230GSVersionDB) {
        man230GSVersionDB__ = man230GSVersionDB;
    }
    /**
     * <p>man230DbKbn を取得します。
     * @return man230DbKbn
     */
    public String getMan230DbKbn() {
        return man230DbKbn__;
    }
    /**
     * <p>man230DbKbn をセットします。
     * @param man230DbKbn man230DbKbn
     */
    public void setMan230DbKbn(String man230DbKbn) {
        man230DbKbn__ = man230DbKbn;
    }
    /**
     * <p>man230ConnectionOp を取得します。
     * @return man230ConnectionOp
     */
    public Man230ConnectionOptionModel getMan230ConnectionOp() {
        return man230ConnectionOp__;
    }
    /**
     * <p>man230ConnectionOp をセットします。
     * @param man230ConnectionOp man230ConnectionOp
     */
    public void setMan230ConnectionOp(Man230ConnectionOptionModel man230ConnectionOp) {
        man230ConnectionOp__ = man230ConnectionOp;
    }
    /**
     * <p>man230GsdataConf を取得します。
     * @return man230GsdataConf
     */
    public Man230GsDataConfModel getMan230GsdataConf() {
        return man230GsdataConf__;
    }
    /**
     * <p>man230GsdataConf をセットします。
     * @param man230GsdataConf man230GsdataConf
     */
    public void setMan230GsdataConf(Man230GsDataConfModel man230GsdataConf) {
        man230GsdataConf__ = man230GsdataConf;
    }
    /**
     * <p>man230BackUpConf を取得します。
     * @return man230BackUpConf
     */
    public CmnBackupConfModel getMan230BackUpConf() {
        return man230BackUpConf__;
    }
    /**
     * <p>man230BackUpConf をセットします。
     * @param man230BackUpConf man230BackUpConf
     */
    public void setMan230BackUpConf(CmnBackupConfModel man230BackUpConf) {
        man230BackUpConf__ = man230BackUpConf;
    }
    /**
     * <p>man230CmnBatchJob を取得します。
     * @return man230CmnBatchJob
     */
    public CmnBatchJobModel getMan230CmnBatchJob() {
        return man230CmnBatchJob__;
    }
    /**
     * <p>man230CmnBatchJob をセットします。
     * @param man230CmnBatchJob man230CmnBatchJob
     */
    public void setMan230CmnBatchJob(CmnBatchJobModel man230CmnBatchJob) {
        man230CmnBatchJob__ = man230CmnBatchJob;
    }
    /**
     * <p>man230PluginList を取得します。
     * @return man230PluginList
     */
    public ArrayList<String> getMan230PluginList() {
        return man230PluginList__;
    }
    /**
     * <p>man230PluginList をセットします。
     * @param man230PluginList man230PluginList
     */
    public void setMan230PluginList(ArrayList<String> man230PluginList) {
        man230PluginList__ = man230PluginList;
    }
    /**
     * <p>man230TempPath を取得します。
     * @return man230TempPath
     */
    public String getMan230TempPath() {
        return man230TempPath__;
    }
    /**
     * <p>man230TempPath をセットします。
     * @param man230TempPath man230TempPath
     */
    public void setMan230TempPath(String man230TempPath) {
        man230TempPath__ = man230TempPath;
    }
    /**
     * <p>man230GsMobSuiteVer を取得します。
     * @return man230GsMobSuiteVer
     */
    public Man230GsMobileSuiteVarsionModel getMan230GsMobSuiteVer() {
        return man230GsMobSuiteVer__;
    }
    /**
     * <p>man230GsMobSuiteVer をセットします。
     * @param man230GsMobSuiteVer man230GsMobSuiteVer
     */
    public void setMan230GsMobSuiteVer(Man230GsMobileSuiteVarsionModel man230GsMobSuiteVer) {
        man230GsMobSuiteVer__ = man230GsMobSuiteVer;
    }
    /**
     * <p>man230fileSearch を取得します。
     * @return man230fileSearch
     */
    public String getMan230FileSearch() {
        return man230FileSearch__;
    }
    /**
     * <p>man230fileSearch をセットします。
     * @param man230fileSearch man230fileSearch
     */
    public void setMan230FileSearch(String man230fileSearch) {
        man230FileSearch__ = man230fileSearch;
    }
    /**
     * <p>man230MailServer を取得します。
     * @return man230MailServer
     */
    public String getMan230MailServer() {
        return man230MailServer__;
    }
    /**
     * <p>man230MailServer をセットします。
     * @param man230MailServer man230MailServer
     */
    public void setMan230MailServer(String man230MailServer) {
        man230MailServer__ = man230MailServer;
    }
    /**
     * <p>man230Portal を取得します。
     * @return man230Portal
     */
    public String getMan230Portal() {
        return man230Portal__;
    }
    /**
     * <p>man230Portal をセットします。
     * @param man230Portal man230Portal
     */
    public void setMan230Portal(String man230Portal) {
        man230Portal__ = man230Portal;
    }
    /**
     * <p>man230RsvPrintUse を取得します。
     * @return man230RsvPrintUse
     */
    public String getMan230RsvPrintUse() {
        return man230RsvPrintUse__;
    }
    /**
     * <p>man230RsvPrintUse をセットします。
     * @param man230RsvPrintUse man230RsvPrintUse
     */
    public void setMan230RsvPrintUse(String man230RsvPrintUse) {
        man230RsvPrintUse__ = man230RsvPrintUse;
    }
    /**
     * <p>man230ServerName を取得します。
     * @return man230ServerName
     */
    public Man230ServerNameModel getMan230ServerName() {
        return man230ServerName__;
    }
    /**
     * <p>man230ServerName をセットします。
     * @param man230ServerName man230ServerName
     */
    public void setMan230ServerName(Man230ServerNameModel man230ServerName) {
        man230ServerName__ = man230ServerName;
    }
    /**
     * <p>man230SsoKeyWord を取得します。
     * @return man230SsoKeyWord
     */
    public String getMan230SsoKeyWord() {
        return man230SsoKeyWord__;
    }
    /**
     * <p>man230SsoKeyWord をセットします。
     * @param man230SsoKeyWord man230SsoKeyWord
     */
    public void setMan230SsoKeyWord(String man230SsoKeyWord) {
        man230SsoKeyWord__ = man230SsoKeyWord;
    }
    /**
     * <p>man230WebMailModel を取得します。
     * @return man230WebMailModel
     */
    public Man230WebMailModel getMan230WebMailModel() {
        return man230WebMailModel__;
    }
    /**
     * <p>man230WebMailModel をセットします。
     * @param man230WebMailModel man230WebMailModel
     */
    public void setMan230WebMailModel(Man230WebMailModel man230WebMailModel) {
        man230WebMailModel__ = man230WebMailModel;
    }
    /**
     * <p>man230CpuCoreNum を取得します。
     * @return man230CpuCoreNum
     */
    public String getMan230CpuCoreNum() {
        return man230CpuCoreNum__;
    }
    /**
     * <p>man230CpuCoreNum をセットします。
     * @param man230CpuCoreNum man230CpuCoreNum
     */
    public void setMan230CpuCoreNum(String man230CpuCoreNum) {
        man230CpuCoreNum__ = man230CpuCoreNum;
    }
    /**
     * <p>man230JvmBitMode を取得します。
     * @return man230JvmBitMode
     */
    public String getMan230JvmBitMode() {
        return man230JvmBitMode__;
    }
    /**
     * <p>man230JvmBitMode をセットします。
     * @param man230JvmBitMode man230JvmBitMode
     */
    public void setMan230JvmBitMode(String man230JvmBitMode) {
        man230JvmBitMode__ = man230JvmBitMode;
    }


}