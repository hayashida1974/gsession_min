package jp.groupsession.v2.restapi.parameter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * <br>[機  能] パラメータバリデートアノテーション
 * <br>[解  説] GSテキストエリア相当のバリデート
 * <br>[備  考]
 *
 * @author JTS
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TextArea {
}