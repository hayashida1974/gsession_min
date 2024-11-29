package jp.groupsession.v2.wml.wml280kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.biz.WmlGetDestListInfoBiz;
import jp.groupsession.v2.wml.dao.base.WmlDestlistAccessConfDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistAddressDao;
import jp.groupsession.v2.wml.dao.base.WmlDestlistDao;
import jp.groupsession.v2.wml.model.WmlAddressParamModel;
import jp.groupsession.v2.wml.model.WmlDestListInfoModel;
import jp.groupsession.v2.wml.model.base.WmlDestlistAccessConfModel;
import jp.groupsession.v2.wml.model.base.WmlDestlistAddressModel;
import jp.groupsession.v2.wml.model.base.WmlDestlistModel;
import jp.groupsession.v2.wml.wml280.Wml280Biz;
import jp.groupsession.v2.wml.wml280.Wml280ParamModel;

/**
 * <br>[機  能] WEBメール 送信先リスト登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml280knBiz extends Wml280Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml280knBiz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 署名情報の取得に失敗
     */
    public void setInitData(Connection con, Wml280knParamModel paramMdl, RequestModel reqMdl)
    throws SQLException, IOToolsException {

        WmlGetDestListInfoBiz wdlInfBiz = new WmlGetDestListInfoBiz();
        WmlDestListInfoModel wdlInfMdl = new WmlDestListInfoModel();

        //編集可能な送信先リストではない場合、閲覧として扱う
        int wdlSid = paramMdl.getWmlEditDestList();
        if (paramMdl.getWmlAccountMode() == 1
        && paramMdl.getWml280initFlg() == 0
        && wdlSid > 0) {
            WmlBiz wmlBiz = new WmlBiz();
            if (!wmlBiz.canEditDestlist(con, wdlSid, reqMdl.getSmodel().getUsrsid())) {
                //画面表示モードを設定
                paramMdl.setWml280knMode(1);

                //対象の送信先リスト情報を設定する
                wdlInfBiz.getDestlistInfo(con, paramMdl.getWmlEditDestList(), wdlInfMdl);
                _setDestListInfo(paramMdl, wdlInfMdl);
            }
        }

        //備考(表示用)を設定
        paramMdl.setWml280knBiko(
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(paramMdl.getWml280biko(), "")));

        //送信先を設定
        _setParam(paramMdl, wdlInfMdl);
        wdlInfBiz.setDestListParam(con, wdlInfMdl);
        paramMdl.setDestUserList(wdlInfMdl.getDestUserList());

        //アクセス権限を設定
        __setSelectAccessCombo(con, paramMdl);
    }

    /**
     * <br>[機  能] 送信先リスト情報の登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param mtCon 採番コントローラ
     * @param sessionUserSid セッションユーザSID
     * @param reqMdl リクエスト情報
     * @throws Exception 実行時例外
     */
    public void entryDestlistData(Connection con, Wml280knParamModel paramMdl,
            MlCountMtController mtCon, int sessionUserSid, RequestModel reqMdl)
    throws Exception {

        log__.debug("START");

        int cmdMode = paramMdl.getWmlCmdMode();
        UDate now = new UDate();

        //送信先リスト情報の登録
        int wdlSid = 0;
        int beforeWdlSid = paramMdl.getWmlEditDestList();
        WmlDestlistModel destlistMdl = new WmlDestlistModel();

        destlistMdl.setWdlName(paramMdl.getWml280name());
        destlistMdl.setWdlBiko(paramMdl.getWml280biko());
        destlistMdl.setWdlEuid(sessionUserSid);
        destlistMdl.setWdlEdate(now);

        WmlDestlistDao destlistDao = new WmlDestlistDao(con);
        if  (cmdMode == GSConstWebmail.CMDMODE_ADD) {
            wdlSid = (int) mtCon.getSaibanNumber(GSConstWebmail.SBNSID_WEBMAIL,
                                                      GSConstWebmail.SBNSID_SUB_DESTLIST,
                                                      sessionUserSid);
            destlistMdl.setWdlSid(wdlSid);
            destlistMdl.setWdlAuid(sessionUserSid);
            destlistMdl.setWdlAdate(now);
            destlistDao.insert(destlistMdl);

        } else if (cmdMode == GSConstWebmail.CMDMODE_EDIT) {
            wdlSid = beforeWdlSid;
            destlistMdl.setWdlSid(wdlSid);
            destlistDao.update(destlistMdl);
        }

        WmlDestlistAddressDao destlistAddressDao = new WmlDestlistAddressDao(con);
        WmlDestlistAccessConfDao destlistAccessDao = new WmlDestlistAccessConfDao(con);
        if (beforeWdlSid > 0) {
            destlistAddressDao.delete(beforeWdlSid);
            destlistAccessDao.delete(beforeWdlSid);
        }

        //送信先リスト_送信先の登録
        WmlGetDestListInfoBiz wdlInfBiz = new WmlGetDestListInfoBiz();
        WmlDestListInfoModel wdlInfMdl = new WmlDestListInfoModel();

        _setParam(paramMdl, wdlInfMdl);
        wdlInfBiz.setDestListParam(con, wdlInfMdl);
        paramMdl.setDestUserList(wdlInfMdl.getDestUserList());
        WmlDestlistAddressModel destlistAddressMdl = new WmlDestlistAddressModel();
        destlistAddressMdl.setWdlSid(wdlSid);

        //送信先リスト_送信先の登録
        List<WmlAddressParamModel> destUserList = paramMdl.getDestUserList();
        if (destUserList != null && !destUserList.isEmpty()) {
            for (WmlAddressParamModel destData : destUserList) {
                if (destData.getType() == GSConstWebmail.WDA_TYPE_ADDRESS) {
                    destlistAddressMdl.setWdaType(GSConstWebmail.WDA_TYPE_ADDRESS);
                } else {
                    destlistAddressMdl.setWdaType(GSConstWebmail.WDA_TYPE_USER);
                }
                destlistAddressMdl.setWdaSid(destData.getSid());
                destlistAddressMdl.setWdaAdrno(destData.getMailNo());

                if (destlistAddressDao.select(destlistAddressMdl.getWdlSid(),
                                                        destlistAddressMdl.getWdaType(),
                                                        destlistAddressMdl.getWdaSid(),
                                                        destlistAddressMdl.getWdaAdrno()) == null) {
                    destlistAddressDao.insert(destlistAddressMdl);
                }
            }
        }

        //送信先リスト_アクセス設定を登録する
        __insertDestlistAccessConf(con, wdlSid, paramMdl.getWml280accessFull(),
                                                GSConstWebmail.WLA_AUTH_ALL);
        __insertDestlistAccessConf(con, wdlSid, paramMdl.getWml280accessRead(),
                                                GSConstWebmail.WLA_AUTH_READ);
    }

    /**
     * <br>[機  能] 送信先リスト_アクセス設定を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param wdlSid 送信先リストSID
     * @param accessList ユーザSID(ユーザ or グループ)
     * @param wlaAuth 権限区分
     * @throws SQLException SQL実行時例外
     */
    private void __insertDestlistAccessConf(Connection con, int wdlSid,
                                                            String[] accessList, int wlaAuth)
    throws SQLException {
        if (accessList == null || accessList.length == 0) {
            return;
        }

        WmlDestlistAccessConfDao destlistAccessDao = new WmlDestlistAccessConfDao(con);
        WmlDestlistAccessConfModel destlistAccessMdl = new WmlDestlistAccessConfModel();
        destlistAccessMdl.setWdlSid(wdlSid);
        for (String usrSid : accessList) {
            destlistAccessMdl.setWlaAuth(wlaAuth);
            if (usrSid.startsWith("G")) {
                destlistAccessMdl.setWlaKbn(GSConstWebmail.WLA_KBN_GROUP);
                destlistAccessMdl.setWlaUsrSid(Integer.parseInt(usrSid.substring(1)));
            } else {
                destlistAccessMdl.setWlaKbn(GSConstWebmail.WLA_KBN_USER);
                destlistAccessMdl.setWlaUsrSid(Integer.parseInt(usrSid));
            }
            destlistAccessDao.insert(destlistAccessMdl);
        }
    }

    /**
     * <br>[機  能] ユーザコンボを設定する
     * <br>[解  説] アクセス権限を設定する
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    private void __setSelectAccessCombo(Connection con, Wml280ParamModel paramMdl)
    throws SQLException {

        //アクセス権限を設定
        paramMdl.setWml280accessFullSelectCombo(
                __getUserLabelList(con, paramMdl.getWml280accessFull()));
        paramMdl.setWml280accessReadSelectCombo(
                __getUserLabelList(con, paramMdl.getWml280accessRead()));
    }

    /**
     * <br>[機  能]  ユーザコンボを作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param selectUserSid ユーザSID
     * @param gsMsg GsMessage
     * @return ユーザコンボ
     * @throws SQLException SQL実行時例外
     */
    protected List<LabelValueBean> __createUserCombo(Connection con, String[] selectUserSid,
                                                                                    GsMessage gsMsg)
    throws SQLException {

        UserBiz userBiz = new UserBiz();
        ArrayList<BaseUserModel> ulist
                = userBiz.getBaseUserList(con, selectUserSid);
        LabelValueBean labelBean = null;
        List <LabelValueBean> selectUserList = new ArrayList<LabelValueBean>();
        for (BaseUserModel umodel : ulist) {
            labelBean = new LabelValueBean();
            labelBean.setLabel(umodel.getUsisei() + " " + umodel.getUsimei());
            labelBean.setValue(String.valueOf(umodel.getUsrsid()));
            selectUserList.add(labelBean);
        }

        return selectUserList;
    }

    /**
     * <br>[機  能] ユーザ情報の一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userList 取得するユーザSID・グループSID
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getUserLabelList(Connection con, String[] userList)
    throws SQLException {

        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();

        //
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (userList != null) {
            for (String userId : userList) {
                String str = NullDefault.getString(userId, "-1");
                log__.debug("str==>" + str);
                log__.debug("G.index==>" + str.indexOf("G"));
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(Integer.valueOf(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }
        //グループ情報取得
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
        UsrLabelValueBean label = null;
        for (GroupModel gmodel : glist) {
            label = new UsrLabelValueBean();
            label.setLabel(gmodel.getGroupName());
            label.setValue("G" + String.valueOf(gmodel.getGroupSid()));
            ret.add(label);
        }

        //ユーザ情報取得
        UserBiz userBiz = new UserBiz();
        List<CmnUsrmInfModel> ulist
                = userBiz.getUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        for (CmnUsrmInfModel umodel : ulist) {
            label = new UsrLabelValueBean(umodel);
            ret.add(label);
        }

        return ret;
    }
}
