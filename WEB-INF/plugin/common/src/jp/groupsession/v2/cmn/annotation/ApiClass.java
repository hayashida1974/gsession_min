package jp.groupsession.v2.cmn.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.TYPE
})
public @interface ApiClass {
    String id();
    String plugin();
    String name();
    String url();
    String reqtype() default "POST";
    boolean referenceFlg() default true;
}
