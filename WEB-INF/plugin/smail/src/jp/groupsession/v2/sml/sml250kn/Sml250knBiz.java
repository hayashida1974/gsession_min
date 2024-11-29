package jp.groupsession.v2.sml.sml250kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnThemeDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnThemeModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rap.mbh.push.PushServiceOperator;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountAutoDestDao;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlAccountDiskDao;
import jp.groupsession.v2.sml.dao.SmlAccountForwardDao;
import jp.groupsession.v2.sml.dao.SmlAccountSortDao;
import jp.groupsession.v2.sml.dao.SmlAccountUserDao;
import jp.groupsession.v2.sml.dao.SmlAdelDao;
import jp.groupsession.v2.sml.dao.SmlPushUconfDao;
import jp.groupsession.v2.sml.model.SmlAccountAutoDestModel;
import jp.groupsession.v2.sml.model.SmlAccountDiskModel;
import jp.groupsession.v2.sml.model.SmlAccountForwardModel;
import jp.groupsession.v2.sml.model.SmlAccountModel;
import jp.groupsession.v2.sml.model.SmlAdelModel;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.sml.model.SmlPushUconfModel;
import jp.groupsession.v2.sml.sml250.Sml250Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] ショートメール アカウント登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml250knBiz extends Sml250Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml250knBiz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Sml250knParamModel paramMdl, RequestModel reqMdl)
    throws SQLException {
        paramMdl.setSml250knBiko(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getSml250biko()), ""));

        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        SmlAdminModel admMdl = new SmlAdminModel();
        admMdl = smlBiz.getSmailAdminConf(reqMdl.getSmodel().getUsrsid(), con);

        _setUserCombo(con, paramMdl, reqMdl);

        int sacSid = paramMdl.getSmlAccountSid();
        boolean acntUserFlg = getAcntUserFlg(con, paramMdl, sacSid, admMdl);
        paramMdl.setSml250acntUserFlg(acntUserFlg);

        //テーマ(表示用)を設定
        CmnThemeDao themeDao = new CmnThemeDao(con);
        CmnThemeModel themeData = themeDao.select(paramMdl.getSml250theme());
        GsMessage gsMsg = new GsMessage(reqMdl);
        String themeName = gsMsg.getMessage("cmn.notset");
        if (themeData != null) {
            themeName = themeData.getCtmName();
        }
        paramMdl.setSml250knTheme(themeName);

        //引用符(表示用)を設定
        paramMdl.setSml250knQuotes(
                SmlCommonBiz.getViewMailQuotes(paramMdl.getSml250quotes(), reqMdl));


        /************************  転送設定  *********************************/
        paramMdl.setSml250tensoKbn(admMdl.getSmaMailfw());
        if (paramMdl.getSmlAccountMode() == GSConstSmail.ACCOUNTMODE_COMMON
            && paramMdl.getSml250tensoKbn() == GSConstSmail.MAIL_FORWARD_OK
            && paramMdl.getSml250tensoSetKbn() == GSConstSmail.MAIL_FORWARD_SET) {

            ArrayList<UsrLabelValueBean> okUserList = new ArrayList<UsrLabelValueBean>();
            ArrayList<UsrLabelValueBean> ngUserList = new ArrayList<UsrLabelValueBean>();
            boolean checkFlg = false;

            //チェックするメールアドレスリストを取得する。
            ArrayList<String> checkList = getCheckList(paramMdl);

            //対象ユーザ情報を取得する。
            CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
            ArrayList<CmnUsrmInfModel> usrmInfList = null;
            String[] usrSids = null;
            if (paramMdl.getSml250ObjKbn() == 1) {
                //ユーザ指定
                usrSids = paramMdl.getSml250userSid();
            } else {

//                if (paramMdl.getSml250userKbn() == GSConstSmail.USRKBN_GROUP) {
//
//                    if (paramMdl.getSml250userKbnGroup() != null
//                            && paramMdl.getSml250userKbnGroup().length > 0) {
//                        CmnBelongmDao bdao = new CmnBelongmDao(con);
//                        List<String> usids = new ArrayList<String>();
//                        usids = bdao.select(paramMdl.getSml250userKbnGroup());
//                        if (paramMdl.getSml250DefActUsrSid() > 0) {
//                            usids.add(String.valueOf(paramMdl.getSml250DefActUsrSid()));
//                        }
//                        if (usids != null && !usids.isEmpty()) {
//                            usrSids = (String[]) usids.toArray(new String[usids.size()]);
//                        }
//                    }
//
//                //使用者 ユーザ 設定チェック
//                } else if (paramMdl.getSml250userKbn() == GSConstSmail.USRKBN_USER) {
                    usrSids = paramMdl.getSml250userKbnUser();
//                }
            }

            ArrayList<String> grpSidList = new ArrayList<String>();
            ArrayList<String> usrSidList = new ArrayList<String>();
            for (String id : usrSids) {
                String str = NullDefault.getString(id, "-1");
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSidList.add(str.substring(1, str.length()));
                } else {
                    //ユーザ
                    usrSidList.add(str);
                }
            }

            if (grpSidList != null && !grpSidList.isEmpty()) {
                CmnBelongmDao bdao = new CmnBelongmDao(con);
                usrSidList.addAll(bdao.select(
                        (String[]) grpSidList.toArray(new String[grpSidList.size()])));

            }

            Map<String, String> sidMap = new HashMap<String, String>();
            for (String sid : usrSidList) {
                sidMap.put(sid, sid);
            }

            usrSidList = new ArrayList<String>();

            Set<String> keySet = sidMap.keySet();
            Iterator<String> keyIte = keySet.iterator();
            while (keyIte.hasNext()) {
                String usidkey = (String) keyIte.next();
                usrSidList.add(sidMap.get(usidkey));
            }


            //ユーザ情報取得
            if (usrSidList != null && !usrSidList.isEmpty()) {
                usrmInfList = usrmInfDao.getUserList(
                        (String[]) usrSidList.toArray(new String[usrSidList.size()]));
            }

            if (usrmInfList == null || usrmInfList.size() < 1) {
                return;
            }

            //メールアドレスチェックフラグ
            if (paramMdl.getSml250PassKbn() == 1
            && (paramMdl.getSml250MailFw().equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))
                 || paramMdl.getSml250MailFw().equals(
                         String.valueOf(GSConstSmail.MAIL_FORWARD_FUZAI_OK)))) {
                for (String mKbn : checkList) {
                    if (!mKbn.equals("0")) {
                        //チェック項目がある。
                        checkFlg = true;
                        break;
                    }
                }
            }

            if (!checkFlg) {
                //メールアドレス1・2・3を選択していない場合
                int cnt = 0;
                for (CmnUsrmInfModel model : usrmInfList) {
                    if (model.getUsrSid() < 100) {
                        continue;
                    }
                    okUserList.add(new UsrLabelValueBean(model));
                    cnt++;
                }
                paramMdl.setSml250knUsrOkLabelList(okUserList);
                paramMdl.setSml250knUsrCnt(cnt);
                return;
            }

            int count = 0;
            int ngCount = 0;
            boolean okFlg = true;
            for (CmnUsrmInfModel model : usrmInfList) {

                if (model.getUsrSid() < 100) {
                    continue;
                }
                okFlg = true;
                for (String mailKbn : checkList) {

                    if (mailKbn.equals("1")) {
                        if (StringUtil.isNullZeroString(model.getUsiMail1())) {
                            ngUserList.add(new UsrLabelValueBean(model));
                            okFlg = false;
                            ngCount++;
                            break;
                        }

                    } else if (mailKbn.equals("2")) {
                        if (StringUtil.isNullZeroString(model.getUsiMail2())) {
                            ngUserList.add(new UsrLabelValueBean(model));
                            okFlg = false;
                            ngCount++;
                            break;
                        }

                    } else if (mailKbn.equals("3")) {
                        if (StringUtil.isNullZeroString(model.getUsiMail3())) {
                            ngUserList.add(new UsrLabelValueBean(model));
                            okFlg = false;
                            ngCount++;
                            break;
                        }
                    }
                }
                if (okFlg) {
                    okUserList.add(new UsrLabelValueBean(model));
                    count++;
                }
            }
            paramMdl.setSml250knUsrOkLabelList(okUserList);
            paramMdl.setSml250knUsrNgLabelList(ngUserList);
            paramMdl.setSml250knUsrCnt(count);
            paramMdl.setSml250knUsrCntNg(ngCount);

        }
        //自動送信先
        _setAutoDestDisp(paramMdl, con);


    }
    /**
     * <br>[機  能] アカウント情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     * @return SmlAccountModel
     */
    public SmlAccountModel entryAddressData(Connection con, Sml250knParamModel paramMdl,
            MlCountMtController mtCon, int sessionUserSid, RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        //管理者設定取得
        SmlCommonBiz smlBiz = new SmlCommonBiz(con, reqMdl);
        SmlAdminModel admMdl = new SmlAdminModel();
        admMdl = smlBiz.getSmailAdminConf(sessionUserSid, con);

        boolean newData = paramMdl.getSmlCmdMode() == GSConstSmail.CMDMODE_ADD;
        int accountMode = paramMdl.getSmlAccountMode();
        //int userKbn = paramMdl.getSml250userKbn();

        //アカウント情報の登録
        SmlAccountModel accountModel = new SmlAccountModel();

        accountModel.setUsrSid(sessionUserSid);
        accountModel.setSacType(GSConstSmail.SAC_TYPE_NORMAL);
//        if (accountMode == GSConstSmail.ACCOUNTMODE_COMMON
//        && userKbn == Sml250knForm.USERKBN_GROUP) {
//            accountModel.setSacType(GSConstSmail.SAC_TYPE_GROUP);
//        } else {
            accountModel.setSacType(GSConstSmail.SAC_TYPE_USER);
//        }

        accountModel.setSacName(paramMdl.getSml250name());
        accountModel.setSacBiko(paramMdl.getSml250biko());
        accountModel.setSacTheme(paramMdl.getSml250theme());
        accountModel.setSacQuotes(paramMdl.getSml250quotes());
        accountModel.setSacSendMailtype(paramMdl.getSml250sendType());


        SmlAccountDao accountDao = new SmlAccountDao(con);
        SmlAccountUserDao accountUserDao = new SmlAccountUserDao(con);
        SmlAccountSortDao accountSortDao = new SmlAccountSortDao(con);
        SmlAccountDiskDao wadDao = new SmlAccountDiskDao(con);

        int accountSid = -1;

        int sacSid = paramMdl.getSmlAccountSid();
        boolean acntUserFlg = getAcntUserFlg(con, paramMdl, sacSid, admMdl);

        //Push通知有効フラグ
        boolean pushUse = PushServiceOperator.getInstance(con, reqMdl.getDomain()).isUseable();
        SmlPushUconfDao spuDao = new SmlPushUconfDao(con);

        //新規登録
        if (newData) {

            //アカウント採番取得
            int wacSaiSid = (int) mtCon.getSaibanNumber(GSConstSmail.SAIBAN_SML_SID,
                                                      GSConstSmail.SBNSID_SUB_ACCOUNT,
                                                      sessionUserSid);

            accountModel.setSacSid(wacSaiSid);
            accountDao.insertAccount(accountModel, accountMode);

            //アカウントの並び順を登録する
//            if (accountMode == GSConstSmail.ACCOUNTMODE_NORMAL
//                    || accountMode == GSConstSmail.ACCOUNTMODE_PSNLSETTING) {
            if (!acntUserFlg) {
                accountSortDao.insertAccountSort(wacSaiSid, sessionUserSid);
                accountUserDao.insert(wacSaiSid, accountModel.getSacType(),
                                        new String[] {String.valueOf(sessionUserSid)});

            } else {
                //アカウントユーザ情報の登録

                ArrayList<String> grpSidList = new ArrayList<String>();
                ArrayList<String> usrSidList = new ArrayList<String>();
                for (String id : paramMdl.getSml250userKbnUser()) {
                    String str = NullDefault.getString(id, "-1");
                    if (str.contains(new String("G").subSequence(0, 1))) {
                        //グループ
                        grpSidList.add(str.substring(1, str.length()));
                    } else {
                        //ユーザ
                        usrSidList.add(str);
                    }
                }

                if (grpSidList != null && !grpSidList.isEmpty()) {
                    accountUserDao.insert(wacSaiSid, GSConstSmail.SAC_TYPE_GROUP,
                            (String[]) grpSidList.toArray(new String[grpSidList.size()]));

                }

                if (usrSidList != null && !usrSidList.isEmpty()) {
                    accountUserDao.insert(wacSaiSid, GSConstSmail.SAC_TYPE_USER,
                        (String[]) usrSidList.toArray(new String[usrSidList.size()]));
                    accountSortDao.insertAccountSortUsr(wacSaiSid,
                            (String[]) usrSidList.toArray(new String[usrSidList.size()]));
                }

                //Push通知機能有効時、通知設定の登録
                if (pushUse) {
                    CmnBelongmDao cbmDao = new CmnBelongmDao(con);
                    //グループの所属ユーザをusrSidListに追加
                    for (String grpSid : grpSidList) {
                        ArrayList<Integer> grpUsrList = cbmDao.selectBelongLiveUserSid(
                                NullDefault.getInt(grpSid, -1));
                        for (int usrSid : grpUsrList) {
                            if (!usrSidList.contains(String.valueOf(usrSid))) {
                                usrSidList.add(String.valueOf(usrSid));
                            }
                        }
                    }
                    for (String usrSid : usrSidList) {
                        SmlPushUconfModel mdl = new SmlPushUconfModel();
                        mdl.setSacSid(wacSaiSid);
                        mdl.setUsrSid(Integer.parseInt(usrSid));
                        mdl.setSpuPushuse(GSConstSmail.ACCOUNT_PUSH_OFF);
                        spuDao.insert(mdl);
                    }
                }

            }

            //ディスク使用量の新規登録
            SmlAccountDiskModel useDiskMdl = new SmlAccountDiskModel();
            useDiskMdl.setSacSid(wacSaiSid);
            //新規登録時は使用サイズを0にセット
            useDiskMdl.setSdsSize(0);
            wadDao.insert(useDiskMdl);

            accountSid = wacSaiSid;

        //編集登録
        } else {

            int wacSid = paramMdl.getSmlAccountSid();

            accountModel.setSacSid(wacSid);

            accountDao.updateAccount(accountModel, accountMode);

//            if (accountMode == GSConstSmail.ACCOUNTMODE_COMMON) {
            if (acntUserFlg) {
                //Push通知有効フラグ 退避用
                Map<Integer, Integer> escPushMap = new HashMap<>();
                if (pushUse) {
                    ArrayList<SmlPushUconfModel> spuList = spuDao.select(wacSid);
                    for (SmlPushUconfModel spuMdl : spuList) {
                        escPushMap.put(spuMdl.getUsrSid(), spuMdl.getSpuPushuse());
                    }
                }

                //アカウントのプッシュ通知設定を削除
                spuDao.deleteAccount(wacSid);

                //アカウント使用者の削除
                String[] usrSids = null;
                accountUserDao.delete(wacSid);

                ArrayList<String> grpSidList = new ArrayList<String>();
                ArrayList<String> usrSidList = new ArrayList<String>();
                for (String id : paramMdl.getSml250userKbnUser()) {
                    String str = NullDefault.getString(id, "-1");
                    if (str.contains(new String("G").subSequence(0, 1))) {
                        //グループ
                        grpSidList.add(str.substring(1, str.length()));
                    } else {
                        //ユーザ
                        usrSidList.add(str);
                    }
                }

                if (grpSidList != null && !grpSidList.isEmpty()) {
                    accountUserDao.insert(wacSid, GSConstSmail.SAC_TYPE_GROUP,
                            (String[]) grpSidList.toArray(new String[grpSidList.size()]));
                    CmnBelongmDao belongmDao = new CmnBelongmDao(con);
                    List<String> uSidList = null;
                    uSidList = belongmDao.select(
                            (String[]) grpSidList.toArray(new String[grpSidList.size()]));
                    if (uSidList != null && !uSidList.isEmpty()) {
                        usrSids = (String[]) uSidList.toArray(new String[uSidList.size()]);
                    }
                }

                if (usrSidList != null && !usrSidList.isEmpty()) {
                    accountUserDao.insert(wacSid, GSConstSmail.SAC_TYPE_USER,
                        (String[]) usrSidList.toArray(new String[usrSidList.size()]));
                    usrSids = (String[]) usrSidList.toArray(new String[usrSidList.size()]);
                    accountSortDao.delAccountSortUsr(wacSid, paramMdl.getSml250userKbnUser());
                    accountSortDao.updateAccountSortUsr(wacSid, paramMdl.getSml250userKbnUser(),
                            wacSid);
                }

                //Push通知機能有効時、通知設定の登録
                if (PushServiceOperator.getInstance(con, reqMdl.getDomain()).isUseable()) {
                    CmnBelongmDao cbmDao = new CmnBelongmDao(con);
                    //グループの所属ユーザをusrSidListに追加
                    for (String grpSid : grpSidList) {
                        ArrayList<Integer> grpUsrList = cbmDao.selectBelongLiveUserSid(
                                NullDefault.getInt(grpSid, -1));
                        for (int usrSid : grpUsrList) {
                            if (!usrSidList.contains(String.valueOf(usrSid))) {
                                usrSidList.add(String.valueOf(usrSid));
                            }
                        }
                    }
                    for (String usrSid : usrSidList) {
                        SmlPushUconfModel mdl = new SmlPushUconfModel();
                        mdl.setSacSid(wacSid);
                        mdl.setUsrSid(Integer.parseInt(usrSid));
                        if (escPushMap.containsKey(Integer.parseInt(usrSid))) {
                            mdl.setSpuPushuse(escPushMap.get(Integer.parseInt(usrSid)));
                        } else {
                            mdl.setSpuPushuse(GSConstSmail.ACCOUNT_PUSH_OFF);
                        }
                        spuDao.insert(mdl);
                    }
                }

                //アカウントを使用できないユーザの転送設定を削除
                SmlAccountForwardDao safDao = new SmlAccountForwardDao(con);
                if (usrSids != null && usrSids.length > 0) {
                    safDao.deleteCantUseUser(wacSid, usrSids);
                }
            }
            accountSid = wacSid;
        }

        /************************  自動削除設定  *********************************/
        if (admMdl.getSmaAutoDelKbn() == GSConstSmail.AUTO_DEL_ACCOUNT
                && accountSid != -1) {
            UDate nowDate = new UDate();

            //ショートメール自動削除設定
            SmlAdelModel delMdl = new SmlAdelModel();

            delMdl.setSacSid(accountSid);
            delMdl.setSadUsrKbn(GSConstSmail.SML_ADEL_USR_KBN_USER);
            delMdl.setSadDelKbn(GSConstSmail.SML_DEL_KBN_USER_SETTING);

            int jdelkbn =
                NullDefault.getInt(
                        paramMdl.getSml250JdelKbn(),
                        GSConstSmail.SML_AUTO_DEL_NO);

            delMdl.setSadJdelKbn(jdelkbn);

            int jdelYear = 0;
            int jdelMonth = 0;

            if (jdelkbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                jdelYear = NullDefault.getInt(paramMdl.getSml250JYear(), 0);
                jdelMonth = NullDefault.getInt(paramMdl.getSml250JMonth(), 0);
            }

            delMdl.setSadJdelYear(jdelYear);
            delMdl.setSadJdelMonth(jdelMonth);

            int sdelkbn =
                NullDefault.getInt(
                        paramMdl.getSml250SdelKbn(),
                        GSConstSmail.SML_AUTO_DEL_NO);

            delMdl.setSadSdelKbn(sdelkbn);

            int sdelYear = 0;
            int sdelMonth = 0;

            if (sdelkbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                sdelYear = NullDefault.getInt(paramMdl.getSml250SYear(), 0);
                sdelMonth = NullDefault.getInt(paramMdl.getSml250SMonth(), 0);
            }

            delMdl.setSadSdelYear(sdelYear);
            delMdl.setSadSdelMonth(sdelMonth);

            int wdelkbn =
                NullDefault.getInt(
                        paramMdl.getSml250WdelKbn(),
                        GSConstSmail.SML_AUTO_DEL_NO);

            delMdl.setSadWdelKbn(wdelkbn);

            int wdelYear = 0;
            int wdelMonth = 0;

            if (wdelkbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                wdelYear = NullDefault.getInt(paramMdl.getSml250WYear(), 0);
                wdelMonth = NullDefault.getInt(paramMdl.getSml250WMonth(), 0);
            }

            delMdl.setSadWdelYear(wdelYear);
            delMdl.setSadWdelMonth(wdelMonth);

            int ddelkbn =
                NullDefault.getInt(
                        paramMdl.getSml250DdelKbn(),
                        GSConstSmail.SML_AUTO_DEL_NO);

            delMdl.setSadDdelKbn(ddelkbn);

            int ddelYear = 0;
            int ddelMonth = 0;

            if (ddelkbn == GSConstSmail.SML_AUTO_DEL_LIMIT) {
                ddelYear = NullDefault.getInt(paramMdl.getSml250DYear(), 0);
                ddelMonth = NullDefault.getInt(paramMdl.getSml250DMonth(), 0);
            }

            delMdl.setSadDdelYear(ddelYear);
            delMdl.setSadDdelMonth(ddelMonth);

            delMdl.setSadAuid(sessionUserSid);
            delMdl.setSadAdate(nowDate);
            delMdl.setSadEuid(sessionUserSid);
            delMdl.setSadEdate(nowDate);

            SmlAdelDao delDao = new SmlAdelDao(con);
            if (delDao.update(delMdl) == 0) {
                delDao.insert(delMdl);
            }
        }

        /************************  転送設定  *********************************/
        paramMdl.setSml250tensoKbn(admMdl.getSmaMailfw());
        if (paramMdl.getSmlAccountMode() == GSConstSmail.ACCOUNTMODE_COMMON
            && paramMdl.getSml250tensoKbn() == GSConstSmail.MAIL_FORWARD_OK
            && paramMdl.getSml250tensoSetKbn() == GSConstSmail.MAIL_FORWARD_SET) {

            ArrayList<CmnUsrmInfModel> okUserList = new ArrayList<CmnUsrmInfModel>();
            UDate nowDate = new UDate();
            SmlAccountForwardModel smlMdl = new SmlAccountForwardModel();


            smlMdl.setSacSid(accountSid);
            smlMdl.setSafMailfw(
                    NullDefault.getInt(paramMdl.getSml250MailFw(), GSConstSmail.MAIL_FORWARD_NG));
            smlMdl.setSafSmailOp(GSConstSmail.OPKBN_UNOPENED);
            smlMdl.setSafMailDf("");
            smlMdl.setSafSmailOp(
                    NullDefault.getInt(paramMdl.getSml250SmailOp(), GSConstSmail.OPKBN_UNOPENED));
            smlMdl.setSafHuriwake(
                    NullDefault.getInt(paramMdl.getSml250HuriwakeKbn(),
                                                                  GSConstSmail.MAIL_FORWARD_NG));
            smlMdl.setSafEuid(sessionUserSid);
            smlMdl.setSafEdate(nowDate);

            //追加用
            smlMdl.setSafAuid(sessionUserSid);
            smlMdl.setSafAdate(nowDate);

            //チェックするメールアドレスリストを取得する。
            ArrayList<String> checkList = getCheckList(paramMdl);

            boolean checkFlg = false;
            //各ユーザのメールアドレス登録チェック
            //対象ユーザ情報を取得する。
            CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
            ArrayList<CmnUsrmInfModel> usrmInfList = null;

            String[] usrSids = null;

            if (paramMdl.getSml250ObjKbn() == 1) {
                //ユーザ指定

                ArrayList<String> grpSidList = new ArrayList<String>();
                ArrayList<String> usrSidList = new ArrayList<String>();
                for (String id : paramMdl.getSml250userSid()) {
                    String str = NullDefault.getString(id, "-1");
                    if (str.contains(new String("G").subSequence(0, 1))) {
                        //グループ
                        grpSidList.add(str.substring(1, str.length()));
                    } else {
                        //ユーザ
                        usrSidList.add(str);
                    }
                }

                if (grpSidList != null && !grpSidList.isEmpty()) {
                    if (grpSidList != null && !grpSidList.isEmpty()) {
                        CmnBelongmDao bdao = new CmnBelongmDao(con);
                        usrSidList.addAll(bdao.select(
                                (String[]) grpSidList.toArray(new String[grpSidList.size()])));

                    }
                }


                usrSids = (String[]) usrSidList.toArray(new String[usrSidList.size()]);
            } else {

                ArrayList<String> grpSidList = new ArrayList<String>();
                ArrayList<String> usrSidList = new ArrayList<String>();
                for (String id : paramMdl.getSml250userKbnUser()) {
                    String str = NullDefault.getString(id, "-1");
                    if (str.contains(new String("G").subSequence(0, 1))) {
                        //グループ
                        grpSidList.add(str.substring(1, str.length()));
                    } else {
                        //ユーザ
                        usrSidList.add(str);
                    }
                }

                if (grpSidList != null && !grpSidList.isEmpty()) {
                    if (grpSidList != null && !grpSidList.isEmpty()) {
                        CmnBelongmDao bdao = new CmnBelongmDao(con);
                        usrSidList.addAll(bdao.select(
                                (String[]) grpSidList.toArray(new String[grpSidList.size()])));

                    }
                }

                usrSids = (String[]) usrSidList.toArray(new String[usrSidList.size()]);

            }

            //ユーザ情報取得
            usrmInfList = usrmInfDao.getUserList(usrSids);

            if (usrmInfList != null && usrmInfList.size() > 0) {

                //メールアドレスチェックフラグ
                if (paramMdl.getSml250PassKbn() == 1
                && (paramMdl.getSml250MailFw().equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))
                        || paramMdl.getSml250MailFw().equals(
                                String.valueOf(GSConstSmail.MAIL_FORWARD_FUZAI_OK)))) {
                    for (String mKbn : checkList) {
                        if (!mKbn.equals("0")) {
                            //チェック項目がある。
                            checkFlg = true;
                            break;
                        }
                    }
                }

                boolean okFlg = true;
                if (checkFlg) {
                    for (CmnUsrmInfModel model : usrmInfList) {

                        if (model.getUsrSid() < 100) {
                            continue;
                        }
                        okFlg = true;
                        for (String mailKbn : checkList) {

                            if (mailKbn.equals("1")) {
                                if (StringUtil.isNullZeroString(model.getUsiMail1())) {
                                    okFlg = false;
                                    break;
                                }

                            } else if (mailKbn.equals("2")) {
                                if (StringUtil.isNullZeroString(model.getUsiMail2())) {
                                    okFlg = false;
                                    break;
                                }

                            } else if (mailKbn.equals("3")) {
                                if (StringUtil.isNullZeroString(model.getUsiMail3())) {
                                    okFlg = false;
                                    break;
                                }
                            }
                        }
                        if (okFlg) {
                            okUserList.add(model);
                        }
                    }

                } else {
                    //チェックが必要ない場合
                    for (CmnUsrmInfModel model : usrmInfList) {
                        if (model.getUsrSid() < 100) {
                            continue;
                        }
                        okUserList.add(model);
                    }
                }

                if (paramMdl.getSml250MailFw().equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))
                        || paramMdl.getSml250MailFw().equals(
                                String.valueOf(GSConstSmail.MAIL_FORWARD_FUZAI_OK))) {
                    //転送機能を使用する場合
                    __updateMailFwOn(paramMdl, con, okUserList, smlMdl);
                } else {
                    //転送機能を使用しない場合
                    __updateMailFwOff(paramMdl, con, okUserList, smlMdl);
                }

            }
        }

        //自動送信先
        __entryAutoDest(accountSid, paramMdl, con);
        return accountModel;
    }
    /**
     *
     * <br>[機  能] 自動送信先を設定
     * <br>[解  説]
     * <br>[備  考]
     * @param sacSid アカウントSID
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    private void __entryAutoDest(int sacSid,
            Sml250knParamModel paramMdl,
            Connection con) throws SQLException {
        SmlAccountAutoDestDao sadDao = new SmlAccountAutoDestDao(con);
        SmlAccountDao sacDao = new SmlAccountDao(con);
        sadDao.delete(sacSid);
        //To
        List<String> usrSidList = new ArrayList<String>();
        List<String> accSidList = new ArrayList<String>();
        String[] strSids = paramMdl.getSml250AutoDestToUsrSid();
        _splitSids(strSids, usrSidList, accSidList);
        List<String> accSids = new ArrayList<String>();
        if (!usrSidList.isEmpty()) {
            accSids = sacDao.selectFromUsrSids(
                usrSidList.toArray(new String[usrSidList.size()]));
        }
        for (String accSid : accSids) {
            SmlAccountAutoDestModel mdl = new SmlAccountAutoDestModel();
            mdl.setSacSid(sacSid);
            mdl.setSaaType(GSConstSmail.SML_SEND_KBN_ATESAKI);
            mdl.setSaaSid(NullDefault.getInt(accSid, -1));
            sadDao.insert(mdl);
        }
        for (String accSid : accSidList) {
            SmlAccountAutoDestModel mdl = new SmlAccountAutoDestModel();
            mdl.setSacSid(sacSid);
            mdl.setSaaType(GSConstSmail.SML_SEND_KBN_ATESAKI);
            mdl.setSaaSid(NullDefault.getInt(accSid, -1));
            sadDao.insert(mdl);
        }

        //Cc
        usrSidList = new ArrayList<String>();
        accSidList = new ArrayList<String>();
        strSids = paramMdl.getSml250AutoDestCcUsrSid();
        _splitSids(strSids, usrSidList, accSidList);
        accSids = new ArrayList<String>();
        if (!usrSidList.isEmpty()) {
            accSids = sacDao.selectFromUsrSids(
                usrSidList.toArray(new String[usrSidList.size()]));
        }
        for (String accSid : accSids) {
            SmlAccountAutoDestModel mdl = new SmlAccountAutoDestModel();
            mdl.setSacSid(sacSid);
            mdl.setSaaType(GSConstSmail.SML_SEND_KBN_CC);
            mdl.setSaaSid(NullDefault.getInt(accSid, -1));
            sadDao.insert(mdl);
        }
        for (String accSid : accSidList) {
            SmlAccountAutoDestModel mdl = new SmlAccountAutoDestModel();
            mdl.setSacSid(sacSid);
            mdl.setSaaType(GSConstSmail.SML_SEND_KBN_CC);
            mdl.setSaaSid(NullDefault.getInt(accSid, -1));
            sadDao.insert(mdl);
        }
        //Bcc
        usrSidList = new ArrayList<String>();
        accSidList = new ArrayList<String>();
        strSids = paramMdl.getSml250AutoDestBccUsrSid();
        _splitSids(strSids, usrSidList, accSidList);
        accSids = new ArrayList<String>();
        if (!usrSidList.isEmpty()) {
            accSids = sacDao.selectFromUsrSids(
                usrSidList.toArray(new String[usrSidList.size()]));
        }
        for (String accSid : accSids) {
            SmlAccountAutoDestModel mdl = new SmlAccountAutoDestModel();
            mdl.setSacSid(sacSid);
            mdl.setSaaType(GSConstSmail.SML_SEND_KBN_BCC);
            mdl.setSaaSid(NullDefault.getInt(accSid, -1));
            sadDao.insert(mdl);
        }
        for (String accSid : accSidList) {
            SmlAccountAutoDestModel mdl = new SmlAccountAutoDestModel();
            mdl.setSacSid(sacSid);
            mdl.setSaaType(GSConstSmail.SML_SEND_KBN_BCC);
            mdl.setSaaSid(NullDefault.getInt(accSid, -1));
            sadDao.insert(mdl);
        }

    }
    /**
     * <br>[機  能] 反映するメールアドレスリストを取得する。
     * <br>[解  説]
     * <br>[備  考] その他のアドレス:0 メールアドレス1:1 メールアドレス2:2 メールアドレス3:3
     *
     * @param paramMdl パラメータ情報
     * @return checkList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getCheckList(Sml250knParamModel paramMdl) throws SQLException {

        ArrayList<String> checkList = new ArrayList<String>();

        checkList.add(paramMdl.getSml250MailDfSelected());
        if (paramMdl.getSml250HuriwakeKbn().equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))) {
            if (checkList.indexOf(paramMdl.getSml250Zmail1Selected()) == -1) {
                checkList.add(paramMdl.getSml250Zmail1Selected());
            }
            if (checkList.indexOf(paramMdl.getSml250Zmail2Selected()) == -1) {
                checkList.add(paramMdl.getSml250Zmail2Selected());
            }
            if (checkList.indexOf(paramMdl.getSml250Zmail3Selected()) == -1) {
                checkList.add(paramMdl.getSml250Zmail3Selected());
            }
        } else if (paramMdl.getSml250HuriwakeKbn().equals(String.valueOf(
                GSConstSmail.MAIL_FORWARD_FUZAI_OK))) {
            if (checkList.indexOf(paramMdl.getSml250Zmail2Selected()) == -1) {
                checkList.add(paramMdl.getSml250Zmail2Selected());
            }
        }
        return checkList;
    }

    /**
     * <br>[機  能] メール転送設定の更新を行う(転送機能を使用する場合)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param okUserList 更新対象ユーザ情報リスト
     * @param smlMdl 更新モデル
     * @throws SQLException SQL実行例外
     */
    private void __updateMailFwOn(
            Sml250knParamModel paramMdl,
            Connection con,
            ArrayList<CmnUsrmInfModel> okUserList,
            SmlAccountForwardModel smlMdl)
        throws SQLException {

        SmlAccountForwardDao smlDao = new SmlAccountForwardDao(con);
        String dfmail = "";
        String zmail1 = "";
        String zmail2 = "";
        String zmail3 = "";
        String dfmailOther = paramMdl.getSml250MailDf();
        String zmail1Other = paramMdl.getSml250Zmail1();
        String zmail2Other = paramMdl.getSml250Zmail2();
        String zmail3Other = paramMdl.getSml250Zmail3();
        int updateCnt = 0;

        for (CmnUsrmInfModel usrModel : okUserList) {

            //デフォルト
            if (paramMdl.getSml250MailDfSelected().equals("1")) {
                dfmail = usrModel.getUsiMail1();
            } else if (paramMdl.getSml250MailDfSelected().equals("2")) {
                dfmail = usrModel.getUsiMail2();
            } else if (paramMdl.getSml250MailDfSelected().equals("3")) {
                dfmail = usrModel.getUsiMail3();
            } else if (paramMdl.getSml250MailDfSelected().equals("0")) {
                dfmail = dfmailOther;
            }
            smlMdl.setSafMailDf(dfmail);
            smlMdl.setUsrSid(usrModel.getUsrSid());

            if (smlMdl.getSafHuriwake() == GSConstSmail.MAIL_FORWARD_OK) {

                //在席
                if (paramMdl.getSml250Zmail1Selected().equals("1")) {
                    zmail1 = usrModel.getUsiMail1();
                } else if (paramMdl.getSml250Zmail1Selected().equals("2")) {
                    zmail1 = usrModel.getUsiMail2();
                } else if (paramMdl.getSml250Zmail1Selected().equals("3")) {
                    zmail1 = usrModel.getUsiMail3();
                } else if (paramMdl.getSml250Zmail1Selected().equals("0")) {
                    zmail1 = zmail1Other;
                }

                //不在
                if (paramMdl.getSml250Zmail2Selected().equals("1")) {
                    zmail2 = usrModel.getUsiMail1();
                } else if (paramMdl.getSml250Zmail2Selected().equals("2")) {
                    zmail2 = usrModel.getUsiMail2();
                } else if (paramMdl.getSml250Zmail2Selected().equals("3")) {
                    zmail2 = usrModel.getUsiMail3();
                } else if (paramMdl.getSml250Zmail2Selected().equals("0")) {
                    zmail2 = zmail2Other;
                }

                //その他
                if (paramMdl.getSml250Zmail3Selected().equals("1")) {
                    zmail3 = usrModel.getUsiMail1();
                } else if (paramMdl.getSml250Zmail3Selected().equals("2")) {
                    zmail3 = usrModel.getUsiMail2();
                } else if (paramMdl.getSml250Zmail3Selected().equals("3")) {
                    zmail3 = usrModel.getUsiMail3();
                } else if (paramMdl.getSml250Zmail3Selected().equals("0")) {
                    zmail3 = zmail3Other;
                }
                smlMdl.setSafZmail1(zmail1);
                smlMdl.setSafZmail2(zmail2);
                smlMdl.setSafZmail3(zmail3);


            } else if (smlMdl.getSafHuriwake() == GSConstSmail.MAIL_FORWARD_FUZAI_OK) {

                //不在
                if (paramMdl.getSml250Zmail2Selected().equals("1")) {
                    zmail2 = usrModel.getUsiMail1();
                } else if (paramMdl.getSml250Zmail2Selected().equals("2")) {
                    zmail2 = usrModel.getUsiMail2();
                } else if (paramMdl.getSml250Zmail2Selected().equals("3")) {
                    zmail2 = usrModel.getUsiMail3();
                } else if (paramMdl.getSml250Zmail2Selected().equals("0")) {
                    zmail2 = zmail2Other;
                }
                smlMdl.setSafZmail1("");
                smlMdl.setSafZmail2(zmail2);
                smlMdl.setSafZmail3("");

            } else {

                smlMdl.setSafZmail1("");
                smlMdl.setSafZmail2("");
                smlMdl.setSafZmail3("");

            }

            //更新
            updateCnt = smlDao.updateSmlForward(smlMdl);

            if (updateCnt == 0) {
                //レコードが無い場合は追加
                smlDao.insert(smlMdl);
            }


        }
    }

    /**
     * <br>[機  能] メール転送設定の更新を行う(転送機能を使用しない場合)
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param okUserList 更新対象ユーザ情報リスト
     * @param smlMdl 更新モデル
     * @throws SQLException SQL実行例外
     */
    private void __updateMailFwOff(
            Sml250knParamModel paramMdl,
            Connection con,
            ArrayList<CmnUsrmInfModel> okUserList,
            SmlAccountForwardModel smlMdl)
        throws SQLException {

        SmlAccountForwardDao smlDao = new SmlAccountForwardDao(con);
        int updateCnt = 0;

        smlMdl.setSafMailDf("");
        smlMdl.setSafZmail1("");
        smlMdl.setSafZmail2("");
        smlMdl.setSafZmail3("");

        for (CmnUsrmInfModel usrModel : okUserList) {

            smlMdl.setUsrSid(usrModel.getUsrSid());

            //更新
            updateCnt = smlDao.updateSmlForward(smlMdl);

            if (updateCnt == 0) {
                //レコードが無い場合は追加
                smlDao.insert(smlMdl);
            }


        }
    }

}
