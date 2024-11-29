package jp.groupsession.v2.man.man130;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.dao.base.CmnFileConfDao;
import jp.groupsession.v2.cmn.model.base.CmnFileConfModel;
import jp.groupsession.v2.man.GSConstMain;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 添付ファイル設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man130Biz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Man130Biz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Man130ParamModel paramMdl) throws SQLException {

        CmnFileConfDao cfcDao = new CmnFileConfDao(con__);
        CmnFileConfModel cfcMdl = cfcDao.select();

        //添付ファイル最大容量
        paramMdl.setMan130maxSize(cfcMdl.getFicMaxSize());

        //写真ファイル最大容量
        Double dblPhotoSize = Double.parseDouble(cfcMdl.getFicPhotoSize());
        int intPhotoSize = dblPhotoSize.intValue();
        Double dblIntPhotoSize = new Double(intPhotoSize);
        double remainder = dblPhotoSize - dblIntPhotoSize;

        String strPhotoSize = "";
        if (remainder > 0) {
            //小数点以下がある場合
            strPhotoSize = Double.toString(dblPhotoSize);
        } else {
            //整数のみの場合
            strPhotoSize = String.valueOf(Math.round(dblPhotoSize));
        }
        paramMdl.setMan130PhotoSize(strPhotoSize);
    }

    /**
     * <br>[機  能] 画面に常に表示する情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void getDspData(Man130ParamModel paramMdl) {

        //添付ファイル最大容量ラベル
        paramMdl.setMaxSizeLabel(__getMaxSizeLabel());

        //写真ファイル最大容量ラベル
        paramMdl.setPhotoSizeLabel(__getPhotoSizeLabel());
    }

    /**
     * <br>[機  能] 添付ファイル最大容量ラベルを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return List (in LabelValueBean)  添付ファイル最大容量ラベル
     */
    private List<LabelValueBean> __getMaxSizeLabel() {

        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        labelList.add(new LabelValueBean(0 + "MB", "0"));
        labelList.add(new LabelValueBean(1 + "MB", "1"));
        labelList.add(new LabelValueBean(2 + "MB", "2"));
        labelList.add(new LabelValueBean(3 + "MB", "3"));
        labelList.add(new LabelValueBean(5 + "MB", "5"));
        labelList.add(new LabelValueBean(7 + "MB", "7"));

        for (int size = GSConstMain.FILE_SIZE_10MB; size <= GSConstMain.FILE_SIZE_MAX; size += 10) {
            labelList.add(new LabelValueBean(size + "MB", String.valueOf(size)));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 写真ファイル最大容量ラベルを生成します
     * <br>[解  説]
     * <br>[備  考]
     * @return List (in LabelValueBean)  写真ファイル最大容量ラベル
     */
    private List<LabelValueBean> __getPhotoSizeLabel() {

        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        labelList.add(new LabelValueBean("0.5" + "MB", "0.5"));

        for (int size = 1; size <= GSConstMain.FILE_SIZE_10MB; size += 1) {
            labelList.add(new LabelValueBean(String.valueOf(size) + "MB", String.valueOf(size)));
        }

        return labelList;
    }

}
