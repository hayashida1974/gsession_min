package jp.co.sjts.util.struts;

import java.sql.Connection;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] 共通的に使用するアクションクラスのベース
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public abstract class AbstractAction extends Action {

    /**
     * <br>[機  能] セッション管理後の動作を記述する。
     * <br>[解  説]
     * <br>[備  考]
     * @param mapping マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con DBコネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    public abstract ActionForward executeAction(
        ActionMapping mapping,
        ActionForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con
        )
        throws Exception;

    /**
     * [機 能] セッションが確立しているか判定を行う<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param req リクエスト
     * @return true:セッションが確立している,false:セッションが確立していない
     */
    public abstract boolean isSession(HttpServletRequest req);

    /**
     * <br>[機 能] セッション情報にKey情報が存在するか判定を行う。
     * <br>[解 説]
     * <br>[備 考]
     * @param req リクエスト
     * @param key キー
     * @return true:KEYが存在している,false:KEYが存在していない
     */
    protected static boolean _existsSessionKey(
        HttpServletRequest req,
        String key) {
        HttpSession hs = req.getSession();
        if (hs != null && hs.getAttribute(key) != null) {
            return true;
        }
        return false;
    }

    /**
     * <br>[機 能] Sessionを削除します。
     * <br>[解 説]
     * <br>[備 考] Sessionがない場合は何もしません。
     * @param req リクエスト
     */
    public void removeSession(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    /**
     * <br>[機 能] セッションが確立されていない状態でのアクセスを許可するのか判定を行う。
     * <br>[解 説]
     * <br>[備 考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:許可する,false:許可しない
     */
    public boolean canNoSessionAccess(HttpServletRequest req, ActionForm form) {
        return false;
    }

    /**
     * [機 能] プラグインが使用可能か判定を行う<br>
     * [解 説] <br>
     * [備 考] <br>
     * @param plugins 使用可能なプラグインID
     * @return true:使用可能,false:使用不可
     */
    public boolean canAccessPlugin(List<String> plugins) {
        return false;
    }

    /**
     * <br>[機 能] キャッシュを有効にして良いか判定を行う
     * <br>[解 説] サブクラスでこのメソッドをオーバーライドして使用する
     * <br>[備 考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {
        return false;
    }

    /**
     * <br>[機 能] キャッシュを無効にする
     * <br>[解 説]
     * <br>[備 考]
     * @param res HttpServletResponse
     */
    public static void setNocache(HttpServletResponse res) {
        //キャッシュを無効にする
        Calendar objCal1 = Calendar.getInstance();
        Calendar objCal2 = Calendar.getInstance();
        objCal2.set(1970, 0, 1, 0, 0, 0);
        res.setDateHeader("Last-Modified", objCal1.getTime().getTime());
        res.setDateHeader("Expires", objCal2.getTime().getTime());
//        res.setHeader("pragma", "no-cache");
        res.setHeader("Cache-Control", "no-cache");
    }
}
