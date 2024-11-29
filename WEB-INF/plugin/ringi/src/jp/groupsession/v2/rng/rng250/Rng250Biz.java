package jp.groupsession.v2.rng.rng250;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngUconfDao;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngUconfModel;

/**
 * <br>[機  能] 稟議個人ショートメール設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng250Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng250Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setInitData(Rng250ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {
        log__.debug("START");

        //稟議個人情報を取得
        RngBiz rngBiz = new RngBiz(con);
        RngUconfModel confMdl = rngBiz.getUConfData(con, userSid);

        paramMdl.setRng250smlNtf(confMdl.getRurSmlNtf());
        paramMdl.setRng250smlNtfJyusin(confMdl.getRurSmlJusin());
        paramMdl.setRng250smlNtfDairi(confMdl.getRurSmlDairi());

        RngAconfModel admMdl = rngBiz.getRngAconf(con);
        if (admMdl != null) {
            paramMdl.setRng250AdmSmlNtf(admMdl.getRarSmlNtf());
        } else {
            paramMdl.setRng250AdmSmlNtf(RngConst.RAR_SML_NTF_USER);
        }

        log__.debug("End");
    }

    /**
     * <br>[機  能] 稟議個人設定情報を更新する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     * @return addEditFlg 登録モード(0:登録 1:編集)
     */
    public int updateUserConf(Rng250ParamModel paramMdl, Connection con, int userSid)
    throws SQLException {
        log__.debug("START");

        int addEditFlg = 1;
        RngUconfModel confMdl = new RngUconfModel();
        confMdl.setUsrSid(userSid);
        confMdl.setRurSmlNtf(paramMdl.getRng250smlNtf());
        confMdl.setRurSmlJusin(paramMdl.getRng250smlNtfJyusin());
        confMdl.setRurSmlDairi(paramMdl.getRng250smlNtfDairi());
        confMdl.setRurViewCnt(RngConst.RNG_PAGE_VIEWCNT);

        RngUconfDao confDao = new RngUconfDao(con);
        if (confDao.updateSml(confMdl) == 0) {
            confDao.insert(confMdl);
            addEditFlg = 0;
        }

        log__.debug("End");
        return addEditFlg;
    }
}
