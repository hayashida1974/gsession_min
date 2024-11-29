package jp.groupsession.v2.usr.usr032kn;

import java.util.List;

import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.usr032.Usr032Form;

/**
 * <br>[機  能] メイン 管理者設定 ユーザインポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr032knForm extends Usr032Form {

    /** 取込みファイル名 */
    private String usr032knFileName__ = null;
    /** 取込み予定ユーザ情報 */
    private List<CmnUsrmInfModel> usr032knImpList__ = null;
    /** 未登録役職リスト */
    private List<String> posList__ = null;
    /** 選択グループ(複数) */
    private List<CmnGroupmModel> usr032knSltgps__ = null;
    /** デフォルトグループ  */
    private CmnGroupmModel usr032knDefgp__ = null;
    /** グループ名称変更フラグ  */
    private int usr032knGrpflg__ = GSConstUser.GROUP_NAME_NOTCHANGE;

    /**
     * @return usr032knImpList を戻します。
     */
    public List<CmnUsrmInfModel> getUsr032knImpList() {
        return usr032knImpList__;
    }
    /**
     * @param usr032knImpList 設定する usr032knImpList。
     */
    public void setUsr032knImpList(List<CmnUsrmInfModel> usr032knImpList) {
        usr032knImpList__ = usr032knImpList;
    }
    /**
     * @return usr032knFileName を戻します。
     */
    public String getUsr032knFileName() {
        return usr032knFileName__;
    }
    /**
     * @param usr032knFileName 設定する usr032knFileName。
     */
    public void setUsr032knFileName(String usr032knFileName) {
        usr032knFileName__ = usr032knFileName;
    }
    /**
     * @return usr030knSltgps を戻します。
     */
    public List<CmnGroupmModel> getUsr032knSltgps() {
        return usr032knSltgps__;
    }
    /**
     * @param usr032knSltgps 設定する usr030knSltgps。
     */
    public void setUsr032knSltgps(List<CmnGroupmModel> usr032knSltgps) {
        usr032knSltgps__ = usr032knSltgps;
    }
    /**
     * @return usr031knDefgp を戻します。
     */
    public CmnGroupmModel getUsr032knDefgp() {
        return usr032knDefgp__;
    }
    /**
     * @param usr032knDefgp 設定する usr032knDefgp。
     */
    public void setUsr032knDefgp(CmnGroupmModel usr032knDefgp) {
        usr032knDefgp__ = usr032knDefgp;
    }
    /**
     * <p>posList を取得します。
     * @return posList
     */
    public List<String> getPosList() {
        return posList__;
    }
    /**
     * <p>posList をセットします。
     * @param posList posList
     */
    public void setPosList(List<String> posList) {
        posList__ = posList;
    }
    /**
     * @return usr032knGrpflg を戻します。
     */
    public int getUsr032knGrpflg() {
        return usr032knGrpflg__;
    }
    /**
     * @param usr032knGrpflg 設定する usr032knGrpflg。
     */
    public void setUsr032knGrpflg(int usr032knGrpflg) {
        usr032knGrpflg__ = usr032knGrpflg;
    }
}