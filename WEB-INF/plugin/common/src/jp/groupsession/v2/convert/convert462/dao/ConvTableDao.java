package jp.groupsession.v2.convert.convert462.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayTemplateDao;
import jp.groupsession.v2.cmn.model.base.CmnHolidayTemplateModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.6.2へコンバートする際に使用する
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
//        boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);

        //ユーザ情報 有効フラグ追加
        sql = new SqlBuffer();
        sql.addSql("alter table CMN_USRM add USR_UKO_FLG integer not null default 0;");
        sqlList.add(sql);

        //ファイル管理 論理削除、物理削除されたデータに対しての更新通知削除
        sql = new SqlBuffer();
        sql.addSql(" delete from FILE_CALL_DATA where");
        sql.addSql(" FDR_SID not in (");
        sql.addSql(" select FDR_SID from FILE_DIRECTORY where");
        sql.addSql(" FDR_JTKBN = 0);");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table CMN_IMP_TIME");
        sql.addSql(" ( CIT_USR_FLG integer not null default 0,");
        sql.addSql(" CIT_USR_TIME_FLG  integer not null default 0,");
        sql.addSql(" CIT_USR_TIME timestamp not null,");
        sql.addSql(" CIT_GRP_FLG integer not null default 0,");
        sql.addSql(" CIT_GRP_TIME_FLG  integer not null default 0,");
        sql.addSql(" CIT_GRP_TIME timestamp not null,");
        sql.addSql(" CIT_BEG_FLG integer not null default 0,");
        sql.addSql(" CIT_BEG_TIME_FLG  integer not null default 0,");
        sql.addSql(" CIT_BEG_TIME timestamp not null);");
        sqlList.add(sql);

        //特例ユーザ設定 追加
        sql = new SqlBuffer();
        sql.addSql("create table LDP_SPUSER");
        sql.addSql("(");
        sql.addSql("  USR_SID         integer         not null,");
        sql.addSql("  GRP_SID         integer         not null,");
        sql.addSql("  primary key (USR_SID, GRP_SID)");
        sql.addSql(");");
        sqlList.add(sql);

        //山の日が既に存在していないかの確認を行う
        CmnHolidayTemplateDao hTempDao = new CmnHolidayTemplateDao(getCon());
        CmnHolidayTemplateModel hTempMdl = new CmnHolidayTemplateModel();
        hTempMdl.setHltDateMonth(8);
        hTempMdl.setHltDateDay(11);

        if (!hTempDao.isExistSelectDate(hTempMdl)) {
            //休日テンプレートに山の日を追加
            int sid = (int) saiban.getSaibanNumber(SaibanModel.SBNSID_MAIN,
                    SaibanModel.SBNSID_SUB_HLT, GSConst.SYSTEM_USER_ADMIN);
            log__.debug("ネスト内採番後gsid=" + sid);

            sql = new SqlBuffer();
            sql.addSql("insert into  CMN_HOLIDAY_TEMPLATE (");
            sql.addSql(" HLT_SID,");
            sql.addSql(" HLT_DATE_MONTH,");
            sql.addSql(" HLT_DATE_DAY, ");
            sql.addSql(" HLT_NAME, ");
            sql.addSql(" HLT_EX_MONTH, ");
            sql.addSql(" HLT_EX_WEEK_MONTH, ");
            sql.addSql(" HLT_EX_DAY_WEEK,");
            sql.addSql(" HLT_EX_FURIKAE,");
            sql.addSql(" HLT_EXFLG,");
            sql.addSql(" HLT_ADUSER,");
            sql.addSql(" HLT_ADDATE,");
            sql.addSql(" HLT_UPUSER,");
            sql.addSql(" HLT_UPDATE ) ");
            sql.addSql("values (");
            sql.addSql(sid + ",");
            sql.addSql(" 8,");
            sql.addSql(" 11,");
            sql.addSql(" '山の日',");
            sql.addSql(" 0,");
            sql.addSql(" 0,");
            sql.addSql(" 0,");
            sql.addSql(" 1,");
            sql.addSql(" 0,");
            sql.addSql(" 0,");
            sql.addSql(" current_timestamp,");
            sql.addSql(" 0,");
            sql.addSql(" current_timestamp );");
            sqlList.add(sql);
        }
        return sqlList;
    }
}