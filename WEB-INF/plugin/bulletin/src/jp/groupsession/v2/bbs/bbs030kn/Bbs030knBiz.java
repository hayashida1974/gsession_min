package jp.groupsession.v2.bbs.bbs030kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs020.Bbs020Biz;
import jp.groupsession.v2.bbs.bbs030.Bbs030Biz;
import jp.groupsession.v2.bbs.dao.BbsForAdminDao;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsForMemDao;
import jp.groupsession.v2.bbs.dao.BbsForSumDao;
import jp.groupsession.v2.bbs.dao.BbsThreInfDao;
import jp.groupsession.v2.bbs.dao.BbsThreViewDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsForAdminModel;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsForMemModel;
import jp.groupsession.v2.bbs.model.BbsForSumModel;
import jp.groupsession.v2.bbs.model.BbsThreViewModel;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 掲示板 フォーラム登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs030knBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs030knBiz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setInitData(Bbs030knParamModel paramMdl, Connection con)
            throws Exception {
        log__.debug("START");

        //コメントを設定
        paramMdl.setBbs030viewcomment(NullDefault.getString(
                StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getBbs030comment()), ""));

        //スレッドテンプレートを設定
        int templateType = paramMdl.getBbs030templateType();
        if (templateType == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            String viewValue = StringUtilHtml.transToHTmlPlusAmparsantAndLink(
                    NullDefault.getString(paramMdl.getBbs030template(), ""));
            paramMdl.setBbs030viewTemplate(viewValue);
        }

        //フォーラム編集メンバリストを設定する
        paramMdl.setBbs030knMemNameList(__getForumFullLabel(paramMdl, con));

        //フォーラム閲覧メンバリストを設定する
        paramMdl.setBbs030knMemNameListRead(__getForumReadLabel(paramMdl, con));

        //フォーラム管理者メンバリストを設定する
        paramMdl.setBbs030knMemNameListAdm(__getForumAdmLabel(paramMdl, con));

        //親フォーラム名を設定する
        if (paramMdl.getBbs030ParentForumSid() != GSConstBulletin.BBS_DEFAULT_PFORUM_SID) {
            //親フォーラムが未設定でない場合
            __setParentForumInfo(paramMdl, con);
        }

        log__.debug("End");
    }

    /**
     * <br>[機  能] フォーラム情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param appRoot アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @throws Exception 実行例外
     */
    public void insertForumData(
            Bbs030knParamModel paramMdl,
            Connection con,
            MlCountMtController cntCon,
            int userSid,
            String appRoot,
            String tempDir)
                    throws Exception {

        log__.debug("START");
        UDate now = new UDate();

        //フォーラムSID採番
        int forumSid = (int) cntCon.getSaibanNumber(
                        GSConstBulletin.SBNSID_BULLETIN,
                        GSConstBulletin.SBNSID_SUB_BULLETIN_FORUM,
                        userSid);

        //メンバーの親フォーラム準拠区分を設定
        int followParentMemFlg = paramMdl.getBbs030FollowParentMemFlg();
        if (paramMdl.getBbs030ParentForumSid() == GSConstBulletin.BBS_DEFAULT_PFORUM_SID) {
            //第1階層に設定する場合、親フォーラムのメンバーに準拠しない。
            followParentMemFlg = GSConstBulletin.FOLLOW_PARENT_MEMBER_NO;
        }
        paramMdl.setBbs030FollowParentMemFlg(followParentMemFlg);

        //登録用のモデルを設定する
        BbsForInfModel regBfiMdl = new BbsForInfModel();
        __setRegistrationModel(con, forumSid, paramMdl, regBfiMdl, GSConstBulletin.BBSCMDMODE_ADD);

        //アイコン情報を設定する
        Long binSid =
                __registerIconData(con, paramMdl, tempDir, appRoot, cntCon, userSid, now);

        //アイコン情報をモデルに設定する
        regBfiMdl.setBinSid(binSid);
        regBfiMdl.setBfiAuid(userSid);
        regBfiMdl.setBfiAdate(now);
        regBfiMdl.setBfiEuid(userSid);
        regBfiMdl.setBfiEdate(now);

        BbsForInfDao bbsInfDao = new BbsForInfDao(con);
        bbsInfDao.insert(regBfiMdl);

        //フォーラム集計情報の登録
        BbsForSumModel bbsSumMdl = new BbsForSumModel();
        bbsSumMdl.setBfiSid(forumSid);
        bbsSumMdl.setBfsThreCnt(0);
        bbsSumMdl.setBfsWrtCnt(0);
        bbsSumMdl.setBfsWrtDate(null);
        bbsSumMdl.setBfsAuid(userSid);
        bbsSumMdl.setBfsAdate(now);
        bbsSumMdl.setBfsEuid(userSid);
        bbsSumMdl.setBfsEdate(now);

        //登録処理
        BbsForSumDao bbsSumDao = new BbsForSumDao(con);
        bbsSumDao.insert(bbsSumMdl);

        //メンバーの登録
        if (followParentMemFlg == GSConstBulletin.FOLLOW_PARENT_MEMBER_YES) {
            //親フォーラムのメンバーに準拠する(第1階層への設定時を除く)
            __registerMember(con, forumSid, null, null,
                    paramMdl.getBbs030UserListAdmReg(), false);
        } else {
            //個別にメンバーを設定する
            __registerMember(con, forumSid,
                    paramMdl.getBbs030UserListWriteReg(),
                    paramMdl.getBbs030UserListReadReg(),
                    paramMdl.getBbs030UserListAdmReg(), false);
        }
    }

    /**
     * <br>[機  能] フォーラム情報の更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @param appRoot アプリケーションルート
     * @param tempDir テンポラリディレクトリ
     * @param cntCon MlCountMtController
     * @throws Exception 実行例外
     */
    public void updateForumData(
            Bbs030knParamModel paramMdl, Connection con, MlCountMtController cntCon,
            int userSid, String appRoot, String tempDir)
                    throws Exception {

        log__.debug("START");
        UDate now = new UDate();
        int forumSid = paramMdl.getBbs020forumSid();

        //現在のフォーラム情報の取得
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        BbsForInfModel bfiMdl = new BbsForInfModel();
        bfiMdl.setBfiSid(forumSid);
        bfiMdl = bfiDao.select(bfiMdl);

        //登録前の親フォーラムSID
        int beforeParentSid = bfiMdl.getBfiParentSid();
        //登録前の階層レベル
        int beforeLevel = bfiMdl.getBfiLevel();

        //メンバーの親フォーラム準拠区分を設定
        int followParentMemFlg = paramMdl.getBbs030FollowParentMemFlg();
        if (paramMdl.getBbs030ParentForumSid() == GSConstBulletin.BBS_DEFAULT_PFORUM_SID) {
            //第1階層に設定する場合、親フォーラムのメンバーに準拠しない。
            followParentMemFlg = GSConstBulletin.FOLLOW_PARENT_MEMBER_NO;
        }
        paramMdl.setBbs030FollowParentMemFlg(followParentMemFlg);

        //登録用のモデルを設定する
        BbsForInfModel regBfiMdl = bfiMdl;
        __setRegistrationModel(con, forumSid, paramMdl, regBfiMdl, GSConstBulletin.BBSCMDMODE_EDIT);

        //登録後の親フォーラムSID
        int afterParentSid = paramMdl.getBbs030ParentForumSid();
        //登録後の階層レベル
        int afterLevel = regBfiMdl.getBfiLevel();

        //アイコン情報を更新する
        BulletinDao bbsDao = new BulletinDao(con);
        bbsDao.deleteBinfForum(forumSid);

        //アイコン情報を設定する
        Long binSid =
                __registerIconData(con, paramMdl, tempDir, appRoot, cntCon, userSid, now);

        //アイコン情報をモデルに設定する
        regBfiMdl.setBinSid(binSid);
        regBfiMdl.setBfiEuid(userSid);
        regBfiMdl.setBfiEdate(now);

        //登録処理
        bfiDao.updateBBSInf(regBfiMdl);

        //スレッド閲覧状況の更新
        if (Integer.parseInt(paramMdl.getBbs030read()) == GSConstBulletin.BBS_THRE_VIEW_YES) {
            __updateThreadView(paramMdl, con, userSid);
        }

        //メンバーの登録
        if (followParentMemFlg == GSConstBulletin.FOLLOW_PARENT_MEMBER_YES) {
            //親フォーラムのメンバーに準拠する(第1階層への設定時を除く)
            __registerMember(con, forumSid, null, null,
                    paramMdl.getBbs030UserListAdmReg(), true);
        } else {
            //個別にメンバーを設定する
            __registerMember(con, forumSid,
                    paramMdl.getBbs030UserListWriteReg(),
                    paramMdl.getBbs030UserListReadReg(),
                    paramMdl.getBbs030UserListAdmReg(), true);
        }

        //子フォーラムに適用するフラグ
        boolean adaptFlg = false;
        if (followParentMemFlg == GSConstBulletin.FOLLOW_PARENT_MEMBER_NO
                && paramMdl.getBbs030AdaptChildMemFlg() == 1) {
            //個別にメンバーを設定、かつ子フォーラムにも適用の場合
            adaptFlg = true;
        }

        //子階層のフォーラムのメンバーを更新する
        List<String> thisMemList = new ArrayList<String>();
        int memberForumSid = 0;
        if (followParentMemFlg == GSConstBulletin.FOLLOW_PARENT_MEMBER_YES) {
            //メンバー設定を持つ上位フォーラムのメンバーをセット
            BbsBiz bbsBiz = new BbsBiz();
            memberForumSid = bbsBiz.getBfiSidForMemberInfo(con, forumSid);
            BbsForMemDao bbsMemDao = new BbsForMemDao(con);
            String[] actualMemSid = bbsMemDao.getForumMemberId(memberForumSid);
            thisMemList.addAll(Arrays.asList(actualMemSid));

        } else {
            memberForumSid = forumSid;
            thisMemList.addAll(Arrays.asList(paramMdl.getBbs030UserListWriteReg()));
            thisMemList.addAll(Arrays.asList(paramMdl.getBbs030UserListReadReg()));
        }

        __updateChildForumMember(con, memberForumSid, adaptFlg, thisMemList);

        if (beforeParentSid != afterParentSid) {
            //親フォーラムの変更がある場合、階層、ソート番号を設定する

            //子階層以下のフォーラムの階層レベルを更新する
            //移動先の階層レベルと移動元の階層レベルの差を算出
            int diffLevel = afterLevel - beforeLevel;
            __updateChildForumLevel(con, forumSid, diffLevel);

            //移動元の表示順の連番を修正する
            Bbs020Biz bbs020Biz = new Bbs020Biz();
            bbs020Biz.fixForumSort(con, paramMdl.getBbs030ParentForumSid());
        }
    }

    /**
     * <br>[機  能] 子階層のフォーラムのメンバーを更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param parentForumSid 親となるフォーラムのSID
     * @param parentMemList 親フォーラムのメンバー
     * @param adaptFlg 子フォーラムに適用するフラグ
     * @throws SQLException SQL実行時例外
     */
    private void __updateChildForumMember(
            Connection con,
            int parentForumSid,
            boolean adaptFlg,
            List<String> parentMemList)
                    throws SQLException {
        //直下のフォーラムを取得
        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> bfiMdlList =
                bbsBiz.getSortedChildForum(con, parentForumSid, true, -1);

        if (bfiMdlList.size() < 1) {
            return;
        }

        //子フォーラムがある場合
        for (BbsForInfModel bbsInfMdl : bfiMdlList) {
            //フォーラムSID
            int childForumSid = bbsInfMdl.getBfiSid();
            //メンバーリスト
            List<String> childMemList = new ArrayList<String>();

            if (adaptFlg) {
                //「下位フォーラムにも適用」時、親フォーラム準拠フラグを設定する
                bbsInfMdl.setBfiFollowParentMem(GSConstBulletin.FOLLOW_PARENT_MEMBER_YES);
                BbsForInfDao bfiDao = new BbsForInfDao(con);
                bfiDao.updateBBSInf(bbsInfMdl);
            }

            if (adaptFlg
                    || bbsInfMdl.getBfiFollowParentMem()
                    == GSConstBulletin.FOLLOW_PARENT_MEMBER_YES) {
                //親フォーラムに準拠する ＝フォーラム管理者のみチェックし直し
                childMemList  = parentMemList;

                //フォーラム管理者をセット
                List<String> memAdmList = __getMemAdmList(con, childForumSid, parentMemList);
                String[] memberSidAdm =
                        (String[]) memAdmList.toArray(new String[memAdmList.size()]);

                //子フォーラムにメンバーを登録し直す
                __registerMember(con, childForumSid, null, null, memberSidAdm, true);

            } else {
                //個別に設定する
                //子フォーラムのメンバーを親フォーラムのメンバーからのみ選択する
                List<String> memWriteList = new ArrayList<String>();
                List<String> memReadList = new ArrayList<String>();
                __getParentLimitedMember(
                        con, childForumSid, parentMemList, memWriteList, memReadList);

                //編集メンバーと閲覧メンバーを子フォーラムのメンバーとして合わせる
                childMemList.addAll(memWriteList);
                childMemList.addAll(memReadList);

                //編集メンバーをセット
                String[] memberSidWrite =
                        (String[]) memWriteList.toArray(new String[memWriteList.size()]);

                //閲覧メンバーをセット
                String[] memberSidRead =
                        (String[]) memReadList.toArray(new String[memReadList.size()]);

                //フォーラム管理者をセット
                List<String> memAdmList = __getMemAdmList(con, childForumSid, childMemList);
                String[] memberSidAdm =
                        (String[]) memAdmList.toArray(new String[memAdmList.size()]);

                //子フォーラムにメンバーを登録し直す
                __registerMember(con, childForumSid,
                        memberSidWrite, memberSidRead, memberSidAdm, true);
            }

            //更に下位のフォーラムSIDを取得する
            __updateChildForumMember(con, childForumSid, adaptFlg, childMemList);
        }
    }

    /**
     * <br>[機  能] フォーラムに設定するフォーラム管理者メンバーを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param forumSid フォーラムSID
     * @param allMemList 全メンバーリスト
     * @throws SQLException SQL実行時例外
     * @return フォーラムに設定するフォーラム管理者メンバー
     */
    private List<String> __getMemAdmList(
            Connection con, int forumSid, List<String> allMemList)
                    throws SQLException {
        List<String> ret = new ArrayList<String>();

        //対象フォーラムの現在のフォーラム管理者を取得
        BbsForAdminDao bfaDao = new BbsForAdminDao(con);
        List<BbsForAdminModel> crntMemAdmList = bfaDao.getUsrData(forumSid);

        //メンバーから、メンバーユーザとメンバーグループ所属ユーザを抽出
        List<String> memUserList = new ArrayList<String>();
        List<String> memGroupList = new ArrayList<String>();
        for (String memSid : allMemList) {
            if (memSid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)) {
                memGroupList.add(memSid.substring(1));
            } else {
                memUserList.add(memSid);
            }
        }
        CmnBelongmDao cbDao = new CmnBelongmDao(con);
        String[] pMemGroup = (String[]) memGroupList.toArray(new String[memGroupList.size()]);
        List<String> pMemGroupsUserList = cbDao.select(pMemGroup);
        for (String grpUsrSid : pMemGroupsUserList) {
            memUserList.add(grpUsrSid);
        }

        //現在のフォーラム管理者から、設定後のメンバーに含まれるユーザのみ抽出
        for (BbsForAdminModel mdl : crntMemAdmList) {
            String sid = String.valueOf(mdl.getUsrSid());
            if (memUserList.contains(sid)) {
                ret.add(sid);
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] 親フォーラムのメンバー内に制限したメンバーを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param forumSid フォーラムSID
     * @param prntAllMemList 親フォーラムの全メンバーリスト
     * @param memWriteList メンバー 編集
     * @param memReadList メンバー 閲覧
     * @throws SQLException SQL実行時例外
     */
    private void __getParentLimitedMember(
            Connection con, int forumSid, List<String> prntAllMemList,
            List<String> memWriteList, List<String> memReadList)
                    throws SQLException {

        //親のメンバーグループとメンバーユーザを取得
        List<String> prntMemGrpList = new ArrayList<String>();
        List<String> prntMemUsrList = new ArrayList<String>();
        for (String memSid : prntAllMemList) {
            if (memSid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)) {
                String memSidWithoutHeadstr = memSid.substring(1);
                if (!prntMemGrpList.contains(memSidWithoutHeadstr)) {
                    prntMemGrpList.add(memSidWithoutHeadstr);
                }
            } else {
                if (!prntMemUsrList.contains(memSid)) {
                    prntMemUsrList.add(memSid);
                }
            }
        }


        //対象フォーラムの現在のメンバーリストを取得
        BbsBiz bbsBiz = new BbsBiz();
        forumSid = bbsBiz.getBfiSidForMemberInfo(con, forumSid);
        BbsForMemDao bfmDao = new BbsForMemDao(con);
        List<BbsForMemModel> crntMemList = new ArrayList<>();
        crntMemList = bfmDao.select(forumSid);

        for (BbsForMemModel bfmMdl : crntMemList) {
            String groupSid = String.valueOf(bfmMdl.getGrpSid());
            String userSid = String.valueOf(bfmMdl.getUsrSid());

            if (bfmMdl.getGrpSid() == -1
                    && prntMemUsrList.contains(userSid)
                    && bfmMdl.getBfmAuth() == GSConstBulletin.ACCESS_KBN_WRITE) {
                //親のメンバーユーザに一致するユーザ 編集メンバー
                memWriteList.add(userSid);

            } else if (bfmMdl.getUsrSid() == -1
                    && prntMemGrpList.contains(groupSid)
                    && bfmMdl.getBfmAuth() == GSConstBulletin.ACCESS_KBN_WRITE) {
                //親のメンバーグループに一致するグループ 編集メンバー
                memWriteList.add(
                        GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR + groupSid);

            } else if (bfmMdl.getGrpSid() == -1
                    && prntMemUsrList.contains(userSid)
                    && bfmMdl.getBfmAuth() == GSConstBulletin.ACCESS_KBN_READ) {
                //親のメンバーユーザに一致するユーザ 閲覧メンバー
                memReadList.add(userSid);

            } else if (bfmMdl.getUsrSid() == -1
                    && prntMemGrpList.contains(groupSid)
                    && bfmMdl.getBfmAuth() == GSConstBulletin.ACCESS_KBN_READ) {
                //親のメンバーグループに一致するグループ 閲覧メンバー
                memReadList.add(
                        GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR + groupSid);
            }
        }

    }

    /**
     * <br>[機  能] DB登録用のデータを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param paramMdl パラメータ情報
     * @param bbsInfMdl 登録用モデル
     * @param mode 処理モード 0:登録 1:編集
     * @throws SQLException SQL実行時例外
     */
    private void __setRegistrationModel(
            Connection con,
            int bfiSid,
            Bbs030knParamModel paramMdl,
            BbsForInfModel bbsInfMdl,
            int mode)
                    throws SQLException {

        bbsInfMdl.setBfiSid(bfiSid);
        bbsInfMdl.setBfiName(paramMdl.getBbs030forumName());
        bbsInfMdl.setBfiCmt(paramMdl.getBbs030comment());

        //フォーラム階層を設定する
        Bbs030Biz bbs030Biz = new Bbs030Biz();
        int parentForumSid = paramMdl.getBbs030ParentForumSid();
        bbsInfMdl.setBfiParentSid(parentForumSid);

        //フォーラム階層レベルを設定する
        int forumLevel;
        if (parentForumSid == GSConstBulletin.BBS_DEFAULT_PFORUM_SID) {
            forumLevel = GSConstBulletin.BBS_FORUM_MIN_LEVEL;
        } else {
            forumLevel = bbs030Biz.getForumLevel(con, parentForumSid);
            ++forumLevel;
        }
        bbsInfMdl.setBfiLevel(forumLevel);

        //メンバーの親フォーラム準拠区分を設定
        bbsInfMdl.setBfiFollowParentMem(paramMdl.getBbs030FollowParentMemFlg());

        //表示順を設定
        int sort = bbsInfMdl.getBfiSort();
        if (mode == GSConstBulletin.BBSCMDMODE_ADD
                || __isChangedParent(con, bfiSid, parentForumSid)) {
            //追加時または親フォーラムに変化がある場合、表示順を最後にする
            Bbs020Biz bbs020Biz = new Bbs020Biz();
            sort = bbs020Biz.getMaxBfiSort(con, parentForumSid) + 1;
        }
        bbsInfMdl.setBfiSort(sort);

        bbsInfMdl.setBfiReply(
                NullDefault.getInt(paramMdl.getBbs030reply(), GSConstBulletin.BBS_THRE_REPLY_NO));
        bbsInfMdl.setBfiRead(
                NullDefault.getInt(paramMdl.getBbs030read(),
                                    GSConstBulletin.NEWUSER_THRE_VIEW_YES));
        bbsInfMdl.setBfiMread(
                NullDefault.getInt(paramMdl.getBbs030mread(), GSConstBulletin.BBS_FORUM_MREAD_NO));
        int templateKbn = paramMdl.getBbs030templateKbn();
        bbsInfMdl.setBfiTemplateKbn(templateKbn);
        int templateType = paramMdl.getBbs030templateType();
        bbsInfMdl.setBfiTemplateType(templateType);

        if (templateKbn == GSConstBulletin.BBS_THRE_TEMPLATE_YES
                && templateType == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            bbsInfMdl.setBfiTemplate(paramMdl.getBbs030template());
            bbsInfMdl.setBfiTemplateWrite(paramMdl.getBbs030templateWriteKbn());
        } else if (templateKbn == GSConstBulletin.BBS_THRE_TEMPLATE_YES
                && templateType == GSConstBulletin.CONTENT_TYPE_TEXT_HTML) {
            bbsInfMdl.setBfiTemplate(paramMdl.getBbs030templateHtml());
            bbsInfMdl.setBfiTemplateWrite(paramMdl.getBbs030templateWriteKbn());
        } else {
            bbsInfMdl.setBfiTemplate("");
            bbsInfMdl.setBfiTemplateWrite(GSConstBulletin.BBS_THRE_TEMPLATE_WRITE_NO);
        }

        bbsInfMdl.setBfiWarnDisk(GSConstBulletin.BFI_WARN_DISK_NO);
        bbsInfMdl.setBfiWarnDiskTh(0);
        if (paramMdl.getBbs030diskSize() == GSConstBulletin.BFI_DISK_LIMITED) {
            bbsInfMdl.setBfiDisk(GSConstBulletin.BFI_DISK_LIMITED);
            bbsInfMdl.setBfiDiskSize(Integer.parseInt(paramMdl.getBbs030diskSizeLimit()));

            if (paramMdl.getBbs030warnDisk() == GSConstBulletin.BFI_WARN_DISK_YES) {
                bbsInfMdl.setBfiWarnDisk(GSConstBulletin.BFI_WARN_DISK_YES);
                bbsInfMdl.setBfiWarnDiskTh(paramMdl.getBbs030warnDiskThreshold());
            }

        } else {
            bbsInfMdl.setBfiDisk(GSConstBulletin.BFI_DISK_NOLIMIT);
            bbsInfMdl.setBfiDiskSize(0);
        }

        //掲示期間有無初期値
        if (paramMdl.getBbs030LimitDisable() == GSConstBulletin.THREAD_DISABLE) {
            bbsInfMdl.setBfiLimitOn(GSConstBulletin.THREAD_DISABLE);
            bbsInfMdl.setBfiLimit(GSConstBulletin.THREAD_LIMIT_NO);
            bbsInfMdl.setBfiUnlimited(GSConstBulletin.BFI_UNLIMITED_YES);
            bbsInfMdl.setBfiLimitDate(0);
            bbsInfMdl.setBfiKeep(GSConstBulletin.THREAD_KEEP_NO);
            bbsInfMdl.setBfiKeepDateY(-1);
            bbsInfMdl.setBfiKeepDateM(-1);
        } else {
            bbsInfMdl.setBfiLimitOn(GSConstBulletin.THREAD_ENABLE);

            //無制限フラグ
            if (paramMdl.getBbs030UnlimitedFlg() == GSConstBulletin.BFI_UNLIMITED_NO) {
                bbsInfMdl.setBfiUnlimited(GSConstBulletin.BFI_UNLIMITED_NO);
            } else {
                bbsInfMdl.setBfiUnlimited(GSConstBulletin.BFI_UNLIMITED_YES);
            }

            //掲示期間初期値
            if (paramMdl.getBbs030Limit() == GSConstBulletin.THREAD_LIMIT_YES) {
                bbsInfMdl.setBfiLimit(GSConstBulletin.THREAD_LIMIT_YES);
                bbsInfMdl.setBfiLimitDate(Integer.parseInt(paramMdl.getBbs030LimitDate()));
            } else {
                bbsInfMdl.setBfiLimit(GSConstBulletin.THREAD_LIMIT_NO);
                bbsInfMdl.setBfiLimitDate(0);
            }

            //時間単位
            bbsInfMdl.setBfiMinDiv(paramMdl.getBbs030TimeUnit());

            //スレッド保存期間
            if (paramMdl.getBbs030Keep() == GSConstBulletin.THREAD_KEEP_YES) {
                bbsInfMdl.setBfiKeep(GSConstBulletin.THREAD_KEEP_YES);
                bbsInfMdl.setBfiKeepDateY(paramMdl.getBbs030KeepDateY());
                bbsInfMdl.setBfiKeepDateM(paramMdl.getBbs030KeepDateM());
            } else {
                bbsInfMdl.setBfiKeep(GSConstBulletin.THREAD_KEEP_NO);
                bbsInfMdl.setBfiKeepDateY(-1);
                bbsInfMdl.setBfiKeepDateM(-1);
            }
        }
    }

    /**
     * <br>[機  能] アイコン情報を登録します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param tempDir テンポラリディレクトリ
     * @param appRoot アプリケーションルート
     * @param cntCon MlCountMtController
     * @param userSid セッションユーザSID
     * @param now 時刻
     * @return バイナリSID
     * @throws TempFileException 一時ファイル例外
     * @throws SQLException SQL実行時例外
     */
    private Long __registerIconData(
            Connection con,
            Bbs030knParamModel paramMdl,
            String tempDir,
            String appRoot,
            MlCountMtController cntCon,
            int userSid,
            UDate now)
                    throws TempFileException, SQLException {

        Long binSid = Long.valueOf(0);
        //テンポラリディレクトリの画像ファイルで画像名を設定

        Bbs030Biz bbs030biz = new Bbs030Biz();
        Cmn110FileModel fMdl= bbs030biz.getFileInfo(tempDir);
        if (fMdl != null) {
            paramMdl.setBbs030ImageName(fMdl.getFileName());
            paramMdl.setBbs030ImageSaveName(fMdl.getSaveFileName());
            CommonBiz cmnBiz = new CommonBiz();
            String filePath = tempDir
                    + fMdl.getSaveFileName();

            binSid = cmnBiz.insertBinInfo(
                    con, appRoot, cntCon, userSid, now, filePath, fMdl.getFileName());
        }


        return binSid;
    }

    /**
     * <br>[機  能] 子階層以下のフォーラムの階層レベルを更新します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param forumSid フォーラムのSID
     * @param diffLevel 階層レベルの差
     * @throws SQLException SQL実行時例外
     */
    private void __updateChildForumLevel(
            Connection con,
            int forumSid,
            int diffLevel)
                    throws SQLException {
        Bbs030Biz bbs030Biz = new Bbs030Biz();
        BbsForInfDao bfiDao = new BbsForInfDao(con);

        //すべての下位フォーラムを取得
        List<BbsForInfModel> childList = new ArrayList<BbsForInfModel>();
        bbs030Biz.getChildForum(con, childList, forumSid);

        //子階層以下のフォーラムの階層レベルを更新します
        int childLevel = 0;
        for (BbsForInfModel mdl : childList) {
            childLevel = mdl.getBfiLevel();
            childLevel += diffLevel;
            mdl.setBfiLevel(childLevel);
            bfiDao.updateBbsLevel(mdl);
        }
    }

    /**
     * <br>[機  能] 親フォーラムが変更されるか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param parentForumSid 設定したい親フォーラムSID
     * @return true:変更あり false:変更なし
     * @exception SQLException SQL実行時例外
     */
    private boolean __isChangedParent(
            Connection con,
            int bfiSid,
            int parentForumSid)
                    throws SQLException {
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        BbsForInfModel bbsInfMdl = new BbsForInfModel();

        //現在設定されている親フォーラムSIDをDBから取得する
        int dbParentSid = -1;
        bbsInfMdl.setBfiSid(bfiSid);
        bbsInfMdl = bfiDao.select(bbsInfMdl);
        dbParentSid = bbsInfMdl.getBfiParentSid();

        if (dbParentSid != parentForumSid) {
            return true;
        }

        return false;
    }

    /**
     * <br>[機  能] スレッド閲覧情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param userSid セッションユーザSID
     * @throws Exception 実行例外
     */
    private void __updateThreadView(Bbs030knParamModel paramMdl,
                                Connection con,
                                int userSid)
    throws Exception {

        String[] addMember = (String[]) ArrayUtils.addAll(
                paramMdl.getBbs030UserListWriteReg(), paramMdl.getBbs030UserListReadReg());
        if (addMember == null || addMember.length < 1) {
            return;
        }

        int bfiSid = paramMdl.getBbs020forumSid();

        BbsBiz bbsBiz = new BbsBiz();
        int memberForumSid = bbsBiz.getBfiSidForMemberInfo(con, bfiSid);
        BbsForMemDao bbsMemDao = new BbsForMemDao(con);
        String[] oldMemberSid = bbsMemDao.getForumMemberId(memberForumSid);

        CommonBiz cmnBiz = new CommonBiz();
        addMember = cmnBiz.getDeleteMember(oldMemberSid, addMember);
        if (addMember == null || addMember.length < 1) {
            return;
        }
        List<Integer> userSidList = __getMemberUserSidList(con, addMember);

        String[] delMemberSid = cmnBiz.getDeleteMember(addMember, oldMemberSid);
        List<Integer> delUserSidList = __getMemberUserSidList(con, delMemberSid);

        //スレッドSIDリストを取得
        BbsThreInfDao ftiDao = new BbsThreInfDao(con);
        String[] threSidList = ftiDao.getThreList(bfiSid);
        if (threSidList == null || threSidList.length < 1) {
            return;
        }

        UDate now = new UDate();
        BbsThreViewDao threViewDao = new BbsThreViewDao(con);
        BbsThreViewModel threViewMdl = new BbsThreViewModel();
        threViewMdl.setBfiSid(bfiSid);
        threViewMdl.setBivViewKbn(GSConstBulletin.NEWUSER_THRE_VIEW_YES);
        threViewMdl.setBivAuid(userSid);
        threViewMdl.setBivAdate(now);
        threViewMdl.setBivEuid(userSid);
        threViewMdl.setBivEdate(now);

        //スレッド閲覧状況の更新
        for (Integer memUserSid : userSidList) {
            if (memUserSid.intValue() <= GSConstUser.USER_RESERV_SID
            || delUserSidList.indexOf(memUserSid) >= 0) {
                continue;
            }

            threViewMdl.setUsrSid(memUserSid.intValue());
            for (String btiSid : threSidList) {
                threViewMdl.setBtiSid(Integer.parseInt(btiSid));
                if (threViewDao.getThreViewCount(threViewMdl.getBtiSid(), threViewMdl.getUsrSid())
                == 0) {
                    threViewDao.insert(threViewMdl);
                }
            }
        }
        userSidList.clear();
    }

    /**
     * <br>[機  能] フォーラム編集メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getForumFullLabel(
            Bbs030knParamModel paramMdl,
            Connection con)
                    throws SQLException {

        //取得するユーザSID・グループSID
        String[] leftFull = paramMdl.getBbs030UserListWriteReg();
        return __getForumLavle(leftFull, con);
    }

    /**
     * <br>[機  能] フォーラム閲覧メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getForumReadLabel(
            Bbs030knParamModel paramMdl,
            Connection con)
                    throws SQLException {

        //取得するユーザSID・グループSID
        String[] leftFull = paramMdl.getBbs030UserListReadReg();
        return __getForumLavle(leftFull, con);
    }

    /**
     * <br>[機  能] フォーラム管理者メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getForumAdmLabel(
            Bbs030knParamModel paramMdl,
            Connection con)
                    throws SQLException {

        //取得するユーザSID・グループSID
        String[] leftFull = paramMdl.getBbs030UserListAdmReg();
        return __getForumLavle(leftFull, con);
    }

    /**
     * <br>[機  能] フォーラムメンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param left 取得するユーザSID・グループSID
     * @param con コネクション
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getForumLavle(String[] left, Connection con)
    throws SQLException {

        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();

        //
        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (left != null) {
            for (int i = 0; i < left.length; i++) {
                String str = NullDefault.getString(left[i], "-1");
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

        UsrLabelValueBean lavelBean = null;
        if (grpSids.size() > 0) {
            //グループ情報取得
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
            ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
            for (GroupModel gmodel : glist) {
                lavelBean = new UsrLabelValueBean();
                lavelBean.setLabel(gmodel.getGroupName());
                lavelBean.setValue("G" + String.valueOf(gmodel.getGroupSid()));
                ret.add(lavelBean);
            }
        }

        //ユーザ情報取得
        UserBiz userBiz = new UserBiz();
        List<CmnUsrmInfModel> ulist
                = userBiz.getUserList(con,
                                        (String[]) usrSids.toArray(new String[usrSids.size()]));
        for (CmnUsrmInfModel umodel : ulist) {
            lavelBean = new UsrLabelValueBean();
            lavelBean.setLabel(umodel.getUsiSei() + " " + umodel.getUsiMei());
            lavelBean.setValue(String.valueOf(umodel.getUsrSid()));
            lavelBean.setUsrUkoFlg(umodel.getUsrUkoFlg());
            ret.add(lavelBean);
        }

        return ret;
    }

    /**
     * <br>[機  能] フォーラムメンバーのユーザSID一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param memberSid フォーラムメンバーSID
     * @return ユーザSID一覧
     * @throws SQLException SQL実行時例外
     */
    private List<Integer> __getMemberUserSidList(Connection con, String[] memberSid)
    throws SQLException {

        CmnBelongmDao belongDao = new CmnBelongmDao(con);
        List<Integer> userSidList = new ArrayList<Integer>();
        for (String memSid : memberSid) {
            if (StringUtil.isNullZeroString(memSid)) {
                continue;
            }

            if (memSid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)) {
                //グループ
                userSidList.addAll(belongDao.selectBelongUserSid(
                                            Integer.parseInt(memSid.substring(1))));
            } else {
                //ユーザ
                userSidList.add(Integer.valueOf(memSid));
            }
        }

        return userSidList;
    }

    /**
     * <br>[機  能] 親フォーラム情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param con コネクション
     * @exception SQLException SQL実行時例外
     */
    private void __setParentForumInfo(
            Bbs030knParamModel paramMdl,
            Connection con)
                    throws SQLException {
        String forumName = null;
        Long forumBinSid = null;

        //フォーラムSIDからモデルを用意
        BbsForInfModel bbsInfMdl = new BbsForInfModel();
        bbsInfMdl.setBfiSid(paramMdl.getBbs030ParentForumSid());

        //フォーラムモデルを取得
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        BbsForInfModel resultModel = bfiDao.select(bbsInfMdl);
        if (resultModel != null) {
            forumName = resultModel.getBfiName();
            forumBinSid = resultModel.getBinSid();
        }

        paramMdl.setBbs030knForumName(forumName);
        paramMdl.setBbs030knParentBinSid(forumBinSid);
    }

    /**
     * <br>[機  能] メンバーの登録処理を行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bfiSid フォーラムSID
     * @param memberSidWrite メンバーSID 編集
     * @param memberSidRead メンバーSID 閲覧
     * @param memberSidAdm メンバーSID 管理者
     * @param updateFlg 更新フラグ
     * @exception SQLException SQL実行時例外
     */
    private void __registerMember(
            Connection con,
            int bfiSid,
            String[] memberSidWrite,
            String[] memberSidRead,
            String[] memberSidAdm,
            boolean updateFlg)
                    throws SQLException {

        BbsForMemDao bbsMemDao = new BbsForMemDao(con);
        BbsForAdminDao forAdmDao = new BbsForAdminDao(con);

        if (updateFlg) {
            //既存のメンバー情報を削除する
            bbsMemDao.deleteForumMem(bfiSid);
            forAdmDao.delete(bfiSid);
        }

        //フォーラム編集メンバーの登録
        bbsMemDao.insert(bfiSid, memberSidWrite, GSConstBulletin.ACCESS_KBN_WRITE);

        //フォーラム閲覧メンバーの登録
        bbsMemDao.insert(bfiSid, memberSidRead, GSConstBulletin.ACCESS_KBN_READ);

        //フォーラム管理者メンバーの登録
        forAdmDao.insert(bfiSid, memberSidAdm);
    }
}
