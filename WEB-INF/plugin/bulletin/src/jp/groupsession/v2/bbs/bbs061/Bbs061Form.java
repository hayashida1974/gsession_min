package jp.groupsession.v2.bbs.bbs061;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs060.Bbs060Form;
import jp.groupsession.v2.bbs.model.ForumModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 スレッド一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs061Form extends Bbs060Form {
    /** フォーラムSID*/
    private int checkForum__ = -1;
    /** 選択可能Level */
    private String selectLevel__;
    /** グループツリー */
    private ArrayList<ForumModel> moveForumList__ = null;
    /** 移動完了フラグ */
    private int bbs061Kanryo__ = GSConstBulletin.MOVE_THREAD_NOT_COMP;
    /**
     * <p>checkForum を取得します。
     * @return checkForum
     */
    public int getCheckForum() {
        return checkForum__;
    }
    /**
     * <p>checkForum をセットします。
     * @param checkForum checkForum
     */
    public void setCheckForum(int checkForum) {
        checkForum__ = checkForum;
    }
    /**
     * <p>selectLevel を取得します。
     * @return selectLevel
     */
    public String getSelectLevel() {
        return selectLevel__;
    }
    /**
     * <p>selectLevel をセットします。
     * @param selectLevel selectLevel
     */
    public void setSelectLevel(String selectLevel) {
        selectLevel__ = selectLevel;
    }
    /**
     * <p>moveForumList を取得します。
     * @return moveForumList
     * @see jp.groupsession.v2.bbs.bbs061.Bbs061Form#moveForumList__
     */
    public ArrayList<ForumModel> getMoveForumList() {
        return moveForumList__;
    }
    /**
     * <p>moveForumList をセットします。
     * @param moveForumList moveForumList
     * @see jp.groupsession.v2.bbs.bbs061.Bbs061Form#moveForumList__
     */
    public void setMoveForumList(ArrayList<ForumModel> moveForumList) {
        moveForumList__ = moveForumList;
    }

    /**
     * <p>bbs061Kanryo を取得します。
     * @return bbs061Kanryo
     * @see jp.groupsession.v2.bbs.bbs061.Bbs061Form#bbs061Kanryo__
     */
    public int getBbs061Kanryo() {
        return bbs061Kanryo__;
    }
    /**
     * <p>bbs061Kanryo をセットします。
     * @param bbs061Kanryo bbs061Kanryo
     * @see jp.groupsession.v2.bbs.bbs061.Bbs061Form#bbs061Kanryo__
     */
    public void setBbs061Kanryo(int bbs061Kanryo) {
        bbs061Kanryo__ = bbs061Kanryo;
    }
    /**
     * <p> 移動先フォーラムに対する権限チェック
     * @param req リクエスト
     * @param con コネクション
     * @param buMdl セッションユーザモデル
     * @param adminFlg プラグイン管理者フラグ
     * @throws Exception 例外処理
     * @return ActionErrors エラー
     * */
    public ActionErrors validateForum(
            HttpServletRequest req,
            Connection con,
            BaseUserModel buMdl,
            boolean adminFlg)
            throws Exception {
        ActionErrors errors = new ActionErrors();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage();
        BbsBiz bbsBiz = new BbsBiz(con);
        // フォーラム存在チェック
        if (!bbsBiz.isCheckExistForum(con, checkForum__)) {
            String msgKey = "error.nothing.selected";
            msg = new ActionMessage(msgKey, gsMsg.getMessage("bbs.3"));
            StrutsUtil.addMessage(errors, msg, msgKey);
            return errors;
        }
        // フォーラム権限チェック
        if (!bbsBiz.isForumEditAuth(con, checkForum__, buMdl)) {
            String msgKey = "error.access.forum.user.write";
            msg = new ActionMessage(msgKey);
            StrutsUtil.addMessage(errors, msg, msgKey);
            return errors;
        }
        // フォーラムディスク容量チェック
        Bbs061Biz bbs061Biz = new Bbs061Biz();
        List<Integer> threadSidList = new ArrayList<Integer>();
        for (String sid : getBbs060ChkInfSid()) {
            threadSidList.add(Integer.parseInt(sid));
        }
        if (!bbs061Biz.isDiskSizeForMove(con, checkForum__, threadSidList)) {
            String msgKey = "error.over.limitsize.bbsdata.move";
            msg = new ActionMessage(msgKey, gsMsg.getMessage("bbs.2"));
            StrutsUtil.addMessage(errors, msg, msgKey);
            return errors;
        }
        // 未選択チェック
        if (checkForum__ < 1) {
            String msgKey = "error.select.required.text";
            msg = new ActionMessage(msgKey, gsMsg.getMessage("bbs.3"));
            StrutsUtil.addMessage(errors, msg, msgKey);
            return errors;
        }

        return errors;
    }
}
