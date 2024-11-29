package jp.groupsession.v2.rng.model;

import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.rng.RngConst;


/**
 * <br>[機  能] 稟議情報を格納するのModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RingiDataModel extends RngRndataModel {

    /** 申請者氏名 */
    private String apprUser__ = null;
    /** 受信日 */
    private UDate rcvDate__ = null;
    /** 最終処理日 */
    private UDate lastManageDate__ = null;
    /** 作成日 */
    private UDate makeDate__ = null;
    /** 承認者状態 */
    private int rncStatus__ = RngConst.RNG_RNCSTATUS_NOSET;
    /** 承認者種別 */
    private int rncType__ = RngConst.RNG_RNCTYPE_APPR;
    /** 申請者 削除フラグ */
    private boolean apprUserDelFlg__ = false;
    /** 申請者 ログイン停止フラグ */
    private int usrUkoFlg__ = 0;
    /** ユーザ単位の状態*/
    private int rssStatus__ = RngConst.RNG_RNCSTATUS_NOSET;

    /** 申請日(文字列) */
    private String strRngAppldate__ = null;
    /** 受信日(文字列) */
    private String strRcvDate__ = null;
    /** 最終処理日(文字列) */
    private String strLastManageDate__ = null;
    /** 作成日(文字列) */
    private String strMakeDate__ = null;

    /** 削除可能フラグ */
    private int delFlg__ = 0;
    /** 経路情報一覧 */
    private List<RingiChannelDataModel> channelList__ = null;

    /** 経路情報一覧グループ名*/
    private List<RingiKeiroNameModel> keiroNameList__ = null;

    /**
     * <p>apprUser を取得します。
     * @return apprUser
     */
    public String getApprUser() {
        return apprUser__;
    }
    /**
     * <p>apprUser をセットします。
     * @param apprUser apprUser
     */
    public void setApprUser(String apprUser) {
        apprUser__ = apprUser;
    }
    /**
     * <p>apprUserDelFlg を取得します。
     * @return apprUserDelFlg
     */
    public boolean isApprUserDelFlg() {
        return apprUserDelFlg__;
    }
    /**
     * <p>apprUserDelFlg をセットします。
     * @param apprUserDelFlg apprUserDelFlg
     */
    public void setApprUserDelFlg(boolean apprUserDelFlg) {
        apprUserDelFlg__ = apprUserDelFlg;
    }
    /**
     * <p>lastManageDate を取得します。
     * @return lastManageDate
     */
    public UDate getLastManageDate() {
        return lastManageDate__;
    }
    /**
     * <p>lastManageDate をセットします。
     * @param lastManageDate lastManageDate
     */
    public void setLastManageDate(UDate lastManageDate) {
        lastManageDate__ = lastManageDate;
    }
    /**
     * <p>makeDate を取得します。
     * @return makeDate
     */
    public UDate getMakeDate() {
        return makeDate__;
    }
    /**
     * <p>makeDate をセットします。
     * @param makeDate makeDate
     */
    public void setMakeDate(UDate makeDate) {
        makeDate__ = makeDate;
    }
    /**
     * <p>rcvDate を取得します。
     * @return rcvDate
     */
    public UDate getRcvDate() {
        return rcvDate__;
    }
    /**
     * <p>rcvDate をセットします。
     * @param rcvDate rcvDate
     */
    public void setRcvDate(UDate rcvDate) {
        rcvDate__ = rcvDate;
    }
    /**
     * <p>strRngAppldate を取得します。
     * @return strRngAppldate
     */
    public String getStrRngAppldate() {
        return strRngAppldate__;
    }
    /**
     * <p>strRngAppldate をセットします。
     * @param strRngAppldate strRngAppldate
     */
    public void setStrRngAppldate(String strRngAppldate) {
        strRngAppldate__ = strRngAppldate;
    }
    /**
     * <p>strLastManageDate を取得します。
     * @return strLastManageDate
     */
    public String getStrLastManageDate() {
        return strLastManageDate__;
    }
    /**
     * <p>strLastManageDate をセットします。
     * @param strLastManageDate strLastManageDate
     */
    public void setStrLastManageDate(String strLastManageDate) {
        strLastManageDate__ = strLastManageDate;
    }
    /**
     * <p>strMakeDate を取得します。
     * @return strMakeDate
     */
    public String getStrMakeDate() {
        return strMakeDate__;
    }
    /**
     * <p>strMakeDate をセットします。
     * @param strMakeDate strMakeDate
     */
    public void setStrMakeDate(String strMakeDate) {
        strMakeDate__ = strMakeDate;
    }
    /**
     * <p>strRcvDate を取得します。
     * @return strRcvDate
     */
    public String getStrRcvDate() {
        return strRcvDate__;
    }
    /**
     * <p>strRcvDate をセットします。
     * @param strRcvDate strRcvDate
     */
    public void setStrRcvDate(String strRcvDate) {
        strRcvDate__ = strRcvDate;
    }
    /**
     * <p>rncStatus を取得します。
     * @return rncStatus
     */
    public int getRncStatus() {
        return rncStatus__;
    }
    /**
     * <p>rncStatus をセットします。
     * @param rncStatus rncStatus
     */
    public void setRncStatus(int rncStatus) {
        rncStatus__ = rncStatus;
    }
    /**
     * <p>rncType を取得します。
     * @return rncType
     */
    public int getRncType() {
        return rncType__;
    }
    /**
     * <p>rncType をセットします。
     * @param rncType rncType
     */
    public void setRncType(int rncType) {
        rncType__ = rncType;
    }
    /**
     * <p>channelList を取得します。
     * @return channelList
     */
    public List<RingiChannelDataModel> getChannelList() {
        return channelList__;
    }
    /**
     * <p>channelList をセットします。
     * @param channelList channelList
     */
    public void setChannelList(List<RingiChannelDataModel> channelList) {
        channelList__ = channelList;
    }
    /**
     * <p>delFlg を取得します。
     * @return delFlg
     */
    public int getDelFlg() {
        return delFlg__;
    }
    /**
     * <p>delFlg をセットします。
     * @param delFlg delFlg
     */
    public void setDelFlg(int delFlg) {
        delFlg__ = delFlg;
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
    /**
     * <p>rssStatus を取得します。
     * @return rssStatus
     */
    public int getRssStatus() {
        return rssStatus__;
    }
    /**
     * <p>rssStatus をセットします。
     * @param rssStatus rssStatus
     */
    public void setRssStatus(int rssStatus) {
        rssStatus__ = rssStatus;
    }
    /**
     * <p>keiroNameList を取得します。
     * @return keiroNameList
     */
    public List<RingiKeiroNameModel> getKeiroNameList() {
        return keiroNameList__;
    }
    /**
     * <p>keiroNameList をセットします。
     * @param keiroNameList keiroNameList
     */
    public void setKeiroNameList(List<RingiKeiroNameModel> keiroNameList) {
        keiroNameList__ = keiroNameList;
    }

}
