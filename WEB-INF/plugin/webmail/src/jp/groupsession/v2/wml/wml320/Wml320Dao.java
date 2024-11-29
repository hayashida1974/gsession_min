package jp.groupsession.v2.wml.wml320;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.groupsession.v2.cmn.DBUtilFactory;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.IDbUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.wml.wml010.Wml010Const;

/**
 * <br>[機  能] WEBメール 個人設定 メール情報一括削除画面で使用するDAOクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml320Dao extends AbstractDao {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml320Dao.class);

    /**
     * <p>
     * デフォルトコンストラクタ
     */
    public Wml320Dao() {
    }

    /**
     * <p>
     * デフォルトコンストラクタ
     *
     * @param con
     *            DBコネクション
     */
    public Wml320Dao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 削除対象メールのメール情報一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param usrSid ユーザSID
     * @param sortKey ソートキー
     * @param order オーダーキー
     * @param offset ページ開始位置
     * @return メール情報一覧
     * @throws SQLException SQL実行例外
     */
    protected List <Wml320DspModel> getMailList(
            int usrSid,
            int sortKey,
            int order,
            int offset)
                    throws SQLException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        ArrayList<Wml320DspModel> ret = new ArrayList<Wml320DspModel>();
        con = getCon();

        try {
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM as WMD_MAILNUM,");
            sql.addSql("   WML_MAILDATA.WMD_TITLE as WMD_TITLE,");
            sql.addSql("   WML_MAILDATA.WMD_FROM as WMD_FROM,");
            sql.addSql("   WML_MAILDATA.WMD_SDATE as WMD_SDATE,");
            sql.addSql("   WML_MAILDATA.WMD_READED as WMD_READED,");
            sql.addSql("   WML_MAILDATA.WMD_SIZE as WMD_SIZE,");
            sql.addSql("   WML_MAILDATA.WMD_TEMPFLG as WMD_TEMPFLG");
            sql.addSql(" from ");
            sql.addSql("   WML_MAILDATA, ");
            sql.addSql("   WML_DELMAIL_TEMP ");
            sql.addSql(" where ");
            sql.addSql("    WML_DELMAIL_TEMP.USR_SID = ? ");
            sql.addSql(" and ");
            sql.addSql("   WML_MAILDATA.WMD_MAILNUM = WML_DELMAIL_TEMP.WMD_MAILNUM ");
            sql.addSql(" order by ");

            if (sortKey == GSConstWebmail.WML_DELETE_FROM) {
                sql.addSql(" WML_MAILDATA.WMD_FROM ");
            } else if (sortKey == GSConstWebmail.WML_DELETE_TITLE) {
                sql.addSql(" WML_MAILDATA.WMD_TITLE ");
            } else {
                sql.addSql(" WML_MAILDATA.WMD_SDATE ");
            }
            if (order == GSConstWebmail.ORDER_ASC) {
                sql.addSql(" asc ");
            } else if (order == GSConstWebmail.ORDER_DESC) {
                sql.addSql(" desc ");
            }

            sql.addIntValue(usrSid);

            sql.setPagingValue(offset - 1, GSConstWebmail.LIMIT_DSP_DELETELIST);

            log__.info(sql.toLogString());

            pstmt = con.prepareStatement(sql.toSqlString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Wml320DspModel bean = new Wml320DspModel();
                bean.setMailNum(rs.getLong("WMD_MAILNUM"));
                bean.setTitle(rs.getString("WMD_TITLE"));
                bean.setFrom(rs.getString("WMD_FROM"));
                bean.setTempFlg(rs.getInt("WMD_TEMPFLG"));;
                bean.setReaded(rs.getInt("WMD_READED"));
                bean.setDspDate(UDateUtil.getSlashYYMD(UDate.getInstanceTimestamp(rs
                        .getTimestamp("WMD_SDATE")))
                        + " "
                        + UDateUtil.getSeparateHM(UDate.getInstanceTimestamp(rs
                                .getTimestamp("WMD_SDATE"))));
                ret.add(bean);

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
     * <br>[機  能] 指定した条件と一致するWEBメール メール情報を「一括削除対象メール」に登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param searchMdl 検索モデル
     * @return
     * @throws SQLException SQL実行例外
     */
    protected void insertDelMailList(Wml320SearchModel searchMdl)
    throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        UDate now = new UDate();
        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" insert ");
            sql.addSql(" into ");
            sql.addSql("   WML_DELMAIL_TEMP ");
            sql.addSql("   select ");
            sql.addSql("     ?, ");
            sql.addSql("     WML_MAILDATA.WMD_MAILNUM,");
            sql.addSql("     WAC_SID, ");
            sql.addSql("     ? ");
            sql.addSql("   from ");
            sql.addSql("     WML_MAILDATA ");
            sql.addSql("   where ");
            sql.addSql("     WML_MAILDATA.WAC_SID = ?");
            sql.addSql("   and ");
            sql.addSql("     WML_MAILDATA.WDR_SID in (");
            sql.addSql("       select ");
            sql.addSql("         WDR_SID  ");
            sql.addSql("       from ");
            sql.addSql("         WML_DIRECTORY  ");
            sql.addSql("       where ");
            sql.addSql("         WML_DIRECTORY.WAC_SID = ?");
            sql.addIntValue(searchMdl.getUsrSid());
            sql.addDateValue(now);
            sql.addIntValue(searchMdl.getAccount());
            sql.addIntValue(searchMdl.getAccount());

            sql.addSql("       and ");
            sql.addSql("         WDR_TYPE in (  ");
            int cnt = 0;
            for (int dir : searchMdl.getDirectory()) {
                if (cnt != 0) {
                    sql.addSql("       , ");
                }
                sql.addSql("       ? ");
                sql.addIntValue(dir);
                cnt++;
            }
            sql.addSql("         )  ");
            sql.addSql("       )  ");
            __setWhereSql(sql, searchMdl);


            sql.addSql(" order by ");

            int sortKey = searchMdl.getSortKey();
            String order = "asc";
            if (searchMdl.getOrder() == GSConstWebmail.ORDER_DESC) {
                order = "desc";
            }

            if (sortKey == GSConstWebmail.WML_DELETE_FROM) {
                sql.addSql("   WML_MAILDATA.WMD_FROM " + order);
            } else if (sortKey == GSConstWebmail.WML_DELETE_TITLE) {
                sql.addSql("   WML_MAILDATA.WMD_TITLE " + order);
            } else {
                sql.addSql("   WML_MAILDATA.WMD_SDATE " + order);
            }


            sql.setPagingValue(0, searchMdl.getDelLimit());

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
     * <br>[機  能] 検索SQLを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param sql SqlBuffer
     * @param searchMdl 検索モデル
     * @return SqlBuffer
     * @throws SQLException SQL実行例外
     */
    private SqlBuffer __setWhereSql(SqlBuffer sql, Wml320SearchModel searchMdl) {
        int wacSid = searchMdl.getAccount();
        boolean existAccount = wacSid > 0;

        //from
        if (!StringUtil.isNullZeroString(searchMdl.getFrom())) {
            sql.addSql("   and ");
            sql.addSql("     WML_MAILDATA.WMD_FROM like '%"
                    + JDBCUtil.escapeForLikeSearch(searchMdl.getFrom())
                    + "%' ESCAPE '"
                    + JDBCUtil.def_esc
                    + "'");
        }

        //未読/既読
        if (searchMdl.getReaded() == Wml010Const.SEARCH_READKBN_NOREAD) {
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WMD_READED = ?");
            sql.addIntValue(GSConstWebmail.READEDFLG_NOREAD);
        } else if (searchMdl.getReaded() == Wml010Const.SEARCH_READKBN_READED) {
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WMD_READED = ?");
            sql.addIntValue(GSConstWebmail.READEDFLG_READED);
        }
        //to
        if (searchMdl.getDestTypeTo() == Wml320Form.WML320_DECIDE
                || searchMdl.getDestTypeCc() == Wml320Form.WML320_DECIDE
                || searchMdl.getDestTypeBcc() == Wml320Form.WML320_DECIDE) {
            if (!StringUtil.isNullZeroString(searchMdl.getDest())) {
                sql.addSql("    and");
                sql.addSql("      WML_MAILDATA.WMD_MAILNUM in (");
                sql.addSql("        select WMD_MAILNUM");
                sql.addSql("        from");
                sql.addSql("          (");
                sql.addSql("            select WMD_MAILNUM, WSA_ADDRESS_SRH from WML_SENDADDRESS");
                sql.addSql("            where");
                if (existAccount) {
                    sql.addSql("              WML_SENDADDRESS.WAC_SID = ?");
                    sql.addSql("            and");
                    sql.addIntValue(wacSid);
                }
                sql.addSql("              WML_SENDADDRESS.WSA_TYPE in (");


                //TO, CC, BCC を選択
                boolean destFlg = false;
                if (searchMdl.getDestTypeTo() == Wml320Form.WML320_DECIDE) {
                    sql.addSql("                 ?");
                    sql.addIntValue(GSConstWebmail.WSA_TYPE_TO);
                    destFlg = true;
                }
                if (searchMdl.getDestTypeCc() == Wml320Form.WML320_DECIDE) {
                    if (destFlg) {
                        sql.addSql("              ,");
                    }
                    sql.addSql("                  ?");
                    sql.addIntValue(GSConstWebmail.WSA_TYPE_CC);
                    destFlg = true;
                }
                if (searchMdl.getDestTypeBcc() == Wml320Form.WML320_DECIDE) {
                    if (destFlg) {
                        sql.addSql("              ,");
                    }
                    sql.addSql("                  ?");
                    sql.addIntValue(GSConstWebmail.WSA_TYPE_BCC);
                }
                sql.addSql("              )");
                sql.addSql("          ) SENDADDRESS");
                sql.addSql("        where");
                sql.addSql("          WSA_ADDRESS_SRH like '%"
                        + JDBCUtil.escapeForLikeSearch(searchMdl.getDest().toLowerCase())
                        + "%' ESCAPE '"
                        + JDBCUtil.def_esc
                        + "'");
                sql.addSql("      )");
            }
        }

        //添付ファイル
        if (searchMdl.getAttach() == GSConstWebmail.TEMPFLG_EXIST) {
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WMD_TEMPFLG = ?");
            sql.addIntValue(GSConstWebmail.TEMPFLG_EXIST);
        } else if (searchMdl.getAttach() == Wml320Form.WML320_TEMPFLG_NOTHING) {
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WMD_TEMPFLG = ?");
            sql.addIntValue(GSConstWebmail.TEMPFLG_NOTHING);
        }

        //日付
        if (searchMdl.getDateType() == Wml320Form.WML320_DECIDE) {
            //日付 受信日 From
            if (searchMdl.getDateFr() != null
            && searchMdl.getDateTo() == null) {
                sql.addSql("    and");
                sql.addSql("      WML_MAILDATA.WMD_SDATE >= ?");
                searchMdl.getDateFr().setZeroHhMmSs();
                sql.addDateValue(searchMdl.getDateFr());
            }

            //日付 受信日 To
            if (searchMdl.getDateTo() != null) {
                searchMdl.getDateTo().setMaxHhMmSs();

                sql.addSql("    and");
                if (searchMdl.getDateFr() == null) {
                    sql.addSql("      WML_MAILDATA.WMD_SDATE <= ?");
                    sql.addDateValue(searchMdl.getDateTo());
                } else {
                    sql.addSql("      WML_MAILDATA.WMD_SDATE between ?");
                    sql.addSql("                             and ?");
                    sql.addDateValue(searchMdl.getDateFr());
                    sql.addDateValue(searchMdl.getDateTo());
                }
            }
        }

        //ラベル
        if (searchMdl.getLabel() > 0) {
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WMD_MAILNUM in (");
            sql.addSql("        select WMD_MAILNUM from WML_LABEL_RELATION");
            sql.addSql("        where");
            if (existAccount) {
                sql.addSql("          WAC_SID = ?");
                sql.addSql("        and");
                sql.addIntValue(wacSid);
            }
            sql.addSql("          WLB_SID = ?");
            sql.addIntValue(searchMdl.getLabel());

            sql.addSql("      )");
        }

        //サイズ
        if (searchMdl.getSize() > 0) {
            sql.addSql("    and");
            sql.addSql("      WML_MAILDATA.WMD_SIZE >= ?");
            sql.addIntValue(searchMdl.getSize() * 1024);
        }

        //キーワード
        if (!StringUtil.isNullZeroString(searchMdl.getKeyword())) {

            sql.addSql("    and");
            sql.addSql("      (");
            sql.addSql("        WML_MAILDATA.WMD_TITLE like '%"
                  + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                  + "%' ESCAPE '"
                  + JDBCUtil.def_esc
                  + "'");

            sql.addSql("     or ");
            IDbUtil dbUtil = DBUtilFactory.getInstance();
            if (dbUtil.getDbType() == GSConst.DBTYPE_H2DB) {
                //H2使用時
                sql.addSql("       WML_MAILDATA.WMD_MAILNUM in (");
                sql.addSql("         select FT.KEYS[0] from FTL_SEARCH_DATA('"
                        + JDBCUtil.encFullStringH2Lucene(searchMdl.getKeyword())
                        + "', 0, 0) FT");
                sql.addSql("         where FT.TABLE = 'WML_MAIL_BODY'");
                sql.addSql("       )");


            } else if (dbUtil.getDbType() == GSConst.DBTYPE_POSTGRES) {
                //PostgreSQL
                sql.addSql("      exists (");
                sql.addSql("        select 1 from WML_MAIL_BODY");
                sql.addSql("        where");
                if (existAccount) {
                    sql.addSql("          WML_MAIL_BODY.WAC_SID = ?");
                    sql.addSql("        and");
                    sql.addIntValue(wacSid);
                }

                if (CommonBiz.isPGroongaUse()) {
                    //PGroonga使用
                    sql.addSql("          WML_MAIL_BODY.WMB_BODY &@ '"
                            + JDBCUtil.encFullTextSearch(searchMdl.getKeyword())
                            + "'");
                } else {
                    //PGroonga未使用
                    sql.addSql("          WML_MAIL_BODY.WMB_BODY like '%"
                            + JDBCUtil.escapeForLikeSearch(searchMdl.getKeyword())
                            + "%' ESCAPE '"
                            + JDBCUtil.def_esc
                            + "'");
                }

                sql.addSql("        and");
                sql.addSql("          WML_MAILDATA.WMD_MAILNUM = WML_MAIL_BODY.WMD_MAILNUM");
                sql.addSql("      )");
            }

            sql.addSql("     )");
        }
        return sql;
    }

}