package jp.groupsession.v2.convert.convert301.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.convert.convert301.model.WmlDirectoryModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v3.0.1へコンバートする際に使用する
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
     * @param mtCon 採番コントローラ
     * @throws SQLException 例外
     */
    public void convert(MlCountMtController mtCon) throws SQLException {

        log__.debug("-- DBコンバート開始 --");

        //新規テーブルのcreate、insertを行う
        createTable();

        //「保管」ディレクトリを登録する
        __insertMailStrageDirectory(mtCon);

        //テーマの追加を行う。
        __insertTheme();

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

        //メイン表示設定 天気予報
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_MDISP_WEATHER");
        sql.addSql(" (");
        sql.addSql("     USR_SID         integer         not null,");
        sql.addSql("     MDW_AREA        integer         not null,");
        sql.addSql("     MDW_SORT        integer         not null,");
        sql.addSql("     MDW_AUID        integer         not null,");
        sql.addSql("     MDW_ADATE       timestamp       not null,");
        sql.addSql("     MDW_EUID        integer         not null,");
        sql.addSql("     MDW_EDATE       timestamp       not null,");
        sql.addSql("     primary key (USR_SID, MDW_AREA) ");
        sql.addSql(" ) ;");
        sqlList.add(sql);

        //天気予報地域
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_WEATHER_AREA ");
        sql.addSql(" (");
        sql.addSql("   CWA_SID  integer not null,");
        sql.addSql("   CWA_NAME varchar(50) not null,");
        sql.addSql("   CWA_SORT integer not null,");
        sql.addSql("   primary key (CWA_SID)");
        sql.addSql(" );");
        sqlList.add(sql);
        __setWeatherAreaSql(sqlList);

        //社員情報:管理者ソート設定
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_USR_ADM_SORT_CONF");
        sql.addSql(" (");
        sql.addSql("   UAS_SORT_KBN          integer   not null,");
        sql.addSql("   UAS_SORT_KEY1         integer   not null,");
        sql.addSql("   UAS_SORT_ORDER1       integer   not null,");
        sql.addSql("   UAS_SORT_KEY2         integer   not null,");
        sql.addSql("   UAS_SORT_ORDER2       integer   not null,");
        sql.addSql("   UAS_AUID              integer   not null,");
        sql.addSql("   UAS_ADATE             timestamp not null,");
        sql.addSql("   UAS_EUID              integer   not null,");
        sql.addSql("   UAS_EDATE             timestamp not null");
        sql.addSql(" );");
        sqlList.add(sql);

        //社員情報:個人ソート設定
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_USR_PRI_SORT_CONF");
        sql.addSql(" (");
        sql.addSql("   USR_SID               integer   not null,");
        sql.addSql("   UPS_SORT_KEY1         integer   not null,");
        sql.addSql("   UPS_SORT_ORDER1       integer   not null,");
        sql.addSql("   UPS_SORT_KEY2         integer   not null,");
        sql.addSql("   UPS_SORT_ORDER2       integer   not null,");
        sql.addSql("   UPS_AUID              integer   not null,");
        sql.addSql("   UPS_ADATE             timestamp not null,");
        sql.addSql("   UPS_EUID              integer   not null,");
        sql.addSql("   UPS_EDATE             timestamp not null,");
        sql.addSql("   primary key (USR_SID)");
        sql.addSql(" );");
        sqlList.add(sql);

        //WEBメール メール本文テーブル変更
        sql = new SqlBuffer();
        sql.addSql(" alter table WML_MAIL_BODY add WMB_CHARSET varchar(50)");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <p>テーマの追加を行う。
     * @throws SQLException SQL実行例外
     */
    private void __insertTheme() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   count(CTM_SID) as CNT");
            sql.addSql(" from ");
            sql.addSql("   CMN_THEME");
            sql.addSql(" where");
            sql.addSql("   CTM_SID = ?");
            sql.addIntValue(6);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            boolean existTheme6 = false;
            if (rs.next()) {
                existTheme6 = rs.getInt("CNT") > 0;
            }


            if (!existTheme6) {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);

                //テーマテーブル初期値（テーマ6）
                sql = new SqlBuffer();
                sql.addSql(" insert into CMN_THEME");
                sql.addSql(" (");
                sql.addSql("   CTM_SID,");
                sql.addSql("   CTM_ID,");
                sql.addSql("   CTM_NAME,");
                sql.addSql("   CTM_PATH,");
                sql.addSql("   CTM_PATH_IMG,");
                sql.addSql("   CTM_AUID,");
                sql.addSql("   CTM_ADATE,");
                sql.addSql("   CTM_EUID,");
                sql.addSql("   CTM_EDATE,");
                sql.addSql(" )");
                sql.addSql(" values");
                sql.addSql(" (");
                sql.addSql("   6,");
                sql.addSql("   'theme6',");
                sql.addSql("   '黄色',");
                sql.addSql("   'common/css/theme6',");
                sql.addSql("   'common/images/image_theme6.gif',");
                sql.addSql("   0,");
                sql.addSql("   current_timestamp,");
                sql.addSql("   0,");
                sql.addSql("   current_timestamp");
                sql.addSql(" );");

                log__.info(sql.toLogString());
                pstmt = con.prepareStatement(sql.toSqlString());
                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

    }

    /**
     * <p>「保管」ディレクトリの追加を行う。
     * @param mtCon 採番コントローラ
     * @throws SQLException SQL実行例外
     */
    private void __insertMailStrageDirectory(MlCountMtController mtCon)
    throws SQLException {

        //WEBメール ディレクトリ種別 保管
        final int dirTypeStrage = 7;
        //WEBメール 迷惑メールディレクトリ区分 表示
        final int dspViewOk = 0;
        //WEBメール デフォルトディレクトリ デフォルトディレクトリ
        final int defDefault = 1;
        //採番ID WEBメール
        final String sbnSid_Webmail = "webmail";
        //WEBメール 採番IDサブ ディレクトリSID
        final String sbnSid_Sub_Directory = "directory";

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WAC_SID");
            sql.addSql(" from ");
            sql.addSql("   WML_ACCOUNT");
            sql.addSql(" where");
            sql.addSql("   WAC_SID not in (");
            sql.addSql("     select WAC_SID from WML_DIRECTORY");
            sql.addSql("     where WDR_TYPE = ?");
            sql.addSql("   )");
            sql.addIntValue(dirTypeStrage);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            List<Integer> wacSidList = new ArrayList<Integer>();
            while (rs.next()) {
                wacSidList.add(rs.getInt("WAC_SID"));
            }

            if (!wacSidList.isEmpty()) {
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);

                WmlDirectoryModel dirModel = new WmlDirectoryModel();
                dirModel.setWdrType(dirTypeStrage);
                dirModel.setWdrName("保管");
                dirModel.setWdrView(dspViewOk);
                dirModel.setWdrDefault(defDefault);

                for (Integer wacSid : wacSidList) {
                    long wdrSid = mtCon.getSaibanNumber(sbnSid_Webmail,
                                                        sbnSid_Sub_Directory,
                                                        0);

                    dirModel.setWdrSid(wdrSid);
                    dirModel.setWacSid(wacSid.intValue());

                    __insertDirectory(con, dirModel);
                }
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

    }

    /**
     * <br>[機  能] WEBメール ディレクトリ情報のinsertを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bean ディレクトリ情報
     * @throws SQLException SQL実行時例外
     */
    private void __insertDirectory(Connection con, WmlDirectoryModel bean)
    throws SQLException {

        PreparedStatement pstmt = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" WML_DIRECTORY(");
            sql.addSql("   WDR_SID,");
            sql.addSql("   WAC_SID,");
            sql.addSql("   WDR_NAME,");
            sql.addSql("   WDR_TYPE,");
            sql.addSql("   WDR_VIEW,");
            sql.addSql("   WDR_DEFAULT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addLongValue(bean.getWdrSid());
            sql.addIntValue(bean.getWacSid());
            sql.addStrValue(bean.getWdrName());
            sql.addIntValue(bean.getWdrType());
            sql.addIntValue(bean.getWdrView());
            sql.addIntValue(bean.getWdrDefault());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] 天気予報 地域のinsert SQLを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param sqlList コンバートSQL一覧
     * @return コンバートSQL一覧
     */
    private List<SqlBuffer> __setWeatherAreaSql(List<SqlBuffer> sqlList) {
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   1,'稚内',1");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   2,'旭川',2");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   3,'札幌',3");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   4,'網走',4");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   5,'釧路',5");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   6,'室蘭',6");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   7,'函館',7");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   8,'青森',8");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   9,'秋田',9");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   10,'盛岡',10");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   11,'仙台',11");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   12,'山形',12");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   13,'福島',13");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   14,'水戸',14");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   15,'宇都宮',15");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   16,'前橋',16");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   17,'熊谷',17");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   18,'東京',18");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   20,'銚子',20");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   21,'横浜',21");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   22,'長野',22");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   23,'甲府',23");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   24,'新潟',24");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   25,'静岡',25");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   26,'名古屋',26");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   27,'岐阜',27");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   28,'津',28");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   29,'富山',29");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   30,'金沢',30");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   31,'福井',31");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   32,'彦根',32");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   33,'京都',33");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   34,'大阪',34");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   35,'神戸',35");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   36,'奈良',36");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   37,'和歌山',37");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   38,'岡山',38");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   39,'広島',39");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   40,'松江',40");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   41,'鳥取',41");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   42,'下関',42");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   43,'徳島',43");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   44,'高松',44");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   45,'松山',45");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   46,'高知',46");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   47,'福岡',47");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   48,'大分',48");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   49,'長崎',49");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   50,'佐賀',50");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   51,'熊本',51");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   52,'宮崎',52");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   53,'鹿児島',53");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_WEATHER_AREA(CWA_SID, CWA_NAME, CWA_SORT)");
        sql.addSql(" values (");
        sql.addSql("   55,'那覇',55");
        sql.addSql(" );");
        sqlList.add(sql);

        return sqlList;
    }
}