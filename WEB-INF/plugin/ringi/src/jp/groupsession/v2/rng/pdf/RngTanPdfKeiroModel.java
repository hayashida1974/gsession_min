package jp.groupsession.v2.rng.pdf;

import java.util.List;

/**
 * <br>[機  能] PDF出力用経路情報Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngTanPdfKeiroModel {

    /** singiTable*/
    private List<RngTanPdfSingiModel> singiList__ = null;
    /** 状態*/
    private int keiroStatus__ = 0;
    /** 経路名*/
    private String keiroName__ = null;
    /** 経路順*/
    private int keiroSort__ = 0;
    /** 審議種別*/
    private int keiroSingi__ = 0;
    /** 審議者数*/
    private int keiroCount__ = 0;
    /** 出力行数*/
    private int keiroRowCount__ = 0;
    /** 経路進行*/
    private int keiroMessage__ = 0;
    /** 経路進行条件閾値*/
    private int keiroLimit__ = 0;
    /** 後閲者名*/
    private String keiroKoetuMei__ = null;
    /**
     * <p>singiList を取得します。
     * @return singiList
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#singiList__
     */
    public List<RngTanPdfSingiModel> getSingiList() {
        return singiList__;
    }
    /**
     * <p>singiList をセットします。
     * @param singiList singiList
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#singiList__
     */
    public void setSingiList(List<RngTanPdfSingiModel> singiList) {
        singiList__ = singiList;
    }
    /**
     * <p>keiroStatus を取得します。
     * @return keiroStatus
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroStatus__
     */
    public int getKeiroStatus() {
        return keiroStatus__;
    }
    /**
     * <p>keiroStatus をセットします。
     * @param keiroStatus keiroStatus
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroStatus__
     */
    public void setKeiroStatus(int keiroStatus) {
        keiroStatus__ = keiroStatus;
    }
    /**
     * <p>keiroName を取得します。
     * @return keiroName
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroName__
     */
    public String getKeiroName() {
        return keiroName__;
    }
    /**
     * <p>keiroName をセットします。
     * @param keiroName keiroName
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroName__
     */
    public void setKeiroName(String keiroName) {
        keiroName__ = keiroName;
    }
    /**
     * <p>keiroSort を取得します。
     * @return keiroSort
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroSort__
     */
    public int getKeiroSort() {
        return keiroSort__;
    }
    /**
     * <p>keiroSort をセットします。
     * @param keiroSort keiroSort
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroSort__
     */
    public void setKeiroSort(int keiroSort) {
        keiroSort__ = keiroSort;
    }
    /**
     * <p>keiroSingi を取得します。
     * @return keiroSingi
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroSingi__
     */
    public int getKeiroSingi() {
        return keiroSingi__;
    }
    /**
     * <p>keiroSingi をセットします。
     * @param keiroSingi keiroSingi
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroSingi__
     */
    public void setKeiroSingi(int keiroSingi) {
        keiroSingi__ = keiroSingi;
    }
    /**
     * <p>keiroCount を取得します。
     * @return keiroCount
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroCount__
     */
    public int getKeiroCount() {
        return keiroCount__;
    }
    /**
     * <p>keiroCount をセットします。
     * @param keiroCount keiroCount
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroCount__
     */
    public void setKeiroCount(int keiroCount) {
        keiroCount__ = keiroCount;
    }
    /**
     * <p>keiroRowCount を取得します。
     * @return keiroRowCount
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroRowCount__
     */
    public int getKeiroRowCount() {
        return keiroRowCount__;
    }
    /**
     * <p>keiroRowCount をセットします。
     * @param keiroRowCount keiroRowCount
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroRowCount__
     */
    public void setKeiroRowCount(int keiroRowCount) {
        keiroRowCount__ = keiroRowCount;
    }
    /**
     * <p>keiroMessage を取得します。
     * @return keiroMessage
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroMessage__
     */
    public int getKeiroMessage() {
        return keiroMessage__;
    }
    /**
     * <p>keiroMessage をセットします。
     * @param keiroMessage keiroMessage
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroMessage__
     */
    public void setKeiroMessage(int keiroMessage) {
        keiroMessage__ = keiroMessage;
    }
    /**
     * <p>keiroLimit を取得します。
     * @return keiroLimit
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroLimit__
     */
    public int getKeiroLimit() {
        return keiroLimit__;
    }
    /**
     * <p>keiroLimit をセットします。
     * @param keiroLimit keiroLimit
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroLimit__
     */
    public void setKeiroLimit(int keiroLimit) {
        keiroLimit__ = keiroLimit;
    }
    /**
     * <p>keiroKoetuMei を取得します。
     * @return keiroKoetuMei
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroKoetuMei__
     */
    public String getKeiroKoetuMei() {
        return keiroKoetuMei__;
    }
    /**
     * <p>keiroKoetuMei をセットします。
     * @param keiroKoetuMei keiroKoetuMei
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfKeiroModel#keiroKoetuMei__
     */
    public void setKeiroKoetuMei(String keiroKoetuMei) {
        keiroKoetuMei__ = keiroKoetuMei;
    }

}