package jp.groupsession.v2.restapi.response;

import jp.groupsession.v2.cmn.model.CmnUserModel;
import jp.groupsession.v2.usr.GSConstUser;

/** API レスポンスモデルユーザ情報（汎用） */
public class MinimalUserInfoModel {
    /** ユーザ */
    private CmnUserModel usrMdl__;
    /** セッションユーザSID */
    private final int sessionUsrSid__;
    /**
     * @param sessionUsrSid
     * @param usrMdl
     */
    public MinimalUserInfoModel(int sessionUsrSid, CmnUserModel usrMdl) {
        usrMdl__ = usrMdl;
        sessionUsrSid__ = sessionUsrSid;
    }
    /**
     * @return the id
     */
    public String getId() {
        return usrMdl__.getUsrLgid();
    }
    /**
     * @return the sid
     */
    public int getSid() {
        return usrMdl__.getUsrSid();
    }
    /**
     * @return the name
     */
    public String getName() {
        return usrMdl__.getUsiName();
    }
    /**
     * @return the loginStopFlg
     */
    public int getLoginStopFlg() {
        return usrMdl__.getUsrUkoFlg();
    }
    /**
     * @return the userDeleteFlg
     */
    public int getUserDeleteFlg() {
        if (usrMdl__.getUsrJkbn() == GSConstUser.USER_JTKBN_ACTIVE) {
            return 0;
        } else {
            return 1;
        }
    }
    /**
     * @return the imageBinSid
     */
    public long getImageBinSid() {
        if ((getImageKoukaiFlg() == 1 && sessionUsrSid__ != usrMdl__.getUsrSid())
            || getUserDeleteFlg() == 1) {
            return 0;
         }
        return usrMdl__.getBinSid();
    }
    /**
     * @return the imageKoukaiFlg
     */
    public int getImageKoukaiFlg() {
        if (getUserDeleteFlg() == 1) {
            return 0;
        }
        return usrMdl__.getUsiPictKf();
    }
}