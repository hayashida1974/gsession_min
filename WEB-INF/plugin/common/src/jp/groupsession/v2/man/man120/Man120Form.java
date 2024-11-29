package jp.groupsession.v2.man.man120;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] メイン 管理者設定 プラグインマネージャー画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man120Form extends AbstractGsForm {

    //入力項目
    /** ユーザによる変更*/
    private String menuEditOk__ = String.valueOf(GSConstMain.MENU_STATIC_NOT_USE);
    /** 表示メニュー(選択) */
    private String[] man120selectViewMenu__ = null;
    /** 非表示メニュー(選択) */
    private String[] man120selectNotViewMenu__ = null;

    //表示項目
    /** 表示メニューコンボ */
    private List<Man120Model> man120viewMenuLabel__ = null;
    /** 非表示メニューコンボ */
    private List<Man120Model> man120notViewMenuLabel__ = null;

    /** 非表示メニュー(パラメータ保持用) */
    private String[] man120notViewMenuList__ = null;
    /** 表示メニュー(パラメータ保持用) */
    private String[] man120viewMenuList__ = null;

    /** 選択したプラグインのプラグインID */
    private String man120pluginId__ = null;
    /** プラグイン使用制限情報 */
    private Man120PluginControlModel pluginControl__ = null;

    /** ソート選択 */
    private String man120sort__ = GSConst.PLUGINID_MAIN;

    /** アクセス制限設定遷移元 */
    private String man280backId__ = "man120";

    /** プラグインID(画像表示) */
    private String man120imgPluginId__ = null;


    /**
     * <p>man120imgPluginId を取得します。
     * @return man120imgPluginId
     */
    public String getMan120imgPluginId() {
        return man120imgPluginId__;
    }

    /**
     * <p>man120imgPluginId をセットします。
     * @param man120imgPluginId man120imgPluginId
     */
    public void setMan120imgPluginId(String man120imgPluginId) {
        man120imgPluginId__ = man120imgPluginId;
    }

    /**
     * <p>man120viewMenuList を取得します。
     * @return man120viewMenuList
     */
    public String[] getMan120viewMenuList() {
        return man120viewMenuList__;
    }

    /**
     * <p>man120viewMenuList をセットします。
     * @param man120viewMenuList man120viewMenuList
     */
    public void setMan120viewMenuList(String[] man120viewMenuList) {
        man120viewMenuList__ = man120viewMenuList;
    }

    /**
     * <p>man120notViewMenuList を取得します。
     * @return man120notViewMenuList
     */
    public String[] getMan120notViewMenuList() {
        return man120notViewMenuList__;
    }

    /**
     * <p>man120notViewMenuList をセットします。
     * @param man120notViewMenuList man120notViewMenuList
     */
    public void setMan120notViewMenuList(String[] man120notViewMenuList) {
        man120notViewMenuList__ = man120notViewMenuList;
    }

    /**
     * <p>menuEditOk を取得します。
     * @return menuEditOk
     */
    public String getMenuEditOk() {
        return menuEditOk__;
    }

    /**
     * <p>menuEditOk をセットします。
     * @param menuEditOk menuEditOk
     */
    public void setMenuEditOk(String menuEditOk) {
        menuEditOk__ = menuEditOk;
    }

    /**
     * <p>man120notViewMenuLabel を取得します。
     * @return man120notViewMenuLabel
     */
    public List<Man120Model> getMan120notViewMenuLabel() {
        return man120notViewMenuLabel__;
    }

    /**
     * <p>man120notViewMenuLabel をセットします。
     * @param man120notViewMenuLabel man120notViewMenuLabel
     */
    public void setMan120notViewMenuLabel(List<Man120Model> man120notViewMenuLabel) {
        man120notViewMenuLabel__ = man120notViewMenuLabel;
    }

    /**
     * <p>man120selectNotViewMenu を取得します。
     * @return man120selectNotViewMenu
     */
    public String[] getMan120selectNotViewMenu() {
        return man120selectNotViewMenu__;
    }

    /**
     * <p>man120selectNotViewMenu をセットします。
     * @param man120selectNotViewMenu man120selectNotViewMenu
     */
    public void setMan120selectNotViewMenu(String[] man120selectNotViewMenu) {
        man120selectNotViewMenu__ = man120selectNotViewMenu;
    }

    /**
     * <p>man120selectViewMenu を取得します。
     * @return man120selectViewMenu
     */
    public String[] getMan120selectViewMenu() {
        return man120selectViewMenu__;
    }

    /**
     * <p>man120selectViewMenu をセットします。
     * @param man120selectViewMenu man120selectViewMenu
     */
    public void setMan120selectViewMenu(String[] man120selectViewMenu) {
        man120selectViewMenu__ = man120selectViewMenu;
    }

    /**
     * <p>man120viewMenuLabel を取得します。
     * @return man120viewMenuLabel
     */
    public List<Man120Model> getMan120viewMenuLabel() {
        return man120viewMenuLabel__;
    }

    /**
     * <p>man120viewMenuLabel をセットします。
     * @param man120viewMenuLabel man120viewMenuLabel
     */
    public void setMan120viewMenuLabel(List<Man120Model> man120viewMenuLabel) {
        man120viewMenuLabel__ = man120viewMenuLabel;
    }

    /**
     * <p>man120pluginId を取得します。
     * @return man120pluginId
     */
    public String getMan120pluginId() {
        return man120pluginId__;
    }

    /**
     * <p>man120pluginId をセットします。
     * @param man120pluginId man120pluginId
     */
    public void setMan120pluginId(String man120pluginId) {
        man120pluginId__ = man120pluginId;
    }

    /**
     * <p>pluginControl を取得します。
     * @return pluginControl
     */
    public Man120PluginControlModel getPluginControl() {
        return pluginControl__;
    }

    /**
     * <p>pluginControl をセットします。
     * @param pluginControl pluginControl
     */
    public void setPluginControl(Man120PluginControlModel pluginControl) {
        pluginControl__ = pluginControl;
    }

    /**
     * <p>man120sort を取得します。
     * @return man120sort
     */
    public String getMan120sort() {
        return man120sort__;
    }

    /**
     * <p>man120sort をセットします。
     * @param man120sort man120sort
     */
    public void setMan120sort(String man120sort) {
        man120sort__ = man120sort;
    }

    /**
     * <p>man280backId を取得します。
     * @return man280backId
     */
    public String getMan280backId() {
        return man280backId__;
    }

    /**
     * <p>man280backId をセットします。
     * @param man280backId man280backId
     */
    public void setMan280backId(String man280backId) {
        man280backId__ = man280backId;
    }

    /**
     * <br>[機  能] 入力チェック(上矢印ボタン、下矢印ボタンクリック時)を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();

        //表示プラグイン(選択)
        if (man120selectViewMenu__ == null || man120selectViewMenu__.length == 0) {
            GsMessage gsMsg = new GsMessage(reqMdl);
            ActionMessage msg = new ActionMessage("error.select.required.text",
                                                gsMsg.getMessage("cmn.use.plugin"));
            StrutsUtil.addMessage(errors, msg, "man120selectViewMenu");
            return errors;

        }

        return errors;
    }
    /**
     * <br>[機  能] 入力チェック(左矢印ボタンクリック時)を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheckLeft(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();

        //表示プラグイン(選択)
        if (man120selectViewMenu__ == null || man120selectViewMenu__.length == 0) {
            ActionMessage msg = new ActionMessage("error.select.required.text",
                    getInterMessage(req, "cmn.use.plugin"));
            StrutsUtil.addMessage(errors, msg, "man120selectViewMenu");
            return errors;
        } else {
            for (String viewMenu : man120selectViewMenu__) {
                if (viewMenu.equals("main")) {
                    ActionMessage msg = new ActionMessage("error.common.no.delete",
                            getInterMessage(req, GSConstMain.TEXT_MAIN_PLUGIN));
                    StrutsUtil.addMessage(errors, msg, "man120selectViewMenu");
                    return errors;
                }
            }
        }

        return errors;
    }
    /**
     * <br>[機  能] 入力チェック(右矢印ボタンクリック時)を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheckRight(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();

        //非表示プラグイン(選択)
        if (man120selectNotViewMenu__ == null || man120selectNotViewMenu__.length == 0) {
            ActionMessage msg = new ActionMessage("error.select.required.text",
                    getInterMessage(req, "cmn.not.use.plugin"));
            StrutsUtil.addMessage(errors, msg, "man120selectNotViewMenu");
            return errors;
        }

        return errors;
    }
}
