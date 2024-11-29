package jp.groupsession.v2.cmn.ui.parts.select;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;

/**
 *
 * <br>[機  能] 選択元 描画処理フィルタチェーン
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GroupSelectionDspInitFilterChain {
    /** イテレータ*/
    private final Iterator<? extends IGroupSelectionDspInitFilter> it__;
    /** フィルタ実行パラメータ*/
    GroupSelectionDspInitModel param__;
    /**
     * コンストラクタ
     * @param filterCollection
     */
    public GroupSelectionDspInitFilterChain(
            Collection<? extends IGroupSelectionDspInitFilter> filterCollection) {
        super();
        it__ = filterCollection.iterator();
    }



    /**
     *
     * <br>[機  能] フィルタチェーン実行
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @param paramModel パラメータモデル
     * @param selector 選択UIモデル
     * @param initModel フィルタパラメータ
     * @throws SQLException
     */
    public void doFilterChain(
            RequestModel reqMdl,
            Connection con,
            ParameterObject paramModel,
            ISelector selector,
            GroupSelectionDspInitModel initModel) throws SQLException {
        param__ = new GroupSelectionDspInitModel();
        try {
            BeanUtils.copyProperties(param__, initModel);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        if (it__.hasNext()) {
            IGroupSelectionDspInitFilter filter = it__.next();
            filter.doFilter(this,
                    reqMdl,
                    con,
                    paramModel,
                    selector,
                    param__
                    );
            return;
        }
    }



    /**
     * <p>selectionGroupList を取得します。
     * @return selectionGroupList
     * @see GroupSelectionDspInitModel#selectGrp__
     */
    public List<? extends IChild> getSelectionGroupList() {
        return param__.getSelectionGroupList();
    }





    /**
     * <p>selectGrp を取得します。
     * @return selectGrp
     * @see GroupSelectionDspInitModel#selectGrp__
     */
    public IChild getSelectGrp() {
        return param__.getSelectGrp();
    }

    /**
     * <p>selectionList を取得します。
     * @return selectionList
     * @see jp.groupsession.v2.cmn.ui.parts.select.GroupSelectionDspInitFilterChain#selectionList__
     */
    public List<? extends IChild> getSelectionList() {
        return param__.getSelectionList();
    }





}
