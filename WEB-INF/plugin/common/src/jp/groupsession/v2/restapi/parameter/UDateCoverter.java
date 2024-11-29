package jp.groupsession.v2.restapi.parameter;

import java.lang.annotation.Annotation;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.Converter;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;

/**
 *
 * <br>[機  能] UDate型へのコンバータ
 * <br>[解  説]
d * <br>[備  考] コンバート失敗時 RestApiValidateExceptionをthrow
 * <br>         ReasonCodeはPARAM_FORMAT
 * @author JTS
 */
public class UDateCoverter implements Converter {
    /** チェック対象表示名*/
    String dispName__;
    /** 日付フォーマットセット*/
    private Set<UDateFormat.Format> format__ = Set.of(UDateFormat.Format.DATETIME_SLUSH);
    /**
     *
     * コンストラクタ
     * @param dispName チェック対象表示名
     * @param ants 日付フォーマットセット
     */
    public UDateCoverter(String dispName, Set<Annotation> ants) {
        dispName__ = dispName;
        if (ants == null) {
            return;
        }
        format__ =
                EnumSet.copyOf(
                    Stream.concat(
                        ants.stream()
                            .filter(a -> (a instanceof UDateFormat))
                            .map(a -> (UDateFormat) a),
                        ants.stream()
                            .filter(a -> (a instanceof UDateFormat.Holder))
                            .flatMap(a -> Stream.of(((UDateFormat.Holder) a).value()))
                            )
                    .map(a -> a.value())
                    .collect(Collectors.toSet()));

    }
    @Override
    public Object convert(@SuppressWarnings("rawtypes") Class paramClass, Object paramObject) {

        if (paramObject instanceof String) {
            String src = (String) paramObject;
            for (UDateFormat.Format format : format__) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format.value());
                formatter = formatter.withResolverStyle(ResolverStyle.LENIENT);
                //フォーマット解析
                //フォーマット不一致はここで例外
                try {
                    formatter.parse(src);
                } catch (DateTimeException e) {
                    continue;
                }

                try {
                    //時刻解析
                    //不正な時刻の場合、例外発生
                    formatter = DateTimeFormatter.ofPattern(format.value());
                    formatter = formatter.withResolverStyle(ResolverStyle.STRICT);
                    TemporalAccessor parsed = null;
                    try {
                        parsed = formatter.parse(src);
                    } catch (DateTimeException e) {
                        //時刻の各値に不正な値が設定されている
                        throw new RestApiValidateException("日時型へのコンバートに失敗 フォーマットエラー", null)
                        .setReasonCode(EnumError.PARAM_OTHER_INVALID)
                        .setMessageResource("error.input.notfound.time", dispName__);
                    }
    
                    //日付解析
                    //不正な日付はここで例外
                    parsed = LocalDateTime.of(
                            __getParsedValue(parsed, ChronoField.YEAR_OF_ERA),
                            __getParsedValue(parsed, ChronoField.MONTH_OF_YEAR),
                            __getParsedValue(parsed, ChronoField.DAY_OF_MONTH),
                            __getParsedValue(parsed, ChronoField.HOUR_OF_DAY),
                            __getParsedValue(parsed, ChronoField.MINUTE_OF_HOUR),
                            __getParsedValue(parsed, ChronoField.SECOND_OF_MINUTE)
                            );
                    //UDateへの変換
                    UDate date = new UDate();
                    date.setTimeStamp(
                            __getParsedValue(parsed, ChronoField.YEAR_OF_ERA),
                            __getParsedValue(parsed, ChronoField.MONTH_OF_YEAR),
                            __getParsedValue(parsed, ChronoField.DAY_OF_MONTH),
                            __getParsedValue(parsed, ChronoField.HOUR_OF_DAY),
                            __getParsedValue(parsed, ChronoField.MINUTE_OF_HOUR),
                            __getParsedValue(parsed, ChronoField.SECOND_OF_MINUTE)
                            );
                    return date;

                } catch (DateTimeException e) {
                    //日付の各値に不正な値が設定されている
                    //エラー理由をcauseの値で識別する
                    throw new RestApiValidateException("日付型へのコンバートに失敗 フォーマットエラー", null)
                    .setReasonCode(EnumError.PARAM_OTHER_INVALID)
                    .setMessageResource("error.input.notfound.date", dispName__);
                }
            }

            throw new RestApiValidateException("日付型へのコンバートに失敗 フォーマットエラー", null)
            .setReasonCode(EnumError.PARAM_FORMAT);

        }
        if (paramObject instanceof java.sql.Date) {
            return UDate.getInstanceSqlDate((java.sql.Date) paramObject);
        }
        if (paramObject instanceof java.util.Date) {
            return UDate.getInstanceDate((java.util.Date) paramObject);
        }
        throw new RestApiValidateException("日付型へのコンバートに失敗 フォーマットエラー", null)
        .setReasonCode(EnumError.PARAM_FORMAT);
    }
    /**
     *
     * <br>[機  能] フォーマット解析結果から値を取り出す
     * <br>[解  説] サポートしていない、フィールドについては0を返す
     * <br>[備  考]
     * @param parsed
     * @param field
     * @return フォーマット解析結果
     */
    private int __getParsedValue(TemporalAccessor parsed, ChronoField field) {
        if (!parsed.isSupported(field)) {
            return 0;
        }
        int ret = parsed.get(field);
        return ret;
    }
}
