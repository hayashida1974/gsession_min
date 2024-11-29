package jp.groupsession.v2.cmn.formbuilder;

import jp.co.sjts.util.IEnumMsgkeyValue;
import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
/**
 *
 * <br>[機  能] 比較子定数
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumCompOpr implements IEnumMsgkeyValue {
    /** に等しい */
    EQ(0, "cmn.comp.eq"),
    /** 以上 */
    OE(1, "cmn.comp.oe"),
    /** 以下 */
    LE(2, "cmn.comp.le"),
    /** 未満 */
    LO(3, "cmn.comp.lo"),
    /** を超過 */
    OV(4, "cmn.comp.ov");

    /** 値 */
    private final int value__;
    /** メッセージキー */
    private final String msgKey__;
    /**
     * <p>value を取得します。
     * @return value
     */
    public int getValue() {
        return value__;
    }

    /**
     * <p>label を取得します。
     * @return msgKey
     */
    public String getMsgKey() {
        return msgKey__;
    }
    /**
     * コンストラクタ
     * @param value 値
     * @param msgKey メッセージキー
     */
    private EnumCompOpr(int value, String msgKey) {
        this.value__ = value;
        this.msgKey__ = msgKey;
    }
    /**
    *
    * <br>[機  能] 数値から列挙型を返す
    * <br>[解  説]
    * <br>[備  考]
    * @param val 選択数値
    * @return 列挙型
    * @throws EnumOutRangeException 列挙型範囲外
    */
    public static EnumCompOpr valueOf(int value)
            throws EnumOutRangeException {
        switch (value) {
        /** に等しい */
            case 0: return EQ;
        /** 以上 */
            case 1: return OE;
        /** 以下 */
            case 2: return LE;
        /** 未満 */
            case 3: return LO;
        /** を超過 */
            case 4: return OV;
            default:
                throw new EnumOutRangeException();
        }
    }
}
