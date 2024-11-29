package jp.groupsession.v2.sml.sml090;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;

/**
 * <br>[機  能] ショートメール詳細検索画面の検索条件パラメータを格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml090SearchParameterModel extends AbstractModel {

    /** オフセット  */
    private int offset__;
    /** 最大表示件数 */
    private int limit__;

    /** メールモード */
    private String mailMode__ = null;

    /** opkbn 開封区分 0:未読のみ, 1: 既読, その他: 全て */
    private int openKbn__ = GSConstCommon.NUM_INIT;

    /** セッションユーザSID */
    private int mySid__ = GSConstCommon.NUM_INIT;

    /** グループ */
    private String sltGroup__ = String.valueOf(GSConstCommon.NUM_INIT);
    /** ユーザ */
    private String sltUser__ = String.valueOf(GSConstCommon.NUM_INIT);

    /** 宛先SIDリスト */
    private String[] userSid__;
    /** 宛先 */
    private String[] atesaki__ = null;

    /** メール種別 */
    private String mailSyubetsu__ = null;
    /** マーク */
    private int mailMark__ = GSConstSmail.MARK_KBN_ALL;

    /** 検索キーワード */
    private List < String > keywordList__ = null;

    /** キーワード検索区分 */
    private int keyWordkbn__ = GSConstSmail.KEY_WORD_KBN_AND;
    /** 検索対象 */
    private String[] searchTarget__ = SmlCommonBiz.getDefultSearchTarget();

    /** 検索日時 開始 */
    private UDate fromDate__ = null;
    /** 検索日時 終了 */
    private UDate toDate__   = null;
    
    /** メールSID*/
    private int smlSid__ = GSConstCommon.NUM_INIT;;

    //ソート順
    /** オーダーキー1 */
    private int searchOrderKey1__ = GSConstSmail.ORDER_KEY_ASC;
    /** ソートキー1 */
    private int searchSortKey1__ = GSConstSmail.MSG_SORT_KEY_DATE;
    /** オーダーキー2 */
    private int searchOrderKey2__ = GSConstSmail.ORDER_KEY_ASC;
    /** ソートキー2 */
    private int searchSortKey2__ = GSConstSmail.MSG_SORT_KEY_MARK;


    /**
     * <br>[機  能] キーワードを設定する
     * <br>[解  説] スペース区切りで複数のキーワードを設定する
     * <br>[備  考]
     * @param keyword キーワード
     */
    public void setKeyword(String keyword) {

        CommonBiz cBiz = new CommonBiz();
        setKeywordList(cBiz.setKeyword(keyword));
    }

    /**
     * <p>atesaki を取得します。
     * @return atesaki
     */
    public String[] getAtesaki() {
        return atesaki__;
    }
    /**
     * <p>atesaki をセットします。
     * @param atesaki atesaki
     */
    public void setAtesaki(String[] atesaki) {
        atesaki__ = atesaki;
    }
    /**
     * <p>keyWordkbn を取得します。
     * @return keyWordkbn
     */
    public int getKeyWordkbn() {
        return keyWordkbn__;
    }
    /**
     * <p>keyWordkbn をセットします。
     * @param keyWordkbn keyWordkbn
     */
    public void setKeyWordkbn(int keyWordkbn) {
        keyWordkbn__ = keyWordkbn;
    }
    /**
     * <p>mailMark を取得します。
     * @return mailMark
     */
    public int getMailMark() {
        return mailMark__;
    }
    /**
     * <p>mailMark をセットします。
     * @param mailMark mailMark
     */
    public void setMailMark(int mailMark) {
        mailMark__ = mailMark;
    }
    /**
     * <p>mailSyubetsu を取得します。
     * @return mailSyubetsu
     */
    public String getMailSyubetsu() {
        return mailSyubetsu__;
    }
    /**
     * <p>mailSyubetsu をセットします。
     * @param mailSyubetsu mailSyubetsu
     */
    public void setMailSyubetsu(String mailSyubetsu) {
        mailSyubetsu__ = mailSyubetsu;
    }
    /**
     * <p>searchOrderKey1 を取得します。
     * @return searchOrderKey1
     */
    public int getSearchOrderKey1() {
        return searchOrderKey1__;
    }
    /**
     * <p>searchOrderKey1 をセットします。
     * @param searchOrderKey1 searchOrderKey1
     */
    public void setSearchOrderKey1(int searchOrderKey1) {
        searchOrderKey1__ = searchOrderKey1;
    }
    /**
     * <p>searchOrderKey2 を取得します。
     * @return searchOrderKey2
     */
    public int getSearchOrderKey2() {
        return searchOrderKey2__;
    }
    /**
     * <p>searchOrderKey2 をセットします。
     * @param searchOrderKey2 searchOrderKey2
     */
    public void setSearchOrderKey2(int searchOrderKey2) {
        searchOrderKey2__ = searchOrderKey2;
    }
    /**
     * <p>searchSortKey1 を取得します。
     * @return searchSortKey1
     */
    public int getSearchSortKey1() {
        return searchSortKey1__;
    }
    /**
     * <p>searchSortKey1 をセットします。
     * @param searchSortKey1 searchSortKey1
     */
    public void setSearchSortKey1(int searchSortKey1) {
        searchSortKey1__ = searchSortKey1;
    }
    /**
     * <p>searchSortKey2 を取得します。
     * @return searchSortKey2
     */
    public int getSearchSortKey2() {
        return searchSortKey2__;
    }
    /**
     * <p>searchSortKey2 をセットします。
     * @param searchSortKey2 searchSortKey2
     */
    public void setSearchSortKey2(int searchSortKey2) {
        searchSortKey2__ = searchSortKey2;
    }
    /**
     * <p>searchTarget を取得します。
     * @return searchTarget
     */
    public String[] getSearchTarget() {
        return searchTarget__;
    }
    /**
     * <p>searchTarget をセットします。
     * @param searchTarget searchTarget
     */
    public void setSearchTarget(String[] searchTarget) {
        searchTarget__ = searchTarget;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public String[] getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(String[] userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>limit を取得します。
     * @return limit
     */
    public int getLimit() {
        return limit__;
    }
    /**
     * <p>limit をセットします。
     * @param limit limit
     */
    public void setLimit(int limit) {
        limit__ = limit;
    }
    /**
     * <p>offset を取得します。
     * @return offset
     */
    public int getOffset() {
        return offset__;
    }
    /**
     * <p>offset をセットします。
     * @param offset offset
     */
    public void setOffset(int offset) {
        offset__ = offset;
    }
    /**
     * <p>mySid を取得します。
     * @return mySid
     */
    public int getMySid() {
        return mySid__;
    }
    /**
     * <p>mySid をセットします。
     * @param mySid mySid
     */
    public void setMySid(int mySid) {
        mySid__ = mySid;
    }
    /**
     * <p>openKbn を取得します。
     * @return openKbn
     */
    public int getOpenKbn() {
        return openKbn__;
    }
    /**
     * <p>openKbn をセットします。
     * @param openKbn openKbn
     */
    public void setOpenKbn(int openKbn) {
        openKbn__ = openKbn;
    }
    /**
     * <p>keywordList を取得します。
     * @return keywordList
     */
    public List<String> getKeywordList() {
        return keywordList__;
    }
    /**
     * <p>keywordList をセットします。
     * @param keywordList keywordList
     */
    public void setKeywordList(List<String> keywordList) {
        keywordList__ = keywordList;
    }

    /**
     * <p>mailMode を取得します。
     * @return mailMode
     */
    public String getMailMode() {
        return mailMode__;
    }

    /**
     * <p>mailMode をセットします。
     * @param mailMode mailMode
     */
    public void setMailMode(String mailMode) {
        mailMode__ = mailMode;
    }

    /**
     * <p>sltGroup を取得します。
     * @return sltGroup
     */
    public String getSltGroup() {
        return sltGroup__;
    }

    /**
     * <p>sltGroup をセットします。
     * @param sltGroup sltGroup
     */
    public void setSltGroup(String sltGroup) {
        sltGroup__ = sltGroup;
    }

    /**
     * <p>sltUser を取得します。
     * @return sltUser
     */
    public String getSltUser() {
        return sltUser__;
    }

    /**
     * <p>sltUser をセットします。
     * @param sltUser sltUser
     */
    public void setSltUser(String sltUser) {
        sltUser__ = sltUser;
    }

    /**
     * <p>fromDate を取得します。
     * @return fromDate
     */
    public UDate getFromDate() {
        return fromDate__;
    }

    /**
     * <p>fromDate をセットします。
     * @param fromDate fromDate
     */
    public void setFromDate(UDate fromDate) {
        fromDate__ = fromDate;
    }

    /**
     * <p>toDate を取得します。
     * @return toDate
     */
    public UDate getToDate() {
        return toDate__;
    }

    /**
     * <p>toDate をセットします。
     * @param toDate toDate
     */
    public void setToDate(UDate toDate) {
        toDate__ = toDate;
    }

    /**
     * <p>smlSid を取得します。
     * @return smlSid
     * @see jp.groupsession.v2.sml.sml090.Sml090SearchParameterModel#smlSid__
     */
    public int getSmlSid() {
        return smlSid__;
    }

    /**
     * <p>smlSid をセットします。
     * @param smlSid smlSid
     * @see jp.groupsession.v2.sml.sml090.Sml090SearchParameterModel#smlSid__
     */
    public void setSmlSid(int smlSid) {
        smlSid__ = smlSid;
    }



}