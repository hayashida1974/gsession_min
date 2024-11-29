package jp.groupsession.v2.adr.adr130;

import java.util.List;

import jp.groupsession.v2.adr.adr280.Adr280ParamModel;

/**
 * <br>[機  能] アドレス帳 ラベル一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr130ParamModel extends Adr280ParamModel {

    /** 業種情報 */
    private List<Adr130LabelModel> adr130LabelList__;

    /** 表示順リスト */
    private String[] adr130KeyList__;
    /** ラベルSID(修正対象) */
    private int adr130EditAlbSid__ = 0;
    /** 処理区分 */
    private int adr130ProcMode__;
    /** チェックされている並び順 */
    private String adr130SortRadio__;
    /** カテゴリSID */
    private int adr280AlcSid__ = 0;

    /** カテゴリ名 */
    private String adr130CatName__ = "";

    /**
     * <p>adr130LabelList を取得します。
     * @return adr130LabelList
     */
    public List<Adr130LabelModel> getAdr130LabelList() {
        return adr130LabelList__;
    }

    /**
     * <p>adr130LabelList をセットします。
     * @param adr130LabelList adr130LabelList
     */
    public void setAdr130LabelList(List<Adr130LabelModel> adr130LabelList) {
        adr130LabelList__ = adr130LabelList;
    }

    /**
     * <p>adr130EditAlbSid を取得します。
     * @return adr130EditAlbSid
     */
    public int getAdr130EditAlbSid() {
        return adr130EditAlbSid__;
    }
    /**
     * <p>adr130EditAlbSid をセットします。
     * @param adr130EditAlbSid adr130EditAlbSid
     */
    public void setAdr130EditAlbSid(int adr130EditAlbSid) {
        adr130EditAlbSid__ = adr130EditAlbSid;
    }
    /**
     * <p>adr130SortRadio を取得します。
     * @return adr130SortRadio
     */
    public String getAdr130SortRadio() {
        return adr130SortRadio__;
    }
    /**
     * <p>adr130SortRadio をセットします。
     * @param adr130SortRadio adr130SortRadio
     */
    public void setAdr130SortRadio(String adr130SortRadio) {
        adr130SortRadio__ = adr130SortRadio;
    }
    /**
     * <p>adr130KeyList を取得します。
     * @return adr130KeyList
     */
    public String[] getAdr130KeyList() {
        return adr130KeyList__;
    }
    /**
     * <p>adr130KeyList をセットします。
     * @param adr130KeyList adr130KeyList
     */
    public void setAdr130KeyList(String[] adr130KeyList) {
        adr130KeyList__ = adr130KeyList;
    }
    /**
     * <p>adr130ProcMode を取得します。
     * @return adr130ProcMode
     */
    public int getAdr130ProcMode() {
        return adr130ProcMode__;
    }
    /**
     * <p>adr130ProcMode をセットします。
     * @param adr130ProcMode adr130ProcMode
     */
    public void setAdr130ProcMode(int adr130ProcMode) {
        adr130ProcMode__ = adr130ProcMode;
    }

    /**
     * <p>adr280AlcSid を取得します。
     * @return adr280AlcSid
     */
    public int getadr280AlcSid() {
        return adr280AlcSid__;
    }

    /**
     * <p>adr280AlcSid をセットします。
     * @param adr280AlcSid adr280AlcSid
     */
    public void setadr280AlcSid(int adr280AlcSid) {
        adr280AlcSid__ = adr280AlcSid;
    }

    /**
     * @return adr130CatName
     */
    public String getAdr130CatName() {
        return adr130CatName__;
    }

    /**
     * @param adr130CatName セットする adr130CatName
     */
    public void setAdr130CatName(String adr130CatName) {
        adr130CatName__ = adr130CatName;
    }

}
