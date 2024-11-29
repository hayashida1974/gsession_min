package jp.groupsession.v2.restapi.filter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(AddFilter.Holder.class)
public @interface AddFilter {
    /** @return 対象クラス */
    Class<?> value() default Object.class;
    /** @return フィルター */
    String runner() default "doFilter";
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Holder {
        AddFilter[] value();
    }

}
