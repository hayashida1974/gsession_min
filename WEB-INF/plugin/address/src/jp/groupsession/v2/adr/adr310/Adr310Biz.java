package jp.groupsession.v2.adr.adr310;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.dao.AdrUconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.adr.model.AdrUconfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 個人設定 初期値設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr310Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr310Biz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr310Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }
    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr310ParamModel
     * @param con コネクション
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Adr310ParamModel paramMdl,
            Connection con) throws Exception {

        log__.debug("setInitData START");

        //セッション情報を取得
        HttpSession session = reqMdl_.getSession();
        BaseUserModel usModel =
            (BaseUserModel) session.getAttribute(GSConst.SESSION_KEY);
        int sessionUsrSid = usModel.getUsrsid();

        //コンボデータセット
        setAdr310CombData(paramMdl);
        //個人設定取得
        AdrUconfDao uconfDao = new AdrUconfDao(con);
        AdrUconfModel adrUconfMdl = uconfDao.select(sessionUsrSid);
        if (adrUconfMdl != null) {
            paramMdl.setAdr310MemDspKbn(GSConstAddress.MEM_DSP_USR);
            paramMdl.setAdr310PermitKbn(adrUconfMdl.getAucPermitView());
            if (adrUconfMdl.getAucPermitView() == GSConst.ADR_VIEWPERMIT_NORESTRICTION) {
                paramMdl.setAdr310EditKbn(adrUconfMdl.getAucPermitEdit());
            }
        } else {
            //管理者設定から初期値取得
            AdrAconfDao aconfDao = new AdrAconfDao(con);
            AdrAconfModel aconfMdl = aconfDao.selectAconf();
            if (aconfMdl != null) {
                paramMdl.setAdr310MemDspKbn(GSConstAddress.MEM_DSP_USR);
                paramMdl.setAdr310PermitKbn(aconfMdl.getAacPvwKbn());
                if (aconfMdl.getAacPvwKbn()  == GSConst.ADR_VIEWPERMIT_NORESTRICTION) {
                    paramMdl.setAdr310PermitKbn(aconfMdl.getAacPetKbn());
                }
            }
        }
    }

    /**
     * <br>[機  能] 初期値設定（個人）をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr310ParamModel
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return AdrUconfModel アドレス個人設定モデル
     */
    public AdrUconfModel setAdrUconf(Adr310ParamModel paramMdl,
            int usrSid, Connection con) throws SQLException {

      boolean commitFlg = false;
      AdrAconfModel amodel = new AdrAconfModel();
      AdrUconfModel umodel = new AdrUconfModel();
      try {
          //DBの存在確認
          AdrAconfDao adao = new AdrAconfDao(con);
          amodel = adao.selectAconf();
          AdrUconfDao udao = new AdrUconfDao(con);
          umodel = udao.select(usrSid);

          //画面値セット
          AdrAconfModel acrtMdl = createAdrAconfData(paramMdl, usrSid, amodel);
          AdrUconfModel ucrtMdl = createAdrUconfData(paramMdl, usrSid, umodel);

          if (amodel == null) {
              adao.insert(acrtMdl);
          } else {
              adao.update(acrtMdl);
          }

          if (umodel == null) {
              udao.insert(ucrtMdl);
          } else {
              udao.update(ucrtMdl);
          }
          commitFlg = true;

      } catch (SQLException e) {
          log__.error("", e);
          throw e;
      } finally {
          if (commitFlg) {
              con.commit();
          }
      }
      return umodel;
    }

    /**
     * <br>[機  能] 初期値設定情報（個人）を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr310ParamModel
     * @param usrSid ユーザSID
     * @param model 個人設定モデル
     * @return AdrUconfModel 個人設定
     */
    public AdrUconfModel createAdrUconfData(
            Adr310ParamModel paramMdl, int usrSid, AdrUconfModel model) {

        UDate nowDate = new UDate();
        AdrUconfModel mdl = new AdrUconfModel();

        if (model != null) {
            mdl = model;
        } else {
            mdl.setUsrSid(usrSid);
            mdl.setAucComcount(Integer.parseInt(GSConstAddress.DEFAULT_COMCOUNT));
            mdl.setAucAdrcount(Integer.parseInt(GSConstAddress.DEFAULT_ADRCOUNT));
        }

        //編集権限を設定
        int prmKbn = paramMdl.getAdr310PermitKbn();
        if (prmKbn == GSConst.ADR_VIEWPERMIT_OWN) {
            paramMdl.setAdr310EditKbn(GSConst.ADR_VIEWPERMIT_OWN);
        } else if (prmKbn == GSConst.ADR_VIEWPERMIT_GROUP) {
            paramMdl.setAdr310EditKbn(GSConst.ADR_VIEWPERMIT_GROUP);
        } else if (prmKbn == GSConst.ADR_VIEWPERMIT_USER) {
            paramMdl.setAdr310EditKbn(GSConst.ADR_VIEWPERMIT_USER);
        }

        mdl.setAucPermitView(paramMdl.getAdr310PermitKbn());
        mdl.setAucPermitEdit(paramMdl.getAdr310EditKbn());

        mdl.setAucAuid(usrSid);
        mdl.setAucAdate(nowDate);
        mdl.setAucEuid(usrSid);
        mdl.setAucEdate(nowDate);

        return mdl;
    }
    /**
     * <br>[機  能] 初期値設定情報（管理者）を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr310ParamModel
     * @param usrSid ユーザSID
     * @param model 管理者設定モデル
     * @return AdrAconfModel 管理者設定
     */
    public AdrAconfModel createAdrAconfData(
            Adr310ParamModel paramMdl, int usrSid, AdrAconfModel model) {

        UDate nowDate = new UDate();
        AdrAconfModel mdl = new AdrAconfModel();

        if (model != null) {
            mdl = model;
        } else {
            mdl.setAacAtiEdit(0);
            mdl.setAacAcoEdit(0);
            mdl.setAacAlbEdit(0);
            mdl.setAacExport(1);
            mdl.setAacYksEdit(0);
        }

        //個人設定
        mdl.setAacVrmEdit(GSConstAddress.MEM_DSP_USR);

        mdl.setAacAuid(usrSid);
        mdl.setAacAdate(nowDate);
        mdl.setAacEuid(usrSid);
        mdl.setAacEdate(nowDate);

        return mdl;
    }
    /**
     * <br>[機  能] コンボデータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr310ParamModel
     */
    public void setAdr310CombData(Adr310ParamModel paramMdl) {
        //ソートキーラベル
        ArrayList<LabelValueBean> kbnLabel = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl_);
        String [] viewPerMitList = new String[] {
                gsMsg.getMessage("address.62"),
                gsMsg.getMessage("group.designation"),
                gsMsg.getMessage("cmn.user.specified"),
                gsMsg.getMessage("cmn.no.setting.permission")
                };

        for (int i = 0; i < GSConstAddress.VIEWPERMIT_ALL.length; i++) {
            String label = viewPerMitList[i];
            String value = Integer.toString(GSConstAddress.VIEWPERMIT_ALL[i]);
            kbnLabel.add(new LabelValueBean(label, value));
        }

        paramMdl.setAdr310PermitKbnLabel(kbnLabel);
        paramMdl.setAdr310EditKbnLabel(kbnLabel);
    }
}
