package jp.groupsession.v2.adr.adr111;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr110.Adr110Form;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;

/**
 * <br>[機  能] アドレス帳 拠点登録登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr111Form extends Adr110Form {

    /** 初期表示フラグ */
    int adr111initFlg__ = 0;

    /** 会社拠点種別 */
    int adr111abaType__ = 0;
    /** 支店・営業所名 */
    String adr111abaName__ = null;
    /** 郵便番号上3桁 */
    String adr111abaPostno1__ = null;
    /** 郵便番号下4桁 */
    String adr111abaPostno2__ = null;
    /** 都道府県 */
    int adr111abaTdfk__ = 0;
    /** 住所１ */
    String adr111abaAddress1__ = null;
    /** 住所２ */
    String adr111abaAddress2__ = null;
    /** 備考 */
    String adr111abaBiko__ = null;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();

        //支店・営業所名
        AdrValidateUtil.validateTextField(errors, adr111abaName__, "adr111abaName",
                                        gsMsg.getMessage(req, "address.10"),
                                        GSConstAddress.MAX_LENGTH_COBASE_NAME, true);
        //郵便番号
        AdrValidateUtil.validatePostNum(errors, adr111abaPostno1__, adr111abaPostno2__, req);
        //住所１
        AdrValidateUtil.validateTextField(errors, adr111abaAddress1__, "adr111abaAddress1",
                                        gsMsg.getMessage(req, "cmn.address") + "１",
                                        GSConstAddress.MAX_LENGTH_ADDRESS, false);
        //住所２
        AdrValidateUtil.validateTextField(errors, adr111abaAddress2__, "adr111abaAddress2",
                                        gsMsg.getMessage(req, "cmn.address") + "２",
                                        GSConstAddress.MAX_LENGTH_ADDRESS, false);
        //備考
        AdrValidateUtil.validateTextAreaField(errors, adr111abaBiko__, "adr111abaBiko__",
                                            gsMsg.getMessage(req, "cmn.memo"),
                                            GSConstAddress.MAX_LENGTH_ADR_BIKO, false);

        return errors;
    }

    /**
     * <p>adr111initFlg を取得します。
     * @return adr111initFlg
     */
    public int getAdr111initFlg() {
        return adr111initFlg__;
    }

    /**
     * <p>adr111initFlg をセットします。
     * @param adr111initFlg adr111initFlg
     */
    public void setAdr111initFlg(int adr111initFlg) {
        adr111initFlg__ = adr111initFlg;
    }

    /**
     * <p>adr111abaAddress1 を取得します。
     * @return adr111abaAddress1
     */
    public String getAdr111abaAddress1() {
        return adr111abaAddress1__;
    }
    /**
     * <p>adr111abaAddress1 をセットします。
     * @param adr111abaAddress1 adr111abaAddress1
     */
    public void setAdr111abaAddress1(String adr111abaAddress1) {
        adr111abaAddress1__ = adr111abaAddress1;
    }
    /**
     * <p>adr111abaAddress2 を取得します。
     * @return adr111abaAddress2
     */
    public String getAdr111abaAddress2() {
        return adr111abaAddress2__;
    }
    /**
     * <p>adr111abaAddress2 をセットします。
     * @param adr111abaAddress2 adr111abaAddress2
     */
    public void setAdr111abaAddress2(String adr111abaAddress2) {
        adr111abaAddress2__ = adr111abaAddress2;
    }
    /**
     * <p>adr111abaBiko を取得します。
     * @return adr111abaBiko
     */
    public String getAdr111abaBiko() {
        return adr111abaBiko__;
    }
    /**
     * <p>adr111abaBiko をセットします。
     * @param adr111abaBiko adr111abaBiko
     */
    public void setAdr111abaBiko(String adr111abaBiko) {
        adr111abaBiko__ = adr111abaBiko;
    }
    /**
     * <p>adr111abaName を取得します。
     * @return adr111abaName
     */
    public String getAdr111abaName() {
        return adr111abaName__;
    }
    /**
     * <p>adr111abaName をセットします。
     * @param adr111abaName adr111abaName
     */
    public void setAdr111abaName(String adr111abaName) {
        adr111abaName__ = adr111abaName;
    }
    /**
     * <p>adr111abaPostno1 を取得します。
     * @return adr111abaPostno1
     */
    public String getAdr111abaPostno1() {
        return adr111abaPostno1__;
    }
    /**
     * <p>adr111abaPostno1 をセットします。
     * @param adr111abaPostno1 adr111abaPostno1
     */
    public void setAdr111abaPostno1(String adr111abaPostno1) {
        adr111abaPostno1__ = adr111abaPostno1;
    }
    /**
     * <p>adr111abaPostno2 を取得します。
     * @return adr111abaPostno2
     */
    public String getAdr111abaPostno2() {
        return adr111abaPostno2__;
    }
    /**
     * <p>adr111abaPostno2 をセットします。
     * @param adr111abaPostno2 adr111abaPostno2
     */
    public void setAdr111abaPostno2(String adr111abaPostno2) {
        adr111abaPostno2__ = adr111abaPostno2;
    }
    /**
     * <p>adr111abaTdfk を取得します。
     * @return adr111abaTdfk
     */
    public int getAdr111abaTdfk() {
        return adr111abaTdfk__;
    }
    /**
     * <p>adr111abaTdfk をセットします。
     * @param adr111abaTdfk adr111abaTdfk
     */
    public void setAdr111abaTdfk(int adr111abaTdfk) {
        adr111abaTdfk__ = adr111abaTdfk;
    }
    /**
     * <p>adr111abaType を取得します。
     * @return adr111abaType
     */
    public int getAdr111abaType() {
        return adr111abaType__;
    }
    /**
     * <p>adr111abaType をセットします。
     * @param adr111abaType adr111abaType
     */
    public void setAdr111abaType(int adr111abaType) {
        adr111abaType__ = adr111abaType;
    }

}
