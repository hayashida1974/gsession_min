package jp.groupsession.v2.cmn.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <br>[機  能] 列挙型 API連携パラメータ cap_type
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumCmnApiConnectParamType {
    /** パラメータ */
    @Params(value = 0, labelMsgKey = "cmn.cmn340.41")
    PARAMATER,
    /** 入れ子 */
    @Params(value = 1, labelMsgKey = "cmn.cmn340.42")
    IREKO,
    /** 入れ子配列 */
    @Params(value = 2, labelMsgKey = "cmn.cmn340.43")
    IREKOARRAY;

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

    /** 値検索用インデクスマップ*/
    private static Map<Integer, EnumCmnApiConnectParamType> valIndexMap__ =
            Stream.of(EnumCmnApiConnectParamType.values())
            .collect(Collectors.toMap(
                    EnumCmnApiConnectParamType::getValue,
                    e -> e));


    /**
     *
     * コンストラクタ
     */
    EnumCmnApiConnectParamType() {
        Params ann;
        Field[] fields = getClass().getFields();
        ann = fields[ordinal()].getAnnotation(Params.class);
        value__ = ann.value();
        labelMsgKey__ = ann.labelMsgKey();
    }
    /**
     * <p>labelMsgKey を取得します。
     * @return labelMsgKey
     * @see jp.groupsession.v2.rng.model.EnumApiConnectParamType#labelMsgKey__
     */
    public String getLabelMsgKey() {
        return labelMsgKey__;
    }
    /**
     * <p>value を取得します。
     * @return value
     * @see jp.groupsession.v2.rng.model.EnumApiConnectParamType#value__
     */
    public int getValue() {
        return value__;
    }
    /**
     *
     * <br>[機  能] 値に一致する 列挙型を返す
     * <br>[解  説] 一致しない場合 null
     * <br>[備  考]
     * @param val 列挙型 値
     * @return 列挙型 稟議API連携
     */
    public static EnumCmnApiConnectParamType valueOf(int val) {
        return valIndexMap__.get(val);
    }
}
