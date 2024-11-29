package jp.groupsession.v2.rng.csv;

import java.io.Serializable;

/**
 * <p>RNG_SINGI Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngCsvUserModel implements Serializable {

    /** SID */
    private int sid__;
    /** 名前 */
    private String name__;
    /** 役職(ユーザデータのみ) */
    private int pos__;
    /** ソートIndex */
    private int sort__ = -1;

    /**
     * <p>Default Constructor
     */
    public RngCsvUserModel() {
    }

    /**
     * <p>sid を取得します。
     * @return sid
     */
    public int getSid() {
        return sid__;
    }

    /**
     * <p>sid をセットします。
     * @param sid sid
     */
    public void setSid(int sid) {
        sid__ = sid;
    }

    /**
     * <p>name を取得します。
     * @return name
     */
    public String getName() {
        return name__;
    }

    /**
     * <p>name をセットします。
     * @param name name
     */
    public void setName(String name) {
        name__ = name;
    }

    /**
     * <p>pos を取得します。
     * @return pos
     */
    public int getPos() {
        return pos__;
    }

    /**
     * <p>pos をセットします。
     * @param pos pos
     */
    public void setPos(int pos) {
        pos__ = pos;
    }

    /**
     * <p>sort を取得します。
     * @return sort
     */
    public int getSort() {
        return sort__;
    }

    /**
     * <p>sort をセットします。
     * @param sort sort
     */
    public void setSort(int sort) {
        sort__ = sort;
    }
}
