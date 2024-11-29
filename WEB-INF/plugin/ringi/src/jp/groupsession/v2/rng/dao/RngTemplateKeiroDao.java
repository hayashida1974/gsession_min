package jp.groupsession.v2.rng.dao;

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
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RngChannelTemplateModel;
import jp.groupsession.v2.rng.model.RngTemplateKeiroModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;

/**
 * <p>RNG_TEMPLATE_KEIRO Data Access Object
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngTemplateKeiroDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngTemplateKeiroDao.class);
    /**
     *
     * <br>[機  能] 検索パラメータオブジェクトインタフェース
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public interface ISearchParam {

        /**
         * <p>rollType を取得します。
         * @return rollType
         */
        public abstract int getRollType();

        /**
         * <p>rollType をセットします。
         * @param rollType rollType
         */
        public abstract void setRollType(int rollType);

    }
    /**
    *
    * <br>[機  能] 検索パラメータオブジェクトインタフェース
    * <br>[解  説]
    * <br>[備  考]
    *
    * @author JTS
    */
   private interface IPrivateSearchParam extends ISearchParam {

   }
    /**
    *
    * <br>[機  能] 稟議検索用パラメータ
    * <br>[解  説]
    * <br>[備  考]
    *
    * @author JTS
    */
   public static class SearchParamForRTP implements IPrivateSearchParam {
       /** 稟議テンプレートSID */
       private int rtpSid__;
       /** 稟議テンプレートバージョン */
       private int rtpVer__;
       /** 稟議経路タイプ*/
       private int rollType__ = -1;

       /**
        *
        * @param rtpSid 稟議テンプレートSID
        * @param rtpVer 稟議テンプレートバージョン
        */
       public SearchParamForRTP(int rtpSid, int rtpVer) {
           rtpSid__ = rtpSid;
           rtpVer__ = rtpVer;
       }

    /**
     * <p>rtpSid を取得します。
     * @return rtpSid
     */
    public int getRtpSid() {
        return rtpSid__;
    }

    /**
     * <p>rtpSid をセットします。
     * @param rtpSid rtpSid
     */
    public void setRtpSid(int rtpSid) {
        rtpSid__ = rtpSid;
    }

    /**
     * <p>rtpVer を取得します。
     * @return rtpVer
     */
    public int getRtpVer() {
        return rtpVer__;
    }

    /**
     * <p>rtpVer をセットします。
     * @param rtpVer rtpVer
     */
    public void setRtpVer(int rtpVer) {
        rtpVer__ = rtpVer;
    }

    /**
     * <p>rollType を取得します。
     * @return rollType
     */
    public int getRollType() {
        return rollType__;
    }

    /**
     * <p>rollType をセットします。
     * @param rollType rollType
     */
    public void setRollType(int rollType) {
        rollType__ = rollType;
    }
   }
   /**
   *
   * <br>[機  能] 稟議テンプレート用検索パラメータ
   * <br>[解  説]
   * <br>[備  考]
   *
   * @author JTS
   */
  public static class SearchParamForRCT implements IPrivateSearchParam {
      /** 稟議経路テンプレートSID */
      private int rctSid__;
      /** ユーザSID */
      private int usrSid__;
      /** 稟議経路タイプ*/
      private int rollType__ = -1;

      /**
       * コンストラクタ
       * @param rtcSid 稟議経路テンプレートSID
       * @param usrSid ユーザSID
       */
      public SearchParamForRCT(int rtcSid, int usrSid) {
          rctSid__ = rtcSid;
          usrSid__ = usrSid;
      }

    /**
     * <p>rctSid を取得します。
     * @return rctSid
     */
    public int getRctSid() {
        return rctSid__;
    }

    /**
     * <p>rctSid をセットします。
     * @param rctSid rctSid
     */
    public void setRctSid(int rctSid) {
        rctSid__ = rctSid;
    }

    /**
     * <p>usrSid を取得します。
     * @return usrSid
     */
    public int getUsrSid() {
        return usrSid__;
    }

    /**
     * <p>usrSid をセットします。
     * @param usrSid usrSid
     */
    public void setUsrSid(int usrSid) {
        usrSid__ = usrSid;
    }

    /**
     * <p>rollType を取得します。
     * @return rollType
     */
    public int getRollType() {
        return rollType__;
    }

    /**
     * <p>rollType をセットします。
     * @param rollType rollType
     */
    public void setRollType(int rollType) {
        rollType__ = rollType;
    }
  }


    /**
     * <p>Default Constructor
     */
    public RngTemplateKeiroDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngTemplateKeiroDao(Connection con) {
        super(con);
    }

    /**
     * <p>Drop Table
     * @throws SQLException SQL実行例外
     */
    public void dropTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql("drop table RNG_TEMPLATE_KEIRO");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Create Table
     * @throws SQLException SQL実行例外
     */
    public void createTable() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" create table RNG_TEMPLATE_KEIRO (");
            sql.addSql("   RTK_SID NUMBER(10,0) not null,");
            sql.addSql("   RCT_SID NUMBER(10,0) not null,");
            sql.addSql("   USR_SID NUMBER(10,0) not null,");
            sql.addSql("   RTP_SID NUMBER(10,0) not null,");
            sql.addSql("   RTP_VER NUMBER(10,0) not null,");
            sql.addSql("   RTK_SORT NUMBER(10,0) not null,");
            sql.addSql("   RTK_LEVEL NUMBER(10,0) not null,");
            sql.addSql("   RTK_NAME varchar(10) not null,");
            sql.addSql("   RTK_TYPE NUMBER(10,0) not null,");
            sql.addSql("   RTK_ROLL_TYPE NUMBER(10,0) not null,");
            sql.addSql("   RTK_OUTCONDITION NUMBER(10,0) not null,");
            sql.addSql("   RTK_OUTCOND_BORDER NUMBER(10,0) not null,");
            sql.addSql("   RTK_NOUSER NUMBER(10,0) not null,");
            sql.addSql("   RTK_ADDSTEP NUMBER(10,0) not null,");
            sql.addSql("   RTK_KEIRO_SKIP NUMBER(10,0) not null,");
            sql.addSql("   RTK_KEIRO_KOETU NUMBER(10,0) not null,");
            sql.addSql("   RTK_MULTISEL_FLG NUMBER(10,0) not null,");
            sql.addSql("   RTK_BOSSSTEP_CNT NUMBER(10,0) not null,");
            sql.addSql("   RTK_BOSSSTEP_MASTCNT NUMBER(10,0) not null,");
            sql.addSql("   RTK_AUID NUMBER(10,0) not null,");
            sql.addSql("   RTK_ADATE varchar(23) not null,");
            sql.addSql("   RTK_EUID NUMBER(10,0) not null,");
            sql.addSql("   RTK_EDATE varchar(23) not null,");
            sql.addSql("   RTK_JKBN NUMBER(10,0) not null,");
            sql.addSql("   RTK_KOETU_SIZI integer not null,");
            sql.addSql("   RTK_OWNSINGI   integer not null,");
            sql.addSql("   RTK_KEIRO_COMMENT varchar(200),");
            sql.addSql("   primary key (RTK_SID)");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <p>Insert RNG_TEMPLATE_KEIRO Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_KEIRO Data Bindding JavaBean
     * @throws SQLException SQL実行例外
     */
    public void insert(RngTemplateKeiroModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql(" RNG_TEMPLATE_KEIRO(");
            sql.addSql("   RTK_SID,");
            sql.addSql("   RCT_SID,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_VER,");
            sql.addSql("   RTK_SORT,");
            sql.addSql("   RTK_LEVEL,");
            sql.addSql("   RTK_NAME,");
            sql.addSql("   RTK_TYPE,");
            sql.addSql("   RTK_ROLL_TYPE,");
            sql.addSql("   RTK_OUTCONDITION,");
            sql.addSql("   RTK_OUTCOND_BORDER,");
            sql.addSql("   RTK_NOUSER,");
            sql.addSql("   RTK_ADDSTEP,");
            sql.addSql("   RTK_KEIRO_SKIP,");
            sql.addSql("   RTK_KEIRO_KOETU,");
            sql.addSql("   RTK_MULTISEL_FLG,");
            sql.addSql("   RTK_BOSSSTEP_CNT,");
            sql.addSql("   RTK_BOSSSTEP_MASTCNT,");
            sql.addSql("   RTK_AUID,");
            sql.addSql("   RTK_ADATE,");
            sql.addSql("   RTK_EUID,");
            sql.addSql("   RTK_EDATE,");
            sql.addSql("   RTK_JKBN,");
            sql.addSql("   RTK_KOETU_SIZI,");
            sql.addSql("   RTK_OWNSINGI,");
            sql.addSql("   RTK_KEIRO_COMMENT");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?,");
            sql.addSql("   ?");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRtkSid());
            sql.addIntValue(bean.getRctSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRtpSid());
            sql.addIntValue(bean.getRtpVer());
            sql.addIntValue(bean.getRtkSort());
            sql.addIntValue(bean.getRtkLevel());
            sql.addStrValue(bean.getRtkName());
            sql.addIntValue(bean.getRtkType());
            sql.addIntValue(bean.getRtkRollType());
            sql.addIntValue(bean.getRtkOutcondition());
            sql.addIntValue(bean.getRtkOutcondBorder());
            sql.addIntValue(bean.getRtkNouser());
            sql.addIntValue(bean.getRtkAddstep());
            sql.addIntValue(bean.getRtkKeiroSkip());
            sql.addIntValue(bean.getRtkKeiroKoetu());
            sql.addIntValue(bean.getRtkMultiselFlg());
            sql.addIntValue(bean.getRtkBossstepCnt());
            sql.addIntValue(bean.getRtkBossstepMastcnt());
            sql.addIntValue(bean.getRtkAuid());
            sql.addDateValue(bean.getRtkAdate());
            sql.addIntValue(bean.getRtkEuid());
            sql.addDateValue(bean.getRtkEdate());
            sql.addIntValue(bean.getRtkJkbn());
            sql.addIntValue(bean.getRtkKoetuSizi());
            sql.addIntValue(bean.getRtkOwnsingi());
            sql.addStrValue(bean.getRtkKeiroComment());
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
     * <p>Update RNG_TEMPLATE_KEIRO Data Bindding JavaBean
     * @param bean RNG_TEMPLATE_KEIRO Data Bindding JavaBean
     * @return 更新件数
     * @throws SQLException SQL実行例外
     */
    public int update(RngTemplateKeiroModel bean) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql(" set ");
            sql.addSql("   RCT_SID=?,");
            sql.addSql("   USR_SID=?,");
            sql.addSql("   RTP_SID=?,");
            sql.addSql("   RTP_VER=?,");
            sql.addSql("   RTK_SORT=?,");
            sql.addSql("   RTK_LEVEL=?,");
            sql.addSql("   RTK_NAME=?,");
            sql.addSql("   RTK_TYPE=?,");
            sql.addSql("   RTK_ROLL_TYPE=?,");
            sql.addSql("   RTK_OUTCONDITION=?,");
            sql.addSql("   RTK_OUTCOND_BORDER=?,");
            sql.addSql("   RTK_NOUSER=?,");
            sql.addSql("   RTK_ADDSTEP=?,");
            sql.addSql("   RTK_KEIRO_SKIP=?,");
            sql.addSql("   RTK_KEIRO_KOETU=?,");
            sql.addSql("   RTK_MULTISEL_FLG=?,");
            sql.addSql("   RTK_BOSSSTEP_CNT=?,");
            sql.addSql("   RTK_BOSSSTEP_MASTCNT=?,");
            sql.addSql("   RTK_AUID=?,");
            sql.addSql("   RTK_ADATE=?,");
            sql.addSql("   RTK_EUID=?,");
            sql.addSql("   RTK_EDATE=?,");
            sql.addSql("   RTK_JKBN=?,");
            sql.addSql("   RTK_KOETU_SIZI=?,");
            sql.addSql("   RTK_OWNSINGI=?,");
            sql.addSql("   RTK_KEIRO_COMMENT=?");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(bean.getRctSid());
            sql.addIntValue(bean.getUsrSid());
            sql.addIntValue(bean.getRtpSid());
            sql.addIntValue(bean.getRtpVer());
            sql.addIntValue(bean.getRtkSort());
            sql.addIntValue(bean.getRtkLevel());
            sql.addStrValue(bean.getRtkName());
            sql.addIntValue(bean.getRtkType());
            sql.addIntValue(bean.getRtkRollType());
            sql.addIntValue(bean.getRtkOutcondition());
            sql.addIntValue(bean.getRtkOutcondBorder());
            sql.addIntValue(bean.getRtkNouser());
            sql.addIntValue(bean.getRtkAddstep());
            sql.addIntValue(bean.getRtkKeiroSkip());
            sql.addIntValue(bean.getRtkKeiroKoetu());
            sql.addIntValue(bean.getRtkMultiselFlg());
            sql.addIntValue(bean.getRtkBossstepCnt());
            sql.addIntValue(bean.getRtkBossstepMastcnt());
            sql.addIntValue(bean.getRtkAuid());
            sql.addDateValue(bean.getRtkAdate());
            sql.addIntValue(bean.getRtkEuid());
            sql.addDateValue(bean.getRtkEdate());
            sql.addIntValue(bean.getRtkJkbn());
            sql.addIntValue(bean.getRtkKoetuSizi());
            sql.addIntValue(bean.getRtkOwnsingi());
            sql.addStrValue(bean.getRtkKeiroComment());
            //where
            sql.addIntValue(bean.getRtkSid());

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
     * <p>Select RNG_TEMPLATE_KEIRO All Data
     * @return List in RNG_TEMPLATE_KEIROModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateKeiroModel> select() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplateKeiroModel> ret = new ArrayList<>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            writeSelectField(sql, null);
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_KEIRO");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateKeiroFromRs(rs));
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
     * <p>どの経路および経路テンプレートでも使用されていない経路テンプレートステップを取得
     *        ただし物理削除対象の経路テンプレートに含まれているものを対象とする
     * @return 経路テンプレートステップSIDリスト
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateKeiroModel> selectTargetDel() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplateKeiroModel> ret = new ArrayList<RngTemplateKeiroModel>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            writeSelectField(sql, "RNG_TEMPLATE_KEIRO");
            sql.addSql(" from");
            sql.addSql("    RNG_TEMPLATE_KEIRO");
            sql.addSql("    left join ( ");
            sql.addSql("     select");
            sql.addSql("        RCT_SID");
            sql.addSql("     from");
            sql.addSql("        RNG_CHANNEL_TEMPLATE");
            sql.addSql("    ) LIVE_RCT");
            sql.addSql("    on RNG_TEMPLATE_KEIRO.RCT_SID = LIVE_RCT.RCT_SID");
            sql.addSql("    left join ( ");
            sql.addSql("     select");
            sql.addSql("        RTP_SID,");
            sql.addSql("        RTP_VER");
            sql.addSql("     from");
            sql.addSql("        RNG_TEMPLATE");
            sql.addSql("    ) LIVE_RTP");
            sql.addSql("    on RNG_TEMPLATE_KEIRO.RTP_SID = LIVE_RTP.RTP_SID");
            sql.addSql("      and RNG_TEMPLATE_KEIRO.RTP_VER = LIVE_RTP.RTP_VER");
            sql.addSql("    left join ( ");
            sql.addSql("     select");
            sql.addSql("        RTK_SID,");
            sql.addSql("        RKS_ADDSTEP");
            sql.addSql("     from");
            sql.addSql("        RNG_KEIRO_STEP");
            sql.addSql("     where");
            sql.addSql("        RTK_SID != -1 and RKS_ADDSTEP != 0");
            sql.addSql("    ) LIVE_RKS");
            sql.addSql("    on RNG_TEMPLATE_KEIRO.RTK_SID = LIVE_RKS.RTK_SID");
            sql.addSql("      or RNG_TEMPLATE_KEIRO.RTK_SID = LIVE_RKS.RKS_ADDSTEP");
            sql.addSql("    left join ( ");
            sql.addSql("     select");
            sql.addSql("        RTK_SID");
            sql.addSql("     from");
            sql.addSql("        RNG_COPY_KEIRO_STEP");
            sql.addSql("     where");
            sql.addSql("        RTK_SID != -1");
            sql.addSql("    ) LIVE_RCK");
            sql.addSql("    on RNG_TEMPLATE_KEIRO.RTK_SID = LIVE_RCK.RTK_SID");
            sql.addSql(" where");
            sql.addSql("  RTK_JKBN=9");
            sql.addSql(" and");
            sql.addSql("    LIVE_RCT.RCT_SID is null");
            sql.addSql(" and");
            sql.addSql("    LIVE_RTP.RTP_SID is null");
            sql.addSql(" and");
            sql.addSql("    LIVE_RKS.RTK_SID is null");
            sql.addSql(" and");
            sql.addSql("    LIVE_RCK.RTK_SID is null");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
             rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateKeiroFromRs(rs));
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
     *
     * <br>[機  能] RNG_TEMPLATE_KEIROの全フィールド取得用のクエリのフィールド取得部を書き込む
     * <br>[解  説]
     * <br>[備  考]
     * @param sql 書き込み先SqlBuffer
     * @param asTableName RNG_TEMPLATE_KEIROにつけられた別名
     */
    public void writeSelectField(SqlBuffer sql, String asTableName) {
        String[] fieldNames = new String[] {
                "RTK_SID",
                "RCT_SID",
                "USR_SID",
                "RTP_SID",
                "RTP_VER",
                "RTK_SORT",
                "RTK_LEVEL",
                "RTK_NAME",
                "RTK_TYPE",
                "RTK_ROLL_TYPE",
                "RTK_OUTCONDITION",
                "RTK_OUTCOND_BORDER",
                "RTK_NOUSER",
                "RTK_ADDSTEP",
                "RTK_KEIRO_SKIP",
                "RTK_KEIRO_KOETU",
                "RTK_MULTISEL_FLG",
                "RTK_BOSSSTEP_CNT",
                "RTK_BOSSSTEP_MASTCNT",
                "RTK_AUID",
                "RTK_ADATE",
                "RTK_EUID",
                "RTK_EDATE",
                "RTK_JKBN",
                "RTK_KOETU_SIZI",
                "RTK_OWNSINGI",
                "RTK_KEIRO_COMMENT"
        };
        boolean isFirst = true;
        boolean isUseAsTableName = false;
        if (asTableName != null && asTableName.length() > 0) {
            isUseAsTableName = true;
        }
        for (String fieldName : fieldNames) {
            if (isFirst) {
                isFirst = false;
            } else {
                sql.addSql(",");
            }
            sql.addSql("   ");
            if (isUseAsTableName) {
                sql.addSql(asTableName);
                sql.addSql(".");
                sql.addSql(fieldName);
                sql.addSql(" as ");
            }
            sql.addSql(fieldName);
        }
    }
    /**
    *
    * <br>[機  能] SIDをselectするクエリを書き込む 稟議経路テンプレート用
    * <br>[解  説]
    * <br>[備  考]
    * @param parameterObject 検索オブジェクト
    * @param sql 書き込み先SqlBuffer
    */
   public void writeSelect(SqlBuffer sql, ISearchParam parameterObject) {
       writeSelect(sql, parameterObject, RngConst.JKBN_ALL);
   }
    /**
     *
     * <br>[機  能] SIDをselectするクエリを書き込む 稟議経路テンプレート用
     * <br>[解  説]
     * <br>[備  考]
     * @param parameterObject 検索オブジェクト
     * @param jkbn 論理削除区分
     * @param sql 書き込み先SqlBuffer
     */
    public void writeSelect(SqlBuffer sql, ISearchParam parameterObject, int jkbn) {
        sql.addSql(" select RTK_SID");
        sql.addSql(" from");
        sql.addSql("   RNG_TEMPLATE_KEIRO");
        __writeWhere(sql, parameterObject, jkbn, "where");
    }

    /**
     * 条件文
     * @param sql SqlBuffer
     * @param parameterObject 検索パラメータオブジェクト
     * @param jkbn 論理削除区分
     * @param startWord 条件文の開始
     * */
    private void __writeWhere(SqlBuffer sql,
            ISearchParam parameterObject,
            int jkbn, String startWord) {

        sql.addSql(" ");
        sql.addSql(startWord);
        sql.addSql(" ");
        if (parameterObject instanceof SearchParamForRCT) {
            SearchParamForRCT rctParam = (SearchParamForRCT) parameterObject;
            sql.addSql("   RCT_SID=? and ");
            sql.addSql("   USR_SID=?  ");
            sql.addIntValue(rctParam.rctSid__);
            sql.addIntValue(rctParam.usrSid__);
        }
        if (parameterObject instanceof SearchParamForRTP) {

            SearchParamForRTP rtpParam = (SearchParamForRTP) parameterObject;
            sql.addSql("   RTP_SID=? and ");
            sql.addSql("   RTP_VER=? ");
            sql.addIntValue(rtpParam.rtpSid__);
            sql.addIntValue(rtpParam.rtpVer__);
        }
        if (jkbn != RngConst.JKBN_ALL) {
            sql.addSql("   and RTK_JKBN=?  ");
            sql.addIntValue(jkbn);
        }
        if (parameterObject.getRollType() != -1) {
            sql.addSql("   and RTK_ROLL_TYPE=?  ");
            sql.addIntValue(parameterObject.getRollType());
        }
    }
    /**
     * <p>Select RNG_TEMPLATE_KEIRO
     * @param rtkSid RTK_SID
     * @return RNG_TEMPLATE_KEIROModel
     * @throws SQLException SQL実行例外
     */
    public RngTemplateKeiroModel select(int rtkSid) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        RngTemplateKeiroModel ret = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            writeSelectField(sql, null);
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtkSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = __getRngTemplateKeiroFromRs(rs);
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
     * <p>テンプレートにひもづく経路リストを取得する
     * @param searchParam 稟議テンプレート検索オブジェクト
     * @return RNG_TEMPLATE_KEIROModel
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateKeiroModel> templateRtkList(
            SearchParamForRTP searchParam
            ) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<RngTemplateKeiroModel> ret;
        con = getCon();
        ret = select(searchParam, RngConst.JKBN_ALL);
        if (!ret.isEmpty()) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            writeSelectField(sql, "RNG_TEMPLATE_KEIRO");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE left join ");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql("     on (RNG_TEMPLATE.RCT_SID > 0");
            sql.addSql("       and RNG_TEMPLATE.RCT_SID = RNG_TEMPLATE_KEIRO.RCT_SID");
            sql.addSql("       and RNG_TEMPLATE.RCT_USR_SID = RNG_TEMPLATE_KEIRO.USR_SID");
            sql.addSql("       and RNG_TEMPLATE_KEIRO.RTK_JKBN=0)");
            sql.addSql("     or (RNG_TEMPLATE.RTP_SID=RNG_TEMPLATE_KEIRO.RTP_SID");
            sql.addSql("       and RNG_TEMPLATE.RTP_VER=RNG_TEMPLATE_KEIRO.RTP_VER)");
            if (searchParam.getRollType() != -1) {
                sql.addSql("   and RNG_TEMPLATE_KEIRO.RTK_ROLL_TYPE=?  ");
                sql.addIntValue(searchParam.getRollType());
            }

            sql.addSql(" where ");
            sql.addSql("   RNG_TEMPLATE.RTP_SID=? and ");
            sql.addSql("   RNG_TEMPLATE.RTP_VER=? ");
            sql.addIntValue(searchParam.rtpSid__);
            sql.addIntValue(searchParam.rtpVer__);


            sql.addSql(" order by RNG_TEMPLATE_KEIRO.RTK_ROLL_TYPE, RNG_TEMPLATE_KEIRO.RTK_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RngTemplateKeiroModel mdl = __getRngTemplateKeiroFromRs(rs);
                ret.add(mdl);
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
     * <p>Delete RNG_TEMPLATE_KEIRO
     * @param rtkSid RTK_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int delete(int rtkSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(rtkSid);

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
     * <p>Delete RNG_TEMPLATE_KEIRO
     * @param rtkList  List in RTK_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteNoUseStep(List<RngTemplateKeiroModel> rtkList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql(" where ");
            sql.addSql("   RTK_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());

            for (RngTemplateKeiroModel rtkMdl : rtkList) {
                sql.addIntValue(rtkMdl.getRtkSid());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count += pstmt.executeUpdate();
                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>Delete RNG_TEMPLATE_KEIRO
     * @param rctList  List in RCT_SID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteNoUseTemp(List<RngChannelTemplateModel> rctList) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql(" where ");
            sql.addSql("   RCT_SID=?");
            pstmt = con.prepareStatement(sql.toSqlString());

            for (RngChannelTemplateModel rctMdl : rctList) {
                sql.addIntValue(rctMdl.getRctSid());
                log__.info(sql.toLogString());
                sql.setParameter(pstmt);
                count += pstmt.executeUpdate();
                sql.clearValue();
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return count;
    }

    /**
     * <p>論理Delete RNG_TEMPLATE_KEIRO 稟議テンプレート用
     * @param rtpSid テンプレートSID
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteRonri(int rtpSid) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql(" set ");
            sql.addSql("   RTK_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(RngConst.JKBN_DELETE);
            sql.addIntValue(rtpSid);

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
     * <p>Delete RNG_TEMPLATE_KEIRO 稟議テンプレート用
     * @param parameterObject TODO
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteRonri(SearchParamForRTP parameterObject) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql(" set ");
            sql.addSql("   RTK_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   RTP_SID=? and ");
            sql.addSql("   RTP_VER=?  ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(RngConst.JKBN_DELETE);
            sql.addIntValue(parameterObject.rtpSid__);
            sql.addIntValue(parameterObject.rtpVer__);

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
     * <p>Delete RNG_TEMPLATE_KEIRO 稟議経路テンプレート用
     * @param parameterObject S
     * @throws SQLException SQL実行例外
     * @return 件数
     */
    public int deleteRonri(SearchParamForRCT parameterObject) throws SQLException {

        PreparedStatement pstmt = null;
        int count = 0;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql(" set ");
            sql.addSql("   RTK_JKBN=?");
            sql.addSql(" where ");
            sql.addSql("   RCT_SID=? and ");
            sql.addSql("   USR_SID=?  ");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(RngConst.JKBN_DELETE);
            sql.addIntValue(parameterObject.rctSid__);
            sql.addIntValue(parameterObject.usrSid__);

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
     * <p>Select RNG_TEMPLATE_KEIRO All Data
     * @return List in RNG_TEMPLATE_KEIROModel
     * @param prop 検索値
     * @param jkbn 削除区分
     * @throws SQLException SQL実行例外
     */
    public List<RngTemplateKeiroModel> select(ISearchParam prop,
            int jkbn) throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<RngTemplateKeiroModel> ret = new ArrayList<>();
        con = getCon();
        if (!(prop instanceof IPrivateSearchParam)) {
            return ret;
        }
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            writeSelectField(sql, null);
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            __writeWhere(sql, prop, jkbn, "where");
            sql.addSql(" order by RTK_ROLL_TYPE,RTK_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);

            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(__getRngTemplateKeiroFromRs(rs));
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
     * <br>[機  能] 指定した経路テンプレートステップ情報の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpList 稟議テンプレート情報
     * @return 件数
     * @throws SQLException SQL実行時例外
     */
    public long getTemplateKeiroCount(List<RngTemplateModel> rtpList) throws SQLException {

        long dataSize = 0;

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   sum(octet_length(RTK_KEIRO_COMMENT)) as COMMENT_SIZE");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_KEIRO");
            sql.addSql(" where");
            sql.addSql("   RTP_SID=?");
            sql.addSql(" and");
            sql.addSql("   RTP_VER=?");
            pstmt = con.prepareStatement(sql.toSqlString());

            for (RngTemplateModel rtpMdl : rtpList) {
                log__.info(sql.toLogString());
                sql.addIntValue(rtpMdl.getRtpSid());
                sql.addIntValue(rtpMdl.getRtpVer());
                sql.setParameter(pstmt);
                log__.info(sql.toLogString());
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    dataSize += rs.getLong("COMMENT_SIZE");
                }
                JDBCUtil.closeResultSet(rs);
                sql.clearValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return dataSize;
    }

    /**
     * <p>Create RNG_TEMPLATE_KEIRO Data Bindding JavaBean From ResultSet
     * @param rs ResultSet
     * @return created RngTemplateKeiroModel
     * @throws SQLException SQL実行例外
     */
    private RngTemplateKeiroModel __getRngTemplateKeiroFromRs(ResultSet rs) throws SQLException {
        RngTemplateKeiroModel bean = new RngTemplateKeiroModel();
        bean.setRtkSid(rs.getInt("RTK_SID"));
        bean.setRctSid(rs.getInt("RCT_SID"));
        bean.setUsrSid(rs.getInt("USR_SID"));
        bean.setRtpSid(rs.getInt("RTP_SID"));
        bean.setRtpVer(rs.getInt("RTP_VER"));
        bean.setRtkSort(rs.getInt("RTK_SORT"));
        bean.setRtkLevel(rs.getInt("RTK_LEVEL"));
        bean.setRtkName(rs.getString("RTK_NAME"));
        bean.setRtkType(rs.getInt("RTK_TYPE"));
        bean.setRtkRollType(rs.getInt("RTK_ROLL_TYPE"));
        bean.setRtkOutcondition(rs.getInt("RTK_OUTCONDITION"));
        bean.setRtkOutcondBorder(rs.getInt("RTK_OUTCOND_BORDER"));
        bean.setRtkNouser(rs.getInt("RTK_NOUSER"));
        bean.setRtkAddstep(rs.getInt("RTK_ADDSTEP"));
        bean.setRtkKeiroSkip(rs.getInt("RTK_KEIRO_SKIP"));
        bean.setRtkKeiroKoetu(rs.getInt("RTK_KEIRO_KOETU"));
        bean.setRtkMultiselFlg(rs.getInt("RTK_MULTISEL_FLG"));
        bean.setRtkBossstepCnt(rs.getInt("RTK_BOSSSTEP_CNT"));
        bean.setRtkBossstepMastcnt(rs.getInt("RTK_BOSSSTEP_MASTCNT"));
        bean.setRtkAuid(rs.getInt("RTK_AUID"));
        bean.setRtkAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RTK_ADATE")));
        bean.setRtkEuid(rs.getInt("RTK_EUID"));
        bean.setRtkEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RTK_EDATE")));
        bean.setRtkJkbn(rs.getInt("RTK_JKBN"));
        bean.setRtkKoetuSizi(rs.getInt("RTK_KOETU_SIZI"));
        bean.setRtkOwnsingi(rs.getInt("RTK_OWNSINGI"));
        bean.setRtkKeiroComment(rs.getString("RTK_KEIRO_COMMENT"));
        return bean;
    }
}
