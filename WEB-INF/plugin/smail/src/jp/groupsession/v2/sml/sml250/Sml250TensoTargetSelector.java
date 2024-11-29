package jp.groupsession.v2.sml.sml250;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.Child;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

public class Sml250TensoTargetSelector extends AbstractSelector {
    /** 含有クラス ユーザ選択機能実装*/
    private final UserGroupSelector base__;
    protected Sml250TensoTargetSelector(ParamForm param) {
        super(param);
        base__ = UserGroupSelector.builder()
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
                .chainType(EnumSelectType.USERGROUP)
                .chainLabel(param.getLabel())
                .chainGroupSelectionParamName(param.getGroupSelectionParamName())
                .build();

    }

    /**
    *
    * <br>[機  能] ビルダークラス
    * <br>[解  説]
    * <br>[備  考]
    *
    * @author JTS
    */
   public static class ParamForm
   extends SelectorFactory<Sml250TensoTargetSelector, ParamForm> {

       public ParamForm(Class<Sml250TensoTargetSelector> targetClz) {
           super(targetClz);
       }

   }

   /**
    *
    * <br>[機  能] ビルダークラスの取得
    * <br>[解  説]
    * <br>[備  考]
    * @return ビルダー
    */
   public static ParamForm builder() {
       ParamForm ret = new ParamForm(Sml250TensoTargetSelector.class);
       return ret;
   }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
            throws SQLException {
        Sml250ParamModel paramMdl = new Sml250ParamModel();
        paramMdl.setParam((Sml250Form) paramModel.getInstance());


        List<String> userSel = Optional.of(paramMdl.getSml250userKbnUser())
                                .map(arr -> Arrays.asList(arr))
                                .orElse(List.of());

        return base__.answerSelectedList(reqMdl, con, paramModel, selectedSidList)
                .stream()
                .map(child -> (Child) child)
                .filter(child -> {
                    if (userSel.contains(child.getValue())) {
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        Sml250ParamModel paramMdl = new Sml250ParamModel();
        paramMdl.setParam((Sml250Form) paramModel.getInstance());


        List<String> userSel = Optional.of(paramMdl.getSml250userKbnUser())
                                .map(arr -> Arrays.asList(arr))
                                .orElse(List.of());

        //選択済みを除外
        userSel = userSel.stream()
            .filter(sid -> selectedSidList.contains(sid) == false)
            .collect(Collectors.toList());


        return base__.answerSelectedList(reqMdl, con, paramModel, userSel);
    }
    @Override
    public List<IChild> answerGroupList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel) throws SQLException {
        return List.of();
    }

}
