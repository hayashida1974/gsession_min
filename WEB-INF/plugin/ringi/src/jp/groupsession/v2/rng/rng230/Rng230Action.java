package jp.groupsession.v2.rng.rng230;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.rng.rng100.Rng100Action;
import jp.groupsession.v2.rng.rng100.Rng100Form;

/**
 * <br>[機  能] 稟議 経路テンプレート一覧画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng230Action extends Rng100Action {

    /**
     * <p>管理者以外のアクセスを許可するのか判定を行う。
     * <p>サブクラスでこのメソッドをオーバーライドして使用する
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNotAdminAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    /** プラグインが使用可能か判定します
     * @param req リクエスト
     * @param form アクションフォーム
     * @param con DBコネクション
     * @return boolean true:使用可能 false:使用不可
     * @throws SQLException SQL実行時例外
     */
    @Override
    protected boolean _isAccessPlugin(HttpServletRequest req, ActionForm form, Connection con)
    throws SQLException {
        if (_isSystemAdmin(req, con)) {
            return true; //システム管理者の場合はプラグインチェックをしない
        }
        return super._isAccessPlugin(req, form, con);
    }

    @Override
    protected void _prepareAction(ActionMapping map, Rng100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        //共有経路テンプレートのテンプレートユーザSIDは0
        form.setUsrSid(0);
    }
    @Override
    protected ActionForward _immigration(ActionMapping map, Rng100Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        return null;
    }
}
