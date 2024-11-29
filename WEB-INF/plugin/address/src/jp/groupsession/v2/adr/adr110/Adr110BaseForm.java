package jp.groupsession.v2.adr.adr110;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.struts.AbstractForm;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 会社登録画面の拠点情報を保持するフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr110BaseForm extends AbstractForm {
    /** 会社拠点情報 index */
    int adr110abaIndex__ = 0;
    /** 会社拠点 会社拠点SID(一覧) */
    int adr110abaSidDetail__ = 0;
    /** 会社拠点 種別(一覧) */
    int adr110abaTypeDetail__ = 0;
    /** 会社拠点 支店・営業所名(一覧) */
    String adr110abaName__ = null;
    /** 会社拠点 郵便番号上3桁(一覧) */
    String adr110abaPostno1__ = null;
    /** 会社拠点 郵便番号下4桁(一覧) */
    String adr110abaPostno2__ = null;
    /** 会社拠点 都道府県(一覧) */
    int adr110abaTdfk__ = 0;
    /** 会社拠点 都道府県名称(一覧) */
    String adr110abaTdfkName__ = null;
    /** 会社拠点 住所１(一覧) */
    String adr110abaAddress1__ = null;
    /** 会社拠点 住所２(一覧) */
    String adr110abaAddress2__ = null;
    /** 会社拠点 備考(一覧) */
    String adr110abaBiko__ = null;

    /** 会社拠点 地図検索ワード */
    String adr110abaWebSearchWord__ = "";


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

        //会社拠点 支店・営業所名(一覧)
        AdrValidateUtil.validateTextField(errors, adr110abaName__, "adr110abaName",
                gsMsg.getMessage(req, "address.10"), GSConstAddress.MAX_LENGTH_COBASE_NAME, true);
        //会社拠点 郵便番号
        AdrValidateUtil.validatePostNum(errors, adr110abaPostno1__, adr110abaPostno2__, req);
        //会社拠点 住所１(一覧)
        AdrValidateUtil.validateTextField(errors, adr110abaAddress1__, "adr110abaAddress1",
                gsMsg.getMessage(req, "cmn.address") + "１",
                GSConstAddress.MAX_LENGTH_ADDRESS, false);
        //会社拠点 住所２(一覧)
        AdrValidateUtil.validateTextField(errors, adr110abaAddress2__, "adr110abaAddress2",
                gsMsg.getMessage(req, "cmn.address") + "２",
                GSConstAddress.MAX_LENGTH_ADDRESS, false);
        //会社拠点 備考(一覧)
        AdrValidateUtil.validateTextAreaField(errors, adr110abaBiko__, "adr110abaBiko__",
                gsMsg.getMessage(req, "cmn.memo"), GSConstAddress.MAX_LENGTH_ADR_BIKO, false);
        //会社拠点 種別(一覧)
        ArrayList<LabelValueBean> abaTypeList = new ArrayList<LabelValueBean>();
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("address.122"),
                                        String.valueOf(GSConstAddress.ABATYPE_HEADOFFICE)));
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("address.123"),
                                        String.valueOf(GSConstAddress.ABATYPE_BRANCH)));
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("address.124"),
                                        String.valueOf(GSConstAddress.ABATYPE_BUSINESSOFFICE)));
        //LabelValueBean型のリストをvalueのみのリストに型変換
        List<String> typeValList = abaTypeList.stream()
                .map(value -> value.getValue()).collect(Collectors.toList());

        if (!typeValList.contains(String.valueOf(adr110abaTypeDetail__))) {
            ActionMessage msg = null;
            String strTdfk = gsMsg.getMessage("address.src.9");
            msg = new ActionMessage("error.input.notvalidate.data", strTdfk);
            errors.add("error.input.notvalidate.data", msg);;
        }
        //会社拠点 都道府県(一覧)
        CommonBiz cmnBiz = new CommonBiz();
        ArrayList<LabelValueBean> tdfkList = cmnBiz.getTdfkLabelList(con, gsMsg);
        //LabelValueBean型のリストをvalueのみのリストに型変換
        List<String> tdfkValList = tdfkList.stream()
                .map(value -> value.getValue()).collect(Collectors.toList());

        if (!tdfkValList.contains(String.valueOf(adr110abaTdfk__))) {
            ActionMessage msg = null;
            String strTdfk = gsMsg.getMessage("cmn.prefectures");
            msg = new ActionMessage("error.input.notvalidate.data", strTdfk);
            errors.add("error.input.notvalidate.data", msg);;
        }
        return errors;
    }

    /**
     * <p>adr110abaIndex を取得します。
     * @return adr110abaIndex
     */
    public int getAdr110abaIndex() {
        return adr110abaIndex__;
    }

    /**
     * <p>adr110abaIndex をセットします。
     * @param adr110abaIndex adr110abaIndex
     */
    public void setAdr110abaIndex(int adr110abaIndex) {
        adr110abaIndex__ = adr110abaIndex;
    }

    /**
     * <p>adr110abaAddress1 を取得します。
     * @return adr110abaAddress1
     */
    public String getAdr110abaAddress1() {
        return adr110abaAddress1__;
    }

    /**
     * <p>adr110abaAddress1 をセットします。
     * @param adr110abaAddress1 adr110abaAddress1
     */
    public void setAdr110abaAddress1(String adr110abaAddress1) {
        adr110abaAddress1__ = adr110abaAddress1;
    }

    /**
     * <p>adr110abaAddress2 を取得します。
     * @return adr110abaAddress2
     */
    public String getAdr110abaAddress2() {
        return adr110abaAddress2__;
    }

    /**
     * <p>adr110abaAddress2 をセットします。
     * @param adr110abaAddress2 adr110abaAddress2
     */
    public void setAdr110abaAddress2(String adr110abaAddress2) {
        adr110abaAddress2__ = adr110abaAddress2;
    }

    /**
     * <p>adr110abaBiko を取得します。
     * @return adr110abaBiko
     */
    public String getAdr110abaBiko() {
        return adr110abaBiko__;
    }

    /**
     * <p>adr110abaBiko をセットします。
     * @param adr110abaBiko adr110abaBiko
     */
    public void setAdr110abaBiko(String adr110abaBiko) {
        adr110abaBiko__ = adr110abaBiko;
    }

    /**
     * <p>adr110abaName を取得します。
     * @return adr110abaName
     */
    public String getAdr110abaName() {
        return adr110abaName__;
    }

    /**
     * <p>adr110abaName をセットします。
     * @param adr110abaName adr110abaName
     */
    public void setAdr110abaName(String adr110abaName) {
        adr110abaName__ = adr110abaName;
    }

    /**
     * <p>adr110abaPostno1 を取得します。
     * @return adr110abaPostno1
     */
    public String getAdr110abaPostno1() {
        return adr110abaPostno1__;
    }

    /**
     * <p>adr110abaPostno1 をセットします。
     * @param adr110abaPostno1 adr110abaPostno1
     */
    public void setAdr110abaPostno1(String adr110abaPostno1) {
        adr110abaPostno1__ = adr110abaPostno1;
    }

    /**
     * <p>adr110abaPostno2 を取得します。
     * @return adr110abaPostno2
     */
    public String getAdr110abaPostno2() {
        return adr110abaPostno2__;
    }

    /**
     * <p>adr110abaPostno2 をセットします。
     * @param adr110abaPostno2 adr110abaPostno2
     */
    public void setAdr110abaPostno2(String adr110abaPostno2) {
        adr110abaPostno2__ = adr110abaPostno2;
    }

    /**
     * <p>adr110abaSidDetail を取得します。
     * @return adr110abaSidDetail
     */
    public int getAdr110abaSidDetail() {
        return adr110abaSidDetail__;
    }

    /**
     * <p>adr110abaSidDetail をセットします。
     * @param adr110abaSidDetail adr110abaSidDetail
     */
    public void setAdr110abaSidDetail(int adr110abaSidDetail) {
        adr110abaSidDetail__ = adr110abaSidDetail;
    }

    /**
     * <p>adr110abaTdfk を取得します。
     * @return adr110abaTdfk
     */
    public int getAdr110abaTdfk() {
        return adr110abaTdfk__;
    }

    /**
     * <p>adr110abaTdfk をセットします。
     * @param adr110abaTdfk adr110abaTdfk
     */
    public void setAdr110abaTdfk(int adr110abaTdfk) {
        adr110abaTdfk__ = adr110abaTdfk;
    }

    /**
     * <p>adr110abaTypeDetail を取得します。
     * @return adr110abaTypeDetail
     */
    public int getAdr110abaTypeDetail() {
        return adr110abaTypeDetail__;
    }

    /**
     * <p>adr110abaTypeDetail をセットします。
     * @param adr110abaTypeDetail adr110abaTypeDetail
     */
    public void setAdr110abaTypeDetail(int adr110abaTypeDetail) {
        adr110abaTypeDetail__ = adr110abaTypeDetail;
    }

    /**
     * <p>adr110abaTdfkName を取得します。
     * @return adr110abaTdfkName
     */
    public String getAdr110abaTdfkName() {
        return adr110abaTdfkName__;
    }

    /**
     * <p>adr110abaTdfkName をセットします。
     * @param adr110abaTdfkName adr110abaTdfkName
     */
    public void setAdr110abaTdfkName(String adr110abaTdfkName) {
        adr110abaTdfkName__ = adr110abaTdfkName;
    }

    /**
     * <br>[機  能] 会社拠点 種別の名称を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 会社拠点種別名称
     */
    public String getAdr110abaTypeNameDetail() {
        String abaTypeName = null;

        switch (adr110abaTypeDetail__) {
            case GSConstAddress.ABATYPE_HEADOFFICE :
                //本社
                abaTypeName = "address.122";
                break;
            case GSConstAddress.ABATYPE_BRANCH :
                //支店
                abaTypeName = "address.123";
                break;
            case GSConstAddress.ABATYPE_BUSINESSOFFICE :
                //営業所
                abaTypeName = "address.124";
                break;
            default :
        }

        return abaTypeName;
    }

    /**
     * <br>[機  能] 会社拠点 備考(一覧 画面表示用)を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 備考(一覧 画面表示用)
     */
    public String getAdr110abaViewBiko() {
        return StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(adr110abaBiko__, ""));
    }

    /**
     * <p>adr110abaWebSearchWord を取得します。
     * @return adr110abaWebSearchWord
     */
    public String getAdr110abaWebSearchWord() {
        return adr110abaWebSearchWord__;
    }

    /**
     * <p>adr110abaWebSearchWord をセットします。
     * @param adr110abaWebSearchWord adr110abaWebSearchWord
     */
    public void setAdr110abaWebSearchWord(String adr110abaWebSearchWord) {
        adr110abaWebSearchWord__ = adr110abaWebSearchWord;
    }
}
