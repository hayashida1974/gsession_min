package jp.groupsession.v2.cmn.biz.apiconnect;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] 連携API登録 認証方式 列挙型
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumAuth {
    /** なし */
    @Params(value = 0, labelMsgKey = "cmn.no")
    NONE,
    /** BASIC */
    @Params(value = 1, labelMsgKey = "api.api020.12")
    BASIC,
    /** トークン */
    @Params(value = 2, labelMsgKey = "cmn.cmn340.12")
    TOKEN;

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
    private static Map<Integer, EnumAuth> valIndexMap__ =
            Stream.of(EnumAuth.values())
            .collect(Collectors.toMap(
                    EnumAuth::getValue,
                    e -> e));


    /**
     *
     * コンストラクタ
     */
    EnumAuth() {
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
     * <br>[機  能] 表示用名称を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param locale ロケール
     * @return 表示用名称
     */
    public String getLabel(Locale locale) {
        GsMessage gsMsg = new GsMessage(locale);
        return gsMsg.getMessage(labelMsgKey__);
    }
    /**
     *
     * <br>[機  能] 値に一致する 列挙型を返す
     * <br>[解  説] 一致しない場合 null
     * <br>[備  考]
     * @param val 列挙型 値
     * @return 列挙型
     */
    public static EnumAuth valueOf(int val) {
        return valIndexMap__.get(val);
    }
}
