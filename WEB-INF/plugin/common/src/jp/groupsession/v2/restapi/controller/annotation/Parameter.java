package jp.groupsession.v2.restapi.controller.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(Parameter.Holder.class)
/**
 * <br>[機  能] アクションメソッドにつける注釈
 * <br>[解  説]
 * <br>[備  考] 複数メソッドがある場合の識別用
 *
 * @author JTS
 */
public @interface Parameter {
    /**
     *
     * <br>[機  能] メソッド識別用情報 パスパラメータ
     * <br>[解  説]
     * <br>[備  考]
     * @return アクションパス
     */
    String value();
    /**
     *
     * <br>[機  能] メソッド識別用情報 パスパラメータ名
     * <br>[解  説]
     * <br>[備  考]
     * @return アクションパス
     */
    String name() default "parameter";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface Holder {
        Parameter[] value();
    }
}
