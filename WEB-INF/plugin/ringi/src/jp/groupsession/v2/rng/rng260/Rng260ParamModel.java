package jp.groupsession.v2.rng.rng260;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng040.Rng040ParamModel;
import jp.groupsession.v2.rng.ui.parts.dairinin.DairininSelector;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 稟議 管理者設定 基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng260ParamModel extends Rng040ParamModel {

    /** 代理人終了期間設定*/
    private int rng260DairiLast__ = RngConst.RNG_DAIRI_NOT_KIKAN;

    /** 対象ユーザ選択*/
    private UserGroupSelectModel usrgroupselect__ = new UserGroupSelectModel();
    /** 対象ユーザ選択*/
    private DairininSelector usrgroupselector__ = null;

    /** 代理人開始期間*/
    private String rng260DairiStart__ = null;
    /** 代理人終了期間*/
    private String rng260DairiFinish__ = null;

    /** ユーザ選択グループコンボ*/
    private ArrayList<LabelValueBean> rng260GroupCombo__ = new ArrayList<LabelValueBean>();
    /** ユーザ選択ユーザコンボ*/
    private List<UsrLabelValueBean> rng260UserCombo__ = new ArrayList<UsrLabelValueBean>();
    /** ユーザ選択コンボ選択ユーザ*/
    private int rng260User__ = 0;
    /** ユーザ選択コンボ選択グループ*/
    private int rng260GrpSid__ = 0;

    /** 入力エラー*/
    private int rng260Error__ = 0;

    /**
     * <p>rng260DairiLast を取得します。
     * @return rng260DairiLast
     */
    public int getRng260DairiLast() {
        return rng260DairiLast__;
    }
    /**
     * <p>rng260DairiLast をセットします。
     * @param rng260DairiLast rng260DairiLast
     */
    public void setRng260DairiLast(int rng260DairiLast) {
        rng260DairiLast__ = rng260DairiLast;
    }
    /**
     * <p>rng260DairiStart を取得します。
     * @return rng260DairiStart
     */
    public String getRng260DairiStart() {
        return rng260DairiStart__;
    }
    /**
     * <p>rng260DairiStart をセットします。
     * @param rng260DairiStart rng260DairiStart
     */
    public void setRng260DairiStart(String rng260DairiStart) {
        rng260DairiStart__ = rng260DairiStart;
    }
    /**
     * <p>rng260DairiFinish を取得します。
     * @return rng260DairiFinish
     */
    public String getRng260DairiFinish() {
        return rng260DairiFinish__;
    }
    /**
     * <p>rng260DairiFinish をセットします。
     * @param rng260DairiFinish rng260DairiFinish
     */
    public void setRng260DairiFinish(String rng260DairiFinish) {
        rng260DairiFinish__ = rng260DairiFinish;
    }
    /**
     * <p>usrgroupselect を取得します。
     * @return usrgroupselect
     */
    public UserGroupSelectModel getUsrgroupselect() {
        return usrgroupselect__;
    }
    /**
     * <p>usrgroupselect をセットします。
     * @param usrgroupselect usrgroupselect
     */
    public void setUsrgroupselect(UserGroupSelectModel usrgroupselect) {
        usrgroupselect__ = usrgroupselect;
    }
    /**
     * <p>usrgroupselector を取得します。
     * @return usrgroupselector
     * @see jp.groupsession.v2.rng.rng260.Rng260ParamModel#usrgroupselector__
     */
    public DairininSelector getUsrgroupselector() {
        return usrgroupselector__;
    }
    /**
     * <p>usrgroupselector をセットします。
     * @param usrgroupselector usrgroupselector
     * @see jp.groupsession.v2.rng.rng260.Rng260ParamModel#usrgroupselector__
     */
    public void setUsrgroupselector(DairininSelector usrgroupselector) {
        usrgroupselector__ = usrgroupselector;
    }
    /**
     * <p>rng260User を取得します。
     * @return rng260User
     */
    public int getRng260User() {
        return rng260User__;
    }
    /**
     * <p>rng260User をセットします。
     * @param rng260User rng260User
     */
    public void setRng260User(int rng260User) {
        rng260User__ = rng260User;
    }
    /**
     * <p>rng260Error を取得します。
     * @return rng260Error
     */
    public int getRng260Error() {
        return rng260Error__;
    }
    /**
     * <p>rng260Error をセットします。
     * @param rng260Error rng260Error
     */
    public void setRng260Error(int rng260Error) {
        rng260Error__ = rng260Error;
    }
    /**
     * <p>rng260GroupCombo を取得します。
     * @return rng260GroupCombo
     */
    public ArrayList<LabelValueBean> getRng260GroupCombo() {
        return rng260GroupCombo__;
    }
    /**
     * <p>rng260GroupCombo をセットします。
     * @param rng260GroupCombo rng260GroupCombo
     */
    public void setRng260GroupCombo(ArrayList<LabelValueBean> rng260GroupCombo) {
        rng260GroupCombo__ = rng260GroupCombo;
    }
    /**
     * <p>rng260GrpSid を取得します。
     * @return rng260GrpSid
     */
    public int getRng260GrpSid() {
        return rng260GrpSid__;
    }
    /**
     * <p>rng260GrpSid をセットします。
     * @param rng260GrpSid rng260GrpSid
     */
    public void setRng260GrpSid(int rng260GrpSid) {
        rng260GrpSid__ = rng260GrpSid;
    }
    /**
     * <p>rng260UserCombo を取得します。
     * @return rng260UserCombo
     */
    public List<UsrLabelValueBean> getRng260UserCombo() {
        return rng260UserCombo__;
    }
    /**
     * <p>rng260UserCombo をセットします。
     * @param rng260UserCombo rng260UserCombo
     */
    public void setRng260UserCombo(List<UsrLabelValueBean> rng260UserCombo) {
        rng260UserCombo__ = rng260UserCombo;
    }
}
