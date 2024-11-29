package jp.groupsession.v2.restapi.response;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Content;
import org.jdom2.Element;
import org.jdom2.Text;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.json.util.JSONUtils;
/**
 *
 * <br>[機  能] Jdom Element 風 モデル
 * <br>[解  説] XML JSON の両方へ変換可能
 * <br>[備  考]
 *
 * @author JTS
 */
public class ResultElement {
    /** エレメント名*/
    String name__;
    /** コンテンツ*/
    List<ResultElement> content__ = new ArrayList<>();
    /** 属性*/
    Map<String, String> attribute__ = new LinkedHashMap<>();
    /** ボディ要素*/
    String body__ = "";
    /**
     *
     * コンストラクタ
     */
    public ResultElement() {
    }
    /**
     * コンストラクタ
     * @param name
     */
    public ResultElement(String name) {
        super();
        name__ = name;
    }
    /**
     * <p>name を取得します。
     * @return name
     * @see Element#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see Element#name__
     */
    public void setName(String name) {
        name__ = name;
    }

    /**
     *
     * <br>[機  能] ボディコンテンツ追加
     * <br>[解  説]
     * <br>[備  考]
     * @param str 内容
     * @return this
     */
    public ResultElement addContent(String str) {
        content__.clear();
        body__ += str;
        return this;
    }
    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param child 内容
     * @return this
     */
    public ResultElement addContent(ResultElement child) {
        body__ = "";
        this.content__.add(child);
        return this;
    }
    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param collection 内容
     * @return this
     */
    public ResultElement addContent(Collection<ResultElement> collection) {
        body__ = "";
        this.content__.addAll(collection);
        return this;
    }
    /**
     *
     * <br>[機  能] ボディコンテンツ追加
     * <br>[解  説]
     * <br>[備  考]
     * @param index
     * @param child 内容
     * @return this
     */
    public ResultElement addContent(int index, ResultElement child) {
        body__ = "";
        this.content__.add(index, child);
        return this;
    }
    /**
     *
     * <br>[機  能] ボディコンテンツ追加
     * <br>[解  説]
     * <br>[備  考]
     * @param index
     * @param collection 内容
     * @return this
     */
    public ResultElement addContent(int index, Collection<ResultElement> collection) {
        body__ = "";
        this.content__.addAll(index, collection);
        return this;
    }
    /**
     *
     * <br>[機  能] 属性設定
     * <br>[解  説]
     * <br>[備  考]
     * @param name
     * @param value
     * @return this
     */
    public ResultElement setAttribute(String name, String value) {
        attribute__.put(name, value);
        return this;
    }
    /**
     *
     * <br>[機  能] XML生成用エレメントに変換
     * <br>[解  説]
     * <br>[備  考]
     * @return Element
     */
    public Content toElement() {
        Element ret = new Element(name__);

        //本文のみ && 名無し
        if (StringUtil.isNullZeroString(name__)) {
            return new Text(
                    body__
                    );
        }

        attribute__.entrySet().stream()
        .forEach(ent -> ret.setAttribute(ent.getKey(), ent.getValue()));

        //本文のみ
        if (!StringUtil.isNullZeroString(body__)) {
            return ret.addContent(
                    body__
                    );
        }

        if (content__.size() == 0 && attribute__.size() == 0) {
            return ret;
        }

        content__.stream()
            .forEach(cnt -> ret.addContent(cnt.toElement()));
        return ret;
    }
    /**
     *
     * @param json
     * @return 空文字列や配列要素のJSON構造をRESTAPI用にコンバートして返す
     */
    public static JSONObject convert(JSONObject json) {
        /** パラメータ初期化リクエストクラス*/
        final class ReqMdl {
            Object src;
            Object desc;
            /**
             *
             * コンストラクタ
             * @param src パラメータ定義
             * @param desc 出力先
             */
            public ReqMdl(Object src, Object desc) {
                this.src = src;
                this.desc = desc;

            }
        }

        Map<String, Object> ret = new LinkedHashMap<String, Object>();


        List<ReqMdl> reqSet = List.of(new ReqMdl(json, ret));
        while (reqSet.size() > 0) {
            List<ReqMdl> sb = new ArrayList<>();

            for (ReqMdl req : reqSet) {

                if (JSONUtils.isArray(req.src) && JSONUtils.isArray(req.desc)) {
                    @SuppressWarnings("unchecked")
                    List<Object> desc = (List<Object>) req.desc;
                    JSONArray arr = JSONArray.fromObject(req.src);
                    for (int i = 0; i < arr.size(); i++) {
                        ReqMdl next;
                        if (JSONUtils.isArray(arr.opt(i))) {
                            next = new ReqMdl(arr.opt(i), new ArrayList<Object>());
                            desc.add(i, next.desc);
                            sb.add(next);
                            continue;
                        }
                        if (JSONUtils.isObject(arr.opt(i))) {
                            next = new ReqMdl(arr.opt(i), new LinkedHashMap<String, Object>());
                            desc.add(i, next.desc);
                            sb.add(next);
                            continue;
                        }
                        desc.add(i, arr.opt(i));
                    }
                    continue;
                }

                if (JSONUtils.isObject(req.src)
                        && JSONUtils.isObject(req.desc)) {
                    @SuppressWarnings("unchecked")
                    Map<String, Object> desc = (Map<String, Object>) req.desc;
                    JSONObject jsonObj = JSONObject.fromObject(req.src);

                    for (Object key : jsonObj.keySet()) {
                        Object child = jsonObj.opt((String) key);

                        if (JSONUtils.isArray(child)) {
                            //空配列はnull
                            if (((JSONArray) child).size() == 0
                                    && String.valueOf(key).endsWith("Array") == false) {
                                desc.put((String) key, "");
                                continue;
                            }
                            ReqMdl next = new ReqMdl(child, new ArrayList<Object>());
                            desc.put((String) key, next.desc);
                            sb.add(next);
                            continue;
                        }
                        //Arrayで終わる要素は配列化
                        if (String.valueOf(key).endsWith("Array")) {
                            if (JSONUtils.isObject(child)) {
                                JSONObject childJson = (JSONObject) child;


                                if (childJson.keySet().size() == 1
                                        && String.valueOf(
                                                childJson.keys().next())
                                            .endsWith("Info")) {
                                    JSONArray arr = new JSONArray();
                                    arr.add(
                                            childJson.opt(
                                                String.valueOf(childJson.keys().next())
                                                )
                                            );
                                    ReqMdl next = new ReqMdl(arr, new ArrayList<Object>());
                                    desc.put((String) key, next.desc);
                                    sb.add(next);
                                    continue;
                                }
                            }
                            JSONArray arr = new JSONArray();
                            arr.add(child);
                            ReqMdl next = new ReqMdl(arr, new ArrayList<Object>());
                            desc.put((String) key, next.desc);
                            sb.add(next);
                            continue;
                        }

                        if (JSONUtils.isObject(child)) {
                            ReqMdl next = new ReqMdl(child, new LinkedHashMap<String, Object>());
                            desc.put((String) key, next.desc);
                            sb.add(next);
                            continue;
                        }
                        desc.put((String) key, child);
                    }

                }
            }
            reqSet = sb;
        }
        return JSONObject.fromObject(ret);
    }


}
