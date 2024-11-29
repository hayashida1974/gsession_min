package jp.groupsession.v2.cmn.model;

import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] ファイル管理情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FileEnhancingModel extends AbstractModel {

    /** バイナリーSID */
    private Long binSid__ = new Long(0);
    /** ファイル名 */
    private String fileName__;
    /** ファイル名(テンポラリディレクトリ保存名) */
    private String saveFileName__;
    /** ファイル容量 */
    private long atattiSize__;
    /** 新規・既存区分 1:新規 2:既存ファイル 3:既存ファイル更新 */
    private int updateKbn__ = 0;
    /** オブジェクト名 */
    private String splitObjName__;
    /** ファイルパス */
    private String binFilePath__;
    /** ディレクトリSID */
    private int pdrSid__;
    /** 登録者 */
    private int pdrAuid__;
    /** 登録日時 */
    private UDate pdrAdate__;
    /** 処理区分 0:何も無し 1:新規 2:更新 3:削除 */
    private int procKbn__ = 0;

    /**
     * <p>procKbn を取得します。
     * @return procKbn
     */
    public int getProcKbn() {
        return procKbn__;
    }
    /**
     * <p>procKbn をセットします。
     * @param procKbn procKbn
     */
    public void setProcKbn(int procKbn) {
        procKbn__ = procKbn;
    }
    /**
     * <p>pdrSid を取得します。
     * @return pdrSid
     */
    public int getPdrSid() {
        return pdrSid__;
    }
    /**
     * <p>pdrSid をセットします。
     * @param pdrSid pdrSid
     */
    public void setPdrSid(int pdrSid) {
        pdrSid__ = pdrSid;
    }
    /**
     * <p>pdrAdate を取得します。
     * @return pdrAdate
     */
    public UDate getPdrAdate() {
        return pdrAdate__;
    }
    /**
     * <p>pdrAdate をセットします。
     * @param pdrAdate pdrAdate
     */
    public void setPdrAdate(UDate pdrAdate) {
        pdrAdate__ = pdrAdate;
    }
    /**
     * <p>pdrAuid を取得します。
     * @return pdrAuid
     */
    public int getPdrAuid() {
        return pdrAuid__;
    }
    /**
     * <p>pdrAuid をセットします。
     * @param pdrAuid pdrAuid
     */
    public void setPdrAuid(int pdrAuid) {
        pdrAuid__ = pdrAuid;
    }
    /**
     * <p>binFilePath を取得します。
     * @return binFilePath
     */
    public String getBinFilePath() {
        return binFilePath__;
    }
    /**
     * <p>binFilePath をセットします。
     * @param binFilePath binFilePath
     */
    public void setBinFilePath(String binFilePath) {
        binFilePath__ = binFilePath;
    }
    /**
     * <p>splitObjName を取得します。
     * @return splitObjName
     */
    public String getSplitObjName() {
        return splitObjName__;
    }
    /**
     * <p>splitObjName をセットします。
     * @param splitObjName splitObjName
     */
    public void setSplitObjName(String splitObjName) {
        splitObjName__ = splitObjName;
    }
    /**
     * <p>updateKbn を取得します。
     * @return updateKbn
     */
    public int getUpdateKbn() {
        return updateKbn__;
    }
    /**
     * <p>updateKbn をセットします。
     * @param updateKbn updateKbn
     */
    public void setUpdateKbn(int updateKbn) {
        updateKbn__ = updateKbn;
    }
    /**
     * @return fileName を戻します。
     */
    public String getFileName() {
        return fileName__;
    }
    /**
     * @param fileName 設定する fileName。
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }
    /**
     * @return saveFileName を戻します。
     */
    public String getSaveFileName() {
        return saveFileName__;
    }
    /**
     * @param saveFileName 設定する saveFileName。
     */
    public void setSaveFileName(String saveFileName) {
        saveFileName__ = saveFileName;
    }
    /**
     * <p>atattiSize を取得します。
     * @return atattiSize
     */
    public long getAtattiSize() {
        return atattiSize__;
    }
    /**
     * <p>atattiSize をセットします。
     * @param atattiSize atattiSize
     */
    public void setAtattiSize(long atattiSize) {
        atattiSize__ = atattiSize;
    }
    /**
     * <p>binSid を取得します。
     * @return binSid
     */
    public Long getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(Long binSid) {
        binSid__ = binSid;
    }
}