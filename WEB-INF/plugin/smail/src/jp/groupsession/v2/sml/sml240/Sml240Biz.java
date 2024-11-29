package jp.groupsession.v2.sml.sml240;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlAccountAutoDestDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlPushUconfDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール アカウントマネージャー画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml240Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml240Biz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Sml240ParamModel paramMdl,
                            RequestModel reqMdl) throws SQLException {
        //1ページ最大30件
        int limit = GSConstSmail.LIMIT_DSP_ACCOUNT;

        if (paramMdl.getSml240popKbn() == 1) {
            //ポップアップ時は10件
            limit = 10;
        }

        //検索結果を取得する
        Sml240SearchModel searchMdl = new Sml240SearchModel();
        searchMdl.setKeyword(paramMdl.getSml240svKeyword());
        searchMdl.setGrpSid(paramMdl.getSml240svGroup());
        searchMdl.setUserSid(paramMdl.getSml240svUser());
        searchMdl.setSortKey(paramMdl.getSml240sortKey());
        searchMdl.setOrder(paramMdl.getSml240order());
        searchMdl.setMaxCount(limit);
        long maxCount = 0;

        //件数カウント
        Sml240Dao dao = new Sml240Dao(con);
        maxCount = dao.recordCount(searchMdl);
        log__.debug("表示件数 = " + maxCount);

        //現在ページ（ページコンボのvalueを設定）
        int nowPage = paramMdl.getSml240pageTop();
        //結果取得開始カーソル位置
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        if (maxPageNum > 1) {
            paramMdl.setSml240pageDspFlg(true);
        }
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
        }
        searchMdl.setPage(start);

        //ページング
        paramMdl.setSml240pageTop(nowPage);
        paramMdl.setSml240pageBottom(nowPage);
        paramMdl.setPageCombo(PageUtil.createPageOptions(maxCount, limit));

        List<Sml240AccountModel> accountList = dao.getAccountList(searchMdl, reqMdl);
        paramMdl.setAccountList(accountList);

        //グループコンボを設定
        GsMessage gsMsg = new GsMessage(reqMdl);
        GroupBiz grpBiz = new GroupBiz();
        List<LabelValueBean> groupCombo = new ArrayList<LabelValueBean>();
        groupCombo.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));

        ArrayList<GroupModel> grpList = grpBiz.getGroupCombList(con);
        for (GroupModel grpMdl : grpList) {
            LabelValueBean label = new LabelValueBean(grpMdl.getGroupName(),
                                                    String.valueOf(grpMdl.getGroupSid()));
            groupCombo.add(label);
        }
        paramMdl.setGroupCombo(groupCombo);

        //ユーザコンボを設定
        List<LabelValueBean> userCombo = new ArrayList<LabelValueBean>();

        UserBiz userBiz = new UserBiz();
        userCombo.addAll(userBiz.getUserLabelListNoSysUser(con, gsMsg, paramMdl.getSml240group()));
        paramMdl.setUserCombo(userCombo);
    }
    /**
     * <br>[機  能] メッセージに表示するアカウント名を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param accountSidList アカウントSID
     * @return アカウント名
     * @throws SQLException SQL実行時例外
     */
    public String getMsgAccountTitle(Connection con, String[] accountSidList)
    throws SQLException {

        Sml240Dao wml030Dao = new Sml240Dao(con);
        List<String> titleList = wml030Dao.getAccountNameList(accountSidList);

        String msgTitle = "";
        for (int idx = 0; idx < titleList.size(); idx++) {

            //最初の要素以外は改行を挿入
            if (idx > 0) {
                msgTitle += "<br>";
            }

            msgTitle += "・ " + StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(titleList.get(idx), ""));
        }

        return msgTitle;
    }

    /**
     * <br>[機  能] アカウントの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteAccount(Connection con, Sml240ParamModel paramMdl, int userSid)
    throws SQLException {

        log__.info("アカウント削除開始");

        boolean commit = false;
        try {
            SmlAccountDao accountDao = new SmlAccountDao(con);
            accountDao.updateJkbn(paramMdl.getSml240selectAcount(), GSConstSmail.SAC_JKBN_DELETE);
            if (paramMdl.getSml240selectAcount() != null) {
                for (String sacSidStr : paramMdl.getSml240selectAcount()) {
                    int sacSid = NullDefault.getInt(sacSidStr, -1);

                    //ショートメール自動送信先を物理削除
                    SmlAccountAutoDestDao saaDao = new SmlAccountAutoDestDao(con);
                    saaDao.delete(sacSid);

                    //プッシュ通知設定を削除
                    SmlPushUconfDao spuDao = new SmlPushUconfDao(con);
                    spuDao.deleteAccount(sacSid);

                }
            }
            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.info("アカウントの削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        log__.info("アカウント削除終了");
    }

    /**
     * <br>[機  能] アカウント削除可能チェック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return jsonData JSONObject
     */
    public ActionErrors checkCanDelAct(Sml240ParamModel paramMdl,
                             RequestModel reqMdl,
                             Connection con)
        throws SQLException {

        ActionErrors errors = new ActionErrors();
        SmlAccountDao sad = new SmlAccountDao(con);
        SmlAccountModel actMdl = null;

        for (String sid : paramMdl.getSml240selectAcount()) {

            if (ValidateUtil.isNumber(sid)) {
                actMdl = sad.select(Integer.valueOf(sid));

                if (actMdl != null && actMdl.getUsrSid() > 0) {
                    ActionMessage msg = new ActionMessage(
                            "error.common.no.delete",
                            StringUtilHtml.transToHTmlPlusAmparsant(actMdl.getSacName()));
                    StrutsUtil.addMessage(errors, msg, String.valueOf(actMdl.getSacSid()));
                }
            }

        }
        return errors;
    }

    /**
     * <br>[機  能] ログ用メッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param sidList SIDリスト
     * @param con コネクション
     * @return メッセージ
     * @throws SQLException SQL実行時例外
     */
    public String getLogMessage(String[] sidList, Connection con)
    throws SQLException {

        String msg = "";
        SmlAccountDao accountDao = new SmlAccountDao(con);
        for (String sid : sidList) {
            SmlAccountModel mdl = accountDao.select(Integer.parseInt(sid));
            if (msg.length() > 0) {
                msg += "\r\n";
            }
            msg += mdl.getSacName();
        }
        return msg;
    }
}
