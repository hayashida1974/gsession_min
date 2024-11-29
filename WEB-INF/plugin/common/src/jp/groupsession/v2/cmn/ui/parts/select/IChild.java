package jp.groupsession.v2.cmn.ui.parts.select;

import java.util.List;

public interface IChild {
    String getName();
    String getValue();
    IChildType getType();
    /**
     * @return 停止状態フラグ 0：通常  1:停止
     */
    int getTeisiFlg();
    /**
     * @return 削除済みフラグ 0：通常  9:削除済み
     */
    int getJkbn();

    /**
     * <p>labelリスト を取得します。
     * @return userLabel
     */
    List<String> getLabelList();
    /**
     * <p>labelリスト をセットします。
     * @param userLabel userLabel
     */
    void setLabelList(List<String> userLabel);
}
