package jp.groupsession.v2.cmn;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 各画面の添付ファイル領域を表記する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class AttachmentAreaTag extends TagSupport {
    /** ロギングクラス */
    public static Log log__ = LogFactory.getLog(AttachmentAreaTag.class);

    /**
     * The key to search default format string for java.sql.Timestamp in resources.
     */
    public static final String SQL_TIMESTAMP_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.sql.timestamp";

    /**
     * The key to search default format string for java.sql.Date in resources.
     */
    public static final String SQL_DATE_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.sql.date";

    /**
     * The key to search default format string for java.sql.Time in resources.
     */
    public static final String SQL_TIME_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.sql.time";

    /**
     * The key to search default format string for java.util.Date in resources.
     */
    public static final String DATE_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.date";

    /**
     * The key to search default format string for int (byte, short, etc.) in resources.
     */
    public static final String INT_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.int";

    /**
     * The key to search default format string for float (double, BigDecimal) in
     * resources.
     */
    public static final String FLOAT_FORMAT_KEY =
        "org.apache.struts.taglib.bean.format.float";

    /**
     * Should we ignore missing beans and simply output nothing?
     */
    protected boolean ignore = false;

    /**
     * get ignore
     * @return boolean
     */
    public boolean getIgnore() {
        return (this.ignore);
    }

    /**
     * set ignore
     * @param b ignore
     */
    public void setIgnore(boolean b) {
        this.ignore = b;
    }

    /** CM110モード */
    private String mode__ = null;
    /** プラグインID */
    private String pluginId__ = null;
    /** ディレクトリID */
    private String tempDirId__ = null;
    /** 添付ファイルの保存先ディレクトリ※添付ディレクトリを分けたい場合に使用 */
    private String tempDirPlus__ = null;
    /** 添付ボタン表示 */
    private boolean tempBtn__ = true;
    /** 削除ボタン表示 */
    private boolean delBtn__ = false;
    /** 削除ボタン押下時の処理(script) */
    private String delBtnAction__ = null;
    /** ファイル一覧表示 */
    private boolean fileList__ = true;
    /** 添付ファイルフォームのID(フォームを複数表示する場合に使用) */
    private String formId__ = null;
    /** プレビュー機能 */
    private boolean preview__ = true;
    /** エラーメッセージ表示領域の有無 */
    private boolean errorMessageArea__ = true;
    /** エラーメッセージ表示領域 セレクタ */
    private String errorMessageAreaSelector__ = null;

    /**
     * <p>mode を取得します。
     * @return mode
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#mode__
     */
    public String getMode() {
        return mode__;
    }
    /**
     * <p>mode をセットします。
     * @param mode mode
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#mode__
     */
    public void setMode(String mode) {
        mode__ = mode;
    }
    /**
     * <p>pluginId を取得します。
     * @return pluginId
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#pluginId__
     */
    public String getPluginId() {
        return pluginId__;
    }
    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#pluginId__
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }
    /**
     * <p>tempDirId を取得します。
     * @return tempDirId
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#tempDirId__
     */
    public String getTempDirId() {
        return tempDirId__;
    }
    /**
     * <p>tempDirId をセットします。
     * @param tempDirId tempDirId
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#tempDirId__
     */
    public void setTempDirId(String tempDirId) {
        tempDirId__ = tempDirId;
    }
    /**
     * <p>tempDirPlus を取得します。
     * @return tempDirPlus
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#tempDirPlus__
     */
    public String getTempDirPlus() {
        return tempDirPlus__;
    }
    /**
     * <p>tempDirPlus をセットします。
     * @param tempDirPlus tempDirPlus
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#tempDirPlus__
     */
    public void setTempDirPlus(String tempDirPlus) {
        tempDirPlus__ = tempDirPlus;
    }
    /**
     * <p>tempBtn を取得します。
     * @return tempBtn
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#tempBtn__
     */
    public boolean isTempBtn() {
        return tempBtn__;
    }
    /**
     * <p>tempBtn をセットします。
     * @param tempBtn tempBtn
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#tempBtn__
     */
    public void setTempBtn(boolean tempBtn) {
        tempBtn__ = tempBtn;
    }
    /**
     * <p>delBtn を取得します。
     * @return delBtn
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#delBtn__
     */
    public boolean isDelBtn() {
        return delBtn__;
    }
    /**
     * <p>delBtn をセットします。
     * @param delBtn delBtn
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#delBtn__
     */
    public void setDelBtn(boolean delBtn) {
        delBtn__ = delBtn;
    }
    /**
     * <p>delBtnAction を取得します。
     * @return delBtnAction
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#delBtnAction__
     */
    public String getDelBtnAction() {
        return delBtnAction__;
    }
    /**
     * <p>delBtnAction をセットします。
     * @param delBtnAction delBtnAction
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#delBtnAction__
     */
    public void setDelBtnAction(String delBtnAction) {
        delBtnAction__ = delBtnAction;
    }
    /**
     * <p>fileList を取得します。
     * @return fileList
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#fileList__
     */
    public boolean isFileList() {
        return fileList__;
    }
    /**
     * <p>fileList をセットします。
     * @param fileList fileList
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#fileList__
     */
    public void setFileList(boolean fileList) {
        fileList__ = fileList;
    }
    /**
     * <p>formId を取得します。
     * @return formId
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#formId__
     */
    public String getFormId() {
        return formId__;
    }
    /**
     * <p>formId をセットします。
     * @param formId formId
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#formId__
     */
    public void setFormId(String formId) {
        formId__ = formId;
    }
    /**
     * <p>preview を取得します。
     * @return preview
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#preview__
     */
    public boolean isPreview() {
        return preview__;
    }
    /**
     * <p>preview をセットします。
     * @param preview preview
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#preview__
     */
    public void setPreview(boolean preview) {
        preview__ = preview;
    }
    /**
     * <p>errorMessageArea を取得します。
     * @return errorMessageArea
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#errorMessageArea__
     */
    public boolean isErrorMessageArea() {
        return errorMessageArea__;
    }
    /**
     * <p>errorMessageArea をセットします。
     * @param errorMessageArea errorMessageArea
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#errorMessageArea__
     */
    public void setErrorMessageArea(boolean errorMessageArea) {
        errorMessageArea__ = errorMessageArea;
    }
    /**
     * <p>errorMessageAreaSelector を取得します。
     * @return errorMessageAreaSelector
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#errorMessageAreaSelector__
     */
    public String getErrorMessageAreaSelector() {
        return errorMessageAreaSelector__;
    }
    /**
     * <p>errorMessageAreaSelector をセットします。
     * @param errorMessageAreaSelector errorMessageAreaSelector
     * @see jp.groupsession.v2.cmn.AttachmentAreaTag#errorMessageAreaSelector__
     */
    public void setErrorMessageAreaSelector(String errorMessageAreaSelector) {
        errorMessageAreaSelector__ = errorMessageAreaSelector;
    }

    /**
     * <br>Process the start tag.
     * @return int
     * @exception JspException if a JSP exception has occurred
     */
    public int doStartTag() throws JspException {
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();

        JspWriter writer = pageContext.getOut();
        try {

            __writeTag(writer, req);

        } catch (Exception e) {
            throw new JspException("Jsp出力に失敗しました。", e);
        }

        // Continue processing this page
        return (SKIP_BODY);

    }

    /**
     * <br>HTMLをJspへ出力します。
     * @param writer JspWriter
     * @param req リクエスト
     * @throws Exception 出力エラー
     */
    private void __writeTag(JspWriter writer, HttpServletRequest req)
        throws Exception {

        String formId = NullDefault.getString(getFormId(), "");

        //単一モード判定
        String multiple = " multiple";
        if (getMode().equals(String.valueOf(GSConstCommon.CMN110MODE_GAZOU))
        || getMode().equals(String.valueOf(GSConstCommon.CMN110MODE_FILE_TANITU))
        || getMode().equals(String.valueOf(GSConstCommon.CMN110MODE_TANITU_USR031))
        || getMode().equals(String.valueOf(GSConstCommon.CMN110MODE_TANITU_FIL030))
        || getMode().equals(String.valueOf(GSConstCommon.CMN110MODE_FILEKANRI_TANITU))) {
            multiple = "";
        }
        GsMessage gsMsg = new GsMessage(req);

        writer.println("<div id=\"attachment_FormArea" + formId + "\">");

        writer.println("<div>");

        writer.println("<input type=\"hidden\" name=\"attachmentFileListFlg" +  formId + "\""
                + " value=\"" +  isFileList() + "\" />");

        //添付ボタン
        if (isTempBtn()) {
            writer.println("  <input type=\"file\" id=\"attachmentAreaBtn" + formId + "\""
                    + " class=\"display_none\""
                    + " onchange=\"attachFileSelect(this, '" + formId + "');\""
                    +  multiple + " />");
            writer.println("  <button type=\"button\" class=\"baseBtn ml0\""
                    + " value=\"" + gsMsg.getMessage("cmn.attached") + "\""
                    + " onClick=\"attachmentLoadFile('" + formId + "');\">");
            writer.println("    <img class=\"btn_classicImg-display\""
                    + " src=\"../common/images/classic/icon_temp_file_2.png\""
                    + " alt=\"" + gsMsg.getMessage("cmn.attached") + "\">");
            writer.println("    <img class=\"btn_originalImg-display\""
                    + " src=\"../common/images/original/icon_attach.png\""
                    + " alt=\"" + gsMsg.getMessage("cmn.attached") + "\">");
            writer.println("    " + gsMsg.getMessage("cmn.attached"));
            writer.print("  </button>");
        }

        //削除ボタン
        if (isDelBtn()) {
            writer.println("  <button type=\"button\" class=\"baseBtn\""
                    + " value=\"" + gsMsg.getMessage("cmn.delete") + "\""
                    + " onclick=\"" + getDelBtnAction() + "\">");
            writer.println("    <img class=\"btn_classicImg-display\""
                    + " src=\"../common/images/classic/icon_trash.png\""
                    + " alt=\"" + gsMsg.getMessage("cmn.delete") + "\">");
            writer.println("    <img class=\"btn_originalImg-display\""
                    + " src=\"../common/images/original/icon_trash.png\""
                    + " alt=\"" + gsMsg.getMessage("cmn.delete") + "\">");
            writer.println("   " + gsMsg.getMessage("cmn.delete"));
            writer.print("  </button>");
        }
        
        if (!getPluginId().equals(GSConst.PLUGIN_ID_CHAT)
                && !getPluginId().equals(GSConst.PLUGIN_ID_MEMO)) {
            writer.println("<span class=\"txt_c ml5 fs_12\">"
                    + gsMsg.getMessage("cmn.attach.drag.drop") + "</span>");
        }
        
        writer.println("</div>");

        //エラーメッセージ表示領域
        String errMsgAreaSelector = getErrorMessageAreaSelector();
        if (isErrorMessageArea()) {
            writer.println("<span id=\"attachmentFileErrorArea" + formId + "\"></span>");
            errMsgAreaSelector = "#attachmentFileErrorArea" + formId;
        } else {
            writer.println("<input type=\"hidden\""
                    + " name=\"attachmentFileErrorAreaSelector" + formId + "\""
                    + " value=\"" + errMsgAreaSelector + "\" />");
        }

        //指定された各パラメータを設定
        writer.println("<input type=\"hidden\" name=\"attachmentMode" + formId + "\""
                + " value=\"" + NullDefault.getString(getMode(), "") + "\" />");
        writer.println("<input type=\"hidden\" name=\"attachmentPluginId" + formId + "\""
                + " value=\"" + NullDefault.getString(getPluginId(), "") + "\" />");
        writer.println("<input type=\"hidden\" name=\"attachmentTempDirId" + formId + "\""
                + " value=\"" + NullDefault.getString(getTempDirId(), "") + "\" />");
        writer.println("<input type=\"hidden\" name=\"attachmentTempDirPlus" + formId + "\""
                + " value=\"" + NullDefault.getString(getTempDirPlus(), "") + "\" />");
        writer.println("<input type=\"hidden\" name=\"attachmentPreview" + formId + "\""
                    + " value=\"" + preview__ + "\" />");

        //フォームID
        if (formId.length() > 0) {
            writer.println("<input type=\"hidden\" name=\"attachment_ID_list\""
                            + " value=\"" + getFormId()
                            + "\" />");
        }

        //アップロード済み添付ファイルのリンク表示領域
        writer.print("<span id=\"attachmentFileListArea" + formId + "\" class=\"mt5\">");

        if (isFileList()) {

            //alt属性に設定する値を画面に保持する
            writer.println("<input type=\"hidden\" name=\"fileAltText\""
                            + " value=\"" + gsMsg.getMessage("cmn.file")
                            + "\" />");
            writer.println("<input type=\"hidden\" name=\"deleteAltText\""
                            + " value=\"" + gsMsg.getMessage("cmn.delete")
                            + "\" />");

            String tempDir = __getTempDirPath(req);
            CommonBiz cmnBiz = new CommonBiz();
            List<LabelValueBean> fileList = cmnBiz.getTempFileLabelList(tempDir);
            Collections.sort(fileList);

            //テンポラリディレクトリ内にファイルが存在する場合、添付ファイルリンクを設定
            if (fileList != null && !fileList.isEmpty()) {
                for (LabelValueBean fileData : fileList) {
                    String scriptValue = "'" + fileData.getValue() + "'";
                    if (formId.length() > 0) {
                        scriptValue +=  ", '" + formId + "'";
                    }

                    if (getMode().equals(String.valueOf(GSConstCommon.CMN110MODE_MEMO))) {
                        String fileLineStr =
                            "<file-preview url=\""
                            + __createInlineDownloadUrl(fileData.getValue(), formId)
                            + "\" filename=\"" + fileData.getLabel() + "\" >" 
                            + "<div id=\"attachmentFileDetail" + formId + "_" + fileData.getValue()
                            + "\">"
                            + "<span class=\"verAlignMid\">"
                            + "<a href=\"#!\""
                            + " onClick=\"attachmentFileDownload(" + scriptValue + ");\">"
                            + "<span class=\"textLink \">" + fileData.getLabel() + "</span>"
                            + "</a>"
                            + "<img class=\"ml5 cursor_p btn_originalImg-display\""
                            + " src=\"../common/images/original/icon_delete.png\""
                            + " onClick=\"attachmentDeleteFile(" + scriptValue + ");\""
                            + " draggable=\"false\" alt=\"" + gsMsg.getMessage("cmn.delete") + "\">"
                            + "<img class=\"ml5 cursor_p btn_classicImg-display\""
                            + " src=\"../common/images/classic/icon_delete.png\""
                            + " onClick=\"attachmentDeleteFile(" + scriptValue + ");\""
                            + " draggable=\"false\" alt=\"" + gsMsg.getMessage("cmn.delete") + "\">"
                            + "</span>"
                            + "</div>"
                            + "</file-preview>";
                        writer.print(fileLineStr);
                    } else {
                        String fileLineStr =
                            "<file-preview url=\""
                            + __createInlineDownloadUrl(fileData.getValue(), formId)
                            + "\" filename=\"" + fileData.getLabel() + "\" >"
                            + "<div id=\"attachmentFileDetail"
                            + formId + "_" + fileData.getValue()
                            + "\" class=\"mt5 display_flex\">"
                            + "<div class=\"verAlignMid\">"
                            + "<img class=\"classic-display\""
                            + " src=\"../common/images/classic/icon_temp_file_2.png\""
                            + " draggable=\"false\" alt=\"" + gsMsg.getMessage("cmn.file") + "\">"
                            + "<img class=\"original-display\""
                            + " src=\"../common/images/original/icon_attach.png\""
                            + " draggable=\"false\" alt=\"" + gsMsg.getMessage("cmn.file") + "\">"
                            + "<a href=\"#!\""
                            + " onClick=\"attachmentFileDownload(" + scriptValue + ");\">"
                            + "<span class=\"textLink ml5\">" + fileData.getLabel() + "</span>"
                            + "</a>"
                            + "<img class=\"ml5 cursor_p btn_originalImg-display\""
                            + " src=\"../common/images/original/icon_delete.png\""
                            + " onClick=\"attachmentDeleteFile(" + scriptValue + ");\""
                            + " draggable=\"false\" alt=\"" + gsMsg.getMessage("cmn.delete") + "\">"
                            + "<img class=\"ml5 cursor_p btn_classicImg-display\""
                            + " src=\"../common/images/classic/icon_delete.png\""
                            + " onClick=\"attachmentDeleteFile(" + scriptValue + ");\""
                            + " draggable=\"false\" alt=\"" + gsMsg.getMessage("cmn.delete") + "\">"
                            + "</div>"
                            + "</div>"
                            + "</file-preview>";
                        writer.print(fileLineStr);
                    }
                }
            }
        }
        writer.println("</span>");
        writer.println("</div>");
    }

    /**
     * Release all allocated resources.
     */
    public void release() {
        super.release();
        ignore = false;
        mode__ = null;
        pluginId__ = null;
        tempDirId__ = null;
        tempDirPlus__ = null;
        delBtn__ = false;
        delBtnAction__ = null;
        fileList__ = false;
        formId__ = null;
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param req HttpServletRequest
     * @return テンポラリディレクトリパス
     */
    private String __getTempDirPath(HttpServletRequest req) {
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();

        String[] path = __createSubPath();

        String dirId = null;
        if (path.length > 0) {
            dirId = path[0];
        }

        String[] subdir = null;
        if (path.length > 1) {
            subdir = (String[]) ArrayUtils.subarray(path, 1, path.length);
        }

        RequestModel reqMdl = new RequestModel();
        reqMdl.setDomain(GroupSession.getResourceManager().getDomain(req));
        reqMdl.setSession(req.getSession(false));
        reqMdl.setLocale(req.getLocale());

        String tempDir = pathUtil.getTempPath(reqMdl,
                getPluginId(),
                dirId, subdir);

        return tempDir;
    }

    /**
     * <br>[機  能] テンポラリディレクトリのディレクトリID以下のパス配列を生成
     * <br>[解  説]
     * <br>[備  考]
     * @return ディレクトリID以下のパス生成
     */
    private String[] __createSubPath() {
        StringBuilder dilBld = new StringBuilder();
        if (!StringUtil.isNullZeroString(getTempDirId())) {
            dilBld.append(getTempDirId());
            dilBld.append(File.separator);
        }
        if (!StringUtil.isNullZeroString(getTempDirPlus())) {
            dilBld.append(getTempDirPlus());
            dilBld.append(File.separator);
        }

        String[] path = StringUtils.split(
                IOTools.replaceFileSep(dilBld.toString()),
                File.separator);
        return path;
    }

    /**
     * <br>[機  能] ダウンロードURLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param fileId ファイルID
     * @param formId フォームID
     * @return url ダウンロードURL
     */
    private String __createInlineDownloadUrl(String fileId, String formId) {
        String url = "../common/cmn110.do"
                    + "?CMD=fileInlineDownload"
                    + "&cmn110DlFileId=" +  fileId;

        url += "&cmn110Mode=" + getMode();
        url += "&cmn110pluginId=" + getPluginId();
        url += "&tempDirId=" + getTempDirId();

        if (!StringUtil.isNullZeroString(getTempDirPlus())) {
            url += "&cmn110TempDirPlus=" + getTempDirPlus();
        }
        return url;
    }
}
