package jp.groupsession.v2.convert.convert453.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmCountDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.5.3へコンバートする際に使用する
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
     * @throws SQLException 例外
     */
    public void convert() throws SQLException {

        log__.debug("-- DBコンバート開始 --");

        //create Table or alter table
        createTable();

        log__.debug("-- DBコンバート終了 --");
    }

    /**
     * <br>[機  能] 新規テーブルのcreate、insertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {

            //SQL生成
            List<SqlBuffer> sqlList = __createSQL();

            for (SqlBuffer sql : sqlList) {
                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

            boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);

            //メイン ユーザ数 ログインユーザ数、ログイン回数を作成
            if (h2db) {
                //H2の場合
                __updateH2CmnUsrCnt(con);
            } else {
                //Postgresqlの場合
                __updatePgCmnUsrCnt(con);
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
     * @return List in SqlBuffer
     * @throws SQLException SQL実行時例外
     */
    private List<SqlBuffer> __createSQL() throws SQLException {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        SqlBuffer sql = null;

        //役職マスタ 役職コード
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_POSITION add POS_CODE varchar(15) not null default '0';");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql("    update CMN_POSITION");
        sql.addSql("    set POS_CODE =");
        sql.addSql("      (");
        sql.addSql("       select POS.POS_SID");
        sql.addSql("       from");
        sql.addSql("        (select POS_SID from CMN_POSITION) POS");
        sql.addSql("       where");
        sql.addSql("         CMN_POSITION.POS_SID = POS.POS_SID");
        sql.addSql("      );");
        sqlList.add(sql);

        //メイン ユーザ数 ログインユーザ数
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_COUNT add CUC_USER bigint not null default 0;");
        sqlList.add(sql);

        //メイン ユーザ数 ログイン回数
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM_COUNT add CUC_LOGIN bigint not null default 0;");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能] メイン ユーザ数のログインユーザ数、ログイン回数を作成する
     * <br>[解  説]
     * <br>[備  考] H2 Database用
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __updateH2CmnUsrCnt(Connection con)
            throws SQLException {

        CmnUsrmCountDao usrmCntDao = new CmnUsrmCountDao(con);
        List<UDate> dateList = usrmCntDao.getDate();
        if (dateList == null || dateList.size() < 1) {
            return;
        }

        //プリペアドステートメント作成
        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();
        sql.addSql("    update CMN_USRM_COUNT");
        sql.addSql("    set");
        sql.addSql("      (");
        sql.addSql("       CMN_USRM_COUNT.CUC_USER,");
        sql.addSql("       CMN_USRM_COUNT.CUC_LOGIN");
        sql.addSql("      )");
        sql.addSql("      =");
        sql.addSql("      (");
        sql.addSql("       select");
        sql.addSql("         count(distinct USR_SID),");
        sql.addSql("         count(USR_SID)");
        sql.addSql("       from");
        sql.addSql("         CMN_LOGIN_HISTORY");
        sql.addSql("       where");
        sql.addSql("         CLH_ADATE >= ?");
        sql.addSql("       and");
        sql.addSql("         CLH_ADATE <= ?");
        sql.addSql("       and");
        sql.addSql("         USR_SID not in");
        sql.addSql("           (");
        sql.addSql("            select");
        sql.addSql("              CMN_USRM.USR_SID");
        sql.addSql("            from");
        sql.addSql("              CMN_USRM");
        sql.addSql("            where");
        sql.addSql("              CMN_USRM.USR_JKBN = ?");
        sql.addSql("            and");
        sql.addSql("              CMN_USRM.USR_EDATE <= ?");
        sql.addSql("           )");
        sql.addSql("      )");
        sql.addSql("    where");
        sql.addSql("      CMN_USRM_COUNT.CUC_DATE = ?");
        pstmt = con.prepareStatement(sql.toSqlString());

        //SQLの値を設定し実行する
        for (UDate udate : dateList) {
            UDate zeroDate = udate.cloneUDate();
            UDate maxDate = udate.cloneUDate();
            zeroDate.setZeroHhMmSs();
            maxDate.setMaxHhMmSs();

            sql.addDateValue(zeroDate);
            sql.addDateValue(maxDate);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addDateValue(maxDate);
            sql.addStrValue(udate.getDateStringForSql());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

            sql.clearValue();
        }
    }

    /**
     * <br>[機  能] メイン ユーザ数のログインユーザ数、ログイン回数を作成する
     * <br>[解  説]
     * <br>[備  考] Postgres用
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __updatePgCmnUsrCnt(Connection con)
            throws SQLException {

        CmnUsrmCountDao usrmCntDao = new CmnUsrmCountDao(con);
        List<UDate> dateList = usrmCntDao.getDate();
        if (dateList == null || dateList.size() < 1) {
            return;
        }

        //プリペアドステートメント作成
        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();
        sql.addSql("  update CMN_USRM_COUNT ");
        sql.addSql("  set ");
        sql.addSql("    CUC_USER ");
        sql.addSql("    = ");
        sql.addSql("    (select ");
        sql.addSql("       count(distinct USR_SID) ");
        sql.addSql("     from ");
        sql.addSql("       CMN_LOGIN_HISTORY ");
        sql.addSql("     where ");
        sql.addSql("       CLH_ADATE >= ? ");
        sql.addSql("     and ");
        sql.addSql("       CLH_ADATE <= ? ");
        sql.addSql("     and ");
        sql.addSql("       USR_SID not in ");
        sql.addSql("         ( ");
        sql.addSql("          select ");
        sql.addSql("            CMN_USRM.USR_SID ");
        sql.addSql("          from ");
        sql.addSql("            CMN_USRM ");
        sql.addSql("          where ");
        sql.addSql("            CMN_USRM.USR_JKBN = ? ");
        sql.addSql("          and ");
        sql.addSql("            CMN_USRM.USR_EDATE <= ? ");
        sql.addSql("         ) ");
        sql.addSql("    ), ");
        sql.addSql("    CUC_LOGIN ");
        sql.addSql("    = ");
        sql.addSql("    (select ");
        sql.addSql("       count(USR_SID) ");
        sql.addSql("     from ");
        sql.addSql("       CMN_LOGIN_HISTORY ");
        sql.addSql("     where ");
        sql.addSql("       CLH_ADATE >= ? ");
        sql.addSql("     and ");
        sql.addSql("       CLH_ADATE <= ? ");
        sql.addSql("     and ");
        sql.addSql("       USR_SID not in ");
        sql.addSql("         ( ");
        sql.addSql("          select ");
        sql.addSql("            CMN_USRM.USR_SID ");
        sql.addSql("          from ");
        sql.addSql("            CMN_USRM ");
        sql.addSql("          where ");
        sql.addSql("            CMN_USRM.USR_JKBN = ? ");
        sql.addSql("          and ");
        sql.addSql("            CMN_USRM.USR_EDATE <= ? ");
        sql.addSql("         ) ");
        sql.addSql("    ) ");
        sql.addSql("  where ");
        sql.addSql("    CUC_DATE = ? :: date ");
        pstmt = con.prepareStatement(sql.toSqlString());

        //SQLの値を設定し実行する
        for (UDate udate : dateList) {
            UDate zeroDate = udate.cloneUDate();
            UDate maxDate = udate.cloneUDate();
            zeroDate.setZeroHhMmSs();
            maxDate.setMaxHhMmSs();

            sql.addDateValue(zeroDate);
            sql.addDateValue(maxDate);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addDateValue(maxDate);
            sql.addDateValue(zeroDate);
            sql.addDateValue(maxDate);
            sql.addIntValue(GSConst.JTKBN_DELETE);
            sql.addDateValue(maxDate);
            sql.addStrValue(udate.getDateStringForSql());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

            sql.clearValue();
        }
    }
}