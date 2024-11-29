package jp.groupsession.v2.restapi.filter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(ActionFilter.Holder.class)
public @interface ActionFilter {
    /** @return 実行メソッド名 */
    String runner() default "doFilter";
    /** @return 対象URL */
    String path() default "";
    /** @return 対象HTTPメソッド */
    String method() default "";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @interface Holder {
        ActionFilter[] value();
    }
}
