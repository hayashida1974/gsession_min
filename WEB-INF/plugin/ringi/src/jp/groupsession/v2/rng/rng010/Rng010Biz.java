package jp.groupsession.v2.rng.rng010;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.dao.RngDairiUserDao;
import jp.groupsession.v2.rng.dao.RngRndataDao;
import jp.groupsession.v2.rng.model.AccountDataModel;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RingiSearchModel;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngRndataModel;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;

/**
 * <br>[機  能] 稟議一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng010Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng010Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @param admin 管理者か否か true:管理者, false:一般ユーザ
     * @throws Exception 実行例外
     */
    public void setInitData(Rng010ParamModel paramMdl, RequestModel reqMdl,
                            Connection con, boolean admin)
    throws Exception {
        log__.debug("START");

        if (admin) {
            paramMdl.setRng010adminFlg(1);
        } else {
            paramMdl.setRng010adminFlg(0);
        }

        // リスト情報取得
        __getListAccount(paramMdl, reqMdl, con);

        int userSid = paramMdl.getRng010ViewAccount();

        //カテゴリ情報取得
        getCategory(paramMdl, con, reqMdl, userSid, admin);

        RngBiz rngBiz = new RngBiz(con);

        //基本設定 削除権限
        BaseUserModel uModel = reqMdl.getSmodel();

        if (rngBiz.isDeleteRingi(uModel, con) && paramMdl.getRng010DairiFlg() == 0) {
            paramMdl.setRng010delAuth(RngConst.RNG_DEL_OK);
        } else {
            paramMdl.setRng010delAuth(RngConst.RNG_DEL_NG);
        }

        log__.debug("End");
    }

    /**
     * <br>[機  能]リスト/アカウントを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl Rng010ParamModel
     * @param reqMdl リクエスト情報
     * @param con Connection
     * @throws Exception 実行例外
     */
    public void __getListAccount(Rng010ParamModel paramMdl, RequestModel reqMdl,
            Connection con)
            throws Exception {

        RingiDao dao = new RingiDao(con);


        int loginSid = reqMdl.getSmodel().getUsrsid();

        RngDairiUserDao dairiDao = new RngDairiUserDao(con);
        UDate now = new UDate();
        List<AccountDataModel> dairiList = dairiDao.select(loginSid, now, now);

        int userSid = 0;
        if (paramMdl.getRng010ViewAccount() < 100
         || paramMdl.getRng010ViewAccount() == loginSid) {
            // 初回 or ログインユーザの場合 → ログインユーザを使用
            userSid = loginSid;
        } else {
            // ログインユーザ以外の場合 → 代理人に該当ユーザがあるかチェック
            for (AccountDataModel mdl : dairiList) {
                if (mdl.getAccountSid() == paramMdl.getRng010ViewAccount()) {
                    userSid = paramMdl.getRng010ViewAccount();
                    break;
                }
            }
            // 代理人に該当するユーザがない場合、ログインユーザを使用
            if (userSid < 100) {
                userSid = loginSid;
            }
        }

        paramMdl.setRng010ViewAccount(userSid);

        //アカウント情報取得
        AccountDataModel adMdl = new AccountDataModel();
        adMdl.setAccountSid(loginSid);
        adMdl.setAccountName(reqMdl.getSmodel().getUsisei() + " " + reqMdl.getSmodel().getUsimei());
        adMdl.setUsrUkoFlg(0);
        //受信件数
        int jusinCnt = 0;
        jusinCnt = dao.getRingiDataCount(reqMdl.getSmodel().getUsrsid(), RngConst.RNG_MODE_JYUSIN);
        adMdl.setAccountCount(jusinCnt);
        paramMdl.setRng010JusinCnt(jusinCnt);
        //草稿件数
        int soukouCnt = 0;
        if (userSid == reqMdl.getSmodel().getUsrsid()) {
            soukouCnt = dao.getRingiDataCount(userSid, RngConst.RNG_MODE_SOUKOU);
        }
        paramMdl.setRng010SoukouCnt(soukouCnt);

        List<AccountDataModel> accountList = new ArrayList<AccountDataModel>();
        accountList.add(adMdl);
        accountList.addAll(dairiList);

        for (AccountDataModel mdl : dairiList) {
            if (mdl.getAccountSid() == userSid) {
                paramMdl.setRng010JusinCnt(mdl.getAccountCount());
                break;
            }
        }

        paramMdl.setRng010AccountList(accountList);
        paramMdl.setRng010ViewAccount(userSid);

        if (paramMdl.getRng010ViewAccount() == reqMdl.getSmodel().getUsrsid()) {
            paramMdl.setRng010DairiFlg(0);
        } else {
            paramMdl.setRng010DairiFlg(1);
            if (paramMdl.getRngProcMode() == RngConst.RNG_MODE_SOUKOU) {
                paramMdl.setRngProcMode(RngConst.RNG_MODE_JYUSIN);
            }
        }

        //最大件数
        int ringiCnt = dao.getRingiDataCount(userSid, paramMdl.getRngProcMode(),
                paramMdl.getRng010SearchCategory());


        //１ページの最大表示件数
        RngBiz rngBiz = new RngBiz(con);
        int viewCount = rngBiz.getViewCount(con, reqMdl.getSmodel().getUsrsid());

        //ページ調整
        int maxPage = ringiCnt / viewCount;
        if ((ringiCnt % viewCount) > 0) {
            maxPage++;
        }
        int page = paramMdl.getRng010pageTop();
        if (page < 1) {
            page = 1;
        } else if (page > maxPage) {
            page = maxPage;
        }
        paramMdl.setRng010pageTop(page);
        paramMdl.setRng010pageBottom(page);

        //ページコンボ設定
        if (ringiCnt > viewCount) {
            paramMdl.setPageList(PageUtil.createPageOptions(ringiCnt, viewCount));
        }

        //稟議情報一覧設定
        List <RingiDataModel> rngList = dao.getRingiDataList(userSid, paramMdl.getRngProcMode(),
                                            paramMdl.getRng010sortKey(),
                                            paramMdl.getRng010orderKey(),
                                            paramMdl.getRng010pageTop(), viewCount,
                                            paramMdl.getRng010SearchCategory());

        // 完了画面において、稟議の削除が可能か判定
        if (paramMdl.getRngProcMode() == RngConst.RNG_MODE_KANRYO) {

            for (int index = 0; index < rngList.size(); index++) {
                // 申請者が自分の場合
                if (rngList.get(index).getRngApplicate() == reqMdl.getSmodel().getUsrsid()) {
                    rngList.get(index).setDelFlg(RngConst.RNG_DEL_OK);
                } else {
                    rngList.get(index).setDelFlg(RngConst.RNG_DEL_NG);
                }
            }
        }

        paramMdl.setRngDataList(rngList);
    }

    /**
     * <br>[機  能] カテゴリ取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param userSid ユーザSID
     * @param isAdmin 管理者ユーザか
     * @throws SQLException SQL実行時例外
     */
    public void getCategory(Rng010ParamModel paramMdl,
            Connection con, RequestModel reqMdl, int userSid,
            boolean isAdmin)
            throws SQLException {

        ArrayList<RngTemplateCategoryModel> categoryList; // テンプレートカテゴリ一覧
        ArrayList<RngTemplateModel> tmplateList;
        ArrayList<LabelValueBean> combs;

        RngBiz biz = new RngBiz(con);
        //共有のカテゴリを取得する(システムorプラグイン管理者も制限チェックあり)
        categoryList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_SHARE,
                userSid, isAdmin, RngConst.RTPLIST_MOKUTEKI_USE);

        //共有テンプレートカテゴリのコンボリストを設定
        combs = biz.createCategoryComb(reqMdl, categoryList, true,
                isAdmin, RngConst.RTPLIST_MOKUTEKI_USE);
        paramMdl.setRng010CategoryList(combs);

        //共有のテンプレートを取得する
        tmplateList  = biz.getTemplateList(reqMdl, RngConst.RNG_TEMPLATE_SHARE,
                                           categoryList, paramMdl.getRng010SelectCategory(),
                                           isAdmin, RngConst.RTPLIST_MOKUTEKI_USE);

        // 汎用稟議テンプレート使用設定を確認。
        boolean chkHanyoFlg = true;
        RngAconfModel aconfMdl = biz.getRngAconf(con);
        if (aconfMdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_NO) {
            chkHanyoFlg = false;
        }
        paramMdl.setRng010TemplateList(tmplateList);

        // 個人テンプレート使用制限確認 汎用稟議が使用できないとき個人テンプレートも使用できない
        if (aconfMdl.getRarTemplatePersonalFlg() == RngConst.RAR_TEMPLATE_PERSONAL_FLG_NO
                || !chkHanyoFlg) {
            // 左側メニューボタンを表示しない。
            paramMdl.setRng010TemplatePersonalFlg(RngConst.RAR_TEMPLATE_PERSONAL_FLG_NO);
            return;
        }

        //個人のカテゴリを取得する
        categoryList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_PRIVATE,
                                                   userSid, true, RngConst.RTPLIST_MOKUTEKI_USE);

        //個人テンプレートカテゴリのコンボリストを設定
        combs = biz.createCategoryComb(reqMdl, categoryList, true,
                true, RngConst.RTPLIST_MOKUTEKI_USE);
        paramMdl.setRng010CategoryListUser(combs);

        //個人のテンプレートを取得する
        tmplateList  = biz.getTemplateList(reqMdl, RngConst.RNG_TEMPLATE_PRIVATE,
                                           categoryList, paramMdl.getRng010SelectCategoryUser(),
                                           true, RngConst.RTPLIST_MOKUTEKI_USE);
        paramMdl.setRng010TemplateListUser(tmplateList);
    }

    /**
     * <br>[機  能] 稟議名一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return stringリスト
     * @throws Exception 実行例外
     */
    public ArrayList<String> getRingiNameList(Rng010ParamModel paramMdl, Connection con)
    throws Exception {

        ArrayList<String> ret = new ArrayList<String>();
        if (paramMdl.getRng010DelSidList() != null) {
            ArrayList<Integer> rngSids = new ArrayList<Integer>();
            for (String strRngSid : paramMdl.getRng010DelSidList()) {
                rngSids.add(Integer.valueOf(strRngSid));
            }

            RngRndataDao dao = new RngRndataDao(con);
            ArrayList<RngRndataModel> rngList = dao.selectList(rngSids);
            for (RngRndataModel mdl : rngList) {
                ret.add(mdl.getRngTitle());
            }
        }
        return ret;
    }

    /**
     * <br>[機  能] 稟議の削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @throws Exception 実行例外
     */
    public void deleteRingi(Rng010ParamModel paramMdl, Connection con, int userSid)
    throws Exception {
        log__.debug("START");

        RngBiz rngBiz = new RngBiz(con);
        for (String strRngSid : paramMdl.getRng010DelSidList()) {

            int rngSid = Integer.parseInt(strRngSid);
            rngBiz.deleteRngData(con, rngSid, userSid);
        }

        log__.debug("End");
    }

    /**
     * <br>[機  能] 検索結果の件数を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param procMode 処理モード
     * @param keyword キーワード
     * @param userSid ユーザSID
     * @return 検索結果の件数
     * @throws SQLException SQL実行時例外
     */
    public int getSearchCount(Connection con, int procMode, String keyword, int userSid)
    throws SQLException {

        RingiSearchModel model = new RingiSearchModel();
        model.setKeyword(RngBiz.createKeywordList(keyword));
        model.setKeywordType(RngConst.RNG_SEARCHTYPE_AND);
        model.setTitleSearchFlg(true);
        model.setContentSearchFlg(true);
        model.setUserSid(userSid);

        RingiDao ringiDao = new RingiDao(con);
        return ringiDao.getRingiDataCount(model, procMode);
    }

}
