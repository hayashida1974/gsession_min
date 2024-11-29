package jp.groupsession.v2.bbs.ptl020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.man.GSConstPortal;
import jp.groupsession.v2.man.dao.base.PtlPortalLayoutDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionDao;
import jp.groupsession.v2.man.dao.base.PtlPortalPositionParamDao;
import jp.groupsession.v2.man.model.base.PtlPortalLayoutModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionModel;
import jp.groupsession.v2.man.model.base.PtlPortalPositionParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] ポートレット スレッド一覧選択のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BbsPtl020Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(BbsPtl020Biz.class);
    /** パラメーター名1 フォーラムSID */
    public static final String BBS_PORTLET_PARAM1 = "bbsPtlBfiSid";
    /** パラメーター名2 アイテムID */
    public static final String BBS_PORTLET_PARAM2 = "bbsPtl010ItemId";

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid ユーザSID
     * @param adminUser 管理者フラグ
     * @throws Exception 実行例外
     */
    public void setInitData(RequestModel reqMdl, BbsPtl020ParamModel paramMdl,
            Connection con, int userSid, boolean adminUser)
                    throws Exception {

        int ptlSid = paramMdl.getPtlPortalSid();

        //フォーラム一覧設定
        List<BbsForInfModel> forumList = getForum(con, userSid, adminUser, ptlSid);
        paramMdl.setForumList(forumList);

        //設定済みのフォーラムリストを設定
        paramMdl.setBbsPtl020createdForumList(__getCreatedForumList(con, ptlSid));

        //プラグインポートレットコンボを設定
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);
        paramMdl.setPortletTypeCombo(
                cmnBiz.getPluginPortletCombo(con, gsMsg, reqMdl.getDomain()));
    }

    /**
     * <br>[機  能] 設定済みのフォーラムリストを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param ptlSid ポータルSID
     * @throws SQLException SQL実行時例外
     * @return 設定済みのフォーラムリスト
     */
    private List<Integer> __getCreatedForumList(
            Connection con, int ptlSid)
                    throws SQLException {
        List<Integer> ret = new ArrayList<Integer>();

        //すでに設定されているフォーラムのリスト
        PtlPortalPositionParamDao paramDao = new PtlPortalPositionParamDao(con);
        List<PtlPortalPositionParamModel> list = paramDao.getParamList(ptlSid, BBS_PORTLET_PARAM1);
        if (list == null) {
            list = new ArrayList<PtlPortalPositionParamModel>();
        }

        //フォーラムSIDをlistに格納
        for (PtlPortalPositionParamModel model : list) {
            ret.add(NullDefault.getInt(model.getPpmParamValue(), 0));
        }

        return ret;
    }

    /**
     * <br>[機  能] プラグイン追加処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig プラグインコンフィグ
     * @throws SQLException SQL実行例外
     */
    public void insertData(BbsPtl020ParamModel paramMdl,
                            Connection con, PluginConfig pconfig) throws SQLException {

        PtlPortalPositionParamDao positionParamDao = new PtlPortalPositionParamDao(con);
        PtlPortalPositionDao ptlPositionDao = new PtlPortalPositionDao(con);
        PtlPortalLayoutDao layoutDao = new PtlPortalLayoutDao(con);
        int ptlSid = paramMdl.getPtlPortalSid();
        UDate now = new UDate();
        String itemId = now.getTimeStamp();
        int forumSid = paramMdl.getBbsptl020forumSid();
        CommonBiz cmnBiz = new CommonBiz();

        //プラグイン選択済フラグ
        paramMdl.setBbsptl020selectFlg(true);

        //レイアウト情報を取得する。
        List<PtlPortalLayoutModel> layoutList
                = layoutDao.getLayoutList(ptlSid,
                                        GSConstPortal.LAYOUT_VIEW_ON);

        //プラグインを追加するポジションを取得する。
        List<Integer> positionSidList = new ArrayList<Integer>();
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_LEFT);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_CENTER);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_RIGHT);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_TOP);
        positionSidList.add(GSConstPortal.LAYOUT_POSITION_BOTTOM);

        int plyPosition = GSConstPortal.LAYOUT_POSITION_LEFT;
        boolean endFlg = false;
        for (Integer position : positionSidList) {

            for (PtlPortalLayoutModel model : layoutList) {
                if (model.getPlyPosition() == position) {
                    plyPosition = position;
                    endFlg = true;
                    break;
                }
            }
            if (endFlg) {
                break;
            }
        }

        //ポータル位置情報の最大値を取得する。
        int maxSort = ptlPositionDao.getMaxSort(ptlSid, plyPosition);

        //選択画面ID
        String screenId = GSConstBulletin.SCREENID_BBSPTL020;

        //ポートレット画面IDを取得する。
        String dspScreenId =
            cmnBiz.getPluginPortletScreenId(pconfig, GSConstBulletin.PLUGIN_ID_BULLETIN, screenId);

        //ポータル位置情報を登録する。
        PtlPortalPositionModel posiModel = new PtlPortalPositionModel();
        posiModel.setPtlSid(ptlSid);
        posiModel.setPtpItemid(now.getTimeStamp());
        posiModel.setPlyPosition(plyPosition);
        posiModel.setPtpSort(maxSort + 1);
        posiModel.setPtpType(GSConstPortal.PTP_TYPE_PLUGINPORTLET);
        posiModel.setPltSid(-1);
        posiModel.setPctPid(GSConstBulletin.PLUGIN_ID_BULLETIN);
        posiModel.setMscId(dspScreenId);
        posiModel.setPtpView(GSConstPortal.PTL_OPENKBN_OK);
        posiModel.setPtpParamkbn(GSConstPortal.PTP_PARAMKBN_ON);
        ptlPositionDao.insert(posiModel);

        //ポータル_位置設定_パラメータを登録する。
        PtlPortalPositionParamModel positionParamModel = new PtlPortalPositionParamModel();
        positionParamModel.setPtlSid(ptlSid);
        positionParamModel.setPtpItemid(itemId);
        positionParamModel.setPpmParamNo(1);
        positionParamModel.setPpmParamName(BBS_PORTLET_PARAM1);
        positionParamModel.setPpmParamValue(String.valueOf(forumSid));
        positionParamDao.insert(positionParamModel);

        //ポータル_位置設定_パラメータ2を登録する。
        positionParamModel = new PtlPortalPositionParamModel();
        positionParamModel.setPtlSid(ptlSid);
        positionParamModel.setPtpItemid(itemId);
        positionParamModel.setPpmParamNo(2);
        positionParamModel.setPpmParamName(BBS_PORTLET_PARAM2);
        positionParamModel.setPpmParamValue(itemId);
        positionParamDao.insert(positionParamModel);

    }


    /**
     * <br>[機  能] フォーラム一覧設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSid ユーザSID
     * @param admin 管理者フラグ
     * @param ptlSid ポータルSID
     * @return result 表示リスト
     * @throws SQLException SQL実行例外
     */
    public List<BbsForInfModel> getForum(
            Connection con, int userSid, boolean admin, int ptlSid)
                    throws SQLException {

        List<BbsForInfModel> result = new ArrayList<BbsForInfModel>();

        UDate now = new UDate();
        int newCnt = 0;
        int start = 0;
        int end = 0;

        //管理者画面であるため不要
        List<BbsForInfModel> usersForumWrite = null;

        //全てのフォーラム
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        List<BbsForInfModel> usersForumAll = bfiDao.select();

        //全てのフォーラム一覧
        BulletinDao dao = new BulletinDao(con);
        List<BulletinDspModel> canDspForum =
                dao.getForumListWithHierarchy(userSid, now, newCnt,
                        start, end, admin, usersForumWrite, usersForumAll);
        if (canDspForum == null) {
            canDspForum = new ArrayList<BulletinDspModel>();
        }

        //すでに追加されているか判定
        for (BulletinDspModel dspModel : canDspForum) {
            BbsForInfModel resultModel = new BbsForInfModel();
            resultModel.setBfiName(dspModel.getBfiName());
            resultModel.setBfiSid(dspModel.getBfiSid());
            resultModel.setBfiLevel(dspModel.getForumLevel());
            Long imgSid = dspModel.getImgBinSid();
            if (imgSid != null) {
                resultModel.setBinSid(imgSid.longValue());
            } else {
                resultModel.setBinSid(0);
            }
            result.add(resultModel);
        }

        return result;
    }

    /**
     * <br>[機  能] フォーラムSIDとアイコンバイナリSIDを照合する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param imgBinSid 画像バイナリSID
     * @return true:照合OK false:照合NG
     * @throws SQLException SQL実行例外
     */
    public boolean cheIcoHnt(Connection con, int bfiSid, Long imgBinSid)
    throws SQLException {

        boolean icoCheckFlg = false;

        //フォーラムSIDとアイコンバイナリSIDの組み合わせチェック
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        boolean existForIcoFlg = bfiDao.existBbsForIco(bfiSid, imgBinSid);

        if (existForIcoFlg) {
            icoCheckFlg = true;
        }

        return icoCheckFlg;
    }
}