package jp.groupsession.v2.man.man200kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.base.CmnPswdConfDao;
import jp.groupsession.v2.cmn.model.base.CmnPswdConfModel;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 パスワードルール設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man200knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man200knBiz.class);
    /** コネクション */
    private Connection con__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param con コネクション
     */
    public Man200knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] パスワードルールの更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usid ユーザID
     * @throws SQLException SQL実行時例外
     */
    public void insert(Man200knParamModel paramMdl, int usid) throws SQLException {

        UDate now = new UDate();
        boolean commitFlg = false;

        //セッション保持時間の設定の更新を行う
        try {

            con__.setAutoCommit(false);
            CmnPswdConfDao dao = new CmnPswdConfDao(con__);
            CmnPswdConfModel model = new CmnPswdConfModel();
            model.setPwcDigit(paramMdl.getMan200Digit());
            model.setPwcCoe(paramMdl.getMan200CoeKbn());
            model.setPwcUppercase(paramMdl.getMan200UppercaseKbn());

            if (paramMdl.getMan200LimitKbn() == GSConstMain.PWC_LIMITKBN_OFF) {
                model.setPwcLimitDay(-1);
            } else {
                model.setPwcLimitDay(Integer.parseInt(paramMdl.getMan200LimitDay()));
            }

            model.setPwcUidPswd(paramMdl.getMan200UidPswdKbn());
            model.setPwcOldPswd(paramMdl.getMan200OldPswdKbn());
            model.setPwcAuid(usid);
            model.setPwcAdate(now);
            model.setPwcEuid(usid);
            model.setPwcEdate(now);
            dao.insert(model);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] パスワードルールの更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param usid ユーザID
     * @throws SQLException SQL実行時例外
     */
    public void update(Man200knParamModel paramMdl, int usid) throws SQLException {

        UDate now = new UDate();
        boolean commitFlg = false;

        //セッション保持時間の設定の更新を行う
        try {
            con__.setAutoCommit(false);
            CmnPswdConfDao dao = new CmnPswdConfDao(con__);
            CmnPswdConfModel model = new CmnPswdConfModel();
            model.setPwcDigit(paramMdl.getMan200Digit());
            model.setPwcCoe(paramMdl.getMan200CoeKbn());
            model.setPwcUppercase(paramMdl.getMan200UppercaseKbn());

            if (paramMdl.getMan200LimitKbn() == GSConstMain.PWC_LIMITKBN_OFF) {
                model.setPwcLimitDay(-1);
            } else {
                model.setPwcLimitDay(Integer.parseInt(paramMdl.getMan200LimitDay()));
            }

            model.setPwcUidPswd(paramMdl.getMan200UidPswdKbn());
            model.setPwcOldPswd(paramMdl.getMan200OldPswdKbn());
            model.setPwcEuid(usid);
            model.setPwcEdate(now);

            dao.update(model);
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con__.commit();
            } else {
                JDBCUtil.rollback(con__);
            }
        }
    }
}