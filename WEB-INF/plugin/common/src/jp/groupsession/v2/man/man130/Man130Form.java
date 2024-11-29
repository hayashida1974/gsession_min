package jp.groupsession.v2.man.man130;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.struts.AbstractGsForm;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 添付ファイル設定画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man130Form extends AbstractGsForm {

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

    /**
     * <br>[機  能] 入力チェックを行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param errors ActionErrors
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @throws IOToolsException 入出力時例外
     * @throws SQLException SQL実行時例外
     * @return ActionErrors
     */
    public ActionErrors validateCheck(
            ActionErrors errors,
            Connection con,
            RequestModel reqMdl)
                    throws IOToolsException, SQLException {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        String msgPhotoSize = gsMsg.getMessage("main.man130.4");

        if (!ValidateUtil.isNumberDot(man130PhotoSize__, 2, 1)) {
            //整数部2桁、小数部1桁の数字ではない場合
            msg = new ActionMessage("error.input.format.length.number", msgPhotoSize, 2, 1);
            StrutsUtil.addMessage(errors, msg, "photosize.error.input.format.length.number");
            return errors;
        }

        Double dblPhotoSize = Double.parseDouble(man130PhotoSize__);
        if (dblPhotoSize > GSConstMain.PHOTO_SIZE_MAX) {
            //写真ファイル最大容量 最大値を超える場合
            msg = new ActionMessage(
                    "error.input.number.under", msgPhotoSize, GSConstMain.PHOTO_SIZE_MAX);
            StrutsUtil.addMessage(errors, msg, "photosize.error.input.number.under");
        }

        return errors;
    }
}
