package jp.groupsession.v2.usr.usr082;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnUsrAdmSortConfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrAdmSortConfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.biz.UsrCommonBiz;
import jp.groupsession.v2.usr.dao.UsrAconfDao;
import jp.groupsession.v2.usr.model.UsrAconfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ユーザ情報 管理者設定 デフォルト表示順設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr082Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Usr082Biz.class);
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Usr082Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr082ParamModel
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setInitData(Usr082ParamModel paramMdl,
            Connection con) throws SQLException {

        if (paramMdl.getUsr082initKbn() == 0) {
            //DBより現在の設定を取得する。(なければデフォルト)
            UsrCommonBiz biz = new UsrCommonBiz(con, reqMdl__);
            CmnUsrAdmSortConfModel sortAdmconf = biz.getSortAdmConfModel(con);

            //ソート設定区分
            int sortKbn = sortAdmconf.getUasSortKbn();
            paramMdl.setUsr082DefoDspKbn(sortKbn);
            //ソート1
            paramMdl.setUsr082AdSortKey1(sortAdmconf.getUasSortKey1());
            paramMdl.setUsr082AdSortOrder1(sortAdmconf.getUasSortOrder1());

            //ソート2
            paramMdl.setUsr082AdSortKey2(sortAdmconf.getUasSortKey2());
            paramMdl.setUsr082AdSortOrder2(sortAdmconf.getUasSortOrder2());

            UsrAconfModel aconf = biz.getAConfModel(con);
            paramMdl.setUsr082QrDisp(aconf.getUadQrKbn());

            //初期表示区分を更新
            paramMdl.setUsr082initKbn(1);
        }

        //ソートキーラベル
        ArrayList<LabelValueBean> sortLabel = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl__);
        /** メッセージ 社員/職員番号 **/
        String syainNumber = gsMsg.getMessage("cmn.employee.staff.number");
        /** メッセージ 氏名 **/
        String name = gsMsg.getMessage("cmn.name");
        /** メッセージ 役職 **/
        String post = gsMsg.getMessage("cmn.post");
        //生年月日
        String birthday = gsMsg.getMessage("cmn.birthday");
        //ソートキー1
        String textSortkey1 = gsMsg.getMessage("cmn.sortkey") + 1;
        //ソートキー2
        String textSortkey2 = gsMsg.getMessage("cmn.sortkey") + 2;

        String[] arrayLabel = {syainNumber, name, post,
                             birthday, textSortkey1, textSortkey2};

        for (int i = 0; i < GSConstUser.LIST_SORT_KEY_USR.length; i++) {
            String label = arrayLabel[i];
            String value = Integer.toString(GSConstUser.LIST_SORT_KEY_USR[i]);
            sortLabel.add(new LabelValueBean(label, value));
        }
        paramMdl.setUsr082SortKeyLabel(sortLabel);

    }

    /**
     * <br>[機  能] 管理者ソート設定をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Usr082ParamModel
     * @param umodel ユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void setSortAdmConfig(Usr082ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {

        //DBより現在の設定を取得する。(なければデフォルト)
        UsrCommonBiz biz = new UsrCommonBiz(con, reqMdl__);
        CmnUsrAdmSortConfModel sortAdmconf = biz.getSortAdmConfModel(con);

        //データを設定
        sortAdmconf.setUasSortKbn(paramMdl.getUsr082DefoDspKbn());
        sortAdmconf.setUasSortKey1(paramMdl.getUsr082AdSortKey1());
        sortAdmconf.setUasSortOrder1(paramMdl.getUsr082AdSortOrder1());
        sortAdmconf.setUasSortKey2(paramMdl.getUsr082AdSortKey2());
        sortAdmconf.setUasSortOrder2(paramMdl.getUsr082AdSortOrder2());
        sortAdmconf.setUasEuid(umodel.getUsrsid());
        UDate now = new UDate();
        sortAdmconf.setUasEdate(now);

        //DB更新
        boolean commitFlg = false;
        try {
            CmnUsrAdmSortConfDao dao = new CmnUsrAdmSortConfDao(con);
            int count = dao.updateSortConfig(sortAdmconf);
            if (count <= 0) {
                sortAdmconf.setUasAuid(umodel.getUsrsid());
                sortAdmconf.setUasAdate(now);
                dao.insert(sortAdmconf);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("管理者ソート設定の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }
    /**
     * 
     * <br>[機  能]QRコードの表示設定を行います。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param umodel ユーザモデル
     * @param con コネクション
     * @throws SQLException SQL実行エラー
     */
    public void qrSetting(Usr082ParamModel paramMdl,
            BaseUserModel umodel, Connection con) throws SQLException {
        //QRコード表示設定更新
        boolean commitFlg = false;
        UDate now = new UDate();
        try {
            UsrAconfDao aconfDao = new UsrAconfDao(con);
            UsrAconfModel aconfMdl = new UsrAconfModel();
            aconfMdl.setUadEdate(now);
            aconfMdl.setUadEuid(umodel.getUsrsid());
            aconfMdl.setUadQrKbn(paramMdl.getUsr082QrDisp());
            //更新データが存在しない場合は新規作成
            int count = aconfDao.updateQrDispKbn(aconfMdl);
            if (count <= 0) {
                //CSVエクスポート制限設定(デフォルトは管理者のみ)
                aconfMdl.setUadExport(GSConstUser.CSV_EXPORT_ADMIN);
                aconfMdl.setUadAuid(umodel.getUsrsid());
                aconfMdl.setUadAdate(now);
                aconfDao.insert(aconfMdl);
            }
            commitFlg = true;
        } catch (SQLException e) {
            log__.error("管理者ソート設定の更新に失敗", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            }
        }
    }
}
