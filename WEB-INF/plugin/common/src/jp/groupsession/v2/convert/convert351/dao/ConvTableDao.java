package jp.groupsession.v2.convert.convert351.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v3.5.1へコンバートする際に使用する
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

        //ショートメール転送設定のPASSのフィールドの定義変更
        sql = new SqlBuffer();
        sql.addSql(" alter table SML_ADMIN alter column SMA_SMTP_PASS type varchar(140);");
        sqlList.add(sql);

        //ショートメールインデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index SML_JMEIS_INDEX1 on SML_JMEIS(SMJ_SID);");
        sqlList.add(sql);

        //スケジュールインデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index SCH_DATA_INDEX2 on SCH_DATA(SCE_SID);");
        sqlList.add(sql);

        //ログイン履歴インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index CMN_LOGIN_HISTORY1 on CMN_LOGIN_HISTORY(CLH_EDATE);");
        sqlList.add(sql);

        //スレッド情報インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index BBS_THRE_INF_INDEX1 on BBS_THRE_INF(BTI_LIMIT_DATE);");
        sqlList.add(sql);

        //ディレクトリ情報インデックス追加
        sql = new SqlBuffer();
        sql.addSql(" create index FILE_DIRECTORY_INDEX1 on FILE_DIRECTORY(FCB_SID);");
        sqlList.add(sql);

        return sqlList;
    }

    /**
     * <br>[機  能] WEBメール アカウント情報のコンバート
     * <br>[解  説]
     * <br>[備  考]
     * @param gscontext context
     * @param con Connection
     * @throws SQLException WEBメール アカウント情報のコンバートに失敗
     */
    public void convertWebmailAccount(GSContext gscontext, Connection con)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //「下書き」フォルダを「草稿」フォルダへ変更(名称の変更)
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update WML_DIRECTORY");
            sql.addSql(" set WDR_NAME = ?");
            sql.addSql(" where WDR_TYPE = ?");

            MessageResources msgResources
                = (MessageResources) gscontext.get(GSContext.MSG_RESOURCE);
            GsMessage gsMsg = new GsMessage();
            sql.addStrValue(gsMsg.getMessage(msgResources, "cmn.draft"));
            sql.addIntValue(GSConstWebmail.DIR_TYPE_DRAFT);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();
            JDBCUtil.closePreparedStatement(pstmt);


            //「未送信」フォルダ内のメールを「草稿」フォルダへ移動
            sql = new SqlBuffer();
            sql.addSql(" select WAC_SID, WDR_SID from WML_DIRECTORY");
            sql.addSql(" where WDR_TYPE = ?");
            sql.addSql(" and WDR_VIEW = ?");
            sql.addIntValue(GSConstWebmail.DIR_TYPE_NOSEND);
            sql.addIntValue(GSConstWebmail.DSP_VIEW_OK);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            Map<Integer, Long> wacMap = new HashMap<Integer, Long>();
            while (rs.next()) {
                int wacSid = rs.getInt("WAC_SID");
                long nosendDirSid = rs.getLong("WDR_SID");

                wacMap.put(wacSid, nosendDirSid);
            }

            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);

            if (!wacMap.isEmpty()) {
                sql = new SqlBuffer();
                sql.addSql(" update WML_MAILDATA");
                sql.addSql(" set WDR_SID = ?");
                sql.addSql(" where WDR_SID = ?");
                pstmt = con.prepareStatement(sql.toSqlString());

                Iterator<Integer> wacIter = wacMap.keySet().iterator();
                while (wacIter.hasNext()) {

                    int wacSid = wacIter.next();
                    long draftDirSid = __getDraftDirSid(con, wacSid);
                    if (draftDirSid <= 0) {
                        throw new SQLException("「草稿」ディレクトリの取得に失敗しました。");
                    }

                    long nosendDirSid = wacMap.get(wacSid);

                    sql.addLongValue(draftDirSid);
                    sql.addLongValue(nosendDirSid);
                    log__.info(sql.toLogString());

                    sql.setParameter(pstmt);
                    pstmt.executeUpdate();
                    sql.clearValue();
                }
            }

            //「未送信」フォルダを非表示に設定する
            sql = new SqlBuffer();
            sql.addSql(" update WML_DIRECTORY");
            sql.addSql(" set WDR_VIEW = ?");
            sql.addSql(" where WDR_TYPE = ?");
            sql.addIntValue(GSConstWebmail.DSP_VIEW_NO);
            sql.addIntValue(GSConstWebmail.DIR_TYPE_NOSEND);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }
    }


    /**
     * <br>[機  能] WEBメール 指定したアカウントの「草稿」ディレクトリSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param wacSid アカウントSID
     * @return 「草稿」ディレクトリSID
     * @throws SQLException 「草稿」ディレクトリSIDの取得に失敗
     */
    private long __getDraftDirSid(Connection con, int wacSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long wdrSid = 0;

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select WDR_SID from WML_DIRECTORY");
            sql.addSql(" where WAC_SID = ?");
            sql.addSql(" and WDR_TYPE = ?");
            sql.addIntValue(wacSid);
            sql.addIntValue(GSConstWebmail.DIR_TYPE_DRAFT);

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                wdrSid = rs.getLong("WDR_SID");
            }

        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closePreparedStatement(pstmt);
        }

        return wdrSid;
    }
}