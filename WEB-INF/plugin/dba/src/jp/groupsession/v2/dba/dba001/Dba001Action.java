package jp.groupsession.v2.dba.dba001;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.Encoding;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.archive.ZipUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.http.TempFileUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.dba.AbstractDbaAction;
import jp.groupsession.v2.dba.GSConstDba;
import jp.groupsession.v2.dba.core.AbstractSqlGenerator;
import jp.groupsession.v2.dba.core.SqlGeneratorFactory;
import jp.groupsession.v2.dba.dba001.dao.Dba001Dao;
import jp.groupsession.v2.dba.gen.DaoBeanGenerator;
import jp.groupsession.v2.dba.gen.GenerateException;
import jp.groupsession.v2.dba.meta.JdbcMetaExplorer;
import jp.groupsession.v2.dba.meta.JdbcMetaUtil;
import jp.groupsession.v2.dba.meta.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <br>[機  能] DBA001
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Dba001Action extends AbstractDbaAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Dba001Action.class);

    /**
     * <br>[機  能] アクションを実行する
     * <br>[解  説]
     * <br>[備  考]
     * @param map ActionMapping
     * @param form ActionForm
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DB Connection
     * @return ActionForward
     * @throws Exception 実行時例外
     * @see jp.co.sjts.util.struts.AbstractAction
     * @see #executeAction(org.apache.struts.action.ActionMapping,
     *                      org.apache.struts.action.ActionForm,
     *                      javax.servlet.http.HttpServletRequest,
     *                      javax.servlet.http.HttpServletResponse,
     *                      java.sql.Connection)
     */
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {

        ActionForward forward = null;
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        cmd = cmd.trim();
        log__.debug("CMD==>" + cmd);

        //Form
        Dba001Form dbaform = (Dba001Form) form;
        dbaform.setResultMessage("");

        if (cmd.equals("001_ok")) {
            //SQL実行
            String sql = dbaform.getDba001SqlString();
            doSql(sql, con, dbaform);
            forward = map.getInputForward();
        } else if (cmd.equals("001_gen")) {
            // Generate Dao and Model
            log__.debug("Generate Dao and Model");
            doGenerateDaoModel(req, res, con, dbaform);
            forward = null;
        } else {
            //初期表示
            forward = map.getInputForward();
        }

        createDbInfo(dbaform, con);
        return forward;
    }

    /**
     * <br>[機  能] 入力されたSQLを実行する。
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SQL文
     * @param con DBコネクション
     * @param dbaform フォーム
     * @throws SQLException SQL実行例外
     */
    private void doSql(String sql, Connection con, Dba001Form dbaform) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        //DBよりテーブル情報を取得する
        JdbcMetaExplorer jmeta = new JdbcMetaExplorer(con);

        //初期化
        dbaform.setDba001ExeType(null);
        if (sql == null) {
            return;
        }

        //実行タイプ判定処理 小文字に修正してから比較
        sql = sql.trim();
        String tmp = sql.toLowerCase();

        log__.info(sql);
        pstmt = con.prepareStatement(sql);

        UDate startTime = new UDate();
        //
        if (tmp.startsWith("select") || tmp.startsWith("explain") || tmp.startsWith("call") || tmp.startsWith("show") || tmp.startsWith("analyze")) {
            //SELECT文実行
            try {
                rs = pstmt.executeQuery();
                //列名
                ArrayList<String> headerNameList = jmeta.getResultsetHeader(rs);
                dbaform.setDba001ResultHeaderName(headerNameList);
                //取得結果
                ArrayList<ArrayList> rows = new ArrayList<ArrayList>();
                while (rs.next()) {
                    //
                    ArrayList<String> row = new ArrayList<String>();
                    rows.add(row);
                    for (String hname : headerNameList) {
                        String data = rs.getString(hname);
                        if (data == null) {
                            data = "[NULL]";
                        }
                        row.add(data);
                    }
                }
                dbaform.setDba001ResultData(rows);
                //実行タイプセット
                dbaform.setDba001ExeType(GSConstDba.SQLEXE_TYPE_SELECT);
            } catch (SQLException e) {
                throw e;
            } finally {
                JDBCUtil.closeResultSet(rs);
            }
        } else {
            //UPDATE or INSERT 実行
            try {
                count = pstmt.executeUpdate();
                con.commit();
            } catch (SQLException e) {
                throw e;
            }
            //実行結果
            dbaform.setDba001ExeType(GSConstDba.SQLEXE_TYPE_UPDATE);
            String message = count + "件更新しました。";
            dbaform.setResultMessage(message);
        }
        //処理時間
        UDate endTime = new UDate();
        long msecond = UDateUtil.diffMillis(startTime, endTime);
        log__.info("処理時間(ミリ秒) " + msecond);
        dbaform.setSqlExeTime("処理時間(ミリ秒):" + msecond);

        log__.debug("SQL実行完了");
    }

    /**
     * <br>[機  能] 画面に表示するDB情報を作成する。
     * <br>[解  説]
     * <br>[備  考]
     * @param dbaform フォーム
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void createDbInfo(Dba001Form dbaform, Connection con) throws SQLException {

        //DBよりテーブル情報を取得する
        JdbcMetaExplorer jmeta = new JdbcMetaExplorer(con);

        String product = JdbcMetaUtil.getDbProductName(con);
        //
        List<Table> tables = jmeta.getTables("TABLE");
        ArrayList<TableInfoModel> tinfoes = new ArrayList<TableInfoModel>();
        //表示用テーブル情報の作成
        for (Table table : tables) {
            TableInfoModel tinfo = new TableInfoModel();
            tinfo.setTable(table);
            AbstractSqlGenerator sqlGenerator = SqlGeneratorFactory.getSqlGenerator(product, table);
            //select
            tinfo.setSqlSelectString(sqlGenerator.generateSelectSql());
            //update
            tinfo.setSqlUpdateString(sqlGenerator.generateUpdateSql());
            //insert
            tinfo.setSqlInsertString(sqlGenerator.generateInsertSql());
            //delete
            tinfo.setSqlDeleteString(sqlGenerator.generateDeleteSql());
            //
            tinfoes.add(tinfo);
        }

        List<Table> views = jmeta.getTables("VIEW");
        ArrayList<ViewInfoModel> vinfoes = new ArrayList<ViewInfoModel>();
        //表示用ビュー情報の作成
        for (Table table : views) {
            ViewInfoModel tinfo = new ViewInfoModel();
            tinfo.setTable(table);
            AbstractSqlGenerator sqlGenerator = SqlGeneratorFactory.getSqlGenerator(product, table);
            //select
            tinfo.setSqlSelectString(sqlGenerator.generateSelectSql());
            //
            vinfoes.add(tinfo);
        }

        //テーブル情報をセット
        dbaform.setDba001TableInfos(tinfoes);
        //VIEW
        dbaform.setDba001ViewInfos(vinfoes);
        //SEQUENCE
        Dba001Dao dao = new Dba001Dao(con);
        dbaform.setDba001SeqInfos(dao.getSequenses());
        //INDEXES
        dbaform.setDba001IndexInfos(dao.getIndexes());
    }

    /**
     * <br>[機  能] Dao,Modelを生成し、zip圧縮し、ダウンロードを行う。
     * <br>[解  説]
     * <br>[備  考]
     * @param req HttpServletRequest
     * @param res HttpServletResponse
     * @param con DBコネクション
     * @param dbaform フォーム
     * @throws SQLException SQL実行例外
     * @throws Exception 予測不可能な例外
     */
    private void doGenerateDaoModel(HttpServletRequest req,
            HttpServletResponse res, Connection con, Dba001Form dbaform)
            throws SQLException, Exception {

        String tableName = dbaform.getDba001GenTableName();
        log__.debug("TABLE NAME = " + tableName);
        if (StringUtil.isNullZeroString(tableName)) {
            //何もしない
            return;
        }
        //DBよりテーブル情報を取得する
        JdbcMetaExplorer jmeta = new JdbcMetaExplorer(con);
        Table table = jmeta.getTable(tableName);

        //テンポラリディレクトリ取得
        HttpSession session = req.getSession();
        String sessionId = session.getId();
        String tempDir = __getTempDir(req, sessionId, tableName);
        IOTools.isDirCheck(tempDir, true);

        //Dao and Model生成
        String product = JdbcMetaUtil.getDbProductName(con);
        DaoBeanGenerator gen = new DaoBeanGenerator(product);
        try {
            gen.generate(table, tempDir, Encoding.UTF_8);
        } catch (GenerateException e) {
            e.printStackTrace();
            log__.error(e);
        }

        //ファイル圧縮
        String fpath = __getZipFilePath(req, sessionId, tableName);
        ZipUtil.zipDir("Windows-31J", tempDir, fpath);

        //ダウンロード
        //ダウンロードするファイルの作成
        String tempZipFile = __getTempDir2(req, sessionId, tableName);
        log__.debug("tempZipFile = " + tempZipFile);
        TempFileUtil.downloadAtachment(req, res, tempZipFile, tableName + ".zip",
                                    Encoding.UTF_8);
    }
    /**
     * HelloWorld用の添付ディレクトリを取得する
     * [機  能]
     * [解  説]
     * [備  考]
     * @param req リクエスト
     * @param sessionId セッションID
     * @param tableName テーブル名称
     * @return String ディレクトリPATH
     */
    private String __getTempDir(HttpServletRequest req, String sessionId, String tableName) {

        //一時添付ディレクトリのRootパスを取得
        String tempRootDir = getTempPath(req);

        //任意のディレクトリを整形
        StringBuilder buf = new StringBuilder("");
        buf.append(tempRootDir);
        buf.append("/");
        buf.append(GSConstDba.PLUGIN_ID_DBA);
        buf.append("/");
        buf.append(sessionId);
        buf.append("/");
        buf.append(tableName);
        buf.append("/");
        return IOTools.replaceFileSep(buf.toString());
    }
    /**
     * HelloWorld用の添付ディレクトリを取得する
     * [機  能]
     * [解  説]
     * [備  考]
     * @param req リクエスト
     * @param sessionId セッションID
     * @param tableName テーブル名称
     * @return String ディレクトリPATH
     */
    private String __getTempDir2(HttpServletRequest req, String sessionId, String tableName) {

        //一時添付ディレクトリのRootパスを取得
        String tempRootDir = getTempPath(req);

        //任意のディレクトリを整形
        StringBuilder buf = new StringBuilder("");
        buf.append(tempRootDir);
        buf.append("/");
        buf.append(GSConstDba.PLUGIN_ID_DBA);
        buf.append("/");
        buf.append(sessionId);
        buf.append("/");
        buf.append(tableName);
        buf.append(".zip");
        return IOTools.replaceFileSep(buf.toString());
    }
    /**
     * HelloWorld用の添付ディレクトリを取得する
     * [機  能]
     * [解  説]
     * [備  考]
     * @param req リクエスト
     * @param sessionId セッションID
     * @param tableName テーブル名称
     * @return String ディレクトリPATH
     */
    private String __getZipFilePath(HttpServletRequest req, String sessionId, String tableName) {

        //一時添付ディレクトリのRootパスを取得
        String tempRootDir = getTempPath(req);

        //任意のディレクトリを整形
        StringBuilder buf = new StringBuilder("");
        buf.append(tempRootDir);
        buf.append("/");
        buf.append(GSConstDba.PLUGIN_ID_DBA);
        buf.append("/");
        buf.append(sessionId);
        buf.append("/");
        buf.append(tableName);
        buf.append(".zip");
        return IOTools.replaceFileSep(buf.toString());
    }
}
