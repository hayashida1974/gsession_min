package jp.groupsession.v2.bbs.bbs070kn;

import java.util.List;

import jp.groupsession.v2.bbs.bbs070.Bbs070Form;

/**
 * <br>[機  能] 掲示板 スレッド登録確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs070knForm extends Bbs070Form {

    /** 投稿者(表示用) */
    private String bbs070knviewContributor__ = null;
    /** 投稿者有効区分 */
    private int bbs070UserYukoKbn__ = 0;
    /** 内容(表示用) */
    private String bbs070knviewvalue__ = null;
    /** 添付ファイル名一覧 */
    private List < String > bbs070knfilesName__ = null;
    /** 添付ファイルID */
    private String bbs070knTmpFileId__ = null;

    /**
     * <p>bbs070knviewContributor を取得します。
     * @return bbs070knviewContributor
     */
    public String getBbs070knviewContributor() {
        return bbs070knviewContributor__;
    }

    /**
     * <p>bbs070knviewContributor をセットします。
     * @param bbs070knviewContributor bbs070knviewContributor
     */
    public void setBbs070knviewContributor(String bbs070knviewContributor) {
        bbs070knviewContributor__ = bbs070knviewContributor;
    }

    /**
     * @return bbs070knviewvalue を戻します。
     */
    public String getBbs070knviewvalue() {
        return bbs070knviewvalue__;
    }

    /**
     * @param bbs070knviewvalue 設定する bbs070knviewvalue。
     */
    public void setBbs070knviewvalue(String bbs070knviewvalue) {
        bbs070knviewvalue__ = bbs070knviewvalue;
    }

    /**
     * <p>bbs070UserYukoKbn を取得します。
     * @return bbs070UserYukoKbn
     */
    public int getBbs070UserYukoKbn() {
        return bbs070UserYukoKbn__;
    }

    /**
     * <p>bbs070UserYukoKbn をセットします。
     * @param bbs070UserYukoKbn bbs070UserYukoKbn
     */
    public void setBbs070UserYukoKbn(int bbs070UserYukoKbn) {
        bbs070UserYukoKbn__ = bbs070UserYukoKbn;
    }

    /**
     * @return bbs070knfilesName を戻します。
     */
    public List < String > getBbs070knfilesName() {
        return bbs070knfilesName__;
    }

    /**
     * @param bbs070knfilesName 設定する bbs070knfilesName。
     */
    public void setBbs070knfilesName(List < String > bbs070knfilesName) {
        bbs070knfilesName__ = bbs070knfilesName;
    }

    /**
     * @return bbs070knTmpFileId を戻します。
     */
    public String getBbs070knTmpFileId() {
        return bbs070knTmpFileId__;
    }

    /**
     * @param bbs070knTmpFileId 設定する bbs070knTmpFileId。
     */
    public void setBbs070knTmpFileId(String bbs070knTmpFileId) {
        bbs070knTmpFileId__ = bbs070knTmpFileId;
    }
}
