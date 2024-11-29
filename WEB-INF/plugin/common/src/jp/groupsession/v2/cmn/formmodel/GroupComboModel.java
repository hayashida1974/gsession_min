package jp.groupsession.v2.cmn.formmodel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.json.JSONString;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormInputBuilder;
import jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.formmodel.ui.InnerGroupSelector;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] グループ選択コンボ モデル
 * <br>[解  説] グループ選択コンボで扱う、表示情報と画面からの入力情報を格納する
 * <br>[備  考]
 *
 * @author JTS
 */
public class GroupComboModel
extends AbstractFormModel
implements JSONString {
    /** 単体選択 複数選択フラグ*/
    private int multiFlg__ = UserGroupSelectModel.FLG_MULTI_OFF;
    /** 選択可能ユーザ　 */
    private List<String> selectable__;
    /**初期選択*/
    private List<String> defaultValue__;
    /** 選択可能ユーザ制限フラグ　 */
    private int useSeigen__ = UserGroupSelectModel.FLG_SEIGEN_OFF;
    /** 選択済みグループ 表示状態フラグ -1:すべて 0:削除グループをのぞく*/
    private int selectJKbn__ = GSConstUser.USER_JTKBN_ACTIVE;

    /** 初期化済みフラグ*/
    private boolean inited__ = false;

    /** 複数選択時 実態クラス*/
    private MultiSelectModel multiselect__ = new MultiSelectModel();

    /** 選択動作 ダイアログフラグ*/
    private int useDialog__ = UserGroupSelectModel.FLG_DIALOG_OFF;

    /** 選択動作 指定なし追加フラグ*/
    private boolean useNosel__ = false;

    /** ユーザ選択動作 実体 (複数選択時)*/
    private final InnerGroupSelector innorSelector__ =
            InnerGroupSelector.builder()
                .chainParent(this)
                .build();

    /** パラメータ文字列 ユーザグループ選択コンボ */
    private String paramName__;



    /**
     * <p>useNosel を取得します。
     * @return useNosel
     * @see jp.groupsession.v2.cmn.formmodel.GroupComboModel#useNosel__
     */
    public boolean isUseNosel() {
        return useNosel__;
    }
    /**
     * <p>useNosel をセットします。
     * @param useNosel useNosel
     * @see jp.groupsession.v2.cmn.formmodel.GroupComboModel#useNosel__
     */
    public void setUseNosel(boolean useNosel) {
        useNosel__ = useNosel;
    }
    /**
     * <p>multiselect を取得します。
     * @return multiselect
     */
    public MultiSelectModel getMultiselect() {
        return multiselect__;
    }
    /**
     *
     */
    public GroupComboModel() {
        super();
    }
    /**
     * @param json json
     */
    public GroupComboModel(JSONObject json) {
        super(json);
    }
    /**
     *
     * @param multiFlg 複数選択フラグ
     */
    public GroupComboModel(int multiFlg) {
        super();
        multiFlg__ = multiFlg;
    }
    /**
     * <p>selected を取得します。
     * @return selected
     */
    public String[] getSelected() {
        String[] selected = multiselect__.getSelected();
        return selected;
    }
    /**
     *
     * <br>[機  能] selected を取得します。
     * <br>[解  説] 指定なしの選択値を空選択として取得します
     * <br>[備  考] 保管用
     * @return selected
     */
    public String[] getSelectedNoselisVoid() {
        if (useNosel__
                && multiFlg__ == UserGroupSelectModel.FLG_MULTI_OFF
                ) {
            if ("-1".equals(getSelectedSingle())) {
                return new String[0];
            }

        }
        return getSelected();

    }
    /**
     *
     * <br>[機  能] 選択済みラベルリストを返す
     * <br>[解  説]
     * <br>[備  考]
     * @return 選択済みラベル
     */
    public List<UsrLabelValueBean> getSelectedLabel() {
        List<UsrLabelValueBean> list = new ArrayList<UsrLabelValueBean>();
        String[] selectedValue = getSelected();
        if (useNosel__
                && multiFlg__ == UserGroupSelectModel.FLG_MULTI_OFF
                ) {
            if ("-1".equals(getSelectedSingle())) {
                return list;
            }

        }

        for (UsrLabelValueBean label : getList()) {
            if (ArrayUtils.contains(selectedValue, label.getValue())) {
                list.add(label);
            }
        }
        return list;
    }
    /**
     * <p>selected先頭ひとつ を取得します。
     * @return selected
     */
    public String getSelectedSingle() {
        String topselected = null;
        String[] selected = multiselect__.getSelected();

        if (selected != null && selected.length > 0) {
            topselected = selected[0];
        }
        return topselected;
    }
    /**
     * <p>selected設定します。
     * @param selected 選択値
     */
    public void setSelectedSingle(String selected) {
        multiselect__.setSelected(new String[] {selected});
    }
    /**
     * <p>selected をセットします。
     * @param selected selected
     */
    public void setSelected(String[] selected) {
        multiselect__.setSelected(selected);
    }
    /**
     * <p>list を取得します。
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List<UsrLabelValueBean> getList() {
        return (List<UsrLabelValueBean>) multiselect__.getAllList();
    }
    /**
     * <p>list をセットします。
     * @param list list
     */
    public void setList(List<UsrLabelValueBean> list) {
        multiselect__.setDspSelectData(list);
    }
    /**
     *
     * <br>[機  能] グループコンボの初期化
     * <br>[解  説]
     * <br>[備  考]
     * @param grplist グループリスト
     * @param defGroupSid 選択要素がない場合のデフォルトSID
     */
    public void init(
            List<UsrLabelValueBean> grplist,
            String defGroupSid) {
        List<String> selectable = getSelectable();

        if (getUseSeigen() == UserGroupSelectModel.FLG_SEIGEN_SELECTABLE
                ) {
            List<UsrLabelValueBean> newNoselList = new ArrayList<UsrLabelValueBean>();
            for (UsrLabelValueBean label : grplist) {

                if (selectable != null
                        && selectable.contains(label.getValue())) {
                    newNoselList.add(label);
                    continue;
                }
                if (useNosel__
                        && multiFlg__ == UserGroupSelectModel.FLG_MULTI_OFF
                        && label.getValue().equals("-1")) {
                    newNoselList.add(label);

                }
            }

            grplist = newNoselList;
        }
        setList(grplist);
        String[] selected = multiselect__.getSelected();
        if (multiFlg__ == UserGroupSelectModel.FLG_MULTI_OFF) {
              if (selected == null || selected.length == 0) {
                  //初期値選択
                  selected = new String[] {
                        __getEnableSelectGroup(grplist, "-1", defGroupSid)
                    };
                  setSelected(selected);
              }
        }
        inited__ = true;
    }
    /**
    *
    * <br>[機  能] 選択した値がグループコンボ上にない場合に有効な値を返す
    * <br>[解  説]
    * <br>[備  考]
    * @param list ラベルリスト
    * @param nowSel 選択中ラベルID
    * @param defSel 初期ラベルID
    * @return 有効な選択値
    */
   private String __getEnableSelectGroup(List<UsrLabelValueBean> list,
         String nowSel,
         String defSel) {
       boolean nowVar = false;
       boolean defVar = false;
       if (list == null || list.size() <= 0) {
           return "";
       }
       for (LabelValueBean label : list) {
           if (label.getValue().equals(nowSel)) {
               nowVar = true;
               break;
           }
           if (label.getValue().equals(defSel)) {
               defVar = true;
           }
       }
       if (nowVar) {
           return nowSel;
       }
       if (defVar) {
           return defSel;
       }
       return list.get(0).getValue();
   }

   /**
    * <p>selectable を取得します。
    * @return selectable
    */
   public List<String> getSelectable() {
       return selectable__;
   }

   /**
    * <p>selectable をセットします。
    * @param selectable selectable
    */
   public void setSelectable(List<String> selectable) {
       selectable__ = selectable;
   }
   /**
    *
    * <br>[機  能] ダイアログ版の入力制限受け渡し用
    * <br>[解  説]
    * <br>[備  考]
    * @param selectable 選択可能GSID
    */
   public void setSelectableGSID(String[] selectable) {
       if (selectable == null) {
           selectable__ = new ArrayList<String>();
       } else {
           selectable__ = new ArrayList<String>(
                   Arrays.asList(selectable)
                   );
       }
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
   *
   * <br>[機  能] 共通メッセージフォームのパラメータ設定 管理者設定アドレス管理画面用のみ
   * <br>[解  説]
   * <br>[備  考]
   * @param msgForm 共通メッセージフォーム
   * @param paramName フォーム要素へのパラメータ指定
   */
    public void setHiddenParam(
          Cmn999Form msgForm, String paramName) {
        msgForm.addHiddenParam(paramName + ".init", 0);
        //選択済みSID
        msgForm.addHiddenParam(paramName + ".selected", this.getSelected());
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
            List<String> value = new ArrayList<>();
            for (Object valueJSON : jsonObj.getJSONArray("selected")) {
                value.add(valueJSON.toString());
            }
            setSelected(value.toArray(new String[value.size()]));
        } catch (JSONException e) {

        }
        if (mergeKbn.equals(KBN_JSON_MERGE.value)) {
            return;
        }
        try {
            List<String> value = new ArrayList<>();
            for (Object valueJSON : jsonObj.getJSONArray("defaultValue")) {
                value.add(valueJSON.toString());
            }
            setDefaultValue(value);
        } catch (JSONException e) {

        }

        try {
            List<String> value = new ArrayList<>();
            for (Object valueJSON : jsonObj.getJSONArray("selectable")) {
                value.add(valueJSON.toString());
            }
            setSelectable(value);
        } catch (JSONException e) {

        }
        try {
            setMultiFlg(jsonObj.getInt("multiFlg"));
        } catch (JSONException e) {

        }
        try {
            setUseSeigen(jsonObj.getInt("useSeigen"));
        } catch (JSONException e) {

        }

    }
    /**
     * <p>defaultValue を取得します。
     * @return defaultValue
     */
    public List<String> getDefaultValue() {
        return defaultValue__;
    }
    /**
     * <p>defaultValue をセットします。
     * @param defaultValue defaultValue
     */
    public void setDefaultValue(List<String> defaultValue) {
        defaultValue__ = defaultValue;
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
    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        List<String> defaultValue = getDefaultValue();
        List<String> selectable = getSelectable();
        String[] selected = getSelected();
        result = prime * result
                + ((defaultValue == null) ? 0 : defaultValue.hashCode());
        result = prime * result + getMultiFlg();
        result = prime * result
                + ((selectable == null) ? 0 : selectable.hashCode());
        result = prime * result + Arrays.hashCode(selected);
        result = prime * result + getUseSeigen();
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
        if (!(obj instanceof GroupComboModel)) {
            return false;
        }
        GroupComboModel other = (GroupComboModel) obj;
        if (!Objects.equals(getDefaultValue(), other.getDefaultValue())) {

            return false;
        }
        if (getMultiFlg() != other.getMultiFlg()) {
            return false;
        }
        if (!Objects.equals(getSelectable(), other.getSelectable())) {
            return false;
        }
        if (!Arrays.equals(getSelected(), other.getSelected())) {
            return false;
        }
        if (getUseSeigen() != other.getUseSeigen()) {
            return false;
        }
        return true;
    }
    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.group;
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
        if (!(obj instanceof GroupComboModel)) {
            return;
        }
        GroupComboModel other = (GroupComboModel) obj;
        if (CollectionUtils.isEmpty(getSelectable())) {
            setSelectable(other.getSelectable());
        }
        if (CollectionUtils.isEmpty(getDefaultValue())) {
            setDefaultValue(other.getDefaultValue());
        }
        if (ArrayUtils.isEmpty(getSelected())) {
            setSelected(other.getSelected());
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

        int scrollY, otherScY;
        scrollY = getScrollY();
        otherScY = other.getScrollY();
        if (scrollY == 0 && otherScY != 0) {
            setScrollY(otherScY);
        }
    }
    @Override
    public void defaultInit() {
        List<String> def = getDefaultValue();
        if (!CollectionUtils.isEmpty(def)) {
            setSelected(def.toArray(new String[def.size()]));
        }
    }
    @Override
    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("defaultValue", getDefaultValue());
        obj.put("multiFlg", getMultiFlg());
        obj.put("useSeigen", getUseSeigen());
        obj.put("selectable", getSelectable());
        return obj.toString();
    }
    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException, IOToolsException {
        dspInit(reqMdl, con, false);
    }
    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param isInBlockList ブロックリスト内かどうか
     * @throws SQLException sql実行時例外
     * @throws IOToolsException ディレクトリ作成時例外
     */
    public void dspInit(RequestModel reqMdl, Connection con,
            boolean isInBlockList) throws SQLException, IOToolsException {
        super.dspInit(reqMdl, con);
        FormInputInitPrefarence initPref = _getInitPref();
        if (initPref != null) {
            int mode = initPref.getMode();
            if (mode == FormInputBuilder.INITMODE_DSP) {
                setSelectJKbn(-1);
            }
        }


        if (!inited__) {
            //選択機能をダイアログ表示方式で固定
            if (isInBlockList) {
                setUseDialog(UserGroupSelectModel.FLG_DIALOG_ON);
            }
            GsMessage gsMsg = new GsMessage(reqMdl);
            GroupBiz gBiz = new GroupBiz();
            int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
            int defGroupSel = gBiz.getDefaultGroupSid(sessionUsrSid, con);
            UserGroupSelectBiz ugsBiz = new UserGroupSelectBiz();
            ArrayList<UsrLabelValueBean> list
              = ugsBiz.getGroupLabel(reqMdl, con,
                      new ArrayList<UsrLabelValueBean>());

            //単体選択かつ未選択使用時、未選択をリストに追加
            if (useNosel__ && multiFlg__ == UserGroupSelectModel.FLG_MULTI_OFF) {
                list.add(0, new UsrLabelValueBean(
                        gsMsg.getMessage("cmn.specified.no"), "-1"
                        ));

            }

            //選択済みの削除済みグループを含める場合
            if (selectJKbn__ <= -1) {
                List<UsrLabelValueBean> selectedDelGrpList =
                        __createSelectedDelGrpList(list, con);
                list.addAll(selectedDelGrpList);
            }
            init(list, String.valueOf(defGroupSel));
        }

    }
    /**
     *
     * <br>[機  能] 選択済みSIDのうちから削除済みグループ一覧を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param grplist 選択済みグループリスト
     * @param con コネクション
     * @return 削除済みグループ
     * @throws SQLException SQL実行時例外
     */
    private List<UsrLabelValueBean> __createSelectedDelGrpList(List<UsrLabelValueBean> grplist,
            Connection con) throws SQLException {
        List<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();
        List<Integer> selectedDelGrpSidList = new ArrayList<Integer>();
        List<String>  liveGrpSidList =  new ArrayList<String>();
        String[] selected = multiselect__.getSelected();
        for (UsrLabelValueBean label : grplist) {
            liveGrpSidList.add(label.getValue());
        }
        for (String sid : selected) {
            if (!liveGrpSidList.contains(sid)) {
                selectedDelGrpSidList.add(NullDefault.getInt(sid, -1));
            }
        }
        int[] gsids = ArrayUtils.toPrimitive(
                selectedDelGrpSidList.toArray(new Integer[selectedDelGrpSidList.size()]));
        if (gsids.length == 0) {
            return ret;
        }
        CmnGroupmDao dao = new CmnGroupmDao(con);
        List<CmnGroupmModel> cgMdl = dao.selectFromSid(gsids, CmnGroupmDao.GRP_JKBN_DELETED);
        for (CmnGroupmModel grpMdl : cgMdl) {
            UsrLabelValueBean grp = new UsrLabelValueBean(grpMdl.getGrpName(),
                    String.valueOf(grpMdl.getGrpSid()));
            grp.setJkbn(CmnGroupmDao.GRP_JKBN_DELETED);
            ret.add(grp);
        }
        return ret;
    }
    @Override
    public void merge(String[] values) {
        if (ArrayUtils.isEmpty(values)) {
            return;
        }
        setSelected(values);
    }
    @Override
    public int getScrollY() {
        return multiselect__.getScrollY();
    }
    @Override
    public void setScrollY(int scrollY) {
        multiselect__.setScrollY(scrollY);
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        boolean nosel = ArrayUtils.isEmpty(getSelected());
        if (info.chkRequire()) {
            //未入力チェック
            if (nosel) {
                msg = new ActionMessage("error.select.required.text",
                        info.outputName(gsMsg));
                errors.add(info.getPath(), msg);
                return;
            }
        }
        //不正選択チェック
        if (!nosel) {
            String[] sids = getSelected();
            List<? extends LabelValueBean> list = getList();
            Map<String, String> labelMap = new HashMap<>();
            if (list != null && list.size() > 0) {
                for (LabelValueBean label : list) {
                    labelMap.put(label.getValue(), label.getValue());
                }
            }
            if (sids != null && sids.length > 0) {
                for (String sel : sids) {
                    if (!labelMap.containsKey(sel)) {
                        msg = new ActionMessage("search.data.notfound",
                                info.outputName(gsMsg));
                        errors.add(info.getPath(), msg);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void chkUnuseableInput(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (info.chkRequire()) {
            //選択可能要素がない
            List<? extends LabelValueBean> list = getList();
            if (list.size() == 0) {
                msg = new ActionMessage("error.cant.select.nogroup.target",
                        info.outputName(gsMsg));
                errors.add(info.getPath(), msg);
                return;
            }
        }
    }
    @Override
    public String getCalced() {
        return "";
    }
    /**
     * <p>useDialog を取得します。
     * @return useDialog
     * @see jp.groupsession.v2.cmn.formmodel.GroupComboModel#useDialog__
     */
    public int getUseDialog() {
        return useDialog__;
    }
    /**
     * <p>useDialog をセットします。
     * @param useDialog useDialog
     * @see jp.groupsession.v2.cmn.formmodel.GroupComboModel#useDialog__
     */
    public void setUseDialog(int useDialog) {
        useDialog__ = useDialog;
    }
    /**
     * <p>selectJKbn を取得します。
     * @return selectJKbn
     * @see jp.groupsession.v2.cmn.formmodel.GroupComboModel#selectJKbn__
     */
    public int getSelectJKbn() {
        return selectJKbn__;
    }
    /**
     * <p>selectJKbn をセットします。
     * @param selectJKbn selectJKbn
     * @see jp.groupsession.v2.cmn.formmodel.GroupComboModel#selectJKbn__
     */
    public void setSelectJKbn(int selectJKbn) {
        selectJKbn__ = selectJKbn;
    }
    /**
     * <p>unSelectedMbh を取得します。
     * @return unSelectedMbh
     * @see jp.groupsession.v2.cmn.formmodel.MultiSelectModel#getRmSelectedMbh()
     */
    public HashMap<String, String[]> getRmSelectedMbh() {
        return multiselect__.getRmSelectedMbh();
    }
    /**
     * <p>unSelectedMbh をセットします。
     * @param unSelectedMbh unSelectedMbh
     * @see jp.groupsession.v2.cmn.formmodel.MultiSelectModel#setRmSelectedMbh(java.util.HashMap)
     */
    public void setRmSelectedMbh(HashMap<String, String[]> unSelectedMbh) {
        multiselect__.setRmSelectedMbh(unSelectedMbh);
    }
    /**
     * <p>innorSelector を取得します。
     * @return innorSelector
     * @see jp.groupsession.v2.cmn.formmodel.GroupComboModel#innorSelector__
     */
    public InnerGroupSelector getInnorSelector() {
        return innorSelector__;
    }
    /**
     * <p>paramName を取得します。
     * @return paramName
     * @see jp.groupsession.v2.cmn.formmodel.GroupComboModel#paramName__
     */
    public String getParamName() {
        return paramName__;
    }
    /**
     * <p>paramName をセットします。
     * @param paramName paramName
     * @see jp.groupsession.v2.cmn.formmodel.GroupComboModel#paramName__
     */
    public void setParamName(String paramName) {
        paramName__ = paramName;
    }

}
