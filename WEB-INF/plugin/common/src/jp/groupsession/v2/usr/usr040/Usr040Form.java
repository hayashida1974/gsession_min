package jp.groupsession.v2.usr.usr040;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.GSValidateUser;
import jp.groupsession.v2.usr.usr030.KanaLinkModel;

/**
 * <br>[機  能] ユーザ情報一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr040Form extends AbstractGsForm {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr040Form.class);

    /** 画面モード 1:氏名, 2:グループ,3:詳細検索 */
    private int usr040cmdMode__ = GSConstUser.MODE_NAME;
    /** 第一ソートオーダー */
    private int usr040orderKey__ = GSConst.ORDER_KEY_ASC;
    /** 第一ソート キー */
    private int usr040sortKey__ = GSConstUser.USER_SORT_NAME;
    /** 第二ソートオーダー */
    private int usr040orderKey2__ = GSConst.ORDER_KEY_ASC;
    /** 第二ソート キー */
    private int usr040sortKey2__ = GSConstUser.USER_SORT_SNO;
    /** 検索条件保存フラグ */
    private int usr040saveFlg__ = 0;
    /** ソート コンボ */
    private List<LabelValueBean> sortKeyLabelList__ = null;

    /** 管理者権限有無*/
    private int adminKbn__;
    /** エクスポートボタン表示切替 */
    private String usr040CsvExportKbn__ = null;
    /** ラベル付与権限 */
    private String usrLabelSetKbn__ = GSConstUser.LABEL_SET_OK;
    /** ラベル編集権限 */
    private String usrLabelEditKbn__ = GSConstUser.LABEL_EDIT_OK;

    // 名前検索
    /** 存在するカナリスト */
    List<KanaLinkModel> usr040ekanas__ = null;
    /** ユーザが選択した検索用カナ */
    private String usr040SearchKana__ = null;

    //グループ検索
    /** 選択グループSID */
    private int selectgsid__ = -1;
    /** 選択グループ名称 */
    private String selectgpname__ = null;
    /** スクロール位置 */
    private String scrollPosition__ = null;
    /** グループ絞込み検索 グループID */
    private String usr040GrpSearchGId__ = null;
    /** グループ絞込み検索 グループ名 */
    private String usr040GrpSearchGName__ = null;

    //詳細検索
    /** キーワード */
    private String usr040Keyword__ = null;
    /** キーワード区分（社員/職員番号） */
    private int usr040KeyKbnShainno__ = 0;
    /** キーワード区分（氏名） */
    private int usr040KeyKbnName__ = 0;
    /** キーワード区分（氏名カナ） */
    private int usr040KeyKbnNameKn__ = 0;
    /** キーワード区分（E-MAIL） */
    private int usr040KeyKbnMail__ = 0;
    /** キーワード区分（電話番号） */
    private int usr040KeyKbnTel__ = 0;

    /** 年齢From */
    private String usr040agefrom__ = null;
    /** 年齢To */
    private String usr040ageto__ = null;
    /** グループラベル */
    private ArrayList<LabelValueBean> grpLabelList__ = null;
    /** 役職 */
    private int usr040yakushoku__ = GSConstCommon.NUM_INIT;
    /** 入社年from */
    private String usr040entranceYearFr__ = null;
    /** 入社月from */
    private String usr040entranceMonthFr__ = null;
    /** 入社日from */
    private String usr040entranceDayFr__ = null;
    /** 入社年to年 */
    private String usr040entranceYearTo__ = null;
    /** 入社月to月 */
    private String usr040entranceMonthTo__ = null;
    /** 入社日to日 */
    private String usr040entranceDayTo__ = null;
    /** 性別 */
    private String usr040seibetu__ = String.valueOf("-1");
    /** 都道府県コード*/
    private String usr040tdfkCd__ = null;
    /** 都道府県ラベル */
    private ArrayList<LabelValueBean> tdfkLabelList__ = null;
    /** 役職ラベル */
    private ArrayList<LabelValueBean> posLabelList__ = null;
    /** 性別ラベル */
    private ArrayList<LabelValueBean> seibetuLabelList__ = null;
    /** 表示用 詳細検索条件文字列 */
    private String usr040DispKensakuJouken__ = null;
    /** 詳細検索実行フラグ */
    private int usr040DetailExeFlg__ = 0;

    /** ページ1(上) */
    private int usr040pageNum1__;
    /** ページ2(下) */
    private int usr040pageNum2__;
    /** ページラベル */
    ArrayList < LabelValueBean > usr040PageLabel__;

    /** 選択グループSID(Save) */
    private int selectgsidSave__ = -1;
    /** グループ絞込み検索 グループId(Save) */
    private String usr040GrpSearchGIdSave__ = null;
    /** グループ絞込み検索 グループ名(Save) */
    private String usr040GrpSearchGNameSave__ = null;
    /** ユーザが選択した検索用カナ(Save) */
    private String usr040SearchKanaSave__ = null;
    /** キーワード(Save) */
    private String usr040KeywordSave__ = null;
    /** キーワード区分（社員/職員番号 Save） */
    private int usr040KeyKbnShainnoSave__ = 0;
    /** キーワード区分（氏名 Save） */
    private int usr040KeyKbnNameSave__ = 1;
    /** キーワード区分（氏名カナ Save） */
    private int usr040KeyKbnNameKnSave__ = 0;
    /** キーワード区分（E-MAIL Save） */
    private int usr040KeyKbnMailSave__ = 0;
    /** キーワード区分（電話番号 Save） */
    private int usr040KeyKbnTelSave__ = 0;
    /** 年齢From(Save) */
    private String usr040agefromSave__ = null;
    /** 年齢To(Save) */
    private String usr040agetoSave__ = null;
    /** 役職(Save) */
    private int usr040yakushokuSave__ = GSConstCommon.NUM_INIT;
    /** 入社年from */
    private String usr040entranceYearFrSave__ = null;
    /** 入社月from */
    private String usr040entranceMonthFrSave__ = null;
    /** 入社日from */
    private String usr040entranceDayFrSave__ = null;
    /** 入社年to年 */
    private String usr040entranceYearToSave__ = null;
    /** 入社月to月 */
    private String usr040entranceMonthToSave__ = null;
    /** 入社日to日 */
    private String usr040entranceDayToSave__ = null;
    /** 性別 */
    private String usr040seibetuSave__ = String.valueOf(GSConstUser.SEIBETU_UNSET);
    /** 都道府県コード(Save) */
    private String usr040tdfkCdSave__ = null;
    /** 第一ソートオーダー */
    private int usr040orderKeySave__ = GSConstCommon.NUM_INIT;
    /** 第一ソート キー */
    private int usr040sortKeySave__ = GSConstCommon.NUM_INIT;
    /** 第二ソートオーダー */
    private int usr040orderKey2Save__ = GSConstCommon.NUM_INIT;
    /** 第二ソート キー */
    private int usr040sortKey2Save__ = GSConstCommon.NUM_INIT;

    /** 検索したかどうかのフラグ */
    private int usr040SearchFlg__ = GSConstUser.SEARCH_MI;
    //スケジュール用
    /** 処理モード */
    private String cmd__ = null;
    /** ユーザSID */
    private String sch010SelectUsrSid__;
    /** ユーザ区分 */
    private String sch010SelectUsrKbn__;
    /** 表示日付 */
    private String sch010DspDate__;

    /** 再表示フラグ */
    private int usr040DspFlg__ = 0;

    //検索結果
    /** 検索結果に該当するユーザ */
    private List<Usr040DspModel> usr040users__ = null;

    /** 遷移元 メイン個人設定メニュー:2 メイン管理者メニュー:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;

    /** Webメール 連携判定 */
    private int usr040webmail__ = 0;
    /** Webメール メールアドレス */
    private String usr040webmailAddress__ = null;
    /** Webメール 連携判定 種別 */
    private int usr040webmailType__ = 0;
    /** Webメール 選択アドレス */
    private String[] usr040webmailSelectAddress__ = null;
    /** Webメール 連携 アドレス帳選択使用可能フラグ */
    private boolean usr040webmailCanUseAddress__ = true;

    /** 選択ラベル */
    private String[] usr040selectLabel__;
    /** 選択ユーザ */
    private String[] usr040selectUser__;

    /** カテゴリーラベルデータリスト */
    private ArrayList<UsrCategoryLabelModel> usr040CaegoryLabelList__ = null;
    /** ラベルSID*/
    private String[] usr040labSid__;

    /** カテゴリー開閉フラグ   0:閉  1:開 */
    private String[] usr040CategoryOpenFlg__ = null;

    /** カテゴリー開閉初期設定フラグ */
    private int usr040CategorySetInitFlg__ = 0;

    /** ラベルSID(Save) */
    private String[] usr040labSidSave__;

    /** 送信区分 */
    private int usr040SendMailMode__ =  GSConst.SEND_KBN_ATESAKI;
    /** 単一選択ユーザSID */
    private int usr040UsrSid__ = 0;
    /** 削除ユーザSID */
    private int usr040DelUsrSid__ = 0;
    /** アドレス帳データ 宛先 */
    private List<CmnUsrmInfModel> usr040AtskList__;
    /** 選択アドレスSID 宛先 */
    private String[] usr040SidsAtsk__;
    /** アドレス帳データ CC */
    private List<CmnUsrmInfModel> usr040CcList__;
    /** 選択アドレスSID CC */
    private String[] usr040SidsCc__;
    /** アドレス帳データ BCC */
    private List<CmnUsrmInfModel> usr040BccList__;
    /** 選択アドレスSID BCC */
    private String[] usr040SidsBcc__;
    /** 選択アドレス区分 */
    private String usr040AddressKbn__ = String.valueOf(GSConst.ADDRESS_KBN_1);

    /** 入社年月日from年ラベル */
    private List<LabelValueBean> usr040entranceYearFrLabel__;
    /** 入社年月日from月ラベル */
    private List<LabelValueBean> usr040entranceMonthFrLabel__;
    /** 入社年月日from日ラベル */
    private List<LabelValueBean> usr040entranceDayFrLabel__;

    /** ベースURL */
    private String usr040BaseUrl__;

    /**
     * <br>[機  能] 宛先・CC・BCCボタンクリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors エラー
     */
    public ActionErrors validateSelectCheckWebmail(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;

        //対象メッセージ
        String msgSendTarget = "";

        if (usr040SendMailMode__ == GSConst.SEND_KBN_ATESAKI) {
            //宛先
            msgSendTarget = gsMsg.getMessage(req, "cmn.from");
        } else if (usr040SendMailMode__ == GSConst.SEND_KBN_CC) {
            //CC
            msgSendTarget = "CC";
        } else {
            //BCC
            msgSendTarget = "BCC";
        }


        //未選択チェック
        if (usr040selectUser__ == null) {
            msg = new ActionMessage(
                    "error.select.required.text", msgSendTarget);
            StrutsUtil.addMessage(errors, msg, "usrSid");
        } else {
            if (usr040selectUser__.length < 1) {
                msg = new ActionMessage(
                        "error.select.required.text", msgSendTarget);
                StrutsUtil.addMessage(errors, msg, "usrSid");
            }
        }
        return errors;
    }

    /**
     * <p>backScreen を取得します。
     * @return backScreen
     */
    public int getBackScreen() {
        return backScreen__;
    }
    /**
     * <p>backScreen をセットします。
     * @param backScreen backScreen
     */
    public void setBackScreen(int backScreen) {
        backScreen__ = backScreen;
    }
    /**
     * <p>usr040cmdMode を取得します。
     * @return usr040cmdMode
     */
    public int getUsr040cmdMode() {
        return usr040cmdMode__;
    }
    /**
     * <p>usr040cmdMode をセットします。
     * @param usr040cmdMode usr040cmdMode
     */
    public void setUsr040cmdMode(int usr040cmdMode) {
        usr040cmdMode__ = usr040cmdMode;
    }
    /**
     * <p>usr040orderKey を取得します。
     * @return usr040orderKey
     */
    public int getUsr040orderKey() {
        return usr040orderKey__;
    }
    /**
     * <p>usr040orderKey をセットします。
     * @param usr040orderKey usr040orderKey
     */
    public void setUsr040orderKey(int usr040orderKey) {
        usr040orderKey__ = usr040orderKey;
    }
    /**
     * <p>usr040sortKey を取得します。
     * @return usr040sortKey
     */
    public int getUsr040sortKey() {
        return usr040sortKey__;
    }
    /**
     * <p>usr040sortKey をセットします。
     * @param usr040sortKey usr040sortKey
     */
    public void setUsr040sortKey(int usr040sortKey) {
        usr040sortKey__ = usr040sortKey;
    }
    /**
     * <p>usr040ekanas を取得します。
     * @return usr040ekanas
     */
    public List<KanaLinkModel> getUsr040ekanas() {
        return usr040ekanas__;
    }
    /**
     * <p>usr040ekanas をセットします。
     * @param usr040ekanas usr040ekanas
     */
    public void setUsr040ekanas(List<KanaLinkModel> usr040ekanas) {
        usr040ekanas__ = usr040ekanas;
    }
    /**
     * <p>usr040SearchKana を取得します。
     * @return usr040SearchKana
     */
    public String getUsr040SearchKana() {
        return usr040SearchKana__;
    }
    /**
     * <p>usr040SearchKana をセットします。
     * @param usr040SearchKana usr040SearchKana
     */
    public void setUsr040SearchKana(String usr040SearchKana) {
        usr040SearchKana__ = usr040SearchKana;
    }
    /**
     * <p>usr040users を取得します。
     * @return usr040users
     */
    public List<Usr040DspModel> getUsr040users() {
        return usr040users__;
    }
    /**
     * <p>usr040users をセットします。
     * @param usr040users usr040users
     */
    public void setUsr040users(List<Usr040DspModel> usr040users) {
        usr040users__ = usr040users;
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
     * <p>selectgpname を取得します。
     * @return selectgpname
     */
    public String getSelectgpname() {
        return selectgpname__;
    }
    /**
     * <p>selectgpname をセットします。
     * @param selectgpname selectgpname
     */
    public void setSelectgpname(String selectgpname) {
        selectgpname__ = selectgpname;
    }
    /**
     * <p>tdfkLabelList を取得します。
     * @return tdfkLabelList
     */
    public ArrayList<LabelValueBean> getTdfkLabelList() {
        return tdfkLabelList__;
    }
    /**
     * <p>tdfkLabelList をセットします。
     * @param tdfkLabelList tdfkLabelList
     */
    public void setTdfkLabelList(ArrayList<LabelValueBean> tdfkLabelList) {
        tdfkLabelList__ = tdfkLabelList;
    }
    /**
     * <p>usr040tdfkCd を取得します。
     * @return usr040tdfkCd
     */
    public String getUsr040tdfkCd() {
        return usr040tdfkCd__;
    }
    /**
     * <p>usr040tdfkCd をセットします。
     * @param usr040tdfkCd usr040tdfkCd
     */
    public void setUsr040tdfkCd(String usr040tdfkCd) {
        usr040tdfkCd__ = usr040tdfkCd;
    }
    /**
     * <p>grpLabelList を取得します。
     * @return grpLabelList
     */
    public ArrayList<LabelValueBean> getGrpLabelList() {
        return grpLabelList__;
    }
    /**
     * <p>grpLabelList をセットします。
     * @param grpLabelList grpLabelList
     */
    public void setGrpLabelList(ArrayList<LabelValueBean> grpLabelList) {
        grpLabelList__ = grpLabelList;
    }
    /**
     * <p>usr040agefrom を取得します。
     * @return usr040agefrom
     */
    public String getUsr040agefrom() {
        return usr040agefrom__;
    }
    /**
     * <p>usr040agefrom をセットします。
     * @param usr040agefrom usr040agefrom
     */
    public void setUsr040agefrom(String usr040agefrom) {
        usr040agefrom__ = usr040agefrom;
    }
    /**
     * <p>usr040ageto を取得します。
     * @return usr040ageto
     */
    public String getUsr040ageto() {
        return usr040ageto__;
    }
    /**
     * <p>usr040ageto をセットします。
     * @param usr040ageto usr040ageto
     */
    public void setUsr040ageto(String usr040ageto) {
        usr040ageto__ = usr040ageto;
    }
    /**
     * <p>usr040yakushoku を取得します。
     * @return usr040yakushoku
     */
    public int getUsr040yakushoku() {
        return usr040yakushoku__;
    }
    /**
     * <p>usr040yakushoku をセットします。
     * @param usr040yakushoku usr040yakushoku
     */
    public void setUsr040yakushoku(int usr040yakushoku) {
        usr040yakushoku__ = usr040yakushoku;
    }
    /**
     * <p>posLabelList を取得します。
     * @return posLabelList
     */
    public ArrayList<LabelValueBean> getPosLabelList() {
        return posLabelList__;
    }
    /**
     * <p>posLabelList をセットします。
     * @param posLabelList posLabelList
     */
    public void setPosLabelList(ArrayList<LabelValueBean> posLabelList) {
        posLabelList__ = posLabelList;
    }

    /**
     * <br>[機  能] 入力チェックを行う(グループ絞込み検索)
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl リクエストモデル
     * @return エラー
     * @throws Exception 実行例外
     */
    public ActionErrors validateGroupSearch(ActionMapping map, RequestModel reqMdl)
            throws Exception {
        ActionErrors errors = new ActionErrors();
        GSValidateUser gsValidateUser = new GSValidateUser(reqMdl);

        //グループID
        if (NullDefault.getString(usr040GrpSearchGId__, "").length() > 0) {
            gsValidateUser.validateSearchGroupId(errors, usr040GrpSearchGId__);
        }
        //グループ名
        if (NullDefault.getString(usr040GrpSearchGName__, "").length() > 0) {
            gsValidateUser.validateSearchGroupName(errors, usr040GrpSearchGName__);
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
        GsMessage gsMsg = new GsMessage(reqMdl);
        GSValidateUser gsValidateUser = new GSValidateUser(reqMdl);

        ActionMessage msg = null;

        //キーワード検索
        if (!StringUtil.isNullZeroString(usr040Keyword__)) {
            if (usr040KeyKbnShainno__ == 1
                    || usr040KeyKbnName__ == 1
                    || usr040KeyKbnNameKn__ == 1
                    || usr040KeyKbnMail__ == 1
                    || usr040KeyKbnTel__ == 1) {
                //キーワードチェック
                GSValidateUser.validateSearchKeyword(errors, usr040Keyword__, reqMdl);
            } else {
                //未選択エラー
                //検索対象
                String textTarget = gsMsg.getMessage("cmn.keyword.search.Kbn");
                //未選択の場合エラー
                msg = new ActionMessage(
                        "error.select.required.text", textTarget);
                StrutsUtil.addMessage(errors, msg, "target");
            }
        }

        //年齢FROM通過前のエラーサイズ
        int ageFrBeforErrorSize = errors.size();

        //年齢 FROM
        gsValidateUser.validateSearchAgeFrom(errors, usr040agefrom__);
        //年齢 TO
        gsValidateUser.validateSearchAgeTo(errors, usr040ageto__);

        //年齢TO通過後のエラーサイズ
        int ageToAfterErrorSize = errors.size();

        if (ageFrBeforErrorSize == ageToAfterErrorSize) {
            log__.debug("年齢From、Toにエラー無し");
            gsValidateUser.validateSearchAgeRange(errors, usr040agefrom__, usr040ageto__);
        }

        //所属グループ
        GSValidateUser.validateSelectGroup(errors, selectgsid__, con, reqMdl);
        //役職
        gsValidateUser.validateSearchYakushoku(errors, usr040yakushoku__, con);
        //都道府県
        gsValidateUser.validateTdfk(errors, usr040tdfkCd__, con);
        //ソートキー
        gsValidateUser.validateSearchSortOrder(
                errors, String.valueOf(usr040sortKey__), String.valueOf(usr040sortKey2__));
        //ラベル
        gsValidateUser.validateSearchLabel(errors, usr040labSid__);

        //入社年月日チェックフラグ(true=入力OK、false=NG)
        boolean fromOk = false;
        UDate sFrDate = new UDate();

        String usr040entranceYearFr = usr040entranceYearFr__;
        String usr040entranceMonthFr = usr040entranceMonthFr__;
        String usr040entranceDayFr = usr040entranceDayFr__;
        String usr040entranceYearTo = usr040entranceYearTo__;
        String usr040entranceMonthTo = usr040entranceMonthTo__;
        String usr040entranceDayTo = usr040entranceDayTo__;

        //日のみ
        if (StringUtil.isNullZeroStringSpace(usr040entranceYearFr)
                && StringUtil.isNullZeroStringSpace(usr040entranceMonthFr)
                && !StringUtil.isNullZeroStringSpace(usr040entranceDayFr)) {
            String textStartDateStart = gsMsg.getMessage("user.153");
            msg = new ActionMessage("error.select3.required.text", textStartDateStart);
            errors.add("error.select3.required.text", msg);
        }

        //日月のみ
        if (StringUtil.isNullZeroStringSpace(usr040entranceYearFr)
                && !StringUtil.isNullZeroStringSpace(usr040entranceMonthFr)
                && !StringUtil.isNullZeroStringSpace(usr040entranceDayFr)) {
            String textStartDateStart = gsMsg.getMessage("user.153");
            msg = new ActionMessage("error.select3.required.text", textStartDateStart);
            errors.add("error.select3.required.text", msg);
        }

        //月のみ
        if (StringUtil.isNullZeroStringSpace(usr040entranceYearFr)
                && !StringUtil.isNullZeroStringSpace(usr040entranceMonthFr)
                && StringUtil.isNullZeroStringSpace(usr040entranceDayFr)) {
            String textStartDateStart = gsMsg.getMessage("user.153");
            msg = new ActionMessage("error.select3.required.text", textStartDateStart);
            errors.add("error.select3.required.text", msg);
        }

        //年日のみ
        if (!StringUtil.isNullZeroStringSpace(usr040entranceYearFr)
                && StringUtil.isNullZeroStringSpace(usr040entranceMonthFr)
                && !StringUtil.isNullZeroStringSpace(usr040entranceDayFr)) {
            String textStartDateStart = gsMsg.getMessage("user.153");
            msg = new ActionMessage("error.select3.required.text", textStartDateStart);
            errors.add("error.select3.required.text", msg);
        }

        //年月のみ
        if (!StringUtil.isNullZeroStringSpace(usr040entranceYearFr)
                && !StringUtil.isNullZeroStringSpace(usr040entranceMonthFr)
                && StringUtil.isNullZeroStringSpace(usr040entranceDayFr)) {
            UDate udate = new UDate();
            udate.setYear(Integer.parseInt(usr040entranceYearFr));
            udate.setMonth(Integer.parseInt(usr040entranceMonthFr));
            usr040entranceDayFr = String.valueOf(1);
        }

        //年のみ
        if (!StringUtil.isNullZeroStringSpace(usr040entranceYearFr)
                && StringUtil.isNullZeroStringSpace(usr040entranceMonthFr)
                && StringUtil.isNullZeroStringSpace(usr040entranceDayFr)) {
            UDate udate = new UDate();
            udate.setYear(Integer.parseInt(usr040entranceYearFr));
            usr040entranceMonthFr = String.valueOf(1);
            usr040entranceDayFr = String.valueOf(1);
        }

        if (!StringUtil.isNullZeroStringSpace(usr040entranceYearFr)
                && !StringUtil.isNullZeroStringSpace(usr040entranceMonthFr)
                && !StringUtil.isNullZeroStringSpace(usr040entranceDayFr)) {
            int sFYear = Integer.parseInt(usr040entranceYearFr);
            int sFMonth = Integer.parseInt(usr040entranceMonthFr);
            int sFDay = Integer.parseInt(usr040entranceDayFr);


            sFrDate.setDate(sFYear, sFMonth, sFDay);
            sFrDate.setSecond(0);
            sFrDate.setMilliSecond(
                    java.util.Calendar.getInstance().getMinimum(java.util.Calendar.MILLISECOND));
            if (sFrDate.getYear() != sFYear
            || sFrDate.getMonth() != sFMonth
            || sFrDate.getIntDay() != sFDay) {
                //入社日付：開始
                String textStartDateStart = gsMsg.getMessage("user.153");
                msg = new ActionMessage("error.input.notfound.date", textStartDateStart);
                errors.add("error.input.notfound.date", msg);
            } else {
                fromOk = true;
            }
        }


        //入社年月日チェックフラグ(true=入力OK、false=NG)
        boolean toOk = false;
        UDate sToDate = new UDate();


        //日のみ
        if (StringUtil.isNullZeroStringSpace(usr040entranceYearTo)
                && StringUtil.isNullZeroStringSpace(usr040entranceMonthTo)
                && !StringUtil.isNullZeroStringSpace(usr040entranceDayTo)) {
            String textStartDateStart = gsMsg.getMessage("user.155");
            msg = new ActionMessage("error.select3.required.text", textStartDateStart);
            errors.add("error.select3.required.text", msg);
        }

        //日月のみ
        if (StringUtil.isNullZeroStringSpace(usr040entranceYearTo)
                && !StringUtil.isNullZeroStringSpace(usr040entranceMonthTo)
                && !StringUtil.isNullZeroStringSpace(usr040entranceDayTo)) {
            String textStartDateStart = gsMsg.getMessage("user.155");
            msg = new ActionMessage("error.select3.required.text", textStartDateStart);
            errors.add("error.select3.required.text", msg);
        }

        //月のみ
        if (StringUtil.isNullZeroStringSpace(usr040entranceYearTo)
                && !StringUtil.isNullZeroStringSpace(usr040entranceMonthTo)
                && StringUtil.isNullZeroStringSpace(usr040entranceDayTo)) {
            String textStartDateStart = gsMsg.getMessage("user.155");
            msg = new ActionMessage("error.select3.required.text", textStartDateStart);
            errors.add("error.select3.required.text", msg);
        }

        //年日のみ
        if (!StringUtil.isNullZeroStringSpace(usr040entranceYearTo)
                && StringUtil.isNullZeroStringSpace(usr040entranceMonthTo)
                && !StringUtil.isNullZeroStringSpace(usr040entranceDayTo)) {
            String textStartDateStart = gsMsg.getMessage("user.155");
            msg = new ActionMessage("error.select3.required.text", textStartDateStart);
            errors.add("error.select3.required.text", msg);
        }

        //年月のみ
        if (!StringUtil.isNullZeroStringSpace(usr040entranceYearTo)
                && !StringUtil.isNullZeroStringSpace(usr040entranceMonthTo)
                && StringUtil.isNullZeroStringSpace(usr040entranceDayTo)) {
            UDate udate = new UDate();
            udate.setYear(Integer.parseInt(usr040entranceYearTo));
            udate.setMonth(Integer.parseInt(usr040entranceMonthTo));
            usr040entranceDayTo = String.valueOf(udate.getMaxDayOfMonth());
        }

        //年のみ
        if (!StringUtil.isNullZeroStringSpace(usr040entranceYearTo)
                && StringUtil.isNullZeroStringSpace(usr040entranceMonthTo)
                && StringUtil.isNullZeroStringSpace(usr040entranceDayTo)) {
            UDate udate = new UDate();
            udate.setYear(Integer.parseInt(usr040entranceYearTo));
            udate.setMonth(12);
            usr040entranceMonthTo = "12";
            usr040entranceDayTo = String.valueOf(udate.getMaxDayOfMonth());
        }


        if (!StringUtil.isNullZeroStringSpace(usr040entranceYearTo)
                && !StringUtil.isNullZeroStringSpace(usr040entranceMonthTo)
                && !StringUtil.isNullZeroStringSpace(usr040entranceDayTo)) {
            int sTYear = Integer.parseInt(usr040entranceYearTo);
            int sTMonth = Integer.parseInt(usr040entranceMonthTo);
            int sTDay = Integer.parseInt(usr040entranceDayTo);


            sToDate.setDate(sTYear, sTMonth, sTDay);
            sToDate.setSecond(0);
            sToDate.setMilliSecond(
                    java.util.Calendar.getInstance().getMinimum(java.util.Calendar.MILLISECOND));
            if (sToDate.getYear() != sTYear
            || sToDate.getMonth() != sTMonth
            || sToDate.getIntDay() != sTDay) {
                //開始日付：開始・終了
                String textStartDateEnd = gsMsg.getMessage("user.155");
                msg = new ActionMessage("error.input.notfound.date", textStartDateEnd);
                errors.add("error.input.notfound.date", msg);
            } else {
                toOk = true;
            }
        }


        //個別チェックOKの場合
        if (fromOk && toOk) {
            //from～to大小チェック
            if (sFrDate.compareDateYMD(sToDate) == UDate.SMALL) {
                //開始 <= 終了
                String textStartOrEnd = gsMsg.getMessage("cmn.start.or.end");
//              開始日付：開始・終了
                String textStartDateStartEnd = gsMsg.getMessage("user.154");
                msg = new ActionMessage("error.input.comp.text",
                        textStartDateStartEnd, textStartOrEnd);
                errors.add("" + "error.input.comp.text", msg);
            }
        }

        return errors;
    }

    /**
     * <br>[機  能] ソートキーの重複チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param reqMdl RequestModel
     * @return エラー
     * @throws SQLException SQL実行例外
     */
    public ActionErrors validateCheckDoubleSort(ActionMapping map,
            RequestModel reqMdl) throws SQLException {
        ActionErrors errors = new ActionErrors();
        GSValidateUser gsValidate = new GSValidateUser(reqMdl);
        //ソートキー
        gsValidate.validateSearchSortOrder(
                errors, String.valueOf(usr040sortKey__), String.valueOf(usr040sortKey2__));

        return errors;
    }

    /**
     * <p>usr040PageLabel を取得します。
     * @return usr040PageLabel
     */
    public ArrayList<LabelValueBean> getUsr040PageLabel() {
        return usr040PageLabel__;
    }
    /**
     * <p>usr040PageLabel をセットします。
     * @param usr040PageLabel usr040PageLabel
     */
    public void setUsr040PageLabel(ArrayList<LabelValueBean> usr040PageLabel) {
        usr040PageLabel__ = usr040PageLabel;
    }
    /**
     * <p>usr040pageNum1 を取得します。
     * @return usr040pageNum1
     */
    public int getUsr040pageNum1() {
        return usr040pageNum1__;
    }
    /**
     * <p>usr040pageNum1 をセットします。
     * @param usr040pageNum1 usr040pageNum1
     */
    public void setUsr040pageNum1(int usr040pageNum1) {
        usr040pageNum1__ = usr040pageNum1;
    }
    /**
     * <p>usr040pageNum2 を取得します。
     * @return usr040pageNum2
     */
    public int getUsr040pageNum2() {
        return usr040pageNum2__;
    }
    /**
     * <p>usr040pageNum2 をセットします。
     * @param usr040pageNum2 usr040pageNum2
     */
    public void setUsr040pageNum2(int usr040pageNum2) {
        usr040pageNum2__ = usr040pageNum2;
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
     * <p>usr040agefromSave を取得します。
     * @return usr040agefromSave
     */
    public String getUsr040agefromSave() {
        return usr040agefromSave__;
    }
    /**
     * <p>usr040agefromSave をセットします。
     * @param usr040agefromSave usr040agefromSave
     */
    public void setUsr040agefromSave(String usr040agefromSave) {
        usr040agefromSave__ = usr040agefromSave;
    }
    /**
     * <p>usr040agetoSave を取得します。
     * @return usr040agetoSave
     */
    public String getUsr040agetoSave() {
        return usr040agetoSave__;
    }
    /**
     * <p>usr040agetoSave をセットします。
     * @param usr040agetoSave usr040agetoSave
     */
    public void setUsr040agetoSave(String usr040agetoSave) {
        usr040agetoSave__ = usr040agetoSave;
    }
    /**
     * <p>usr040SearchKanaSave を取得します。
     * @return usr040SearchKanaSave
     */
    public String getUsr040SearchKanaSave() {
        return usr040SearchKanaSave__;
    }
    /**
     * <p>usr040SearchKanaSave をセットします。
     * @param usr040SearchKanaSave usr040SearchKanaSave
     */
    public void setUsr040SearchKanaSave(String usr040SearchKanaSave) {
        usr040SearchKanaSave__ = usr040SearchKanaSave;
    }
    /**
     * <p>usr040tdfkCdSave を取得します。
     * @return usr040tdfkCdSave
     */
    public String getUsr040tdfkCdSave() {
        return usr040tdfkCdSave__;
    }
    /**
     * <p>usr040tdfkCdSave をセットします。
     * @param usr040tdfkCdSave usr040tdfkCdSave
     */
    public void setUsr040tdfkCdSave(String usr040tdfkCdSave) {
        usr040tdfkCdSave__ = usr040tdfkCdSave;
    }
    /**
     * <p>usr040yakushokuSave を取得します。
     * @return usr040yakushokuSave
     */
    public int getUsr040yakushokuSave() {
        return usr040yakushokuSave__;
    }
    /**
     * <p>usr040yakushokuSave をセットします。
     * @param usr040yakushokuSave usr040yakushokuSave
     */
    public void setUsr040yakushokuSave(int usr040yakushokuSave) {
        usr040yakushokuSave__ = usr040yakushokuSave;
    }
    /**
     * <p>usr040DispKensakuJouken を取得します。
     * @return usr040DispKensakuJouken
     */
    public String getUsr040DispKensakuJouken() {
        return usr040DispKensakuJouken__;
    }
    /**
     * <p>usr040DispKensakuJouken をセットします。
     * @param usr040DispKensakuJouken usr040DispKensakuJouken
     */
    public void setUsr040DispKensakuJouken(String usr040DispKensakuJouken) {
        usr040DispKensakuJouken__ = usr040DispKensakuJouken;
    }
    /**
     * <p>usr040SearchFlg を取得します。
     * @return usr040SearchFlg
     */
    public int getUsr040SearchFlg() {
        return usr040SearchFlg__;
    }
    /**
     * <p>usr040SearchFlg をセットします。
     * @param usr040SearchFlg usr040SearchFlg
     */
    public void setUsr040SearchFlg(int usr040SearchFlg) {
        usr040SearchFlg__ = usr040SearchFlg;
    }
    /**
     * <p>usr040CsvExportKbn を取得します。
     * @return usr040CsvExportKbn
     */
    public String getUsr040CsvExportKbn() {
        return usr040CsvExportKbn__;
    }
    /**
     * <p>usr040CsvExportKbn をセットします。
     * @param usr040CsvExportKbn usr040CsvExportKbn
     */
    public void setUsr040CsvExportKbn(String usr040CsvExportKbn) {
        usr040CsvExportKbn__ = usr040CsvExportKbn;
    }
    /**
     * <p>usrLabelSetKbn を取得します。
     * @return usrLabelSetKbn
     */
    public String getUsrLabelSetKbn() {
        return usrLabelSetKbn__;
    }
    /**
     * <p>usrLabelSetKbn をセットします。
     * @param usrLabelSetKbn usrLabelSetKbn
     */
    public void setUsrLabelSetKbn(String usrLabelSetKbn) {
        usrLabelSetKbn__ = usrLabelSetKbn;
    }
    /**
     * <p>adminKbn を取得します。
     * @return adminKbn
     */
    public int getAdminKbn() {
        return adminKbn__;
    }
    /**
     * <p>adminKbn をセットします。
     * @param adminKbn adminKbn
     */
    public void setAdminKbn(int adminKbn) {
        adminKbn__ = adminKbn;
    }
    /**
     * <p>sch010SelectUsrKbn を取得します。
     * @return sch010SelectUsrKbn
     */
    public String getSch010SelectUsrKbn() {
        return sch010SelectUsrKbn__;
    }
    /**
     * <p>sch010SelectUsrKbn をセットします。
     * @param sch010SelectUsrKbn sch010SelectUsrKbn
     */
    public void setSch010SelectUsrKbn(String sch010SelectUsrKbn) {
        sch010SelectUsrKbn__ = sch010SelectUsrKbn;
    }
    /**
     * <p>sch010SelectUsrSid を取得します。
     * @return sch010SelectUsrSid
     */
    public String getSch010SelectUsrSid() {
        return sch010SelectUsrSid__;
    }
    /**
     * <p>sch010SelectUsrSid をセットします。
     * @param sch010SelectUsrSid sch010SelectUsrSid
     */
    public void setSch010SelectUsrSid(String sch010SelectUsrSid) {
        sch010SelectUsrSid__ = sch010SelectUsrSid;
    }

    /**
     * <p>cmd を取得します。
     * @return cmd
     */
    public String getCmd() {
        return cmd__;
    }
    /**
     * <p>cmd をセットします。
     * @param cmd cmd
     */
    public void setCmd(String cmd) {
        cmd__ = cmd;
    }
    /**
     * <p>sch010DspDate を取得します。
     * @return sch010DspDate
     */
    public String getSch010DspDate() {
        return sch010DspDate__;
    }
    /**
     * <p>sch010DspDate をセットします。
     * @param sch010DspDate sch010DspDate
     */
    public void setSch010DspDate(String sch010DspDate) {
        sch010DspDate__ = sch010DspDate;
    }
    /**
     * <p>usr040webmail を取得します。
     * @return usr040webmail
     */
    public int getUsr040webmail() {
        return usr040webmail__;
    }
    /**
     * <p>usr040webmail をセットします。
     * @param usr040webmail usr040webmail
     */
    public void setUsr040webmail(int usr040webmail) {
        usr040webmail__ = usr040webmail;
    }
    /**
     * <p>usr040webmailAddress を取得します。
     * @return usr040webmailAddress
     */
    public String getUsr040webmailAddress() {
        return usr040webmailAddress__;
    }
    /**
     * <p>usr040webmailAddress をセットします。
     * @param usr040webmailAddress usr040webmailAddress
     */
    public void setUsr040webmailAddress(String usr040webmailAddress) {
        usr040webmailAddress__ = usr040webmailAddress;
    }
    /**
     * <p>usr040webmailType を取得します。
     * @return usr040webmailType
     */
    public int getUsr040webmailType() {
        return usr040webmailType__;
    }
    /**
     * <p>usr040webmailType をセットします。
     * @param usr040webmailType usr040webmailType
     */
    public void setUsr040webmailType(int usr040webmailType) {
        usr040webmailType__ = usr040webmailType;
    }
    /**
     * <p>usr040webmailSelectAddress を取得します。
     * @return usr040webmailSelectAddress
     */
    public String[] getUsr040webmailSelectAddress() {
        return usr040webmailSelectAddress__;
    }
    /**
     * <p>usr040webmailSelectAddress をセットします。
     * @param usr040webmailSelectAddress usr040webmailSelectAddress
     */
    public void setUsr040webmailSelectAddress(String[] usr040webmailSelectAddress) {
        usr040webmailSelectAddress__ = usr040webmailSelectAddress;
    }

    /**
     * @return sortKeyLabelList
     */
    public List<LabelValueBean> getSortKeyLabelList() {
        return sortKeyLabelList__;
    }
    /**
     * @param sortKeyLabelList 設定する sortKeyLabelList
     */
    public void setSortKeyLabelList(List<LabelValueBean> sortKeyLabelList) {
        sortKeyLabelList__ = sortKeyLabelList;
    }
    /**
     * @return usr040orderKey2
     */
    public int getUsr040orderKey2() {
        return usr040orderKey2__;
    }
    /**
     * @param usr040orderKey2 設定する usr040orderKey2
     */
    public void setUsr040orderKey2(int usr040orderKey2) {
        usr040orderKey2__ = usr040orderKey2;
    }
    /**
     * @return usr040sortKey2
     */
    public int getUsr040sortKey2() {
        return usr040sortKey2__;
    }
    /**
     * @param usr040sortKey2 設定する usr040sortKey2
     */
    public void setUsr040sortKey2(int usr040sortKey2) {
        usr040sortKey2__ = usr040sortKey2;
    }
    /**
     * @return usr040DspFlg
     */
    public int getUsr040DspFlg() {
        return usr040DspFlg__;
    }
    /**
     * @param usr040DspFlg 設定する usr040DspFlg
     */
    public void setUsr040DspFlg(int usr040DspFlg) {
        usr040DspFlg__ = usr040DspFlg;
    }
    /**
     * @return usr040orderKey2Save
     */
    public int getUsr040orderKey2Save() {
        return usr040orderKey2Save__;
    }
    /**
     * @param usr040orderKey2Save 設定する usr040orderKey2Save
     */
    public void setUsr040orderKey2Save(int usr040orderKey2Save) {
        usr040orderKey2Save__ = usr040orderKey2Save;
    }
    /**
     * @return usr040orderKeySave
     */
    public int getUsr040orderKeySave() {
        return usr040orderKeySave__;
    }
    /**
     * @param usr040orderKeySave 設定する usr040orderKeySave
     */
    public void setUsr040orderKeySave(int usr040orderKeySave) {
        usr040orderKeySave__ = usr040orderKeySave;
    }
    /**
     * @return usr040sortKey2Save
     */
    public int getUsr040sortKey2Save() {
        return usr040sortKey2Save__;
    }
    /**
     * @param usr040sortKey2Save 設定する usr040sortKey2Save
     */
    public void setUsr040sortKey2Save(int usr040sortKey2Save) {
        usr040sortKey2Save__ = usr040sortKey2Save;
    }
    /**
     * @return usr040sortKeySave
     */
    public int getUsr040sortKeySave() {
        return usr040sortKeySave__;
    }
    /**
     * @param usr040sortKeySave 設定する usr040sortKeySave
     */
    public void setUsr040sortKeySave(int usr040sortKeySave) {
        usr040sortKeySave__ = usr040sortKeySave;
    }
    /**
     * @return usr040saveFlg
     */
    public int getUsr040saveFlg() {
        return usr040saveFlg__;
    }
    /**
     * @param usr040saveFlg 設定する usr040saveFlg
     */
    public void setUsr040saveFlg(int usr040saveFlg) {
        usr040saveFlg__ = usr040saveFlg;
    }
    /**
     * @return usr040DetailExeFlg
     */
    public int getUsr040DetailExeFlg() {
        return usr040DetailExeFlg__;
    }
    /**
     * @param usr040DetailExeFlg 設定する usr040DetailExeFlg
     */
    public void setUsr040DetailExeFlg(int usr040DetailExeFlg) {
        usr040DetailExeFlg__ = usr040DetailExeFlg;
    }
    /**
     * <p>usr040selectLabel を取得します。
     * @return usr040selectLabel
     */
    public String[] getUsr040selectLabel() {
        return usr040selectLabel__;
    }
    /**
     * <p>usr040selectLabel をセットします。
     * @param usr040selectLabel usr040selectLabel
     */
    public void setUsr040selectLabel(String[] usr040selectLabel) {
        usr040selectLabel__ = usr040selectLabel;
    }
    /**
     * <p>usr040selectUser を取得します。
     * @return usr040selectUser
     */
    public String[] getUsr040selectUser() {
        return usr040selectUser__;
    }
    /**
     * <p>usr040selectUser をセットします。
     * @param usr040selectUser usr040selectUser
     */
    public void setUsr040selectUser(String[] usr040selectUser) {
        usr040selectUser__ = usr040selectUser;
    }
    /**
     * <p>usr040labSid を取得します。
     * @return usr040labSid
     */
    public String[] getUsr040labSid() {
        return usr040labSid__;
    }
    /**
     * <p>usr040labSid をセットします。
     * @param usr040labSid usr040labSid
     */
    public void setUsr040labSid(String[] usr040labSid) {
        usr040labSid__ = usr040labSid;
    }
    /**
     * <p>usr040labSidSave を取得します。
     * @return usr040labSidSave
     */
    public String[] getUsr040labSidSave() {
        return usr040labSidSave__;
    }
    /**
     * <p>usr040labSidSave をセットします。
     * @param usr040labSidSave usr040labSidSave
     */
    public void setUsr040labSidSave(String[] usr040labSidSave) {
        usr040labSidSave__ = usr040labSidSave;
    }
    /**
     * <p>usrLabelEditKbn を取得します。
     * @return usrLabelEditKbn
     */
    public String getUsrLabelEditKbn() {
        return usrLabelEditKbn__;
    }
    /**
     * <p>usrLabelEditKbn をセットします。
     * @param usrLabelEditKbn usrLabelEditKbn
     */
    public void setUsrLabelEditKbn(String usrLabelEditKbn) {
        usrLabelEditKbn__ = usrLabelEditKbn;
    }
    /**
     * <p>scrollPosition を取得します。
     * @return scrollPosition
     */
    public String getScrollPosition() {
        return scrollPosition__;
    }
    /**
     * <p>scrollPosition をセットします。
     * @param scrollPosition scrollPosition
     */
    public void setScrollPosition(String scrollPosition) {
        scrollPosition__ = scrollPosition;
    }
    /**
     * <p>usr040AtskList を取得します。
     * @return usr040AtskList
     */
    public List<CmnUsrmInfModel> getUsr040AtskList() {
        return usr040AtskList__;
    }
    /**
     * <p>usr040AtskList をセットします。
     * @param usr040AtskList usr040AtskList
     */
    public void setUsr040AtskList(List<CmnUsrmInfModel> usr040AtskList) {
        usr040AtskList__ = usr040AtskList;
    }
    /**
     * <p>usr040BccList を取得します。
     * @return usr040BccList
     */
    public List<CmnUsrmInfModel> getUsr040BccList() {
        return usr040BccList__;
    }
    /**
     * <p>usr040BccList をセットします。
     * @param usr040BccList usr040BccList
     */
    public void setUsr040BccList(List<CmnUsrmInfModel> usr040BccList) {
        usr040BccList__ = usr040BccList;
    }
    /**
     * <p>usr040CcList を取得します。
     * @return usr040CcList
     */
    public List<CmnUsrmInfModel> getUsr040CcList() {
        return usr040CcList__;
    }
    /**
     * <p>usr040CcList をセットします。
     * @param usr040CcList usr040CcList
     */
    public void setUsr040CcList(List<CmnUsrmInfModel> usr040CcList) {
        usr040CcList__ = usr040CcList;
    }
    /**
     * <p>usr040DelUsrSid を取得します。
     * @return usr040DelUsrSid
     */
    public int getUsr040DelUsrSid() {
        return usr040DelUsrSid__;
    }
    /**
     * <p>usr040DelUsrSid をセットします。
     * @param usr040DelUsrSid usr040DelUsrSid
     */
    public void setUsr040DelUsrSid(int usr040DelUsrSid) {
        usr040DelUsrSid__ = usr040DelUsrSid;
    }
    /**
     * <p>usr040SendMailMode を取得します。
     * @return usr040SendMailMode
     */
    public int getUsr040SendMailMode() {
        return usr040SendMailMode__;
    }
    /**
     * <p>usr040SendMailMode をセットします。
     * @param usr040SendMailMode usr040SendMailMode
     */
    public void setUsr040SendMailMode(int usr040SendMailMode) {
        usr040SendMailMode__ = usr040SendMailMode;
    }
    /**
     * <p>usr040SidsAtsk を取得します。
     * @return usr040SidsAtsk
     */
    public String[] getUsr040SidsAtsk() {
        return usr040SidsAtsk__;
    }
    /**
     * <p>usr040SidsAtsk をセットします。
     * @param usr040SidsAtsk usr040SidsAtsk
     */
    public void setUsr040SidsAtsk(String[] usr040SidsAtsk) {
        usr040SidsAtsk__ = usr040SidsAtsk;
    }
    /**
     * <p>usr040SidsBcc を取得します。
     * @return usr040SidsBcc
     */
    public String[] getUsr040SidsBcc() {
        return usr040SidsBcc__;
    }
    /**
     * <p>usr040SidsBcc をセットします。
     * @param usr040SidsBcc usr040SidsBcc
     */
    public void setUsr040SidsBcc(String[] usr040SidsBcc) {
        usr040SidsBcc__ = usr040SidsBcc;
    }
    /**
     * <p>usr040SidsCc を取得します。
     * @return usr040SidsCc
     */
    public String[] getUsr040SidsCc() {
        return usr040SidsCc__;
    }
    /**
     * <p>usr040SidsCc をセットします。
     * @param usr040SidsCc usr040SidsCc
     */
    public void setUsr040SidsCc(String[] usr040SidsCc) {
        usr040SidsCc__ = usr040SidsCc;
    }

    /**
     * <p>usr040UsrSid を取得します。
     * @return usr040UsrSid
     */
    public int getUsr040UsrSid() {
        return usr040UsrSid__;
    }

    /**
     * <p>usr040UsrSid をセットします。
     * @param usr040UsrSid usr040UsrSid
     */
    public void setUsr040UsrSid(int usr040UsrSid) {
        usr040UsrSid__ = usr040UsrSid;
    }

    /**
     * <p>usr040AddressKbn を取得します。
     * @return usr040AddressKbn
     */
    public String getUsr040AddressKbn() {
        return usr040AddressKbn__;
    }

    /**
     * <p>usr040AddressKbn をセットします。
     * @param usr040AddressKbn usr040AddressKbn
     */
    public void setUsr040AddressKbn(String usr040AddressKbn) {
        usr040AddressKbn__ = usr040AddressKbn;
    }

    /**
     * <p>usr040entranceYearFr を取得します。
     * @return usr040entranceYearFr
     */
    public String getUsr040entranceYearFr() {
        return usr040entranceYearFr__;
    }

    /**
     * <p>usr040entranceYearFr をセットします。
     * @param usr040entranceYearFr usr040entranceYearFr
     */
    public void setUsr040entranceYearFr(String usr040entranceYearFr) {
        usr040entranceYearFr__ = usr040entranceYearFr;
    }

    /**
     * <p>usr040entranceMonthFr を取得します。
     * @return usr040entranceMonthFr
     */
    public String getUsr040entranceMonthFr() {
        return usr040entranceMonthFr__;
    }

    /**
     * <p>usr040entranceMonthFr をセットします。
     * @param usr040entranceMonthFr usr040entranceMonthFr
     */
    public void setUsr040entranceMonthFr(String usr040entranceMonthFr) {
        usr040entranceMonthFr__ = usr040entranceMonthFr;
    }

    /**
     * <p>usr040entranceDayFr を取得します。
     * @return usr040entranceDayFr
     */
    public String getUsr040entranceDayFr() {
        return usr040entranceDayFr__;
    }

    /**
     * <p>usr040entranceDayFr をセットします。
     * @param usr040entranceDayFr usr040entranceDayFr
     */
    public void setUsr040entranceDayFr(String usr040entranceDayFr) {
        usr040entranceDayFr__ = usr040entranceDayFr;
    }

    /**
     * <p>usr040entranceYearTo を取得します。
     * @return usr040entranceYearTo
     */
    public String getUsr040entranceYearTo() {
        return usr040entranceYearTo__;
    }

    /**
     * <p>usr040entranceYearTo をセットします。
     * @param usr040entranceYearTo usr040entranceYearTo
     */
    public void setUsr040entranceYearTo(String usr040entranceYearTo) {
        usr040entranceYearTo__ = usr040entranceYearTo;
    }

    /**
     * <p>usr040entranceMonthTo を取得します。
     * @return usr040entranceMonthTo
     */
    public String getUsr040entranceMonthTo() {
        return usr040entranceMonthTo__;
    }

    /**
     * <p>usr040entranceMonthTo をセットします。
     * @param usr040entranceMonthTo usr040entranceMonthTo
     */
    public void setUsr040entranceMonthTo(String usr040entranceMonthTo) {
        usr040entranceMonthTo__ = usr040entranceMonthTo;
    }

    /**
     * <p>usr040entranceDayTo を取得します。
     * @return usr040entranceDayTo
     */
    public String getUsr040entranceDayTo() {
        return usr040entranceDayTo__;
    }

    /**
     * <p>usr040entranceDayTo をセットします。
     * @param usr040entranceDayTo usr040entranceDayTo
     */
    public void setUsr040entranceDayTo(String usr040entranceDayTo) {
        usr040entranceDayTo__ = usr040entranceDayTo;
    }

    /**
     * <p>usr040seibetu を取得します。
     * @return usr040seibetu
     */
    public String getUsr040seibetu() {
        return usr040seibetu__;
    }

    /**
     * <p>usr040seibetu をセットします。
     * @param usr040seibetu usr040seibetu
     */
    public void setUsr040seibetu(String usr040seibetu) {
        usr040seibetu__ = usr040seibetu;
    }

    /**
     * <p>usr040entranceYearFrSave を取得します。
     * @return usr040entranceYearFrSave
     */
    public String getUsr040entranceYearFrSave() {
        return usr040entranceYearFrSave__;
    }

    /**
     * <p>usr040entranceYearFrSave をセットします。
     * @param usr040entranceYearFrSave usr040entranceYearFrSave
     */
    public void setUsr040entranceYearFrSave(String usr040entranceYearFrSave) {
        usr040entranceYearFrSave__ = usr040entranceYearFrSave;
    }

    /**
     * <p>usr040entranceMonthFrSave を取得します。
     * @return usr040entranceMonthFrSave
     */
    public String getUsr040entranceMonthFrSave() {
        return usr040entranceMonthFrSave__;
    }

    /**
     * <p>usr040entranceMonthFrSave をセットします。
     * @param usr040entranceMonthFrSave usr040entranceMonthFrSave
     */
    public void setUsr040entranceMonthFrSave(String usr040entranceMonthFrSave) {
        usr040entranceMonthFrSave__ = usr040entranceMonthFrSave;
    }

    /**
     * <p>usr040entranceDayFrSave を取得します。
     * @return usr040entranceDayFrSave
     */
    public String getUsr040entranceDayFrSave() {
        return usr040entranceDayFrSave__;
    }

    /**
     * <p>usr040entranceDayFrSave をセットします。
     * @param usr040entranceDayFrSave usr040entranceDayFrSave
     */
    public void setUsr040entranceDayFrSave(String usr040entranceDayFrSave) {
        usr040entranceDayFrSave__ = usr040entranceDayFrSave;
    }

    /**
     * <p>usr040entranceYearToSave を取得します。
     * @return usr040entranceYearToSave
     */
    public String getUsr040entranceYearToSave() {
        return usr040entranceYearToSave__;
    }

    /**
     * <p>usr040entranceYearToSave をセットします。
     * @param usr040entranceYearToSave usr040entranceYearToSave
     */
    public void setUsr040entranceYearToSave(String usr040entranceYearToSave) {
        usr040entranceYearToSave__ = usr040entranceYearToSave;
    }

    /**
     * <p>usr040entranceMonthToSave を取得します。
     * @return usr040entranceMonthToSave
     */
    public String getUsr040entranceMonthToSave() {
        return usr040entranceMonthToSave__;
    }

    /**
     * <p>usr040entranceMonthToSave をセットします。
     * @param usr040entranceMonthToSave usr040entranceMonthToSave
     */
    public void setUsr040entranceMonthToSave(String usr040entranceMonthToSave) {
        usr040entranceMonthToSave__ = usr040entranceMonthToSave;
    }

    /**
     * <p>usr040entranceDayToSave を取得します。
     * @return usr040entranceDayToSave
     */
    public String getUsr040entranceDayToSave() {
        return usr040entranceDayToSave__;
    }

    /**
     * <p>usr040entranceDayToSave をセットします。
     * @param usr040entranceDayToSave usr040entranceDayToSave
     */
    public void setUsr040entranceDayToSave(String usr040entranceDayToSave) {
        usr040entranceDayToSave__ = usr040entranceDayToSave;
    }

    /**
     * <p>usr040seibetuSave を取得します。
     * @return usr040seibetuSave
     */
    public String getUsr040seibetuSave() {
        return usr040seibetuSave__;
    }

    /**
     * <p>usr040seibetuSave をセットします。
     * @param usr040seibetuSave usr040seibetuSave
     */
    public void setUsr040seibetuSave(String usr040seibetuSave) {
        usr040seibetuSave__ = usr040seibetuSave;
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
     * <p>usr040entranceYearFrLabel を取得します。
     * @return usr040entranceYearFrLabel
     */
    public List<LabelValueBean> getUsr040entranceYearFrLabel() {
        return usr040entranceYearFrLabel__;
    }

    /**
     * <p>usr040entranceYearFrLabel をセットします。
     * @param usr040entranceYearFrLabel usr040entranceYearFrLabel
     */
    public void setUsr040entranceYearFrLabel(
            List<LabelValueBean> usr040entranceYearFrLabel) {
        usr040entranceYearFrLabel__ = usr040entranceYearFrLabel;
    }

    /**
     * <p>usr040entranceMonthFrLabel を取得します。
     * @return usr040entranceMonthFrLabel
     */
    public List<LabelValueBean> getUsr040entranceMonthFrLabel() {
        return usr040entranceMonthFrLabel__;
    }

    /**
     * <p>usr040entranceMonthFrLabel をセットします。
     * @param usr040entranceMonthFrLabel usr040entranceMonthFrLabel
     */
    public void setUsr040entranceMonthFrLabel(
            List<LabelValueBean> usr040entranceMonthFrLabel) {
        usr040entranceMonthFrLabel__ = usr040entranceMonthFrLabel;
    }

    /**
     * <p>usr040entranceDayFrLabel を取得します。
     * @return usr040entranceDayFrLabel
     */
    public List<LabelValueBean> getUsr040entranceDayFrLabel() {
        return usr040entranceDayFrLabel__;
    }

    /**
     * <p>usr040entranceDayFrLabel をセットします。
     * @param usr040entranceDayFrLabel usr040entranceDayFrLabel
     */
    public void setUsr040entranceDayFrLabel(
            List<LabelValueBean> usr040entranceDayFrLabel) {
        usr040entranceDayFrLabel__ = usr040entranceDayFrLabel;
    }

    /**
     * <p>usr040CaegoryLabelList を取得します。
     * @return usr040CaegoryLabelList
     */
    public ArrayList<UsrCategoryLabelModel> getUsr040CaegoryLabelList() {
        return usr040CaegoryLabelList__;
    }

    /**
     * <p>usr040CaegoryLabelList をセットします。
     * @param usr040CaegoryLabelList usr040CaegoryLabelList
     */
    public void setUsr040CaegoryLabelList(
            ArrayList<UsrCategoryLabelModel> usr040CaegoryLabelList) {
        usr040CaegoryLabelList__ = usr040CaegoryLabelList;
    }

    /**
     * <p>usr040CategoryOpenFlg を取得します。
     * @return usr040CategoryOpenFlg
     */
    public String[] getUsr040CategoryOpenFlg() {
        return usr040CategoryOpenFlg__;
    }

    /**
     * <p>usr040CategoryOpenFlg をセットします。
     * @param usr040CategoryOpenFlg usr040CategoryOpenFlg
     */
    public void setUsr040CategoryOpenFlg(String[] usr040CategoryOpenFlg) {
        usr040CategoryOpenFlg__ = usr040CategoryOpenFlg;
    }

    /**
     * <p>usr040CategorySetInitFlg を取得します。
     * @return usr040CategorySetInitFlg
     */
    public int getUsr040CategorySetInitFlg() {
        return usr040CategorySetInitFlg__;
    }

    /**
     * <p>usr040CategorySetInitFlg をセットします。
     * @param usr040CategorySetInitFlg usr040CategorySetInitFlg
     */
    public void setUsr040CategorySetInitFlg(int usr040CategorySetInitFlg) {
        usr040CategorySetInitFlg__ = usr040CategorySetInitFlg;
    }

    /**
     * <p>usr040GrpSearchGName を取得します。
     * @return usr040GrpSearchGName
     */
    public String getUsr040GrpSearchGName() {
        return usr040GrpSearchGName__;
    }

    /**
     * <p>usr040GrpSearchGName をセットします。
     * @param usr040GrpSearchGName usr040GrpSearchGName
     */
    public void setUsr040GrpSearchGName(String usr040GrpSearchGName) {
        usr040GrpSearchGName__ = usr040GrpSearchGName;
    }

    /**
     * <p>usr040GrpSearchGId を取得します。
     * @return usr040GrpSearchGId
     */
    public String getUsr040GrpSearchGId() {
        return usr040GrpSearchGId__;
    }

    /**
     * <p>usr040GrpSearchGId をセットします。
     * @param usr040GrpSearchGId usr040GrpSearchGId
     */
    public void setUsr040GrpSearchGId(String usr040GrpSearchGId) {
        usr040GrpSearchGId__ = usr040GrpSearchGId;
    }

    /**
     * <p>usr040GrpSearchGNameSave を取得します。
     * @return usr040GrpSearchGNameSave
     */
    public String getUsr040GrpSearchGNameSave() {
        return usr040GrpSearchGNameSave__;
    }

    /**
     * <p>usr040GrpSearchGNameSave をセットします。
     * @param usr040GrpSearchGNameSave usr040GrpSearchGNameSave
     */
    public void setUsr040GrpSearchGNameSave(String usr040GrpSearchGNameSave) {
        usr040GrpSearchGNameSave__ = usr040GrpSearchGNameSave;
    }

    /**
     * <p>usr040GrpSearchGIdSave を取得します。
     * @return usr040GrpSearchGIdSave
     */
    public String getUsr040GrpSearchGIdSave() {
        return usr040GrpSearchGIdSave__;
    }

    /**
     * <p>usr040GrpSearchGIdSave をセットします。
     * @param usr040GrpSearchGIdSave usr040GrpSearchGIdSave
     */
    public void setUsr040GrpSearchGIdSave(String usr040GrpSearchGIdSave) {
        usr040GrpSearchGIdSave__ = usr040GrpSearchGIdSave;
    }

    /**
     * <p>usr040Keyword を取得します。
     * @return usr040Keyword
     */
    public String getUsr040Keyword() {
        return usr040Keyword__;
    }

    /**
     * <p>usr040Keyword をセットします。
     * @param usr040Keyword usr040Keyword
     */
    public void setUsr040Keyword(String usr040Keyword) {
        usr040Keyword__ = usr040Keyword;
    }

    /**
     * <p>usr040KeyKbnShainno を取得します。
     * @return usr040KeyKbnShainno
     */
    public int getUsr040KeyKbnShainno() {
        return usr040KeyKbnShainno__;
    }

    /**
     * <p>usr040KeyKbnShainno をセットします。
     * @param usr040KeyKbnShainno usr040KeyKbnShainno
     */
    public void setUsr040KeyKbnShainno(int usr040KeyKbnShainno) {
        usr040KeyKbnShainno__ = usr040KeyKbnShainno;
    }

    /**
     * <p>usr040KeyKbnName を取得します。
     * @return usr040KeyKbnName
     */
    public int getUsr040KeyKbnName() {
        return usr040KeyKbnName__;
    }

    /**
     * <p>usr040KeyKbnName をセットします。
     * @param usr040KeyKbnName usr040KeyKbnName
     */
    public void setUsr040KeyKbnName(int usr040KeyKbnName) {
        usr040KeyKbnName__ = usr040KeyKbnName;
    }

    /**
     * <p>usr040KeyKbnNameKn を取得します。
     * @return usr040KeyKbnNameKn
     */
    public int getUsr040KeyKbnNameKn() {
        return usr040KeyKbnNameKn__;
    }

    /**
     * <p>usr040KeyKbnNameKn をセットします。
     * @param usr040KeyKbnNameKn usr040KeyKbnNameKn
     */
    public void setUsr040KeyKbnNameKn(int usr040KeyKbnNameKn) {
        usr040KeyKbnNameKn__ = usr040KeyKbnNameKn;
    }

    /**
     * <p>usr040KeyKbnMail を取得します。
     * @return usr040KeyKbnMail
     */
    public int getUsr040KeyKbnMail() {
        return usr040KeyKbnMail__;
    }

    /**
     * <p>usr040KeyKbnMail をセットします。
     * @param usr040KeyKbnMail usr040KeyKbnMail
     */
    public void setUsr040KeyKbnMail(int usr040KeyKbnMail) {
        usr040KeyKbnMail__ = usr040KeyKbnMail;
    }

    /**
     * <p>usr040KeyKbnTel を取得します。
     * @return usr040KeyKbnTel
     */
    public int getUsr040KeyKbnTel() {
        return usr040KeyKbnTel__;
    }

    /**
     * <p>usr040KeyKbnTel をセットします。
     * @param usr040KeyKbnTel usr040KeyKbnTel
     */
    public void setUsr040KeyKbnTel(int usr040KeyKbnTel) {
        usr040KeyKbnTel__ = usr040KeyKbnTel;
    }

    /**
     * <p>usr040KeywordSave を取得します。
     * @return usr040KeywordSave
     */
    public String getUsr040KeywordSave() {
        return usr040KeywordSave__;
    }

    /**
     * <p>usr040KeywordSave をセットします。
     * @param usr040KeywordSave usr040KeywordSave
     */
    public void setUsr040KeywordSave(String usr040KeywordSave) {
        usr040KeywordSave__ = usr040KeywordSave;
    }

    /**
     * <p>usr040KeyKbnShainnoSave を取得します。
     * @return usr040KeyKbnShainnoSave
     */
    public int getUsr040KeyKbnShainnoSave() {
        return usr040KeyKbnShainnoSave__;
    }

    /**
     * <p>usr040KeyKbnShainnoSave をセットします。
     * @param usr040KeyKbnShainnoSave usr040KeyKbnShainnoSave
     */
    public void setUsr040KeyKbnShainnoSave(int usr040KeyKbnShainnoSave) {
        usr040KeyKbnShainnoSave__ = usr040KeyKbnShainnoSave;
    }

    /**
     * <p>usr040KeyKbnNameSave を取得します。
     * @return usr040KeyKbnNameSave
     */
    public int getUsr040KeyKbnNameSave() {
        return usr040KeyKbnNameSave__;
    }

    /**
     * <p>usr040KeyKbnNameSave をセットします。
     * @param usr040KeyKbnNameSave usr040KeyKbnNameSave
     */
    public void setUsr040KeyKbnNameSave(int usr040KeyKbnNameSave) {
        usr040KeyKbnNameSave__ = usr040KeyKbnNameSave;
    }

    /**
     * <p>usr040KeyKbnNameKnSave を取得します。
     * @return usr040KeyKbnNameKnSave
     */
    public int getUsr040KeyKbnNameKnSave() {
        return usr040KeyKbnNameKnSave__;
    }

    /**
     * <p>usr040KeyKbnNameKnSave をセットします。
     * @param usr040KeyKbnNameKnSave usr040KeyKbnNameKnSave
     */
    public void setUsr040KeyKbnNameKnSave(int usr040KeyKbnNameKnSave) {
        usr040KeyKbnNameKnSave__ = usr040KeyKbnNameKnSave;
    }

    /**
     * <p>usr040KeyKbnMailSave を取得します。
     * @return usr040KeyKbnMailSave
     */
    public int getUsr040KeyKbnMailSave() {
        return usr040KeyKbnMailSave__;
    }

    /**
     * <p>usr040KeyKbnMailSave をセットします。
     * @param usr040KeyKbnMailSave usr040KeyKbnMailSave
     */
    public void setUsr040KeyKbnMailSave(int usr040KeyKbnMailSave) {
        usr040KeyKbnMailSave__ = usr040KeyKbnMailSave;
    }

    /**
     * <p>usr040KeyKbnTelSave を取得します。
     * @return usr040KeyKbnTelSave
     */
    public int getUsr040KeyKbnTelSave() {
        return usr040KeyKbnTelSave__;
    }

    /**
     * <p>usr040KeyKbnTelSave をセットします。
     * @param usr040KeyKbnTelSave usr040KeyKbnTelSave
     */
    public void setUsr040KeyKbnTelSave(int usr040KeyKbnTelSave) {
        usr040KeyKbnTelSave__ = usr040KeyKbnTelSave;
    }

    /**
     * <p>usr040BaseUrl を取得します。
     * @return usr040BaseUrl
     */
    public String getUsr040BaseUrl() {
        return usr040BaseUrl__;
    }

    /**
     * <p>usr040BaseUrl をセットします。
     * @param usr040BaseUrl usr040BaseUrl
     */
    public void setUsr040BaseUrl(String usr040BaseUrl) {
        usr040BaseUrl__ = usr040BaseUrl;
    }

    /**
     * <p>usr040webmailCanUseAddress を取得します。
     * @return usr040webmailCanUseAddress
     * @see jp.groupsession.v2.usr.usr040.Usr040Form#usr040webmailCanUseAddress__
     */
    public boolean isUsr040webmailCanUseAddress() {
        return usr040webmailCanUseAddress__;
    }

    /**
     * <p>usr040webmailCanUseAddress をセットします。
     * @param usr040webmailCanUseAddress usr040webmailCanUseAddress
     * @see jp.groupsession.v2.usr.usr040.Usr040Form#usr040webmailCanUseAddress__
     */
    public void setUsr040webmailCanUseAddress(boolean usr040webmailCanUseAddress) {
        usr040webmailCanUseAddress__ = usr040webmailCanUseAddress;
    }
}