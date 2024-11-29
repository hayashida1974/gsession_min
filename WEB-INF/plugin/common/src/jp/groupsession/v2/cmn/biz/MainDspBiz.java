package jp.groupsession.v2.cmn.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.base.CmnMdispDao;
import jp.groupsession.v2.cmn.model.base.CmnMdispModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン画面表示設定に関するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class MainDspBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(MainDspBiz.class);


    /**
     * メイン画面表示設定一覧を取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return ArrayList メイン画面表示設定一覧
     */
    public ArrayList<CmnMdispModel> getMainDspConfList(int userSid, Connection con)
    throws SQLException {
        ArrayList<CmnMdispModel> ret = null;
        CmnMdispDao dao = new CmnMdispDao(con);
        ret = dao.select(userSid);
        log__.debug("メイン画面表示設定件数==>" + ret.size());
        if (ret.size() == 0) {
            //初期値で設定レコード作成
            ret = createInitMdispConf(userSid, userSid, con);
        }
        return ret;
    }

    /**
     * メイン画面表示設定一覧をMapで取得する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     * @return HashMap メイン画面表示設定Map
     */
    public HashMap<String, String> getMainDspConfMap(int userSid, Connection con)
    throws SQLException {
        ArrayList<CmnMdispModel> confs = getMainDspConfList(userSid, con);
        HashMap<String, String> map = new HashMap<String, String>();
        for (CmnMdispModel conf : confs) {
            if (conf.getMdpDsp() == GSConstCommon.MAIN_RELOAD) {
                map.put(conf.getMdpPid(), String.valueOf(conf.getMdpReload()));
            } else {
                map.put(conf.getMdpPid(), String.valueOf(conf.getMdpDsp()));
            }
        }
        return map;
    }

    /**
     * 初期値のメイン画面表示設定を登録し、設定内容を返します。
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid 作成する設定のユーザSID
     * @param creater 作成者
     * @param con コネクション
     * @return ArrayList 設定内容
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<CmnMdispModel> createInitMdispConf(int userSid, int creater, Connection con)
    throws SQLException {
        ArrayList<CmnMdispModel> ret = new ArrayList<CmnMdispModel>();
        UDate now = new UDate();
        CmnMdispDao dao = new CmnMdispDao(con);
        CmnMdispModel conf = null;
        for (String pid : GSConstCommon.MAIN_DSPVALUE) {
            if (pid.equals(GSConstCommon.MAIN_DSPVALUE_WEATHER)) {
                continue;
            }

            conf = new CmnMdispModel();
            conf.setUsrSid(userSid);
            conf.setMdpPid(pid);
            if (pid.equals(GSConstCommon.MAIN_DSPVALUE_AUTORELOAD)) {
                conf.setMdpDsp(GSConstCommon.MAIN_RELOAD);
                conf.setMdpReload(GSConstCommon.MAIN_DSPRELOAD);
            } else {
                conf.setMdpDsp(GSConstCommon.MAIN_DSP);
                conf.setMdpReload(GSConstCommon.MAIN_NOT_DSP);
            }
            conf.setMdpAuid(creater);
            conf.setMdpAdate(now);
            conf.setMdpEuid(creater);
            conf.setMdpEdate(now);
            dao.insert(conf);
            ret.add(conf);
        }
        return ret;
    }
}