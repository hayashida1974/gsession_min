package jp.groupsession.v2.cmn;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] WEB-INF以下に配置したファイルをダウンロードさせるServlet
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WebinfServlet extends HttpServlet {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(WebinfServlet.class);

    /**
     * デフォルトコンストラクタ
     */
    public WebinfServlet() {
        super();
    }

    /**
     * <p>GETリクエストの処理を行う。
     * @param  req リクエスト
     * @param  res レスポンス
     * @exception ServletException サーブレット例外
     */
    public final void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException {
        doRedirect(req, res);
    }

    /**
     * <p>POSTリクエストの処理を行う。
     * @param  req リクエスト
     * @param  res レスポンス
     * @exception ServletException サーブレット例外
     */
    public final void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException {
        doRedirect(req, res);
    }

    /**
     * <br>[機  能] 処理メイン
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param res レスポンス
     */
    private void doRedirect(HttpServletRequest req, HttpServletResponse res) {

        //キャッシュを無効にする
        setNocache(res);

        //以下リダイレクト処理
        String uri = req.getRequestURI();
        if (StringUtil.isNullZeroString(uri)) {
            return;
        }

        //
        char[] target = uri.toCharArray();
        boolean slFlg = false;
        StringBuilder dBuf = new StringBuilder();
        dBuf.append("/WEB-INF/plugin/");
        for (int i = 0; i < target.length; i++) {
            if (i == 0) {
                //始めの/は読みとばす
                continue;
            }
            char c = target[i];
            if (slFlg == false) {
                if ('/' == c) {
                    slFlg = true;
                }
            } else {
                dBuf.append(c);
            }
        }

        //
        String dpath = dBuf.toString();
        log__.debug(dpath);
        RequestDispatcher dispatch = getServletContext().getRequestDispatcher(dpath);
        try {
            dispatch.forward(req, res);
        } catch (ServletException e) {
            log__.error(e);
        } catch (IOException e) {
            log__.error(e);
        }
    }

    /**
     * キャッシュを無効にする
     * @param res HttpServletResponse
     */
    public static void setNocache(HttpServletResponse res) {
        //キャッシュを無効にする
        Calendar objCal1 = Calendar.getInstance();
        Calendar objCal2 = Calendar.getInstance();
        objCal2.set(1970, 0, 1, 0, 0, 0);
        res.setDateHeader("Last-Modified", objCal1.getTime().getTime());
        res.setDateHeader("Expires", objCal2.getTime().getTime());
        res.setHeader("pragma", "no-cache");
        res.setHeader("Cache-Control", "no-cache");
    }

    /**
     * <p>セッションが確立しているか判定を行う
     * @param req リクエスト
     * @return true:セッションが確立している,false:セッションが確立していない
     */
    public boolean isSession(HttpServletRequest req) {
        BaseUserModel smodel = getSessionUserModel(req);
        if (smodel == null) {
            return false;
        }
        return true;
    }

    /**
     * <p>セッションからユーザモデルを取得する。
     * @param req リクエスト
     * @return SessionUserModel
     */
    public BaseUserModel getSessionUserModel(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session == null) {
            return null;
        }
        Object tmp = session.getAttribute(GSConst.SESSION_KEY);
        if (tmp == null) {
            return null;
        }
        BaseUserModel smodel = (BaseUserModel) tmp;
        return smodel;
    }
}