package jp.groupsession.v2.bbs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BulletinForumDiskModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン画面 インフォメーションへメッセージを表示するクラス
 * <br>[解  説] 掲示板に関するメッセージを表示します。
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsMainInfoMessage implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsMainInfoMessage.class);

     /** 掲示板投稿メインURL */
    public static final String BULLETIN_MAIN_URL = "../bulletin/bbs010.do";
    /** 掲示板 スレッド一覧URL */
   public static final String BULLETIN_THRELIST_URL = "../bulletin/bbs060.do";

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public BbsMainInfoMessage() {
    }

    /**
     * <br>[機  能] インフォメーション用メッセージを取得する。
     * <br>[解  説] メインへは未開封のメッセージ件数を表示します。
     * <br>未開封のメッセージがない場合は表示しません。
     * <br>[備  考]
     * @param paramMap パラメータ
     * @param usid ユーザSID
     * @param con DBコネクション
     * @param gsMsg Gsメッセージ
     * @param reqMdl リクエストモデル
     * @return メッセージのリスト
     */
    public List<MainInfoMessageModel> getMessage(Map<String, Object> paramMap,
            int usid, Connection con, GsMessage gsMsg, RequestModel reqMdl) {
        ArrayList<MainInfoMessageModel> msgList = null;
        String linkUrl = BULLETIN_MAIN_URL;

        boolean autoCommit = false;
        try {
            try {
                autoCommit = con.getAutoCommit();
                if (!autoCommit) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                log__.error("auto commitの設定に失敗", e);
            }

            //新着の件数を取得する。
            BbsBiz bbsBiz = new BbsBiz();
            List<Integer> groupSidList = new ArrayList<Integer>();
            List<BbsForInfModel> bfiMdlList = new ArrayList<BbsForInfModel>();
            BulletinDao bbsDao = new BulletinDao(con);
            int count = 0;
            try {
                //ユーザがメンバーに含まれているフォーラムのSIDを取得
                bfiMdlList = bbsBiz.getForumListOfUser(con, usid);
                for (BbsForInfModel bfiMdl : bfiMdlList) {
                    groupSidList.add(bfiMdl.getBfiSid());
                }

                count = bbsDao.getThreadListCnt(usid, groupSidList);
            } catch (SQLException e) {
                log__.error("新着掲示板カウントの取得に失敗", e);
            }

            if (count <= 0) {
                return null;
            }

            String textBbs = gsMsg.getMessage("cmn.bulletin");
            String[] cnt = new String[1];
            cnt[0] = String.valueOf(count);
            String textMessage = gsMsg.getMessage("bbs.27", cnt);

            //メッセージを作成する。
            MainInfoMessageModel model = new MainInfoMessageModel();
            model.setPluginId(GSConst.PLUGIN_ID_BULLETIN);
            model.setPluginName(textBbs);
            model.setLinkUrl(linkUrl);
            StringBuilder msgBuf = new StringBuilder();
            msgBuf.append("[ " + textBbs + " ] ");
            msgBuf.append(textMessage);
            model.setMessage(msgBuf.toString());
            model.setOriginalMessage(textMessage);
            CommonBiz cmnBiz = new CommonBiz();
            String iconUrl = cmnBiz.getPluginIconUrl(GSConstBulletin.PLUGIN_ID_BULLETIN,
                                                                        reqMdl.getDomain());
            model.setIcon(iconUrl);

            msgList = new ArrayList<MainInfoMessageModel>();
            msgList.add(model);

            //ディスク使用量警告
            try {
                List<BulletinForumDiskModel> forumDiskList
                    = bbsDao.getWarnForumList(usid,
                                    cmnBiz.isPluginAdmin(con, reqMdl.getSmodel(),
                                                            GSConstBulletin.PLUGIN_ID_BULLETIN));

                for (BulletinForumDiskModel forumDiskData : forumDiskList) {
                    if (bbsBiz.checkForumWarnDisk(forumDiskData)) {
                        model = new MainInfoMessageModel();
                        model.setPluginId(GSConst.PLUGIN_ID_BULLETIN);
                        model.setLinkUrl(BULLETIN_THRELIST_URL + "?bbs010forumSid="
                                        + forumDiskData.getBfiSid());
                        msgBuf = new StringBuilder();
                        msgBuf.append("  ・ ");
                        msgBuf.append(forumDiskData.getBfiName());
                        msgBuf.append(": ");
                        msgBuf.append(
                                gsMsg.getMessage("wml.250",
                                                    new String[] {
                                                        Integer.toString(
                                                            forumDiskData.getBfiWarnDiskTh())}));

                        model.setMessage(msgBuf.toString());
                        model.setOriginalMessage(msgBuf.toString());
                        msgList.add(model);
                    }
                }
            } catch (SQLException e) {
                log__.error("ディスク容量警告対象フォーラムの表示に失敗", e);
            }
        } finally {
            if (!autoCommit) {
                try {
                    con.setAutoCommit(false);
                } catch (SQLException e) {
                    log__.error("auto commitの設定に失敗", e);
                }
            }
        }

        return msgList;
    }

}
