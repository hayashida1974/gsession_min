package jp.groupsession.v2.man.man130;

import java.util.List;

import jp.groupsession.v2.cmn.model.AbstractParamModel;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 添付ファイル設定画面の情報を保持するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man130ParamModel extends AbstractParamModel {

    //入力項目
    /** 添付ファイル最大容量 */
    private int man130maxSize__ = GSConstMain.FILE_SIZE_10MB;
    /** 写真ファイル最大容量 */
    private String man130PhotoSize__ = "1";

    //表示項目
    /** 添付ファイル最大容量ラベル */
    private List<LabelValueBean> maxSizeLabel__;
    /** 写真ファイル最大容量ラベル */
    private List<LabelValueBean> photoSizeLabel__;

    /**
     * <p>man130maxSize を取得します。
     * @return man130maxSize
     */
    public int getMan130maxSize() {
        return man130maxSize__;
    }

    /**
     * <p>man130maxSize をセットします。
     * @param man130maxSize man130maxSize
     */
    public void setMan130maxSize(int man130maxSize) {
        man130maxSize__ = man130maxSize;
    }

    /**
     * <p>man130PhotoSize を取得します。
     * @return man130PhotoSize
     */
    public String getMan130PhotoSize() {
        return man130PhotoSize__;
    }

    /**
     * <p>man130PhotoSize をセットします。
     * @param man130PhotoSize man130PhotoSize
     */
    public void setMan130PhotoSize(String man130PhotoSize) {
        man130PhotoSize__ = man130PhotoSize;
    }

    /**
     * <p>maxSizeLabel を取得します。
     * @return maxSizeLabel
     */
    public List<LabelValueBean> getMaxSizeLabel() {
        return maxSizeLabel__;
    }

    /**
     * <p>maxSizeLabel をセットします。
     * @param maxSizeLabel maxSizeLabel
     */
    public void setMaxSizeLabel(List<LabelValueBean> maxSizeLabel) {
        maxSizeLabel__ = maxSizeLabel;
    }

    /**
     * <p>photoSizeLabel を取得します。
     * @return photoSizeLabel
     */
    public List<LabelValueBean> getPhotoSizeLabel() {
        return photoSizeLabel__;
    }

    /**
     * <p>photoSizeLabel をセットします。
     * @param photoSizeLabel photoSizeLabel
     */
    public void setPhotoSizeLabel(List<LabelValueBean> photoSizeLabel) {
        photoSizeLabel__ = photoSizeLabel;
    }

}