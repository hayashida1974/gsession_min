package jp.groupsession.v2.cmn.ui.parts.select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;

public class SelectorParamForm {
    /** 必須フラグ*/
    private boolean hissuFlg__ = false;
    /** 表示名称  */
    private String labelText__;
    /** 表示名称 メッセージキー */
    private GsMessageReq label__;
    /** 選択先設定*/
    private final Map<String, Select.ParamForm> selectMap__ = new LinkedHashMap<>();

    /** グループ選択パラメータ名*/
    private String groupSelectionParamName__;
    /** グループ選択状態 SID*/
    private String selectGroupSid__;
    /** 選択設定先 選択状態*/
    private String selectTargetKey__;
    /** 追加ビジネスロジックフィルタ(入力チェック)*/
    private final List<IValidateFilter> validateFilterList__ =
            new ArrayList<>();
    /** 追加ビジネスロジックフィルタ(選択先描画)*/
    private final List<ISelectDspInitFilter> selectDspInitFilterList__ =
            new ArrayList<>();
    /** 追加ビジネスロジックフィルタ(選択元描画)*/
    private final List<IGroupSelectionDspInitFilter> selectionDspInitFilterList__ =
            new ArrayList<>();
    /** 追加 画面利用パラメータ */
    private final Map<String, String> optionParameterMap__ = new HashMap<>();
    /**
     *
     * コンストラクタ
     */
    public SelectorParamForm() {
    }

    /**
     *
     * <br>[機  能] 選択先要素設定取得
     * <br>[解  説]
     * <br>[備  考]
     * @param key 選択先キー
     * @return 選択先要素設定
     */
    public Select.ParamForm getSelect(String key) {
        if (!selectMap__.containsKey(key)) {
            selectMap__.put(key, Select.builder());
        }
        return selectMap__.get(key);
    }
    /**
     *
     * <br>[機  能] 選択先要素設定
     * <br>[解  説]
     * <br>[備  考]
     * @param select 選択先要素設定
     */
    public void appendSelect(Select.ParamForm select) {
        selectMap__.put(ISelector.SELECT_NAME_HEAD + selectMap__.size(), select);
    }

    /**
     * <p>selectMap を取得します。
     * @return selectMap
     * @see selectMap__
     */
    public Map<String, Select.ParamForm> getSelectMap() {
        return selectMap__;
    }

    /**
     * <p>hissuFlg を取得します。
     * @return hissuFlg
     * @see #hissuFlg__
     */
    public boolean isHissuFlg() {
        return hissuFlg__;
    }
    /**
     * <p>hissuFlg をセットします。
     * @param hissuFlg hissuFlg
     * @see #hissuFlg__
     */
    public void setHissuFlg(boolean hissuFlg) {
        hissuFlg__ = hissuFlg;
    }

    /**
     * <p>label を取得します。
     * @return label
     * @see #label__
     */
    public GsMessageReq getLabel() {
        return label__;
    }

    /**
     * <p>label をセットします。
     * @param label label
     * @see #label__
     */
    public void setLabel(GsMessageReq label) {
        label__ = label;
    }

    /**
     * <p>labelText を取得します。
     * @return labelText
     * @see labelText__
     */
    public String getLabelText() {
        return labelText__;
    }
    /**
     * <p>labelText をセットします。
     * @param labelText labelText
     * @see labelText__
     */
    public void setLabelText(String labelText) {
        labelText__ = labelText;
    }
    /**
     * <p>selectGroupSid を取得します。
     * @return selectGroupSid
     * @see selectGroupSid__
     */
    public String getSelectGroupSid() {
        return selectGroupSid__;
    }
    /**
     * <p>selectGroupSid をセットします。
     * @param selectGroupSid selectGroupSid
     * @see selectGroupSid__
     */
    public void setSelectGroupSid(String selectGroupSid) {
        selectGroupSid__ = selectGroupSid;
    }

    /**
     * <p>selectTargetKey を取得します。
     * @return selectTargetKey
     * @see #selectTargetKey__
     */
    public String getSelectTargetKey() {
        return selectTargetKey__;
    }
    /**
     * <p>selectTargetKey をセットします。
     * @param selectTargetKey selectTargetKey
     * @see #selectTargetKey__
     */
    public void setSelectTargetKey(String selectTargetKey) {
        selectTargetKey__ = selectTargetKey;
    }
    /**
     * <p>groupSelectionParamName を取得します。
     * @return groupSelectionParamName
     * @see #groupSelectionParamName__
     */
    public String getGroupSelectionParamName() {
        return groupSelectionParamName__;
    }
    /**
     * <p>groupSelectionParamName をセットします。
     * @param groupSelectionParamName groupSelectionParamName
     * @see #groupSelectionParamName__
     */
    public void setGroupSelectionParamName(
            String groupSelectionParamName) {
        groupSelectionParamName__ = groupSelectionParamName;
    }

    /**
     * <p>validateFilterList を取得します。
     * @return validateFilterList
     * @see #validateFilterList__
     */
    public List<IValidateFilter> getValidateFilterList() {
        return validateFilterList__;
    }
    /**
     * <p>selectDspInitFilterList を取得します。
     * @return selectDspInitFilterList
     * @see #selectDspInitFilterList__
     */
    public List<ISelectDspInitFilter> getSelectDspInitFilterList() {
        return selectDspInitFilterList__;
    }
    /**
     * <p>selectionDspInitFilterList を取得します。
     * @return selectionDspInitFilterList
     * @see #selectionDspInitFilterList__
     */
    public List<IGroupSelectionDspInitFilter> getSelectionDspInitFilterList() {
        return selectionDspInitFilterList__;
    }

    /**
     *
     * <br>[機  能] validateFilterList をセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param filter
     * @see #selectionDspInitFilterList__
     */
    public void appendValidateFilter(IValidateFilter filter) {
        validateFilterList__.add(filter);

    }

    /**
     *
     * <br>[機  能]  selectionDspInitFilterListをセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param filter
     * @see #selectionDspInitFilterList__
     */
    public void appendSelectionDspInitFilter(IGroupSelectionDspInitFilter filter) {
        selectionDspInitFilterList__.add(filter);
    }

    /**
     *
     * <br>[機  能] 連動パラメータ名の取得を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param key パラメータキー
     * @return パラメータ情報
     */
    public String getOptionParameter(String key) {
        return optionParameterMap__.get(key);
    }
    /**
     *
     * <br>[機  能] 連動パラメータ名Mapの取得を行う
     * <br>[解  説]
     * <br>[備  考]
     * @return パラメータ情報Map
     */
    public Map<String, String> getOptionParameterMap() {
        return optionParameterMap__;
    }
    /**
     *
     * <br>[機  能] 連動パラメータ名のセットを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param key パラメータキー
     * @param paramName 対象パラメータ名
     */
    public void setOptionParameter(String key, String paramName) {
        optionParameterMap__.put(key, paramName);
    }


}