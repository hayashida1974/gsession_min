package jp.groupsession.v2.rng.rng180;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng040.Rng040Form;

/**
 * <br>[機  能] 稟議 管理者設定 基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng180Form extends Rng040Form {
    /** 初期表示区分 */
    private int rng180initFlg__ = RngConst.DSP_FIRST;
    /** 稟議の削除 管理者/ユーザ */
    private int rng180delKbn__ = 0;
    /** 汎用稟議テンプレート使用設定  */
    private int rng180HanyoFlg__ = RngConst.RAR_HANYO_FLG_YES;
    /** 個人テンプレート使用設定  */
    private int rng180TemplatePFlg__ = RngConst.RAR_TEMPLATE_PERSONAL_FLG_YES;
    /** 個人経路テンプレート使用設定  */
    private int rng180KeiroPFlg__ = RngConst.RAR_KEIRO_PERSONAL_FLG_YES;
    /** 申請ID*/
    private int rng180sinseiKbn__ = 0;
    /** デフォルトID*/
    private int rng180defaultId__ = 0;
    /** 重複設定*/
    private int rng180Overlap__ = RngConst.RAR_SINSEI_KYOKA;
    /** テンプレートリスト*/
    private List<LabelValueBean> rng180TempList__ = null;

    /**
     * <p>rng180delKbn を取得します。
     * @return rng180delKbn
     */
    public int getRng180delKbn() {
        return rng180delKbn__;
    }
    /**
     * <p>rng180delKbn をセットします。
     * @param rng180delKbn rng180delKbn
     */
    public void setRng180delKbn(int rng180delKbn) {
        rng180delKbn__ = rng180delKbn;
    }
    /**
     * <p>rng180initFlg を取得します。
     * @return rng180initFlg
     */
    public int getRng180initFlg() {
        return rng180initFlg__;
    }
    /**
     * <p>rng180initFlg をセットします。
     * @param rng180initFlg rng180initFlg
     */
    public void setRng180initFlg(int rng180initFlg) {
        rng180initFlg__ = rng180initFlg;
    }
    /**
     * <p>rng180HanyoFlg を取得します。
     * @return rng180HanyoFlg
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180HanyoFlg__
     */
    public int getRng180HanyoFlg() {
        return rng180HanyoFlg__;
    }
    /**
     * <p>rng180HanyoFlg をセットします。
     * @param rng180HanyoFlg rng180HanyoFlg
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180HanyoFlg__
     */
    public void setRng180HanyoFlg(int rng180HanyoFlg) {
        rng180HanyoFlg__ = rng180HanyoFlg;
    }
    /**
     * <p>rng180TemplatePFlg を取得します。
     * @return rng180TemplatePFlg
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180TemplatePFlg__
     */
    public int getRng180TemplatePFlg() {
        return rng180TemplatePFlg__;
    }
    /**
     * <p>rng180TemplatePFlg をセットします。
     * @param rng180TemplatePFlg rng180TemplatePFlg
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180TemplatePFlg__
     */
    public void setRng180TemplatePFlg(int rng180TemplatePFlg) {
        rng180TemplatePFlg__ = rng180TemplatePFlg;
    }
    /**
     * <p>rng180KeiroPFlg を取得します。
     * @return rng180KeiroPFlg
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180KeiroPFlg__
     */
    public int getRng180KeiroPFlg() {
        return rng180KeiroPFlg__;
    }
    /**
     * <p>rng180KeiroPFlg をセットします。
     * @param rng180KeiroPFlg rng180KeiroPFlg
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180KeiroPFlg__
     */
    public void setRng180KeiroPFlg(int rng180KeiroPFlg) {
        rng180KeiroPFlg__ = rng180KeiroPFlg;
    }
    /**
     * <p>rng180sinseiKbn を取得します。
     * @return rng180sinseiKbn
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180sinseiKbn__
     */
    public int getRng180sinseiKbn() {
        return rng180sinseiKbn__;
    }
    /**
     * <p>rng180sinseiKbn をセットします。
     * @param rng180sinseiKbn rng180sinseiKbn
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180sinseiKbn__
     */
    public void setRng180sinseiKbn(int rng180sinseiKbn) {
        rng180sinseiKbn__ = rng180sinseiKbn;
    }
    /**
     * <p>rng180defaultId を取得します。
     * @return rng180defaultId
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180defaultId__
     */
    public int getRng180defaultId() {
        return rng180defaultId__;
    }
    /**
     * <p>rng180defaultId をセットします。
     * @param rng180defaultId rng180defaultId
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180defaultId__
     */
    public void setRng180defaultId(int rng180defaultId) {
        rng180defaultId__ = rng180defaultId;
    }
    /**
     * <p>rng180Overlap を取得します。
     * @return rng180Overlap
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180Overlap__
     */
    public int getRng180Overlap() {
        return rng180Overlap__;
    }
    /**
     * <p>rng180Overlap をセットします。
     * @param rng180Overlap rng180Overlap
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180Overlap__
     */
    public void setRng180Overlap(int rng180Overlap) {
        rng180Overlap__ = rng180Overlap;
    }
    /**
     * <p>rng180TempList を取得します。
     * @return rng180TempList
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180TempList__
     */
    public List<LabelValueBean> getRng180TempList() {
        return rng180TempList__;
    }
    /**
     * <p>rng180TempList をセットします。
     * @param rng180TempList rng180TempList
     * @see jp.groupsession.v2.rng.rng180.Rng180Form#rng180TempList__
     */
    public void setRng180TempList(List<LabelValueBean> rng180TempList) {
        rng180TempList__ = rng180TempList;
    }

}
