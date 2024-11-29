package jp.groupsession.v2.cmn.formbuilder;

import jp.co.sjts.util.struts.AbstractForm;
import jp.groupsession.v2.cmn.formmodel.AbstractFormModel;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.formmodel.MultiSelectModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
public class FormCellDialogForm extends AbstractForm
implements IFormCellPrefarenceParamModel {

    /** json文字列*/
    private String json__;
    /** json展開後のFormCell要素*/
    private FormCell cell__;
    /** タイトル必須フラグ*/
    private int titleRequireFlg__ = 1;
    /** 描画更新先jquery参照*/
    private String parentJquery__;

    /** プラグインId*/
    private String pluginId__;

    /** 添付ファイルディレクトリID*/
    private String directoryId__;
    /** 設定対象要素区分文字列*/
    private String selectorTypeText__ = "";

    /** ユーザ選択設定用 */
    UserGroupSelectModel selectParamUser__ = new UserGroupSelectModel();
    /** ユーザ選択設定用 選択UI */
    private UserGroupSelector selectParamUserSelector__ =
            UserGroupSelector.builder()
                .chainGroupSelectionParamName(
                        "select.group.selectedSingle"
                                )
                .chainType(EnumSelectType.USER)
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
                .chainSelect(Select.builder()
                        .chainParameterName(
                                "select.selected("
                                + "target"
                                + ")"
                                )
                        )
                .build();

    /** グループ選択選択設定用 */
    GroupComboModel selectParamGroup__ = new GroupComboModel();
    /** グループ選択設定用 選択UI */
    private UserGroupSelector selectParamGroupSelector__ =
            UserGroupSelector.builder()
                .chainType(EnumSelectType.GROUP)
                .chainSelect(Select.builder()
                        .chainParameterName(
                                "select.selected"
                                )
                        )
                .build();



    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.formbuilder.IFormCellPrefarenceParamModel#getPluginId()
     */
    @Override
    public String getPluginId() {
        return pluginId__;
    }
    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#pluginId__
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.formbuilder.IFormCellPrefarenceParamModel#getDirectoryId()
     */
    @Override
    public String getDirectoryId() {
        return directoryId__;
    }
    /**
     * <p>directoryId をセットします。
     * @param directoryId directoryId
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#directoryId__
     */
    public void setDirectoryId(String directoryId) {
        directoryId__ = directoryId;
    }

    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.formbuilder.IFormCellPrefarenceParamModel#getJson()
     */
    @Override
    public String getJson() {
        return json__;
    }
    /**
     * <p>json をセットします。
     * @param json json
     */
    public void setJson(String json) {
        json__ = json;
    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.formbuilder.IFormCellPrefarenceParamModel#getCell()
     */
    @Override
    public FormCell getCell() {
        return cell__;
    }
    /**
     * <p>cell をセットします。
     * @param cell cell
     */
    public void setCell(FormCell cell) {
        cell__ = cell;
    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.formbuilder.IFormCellPrefarenceParamModel#getParentJquery()
     */
    @Override
    public String getParentJquery() {
        return parentJquery__;
    }
    /**
     * <p>parentJquery をセットします。
     * @param parentJquery parentJquery
     */
    public void setParentJquery(String parentJquery) {
        parentJquery__ = parentJquery;
    }
    /* (非 Javadoc)
     * @see jp.groupsession.v2.cmn.formbuilder.IFormCellPrefarenceParamModel#getTitleRequireFlg()
     */
    @Override
    public int getTitleRequireFlg() {
        return titleRequireFlg__;
    }
    /**
     * <p>titleRequireFlg をセットします。
     * @param titleRequireFlg titleRequireFlg
     */
    public void setTitleRequireFlg(int titleRequireFlg) {
        titleRequireFlg__ = titleRequireFlg;
    }
    /**
     * <p>selectorTypeText を取得します。
     * @return selectorTypeText
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#selectorTypeText__
     */
    public String getSelectorTypeText() {
        return selectorTypeText__;
    }
    /**
     * <p>selectorTypeText をセットします。
     * @param selectorTypeText selectorTypeText
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#selectorTypeText__
     */
    public void setSelectorTypeText(String selectorTypeText) {

        selectorTypeText__ = selectorTypeText;
    }
    @Override
    public EnumFormModelKbn getSelectorType() {
        EnumFormModelKbn kbn = null;
        if (cell__ == null) {
            try {
                kbn = EnumFormModelKbn.valueOf(selectorTypeText__);
            } catch (IllegalArgumentException e) {
            }
        } else {
            kbn = cell__.getType();
        }

        return kbn;
    }

    @Override
    public AbstractFormModel getSelect() {
        EnumFormModelKbn kbn = getSelectorType();

        if (kbn == EnumFormModelKbn.user) {
            return selectParamUser__;
        }
        if (kbn == EnumFormModelKbn.group) {
            return selectParamGroup__;
        }
        return null;
    }

    /**
     * <p>selectParamUser をセットします。
     * @param selectParamUser selectParamUser
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#selectParamUser__
     */
    public void setSelectParamUser(UserGroupSelectModel selectParamUser) {
        selectParamUser__.setSelected("target", selectParamUser.getSelected("target"));

    }
    /**
     * <p>selectParamGroup をセットします。
     * @param multiSelectModel selectParamGroup
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#selectParamGroup__
     */
    public void setSelectParamGroup(MultiSelectModel multiSelectModel) {
        selectParamGroup__.setSelected(multiSelectModel.getSelected());
    }


    /**
     * <p>selectParamUserSelector を取得します。
     * @return selectParamUserSelector
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#selectParamUserSelector__
     */
    public UserGroupSelector getSelectParamUserSelector() {
        return selectParamUserSelector__;
    }


    /**
     * <p>selectParamUserSelector をセットします。
     * @param selectParamUserSelector selectParamUserSelector
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#selectParamUserSelector__
     */
    public void setSelectParamUserSelector(UserGroupSelector selectParamUserSelector) {
        selectParamUserSelector__ = selectParamUserSelector;
    }


    /**
     * <p>selectParamGroupSelector を取得します。
     * @return selectParamGroupSelector
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#selectParamGroupSelector__
     */
    public UserGroupSelector getSelectParamGroupSelector() {
        return selectParamGroupSelector__;
    }


    /**
     * <p>selectParamGroupSelector をセットします。
     * @param selectParamGroupSelector selectParamGroupSelector
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#selectParamGroupSelector__
     */
    public void setSelectParamGroupSelector(UserGroupSelector selectParamGroupSelector) {
        selectParamGroupSelector__ = selectParamGroupSelector;
    }


}
