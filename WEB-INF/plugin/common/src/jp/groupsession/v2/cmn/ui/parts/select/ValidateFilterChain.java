package jp.groupsession.v2.cmn.ui.parts.select;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.exception.UIValidateException;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;

/**
 *
 * <br>[機  能] 入力チェック動作 フィルタチェーン
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ValidateFilterChain {
    /** イテレータ*/
    private final Iterator<? extends IValidateFilter> it__;
    /** 入力チェック例外*/
    private UIValidateException exception__;

    /**
     * <p>exception を取得します。
     * @return exception
     * @see jp.groupsession.v2.cmn.ui.parts.select.ValidateFilterChain#exception__
     */
    public UIValidateException getException() {
        return exception__;
    }

    /**
     *
     * コンストラクタ
     * @param validateFilterList
     */
    public ValidateFilterChain(Collection<? extends IValidateFilter> validateFilterList) {
        it__ = validateFilterList.iterator();
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
     * @param select 選択UI内入力チェック対象選択モデル
     * @throws SQLException
     */
    public void doFilterChain(
            RequestModel reqMdl,
            Connection con,
            ParameterObject paramModel,
            ISelector selector,
            Select select
            ) throws SQLException {
        if (it__.hasNext()) {
            IValidateFilter filter = it__.next();
            filter.doFilter(this,
                    reqMdl,
                    con,
                    paramModel,
                    selector,
                    select);
            return;
        }
    }
    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param e
     */
    public void addException(UIValidateException e) {
        if (exception__ == null) {
            exception__ = e;
        } else {
            UIValidateException.union(exception__, e);
        }
    }
}
