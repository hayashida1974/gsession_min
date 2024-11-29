package jp.groupsession.v2.rng.rng260;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng040.Rng040Form;
import jp.groupsession.v2.rng.ui.parts.dairinin.DairininSelector;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 稟議 管理者設定 基本設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng260Form extends Rng040Form {

    /** 代理人終了期間設定*/
    private int rng260DairiLast__ = RngConst.RNG_DAIRI_NOT_KIKAN;

    /** 代理人開始期間*/
    private String rng260DairiStart__ = null;
    /** 代理人終了期間*/
    private String rng260DairiFinish__ = null;

    /** 対象ユーザ選択*/
    private UserGroupSelectModel usrgroupselect__ = new UserGroupSelectModel();
    /** 対象ユーザ選択*/
    private DairininSelector usrgroupselector__ =
            DairininSelector.builder()
                .chainGroupSelectionParamName("usrgroupselect.group.selectedSingle")
                .chainType(EnumSelectType.USER)
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
                .chainSelect(Select.builder()
                        .chainParameterName("usrgroupselect.selected(target)"))
                .chainOptionParameter(
                        DairininSelector.OPTIONPARAMKEY_TARGETUSRSID,
                        "rng260User")
                .build();

    /** ユーザ選択グループコンボ*/
    private ArrayList<LabelValueBean> rng260GroupCombo__ = new ArrayList<LabelValueBean>();
    /** ユーザ選択ユーザコンボ*/
    private List<UsrLabelValueBean> rng260UserCombo__ = new ArrayList<UsrLabelValueBean>();
    /** ユーザ選択コンボ選択ユーザ*/
    private int rng260User__ = 0;
    /** ユーザ選択コンボ選択グループ*/
    private int rng260GrpSid__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return errors エラー
     */
    public ActionErrors validateCheck(HttpServletRequest reqMdl) {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage();
        String user = gsMsg.getMessage(reqMdl, "cmn.user");
        String sStart = gsMsg.getMessage(reqMdl, "cmn.start");
        String sFinish = gsMsg.getMessage(reqMdl, "main.src.man250.30");

        //ユーザ選択
        if (rng260User__ <= 100) {
            ActionMessage msg = null;

            String eprefix = "ringi";
            msg = new ActionMessage("error.select.required.text", user);
            StrutsUtil.addMessage(
                   errors, msg, eprefix);
            return errors;
        }

        //期間選択
        boolean bOut = false;
        if (rng260DairiStart__.length() != 10) {
            bOut = true;
        }
        for (int i = 0; i < rng260DairiStart__.length(); i++) {
            char c = rng260DairiStart__.charAt(i);
            if ((c < '0' || c > '9') && (c != '/')) {
                bOut = true;
            }
        }
        if (rng260DairiLast__ == 0) {
            if (rng260DairiFinish__.length() != 10) {
                bOut = true;
            }
            for (int i = 0; i < rng260DairiFinish__.length(); i++) {
                char c = rng260DairiFinish__.charAt(i);
                if ((c < '0' || c > '9') && (c != '/')) {
                    bOut = true;
                }
            }
            if (!bOut) {
                UDate start = new UDate();
                UDate finish = new UDate();
                start.setDate(rng260DairiStart__.replaceAll("/", ""));
                finish.setDate(rng260DairiFinish__.replaceAll("/", ""));
                if (start.compareDateYMD(finish) == -1) {
                    bOut = true;
                }
            }
        }

        if (bOut) {
            ActionMessage msg = null;
            String eprefix = "ringi";
            msg = new ActionMessage("error.input.range.date", sStart, sFinish);
            StrutsUtil.addMessage(
                    errors, msg, eprefix);
        }
        return errors;
    }

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
     * @see jp.groupsession.v2.rng.rng260.Rng260Form#usrgroupselector__
     */
    public DairininSelector getUsrgroupselector() {
        return usrgroupselector__;
    }

    /**
     * <p>usrgroupselector をセットします。
     * @param usrgroupselector usrgroupselector
     * @see jp.groupsession.v2.rng.rng260.Rng260Form#usrgroupselector__
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
