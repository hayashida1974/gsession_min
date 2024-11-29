package jp.groupsession.v2.usr.usr030;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.http.TempFileUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstLog;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.UserSearchDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmLabelDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmModel;
import jp.groupsession.v2.lic.AbstractLicenseAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.usr031.Usr031Biz;
import jp.groupsession.v2.usr.usr031.Usr031Form;
import jp.groupsession.v2.usr.usr031kn.Usr031knForm;
import jp.groupsession.v2.usr.usr040.ShainSearchModel;

/**
 * <br>[機  能] メイン 管理者設定 ユーザマネージャー画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr030Action extends AbstractLicenseAction {
    /** 画面ID */
    public static final String SCR_ID = "usr030";

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr030Action.class);
    /**
     * <br>[機  能] キャッシュを有効にして良いか判定を行う
     * <br>[解  説] ダウンロード時のみ有効にする
     * <br>[備  考]
     * @param req リクエスト
     * @param form アクションフォーム
     * @return true:有効にする,false:無効にする
     */
    public boolean isCacheOk(HttpServletRequest req, ActionForm form) {

        //CMD
        String cmd = NullDefault.getString(req.getParameter(GSConst.P_CMD), "");
        cmd = cmd.trim();
        //ダウンロードフラグ
        String downLoadFlg = NullDefault.getString(req.getParameter("csvOut"), "");
        downLoadFlg = downLoadFlg.trim();

        if (cmd.equals("exp_ok")) {
            if (downLoadFlg.equals("1")) {
                log__.debug("CSVファイルダウンロード");
                return true;
            }
        }
        return false;
    }

    /**
     * <p>
     * アクション実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     */
    public ActionForward usrManageAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        log__.debug("START");
        ActionForward forward = null;

        Usr030Form usr030Form = (Usr030Form) form;
        log__.debug("csvOut==>" + req.getParameter("csvOut"));

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        if (cmd.equals("back")) {
            forward = map.findForward("back");

        } else if (cmd.equals("groupEdit")) {
            log__.debug(">>>グループ修正");
            forward = map.findForward("groupEdit");

        } else if (cmd.equals("add")) {
            log__.debug(">>>ユーザ追加");
            return map.findForward("add");

        } else if (cmd.equals("edit")) {
            log__.debug(">>>ユーザ修正");
            forward = __doEdit(map, usr030Form, req, res, con);

        } else if (cmd.equals("del")) {
            log__.debug(">>>ユーザ削除");
            forward = __doDelete(map, usr030Form, req, res, con);

        } else if (cmd.equals("searchKn")) {
            log__.debug(">>>カナ検索実行");
            forward = __doSearchKana(map, usr030Form, req, res, con);
        } else if (cmd.equals("searchSyosai")) {
            log__.debug(">>>詳細検索実行");

            //詳細検索フラグを設定
            boolean detailSearchFlg = usr030Form.getUsr030DetailSearchFlg();
            detailSearchFlg = true;
            usr030Form.setUsr030DetailSearchFlg(detailSearchFlg);

            forward = __doSearchSyosai(map, usr030Form, req, res, con);
        } else if (cmd.equals("yuko")) {
            log__.debug(">>>ユーザ有効化");
            forward = __doYuko(map, usr030Form, req, res, con);
        } else if (cmd.equals("muko")) {
            log__.debug(">>>ユーザ無効化");
            forward = __doMuko(map, usr030Form, req, res, con);

        } else if (cmd.equals("csvDel")) {
            log__.debug("ユーザ一括削除");
            forward = map.findForward("csvDel");

        } else if (cmd.equals("userImp")) {
            log__.debug("ユーザインポート");
            forward = map.findForward("userImp");

        } else if (cmd.equals("userExp")) {
            log__.debug("ユーザエクスポート確認");
            forward = __doExport(map, usr030Form, req, res, con);

        } else if (cmd.equals("exp_ok")) {
            log__.debug("ユーザエクスポート実行");
            String downLoadFlg = NullDefault.getString(req.getParameter("csvOut"), "");
            downLoadFlg = downLoadFlg.trim();
            if (downLoadFlg.equals("1")) {
                log__.debug("CSVファイルダウンロード");
                return __doExportOk(map, usr030Form, req, res, con);
            }

        } else if (cmd.equals("name")) {
            log__.debug("氏名タブクリック");
            forward = __doNameTab(map, usr030Form, req, res, con);

        } else if (cmd.equals("shousai")) {
            log__.debug("詳細検索タブクリック");
            forward = __doShousaiTab(map, usr030Form, req, res, con);

        } else {
            log__.debug(">>>デフォルト ユーザ一覧表示");
            forward = __doInit(map, usr030Form, req, res, con);
        }

        log__.debug("END");
        return forward;
    }

    /**
     * <br>[機  能] 初期パラメータ設定
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map, Usr030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("初期表示処理");
        ActionForward forward = null;

        int mode = form.getUsr030cmdMode();
        int serchFlg = form.getUsr030SearchFlg();
        if (mode == GSConstUser.MODE_NAME) {
            //氏名検索
            if (serchFlg == GSConstUser.SEARCH_ZUMI) {
                //検索実行済みの場合
                log__.debug("氏名検索済");
                forward = __doSearchKana(map, form, req, res, con);
            } else {
                //未検索の場合
                log__.debug("氏名未検索");
                forward = __doNameTab(map, form, req, res, con);
            }
        } else if (mode == GSConstUser.MODE_SHOUSAI) {
            //詳細
            if (serchFlg == GSConstUser.SEARCH_ZUMI) {
                //検索実行済みの場合
                log__.debug("詳細検索済");
                forward = __doSearchSyosai(map, form, req, res, con);
            } else {
                //未検索の場合
                log__.debug("詳細未検索");
                forward = __doShousaiTab(map, form, req, res, con);
            }
        }

        return forward;
    }

    /**
     * <p>
     * 削除モード時初期処理
     * @param map アクションマッピング
     * @param knForm アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     */
    private void __doDelInit(ActionMapping map, Usr030Form knForm,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        log__.debug("START");

        con.setAutoCommit(true);

        Usr031knForm form = new Usr031knForm();
        //削除時の情報取得
        form.setProcessMode("del");
        CmnUsrmDao udao = new CmnUsrmDao(con);
        CmnUsrmModel umodel = new CmnUsrmModel();
        CmnUsrmLabelDao culDao = new CmnUsrmLabelDao(con);
        //セレクトボックス内選択ユーザーセット
        log__.debug(">>>form.getUsr030selectuser()=" + knForm.getUsr030selectuser());
        String[] usids = knForm.getUsr030selectusers();
        int usid = NullDefault.getInt(usids[0], -1);
        umodel.setUsrSid(usid);

        log__.debug(">>>SELECT: CMN_USRM ログインID取得");
        //ログイン時ユーザーID取得
        form.setUsr031userid(udao.select(usid).getUsrLgid());

        log__.debug(">>>SELECT: CMN_USRM_INF ユーザー情報取得");
        CmnUsrmInfDao ufdao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel ufmodel = new CmnUsrmInfModel();

        ufmodel.setUsrSid(usid);
        CmnUsrmInfModel infBean = ufdao.select(ufmodel);

        //氏名漢字
        form.setUsr031sei(infBean.getUsiSei());
        form.setUsr031mei(infBean.getUsiMei());

        //氏名カナ
        form.setUsr031seikn(infBean.getUsiSeiKn());
        form.setUsr031meikn(infBean.getUsiMeiKn());
        //職員番号
        form.setUsr031shainno(infBean.getUsiSyainNo());
        //役職
        form.setUsr031yakushoku(infBean.getUsiYakusyoku());
        //備考
        form.setUsr031bikou(infBean.getUsiBiko());

        //所属部署
        CmnBelongmDao bdao = new CmnBelongmDao(con);
        List<CmnBelongmModel> bmodelList = bdao.selectUserBelongGroup(usid);

        String selectgroup = Usr031Biz.makeCSVStatement(bmodelList);
        form.setSelectgroup(selectgroup);

        //ラベル
        form.setUsrLabel(culDao.getLabListBelongUsr(usid));

        //デフォルトグループ
        GroupBiz grpBz = new GroupBiz();
        int defGrp = grpBz.getDefaultGroupSid(usid, con);
        form.setUsr031defgroup(defGrp);

        con.setAutoCommit(false);

        req.setAttribute("usr031Form", (Usr031Form) form);
        req.setAttribute("usr031knForm", form);
        log__.debug("END");
    }

    /**
     * <p>カナ検索
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doSearchKana(ActionMapping map, Usr030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;
        log__.debug("START");
        log__.debug("五十音検索実行");

        con.setAutoCommit(true);

        //五十音リストの作成
        __setKanaIndex(form, con, req);
        //検索実行
        UserSearchDao udao = new UserSearchDao(con);
        String kanaKey = form.getUsr030SearchKana();
        log__.debug("kanaKey :" + kanaKey);
        if (kanaKey != null) {
            List<CmnUsrmInfModel> ulist = udao.getUserKanaIndex(kanaKey);
            form.setUsr030users(ulist);
        }

        //検索済にする
        form.setUsr030SearchFlg(GSConstUser.SEARCH_ZUMI);
        forward = map.getInputForward();

        con.setAutoCommit(false);
        log__.debug("END");
        return forward;
    }

    /**
     * <p>五十音のリストを作成しフォームにセットする。
     * @param form アクションフォーム
     * @param con コネクション
     * @param req リクエスト
     * @throws Exception 実行例外
     */
    private void __setKanaIndex(Usr030Form form, Connection con,
                                HttpServletRequest req) throws Exception {
        log__.debug("START");

        //五十音の存在するリストを取得
        CmnUsrmDao dao = new CmnUsrmDao(con);
        Map<String, String> ekanas = dao.getExistsKanaIndex(false);

        List<KanaLinkModel> list50 = getKanaLinkList(ekanas, req);
        form.setUsr030ekanas(list50);

        log__.debug("END");
    }

    /**
     * <br>[機  能] 削除ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doDelete(
        ActionMapping map,
        Usr030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //削除時のチェックを行う
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateDelSidCheck(req, con, reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        if (form.getUsr030selectusers().length == 1) {
            //1ユーザ削除
            __doDelInit(map, form, req, res, con);
        }

        return map.findForward("del");
    }
    /**
     * <br>[機  能] 有効ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doYuko(
        ActionMapping map,
        Usr030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //SIDのチェックを行う
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateTargetSidCheck(req, con, "yuko", reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        form.setProcessMode("yuko");
        saveToken(req);
        return map.findForward("yuko");
    }
    /**
     * <br>[機  能] 無効ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doMuko(
        ActionMapping map,
        Usr030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //SIDのチェックを行う
        RequestModel reqMdl = getRequestModel(req);
        ActionErrors errors = form.validateTargetSidCheck(req, con, "muko", reqMdl);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }
        form.setProcessMode("yuko");
        saveToken(req);
        return map.findForward("muko");
    }

    /**
     * <p>カナのリストを作成する
     * @param existsMap 存在カナのマップ
     * @param req リクエスト
     * @return 50音作成のモデル
     */
    private List<KanaLinkModel> getKanaLinkList(Map<String, String> existsMap,
                                                HttpServletRequest req) {
        log__.debug("START");

        List<KanaLinkModel> list50 = new ArrayList<KanaLinkModel>();
        GsMessage gsMsg = new GsMessage();
        /** メッセージ 50音カタカナ **/
        String kana = gsMsg.getMessage(req, "cmn.kana.a")
        + gsMsg.getMessage(req, "cmn.kana.ka")
        + gsMsg.getMessage(req, "cmn.kana.sa")
        + gsMsg.getMessage(req, "cmn.kana.ta")
        + gsMsg.getMessage(req, "cmn.kana.na")
        + gsMsg.getMessage(req, "cmn.kana.ha")
        + gsMsg.getMessage(req, "cmn.kana.ma")
        + gsMsg.getMessage(req, "cmn.kana.ya")
        + gsMsg.getMessage(req, "cmn.kana.ra")
        + gsMsg.getMessage(req, "cmn.kana.wa")
        + gsMsg.getMessage(req, "cmn.kana.i")
        + gsMsg.getMessage(req, "cmn.kana.ki")
        + gsMsg.getMessage(req, "cmn.kana.shi")
        + gsMsg.getMessage(req, "cmn.kana.chi")
        + gsMsg.getMessage(req, "cmn.kana.ni")
        + gsMsg.getMessage(req, "cmn.kana.hi")
        + gsMsg.getMessage(req, "cmn.kana.mi")
        + " "
        + gsMsg.getMessage(req, "cmn.kana.ri")
        + gsMsg.getMessage(req, "cmn.kana.wo")
        + gsMsg.getMessage(req, "cmn.kana.u")
        + gsMsg.getMessage(req, "cmn.kana.ku")
        + gsMsg.getMessage(req, "cmn.kana.su")
        + gsMsg.getMessage(req, "cmn.kana.tsu")
        + gsMsg.getMessage(req, "cmn.kana.nu")
        + gsMsg.getMessage(req, "cmn.kana.fu")
        + gsMsg.getMessage(req, "cmn.kana.mu")
        + gsMsg.getMessage(req, "cmn.kana.yu")
        + gsMsg.getMessage(req, "cmn.kana.ru")
        + gsMsg.getMessage(req, "cmn.kana.n")
        + gsMsg.getMessage(req, "cmn.kana.e")
        + gsMsg.getMessage(req, "cmn.kana.ke")
        + gsMsg.getMessage(req, "cmn.kana.se")
        + gsMsg.getMessage(req, "cmn.kana.te")
        + gsMsg.getMessage(req, "cmn.kana.ne")
        + gsMsg.getMessage(req, "cmn.kana.he")
        + gsMsg.getMessage(req, "cmn.kana.me")
        + " "
        + gsMsg.getMessage(req, "cmn.kana.re")
        + " "
        + gsMsg.getMessage(req, "cmn.kana.o")
        + gsMsg.getMessage(req, "cmn.kana.ko")
        + gsMsg.getMessage(req, "cmn.kana.so")
        + gsMsg.getMessage(req, "cmn.kana.to")
        + gsMsg.getMessage(req, "cmn.kana.no")
        + gsMsg.getMessage(req, "cmn.kana.ho")
        + gsMsg.getMessage(req, "cmn.kana.mo")
        + gsMsg.getMessage(req, "cmn.kana.yo")
        + gsMsg.getMessage(req, "cmn.kana.ro")
        + " ";
        char[] katakana = kana.toCharArray();

        int row = 0;
        for (int i = 0; i < katakana.length; i++) {
            KanaLinkModel kmodel = new KanaLinkModel();
            if ((i % 10) == 0) {
                row += 1;
            }
            kmodel.setRow(Integer.toString(row));
            String tmp = String.valueOf(katakana[i]);
            kmodel.setKana(tmp);
            if (existsMap.get(tmp) == null) {
                kmodel.setExists(false);
            } else {
                kmodel.setExists(true);
            }
            list50.add(kmodel);
        }

        log__.debug("END");
        return list50;
    }

    /**
     * <p>エクスポートボタンクリック時処理
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doExport(ActionMapping map, Usr030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        ActionForward forward = null;

        //確認画面へ
        log__.debug("エクスポート確認画面へ");
        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OKCANCEL);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);
        urlForward = map.findForward("expok");
        cmn999Form.setUrlOK(urlForward.getPath());
        urlForward = map.findForward("expcancel");
        cmn999Form.setUrlCancel(urlForward.getPath());

        cmn999Form.setMessage(msgRes.getMessage("export.kakunin.user"));


        cmn999Form.addHiddenParam("usr030SearchKana", form.getUsr030SearchKana());
        cmn999Form.addHiddenParam("usr030selectusers", form.getUsr030selectusers());

        cmn999Form.addHiddenParam("selectgsid", form.getSelectgsid());
        cmn999Form.addHiddenParam("usr030userId", form.getUsr030userId());
        cmn999Form.addHiddenParam("usr030usrUkoFlg", form.getUsr030usrUkoFlg());
        cmn999Form.addHiddenParam("usr030shainno", form.getUsr030shainno());
        cmn999Form.addHiddenParam("usr030sei", form.getUsr030sei());
        cmn999Form.addHiddenParam("usr030mei", form.getUsr030mei());
        cmn999Form.addHiddenParam("usr030seikn", form.getUsr030seikn());
        cmn999Form.addHiddenParam("usr030meikn", form.getUsr030meikn());
        cmn999Form.addHiddenParam("usr030agefrom", form.getUsr030agefrom());
        cmn999Form.addHiddenParam("usr030ageto", form.getUsr030ageto());
        cmn999Form.addHiddenParam("usr030yakushoku", form.getUsr030yakushoku());
        cmn999Form.addHiddenParam("usr030mail", form.getUsr030mail());
        cmn999Form.addHiddenParam("usr030tdfkCd", form.getUsr030tdfkCd());
        cmn999Form.addHiddenParam("usr030seibetu", form.getUsr030seibetu());
        cmn999Form.addHiddenParam("usr030entranceYearFr", form.getUsr030entranceYearFr());
        cmn999Form.addHiddenParam("usr030entranceMonthFr", form.getUsr030entranceMonthFr());
        cmn999Form.addHiddenParam("usr030entranceDayFr", form.getUsr030entranceDayFr());
        cmn999Form.addHiddenParam("usr030entranceYearTo", form.getUsr030entranceYearTo());
        cmn999Form.addHiddenParam("usr030entranceMonthTo", form.getUsr030entranceMonthTo());
        cmn999Form.addHiddenParam("usr030entranceDayTo", form.getUsr030entranceDayTo());



        cmn999Form.addHiddenParam("selectgsidSave", form.getSelectgsidSave());
        cmn999Form.addHiddenParam("usr030userIdSave", form.getUsr030userIdSave());
        cmn999Form.addHiddenParam("usr030usrUkoFlgSave", form.getUsr030usrUkoFlgSave());
        cmn999Form.addHiddenParam("usr030shainnoSave", form.getUsr030shainnoSave());
        cmn999Form.addHiddenParam("usr030seiSave", form.getUsr030seiSave());
        cmn999Form.addHiddenParam("usr030meiSave", form.getUsr030meiSave());
        cmn999Form.addHiddenParam("usr030seiknSave", form.getUsr030seiknSave());
        cmn999Form.addHiddenParam("usr030meiknSave", form.getUsr030meiknSave());
        cmn999Form.addHiddenParam("usr030agefromSave", form.getUsr030agefromSave());
        cmn999Form.addHiddenParam("usr030agetoSave", form.getUsr030agetoSave());
        cmn999Form.addHiddenParam("usr030yakushokuSave", form.getUsr030yakushokuSave());
        cmn999Form.addHiddenParam("usr030mailSave", form.getUsr030mailSave());
        cmn999Form.addHiddenParam("usr030tdfkCdSave", form.getUsr030tdfkCdSave());
        cmn999Form.addHiddenParam("usr030seibetuSave", form.getUsr030seibetu());
        cmn999Form.addHiddenParam("usr030entranceYearFrSave", form.getUsr030entranceYearFrSave());
        cmn999Form.addHiddenParam("usr030entranceMonthFrSave", form.getUsr030entranceMonthFrSave());
        cmn999Form.addHiddenParam("usr030entranceDayFrSave", form.getUsr030entranceDayFrSave());
        cmn999Form.addHiddenParam("usr030entranceYearToSave", form.getUsr030entranceYearToSave());
        cmn999Form.addHiddenParam("usr030entranceMonthToSave", form.getUsr030entranceMonthToSave());
        cmn999Form.addHiddenParam("usr030entranceDayToSave", form.getUsr030entranceDayToSave());

        cmn999Form.addHiddenParam("usr030cmdMode", form.getUsr030cmdMode());
        cmn999Form.addHiddenParam("usr030SearchFlg", form.getUsr030SearchFlg());

        req.setAttribute("cmn999Form", cmn999Form);

        forward = map.findForward("gf_msg");
        return forward;

    }

    /**
     * エクスポート処理実行
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward 画面遷移先
     * @throws Exception SQL実行時例外
     */
    private ActionForward __doExportOk(ActionMapping map,
                                        Usr030Form form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        //ユーザ情報
        GsMessage gsMsg = new GsMessage(req);
        String textUserInfo = gsMsg.getMessage(req, "user.src.60");

        log__.debug("エクスポート処理実行");

        //テンポラリディレクトリパスを取得
        RequestModel reqMdl = getRequestModel(req);
        UsrCommonBiz usrCmnBiz = new UsrCommonBiz(reqMdl);
        String tempDir = usrCmnBiz.getTempDir(SCR_ID);

        //CSVファイルを作成
        String fileName = UsrCsvWriter.FILE_NAME;
        UsrCsvWriter write = new UsrCsvWriter(req);
        write.outputCsv(con, tempDir);

        //ダウンロード
        String fullPath = tempDir + fileName;
        TempFileUtil.downloadAtachment(req, res, fullPath, fileName, Encoding.UTF_8);

        //TEMPディレクトリ削除
        usrCmnBiz.deleteTempDir(SCR_ID);

        /** メッセージ 削除 **/
        String export = gsMsg.getMessage(req, "cmn.export");

        //ログ出力
        CommonBiz cmnBiz = new CommonBiz();
        cmnBiz.outPutCommonLog(map, reqMdl, gsMsg, con,
                export, GSConstLog.LEVEL_INFO, textUserInfo);

        return null;
    }

    /**
     * <br>[機  能] 詳細検索タブクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception S例外
     * @return ActionForward
     */
    private ActionForward __doShousaiTab(
        ActionMapping map,
        Usr030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        log__.debug("詳細タブクリック");
        form.setUsr030cmdMode(GSConstUser.MODE_SHOUSAI);
        form.setUsr030SearchFlg(GSConstUser.SEARCH_MI);

        //画面に常に表示する値をセットする
        con.setAutoCommit(true);
        Usr030Biz biz = new Usr030Biz(getRequestModel(req));

        Usr030ParamModel paramMdl = new Usr030ParamModel();
        paramMdl.setParam(form);
        biz.setDspData(paramMdl, con);
        paramMdl.setFormData(form);

        con.setAutoCommit(false);

        return map.getInputForward();
    }

    /**
     * <p>詳細検索
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doSearchSyosai(ActionMapping map, Usr030Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;
        log__.debug("詳細検索");

        con.setAutoCommit(true);
        try {

            //画面設定
            Usr030Biz biz = new Usr030Biz(getRequestModel(req));

        form.setUsr030SearchFlg(GSConstUser.SEARCH_MI);
            //画面に常に表示する値をセットする
            Usr030ParamModel paramMdl = new Usr030ParamModel();
            paramMdl.setParam(form);
            biz.setDspData(paramMdl, con);
            paramMdl.setFormData(form);

            if (form.getUsr030DetailSearchFlg()) {
                ActionErrors errors = form.validateSearchSyosai(map, getRequestModel(req), con);
                if (!errors.isEmpty()) {
                    //エラーあり
                    addErrors(req, errors);
                    forward = __doInit(map, form, req, res, con);
                    return forward;
                }
            }
            //エラーなし
            UserSearchDao udao = new UserSearchDao(con);

            //検索条件作成
            ShainSearchModel searchModel = new ShainSearchModel();
            searchModel.setExcludeSysUser(true);

            //検索ボタン未押下時の検索
            if (!form.getUsr030DetailSearchFlg()) {
               //Save用のデータで検索条件抽出
                searchModel.setSelectgsid(form.getSelectgsidSave());
                searchModel.setShainno(form.getUsr030shainnoSave());
                searchModel.setUserId(form.getUsr030userIdSave());
                searchModel.setSei(form.getUsr030seiSave());
                searchModel.setMei(form.getUsr030meiSave());
                searchModel.setSeikn(form.getUsr030seiknSave());
                searchModel.setMeikn(form.getUsr030meiknSave());
                searchModel.setAgefrom(form.getUsr030agefromSave());
                searchModel.setAgeto(form.getUsr030agetoSave());
                searchModel.setYakushoku(form.getUsr030yakushokuSave());
                searchModel.setMail(form.getUsr030mailSave());
                searchModel.setTdfkCd(form.getUsr030tdfkCdSave());
                searchModel.setSeibetu(form.getUsr030seibetuSave());
                searchModel.setUsrUkoFlg(form.getUsr030usrUkoFlgSave());
                UDate date1 = null;

                //年月のみ
                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearFrSave())
                    && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthFrSave())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayFrSave())) {

                    date1 = new UDate();
                    date1.setYear(Integer.parseInt(paramMdl.getUsr030entranceYearFrSave()));
                    date1.setMonth(Integer.parseInt(paramMdl.getUsr030entranceMonthFrSave()));
                    date1.setDay(1);
                    date1.setZeroHhMmSs();
                    searchModel.setEntranceDateFr(date1);
                }

                //年のみ
                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearFrSave())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthFrSave())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayFrSave())) {

                    date1 = new UDate();
                    date1.setYear(Integer.parseInt(paramMdl.getUsr030entranceYearFrSave()));
                    date1.setMonth(1);
                    date1.setDay(1);
                    date1.setZeroHhMmSs();
                    searchModel.setEntranceDateFr(date1);
                }

                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearFrSave())
                      && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthFrSave())
                      && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayFrSave())) {
                    date1 = new UDate();
                    date1.setDate(
                            Integer.parseInt(paramMdl.getUsr030entranceYearFrSave()),
                            Integer.parseInt(paramMdl.getUsr030entranceMonthFrSave()),
                            Integer.parseInt(paramMdl.getUsr030entranceDayFrSave()));
                    date1.setZeroHhMmSs();
                    searchModel.setEntranceDateFr(date1);
                }


                UDate date2 = null;

              //年月のみ
                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearToSave())
                    && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthToSave())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayToSave())) {

                    date2 = new UDate();
                    date2.setYear(Integer.parseInt(paramMdl.getUsr030entranceYearToSave()));
                    date2.setMonth(Integer.parseInt(paramMdl.getUsr030entranceMonthToSave()));
                    date2.setDay(1);
                    date2.setZeroHhMmSs();
                    searchModel.setEntranceDateTo(date2);
                }

                //年のみ
                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearToSave())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthToSave())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayToSave())) {

                    date2 = new UDate();
                    date2.setYear(Integer.parseInt(paramMdl.getUsr030entranceYearToSave()));
                    date2.setMonth(12);
                    date2.setDay(31);
                    date2.setZeroHhMmSs();
                    searchModel.setEntranceDateTo(date2);
                }

                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearToSave())
                     && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthToSave())
                     && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayToSave())) {
                    date2 = new UDate();
                    date2.setDate(
                            Integer.parseInt(paramMdl.getUsr030entranceYearToSave()),
                            Integer.parseInt(paramMdl.getUsr030entranceMonthToSave()),
                            Integer.parseInt(paramMdl.getUsr030entranceDayToSave()));
                    date2.setZeroHhMmSs();
                    searchModel.setEntranceDateTo(date2);
                  }

            //検索ボタン押下時の検索
            } else {
               //検索入力データで検索条件抽出
                searchModel.setSelectgsid(form.getSelectgsid());
                searchModel.setShainno(form.getUsr030shainno());
                searchModel.setUserId(form.getUsr030userId());
                searchModel.setSei(form.getUsr030sei());
                searchModel.setMei(form.getUsr030mei());
                searchModel.setSeikn(form.getUsr030seikn());
                searchModel.setMeikn(form.getUsr030meikn());
                searchModel.setAgefrom(form.getUsr030agefrom());
                searchModel.setAgeto(form.getUsr030ageto());
                searchModel.setYakushoku(form.getUsr030yakushoku());
                searchModel.setMail(form.getUsr030mail());
                searchModel.setTdfkCd(form.getUsr030tdfkCd());
                searchModel.setSeibetu(form.getUsr030seibetu());
                searchModel.setUsrUkoFlg(form.getUsr030usrUkoFlg());

                UDate date1 = null;

                //年月のみ
                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearFr())
                    && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthFr())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayFr())) {

                    date1 = new UDate();
                    date1.setYear(Integer.parseInt(paramMdl.getUsr030entranceYearFr()));
                    date1.setMonth(Integer.parseInt(paramMdl.getUsr030entranceMonthFr()));
                    date1.setDay(1);
                    date1.setZeroHhMmSs();
                    searchModel.setEntranceDateFr(date1);
                }

                //年のみ
                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearFr())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthFr())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayFr())) {

                    date1 = new UDate();
                    date1.setYear(Integer.parseInt(paramMdl.getUsr030entranceYearFr()));
                    date1.setMonth(1);
                    date1.setDay(1);
                    date1.setZeroHhMmSs();
                    searchModel.setEntranceDateFr(date1);
                }

                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearFr())
                      && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthFr())
                      && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayFr())) {
                    date1 = new UDate();
                    date1.setDate(
                            Integer.parseInt(paramMdl.getUsr030entranceYearFr()),
                            Integer.parseInt(paramMdl.getUsr030entranceMonthFr()),
                            Integer.parseInt(paramMdl.getUsr030entranceDayFr()));
                    date1.setZeroHhMmSs();
                    searchModel.setEntranceDateFr(date1);
                }

                UDate date2 = null;

                //年月のみ
                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearTo())
                    && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthTo())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayTo())) {

                    date2 = new UDate();
                    date2.setYear(Integer.parseInt(paramMdl.getUsr030entranceYearTo()));
                    date2.setMonth(Integer.parseInt(paramMdl.getUsr030entranceMonthTo()));
                    date2.setDay(1);
                    date2.setZeroHhMmSs();
                    searchModel.setEntranceDateTo(date2);
                }

                //年のみ
                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearTo())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthTo())
                    && StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayTo())) {

                    date2 = new UDate();
                    date2.setYear(Integer.parseInt(paramMdl.getUsr030entranceYearTo()));
                    date2.setMonth(12);
                    date2.setDay(31);
                    date2.setZeroHhMmSs();
                    searchModel.setEntranceDateTo(date2);
                }

                if (!StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceYearTo())
                     && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceMonthTo())
                     && !StringUtil.isNullZeroStringSpace(paramMdl.getUsr030entranceDayTo())) {
                    date2 = new UDate();
                    date2.setDate(
                            Integer.parseInt(paramMdl.getUsr030entranceYearTo()),
                            Integer.parseInt(paramMdl.getUsr030entranceMonthTo()),
                            Integer.parseInt(paramMdl.getUsr030entranceDayTo()));
                    date1.setZeroHhMmSs();
                    searchModel.setEntranceDateTo(date2);
                  }
            }

            //検索実行
            List<CmnUsrmInfModel> ulist = udao.getSyousaiSearchList(searchModel, 0, 0);
            form.setUsr030users(ulist);

            //検索ボタン押下時に検索条件を保存
            if (form.getUsr030DetailSearchFlg()) {
                form.setSelectgsidSave(form.getSelectgsid()); //グループSID
                form.setUsr030shainnoSave(form.getUsr030shainno()); //社員/職員番号
                form.setUsr030userIdSave(form.getUsr030userId()); //ユーザID
                form.setUsr030usrUkoFlgSave(form.getUsr030usrUkoFlg()); //ユーザID
                form.setUsr030seiSave(form.getUsr030sei()); //姓
                form.setUsr030meiSave(form.getUsr030mei()); //名
                form.setUsr030seiknSave(form.getUsr030seikn()); //セイ
                form.setUsr030meiknSave(form.getUsr030meikn()); //メイ
                form.setUsr030agefromSave(form.getUsr030agefrom()); //年齢FROM
                form.setUsr030agetoSave(form.getUsr030ageto()); //年齢TO
                form.setUsr030yakushokuSave(form.getUsr030yakushoku()); //役職
                form.setUsr030mailSave(form.getUsr030mail()); //メール
                form.setUsr030tdfkCdSave(form.getUsr030tdfkCd()); //都道府県コード
                form.setUsr030seibetuSave(form.getUsr030seibetu()); //性別
                form.setUsr030entranceYearFrSave(form.getUsr030entranceYearFr()); //入社年
                form.setUsr030entranceMonthFrSave(form.getUsr030entranceMonthFr()); //入社月
                form.setUsr030entranceDayFrSave(form.getUsr030entranceDayFr()); //入社日
                form.setUsr030entranceYearToSave(form.getUsr030entranceYearTo()); //入社年
                form.setUsr030entranceMonthToSave(form.getUsr030entranceMonthTo()); //入社月
                form.setUsr030entranceDayToSave(form.getUsr030entranceDayTo()); //入社日
            }

            forward = map.getInputForward();
        } finally {
            con.setAutoCommit(false);
        }

        //検索済にする
        form.setUsr030SearchFlg(GSConstUser.SEARCH_ZUMI);
        return forward;
    }

    /**
     * <br>[機  能] 氏名タブクリック時の処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception S例外
     * @return ActionForward
     */
    private ActionForward __doNameTab(
        ActionMapping map,
        Usr030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //カナインデックス作成
        con.setAutoCommit(true);
        __setKanaIndex(form, con, req);
        con.setAutoCommit(false);

        form.setUsr030cmdMode(GSConstUser.MODE_NAME);

        form.setUsr030SearchFlg(GSConstUser.SEARCH_MI);

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 修正ボタンクリック時の処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward フォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doEdit(
        ActionMapping map,
        Usr030Form form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {

        //修正時のチェックを行う
        ActionErrors errors = form.validateEdit(req, con);
        if (!errors.isEmpty()) {
            addErrors(req, errors);
            return __doInit(map, form, req, res, con);
        }

        return map.findForward("edit");
    }
}