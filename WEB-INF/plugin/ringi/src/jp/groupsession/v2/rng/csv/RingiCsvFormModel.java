package jp.groupsession.v2.rng.csv;

import jp.groupsession.v2.rng.model.RngTemplateFormModel;


/**
 * <br>[機  能] 稟議検索結果からCSV出力する入力フォーム情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RingiCsvFormModel extends RngTemplateFormModel {

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
}