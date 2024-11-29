package jp.groupsession.v2.man.man050;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.dao.SltUserPerGroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnLoginHistoryDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.LoginHistorySearchModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ログイン履歴(CSV)の出力を行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LoginHistoryCsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(LoginHistoryCsvWriter.class);

    /** コネクション */
    private Connection con__ = null;

    /** ログイン履歴ファイル名(一覧) */
    public static final String FILE_NAME_LIST = "loginHistory.csv";
    /** ログイン履歴ファイル名(検索) */
    public static final String FILE_NAME_SEARCH = "loginHistorySearch.csv";

    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /** サーチモデル */
    private LoginHistorySearchModel mdl__ = null;

    /**
     * <p>コンストラクタ
     * @param reqMdl RequestModel
     * @param mdl LoginHistorySearchModel
     */
    public LoginHistoryCsvWriter(RequestModel reqMdl, LoginHistorySearchModel mdl) {
        reqMdl__ = reqMdl;
        mdl__ = mdl;
    }

    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param csvPath 出力先
     * @throws CSVException CSV出力時例外
     */
    public void outputCsv(Connection con, String csvPath)
    throws CSVException {

        setCon(con);

        //ディレクトリの作成
        File tmpDir = new File(csvPath);
        tmpDir.mkdirs();

        //セットファイル名とフルパス
        String fileName = FILE_NAME_LIST;

        if (String.valueOf(mdl__.getMode()).equals(GSConstMain.MODE_SEARCH)) {
            fileName = FILE_NAME_SEARCH;
        }

        String fileFullPath = IOTools.replaceFileSep(csvPath + File.separator + fileName);
        log__.debug("CSVファイルのパス = " + fileFullPath);

        //出力初期セット
        setCsvPath(fileFullPath);

        log__.debug("開始");
        write();
        log__.debug("終了");
    }

    /**
     * <br>[機  能] CSV生成 値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    public void create(PrintWriter pw) throws CSVException {

        //ヘッダ
        __writeHeader(pw);

        //明細
        __writeItem(pw);
    }

    /**
     * <p>ヘッダ部分を生成します。
     * @param pw PrintWriter
     */
    private void __writeHeader(PrintWriter pw) {
        String strHeader = "";

        GsMessage gsMsg = new GsMessage(reqMdl__);

        if (String.valueOf(mdl__.getMode()).equals(GSConstMain.MODE_LIST)) {
            strHeader = gsMsg.getMessage("cmn.employee.staff.number")
            + ","
            + gsMsg.getMessage("cmn.name")
            + ","
            + gsMsg.getMessage("cmn.post")
            + ","
            + gsMsg.getMessage("user.usr090.2");
        } else {
            strHeader = gsMsg.getMessage("cmn.employee.staff.number")
            + ","
            + gsMsg.getMessage("cmn.name")
            + ","
            + gsMsg.getMessage("cmn.post")
            + ","
            + gsMsg.getMessage("main.man050.5")
            + ","
            + gsMsg.getMessage("main.man050.6")
            + ","
            + gsMsg.getMessage("cmn.careers")
            + ","
            + gsMsg.getMessage("cmn.identification.number");
        }

        pw.println(strHeader);
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        LoginHistoryCsvRecordListenerImpl rl = new LoginHistoryCsvRecordListenerImpl(pw, mdl__);
        Connection con = getCon();
        try {

            if (String.valueOf(mdl__.getMode()).equals(GSConstMain.MODE_LIST)) {
                SltUserPerGroupDao supgDao = new SltUserPerGroupDao(con);
                supgDao.selectGroupListSortForCsv(
                      rl, mdl__.getGsid(), mdl__.getSortKey(), mdl__.getOrderKey(), reqMdl__);
            } else {
                CmnLoginHistoryDao loginHisDao = new CmnLoginHistoryDao(con);
                loginHisDao.selectSearchLoginListForCsv(rl, mdl__, reqMdl__);
            }

        } catch (SQLException e) {
            log__.error("SQLの実行に失敗", e);
            throw new CSVException("SQLの実行に失敗", e);
        }
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }
}