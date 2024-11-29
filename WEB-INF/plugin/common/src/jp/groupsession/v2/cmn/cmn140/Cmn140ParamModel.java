package jp.groupsession.v2.cmn.cmn140;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 個人設定 メニュー項目の設定画面のパラメータ、出力値を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn140ParamModel extends AbstractParamModel {

    //入力項目
    /** 表示メニュー(選択) */
    private String[] cmn140selectViewMenu__ = null;
    /** 非表示メニュー(選択) */
    private String[] cmn140selectNotViewMenu__ = null;

    //表示項目
    /** 表示メニューコンボ */
    private List<LabelValueBean> cmn140viewMenuLabel__ = null;
    /** 非表示メニューコンボ */
    private List<LabelValueBean> cmn140notViewMenuLabel__ = null;

    /** 表示メニュー(パラメータ保持用) */
    private String[] cmn140viewMenuList__ = null;

    /** 編集許可区分*/
    private int cmn140EditKbn__ = 0;

    /**
     * <p>cmn140EditKbn を取得します。
     * @return cmn140EditKbn
     */
    public int getCmn140EditKbn() {
        return cmn140EditKbn__;
    }
    /**
     * <p>cmn140EditKbn をセットします。
     * @param cmn140EditKbn cmn140EditKbn
     */
    public void setCmn140EditKbn(int cmn140EditKbn) {
        cmn140EditKbn__ = cmn140EditKbn;
    }
    /**
     * <p>cmn140notViewMenuLabel を取得します。
     * @return cmn140notViewMenuLabel
     */
    public List<LabelValueBean> getCmn140notViewMenuLabel() {
        return cmn140notViewMenuLabel__;
    }
    /**
     * <p>cmn140notViewMenuLabel をセットします。
     * @param cmn140notViewMenuLabel cmn140notViewMenuLabel
     */
    public void setCmn140notViewMenuLabel(
            List<LabelValueBean> cmn140notViewMenuLabel) {
        cmn140notViewMenuLabel__ = cmn140notViewMenuLabel;
    }
    /**
     * <p>cmn140selectNotViewMenu を取得します。
     * @return cmn140selectNotViewMenu
     */
    public String[] getCmn140selectNotViewMenu() {
        return cmn140selectNotViewMenu__;
    }
    /**
     * <p>cmn140selectNotViewMenu をセットします。
     * @param cmn140selectNotViewMenu cmn140selectNotViewMenu
     */
    public void setCmn140selectNotViewMenu(String[] cmn140selectNotViewMenu) {
        cmn140selectNotViewMenu__ = cmn140selectNotViewMenu;
    }
    /**
     * <p>cmn140selectViewMenu を取得します。
     * @return cmn140selectViewMenu
     */
    public String[] getCmn140selectViewMenu() {
        return cmn140selectViewMenu__;
    }
    /**
     * <p>cmn140selectViewMenu をセットします。
     * @param cmn140selectViewMenu cmn140selectViewMenu
     */
    public void setCmn140selectViewMenu(String[] cmn140selectViewMenu) {
        cmn140selectViewMenu__ = cmn140selectViewMenu;
    }
    /**
     * <p>cmn140viewMenuLabel を取得します。
     * @return cmn140viewMenuLabel
     */
    public List<LabelValueBean> getCmn140viewMenuLabel() {
        return cmn140viewMenuLabel__;
    }
    /**
     * <p>cmn140viewMenuLabel をセットします。
     * @param cmn140viewMenuLabel cmn140viewMenuLabel
     */
    public void setCmn140viewMenuLabel(List<LabelValueBean> cmn140viewMenuLabel) {
        cmn140viewMenuLabel__ = cmn140viewMenuLabel;
    }
    /**
     * <p>cmn140viewMenuList を取得します。
     * @return cmn140viewMenuList
     */
    public String[] getCmn140viewMenuList() {
        return cmn140viewMenuList__;
    }
    /**
     * <p>cmn140viewMenuList をセットします。
     * @param cmn140viewMenuList cmn140viewMenuList
     */
    public void setCmn140viewMenuList(String[] cmn140viewMenuList) {
        cmn140viewMenuList__ = cmn140viewMenuList;
    }

    /**
     * <br>[機  能] 入力チェック(左矢印ボタン、上矢印ボタン、下矢印ボタンクリック時)を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @return エラー
     */
    public ActionErrors validateCheck(HttpServletRequest req) {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage();
        //表示プラグイン(選択)
        if (cmn140selectViewMenu__ == null || cmn140selectViewMenu__.length == 0) {
            //表示メニュー
            String textViewMenu = gsMsg.getMessage(req, "cmn.cmn140.3");

            ActionMessage msg = new ActionMessage("error.select.required.text",
                    textViewMenu);
            StrutsUtil.addMessage(errors, msg, "cmn140selectViewMenu");
            return errors;
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

        GsMessage gsMsg = new GsMessage();

        //非表示プラグイン(選択)
        if (cmn140selectNotViewMenu__ == null || cmn140selectNotViewMenu__.length == 0) {
            //非表示メニュー
            String textNotViewMenu = gsMsg.getMessage(req, "cmn.cmn140.2");

            ActionMessage msg = new ActionMessage("error.select.required.text",
                    textNotViewMenu);
            StrutsUtil.addMessage(errors, msg, "cmn140selectNotViewMenu");
            return errors;
        }

        return errors;
    }

}