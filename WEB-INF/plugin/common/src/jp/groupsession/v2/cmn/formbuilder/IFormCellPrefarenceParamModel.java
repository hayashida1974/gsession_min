package jp.groupsession.v2.cmn.formbuilder;

import jp.groupsession.v2.cmn.formmodel.AbstractFormModel;

public interface IFormCellPrefarenceParamModel {

    /**
     * <p>pluginId を取得します。
     * @return pluginId
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#pluginId__
     */
    String getPluginId();

    /**
     * <p>directoryId を取得します。
     * @return directoryId
     * @see jp.groupsession.v2.cmn.formbuilder.FormCellDialogForm#directoryId__
     */
    String getDirectoryId();

    /**
     * <p>json を取得します。
     * @return json
     */
    String getJson();

    /**
     * <p>cell を取得します。
     * @return cell
     */
    FormCell getCell();

    /**
     * <p>parentJquery を取得します。
     * @return parentJquery
     */
    String getParentJquery();

    /**
     * <p>titleRequireFlg を取得します。
     * @return titleRequireFlg
     */
    int getTitleRequireFlg();
    /**
     * <p>select を取得します。
     * @return select
     */
    AbstractFormModel getSelect();
    /**
     * <p>selectorType を取得します。
     * @return selectorType
     */
    EnumFormModelKbn getSelectorType();

}