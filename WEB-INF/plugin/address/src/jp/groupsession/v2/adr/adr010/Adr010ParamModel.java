package jp.groupsession.v2.adr.adr010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.AdrCommonBiz;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.model.Adr010DetailModel;
import jp.groupsession.v2.adr.adr010.model.AdrCategoryLabelModel;
import jp.groupsession.v2.adr.adr330.model.Adr330ViewSearchModel;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.adr.model.AdrLabelModel;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] アドレス帳一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr010ParamModel extends AbstractParamModel {

    /** 処理モード */
    private int adr010cmdMode__ = Adr010Const.CMDMODE_TANTO;
    /** 検索実行フラグ */
    private int adr010searchFlg__ = 0;
    /** 編集アドレス帳SID */
    private int adr010EditAdrSid__ = 0;
    /** 並び順 */
    private int adr010orderKey__ = Adr010Const.ORDERKEY_ASC;
    /** ソートキー */
    private int adr010sortKey__ = Adr010Const.SORTKEY_UNAME;
    /** ページ */
    private int adr010page__ = 0;
    /** ページ上段 */
    private int adr010pageTop__ = 0;
    /** ページ下段 */
    private int adr010pageBottom__ = 0;
    /** アドレス帳登録処理区分 */
    private int adr020ProcMode__ = GSConstAddress.PROCMODE_ADD;
    /** アドレス帳確認フラグ */
    private int adr020viewFlg__ = 0;
    /** 戻り先画面指定 */
    private String adr020BackId__ = null;

    /** 企業コード */
    private String adr010code__ = null;
    /** 会社名 */
    private String adr010coName__ = null;
    /** 会社名カナ */
    private String adr010coNameKn__ = null;
    /** 支店・営業所名 */
    private String adr010coBaseName__ = null;
    /** 業種 */
    private int adr010atiSid__ = -1;
    /** 都道府県 */
    private int adr010tdfk__ = -1;
    /** 備考 */
    private String adr010biko__ = null;
    /** 企業コード(検索条件保持用) */
    private String adr010svCode__ = null;
    /** 会社名(検索条件保持用) */
    private String adr010svCoName__ = null;
    /** 会社名カナ(検索条件保持用) */
    private String adr010svCoNameKn__ = null;
    /** 支店・営業所名(検索条件保持用) */
    private String adr010svCoBaseName__ = null;
    /** 業種(検索条件保持用) */
    private int adr010svAtiSid__ = -1;
    /** 都道府県(検索条件保持用) */
    private int adr010svTdfk__ = -1;
    /** 備考(検索条件保持用) */
    private String adr010svBiko__ = null;
    /** プロジェクト区分 */
    private int projectKbnSv__ = GSConstAddress.PROTYPE_ADD;
    /** 状態 */
    private int statusKbnSv__ = GSConstAddress.STATUS_ALL;

    /** クリックカナ */
    private String adr010SearchKana__ = null;
    /** クリックカナ(検索条件保持用) */
    private String adr010svSearchKana__ = null;

    /** クリックカナ会社名 */
    private String adr010SearchComKana__ = null;
    /** クリックカナ会社名(検索条件保持用) */
    private String adr010svSearchComKana__ = null;

    /** 担当者グループ */
    private String adr010tantoGroup__ = "-2";
    /** 担当者ユーザ */
    private int adr010tantoUser__ = -2;
    /** 担当者グループ(検索条件保持用) */
    private int adr010svTantoGroup__ = -1;
    /** 担当者ユーザ(検索条件保持用) */
    private int adr010svTantoUser__ = -1;

    /** 氏名 姓 */
    private String adr010unameSei__ = null;
    /** 氏名 名 */
    private String adr010unameMei__ = null;
    /** 氏名カナ 姓 */
    private String adr010unameSeiKn__ = null;
    /** 氏名カナ 名 */
    private String adr010unameMeiKn__ = null;
    /** 会社名(詳細検索) */
    private String adr010detailCoName__ = null;
    /** 所属 */
    private String adr010syozoku__ = null;
    /** 役職 */
    private int adr010position__ = -1;
    /** E-MAIL */
    private String adr010mail__ = null;
    /** 担当者グループ(詳細検索) */
    private String adr010detailTantoGroup__ = "-1";
    /** 担当者ユーザ(詳細検索) */
    private int adr010detailTantoUser__ = -1;
    /** 業種(詳細検索) */
    private int adr010detailAtiSid__ = -1;
    /** 氏名 姓(検索条件保持用) */
    private String adr010svUnameSei__ = null;
    /** 氏名 名(検索条件保持用) */
    private String adr010svUnameMei__ = null;
    /** 氏名カナ 姓(検索条件保持用) */
    private String adr010svUnameSeiKn__ = null;
    /** 氏名カナ 名(検索条件保持用) */
    private String adr010svUnameMeiKn__ = null;
    /** 会社名(詳細検索 検索条件保持用) */
    private String adr010svDetailCoName__ = null;
    /** 所属(検索条件保持用) */
    private String adr010svSyozoku__ = null;
    /** 役職(検索条件保持用) */
    private int adr010svPosition__ = -1;
    /** E-MAIL(検索条件保持用) */
    private String adr010svMail__ = null;
    /** 担当者グループ(詳細検索 検索条件保持用) */
    private int adr010svDetailTantoGroup__ = -1;
    /** 担当者ユーザ(詳細検索 検索条件保持用) */
    private int adr010svDetailTantoUser__ = -1;
    /** 業種(詳細検索 検索条件保持用) */
    private int adr010svDetailAtiSid__ = -1;
    /** プロジェクトコンボ選択値(保存用) */
    private String selectingProjectSv__ = "-1";

    /** 担当者グループ(コンタクト履歴) */
    private String adr010tantoGroupContact__ = "-2";
    /** 担当者ユーザ(コンタクト履歴) */
    private int adr010tantoUserContact__ = -2;
    /** 担当者グループ(コンタクト履歴)(検索条件保持用) */
    private int adr010svTantoGroupContact__ = -1;
    /** 担当者ユーザ(コンタクト履歴)(検索条件保持用) */
    private int adr010svTantoUserContact__ = -1;
    /** 氏名 姓(コンタクト履歴) */
    private String adr010unameSeiContact__ = null;
    /** 氏名 名(コンタクト履歴) */
    private String adr010unameMeiContact__ = null;
    /** 氏名 姓(コンタクト履歴)(検索条件保持用) */
    private String adr010svUnameSeiContact__ = null;
    /** 氏名 名(コンタクト履歴)(検索条件保持用) */
    private String adr010svUnameMeiContact__ = null;
    /** 会社名(コンタクト履歴) */
    private String adr010CoNameContact__ = null;
    /** 会社名(コンタクト履歴 検索条件保持用) */
    private String adr010svCoNameContact__ = null;
    /** 拠点(コンタクト履歴) */
    private String adr010CoBaseNameContact__ = null;
    /** 拠点(コンタクト履歴) */
    private String adr010svCoBaseNameContact__ = null;
    /** プロジェクト(コンタクト履歴) */
    private int adr010ProjectContact__ = -1;
    /** プロジェクト(コンタクト履歴)(検索条件保持用) */
    private int adr010svProjectContact__ = -1;
    /** 添付ファイル区分(コンタクト履歴) */
    private String adr010TempFilekbnContact__ = String.valueOf(GSConstAddress.TEMPFILE_KBN_FREE);
    /** 添付ファイル区分(コンタクト履歴)(検索条件保持用) */
    private String adr010SvTempFilekbnContact__ = String.valueOf(GSConstAddress.TEMPFILE_KBN_FREE);
    /** from年(コンタクト履歴) */
    private String adr010SltYearFrContact__ = null;
    /** from月(コンタクト履歴) */
    private String adr010SltMonthFrContact__ = null;
    /** from日(コンタクト履歴) */
    private String adr010SltDayFrContact__ = null;
    /** to年(コンタクト履歴) */
    private String adr010SltYearToContact__ = null;
    /** to月(コンタクト履歴) */
    private String adr010SltMonthToContact__ = null;
    /** to日(コンタクト履歴) */
    private String adr010SltDayToContact__ = null;
    /** From年月日 */
    private String adr010SltFrContact__ = null;
    /** To年月日 */
    private String adr010SltToContact__ = null;
    /** from年(コンタクト履歴)(検索条件保持用) */
    private String adr010svSltYearFrContact__ = null;
    /** from月(コンタクト履歴)(検索条件保持用) */
    private String adr010svSltMonthFrContact__ = null;
    /** from日(コンタクト履歴)(検索条件保持用) */
    private String adr010svSltDayFrContact__ = null;
    /** to年(コンタクト履歴)(検索条件保持用) */
    private String adr010svSltYearToContact__ = null;
    /** to月(コンタクト履歴)(検索条件保持用) */
    private String adr010svSltMonthToContact__ = null;
    /** to日(コンタクト履歴)(検索条件保持用) */
    private String adr010svSltDayToContact__ = null;
    /** From年月日 (検索条件保持用)*/
    private String adr010svSltFrContact__ = null;
    /** To年月日 (検索条件保持用)*/
    private String adr010svSltToContact__ = null;
    /** 種別(コンタクト履歴) */
    private int adr010SyubetsuContact__ = GSConstAddress.NOT_SYUBETU;
    /** 種別(コンタクト履歴)(検索条件保持用) */
    private int adr010svSyubetsuContact__ = GSConstAddress.NOT_SYUBETU;
    /** 検索キーワード(コンタクト履歴) */
    private String adr010SearchWordContact__;
    /** 検索キーワード(コンタクト履歴)(検索条件保持用) */
    private String adr010svSearchWordContact__;
    /** キーワード検索区分(コンタクト履歴) */
    private String adr010KeyWordkbnContact__ = String.valueOf(GSConstAddress.KEY_WORD_KBN_AND);
    /** キーワード検索区分(コンタクト履歴)(検索条件保持用) */
    private String adr010SvKeyWordkbnContact__ = String.valueOf(GSConstAddress.KEY_WORD_KBN_AND);
    /** 検索対象(コンタクト履歴) */
    private String[] adr010SearchTargetContact__ = null;
    /** 検索対象(コンタクト履歴)(検索条件保持用) */
    private String[] adr010svSearchTargetContact__ = null;
    /** コンタクト履歴初期表示フラグ */
    private int adr010InitDspContactFlg__ = 0;
    /** コンタクト履歴日時指定無し */
    private int adr010dateNoKbn__ = 0;
    /** コンタクト履歴日時指定無し */
    private int adr010svdateNoKbn__ = 0;

    /** 管理者設定ボタン表示フラグ */
    private int adr010viewAdminBtn__ = 0;
    /** 役職情報ボタン表示フラグ */
    private int adr010viewYksBtn__ = 0;
    /** 業種情報ボタン表示フラグ */
    private int adr010viewGyosyuBtn__ = 0;
    /** 会社情報ボタン表示フラグ */
    private int adr010viewCompanyBtn__ = 0;
    /** ラベル設定ボタン表示フラグ */
    private int adr010viewLabelBtn__ = 0;
    /** エクスポートボタン表示フラグ */
    private int adr010viewExportBtn__ = 0;
    /** アドレス帳登録権限 */
    private int adr010AbleEdit__ = 0;

    /** ラベル選択 */
    private String[] adr010searchLabel__ = null;
    /** ラベル選択(検索条件保持用) */
    private String[] adr010svSearchLabel__ = null;

    /** 検索条件文字列 */
    private String adr010searchParamString__ = null;
    /** 検索条件文字列(ラベル) */
    private String adr010searchLabelString__ = null;
    /** 名前カナ一覧 */
    private List<String> adr010unameKanaList__ = null;
    /** 会社名カナ一覧 */
    private List<String> adr010cnameKanaList__ = null;

    /** 戻り先画面 */
    private int adr100backFlg__ = 0;
    /** 処理モード */
    private int adr110ProcMode__ = 0;
    /** 編集対象会社SID */
    private int adr110editAcoSid__ = 0;

    /** 業種コンボ */
    private List<LabelValueBean> atiCmbList__ = null;
    /** 都道府県コンボ */
    private List<LabelValueBean> tdfkCmbList__ = null;
    /** グループコンボ */
    private List<LabelValueBean> groupCmbList__ = null;
    /** ユーザコンボ */
    private List<UsrLabelValueBean> userCmbList__ = null;
    /** 役職コンボ */
    private List<LabelValueBean> positionCmbList__ = null;
    /** プロジェクトコンボ */
    private List<LabelValueBean> projectCmbList__ = null;
    /** プロジェクトコンボサイズ */
    private int projectCmbsize__ = 0;
    /** ページコンボ */
    private List<LabelValueBean> pageCmbList__ = null;

    /** カテゴリーラベルデータリスト */
    private ArrayList<AdrCategoryLabelModel> adr010CaegoryLabelList__ = null;

    /** 選択カテゴリ */
    private int adr010selectCategory__ = -1;


    /** 選択ラベル一覧 */
    private List<AdrLabelModel> selectLabelList__ = null;

    /** 検索結果一覧 */
    private List<Adr010DetailModel> detailList__ = null;

    /** ページ1 */
    private int adr160pageNum1__;
    /** ページ2 */
    private int adr160pageNum2__;
    /** ソート項目*/
    private String sortKey__ = String.valueOf(GSConstAddress.CONTACT_SORT_DATE);
    /** ソート順*/
    private String orderKey__ = String.valueOf(GSConst.ORDER_KEY_DESC);
    /** 戻り先画面指定 */
    private String adr110BackId__ = null;

    /** 遷移元 メイン個人設定:1 メイン管理者設定:1 その他:0*/
    private int backScreen__ = GSConstMain.BACK_PLUGIN;
    /** Webメール 連携判定 */
    private int adr010webmail__ = 0;
    /** Webメール メールアドレス */
    private String adr010webmailAddress__ = null;
    /** Webメール 連携判定 種別 */
    private int adr010webmailType__ = 0;
    /** Webメール 選択アドレス */
    private String[] adr010webmailSelectAddress__ = null;

    /** 役職POPUP 受け取り用 役職SID */
    private int adrPosition__ = 0;

    /** プロジェクト区分 */
    private int projectKbn__ = GSConstAddress.PROTYPE_ADD;
    /** 状態 */
    private int statusKbn__ = GSConstAddress.STATUS_ALL;
    /** プロジェクトコンボ選択値 */
    private String selectingProject__ = "-1";

    /** 選択アドレスチェック */
    private String[] adr010selectSid__;

    /** ポップアップ画面選択アドレス */
    private String[] adr010selectLabelSid__ = null;

    /** 送信区分 */
    private int adr010SendMailMode__ =  GSConst.SEND_KBN_ATESAKI;
    /** 削除アドレス帳SID */
    private int adr010DelAdrSid__ = 0;
    /** 単一選択アドレス帳SID */
    private int adr010AdrSid__ = 0;
    /** アドレス帳データ 宛先 */
    private List<AdrAddressModel> adr010AtskList__;
    /** 選択アドレスSID 宛先 */
    private String[] adr010SidsAtsk__;
    /** 選択アドレス 宛先 */
    private String[] adr010Atsk__;
    /** アドレス帳データ CC */
    private List<AdrAddressModel> adr010CcList__;
    /** 選択アドレスSID CC */
    private String[] adr010SidsCc__;
    /** 選択アドレス CC */
    private String[] adr010Cc__;
    /** アドレス帳データ BCC */
    private List<AdrAddressModel> adr010BccList__;
    /** 選択アドレスSID BCC */
    private String[] adr010SidsBcc__;
    /** 選択アドレス BCC */
    private String[] adr010Bcc__;
    /** 選択アドレス 種別 */
    private int adr010AdrType__ = 0;
    /** カテゴリー開閉フラグ   0:閉  1:開 */
    private String[] adr010CategoryOpenFlg__ = null;

    /** カテゴリー開閉初期設定フラグ */
    private int adr010CategorySetInitFlg__ = 0;

    /** 画像SID */
    private Long photoFileSid__ = null;

    /** アドレス帳管理画面用 検索モデル */
    private Adr330ViewSearchModel adr330searchBean__ = new Adr330ViewSearchModel();
    /** アドレス帳管理画面用 前回検索モデル */
    private Adr330ViewSearchModel adr330searchSVBean__ = new Adr330ViewSearchModel();
    /** アドレス帳管理画面用 検索済みフラグ */
    private int adr330searchFlg__ = 0;
    /** アドレス管理画面へのバックフラグ */
    private int adr330back__ = 0;


    /**
     * <br>[機  能] 共通メッセージフォームへのパラメータ設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージフォーム
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        //処理モード
        msgForm.addHiddenParam("adr010cmdMode", adr010cmdMode__);
        //検索実行フラグ
        msgForm.addHiddenParam("adr010searchFlg", adr010searchFlg__);
        //編集アドレス帳SID
        msgForm.addHiddenParam("adr010EditAdrSid", adr010EditAdrSid__);
        //並び順
        msgForm.addHiddenParam("adr010orderKey", adr010orderKey__);
        //ソートキー
        msgForm.addHiddenParam("adr010sortKey", adr010sortKey__);
        //ページ
        msgForm.addHiddenParam("adr010page", adr010page__);
        //ページ上段
        msgForm.addHiddenParam("adr010pageTop", adr010pageTop__);
        //ページ下段
        msgForm.addHiddenParam("adr010pageBottom", adr010pageBottom__);

        //企業コード
        msgForm.addHiddenParam("adr010code", adr010code__);
        //会社名
        msgForm.addHiddenParam("adr010coName", adr010coName__);
        //会社名カナ
        msgForm.addHiddenParam("adr010coNameKn", adr010coNameKn__);
        //支店・営業所名
        msgForm.addHiddenParam("adr010coBaseName", adr010coBaseName__);
        //業種
        msgForm.addHiddenParam("adr010atiSid", adr010atiSid__);
        //都道府県
        msgForm.addHiddenParam("adr010tdfk", adr010tdfk__);
        //備考
        msgForm.addHiddenParam("adr010biko", adr010biko__);
        //プロジェクト区分
        msgForm.addHiddenParam("projectKbn", projectKbn__);
        //プロジェクト状態区分
        msgForm.addHiddenParam("statusKbn", statusKbn__);
        //プロジェクト選択
        msgForm.addHiddenParam("selectingProject", selectingProject__);
        //企業コード(検索条件保持用)
        msgForm.addHiddenParam("adr010svCode", adr010svCode__);
        //会社名(検索条件保持用)
        msgForm.addHiddenParam("adr010svCoName", adr010svCoName__);
        //会社名カナ(検索条件保持用)
        msgForm.addHiddenParam("adr010svCoNameKn", adr010svCoNameKn__);
        //支店・営業所名(検索条件保持用)
        msgForm.addHiddenParam("adr010svCoBaseName", adr010svCoBaseName__);
        //業種(検索条件保持用)
        msgForm.addHiddenParam("adr010svAtiSid", adr010svAtiSid__);
        //都道府県(検索条件保持用)
        msgForm.addHiddenParam("adr010svTdfk", adr010svTdfk__);
        //備考(検索条件保持用)
        msgForm.addHiddenParam("adr010svBiko", adr010svBiko__);
        //プロジェクト区分(検索条件保持用)
        msgForm.addHiddenParam("projectKbnSv", projectKbnSv__);
        //プロジェクト状態区分(検索条件保持用)
        msgForm.addHiddenParam("statusKbnSv", statusKbnSv__);
        //プロジェクト選択(検索条件保持用)
        msgForm.addHiddenParam("selectingProjectSv", selectingProjectSv__);

        //クリックカナ
        msgForm.addHiddenParam("adr010SearchKana", adr010SearchKana__);
        //クリックカナ(検索条件保持用)
        msgForm.addHiddenParam("adr010svSearchKana", adr010svSearchKana__);

        //クリック会社名カナ
        msgForm.addHiddenParam("adr010SearchComKana", adr010SearchComKana__);
        //クリック会社名カナ(検索条件保持用)
        msgForm.addHiddenParam("adr010svSearchComKana", adr010svSearchComKana__);

        //担当者グループ
        msgForm.addHiddenParam("adr010tantoGroup", adr010tantoGroup__);
        //担当者ユーザ
        msgForm.addHiddenParam("adr010tantoUser", adr010tantoUser__);
        //担当者グループ(検索条件保持用)
        msgForm.addHiddenParam("adr010svTantoGroup", adr010svTantoGroup__);
        //担当者ユーザ(検索条件保持用)
        msgForm.addHiddenParam("adr010svTantoUser", adr010svTantoUser__);

        //氏名 姓
        msgForm.addHiddenParam("adr010unameSei", adr010unameSei__);
        //氏名 名
        msgForm.addHiddenParam("adr010unameMei", adr010unameMei__);
        //氏名カナ 姓
        msgForm.addHiddenParam("adr010unameSeiKn", adr010unameSeiKn__);
        //氏名カナ 名
        msgForm.addHiddenParam("adr010unameMeiKn", adr010unameMeiKn__);
        //会社名(詳細検索)
        msgForm.addHiddenParam("adr010detailCoName", adr010detailCoName__);
        //所属(詳細検索)
        msgForm.addHiddenParam("adr010syozoku", adr010syozoku__);
        //役職
        msgForm.addHiddenParam("adr010position", adr010position__);
        //E-MAIL
        msgForm.addHiddenParam("adr010mail", adr010mail__);
        //担当者グループ(詳細検索)
        msgForm.addHiddenParam("adr010detailTantoGroup", adr010detailTantoGroup__);
        //担当者ユーザ(詳細検索)
        msgForm.addHiddenParam("adr010detailTantoUser", adr010detailTantoUser__);
        //業種(詳細検索)
        msgForm.addHiddenParam("adr010detailAtiSid", adr010detailAtiSid__);
        //氏名 姓(検索条件保持用)
        msgForm.addHiddenParam("adr010svUnameSei", adr010svUnameSei__);
        //氏名 名(検索条件保持用)
        msgForm.addHiddenParam("adr010svUnameMei", adr010svUnameMei__);
        //氏名カナ 姓(検索条件保持用)
        msgForm.addHiddenParam("adr010svUnameSeiKn", adr010svUnameSeiKn__);
        //氏名カナ 名(検索条件保持用)
        msgForm.addHiddenParam("adr010svUnameMeiKn", adr010svUnameMeiKn__);
        //会社名(詳細検索 検索条件保持用)
        msgForm.addHiddenParam("adr010svDetailCoName", adr010svDetailCoName__);
        //所属(詳細検索 検索条件保持用)
        msgForm.addHiddenParam("adr010svSyozoku", adr010svSyozoku__);
        //役職(検索条件保持用)
        msgForm.addHiddenParam("adr010svPosition", adr010svPosition__);
        //E-MAIL(検索条件保持用)
        msgForm.addHiddenParam("adr010svMail", adr010svMail__);
        //担当者グループ(詳細検索 検索条件保持用)
        msgForm.addHiddenParam("adr010svDetailTantoGroup", adr010svDetailTantoGroup__);
        //担当者ユーザ(詳細検索 検索条件保持用)
        msgForm.addHiddenParam("adr010svDetailTantoUser", adr010svDetailTantoUser__);
        //業種(詳細検索 検索条件保持用)
        msgForm.addHiddenParam("adr010svDetailAtiSid", adr010svDetailAtiSid__);

        //担当者グループ(コンタクト履歴)
        msgForm.addHiddenParam("adr010tantoGroupContact", adr010tantoGroupContact__);
        //担当者ユーザ(コンタクト履歴)
        msgForm.addHiddenParam("adr010tantoUserContact", adr010tantoUserContact__);
        //担当者グループ(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svTantoGroupContact", adr010svTantoGroupContact__);
        //担当者ユーザ(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svTantoUserContact", adr010svTantoUserContact__);

        //氏名 姓(コンタクト履歴)
        msgForm.addHiddenParam("adr010unameSeiContact", adr010unameSeiContact__);
        //氏名 名(コンタクト履歴)
        msgForm.addHiddenParam("adr010unameMeiContact", adr010unameMeiContact__);
        //氏名 姓(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svTantoUserContact", adr010svUnameSeiContact__);
        //氏名 名(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svUnameMeiContact", adr010svUnameMeiContact__);
        //会社名(コンタクト履歴)
        msgForm.addHiddenParam("adr010CoNameContact", adr010CoNameContact__);
        //会社名(コンタクト履歴 (検索条件保持用)
        msgForm.addHiddenParam("adr010svCoNameContact", adr010svCoNameContact__);
        //拠点(コンタクト履歴)
        msgForm.addHiddenParam("adr010CoBaseNameContact", adr010CoBaseNameContact__);
        //拠点(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svCoBaseNameContact", adr010svCoBaseNameContact__);
        //プロジェクト(コンタクト履歴)
        msgForm.addHiddenParam("adr010ProjectContact", adr010ProjectContact__);
        //プロジェクト(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svProjectContact", adr010svProjectContact__);
        //添付ファイル区分(コンタクト履歴)
        msgForm.addHiddenParam("adr010TempFilekbnContact", adr010TempFilekbnContact__);
        //添付ファイル区分(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010SvTempFilekbnContact", adr010SvTempFilekbnContact__);
        //from年(コンタクト履歴)
        msgForm.addHiddenParam("adr010SltYearFrContact", adr010SltYearFrContact__);
        //from月(コンタクト履歴)
        msgForm.addHiddenParam("adr010SltMonthFrContact", adr010SltMonthFrContact__);
        //from日(コンタクト履歴)
        msgForm.addHiddenParam("adr010SltDayFrContact", adr010SltDayFrContact__);
        //to年(コンタクト履歴)
        msgForm.addHiddenParam("adr010SltYearToContact", adr010SltYearToContact__);
        //to月(コンタクト履歴)
        msgForm.addHiddenParam("adr010SltMonthToContact", adr010SltMonthToContact__);
        //to日(コンタクト履歴)
        msgForm.addHiddenParam("adr010SltDayToContact", adr010SltDayToContact__);
        //from年(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svSltYearFrContact", adr010svSltYearFrContact__);
        //from月(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svSltMonthFrContact", adr010svSltMonthFrContact__);
        //from日(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svSltDayFrContact", adr010svSltDayFrContact__);
        //to年(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svSltYearToContact", adr010svSltYearToContact__);
        //to月(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svSltMonthToContact", adr010svSltMonthToContact__);
        //to日(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svSltDayToContact", adr010svSltDayToContact__);
        //種別(コンタクト履歴)
        msgForm.addHiddenParam("adr010SyubetsuContact", adr010SyubetsuContact__);
        //種別(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svSyubetsuContact", adr010svSyubetsuContact__);
        //検索キーワード(コンタクト履歴)
        msgForm.addHiddenParam("adr010SearchWordContact", adr010SearchWordContact__);
        //検索キーワード(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svSearchWordContact", adr010svSearchWordContact__);
        //キーワード検索区分(コンタクト履歴)
        msgForm.addHiddenParam("adr010KeyWordkbnContact", adr010KeyWordkbnContact__);
        //キーワード検索区分(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010SvKeyWordkbnContact", adr010SvKeyWordkbnContact__);
        //検索対象(コンタクト履歴)
        msgForm.addHiddenParam("adr010SearchTargetContact", adr010SearchTargetContact__);
        //検索対象(コンタクト履歴)(検索条件保持用)
        msgForm.addHiddenParam("adr010svSearchTargetContact", adr010svSearchTargetContact__);
        //コンタクト履歴初期表示フラグ
        msgForm.addHiddenParam("adr010InitDspContactFlg", adr010InitDspContactFlg__);
        //コンタクト履歴日時指定無し
        msgForm.addHiddenParam("adr010dateNoKbn", adr010dateNoKbn__);
        //コンタクト履歴日時指定無し
        msgForm.addHiddenParam("adr010svdateNoKbn", adr010svdateNoKbn__);

        //ラベル選択
        msgForm.addHiddenParam("adr010searchLabel", adr010searchLabel__);
        //ラベル選択(検索条件保持用)
        msgForm.addHiddenParam("adr010svSearchLabel", adr010svSearchLabel__);

        //削除チェックボックス
        msgForm.addHiddenParam("adr010selectSid", adr010selectSid__);
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();

        switch (adr010cmdMode__) {
            case Adr010Const.CMDMODE_COMPANY :

                //企業コード
                AdrValidateUtil.validateTextField(errors, adr010code__, "adr010code",
                        gsMsg.getMessage(req, "address.7"),
                                        GSConstAddress.MAX_LENGTH_COMPANY_CODE, false);
                //会社名
                AdrValidateUtil.validateTextField(errors, adr010coName__, "adr010coName",
                        gsMsg.getMessage(req, "cmn.company.name"),
                                        GSConstAddress.MAX_LENGTH_COMPANY_NAME, false);
                //会社名カナ
                AdrValidateUtil.validateTextFieldKana(
                        errors, adr010coNameKn__, "adr010coNameKn",
                        gsMsg.getMessage(req, "address.9"),
                                        GSConstAddress.MAX_LENGTH_COMPANY_NAME_KN, false);
                //支店・営業所名
                AdrValidateUtil.validateTextField(
                        errors, adr010coBaseName__, "adr010coBaseName",
                        gsMsg.getMessage(req, "address.10"),
                                        GSConstAddress.MAX_LENGTH_COBASE_NAME, false);
                //備考
                AdrValidateUtil.validateTextIncludeComma(errors, adr010biko__, "adr010biko",
                                                gsMsg.getMessage(req, "cmn.memo"),
                                                GSConstAddress.MAX_LENGTH_ADR_BIKO, false);
                break;
            case Adr010Const.CMDMODE_NAME :
                break;
            case Adr010Const.CMDMODE_TANTO :

                //マイグループかつユーザ選択なし 担当者
                if (AdrCommonBiz.isMyGroupSid(getAdr010tantoGroup())
                        && getAdr010tantoUser() == -2) {
                    AdrValidateUtil.validateMyGroupUser(errors, gsMsg.getMessage("cmn.staff"));
                }
                break;
            case Adr010Const.CMDMODE_PROJECT :
                break;
            case Adr010Const.CMDMODE_DETAILED :
                //氏名 姓
                String nameSei = gsMsg.getMessage(req, "cmn.name") + " "
                                 + gsMsg.getMessage(req, "cmn.lastname");
                AdrValidateUtil.validateTextField(errors, adr010unameSei__, "adr010unameSei",
                        nameSei, GSConstAddress.MAX_LENGTH_NAME_SEI, false);
                //氏名 名
                String nameMei = gsMsg.getMessage(req, "cmn.name") + " "
                                 + gsMsg.getMessage(req, "cmn.name3");
                AdrValidateUtil.validateTextField(errors, adr010unameMei__, "adr010unameMei",
                        nameMei, GSConstAddress.MAX_LENGTH_NAME_MEI, false);
                //氏名カナ 姓
                String nameSeiKn = gsMsg.getMessage(req, "cmn.name.kana") + " "
                                   + gsMsg.getMessage(req, "cmn.lastname");
                AdrValidateUtil.validateTextFieldKana(errors,
                                                    adr010unameSeiKn__, "adr010unameSeiKn",
                                                    nameSeiKn,
                                                    GSConstAddress.MAX_LENGTH_NAME_SEI_KN, false);
                //氏名カナ 名
                String nameMeiKn = gsMsg.getMessage(req, "cmn.name.kana") + " "
                                   + gsMsg.getMessage(req, "cmn.name3");
                AdrValidateUtil.validateTextFieldKana(errors,
                                                    adr010unameMeiKn__, "adr010unameMeiKn",
                                                    nameMeiKn,
                                                    GSConstAddress.MAX_LENGTH_NAME_MEI_KN, false);
                //会社名
                AdrValidateUtil.validateTextField(errors,
                        adr010detailCoName__, "adr010detailCoName",
                        gsMsg.getMessage(req, "cmn.company.name"),
                        GSConstAddress.MAX_LENGTH_COMPANY_NAME, false);
                //E-MAIL
                AdrValidateUtil.validateMail(errors, this.adr010mail__, 0, req);

                //マイグループかつユーザ選択なし 担当者
                if (AdrCommonBiz.isMyGroupSid(getAdr010detailTantoGroup())
                        && getAdr010detailTantoUser() == -1) {
                    AdrValidateUtil.validateMyGroupUser(errors, gsMsg.getMessage("cmn.staff"));
                }
                break;
            case Adr010Const.CMDMODE_CONTACT :
                //氏名 姓
                nameSei = gsMsg.getMessage(req, "cmn.name") + " "
                          + gsMsg.getMessage(req, "cmn.lastname");
                AdrValidateUtil.validateTextField(errors, adr010unameSeiContact__,
                                                  "adr010unameSeiContact", nameSei,
                                                  GSConstAddress.MAX_LENGTH_NAME_SEI, false);

                //氏名 名
                nameMei = gsMsg.getMessage(req, "cmn.name") + " "
                          + gsMsg.getMessage(req, "cmn.name3");
                AdrValidateUtil.validateTextField(errors, adr010unameMeiContact__,
                                                "adr010unameMeiContact", nameMei,
                                                GSConstAddress.MAX_LENGTH_NAME_MEI, false);

                //会社名
                AdrValidateUtil.validateTextField(errors, adr010CoNameContact__,
                        "adr010CoNameContact", gsMsg.getMessage(req, "cmn.company.name"),
                        GSConstAddress.MAX_LENGTH_COMPANY_NAME, false);

                //支店・営業所名
                AdrValidateUtil.validateTextField(errors, adr010CoBaseNameContact__,
                        "adr010coBaseNameContact", gsMsg.getMessage(req, "address.10"),
                        GSConstAddress.MAX_LENGTH_COBASE_NAME, false);

                //マイグループかつユーザ選択なし 担当者
                if (AdrCommonBiz.isMyGroupSid(String.valueOf(getAdr010tantoGroupContact()))
                        && getAdr010tantoUserContact() == -2) {
                    AdrValidateUtil.validateMyGroupUser(errors, gsMsg.getMessage("cmn.staff"));
                }

                if (adr010dateNoKbn__ != 1) {
                    //日時From
                    String msgDateFr = gsMsg.getMessage(req, "cmn.date") + "From";
                    AdrValidateUtil.validateDate(errors,
                                                 msgDateFr,
                                                 adr010SltYearFrContact__,
                                                 adr010SltMonthFrContact__,
                                                 adr010SltDayFrContact__,
                                                 req);
                    //日時To
                    String msgDateTo = gsMsg.getMessage(req, "cmn.date") + "To";
                    AdrValidateUtil.validateDate(errors,
                                                 msgDateTo,
                                                 adr010SltYearToContact__,
                                                 adr010SltMonthToContact__,
                                                 adr010SltDayToContact__,
                                                 req);
                    //日時大小チェック
                    __validateDataRange(errors, req);
                }

                //キーワードチェック
                validateSearchTarget(errors,
                        getAdr010SearchWordContact(), adr010SearchTargetContact__, req);

                break;

            default :
        }

        return errors;
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return errors エラー
     */
    public ActionErrors validateSelectCheck010(HttpServletRequest req) {

        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();
        ActionMessage msg = null;
        //削除するアドレス
        String msgDelAdr = gsMsg.getMessage(req, "address.src.3");

        //未選択チェック
        if (adr010selectSid__ == null) {
            msg = new ActionMessage(
                    "error.select.required.text", msgDelAdr);
            StrutsUtil.addMessage(errors, msg, "adrSid");
        } else {
            if (adr010selectSid__.length < 1) {
                msg = new ActionMessage(
                        "error.select.required.text", msgDelAdr);
                StrutsUtil.addMessage(errors, msg, "adrSid");
            }
        }
        return errors;
    }

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

        if (adr010SendMailMode__ == GSConst.SEND_KBN_ATESAKI) {
            //宛先
            msgSendTarget = gsMsg.getMessage(req, "cmn.from");
        } else if (adr010SendMailMode__ == GSConst.SEND_KBN_CC) {
            //CC
            msgSendTarget = "CC";
        } else {
            //BCC
            msgSendTarget = "BCC";
        }


        //未選択チェック
        if (adr010selectSid__ == null) {
            msg = new ActionMessage(
                    "error.select.required.text", msgSendTarget);
            StrutsUtil.addMessage(errors, msg, "adrSid");
        } else {
            if (adr010selectSid__.length < 1) {
                msg = new ActionMessage(
                        "error.select.required.text", msgSendTarget);
                StrutsUtil.addMessage(errors, msg, "adrSid");
            }
        }
        return errors;
    }

    /**
     * <br>[機  能] 年月日時分の大小チェック
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param req リクエスト
     * @return チェック結果 true :エラー有り false :エラー無し
     */
    private boolean __validateDataRange(ActionErrors errors, HttpServletRequest req) {
        GsMessage gsMsg = new GsMessage();

        UDate dateFrom = new UDate();
        dateFrom.setTimeStamp(adr010SltYearFrContact__
                            + StringUtil.toDecFormat(adr010SltMonthFrContact__, "00")
                            + StringUtil.toDecFormat(adr010SltDayFrContact__, "00")
                            + "00"
                            + "00"
                            + "00");

        UDate dateTo = new UDate();
        dateTo.setTimeStamp(adr010SltYearToContact__
                          + StringUtil.toDecFormat(adr010SltMonthToContact__, "00")
                          + StringUtil.toDecFormat(adr010SltDayToContact__, "00")
                          + "00"
                          + "00"
                          + "00");

        //大小チェック
        if (dateTo.compareDateYMDHM(dateFrom) == UDate.LARGE) {
            ActionMessage msg = new ActionMessage(
                    "error.input.comp.text",
                    gsMsg.getMessage(req, "cmn.date"),
                    gsMsg.getMessage(req, "cmn.start.lessthan.end"));
            errors.add("" + "error.input.comp.text", msg);
            return true;
        }

        //入力エラー無し
        return false;
    }

    /**
     * <br>[機  能] 検索対象の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param keyWord キーワード
     * @param searchTarget 検索対象
     * @param req リクエスト
     * @return true: エラーあり false: エラーなし
     */
    public static boolean validateSearchTarget(
        ActionErrors errors,
        String keyWord,
        String[] searchTarget,
        HttpServletRequest req) {

        ActionMessage msg = null;

        //キーワード未入力時はチェックなし
        if (NullDefault.getString(keyWord, "").length() < 1) {
            return false;
        }

        if (searchTarget == null || searchTarget.length < 1) {
            GsMessage gsMsg = new GsMessage();
            //未選択の場合エラー
            msg = new ActionMessage(
                    "error.select.required.text", gsMsg.getMessage(req, "cmn.search2"));
            StrutsUtil.addMessage(errors, msg, "target");
            return true;
        }

        return false;
    }

    /**
     * <p>adr010atiSid を取得します。
     * @return adr010atiSid
     */
    public int getAdr010atiSid() {
        return adr010atiSid__;
    }

    /**
     * <p>adr010atiSid をセットします。
     * @param adr010atiSid adr010atiSid
     */
    public void setAdr010atiSid(int adr010atiSid) {
        adr010atiSid__ = adr010atiSid;
    }

    /**
     * <p>adr010biko を取得します。
     * @return adr010biko
     */
    public String getAdr010biko() {
        return adr010biko__;
    }

    /**
     * <p>adr010biko をセットします。
     * @param adr010biko adr010biko
     */
    public void setAdr010biko(String adr010biko) {
        adr010biko__ = adr010biko;
    }

    /**
     * <p>adr010cmdMode を取得します。
     * @return adr010cmdMode
     */
    public int getAdr010cmdMode() {
        return adr010cmdMode__;
    }

    /**
     * <p>adr010cmdMode をセットします。
     * @param adr010cmdMode adr010cmdMode
     */
    public void setAdr010cmdMode(int adr010cmdMode) {
        adr010cmdMode__ = adr010cmdMode;
    }

    /**
     * <p>adr010coBaseName を取得します。
     * @return adr010coBaseName
     */
    public String getAdr010coBaseName() {
        return adr010coBaseName__;
    }

    /**
     * <p>adr010coBaseName をセットします。
     * @param adr010coBaseName adr010coBaseName
     */
    public void setAdr010coBaseName(String adr010coBaseName) {
        adr010coBaseName__ = adr010coBaseName;
    }

    /**
     * <p>adr010code を取得します。
     * @return adr010code
     */
    public String getAdr010code() {
        return adr010code__;
    }

    /**
     * <p>adr010code をセットします。
     * @param adr010code adr010code
     */
    public void setAdr010code(String adr010code) {
        adr010code__ = adr010code;
    }

    /**
     * <p>adr010coName を取得します。
     * @return adr010coName
     */
    public String getAdr010coName() {
        return adr010coName__;
    }

    /**
     * <p>adr010coName をセットします。
     * @param adr010coName adr010coName
     */
    public void setAdr010coName(String adr010coName) {
        adr010coName__ = adr010coName;
    }

    /**
     * <p>adr010coNameKn を取得します。
     * @return adr010coNameKn
     */
    public String getAdr010coNameKn() {
        return adr010coNameKn__;
    }

    /**
     * <p>adr010coNameKn をセットします。
     * @param adr010coNameKn adr010coNameKn
     */
    public void setAdr010coNameKn(String adr010coNameKn) {
        adr010coNameKn__ = adr010coNameKn;
    }

    /**
     * <p>adr010detailAtiSid を取得します。
     * @return adr010detailAtiSid
     */
    public int getAdr010detailAtiSid() {
        return adr010detailAtiSid__;
    }

    /**
     * <p>adr010detailAtiSid をセットします。
     * @param adr010detailAtiSid adr010detailAtiSid
     */
    public void setAdr010detailAtiSid(int adr010detailAtiSid) {
        adr010detailAtiSid__ = adr010detailAtiSid;
    }

    /**
     * <p>adr010detailCoName を取得します。
     * @return adr010detailCoName
     */
    public String getAdr010detailCoName() {
        return adr010detailCoName__;
    }

    /**
     * <p>adr010detailCoName をセットします。
     * @param adr010detailCoName adr010detailCoName
     */
    public void setAdr010detailCoName(String adr010detailCoName) {
        adr010detailCoName__ = adr010detailCoName;
    }

    /**
     * <p>adr010detailTantoGroup を取得します。
     * @return adr010detailTantoGroup
     */
    public String getAdr010detailTantoGroup() {
        return adr010detailTantoGroup__;
    }

    /**
     * <p>adr010detailTantoGroup をセットします。
     * @param adr010detailTantoGroup adr010detailTantoGroup
     */
    public void setAdr010detailTantoGroup(String adr010detailTantoGroup) {
        adr010detailTantoGroup__ = adr010detailTantoGroup;
    }

    /**
     * <p>adr010detailTantoUser を取得します。
     * @return adr010detailTantoUser
     */
    public int getAdr010detailTantoUser() {
        return adr010detailTantoUser__;
    }

    /**
     * <p>adr010detailTantoUser をセットします。
     * @param adr010detailTantoUser adr010detailTantoUser
     */
    public void setAdr010detailTantoUser(int adr010detailTantoUser) {
        adr010detailTantoUser__ = adr010detailTantoUser;
    }

    /**
     * <p>adr010EditAdrSid を取得します。
     * @return adr010EditAdrSid
     */
    public int getAdr010EditAdrSid() {
        return adr010EditAdrSid__;
    }

    /**
     * <p>adr010EditAdrSid をセットします。
     * @param adr010EditAdrSid adr010EditAdrSid
     */
    public void setAdr010EditAdrSid(int adr010EditAdrSid) {
        adr010EditAdrSid__ = adr010EditAdrSid;
    }

    /**
     * <p>adr010mail を取得します。
     * @return adr010mail
     */
    public String getAdr010mail() {
        return adr010mail__;
    }

    /**
     * <p>adr010mail をセットします。
     * @param adr010mail adr010mail
     */
    public void setAdr010mail(String adr010mail) {
        adr010mail__ = adr010mail;
    }

    /**
     * <p>adr010orderKey を取得します。
     * @return adr010orderKey
     */
    public int getAdr010orderKey() {
        return adr010orderKey__;
    }

    /**
     * <p>adr010orderKey をセットします。
     * @param adr010orderKey adr010orderKey
     */
    public void setAdr010orderKey(int adr010orderKey) {
        adr010orderKey__ = adr010orderKey;
    }

    /**
     * <p>adr010page を取得します。
     * @return adr010page
     */
    public int getAdr010page() {
        return adr010page__;
    }

    /**
     * <p>adr010page をセットします。
     * @param adr010page adr010page
     */
    public void setAdr010page(int adr010page) {
        adr010page__ = adr010page;
    }

    /**
     * <p>adr010pageBottom を取得します。
     * @return adr010pageBottom
     */
    public int getAdr010pageBottom() {
        return adr010pageBottom__;
    }

    /**
     * <p>adr010pageBottom をセットします。
     * @param adr010pageBottom adr010pageBottom
     */
    public void setAdr010pageBottom(int adr010pageBottom) {
        adr010pageBottom__ = adr010pageBottom;
    }

    /**
     * <p>adr010pageTop を取得します。
     * @return adr010pageTop
     */
    public int getAdr010pageTop() {
        return adr010pageTop__;
    }

    /**
     * <p>adr010pageTop をセットします。
     * @param adr010pageTop adr010pageTop
     */
    public void setAdr010pageTop(int adr010pageTop) {
        adr010pageTop__ = adr010pageTop;
    }

    /**
     * <p>adr010position を取得します。
     * @return adr010position
     */
    public int getAdr010position() {
        return adr010position__;
    }

    /**
     * <p>adr010position をセットします。
     * @param adr010position adr010position
     */
    public void setAdr010position(int adr010position) {
        adr010position__ = adr010position;
    }

    /**
     * <p>adr010searchFlg を取得します。
     * @return adr010searchFlg
     */
    public int getAdr010searchFlg() {
        return adr010searchFlg__;
    }

    /**
     * <p>adr010searchFlg をセットします。
     * @param adr010searchFlg adr010searchFlg
     */
    public void setAdr010searchFlg(int adr010searchFlg) {
        adr010searchFlg__ = adr010searchFlg;
    }

    /**
     * <p>adr010SearchKana を取得します。
     * @return adr010SearchKana
     */
    public String getAdr010SearchKana() {
        return adr010SearchKana__;
    }

    /**
     * <p>adr010SearchKana をセットします。
     * @param adr010SearchKana adr010SearchKana
     */
    public void setAdr010SearchKana(String adr010SearchKana) {
        adr010SearchKana__ = adr010SearchKana;
    }

    /**
     * <p>adr010searchLabel を取得します。
     * @return adr010searchLabel
     */
    public String[] getAdr010searchLabel() {
        return adr010searchLabel__;
    }

    /**
     * <p>adr010searchLabel をセットします。
     * @param adr010searchLabel adr010searchLabel
     */
    public void setAdr010searchLabel(String[] adr010searchLabel) {
        adr010searchLabel__ = adr010searchLabel;
    }

    /**
     * <p>adr010sortKey を取得します。
     * @return adr010sortKey
     */
    public int getAdr010sortKey() {
        int[] sortKeyList = new int[Adr010Const.SORTKEY_NORMAL.length];
        System.arraycopy(Adr010Const.SORTKEY_NORMAL, 0, sortKeyList, 0, sortKeyList.length);
        if (adr010cmdMode__ == Adr010Const.CMDMODE_CONTACT) {
            sortKeyList = new int[Adr010Const.SORTKEY_CONTACT.length];
            System.arraycopy(Adr010Const.SORTKEY_CONTACT, 0, sortKeyList, 0, sortKeyList.length);
        }

        Arrays.sort(sortKeyList);
        if (Arrays.binarySearch(sortKeyList, adr010sortKey__) < 0) {
            if (adr010cmdMode__ == Adr010Const.CMDMODE_CONTACT) {
                adr010sortKey__ = Adr010Const.SORTKEY_CONTACT_DATE;
            } else {
                adr010sortKey__ = Adr010Const.SORTKEY_UNAME;
            }
            adr010orderKey__ = Adr010Const.ORDERKEY_ASC;
        }

        return adr010sortKey__;
    }

    /**
     * <p>adr010sortKey をセットします。
     * @param adr010sortKey adr010sortKey
     */
    public void setAdr010sortKey(int adr010sortKey) {
        adr010sortKey__ = adr010sortKey;
    }

    /**
     * <p>adr010svAtiSid を取得します。
     * @return adr010svAtiSid
     */
    public int getAdr010svAtiSid() {
        return adr010svAtiSid__;
    }

    /**
     * <p>adr010svAtiSid をセットします。
     * @param adr010svAtiSid adr010svAtiSid
     */
    public void setAdr010svAtiSid(int adr010svAtiSid) {
        adr010svAtiSid__ = adr010svAtiSid;
    }

    /**
     * <p>adr010svBiko を取得します。
     * @return adr010svBiko
     */
    public String getAdr010svBiko() {
        return adr010svBiko__;
    }

    /**
     * <p>adr010svBiko をセットします。
     * @param adr010svBiko adr010svBiko
     */
    public void setAdr010svBiko(String adr010svBiko) {
        adr010svBiko__ = adr010svBiko;
    }

    /**
     * <p>adr010svCoBaseName を取得します。
     * @return adr010svCoBaseName
     */
    public String getAdr010svCoBaseName() {
        return adr010svCoBaseName__;
    }

    /**
     * <p>adr010svCoBaseName をセットします。
     * @param adr010svCoBaseName adr010svCoBaseName
     */
    public void setAdr010svCoBaseName(String adr010svCoBaseName) {
        adr010svCoBaseName__ = adr010svCoBaseName;
    }

    /**
     * <p>adr010svCode を取得します。
     * @return adr010svCode
     */
    public String getAdr010svCode() {
        return adr010svCode__;
    }

    /**
     * <p>adr010svCode をセットします。
     * @param adr010svCode adr010svCode
     */
    public void setAdr010svCode(String adr010svCode) {
        adr010svCode__ = adr010svCode;
    }

    /**
     * <p>adr010svCoName を取得します。
     * @return adr010svCoName
     */
    public String getAdr010svCoName() {
        return adr010svCoName__;
    }

    /**
     * <p>adr010svCoName をセットします。
     * @param adr010svCoName adr010svCoName
     */
    public void setAdr010svCoName(String adr010svCoName) {
        adr010svCoName__ = adr010svCoName;
    }

    /**
     * <p>adr010svCoNameKn を取得します。
     * @return adr010svCoNameKn
     */
    public String getAdr010svCoNameKn() {
        return adr010svCoNameKn__;
    }

    /**
     * <p>adr010svCoNameKn をセットします。
     * @param adr010svCoNameKn adr010svCoNameKn
     */
    public void setAdr010svCoNameKn(String adr010svCoNameKn) {
        adr010svCoNameKn__ = adr010svCoNameKn;
    }

    /**
     * <p>adr010svDetailAtiSid を取得します。
     * @return adr010svDetailAtiSid
     */
    public int getAdr010svDetailAtiSid() {
        return adr010svDetailAtiSid__;
    }

    /**
     * <p>adr010svDetailAtiSid をセットします。
     * @param adr010svDetailAtiSid adr010svDetailAtiSid
     */
    public void setAdr010svDetailAtiSid(int adr010svDetailAtiSid) {
        adr010svDetailAtiSid__ = adr010svDetailAtiSid;
    }

    /**
     * <p>adr010svDetailCoName を取得します。
     * @return adr010svDetailCoName
     */
    public String getAdr010svDetailCoName() {
        return adr010svDetailCoName__;
    }

    /**
     * <p>adr010svDetailCoName をセットします。
     * @param adr010svDetailCoName adr010svDetailCoName
     */
    public void setAdr010svDetailCoName(String adr010svDetailCoName) {
        adr010svDetailCoName__ = adr010svDetailCoName;
    }

    /**
     * <p>adr010svDetailTantoGroup を取得します。
     * @return adr010svDetailTantoGroup
     */
    public int getAdr010svDetailTantoGroup() {
        return adr010svDetailTantoGroup__;
    }

    /**
     * <p>adr010svDetailTantoGroup をセットします。
     * @param adr010svDetailTantoGroup adr010svDetailTantoGroup
     */
    public void setAdr010svDetailTantoGroup(int adr010svDetailTantoGroup) {
        adr010svDetailTantoGroup__ = adr010svDetailTantoGroup;
    }

    /**
     * <p>adr010svDetailTantoUser を取得します。
     * @return adr010svDetailTantoUser
     */
    public int getAdr010svDetailTantoUser() {
        return adr010svDetailTantoUser__;
    }

    /**
     * <p>adr010svDetailTantoUser をセットします。
     * @param adr010svDetailTantoUser adr010svDetailTantoUser
     */
    public void setAdr010svDetailTantoUser(int adr010svDetailTantoUser) {
        adr010svDetailTantoUser__ = adr010svDetailTantoUser;
    }

    /**
     * <p>adr010svMail を取得します。
     * @return adr010svMail
     */
    public String getAdr010svMail() {
        return adr010svMail__;
    }

    /**
     * <p>adr010svMail をセットします。
     * @param adr010svMail adr010svMail
     */
    public void setAdr010svMail(String adr010svMail) {
        adr010svMail__ = adr010svMail;
    }

    /**
     * <p>adr010svPosition を取得します。
     * @return adr010svPosition
     */
    public int getAdr010svPosition() {
        return adr010svPosition__;
    }

    /**
     * <p>adr010svPosition をセットします。
     * @param adr010svPosition adr010svPosition
     */
    public void setAdr010svPosition(int adr010svPosition) {
        adr010svPosition__ = adr010svPosition;
    }

    /**
     * <p>adr010svSearchKana を取得します。
     * @return adr010svSearchKana
     */
    public String getAdr010svSearchKana() {
        return adr010svSearchKana__;
    }

    /**
     * <p>adr010svSearchKana をセットします。
     * @param adr010svSearchKana adr010svSearchKana
     */
    public void setAdr010svSearchKana(String adr010svSearchKana) {
        adr010svSearchKana__ = adr010svSearchKana;
    }

    /**
     * <p>adr010svSearchLabel を取得します。
     * @return adr010svSearchLabel
     */
    public String[] getAdr010svSearchLabel() {
        return adr010svSearchLabel__;
    }

    /**
     * <p>adr010svSearchLabel をセットします。
     * @param adr010svSearchLabel adr010svSearchLabel
     */
    public void setAdr010svSearchLabel(String[] adr010svSearchLabel) {
        adr010svSearchLabel__ = adr010svSearchLabel;
    }

    /**
     * <p>adr010svTantoGroup を取得します。
     * @return adr010svTantoGroup
     */
    public int getAdr010svTantoGroup() {
        return adr010svTantoGroup__;
    }

    /**
     * <p>adr010svTantoGroup をセットします。
     * @param adr010svTantoGroup adr010svTantoGroup
     */
    public void setAdr010svTantoGroup(int adr010svTantoGroup) {
        adr010svTantoGroup__ = adr010svTantoGroup;
    }

    /**
     * <p>adr010svTantoUser を取得します。
     * @return adr010svTantoUser
     */
    public int getAdr010svTantoUser() {
        return adr010svTantoUser__;
    }

    /**
     * <p>adr010svTantoUser をセットします。
     * @param adr010svTantoUser adr010svTantoUser
     */
    public void setAdr010svTantoUser(int adr010svTantoUser) {
        adr010svTantoUser__ = adr010svTantoUser;
    }

    /**
     * <p>projectKbnSv を取得します。
     * @return projectKbnSv
     */
    public int getProjectKbnSv() {
        return projectKbnSv__;
    }

    /**
     * <p>projectKbnSv をセットします。
     * @param projectKbnSv projectKbnSv
     */
    public void setProjectKbnSv(int projectKbnSv) {
        projectKbnSv__ = projectKbnSv;
    }

    /**
     * <p>statusKbnSv を取得します。
     * @return statusKbnSv
     */
    public int getStatusKbnSv() {
        return statusKbnSv__;
    }

    /**
     * <p>statusKbnSv をセットします。
     * @param statusKbnSv statusKbnSv
     */
    public void setStatusKbnSv(int statusKbnSv) {
        statusKbnSv__ = statusKbnSv;
    }

    /**
     * <p>adr010svTdfk を取得します。
     * @return adr010svTdfk
     */
    public int getAdr010svTdfk() {
        return adr010svTdfk__;
    }

    /**
     * <p>adr010svTdfk をセットします。
     * @param adr010svTdfk adr010svTdfk
     */
    public void setAdr010svTdfk(int adr010svTdfk) {
        adr010svTdfk__ = adr010svTdfk;
    }

    /**
     * <p>adr010svUnameMei を取得します。
     * @return adr010svUnameMei
     */
    public String getAdr010svUnameMei() {
        return adr010svUnameMei__;
    }

    /**
     * <p>adr010svUnameMei をセットします。
     * @param adr010svUnameMei adr010svUnameMei
     */
    public void setAdr010svUnameMei(String adr010svUnameMei) {
        adr010svUnameMei__ = adr010svUnameMei;
    }

    /**
     * <p>adr010svUnameMeiKn を取得します。
     * @return adr010svUnameMeiKn
     */
    public String getAdr010svUnameMeiKn() {
        return adr010svUnameMeiKn__;
    }

    /**
     * <p>adr010svUnameMeiKn をセットします。
     * @param adr010svUnameMeiKn adr010svUnameMeiKn
     */
    public void setAdr010svUnameMeiKn(String adr010svUnameMeiKn) {
        adr010svUnameMeiKn__ = adr010svUnameMeiKn;
    }

    /**
     * <p>adr010svUnameSei を取得します。
     * @return adr010svUnameSei
     */
    public String getAdr010svUnameSei() {
        return adr010svUnameSei__;
    }

    /**
     * <p>adr010svUnameSei をセットします。
     * @param adr010svUnameSei adr010svUnameSei
     */
    public void setAdr010svUnameSei(String adr010svUnameSei) {
        adr010svUnameSei__ = adr010svUnameSei;
    }

    /**
     * <p>adr010svUnameSeiKn を取得します。
     * @return adr010svUnameSeiKn
     */
    public String getAdr010svUnameSeiKn() {
        return adr010svUnameSeiKn__;
    }

    /**
     * <p>adr010svUnameSeiKn をセットします。
     * @param adr010svUnameSeiKn adr010svUnameSeiKn
     */
    public void setAdr010svUnameSeiKn(String adr010svUnameSeiKn) {
        adr010svUnameSeiKn__ = adr010svUnameSeiKn;
    }

    /**
     * <p>adr010tantoGroup を取得します。
     * @return adr010tantoGroup
     */
    public String getAdr010tantoGroup() {
        return adr010tantoGroup__;
    }

    /**
     * <p>adr010tantoGroup をセットします。
     * @param adr010tantoGroup adr010tantoGroup
     */
    public void setAdr010tantoGroup(String adr010tantoGroup) {
        adr010tantoGroup__ = adr010tantoGroup;
    }

    /**
     * <p>adr010tantoUser を取得します。
     * @return adr010tantoUser
     */
    public int getAdr010tantoUser() {
        return adr010tantoUser__;
    }

    /**
     * <p>adr010tantoUser をセットします。
     * @param adr010tantoUser adr010tantoUser
     */
    public void setAdr010tantoUser(int adr010tantoUser) {
        adr010tantoUser__ = adr010tantoUser;
    }

    /**
     * <p>adr010svTantoGroupContact を取得します。
     * @return adr010svTantoGroupContact
     */
    public int getAdr010svTantoGroupContact() {
        return adr010svTantoGroupContact__;
    }

    /**
     * <p>adr010svTantoGroupContact をセットします。
     * @param adr010svTantoGroupContact adr010svTantoGroupContact
     */
    public void setAdr010svTantoGroupContact(int adr010svTantoGroupContact) {
        adr010svTantoGroupContact__ = adr010svTantoGroupContact;
    }

    /**
     * <p>adr010svTantoUserContact を取得します。
     * @return adr010svTantoUserContact
     */
    public int getAdr010svTantoUserContact() {
        return adr010svTantoUserContact__;
    }

    /**
     * <p>adr010svTantoUserContact をセットします。
     * @param adr010svTantoUserContact adr010svTantoUserContact
     */
    public void setAdr010svTantoUserContact(int adr010svTantoUserContact) {
        adr010svTantoUserContact__ = adr010svTantoUserContact;
    }

    /**
     * <p>adr010tantoGroupContact を取得します。
     * @return adr010tantoGroupContact
     */
    public String getAdr010tantoGroupContact() {
        return adr010tantoGroupContact__;
    }

    /**
     * <p>adr010tantoGroupContact をセットします。
     * @param adr010tantoGroupContact adr010tantoGroupContact
     */
    public void setAdr010tantoGroupContact(String adr010tantoGroupContact) {
        adr010tantoGroupContact__ = adr010tantoGroupContact;
    }

    /**
     * <p>adr010tantoUserContact を取得します。
     * @return adr010tantoUserContact
     */
    public int getAdr010tantoUserContact() {
        return adr010tantoUserContact__;
    }

    /**
     * <p>adr010tantoUserContact をセットします。
     * @param adr010tantoUserContact adr010tantoUserContact
     */
    public void setAdr010tantoUserContact(int adr010tantoUserContact) {
        adr010tantoUserContact__ = adr010tantoUserContact;
    }

    /**
     * <p>adr010tdfk を取得します。
     * @return adr010tdfk
     */
    public int getAdr010tdfk() {
        return adr010tdfk__;
    }

    /**
     * <p>adr010tdfk をセットします。
     * @param adr010tdfk adr010tdfk
     */
    public void setAdr010tdfk(int adr010tdfk) {
        adr010tdfk__ = adr010tdfk;
    }

    /**
     * <p>adr010unameMei を取得します。
     * @return adr010unameMei
     */
    public String getAdr010unameMei() {
        return adr010unameMei__;
    }

    /**
     * <p>adr010unameMei をセットします。
     * @param adr010unameMei adr010unameMei
     */
    public void setAdr010unameMei(String adr010unameMei) {
        adr010unameMei__ = adr010unameMei;
    }

    /**
     * <p>adr010unameMeiKn を取得します。
     * @return adr010unameMeiKn
     */
    public String getAdr010unameMeiKn() {
        return adr010unameMeiKn__;
    }

    /**
     * <p>adr010unameMeiKn をセットします。
     * @param adr010unameMeiKn adr010unameMeiKn
     */
    public void setAdr010unameMeiKn(String adr010unameMeiKn) {
        adr010unameMeiKn__ = adr010unameMeiKn;
    }

    /**
     * <p>adr010unameSei を取得します。
     * @return adr010unameSei
     */
    public String getAdr010unameSei() {
        return adr010unameSei__;
    }

    /**
     * <p>adr010unameSei をセットします。
     * @param adr010unameSei adr010unameSei
     */
    public void setAdr010unameSei(String adr010unameSei) {
        adr010unameSei__ = adr010unameSei;
    }

    /**
     * <p>adr010unameSeiKn を取得します。
     * @return adr010unameSeiKn
     */
    public String getAdr010unameSeiKn() {
        return adr010unameSeiKn__;
    }

    /**
     * <p>adr010unameSeiKn をセットします。
     * @param adr010unameSeiKn adr010unameSeiKn
     */
    public void setAdr010unameSeiKn(String adr010unameSeiKn) {
        adr010unameSeiKn__ = adr010unameSeiKn;
    }

    /**
     * <p>adr020ProcMode を取得します。
     * @return adr020ProcMode
     */
    public int getAdr020ProcMode() {
        return adr020ProcMode__;
    }

    /**
     * <p>adr020ProcMode をセットします。
     * @param adr020ProcMode adr020ProcMode
     */
    public void setAdr020ProcMode(int adr020ProcMode) {
        adr020ProcMode__ = adr020ProcMode;
    }

    /**
     * <p>atiCmbList を取得します。
     * @return atiCmbList
     */
    public List<LabelValueBean> getAtiCmbList() {
        return atiCmbList__;
    }

    /**
     * <p>atiCmbList をセットします。
     * @param atiCmbList atiCmbList
     */
    public void setAtiCmbList(List<LabelValueBean> atiCmbList) {
        atiCmbList__ = atiCmbList;
    }

    /**
     * <p>groupCmbList を取得します。
     * @return groupCmbList
     */
    public List<LabelValueBean> getGroupCmbList() {
        return groupCmbList__;
    }

    /**
     * <p>groupCmbList をセットします。
     * @param groupCmbList groupCmbList
     */
    public void setGroupCmbList(List<LabelValueBean> groupCmbList) {
        groupCmbList__ = groupCmbList;
    }

    /**
     * <p>selectLabelList を取得します。
     * @return selectLabelList
     */
    public List<AdrLabelModel> getSelectLabelList() {
        return selectLabelList__;
    }

    /**
     * <p>selectLabelList をセットします。
     * @param selectLabelList selectLabelList
     */
    public void setSelectLabelList(List<AdrLabelModel> selectLabelList) {
        selectLabelList__ = selectLabelList;
    }

    /**
     * <p>tdfkCmbList を取得します。
     * @return tdfkCmbList
     */
    public List<LabelValueBean> getTdfkCmbList() {
        return tdfkCmbList__;
    }

    /**
     * <p>tdfkCmbList をセットします。
     * @param tdfkCmbList tdfkCmbList
     */
    public void setTdfkCmbList(List<LabelValueBean> tdfkCmbList) {
        tdfkCmbList__ = tdfkCmbList;
    }

    /**
     * <p>userCmbList を取得します。
     * @return userCmbList
     */
    public List<UsrLabelValueBean> getUserCmbList() {
        return userCmbList__;
    }

    /**
     * <p>userCmbList をセットします。
     * @param userCmbList userCmbList
     */
    public void setUserCmbList(List<UsrLabelValueBean> userCmbList) {
        userCmbList__ = userCmbList;
    }

    /**
     * <p>positionCmbList を取得します。
     * @return positionCmbList
     */
    public List<LabelValueBean> getPositionCmbList() {
        return positionCmbList__;
    }

    /**
     * <p>positionCmbList をセットします。
     * @param positionCmbList positionCmbList
     */
    public void setPositionCmbList(List<LabelValueBean> positionCmbList) {
        positionCmbList__ = positionCmbList;
    }

    /**
     * <p>detailList を取得します。
     * @return detailList
     */
    public List<Adr010DetailModel> getDetailList() {
        return detailList__;
    }

    /**
     * <p>detailList をセットします。
     * @param detailList detailList
     */
    public void setDetailList(List<Adr010DetailModel> detailList) {
        detailList__ = detailList;
    }

    /**
     * <p>pageCmbList を取得します。
     * @return pageCmbList
     */
    public List<LabelValueBean> getPageCmbList() {
        return pageCmbList__;
    }

    /**
     * <p>pageCmbList をセットします。
     * @param pageCmbList pageCmbList
     */
    public void setPageCmbList(List<LabelValueBean> pageCmbList) {
        pageCmbList__ = pageCmbList;
    }

    /**
     * <p>adr010selectCategory を取得します。
     * @return adr010selectCategory
     */
    public int getAdr010selectCategory() {
        return adr010selectCategory__;
    }

    /**
     * <p>adr010selectCategory をセットします。
     * @param adr010selectCategory adr010selectCategory
     */
    public void setAdr010selectCategory(int adr010selectCategory) {
        adr010selectCategory__ = adr010selectCategory;
    }

    /**
     * <p>adr010unameKanaList を取得します。
     * @return adr010unameKanaList
     */
    public List<String> getAdr010unameKanaList() {
        return adr010unameKanaList__;
    }

    /**
     * <p>adr010unameKanaList をセットします。
     * @param adr010unameKanaList adr010unameKanaList
     */
    public void setAdr010unameKanaList(List<String> adr010unameKanaList) {
        adr010unameKanaList__ = adr010unameKanaList;
    }

    /**
     * <p>adr010viewAdminBtn を取得します。
     * @return adr010viewAdminBtn
     */
    public int getAdr010viewAdminBtn() {
        return adr010viewAdminBtn__;
    }

    /**
     * <p>adr010viewAdminBtn をセットします。
     * @param adr010viewAdminBtn adr010viewAdminBtn
     */
    public void setAdr010viewAdminBtn(int adr010viewAdminBtn) {
        adr010viewAdminBtn__ = adr010viewAdminBtn;
    }

    /**
     * <p>adr010viewCompanyBtn を取得します。
     * @return adr010viewCompanyBtn
     */
    public int getAdr010viewCompanyBtn() {
        return adr010viewCompanyBtn__;
    }

    /**
     * <p>adr010viewCompanyBtn をセットします。
     * @param adr010viewCompanyBtn adr010viewCompanyBtn
     */
    public void setAdr010viewCompanyBtn(int adr010viewCompanyBtn) {
        adr010viewCompanyBtn__ = adr010viewCompanyBtn;
    }

    /**
     * <p>adr010viewGyosyuBtn を取得します。
     * @return adr010viewGyosyuBtn
     */
    public int getAdr010viewGyosyuBtn() {
        return adr010viewGyosyuBtn__;
    }

    /**
     * <p>adr010viewGyosyuBtn をセットします。
     * @param adr010viewGyosyuBtn adr010viewGyosyuBtn
     */
    public void setAdr010viewGyosyuBtn(int adr010viewGyosyuBtn) {
        adr010viewGyosyuBtn__ = adr010viewGyosyuBtn;
    }

    /**
     * <p>adr010viewLabelBtn を取得します。
     * @return adr010viewLabelBtn
     */
    public int getAdr010viewLabelBtn() {
        return adr010viewLabelBtn__;
    }

    /**
     * <p>adr010viewLabelBtn をセットします。
     * @param adr010viewLabelBtn adr010viewLabelBtn
     */
    public void setAdr010viewLabelBtn(int adr010viewLabelBtn) {
        adr010viewLabelBtn__ = adr010viewLabelBtn;
    }

    /**
     * <p>adr010viewExportBtn を取得します。
     * @return adr010viewExportBtn
     */
    public int getAdr010viewExportBtn() {
        return adr010viewExportBtn__;
    }

    /**
     * <p>adr010viewExportBtn をセットします。
     * @param adr010viewExportBtn adr010viewExportBtn
     */
    public void setAdr010viewExportBtn(int adr010viewExportBtn) {
        adr010viewExportBtn__ = adr010viewExportBtn;
    }

    /**
     * <p>adr100backFlg を取得します。
     * @return adr100backFlg
     */
    public int getAdr100backFlg() {
        return adr100backFlg__;
    }

    /**
     * <p>adr100backFlg をセットします。
     * @param adr100backFlg adr100backFlg
     */
    public void setAdr100backFlg(int adr100backFlg) {
        adr100backFlg__ = adr100backFlg;
    }

    /**
     * <p>adr110editAcoSid を取得します。
     * @return adr110editAcoSid
     */
    public int getAdr110editAcoSid() {
        return adr110editAcoSid__;
    }

    /**
     * <p>adr110editAcoSid をセットします。
     * @param adr110editAcoSid adr110editAcoSid
     */
    public void setAdr110editAcoSid(int adr110editAcoSid) {
        adr110editAcoSid__ = adr110editAcoSid;
    }

    /**
     * <p>adr110ProcMode を取得します。
     * @return adr110ProcMode
     */
    public int getAdr110ProcMode() {
        return adr110ProcMode__;
    }

    /**
     * <p>adr110ProcMode をセットします。
     * @param adr110ProcMode adr110ProcMode
     */
    public void setAdr110ProcMode(int adr110ProcMode) {
        adr110ProcMode__ = adr110ProcMode;
    }

    /**
     * <p>adr010searchParamString を取得します。
     * @return adr010searchParamString
     */
    public String getAdr010searchParamString() {
        return adr010searchParamString__;
    }

    /**
     * <p>adr010searchParamString をセットします。
     * @param adr010searchParamString adr010searchParamString
     */
    public void setAdr010searchParamString(String adr010searchParamString) {
        adr010searchParamString__ = adr010searchParamString;
    }

    /**
     * <p>adr010searchLabelString を取得します。
     * @return adr010searchLabelString
     */
    public String getAdr010searchLabelString() {
        return adr010searchLabelString__;
    }

    /**
     * <p>adr010searchLabelString をセットします。
     * @param adr010searchLabelString adr010searchLabelString
     */
    public void setAdr010searchLabelString(String adr010searchLabelString) {
        adr010searchLabelString__ = adr010searchLabelString;
    }

    /**
     * <p>adr020viewFlg を取得します。
     * @return adr020viewFlg
     */
    public int getAdr020viewFlg() {
        return adr020viewFlg__;
    }

    /**
     * <p>adr020viewFlg をセットします。
     * @param adr020viewFlg adr020viewFlg
     */
    public void setAdr020viewFlg(int adr020viewFlg) {
        adr020viewFlg__ = adr020viewFlg;
    }

    /**
     * <p>adr010viewYksBtn を取得します。
     * @return adr010viewYksBtn
     */
    public int getAdr010viewYksBtn() {
        return adr010viewYksBtn__;
    }

    /**
     * <p>adr010viewYksBtn をセットします。
     * @param adr010viewYksBtn adr010viewYksBtn
     */
    public void setAdr010viewYksBtn(int adr010viewYksBtn) {
        adr010viewYksBtn__ = adr010viewYksBtn;
    }

    /**
     * <p>adr020BackId を取得します。
     * @return adr020BackId
     */
    public String getAdr020BackId() {
        return adr020BackId__;
    }

    /**
     * <p>adr020BackId をセットします。
     * @param adr020BackId adr020BackId
     */
    public void setAdr020BackId(String adr020BackId) {
        adr020BackId__ = adr020BackId;
    }

    /**
     * <p>adr160pageNum1 を取得します。
     * @return adr160pageNum1
     */
    public int getAdr160pageNum1() {
        return adr160pageNum1__;
    }

    /**
     * <p>adr160pageNum1 をセットします。
     * @param adr160pageNum1 adr160pageNum1
     */
    public void setAdr160pageNum1(int adr160pageNum1) {
        adr160pageNum1__ = adr160pageNum1;
    }

    /**
     * <p>adr160pageNum2 を取得します。
     * @return adr160pageNum2
     */
    public int getAdr160pageNum2() {
        return adr160pageNum2__;
    }

    /**
     * <p>adr160pageNum2 をセットします。
     * @param adr160pageNum2 adr160pageNum2
     */
    public void setAdr160pageNum2(int adr160pageNum2) {
        adr160pageNum2__ = adr160pageNum2;
    }

    /**
     * <p>sortKey を取得します。
     * @return sortKey
     */
    public String getSortKey() {
        return sortKey__;
    }

    /**
     * <p>sortKey をセットします。
     * @param sortKey sortKey
     */
    public void setSortKey(String sortKey) {
        sortKey__ = sortKey;
    }

    /**
     * <p>orderKey を取得します。
     * @return orderKey
     */
    public String getOrderKey() {
        return orderKey__;
    }

    /**
     * <p>orderKey をセットします。
     * @param orderKey orderKey
     */
    public void setOrderKey(String orderKey) {
        orderKey__ = orderKey;
    }

    /**
     * <p>adr110BackId を取得します。
     * @return adr110BackId
     */
    public String getAdr110BackId() {
        return adr110BackId__;
    }

    /**
     * <p>adr110BackId をセットします。
     * @param adr110BackId adr110BackId
     */
    public void setAdr110BackId(String adr110BackId) {
        adr110BackId__ = adr110BackId;
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
     * <p>adr010SearchComKana を取得します。
     * @return adr010SearchComKana
     */
    public String getAdr010SearchComKana() {
        return adr010SearchComKana__;
    }

    /**
     * <p>adr010SearchComKana をセットします。
     * @param adr010SearchComKana adr010SearchComKana
     */
    public void setAdr010SearchComKana(String adr010SearchComKana) {
        adr010SearchComKana__ = adr010SearchComKana;
    }

    /**
     * <p>adr010svSearchComKana を取得します。
     * @return adr010svSearchComKana
     */
    public String getAdr010svSearchComKana() {
        return adr010svSearchComKana__;
    }

    /**
     * <p>adr010svSearchComKana をセットします。
     * @param adr010svSearchComKana adr010svSearchComKana
     */
    public void setAdr010svSearchComKana(String adr010svSearchComKana) {
        adr010svSearchComKana__ = adr010svSearchComKana;
    }

    /**
     * <p>adr010cnameKanaList を取得します。
     * @return adr010cnameKanaList
     */
    public List<String> getAdr010cnameKanaList() {
        return adr010cnameKanaList__;
    }

    /**
     * <p>adr010cnameKanaList をセットします。
     * @param adr010cnameKanaList adr010cnameKanaList
     */
    public void setAdr010cnameKanaList(List<String> adr010cnameKanaList) {
        adr010cnameKanaList__ = adr010cnameKanaList;
    }

    /**
     * <p>adr010webmail を取得します。
     * @return adr010webmail
     */
    public int getAdr010webmail() {
        return adr010webmail__;
    }
    /**
     * <p>adr010webmail をセットします。
     * @param adr010webmail adr010webmail
     */
    public void setAdr010webmail(int adr010webmail) {
        adr010webmail__ = adr010webmail;
    }

    /**
     * <p>adr010webmailAddress を取得します。
     * @return adr010webmailAddress
     */
    public String getAdr010webmailAddress() {
        return adr010webmailAddress__;
    }

    /**
     * <p>adr010webmailAddress をセットします。
     * @param adr010webmailAddress adr010webmailAddress
     */
    public void setAdr010webmailAddress(String adr010webmailAddress) {
        adr010webmailAddress__ = adr010webmailAddress;
    }

    /**
     * <p>adr010webmailType を取得します。
     * @return adr010webmailType
     */
    public int getAdr010webmailType() {
        return adr010webmailType__;
    }

    /**
     * <p>adr010webmailType をセットします。
     * @param adr010webmailType adr010webmailType
     */
    public void setAdr010webmailType(int adr010webmailType) {
        adr010webmailType__ = adr010webmailType;
    }

    /**
     * <p>adr010webmailSelectAddress を取得します。
     * @return adr010webmailSelectAddress
     */
    public String[] getAdr010webmailSelectAddress() {
        return adr010webmailSelectAddress__;
    }

    /**
     * <p>adr010webmailSelectAddress をセットします。
     * @param adr010webmailSelectAddress adr010webmailSelectAddress
     */
    public void setAdr010webmailSelectAddress(String[] adr010webmailSelectAddress) {
        adr010webmailSelectAddress__ = adr010webmailSelectAddress;
    }

    /**
     * @return adrPosition
     */
    public int getAdrPosition() {
        return adrPosition__;
    }

    /**
     * @param adrPosition 設定する adrPosition
     */
    public void setAdrPosition(int adrPosition) {
        adrPosition__ = adrPosition;
    }

    /**
     * @return projectKbn
     */
    public int getProjectKbn() {
        return projectKbn__;
    }

    /**
     * @param projectKbn 設定する projectKbn
     */
    public void setProjectKbn(int projectKbn) {
        projectKbn__ = projectKbn;
    }

    /**
     * @return statusKbn
     */
    public int getStatusKbn() {
        return statusKbn__;
    }

    /**
     * @param statusKbn 設定する statusKbn
     */
    public void setStatusKbn(int statusKbn) {
        statusKbn__ = statusKbn;
    }

    /**
     * @return projectCmbList
     */
    public List<LabelValueBean> getProjectCmbList() {
        return projectCmbList__;
    }

    /**
     * @param projectCmbList 設定する projectCmbList
     */
    public void setProjectCmbList(List<LabelValueBean> projectCmbList) {
        projectCmbList__ = projectCmbList;
    }

    /**
     * @return selectingProject
     */
    public String getSelectingProject() {
        return selectingProject__;
    }

    /**
     * @param selectingProject 設定する selectingProject
     */
    public void setSelectingProject(String selectingProject) {
        selectingProject__ = selectingProject;
    }

    /**
     * @return selectingProjectSv
     */
    public String getSelectingProjectSv() {
        return selectingProjectSv__;
    }

    /**
     * @param selectingProjectSv 設定する selectingProjectSv
     */
    public void setSelectingProjectSv(String selectingProjectSv) {
        selectingProjectSv__ = selectingProjectSv;
    }

    /**
     * <p>adr010selectSid を取得します。
     * @return adr010selectSid
     */
    public String[] getAdr010selectSid() {
        return adr010selectSid__;
    }

    /**
     * <p>adr010selectSid をセットします。
     * @param adr010selectSid adr010selectSid
     */
    public void setAdr010selectSid(String[] adr010selectSid) {
        adr010selectSid__ = adr010selectSid;
    }

    /**
     * @return projectCmbsize
     */
    public int getProjectCmbsize() {
        return projectCmbsize__;
    }

    /**
     * @param projectCmbsize 設定する projectCmbsize
     */
    public void setProjectCmbsize(int projectCmbsize) {
        projectCmbsize__ = projectCmbsize;
    }

    /**
     * <p>adr010selectLabelSid を取得します。
     * @return adr010selectLabelSid
     */
    public String[] getAdr010selectLabelSid() {
        return adr010selectLabelSid__;
    }

    /**
     * <p>adr010selectLabelSid をセットします。
     * @param adr010selectLabelSid adr010selectLabelSid
     */
    public void setAdr010selectLabelSid(String[] adr010selectLabelSid) {
        adr010selectLabelSid__ = adr010selectLabelSid;
    }

    /**
     * <p>adr010CoBaseNameContact を取得します。
     * @return adr010CoBaseNameContact
     */
    public String getAdr010CoBaseNameContact() {
        return adr010CoBaseNameContact__;
    }

    /**
     * <p>adr010CoBaseNameContact をセットします。
     * @param adr010CoBaseNameContact adr010CoBaseNameContact
     */
    public void setAdr010CoBaseNameContact(String adr010CoBaseNameContact) {
        adr010CoBaseNameContact__ = adr010CoBaseNameContact;
    }

    /**
     * <p>adr010CoNameContact を取得します。
     * @return adr010CoNameContact
     */
    public String getAdr010CoNameContact() {
        return adr010CoNameContact__;
    }

    /**
     * <p>adr010CoNameContact をセットします。
     * @param adr010CoNameContact adr010CoNameContact
     */
    public void setAdr010CoNameContact(String adr010CoNameContact) {
        adr010CoNameContact__ = adr010CoNameContact;
    }

    /**
     * <p>adr010KeyWordkbnContact を取得します。
     * @return adr010KeyWordkbnContact
     */
    public String getAdr010KeyWordkbnContact() {
        return adr010KeyWordkbnContact__;
    }

    /**
     * <p>adr010KeyWordkbnContact をセットします。
     * @param adr010KeyWordkbnContact adr010KeyWordkbnContact
     */
    public void setAdr010KeyWordkbnContact(String adr010KeyWordkbnContact) {
        adr010KeyWordkbnContact__ = adr010KeyWordkbnContact;
    }

    /**
     * <p>adr010ProjectContact を取得します。
     * @return adr010ProjectContact
     */
    public int getAdr010ProjectContact() {
        return adr010ProjectContact__;
    }

    /**
     * <p>adr010ProjectContact をセットします。
     * @param adr010ProjectContact adr010ProjectContact
     */
    public void setAdr010ProjectContact(int adr010ProjectContact) {
        adr010ProjectContact__ = adr010ProjectContact;
    }

    /**
     * <p>adr010SearchTargetContact を取得します。
     * @return adr010SearchTargetContact
     */
    public String[] getAdr010SearchTargetContact() {
        return adr010SearchTargetContact__;
    }

    /**
     * <p>adr010SearchTargetContact をセットします。
     * @param adr010SearchTargetContact adr010SearchTargetContact
     */
    public void setAdr010SearchTargetContact(String[] adr010SearchTargetContact) {
        adr010SearchTargetContact__ = adr010SearchTargetContact;
    }

    /**
     * <p>adr010SearchWordContact を取得します。
     * @return adr010SearchWordContact
     */
    public String getAdr010SearchWordContact() {
        return adr010SearchWordContact__;
    }

    /**
     * <p>adr010SearchWordContact をセットします。
     * @param adr010SearchWordContact adr010SearchWordContact
     */
    public void setAdr010SearchWordContact(String adr010SearchWordContact) {
        adr010SearchWordContact__ = adr010SearchWordContact;
    }

    /**
     * <p>adr010SltDayFrContact を取得します。
     * @return adr010SltDayFrContact
     */
    public String getAdr010SltDayFrContact() {
        return adr010SltDayFrContact__;
    }

    /**
     * <p>adr010SltDayFrContact をセットします。
     * @param adr010SltDayFrContact adr010SltDayFrContact
     */
    public void setAdr010SltDayFrContact(String adr010SltDayFrContact) {
        adr010SltDayFrContact__ = adr010SltDayFrContact;
    }

    /**
     * <p>adr010SltDayToContact を取得します。
     * @return adr010SltDayToContact
     */
    public String getAdr010SltDayToContact() {
        return adr010SltDayToContact__;
    }

    /**
     * <p>adr010SltDayToContact をセットします。
     * @param adr010SltDayToContact adr010SltDayToContact
     */
    public void setAdr010SltDayToContact(String adr010SltDayToContact) {
        adr010SltDayToContact__ = adr010SltDayToContact;
    }
    
    /**
     * <p>adr010SltMonthFrContact を取得します。
     * @return adr010SltMonthFrContact
     */
    public String getAdr010SltMonthFrContact() {
        return adr010SltMonthFrContact__;
    }

    /**
     * <p>adr010SltMonthFrContact をセットします。
     * @param adr010SltMonthFrContact adr010SltMonthFrContact
     */
    public void setAdr010SltMonthFrContact(String adr010SltMonthFrContact) {
        adr010SltMonthFrContact__ = adr010SltMonthFrContact;
    }

    /**
     * <p>adr010SltMonthToContact を取得します。
     * @return adr010SltMonthToContact
     */
    public String getAdr010SltMonthToContact() {
        return adr010SltMonthToContact__;
    }

    /**
     * <p>adr010SltMonthToContact をセットします。
     * @param adr010SltMonthToContact adr010SltMonthToContact
     */
    public void setAdr010SltMonthToContact(String adr010SltMonthToContact) {
        adr010SltMonthToContact__ = adr010SltMonthToContact;
    }

    /**
     * <p>adr010SltYearFrContact を取得します。
     * @return adr010SltYearFrContact
     */
    public String getAdr010SltYearFrContact() {
        return adr010SltYearFrContact__;
    }

    /**
     * <p>adr010SltYearFrContact をセットします。
     * @param adr010SltYearFrContact adr010SltYearFrContact
     */
    public void setAdr010SltYearFrContact(String adr010SltYearFrContact) {
        adr010SltYearFrContact__ = adr010SltYearFrContact;
    }

    /**
     * <p>adr010SltYearToContact を取得します。
     * @return adr010SltYearToContact
     */
    public String getAdr010SltYearToContact() {
        return adr010SltYearToContact__;
    }

    /**
     * <p>adr010SltYearToContact をセットします。
     * @param adr010SltYearToContact adr010SltYearToContact
     */
    public void setAdr010SltYearToContact(String adr010SltYearToContact) {
        adr010SltYearToContact__ = adr010SltYearToContact;
    }
    
    /**
     * <p>adr010SltFrContact を取得します。
     * @return adr010SltFrContact
     * @see jp.groupsession.v2.adr.adr010.Adr010ParamModel#adr010SltFrContact__
     */
    public String getAdr010SltFrContact() {
        return adr010SltFrContact__;
    }

    /**
     * <p>adr010SltFrContact をセットします。
     * @param adr010SltFrContact adr010SltFrContact
     * @see jp.groupsession.v2.adr.adr010.Adr010ParamModel#adr010SltFrContact__
     */
    public void setAdr010SltFrContact(String adr010SltFrContact) {
        adr010SltFrContact__ = adr010SltFrContact;
    }

    /**
     * <p>adr010SltToContact を取得します。
     * @return adr010SltToContact
     * @see jp.groupsession.v2.adr.adr010.Adr010ParamModel#adr010SltToContact__
     */
    public String getAdr010SltToContact() {
        return adr010SltToContact__;
    }

    /**
     * <p>adr010SltToContact をセットします。
     * @param adr010SltToContact adr010SltToContact
     * @see jp.groupsession.v2.adr.adr010.Adr010ParamModel#adr010SltToContact__
     */
    public void setAdr010SltToContact(String adr010SltToContact) {
        adr010SltToContact__ = adr010SltToContact;
    }

    /**
     * <p>adr010svCoBaseNameContact を取得します。
     * @return adr010svCoBaseNameContact
     */
    public String getAdr010svCoBaseNameContact() {
        return adr010svCoBaseNameContact__;
    }

    /**
     * <p>adr010svCoBaseNameContact をセットします。
     * @param adr010svCoBaseNameContact adr010svCoBaseNameContact
     */
    public void setAdr010svCoBaseNameContact(String adr010svCoBaseNameContact) {
        adr010svCoBaseNameContact__ = adr010svCoBaseNameContact;
    }

    /**
     * <p>adr010svCoNameContact を取得します。
     * @return adr010svCoNameContact
     */
    public String getAdr010svCoNameContact() {
        return adr010svCoNameContact__;
    }

    /**
     * <p>adr010svCoNameContact をセットします。
     * @param adr010svCoNameContact adr010svCoNameContact
     */
    public void setAdr010svCoNameContact(String adr010svCoNameContact) {
        adr010svCoNameContact__ = adr010svCoNameContact;
    }

    /**
     * <p>adr010SvKeyWordkbnContact を取得します。
     * @return adr010SvKeyWordkbnContact
     */
    public String getAdr010SvKeyWordkbnContact() {
        return adr010SvKeyWordkbnContact__;
    }

    /**
     * <p>adr010SvKeyWordkbnContact をセットします。
     * @param adr010SvKeyWordkbnContact adr010SvKeyWordkbnContact
     */
    public void setAdr010SvKeyWordkbnContact(String adr010SvKeyWordkbnContact) {
        adr010SvKeyWordkbnContact__ = adr010SvKeyWordkbnContact;
    }

    /**
     * <p>adr010svProjectContact を取得します。
     * @return adr010svProjectContact
     */
    public int getAdr010svProjectContact() {
        return adr010svProjectContact__;
    }

    /**
     * <p>adr010svProjectContact をセットします。
     * @param adr010svProjectContact adr010svProjectContact
     */
    public void setAdr010svProjectContact(int adr010svProjectContact) {
        adr010svProjectContact__ = adr010svProjectContact;
    }

    /**
     * <p>adr010svSearchTargetContact を取得します。
     * @return adr010svSearchTargetContact
     */
    public String[] getAdr010svSearchTargetContact() {
        return adr010svSearchTargetContact__;
    }

    /**
     * <p>adr010svSearchTargetContact をセットします。
     * @param adr010svSearchTargetContact adr010svSearchTargetContact
     */
    public void setAdr010svSearchTargetContact(String[] adr010svSearchTargetContact) {
        adr010svSearchTargetContact__ = adr010svSearchTargetContact;
    }

    /**
     * <p>adr010svSearchWordContact を取得します。
     * @return adr010svSearchWordContact
     */
    public String getAdr010svSearchWordContact() {
        return adr010svSearchWordContact__;
    }

    /**
     * <p>adr010svSearchWordContact をセットします。
     * @param adr010svSearchWordContact adr010svSearchWordContact
     */
    public void setAdr010svSearchWordContact(String adr010svSearchWordContact) {
        adr010svSearchWordContact__ = adr010svSearchWordContact;
    }

    /**
     * <p>adr010svSltDayFrContact を取得します。
     * @return adr010svSltDayFrContact
     */
    public String getAdr010svSltDayFrContact() {
        return adr010svSltDayFrContact__;
    }

    /**
     * <p>adr010svSltDayFrContact をセットします。
     * @param adr010svSltDayFrContact adr010svSltDayFrContact
     */
    public void setAdr010svSltDayFrContact(String adr010svSltDayFrContact) {
        adr010svSltDayFrContact__ = adr010svSltDayFrContact;
    }

    /**
     * <p>adr010svSltDayToContact を取得します。
     * @return adr010svSltDayToContact
     */
    public String getAdr010svSltDayToContact() {
        return adr010svSltDayToContact__;
    }

    /**
     * <p>adr010svSltDayToContact をセットします。
     * @param adr010svSltDayToContact adr010svSltDayToContact
     */
    public void setAdr010svSltDayToContact(String adr010svSltDayToContact) {
        adr010svSltDayToContact__ = adr010svSltDayToContact;
    }

    /**
     * <p>adr010svSltMonthFrContact を取得します。
     * @return adr010svSltMonthFrContact
     */
    public String getAdr010svSltMonthFrContact() {
        return adr010svSltMonthFrContact__;
    }

    /**
     * <p>adr010svSltMonthFrContact をセットします。
     * @param adr010svSltMonthFrContact adr010svSltMonthFrContact
     */
    public void setAdr010svSltMonthFrContact(String adr010svSltMonthFrContact) {
        adr010svSltMonthFrContact__ = adr010svSltMonthFrContact;
    }

    /**
     * <p>adr010svSltMonthToContact を取得します。
     * @return adr010svSltMonthToContact
     */
    public String getAdr010svSltMonthToContact() {
        return adr010svSltMonthToContact__;
    }

    /**
     * <p>adr010svSltMonthToContact をセットします。
     * @param adr010svSltMonthToContact adr010svSltMonthToContact
     */
    public void setAdr010svSltMonthToContact(String adr010svSltMonthToContact) {
        adr010svSltMonthToContact__ = adr010svSltMonthToContact;
    }

    /**
     * <p>adr010svSltYearFrContact を取得します。
     * @return adr010svSltYearFrContact
     */
    public String getAdr010svSltYearFrContact() {
        return adr010svSltYearFrContact__;
    }

    /**
     * <p>adr010svSltYearFrContact をセットします。
     * @param adr010svSltYearFrContact adr010svSltYearFrContact
     */
    public void setAdr010svSltYearFrContact(String adr010svSltYearFrContact) {
        adr010svSltYearFrContact__ = adr010svSltYearFrContact;
    }

    /**
     * <p>adr010svSltYearToContact を取得します。
     * @return adr010svSltYearToContact
     */
    public String getAdr010svSltYearToContact() {
        return adr010svSltYearToContact__;
    }

    /**
     * <p>adr010svSltYearToContact をセットします。
     * @param adr010svSltYearToContact adr010svSltYearToContact
     */
    public void setAdr010svSltYearToContact(String adr010svSltYearToContact) {
        adr010svSltYearToContact__ = adr010svSltYearToContact;
    }
    
    /**
     * <p>adr010svSltFrContact を取得します。
     * @return adr010svSltFrContact
     * @see jp.groupsession.v2.adr.adr010.Adr010ParamModel#adr010svSltFrContact__
     */
    public String getAdr010svSltFrContact() {
        return adr010svSltFrContact__;
    }

    /**
     * <p>adr010svSltFrContact をセットします。
     * @param adr010svSltFrContact adr010svSltFrContact
     * @see jp.groupsession.v2.adr.adr010.Adr010ParamModel#adr010svSltFrContact__
     */
    public void setAdr010svSltFrContact(String adr010svSltFrContact) {
        adr010svSltFrContact__ = adr010svSltFrContact;
    }

    /**
     * <p>adr010svSltToContact を取得します。
     * @return adr010svSltToContact
     * @see jp.groupsession.v2.adr.adr010.Adr010ParamModel#adr010svSltToContact__
     */
    public String getAdr010svSltToContact() {
        return adr010svSltToContact__;
    }

    /**
     * <p>adr010svSltToContact をセットします。
     * @param adr010svSltToContact adr010svSltToContact
     * @see jp.groupsession.v2.adr.adr010.Adr010ParamModel#adr010svSltToContact__
     */
    public void setAdr010svSltToContact(String adr010svSltToContact) {
        adr010svSltToContact__ = adr010svSltToContact;
    }

    /**
     * <p>adr010svSyubetsuContact を取得します。
     * @return adr010svSyubetsuContact
     */
    public int getAdr010svSyubetsuContact() {
        return adr010svSyubetsuContact__;
    }

    /**
     * <p>adr010svSyubetsuContact をセットします。
     * @param adr010svSyubetsuContact adr010svSyubetsuContact
     */
    public void setAdr010svSyubetsuContact(int adr010svSyubetsuContact) {
        adr010svSyubetsuContact__ = adr010svSyubetsuContact;
    }

    /**
     * <p>adr010SvTempFilekbnContact を取得します。
     * @return adr010SvTempFilekbnContact
     */
    public String getAdr010SvTempFilekbnContact() {
        return adr010SvTempFilekbnContact__;
    }

    /**
     * <p>adr010SvTempFilekbnContact をセットします。
     * @param adr010SvTempFilekbnContact adr010SvTempFilekbnContact
     */
    public void setAdr010SvTempFilekbnContact(String adr010SvTempFilekbnContact) {
        adr010SvTempFilekbnContact__ = adr010SvTempFilekbnContact;
    }

    /**
     * <p>adr010svUnameMeiContact を取得します。
     * @return adr010svUnameMeiContact
     */
    public String getAdr010svUnameMeiContact() {
        return adr010svUnameMeiContact__;
    }

    /**
     * <p>adr010svUnameMeiContact をセットします。
     * @param adr010svUnameMeiContact adr010svUnameMeiContact
     */
    public void setAdr010svUnameMeiContact(String adr010svUnameMeiContact) {
        adr010svUnameMeiContact__ = adr010svUnameMeiContact;
    }

    /**
     * <p>adr010svUnameSeiContact を取得します。
     * @return adr010svUnameSeiContact
     */
    public String getAdr010svUnameSeiContact() {
        return adr010svUnameSeiContact__;
    }

    /**
     * <p>adr010svUnameSeiContact をセットします。
     * @param adr010svUnameSeiContact adr010svUnameSeiContact
     */
    public void setAdr010svUnameSeiContact(String adr010svUnameSeiContact) {
        adr010svUnameSeiContact__ = adr010svUnameSeiContact;
    }

    /**
     * <p>adr010SyubetsuContact を取得します。
     * @return adr010SyubetsuContact
     */
    public int getAdr010SyubetsuContact() {
        return adr010SyubetsuContact__;
    }

    /**
     * <p>adr010SyubetsuContact をセットします。
     * @param adr010SyubetsuContact adr010SyubetsuContact
     */
    public void setAdr010SyubetsuContact(int adr010SyubetsuContact) {
        adr010SyubetsuContact__ = adr010SyubetsuContact;
    }

    /**
     * <p>adr010TempFilekbnContact を取得します。
     * @return adr010TempFilekbnContact
     */
    public String getAdr010TempFilekbnContact() {
        return adr010TempFilekbnContact__;
    }

    /**
     * <p>adr010TempFilekbnContact をセットします。
     * @param adr010TempFilekbnContact adr010TempFilekbnContact
     */
    public void setAdr010TempFilekbnContact(String adr010TempFilekbnContact) {
        adr010TempFilekbnContact__ = adr010TempFilekbnContact;
    }

    /**
     * <p>adr010unameMeiContact を取得します。
     * @return adr010unameMeiContact
     */
    public String getAdr010unameMeiContact() {
        return adr010unameMeiContact__;
    }

    /**
     * <p>adr010unameMeiContact をセットします。
     * @param adr010unameMeiContact adr010unameMeiContact
     */
    public void setAdr010unameMeiContact(String adr010unameMeiContact) {
        adr010unameMeiContact__ = adr010unameMeiContact;
    }

    /**
     * <p>adr010unameSeiContact を取得します。
     * @return adr010unameSeiContact
     */
    public String getAdr010unameSeiContact() {
        return adr010unameSeiContact__;
    }

    /**
     * <p>adr010unameSeiContact をセットします。
     * @param adr010unameSeiContact adr010unameSeiContact
     */
    public void setAdr010unameSeiContact(String adr010unameSeiContact) {
        adr010unameSeiContact__ = adr010unameSeiContact;
    }

    /**
     * <p>adr010InitDspContactFlg を取得します。
     * @return adr010InitDspContactFlg
     */
    public int getAdr010InitDspContactFlg() {
        return adr010InitDspContactFlg__;
    }

    /**
     * <p>adr010InitDspContactFlg をセットします。
     * @param adr010InitDspContactFlg adr010InitDspContactFlg
     */
    public void setAdr010InitDspContactFlg(int adr010InitDspContactFlg) {
        adr010InitDspContactFlg__ = adr010InitDspContactFlg;
    }

    /**
     * <p>adr010dateNoKbn を取得します。
     * @return adr010dateNoKbn
     */
    public int getAdr010dateNoKbn() {
        return adr010dateNoKbn__;
    }

    /**
     * <p>adr010dateNoKbn をセットします。
     * @param adr010dateNoKbn adr010dateNoKbn
     */
    public void setAdr010dateNoKbn(int adr010dateNoKbn) {
        adr010dateNoKbn__ = adr010dateNoKbn;
    }

    /**
     * <p>adr010svdateNoKbn を取得します。
     * @return adr010svdateNoKbn
     */
    public int getAdr010svdateNoKbn() {
        return adr010svdateNoKbn__;
    }

    /**
     * <p>adr010svdateNoKbn をセットします。
     * @param adr010svdateNoKbn adr010svdateNoKbn
     */
    public void setAdr010svdateNoKbn(int adr010svdateNoKbn) {
        adr010svdateNoKbn__ = adr010svdateNoKbn;
    }

    /**
     * <p>adr010SendMailMode を取得します。
     * @return adr010SendMailMode
     */
    public int getAdr010SendMailMode() {
        return adr010SendMailMode__;
    }

    /**
     * <p>adr010SendMailMode をセットします。
     * @param adr010SendMailMode adr010SendMailMode
     */
    public void setAdr010SendMailMode(int adr010SendMailMode) {
        adr010SendMailMode__ = adr010SendMailMode;
    }

    /**
     * <p>adr010AtskList を取得します。
     * @return adr010AtskList
     */
    public List<AdrAddressModel> getAdr010AtskList() {
        return adr010AtskList__;
    }

    /**
     * <p>adr010AtskList をセットします。
     * @param adr010AtskList adr010AtskList
     */
    public void setAdr010AtskList(List<AdrAddressModel> adr010AtskList) {
        adr010AtskList__ = adr010AtskList;
    }

    /**
     * <p>adr010BccList を取得します。
     * @return adr010BccList
     */
    public List<AdrAddressModel> getAdr010BccList() {
        return adr010BccList__;
    }

    /**
     * <p>adr010BccList をセットします。
     * @param adr010BccList adr010BccList
     */
    public void setAdr010BccList(List<AdrAddressModel> adr010BccList) {
        adr010BccList__ = adr010BccList;
    }

    /**
     * <p>adr010SidsAtsk を取得します。
     * @return adr010SidsAtsk
     */
    public String[] getAdr010SidsAtsk() {
        return adr010SidsAtsk__;
    }

    /**
     * <p>adr010SidsAtsk をセットします。
     * @param adr010SidsAtsk adr010SidsAtsk
     */
    public void setAdr010SidsAtsk(String[] adr010SidsAtsk) {
        adr010SidsAtsk__ = adr010SidsAtsk;
    }

    /**
     * <p>adr010SidsBcc を取得します。
     * @return adr010SidsBcc
     */
    public String[] getAdr010SidsBcc() {
        return adr010SidsBcc__;
    }

    /**
     * <p>adr010SidsBcc をセットします。
     * @param adr010SidsBcc adr010SidsBcc
     */
    public void setAdr010SidsBcc(String[] adr010SidsBcc) {
        adr010SidsBcc__ = adr010SidsBcc;
    }

    /**
     * <p>adr010CcList を取得します。
     * @return adr010CcList
     */
    public List<AdrAddressModel> getAdr010CcList() {
        return adr010CcList__;
    }

    /**
     * <p>adr010CcList をセットします。
     * @param adr010CcList adr010CcList
     */
    public void setAdr010CcList(List<AdrAddressModel> adr010CcList) {
        adr010CcList__ = adr010CcList;
    }

    /**
     * <p>adr010SidsCc を取得します。
     * @return adr010SidsCc
     */
    public String[] getAdr010SidsCc() {
        return adr010SidsCc__;
    }

    /**
     * <p>adr010SidsCc をセットします。
     * @param adr010SidsCc adr010SidsCc
     */
    public void setAdr010SidsCc(String[] adr010SidsCc) {
        adr010SidsCc__ = adr010SidsCc;
    }

    /**
     * <p>adr010DelAdrSid を取得します。
     * @return adr010DelAdrSid
     */
    public int getAdr010DelAdrSid() {
        return adr010DelAdrSid__;
    }

    /**
     * <p>adr010DelAdrSid をセットします。
     * @param adr010DelAdrSid adr010DelAdrSid
     */
    public void setAdr010DelAdrSid(int adr010DelAdrSid) {
        adr010DelAdrSid__ = adr010DelAdrSid;
    }

    /**
     * <p>adr010Atsk を取得します。
     * @return adr010Atsk
     */
    public String[] getAdr010Atsk() {
        return adr010Atsk__;
    }

    /**
     * <p>adr010Atsk をセットします。
     * @param adr010Atsk adr010Atsk
     */
    public void setAdr010Atsk(String[] adr010Atsk) {
        adr010Atsk__ = adr010Atsk;
    }

    /**
     * <p>adr010Bcc を取得します。
     * @return adr010Bcc
     */
    public String[] getAdr010Bcc() {
        return adr010Bcc__;
    }

    /**
     * <p>adr010Bcc をセットします。
     * @param adr010Bcc adr010Bcc
     */
    public void setAdr010Bcc(String[] adr010Bcc) {
        adr010Bcc__ = adr010Bcc;
    }

    /**
     * <p>adr010Cc を取得します。
     * @return adr010Cc
     */
    public String[] getAdr010Cc() {
        return adr010Cc__;
    }

    /**
     * <p>adr010Cc をセットします。
     * @param adr010Cc adr010Cc
     */
    public void setAdr010Cc(String[] adr010Cc) {
        adr010Cc__ = adr010Cc;
    }

    /**
     * <p>adr010AdrSid を取得します。
     * @return adr010AdrSid
     */
    public int getAdr010AdrSid() {
        return adr010AdrSid__;
    }

    /**
     * <p>adr010AdrSid をセットします。
     * @param adr010AdrSid adr010AdrSid
     */
    public void setAdr010AdrSid(int adr010AdrSid) {
        adr010AdrSid__ = adr010AdrSid;
    }

    /**
     * <p>adr010AdrType を取得します。
     * @return adr010AdrType
     */
    public int getAdr010AdrType() {
        return adr010AdrType__;
    }

    /**
     * <p>adr010AdrType をセットします。
     * @param adr010AdrType adr010AdrType
     */
    public void setAdr010AdrType(int adr010AdrType) {
        adr010AdrType__ = adr010AdrType;
    }

    /**
     * <p>adr010CaegoryLabelList を取得します。
     * @return adr010CaegoryLabelList
     */
    public ArrayList<AdrCategoryLabelModel> getAdr010CaegoryLabelList() {
        return adr010CaegoryLabelList__;
    }

    /**
     * <p>adr010CaegoryLabelList をセットします。
     * @param adr010CaegoryLabelList adr010CaegoryLabelList
     */
    public void setAdr010CaegoryLabelList(
            ArrayList<AdrCategoryLabelModel> adr010CaegoryLabelList) {
        adr010CaegoryLabelList__ = adr010CaegoryLabelList;
    }

    /**
     * <p>adr010CategoryOpenFlg を取得します。
     * @return adr010CategoryOpenFlg
     */
    public String[] getAdr010CategoryOpenFlg() {
        return adr010CategoryOpenFlg__;
    }

    /**
     * <p>adr010CategoryOpenFlg をセットします。
     * @param adr010CategoryOpenFlg adr010CategoryOpenFlg
     */
    public void setAdr010CategoryOpenFlg(String[] adr010CategoryOpenFlg) {
        adr010CategoryOpenFlg__ = adr010CategoryOpenFlg;
    }

    /**
     * <p>photoFileSid を取得します。
     * @return photoFileSid
     */
    public Long getPhotoFileSid() {
        return photoFileSid__;
    }

    /**
     * <p>photoFileSid をセットします。
     * @param photoFileSid photoFileSid
     */
    public void setPhotoFileSid(Long photoFileSid) {
        photoFileSid__ = photoFileSid;
    }

    /**
     * <p>adr010CategorySetInitFlg を取得します。
     * @return adr010CategorySetInitFlg
     */
    public int getAdr010CategorySetInitFlg() {
        return adr010CategorySetInitFlg__;
    }

    /**
     * <p>adr010CategorySetInitFlg をセットします。
     * @param adr010CategorySetInitFlg adr010CategorySetInitFlg
     */
    public void setAdr010CategorySetInitFlg(int adr010CategorySetInitFlg) {
        adr010CategorySetInitFlg__ = adr010CategorySetInitFlg;
    }

    /**
     * <p>adr010syozoku を取得します。
     * @return adr010syozoku
     */
    public String getAdr010syozoku() {
        return adr010syozoku__;
    }

    /**
     * <p>adr010syozoku をセットします。
     * @param adr010syozoku adr010syozoku
     */
    public void setAdr010syozoku(String adr010syozoku) {
        adr010syozoku__ = adr010syozoku;
    }

    /**
     * <p>adr010svSyozoku を取得します。
     * @return adr010svSyozoku
     */
    public String getAdr010svSyozoku() {
        return adr010svSyozoku__;
    }

    /**
     * <p>adr010svSyozoku をセットします。
     * @param adr010svSyozoku adr010svSyozoku
     */
    public void setAdr010svSyozoku(String adr010svSyozoku) {
        adr010svSyozoku__ = adr010svSyozoku;
    }

    /**
     * <p>adr010AbleEdit を取得します。
     * @return adr010AbleEdit
     */
    public int getAdr010AbleEdit() {
        return adr010AbleEdit__;
    }

    /**
     * <p>adr010AbleEdit をセットします。
     * @param adr010AbleEdit adr010AbleEdit
     */
    public void setAdr010AbleEdit(int adr010AbleEdit) {
        adr010AbleEdit__ = adr010AbleEdit;
    }
    /**
     * <p>adr330searchBean を取得します。
     * @return adr330searchBean
     */
    public Adr330ViewSearchModel getAdr330searchBean() {
        return adr330searchBean__;
    }

    /**
     * <p>adr330searchBean をセットします。
     * @param adr330searchBean adr330searchBean
     */
    public void setAdr330searchBean(Adr330ViewSearchModel adr330searchBean) {
        adr330searchBean__ = adr330searchBean;
    }

    /**
     * <p>adr330searchSVBean を取得します。
     * @return adr330searchSVBean
     */
    public Adr330ViewSearchModel getAdr330searchSVBean() {
        return adr330searchSVBean__;
    }

    /**
     * <p>adr330searchSVBean をセットします。
     * @param adr330searchSVBean adr330searchSVBean
     */
    public void setAdr330searchSVBean(Adr330ViewSearchModel adr330searchSVBean) {
        adr330searchSVBean__ = adr330searchSVBean;
    }

    /**
     * <p>adr330searchFlg を取得します。
     * @return adr330searchFlg
     */
    public int getAdr330searchFlg() {
        return adr330searchFlg__;
    }

    /**
     * <p>adr330searchFlg をセットします。
     * @param adr330searchFlg adr330searchFlg
     */
    public void setAdr330searchFlg(int adr330searchFlg) {
        adr330searchFlg__ = adr330searchFlg;
    }

    /**
     * <p>adr330back を取得します。
     * @return adr330back
     */
    public int getAdr330back() {
        return adr330back__;
    }

    /**
     * <p>adr330back をセットします。
     * @param adr330back adr330back
     */
    public void setAdr330back(int adr330back) {
        adr330back__ = adr330back;
    }
}