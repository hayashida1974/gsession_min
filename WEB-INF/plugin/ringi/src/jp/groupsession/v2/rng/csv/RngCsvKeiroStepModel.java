package jp.groupsession.v2.rng.csv;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RngKeiroStepModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <p>RNG_SINGI Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngCsvKeiroStepModel extends RngKeiroStepModel {

    /** 経路タイプ */
    private int rkeType__ = -1;

    /** ユーザSID */
    private int usrSid__ = -1;

    /** グループSID */
    private int grpSid__ = -1;

    /** 役職SID */
    private int posSid__ = -1;

    /** 後閲ユーザSID */
    private int kouetuUsrSid__;

    /** 代理人ユーザSID */
    private int dairiUsrSid__;

    /** 経路 ユーザ名 */
    private String rksName__;

    /** ソートIndex */
    private int sort__ = -1;

    /**
     * <p>Default Constructor
     */
    public RngCsvKeiroStepModel() {
    }

    /**
     * <p>rksType を取得します。
     * @return rksType
     */
    public int getRksType() {
        return rkeType__;
    }
    /**
     * <p>rksType をセットします。
     * @param rksType rksType
     */
    public void setRksType(int rksType) {
        rkeType__ = rksType;
    }

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }
    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>grpSid を取得します。
     * @return grpSid
     */
    public int getGrpSid() {
        return grpSid__;
    }
    /**
     * <p>grpSid をセットします。
     * @param grpSid grpSid
     */
    public void setGrpSid(int grpSid) {
        grpSid__ = grpSid;
    }

    /**
     * <p>rksType を取得します。
     * @return rksType
     */
    public int getPosSid() {
        return posSid__;
    }
    /**
     * <p>posSid をセットします。
     * @param posSid posSid
     */
    public void setPosSid(int posSid) {
        posSid__ = posSid;
    }

    /**
     * <p>kouetuUsrSid を取得します。
     * @return kouetuUsrSid
     */
    public int getKouetuUsrSid() {
        return kouetuUsrSid__;
    }
    /**
     * <p>kouetuUsrSid をセットします。
     * @param kouetuUsrSid kouetuUsrSid
     */
    public void setKouetuUsrSid(int kouetuUsrSid) {
        kouetuUsrSid__ = kouetuUsrSid;
    }

    /**
     * <p>dairiUsrSid を取得します。
     * @return dairiUsrSid
     */
    public int getDairiUsrSid() {
        return dairiUsrSid__;
    }
    /**
     * <p>dairiUsrSid をセットします。
     * @param dairiUsrSid dairiUsrSid
     */
    public void setDairiUsrSid(int dairiUsrSid) {
        dairiUsrSid__ = dairiUsrSid;
    }

    /**
     * <p>rksName を取得します。
     * @return rksName
     */
    public String getRksName() {
        return rksName__;
    }
    /**
     * <p>rksName をセットします。
     * @param rksName rksName
     */
    public void setRksName(String rksName) {
        rksName__ = rksName;
    }

    /**
     * <p>sort を取得します。
     * @return sort
     */
    public int getSort() {
        return sort__;
    }

    /**
     * <p>sort をセットします。
     * @param sort sort
     */
    public void setSort(int sort) {
        sort__ = sort;
    }

    /**
     * 経路ステータスから状態名を取得する
     *
     * @param gsMsg GSメッセージ
     * @param isComp 稟議完了済みフラグ
     * @return 経路状態名を取得
     */
    public String getStatusTitle(GsMessage gsMsg, boolean isComp) {
        String ret = "";
        if (this.getRksRollType() == RngConst.RNG_RNCTYPE_APPL) {
            // 申請者
            switch (this.getRksStatus()) {
                case RngConst.RNG_RNCSTATUS_NOSET: // 未設定
                    ret = gsMsg.getMessage("rng.47"); //"申請者";
                    break;
                case RngConst.RNG_RNCSTATUS_DENIAL: // 否認
                    ret = gsMsg.getMessage("rng.rng030.15"); //"取り下げ";
                    break;
                case RngConst.RNG_RNCSTATUS_REAPPLY: // 再申請
                    ret = gsMsg.getMessage("rng.rng030.09"); //"再申請";
                    break;
                default:
                    break;
            }
        } else if (this.getRksRollType() == RngConst.RNG_RNCTYPE_APPR) {
            // 承認者
            switch (this.getRksStatus()) {
                case RngConst.RNG_RNCSTATUS_CONFIRM: // 確認中
                    ret = gsMsg.getMessage("rng.rng030.14"); //"確認中";
                    break;
                case RngConst.RNG_RNCSTATUS_APPR: // 承認
                    ret = gsMsg.getMessage("rng.41"); //"承認";
                    break;
                case RngConst.RNG_RNCSTATUS_DENIAL: // 否認
                    ret = gsMsg.getMessage("rng.rng030.12"); //"否認";
                    break;
                case RngConst.RNG_RNCSTATUS_KOETU: // 後閲
                    ret = gsMsg.getMessage("rng.41")
                        + "(" + gsMsg.getMessage("rng.109") + ")"; // 承認(後閲)
                    break;
                case RngConst.RNG_RNCSTATUS_SKIP:  // スキップ
                    ret = gsMsg.getMessage("rng.rng030.03"); // スキップ
                    break;
                case RngConst.RNG_RNCSTATUS_NOSET: // 未設定
                default:
                    break;
            }
        } else if (this.getRksRollType() == RngConst.RNG_RNCTYPE_CONFIRM) {
            if (isComp) {
                // 最終確認者(完了済みの場合のみ)
                switch (this.getRksStatus()) {
                    case RngConst.RNG_RNCSTATUS_CONFIRM: // 確認中
                    case RngConst.RNG_RNCSTATUS_NOSET:   // 未設定
                        ret = gsMsg.getMessage("rng.110"); //"最終確認中";
                        break;
                    case RngConst.RNG_RNCSTATUS_CONFIRMATION: // 確認
                        ret = gsMsg.getMessage("rng.111"); //"最終確認済み";
                        break;
                    default:
                        break;
                }
            }
        }

        return ret;
    }
}
