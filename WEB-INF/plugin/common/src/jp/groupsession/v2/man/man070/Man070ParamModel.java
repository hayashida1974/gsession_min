package jp.groupsession.v2.man.man070;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;

/**
 * <br>[機  能] メイン 管理者設定 プロキシサーバ設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man070ParamModel extends AbstractParamModel {
    /** プロキシサーバ使用区分 */
    private int man070pxyUseKbn__ = GSConstMain.PROXY_SERVER_NOT_USE;
    /** プロキシサーバアドレス */
    private String man070address__;
    /** プロキシサーバ使用ポート番号 */
    private String man070portnum__;

    /** プロキシサーバ ユーザ認証 区分 */
    private int man070Auth__ = 0;
    /** プロキシサーバ ユーザ認証 ユーザ */
    private String man070AuthUser__ = null;
    /** プロキシサーバ ユーザ認証 パスワード */
    private String man070AuthPassword__ = null;

    /** プロキシサーバ プロキシサーバを使用しないアドレス */
    private String man070NoProxyAddress__ = null;

    /**
     * <p>man070address__ を取得します。
     * @return man070address
     */
    public String getMan070address() {
        return man070address__;
    }
    /**
     * <p>man070address__ をセットします。
     * @param man070address man070address__
     */
    public void setMan070address(String man070address) {
        man070address__ = man070address;
    }
    /**
     * <p>man070portnum__ を取得します。
     * @return man070portnum
     */
    public String getMan070portnum() {
        return man070portnum__;
    }
    /**
     * <p>man070portnum__ をセットします。
     * @param man070portnum man070portnum__
     */
    public void setMan070portnum(String man070portnum) {
        man070portnum__ = man070portnum;
    }
    /**
     * <p>man070pxyUseKbn__ を取得します。
     * @return man070pxyUseKbn
     */
    public int getMan070pxyUseKbn() {
        return man070pxyUseKbn__;
    }
    /**
     * <p>man070pxyUseKbn__ をセットします。
     * @param man070pxyUseKbn man070pxyUseKbn__
     */
    public void setMan070pxyUseKbn(int man070pxyUseKbn) {
        man070pxyUseKbn__ = man070pxyUseKbn;
    }

    /**
     * <p>man070Auth を取得します。
     * @return man070Auth
     */
    public int getMan070Auth() {
        return man070Auth__;
    }
    /**
     * <p>man070Auth をセットします。
     * @param man070Auth man070Auth
     */
    public void setMan070Auth(int man070Auth) {
        man070Auth__ = man070Auth;
    }

    /**
     * <p>man070AuthPassword を取得します。
     * @return man070AuthPassword
     */
    public String getMan070AuthPassword() {
        return man070AuthPassword__;
    }
    /**
     * <p>man070AuthPassword をセットします。
     * @param man070AuthPassword man070AuthPassword
     */
    public void setMan070AuthPassword(String man070AuthPassword) {
        man070AuthPassword__ = man070AuthPassword;
    }
    /**
     * <p>man070AuthUser を取得します。
     * @return man070AuthUser
     */
    public String getMan070AuthUser() {
        return man070AuthUser__;
    }
    /**
     * <p>man070AuthUser をセットします。
     * @param man070AuthUser man070AuthUser
     */
    public void setMan070AuthUser(String man070AuthUser) {
        man070AuthUser__ = man070AuthUser;
    }
    /**
     * <p>man070NoProxyAddress を取得します。
     * @return man070NoProxyAddress
     */
    public String getMan070NoProxyAddress() {
        return man070NoProxyAddress__;
    }
    /**
     * <p>man070NoProxyAddress をセットします。
     * @param man070NoProxyAddress man070NoProxyAddress
     */
    public void setMan070NoProxyAddress(String man070NoProxyAddress) {
        man070NoProxyAddress__ = man070NoProxyAddress;
    }

    /**
     * <br>[機  能] プロキシサーバを使用しないアドレスを分割して返す
     * <br>[解  説]
     * <br>[備  考]
     * @return プロキシサーバを使用しないアドレス
     */
    public String[] getNoProxyAddressList() {
        List<String> addressList = new ArrayList<String>();
        if (man070NoProxyAddress__ != null
        && man070NoProxyAddress__.trim().length() > 0) {

            StringTokenizer adrToken = new StringTokenizer(man070NoProxyAddress__, "\n");
            while (adrToken.hasMoreTokens()) {
                addressList.add(adrToken.nextToken().trim());
            }
        }

        return addressList.toArray(new String[addressList.size()]);
    }
}