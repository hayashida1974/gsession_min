package jp.groupsession.v2.adr.adr210;

import java.util.List;

import jp.groupsession.v2.adr.adr010.Adr010Form;

/**
 * <br>[機  能] アドレス帳 役職一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr210Form extends Adr010Form {

    //入力項目
    /** チェックされている並び順 */
    private String adr210SortRadio__;
    /** 役職表示順リスト */
    private String[] adr210KeyList__;
    /** 処理区分 */
    private int adr210ProcMode__;
    /** 修正対象の役職SID */
    private int adr210EditPosSid__;

    //表示項目
    /** 役職リスト */
    private List<Adr210PositionModel> posList__;

    /**
     * <p>adr210SortRadio を取得します。
     * @return adr210SortRadio
     */
    public String getAdr210SortRadio() {
        return adr210SortRadio__;
    }
    /**
     * <p>adr210SortRadio をセットします。
     * @param adr210SortRadio adr210SortRadio
     */
    public void setAdr210SortRadio(String adr210SortRadio) {
        adr210SortRadio__ = adr210SortRadio;
    }
    /**
     * <p>adr210KeyList を取得します。
     * @return adr210KeyList
     */
    public String[] getAdr210KeyList() {
        return adr210KeyList__;
    }
    /**
     * <p>adr210KeyList をセットします。
     * @param adr210KeyList adr210KeyList
     */
    public void setAdr210KeyList(String[] adr210KeyList) {
        adr210KeyList__ = adr210KeyList;
    }
    /**
     * <p>adr210ProcMode を取得します。
     * @return adr210ProcMode
     */
    public int getAdr210ProcMode() {
        return adr210ProcMode__;
    }
    /**
     * <p>adr210ProcMode をセットします。
     * @param adr210ProcMode adr210ProcMode
     */
    public void setAdr210ProcMode(int adr210ProcMode) {
        adr210ProcMode__ = adr210ProcMode;
    }
    /**
     * <p>adr210EditPosSid を取得します。
     * @return adr210EditPosSid
     */
    public int getAdr210EditPosSid() {
        return adr210EditPosSid__;
    }
    /**
     * <p>adr210EditPosSid をセットします。
     * @param adr210EditPosSid adr210EditPosSid
     */
    public void setAdr210EditPosSid(int adr210EditPosSid) {
        adr210EditPosSid__ = adr210EditPosSid;
    }
    /**
     * <p>posList を取得します。
     * @return posList
     */
    public List<Adr210PositionModel> getPosList() {
        return posList__;
    }
    /**
     * <p>posList をセットします。
     * @param posList posList
     */
    public void setPosList(List<Adr210PositionModel> posList) {
        posList__ = posList;
    }
}