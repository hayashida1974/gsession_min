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
 * <br>[機  能] 連携API登録 ContentType 列挙型
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumContentType {
    /** 手入力 */
    @Params(value = 0, label = "", group = EnumContentTypeGroup.TXT)
    MANUAL,
    /** application/json */
    @Params(value = 2, label = "application/json", group = EnumContentTypeGroup.JSON)
    APPLICATION_JSON,

    /** application/x-www-form-urlencoded */
    @Params(value = 1,
        label = "application/x-www-form-urlencoded",
        group = EnumContentTypeGroup.URL)
    APPLICATION_FORM,

    /** text/xml */
    @Params(value = 3, label = "text/xml", group = EnumContentTypeGroup.XML)
    TEXT_XML,

    /** application/xml */
    @Params(value = 4, label = "application/xml", group = EnumContentTypeGroup.XML)
    APPLICATION_XML,

    /** text/xml-external-parsed-entity */
    @Params(value = 5, label = "text/xml-external-parsed-entity", group = EnumContentTypeGroup.XML)
    TEXT_XML_EXTERNAL_PARSED_ENTITY,

    /** application/xml-external-parsed-entity */
    @Params(value = 6,
        label = "application/xml-external-parsed-entity",
        group = EnumContentTypeGroup.XML)
    APPLICATION_XML_EXTERNAL_PARSED_ENTITY,

    /** application/xml-dtd */
    @Params(value = 7, label = "application/xml-dtd", group = EnumContentTypeGroup.XML)
    APPLICATION_XML_DTD,

    /** multipart/form-data */
    @Params(value = 8, label = "multipart/form-data", group = EnumContentTypeGroup.MLT)
    MULTIPART_FORM_DATA;

    /**列挙型 グループ*/
    public enum EnumContentTypeGroup {
        /** json*/
        JSON,
        /** url*/
        URL,
        /** xml*/
        XML,
        /** txt*/
        TXT,
        /** multipart/form-data*/
        MLT
    }

    /**値*/
    private final int value__;
    /**表示名*/
    private final String label__;
    /**列挙型グループ*/
    private final EnumContentTypeGroup group__;

    /** 値検索用インデクスマップ*/
    private static Map<Integer, EnumContentType> valIndexMap__ =
            Stream.of(EnumContentType.values())
            .collect(Collectors.toMap(
                    EnumContentType::getValue,
                    e -> e));


    private EnumContentType() {
        Params ann;
        Field[] fields = getClass().getFields();
        ann = fields[ordinal()].getAnnotation(Params.class);
        value__ = ann.value();
        label__ = ann.label();
        group__ = ann.group();
    }

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
        /** @return 表示用名称 */
        String label();
        /** @return グループ */
        EnumContentTypeGroup group();
    }
    /**
     *
     * <br>[機  能] 表示用名称を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 表示用名称
     */
    public String getLabel() {
        return label__;
    }
    /**
     *
     * <br>[機  能] 値を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 表示用名称
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
    public static EnumContentType valueOf(int val) {
        return valIndexMap__.get(val);
    }
    /**
     * <p>group を取得します。
     * @return group
     * @see jp.groupsession.v2.cmn.biz.apiconnect.EnumContentType#group__
     */
    public EnumContentTypeGroup getGroup() {
        return group__;
    }
}
