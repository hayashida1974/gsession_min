package jp.co.sjts.util.sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * <br>[機  能] java.util.Listのソートを行うクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ListSorter {

    /**
     * <p>ソートを実行します
     * @param al ソート対象のリスト
     * @param order オーダー
     * @return ソート結果のリスト
     */
    @SuppressWarnings({"unchecked", "all" })
    public static List sort(ArrayList al, boolean order) {
        Iterator it = al.iterator();
        ArrayList tmp = new ArrayList();
        while (it.hasNext()) {
            IListSortable ils = (IListSortable) it.next();

            ArrayList linTmp = new ArrayList();
            linTmp.add(ils.getSortKey());
            linTmp.add(ils);
            tmp.add(linTmp);
        }

        //比較オブジェクトの生成
        ListCompare lc = new ListCompare(order, 0);
        //ソート実行
        Collections.sort(tmp, lc);

        //結果をListにつめて返す
        ArrayList sorted = new ArrayList();
        Iterator it2 = tmp.iterator();
        while (it2.hasNext()) {
            ArrayList ar = (ArrayList) it2.next();
            sorted.add(ar.get(1));
        }
        return sorted;
    }
}
