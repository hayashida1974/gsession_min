package jp.co.sjts.util.csv;

import jp.groupsession.v2.cmn.model.AbstractModel;

/**
 * <br>[機  能] CSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public interface CSVRecordListener {

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public abstract void setRecord(AbstractModel model) throws CSVException;

}
