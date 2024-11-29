package jp.groupsession.v2.cmn.formmodel.prefarence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] 自動計算設定用モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class CalcPrefarence extends jp.groupsession.v2.cmn.formmodel.Calc {
    /** フォーム選択用LabelValue*/
    private List<LabelValueBean> select__;
    /** 演算子選択用LabelValue*/
    private List<LabelValueBean> operands__;

    /**
     * <p>select を取得します。
     * @return select
     */
    public List<LabelValueBean> getSelect() {
        return select__;
    }

    /**
     * <p>select をセットします。
     * @param select select
     */
    public void setSelect(List<LabelValueBean> select) {
        select__ = select;
    }

    /**
     * <p>operands を取得します。
     * @return operands
     */
    public List<LabelValueBean> getOperands() {
        return operands__;
    }

    /**
     * <p>operands をセットします。
     * @param operands operands
     */
    public void setOperands(List<LabelValueBean> operands) {
        operands__ = operands;
    }
    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
    		throws SQLException, IOToolsException {
        super.dspInit(reqMdl, con);
        List<LabelValueBean> operandList = new ArrayList<>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        operandList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.operand.plus"),
                        Format.OPR_PLUS)
                );
        operandList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.operand.minus"),
                        Format.OPR_MINUS)
                );
        operandList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.operand.mul"),
                        Format.OPR_MUL)
                );
        operandList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.operand.per"),
                        Format.OPR_PER)
                );
        operandList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.operand.left"),
                        Format.OPR_LEFT)
                );
        operandList.add(
                new LabelValueBean(
                        gsMsg.getMessage("cmn.operand.right"),
                        Format.OPR_RIGHT)
                );

        setOperands(operandList);

    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        GsMessage gsMsg = new GsMessage(reqMdl);
       /* String teisuName = gsMsg.getMessage("cmn.teisu");
        for (Format format : getFormat()) {
            if (format.getType() == 0) {
                GSValidateCommon.validateNumberDecimal(errors,
                        format.getValue(),
                        teisuName, 20, true);
            }
        }*/
        GSValidateCommon.validateTextField(errors,
                getTanni(),
                "tanni",
                gsMsg.getMessage("ntp.102"),
                100,
                false);

        int errorsCount = errors.size();
        GSValidateCommon.validateNumberInt(errors,
                getKeta(),
                gsMsg.getMessage("cmn.keta"),
                1);

        if (errors.size() == errorsCount) {
            int nKeta = Integer.parseInt(getKeta());
            if (nKeta < 0 || nKeta > 5) {
                ActionMessage msg = null;
                msg = new ActionMessage("error.input.addhani.text",
                        gsMsg.getMessage("cmn.keta"), "0", "5");
                StrutsUtil.addMessage(errors, msg, "keta");
            }
        }

        if (!(getRound() == 0 || getRound() == 1 || getRound() == 2)) {
            ActionMessage msg = null;
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage("cmn.form.round"));
            StrutsUtil.addMessage(errors, msg, "round");
        }

        //数式入力チェック
        if (getSiki() == null || getSiki().length() == 0) {
            ActionMessage msg = null;
            msg = new ActionMessage("error.input.required.text",
                    gsMsg.getMessage("cmn.form.pref.calc"));
            StrutsUtil.addMessage(errors, msg, "siki");
        }

        // ()チェック
        if (errors.isEmpty()) {
            String siki = getSiki();
            char cLeft = '(';
            char cRight = ')';
            int nLeftCnt = 0;
            int nRightCnt = 0;
            boolean bError = false;
            for (char idx : siki.toCharArray()) {
                if (idx == cLeft) {
                    nLeftCnt++;
                } else if (idx == cRight) {
                    nRightCnt++;
                }
            }
            if (nLeftCnt != nRightCnt) {
                bError = true;
            }
            if (!bError) {
                int nZengoChk = 0;
                for (int idx = 0; idx < nLeftCnt; idx++) {
                    int nLeft = siki.indexOf("(");
                    int nRight = siki.indexOf(")");
                    if (nLeft < nRight) {
                        nZengoChk++;
                    } else {
                        if (nZengoChk == 0) {
                            bError = true;
                            break;
                        } else {
                            nZengoChk--;
                        }
                    }
                }
            }
            if (bError) {
                ActionMessage msg = null;
                msg = new ActionMessage("error.brackets");
                StrutsUtil.addMessage(errors, msg, "siki");
            }
        }
        //[]の代入式チェック
        if (errors.isEmpty()) {
            String siki = getSiki();
            char cLeft = '[';
            char cRight = ']';
            int nLeftCnt = 0;
            int nRightCnt = 0;
            boolean bError = false;
            for (char idx : siki.toCharArray()) {
                if (idx == cLeft) {
                    nLeftCnt++;
                } else if (idx == cRight) {
                    nRightCnt++;
                }
            }
            if (nLeftCnt != nRightCnt) {
                bError = true;
            }
            if (!bError) {
                int nZengoChk = 0;
                for (int idx = 0; idx < nLeftCnt; idx++) {
                    int nLeft = siki.indexOf("[");
                    int nRight = siki.indexOf("]");
                    if (nLeft < nRight) {
                        nZengoChk++;
                    } else {
                        if (nZengoChk == 0) {
                            bError = true;
                            break;
                        } else {
                            nZengoChk--;
                        }
                    }
                }
            }
            if (bError) {
                ActionMessage msg = null;
                msg = new ActionMessage("error.form.select.id");
                StrutsUtil.addMessage(errors, msg, "siki");
            }
        }
        //符号先頭チェック
        if (errors.isEmpty()) {
            String sMin = getSiki().replace("(", "");
            if (sMin.indexOf("+") == 0 || sMin.indexOf("*") == 0 || sMin.indexOf("/") == 0) {
                ActionMessage msg = null;
                msg = new ActionMessage("error.fugou.select.id");
                StrutsUtil.addMessage(errors, msg, "siki");
            }
        }
        //符号最終チェック
        if (errors.isEmpty()) {
            String sMin = getSiki().replace(")", "");
            if (sMin.lastIndexOf("+") == sMin.length() - 1
                    || sMin.lastIndexOf("-") == sMin.length() - 1
                    || sMin.lastIndexOf("*") == sMin.length() - 1
                    || sMin.lastIndexOf("/") == sMin.length() - 1) {
                ActionMessage msg = null;
                msg = new ActionMessage("error.fugou.select.id");
                StrutsUtil.addMessage(errors, msg, "siki");
            }
        }
        //符号連続チェック
        if (errors.isEmpty()) {
            String sChk = getSiki();
            int nIdx = 0;
            for (int idx = 0; idx < sChk.length(); idx++) {
                Format format = new Format();
                if (idx > 0) {
                    int nPlu = sChk.indexOf(Format.OPR_PLUS, nIdx);
                    int nMin = sChk.indexOf(Format.OPR_MINUS, nIdx);
                    int nMuln = sChk.indexOf(Format.OPR_MUL, nIdx);
                    int nPer = sChk.indexOf(Format.OPR_PER, nIdx);
                    if (nPlu == -1 && nMin == -1 && nMuln == -1 && nPer == -1) {
                        break;
                    }
                    if (nPlu == -1) {
                        nPlu = 10000;
                    }
                    if (nMin == -1) {
                        nMin = 10000;
                    }
                    if (nMuln == -1) {
                        nMuln = 10000;
                    }
                    if (nPer == -1) {
                        nPer = 10000;
                    }
                    if (nPlu < nMin && nPlu < nMuln && nPlu < nPer) {
                        format.setSiki(Format.OPR_PLUS);
                        nIdx = nPlu + 1;
                    } else if (nMin < nPlu && nMin < nMuln && nMin < nPer) {
                        format.setSiki(Format.OPR_MINUS);
                        nIdx = nMin + 1;
                    } else if (nMuln < nPlu && nMuln < nMin && nMuln < nPer) {
                        format.setSiki(Format.OPR_MUL);
                        nIdx = nMuln + 1;
                    } else if (nPer < nPlu && nPer < nMin && nPer < nMuln) {
                        format.setSiki(Format.OPR_PER);
                        nIdx = nPer + 1;
                    }
                }
                String sOpe =  sChk.substring(nIdx, nIdx + 1);
                if (sOpe.equals(Format.OPR_PLUS) || sOpe.equals(Format.OPR_MINUS)
                        || sOpe.equals(Format.OPR_MUL) || sOpe.equals(Format.OPR_PER)) {
                    ActionMessage msg = null;
                    msg = new ActionMessage("error.fugou.select.id");
                    StrutsUtil.addMessage(errors, msg, "siki");
                    break;
                }
            }
        }

        //数字チェック
        if (errors.isEmpty()) {
            String siki = getSiki();
            StringBuilder sb = new StringBuilder(siki);
            int nLeft = 0;
            while (nLeft != -1) {
                nLeft = sb.indexOf("[");
                if (nLeft == -1) {
                    break;
                }
                sb.delete(nLeft, sb.indexOf("]", nLeft) + 1);
            }
            siki = sb.toString();
            String[] sNum = siki.split("[-+*/()]");
            for (String sValue: sNum) {
                if (errors.isEmpty()) {
                    GSValidateCommon.validateNumberDecimal(errors,
                            sValue, gsMsg.getMessage("cmn.form.pref.calc"), 1000, false);
                } else {
                    break;
                }
                //半角チェック
                if (sValue.equals("") || sValue.length() == 0) {
                    continue;
                }
                if (!Pattern.matches("^-?[0-9]*.?[0-9]+$", sValue)) {
                    ActionMessage msg = null;
                    msg = new ActionMessage("error.input.number.hankaku",
                            gsMsg.getMessage("cmn.form.pref.calc"));
                    StrutsUtil.addMessage(errors, msg, "siki");
                }
            }
        }

    }
}