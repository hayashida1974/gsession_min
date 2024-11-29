package jp.groupsession.v2.cmn.formmodel.action;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
/**
*
* <br>[機  能] 添付ファイル削除 アクション
* <br>[解  説]
* <br>[備  考]
*
* @author JTS
*/
public class TempFileDeleteAction extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(TempFileDeleteAction.class);

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        TempFileDeleteForm thisForm = (TempFileDeleteForm) form;

        ActionForward forward = null;

        forward = __doInit(map, thisForm, req, res, con);

        return forward;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return ActionForward
     */
    private ActionForward __doInit(
        ActionMapping map,
        TempFileDeleteForm form,
        HttpServletRequest req,
        HttpServletResponse res,
        Connection con) throws Exception {


        RequestModel reqMdl = getRequestModel(req);

        // サーバで仕様可能なプラグイン一覧と指定されたプラグイン名を比較
        PluginConfig pconfig = getPluginConfig(req);
        if (StringUtil.isNullZeroString(form.getPlugin())
         || pconfig.getPlugin(form.getPlugin()) == null) {
            return null; // 使用可能なプラグインが指定されていない為、ここで終了
        }
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();

        String tempDir  = pathUtil.getTempPath(reqMdl, form.getPlugin(),
                form.getTempDirId(), form.getSubDir()
                );

        log__.debug("テンポラリディレクトリ = " + tempDir);

        if (form.getFiles() != null && form.getFiles().length > 0) {
            log__.debug("削除ファイル数 = " + form.getFiles().length);

            //選択された添付ファイルを削除する
            pathUtil.deleteFile(form.getFiles(), reqMdl, form.getPlugin(),
                    form.getTempDirId(), form.getSubDir());
        }

        //添付ファイル一覧を設定
        //テンポラリディレクトリパスを取得
        CommonBiz cmnBiz = new CommonBiz();
        List<LabelValueBean> fileList = cmnBiz.getTempFileLabelList(tempDir);

        PrintWriter writer = null;
        try {
            if (fileList != null) {
                res.setContentType("text/txt; charset=UTF-8");
                writer = res.getWriter();
                writer.println("{");
                for (int i = 0; i < fileList.size(); i++) {
                    LabelValueBean bean = fileList.get(i);
                    String fileStr = "\"" + bean.getValue() + "\" : \"" + bean.getLabel() + "\"";
                    if (i > 0) {
                        writer.println("," + fileStr);
                    } else {
                        writer.println(fileStr);
                    }
                }
                writer.println("}");
                writer.flush();
            }
        } catch (Exception e) {
            log__.debug("書き込みに失敗。");
        } finally {
            if (writer != null) {
                writer.close();
            }
        }

        return null;
    }
}
