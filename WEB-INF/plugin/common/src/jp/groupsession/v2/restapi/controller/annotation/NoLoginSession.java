package jp.groupsession.v2.restapi.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
/**
 * <br>[機  能] アクション実行時、ログインセッションによる認証を行わない
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public @interface NoLoginSession {

}
