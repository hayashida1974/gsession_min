package jp.groupsession.v2.man.man210;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parts.select.ISelectorUseForm;
import jp.groupsession.v2.cmn.ui.parts.select.Select;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] メイン 管理者設定 モバイル使用一括設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man210Form extends AbstractGsForm implements ISelectorUseForm {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man210Form.class);

    /** 対象区分 0 = 全 1 = 指定 */
    private int man210ObjKbn__ = 0;
    /** モバイル使用区分 0 = 可 1 = 不可 */
    private int man210UseKbn__ = 0;
    /** 個体識別番号制御 */
    private String man210NumCont__ = String.valueOf(GSConstUser.UID_DOESNT_CONTROL);
    /** 個体識別番号自動登録 */
    private String man210NumAutAdd__ = String.valueOf(GSConstUser.UID_AUTO_REG_NO);

    /** グループ */
    private String man210groupSid__ = null;
    /** 対象 メンバー */
    private String[] man210userSid__ = null;
    /** 対象 メンバー UI */
    private UserGroupSelector man210userSidUI__ =
            UserGroupSelector.builder()
                .chainLabel(new GsMessageReq("cmn.target", null))
                .chainType(EnumSelectType.USER)
                .chainSelect(
                        Select.builder()
                        .chainLabel(new GsMessageReq("cmn.member", null))
                        .chainParameterName(
                                "man210userSid")
                    )
                .chainGroupSelectionParamName("man210groupSid")
                .build();

    /**
     * <p>man210NumAutAdd を取得します。
     * @return man210NumAutAdd
     */
    public String getMan210NumAutAdd() {
        return man210NumAutAdd__;
    }
    /**
     * <p>man210NumAutAdd をセットします。
     * @param man210NumAutAdd man210NumAutAdd
     */
    public void setMan210NumAutAdd(String man210NumAutAdd) {
        man210NumAutAdd__ = man210NumAutAdd;
    }
    /**
     * <p>man210NumCont を取得します。
     * @return man210NumCont
     */
    public String getMan210NumCont() {
        return man210NumCont__;
    }
    /**
     * <p>man210NumCont をセットします。
     * @param man210NumCont man210NumCont
     */
    public void setMan210NumCont(String man210NumCont) {
        man210NumCont__ = man210NumCont;
    }
    /**
     * <p>man210ObjKbn を取得します。
     * @return man210ObjKbn
     */
    public int getMan210ObjKbn() {
        return man210ObjKbn__;
    }
    /**
     * <p>man210ObjKbn をセットします。
     * @param man210ObjKbn man210ObjKbn
     */
    public void setMan210ObjKbn(int man210ObjKbn) {
        man210ObjKbn__ = man210ObjKbn;
    }
    /**
     * <p>man210UseKbn を取得します。
     * @return man210UseKbn
     */
    public int getMan210UseKbn() {
        return man210UseKbn__;
    }
    /**
     * <p>man210UseKbn をセットします。
     * @param man210UseKbn man210UseKbn
     */
    public void setMan210UseKbn(int man210UseKbn) {
        man210UseKbn__ = man210UseKbn;
    }
    /**
     * <p>man210groupSid を取得します。
     * @return man210groupSid
     */
    public String getMan210groupSid() {
        return man210groupSid__;
    }
    /**
     * <p>man210groupSid をセットします。
     * @param man210groupSid man210groupSid
     */
    public void setMan210groupSid(String man210groupSid) {
        man210groupSid__ = man210groupSid;
    }
    /**
     * <p>man210userSid を取得します。
     * @return man210userSid
     */
    public String[] getMan210userSid() {
        return man210userSid__;
    }
    /**
     * <p>man210userSid をセットします。
     * @param man210userSid man210userSid
     */
    public void setMan210userSid(String[] man210userSid) {
        man210userSid__ = man210userSid;
    }
    /**
     * <p>man210userSidUI を取得します。
     * @return man210userSidUI
     */
    public UserGroupSelector getMan210userSidUI() {
        return man210userSidUI__;
    }
    /**
     * <p>man210userSidUI をセットします。
     * @param man210userSidUI man210userSidUI
     */
    public void setMan210userSidUI(UserGroupSelector man210userSidUI) {
        man210userSidUI__ = man210userSidUI;
    }
    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return エラー
     */
    public ActionErrors validateCheck(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        log__.debug("入力チェック開始");
        GsMessage gsMsg = new GsMessage(reqMdl);
        String eprefix = "man210userSid.";
        if (man210userSid__ == null) {
            //未入力チェック
            msg = new ActionMessage("error.select.required.text",
                    gsMsg.getMessage("cmn.named.user"));

            StrutsUtil.addMessage(errors, msg, eprefix
                    +  "error.select.required.text");
        }
        log__.debug("入力チェック終了");
        return errors;
    }
}