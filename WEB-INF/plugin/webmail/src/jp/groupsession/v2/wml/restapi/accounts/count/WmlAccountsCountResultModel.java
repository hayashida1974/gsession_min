package jp.groupsession.v2.wml.restapi.accounts.count;

import java.util.List;

import jp.groupsession.v2.restapi.response.annotation.ChildElementName;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;
import jp.groupsession.v2.wml.model.WmlLabelCountModel;
import jp.groupsession.v2.wml.restapi.model.DirectoryArray;

/**
 * <br>[機  能] WEBメール メール件数取得API 実行結果モデル
 * <br>[解  説]
 * <br>[備  考]
 */
@ResponceModel(targetField = {
    "directoryArray",
    "labelArray"
    })
public class WmlAccountsCountResultModel {

    /** ディレクトリ配列 */
    @ChildElementName("directoryInfo")
    private List<DirectoryArray> directoryArray__;

    /** ラベル配列 */
    @ChildElementName("labelInfo")
    private List<LabelArray> labelArray__;

    /**
     * <br>[機  能] WEBメールRESTAPI ディレクトリ情報保持クラス
     * <br>[解  説]
     * <br>[備  考]
     */
    @ResponceModel(targetField = {
        "sid",
        "name",
        "nonReadCountNum",
        "countNum"
        })
    public static class LabelArray {

        /** 含有モデル */
        private final WmlLabelCountModel base__;
        /** 全メール件数 */
        private long countNum__;

        /**
         *
         * コンストラクタ
         * @param base モデル
         */
        public LabelArray(WmlLabelCountModel base, long countNum) {
            base__ = base;
            countNum__ = countNum;
        }

        /** @return sid */
        public int getSid() {
            return base__.getId();
        }

        /** @return name */
        public String getName() {
            return base__.getName();
        }

        /** @return nonReadCountNum */
        public long getNonReadCountNum() {
            return base__.getNoReadCount();
        }

        /** @return countNum */
        public long getCountNum() {
            return countNum__;
        }
    }

    /**
     * <p>directoryArray を取得します。
     * @return directoryArray
     * @see jp.groupsession.v2.wml.restapi.accounts.count.WmlAccountsCountResultModel#directoryArray__
     */
    public List<DirectoryArray> getDirectoryArray() {
        return directoryArray__;
    }

    /**
     * <p>directoryArray を設定します。
     * @param directoryArray
     * @see jp.groupsession.v2.wml.restapi.accounts.count.WmlAccountsCountResultModel#directoryArray__
     */
    public void setDirectoryArray(List<DirectoryArray> directoryArray) {
        directoryArray__ = directoryArray;
    }

    /**
     * <p>labelArray を取得します。
     * @return labelArray
     * @see jp.groupsession.v2.wml.restapi.accounts.count.WmlAccountsCountResultModel#labelArray__
     */
    public List<LabelArray> getLabelArray() {
        return labelArray__;
    }

    /**
     * <p>labelArray を設定します。
     * @param labelArray
     * @see jp.groupsession.v2.wml.restapi.accounts.count.WmlAccountsCountResultModel#labelArray__
     */
    public void setLabelArray(List<LabelArray> labelArray) {
        labelArray__ = labelArray;
    }

}
