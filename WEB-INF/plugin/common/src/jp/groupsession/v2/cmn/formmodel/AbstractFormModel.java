package jp.groupsession.v2.cmn.formmodel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormCalcBiz;
import jp.groupsession.v2.cmn.formbuilder.FormCell;
import jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence;
import jp.groupsession.v2.cmn.formbuilder.IFormAccessMapMakable;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 *
 * <br>[機  能] 入力機能モデル要素の共通機能の仮想クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractFormModel implements ISetableHiddenParam {
    /** 縦スクロール位置*/
    private int scrollY__;
    /** 変更時実行javaスクリプトイベントリスト*/
    private List<String> valueChangedEvList__ = new ArrayList<String>();
    /** BlockList内の識別番号*/
    private int blockIdx__ = 0;
    /** root要素を保持 json出力対象にならないよう publicのゲッターを用意しないこと*/
    private IFormAccessMapMakable root__;
    /** cell情報を保持 json出力対象にならないよう publicのゲッターを用意しないこと*/
    private FormCell cellInfo__;

    /**
     *
     * <br>[機  能] json入力区分
     * <br>[解  説] jsonで取り込む内容をフラグ管理するために使用
     * <br>[備  考]
     *
     * @author JTS
     */
    public enum KBN_JSON_MERGE {
        /** json入力区分 all 入力設定なども含む*/
        all,
        /** json入力区分 入力値のみ*/
        value;
    }
    /**
     * デフォルトコンストラクタ
     */
    protected AbstractFormModel() {
        super();
    }

    /**
     * コンストラクタJSONオブジェクトから生成
     * @param json jsonオブジェクト
     */
    protected AbstractFormModel(JSONObject json) {
        mergeJson(json, KBN_JSON_MERGE.all);
    }
    /**
    *
    * <br>[機  能] フォームタイプでインスタンスを生成する。
    * <br>[解  説]
    * <br>[備  考]
    * @param type フォーム種別
    * @return インスタンス
    */
    public static AbstractFormModel getInstance(EnumFormModelKbn type) {
       return getInstance(type, null);
    }

    /**
     *
     * <br>[機  能] フォームタイプとjsonでインスタンスを生成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param type フォーム種別
     * @param json jsonObject
     * @return インスタンス
     */
    public static AbstractFormModel getInstance(EnumFormModelKbn type, JSON json) {
        if (type == null) {
            return null;
        }
        AbstractFormModel ret = null;
        switch (type) {
        case label:
            ret = new Comment();
            break;
        case textbox:
            ret = new Textbox();
            break;
        case textarea:
            ret = new Textarea();
            break;
        case date:
            ret = new DateBox();
            break;
        case time:
            ret = new TimeBox();
            break;
        case number:
            ret = new NumberBox();
            break;
        case radio:
            ret = new RadioButton();
            break;
        case combo:
            ret = new ComboBox();
            break;
        case check:
            ret = new CheckBox();
            break;
        case sum:
            ret = new Sum();
            break;
        case calc:
            ret = new Calc();
            break;
        case user:
            ret = new SimpleUserSelect();
            break;
        case group:
            ret = new GroupComboModel();
            break;
        case block:
            ret = new Block();
            break;
        case blocklist:
            ret = new BlockList();
            break;
        case file:
            ret = new Temp();
            break;
        default:
//          ret = new Textbox();
          break;
        }
        if (ret != null && json != null) {
            ret.mergeJson(json, KBN_JSON_MERGE.all);
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] JSONオブジェクトの内容を取り込む
     * <br>[解  説]
     * <br>[備  考]
     * @param json jsonオブジェクト
     * @param mergeKbn 入力範囲区分
     */
    public abstract void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn);
    /**
     *
     * <br>[機  能] 表示情報の初期化
     * <br>[解  説] 入力情報に基づき、表示情報を設定する
     * <br>[備  考] 入力フォームｓ
     * @param reqMdl リクエストモデル
     * @param con DBコネクション
     * @throws SQLException sql実行時例外
     * @throws IOToolsException ディレクトリ作成時例外
     */
    public void dspInit(RequestModel reqMdl, Connection con) throws SQLException, IOToolsException {

    }
    /**
     *
     * <br>[機  能] 関連するフォーム区分列挙子を返す
     * <br>[解  説] 関連する列挙子がないものはnullを返す
     * <br>[備  考]
     * @return フォーム区分列挙子
     */
    public abstract EnumFormModelKbn formKbn();
    /**
     *
     * <br>[機  能] 他の要素の入力値でマージする
     * <br>[解  説] 画面再読込み時の入力値のマージに使用
     * <br>[備  考]
     * @param obj マージ元の要素
     */
    public void merge(AbstractFormModel obj) {
        if (this == obj) {
            return;
        }
        if (obj == null) {
            return;
        }
        int blockIdx = obj.getBlockIdx();
        if (blockIdx > 0) {
            setBlockIdx(blockIdx);
        }
    }

    /**
    *
    * <br>[機  能] 他の要素の入力値でマージする
    * <br>[解  説] DBからロードした値をマージする場合に使用
    * <br>[備  考]
    * @param values 入力値
    */
   public void merge(String[] values) {

   }

    /**
     *
     * <br>[機  能] デフォルト設定で要素を初期化する
     * <br>[解  説]
     * <br>[備  考]
     */
    public abstract void defaultInit();
    /**
    *
    * <br>[機  能] 計算処理を行う
    * <br>[解  説]
    * <br>[備  考] 別の
    * @param biz 計算用ビジネスロジック
    */
    public void calc(FormCalcBiz biz) {

    }

   /**
    *
    * <br>[機  能] 計算結果を返す
    * <br>[解  説]
    * <br>[備  考] Override実装時に処理の中でcalcを呼ばないこと
    * @return 計算結果
    */
    public String getCalced() {
        return "";
    }

    /**
     * <p>scrollY を取得します。
     * @return scrollY
     */
    public int getScrollY() {
        return scrollY__;
    }

    /**
     * <p>scrollY をセットします。
     * @param scrollY scrollY
     */
    public void setScrollY(int scrollY) {
        scrollY__ = scrollY;
    }

    /**
     * <p>blockIdx を取得します。
     * @return blockIdx
     */
    public int getBlockIdx() {
        return blockIdx__;
    }

    /**
     * <p>blockIdx をセットします。
     * @param blockIdx blockIdx
     */
    public void setBlockIdx(int blockIdx) {
        blockIdx__ = blockIdx;
    }

    /**
     *
     * <br>[機  能] 画面再描画前のスクロール位置保管スクリプトを出力
     * <br>[解  説]
     * <br>[備  考]
     * @param name 要素へのフォーム参照名
     * @return script
     */
    public String outputSetScrollYSclipt(String name) {
        StringBuilder sb = new StringBuilder();
        sb.append("var scrollPosition = $(window).scrollTop();");
        sb.append("$('<input>', {");
        sb.append("    type: 'hidden', ");
        sb.append("    name: '" + name + ".scrollY', ");
        sb.append("    value: scrollPosition ");
        sb.append("    }).appendTo($(this).parent()); ");
        return sb.toString();
    }
    /**
     *
     * <br>[機  能] 画面再描画後のスクロールスクリプトを出力
     * <br>[解  説]
     * <br>[備  考]
     * @return script
     */
    public String outputDoScrollYSclipt() {
        if (this.getScrollY() == 0) {
            return "";
        }
        return "$(window).scrollTop(" + this.getScrollY() + ");";
    }
    /**
     *
     * <br>[機  能] 入力値のバリデートチェックを行う
     * <br>[解  説]
     * <br>[備  考] dspInitを実行済みである必要がある
     * @param errors エラー格納用アクションエラー
     * @param reqMdl リクエストモデル
     */
    public final void validateCheck(ActionErrors errors, RequestModel reqMdl) {
        validateCheck(errors, reqMdl, new ValidateInfo());
    }
    /**
    *
    * <br>[機  能] 入力値のバリデートチェックを行う
    * <br>[解  説]
    * <br>[備  考] dspInitを実行済みである必要がある
    * @param errors エラー格納用アクションエラー
    * @param reqMdl リクエストモデル
    * @param info バリデート情報
    */
   public void validateCheck(ActionErrors errors, RequestModel reqMdl, ValidateInfo info) {
   }

    /**
     * <p>valueChangedEvList を取得します。
     * @return valueChangedEvList
     * @see jp.groupsession.v2.cmn.formmodel.AbstractFormModel#valueChangedEvList__
     */
    public List<String> getValueChangedEvList() {
        return valueChangedEvList__;
    }

    /**
     * <p>valueChangedEvList をセットします。
     * @param valueChangedEvList valueChangedEvList
     * @see jp.groupsession.v2.cmn.formmodel.AbstractFormModel#valueChangedEvList__
     */
    public void setValueChangedEvList(List<String> valueChangedEvList) {
        valueChangedEvList__ = valueChangedEvList;
    }
    /**
     *
     * <br>[機  能] 要素の値が変更された場合のjavascriptイベントを追加します。
     * <br>[解  説] 要素のblurイベントで値変更時に実行されます
     * <br>[備  考]
     * @param ev イベント
     */
    public void addValueChangedEv(String ev) {
        valueChangedEvList__.add(ev);
    }

    /**
     *
     * <br>[機  能] 表ボディの行削除により各要素で実行されるイベント
     * <br>[解  説]
     * <br>[備  考] 必要な場合、オーバーライドして使用する
     * @param formSid フォームSID
     * @param rowNo   行番号
     */
    public void removeRow(int formSid, int rowNo) {
    }

    /**
     *
     * <br>[機  能] 利用時に必須入力条件を満たすことができない申請内容がないことをチェックします
     * <br>[解  説]
     * <br>[備  考]
     * @param errors エラー配列
     * @param reqMdl リクエストモデル
     * @param info バリデート情報
     */
    public void chkUnuseableInput(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
    }
    /**
     * <p>root を取得します。
     * @return root
     * @see jp.groupsession.v2.cmn.formmodel.AbstractFormModel#root__
     */
    protected IFormAccessMapMakable _getRoot() {
        return root__;
    }

    /**
     * <p>root をセットします。
     * @param root root
     * @see jp.groupsession.v2.cmn.formmodel.AbstractFormModel#root__
     */
    protected void _setRoot(IFormAccessMapMakable root) {
        root__ = root;
    }
    /**
     * <p>cellInfo を取得します。
     * @return cellInfo
     * @see jp.groupsession.v2.cmn.formmodel.AbstractFormModel#cellInfo__
     */
    protected FormCell _getCellInfo() {
        return cellInfo__;
    }

    /**
     * <p>cellInfo をセットします。
     * @param cellInfo cellInfo
     * @see jp.groupsession.v2.cmn.formmodel.AbstractFormModel#cellInfo__
     */
    public void setCellInfo(FormCell cellInfo) {
        cellInfo__ = cellInfo;
    }

    /**
     *
     * <br>[機  能] 画面設定モデルの取得
     * <br>[解  説] ルート要素（FormInputBuilder）の画面設定モデルを返す
     * <br>[備  考] FormInputBuilderに画面設定モデルが設定されていなければnull
     * @return
     */
    protected FormInputInitPrefarence _getInitPref() {
        if (root__ != null) {
            return root__.getInitPref();
        }
        return null;
    }
}
