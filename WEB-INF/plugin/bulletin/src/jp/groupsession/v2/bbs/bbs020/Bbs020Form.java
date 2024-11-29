package jp.groupsession.v2.bbs.bbs020;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.bbs010.Bbs010Form;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BulletinDspModel;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

/**
 * <br>[機  能] 掲示板 フォーラム管理画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs020Form extends Bbs010Form {

    /** ページコンボ上 */
    private int bbs020page1__ = 0;
    /** ページコンボ下 */
    private int bbs020page2__ = 0;
    /** フォーラムSID */
    private int bbs020forumSid__ = 0;
    /** 表示順ラジオボタン */
    private int bbs020indexRadio__ = 0;
    /** 削除フォーラム名 */
    private String bbs020delForumName__ = null;

    /**
     * <p>bbs020indexRadio を取得します。
     * @return bbs020indexRadio
     */
    public int getBbs020indexRadio() {
        return bbs020indexRadio__;
    }
    /**
     * <p>bbs020indexRadio をセットします。
     * @param bbs020indexRadio bbs020indexRadio
     */
    public void setBbs020indexRadio(int bbs020indexRadio) {
        bbs020indexRadio__ = bbs020indexRadio;
    }
    /**
     * @return bbs020page1 を戻します。
     */
    public int getBbs020page1() {
        return bbs020page1__;
    }
    /**
     * @param bbs020page1 設定する bbs020page1。
     */
    public void setBbs020page1(int bbs020page1) {
        bbs020page1__ = bbs020page1;
    }
    /**
     * @return bbs020page2 を戻します。
     */
    public int getBbs020page2() {
        return bbs020page2__;
    }
    /**
     * @param bbs020page2 設定する bbs020page2。
     */
    public void setBbs020page2(int bbs020page2) {
        bbs020page2__ = bbs020page2;
    }
    /**
     * @return bbs020forumSid を戻します。
     */
    public int getBbs020forumSid() {
        return bbs020forumSid__;
    }
    /**
     * @param bbs020forumSid 設定する bbs020forumSid。
     */
    public void setBbs020forumSid(int bbs020forumSid) {
        bbs020forumSid__ = bbs020forumSid;
    }
    /**
     * <p>bbs020delForumName を取得します。
     * @return bbs020delForumName
     */
    public String getBbs020delForumName() {
        return bbs020delForumName__;
    }
    /**
     * <p>bbs020delForumName をセットします。
     * @param bbs020delForumName bbs020delForumName
     */
    public void setBbs020delForumName(String bbs020delForumName) {
        bbs020delForumName__ = bbs020delForumName;
    }


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

        //削除対象のフォーラムが、子フォーラムを持つフォーラムか
        BulletinDao bbsDao = new BulletinDao(con);
        int childCount = bbsDao.getChildForumCount(bbs020forumSid__, true, null);

        if (childCount > 0) {
            BbsBiz biz = new BbsBiz();
            BulletinDspModel btMdl = biz.getForumData(con, bbs020forumSid__);

            String prefix = "bbs020Sid";
            //「[{0}]は子フォーラムを持つため削除できません。」
            msg = new ActionMessage("error.forum.parent.no.delete",
                    StringUtilHtml.transToHTmlPlusAmparsant(btMdl.getBfiName()));
            errors.add(prefix + "error.forum.parent.no.delete", msg);
        }

        return errors;
    }
}
