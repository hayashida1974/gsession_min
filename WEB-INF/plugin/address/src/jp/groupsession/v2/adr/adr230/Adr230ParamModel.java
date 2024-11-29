package jp.groupsession.v2.adr.adr230;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.model.AdrCompanyModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 会社選択ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr230ParamModel extends AbstractParamModel {

    /** 1ページ表示件数 */
    public static final int PAGE_MAX_DATA_CMT = 20;

    /** ソートキー */
    private int adr230SortKey__ = GSConstAddress.COMPANY_SORT_CODE;
    /** オーダーキー */
    private int adr230OrderKey__ = GSConst.ORDER_KEY_DESC;
    /** ページ(上) */
    private int adr230PageTop__ = 1;
    /** ページ(下) */
    private int adr230PageBottom__ = 1;
    /** ページリスト */
    private List<LabelValueBean> adr230PageList__;
    /** 企業一覧 */
    private ArrayList<AdrCompanyModel> adr230CompanyList__;

    /**
     * <p>adr230SortKey を取得します。
     * @return adr230SortKey
     */
    public int getAdr230SortKey() {
        return adr230SortKey__;
    }
    /**
     * <p>adr230SortKey をセットします。
     * @param adr230SortKey adr230SortKey
     */
    public void setAdr230SortKey(int adr230SortKey) {
        adr230SortKey__ = adr230SortKey;
    }
    /**
     * <p>adr230OrderKey を取得します。
     * @return adr230OrderKey
     */
    public int getAdr230OrderKey() {
        return adr230OrderKey__;
    }
    /**
     * <p>adr230OrderKey をセットします。
     * @param adr230OrderKey adr230OrderKey
     */
    public void setAdr230OrderKey(int adr230OrderKey) {
        adr230OrderKey__ = adr230OrderKey;
    }
    /**
     * <p>adr230PageTop を取得します。
     * @return adr230PageTop
     */
    public int getAdr230PageTop() {
        return adr230PageTop__;
    }
    /**
     * <p>adr230PageTop をセットします。
     * @param adr230PageTop adr230PageTop
     */
    public void setAdr230PageTop(int adr230PageTop) {
        adr230PageTop__ = adr230PageTop;
    }
    /**
     * <p>adr230PageBottom を取得します。
     * @return adr230PageBottom
     */
    public int getAdr230PageBottom() {
        return adr230PageBottom__;
    }
    /**
     * <p>adr230PageBottom をセットします。
     * @param adr230PageBottom adr230PageBottom
     */
    public void setAdr230PageBottom(int adr230PageBottom) {
        adr230PageBottom__ = adr230PageBottom;
    }
    /**
     * <p>adr230PageList を取得します。
     * @return adr230PageList
     */
    public List<LabelValueBean> getAdr230PageList() {
        return adr230PageList__;
    }
    /**
     * <p>adr230PageList をセットします。
     * @param adr230PageList adr230PageList
     */
    public void setAdr230PageList(List<LabelValueBean> adr230PageList) {
        adr230PageList__ = adr230PageList;
    }
    /**
     * <p>adr230CompanyList を取得します。
     * @return adr230CompanyList
     */
    public ArrayList<AdrCompanyModel> getAdr230CompanyList() {
        return adr230CompanyList__;
    }
    /**
     * <p>adr230CompanyList をセットします。
     * @param adr230CompanyList adr230CompanyList
     */
    public void setAdr230CompanyList(ArrayList<AdrCompanyModel> adr230CompanyList) {
        adr230CompanyList__ = adr230CompanyList;
    }
}