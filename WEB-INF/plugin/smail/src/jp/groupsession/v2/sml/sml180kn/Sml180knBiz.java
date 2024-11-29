package jp.groupsession.v2.sml.sml180kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.SmlAccountForwardModel;

/**
 * <br>[機  能] ショートメール 管理者設定 メール転送一括設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml180knBiz {

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(Sml180knParamModel paramMdl, Connection con)
        throws SQLException {

        //ユーザリストをセットする
        __setUserList(paramMdl, con);
    }

    /**
     * <br>[機  能] ユーザリストをセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    private void __setUserList(Sml180knParamModel paramMdl, Connection con)
        throws SQLException {

        ArrayList<String> okUserList = new ArrayList<String>();
        ArrayList<String> ngUserList = new ArrayList<String>();
        boolean checkFlg = false;

        //チェックするメールアドレスリストを取得する。
        ArrayList<String> checkList = getCheckList(paramMdl);

        //対象ユーザ情報を取得する。
        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
        ArrayList<CmnUsrmInfModel> usrmInfList = null;
        String[] usrSids = null;
        if (paramMdl.getSml180ObjKbn() == 1) {
            //ユーザ指定
            usrSids = paramMdl.getSml180userSid();;
        }

        //ユーザ情報取得
        usrmInfList = usrmInfDao.getUserList(usrSids);

        if (usrmInfList == null || usrmInfList.size() < 1) {
            return;
        }

        //メールアドレスチェックフラグ
        if (paramMdl.getSml180PassKbn() == 1
        && (paramMdl.getSml180MailFw().equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))
             || paramMdl.getSml180MailFw().equals(
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
                okUserList.add(model.getUsiSei() + " " + model.getUsiMei());
                cnt++;
            }
            paramMdl.setSml180knUsrOkLabelList(okUserList);
            paramMdl.setSml180knUsrCnt(cnt);
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
                        ngUserList.add(model.getUsiSei() + " " + model.getUsiMei());
                        okFlg = false;
                        ngCount++;
                        break;
                    }

                } else if (mailKbn.equals("2")) {
                    if (StringUtil.isNullZeroString(model.getUsiMail2())) {
                        ngUserList.add(model.getUsiSei() + " " + model.getUsiMei());
                        okFlg = false;
                        ngCount++;
                        break;
                    }

                } else if (mailKbn.equals("3")) {
                    if (StringUtil.isNullZeroString(model.getUsiMail3())) {
                        ngUserList.add(model.getUsiSei() + " " + model.getUsiMei());
                        okFlg = false;
                        ngCount++;
                        break;
                    }
                }
            }
            if (okFlg) {
                okUserList.add(model.getUsiSei() + " " + model.getUsiMei());
                count++;
            }
        }
        paramMdl.setSml180knUsrOkLabelList(okUserList);
        paramMdl.setSml180knUsrNgLabelList(ngUserList);
        paramMdl.setSml180knUsrCnt(count);
        paramMdl.setSml180knUsrCntNg(ngCount);

    }

    /**
     * <br>[機  能] メール転送設定の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param sessionUsrSid ユーザSID
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void updateMailFw(int sessionUsrSid,
                                Sml180knParamModel paramMdl,
                                Connection con)
        throws SQLException {

        ArrayList<CmnUsrmInfModel> okUserList = new ArrayList<CmnUsrmInfModel>();
        UDate nowDate = new UDate();
        SmlAccountForwardModel smlMdl = new SmlAccountForwardModel();

        smlMdl.setSafMailfw(
                NullDefault.getInt(paramMdl.getSml180MailFw(), GSConstSmail.MAIL_FORWARD_NG));
        smlMdl.setSafSmailOp(GSConstSmail.OPKBN_UNOPENED);
        smlMdl.setSafMailDf("");
        smlMdl.setSafSmailOp(
                NullDefault.getInt(paramMdl.getSml180SmailOp(), GSConstSmail.OPKBN_UNOPENED));
        smlMdl.setSafHuriwake(
                NullDefault.getInt(paramMdl.getSml180HuriwakeKbn(), GSConstSmail.MAIL_FORWARD_NG));
        smlMdl.setSafEuid(sessionUsrSid);
        smlMdl.setSafEdate(nowDate);

        //追加用
        smlMdl.setSafAuid(sessionUsrSid);
        smlMdl.setSafAdate(nowDate);

        //チェックするメールアドレスリストを取得する。
        ArrayList<String> checkList = getCheckList(paramMdl);

        boolean checkFlg = false;
        //各ユーザのメールアドレス登録チェック
        //対象ユーザ情報を取得する。
        CmnUsrmInfDao usrmInfDao = new CmnUsrmInfDao(con);
        ArrayList<CmnUsrmInfModel> usrmInfList = null;

        String[] usrSids = null;
        if (paramMdl.getSml180ObjKbn() == 1) {
            //ユーザ指定
            usrSids = paramMdl.getSml180userSid();;
        }
        //ユーザ情報取得
        usrmInfList = usrmInfDao.getUserList(usrSids);

        if (usrmInfList == null || usrmInfList.size() < 1) {
            //対象ユーザ情報なし
            return;
        }

        //メールアドレスチェックフラグ
        if (paramMdl.getSml180PassKbn() == 1
        && (paramMdl.getSml180MailFw().equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))
                || paramMdl.getSml180MailFw().equals(
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

//        if (paramMdl.getSml180MailFw().equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))
//                || paramMdl.getSml180MailFw().equals(
//                        String.valueOf(GSConstSmail.MAIL_FORWARD_FUZAI_OK))) {
//            //転送機能を使用する場合
//            __updateMailFwOn(paramMdl, con, okUserList, smlMdl);
//        } else {
//            //転送機能を使用しない場合
//            __updateMailFwOff(paramMdl, con, okUserList, smlMdl);
//        }

    }

//    /**
//     * <br>[機  能] メール転送設定の更新を行う(転送機能を使用する場合)
//     * <br>[解  説]
//     * <br>[備  考]
//     *
//     * @param paramMdl パラメータ情報
//     * @param con コネクション
//     * @param okUserList 更新対象ユーザ情報リスト
//     * @param smlMdl 更新モデル
//     * @throws SQLException SQL実行例外
//     */
//    private void __updateMailFwOn(
//            Sml180knParamModel paramMdl,
//            Connection con,
//            ArrayList<CmnUsrmInfModel> okUserList,
//            SmlAccountForwardModel smlMdl)
//        throws SQLException {
//
//        SmlAccountForwardDao smlDao = new SmlAccountForwardDao(con);
//        String dfmail = "";
//        String zmail1 = "";
//        String zmail2 = "";
//        String zmail3 = "";
//        String dfmailOther = paramMdl.getSml180MailDf();
//        String zmail1Other = paramMdl.getSml180Zmail1();
//        String zmail2Other = paramMdl.getSml180Zmail2();
//        String zmail3Other = paramMdl.getSml180Zmail3();
//        int updateCnt = 0;
//
//        for (CmnUsrmInfModel usrModel : okUserList) {
//
//            //デフォルト
//            if (paramMdl.getSml180MailDfSelected().equals("1")) {
//                dfmail = usrModel.getUsiMail1();
//            } else if (paramMdl.getSml180MailDfSelected().equals("2")) {
//                dfmail = usrModel.getUsiMail2();
//            } else if (paramMdl.getSml180MailDfSelected().equals("3")) {
//                dfmail = usrModel.getUsiMail3();
//            } else if (paramMdl.getSml180MailDfSelected().equals("0")) {
//                dfmail = dfmailOther;
//            }
//            smlMdl.setSafMailDf(dfmail);
//            smlMdl.setUsrSid(usrModel.getUsrSid());
//
//            if (smlMdl.getSafHuriwake() == GSConstSmail.MAIL_FORWARD_OK) {
//
//                //在席
//                if (paramMdl.getSml180Zmail1Selected().equals("1")) {
//                    zmail1 = usrModel.getUsiMail1();
//                } else if (paramMdl.getSml180Zmail1Selected().equals("2")) {
//                    zmail1 = usrModel.getUsiMail2();
//                } else if (paramMdl.getSml180Zmail1Selected().equals("3")) {
//                    zmail1 = usrModel.getUsiMail3();
//                } else if (paramMdl.getSml180Zmail1Selected().equals("0")) {
//                    zmail1 = zmail1Other;
//                }
//
//                //不在
//                if (paramMdl.getSml180Zmail2Selected().equals("1")) {
//                    zmail2 = usrModel.getUsiMail1();
//                } else if (paramMdl.getSml180Zmail2Selected().equals("2")) {
//                    zmail2 = usrModel.getUsiMail2();
//                } else if (paramMdl.getSml180Zmail2Selected().equals("3")) {
//                    zmail2 = usrModel.getUsiMail3();
//                } else if (paramMdl.getSml180Zmail2Selected().equals("0")) {
//                    zmail2 = zmail2Other;
//                }
//
//                //その他
//                if (paramMdl.getSml180Zmail3Selected().equals("1")) {
//                    zmail3 = usrModel.getUsiMail1();
//                } else if (paramMdl.getSml180Zmail3Selected().equals("2")) {
//                    zmail3 = usrModel.getUsiMail2();
//                } else if (paramMdl.getSml180Zmail3Selected().equals("3")) {
//                    zmail3 = usrModel.getUsiMail3();
//                } else if (paramMdl.getSml180Zmail3Selected().equals("0")) {
//                    zmail3 = zmail3Other;
//                }
//                smlMdl.setSafZmail1(zmail1);
//                smlMdl.setSafZmail2(zmail2);
//                smlMdl.setSafZmail3(zmail3);
//
//
//            } else if (smlMdl.getSafHuriwake() == GSConstSmail.MAIL_FORWARD_FUZAI_OK) {
//
//                //不在
//                if (paramMdl.getSml180Zmail2Selected().equals("1")) {
//                    zmail2 = usrModel.getUsiMail1();
//                } else if (paramMdl.getSml180Zmail2Selected().equals("2")) {
//                    zmail2 = usrModel.getUsiMail2();
//                } else if (paramMdl.getSml180Zmail2Selected().equals("3")) {
//                    zmail2 = usrModel.getUsiMail3();
//                } else if (paramMdl.getSml180Zmail2Selected().equals("0")) {
//                    zmail2 = zmail2Other;
//                }
//                smlMdl.setSafZmail1("");
//                smlMdl.setSafZmail2(zmail2);
//                smlMdl.setSafZmail3("");
//
//            } else {
//
//                smlMdl.setSafZmail1("");
//                smlMdl.setSafZmail2("");
//                smlMdl.setSafZmail3("");
//
//            }
////
////            //更新
////            updateCnt = smlDao.updateSmlForward(smlMdl);
////
////            if (updateCnt == 0) {
////                //レコードが無い場合は追加
////                smlDao.insertSmlUser(smlMdl);
////            }
//
//
//        }
//    }
//
//    /**
//     * <br>[機  能] メール転送設定の更新を行う(転送機能を使用しない場合)
//     * <br>[解  説]
//     * <br>[備  考]
//     *
//     * @param paramMdl パラメータ情報
//     * @param con コネクション
//     * @param okUserList 更新対象ユーザ情報リスト
//     * @param smlMdl 更新モデル
//     * @throws SQLException SQL実行例外
//     */
//    private void __updateMailFwOff(
//            Sml180knParamModel paramMdl,
//            Connection con,
//            ArrayList<CmnUsrmInfModel> okUserList,
//            SmlUserModel smlMdl)
//        throws SQLException {
//
//        SmlUserDao smlDao = new SmlUserDao(con);
//        int updateCnt = 0;
//
////        smlMdl.setSmlMailDf("");
////        smlMdl.setSmlZmail1("");
////        smlMdl.setSmlZmail2("");
////        smlMdl.setSmlZmail3("");
////        smlMdl.setSmlMainKbn(0);
////        smlMdl.setSmlMainCnt(10);
////        smlMdl.setSmlMainSort(0);
//
////        for (CmnUsrmInfModel usrModel : okUserList) {
////
////            smlMdl.setUsrSid(usrModel.getUsrSid());
////
////            //更新
////            updateCnt = smlDao.updateSmlForward(smlMdl);
////
////            if (updateCnt == 0) {
////                //レコードが無い場合は追加
////                smlDao.insertSmlUser(smlMdl);
////            }
////
////
////        }
//    }

    /**
     * <br>[機  能] 反映するメールアドレスリストを取得する。
     * <br>[解  説]
     * <br>[備  考] その他のアドレス:0 メールアドレス1:1 メールアドレス2:2 メールアドレス3:3
     *
     * @param paramMdl パラメータ情報
     * @return checkList
     * @throws SQLException SQL実行例外
     */
    public ArrayList<String> getCheckList(Sml180knParamModel paramMdl) throws SQLException {

        ArrayList<String> checkList = new ArrayList<String>();

        checkList.add(paramMdl.getSml180MailDfSelected());
        if (paramMdl.getSml180HuriwakeKbn().equals(String.valueOf(GSConstSmail.MAIL_FORWARD_OK))) {
            if (checkList.indexOf(paramMdl.getSml180Zmail1Selected()) == -1) {
                checkList.add(paramMdl.getSml180Zmail1Selected());
            }
            if (checkList.indexOf(paramMdl.getSml180Zmail2Selected()) == -1) {
                checkList.add(paramMdl.getSml180Zmail2Selected());
            }
            if (checkList.indexOf(paramMdl.getSml180Zmail3Selected()) == -1) {
                checkList.add(paramMdl.getSml180Zmail3Selected());
            }
        } else if (paramMdl.getSml180HuriwakeKbn().equals(String.valueOf(
                GSConstSmail.MAIL_FORWARD_FUZAI_OK))) {
            if (checkList.indexOf(paramMdl.getSml180Zmail2Selected()) == -1) {
                checkList.add(paramMdl.getSml180Zmail2Selected());
            }
        }
        return checkList;
    }
}