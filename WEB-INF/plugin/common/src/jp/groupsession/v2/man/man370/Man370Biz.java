package jp.groupsession.v2.man.man370;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnLangDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrLangDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnLangModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrLangModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 言語変更画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man370Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man370Biz.class);

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param usrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Connection con, Man370ParamModel paramMdl, int usrSid)
    throws SQLException {
        log__.debug("START");

        CmnLangDao dao = new CmnLangDao(con);
        List<CmnLangModel> mdlList = new ArrayList<CmnLangModel>();
        mdlList = dao.select();
        paramMdl.setMan370LangList(mdlList);

        // cutCoutryの取得
        CmnUsrLangModel usrMdl = null;
        CmnUsrLangDao usrDao = new CmnUsrLangDao(con);
        usrMdl = usrDao.select(usrSid);

        if (usrMdl != null) {
            paramMdl.setMan370SelectLang(usrMdl.getCulCountry());
        }

        log__.debug("END");
    }

    /**
     * <br>[機  能] 設定された言語設定情報をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setLangSetting(Man370ParamModel paramMdl,
                            RequestModel reqMdl, int usrSid, Connection con) throws SQLException {

        UDate now = new UDate();
        CmnUsrLangModel mdl = new CmnUsrLangModel();
        boolean commitFlg = false;
        try {
            CmnUsrLangDao dao = new CmnUsrLangDao(con);
            mdl.setUsrSid(usrSid);
            mdl.setCulCountry(paramMdl.getMan370SelectLang());
            mdl.setCulAuid(usrSid);
            mdl.setCulAdate(now);
            mdl.setCulEuid(usrSid);
            mdl.setCulEdate(now);

            if (dao.update(mdl) == 0) {
                dao.insert(mdl);
            }

            con.commit();
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("", e);
            throw e;
        } finally {
            if (!commitFlg) {
                con.rollback();
            }
        }
    }
}