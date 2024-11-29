package jp.groupsession.v2.rng.rng210;

import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.rng.rng200.Rng200ParamModel;

/**
 * <br>[機  能] 稟議 管理者設定 申請ID一覧のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng210ParamModel extends Rng200ParamModel {

    /** タイトル*/
    private String rng210Title__ = null;
    /** フォーマット*/
    private String rng210Format__ = null;
    /** 採番初期値*/
    private String rng210Init__ = "1";
    /** 0埋め値*/
    private  String rng210Zeroume__ = "1";
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
        rng210Cmd__ = rng210Cmd;
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
