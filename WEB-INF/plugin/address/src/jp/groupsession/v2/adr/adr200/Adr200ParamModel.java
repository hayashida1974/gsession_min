package jp.groupsession.v2.adr.adr200;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrLabelCategoryDao;
import jp.groupsession.v2.adr.model.AdrLabelCategoryModel;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 ラベル登録ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr200ParamModel extends AbstractParamModel {

    /** ラベル名称 */
    private String adr200labelName__ = null;
    /** 画面closeフラグ */
    private boolean adr200closeFlg__ = false;

    /** カテゴリ一覧 */
    private List<LabelValueBean> adr200category__ = null;
    /** 選択カテゴリ */
    private int adr200selectCategory__ = GSConstAddress.LABEL_CATEGORY_NOSET;
    /** 管理者判定フラグ 管理者=1、一般=0 */
    private int adr200Admin__ = 0;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(Connection con, HttpServletRequest req)
    throws SQLException {
        ActionErrors errors = new ActionErrors();
        GsMessage gsMsg = new GsMessage();

        //カテゴリ
        AdrLabelCategoryDao categoryDao = new AdrLabelCategoryDao(con);
        AdrLabelCategoryModel categoryData = categoryDao.select(adr200selectCategory__);
        if (categoryData == null || categoryData.getAlcSid() <= 0) {
            ActionMessage msg = new ActionMessage(
                    "error.input.notvalidate.data", gsMsg.getMessage(req, "cmn.category"));
            StrutsUtil.addMessage(errors, msg, "adr200selectCategory.error.input.notvalidate.data");
        }

        AdrValidateUtil.validateNecessary(errors, adr200labelName__,
                                        "adr200labelName", gsMsg.getMessage(req, "cmn.label"));

        if (errors.isEmpty()) {
            StringTokenizer st = new StringTokenizer(adr200labelName__, ",");
            while (st.hasMoreTokens()) {
                String labelName = st.nextToken();
                AdrValidateUtil.validateTextField(errors, labelName,
                                                "adr200labelName",
                                                gsMsg.getMessage(req, "cmn.label"),
                                                GSConstAddress.MAX_LENGTH_LABEL_NAME, true);
                if (!errors.isEmpty()) {
                    break;
                }
            }
        }
        return errors;
    }

    /**
     * <p>adr200labelName を取得します。
     * @return adr200labelName
     */
    public String getAdr200labelName() {
        return adr200labelName__;
    }

    /**
     * <p>adr200labelName をセットします。
     * @param adr200labelName adr200labelName
     */
    public void setAdr200labelName(String adr200labelName) {
        adr200labelName__ = adr200labelName;
    }

    /**
     * <p>adr200closeFlg を取得します。
     * @return adr200closeFlg
     */
    public boolean isAdr200closeFlg() {
        return adr200closeFlg__;
    }

    /**
     * <p>adr200closeFlg をセットします。
     * @param adr200closeFlg adr200closeFlg
     */
    public void setAdr200closeFlg(boolean adr200closeFlg) {
        adr200closeFlg__ = adr200closeFlg;
    }
    /**
     * <p>adr200category を取得します。
     * @return adr200category
     */
    public List<LabelValueBean> getAdr200category() {
        return adr200category__;
    }
    /**
     * <p>adr200category をセットします。
     * @param adr200category adr200category
     */
    public void setAdr200category(List<LabelValueBean> adr200category) {
        adr200category__ = adr200category;
    }
    /**
     * <p>adr200selectCategory を取得します。
     * @return adr200selectCategory
     */
    public int getAdr200selectCategory() {
        return adr200selectCategory__;
    }
    /**
     * <p>adr200selectCategory をセットします。
     * @param adr200selectCategory adr200selectCategory
     */
    public void setAdr200selectCategory(int adr200selectCategory) {
        adr200selectCategory__ = adr200selectCategory;
    }
    /**
     * adr200Adminを取得します。
     * @return adr200Admin
     */
    public int getAdr200Admin() {
        return adr200Admin__;
    }
    /**
     * adr200Adminをセットします。
     * @param adr200Admin adr200Admin
     * */
    public void setAdr200Admin(int adr200Admin) {
        adr200Admin__ = adr200Admin;
    }

}