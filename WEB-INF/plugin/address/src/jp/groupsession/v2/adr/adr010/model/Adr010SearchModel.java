package jp.groupsession.v2.adr.adr010.model;

import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] アドレス帳一覧画面の検索条件を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr010SearchModel extends AbstractModel {

    /** セッションユーザSID */
    private int sessionUser__ = 0;

    /** 処理種別 */
    private int cmdMode__ = 0;
    /** ソート項目 */
    private int sortKey__ = 0;
    /** 並び順 */
    private int orderKey__ = 0;

    /** 企業コード */
    private String coCode__ = null;
    /** 会社名 */
    private String coName__ = null;
    /** 会社名カナ */
    private String coNameKn__ = null;
    /** 支店・営業所名 */
    private String coBaseName__ = null;
    /** 業種 */
    private int atiSid__ = -1;
    /** 都道府県 */
    private int tdfk__ = -1;
    /** 備考 */
    private String biko__ = null;


    /** 氏名カナ 頭文字 */
    private String unameKnHead__ = null;

    /** 会社名 頭文字 */
    private String cnameKnHead__ = null;

    /** グループ */
    private int group__ = -1;
    /** ユーザ */
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

    /** ラベル */
    private String[] label__ = null;

    /** 会社SID */
    private Integer companySid__ = null;
    /** 会社拠点SID */
    private Integer companyBaseSid__ = null;

    /** ページ */
    private int page__ = 0;
    /** 1ページの最大表示件数 */
    private int maxViewCount__ = 0;

    /** アドレスSID(プロジェクトプラグイン用) */
    private int adrSid__ = 0;
    /** プロジェクトSID(プロジェクトプラグイン用) */
    private int prjSid__ = 0;
    /** プロジェクト検索フラグ(プロジェクトプラグイン用) */
    private boolean prjSerchFlg__ = false;
    /** プロジェクト区分(プロジェクトプラグイン用) */
    private int projectKbn__ = GSConstAddress.PROTYPE_ADD;
    /** 状態(プロジェクトプラグイン用) */
    private int statusKbn__ = GSConstAddress.STATUS_ALL;
    /** ユーザSID(プロジェクトプラグイン用) */
    private int usrSid__ = -1;
    /** システム管理者区分(プロジェクトプラグイン用) True:システム管理者 false:一般ユーザ */
    private boolean usrKbn__ = false;

    /** 添付ファイル有無 */
    private int tempFileExist__ = 0;
    /** 日時 from */
    private UDate dateFr__;
    /** 日時 to */
    private UDate dateTo__;
    /** 種別 */
    private int syubetsu__ = 0;
    /** キーワード */
    private List<String> adrKeyValue__;
    /** キーワード区分(AND・OR) */
    private int keyWordkbn__;
    /** 検索対象 タイトル*/
    private boolean targetTitle__ = false;
    /** 検索対象 備考*/
    private boolean targetBiko__ = false;

    /** 日時検索フラグ */
    private boolean dateSchFlg__ = false;

    /** 添付ファイル存在コンタクト履歴SID */
    private List<Integer> haveTmpFileAdcSids__;

    /** 管理者検索フラグ*/
    private boolean adminSearchFlg__ = false;

    /**
     * <p>adrKeyValue を取得します。
     * @return adrKeyValue
     */
    public List<String> getAdrKeyValue() {
        return adrKeyValue__;
    }
    /**
     * <p>adrKeyValue をセットします。
     * @param adrKeyValue adrKeyValue
     */
    public void setAdrKeyValue(List<String> adrKeyValue) {
        adrKeyValue__ = adrKeyValue;
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
     * <p>syubetsu を取得します。
     * @return syubetsu
     */
    public int getSyubetsu() {
        return syubetsu__;
    }
    /**
     * <p>syubetsu をセットします。
     * @param syubetsu syubetsu
     */
    public void setSyubetsu(int syubetsu) {
        syubetsu__ = syubetsu;
    }
    /**
     * <p>tempFileExist を取得します。
     * @return tempFileExist
     */
    public int getTempFileExist() {
        return tempFileExist__;
    }
    /**
     * <p>tempFileExist をセットします。
     * @param tempFileExist tempFileExist
     */
    public void setTempFileExist(int tempFileExist) {
        tempFileExist__ = tempFileExist;
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
     * <p>biko を取得します。
     * @return biko
     */
    public String getBiko() {
        return biko__;
    }
    /**
     * <p>biko をセットします。
     * @param biko biko
     */
    public void setBiko(String biko) {
        biko__ = biko;
    }
    /**
     * <p>cmdMode を取得します。
     * @return cmdMode
     */
    public int getCmdMode() {
        return cmdMode__;
    }
    /**
     * <p>cmdMode をセットします。
     * @param cmdMode cmdMode
     */
    public void setCmdMode(int cmdMode) {
        cmdMode__ = cmdMode;
    }
    /**
     * <p>coBaseName を取得します。
     * @return coBaseName
     */
    public String getCoBaseName() {
        return coBaseName__;
    }
    /**
     * <p>coBaseName をセットします。
     * @param coBaseName coBaseName
     */
    public void setCoBaseName(String coBaseName) {
        coBaseName__ = coBaseName;
    }
    /**
     * <p>coCode を取得します。
     * @return coCode
     */
    public String getCoCode() {
        return coCode__;
    }
    /**
     * <p>coCode をセットします。
     * @param coCode coCode
     */
    public void setCoCode(String coCode) {
        coCode__ = coCode;
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
     * <p>coNameKn を取得します。
     * @return coNameKn
     */
    public String getCoNameKn() {
        return coNameKn__;
    }
    /**
     * <p>coNameKn をセットします。
     * @param coNameKn coNameKn
     */
    public void setCoNameKn(String coNameKn) {
        coNameKn__ = coNameKn;
    }
    /**
     * <p>group を取得します。
     * @return group
     */
    public int getGroup() {
        return group__;
    }
    /**
     * <p>group をセットします。
     * @param group group
     */
    public void setGroup(int group) {
        group__ = group;
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
     * <p>tdfk を取得します。
     * @return tdfk
     */
    public int getTdfk() {
        return tdfk__;
    }
    /**
     * <p>tdfk をセットします。
     * @param tdfk tdfk
     */
    public void setTdfk(int tdfk) {
        tdfk__ = tdfk;
    }
    /**
     * <p>unameKnHead を取得します。
     * @return unameKnHead
     */
    public String getUnameKnHead() {
        return unameKnHead__;
    }
    /**
     * <p>unameKnHead をセットします。
     * @param unameKnHead unameKnHead
     */
    public void setUnameKnHead(String unameKnHead) {
        unameKnHead__ = unameKnHead;
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
     * <p>label を取得します。
     * @return label
     */
    public String[] getLabel() {
        return label__;
    }
    /**
     * <p>label をセットします。
     * @param label label
     */
    public void setLabel(String[] label) {
        label__ = label;
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
     * <p>companyBaseSid を取得します。
     * @return companyBaseSid
     */
    public Integer getCompanyBaseSid() {
        return companyBaseSid__;
    }
    /**
     * <p>companyBaseSid をセットします。
     * @param companyBaseSid companyBaseSid
     */
    public void setCompanyBaseSid(Integer companyBaseSid) {
        companyBaseSid__ = companyBaseSid;
    }
    /**
     * <p>companySid を取得します。
     * @return companySid
     */
    public Integer getCompanySid() {
        return companySid__;
    }
    /**
     * <p>companySid をセットします。
     * @param companySid companySid
     */
    public void setCompanySid(Integer companySid) {
        companySid__ = companySid;
    }
    /**
     * <br>[機  能] 検索条件が設定されているかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @return 判定結果
     */
    public boolean existSearchCondition() {

        if (!StringUtil.isNullZeroString(coCode__)) {
            return true;
        }

        if (!StringUtil.isNullZeroString(coName__)) {
            return true;
        }

        if (!StringUtil.isNullZeroString(coNameKn__)) {
            return true;
        }

        if (!StringUtil.isNullZeroString(coBaseName__)) {
            return true;
        }

        if (atiSid__ >= 0) {
            return true;
        }

        if (tdfk__ > 0) {
            return true;
        }

        if (!StringUtil.isNullZeroString(biko__)) {
            return true;
        }

        if (!StringUtil.isNullZeroString(unameKnHead__)) {
            return true;
        }

        if (group__ >= 0) {
            return true;
        }
        if (user__ >= 0) {
            return true;
        }

        if (!StringUtil.isNullZeroString(unameSei__)) {
            return true;
        }

        if (!StringUtil.isNullZeroString(unameMei__)) {
            return true;
        }

        if (!StringUtil.isNullZeroString(unameSeiKn__)) {
            return true;
        }

        if (!StringUtil.isNullZeroString(unameMeiKn__)) {
            return true;
        }

        if (position__ >= 0) {
            return true;
        }

        if (!StringUtil.isNullZeroString(mail__)) {
            return true;
        }

        if (label__ != null && label__.length > 0) {
            return true;
        }

        if (companySid__ != null) {
            return true;
        }

        if (companyBaseSid__ != null) {
            return true;
        }

        return false;
    }
    /**
     * <p>cnameKnHead を取得します。
     * @return cnameKnHead
     */
    public String getCnameKnHead() {
        return cnameKnHead__;
    }
    /**
     * <p>cnameKnHead をセットします。
     * @param cnameKnHead cnameKnHead
     */
    public void setCnameKnHead(String cnameKnHead) {
        cnameKnHead__ = cnameKnHead;
    }
    /**
     * @return adrSid
     */
    public int getAdrSid() {
        return adrSid__;
    }
    /**
     * @param adrSid 設定する adrSid
     */
    public void setAdrSid(int adrSid) {
        adrSid__ = adrSid;
    }
    /**
     * @return prjSid
     */
    public int getPrjSid() {
        return prjSid__;
    }
    /**
     * @param prjSid 設定する prjSid
     */
    public void setPrjSid(int prjSid) {
        prjSid__ = prjSid;
    }
    /**
     * @return prjSerchFlg
     */
    public boolean isPrjSerchFlg() {
        return prjSerchFlg__;
    }
    /**
     * @param prjSerchFlg 設定する prjSerchFlg
     */
    public void setPrjSerchFlg(boolean prjSerchFlg) {
        prjSerchFlg__ = prjSerchFlg;
    }
    /**
     * @return projectKbn
     */
    public int getProjectKbn() {
        return projectKbn__;
    }
    /**
     * @param projectKbn 設定する projectKbn
     */
    public void setProjectKbn(int projectKbn) {
        projectKbn__ = projectKbn;
    }
    /**
     * @return statusKbn
     */
    public int getStatusKbn() {
        return statusKbn__;
    }
    /**
     * @param statusKbn 設定する statusKbn
     */
    public void setStatusKbn(int statusKbn) {
        statusKbn__ = statusKbn;
    }
    /**
     * @return usrKbn
     */
    public boolean isUsrKbn() {
        return usrKbn__;
    }
    /**
     * @param usrKbn 設定する usrKbn
     */
    public void setUsrKbn(boolean usrKbn) {
        usrKbn__ = usrKbn;
    }
    /**
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * @param usrSid 設定する usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }
    /**
     * <p>dateFr を取得します。
     * @return dateFr
     */
    public UDate getDateFr() {
        return dateFr__;
    }
    /**
     * <p>dateFr をセットします。
     * @param dateFr dateFr
     */
    public void setDateFr(UDate dateFr) {
        dateFr__ = dateFr;
    }
    /**
     * <p>dateTo を取得します。
     * @return dateTo
     */
    public UDate getDateTo() {
        return dateTo__;
    }
    /**
     * <p>dateTo をセットします。
     * @param dateTo dateTo
     */
    public void setDateTo(UDate dateTo) {
        dateTo__ = dateTo;
    }
    /**
     * <p>targetBiko を取得します。
     * @return targetBiko
     */
    public boolean isTargetBiko() {
        return targetBiko__;
    }
    /**
     * <p>targetBiko をセットします。
     * @param targetBiko targetBiko
     */
    public void setTargetBiko(boolean targetBiko) {
        targetBiko__ = targetBiko;
    }
    /**
     * <p>targetTitle を取得します。
     * @return targetTitle
     */
    public boolean isTargetTitle() {
        return targetTitle__;
    }
    /**
     * <p>targetTitle をセットします。
     * @param targetTitle targetTitle
     */
    public void setTargetTitle(boolean targetTitle) {
        targetTitle__ = targetTitle;
    }
    /**
     * <p>dateSchFlg を取得します。
     * @return dateSchFlg
     */
    public boolean isDateSchFlg() {
        return dateSchFlg__;
    }
    /**
     * <p>dateSchFlg をセットします。
     * @param dateSchFlg dateSchFlg
     */
    public void setDateSchFlg(boolean dateSchFlg) {
        dateSchFlg__ = dateSchFlg;
    }
    /**
     * <p>haveTmpFileAdcSids を取得します。
     * @return haveTmpFileAdcSids
     */
    public List<Integer> getHaveTmpFileAdcSids() {
        return haveTmpFileAdcSids__;
    }
    /**
     * <p>haveTmpFileAdcSids をセットします。
     * @param haveTmpFileAdcSids haveTmpFileAdcSids
     */
    public void setHaveTmpFileAdcSids(List<Integer> haveTmpFileAdcSids) {
        haveTmpFileAdcSids__ = haveTmpFileAdcSids;
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
     * <p>adminSearchFlg を取得します。
     * @return adminSearchFlg
     */
    public boolean isAdminSearchFlg() {
        return adminSearchFlg__;
    }
    /**
     * <p>adminSearchFlg をセットします。
     * @param adminSearchFlg adminSearchFlg
     */
    public void setAdminSearchFlg(boolean adminSearchFlg) {
        adminSearchFlg__ = adminSearchFlg;
    }
}