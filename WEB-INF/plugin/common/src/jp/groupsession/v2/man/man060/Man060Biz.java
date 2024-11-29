package jp.groupsession.v2.man.man060;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.dao.base.CmnDiskadminDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnDiskadminModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.io.FileUtils;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] メイン 管理者設定 ディスク容量管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man060Biz {

    /**
     * <br>[機  能] 初期表示の設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param realPath ディスクのパス
     * @throws SQLException SQL実行時例外
     * @throws IOException 空き容量の取得に失敗
     */
    public void setInitData(
                Connection con,
                RequestModel reqMdl,
                Man060ParamModel paramMdl,
                String realPath) throws IOException, SQLException {

        //容量一覧を設定
        List<LabelValueBean> capacityList = new ArrayList<LabelValueBean>();
        GsMessage gsMsg = new GsMessage(reqMdl);
        capacityList.add(new LabelValueBean(gsMsg.getMessage("cmn.specified.no"), "0"));
        capacityList.add(new LabelValueBean("100MB", "100"));
        capacityList.add(new LabelValueBean("150MB", "150"));
        capacityList.add(new LabelValueBean("200MB", "200"));
        capacityList.add(new LabelValueBean("250MB", "250"));
        capacityList.add(new LabelValueBean("300MB", "300"));
        capacityList.add(new LabelValueBean("350MB", "350"));
        capacityList.add(new LabelValueBean("400MB", "400"));
        capacityList.add(new LabelValueBean("500MB", "500"));
        capacityList.add(new LabelValueBean("1GB", "1000"));
        capacityList.add(new LabelValueBean("2GB", "2000"));
        capacityList.add(new LabelValueBean("5GB", "5000"));
        capacityList.add(new LabelValueBean("10GB", "10000"));
        capacityList.add(new LabelValueBean("20GB", "20000"));
        capacityList.add(new LabelValueBean("30GB", "30000"));
        capacityList.add(new LabelValueBean("40GB", "40000"));
        capacityList.add(new LabelValueBean("50GB", "50000"));
        capacityList.add(new LabelValueBean("70GB", "70000"));
        capacityList.add(new LabelValueBean("100GB", "100000"));
        capacityList.add(new LabelValueBean("200GB", "200000"));
        capacityList.add(new LabelValueBean("300GB", "300000"));

        paramMdl.setCapacityList(capacityList);

        //空き容量を設定
        BigDecimal freeSpace = new BigDecimal(new File(realPath).getFreeSpace());
        StringBuilder strFreeSpace = new StringBuilder("");
        strFreeSpace.append(freeSpace.divide(new BigDecimal(FileUtils.ONE_MB), 1).toString());
        strFreeSpace.append("MB");

        if (freeSpace.longValue() > FileUtils.ONE_GB) {
            strFreeSpace.append(" (");
            strFreeSpace.append(freeSpace.divide(new BigDecimal(FileUtils.ONE_GB), 1).toString());
            strFreeSpace.append("GB)");
        }
        paramMdl.setFreeSpace(strFreeSpace.toString());

        if (paramMdl.getMan060capacity() < 0) {
            CmnDiskadminDao diskDao = new CmnDiskadminDao(con);
            CmnDiskadminModel diskMdl = diskDao.select();

            if (diskMdl != null) {

                paramMdl.setMan060capacity(diskMdl.getDskValue());
            }

        }
    }
}
