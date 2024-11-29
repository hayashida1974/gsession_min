package jp.co.sjts.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * <br>[機  能] clone 時の フィールドコピー
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CloneableUtil {
    public static void copyField(Object desc, Object src) {
        try {
           Class clz = src.getClass();
           while (clz != null
                   && clz != Object.class) {
               // メンバ変数をすべてコピーする。(privateタイプ含む)
               for (Field field : clz.getDeclaredFields()) {
                   if (Modifier.isStatic(field.getModifiers())) {
                       continue;
                   }
                   switch (field.getName()) {
                   case "class":
                       continue;
                   default:
                   }
                   boolean seq = field.canAccess(src);
                   field.setAccessible(true);
                   Object data = field.get(src);
                   field.set(desc, data);
                   field.setAccessible(seq);
               }
               clz = clz.getSuperclass();
           }
        } catch (IllegalAccessException 
                | IllegalArgumentException 
                | SecurityException  e) {
            throw new RuntimeException("フィールドコピーに失敗", e);
        }
    }

}
