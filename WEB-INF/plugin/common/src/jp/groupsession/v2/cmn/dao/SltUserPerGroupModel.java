package jp.groupsession.v2.cmn.dao;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 所属利用者セレクトボックスの情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考] CMN_BELONGM, CMN_GROUPM, CMN_USRM_INFの情報を対象とする
 *
 * @author JTS
 */
public class SltUserPerGroupModel extends AbstractModel {
    /** グループSID */
    private int groupsid__ = -1;
    /** ユーザーSID */
    private int usrsid__ = -1;
    /** ログインID */
    private String usrlgid__ = null;
    /** 氏名　姓 */
    private String namesei__ = null;
    /** 氏名　名 */
    private String namemei__ = null;
    /** 氏名　姓名セット */
    private String fullName__ = null;
    /** 社員/職員番号 */
    private String syainno__ = null;
    /** 役職 */
    private String yakusyoku__ = null;
    /** 最終ログイン時間 */
    private UDate lgintime__ = null;
    /** 最終ログイン時間 表示用 */
    private String lgintimeStr__ = null;
    /** グループ管理者 */
    private int grpAdmin__ = -1;
    /** USR_UKO_FLG mapping */
    private int usrUkoFlg__ = GSConst.YUKOMUKO_YUKO;


    /**
     * @return grpAdmin__ を戻します。
     */
    public int getGrpAdmin() {
        return grpAdmin__;
    }
    /**
     * @param grpAdmin 設定する grpAdmin__。
     */
    public void setGrpAdmin(int grpAdmin) {
        grpAdmin__ = grpAdmin;
    }
    /**
     * @return groupsid を戻します。
     */
    public int getGroupsid() {
        return groupsid__;
    }
    /**
     * @param groupsid 設定する groupsid__。
     */
    public void setGroupsid(int groupsid) {
        this.groupsid__ = groupsid;
    }
    /**
     * @return namemei__ を戻します。
     */
    public String getNamemei() {
        return namemei__;
    }
    /**
     * @param namemei 設定する namemei__。
     */
    public void setNamemei(String namemei) {
        this.namemei__ = namemei;
    }
    /**
     * @return namesei__ を戻します。
     */
    public String getNamesei() {
        return namesei__;
    }
    /**
     * @param namesei 設定する namesei__。
     */
    public void setNamesei(String namesei) {
        this.namesei__ = namesei;
    }
    /**
     * @return usrsid__ を戻します。
     */
    public int getUsrsid() {
        return usrsid__;
    }
    /**
     * @param usrsid 設定する usrsid__。
     */
    public void setUsrsid(int usrsid) {
        this.usrsid__ = usrsid;
    }

    /**
     * @return fullName__ を戻します。
     */
    public String getFullName() {
        return fullName__;
    }
    /**
     * @param fullName 設定する fullName__。
     */
    public void setFullName(String fullName) {
        this.fullName__ = fullName;
    }
    /**
     * @return syainno を戻します。
     */
    public String getSyainno() {
        return syainno__;
    }
    /**
     * @param syainno 設定する syainno。
     */
    public void setSyainno(String syainno) {
        syainno__ = syainno;
    }
    /**
     * @return usrlgid を戻します。
     */
    public String getUsrlgid() {
        return usrlgid__;
    }
    /**
     * @param usrlgid 設定する usrlgid。
     */
    public void setUsrlgid(String usrlgid) {
        usrlgid__ = usrlgid;
    }
    /**
     * @return yakusyoku を戻します。
     */
    public String getYakusyoku() {
        return yakusyoku__;
    }
    /**
     * @param yakusyoku 設定する yakusyoku。
     */
    public void setYakusyoku(String yakusyoku) {
        yakusyoku__ = yakusyoku;
    }
    /**
     * @return lgintime を戻します。
     */
    public UDate getLgintime() {
        return lgintime__;
    }
    /**
     * @param lgintime 設定する lgintime。
     */
    public void setLgintime(UDate lgintime) {
        lgintime__ = lgintime;
    }
    /**
     * <p>lgintimeStr を取得します。
     * @return lgintimeStr
     */
    public String getLgintimeStr() {
        return lgintimeStr__;
    }
    /**
     * <p>lgintimeStr をセットします。
     * @param lgintimeStr lgintimeStr
     */
    public void setLgintimeStr(String lgintimeStr) {
        lgintimeStr__ = lgintimeStr;
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
