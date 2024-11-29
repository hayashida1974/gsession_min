package jp.groupsession.v2.man.man050;

import java.util.List;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 最終ログイン時間画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man050ParamModel extends AbstractParamModel {
    /** グループID */
    private int man050grpSid__ = -1;
    /** オーダーキー */
    private int man050OrderKey__ = GSConst.ORDER_KEY_ASC;
    /** ソートキー(デフォルトで最終ログイン時間) */
    private int man050SortKey__ = GSConstUser.USER_SORT_LALG;

    /** ユーザID(検索用) */
    private int man050usrSid__ = -1;
    /** 端末 */
    private int man050Terminal__;
    /** キャリア */
    private int man050Car__;

    /** 所属ユーザ一覧 */
    private List < Man050ViewModel > man050UserList__ = null;
    /** グループ一覧 */
    private List < LabelValueBean > man050GroupList__ = null;
    /** 戻り先 1:メイン,2:管理者ツール */
    private int man050Backurl__ = 1;
    /** 選択ユーザSID */
    private int man050SelectedUsrSid__;
    /** 管理者フラグ */
    private boolean man050adminFlg__ = false;
    /** 処理モード */
    private String man050cmdMode__ = GSConstMain.MODE_LIST;

    /** 所属ユーザ一覧(検索用) */
    private  List<UsrLabelValueBean> man050BelongUserList__ = null;

    /** 開始年 */
    private String man050FrYear__;
    /** 開始月 */
    private String man050FrMonth__;
    /** 開始日 */
    private String man050FrDay__;
    /** 終了年 */
    private String man050ToYear__;
    /** 終了月 */
    private String man050ToMonth__;
    /** 終了日 */
    private String man050ToDay__;
    /** 開始日 */
    private String man050FrDate__ = null;
    /** 終了年 */
    private String man050ToDate__ = null;

    /** ページ(上) */
    private int man050PageTop__ = 1;
    /** ページ(下) */
    private int man050PageBottom__ = 1;

    /** ページリスト */
    private List<LabelValueBean> man050PageList__;
    /** 検索したかどうかのフラグ */
    private int man050SearchFlg__ = GSConstUser.SEARCH_MI;
    //スケジュール用
    /** 処理モード */
    private String cmd__ = null;
    /** ユーザSID */
    private String sch010SelectUsrSid__;
    /** ユーザ区分 */
    private String sch010SelectUsrKbn__;
    /** 表示日付 */
    private String sch010DspDate__;
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
     * @return man050GroupList を戻します。
     */
    public List < LabelValueBean > getMan050GroupList() {
        return man050GroupList__;
    }

    /**
     * @param man050GroupList 設定する man050GroupList。
     */
    public void setMan050GroupList(List < LabelValueBean > man050GroupList) {
        man050GroupList__ = man050GroupList;
    }

    /**
     * @return man050grpSid を戻します。
     */
    public int getMan050grpSid() {
        return man050grpSid__;
    }

    /**
     * @param man050grpSid 設定する man050grpSid。
     */
    public void setMan050grpSid(int man050grpSid) {
        man050grpSid__ = man050grpSid;
    }

    /**
     * @return man050OrderKey を戻します。
     */
    public int getMan050OrderKey() {
        return man050OrderKey__;
    }

    /**
     * @param man050OrderKey 設定する man050OrderKey。
     */
    public void setMan050OrderKey(int man050OrderKey) {
        man050OrderKey__ = man050OrderKey;
    }

    /**
     * @return man050SortKey を戻します。
     */
    public int getMan050SortKey() {
        return man050SortKey__;
    }

    /**
     * @param man050SortKey 設定する man050SortKey。
     */
    public void setMan050SortKey(int man050SortKey) {
        man050SortKey__ = man050SortKey;
    }

    /**
     * @return man050UserList を戻します。
     */
    public List < Man050ViewModel > getMan050UserList() {
        return man050UserList__;
    }

    /**
     * @param man050UserList 設定する man050UserList。
     */
    public void setMan050UserList(List < Man050ViewModel > man050UserList) {
        man050UserList__ = man050UserList;
    }

    /**
     * <p>man050Backurl を取得します。
     * @return man050Backurl
     */
    public int getMan050Backurl() {
        return man050Backurl__;
    }

    /**
     * <p>man050Backurl をセットします。
     * @param man050Backurl man050Backurl
     */
    public void setMan050Backurl(int man050Backurl) {
        man050Backurl__ = man050Backurl;
    }

    /**
     * <p>man050SelectedUsrSid を取得します。
     * @return man050SelectedUsrSid
     */
    public int getMan050SelectedUsrSid() {
        return man050SelectedUsrSid__;
    }

    /**
     * <p>man050SelectedUsrSid をセットします。
     * @param man050SelectedUsrSid man050SelectedUsrSid
     */
    public void setMan050SelectedUsrSid(int man050SelectedUsrSid) {
        man050SelectedUsrSid__ = man050SelectedUsrSid;
    }

    /**
     * <p>man050adminFlg を取得します。
     * @return man050adminFlg
     */
    public boolean isMan050adminFlg() {
        return man050adminFlg__;
    }

    /**
     * <p>man050adminFlg をセットします。
     * @param man050adminFlg man050adminFlg
     */
    public void setMan050adminFlg(boolean man050adminFlg) {
        man050adminFlg__ = man050adminFlg;
    }

    /**
     * <p>man050cmdMode を取得します。
     * @return man050cmdMode
     */
    public String getMan050cmdMode() {
        return man050cmdMode__;
    }

    /**
     * <p>man050cmdMode をセットします。
     * @param man050cmdMode man050cmdMode
     */
    public void setMan050cmdMode(String man050cmdMode) {
        man050cmdMode__ = man050cmdMode;
    }

    /**
     * <p>man050FrDay を取得します。
     * @return man050FrDay
     */
    public String getMan050FrDay() {
        return man050FrDay__;
    }

    /**
     * <p>man050FrDay をセットします。
     * @param man050FrDay man050FrDay
     */
    public void setMan050FrDay(String man050FrDay) {
        man050FrDay__ = man050FrDay;
    }

    /**
     * <p>man050FrMonth を取得します。
     * @return man050FrMonth
     */
    public String getMan050FrMonth() {
        return man050FrMonth__;
    }

    /**
     * <p>man050FrMonth をセットします。
     * @param man050FrMonth man050FrMonth
     */
    public void setMan050FrMonth(String man050FrMonth) {
        man050FrMonth__ = man050FrMonth;
    }

    /**
     * <p>man050FrYear を取得します。
     * @return man050FrYear
     */
    public String getMan050FrYear() {
        return man050FrYear__;
    }

    /**
     * <p>man050FrYear をセットします。
     * @param man050FrYear man050FrYear
     */
    public void setMan050FrYear(String man050FrYear) {
        man050FrYear__ = man050FrYear;
    }

    /**
     * <p>man050ToDay を取得します。
     * @return man050ToDay
     */
    public String getMan050ToDay() {
        return man050ToDay__;
    }

    /**
     * <p>man050ToDay をセットします。
     * @param man050ToDay man050ToDay
     */
    public void setMan050ToDay(String man050ToDay) {
        man050ToDay__ = man050ToDay;
    }

    /**
     * <p>man050ToMonth を取得します。
     * @return man050ToMonth
     */
    public String getMan050ToMonth() {
        return man050ToMonth__;
    }

    /**
     * <p>man050ToMonth をセットします。
     * @param man050ToMonth man050ToMonth
     */
    public void setMan050ToMonth(String man050ToMonth) {
        man050ToMonth__ = man050ToMonth;
    }

    /**
     * <p>man050ToYear を取得します。
     * @return man050ToYear
     */
    public String getMan050ToYear() {
        return man050ToYear__;
    }

    /**
     * <p>man050ToYear をセットします。
     * @param man050ToYear man050ToYear
     */
    public void setMan050ToYear(String man050ToYear) {
        man050ToYear__ = man050ToYear;
    }

    /**
     * <p>man050FrDate を取得します。
     * @return man050FrDate
     * @see jp.groupsession.v2.man.man050.Man050ParamModel#man050FrDate__
     */
    public String getMan050FrDate() {
        return man050FrDate__;
    }

    /**
     * <p>man050FrDate をセットします。
     * @param man050FrDate man050FrDate
     * @see jp.groupsession.v2.man.man050.Man050ParamModel#man050FrDate__
     */
    public void setMan050FrDate(String man050FrDate) {
        man050FrDate__ = man050FrDate;
    }

    /**
     * <p>man050ToDate を取得します。
     * @return man050ToDate
     * @see jp.groupsession.v2.man.man050.Man050ParamModel#man050ToDate__
     */
    public String getMan050ToDate() {
        return man050ToDate__;
    }

    /**
     * <p>man050ToDate をセットします。
     * @param man050ToDate man050ToDate
     * @see jp.groupsession.v2.man.man050.Man050ParamModel#man050ToDate__
     */
    public void setMan050ToDate(String man050ToDate) {
        man050ToDate__ = man050ToDate;
    }

    /**
     * <p>man050usrSid を取得します。
     * @return man050usrSid
     */
    public int getMan050usrSid() {
        return man050usrSid__;
    }

    /**
     * <p>man050usrSid をセットします。
     * @param man050usrSid man050usrSid
     */
    public void setMan050usrSid(int man050usrSid) {
        man050usrSid__ = man050usrSid;
    }

    /**
     * <p>man050Car を取得します。
     * @return man050Car
     */
    public int getMan050Car() {
        return man050Car__;
    }

    /**
     * <p>man050Car をセットします。
     * @param man050Car man050Car
     */
    public void setMan050Car(int man050Car) {
        man050Car__ = man050Car;
    }

    /**
     * <p>man050Terminal を取得します。
     * @return man050Terminal
     */
    public int getMan050Terminal() {
        return man050Terminal__;
    }

    /**
     * <p>man050Terminal をセットします。
     * @param man050Terminal man050Terminal
     */
    public void setMan050Terminal(int man050Terminal) {
        man050Terminal__ = man050Terminal;
    }

    /**
     * <p>man050BelongUserList を取得します。
     * @return man050BelongUserList
     */
    public List<UsrLabelValueBean> getMan050BelongUserList() {
        return man050BelongUserList__;
    }

    /**
     * <p>man050BelongUserList をセットします。
     * @param man050BelongUserList man050BelongUserList
     */
    public void setMan050BelongUserList(List<UsrLabelValueBean> man050BelongUserList) {
        man050BelongUserList__ = man050BelongUserList;
    }

    /**
     * <p>man050PageBottom を取得します。
     * @return man050PageBottom
     */
    public int getMan050PageBottom() {
        return man050PageBottom__;
    }

    /**
     * <p>man050PageBottom をセットします。
     * @param man050PageBottom man050PageBottom
     */
    public void setMan050PageBottom(int man050PageBottom) {
        man050PageBottom__ = man050PageBottom;
    }

    /**
     * <p>man050PageList を取得します。
     * @return man050PageList
     */
    public List<LabelValueBean> getMan050PageList() {
        return man050PageList__;
    }

    /**
     * <p>man050PageList をセットします。
     * @param man050PageList man050PageList
     */
    public void setMan050PageList(List<LabelValueBean> man050PageList) {
        man050PageList__ = man050PageList;
    }

    /**
     * <p>man050PageTop を取得します。
     * @return man050PageTop
     */
    public int getMan050PageTop() {
        return man050PageTop__;
    }

    /**
     * <p>man050PageTop をセットします。
     * @param man050PageTop man050PageTop
     */
    public void setMan050PageTop(int man050PageTop) {
        man050PageTop__ = man050PageTop;
    }

    /**
     * <p>man050SearchFlg を取得します。
     * @return man050SearchFlg
     */
    public int getMan050SearchFlg() {
        return man050SearchFlg__;
    }

    /**
     * <p>man050SearchFlg をセットします。
     * @param man050SearchFlg man050SearchFlg
     */
    public void setMan050SearchFlg(int man050SearchFlg) {
        man050SearchFlg__ = man050SearchFlg;
    }
}