package jp.groupsession.v2.rng.rng270;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.rng080.Rng080Form;
import jp.groupsession.v2.rng.ui.parts.dairinin.DairininSelector;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議個人設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng270Form extends Rng080Form {

    /** 代理人終了期間設定*/
    private int rng270DairiLast__ = RngConst.RNG_DAIRI_NOT_KIKAN;

    /** 代理人開始期間*/
    private String rng270DairiStart__ = null;
    /** 代理人終了期間*/
    private String rng270DairiFinish__ = null;

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
                        DairininSelector.OPTIONPARAM_SESSIONSID)
                .build();

    /** 初期表示フラグ 0=初期 1=初期済み */
    private String rng270ScrollFlg__ = "0";


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
        String sStart = gsMsg.getMessage(reqMdl, "cmn.start");
        String sFinish = gsMsg.getMessage(reqMdl, "main.src.man250.30");

        //期間選択
        boolean bOut = false;
        if (rng270DairiStart__.length() != 10) {
            bOut = true;
        }
        for (int i = 0; i < rng270DairiStart__.length(); i++) {
            char c = rng270DairiStart__.charAt(i);
            if ((c < '0' || c > '9') && (c != '/')) {
                bOut = true;
            }
        }
        if (rng270DairiLast__ == 0) {
            if (rng270DairiFinish__.length() != 10) {
                bOut = true;
            }
            for (int i = 0; i < rng270DairiFinish__.length(); i++) {
                char c = rng270DairiFinish__.charAt(i);
                if ((c < '0' || c > '9') && (c != '/')) {
                    bOut = true;
                }
            }
            if (!bOut) {
                UDate start = new UDate();
                UDate finish = new UDate();
                start.setDate(rng270DairiStart__.replaceAll("/", ""));
                finish.setDate(rng270DairiFinish__.replaceAll("/", ""));
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
     * コンストラクタ
     */
    public Rng270Form() {
    }

    /**
     * <p>rng270DairiLast を取得します。
     * @return rng270DairiLast
     */
    public int getRng270DairiLast() {
        return rng270DairiLast__;
    }

    /**
     * <p>rng270DairiLast をセットします。
     * @param rng270DairiLast rng270DairiLast
     */
    public void setRng270DairiLast(int rng270DairiLast) {
        rng270DairiLast__ = rng270DairiLast;
    }

    /**
     * <p>rng270DairiStart を取得します。
     * @return rng270DairiStart
     */
    public String getRng270DairiStart() {
        return rng270DairiStart__;
    }

    /**
     * <p>rng270DairiStart をセットします。
     * @param rng270DairiStart rng270DairiStart
     */
    public void setRng270DairiStart(String rng270DairiStart) {
        rng270DairiStart__ = rng270DairiStart;
    }

    /**
     * <p>rng270DairiFinish を取得します。
     * @return rng270DairiFinish
     */
    public String getRng270DairiFinish() {
        return rng270DairiFinish__;
    }

    /**
     * <p>rng270DairiFinish をセットします。
     * @param rng270DairiFinish rng270DairiFinish
     */
    public void setRng270DairiFinish(String rng270DairiFinish) {
        rng270DairiFinish__ = rng270DairiFinish;
    }

    /**
     * <p>rng270ScrollFlg を取得します。
     * @return rng270ScrollFlg
     */
    public String getRng270ScrollFlg() {
        return rng270ScrollFlg__;
    }

    /**
     * <p>rng270ScrollFlg をセットします。
     * @param rng270ScrollFlg rng270ScrollFlg
     */
    public void setRng270ScrollFlg(String rng270ScrollFlg) {
        rng270ScrollFlg__ = rng270ScrollFlg;
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
     * @see jp.groupsession.v2.rng.rng270.Rng270Form#usrgroupselector__
     */
    public DairininSelector getUsrgroupselector() {
        return usrgroupselector__;
    }

    /**
     * <p>usrgroupselector をセットします。
     * @param usrgroupselector usrgroupselector
     * @see jp.groupsession.v2.rng.rng270.Rng270Form#usrgroupselector__
     */
    public void setUsrgroupselector(DairininSelector usrgroupselector) {
        usrgroupselector__ = usrgroupselector;
    }
}
