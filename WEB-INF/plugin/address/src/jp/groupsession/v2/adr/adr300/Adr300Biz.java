package jp.groupsession.v2.adr.adr300;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrAconfDao;
import jp.groupsession.v2.adr.dao.AdrUconfDao;
import jp.groupsession.v2.adr.model.AdrAconfModel;
import jp.groupsession.v2.adr.model.AdrUconfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 管理者設定 初期値設定画面ののビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr300Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr300Biz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr300Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }
    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr300ParamModel
     * @param con コネクション
     * @throws Exception SQL実行エラー
     */
    public void setInitData(Adr300ParamModel paramMdl,
            Connection con) throws Exception {

        log__.debug("setInitData START");

        //コンボデータセット
        setAdr300CombData(paramMdl);
        //管理者設定取得
        AdrAconfDao dao = new AdrAconfDao(con);
        AdrAconfModel model = dao.selectAconf();
        if (model != null) {
            if (model.getAacVrmEdit() == GSConstAddress.MEM_DSP_ADM) {
                paramMdl.setAdr300MemDspKbn(GSConstAddress.MEM_DSP_ADM);
                paramMdl.setAdr300PermitKbn(model.getAacPvwKbn());
                if (model.getAacPvwKbn() == GSConst.ADR_VIEWPERMIT_NORESTRICTION) {
                    paramMdl.setAdr300EditKbn(model.getAacPetKbn());
                }
            } else {
                //個人設定
                if (model != null) {
                    paramMdl.setAdr300MemDspKbn(GSConstAddress.MEM_DSP_USR);
                    paramMdl.setAdr300PermitKbn(model.getAacPvwKbn());
                    if (model.getAacPvwKbn() == GSConst.ADR_VIEWPERMIT_NORESTRICTION) {
                        paramMdl.setAdr300EditKbn(model.getAacPetKbn());
                    }
                }
            }
        }
    }
    /**
     * <br>[機  能] 初期値設定（管理者）をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr300ParamModel
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return AdrAconfModel アドレス管理者設定モデル
     */
    public AdrAconfModel setAdrAconf(Adr300ParamModel paramMdl,
            int usrSid, Connection con) throws SQLException {

      boolean commitFlg = false;
      AdrAconfModel model = new AdrAconfModel();
      try {
          //DBの存在確認
          AdrAconfDao dao = new AdrAconfDao(con);
          model = dao.selectAconf();
          //画面値セット
          AdrAconfModel crtMdl = createAdrAconfData(paramMdl,
                     usrSid, model, GSConstAddress.MEM_DSP_ADM);

          if (model == null) {
              dao.insert(crtMdl);
          } else {
              dao.update(crtMdl);
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
      return model;
    }

    /**
     * <br>[機  能] 初期値設定情報（管理者）を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr300ParamModel
     * @param usrSid ユーザSID
     * @param model 管理者設定モデル
     * @param createKbn 管理者設定 or 個人設定
     * @return AdrAconfModel 管理者設定
     */
    public AdrAconfModel createAdrAconfData(
            Adr300ParamModel paramMdl, int usrSid, AdrAconfModel model, int createKbn) {

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

        if (createKbn == GSConstAddress.MEM_DSP_ADM) {
            //管理者設定
            int prmKbn = paramMdl.getAdr300PermitKbn();
            if (prmKbn == GSConst.ADR_VIEWPERMIT_OWN) {
                paramMdl.setAdr300EditKbn(GSConst.ADR_VIEWPERMIT_OWN);
            } else if (prmKbn == GSConst.ADR_VIEWPERMIT_GROUP) {
                paramMdl.setAdr300EditKbn(GSConst.ADR_VIEWPERMIT_GROUP);
            } else if (prmKbn == GSConst.ADR_VIEWPERMIT_USER) {
                paramMdl.setAdr300EditKbn(GSConst.ADR_VIEWPERMIT_USER);
            }

            mdl.setAacVrmEdit(GSConstAddress.MEM_DSP_ADM);
            mdl.setAacPvwKbn(paramMdl.getAdr300PermitKbn());
            mdl.setAacPetKbn(paramMdl.getAdr300EditKbn());
        } else {
            //個人設定
            int prmKbn = paramMdl.getAdr300PermitKbn();
            if (prmKbn == GSConst.ADR_VIEWPERMIT_OWN) {
                paramMdl.setAdr300EditKbn(GSConst.ADR_VIEWPERMIT_OWN);
            } else if (prmKbn == GSConst.ADR_VIEWPERMIT_GROUP) {
                paramMdl.setAdr300EditKbn(GSConst.ADR_VIEWPERMIT_GROUP);
            } else if (prmKbn == GSConst.ADR_VIEWPERMIT_USER) {
                paramMdl.setAdr300EditKbn(GSConst.ADR_VIEWPERMIT_USER);
            }
            mdl.setAacVrmEdit(GSConstAddress.MEM_DSP_USR);
            mdl.setAacPvwKbn(paramMdl.getAdr300PermitKbn());
            mdl.setAacPetKbn(paramMdl.getAdr300EditKbn());
        }


        mdl.setAacAuid(usrSid);
        mdl.setAacAdate(nowDate);
        mdl.setAacEuid(usrSid);
        mdl.setAacEdate(nowDate);

        return mdl;
    }
    /**
     * <br>[機  能] 初期値設定（個人）をDBに保存する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr300ParamModel
     * @param usrSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return AdrUconfModel アドレス個人設定モデル
     */
    public AdrUconfModel setAdrUconf(Adr300ParamModel paramMdl,
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
          AdrAconfModel acrtMdl = createAdrAconfData(paramMdl,
                       usrSid, amodel, GSConstAddress.MEM_DSP_USR);

          if (amodel == null) {
              adao.insert(acrtMdl);
          } else {
              adao.update(acrtMdl);
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
     * @param paramMdl Adr300ParamModel
     * @param usrSid ユーザSID
     * @param model 個人設定モデル
     * @return AdrUconfModel 個人設定
     */
    public AdrUconfModel createAdrUconfData(
            Adr300ParamModel paramMdl, int usrSid, AdrUconfModel model) {

        UDate nowDate = new UDate();
        AdrUconfModel mdl = new AdrUconfModel();

        if (model != null) {
            mdl = model;
        } else {
            mdl.setUsrSid(usrSid);
            mdl.setAucComcount(0);
            mdl.setAucAdrcount(0);
        }

        //編集権限を設定
        int prmKbn = paramMdl.getAdr300PermitKbn();
        if (prmKbn == GSConst.ADR_VIEWPERMIT_OWN) {
            paramMdl.setAdr300EditKbn(GSConst.ADR_VIEWPERMIT_OWN);
        } else if (prmKbn == GSConst.ADR_VIEWPERMIT_GROUP) {
            paramMdl.setAdr300EditKbn(GSConst.ADR_VIEWPERMIT_GROUP);
        } else if (prmKbn == GSConst.ADR_VIEWPERMIT_USER) {
            paramMdl.setAdr300EditKbn(GSConst.ADR_VIEWPERMIT_USER);
        }

        mdl.setAucPermitView(paramMdl.getAdr300PermitKbn());
        mdl.setAucPermitEdit(paramMdl.getAdr300EditKbn());

        mdl.setAucAuid(usrSid);
        mdl.setAucAdate(nowDate);
        mdl.setAucEuid(usrSid);
        mdl.setAucEdate(nowDate);

        return mdl;
    }

    /**
     * <br>[機  能] コンボデータをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr300ParamModel
     */
    public void setAdr300CombData(Adr300ParamModel paramMdl) {
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

        paramMdl.setAdr300PermitKbnLabel(kbnLabel);
        paramMdl.setAdr300EditKbnLabel(kbnLabel);
    }
}
