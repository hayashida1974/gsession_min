package jp.groupsession.v2.usr;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.TagUtils;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * Tag that retrieves the specified property of the specified bean, converts
 * it to a String representation (if necessary), and writes it to the current
 * output stream, optionally filtering characters that are sensitive in HTML.
 *
 * @version $Revision: 1.1 $ $Date: 2016/11/04 08:42:48 $
 */
public class GroupTreeTag extends TagSupport {
    /** ロギングクラス */
    public static Log log__ = LogFactory.getLog(GroupTreeTag.class);

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

    /**
     * Name of the bean that contains the data we will be rendering.
     */
    protected String name = null;

    /**
     * get name
     * @return String
     */
    public String getName() {
        return (this.name);
    }

    /**
     * set name
     * @param string name
     */
    public void setName(String string) {
        this.name = string;
    }

    /**
     * Name of the property to be accessed on the specified bean.
     */
    protected String property = null;

    /**
     * get Property
     * @return String
     */
    public String getProperty() {
        return (this.property);
    }

    /**
     * set Property
     * @param string property
     */
    public void setProperty(String string) {
        this.property = string;
    }

    /**
     * The scope to be searched to retrieve the specified bean.
     */
    protected String scope = null;

    /**
     * get Scope
     * @return String
     */
    public String getScope() {
        return (this.scope);
    }

    /**
     * set Scope
     * @param string scope
     */
    public void setScope(String string) {
        this.scope = string;
    }

    /**
     * The servlet context attribute key for our resources.
     */
    protected String bundle = null;

    /**
     * getBundle
     * @return String
     */
    public String getBundle() {
        return (this.bundle);
    }

    /**
     * setBundle
     * @param string bundle
     */
    public void setBundle(String string) {
        this.bundle = string;
    }

    /**
     * Type属性
     * radio = ラジオボタン（単一選択）
     * check = チェックボックス（複数選択）
     * hyper = ハイパーリンク（単一選択）
     */
    protected String type = null;

    /**
     * get Type属性
     * @return String
     */
    public String getType() {
        return (this.type);
    }

    /**
     * set Type属性
     * @param string Type
     */
    public void setType(String string) {
        this.type = string;
    }

    /**
     * SelectLevel属性
     * 選択可能にする階層レベルを1～10の範囲で設定します。
     */
    protected String level = null;

    /**
     * get SelectLevel属性
     * @return int
     */
    public String getLevel() {
        return (this.level);
    }

    /**
     * set SelectLevel属性
     * @param string 選択可能階層レベル(1～10)
     */
    public void setLevel(String string) {
        this.level = string;
    }

    /**
     * Root表示・非表示属性
     */
    protected String root = null;

    /**
     * Root表示・非表示属性を取得します。
     * @return String
     */
    public String getRoot() {
        return (this.root);
    }

    /**
     * Root表示・非表示属性を設定します。
     * @param string 0:非表示 1:表示
     */
    public void setRoot(String string) {
        this.root = string;
    }

    // --------------------------------------------------------- Public Methods

    /**
     * Process the start tag.
     * @return int
     * @exception JspException if a JSP exception has occurred
     */
    @SuppressWarnings({"unchecked", "all" })
    public int doStartTag() throws JspException {
        // Look up the requested bean (if necessary)
        HttpServletRequest req = (HttpServletRequest) pageContext.getRequest();
        if (ignore) {
            if (TagUtils.getInstance().lookup(pageContext, name, scope)
                == null) {
                return (SKIP_BODY); // Nothing to output
            }
        }
        // Look up the requested property value
        Object value =
            TagUtils.getInstance().lookup(pageContext, name, property, scope);

        if (value == null) {
            return (SKIP_BODY); // Nothing to output
        }
        //使用する型に置き換える
        log__.debug("<==value==>" + value.getClass());
        if (value instanceof java.util.ArrayList) {
            //型が違う場合はエラー
            //            return (SKIP_BODY); // Nothing to output
        }
        ArrayList groupCollection = (ArrayList) value; //型はArrayList

        JspWriter writer = pageContext.getOut();
        //      //再起的にHTMLを吐き出す。
        try {
            __writeTag(writer, groupCollection, req);
        } catch (Exception e) {
            throw new JspException("Jsp出力に失敗しました。", e);
        }

        // Continue processing this page
        return (SKIP_BODY);

    }

    /**
     * HTMLをJspへ出力します。
     * @param writer JspWriter
     * @param groupList ディレクトリ構造リスト
     * @param req リクエスト
     * @throws Exception 出力エラー
     */
    private void __writeTag(JspWriter writer, ArrayList<GroupModel> groupList,
            HttpServletRequest req)
        throws Exception {
        //Type属性を取得
        String viewType = type;
        log__.debug("<==viewType==>" + viewType);
        log__.debug("<==selectLevel==>" + level);
        log__.debug("<==dspRoot==>" + root);
        int select = (new Integer(level)).intValue();
        if (root.equals("1")) {
            __writeRootGroup(writer, viewType, select, req);
        }
        for (int i = 0; i < groupList.size(); i++) {
            GroupModel gModel = (GroupModel) groupList.get(i);
            __writeHtmlString(writer, gModel, viewType, select, req);
        }
    }

    /**
     * GroupTreeModelからHTML文字列を取得します。
     * @param writer ライター
     * @param type リストタイプ
     * @param sel 選択可能レベル
     * @param req リクエスト
     * @throws Exception IOエラー時にスロー
     */
    private static void __writeRootGroup(
        JspWriter writer,
        String type,
        int sel,
        HttpServletRequest req)
        throws Exception {
        GsMessage gsMsg = new GsMessage();
        //第1階層に設定する場合はこちらを選択してください。
        String textFirstHierarchy = gsMsg.getMessage(req, "user.145");
        //タイプの指定が不正です。
        String textIllegalType = gsMsg.getMessage(req, "user.146");
        //いずれかを指定してください。
        String textSpecifyEither = gsMsg.getMessage(req, "user.147");

        writer.println("<div class=\"dep1\">");
        if (type.equals("radio")) {
            writer.println("  <span class=\"verAlignMid\">"
                    + "<input id=\"-1\" type=\"radio\" name=\"c1\" value=\"-1\">");
            writer.println(
                "  <label for=\"-1\">"
                    + "<img class=\"classic-display\" "
                    + "src=\"../common/images/classic/icon_group.png\">"
                    + "<img class=\"original-display\" "
                    + "src=\"../common/images/original/icon_group.png\">"
                    + "<font class=\"text_r1\">※" + textFirstHierarchy + " </font></label></span>");
            writer.println("</div>");
            writer.println("<div class=\"dep1\">");
            writer.println("</div>");
        } else if (type.equals("checkbox")) {
            writer.println(
                "  <span class=\"verAlignMid\">"
                + "<input id=\"-1\" type=\"checkbox\" name=\"c1\" value=\"-1\">");
            writer.println(
                "  <label for=\"-1\">"
                    + "<img class=\"classic-display\" "
                    + "src=\"../common/images/classic/icon_group.png\">"
                    + "<img class=\"original-display\" "
                    + "src=\"../common/images/original/icon_group.png\">"
                    + "<font class=\"cl_fontWarn\"> ※"
                    + textFirstHierarchy + " </font></label></span>");
            writer.println("</div>");
            writer.println("<div class=\"dep1\">");
            writer.println("</div>");
        } else if (type.equals("link")) {
            writer.println(
                "  <a href=\"#!\" onclick=\"onParentSubmit(\"-1\")\">");
            writer.println(
                     "<img class=\"classic-display\" "
                     + "src=\"../common/images/classic/icon_group.png\">"
                    + "<img class=\"original-display\" "
                    + "src=\"../common/images/original/icon_group.png\">"
                    + "<font class=\"text_r1\"> ※" + textFirstHierarchy + " </font></a>");
            writer.println("</div>");
            writer.println("<div class=\"dep1\">&nbsp;");
            writer.println("</div>");
        } else {
            throw new JspException(
                    textIllegalType + "radio,checkbox,linkの" + textSpecifyEither);
        }
    }

    /**
     * GroupTreeModelからHTML文字列を取得します。
     * @param writer ライター
     * @param gModel GroupTreeModel
     * @param type リストタイプ
     * @param req リクエスト
     * @param sel 選択可能レベル
     * @throws Exception IOエラー時にスロー
     */
    private static void __writeHtmlString(
        JspWriter writer,
        GroupModel gModel,
        String type,
        int sel,
        HttpServletRequest req)
        throws Exception {
        GsMessage gsMsg = new GsMessage();
        //タイプの指定が不正です。
        String textIllegalType = gsMsg.getMessage(req, "user.146");
        //いずれかを指定してください。
        String textSpecifyEither = gsMsg.getMessage(req, "user.147");

        int gSid = gModel.getGroupSid();
        String gName = StringUtilHtml.transToHTmlPlusAmparsant(gModel.getGroupName());
        int classLv = gModel.getClassLevel();
        switch (classLv) {
            case 1 :
                writer.println("<div class=\"dep1\">");
                break;
            case 2 :
                writer.println("<div class=\"dep2\">");
                break;
            case 3 :
                writer.println("<div class=\"dep3\">");
                break;
            case 4 :
                writer.println("<div class=\"dep4\">");
                break;
            case 5 :
                writer.println("<div class=\"dep5\">");
                break;
            case 6 :
                writer.println("<div class=\"dep6\">");
                break;
            case 7 :
                writer.println("<div class=\"dep7\">");
                break;
            case 8 :
                writer.println("<div class=\"dep8\">");
                break;
            case 9 :
                writer.println("<div class=\"dep9\">");
                break;
            case 10 :
                writer.println("<div class=\"dep10\">");
                break;
            default :
                writer.println("<div class=\"dep1\">");
                break;
        }

        if (type.equals("radio")) {
            if (sel >= classLv) {
                writer.println(
                    "  <span class=\"verAlignMid\">"
                    + "<input id=\"" + gSid + "\" type=\"radio\" name=\"c1\" value=\""
                        + gSid
                        + "\">");
            } else {
                writer.println(
                    "  <span class=\"verAlignMid\">"
                    + "<input id=\"" + gSid + "\" type=\"radio\" name=\"c1\" value=\""
                        + gSid
                        + "\" disabled>");
            }
            writer.println(
                "  <label for=\"" + gSid + "\">"
                    + "<img class=\"classic-display\" "
                    + "src=\"../common/images/classic/icon_group.png\">"
                    + "<img class=\"original-display\" "
                    + "src=\"../common/images/original/icon_group.png\">"
                    + "<font class=\"cl_linkDef\">"
                    + gName
                    + "</font></label></span>");
            writer.println("</div>");
        } else if (type.equals("checkbox")) {
//            if (sel >= classLv) {
//                writer.println(
//                    "  <input id=\"" + gSid + "\" type=\"checkbox\" name=\"c1\" value=\""
//                        + gSid
//                        + "\" class=\"" + classLv + "\" onclick=\"onChildAllChecked("
//                        + gSid
//                        + "," + classLv + ")\">");
            if (sel >= classLv) {
                writer.println(
                    "  <span class=\"verAlignMid\">"
                    + "<input id=\"" + gSid + "\" type=\"checkbox\" name=\"c1\" value=\""
                        + gSid
                        + "\" class=\"" + classLv + "\" onclick=\"defaultGroup();\">");
            } else {
                writer.println(
                    "  <span class=\"verAlignMid\">"
                    + "<input type=\"checkbox\" name=\"c1\" value=\""
                        + gSid
                        + "\" class=\"dep" + classLv + "\" disabled>");
            }
            writer.println(
                "  <label for=\"" + gSid + "\">"
                    + "<img class=\"classic-display\" "
                    + "src=\"../common/images/classic/icon_group.png\">"
                    + "<img class=\"original-display\" "
                    + "src=\"../common/images/original/icon_group.png\">"
                    + "<font class=\"cl_linkDef\">"
                    + gName
                    + "</font></label></span>");
            writer.println("</div>");
        } else if (type.equals("link")) {
            if (sel >= classLv) {
                writer.println(
                    "  <a href=\"#!\" onclick=\"onParentSubmit("
                        + gSid
                        + ")\">");
                writer.println(
                        "<img class=\"classic-display\" "
                        + "src=\"../common/images/classic/icon_group.png\">"
                        + "<img class=\"original-display\" "
                        + "src=\"../common/images/original/icon_group.png\">"
                        + "<font>"
                        + gName
                        + "</font></a>");
                writer.println("</div>");
            } else {
                writer.println(
                        "<img class=\"classic-display\" "
                        + "src=\"../common/images/classic/icon_group.png\">"
                        + "<img class=\"original-display\" "
                        + "src=\"../common/images/original/icon_group.png\">"
                        + "<font>"
                        + gName
                        + "</font>");
                writer.println("</div>");
            }

        } else {
            throw new JspException(
                    textIllegalType + "radio,checkbox,linkの" + textSpecifyEither);
        }

    }

    /**
     * Release all allocated resources.
     */
    public void release() {

        super.release();
        ignore = false;
        name = null;
        property = null;
        scope = null;
        bundle = null;

    }

}
