package jp.groupsession.v2.cmn.cmn240;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.ThemePathBiz;
import jp.groupsession.v2.cmn.dao.base.CmnUsrThemeDao;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] ニュース一覧(メイン)のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn240Biz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramModel パラメータ
     * @param con コネクション
     * @param userSid ユーザSID
     * @param reqMdl リクエスト
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Cmn240ParamModel paramModel, Connection con,
            int userSid, RequestModel reqMdl)
    throws SQLException {

        String serverUrl = "http://" + GSConst.BIZ_URL + "/news/";
        String bizUrl = CommonBiz.getBizUrl(reqMdl);
        if (bizUrl != null) {
            serverUrl = bizUrl + "news/";
        }

        paramModel.setCmn240newsUrl(serverUrl);
        UDate date = new UDate();
        paramModel.setCmn240nowTime(date.getDateString() + date.getStrHour());

        // テーマ反映用パラメータの取得
        int ctmSid = 0;
        CmnUsrThemeDao cmnDao = new CmnUsrThemeDao(con);
        ctmSid = cmnDao.getThemeSid(userSid);
        if (ctmSid == 0) {
            ctmSid = ThemePathBiz.DEFAULT_CTM_SID;
        }
        paramModel.setCmn240themeSid(ctmSid);
    }
}