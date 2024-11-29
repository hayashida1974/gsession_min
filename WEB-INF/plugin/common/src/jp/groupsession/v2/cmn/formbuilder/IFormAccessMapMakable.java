package jp.groupsession.v2.cmn.formbuilder;

import java.util.Map;

import jp.co.sjts.util.json.JSONString;


/**
 * <br>[機  能] インタフェース宣言 子要素へのアクセスマップを生成する機能
 * <br>[解  説]
 * <br>[備  考] 実装要素は循環参照を防ぐため、root要素への参照をJSONオブジェクト化対象から除外し、実装すること
 *
 * @author JTS
 */
public interface IFormAccessMapMakable extends JSONString {
    /**
     *
     * <br>[機  能] 子要素へのアクセスマップを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param target 保管先Map
     * @param rowNo 行番号
     */
    public void makeFormMap(Map<FormAccesser, FormCell> target, int rowNo);
    /**
     *
     * <br>[機  能] root要素への参照を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param root root要素
     */
    public void setRoot(IFormAccessMapMakable root);

    /**
     *
     * <br>[機  能] root要素へ子要素のアクセス参照を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param accsess アクセサクラス
     * @param cell 登録要素
     */
    public void setAccessIndex(FormAccesser accsess, FormCell cell);

    /**
    *
    * <br>[機  能] root要素から全要素へのアクセスマップを取得する
    * <br>[解  説]
    * <br>[備  考]
    * @return アクセスマップ root要素へパスが通らない場合 null
    */
    public abstract Map<FormAccesser, FormCell> getRootAccessMap();
    /**
    *
    * <br>[機  能] root要素から計算用ビジネスロジックを取得する
    * <br>[解  説] root要素のdspInit開始以前はnull
    * <br>[備  考]
    * @return アクセスマップ root要素へパスが通らない場合 null
    */
    public abstract FormCalcBiz getFormCalcBiz();
    /**
     *
     * <br>[機  能] root要素の画面設定モデルを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 画面設定モデル
     */
    public abstract FormInputInitPrefarence getInitPref();

}
