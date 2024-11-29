package jp.groupsession.v2.cmn.ui.parameter;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.PropertyUtils;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.json.util.JSONUtils;

/**
 *
 * <br>[機  能] パラメータオブジェクトをラップし複数のパラメータ型に対応する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ParameterObject {
    /** パラメータオブジェクト JSON以外*/
    private Object instance__;
    /** パラメータオブジェクト JSON*/
    private JSONObject instanceJson__;

    /**
     * コンストラクタ
     * @param instance
     */
    public ParameterObject(Object instance) {
        super();
        if (instance instanceof Map) {
            instanceJson__ = JSONObject.fromObject(instance);
        } else {
            instance__ = instance;
        }
    }

    /**
     *
     * <br>[機  能] 値の取得
     * <br>[解  説]
     * <br>[備  考]
     * @param key
     * @return 値 オブジェクト
     */
    public Object get(String key) {

        if (instanceJson__ != null) {
            return instanceJson__.get(key);
        } else {
            Object val;
            try {
                val = PropertyUtils.getNestedProperty(instance__, key);
            } catch (IllegalAccessException | InvocationTargetException
                    | NoSuchMethodException e) {
                val = null;
            }
            return val;
        }

    }
    /**
    *
    * <br>[機  能] 値の取得
    * <br>[解  説]
    * <br>[備  考]
    * @param <T> 返り値型指定 対応可能 String, String[], Integer, Long, Object, Object[]
    * @param key
    * @param clz 返り値型指定
    * @return 値 オブジェクト
    */
   @SuppressWarnings("unchecked")
public <T> T get(String key, Class<T> clz) {
       Object val = null;
       if (StringUtil.isNullZeroString(key)) {
           val = null;
       } else if (instanceJson__ != null) {
           val =  instanceJson__.get(key);
       } else {
           try {
               val = PropertyUtils.getNestedProperty(instance__, key);
           } catch (IllegalAccessException | InvocationTargetException
                   | NoSuchMethodException e) {
               val = null;
           }
       }
       if (clz.isArray()) {
           Class<?> type = clz.getComponentType();
           if (JSONUtils.isArray(val)) {
               JSONArray arr = JSONArray.fromObject(val);
               Object[] ret = (Object[]) Array.newInstance(type, arr.size());
               for (int i = 0; i < arr.size(); i++) {
                   if (String.class.isAssignableFrom(type)) {
                       ret[i] = arr.optString(i);
                   } else {
                       ret[i] = arr.opt(i);
                   }
               }
               return (T) ret;
           }
           if (JSONUtils.isObject(type) || val == null) {
               return (T) Array.newInstance(type, 0);
           }

           Object[] ret = (Object[]) Array.newInstance(type, 1);
           ret[0] = type;
           return (T) ret;
       }
       if (clz.isPrimitive()) {
           if (JSONUtils.isArray(val)) {
               JSONArray arr = JSONArray.fromObject(val);
               if  (clz.isAssignableFrom(long.class)) {
                   if (arr.size() == 0) {
                       return (T) Long.valueOf(0);
                   }
                   return (T) Long.valueOf(arr.optInt(0));
               }
               if  (clz.isAssignableFrom(int.class)) {
                   if (arr.size() == 0) {
                       return (T) Integer.valueOf(0);
                   }
                   return (T) Integer.valueOf(arr.optInt(0));
               }

           }
           if (JSONUtils.isNumber(val)
                   || ValidateUtil.isNumberHaifun(String.valueOf(val))) {
               if  (clz.isAssignableFrom(long.class)) {
                   return (T) Long.valueOf(String.valueOf(val));
               }
               if  (clz.isAssignableFrom(int.class)) {
                   return (T) Integer.valueOf(String.valueOf(val));
               }
           }
           return (T) Integer.valueOf(0);
       }
       if (JSONUtils.isArray(val)) {
           JSONArray arr = JSONArray.fromObject(val);
           if (arr.size() == 0) {
               return (T) "";
           }
           return (T) arr.optString(0);
       }
       if (JSONUtils.isString(val)) {
           return (T) val;
       }
       return (T) String.valueOf(val);

   }

    /**
     *
     * <br>[機  能] パラメータキー 一括取得
     * <br>[解  説]
     * <br>[備  考]
     * @return パラメータキーセット
     */
    public Set<String> keySet() {
        if (instanceJson__ != null) {
            Set<String> ret = new HashSet<>();
            for (Object keyObj:instanceJson__.keySet()) {
                ret.add(String.valueOf(keyObj));
            }
            return ret;
        }
        return Stream.of(PropertyUtils.getPropertyDescriptors(instance__))
            .map(pd -> pd.getName())
            .filter(key -> Objects.equals(key, "class"))
            .collect(Collectors.toSet());
    }
    /**
     *
     * <br>[機  能] ParameterObject のベースのクラス型名を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return クラス名
     */
    public String getInstanceClsName() {
        if (instanceJson__ != null) {
            return instanceJson__.getClass().getName();
        }
        if (instance__ != null) {
            return instance__.getClass().getName();
        }

        return null;
    }
    /**
     *
     * <br>[機  能] ParameterObjectのベースインスタンスを返す
     * <br>[解  説] 指定の型が一致しない場合 nullが返る
     * <br>[備  考]
     * @param <T>
     * @param clz 返り値型指定
     * @return ParameterObjectのベースインスタンス
     */
    @SuppressWarnings("unchecked")
    public <T> T getInstance(Class<T> clz) {
        if (instance__ != null
                && instance__.getClass().isAssignableFrom(clz)) {
            return (T) instance__;
        }
        return null;
    }
    /**
     *
     * <br>[機  能] ParameterObjectのベースインスタンスを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return ParameterObjectのベースインスタンス
     */
    public Object getInstance() {
        return instance__;
    }

}
