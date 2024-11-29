package jp.groupsession.v2.bbs.bbs120;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.dao.BbsAdmConfDao;
import jp.groupsession.v2.bbs.model.BbsAdmConfModel;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnBatchJobDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBatchJobModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 掲示板 自動データ削除設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs120Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs120Biz.class);

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Bbs120Biz() {
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param umodel セッションユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(
            Bbs120ParamModel paramMdl,
            RequestModel reqMdl,
            BaseUserModel umodel,
            Connection con) throws SQLException {
        int sessionUid = umodel.getUsrsid();
        //DBより現在の設定を取得する。(なければデフォルト)
        BbsBiz biz = new BbsBiz();
        BbsAdmConfModel conf = biz.getBbsAdminData(con, sessionUid);
        paramMdl.setBbs120AtdelFlg(conf.getBacAtdelFlg());
        paramMdl.setBbs120AtdelYear(conf.getBacAtdelY());
        paramMdl.setBbs120AtdelMonth(conf.getBacAtdelM());
        setLabel(reqMdl, paramMdl);
    }

    /**
     * <br>[機  能] 表示用データを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行エラー
     */
    public void setShowData(Bbs120ParamModel paramMdl, Connection con, RequestModel reqMdl)
    throws SQLException  {

        //バッチ処理実行時間を取得
        CmnBatchJobDao batDao = new CmnBatchJobDao(con);
        CmnBatchJobModel batMdl = batDao.select();
        String batchTime = "";
        if (batMdl != null) {
            batchTime = String.valueOf(batMdl.getBatFrDate());
        }
        paramMdl.setBatchTime(batchTime);
        setLabel(reqMdl, paramMdl);
    }

    /**
     * <br>[機  能] 自動削除設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param umodel ユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setAutoDeleteSetting(
            Bbs120ParamModel paramMdl,
            RequestModel reqMdl,
            BaseUserModel umodel,
            Connection con) throws SQLException {

        int sessionUid = umodel.getUsrsid();
        //既存のデータを取得
        //DBより現在の設定を取得する。(なければデフォルト)
        BbsBiz biz = new BbsBiz();
        BbsAdmConfModel conf = biz.getBbsAdminData(con, sessionUid);
        //データを設定
        conf.setBacAtdelFlg(paramMdl.getBbs120AtdelFlg());
        if (paramMdl.getBbs120AtdelFlg() == GSConstBulletin.AUTO_DELETE_OFF) {
            conf.setBacAtdelY(-1);
            conf.setBacAtdelM(-1);
        } else {
            conf.setBacAtdelY(paramMdl.getBbs120AtdelYear());
            conf.setBacAtdelM(paramMdl.getBbs120AtdelMonth());
        }
        conf.setBacEuid(umodel.getUsrsid());
        UDate now = new UDate();
        conf.setBacEdate(now);
        //DB更新
        boolean commitFlg = false;
        try {
            BbsAdmConfDao dao = new BbsAdmConfDao(con);
            int count = dao.updateAutoDelete(conf);
            if (count <= 0) {
                conf.setBacAuid(umodel.getUsrsid());
                conf.setBacAdate(now);
                dao.insert(conf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("共有範囲設定の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }

    /**
     * <br>[機  能] 年月を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     */
    public void setLabel(RequestModel reqMdl, Bbs120ParamModel paramMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);

        //年ラベル作成
        ArrayList<LabelValueBean> bbs120AtdelYearLabel = new ArrayList<LabelValueBean>();
        for (int nLabel : GSConst.DEL_YEAR_DATE) {
            String label = String.valueOf(nLabel);
            bbs120AtdelYearLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[] {label}), label));
        }
        paramMdl.setBbs120AtdelYearLabel(bbs120AtdelYearLabel);
        //月ラベル作成
        ArrayList<LabelValueBean> bbs120AtdelMonthLabel = new ArrayList<LabelValueBean>();
        for (int nLabel : GSConst.DEL_MONTH_DATE) {
            String label = String.valueOf(nLabel);
            bbs120AtdelMonthLabel.add(new LabelValueBean(
                                      gsMsg.getMessage("cmn.months", new String[] {label}),
                                      label));
        }
        paramMdl.setBbs120AtdelMonthLabel(bbs120AtdelMonthLabel);
    }
}
