package jp.groupsession.v2.cmn.cmn260;

import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.cmn250.Cmn250ParamModel;

/**
 * <br>[機  能] OAuth認証情報登録画面のパラメータ情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn260ParamModel extends Cmn250ParamModel {
    /** プロバイダ名 */
    private String cmn260prvName__ = null;
    /** プロバイダ */
    private int cmn260provider__ = GSConstCommon.COU_PROVIDER_GOOGLE;
    /** クライアントID */
    private String cmn260cliendId__ = null;
    /** シークレットキー */
    private String cmn260secret__ = null;
    /** 備考 */
    private String cmn260biko__ = null;
    /** 初期表示区分 */
    private int cmn260initKbn__ = Cmn260Form.INIT_FLG;
    /** 認証情報リスト */
    private List<LabelValueBean> cmn260authList__ = null;

    /**
     * <p>cmn260prvName を取得します。
     * @return cmn260prvName
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260prvName__
     */
    public String getCmn260prvName() {
        return cmn260prvName__;
    }

    /**
     * <p>cmn260prvName をセットします。
     * @param cmn260prvName cmn260prvName
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260prvName__
     */
    public void setCmn260prvName(String cmn260prvName) {
        cmn260prvName__ = cmn260prvName;
    }
    /**
     * <p>cmn260provider を取得します。
     * @return cmn260provider
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260provider__
     */
    public int getCmn260provider() {
        return cmn260provider__;
    }
    /**
     * <p>cmn260provider をセットします。
     * @param cmn260provider cmn260provider
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260provider__
     */
    public void setCmn260provider(int cmn260provider) {
        cmn260provider__ = cmn260provider;
    }
    /**
     * <p>cmn260cliendId を取得します。
     * @return cmn260cliendId
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260cliendId__
     */
    public String getCmn260cliendId() {
        return cmn260cliendId__;
    }
    /**
     * <p>cmn260cliendId をセットします。
     * @param cmn260cliendId cmn260cliendId
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260cliendId__
     */
    public void setCmn260cliendId(String cmn260cliendId) {
        cmn260cliendId__ = cmn260cliendId;
    }
    /**
     * <p>cmn260secret を取得します。
     * @return cmn260secret
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260secret__
     */
    public String getCmn260secret() {
        return cmn260secret__;
    }
    /**
     * <p>cmn260secret をセットします。
     * @param cmn260secret cmn260secret
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260secret__
     */
    public void setCmn260secret(String cmn260secret) {
        cmn260secret__ = cmn260secret;
    }
    /**
     * <p>cmn260biko を取得します。
     * @return cmn260biko
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260biko__
     */
    public String getCmn260biko() {
        return cmn260biko__;
    }
    /**
     * <p>cmn260biko をセットします。
     * @param cmn260biko cmn260biko
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260biko__
     */
    public void setCmn260biko(String cmn260biko) {
        cmn260biko__ = cmn260biko;
    }
    /**
     * <p>cmn260initKbn を取得します。
     * @return cmn260initKbn
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260initKbn__
     */
    public int getCmn260initKbn() {
        return cmn260initKbn__;
    }
    /**
     * <p>cmn260initKbn をセットします。
     * @param cmn260initKbn cmn260initKbn
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260initKbn__
     */
    public void setCmn260initKbn(int cmn260initKbn) {
        cmn260initKbn__ = cmn260initKbn;
    }

    /**
     * <p>cmn260authList を取得します。
     * @return cmn260authList
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260authList__
     */
    public List<LabelValueBean> getCmn260authList() {
        return cmn260authList__;
    }

    /**
     * <p>cmn260authList をセットします。
     * @param cmn260authList cmn260authList
     * @see jp.groupsession.v2.cmn.cmn260.Cmn260Form#cmn260authList__
     */
    public void setCmn260authList(List<LabelValueBean> cmn260authList) {
        cmn260authList__ = cmn260authList;
    }
}