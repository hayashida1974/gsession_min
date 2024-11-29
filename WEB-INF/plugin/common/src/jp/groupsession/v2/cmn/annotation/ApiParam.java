package jp.groupsession.v2.cmn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.FIELD,
})
@Inherited
public @interface ApiParam {
    String name();
    String viewName();
    boolean required() default true;
    boolean confRequired() default false;
    int dataType() default 0;
}
