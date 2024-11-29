package jp.groupsession.v2.rng.rng210;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngIdDao;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.rng210kn.Rng210knParamModel;

/**
 * <br>[機  能] 稟議 管理者設定 基本設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng210Biz {


    /**
     * <br>[機  能] 初期表示する。
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param cntCon
     * @param cmd コマンド
     * @throws Exception 実行例外
     */
    public void setInit(
            Connection con,
            Rng210ParamModel paramMdl,
            int userSid,
            String cmd) throws Exception {

        if (paramMdl.getRng200Sid() > 0) {
            paramMdl.setRng210Cmd(1);
            if (!cmd.equals("backList")) {
                RingiIdModel mdl = new RingiIdModel();
                RngIdDao dao = new RngIdDao(con);
                mdl = dao.selectData(paramMdl.getRng200Sid());
                if (mdl != null) {
                    paramMdl.setRng210Format(mdl.getRngFormat());
                    paramMdl.setRng210Init(String.valueOf(mdl.getRngInit()));
                    paramMdl.setRng210Manual(mdl.getRngManual());
                    paramMdl.setRng210Reset(mdl.getRngReset());
                    paramMdl.setRng210Zeroume(String.valueOf(mdl.getRngZeroume()));
                    paramMdl.setRng210Title(mdl.getRngTitle());

                    String format = mdl.getRngFormat();
                    formatSplit(paramMdl, format, con);
                }
            } else {
                String formatWord = paramMdl.getRng210Format();
                formatSplit(paramMdl, formatWord, con);
            }
        } else {
            String formatWord = paramMdl.getRng210Format();
            formatSplit(paramMdl, formatWord, con);
        }

        ArrayList<LabelValueBean> folderLabel = new ArrayList<LabelValueBean>();
        LabelValueBean labelBean = null;

        for (int nIdx = 0; nIdx < RngConst.CONF_LIST_FORMAT.length; nIdx++) {
            labelBean = new LabelValueBean();
            labelBean.setLabel(RngConst.CONF_LIST_FORMAT[nIdx]);
            labelBean.setValue(String.valueOf(RngConst.CONF_LIST_FORMAT_NO[nIdx]));
            folderLabel.add(labelBean);
        }
        paramMdl.setRng210FormatListLabel(folderLabel);
    }

    /**
     * <br>[機  能] 稟議申請IDの削除を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 例外
     * @return 削除したモデル
     */
    public RingiIdModel deleteKanryoRng(Rng210knParamModel paramMdl, Connection con)
    throws Exception {

        RingiIdModel ret = null;

        if (paramMdl.getRng200Sid() > 0) {
            RngIdDao dao = new RngIdDao(con);

            // 削除前にデータ取得
            ret = dao.selectData(paramMdl.getRng200Sid());

            // 削除実行
            dao.delete(paramMdl.getRng200Sid());
        }
        return ret;
    }


    /**
     * <br>[機  能] フォーマット値の分割を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param format 1行フォーマット値
     * @param con コネクション
     */
    public void formatSplit(Rng210ParamModel paramMdl,
            String format,
            Connection con) {

        ArrayList<Rng210ListModel> lMdlList;
        RngBiz rngBiz = new RngBiz(con);

        lMdlList = rngBiz.getRngidFormatList(format);
        paramMdl.setRng210FormatList(lMdlList);
    }
}
