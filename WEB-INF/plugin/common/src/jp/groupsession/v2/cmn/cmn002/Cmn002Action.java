package jp.groupsession.v2.cmn.cmn002;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.config.Plugin;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnExtPageDao;
import jp.groupsession.v2.cmn.dao.base.CmnPermittedDomainDao;
import jp.groupsession.v2.cmn.dao.base.CmnPluginAdminDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnExtPageModel;
import jp.groupsession.v2.cmn.model.base.CmnPermittedDomainModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] フレーム(メニューとボディ)のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn002Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn002Action.class);

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

    /**
     * <p>
     * アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        ActionForward forward = null;
        Cmn002Form cmnForm = (Cmn002Form) form;
        forward = __doDisp(map, cmnForm, req, res, con);
        return forward;
    }

    /**
     * <p>表示
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward __doDisp(ActionMapping map, Cmn002Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = map.getInputForward();
        
        BaseUserModel umodel = getSessionUserModel(req);
        if (umodel == null) {
            return null;
        }
        
        //管理者設定，個人設定からの戻り先を設定
        if (!StringUtil.isNullZeroString(form.getBackPlugin())) {
            CommonBiz cmnBiz = new CommonBiz();
            int usrSid = umodel.getUsrsid();
            List<String> pluginList = cmnBiz.getCanUsePluginIdList(con, usrSid);
            if (pluginList.contains(form.getBackPlugin())) {
                umodel.setBackPluginId(form.getBackPlugin());
            }
        }
        
        //管理者設定表示フラグを設定
        __setSettingiFlg(form, umodel, con, req);

        if (umodel.getUsrsid() == 0) {
            //初期管理者ユーザの場合
            //管理者ツールURLをセット
            ActionForward defforward = map.findForward("admin");
            String url = ".." + defforward.getPath();
            form.setUrl(url);
        }

        //入力チェック
        PluginConfig pconfig = getPluginConfig(req);
        ActionErrors errors = form.validateBodyUrl(map, req, pconfig);
        if (errors.size() > 0) {
            log__.debug("エラー " + errors.size());
            addErrors(req, errors);
            //デフォルトURLをセット
            form.setUrl(__getDefaultUrl(map));
        }

        //未入力の場合
        if (form.getUrl() == null || form.getUrl().length() <= 0) {
            //デフォルトURLをセット
            form.setUrl(__getDefaultUrl(map));
        } else {
            //URLから想定しない文字列を除去
            String url = form.getUrl();
            url = url.replaceAll("'", "");
            url = url.replaceAll("%27", "");
            form.setUrl(url);
        }

        //ドメインチェック
        char[] url = form.getUrl().toCharArray();

        //入力URLが相対パスの場合は内部ドメインなので許可
        if ((url[0] == '.' && url[1] == '.')
                || (url[0] == '/' && url[1] != '/')
                        || (url[0] == '.' && url[1] == '/')) {
            return forward;
        }

        CmnExtPageDao pgDao = new CmnExtPageDao(con);
        CmnExtPageModel pgMdl = new CmnExtPageModel();
        pgMdl = pgDao.select();

        //設定がされていない場合は全許可
        if (pgMdl == null) {
            return forward;
        }

        //制限設定:制限しないの場合は外部ドメインも許可
        if (pgMdl.getCepLimitDsp() == GSConstMain.DSP_EXT_PAGE_NO_LIMIT) {
            return forward;
        }

        boolean dspPage = false;
        String domain = "";

        //入力URLが絶対パスの場合ドメインを抽出してチェックを行う
        if (pgMdl.getCepLimitDsp() == GSConstMain.DSP_EXT_PAGE_LIMITED) {

            //サーバURLのドメインを取得
            RequestModel reqMdl = getRequestModel(req);
            String serverUrl = reqMdl.getRequestURL().toString();
            int startServerDomain = __getStartDomain(serverUrl);
            int endServerDomain = __getEndDomain(serverUrl, startServerDomain);
            String serverDomain = serverUrl.substring(startServerDomain, endServerDomain);

            //入力されたURLのドメインを取得
            int startDomain = __getStartDomain(form.getUrl());
            int endDomain = __getEndDomain(form.getUrl(), startDomain);
            String outDomain = form.getUrl().substring(startDomain, endDomain);

            //サーバドメインと入力ドメインの比較
            if (!serverDomain.equals(outDomain)) {
                domain = form.getUrl().substring(startDomain, endDomain);
                dspPage = __isPermittedDomain(con, domain);
            } else {
                dspPage = true;
            }
        }

        //ページの表示を許可されていない場合
        if (!dspPage) {
            forward = __doDspPageError(map, form, req, domain);
        }
        
        log__.debug("BODY URL is " + form.getUrl());
        return forward;
    }

    /**
     * <p>BodyデフォルトのURLを返す
     * @param map アクションマッピング
     * @return デフォルトURL
     */
    private String __getDefaultUrl(ActionMapping map) {
        ActionForward defforward = map.findForward("main");
        String url = ".." + defforward.getPath();
        return url;
    }

    /**
     * <p> 引数で指定したURLの何文字目からドメインかを取得します。
     * @param url URL
     * @return URLの何文字目か
     * */
    private int __getStartDomain(String url) {
        char[] urlArray = url.toCharArray();
        for (int i = 0; i < urlArray.length; i++) {
            if (urlArray[i] == '/' && urlArray[i + 1] == '/') {
                if (i < urlArray.length - 1 && urlArray[i + 2] != '/') {
                    return i + 2;
                }
            }
        }
        return 0;
    }

    /**
     * <p> 引数で指定したURLの何文字目がドメインの終わり(/)かを取得します。
     * @param url URL
     * @param startDomain URLの何文字目からドメインか
     * @return URLの何文字目か
     * */
    private int __getEndDomain(String url, int startDomain) {
        char[] urlArray = url.toCharArray();
        for (int i = startDomain; i < urlArray.length; i++) {
            if (urlArray[i] == '/') {
                return i;
            }
        }
        //URL末尾が「/」でない場合、URLの文字数を返す
        return urlArray.length;
    }
    
    /**
     * <br>[機  能] 管理者設定表示フラグを設定する
     * <br>[解  説]
     * <br>[備  考] システム管理者グループに属している、またはプラグイン管理者の場合に表示する
     * @param form フォーム
     * @param umodel BaseUserModel
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __setSettingiFlg(Cmn002Form form, BaseUserModel umodel, 
            Connection con, HttpServletRequest req) throws Exception {
        
        int usrSid = umodel.getUsrsid();
        CmnPluginAdminDao cpaDao = new CmnPluginAdminDao(con);
        List<String> adminPluginList = cpaDao.getPluginAdmin(usrSid);
        CommonBiz cmnBiz = new CommonBiz();
        adminPluginList = cmnBiz.getPluginIdWithoutControl(con, adminPluginList, usrSid);
        if (umodel.getUsrsid() != GSConst.SYSTEM_USER_ADMIN) {
            form.setPreSettingFlg(1);
        }
        if (umodel.getAdminFlg()) {
            form.setAdmSettingFlg(1);
        }
        if (adminPluginList.isEmpty()) {
            return;
        }
        PluginConfig pconfig = getPluginConfig(req);
        for (Plugin plugin : pconfig.getPluginDataList()) {
            if (plugin.getAdminSettingInfo().getView() == null) {
                continue;
            }
            if (plugin.getAdminSettingInfo().getView().equals("true")) {
                for (String id : adminPluginList) {
                    if (id.equals(plugin.getId())) {
                        form.setAdmSettingFlg(1);
                        return;
                    }
                }
            }
        }
    }

    /**
     * <p>外部ドメインが、ページの表示を許可されているドメインかを判定
     * @param con コネクション
     * @param domain urlパラメータに入力された外部ドメイン
     * @return true:外部ページ表示可能 false:外部ページ表示不可能
     * @throws SQLException SQL実行例外
     * */
    private boolean __isPermittedDomain(Connection con,
                             String domain) throws SQLException {

        CmnPermittedDomainDao cpdDao
                                    = new CmnPermittedDomainDao(con);
        List<CmnPermittedDomainModel> cpdMdlList = cpdDao.select();



        //外部ドメイン
        String chkDomain = domain;
        //外部ドメインの末尾に「/」を追加
        if (!domain.endsWith("/")) {
            chkDomain = chkDomain + "/";
        }

        for (CmnPermittedDomainModel model : cpdMdlList) {
                //表示を許可されている外部ドメインを取得
                String permittedDomain = model.getCpdExtDomain();
                //末尾に「/」がない場合は追加
                if (!permittedDomain.endsWith("/")) {
                    permittedDomain = permittedDomain + "/";
                }
                //入力された外部ドメインが表示を許可されている場合
                if (__isMatchDomain(permittedDomain, chkDomain)) {
                    return true;
                }
        }
        return false;
    }


    /**
     * <p>urlパラメータに入力された外部ドメインが表示を許可されているか判定
     * @param permittedDomain 表示を許可する外部ドメイン
     * @param chkDomain チェックドメイン
     * @return true:表示を許可 false:表示を禁止
     * */
    private boolean __isMatchDomain(String permittedDomain, String chkDomain) {

        //表示を許可されている外部ドメインに「*」が含まれる場合、ワイルドカードとして扱う
        if (permittedDomain.contains("*")) {
            //「*」ごとに文字列を区切る
            String[] wildCard = permittedDomain.split("\\*");
            //区切られた文字列と入力された外部ドメインを比較
            for (int i = 0; i < wildCard.length; i++) {
                if (StringUtil.isNullZeroStringSpace(wildCard[i])) {
                    continue;
                }
                //一番最初の「*」より前にある文字列で前方一致
                if (i == 0 && !chkDomain.startsWith(wildCard[i])) {
                    return false;
                //「*」同士の間は部分一致
                } else if (!chkDomain.contains(wildCard[i])) {
                    return false;
                //一番最後の「*」より後にある文字列で後方一致
                } else if (i == wildCard.length - 1
                        && !chkDomain.endsWith(wildCard[i])) {
                    return false;
                }
                //比較位置をずらす
                int idx = chkDomain.indexOf(wildCard[i]);
                chkDomain = chkDomain.substring(idx + wildCard[i].length());
            }
        //ワイルドカードを含まない場合は完全一致
        } else if (!permittedDomain.endsWith(chkDomain)) {
            return false;
        }
        return true;
    }

    /**
     * <p>許可されない外部ドメインを入力された場合のエラー画面設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param domain 外部ドメイン
     * @return アクションフォワード
     * */
    private ActionForward __doDspPageError(ActionMapping map,
            Cmn002Form form, HttpServletRequest req, String domain) {
        ActionForward forward = null;

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //エラーメッセージ
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_WARN);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_SELF);
        cmn999Form.setMessage(
                msgRes.getMessage("error.not.permitted.domain", domain));

        urlForward = map.findForward("mainFrame");
        cmn999Form.setUrlOK(urlForward.getPath());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;
    }

}
