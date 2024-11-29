package jp.groupsession.v2.convert.convert492.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.9.2へコンバートする際に使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvTableDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvTableDao.class);

    /**
     * <p>Default Constructor
     */
    public ConvTableDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvTableDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] DBのコンバートを実行
     * <br>[解  説] 項目追加など、DB設計に変更を加えた部分のコンバートを行う
     * <br>[備  考]
     * @param saiban 採番用コントローラー
     * @throws SQLException 例外
     */
    public void convert(
            MlCountMtController saiban) throws SQLException {

        log__.debug("-- DBコンバート開始 --");
        //create Table or alter table
        createTable(saiban);

        // 稟議の入力フォームにおいて、重複した添付ファイルフォームを削除
        __delDuplicateTempForm(getCon());

        // WEBメールフィルタにおいて、動作_ゴミ箱に移動するを削除し元の設定値を動作_フォルダに移動するへ追加する
        __delDustInsFolder(getCon());


        log__.debug("-- DBコンバート終了 --");
    }

    /**
     * <br>[機  能] 新規テーブルのcreate、insertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントローラー
     * @throws SQLException SQL実行例外
     */
    public void createTable(
            MlCountMtController saiban) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL生成
            List<SqlBuffer> sqlList = __createSQL(saiban);

            for (SqlBuffer sql : sqlList) {
                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] SQLを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントローラー
     * @return List in SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private List<SqlBuffer> __createSQL(
            MlCountMtController saiban) throws SQLException {
        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = null;
        // チャットチューニング
        IDbUtil dbUtil = DBUtilFactory.getInstance();
        if (dbUtil.getDbType() == GSConst.DBTYPE_H2DB) {
            sql = new SqlBuffer();
            sql.addSql(" create index CHT_USER_DATA_INDEX on CHT_USER_DATA(CUP_SID); ");
            sqlList.add(sql);
            sql = new SqlBuffer();
            sql.addSql(" create index CHT_GROUP_DATA_INDEX on CHT_GROUP_DATA(CGI_SID); ");
            sqlList.add(sql);
        }
        // 掲示板草稿情報テーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table BBS_SOUKOU");
        sql.addSql("   (");
        sql.addSql("    BSK_SID         integer not null,");
        sql.addSql("    BFI_SID        integer not null,");
        sql.addSql("    BTI_SID        integer not null,");
        sql.addSql("    BSK_SOUKOU_TYPE        integer not null,");
        sql.addSql("    BSK_TITLE           varchar(150),");
        sql.addSql("    BSK_VALUE           text,");
        sql.addSql("    BSK_TYPE        integer    not null,");
        sql.addSql("    BSK_VALUE_PLAIN     text,");
        sql.addSql("    BSK_IMPORTANCE      integer,");
        sql.addSql("    BSK_LIMIT_FR_DATE   timestamp,");
        sql.addSql("    BSK_LIMIT           integer not null,");
        sql.addSql("    BSK_LIMIT_DATE      timestamp,");
        sql.addSql("    BSK_AGID            integer,");
        sql.addSql("    BSK_AUID            integer not null,");
        sql.addSql("    BSK_ADATE           timestamp not null,");
        sql.addSql("    BSK_EUID            integer not null,");
        sql.addSql("    BSK_EDATE           timestamp not null,");
        sql.addSql("    primary key (BSK_SID)");
        sql.addSql("   );");
        sqlList.add(sql);
        // 掲示板草稿添付情報テーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table BBS_SOUKOU_BIN");
        sql.addSql("   (");
        sql.addSql("    BSK_SID         integer not null,");
        sql.addSql("    BIN_SID        integer not null,");
        sql.addSql("    primary key (BSK_SID, BIN_SID)");
        sql.addSql("   );");
        sqlList.add(sql);
        // 掲示板草稿本文添付情報テーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table BBS_SOUKOU_BODY_BIN");
        sql.addSql("   (");
        sql.addSql("    BSK_SID         integer not null,");
        sql.addSql("    BSB_FILE_SID        integer not null,");
        sql.addSql("    BIN_SID        integer not null,");
        sql.addSql("    primary key (BSK_SID, BSB_FILE_SID)");
        sql.addSql("   );");
        sqlList.add(sql);
        // 回覧板ラベル情報テーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_LABEL");
        sql.addSql("   (");
        sql.addSql("    CLA_SID         integer not null,");
        sql.addSql("    CAC_SID        integer not null,");
        sql.addSql("    CLA_NAME        varchar(100) not null,");
        sql.addSql("    CLA_ORDER        integer not null,");
        sql.addSql("    CLA_AUID            integer not null,");
        sql.addSql("    CLA_ADATE           timestamp not null,");
        sql.addSql("    CLA_EUID            integer not null,");
        sql.addSql("    CLA_EDATE           timestamp not null,");
        sql.addSql("    primary key (CLA_SID)");
        sql.addSql("   );");
        sqlList.add(sql);
        // 回覧板受信ラベルテーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_VIEW_LABEL");
        sql.addSql("   (");
        sql.addSql("    CIF_SID         integer not null,");
        sql.addSql("    CAC_SID        integer not null,");
        sql.addSql("    CLA_SID        integer not null,");
        sql.addSql("    primary key(CIF_SID,CAC_SID,CLA_SID)");
        sql.addSql("   );");
        sqlList.add(sql);
        // 回覧板送信ラベルテーブル追加
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_INF_LABEL");
        sql.addSql("   (");
        sql.addSql("    CIF_SID         integer not null,");
        sql.addSql("    CAC_SID        integer not null,");
        sql.addSql("    CLA_SID        integer not null,");
        sql.addSql("    primary key(CIF_SID,CAC_SID,CLA_SID)");
        sql.addSql("   );");
        sqlList.add(sql);

        // スケジュール情報テーブルコメント追加
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_DATA add SCD_ATTEND_COMMENT varchar(50);");
        sqlList.add(sql);


        //回覧板管理者設定 ショートメール通知フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ACONF add CAF_SMAIL_SEND_MEMO integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ACONF add CAF_SMAIL_SEND_EDIT integer not null default 0;");
        sqlList.add(sql);
        //回覧板管理者設定 追加したショートメール通知フィールドに既存設定を複写
        sql = new SqlBuffer();
        sql.addSql(" update CIR_ACONF set CAF_SMAIL_SEND_MEMO=CAF_SMAIL_SEND");
        sql.addSql(" , CAF_SMAIL_SEND_EDIT=CAF_SMAIL_SEND;");
        sqlList.add(sql);
        //回覧板アカウント情報 ショートメール通知フィールド追加
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ACCOUNT add CAC_SML_MEMO integer not null default 0;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ACCOUNT add CAC_SML_EDIT integer not null default 0;");
        sqlList.add(sql);
        //回覧板アカウント情報 追加したショートメール通知フィールドに既存設定を複写
        sql = new SqlBuffer();
        sql.addSql(" update CIR_ACCOUNT set CAC_SML_MEMO=CAC_SML_NTF, CAC_SML_EDIT=CAC_SML_NTF;");
        sqlList.add(sql);
        //回覧版情報 最大文字数変更
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_INF alter column CIF_VALUE type text;");
        sqlList.add(sql);

        // CMN グループID最大文字数変更
        sql = new SqlBuffer();
        sql.addSql("alter table CMN_GROUPM alter column GRP_ID type VARCHAR(50);");
        sqlList.add(sql);

        // WEBメールフィルター 動作_フォルダに移動するフィールドを追加
        sql = new SqlBuffer();
        sql.addSql("alter table WML_FILTER add WFT_ACTION_FOLDER integer not null default 0");
        sqlList.add(sql);

        return sqlList;

    }

    /**
     * <p>重複して存在する添付ファイルフォームを取得
     * @param con コネクション
     * @return 重複して存在する添付ファイルフォーム
     * @throws SQLException SQL実行例外
     * */
    private List<RngFormdataModel> __getDuplicateTempForm(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<RngFormdataModel> ret = new ArrayList<RngFormdataModel>();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RFD_SID,");
            sql.addSql("   RFD_ROWNO,");
            sql.addSql("   RFD_ID,");
            sql.addSql("   RFD_VALUE,");
            sql.addSql("   RFD_AUID,");
            sql.addSql("   RFD_ADATE,");
            sql.addSql("   RFD_EUID,");
            sql.addSql("   RFD_EDATE");
            sql.addSql(" from");
            sql.addSql("   RNG_FORMDATA");
            sql.addSql(" group by");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RFD_SID,");
            sql.addSql("   RFD_ROWNO,");
            sql.addSql("   RFD_ID,");
            sql.addSql("   RFD_VALUE,");
            sql.addSql("   RFD_AUID,");
            sql.addSql("   RFD_ADATE,");
            sql.addSql("   RFD_EUID,");
            sql.addSql("   RFD_EDATE");
            sql.addSql(" having");
            sql.addSql("   count(RNG_SID) > 1");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RngFormdataModel model = new RngFormdataModel();
                model.setRngSid(rs.getInt("RNG_SID"));
                model.setRfdSid(rs.getInt("RFD_SID"));
                model.setRfdRowno(rs.getInt("RFD_ROWNO"));
                model.setRfdId(rs.getString("RFD_ID"));
                model.setRfdValue(rs.getString("RFD_VALUE"));
                model.setRfdAuid(rs.getInt("RFD_AUID"));
                model.setRfdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RFD_ADATE")));
                model.setRfdEuid(rs.getInt("RFD_EUID"));
                model.setRfdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RFD_EDATE")));
                ret.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }


    /**
     * <p> 重複した添付ファイルフォームを削除
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * */
    private void __delDuplicateTempForm(Connection con) throws SQLException {

        // 削除対象の入力フォームを取得
        List<RngFormdataModel> delFormList = __getDuplicateTempForm(con);

        if (delFormList.isEmpty() || delFormList.size() == 0) {
            return;
        }

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();

        try {
            // SQL文
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_FORMDATA");
            sql.addSql(" where");
            sql.addSql("   RNG_SID=?");
            sql.addSql(" and");
            sql.addSql("   RFD_SID=?");
            sql.addSql(" and");
            sql.addSql("   RFD_VALUE=?");
            sql.addSql(" and");
            sql.addSql("   RFD_ID like 'file_%'");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (int i = 0; i < delFormList.size(); i++) {
                sql.addIntValue(delFormList.get(i).getRngSid());
                sql.addIntValue(delFormList.get(i).getRfdSid());
                sql.addStrValue(delFormList.get(i).getRfdValue());
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                sql.clearValue();
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            // 削除した入力フォームを登録
            __insertDuplicatedTempForm(con, delFormList);
        }

    }

    /**
     * <p> 重複していた添付ファイルフォームを登録
     * @param con コネクション
     * @param deletedFormList 削除した添付ファイルフォーム
     * @return 登録件数
     * @throws SQLException SQL実行例外
     * */
    private int __insertDuplicatedTempForm(Connection con, List<RngFormdataModel> deletedFormList)
            throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        SqlBuffer sql = new SqlBuffer();

        try {
            sql.addSql(" insert into ");
            sql.addSql("   RNG_FORMDATA (");
            sql.addSql("     RNG_SID,");
            sql.addSql("     RFD_SID,");
            sql.addSql("     RFD_ROWNO,");
            sql.addSql("     RFD_ID,");
            sql.addSql("     RFD_VALUE,");
            sql.addSql("     RFD_AUID,");
            sql.addSql("     RFD_ADATE,");
            sql.addSql("     RFD_EUID,");
            sql.addSql("     RFD_EDATE");
            sql.addSql("   )");
            sql.addSql("   values (");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?,");
            sql.addSql("     ?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (RngFormdataModel form : deletedFormList) {
                sql.addIntValue(form.getRngSid());
                sql.addIntValue(form.getRfdSid());
                sql.addIntValue(form.getRfdRowno());
                sql.addStrValue(form.getRfdId());
                sql.addStrValue(form.getRfdValue());
                sql.addIntValue(form.getRfdAuid());
                sql.addDateValue(form.getRfdAdate());
                sql.addIntValue(form.getRfdEuid());
                sql.addDateValue(form.getRfdEdate());
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                sql.clearValue();
                count += pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p> WEBメールフィルターゴミ箱を削除フォルダを追加
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * */
    private void __delDustInsFolder(Connection con) throws SQLException {

        // ゴミ箱の設定値を取得する
        List<WmlFilterModel> delList = __getWftActionDust(con);

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();

        try {
            // SQL文
            sql.addSql(" alter table");
            sql.addSql("   WML_FILTER");
            sql.addSql(" drop");
            sql.addSql("   WFT_ACTION_DUST");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (delList.isEmpty() || delList.size() == 0) {
                return;
            }
            // 削除したフィールドを別フィールドに更新
            __updateWftActionFolder(con, delList);
        }
    }

    /**
     * <p>WFT_ACTION_DUSTのデータを取得
     * @param con コネクション
     * @return WFT_ACTION_DUST Model Class
     * @throws SQLException SQL実行例外
     * */
    private List<WmlFilterModel> __getWftActionDust(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<WmlFilterModel> ret = new ArrayList<WmlFilterModel>();

        try {
            // SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   WFT_SID,");
            sql.addSql("   WFT_ACTION_DUST");
            sql.addSql(" from");
            sql.addSql("   WML_FILTER");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                WmlFilterModel model = new WmlFilterModel();
                model.setWftSid(rs.getInt("WFT_SID"));
                model.setWftActionDust(rs.getInt("WFT_ACTION_DUST"));
                ret.add(model);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <p> WFT_ACTION_FOLDERにデータを更新
     * @param con コネクション
     * @param deletedList 削除したWFT_ACTION_DUST情報
     * @return 登録件数
     * @throws SQLException SQL実行例外
     * */
    private int __updateWftActionFolder(Connection con, List<WmlFilterModel> deletedList)
            throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        SqlBuffer sql = new SqlBuffer();

        try {
            sql.addSql(" update ");
            sql.addSql("   WML_FILTER");
            sql.addSql(" set");
            sql.addSql("   WFT_ACTION_FOLDER=?");
            sql.addSql(" where");
            sql.addSql("   WFT_SID = ?");

            pstmt = con.prepareStatement(sql.toSqlString());

            for (WmlFilterModel form : deletedList) {
                sql.addIntValue(form.getWftActionDust());
                sql.addIntValue(form.getWftSid());
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                sql.clearValue();
                count += pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }
}
