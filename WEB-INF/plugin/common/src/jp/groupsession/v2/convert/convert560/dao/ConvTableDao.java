package jp.groupsession.v2.convert.convert560.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.base.SaibanModel;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v5.6.0へコンバートする際に使用する
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

        __insertStampSaibanData();

        __deleteChtTempData();

        __insertGroupTempData();
        __insertUserTempData();
        __deleteChtBinColumn();
        __insertDefaultStampData();
        __addChtStampSidColumn();
        __addGroupDataHaveURL();
        __addUserDataHaveURL();
        __deleteChtGroupUser();
        __deleteChtGroupGroup();
        __deleteUserChtFavorite();
        __addGroupFavorite();
        __addUserFavorite(saiban);
        __dropFavorite();

        log__.debug("-- DBコンバート終了 --");
    }
    /**
     * <br>[機  能] チャットグループユーザ情報から削除済みユーザのお気に入り情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __deleteUserChtFavorite() throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_FAVORITE");
            sql.addSql(" where ");
            sql.addSql("   CHF_UID in (");
            sql.addSql("     select");
            sql.addSql("       USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_USRM");
            sql.addSql("     where");
            sql.addSql("       USR_JKBN=?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(GSConst.JTKBN_DELETE);
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
     * <br>[機  能] グループ投稿情報からURL区分を登録
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __addGroupDataHaveURL() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        Pattern urlPattern = Pattern.compile("((\\w+)://){1}[\\w\\.\\-/:\\#"
        + "\\!\\?\\=\\&\\;\\%\\~\\+\\$\\,\\*\\'\\(\\)\\@]+");

        try {
            int allCnt = 0;
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CGD_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                allCnt = rs.getInt("CNT");
            }
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            int offset = 0;
            while (offset < allCnt) {
                int limit = 1000;
                if (limit > allCnt) {
                    limit = allCnt;
                }
                Set<Long> updSidSet = new HashSet<>();

                sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   CGD_SID,");
                sql.addSql("   CGD_TEXT");
                sql.addSql(" from");
                sql.addSql("   CHT_GROUP_DATA");
                sql.addSql("   order by CGD_SID");

                sql.setPagingValue(offset, limit);

                pstmt = con.prepareStatement(sql.toSqlString());

                log__.info(sql.toLogString());

                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String msg = rs.getString("CGD_TEXT");
                    if (msg == null) {
                        continue;
                    }
                    Matcher matcher = urlPattern.matcher(msg);
                    if (matcher.find()) {
                        updSidSet.add(rs.getLong("CGD_SID"));
                    }
                }
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);

                if (updSidSet.size() > 0) {
                    sql = new SqlBuffer();
                    sql.addSql(" update CHT_GROUP_DATA set CGD_URL_FLG=1");
                    sql.addSql(" where CGD_SID in (");
                    sql.addSql(
                        updSidSet.stream()
                            .map(sid -> {
                                 return String.valueOf(sid);
                                })
                            .collect(
                                Collectors.joining(",")
                                )
                    );
                    sql.addSql("   )");

                    log__.info(sql.toLogString());
                    pstmt = con.prepareStatement(sql.toSqlString());
                    sql.setParameter(pstmt);
                    pstmt.executeUpdate();
                    JDBCUtil.closeResultSet(rs);
                    JDBCUtil.closeStatement(pstmt);
                }

                offset = offset + limit;
            }


        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] ユーザ投稿情報からURL区分を登録
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __addUserDataHaveURL() throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();
        Pattern urlPattern = Pattern.compile("((\\w+)://){1}[\\w\\.\\-/:\\#"
        + "\\!\\?\\=\\&\\;\\%\\~\\+\\$\\,\\*\\'\\(\\)\\@]+");

        try {
            int allCnt = 0;
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   count(CUD_SID) as CNT");
            sql.addSql(" from");
            sql.addSql("   CHT_USER_DATA");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                allCnt = rs.getInt("CNT");
            }
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
            int offset = 0;
            while (offset < allCnt) {
                int limit = 1000;
                if (limit > allCnt) {
                    limit = allCnt;
                }
                Set<Long> updSidSet = new HashSet<>();

                sql = new SqlBuffer();
                sql.addSql(" select");
                sql.addSql("   CUD_SID,");
                sql.addSql("   CUD_TEXT");
                sql.addSql(" from");
                sql.addSql("   CHT_USER_DATA");
                sql.addSql("   order by CUD_SID");

                sql.setPagingValue(offset, limit);

                pstmt = con.prepareStatement(sql.toSqlString());

                log__.info(sql.toLogString());

                sql.setParameter(pstmt);
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    String msg = rs.getString("CUD_TEXT");
                    if (msg == null) {
                        continue;
                    }
                    Matcher matcher = urlPattern.matcher(msg);
                    if (matcher.find()) {
                        updSidSet.add(rs.getLong("CUD_SID"));
                    }
                }
                JDBCUtil.closeResultSet(rs);
                JDBCUtil.closeStatement(pstmt);

                if (updSidSet.size() > 0) {
                    sql = new SqlBuffer();
                    sql.addSql(" update CHT_USER_DATA set CUD_URL_FLG=1");
                    sql.addSql(" where CUD_SID in (");
                    sql.addSql(
                        updSidSet.stream()
                            .map(sid -> {
                                return String.valueOf(sid);
                                })
                            .collect(
                                Collectors.joining(",")
                                )
                    );
                    sql.addSql("   )");

                    log__.info(sql.toLogString());
                    pstmt = con.prepareStatement(sql.toSqlString());
                    sql.setParameter(pstmt);
                    pstmt.executeUpdate();
                    JDBCUtil.closeResultSet(rs);
                    JDBCUtil.closeStatement(pstmt);
                }
                offset = offset + limit;
            }


        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] グループチャットのお気に入り区分を新規作成したテーブルに移行する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __addGroupFavorite() throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CHT_GROUP_UCONF (");
            sql.addSql("   USR_SID, CGI_SID, CGUC_FAVORITE_KBN, CGUC_MUTE_KBN)");
            sql.addSql("select distinct");
            sql.addSql("   CHF_UID,");
            sql.addSql("   CHF_SID,");
            sql.addSql("   1,");
            sql.addSql("   0");
            sql.addSql(" from");
            sql.addSql("   CHT_FAVORITE");
            sql.addSql(" where");
            sql.addSql("   CHF_CHAT_KBN = 2");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] ユーザチャットのお気に入り情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return ChtUserFavoriteModelリスト
     * @throws SQLException SQL実行時例外
     */
    private List<ChtUserFavoriteModel> __getUserFavoritePair() throws SQLException {

        List<ChtUserFavoriteModel> ret = new ArrayList<ChtUserFavoriteModel>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select ");
            sql.addSql("   distinct");
            sql.addSql("   CHT_FAVORITE.CHF_UID as CHF_UID,");
            sql.addSql("   COALESCE(CHT_USER_PAIR.CUP_SID, -1) as CUP_SID,");
            sql.addSql("   CHT_FAVORITE.CHF_SID as CHF_SID");
            sql.addSql(" from ");
            sql.addSql("   CHT_FAVORITE");
            sql.addSql(" left join ");
            sql.addSql("   CHT_USER_PAIR");
            sql.addSql(" on ");
            sql.addSql("   (");
            sql.addSql("     CHT_USER_PAIR.CUP_UID_F = CHT_FAVORITE.CHF_UID");
            sql.addSql("     and");
            sql.addSql("     CHT_USER_PAIR.CUP_UID_S = CHT_FAVORITE.CHF_SID");
            sql.addSql("   )");
            sql.addSql(" or ");
            sql.addSql("   (");
            sql.addSql("     CHT_USER_PAIR.CUP_UID_F = CHT_FAVORITE.CHF_SID");
            sql.addSql("     and");
            sql.addSql("     CHT_USER_PAIR.CUP_UID_S = CHT_FAVORITE.CHF_UID");
            sql.addSql("   )");
            sql.addSql(" where ");
            sql.addSql("   CHT_FAVORITE.CHF_CHAT_KBN = 1");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            ChtUserFavoriteModel cufMdl = null;
            while (rs.next()) {
                cufMdl = new ChtUserFavoriteModel();
                cufMdl.setChfUid(rs.getInt("CHF_UID"));
                cufMdl.setChfSid(rs.getInt("CHF_SID"));
                cufMdl.setCupSid(rs.getInt("CUP_SID"));
                ret.add(cufMdl);
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
     * <br>[機  能] お気に入りに登録されているが、ペア情報が作成されていない場合にペア情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番コントローラ
     * @param userFavoriteList ユーザチャットを対象にしたお気に入り情報
     * @throws SQLException SQL実行時例外
     */
    private void __addUserPair(MlCountMtController saiban,
        List<ChtUserFavoriteModel> userFavoriteList) throws SQLException {

        if (userFavoriteList == null || userFavoriteList.isEmpty()) {
            return;
        }

        List<ChtUserFavoriteModel> addPairList = new ArrayList<ChtUserFavoriteModel>();
        Map<List<Integer>, Integer> pairSidMap = new HashMap<List<Integer>, Integer>();
        List<Integer> userSidList = new ArrayList<Integer>();
        int cupSid;
        for (ChtUserFavoriteModel mdl : userFavoriteList) {
            if (mdl.getCupSid() != -1) {
                continue;
            }
            //重複してペアが登録されることが無いように、1つ目のユーザSIDの値<=2つめのユーザSIDの値となるようにする
            userSidList = new ArrayList<Integer>();
            if (mdl.getChfUid() < mdl.getChfSid()) {
                userSidList.add(mdl.getChfUid());
                userSidList.add(mdl.getChfSid());
            } else {
                userSidList.add(mdl.getChfSid());
                userSidList.add(mdl.getChfUid());
            }

            //ペアSIDが設定されていないが既にこの処理の中でペアSIDを取得済みの場合は、そのSIDを設定する
            if (pairSidMap.get(userSidList) != null) {
                mdl.setCupSid(pairSidMap.get(userSidList));
                continue;
            }

            cupSid = (int) saiban.getSaibanNumber(
                SaibanModel.SBNSID_CHAT, SaibanModel.SBNSID_SUB_PAIR, 0);
            mdl.setCupSid(cupSid);
            pairSidMap.put(userSidList, cupSid);
            addPairList.add(mdl);
        }

        if (addPairList.isEmpty()) {
            return;
        }

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CHT_USER_PAIR (");
            sql.addSql("   CUP_SID, CUP_UID_F, CUP_UID_S, CUP_COMP_FLG)");
            sql.addSql(" values ");
            ChtUserFavoriteModel cufMdl = null;
            for (int idx = 0; idx < addPairList.size(); idx++) {
                sql.addSql(" (?, ?, ?, ?) ");
                if (idx != addPairList.size() - 1) {
                    sql.addSql(",");
                }

                cufMdl = addPairList.get(idx);
                sql.addIntValue(cufMdl.getCupSid());
                sql.addIntValue(cufMdl.getChfUid());
                sql.addIntValue(cufMdl.getChfSid());
                sql.addIntValue(0);
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] ユーザチャットのお気に入り区分を新規作成したテーブルに移行する
     * <br>[解  説]
     * <br>[備  考]
     * @param saiban 採番用コントローラ
     * @throws SQLException SQL実行時例外
     */
    private void __addUserFavorite(MlCountMtController saiban) throws SQLException {

        List<ChtUserFavoriteModel> userFavoriteList = __getUserFavoritePair();
        if (userFavoriteList.isEmpty()) {
            return;
        }

        //ペア情報が存在しない場合に登録
        __addUserPair(saiban, userFavoriteList);

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CHT_USER_UCONF (");
            sql.addSql("   USR_SID, CUP_SID, CUUC_FAVORITE_KBN, CUUC_MUTE_KBN)");
            sql.addSql(" values ");

            ChtUserFavoriteModel cufMdl = null;
            for (int idx = 0; idx < userFavoriteList.size(); idx++) {
                sql.addSql(" (?, ?, ?, ?) ");
                if (idx != userFavoriteList.size() - 1) {
                    sql.addSql(",");
                }
                cufMdl = userFavoriteList.get(idx);
                sql.addIntValue(cufMdl.getChfUid());
                sql.addIntValue(cufMdl.getCupSid());
                sql.addIntValue(1);
                sql.addIntValue(0);
            }

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] グループチャットのお気に入り情報テーブルを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __dropFavorite() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" drop table CHT_FAVORITE");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
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

        //グループ投稿添付情報テーブルを作成
        __addSql(sqlList,
            " create table CHT_GROUP_DATA_TEMP("
            + "    CGD_SID            bigint        not null,"
            + "    BIN_SID            bigint        not null,"
            + "    primary key (CGD_SID, BIN_SID)"
            + " );");

        //ユーザ投稿添付情報テーブルを作成
        __addSql(sqlList,
            " create table CHT_USER_DATA_TEMP("
            + "    CUD_SID            bigint        not null,"
            + "    BIN_SID            bigint        not null,"
            + "    primary key (CUD_SID, BIN_SID)"
            + " );");

        //グループ投稿リアクション情報テーブルを作成
        __addSql(sqlList,
            " create table CHT_GROUP_DATA_REACTION("
            + "    CGD_SID            bigint        not null,"
            + "    USR_SID            integer       not null,"
            + "    CGR_REACTION_NO    integer       not null,"
            + "    CGR_ADATE          timestamp     not null,"
            + "    primary key (CGD_SID, USR_SID, CGR_REACTION_NO)"
            + " );");

        //ユーザ投稿リアクション情報テーブルを作成
        __addSql(sqlList,
            " create table CHT_USER_DATA_REACTION("
            + "    CUD_SID            bigint        not null,"
            + "    USR_SID            integer       not null,"
            + "    CUR_REACTION_NO    integer       not null,"
            + "    CUR_ADATE          timestamp     not null,"
            + "    primary key (CUD_SID, USR_SID, CUR_REACTION_NO)"
            + " );");

        //スタンプ情報テーブルを作成
        __addSql(sqlList,
            " create table CHT_STAMP("
            + "    CST_SID         integer not null,"
            + "    BIN_SID         bigint,"
            + "    CST_DEFSTAMP_ID integer,"
            + "    CST_SORT        integer not null,"
            + "    CST_JKBN        integer not null,"
            + "    CST_USE_KBN     integer not null,"
            + "    primary key (CST_SID)"
            + " );");

        //リプライ元投稿SIDフィールドを追加
        __addSql(sqlList, "alter table CHT_GROUP_DATA add CGD_REPLY_SID bigint default 0;");
        __addSql(sqlList, "alter table CHT_USER_DATA add CUD_REPLY_SID bigint default 0;");
        //グループ投稿メンション情報テーブルを作成
        __addSql(sqlList,
            " create table CHT_GROUP_DATA_MENTION("
            + "    CGD_SID            bigint        not null,"
            + "    USR_SID            integer       not null,"
            + "    primary key (CGD_SID, USR_SID)"
            + " );");

        //ユーザ投稿メンション情報テーブルを作成
        __addSql(sqlList,
            " create table CHT_USER_DATA_MENTION("
            + "    CUD_SID            bigint        not null,"
            + "    USR_SID            integer       not null,"
            + "    primary key (CUD_SID, USR_SID)"
            + " );");

        //URL区分フィールドを追加
        __addSql(sqlList, "alter table CHT_GROUP_DATA add CGD_URL_FLG integer not null default 0;");
        __addSql(sqlList, "alter table CHT_USER_DATA add CUD_URL_FLG  integer not null default 0;");

        //右ペイン表示フラグフィールドを追加
        __addSql(sqlList,
                "alter table CHT_PRI_CONF"
                + " add CPC_RIGHTPANE_FLG integer not null default 0;");

        //ユーザ投稿ピン止め情報テーブルを作成
        __addSql(sqlList,
             "create table CHT_USER_DATA_PIN("
                + " USR_SID     integer   not null,"
                + " CUP_SID     integer   not null,"
                + " CUD_SID     bigint    not null,"
                + " CUDP_SORT   integer   not null,"
                + " primary key (USR_SID, CUP_SID, CUD_SID)"
                + " );");

        //グループ投稿ピン止め情報テーブルを追加
        __addSql(sqlList,
                "create table CHT_GROUP_DATA_PIN("
                + " USR_SID     integer   not null,"
                + " CGI_SID     integer   not null,"
                + " CGD_SID     bigint    not null,"
                + " CGDP_SORT   integer   not null,"
                + " primary key (USR_SID, CGI_SID, CGD_SID)"
                + " );");

        //グループ状態情報テーブルを追加
        __addSql(sqlList,
                "create table CHT_GROUP_UCONF("
                + " USR_SID     integer   not null,"
                + " CGI_SID     integer   not null,"
                + " CGUC_FAVORITE_KBN     integer    not null default 0,"
                + " CGUC_MUTE_KBN   integer   not null default 0,"
                + " primary key (USR_SID, CGI_SID)"
                + " );");

        //ユーザ状態情報テーブルを追加
        __addSql(sqlList,
                "create table CHT_USER_UCONF("
                + " USR_SID     integer   not null,"
                + " CUP_SID     integer   not null,"
                + " CUUC_FAVORITE_KBN     integer    not null default 0,"
                + " CUUC_MUTE_KBN   integer   not null default 0,"
                + " primary key (USR_SID, CUP_SID)"
                + " );");


        //削除済みユーザデータの削除


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

    /**
     * <br>[機  能] 初期スタンプの採番情報を採番テーブルに登録する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __insertStampSaibanData() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CMN_SAIBAN (");
            sql.addSql("   SBN_SID,");
            sql.addSql("   SBN_SID_SUB,");
            sql.addSql("   SBN_NUMBER,");
            sql.addSql("   SBN_STRING,");
            sql.addSql("   SBN_AID,");
            sql.addSql("   SBN_ADATE,");
            sql.addSql("   SBN_EID,");
            sql.addSql("   SBN_EDATE");
            sql.addSql(" )");
            sql.addSql(" values (");
            sql.addSql("   'chat',");
            sql.addSql("   'stamp',");
            sql.addSql("   21,");
            sql.addSql("   'stamp',");
            sql.addSql("   0,");
            sql.addSql("   current_timestamp,");
            sql.addSql("   0,");
            sql.addSql("   current_timestamp");
            sql.addSql(" );");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] チャットの投稿情報(ユーザ, グループ)からBIN_SIDを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __deleteChtTempData() throws SQLException {
        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        //チャットメッセージ情報からBIN_SIDを削除
        __addSql(sqlList,
            "   update"
            + "   CMN_BINF"
            + " set"
            + "   BIN_JKBN = 9"
            + " where"
            + "   CMN_BINF.BIN_SID in ("
            + "     select"
            + "       CHT_USER_DATA.BIN_SID"
            + "     from"
            + "       CHT_USER_DATA"
            + "     where"
            + "       CHT_USER_DATA.CUD_STATE_KBN = 9"
            + "     union all"
            + "     select"
            + "       CHT_GROUP_DATA.BIN_SID"
            + "     from"
            + "       CHT_GROUP_DATA"
            + "     where"
            + "       CHT_GROUP_DATA.CGD_STATE_KBN = 9"
            + "   );"
        );

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
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
     * <br>[機  能] グループ投稿情報から取得した添付ファイル情報を、添付情報テーブルに登録する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __insertGroupTempData() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CHT_GROUP_DATA_TEMP (CGD_SID, BIN_SID)");
            sql.addSql("select ");
            sql.addSql("   CGD_SID, ");
            sql.addSql("   BIN_SID ");
            sql.addSql(" from ");
            sql.addSql("  CHT_GROUP_DATA");
            sql.addSql(" where ");
            sql.addSql("  BIN_SID != -1");
            sql.addSql(" and ");
            sql.addSql("  CGD_STATE_KBN != 9");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] ユーザ投稿情報から取得した添付ファイル情報を、添付情報テーブルに登録する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __insertUserTempData() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CHT_USER_DATA_TEMP (CUD_SID, BIN_SID)");
            sql.addSql("select ");
            sql.addSql("   CUD_SID, ");
            sql.addSql("   BIN_SID ");
            sql.addSql(" from ");
            sql.addSql("  CHT_USER_DATA");
            sql.addSql(" where ");
            sql.addSql("  BIN_SID != -1");
            sql.addSql(" and ");
            sql.addSql("  CUD_STATE_KBN != 9");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] チャットの投稿情報(ユーザ, グループ)からBIN_SIDを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __deleteChtBinColumn() throws SQLException {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        //チャットメッセージ情報からBIN_SIDを削除
        __addSql(sqlList, " alter table CHT_USER_DATA drop column BIN_SID;");
        __addSql(sqlList, " alter table CHT_GROUP_DATA drop column BIN_SID;");

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
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
     * <br>[機  能] チャットのスタンプ情報にて初期スタンプを追加する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __insertDefaultStampData() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert into CHT_STAMP (");
            sql.addSql("   CST_SID,");
            sql.addSql("   BIN_SID,");
            sql.addSql("   CST_DEFSTAMP_ID,");
            sql.addSql("   CST_SORT,");
            sql.addSql("   CST_JKBN,");
            sql.addSql("   CST_USE_KBN");
            sql.addSql(" )");
            sql.addSql(" values");
            sql.addSql(" (1, 0, 1, 1, 0, 0),");
            sql.addSql(" (2, 0, 2, 2, 0, 0),");
            sql.addSql(" (3, 0, 3, 3, 0, 0),");
            sql.addSql(" (4, 0, 4, 4, 0, 0),");
            sql.addSql(" (5, 0, 5, 5, 0, 0),");
            sql.addSql(" (6, 0, 6, 6, 0, 0),");
            sql.addSql(" (7, 0, 7, 7, 0, 0),");
            sql.addSql(" (8, 0, 8, 8, 0, 0),");
            sql.addSql(" (9, 0, 9, 9, 0, 0),");
            sql.addSql(" (10, 0, 10, 10, 0, 0),");
            sql.addSql(" (11, 0, 11, 11, 0, 0),");
            sql.addSql(" (12, 0, 12, 12, 0, 0),");
            sql.addSql(" (13, 0, 13, 13, 0, 0),");
            sql.addSql(" (14, 0, 14, 14, 0, 0),");
            sql.addSql(" (15, 0, 15, 15, 0, 0),");
            sql.addSql(" (16, 0, 16, 16, 0, 0),");
            sql.addSql(" (17, 0, 17, 17, 0, 0),");
            sql.addSql(" (18, 0, 18, 18, 0, 0),");
            sql.addSql(" (19, 0, 19, 19, 0, 0),");
            sql.addSql(" (20, 0, 20, 20, 0, 0),");
            sql.addSql(" (21, 0, 21, 21, 0, 0)");

            log__.info(sql.toLogString());
            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * <br>[機  能] チャットの投稿情報(ユーザ, グループ)にてCST_SIDを追加する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __addChtStampSidColumn() throws SQLException {

        ArrayList<SqlBuffer> sqlList = new ArrayList<SqlBuffer>();

        //チャットメッセージ情報からBIN_SIDを削除
        __addSql(sqlList, " alter table CHT_USER_DATA add CST_SID integer default 0;");
        __addSql(sqlList, " alter table CHT_GROUP_DATA add CST_SID integer default 0;");

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
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
     * <br>[機  能] チャットグループユーザ情報から削除済みユーザの情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __deleteChtGroupUser() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" where ");
            sql.addSql("   CHT_GROUP_USER.CGU_KBN=?");
            sql.addSql(" and ");
            sql.addSql("   CHT_GROUP_USER.CGU_SELECT_SID in (");
            sql.addSql("     select");
            sql.addSql("       USR_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_USRM");
            sql.addSql("     where");
            sql.addSql("       USR_JKBN=?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(1); //ユーザ区分
            sql.addIntValue(GSConst.JTKBN_DELETE);
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
     * <br>[機  能] チャットグループユーザ情報から削除済みグループの情報を削除する。
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __deleteChtGroupGroup() throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" delete");
            sql.addSql(" from");
            sql.addSql("   CHT_GROUP_USER");
            sql.addSql(" where ");
            sql.addSql("   CHT_GROUP_USER.CGU_KBN=?");
            sql.addSql(" and ");
            sql.addSql("   CHT_GROUP_USER.CGU_SELECT_SID in (");
            sql.addSql("     select");
            sql.addSql("       GRP_SID");
            sql.addSql("     from");
            sql.addSql("       CMN_GROUPM");
            sql.addSql("     where");
            sql.addSql("       GRP_JKBN=?");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(2);
            sql.addIntValue(9);
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

}
