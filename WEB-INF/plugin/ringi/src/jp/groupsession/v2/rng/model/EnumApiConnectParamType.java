package jp.groupsession.v2.rng.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <br>[機  能] 列挙型 稟議API連携 パラメータ対象
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumApiConnectParamType {
    /** 手入力*/
    @Params(value = 1, labelMsgKey = "rng.rng090.19", formType = "")
    MANUAL,
    /** 申請ユーザ*/
    @Params(value = 2, labelMsgKey = "rng.rng090.20", formType = "user")
    SINSEI_USER,
    /** 申請日*/
    @Params(value = 3, labelMsgKey = "rng.rng090.21", formType = "datetime")
    SINSEI_DATE,
    /** 最後の承認ユーザ*/
    @Params(value = 4, labelMsgKey = "rng.rng090.22", formType = "user")
    KESSAI_USER,
    /** 決済日*/
    @Params(value = 5, labelMsgKey = "rng.rng090.23", formType = "datetime")
    KESSAI_DATE,
    /** タイトル */
    @Params(value = 6, labelMsgKey = "cmn.title", formType = "")
    TITLE,
    /** 稟議フォーム */
    @Params(value = 7, labelMsgKey = "rng.rng090.04", formType = "")
    FORM_VALUE,
    /** 指定ユーザ */
    @Params(value = 8, labelMsgKey = "rng.rng090.46", formType = "user")
    SELECT_USER,
    /** 指定グループ; */
    @Params(value = 9, labelMsgKey = "rng.rng090.47", formType = "group")
    SELECT_GROUP,
    /** 入れ子; */
    @Params(value = 10, labelMsgKey = "rng.rng090.48", formType = "")
    OBJECT,
    /** 表ボディ; */
    @Params(value = 11, labelMsgKey = "rng.rng090.49", formType = "")
    LIST_BODY;


    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface Params {
        /** @return 値 */
        int value();
        /** @return ラベル表示用メッセージキー*/
        String labelMsgKey();
        /** @return formatType*/
        String formType();

    }

    /**値*/
    private final int value__;
    /**ラベル表示メッセージキー*/
    private final String labelMsgKey__;
    /**フォームタイプ*/
    private final String formType__;

    /** 値検索用インデクスマップ*/
    private static Map<Integer, EnumApiConnectParamType> valIndexMap__ =
            Stream.of(EnumApiConnectParamType.values())
            .collect(Collectors.toMap(
                    EnumApiConnectParamType::getValue,
                    e -> e));


    /**
     *
     * コンストラクタ
     */
    EnumApiConnectParamType() {
        Params ann;
        Field[] fields = getClass().getFields();
        ann = fields[ordinal()].getAnnotation(Params.class);
        value__ = ann.value();
        labelMsgKey__ = ann.labelMsgKey();
        formType__ = ann.formType();
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
     * <p>formatType を取得します。
     * @return formatType
     * @see jp.groupsession.v2.rng.model.EnumApiConnectParamType#formatType__
     */
    public String getFormType() {
        return formType__;
    }
    /**
     *
     * <br>[機  能] 値に一致する 列挙型を返す
     * <br>[解  説] 一致しない場合 null
     * <br>[備  考]
     * @param val 列挙型 値
     * @return 列挙型 稟議API連携
     */
    public static EnumApiConnectParamType valueOf(int val) {
        return valIndexMap__.get(val);
    }
}
