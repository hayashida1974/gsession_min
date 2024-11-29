package jp.groupsession.v2.restapi.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Repeatable(Plugin.Holder.class)
@Inherited
/**
 * <br>[機  能] APIの注釈
 * <br>[解  説] どのプラグイン権限が必要かを設定する
 * <br>[備  考]
 *
 * @author JTS
 */
public @interface Plugin {
    String value();
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    @Inherited
    @interface Holder {
        Plugin[] value();
    }}
