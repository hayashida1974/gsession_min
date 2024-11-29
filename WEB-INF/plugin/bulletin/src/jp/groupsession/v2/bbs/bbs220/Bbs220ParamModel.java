package jp.groupsession.v2.bbs.bbs220;

import java.util.List;

import jp.groupsession.v2.bbs.bbs060.Bbs060ParamModel;
import jp.groupsession.v2.bbs.model.BulletinSoukouModel;

/**
 * <br>[機  能] 掲示板 草稿一覧画面のパラメータモデル
 * <br>[解  説]
 * <br>[備  考]
 */
public class Bbs220ParamModel extends Bbs060ParamModel {

    /** 草稿一覧 */
    private List<BulletinSoukouModel> bbs220SoukouList__ = null;

    /** 選択草稿区分 */
    private int bbs220SelectType__ = -1;
    /** 選択草稿内容 */
    private BulletinSoukouModel bbs220SelectSoukoInfo__ = null;
    /** 選択草稿バイナリSID */
    private Long bbs220SelectBinSid__ = new Long(0);
    /** 投稿本文ファイルSID */
    private int bbs220BodyFileId__ = 0;
    /** 削除対象SID */
    private String[] bbs220delInfSid__;


    /**
     * <p>bbs220SoukouList を取得します。
     * @return bbs220SoukouList
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220SoukouList__
     */
    public List<BulletinSoukouModel> getBbs220SoukouList() {
        return bbs220SoukouList__;
    }

    /**
     * <p>bbs220SoukouList をセットします。
     * @param bbs220SoukouList bbs220SoukouList
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220SoukouList__
     */
    public void setBbs220SoukouList(List<BulletinSoukouModel> bbs220SoukouList) {
        bbs220SoukouList__ = bbs220SoukouList;
    }


    /**
     * <p>bbs220SelectType を取得します。
     * @return bbs220SelectType
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220SelectType__
     */
    public int getBbs220SelectType() {
        return bbs220SelectType__;
    }

    /**
     * <p>bbs220SelectType をセットします。
     * @param bbs220SelectType bbs220SelectType
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220SelectType__
     */
    public void setBbs220SelectType(int bbs220SelectType) {
        bbs220SelectType__ = bbs220SelectType;
    }

    /**
     * <p>bbs220SelectSoukoInfo を取得します。
     * @return bbs220SelectSoukoInfo
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220SelectSoukoInfo__
     */
    public BulletinSoukouModel getBbs220SelectSoukoInfo() {
        return bbs220SelectSoukoInfo__;
    }

    /**
     * <p>bbs220SelectSoukoInfo をセットします。
     * @param bbs220SelectSoukoInfo bbs220SelectSoukoInfo
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220SelectSoukoInfo__
     */
    public void setBbs220SelectSoukoInfo(
            BulletinSoukouModel bbs220SelectSoukoInfo) {
        bbs220SelectSoukoInfo__ = bbs220SelectSoukoInfo;
    }

    /**
     * <p>bbs220SelectBinSid を取得します。
     * @return bbs220SelectBinSid
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220SelectBinSid__
     */
    public Long getBbs220SelectBinSid() {
        return bbs220SelectBinSid__;
    }

    /**
     * <p>bbs220SelectBinSid をセットします。
     * @param bbs220SelectBinSid bbs220SelectBinSid
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220SelectBinSid__
     */
    public void setBbs220SelectBinSid(Long bbs220SelectBinSid) {
        bbs220SelectBinSid__ = bbs220SelectBinSid;
    }

    /**
     * <p>bbs220BodyFileId を取得します。
     * @return bbs220BodyFileId
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220BodyFileId__
     */
    public int getBbs220BodyFileId() {
        return bbs220BodyFileId__;
    }

    /**
     * <p>bbs220BodyFileId をセットします。
     * @param bbs220BodyFileId bbs220BodyFileId
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220BodyFileId__
     */
    public void setBbs220BodyFileId(int bbs220BodyFileId) {
        bbs220BodyFileId__ = bbs220BodyFileId;
    }

    /**
     * <p>bbs220delInfSid を取得します。
     * @return bbs220delInfSid
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220delInfSid__
     */
    public String[] getBbs220delInfSid() {
        return bbs220delInfSid__;
    }

    /**
     * <p>bbs220delInfSid をセットします。
     * @param bbs220delInfSid bbs220delInfSid
     * @see jp.groupsession.v2.bbs.bbs220.Bbs220ParamModel#bbs220delInfSid__
     */
    public void setBbs220delInfSid(String[] bbs220delInfSid) {
        bbs220delInfSid__ = bbs220delInfSid;
    }


}