package jp.groupsession.v2.rng.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 列挙型 稟議API連携 利用条件 比較子
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public enum EnumApiConnectParamConditionOperand {
    /** 等しい  */
    @Params(value = 0, labelMsgKey = "cmn.comp.eq")
    EQ,

    /**  超過 */
    @Params(value = 1, labelMsgKey = "cmn.comp.ov")
    OVER,

    /**  以上  */
    @Params(value = 2, labelMsgKey = "cmn.comp.oe")
    OVER_EQ,

    /** 以下  */
    @Params(value = 3, labelMsgKey = "cmn.comp.le")
    LESS_EQ,

    /** 未満  */
    @Params(value = 4, labelMsgKey = "cmn.comp.lo")
    LESS,

    /**  一致しない */
    @Params(value = 5, labelMsgKey = "cmn.comp.ne")
    NOTEQ,

    /** 部分一致  */
    @Params(value = 6, labelMsgKey = "cmn.comp.pe")
    MATCH_PARTIAL,

    /** 含む  */
    @Params(value = 7, labelMsgKey = "cmn.comp.rap")
    RAP,

    /** すべて含まれる */
    @Params(value = 8, labelMsgKey = "cmn.comp.raped")
    RAPED;

    /** フォーム区分ごとの比較子マップ*/
    private static final Map<EnumFormModelKbn,
    EnumSet<EnumApiConnectParamConditionOperand>> FORM_TYPE_OPR_MAP__ =
            Stream.of(EnumFormModelKbn.values())
            .collect(Collectors.toMap(
                    kbn -> kbn,
                    kbn -> {
                        switch (kbn) {
                            case textbox:
                            case textarea:
                                return EnumSet.of(
                                        EQ,
                                        RAP,
                                        NOTEQ
                                        );
                            case number:
                            case calc:
                            case sum:
                            case date:
                            case time:
                                return EnumSet.of(
                                        EQ,
                                        LESS,
                                        LESS_EQ,
                                        OVER_EQ,
                                        OVER,
                                        NOTEQ
                                        );

                            case radio:
                            case combo:
                                return EnumSet.of(
//                                        EQ,
//                                        NOTEQ,
                                        RAPED
                                        );
                            case check:
                            case user:
                            case group:
                                return EnumSet.of(
                                        EQ,
                                        NOTEQ,
                                        MATCH_PARTIAL,
                                        RAP,
                                        RAPED
                                        );
                            case label:
                            case block:
                            case blocklist:
                            case file:
                            default:
                                return EnumSet.noneOf(EnumApiConnectParamConditionOperand.class);
                        }
                    }
                    ));
    /** 値検索用インデクスマップ*/
    private static Map<Integer, EnumApiConnectParamConditionOperand> valIndexMap__ =
            Stream.of(EnumApiConnectParamConditionOperand.values())
            .collect(Collectors.toMap(
                    EnumApiConnectParamConditionOperand::getValue,
                    e -> e));

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
    private EnumApiConnectParamConditionOperand() {
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
    /**
     *
     * <br>[機  能] 選択リストの生成
     * <br>[解  説]
     * <br>[備  考]
     * @param kbn フォームタイプ
     * @param gsMsg
     * @return 選択リスト
     */
    public static List<LabelValueBean> getSelectList(EnumFormModelKbn kbn, GsMessage gsMsg) {
        return FORM_TYPE_OPR_MAP__.get(kbn).stream()
        .map(opr -> {
            if (kbn == EnumFormModelKbn.date || kbn == EnumFormModelKbn.time ) {
                switch (opr) {
                  case LESS:
                  case LESS_EQ:
                  case OVER:
                  case OVER_EQ:
                    return new LabelValueBean(
                            gsMsg.getMessage(opr.getLabelMsgKey() + ".date"),
                            String.valueOf(opr.getValue())
                            );
                  default:
                }
            }
            if (kbn == EnumFormModelKbn.textbox || kbn == EnumFormModelKbn.textarea ) {
                switch (opr) {
                  case RAP:
                    return new LabelValueBean(
                            gsMsg.getMessage("cmn.contains.all"),
                            String.valueOf(opr.getValue())
                            );
                  default:
                }
            }
            return new LabelValueBean(
                    gsMsg.getMessage(opr.getLabelMsgKey()),
                    String.valueOf(opr.getValue())
                    );
                    })
        .collect(Collectors.toList());
    }
    /**
     *
     * <br>[機  能] 値に一致する 列挙型を返す
     * <br>[解  説] 一致しない場合 null
     * <br>[備  考]
     * @param val 列挙型 値
     * @return 列挙型 稟議API連携 利用条件 比較子
     */
    public static EnumApiConnectParamConditionOperand valueOf(int val) {
        return valIndexMap__.get(val);
    }

}