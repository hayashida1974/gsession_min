package jp.groupsession.v2.restapi.parameter;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.json.util.JSONUtils;
import jp.co.sjts.util.struts.RequestLocal;
import jp.groupsession.v2.cmn.GSValidateUtil;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.EnumError;
import jp.groupsession.v2.restapi.exception.RestApiException;
import jp.groupsession.v2.restapi.exception.RestApiValidateException;
import jp.groupsession.v2.restapi.exception.RestApiValidateExceptionNest;
import jp.groupsession.v2.restapi.parameter.annotation.Default;
import jp.groupsession.v2.restapi.parameter.annotation.MaxArraySize;
import jp.groupsession.v2.restapi.parameter.annotation.MaxLength;
import jp.groupsession.v2.restapi.parameter.annotation.MaxValue;
import jp.groupsession.v2.restapi.parameter.annotation.MinValue;
import jp.groupsession.v2.restapi.parameter.annotation.NoParameter;
import jp.groupsession.v2.restapi.parameter.annotation.NotBlank;
import jp.groupsession.v2.restapi.parameter.annotation.ParamModel;
import jp.groupsession.v2.restapi.parameter.annotation.Selectable;
import jp.groupsession.v2.restapi.parameter.annotation.TextArea;
import jp.groupsession.v2.restapi.parameter.annotation.TextField;
import jp.groupsession.v2.restapi.parameter.annotation.Validator;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] リクエストからパラメータモデルを生成する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 * @param <T>
 */
public class ParamModelCreator<T> {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(ParamModelCreator.class);

    /** クラス定義 */
    private final Class<T> paramClz__;
    /** RESTAPIコンテキスト*/
    private final RestApiContext ctx__;
    /** パラメータフォーマットとモデル先フィールドの型変換時のエクセプション */
    private final List<RestApiValidateException> valErrorList__ = new ArrayList<>();
    /** リクエストローカル リクエストボディキャッシュ*/
    private static final Object LOCALKEY_REQUESTBODY = new Object();
    /**
     *
     * コンストラクタ
     * @param paramClz クラス定義
     * @param ctx RESTAPIコンテキスト
     */
    public ParamModelCreator(Class<T> paramClz, RestApiContext ctx) {
        paramClz__ =  paramClz;
        ctx__ = ctx;
    }
    /**
     *
     * <br>[機  能] パラメータモデルを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return パラメータモデル
     */
    public T execute(HttpServletRequest req) {
        T paramMdl;

        String type = Optional.ofNullable(req.getHeader("content-type"))
                  .orElse("");
        if (type.indexOf(";") >= 0) {
            type = type.substring(0, type.indexOf(";"));
        }
        try {
            paramMdl = (T) paramClz__.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("パラメータモデル生成失敗", e);
        }

        switch (type) {
            case "application/json":
                __writeFromJSON(req, paramMdl);
                break;
            default:
                __whiteFromForm(req, paramMdl);
                break;
        }
        if (valErrorList__.size() > 0) {
            throw new RestApiValidateExceptionNest(valErrorList__);
        }

        return paramMdl;

    }
    /**
     *
     * <br>[機  能] リクエストパラメータからパラメータモデルに値をセット
     * <br>[解  説]
     * <br>[備  考] パラメータのコンバートに失敗する場合 RestApiValidateExceptionNestをthrow
     * @param req リクエスト
     * @param desc パラメータモデル
     */
    private void __whiteFromForm(HttpServletRequest req, Object desc) {
        JSONObject obj = JSONObject.fromObject(req.getParameterMap());

        Set<PropertyDescriptor> pdSet =
                Stream.of(PropertyUtils.getPropertyDescriptors(desc.getClass()))
                .filter(discriptor -> !Objects.equals(discriptor.getName(), "class"))
                .filter(pd -> !Objects.equals(pd.getWriteMethod(), null)
                        && !Objects.equals(pd.getReadMethod(), null))
                .collect(Collectors.toSet());
        for (PropertyDescriptor pd : pdSet) {
            //該当のpathパラメータの反映
            Object prop = ctx__.getMap().getProperty(pd.getName());
            if (prop != null) {
                obj.put(pd.getName(), prop);
            }
        }


        __recursibleWrite(desc, obj);
    }
    /**
     *
     * <br>[機  能] リクエストボディのJSONからパラメータモデルに値をセット
     * <br>[解  説]
     * <br>[備  考] パラメータのコンバートに失敗する場合 RestApiValidateExceptionNestをthrow
     * @param req リクエストボディの値は一度しか読み込めないため、キャッシュを使いまわす
     * @param desc パラメータモデル
     */
    private void __writeFromJSON(HttpServletRequest req, Object desc) {
        JSONObject objBody = RequestLocal.get(LOCALKEY_REQUESTBODY, JSONObject.class);
        if (objBody != null) {
            __recursibleWrite(desc, objBody);
            return;
        }
        String line;
        StringBuilder sb = new StringBuilder();
        try {
            while ((line = req.getReader().readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("リクエストボディ取得失敗", e);
        }
        try {
            objBody = JSONObject.fromObject(sb.toString());
        } catch (JSONException e) {
            throw new RestApiValidateException(EnumError.REQ_BODY_FORMAT,
                    "error.input.format.text",
                    "JSON"
                    );
        }
        JSONObject objParam = JSONObject.fromObject(req.getParameterMap());
        objBody.putAll(objParam);
        Set<PropertyDescriptor> pdSet =
                Stream.of(PropertyUtils.getPropertyDescriptors(desc.getClass()))
                .filter(discriptor -> !Objects.equals(discriptor.getName(), "class"))
                .filter(pd -> !Objects.equals(pd.getWriteMethod(), null)
                        && !Objects.equals(pd.getReadMethod(), null))
                .collect(Collectors.toSet());
        for (PropertyDescriptor pd : pdSet) {
            //該当のpathパラメータの反映
            Object prop = ctx__.getMap().getProperty(pd.getName());
            if (prop != null) {
                objBody.put(pd.getName(), prop);
            }
        }

        RequestLocal.put(LOCALKEY_REQUESTBODY, objBody);
        __recursibleWrite(desc, objBody);
    }
        /** パラメータ初期化リクエストクラス*/
    @SuppressWarnings({  "rawtypes" })
    private static class ReqMdl {
        /** パラメータ定義*/
        Class clz;
        /** パラメータアクセス名*/
        String name;
        /** コンバート元データ*/
        Object obj;
        Set <Annotation> ants = new HashSet<>();
        /**
         *
         * コンストラクタ
         * @param arg1 パラメータ定義
         * @param arg2 パラメータアクセス名
         * @param arg3 コンバート元データ
         */
        public ReqMdl(Class arg1, String arg2, Object arg3) {
            this.clz = arg1;
            this.name = arg2;
            this.obj = arg3;

        }
        /**
         *
         * コンストラクタ
         * @param arg1 パラメータ定義
         * @param arg2 パラメータアクセス名
         * @param arg3 コンバート元データ
         * @param arg4 パラメータに設定されたアノテーション
         */
        public ReqMdl(Class arg1, String arg2, Object arg3, Set <Annotation> arg4) {
            this(arg1, arg2, arg3);
            ants.addAll(arg4);
        }
    }

    @SuppressWarnings("unchecked")
    private void __recursibleWrite(Object desc, Object obj) {
        BeanUtilsBean bu = new BeanUtilsBean(
                new ConvertUtilsBean(),     //独自コンバーターを登録する為に新しいインスタンスを生成
                BeanUtilsBean.getInstance().getPropertyUtils() //デフォルトのPropertyUtilsBeanを共用
            );

        Set<ReqMdl> reqSet = Set.of(new ReqMdl(desc.getClass(), "", obj));
        while (reqSet.size() > 0) {
            Stream.Builder<ReqMdl> sb = Stream.builder();

            for (ReqMdl req : reqSet) {

                //配列型への変換
                if (req.clz.isArray()) {
                    @SuppressWarnings("rawtypes")
                    Class clz = req.clz.getComponentType();


                    JSONArray jsonArr;
                    if (JSONUtils.isArray(req.obj)) {
                        jsonArr = JSONArray.fromObject(req.obj);
                    } else {
                        jsonArr = new JSONArray();
                        if (req.obj != null) {
                            jsonArr.add(req.obj);
                        }
                    }
                    //配列の初期化
                    try {
                        bu.setProperty(desc, req.name, Array.newInstance(clz, jsonArr.size()));
                    } catch (InvocationTargetException e) {
                        if (e.getTargetException() instanceof RestApiValidateException) {
                            RestApiValidateException ve =
                                    (RestApiValidateException) e.getTargetException();
                            ve.setParamName(req.name);
                            valErrorList__.add(ve);
                        } else if (e.getTargetException() instanceof RestApiException) {
                            throw (RestApiException) e.getTargetException();
                        } else {
                            throw new RuntimeException("インスタンス生成失敗", e);
                        }
                    } catch (IllegalAccessException
                            | NegativeArraySizeException e) {
                        //セッターがない場合はここでは例外としない
                        log__.debug("対応フィールドがない", e);
                    }
                    if (JSONUtils.isArray(jsonArr)) {
                        for (int i = 0; i < jsonArr.size(); i++) {
                            ReqMdl propReq = new ReqMdl(
                                    clz,
                                    String.format("%s[%d]", req.name, i),
                                    jsonArr.get(i),
                                    req.ants);
                            sb.add(propReq);
                        }
                        //配列サイズ入力チェック
                        __validateArraySize(req, jsonArr.size());
                        continue;
                    }

                    ReqMdl propReq = new ReqMdl(
                            clz,
                            req.name,
                            req.obj,
                            req.ants
                            );
                    sb.add(propReq);
                    continue;
                }
                //Collection型への変換
                if (Collection.class.isAssignableFrom(req.clz)) {
                    @SuppressWarnings("rawtypes")
                    Class clz;
                    try {
                        clz = Class.forName(req.clz.getGenericInterfaces()[0].getTypeName());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException("インスタンス生成失敗", e);
                    }
                    JSONArray jsonArr;
                    if (JSONUtils.isArray(req.obj)) {
                        jsonArr = JSONArray.fromObject(req.obj);
                    } else {
                        jsonArr = new JSONArray();
                        if (req.obj != null) {
                            jsonArr.add(req.obj);
                        }
                    }
                    if (JSONUtils.isArray(jsonArr)) {
                        for (int i = 0; i < jsonArr.size(); i++) {
                            ReqMdl propReq = new ReqMdl(
                                    clz,
                                    String.format("%s[%d]", req.name, i),
                                    jsonArr.get(i),
                                    req.ants
                                    );
                            sb.add(propReq);
                        }
                        //配列サイズ入力チェック
                        __validateArraySize(req, jsonArr.size());

                        continue;
                    }
                    ReqMdl propReq = new ReqMdl(
                            clz,
                            req.name,
                            req.obj,
                            req.ants
                            );
                    sb.add(propReq);
                    continue;
                }
                //Map型への変換
                if (Map.class.isAssignableFrom(req.clz)) {
                    Type[] types = req.clz.getGenericInterfaces();
                    JSONObject jsonObj;
                    try {
                        jsonObj = JSONObject.fromObject(req.obj);
                    } catch (JSONException e) {
                        continue;
                    }
                    for (int i = 0; i < jsonObj.names().size(); i++) {
                        String propName = jsonObj.names().optString(i);
                        Object propObj = jsonObj.opt(propName);
                        ReqMdl propReq;
                        try {
                            propReq = new ReqMdl(
                                    Class.forName(types[1].getTypeName()),
                                    String.format("%s(%s)", req.name, propName),
                                    propObj);
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException("インスタンス生成失敗", e);
                        }
                        sb.add(propReq);
                    }
                    continue;
                }
                //プリミティブ型
                //文字列型
                //UDate型
                //列挙型 への変換
                if ((req.clz.isPrimitive()
                            || Number.class.isAssignableFrom(req.clz)
                            || String.class.isAssignableFrom(req.clz)
                            || UDate.class.isAssignableFrom(req.clz)
                            || Enum.class.isAssignableFrom(req.clz)
                        ) && req.name.length() > 0) {

                    if (JSONUtils.isArray(req.obj)) {
                        JSONArray arr = JSONArray.fromObject(req.obj);
                        if (arr.size() == 0) {
                            req.obj = null;
                        } else {
                            req.obj = arr.opt(0);
                        }
                    }
                    //プロパティへセット
                    if (req.obj == null) {
                        req.obj =
                            req.ants.stream()
                                .filter(ant -> (ant instanceof Default))
                                .map(def -> ((Default) def).value())
                                .findAny()
                                .orElse(null);
                    }
                    if (req.obj == null) {
                        continue;
                    }
                    if (req.obj != null) {
                        req.obj = req.obj.toString();
                    }
                    Set<Annotation> paramValidateAnt =
                            req.ants
                            .stream()
                            .filter(a -> (a instanceof NotBlank)
                                    || (a instanceof MaxLength)
                                    || (a instanceof MinValue)
                                    || (a instanceof MaxValue)
                                    || (a instanceof Selectable)
                                    || (a instanceof TextArea)
                                    || (a instanceof TextField)
                                    )
                            .collect(Collectors.toSet());
                    if (!__validateParam(req.name, req.obj, paramValidateAnt)) {
                        continue;
                    }

                    if ((Number.class.isAssignableFrom(req.clz)
                            || req.clz.isPrimitive()) && boolean.class != req.clz) {
                        if (req.obj != null) {
                            req.obj = req.obj.toString().trim();
                        }
                        //BeanUtilsで自動変換される前に、数値以外の文字列判定を行う
                        if (req.obj != null
                                && !NumberUtils.isNumber(String.valueOf(req.obj))) {
                            RestApiValidateException ve =
                                    new RestApiValidateException(
                                            EnumError.PARAM_FORMAT,
                                            "error.input.number.text", req.name);
                            ve.setParamName(req.name);
                            valErrorList__.add(ve);
                            continue;
                        }
                    }
                    if (boolean.class == req.clz) {
                        if (req.obj != null) {
                            req.obj = req.obj.toString().trim();
                        }
                        if (Stream.of("true", "false")
                                .anyMatch(bstr ->
                                    Objects.equals(
                                            bstr,
                                            String.valueOf(req.obj).trim()))
                                ) {
                            RestApiValidateException ve =
                                    new RestApiValidateException(
                                            EnumError.PARAM_FORMAT,
                                            "error.input.format.text", req.name);
                            ve.setParamName(req.name);
                            valErrorList__.add(ve);
                            continue;

                        }
                    }
                    if (Enum.class.isAssignableFrom(req.clz)) {
                        if (req.obj != null) {
                            req.obj = req.obj.toString().trim();
                        }
                        bu.getConvertUtils().register(
                                new EnumConverter(
                                        req.name
                                ), //独自コンバーターをEnum型への変換として登録
                                req.clz
                            );
                    }
                    if (UDate.class.isAssignableFrom(req.clz)) {
                        if (req.obj != null) {
                            req.obj = req.obj.toString().trim();
                        }
                        bu.getConvertUtils().register(
                                new UDateCoverter(req.name, req.ants), //独自コンバーターをUDate型への変換として登録
                                req.clz
                            );
                    }
                    try {
                        bu.setProperty(desc, req.name, req.obj);
                    } catch (RestApiValidateException e) {
                        RestApiValidateException ve =
                                (RestApiValidateException) e;
                        ve.setMessageResourceIfBlank("error.input.format.text", req.name);
                        ve.setParamName(req.name);
                        valErrorList__.add(ve);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("インスタンス生成失敗", e);
                    } catch (InvocationTargetException e) {
                        if (e.getTargetException() instanceof RestApiValidateException) {
                            RestApiValidateException ve =
                                    (RestApiValidateException) e.getTargetException();
                            ve.setParamName(req.name);
                            valErrorList__.add(ve);
                        } else if (e.getTargetException() instanceof RestApiException) {
                            throw (RestApiException) e.getTargetException();
                        } else {
                            throw new RuntimeException("インスタンス生成失敗", e);
                        }
                    }
                    continue;

                //オブジェクト型
                }
                //モデルの初期化
                try {



                    bu.setProperty(desc, req.name,
                            req.clz.getDeclaredConstructor().newInstance()
                            );
                } catch (InvocationTargetException e) {
                    if (e.getTargetException() instanceof RestApiValidateException) {
                        RestApiValidateException ve =
                                (RestApiValidateException) e.getTargetException();
                        ve.setParamName(req.name);
                        valErrorList__.add(ve);
                    } else if (e.getTargetException() instanceof RestApiException) {
                        throw (RestApiException) e.getTargetException();
                    }
                } catch (IllegalAccessException
                        | NegativeArraySizeException
                        | IllegalArgumentException
                        | InstantiationException
                        | NoSuchMethodException
                        | SecurityException e) {
                    //セッターがない場合はここでは例外としない
                    log__.debug("対応フィールドがない", e);

                }


                Set<PropertyDescriptor> pdSet =
                        Stream.of(PropertyUtils.getPropertyDescriptors(req.clz))
                        .filter(pd -> !Objects.equals(pd.getName(), "class"))
                        .filter(pd -> !Objects.equals(pd.getWriteMethod(), null)
                                                && !Objects.equals(pd.getReadMethod(), null))
                        .collect(Collectors.toSet());
                JSONObject jsonObj;
                try {
                    jsonObj = JSONObject.fromObject(req.obj);
                } catch (JSONException e) {
                    continue;
                }
                for (PropertyDescriptor pd : pdSet) {

                    String reqName = req.name;
                    if (reqName.length() > 0) {
                        reqName += ".";
                    }
                    reqName += pd.getName();
                    //デフォルト設定の取得
                    Set<Annotation> ants = __fieldAnnotations(req.clz, pd);

                    //NoParameterフィールドへの値の設定を除外
                    if (ants.stream().anyMatch(a -> a instanceof NoParameter)) {
                        continue;
                    }


                    Object prop = null;
                    if (JSONUtils.isNull(jsonObj) == false) {
                        prop =  jsonObj.opt(pd.getName());
                    }

                    ReqMdl propReq = new ReqMdl(
                            pd.getPropertyType(),
                            reqName,
                            prop,
                            ants);
                    sb.add(propReq);
                }
            }
            reqSet = sb.build()
                        .collect(Collectors.toSet());
        }
        __validateNoBlank("", desc, obj, null);

        if (valErrorList__.size() > 0) {
            return;
        }
        __validate("", desc, null);

    }
    /**
     * <p>配列に対する入力チェックを行う
     * @param req
     * @param i
     */
    private void __validateArraySize(ReqMdl req, int arrSize) {
        if (req.clz.isArray()
        || Collection.class.isAssignableFrom(req.clz)) {
            req.ants
            .stream()
            .filter(a -> a instanceof MaxArraySize)
            .map(a -> (MaxArraySize) a)
            .filter(a -> a.value() < arrSize)
            .forEach(a -> {
                valErrorList__.add(
                    new RestApiValidateException(
                            EnumError.PARAM_FORMAT,
                            "error.input.array.size.over",
                            req.name,
                            a.value()
                            )
                        .setParamName(req.name)
                    );

            });
        }
    }
    /**
     *
     * <br>[機  能] プリミティブパラメータの入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param name パラメータ名
     * @param obj 入力値
     * @param ants 入力チェック定義
     * @return true 入力チェッククリア
     */
    private boolean __validateParam(String name, Object obj,
            Set<Annotation> ants) {
        String value = Optional.ofNullable(obj)
                .map(v -> {
                    return String.valueOf(v);
                })
                .orElse("");
        int sErrCnt = valErrorList__.size();

        //文字数制限
        ants.stream()
        .filter(ant -> (ant instanceof MaxLength))
        .map(ant -> (MaxLength) ant)
        .filter(ant -> (ant.value() < Optional.ofNullable(value)
                .map(v -> {
                    return String.valueOf(v).length();
                    })
                .orElse(0)))
        .findAny()
        .ifPresent(ant -> {
            valErrorList__.add(
                    new RestApiValidateException(
                            EnumError.PARAM_MAXLENGTH,
                            "error.input.length.text",
                            name,
                            ant.value()
                            )
                        .setParamName(name)
                    );
        });

        //最大数値最小数値制限
        MaxValue maxAnt = ants.stream()
            .filter(ant -> (ant instanceof MaxValue))
            .map(ant -> (MaxValue) ant)
            .findFirst()
            .orElse(null);
        MinValue minAnt = ants.stream()
                .filter(ant -> (ant instanceof MinValue))
                .map(ant -> (MinValue) ant)
                .findFirst()
                .orElse(null);
        if (maxAnt != null && minAnt != null
                && (Optional.ofNullable(value)
                        .map(v -> {
                            if (!NumberUtils.isDigits(v)) {
                                return maxAnt.value() + 1;
                            }
                            return Long.valueOf(v);
                            })
                        .map(v -> maxAnt.value()
                                < v)
                        .orElse(false)
                        || Optional.ofNullable(value)
                        .map(v -> {
                            if (!NumberUtils.isDigits(v)) {
                                return minAnt.value() - 1;
                            }
                            return Long.valueOf(v);
                            })
                        .map(v -> minAnt.value() >  v)
                        .orElse(false)
                        )
                ) {
            valErrorList__.add(
                    new RestApiValidateException(
                            EnumError.PARAM_FORMAT,
                            "error.input.addhani.text",
                            name,
                            minAnt.value(),
                            maxAnt.value()
                            )
                        .setParamName(name)
                    );

        }
        if (maxAnt != null && minAnt == null
                && Optional.ofNullable(value)
                .map(v -> {
                    if (!NumberUtils.isDigits(v)) {
                        return maxAnt.value() + 1;
                    }
                    return Long.valueOf(v);
                    })
                .map(v -> maxAnt.value()
                        < v)
                .orElse(false)
                ) {
            valErrorList__.add(
                    new RestApiValidateException(
                            EnumError.PARAM_FORMAT,
                            "error.input.number.under",
                            name,
                            maxAnt.value()
                            )
                        .setParamName(name)
                    );

        }
        if (minAnt != null && maxAnt == null
                && Optional.ofNullable(value)
                .map(v -> {
                    if (!NumberUtils.isDigits(v)) {
                        return minAnt.value() - 1;
                    }
                    return Long.valueOf(v);
                    })
                .map(v -> minAnt.value() >  v)
                .orElse(false)
                ) {
            valErrorList__.add(
                    new RestApiValidateException(
                            EnumError.PARAM_FORMAT,
                            "error.input.number.over",
                            name,
                            minAnt.value()
                            )
                        .setParamName(name)
                    );

        }

        //選択値制限
        ants.stream()
        .filter(ant -> (ant instanceof Selectable))
        .map(ant -> (Selectable) ant)
        .filter(ant -> (Set.of(ant.value()).contains(String.valueOf(value)) == false))
        .findAny()
        .ifPresent(ant -> {
            valErrorList__.add(
                    new RestApiValidateException(
                            EnumError.PARAM_FORMAT,
                            "error.input.comp.text.either",
                            name,
                            Arrays.stream(ant.value())
                                .collect(Collectors.joining(","))
                            )
                        .setParamName(name)
                    );
        });

        if (!value.isEmpty()) {
            //テキストボックス制限
            if (ants.stream()
                    .anyMatch(ant -> (ant instanceof TextField))) {
                //スペースのみチェック
                if (ValidateUtil.isSpace(value)) {
                    valErrorList__.add(
                            new RestApiValidateException(
                                    EnumError.PARAM_ONLY_SPACE,
                                    "error.input.spase.only",
                                    name
                                    )
                                .setParamName(name)
                            );
                //先頭スペースチェック
                } else if (ValidateUtil.isSpaceStart(value)) {
                    valErrorList__.add(
                            new RestApiValidateException(
                                    EnumError.PARAM_START_SPACE,
                                    "error.input.spase.start",
                                    name
                                    )
                                .setParamName(name)
                            );
                //タブスペースチェック
                } else if (ValidateUtil.isTab(value)) {
                    valErrorList__.add(
                            new RestApiValidateException(
                                    EnumError.PARAM_TAB,
                                    "error.input.tab.text",
                                    name
                                    )
                                .setParamName(name)
                            );
                }
                //改行チェック
                if (value.indexOf("\n") >= 0) {
                    valErrorList__.add(
                            new RestApiValidateException(
                                    EnumError.PARAM_LETER,
                                    "error.input.njapan.text2",
                                    name,
                                    new GsMessage(ctx__.getRequestModel())
                                        .getMessage("cmn.linefeed")
                                    )
                                .setParamName(name)
                            );

                }

                //JIS第2水準チェック
                if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
                    valErrorList__.add(
                        new RestApiValidateException(
                                EnumError.PARAM_LETER,
                                "error.input.njapan.text3",
                                name
                                )
                            .setParamName(name)
                            );
                }
            }

            //テキストエリア制限
            if (ants.stream()
                    .anyMatch(ant -> (ant instanceof TextArea))) {

                //スペース(改行)のみチェック
                if (ValidateUtil.isSpaceOrKaigyou(value)) {
                    valErrorList__.add(
                            new RestApiValidateException(
                                    EnumError.PARAM_ONLY_SPACE,
                                    "error.input.spase.cl.only",
                                    name
                                    )
                                .setParamName(name)
                            );
                }
                //JIS第2水準チェック
                if (!GSValidateUtil.isGsJapaneaseStringTextArea(value)) {
                    valErrorList__.add(
                        new RestApiValidateException(
                                EnumError.PARAM_LETER,
                                "error.input.njapan.text3",
                                name
                                )
                            .setParamName(name)
                            );
                }
            }
        }

        return (valErrorList__.size() == sErrCnt);
    }
    /**
    *
    * <br>[機  能] 初期化の完了したパラメータモデルに対して入力チェックを実行する
    * <br>[解  説] 再帰的に実行される
    * <br>[備  考]
    * @param name パラメータ名
    * @param desc パラメータモデル
    * @param src  リクエスト値
    * @param ants パラメータに設定されたアノテーション
    */
   @SuppressWarnings("unchecked")
   private void __validateNoBlank(String name,
           Object desc,
           Object src,
           Set<Annotation> ants) {
       if (desc == null) {
           return;
       }
       Class<? extends Object> clz = desc.getClass();

       if (clz.isArray()) {
           int length = Array.getLength(desc);


           for (int i = 0; i < length; i++) {
               __validateNoBlank(
                       String.format("%s[%d]", name, i),
                       Array.get(desc, i),
                       src,
                       ants);
           }
       } else if (Collection.class.isAssignableFrom(clz)) {

           List<Object> list;
           list = ((Collection<Object>) desc)
                   .stream()
                   .collect(Collectors.toList());

           for (int i = 0; i < list.size(); i++) {
               __validateNoBlank(
                       String.format("%s[%d]", name, i),
                       list.get(i),
                       src,
                       ants);
           }

       } else if (Map.class.isAssignableFrom(clz)) {
           for (Entry<Object, Object> entry : ((Map<Object, Object>) desc).entrySet()) {
               __validateNoBlank(
                       String.format("%s(%s)",
                               name,
                               String.valueOf(entry.getKey())),
                       entry.getValue(),
                       src,
                       ants);
           }

       } else if (clz.isPrimitive()
                   || Number.class.isAssignableFrom(clz)
                   || String.class.isAssignableFrom(clz)
                   || UDate.class.isAssignableFrom(clz)
                   || Enum.class.isAssignableFrom(clz)) {
           if (ants == null) {
               return;
           }
           //プリミティブパラメータの入力チェックは実行済み
       } else {


           Set<PropertyDescriptor> pdSet =
                   Stream.of(PropertyUtils.getPropertyDescriptors(clz))
                   .filter(discriptor -> !Objects.equals(discriptor.getName(), "class"))
                   .filter(pd -> !Objects.equals(pd.getWriteMethod(), null)
                           && !Objects.equals(pd.getReadMethod(), null))
                   .collect(Collectors.toSet());

           for (PropertyDescriptor pd : pdSet) {
               String reqName = name;
               if (reqName.length() > 0) {
                   reqName += ".";
               }
               reqName += pd.getName();
               Object value = null;
               try {
                   value = PropertyUtils.getProperty(src, reqName);
               } catch (IllegalAccessException | InvocationTargetException
                       | NoSuchMethodException
                       | NestedNullException e) {
               }
               Set<Annotation> childAnt =
                       __fieldAnnotations(clz, pd)
                       .stream()
                       .filter(a -> (a instanceof NotBlank)
                               )
                       .collect(Collectors.toSet());
               if (childAnt.stream()
                   .anyMatch(ant -> (ant instanceof NotBlank))
                   && Optional.ofNullable(value)
                       .map(v -> String.valueOf(v))
                       .orElse("").length() == 0) {
                   valErrorList__.add(
                           new RestApiValidateException(
                                   EnumError.PARAM_REQUIRED,
                                   "error.input.required.text",
                                   reqName
                                   )
                               .setParamName(reqName)
                           );
                   continue;
               }
               try {
                   value = PropertyUtils.getProperty(desc, reqName);
               } catch (IllegalAccessException | InvocationTargetException
                       | NoSuchMethodException
                       | NestedNullException e) {
               }
               __validateNoBlank(reqName, value, src, childAnt);
           }
       }
   }

    /**
     *
     * <br>[機  能] 初期化の完了したパラメータモデルに対して入力チェックを実行する
     * <br>[解  説] 再帰的に実行される
     * <br>[備  考]
     * @param name パラメータ名
     * @param desc パラメータ
     * @param ants パラメータに設定されたアノテーション
     */
    @SuppressWarnings("unchecked")
    private void __validate(String name, Object desc, Set<Annotation> ants) {
        if (desc == null) {
            return;
        }
        Class<? extends Object> clz = desc.getClass();

        if (clz.isArray()) {
            int length = Array.getLength(desc);


            for (int i = 0; i < length; i++) {
                __validate(
                        String.format("%s[%d]", name, i),
                        Array.get(desc, i),
                        ants);
            }
        } else if (Collection.class.isAssignableFrom(clz)) {

            List<Object> list;
            list = ((Collection<Object>) desc)
                    .stream()
                    .collect(Collectors.toList());

            for (int i = 0; i < list.size(); i++) {
                __validate(
                        String.format("%s[%d]", name, i),
                        list.get(i),
                        ants);
            }

        } else if (Map.class.isAssignableFrom(clz)) {
            for (Entry<Object, Object> entry : ((Map<Object, Object>) desc).entrySet()) {
                __validate(
                        String.format("%s(%s)",
                                name,
                                String.valueOf(entry.getKey())),
                        entry.getValue(),
                        ants);
            }

        } else if (clz.isPrimitive()
                    || Number.class.isAssignableFrom(clz)
                    || String.class.isAssignableFrom(clz)
                    || UDate.class.isAssignableFrom(clz)
                    || Enum.class.isAssignableFrom(clz)) {
            if (ants == null) {
                return;
            }
            //プリミティブパラメータの入力チェックは実行済み
        } else {


            Set<PropertyDescriptor> pdSet =
                    Stream.of(PropertyUtils.getPropertyDescriptors(clz))
                    .filter(discriptor -> !Objects.equals(discriptor.getName(), "class"))
                    .filter(pd -> !Objects.equals(pd.getWriteMethod(), null)
                            && !Objects.equals(pd.getReadMethod(), null))
                    .collect(Collectors.toSet());

            for (PropertyDescriptor pd : pdSet) {
                String reqName = name;
                if (reqName.length() > 0) {
                    reqName += ".";
                }
                reqName += pd.getName();
                Object value = null;
                try {
                    value = PropertyUtils.getProperty(desc, reqName);
                } catch (IllegalAccessException | InvocationTargetException
                        | NoSuchMethodException
                        | NestedNullException e) {
                }
                Set<Annotation> childAnt =
                        __fieldAnnotations(clz, pd)
                        .stream()
                        .filter(a -> (a instanceof NotBlank)
                                )
                        .collect(Collectors.toSet());
                if (childAnt.stream()
                    .anyMatch(ant -> (ant instanceof NotBlank))
                    && Optional.ofNullable(value)
                        .map(v -> String.valueOf(v))
                        .orElse("").length() == 0) {
                    valErrorList__.add(
                            new RestApiValidateException(
                                    EnumError.PARAM_REQUIRED,
                                    "error.input.required.text",
                                    reqName
                                    )
                                .setParamName(reqName)
                            );
                    continue;
                }
                __validate(reqName, value, childAnt);
            }
            if (clz.isAnnotationPresent(ParamModel.class)) {
                Stream.of(clz.getMethods())
                    .filter(m -> m.isAnnotationPresent(Validator.class))
                    .forEach(m -> {
                        Object[] params = new Object[m.getParameterCount()];
                        Parameter[] parameters = m.getParameters();
                        for (int i = 0; i < params.length; i++) {
                            Parameter parameter = parameters[i];
                            if (parameter.getType().isAssignableFrom(RestApiContext.class)) {
                                params[i] = ctx__;
                            }
                        }
                        try {
                            m.invoke(desc, params);
                        } catch (IllegalAccessException
                                | IllegalArgumentException e) {

                        } catch (InvocationTargetException e) {
                            if (e.getTargetException() instanceof RestApiValidateException) {
                                valErrorList__.add(
                                        (RestApiValidateException) e.getTargetException());
                                return;
                            } else if (e.getTargetException() instanceof RestApiException) {
                                throw (RestApiException) e.getTargetException();
                            }
                            throw new RuntimeException("リクエストメソッド実行失敗", e);
                        }
                    });
            }
        }
    }
    /**
     * <p>valErrorList を取得します。
     * @return valErrorList
     * @see ParamModelCreator#valErrorList__
     */
    public List<RestApiValidateException> getValErrorList() {
        return valErrorList__;
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
    private Set<Annotation> __fieldAnnotations(Class<? extends Object> bodyClz,
            PropertyDescriptor pd) {
        Class<? extends Object> clz = bodyClz;
        Set<Annotation> ret = new HashSet<>();
        while (clz != null && clz != Object.class) {
            Field field = Stream.of(
                __getDeclaredField(clz, pd.getName()),
                __getDeclaredField(clz, String.format("%s_", pd.getName())),
                __getDeclaredField(clz, String.format("%s__", pd.getName()))
                )
                .filter(Objects::nonNull)
                .findAny()
                .orElse(null);
            if (field != null) {
                ret.addAll(Stream.of(
                    field.getDeclaredAnnotations()
                )
                .collect(Collectors.toSet()));
                break;
            }
            clz = clz.getSuperclass();
        }
        Method m = pd.getReadMethod();
        if (m == null) {
            return ret;
        }
        ret.addAll(
                Stream.of(m.getAnnotations())
                    .collect(Collectors.toSet()));
        return ret;
    }
    /***
     * クラスからアクセスできる指定フィールドを返す
     * @param clz 対象クラス
     * @param name 対象フィールド名
     * @return 指定フィールド 存在しない場合はnull
     */
    private Field __getDeclaredField(Class<? extends Object> clz, String name) {
        try {
            return clz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            return null;
        }
    }
}
