package jp.groupsession.v2.dba.meta;

import java.util.ArrayList;
import java.util.Iterator;

import jp.co.sjts.util.sort.ListSorter;

/**
 * <br>[機  能] テーブル情報を格納する
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class Table {

    /** 複数の行情報 */
    private ArrayList<Column> columns__ = new ArrayList<Column>();
    /** テーブル名 */
    private String name__ = null;

    /**
     * コンストラクタ
     */
    public Table() {
        super();
    }
    /**
     * <p>全ての行情報を取得する
     * @return 全ての行情報
     */
    public ArrayList getColumns() {
        return columns__;
    }

    /**
     * <p>全ての行情報をセットする。
     * @param list 保持している全ての行情報
     */
    public void setColumns(ArrayList<Column> list) {
        columns__ = list;
    }

    /**
     * <p>テーブル名を取得します
     * @return テーブル名
     */
    public String getName() {
        return name__;
    }

    /**
     * <p>テーブル名をセットします
     * @param string テーブル名
     */
    public void setName(String string) {
        name__ = string;
    }

    /**
     * <p>このオブジェクトの文字列形式を返します。
     * @return このオブジェクトの文字列形式
     */
    public String toString() {
        if (name__ == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        buf.append(name__ + "\r\n");

        if (columns__ == null) {
            return buf.toString();
        }

        //
        Iterator it = columns__.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            buf.append("    " + obj.toString() + "\r\n");
        }
        return buf.toString();
    }

    /**
     * <p>プライマリキーの列情報をキーの昇順で返します。
     * @return プライマリキーの列情報
     */
    public ArrayList getPkey() {
        ArrayList<Column> ret = new ArrayList<Column>();
        //
        Iterator it = columns__.iterator();
        while (it.hasNext()) {
            Column col = (Column) it.next();
            int keySeq = col.getSeqKey();
            if (keySeq > 0) {
                ret.add(col);
            }
        }
        ret.trimToSize();
        return (ArrayList) ListSorter.sort(ret, true);
    }

    /**
     * <p>列を追加します
     * @param col 列情報
     */
    public void add(Column col) {
        columns__.add(col);
    }
}
