package jp.groupsession.v2.man.man340kn;

import jp.groupsession.v2.man.man340.Man340Form;

/**
 * <br>[機  能] メイン 管理者設定 プラグイン追加確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man340knForm extends Man340Form {

    /** バイナリSID */
    private Long man340knBinSid__ = new Long(0);
    /**
     * <p>man340knBinSid を取得します。
     * @return man340knBinSid
     */
    public Long getMan340knBinSid() {
        return man340knBinSid__;
    }
    /**
     * <p>man340knBinSid をセットします。
     * @param man340knBinSid man340knBinSid
     */
    public void setMan340knBinSid(Long man340knBinSid) {
        man340knBinSid__ = man340knBinSid;
    }

}