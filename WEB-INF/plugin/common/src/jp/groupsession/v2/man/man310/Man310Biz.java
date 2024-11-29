package jp.groupsession.v2.man.man310;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoBinDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoMsgDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoTagDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnInfoMsgModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoTagModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;

/**
 * <br>[機  能] メイン インフォメーション詳細画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man310Biz {

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Man310ParamModel paramMdl, Connection con)
    throws SQLException {

        int imsSid = NullDefault.getInt(paramMdl.getMan310SelectedSid(), -1);
        CmnInfoMsgDao infoDao = new CmnInfoMsgDao(con);
        CmnInfoMsgModel infoMdl = infoDao.select(imsSid);
        if (infoMdl == null) {
            return;
        }

        //メッセージ・内容文字生成
        paramMdl.setMan310Msg(infoMdl.getImsMsg());
        paramMdl.setMan310Value(StringUtilHtml.transToHTmlPlusAmparsantAndLink(
                NullDefault.getString(infoMdl.getImsValue(), "")));

        CmnUsrmInfDao uiDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel uiMdl = uiDao.selectUserNameAndJtkbn(infoMdl.getImsEuid());
        if (uiMdl != null) {
            paramMdl.setMan310NameSei(uiMdl.getUsiSei());
            paramMdl.setMan310NameMei(uiMdl.getUsiMei());
            paramMdl.setMan310UsrJkbn(uiMdl.getUsrJkbn());
        } else {
            paramMdl.setMan310NameSei("");
            paramMdl.setMan310NameMei("");
            paramMdl.setMan310UsrJkbn(0);
        }


        //公開対象生成
        CmnInfoTagDao tagDao = new CmnInfoTagDao(con);
        ArrayList<CmnInfoTagModel> tagMdlList = null;
        ArrayList<String> list = new ArrayList<String>();
        tagMdlList = tagDao.select(imsSid);

        for (int i = 0; i < tagMdlList.size(); i++) {
            if (tagMdlList.get(i).getUsrSid() != -1) {
                list.add(String.valueOf(tagMdlList.get(i).getUsrSid()));
            }

            if (tagMdlList.get(i).getGrpSid() != -1) {
                list.add(String.valueOf("G"
                                        + tagMdlList.get(i).getGrpSid()));
            }

        }
        String[] usrGrpSids = (String[]) list.toArray(new String[list.size()]);
        paramMdl.setMan310KoukaiList(__getKoukaiLabel(usrGrpSids, con));
        //添付ファイル一覧
        CmnInfoBinDao binDao = new CmnInfoBinDao(con);
        paramMdl.setTmpFileList(binDao.getBinList(imsSid));
    }

    /**
     * <br>[機  能] 公開対象一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param left 取得するユーザSID・グループSID
     * @param con コネクション
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<LabelValueBean> __getKoukaiLabel(String[] left, Connection con)
    throws SQLException {

        ArrayList<LabelValueBean> ret = new ArrayList<LabelValueBean>();

        //
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (left != null) {
            for (int i = 0; i < left.length; i++) {
                String str = NullDefault.getString(left[i], "-1");
                if (str.contains(new String("G").subSequence(0, 1))) {
                    //グループ
                    grpSids.add(Integer.parseInt(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }
        //グループ情報取得
        UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
        ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
        LabelValueBean labelBean = null;
        for (GroupModel gmodel : glist) {
            labelBean = new LabelValueBean();
            labelBean.setLabel(gmodel.getGroupName());
            labelBean.setValue("G" + String.valueOf(gmodel.getGroupSid()));
            ret.add(labelBean);
        }

        //ユーザ情報取得
        UserBiz userBiz = new UserBiz();
        ArrayList<BaseUserModel> ulist
                = userBiz.getBaseUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        for (BaseUserModel umodel : ulist) {
            labelBean = new LabelValueBean();
            labelBean.setLabel(umodel.getUsisei() + " " + umodel.getUsimei());
            labelBean.setValue(String.valueOf(umodel.getUsrsid()));
            ret.add(labelBean);
        }

        return ret;
    }

    /**
     * <br>[機  能] 指定したインフォメーション情報の添付ファイルが存在するかを判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param imsSid インフォメーションSID
     * @param binSid バイナリ―SID
     * @return true: 存在する false: 存在しない
     * @throws SQLException SQL実行時例外
     */
    public boolean existInfoBinData(Connection con, int imsSid, long binSid) throws SQLException {
        boolean result = false;

        CmnInfoMsgDao infoDao = new CmnInfoMsgDao(con);
        CmnInfoMsgModel infoMdl = infoDao.select(imsSid);
        if (infoMdl != null) {
            CmnInfoBinDao binDao = new CmnInfoBinDao(con);
            result = binDao.select(imsSid, binSid) != null;
        }

        return result;
    }
}
