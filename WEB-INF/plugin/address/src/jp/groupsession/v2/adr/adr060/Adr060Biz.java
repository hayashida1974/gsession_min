package jp.groupsession.v2.adr.adr060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.dao.AdrUconfDao;
import jp.groupsession.v2.adr.model.AdrUconfModel;
import jp.groupsession.v2.cmn.model.RequestModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳 表示件数設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr060Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Adr060Biz.class);
    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr060Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl アクションフォーム
     * @param sessionUserSid セッションユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Adr060ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        log__.debug("start");

        //表示件数コンボを設定
        paramMdl.setAdr060DspCountList(__getDspCount());

        //DBより現在の設定を取得する。(なければデフォルト)
        AdrUconfDao dao = new AdrUconfDao(con);
        AdrUconfModel model = dao.select(sessionUserSid);

        if (model == null) {
            paramMdl.setAdr060DspCount(GSConstAddress.DEFAULT_ADRCOUNT);
            paramMdl.setAdr060ComCount(GSConstAddress.DEFAULT_COMCOUNT);
        } else {
            paramMdl.setAdr060DspCount(
                    __getInitComb(NullDefault.getString(
                            paramMdl.getAdr060DspCount(),
                            (String.valueOf(model.getAucAdrcount())))));
            paramMdl.setAdr060ComCount(
                    __getInitComb(NullDefault.getString(
                            paramMdl.getAdr060ComCount(),
                            (String.valueOf(model.getAucComcount())))));
        }
    }

    /**
     * <br>[機  能] 表示件数コンボの初期値を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @param combValue コンボ値
     * @return labelList ラベルリスト
     */
    private String __getInitComb(String combValue) {

        String defoValue = combValue;

        if (defoValue.equals("0")) {
            //コンボ値に0が入っているとき
            defoValue = "30";
        }

        return defoValue;
    }

    /**
     * <br>[機  能] 表示件数コンボの表示を設定する。
     * <br>[解  説]
     * <br>[備  考]
     * @return labelList ラベルリスト
     */
    private List <LabelValueBean> __getDspCount() {

        List <LabelValueBean> labelList = new ArrayList <LabelValueBean>();

        for (int count = 10; count <= 50; count += 10) {
            String strCount = String.valueOf(count);
            labelList.add(new LabelValueBean(strCount, strCount));
        }

        return labelList;
    }

    /**
     * <br>[機  能] 表示件数設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr060ParamModel
     * @param sessionUserSid ユーザSID
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     * @return AdrUconfModel 個人設定モデル
     */
    public AdrUconfModel setAdrUconfSetting(
            Adr060ParamModel paramMdl,
            int sessionUserSid,
            Connection con) throws SQLException {

        boolean commitFlg = false;
        AdrUconfModel model = new AdrUconfModel();

        try {
            //DBの存在確認
            AdrUconfDao dao = new AdrUconfDao(con);
            model = dao.select(sessionUserSid);
            //画面値セット
            AdrUconfModel crtMdl = createAdrUconfData(paramMdl, sessionUserSid, model);

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
     * <br>[機  能] 表示件数設定情報を作成
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Adr060ParamModel
     * @param usrSid ユーザSID
     * @param auDataMdl 個人設定情報モデル
     * @return KhmSyaikbnModel 社員区分情報
     */
    public AdrUconfModel createAdrUconfData(
            Adr060ParamModel paramMdl, int usrSid, AdrUconfModel auDataMdl) {

        UDate nowDate = new UDate();
        AdrUconfModel mdl = new AdrUconfModel();

        mdl.setUsrSid(usrSid);
        mdl.setAucAdrcount(Integer.parseInt(paramMdl.getAdr060DspCount()));
        mdl.setAucComcount(Integer.parseInt(paramMdl.getAdr060ComCount()));
        mdl.setAucAuid(usrSid);
        mdl.setAucAdate(nowDate);
        mdl.setAucEuid(usrSid);
        mdl.setAucEdate(nowDate);

        if (auDataMdl != null) {
            mdl.setAucPermitEdit(auDataMdl.getAucPermitEdit());
            mdl.setAucPermitView(auDataMdl.getAucPermitView());
        }
        return mdl;
    }
}
