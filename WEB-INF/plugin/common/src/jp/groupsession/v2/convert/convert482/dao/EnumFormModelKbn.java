package jp.groupsession.v2.convert.convert482.dao;

import jp.co.sjts.util.IEnumMsgkeyValue;

/**
 *
 * <br>[機  能] 入力フォームモデル選択用 列挙型
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumFormModelKbn implements IEnumMsgkeyValue {
    /** コメント  */
    label(1, "cmn.form.label"),
    /** テキスト入力　 */
    textbox(2, "cmn.form.textbox"),
    /** テキスト入力(複数行)　 */
    textarea(3, "cmn.form.textarea"),
    /** 日付入力  */
    date(4, "cmn.form.date"),
    /** 数値入力  */
    number(5, "cmn.form.number"),
    /** ラジオボタン  */
    radio(6, "cmn.form.radio"),
    /** コンボボックス  */
    combo(7, "cmn.form.combo"),
    /** チェックボックス */
    check(8, "cmn.form.check"),
    /** 自動計算（合計） */
    sum(9, "cmn.form.sum"),
    /** 自動計算（その他） */
    calc(10, "cmn.form.calc"),
    /** ユーザ選択 */
    user(11, "cmn.form.user"),
    /** グループ選択 */
    group(12, "cmn.form.group"),
    /** ブロック */
    block(13, "cmn.form.block"),
    /** 表 */
    blocklist(14, "cmn.form.blocklist"),
    /** 添付*/
    file(15, "cmn.form.temp");


    /** フォーム種別ID */
    private int value__;
    /** メッセージキー */
    private String msgKey__;

    @Override
    public int getValue() {
        return value__;
    }

    @Override
    public String getMsgKey() {
        // TODO 自動生成されたメソッド・スタブ
        return msgKey__;
    }
    /**
     * コンストラクタ
     * @param value フォーム種別ID
     * @param msgKey メッセージキー
     */
    private EnumFormModelKbn(int value, String msgKey) {
        value__ = value;
        msgKey__ = msgKey;
    }


}
