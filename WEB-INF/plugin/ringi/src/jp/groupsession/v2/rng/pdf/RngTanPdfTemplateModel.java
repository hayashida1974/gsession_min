package jp.groupsession.v2.rng.pdf;

/**
 * <br>[機  能] 稟議確認画面のPDF出力用Modelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngTanPdfTemplateModel {

    /** タイトル */
    private String title__ = "";
    /** 表示パラメータ */
    private String param__ = "";
    /** 親フォームSID(Block or BlockList) */
    private int parentSid__ = -1;
    /** 親フォームタイプ */
    private int parentType__ = -1;
    /** 親タイトル */
    private String parentTitle__ = "";
    /** 表サイズ */
    private int listSize__ = 0;
    /** テーブル行 */
    private int tableRow__ = -1;
    /** 上下寄せフラグ(コメント用) */
    private int commentValign__ = -1;

    /**
     * <p>title を取得します。
     * @return title
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#title__
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title をセットします。
     * @param title title
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#title__
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>param を取得します。
     * @return param
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#param__
     */
    public String getParam() {
        return param__;
    }
    /**
     * <p>param をセットします。
     * @param param param
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#param__
     */
    public void setParam(String param) {
        param__ = param;
    }
    /**
     * <p>parentSid を取得します。
     * @return parentSid
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#parentSid__
     */
    public int getParentSid() {
        return parentSid__;
    }
    /**
     * <p>parentSid をセットします。
     * @param parentSid parentSid
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#parentSid__
     */
    public void setParentSid(int parentSid) {
        parentSid__ = parentSid;
    }
    /**
     * <p>parentType を取得します。
     * @return parentType
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#parentType__
     */
    public int getParentType() {
        return parentType__;
    }
    /**
     * <p>parentType をセットします。
     * @param parentType parentType
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#parentType__
     */
    public void setParentType(int parentType) {
        parentType__ = parentType;
    }
    /**
     * <p>parentTitle を取得します。
     * @return parentTitle
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#parentTitle__
     */
    public String getParentTitle() {
        return parentTitle__;
    }
    /**
     * <p>parentTitle をセットします。
     * @param parentTitle parentTitle
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#parentTitle__
     */
    public void setParentTitle(String parentTitle) {
        parentTitle__ = parentTitle;
    }
    /**
     * <p>listSize を取得します。
     * @return listSize
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#listSize__
     */
    public int getListSize() {
        return listSize__;
    }
    /**
     * <p>listSize をセットします。
     * @param listSize listSize
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#listSize__
     */
    public void setListSize(int listSize) {
        listSize__ = listSize;
    }
    /**
     * <p>tableRow を取得します。
     * @return tableRow
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#tableRow__
     */
    public int getTableRow() {
        return tableRow__;
    }
    /**
     * <p>tableRow をセットします。
     * @param tableRow tableRow
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#tableRow__
     */
    public void setTableRow(int tableRow) {
        tableRow__ = tableRow;
    }
    /**
     * <p>commentValign を取得します。
     * @return commentValign
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#commentValign__
     */
    public int getCommentValign() {
        return commentValign__;
    }
    /**
     * <p>commentValign をセットします。
     * @param commentValign commentValign
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfTemplateModel#commentValign__
     */
    public void setCommentValign(int commentValign) {
        commentValign__ = commentValign;
    }
}