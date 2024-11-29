package jp.groupsession.v2.cmn.cmn100;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.qrcode.QrAddressModel;
import jp.co.sjts.util.qrcode.QrCodeGenerator;
import jp.co.sjts.util.qrcode.QrUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.AbstractGsAction;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] ユーザ情報ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn100Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn100Action.class);

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

        if (cmd.equals("getImageFile") || cmd.equals("getQrDocomo")
                || cmd.equals("getQrAu") || cmd.equals("getQrSoftBank")) {
            return true;

        }
        return false;
    }

    /**
     * <br>[機  能] アクション実行
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
    public ActionForward executeAction(ActionMapping map,
                                         ActionForm form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws Exception {

        ActionForward forward = null;
        Cmn100Form thisForm = (Cmn100Form) form;

        //パラメータからユーザSIDをセット
        thisForm.setCmn100usid(req.getParameter("userSid"));
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        //フォームアンロード
        if (cmd.equals("formUnload")) {
//            log__.debug("フォームアンロード");
//            __doTempDirDelete(map, thisForm, req, res, con);
        //写真画像ダウンロード
        } else if (cmd.equals("getImageFile")) {
            log__.debug("写真画像ダウンロード");
//            Cmn100Biz biz = new Cmn100Biz();
//            //所属グループ名をセットします
//            biz.setGrpName(thisForm, con);
            __doGetImageFile(map, thisForm, req, res, con);
            forward = null;
        } else if (cmd.equals("getQrDocomo")) {
            //QRコード表示 Docomo
            __doGetQrCode(map, thisForm, req, res, con, 1);
            forward = null;
        } else if (cmd.equals("getQrAu")) {
            //QRコード表示 au
            __doGetQrCode(map, thisForm, req, res, con, 2);
            forward = null;
        } else if (cmd.equals("getQrSoftBank")) {
            //QRコード表示 SoftBank
            __doGetQrCode(map, thisForm, req, res, con, 3);
            forward = null;
        //初期表示
        } else {
            log__.debug("初期表示");
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <br>[機  能] 初期表示処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                     Cmn100Form form,
                                     HttpServletRequest req,
                                     HttpServletResponse res,
                                     Connection con)
    throws Exception {

        con.setAutoCommit(true);
        try {

            GsMessage gsMsg = new GsMessage(req);
            //ユーザ情報をセットします
            Cmn100ParamModel paramModel = new Cmn100ParamModel();
            paramModel.setParam(form);

            //アプリケーションのルートパス
            String appRootPath = getAppRootPath();
            //テンポラリディレクトリパス
            String rootDir = getTempPath(req);

            //インスタンスを生成
            Cmn100Biz biz = new Cmn100Biz(gsMsg);

            int ecode = biz.setInit(paramModel, appRootPath, rootDir
                    , getRequestModel(req), con, getServlet().getServletContext());

            //ユーザが存在しない
            if (ecode == Cmn100Biz.INIT_ERR_NOTFOUND) {
                return __setRegistErr(map, req);
            } else if (ecode == Cmn100Biz.INIT_ERR_DELETED) {
                //状態区分が正常ではない場合
                form.setCmn100usrJkbn(String.valueOf(GSConstUser.USER_JTKBN_DELETE));
                return map.getInputForward();
            } else {
                paramModel.setFormData(form);
                return map.getInputForward();
            }
        } finally {
            con.setAutoCommit(false);
        }
    }

    /**
     * <br>[機  能] 検索結果が無い場合のエラー画面処理と遷移
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param req リクエスト
     * @return アクションフォワード
     */
    private ActionForward __setRegistErr(
                ActionMapping map,
                HttpServletRequest req) {

        Cmn999Form cmn999Form = new Cmn999Form();
        MessageResources msgRes = getResources(req);
        cmn999Form.setMessage(
                msgRes.getMessage("error.search.notfound.user"));

        //ポップアップフラグをTRUEに
        cmn999Form.setType_popup(Cmn999Form.POPUP_TRUE);

        req.setAttribute("cmn999Form", cmn999Form);

        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] QRコードイメージを返す
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @param qcr QR生成対象のキャリア
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetQrCode(ActionMapping map,
                                            Cmn100Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con,
                                            int qcr
                                            )
        throws Exception {

        //ユーザSIDをセット
        CmnUsrmInfModel model = new CmnUsrmInfModel();
        model.setUsrSid(
                Integer.parseInt(
                        NullDefault.getString(form.getCmn100usid(), "-1")));

        con.setAutoCommit(true);
        CmnUsrmInfDao dao = new CmnUsrmInfDao(con);
        model = dao.select(model);
        con.setAutoCommit(false);

        if (model == null) {
            return null;
        }

        //バージョン
        int version = 5;
        QrAddressModel adata = new QrAddressModel();
        adata.setNameSei(model.getUsiSei());
        adata.setNameMei(model.getUsiMei());
        adata.setNameSeiKn(model.getUsiSeiKn());
        adata.setNameMeiKn(model.getUsiMeiKn());
        //TEL1
        if (model.getUsiTel1Kf() == GSConstUser.INDIVIDUAL_INFO_OPEN) {
            if (!ValidateUtil.isEmpty(model.getUsiTel1())) {
                adata.setTel1(model.getUsiTel1());
            }
        }
        //TEL2
        if (model.getUsiTel2Kf() == GSConstUser.INDIVIDUAL_INFO_OPEN) {
            if (!ValidateUtil.isEmpty(model.getUsiTel2())) {
                adata.setTel2(model.getUsiTel2());
            }
        }
        //TEL3
        if (model.getUsiTel3Kf() == GSConstUser.INDIVIDUAL_INFO_OPEN) {
            if (!ValidateUtil.isEmpty(model.getUsiTel3())) {
                adata.setTel3(model.getUsiTel3());
            }
        }
        //MAIL1
        if (model.getUsiMail1Kf() == GSConstUser.INDIVIDUAL_INFO_OPEN) {
            if (!ValidateUtil.isEmpty(model.getUsiMail1())) {
                adata.setMail1(model.getUsiMail1());
            }
        }
        //MAIL2
        if (model.getUsiMail2Kf() == GSConstUser.INDIVIDUAL_INFO_OPEN) {
            if (!ValidateUtil.isEmpty(model.getUsiMail2())) {
                adata.setMail2(model.getUsiMail2());
            }
        }
        //MAIL3
        if (model.getUsiMail3Kf() == GSConstUser.INDIVIDUAL_INFO_OPEN) {
            if (!ValidateUtil.isEmpty(model.getUsiMail3())) {
                adata.setMail3(model.getUsiMail3());
            }
        }

        //QR生成インスタンス
        QrCodeGenerator qt = new QrCodeGenerator();
        qt.setQrversion(version);

        //ファイル名
        String fileName = model.getUsrSid() + "_" + qcr + ".gif";

        //QR化文字列生成
        String buf = null;
        buf = __createCrString(qcr, adata);

        //QRバージョンチェック
        int nversion = version;
        if (!QrUtil.isVersionOkM10(version, buf)) {
            //文字数が多い場合バージョンを変える
            nversion = QrUtil.getBestVersionM10(buf);
            qt.setQrversion(nversion);
        }

        //QRバージョンが-1だった場合
        if (nversion == -1) {
            buf = __reCreateCrString(qcr, adata);
            if (!StringUtil.isNullZeroString(buf)) {
                nversion = QrUtil.getBestVersionM10(buf);
                qt.setQrversion(nversion);
            } else {
                //ルール７ 画像を出力しない（spacer.gifを表示する）
                OutputStream out = null;
                try {
                    String appRootPath = IOTools.setEndPathChar(getAppRootPath());
                    String filePath = IOTools.replaceFileSep(appRootPath
                            + "common/images/spacer.gif");
                    File file = new File(filePath);
                    if (file.exists()) {
                        BufferedImage jpegIimage =  ImageIO.read(file);
                        out = new BufferedOutputStream(res.getOutputStream());
                        ImageIO.write(jpegIimage, "gif", out);
                    }
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
                log__.info("QRコード文字列が長すぎるためQRコード生成不可（ルール７）");
                return null;
            }
        }

        try {
//            qt.create(buf, qrFilePath, "gif");
            qt.downloadInline(buf, req, res, fileName, "gif", Encoding.UTF_8);
        } catch (Exception e) {
            log__.error("不明なエラー", e);
            return null;
        }
        return null;
    }

    /**
     * <br>[機  能] 各キャリアに対応した文字列を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param qcr キャリア
     * @param adata 対象データ
     * @return 文字列
     */
    private String __createCrString(int qcr, QrAddressModel adata) {
        String buf = null;
        if (qcr == 1) {
            //Docomo
            buf = QrUtil.toDocomoAddressString(adata);
        } else if (qcr == 2) {
            buf = QrUtil.toAuAddressString(adata);
        } else {
            buf = QrUtil.toSoftBankAddressString(adata);
        }
        return buf;
    }

    /**
     * <br>[機  能] 画像を読み込む
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param map マップ
     * @param form フォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con Connection
     * @return ActionForward フォワード
     * @throws Exception 実行時例外
     */
    private ActionForward __doGetImageFile(ActionMapping map,
                                            Cmn100Form form,
                                            HttpServletRequest req,
                                            HttpServletResponse res,
                                            Connection con)
        throws Exception {


        //画像情報取得
        if (form.getCmn100binSid() > 0) {

            CommonBiz cmnBiz = new CommonBiz();

            //指定したbinSidがユーザ画像かチェックする。
            if (cmnBiz.isCheckUserImage(con, form.getCmn100binSid(), getSessionUserSid(req))) {

                CmnBinfModel cbMdl = cmnBiz.getBinInfo(con, form.getCmn100binSid(),
                        GroupSession.getResourceManager().getDomain(req));

                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);

                //ファイルをダウンロードする
                TempFileUtil.downloadInline(req, res, cbMdl, getAppRootPath(), Encoding.UTF_8);
            }

        }
        return null;
    }

    /**
     * <br>[機  能] 条件に従って表示可能な文字列を取得する。
     * <br>[解  説] 下記ルールに従ってカットする
     *        1. カナを1文字にする
     *        2. メールアドレス3を削る
     *        3. メールアドレス2を削る
     *        4. 電話番号3を削る
     *        5. 電話番号2を削る
     *        6. 名を削る
     *        7. 画像を出力しない（ログにinfoレベルで生成できないログを残す、spacer.gifを返す）
     *
     * <br>[備  考]
     * @param qcr キャリア
     * @param adata 対象データ
     * @return 文字列
     */
    private String __reCreateCrString(int qcr, QrAddressModel adata) {

        //ルール１
        String buf = null;
        if (qcr == 1) {
            //Docomo
            buf = QrUtil.toDocomoAddressStringRule1(adata);
        } else if (qcr == 2) {
            buf = QrUtil.toAuAddressStringRule1(adata);
        } else {
            buf = QrUtil.toSoftBankAddressStringRule1(adata);
        }

        if (__checkCrStringByteLength(buf)) {
            log__.info("QRコード文字列 ルール１適応");
            return buf;
        }

        //ルール２
        buf = null;
        if (qcr == 1) {
            //Docomo
            buf = QrUtil.toDocomoAddressStringRule2(adata);
        } else if (qcr == 2) {
            buf = QrUtil.toAuAddressStringRule2(adata);
        } else {
            buf = QrUtil.toSoftBankAddressStringRule2(adata);
        }

        if (__checkCrStringByteLength(buf)) {
            log__.info("QRコード文字列 ルール２適応");
            return buf;
        }

        //ルール３
        buf = null;
        if (qcr == 1) {
            //Docomo
            buf = QrUtil.toDocomoAddressStringRule3(adata);
        } else if (qcr == 2) {
            buf = QrUtil.toAuAddressStringRule3(adata);
        } else {
            buf = QrUtil.toSoftBankAddressStringRule3(adata);
        }

        if (__checkCrStringByteLength(buf)) {
            log__.info("QRコード文字列 ルール３適応");
            return buf;
        }

        //ルール４
        buf = null;
        if (qcr == 1) {
            //Docomo
            buf = QrUtil.toDocomoAddressStringRule4(adata);
        } else if (qcr == 2) {
            buf = QrUtil.toAuAddressStringRule4(adata);
        } else {
            buf = QrUtil.toSoftBankAddressStringRule4(adata);
        }

        if (__checkCrStringByteLength(buf)) {
            log__.info("QRコード文字列 ルール４適応");
            return buf;
        }

        //ルール５
        buf = null;
        if (qcr == 1) {
            //Docomo
            buf = QrUtil.toDocomoAddressStringRule5(adata);
        } else if (qcr == 2) {
            buf = QrUtil.toAuAddressStringRule5(adata);
        } else {
            buf = QrUtil.toSoftBankAddressStringRule5(adata);
        }

        if (__checkCrStringByteLength(buf)) {
            log__.info("QRコード文字列 ルール５適応");
            return buf;
        }

        //ルール６
        buf = null;
        if (qcr == 1) {
            //Docomo
            buf = QrUtil.toDocomoAddressStringRule6(adata);
        } else if (qcr == 2) {
            buf = QrUtil.toAuAddressStringRule6(adata);
        } else {
            buf = QrUtil.toSoftBankAddressStringRule6(adata);
        }

        if (__checkCrStringByteLength(buf)) {
            log__.info("QRコード文字列 ルール６適応");
            return buf;
        }


        return null;
    }

    /**
     * <br>[機  能] QRコードの字数チェックを行う
     * <br>[解  説]
     * <br>[備  考] 誤り訂正レベルM バージョン10
     * @param buf 文字列
     * @return true:可 false:不可
     */
    private boolean __checkCrStringByteLength(String buf) {

        byte[] b = buf.getBytes();
        int size = b.length;

        //誤り訂正レベルM バージョン10の時の最大
        if (size <= 213) {
            return true;
        } else {
            return false;
        }
    }
}