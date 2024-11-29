package jp.groupsession.v2.bbs.bbs090kn;

import jp.groupsession.v2.bbs.bbs090.Bbs090ParamModel;

/**
 * <br>[機  能] 掲示板 投稿登録確認画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs090knParamModel extends Bbs090ParamModel {
    /** 投稿者(表示用) */
    private String bbs090knviewContributor__ = null;
    /** 内容(表示用) */
    private String bbs090knviewvalue__ = null;
    /** 添付ファイルID */
    private String bbs090knTmpFileId__ = null;

    /**
     * <p>bbs090knviewContributor を取得します。
     * @return bbs090knviewContributor
     */
    public String getBbs090knviewContributor() {
        return bbs090knviewContributor__;
    }
    /**
     * <p>bbs090knviewContributor をセットします。
     * @param bbs090knviewContributor bbs090knviewContributor
     */
    public void setBbs090knviewContributor(String bbs090knviewContributor) {
        bbs090knviewContributor__ = bbs090knviewContributor;
    }
    /**
     * @return bbs090knviewvalue を戻します。
     */
    public String getBbs090knviewvalue() {
        return bbs090knviewvalue__;
    }
    /**
     * @param bbs090knviewvalue 設定する bbs090knviewvalue。
     */
    public void setBbs090knviewvalue(String bbs090knviewvalue) {
        bbs090knviewvalue__ = bbs090knviewvalue;
    }
    /**
     * @return bbs090knTmpFileId を戻します。
     */
    public String getBbs090knTmpFileId() {
        return bbs090knTmpFileId__;
    }
    /**
     * @param bbs090knTmpFileId 設定する bbs090knTmpFileId。
     */
    public void setBbs090knTmpFileId(String bbs090knTmpFileId) {
        bbs090knTmpFileId__ = bbs090knTmpFileId;
    }
}