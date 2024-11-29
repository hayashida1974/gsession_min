package jp.groupsession.v2.rng.pdf;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;



/**
 * <br>[機  能] 稟議確認画面のPDF出力用Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngTanPdfModel {

    /** ファイル名 */
    private String fileName__ = null;
    /** セーブ用ファイル名 */
    private String saveFileName__ = null;

    /** 状態 */
    private String pdfStatus__ = null;
    /** タイトル */
    private String pdfTitle__ = null;
    /** 申請者 */
    private String pdfApprUser__ = null;
    /** 作成日 */
    private String pdfMakeDate__ = null;
    /** 申請ID */
    private String rngId__;
    /** 稟議テンプレートSID */
    private int pdfRtpSid__ = 0;
    /** 稟議テンプレート情報 */
    private List<RngTanPdfTemplateModel> pdfTmpList__ = null;
    /** 添付情報 */
    private List<CmnBinfModel> pdfTmpFileList__ = null;
    /** 経路情報 */
    private List<RngTanPdfKeiroModel> pdfKeiroList__ = null;

    /**
     * <p>fileName を取得します。
     * @return fileName
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#fileName__
     */
    public String getFileName() {
        return fileName__;
    }
    /**
     * <p>fileName をセットします。
     * @param fileName fileName
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#fileName__
     */
    public void setFileName(String fileName) {
        fileName__ = fileName;
    }
    /**
     * <p>saveFileName を取得します。
     * @return saveFileName
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#saveFileName__
     */
    public String getSaveFileName() {
        return saveFileName__;
    }
    /**
     * <p>saveFileName をセットします。
     * @param saveFileName saveFileName
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#saveFileName__
     */
    public void setSaveFileName(String saveFileName) {
        saveFileName__ = saveFileName;
    }
    /**
     * <p>pdfStatus を取得します。
     * @return pdfStatus
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfStatus__
     */
    public String getPdfStatus() {
        return pdfStatus__;
    }
    /**
     * <p>pdfStatus をセットします。
     * @param pdfStatus pdfStatus
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfStatus__
     */
    public void setPdfStatus(String pdfStatus) {
        pdfStatus__ = pdfStatus;
    }
    /**
     * <p>pdfTitle を取得します。
     * @return pdfTitle
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfTitle__
     */
    public String getPdfTitle() {
        return pdfTitle__;
    }
    /**
     * <p>pdfTitle をセットします。
     * @param pdfTitle pdfTitle
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfTitle__
     */
    public void setPdfTitle(String pdfTitle) {
        pdfTitle__ = pdfTitle;
    }
    /**
     * <p>pdfApprUser を取得します。
     * @return pdfApprUser
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfApprUser__
     */
    public String getPdfApprUser() {
        return pdfApprUser__;
    }
    /**
     * <p>pdfApprUser をセットします。
     * @param pdfApprUser pdfApprUser
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfApprUser__
     */
    public void setPdfApprUser(String pdfApprUser) {
        pdfApprUser__ = pdfApprUser;
    }
    /**
     * <p>pdfMakeDate を取得します。
     * @return pdfMakeDate
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfMakeDate__
     */
    public String getPdfMakeDate() {
        return pdfMakeDate__;
    }
    /**
     * <p>pdfMakeDate をセットします。
     * @param pdfMakeDate pdfMakeDate
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfMakeDate__
     */
    public void setPdfMakeDate(String pdfMakeDate) {
        pdfMakeDate__ = pdfMakeDate;
    }
    /**
     * <p>rngId を取得します。
     * @return rngId
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#rngId__
     */
    public String getRngId() {
        return rngId__;
    }
    /**
     * <p>rngId をセットします。
     * @param rngId rngId
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#rngId__
     */
    public void setRngId(String rngId) {
        rngId__ = rngId;
    }
    /**
     * <p>pdfRtpSid を取得します。
     * @return pdfRtpSid
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfRtpSid__
     */
    public int getPdfRtpSid() {
        return pdfRtpSid__;
    }
    /**
     * <p>pdfRtpSid をセットします。
     * @param pdfRtpSid pdfRtpSid
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfRtpSid__
     */
    public void setPdfRtpSid(int pdfRtpSid) {
        pdfRtpSid__ = pdfRtpSid;
    }
    /**
     * <p>pdfTmpList を取得します。
     * @return pdfTmpList
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfTmpList__
     */
    public List<RngTanPdfTemplateModel> getPdfTmpList() {
        return pdfTmpList__;
    }
    /**
     * <p>pdfTmpList をセットします。
     * @param pdfTmpList pdfTmpList
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfTmpList__
     */
    public void setPdfTmpList(List<RngTanPdfTemplateModel> pdfTmpList) {
        pdfTmpList__ = pdfTmpList;
    }
    /**
     * <p>pdfTmpFileList を取得します。
     * @return pdfTmpFileList
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfTmpFileList__
     */
    public List<CmnBinfModel> getPdfTmpFileList() {
        return pdfTmpFileList__;
    }
    /**
     * <p>pdfTmpFileList をセットします。
     * @param pdfTmpFileList pdfTmpFileList
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfTmpFileList__
     */
    public void setPdfTmpFileList(List<CmnBinfModel> pdfTmpFileList) {
        pdfTmpFileList__ = pdfTmpFileList;
    }
    /**
     * <p>pdfKeiroList を取得します。
     * @return pdfKeiroList
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfKeiroList__
     */
    public List<RngTanPdfKeiroModel> getPdfKeiroList() {
        return pdfKeiroList__;
    }
    /**
     * <p>pdfKeiroList をセットします。
     * @param pdfKeiroList pdfKeiroList
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfModel#pdfKeiroList__
     */
    public void setPdfKeiroList(List<RngTanPdfKeiroModel> pdfKeiroList) {
        pdfKeiroList__ = pdfKeiroList;
    }
}