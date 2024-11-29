package jp.groupsession.v2.rng.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RngIdDao;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.rng210.Rng210ListModel;
/**
 *
 * <br>[機  能] 稟議 申請ID用採番コントローラー
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public final class RngIdSaibanControler {
    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(RngIdSaibanControler.class);

    /**稟議IDモデル*/
    private RingiIdModel idMdl__;
    /**稟議IDフォーマット*/
    private ArrayList<Rng210ListModel> list__;
    /**コネクション*/
    private Connection con__;
    /**リクエストモデル*/
    private RequestModel reqMdl__;
    /** シングルトン制御オブジェクト */
    private static final Object LOCK__ = new Object();
    /**管理者設定*/
    RngAconfModel aconf__;
    /**
     *
     * コンストラクタ
     */
    private RngIdSaibanControler() {

    }
    /**
     *
     * <br>[機  能] インスタンス生成
     * <br>[解  説]
     * <br>[備  考]
     * @param idMdl 稟議IDモデル
     * @param list 稟議IDフォーマット
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param aconfMdl 管理者設定
     * @return 申請ID採番コントローラ
     */
    public static RngIdSaibanControler getInstance(RingiIdModel idMdl,
            ArrayList<Rng210ListModel> list,
            Connection con,
            RequestModel reqMdl,
            RngAconfModel aconfMdl) {
        RngIdSaibanControler ret = new RngIdSaibanControler();
        ret.idMdl__ = idMdl;
        ret.list__ = list;
        ret.con__ = con;
        ret.aconf__ = aconfMdl;
        ret.reqMdl__ = reqMdl;

        return ret;
    }

    /**
     *
     * <br>[機  能] 稟議申請ID を生成
     * <br>[解  説] 更新時はシングルトンで動作する
     * <br>[備  考]
     * @param rngid  入力された申請ID(手動入力時のみ使用)
     * @param isUpdate 申請ID 更新フラグ(自動入力時のみ使用)
     * @param banRngSid 重複チェックで除外する稟議SID(自動入力時のみ使用)
     * @return 発行された稟議ID登録
     * @throws SQLException SQL実行時例外
     */
    public String createAutoRngId(String rngid,
            boolean isUpdate,
            int banRngSid) throws SQLException  {
        String ret = null;
        if (isUpdate) {
            synchronized (LOCK__) {
                Connection con = null;
                boolean succsess = false;
                try {
                    //
                    DataSource ds =
                            GroupSession.getResourceManager().getDataSource(reqMdl__.getDomain());
                    con = GroupSession.getConnection(
                            reqMdl__.getDomain(),
                            1000);
                    con.setAutoCommit(false);
                    //最新の申請IDフォーマット取得
                    RngIdDao rngidDao = new RngIdDao(con__);
                    idMdl__ =  rngidDao.selectData(idMdl__.getRngSid());
                    ret = __createAutoRngId(rngid, isUpdate, banRngSid, con);
                    con.commit();
                    succsess = true;
                } catch (SQLException sqle) {
                    throw sqle;
                } catch (Exception e) {
                    log__.error("データソース取得に失敗", e);
                    throw new SQLException(e);
                } finally {
                    if (con != null) {
                        if (!succsess) {
                            JDBCUtil.rollback(con);
                        }
                        JDBCUtil.closeConnection(con);
                    }
                    con = null;
                }
            }
        } else {
            ret = __createAutoRngId(rngid, isUpdate, banRngSid, con__);
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] 稟議申請ID を生成
     * <br>[解  説]
     * <br>[備  考]
     * @param rngid  入力された申請ID(手動入力時のみ使用)
     * @param isUpdate 申請ID 更新フラグ(自動入力時のみ使用)
     * @param banRngSid 重複チェックで除外する稟議SID(自動入力時のみ使用)
     * @param con コネクション
     * @return 発行された稟議ID登録
     * @throws SQLException SQL実行例外
     */
    private String __createAutoRngId(String rngid,
            boolean isUpdate,
            int banRngSid,
            Connection con
            ) throws SQLException {
        String ret = null;

        __rngIdReset();

        rngid = __formatRngId(list__, idMdl__, 0);
        RngIdDao rngIdDao = new RngIdDao(con);

        int offset = 0;
        // エラー終了
        if (StringUtil.isNullZeroString(rngid)) {
            ret = null;
        // 重複チェックなし
        } else if (aconf__.getRarOverlap() == RngConst.RAR_SINSEI_KYOKA) {
            offset = 1;
            ret = rngid;
        // 重複しない
        } else if (rngIdDao.getOverlapCount(rngid, banRngSid) == 0) {
            offset = 1;
            ret = rngid;
         // 稟議件数をチェック最大回数として、重複しない申請IDが見つかるまでチェック
        } else {
            int rngCount = rngIdDao.getOverlapCount(null, -1);
            for (int i = 1; i < rngCount; i++) {
                rngid = __formatRngId(list__, idMdl__, i);
                // エラー終了
                if (StringUtil.isNullZeroString(rngid)) {
                    break;
                } else if (rngIdDao.getOverlapCount(rngid, banRngSid) == 0) {
                    // 重複しないので申請IDを返す
                    ret = rngid;
                    offset = i;
                    break;
                }
            }
        }

        // 自動で申請IDが見つかったのでＤＢ更新
        if (isUpdate && !StringUtil.isNullZeroString(ret)) {
            int saiban = idMdl__.getRngInit() + offset; // 次の番号をセット
            RngIdDao rngidDao = new RngIdDao(con);
            rngidDao.idUpdate(saiban, idMdl__.getRngSid());
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] リセット設定に応じて申請IDオブジェクトの採番値の初期化を行う
     * <br>[解  説]
     * <br>[備  考]
     */
    private void __rngIdReset() {
        if (idMdl__.getRngReset() == RngConst.RAR_RESET_NONE) {
            return;
        }
        UDate udate = idMdl__.getRidUseDate().cloneUDate();
        UDate now = new UDate();
        //時間を合わせる
        udate.setZeroHhMmSs();
        now.setZeroHhMmSs();
        if (idMdl__.getRngReset() == RngConst.RAR_RESET_YEAR) {
            //月日を合わせる
            udate.setMonth(now.getMonth());
            udate.setDay(now.getIntDay());
        } else if (idMdl__.getRngReset() == RngConst.RAR_RESET_MONTH) {
            //日を合わせる
            udate.setDay(now.getIntDay());
        }
        if (udate.compare(udate, now) == UDate.LARGE) {
            idMdl__.setRngInit(1);
        }

    }
    /**
     * <br>[機  能] 稟議IDをフォーマットにしたがって生成
     * <br>[解  説]
     * <br>[備  考]
     * @param list     稟議申請IDフォーマット一覧
     * @param rngidMdl 稟議申請ID設定情報
     * @param offset   オフセット値
     * @return 稟議申請ID設定で指定された申請ID
     */
    private String __formatRngId(ArrayList<Rng210ListModel> list,
                                 RingiIdModel rngidMdl, int offset) {
        String ret = "";
        if (list != null && list.size() > 0) {
            UDate now = new UDate();
            StringBuilder sb = new StringBuilder();
            for (Rng210ListModel lMdl : list) {
                switch (lMdl.getRng210SelectFormat()) {
                    case "2":
                        int initNum = rngidMdl.getRngInit() + offset;
                        String no = String.valueOf(initNum);
                        if (rngidMdl.getRngZeroume() > 0) {
                            String format = "%0" + String.valueOf(rngidMdl.getRngZeroume()) + "d";
                            no = String.format(format, initNum);
                        }
                        sb.append(no);
                        break;
                    case "3":
                        sb.append(now.getStrYear());
                        break;
                    case "4":
                        sb.append(now.getStrYear().substring(2, 4));
                        break;
                    case "5":
                        sb.append(now.getStrMonth());
                        break;
                    case "6":
                        sb.append(now.getStrDay());
                        break;
                    default:
                        sb.append(lMdl.getRng210FormatWord());
                        break;
                }
            }
            ret = sb.toString();
        }
        return ret;
    }

}
