package jp.groupsession.v2.wml.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.model.base.LabelDataModel;
import jp.groupsession.v2.wml.model.base.WmlLabelModel;

/**
 * <br>[機  能] WEBメールプラグインのラベルを操作するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlLabelBiz {

    private final Log log__ = LogFactory.getLog(WmlLabelBiz.class);
    
    /**
     * <br>[機  能] ラベルを登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param mlCnt 採番コントローラ
     * @param con コネクション
     * @param userSid ユーザSID
     * @param labelName ラベル名
     * @param wacSid アカウントSID
     * @return 作成したラベルの情報
     * @throws SQLException SQL実行例外
     */
    public WmlLabelModel doInsertLabel(
        MlCountMtController mlCnt, Connection con,
        int userSid, String labelName, int wacSid) throws SQLException {

        int labelSid = (int) mlCnt.getSaibanNumber(SaibanModel.SBNSID_WEBMAIL,
                            SaibanModel.SBNSID_SUB_LABEL, userSid);
        log__.info("ラベル採番値：" + labelSid);
        
        WmlLabelModel labelModel = new WmlLabelModel();
        labelModel.setWlbSid(labelSid);
        labelModel.setWlbName(labelName);
        labelModel.setWacSid(wacSid);

        //並び順の設定
        WmlLabelDao wlbDao = new WmlLabelDao(con);
        labelModel.setWlbOrder(wlbDao.maxSortNumber(labelModel.getWacSid()) + 1);

        log__.info("ラベル並び順：" + labelModel.getWlbOrder());

        //ラベルの登録
        wlbDao.insert(labelModel);
        
        return labelModel;
    }

    /**
     * <br>[機  能] WmlLabelModelから表示用のLabelDataModel一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param labelList 型変換前のラベル一覧
     * @return LabelDataModelリスト
     */
    public List<LabelDataModel> getLabelDataList(List<WmlLabelModel> labelList) {
        
        List<LabelDataModel> ret = new ArrayList<LabelDataModel>();
        if (labelList == null || labelList.isEmpty()) {
            return ret;
        }
        
        LabelDataModel labelMdl = null;
        for (WmlLabelModel mdl : labelList) {
            labelMdl = new LabelDataModel();
            labelMdl.setLabelSid(mdl.getWlbSid());
            labelMdl.setLabelName(mdl.getWlbName());
            labelMdl.setLabelOrder(mdl.getWlbOrder());
            labelMdl.setLbValue(String.valueOf(mdl.getWlbSid()));
            ret.add(labelMdl);
        }

        return ret;
    }

}
