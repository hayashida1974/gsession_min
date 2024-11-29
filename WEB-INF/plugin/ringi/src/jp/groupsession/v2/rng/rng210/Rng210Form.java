package jp.groupsession.v2.rng.rng210;

import java.util.ArrayList;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngValidate;
import jp.groupsession.v2.rng.rng200.Rng200Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議 管理者設定 申請ID一覧のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng210Form extends Rng200Form {

    /** タイトル*/
    private String rng210Title__ = null;
    /** フォーマット*/
    private String rng210Format__ = null;
    /** 採番初期値*/
    private String rng210Init__ = "1";
    /** 0埋め値*/
    private String rng210Zeroume__ = "1";
    /** 申請ID手動設定*/
    private int rng210Manual__ = 0;
    /** 連番値のリセット設定*/
    private int rng210Reset__ = 0;
    /** フォーマットの日付使用*/
    private int rng210Date__ = 0;
    /** 初期表示フォーマット一覧 */
    private ArrayList<LabelValueBean> rng210FormatListLabel__ = null;
    /** フォーマットリスト*/
    private ArrayList<Rng210ListModel> rng210FormatList__ = new ArrayList<Rng210ListModel>();
    /** 表示用フォーマット値*/
    private String rng210DispFormat__ = null;
    /** 表示用パターン値*/
    private String rng210Pattern__ = null;

    /** 追加0 編集1*/
    private int rng210Cmd__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return errors エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage(reqMdl);
        String title   = gsMsg.getMessage("cmn.title");
        String format  = gsMsg.getMessage("rng.12");
        String renban  = gsMsg.getMessage("rng.rng210.01"); // 現在の連番値
        String zeroume = gsMsg.getMessage("rng.rng210.10"); // 0埋め桁数

        //タイトル
        errors = RngValidate.validateCmnFieldText(
                errors,                //errors
                title,                 //エラーメッセージ表示テキスト
                rng210Title__,         //チェックするフィールド
                "rng210Title__",       //チェックするフィールドの文字列
                50,                    //最大桁数
                true);                 //入力必須か

        //フォーマット
        errors = RngValidate.validateCmnFieldText(
                errors,                //errors
                format,                //エラーメッセージ表示テキスト
                rng210DispFormat__,    //チェックするフィールド
                "rng210Format__",      //チェックするフィールドの文字列
                120,                   //最大桁数
                true);                 //入力必須か

        String nstr = "";
        int inputFormatDoll = rng210DispFormat__.indexOf("$");
        if (inputFormatDoll > -1) {
            nstr = "$";
        }

        int inputFormatBraStart = rng210DispFormat__.indexOf("{");
        if (inputFormatBraStart > -1) {
            nstr = nstr + "{";
        }

        int inputFormatBraEnd = rng210DispFormat__.indexOf("}");
        if (inputFormatBraEnd > -1) {
            nstr = nstr + "}";
        }
        int doNotUseSum = inputFormatDoll + inputFormatBraStart + inputFormatBraEnd;
        if (doNotUseSum > -3) {
            ActionMessage msg = null;
            String eprefix = "ringi";
            String fieldfix = format + ".";
            msg = new ActionMessage("error.input.njapan.text", format, nstr);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + "rng210Format__");
        }

        if (rng210Format__.indexOf("{NO}") == -1) {
            ActionMessage msg = null;
            String eprefix = "ringi";
            String fieldfix = format + ".";
            String renbanti = gsMsg.getMessage("rng.rng210.15");
            msg = new ActionMessage("error.input.use.need", format, renbanti);
            StrutsUtil.addMessage(
                    errors, msg, eprefix + fieldfix + "rng210Format__");
        }

        if (!StringUtil.isNullZeroString(format)) {
            String[] formatList = rng210Format__.split("\\$", 0);
            boolean bRoop = true;
            for (int idx = 1; idx < formatList.length; idx++) {
                if (!bRoop) {
                    break;
                }
                switch (formatList[idx]) {
                case "{NO}":
                    break;
                case "{YEAR4}":
                    break;
                case "{YEAR2}":
                    break;
                case "{MON}":
                    break;
                case "{DAY}":
                    break;
                default:
                    // 文字入力の{}区切り文字なのでカウントから除外する
                    String validateString = formatList[idx].substring(
                            formatList[idx].indexOf("}") + 1, formatList[idx].length());
                    if (validateString.length() > 10) {
                        ActionMessage msg = null;
                        String eprefix = "ringi";
                        String fieldfix = format + ".";
                        String mes = gsMsg.getMessage("rng.rng210.16");
                        msg = new ActionMessage("error.input.length.text", mes, "10");
                        StrutsUtil.addMessage(
                                errors, msg, eprefix + fieldfix + "rng210Format__");
                        bRoop = false;
                    }
                    break;
                }
            }
        }
        int eSize = errors.size();
        //採番初期値
        errors = RngValidate.validateNumberInt(
                errors,
                rng210Init__,
                renban,
                10
                );

        ActionMessage msg = null;

        // 採番初期値 0以下チェック
        if (NullDefault.getInt(rng210Init__, 0) < 1 && errors.size() == eSize) {
            msg = new ActionMessage("error.input.number.over",
                                    "[" + renban + "]", "[" + String.valueOf(1) + "]");
            StrutsUtil.addMessage(errors, msg, "rng210Init.minus");
        }
        //0埋め桁数
        eSize = errors.size();
        errors = RngValidate.validateNumberInt(
                errors,
                rng210Zeroume__,
                zeroume,
                10
                );
        int rng210zeroume = NullDefault.getInt(rng210Zeroume__, 0);
        // 数値範囲チェック(0～10)
        if (eSize == errors.size() && (rng210zeroume > 10 || rng210zeroume < 1)) {
            msg = new ActionMessage(
                    "error.input.addhani.text", zeroume, String.valueOf(1), String.valueOf(10));
            StrutsUtil.addMessage(errors, msg, "rng210Zeroume");
        }

        return errors;
    }

    /**
     * <p>rng210Format を取得します。
     * @return rng210Format
     */
    public String getRng210Format() {
        return rng210Format__;
    }
    /**
     * <p>rng210Format をセットします。
     * @param rng210Format rng210Format
     */
    public void setRng210Format(String rng210Format) {
        this.rng210Format__ = rng210Format;
    }
    /**
     * <p>rng210Init を取得します。
     * @return rng210Init
     */
    public String getRng210Init() {
        return rng210Init__;
    }
    /**
     * <p>rng210Init をセットします。
     * @param rng210Init rng210Init
     */
    public void setRng210Init(String rng210Init) {
        this.rng210Init__ = rng210Init;
    }
    /**
     * <p>rng210Manual を取得します。
     * @return rng210Manual
     */
    public int getRng210Manual() {
        return rng210Manual__;
    }
    /**
     * <p>rng210Manual をセットします。
     * @param rng210Manual rng210Manual
     */
    public void setRng210Manual(int rng210Manual) {
        this.rng210Manual__ = rng210Manual;
    }
    /**
     * <p>rng210Cmd を取得します。
     * @return rng210Cmd
     */
    public int getRng210Cmd() {
        return rng210Cmd__;
    }
    /**
     * <p>rng210Cmd をセットします。
     * @param rng210Cmd rng210Cmd
     */
    public void setRng210Cmd(int rng210Cmd) {
        this.rng210Cmd__ = rng210Cmd;
    }
    /**
     * <p>rng210Date を取得します。
     * @return rng210Date
     */
    public int getRng210Date() {
        return rng210Date__;
    }
    /**
     * <p>rng210Date をセットします。
     * @param rng210Date rng210Date
     */
    public void setRng210Date(int rng210Date) {
        rng210Date__ = rng210Date;
    }

    /**
     * <p>rng210Reset を取得します。
     * @return rng210Reset
     */
    public int getRng210Reset() {
        return rng210Reset__;
    }

    /**
     * <p>rng210Reset をセットします。
     * @param rng210Reset rng210Reset
     */
    public void setRng210Reset(int rng210Reset) {
        rng210Reset__ = rng210Reset;
    }

    /**
     * <p>rng210FormatListLabel を取得します。
     * @return rng210FormatListLabel
     */
    public ArrayList<LabelValueBean> getRng210FormatListLabel() {
        return rng210FormatListLabel__;
    }

    /**
     * <p>rng210FormatListLabel をセットします。
     * @param rng210FormatListLabel rng210FormatListLabel
     */
    public void setRng210FormatListLabel(
            ArrayList<LabelValueBean> rng210FormatListLabel) {
        rng210FormatListLabel__ = rng210FormatListLabel;
    }

    /**
     * <p>rng210FormatList を取得します。
     * @return rng210FormatList
     */
    public ArrayList<Rng210ListModel> getRng210FormatList() {
        return rng210FormatList__;
    }

    /**
     * <p>rng210FormatList をセットします。
     * @param rng210FormatList rng210FormatList
     */
    public void setRng210FormatList(ArrayList<Rng210ListModel> rng210FormatList) {
        rng210FormatList__ = rng210FormatList;
    }

    /**
     * <p>rng210DispFormat を取得します。
     * @return rng210DispFormat
     */
    public String getRng210DispFormat() {
        return rng210DispFormat__;
    }

    /**
     * <p>rng210DispFormat をセットします。
     * @param rng210DispFormat rng210DispFormat
     */
    public void setRng210DispFormat(String rng210DispFormat) {
        rng210DispFormat__ = rng210DispFormat;
    }

    /**
     * <p>rng210Zeroume を取得します。
     * @return rng210Zeroume
     */
    public String getRng210Zeroume() {
        return rng210Zeroume__;
    }

    /**
     * <p>rng210Zeroume をセットします。
     * @param rng210Zeroume rng210Zeroume
     */
    public void setRng210Zeroume(String rng210Zeroume) {
        rng210Zeroume__ = rng210Zeroume;
    }

    /**
     * <p>rng210Title を取得します。
     * @return rng210Title
     */
    public String getRng210Title() {
        return rng210Title__;
    }

    /**
     * <p>rng210Title をセットします。
     * @param rng210Title rng210Title
     */
    public void setRng210Title(String rng210Title) {
        rng210Title__ = rng210Title;
    }

    /**
     * <p>rng210Pattern を取得します。
     * @return rng210Pattern
     */
    public String getRng210Pattern() {
        return rng210Pattern__;
    }

    /**
     * <p>rng210Pattern をセットします。
     * @param rng210Pattern rng210Pattern
     */
    public void setRng210Pattern(String rng210Pattern) {
        rng210Pattern__ = rng210Pattern;
    }

}
