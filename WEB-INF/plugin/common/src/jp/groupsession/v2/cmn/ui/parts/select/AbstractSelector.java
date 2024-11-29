package jp.groupsession.v2.cmn.ui.parts.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.exception.UIValidateException;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 選択UIクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractSelector
implements ISelector, IValidateFilter, IGroupSelectionDspInitFilter {

    /** 表示モード*/
    private EnumModeType mode__ = EnumModeType.EDIT_INLINE;
    /** 設定パラメータ*/
    private final SelectorParamForm param__;
    /** 選択先設定*/
    private Map<String, Select> select__ = new LinkedHashMap<>();
    /** 詳細検索用フィルター*/
    private String[] multiselectorFilter__ = null;

    /*
     * 以下は dspInitで初期化
     */
    /** 選択グループラベル */
    private IChild selectGrp__;
    /** グループラベルリスト*/
    private final List<IChild> groupSelectionList__ = new ArrayList<>();
    /** ユーザ選択*/
    private final List<IChild> selectionList__ = new ArrayList<>();

    protected AbstractSelector(SelectorParamForm param) {
        param__ = param;
        param__.getSelectMap().entrySet()
        .stream()
        .forEach(entry -> {
            select__.put(
                    entry.getKey(),
                    entry.getValue().build()
                    );
        });
        //Map要素を非可変に変更
        select__ = Collections.unmodifiableMap(select__);

    }

    @Override
    public String getOptionParameter(String key) {
        return param__.getOptionParameter(key);
    }

    @Override
    public Map<String, String> getOptionParameterMap() {
        return param__.getOptionParameterMap();
    }

    @Override
    public GsMessageReq getLabel() {
        return param__.getLabel();
    }

    @Override
    public Select getSelect(String key) {
        return select__.get(key);
    }

    @Override
    public Map<String, Select> getSelectMap() {
        return select__;
    }

    @Override
    public String getSelectGroupSid() {
        return param__.getSelectGroupSid();
    }

    @Override
    public void setSelectGroupSid(String selectGroupSid) {
        param__.setSelectGroupSid(selectGroupSid);
    }

    @Override
    public IChild getSelectGrp() {
        return selectGrp__;
    }

    @Override
    public String getSelectTargetKey() {
        return param__.getSelectTargetKey();
    }

    @Override
    public void setSelectTargetKey(String selectTargetKey) {
        param__.setSelectTargetKey(selectTargetKey);
    }

    @Override
    public boolean isHissuFlg() {
        return param__.isHissuFlg();
    }

    @Override
    public String getGroupSelectionParamName() {
        return param__.getGroupSelectionParamName();
    }

    @Override
    public List<? extends IChild> getSelectionList() {
        return selectionList__;
    }


    @Override
    public List<? extends IChild> getGroupSelectionList() {
        return groupSelectionList__;
    }

    /**
     * <p>mode を取得します。
     * @return mode
     * @see #mode__
     */
    public EnumModeType getMode() {
        return mode__;
    }
    /**
     * <p>mode をセットします。
     * @param mode mode
     * @see #mode__
     */
    public void setMode(EnumModeType mode) {
        mode__ = mode;
    }
    /**
     * <p>mode をセットします。
     * @param mode mode
     * @see #mode__
     */
    public void setModeText(String mode) {
        setMode(EnumModeType.valueOf(mode));
    }

    /**
     * <p>multiselectorFilter を取得します。
     * @return multiselectorFilter
     * @see jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector#multiselectorFilter
     */
    public String[] getMultiselectorFilter() {
        return multiselectorFilter__;
    }

    /**
     * <p>multiselectorFilter をセットします。
     * @param multiselectorFilter multiselectorFilter
     * @see jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector#multiselectorFilter
     */
    public void setMultiselectorFilter(String[] multiselectorFilter) {
        this.multiselectorFilter__ = multiselectorFilter;
    }


    @Override
    public final void dspInit(ParameterObject paramModel, RequestModel reqMdl,
            Connection con) throws SQLException {


        String[] selecs = paramModel.get("multiselectorFilter", String[].class);
        if (selecs != null) {
            for (String s : selecs) {
                System.out.println("s : " + s);
            }
        }

        List<IChild> selectedSum = new ArrayList<>();
        //選択済み要素表示
        for (Select select : getSelectMap().values()) {
            List<IChild> selected = new ArrayList<>();
            String[] selectedSids = paramModel.get(select.getParameterName(), String[].class);
            selected.addAll(answerSelectedList(reqMdl, con, paramModel,
                    Arrays.asList(selectedSids)));
            select.dspInit(paramModel, reqMdl, con, selected);
            selectedSum.addAll(selected);
        }
        //選択先設定
        if (getSelectTargetKey() == null) {
            setSelectTargetKey(
                    getSelectMap().keySet().stream().findFirst().orElse(SELECT_NAME_HEAD + 0));
        }
        //選択対象要素表示
        GroupSelectionDspInitFilterChain chain = new GroupSelectionDspInitFilterChain(
                Stream.concat(Stream.of((IGroupSelectionDspInitFilter) this),
                        param__.getSelectionDspInitFilterList().stream())
                .collect(Collectors.toList())
                );
        GroupSelectionDspInitModel initModel = new GroupSelectionDspInitModel();
        initModel.setSelectedList(selectedSum);

        chain.doFilterChain(reqMdl,
                con,
                paramModel,
                this,
                initModel);

        groupSelectionList__.addAll(chain.getSelectionGroupList().stream()
                .collect(Collectors.toList())
                );

        selectGrp__ = chain.getSelectGrp();

        selectionList__.addAll(chain.getSelectionList().stream()
        .collect(Collectors.toList())
            );


    }

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
     * @throws SQLException
     */
    @Override
    public final void validate(ParameterObject paramModel,
            RequestModel reqMdl,
            Connection con,
            ActionErrors errors,
            String uiParamName
            ) throws SQLException  {
        UIValidateException error = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        for (Select select : getSelectMap().values()) {
            ValidateFilterChain chain = new ValidateFilterChain(
                    Stream.concat(Stream.of(),
                            param__.getValidateFilterList().stream())
                    .collect(Collectors.toList())
                    );
            chain.doFilterChain(reqMdl,
                    con,
                    paramModel,
                    this,
                    select);
            if (chain.getException() != null) {
                UIValidateException.union(error, chain.getException());
            }
        }

        //複数選択間の重複チェック
        Set<String> selectSidSet = new HashSet<>();
        for (Select select : getSelectMap().values()) {


            String[] sids = paramModel.get(select.getParameterName(), String[].class);

            int chkCnt = selectSidSet.size()
                    + sids.length;
            selectSidSet.addAll(
                    Arrays.stream(
                            sids)
                    .collect(Collectors.toList())
                    );
            if (selectSidSet.size() < chkCnt) {
                //SIDの指定が重複
                error = UIValidateException.union(error,
                        new UIValidateException("error.select.dup.list",
                                new ActionMessage("error.select.dup.list",
                                        getLabel().getMessage(gsMsg))
                                ));

            }
        }

        if (error != null) {
            error.addError(errors, uiParamName);
        }

    }
    /**
     *
     * <br>[機  能] 選択先リストを返す
     * <br>[解  説] 選択先リスト設定リスナメソッド
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @param paramModel
     * @param selectedSidList
     * @return 選択先リスト
     * @throws SQLException
     */
    public abstract List<IChild> answerSelectedList(
            RequestModel reqMdl,
            Connection con,
            ParameterObject paramModel,
            List<String> selectedSidList) throws SQLException;
    /**
     *
     * <br>[機  能] 選択元リストを返す
     * <br>[解  説] 選択元リスト設定リスナメソッド
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @param paramModel
     * @param selectedGrpSid
     * @param selectedSidList
     * @return 選択元リスト
     * @throws SQLException
     */
    public abstract List<IChild> answerSelectionList(
            RequestModel reqMdl,
            Connection con, ParameterObject paramModel,
            String selectedGrpSid,
            List<String> selectedSidList) throws SQLException;
    /**
     *
     * <br>[機  能] 選択元グループリストを返す
     * <br>[解  説] 選択元リスト設定リスナメソッド
     * <br>[備  考] 空の場合はグループ無し
     * @param reqMdl
     * @param con
     * @param paramModel
     * @return 選択元グループリスト
     * @throws SQLException
     */
    public List<IChild> answerGroupList(
            RequestModel reqMdl,
            Connection con,
            ParameterObject paramModel) throws SQLException {
        return List.of();
    }
    /**
     *
     * <br>[機  能] デフォルト選択グループを返す
     * <br>[解  説] 選択元リスト設定リスナメソッド
     * <br>[備  考] 空の場合はグループ無し
     * @param reqMdl
     * @param con
     * @param paramModel
     * @param groupSidList
     * @return デフォルト選択グループ
     * @throws SQLException
     */
    public String answerDefaultGroup(RequestModel reqMdl,
            Connection con, ParameterObject paramModel,
            List<String> groupSidList) throws SQLException {
        if (groupSidList == null || groupSidList.size() == 0) {
            return null;
        }
        return groupSidList.get(0);
    }

    @Override
    public void doFilter(GroupSelectionDspInitFilterChain chain,
            RequestModel reqMdl, Connection con, ParameterObject paramModel,
            ISelector selector, GroupSelectionDspInitModel initModel) throws SQLException {
        String selectedGrpSid = paramModel.get(selector.getGroupSelectionParamName(), String.class);

        initModel.setSelectionGroupList(answerGroupList(reqMdl, con, paramModel));
        if (initModel.getSelectionGroupList().isEmpty() == false) {
            //指定グループが見つからなければデフォルトグループを設定
            IChild selectGrp = initModel.getSelectionGroupList().stream()
                            .filter(child -> Objects.equals(child.getValue(), selectedGrpSid))
                            .findAny()
                            .orElse(null);
            if (selectGrp == null) {

                String ansGrpSid = answerDefaultGroup(reqMdl, con, paramModel,
                        initModel.getSelectionGroupList().stream()
                            .map(child -> child.getValue())
                            .collect(Collectors.toList()));
                selectGrp = initModel.getSelectionGroupList().stream()
                        .filter(child -> Objects.equals(child.getValue(), ansGrpSid))
                        .findAny()
                        .orElse(null);
                if (selectGrp == null) {
                    //デフォルトグループも見つからなければ先頭を選択
                    selectGrp = initModel.getSelectionGroupList().get(0);
                }

            }
            initModel.setSelectGrp(selectGrp);
        }
        initModel.setSelectionList(answerSelectionList(reqMdl, con, paramModel,
                Optional.ofNullable(initModel.getSelectGrp())
                    .map(selectGrp -> selectGrp.getValue())
                    .orElse(null),
                initModel.getSelectedList().stream()
                    .map(child -> child.getValue())
                    .collect(Collectors.toList()))
        .stream()
        .collect(Collectors.toList()));

        chain.doFilterChain(reqMdl, con, paramModel, selector, initModel);

    }


    @Override
    public void doFilter(ValidateFilterChain chain, RequestModel reqMdl,
            Connection con, ParameterObject paramModel, ISelector selector,
            Select select) throws SQLException {
        chain.doFilterChain(reqMdl, con, paramModel, selector, select);
    }

    @Override
    public boolean isUseDetailSearch() {
        return false;
    }

}
