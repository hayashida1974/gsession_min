package jp.groupsession.v2.rng.rng120;

import java.sql.Connection;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngUconfDao;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngUconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 稟議個人設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng120Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng120Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    public void setInitData(Rng120ParamModel paramMdl, Connection con, int userSid)
    throws Exception {
        log__.debug("START");

        //稟議個人情報を取得
        RngBiz rngBiz = new RngBiz(con);
        RngUconfModel confMdl = rngBiz.getUConfData(con, userSid);

        paramMdl.setRng120smlNtf(confMdl.getRurSmlNtf());
        paramMdl.setRng120ringiCnt(confMdl.getRurViewCnt());

        RngAconfModel admMdl = rngBiz.getRngAconf(con);
        if (admMdl != null) {
            paramMdl.setRng120AdmSmlNtf(admMdl.getRarSmlNtf());
        } else {
            paramMdl.setRng120AdmSmlNtf(RngConst.RAR_SML_NTF_USER);
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
    public int updateUserConf(Rng120ParamModel paramMdl, Connection con, int userSid)
    throws Exception {
        log__.debug("START");

        int addEditFlg = 1;
        RngUconfModel confMdl = new RngUconfModel();
        confMdl.setUsrSid(userSid);
        confMdl.setRurSmlNtf(paramMdl.getRng120smlNtf());
        confMdl.setRurViewCnt(__getInputValue(paramMdl.getRng120ringiCnt(),
                                            Rng120ParamModel.RINGICNTVALUE,
                                            RngConst.RNG_PAGE_VIEWCNT));

        RngUconfDao confDao = new RngUconfDao(con);
        if (confDao.update(confMdl) == 0) {
            confDao.insert(confMdl);
            addEditFlg = 0;
        }

        log__.debug("End");
        return addEditFlg;
    }

    /**
     * <br>[機  能] 入力値のチェックを行う
     * <br>[解  説] 入力値が選択項目一覧に含まれない値の場合、初期値を返す
     * <br>[備  考]
     * @param value 入力値
     * @param labels 選択項目一覧
     * @param initValue 初期値
     * @return 値
     */
    private int __getInputValue(int value, String[] labels, int initValue) {
        int ret = initValue;

        for (String label : labels) {
            if (Integer.parseInt(label) == value) {
                ret = value;
                break;
            }
        }

        return ret;
    }

}
