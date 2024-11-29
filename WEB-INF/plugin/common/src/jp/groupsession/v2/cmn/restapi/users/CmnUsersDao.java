package jp.groupsession.v2.cmn.restapi.users;

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
import jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel.GroupInfo;
import jp.groupsession.v2.cmn.restapi.users.CmnUsersResultModel.LabelInfo;

/**
 * <p>ユーザ個人情報に関するSQL実行を行う
 *
 */
public class CmnUsersDao extends AbstractDao {

    private static Log log__ = LogFactory.getLog(CmnUsersDao.class);

    /**
     * <p>Default Constructor
     */
    public CmnUsersDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public CmnUsersDao(Connection con) {
        super(con);
    }

    /**
     * <br>[機  能] 指定したユーザ情報に所属グループ、ラベル情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param userList ユーザ情報一覧
     * @return ユーザ情報一覧(所属グループ、ラベル設定済み)
     * @throws SQLException SQL実行例外
     */
    public ArrayList<CmnUsersResultModel> createUserQueryResultList(ArrayList<CmnUsersResultModel> userList)
    throws SQLException {

        if (userList == null || userList.isEmpty()) {
            return userList;
        }

        PreparedStatement groupPstmt = null;
        PreparedStatement labelPstmt = null;
        ResultSet rs = null;
        Connection con = null;
        con = getCon();

        try {
            //所属グループ取得用SQL
            SqlBuffer groupSql = new SqlBuffer();
            groupSql.addSql(" select ");
            groupSql.addSql("   CMN_GROUPM.GRP_ID as GRP_ID,");
            groupSql.addSql("   CMN_GROUPM.GRP_NAME as GRP_NAME");
            groupSql.addSql(" from ");
            groupSql.addSql("   CMN_GROUPM,");
            groupSql.addSql("   CMN_BELONGM");
            groupSql.addSql(" where ");
            groupSql.addSql("   CMN_BELONGM.USR_SID = ?");
            groupSql.addSql(" and ");
            groupSql.addSql("   CMN_GROUPM.GRP_SID = CMN_BELONGM.GRP_SID");
            groupPstmt = con.prepareStatement(groupSql.toSqlString());

            //ラベル情報取得用SQL
            SqlBuffer labelSql = new SqlBuffer();
            labelSql.addSql(" select ");
            labelSql.addSql("   CMN_LABEL_USR.LAB_SID as LAB_SID,");
            labelSql.addSql("   CMN_LABEL_USR.LAB_NAME as LAB_NAME,");
            labelSql.addSql("   CMN_LABEL_USR_CATEGORY.LUC_SID as LUC_SID,");
            labelSql.addSql("   CMN_LABEL_USR_CATEGORY.LUC_NAME as LUC_NAME");
            labelSql.addSql(" from ");
            labelSql.addSql("   CMN_LABEL_USR,");
            labelSql.addSql("   CMN_LABEL_USR_CATEGORY,");
            labelSql.addSql("   CMN_USRM_LABEL");
            labelSql.addSql(" where ");
            labelSql.addSql("   CMN_USRM_LABEL.USR_SID = ?");
            labelSql.addSql(" and ");
            labelSql.addSql("   CMN_USRM_LABEL.LAB_SID = CMN_LABEL_USR.LAB_SID");
            labelSql.addSql(" and ");
            labelSql.addSql("   CMN_LABEL_USR.LUC_SID = CMN_LABEL_USR_CATEGORY.LUC_SID");
            labelSql.addSql(" order by");
            labelSql.addSql("   CMN_LABEL_USR.LAB_SID");
            labelPstmt = con.prepareStatement(labelSql.toSqlString());

            for (int index = 0 ; index < userList.size(); index++) {

                int resultUserSid = userList.get(index).getUserSid();

                //グループ情報の取得
                groupSql.clearValue();
                groupPstmt.clearParameters();
                groupSql.addIntValue(resultUserSid);
                groupSql.setParameter(groupPstmt);
                log__.info(groupSql.toLogString());
                rs = groupPstmt.executeQuery();
                List<GroupInfo> groupList = new ArrayList<>();
                while (rs.next()) {
                    GroupInfo groupData = new GroupInfo();
                    groupData.setId(rs.getString("GRP_ID"));
                    groupData.setName(rs.getString("GRP_NAME"));
                    groupList.add(groupData);
                }
                userList.get(index).setGroupArray(groupList);
                JDBCUtil.closeResultSet(rs);

                //ラベル情報の取得
                labelSql.clearValue();
                labelPstmt.clearParameters();
                labelSql.addIntValue(resultUserSid);
                labelSql.setParameter(labelPstmt);
                log__.info(labelSql.toLogString());
                rs = labelPstmt.executeQuery();
                List<LabelInfo> labelList = new ArrayList<>();
                while (rs.next()) {
                    LabelInfo labelData = new LabelInfo();
                    labelData.setSid(rs.getInt("LAB_SID"));
                    labelData.setName(rs.getString("LAB_NAME"));
                    labelData.setCategorySid(rs.getInt("LUC_SID"));
                    labelData.setCategoryName(rs.getString("LUC_NAME"));
                    labelList.add(labelData);
                }
                userList.get(index).setLabelArray(labelList);
                JDBCUtil.closeResultSet(rs);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(groupPstmt);
            JDBCUtil.closeStatement(labelPstmt);
        }

        return userList;
    }

}