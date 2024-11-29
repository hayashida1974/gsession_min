package jp.groupsession.v2.rng.pdf;

import java.util.List;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.model.RingiDataModel;

/**
 * <br>[機  能] 稟議 管理者設定 申請中案件管理画面のパラメータを保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngMultiThreadModel {
    //フィールド

    /** 稟議情報一覧 */
    private List<RingiDataModel> rngDataList__ = null;
    /** 処理モード */
    private int rngProcMode__ = RngConst.RNG_MODE_JYUSIN;
    /** 申請モード */
    private int rngApprMode__ = RngConst.RNG_APPRMODE_APPR;


    /**
     * <p>rngDataList を取得します。
     * @return rngDataList
     */
    public List<RingiDataModel> getRngDataList() {
        return rngDataList__;
    }

    /**
     * <p>rngDataList をセットします。
     * @param rngDataList rngDataList
     */
    public void setRngDataList(List<RingiDataModel> rngDataList) {
        rngDataList__ = rngDataList;
    }
    /**
     * <p>rngProcMode を取得します。
     * @return rngProcMode
     */
    public int getRngProcMode() {
        return rngProcMode__;
    }
    /**
     * <p>rngProcMode をセットします。
     * @param rngProcMode rngProcMode
     */
    public void setRngProcMode(int rngProcMode) {
        rngProcMode__ = rngProcMode;
    }
    /**
     * <p>rngApprMode を取得します。
     * @return rngApprMode
     */
    public int getRngApprMode() {
        return rngApprMode__;
    }
    /**
     * <p>rngApprMode をセットします。
     * @param rngApprMode rngApprMode
     */
    public void setRngApprMode(int rngApprMode) {
        rngApprMode__ = rngApprMode;
    }
}