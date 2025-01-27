package jp.groupsession.v2.restapi.parameter.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 *
 * <br>[機  能] RestApiパラメータモデル注釈
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ParamModel {

}
