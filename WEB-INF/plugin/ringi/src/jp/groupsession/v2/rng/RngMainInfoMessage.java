package jp.groupsession.v2.rng;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.MainInfoMessage;
import jp.groupsession.v2.man.MainInfoMessageModel;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン画面 インフォメーションへメッセージを表示するクラス
 * <br>[解  説] 稟議に関するメッセージを表示します。
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngMainInfoMessage implements MainInfoMessage {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngMainInfoMessage.class);

    /** 稟議メインURL */
    private static final String RINGI_MAIN_URL__ = "../ringi/rng010.do";

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public RngMainInfoMessage() {
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
        String linkUrl = RINGI_MAIN_URL__;

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

            //新着の件数を取得する。
            RingiDao dao = new RingiDao(con);
            int count = 0;
            try {
                count = dao.getRingiDataCount(usid, RngConst.RNG_MODE_JYUSIN);
            } catch (SQLException e) {
                log__.error("稟議受信件数の取得に失敗", e);
            }

            if (count <= 0) {
                return null;
            }
            String ringi = gsMsg.getMessage("rng.70");
            String msg = gsMsg.getMessage("rng.75", new String[] {String.valueOf(count)});

            //メッセージを作成する。
            MainInfoMessageModel model = new MainInfoMessageModel();
            model.setPluginId(GSConst.PLUGIN_ID_RINGI);
            model.setPluginName(gsMsg.getMessage("rng.62"));
            model.setLinkUrl(linkUrl);
            StringBuilder msgBuf = new StringBuilder();
            msgBuf.append(ringi + " ");
            msgBuf.append(msg);
            model.setMessage(msgBuf.toString());
            model.setOriginalMessage(msg);
            CommonBiz cmnBiz = new CommonBiz();
            model.setIcon(cmnBiz.getPluginIconUrl(RngConst.PLUGIN_ID_RINGI,
                    reqMdl.getDomain()));

            msgList = new ArrayList<MainInfoMessageModel>();
            msgList.add(model);
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
