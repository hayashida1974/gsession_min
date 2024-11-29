package jp.groupsession.v2.sml.sml180;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] ショートメール 管理者設定 メール転送一括設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml180Biz {

    /** 対象 全ユーザ */
    public static final int TAISYO_ALL = 0;
    /** 対象 ユーザ指定 */
    public static final int TAISYO_SELECT = 1;
    /** リクエスト情報 */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Sml180Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(RequestModel reqMdl, Sml180ParamModel paramMdl, Connection con)
        throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);

        /** グループコンボセット **************************************************/
        GroupBiz biz = new GroupBiz();
        paramMdl.setSml180GpLabelList(biz.getGroupCombLabelList(con, true, gsMsg));

        /** 現在選択中のメンバーコンボセット **************************************/
        UserBiz userBiz = new UserBiz();
        paramMdl.setSml180MbLabelList(
                (ArrayList<UsrLabelValueBean>) userBiz.getUserLabelList(
                        con, paramMdl.getSml180userSid()));

        /** 追加用メンバーコンボセット ********************************************/
        //グループSID
        int gpSid = NullDefault.getInt(paramMdl.getSml180groupSid(), -1);

        //除外するユーザSID
        ArrayList<Integer> usrSids = new ArrayList<Integer>();
        String[] userSids = paramMdl.getSml180userSid();
        if (userSids != null) {
            for (int i = 0; i < userSids.length; i++) {
                usrSids.add(new Integer(NullDefault.getInt(userSids[i], -1)));
            }
        }

        List<CmnUsrmInfModel> usList
            = userBiz.getBelongUserList(con, gpSid, usrSids);

        ArrayList<UsrLabelValueBean> labelListAdd = new ArrayList<UsrLabelValueBean>();
        for (int i = 0; i < usList.size(); i++) {
            CmnUsrmInfModel cuiMdl = usList.get(i);
            labelListAdd.add(new UsrLabelValueBean(cuiMdl));
        }
        paramMdl.setSml180AdLabelList(labelListAdd);


        //メールアドレスコンボ設定
        paramMdl.setSml180MailList(__getMailCombo());

    }

    /**
     * <br>[機  能] メールアドレスコンボを生成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @return List (in LabelValueBean)  メールアドレスコンボ
     * @throws SQLException SQL実行時例外
     */
    private List<LabelValueBean> __getMailCombo() throws SQLException {

        List <LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        GsMessage gsMsg = new GsMessage(reqMdl__);
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
     * <br>[機  能] コンボで選択中のメンバーをメンバーリストから削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void deleteMnb(Sml180ParamModel paramMdl) {

        //コンボで選択中
        String[] selectUserSid = paramMdl.getSml180selectUserSid();
        //メンバーリスト(コンボ表示に使用する値)
        String[] userSid = paramMdl.getSml180userSid();

        CommonBiz biz = new CommonBiz();
        paramMdl.setSml180userSid(biz.getDeleteMember(selectUserSid, userSid));
    }

    /**
     * <br>[機  能] 追加用メンバーコンボで選択中のメンバーをメンバーリストに追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void addMnb(Sml180ParamModel paramMdl) {

        //追加用メンバー(選択中)
        String[] addUserSid = paramMdl.getSml180addUserSid();
        //メンバーリスト(コンボ表示に使用する値)
        String[] userSid = paramMdl.getSml180userSid();

        CommonBiz biz = new CommonBiz();
        paramMdl.setSml180userSid(biz.getAddMember(addUserSid, userSid));
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
    public void setCanUsePluginFlg(Sml180ParamModel paramMdl, Connection con, PluginConfig pconfig)
    throws SQLException {

        CommonBiz cmnBiz = new CommonBiz();
        //在席管理は利用可能か判定
        if (cmnBiz.isCanUsePlugin(GSConstSmail.PLUGIN_ID_ZAISEKI, pconfig)) {
            paramMdl.setSml180ZaisekiPlugin(GSConstSmail.PLUGIN_USE);
        } else {
            paramMdl.setSml180ZaisekiPlugin(GSConstSmail.PLUGIN_NOT_USE);
        }
    }
}