package jp.groupsession.v2.wml.wml010;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.AbstractWmlForm;
import jp.groupsession.v2.wml.GSValidateWebmail;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlMailSendBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.model.WmlValidateMailAddressResultModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.wml010.model.Wml010AccountModel;


/**
 * <br>[機  能] WEBメール メール一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml010Form extends AbstractWmlForm {

    /** プラグイン 使用不可 */
    public static final int PLUGIN_NOUSE = 0;
    /** プラグイン 使用可能 */
    public static final int PLUGIN_USE = 1;

    /** アドレス帳 全て */
    public static final int ADDRESS_TYPE_ALL = 1;
    /** アドレス帳 担当アドレス */
    public static final int ADDRESS_TYPE_TANTO = 0;

    /** 送信メール 送信予約 未設定 */
    public static final int SENDMAILPLAN_TYPE_NOSET = 0;
    /** 送信メール 送信予約 指定日時に送信する */
    public static final int SENDMAILPLAN_TYPE_LATER = 1;

    /** 送信メール 本文 カーソル位置 挿入種別 通常 */
    public static final int SENDMAIL_CURSORTYPE_NORMAL = 0;
    /** 送信メール 本文 カーソル位置 挿入種別 HTML */
    public static final int SENDMAIL_CURSORTYPE_HTML = 1;

    /** 管理者ユーザフラグ */
    private boolean wml010adminUser__ = false;

    /** 表示ディレクトリ */
    private long wml010viewDirectory__ = -1;
    /** 表示ディレクトリ種別 */
    private long wml010viewDirectoryType__ = -1;
    /** 表示ラベル */
    private int wml010viewLabel__ = -1;
    /** 削除メール(ゴミ箱フォルダ内のメール)を対象とするか */
    private int wml010viewDelMail__ = 0;
    /** 表示メッセージ番号 */
    private long wml010viewMailNum__ = -1;
    /** 表示アカウント FromAddress */
    private String wml010viewAccountAddress__ = null;

    /** 表示アカウント自動保存 */
    private int wml010viewAccountAutoSave__ = 0;

    /** ページ */
    private int wml010selectPage__ = 0;
    /** ページ上段 */
    private int wml010pageTop__ = 0;
    /** ページ下段 */
    private int wml010pageBottom__ = 0;
    /** ソートキー */
    private int wml010sortKey__ = 0;
    /** 並び順 */
    private int wml010order__ = 0;

    /** ユーザ情報プラグイン使用可能フラグ */
    private int wml010pluginUserUse__ = PLUGIN_NOUSE;
    /** アドレス帳プラグイン使用可能フラグ */
    private int wml010pluginAddressUse__ = PLUGIN_NOUSE;
    /** 回覧板プラグイン使用可能フラグ */
    private int wml010pluginCircularUse__ = PLUGIN_NOUSE;
    /** ショートメールプラグイン使用可能フラグ */
    private int wml010pluginSmailUse__ = PLUGIN_NOUSE;
    /** ファイル管理プラグイン使用可能フラグ */
    private int wml010pluginFilekanriUse__ = PLUGIN_NOUSE;

    /** ページ上段(検索結果) */
    private int wml010searchPageTop__ = 0;
    /** ページ下段(検索結果) */
    private int wml010searchPageBottom__ = 0;
    /** ソートキー(検索結果) */
    private int wml010searchSortKey__ = 0;
    /** 並び順(検索結果) */
    private int wml010searchOrder__ = 0;

    /** 検索条件 検索キーワード */
    private String wml010searchKeywordNml__ = null;
    /** 検索実行フラグ */
    private int wml010searchFlg__ = 0;
    /** 検索種別 */
    private int wml010searchType__ = Wml010Const.SEARCHTYPE_KEYWORD;

    /** 検索条件 From */
    private String wml010searchFrom__ = null;
    /** 検索条件 送信先 */
    private String wml010searchTo__ = null;
    /** 検索条件 送信先 宛先 */
    private int wml010searchToKbnTo__ = 0;
    /** 検索条件 送信先 CC */
    private int wml010searchToKbnCc__ = 0;
    /** 検索条件 送信先 BCC */
    private int wml010searchToKbnBcc__ = 0;

    /** 検索条件 日付 */
    private String wml010searchDateType__ = Wml010Const.SEARCH_DATE_NOSET;
    /** 検索条件 日付 From 年 */
    private String wml010searchDateYearFr__ = null;
    /** 検索条件 日付 From 月 */
    private String wml010searchDateMonthFr__ = null;
    /** 検索条件 日付 From 日 */
    private String wml010searchDateDayFr__ = null;
    /** 検索条件 日付 To 年 */
    private String wml010searchDateYearTo__ = null;
    /** 検索条件 日付 To 月 */
    private String wml010searchDateMonthTo__ = null;
    /** 検索条件 日付 To 日 */
    private String wml010searchDateDayTo__ = null;
    /** 検索条件 日付 From 年月日 */
    private String wml010searchDateFr__ = null;
    /** 検索条件 日付 To 年月日 */
    private String wml010searchDateTo__ = null;
    /** 検索条件 添付ファイル */
    private int wml010searchTempFile__ = 0;
    /** 検索条件 キーワード 区分 */
    private String wml010searchKeywordKbn__ = null;
    /** 検索条件 キーワード */
    private String wml010searchKeyword__ = null;
    /** 検索条件 未読/既読 */
    private int wml010searchReadKbn__ = Wml010Const.SEARCH_READKBN_NOSET;

    /** 検索条件 検索キーワード(検索条件保持)　*/
    private String wml010svSearchKeywordNml__ = null;

    /** 検索条件 From(検索条件保持) */
    private String wml010svSearchFrom__ = null;
    /** 検索条件 送信先(検索条件保持) */
    private String wml010svSearchTo__ = null;
    /** 検索条件 送信先 宛先(検索条件保持) */
    private int wml010svSearchToKbnTo__ = 0;
    /** 検索条件 送信先 CC(検索条件保持) */
    private int wml010svSearchToKbnCc__ = 0;
    /** 検索条件 送信先 BCC(検索条件保持) */
    private int wml010svSearchToKbnBcc__ = 0;
    /** 検索条件 日付(検索条件保持) */
    private String wml010svSearchDateType__ = null;
    /** 検索条件 日付 From 年(検索条件保持) */
    private String wml010svSearchDateYearFr__ = null;
    /** 検索条件 日付 From 月(検索条件保持) */
    private String wml010svSearchDateMonthFr__ = null;
    /** 検索条件 日付 From 日(検索条件保持) */
    private String wml010svSearchDateDayFr__ = null;
    /** 検索条件 日付 To 年(検索条件保持) */
    private String wml010svSearchDateYearTo__ = null;
    /** 検索条件 日付 To 月(検索条件保持) */
    private String wml010svSearchDateMonthTo__ = null;
    /** 検索条件 日付 To 日(検索条件保持) */
    private String wml010svSearchDateDayTo__ = null;
    /** 検索条件 添付ファイル */
    private int wml010svSearchTempFile__ = 0;
    /** 検索条件 キーワード 区分(検索条件保持) */
    private String wml010svSearchKeywordKbn__ = null;
    /** 検索条件 キーワード(検索条件保持) */
    private String wml010svSearchKeyword__ = null;
    /** 検索条件 未読/既読(検索条件保持) */
    private int wml010svSearchReadKbn__ = Wml010Const.SEARCH_READKBN_NOSET;

    /** 添付ファイル メッセージ番号 */
    private long wml010downloadMessageNum__ = 0;
    /** 添付ファイルSID */
    private long wml010downloadFileId__ = 0;
    /** 削除対象添付ファイルSID */
    private long[] wml010delFileId__ = null;

    /** 選択されたメールのメッセージ番号 */
    private long[] wml010selectMessageNum__ = null;
    /** 選択されたメールのディレクトリSID */
    private int[] wml010selectMessageDirId__ = null;

    /** ラベル追加種別 0:既存のラベルを付加、1:新規登録したラベルを追加 */
    private int wml010addLabelType__ = Wml010Const.ADDLABEL_NORMAL;
    /** 追加ラベルSID */
    private int wml010addLabel__ = 0;
    /** 追加ラベル名(ラベル新規登録) */
    private String wml010addLabelName__ = null;
    /** 削除ラベルSID */
    private int wml010delLabel__ = 0;

    /** メールの移動先フォルダ(ディレクトリSID */
    private long wml010moveFolder__ = 0;

    /** メール送信アカウント */
    private int wml010sendAccount__ = 0;
    /** メール送信アカウント 署名 */
    private int wml010sendSign__ = 0;
    /** メール送信アカウント 署名(変更前) */
    private int wml010sendSignOld__ = 0;
    /** メール送信アカウント メールテンプレート */
    private int wml010sendTemplate__ = 0;
    /** 返信 or 転送元メッセージ番号  */
    private long wml010sendMessageNum__ = 0;
    /** 送信先 TO */
    private String wml010sendAddressTo__ = null;
    /** 送信先 CC */
    private String wml010sendAddressCc__ = null;
    /** 送信先 BCC */
    private String wml010sendAddressBcc__ = null;
    /** 送信メール 件名 */
    private String wml010sendSubject__ = null;
    /** 送信メール 本文 */
    private String wml010sendContent__ = null;
    /** 送信メール 本文 保存用*/
    private String wml010svSendContent__ = null;
    /** 送信メール 本文 カーソル位置 */
    private int wml010sendContentCursor__ = 0;
    /** 送信メール 本文 カーソル位置 挿入種別 */
    private int wml010sendContentCursorType__ = 0;
    /** 送信メール 種別 */
    private int wml010sendMailType__ = GSConstWebmail.SEND_TYPE_NORMAL;
    /** 送信メール HTMLメール */
    private int wml010sendMailHtml__ = GSConstWebmail.SEND_HTMLMAIL_TEXT;
    /** 送信メール テンポラリディレクトリID */
    private String wml010tempDirId__ = null;
    /** 送信メール 添付ファイル */
    private List<FormFile> wml010sendMailFile__ = new ArrayList<FormFile>();
    /** 送信メール ダウンロード対象添付ファイル */
    private String wml010sendMailDownloadFile__ = null;
    /** 送信メール 送信予約 */
    private int sendMailPlanType__ = SENDMAILPLAN_TYPE_NOSET;
    /** 送信メール 送信予定日時 年 */
    private String wml010sendMailPlanDateYear__ = null;
    /** 送信メール 送信予定日時 月 */
    private String wml010sendMailPlanDateMonth__ = null;
    /** 送信メール 送信予定日時 日 */
    private String wml010sendMailPlanDateDay__ = null;
    /** 送信メール 送信予定日時 年月日 */
    private String wml010sendMailPlanDate__ = null;
    /** 送信メール 送信予定日時 時 */
    private String wml010sendMailPlanDateHour__ = null;
    /** 送信メール 送信予定日時 分 */
    private String wml010sendMailPlanDateMinute__ = null;
    /** 送信メール 送信予定日時 時分 */
    private String wml010sendMailPlanTime__ = null;
    /** 送信メール 送信予約 即時送信 */
    private int wml010sendMailPlanImm__ = 0;
    /** 送信メール 添付ファイル圧縮 */
    private int wml010sendTempfileCompress__ = 0;

    /** アカウントコンボ */
    private List<LabelValueBean> accountCombo__ = null;
    /** アカウント情報一覧(選択されていない) */
    private List<Wml010AccountModel> accountLinkList__ = null;
    /** キーワードコンボ */
    private List<LabelValueBean> keywordCombo__ = null;
    /** ラベルコンボ */
    private List<LabelValueBean> labelCombo__ = null;
    /** フォルダコンボ */
    private List<LabelValueBean> folderCombo__ = null;
    /** 送信先リストコンボ */
    private List<LabelValueBean> destlistCombo__ = null;

    /** ユーザ情報 グループ */
    private int wml010shainGroup__ = -1;
    /** ユーザ情報 グループコンボ */
    private List<LabelValueBean> shainGroupCombo__ = null;

    /** アドレス帳 種別 */
    private int wml010addressType__ = ADDRESS_TYPE_TANTO;

    /** メール本文最大文字数 */
    private int wml010maxBodySize__ = GSConstWebmail.MAILBODY_LIMIT_NOLIMIT;

    /** テーマ */
    private String wml010theme__ = null;
    /** 宛先の確認 */
    private int wml010checkAddress__ = 0;
    /** 添付ファイルの確認 */
    private int wml010checkFile__ = 0;

    /** 送信先リストSID */
    private int wml010destlistId__ = 0;
    /** メッセージNo */
    private int wml011mailNum__ = 0;

    /** ショートメールからメール共有用パラメータ*/
    /** ショートメール共有フラグ*/
    private int wml010smlShareFlg__ = 0;
    /** ショートメール共有作成画面閉じる押下フラグ*/
    private int wml010smlShareCloseFlg__ = 0;
    /** タイトル*/
    private String wml010smlShareTitle__ = null;
    /** 本文*/
    private String wml010smlShareBody__ = null;
    /** テンポラリディレクトリパス*/
    private String wml010smlShareTemp__ = null;
    /** htmlFlg*/
    private int wml010smlShareHtml__ = 0;

    /** メール本文に挿入したファイルの一時保存ディレクトリID */
    private String wml010TempSaveId__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Wml010Form() {
        wml010sendMailFile__ = new ArrayList<FormFile>();
    }

    /**
     * <br>[機  能] 共通メッセージ画面遷移時に保持するパラメータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param msgForm 共通メッセージ画面Form
     */
    public void setHiddenParam(Cmn999Form msgForm) {
        super.setHiddenParam(msgForm);

        msgForm.addHiddenParam("wml010viewDirectory", wml010viewDirectory__);
        msgForm.addHiddenParam("wml010viewDirectoryType", wml010viewDirectoryType__);
        msgForm.addHiddenParam("wml010viewMailNum", wml010viewMailNum__);
        msgForm.addHiddenParam("wml010viewAccountAddress", wml010viewAccountAddress__);
        msgForm.addHiddenParam("wml010viewAccountAutoSave", wml010viewAccountAutoSave__);
        msgForm.addHiddenParam("wml010selectPage", wml010selectPage__);
        msgForm.addHiddenParam("wml010pageTop", wml010pageTop__);
        msgForm.addHiddenParam("wml010pageBottom", wml010pageBottom__);
        msgForm.addHiddenParam("wml010sortKey", wml010sortKey__);
        msgForm.addHiddenParam("wml010order", wml010order__);
        msgForm.addHiddenParam("wml010searchKeywordNml", wml010searchKeywordNml__);
        msgForm.addHiddenParam("wml010searchFlg", wml010searchFlg__);
        msgForm.addHiddenParam("wml010searchFrom", wml010searchFrom__);
        msgForm.addHiddenParam("wml010searchTo", wml010searchTo__);
        msgForm.addHiddenParam("wml010searchToKbnTo", wml010searchToKbnTo__);
        msgForm.addHiddenParam("wml010searchToKbnCc", wml010searchToKbnCc__);
        msgForm.addHiddenParam("wml010searchToKbnBcc", wml010searchToKbnBcc__);
        msgForm.addHiddenParam("wml010searchDateType", wml010searchDateType__);
        msgForm.addHiddenParam("wml010searchDateYearFr", wml010searchDateYearFr__);
        msgForm.addHiddenParam("wml010searchDateMonthFr", wml010searchDateMonthFr__);
        msgForm.addHiddenParam("wml010searchDateDayFr", wml010searchDateDayFr__);
        msgForm.addHiddenParam("wml010searchDateYearTo", wml010searchDateYearTo__);
        msgForm.addHiddenParam("wml010searchDateMonthTo", wml010searchDateMonthTo__);
        msgForm.addHiddenParam("wml010searchDateDayTo", wml010searchDateDayTo__);
        msgForm.addHiddenParam("wml010searchTempFile", wml010searchTempFile__);
        msgForm.addHiddenParam("wml010searchKeywordKbn", wml010searchKeywordKbn__);
        msgForm.addHiddenParam("wml010searchKeyword", wml010searchKeyword__);
        msgForm.addHiddenParam("wml010searchReadKbn", wml010searchReadKbn__);
        msgForm.addHiddenParam("wml010svSearchKeywordNml", wml010svSearchKeywordNml__);
        msgForm.addHiddenParam("wml010svSearchFrom", wml010svSearchFrom__);
        msgForm.addHiddenParam("wml010svSearchTo", wml010svSearchTo__);
        msgForm.addHiddenParam("wml010svSearchToKbnTo", wml010svSearchToKbnTo__);
        msgForm.addHiddenParam("wml010svSearchToKbnCc", wml010svSearchToKbnCc__);
        msgForm.addHiddenParam("wml010svSearchToKbnBcc", wml010svSearchToKbnBcc__);
        msgForm.addHiddenParam("wml010svSearchDateType", wml010svSearchDateType__);
        msgForm.addHiddenParam("wml010svSearchDateYearFr", wml010svSearchDateYearFr__);
        msgForm.addHiddenParam("wml010svSearchDateMonthFr", wml010svSearchDateMonthFr__);
        msgForm.addHiddenParam("wml010svSearchDateDayFr", wml010svSearchDateDayFr__);
        msgForm.addHiddenParam("wml010svSearchDateYearTo", wml010svSearchDateYearTo__);
        msgForm.addHiddenParam("wml010svSearchDateMonthTo", wml010svSearchDateMonthTo__);
        msgForm.addHiddenParam("wml010svSearchDateDayTo", wml010svSearchDateDayTo__);
        msgForm.addHiddenParam("wml010svSearchTempFile", wml010svSearchTempFile__);
        msgForm.addHiddenParam("wml010svSearchKeywordKbn", wml010svSearchKeywordKbn__);
        msgForm.addHiddenParam("wml010svSearchKeyword", wml010svSearchKeyword__);
        msgForm.addHiddenParam("wml010svSearchReadKbn", wml010svSearchReadKbn__);
    }

    /**
     * <br>[機  能] 検索条件(キーワード検索)の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラーメッセージ
     */
    public String[] validateSearchKeyword(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage();
        return Wml010Validate.validateText(wml010searchKeywordNml__,
                gsMsg.getMessage("cmn.search.keyword"),
                GSConstWebmail.MAXLEN_SEARCH_KEYWORD,
                true, reqMdl);
    }

    /**
     * <br>[機  能] 検索条件(詳細検索)の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラーメッセージ
     * @throws NoSuchMethodException 
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     */
    public String[] validateSearchDetail(RequestModel reqMdl)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        GsMessage gsMsg = new GsMessage(reqMdl);
        //From
        String[] message = Wml010Validate.validateText(wml010searchFrom__,
                gsMsg.getMessage("cmn.sendfrom"),
                256,
                false, reqMdl);
        //送信先
        if (!StringUtil.isNullZeroString(wml010searchTo__)) {
            if (wml010searchToKbnTo__ != Wml010ParamModel.DESTINATION_SELECT
                    && wml010searchToKbnCc__ != Wml010ParamModel.DESTINATION_SELECT
                    && wml010searchToKbnBcc__ != Wml010ParamModel.DESTINATION_SELECT) {
                String[] elName = new String[] {gsMsg.getMessage("wml.send.dest")};
                message = __addMessage(message,
                        new String[] {gsMsg.getMessage("cmn.select.4",
                                elName)});
            }
            message = __addMessage(message,
                    Wml010Validate.validateText(wml010searchTo__,
                            gsMsg.getMessage("wml.send.dest"),
                            256,
                            false, reqMdl));
        }

        //キーワード
        message = __addMessage(message,
                Wml010Validate.validateText(wml010searchKeyword__,
                        gsMsg.getMessage("cmn.keyword"),
                        GSConstWebmail.MAXLEN_SEARCH_KEYWORD,
                        false, reqMdl));

        //日付
        if (NullDefault.getString(wml010searchDateType__, "").equals(Wml010Const.SEARCH_DATE_SET)) {
            DateTimePickerBiz picker = new DateTimePickerBiz();
            ActionErrors errors = new ActionErrors();
            int errCnt = errors.size();
            //日付 From
            String[] dateMessage = null;
            String[] msg = new String[]{
                    gsMsg.getMessageVal0("wml.225", gsMsg.getMessage("wml.202"))};
            if (!StringUtil.isNullZeroString(wml010searchDateFr__)) {
                errors.add(picker.setYmdParam(this, "wml010searchDateFr",
                        "wml010searchDateYearFr", "wml010searchDateMonthFr",
                        "wml010searchDateDayFr", gsMsg.getMessage("wml.202")));
                if (errors.size() > 0) {
                    dateMessage = __addMessage(dateMessage, msg);
                }
            }
            if (errCnt == errors.size()) {
                dateMessage = __addMessage(dateMessage, __checkDate(
                        gsMsg.getMessage("wml.202"),
                        wml010searchDateYearFr__,
                        wml010searchDateMonthFr__,
                        wml010searchDateDayFr__, reqMdl));
            }
            errCnt = errors.size();

            //日付 To
            if (!StringUtil.isNullZeroString(wml010searchDateTo__)) {
                errors.add(picker.setYmdParam(this, "wml010searchDateTo",
                        "wml010searchDateYearTo", "wml010searchDateMonthTo",
                        "wml010searchDateDayTo", gsMsg.getMessage("wml.206")));
                if (errCnt != errors.size()) {
                    msg = new String[]{
                            gsMsg.getMessageVal0("wml.225", gsMsg.getMessage("wml.206"))};
                    dateMessage = __addMessage(dateMessage, msg);
                }
            }
            if (errCnt == errors.size()) {
                dateMessage = __addMessage(dateMessage,
                        __checkDate(gsMsg.getMessage("wml.206"),
                                wml010searchDateYearTo__,
                                wml010searchDateMonthTo__,
                                wml010searchDateDayTo__, reqMdl));
            }

            if (dateMessage == null || dateMessage.length == 0) {
                UDate frDate = new UDate();
                frDate.setDate(Integer.parseInt(wml010searchDateYearFr__),
                        Integer.parseInt(wml010searchDateMonthFr__),
                        Integer.parseInt(wml010searchDateDayFr__));

                UDate toDate = new UDate();
                toDate.setDate(Integer.parseInt(wml010searchDateYearTo__),
                        Integer.parseInt(wml010searchDateMonthTo__),
                        Integer.parseInt(wml010searchDateDayTo__));

                if (frDate.compareDateYMD(toDate) == UDate.SMALL) {
                    dateMessage = __addMessage(dateMessage,
                            new String[] {gsMsg.getMessage("wml.205")});
                }
            }

            message = __addMessage(message, dateMessage);
        }
        return message;
    }

    /**
     * <br>[機  能] 送信メールの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsContext GroupSession共通情報
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリ
     * @param reqMdl リクエスト情報
     * @return エラーメッセージ
     * @throws Exception 送信メールサイズチェック時に例外発生
     */
    public String[] validateSendMail(Connection con,
            GSContext gsContext, int userSid,
            String appRootPath, String tempDir,
            RequestModel reqMdl)
                    throws Exception {
        return this.validateSendMail(con, gsContext, userSid, appRootPath, tempDir, reqMdl, 0);
    }

    /**
     * <br>[機  能] 送信メールの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsContext GroupSession共通情報
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリ
     * @param reqMdl リクエスト情報
     * @param fileSize サーバーに保存されていない添付ファイルサイズ
     * @return エラーメッセージ
     * @throws Exception 送信メールサイズチェック時に例外発生
     */
    public String[] validateSendMail(Connection con,
            GSContext gsContext, int userSid,
            String appRootPath, String tempDir,
            RequestModel reqMdl,
            long fileSize)
                    throws Exception {
        GsMessage gsMsg = new GsMessage();
        String[] message = null;

        if (StringUtil.isNullZeroString(wml010sendAddressTo__)
                && StringUtil.isNullZeroString(wml010sendAddressCc__)
                && StringUtil.isNullZeroString(wml010sendAddressBcc__)) {
            //送信先が未入力
            message = new String[]{
                    gsMsg.getMessage("cmn.plz.input",
                            new String[] {
                                    gsMsg.getMessage("anp.send.dest")})};
        } else {
            Wml010ParamModel paramMdl = new Wml010ParamModel();
            paramMdl.setParam(this);

            Wml010Biz biz = new Wml010Biz();
            WmlAccountDao accountDao = new WmlAccountDao(con);
            WmlAccountModel accountData = accountDao.select(biz._getSendAccountSid(paramMdl));
            String encode = WmlBiz.getSendEncode(accountData);

            WmlBiz wBiz = new WmlBiz();
            WmlAdmConfModel wadMdl = wBiz.getAdminConf(con); // 管理者設定
            String[] tldList = wBiz.getTldList(wadMdl);

            //To
            message = __validateSendMailAddress(wml010sendAddressTo__,
                    gsMsg.getMessage("cmn.from"),
                    false,
                    reqMdl, encode, tldList);
            //Cc
            message = __addMessage(message,
                    __validateSendMailAddress(wml010sendAddressCc__,
                            "CC",
                            false,
                            reqMdl, encode, tldList));
            //Bcc
            message = __addMessage(message,
                    __validateSendMailAddress(wml010sendAddressBcc__,
                            "BCC",
                            false,
                            reqMdl, encode, tldList));
        }

        //件名
        message = __addMessage(message,
                Wml010Validate.validateText(wml010sendSubject__,
                        gsMsg.getMessage("cmn.subject"),
                        GSConstWebmail.MAXLEN_MAIL_SUBJECT,
                        false, reqMdl));

        //予約送信日付
        message = __addMessage(message, __validateSendPlanDate(reqMdl, true));

        //本文
        int bodyLimit = WmlBiz.getBodyLimitLength(appRootPath);
        String body = WmlMailSendBiz.getFormattedBody(
            wml010svSendContent__, bodyLimit, wml010sendMailHtml__);

        message = __addMessage(message,
                GSValidateWebmail.validateHonbun(body,
                        gsMsg.getMessage("cmn.body"),
                        bodyLimit,
                        true, reqMdl));

        return message;
    }

    /**
     * <br>[機  能] 送信メール(草稿に保存)の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsContext GroupSession共通情報
     * @param userSid ユーザSID
     * @param appRootPath アプリケーションルートパス
     * @param tempDir テンポラリディレクトリ
     * @param reqMdl リクエスト情報
     * @param fileSize サーバーに保存されていない添付ファイルサイズ
     * @return エラーメッセージ
     * @throws Exception 送信メールサイズチェック時に例外発生
     */
    public String[] validateSendMailDraft(Connection con,
            GSContext gsContext, int userSid,
            String appRootPath, String tempDir,
            RequestModel reqMdl, long fileSize)
    throws Exception {
        GsMessage gsMsg = new GsMessage();

        //To
        String[] message = Wml010Validate.validateText(wml010sendAddressTo__,
                                                    gsMsg.getMessage("cmn.from"),
                                                    -1,
                                                    false, reqMdl);
        //Cc
        message = __addMessage(message,
                            Wml010Validate.validateText(wml010sendAddressCc__,
                                                        "CC",
                                                        -1,
                                                        false, reqMdl));
        //Bcc
        message = __addMessage(message,
                            Wml010Validate.validateText(wml010sendAddressBcc__,
                                                        "BCC",
                                                        -1,
                                                        false, reqMdl));
        //件名
        message = __addMessage(message,
                            Wml010Validate.validateText(wml010sendSubject__,
                                                        gsMsg.getMessage("cmn.subject"),
                                                        100,
                                                        false, reqMdl));

        //予約送信日付
        message = __addMessage(message, __validateSendPlanDate(reqMdl, false));

        //本文
        message = __addMessage(message,
                        GSValidateWebmail.validateHonbun(wml010svSendContent__,
                        gsMsg.getMessage("cmn.body"),
                        -1,
                        false, reqMdl));

        // ファイルサイズ取得

        return message;
    }

    /**
     * <br>[機  能] 日付チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramName 名称
     * @param year 年
     * @param month 月
     * @param day 日
     * @param reqMdl リクエスト情報
     * @return エラーメッセージ
     */
    private String[] __checkDate(String paramName,
            String year, String month, String day, RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (StringUtil.isNullZeroString(year)
                || StringUtil.isNullZeroString(month)
                || StringUtil.isNullZeroString(day)
                || !ValidateUtil.isNumber(year)
                || !ValidateUtil.isNumber(month)
                || !ValidateUtil.isNumber(day)) {
            return new String[]{gsMsg.getMessage("wml.226", new String[] {paramName})};
        }

        UDate date = new UDate();
        date.setDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        if (date.getYear() != Integer.parseInt(year)
                || date.getMonth() != Integer.parseInt(month)
                || date.getIntDay() != Integer.parseInt(day)) {
            return new String[]{gsMsg.getMessage("wml.225", new String[] {paramName})};
        }

        return null;
    }

    /**
     * <br>[機  能] メッセージの追加を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param message メッセージ
     * @param newMessage 追加メッセージ
     * @return メッセージ
     */
    private String[] __addMessage(String[] message, String[] newMessage) {

        if (newMessage != null) {
            if (message != null) {
                String[] msg = new String[message.length + newMessage.length];
                System.arraycopy(message, 0, msg, 0, message.length);
                System.arraycopy(newMessage, 0, msg, message.length, newMessage.length);
                return msg;
            } else {
                return newMessage;
            }
        }

        return message;
    }

    //    /**
    //     * <br>[機  能] 送信先メールアドレスの入力チェックを行う
    //     * <br>[解  説]
    //     * <br>[備  考]
    //     * @param address メールアドレス
    //     * @param name パラメータ名
    //     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
    //     * @param reqMdl リクエスト情報
    //     * @param encode 文字コード
    //     * @return エラーメッセージ
    //     */
    //    private String[] __validateSendMailAddress(String address, String name,
    //                                                boolean checkNoInput, RequestModel reqMdl,
    //                                                String encode) {
    //
    //        String[] message = Wml010Validate.validateText(
    //                               address, name, -1, checkNoInput, reqMdl);
    //        if (message == null || message.length == 0) {
    //
    //            if (!StringUtil.isNullZeroString(address)
    //            && (message == null || message.length == 0)) {
    //                List<String> errAddressList = new ArrayList<String>();
    //                InternetAddress[] addressList = null;
    //                try {
    //                    addressList = WmlBiz.parseAddressPlus(address, encode);
    //                } catch (Exception e) {
    //                    log__.debug("エラー発生アドレス" + address);
    //
    //                    String[] mailList = address.split(",");
    //                    for (String mail : mailList) {
    //                        try {
    //                            addressList = WmlBiz.parseAddressPlus(mail, encode);
    //                            for (InternetAddress addressData : addressList) {
    //                                if (!GSValidateUtil.isMailFormat(addressData.getAddress())) {
    //                                    errAddressList.add(mail);
    //                                    break;
    //                                }
    //                            }
    //                        } catch (Exception mailE) {
    //                            errAddressList.add(mail);
    //                        }
    //                    }
    //                    addressList = null;
    //                }
    //
    //                GsMessage gsMsg = new GsMessage(reqMdl);
    //                if (addressList == null || addressList.length == 0) {
    //                    if (errAddressList.isEmpty()) {
    //                        message = new String[] {gsMsg.getMessage(
    //                                   "wml.221", new String[] {name})};
    //                    }
    //                } else {
    //                    //メールアドレスフォーマットチェック
    //                    for (InternetAddress addressData : addressList) {
    //                        if (!GSValidateUtil.isMailFormat(addressData.getAddress())) {
    //                            errAddressList.add(addressData.getAddress());
    //                        }
    //                    }
    //                }
    //
    //                if (!errAddressList.isEmpty()) {
    //                    String errAddressMsg = "";
    //                    for (String errAddress : errAddressList) {
    //                        errAddressMsg += "・" + errAddress + "\r\n";
    //                    }
    //
    //                    message = new String[] {
    //                            gsMsg.getMessage("wml.274", new String[] {name, errAddressMsg})};
    //                }
    //            }
    //        }
    //
    //        return message;
    //    }

    /**
     * <br>[機  能] 送信先メールアドレスの入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param address メールアドレス
     * @param nameJp パラメータ名(日本語名)
     * @param checkNoInput 未入力チェック true:チェックする false:チェックしない
     * @param reqMdl リクエスト情報
     * @param encode 文字コード
     * @param tldList TLD制限許可リスト
     * @return エラーメッセージ
     */
    private String[] __validateSendMailAddress(String address, String nameJp,
            boolean checkNoInput, RequestModel reqMdl,
            String encode, String[] tldList) {

        String[] message = Wml010Validate.validateText(address, nameJp, -1, checkNoInput, reqMdl);
        if (message == null || message.length == 0) {

            WmlValidateMailAddressResultModel resultMdl
            = GSValidateWebmail.validateMailAddress(
                    address, nameJp,
                    reqMdl, encode, tldList);
            message = resultMdl.getErrMessage();
        }

        return message;
    }

    /**
     * <br>[機  能] 予約送信の入力チェックを行う
     * <br>[解  説]
     * <br>[備  考] 引数のmessageにエラーメッセージを追加する
     * @param reqMdl リクエスト情報
     * @param checkFutureFlg 未来日をチェックするか true:チェックする, false:チェックしない
     * @return エラー内容
     */
    private String[] __validateSendPlanDate(RequestModel reqMdl, boolean checkFutureFlg)
        throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        String[] ret = null;

        //予約送信ではない場合はチェックを行わない
        if (getSendMailPlanType() != 1) {
            return ret;
        }

        GsMessage gsMsg = new GsMessage(reqMdl);
        ActionErrors errors = new ActionErrors();
        DateTimePickerBiz picker = new DateTimePickerBiz();
        int errorCnt = errors.size();
        String rsvDate = gsMsg.getMessage("wml.211") + gsMsg.getMessage("cmn.date2");
        String rsvTime = gsMsg.getMessage("wml.211") + gsMsg.getMessage("cmn.time");
        errors.add(picker.setYmdParam(this,
                "wml010sendMailPlanDate",
                "wml010sendMailPlanDateYear",
                "wml010sendMailPlanDateMonth",
                "wml010sendMailPlanDateDay",
                rsvDate));
        if (errorCnt != errors.size()) {
            ret = __addMessage(ret, new String[]{gsMsg.getMessageVal0("wml.225", rsvDate)});
        }
        errorCnt = errors.size();        
        errors.add(picker.setHmParam(this,
                "wml010sendMailPlanTime",
                "wml010sendMailPlanDateHour",
                "wml010sendMailPlanDateMinute",
                rsvTime));
        if (errorCnt != errors.size()) {
            ret = __addMessage(ret, new String[]{gsMsg.getMessageVal0("cmn.plz.input", rsvTime)});
        }

        //日付フォーマット, 時間フォーマットでエラーがある場合はチェックを終了
        if (ret != null) {
            return ret;
        }

        //未来の日付が設定されているかを確認する
        if (checkFutureFlg) {
            UDate now = new UDate();
            UDate sendPlanDate = now.cloneUDate();
            sendPlanDate.setTimeStamp(Integer.parseInt(getWml010sendMailPlanDateYear()),
                    Integer.parseInt(getWml010sendMailPlanDateMonth()),
                    Integer.parseInt(getWml010sendMailPlanDateDay()),
                    Integer.parseInt(getWml010sendMailPlanDateHour()),
                    Integer.parseInt(getWml010sendMailPlanDateMinute()),
                    0);
            if (now.compareDateYMDHM(sendPlanDate) != UDate.LARGE) {
                ret = __addMessage(ret, new String[]{gsMsg.getMessage("wml.wml010.33")});
            }
        }

        return ret;
    }

    /**
     * <p>wml010adminUser を取得します。
     * @return wml010adminUser
     */
    public boolean isWml010adminUser() {
        return wml010adminUser__;
    }

    /**
     * <p>wml010adminUser をセットします。
     * @param wml010adminUser wml010adminUser
     */
    public void setWml010adminUser(boolean wml010adminUser) {
        wml010adminUser__ = wml010adminUser;
    }

    /**
     * <p>accountCombo を取得します。
     * @return accountCombo
     */
    public List<LabelValueBean> getAccountCombo() {
        return accountCombo__;
    }

    /**
     * <p>accountCombo をセットします。
     * @param accountCombo accountCombo
     */
    public void setAccountCombo(List<LabelValueBean> accountCombo) {
        accountCombo__ = accountCombo;
    }

    /**
     * <p>accountLinkList を取得します。
     * @return accountLinkList
     */
    public List<Wml010AccountModel> getAccountLinkList() {
        return accountLinkList__;
    }

    /**
     * <p>accountLinkList をセットします。
     * @param accountLinkList accountLinkList
     */
    public void setAccountLinkList(List<Wml010AccountModel> accountLinkList) {
        accountLinkList__ = accountLinkList;
    }

    /**
     * <p>keywordCombo を取得します。
     * @return keywordCombo
     */
    public List<LabelValueBean> getKeywordCombo() {
        return keywordCombo__;
    }

    /**
     * <p>keywordCombo をセットします。
     * @param keywordCombo keywordCombo
     */
    public void setKeywordCombo(List<LabelValueBean> keywordCombo) {
        keywordCombo__ = keywordCombo;
    }

    /**
     * <p>wml010selectPage を取得します。
     * @return wml010selectPage
     */
    public int getWml010selectPage() {
        return wml010selectPage__;
    }

    /**
     * <p>wml010selectPage をセットします。
     * @param wml010selectPage wml010selectPage
     */
    public void setWml010selectPage(int wml010selectPage) {
        wml010selectPage__ = wml010selectPage;
    }

    /**
     * <p>wml010pageBottom を取得します。
     * @return wml010pageBottom
     */
    public int getWml010pageBottom() {
        return wml010pageBottom__;
    }

    /**
     * <p>wml010pageBottom をセットします。
     * @param wml010pageBottom wml010pageBottom
     */
    public void setWml010pageBottom(int wml010pageBottom) {
        wml010pageBottom__ = wml010pageBottom;
    }

    /**
     * <p>wml010pageTop を取得します。
     * @return wml010pageTop
     */
    public int getWml010pageTop() {
        return wml010pageTop__;
    }

    /**
     * <p>wml010pageTop をセットします。
     * @param wml010pageTop wml010pageTop
     */
    public void setWml010pageTop(int wml010pageTop) {
        wml010pageTop__ = wml010pageTop;
    }

    /**
     * <p>wml010searchDateDayFr を取得します。
     * @return wml010searchDateDayFr
     */
    public String getWml010searchDateDayFr() {
        return wml010searchDateDayFr__;
    }

    /**
     * <p>wml010searchDateDayFr をセットします。
     * @param wml010searchDateDayFr wml010searchDateDayFr
     */
    public void setWml010searchDateDayFr(String wml010searchDateDayFr) {
        wml010searchDateDayFr__ = wml010searchDateDayFr;
    }

    /**
     * <p>wml010searchDateDayTo を取得します。
     * @return wml010searchDateDayTo
     */
    public String getWml010searchDateDayTo() {
        return wml010searchDateDayTo__;
    }

    /**
     * <p>wml010searchDateDayTo をセットします。
     * @param wml010searchDateDayTo wml010searchDateDayTo
     */
    public void setWml010searchDateDayTo(String wml010searchDateDayTo) {
        wml010searchDateDayTo__ = wml010searchDateDayTo;
    }

    /**
     * <p>wml010searchDateMonthFr を取得します。
     * @return wml010searchDateMonthFr
     */
    public String getWml010searchDateMonthFr() {
        return wml010searchDateMonthFr__;
    }

    /**
     * <p>wml010searchDateMonthFr をセットします。
     * @param wml010searchDateMonthFr wml010searchDateMonthFr
     */
    public void setWml010searchDateMonthFr(String wml010searchDateMonthFr) {
        wml010searchDateMonthFr__ = wml010searchDateMonthFr;
    }

    /**
     * <p>wml010searchDateMonthTo を取得します。
     * @return wml010searchDateMonthTo
     */
    public String getWml010searchDateMonthTo() {
        return wml010searchDateMonthTo__;
    }

    /**
     * <p>wml010searchDateMonthTo をセットします。
     * @param wml010searchDateMonthTo wml010searchDateMonthTo
     */
    public void setWml010searchDateMonthTo(String wml010searchDateMonthTo) {
        wml010searchDateMonthTo__ = wml010searchDateMonthTo;
    }

    /**
     * <p>wml010searchDateType を取得します。
     * @return wml010searchDateType
     */
    public String getWml010searchDateType() {
        return wml010searchDateType__;
    }

    /**
     * <p>wml010searchDateType をセットします。
     * @param wml010searchDateType wml010searchDateType
     */
    public void setWml010searchDateType(String wml010searchDateType) {
        wml010searchDateType__ = wml010searchDateType;
    }

    /**
     * <p>wml010searchDateYearFr を取得します。
     * @return wml010searchDateYearFr
     */
    public String getWml010searchDateYearFr() {
        return wml010searchDateYearFr__;
    }

    /**
     * <p>wml010searchDateYearFr をセットします。
     * @param wml010searchDateYearFr wml010searchDateYearFr
     */
    public void setWml010searchDateYearFr(String wml010searchDateYearFr) {
        wml010searchDateYearFr__ = wml010searchDateYearFr;
    }

    /**
     * <p>wml010searchDateYearTo を取得します。
     * @return wml010searchDateYearTo
     */
    public String getWml010searchDateYearTo() {
        return wml010searchDateYearTo__;
    }

    /**
     * <p>wml010searchDateYearTo をセットします。
     * @param wml010searchDateYearTo wml010searchDateYearTo
     */
    public void setWml010searchDateYearTo(String wml010searchDateYearTo) {
        wml010searchDateYearTo__ = wml010searchDateYearTo;
    }

    /**
     * <p>wml010searchFrom を取得します。
     * @return wml010searchFrom
     */
    public String getWml010searchFrom() {
        return wml010searchFrom__;
    }

    /**
     * <p>wml010searchFrom をセットします。
     * @param wml010searchFrom wml010searchFrom
     */
    public void setWml010searchFrom(String wml010searchFrom) {
        wml010searchFrom__ = wml010searchFrom;
    }

    /**
     * <p>wml010searchKeyword を取得します。
     * @return wml010searchKeyword
     */
    public String getWml010searchKeyword() {
        return wml010searchKeyword__;
    }

    /**
     * <p>wml010searchKeyword をセットします。
     * @param wml010searchKeyword wml010searchKeyword
     */
    public void setWml010searchKeyword(String wml010searchKeyword) {
        wml010searchKeyword__ = wml010searchKeyword;
    }

    /**
     * <p>wml010searchKeywordKbn を取得します。
     * @return wml010searchKeywordKbn
     */
    public String getWml010searchKeywordKbn() {
        return wml010searchKeywordKbn__;
    }

    /**
     * <p>wml010searchKeywordKbn をセットします。
     * @param wml010searchKeywordKbn wml010searchKeywordKbn
     */
    public void setWml010searchKeywordKbn(String wml010searchKeywordKbn) {
        wml010searchKeywordKbn__ = wml010searchKeywordKbn;
    }

    /**
     * <p>wml010searchTo を取得します。
     * @return wml010searchTo
     */
    public String getWml010searchTo() {
        return wml010searchTo__;
    }

    /**
     * <p>wml010searchTo をセットします。
     * @param wml010searchTo wml010searchTo
     */
    public void setWml010searchTo(String wml010searchTo) {
        wml010searchTo__ = wml010searchTo;
    }

    /**
     * <p>wml010searchToKbnTo を取得します。
     * @return wml010searchToKbnTo
     */
    public int getWml010searchToKbnTo() {
        return wml010searchToKbnTo__;
    }

    /**
     * <p>wml010searchToKbnTo をセットします。
     * @param wml010searchToKbnTo wml010searchToKbnTo
     */
    public void setWml010searchToKbnTo(int wml010searchToKbnTo) {
        wml010searchToKbnTo__ = wml010searchToKbnTo;
    }

    /**
     * <p>wml010searchToKbnCc を取得します。
     * @return wml010searchToKbnCc
     */
    public int getWml010searchToKbnCc() {
        return wml010searchToKbnCc__;
    }

    /**
     * <p>wml010searchToKbnCc をセットします。
     * @param wml010searchToKbnCc wml010searchToKbnCc
     */
    public void setWml010searchToKbnCc(int wml010searchToKbnCc) {
        wml010searchToKbnCc__ = wml010searchToKbnCc;
    }

    /**
     * <p>wml010searchToKbnBcc を取得します。
     * @return wml010searchToKbnBcc
     */
    public int getWml010searchToKbnBcc() {
        return wml010searchToKbnBcc__;
    }

    /**
     * <p>wml010searchToKbnBcc をセットします。
     * @param wml010searchToKbnBcc wml010searchToKbnBcc
     */
    public void setWml010searchToKbnBcc(int wml010searchToKbnBcc) {
        wml010searchToKbnBcc__ = wml010searchToKbnBcc;
    }

    /**
     * <p>wml010svSearchDateDayFr を取得します。
     * @return wml010svSearchDateDayFr
     */
    public String getWml010svSearchDateDayFr() {
        return wml010svSearchDateDayFr__;
    }

    /**
     * <p>wml010svSearchDateDayFr をセットします。
     * @param wml010svSearchDateDayFr wml010svSearchDateDayFr
     */
    public void setWml010svSearchDateDayFr(String wml010svSearchDateDayFr) {
        wml010svSearchDateDayFr__ = wml010svSearchDateDayFr;
    }

    /**
     * <p>wml010svSearchDateDayTo を取得します。
     * @return wml010svSearchDateDayTo
     */
    public String getWml010svSearchDateDayTo() {
        return wml010svSearchDateDayTo__;
    }

    /**
     * <p>wml010svSearchDateDayTo をセットします。
     * @param wml010svSearchDateDayTo wml010svSearchDateDayTo
     */
    public void setWml010svSearchDateDayTo(String wml010svSearchDateDayTo) {
        wml010svSearchDateDayTo__ = wml010svSearchDateDayTo;
    }

    /**
     * <p>wml010svSearchDateMonthFr を取得します。
     * @return wml010svSearchDateMonthFr
     */
    public String getWml010svSearchDateMonthFr() {
        return wml010svSearchDateMonthFr__;
    }

    /**
     * <p>wml010svSearchDateMonthFr をセットします。
     * @param wml010svSearchDateMonthFr wml010svSearchDateMonthFr
     */
    public void setWml010svSearchDateMonthFr(String wml010svSearchDateMonthFr) {
        wml010svSearchDateMonthFr__ = wml010svSearchDateMonthFr;
    }

    /**
     * <p>wml010svSearchDateMonthTo を取得します。
     * @return wml010svSearchDateMonthTo
     */
    public String getWml010svSearchDateMonthTo() {
        return wml010svSearchDateMonthTo__;
    }

    /**
     * <p>wml010svSearchDateMonthTo をセットします。
     * @param wml010svSearchDateMonthTo wml010svSearchDateMonthTo
     */
    public void setWml010svSearchDateMonthTo(String wml010svSearchDateMonthTo) {
        wml010svSearchDateMonthTo__ = wml010svSearchDateMonthTo;
    }

    /**
     * <p>wml010svSearchDateType を取得します。
     * @return wml010svSearchDateType
     */
    public String getWml010svSearchDateType() {
        return wml010svSearchDateType__;
    }

    /**
     * <p>wml010svSearchDateType をセットします。
     * @param wml010svSearchDateType wml010svSearchDateType
     */
    public void setWml010svSearchDateType(String wml010svSearchDateType) {
        wml010svSearchDateType__ = wml010svSearchDateType;
    }

    /**
     * <p>wml010svSearchDateYearFr を取得します。
     * @return wml010svSearchDateYearFr
     */
    public String getWml010svSearchDateYearFr() {
        return wml010svSearchDateYearFr__;
    }

    /**
     * <p>wml010svSearchDateYearFr をセットします。
     * @param wml010svSearchDateYearFr wml010svSearchDateYearFr
     */
    public void setWml010svSearchDateYearFr(String wml010svSearchDateYearFr) {
        wml010svSearchDateYearFr__ = wml010svSearchDateYearFr;
    }

    /**
     * <p>wml010svSearchDateYearTo を取得します。
     * @return wml010svSearchDateYearTo
     */
    public String getWml010svSearchDateYearTo() {
        return wml010svSearchDateYearTo__;
    }

    /**
     * <p>wml010svSearchDateYearTo をセットします。
     * @param wml010svSearchDateYearTo wml010svSearchDateYearTo
     */
    public void setWml010svSearchDateYearTo(String wml010svSearchDateYearTo) {
        wml010svSearchDateYearTo__ = wml010svSearchDateYearTo;
    }

    /**
     * <p>wml010svSearchFrom を取得します。
     * @return wml010svSearchFrom
     */
    public String getWml010svSearchFrom() {
        return wml010svSearchFrom__;
    }

    /**
     * <p>wml010svSearchFrom をセットします。
     * @param wml010svSearchFrom wml010svSearchFrom
     */
    public void setWml010svSearchFrom(String wml010svSearchFrom) {
        wml010svSearchFrom__ = wml010svSearchFrom;
    }

    /**
     * <p>wml010svSearchKeyword を取得します。
     * @return wml010svSearchKeyword
     */
    public String getWml010svSearchKeyword() {
        return wml010svSearchKeyword__;
    }

    /**
     * <p>wml010svSearchKeyword をセットします。
     * @param wml010svSearchKeyword wml010svSearchKeyword
     */
    public void setWml010svSearchKeyword(String wml010svSearchKeyword) {
        wml010svSearchKeyword__ = wml010svSearchKeyword;
    }

    /**
     * <p>wml010svSearchKeywordKbn を取得します。
     * @return wml010svSearchKeywordKbn
     */
    public String getWml010svSearchKeywordKbn() {
        return wml010svSearchKeywordKbn__;
    }

    /**
     * <p>wml010svSearchKeywordKbn をセットします。
     * @param wml010svSearchKeywordKbn wml010svSearchKeywordKbn
     */
    public void setWml010svSearchKeywordKbn(String wml010svSearchKeywordKbn) {
        wml010svSearchKeywordKbn__ = wml010svSearchKeywordKbn;
    }

    /**
     * <p>wml010svSearchTo を取得します。
     * @return wml010svSearchTo
     */
    public String getWml010svSearchTo() {
        return wml010svSearchTo__;
    }

    /**
     * <p>wml010svSearchTo をセットします。
     * @param wml010svSearchTo wml010svSearchTo
     */
    public void setWml010svSearchTo(String wml010svSearchTo) {
        wml010svSearchTo__ = wml010svSearchTo;
    }

    /**
     * <p>wml010svSearchToKbnTo を取得します。
     * @return wml010svSearchToKbnTo
     */
    public int getWml010svSearchToKbnTo() {
        return wml010svSearchToKbnTo__;
    }

    /**
     * <p>wml010svSearchToKbnTo をセットします。
     * @param wml010svSearchToKbnTo wml010svSearchToKbnTo
     */
    public void setWml010svSearchToKbnTo(int wml010svSearchToKbnTo) {
        wml010svSearchToKbnTo__ = wml010svSearchToKbnTo;
    }

    /**
     * <p>wml010svSearchToKbnCc を取得します。
     * @return wml010svSearchToKbnCc
     */
    public int getWml010svSearchToKbnCc() {
        return wml010svSearchToKbnCc__;
    }

    /**
     * <p>wml010svSearchToKbnCc をセットします。
     * @param wml010svSearchToKbnCc wml010svSearchToKbnCc
     */
    public void setWml010svSearchToKbnCc(int wml010svSearchToKbnCc) {
        wml010svSearchToKbnCc__ = wml010svSearchToKbnCc;
    }

    /**
     * <p>wml010svSearchToKbnBcc を取得します。
     * @return wml010svSearchToKbnBcc
     */
    public int getWml010svSearchToKbnBcc() {
        return wml010svSearchToKbnBcc__;
    }

    /**
     * <p>wml010svSearchToKbnBcc をセットします。
     * @param wml010svSearchToKbnBcc wml010svSearchToKbnBcc
     */
    public void setWml010svSearchToKbnBcc(int wml010svSearchToKbnBcc) {
        wml010svSearchToKbnBcc__ = wml010svSearchToKbnBcc;
    }

    /**
     * <p>wml010viewDirectory を取得します。
     * @return wml010viewDirectory
     */
    public long getWml010viewDirectory() {
        return wml010viewDirectory__;
    }

    /**
     * <p>wml010viewDirectory をセットします。
     * @param wml010viewDirectory wml010viewDirectory
     */
    public void setWml010viewDirectory(long wml010viewDirectory) {
        wml010viewDirectory__ = wml010viewDirectory;
    }

    /**
     * <p>wml010viewDirectoryType を取得します。
     * @return wml010viewDirectoryType
     */
    public long getWml010viewDirectoryType() {
        return wml010viewDirectoryType__;
    }

    /**
     * <p>wml010viewDirectoryType をセットします。
     * @param wml010viewDirectoryType wml010viewDirectoryType
     */
    public void setWml010viewDirectoryType(long wml010viewDirectoryType) {
        wml010viewDirectoryType__ = wml010viewDirectoryType;
    }

    /**
     * <p>wml010viewLabel を取得します。
     * @return wml010viewLabel
     */
    public int getWml010viewLabel() {
        return wml010viewLabel__;
    }

    /**
     * <p>wml010viewLabel をセットします。
     * @param wml010viewLabel wml010viewLabel
     */
    public void setWml010viewLabel(int wml010viewLabel) {
        wml010viewLabel__ = wml010viewLabel;
    }

    /**
     * <p>wml010viewDelMail を取得します。
     * @return wml010viewDelMail
     */
    public int getWml010viewDelMail() {
        return wml010viewDelMail__;
    }

    /**
     * <p>wml010viewDelMail をセットします。
     * @param wml010viewDelMail wml010viewDelMail
     */
    public void setWml010viewDelMail(int wml010viewDelMail) {
        wml010viewDelMail__ = wml010viewDelMail;
    }

    /**
     * <p>wml010viewMailNum を取得します。
     * @return wml010viewMailNum
     */
    public long getWml010viewMailNum() {
        return wml010viewMailNum__;
    }

    /**
     * <p>wml010viewMailNum をセットします。
     * @param wml010viewMailNum wml010viewMailNum
     */
    public void setWml010viewMailNum(long wml010viewMailNum) {
        wml010viewMailNum__ = wml010viewMailNum;
    }

    /**
     * <p>wml010viewAccountAddress を取得します。
     * @return wml010viewAccountAddress
     */
    public String getWml010viewAccountAddress() {
        return wml010viewAccountAddress__;
    }

    /**
     * <p>wml010viewAccountAddress をセットします。
     * @param wml010viewAccountAddress wml010viewAccountAddress
     */
    public void setWml010viewAccountAddress(String wml010viewAccountAddress) {
        wml010viewAccountAddress__ = wml010viewAccountAddress;
    }

    /**
     * <p>wml010viewAccountAutoSave を取得します。
     * @return wml010viewAccountAutoSave
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010viewAccountAutoSave__
     */
    public int getWml010viewAccountAutoSave() {
        return wml010viewAccountAutoSave__;
    }

    /**
     * <p>wml010viewAccountAutoSave をセットします。
     * @param wml010viewAccountAutoSave wml010viewAccountAutoSave
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010viewAccountAutoSave__
     */
    public void setWml010viewAccountAutoSave(int wml010viewAccountAutoSave) {
        wml010viewAccountAutoSave__ = wml010viewAccountAutoSave;
    }

    /**
     * <p>wml010searchTempFile を取得します。
     * @return wml010searchTempFile
     */
    public int getWml010searchTempFile() {
        return wml010searchTempFile__;
    }

    /**
     * <p>wml010searchTempFile をセットします。
     * @param wml010searchTempFile wml010searchTempFile
     */
    public void setWml010searchTempFile(int wml010searchTempFile) {
        wml010searchTempFile__ = wml010searchTempFile;
    }

    /**
     * <p>wml010svSearchTempFile を取得します。
     * @return wml010svSearchTempFile
     */
    public int getWml010svSearchTempFile() {
        return wml010svSearchTempFile__;
    }

    /**
     * <p>wml010svSearchTempFile をセットします。
     * @param wml010svSearchTempFile wml010svSearchTempFile
     */
    public void setWml010svSearchTempFile(int wml010svSearchTempFile) {
        wml010svSearchTempFile__ = wml010svSearchTempFile;
    }

    /**
     * <p>wml010searchKeywordNml を取得します。
     * @return wml010searchKeywordNml
     */
    public String getWml010searchKeywordNml() {
        return wml010searchKeywordNml__;
    }

    /**
     * <p>wml010searchKeywordNml をセットします。
     * @param wml010searchKeywordNml wml010searchKeywordNml
     */
    public void setWml010searchKeywordNml(String wml010searchKeywordNml) {
        wml010searchKeywordNml__ = wml010searchKeywordNml;
    }

    /**
     * <p>wml010svSearchKeywordNml を取得します。
     * @return wml010svSearchKeywordNml
     */
    public String getWml010svSearchKeywordNml() {
        return wml010svSearchKeywordNml__;
    }

    /**
     * <p>wml010svSearchKeywordNml をセットします。
     * @param wml010svSearchKeywordNml wml010svSearchKeywordNml
     */
    public void setWml010svSearchKeywordNml(String wml010svSearchKeywordNml) {
        wml010svSearchKeywordNml__ = wml010svSearchKeywordNml;
    }

    /**
     * <p>wml010searchFlg を取得します。
     * @return wml010searchFlg
     */
    public int getWml010searchFlg() {
        return wml010searchFlg__;
    }

    /**
     * <p>wml010searchFlg をセットします。
     * @param wml010searchFlg wml010searchFlg
     */
    public void setWml010searchFlg(int wml010searchFlg) {
        wml010searchFlg__ = wml010searchFlg;
    }

    /**
     * <p>wml010order を取得します。
     * @return wml010order
     */
    public int getWml010order() {
        return wml010order__;
    }

    /**
     * <p>wml010order をセットします。
     * @param wml010order wml010order
     */
    public void setWml010order(int wml010order) {
        wml010order__ = wml010order;
    }

    /**
     * <p>wml010sortKey を取得します。
     * @return wml010sortKey
     */
    public int getWml010sortKey() {
        return wml010sortKey__;
    }

    /**
     * <p>wml010sortKey をセットします。
     * @param wml010sortKey wml010sortKey
     */
    public void setWml010sortKey(int wml010sortKey) {
        wml010sortKey__ = wml010sortKey;
    }

    /**
     * <p>wml010downloadFileId を取得します。
     * @return wml010downloadFileId
     */
    public long getWml010downloadFileId() {
        return wml010downloadFileId__;
    }

    /**
     * <p>wml010downloadFileId をセットします。
     * @param wml010downloadFileId wml010downloadFileId
     */
    public void setWml010downloadFileId(long wml010downloadFileId) {
        wml010downloadFileId__ = wml010downloadFileId;
    }

    /**
     * <p>wml010downloadMessageNum を取得します。
     * @return wml010downloadMessageNum
     */
    public long getWml010downloadMessageNum() {
        return wml010downloadMessageNum__;
    }

    /**
     * <p>wml010downloadMessageNum をセットします。
     * @param wml010downloadMessageNum wml010downloadMessageNum
     */
    public void setWml010downloadMessageNum(long wml010downloadMessageNum) {
        wml010downloadMessageNum__ = wml010downloadMessageNum;
    }

    /**
     * <p>labelCombo を取得します。
     * @return labelCombo
     */
    public List<LabelValueBean> getLabelCombo() {
        return labelCombo__;
    }

    /**
     * <p>labelCombo をセットします。
     * @param labelCombo labelCombo
     */
    public void setLabelCombo(List<LabelValueBean> labelCombo) {
        labelCombo__ = labelCombo;
    }

    /**
     * <p>wml010addLabel を取得します。
     * @return wml010addLabel
     */
    public int getWml010addLabel() {
        return wml010addLabel__;
    }

    /**
     * <p>wml010addLabel をセットします。
     * @param wml010addLabel wml010addLabel
     */
    public void setWml010addLabel(int wml010addLabel) {
        wml010addLabel__ = wml010addLabel;
    }

    /**
     * <p>wml010addLabelName を取得します。
     * @return wml010addLabelName
     */
    public String getWml010addLabelName() {
        return wml010addLabelName__;
    }

    /**
     * <p>wml010addLabelName をセットします。
     * @param wml010addLabelName wml010addLabelName
     */
    public void setWml010addLabelName(String wml010addLabelName) {
        wml010addLabelName__ = wml010addLabelName;
    }

    /**
     * <p>wml010addLabelType を取得します。
     * @return wml010addLabelType
     */
    public int getWml010addLabelType() {
        return wml010addLabelType__;
    }

    /**
     * <p>wml010addLabelType をセットします。
     * @param wml010addLabelType wml010addLabelType
     */
    public void setWml010addLabelType(int wml010addLabelType) {
        wml010addLabelType__ = wml010addLabelType;
    }

    /**
     * <p>wml010delLabel を取得します。
     * @return wml010delLabel
     */
    public int getWml010delLabel() {
        return wml010delLabel__;
    }

    /**
     * <p>wml010delLabel をセットします。
     * @param wml010delLabel wml010delLabel
     */
    public void setWml010delLabel(int wml010delLabel) {
        wml010delLabel__ = wml010delLabel;
    }

    /**
     * <p>wml010delFileId を取得します。
     * @return wml010delFileId
     */
    public long[] getWml010delFileId() {
        return wml010delFileId__;
    }

    /**
     * <p>wml010delFileId をセットします。
     * @param wml010delFileId wml010delFileId
     */
    public void setWml010delFileId(long[] wml010delFileId) {
        wml010delFileId__ = wml010delFileId;
    }

    /**
     * <p>wml010searchType を取得します。
     * @return wml010searchType
     */
    public int getWml010searchType() {
        return wml010searchType__;
    }

    /**
     * <p>wml010searchType をセットします。
     * @param wml010searchType wml010searchType
     */
    public void setWml010searchType(int wml010searchType) {
        wml010searchType__ = wml010searchType;
    }

    /**
     * <p>wml010searchOrder を取得します。
     * @return wml010searchOrder
     */
    public int getWml010searchOrder() {
        return wml010searchOrder__;
    }

    /**
     * <p>wml010searchOrder をセットします。
     * @param wml010searchOrder wml010searchOrder
     */
    public void setWml010searchOrder(int wml010searchOrder) {
        wml010searchOrder__ = wml010searchOrder;
    }

    /**
     * <p>wml010searchPageBottom を取得します。
     * @return wml010searchPageBottom
     */
    public int getWml010searchPageBottom() {
        return wml010searchPageBottom__;
    }

    /**
     * <p>wml010searchPageBottom をセットします。
     * @param wml010searchPageBottom wml010searchPageBottom
     */
    public void setWml010searchPageBottom(int wml010searchPageBottom) {
        wml010searchPageBottom__ = wml010searchPageBottom;
    }

    /**
     * <p>wml010searchPageTop を取得します。
     * @return wml010searchPageTop
     */
    public int getWml010searchPageTop() {
        return wml010searchPageTop__;
    }

    /**
     * <p>wml010searchPageTop をセットします。
     * @param wml010searchPageTop wml010searchPageTop
     */
    public void setWml010searchPageTop(int wml010searchPageTop) {
        wml010searchPageTop__ = wml010searchPageTop;
    }

    /**
     * <p>wml010searchSortKey を取得します。
     * @return wml010searchSortKey
     */
    public int getWml010searchSortKey() {
        return wml010searchSortKey__;
    }

    /**
     * <p>wml010searchSortKey をセットします。
     * @param wml010searchSortKey wml010searchSortKey
     */
    public void setWml010searchSortKey(int wml010searchSortKey) {
        wml010searchSortKey__ = wml010searchSortKey;
    }

    /**
     * <p>wml010sendMessageNum を取得します。
     * @return wml010sendMessageNum
     */
    public long getWml010sendMessageNum() {
        return wml010sendMessageNum__;
    }

    /**
     * <p>wml010sendMessageNum をセットします。
     * @param wml010sendMessageNum wml010sendMessageNum
     */
    public void setWml010sendMessageNum(long wml010sendMessageNum) {
        wml010sendMessageNum__ = wml010sendMessageNum;
    }

    /**
     * <p>wml010selectMessageDirId を取得します。
     * @return wml010selectMessageDirId
     */
    public int[] getWml010selectMessageDirId() {
        return wml010selectMessageDirId__;
    }

    /**
     * <p>wml010selectMessageDirId をセットします。
     * @param wml010selectMessageDirId wml010selectMessageDirId
     */
    public void setWml010selectMessageDirId(int[] wml010selectMessageDirId) {
        wml010selectMessageDirId__ = wml010selectMessageDirId;
    }

    /**
     * <p>wml010selectMessageNum を取得します。
     * @return wml010selectMessageNum
     */
    public long[] getWml010selectMessageNum() {
        return wml010selectMessageNum__;
    }

    /**
     * <p>wml010selectMessageNum をセットします。
     * @param wml010selectMessageNum wml010selectMessageNum
     */
    public void setWml010selectMessageNum(long[] wml010selectMessageNum) {
        wml010selectMessageNum__ = wml010selectMessageNum;
    }

    /**
     * <p>wml010sendAccount を取得します。
     * @return wml010sendAccount
     */
    public int getWml010sendAccount() {
        return wml010sendAccount__;
    }

    /**
     * <p>wml010sendAccount をセットします。
     * @param wml010sendAccount wml010sendAccount
     */
    public void setWml010sendAccount(int wml010sendAccount) {
        wml010sendAccount__ = wml010sendAccount;
    }

    /**
     * <p>wml010sendTemplate を取得します。
     * @return wml010sendTemplate
     */
    public int getWml010sendTemplate() {
        return wml010sendTemplate__;
    }

    /**
     * <p>wml010sendTemplate をセットします。
     * @param wml010sendTemplate wml010sendTemplate
     */
    public void setWml010sendTemplate(int wml010sendTemplate) {
        wml010sendTemplate__ = wml010sendTemplate;
    }

    /**
     * <p>wml010sendSign を取得します。
     * @return wml010sendSign
     */
    public int getWml010sendSign() {
        return wml010sendSign__;
    }

    /**
     * <p>wml010sendSign をセットします。
     * @param wml010sendSign wml010sendSign
     */
    public void setWml010sendSign(int wml010sendSign) {
        wml010sendSign__ = wml010sendSign;
    }

    /**
     * <p>wml010sendSignOld を取得します。
     * @return wml010sendSignOld
     */
    public int getWml010sendSignOld() {
        return wml010sendSignOld__;
    }

    /**
     * <p>wml010sendSignOld をセットします。
     * @param wml010sendSignOld wml010sendSignOld
     */
    public void setWml010sendSignOld(int wml010sendSignOld) {
        wml010sendSignOld__ = wml010sendSignOld;
    }

    /**
     * <p>wml010sendAddressBcc を取得します。
     * @return wml010sendAddressBcc
     */
    public String getWml010sendAddressBcc() {
        return wml010sendAddressBcc__;
    }

    /**
     * <p>wml010sendAddressBcc をセットします。
     * @param wml010sendAddressBcc wml010sendAddressBcc
     */
    public void setWml010sendAddressBcc(String wml010sendAddressBcc) {
        wml010sendAddressBcc__ = wml010sendAddressBcc;
    }

    /**
     * <p>wml010sendAddressCc を取得します。
     * @return wml010sendAddressCc
     */
    public String getWml010sendAddressCc() {
        return wml010sendAddressCc__;
    }

    /**
     * <p>wml010sendAddressCc をセットします。
     * @param wml010sendAddressCc wml010sendAddressCc
     */
    public void setWml010sendAddressCc(String wml010sendAddressCc) {
        wml010sendAddressCc__ = wml010sendAddressCc;
    }

    /**
     * <p>wml010sendAddressTo を取得します。
     * @return wml010sendAddressTo
     */
    public String getWml010sendAddressTo() {
        return wml010sendAddressTo__;
    }

    /**
     * <p>wml010sendAddressTo をセットします。
     * @param wml010sendAddressTo wml010sendAddressTo
     */
    public void setWml010sendAddressTo(String wml010sendAddressTo) {
        wml010sendAddressTo__ = wml010sendAddressTo;
    }

    /**
     * <p>wml010sendContent を取得します。
     * @return wml010sendContent
     */
    public String getWml010sendContent() {
        return wml010sendContent__;
    }

    /**
     * <p>wml010sendContent をセットします。
     * @param wml010sendContent wml010sendContent
     */
    public void setWml010sendContent(String wml010sendContent) {
        wml010sendContent__ = wml010sendContent;
    }

    /**
     * <p>wml010sendContentCursor を取得します。
     * @return wml010sendContentCursor
     */
    public int getWml010sendContentCursor() {
        return wml010sendContentCursor__;
    }

    /**
     * <p>wml010sendContentCursor をセットします。
     * @param wml010sendContentCursor wml010sendContentCursor
     */
    public void setWml010sendContentCursor(int wml010sendContentCursor) {
        wml010sendContentCursor__ = wml010sendContentCursor;
    }

    /**
     * <p>wml010sendContentCursorType を取得します。
     * @return wml010sendContentCursorType
     */
    public int getWml010sendContentCursorType() {
        return wml010sendContentCursorType__;
    }

    /**
     * <p>wml010sendContentCursorType をセットします。
     * @param wml010sendContentCursorType wml010sendContentCursorType
     */
    public void setWml010sendContentCursorType(int wml010sendContentCursorType) {
        wml010sendContentCursorType__ = wml010sendContentCursorType;
    }

    /**
     * <p>wml010sendSubject を取得します。
     * @return wml010sendSubject
     */
    public String getWml010sendSubject() {
        return wml010sendSubject__;
    }

    /**
     * <p>wml010sendSubject をセットします。
     * @param wml010sendSubject wml010sendSubject
     */
    public void setWml010sendSubject(String wml010sendSubject) {
        wml010sendSubject__ = wml010sendSubject;
    }

    /**
     * <p>wml010sendMailType を取得します。
     * @return wml010sendMailType
     */
    public int getWml010sendMailType() {
        return wml010sendMailType__;
    }

    /**
     * <p>wml010sendMailType をセットします。
     * @param wml010sendMailType wml010sendMailType
     */
    public void setWml010sendMailType(int wml010sendMailType) {
        wml010sendMailType__ = wml010sendMailType;
    }

    /**
     * <p>wml010sendMailHtml を取得します。
     * @return wml010sendMailHtml
     */
    public int getWml010sendMailHtml() {
        return wml010sendMailHtml__;
    }

    /**
     * <p>wml010sendMailHtml をセットします。
     * @param wml010sendMailHtml wml010sendMailHtml
     */
    public void setWml010sendMailHtml(int wml010sendMailHtml) {
        wml010sendMailHtml__ = wml010sendMailHtml;
    }

    /**
     * <p>wml010svSendContent を取得します。
     * @return wml010svSendContent
     */
    public String getWml010svSendContent() {
        return wml010svSendContent__;
    }

    /**
     * <p>wml010svSendContent をセットします。
     * @param wml010svSendContent wml010svSendContent
     */
    public void setWml010svSendContent(String wml010svSendContent) {
        wml010svSendContent__ = wml010svSendContent;
    }

    /**
     * <p>wml010tempDirId を取得します。
     * @return wml010tempDirId
     */
    public String getWml010tempDirId() {
        return wml010tempDirId__;
    }

    /**
     * <p>wml010tempDirId をセットします。
     * @param wml010tempDirId wml010tempDirId
     */
    public void setWml010tempDirId(String wml010tempDirId) {
        wml010tempDirId__ = wml010tempDirId;
    }

    /**
     * <p>wml010sendMailFile を取得します。
     * @return wml010sendMailFile
     */
    public List<FormFile> getWml010sendMailFileForm() {
        return wml010sendMailFile__;
    }

    /**
     * <p>wml010sendMailFile をセットします。
     * @param wml010sendMailFile wml010sendMailFile
     */
    public void setWml010sendMailFileForm(List<FormFile> wml010sendMailFile) {
        wml010sendMailFile__ = wml010sendMailFile;
    }
    /**
     * <p>wml010sendMailFile を取得します。
     * @return wml010sendMailFile
     */
    public FormFile[] getWml010sendMailFile() {
        if (wml010sendMailFile__ == null) {
            return null;
        }
        return wml010sendMailFile__.toArray(new FormFile[wml010sendMailFile__.size()]);
    }

    /**
     * <p>wml010sendMailFile をセットします。
     * @param wml010sendMailFile wml010sendMailFile
     */
    public void setWml010sendMailFile(FormFile[] wml010sendMailFile) {
        if (wml010sendMailFile == null) {
            return;
        }
        for (int i = 0; i < wml010sendMailFile.length; i++) {
            setWml010sendMailFile(i, wml010sendMailFile[i]);
        }
    }

    /**
     * <p>wml010sendMailDownloadFile を取得します。
     * @return wml010sendMailDownloadFile
     */
    public String getWml010sendMailDownloadFile() {
        return wml010sendMailDownloadFile__;
    }

    /**
     * <p>wml010sendMailDownloadFile をセットします。
     * @param wml010sendMailDownloadFile wml010sendMailDownloadFile
     */
    public void setWml010sendMailDownloadFile(String wml010sendMailDownloadFile) {
        wml010sendMailDownloadFile__ = wml010sendMailDownloadFile;
    }

    /**
     * <p>shainGroupCombo を取得します。
     * @return shainGroupCombo
     */
    public List<LabelValueBean> getShainGroupCombo() {
        return shainGroupCombo__;
    }

    /**
     * <p>shainGroupCombo をセットします。
     * @param shainGroupCombo shainGroupCombo
     */
    public void setShainGroupCombo(List<LabelValueBean> shainGroupCombo) {
        shainGroupCombo__ = shainGroupCombo;
    }

    /**
     * <p>wml010shainGroup を取得します。
     * @return wml010shainGroup
     */
    public int getWml010shainGroup() {
        return wml010shainGroup__;
    }

    /**
     * <p>wml010shainGroup をセットします。
     * @param wml010shainGroup wml010shainGroup
     */
    public void setWml010shainGroup(int wml010shainGroup) {
        wml010shainGroup__ = wml010shainGroup;
    }
    /**
     * <p>wml010addressType を取得します。
     * @return wml010addressType
     */
    public int getWml010addressType() {
        return wml010addressType__;
    }

    /**
     * <p>wml010addressType をセットします。
     * @param wml010addressType wml010addressType
     */
    public void setWml010addressType(int wml010addressType) {
        wml010addressType__ = wml010addressType;
    }

    /**
     * <p>wml010searchReadKbn を取得します。
     * @return wml010searchReadKbn
     */
    public int getWml010searchReadKbn() {
        return wml010searchReadKbn__;
    }

    /**
     * <p>wml010searchReadKbn をセットします。
     * @param wml010searchReadKbn wml010searchReadKbn
     */
    public void setWml010searchReadKbn(int wml010searchReadKbn) {
        wml010searchReadKbn__ = wml010searchReadKbn;
    }

    /**
     * <p>wml010svSearchReadKbn を取得します。
     * @return wml010svSearchReadKbn
     */
    public int getWml010svSearchReadKbn() {
        return wml010svSearchReadKbn__;
    }

    /**
     * <p>wml010svSearchReadKbn をセットします。
     * @param wml010svSearchReadKbn wml010svSearchReadKbn
     */
    public void setWml010svSearchReadKbn(int wml010svSearchReadKbn) {
        wml010svSearchReadKbn__ = wml010svSearchReadKbn;
    }

    /**
     * <p>wml010maxBodySize を取得します。
     * @return wml010maxBodySize
     */
    public int getWml010maxBodySize() {
        return wml010maxBodySize__;
    }

    /**
     * <p>wml010maxBodySize をセットします。
     * @param wml010maxBodySize wml010maxBodySize
     */
    public void setWml010maxBodySize(int wml010maxBodySize) {
        wml010maxBodySize__ = wml010maxBodySize;
    }

    /**
     * <p>wml010pluginAddressUse を取得します。
     * @return wml010pluginAddressUse
     */
    public int getWml010pluginAddressUse() {
        return wml010pluginAddressUse__;
    }

    /**
     * <p>wml010pluginAddressUse をセットします。
     * @param wml010pluginAddressUse wml010pluginAddressUse
     */
    public void setWml010pluginAddressUse(int wml010pluginAddressUse) {
        wml010pluginAddressUse__ = wml010pluginAddressUse;
    }

    /**
     * <p>wml010pluginCircularUse を取得します。
     * @return wml010pluginCircularUse
     */
    public int getWml010pluginCircularUse() {
        return wml010pluginCircularUse__;
    }

    /**
     * <p>wml010pluginCircularUse をセットします。
     * @param wml010pluginCircularUse wml010pluginCircularUse
     */
    public void setWml010pluginCircularUse(int wml010pluginCircularUse) {
        wml010pluginCircularUse__ = wml010pluginCircularUse;
    }

    /**
     * <p>wml010pluginSmailUse を取得します。
     * @return wml010pluginSmailUse
     */
    public int getWml010pluginSmailUse() {
        return wml010pluginSmailUse__;
    }

    /**
     * <p>wml010pluginSmailUse をセットします。
     * @param wml010pluginSmailUse wml010pluginSmailUse
     */
    public void setWml010pluginSmailUse(int wml010pluginSmailUse) {
        wml010pluginSmailUse__ = wml010pluginSmailUse;
    }

    /**
     * <p>wml010pluginFilekanriUse を取得します。
     * @return wml010pluginFilekanriUse
     */
    public int getWml010pluginFilekanriUse() {
        return wml010pluginFilekanriUse__;
    }

    /**
     * <p>wml010pluginFilekanriUse をセットします。
     * @param wml010pluginFilekanriUse wml010pluginFilekanriUse
     */
    public void setWml010pluginFilekanriUse(int wml010pluginFilekanriUse) {
        wml010pluginFilekanriUse__ = wml010pluginFilekanriUse;
    }

    /**
     * <p>wml010pluginUserUse を取得します。
     * @return wml010pluginUserUse
     */
    public int getWml010pluginUserUse() {
        return wml010pluginUserUse__;
    }

    /**
     * <p>wml010pluginUserUse をセットします。
     * @param wml010pluginUserUse wml010pluginUserUse
     */
    public void setWml010pluginUserUse(int wml010pluginUserUse) {
        wml010pluginUserUse__ = wml010pluginUserUse;
    }

    /**
     * <p>folderCombo を取得します。
     * @return folderCombo
     */
    public List<LabelValueBean> getFolderCombo() {
        return folderCombo__;
    }

    /**
     * <p>folderCombo をセットします。
     * @param folderCombo folderCombo
     */
    public void setFolderCombo(List<LabelValueBean> folderCombo) {
        folderCombo__ = folderCombo;
    }

    /**
     * <p>wml010moveFolder を取得します。
     * @return wml010moveFolder
     */
    public long getWml010moveFolder() {
        return wml010moveFolder__;
    }

    /**
     * <p>wml010moveFolder をセットします。
     * @param wml010moveFolder wml010moveFolder
     */
    public void setWml010moveFolder(long wml010moveFolder) {
        wml010moveFolder__ = wml010moveFolder;
    }

    /**
     * <p>wml010destlistId を取得します。
     * @return wml010destlistId
     */
    public int getWml010destlistId() {
        return wml010destlistId__;
    }

    /**
     * <p>wml010destlistId をセットします。
     * @param wml010destlistId wml010destlistId
     */
    public void setWml010destlistId(int wml010destlistId) {
        wml010destlistId__ = wml010destlistId;
    }

    /**
     * <p>destlistCombo を取得します。
     * @return destlistCombo
     */
    public List<LabelValueBean> getDestlistCombo() {
        return destlistCombo__;
    }

    /**
     * <p>destlistCombo をセットします。
     * @param destlistCombo destlistCombo
     */
    public void setDestlistCombo(List<LabelValueBean> destlistCombo) {
        destlistCombo__ = destlistCombo;
    }

    /**
     * <p>wml011mailNum を取得します。
     * @return wml011mailNum
     */
    public int getWml011mailNum() {
        return wml011mailNum__;
    }

    /**
     * <p>wml011mailNum をセットします。
     * @param wml011mailNum wml011mailNum
     */
    public void setWml011mailNum(int wml011mailNum) {
        wml011mailNum__ = wml011mailNum;
    }

    /**
     * <p>wml010theme を取得します。
     * @return wml010theme
     */
    public String getWml010theme() {
        return wml010theme__;
    }

    /**
     * <p>wml010theme をセットします。
     * @param wml010theme wml010theme
     */
    public void setWml010theme(String wml010theme) {
        wml010theme__ = wml010theme;
    }

    /**
     * <p>wml010checkAddress を取得します。
     * @return wml010checkAddress
     */
    public int getWml010checkAddress() {
        return wml010checkAddress__;
    }

    /**
     * <p>wml010checkAddress をセットします。
     * @param wml010checkAddress wml010checkAddress
     */
    public void setWml010checkAddress(int wml010checkAddress) {
        wml010checkAddress__ = wml010checkAddress;
    }

    /**
     * <p>wml010checkFile を取得します。
     * @return wml010checkFile
     */
    public int getWml010checkFile() {
        return wml010checkFile__;
    }

    /**
     * <p>wml010checkFile をセットします。
     * @param wml010checkFile wml010checkFile
     */
    public void setWml010checkFile(int wml010checkFile) {
        wml010checkFile__ = wml010checkFile;
    }

    /**
     * <p>wml010sendMailPlanDateYear を取得します。
     * @return wml010sendMailPlanDateYear
     */
    public String getWml010sendMailPlanDateYear() {
        return wml010sendMailPlanDateYear__;
    }

    /**
     * <p>sendMailPlanType を取得します。
     * @return sendMailPlanType
     */
    public int getSendMailPlanType() {
        return sendMailPlanType__;
    }

    /**
     * <p>sendMailPlanType をセットします。
     * @param sendMailPlanType sendMailPlanType
     */
    public void setSendMailPlanType(int sendMailPlanType) {
        sendMailPlanType__ = sendMailPlanType;
    }

    /**
     * <p>wml010sendMailPlanDateYear をセットします。
     * @param wml010sendMailPlanDateYear wml010sendMailPlanDateYear
     */
    public void setWml010sendMailPlanDateYear(String wml010sendMailPlanDateYear) {
        wml010sendMailPlanDateYear__ = wml010sendMailPlanDateYear;
    }

    //---------------- 添付ファイル -------------------------------------

    /**
     * <p>wml010sendMailPlanDateMonth を取得します。
     * @return wml010sendMailPlanDateMonth
     */
    public String getWml010sendMailPlanDateMonth() {
        return wml010sendMailPlanDateMonth__;
    }

    /**
     * <p>wml010sendMailPlanDateMonth をセットします。
     * @param wml010sendMailPlanDateMonth wml010sendMailPlanDateMonth
     */
    public void setWml010sendMailPlanDateMonth(String wml010sendMailPlanDateMonth) {
        wml010sendMailPlanDateMonth__ = wml010sendMailPlanDateMonth;
    }

    /**
     * <p>wml010sendMailPlanDateDay を取得します。
     * @return wml010sendMailPlanDateDay
     */
    public String getWml010sendMailPlanDateDay() {
        return wml010sendMailPlanDateDay__;
    }

    /**
     * <p>wml010sendMailPlanDateDay をセットします。
     * @param wml010sendMailPlanDateDay wml010sendMailPlanDateDay
     */
    public void setWml010sendMailPlanDateDay(String wml010sendMailPlanDateDay) {
        wml010sendMailPlanDateDay__ = wml010sendMailPlanDateDay;
    }

    /**
     * <p>wml010sendMailPlanDateHour を取得します。
     * @return wml010sendMailPlanDateHour
     */
    public String getWml010sendMailPlanDateHour() {
        return wml010sendMailPlanDateHour__;
    }

    /**
     * <p>wml010sendMailPlanDateHour をセットします。
     * @param wml010sendMailPlanDateHour wml010sendMailPlanDateHour
     */
    public void setWml010sendMailPlanDateHour(String wml010sendMailPlanDateHour) {
        wml010sendMailPlanDateHour__ = wml010sendMailPlanDateHour;
    }

    /**
     * <p>wml010sendMailPlanDateMinute を取得します。
     * @return wml010sendMailPlanDateMinute
     */
    public String getWml010sendMailPlanDateMinute() {
        return wml010sendMailPlanDateMinute__;
    }

    /**
     * <p>wml010sendMailPlanDateMinute をセットします。
     * @param wml010sendMailPlanDateMinute wml010sendMailPlanDateMinute
     */
    public void setWml010sendMailPlanDateMinute(String wml010sendMailPlanDateMinute) {
        wml010sendMailPlanDateMinute__ = wml010sendMailPlanDateMinute;
    }

    /**
     * <p>wml010sendMailPlanImm を取得します。
     * @return wml010sendMailPlanImm
     */
    public int getWml010sendMailPlanImm() {
        return wml010sendMailPlanImm__;
    }

    /**
     * <p>wml010sendMailPlanImm をセットします。
     * @param wml010sendMailPlanImm wml010sendMailPlanImm
     */
    public void setWml010sendMailPlanImm(int wml010sendMailPlanImm) {
        wml010sendMailPlanImm__ = wml010sendMailPlanImm;
    }

    /**
     * <p>wml010sendTempfileCompress を取得します。
     * @return wml010sendTempfileCompress
     */
    public int getWml010sendTempfileCompress() {
        return wml010sendTempfileCompress__;
    }

    /**
     * <p>wml010sendTempfileCompress をセットします。
     * @param wml010sendTempfileCompress wml010sendTempfileCompress
     */
    public void setWml010sendTempfileCompress(int wml010sendTempfileCompress) {
        wml010sendTempfileCompress__ = wml010sendTempfileCompress;
    }

    /**
     * 添付ファイルを取得します。
     * @param index 要素番号
     * @return FormFile
     */
    public FormFile getWml010sendMailFile(int index) {
        while (wml010sendMailFile__.size() <= index) {
            return null;
        }

        return (FormFile) wml010sendMailFile__.get(index);
    }

    /**
     * 添付ファイルを追加します。
     * @param index 要素番号
     * @param wml010sendMailFile FormFile
     */
    public void setWml010sendMailFile(int index, FormFile wml010sendMailFile) {
        while (wml010sendMailFile__.size() <= index) {
            wml010sendMailFile__.add(null);
        }
        wml010sendMailFile__.remove(index);
        wml010sendMailFile__.add(index, wml010sendMailFile);
    }

    /**
     * <p>wml010smlShareFlg を取得します。
     * @return wml010smlShareFlg
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareFlg__
     */
    public int getWml010smlShareFlg() {
        return wml010smlShareFlg__;
    }

    /**
     * <p>wml010smlShareFlg をセットします。
     * @param wml010smlShareFlg wml010smlShareFlg
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareFlg__
     */
    public void setWml010smlShareFlg(int wml010smlShareFlg) {
        wml010smlShareFlg__ = wml010smlShareFlg;
    }

    /**
     * <p>wml010smlShareTitle を取得します。
     * @return wml010smlShareTitle
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareTitle__
     */
    public String getWml010smlShareTitle() {
        return wml010smlShareTitle__;
    }

    /**
     * <p>wml010smlShareTitle をセットします。
     * @param wml010smlShareTitle wml010smlShareTitle
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareTitle__
     */
    public void setWml010smlShareTitle(String wml010smlShareTitle) {
        wml010smlShareTitle__ = wml010smlShareTitle;
    }

    /**
     * <p>wml010smlShareBody を取得します。
     * @return wml010smlShareBody
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareBody__
     */
    public String getWml010smlShareBody() {
        return wml010smlShareBody__;
    }

    /**
     * <p>wml010smlShareBody をセットします。
     * @param wml010smlShareBody wml010smlShareBody
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareBody__
     */
    public void setWml010smlShareBody(String wml010smlShareBody) {
        wml010smlShareBody__ = wml010smlShareBody;
    }

    /**
     * <p>wml010smlShareTemp を取得します。
     * @return wml010smlShareTemp
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareTemp__
     */
    public String getWml010smlShareTemp() {
        return wml010smlShareTemp__;
    }

    /**
     * <p>wml010smlShareTemp をセットします。
     * @param wml010smlShareTemp wml010smlShareTemp
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareTemp__
     */
    public void setWml010smlShareTemp(String wml010smlShareTemp) {
        wml010smlShareTemp__ = wml010smlShareTemp;
    }

    /**
     * <p>wml010smlShareCloseFlg を取得します。
     * @return wml010smlShareCloseFlg
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareCloseFlg__
     */
    public int getWml010smlShareCloseFlg() {
        return wml010smlShareCloseFlg__;
    }

    /**
     * <p>wml010smlShareCloseFlg をセットします。
     * @param wml010smlShareCloseFlg wml010smlShareCloseFlg
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareCloseFlg__
     */
    public void setWml010smlShareCloseFlg(int wml010smlShareCloseFlg) {
        wml010smlShareCloseFlg__ = wml010smlShareCloseFlg;
    }

    /**
     * <p>wml010smlShareHtml を取得します。
     * @return wml010smlShareHtml
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareHtml__
     */
    public int getWml010smlShareHtml() {
        return wml010smlShareHtml__;
    }

    /**
     * <p>wml010smlShareHtml をセットします。
     * @param wml010smlShareHtml wml010smlShareHtml
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010smlShareHtml__
     */
    public void setWml010smlShareHtml(int wml010smlShareHtml) {
        wml010smlShareHtml__ = wml010smlShareHtml;
    }

    /**
     * <p>wml010searchDateFr を取得します。
     * @return wml010searchDateFr
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010searchDateFr__
     */
    public String getWml010searchDateFr() {
        return wml010searchDateFr__;
    }

    /**
     * <p>wml010searchDateFr をセットします。
     * @param wml010searchDateFr wml010searchDateFr
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010searchDateFr__
     */
    public void setWml010searchDateFr(String wml010searchDateFr) {
        wml010searchDateFr__ = wml010searchDateFr;
    }

    /**
     * <p>wml010searchDateTo を取得します。
     * @return wml010searchDateTo
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010searchDateTo__
     */
    public String getWml010searchDateTo() {
        return wml010searchDateTo__;
    }

    /**
     * <p>wml010searchDateTo をセットします。
     * @param wml010searchDateTo wml010searchDateTo
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010searchDateTo__
     */
    public void setWml010searchDateTo(String wml010searchDateTo) {
        wml010searchDateTo__ = wml010searchDateTo;
    }

    /**
     * <p>wml010sendMailPlanDate を取得します。
     * @return wml010sendMailPlanDate
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010sendMailPlanDate__
     */
    public String getWml010sendMailPlanDate() {
        return wml010sendMailPlanDate__;
    }

    /**
     * <p>wml010sendMailPlanDate をセットします。
     * @param wml010sendMailPlanDate wml010sendMailPlanDate
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010sendMailPlanDate__
     */
    public void setWml010sendMailPlanDate(String wml010sendMailPlanDate) {
        wml010sendMailPlanDate__ = wml010sendMailPlanDate;
    }

    /**
     * <p>wml010sendMailPlanTime を取得します。
     * @return wml010sendMailPlanTime
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010sendMailPlanTime__
     */
    public String getWml010sendMailPlanTime() {
        return wml010sendMailPlanTime__;
    }

    /**
     * <p>wml010sendMailPlanTime をセットします。
     * @param wml010sendMailPlanTime wml010sendMailPlanTime
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010sendMailPlanTime__
     */
    public void setWml010sendMailPlanTime(String wml010sendMailPlanTime) {
        wml010sendMailPlanTime__ = wml010sendMailPlanTime;
    }

    /**
     * <p>wml010TempSaveId を取得します。
     * @return wml010TempSaveId
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010TempSaveId__
     */
    public String getWml010TempSaveId() {
        return wml010TempSaveId__;
    }

    /**
     * <p>wml010TempSaveId をセットします。
     * @param wml010TempSaveId wml010TempSaveId
     * @see jp.groupsession.v2.wml.wml010.Wml010Form#wml010TempSaveId__
     */
    public void setWml010TempSaveId(String wml010TempSaveId) {
        wml010TempSaveId__ = wml010TempSaveId;
    }
}
