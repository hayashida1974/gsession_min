package jp.groupsession.v2.rng.rng110keiro;

import jp.co.sjts.util.EnumUtil;
import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.IEnumMsgkeyValue;
/**
 *
 * <br>[機  能] 経路区分 列挙型
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumKeiroKbn implements IEnumMsgkeyValue {
    /**任意設定*/
    FREESET(0, "rng.rng110.08"),
    /**ユーザ指定*/
    USERTARGET(1, "rng.rng110.03"),
    /**役職指定*/
    POSTARGET(2, "rng.rng110.04"),
    /**ユーザ選択*/
    USERSEL(3, "rng.rng110.06"),
    /**グループ選択*/
    GROUPSEL(4, "rng.rng110.07"),
    /**上長指定*/
    BOSSTARGET(5, "rng.rng110.05");

    /**任意設定*/
    public static final int FREESET_VAL = 0;
    /**ユーザ指定*/
    public static final int  USERTARGET_VAL = 1;
    /**役職指定*/
    public static final int  POSTARGET_VAL = 2;
    /**ユーザ選択*/
    public static final int  USERSEL_VAL = 3;
    /**グループ選択*/
    public static final int  GROUPSEL_VAL = 4;
    /**上長指定*/
    public static final int  BOSSTARGET_VAL = 5;

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
    private EnumKeiroKbn(int value, String msgKey) {
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
    public static EnumKeiroKbn valueOf(int val) throws EnumOutRangeException {
        switch (val) {
        case EnumKeiroKbn.FREESET_VAL:
            return FREESET;
        case EnumKeiroKbn.USERTARGET_VAL:
            return USERTARGET;
        case EnumKeiroKbn.POSTARGET_VAL:
            return POSTARGET;
        case EnumKeiroKbn.USERSEL_VAL:
            return USERSEL;
        case EnumKeiroKbn.GROUPSEL_VAL:
            return GROUPSEL;
        case EnumKeiroKbn.BOSSTARGET_VAL:
            return BOSSTARGET;
        default:
            throw new EnumUtil.EnumOutRangeException();
        }
    }
}
