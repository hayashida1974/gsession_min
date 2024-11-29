package jp.groupsession.v2.rng.pdf;

import jp.groupsession.v2.rng.model.RngTemplateFormModel;


/**
 * <br>[機  能] 稟議検索結果からPDF出力する入力フォーム情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngTanPdfFormModel extends RngTemplateFormModel {

    /** ソート番号 */
    private int sort__ = 0;

    /** テーブル行 */
    private int tableRow__ = -1;

    /** ブロックインデックス */
    private int blockIdx__ = -1;

    /** 親フォームSID(Block or BlockList) */
    private int parentSid__ = -1;

    /** 親フォームタイプ */
    private int parentType__ = -1;

    /** 表ボディフラグ */
    private boolean listBodyFlg__ = false;

    /** 表フッタフラグ */
    private boolean listFooterFlg__ = false;

    /** 親タイトル */
    private String parentTitle__ = null;

    /** 上下寄せフラグ(コメント用) */
    private int commentValign__ = -1;

    /**
     * <p>sort を取得します。
     * @return sort
     */
    public int getSort() {
        return sort__;
    }

    /**
     * <p>sort をセットします。
     * @param sort sort
     */
    public void setSort(int sort) {
        sort__ = sort;
    }

    /**
     * <p>tableRow を取得します。
     * @return tableRow
     */
    public int getTableRow() {
        return tableRow__;
    }

    /**
     * <p>tableRow をセットします。
     * @param tableRow tableRow
     */
    public void setTableRow(int tableRow) {
        tableRow__ = tableRow;
    }

    /**
     * <p>tableRow を取得します。
     * @return tableRow
     */
    public int getBlockIdx() {
        return blockIdx__;
    }

    /**
     * <p>blockIdx をセットします。
     * @param blockIdx blockIdx
     */
    public void setBlockIdx(int blockIdx) {
        blockIdx__ = blockIdx;
    }

    /**
     * <p>parentSid を取得します。
     * @return parentSid
     */
    public int getParentSid() {
        return parentSid__;
    }

    /**
     * <p>parentSid をセットします。
     * @param parentSid parentSid
     */
    public void setParentSid(int parentSid) {
        parentSid__ = parentSid;
    }

    /**
     * <p>parentType を取得します。
     * @return parentType
     */
    public int getParentType() {
        return parentType__;
    }

    /**
     * <p>parentType をセットします。
     * @param parentType parentType
     */
    public void setParentType(int parentType) {
        parentType__ = parentType;
    }

    /**
     * <p>listBodyFlg を取得します。
     * @return listBodyFlg
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfFormModel#listBodyFlg__
     */
    public boolean isListBodyFlg() {
        return listBodyFlg__;
    }

    /**
     * <p>listBodyFlg をセットします。
     * @param listBodyFlg listBodyFlg
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfFormModel#listBodyFlg__
     */
    public void setListBodyFlg(boolean listBodyFlg) {
        listBodyFlg__ = listBodyFlg;
    }
    /**
     * <p>listFooterFlg を取得します。
     * @return listFooterFlg
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfFormModel#listFooterFlg__
     */
    public boolean isListFooterFlg() {
        return listFooterFlg__;
    }

    /**
     * <p>listFooterFlg をセットします。
     * @param listFooterFlg listFooterFlg
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfFormModel#listFooterFlg__
     */
    public void setListFooterFlg(boolean listFooterFlg) {
        listFooterFlg__ = listFooterFlg;
    }
    /**
     * <p>parentTitle を取得します。
     * @return parentTitle
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfFormModel#parentTitle__
     */
    public String getParentTitle() {
        return parentTitle__;
    }

    /**
     * <p>parentTitle をセットします。
     * @param parentTitle parentTitle
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfFormModel#parentTitle__
     */
    public void setParentTitle(String parentTitle) {
        parentTitle__ = parentTitle;
    }

    /**
     * <p>commentValign を取得します。
     * @return commentValign
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfFormModel#commentValign__
     */
    public int getCommentValign() {
        return commentValign__;
    }

    /**
     * <p>commentValign をセットします。
     * @param commentValign commentValign
     * @see jp.groupsession.v2.rng.pdf.RngTanPdfFormModel#commentValign__
     */
    public void setCommentValign(int commentValign) {
        commentValign__ = commentValign;
    }
}