package jp.groupsession.v2.man.man028;

import java.util.ArrayList;

import jp.groupsession.v2.cmn.model.AbstractParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 休日設定インポート画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man028ParamModel extends AbstractParamModel {
    /** 日付　月-拡張 */
    /** 添付ファイル(コンボで選択中) */
    private String[] man028SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man028FileLabelList__ = null;
    /** 有効データ件数 */
    private int impDataCnt__ = 0;
    /** 上書きフラグ */
    private int man028updateFlg__ = 0;

    /**
     * @return man028SelectFiles
     */
    public String[] getMan028SelectFiles() {
        return man028SelectFiles__;
    }

    /**
     * @param man028SelectFiles セットする man028SelectFiles
     */
    public void setMan028SelectFiles(String[] man028SelectFiles) {
        man028SelectFiles__ = man028SelectFiles;
    }

    /**
     * @return man028FileLabelList
     */
    public ArrayList<LabelValueBean> getMan028FileLabelList() {
        return man028FileLabelList__;
    }

    /**
     * @param man028FileLabelList セットする man028FileLabelList
     */
    public void setMan028FileLabelList(ArrayList<LabelValueBean> man028FileLabelList) {
        man028FileLabelList__ = man028FileLabelList;
    }

    /**
     * @return impDataCnt を戻します。
     */
    public int getImpDataCnt() {
        return impDataCnt__;
    }

    /**
     * @param impDataCnt 設定する impDataCnt
     */
    public void setImpDataCnt(int impDataCnt) {
        impDataCnt__ = impDataCnt;
    }

    /**
     * @return man028updateFlg
     */
    public int getMan028updateFlg() {
        return man028updateFlg__;
    }

    /**
     * @param man028updateFlg セットする man028updateFlg
     */
    public void setMan028updateFlg(int man028updateFlg) {
        man028updateFlg__ = man028updateFlg;
    }
}