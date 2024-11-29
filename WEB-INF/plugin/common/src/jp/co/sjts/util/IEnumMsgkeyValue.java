package jp.co.sjts.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;

/**
 *
 * <br>[機  能] メッセージキーと値をセットで持つ定数型のインタフェース
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public interface IEnumMsgkeyValue {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    public @interface MsgkeyValue {
        /** @return 値 */
        int value();
        /** @return メッセージキー */
        String msgKey();
    }
    /**
     * <p>value を取得します。
     * @return value
     */
    int getValue();
    /**
     * <p>value を取得します。
     * @param self
     * @return value
     */
    public static int getValue(IEnumMsgkeyValue self) {
        if (self.getClass().isEnum()) {
            String name;
            try {
                name = (String) self.getClass().getMethod("name").invoke(self);
                MsgkeyValue ant
                    = self.getClass().getField(name)
                        .getAnnotation(MsgkeyValue.class);
                return ant.value();
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException
                    | SecurityException | NoSuchFieldException e) {
            }

        }
        return 0;
    }
    /**
     * <p>value を取得します。
     * @return value
     */
    String getMsgKey();

    /**
     * <p>label を取得します。
     * @param self
     * @return msgKey
     */
    public static String getMsgKey(IEnumMsgkeyValue self) {
        if (self.getClass().isEnum()) {
            String name;
            try {
                name = (String) self.getClass().getMethod("name").invoke(self);
                MsgkeyValue ant
                    = self.getClass().getField(name)
                        .getAnnotation(MsgkeyValue.class);
                return ant.msgKey();
            } catch (IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException
                    | SecurityException | NoSuchFieldException e) {
            }

        }
        return "";
    }



}
