package jp.groupsession.v2.wml.model.mail;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] WEBメール 送信先情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlMailSendAddrModel extends AbstractModel {

    /** TO */
    private List<String> toList__ = null;
    /** CC */
    private List<String> ccList__ = null;
    /** BCC */
    private List<String> bccList__ = null;
    /** 連番値*/
    private List<Integer> renbanList__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public WmlMailSendAddrModel() {
        toList__ = new ArrayList<String>();
        ccList__ = new ArrayList<String>();
        bccList__ = new ArrayList<String>();
        renbanList__ = new ArrayList<Integer>();
    }

    /**
     * <p>bccList を取得します。
     * @return bccList
     */
    public List<String> getBccList() {
        return bccList__;
    }

    /**
     * <p>bccList をセットします。
     * @param bccList bccList
     */
    public void setBccList(List<String> bccList) {
        bccList__ = bccList;
    }

    /**
     * <p>ccList を取得します。
     * @return ccList
     */
    public List<String> getCcList() {
        return ccList__;
    }

    /**
     * <p>ccList をセットします。
     * @param ccList ccList
     */
    public void setCcList(List<String> ccList) {
        ccList__ = ccList;
    }

    /**
     * <p>toList を取得します。
     * @return toList
     */
    public List<String> getToList() {
        return toList__;
    }

    /**
     * <p>toList をセットします。
     * @param toList toList
     */
    public void setToList(List<String> toList) {
        toList__ = toList;
    }

    /**
     * <br>[機  能] TOメールアドレスを追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     */
    public void addToAddress(String address) {
        toList__.add(address);
    }
    /**
     * <br>[機  能] CCメールアドレスを追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     */
    public void addCcAddress(String address) {
        ccList__.add(address);
    }
    /**
     * <br>[機  能] BCCメールアドレスを追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     */
    public void addBccAddress(String address) {
        bccList__.add(address);
    }

    /**
     * <br>[機  能] 連番値を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param number 連番値
     */
    public void addRenban(int number) {
        renbanList__.add(number);
    }

    /**
     * <p>renbanList を取得します。
     * @return renbanList
     * @see jp.groupsession.v2.wml.wml010.model.Wml010SendAddrModel#renbanList__
     */
    public List<Integer> getRenbanList() {
        return renbanList__;
    }

    /**
     * <p>renbanList をセットします。
     * @param renbanList renbanList
     * @see jp.groupsession.v2.wml.wml010.model.Wml010SendAddrModel#renbanList__
     */
    public void setRenbanList(List<Integer> renbanList) {
        renbanList__ = renbanList;
    }

}
