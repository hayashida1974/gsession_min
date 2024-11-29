package jp.groupsession.v2.rng.rng010;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.rng.RingiParamModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.AccountDataModel;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;

/**
 * <br>[機  能] 稟議一覧画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng010ParamModel extends RingiParamModel {
    /** 管理者フラグ */
    private int rng010adminFlg__ = -1;
    /** オーダーキー */
    private int rng010orderKey__ = RngConst.RNG_ORDER_ASC;
    /** ソートキー */
    private int rng010sortKey__ = RngConst.RNG_SORT_JYUSIN;
    /** ページコンボ上段 */
    private int rng010pageTop__ = 1;
    /** ページコンボ下段 */
    private int rng010pageBottom__ = 1;
    /** ページコンボリスト */
    private List < LabelValueBean > pageList__ = null;
    /** 稟議情報一覧 */
    private List <RingiDataModel> rngDataList__ = null;
    /** 削除対象一覧 */
    private String[] rng010DelSidList__ = null;
    /** キーワード */
    private String rngKeyword__ = null;
    /** 削除権限 */
    private int rng010delAuth__ = 0;
    /** 代理人選択フラグ*/
    private int rng010DairiFlg__ = 0;
    /** 遷移元 メイン個人設定:1 メイン管理者設定:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;
    /** アカウント一覧 */
    private List<AccountDataModel> rng010AccountList__;
    /** 表示アカウント */
    private int rng010ViewAccount__ = 0;
    /** カテゴリリスト*/
    private List <LabelValueBean> rng010CategoryList__ = null;
    /** 選択カテゴリ*/
    private int rng010SelectCategory__ = -1;
    /** テンプレート一覧*/
    private List <RngTemplateModel> rng010TemplateList__ = null;
    /** カテゴリリスト*/
    private List <LabelValueBean> rng010CategoryListUser__ = null;
    /** 選択カテゴリ*/
    private int rng010SelectCategoryUser__ = -1;
    /** テンプレート一覧*/
    private List <RngTemplateModel> rng010TemplateListUser__ = null;

    /** 検索選択カテゴリ*/
    private int rng010SearchCategory__ = -1;

    /** 受信カウント数*/
    private int rng010JusinCnt__ = 0;
    /** 草稿カウント数*/
    private int rng010SoukouCnt__ = 0;

    /** 一覧遷移フラグ*/
    private int rng010TransitionFlg__ = 0;

    /** 個人テンプレート使用有無 */
    private int rng010TemplatePersonalFlg__ = RngConst.RAR_TEMPLATE_PERSONAL_FLG_YES;

    /**
     * <p>rng010adminFlg を取得します。
     * @return rng010adminFlg
     */
    public int getRng010adminFlg() {
        return rng010adminFlg__;
    }

    /**
     * <p>rng010adminFlg をセットします。
     * @param rng010adminFlg rng010adminFlg
     */
    public void setRng010adminFlg(int rng010adminFlg) {
        rng010adminFlg__ = rng010adminFlg;
    }

    /**
     * <p>rng010orderKey を取得します。
     * @return rng010orderKey
     */
    public int getRng010orderKey() {
        return rng010orderKey__;
    }

    /**
     * <p>rng010orderKey をセットします。
     * @param rng010orderKey rng010orderKey
     */
    public void setRng010orderKey(int rng010orderKey) {
        rng010orderKey__ = rng010orderKey;
    }

    /**
     * <p>rng010sortKey を取得します。
     * @return rng010sortKey
     */
    public int getRng010sortKey() {
        return rng010sortKey__;
    }

    /**
     * <p>rng010sortKey をセットします。
     * @param rng010sortKey rng010sortKey
     */
    public void setRng010sortKey(int rng010sortKey) {
        rng010sortKey__ = rng010sortKey;
    }

    /**
     * <p>rng010pageTop を取得します。
     * @return rng010pageTop
     */
    public int getRng010pageTop() {
        return rng010pageTop__;
    }

    /**
     * <p>rng010pageTop をセットします。
     * @param rng010pageTop rng010pageTop
     */
    public void setRng010pageTop(int rng010pageTop) {
        rng010pageTop__ = rng010pageTop;
    }

    /**
     * <p>rng010pageBottom を取得します。
     * @return rng010pageBottom
     */
    public int getRng010pageBottom() {
        return rng010pageBottom__;
    }

    /**
     * <p>rng010pageBottom をセットします。
     * @param rng010pageBottom rng010pageBottom
     */
    public void setRng010pageBottom(int rng010pageBottom) {
        rng010pageBottom__ = rng010pageBottom;
    }

    /**
     * <p>pageList を取得します。
     * @return pageList
     */
    public List<LabelValueBean> getPageList() {
        return pageList__;
    }

    /**
     * <p>pageList をセットします。
     * @param pageList pageList
     */
    public void setPageList(List<LabelValueBean> pageList) {
        pageList__ = pageList;
    }

    /**
     * <p>rngDataList を取得します。
     * @return rngDataList
     */
    public List<RingiDataModel> getRngDataList() {
        return rngDataList__;
    }

    /**
     * <p>rngDataList をセットします。
     * @param rngDataList rngDataList
     */
    public void setRngDataList(List<RingiDataModel> rngDataList) {
        rngDataList__ = rngDataList;
    }

    /**
     * <p>rng010DelSidList を取得します。
     * @return rng010DelSidList
     */
    public String[] getRng010DelSidList() {
        return rng010DelSidList__;
    }

    /**
     * <p>rng010DelSidList をセットします。
     * @param rng010DelSidList rng010DelSidList
     */
    public void setRng010DelSidList(String[] rng010DelSidList) {
        rng010DelSidList__ = rng010DelSidList;
    }

    /**
     * <p>rngKeyword を取得します。
     * @return rngKeyword
     */
    public String getRngKeyword() {
        return rngKeyword__;
    }

    /**
     * <p>rngKeyword をセットします。
     * @param rngKeyword rngKeyword
     */
    public void setRngKeyword(String rngKeyword) {
        rngKeyword__ = rngKeyword;
    }

    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }

    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }

    /**
     * <p>rng010delAuth を取得します。
     * @return rng010delAuth
     */
    public int getRng010delAuth() {
        return rng010delAuth__;
    }

    /**
     * <p>rng010delAuth をセットします。
     * @param rng010delAuth rng010delAuth
     */
    public void setRng010delAuth(int rng010delAuth) {
        rng010delAuth__ = rng010delAuth;
    }

    /**
     * <p>rng010AccountList を取得します。
     * @return rng010AccountList
     */
    public List<AccountDataModel> getRng010AccountList() {
        return rng010AccountList__;
    }

    /**
     * <p>rng010AccountList をセットします。
     * @param rng010AccountList rng010AccountList
     */
    public void setRng010AccountList(List<AccountDataModel> rng010AccountList) {
        rng010AccountList__ = rng010AccountList;
    }

    /**
     * <p>rng010ViewAccount を取得します。
     * @return rng010ViewAccount
     */
    public int getRng010ViewAccount() {
        return rng010ViewAccount__;
    }

    /**
     * <p>rng010ViewAccount をセットします。
     * @param rng010ViewAccount rng010ViewAccount
     */
    public void setRng010ViewAccount(int rng010ViewAccount) {
        rng010ViewAccount__ = rng010ViewAccount;
    }

    /**
     * <p>rng010DairiFlg を取得します。
     * @return rng010DairiFlg
     */
    public int getRng010DairiFlg() {
        return rng010DairiFlg__;
    }

    /**
     * <p>rng010DairiFlg をセットします。
     * @param rng010DairiFlg rng010DairiFlg
     */
    public void setRng010DairiFlg(int rng010DairiFlg) {
        rng010DairiFlg__ = rng010DairiFlg;
    }

    /**
     * <p>rng010CategoryList を取得します。
     * @return rng010CategoryList
     */
    public List<LabelValueBean> getRng010CategoryList() {
        return rng010CategoryList__;
    }

    /**
     * <p>rng010CategoryList をセットします。
     * @param rng010CategoryList rng010CategoryList
     */
    public void setRng010CategoryList(List<LabelValueBean> rng010CategoryList) {
        rng010CategoryList__ = rng010CategoryList;
    }

    /**
     * <p>rng010SelectCategory を取得します。
     * @return rng010SelectCategory
     */
    public int getRng010SelectCategory() {
        return rng010SelectCategory__;
    }

    /**
     * <p>rng010SelectCategory をセットします。
     * @param rng010SelectCategory rng010SelectCategory
     */
    public void setRng010SelectCategory(int rng010SelectCategory) {
        rng010SelectCategory__ = rng010SelectCategory;
    }

    /**
     * <p>rng010TemplateList を取得します。
     * @return rng010TemplateList
     */
    public List<RngTemplateModel> getRng010TemplateList() {
        return rng010TemplateList__;
    }

    /**
     * <p>rng010TemplateList をセットします。
     * @param rng010TemplateList rng010TemplateList
     */
    public void setRng010TemplateList(List<RngTemplateModel> rng010TemplateList) {
        rng010TemplateList__ = rng010TemplateList;
    }

    /**
     * <p>rng010SoukouCnt を取得します。
     * @return rng010SoukouCnt
     */
    public int getRng010SoukouCnt() {
        return rng010SoukouCnt__;
    }

    /**
     * <p>rng010SoukouCnt をセットします。
     * @param rng010SoukouCnt rng010SoukouCnt
     */
    public void setRng010SoukouCnt(int rng010SoukouCnt) {
        rng010SoukouCnt__ = rng010SoukouCnt;
    }

    /**
     * <p>rng010CategoryListUser を取得します。
     * @return rng010CategoryListUser
     */
    public List<LabelValueBean> getRng010CategoryListUser() {
        return rng010CategoryListUser__;
    }

    /**
     * <p>rng010CategoryListUser をセットします。
     * @param rng010CategoryListUser rng010CategoryListUser
     */
    public void setRng010CategoryListUser(
            List<LabelValueBean> rng010CategoryListUser) {
        rng010CategoryListUser__ = rng010CategoryListUser;
    }

    /**
     * <p>rng010SelectCategoryUser を取得します。
     * @return rng010SelectCategoryUser
     */
    public int getRng010SelectCategoryUser() {
        return rng010SelectCategoryUser__;
    }

    /**
     * <p>rng010SelectCategoryUser をセットします。
     * @param rng010SelectCategoryUser rng010SelectCategoryUser
     */
    public void setRng010SelectCategoryUser(int rng010SelectCategoryUser) {
        rng010SelectCategoryUser__ = rng010SelectCategoryUser;
    }

    /**
     * <p>rng010TemplateListUser を取得します。
     * @return rng010TemplateListUser
     */
    public List<RngTemplateModel> getRng010TemplateListUser() {
        return rng010TemplateListUser__;
    }

    /**
     * <p>rng010TemplateListUser をセットします。
     * @param rng010TemplateListUser rng010TemplateListUser
     */
    public void setRng010TemplateListUser(
            List<RngTemplateModel> rng010TemplateListUser) {
        rng010TemplateListUser__ = rng010TemplateListUser;
    }

    /**
     * <p>rng010SearchCategory を取得します。
     * @return rng010SearchCategory
     */
    public int getRng010SearchCategory() {
        return rng010SearchCategory__;
    }

    /**
     * <p>rng010SearchCategory をセットします。
     * @param rng010SearchCategory rng010SearchCategory
     */
    public void setRng010SearchCategory(int rng010SearchCategory) {
        rng010SearchCategory__ = rng010SearchCategory;
    }

    /**
     * <p>rng010JusinCnt を取得します。
     * @return rng010JusinCnt
     */
    public int getRng010JusinCnt() {
        return rng010JusinCnt__;
    }

    /**
     * <p>rng010JusinCnt をセットします。
     * @param rng010JusinCnt rng010JusinCnt
     */
    public void setRng010JusinCnt(int rng010JusinCnt) {
        rng010JusinCnt__ = rng010JusinCnt;
    }

    /**
     * <p>rng010TransitionFlg を取得します。
     * @return rng010TransitionFlg
     */
    public int getRng010TransitionFlg() {
        return rng010TransitionFlg__;
    }

    /**
     * <p>rng010TransitionFlg をセットします。
     * @param rng010TransitionFlg rng010TransitionFlg
     */
    public void setRng010TransitionFlg(int rng010TransitionFlg) {
        rng010TransitionFlg__ = rng010TransitionFlg;
    }

    /**
     * <p>rng010TemplatePersonalFlg を取得します。
     * @return rng010TemplatePersonalFlg
     * @see jp.groupsession.v2.rng.rng010.Rng010ParamModel#rng010TemplatePersonalFlg__
     */
    public int getRng010TemplatePersonalFlg() {
        return rng010TemplatePersonalFlg__;
    }

    /**
     * <p>rng010TemplatePersonalFlg をセットします。
     * @param rng010TemplatePersonalFlg rng010TemplatePersonalFlg
     * @see jp.groupsession.v2.rng.rng010.Rng010ParamModel#rng010TemplatePersonalFlg__
     */
    public void setRng010TemplatePersonalFlg(int rng010TemplatePersonalFlg) {
        rng010TemplatePersonalFlg__ = rng010TemplatePersonalFlg;
    }
}