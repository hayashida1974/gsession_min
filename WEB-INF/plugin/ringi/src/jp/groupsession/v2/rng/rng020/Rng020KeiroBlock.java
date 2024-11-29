package jp.groupsession.v2.rng.rng020;

import java.util.HashMap;
import java.util.Map;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng110keiro.EnumKeiroKbn;
import jp.groupsession.v2.rng.rng110keiro.KeiroInCondition;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogParamModel;

/**
 *
 * <br>[機  能] 経路ブロック内オブジェクト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng020KeiroBlock {
    /** 経路種別*/
    private int keiroKbn__ = EnumKeiroKbn.FREESET_VAL;

    /** 経路ステップ 設定*/
    private Map<Integer, Rng020Keiro> keiroMap__ = new HashMap<>();
    /** 行削除*/
    private String[] deleteRow__;
    /** 行追加*/
    private String[] addRow__;
    /** 行移動（上） */
    private String[] upRow__;
    /** 行移動（下） */
    private String[] downRow__;
    /** 稟議テンプレート経路SID */
    private int rtkSid__ = 0;

    /** スクロール位置*/
    private int scrollY__;
    /**非表示設定 経路ステップ使用条件を満たさない場合にhiddenとして非表示にする*/
    private int hidden__ = 0;
    /**前回描画時の非表示設定 */
    private int prevHidden__ = 0;
    /** 経路ステップ 設定*/
    private Rng110KeiroDialogParamModel pref__ = new Rng110KeiroDialogParamModel();

    /** 経路使用条件リスト*/
    private Map<String, KeiroInCondition> inCond__ = new HashMap<>();

    /** スキップ可能フラグ 0:スキップ不可 1:スキップ可能 */
    private int skipKyoka__ = RngConst.RNG_DISABLE_SKIP;
    /** スキップ時に承認される経路か 0:承認されない 1:承認される */
    private int apprFlg__ = RngConst.RNG_RNCSTATUS_NOSET;

    /** ユーザが1件もいない場合スキップ*/
    private int skipNonUser__ = 0;

    /** 選択ユーザ不在フラグ 1:存在 0:不在*/
    private int selectUserNone__ = 0;


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
     * <p>keiroMap を取得します。
     * @return keiroMap
     */
    public Map<Integer, Rng020Keiro> getKeiroMap() {
        return keiroMap__;
    }
    /**
     * <p>keiro を取得します。
     * @return keiro
     */
    public Rng020Keiro getKeiroSingle() {
        return getKeiro("0");
    }
    /**
     * <p>keiro を取得します。
     * @param key 行番号
     * @return keiro
     */
    public Rng020Keiro getKeiro(String key) {
        Integer rowNo = Integer.valueOf(key);
        if (keiroMap__.containsKey(rowNo)) {
            return keiroMap__.get(rowNo);
        }
        Rng020Keiro ret = new Rng020Keiro();
        keiroMap__.put(rowNo, ret);
        return ret;
    }

    /**
     * <p>keiroMap をセットします。
     * @param keiroMap keiroMap
     */
    public void setKeiroMap(Map<Integer, Rng020Keiro> keiroMap) {
        keiroMap__ = keiroMap;
    }
    /**
     * <p>keiroMap をセットします。
     * @param rowNo 行番号
     * @param keiro keiro
     */
    public void setKeiro(Integer rowNo, Rng020Keiro keiro) {
        keiroMap__.put(rowNo, keiro);
    }
    /**
     * <p>keiroKbn を取得します。
     * @return keiroKbn
     */
    public int getKeiroKbn() {
        return keiroKbn__;
    }
    /**
     * <p>keiroKbn をセットします。
     * @param keiroKbn keiroKbn
     */
    public void setKeiroKbn(int keiroKbn) {
        keiroKbn__ = keiroKbn;
    }
    /**
     * <p>addRow を取得します。
     * @return addRow
     */
    public String[] getAddRow() {
        return addRow__;
    }

    /**
     * <p>addRow をセットします。
     * @param addRow addRow
     */
    public void setAddRow(String[] addRow) {
        addRow__ = addRow;
    }

    /**
     * <p>deleteRow を取得します。
     * @return deleteRow
     */
    public String[] getDeleteRow() {
        return deleteRow__;
    }

    /**
     * <p>deleteRow をセットします。
     * @param deleteRow deleteRow
     */
    public void setDeleteRow(String[] deleteRow) {
        deleteRow__ = deleteRow;
    }
    /**
     * <p>upRow を取得します。
     * @return upRow
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#upRow__
     */
    public String[] getUpRow() {
        return upRow__;
    }
    /**
     * <p>upRow をセットします。
     * @param upRow upRow
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#upRow__
     */
    public void setUpRow(String[] upRow) {
        upRow__ = upRow;
    }
    /**
     * <p>downRow を取得します。
     * @return downRow
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#downRow__
     */
    public String[] getDownRow() {
        return downRow__;
    }
    /**
     * <p>downRow をセットします。
     * @param downRow downRow
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#downRow__
     */
    public void setDownRow(String[] downRow) {
        downRow__ = downRow;
    }

    /**
    *
    * <br>[機  能] 行追加スクリプトを出力
    * <br>[解  説]
    * <br>[備  考]
    * @param name 要素へのフォーム参照名
    * @return script
    */
    public String outputAddRowSclipt(String name) {
       StringBuilder sb = new StringBuilder();
       sb.append("$('<input>', {");
       sb.append("    type: 'hidden', ");
       sb.append("    name: '" + name + ".addRow', ");
       sb.append("    value:"); sb.append(getKeiroMap().size());
       sb.append("    }).appendTo($(this).parent()); ");
       sb.append(outputSetScrollYSclipt(name));
       return sb.toString();

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
       sb.append("$('input[name=\\'" + name + ".scrollY\\']').remove();");

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
    * <br>[機  能] 行削除スクリプトを出力
    * <br>[解  説]
    * <br>[備  考]
    * @param name 要素へのフォーム参照名
    * @param rowNo 削除対象行番号
    * @return script
    */
    public String outputDeleteRowSclipt(String name, int rowNo) {
      StringBuilder sb = new StringBuilder();
      sb.append("$('<input>', {");
      sb.append("    type: 'hidden', ");
      sb.append("    name: '" + name + ".deleteRow', ");
      sb.append("    value:"); sb.append(rowNo);
      sb.append("    }).appendTo($(this).parent()); ");
      sb.append(outputSetScrollYSclipt(name));
      return sb.toString();
    }

    /**
    * <br>[機  能] 行の上移動スクリプトを出力
    * <br>[解  説]
    * <br>[備  考]
    * @param name 要素へのフォーム参照名
    * @param rowNo 削除対象行番号
    * @return script
    */
    public String outputUpRowSclipt(String name, int rowNo) {
      StringBuilder sb = new StringBuilder();
      sb.append("$('<input>', {");
      sb.append("    type: 'hidden', ");
      sb.append("    name: '" + name + ".upRow', ");
      sb.append("    value:"); sb.append(rowNo);
      sb.append("    }).appendTo($(this).parent()); ");
      sb.append(outputSetScrollYSclipt(name));
      return sb.toString();
    }

    /**
    * <br>[機  能] 行の下移動スクリプトを出力
    * <br>[解  説]
    * <br>[備  考]
    * @param name 要素へのフォーム参照名
    * @param rowNo 削除対象行番号
    * @return script
    */
    public String outputDownRowSclipt(String name, int rowNo) {
      StringBuilder sb = new StringBuilder();
      sb.append("$('<input>', {");
      sb.append("    type: 'hidden', ");
      sb.append("    name: '" + name + ".downRow', ");
      sb.append("    value:"); sb.append(rowNo);
      sb.append("    }).appendTo($(this).parent()); ");
      sb.append(outputSetScrollYSclipt(name));
      return sb.toString();
    }


    /**
     * <p>hidden を取得します。
     * @return hidden
     */
    public int getHidden() {
        return hidden__;
    }
    /**
     * <p>hidden をセットします。
     * @param hidden hidden
     */
    public void setHidden(int hidden) {
        hidden__ = hidden;
    }
    /**
     * <p>inCond を取得します。
     * @return inCond
     */
    public Map<String, KeiroInCondition> getInCond() {
        return inCond__;
    }
    /**
     * <p>inCond をセットします。
     * @param inCond inCond
     */
    public void setInCond(Map<String, KeiroInCondition> inCond) {
        inCond__ = inCond;
    }
    /**
     * <p>prevHidden を取得します。
     * @return prevHidden
     */
    public int getPrevHidden() {
        return prevHidden__;
    }
    /**
     * <p>prevHidden をセットします。
     * @param prevHidden prevHidden
     */
    public void setPrevHidden(int prevHidden) {
        prevHidden__ = prevHidden;
    }
    /**
     * <p>keiroSkip を取得します。
     * @return keiroSkip
     */
    public int getKeiroSkip() {
        return pref__.getSkip();
    }
    /**
     * <p>koetuKyoka を取得します。
     * @return koetuKyoka
     */
    public int getKoetuKyoka() {
        return pref__.getKouetu();
    }
    /**
     * <p>addStepKyoka を取得します。
     * @return addStepKyoka
     */
    public int getAddStepKyoka() {
        return pref__.getAddkeiro();
    }
    /**
     * <p>rtkSid を取得します。
     * @return rtkSid
     */
    public int getRtkSid() {
        return rtkSid__;
    }
    /**
     * <p>rtkSid をセットします。
     * @param rtkSid rtkSid
     */
    public void setRtkSid(int rtkSid) {
        rtkSid__ = rtkSid;
    }
    /**
     * <p>skipKyoka を取得します。
     * @return skipKyoka
     */
    public int getSkipKyoka() {
        return skipKyoka__;
    }
    /**
     * <p>skipKyoka をセットします。
     * @param skipKyoka skipKyoka
     */
    public void setSkipKyoka(int skipKyoka) {
        skipKyoka__ = skipKyoka;
    }
    /**
     * <p>apprFlg を取得します。
     * @return apprFlg
     */
    public int getApprFlg() {
        return apprFlg__;
    }
    /**
     * <p>apprFlg をセットします。
     * @param apprFlg apprFlg
     */
    public void setApprFlg(int apprFlg) {
        apprFlg__ = apprFlg;
    }
    /**
     * <p>kouetuSiji を取得します。
     * @return kouetuSiji
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#kouetuSiji__
     */
    public int getKouetuSiji() {
        return pref__.getKouetuSiji();
    }
    /**
     * <p>pref を取得します。
     * @return pref
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#pref__
     */
    public Rng110KeiroDialogParamModel getPref() {
        return pref__;
    }
    /**
     * <p>pref をセットします。
     * @param pref pref
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#pref__
     */
    public void setPref(Rng110KeiroDialogParamModel pref) {
        pref__ = pref;
    }
    /**
     * <p>ownSingi を取得します。
     * @return ownSingi
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#ownSingi__
     */
    public int getOwnSingi() {
        return pref__.getOwn();
    }
    /**
     *
     * <br>[機  能] モバイル版行移動ボタンイベント
     * <br>[解  説]
     * <br>[備  考]
     * @param key 対象行番号
     * @param values ボタン表示文字列
     */
    public void setUpBtnMap(String key, String[] values) {
        setUpRow(new String[] {key});
    }
    /**
     *
     * <br>[機  能] モバイル版行移動ボタンイベント
     * <br>[解  説]
     * <br>[備  考]
     * @param key 対象行番号
     * @param values ボタン表示文字列
     */
    public void setDownBtnMap(String key, String[] values) {
        setDownRow(new String[] {key});
    }
    /**
     *
     * <br>[機  能] モバイル版行削除ボタンイベント
     * <br>[解  説]
     * <br>[備  考]
     * @param key 対象行番号
     * @param values ボタン表示文字列
     */
    public void setDelBtnMap(String key, String[] values) {
        setDeleteRow(new String[] {key});
    }
    /**
     * <p>skipNonUser を取得します。
     * @return skipNonUser
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#skipNonUser__
     */
    public int getSkipNonUser() {
        return skipNonUser__;
    }
    /**
     * <p>skipNonUser をセットします。
     * @param skipNonUser skipNonUser
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#skipNonUser__
     */
    public void setSkipNonUser(int skipNonUser) {
        skipNonUser__ = skipNonUser;
    }
    /**
     * <p>selectUserNone を取得します。
     * @return selectUserNone
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#selectUserNone__
     */
    public int getSelectUserNone() {
        return selectUserNone__;
    }
    /**
     * <p>selectUserNone をセットします。
     * @param selectUserNone selectUserNone
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroBlock#selectUserNone__
     */
    public void setSelectUserNone(int selectUserNone) {
        selectUserNone__ = selectUserNone;
    }

}