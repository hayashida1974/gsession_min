package jp.groupsession.v2.sml;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン画面 インフォメーションへメッセージを表示するクラス
 * <br>[解  説] ショートメールに関するメッセージを表示します。
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlMainInfoMessage implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SmlMainInfoMessage.class);

    /** ショートメールメインURL */
    public static final String SMAIL_MAIN_URL = "../smail/sml010.do";

    /**
     * <p>コンストラクタ
     */
    public SmlMainInfoMessage() {
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
        String linkUrl = SMAIL_MAIN_URL;

        SmlAccountDao sacDao = new SmlAccountDao(con);
        List<SmlAccountModel> sacMdlList = null;

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

            //使用可能なアカウントを取得
            try {
                sacMdlList = sacDao.getAccountList(usid);
            } catch (SQLException e1) {
                log__.error("アカウントの取得に失敗", e1);
            }

            if (sacMdlList != null && !sacMdlList.isEmpty()) {

                boolean headMsgFlg = false;
                msgList = new ArrayList<MainInfoMessageModel>();

                for (SmlAccountModel sacMdl : sacMdlList) {
                    //未確認の件数を取得する。
                    SmlCommonBiz biz = new SmlCommonBiz(null);
                    int count = 0;
                    try {
                        count = biz.getUnopenedMsgCnt(sacMdl.getSacSid(), con);
                    } catch (SQLException e) {
                        log__.error("未開封メッセージカウントの取得に失敗", e);
                    }

                    if (count > 0) {

                        if (!headMsgFlg) {
                            MainInfoMessageModel model = new MainInfoMessageModel();
                            model.setPluginId(GSConst.PLUGINID_SML);
                            model.setPluginName(gsMsg.getMessage("cmn.shortmail"));
                            model.setMessage(gsMsg.getMessage("sml.171"));
                            model.setOriginalMessage(gsMsg.getMessage("sml.info.msg"));
                            CommonBiz cmnBiz = new CommonBiz();
                            model.setIcon(cmnBiz.getPluginIconUrl(GSConstSmail.PLUGIN_ID_SMAIL,
                                    reqMdl.getDomain()));
                            model.setLinkUrl(linkUrl);
                            msgList.add(model);
                            headMsgFlg = true;
                        }

                        //メッセージを作成する。
                        MainInfoMessageModel model = new MainInfoMessageModel();
                        StringBuilder msgBuf = new StringBuilder();

                        msgBuf.append("  ・ ");
                        msgBuf.append(sacMdl.getSacName());
                        msgBuf.append(": ");
                        msgBuf.append(String.valueOf(count));
                        msgBuf.append(gsMsg.getMessage("cmn.number") + " ");
                        model.setPluginId(GSConst.PLUGINID_SML);
                        model.setLinkUrl(linkUrl + "?smlViewAccount="
                                                 + sacMdl.getSacSid());
                        model.setMessage(msgBuf.toString());
                        model.setOriginalMessage(msgBuf.toString());

                        msgList.add(model);
                    }
                }
            }

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
