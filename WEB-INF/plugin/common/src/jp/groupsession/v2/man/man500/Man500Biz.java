package jp.groupsession.v2.man.man500;

import java.sql.Connection;
import java.sql.SQLException;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.model.base.CmnPconfEditModel;

/**
 * <br>[機  能] メイン 管理者設定 個人情報編集権限設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man500Biz {

    /** リクエスモデル */
    public RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Man500Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットします。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Man500ParamModel
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Connection con, Man500ParamModel paramMdl)
        throws SQLException {
        MainCommonBiz manCmnBiz = new MainCommonBiz();

        if (paramMdl.getMan500init() == 0) {
            //初回表示時はDBより値を取得する
            CmnPconfEditModel pconfEditMdl = new CmnPconfEditModel();
            pconfEditMdl = manCmnBiz.getCpeConf(0, con);

            paramMdl.setMan500EditKbn(pconfEditMdl.getCpePconfKbn());
            paramMdl.setMan500PasswordKbn(pconfEditMdl.getCpePasswordKbn());
            paramMdl.setManPasswordKbn(pconfEditMdl.getCpePasswordKbn());
            paramMdl.setMan500OtpAddressKbn(pconfEditMdl.getCpeOtpsendKbn());
            paramMdl.setMan500init(1);
        }

        //パスワード有効期限設定有無チェック
        int passLimitFlg = manCmnBiz.checkPassLimit(con);

        //パスワード有効期限が設定されている時は、
        //有効期限が切れた後、パスワードを変更できるよう強制的に「各ユーザが設定」にするに設定する
        if (passLimitFlg == GSConstMain.PWC_LIMITKBN_ON) {
            paramMdl.setManPasswordLimitFlg(GSConstMain.PWC_LIMITKBN_ON);
            paramMdl.setMan500PasswordKbn(GSConstMain.PASSWORD_EDIT_USER);
        }


    }

}
