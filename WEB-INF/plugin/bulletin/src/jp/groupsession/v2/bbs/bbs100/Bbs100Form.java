package jp.groupsession.v2.bbs.bbs100;

import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.bbs.bbs010.Bbs010Form;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.GSConstUser;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 掲示板 フォーラムメンバー一覧画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs100Form extends Bbs010Form {

    /** フォーラムメンバー一覧最大表示件数 */
    public static final int VIEW_MAXCNT = 30;

    /** ソートオーダー */
    private int bbs100orderKey__ = GSConst.ORDER_KEY_ASC;
    /** ソート キー */
    private int bbs100sortKey__ = GSConstUser.USER_SORT_SNO;

    /** ページ1(上) */
    private int bbs100pageNum1__;
    /** ページ2(下) */
    private int bbs100pageNum2__;
    /** ページラベル */
    ArrayList < LabelValueBean > bbs100PageLabel__;

    //検索結果
    /** 検索結果に該当するユーザ */
    private List<CmnUsrmInfModel> bbs100users__ = null;

    /**
     * <p>bbs100orderKey を取得します。
     * @return bbs100orderKey
     */
    public int getBbs100orderKey() {
        return bbs100orderKey__;
    }

    /**
     * <p>bbs100orderKey をセットします。
     * @param bbs100orderKey bbs100orderKey
     */
    public void setBbs100orderKey(int bbs100orderKey) {
        bbs100orderKey__ = bbs100orderKey;
    }

    /**
     * <p>bbs100PageLabel を取得します。
     * @return bbs100PageLabel
     */
    public ArrayList<LabelValueBean> getBbs100PageLabel() {
        return bbs100PageLabel__;
    }

    /**
     * <p>bbs100PageLabel をセットします。
     * @param bbs100PageLabel bbs100PageLabel
     */
    public void setBbs100PageLabel(ArrayList<LabelValueBean> bbs100PageLabel) {
        bbs100PageLabel__ = bbs100PageLabel;
    }

    /**
     * <p>bbs100pageNum1 を取得します。
     * @return bbs100pageNum1
     */
    public int getBbs100pageNum1() {
        return bbs100pageNum1__;
    }

    /**
     * <p>bbs100pageNum1 をセットします。
     * @param bbs100pageNum1 bbs100pageNum1
     */
    public void setBbs100pageNum1(int bbs100pageNum1) {
        bbs100pageNum1__ = bbs100pageNum1;
    }

    /**
     * <p>bbs100pageNum2 を取得します。
     * @return bbs100pageNum2
     */
    public int getBbs100pageNum2() {
        return bbs100pageNum2__;
    }

    /**
     * <p>bbs100pageNum2 をセットします。
     * @param bbs100pageNum2 bbs100pageNum2
     */
    public void setBbs100pageNum2(int bbs100pageNum2) {
        bbs100pageNum2__ = bbs100pageNum2;
    }

    /**
     * <p>bbs100sortKey を取得します。
     * @return bbs100sortKey
     */
    public int getBbs100sortKey() {
        return bbs100sortKey__;
    }

    /**
     * <p>bbs100sortKey をセットします。
     * @param bbs100sortKey bbs100sortKey
     */
    public void setBbs100sortKey(int bbs100sortKey) {
        bbs100sortKey__ = bbs100sortKey;
    }

    /**
     * <p>bbs100users を取得します。
     * @return bbs100users
     */
    public List<CmnUsrmInfModel> getBbs100users() {
        return bbs100users__;
    }

    /**
     * <p>bbs100users をセットします。
     * @param bbs100users bbs100users
     */
    public void setBbs100users(List<CmnUsrmInfModel> bbs100users) {
        bbs100users__ = bbs100users;
    }

}