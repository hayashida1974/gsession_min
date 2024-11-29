package jp.groupsession.v2.rng.pdf;

/**
 * <br>[機  能] 稟議確認画面のPDF出力(表-body)用Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngTanPdfBodyModel {

    /** タイトル */
    private String title__ = null;
    /** 表示パラメータ */
    private String param__ = null;
    /** フォームSID */
    private int formSid__ = -1;
    /** テーブル行 */
    private int tableRow__ = -1;
    /**
     * <p>title を取得します。
     * @return title
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfBodyModel#title__
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfBodyModel#title__
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>param を取得します。
     * @return param
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfBodyModel#param__
     */
    public String getParam() {
        return param__;
    }
    /**
     * <p>param をセットします。
     * @param param param
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfBodyModel#param__
     */
    public void setParam(String param) {
        param__ = param;
    }
    /**
     * <p>formSid を取得します。
     * @return formSid
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfBodyModel#formSid__
     */
    public int getFormSid() {
        return formSid__;
    }
    /**
     * <p>formSid をセットします。
     * @param formSid formSid
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfBodyModel#formSid__
     */
    public void setFormSid(int formSid) {
        formSid__ = formSid;
    }
    /**
     * <p>tableRow を取得します。
     * @return tableRow
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfBodyModel#tableRow__
     */
    public int getTableRow() {
        return tableRow__;
    }
    /**
     * <p>tableRow をセットします。
     * @param tableRow tableRow
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfBodyModel#tableRow__
     */
    public void setTableRow(int tableRow) {
        tableRow__ = tableRow;
    }
}