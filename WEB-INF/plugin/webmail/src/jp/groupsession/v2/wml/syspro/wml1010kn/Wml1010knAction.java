package jp.groupsession.v2.wml.syspro.wml1010kn;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.wml.AbstractWebmailAction;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlLabelBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlDirectoryDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.model.WmlReceiveServerModel;
import jp.groupsession.v2.wml.model.base.AccountDataModel;
import jp.groupsession.v2.wml.model.base.LabelDataModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;
import jp.groupsession.v2.wml.pop3.Pop3Receive;
import jp.groupsession.v2.wml.pop3.model.Pop3ReceiveModel;
import jp.groupsession.v2.wml.wml110.Wml110Dao;
import jp.co.sjts.util.date.UDate;

/**
 * <br>[機  能] WEBメール メールインポート確認画面のアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml1010knAction extends AbstractWebmailAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml1010knAction.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map,
                                        ActionForm form,
                                        HttpServletRequest req,
                                        HttpServletResponse res,
                                        Connection con)
        throws Exception {

        ActionForward forward = null;
        Wml1010knForm uform = (Wml1010knForm) form;

        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");

        if (cmd.equals("backInput")) {
            //戻るボタン押下
            log__.debug("戻るボタン押下");
            forward = map.findForward("backInput");
        } else if (cmd.equals("doImp")) {
            //実行ボタン押下
            log__.debug("実行ボタン押下");
            forward = __doImport(map, uform, req, res, con);
        } else {
            //初期表示
            log__.debug("初期表示処理");
            forward = __doInit(map, uform, req, res, con);
        }

        return forward;
    }


    /**
     * <br>[機  能] 確認画面の表示
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return アクションフォーワード
     * @throws Exception 実行例外
     */
    private ActionForward __doInit(ActionMapping map,
                                    Wml1010knForm form,
                                    HttpServletRequest req,
                                    HttpServletResponse res,
                                    Connection con)
        throws Exception {

        //リクエストモデル取得
        RequestModel reqModel = getRequestModel(req);

        //アカウント名を取得
        WmlAccountDao wmlAccountDao = new WmlAccountDao(con);
        WmlAccountModel wmlAccountModel = wmlAccountDao.select(form.getWml1010accountSid());
        String accountName = String.format("%s（%s）", wmlAccountModel.getWacName(), wmlAccountModel.getWacAddress());

        //ラベルリストを取得
        WmlLabelDao wmlLabelDao = new WmlLabelDao(con);
        String labelName = wmlLabelDao.getLabelName(form.getWml1010labelSid());

        //テンポラリディレクトリパスを取得
        WmlBiz wmlBiz = new WmlBiz();
        String tempDir = wmlBiz.getTempDir(reqModel, "wml1010");

        //初期表示情報を画面にセットする
        form.setWml1010knAccountString(accountName);
        form.setWml1010knLabelString(labelName);
        form.setWml1010knMailList(__getMessages(tempDir));

        return map.getInputForward();
    }

    /**
     * <br>[機  能] 添付ファイルのメール情報を取得します。
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir 添付ディレクトリPATH
     * @return List<Wml1010knMailModel> メール情報
     * @throws IOToolsException 添付ファイルへのアクセスに失敗
     */
    private List<Wml1010knMailModel> __getMessages(String tempDir) throws IOToolsException {
        List<Wml1010knMailModel> ret = new ArrayList<Wml1010knMailModel>();
        Enumeration<File> files = IOTools.getFiles(tempDir);
        if (files != null) {
        	while (files.hasMoreElements()) {
        		File emlFile = files.nextElement();
        		String fileName = emlFile.getName();
                if (fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

        		InputStream inputStream = null;
        		try {
            		inputStream = new FileInputStream(emlFile);
            		Session session = Session.getDefaultInstance(new Properties());
            		Message message = new MimeMessage(session, inputStream);
                    if (message != null) {
                    	Wml1010knMailModel mail = new Wml1010knMailModel();
                    	mail.setImport(1);
                    	mail.setFileName(emlFile.getName());
                    	//差出人取得
                    	String from = ((InternetAddress) message.getFrom()[0]).getPersonal();
                    	if (from == null || from.isBlank()) {
                    		from = ((InternetAddress) message.getFrom()[0]).getAddress();
                    	}
                    	mail.setFrom(from);
                    	//件名取得
                    	mail.setSubject(message.getSubject());
                    	//送信日取得
                    	mail.setDate(UDate.getInstanceDate(message.getSentDate()));
                    	//添付ファイル有無取得
                        if (message.isMimeType("multipart/*")) {
                            Multipart multipart = (Multipart) message.getContent();
                            for (int i = 0; i < multipart.getCount(); i++) {
                                BodyPart bodyPart = multipart.getBodyPart(i);
                                if (Part.ATTACHMENT.equalsIgnoreCase(bodyPart.getDisposition()) ||
                                    bodyPart.getFileName() != null) {
                                	mail.setAttach(true);
                                }
                            }
                        }
                        //サイズ
                        mail.setSize(message.getSize());

                    	ret.add(mail);
                    }
        		}catch(Exception e) {
        		}finally {
        			try {
            			if (inputStream != null) {
                            inputStream.close();
            			}
        			}catch(Exception e) {
        			}
        		}
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] ユーザインポート処理
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーワード
     */
    private ActionForward __doImport(ActionMapping map,
    		                          Wml1010knForm form,
                                      HttpServletRequest req,
                                      HttpServletResponse res,
                                      Connection con)
        throws Exception {

        if (!isTokenValid(req, true)) {
            log__.info("２重投稿");
            return getSubmitErrorPage(map, req);
        }

        //GroupSession共通情報取得
        GSContext gsContext = getGsContext();
        //リクエストモデル取得
        RequestModel reqModel = getRequestModel(req);

        //テンポラリディレクトリパスを取得
        WmlBiz wmlBiz = new WmlBiz();
        String tempDir = wmlBiz.getTempDir(reqModel, "wml1010");

        //ログインユーザSIDを取得
        int userSid = 0;
        BaseUserModel buMdl = getSessionUserModel(req);
        if (buMdl != null) {
            userSid = buMdl.getUsrsid();
        }

        Pop3Receive receive = new Pop3Receive();
        String domain = reqModel.getDomain();
        WmlDirectoryDao directoryDao = new WmlDirectoryDao(con);

        //insertMailDataメソッド呼び出しの為の関連情報設定
        Pop3ReceiveModel pop3Model = new Pop3ReceiveModel();
        pop3Model.setCon(con);
        pop3Model.setMtCon(GroupSession.getResourceManager().getCountController(domain));
        int wacSid = form.getWml1010accountSid();
        pop3Model.setWacSid(wacSid);
        pop3Model.setSaveWdrSid(form.getWml1010saveWdrSid());
        pop3Model.setDustWdrSid(directoryDao.getDirSid(wacSid, GSConstWebmail.DIR_TYPE_DUST));
        pop3Model.setStrageWdrSid(directoryDao.getDirSid(wacSid, GSConstWebmail.DIR_TYPE_STORAGE));
        pop3Model.setAppRootPath((String)gsContext.get(GSContext.APP_ROOT_PATH));
        pop3Model.setFileSaveDir(__getTempDirPath(wacSid, domain));
        pop3Model.setUserSid(userSid);

        WmlReceiveServerModel receiveServerModel = new WmlReceiveServerModel();
        MessageResources msgResource = (MessageResources) getGsContext().get(GSContext.MSG_RESOURCE);

        int labelSid = form.getWml1010labelSid();

        //取込み処理
        boolean commit = false;
        try {
            Enumeration<File> files = IOTools.getFiles(tempDir);
            if (files != null) {
            	while (files.hasMoreElements()) {
            		File emlFile = files.nextElement();

            		String fileName = emlFile.getName();
                    if (fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                        continue;
                    }

            		InputStream inputStream = null;
            		try {
                		inputStream = new FileInputStream(emlFile);
                		Session session = Session.getDefaultInstance(new Properties());
                		Message message = new MimeMessage(session, inputStream);
                        if (message != null) {
                        	//メール登録
                        	receive.insertMailData(
                                    domain,
                                    pop3Model,
                                    receiveServerModel,
                                    (MimeMessage) message,
                                    msgResource);
                        }
            		}catch(Throwable e) {
            			throw new Exception(e);
            		}finally {
            			try {
                			if (inputStream != null) {
                                inputStream.close();
                			}
            			}catch(Exception e) {
            			}
            		}
                }
            	//ラベル追加
            	if (labelSid != -1) {
            		List<Long> mailSidList = receive.getMailSidList();
            		long[] mailSidArray = mailSidList.stream().mapToLong(Long::longValue).toArray();
            		wmlBiz.addLabelToMail(con, wacSid, mailSidArray, labelSid);
            	}
            }
            commit = true;
        } catch (Exception e) {
            log__.error("メールファイルの取り込みに失敗しました。" + e);
            throw e;
        } finally {
            if (commit) {
                con.commit();
            } else {
                con.rollback();
            }
            //テンポラリディレクトリのファイル削除を行う
            wmlBiz.deleteTempDir(reqModel, "wml1010");
        }

        //完了画面遷移
        __setCompPageParam(map, req, form);
        return map.findForward("gf_msg");
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得
     * <br>[解  説]
     * <br>[備  考]
     * @param wacSid アカウントSID
     * @param domain ドメイン
     * @return テンポラリディレクトリパス
     */
    private String __getTempDirPath(int wacSid, String domain) {

        String tempDir = GroupSession.getResourceManager().getTempPath(domain);
        tempDir += "/";
        tempDir += GSConstWebmail.PLUGIN_ID_WEBMAIL;
        tempDir += "/import/";
        tempDir += wacSid;
        tempDir += "/";

        return IOTools.replaceFileSep(tempDir.toString());
    }

    /**
     * [機  能] 登録完了画面のパラメータセット<br>
     * [解  説] <br>
     * [備  考] <br>
     * @param map マッピング
     * @param req リクエスト
     * @param form フォーム
     * @param msgState 完了画面に表示するメッセージのキー
     */
    private void __setCompPageParam(ActionMapping map,
                               HttpServletRequest req,
                               Wml1010knForm form) {

        Cmn999Form cmn999Form = new Cmn999Form();
        ActionForward urlForward = null;

        //権限エラー警告画面パラメータの設定
        MessageResources msgRes = getResources(req);
        cmn999Form.setType(Cmn999Form.TYPE_OK);
        cmn999Form.setIcon(Cmn999Form.ICON_INFO);
        cmn999Form.setWtarget(Cmn999Form.WTARGET_BODY);

        urlForward = map.findForward("psnTool");
        cmn999Form.setUrlOK(urlForward.getPath());

        cmn999Form.setMessage(msgRes.getMessage(
        		"touroku.kanryo.object",
                "メールファイル"));

        req.setAttribute("cmn999Form", cmn999Form);
    }
}