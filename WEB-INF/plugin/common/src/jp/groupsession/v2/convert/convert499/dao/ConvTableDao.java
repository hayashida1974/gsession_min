package jp.groupsession.v2.convert.convert499.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.MlCountMtController;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v4.9.9へコンバートする際に使用する
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

        //2月23日のテンプレートが「天皇誕生日 」なら「天皇誕生日」に
        sql = new SqlBuffer();
        sql.addSql(" update CMN_HOLIDAY_TEMPLATE set");
        sql.addSql(" HLT_NAME = ?");
        sql.addSql(" where HLT_DATE_MONTH = 2");
        sql.addSql(" and HLT_DATE_DAY = 23");
        sql.addSql(" and HLT_EXFLG = 0");
        sql.addSql(" and HLT_NAME = ?");
        sql.addStrValue("天皇誕生日");
        sql.addStrValue("天皇誕生日 ");
        sqlList.add(sql);


        //天皇誕生日の休日テンプレート重複修正
        //2月23日にテンプレートが重複する場合は1件を残して削除
        //生存優先度
        //第1キー：名称が天皇誕生日ではないか
        //第2キー：登録日時が新しい
        sql = new SqlBuffer();
        sql.addSql(" delete ");
        sql.addSql(" from CMN_HOLIDAY_TEMPLATE ");
        sql.addSql(" where HLT_DATE_MONTH=2 and HLT_DATE_DAY=23 and HLT_EXFLG = 0 ");
        sql.addSql("        and HLT_SID not in ( ");
        sql.addSql("   select ");
        sql.addSql("     LIVE_SID ");
        sql.addSql("   from ( ");

        sql.addSql("     select ");
        sql.addSql("       case HLT_NAME when '天皇誕生日' then 1 ");
        sql.addSql("          else 0 end as LIFE_WEIGHT, ");
        sql.addSql("       HLT_SID as LIVE_SID, ");
        sql.addSql("       HLT_ADDATE ");
        sql.addSql("     from CMN_HOLIDAY_TEMPLATE ");
        sql.addSql("     where HLT_DATE_MONTH=2 and HLT_DATE_DAY=23 and HLT_EXFLG = 0 ");
        sql.addSql("     order by LIFE_WEIGHT asc, HLT_ADDATE desc limit 1 ");
        sql.addSql("   ) as FILTER ");
        sql.addSql(" ) ");
        sqlList.add(sql);

        //ショートメールアカウント使用ユーザテーブル(SML_ACCOUNT_USER)から
        //「USR_SID < 100、GRP_SID = null」のレコードを削除する
        sql = new SqlBuffer();
        sql.addSql(" delete from SML_ACCOUNT_USER");
        sql.addSql(" where USR_SID < 100");
        sql.addSql(" and GRP_SID is NULL");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" delete from SML_ACCOUNT_USER");
        sql.addSql(" where SAC_SID in (0, 1)");
        sqlList.add(sql);

        //ショートメールアカウント使用ユーザテーブル(SML_ACCOUNT_USER)の変更
        sql = new SqlBuffer();
        sql.addSql(" update SML_ACCOUNT_USER set USR_SID=-1 where USR_SID is NULL");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" update SML_ACCOUNT_USER set GRP_SID=-1 where GRP_SID is NULL");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ACCOUNT_USER alter column USR_SID set not null");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ACCOUNT_USER alter column GRP_SID set not null");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ACCOUNT_USER alter column USR_SID set default -1");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ACCOUNT_USER alter column GRP_SID set default -1");
        sqlList.add(sql);

        //回覧板アカウント使用ユーザテーブル(CIR_ACCOUNT_USER)から
        //「USR_SID < 100、GRP_SID = null」のレコードを削除する
        sql = new SqlBuffer();
        sql.addSql(" delete from CIR_ACCOUNT_USER");
        sql.addSql(" where USR_SID < 100");
        sql.addSql(" and GRP_SID is NULL");
        sqlList.add(sql);

        //回覧板アカウント使用ユーザテーブル(CIR_ACCOUNT_USER)の変更
        sql = new SqlBuffer();
        sql.addSql(" update CIR_ACCOUNT_USER set USR_SID=-1 where USR_SID is NULL");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" update CIR_ACCOUNT_USER set GRP_SID=-1 where GRP_SID is NULL");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ACCOUNT_USER alter column USR_SID set not null");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ACCOUNT_USER alter column GRP_SID set not null");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ACCOUNT_USER alter column USR_SID set default -1");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table CIR_ACCOUNT_USER alter column GRP_SID set default -1");
        sqlList.add(sql);

        //チャットペア重複登録問題修復
        sqlList.addAll(
           __sqlListRepairCUP()
        );


        return sqlList;
    }
    /**
     *
     * <br>[機  能] チャットペア重複登録問題修復用クエリ出力
     * <br>[解  説] 下記テーブルからCHT_USER_PAIRでUIDの重複する組み合わせについて削除する
     * <br>         ・CMN_BINF（論理削除）
     * <br>         ・CHT_USER_DATA
     * <br>         ・CHT_USER_DATA_SUM
     * <br>         ・CHT_USER_VIEW
     * <br>         ・CHT_USER_PAIR
     * <br>[備  考]
     * @return SQL List
     */
    private ArrayList<SqlBuffer> __sqlListRepairCUP() {
        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();
        SqlBuffer sql = null;
        //・CMN_BINF（論理削除）
        sql = new SqlBuffer();
        sql.addSql(" update");
        sql.addSql("   CMN_BINF");
        sql.addSql(" set ");
        sql.addSql("   BIN_UPUSER = ?,");
        sql.addSql("   BIN_UPDATE = ?,");
        sql.addSql("   BIN_JKBN = ?");
        sql.addIntValue(0);
        sql.addDateValue(new UDate());
        sql.addIntValue(9);
        sql.addSql(" where");
        sql.addSql("   BIN_SID in (");
        sql.addSql("   select");
        sql.addSql("     BIN_SID");
        sql.addSql("   from");
        sql.addSql("     CHT_USER_DATA");
        sql.addSql("   where ");
        sql.addSql("     CUP_SID not in ( ");
        __writeDeleatePairFilter(sql);
        sql.addSql("   ) ");
        sql.addSql(" ); ");
        sqlList.add(sql);

        //・CHT_USER_DATA
        sql = new SqlBuffer();
        sql.addSql(" delete from CHT_USER_DATA  ");
        sql.addSql(" where CUP_SID not in ( ");
        __writeDeleatePairFilter(sql);
        sql.addSql(" ); ");
        sqlList.add(sql);

        //・CHT_USER_DATA_SUM
        sql = new SqlBuffer();
        sql.addSql(" delete from CHT_USER_DATA_SUM ");
        sql.addSql(" where CUP_SID not in ( ");
        __writeDeleatePairFilter(sql);
        sql.addSql(" ); ");
        sqlList.add(sql);

        //・CHT_USER_VIEW
        sql = new SqlBuffer();
        sql.addSql(" delete from CHT_USER_VIEW  ");
        sql.addSql(" where CUP_SID not in ( ");
        __writeDeleatePairFilter(sql);
        sql.addSql(" ); ");
        sqlList.add(sql);

        //・CHT_USER_PAIR
        sql = new SqlBuffer();
        sql.addSql(" delete from CHT_USER_PAIR  ");
        sql.addSql(" where CUP_SID not in ( ");
        __writeDeleatePairFilter(sql);
        sql.addSql(" ); ");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     *
     * <br>[機  能] 残すペアSID絞り込みSQLの書き込み
     * <br>[解  説]
     * <br>[備  考]
     * @param sql sb
     */
    private void __writeDeleatePairFilter(SqlBuffer sql) {
        sql.addSql(" select LIVE_SID from ( ");
        sql.addSql("   select  case ");
        sql.addSql("           when CUP_UID_F > CUP_UID_S then CUP_UID_S || '_' || CUP_UID_F ");
        sql.addSql("           else   CUP_UID_F || '_' || CUP_UID_S end as UID_PAIR, ");
        sql.addSql("           min(CUP_SID) as LIVE_SID ");
        sql.addSql("   from CHT_USER_PAIR ");
        sql.addSql("   group by UID_PAIR ");
        sql.addSql(" ) as PAIR_FILTER");
    }


}
