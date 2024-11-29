package jp.groupsession.v2.rng.ui.parts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.ISelector;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.struts.msg.GsMessage;

public class InCondPostSelector extends AbstractSelector {
    /** 役職リスト*/
    private List<IChild> posList__;
    /** 選択済みID */
    private String[] selected__ = new String[0];
    /** 選択先設定*/
    private Map<String, Select> select__;

    protected InCondPostSelector(ParamForm param) {
        super(param);
    }

    /** パラメータ文字列  */
    private String paramName__;

    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class ParamForm extends SelectorFactory<InCondPostSelector, ParamForm> {

        public ParamForm(Class<InCondPostSelector> targetClz) {
            super(targetClz);
        }

    }
    /**
     *
     * <br>[機  能] ビルダークラスの生成
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダークラス
     */
    public static ParamForm builder() {
        ParamForm ret = new ParamForm(InCondPostSelector.class);
        return ret;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
                    throws SQLException {
        return __getPosList(reqMdl, con)
                .stream()
                .filter(child -> selectedSidList.contains(child.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        return __getPosList(reqMdl, con)
                .stream()
                .filter(child -> selectedSidList.contains(child.getValue()) == false)
                .collect(Collectors.toList());
    }

    private List<IChild> __getPosList(RequestModel reqMdl, Connection con) throws SQLException {
        if (posList__ != null) {
            return posList__;
        }
        GsMessage gsMsg = new GsMessage(reqMdl);
        PosBiz posBiz = new PosBiz();
        //役職選択
        List<LabelValueBean> allPosLabelList = posBiz.getPosLabelList(con, false);
        //役職のないユーザ指定用に「役職なし」を追加
        allPosLabelList.add(0, new LabelValueBean(gsMsg.getMessage("cmn.nopost"), "0"));
        posList__ = allPosLabelList.stream()
                .map(lbl -> new Child(lbl.getLabel(), lbl.getValue()))
                .collect(Collectors.toList());
        return posList__;

    }

    @Override
    public Map<String, Select> getSelectMap() {
        return select__;
    }
    @Override
    public Select getSelect(String key) {
        return getSelectMap().get(key);
    }
    /**
     * <p>selected を取得します。
     * @return selected
     */
    public String[] getSelected() {
        return selected__;
    }
    /**
     * <p>selected をセットします。
     * @param selected selected
     */
    public void setSelected(String[] selected) {
        selected__ = selected;
    }

    /**
     * <p>paramName を取得します。
     * @return paramName
     * @see jp.groupsession.v2.rng.ui.parts.InCondPostSelector#paramName__
     */
    public String getParamName() {
        return paramName__;
    }

    /**
     * <p>paramName をセットします。
     * @param paramName paramName
     * @see jp.groupsession.v2.rng.ui.parts.InCondPostSelector#paramName__
     */
    public void setParamName(String paramName) {
        paramName__ = paramName;
        select__ = new LinkedHashMap<String, Select>();
        select__.put(
                ISelector.SELECT_NAME_HEAD + select__.size(),
                Select.builder()
                .chainParameterName(
                        String.format(
                                "%s.selected",
                                paramName__)

                        )
                .build()
                );

    }
    /**
     * <p>selected を取得します。
     * @return selected
     */
    public List<LabelValueBean> getSelectedList() {
        return getSelectMap().values()
            .stream()
            .findAny()
            .stream()
            .flatMap(sel -> sel.getList().stream())
            .map(child -> new LabelValueBean(
                    child.getName(),
                    child.getValue()))
            .collect(Collectors.toList());
    }


}
