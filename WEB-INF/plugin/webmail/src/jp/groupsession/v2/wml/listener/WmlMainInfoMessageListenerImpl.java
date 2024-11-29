package jp.groupsession.v2.wml.listener;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.model.WmlMainInfoMessageModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;

/**
 * <br>[機  能] メイン画面 インフォメーションへメッセージを表示するクラス
 * <br>[解  説] WEBメールに関するメッセージを表示します。
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlMainInfoMessageListenerImpl implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(WmlMainInfoMessageListenerImpl.class);

    /** メール一覧URL */
    public static final String WML_MAILLIST_URL = "../webmail/wml010.do";

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public WmlMainInfoMessageListenerImpl() {
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
                                                int usid, Connection con,
                                                GsMessage gsMsg, RequestModel reqMdl) {
        List<MainInfoMessageModel> msgList = new ArrayList<MainInfoMessageModel>();

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

            //未読メール件数を取得する
            WmlAccountDao accountDao = new WmlAccountDao(con);
            int[] wacSidList = accountDao.getAccountSidList(usid);
            if (wacSidList != null && wacSidList.length > 0) {
                WebmailDao wmlDao = new WebmailDao(con);
                List<WmlMainInfoMessageModel> wmlMsgList
                        = wmlDao.getMainInfoMessageList(usid, wacSidList);

                if (!wmlMsgList.isEmpty()) {
                    MainInfoMessageModel model = new MainInfoMessageModel();
                    model.setPluginId(GSConst.PLUGINID_WML);
                    model.setPluginName(gsMsg.getMessage("wml.wml010.25"));
                    model.setMessage(gsMsg.getMessage("wml.218"));
                    model.setOriginalMessage(gsMsg.getMessage("wml.info.msg"));
                    model.setLinkUrl(WML_MAILLIST_URL);
                    CommonBiz cmnBiz = new CommonBiz();
                    String iconUrl = cmnBiz.getPluginIconUrl(GSConstWebmail.PLUGIN_ID_WEBMAIL,
                                                                                reqMdl.getDomain());
                    model.setIcon(iconUrl);
                    msgList.add(model);

                    //新着メール
                    for (WmlMainInfoMessageModel wmlMsgData : wmlMsgList) {
                        model = new MainInfoMessageModel();
                        model.setPluginId(GSConst.PLUGINID_WML);
                        model.setLinkUrl(WML_MAILLIST_URL + "?wmlViewAccount="
                                        + wmlMsgData.getWacSid());
                        StringBuilder msgBuf = new StringBuilder();

                        msgBuf.append("  ・ ");
                        msgBuf.append(wmlMsgData.getWacName());
                        msgBuf.append(": ");
                        msgBuf.append(String.valueOf(wmlMsgData.getNoReadCount()));
                        msgBuf.append(gsMsg.getMessage("cmn.number") + " ");

                        model.setMessage(msgBuf.toString());
                        model.setOriginalMessage(msgBuf.toString());
                        msgList.add(model);
                    }

                    //ディスク使用量警告
                    WmlAdmConfDao wacDao = new WmlAdmConfDao(con);
                    WmlAdmConfModel admConfMdl = wacDao.selectAdmData();
                    if (admConfMdl.getWadWarnDisk() == GSConstWebmail.WAD_WARN_DISK_YES) {
                        WmlBiz wmlBiz = new WmlBiz();

                        //管理者設定 ディスク容量警告 閾値
                        int diskWarnTh = admConfMdl.getWadWarnDiskTh();
                        for (WmlMainInfoMessageModel wmlMsgData : wmlMsgList) {

                            BigDecimal limitSize = new BigDecimal(
                                    wmlBiz.getDiskLimitSize(con, wmlMsgData.getWacSid(), admConfMdl)
                                    * 1024 * 1024);
                            if (limitSize.longValue() <= 0) {
                                continue;
                            }

                            BigDecimal useDiskSize = new BigDecimal(wmlMsgData.getWdsSize());
                            limitSize = limitSize.multiply(
                                    new BigDecimal(diskWarnTh));
                            limitSize = limitSize.divide(
                                    new BigDecimal(100), 2, RoundingMode.HALF_UP);

                            if (useDiskSize.compareTo(limitSize) >= 0) {
                                model = new MainInfoMessageModel();
                                model.setPluginId(GSConst.PLUGINID_WML);
                                model.setLinkUrl(WML_MAILLIST_URL + "?wmlViewAccount="
                                                + wmlMsgData.getWacSid());
                                StringBuilder msgBuf = new StringBuilder();
                                msgBuf.append("  ・ ");
                                msgBuf.append(wmlMsgData.getWacName());
                                msgBuf.append(": ");
                                msgBuf.append(
                                        gsMsg.getMessage("wml.250",
                                                                    new String[] {
                                                                    Integer.toString(diskWarnTh)}));
                                model.setMessage(msgBuf.toString());
                                model.setOriginalMessage(msgBuf.toString());

                                msgList.add(model);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            log__.error("未読メール件数の取得に失敗", e);

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
