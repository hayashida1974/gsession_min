package jp.groupsession.v2.wml.restapi.model;

import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.restapi.response.annotation.ResponceModel;
import jp.groupsession.v2.wml.model.WmlDirectoryCountModel;

/**
 * <br>[機  能] WEBメールRESTAPI ディレクトリ情報保持クラス
 * <br>[解  説]
 * <br>[備  考]
 */
@ResponceModel(targetField = {
    "sid",
    "type",
    "name",
    "nonReadCountNum",
    "countNum"
    })
public class DirectoryArray {
    /** 含有モデル */
    private final WmlDirectoryCountModel base__;
    /** 全メール件数 */
    private int countNum__;

    /**
     *
     * コンストラクタ
     * @param base モデル
     */
    public DirectoryArray(WmlDirectoryCountModel base, int countNum) {
        base__ = base;
        countNum__ = countNum;
    }

    /** @return sid */
    public long getSid() {
        return base__.getId();
    }
    /** @return type */
    public String getType() {
        switch(base__.getType()) {
            case GSConstWebmail.DIR_TYPE_RECEIVE :
            return GSConstWebmail.RESTAPI_MAILBOX_INBOX;

            case GSConstWebmail.DIR_TYPE_SENDED :
            return GSConstWebmail.RESTAPI_MAILBOX_SENT;

            case GSConstWebmail.DIR_TYPE_NOSEND :
            return GSConstWebmail.RESTAPI_MAILBOX_FUTURE;

            case GSConstWebmail.DIR_TYPE_DRAFT :
            return GSConstWebmail.RESTAPI_MAILBOX_DRAFT;

            case GSConstWebmail.DIR_TYPE_DUST :
            return GSConstWebmail.RESTAPI_MAILBOX_TRASH;

            case GSConstWebmail.DIR_TYPE_STORAGE :
            return GSConstWebmail.RESTAPI_MAILBOX_KEEP;

            default : 
            return GSConstWebmail.RESTAPI_MAILBOX_INBOX;
        }
    }
    /** @return name */
    public String getName() {
        return base__.getName();
    }

    /** @return nonReadCountNum */
    public int getNonReadCountNum() {
        return (int) base__.getNoReadCount();
    }

    /** @return countNum */
    public int getCountNum() {
        return countNum__;
    }

    /**
     * <p>countNum を設定します。
     * @param countNum
     * @see jp.groupsession.v2.wml.restapi.accounts.configs.WmlAccountsConfigsResultModel.DirectoryArray#countNum__
     */
    public void setCountNum(int countNum) {
        countNum__ = countNum;
    }
}