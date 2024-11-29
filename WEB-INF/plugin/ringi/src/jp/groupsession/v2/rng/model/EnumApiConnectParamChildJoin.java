package jp.groupsession.v2.rng.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 *
 * <br>[機  能] RAP_CHILD_JOIN 設定値列挙型
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumApiConnectParamChildJoin {
    /** 文字列として結合*/
    @Params(value = 0, labelMsgKey = "rng.rng090.56")
    JOIN_STRING,
    /** 配列として結合*/
    @Params(value = 1, labelMsgKey = "rng.rng090.57")
    JOIN_ARRAY;

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface Params {
        /** @return 値 */
        int value();
        /** @return ラベル表示用メッセージキー*/
        String labelMsgKey();

    }

    /**値*/
    private final int value__;
    /**ラベル表示メッセージキー*/
    private final String labelMsgKey__;
    /**
     * コンストラクタ
     */
    private EnumApiConnectParamChildJoin() {
        Params ann;
        Field[] fields = getClass().getFields();
        ann = fields[ordinal()].getAnnotation(Params.class);
        value__ = ann.value();
        labelMsgKey__ = ann.labelMsgKey();
    }
    /**
     * <p>value を取得します。
     * @return value
     * @see jp.groupsession.v2.rng.model.EnumApiConnectParamConditionOperand#value__
     */
    public int getValue() {
        return value__;
    }
    /**
     * <p>labelMsgKey を取得します。
     * @return labelMsgKey
     * @see jp.groupsession.v2.rng.model.EnumApiConnectParamConditionOperand#labelMsgKey__
     */
    public String getLabelMsgKey() {
        return labelMsgKey__;
    }

}
