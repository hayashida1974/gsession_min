package jp.groupsession.v2.convert.convert510.dao;

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
 * <br>[解  説] v5.1.0へコンバートする際に使用する
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

        //ブックマーク 管理者設定(BMK_ACONF)に「BAC_LIMIT」フィールドを追加
        __addSql(sqlList,
                " alter table BMK_ACONF add column BAC_LIMIT integer not null default 1;");

        /*--- No.1022 テーブル定義の変更 start ---*/
        //掲示板 草稿添付情報(BBS_SOUKOU_BIN)の変更
        __addSql(sqlList,
                " alter table BBS_SOUKOU_BIN alter column BIN_SID type bigint;");

        //掲示板 投稿草稿本文添付情報(BBS_SOUKOU_BODY_BIN)の変更
        __addSql(sqlList,
                " alter table BBS_SOUKOU_BODY_BIN alter column BIN_SID type bigint;");

        //プラグイン使用制限(CMN_PLUGIN_CONTROL)の変更
        __addSql(sqlList,
                "update CMN_PLUGIN_CONTROL set PCT_TYPE = 0 where PCT_TYPE is null;");
        __addSql(sqlList,
                "alter table CMN_PLUGIN_CONTROL alter column PCT_TYPE set default 0;");
        __addSql(sqlList,
                "alter table CMN_PLUGIN_CONTROL alter column PCT_TYPE set not null;");

        //稟議 複写用経路ステップ選択ユーザ情報(RNG_COPY_KEIROSTEP_SELECT)の変更
        __addSql(sqlList,
                "alter table RNG_COPY_KEIROSTEP_SELECT alter column GRP_SID type integer;");
        __addSql(sqlList,
                "alter table RNG_COPY_KEIROSTEP_SELECT alter column POS_SID type integer;");
        __addSql(sqlList,
                "alter table RNG_COPY_KEIROSTEP_SELECT alter column RCK_SORT type integer;");
        __addSql(sqlList,
                "alter table RNG_COPY_KEIROSTEP_SELECT alter column RKS_SID type integer;");
        __addSql(sqlList,
                "alter table RNG_COPY_KEIROSTEP_SELECT alter column USR_SID type integer;");

        //稟議 複写用経路ステップ情報(RNG_COPY_KEIRO_STEP)の変更
        __addSql(sqlList,
                "alter table RNG_COPY_KEIRO_STEP alter column RKS_SID type integer;");
        __addSql(sqlList,
                "alter table RNG_COPY_KEIRO_STEP alter column RCK_SORT type integer;");
        __addSql(sqlList,
                "alter table RNG_COPY_KEIRO_STEP alter column RTK_SID type integer;");

        //施設予約 施設グループ(RSV_SIS_GRP)の変更
        __addSql(sqlList,
                "update RSV_SIS_GRP set RSG_ACS_LIMIT_KBN = -1 where RSG_ACS_LIMIT_KBN is null;");
        __addSql(sqlList,
                "alter table RSV_SIS_GRP alter column RSG_ACS_LIMIT_KBN set default -1;");
        __addSql(sqlList,
                "alter table RSV_SIS_GRP alter column RSG_ACS_LIMIT_KBN set not null;");

        //施設予約 施設予約拡張区分別情報(RSV_SIS_KRYRK)の変更
        __addSql(sqlList,
                "alter table RSV_SIS_KRYRK alter column RKR_NAME type varchar(70);");

        //ショートメール ショートメール明細(受信)(SML_JMEIS)の変更
        __addSql(sqlList,
                "alter table SML_JMEIS alter column SMJ_SENDKBN drop not null;");

        //タイムカード タイムカード個人設定(TCD_PRI_CONF)の変更
        __addSql(sqlList,
                "alter table TCD_PRI_CONF alter column TPC_ZSK_STS drop not null;");
        /*--- No.1022 テーブル定義の変更 end ---*/

        return sqlList;
    }

    /**
     * <br>[機  能] 指定されたSQL文をSqlBufferに設定し、実行SQLリストへ追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sqlList 実行SQLリスト
     * @param sql SQL文
     */
    private void __addSql(ArrayList<SqlBuffer> sqlList, String sql) {
        SqlBuffer sqlBuffer = new SqlBuffer();
        sqlBuffer.addSql(sql);
        sqlList.add(sqlBuffer);
    }
}
