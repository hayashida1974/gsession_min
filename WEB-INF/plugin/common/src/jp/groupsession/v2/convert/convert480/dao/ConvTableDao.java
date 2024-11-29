package jp.groupsession.v2.convert.convert480.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.7.1へコンバートする際に使用する
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
        //稟議テーブルのコンバート
        ConvRingiDao rngConvDao = new ConvRingiDao();
        rngConvDao.convert(getCon(), saiban);

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

        //アドレス情報 メールアドレス1 字数変更(50→256)
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ADDRESS alter column ADR_MAIL1 type varchar(256);");
        sqlList.add(sql);

        //アドレス情報 メールアドレス2 字数変更(50→256)
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ADDRESS alter column ADR_MAIL2 type varchar(256);");
        sqlList.add(sql);

        //アドレス情報 メールアドレス3 字数変更(50→256)
        sql = new SqlBuffer();
        sql.addSql(" alter table ADR_ADDRESS alter column ADR_MAIL3 type varchar(256);");
        sqlList.add(sql);

        //施設予約 繰り返し登録担当者名部分の長さを変更
        sql = new SqlBuffer();
        sql.addSql("alter table RSV_SIS_KRYRK alter RKR_NAME type varchar(70);");
        sqlList.add(sql);

        //アドレス帳 メールアドレスに含まれるタブ文字を除去
        sql = new SqlBuffer();
        sql.addSql(" update ");
        sql.addSql("  ADR_ADDRESS");
        sql.addSql(" set");
        sql.addSql("  ADR_MAIL1=replace(ADR_MAIL1,chr(9),'')");
        sql.addSql(" where");
        sql.addSql("  ADR_MAIL1 like '%' || chr(9); ");
        sql.addSql(" update ");
        sql.addSql("  ADR_ADDRESS");
        sql.addSql(" set");
        sql.addSql("  ADR_MAIL2=replace(ADR_MAIL2,chr(9),'')");
        sql.addSql(" where");
        sql.addSql("  ADR_MAIL2 like '%' || chr(9); ");
        sql.addSql(" update ");
        sql.addSql("  ADR_ADDRESS");
        sql.addSql(" set");
        sql.addSql("  ADR_MAIL3=replace(ADR_MAIL3,chr(9),'')");
        sql.addSql(" where");
        sql.addSql("  ADR_MAIL3 like '%' || chr(9); ");
        sqlList.add(sql);

        // ユーザ情報 メールアドレスに含まれるタブ文字を除去
        sql = new SqlBuffer();
        sql.addSql(" update ");
        sql.addSql("  CMN_USRM_INF");
        sql.addSql(" set");
        sql.addSql("  USI_MAIL1=replace(USI_MAIL1,chr(9),'')");
        sql.addSql(" where");
        sql.addSql("  USI_MAIL1 like '%' || chr(9); ");
        sql.addSql(" update ");
        sql.addSql("  CMN_USRM_INF");
        sql.addSql(" set");
        sql.addSql("  USI_MAIL2=replace(USI_MAIL2,chr(9),'')");
        sql.addSql(" where");
        sql.addSql("  USI_MAIL2 like '%' || chr(9); ");
        sql.addSql(" update ");
        sql.addSql("  CMN_USRM_INF");
        sql.addSql(" set");
        sql.addSql("  USI_MAIL3=replace(USI_MAIL3,chr(9),'')");
        sql.addSql(" where");
        sql.addSql("  USI_MAIL3 like '%' || chr(9); ");
        sqlList.add(sql);
        //アンケート v472不具合 アクセス時に管理者設定が新規追加される不具合で生成された管理者設定を統一
        //------------------------
        sql = new SqlBuffer();
        sql.addSql(" alter table ENQ_ADM_CONF rename to ENQ_ADM_CONF_BK;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" create table ENQ_ADM_CONF");
        sql.addSql(" ( ");
        sql.addSql("         EAC_KBN_TAISYO      integer                 , ");
        sql.addSql("         EAC_MAIN_DSP_USE    integer                 , ");
        sql.addSql("         EAC_MAIN_DSP        integer                 , ");
        sql.addSql("         EAC_LIST_CNT_USE    integer                 , ");
        sql.addSql("         EAC_LIST_CNT        integer                 , ");
        sql.addSql("         EAC_AUID            integer         not null, ");
        sql.addSql("         EAC_ADATE           timestamp       not null, ");
        sql.addSql("         EAC_EUID            integer         not null, ");
        sql.addSql("         EAC_EDATE           timestamp       not null, ");
        sql.addSql("         EAC_FOLDER_USE      integer                 , ");
        sql.addSql("         EAC_FOLDER_SELECT   integer                 , ");
        sql.addSql("         EAC_CAN_ANSWER      integer ");
        sql.addSql(" ); ");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" insert into ENQ_ADM_CONF ");
        sql.addSql(" ( ");
        sql.addSql("         EAC_KBN_TAISYO  , ");
        sql.addSql("         EAC_MAIN_DSP_USE, ");
        sql.addSql("         EAC_MAIN_DSP    , ");
        sql.addSql("         EAC_LIST_CNT_USE, ");
        sql.addSql("         EAC_LIST_CNT    , ");
        sql.addSql("         EAC_AUID        , ");
        sql.addSql("         EAC_ADATE       , ");
        sql.addSql("         EAC_EUID        , ");
        sql.addSql("         EAC_EDATE       , ");
        sql.addSql("         EAC_FOLDER_USE  , ");
        sql.addSql("         EAC_FOLDER_SELECT, ");
        sql.addSql("         EAC_CAN_ANSWER ");
        sql.addSql(" ) ");
        sql.addSql(" select  ");
        sql.addSql("         EAC_KBN_TAISYO  , ");
        sql.addSql("         EAC_MAIN_DSP_USE, ");
        sql.addSql("         EAC_MAIN_DSP    , ");
        sql.addSql("         EAC_LIST_CNT_USE, ");
        sql.addSql("         EAC_LIST_CNT    , ");
        sql.addSql("         EAC_AUID        , ");
        sql.addSql("         EAC_ADATE       , ");
        sql.addSql("         EAC_EUID        , ");
        sql.addSql("         EAC_EDATE       , ");
        sql.addSql("         EAC_FOLDER_USE  , ");
        sql.addSql("         EAC_FOLDER_SELECT, ");
        sql.addSql("         EAC_CAN_ANSWER ");
        sql.addSql(" from ENQ_ADM_CONF_BK ");
        sql.addSql(" order by EAC_ADATE ");
        sql.addSql(" limit 1; ");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" drop table ENQ_ADM_CONF_BK; ");
        sqlList.add(sql);
        //------------------------//

        return sqlList;
    }
}
