package jp.groupsession.v2.rng.rng030;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] 稟議内容確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng030SingiParam {

    /** 経路SID*/
    private int keiroSid__ = 0;
    /** ユーザSID*/
    private int userSid__ = 0;
    /** ユーザ名*/
    private String singiName__ = null;
    /** ユーザ削除フラグ*/
    private int userJkbn__ = 0;
    /** ログイン有効フラグ*/
    private int userUkoFlg__ = 0;
    /** 代理人SID*/
    private int dairiSid__ = 0;
    /** 代理人名*/
    private String singiDairi__ = null;
    /** 後閲指示者名*/
    private String singiKoetu__ = null;
    /** コメント*/
    private String singiComment__ = null;
    /** 状態*/
    private int singiStatus__ = 0;
    /** 確認日*/
    private String singiDate__ = null;
    /** 確認時間*/
    private String singiTime__ = null;
    /** 役職*/
    private String singiPosition__ = null;
    /** 代理人使用フラグ*/
    private int singiDairiFlg__ = 0;
    /** 確認済みフラグ*/
    private int singiCheckFlg__ = 0;
    /** コメント表示フラグ*/
    private int singiCommentFlg__ = 0;
    /** 添付ファイル情報 */
    private List<CmnBinfModel> tmpFileList__ = null;

    /**
     * <p>keiroSid を取得します。
     * @return keiroSid
     */
    public int getKeiroSid() {
        return keiroSid__;
    }
    /**
     * <p>keiroSid をセットします。
     * @param keiroSid keiroSid
     */
    public void setKeiroSid(int keiroSid) {
        keiroSid__ = keiroSid;
    }
    /**
     * <p>name を取得します。
     * @return name
     */
    public String getSingiName() {
        return singiName__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setSingiName(String name) {
        singiName__ = name;
    }
    /**
     * <p>dairi を取得します。
     * @return dairi
     */
    public String getSingiDairi() {
        return singiDairi__;
    }
    /**
     * <p>dairi をセットします。
     * @param dairi dairi
     */
    public void setSingiDairi(String dairi) {
        singiDairi__ = dairi;
    }
    /**
     * <p>comment を取得します。
     * @return comment
     */
    public String getSingiComment() {
        return singiComment__;
    }
    /**
     * <p>comment をセットします。
     * @param comment comment
     */
    public void setSingiComment(String comment) {
        singiComment__ = comment;
    }
    /**
     * <p>status を取得します。
     * @return status
     */
    public int getSingiStatus() {
        return singiStatus__;
    }
    /**
     * <p>status をセットします。
     * @param status status
     */
    public void setSingiStatus(int status) {
        singiStatus__ = status;
    }
    /**
     * <p>date を取得します。
     * @return date
     */
    public String getSingiDate() {
        return singiDate__;
    }
    /**
     * <p>date をセットします。
     * @param date date
     */
    public void setSingiDate(String date) {
        singiDate__ = date;
    }
    /**
     * <p>singiDairiFlg を取得します。
     * @return singiDairiFlg
     */
    public int getSingiDairiFlg() {
        return singiDairiFlg__;
    }
    /**
     * <p>singiDairiFlg をセットします。
     * @param singiDairiFlg singiDairiFlg
     */
    public void setSingiDairiFlg(int singiDairiFlg) {
        singiDairiFlg__ = singiDairiFlg;
    }
    /**
     * <p>singiCheckFlg を取得します。
     * @return singiCheckFlg
     */
    public int getSingiCheckFlg() {
        return singiCheckFlg__;
    }
    /**
     * <p>singiCheckFlg をセットします。
     * @param singiCheckFlg singiCheckFlg
     */
    public void setSingiCheckFlg(int singiCheckFlg) {
        singiCheckFlg__ = singiCheckFlg;
    }
    /**
     * <p>singiCommentFlg を取得します。
     * @return singiCommentFlg
     */
    public int getSingiCommentFlg() {
        return singiCommentFlg__;
    }
    /**
     * <p>singiCommentFlg をセットします。
     * @param singiCommentFlg singiCommentFlg
     */
    public void setSingiCommentFlg(int singiCommentFlg) {
        singiCommentFlg__ = singiCommentFlg;
    }
    /**
     * <p>tmpFileList を取得します。
     * @return tmpFileList
     */
    public List<CmnBinfModel> getTmpFileList() {
        return tmpFileList__;
    }
    /**
     * <p>tmpFileList をセットします。
     * @param tmpFileList tmpFileList
     */
    public void setTmpFileList(List<CmnBinfModel> tmpFileList) {
        tmpFileList__ = tmpFileList;
    }
    /**
     * <p>userSid を取得します。
     * @return userSid
     */
    public int getUserSid() {
        return userSid__;
    }
    /**
     * <p>userSid をセットします。
     * @param userSid userSid
     */
    public void setUserSid(int userSid) {
        userSid__ = userSid;
    }
    /**
     * <p>singiKoetu を取得します。
     * @return singiKoetu
     */
    public String getSingiKoetu() {
        return singiKoetu__;
    }
    /**
     * <p>singiKoetu をセットします。
     * @param singiKoetu singiKoetu
     */
    public void setSingiKoetu(String singiKoetu) {
        singiKoetu__ = singiKoetu;
    }
    /**
     * <p>singiTime を取得します。
     * @return singiTime
     */
    public String getSingiTime() {
        return singiTime__;
    }
    /**
     * <p>singiTime をセットします。
     * @param singiTime singiTime
     */
    public void setSingiTime(String singiTime) {
        singiTime__ = singiTime;
    }
    /**
     * <p>singiPosition を取得します。
     * @return singiPosition
     */
    public String getSingiPosition() {
        return singiPosition__;
    }
    /**
     * <p>singiPosition をセットします。
     * @param singiPosition singiPosition
     */
    public void setSingiPosition(String singiPosition) {
        singiPosition__ = singiPosition;
    }
    /**
     * <p>dairiSid を取得します。
     * @return dairiSid
     */
    public int getDairiSid() {
        return dairiSid__;
    }
    /**
     * <p>dairiSid をセットします。
     * @param dairiSid dairiSid
     */
    public void setDairiSid(int dairiSid) {
        dairiSid__ = dairiSid;
    }
    /**
     * <p>userJkbn を取得します。
     * @return userJkbn
     */
    public int getUserJkbn() {
        return userJkbn__;
    }
    /**
     * <p>userJkbn をセットします。
     * @param userJkbn userJkbn
     */
    public void setUserJkbn(int userJkbn) {
        userJkbn__ = userJkbn;
    }
    /**
     * <p>userUkoFlg を取得します。
     * @return userUkoFlg
     */
    public int getUserUkoFlg() {
        return userUkoFlg__;
    }
    /**
     * <p>userUkoFlg をセットします。
     * @param userUkoFlg userUkoFlg
     */
    public void setUserUkoFlg(int userUkoFlg) {
        userUkoFlg__ = userUkoFlg;
    }
}