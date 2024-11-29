package jp.groupsession.v2.restapi.response.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *
 * <br>[機  能] レスポンス定義注釈
 * <br>[解  説] レスポンスに設定するフィールドを指定する
 * <br>[備  考]
 *
 * @author JTS
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD})
public @interface ResponceModel {
    String[] targetField() default {};
}
