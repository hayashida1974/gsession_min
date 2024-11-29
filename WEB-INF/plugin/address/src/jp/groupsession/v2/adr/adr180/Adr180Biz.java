package jp.groupsession.v2.adr.adr180;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr010.Adr010Biz;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能] アドレス帳 役職登録ポップアップのビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr180Biz extends Adr010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr180Biz.class);
    /** リクエストモデル */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr180Biz(RequestModel reqMdl) {
        super(reqMdl);
        reqMdl_ = reqMdl;
    }

    /**
     *
     * <br>[機  能]初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr180ParamModel
     * @param con コネクション
     * @param userMdl セッションユーザ情報
     * @throws Exception 実行例外
     */
    public void setInitData(Adr180ParamModel paramMdl,
                                    Connection con,
                                    BaseUserModel userMdl) throws Exception {

        CommonBiz cmnBiz = new CommonBiz();
        boolean adminUser = cmnBiz.isPluginAdmin(con, userMdl, GSConstAddress.PLUGIN_ID_ADDRESS);
        AdrAconfDao aconfDao = new AdrAconfDao(con);
        AdrAconfModel aconfMdl = aconfDao.selectAconf();

        //セッションユーザが管理者、もしくは役職編集権限を設定していない場合、入力フォームと追加ボタンを表示
        if (adminUser || (aconfMdl == null || aconfMdl.getAacYksEdit() == GSConstAddress.POW_ALL)) {
            paramMdl.setAdr180Admin(GSConstAddress.DSP_ELEMENT);
        }

    }

    /**
     * <br>[機  能] アドレス帳役職マスタの登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr180ParamModel
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行例外
     */
    public void entryPositionData(
            Connection con, Adr180ParamModel paramMdl, MlCountMtController mtCon,
                                int sessionUserSid)
    throws SQLException {

        log__.debug("START");

        UDate now = new UDate();

        AdrPositionModel positionMdl = new AdrPositionModel();
        long apsSid = mtCon.getSaibanNumber(GSConst.SBNSID_ADDRESS,
                                            GSConstAddress.SBNSID_SUB_POSITION, sessionUserSid);

        positionMdl.setApsSid((int) apsSid);
        positionMdl.setApsName(paramMdl.getAdr180positionName());
        positionMdl.setApsAuid(sessionUserSid);
        positionMdl.setApsAdate(now);
        positionMdl.setApsEuid(sessionUserSid);
        positionMdl.setApsEdate(now);

        AdrPositionDao positionDao = new AdrPositionDao(con);
        positionDao.insertNewYakusyoku(positionMdl);

        paramMdl.setAdr180position(String.valueOf(apsSid));
        log__.debug("End");
    }

}
