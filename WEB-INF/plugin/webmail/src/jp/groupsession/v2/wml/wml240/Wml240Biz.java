package jp.groupsession.v2.wml.wml240;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz.SortResult;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlUsedDataBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateFileDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlMailTemplateModel;

/**
 * <br>[機  能] WEBメール メールテンプレート管理画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml240Biz {

    /**
     * <br>[機  能] メールテンプレートの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteTemplate(Connection con, Wml240ParamModel paramMdl, int sessionUserSid)
    throws SQLException {

        int wtpSid = paramMdl.getWmlEditTemplateId();

        //メールテンプレート情報のデータ使用量を登録(削除対象のデータ使用量を減算)
        WmlUsedDataBiz usedDataBiz = new WmlUsedDataBiz(con);
        usedDataBiz.insertTemplateSize(wtpSid, false);

        //メールテンプレートを削除する
        WmlMailTemplateDao wlDao = new WmlMailTemplateDao(con);
        wlDao.delete(wtpSid);

        //メールテンプレートに関連するバイナリ―情報を削除する
        WmlMailTemplateFileDao tempFileDao = new WmlMailTemplateFileDao(con);
        tempFileDao.removeTemplateBinData(wtpSid, sessionUserSid, new UDate());

        //メールテンプレート_ファイルを削除する
        tempFileDao.delete(wtpSid);

    }

    /**
     * <br>[機  能] 初期表示を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
                             Wml240ParamModel paramMdl,
                             int userSid,
                             RequestModel reqMdl) throws SQLException {

        int templateType = GSConstWebmail.WTP_TYPE_COMMON;
        int selectWacSid = paramMdl.getWmlAccountSid();
        if (paramMdl.getWmlMailTemplateKbn() != GSConstWebmail.MAILTEMPLATE_COMMON) {
            templateType = GSConstWebmail.WTP_TYPE_ACCOUNT;

            //アカウント名取得
            WmlAccountDao wacDao = new WmlAccountDao(con);
            WmlAccountModel wacMdl = wacDao.select(selectWacSid);
            paramMdl.setWml240accountName(wacMdl.getWacName());
        }

        WmlMailTemplateDao templateDao = new WmlMailTemplateDao(con);
        List<WmlMailTemplateModel> templateList
            = templateDao.getTemplateList(templateType, selectWacSid);
        paramMdl.setTemplateList(templateList);
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param changeKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @param sessionUserSid セッションユーザSID
     * @return updateフラグ
     * @throws SQLException SQL実行時例外
     */
    public boolean updateSort(Connection con, Wml240ParamModel paramMdl,
                                    int changeKbn, int sessionUserSid)
    throws SQLException {

        boolean updateFlg = false;
        //ラジオ選択値取得
        int motoSid = paramMdl.getWml240SortRadio();
        if (motoSid <= 0) {
            return false;
        }

        int wacSid = paramMdl.getWmlAccountSid();
        int type = GSConstWebmail.WTP_TYPE_COMMON;
        if (paramMdl.getWmlMailTemplateKbn() != GSConstWebmail.MAILTEMPLATE_COMMON) {
            type = GSConstWebmail.WTP_TYPE_ACCOUNT;
        }
        final int wtpType = type;


        WmlMailTemplateDao templateDao = new WmlMailTemplateDao(con);
        SortResult<WmlMailTemplateModel> result = null;
        SortChangeBiz<WmlMailTemplateModel> sortBiz =
                SortChangeBiz.<WmlMailTemplateModel> builder()
                .setFuncTargetList(() -> {
                    return templateDao.getTemplateList(wtpType, wacSid);
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getWtpSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getWtpOrder();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getWtpSid() == m2.getWtpSid()) {
                        return 0;
                    } else {
                        return (m1.getWtpSid() - m2.getWtpSid())
                                / Math.abs(m1.getWtpSid() - m2.getWtpSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    m.setWtpOrder(newSort);
                    templateDao.delete(m.getWtpSid());
                    templateDao.insert(m);
                })
                .build();

        if (changeKbn == GSConst.SORT_UP) {
            result = sortBiz.up();
        } else if (changeKbn == GSConst.SORT_DOWN) {
            result = sortBiz.down();
        }
        if (result != null) {
            updateFlg = true;
        }

        return updateFlg;
    }

    /**
     * <br>[機  能] アカウント名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param accountSid アカウントSID
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    public String getAccountName(Connection con,
                             int accountSid) throws SQLException {

        WmlAccountDao wacDao = new WmlAccountDao(con);
        WmlAccountModel wacMdl = wacDao.select(accountSid);
        return wacMdl.getWacName();
    }

    /**
     * <br>[機  能] テンプレート名取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param targetSid ターゲットSID
     * @return テンプレート名
     * @throws SQLException SQL実行時例外
     */
    public String getTempName(Connection con,
                             int targetSid) throws SQLException {

        WmlMailTemplateDao templateDao = new WmlMailTemplateDao(con);
        WmlMailTemplateModel tempMdl = templateDao.select(targetSid);
        return tempMdl.getWtpName();
    }
}
