package jp.groupsession.v2.sml.model;

import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <p>CMN_USRM_INF Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class SmailUsrModel extends CmnUsrmInfModel {

    /** アカウントSID */
    private String sacSid__ = null;
    /** アカウント名 */
    private String sacName__ = null;
    /** アカウント状態区分 */
    private int sacJkbn__;
    /**
     * <p>sacSid を取得します。
     * @return sacSid
     */
    public String getSacSid() {
        return sacSid__;
    }
    /**
     * <p>sacSid をセットします。
     * @param sacSid sacSid
     */
    public void setSacSid(String sacSid) {
        sacSid__ = sacSid;
    }
    /**
     * <p>sacName を取得します。
     * @return sacName
     */
    public String getSacName() {
        return sacName__;
    }
    /**
     * <p>sacName をセットします。
     * @param sacName sacName
     */
    public void setSacName(String sacName) {
        sacName__ = sacName;
    }
    /**
     * <p>sacJkbn を取得します。
     * @return sacJkbn
     */
    public int getSacJkbn() {
        return sacJkbn__;
    }
    /**
     * <p>sacJkbn をセットします。
     * @param sacJkbn sacJkbn
     */
    public void setSacJkbn(int sacJkbn) {
        sacJkbn__ = sacJkbn;
    }



}