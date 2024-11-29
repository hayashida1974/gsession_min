package jp.groupsession.v2.usr.usr010;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] グループ情報一覧(CSV)を出力する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class GrpCsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(GrpCsvWriter.class);

    /** コネクション */
    private Connection con__ = null;

    /** グループ情報一覧ファイル名 */
    public static final String FILE_NAME = "groupList.csv";

    /** リクエスト情報 */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public GrpCsvWriter(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
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
        String fileName = FILE_NAME;
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
     * <br>[機  能] ヘッダ部分を生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     */
    private void __writeHeader(PrintWriter pw) {
        GsMessage gsMsg = new GsMessage(reqMdl__);
        StringBuilder sb = new StringBuilder();
        sb.append(gsMsg.getMessage("cmn.group.id"));
        sb.append(",");
        sb.append(gsMsg.getMessage("cmn.group.name"));
        sb.append(",");
        sb.append(gsMsg.getMessage("user.14"));
        sb.append(",");
        sb.append(gsMsg.getMessage("cmn.parant.group.id"));
        sb.append(",");
        sb.append(gsMsg.getMessage("cmn.comment"));
        String strHeader = new String(sb);
        pw.println(strHeader);
    }

    /**
     * <br>[機  能] 明細部分を生成します。
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        GrpCsvRecordListenerImpl rl = new GrpCsvRecordListenerImpl(pw);
        Connection con = getCon();

        try {

            GroupDao grpDao = new GroupDao(con);
            grpDao.createAllGroupInfoForCsv(rl);

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