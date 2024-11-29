package jp.groupsession.v2.rng;
/**
 *
 * <br>[機  能] 稟議カテゴリに対してアクセスできない場合のチェック例外
 * <br>[解  説] エラー画面表示用にseigenKbnとcantActionStrに制限理由と制限される内容を設定する
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngCategoriCantAccessException extends Exception {
    /**制限区分定数 使用権限*/
    public static final int SEIGEN_KBN_USE = 0;
    /**制限区分定数 管理者権限*/
    public static final int SEIGEN_KBN_AUTH = 1;
    /**制限区分*/
    int seigenKbn__;
    /**制限により実行できない内容名*/
    String cantActionStr__;
    /**
     * <p>seigenKbn を取得します。
     * @return seigenKbn
     */
    public int getSeigenKbn() {
        return seigenKbn__;
    }
    /**
     * <p>seigenKbn をセットします。
     * @param seigenKbn seigenKbn
     */
    public void setSeigenKbn(int seigenKbn) {
        seigenKbn__ = seigenKbn;
    }
    /**
     * <p>cantActionStr を取得します。
     * @return cantActionStr
     */
    public String getCantActionStr() {
        return cantActionStr__;
    }
    /**
     * <p>cantActionStr をセットします。
     * @param cantActionStr cantActionStr
     */
    public void setCantActionStr(String cantActionStr) {
        cantActionStr__ = cantActionStr;
    }
    /**
     *
     * <br>[機  能] 制限区分に対応したメッセージキーを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 制限理由名のメッセージキー
     */
    public String getMsgKeySeigenKbn() {
        if (seigenKbn__ == SEIGEN_KBN_USE) {
            //テンプレートカテゴリ使用権限
            return "rng.112";
        } else {
            //テンプレートカテゴリ管理者権限
            return "rng.113";

        }
    }
}
