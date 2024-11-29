package jp.groupsession.v2.adr.adr080;

import java.util.List;

import jp.groupsession.v2.adr.adr010.Adr010Form;

/**
 * <br>[機  能] アドレス帳 業種一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr080Form extends Adr010Form {

    /** 業種情報 */
    private List<Adr080TypeindustryModel> adr080GyosyuList__;

    /** 表示順リスト */
    private String[] adr080KeyList__;
    /** 業種SID(修正対象) */
    private int adr080EditAtiSid__ = 0;
    /** 処理区分 */
    private int adr080ProcMode__;
    /** チェックされている並び順 */
    private String adr080SortRadio__;

    /**
     * <p>adr080GyosyuList を取得します。
     * @return adr080GyosyuList
     */
    public List<Adr080TypeindustryModel> getAdr080GyosyuList() {
        return adr080GyosyuList__;
    }
    /**
     * <p>adr080GyosyuList をセットします。
     * @param adr080GyosyuList adr080GyosyuList
     */
    public void setAdr080GyosyuList(List<Adr080TypeindustryModel> adr080GyosyuList) {
        adr080GyosyuList__ = adr080GyosyuList;
    }

    /**
     * <p>adr080EditAtiSid を取得します。
     * @return adr080EditAtiSid
     */
    public int getAdr080EditAtiSid() {
        return adr080EditAtiSid__;
    }
    /**
     * <p>adr080EditAtiSid をセットします。
     * @param adr080EditAtiSid adr080EditAtiSid
     */
    public void setAdr080EditAtiSid(int adr080EditAtiSid) {
        adr080EditAtiSid__ = adr080EditAtiSid;
    }
    /**
     * <p>adr080SortRadio を取得します。
     * @return adr080SortRadio
     */
    public String getAdr080SortRadio() {
        return adr080SortRadio__;
    }
    /**
     * <p>adr080SortRadio をセットします。
     * @param adr080SortRadio adr080SortRadio
     */
    public void setAdr080SortRadio(String adr080SortRadio) {
        adr080SortRadio__ = adr080SortRadio;
    }
    /**
     * <p>adr080KeyList を取得します。
     * @return adr080KeyList
     */
    public String[] getAdr080KeyList() {
        return adr080KeyList__;
    }
    /**
     * <p>adr080KeyList をセットします。
     * @param adr080KeyList adr080KeyList
     */
    public void setAdr080KeyList(String[] adr080KeyList) {
        adr080KeyList__ = adr080KeyList;
    }
    /**
     * <p>adr080ProcMode を取得します。
     * @return adr080ProcMode
     */
    public int getAdr080ProcMode() {
        return adr080ProcMode__;
    }
    /**
     * <p>adr080ProcMode をセットします。
     * @param adr080ProcMode adr080ProcMode
     */
    public void setAdr080ProcMode(int adr080ProcMode) {
        adr080ProcMode__ = adr080ProcMode;
    }

}
