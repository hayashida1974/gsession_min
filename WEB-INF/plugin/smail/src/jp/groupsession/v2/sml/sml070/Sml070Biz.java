package jp.groupsession.v2.sml.sml070;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDateUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.dao.SmailDao;
import jp.groupsession.v2.sml.model.SmailModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ショートメール一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml070Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml070Biz.class);

    /**
     * <br>[機  能] セッションユーザSIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl リクエスト
     * @return sessionUsrSid セッションユーザSID
     */
    private int __getSessionUserSid(RequestModel reqMdl) {

        log__.debug("セッションユーザSID取得");

        int sessionUsrSid = -1;

        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        if (usModel != null) {
            sessionUsrSid = usModel.getUsrsid();
        }

        return sessionUsrSid;
    }

    /**
     * <br>[機  能] メッセンジャーデータセット
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitDataJusin(Sml070ParamModel paramMdl,
                                  RequestModel reqMdl,
                                  Connection con)
        throws SQLException {

        log__.debug("初期表示データ取得");

        SmailDao sDao = new SmailDao(con);

        int sessionUsrSid = __getSessionUserSid(reqMdl);

        //データ取得
        ArrayList<SmailModel> resultList =
            sDao.selectJmeisList(sessionUsrSid);

        //取得データを表示形式に変換
        ArrayList<SmailModel> ret = __convertJmeisData(resultList);

        paramMdl.setSml070SmlList(ret);
    }

    /**
     * <br>[機  能] 取得結果を変換する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramList 取得結果リスト
     * @return ret 変換後リスト
     */
    private ArrayList<SmailModel> __convertJmeisData(ArrayList<SmailModel> paramList) {

        ArrayList<SmailModel> ret = new ArrayList<SmailModel>();

        for (SmailModel paramMdl : paramList) {
            SmailModel retMdl = new SmailModel();
            retMdl.setSmlSid(paramMdl.getSmlSid());
            retMdl.setSmsMark(paramMdl.getSmsMark());
            retMdl.setSmsTitle(NullDefault.getString(paramMdl.getSmsTitle(), ""));
            if (paramMdl.getSmsSdate() != null) {
                String strSdate =
                    UDateUtil.getSlashYYMD(paramMdl.getSmsSdate())
                    + "  "
                    + UDateUtil.getSeparateHMS(paramMdl.getSmsSdate());
                retMdl.setStrSdate(strSdate);
            }
            retMdl.setUsrJkbn(paramMdl.getUsrJkbn());
            retMdl.setUsrUkoFlg(paramMdl.getUsrUkoFlg());
            retMdl.setUsrSid(paramMdl.getUsrSid());
            retMdl.setUsiSei(NullDefault.getString(paramMdl.getUsiSei(), ""));
            retMdl.setUsiMei(NullDefault.getString(paramMdl.getUsiMei(), ""));
            ret.add(retMdl);
        }

        return ret;
    }
}