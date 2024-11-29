package jp.groupsession.v2.restapi.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
/**
 * <br>[機  能] アクションメソッドにつける注釈
 * <br>[解  説] 
 * <br>[備  考] 複数メソッドがある場合はactionで識別
 *
 * @author JTS
 */
public @interface Delete {
}
