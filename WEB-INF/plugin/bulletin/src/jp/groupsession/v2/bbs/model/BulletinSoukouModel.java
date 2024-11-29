package jp.groupsession.v2.bbs.model;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] 掲示板の草稿情報を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BulletinSoukouModel extends BbsSoukouModel {


    /** 草稿：フォーラム削除フラグ */
    private int forumDelFlg__ = -1;
    /** 草稿：スレッド削除フラグ */
    private int thredDelFlg__ = -1;
    /** 草稿：編集可能フラグ */
    private int editFlg__ = -1;
    /** 草稿：フォーラム名 */
    private String forumName__ = null;
    /** 草稿：スレッド名 */
    private String threadName__ = null;
    /** 草稿：作成日時 */
    private String strDate__ = null;
    /** 草稿:フォーラム画像バイナリSID */
    private Long imgBinSid__;

    /** 草稿：重要度区分 */
    private int juyoKbn__ = -1;
    /** 草稿：投稿者名 */
    private String postName__ = null;
    /** 草稿：投稿者削除区分 */
    private int postJKbn__ = -1;
    /** 草稿：投稿者有効区分 */
    private int postYukoKbn__ = -1;
    /** 草稿：内容区分 */
    private int contentKbn__ = -1;
    /** 草稿：内容 */
    private String content__ = null;
    /** 草稿：内容添付表示フラグ */
    private int contentFileFlg__ = -1;
    /** 草稿：掲示開始日*/
    private String limitFrDate__ = null;
    /** 草稿：掲示終了日*/
    private String limitToDate__ = null;

    /** 草稿：添付ファイル存在フラグ */
    private int fileFlg__ = -1;
    /** 草稿：添付ファイル情報一覧 */
    private List <CmnBinfModel> filesInfo__ = null;

    /**
     * <p>forumDelFlg を取得します。
     * @return forumDelFlg
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#forumDelFlg__
     */
    public int getForumDelFlg() {
        return forumDelFlg__;
    }
    /**
     * <p>forumDelFlg をセットします。
     * @param forumDelFlg forumDelFlg
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#forumDelFlg__
     */
    public void setForumDelFlg(int forumDelFlg) {
        forumDelFlg__ = forumDelFlg;
    }
    /**
     * <p>thredDelFlg を取得します。
     * @return thredDelFlg
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#thredDelFlg__
     */
    public int getThredDelFlg() {
        return thredDelFlg__;
    }
    /**
     * <p>thredDelFlg をセットします。
     * @param thredDelFlg thredDelFlg
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#thredDelFlg__
     */
    public void setThredDelFlg(int thredDelFlg) {
        thredDelFlg__ = thredDelFlg;
    }
    /**
     * <p>editFlg を取得します。
     * @return editFlg
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#editFlg__
     */
    public int getEditFlg() {
        return editFlg__;
    }
    /**
     * <p>editFlg をセットします。
     * @param editFlg editFlg
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#editFlg__
     */
    public void setEditFlg(int editFlg) {
        editFlg__ = editFlg;
    }
    /**
     * <p>forumName を取得します。
     * @return forumName
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#forumName__
     */
    public String getForumName() {
        return forumName__;
    }
    /**
     * <p>forumName をセットします。
     * @param forumName forumName
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#forumName__
     */
    public void setForumName(String forumName) {
        forumName__ = forumName;
    }
    /**
     * <p>threadName を取得します。
     * @return threadName
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#threadName__
     */
    public String getThreadName() {
        return threadName__;
    }
    /**
     * <p>threadName をセットします。
     * @param threadName threadName
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#threadName__
     */
    public void setThreadName(String threadName) {
        threadName__ = threadName;
    }
    /**
     * <p>strDate を取得します。
     * @return strDate
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#strDate__
     */
    public String getStrDate() {
        return strDate__;
    }
    /**
     * <p>strDate をセットします。
     * @param strDate strDate
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#strDate__
     */
    public void setStrDate(String strDate) {
        strDate__ = strDate;
    }
    /**
     * <p>postName を取得します。
     * @return postName
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#postName__
     */
    public String getPostName() {
        return postName__;
    }
    /**
     * <p>postName をセットします。
     * @param postName postName
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#postName__
     */
    public void setPostName(String postName) {
        postName__ = postName;
    }
    /**
     * <p>postJKbn を取得します。
     * @return postJKbn
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#postJKbn__
     */
    public int getPostJKbn() {
        return postJKbn__;
    }
    /**
     * <p>postJKbn をセットします。
     * @param postJKbn postJKbn
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#postJKbn__
     */
    public void setPostJKbn(int postJKbn) {
        postJKbn__ = postJKbn;
    }
    /**
     * <p>postYukoKbn を取得します。
     * @return postYukoKbn
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#postYukoKbn__
     */
    public int getPostYukoKbn() {
        return postYukoKbn__;
    }
    /**
     * <p>postYukoKbn をセットします。
     * @param postYukoKbn postYukoKbn
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#postYukoKbn__
     */
    public void setPostYukoKbn(int postYukoKbn) {
        postYukoKbn__ = postYukoKbn;
    }
    /**
     * <p>filesInfo を取得します。
     * @return filesInfo
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#filesInfo__
     */
    public List<CmnBinfModel> getFilesInfo() {
        return filesInfo__;
    }
    /**
     * <p>filesInfo をセットします。
     * @param filesInfo filesInfo
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#filesInfo__
     */
    public void setFilesInfo(List<CmnBinfModel> filesInfo) {
        filesInfo__ = filesInfo;
    }
    /**
     * <p>content を取得します。
     * @return content
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#content__
     */
    public String getContent() {
        return content__;
    }
    /**
     * <p>content をセットします。
     * @param content content
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#content__
     */
    public void setContent(String content) {
        content__ = content;
    }
    /**
     * <p>contentKbn を取得します。
     * @return contentKbn
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#contentKbn__
     */
    public int getContentKbn() {
        return contentKbn__;
    }
    /**
     * <p>contentKbn をセットします。
     * @param contentKbn contentKbn
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#contentKbn__
     */
    public void setContentKbn(int contentKbn) {
        contentKbn__ = contentKbn;
    }
    /**
     * <p>imgBinSid を取得します。
     * @return imgBinSid
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#imgBinSid__
     */
    public Long getImgBinSid() {
        return imgBinSid__;
    }
    /**
     * <p>imgBinSid をセットします。
     * @param imgBinSid imgBinSid
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#imgBinSid__
     */
    public void setImgBinSid(Long imgBinSid) {
        imgBinSid__ = imgBinSid;
    }
    /**
     * <p>limitFrDate を取得します。
     * @return limitFrDate
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#limitFrDate__
     */
    public String getLimitFrDate() {
        return limitFrDate__;
    }
    /**
     * <p>limitFrDate をセットします。
     * @param limitFrDate limitFrDate
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#limitFrDate__
     */
    public void setLimitFrDate(String limitFrDate) {
        limitFrDate__ = limitFrDate;
    }
    /**
     * <p>limitToDate を取得します。
     * @return limitToDate
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#limitToDate__
     */
    public String getLimitToDate() {
        return limitToDate__;
    }
    /**
     * <p>limitToDate をセットします。
     * @param limitToDate limitToDate
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#limitToDate__
     */
    public void setLimitToDate(String limitToDate) {
        limitToDate__ = limitToDate;
    }
    /**
     * <p>juyoKbn を取得します。
     * @return juyoKbn
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#juyoKbn__
     */
    public int getJuyoKbn() {
        return juyoKbn__;
    }
    /**
     * <p>juyoKbn をセットします。
     * @param juyoKbn juyoKbn
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#juyoKbn__
     */
    public void setJuyoKbn(int juyoKbn) {
        juyoKbn__ = juyoKbn;
    }
    /**
     * <p>fileFlg を取得します。
     * @return fileFlg
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#fileFlg__
     */
    public int getFileFlg() {
        return fileFlg__;
    }
    /**
     * <p>fileFlg をセットします。
     * @param fileFlg fileFlg
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#fileFlg__
     */
    public void setFileFlg(int fileFlg) {
        fileFlg__ = fileFlg;
    }
    /**
     * <p>contentFileFlg を取得します。
     * @return contentFileFlg
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#contentFileFlg__
     */
    public int getContentFileFlg() {
        return contentFileFlg__;
    }
    /**
     * <p>contentFileFlg をセットします。
     * @param contentFileFlg contentFileFlg
     * @see jp.groupsession.v2.bbs.model.BulletinSoukouModel#contentFileFlg__
     */
    public void setContentFileFlg(int contentFileFlg) {
        contentFileFlg__ = contentFileFlg;
    }



}
