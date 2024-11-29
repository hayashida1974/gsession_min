package jp.groupsession.v2.man.man070kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.encryption.EncryptionException;
import jp.groupsession.v2.cmn.dao.base.CmnContmDao;
import jp.groupsession.v2.cmn.dao.base.CmnProxyAddressDao;
import jp.groupsession.v2.cmn.model.CmnContmModel;
import jp.groupsession.v2.cmn.model.base.CmnProxyAddressModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.usr.GSPassword;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] メイン 管理者設定 プロキシサーバ設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man070knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man070knBiz.class);

    /**
     * <br>[機  能] 初期表示情報の設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setInitData(Man070knParamModel paramMdl) {
        paramMdl.setMan070knViewNoProxyAddress(paramMdl.getNoProxyAddressList());
    }

    /**
     * <br>[機  能] 確定ボタン押下時処理
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * @throws EncryptionException パスワードの暗号化に失敗
     */
    public void updateProxyData(Man070knParamModel paramMdl, Connection con)
    throws SQLException, EncryptionException {

        log__.debug("プロキシサーバ設定情報更新");

        CmnContmModel param = __setCmnContmModel(paramMdl);
        CmnContmDao dao = new CmnContmDao(con);
        if (dao.updateProxyData(param) < 1) {
            dao.insertProxyData(param);
        }

        //プロキシ設定_アドレス
        CmnProxyAddressDao pxyAddressDao = new CmnProxyAddressDao(con);
        pxyAddressDao.deleteAll();
        if (param.getCntPxyAdrkbn() == GSConstMain.PROXY_SERVER_ADRKBN_EXISTADDRESS) {

            String[] addressList = paramMdl.getNoProxyAddressList();
            for (int cxaNo = 1; cxaNo <= addressList.length; cxaNo++) {
                CmnProxyAddressModel pxyAddressData = new CmnProxyAddressModel();
                pxyAddressData.setCxaAddress(addressList[cxaNo - 1].trim());
                pxyAddressData.setCxaNo(cxaNo);
                pxyAddressDao.insert(pxyAddressData);
            }
        }
    }

    /**
     * <br>[機  能] DB更新処理モデルの設定
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @return ret モデル
     * @throws EncryptionException パスワードの暗号化に失敗
     */
    private CmnContmModel __setCmnContmModel(Man070knParamModel paramMdl)
    throws EncryptionException {

        CmnContmModel ret = new CmnContmModel();

        ret.setCntPxyUse(paramMdl.getMan070pxyUseKbn());
        ret.setCntPxyUrl(NullDefault.getStringZeroLength(paramMdl.getMan070address(), ""));

        String proxyPort =
            NullDefault.getStringZeroLength(paramMdl.getMan070portnum(), "-1");
        ret.setCntPxyPort(Integer.parseInt(proxyPort));

        if (paramMdl.getMan070pxyUseKbn() == jp.groupsession.v2.man.GSConstMain.PROXY_SERVER_USE) {

            //ユーザ認証
            if (paramMdl.getMan070Auth() == Man070knForm.MAN070_AUTH_USE) {
                ret.setCntPxyAuth(GSConstMain.PROXY_SERVER_USERAUTH_AUTH);
                ret.setCntPxyAuthId(paramMdl.getMan070AuthUser());
                ret.setCntPxyAuthPass(
                        GSPassword.getEncryPassword(paramMdl.getMan070AuthPassword()));
            } else {
                ret.setCntPxyAuth(GSConstMain.PROXY_SERVER_USERAUTH_NOSET);
                ret.setCntPxyAuthId(null);
                ret.setCntPxyAuthPass(null);
            }

            //プロキシサーバを使用しないアドレス
            String proxyAddress
                = NullDefault.getString(paramMdl.getMan070NoProxyAddress(), "").trim();
            if (proxyAddress.length() == 0) {
                ret.setCntPxyAdrkbn(GSConstMain.PROXY_SERVER_ADRKBN_NOSET);
            } else {
                ret.setCntPxyAdrkbn(GSConstMain.PROXY_SERVER_ADRKBN_EXISTADDRESS);
            }

        }

        return ret;
    }
}