package jp.groupsession.v2.bbs.bbs140;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.bbs.bbs060.Bbs060Form;
import jp.groupsession.v2.bbs.model.BulletinWachModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 掲示板 フォーラムメンバー閲覧状況ポップアップのフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs140Form extends Bbs060Form {

    /** フォーラムメンバー一覧最大表示件数 */
    public static final int VIEW_MAXCNT = 10;

    /** ソートオーダー */
    private int bbs140orderKey__ = GSConst.ORDER_KEY_ASC;
    /** ソート キー */
    private int bbs140sortKey__ = GSConstUser.USER_SORT_NAME;

    /** ページ1(上) */
    private int bbs140pageNum1__;
    /** ページ2(下) */
    private int bbs140pageNum2__;
    /** ページラベル */
    ArrayList < LabelValueBean > bbs140PageLabel__;

    //検索結果
    /** 検索結果に該当するユーザ */
    private List<BulletinWachModel> bbs140users__ = null;

    /**
     * <p>bbs140orderKey を取得します。
     * @return bbs140orderKey
     */
    public int getBbs140orderKey() {
        return bbs140orderKey__;
    }

    /**
     * <p>bbs140orderKey をセットします。
     * @param bbs140orderKey bbs140orderKey
     */
    public void setBbs140orderKey(int bbs140orderKey) {
        bbs140orderKey__ = bbs140orderKey;
    }

    /**
     * <p>bbs140PageLabel を取得します。
     * @return bbs140PageLabel
     */
    public ArrayList<LabelValueBean> getBbs140PageLabel() {
        return bbs140PageLabel__;
    }

    /**
     * <p>bbs140PageLabel をセットします。
     * @param bbs140PageLabel bbs140PageLabel
     */
    public void setBbs140PageLabel(ArrayList<LabelValueBean> bbs140PageLabel) {
        bbs140PageLabel__ = bbs140PageLabel;
    }

    /**
     * <p>bbs140pageNum1 を取得します。
     * @return bbs140pageNum1
     */
    public int getBbs140pageNum1() {
        return bbs140pageNum1__;
    }

    /**
     * <p>bbs140pageNum1 をセットします。
     * @param bbs140pageNum1 bbs140pageNum1
     */
    public void setBbs140pageNum1(int bbs140pageNum1) {
        bbs140pageNum1__ = bbs140pageNum1;
    }

    /**
     * <p>bbs140pageNum2 を取得します。
     * @return bbs140pageNum2
     */
    public int getBbs140pageNum2() {
        return bbs140pageNum2__;
    }

    /**
     * <p>bbs140pageNum2 をセットします。
     * @param bbs140pageNum2 bbs140pageNum2
     */
    public void setBbs140pageNum2(int bbs140pageNum2) {
        bbs140pageNum2__ = bbs140pageNum2;
    }

    /**
     * <p>bbs140sortKey を取得します。
     * @return bbs140sortKey
     */
    public int getBbs140sortKey() {
        return bbs140sortKey__;
    }

    /**
     * <p>bbs140sortKey をセットします。
     * @param bbs140sortKey bbs140sortKey
     */
    public void setBbs140sortKey(int bbs140sortKey) {
        bbs140sortKey__ = bbs140sortKey;
    }

    /**
     * <p>bbs140users を取得します。
     * @return bbs140users
     */
    public List<BulletinWachModel> getBbs140users() {
        return bbs140users__;
    }

    /**
     * <p>bbs140users をセットします。
     * @param bbs140users bbs140users
     */
    public void setBbs140users(List<BulletinWachModel> bbs140users) {
        bbs140users__ = bbs140users;
    }
}
