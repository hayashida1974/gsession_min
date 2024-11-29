package jp.groupsession.v2.rng.main;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.model.RingiDataModel;

/**
 * <br>[機  能] 稟議一覧(メイン画面表示用)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngMainBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngMainBiz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param form フォーム
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admin 管理者か否か true:管理者, false:一般ユーザ
     * @throws Exception 実行例外
     */
    public void setInitData(RngMainForm form, Connection con, int userSid, boolean admin)
    throws Exception {
        log__.debug("START");

        if (form == null) {
            form = new RngMainForm();
        }

        RingiDao dao = new RingiDao(con);

        //最大件数
        int ringiCnt = dao.getRingiDataCount(userSid, form.getRngProcMode());

        //稟議情報一覧設定
        List <RingiDataModel> rngList = dao.getRingiDataList(userSid, form.getRngProcMode(),
                                            form.getRng010sortKey(), form.getRng010orderKey(),
                                            -1, ringiCnt, -1);
        form.setRngDataList(rngList);

        log__.debug("End");
    }

}
