package jp.groupsession.v2.convert.convert530.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONObject;

public class RngFormIdPublisher {
        /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngFormIdPublisher.class);

    enum FormType {
        /** コメント  */
        label(1),
        /** テキスト入力　 */
        textbox(2),
        /** テキスト入力(複数行) */
        textarea(3),
        /** 日付入力  */
        date(4),
        /** 数値入力  */
        number(5),
        /** ラジオボタン  */
        radio(6),
        /** コンボボックス  */
        combo(7),
        /** チェックボックス */
        check(8),
        /** 自動計算（合計） */
        sum(9),
        /** 自動計算（その他） */
        calc(10),
        /** ユーザ選択 */
        user(11),
        /** グループ選択 */
        group(12),
        /** ブロック */
        block(13),
        /** 表 */
        blocklist(14),
        /** 添付*/
        file(15);
        /** フォーム種別ID */
        private int value__;
        public int getValue() {
            return value__;
        }

        /**
         * コンストラクタ
         * @param value フォーム種別ID
         */
        private FormType(int value) {
            value__ = value;
        }
    }
    /** 自動採番FormID管理MAP */
    Map<FormType, AtomicInteger> typeCountMap__ =
            Stream.of(FormType.values())
            .collect(Collectors.toMap(
                    type -> type,
                    type -> new AtomicInteger(0)));


    /** IDが追加されたFormCellList
     *  <br> キー ：FormSID
     *  <br> バリュー：発行されたFormSid */
    final List<JSONObject> publishedList__ = new ArrayList<>();

    /** ID追加後のJSON*/
    JSONArray publishedJSON__;


    public RngFormIdPublisher(String json) {
        publishedJSON__ = JSONArray.fromObject(json);
        __compile();
    }
    /**
     *
     * <br>[機  能] FormID未設定のフォーム要素にフォームIDを設定する
     * <br>[解  説]
     * <br>[備  考]
     */
    private void __compile() {
        final List<JSONObject> silializeCell = __createSilializeCell();
        log__.debug("<--- template compile --->");
        __initFormIdTbl(silializeCell);

        __pickupPublishForm(silializeCell);

        __saibanForm();
        log__.debug("</---  template compile --->");
    }
    /**
     *
     * <br>[機  能] 新規採番したフォームIDを設定する
     * <br>[解  説] publishedList__が更新される
     * <br>[備  考]
     */
    private void __saibanForm() {
        publishedList__.stream()
        .forEach(obj -> {
            String formID;
            try {
                FormType type = FormType.valueOf(obj.optString("type"));
                formID = String.format(
                        "%s_%d",
                        type.name(),
                        typeCountMap__.get(type).incrementAndGet());
            } catch (IllegalArgumentException e) {
                return;
            }
            log__.debug("<--- output --->");
            log__.debug("  <--- type --->");
            log__.debug("    " + obj.optString("type"));
            log__.debug("  </--- type --->");
            log__.debug("  <--- sid --->");
            log__.debug("    " + obj.optString("sid"));
            log__.debug("  </--- sid --->");
            log__.debug("  <--- formID --->");
            log__.debug("    " + obj.optString("formID"));
            log__.debug("  </--- formID --->");
            log__.debug("</--- output --->");

            obj.element("formID", formID);
        });
    }
    /**
     *
     * <br>[機  能] フォームリストから変更対象のリストを生成する
     * <br>[解  説] 実行後 publishedList__が設定される
     * <br>[備  考]
     * @param silializeCell 直列化したJSONObject
     */
    private void __pickupPublishForm(List<JSONObject> silializeCell) {
        silializeCell.stream()
        .filter(obj ->
                (Objects.equals(
                    obj.optString("type"),
                    FormType.block.name()) == false)
                && obj.optString("formID").length() == 0)
        .forEach(obj -> {
            log__.debug("<--- picup --->");
            log__.debug("  <--- type --->");
            log__.debug("    " + obj.optString("type"));
            log__.debug("  </--- type --->");
            log__.debug("  <--- sid --->");
            log__.debug("    " + obj.optString("sid"));
            log__.debug("  </--- sid --->");
            log__.debug("</--- picup --->");

            publishedList__.add(obj);
        });


    }
    /**
     *
     * <br>[機  能] 自動採番用IDテーブルを初期化
     * <br>[解  説]
     * <br>[備  考]
     * @param silializeCell 直列化したJSONObject
     */
    private void __initFormIdTbl(List<JSONObject> silializeCell) {
        silializeCell.stream()
        .forEach(obj -> {
            try {
                FormType type = FormType.valueOf(obj.optString("type"));

                if (type == FormType.block) {
                    return;
                }
                String formID = obj.optString("formID");
                if (formID.startsWith(type.name() + "_")) {
                    final int cnt;
                    try {
                        cnt = Integer.valueOf(formID.substring((type.name() + "_").length()));
                    } catch (NumberFormatException e) {
                        return;
                    }
                    typeCountMap__.get(type).updateAndGet(val -> {
                        if (val < cnt) {
                            return cnt;
                        }
                        return val;
                    });
                }

            } catch (IllegalArgumentException e) {
                return;
            }
        });

    }
    /**
     *
     * <br>[機  能] テーブル型JSONArrayをFormCellのJSONObjectで直列化したStreamを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param array
     * @return JSONObjectで直列化したStream
     */
    @SuppressWarnings("unchecked")
    private static Stream<JSONObject> __tblSilializedStream(JSONArray array) {
        //行数分直列化
        return StreamSupport.stream(
                Spliterators.spliterator(
                        array.iterator(),
                        array.size(),
                        Spliterator.SIZED),
                false)
        //列数分直列化
        .filter(obj -> JSONArray.class.isInstance(obj))
        .flatMap(obj -> {
            JSONArray cols = JSONArray.class.cast(obj);
            return StreamSupport.stream(
                    Spliterators.spliterator(
                       cols.iterator(), cols.size(), Spliterator.SIZED),
                    false);
            })
        .filter(obj -> JSONObject.class.isInstance(obj))
        .map(obj -> JSONObject.class.cast(obj));

    }

    /**
     *
     * <br>[機  能] FormCellのオブジェクトで直列化したリストを生成する
     * <br>[解  説] publishedJSON__から生成する
     * <br>[備  考]
     * @return FormCellのオブジェクトで直列化したリスト
     */
    private List<JSONObject> __createSilializeCell() {
        return RngFormIdPublisher.__tblSilializedStream(publishedJSON__)
        //ブロック要素、表要素内も展開
        .flatMap(cell -> {
            try {
                FormType type = FormType.valueOf(cell.optString("type"));

                if (type == FormType.block) {
                    return Stream.concat(
                                Stream.of(cell),
                                RngFormIdPublisher.__tblSilializedStream(
                                        cell.optJSONObject("body")
                                            .optJSONArray("formTable")
                                        )
                                );
                }
                if (type == FormType.blocklist) {
                    return Stream.of(
                                Stream.of(cell),
                                RngFormIdPublisher.__tblSilializedStream(
                                        cell.optJSONObject("body")
                                            .optJSONObject("header")
                                            .optJSONArray("formTable")
                                        ),
                                RngFormIdPublisher.__tblSilializedStream(
                                        cell.optJSONObject("body")
                                            .optJSONArray("body")
                                            .optJSONObject(0)
                                            .optJSONArray("formTable")
                                        ),
                                RngFormIdPublisher.__tblSilializedStream(
                                        cell.optJSONObject("body")
                                            .optJSONObject("footer")
                                            .optJSONArray("formTable")
                                        )
                                )
                            .flatMap(str -> str);
                }
                return Stream.of(cell);

            } catch (IllegalArgumentException e) {
                return Stream.of();
            }
        })
        .collect(Collectors.toList());



    }
    /**
     *
     * <br>[機  能] 新規に追加されたFormSID → FormIDのマップを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return IDが追加されたFormSIDMap
     */
    public Map<Integer, String> getPublishedIdMap() {
        return publishedList__
                .stream()
                .collect(Collectors.toMap(
                        obj -> obj.optInt("sid"),
                        obj -> obj.optString("formID")
                        ));
    }
    /**
     *
     * <br>[機  能] FormID追加後のJSON文字列を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return FormID追加後のJSON文字列
     */
    public String getPublishedJSON() {
        return publishedJSON__.toString();
    }



}
