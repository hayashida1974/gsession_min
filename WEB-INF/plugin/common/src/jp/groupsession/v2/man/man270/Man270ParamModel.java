package jp.groupsession.v2.man.man270;

import java.util.List;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.model.AbstractParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 ログイン設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man270ParamModel extends AbstractParamModel {
    /** 初期表示区分 */
    private int man270InitFlg__ = 0;
    /** ロックアウト区分 */
    private int man270lockKbn__ = GSConstCommon.LOGIN_LOCKKBN_NOSET;
    /** 失敗回数 */
    private int man270failCount__ = 0;
    /** 失敗回数リセット */
    private int man270failTime__ = GSConstCommon.LOGIN_FAILTIME_LIST[0];
    /** ロックアウト時間 */
    private int man270lockTime__ = GSConstCommon.LOGIN_LOCKTIME_LIST[0];

    /** 失敗回数 コンボ */
    private List<LabelValueBean> man270failCountList__ = null;
    /** ロックアウト時間 コンボ */
    private List<LabelValueBean> man270lockTimeList__ = null;
    /** 失敗回数リセット コンボ */
    private List<LabelValueBean> man270failTimeList__ = null;

    /**
     * <p>man270failCount を取得します。
     * @return man270failCount
     */
    public int getMan270failCount() {
        return man270failCount__;
    }
    /**
     * <p>man270failCount をセットします。
     * @param man270failCount man270failCount
     */
    public void setMan270failCount(int man270failCount) {
        man270failCount__ = man270failCount;
    }
    /**
     * <p>man270failCountList を取得します。
     * @return man270failCountList
     */
    public List<LabelValueBean> getMan270failCountList() {
        return man270failCountList__;
    }
    /**
     * <p>man270failCountList をセットします。
     * @param man270failCountList man270failCountList
     */
    public void setMan270failCountList(List<LabelValueBean> man270failCountList) {
        man270failCountList__ = man270failCountList;
    }
    /**
     * <p>man270failTime を取得します。
     * @return man270failTime
     */
    public int getMan270failTime() {
        return man270failTime__;
    }
    /**
     * <p>man270failTime をセットします。
     * @param man270failTime man270failTime
     */
    public void setMan270failTime(int man270failTime) {
        man270failTime__ = man270failTime;
    }
    /**
     * <p>man270failTimeList を取得します。
     * @return man270failTimeList
     */
    public List<LabelValueBean> getMan270failTimeList() {
        return man270failTimeList__;
    }
    /**
     * <p>man270failTimeList をセットします。
     * @param man270failTimeList man270failTimeList
     */
    public void setMan270failTimeList(List<LabelValueBean> man270failTimeList) {
        man270failTimeList__ = man270failTimeList;
    }
    /**
     * <p>man270InitFlg を取得します。
     * @return man270InitFlg
     */
    public int getMan270InitFlg() {
        return man270InitFlg__;
    }
    /**
     * <p>man270InitFlg をセットします。
     * @param man270InitFlg man270InitFlg
     */
    public void setMan270InitFlg(int man270InitFlg) {
        man270InitFlg__ = man270InitFlg;
    }
    /**
     * <p>man270lockKbn を取得します。
     * @return man270lockKbn
     */
    public int getMan270lockKbn() {
        return man270lockKbn__;
    }
    /**
     * <p>man270lockKbn をセットします。
     * @param man270lockKbn man270lockKbn
     */
    public void setMan270lockKbn(int man270lockKbn) {
        man270lockKbn__ = man270lockKbn;
    }
    /**
     * <p>man270lockTime を取得します。
     * @return man270lockTime
     */
    public int getMan270lockTime() {
        return man270lockTime__;
    }
    /**
     * <p>man270lockTime をセットします。
     * @param man270lockTime man270lockTime
     */
    public void setMan270lockTime(int man270lockTime) {
        man270lockTime__ = man270lockTime;
    }
    /**
     * <p>man270lockTimeList を取得します。
     * @return man270lockTimeList
     */
    public List<LabelValueBean> getMan270lockTimeList() {
        return man270lockTimeList__;
    }
    /**
     * <p>man270lockTimeList をセットします。
     * @param man270lockTimeList man270lockTimeList
     */
    public void setMan270lockTimeList(List<LabelValueBean> man270lockTimeList) {
        man270lockTimeList__ = man270lockTimeList;
    }
}