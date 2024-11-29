package jp.groupsession.v2.man;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.ConfigBundle;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnDiskadminDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnDiskadminModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン画面 インフォメーションへディスク容量の警告メッセージを表示するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ManDiskInfoMessage implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ManDiskInfoMessage.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public ManDiskInfoMessage() {
    }

    /**
     * <br>[機  能] インフォメーション用メッセージを取得する。
     * <br>[解  説] ディスクの空き容量が設定値以下の場合、警告メッセージを表示します。
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
        String linkUrl = "../main/man060.do";

        boolean autoCommit = false;
        try {
            try {
                autoCommit = con.getAutoCommit();
                if (!autoCommit) {
                    con.setAutoCommit(true);
                }
            } catch (SQLException e) {
                log__.info("auto commitの設定に失敗", e);
            }

            //一般ユーザの場合は警告メッセージを表示しない
            GroupDao gdao = new GroupDao(con);
            if (!gdao.isBelongAdmin(usid)) {
                return null;
            }

            //未確認の件数を取得する。
            CmnDiskadminDao diskDao = new CmnDiskadminDao(con);
            CmnDiskadminModel diskMdl = diskDao.select();
            if (diskMdl != null) {

                String realPath = ConfigBundle.getValue("GSDATA_DIR");
                if (StringUtil.isNullZeroString(realPath)) {
                    realPath = (String) paramMap.get("realPath");
                }

                String warnMsg = null;
                int warnSize = diskMdl.getDskValue();
                if (diskMdl.getDskType() == GSConstMain.DISKWARN_TYPE_CAPACITY
                && warnSize > 0) {
//                    long freeSize = FileSystemUtils.freeSpaceKb(realPath);
                    long freeSize = new File(realPath).getFreeSpace() / 1024;

                    if ((freeSize / 1024) <= warnSize) {

                        if (warnSize < 1000) {
                            warnMsg = String.valueOf(warnSize).concat("MB");
                        } else {
                            warnMsg = String.valueOf(warnSize / 1000).concat("GB");
                        }
                    }
                }

                //メッセージを作成する。
                if (!StringUtil.isNullZeroString(warnMsg)) {
                    MainInfoMessageModel model = new MainInfoMessageModel();
                    model.setLinkUrl(linkUrl);
                    StringBuilder msgBuf = new StringBuilder();
                    msgBuf.append("[ ");
                    msgBuf.append(gsMsg.getMessage("cmn.admin.setting"));
                    msgBuf.append(" ] ");
                    msgBuf.append(gsMsg.getMessage("main.src.35", new String[] {warnMsg}));
                    model.setMessage(msgBuf.toString());
                    model.setOriginalMessage(msgBuf.toString());
                    model.setPluginId(GSConst.PLUGINID_MAIN);
                    model.setPluginName(gsMsg.getMessage("cmn.main"));
                    model.setIcon("../main/images/classic/menu_icon_single_info.gif");

                    msgList = new ArrayList<MainInfoMessageModel>();
                    msgList.add(model);
                }

            }
        } catch (Exception e) {
            log__.error("ディスク容量警告メッセージの表示に失敗", e);
        } finally {
            if (!autoCommit) {
                try {
                    con.setAutoCommit(false);
                } catch (SQLException e) {
                    log__.info("auto commitの設定に失敗", e);
                }
            }
        }

        return msgList;
    }

}
