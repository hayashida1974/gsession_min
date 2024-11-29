package jp.groupsession.v2.usr.usr011kn;

import jp.groupsession.v2.usr.usr011.Usr011Form;

/**
 * <br>[機  能] メイン 管理者設定 グループ登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr011knForm extends Usr011Form {

    /** グループ階層文字列 */
    private String usr011knGroupClassName__ = null;

    /**
     * @return usr011knGroupClassName を戻します。
     */
    public String getUsr011knGroupClassName() {
        return usr011knGroupClassName__;
    }

    /**
     * @param usr011knGroupClassName 設定する usr011knGroupClassName。
     */
    public void setUsr011knGroupClassName(String usr011knGroupClassName) {
        usr011knGroupClassName__ = usr011knGroupClassName;
    }
}