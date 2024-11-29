package jp.groupsession.v2.bbs.pdf;

import java.util.List;

import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] 掲示板投稿一覧情報をPDFへ出力する情報を格納するModelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsListPdfModel {

    /** フォーラムSID */
    private int bfiSid__ = 0;
    /** フォーラム名 */
    private String bfiName__ = null;
    /** スレッドSID */
    private int btiSid__ = 0;
    /** スレッド名 */
    private String btiTitle__  = null;
    /** 投稿内容 */
    private List<BulletinDspModel> writeList__ = null;
    /** 投稿者画像情報 */
    private List<CmnBinfModel> cbMdl__ = null;
   /**  出力PDFファイル名 */
    private String fileName__ = null;
    /**  出力PDFファイル名(save) */
    private String saveFileName__ = null;
    /** 重要度 */
    private int bfiThreImportance__ = 0;

    /**
     * @return bfiName
     */
    public String getBfiName() {
        return bfiName__;
    }
    /**
     * @param bfiName セットする bfiName
     */
    public void setBfiName(String bfiName) {
        bfiName__ = bfiName;
    }
    /**
     * @return btiTitle
     */
    public String getBtiTitle() {
        return btiTitle__;
    }
    /**
     * @param btiTitle セットする btiTitle
     */
    public void setBtiTitle(String btiTitle) {
        btiTitle__ = btiTitle;
    }
    /**
     * @return writeList
     */
    public List<BulletinDspModel> getWriteList() {
        return writeList__;
    }
    /**
     * @param writeList セットする writeList
     */
    public void setWriteList(List<BulletinDspModel> writeList) {
        writeList__ = writeList;
    }
    /**
     * @return cbMdl
     */
    public List<CmnBinfModel> getCbMdl() {
        return cbMdl__;
    }
    /**
     * @param cbMdl セットする cbMdl
     */
    public void setCbMdl(List<CmnBinfModel> cbMdl) {
        cbMdl__ = cbMdl;
    }
    /**
     * @return fileName
     */
    public String getFileName() {
        return fileName__;
    }
    /**
     * @param fileName セットする fileName
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }
    /**
     * <p>saveFileName を取得します。
     * @return saveFileName
     */
    public String getSaveFileName() {
        return saveFileName__;
    }
    /**
     * <p>saveFileName をセットします。
     * @param saveFileName saveFileName
     */
    public void setSaveFileName(String saveFileName) {
        saveFileName__ = saveFileName;
    }
    /**
     * <p>bfiSid を取得します。
     * @return bfiSid
     */
    public int getBfiSid() {
        return bfiSid__;
    }
    /**
     * <p>bfiSid をセットします。
     * @param bfiSid bfiSid
     */
    public void setBfiSid(int bfiSid) {
        bfiSid__ = bfiSid;
    }
    /**
     * <p>btiSid を取得します。
     * @return btiSid
     */
    public int getBtiSid() {
        return btiSid__;
    }
    /**
     * <p>btiSid をセットします。
     * @param btiSid btiSid
     */
    public void setBtiSid(int btiSid) {
        btiSid__ = btiSid;
    }
    /**
     * <p>bfiThreImportance を取得します。
     * @return bfiThreImportance
     */
    public int getBfiThreImportance() {
        return bfiThreImportance__;
    }
    /**
     * <p>bfiThreImportance をセットします。
     * @param bfiThreImportance bfiThreImportance
     */
    public void setBfiThreImportance(int bfiThreImportance) {
        bfiThreImportance__ = bfiThreImportance;
    }
}