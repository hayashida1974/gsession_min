package jp.groupsession.v2.cmn.formmodel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.formmodel.ui.InnerUserSelector;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
*
* <br>[機  能] ユーザグループ選択コンボ モデル
* <br>[解  説] ユーザグループ選択で扱う、表示情報と画面からの入力情報を格納する
* <br>[備  考]
*
* @author JTS
*/
public class UserGroupSelectModel extends AbstractFormModel {
    /**定数 アクション部 選択済みへ追加*/
    public static final String CMDACTION_ADD_SELECT = "addSelect";
    /**定数 アクション部 選択済みから除去*/
    public static final String CMDACTION_REM_SELECT = "removeSelect";
    /**定数 アクション部 自動設置選択ボックスキー*/
    public static final String KEY_DEFAULT = "default";

    /**定数 複数選択OFF*/
    public static final int FLG_MULTI_OFF = 0;
    /**定数 複数選択ON*/
    public static final int FLG_MULTI_ON = 1;
    /**定数 選択制限OFF*/
    public static final int FLG_SEIGEN_OFF = 0;
    /**定数 選択制限 選択可能ユーザ選択*/
    public static final int FLG_SEIGEN_SELECTABLE = 1;
    /**定数 ダイアログ選択OFF*/
    public static final int FLG_DIALOG_OFF = 0;
    /**定数 ダイアログ選択ON*/
    public static final int FLG_DIALOG_ON = 1;

    /**選択済みリスト参照キー*/
    private String[] keys__;
    /** 選択済みリスト 表示用タイトル*/
    private HashMap<String, String> selectedListName__ = new HashMap<String, String>();
    /** 選択済みリスト 表示用*/
    private HashMap<String, List<UsrLabelValueBean>> selectedList__ =
            new HashMap<String, List<UsrLabelValueBean>>();
    /** 未選択リスト 表示用*/
    private List<UsrLabelValueBean> noselectList__ = new ArrayList<UsrLabelValueBean>();
    /** 選択済み ＋ 未選択リスト*/
    private List<? extends LabelValueBean> allList__;
    /** 選択済みID リスト リクエストパラメータの選択値格納*/
    private HashMap<String, String[]> selected__ = new HashMap<String, String[]>();
    /** 選択解除ID リスト ガラケーの選択解除ボタン用*/
    private HashMap<String, String[]> rmSelectedMbh__ = new HashMap<String, String[]>();

    /** グループコンボ*/
    private GroupComboModel group__ = new GroupComboModel();
    /** 選択済みリスト利用区分*/
    private HashMap<String, String> selectedListUse__ = new HashMap<String, String>();
    /** 一時選択対象*/
    private String[] targetSelected__;
    /** 一時選択対象*/
    private String[] targetNoSelected__;
    /** 表示非表示*/
    private boolean hide__ = false;
    /** 選択除外ユーザ*/
    private String[] banUsrSid__;
    /** 選択対象ユーザ*/
    private String[] selectableUsrSid__;
    /** 単体選択 複数選択フラグ*/
    private int multiFlg__ = FLG_MULTI_ON;
    /** ユーザ選択対象制限フラグ*/
    private int useSeigen__ = FLG_SEIGEN_OFF;
    /** 選択動作 ダイアログフラグ*/
    private int useDialog__ = FLG_DIALOG_OFF;
    /** プラグイン使用制限の反映*/
    private boolean usePluginSeigen__ = false;
    /** プラグインID*/
    private String pluginId__;
    /** 選択対象ユーザ状態フラグ -1:すべて 0:削除ユーザをのぞく 9:削除ユーザのみ*/
    private int selectJKbn__ = GSConstUser.USER_JTKBN_ACTIVE;

    /** ユーザ選択動作 実体 (複数選択時)*/
    private final InnerUserSelector innorSelector__ =
            InnerUserSelector.builder()
                .chainParent(this)
                .build();

    /** パラメータ文字列 ユーザグループ選択コンボ */
    private String paramName__;

    /** 表示初期化*/
    private boolean dspInited__ = false;



    /**
     *
     */
    public UserGroupSelectModel() {
        super();
    }

    /**
     * @param json json
     */
    public UserGroupSelectModel(JSONObject json) {
        super(json);
    }
    //    /**
//     * <p>selectedList を取得します。
//     * @return selectedList
//     */
//    public HashMap<String, List<UsrLabelValueBean>> getSelectedList() {
//        return selectedList__;
//    }

    /**
     * <p>ユーザ選択UIにて、グループを含めるかを設定します
     * @return ユーザ選択UIにて、グループを含めるか
     */
    public boolean getSelecterWithGroup() {
        return innorSelector__.isWithGroup();
    }

    /**
     * <p>ユーザ選択UIにて、グループを含めるかを設定します
     * @param withGroup
     */
    public void setSelecterWithGroup(boolean withGroup) {
        innorSelector__.setWithGroup(withGroup);
    }
    
    /**
     * <p>selectedList を取得します。
     * @param key 参照ID
     * @return selectedList
     */
    public List<UsrLabelValueBean> getSelectedList(String key) {
        if (selectedList__.containsKey(key)) {
            return selectedList__.get(key);
        }
        return new ArrayList<UsrLabelValueBean>();
    }
    /**
     * <p>selectedList を取得します。
     * @return selectedList
     */
    public List<UsrLabelValueBean> getSelectedListAll() {
        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();
        for (Entry<String, List<UsrLabelValueBean>> entry : selectedList__.entrySet()) {
            ret.addAll(entry.getValue());
        }
        return ret;
    }
    /**
     * <p>selectedList を取得します。
     * @param key 参照ID
     * @return selectedList
     */
    public UsrLabelValueBean getSelectedUser(String key) {
        if (selectedList__.containsKey(key)) {
            List<UsrLabelValueBean> selectedUser = selectedList__.get(key);
            if (selectedUser.size() > 0) {
                return selectedUser.get(0);
            }
            return null;
        }
        return null;
    }
    /**
     * <p>selectedList を取得します。
     * @return selectedList
     */
    public UsrLabelValueBean getSelectedUserSimple() {
        return getSelectedUser(KEY_DEFAULT);
    }

//    /**
//     * <p>selectedList をセットします。
//     * @param selectedList selectedList
//     */
//    public void setSelectedList(
//            HashMap<String, List<UsrLabelValueBean>> selectedList) {
//        selectedList__ = selectedList;
//    }
    /**
     * <p>selectedList をセットします。
     * @param key 参照ID
     * @param selectedList selectedList
     */
    public void setSelectedList(
            String key, List<UsrLabelValueBean> selectedList) {
        selectedList__.put(key, selectedList);
    }

    /**
     * <p>noselectList を取得します。
     * @return noselectList
     */
    public List<UsrLabelValueBean> getNoselectList() {
        return noselectList__;
    }

    /**
     * <p>noselectList をセットします。
     * @param noselectList noselectList
     */
    public void setNoselectList(List<UsrLabelValueBean> noselectList) {
        noselectList__ = noselectList;
    }

    /**
     * <p>selected を取得します。
     * @return selected
     */
    public HashMap<String, String[]> getSelected() {

        return selected__;
    }

    /**
     * <p>selected を取得します。
     * @param key 参照ID
     * @return selected
     */
    public String[] getSelected(String key) {
        if (selected__.containsKey(key)) {
            String[] ret = selected__.get(key);
            if (ret == null) {
                return  new String[0];
            }
            return ret;
        }
        return new String[0];
    }
    /**
     * <p>selected を取得します。
     * @param key 参照ID
     * @return selected
     */
    public String getSelectedSingle(String key) {
        String[] retArr = getSelected(key);
        if (ArrayUtils.isEmpty(retArr)) {
            return "";
        }
        return retArr[0];
    }
    /**
     * <p>selected を取得します。
     * 単体選択モード専用
     * @return selected
     */
    public String getSelectedSingle() {
        return getSelectedSingle(KEY_DEFAULT);
    }
    /**
     * <p>selected を取得します。
     * シンプル実装用
     * @return selected
     */
    public String[] getSelectedSimple() {
        return getSelected(KEY_DEFAULT);
    }

    /**
     * <p>selected をセットします。
     * @param selected selected
     */
    public void setSelected(HashMap<String, String[]> selected) {
        selected__ = selected;
    }
    /**
     * <p>selected をセットします。
     * @param key 参照ID
     * @param selected selected
     */
    public void setSelected(String key, String[] selected) {
        selected__.put(key, selected);
    }
    /**
     * <p>selected をセットします。
     * @param key 参照ID
     * @param selected selected
     */
    public void setSelected(String key, String selected) {
        setSelected(key, new String[] {selected});
    }
    /**
     * <p>selected をセットします。
     * 単体選択モード専用
     * @param selected selected
     */
    public void setSelectedSingle(String selected) {
        setSelected(KEY_DEFAULT, selected);
    }
    /**
     * <p>selected をセットします。
     * シンプル実装用
     * @param selected selected
     */
    public void setSelectedSimple(String[] selected) {
        setSelected(KEY_DEFAULT, selected);
    }

    /**
     * <p>keys を取得します。
     * @return keys
     */
    public String[] getKeys() {
        return keys__;
    }

    /**
     * <p>keys をセットします。
     * @param keys keys
     */
    public void setKeys(String[] keys) {
        keys__ = keys;
    }

    /**
     * <p>selectedListName を取得します。
     * @return selectedListName
     */
    public HashMap<String, String> getSelectedListName() {
        return selectedListName__;
    }

    /**
     * <p>selectedListName をセットします。
     * @param selectedListName selectedListName
     */
    public void setSelectedListName(HashMap<String, String> selectedListName) {
        selectedListName__ = selectedListName;
    }

    /**
     * <p>group を取得します。
     * @return group
     */
    public GroupComboModel getGroup() {
        return group__;
    }

    /**
     * <p>group をセットします。
     * @param group group
     */
    public void setGroup(GroupComboModel group) {
        group__ = group;
    }
    /**
     * <p>selectedListUse を取得します。
     * @return selectedListUse
     */
    public HashMap<String, String> getSelectedListUse() {
        return selectedListUse__;
    }
    /**
     * <p>selectedListUse をセットします。
     * @param selectedListUse selectedListUse
     */
    public void setSelectedListUse(HashMap<String, String> selectedListUse) {
        selectedListUse__ = selectedListUse;
    }
    /**
     * <p>selectedListUse をセットします。
     * @param key key
     * @param value value
     */
    public void setSelectedListUse(String key, String value) {
        selectedListUse__.put(key, value);
    }
    /**
     * <p>targetSelected を取得します。
     * @return targetSelected
     */
    public String[] getTargetSelected() {
        return targetSelected__;
    }
    /**
     * <p>targetSelected をセットします。
     * @param targetSelected targetSelected
     */
    public void setTargetSelected(String[] targetSelected) {
        targetSelected__ = targetSelected;
    }
    /**
     * <p>targetNoSelected を取得します。
     * @return targetNoSelected
     */
    public String[] getTargetNoSelected() {
        return targetNoSelected__;
    }
    /**
     * <p>targetNoSelected をセットします。
     * @param targetNoSelected targetNoSelected
     */
    public void setTargetNoSelected(String[] targetNoSelected) {
        targetNoSelected__ = targetNoSelected;
    }
    /**
     * <p>hide を取得します。
     * @return hide
     */
    public boolean isHide() {
        return hide__;
    }
    /**
     * <p>hide をセットします。
     * @param hide hide
     */
    public void setHide(boolean hide) {
        hide__ = hide;
    }
    /**
     *
     * <br>[機  能] hideに応じてCSSのdisplay記述を返します。
     * <br>[解  説]
     * <br>[備  考]
     * @return CSSのdisplay記述
     */
    public String getStyleDisplay() {
        if (isHide()) {
            return "display:none;";
        } else {
            return "display:block;";
        }
    }

    /**
     * <p>banUsrSid を取得します。
     * @return banUsrSid
     */
    public String[] getBanUsrSid() {
        return banUsrSid__;
    }
    /**
     * <p>banUsrSid をセットします。
     * @param banUsrSid banUsrSid
     */
    public void setBanUsrSid(String[] banUsrSid) {
        banUsrSid__ = banUsrSid;
    }
    /**
     * <p>multiFlg を取得します。
     * @return multiFlg
     */
    public int getMultiFlg() {
        return multiFlg__;
    }

    /**
     * <p>multiFlg をセットします。
     * @param multiFlg multiFlg
     */
    public void setMultiFlg(int multiFlg) {
        multiFlg__ = multiFlg;
    }

    /**
     * <p>selectableUsrSid を取得します。
     * @return selectableUsrSid
     */
    public String[] getSelectable() {
        return selectableUsrSid__;
    }

    /**
     * <p>selectableUsrSid をセットします。
     * @param selectableUsrSid selectableUsrSid
     */
    public void setSelectable(String[] selectableUsrSid) {
        selectableUsrSid__ = selectableUsrSid;
    }

    /**
    *
    * <br>[機  能] ユーザ選択フォーム初期化処理(最小実装)
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @param defGroupSid デフォルトグループSID
    * @throws SQLException SQL実行時例外
    */
   public void init(Connection con, RequestModel reqMdl,
           String defGroupSid) throws SQLException {
       init(con, reqMdl, null, null, defGroupSid, null, banUsrSid__);
   }

    /**
    *
    * <br>[機  能] ユーザ選択フォーム初期化処理(選択済みリストをタグリブで定義する場合用)
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param reqMdl リクエストモデル
    * @param defGroupSid デフォルトグループSID
    * @param grplist グループ選択リスト nullの場合はグループ一覧を含むグループツリー
    * @param banSids 選択対象に含まないSID
    * @throws SQLException SQL実行時例外
    */
   public void init(Connection con, RequestModel reqMdl,
           String defGroupSid, List<UsrLabelValueBean> grplist,
           String[] banSids) throws SQLException {
       init(con, reqMdl, null, null, defGroupSid, grplist, banSids);
   }

    /**
     *
     * <br>[機  能] ユーザ選択フォーム初期化処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param keys 選択済みリスト参照キー
     * @param selectedListName 選択済みリスト表示タイトル
     * @param defGroupSid デフォルトグループSID
     * @param grplist グループ選択リスト nullの場合はグループ一覧を含むグループツリー
     * @param banSids 選択対象に含まないSID
     * @throws SQLException SQL実行時例外
     */
    @SuppressWarnings("unchecked")
    public void init(Connection con, RequestModel reqMdl,
            String[] keys, String[] selectedListName,
            String defGroupSid, List<UsrLabelValueBean> grplist,
            String[] banSids) throws SQLException {
        if (banSids == null) {
            banSids = new String[0];
        }
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        banUsrSid__ = banSids;
        Set<String> selectedIDList = new HashSet<String>();
        selectedIDList.addAll(Arrays.asList(banUsrSid__));

        UserGroupSelectBiz biz = new UserGroupSelectBiz();
        Set<String> usrSelectableSid = new HashSet<String>();

        if (grplist == null) {
            grplist = biz.getGroupLabel(reqMdl, con);
        }
        //グループ一覧選択判定
        boolean groupSelFlg = false;
        if (grplist != null) {
            String grpSel = String.valueOf(UserGroupSelectBiz.GROUP_GRPLIST);
            for (UsrLabelValueBean label : grplist) {
                if (grpSel.equals(label.getValue())) {
                    groupSelFlg = true;
                }
            }
        }
        if (getUseSeigen() == FLG_SEIGEN_SELECTABLE) {
            Set<String> grpSelectableSid = new HashSet<String>();
            if (!ArrayUtils.isEmpty(getSelectable())) {
                for (String sid : getSelectable()) {
                    if (sid.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                        grpSelectableSid.add(
                                sid.substring(
                                        UserGroupSelectBiz.GROUP_PREFIX.length()));
                    } else {
                        usrSelectableSid.add(String.valueOf(
                                NullDefault.getInt(sid, -1)
                                ));
                    }
                }
            }
            //選択可能ユーザSIDから除外ユーザSIDを除外
            usrSelectableSid = new HashSet<String>(
                    CollectionUtils.subtract(
                            usrSelectableSid, Arrays.asList(banUsrSid__)));

            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            ArrayList<Integer> grpSids = belongDao.selectUserBelongGroupSid(
                    new ArrayList<>(usrSelectableSid));

            List<String> usrSids = new ArrayList<String>();
            if (grpSelectableSid.size() > 0) {
                usrSelectableSid.addAll(belongDao.select(
                            grpSelectableSid.toArray(new String[grpSelectableSid.size()])));
            }
            for (Integer sid : grpSids) {
                grpSelectableSid.add(sid.toString());
            }
            usrSelectableSid.addAll(usrSids);
            group__.setSelectable(new ArrayList<>(grpSelectableSid));
            group__.setUseSeigen(FLG_SEIGEN_SELECTABLE);
        }
        if (getUseDialog() == FLG_DIALOG_OFF) {
            group__.init(grplist, defGroupSid);
        }

        //ガラケー版削除ボタンによる選択解除
        Map<String, List<String>> rmSelectedMbh = new HashMap<String, List<String>>();
        for (String entry:rmSelectedMbh__.keySet()) {
            String key = entry.substring(0, entry.indexOf("_"));
            String value = entry.substring(entry.indexOf("_") + 1);
            if (rmSelectedMbh.containsKey(key)) {
                rmSelectedMbh.get(key).add(value);
            } else {
                ArrayList<String> values = new ArrayList<>();
                values.add(value);
                rmSelectedMbh.put(key, values);
            }
        }
        for (Entry<String, List<String>> entry:rmSelectedMbh.entrySet()) {
            if (selected__.containsKey(entry.getKey())) {
                Set<String> selectedSet =  new HashSet<String>(
                        CollectionUtils.subtract(
                                Arrays.asList(selected__.get(entry.getKey())), entry.getValue()));
                setSelected(entry.getKey(), selectedSet.toArray(new String[selectedSet.size()]));

            }
        }
        //除外リスト生成 （指定ユーザ + システムユーザ）
        List<String> banList = new ArrayList<>(Arrays.asList(banUsrSid__));
        banList.add(String.valueOf(GSConst.SYSTEM_USER_ADMIN));
        banList.add(String.valueOf(GSConst.SYSTEM_USER_MAIL));
//        //選択済みリスト参照キーごとにリストを初期化
        for (Entry<String, String[]> entry : selected__.entrySet()) {
            String[] selected = getSelected(entry.getKey());
            //未選択は空配列で初期化する。
            if (selected.length == 0) {
                setSelected(entry.getKey(), selected);
            }
            List<String> selectSidList = new ArrayList<String>(Arrays.asList(selected));
            selectSidList.removeAll(banList);
            if (!CollectionUtils.isEmpty(usrSelectableSid)) {
                //選択可能SIDリストとの積集合をとる
                selectSidList = (List<String>) CollectionUtils.intersection(
                        selectSidList, usrSelectableSid);
            }
            if (usePluginSeigen__) {
                selectSidList = __getCanUsePluginUser(con, selectSidList);
            }
            //グループ選択不可の場合はグループを除外
            if (!groupSelFlg) {
                selectSidList = __getUserSelect(con, selectSidList);
            }
            selected = selectSidList.toArray(new String[selectSidList.size()]);
            List<UsrLabelValueBean> selectedList = biz.getSelectedLabel(
                    selected, sessionUsrSid, con, selectJKbn__);
            selectedList__.put(entry.getKey(), selectedList);
        }
        if (getUseDialog() == FLG_DIALOG_OFF) {
            if (getMultiFlg() == FLG_MULTI_ON) {
                //指定した選択済みリストのみの使用フラグを立てる
                if (keys != null) {
                    setKeys(keys);
                    for (int i = 0; i < keys.length; i++) {
                        if (i < selectedListName.length) {
                            selectedListName__.put(keys[i], selectedListName[i]);
                            String[] selected = getSelected(keys[i]);
                            selectedIDList.addAll(Arrays.asList(selected));
                            selectedListUse__.put(keys[i], String.valueOf(true));
                        }
                    }
                } else {
                    selectedListName__.put(KEY_DEFAULT, "");
                    String[] selected = getSelected(KEY_DEFAULT);
                    selectedIDList.addAll(Arrays.asList(selected));
                    selectedListUse__.put(KEY_DEFAULT, String.valueOf(true));

                }
                noselectList__ = __loadNonSelectLabel(biz, group__.getSelectedSingle(),
                        selectedIDList.toArray(new String[selectedIDList.size()]),
                        reqMdl.getSmodel().getUsrsid(), con, usrSelectableSid);

                List<UsrLabelValueBean> allList = new ArrayList<UsrLabelValueBean>();

                if (selectedList__ != null) {
                    for (List<UsrLabelValueBean> selected: selectedList__.values()) {
                        allList.addAll(selected);
                    }
                }
                if (noselectList__ != null) {
                    allList.addAll(noselectList__);
                }
                allList__ = allList;
            } else {
                allList__ = __loadNonSelectLabel(biz, group__.getSelectedSingle(),
                        selectedIDList.toArray(new String[selectedIDList.size()]),
                        reqMdl.getSmodel().getUsrsid(), con, usrSelectableSid);
                addKey(KEY_DEFAULT, "");
            }
        }
        dspInited__ = true;
    }
    /**
     *
     * <br>[機  能] 未選択 ユーザラベルの取得
     * <br>[解  説]
     * <br>[備  考]
     * @param biz ユーザ選択Biz
     * @param grpSid 表示中グループSID
     * @param selectList 選択済みSIDリスト
     * @param sessionUsrSid セッションユーザSID
     * @param con コネクション
     * @param usrSelectableSid 選択可能SID
     * @return ユーザラベル
     * @throws SQLException SQL実行時例外
     */
    @SuppressWarnings("unchecked")
    private List<UsrLabelValueBean> __loadNonSelectLabel(
            UserGroupSelectBiz biz,
            String grpSid,
            String[] selectList,
            int sessionUsrSid,
            Connection con,
            Set<String> usrSelectableSid
            ) throws SQLException {
        ArrayList<UsrLabelValueBean> noselLabel = biz.getNonSelectLabel(
                grpSid, selectList, sessionUsrSid, con);
        Set<String> noselSidSet = new HashSet<>();
        for (UsrLabelValueBean label : noselLabel) {
            noselSidSet.add(label.getValue());
        }
        List<String> noselSids = new ArrayList<String>(
                noselSidSet);
        if (useSeigen__ == FLG_SEIGEN_SELECTABLE) {
            //選択可能SIDリストとの積集合をとる
            noselSids = (List<String>) CollectionUtils.intersection(
                    noselSids, usrSelectableSid);
        }
        if (usePluginSeigen__) {
            noselSids = __getCanUsePluginUser(con, noselSids);
        }
        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();
        for (UsrLabelValueBean label : noselLabel) {
            if (noselSids.contains(label.getValue())) {
                ret.add(label);
            }
        }

        return ret;

    }
    /**
     * <br>[機  能] 指定されたユーザ選択からグループ選択を除外して返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID
     * @return ユーザSID
     * @throws SQLException SQL実行時例外
     */
    private List<String> __getUserSelect(Connection con, List<String> userSidList) {
        if (userSidList == null || userSidList.size() == 0) {
            return userSidList;
        }
        List<String> ret = new ArrayList<String>();

        for (String userSid : userSidList) {
            if (ValidateUtil.isNumberHaifun(userSid)) {
                ret.add(userSid);
            }
        }
        return ret;


    }
    /**
     * <br>[機  能] 指定されたユーザから対象プラグインを使用可能なユーザを選択して返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID
     * @return ユーザSID
     * @throws SQLException SQL実行時例外
     */
    private List<String> __getCanUsePluginUser(Connection con, List<String> userSidList)
    throws SQLException {
        if (userSidList == null || userSidList.size() == 0) {
            return userSidList;
        }

        List<Integer> iUserSidList = new ArrayList<Integer>(userSidList.size());
        for (String userSid : userSidList) {
            try {
                iUserSidList.add(Integer.valueOf(userSid));
            } catch (NumberFormatException e) {
                //数値以外のSIDを除外
            }
        }
        CommonBiz cmnBiz = new CommonBiz();
        List<Integer> cantUseSidList = cmnBiz.getCantUsePluginUser(con, pluginId__, iUserSidList);
        List<String> cantUseUserList = new ArrayList<String>();
        for (Integer userSid : cantUseSidList) {
            cantUseUserList.add(String.valueOf(userSid));
        }

        //ユーザSIDの順序を維持してプラグイン使用禁止ユーザを除外
        @SuppressWarnings("unchecked")
        List<String> canUseUserList = new ArrayList<String>(
                CollectionUtils.subtract(
                    userSidList, cantUseUserList));
        return canUseUserList;
    }

    /**
     *
     * <br>[機  能] 選択の追加変更の実行
     * <br>[解  説] コマンドから処理を切り替える
     * <br>[備  考]
     * @param cmd コマンド文字列
     * @param paramName ユーザ選択フォームのパラメータ名
     * @throws Exception 実行時例外
     */
    public void executeCmd(String cmd, String paramName) throws Exception {
        if (cmd == null || paramName == null || !cmd.startsWith(paramName)) {
            return;
        }
        //コマンド（paramName.アクション(参照ID) からparamName.を除去）
        String action = cmd.substring(paramName.length() + 1);
        if (action.startsWith(CMDACTION_ADD_SELECT)) {
            String key = action.substring(CMDACTION_ADD_SELECT.length() + 1,
                    action.indexOf(")"));
            __addSelect(key);

        } else if (action.startsWith(CMDACTION_REM_SELECT)) {
            String key = action.substring(CMDACTION_REM_SELECT.length() + 1,
                    action.indexOf(")"));
            __remSelect(key);
        }
    }
    /**
     *
     * <br>[機  能] 選択リストから選択済みを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param key 参照ID
     * @throws Exception 実行時例外
     */
    private void __addSelect(String key) throws Exception {
        UserGroupSelectBiz biz = new UserGroupSelectBiz();
        String[] newSelected = biz.getListToAdd(getSelected(key), targetNoSelected__);
        setSelected(key, newSelected);
    }
    /**
     *
     * <br>[機  能] 選択リストから選択済みを設定
     * <br>[解  説]
     * <br>[備  考]
     * @param key 参照ID
     * @throws Exception 実行時例外
     */
    private void __remSelect(String key) throws Exception {
        UserGroupSelectBiz biz = new UserGroupSelectBiz();
        String[] newSelected = biz.getListToRemove(getSelected(key), targetSelected__);
        setSelected(key, newSelected);
    }
    /**
    *
    * <br>[機  能] 共通メッセージフォームのパラメータ設定 管理者設定アドレス管理画面用のみ
    * <br>[解  説]
    * <br>[備  考]
    * @param msgForm 共通メッセージフォーム
    * @param paramName フォーム要素へのパラメータ指定
    */
   public void setHiddenParam(
           Cmn999Form msgForm, String paramName) {
       //グループコンボ
       group__.setHiddenParam(msgForm, paramName + ".group");
       //選択済みID
       for (String key : selectedList__.keySet()) {
           String[] selected = selected__.get(key);
           if (selected != null && selected.length > 0) {
               msgForm.addHiddenParam(paramName + ".selected(" + key + ")", selected);
           }
       }
   }
   /**
    *
    * <br>[機  能] 使用する選択済みリストを追加
    * <br>[解  説]
    * <br>[備  考]
    * @param key 選択済みリストキー
    * @param dspName 選択済みリスト表示ラベル
    */
   public void addKey(String key, String dspName) {
       String[] oldKeys = getKeys();
       if (oldKeys == null) {
           oldKeys = new String[0];
       }
       ArrayList<String> newKeyList = new ArrayList<>(Arrays.asList(oldKeys));
       if (!newKeyList.contains(key)) {
           newKeyList.add(key);
           setKeys(newKeyList.toArray(new String[newKeyList.size()]));
           //選択済みリストの使用フラグを立てる
           selectedListName__.put(key, dspName);
           if (!selectedListUse__.containsKey(key)) {
               selectedListUse__.put(key, String.valueOf(true));
           }
           String[] selected = getSelected(key);
           if (selected != null) {
               HashSet<String> selectedSet = new HashSet<String>(Arrays.asList(selected));
               ArrayList<UsrLabelValueBean> newNosel = new ArrayList<UsrLabelValueBean>();
               //未選択リストから除外する
               for (UsrLabelValueBean nosel : noselectList__) {
                   if (!selectedSet.contains(nosel.getValue())) {
                       newNosel.add(nosel);
                   }
               }
               setNoselectList(newNosel);
           }
       }
    }
    @Override
    public void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn) {
        JSONObject jsonObj = null;
        if (json instanceof JSONObject) {
            jsonObj = (JSONObject) json;
        }
        if (jsonObj == null) {
            return;
        }
        try {
            group__.mergeJson(jsonObj.getJSONObject("group"), mergeKbn);
        } catch (JSONException e) {

        }
        try {
            List<String> value = new ArrayList<>();
            for (Object obj : jsonObj.getJSONArray("selected")) {
                value.add(obj.toString());
            }
            setSelected("target", value.toArray(new String[value.size()]));
        } catch (JSONException e) {

        }
        if (mergeKbn == KBN_JSON_MERGE.value) {
            return;
        }

    }

    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((getSelected() == null) ? 0 : getSelected().hashCode());
        return result;
    }

    /* (非 Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UserGroupSelectModel)) {
            return false;
        }
        UserGroupSelectModel other = (UserGroupSelectModel) obj;
        if (getSelected() == null) {
            if (other.getSelected() != null) {
                return false;
            }
        } else {
            Set<String> thisKeySet =  new HashSet<>(getSelected().keySet());
            Set<String> otherKeySet =  new HashSet<>(other.getSelected().keySet());
            if (thisKeySet.size() != otherKeySet.size()) {
                return false;
            }
            for (String key : thisKeySet) {
                if (otherKeySet.contains(key)) {
                    otherKeySet.remove(key);
                } else {
                    return false;
                }
            }
            for (String key : thisKeySet) {

                String[] thisSelected = getSelected().get(key);
                String[] otherSelected = other.getSelected().get(key);
                if (!Arrays.equals(thisSelected, otherSelected)) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public EnumFormModelKbn formKbn() {

        return null;
    }
    @Override
    public void merge(AbstractFormModel obj) {
        super.merge(obj);

        if (this == obj) {
            return;
        }
        if (obj == null) {
            return;
        }
        if (!(obj instanceof UserGroupSelectModel)) {
            return;
        }
        UserGroupSelectModel other = (UserGroupSelectModel) obj;
        Set<String> otherKeySet =  new HashSet<>(other.getSelected().keySet());
        for (String key : otherKeySet) {
            if (ArrayUtils.isEmpty(getSelected(key))) {
                setSelected(key, other.getSelected(key));
            }
        }
        Map<String, String[]> unselMbh = getRmSelectedMbh();
        Map<String, String[]> unselMbhOth = other.getRmSelectedMbh();
        if (!unselMbhOth.isEmpty()) {
            for (Entry<String, String[]> entry:unselMbhOth.entrySet()) {
                if (!unselMbh.containsKey(entry.getKey())) {
                    getRmSelectedMbh().put(entry.getKey(), entry.getValue());
                }
            }
        }
        group__.merge(other.getGroup());
        int scrollY, otherScY;
        scrollY = getScrollY();
        otherScY = other.getScrollY();
        if (scrollY == 0 && otherScY != 0) {
            setScrollY(otherScY);
        }
    }
    @Override
    public void defaultInit() {
    }

    /**
     * <p>useSeigen を取得します。
     * @return useSeigen
     */
    public int getUseSeigen() {
        return useSeigen__;
    }

    /**
     * <p>useSeigen をセットします。
     * @param useSeigen useSeigen
     */
    public void setUseSeigen(int useSeigen) {
        useSeigen__ = useSeigen;
    }
    @Override
    public void merge(String[] values) {
        return;
    }
    /**
     * <p>allList を取得します。
     * @return allList
     */
    public List<? extends LabelValueBean> getAllList() {
        return allList__;
    }

    /**
     * <p>useDialog を取得します。
     * @return useDialog
     */
    public int getUseDialog() {
        return useDialog__;
    }

    /**
     * <p>useDialog をセットします。
     * @param useDialog useDialog
     */
    public void setUseDialog(int useDialog) {
        useDialog__ = useDialog;
    }
    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException {
        GroupBiz grpBiz = new GroupBiz();
        UserGroupSelectBiz usrGrpBiz = new UserGroupSelectBiz();
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        init(con, reqMdl,
                String.valueOf(grpBiz.getDefaultGroupSid(sessionUsrSid, con)),
                usrGrpBiz.getGroupLabel(reqMdl, con, new ArrayList<UsrLabelValueBean>()),
                banUsrSid__);

    }
    /**
     *
     * <br>[機  能] 選択ユーザに指定プラグインの使用制限ユーザを含めない
     * <br>[解  説]
     * <br>[備  考]
     * @param use 制限フラグ
     * @param pluginId プラグインID
     */
    public void setUsePluginSeigen(boolean use, String pluginId) {
        usePluginSeigen__ = use;
        pluginId__ = pluginId;
    }

    /**
     * <p>selectJKbn を取得します。
     * @return selectJKbn
     * @see jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel#selectJKbn__
     */
    public int getSelectJKbn() {
        return selectJKbn__;
    }

    /**
     * <p>selectJKbn をセットします。
     * @param selectJKbn selectJKbn
     * @see jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel#selectJKbn__
     */
    public void setSelectJKbn(int selectJKbn) {
        selectJKbn__ = selectJKbn;
    }

    /**
     * <p>rmSelectedMbh を取得します。
     * @return rmSelectedMbh
     * @see jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel#rmSelectedMbh__
     */
    public HashMap<String, String[]> getRmSelectedMbh() {
        return rmSelectedMbh__;
    }

    /**
     * <p>rmSelectedMbh をセットします。
     * @param rmSelectedMbh rmSelectedMbh
     * @see jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel#rmSelectedMbh__
     */
    public void setRmSelectedMbh(HashMap<String, String[]> rmSelectedMbh) {
        rmSelectedMbh__ = rmSelectedMbh;
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<String> selectedAll = new ArrayList<>();
        List<String> selectedOkAll = new ArrayList<>();
        for (Entry<String, String[]> entry : getSelected().entrySet()) {
            String[] sel = entry.getValue();
            if (sel != null && sel.length > 0) {
                selectedAll.addAll(Arrays.asList(entry.getValue()));
            }
        }
        for (Entry<String, List<UsrLabelValueBean>> entry : selectedList__.entrySet()) {
            List<UsrLabelValueBean> sel = entry.getValue();
            if (sel != null && sel.size() > 0) {
                for (UsrLabelValueBean usr : sel) {
                    selectedOkAll.add(usr.getValue());
                }
            }
        }
        boolean empty = selectedAll.isEmpty();
        if (info.chkRequire()) {
            //未入力チェック
            if (empty) {
                msg = new ActionMessage("error.select.required.text",
                        info.outputName(gsMsg));
                errors.add(info.getPath(), msg);
            }
        }
        if (!empty) {
            if (selectedAll.size() != selectedOkAll.size()) {
                msg = new ActionMessage("search.data.notfound",
                        info.outputName(gsMsg));
                errors.add(info.getPath(), msg);
            }
        }
    }

    /**
     * <p>innorSelector を取得します。
     * @return innorSelector
     * @see jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel#innorSelector__
     */
    public InnerUserSelector getInnorSelector() {
        return innorSelector__;
    }

    /**
     * <p>paramName を取得します。
     * @return paramName
     * @see jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel#paramName__
     */
    public String getParamName() {
        return paramName__;
    }

    /**
     * <p>paramName をセットします。
     * @param paramName paramName
     * @see jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel#paramName__
     */
    public void setParamName(String paramName) {
        paramName__ = paramName;
    }

    /**
     * <p>dspInited を取得します。
     * @return dspInited
     * @see jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel#dspInited__
     */
    public boolean isDspInited() {
        return dspInited__;
    }


}
