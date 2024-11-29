package jp.groupsession.v2.rng.rng200;

import java.sql.Connection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngIdDao;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.rng210.Rng210ListModel;

/**
 * <br>[機  能] 稟議 管理者設定 申請フォーマット画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng200Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng200Biz.class);


    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Rng200Biz() {
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setInitData(
            RequestModel reqMdl,
            Rng200ParamModel paramMdl,
            Connection con)  throws Exception {

        log__.debug("start");

        RngIdDao dao = new RngIdDao(con);
        List<RingiIdModel> mdl = dao.select();
        RngBiz rngBiz = new RngBiz(con);
        RngAconfModel aconfMdl = rngBiz.getRngAconf(con);

        paramMdl.setRng200Default(aconfMdl.getRarRngidDefSid());
        paramMdl.setRng200List(mdl);

        for (int idx = 0; idx < paramMdl.getRng200List().size(); idx++) {
            String format = paramMdl.getRng200List().get(idx).getRngFormat();
            List<Rng210ListModel> formatList = rngBiz.getRngidFormatList(format);

            StringBuilder sb = new StringBuilder();
            StringBuilder pattern = new StringBuilder();
            UDate now = new UDate();

            for (Rng210ListModel lMdl : formatList) {
                switch (lMdl.getRng210SelectFormat()) {
                    case "2":
                        String no = String.valueOf(paramMdl.getRng200List().get(idx).getRngZeroume());
                        if (no.equals("0")) {
                            no = String.valueOf(paramMdl.getRng200List().get(idx).getRngInit());
                        } else {
                            no = String.format("%0" + no + "d",
                                    paramMdl.getRng200List().get(idx).getRngInit());
                        }
                        sb.append(no);
                        pattern.append("No");
                        break;
                    case "3":
                        sb.append(now.getStrYear());
                        pattern.append("YYYY");
                        break;
                    case "4":
                        sb.append(now.getStrYear().substring(2, 4));
                        pattern.append("YY");
                        break;
                    case "5":
                        sb.append(now.getStrMonth());
                        pattern.append("MM");
                        break;
                    case "6":
                        sb.append(now.getStrDay());
                        pattern.append("DD");
                        break;
                    default:
                        sb.append(lMdl.getRng210FormatWord());
                        pattern.append(lMdl.getRng210FormatWord());
                        break;
                }
            }
            paramMdl.getRng200DispList().add(sb.toString());
            paramMdl.getRng200Pattern().add(pattern.toString());
        }
    }

    /**
     * <br>[機  能] 検索表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void searchData(
            RequestModel reqMdl,
            Rng200ParamModel paramMdl,
            Connection con)  throws Exception {

        log__.debug("start");

        RngIdDao dao = new RngIdDao(con);
        List<RingiIdModel> mdl = dao.serachSelect(paramMdl.getRng200keyword());
        RngBiz rngBiz = new RngBiz(con);
        RngAconfModel aconfMdl = rngBiz.getRngAconf(con);

        paramMdl.setRng200Default(aconfMdl.getRarRngidDefSid());
        paramMdl.setRng200List(mdl);
    }

    /**
     * <br>[機  能] OK押下時
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setOkData(
            RequestModel reqMdl,
            Rng200ParamModel paramMdl,
            Connection con)  throws Exception {

        log__.debug("ok");

        RngIdDao dao = new RngIdDao(con);
        String sFormat = dao.select(paramMdl.getRng200Default());
        paramMdl.setRng200DefFormat(sFormat);
    }

    /**
     * <br>[機  能] デフォルト申請IDをDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws Exception 実行例外
     */
    /*public void entryAdmConf(
            Connection con,
            Rng200ParamModel paramMdl,
            int userSid) throws Exception {

        RngAconfModel aconfMdl = new RngAconfModel();
        aconfMdl.setRarRngidDefSid(paramMdl.getRng200Default());
        RngAconfDao aconfDao = new RngAconfDao(con);
        aconfDao.updateDefault(aconfMdl);
    }*/
}
