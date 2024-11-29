package jp.groupsession.v2.cmn.cmn140;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 個人設定 メニュー項目の設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn140Form extends AbstractGsForm {

    //入力項目
    /** 表示メニュー(選択) */
    private String[] cmn140selectViewMenu__ = null;
    /** 非表示メニュー(選択) */
    private String[] cmn140selectNotViewMenu__ = null;
    /** メニュー選択UI*/
    private MenuSelector cmn140viewMenuUI__ =
            MenuSelector.builder()
                //ユーザ選択 日本語名（入力チェック時に使用）
                .chainLabel(new GsMessageReq("cmn.display.settings", null))
                //選択対象設定
                .chainSelect(Select.builder()
                        //ユーザSID保管パラメータ名
                        .chainParameterName(
                                "cmn140viewMenuList")
                        )
                .build();


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
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return エラー
     * @throws SQLException
     */
    public ActionErrors validateCheck(
            RequestModel reqMdl,
            Connection con) throws SQLException {
        ActionErrors errors = new ActionErrors();

        cmn140viewMenuUI__.validate(
                new ParameterObject(this),
                reqMdl,
                con,
                errors,
                "cmn140viewMenuUI");


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
    /**
     * <p>cmn140viewMenuUI を取得します。
     * @return cmn140viewMenuUI
     * @see jp.groupsession.v2.cmn.cmn140.Cmn140Form#cmn140viewMenuUI__
     */
    public MenuSelector getCmn140viewMenuUI() {
        return cmn140viewMenuUI__;
    }
    /**
     * <p>cmn140viewMenuUI をセットします。
     * @param cmn140viewMenuUI cmn140viewMenuUI
     * @see jp.groupsession.v2.cmn.cmn140.Cmn140Form#cmn140viewMenuUI__
     */
    public void setCmn140viewMenuUI(MenuSelector cmn140viewMenuUI) {
        cmn140viewMenuUI__ = cmn140viewMenuUI;
    }
}
