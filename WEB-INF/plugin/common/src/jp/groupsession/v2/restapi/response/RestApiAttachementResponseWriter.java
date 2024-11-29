package jp.groupsession.v2.restapi.response;

import java.io.File;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.log.LogBlock;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.restapi.exception.ReasonCode;
import jp.groupsession.v2.restapi.exception.RestApiPermissionException;

/**
 * <br>[機  能] RestApiのレスポンスとして添付ファイルをダウンロードするビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RestApiAttachementResponseWriter {

    /** Logging インスタンス */
    protected static Log log__ = LogFactory.getLog(RestApiAttachementResponseWriter.class);

    /** 書き込み先レスポンス*/
    private HttpServletResponse res__;
    /** リクエスト*/
    private HttpServletRequest req__;
    /** RestApiコンテキスト*/
    private RestApiContext ctx__;
    /** バイナリSID */
    private long binSid__;
    /** 出力対象ファイル*/
    private File file__;
    /** 出力対象ファイル名*/
    private String fileName__;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考] 
     *
     * @author JTS
     */
    private RestApiAttachementResponseWriter() {}

    /**
     * <br>[機  能] パラメータを設定し、ダウンロードを実行
     * <br>[解  説]
     * <br>[備  考] バイナリSIDを設定
     *
     * @author JTS
     */
    public static void execute(
        HttpServletResponse res,
        HttpServletRequest req,
        RestApiContext ctx,
        long binSid) {
        
        RestApiAttachementResponseWriter executer = new RestApiAttachementResponseWriter();
        executer.res__ = res;
        executer.req__ = req;
        executer.ctx__ = ctx;
        executer.binSid__ = binSid;
        executer.file__ = null;
        executer.fileName__ = null;
        executer.__downloadFile();
    }

    /**
     * <br>[機  能] パラメータを設定し、ダウンロードを実行
     * <br>[解  説]
     * <br>[備  考] ファイルを設定
     *
     * @author JTS
     */
    public static void execute(
        HttpServletResponse res,
        HttpServletRequest req,
        RestApiContext ctx,
        File file) {
        
        RestApiAttachementResponseWriter executer = new RestApiAttachementResponseWriter();
        executer.res__ = res;
        executer.req__ = req;
        executer.ctx__ = ctx;
        executer.binSid__ = -1;
        executer.file__ = file;
        executer.fileName__ = null;
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

        try (LogBlock lb = LogBlock.builder("RestApi outputAttachment", log__).build()) {
            CommonBiz cmnBiz = new CommonBiz();
            Connection con = ctx__.getCon();
            if (file__ == null) {
                //DBよりファイル情報を取得する。
                CmnBinfModel binModel;
                try {
                    binModel = cmnBiz.getBinInfo(con, binSid__,
                            ctx__.getRequestModel().getDomain());
                } catch (TempFileException e) {
                    throw new RuntimeException("添付ファイル操作例外");
                }
                if (binModel == null
                        || binModel.getBinJkbn() == GSConst.JTKBN_DELETE) {
                    //ファイルが存在しないか、削除されているか場合
                    throw new RestApiPermissionException(
                            ReasonCode.EnumError.IMPERMISSIBLE,
                            "error.input.notfound.file");
                }
                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);
                //ファイルのダウンロード
                try {
                    TempFileUtil.downloadAtachment(
                            req__, res__, binModel, ctx__.getAppRootPath(), Encoding.UTF_8);
                } catch (Exception e) {
                    //レスポンスアウトプット時のエクセプションは処理しない
                }
            } else {
                //時間のかかる処理の前にコネクションを破棄
                JDBCUtil.closeConnectionAndNull(con);
                //ファイルのダウンロード
                try {
                TempFileUtil.downloadAtachment(
                        req__, res__, file__,
                        NullDefault.getString(fileName__, file__.getName()),
                        Encoding.UTF_8);
                } catch (Exception e) {
                    //レスポンスアウトプット時のエクセプションは処理しない
                }
            }
        }
    }
}