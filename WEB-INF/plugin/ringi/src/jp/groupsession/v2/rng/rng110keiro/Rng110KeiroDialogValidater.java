package jp.groupsession.v2.rng.rng110keiro;

import java.util.HashSet;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.EnumUtil;
import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RngValidate;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm.TargetPosSel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
/**
 *
 * <br>[機  能]  経路ステップの入力チェック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng110KeiroDialogValidater {
    /** 経路区分 Utilクラス*/
    private EnumUtil<EnumKeiroKbn> keiroKbnUtil__ = new EnumUtil<EnumKeiroKbn>(EnumKeiroKbn.class);
    /** 稟議テンプレート仕様バージョン*/
    private int rtpSpecVer__ = RngConst.RNG_RTP_SPEC_VER_A480;
    /**
     *
     * コンストラクタ
     */
    public Rng110KeiroDialogValidater() {
    }
    /**
     *
     * コンストラクタ
     * @param specVer 稟議テンプレート仕様バージョン
     */
    public Rng110KeiroDialogValidater(int specVer) {
        rtpSpecVer__ = specVer;
    }
    /**
     *
     * <br>[機  能] 経路ステップの入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param errEntryName エラー対象ステップ識別名
     * @param param 検証対象
     * @return ActionErrors;
     */
    public ActionErrors validate(
            RequestModel reqMdl,
            String errEntryName,
            IRng110KeiroDialogParam param) {
        ActionMessage msg = null;
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);
        //経路使用条件入力チェック
        for (Entry<String, KeiroInCondition> entry:param.getInCondMap().entrySet()) {
            KeiroInCondition inCond = entry.getValue();
            if ("template".equals(entry.getKey())) {
                continue;
            }
            int condKbn = NullDefault.getInt(inCond.getCondKbn().getSelected(), 2);
            try {
                switch (EnumKeiroInConditionKbn.valueOf(condKbn)) {
                case CONDKBN_GROUP:
                    if (ArrayUtils.isEmpty(inCond.getSelGrp().getSelected())) {
                        msg = new ActionMessage("error.select.required.text",
                                gsMsg.getMessage("rng.rng110.10") + ":"
                                + gsMsg.getMessage(
                                       EnumKeiroInConditionKbn.CONDKBN_GROUP.getMsgKey()));
                        StrutsUtil.addMessage(errors, msg, "rng.rng110.10");
                    }
                    break;
                case CONDKBN_POS:
                    if (ArrayUtils.isEmpty(inCond.getSelPos().getSelected())) {
                        msg = new ActionMessage("error.select.required.text",
                                gsMsg.getMessage("rng.rng110.10") + ":"
                                + gsMsg.getMessage(
                                        EnumKeiroInConditionKbn.CONDKBN_POS.getMsgKey()));
                        StrutsUtil.addMessage(errors, msg, "rng.rng110.10");
                    }
                    break;
                case CONDKBN_INPUT:
                    errors = RngValidate.validateCmnFieldText(
                            errors,
                            gsMsg.getMessage("rng.rng110.10") + gsMsg.getMessage("rng.rng110.31"),
                            inCond.getFormId(),
                            "inCondFormID",
                            KeiroInCondition.MAXTEXTSIZE_INCOND_FORMID,
                            true);
                    if (errors.size() > 0) {
                        continue;
                    }
                    errors = RngValidate.validateCmnFieldText(
                            errors,
                            gsMsg.getMessage("rng.rng110.10") + gsMsg.getMessage("rng.rng110.32"),
                            inCond.getFormValue(),
                            "inCondValue",
                            KeiroInCondition.MAXTEXTSIZE_INCOND_VALUE,
                            true);
                    if (errors.size() > 0) {
                        continue;
                    }
                    break;
                default:
                }
            } catch (EnumOutRangeException e) {
                msg = new ActionMessage("error.select3.required.text",
                        gsMsg.getMessage("rng.rng110.10"));
                StrutsUtil.addMessage(errors, msg, "rng.rng110.10");
            }
        }
        //経路コメントチェック
        errors = RngValidate.validateCmnFieldText(
                errors,
                gsMsg.getMessage("rng.rng110.43"),
                param.getKeiroComment(),
                "keiroComment",
                RngConst.KEIRO_COMMENT_LENGTH,
                false);
        //対象選択チェック
        String[] userTarget = param.getUsrgrouptarget().getSelected("target");
        try {
            if (rtpSpecVer__ == RngConst.RNG_RTP_SPEC_VER_INIT) {
                if (keiroKbnUtil__.valueOf(param.getKeiroKbn()) != EnumKeiroKbn.FREESET) {
                    msg = new ActionMessage("error.select3.required.text",
                            gsMsg.getMessage("rng.rng090.04"));
                    StrutsUtil.addMessage(errors, msg, "rng.rng090.04");
                }
            }
            switch (keiroKbnUtil__.valueOf(param.getKeiroKbn())) {
            case FREESET:
            case USERSEL:
                break;
            case USERTARGET:
                if (userTarget == null || userTarget.length <= 0) {
                    msg = new ActionMessage("error.select.required.text",
                            gsMsg.getMessage("rng.rng110.12"));
                    StrutsUtil.addMessage(errors, msg, "usrgroutarget");
                }
                break;
            case POSTARGET:
                __validatePostTarget(errors, param, gsMsg);
                break;
            case GROUPSEL:
                break;
            case BOSSTARGET:
                if (param.getBossStepCnt() <= 0 || param.getBossStepCnt() > 10) {
                    msg = new ActionMessage("error.input.addhani.text",
                            gsMsg.getMessage("rng.rng110.19"), 1, 10);
                    StrutsUtil.addMessage(errors, msg, "rng.rng110.19");
                }
                if (param.getBossStepCntMin() <= 0 || param.getBossStepCntMin() > 10) {
                    msg = new ActionMessage("error.input.addhani.text",
                            gsMsg.getMessage("rng.rng110.30"), 1, 10);
                    StrutsUtil.addMessage(errors, msg, "rng.rng110.30");
                }
                break;
            default:
                msg = new ActionMessage("error.select3.required.text",
                        gsMsg.getMessage("rng.rng090.04"));
                StrutsUtil.addMessage(errors, msg, "rng.rng090.04");
                break;
            }
        } catch (EnumOutRangeException e) {
            msg = new ActionMessage("error.select3.required.text",
                    gsMsg.getMessage("rng.rng090.04"));
            StrutsUtil.addMessage(errors, msg, "rng.rng090.04");

        }
        if (param.getOutcondition() == 2 || param.getOutcondition() == 3) {
            if (!StringUtil.isNullZeroString(param.getOutcond_threshold())) {
                RngValidate.validateNumberInt(errors,
                    param.getOutcond_threshold(),
                    gsMsg.getMessage("cmn.threshold"), 3);
            } else {
                msg = new ActionMessage("error.select.required.text",
                        gsMsg.getMessage("cmn.threshold"));
                StrutsUtil.addMessage(errors, msg, "cmn.threshold");
            }
        }
        if (param.getOutcondition() == RngConst.RNG_OUT_CONDITION_NUMBER) {
            int threshold = NullDefault.getInt(param.getOutcond_threshold(), -1);
            if (threshold < 1) {
                msg = new ActionMessage("error.input.number.over",
                        gsMsg.getMessage("cmn.threshold"), 1);
                StrutsUtil.addMessage(errors, msg, "cmn.threshold");
            }

            try {
                boolean isThresholdErr = false; // 承認数エラーフラグ

                EnumKeiroKbn keiroKbn = EnumKeiroKbn.valueOf(param.getKeiroKbn());
                if (keiroKbn == EnumKeiroKbn.USERTARGET) {
                    isThresholdErr = __isCheckThresholdCount(param);
                }
                if (keiroKbn == EnumKeiroKbn.USERSEL) {
                    if (param.getMultisel() == 0) {
                        // 単一選択
                        if (threshold > 1) {
                            // 承認数(設定)が1人以外 → 選択対象人数が足りないのでエラー
                            isThresholdErr = true;
                        }
                    } else {
                        // 複数選択
                        isThresholdErr = __isCheckThresholdCount(param);
                    }
                }

                if (isThresholdErr) {
                    // 承認数不足によるエラー
                    msg = new ActionMessage("error.keiro.canreach.outcondition",
                            gsMsg.getMessage("cmn.threshold"));
                    StrutsUtil.addMessage(errors, msg, "cmn.threshold");
                }

            } catch (EnumOutRangeException e) {
                msg = new ActionMessage("error.select3.required.text",
                        gsMsg.getMessage("rng.rng090.04"));
                StrutsUtil.addMessage(errors, msg, "rng.rng090.04");
            }
        }
        if (param.getOutcondition() == RngConst.RNG_OUT_CONDITION_RATE) {
            int threshold = NullDefault.getInt(param.getOutcond_threshold(), -1);
            if (threshold < 1 || threshold > 100) {
                msg = new ActionMessage("error.input.addhani.text",
                        gsMsg.getMessage("cmn.threshold"), 1, 100);
                StrutsUtil.addMessage(errors, msg, "cmn.threshold");
            }
        }
        return errors;
    }

    /**
     *
     * <br>[機  能] 承認数(設定値)に対して選択対象数が不足しているかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param param 検証対象パラメータ
     * @return 不足判定(true:不足している / false:不足していない)
     */
    private boolean __isCheckThresholdCount(IRng110KeiroDialogParam param) {
        int threshold = NullDefault.getInt(param.getOutcond_threshold(), 0);

        String[] selSid = param.getUsrgroupselect().getSelected("target");
        int cntUsr = 0;
        boolean grpSelected = false;
        if (selSid != null && selSid.length > 0) {
            for (String sid : selSid) {
                if (StringUtil.isNullZeroString(sid)) {
                    continue;
                }
                if (sid.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                    grpSelected = true;
                    break;
                } else {
                    if (NullDefault.getInt(sid, -1) > GSConstUser.USER_RESERV_SID) {
                        cntUsr++;
                    }
                }
            }
            if (cntUsr < threshold && !grpSelected) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * <br>[機  能] 役職選択の入力チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors アクションエラー
     * @param param 経路設定パラメータ
     * @param gsMsg メッセージオブジェクト
     */
    private void __validatePostTarget(ActionErrors errors,
            IRng110KeiroDialogParam param,
            GsMessage gsMsg) {
        boolean hasError = true;
        for (String key : param.getTargetposMap().keySet()) {
            if (!key.equals("template")) {
                hasError = false;
                break;
            }
        }
        if (hasError) {
            ActionMessage msg = new ActionMessage("error.select.required.text",
                    gsMsg.getMessage("rng.rng110.12"));
            StrutsUtil.addMessage(errors, msg, "targetpos");
            return;
        }
        HashSet<TargetPosSel> juhukuChkSet = new HashSet<>();
        for (Entry<String, TargetPosSel> entry : param.getTargetposMap().entrySet()) {
            if (entry.getKey().equals("template")) {
                continue;
            }
            if (juhukuChkSet.contains(entry.getValue())) {
                //重複エラー
                ActionMessage msg =
                    new ActionMessage("error.select.dup.list", gsMsg.getMessage("rng.rng110.12"));
                StrutsUtil.addMessage(errors, msg, "error.select.dup.list");
                break;
            }
            juhukuChkSet.add(entry.getValue());
        }

    }
}
