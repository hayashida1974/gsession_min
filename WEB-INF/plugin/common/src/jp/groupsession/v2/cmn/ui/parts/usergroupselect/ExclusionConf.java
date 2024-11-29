package jp.groupsession.v2.cmn.ui.parts.usergroupselect;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * <br>[機  能] 要素除外設定クラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ExclusionConf {
    /** 動作区分 true: 許可リスト false:除外リスト */
    private final boolean kyokaListFlg__;
    /** 指定SIDリスト */
    private List<String> sidList__ = new ArrayList<>();

    /**
     * コンストラクタ
     * @param kyokaListFlg 動作区分 true: 許可リスト false:除外リスト
     */
    public ExclusionConf(boolean kyokaListFlg) {
        super();
        kyokaListFlg__ = kyokaListFlg;
    }

    /**
     * コンストラクタ
     * @param kyokaListFlg 動作区分 true: 許可リスト false:除外リスト
     * @param sidList 指定SIDリスト
     */
    public ExclusionConf(boolean kyokaListFlg, List<String> sidList) {
        super();
        kyokaListFlg__ = kyokaListFlg;
        sidList__.addAll(sidList);
    }

    /**
     * @param sid
     * @return true
     * @see java.util.List#add(java.lang.Object)
     */
    public boolean add(String sid) {
        return sidList__.add(sid);
    }

    /**
     * @param sidList
     * @return true
     * @see java.util.List#addAll(java.util.Collection)
     */
    public boolean addAll(Collection<? extends String> sidList) {
        return sidList__.addAll(sidList);
    }

    /**
     * @return sidList__.size()
     * @see java.util.List#size()
     */
    public int size() {
        return sidList__.size();
    }

    /**
     * @param o
     * @return sidList__.contains(o)
     * @see java.util.List#contains(java.lang.Object)
     */
    public boolean contains(Object o) {
        return sidList__.contains(o);
    }

    /**
     * @param index
     * @return sidList__.get(index)
     * @see java.util.List#get(int)
     */
    public String get(int index) {
        return sidList__.get(index);
    }

    /**
     * @return sidList__.stream()
     * @see java.util.Collection#stream()
     */
    public Stream<String> stream() {
        return sidList__.stream();
    }

    /**
     * @return sidList__.isEmpty()
     * @see java.util.List#isEmpty()
     */
    public boolean isEmpty() {
        return sidList__.isEmpty();
    }

    /**
     * <p>kyokaListFlg を取得します。
     * @return kyokaListFlg
     * @see jp.groupsession.v2.cmn.ui.parts.usergroupselect.ExclusionConf#kyokaListFlg__
     */
    public boolean isKyokaListFlg() {
        return kyokaListFlg__;
    }






}
