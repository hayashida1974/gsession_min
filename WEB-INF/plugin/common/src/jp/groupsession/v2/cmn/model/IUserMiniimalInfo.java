package jp.groupsession.v2.cmn.model;

public interface IUserMiniimalInfo {

    /**
     * @return ユーザ削除区分
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsrJkbn()
     */
    int getUsrJkbn();

    /**
     * @return ユーザSID
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsrSid()
     */
    int getUsrSid();

    /**
     * @return ユーザ姓
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiSei()
     */
    String getUsiSei();

    /**
     * @return ユーザ名
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMei()
     */
    String getUsiMei();

    /**
     * @return ユーザ姓カナ
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiSeiKn()
     */
    String getUsiSeiKn();

    /**
     * @return ユーザ名カナ
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiMeiKn()
     */
    String getUsiMeiKn();

    /**
     * @return ユーザ姓名
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiName()
     */
    String getUsiName();

    /**
     * @return ユーザ姓名カナ
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsiName()
     */
    String getUsiNameKn();

    /**
     * @return ログイン停止フラグ
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel#getUsrUkoFlg()
     */
    int getUsrUkoFlg();

    /**
     * @return ログインID
     * @see jp.groupsession.v2.cmn.model.base.CmnUsrmModel#getUsrLgid()
     */
    String getUsrLgid();

}