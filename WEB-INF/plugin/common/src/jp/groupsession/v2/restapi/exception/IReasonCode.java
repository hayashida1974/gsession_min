package jp.groupsession.v2.restapi.exception;
/**
 *
 * <br>[機  能] 理由コードインタフェース
 * <br>[解  説] 独自定義用
 * <br>[備  考]
 *
 * @author JTS
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;

public interface IReasonCode {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface ReasonCodeString {
        /** @return 値 */
        String value();
    }
    public default String reasonCodeText() {
        if (this.getClass().isEnum()) {
            String name;
            try {
                name = (String) this.getClass().getMethod("name").invoke(this);
                ReasonCodeString ant
                    = this.getClass().getField(name)
                        .getAnnotation(ReasonCodeString.class);
                return ant.value();
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException
                    | SecurityException | NoSuchFieldException e) {
            }

        }
        return "";
    }
}
