package jp.groupsession.v2.struts.taglib;
import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.taglib.bean.WriteTag;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.ThemePathBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;

/**
 * <br>[機  能] linkタグを作成する
 * <br>[解  説] 読み込むCSSファイルを設定する
 * <br>[備  考]
 *
 * @author JTS
 */
public class CssTag extends WriteTag {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(CssTag.class);


    /** pageContext */
    private PageContext pageContext__;
    /** テーマパス */
    private String themePath__ = null;
    /** ファイル名 */
    private String fileName__ = null;
    /** 選択テーマパス */
    private String selectThemePath__ = null;
    /** テーマフラグ */
    private Boolean themeFlg__ = false;

    /**
     * @param fileName 設定する fileName。
     */
    public void setFilename(String fileName) {
        fileName__ = fileName;
    }
    /**
     * @return fileName を戻します。
     */
    public String getFilename() {
        return fileName__;
    }
    /**
     * @param selectThemePath 設定する selectThemePath。
     */
    public void setSelectthemepath(String selectThemePath) {
        selectThemePath__ = selectThemePath;
    }
    /**
     * @return selectThemePath を戻します。
     */
    public String getSelectthemepath() {
        return selectThemePath__;
    }
    /**
     * @return pageContext を戻します。
     */
    public PageContext getPageContext() {
        return pageContext__;
    }
    /**
     * @param pageContext 設定する pageContext。
     */
    public void setPageContext(PageContext pageContext) {
        pageContext__ = pageContext;
    }
    /**
     * <p>タグが読み込まれた時
     * @throws JspException 実行例外
     * @return SKIP_BODY
     */
    public int doStartTag() throws JspException {

        BaseUserModel smodel = new BaseUserModel();

        //themePath設定
        if (pageContext__.getSession() == null
                || pageContext__.getSession().getAttribute(GSConst.SESSION_KEY) == null) {
            themePath__ = ThemePathBiz.DEFAULT_THEME_PATH;
        } else {

            if (selectThemePath__ != null) {
                themePath__ = selectThemePath__;
            } else {
                smodel = (BaseUserModel) pageContext__.getSession()
                        .getAttribute(GSConst.SESSION_KEY);
                themePath__ = smodel.getCtmPath();
                if (themePath__ == null) {
                    themePath__ = ThemePathBiz.DEFAULT_THEME_PATH;
                }
            }
        }

        try {
            __writeLinkTheme(themePath__);


            themeFlg__ = true;

        } catch (Exception e) {
              //throw new JspException(e.getMessage());
            log__.debug("Exception3", e);
        } finally {
            if (themeFlg__ == false) {
                try {
                    __writeLinkTheme(ThemePathBiz.DEFAULT_THEME_PATH);
                } catch (Exception e) {
//                    throw new JspException(e.getMessage());
                    log__.debug("Exception4", e);
                }
            }
        }

        return SKIP_BODY;
    }
    /**
     *
     * <br>[機  能] theme.cssのリンクを書き出す
     * <br>[解  説] 指定されたファイルの経路上のtheme_base.cssのリンクも書き出す
     * <br>[備  考]
     * @param themePath thme.cssのパス
     * @throws IOException ファイルアクセス例外
     */
    private void __writeLinkTheme(String themePath) throws IOException {
        ThemePathBiz tpBiz = new ThemePathBiz();
        List<String> cssPathList = tpBiz.createPathList(themePath + "/" + fileName__);

        //読み込み対象（theme_base.css 読み込み）
        JspWriter out = pageContext__.getOut();

        for (String cssPath : cssPathList) {
            out.print("<link rel=stylesheet href='../"
            + cssPath
            + "?" + GSConst.VERSION_PARAM + "'"
                    + " type='text/css'>");
        }
    }
    /**
     * <p>タグが読み終わった時
     * @throws JspException 実行例外
     * @return EVAL_PAGE
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }


}
