package jp.groupsession.v2.cmn.biz.apiconnect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * <br>[機  能] 連携API登録 HTTPリクエストメソッド 列挙型
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumMethod {
    /** GET */
    @Params(0)
    GET,
    /** POST */
    @Params(1)
    POST,
    /** PUT */
    @Params(2)
    PUT,
    /** DELETE */
    @Params(3)
    DELETE;

    /**値*/
    private final int value__;
    /** 値検索用インデクスマップ*/
    private static Map<Integer, EnumMethod> valIndexMap__ =
            Stream.of(EnumMethod.values())
            .collect(Collectors.toMap(
                    EnumMethod::getValue,
                    e -> e));
    /**
     *
     * <br>[機  能] 列挙型初期化アノテーション
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface Params {
        /** @return 値 */
        int value();
    }

    private EnumMethod() {
        Params ann;
        Field[] fields = getClass().getFields();
        ann = fields[ordinal()].getAnnotation(Params.class);
        value__ = ann.value();
    }

    /**
     * <p>value を取得します。
     * @return value
     * @see jp.groupsession.v2.cmn.biz.apiconnect.EnumMethod#value__
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
     * @return 列挙型
     */
    public static EnumMethod valueOf(int val) {
        return valIndexMap__.get(val);
    }

}
