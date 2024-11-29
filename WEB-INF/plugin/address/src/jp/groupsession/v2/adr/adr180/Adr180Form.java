package jp.groupsession.v2.adr.adr180;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.util.AdrValidateUtil;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 役職登録ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr180Form extends ActionForm {

    /** 役職名 */
    private String adr180positionName__ = null;
    /** 画面closeフラグ */
    private boolean adr180closeFlg__ = false;
    /** 役職SID */
    private String adr180position__ = null;
    /** 管理者判定フラグ 管理者=1、一般=0 */
    private int adr180Admin__ = 0;

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

        //役職名
        AdrValidateUtil.validateTextField(errors, adr180positionName__, "adr180positionName",
                gsMsg.getMessage(req, "cmn.job.title"),
                GSConstAddress.MAX_LENGTH_POSITION_NAME, true);
        AdrPositionDao apDao = new AdrPositionDao(con);
        boolean existFlg = apDao.isExistPositionName(adr180positionName__, 0);

        if (existFlg) {
            ActionMessage msg =
                new ActionMessage("error.input.exist.data",
                        gsMsg.getMessage(req, "cmn.job.title"));
            StrutsUtil.addMessage(errors, msg, "yakusyoku_name");
        }

        return errors;
    }

    /**
     * <p>adr180positionName を取得します。
     * @return adr180positionName
     */
    public String getAdr180positionName() {
        return adr180positionName__;
    }
    /**
     * <p>adr180positionName をセットします。
     * @param adr180positionName adr180positionName
     */
    public void setAdr180positionName(String adr180positionName) {
        adr180positionName__ = adr180positionName;
    }
    /**
     * <p>adr180closeFlg を取得します。
     * @return adr180closeFlg
     */
    public boolean isAdr180closeFlg() {
        return adr180closeFlg__;
    }
    /**
     * <p>adr180closeFlg をセットします。
     * @param adr180closeFlg adr180closeFlg
     */
    public void setAdr180closeFlg(boolean adr180closeFlg) {
        adr180closeFlg__ = adr180closeFlg;
    }

    /**
     * @return adr180position
     */
    public String getAdr180position() {
        return adr180position__;
    }

    /**
     * @param adr180position 設定する adr180position
     */
    public void setAdr180position(String adr180position) {
        adr180position__ = adr180position;
    }

    /**
     * adr180Adminを取得します。
     * @return adr180Admin
     */
    public int getAdr180Admin() {
        return adr180Admin__;
    }

    /**
     * adr180Adminをセットします。
     * @param adr180Admin adr180Admin
     * */
    public void setAdr180Admin(int adr180Admin) {
        adr180Admin__ = adr180Admin;
    }
}