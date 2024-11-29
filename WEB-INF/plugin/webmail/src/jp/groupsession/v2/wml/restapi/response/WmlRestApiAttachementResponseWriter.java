package jp.groupsession.v2.wml.restapi.response;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.log.LogBlock;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.WmlTempfileModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;
import jp.groupsession.v2.wml.biz.WmlBiz;

public class WmlRestApiAttachementResponseWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlRestApiAttachementResponseWriter.class);

    /** 書き込み先レスポンス*/
    private HttpServletResponse res__;
    /** リクエスト*/
    private HttpServletRequest req__;
    /** RestApiコンテキスト*/
    private RestApiContext ctx__;
    /** バイナリSID */
    private long binSid__;
    /** メールSID */
    private long mailSid__;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考] 
     *
     * @author JTS
     */
    private WmlRestApiAttachementResponseWriter() {}

    /**
     * <br>[機  能] パラメータを設定
     * <br>[解  説]
     * <br>[備  考] バイナリSIDを設定
     *
     * @author JTS
     */
    public static void execute(
        HttpServletResponse res,
        HttpServletRequest req,
        RestApiContext ctx,
        long mailSid,
        long binSid) {

        WmlRestApiAttachementResponseWriter executer = new WmlRestApiAttachementResponseWriter();
        executer.res__ = res;
        executer.req__ = req;
        executer.ctx__ = ctx;
        executer.mailSid__ = mailSid;
        executer.binSid__ = binSid;
        executer.__downloadFile();
    }

    /**
     * <br>[機  能] 添付ファイルダウンロードを実行
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private void __downloadFile() {

        try (LogBlock lb = LogBlock.builder("RestApi outputWmlAttachment", log__).build()) {
            Connection con = ctx__.getCon();
            //DBよりファイル情報を取得する。
            WmlBiz wmlBiz = new WmlBiz();
            WmlTempfileModel wtfModel;
            try {
                wtfModel = wmlBiz.getTempFileData(con, mailSid__, binSid__, ctx__.getRequestModel());
            } catch (TempFileException e) {
                throw new RuntimeException("添付ファイルダウンロード実行例外");
            }
            if (wtfModel == null
                    || wtfModel.getWtfJkbn() == GSConstWebmail.TEMPFILE_STATUS_DELETE) {
                //ファイルが存在しないか、削除されているか場合
                throw new RestApiPermissionException(
                        ReasonCode.EnumError.IMPERMISSIBLE,
                        "error.input.notfound.file");
            }
            //時間のかかる処理の前にコネクションを破棄
            JDBCUtil.closeConnectionAndNull(con);
            //ファイルのダウンロード
            String charset = null;
            if (!StringUtil.isNullZeroString(wtfModel.getWtfCharset())) {
                charset = wtfModel.getWtfCharset();
            }
            try {
                TempFileUtil.downloadAtachmentForWebmail(
                        req__, res__, wtfModel, ctx__.getAppRootPath(), charset);
            } catch (Exception e) {
                //レスポンスアウトプット時のエクセプションは処理しない
            }
        }
    }
}