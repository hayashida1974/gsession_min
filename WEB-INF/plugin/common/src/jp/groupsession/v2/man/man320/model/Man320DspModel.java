package jp.groupsession.v2.man.man320.model;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.base.CmnInfoMsgModel;
import jp.groupsession.v2.man.man320.Man320Biz;

/**
 * <br>[機  能] インフォメーション情報を格納するModelクラスです。
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man320DspModel extends CmnInfoMsgModel {

    /** 登録者ユーザ姓 */
    private String usrNameSei__;
    /** 登録者ユーザ名 */
    private String usrNameMei__;
    /** 登録者/更新者状態区分 */
    private int usrJkbn__;
    /** 開始日時 */
    private String frDate__;
    /** 終了日時 */
    private String toDate__;
    /** 拡張設定 */
    private String exString__;
    /** 公開対象 */
    private ArrayList<String> targetNameList__;
    /** インフォメーション期限チェックフラグ */
    private int kigenChFlg__ = Man320Biz.ALERT_FLG_KIGEN_OK;

    /**
     * <p>kigenChFlg を取得します。
     * @return kigenChFlg
     */
    public int getKigenChFlg() {
        return kigenChFlg__;
    }
    /**
     * <p>kigenChFlg をセットします。
     * @param kigenChFlg kigenChFlg
     */
    public void setKigenChFlg(int kigenChFlg) {
        kigenChFlg__ = kigenChFlg;
    }
    /**
     * @return the usrJkbn
     */
    public int getUsrJkbn() {
        return usrJkbn__;
    }
    /**
     * @param usrJkbn the usrJkbn to set
     */
    public void setUsrJkbn(int usrJkbn) {
        usrJkbn__ = usrJkbn;
    }
    /**
     * @return the targetNameList
     */
    public ArrayList<String> getTargetNameList() {
        return targetNameList__;
    }
    /**
     * @param targetNameList the targetNameList to set
     */
    public void setTargetNameList(ArrayList<String> targetNameList) {
        targetNameList__ = targetNameList;
    }
    /**
     * @return the usrNameSei
     */
    public String getUsrNameSei() {
        return usrNameSei__;
    }
    /**
     * @param usrNameSei the usrNameSei to set
     */
    public void setUsrNameSei(String usrNameSei) {
        usrNameSei__ = usrNameSei;
    }
    /**
     * @return the usrNameMei
     */
    public String getUsrNameMei() {
        return usrNameMei__;
    }
    /**
     * @param usrNameMei the usrNameMei to set
     */
    public void setUsrNameMei(String usrNameMei) {
        usrNameMei__ = usrNameMei;
    }
    /**
     * @return the frDate
     */
    public String getFrDate() {
        return frDate__;
    }
    /**
     * @param frDate the frDate to set
     */
    public void setFrDate(String frDate) {
        frDate__ = frDate;
    }
    /**
     * @return the toDate
     */
    public String getToDate() {
        return toDate__;
    }
    /**
     * @param toDate the toDate to set
     */
    public void setToDate(String toDate) {
        toDate__ = toDate;
    }
    /**
     * @return the exString
     */
    public String getExString() {
        return exString__;
    }
    /**
     * @param exString the exString to set
     */
    public void setExString(String exString) {
        exString__ = exString;
    }

 }
