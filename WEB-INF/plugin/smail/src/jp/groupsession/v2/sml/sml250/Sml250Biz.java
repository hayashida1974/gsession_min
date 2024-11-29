package jp.groupsession.v2.sml.sml250;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnThemeDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnThemeModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountAutoDestDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAccountUserDao;
import jp.groupsession.v2.sml.dao.SmlAdelDao;
import jp.groupsession.v2.sml.dao.SmlPushUconfDao;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAccountUserModel;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] ショートメール アカウント登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml250Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml250Biz.class);

    /** 使用者 ユーザを指定 */
    public static final int USERKBN_USER = 1;
    /** 対象 全ユーザ */
    public static final int TAISYO_ALL = 0;
    /** 対象 ユーザ指定 */
    public static final int TAISYO_SELECT = 1;

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     */
    public void setInitData(Connection con, Sml250ParamModel paramMdl, RequestModel reqMdl)
    throws Exception {

        //自動削除区分を設定
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        SmlAdminModel admMdl = new SmlAdminModel();
        admMdl = smlBiz.getSmailAdminConf(reqMdl.getSmodel().getUsrsid(), con);
        paramMdl.setSml250autoDelKbn(admMdl.getSmaAutoDelKbn());

        ArrayList<LabelValueBean> yearLabel = new ArrayList<LabelValueBean>();
        ArrayList<LabelValueBean> monthLabel = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);

        //年リスト
        for (int i = 0; i <= 10; i++) {
            yearLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[] {String.valueOf(i)}),
                    Integer.toString(i)));
        }
        paramMdl.setSml250YearLabelList(yearLabel);

        //月リスト
        for (int i = 0; i < 12; i++) {
            monthLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {String.valueOf(i)}),
                    Integer.toString(i)));
        }
        paramMdl.setSml250MonthLabelList(monthLabel);

        int sacSid = paramMdl.getSmlAccountSid();
        boolean acntUserFlg = getAcntUserFlg(con, paramMdl, sacSid, admMdl);
        paramMdl.setSml250acntUserFlg(acntUserFlg);

        //新規登録 初期表示
        if (paramMdl.getSml250initFlg() == GSConstSmail.DSP_FIRST
                && paramMdl.getSmlCmdMode() == GSConstSmail.CMDMODE_ADD) {

            paramMdl.setSml250initFlg(GSConstSmail.DSP_ALREADY);

            if (admMdl.getSmaAutoDelKbn() == GSConstSmail.AUTO_DEL_ACCOUNT) {
                paramMdl.setSml250JdelKbn(String.valueOf(GSConstSmail.SML_AUTO_DEL_NO));
                paramMdl.setSml250SdelKbn(String.valueOf(GSConstSmail.SML_AUTO_DEL_NO));
                paramMdl.setSml250WdelKbn(String.valueOf(GSConstSmail.SML_AUTO_DEL_NO));
                paramMdl.setSml250DdelKbn(String.valueOf(GSConstSmail.SML_AUTO_DEL_NO));
            }


        //編集 初期表示
        } else if (paramMdl.getSml250initFlg() == GSConstSmail.DSP_FIRST
                && paramMdl.getSmlCmdMode() == GSConstSmail.CMDMODE_EDIT) {

            //アカウント情報を設定する
            SmlAccountDao accountDao = new SmlAccountDao(con);
            SmlAccountModel accountMdl = accountDao.select(sacSid);

            if (accountMdl.getUsrSid() > 0) {
                paramMdl.setSml250AccountKbn(GSConstSmail.ACNT_DEF);
                paramMdl.setSml250DefActUsrSid(accountMdl.getUsrSid());
            }

            paramMdl.setSml250name(accountMdl.getSacName());
            paramMdl.setSml250biko(accountMdl.getSacBiko());

            if (acntUserFlg) {
                //使用者を設定
                SmlAccountUserDao accountUserDao = new SmlAccountUserDao(con);
                List<SmlAccountUserModel> accountUserList = accountUserDao.select(sacSid);
                String[] id = new String[accountUserList.size()];
                for (int index = 0; index < id.length; index++) {

                    if (accountUserList.get(index).getUsrSid() > 0) {
                        id[index] = String.valueOf(accountUserList.get(index).getUsrSid());
                    } else {
                        id[index] = "G"
                                  + String.valueOf(accountUserList.get(index).getGrpSid());
                    }
                }
                paramMdl.setSml250userKbnUser(id);
            }

            //送信形式
            paramMdl.setSml250sendType(accountMdl.getSacSendMailtype());

            //テーマ
            paramMdl.setSml250theme(accountMdl.getSacTheme());

            //引用符
            paramMdl.setSml250quotes(accountMdl.getSacQuotes());

            if (admMdl.getSmaAutoDelKbn() == GSConstSmail.AUTO_DEL_ACCOUNT) {
                SmlAdelDao delDao = new SmlAdelDao(con);
                SmlAdelModel delMdl = delDao.select(sacSid);
                if (delMdl == null) {
                    delMdl = new SmlAdelModel();
                    delMdl.setSadJdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
                    delMdl.setSadJdelYear(0);
                    delMdl.setSadJdelMonth(0);
                    delMdl.setSadSdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
                    delMdl.setSadSdelYear(0);
                    delMdl.setSadSdelMonth(0);
                    delMdl.setSadWdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
                    delMdl.setSadWdelYear(0);
                    delMdl.setSadWdelMonth(0);
                    delMdl.setSadDdelKbn(GSConstSmail.SML_AUTO_DEL_NO);
                    delMdl.setSadDdelYear(0);
                    delMdl.setSadDdelMonth(0);
                }

                //受信タブ処理 選択値
                paramMdl.setSml250JdelKbn(
                        NullDefault.getStringZeroLength(
                                paramMdl.getSml250JdelKbn(),
                                String.valueOf(delMdl.getSadJdelKbn())));

                //受信タブ 年
                paramMdl.setSml250JYear(
                        NullDefault.getStringZeroLength(
                                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml250JYear()),
                                String.valueOf(delMdl.getSadJdelYear())));

                //受信タブ 月
                paramMdl.setSml250JMonth(
                        NullDefault.getStringZeroLength(
                                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml250JMonth()),
                                String.valueOf(delMdl.getSadJdelMonth())));

                //送信タブ処理 選択値
                paramMdl.setSml250SdelKbn(
                        NullDefault.getStringZeroLength(
                                paramMdl.getSml250SdelKbn(),
                                String.valueOf(delMdl.getSadSdelKbn())));

                //送信タブ 年
                paramMdl.setSml250SYear(
                        NullDefault.getStringZeroLength(
                                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml250SYear()),
                                String.valueOf(delMdl.getSadSdelYear())));

                //送信タブ 月
                paramMdl.setSml250SMonth(
                        NullDefault.getStringZeroLength(
                                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml250SMonth()),
                                String.valueOf(delMdl.getSadSdelMonth())));

                //草稿タブ処理 選択値
                paramMdl.setSml250WdelKbn(
                        NullDefault.getStringZeroLength(
                                paramMdl.getSml250WdelKbn(),
                                String.valueOf(delMdl.getSadWdelKbn())));

                //草稿タブ 年
                paramMdl.setSml250WYear(
                        NullDefault.getStringZeroLength(
                                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml250WYear()),
                                String.valueOf(delMdl.getSadWdelYear())));

                //草稿タブ 月
                paramMdl.setSml250WMonth(
                        NullDefault.getStringZeroLength(
                                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml250WMonth()),
                                String.valueOf(delMdl.getSadWdelMonth())));

                //ゴミ箱タブ処理 選択値
                paramMdl.setSml250DdelKbn(
                        NullDefault.getStringZeroLength(
                                paramMdl.getSml250DdelKbn(),
                                String.valueOf(delMdl.getSadDdelKbn())));

                //ゴミ箱タブ 年
                paramMdl.setSml250DYear(
                        NullDefault.getStringZeroLength(
                                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml250DYear()),
                                String.valueOf(delMdl.getSadDdelYear())));

                //ゴミ箱タブ 月
                paramMdl.setSml250DMonth(
                        NullDefault.getStringZeroLength(
                                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml250DMonth()),
                                String.valueOf(delMdl.getSadDdelMonth())));
            }

            __loadAuteDest(paramMdl, con);
            paramMdl.setSml250initFlg(GSConstSmail.DSP_ALREADY);
        }


        //使用者 グループコンボ、ユーザコンボを設定
        //_setGroupCombo(con, paramMdl);
        _setUserCombo(con, paramMdl, reqMdl);


        //テーマコンボを設定
        List<LabelValueBean> themeCombo = new ArrayList<LabelValueBean>();
        themeCombo.add(
                new LabelValueBean(gsMsg.getMessage("cmn.notset"),
                                            String.valueOf(GSConstSmail.SAC_THEME_NOSET)));

        CmnThemeDao themeDao = new CmnThemeDao(con);
        List<CmnThemeModel> themeList = themeDao.getThemeList();
        for (CmnThemeModel themeData : themeList) {
            themeCombo.add(
                    new LabelValueBean(themeData.getCtmName(),
                                                    String.valueOf(themeData.getCtmSid())));
        }
        paramMdl.setSml250themeList(themeCombo);

        //引用符コンボを設定
        List<LabelValueBean> quotesCombo = new ArrayList<LabelValueBean>();
        int[] quotesList = {GSConstSmail.SAC_QUOTES_DEF,
                                    GSConstSmail.SAC_QUOTES_NONE,
                                    GSConstSmail.SAC_QUOTES_2,
                                    GSConstSmail.SAC_QUOTES_3,
                                    GSConstSmail.SAC_QUOTES_4,
                                    GSConstSmail.SAC_QUOTES_5};
        for (int quotes : quotesList) {
            quotesCombo.add(new LabelValueBean(SmlCommonBiz.getViewMailQuotes(quotes, reqMdl),
                                                                    Integer.toString(quotes)));
        }
        paramMdl.setSml250quotesList(quotesCombo);


        /************************  転送設定  *********************************/
        paramMdl.setSml250tensoKbn(admMdl.getSmaMailfw());
        if (paramMdl.getSmlAccountMode() == GSConstSmail.ACCOUNTMODE_COMMON
            && paramMdl.getSml250tensoKbn() == GSConstSmail.MAIL_FORWARD_OK) {


            //メールアドレスコンボ設定
            paramMdl.setSml250MailList(__getMailCombo(reqMdl));
        }

        _setAutoDestDisp(paramMdl, con);

    }
    /**
    *
    * <br>[機  能] sid配列をユーザSIDリストとアカウントSIDリストに分割
    * <br>[解  説]
    * <br>[備  考]
    * @param strSids ソース配列
    * @param destUsrSidList ユーザSID格納先
    * @param destAccSidList アカウントSID格納先
    */
   protected void _splitSids(String[] strSids,
           List<String> destUsrSidList,
           List<String> destAccSidList) {
       if (strSids != null) {
           for (String strSid : strSids) {
               if (strSid != null && strSid.startsWith(GSConstSmail.SML_ACCOUNT_STR)) {
                   destAccSidList.add(String.valueOf(NullDefault.getInt(
                           strSid.substring(GSConstSmail.SML_ACCOUNT_STR.length()),
                           -1)));
               } else {
                   int usrSid = NullDefault.getInt(
                           strSid,
                           -1);
                   if (usrSid > GSConstUser.USER_RESERV_SID) {
                       destUsrSidList.add(String.valueOf(usrSid));
                   }
               }
           }
       }

   }
   /**
    *
    * <br>[機  能] 自動送信先の表示設定
    * <br>[解  説]
    * <br>[備  考]
    * @param paramMdl パラメータモデル
    * @param con コネクション
    * @throws SQLException SQL実行時例外
    */
   protected void _setAutoDestDisp(Sml250ParamModel paramMdl,
           Connection con) throws SQLException {
       UserBiz usrBiz = new UserBiz();
       SmlAccountDao sacDao = new SmlAccountDao(con);
       //To
       List<String> usrSidList = new ArrayList<String>();
       List<String> accSidList = new ArrayList<String>();
       List<UsrLabelValueBean> labelList = new ArrayList<UsrLabelValueBean>();
       String[] strSids = paramMdl.getSml250AutoDestToUsrSid();
       _splitSids(strSids, usrSidList, accSidList);
       labelList.addAll(usrBiz.getUserLabelList(con,
               usrSidList.toArray(new String[usrSidList.size()])));
       labelList.addAll(sacDao.selectSacSids2(
               accSidList.toArray(new String[accSidList.size()]), SmlAccountDao.JKBN_LIV));
       paramMdl.setSml250AutoDestToLabelList(labelList);
       //Cc
       usrSidList = new ArrayList<String>();
       accSidList = new ArrayList<String>();
       labelList = new ArrayList<UsrLabelValueBean>();
       strSids = paramMdl.getSml250AutoDestCcUsrSid();
       _splitSids(strSids, usrSidList, accSidList);
       labelList.addAll(usrBiz.getUserLabelList(con,
               usrSidList.toArray(new String[usrSidList.size()])));
       labelList.addAll(sacDao.selectSacSids2(
               accSidList.toArray(new String[accSidList.size()]), SmlAccountDao.JKBN_LIV));
       paramMdl.setSml250AutoDestCcLabelList(labelList);
       //Bcc
       usrSidList = new ArrayList<String>();
       accSidList = new ArrayList<String>();
       labelList = new ArrayList<UsrLabelValueBean>();
       strSids = paramMdl.getSml250AutoDestBccUsrSid();
       _splitSids(strSids, usrSidList, accSidList);
       labelList.addAll(usrBiz.getUserLabelList(con,
               usrSidList.toArray(new String[usrSidList.size()])));
       labelList.addAll(sacDao.selectSacSids2(
               accSidList.toArray(new String[accSidList.size()]), SmlAccountDao.JKBN_LIV));
       paramMdl.setSml250AutoDestBccLabelList(labelList);



   }
    /**
     *
     * <br>[機  能] DBから保管済みの自動送信先を読み込む
     * <br>[解  説]
     * <br>[備  考]
     * @param paramModel パラメータモデル
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void __loadAuteDest(Sml250ParamModel paramModel, Connection con)
            throws SQLException {
        SmlAccountAutoDestDao sadDao = new SmlAccountAutoDestDao(con);
        for (int type = 0; type < 3; type++) {
            List<SmlAccountModel> sacModels =
                    sadDao.getAutoDestAccounts(paramModel.getSmlAccountSid(), type);
            List<UsrLabelValueBean> labelList = new ArrayList<UsrLabelValueBean>();
            String[] sids = new String[sacModels.size()];
            for (int i = 0; i < sids.length; i++) {
                SmlAccountModel acc = sacModels.get(i);
                UsrLabelValueBean bean = new UsrLabelValueBean();
                if (acc.getUsrSid() > 0) {
                    bean.setValue(String.valueOf(acc.getUsrSid()));
                } else {
                    bean.setValue(GSConstSmail.SML_ACCOUNT_STR + String.valueOf(acc.getSacSid()));
                }
                bean.setLabel(acc.getSacName());
                labelList.add(bean);
                sids[i] = bean.getValue();
            }
            switch (type) {
             case GSConstSmail.SML_SEND_KBN_ATESAKI:
                 paramModel.setSml250AutoDestToUsrSid(sids);
                 break;
             case GSConstSmail.SML_SEND_KBN_CC:
                 paramModel.setSml250AutoDestCcUsrSid(sids);
                 break;
             case GSConstSmail.SML_SEND_KBN_BCC:
                 paramModel.setSml250AutoDestBccUsrSid(sids);
                 break;
             default:
            }

        }
    }


    /**
     * <br>[機  能] アカウントの削除を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void deleteAccount(Connection con, Sml250ParamModel paramMdl, int userSid)
   throws SQLException {

        log__.info("アカウント削除開始");

        boolean commit = false;
        try {
            SmlAccountDao accountDao = new SmlAccountDao(con);
            //アカウントを論理削除
            accountDao.updateJkbn(paramMdl.getSmlAccountSid(), GSConstSmail.SAC_JKBN_DELETE);

            //ショートメール自動送信先を物理削除
            SmlAccountAutoDestDao saaDao = new SmlAccountAutoDestDao(con);
            saaDao.delete(paramMdl.getSmlAccountSid());

            //プッシュ通知設定を削除
            SmlPushUconfDao spuDao = new SmlPushUconfDao(con);
            spuDao.deleteAccount(paramMdl.getSmlAccountSid());

            con.commit();
            commit = true;
        } catch (SQLException e) {
            log__.info("アカウントの削除に失敗", e);
            throw e;
        } finally {
            if (!commit) {
                con.rollback();
            }
        }

        log__.info("アカウント削除終了");
    }

//    /**
//     * <br>[機  能] グループコンボを設定する
//     * <br>[解  説]
//     * <br>[備  考]
//     * @param con コネクション
//     * @param paramMdl パラメータ情報
//     * @throws SQLException SQL実行時例外
//     */
//    protected void _setGroupCombo(Connection con, Sml250ParamModel paramMdl)
//    throws SQLException {
//        List<LabelValueBean> groupCombo = new ArrayList<LabelValueBean>();
//        List<LabelValueBean> selectGroupCombo = new ArrayList<LabelValueBean>();
//
//        String[] selectGrpSid = paramMdl.getSml250userKbnGroup();
//        if (selectGrpSid == null) {
//            selectGrpSid = new String[0];
//        }
//        Arrays.sort(selectGrpSid);
//
//        GroupBiz grpBiz = new GroupBiz();
//        ArrayList<GroupModel> gpList = grpBiz.getGroupCombList(con);
//        for (GroupModel grpMdl : gpList) {
//            LabelValueBean label = new LabelValueBean(grpMdl.getGroupName(),
//                                                String.valueOf(grpMdl.getGroupSid()));
//            if (Arrays.binarySearch(selectGrpSid, String.valueOf(grpMdl.getGroupSid())) < 0) {
//                groupCombo.add(label);
//            } else {
//                selectGroupCombo.add(label);
//            }
//        }
//
//        paramMdl.setUserKbnGroupSelectCombo(selectGroupCombo);
//        paramMdl.setUserKbnGroupNoSelectCombo(groupCombo);
//
//    }

    /**
     * <br>[機  能] ユーザコンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setUserCombo(Connection con, Sml250ParamModel paramMdl, RequestModel reqMdl)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);

        int grpSid = NullDefault.getInt(paramMdl.getSml250userKbnUserGroup(), -1);

        String[] selectUserSid = paramMdl.getSml250userKbnUser();
        if (selectUserSid == null) {
            selectUserSid = new String[0];
        }

        //デフォルトユーザを設定
        if (paramMdl.getSml250DefActUsrSid() > 0) {
            boolean defFlg = false;
            ArrayList<String> usrSidList = new ArrayList<String>();
            for (String usid : selectUserSid) {
                usrSidList.add(usid);
                if (usid.equals(String.valueOf(paramMdl.getSml250DefActUsrSid()))) {
                    defFlg = true;
                }
            }
            if (!defFlg) {
                usrSidList.add(String.valueOf(paramMdl.getSml250DefActUsrSid()));
            }
            paramMdl.setSml250userKbnUser(
                    (String[]) usrSidList.toArray(new String[usrSidList.size()]));
            selectUserSid = (String[]) usrSidList.toArray(new String[usrSidList.size()]);
        }

        Arrays.sort(selectUserSid);
        paramMdl.setUserKbnUserSelectCombo(__getMemberList(selectUserSid, con));



    }

    /**
     * <br>[機  能] メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param left 取得するユーザSID・グループSID
     * @param con コネクション
     * @return メンバー一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getMemberList(String[] left, Connection con)
    throws SQLException {

        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();

        //
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (left != null) {
            for (int i = 0; i < left.length; i++) {
                String str = NullDefault.getString(left[i], "-1");
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(new Integer(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }
        //グループ情報取得
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
        UsrLabelValueBean labelBean = null;
        for (GroupModel gmodel : glist) {
            labelBean = new UsrLabelValueBean();
            labelBean.setLabel(gmodel.getGroupName());
            labelBean.setValue("G" + String.valueOf(gmodel.getGroupSid()));
            ret.add(labelBean);
        }
        //ユーザ情報取得
        UserBiz userBiz = new UserBiz();
        List<CmnUsrmInfModel> ulist
                = userBiz.getUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        for (CmnUsrmInfModel umodel : ulist) {
            labelBean = new UsrLabelValueBean(umodel);
            ret.add(labelBean);
        }
        return ret;
    }


    /**
     * 在席管理が利用可能かを  設定する
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig プラグインコンフィグ
     * @throws SQLException SQL実行時例外
     */
    public void setCanUsePluginFlg(Sml250ParamModel paramMdl, Connection con, PluginConfig pconfig)
    throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSmail.PLUGIN_ID_ZAISEKI, pconfig)) {
            paramMdl.setSml250ZaisekiPlugin(GSConstSmail.PLUGIN_USE);
        } else {
            paramMdl.setSml250ZaisekiPlugin(GSConstSmail.PLUGIN_NOT_USE);
        }
    }

    /**
     * <br>[機  能] メールアドレスコンボを生成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param reqMdl RequestModel
     * @return List (in LabelValueBean)  メールアドレスコンボ
     * @throws SQLException SQL実行時例外
     */
    private List<LabelValueBean> __getMailCombo(RequestModel reqMdl) throws SQLException {

        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String mailAdr1 = gsMsg.getMessage("cmn.mailaddress1");
        String mailAdr2 = gsMsg.getMessage("cmn.mailaddress2");
        String mailAdr3 = gsMsg.getMessage("cmn.mailaddress3");
        String mailAdr4 = gsMsg.getMessage("sml.122");

        labelList.add(new LabelValueBean(mailAdr1, "1"));
        labelList.add(new LabelValueBean(mailAdr2, "2"));
        labelList.add(new LabelValueBean(mailAdr3, "3"));
        labelList.add(new LabelValueBean(mailAdr4, "0"));

        return labelList;
    }



    /**
     * <br>[機  能] 使用者設定フラグを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param sacSid アカウントSID
     * @param admMdl ショートメール管理者設定
     * @return 使用者設定フラグ
     * @throws SQLException SQL実行時例外
     */
    public boolean getAcntUserFlg(Connection con, Sml250ParamModel paramMdl,
                                                int sacSid, SmlAdminModel admMdl)
    throws SQLException {
        boolean acntUserFlg = false;

        if (paramMdl.getSmlCmdMode() == GSConstSmail.CMDMODE_ADD) {
            sacSid = 0;
        }

        if (admMdl == null) {
            SmlCommonBiz smlBiz = new SmlCommonBiz();
            admMdl = new SmlAdminModel();
            admMdl = smlBiz.getSmailAdminConf(0, con);
        }

        boolean admUserFlg = admMdl.getSmaAcntUser() == GSConstSmail.KANRI_ACCOUNT_USER_NO;
        if (paramMdl.getSmlAccountMode() == GSConstSmail.ACCOUNTMODE_COMMON) {
            int sacType = GSConstSmail.SAC_TYPE_NORMAL;
            if (sacSid > 0) {
                SmlAccountDao accountDao = new SmlAccountDao(con);
                sacType = accountDao.getSacType(sacSid);
                acntUserFlg = admUserFlg || sacType != GSConstSmail.SAC_TYPE_NORMAL;
            } else {
                acntUserFlg = true;
            }
        } else {
            acntUserFlg = admUserFlg;
        }

        return acntUserFlg;
    }

    /**
     * <br>[機  能] ログ用メッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param sid SID
     * @param con コネクション
     * @return メッセージ
     * @throws SQLException SQL実行時例外
     */
    public String getLogMessage(int sid, Connection con)
    throws SQLException {

        SmlAccountDao accountDao = new SmlAccountDao(con);
        SmlAccountModel mdl = accountDao.select(sid);
        return mdl.getSacName();
    }
}
