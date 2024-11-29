package jp.groupsession.v2.convert.convert454.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.fil.GSConstFile;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.5.4へコンバートする際に使用する
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

            //ファイル管理 アクセス制限設定
            __updateAccessSid();

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

        //メイン 添付ファイル設定 写真ファイル最大容量
        sql = new SqlBuffer();
        sql.addSql(
                " alter table CMN_FILE_CONF add FIC_PHOTO_SIZE varchar(4) not null default '1';");
        sqlList.add(sql);

        //ファイル管理 階層権限チェック
        sql = new SqlBuffer();
        sql.addSql(" create table FILE_DACCESS_CONF");
        sql.addSql(" (");
        sql.addSql("   FDR_SID          integer      not null,");
        sql.addSql("   USR_SID          integer      not null,");
        sql.addSql("   USR_KBN          integer      not null,");
        sql.addSql("   FDA_AUTH         integer      not null,");
        sql.addSql("   primary key (FDR_SID, USR_SID, USR_KBN)");
        sql.addSql(" );");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_DIRECTORY add FDR_ACCESS_SID integer not null default 0;");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" insert into");
        sql.addSql("   FILE_DACCESS_CONF");
        sql.addSql("   (FDR_SID,USR_SID,USR_KBN,FDA_AUTH)");
        sql.addSql("     select");
        sql.addSql("       FDR_SID,USR_SID,USR_KBN,FAA_AUTH");
        sql.addSql("     from");
        sql.addSql("       FILE_ACCESS_CONF,");
        sql.addSql("       FILE_DIRECTORY");
        sql.addSql("     where");
        sql.addSql("       FILE_ACCESS_CONF.FCB_SID = FILE_DIRECTORY.FCB_SID");
        sql.addSql("     and");
        sql.addSql("       FILE_DIRECTORY.FDR_PARENT_SID = 0;");
        sqlList.add(sql);

        //ファイル管理 検索警告
        sql = new SqlBuffer();
        sql.addSql(" alter table FILE_ACONF add FAC_WARN_CNT integer not null default 0;");
        sqlList.add(sql);

        //ファイル管理 キャビネット作成権限者に各キャビネットの管理権限を付与
        sql = new SqlBuffer();
        sql.addSql(" insert into ");
        sql.addSql("    FILE_CABINET_ADMIN ");
        sql.addSql(" select distinct ");
        sql.addSql("    FC.FCB_SID as FCB_SID, ");
        sql.addSql("    CB.USR_SID as USR_SID ");
        sql.addSql(" from ");
        sql.addSql("    FILE_CRT_CONF FCC, ");
        sql.addSql("    CMN_BELONGM CB, ");
        sql.addSql("    FILE_CABINET FC ");
        sql.addSql(" where ");
        sql.addSql("    ((exists(select 0 from FILE_ACONF where FAC_CRT_KBN = 1) ");
        sql.addSql("      and FCC.USR_KBN = 1 ");
        sql.addSql("      and CB.GRP_SID = FCC.USR_SID) or ");
        sql.addSql("     (exists(select 0 from FILE_ACONF where FAC_CRT_KBN = 2) ");
        sql.addSql("      and FCC.USR_KBN = 0 ");
        sql.addSql("      and CB.USR_SID = FCC.USR_SID) ");
        sql.addSql("    )  ");
        sql.addSql("    and not exists( ");
        sql.addSql("      select 0 from FILE_CABINET_ADMIN ");
        sql.addSql("      where FCB_SID = FC.FCB_SID and USR_SID = CB.USR_SID ");
        sql.addSql("    ); ");
        sqlList.add(sql);

        //アンケート アンケート_基本情報 公開期間_終了日 指定
        sql = new SqlBuffer();
        sql.addSql(" alter table ENQ_MAIN add EMN_OPEN_END_KBN integer;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" update ENQ_MAIN");
        sql.addSql(" set EMN_OPEN_END_KBN = 0;");
        sqlList.add(sql);

        //アンケート アンケート_基本情報 結果公開_開始日
        sql = new SqlBuffer();
        sql.addSql(" alter table ENQ_MAIN add EMN_ANS_PUB_STR date;");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" update ENQ_MAIN");
        sql.addSql(" set EMN_ANS_PUB_STR = (");
        sql.addSql("   select");
        sql.addSql("     ENQ_DATA.EMN_OPEN_STR");
        sql.addSql("   from");
        sql.addSql("     ENQ_MAIN ENQ_DATA");
        sql.addSql("   where");
        sql.addSql("     ENQ_MAIN.EMN_SID = ENQ_DATA.EMN_SID");
        sql.addSql(" );");
        sqlList.add(sql);

        //アンケート アンケート_基本情報 結果非公開アンケートの公開期間データの削除
        sql = new SqlBuffer();
        sql.addSql(" update ENQ_MAIN");
        sql.addSql(" set EMN_OPEN_END = null,");
        sql.addSql("     EMN_OPEN_END_KBN = 1");
        sql.addSql(" where");
        sql.addSql("   ENQ_MAIN.EMN_ANS_OPEN = 0");
        sql.addSql(" ;");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能] 削除されたユーザ分のBBS_THRE_VIEWを削除します。
     * <br>[解  説]
     * <br>[備  考]
     * @return delete count
     * @throws SQLException SQL実行例外
     */
    public int deleteThreView() throws SQLException {

        int count = 0;

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   BBS_THRE_VIEW");
            sql.addSql(" where");
            sql.addSql("   USR_SID in ( ");
            sql.addSql("     select USR_SID from CMN_USRM where USR_JKBN = ?");
            sql.addSql("   ) ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConstUser.USER_JTKBN_DELETE);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            count = pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>指定したディレクトリSID以下のサブディレクトリ・ファイルのアクセス設定SIDを更新する。
     * @throws SQLException SQL実行例外
     */
    private void __updateAccessSid() throws SQLException {
        boolean h2db = (DBUtilFactory.getInstance().getDbType() == GSConst.DBTYPE_H2DB);
        if (h2db) {
            __updateAccessSidH2();
        } else {
            __updateAccessSidPosgre();
        }
    }

    /**
     * <p>指定したディレクトリSID以下のサブディレクトリ・ファイルのアクセス設定SIDを更新する。
     * @throws SQLException SQL実行例外
     */
    private void __updateAccessSidPosgre() throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   FILE_DIRECTORY");
            sql.addSql(" set ");
            sql.addSql("   FDR_ACCESS_SID = DIRECTORY.ACCESS_SID");
            sql.addSql(" from ");
            // sub-5 ↓ FILE_DACCESS_CONF が存在する直近のディレクトリSIDを取り出す ---------------*
            sql.addSql("   (select distinct");
            sql.addSql("      DIRECTORY.FDR_SID,");
            sql.addSql("      DIRECTORY.ACCESS_SID");
            sql.addSql("    from");
            // sub-4 ↓ FILE_DACCESS_CONF が存在する直近のディレクトリに順位を付加 ----------------*
            sql.addSql("      (select");
            sql.addSql("         DIRECTORY.FDR_SID,");
            sql.addSql("         coalesce(DACCESS.FDR_SID, ?) ACCESS_SID,");
            sql.addSql("         rank() over (partition by DIRECTORY.FDR_SID");
            sql.addSql("           order by case");
            sql.addSql("                    when DACCESS.FDR_SID is null then 99999");
            sql.addSql("                    else rn end) RK");
            sql.addSql("       from");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            // sub-3 ↓ sub-1 で取り出した下層配列に行番号を付加して縦行に変換 --------------------*
            sql.addSql("         (select");
            sql.addSql("            DIRECTORY.FDR_SID,");
            sql.addSql("            DIRECTORY.PATH,");
            sql.addSql("            generate_subscripts(DIRECTORY.PATH, 1) rn");
            sql.addSql("          from");
            // sub-1 ↓ 上位～下位のディレクトリ情報を取得するための階層問い合わせ -----------------*
            sql.addSql("            (with recursive rec(FDR_SID, FDR_PARENT_SID, PATH) as (");
            sql.addSql("             select A.FDR_SID, A.FDR_PARENT_SID, array[A.FDR_SID]");
            sql.addSql("               from FILE_DIRECTORY A");
            sql.addSql("              where A.FDR_PARENT_SID = ?");
            sql.addIntValue(GSConstFile.DIRECTORY_ROOT);
            sql.addSql("              union all");
            sql.addSql("             select B.FDR_SID, B.FDR_PARENT_SID, B.FDR_SID || A.PATH");
            sql.addSql("               from rec A");
            sql.addSql("               join FILE_DIRECTORY B on A.FDR_SID = B.FDR_PARENT_SID)");
            sql.addSql("             select distinct rec.FDR_SID,PATH");
            sql.addSql("               from rec");
            // sub-1 ↑ -----------------------------------------------------------------------*
            sql.addSql("            ) DIRECTORY");
            // sub-3 ↑ -----------------------------------------------------------------------*
            sql.addSql("         ) DIRECTORY");
            sql.addSql("       left join");
            sql.addSql("         (select distinct FDR_SID from FILE_DACCESS_CONF) DACCESS");
            sql.addSql("         on DIRECTORY.PATH[rn] = DACCESS.FDR_SID");
            sql.addSql("      ) DIRECTORY");
            // sub-4 ↑ -----------------------------------------------------------------------*
            sql.addSql("    where");
            sql.addSql("      DIRECTORY.RK = 1");
            sql.addSql("   ) DIRECTORY");
            // sub-5 ↑ ---------------------------------------------------------------- ------*
            sql.addSql(" where");
            sql.addSql("   FILE_DIRECTORY.FDR_SID = DIRECTORY.FDR_SID");

            pstmt = con.prepareStatement(sql.toSqlString());
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
     * <p>指定したディレクトリSID以下のサブディレクトリ・ファイルのアクセス設定SIDを更新する。
     * @throws SQLException SQL実行例外
     */
    private void __updateAccessSidH2() throws SQLException {

        PreparedStatement pstmt = null;
        PreparedStatement pstmtUpd = null;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;
        try {

            /*再利用するセレクトステートメント*/
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("    select distinct ");
            sql.addSql("      FDR_SID, ");
            sql.addSql("      ( ");
            sql.addSql("        select");
            sql.addSql("          count(distinct A.FDR_SID)");
            sql.addSql("        from FILE_DACCESS_CONF A");
            sql.addSql("        where A.FDR_SID = D.FDR_SID ");
            sql.addSql("      ) as ACCESS ");
            sql.addSql("    from ");
            sql.addSql("      FILE_DIRECTORY D ");
            sql.addSql("    where FDR_PARENT_SID = ? ");
            pstmt = con.prepareStatement(sql.toSqlString());

            /*再利用するアップデートステートメント*/
            SqlBuffer sqlUpd = new SqlBuffer();
            sqlUpd.addSql("    update ");
            sqlUpd.addSql("      FILE_DIRECTORY ");
            sqlUpd.addSql("    set FDR_ACCESS_SID = ?");
            sqlUpd.addSql("    where FDR_SID = ? ");
            pstmtUpd = con.prepareStatement(sqlUpd.toSqlString());

            int dirSid = 0;
            List<Integer[]> prevList = new ArrayList<Integer[]>();
            int accessSid = 0;

            Integer[] first = new Integer[2];
            //初回指定ディレクトリの更新
            accessSid = __getParrentAccessSid(dirSid);
            sqlUpd.addIntValue(accessSid);
            sqlUpd.addIntValue(dirSid);
            log__.info(sqlUpd.toLogString());
            sqlUpd.setParameter(pstmtUpd);
            pstmtUpd.executeUpdate();
            sqlUpd.clearValue();

            first[0] = dirSid;
            first[1] = accessSid;
            prevList.add(first);
            for (int i = 0; i <= GSConstFile.MAX_LEVEL; i++) {
                List<Integer[]> updList = new ArrayList<Integer[]>();
                //対象サブディレクトリを階層ごとに取得
                for (Integer[] prev : prevList) {
                    sql.addIntValue(prev[0]);
                    log__.info(sql.toLogString());
                    sql.setParameter(pstmt);
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        Integer[] upd = new Integer[2];
                        upd[0] = rs.getInt("FDR_SID");
                        //個別アクセス設定がない場合は親のアクセスSIDを使用する
                        if (rs.getInt("ACCESS") > 0) {
                            upd[1] = upd[0];
                        } else {
                            upd[1] = prev[1];
                        }
                        updList.add(upd);
                    }
                    sql.clearValue();
                    JDBCUtil.closeResultSet(rs);
                }
                //対象サブディレクトリを更新
                for (Integer[] upd : updList) {
                    sqlUpd.addIntValue(upd[1]);
                    sqlUpd.addIntValue(upd[0]);
                    log__.info(sqlUpd.toLogString());
                    sqlUpd.setParameter(pstmtUpd);
                    pstmtUpd.executeUpdate();
                    sqlUpd.clearValue();
                }
                prevList = updList;
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmtUpd);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     *
     * <br>[機  能] 直近のアクセスSIDを取得する
     * <br>[解  説]
     * <br>[備  考] 指定ディレクトリのアクセスSIDが適切であることを前提とする。
     * @param fdrSid 指定ディレクトリ
     * @return アクセスSID
     * @throws SQLException SQL実行時例外
     */
   private int __getParrentAccessSid(int fdrSid) throws SQLException {
       int dirSid = fdrSid;
       PreparedStatement pstmt = null;
       int ret = 0;
       Connection con = null;
       con = getCon();
       ResultSet rs = null;

       try {
           SqlBuffer sql = new SqlBuffer();
           sql.addSql("    select distinct ");
           sql.addSql("      FDR_SID, ");
           sql.addSql("      FDR_PARENT_SID, ");
           sql.addSql("      FDR_LEVEL, ");
           sql.addSql("      coalesce(( ");
           sql.addSql("        select distinct");
           sql.addSql("          A.FDR_SID");
           sql.addSql("        from");
           sql.addSql("          FILE_DACCESS_CONF A");
           sql.addSql("        where A.FDR_SID = D.FDR_SID ");
           sql.addSql("      ), 0) as FDR_ACCESS_SID ");
           sql.addSql("    from ");
           sql.addSql("      FILE_DIRECTORY D ");
           sql.addSql("    where FDR_SID = ? ");
           sql.addSql("      and FDR_LEVEL <= ? ");
           pstmt = con.prepareStatement(sql.toSqlString());
           for (int i = GSConstFile.MAX_LEVEL; i >= 0; i--) {
               if (dirSid == 0) {
                   break;
               }
               sql.addIntValue(dirSid);
               sql.addIntValue(i);
               log__.info(sql.toLogString());
               sql.setParameter(pstmt);
               rs = pstmt.executeQuery();
               if (rs.next()) {
                   dirSid = rs.getInt("FDR_PARENT_SID");
                   ret = rs.getInt("FDR_ACCESS_SID");
                   i = rs.getInt("FDR_LEVEL");
                   if (ret != 0) {
                       break;
                   }
               } else {
                   return 0;
               }
               sql.clearValue();
               JDBCUtil.closeResultSet(rs);
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeResultSet(rs);
           JDBCUtil.closeStatement(pstmt);
       }

       return ret;
   }

}