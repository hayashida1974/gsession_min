package jp.groupsession.v2.usr.usr083kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnLabelUsrConfDao;
import jp.groupsession.v2.cmn.model.base.CmnLabelUsrConfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ユーザ情報 管理者設定 権限設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr083knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr083knBiz.class);

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr083knParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Usr083knParamModel paramMdl,
            Connection con) throws SQLException {

    }

    /**
     * <br>[機  能] 権限設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr083knParamModel
     * @param usrSid セッションユーザSID
     * @param con コネクション
     * @return CmnLabelUsrConfModel
     * @throws SQLException SQL実行エラー
     */
    public CmnLabelUsrConfModel setLabelUsrConfig(Usr083knParamModel paramMdl,
            int usrSid, Connection con) throws SQLException {

        CmnLabelUsrConfDao labelUconfDao = new CmnLabelUsrConfDao(con);
        CmnLabelUsrConfModel model = new CmnLabelUsrConfModel();
        boolean commitFlg = false;
        try {
            //DB存在確認
            model = labelUconfDao.select();
            //画面値セット
            CmnLabelUsrConfModel labelUconfMdl = createLabelUsrConfData(paramMdl, usrSid);
            int count = labelUconfDao.update(labelUconfMdl);
            if (count <= 0) {
                labelUconfDao.insert(labelUconfMdl);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("管理者ソート設定の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
        return model;
    }

    /**
     * <br>[機  能] 画面値設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr083knParamModel
     * @param usrSid ユーザSID
     * @return KhmSyaikbnModel 社員区分情報
     */
    public CmnLabelUsrConfModel createLabelUsrConfData(
            Usr083knParamModel paramMdl, int usrSid) {

        UDate nowDate = new UDate();
        CmnLabelUsrConfModel mdl = new CmnLabelUsrConfModel();

        mdl.setLufEdit(paramMdl.getUsr083Pow1());
        mdl.setLufSet(paramMdl.getUsr083Pow2());

        mdl.setLufAuid(usrSid);
        mdl.setLufAdate(nowDate);
        mdl.setLufEuid(usrSid);
        mdl.setLufEdate(nowDate);

        return mdl;
    }

}
