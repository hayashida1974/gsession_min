package jp.groupsession.v2.man.man500kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.dao.base.CmnPconfEditDao;
import jp.groupsession.v2.man.model.base.CmnPconfEditModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 個人情報編集確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man500knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man500knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * コンストラクタ
     * @param con Connection
     * @param reqMdl RequestModel
     */
    public Man500knBiz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws Exception 実行例外
     */
    public void setData(Man500knParamModel paramMdl) throws Exception {

        boolean commitFlg = false;

        try {

            MainCommonBiz biz = new MainCommonBiz();
            CmnPconfEditDao cpeDao = new CmnPconfEditDao(con__);
            CmnPconfEditModel cpeMdl = null;
            cpeMdl = biz.getCpeConf(reqMdl__.getSmodel().getUsrsid(), con__);

            //個人情報編集権限設定
            cpeMdl.setCpePconfKbn(paramMdl.getMan500EditKbn());
            //パスワード編集権限設定
            cpeMdl.setCpePasswordKbn(paramMdl.getMan500PasswordKbn());
            //ワンタイムパスワード通知先アドレス
            cpeMdl.setCpeOtpsendKbn(paramMdl.getMan500OtpAddressKbn());

            //テーブルにデータが存在するかチェックする
            int count = cpeDao.selectCount();
            if (count > 0) {
                //更新処理
                cpeDao.update(cpeMdl);
            } else {
                //登録処理
                cpeDao.insert(cpeMdl);
            }
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
