package jp.groupsession.v2.man.man250;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.man250.dao.Man250Dao;
import jp.groupsession.v2.man.man250.model.Man250SearchModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] オペレーションログ情報一覧(CSV)を出力する
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class LogCsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(LogCsvWriter.class);

    /** コネクション */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /** オペレーションログ情報一覧ファイル名 */
    public static final String FILE_NAME = "operationLogList.csv";

    /** 検索条件 */
    private Man250SearchModel searchModel__ = null;

    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param csvPath 出力先
     * @param reqMdl リクエスト情報
     * @throws CSVException CSV出力時例外
     */
    public void outputCsv(Connection con, String csvPath, RequestModel reqMdl)
    throws CSVException {

        setCon(con);
        setReqMdl(reqMdl);

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
     * <p>ヘッダ部分を生成します。
     * @param pw PrintWriter
     */
    private void __writeHeader(PrintWriter pw) {

        StringBuilder sb = new StringBuilder();
        GsMessage gsMsg = new GsMessage(getReqMdl().getLocale());

        //ログレベル
        sb.append(gsMsg.getMessage("cmn.loglevel"));
        sb.append(",");
        //実行日時
        sb.append(gsMsg.getMessage("man.run.time"));
        sb.append(",");
        //プラグイン
        sb.append(gsMsg.getMessage("cmn.plugin"));
        sb.append(",");
        //実行ユーザ
        sb.append(gsMsg.getMessage("man.run.user"));
        sb.append(",");
        //画面ID
        sb.append(gsMsg.getMessage("main.src.man250.5"));
        sb.append(",");
        //画面・機能名
        sb.append(gsMsg.getMessage("main.scr.feature.name"));
        sb.append(",");
        //操作
        sb.append(gsMsg.getMessage("cmn.operations"));
        sb.append(",");
        //内容
        sb.append(gsMsg.getMessage("cmn.content"));
        sb.append(",");
        //IPアドレス
        sb.append(gsMsg.getMessage("cmn.ipaddress"));
        sb.append(",");
        //バージョン
        sb.append(gsMsg.getMessage("cmn.version"));

        pw.println(sb.toString());
    }

    /**
     * <p>明細部分を生成します。
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    private void __writeItem(PrintWriter pw) throws CSVException {

        LogCsvRecordListenerImpl rl = new LogCsvRecordListenerImpl(pw);
        Connection con = getCon();

        try {
            Man250Dao dao = new Man250Dao(con);
            dao.getLogListForCsv(searchModel__, rl, getReqMdl());

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

    /**
     * <p>searchModel を取得します。
     * @return searchModel
     */
    public Man250SearchModel getSearchModel() {
        return searchModel__;
    }

    /**
     * <p>searchModel をセットします。
     * @param searchModel searchModel
     */
    public void setSearchModel(Man250SearchModel searchModel) {
        searchModel__ = searchModel;
    }

    /**
     * <p>reqMdl を取得します。
     * @return reqMdl
     */
    public RequestModel getReqMdl() {
        return reqMdl__;
    }

    /**
     * <p>reqMdl をセットします。
     * @param reqMdl reqMdl
     */
    public void setReqMdl(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

}