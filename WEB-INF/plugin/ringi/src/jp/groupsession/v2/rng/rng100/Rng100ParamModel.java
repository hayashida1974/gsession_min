package jp.groupsession.v2.rng.rng100;

import java.util.List;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RngChannelTemplateModel;
import jp.groupsession.v2.rng.rng010.Rng010ParamModel;

/**
 * <br>[機  能] 稟議 経路テンプレート一覧画面のパラメータを保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng100ParamModel extends Rng010ParamModel {
    /** 経路テンプレートSID */
    private int rctSid__ = -1;
    /** 経路テンプレート処理モード */
    private int rngRctCmdMode__ = RngConst.RNG_CMDMODE_ADD;
        /** テンプレート種別 */
    private int rngRctTemplateMode__ = RngConst.RNG_TEMPLATE_SHARE;

    /** 経路テンプレート一覧 */
    private List<RngChannelTemplateModel> rng100keiroTemplateList__ = null;

    /**
     * <p>rctSid を取得します。
     * @return rctSid
     */
    public int getRctSid() {
        return rctSid__;
    }
    /**
     * <p>rctSid をセットします。
     * @param rctSid rctSid
     */
    public void setRctSid(int rctSid) {
        rctSid__ = rctSid;
    }
    /**
     * <p>rng100keiroTemplateList を取得します。
     * @return rng100keiroTemplateList
     */
    public List<RngChannelTemplateModel> getRng100keiroTemplateList() {
        return rng100keiroTemplateList__;
    }
    /**
     * <p>rng100keiroTemplateList をセットします。
     * @param rng100keiroTemplateList rng100keiroTemplateList
     */
    public void setRng100keiroTemplateList(
            List<RngChannelTemplateModel> rng100keiroTemplateList) {
        rng100keiroTemplateList__ = rng100keiroTemplateList;
    }
    /**
     * <p>rngRctCmdMode を取得します。
     * @return rngRctCmdMode
     */
    public int getRngRctCmdMode() {
        return rngRctCmdMode__;
    }
    /**
     * <p>rngRctCmdMode をセットします。
     * @param rngRctCmdMode rngRctCmdMode
     */
    public void setRngRctCmdMode(int rngRctCmdMode) {
        rngRctCmdMode__ = rngRctCmdMode;
    }
    /**
     * <p>rngRctTemplateMode を取得します。
     * @return rngRctTemplateMode
     */
    public int getRngRctTemplateMode() {
        return rngRctTemplateMode__;
    }
    /**
     * <p>rngRctTemplateMode をセットします。
     * @param rngRctTemplateMode rngRctTemplateMode
     */
    public void setRngRctTemplateMode(int rngRctTemplateMode) {
        rngRctTemplateMode__ = rngRctTemplateMode;
    }
}