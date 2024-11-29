package jp.groupsession.v2.wml.wml320;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import jp.groupsession.v2.wml.wml090.Wml090ParamModel;

/**
 * <br>[機  能]個人設定 メール情報一括削除画面のパラメータモデルクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml320ParamModel extends Wml090ParamModel {

    /** 検索条件 アカウント */
    private int wml320account__ = 0;

    /** 検索条件 ディレクトリ */
    private int[] wml320directory__ = null;
    /** 検索条件 キーワード */
    private String wml320keyword__ = null;
    /** 検索条件 From */
    private String wml320from__ = null;
    /** 検索条件 未読/既読 */
    private int wml320readed__ = 0;
    /** 検索条件 送信先 */
    private String wml320dest__ = null;
    /** 検索条件 送信先 対象 To*/
    private int wml320destTypeTo__ = 0;
    /** 検索条件 送信先 対象 Bcc*/
    private int wml320destTypeBcc__ = 0;
    /** 検索条件 送信先 対象 Cc*/
    private int wml320destTypeCc__ = 0;
    /** 検索条件 添付ファイル */
    private int wml320attach__ = 0;
    /** 検索条件 日付選択 */
    private int wml320dateType__ = 0;
    /** 検索条件 日付 From 年 */
    private int wml320dateYearFr__ = 0;
    /** 検索条件 日付 From 月 */
    private int wml320dateMonthFr__ = 0;
    /** 検索条件 日付 From 日 */
    private int wml320dateDayFr__ = 0;
    /** 検索条件 日付 To 年 */
    private int wml320dateYearTo__ = 0;
    /** 検索条件 日付 To 月 */
    private int wml320dateMonthTo__ = 0;
    /** 検索条件 日付 To 日 */
    private int wml320dateDayTo__ = 0;
    /** 検索条件 ラベル */
    private int wml320label__ = 0;
    /** 検索条件 サイズ */
    private String wml320size__ = null;
    /** 検索条件 ソートキー */
    private int wml320sortKey__ = 0;
    /** 検索条件 並び順 */
    private int wml320order__ = 0;

    /** 検索条件 アカウント */
    private int wml320svAccount__ = 0;

    /** 検索条件 ディレクトリ */
    private int[] wml320svDirectory__ = null;
    /** 検索条件 キーワード */
    private String wml320svKeyword__ = null;
    /** 検索条件 From */
    private String wml320svFrom__ = null;
    /** 検索条件 未読/既読 */
    private int wml320svReaded__ = 0;
    /** 検索条件 送信先 */
    private String wml320svDest__ = null;
    /** 検索条件 送信先 対象 To*/
    private int wml320svDestTypeTo__ = 0;
    /** 検索条件 送信先 対象 Bcc*/
    private int wml320svDestTypeBcc__ = 0;
    /** 検索条件 送信先 対象 Cc*/
    private int wml320svDestTypeCc__ = 0;
    /** 検索条件 添付ファイル */
    private int wml320svAttach__ = 0;
    /** 検索条件 日付選択 */
    private int wml320svDateType__ = 0;
    /** 検索条件 日付 From 年 */
    private int wml320svDateYearFr__ = 0;
    /** 検索条件 日付 From 月 */
    private int wml320svDateMonthFr__ = 0;
    /** 検索条件 日付 From 日 */
    private int wml320svDateDayFr__ = 0;
    /** 検索条件 日付 To 年 */
    private int wml320svDateYearTo__ = 0;
    /** 検索条件 日付 To 月 */
    private int wml320svDateMonthTo__ = 0;
    /** 検索条件 日付 To 日 */
    private int wml320svDateDayTo__ = 0;
    /** 検索条件 日付 From 年月日 */
    private String wml320DateFr__ = null;
    /** 検索条件 日付 To 年月日 */
    private String wml320DateTo__ = null;
    /** 検索条件 ラベル */
    private int wml320svLabel__ = 0;
    /** 検索条件 サイズ */
    private String wml320svSize__ = null;
    /** 検索条件 ソートキー */
    private int wml320svSortKey__ = 0;
    /** 検索条件 並び順 */
    private int wml320svOrder__ = 0;

    /** メール一覧 ページ(上段) */
    private int wml320NowPage__ = 1;
    /** 表示ページ（上） */
    private int wml320pageTop__ = 0;
    /** メール一覧 ページ(下段) */
    private int wml320pageBottom__ = 0;

    /** 検索フラグ */
    private boolean wml320searchFlg__ = false;
    /** 初期表示フラグ */
    private int wml320initFlg__ = GSConstWebmail.INIT_FLG;
    /** 削除メール数上限 */
    private int wml320delLimit__ = GSConstWebmail.DELETE_MAIL_LIMIT_DEFAULT;
    /** 選択中アカウントの名称 */
    private String accountName__ = null;
    /** 選択中アカウントの削除対象メール件数 */
    private int deleteMailCount__ = 0;

    /** ページラベル */
    private List<LabelValueBean> wml320pageLabel__;
    /** 検索結果一覧 */
    private List<Wml320DspModel> wml320mailList__;
    /** ページコンボ */
    private List<LabelValueBean> wml320pageCombo__;
    /** アカウントコンボ */
    private List<UsrLabelValueBean> wml320accountCombo__;
    /** ラベルコンボ */
    private List<LabelValueBean> wml320labelCombo__;
    /** ソート順コンボ */
    private List<LabelValueBean> wml320sortKeyCombo__;


    /**
     * <p>wml320account を取得します。
     * @return wml320account
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320account__
     */
    public int getWml320account() {
        return wml320account__;
    }
    /**
     * <p>wml320account をセットします。
     * @param wml320account wml320account
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320account__
     */
    public void setWml320account(int wml320account) {
        wml320account__ = wml320account;
    }

    /**
     * <p>wml320directory を取得します。
     * @return wml320directory
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320directory__
     */
    public int[] getWml320directory() {
        return wml320directory__;
    }
    /**
     * <p>wml320directory をセットします。
     * @param wml320directory wml320directory
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320directory__
     */
    public void setWml320directory(int[] wml320directory) {
        wml320directory__ = wml320directory;
    }
    /**
     * <p>wml320keyword を取得します。
     * @return wml320keyword
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320keyword__
     */
    public String getWml320keyword() {
        return wml320keyword__;
    }
    /**
     * <p>wml320keyword をセットします。
     * @param wml320keyword wml320keyword
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320keyword__
     */
    public void setWml320keyword(String wml320keyword) {
        wml320keyword__ = wml320keyword;
    }
    /**
     * <p>wml320from を取得します。
     * @return wml320from
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320from__
     */
    public String getWml320from() {
        return wml320from__;
    }
    /**
     * <p>wml320from をセットします。
     * @param wml320from wml320from
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320from__
     */
    public void setWml320from(String wml320from) {
        wml320from__ = wml320from;
    }
    /**
     * <p>wml320readed を取得します。
     * @return wml320readed
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320readed__
     */
    public int getWml320readed() {
        return wml320readed__;
    }
    /**
     * <p>wml320readed をセットします。
     * @param wml320readed wml320readed
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320readed__
     */
    public void setWml320readed(int wml320readed) {
        wml320readed__ = wml320readed;
    }
    /**
     * <p>wml320dest を取得します。
     * @return wml320dest
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dest__
     */
    public String getWml320dest() {
        return wml320dest__;
    }
    /**
     * <p>wml320dest をセットします。
     * @param wml320dest wml320dest
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dest__
     */
    public void setWml320dest(String wml320dest) {
        wml320dest__ = wml320dest;
    }

    /**
     * <p>wml320destTypeTo を取得します。
     * @return wml320destTypeTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320destTypeTo__
     */
    public int getWml320destTypeTo() {
        return wml320destTypeTo__;
    }
    /**
     * <p>wml320destTypeTo をセットします。
     * @param wml320destTypeTo wml320destTypeTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320destTypeTo__
     */
    public void setWml320destTypeTo(int wml320destTypeTo) {
        wml320destTypeTo__ = wml320destTypeTo;
    }
    /**
     * <p>wml320destTypeBcc を取得します。
     * @return wml320destTypeBcc
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320destTypeBcc__
     */
    public int getWml320destTypeBcc() {
        return wml320destTypeBcc__;
    }
    /**
     * <p>wml320destTypeBcc をセットします。
     * @param wml320destTypeBcc wml320destTypeBcc
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320destTypeBcc__
     */
    public void setWml320destTypeBcc(int wml320destTypeBcc) {
        wml320destTypeBcc__ = wml320destTypeBcc;
    }
    /**
     * <p>wml320destTypeCc を取得します。
     * @return wml320destTypeCc
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320destTypeCc__
     */
    public int getWml320destTypeCc() {
        return wml320destTypeCc__;
    }
    /**
     * <p>wml320destTypeCc をセットします。
     * @param wml320destTypeCc wml320destTypeCc
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320destTypeCc__
     */
    public void setWml320destTypeCc(int wml320destTypeCc) {
        wml320destTypeCc__ = wml320destTypeCc;
    }
    /**
     * <p>wml320attach を取得します。
     * @return wml320attach
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320attach__
     */
    public int getWml320attach() {
        return wml320attach__;
    }
    /**
     * <p>wml320attach をセットします。
     * @param wml320attach wml320attach
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320attach__
     */
    public void setWml320attach(int wml320attach) {
        wml320attach__ = wml320attach;
    }
    /**
     * <p>wml320dateType を取得します。
     * @return wml320dateType
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateType__
     */
    public int getWml320dateType() {
        return wml320dateType__;
    }
    /**
     * <p>wml320dateType をセットします。
     * @param wml320dateType wml320dateType
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateType__
     */
    public void setWml320dateType(int wml320dateType) {
        wml320dateType__ = wml320dateType;
    }

    /**
     * <p>wml320dateYearFr を取得します。
     * @return wml320dateYearFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateYearFr__
     */
    public int getWml320dateYearFr() {
        return wml320dateYearFr__;
    }
    /**
     * <p>wml320dateYearFr をセットします。
     * @param wml320dateYearFr wml320dateYearFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateYearFr__
     */
    public void setWml320dateYearFr(int wml320dateYearFr) {
        wml320dateYearFr__ = wml320dateYearFr;
    }
    /**
     * <p>wml320dateMonthFr を取得します。
     * @return wml320dateMonthFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateMonthFr__
     */
    public int getWml320dateMonthFr() {
        return wml320dateMonthFr__;
    }
    /**
     * <p>wml320dateMonthFr をセットします。
     * @param wml320dateMonthFr wml320dateMonthFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateMonthFr__
     */
    public void setWml320dateMonthFr(int wml320dateMonthFr) {
        wml320dateMonthFr__ = wml320dateMonthFr;
    }
    /**
     * <p>wml320dateDayFr を取得します。
     * @return wml320dateDayFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateDayFr__
     */
    public int getWml320dateDayFr() {
        return wml320dateDayFr__;
    }
    /**
     * <p>wml320dateDayFr をセットします。
     * @param wml320dateDayFr wml320dateDayFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateDayFr__
     */
    public void setWml320dateDayFr(int wml320dateDayFr) {
        wml320dateDayFr__ = wml320dateDayFr;
    }
    /**
     * <p>wml320dateYearTo を取得します。
     * @return wml320dateYearTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateYearTo__
     */
    public int getWml320dateYearTo() {
        return wml320dateYearTo__;
    }
    /**
     * <p>wml320dateYearTo をセットします。
     * @param wml320dateYearTo wml320dateYearTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateYearTo__
     */
    public void setWml320dateYearTo(int wml320dateYearTo) {
        wml320dateYearTo__ = wml320dateYearTo;
    }
    /**
     * <p>wml320dateMonthTo を取得します。
     * @return wml320dateMonthTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateMonthTo__
     */
    public int getWml320dateMonthTo() {
        return wml320dateMonthTo__;
    }
    /**
     * <p>wml320dateMonthTo をセットします。
     * @param wml320dateMonthTo wml320dateMonthTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateMonthTo__
     */
    public void setWml320dateMonthTo(int wml320dateMonthTo) {
        wml320dateMonthTo__ = wml320dateMonthTo;
    }
    /**
     * <p>wml320dateDayTo を取得します。
     * @return wml320dateDayTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateDayTo__
     */
    public int getWml320dateDayTo() {
        return wml320dateDayTo__;
    }
    /**
     * <p>wml320dateDayTo をセットします。
     * @param wml320dateDayTo wml320dateDayTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320dateDayTo__
     */
    public void setWml320dateDayTo(int wml320dateDayTo) {
        wml320dateDayTo__ = wml320dateDayTo;
    }
    /**
     * <p>wml320label を取得します。
     * @return wml320label
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320label__
     */
    public int getWml320label() {
        return wml320label__;
    }
    /**
     * <p>wml320label をセットします。
     * @param wml320label wml320label
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320label__
     */
    public void setWml320label(int wml320label) {
        wml320label__ = wml320label;
    }
    /**
     * <p>wml320size を取得します。
     * @return wml320size
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320size__
     */
    public String getWml320size() {
        return wml320size__;
    }
    /**
     * <p>wml320size をセットします。
     * @param wml320size wml320size
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320size__
     */
    public void setWml320size(String wml320size) {
        wml320size__ = wml320size;
    }
    /**
     * <p>wml320svAccount を取得します。
     * @return wml320svAccount
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svAccount__
     */
    public int getWml320svAccount() {
        return wml320svAccount__;
    }
    /**
     * <p>wml320svAccount をセットします。
     * @param wml320svAccount wml320svAccount
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svAccount__
     */
    public void setWml320svAccount(int wml320svAccount) {
        wml320svAccount__ = wml320svAccount;
    }

    /**
     * <p>wml320svDirectory を取得します。
     * @return wml320svDirectory
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDirectory__
     */
    public int[] getWml320svDirectory() {
        return wml320svDirectory__;
    }
    /**
     * <p>wml320svDirectory をセットします。
     * @param wml320svDirectory wml320svDirectory
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDirectory__
     */
    public void setWml320svDirectory(int[] wml320svDirectory) {
        wml320svDirectory__ = wml320svDirectory;
    }
    /**
     * <p>wml320svKeyword を取得します。
     * @return wml320svKeyword
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svKeyword__
     */
    public String getWml320svKeyword() {
        return wml320svKeyword__;
    }
    /**
     * <p>wml320svKeyword をセットします。
     * @param wml320svKeyword wml320svKeyword
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svKeyword__
     */
    public void setWml320svKeyword(String wml320svKeyword) {
        wml320svKeyword__ = wml320svKeyword;
    }
    /**
     * <p>wml320svFrom を取得します。
     * @return wml320svFrom
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svFrom__
     */
    public String getWml320svFrom() {
        return wml320svFrom__;
    }
    /**
     * <p>wml320svFrom をセットします。
     * @param wml320svFrom wml320svFrom
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svFrom__
     */
    public void setWml320svFrom(String wml320svFrom) {
        wml320svFrom__ = wml320svFrom;
    }
    /**
     * <p>wml320svReaded を取得します。
     * @return wml320svReaded
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svReaded__
     */
    public int getWml320svReaded() {
        return wml320svReaded__;
    }
    /**
     * <p>wml320svReaded をセットします。
     * @param wml320svReaded wml320svReaded
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svReaded__
     */
    public void setWml320svReaded(int wml320svReaded) {
        wml320svReaded__ = wml320svReaded;
    }
    /**
     * <p>wml320svDest を取得します。
     * @return wml320svDest
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDest__
     */
    public String getWml320svDest() {
        return wml320svDest__;
    }
    /**
     * <p>wml320svDest をセットします。
     * @param wml320svDest wml320svDest
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDest__
     */
    public void setWml320svDest(String wml320svDest) {
        wml320svDest__ = wml320svDest;
    }

    /**
     * <p>wml320svDestTypeTo を取得します。
     * @return wml320svDestTypeTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDestTypeTo__
     */
    public int getWml320svDestTypeTo() {
        return wml320svDestTypeTo__;
    }
    /**
     * <p>wml320svDestTypeTo をセットします。
     * @param wml320svDestTypeTo wml320svDestTypeTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDestTypeTo__
     */
    public void setWml320svDestTypeTo(int wml320svDestTypeTo) {
        wml320svDestTypeTo__ = wml320svDestTypeTo;
    }
    /**
     * <p>wml320svDestTypeBcc を取得します。
     * @return wml320svDestTypeBcc
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDestTypeBcc__
     */
    public int getWml320svDestTypeBcc() {
        return wml320svDestTypeBcc__;
    }
    /**
     * <p>wml320svDestTypeBcc をセットします。
     * @param wml320svDestTypeBcc wml320svDestTypeBcc
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDestTypeBcc__
     */
    public void setWml320svDestTypeBcc(int wml320svDestTypeBcc) {
        wml320svDestTypeBcc__ = wml320svDestTypeBcc;
    }
    /**
     * <p>wml320svDestTypeCc を取得します。
     * @return wml320svDestTypeCc
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDestTypeCc__
     */
    public int getWml320svDestTypeCc() {
        return wml320svDestTypeCc__;
    }
    /**
     * <p>wml320svDestTypeCc をセットします。
     * @param wml320svDestTypeCc wml320svDestTypeCc
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDestTypeCc__
     */
    public void setWml320svDestTypeCc(int wml320svDestTypeCc) {
        wml320svDestTypeCc__ = wml320svDestTypeCc;
    }
    /**
     * <p>wml320svAttach を取得します。
     * @return wml320svAttach
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svAttach__
     */
    public int getWml320svAttach() {
        return wml320svAttach__;
    }
    /**
     * <p>wml320svAttach をセットします。
     * @param wml320svAttach wml320svAttach
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svAttach__
     */
    public void setWml320svAttach(int wml320svAttach) {
        wml320svAttach__ = wml320svAttach;
    }
    /**
     * <p>wml320svDateType を取得します。
     * @return wml320svDateType
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateType__
     */
    public int getWml320svDateType() {
        return wml320svDateType__;
    }
    /**
     * <p>wml320svDateType をセットします。
     * @param wml320svDateType wml320svDateType
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateType__
     */
    public void setWml320svDateType(int wml320svDateType) {
        wml320svDateType__ = wml320svDateType;
    }

    /**
     * <p>wml320svDateYearFr を取得します。
     * @return wml320svDateYearFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateYearFr__
     */
    public int getWml320svDateYearFr() {
        return wml320svDateYearFr__;
    }
    /**
     * <p>wml320svDateYearFr をセットします。
     * @param wml320svDateYearFr wml320svDateYearFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateYearFr__
     */
    public void setWml320svDateYearFr(int wml320svDateYearFr) {
        wml320svDateYearFr__ = wml320svDateYearFr;
    }
    /**
     * <p>wml320svDateMonthFr を取得します。
     * @return wml320svDateMonthFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateMonthFr__
     */
    public int getWml320svDateMonthFr() {
        return wml320svDateMonthFr__;
    }
    /**
     * <p>wml320svDateMonthFr をセットします。
     * @param wml320svDateMonthFr wml320svDateMonthFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateMonthFr__
     */
    public void setWml320svDateMonthFr(int wml320svDateMonthFr) {
        wml320svDateMonthFr__ = wml320svDateMonthFr;
    }
    /**
     * <p>wml320svDateDayFr を取得します。
     * @return wml320svDateDayFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateDayFr__
     */
    public int getWml320svDateDayFr() {
        return wml320svDateDayFr__;
    }
    /**
     * <p>wml320svDateDayFr をセットします。
     * @param wml320svDateDayFr wml320svDateDayFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateDayFr__
     */
    public void setWml320svDateDayFr(int wml320svDateDayFr) {
        wml320svDateDayFr__ = wml320svDateDayFr;
    }
    /**
     * <p>wml320svDateYearTo を取得します。
     * @return wml320svDateYearTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateYearTo__
     */
    public int getWml320svDateYearTo() {
        return wml320svDateYearTo__;
    }
    /**
     * <p>wml320svDateYearTo をセットします。
     * @param wml320svDateYearTo wml320svDateYearTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateYearTo__
     */
    public void setWml320svDateYearTo(int wml320svDateYearTo) {
        wml320svDateYearTo__ = wml320svDateYearTo;
    }
    /**
     * <p>wml320svDateMonthTo を取得します。
     * @return wml320svDateMonthTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateMonthTo__
     */
    public int getWml320svDateMonthTo() {
        return wml320svDateMonthTo__;
    }
    /**
     * <p>wml320svDateMonthTo をセットします。
     * @param wml320svDateMonthTo wml320svDateMonthTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateMonthTo__
     */
    public void setWml320svDateMonthTo(int wml320svDateMonthTo) {
        wml320svDateMonthTo__ = wml320svDateMonthTo;
    }
    /**
     * <p>wml320svDateDayTo を取得します。
     * @return wml320svDateDayTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateDayTo__
     */
    public int getWml320svDateDayTo() {
        return wml320svDateDayTo__;
    }
    /**
     * <p>wml320svDateDayTo をセットします。
     * @param wml320svDateDayTo wml320svDateDayTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svDateDayTo__
     */
    public void setWml320svDateDayTo(int wml320svDateDayTo) {
        wml320svDateDayTo__ = wml320svDateDayTo;
    }
    /**
     * <p>wml320svLabel を取得します。
     * @return wml320svLabel
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svLabel__
     */
    public int getWml320svLabel() {
        return wml320svLabel__;
    }
    /**
     * <p>wml320svLabel をセットします。
     * @param wml320svLabel wml320svLabel
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svLabel__
     */
    public void setWml320svLabel(int wml320svLabel) {
        wml320svLabel__ = wml320svLabel;
    }
    /**
     * <p>wml320svSize を取得します。
     * @return wml320svSize
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svSize__
     */
    public String getWml320svSize() {
        return wml320svSize__;
    }
    /**
     * <p>wml320svSize をセットします。
     * @param wml320svSize wml320svSize
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320svSize__
     */
    public void setWml320svSize(String wml320svSize) {
        wml320svSize__ = wml320svSize;
    }
    /**
     * <p>wml320NowPage を取得します。
     * @return wml320NowPage
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320NowPage__
     */
    public int getWml320NowPage() {
        return wml320NowPage__;
    }
    /**
     * <p>wml320NowPage をセットします。
     * @param wml320NowPage wml320NowPage
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320NowPage__
     */
    public void setWml320NowPage(int wml320NowPage) {
        wml320NowPage__ = wml320NowPage;
    }
    /**
     * <p>wml320pageTop を取得します。
     * @return wml320pageTop
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320pageTop__
     */
    public int getWml320pageTop() {
        return wml320pageTop__;
    }
    /**
     * <p>wml320pageTop をセットします。
     * @param wml320pageTop wml320pageTop
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320pageTop__
     */
    public void setWml320pageTop(int wml320pageTop) {
        wml320pageTop__ = wml320pageTop;
    }
    /**
     * <p>wml320pageBottom を取得します。
     * @return wml320pageBottom
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320pageBottom__
     */
    public int getWml320pageBottom() {
        return wml320pageBottom__;
    }
    /**
     * <p>wml320pageBottom をセットします。
     * @param wml320pageBottom wml320pageBottom
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320pageBottom__
     */
    public void setWml320pageBottom(int wml320pageBottom) {
        wml320pageBottom__ = wml320pageBottom;
    }
    /**
     * <p>wml320sortKey を取得します。
     * @return wml320sortKey
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320sortKey__
     */
    public int getWml320sortKey() {
        return wml320sortKey__;
    }
    /**
     * <p>wml320sortKey をセットします。
     * @param wml320sortKey wml320sortKey
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320sortKey__
     */
    public void setWml320sortKey(int wml320sortKey) {
        wml320sortKey__ = wml320sortKey;
    }
    /**
     * <p>wml320order を取得します。
     * @return wml320order
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320order__
     */
    public int getWml320order() {
        return wml320order__;
    }
    /**
     * <p>wml320order をセットします。
     * @param wml320order wml320order
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320order__
     */
    public void setWml320order(int wml320order) {
        wml320order__ = wml320order;
    }
    /**
     * <p>eml320searchFlg を取得します。
     * @return wml320searchFlg
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320searchFlg__
     */
    public boolean isWml320searchFlg() {
        return wml320searchFlg__;
    }
    /**
     * <p>eml320searchFlg をセットします。
     * @param wml320searchFlg wml320searchFlg
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320searchFlg__
     */
    public void setWml320searchFlg(boolean wml320searchFlg) {
        wml320searchFlg__ = wml320searchFlg;
    }
    /**
     * <p>wml320pageLabel を取得します。
     * @return wml320pageLabel
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320pageLabel__
     */
    public List<LabelValueBean> getWml320pageLabel() {
        return wml320pageLabel__;
    }
    /**
     * <p>wml320pageLabel をセットします。
     * @param wml320pageLabel wml320pageLabel
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320pageLabel__
     */
    public void setWml320pageLabel(List<LabelValueBean> wml320pageLabel) {
        wml320pageLabel__ = wml320pageLabel;
    }
    /**
     * <p>wml320mailList を取得します。
     * @return wml320mailList
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320mailList__
     */
    public List<Wml320DspModel> getWml320mailList() {
        return wml320mailList__;
    }
    /**
     * <p>wml320mailList をセットします。
     * @param wml320mailList wml320mailList
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320mailList__
     */
    public void setWml320mailList(List<Wml320DspModel> wml320mailList) {
        wml320mailList__ = wml320mailList;
    }
    /**
     * <p>wml320pageCombo を取得します。
     * @return wml320pageCombo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320pageCombo__
     */
    public List<LabelValueBean> getWml320pageCombo() {
        return wml320pageCombo__;
    }
    /**
     * <p>wml320pageCombo をセットします。
     * @param wml320pageCombo wml320pageCombo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320pageCombo__
     */
    public void setWml320pageCombo(List<LabelValueBean> wml320pageCombo) {
        wml320pageCombo__ = wml320pageCombo;
    }
    /**
     * <p>wml320accountCombo を取得します。
     * @return wml320accountCombo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320accountCombo__
     */
    public List<UsrLabelValueBean> getWml320accountCombo() {
        return wml320accountCombo__;
    }
    /**
     * <p>wml320accountCombo をセットします。
     * @param wml320accountCombo wml320accountCombo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320accountCombo__
     */
    public void setWml320accountCombo(List<UsrLabelValueBean> wml320accountCombo) {
        wml320accountCombo__ = wml320accountCombo;
    }
    /**
     * <p>wml320labelCombo を取得します。
     * @return wml320labelCombo
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320labelCombo__
     */
    public List<LabelValueBean> getWml320labelCombo() {
        return wml320labelCombo__;
    }
    /**
     * <p>wml320labelCombo をセットします。
     * @param wml320labelCombo wml320labelCombo
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320labelCombo__
     */
    public void setWml320labelCombo(List<LabelValueBean> wml320labelCombo) {
        wml320labelCombo__ = wml320labelCombo;
    }
    /**
     * <p>wml320svSortKey を取得します。
     * @return wml320svSortKey
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320svSortKey__
     */
    public int getWml320svSortKey() {
        return wml320svSortKey__;
    }
    /**
     * <p>wml320svSortKey をセットします。
     * @param wml320svSortKey wml320svSortKey
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320svSortKey__
     */
    public void setWml320svSortKey(int wml320svSortKey) {
        wml320svSortKey__ = wml320svSortKey;
    }
    /**
     * <p>wml320svOrder を取得します。
     * @return wml320svOrder
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320svOrder__
     */
    public int getWml320svOrder() {
        return wml320svOrder__;
    }
    /**
     * <p>wml320svOrder をセットします。
     * @param wml320svOrder wml320svOrder
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320svOrder__
     */
    public void setWml320svOrder(int wml320svOrder) {
        wml320svOrder__ = wml320svOrder;
    }
    /**
     * <p>wml320sortKeyCombo を取得します。
     * @return wml320sortKeyCombo
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320sortKeyCombo__
     */
    public List<LabelValueBean> getWml320sortKeyCombo() {
        return wml320sortKeyCombo__;
    }
    /**
     * <p>wml320sortKeyCombo をセットします。
     * @param wml320sortKeyCombo wml320sortKeyCombo
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320sortKeyCombo__
     */
    public void setWml320sortKeyCombo(List<LabelValueBean> wml320sortKeyCombo) {
        wml320sortKeyCombo__ = wml320sortKeyCombo;
    }
    /**
     * <p>wml320initFlg を取得します。
     * @return wml320initFlg
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320initFlg__
     */
    public int getWml320initFlg() {
        return wml320initFlg__;
    }
    /**
     * <p>wml320initFlg をセットします。
     * @param wml320initFlg wml320initFlg
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320initFlg__
     */
    public void setWml320initFlg(int wml320initFlg) {
        wml320initFlg__ = wml320initFlg;
    }
    /**
     * <p>wml320delLimit を取得します。
     * @return wml320delLimit
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320delLimit__
     */
    public int getWml320delLimit() {
        return wml320delLimit__;
    }
    /**
     * <p>wml320delLimit をセットします。
     * @param wml320delLimit wml320delLimit
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#wml320delLimit__
     */
    public void setWml320delLimit(int wml320delLimit) {
        wml320delLimit__ = wml320delLimit;
    }
    /**
     * <p>accountName を取得します。
     * @return accountName
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#accountName__
     */
    public String getAccountName() {
        return accountName__;
    }
    /**
     * <p>accountName をセットします。
     * @param accountName accountName
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#accountName__
     */
    public void setAccountName(String accountName) {
        accountName__ = accountName;
    }
    /**
     * <p>deleteMailCount を取得します。
     * @return deleteMailCount
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#deleteMailCount__
     */
    public int getDeleteMailCount() {
        return deleteMailCount__;
    }
    /**
     * <p>deleteMailCount をセットします。
     * @param deleteMailCount deleteMailCount
     * @see jp.groupsession.v2.wml.wml320.Wml320ParamModel#deleteMailCount__
     */
    public void setDeleteMailCount(int deleteMailCount) {
        deleteMailCount__ = deleteMailCount;
    }
    /**
     * <p>wml320DateFr を取得します。
     * @return wml320DateFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320DateFr__
     */
    public String getWml320DateFr() {
        return wml320DateFr__;
    }
    /**
     * <p>wml320DateFr をセットします。
     * @param wml320DateFr wml320DateFr
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320DateFr__
     */
    public void setWml320DateFr(String wml320DateFr) {
        wml320DateFr__ = wml320DateFr;
    }
    /**
     * <p>wml320DateTo を取得します。
     * @return wml320DateTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320DateTo__
     */
    public String getWml320DateTo() {
        return wml320DateTo__;
    }
    /**
     * <p>wml320DateTo をセットします。
     * @param wml320DateTo wml320DateTo
     * @see jp.groupsession.v2.wml.wml320.Wml320Form#wml320DateTo__
     */
    public void setWml320DateTo(String wml320DateTo) {
        wml320DateTo__ = wml320DateTo;
    }
}
