package jp.groupsession.v2.cmn.ui.parts.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;

public interface ISelector {
    /** ユーザ選択 選択名 接頭*/
    String SELECT_NAME_HEAD = "select_";

    /**
     *
     * <br>[機  能] 連動パラメータ名の取得を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param key パラメータキー
     * @return パラメータ情報
     */
    String getOptionParameter(String key);
    /**
     *
     * <br>[機  能] 連動パラメータ名Mapの取得を行う
     * <br>[解  説]
     * <br>[備  考]
     * @return 連動パラメータ名Map
     */
    Map<String, String> getOptionParameterMap();
    /**
     * <p>label を取得します。
     * @return label
     * @see #data__.getLabel()
     */
    GsMessageReq getLabel();
    /**
     * <p>mode を取得します。
     * @return mode
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.Param#data__.getMode()
     */
    EnumModeType getMode();
    /**
     *
     * <br>[機  能] 選択先要素取得
     * <br>[解  説]
     * <br>[備  考]
     * @param key 選択参照名
     * @return 選択要素
     */
    Select getSelect(String key);
    Map<String, Select> getSelectMap();
    /**
     * @return selectGroupSid
     * @see SelectorParamForm#getSelectGroupSid()
     */
    String getSelectGroupSid();
    /**
     * @param selectGroupSid
     * @see SelectorParamForm#setSelectGroupSid(String)
     */
    void setSelectGroupSid(String selectGroupSid);
    /**
     * <p>selectGrp を取得します。
     * @return selectGrp
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.Selection#selectGrp__
     */
    IChild getSelectGrp();
    /**
     * @return selectTargetKey
     * @see SelectorParamForm#getSelectTargetKey()
     */
    String getSelectTargetKey();
    /**
     * @param selectTargetKey
     * @see SelectorParamForm#setSelectTargetKey(java.lang.String)
     */
    void setSelectTargetKey(String selectTargetKey);
    /**
     * <p>hissuFlg を取得します。
     * @return hissuFlg
     * @see Param#data__.isHissuFlg()
     */
    boolean isHissuFlg();
    /**
     * @return groupSelectionParamName
     * @see SelectorParamForm#getGroupSelectionParamName()
     */
    String getGroupSelectionParamName();
    /**
     * @param multiselectorFilter
     * @see SelectorParamForm#setMultiselectorFilter(String[])
     */
    void setMultiselectorFilter(String[] multiselectorFilter);
    /**
     * @return multiselectorFlter
     * @see SelectorParamForm#getMultiselectorFlter()
     */
    String[] getMultiselectorFilter();
    /**
     *
     * <br>[機  能] 表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramModel
     * @param reqMdl
     * @param con
     */
    void dspInit(ParameterObject paramModel, RequestModel reqMdl,
            Connection con) throws SQLException;
    /**
     *
     * <br>[機  能] 入力チェックを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramModel リクエストパラメータモデル
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param errors エラー配列
     * @param uiParamName 選択UIパラメータ名
     */
    void validate(ParameterObject paramModel, RequestModel reqMdl,
            Connection con, ActionErrors errors, String uiParamName) throws SQLException;
    /**
     * <p>userSelection を取得します。
     * @return userSelection
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.Selection#selectionList__
     */
    List<? extends IChild> getSelectionList();
    /**
     * <p>groupSelection を取得します。
     * @return groupSelection
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.Selection#groupSelectionList__
     */
    List<? extends IChild> getGroupSelectionList();
    /**
     *
     * <br>[機  能] 詳細検索の使用の有無を返す
     * <br>[解  説]
     * <br>[備  考]
     * @return true 詳細検索を使用する
     */
    boolean isUseDetailSearch();

}
