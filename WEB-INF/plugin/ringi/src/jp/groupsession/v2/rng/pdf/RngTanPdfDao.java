package jp.groupsession.v2.rng.pdf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.dao.AbstractDao;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONException;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormBuilder;
import jp.groupsession.v2.cmn.formbuilder.FormCell;
import jp.groupsession.v2.cmn.formmodel.AbstractFormModel;
import jp.groupsession.v2.cmn.formmodel.Block;
import jp.groupsession.v2.cmn.formmodel.BlockList;
import jp.groupsession.v2.cmn.formmodel.Calc;
import jp.groupsession.v2.cmn.formmodel.CheckBox;
import jp.groupsession.v2.cmn.formmodel.Comment;
import jp.groupsession.v2.cmn.formmodel.NumberBox;
import jp.groupsession.v2.cmn.formmodel.Sum;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngFormBuildBiz;
import jp.groupsession.v2.rng.csv.RngCsvKeiroStepModel;
import jp.groupsession.v2.rng.csv.RngCsvPositionMemberModel;
import jp.groupsession.v2.rng.csv.RngCsvUserModel;



/**
 * <br>[機  能] 稟議検索結果からCSV出力する情報を取得するDaoクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngTanPdfDao extends AbstractDao {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngTanPdfDao.class);

    /**
     * <p>Default Constructor
     */
    public RngTanPdfDao() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public RngTanPdfDao(Connection con) {
        super(con);
    }

    /**
     * 稟議SID毎のテンプレートフォーム一覧取得
     *
     * @param reqMdl ResultSet
     * @param tplKey テンプレートSID
     * @param tplVer テンプレートバージョン
     * @return テンプレートフォーム一覧(キー:稟議SID)
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, ArrayList<RngTanPdfFormModel>> select(RequestModel reqMdl,
                                                                int tplKey, int tplVer)
                                                                        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, ArrayList<RngTanPdfFormModel>> ret =
                new HashMap<String, ArrayList<RngTanPdfFormModel>>();

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RTP_TITLE,");
            sql.addSql("   RTP_RNG_TITLE,");
            sql.addSql("   RTP_SORT,");
            sql.addSql("   RTP_AUID,");
            sql.addSql("   RTP_ADATE,");
            sql.addSql("   RTP_EUID,");
            sql.addSql("   RTP_EDATE,");
            sql.addSql("   RTC_SID,");
            sql.addSql("   RTP_VER, ");
            sql.addSql("   RTP_MAXVER_KBN, ");
            sql.addSql("   RTP_JKBN, ");
            sql.addSql("   RTP_IDFORMAT_SID, ");
            sql.addSql("   RTP_FORM, ");
            sql.addSql("   RCT_SID, ");
            sql.addSql("   RCT_USR_SID ");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE");
            sql.addSql(" where");

            StringBuffer strBuf = new StringBuffer();
            sql.addSql("   (RTP_SID, RTP_VER) in (");
            strBuf.append("(");
            strBuf.append(getRngTemplateKey(tplKey, tplVer));
            strBuf.append(")");
            sql.addSql("     " + strBuf.toString());
            sql.addSql("   )");
            sql.addSql(" order by RTP_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int rtpSid = rs.getInt("RTP_SID");
                int rtpVer = rs.getInt("RTP_VER");
                String key = getRngTemplateKey(rtpSid, rtpVer);
                ArrayList<RngTanPdfFormModel> list =
                     __convertRngTemplateFormFromString(reqMdl, rtpSid, rtpVer,
                                                        rs.getString("RTP_FORM"));
                ret.put(key, list);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        // 汎用稟議テンプレートが必要な場合、固定のフォームデータ追加
        String tpl = getRngTemplateKey(tplKey, tplVer);
        String cmnRtpKey = getRngTemplateKey(0, 0);
        if (tpl.contains(cmnRtpKey)) {
            RngFormBuildBiz rngFormBiz = new RngFormBuildBiz(reqMdl);
            FormCell cell = rngFormBiz.createHanyouRingiNaiyo("");
            RngTanPdfFormModel cmnMdl = __getRngTemplateModel(cell, null);

            ArrayList<RngTanPdfFormModel> list = new ArrayList<RngTanPdfFormModel>();
            list.add(cmnMdl);
            ret.put(cmnRtpKey, list);
        }
        return ret;
    }

    /**
     * 稟議SID毎のテンプレートフォーム一覧取得
     *
     * @param reqMdl ResultSet
     * @param tplKeyList テンプレートSID
     * @param tplVerList テンプレートバージョン
     * @return テンプレートフォーム一覧(キー:稟議SID)
     * @throws SQLException SQL実行例外
     */
    public HashMap<String, ArrayList<RngTanPdfFormModel>> selects(RequestModel reqMdl,
                                              ArrayList<Integer> tplKeyList,
                                              ArrayList<Integer> tplVerList)
                                                                        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<String, ArrayList<RngTanPdfFormModel>> ret =
                new HashMap<String, ArrayList<RngTanPdfFormModel>>();
        boolean firstFlg = false;
        int idx = 0;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RTP_SID,");
            sql.addSql("   RTP_TYPE,");
            sql.addSql("   USR_SID,");
            sql.addSql("   RTP_TITLE,");
            sql.addSql("   RTP_RNG_TITLE,");
            sql.addSql("   RTP_SORT,");
            sql.addSql("   RTP_AUID,");
            sql.addSql("   RTP_ADATE,");
            sql.addSql("   RTP_EUID,");
            sql.addSql("   RTP_EDATE,");
            sql.addSql("   RTC_SID,");
            sql.addSql("   RTP_VER, ");
            sql.addSql("   RTP_MAXVER_KBN, ");
            sql.addSql("   RTP_JKBN, ");
            sql.addSql("   RTP_IDFORMAT_SID, ");
            sql.addSql("   RTP_FORM, ");
            sql.addSql("   RCT_SID, ");
            sql.addSql("   RCT_USR_SID ");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE");
            sql.addSql(" where");

            idx = 0;
            for (int tplKey : tplKeyList) {
                if (firstFlg) {
                    sql.addSql(" or ");
                }
                sql.addSql("   (RTP_SID = ? ");
                sql.addIntValue(tplKey);
                sql.addSql(" and ");
                sql.addSql("   RTP_VER = ? )");
                sql.addIntValue(tplVerList.get(idx));
                firstFlg = true;
                idx++;
            }
            sql.addSql(" order by RTP_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int rtpSid = rs.getInt("RTP_SID");
                int rtpVer = rs.getInt("RTP_VER");
                String key = getRngTemplateKey(rtpSid, rtpVer);
                ArrayList<RngTanPdfFormModel> list =
                     __convertRngTemplateFormFromString(reqMdl, rtpSid, rtpVer,
                                                        rs.getString("RTP_FORM"));
                ret.put(key, list);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }

        // 汎用稟議テンプレートが必要な場合、固定のフォームデータ追加

        idx = 0;
        for (int tplKey : tplKeyList) {
            String tpl = getRngTemplateKey(tplKey, tplVerList.get(idx));
            String cmnRtpKey = getRngTemplateKey(0, 0);
            if (tpl.contains(cmnRtpKey)) {
                RngFormBuildBiz rngFormBiz = new RngFormBuildBiz(reqMdl);
                FormCell cell = rngFormBiz.createHanyouRingiNaiyo("");
                RngTanPdfFormModel cmnMdl = __getRngTemplateModel(cell, null);

                ArrayList<RngTanPdfFormModel> list = new ArrayList<RngTanPdfFormModel>();
                list.add(cmnMdl);
                ret.put(cmnRtpKey, list);
                break;
            }
            idx++;
        }
        return ret;
    }

    /**
     * 稟議テンプレートフォーム(JSON文字列)をモデルデータへ変換
     *
     * @param reqMdl ResultSet
     * @param rtpSid 稟議テンプレートSID
     * @param rtpVer 稟議テンプレートバージョン
     * @param formStr フォームデータ(JSON文字列)
     * @return created RngTemplateFormModel
     * @throws SQLException SQL実行例外
     */
    private ArrayList<RngTanPdfFormModel> __convertRngTemplateFormFromString(
            RequestModel reqMdl, int rtpSid, int rtpVer, String formStr)
                    throws SQLException {
        ArrayList<RngTanPdfFormModel> ret = new ArrayList<RngTanPdfFormModel>();
        JSONArray jsonArr = null;
        if (formStr != null) {
            try {
                jsonArr = JSONArray.fromObject(formStr);
            } catch (JSONException je) {
            }
        }

        if (jsonArr != null) {
            FormBuilder builder = new FormBuilder();
            builder.setFormTable((JSON) jsonArr);

            List<List<FormCell>> formTable = builder.getFormTable();
            if (formTable != null) {
                int sort = 0;
                for (List<FormCell> forms : formTable) {
                    for (FormCell cell : forms) {
                        EnumFormModelKbn rngType = cell.getType();

                        if (rngType == EnumFormModelKbn.block) {
                            // ブロックの場合
                            ArrayList<RngTanPdfFormModel> list =
                                    __getRngTemplateFormBlock(builder, cell);
                            if (list != null) {
                                for (RngTanPdfFormModel mdl : list) {
                                    mdl.setRtpSid(rtpSid);
                                    mdl.setRtpSid(rtpVer);
                                    mdl.setSort(sort);
                                    mdl.setParentSid(cell.getSid());
                                    mdl.setParentType(rngType.getValue());
                                    ret.add(mdl);
                                }
                            }
                        } else if (rngType == EnumFormModelKbn.blocklist) {
                            // 表の場合
                            ArrayList<RngTanPdfFormModel> list =
                                    __getRngTemplateFormTable(builder, cell);
                            if (list != null) {
                                for (RngTanPdfFormModel mdl : list) {
                                    mdl.setRtpSid(rtpSid);
                                    mdl.setRtpSid(rtpVer);
                                    mdl.setSort(sort);
                                    mdl.setParentSid(cell.getSid());
                                    mdl.setParentType(rngType.getValue());
                                    ret.add(mdl);
                                }
                            }
                        } else if (rngType != null) {
                            // その他の場合
                            RngTanPdfFormModel mdl = __getRngTemplateModel(cell, null);
                            if (mdl != null) {
                                mdl.setRtpSid(rtpSid);
                                mdl.setRtpSid(rtpVer);
                                mdl.setSort(sort);
                                mdl.setParentType(rngType.getValue());
                                ret.add(mdl);
                            }
                        }
                        sort++;
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 汎用フォーム要素からモデルデータ生成
     *
     * @param cell フォーム要素
     * @param title 表題(Block, BlockList の場合のみ)
     * @return created RngTanPdfFormModel
     * @throws SQLException SQL実行例外
     */
    private RngTanPdfFormModel __getRngTemplateModel(FormCell cell, String title) {
        String titleStr = NullDefault.getString(cell.getTitle(), "");

        int rngType = (cell.getType() != null ? cell.getType().getValue() : -1);

        RngTanPdfFormModel ret = new RngTanPdfFormModel();
        ret.setRtfSid(cell.getSid());
        ret.setRtfId(cell.getFormID());
        ret.setRtfTitle(titleStr);
        ret.setRtfType(rngType);
        ret.setParentTitle(title);
        String body = "";
        AbstractFormModel bodyObj = cell.getBody();
        if (rngType == EnumFormModelKbn.label.getValue()) {
            body =  ((Comment) bodyObj).getValue();
            int notitle =  ((Comment) bodyObj).getNotitle();
            if (notitle == 1) {
                ret.setCommentValign(((Comment) bodyObj).getValign());
            }
        } else if (rngType == EnumFormModelKbn.textbox.getValue()) {
        } else if (rngType == EnumFormModelKbn.textarea.getValue()) {
        } else if (rngType == EnumFormModelKbn.date.getValue()) {
        } else if (rngType == EnumFormModelKbn.number.getValue()) {
            body = ((NumberBox) bodyObj).getTanni(); // 単位をセット
        } else if (rngType == EnumFormModelKbn.radio.getValue()) {
        } else if (rngType == EnumFormModelKbn.combo.getValue()) {
        } else if (rngType == EnumFormModelKbn.check.getValue()) {
            List<String> list = ((CheckBox) bodyObj).getDefaultValue();
            StringBuffer strBuf = new StringBuffer();
            if (list != null) {
                for (String str : list) {
                    if (strBuf.length() > 0) {
                        strBuf.append(",");
                    }
                    strBuf.append(str);
                }
            }
            body = strBuf.toString();
        } else if (rngType == EnumFormModelKbn.sum.getValue()) {
            body =  ((Sum) bodyObj).getTanni(); // 単位をセット
        } else if (rngType == EnumFormModelKbn.calc.getValue()) {
            body =  ((Calc) bodyObj).getTanni(); // 単位をセット
        } else if (rngType == EnumFormModelKbn.user.getValue()) {
            body = null; // ((SimpleUserSelect) bodyObj).getDefaultValue();
        } else if (rngType == EnumFormModelKbn.group.getValue()) {
            body = null; // ((GroupComboModel) bodyObj).getDefaultValue();
        }
        ret.setRtfBody(body);

        return ret;
    }

    /**
     * Blockフォーム要素からモデルデータ一覧を生成
     *
     * @param builder FormBuilder
     * @param cell フォーム要素
     * @return created RngTanPdfFormModel List
     * @throws SQLException SQL実行例外
     */
    private ArrayList<RngTanPdfFormModel> __getRngTemplateFormBlock(FormBuilder builder,
                                                                   FormCell cell) {

        ArrayList<RngTanPdfFormModel> ret = new ArrayList<RngTanPdfFormModel>();

        String title = NullDefault.getString(cell.getTitle(), "");
        if (cell.getBody() != null) {
            List<List<FormCell>> formTable = ((Block) cell.getBody()).getFormTable();
            if (formTable != null) {
                for (List<FormCell> forms : formTable) {
                    for (FormCell form : forms) {
                        RngTanPdfFormModel mdl = __getRngTemplateModel(form, title);
                        if (mdl != null) {
                            ret.add(mdl);
                        }
                    }
                }
            }
        }

        return ret;
    }

    /**
     * BlockListフォーム要素からモデルデータ一覧を生成
     *
     * @param builder FormBuilder
     * @param cell フォーム要素
     * @return created RngTanPdfFormModel List
     * @throws SQLException SQL実行例外
     */
    private ArrayList<RngTanPdfFormModel> __getRngTemplateFormTable(FormBuilder builder,
                                                                   FormCell cell) {

        ArrayList<RngTanPdfFormModel> ret = new ArrayList<RngTanPdfFormModel>();

        String title = NullDefault.getString(cell.getTitle(), "");
        if (cell.getBody() != null) {
            Block header = ((BlockList) cell.getBody()).getHeader();
            if (header.getFormTable() != null) {
                for (List<FormCell> forms : header.getFormTable()) {
                    for (FormCell form : forms) {
                        RngTanPdfFormModel mdl = __getRngTemplateModel(form, title);
                        if (mdl != null) {
                            mdl.setTableRow(0);
                            ret.add(mdl);
                        }
                    }
                }
            }

            List<Block> bodyList = ((BlockList) cell.getBody()).getBodyList();
            if (bodyList != null && bodyList.size() > 0) {
                for (Block body : bodyList) {
                    if (body.getFormTable() != null) {
                        for (List<FormCell> forms : body.getFormTable()) {
                            for (FormCell form : forms) {
                                RngTanPdfFormModel mdl = __getRngTemplateModel(form, title);
                                if (mdl != null) {
                                    mdl.setTableRow(1);
                                    mdl.setListBodyFlg(true);
                                    ret.add(mdl);
                                }
                            }
                        }
                    }
                }
            }

            Block footer = ((BlockList) cell.getBody()).getFooter();
            if (footer.getFormTable() != null) {
                for (List<FormCell> forms : footer.getFormTable()) {
                    for (FormCell form : forms) {
                        RngTanPdfFormModel mdl = __getRngTemplateModel(form, title);
                        if (mdl != null) {
                            mdl.setTableRow(2);
                            mdl.setListFooterFlg(true);
                            ret.add(mdl);
                        }
                    }
                }
            }
        }

        return ret;
    }

    /**
     * 稟議SID＋稟議バージョンから検索キーを生成
     * @param rtpSid 稟議SID
     * @param rtpVer 稟議バージョン
     * @return 検索キー
     */
    public String getRngTemplateKey(int rtpSid, int rtpVer) {
        return String.valueOf(rtpSid) + "," + String.valueOf(rtpVer);
    }

    /**
     * <br>[機  能] 経路ステップ情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSidList 稟議SID一覧
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public ArrayList<RngCsvKeiroStepModel> keiroSelect(List<Integer> rngSidList)
    throws SQLException {

        PreparedStatement pstmt = null;
        ArrayList<RngCsvKeiroStepModel>  ret =
                new ArrayList<RngCsvKeiroStepModel>();
        if (rngSidList.size() <= 0) {
            return ret;
        }
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        try {

            StringBuffer strBuf = new StringBuffer();
            for (Integer rngSid : rngSidList) {
                if (strBuf.length() > 0) {
                    strBuf.append(",");
                }
                strBuf.append(rngSid.intValue());
            }

            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID as RKS_SID,");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID as RNG_SID,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SORT as RKS_SORT,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_STATUS as RKS_STATUS,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_ROLL_TYPE as RKS_ROLL_TYPE,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_RCVDATE as RKS_RCVDATE,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_CHKDATE as RKS_CHKDATE,");
            sql.addSql("   RNG_KEIROSTEP_SELECT.USR_SID as USR_SID,");
            sql.addSql("   RNG_KEIROSTEP_SELECT.GRP_SID as GRP_SID,");
            sql.addSql("   RNG_KEIROSTEP_SELECT.POS_SID as POS_SID,");
            sql.addSql("   (case RKS_ROLL_TYPE");
            sql.addSql("     when " + RngConst.RNG_RNCTYPE_APPL + " then 0");
            sql.addSql("     else 1");
            sql.addSql("     end");
            sql.addSql("   ) as RKS_ROLL_SORT,");
            sql.addSql("   -1 as USR_SID_KOETU,");
            sql.addSql("   -1 as USR_SID_DAIRI,");
            sql.addSql("    0 as RKS_TYPE");
            sql.addSql(" from ");
            sql.addSql("   RNG_KEIROSTEP_SELECT");
            sql.addSql(" left join ");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" on RNG_KEIROSTEP_SELECT.RKS_SID = RNG_KEIRO_STEP.RKS_SID ");
            sql.addSql(" where");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID in (");
            sql.addSql("     " + strBuf.toString());
            sql.addSql("   )");

            sql.addSql(" UNION ALL");
            sql.addSql(" select");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SID as RKS_SID,");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID as RNG_SID,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_SORT as RKS_SORT,");
            sql.addSql("   RNG_SINGI.RSS_STATUS as RKS_STATUS,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_ROLL_TYPE as RKS_ROLL_TYPE,");
            sql.addSql("   RNG_KEIRO_STEP.RKS_RCVDATE as RKS_RCVDATE,");
            sql.addSql("   RNG_SINGI.RSS_CHKDATE as RKS_CHKDATE,");
            sql.addSql("   RNG_SINGI.USR_SID as USR_SID,");
            sql.addSql("   (case RKS_ROLL_TYPE");
            sql.addSql("     when " + RngConst.RNG_RNCTYPE_APPL + " then 0");
            sql.addSql("     else 1");
            sql.addSql("     end");
            sql.addSql("   ) as RKS_ROLL_SORT,");
            sql.addSql("   -1 as GRP_SID,");
            sql.addSql("   -1 as POS_SID,");
            sql.addSql("   RNG_SINGI.USR_SID_KOETU as USR_SID_KOETU,");
            sql.addSql("   RNG_SINGI.USR_SID_DAIRI as USR_SID_DAIRI,");
            sql.addSql("    1 as RKS_TYPE");
            sql.addSql(" from ");
            sql.addSql("   RNG_SINGI");
            sql.addSql(" left join ");
            sql.addSql("   RNG_KEIRO_STEP");
            sql.addSql(" on RNG_SINGI.RKS_SID = RNG_KEIRO_STEP.RKS_SID ");
            sql.addSql(" where");
            sql.addSql("   RNG_KEIRO_STEP.RNG_SID in (");
            sql.addSql("     " + strBuf.toString());
            sql.addSql("   )");

            // 経路種別順([申請者]は最優先) > ソート番号
            sql.addSql(" order by");
            sql.addSql("   RNG_SID,");
            sql.addSql("   RKS_TYPE,");
            sql.addSql("   RKS_ROLL_SORT,");
            sql.addSql("   RKS_ROLL_TYPE,");
            sql.addSql("   RKS_SORT");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            ArrayList<Integer> grpSids = new ArrayList<Integer>(); // グループSID一覧
            ArrayList<Integer> posSids = new ArrayList<Integer>(); // 役職SID一覧
            ArrayList<Integer> usrSids = new ArrayList<Integer>(); // ユーザSID一覧
            while (rs.next()) {
                RngCsvKeiroStepModel mdl = new RngCsvKeiroStepModel();
                mdl.setRksSid(rs.getInt("RKS_SID"));
                mdl.setRngSid(rs.getInt("RNG_SID"));
                mdl.setRksSort(rs.getInt("RKS_SORT"));
                mdl.setRksStatus(rs.getInt("RKS_STATUS"));
                mdl.setRksRollType(rs.getInt("RKS_ROLL_TYPE"));
                mdl.setRksRcvdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKS_RCVDATE")));
                mdl.setRksChkdate(UDate.getInstanceTimestamp(rs.getTimestamp("RKS_CHKDATE")));

                int rksType = rs.getInt("RKS_TYPE");
                mdl.setRksType(rksType);
                if (rksType > 0) {
                    // 審議データ
                    int usrSid    = rs.getInt("USR_SID");
                    int kouetuSid = rs.getInt("USR_SID_KOETU");
                    int dairiSid  = rs.getInt("USR_SID_DAIRI");
                    if (kouetuSid > 0 && usrSids.contains(Integer.valueOf(kouetuSid))) {
                        usrSids.add(Integer.valueOf(kouetuSid));
                    }
                    if (dairiSid > 0 && usrSids.contains(Integer.valueOf(dairiSid))) {
                        usrSids.add(Integer.valueOf(dairiSid));
                    }
                    mdl.setUsrSid(usrSid);
                    mdl.setDairiUsrSid(dairiSid);
                    mdl.setKouetuUsrSid(kouetuSid);
                } else {
                    // 経路ステップ選択ユーザデータ
                    int usrSid    = rs.getInt("USR_SID");
                    int grpSid    = rs.getInt("GRP_SID");
                    int posSid    = rs.getInt("POS_SID");
                    if (grpSid >= 0 && !grpSids.contains(Integer.valueOf(grpSid))) {
                        grpSids.add(Integer.valueOf(grpSid));
                    }
                    if (posSid >= 0 && !posSids.contains(Integer.valueOf(posSid))) {
                        posSids.add(Integer.valueOf(posSid));
                    }
                    if (grpSid < 0 && posSid < 0 && usrSid > 0
                     && usrSids.contains(Integer.valueOf(usrSid))) {
                        usrSids.add(Integer.valueOf(usrSid));
                    }
                    mdl.setUsrSid(usrSid);
                    mdl.setGrpSid(grpSid);
                    mdl.setPosSid(posSid);
                }
                ret.add(mdl);
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 経路ステップ情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param grpSidList グループSID一覧
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public HashMap<Integer, String> getGroupNameMap(List<Integer> grpSidList)
    throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        HashMap<Integer, String> ret = new HashMap<Integer, String>();

        if (grpSidList == null || grpSidList.size() == 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   GRP_SID,");
            sql.addSql("   GRP_NAME");
            sql.addSql(" from ");
            sql.addSql("   CMN_GROUPM");
            sql.addSql(" where");
            sql.addSql("   GRP_SID in (");
            StringBuffer strBuf = new StringBuffer();
            for (Integer grpSid : grpSidList) {
                if (strBuf.length() > 0) {
                    strBuf.append(",");
                }
                strBuf.append(grpSid.intValue());
            }
            sql.addSql("     " + strBuf.toString());
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer key  = Integer.valueOf(rs.getInt("GRP_SID"));
                String  name = rs.getString("GRP_NAME");
                ret.put(key, name);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 経路ステップ情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param posSidList 役職SID一覧
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public HashMap<Integer, RngCsvPositionMemberModel> getPositionMember(List<Integer> posSidList)
    throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        HashMap<Integer, RngCsvPositionMemberModel> ret =
                new HashMap<Integer, RngCsvPositionMemberModel>();

        if (posSidList == null || posSidList.size() == 0) {
            return ret;
        }

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   CMN_USRM_INF.POS_SID as POS_SID,");
            sql.addSql("   CMN_USRM_INF.USR_SID as USR_SID,");
            sql.addSql("   CMN_USRM_INF.USI_SEI as USI_SEI,");
            sql.addSql("   CMN_USRM_INF.USI_MEI as USI_MEI,");
            sql.addSql("   CMN_POSITION.POS_NAME as POS_NAME");
            sql.addSql(" from ");
            sql.addSql("   CMN_USRM_INF");
            sql.addSql(" left join ");
            sql.addSql("   CMN_POSITION");
            sql.addSql(" on CMN_USRM_INF.POS_SID = CMN_POSITION.POS_SID ");
            sql.addSql(" where");
            sql.addSql("   CMN_USRM_INF.POS_SID in (");
            StringBuffer strBuf = new StringBuffer();
            for (Integer grpSid : posSidList) {
                if (strBuf.length() > 0) {
                    strBuf.append(",");
                }
                strBuf.append(grpSid.intValue());
            }
            sql.addSql("     " + strBuf.toString());
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Integer key = Integer.valueOf(rs.getInt("POS_SID"));
                RngCsvPositionMemberModel mdl = ret.get(key);
                if (mdl == null) {
                    mdl = new RngCsvPositionMemberModel();
                    mdl.setSid(rs.getInt("POS_SID"));
                    mdl.setName(rs.getString("POS_NAME"));
                }
                RngCsvUserModel usrMdl = new RngCsvUserModel();
                usrMdl.setSid(rs.getInt("USR_SID"));
                usrMdl.setName(rs.getString("USI_SEI") + " " + rs.getString("USI_MEI"));
                usrMdl.setPos(rs.getInt("POS_SID"));
                mdl.addUsrList(usrMdl);
                ret.put(key, mdl);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * <br>[機  能] 経路ステップ情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param fileBinSidArray 添付ファイルバイナリSIDリスト
     * @throws SQLException SQL実行例外
     * @return count 更新件数
     */
    public HashMap<Integer, String> getFileNameList(List<Integer> fileBinSidArray)
    throws SQLException {
        PreparedStatement pstmt = null;
        Connection con = null;
        con = getCon();
        ResultSet rs = null;

        HashMap<Integer, String> ret =
                new HashMap<Integer, String>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();

            sql.addSql(" select");
            sql.addSql("   BIN_SID,");
            sql.addSql("   BIN_FILE_NAME");
            sql.addSql(" from ");
            sql.addSql("   CMN_BINF");
            sql.addSql(" where");
            boolean firstFlg = false;
            for (int fileBinSid : fileBinSidArray) {
                if (firstFlg) {
                    sql.addSql(" or ");
                }
                sql.addSql("   BIN_SID = ? ");
                sql.addIntValue(fileBinSid);
                firstFlg = true;
            }

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                ret.put(Integer.valueOf(rs.getString("BIN_SID")), rs.getString("BIN_FILE_NAME"));
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }

    /**
     * 稟議SID毎のテンプレート内表要素のボディ行取得
     *
     * @param rngSidList 稟議SIDリスト
     * @return 表要素内ボディ行数データ(キー:稟議SID)
     * @throws SQLException SQL実行例外
     */
    public HashMap<Integer, HashMap<Integer, Integer>> getBodyMap(
                                              ArrayList<Integer> rngSidList)
                                                                        throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Connection con = null;
        HashMap<Integer, HashMap<Integer, Integer>> ret =
                new HashMap<Integer, HashMap<Integer, Integer>>();
        HashMap<Integer, Integer> bodyMap = new HashMap<Integer, Integer>();
        boolean firstFlg = false;

        con = getCon();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select");
            sql.addSql("   RNG_FORMDATA.RNG_SID RNG_SID,");
            sql.addSql("   RNG_FORMDATA.RFD_SID RFD_SID,");
            sql.addSql("   RNG_FORMDATA.RFD_VALUE RFD_VALUE");
            sql.addSql(" from");
            sql.addSql("   RNG_TEMPLATE_FORM,");
            sql.addSql("   RNG_FORMDATA,");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" where");
            sql.addSql("   RNG_TEMPLATE_FORM.RTP_SID = RNG_RNDATA.RTP_SID");
            sql.addSql(" and ");
            sql.addSql("   RNG_TEMPLATE_FORM.RTP_VER = RNG_RNDATA.RTP_VER");
            sql.addSql(" and ");
            sql.addSql("   RNG_RNDATA.RNG_SID = RNG_FORMDATA.RNG_SID");
            sql.addSql(" and ");
            sql.addSql("   RNG_TEMPLATE_FORM.RTF_SID = RNG_FORMDATA.RFD_SID");
            sql.addSql(" and ");
            sql.addSql("   RNG_TEMPLATE_FORM.RTF_TYPE = 14");
            sql.addSql(" and (");
            for (int rngSid : rngSidList) {
                if (firstFlg) {
                    sql.addSql("   or ");
                } else {
                    firstFlg = true;
                }
                sql.addSql("   RNG_FORMDATA.RNG_SID = ? ");
                sql.addIntValue(rngSid);
            }
            sql.addSql("   )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            sql.setParameter(pstmt);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                bodyMap = new HashMap<Integer, Integer>();
                if (ret.containsKey(rs.getInt("RNG_SID"))) {
                    bodyMap = ret.get(rs.getInt("RNG_SID"));
                }
                bodyMap.put(rs.getInt("RFD_SID"), rs.getInt("RFD_VALUE"));
                ret.put(rs.getInt("RNG_SID"), bodyMap);
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