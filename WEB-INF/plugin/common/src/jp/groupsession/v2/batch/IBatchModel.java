package jp.groupsession.v2.batch;

import jp.groupsession.v2.cmn.GSContext;
import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] 日次バッチ起動時に使用する情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class IBatchModel extends AbstractModel {

    /** GSコンテキスト */
    private GSContext gsContext__ = null;
    /** ドメイン */
    private String domain__ = null;
    /**
     * <p>domain を取得します。
     * @return domain
     */
    public String getDomain() {
        return domain__;
    }
    /**
     * <p>domain をセットします。
     * @param domain domain
     */
    public void setDomain(String domain) {
        domain__ = domain;
    }
    /**
     * <p>gsContext を取得します。
     * @return gsContext
     */
    public GSContext getGsContext() {
        return gsContext__;
    }
    /**
     * <p>gsContext をセットします。
     * @param gsContext gsContext
     */
    public void setGsContext(GSContext gsContext) {
        gsContext__ = gsContext;
    }



 }
