package jp.groupsession.v2.adr.adr330.model;

import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.adr010.Adr010Const;
import jp.groupsession.v2.adr.adr010.model.Adr010SearchModel;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;

/**
 *
 * <br>[機  能] 管理者設定 アドレス管理用検索パラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr330ViewSearchModel {

    /**選択グループ*/
    private String groupSidStr__ = "-1";
    /** セッションユーザSID */
    private int sessionUser__ = 0;

    /** ソート項目 */
    private int sortKey__ = Adr010Const.SORTKEY_UNAME;
    /** 並び順 */
    private int orderKey__ = Adr010Const.ORDERKEY_ASC;

    /** 会社名 */
    private String coName__ = null;
    /** 業種 */
    private int atiSid__ = -1;

    /** 担当者ユーザSID */
    private int user__ = -1;

    /** 氏名 姓 */
    private String unameSei__ = null;
    /** 氏名 名 */
    private String unameMei__ = null;
    /** 氏名カナ 姓 */
    private String unameSeiKn__ = null;
    /** 氏名カナ 名 */
    private String unameMeiKn__ = null;
    /** 所属 */
    private String syozoku__ = null;
    /** 役職 */
    private int position__ = -1;
    /** E-MAIL */
    private String mail__ = null;

    /** ページ */
    private int page__ = 0;
    /** 1ページの最大表示件数 */
    private int maxViewCount__ = 0;


    /**
     * <p>groupSidStr を取得します。
     * @return groupSidStr
     */
    public String getGroupSidStr() {
        return groupSidStr__;
    }

    /**
     * <p>sessionUser を取得します。
     * @return sessionUser
     */
    public int getSessionUser() {
        return sessionUser__;
    }

    /**
     * <p>sessionUser をセットします。
     * @param sessionUser sessionUser
     */
    public void setSessionUser(int sessionUser) {
        sessionUser__ = sessionUser;
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
     * <p>orderKey を取得します。
     * @return orderKey
     */
    public int getOrderKey() {
        return orderKey__;
    }

    /**
     * <p>orderKey をセットします。
     * @param orderKey orderKey
     */
    public void setOrderKey(int orderKey) {
        orderKey__ = orderKey;
    }

    /**
     * <p>coName を取得します。
     * @return coName
     */
    public String getCoName() {
        return coName__;
    }

    /**
     * <p>coName をセットします。
     * @param coName coName
     */
    public void setCoName(String coName) {
        coName__ = coName;
    }

    /**
     * <p>atiSid を取得します。
     * @return atiSid
     */
    public int getAtiSid() {
        return atiSid__;
    }

    /**
     * <p>atiSid をセットします。
     * @param atiSid atiSid
     */
    public void setAtiSid(int atiSid) {
        atiSid__ = atiSid;
    }



    /**
     * <p>user を取得します。
     * @return user
     */
    public int getUser() {
        return user__;
    }

    /**
     * <p>user をセットします。
     * @param user user
     */
    public void setUser(int user) {
        user__ = user;
    }

    /**
     * <p>unameSei を取得します。
     * @return unameSei
     */
    public String getUnameSei() {
        return unameSei__;
    }

    /**
     * <p>unameSei をセットします。
     * @param unameSei unameSei
     */
    public void setUnameSei(String unameSei) {
        unameSei__ = unameSei;
    }

    /**
     * <p>unameMei を取得します。
     * @return unameMei
     */
    public String getUnameMei() {
        return unameMei__;
    }

    /**
     * <p>unameMei をセットします。
     * @param unameMei unameMei
     */
    public void setUnameMei(String unameMei) {
        unameMei__ = unameMei;
    }

    /**
     * <p>unameSeiKn を取得します。
     * @return unameSeiKn
     */
    public String getUnameSeiKn() {
        return unameSeiKn__;
    }

    /**
     * <p>unameSeiKn をセットします。
     * @param unameSeiKn unameSeiKn
     */
    public void setUnameSeiKn(String unameSeiKn) {
        unameSeiKn__ = unameSeiKn;
    }

    /**
     * <p>unameMeiKn を取得します。
     * @return unameMeiKn
     */
    public String getUnameMeiKn() {
        return unameMeiKn__;
    }

    /**
     * <p>unameMeiKn をセットします。
     * @param unameMeiKn unameMeiKn
     */
    public void setUnameMeiKn(String unameMeiKn) {
        unameMeiKn__ = unameMeiKn;
    }

    /**
     * <p>syozoku を取得します。
     * @return syozoku
     */
    public String getSyozoku() {
        return syozoku__;
    }

    /**
     * <p>syozoku をセットします。
     * @param syozoku syozoku
     */
    public void setSyozoku(String syozoku) {
        syozoku__ = syozoku;
    }

    /**
     * <p>position を取得します。
     * @return position
     */
    public int getPosition() {
        return position__;
    }

    /**
     * <p>position をセットします。
     * @param position position
     */
    public void setPosition(int position) {
        position__ = position;
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
     * <p>page を取得します。
     * @return page
     */
    public int getPage() {
        return page__;
    }

    /**
     * <p>page をセットします。
     * @param page page
     */
    public void setPage(int page) {
        page__ = page;
    }

    /**
     * <p>maxViewCount を取得します。
     * @return maxViewCount
     */
    public int getMaxViewCount() {
        return maxViewCount__;
    }

    /**
     * <p>maxViewCount をセットします。
     * @param maxViewCount maxViewCount
     */
    public void setMaxViewCount(int maxViewCount) {
        maxViewCount__ = maxViewCount;
    }

    /**
     * <p>groupSidStr をセットします。
     * @param groupSidStr groupSidStr
     */
    public void setGroupSidStr(String groupSidStr) {
        groupSidStr__ = groupSidStr;
    }

    /**
    *
    * <br>[機  能] 共通メッセージフォームのパラメータ設定 管理者設定アドレス管理画面用のみ
    * <br>[解  説]
    * <br>[備  考]
    * @param msgForm 共通メッセージフォーム
    * @param paramName 検索フォームパラメータ名
    */
   public void setHiddenParamAdr330SearchParam(
           Cmn999Form msgForm, String paramName) {
       //ページ数
       msgForm.addHiddenParam(paramName + ".page", this.getPage());
       //ソートキー
       msgForm.addHiddenParam(paramName + ".sortKey", this.getSortKey());
       //オーダーキー
       msgForm.addHiddenParam(paramName + ".orderKey", this.getOrderKey());

       //氏名 姓
       msgForm.addHiddenParam(paramName + ".unameSei", this.getUnameSei());
       //氏名 名
       msgForm.addHiddenParam(paramName + ".unameMei", this.getUnameMei());
       //氏名カナ 姓
       msgForm.addHiddenParam(paramName + ".unameSeiKn", this.getUnameSeiKn());
       //氏名カナ 名
       msgForm.addHiddenParam(paramName + ".unameMeiKn", this.getUnameMeiKn());
       //会社名
       msgForm.addHiddenParam(paramName + ".coName", this.getCoName());
       //所属
       msgForm.addHiddenParam(paramName + ".syozoku", this.getSyozoku());
       //役職
       msgForm.addHiddenParam(paramName + ".position", this.getPosition());
       //E-MAIL
       msgForm.addHiddenParam(paramName + ".mail", this.getMail());
       //グループ
       msgForm.addHiddenParam(paramName + ".groupSidStr", this.getGroupSidStr());

       //担当者ユーザ
       msgForm.addHiddenParam(paramName + ".user", this.getUser());
       //業種
       msgForm.addHiddenParam(paramName + ".atiSid", this.getAtiSid());
   }

   /**
    * <br>[機  能] 表示用検索モデルからDao用検索モデルの変換を行う
    * <br>[解  説]
    * <br>[備  考]
    * @param userSid セッションユーザSID
    * @return 検索モデル
    *
    */
   public Adr010SearchModel createSearchModel(
           int userSid) {
       Adr010SearchModel ret = new Adr010SearchModel();

       ret.setSessionUser(userSid);
       ret.setAdminSearchFlg(true);

       //ページ数
       ret.setPage(this.getPage());
       //ソートキー
       ret.setSortKey(this.getSortKey());
       //オーダーキー
       ret.setOrderKey(this.getOrderKey());

       ret.setCmdMode(Adr010Const.CMDMODE_DETAILED);

       //氏名 姓
       ret.setUnameSei(this.getUnameSei());
       //氏名 名
       ret.setUnameMei(this.getUnameMei());
       //氏名カナ 姓
       ret.setUnameSeiKn(this.getUnameSeiKn());
       //氏名カナ 名
       ret.setUnameMeiKn(this.getUnameMeiKn());
       //会社名
       ret.setCoName(this.getCoName());
       //所属
       ret.setSyozoku(this.getSyozoku());
       //役職
       ret.setPosition(this.getPosition());
       //E-MAIL
       ret.setMail(this.getMail());
       if (!AdrCommonBiz.isMyGroupSid(this.getGroupSidStr())) {
           //グループ
           ret.setGroup(Integer.parseInt(this.getGroupSidStr()));
       }
       //担当者ユーザ
       ret.setUser(this.getUser());
       //業種
       ret.setAtiSid(this.getAtiSid());
       return ret;
   }
}
