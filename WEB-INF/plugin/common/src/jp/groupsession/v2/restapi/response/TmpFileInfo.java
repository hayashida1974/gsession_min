package jp.groupsession.v2.restapi.response;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

public class TmpFileInfo {
    /** 含有モデル*/
    private final CmnBinfModel base__;
    /**
     *
     * コンストラクタ
     * @param base モデル
     */
    public TmpFileInfo(CmnBinfModel base) {
        base__ = base;
    }
    /** @return binSid */
    public long getBinSid() {
        return base__.getBinSid();
    }
    /** @return fileName */
    public String getFileName() {
        return base__.getBinFileName();
    }
    /** @return fileSizeByteNum */
    public long getFileSizeByteNum() {
        return base__.getBinFileSize();
    }
}