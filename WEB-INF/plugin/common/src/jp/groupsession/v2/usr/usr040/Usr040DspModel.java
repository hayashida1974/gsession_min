package jp.groupsession.v2.usr.usr040;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] ユーザ情報一覧表示用検索結果モデル
 * <br>[解  説] ユーザ情報＋所属グループ
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr040DspModel extends CmnUsrmInfModel {

    /** 所属グループ一覧 */
    private ArrayList<GroupModel> belongGrpList__ = null;

    /**
     * <p>belongGrpList を取得します。
     * @return belongGrpList
     */
    public ArrayList<GroupModel> getBelongGrpList() {
        return belongGrpList__;
    }

    /**
     * <p>belongGrpList をセットします。
     * @param belongGrpList belongGrpList
     */
    public void setBelongGrpList(ArrayList<GroupModel> belongGrpList) {
        belongGrpList__ = belongGrpList;
    }

}