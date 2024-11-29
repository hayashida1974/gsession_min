package jp.groupsession.v2.rng.rng060;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.SortChangeBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.dao.RngTemplateDao;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;

/**
 * <br>[機  能] 稟議 内容テンプレート選択画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng060Biz {
    /** テンプレート種別 共有 */
    public static final int TEMPLATETYPE_COMMON = 0;
    /** テンプレート種別 個人 */
    public static final int TEMPLATETYPE_PERSONAL = 1;


    /** 順序変更処理区分 順序をあげる */
    public static final int SORT_UP = 0;
    /** 順序変更処理区分 順序を下げる */
    public static final int SORT_DOWN = 1;

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Rng060Biz.class);
    /** Connection */
    private Connection con__ = null;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * @param con Connection
     * @param reqMdl リクエスト情報
     */
    Rng060Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }
    /**
     *
     * <br>[機  能] パラメータから画面の使用目的を判定
     * <br>[解  説]
     * <br>[備  考]
     * @param tMode rngTemplateMode
     * @param rng010TransitionFlg rng010TransitionFlg
     * @return 使用目的区分
     */
    private int __judgeMokuteki(int tMode, int rng010TransitionFlg) {

        if (tMode == RngConst.RNG_TEMPLATE_ALL) {
            //稟議一覧画面 からの稟議申請時の画面遷移
            return RngConst.RTPLIST_MOKUTEKI_USE;
        } else if (rng010TransitionFlg == 1) {
            //稟議一覧画面 からのテンプレート編集の画面遷移
            return RngConst.RTPLIST_MOKUTEKI_KANRI;

        } else if (tMode == RngConst.RNG_TEMPLATE_SHARE) {
            //管理者設定画面 からのテンプレート編集の画面遷移
            return RngConst.RTPLIST_MOKUTEKI_KANRI;

        } else if (tMode == RngConst.RNG_TEMPLATE_PRIVATE) {
            //個人設定画面 からのテンプレート編集の画面遷移
            return RngConst.RTPLIST_MOKUTEKI_KANRI;
        }
        return RngConst.RTPLIST_MOKUTEKI_USE;
    }
    /**
     * <br>[機  能] 初期表示処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void initDsp(Rng060ParamModel paramMdl, int userSid) throws SQLException {

        boolean isShareTemplate   = false;
        boolean isPrivateTemplate = false;
        ArrayList<RngTemplateCategoryModel> categoryList = null; // テンプレートカテゴリ一覧
        ArrayList<RngTemplateModel>         tmplateList  = null; // テンプレート一覧

        // 使用可能なカテゴリ一覧を取得
        int tplMode = paramMdl.getRngTemplateMode();
        if (tplMode == RngConst.RNG_TEMPLATE_ALL) {
            // tplModeが「全て」であるならば、
            isShareTemplate  = true;
            isPrivateTemplate = true;
        } else if (tplMode == RngConst.RNG_TEMPLATE_SHARE) {
            // tplModeが「共有」であるならば、
            isShareTemplate  = true;
        } else if (tplMode == RngConst.RNG_TEMPLATE_PRIVATE) {
            // tplModeが「個人」であるならば、
            isPrivateTemplate = true;
        } else {
            log__.debug("テンプレート情報はありません。");
        }
        RngBiz biz = new RngBiz(con__);
        int mokuteki = __judgeMokuteki(tplMode,
                paramMdl.getRng010TransitionFlg());
        //プラグイン管理者
        boolean isAdmin = false;
        CommonBiz cmnBiz = new CommonBiz();
        BaseUserModel usModel = reqMdl__.getSmodel();
        isAdmin = cmnBiz.isPluginAdmin(con__, usModel, RngConst.PLUGIN_ID_RINGI);

        //共有テンプレート
        if (isShareTemplate) {
            //共有のカテゴリを取得する
            categoryList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_SHARE,
                                                       userSid, isAdmin, mokuteki);

            //共有テンプレートカテゴリのコンボリストを設定
            ArrayList<LabelValueBean> combs =
                                            biz.createCategoryComb(reqMdl__, categoryList, true,
                                                    isAdmin, mokuteki);
            paramMdl.setRng060CategoryList(combs);
            //共有のテンプレートを取得する
            tmplateList  = biz.getTemplateList(reqMdl__, RngConst.RNG_TEMPLATE_SHARE,
                                               categoryList, paramMdl.getRng060SelectCat(),
                                               isAdmin, mokuteki);

            //決裁後アクションの設定が正しいかの判定
            ArrayList<Rng060TemplateModel> templateShareList =
                    new ArrayList<Rng060TemplateModel>();
            for (RngTemplateModel rtMdl : tmplateList) {
                Rng060TemplateModel rtShareMdl = new Rng060TemplateModel();
                rtShareMdl.setRtpSid(rtMdl.getRtpSid());
                rtShareMdl.setRtpType(rtMdl.getRtpType());
                rtShareMdl.setUsrSid(rtMdl.getUsrSid());
                rtShareMdl.setRtpTitle(rtMdl.getRtpTitle());
                rtShareMdl.setRtpRngTitle(rtMdl.getRtpRngTitle());
                rtShareMdl.setRtpSort(rtMdl.getRtpSort());
                rtShareMdl.setRtpAuid(rtMdl.getRtpAuid());
                rtShareMdl.setRtpAdate(rtMdl.getRtpAdate());
                rtShareMdl.setRtpEuid(rtMdl.getRtpEuid());
                rtShareMdl.setRtpEdate(rtMdl.getRtpEdate());
                rtShareMdl.setRtcSid(rtMdl.getRtcSid());
                rtShareMdl.setRtpVer(rtMdl.getRtpVer());
                rtShareMdl.setRtpMaxverKbn(rtMdl.getRtpMaxverKbn());
                rtShareMdl.setRtpJkbn(rtMdl.getRtpJkbn());
                rtShareMdl.setRtpIdformatSid(rtMdl.getRtpIdformatSid());
                rtShareMdl.setRtpForm(rtMdl.getRtpForm());
                rtShareMdl.setRctSid(rtMdl.getRctSid());
                rtShareMdl.setRctUsrSid(rtMdl.getRctUsrSid());
                rtShareMdl.setRtpBiko(rtMdl.getRtpBiko());
                rtShareMdl.setRtpIdmanual(rtMdl.getRtpIdmanual());
                rtShareMdl.setRtpSpecVer(rtMdl.getRtpSpecVer());
                rtShareMdl.setRctVer(rtMdl.getRctVer());
                rtShareMdl.setRtpUseApiconnect(rtMdl.getRtpUseApiconnect());
                rtShareMdl.setRtpApiconnectComment(rtMdl.getRtpApiconnectComment());
                rtShareMdl.setRtcName(rtMdl.getRtcName());
                templateShareList.add(rtShareMdl);
            }

            // 汎用稟議設定
            RngAconfModel aconfMdl = biz.getRngAconf(con__);

            // 汎用稟議テンプレートを使用しないの場合、汎用稟議テンプレートを削除
            if (tplMode != RngConst.RNG_TEMPLATE_SHARE
                    && aconfMdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_NO
                    && tmplateList.size() > 0) {
                if (tmplateList.get(0).getRtpSid() == 0) {
                    tmplateList.remove(0);
                }
            } else {
                if (tplMode == RngConst.RNG_TEMPLATE_SHARE
                        && tmplateList.size() > 0) {
                    if (tmplateList.get(0).getRtpSid() == 0) {
                        tmplateList.remove(0);
                    }
                }
            }

//            paramMdl.setRng060tplListShare(tmplateList);
            paramMdl.setRng060tplListShare(templateShareList);
        }



        //カテゴリ編集権限設定
        paramMdl.setRtcEditable(0);
        paramMdl.setRtpAddable(0);
        if (mokuteki == RngConst.RTPLIST_MOKUTEKI_KANRI) {
            if (tplMode == RngConst.RNG_TEMPLATE_PRIVATE) {
                paramMdl.setRtcEditable(1);
                paramMdl.setRtpAddable(1);
            }
            if (tplMode == RngConst.RNG_TEMPLATE_SHARE) {
                if (isAdmin) {
                    paramMdl.setRtcEditable(1);
                }
                //管理するカテゴリがある場合のみ追加可能
                if (categoryList.size() > 0 || isAdmin) {
                    paramMdl.setRtpAddable(1);
                }
            }
        }
        // tplModeが「全て」で個人テンプレート使用できない場合
        if (tplMode == RngConst.RNG_TEMPLATE_ALL) {
            RngAconfModel aconfMdl = biz.getRngAconf(con__);
            if (aconfMdl.getRarHanyoFlg()
                    == RngConst.RAR_HANYO_FLG_NO) {
                paramMdl.setRng060TemplatePersonalFlg(RngConst.RAR_TEMPLATE_PERSONAL_FLG_NO);
                return;
            }
            if (aconfMdl.getRarTemplatePersonalFlg()
                    == RngConst.RAR_KEIRO_PERSONAL_FLG_NO) {
                paramMdl.setRng060TemplatePersonalFlg(RngConst.RAR_TEMPLATE_PERSONAL_FLG_NO);
                return;
            }
        }

        //個人テンプレート
        if (isPrivateTemplate) {

            //個人のカテゴリを取得する
            categoryList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_PRIVATE,
                                                       userSid, true, mokuteki);

            //個人テンプレートカテゴリのコンボリストを設定
            ArrayList<LabelValueBean> combs = biz.createCategoryComb(reqMdl__, categoryList, true,
                    true, mokuteki);
            paramMdl.setRng060CategoryListPrivate(combs);

            //個人のテンプレートを取得する
            tmplateList  = biz.getTemplateList(reqMdl__, RngConst.RNG_TEMPLATE_PRIVATE,
                                               categoryList, paramMdl.getRng060SelectCatUsr(),
                                               true, mokuteki);

            paramMdl.setRng060tplListPrivate(tmplateList);
        }
    }

    /**
     * <br>[機  能] 順序変更処理
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param sortKbn 処理区分 0:順序をあげる 1:順序を下げる
     * @param sessionUserSid セッションユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void updateSort(Connection con, Rng060ParamModel paramMdl,
                            int sortKbn, int sessionUserSid)
        throws SQLException {

        UDate now = new UDate();

        //ラジオ選択値取得
        int tplMode = paramMdl.getRng060TemplateMode();
        String sortRtpSid = paramMdl.getRng060SortRadio();
        if (tplMode == RngConst.RNG_TEMPLATE_PRIVATE) {
            sortRtpSid = paramMdl.getRng060SortRadioPrivate();
        }
        if (StringUtil.isNullZeroString(sortRtpSid)) {
            return;
        }
        final int motoSid = NullDefault.getInt(sortRtpSid, -1);
        if (motoSid <= 0) {
            return;
        }

        RngTemplateDao templateDao = new RngTemplateDao(con);
        RngBiz biz = new RngBiz(con);
        SortChangeBiz<RngTemplateModel> sortBiz =
                SortChangeBiz.<RngTemplateModel> builder()
                .setFuncTargetList(() -> {
                    ArrayList<RngTemplateCategoryModel> categoryList = null; // テンプレートカテゴリ一覧
                    int mokuteki = __judgeMokuteki(tplMode,
                            paramMdl.getRng010TransitionFlg());

                    if (tplMode == RngConst.RNG_TEMPLATE_PRIVATE) {
                        categoryList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_PRIVATE,
                                sessionUserSid, true, mokuteki);
                        return biz.getTemplateList(reqMdl__, RngConst.RNG_TEMPLATE_PRIVATE,
                                categoryList, paramMdl.getRng060SelectCatUsr(),
                                true, mokuteki);
                    } else {
                        boolean isAdmin = false;
                        CommonBiz cmnBiz = new CommonBiz();
                        BaseUserModel usModel = reqMdl__.getSmodel();
                        isAdmin = cmnBiz.isPluginAdmin(con__, usModel, RngConst.PLUGIN_ID_RINGI);
                        categoryList = biz.getTemplateCategoryList(RngConst.RNG_TEMPLATE_SHARE,
                                sessionUserSid, isAdmin, mokuteki);
                        return biz.getTemplateList(reqMdl__, RngConst.RNG_TEMPLATE_SHARE,
                                categoryList, paramMdl.getRng060SelectCat(),
                                isAdmin, mokuteki);
                    }
                })
                .setFuncIsSelected(m -> {
                    return (Objects.equals(m.getRtpSid(), motoSid));
                })
                .setFuncGetOrderNo(m -> {
                    return m.getRtpSort();
                })
                .setFuncExeComparater((m1, m2) -> {
                    if (m1.getRtpSid() == m2.getRtpSid()) {
                        return 0;
                    } else {
                        return (m1.getRtpSid() - m2.getRtpSid())
                                / Math.abs(m1.getRtpSid() - m2.getRtpSid());
                    }
                })
                .setFuncUpdateSort((m, newSort) -> {
                    //並び替え更新実行 ラムダ関数
                    templateDao.updateSort(m.getRtpSid(), newSort, sessionUserSid, now);
                })
                .build();

        if (sortKbn == GSConst.SORT_UP) {
            sortBiz.up();
        } else if (sortKbn == GSConst.SORT_DOWN) {
            sortBiz.down();
        }
    }
}
