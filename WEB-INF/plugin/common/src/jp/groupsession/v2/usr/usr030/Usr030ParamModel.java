package jp.groupsession.v2.usr.usr030;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSValidateUser;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 ユーザマネージャー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr030ParamModel extends AbstractParamModel {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr030ParamModel.class);
    /** 検索用カナ */
    private String usr030SearchKana__ = null;
    /** 存在するカナリスト */
    List<KanaLinkModel> usr030ekanas__ = null;
    /** 検索結果(ユーザリスト) */
    List<CmnUsrmInfModel> usr030users__ = null;
    /** 選択されたユーザ */
    private int usr030selectuser__ = -1;
    /** 選択されたユーザ */
    private String[] usr030selectusers__ = null;
    /** CSV出力フラグ */
    private int csvOut__ = 0;
    /** 入力処理モード **/
    private String processMode__ = null;
    /** 画面モード 1:氏名, 2:詳細検索 **/
    private int usr030cmdMode__ = GSConstUser.MODE_NAME;
    /** 検索したかどうかのフラグ */
    private int usr030SearchFlg__ = GSConstUser.SEARCH_MI;
    /** 詳細検索フラグ */
    private boolean usr030DetailSearchFlg__ = false;
    /** 社員/職員番号 */
    private String usr030shainno__ = null;
    /** ユーザID */
    private String usr030userId__ = null;
    /** 詳細検索条件 ログイン停止フラグ */
    private int usr030usrUkoFlg__ = GSConstUser.USER_SEARCH_USRUKOFLG_ALL;
    /** 選択グループSID */
    private int selectgsid__ = -1;
    /** ユーザ名 姓 */
    private String usr030sei__ = null;
    /** ユーザ名 名 */
    private String usr030mei__ = null;
    /** ユーザ名 姓カナ */
    private String usr030seikn__ = null;
    /** ユーザ名 名カナ */
    private String usr030meikn__ = null;
    /** 年齢From */
    private String usr030agefrom__ = null;
    /** 年齢To */
    private String usr030ageto__ = null;
    /** 入社年from */
    private String usr030entranceYearFr__ = null;
    /** 入社月from */
    private String usr030entranceMonthFr__ = null;
    /** 入社日from */
    private String usr030entranceDayFr__ = null;
    /** 入社年to年 */
    private String usr030entranceYearTo__ = null;
    /** 入社月to月 */
    private String usr030entranceMonthTo__ = null;
    /** 入社日to日 */
    private String usr030entranceDayTo__ = null;
    /** 性別 */
    private String usr030seibetu__ = null;
    /** グループラベル */
    private ArrayList<LabelValueBean> grpLabelList__ = null;
    /** 役職 */
    private int usr030yakushoku__ = GSConstCommon.NUM_INIT;
    /** メール*/
    private String usr030mail__ = null;
    /** 都道府県コード*/
    private String usr030tdfkCd__ = null;
    /** 都道府県ラベル */
    private ArrayList<LabelValueBean> tdfkLabelList__ = null;
    /** 役職ラベル */
    private ArrayList<LabelValueBean> posLabelList__ = null;
    /** 性別ラベル */
    private ArrayList<LabelValueBean> seibetuLabelList__ = null;
    /** ユーザID(Save) */
    private String usr030userIdSave__ = null;
    /** 詳細検索条件 ログイン停止フラグ（Save） */
    private int usr030usrUkoFlgSave__ = GSConstUser.USER_SEARCH_USRUKOFLG_ALL;
    /** 社員/職員番号(Save) */
    private String usr030shainnoSave__ = null;
    /** ユーザ名 姓(Save) */
    private String usr030seiSave__ = null;
    /** ユーザ名 名(Save) */
    private String usr030meiSave__ = null;
    /** ユーザ名 姓カナ(Save) */
    private String usr030seiknSave__ = null;
    /** ユーザ名 名カナ(Save) */
    private String usr030meiknSave__ = null;
    /** 年齢From(Save) */
    private String usr030agefromSave__ = null;
    /** 年齢To(Save) */
    private String usr030agetoSave__ = null;
    /** 役職(Save) */
    private int usr030yakushokuSave__ = GSConstCommon.NUM_INIT;
    /** メール(Save) */
    private String usr030mailSave__ = null;
    /** 都道府県コード(Save) */
    private String usr030tdfkCdSave__ = null;
    /** 選択グループSID(Save) */
    private int selectgsidSave__ = -1;
    /** 入社年from(Save) */
    private String usr030entranceYearFrSave__ = null;
    /** 入社月from(Save) */
    private String usr030entranceMonthFrSave__ = null;
    /** 入社日from(Save) */
    private String usr030entranceDayFrSave__ = null;
    /** 入社年to年(Save) */
    private String usr030entranceYearToSave__ = null;
    /** 入社月to月(Save) */
    private String usr030entranceMonthToSave__ = null;
    /** 入社日to日(Save) */
    private String usr030entranceDayToSave__ = null;
    /** 性別(Save) */
    private String usr030seibetuSave__ = null;
    /** 検索結果に該当するユーザ */
    private List<BaseUserModel> usr030detailusers__ = null;

    /** システムユーザを検索結果に含まない */
    private boolean excludeSysUser__ = true;

    /** 入社年月日from年ラベル */
    private List<LabelValueBean> usr030entranceYearFrLabel__;
    /** 入社年月日from月ラベル */
    private List<LabelValueBean> usr030entranceMonthFrLabel__;
    /** 入社年月日from日ラベル */
    private List<LabelValueBean> usr030entranceDayFrLabel__;

    /**
     * <br>[機  能] 削除時のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateDelete(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        String eprefix = "sltgp.";
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //ユーザ
        String textUser = gsMsg.getMessage(req, "cmn.user");
        //システムユーザ
        String textSystemUser = gsMsg.getMessage(req, "user.src.61");

        if (usr030selectusers__ == null || usr030selectusers__.length < 1) {
            msg = new ActionMessage("error.select.required.text", textUser);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");
            return errors;
        }

        for (String usrSid : usr030selectusers__) {
            int usrSidNum = NullDefault.getInt(usrSid, GSConstUser.USER_RESERV_SID);

            if (usrSidNum < GSConstUser.USER_RESERV_SID) {
                msg = new ActionMessage("error.common.no.delete", textSystemUser);
                StrutsUtil.addMessage(errors, msg, eprefix + "error.common.no.delete");
                return errors;
            }

        }

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う(詳細検索)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl RequestModel
     * @param con DBコネクション
     * @return エラー
     * @throws Exception 実行例外
     */
    public ActionErrors validateSearchSyosai(ActionMapping map, RequestModel reqMdl,
            Connection con) throws Exception {
        ActionErrors errors = new ActionErrors();
        GSValidateUser gsValidateUser = new GSValidateUser(reqMdl);
        //ユーザＩＤ
        gsValidateUser.validateUserSearchId(errors, usr030userId__);
        //社員/職員番号
        gsValidateUser.validateSearchShainNo(errors, usr030shainno__);
        //氏名 姓
        GSValidateUser.validateSearchUserNameSei(errors, usr030sei__, reqMdl);
        //氏名 名
        GSValidateUser.validateSearchUserNameMei(errors, usr030mei__, reqMdl);
        //氏名カナ セイ
        GSValidateUser.validateSearchUserNameSeiKana(errors, usr030seikn__, reqMdl);
        //氏名カナ メイ
        GSValidateUser.validateSearchUserNameMeiKana(errors, usr030meikn__, reqMdl);

        //年齢FROM通過前のエラーサイズ
        int ageFrBeforErrorSize = errors.size();

        //年齢 FROM
        gsValidateUser.validateSearchAgeFrom(errors, usr030agefrom__);
        //年齢 TO
        gsValidateUser.validateSearchAgeTo(errors, usr030ageto__);

        //年齢TO通過後のエラーサイズ
        int ageToAfterErrorSize = errors.size();

        if (ageFrBeforErrorSize == ageToAfterErrorSize) {
            log__.debug("年齢From、Toにエラー無し");
            gsValidateUser.validateSearchAgeRange(errors, usr030agefrom__, usr030ageto__);
        }

        //所属グループ
        GSValidateUser.validateSelectGroup(errors, selectgsid__, con, reqMdl);
        //役職
        gsValidateUser.validateSearchYakushoku(errors, usr030yakushoku__, con);
        //E-MAIL
        gsValidateUser.validateMail(errors, usr030mail__, 4);
        //都道府県
        gsValidateUser.validateTdfk(errors, usr030tdfkCd__, con);
        return errors;
    }

    /**
     * <br>[機  能] 修正時のチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateEdit(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();
        String eprefix = "sltgp.";
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        //ユーザ
        String textUser = gsMsg.getMessage(req, "cmn.user");
        //修正
        String textFixed = gsMsg.getMessage(req, "cmn.fixed");
        if (usr030selectusers__ == null || usr030selectusers__.length < 1) {
            //未選択
            msg = new ActionMessage("error.select.required.text", textUser);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");

        } else if (usr030selectusers__.length > 1) {
            //複数選択
            msg = new ActionMessage("error.plurals.select", textUser, textFixed);
            StrutsUtil.addMessage(errors, msg, eprefix + "error.select.required.text");

        }


        return errors;
    }

    /**
     * @return processMode を戻します。
     */
    public String getProcessMode() {
        return processMode__;
    }

    /**
     * @param processMode 設定する processMode。
     */
    public void setProcessMode(String processMode) {
        processMode__ = processMode;
    }

    /**
     * @return csvOut を戻します。
     */
    public int getCsvOut() {
        return csvOut__;
    }

    /**
     * @param csvOut 設定する csvOut。
     */
    public void setCsvOut(int csvOut) {
        csvOut__ = csvOut;
    }

    /**
     * @return usr030SearchKana を戻します。
     */
    public String getUsr030SearchKana() {
        return usr030SearchKana__;
    }

    /**
     * @param usr030SearchKana 設定する usr030SearchKana。
     */
    public void setUsr030SearchKana(String usr030SearchKana) {
        usr030SearchKana__ = usr030SearchKana;
    }

    /**
     * @return usr030ekanas を戻します。
     */
    public List<KanaLinkModel> getUsr030ekanas() {
        return usr030ekanas__;
    }

    /**
     * @param usr030ekanas 設定する usr030ekanas。
     */
    public void setUsr030ekanas(List<KanaLinkModel> usr030ekanas) {
        usr030ekanas__ = usr030ekanas;
    }

    /**
     * @return usr030users を戻します。
     */
    public List<CmnUsrmInfModel> getUsr030users() {
        return usr030users__;
    }

    /**
     * @param usr030users 設定する usr030users。
     */
    public void setUsr030users(List<CmnUsrmInfModel> usr030users) {
        usr030users__ = usr030users;
    }

    /**
     * @return usr030cmdMode
     */
    public int getUsr030cmdMode() {
        return usr030cmdMode__;
    }

    /**
     * @param usr030cmdMode 設定する usr030cmdMode
     */
    public void setUsr030cmdMode(int usr030cmdMode) {
        usr030cmdMode__ = usr030cmdMode;
    }

    /**
     * @return usr030SearchFlg
     */
    public int getUsr030SearchFlg() {
        return usr030SearchFlg__;
    }

    /**
     * @param usr030SearchFlg 設定する usr030SearchFlg
     */
    public void setUsr030SearchFlg(int usr030SearchFlg) {
        usr030SearchFlg__ = usr030SearchFlg;
    }

    /**
     * @return grpLabelList
     */
    public ArrayList<LabelValueBean> getGrpLabelList() {
        return grpLabelList__;
    }

    /**
     * @param grpLabelList 設定する grpLabelList
     */
    public void setGrpLabelList(ArrayList<LabelValueBean> grpLabelList) {
        grpLabelList__ = grpLabelList;
    }

    /**
     * @return posLabelList
     */
    public ArrayList<LabelValueBean> getPosLabelList() {
        return posLabelList__;
    }

    /**
     * @param posLabelList 設定する posLabelList
     */
    public void setPosLabelList(ArrayList<LabelValueBean> posLabelList) {
        posLabelList__ = posLabelList;
    }

    /**
     * @return tdfkLabelList
     */
    public ArrayList<LabelValueBean> getTdfkLabelList() {
        return tdfkLabelList__;
    }

    /**
     * @param tdfkLabelList 設定する tdfkLabelList
     */
    public void setTdfkLabelList(ArrayList<LabelValueBean> tdfkLabelList) {
        tdfkLabelList__ = tdfkLabelList;
    }

    /**
     * @return usr030agefrom
     */
    public String getUsr030agefrom() {
        return usr030agefrom__;
    }

    /**
     * @param usr030agefrom 設定する usr030agefrom
     */
    public void setUsr030agefrom(String usr030agefrom) {
        usr030agefrom__ = usr030agefrom;
    }

    /**
     * @return usr030ageto
     */
    public String getUsr030ageto() {
        return usr030ageto__;
    }

    /**
     * @param usr030ageto 設定する usr030ageto
     */
    public void setUsr030ageto(String usr030ageto) {
        usr030ageto__ = usr030ageto;
    }

    /**
     * @return usr030mail
     */
    public String getUsr030mail() {
        return usr030mail__;
    }

    /**
     * @param usr030mail 設定する usr030mail
     */
    public void setUsr030mail(String usr030mail) {
        usr030mail__ = usr030mail;
    }

    /**
     * @return usr030mei
     */
    public String getUsr030mei() {
        return usr030mei__;
    }

    /**
     * @param usr030mei 設定する usr030mei
     */
    public void setUsr030mei(String usr030mei) {
        usr030mei__ = usr030mei;
    }

    /**
     * @return usr030meikn
     */
    public String getUsr030meikn() {
        return usr030meikn__;
    }

    /**
     * @param usr030meikn 設定する usr030meikn
     */
    public void setUsr030meikn(String usr030meikn) {
        usr030meikn__ = usr030meikn;
    }

    /**
     * @return usr030sei
     */
    public String getUsr030sei() {
        return usr030sei__;
    }

    /**
     * @param usr030sei 設定する usr030sei
     */
    public void setUsr030sei(String usr030sei) {
        usr030sei__ = usr030sei;
    }

    /**
     * @return usr030seikn
     */
    public String getUsr030seikn() {
        return usr030seikn__;
    }

    /**
     * @param usr030seikn 設定する usr030seikn
     */
    public void setUsr030seikn(String usr030seikn) {
        usr030seikn__ = usr030seikn;
    }

    /**
     * @return usr030shainno
     */
    public String getUsr030shainno() {
        return usr030shainno__;
    }

    /**
     * @param usr030shainno 設定する usr030shainno
     */
    public void setUsr030shainno(String usr030shainno) {
        usr030shainno__ = usr030shainno;
    }

    /**
     * @return usr030tdfkCd
     */
    public String getUsr030tdfkCd() {
        return usr030tdfkCd__;
    }

    /**
     * @param usr030tdfkCd 設定する usr030tdfkCd
     */
    public void setUsr030tdfkCd(String usr030tdfkCd) {
        usr030tdfkCd__ = usr030tdfkCd;
    }

    /**
     * @return usr030yakushoku
     */
    public int getUsr030yakushoku() {
        return usr030yakushoku__;
    }

    /**
     * @param usr030yakushoku 設定する usr030yakushoku
     */
    public void setUsr030yakushoku(int usr030yakushoku) {
        usr030yakushoku__ = usr030yakushoku;
    }

    /**
     * <p>usr030agefromSave を取得します。
     * @return usr030agefromSave
     */
    public String getUsr030agefromSave() {
        return usr030agefromSave__;
    }
    /**
     * <p>usr030agefromSave をセットします。
     * @param usr030agefromSave usr030agefromSave
     */
    public void setUsr030agefromSave(String usr030agefromSave) {
        usr030agefromSave__ = usr030agefromSave;
    }
    /**
     * <p>usr030agetoSave を取得します。
     * @return usr030agetoSave
     */
    public String getUsr030agetoSave() {
        return usr030agetoSave__;
    }
    /**
     * <p>usr030agetoSave をセットします。
     * @param usr030agetoSave usr030agetoSave
     */
    public void setUsr030agetoSave(String usr030agetoSave) {
        usr030agetoSave__ = usr030agetoSave;
    }
    /**
     * <p>usr030mailSave を取得します。
     * @return usr030mailSave
     */
    public String getUsr030mailSave() {
        return usr030mailSave__;
    }
    /**
     * <p>usr030mailSave をセットします。
     * @param usr030mailSave usr030mailSave
     */
    public void setUsr030mailSave(String usr030mailSave) {
        usr030mailSave__ = usr030mailSave;
    }
    /**
     * <p>usr030meiknSave を取得します。
     * @return usr030meiknSave
     */
    public String getUsr030meiknSave() {
        return usr030meiknSave__;
    }
    /**
     * <p>usr030meiknSave をセットします。
     * @param usr030meiknSave usr030meiknSave
     */
    public void setUsr030meiknSave(String usr030meiknSave) {
        usr030meiknSave__ = usr030meiknSave;
    }
    /**
     * <p>usr030meiSave を取得します。
     * @return usr030meiSave
     */
    public String getUsr030meiSave() {
        return usr030meiSave__;
    }
    /**
     * <p>usr030meiSave をセットします。
     * @param usr030meiSave usr030meiSave
     */
    public void setUsr030meiSave(String usr030meiSave) {
        usr030meiSave__ = usr030meiSave;
    }
    /**
     * <p>usr030seiknSave を取得します。
     * @return usr030seiknSave
     */
    public String getUsr030seiknSave() {
        return usr030seiknSave__;
    }
    /**
     * <p>usr030seiknSave をセットします。
     * @param usr030seiknSave usr030seiknSave
     */
    public void setUsr030seiknSave(String usr030seiknSave) {
        usr030seiknSave__ = usr030seiknSave;
    }
    /**
     * <p>usr030seiSave を取得します。
     * @return usr030seiSave
     */
    public String getUsr030seiSave() {
        return usr030seiSave__;
    }
    /**
     * <p>usr030seiSave をセットします。
     * @param usr030seiSave usr030seiSave
     */
    public void setUsr030seiSave(String usr030seiSave) {
        usr030seiSave__ = usr030seiSave;
    }
    /**
     * <p>usr030shainnoSave を取得します。
     * @return usr030shainnoSave
     */
    public String getUsr030shainnoSave() {
        return usr030shainnoSave__;
    }
    /**
     * <p>usr030shainnoSave をセットします。
     * @param usr030shainnoSave usr030shainnoSave
     */
    public void setUsr030shainnoSave(String usr030shainnoSave) {
        usr030shainnoSave__ = usr030shainnoSave;
    }
    /**
     * <p>usr030tdfkCdSave を取得します。
     * @return usr030tdfkCdSave
     */
    public String getUsr030tdfkCdSave() {
        return usr030tdfkCdSave__;
    }
    /**
     * <p>usr030tdfkCdSave をセットします。
     * @param usr030tdfkCdSave usr030tdfkCdSave
     */
    public void setUsr030tdfkCdSave(String usr030tdfkCdSave) {
        usr030tdfkCdSave__ = usr030tdfkCdSave;
    }
    /**
     * <p>usr030yakushokuSave を取得します。
     * @return usr030yakushokuSave
     */
    public int getUsr030yakushokuSave() {
        return usr030yakushokuSave__;
    }
    /**
     * <p>usr030yakushokuSave をセットします。
     * @param usr030yakushokuSave usr030yakushokuSave
     */
    public void setUsr030yakushokuSave(int usr030yakushokuSave) {
        usr030yakushokuSave__ = usr030yakushokuSave;
    }

    /**
     * <p>usr030userId を取得します。
     * @return usr030userId
     */
    public String getUsr030userId() {
        return usr030userId__;
    }

    /**
     * <p>usr030userId をセットします。
     * @param usr030userId usr030userId
     */
    public void setUsr030userId(String usr030userId) {
        usr030userId__ = usr030userId;
    }

    /**
     * <p>selectgsid を取得します。
     * @return selectgsid
     */
    public int getSelectgsid() {
        return selectgsid__;
    }

    /**
     * <p>selectgsid をセットします。
     * @param selectgsid selectgsid
     */
    public void setSelectgsid(int selectgsid) {
        selectgsid__ = selectgsid;
    }

    /**
     * <p>usr030detailusers を取得します。
     * @return usr030detailusers
     */
    public List<BaseUserModel> getUsr030detailusers() {
        return usr030detailusers__;
    }

    /**
     * <p>usr030detailusers をセットします。
     * @param usr030detailusers usr030detailusers
     */
    public void setUsr030detailusers(List<BaseUserModel> usr030detailusers) {
        usr030detailusers__ = usr030detailusers;
    }

    /**
     * <p>selectgsidSave を取得します。
     * @return selectgsidSave
     */
    public int getSelectgsidSave() {
        return selectgsidSave__;
    }

    /**
     * <p>selectgsidSave をセットします。
     * @param selectgsidSave selectgsidSave
     */
    public void setSelectgsidSave(int selectgsidSave) {
        selectgsidSave__ = selectgsidSave;
    }

    /**
     * <p>usr030userIdSave を取得します。
     * @return usr030userIdSave
     */
    public String getUsr030userIdSave() {
        return usr030userIdSave__;
    }

    /**
     * <p>usr030userIdSave をセットします。
     * @param usr030userIdSave usr030userIdSave
     */
    public void setUsr030userIdSave(String usr030userIdSave) {
        usr030userIdSave__ = usr030userIdSave;
    }

    /**
     * <p>usr030DetailSearchFlg を取得します。
     * @return usr030DetailSearchFlg
     */
    public boolean getUsr030DetailSearchFlg() {
        return usr030DetailSearchFlg__;
    }

    /**
     * <p>usr030DetailSearchFlg をセットします。
     * @param usr030DetailSearchFlg usr030DetailSearchFlg
     */
    public void setUsr030DetailSearchFlg(boolean usr030DetailSearchFlg) {
        usr030DetailSearchFlg__ = usr030DetailSearchFlg;
    }

    /**
     * <p>excludeSysUser を取得します。
     * @return excludeSysUser
     */
    public boolean isExcludeSysUser() {
        return excludeSysUser__;
    }

    /**
     * <p>excludeSysUser をセットします。
     * @param excludeSysUser excludeSysUser
     */
    public void setExcludeSysUser(boolean excludeSysUser) {
        excludeSysUser__ = excludeSysUser;
    }

    /**
     * <p>usr030selectuser を取得します。
     * @return usr030selectuser
     */
    public int getUsr030selectuser() {
        return usr030selectuser__;
    }

    /**
     * <p>usr030selectuser をセットします。
     * @param usr030selectuser usr030selectuser
     */
    public void setUsr030selectuser(int usr030selectuser) {
        usr030selectuser__ = usr030selectuser;
    }

    /**
     * <p>usr030selectusers を取得します。
     * @return usr030selectusers
     */
    public String[] getUsr030selectusers() {
        return usr030selectusers__;
    }

    /**
     * <p>usr030selectusers をセットします。
     * @param usr030selectusers usr030selectusers
     */
    public void setUsr030selectusers(String[] usr030selectusers) {
        usr030selectusers__ = usr030selectusers;
    }

    /**
     * <p>usr030entranceYearFr を取得します。
     * @return usr030entranceYearFr
     */
    public String getUsr030entranceYearFr() {
        return usr030entranceYearFr__;
    }

    /**
     * <p>usr030entranceYearFr をセットします。
     * @param usr030entranceYearFr usr030entranceYearFr
     */
    public void setUsr030entranceYearFr(String usr030entranceYearFr) {
        usr030entranceYearFr__ = usr030entranceYearFr;
    }

    /**
     * <p>usr030entranceMonthFr を取得します。
     * @return usr030entranceMonthFr
     */
    public String getUsr030entranceMonthFr() {
        return usr030entranceMonthFr__;
    }

    /**
     * <p>usr030entranceMonthFr をセットします。
     * @param usr030entranceMonthFr usr030entranceMonthFr
     */
    public void setUsr030entranceMonthFr(String usr030entranceMonthFr) {
        usr030entranceMonthFr__ = usr030entranceMonthFr;
    }

    /**
     * <p>usr030entranceDayFr を取得します。
     * @return usr030entranceDayFr
     */
    public String getUsr030entranceDayFr() {
        return usr030entranceDayFr__;
    }

    /**
     * <p>usr030entranceDayFr をセットします。
     * @param usr030entranceDayFr usr030entranceDayFr
     */
    public void setUsr030entranceDayFr(String usr030entranceDayFr) {
        usr030entranceDayFr__ = usr030entranceDayFr;
    }

    /**
     * <p>usr030entranceYearTo を取得します。
     * @return usr030entranceYearTo
     */
    public String getUsr030entranceYearTo() {
        return usr030entranceYearTo__;
    }

    /**
     * <p>usr030entranceYearTo をセットします。
     * @param usr030entranceYearTo usr030entranceYearTo
     */
    public void setUsr030entranceYearTo(String usr030entranceYearTo) {
        usr030entranceYearTo__ = usr030entranceYearTo;
    }

    /**
     * <p>usr030entranceMonthTo を取得します。
     * @return usr030entranceMonthTo
     */
    public String getUsr030entranceMonthTo() {
        return usr030entranceMonthTo__;
    }

    /**
     * <p>usr030entranceMonthTo をセットします。
     * @param usr030entranceMonthTo usr030entranceMonthTo
     */
    public void setUsr030entranceMonthTo(String usr030entranceMonthTo) {
        usr030entranceMonthTo__ = usr030entranceMonthTo;
    }

    /**
     * <p>usr030entranceDayTo を取得します。
     * @return usr030entranceDayTo
     */
    public String getUsr030entranceDayTo() {
        return usr030entranceDayTo__;
    }

    /**
     * <p>usr030entranceDayTo をセットします。
     * @param usr030entranceDayTo usr030entranceDayTo
     */
    public void setUsr030entranceDayTo(String usr030entranceDayTo) {
        usr030entranceDayTo__ = usr030entranceDayTo;
    }

    /**
     * <p>usr030seibetu を取得します。
     * @return usr030seibetu
     */
    public String getUsr030seibetu() {
        return usr030seibetu__;
    }

    /**
     * <p>usr030seibetu をセットします。
     * @param usr030seibetu usr030seibetu
     */
    public void setUsr030seibetu(String usr030seibetu) {
        usr030seibetu__ = usr030seibetu;
    }

    /**
     * <p>usr030entranceYearFrSave を取得します。
     * @return usr030entranceYearFrSave
     */
    public String getUsr030entranceYearFrSave() {
        return usr030entranceYearFrSave__;
    }

    /**
     * <p>usr030entranceYearFrSave をセットします。
     * @param usr030entranceYearFrSave usr030entranceYearFrSave
     */
    public void setUsr030entranceYearFrSave(String usr030entranceYearFrSave) {
        usr030entranceYearFrSave__ = usr030entranceYearFrSave;
    }

    /**
     * <p>usr030entranceMonthFrSave を取得します。
     * @return usr030entranceMonthFrSave
     */
    public String getUsr030entranceMonthFrSave() {
        return usr030entranceMonthFrSave__;
    }

    /**
     * <p>usr030entranceMonthFrSave をセットします。
     * @param usr030entranceMonthFrSave usr030entranceMonthFrSave
     */
    public void setUsr030entranceMonthFrSave(String usr030entranceMonthFrSave) {
        usr030entranceMonthFrSave__ = usr030entranceMonthFrSave;
    }

    /**
     * <p>usr030entranceDayFrSave を取得します。
     * @return usr030entranceDayFrSave
     */
    public String getUsr030entranceDayFrSave() {
        return usr030entranceDayFrSave__;
    }

    /**
     * <p>usr030entranceDayFrSave をセットします。
     * @param usr030entranceDayFrSave usr030entranceDayFrSave
     */
    public void setUsr030entranceDayFrSave(String usr030entranceDayFrSave) {
        usr030entranceDayFrSave__ = usr030entranceDayFrSave;
    }

    /**
     * <p>usr030entranceYearToSave を取得します。
     * @return usr030entranceYearToSave
     */
    public String getUsr030entranceYearToSave() {
        return usr030entranceYearToSave__;
    }

    /**
     * <p>usr030entranceYearToSave をセットします。
     * @param usr030entranceYearToSave usr030entranceYearToSave
     */
    public void setUsr030entranceYearToSave(String usr030entranceYearToSave) {
        usr030entranceYearToSave__ = usr030entranceYearToSave;
    }

    /**
     * <p>usr030entranceMonthToSave を取得します。
     * @return usr030entranceMonthToSave
     */
    public String getUsr030entranceMonthToSave() {
        return usr030entranceMonthToSave__;
    }

    /**
     * <p>usr030entranceMonthToSave をセットします。
     * @param usr030entranceMonthToSave usr030entranceMonthToSave
     */
    public void setUsr030entranceMonthToSave(String usr030entranceMonthToSave) {
        usr030entranceMonthToSave__ = usr030entranceMonthToSave;
    }

    /**
     * <p>usr030entranceDayToSave を取得します。
     * @return usr030entranceDayToSave
     */
    public String getUsr030entranceDayToSave() {
        return usr030entranceDayToSave__;
    }

    /**
     * <p>usr030entranceDayToSave をセットします。
     * @param usr030entranceDayToSave usr030entranceDayToSave
     */
    public void setUsr030entranceDayToSave(String usr030entranceDayToSave) {
        usr030entranceDayToSave__ = usr030entranceDayToSave;
    }

    /**
     * <p>usr030seibetuSave を取得します。
     * @return usr030seibetuSave
     */
    public String getUsr030seibetuSave() {
        return usr030seibetuSave__;
    }

    /**
     * <p>usr030seibetuSave をセットします。
     * @param usr030seibetuSave usr030seibetuSave
     */
    public void setUsr030seibetuSave(String usr030seibetuSave) {
        usr030seibetuSave__ = usr030seibetuSave;
    }

    /**
     * <p>seibetuLabelList を取得します。
     * @return seibetuLabelList
     */
    public ArrayList<LabelValueBean> getSeibetuLabelList() {
        return seibetuLabelList__;
    }

    /**
     * <p>seibetuLabelList をセットします。
     * @param seibetuLabelList seibetuLabelList
     */
    public void setSeibetuLabelList(ArrayList<LabelValueBean> seibetuLabelList) {
        seibetuLabelList__ = seibetuLabelList;
    }

    /**
     * <p>usr030entranceYearFrLabel を取得します。
     * @return usr030entranceYearFrLabel
     */
    public List<LabelValueBean> getUsr030entranceYearFrLabel() {
        return usr030entranceYearFrLabel__;
    }

    /**
     * <p>usr030entranceYearFrLabel をセットします。
     * @param usr030entranceYearFrLabel usr030entranceYearFrLabel
     */
    public void setUsr030entranceYearFrLabel(
            List<LabelValueBean> usr030entranceYearFrLabel) {
        usr030entranceYearFrLabel__ = usr030entranceYearFrLabel;
    }

    /**
     * <p>usr030entranceMonthFrLabel を取得します。
     * @return usr030entranceMonthFrLabel
     */
    public List<LabelValueBean> getUsr030entranceMonthFrLabel() {
        return usr030entranceMonthFrLabel__;
    }

    /**
     * <p>usr030entranceMonthFrLabel をセットします。
     * @param usr030entranceMonthFrLabel usr030entranceMonthFrLabel
     */
    public void setUsr030entranceMonthFrLabel(
            List<LabelValueBean> usr030entranceMonthFrLabel) {
        usr030entranceMonthFrLabel__ = usr030entranceMonthFrLabel;
    }

    /**
     * <p>usr030entranceDayFrLabel を取得します。
     * @return usr030entranceDayFrLabel
     */
    public List<LabelValueBean> getUsr030entranceDayFrLabel() {
        return usr030entranceDayFrLabel__;
    }

    /**
     * <p>usr030entranceDayFrLabel をセットします。
     * @param usr030entranceDayFrLabel usr030entranceDayFrLabel
     */
    public void setUsr030entranceDayFrLabel(
            List<LabelValueBean> usr030entranceDayFrLabel) {
        usr030entranceDayFrLabel__ = usr030entranceDayFrLabel;
    }

    /**
     * <p>usr030usrUkoFlg を取得します。
     * @return usr030usrUkoFlg
     */
    public int getUsr030usrUkoFlg() {
        return usr030usrUkoFlg__;
    }

    /**
     * <p>usr030usrUkoFlg をセットします。
     * @param usr030usrUkoFlg usr030usrUkoFlg
     */
    public void setUsr030usrUkoFlg(int usr030usrUkoFlg) {
        usr030usrUkoFlg__ = usr030usrUkoFlg;
    }

    /**
     * <p>usr030usrUkoFlgSave を取得します。
     * @return usr030usrUkoFlgSave
     */
    public int getUsr030usrUkoFlgSave() {
        return usr030usrUkoFlgSave__;
    }

    /**
     * <p>usr030usrUkoFlgSave をセットします。
     * @param usr030usrUkoFlgSave usr030usrUkoFlgSave
     */
    public void setUsr030usrUkoFlgSave(int usr030usrUkoFlgSave) {
        usr030usrUkoFlgSave__ = usr030usrUkoFlgSave;
    }

}