package jp.groupsession.v2.usr.usr081;

import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.usr080.Usr080Form;

/**
 * <br>[機  能] ユーザ情報 エクスポート制限設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr081Form extends Usr080Form {

    /** CSVエクスポート許可設定 */
    private int usr081CsvExp__ = GSConstUser.CSV_EXPORT_ALL;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Usr081Form() {
    }

    /**
     * <p>usr081CsvExp を取得します。
     * @return usr081CsvExp
     */
    public int getUsr081CsvExp() {
        return usr081CsvExp__;
    }

    /**
     * <p>usr081CsvExp をセットします。
     * @param usr081CsvExp usr081CsvExp
     */
    public void setUsr081CsvExp(int usr081CsvExp) {
        usr081CsvExp__ = usr081CsvExp;
    }


}