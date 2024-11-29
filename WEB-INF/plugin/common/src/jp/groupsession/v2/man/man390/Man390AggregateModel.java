package jp.groupsession.v2.man.man390;

import java.io.Serializable;


/**
 * <br>[機  能] メイン 管理者設定 ログイン履歴統計情報の集計用のモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man390AggregateModel implements Serializable {

    /** ログイン回数の合計 */
    private long man390SumLoginNum__;
    /** ログインユーザ数の合計 */
    private long man390SumLoginData__;
    /** 登録ユーザ数の合計 */
    private long man390SumRegUserNum__;

    /**
     * <p>man390SumLoginNum を取得します。
     * @return man390SumLoginNum
     */
    public long getMan390SumLoginNum() {
        return man390SumLoginNum__;
    }
    /**
     * <p>man390SumLoginNum をセットします。
     * @param man390SumLoginNum man390SumLoginNum
     */
    public void setMan390SumLoginNum(long man390SumLoginNum) {
        man390SumLoginNum__ = man390SumLoginNum;
    }
    /**
     * <p>man390SumLoginData を取得します。
     * @return man390SumLoginData
     */
    public long getMan390SumLoginData() {
        return man390SumLoginData__;
    }
    /**
     * <p>man390SumLoginData をセットします。
     * @param man390SumLoginData man390SumLoginData
     */
    public void setMan390SumLoginData(long man390SumLoginData) {
        man390SumLoginData__ = man390SumLoginData;
    }
    /**
     * <p>man390SumRegUserNum を取得します。
     * @return man390SumRegUserNum
     */
    public long getMan390SumRegUserNum() {
        return man390SumRegUserNum__;
    }
    /**
     * <p>man390SumRegUserNum をセットします。
     * @param man390SumRegUserNum man390SumRegUserNum
     */
    public void setMan390SumRegUserNum(long man390SumRegUserNum) {
        man390SumRegUserNum__ = man390SumRegUserNum;
    }
}