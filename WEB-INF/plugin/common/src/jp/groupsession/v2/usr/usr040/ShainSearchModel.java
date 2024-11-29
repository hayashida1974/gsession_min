package jp.groupsession.v2.usr.usr040;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] ユーザ情報一覧画面 検索条件を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ShainSearchModel {
    /** 選択グループSID */
    private int selectgsid__ = -1;
    /** ユーザが選択した検索用カナ */
    private String searchKana__ = null;
    /** ユーザID(Save) */
    private String userId__ = null;

//--------画面用↓
    /** キーワード (Save) */
    private String keyword__ = null;
    /** キーワード区分 (社員/職員番号 Save) */
    private int keyKbnShainno__ = 0;
    /** キーワード区分 (氏名 Save) */
    private int keyKbnName__ = 0;
    /** キーワード区分 (氏名カナ Save) */
    private int keyKbnNameKn__ = 0;
    /** キーワード区分 (メールアドレス Save) */
    private int keyKbnMail__ = 0;
    /**キーワード区分(電話番号)*/
    private int keyKbnTel__ = 0;
//--------画面用↑

//--------API用↓
    /** 社員/職員番号(Save) */
    private String shainno__ = null;
    /** ユーザ名 姓(Save) */
    private String sei__ = null;
    /** ユーザ名 名(Save) */
    private String mei__ = null;
    /** ユーザ名 姓カナ(Save) */
    private String seikn__ = null;
    /** ユーザ名 名カナ(Save) */
    private String meikn__ = null;
    /** メール(Save) */
    private String mail__ = null;
//--------API用↑

    /** 年齢From(Save) */
    private String agefrom__ = null;
    /** 年齢To(Save) */
    private String ageto__ = null;
    /** 役職(Save) */
    private int yakushoku__ = GSConstCommon.NUM_INIT;
    /** 都道府県コード(Save) */
    private String tdfkCd__ = null;

    /** 入社年月日from */
    private UDate entranceDateFr__;
    /** 入社年月日to */
    private UDate entranceDateTo__;

    /** 性別 */
    private String seibetu__;
    /**ログイン停止状態 -1:すべて 0:停止ユーザ含まない 1:停止ユーザのみ*/
    private int usrUkoFlg__ = GSConstUser.USER_SEARCH_USRUKOFLG_ALL;

    /** システムユーザを検索結果に含まない */
    private boolean excludeSysUser__ = true;

    /** 第一ソートキー */
    private int sortKey__ = GSConstUser.USER_SORT_NAME;
    /** 第一ソートオーダー */
    private int sortOrder__ = GSConst.ORDER_KEY_ASC;
    /** 第二ソートキー */
    private int sortKey2__ = GSConstUser.USER_SORT_SNO;
    /** 第二ソートオーダー */
    private int sortOrder2__ = GSConst.ORDER_KEY_ASC;

    /** CSV出力時に使用するモード */
    private int outputCsvMode__ = -1;

    /** カテゴリーSID */
    private String categorySid__ = null;
    /** ラベルSID */
    private String[] labelSid__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public ShainSearchModel() {
    }

    /**
     * <p>agefrom を取得します。
     * @return agefrom
     */
    public String getAgefrom() {
        return agefrom__;
    }

    /**
     * <p>agefrom をセットします。
     * @param agefrom agefrom
     */
    public void setAgefrom(String agefrom) {
        agefrom__ = agefrom;
    }

    /**
     * <p>ageto を取得します。
     * @return ageto
     */
    public String getAgeto() {
        return ageto__;
    }

    /**
     * <p>ageto をセットします。
     * @param ageto ageto
     */
    public void setAgeto(String ageto) {
        ageto__ = ageto;
    }

    /**
     * <p>mail を取得します。
     * @return mail
     */
    public String getMail() {
        return mail__;
    }

    /**
     * <p>mail をセットします。
     * @param mail mail
     */
    public void setMail(String mail) {
        mail__ = mail;
    }

    /**
     * <p>mei を取得します。
     * @return mei
     */
    public String getMei() {
        return mei__;
    }

    /**
     * <p>mei をセットします。
     * @param mei mei
     */
    public void setMei(String mei) {
        mei__ = mei;
    }

    /**
     * <p>meikn を取得します。
     * @return meikn
     */
    public String getMeikn() {
        return meikn__;
    }

    /**
     * <p>meikn をセットします。
     * @param meikn meikn
     */
    public void setMeikn(String meikn) {
        meikn__ = meikn;
    }

    /**
     * <p>searchKana を取得します。
     * @return searchKana
     */
    public String getSearchKana() {
        return searchKana__;
    }

    /**
     * <p>searchKana をセットします。
     * @param searchKana searchKana
     */
    public void setSearchKana(String searchKana) {
        searchKana__ = searchKana;
    }

    /**
     * <p>sei を取得します。
     * @return sei
     */
    public String getSei() {
        return sei__;
    }

    /**
     * <p>sei をセットします。
     * @param sei sei
     */
    public void setSei(String sei) {
        sei__ = sei;
    }

    /**
     * <p>seikn を取得します。
     * @return seikn
     */
    public String getSeikn() {
        return seikn__;
    }

    /**
     * <p>seikn をセットします。
     * @param seikn seikn
     */
    public void setSeikn(String seikn) {
        seikn__ = seikn;
    }

    /**
     * <p>selectgsid を取得します。
     * @return selectgsid
     */
    public int getSelectgsid() {
        return selectgsid__;
    }

    /**
     * <p>selectgsid をセットします。
     * @param selectgsid selectgsid
     */
    public void setSelectgsid(int selectgsid) {
        selectgsid__ = selectgsid;
    }

    /**
     * <p>shainno を取得します。
     * @return shainno
     */
    public String getShainno() {
        return shainno__;
    }

    /**
     * <p>shainno をセットします。
     * @param shainno shainno
     */
    public void setShainno(String shainno) {
        shainno__ = shainno;
    }

    /**
     * <p>userId を取得します。
     * @return userId
     */
    public String getUserId() {
        return userId__;
    }

    /**
     * <p>userId をセットします。
     * @param userId userId
     */
    public void setUserId(String userId) {
        userId__ = userId;
    }

    /**
     * <p>tdfkCd を取得します。
     * @return tdfkCd
     */
    public String getTdfkCd() {
        return tdfkCd__;
    }

    /**
     * <p>tdfkCd をセットします。
     * @param tdfkCd tdfkCd
     */
    public void setTdfkCd(String tdfkCd) {
        tdfkCd__ = tdfkCd;
    }

    /**
     * <p>yakushoku を取得します。
     * @return yakushoku
     */
    public int getYakushoku() {
        return yakushoku__;
    }

    /**
     * <p>yakushoku をセットします。
     * @param yakushoku yakushoku
     */
    public void setYakushoku(int yakushoku) {
        yakushoku__ = yakushoku;
    }

    /**
     * <p>outputCsvMode を取得します。
     * @return outputCsvMode
     */
    public int getOutputCsvMode() {
        return outputCsvMode__;
    }

    /**
     * <p>outputCsvMode をセットします。
     * @param outputCsvMode outputCsvMode
     */
    public void setOutputCsvMode(int outputCsvMode) {
        outputCsvMode__ = outputCsvMode;
    }

    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public int getSortKey() {
        return sortKey__;
    }

    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(int sortKey) {
        sortKey__ = sortKey;
    }

    /**
     * <p>sortOrder を取得します。
     * @return sortOrder
     */
    public int getSortOrder() {
        return sortOrder__;
    }

    /**
     * <p>sortOrder をセットします。
     * @param sortOrder sortOrder
     */
    public void setSortOrder(int sortOrder) {
        sortOrder__ = sortOrder;
    }

    /**
     * <p>excludeSysUser を取得します。
     * @return excludeSysUser
     */
    public boolean isExcludeSysUser() {
        return excludeSysUser__;
    }

    /**
     * <p>excludeSysUser をセットします。
     * @param excludeSysUser excludeSysUser
     */
    public void setExcludeSysUser(boolean excludeSysUser) {
        excludeSysUser__ = excludeSysUser;
    }

    /**
     * @return sortOrder2
     */
    public int getSortOrder2() {
        return sortOrder2__;
    }

    /**
     * @param sortOrder2 設定する sortOrder2
     */
    public void setSortOrder2(int sortOrder2) {
        sortOrder2__ = sortOrder2;
    }

    /**
     * @return sortKey2
     */
    public int getSortKey2() {
        return sortKey2__;
    }

    /**
     * @param sortKey2 設定する sortKey2
     */
    public void setSortKey2(int sortKey2) {
        sortKey2__ = sortKey2;
    }

    /**
     * <p>categorySid を取得します。
     * @return categorySid
     */
    public String getCategorySid() {
        return categorySid__;
    }

    /**
     * <p>categorySid をセットします。
     * @param categorySid categorySid
     */
    public void setCategorySid(String categorySid) {
        categorySid__ = categorySid;
    }

    /**
     * <p>labelSid を取得します。
     * @return labelSid
     */
    public String[] getLabelSid() {
        return labelSid__;
    }

    /**
     * <p>labelSid をセットします。
     * @param labelSid labelSid
     */
    public void setLabelSid(String[] labelSid) {
        labelSid__ = labelSid;
    }

    /**
     * <p>entranceDateFr を取得します。
     * @return entranceDateFr
     */
    public UDate getEntranceDateFr() {
        return entranceDateFr__;
    }

    /**
     * <p>entranceDateFr をセットします。
     * @param entranceDateFr entranceDateFr
     */
    public void setEntranceDateFr(UDate entranceDateFr) {
        entranceDateFr__ = entranceDateFr;
    }

    /**
     * <p>entranceDateTo を取得します。
     * @return entranceDateTo
     */
    public UDate getEntranceDateTo() {
        return entranceDateTo__;
    }

    /**
     * <p>entranceDateTo をセットします。
     * @param entranceDateTo entranceDateTo
     */
    public void setEntranceDateTo(UDate entranceDateTo) {
        entranceDateTo__ = entranceDateTo;
    }

    /**
     * <p>seibetu を取得します。
     * @return seibetu
     */
    public String getSeibetu() {
        return seibetu__;
    }

    /**
     * <p>seibetu をセットします。
     * @param seibetu seibetu
     */
    public void setSeibetu(String seibetu) {
        seibetu__ = seibetu;
    }

    /**
     * <p>keyword を取得します。
     * @return keyword
     */
    public String getKeyword() {
        return keyword__;
    }

    /**
     * <p>keyword をセットします。
     * @param keyword keyword
     */
    public void setKeyword(String keyword) {
        keyword__ = keyword;
    }

    /**
     * <p>keyKbnShainno を取得します。
     * @return keyKbnShainno
     */
    public int getKeyKbnShainno() {
        return keyKbnShainno__;
    }

    /**
     * <p>keyKbnShainno をセットします。
     * @param keyKbnShainno keyKbnShainno
     */
    public void setKeyKbnShainno(int keyKbnShainno) {
        keyKbnShainno__ = keyKbnShainno;
    }

    /**
     * <p>keyKbnName を取得します。
     * @return keyKbnName
     */
    public int getKeyKbnName() {
        return keyKbnName__;
    }

    /**
     * <p>keyKbnName をセットします。
     * @param keyKbnName keyKbnName
     */
    public void setKeyKbnName(int keyKbnName) {
        keyKbnName__ = keyKbnName;
    }

    /**
     * <p>keyKbnNameKn を取得します。
     * @return keyKbnNameKn
     */
    public int getKeyKbnNameKn() {
        return keyKbnNameKn__;
    }

    /**
     * <p>keyKbnNameKn をセットします。
     * @param keyKbnNameKn keyKbnNameKn
     */
    public void setKeyKbnNameKn(int keyKbnNameKn) {
        keyKbnNameKn__ = keyKbnNameKn;
    }

    /**
     * <p>keyKbnMail を取得します。
     * @return keyKbnMail
     */
    public int getKeyKbnMail() {
        return keyKbnMail__;
    }

    /**
     * <p>keyKbnMail をセットします。
     * @param keyKbnMail keyKbnMail
     */
    public void setKeyKbnMail(int keyKbnMail) {
        keyKbnMail__ = keyKbnMail;
    }

    /**
     * <p>keyKbnTel を取得します。
     * @return keyKbnTel
     */
    public int getKeyKbnTel() {
        return keyKbnTel__;
    }

    /**
     * <p>keyKbnTel をセットします。
     * @param keyKbnTel keyKbnTel
     */
    public void setKeyKbnTel(int keyKbnTel) {
        keyKbnTel__ = keyKbnTel;
    }

    /**
     * <p>usrUkoFlg を取得します。
     * @return usrUkoFlg
     */
    public int getUsrUkoFlg() {
        return usrUkoFlg__;
    }

    /**
     * <p>usrUkoFlg をセットします。
     * @param usrUkoFlg usrUkoFlg
     */
    public void setUsrUkoFlg(int usrUkoFlg) {
        usrUkoFlg__ = usrUkoFlg;
    }

}