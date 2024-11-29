package jp.groupsession.v2.man.man029;

import java.util.ArrayList;

import jp.groupsession.v2.man.man023.Man023ParamModel;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 休日テンプレートインポート画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man029ParamModel extends Man023ParamModel {
    /** 添付ファイル(コンボで選択中) */
    private String[] man029SelectFiles__ = null;
    /** ファイルコンボ */
    private ArrayList<LabelValueBean> man029FileLabelList__ = null;
    /** 有効データ件数 */
    private int impDataCnt__ = 0;
    /** 上書きフラグ */
    private int man029updateFlg__ = 0;

    /**
     * @return man029SelectFiles
     */
    public String[] getMan029SelectFiles() {
        return man029SelectFiles__;
    }

    /**
     * @param man029SelectFiles セットする man029SelectFiles
     */
    public void setMan029SelectFiles(String[] man029SelectFiles) {
        man029SelectFiles__ = man029SelectFiles;
    }

    /**
     * @return man029FileLabelList
     */
    public ArrayList<LabelValueBean> getMan029FileLabelList() {
        return man029FileLabelList__;
    }

    /**
     * @param man029FileLabelList セットする man029FileLabelList
     */
    public void setMan029FileLabelList(ArrayList<LabelValueBean> man029FileLabelList) {
        man029FileLabelList__ = man029FileLabelList;
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
     * @return man029updateFlg
     */
    public int getMan029updateFlg() {
        return man029updateFlg__;
    }

    /**
     * @param man029updateFlg セットする man029updateFlg
     */
    public void setMan029updateFlg(int man029updateFlg) {
        man029updateFlg__ = man029updateFlg;
    }
}