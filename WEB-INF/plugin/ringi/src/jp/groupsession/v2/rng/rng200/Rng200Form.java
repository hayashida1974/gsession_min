package jp.groupsession.v2.rng.rng200;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngValidate;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.rng010.Rng010Form;

/**
 * <br>[機  能] 稟議 管理者設定 申請ID一覧のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng200Form extends Rng010Form {

    /** 検索キーワード*/
    private String rng200keyword__ = null;
    /** 申請IDリスト*/
    private List<RingiIdModel> rng200List__ = null;
    /** 編集SID*/
    private int rng200Sid__ = -1;
    /** デフォルトフォーマットSID*/
    private int rng200Default__;
    /** デフォルトフォーマット*/
    private String rng200DefFormat__ = null;
    /** 表示用フォーマットリスト*/
    private List<String> rng200DispList__ = new ArrayList<String>();
    /** パターン表示*/
    private List<String> rng200Pattern__ = new ArrayList<String>();

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return errors エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        //フォーマット
        errors = RngValidate.validateRadio(
                errors,                          //errors
                String.valueOf(rng200Default__), //チェックするフィールド
                "デフォルト");              //チェックするフィールドの文字列;

        return errors;
    }

    /**
     * <p>rng200keyword を取得します。
     * @return rng200keyword
     */
    public String getRng200keyword() {
        return rng200keyword__;
    }

    /**
     * <p>rng200keyword をセットします。
     * @param rng200keyword rng200keyword
     */
    public void setRng200keyword(String rng200keyword) {
        rng200keyword__ = rng200keyword;
    }

    /**
     * <p>rng020List を取得します。
     * @return rng020List
     */
    public List<RingiIdModel> getRng200List() {
        return rng200List__;
    }

    /**
     * <p>rng020List をセットします。
     * @param rng020List rng020List
     */
    public void setRng200List(List<RingiIdModel> rng020List) {
        rng200List__ = rng020List;
    }

    /**
     * <p>rng200Default を取得します。
     * @return rng200Default
     */
    public int getRng200Default() {
        return rng200Default__;
    }

    /**
     * <p>rng200Default をセットします。
     * @param rng200Default rng200Default
     */
    public void setRng200Default(int rng200Default) {
        rng200Default__ = rng200Default;
    }

    /**
     * <p>rng200DefFormat を取得します。
     * @return rng200DefFormat
     */
    public String getRng200DefFormat() {
        return rng200DefFormat__;
    }

    /**
     * <p>rng200DefFormat をセットします。
     * @param rng200DefFormat rng200DefFormat
     */
    public void setRng200DefFormat(String rng200DefFormat) {
        rng200DefFormat__ = rng200DefFormat;
    }

    /**
     * <p>rng200Sid を取得します。
     * @return rng200Sid
     */
    public int getRng200Sid() {
        return rng200Sid__;
    }

    /**
     * <p>rng200Sid をセットします。
     * @param rng200Sid rng200Sid
     */
    public void setRng200Sid(int rng200Sid) {
        rng200Sid__ = rng200Sid;
    }

    /**
     * <p>rng200DispList を取得します。
     * @return rng200DispList
     */
    public List<String> getRng200DispList() {
        return rng200DispList__;
    }

    /**
     * <p>rng200DispList をセットします。
     * @param rng200DispList rng200DispList
     */
    public void setRng200DispList(List<String> rng200DispList) {
        rng200DispList__ = rng200DispList;
    }

    /**
     * <p>rng200Pattern を取得します。
     * @return rng200Pattern
     */
    public List<String> getRng200Pattern() {
        return rng200Pattern__;
    }

    /**
     * <p>rng200Pattern をセットします。
     * @param rng200Pattern rng200Pattern
     */
    public void setRng200Pattern(List<String> rng200Pattern) {
        rng200Pattern__ = rng200Pattern;
    }


}
