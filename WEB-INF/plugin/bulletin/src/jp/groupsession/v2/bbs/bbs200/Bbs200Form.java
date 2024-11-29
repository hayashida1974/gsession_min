package jp.groupsession.v2.bbs.bbs200;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs110.Bbs110Form;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 管理者設定 初期値設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs200Form extends Bbs110Form {
    /** 初期化フラグ*/
    private int bbs200Init__;

    /** 投稿タイプ初期値 設定種別 */
    private int bbs200IniPostTypeKbn__ = GSConstBulletin.BAC_INI_POST_TYPE_KBN_USER;
    /** 投稿タイプ初期値 */
    private int bbs200IniPostType__ = GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN;

    /**
     * <br>[機  能] 入力チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateCheck(
            HttpServletRequest req, Connection con)
                    throws SQLException {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();

        if (!(bbs200IniPostTypeKbn__ == GSConstBulletin.BAC_INI_POST_TYPE_KBN_USER
                || bbs200IniPostTypeKbn__ == GSConstBulletin.BAC_INI_POST_TYPE_KBN_ADM)) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.37"));
            errors.add("error.select.forum.radio", msg);
            return errors;
        }

        if (!(bbs200IniPostType__ == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN
                || bbs200IniPostType__ == GSConstBulletin.CONTENT_TYPE_TEXT_HTML)) {
            msg = new ActionMessage(
                    "error.select.forum.radio", gsMsg.getMessage(req, "bbs.37"));
            errors.add("error.select.forum.radio", msg);
            return errors;
        }
        return errors;
    }

    /**
     * <p>bbs200IniPostTypeKbn を取得します。
     * @return bbs200IniPostTypeKbn
     */
    public int getBbs200IniPostTypeKbn() {
        return bbs200IniPostTypeKbn__;
    }
    /**
     * <p>bbs200IniPostTypeKbn をセットします。
     * @param bbs200IniPostTypeKbn bbs200IniPostTypeKbn
     */
    public void setBbs200IniPostTypeKbn(int bbs200IniPostTypeKbn) {
        bbs200IniPostTypeKbn__ = bbs200IniPostTypeKbn;
    }

    /**
     * <p>bbs200IniPostType を取得します。
     * @return bbs200IniPostType
     */
    public int getBbs200IniPostType() {
        return bbs200IniPostType__;
    }
    /**
     * <p>bbs200IniPostType をセットします。
     * @param bbs200IniPostType bbs200IniPostType
     */
    public void setBbs200IniPostType(int bbs200IniPostType) {
        bbs200IniPostType__ = bbs200IniPostType;
    }
    /**
     * <p>bbs200Init を取得します。
     * @return bbs200Init
     */
    public int getBbs200Init() {
        return bbs200Init__;
    }
    /**
     * <p>bbs200Init をセットします。
     * @param bbs200Init bbs200Init
     */
    public void setBbs200Init(int bbs200Init) {
        bbs200Init__ = bbs200Init;
    }

}