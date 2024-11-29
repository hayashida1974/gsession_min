package jp.groupsession.v2.rng.rng020kn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.groupsession.v2.rng.model.RingiChannelDataModel;
import jp.groupsession.v2.rng.rng020.Rng020Form;
import jp.groupsession.v2.rng.rng020.Rng020KeiroBlock;

/**
 * <br>[機  能] 稟議作成確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng020knForm extends Rng020Form {

    /** 添付ファイルSID */
    private String rng020BinSid__ = "";
    /** 添付ファイルディレクトリID */
    private String rng020BinDirId__ = "";

    /** 承認経路 ユーザ数 */
    private String rng020apprUserCnt__ = "";
    /** 最終確認 ユーザ数*/
    private String rng020confirmUserCnt__ = "";
    /** 経路情報一覧 */
    private List<RingiChannelDataModel> channelList__ = null;
    /** 経路情報一覧(確認) */
    private List<RingiChannelDataModel> confirmChannelList__ = null;
    /** 表示用内容 */
    private String rng020knContent__ = null;
    /** 最終確認設定 表示専用 */
    private Map<Integer, Rng020KeiroBlock> rng020kakuninKeiroDsp__
    = new HashMap<Integer, Rng020KeiroBlock>();


    /**
     * <p>rng020knContent を取得します。
     * @return rng020knContent
     */
    public String getRng020knContent() {
        return rng020knContent__;
    }

    /**
     * <p>rng020knContent をセットします。
     * @param rng020knContent rng020knContent
     */
    public void setRng020knContent(String rng020knContent) {
        rng020knContent__ = rng020knContent;
    }

    /**
     * <p>rng020BinSid を取得します。
     * @return rng020BinSid
     */
    public String getRng020BinSid() {
        return rng020BinSid__;
    }

    /**
     * <p>rng020BinSid をセットします。
     * @param rng020BinSid rng020BinSid
     */
    public void setRng020BinSid(String rng020BinSid) {
        rng020BinSid__ = rng020BinSid;
    }

    /**
     * <p>rng020BinDirId を取得します。
     * @return rng020BinDirId
     */
    public String getRng020BinDirId() {
        return rng020BinDirId__;
    }

    /**
     * <p>rng020BinDirId をセットします。
     * @param rng020BinDirId rng020BinDirId
     */
    public void setRng020BinDirId(String rng020BinDirId) {
        rng020BinDirId__ = rng020BinDirId;
    }

    /**
     * <p>rng020apprUserCnt を取得します。
     * @return rng020apprUserCnt
     */
    public String getRng020apprUserCnt() {
        return rng020apprUserCnt__;
    }

    /**
     * <p>rng020apprUserCnt をセットします。
     * @param rng020apprUserCnt rng020apprUserCnt
     */
    public void setRng020apprUserCnt(String rng020apprUserCnt) {
        rng020apprUserCnt__ = rng020apprUserCnt;
    }

    /**
     * <p>rng020confirmUserCnt を取得します。
     * @return rng020confirmUserCnt
     */
    public String getRng020confirmUserCnt() {
        return rng020confirmUserCnt__;
    }

    /**
     * <p>rng020confirmUserCnt をセットします。
     * @param rng020confirmUserCnt rng020confirmUserCnt
     */
    public void setRng020confirmUserCnt(String rng020confirmUserCnt) {
        rng020confirmUserCnt__ = rng020confirmUserCnt;
    }

    /**
     * <p>channelList を取得します。
     * @return channelList
     */
    public List<RingiChannelDataModel> getChannelList() {
        return channelList__;
    }

    /**
     * <p>channelList をセットします。
     * @param channelList channelList
     */
    public void setChannelList(List<RingiChannelDataModel> channelList) {
        channelList__ = channelList;
    }

    /**
     * <p>confirmChannelList を取得します。
     * @return confirmChannelList
     */
    public List<RingiChannelDataModel> getConfirmChannelList() {
        return confirmChannelList__;
    }

    /**
     * <p>confirmChannelList をセットします。
     * @param confirmChannelList confirmChannelList
     */
    public void setConfirmChannelList(List<RingiChannelDataModel> confirmChannelList) {
        confirmChannelList__ = confirmChannelList;
    }

    /**
     * <p>rng020kakuninKeiro を取得します。
     * @param sortNo 表示順
     * @return rng020kakuninKeiro
     */
    public Rng020KeiroBlock getRng020kakuninKeiroDsp(String sortNo) {
        Integer sort = Integer.valueOf(sortNo);
        if (rng020kakuninKeiroDsp__.containsKey(sort)) {
            return rng020kakuninKeiroDsp__.get(sort);
        }
        Rng020KeiroBlock ret = new Rng020KeiroBlock();
        rng020kakuninKeiroDsp__.put(sort, ret);
        return ret;
    }
    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param sort 表示順
     * @param rng020kakuninKeiro rng020kakuninKeiro
     */
    public void setRng020kakuninKeiroDsp(Integer sort, Rng020KeiroBlock rng020kakuninKeiro) {
        rng020kakuninKeiroDsp__.put(sort, rng020kakuninKeiro);
    }
    /**
     * <p>rng020kakuninKeiro を取得します。
     * @return rng020kakuninKeiro
     */
    public Map<Integer, Rng020KeiroBlock> getRng020kakuninKeiroDspMap() {
        return rng020kakuninKeiroDsp__;
    }
    /**
     * <p>rng020kakuninKeiro をセットします。
     * @param rng020kakuninKeiro rng020kakuninKeiro
     */
    public void setRng020kakuninKeiroDspMap(Map<Integer, Rng020KeiroBlock> rng020kakuninKeiro) {
        rng020kakuninKeiroDsp__ = rng020kakuninKeiro;
    }
}
