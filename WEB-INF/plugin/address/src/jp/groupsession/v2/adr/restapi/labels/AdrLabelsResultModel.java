package jp.groupsession.v2.adr.restapi.labels;

import java.util.List;

import jp.groupsession.v2.restapi.response.annotation.ChildElementName;

/**
 * <br>[機  能] アドレス帳 ラベル一覧取得API 実行結果モデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class AdrLabelsResultModel {

    /** アドレス帳 ラベルカテゴリSID */
    private Integer sid__ = 0;
    /** アドレス帳 ラベルカテゴリ名 */
    private String name__ = null;
    /** ラベル情報配列 */
    @ChildElementName("labelInfo")
    private List<LabelInfo> labelArray__;

    public static class LabelInfo {
        /** アドレス帳 ラベルSID */
        private Integer sid__ = 0;
        /** アドレス帳 ラベル名 */
        private String name__ = null;
        
        public Integer getSid() {
            return sid__;
        }
        public void setSid(Integer sid) {
            sid__ = sid;
        }
        public String getName() {
            return name__;
        }
        public void setName(String name) {
            name__ = name;
        }       
    }

    public Integer getSid() {
        return sid__;
    }

    public void setSid(Integer sid) {
        sid__ = sid;
    }

    public String getName() {
        return name__;
    }

    public void setName(String name) {
        name__ = name;
    }

    public List<LabelInfo> getLabelArray() {
        return labelArray__;
    }

    public void setLabelArray(List<LabelInfo> labelArray) {
        labelArray__ = labelArray;
    }
}
