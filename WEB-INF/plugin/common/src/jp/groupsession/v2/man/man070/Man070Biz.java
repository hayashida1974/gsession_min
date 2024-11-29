package jp.groupsession.v2.man.man070;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.encryption.EncryptionException;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnProxyAddressDao;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.usr.GSPassword;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 プロキシサーバ設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man070Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man070Biz.class);

    /** リクエスト */
    protected HttpServletRequest req_ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Man070Biz() { }


    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     */
    public Man070Biz(HttpServletRequest req) {
        req_ = req;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param form アクションフォーム
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws EncryptionException パスワードの復号化に失敗
     */
    public void setInitData(Man070Form form, Connection con)
    throws SQLException, EncryptionException {

        log__.debug("初期表示データセット");

        CmnContmDao contDao = new CmnContmDao(con);
        CmnContmModel ret = contDao.select();

        if (ret != null) {
            int pxyUseKbn = ret.getCntPxyUse();
            form.setMan070pxyUseKbn(ret.getCntPxyUse());

            //使用区分 = プロキシサーバを使用する
            if (pxyUseKbn == GSConstMain.PROXY_SERVER_USE) {
                form.setMan070address(ret.getCntPxyUrl());
                form.setMan070portnum(String.valueOf(ret.getCntPxyPort()));

                //プロキシサーバー ユーザ認証 = 使用する
                if (ret.getCntPxyAuth() == GSConstMain.PROXY_SERVER_USERAUTH_AUTH) {
                    form.setMan070Auth(Man070Form.MAN070_AUTH_USE);
                    form.setMan070AuthUser(ret.getCntPxyAuthId());
                    form.setMan070AuthPassword(
                            GSPassword.getDecryPassword(ret.getCntPxyAuthPass()));
                }

                //プロキシサーバを使用しないアドレス
                if (ret.getCntPxyAdrkbn() == GSConstMain.PROXY_SERVER_ADRKBN_EXISTADDRESS) {
                    CmnProxyAddressDao pxyAddressDao = new CmnProxyAddressDao(con);
                    String[] addressList = pxyAddressDao.getAddressList();
                    StringBuilder noProxyAddress = new StringBuilder();
                    for (int idx = 0; idx < addressList.length; idx++) {
                        if (idx > 0) {
                            noProxyAddress.append("\n");
                        }
                        noProxyAddress.append(addressList[idx]);
                    }

                    form.setMan070NoProxyAddress(noProxyAddress.toString());
                }
            }
        }
    }
}