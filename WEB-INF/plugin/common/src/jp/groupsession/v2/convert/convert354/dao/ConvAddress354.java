package jp.groupsession.v2.convert.convert354.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.convert.convert354.model.AdrLabelModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] アドレス帳のコンバートを行う
 * <br>[解  説] v3.5.4以前のバージョンからのコンバートで使用する
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvAddress354 extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(ConvAddress354.class);

    /**
     * <p>Default Constructor
     */
    public ConvAddress354() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public ConvAddress354(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] アドレス帳のコンバートを実行
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException 例外
     */
    public void convert(Connection con) throws SQLException {

        log__.debug("-- v3.5.4へのコンバート - アドレス帳 開始 --");

        //アドレス帳ラベル設定
        adrLabelConv(con);

        log__.debug("-- v3.5.4へのコンバート - アドレス帳 終了 --");
    }

    /**
     * <br>[機  能] アドレス帳ラベル設定を行う
     * <br>[解  説] カテゴリごと削除されたとき、削除されたカテゴリ内のラベルを
     * 　　　　　　『未設定』カテゴリに移動するコンバート処理です
     * <br>[備  考]
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void adrLabelConv(Connection con) throws SQLException {

        PreparedStatement pstmt = null;

        try {

            //『未設定』カテゴリへの登録が必要なラベルデータを取得する
            List<AdrLabelModel> lbCategoryMdlList = new ArrayList<AdrLabelModel>();
            lbCategoryMdlList = __getLabelDataToNotCategory(con);

            //『未設定』カテゴリの最大ソート数を取得する
            int maxSortNotConf = __getMaxSort(1, con);
            log__.debug("***未設定カテゴリ最大ソート数:" + maxSortNotConf);

            //ラベルを『未設定』カテゴリに設定する
            for (AdrLabelModel bean : lbCategoryMdlList) {

                //カテゴリを「未設定」のカテゴリに変更
                maxSortNotConf++;

                SqlBuffer sql = new SqlBuffer();

                sql.addSql(" update");
                sql.addSql("   ADR_LABEL");
                sql.addSql(" set ");
                sql.addSql("   ALB_SORT=?,");
                sql.addSql("   ALC_SID=?");
                sql.addSql(" where ");
                sql.addSql("   ALB_SID=?");

                sql.addIntValue(maxSortNotConf);
                //『未設定』カテゴリSID
                sql.addIntValue(1);
                sql.addIntValue(bean.getAlbSid());

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
     * <br>[機  能] 『未設定』カテゴリへの追加が必要なラベル情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @return ラベル情報
     * @throws SQLException SQL実行例外
     */
    private List<AdrLabelModel> __getLabelDataToNotCategory(Connection con)
    throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<AdrLabelModel> ret = new ArrayList<AdrLabelModel>();
        AdrLabelModel alMdl = null;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   ALC_SID,");
            sql.addSql("   ALB_SID");
            sql.addSql(" from ");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" where ");
            sql.addSql("   ALC_SID not in (");
            sql.addSql("     select ALC_SID from ADR_LABEL_CATEGORY");
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                alMdl = new AdrLabelModel();
                alMdl.setAlcSid(rs.getInt("ALC_SID"));
                alMdl.setAlbSid(rs.getInt("ALB_SID"));
                ret.add(alMdl);
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
     * <br>[機  能] 『未設定』カテゴリの最大ソート数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param alcSid カテゴリSID
     * @param con コネクション
     * @return 最大ソート数
     * @throws SQLException SQL実行例外
     */
    private int __getMaxSort(int alcSid, Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int ret = 0;

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   max(ALB_SORT) as MAX");
            sql.addSql(" from");
            sql.addSql("   ADR_LABEL");
            sql.addSql(" where");
            sql.addSql("   ALC_SID=?");

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.addIntValue(alcSid);

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                ret = rs.getInt("MAX");
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