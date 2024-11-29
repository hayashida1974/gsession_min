package jp.groupsession.v2.convert.convert240.model;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] プロジェクトのファイル情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PrjSimpleDirectoryModel extends AbstractModel {

    /** ディレクトリSID */
    private int pdrSid__;
    /** ディレクトリ区分 */
    private int pdrKbn__;
    /** ディレクトリ名称 */
    private String pdrName__;
    /** ディレクトリ階層 */
    private int pdrLevel__;
    /** バイナリSID */
    private int binSid__;
    /** バイナリファイル名 */
    private String binFileName__;
    /** バイナリファイルパス */
    private String binFilePath__;
    /** 拡張子 */
    private String pflExt__;
    /** ファイルサイズ */
    private long pflFileSize__;
    /** ファイルサイズ(表示) */
    private String pflFileSizeStr__;
    /** 更新日時 */
    private UDate pdrEdate__;
    /** 更新日時(表示) */
    private String pdrEdateStr__;
    /** 更新者 姓 */
    private String usiSei__;
    /** 更新者 姓 */
    private String usiMei__;
    /** 備考 */
    private String pdrBiko__;

    /**
     * <p>pdrBiko を取得します。
     * @return pdrBiko
     */
    public String getPdrBiko() {
        return pdrBiko__;
    }
    /**
     * <p>pdrBiko をセットします。
     * @param pdrBiko pdrBiko
     */
    public void setPdrBiko(String pdrBiko) {
        pdrBiko__ = pdrBiko;
    }
    /**
     * <p>pdrLevel を取得します。
     * @return pdrLevel
     */
    public int getPdrLevel() {
        return pdrLevel__;
    }
    /**
     * <p>pdrLevel をセットします。
     * @param pdrLevel pdrLevel
     */
    public void setPdrLevel(int pdrLevel) {
        pdrLevel__ = pdrLevel;
    }
    /**
     * <p>binFileName を取得します。
     * @return binFileName
     */
    public String getBinFileName() {
        return binFileName__;
    }
    /**
     * <p>binFileName をセットします。
     * @param binFileName binFileName
     */
    public void setBinFileName(String binFileName) {
        binFileName__ = binFileName;
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
     * <p>binSid を取得します。
     * @return binSid
     */
    public int getBinSid() {
        return binSid__;
    }
    /**
     * <p>binSid をセットします。
     * @param binSid binSid
     */
    public void setBinSid(int binSid) {
        binSid__ = binSid;
    }
    /**
     * <p>pdrEdate を取得します。
     * @return pdrEdate
     */
    public UDate getPdrEdate() {
        return pdrEdate__;
    }
    /**
     * <p>pdrEdate をセットします。
     * @param pdrEdate pdrEdate
     */
    public void setPdrEdate(UDate pdrEdate) {
        pdrEdate__ = pdrEdate;
    }
    /**
     * <p>pdrEdateStr を取得します。
     * @return pdrEdateStr
     */
    public String getPdrEdateStr() {
        return pdrEdateStr__;
    }
    /**
     * <p>pdrEdateStr をセットします。
     * @param pdrEdateStr pdrEdateStr
     */
    public void setPdrEdateStr(String pdrEdateStr) {
        pdrEdateStr__ = pdrEdateStr;
    }
    /**
     * <p>pdrKbn を取得します。
     * @return pdrKbn
     */
    public int getPdrKbn() {
        return pdrKbn__;
    }
    /**
     * <p>pdrKbn をセットします。
     * @param pdrKbn pdrKbn
     */
    public void setPdrKbn(int pdrKbn) {
        pdrKbn__ = pdrKbn;
    }
    /**
     * <p>pdrName を取得します。
     * @return pdrName
     */
    public String getPdrName() {
        return pdrName__;
    }
    /**
     * <p>pdrName をセットします。
     * @param pdrName pdrName
     */
    public void setPdrName(String pdrName) {
        pdrName__ = pdrName;
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
     * <p>pflExt を取得します。
     * @return pflExt
     */
    public String getPflExt() {
        return pflExt__;
    }
    /**
     * <p>pflExt をセットします。
     * @param pflExt pflExt
     */
    public void setPflExt(String pflExt) {
        pflExt__ = pflExt;
    }
    /**
     * <p>pflFileSize を取得します。
     * @return pflFileSize
     */
    public long getPflFileSize() {
        return pflFileSize__;
    }
    /**
     * <p>pflFileSize をセットします。
     * @param pflFileSize pflFileSize
     */
    public void setPflFileSize(long pflFileSize) {
        pflFileSize__ = pflFileSize;
    }
    /**
     * <p>pflFileSizeStr を取得します。
     * @return pflFileSizeStr
     */
    public String getPflFileSizeStr() {
        return pflFileSizeStr__;
    }
    /**
     * <p>pflFileSizeStr をセットします。
     * @param pflFileSizeStr pflFileSizeStr
     */
    public void setPflFileSizeStr(String pflFileSizeStr) {
        pflFileSizeStr__ = pflFileSizeStr;
    }
    /**
     * <p>usiMei を取得します。
     * @return usiMei
     */
    public String getUsiMei() {
        return usiMei__;
    }
    /**
     * <p>usiMei をセットします。
     * @param usiMei usiMei
     */
    public void setUsiMei(String usiMei) {
        usiMei__ = usiMei;
    }
    /**
     * <p>usiSei を取得します。
     * @return usiSei
     */
    public String getUsiSei() {
        return usiSei__;
    }
    /**
     * <p>usiSei をセットします。
     * @param usiSei usiSei
     */
    public void setUsiSei(String usiSei) {
        usiSei__ = usiSei;
    }
}