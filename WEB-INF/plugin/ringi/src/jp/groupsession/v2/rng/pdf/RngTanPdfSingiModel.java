package jp.groupsession.v2.rng.pdf;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] PDF出力用審議情報Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngTanPdfSingiModel {

    /** ユーザ名*/
    private String singiName__ = null;
    /** 代理人SID*/
    private int dairiSid__ = 0;
    /** 代理人名*/
    private String singiDairi__ = null;
    /** 後閲指示者名*/
    private String singiKoetu__ = null;
    /** コメント*/
    private String singiComment__ = null;
    /** 状態*/
    private int singiStatus__ = 0;
    /** 確認日*/
    private String singiDate__ = null;
    /** 確認時間*/
    private String singiTime__ = null;
    /** 役職*/
    private String singiPosition__ = null;
    /** 添付ファイル情報 */
    private List<CmnBinfModel> tmpFileList__ = null;
    /**
     * <p>singiName を取得します。
     * @return singiName
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiName__
     */
    public String getSingiName() {
        return singiName__;
    }
    /**
     * <p>singiName をセットします。
     * @param singiName singiName
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiName__
     */
    public void setSingiName(String singiName) {
        singiName__ = singiName;
    }
    /**
     * <p>dairiSid を取得します。
     * @return dairiSid
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#dairiSid__
     */
    public int getDairiSid() {
        return dairiSid__;
    }
    /**
     * <p>dairiSid をセットします。
     * @param dairiSid dairiSid
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#dairiSid__
     */
    public void setDairiSid(int dairiSid) {
        dairiSid__ = dairiSid;
    }
    /**
     * <p>singiDairi を取得します。
     * @return singiDairi
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiDairi__
     */
    public String getSingiDairi() {
        return singiDairi__;
    }
    /**
     * <p>singiDairi をセットします。
     * @param singiDairi singiDairi
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiDairi__
     */
    public void setSingiDairi(String singiDairi) {
        singiDairi__ = singiDairi;
    }
    /**
     * <p>singiKoetu を取得します。
     * @return singiKoetu
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiKoetu__
     */
    public String getSingiKoetu() {
        return singiKoetu__;
    }
    /**
     * <p>singiKoetu をセットします。
     * @param singiKoetu singiKoetu
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiKoetu__
     */
    public void setSingiKoetu(String singiKoetu) {
        singiKoetu__ = singiKoetu;
    }
    /**
     * <p>singiComment を取得します。
     * @return singiComment
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiComment__
     */
    public String getSingiComment() {
        return singiComment__;
    }
    /**
     * <p>singiComment をセットします。
     * @param singiComment singiComment
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiComment__
     */
    public void setSingiComment(String singiComment) {
        singiComment__ = singiComment;
    }
    /**
     * <p>singiStatus を取得します。
     * @return singiStatus
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiStatus__
     */
    public int getSingiStatus() {
        return singiStatus__;
    }
    /**
     * <p>singiStatus をセットします。
     * @param singiStatus singiStatus
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiStatus__
     */
    public void setSingiStatus(int singiStatus) {
        singiStatus__ = singiStatus;
    }
    /**
     * <p>singiDate を取得します。
     * @return singiDate
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiDate__
     */
    public String getSingiDate() {
        return singiDate__;
    }
    /**
     * <p>singiDate をセットします。
     * @param singiDate singiDate
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiDate__
     */
    public void setSingiDate(String singiDate) {
        singiDate__ = singiDate;
    }
    /**
     * <p>singiTime を取得します。
     * @return singiTime
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiTime__
     */
    public String getSingiTime() {
        return singiTime__;
    }
    /**
     * <p>singiTime をセットします。
     * @param singiTime singiTime
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiTime__
     */
    public void setSingiTime(String singiTime) {
        singiTime__ = singiTime;
    }
    /**
     * <p>singiPosition を取得します。
     * @return singiPosition
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiPosition__
     */
    public String getSingiPosition() {
        return singiPosition__;
    }
    /**
     * <p>singiPosition をセットします。
     * @param singiPosition singiPosition
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#singiPosition__
     */
    public void setSingiPosition(String singiPosition) {
        singiPosition__ = singiPosition;
    }
    /**
     * <p>tmpFileList を取得します。
     * @return tmpFileList
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#tmpFileList__
     */
    public List<CmnBinfModel> getTmpFileList() {
        return tmpFileList__;
    }
    /**
     * <p>tmpFileList をセットします。
     * @param tmpFileList tmpFileList
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfSingiModel#tmpFileList__
     */
    public void setTmpFileList(List<CmnBinfModel> tmpFileList) {
        tmpFileList__ = tmpFileList;
    }

}