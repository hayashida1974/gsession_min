package jp.groupsession.v2.restapi.response;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.enums.ValuedEnum;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import jp.co.sjts.util.CloneableUtil;
import jp.co.sjts.util.IEnumMsgkeyValue;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.json.util.JSONUtils;
import jp.co.sjts.util.json.xml.XMLSerializer;
import jp.co.sjts.util.log.LogBlock;
import jp.groupsession.v2.cmn.biz.apiconnect.EnumContentType;
import jp.groupsession.v2.cmn.biz.apiconnect.EnumContentType.EnumContentTypeGroup;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.parameter.annotation.UDateFormat;
import jp.groupsession.v2.restapi.response.annotation.ChildElementName;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;

/**
 * <br>[機  能] RestApiのレスポンスを書き込むビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiResponseWriter {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RestApiResponseWriter.class);
    /** パラメータオブジェクト*/
    private final Builder param__;
    /** レスポンスに含めるかを判定するメソッド名 先頭部分 */
    private static final String CHECK_METHOD_NAME = "canDisplay";
    /**
     *
     * <br>[機  能] RestApiResponceWriter ビルダー
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class Builder implements Cloneable {
        /** 書き込み先レスポンス*/
        HttpServletResponse res__;
        /** リクエスト*/
        HttpServletRequest req__;
        /** 書き込み内容*/
        List<ResultElement> resultList__ = new ArrayList<>();
        /** RestApiコンテキスト*/
        RestApiContext ctx__;

        /** 結果追加 属性*/
        Map<String, String> attributes__ = new HashMap<>();

        private Builder() {
        }
        /**
         * <p>resultList をセットします。
         * @param resultList resultList
         * @see jp.groupsession.v2.restapi.response.RestApiResponseWriter.Builder#resultList__
         * @return this
         */
        public Builder setResultList(List<ResultElement> resultList) {
            resultList__.addAll(resultList);
            return this;
        }
        /**
         * <p>resultList に追加します。
         * @param element result
         * @see jp.groupsession.v2.restapi.response.RestApiResponseWriter.Builder#resultList__
         * @return this
         */
        public Builder addResult(ResultElement element) {
            resultList__.add(element);
            return this;
        }
        /**
         * <p>resultList に追加します。
         * @param bean result
         * @see jp.groupsession.v2.restapi.response.RestApiResponseWriter.Builder#resultList__
         * @return this
         */
        public Builder addResult(Object bean) {

            return addResult(bean, null);
        }
        /**
         * <p>resultList に追加します。
         * @param beans result list
         * @see jp.groupsession.v2.restapi.response.RestApiResponseWriter.Builder#resultList__
         * @return this
         */
        public Builder addResultList(Collection<?> beans) {
            return addResultList(beans, null);
        }

        /**
         * <p>resultList に追加します。
         * @param bean result
         * @param respDef レスポンス対象フィールド名リスト
         * @see jp.groupsession.v2.restapi.response.RestApiResponseWriter.Builder#resultList__
         * @return this
         */
        public Builder addResult(Object bean, String[] respDef) {

            resultList__.add(__createElement(bean, respDef));
            return this;
        }
        /**
         * <p>resultList に追加します。
         * @param beans result list
         * @param respDef レスポンス対象フィールド名リスト
         * @see jp.groupsession.v2.restapi.response.RestApiResponseWriter.Builder#resultList__
         * @return this
         */
        public Builder addResultList(Collection<?> beans, String[] respDef) {
            if (beans == null) {
                return this;
            }
            beans.stream()
                .forEach(bean -> {
                    resultList__.add(__createElement(bean, null));
                });
            return this;
        }

        /**
         * <p>max をセットします。
         * @param max max
         * @see jp.groupsession.v2.restapi.response.RestApiResponseWriter.Builder#max__
         * @return this
         */
        public Builder setMax(Integer max) {
            attributes__.put("max", String.valueOf(max));
            return this;
        }
        /**
         * 追加属性を設定する
         * @param key 属性名
         * @param value 属性値
         * @return this
         */
        public Builder setAttribute(String key, String value) {
            attributes__.put(key, value);
            return this;
        }

        @Override
        public Builder clone() {
            Builder ret = new Builder();
            CloneableUtil.copyField(ret, this);
            return ret;
        }
        /**
         *
         * <br>[機  能] RestApiResponceWriter インスタンス生成
         * <br>[解  説]
         * <br>[備  考]
         * @return RestApiResponceWriter
         */
        public RestApiResponseWriter build() {
            return new RestApiResponseWriter(this.clone());
        }
        /**
         *
         * <br>[機  能] レスポンス書き込み処理
         * <br>[解  説]
         * <br>[備  考]
         */
        void _writeResponse() {
            try (LogBlock lb =
                    LogBlock.builder("RestApi outputResponce", log__)
                    .build()) {
                //ルートエレメントResultSet
                ResultElement resultSet = new ResultElement("resultArray");
                resultSet.setAttribute("url", ctx__.getApiUrl());

                attributes__.entrySet().stream()
                        .forEach(entry -> {
                            resultSet.setAttribute(entry.getKey(), entry.getValue());
                        });
                resultList__.stream()
                .forEach(element -> {
                    element.setName("result");
                    resultSet.addContent(element);
                });
                _writeElement(resultSet);
            }

        }
        /**
         *
         * <br>[機  能] 書き込み処理 共通
         * <br>[解  説]
         * <br>[備  考]
         * @param resultSet
         * @throws IOException
         */
        void _writeElement(ResultElement resultSet) {
            try (OutputStream out = res__.getOutputStream();
                 OutputStreamWriter outWriter = new OutputStreamWriter(out, "UTF-8");
                    ) {

                XMLOutputter xout = new XMLOutputter();
                //フォーマットを設定する。
                Format format = xout.getFormat();
                format.setEncoding("UTF-8");
                format.setLineSeparator("\r\n");
                format.setIndent("    ");
                format.setOmitDeclaration(false);
                format.setOmitEncoding(false);
                format.setExpandEmptyElements(true);
                EnumContentType type = EnumContentType.APPLICATION_XML;
                if (ctx__ != null) {
                    type = ctx__.getAcceptType();
                } else if (req__ != null) {
                    type = EnumSet.allOf(EnumContentType.class)
                                .stream()
                                .filter(en -> Objects.equals(
                                        en.getLabel(),
                                        req__.getHeader("Accept")
                                        ))
                                .findAny()
                                .orElse(EnumContentType.APPLICATION_JSON);
                }
                Document doc = new Document()
                        .setContent(resultSet.toElement());

                if (type.getGroup() == EnumContentTypeGroup.XML) {

                    res__.setContentType(
                            type.getLabel() + "; charset=" + "UTF-8");


                    xout.output(doc, out);
                    return;
                }
                if (type.getGroup() == EnumContentTypeGroup.JSON) {
                    //jsonの場合
                    String strXml = xout.outputString(doc);
                    String strJSON = "";
                    try {
//                    //net.sf.json.xml.XMLSerializer#readで読み込み、JSONオブジェクトを作成
                        XMLSerializer xmlSerializer = new XMLSerializer();
                        JSON json = xmlSerializer.read(strXml);
                        JSONObject root = JSONObject.fromObject(json);
                        if (root.opt("result") != null && !JSONUtils.isArray(root.opt("result"))) {
                            JSONArray result = new JSONArray();
                            result.add(root.opt("result"));
                            root.elementOpt("result", result);
                        }
                        if (root.opt("error") != null && !JSONUtils.isArray(root.opt("error"))) {
                            JSONArray result = new JSONArray();
                            result.add(root.opt("error"));
                            root.elementOpt("error", result);
                        }

                        strJSON = ResultElement.convert(root).toString();
                    } catch (JSONException je) {
                        strJSON = "failed toJSON ";
                    }
                    //レスポンスを返す
                    res__.setContentType(type.getLabel());
                    outWriter.write(strJSON);
                    return;
                }
            } catch (Exception e) {
                //レスポンスアウトプット時のエクセプションは処理しない
                log__.debug(e);
            }
        }

    }

    /**
     *
     * <br>[機  能] RestApiResponceWriter ビルダー
     * <br>[解  説] エラーメッセージ用
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class ErrorResponceBuilder extends Builder implements Cloneable {
        /**
         *
         * <br>[機  能] RestApiContext取得前用の代替にリクエストをセットする
         * <br>[解  説]
         * <br>[備  考]
         * @param req サーブレットリクエスト
         * @return this
         */
        public ErrorResponceBuilder setRequest(HttpServletRequest req) {
            req__ = req;
            return this;
        }
        /**
         *
         * <br>[機  能] RestApiContextをセットする
         * <br>[解  説]
         * <br>[備  考]
         * @param ctx RESTAPIコンテキスト
         * @return this
         */
        public ErrorResponceBuilder setRestApiContext(RestApiContext ctx) {
            ctx__ = ctx;
            return this;
        }
        private ErrorResponceBuilder() {
            super();
        }
        /**
         * @return cloneを作成する。
        */
        public ErrorResponceBuilder clone() {
            ErrorResponceBuilder ret = new ErrorResponceBuilder();
            CloneableUtil.copyField(ret, this);
            return ret;
        }
        /**
         *
         * <br>[機  能] RestApiResponceWriter インスタンス生成
         * <br>[解  説]
         * <br>[備  考]
         * @return RestApiResponceWriter
         */
        public RestApiResponseWriter build() {
            return new RestApiResponseWriter(this.clone());
        }
        @Override
        void _writeResponse() {
            try (LogBlock lb =
                    LogBlock.builder("RestApi outputErrorResponce", log__)
                    .build()) {
                //ルートエレメントResultSet
                ResultElement resultSet = new ResultElement("errorArray");
                if (ctx__ != null) {
                    resultSet.setAttribute("url", ctx__.getApiUrl());
                } else if (req__ != null) {
                    resultSet.setAttribute("url", req__.getRequestURI());
                }
                resultList__.stream()
                .forEach(element -> {
                    element.setName("error");
                    resultSet.addContent(element);
                });
                _writeElement(resultSet);
            }
        }

    }
    /**
    *
    * <br>[機  能] RestApiResponceWriter ビルダー
    * <br>[解  説] 警告メッセージ用
    * <br>[備  考]
    *
    * @author JTS
    */
   public static class WarnResponceBuilder extends Builder implements Cloneable {
       /**
        *
        * <br>[機  能] RestApiContext取得前用の代替にリクエストをセットする
        * <br>[解  説]
        * <br>[備  考]
        * @param req サーブレットリクエスト
        * @return this
        */
       public WarnResponceBuilder setRequest(HttpServletRequest req) {
           req__ = req;
           return this;
       }
       /**
        *
        * <br>[機  能] RestApiContextをセットする
        * <br>[解  説]
        * <br>[備  考]
        * @param ctx RESTAPIコンテキスト
        * @return this
        */
       public WarnResponceBuilder setRestApiContext(RestApiContext ctx) {
           ctx__ = ctx;
           return this;
       }
       private WarnResponceBuilder() {
           super();
       }
       /**
        * @return cloneを作成する。
        */
       public WarnResponceBuilder clone() {
           WarnResponceBuilder ret = new WarnResponceBuilder();
           CloneableUtil.copyField(ret, this);
           return ret;
       }
       /**
        *
        * <br>[機  能] RestApiResponceWriter インスタンス生成
        * <br>[解  説]
        * <br>[備  考]
        * @return RestApiResponceWriter
        */
       public RestApiResponseWriter build() {
           return new RestApiResponseWriter(this.clone());
       }
       @Override
       void _writeResponse() {
           try (LogBlock lb =
                   LogBlock.builder("RestApi outputWarnResponce", log__)
                   .build()) {
               //ルートエレメントResultSet
               ResultElement resultSet = new ResultElement("warnArray");
               if (ctx__ != null) {
                   resultSet.setAttribute("url", ctx__.getApiUrl());
               } else if (req__ != null) {
                   resultSet.setAttribute("url", req__.getRequestURI());
               }
               resultList__.stream()
               .forEach(element -> {
                   element.setName("warn");
                   resultSet.addContent(element);
               });
               _writeElement(resultSet);
           }
       }

   }

    /**
     * コンストラクタ
     * @param param パラメータモデル
     */
    private RestApiResponseWriter(Builder param) {
        param__ = param;
    }
    /**
     *
     * <br>[機  能] レスポンスへの書き込みを実行する
     * <br>[解  説]
     * <br>[備  考]
     */
    public void execute() {

        param__._writeResponse();
    }
    /**
     *
     * <br>[機  能] ビルダ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param resp
     * @param ctx RESTAPIコンテキスト
     * @return ビルダー
     */
    public static Builder builder(HttpServletResponse resp, RestApiContext ctx) {
        Builder ret = new Builder();
        ret.res__ = resp;
        ret.ctx__ = ctx;
        return ret;
    }
    /**
    *
    * <br>[機  能] ビルダ取得
    * <br>[解  説] エラー用
    * <br>[備  考]
    * @param resp
    * @return ビルダー
    */
   public static ErrorResponceBuilder builderError(HttpServletResponse resp) {
       ErrorResponceBuilder ret = new ErrorResponceBuilder();
       ret.res__ = resp;
       return ret;
   }
   /**
   *
   * <br>[機  能] ビルダ取得
   * <br>[解  説] 警告用
   * <br>[備  考]
   * @param resp
   * @return ビルダー
   */
  public static WarnResponceBuilder builderWarn(HttpServletResponse resp) {
      WarnResponceBuilder ret = new WarnResponceBuilder();
      ret.res__ = resp;
      return ret;
  }

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
