package jp.groupsession.v2.restapi.parameter.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * <br>[機  能] 対応日付フォーマット注釈
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(UDateFormat.Holder.class)
public @interface UDateFormat {
    Format value();
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Holder {
        UDateFormat[] value();
    }

    /** 日付フォーマット注釈*/
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface DateTimeFormat {
        String value();
    }
    /** 日付フォーマット 列挙型*/
    public enum Format {
        /** yyyy/mm/dd hh:mm:ss */
        @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
        DATETIME_SLUSH,
        /** yyyy-mm-dd hh-mm-ss */
        @DateTimeFormat("yyyy-MM-dd HH-mm-ss")
        DATETIME_CORON,
        /** yyyymmdd hhmmss */
        @DateTimeFormat("yyyyMMdd HHmmss")
        DATETIME_TYOKKETU,
        /** yyyy/mm/dd hh:mm */
        @DateTimeFormat("yyyy/MM/dd HH:mm")
        DATETIME_SLUSH_HHMM,
        /** yyyy-mm-dd hh-mm */
        @DateTimeFormat("yyyy-MM-dd HH-mm")
        DATETIME_CORON_HHMM,
        /** yyyymmdd hhmm */
        @DateTimeFormat("yyyyMMdd HHmm")
        DATETIME_TYOKKETU_HHMM,
        /** yyyy/mm/dd */
        @DateTimeFormat("yyyy/MM/dd")
        DATE_SLUSH,
        /** yyyy-mm-dd */
        @DateTimeFormat("yyyy-MM-dd")
        DATE_HIHUN,
        /** yyyymmdd */
        @DateTimeFormat("yyyyMMdd")
        DATE_TYOKKETU,
        /** yyyy */
        @DateTimeFormat("yyyy")
        YEAR_SEIREKI,
        /** yy(年の下2桁） */
        @DateTimeFormat("yy")
        YEAR_SEIREKI2KETA,
        /** mm */
        @DateTimeFormat("MM")
        MONTH,
        /** dd */
        @DateTimeFormat("dd")
        DAY,
        /** hh:mm:ss */
        @DateTimeFormat("HH:mm:ss")
        TIME_CORON,
        /** hh-mm-ss */
        @DateTimeFormat("HH-mm-ss")
        TIME_HIHUN,
        /** hhmmss */
        @DateTimeFormat("HHmmss")
        TIME_TYOKKETU,
        /** hh:mm */
        @DateTimeFormat("HH:mm")
        TIME_CORON_HHMM,
        /** hh-mm */
        @DateTimeFormat("HH-mm")
        TIME_HIHUN_HHMM,
        /** hhmm */
        @DateTimeFormat("HHmm")
        TIME_TYOKKETU_HHMM,
        /** hh */
        @DateTimeFormat("HH")
        HOUR,
        /** mm */
        @DateTimeFormat("mm")
        MINUTES,
        /** ss */
        @DateTimeFormat("ss")
        SECONDS;
        public String value() {
            String name;
            try {
                name = (String) this.getClass().getMethod("name").invoke(this);
                DateTimeFormat ant
                    = this.getClass().getField(name)
                        .getAnnotation(DateTimeFormat.class);
                return ant.value();
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException
                    | SecurityException | NoSuchFieldException e) {
            }
            return "";

        }
    }
}
