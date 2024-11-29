package jp.groupsession.v2.sml;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.usedsize.IUsedDetailListener;
import jp.groupsession.v2.man.man520.Man520DataModel;
import jp.groupsession.v2.sml.dao.SmlDatausedSumDao;
import jp.groupsession.v2.sml.model.SmlDatausedSumModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] データ使用量取得時に実行されるリスナーを実装
 * <br>[解  説] ショートメールについての処理を行う
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlUsedDetailListenerImpl implements IUsedDetailListener {
    /** データサイズMappingキー ショートメール メールサイズ */
    public static final String KEY_SML_MAIL = "main.useddisk.sml.mail";
    /** データサイズMappingキー ショートメール アカウントディスク使用量 */
    public static final String KEY_SML_ACCOUNT = "main.useddisk.sml.account";

    /**
     * <br>[機  能] データ使用量の詳細を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return データ使用量の詳細
     * @throws SQLException SQL実行時例外
     */
    public Map<String, Long> getDatails(Connection con) throws SQLException {
        Map<String, Long> detailMap = new LinkedHashMap<String, Long>();

        SmlDatausedSumDao dataUsedDao = new SmlDatausedSumDao(con);
        SmlDatausedSumModel totalDataMdl = dataUsedDao.getTotalData();
        if (totalDataMdl != null) {
            detailMap.put(KEY_SML_MAIL, totalDataMdl.getSmlMailSize());
            detailMap.put(KEY_SML_ACCOUNT, totalDataMdl.getSacDiscsizeSum());
        }

        return detailMap;
    }

    /**
     * <br>[機  能] プラグイン独自の集計マップを使用するかを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return true: プラグイン独自の集計マップを使用する, false: 共通の集計マップを使用する
     * @throws SQLException SQL実行時例外
     */
    public boolean isUseSenyoDetails(Connection con) throws SQLException {
        return true;
    }

    /**
     * <br>[機  能] プラグイン独自の集計マップHTMLを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @return プラグイン独自の集計マップHTML
     * @throws SQLException SQL実行時例外
     */
    public String getUseSenyoHtml(Connection con, RequestModel reqMdl) throws SQLException {
        Map<String, Long> detailMap = getDatails(con);
        Man520DataModel diskSizeMdl = new Man520DataModel();
        diskSizeMdl.setSizeMap(detailMap);


        GsMessage gsMsg = new GsMessage(reqMdl);

        StringBuilder sb = new StringBuilder("");
        sb.append("        <span>" + gsMsg.getMessage("main.man520.5") + "</span>");
        sb.append("        <table class=\"table-left w100 mt0 mb20\">");
        sb.append("          <tr>");
        sb.append("            <th class=\"txt_l w70\" name=\"5\">"
                + gsMsg.getMessage("main.useddisk.sml.mail") + "</th>");
        sb.append("            <td class=\"txt_r w30\">"
                + diskSizeMdl.getDetailSize("main.useddisk.sml.mail")
                + diskSizeMdl.getDetailSizeUnit("main.useddisk.sml.mail")
                + "</td>");
        sb.append("          </tr>");
        sb.append("        </table>");
        sb.append("        <span>"
                + gsMsg.getMessage("main.man520.6")
                + "<br>" + gsMsg.getMessage("main.man520.7")
                + "</span>");
        sb.append("        <table class=\"table-left w100 mt0\">");
        sb.append("          <tr>");
        sb.append("            <th class=\"txt_l w70\" name=\"5\">"
                + gsMsg.getMessage("main.useddisk.sml.account")
                + "</th>");
        sb.append("            <td class=\"txt_r w30\">"
                + diskSizeMdl.getDetailSize("main.useddisk.sml.account")
                + diskSizeMdl.getDetailSizeUnit("main.useddisk.sml.account")
                + "</td>");
        sb.append("          </tr>");
        sb.append("        </table>");

        return sb.toString();
    }
}