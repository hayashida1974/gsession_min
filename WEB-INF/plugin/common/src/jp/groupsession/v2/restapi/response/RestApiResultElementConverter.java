package jp.groupsession.v2.restapi.response;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.enums.ValuedEnum;

import jp.co.sjts.util.IEnumMsgkeyValue;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.response.annotation.ChildElementName;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;

public class RestApiResultElementConverter {
    /** レスポンスに含めるかを判定するメソッド名 先頭部分 */
    private static final String CHECK_METHOD_NAME = "canDisplay";

   /**
    *
    * <br>[機  能] JavaBeanをエレメント化する
    * <br>[解  説]
    * <br>[備  考]
    * @param bean
    * @param respDef
    * @return Element
    */
   @SuppressWarnings("unchecked")
private static ResultElement __createElement(Object bean, String[] respDef) {
       ResultElement ret = new ResultElement("result");
       final class ReqMdl {
            ResultElement desc;
            Object obj;
            String[] respDef;
            List<Annotation> ants;
            public ReqMdl(ResultElement desc, Object obj, String[] respDef, List<Annotation> ants) {
                super();
                this.desc = desc;
                this.obj = obj;
                this.respDef = respDef;
                this.ants = ants;
            }
       }
       Set<ReqMdl> reqSet = Set.of(
               new ReqMdl(
                       ret,
                       bean,
                       respDef,
                       null));
       while (reqSet.size() > 0) {
           Set<ReqMdl> next = new HashSet<>();
           for (ReqMdl req : reqSet) {
               if (req.obj == null) {
                   continue;
               }
               if (Enum.class.isAssignableFrom(req.obj.getClass())) {
                   try {

                       if (IEnumMsgkeyValue.class.isAssignableFrom(req.obj.getClass())) {
                           Method m;
                           m = req.obj.getClass().getMethod("getValue");
                           req.desc.addContent(String.valueOf(m.invoke(req.obj)));
                           continue;
                       }
                       if (ValuedEnum.class.isAssignableFrom(req.obj.getClass())) {
                           Method m = req.obj.getClass().getMethod("getValue");
                           req.desc.addContent(String.valueOf(m.invoke(req.obj)));
                           continue;
                       }
                       req.desc.addContent(
                               req.obj.toString());
                   } catch (IllegalAccessException
                           | IllegalArgumentException
                           | InvocationTargetException
                           | NoSuchMethodException | SecurityException e) {
                       e.printStackTrace();
                   }

                   continue;
               }
               if (UDate.class.isAssignableFrom(req.obj.getClass())) {

                   String format =
                           Optional.ofNullable(req.ants)
                           .stream()
                           .flatMap(list -> list.stream())
                           .filter(a -> (a instanceof UDateFormat))
                           .findFirst()
                           .map(ant -> ((UDateFormat) ant).value())
                           .orElse(UDateFormat.Format.DATETIME_SLUSH)
                           .value();

                   SimpleDateFormat sdf = new SimpleDateFormat(format);
                   req.desc.addContent(sdf.format(((UDate) req.obj).toJavaUtilDate()));
                   continue;
               }
               if (req.obj.getClass().isPrimitive()
                           || Number.class.isAssignableFrom(req.obj.getClass())
                           || String.class.isAssignableFrom(req.obj.getClass())) {
                   req.desc.addContent(String.valueOf(req.obj));
                   continue;
               }



               if (req.respDef == null) {
                   req.respDef = Optional.ofNullable(
                               req.obj.getClass().getAnnotation(ResponceModel.class))
                               .map(a -> a.targetField())
                               .orElse(null);

               }
               LinkedHashMap<String, ResultElement> map = new LinkedHashMap<>();
               PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(req.obj);
                if (req.respDef != null) {
                    Stream.of(req.respDef)
                        .filter(key -> !Objects.equals(key, "class"))
                        .filter(key -> __isResult(req.obj, key))
                        .forEach(key -> map.put(key, new ResultElement(key)));
                } else {
                    List<String> fieldList = Stream.of(req.obj.getClass().getDeclaredFields())
                                                .map(f -> f.getName().replaceAll("_", ""))
                                                .filter(f -> !Objects.equals(f, "class"))
                                                .filter(f -> __isResult(req.obj, f))
                                                .collect(Collectors.toList());
                    List<String> propertyList = Stream.of(pds)
                            .map(f -> f.getName())
                            .filter(f -> !Objects.equals(f, "class"))
                            .filter(f -> __isResult(req.obj, f))
                            .collect(Collectors.toList());

                    for (String name : fieldList) {
                        if (propertyList.contains(name)) {
                            map.put(name, new ResultElement(name));
                            propertyList.remove(name);
                        }
                    }
                    for (String name : propertyList) {
                        map.put(name, new ResultElement(name));
                    }
                }


               for (PropertyDescriptor pd : pds) {
                   // 出力対象外
                   if (!map.containsKey(pd.getName())) {
                       continue;
                   }
                   // ゲッターがない
                   if (pd.getReadMethod() == null) {
                       continue;
                   }
                   ResultElement elm = map.get(pd.getName());
                   Class<?> type = pd.getPropertyType();
                   List<Annotation> ants =  __fieldAnnotations(req.obj.getClass(), pd);
                   ResponceModel antRespDef = ants
                       .stream()
                       .filter(a -> (a instanceof ResponceModel))
                       .map(a -> (ResponceModel) a)
                       .findAny()
                       .orElse(null);
                   if (antRespDef == null) {
                       antRespDef = type.getAnnotation(ResponceModel.class);
                   }

                   try {
                       if (type.isArray()
                               || Collection.class.isAssignableFrom(type)) {

                           elm.setName(pd.getName());

                           List<Object> list;
                           if (type.isArray()) {

                               list = Stream.of(
                                       Optional.ofNullable(
                                               PropertyUtils.getProperty(req.obj, pd.getName()))
                                           .map(arg -> (Object[]) arg)
                                           .orElse(new Object[0]))
                                       .collect(Collectors.toList());
                           } else  {
                               list = Optional.ofNullable(
                                       PropertyUtils.getProperty(req.obj, pd.getName()))
                               .map(arg -> (Collection<Object>) arg)
                               .orElse(List.of())
                               .stream()
                               .collect(Collectors.toList());
                           }
                           String chldname = ants
                                   .stream()
                                   .filter(a -> (a instanceof ChildElementName))
                                   .findAny()
                                   .map(a -> ((ChildElementName) a).value())
                                   .orElse(pd.getName());

                           for (Object obj : list) {

                               ResultElement child = new ResultElement(chldname);
                               elm.addContent(child);
                               next.add(new ReqMdl(child,
                                       obj,
                                       Optional.ofNullable(antRespDef)
                                           .map(a -> a.targetField())
                                           .orElse(null),
                                       ants
                                   ));

                           }
                           continue;
                       }
                       if (Map.class.isAssignableFrom(type)) {

                           Map<Object, Object> obj =
                                   Optional.ofNullable(PropertyUtils.getProperty(
                                           req.obj, pd.getName()))
                                       .map(arg -> (Map<Object, Object>) arg)
                                       .orElse(Map.of());
                           for (Entry<Object, Object> entry : obj.entrySet()) {
                               ResultElement child =
                                       new ResultElement(String.valueOf(entry.getKey()));
                               elm.addContent(child);

                               next.add(new ReqMdl(child,
                                       entry.getValue(),
                                       Optional.ofNullable(antRespDef)
                                       .map(a -> a.targetField())
                                       .orElse(null),
                                       ants
                                   ));

                           }
                           continue;
                       }
                       next.add(new ReqMdl(elm,
                                   PropertyUtils.getProperty(req.obj, pd.getName()),
                                   Optional.ofNullable(antRespDef)
                                   .map(a -> a.targetField())
                                   .orElse(null),
                                   ants
                               ));

                   } catch (IllegalAccessException | InvocationTargetException
                        | NoSuchMethodException e) {
                       throw new RuntimeException("インスタンス生成失敗", e);

                   }

               }
               map.entrySet()
                   .stream()
                   .forEach(entry -> {
                       req.desc.addContent(entry.getValue());
                   });
           }

           reqSet = next;
       }

       return ret;
   }
   /**
    *
    * <br>[機  能] 指定プロパティ名からフィールドまたはゲッターにつけられたアノテーションを取り出す
    * <br>[解  説]
    * <br>[備  考]
    * @param bodyClz モデルクラス
    * @param pd プロパティディスクリプター
    * @return アノテーション
    */
   private static List<Annotation> __fieldAnnotations(Class<? extends Object> bodyClz,
           PropertyDescriptor pd) {
       Class<? extends Object> clz = bodyClz;
       List<Annotation> ret = new ArrayList<>();
       while (clz != null && clz != Object.class) {
           ret.addAll(Stream.of(clz.getDeclaredFields())
                   .filter(f -> Objects.equals(f.getName().replaceAll("_", ""), pd.getName()))
                   .flatMap(f -> Stream.of(f.getDeclaredAnnotations()))
                   .collect(Collectors.toList()));
           clz = clz.getSuperclass();
       }
       Method m = pd.getReadMethod();
       if (m == null) {
           return ret;
       }
       ret.addAll(
               Stream.of(m.getAnnotations())
               .collect(Collectors.toList()));
       return ret;
   }
   /**
    * <br>[機  能] 指定プロパティ名から、レスポンスに含まれるかどうかを返します。
    * <br>[解  説] 引数のプロパティ名をレスポンスに含めるか判定するメソッドを呼び出します。
    * <br>[備  考] 引数で指定したオブジェクトに判定メソッドが用意されていない場合は、trueを返します。
    * @param obj 対象オブジェクト
    * @param propertyName プロパティ名
    * @return true:レスポンスとして表示する, false:レスポンスとして表示しない
    */
    private static boolean __isResult(Object obj, String propertyName) {
        String checkMethodName = __getCheckMethod(propertyName);

        try {
            Method checkMethod = obj.getClass().getDeclaredMethod(checkMethodName);
            Object flg = checkMethod.invoke(obj);
            return (boolean) flg;
        } catch (IllegalAccessException |  IllegalArgumentException
            | InvocationTargetException | NoSuchMethodException e) {
            //レスポンスに含めるか判定するメソッドが存在しない場合は例外が発生するが、エラーではないため何もしない
        }

        //レスポンスに含めるか判定するメソッドが用意されていないため、レスポンスに表示する
        return true;
    }

    /**
    * <br>[機  能] プロパティ名から、結果として含めるかどうかを判定するメソッドの名前を取得します。
    * <br>[解  説]
    * <br>[備  考]
    * @param propertyName プロパティ名
    * @return 結果として含めるかを判定するメソッド名
    */
    private static String __getCheckMethod(String propertyName) {

        if (propertyName == null || propertyName.length() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(CHECK_METHOD_NAME);
        sb.append(propertyName.substring(0, 1).toUpperCase());
        sb.append(propertyName.substring(1));

        return sb.toString();
    }

}
