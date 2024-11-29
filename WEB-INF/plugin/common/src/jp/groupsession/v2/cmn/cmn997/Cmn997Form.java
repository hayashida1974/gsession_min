package jp.groupsession.v2.cmn.cmn997;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;

/**
 * <br>[機  能] ライセンス情報の再読み込み画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 */
public class Cmn997Form extends Cmn999Form {

    /** domain */
    public String domain__;
    /** changeKbn 0:追加  1:削除 */
    public int changeKbn__ = 0;
    /** cmn997ValueGsAuthParam */
    public String cmn997ValueGsAuthParam__;

    /**
     * <p>domain を取得します。
     * @return domain
     */
    public String getDomain() {
        return domain__;
    }

    /**
     * <p>domain をセットします。
     * @param domain domain
     */
    public void setDomain(String domain) {
        domain__ = domain;
    }
    /**
     * <p>changeKbn を取得します。
     * @return changeKbn
     */
    public int getChangeKbn() {
        return changeKbn__;
    }

    /**
     * <p>changeKbn をセットします。
     * @param changeKbn changeKbn
     */
    public void setChangeKbn(int changeKbn) {
        changeKbn__ = changeKbn;
    }

    /**
     * <p>cmn997ValueGsAuthParam を取得します。
     * @return cmn997ValueGsAuthParam
     */
    public String getCmn997ValueGsAuthParam() {
        return cmn997ValueGsAuthParam__;
    }

    /**
     * <p>cmn997ValueGsAuthParam をセットします。
     * @param cmn997ValueGsAuthParam cmn997ValueGsAuthParam
     */
    public void setCmn997ValueGsAuthParam(String cmn997ValueGsAuthParam) {
        cmn997ValueGsAuthParam__ = cmn997ValueGsAuthParam;
    }
}