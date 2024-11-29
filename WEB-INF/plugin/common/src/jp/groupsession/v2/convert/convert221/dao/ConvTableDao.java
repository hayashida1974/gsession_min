package jp.groupsession.v2.convert.convert221.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilKana;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v2.2.1へコンバートする際に使用する
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

        //新規テーブルのcreate、insertを行う
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
            List<SqlBuffer> sqlList = __createSQL(con);

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
     * @param con コネクション
     * @return List in SqlBuffer
     * @throws SQLException SQL実行例外
     */
    private List<SqlBuffer> __createSQL(Connection con) throws SQLException {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        //ALTER CMN_GROUPM
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" alter table CMN_GROUPM add GRP_ID varchar(8) not null default '99999999'");
        sqlList.add(sql);

        //UPDATE CMN_GROUPM.GRP_ID
        __updateGroupId(con, sqlList);

        //ALTER CMN_USRM
        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM add USR_PSWD_KBN integer not null default 0");
        sqlList.add(sql);

        sql = new SqlBuffer();
        sql.addSql(" alter table CMN_USRM"
                + " add USR_PSWD_EDATE timestamp not null default current_timestamp");
        sqlList.add(sql);

        //ALTER SCH_EXDATA
        sql = new SqlBuffer();
        sql.addSql(" alter table SCH_EXDATA add SCE_DAILY integer default 0");
        sqlList.add(sql);

        //CREATE CMN_PSWD_CONF
        sql = new SqlBuffer();
        sql.addSql(" create table CMN_PSWD_CONF");
        sql.addSql(" (");
        sql.addSql("   PWC_DIGIT         integer         not null,");
        sql.addSql("   PWC_COE           integer         not null,");
        sql.addSql("   PWC_LIMIT_DAY     integer         not null,");
        sql.addSql("   PWC_UIDPSWD       integer         not null,");
        sql.addSql("   PWC_OLDPSWD       integer         not null,");
        sql.addSql("   PWC_AUID          integer         not null,");
        sql.addSql("   PWC_ADATE         timestamp       not null,");
        sql.addSql("   PWC_EUID          integer         not null,");
        sql.addSql("   PWC_EDATE         timestamp       not null");
        sql.addSql(" )");
        sqlList.add(sql);

        //スケジュールチューニング
        sql = new SqlBuffer();
        sql.addSql(" create index SCH_DATA_INDEX1"
                + " on SCH_DATA(SCD_USR_SID,SCD_FR_DATE,SCD_TO_DATE)");
        sqlList.add(sql);

        //ショートメール
        sql = new SqlBuffer();
        sql.addSql(" create table CIR_ADEL");
        sql.addSql(" (");
        sql.addSql("   USR_SID          integer    not null,");
        sql.addSql("   CAD_USR_KBN      integer    not null,");
        sql.addSql("   CAD_DEL_KBN      integer    not null,");
        sql.addSql("   CAD_JDEL_KBN     integer    not null,");
        sql.addSql("   CAD_JDEL_YEAR    integer    not null,");
        sql.addSql("   CAD_JDEL_MONTH   integer    not null,");
        sql.addSql("   CAD_SDEL_KBN     integer    not null,");
        sql.addSql("   CAD_SDEL_YEAR    integer    not null,");
        sql.addSql("   CAD_SDEL_MONTH   integer    not null,");
        sql.addSql("   CAD_DDEL_KBN     integer    not null,");
        sql.addSql("   CAD_DDEL_YEAR    integer    not null,");
        sql.addSql("   CAD_DDEL_MONTH   integer    not null,");
        sql.addSql("   CAD_AUID         integer    not null,");
        sql.addSql("   CAD_ADATE        timestamp  not null,");
        sql.addSql("   CAD_EUID         integer    not null,");
        sql.addSql("   CAD_EDATE        timestamp  not null,");
        sql.addSql("   primary key (USR_SID)");
        sql.addSql(" )");
        sqlList.add(sql);
        //個人設定
        sql = new SqlBuffer();
        sql.addSql(" update SML_USER set SML_MAX_DSP=10, SML_EUID=0, SML_EDATE=sysdate"
                + " where SML_MAX_DSP=0");
        sqlList.add(sql);

        //掲示板
        //ALTER BBS_FOR_INF
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_REPLY integer not null default 0");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table BBS_FOR_INF add BFI_READ integer not null default 1");
        sqlList.add(sql);

        //施設予約
        //ALTER RSV_ADM_CONF
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_ADM_CONF add RAC_HOUR_DIV integer default 10");
        sqlList.add(sql);

        //施設予約チューニング
        sql = new SqlBuffer();
        sql.addSql(" create index RSV_SIS_YRK_INDEX1"
                + " on RSV_SIS_YRK(RSD_SID, RSY_FR_DATE, RSY_TO_DATE)");
        sqlList.add(sql);

        //ALTER RSV_USER
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_USER add RSU_INI_FR_DATE timestamp");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_USER add RSU_INI_TO_DATE timestamp");
        sqlList.add(sql);
        sql = new SqlBuffer();
        sql.addSql(" alter table RSV_USER add RSU_INI_EDIT integer");
        sqlList.add(sql);

        //アドレス帳
        //氏名カナ 姓の先頭一文字」の更新
        __updateAddressSini(sqlList);

        return sqlList;
    }

    /**
     * <br>[機  能] グループマスタのグループID更新のSQLをSQLリストに追加する。
     * <br>[解  説] グループSIDの8桁0埋めのフォーマットでグループIDを設定する。
     * <br>[備  考]
     * @param con コネクション
     * @param sqlList SQLリスト
     * @throws SQLException SQL実行例外
     */
    private void __updateGroupId(Connection con, ArrayList<SqlBuffer> sqlList) throws SQLException {

        CmnGroupmDao grpmDao = new CmnGroupmDao(con);
        List<Integer> grpmList = grpmDao.getGrpSidList();
        String grpId = null;
        SqlBuffer sql = null;
        for (Integer grpSid : grpmList) {
            sql = new SqlBuffer();
            grpId = StringUtil.toDecFormat(grpSid, "00000000");
            sql.addSql(" update");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" set ");
            sql.addSql("   GRP_ID='" + grpId + "'");
            sql.addSql(" where ");
            sql.addSql("   GRP_SID=" + grpSid);
            sqlList.add(sql);
        }
    }

    /**
     * <br>[機  能] アドレス帳情報の「氏名カナ 姓の先頭一文字」更新用SQLをSQLリストに追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sqlList SQLリスト
     */
    private void __updateAddressSini(ArrayList<SqlBuffer> sqlList) {
        String[] siniList = new String[] {
                                        "ガ" , "ギ" , "グ" , "ゲ" , "ゴ" ,
                                        "ザ" , "ジ" , "ズ" , "ゼ" , "ゾ" ,
                                        "ダ" , "ヂ" , "ヅ" , "デ" , "ド" ,
                                        "バ" , "ビ" , "ブ" , "ベ" , "ボ" ,
                                        "パ" , "ピ" , "プ" , "ペ" , "ポ",
                                        "ャ" , "ュ" , "ョ" , "ヴ" ,
                                        "ァ" , "ィ" , "ゥ" , "ェ" , "ォ"
                                       };

        for (String sini : siniList) {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   ADR_ADDRESS");
            sql.addSql(" set");
            sql.addSql("   ADR_SINI = '" + StringUtilKana.getInitKanaChar(sini) + "'");
            sql.addSql(" where");
            sql.addSql("   ADR_SINI = '" + sini + "'");
            sqlList.add(sql);
        }
    }
}