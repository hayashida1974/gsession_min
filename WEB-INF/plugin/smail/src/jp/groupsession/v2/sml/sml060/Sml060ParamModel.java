package jp.groupsession.v2.sml.sml060;

import jp.groupsession.v2.sml.sml050.Sml050ParamModel;

/**
 * <br>[機  能] ショートメール ひな形登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml060ParamModel extends Sml050ParamModel {

    /**  ひな形一覧のアカウント名 */
    private String sml060AccountName__;

    /** 入力チェックモード */
    public static final int VALIDATE_MODE_HINA = 0;
    /** ひな形名称 */
    private String sml060HinaName__;
    /** 件名 */
    private String sml060HinaTitle__;
    /** マーク */
    private int sml060HinaMark__;
    /** 本文 */
    private String sml060HinaBody__;
    /**
     * <p>sml060HinaName を取得します。
     * @return sml060HinaName
     */
    public String getSml060HinaName() {
        return sml060HinaName__;
    }
    /**
     * <p>sml060HinaName をセットします。
     * @param sml060HinaName sml060HinaName
     */
    public void setSml060HinaName(String sml060HinaName) {
        sml060HinaName__ = sml060HinaName;
    }
    /**
     * <p>sml060HinaTitle を取得します。
     * @return sml060HinaTitle
     */
    public String getSml060HinaTitle() {
        return sml060HinaTitle__;
    }
    /**
     * <p>sml060HinaTitle をセットします。
     * @param sml060HinaTitle sml060HinaTitle
     */
    public void setSml060HinaTitle(String sml060HinaTitle) {
        sml060HinaTitle__ = sml060HinaTitle;
    }
    /**
     * <p>sml060HinaMark を取得します。
     * @return sml060HinaMark
     */
    public int getSml060HinaMark() {
        return sml060HinaMark__;
    }
    /**
     * <p>sml060HinaMark をセットします。
     * @param sml060HinaMark sml060HinaMark
     */
    public void setSml060HinaMark(int sml060HinaMark) {
        sml060HinaMark__ = sml060HinaMark;
    }
    /**
     * <p>sml060HinaBody を取得します。
     * @return sml060HinaBody
     */
    public String getSml060HinaBody() {
        return sml060HinaBody__;
    }
    /**
     * <p>sml060HinaBody をセットします。
     * @param sml060HinaBody sml060HinaBody
     */
    public void setSml060HinaBody(String sml060HinaBody) {
        sml060HinaBody__ = sml060HinaBody;
    }
    /**
     * <p>sml060AccountName を取得します。
     * @return sml060AccountName
     */
    public String getSml060AccountName() {
        return sml060AccountName__;
    }
    /**
     * <p>sml060AccountName をセットします。
     * @param sml060AccountName sml060AccountName
     */
    public void setSml060AccountName(String sml060AccountName) {
        sml060AccountName__ = sml060AccountName;
    }
}