package jp.groupsession.v2.man.man110;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.GSValidateMain;
import jp.groupsession.v2.man.man100.Man100Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] メイン 管理者設定 役職登録画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man110Form extends Man100Form {

    //入力項目
    /** 役職コード */
    private String man110posCode__;
    /** 役職名 */
    private String man110posName__;
    /** 備考 */
    private String man110bikou__;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @throws SQLException SQL実行時例外
     * @return エラー
     */
    public ActionErrors validateMan110(Connection con, HttpServletRequest req)
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        //役職コード
        GSValidateMain.validateIDStr(errors,
                man110posCode__,
                getInterMessage(req, GSConstMain.TEXT_POS_CODE),
                GSConstMain.MAX_LENGTH_POS_CODE);

        //役職コードの重複チェック
        int editPosSid = getMan100EditPosSid();
        CmnPositionDao cpDao = new CmnPositionDao(con);
        boolean codeExistFlg = cpDao.isExistPositionCode(man110posCode__, editPosSid);

        if (codeExistFlg) {
            ActionMessage msg =
                new ActionMessage("error.input.exist.data",
                        getInterMessage(req, GSConstMain.TEXT_POS_CODE));
            StrutsUtil.addMessage(errors, msg, "yakusyoku_code");
        }

        //役職名
        GSValidateMain.validateNameStr(errors,
                                       man110posName__,
                                       getInterMessage(req, GSConstMain.TEXT_POS_NAME),
                                       GSConstMain.MAX_LENGTH_POS);

        //役職名の重複チェック
        boolean existFlg = cpDao.isExistPositionName(man110posName__, editPosSid);

        if (existFlg) {
            ActionMessage msg =
                new ActionMessage("error.input.exist.data",
                        getInterMessage(req, GSConstMain.TEXT_POS_NAME));
            StrutsUtil.addMessage(errors, msg, "yakusyoku_name");
        }

        //備考
        GSValidateMain.validateTxtarea(errors,
                                       man110bikou__,
                                       getInterMessage(req, GSConstMain.TEXT_POS_CMT),
                                       GSConstMain.MAX_LENGTH_POS_CMT,
                                       false);

        return errors;
    }

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param req リクエスト
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateMan111(Connection con, HttpServletRequest req)
    throws SQLException {
        ActionErrors errors = new ActionErrors();

        //役職コード
        GSValidateMain.validateIDStr(errors,
                man110posCode__,
                getInterMessage(req, GSConstMain.TEXT_POS_CODE),
                GSConstMain.MAX_LENGTH_POS_CODE);

        //役職コードの重複チェック
        CmnPositionDao cpDao = new CmnPositionDao(con);
        boolean codeExistFlg = cpDao.isExistPositionCode(man110posCode__, -1);

        if (codeExistFlg) {
            ActionMessage msg =
                new ActionMessage("error.input.exist.data",
                        getInterMessage(req, GSConstMain.TEXT_POS_CODE));
            StrutsUtil.addMessage(errors, msg, "yakusyoku_code");
        }

        //役職名
        GSValidateMain.validateNameStr(errors,
                                       man110posName__,
                                       getInterMessage(req, GSConstMain.TEXT_POS_NAME),
                                       GSConstMain.MAX_LENGTH_POS);

        //役職名の重複チェック
        boolean existFlg = cpDao.isExistPositionName(man110posName__, -1);

        if (existFlg) {
            ActionMessage msg =
                new ActionMessage("error.input.exist.data",
                        getInterMessage(req, GSConstMain.TEXT_POS_NAME));
            StrutsUtil.addMessage(errors, msg, "yakusyoku_name");
        }


        return errors;
    }

    /**
     * <p>man110bikou を取得します。
     * @return man110bikou
     */
    public String getMan110bikou() {
        return man110bikou__;
    }
    /**
     * <p>man110bikou をセットします。
     * @param man110bikou man110bikou
     */
    public void setMan110bikou(String man110bikou) {
        man110bikou__ = man110bikou;
    }
    /**
     * <p>man110posName を取得します。
     * @return man110posName
     */
    public String getMan110posName() {
        return man110posName__;
    }
    /**
     * <p>man110posName をセットします。
     * @param man110posName man110posName
     */
    public void setMan110posName(String man110posName) {
        man110posName__ = man110posName;
    }

    /**
     * <p>man110posCode を取得します。
     * @return man110posCode
     */
    public String getMan110posCode() {
        return man110posCode__;
    }

    /**
     * <p>man110posCode をセットします。
     * @param man110posCode man110posCode
     */
    public void setMan110posCode(String man110posCode) {
        man110posCode__ = man110posCode;
    }

}
