package jp.groupsession.v2.convert.convert550.dao;

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
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.SaibanDao;
import jp.groupsession.v2.cmn.model.base.SaibanModel;

/**
 * <br>[機  能] alter tableなどのDBの編集を行うDAOクラス
 * <br>[解  説] v5.5.0へコンバートする際に使用する
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

        //施設グループIDが空のデータを修正
        __updateRsvEmptyGrpId();


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
        // 除外してしまった"GS 菅理者"と"システム メール"をシステム管理グループに戻す
        __addSql(sqlList,
            " insert into CMN_BELONGM("
            + "   grp_sid , usr_sid , beg_auid , beg_adate , beg_euid , beg_edate , beg_defgrp , beg_grpkbn)"
            + " select"
            + "   0,"
            + "   0,"
            + "   0,"
            + "   '2007-04-25 11:15:22.14',"
            + "   0,"
            + "   '2007-04-25 11:15:22.14',"
            + "   1 ,"
            + "   1"
            + " where not exists("
            + "   select"
            + "     * "
            + "   from"
            + "     CMN_BELONGM"
            + "   where"
            + "     GRP_SID = 0"
            + "   and"
            + "     USR_SID = 0"
            + " );");
        
        __addSql(sqlList,
            " insert into CMN_BELONGM("
            + "   grp_sid , usr_sid , beg_auid , beg_adate , beg_euid , beg_edate , beg_defgrp , beg_grpkbn)"
            + " select"
            + "   0,"
            + "   1,"
            + "   0,"
            + "   '2007-04-25 11:15:22.14',"
            + "   0,"
            + "   '2007-04-25 11:15:22.14',"
            + "   1 ,"
            + "   1"
            + " where not exists("
            + "   select"
            + "     * "
            + "   from"
            + "     CMN_BELONGM"
            + "   where"
            + "     GRP_SID = 0"
            + "   and"
            + "     USR_SID = 1"
            + " );");

        //==== ファイル管理 振り分け先フォルダ変更 ====
        __addSql(sqlList, "alter table FILE_CABINET add FCB_SORT_FOLDER3 integer;");
        __addSql(sqlList, "alter table FILE_ERRL_ADDDATA add FEA_DEFGRP_NAME varchar(50);");
        __addSql(sqlList, "update FILE_CABINET set FCB_SORT_FOLDER3 = 0;");

        //登録済みの仮登録ファイル情報に「ファイル仮登録ユーザのデフォルトグループ名」を設定
        __addSql(sqlList,
            " update"
            + "   FILE_ERRL_ADDDATA"
            + " set"
            + "   FEA_DEFGRP_NAME = ("
            + "     select"
            + "       CMN_GROUPM.GRP_NAME as GRP_NAME"
            + "     from"
            + "       CMN_GROUPM,"
            + "       CMN_BELONGM"
            + "     where"
            + "       CMN_BELONGM.BEG_DEFGRP = 1"
            + "     and"
            + "       CMN_GROUPM.GRP_SID = CMN_BELONGM.GRP_SID"
            + "     and"
            + "       FILE_ERRL_ADDDATA.FDR_AUID = CMN_BELONGM.USR_SID"
            + "   );");

        //仮登録ファイルの削除ユーザが削除されていた場合、
        __addSql(sqlList,
            " update"
            + "   FILE_ERRL_ADDDATA"
            + " set"
            + "   FEA_DEFGRP_NAME = '保存先が存在しないファイル'"
            + " where"
            + "   not exists ("
            + "     select"
            + "       1"
            + "     from"
            + "       CMN_BELONGM"
            + "     where"
            + "       CMN_BELONGM.USR_SID = FILE_ERRL_ADDDATA.FDR_AUID"
            + "   );");

        __addSql(sqlList, "alter table FILE_ERRL_ADDDATA alter column FEA_DEFGRP_NAME set not null;");
        //==== ファイル管理 振り分け先フォルダ変更 ====

        //WEBメールのラベル情報からWLB_TYPEとUSR_SIDを削除
        __addSql(sqlList, " alter table WML_LABEL drop column WLB_TYPE;");
        __addSql(sqlList, " alter table WML_LABEL drop column USR_SID;");

        //マイプロジェクト 担当者のないTODOに担当者を設定
        __addSql(sqlList,
            " insert into PRJ_TODOMEMBER ("
            + "   PRJ_SID , PTD_SID , USR_SID , PTM_EMPLOYEE_KBN , PTM_AUID , PTM_ADATE , PTM_EUID , PTM_EDATE)"
            + " select"
            + "   PRJD.PRJ_SID,"
            + "   TODO.PTD_SID,"
            + "   PRJM.USR_SID,"
            + "   0,"
            + "   TODO.PTD_AUID,"
            + "   TODO.PTD_ADATE,"
            + "   TODO.PTD_EUID,"
            + "   TODO.PTD_EDATE"
            + " from"
            + "   PRJ_PRJDATA PRJD"
            + "   inner join"
            + "     PRJ_TODODATA TODO"
            + "     on PRJD.PRJ_SID = TODO.PRJ_SID"
            + "   inner join"
            + "     PRJ_MEMBERS PRJM"
            + "     on PRJM.PRJ_SID = PRJD.PRJ_SID"
            + "     and PRJM.PRM_EMPLOYEE_KBN = 0"
            + " where"
            + "   PRJD.PRJ_MY_KBN = 1"
            + " and not exists("
            + "   select 1 from PRJ_TODOMEMBER EM"
            + "   where "
            + "     EM.PRJ_SID = PRJD.PRJ_SID"
            + "   and "
            + "     EM.PTD_SID = TODO.PTD_SID"
            + "   and "
            + "     EM.USR_SID = PRJM.USR_SID"
            + "   and "
            + "     EM.PTM_EMPLOYEE_KBN = 0"
            + " );");

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
     * <br>[機  能] 施設グループのグループID=空文字となっているデータを編集し、重複しない数字が入るように修正する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private void __updateRsvEmptyGrpId() throws SQLException {

        //編集対象があるかの確認
        List<RsvSisGrpModel> targetGrpList = __getRsvGrpSid();
        if (targetGrpList.isEmpty()) {
            return;
        }

        //グループIDに使用するグループSIDを取得
        SaibanDao sbnDao = new SaibanDao(getCon());
        SaibanModel searchMdl = new SaibanModel();
        searchMdl.setSbnSid("reserve");
        searchMdl.setSbnSidSub("group");
        SaibanModel sbnMdl = sbnDao.getSaibanData(searchMdl);

        int grpIdInt;
        if (sbnMdl == null) {
            grpIdInt = 1;
        } else {
            grpIdInt = (int) sbnMdl.getSbnNumber();
        }

        List<String> existGrpId = __getExistGrpId();
        
        for (RsvSisGrpModel grpMdl : targetGrpList) {
            while (true) {
                String grpId = String.valueOf(grpIdInt);
                if (!existGrpId.contains(grpId)) {
                    grpMdl.setRsgId(grpId);
                    existGrpId.add(grpId);
                    break;
                }
                grpIdInt++;
            }
        }

        __updateRsvGrp(targetGrpList);
    }

    /**
     * <br>[機  能] 施設グループが空文字になっている施設グループ一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行例外
     */
    private List<RsvSisGrpModel> __getRsvGrpSid() throws SQLException {

        List<RsvSisGrpModel> ret = new ArrayList<RsvSisGrpModel>();

        PreparedStatement pstmt = null;
        Connection con = null;
        ResultSet rs = null;

        con = getCon();
        SqlBuffer sql = new SqlBuffer();
        sql.addSql(" select");
        sql.addSql("   RSG_SID");
        sql.addSql(" from");
        sql.addSql("   RSV_SIS_GRP");
        sql.addSql(" where");
        sql.addSql("   RSG_ID = ?");
        sql.addStrValue("");

        pstmt = con.prepareStatement(sql.toSqlString());
        sql.setParameter(pstmt);
        rs = pstmt.executeQuery();

        RsvSisGrpModel mdl = null;
        while (rs.next()) {
            mdl = new RsvSisGrpModel();
            mdl.setRsgSid(rs.getInt("RSG_SID"));
            ret.add(mdl);
        }

        return ret;
    }

    /**
     * <br>[機  能] 既存のグループIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return 既存のグループID一覧
     * @throws SQLException SQL実行例外
     */
    private List<String> __getExistGrpId() throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        List<String> ret = new ArrayList<String>();
        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RSG_ID");
            sql.addSql(" from");
            sql.addSql("   RSV_SIS_GRP");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                ret.add(rs.getString("RSG_ID"));
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
     * <br>[機  能] 施設グループの編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param grpList 施設グループ一覧
     * @throws SQLException SQL実行例外
     */
    private void __updateRsvGrp(List<RsvSisGrpModel> grpList) throws SQLException {

        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();

        try {
            //SQLの作成
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RSV_SIS_GRP");
            sql.addSql(" set");
            sql.addSql("   RSG_ID = ?");
            sql.addSql(" where");
            sql.addSql("   RSG_SID = ?");
            pstmt = con.prepareStatement(sql.toSqlString());

            //updateの実行
            for (RsvSisGrpModel grpMdl : grpList) {
                sql.addStrValue(grpMdl.getRsgId());
                sql.addIntValue(grpMdl.getRsgSid());
                sql.setParameter(pstmt);
                
                pstmt.executeUpdate();
                sql.clearValue();
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
}
