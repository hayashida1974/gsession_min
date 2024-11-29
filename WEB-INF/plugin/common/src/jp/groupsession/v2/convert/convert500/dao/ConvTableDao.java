package jp.groupsession.v2.convert.convert500.dao;

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
 * <br>[解  説] v5.0.0へコンバートする際に使用する
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
                sql.setParameter(pstmt);
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

        // テーマ（デフォルト）
        sql = new SqlBuffer();
        sql.addSql(" update CMN_THEME set");
        sql.addSql("  CTM_NAME = 'デフォルト（Classic）',");
        sql.addSql("  CTM_PATH = 'common/css/theme_classic/default',");
        sql.addSql("  CTM_PATH_IMG = 'common/images/themeImg_default.png'");
        sql.addSql(" where CTM_SID = 1;");
        sqlList.add(sql);

        // テーマ（グレー）
        sql = new SqlBuffer();
        sql.addSql(" update CMN_THEME set");
        sql.addSql("  CTM_NAME = 'グレー（Classic）',");
        sql.addSql("  CTM_PATH = 'common/css/theme_classic/gray',");
        sql.addSql("  CTM_PATH_IMG = 'common/images/themeImg_gray.png'");
        sql.addSql(" where CTM_SID = 2;");
        sqlList.add(sql);

        // テーマ（緑）
        sql = new SqlBuffer();
        sql.addSql(" update CMN_THEME set");
        sql.addSql("  CTM_NAME = '緑（Classic）',");
        sql.addSql("  CTM_PATH = 'common/css/theme_classic/green',");
        sql.addSql("  CTM_PATH_IMG = 'common/images/themeImg_green.png'");
        sql.addSql(" where CTM_SID = 3;");
        sqlList.add(sql);

        // テーマ（赤）
        sql = new SqlBuffer();
        sql.addSql(" update CMN_THEME set");
        sql.addSql("  CTM_NAME = '赤（Classic）',");
        sql.addSql("  CTM_PATH = 'common/css/theme_classic/red',");
        sql.addSql("  CTM_PATH_IMG = 'common/images/themeImg_red.png'");
        sql.addSql(" where CTM_SID = 4;");
        sqlList.add(sql);
        // テーマ（ピンク）
        sql = new SqlBuffer();
        sql.addSql(" update CMN_THEME set");
        sql.addSql("  CTM_NAME = 'ピンク（Classic）',");
        sql.addSql("  CTM_PATH = 'common/css/theme_classic/pink',");
        sql.addSql("  CTM_PATH_IMG = 'common/images/themeImg_pink.png'");
        sql.addSql(" where CTM_SID = 5;");
        sqlList.add(sql);

        // テーマ（黄色）
        sql = new SqlBuffer();
        sql.addSql(" update CMN_THEME set");
        sql.addSql("  CTM_NAME = '黄色（Classic）',");
        sql.addSql("  CTM_PATH = 'common/css/theme_classic/yellow',");
        sql.addSql("  CTM_PATH_IMG = 'common/images/themeImg_yellow.png'");
        sql.addSql(" where CTM_SID = 6;");
        sqlList.add(sql);

        // テーマ（オリジナル）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME (");
        sql.addSql("  CTM_SID,");
        sql.addSql("  CTM_ID,");
        sql.addSql("  CTM_NAME,");
        sql.addSql("  CTM_PATH,");
        sql.addSql("  CTM_PATH_IMG,");
        sql.addSql("  CTM_AUID,");
        sql.addSql("  CTM_ADATE,");
        sql.addSql("  CTM_EUID,");
        sql.addSql("  CTM_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  7,");
        sql.addSql("  'theme7',");
        sql.addSql("  'オリジナル',");
        sql.addSql("  'common/css/theme_original/light/original',");
        sql.addSql("  'common/images/themeImg_original.png',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        // テーマ（草原）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME (");
        sql.addSql("  CTM_SID,");
        sql.addSql("  CTM_ID,");
        sql.addSql("  CTM_NAME,");
        sql.addSql("  CTM_PATH,");
        sql.addSql("  CTM_PATH_IMG,");
        sql.addSql("  CTM_AUID,");
        sql.addSql("  CTM_ADATE,");
        sql.addSql("  CTM_EUID,");
        sql.addSql("  CTM_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  8,");
        sql.addSql("  'theme8',");
        sql.addSql("  '草原',");
        sql.addSql("  'common/css/theme_original/light/sougen',");
        sql.addSql("  'common/images/themeImg_sougen.png',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        // テーマ（夕日）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME (");
        sql.addSql("  CTM_SID,");
        sql.addSql("  CTM_ID,");
        sql.addSql("  CTM_NAME,");
        sql.addSql("  CTM_PATH,");
        sql.addSql("  CTM_PATH_IMG,");
        sql.addSql("  CTM_AUID,");
        sql.addSql("  CTM_ADATE,");
        sql.addSql("  CTM_EUID,");
        sql.addSql("  CTM_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  9,");
        sql.addSql("  'theme9',");
        sql.addSql("  '夕日',");
        sql.addSql("  'common/css/theme_original/light/sunset',");
        sql.addSql("  'common/images/themeImg_sunset.png',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        // テーマ（さくら）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME (");
        sql.addSql("  CTM_SID,");
        sql.addSql("  CTM_ID,");
        sql.addSql("  CTM_NAME,");
        sql.addSql("  CTM_PATH,");
        sql.addSql("  CTM_PATH_IMG,");
        sql.addSql("  CTM_AUID,");
        sql.addSql("  CTM_ADATE,");
        sql.addSql("  CTM_EUID,");
        sql.addSql("  CTM_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  10,");
        sql.addSql("  'theme10',");
        sql.addSql("  'さくら',");
        sql.addSql("  'common/css/theme_original/light/sakura',");
        sql.addSql("  'common/images/themeImg_sakura.png',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        // テーマ（青空）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME (");
        sql.addSql("  CTM_SID,");
        sql.addSql("  CTM_ID,");
        sql.addSql("  CTM_NAME,");
        sql.addSql("  CTM_PATH,");
        sql.addSql("  CTM_PATH_IMG,");
        sql.addSql("  CTM_AUID,");
        sql.addSql("  CTM_ADATE,");
        sql.addSql("  CTM_EUID,");
        sql.addSql("  CTM_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  11,");
        sql.addSql("  'theme11',");
        sql.addSql("  '青空',");
        sql.addSql("  'common/css/theme_original/light/sky',");
        sql.addSql("  'common/images/themeImg_sky.png',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        // テーマ（シティ）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME (");
        sql.addSql("  CTM_SID,");
        sql.addSql("  CTM_ID,");
        sql.addSql("  CTM_NAME,");
        sql.addSql("  CTM_PATH,");
        sql.addSql("  CTM_PATH_IMG,");
        sql.addSql("  CTM_AUID,");
        sql.addSql("  CTM_ADATE,");
        sql.addSql("  CTM_EUID,");
        sql.addSql("  CTM_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  12,");
        sql.addSql("  'theme12',");
        sql.addSql("  'シティ',");
        sql.addSql("  'common/css/theme_original/light/city',");
        sql.addSql("  'common/images/themeImg_city.png',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        // テーマ（ダーク）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME (");
        sql.addSql("  CTM_SID,");
        sql.addSql("  CTM_ID,");
        sql.addSql("  CTM_NAME,");
        sql.addSql("  CTM_PATH,");
        sql.addSql("  CTM_PATH_IMG,");
        sql.addSql("  CTM_AUID,");
        sql.addSql("  CTM_ADATE,");
        sql.addSql("  CTM_EUID,");
        sql.addSql("  CTM_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  13,");
        sql.addSql("  'theme13',");
        sql.addSql("  'ダーク',");
        sql.addSql("  'common/css/theme_original/dark/dark',");
        sql.addSql("  'common/images/themeImg_dark.png',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        // テーマ（木目）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME (");
        sql.addSql("  CTM_SID,");
        sql.addSql("  CTM_ID,");
        sql.addSql("  CTM_NAME,");
        sql.addSql("  CTM_PATH,");
        sql.addSql("  CTM_PATH_IMG,");
        sql.addSql("  CTM_AUID,");
        sql.addSql("  CTM_ADATE,");
        sql.addSql("  CTM_EUID,");
        sql.addSql("  CTM_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  14,");
        sql.addSql("  'theme14',");
        sql.addSql("  '木目',");
        sql.addSql("  'common/css/theme_original/light/mokume',");
        sql.addSql("  'common/images/themeImg_mokume.png',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        // テーマ（ダークブルー）
        sql = new SqlBuffer();
        sql.addSql(" insert into CMN_THEME (");
        sql.addSql("  CTM_SID,");
        sql.addSql("  CTM_ID,");
        sql.addSql("  CTM_NAME,");
        sql.addSql("  CTM_PATH,");
        sql.addSql("  CTM_PATH_IMG,");
        sql.addSql("  CTM_AUID,");
        sql.addSql("  CTM_ADATE,");
        sql.addSql("  CTM_EUID,");
        sql.addSql("  CTM_EDATE");
        sql.addSql(" ) values (");
        sql.addSql("  15,");
        sql.addSql("  'theme15',");
        sql.addSql("  'ダークブルー',");
        sql.addSql("  'common/css/theme_original/dark/darkblue',");
        sql.addSql("  'common/images/themeImg_darkblue.png',");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp,");
        sql.addSql("  0,");
        sql.addSql("  current_timestamp");
        sql.addSql(" );");
        sqlList.add(sql);

        //システムメールのユーザアイコンを強制上書き
        //コンバートで画像を削除
        //起動時の処理で画像が設定される
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   CMN_BINF");
        sql.addSql(" set ");
        sql.addSql("   BIN_JKBN = 9");
        sql.addSql(" where ");
        sql.addSql("   BIN_SID=(");
        sql.addSql("    select BIN_SID from CMN_USRM_INF");
        sql.addSql("   where USR_SID=1");
        sql.addSql("   );");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   CMN_USRM_INF");
        sql.addSql(" set ");
        sql.addSql("   BIN_SID=0");
        sql.addSql(" where ");
        sql.addSql("   USR_SID=1;");
        sqlList.add(sql);

        return sqlList;
    }

}
