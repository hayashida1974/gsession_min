package jp.groupsession.v2.cmn.formmodel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.json.JSONString;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormInputBuilder;
import jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
/**
 *
 * <br>[機  能] ユーザ選択機能
 * <br>[解  説] 選択先は一つのみ
 * <br>[備  考]
 *
 * @author JTS
 */
public class SimpleUserSelect
extends SimpleMultiSelect
implements JSONString {

    /** ユーザ選択動作 実体*/
    private UserGroupSelectModel usergrpselect__ = new UserGroupSelectModel();


    /** フラグ 選択可能ユーザ有無 表示情報取得時に設定*/
    private boolean selectableUsrNone__ = false;

    /**
     * デフォルトコンストラクタ
     */
    public SimpleUserSelect() {
    }

    /**
     * <p>multiFlg を取得します。
     * @return multiFlg
     */
    public int getMultiFlg() {
        return usergrpselect__.getMultiFlg();
    }

    /**
     * <p>multiFlg をセットします。
     * @param multiFlg multiFlg
     */
    public void setMultiFlg(int multiFlg) {
        usergrpselect__.setMultiFlg(multiFlg);
    }

    /**
     * <p>selectable を取得します。
     * @return selectable
     */
    public List<String> getSelectable() {
        UserGroupSelectModel model = usergrpselect__;
        if (model.getSelectable() != null) {
            return new ArrayList<>(
                    Arrays.asList(model.getSelectable()));
        }
        return new ArrayList<>();

    }

    /**
     * <p>selectable をセットします。
     * @param selectable selectable
     */
    public void setSelectable(List<String> selectable) {
        UserGroupSelectModel model = usergrpselect__;
        if (!CollectionUtils.isEmpty(selectable)
             ) {
            model.setSelectable(
                selectable.toArray(new String[selectable.size()])
                );
        } else {
            model.setSelectable(new String[0]);
        }
    }

    /**
     * @return 選択済み要素の削除ユーザの表示区分
     * @see jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel#getSelectJKbn()
     */
    public int getSelectJKbn() {
        return usergrpselect__.getSelectJKbn();
    }

    /**
     * @param selectJKbn 選択ユーザの削除区分 -1:削除ユーザを含む 0:削除ユーザを含まない
     * @see jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel#setSelectJKbn(int)
     */
    public void setSelectJKbn(int selectJKbn) {
        usergrpselect__.setSelectJKbn(selectJKbn);
    }

    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {
        msgForm.addHiddenParam(paramName + ".init", 0);

        msgForm.addHiddenParam(paramName + ".selected", getSelected());
        //グループコンボ
        usergrpselect__.getGroup().setHiddenParam(msgForm, paramName + ".group");
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
        super.mergeJson(jsonObj, mergeKbn);
        if (mergeKbn.equals(KBN_JSON_MERGE.value)) {
            return;
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
     * <p>useSeigen を取得します。
     * @return useSeigen
     */
    public int getUseSeigen() {
        return usergrpselect__.getUseSeigen();
    }

    /**
     * <p>useSeigen をセットします。
     * @param useSeigen useSeigen
     */
    public void setUseSeigen(int useSeigen) {
        usergrpselect__.setUseSeigen(useSeigen);
    }

    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + getMultiFlg();
        result = prime * result
                + ((getSelectable() == null) ? 0 : getSelectable().hashCode());
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
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof SimpleUserSelect)) {
            return false;
        }
        SimpleUserSelect other = (SimpleUserSelect) obj;
        if (getMultiFlg() != other.getMultiFlg()) {
            return false;
        }
        if (getSelectable() == null) {
            if (other.getSelectable() != null) {
                return false;
            }
        } else if (!getSelectable().equals(other.getSelectable())) {
            return false;
        }
        if (getUseSeigen() != other.getUseSeigen()) {
            return false;
        }
        return true;
    }
    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.user;
    }

    /**
     * <p>usergrpselect を取得します。
     * @return usergrpselect
     */
    public UserGroupSelectModel getUsergrpselect() {
        return usergrpselect__;
    }

    /**
     * <p>usergrpselect をセットします。
     * @param usergrpselect usergrpselect
     */
    public void setUsergrpselect(UserGroupSelectModel usergrpselect) {
        usergrpselect__ = usergrpselect;
    }
    @Override
    public String[] getSelected() {
        String[] selected = usergrpselect__.getSelected(UserGroupSelectModel.KEY_DEFAULT);
        return selected;
    }
    @Override
    public void setSelected(String[] value) {
        String[] selected = value;
        if (selected == null) {
            selected = new String[0];
        }
        usergrpselect__.setSelected(UserGroupSelectModel.KEY_DEFAULT,
                selected);
    }
    @Override
    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("defaultValue", getDefaultValue());
        obj.put("multiFlg", getMultiFlg());
        obj.put("useSeigen", getUseSeigen());
        obj.put("selectable", getSelectable());
        obj.put("selected", getSelected());
        return obj.toString();
    }
    /**
     *
     * <br>[機  能]  表示設定
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param isInBlockListFlg ブロックリスト内かどうか
     * @throws SQLException SQL実行時例外
     */
    @SuppressWarnings("unchecked")
    public void dspInit(RequestModel reqMdl, Connection con, boolean isInBlockListFlg)
                throws SQLException {

        FormInputInitPrefarence initPref = _getInitPref();
        if (initPref != null) {
            int mode = initPref.getMode();
            if (mode == FormInputBuilder.INITMODE_DSP) {
                setSelectJKbn(-1);
            }
        }


        GroupBiz grpBiz = new GroupBiz();
        UserGroupSelectBiz usrGrpBiz = new UserGroupSelectBiz();
        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        //ユーザ選択機能をダイアログ表示方式で固定
        if (isInBlockListFlg || getMultiFlg() == UserGroupSelectModel.FLG_MULTI_OFF) {
            usergrpselect__.setUseDialog(UserGroupSelectModel.FLG_DIALOG_ON);
        }

        usergrpselect__.init(con, reqMdl,
                String.valueOf(grpBiz.getDefaultGroupSid(sessionUsrSid, con)),
                usrGrpBiz.getGroupLabel(reqMdl, con, new ArrayList<UsrLabelValueBean>()),
                null);
        Set<String> usrSelectableSid = new HashSet<String>();
        selectableUsrNone__ = false;
        if (usergrpselect__.getUseSeigen() == UserGroupSelectModel.FLG_SEIGEN_SELECTABLE) {
            Set<String> grpSelectableSid = new HashSet<String>();
            if (!ArrayUtils.isEmpty(usergrpselect__.getSelectable())) {
                for (String sid : usergrpselect__.getSelectable()) {
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
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            //選択可能グループ所属ユーザを直列化
            if (grpSelectableSid.size() > 0) {
                usrSelectableSid.addAll(belongDao.select(
                            grpSelectableSid.toArray(new String[grpSelectableSid.size()])));
            }

            //除外リスト生成 （指定ユーザ + システムユーザ）
            List<String> banList = new ArrayList<>();
            banList.add(String.valueOf(GSConst.SYSTEM_USER_ADMIN));
            banList.add(String.valueOf(GSConst.SYSTEM_USER_MAIL));

            //選択可能ユーザSIDから除外ユーザSIDを除外
            usrSelectableSid = new HashSet<String>(
                    CollectionUtils.subtract(
                            usrSelectableSid, banList));

            CmnUsrmDao cumDao = new CmnUsrmDao(con);
            String[] usrSidStr = cumDao.getNoDeleteUser(
                    usrSelectableSid.toArray(new String[usrSelectableSid.size()]));
            if (usrSidStr.length == 0) {
                selectableUsrNone__ = true;
            }
        }
    }

    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException {
        dspInit(reqMdl, con, false);
    }
    @Override
    public void merge(AbstractFormModel obj) {
        if (this == obj) {
            return;
        }
        if (obj == null) {
            return;
        }
        super.merge(obj);
        if (!(obj instanceof SimpleUserSelect)) {
            return;
        }
        SimpleUserSelect other = (SimpleUserSelect) obj;
        if (CollectionUtils.isEmpty(getSelectable())) {
            if (CollectionUtils.isEmpty(other.getSelectable())) {
                setSelectable(other.getSelectable());
            }
        }
        usergrpselect__.merge(other.getUsergrpselect());
    }
    @Override
    public String getCalced() {
        return "";
    }

    /**
     * @param use true 制限する
     * @param pluginId プラグインID
     * @see UserGroupSelectModel#setUsePluginSeigen(boolean, java.lang.String)
     */
    public void setUsePluginSeigen(boolean use, String pluginId) {
        usergrpselect__.setUsePluginSeigen(use, pluginId);
    }

    /**
     * @return 選択解除ID リスト ガラケーの選択解除ボタン用
     * @see jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel#getRmSelectedMbh()
     */
    public HashMap<String, String[]> getRmSelectedMbh() {
        return usergrpselect__.getRmSelectedMbh();
    }

    /**
     * @param rmSelectedMbh 選択解除ID リスト ガラケーの選択解除ボタン用
     * @see jp.groupsession.v2.cmn.formmodel.
     * UserGroupSelectModel#setRmSelectedMbh(java.util.HashMap)
     */
    public void setRmSelectedMbh(HashMap<String, String[]> rmSelectedMbh) {
        usergrpselect__.setRmSelectedMbh(rmSelectedMbh);
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        usergrpselect__.validateCheck(errors, reqMdl, info);
    }
    @Override
    public void chkUnuseableInput(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (info.chkRequire()) {
            //選択可能要素がないか
            if (selectableUsrNone__) {
                msg = new ActionMessage("error.cant.select.nouser.target",
                        info.outputName(gsMsg));
                errors.add(info.getPath(), msg);
                return;
            }
        }
    }



}
