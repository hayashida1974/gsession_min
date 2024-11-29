package jp.groupsession.v2.sml.ui.parts.account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.sml.dao.SmlAccountDao;

public class SharedAccountSelector extends AbstractSelector {
    protected SharedAccountSelector(ParamForm param) {
        super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class ParamForm extends SelectorFactory<SharedAccountSelector, ParamForm> {

        public ParamForm(Class<SharedAccountSelector> targetClz) {
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
        ParamForm ret = new ParamForm(SharedAccountSelector.class);
        return ret;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
            throws SQLException {
        List<IChild> ret = new ArrayList<IChild>();

        //代表アカウントを取得
        SmlAccountDao sacDao = new SmlAccountDao(con);

        List<Integer> sidList =
                selectedSidList.stream()
                    .map(sid -> Integer.valueOf(
                            sid
                            )
                        )
                    .collect(Collectors.toList());
        if (sidList.size() > 0) {
            ret.addAll(
                sacDao.selectSmlAccount(sidList)
                    .stream()
                    .map(acc -> new Child(
                            acc.getSacName(),
                            String.valueOf(acc.getSacSid())
                            )
                        )
                    .collect(Collectors.toList())
            );
        }
        return ret;
    }

    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        List<IChild> ret = new ArrayList<IChild>();
        //代表アカウントを取得
        SmlAccountDao sacDao = new SmlAccountDao(con);

        List<Integer> sidList = sacDao.getSmlAccountSidList()
                    .stream()
                    .filter(sid -> selectedSidList.contains(
                                String.valueOf(sid)
                                ) == false
                            )
                    .collect(Collectors.toList());
        if (sidList.size() > 0) {
            ret.addAll(
                sacDao.selectSmlAccount(sidList)
                    .stream()
                    .map(acc -> new Child(
                            acc.getSacName(),
                            String.valueOf(acc.getSacSid())
                            )
                        )
                    .collect(Collectors.toList())
            );
        }

        return ret;
    }

}
