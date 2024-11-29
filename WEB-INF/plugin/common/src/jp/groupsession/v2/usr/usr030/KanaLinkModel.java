package jp.groupsession.v2.usr.usr030;

/**
 * <br>[機  能] 五十音インデックスの表示情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class KanaLinkModel {
    /** カタカナ */
    private String kana__ = null;
    /** ユーザ情報ありなしのフラグ */
    private boolean exists__ = false;
    /** 行番号 */
    private String row__ = null;

    /**
     * @return exists を戻します。
     */
    public boolean isExists() {
        return exists__;
    }
    /**
     * @param exists 設定する exists。
     */
    public void setExists(boolean exists) {
        exists__ = exists;
    }
    /**
     * @return kana を戻します。
     */
    public String getKana() {
        return kana__;
    }
    /**
     * @param kana 設定する kana。
     */
    public void setKana(String kana) {
        kana__ = kana;
    }
    /**
     * @return row を戻します。
     */
    public String getRow() {
        return row__;
    }
    /**
     * @param row 設定する row。
     */
    public void setRow(String row) {
        row__ = row;
    }
}
