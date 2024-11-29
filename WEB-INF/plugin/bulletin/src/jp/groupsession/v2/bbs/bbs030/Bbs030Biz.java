package jp.groupsession.v2.bbs.bbs030;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.dao.BbsForAdminDao;
import jp.groupsession.v2.bbs.dao.BbsForInfDao;
import jp.groupsession.v2.bbs.dao.BbsForMemDao;
import jp.groupsession.v2.bbs.dao.BulletinDao;
import jp.groupsession.v2.bbs.model.BbsAdmConfModel;
import jp.groupsession.v2.bbs.model.BbsForAdminModel;
import jp.groupsession.v2.bbs.model.BbsForInfModel;
import jp.groupsession.v2.bbs.model.BbsForMemModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 掲示板 フォーラム登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs030Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs030Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、フォーラム情報を設定する
     * <br>[備  考]
     * @param cmd CMDパラメータ
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param tempDir テンポラリディレクトリパス
     * @param appRoot アプリケーションルートパス
     * @param sessionUsrSid セッションユーザSID
     * @throws SQLException 実行例外
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws IOException バイナリファイル操作時例外
     * @throws TempFileException 添付ファイルUtil内での例外
     *
     */
    public void setInitData(
            String cmd, RequestModel reqMdl, Bbs030ParamModel paramMdl,
            Connection con, String tempDir, String appRoot, int sessionUsrSid)
                    throws SQLException, IOToolsException, TempFileException, IOException {
        log__.debug("START");

        if (cmd.equals("addForum")
                && paramMdl.getBbs030cmdMode() == GSConstBulletin.BBSCMDMODE_ADD) {
            //登録での初期表示時（フォーラム一覧からの遷移）
            paramMdl.setBbs020forumSid(GSConstBulletin.BBS_DEFAULT_PFORUM_SID);

        } else if (cmd.equals("editForum")
                && paramMdl.getBbs030cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            //編集での初期表示時（フォーラム一覧からの遷移）、フォーラム情報を取得する
            __setEditForumData(con, paramMdl);
        }

        //メンバーを設定
        __setMember(con, reqMdl, paramMdl);

        CommonBiz cmnBiz = new CommonBiz();

        //バイナリSIDが取得できていたら画像を取得
        if (paramMdl.getBbs030BinSid() > 0) {
            CmnBinfModel binMdl
                = cmnBiz.getBinInfo(con, paramMdl.getBbs030BinSid(), reqMdl.getDomain());
            if (binMdl != null) {

                //テンポラリディレクトリにバイナリデータから作成したファイルを保存する
                String imageSaveName = cmnBiz.saveSingleTempFile(binMdl, appRoot, tempDir);
                paramMdl.setBbs030ImageName(binMdl.getBinFileName());
                paramMdl.setBbs030ImageSaveName(imageSaveName);
            }
        }

        //テンポラリディレクトリの画像ファイルで画像名を設定
        Cmn110FileModel fMdl= getFileInfo(tempDir);
        if (fMdl != null) {
            paramMdl.setBbs030ImageName(fMdl.getFileName());
            paramMdl.setBbs030ImageSaveName(fMdl.getSaveFileName());

        }


        //ディスク容量警告 選択値を設定
        List<LabelValueBean> warnDiskThresholdList = new ArrayList<LabelValueBean>();
        for (int warnValue = 10; warnValue <= 90; warnValue += 10) {
            String strWarnValue = Integer.toString(warnValue);
            warnDiskThresholdList.add(new LabelValueBean(strWarnValue,
                                                                                    strWarnValue));
        }
        paramMdl.setWarnDiskThresholdList(warnDiskThresholdList);

        GsMessage gsMsg = new GsMessage(reqMdl);

        //スレッド保存期間 経過年 選択値を設定
        ArrayList<LabelValueBean> bbs030KeepDateYearLabel = new ArrayList<LabelValueBean>();
        for (String label : Bbs030Form.YEAR_VALUE) {
            bbs030KeepDateYearLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.year", new String[] {label}), label));
        }
        paramMdl.setBbs030KeepDateYLabel(bbs030KeepDateYearLabel);

        //スレッド保存期間 経過月 選択値を設定
        ArrayList<LabelValueBean> bbs030KeepDateMonthLabel = new ArrayList<LabelValueBean>();
        for (String label : Bbs030Form.MONTH_VALUE) {
            bbs030KeepDateMonthLabel.add(new LabelValueBean(
                    gsMsg.getMessage("cmn.months", new String[] {label}), label));
        }
        paramMdl.setBbs030KeepDateMLabel(bbs030KeepDateMonthLabel);


        //管理者設定 自動削除設定を取得する
        //DBより現在の設定を取得する。(なければデフォルト)
        BbsBiz biz = new BbsBiz();
        BbsAdmConfModel conf = biz.getBbsAdminData(con, sessionUsrSid);
        //スレッド保存期間 自動削除設定内容表示フラグ
        paramMdl.setBbs030DspAtdelFlg(conf.getBacAtdelFlg());
        //スレッド保存期間  表示用 自動削除設定内容 経過年
        paramMdl.setBbs030DspAtdelYear(conf.getBacAtdelY());
        //スレッド保存期間  表示用 自動削除設定内容 経過月
        paramMdl.setBbs030DspAtdelMonth(conf.getBacAtdelM());

        //選択不可の親フォーラムSIDを設定
        paramMdl.setBbs030DisabledForumSid(__checkDisabledForum(con, paramMdl, cmd));
    }

    /**
     * <br>[機  能] フォーラム編集時の初期表示情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    private void __setEditForumData(
            Connection con, Bbs030ParamModel paramMdl)
                    throws SQLException {

        BbsForMemDao bbsMemDao = new BbsForMemDao(con);

        int bfiSid = paramMdl.getBbs020forumSid();

        BbsBiz bbsBiz = new BbsBiz(con);
        BulletinDspModel btMdl = bbsBiz.getForumData(con, bfiSid);
        if (btMdl == null) {
            throw new SQLException("フォーラム情報の取得に失敗");
        }
        //フォーラム名
        paramMdl.setBbs030forumName(btMdl.getBfiName());
        //コメント
        paramMdl.setBbs030comment(btMdl.getBfiCmt());
        //投稿許可
        paramMdl.setBbs030reply(String.valueOf(btMdl.getBfiReply()));
        //新規ユーザスレッド閲覧状態
        paramMdl.setBbs030read(String.valueOf(btMdl.getBfiRead()));
        //全て既読にする許可
        paramMdl.setBbs030mread(String.valueOf(btMdl.getBfiMread()));
        //スレッドテンプレート区分
        paramMdl.setBbs030templateKbn(btMdl.getBfiTemplateKbn());
        //スレッドテンプレートタイプ
        int templateType = btMdl.getBfiTemplateType();
        paramMdl.setBbs030templateType(templateType);
        //スレッドテンプレート
        if (templateType == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            paramMdl.setBbs030template(btMdl.getBfiTemplate());
        } else {
            paramMdl.setBbs030templateHtml(btMdl.getBfiTemplate());
        }
        //スレッドテンプレート 投稿時使用区分
        paramMdl.setBbs030templateWriteKbn(btMdl.getBfiTemplateWrite());
        //掲示期間 有効初期値
        paramMdl.setBbs030LimitDisable(btMdl.getBfiLimitOn());
        //掲示期間 初期値
        paramMdl.setBbs030Limit(btMdl.getBfiLimit());
        //掲示期間 無制限
        paramMdl.setBbs030UnlimitedFlg(btMdl.getBfiUnlimited());
        //掲示期間日数 初期値
        paramMdl.setBbs030LimitDate(String.valueOf(btMdl.getBfiLimitDate()));
        //時間単位
        paramMdl.setBbs030TimeUnit(btMdl.getMinDiv());
        //スレッド保存期間設定
        paramMdl.setBbs030Keep(btMdl.getBfiKeep());
        //スレッド保存期間 経過年
        paramMdl.setBbs030KeepDateY(btMdl.getBfiKeepDateY());
        //スレッド保存期間 経過月
        paramMdl.setBbs030KeepDateM(btMdl.getBfiKeepDateM());

        //ディスク容量
        paramMdl.setBbs030diskSize(btMdl.getBfiDisk());
        if (btMdl.getBfiDisk() == GSConstBulletin.BFI_DISK_LIMITED) {
            //ディスク容量 最大値
            paramMdl.setBbs030diskSizeLimit(Integer.toString(btMdl.getBfiDiskSize()));
        }
        //ディスク容量警告 閾値
        paramMdl.setBbs030warnDisk(btMdl.getBfiWarnDisk());
        if (btMdl.getBfiWarnDisk() == GSConstBulletin.BFI_WARN_DISK_YES) {
            //ディスク容量警告 閾値
            paramMdl.setBbs030warnDiskThreshold(btMdl.getBfiWarnDiskTh());
        }

        //フォーラム階層設定 選択中の親フォーラムSID
        paramMdl.setBbs030ParentForumSid(btMdl.getParentForumSid());
        //フォーラム階層設定 フォーラム階層レベル
        paramMdl.setBbs030ForumLevel(btMdl.getForumLevel());

        //メンバーの親フォーラム準拠フラグ
        int followParentMemFlg = btMdl.getFollowParentMemFlg();
        paramMdl.setBbs030FollowParentMemFlg(followParentMemFlg);

        //フォーラムメンバーを設定
        List<BbsForMemModel> bfmMdlList = new ArrayList<BbsForMemModel>();
        ArrayList<String> editList = new ArrayList<String>();
        ArrayList<String> readList = new ArrayList<String>();
        //フォーラムSIDからメンバー情報を持つフォーラムのSIDを取得する。
        int memberForumSid = bbsBiz.getBfiSidForMemberInfo(con, bfiSid);
        //ユーザデータ一覧を取得する
        bfmMdlList = bbsMemDao.getUsrData(memberForumSid);

        for (BbsForMemModel mdl : bfmMdlList) {
            if (mdl.getBfmAuth() == GSConstBulletin.ACCESS_KBN_WRITE) {
                //追加・変更・削除メンバー
                if (mdl.getUsrSid() != -1) {
                    //ユーザ
                    editList.add(String.valueOf(mdl.getUsrSid()));
                }
                if (mdl.getGrpSid() != -1) {
                    //グループ
                    editList.add(String.valueOf(
                            GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR + mdl.getGrpSid()));
                }

            } else {
                //閲覧メンバー
                if (mdl.getUsrSid() != -1) {
                    //ユーザ
                    readList.add(String.valueOf(mdl.getUsrSid()));
                }
                if (mdl.getGrpSid() != -1) {
                    //グループ
                    readList.add(String.valueOf(
                            GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR + mdl.getGrpSid()));
                }
            }
        }

        //メンバーSID 追加・編集・削除
        paramMdl.setBbs030UserListWriteReg(
                (String[]) editList.toArray(new String[editList.size()]));
        //メンバーSID 閲覧
        paramMdl.setBbs030UserListReadReg(
                (String[]) readList.toArray(new String[readList.size()]));

        //フォーラム管理者を設定
        BbsForAdminDao forAdmDao = new BbsForAdminDao(con);
        List<BbsForAdminModel> forAdmMdlList = new ArrayList<BbsForAdminModel>();
        ArrayList<String> admList = new ArrayList<String>();
        forAdmMdlList = forAdmDao.getUsrData(bfiSid);
        for (int i = 0; i < forAdmMdlList.size(); i++) {
            admList.add(String.valueOf(forAdmMdlList.get(i).getUsrSid()));
        }
        String[] admUsrSids = (String[]) admList.toArray(new String[admList.size()]);
        paramMdl.setBbs030UserListAdmReg(admUsrSids);

        //画像バイナリSIDを取得
        paramMdl.setBbs030BinSid(btMdl.getBinSid());
    }

    /**
     * <br>[機  能] メンバーを設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @throws SQLException 実行例外
     */
    private void __setMember(
            Connection con, RequestModel reqMdl, Bbs030ParamModel paramMdl)
                    throws SQLException {

        int parentForumSid = paramMdl.getBbs030ParentForumSid();
        //親フォーラムのメンバーグループ
        List<String> parentGroupMember =
                __getForumMembers(con, parentForumSid,
                        GSConstBulletin.MODE_MEMBER_GROUP);
        //親フォーラムのメンバーユーザ
        List<String> parentUserMember =
                __getForumMembers(con, parentForumSid,
                        GSConstBulletin.MODE_MEMBER_USER);

        //ポップアップ用に選択禁止メンバーを設定
        __setDisableMember(con, paramMdl);

        //編集メンバー追加一覧（左）
        List<String> writeMemberList = new ArrayList<String>();
        List<UsrLabelValueBean> forumLabelList =
                __getForumWriteLabel(con, paramMdl,
                        writeMemberList, parentGroupMember, parentUserMember);
        paramMdl.setBbs030LeftUserList(forumLabelList);

        //閲覧メンバー追加一覧（左）
        List<String> readMemberList = new ArrayList<String>();
        List<UsrLabelValueBean> forumReadLabelList =
                __getForumReadLabel(con, paramMdl,
                        readMemberList, parentGroupMember, parentUserMember);
        paramMdl.setBbs030LeftUserListRead(forumReadLabelList);

        //メンバーのグループリスト
        List<String> memberGroupList = new ArrayList<String>();
        //メンバーのユーザリスト
        List<String> memberUserList = new ArrayList<String>();
        //メンバーグループリストとメンバーユーザリストをセット
        __setMemberGroupUserList(
                memberGroupList, memberUserList, writeMemberList, readMemberList);

        //フォーラムメンバー グループコンボを設定
        __setMemberGroupCombo(con, reqMdl, paramMdl);

        //フォーラムメンバーの選択コンボ（右）を設定
        __setMemberSelectCombo(con, paramMdl, memberGroupList, memberUserList);

        //フォーラムメンバーが「常に親フォーラムのメンバーに合わせる」の場合
        //親フォーラムのフォーラムメンバーをフォーラム管理者メンバーの基準とする
        if (paramMdl.getBbs030FollowParentMemFlg() == GSConstBulletin.FOLLOW_PARENT_MEMBER_YES
        && parentForumSid != GSConstBulletin.BBS_DEFAULT_PFORUM_SID) {
            List<String> parentGroupList = new ArrayList<String>();
            for (String parentGrpMem : parentGroupMember) {
                parentGroupList.add(parentGrpMem.substring(1));
            }
            memberGroupList = parentGroupList;
            memberUserList = parentUserMember;
        }

        //フォーラム管理者メンバー追加一覧（左）
        List<String> admMemberList = new ArrayList<String>();
        paramMdl.setBbs030LeftUserListAdm(
                __getForumAdmLabel(con, paramMdl, admMemberList, memberGroupList, memberUserList));

        //フォーラム管理者メンバーのグループコンボを設定
        __setAdmMemberGroupCombo(con, reqMdl, paramMdl, memberGroupList);

        //フォーラム管理者メンバーの選択コンボ（右）を設定
        __setAdmMemberSelectCombo(con, reqMdl,
                paramMdl, admMemberList, memberUserList);
    }

    /**
     * <br>[機  能] 選択禁止メンバーを設定します
     * <br>[解  説]
     * <br>[備  考] メンバー選択ポップアップ用
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    private void __setDisableMember(
            Connection con, Bbs030ParamModel paramMdl)
                    throws SQLException {
        int prntForumSid = paramMdl.getBbs030ParentForumSid();

        //選択禁止ユーザ
        List<Integer> banUserSidList = new ArrayList<Integer>();
        //選択禁止グループ
        List<Integer> banGroupSidList = new ArrayList<Integer>();
        //ユーザ絞り込み選択禁止グループ
        List<Integer> disableGroupSidList = new ArrayList<Integer>();

        //親フォーラムのメンバー
        List<String> memberUserList = __getForumMembers(
                con, prntForumSid, GSConstBulletin.MODE_MEMBER_USER);
        List<String> memberGroupList = __getForumMembers(
                con, prntForumSid, GSConstBulletin.MODE_MEMBER_GROUP);
        List<String> memberUsersGroupList = __getForumMembers(
                con, prntForumSid, GSConstBulletin.MODE_MEMBER_USERS_GROUP);

        //全ユーザのうち、親のメンバーユーザでないユーザを選択禁止ユーザに追加
        CmnUsrmDao cuDao = new CmnUsrmDao(con);
        List<CmnUsrmInfModel> usrAllList = cuDao.getUsrAll();
        for (CmnUsrmInfModel mdl : usrAllList) {
            String sid = String.valueOf(mdl.getUsrSid());
            if (!memberUserList.contains(sid)) {
                banUserSidList.add(mdl.getUsrSid());
            }
        }

        //全グループのうち、親のメンバーグループでないグループを選択禁止グループに追加
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        GroupDao dao = new GroupDao(con);
        List<GroupModel> allGroupList = dao.getGroupTree(sortMdl);
        for (GroupModel mdl : allGroupList) {
            String sid = GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR
                    + String.valueOf(mdl.getGroupSid());
            if (!memberGroupList.contains(sid)) {
                banGroupSidList.add(mdl.getGroupSid());
            }
        }

        //ユーザの所属グループでないグループを絞り込み禁止グループに追加
        for (GroupModel mdl : allGroupList) {
            String sid = GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR
                    + String.valueOf(mdl.getGroupSid());
            if (!memberUsersGroupList.contains(sid)) {
                disableGroupSidList.add(mdl.getGroupSid());
            }
        }

        //選択禁止ユーザを設定
        paramMdl.setBbs030BanUserSidList(banUserSidList);
        //選択禁止グループを設定
        paramMdl.setBbs030BanGroupSidList(banGroupSidList);
        //ユーザ絞り込み選択禁止グループを設定
        paramMdl.setBbs030DisableGroupSidList(disableGroupSidList);
    }

    /**
     * <br>[機  能] メンバーグループリストとメンバーユーザリストを作成します
     * <br>[解  説]
     * <br>[備  考]
     * @param memberGroupList メンバーのグループリスト
     * @param memberUserList メンバーのユーザリスト
     * @param writeMemberList 編集メンバー一覧
     * @param readMemberList 閲覧メンバー一覧
     */
    private void __setMemberGroupUserList(
            List<String> memberGroupList, List<String> memberUserList,
            List<String> writeMemberList, List<String> readMemberList) {

        //編集メンバー（ユーザSID・グループSID）
        for (String sid : writeMemberList) {
            if (sid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)) {
                memberGroupList.add(sid.substring(1));
            } else {
                memberUserList.add(sid);
            }
        }

        //閲覧メンバー（ユーザSID・グループSID）
        for (String sid : readMemberList) {
            if (sid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)) {
                memberGroupList.add(sid.substring(1));
            } else {
                memberUserList.add(sid);
            }
        }
    }

    /**
     * <br>[機  能] 選択不可のフォーラムを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param cmd CMDパラメータ
     * @return 選択不可フォーラムのSID
     * @throws SQLException SQL実行時例外
     */
    private int[] __checkDisabledForum(
            Connection con, Bbs030ParamModel paramMdl, String cmd)
                    throws SQLException {

        List<Integer> disabledForumSid = new ArrayList<Integer>();

        int forumSid = paramMdl.getBbs020forumSid();

        //階層が上限を超えるような親フォーラムは設定できない
        int disabledLevel;
        if (forumSid == GSConstBulletin.BBS_DEFAULT_PFORUM_SID) {
            //フォーラム未設定時、階層上限は最大階層の設定と同じ
            disabledLevel = GSConstBulletin.BBS_FORUM_MAX_LEVEL;

        } else {
            //自フォーラムは選択不可
            disabledForumSid.add(forumSid);

            //選択不可の階層レベル
            List<BbsForInfModel> childBfiList = new ArrayList<BbsForInfModel>();
            getChildForum(con, childBfiList, forumSid);
            int lowestLevel = paramMdl.getBbs030ForumLevel();
            if (childBfiList != null && childBfiList.size() > 0) {
                for (BbsForInfModel mdl : childBfiList) {
                    if (lowestLevel < mdl.getBfiLevel()) {
                        lowestLevel = mdl.getBfiLevel();
                    }
                }
            }
            disabledLevel =
                    GSConstBulletin.BBS_FORUM_MAX_LEVEL
                    + paramMdl.getBbs030ForumLevel()
                    - lowestLevel;
        }

        BbsForInfDao bfiDao = new BbsForInfDao(con);
        List<Integer> disabledLevelSidList = bfiDao.getLowerLevelForumSid(disabledLevel);
        disabledForumSid.addAll(disabledLevelSidList);

        //自フォーラムの下位階層にあるフォーラムは選択不可である
        if (forumSid > 0) {

            //子フォーラムのSIDリストを取得
            List<BbsForInfModel> childList = new ArrayList<BbsForInfModel>();
            getChildForum(con, childList, forumSid);
            if (childList.size() > 0) {
                paramMdl.setBbs030HaveChildForumFlg(GSConstBulletin.FORUM_CHILD_EXIST);
            }

            //子フォーラムのSIDを重複がないように選択不可リストに追加
            for (BbsForInfModel childMdl : childList) {
                int childSid = childMdl.getBfiSid();
                if (!disabledForumSid.contains(childSid)) {
                    disabledForumSid.add(childSid);
                }
            }
        }

        int[] ret = new int[disabledForumSid.size()];
        int count = 0;
        for (Integer sid : disabledForumSid) {
            ret[count] = sid;
            ++count;
        }
        return ret;
    }

    /**
     * <br>[機  能] フォーラム編集メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param writeMemberList 編集メンバー一覧
     * @param parentGroupMember 親フォーラムのメンバーグループ
     * @param parentUserMember 親フォーラムのメンバーユーザ
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getForumWriteLabel(
            Connection con, Bbs030ParamModel paramMdl,
            List<String> writeMemberList,
            List<String> parentGroupMember,
            List<String> parentUserMember)
                    throws SQLException {

        //編集メンバー（ユーザSID・グループSID）
        String[] memberSid = paramMdl.getBbs030UserListWriteReg();
        List<String> memberSidList = Arrays.asList(memberSid);

        //親フォーラムに含まれるメンバーのみ取得
        for (String sid: memberSidList) {
            if (parentGroupMember.contains(sid)
                    || parentUserMember.contains(sid)) {
                writeMemberList.add(sid);
            }
        }

        //登録用の編集メンバーを設定
        paramMdl.setBbs030UserListWriteReg(
                (String[]) writeMemberList.toArray(new String[writeMemberList.size()]));

        //編集メンバー一覧を作成
        return __getForumLabel(writeMemberList, con);
    }

    /**
     * <br>[機  能] フォーラム閲覧メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param readMemberList 閲覧メンバー一覧
     * @param parentGroupMember 親フォーラムのメンバーグループ
     * @param parentUserMember 親フォーラムのメンバーユーザ
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getForumReadLabel(
            Connection con, Bbs030ParamModel paramMdl,
            List<String> readMemberList,
            List<String> parentGroupMember,
            List<String> parentUserMember)
                    throws SQLException {

        //閲覧メンバー（ユーザSID・グループSID）
        String[] memberSidRead = paramMdl.getBbs030UserListReadReg();
        List<String> memberSidReadList = Arrays.asList(memberSidRead);

        //親フォーラムに含まれるメンバーのみ取得
        for (String sid: memberSidReadList) {
            if (parentGroupMember.contains(sid)
                    || parentUserMember.contains(sid)) {
                readMemberList.add(sid);
            }
        }

        //登録用の閲覧メンバーを設定
        paramMdl.setBbs030UserListReadReg(
                (String[]) readMemberList.toArray(new String[readMemberList.size()]));

        return __getForumLabel(readMemberList, con);
    }

    /**
     * <br>[機  能] フォーラムメンバーのグループコンボを設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @throws SQLException SQL実行時例外
     */
    private void __setMemberGroupCombo(
            Connection con, RequestModel reqMdl, Bbs030ParamModel paramMdl)
                    throws SQLException {

        GroupBiz groupBiz = new GroupBiz();
        List<GroupModel> allGroupList = groupBiz.getGroupList(con);

        List<String> parentMembersGroup =
                __getForumMembers(con, paramMdl.getBbs030ParentForumSid(),
                        GSConstBulletin.MODE_MEMBER_USERS_GROUP);

        //親フォーラムのメンバー以外を除く
        List<GroupModel> checkedGroupComboList = new ArrayList<GroupModel>();
        for (GroupModel mdl : allGroupList) {
            String sid = GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR
                    + String.valueOf(mdl.getGroupSid());
            if (parentMembersGroup.contains(sid)) {
                checkedGroupComboList.add(mdl);
            }
        }

        paramMdl.setBbs030GroupList(__getGroupLabelList(con, reqMdl, checkedGroupComboList, false));
    }

    /**
     * <br>[機  能] フォーラムメンバーの選択コンボ（右）を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータモデル
     * @param memberGroupList メンバーのグループリスト
     * @param memberUserList メンバーのユーザリスト
     * @throws SQLException SQL実行時例外
     */
    private void __setMemberSelectCombo(
            Connection con, Bbs030ParamModel paramMdl,
            List<String> memberGroupList,
            List<String> memberUserList)
                    throws SQLException {
        List<UsrLabelValueBean> labelListAdd = new ArrayList<UsrLabelValueBean>();

        if (paramMdl.getBbs030groupSid()
                == Integer.parseInt(GSConstBulletin.GROUP_COMBO_VALUE)) {
            //「グループ一覧」選択時

            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
            GroupDao dao = new GroupDao(con);
            List<GroupModel> groupModelList = dao.getGroupTree(sortMdl);

            if (paramMdl.getBbs030ParentForumSid()
                    != GSConstBulletin.BBS_DEFAULT_PFORUM_SID) {
                //親フォーラム設定時

                //親フォーラムのメンバーグループ
                List<String> parentGroupMember =
                        __getForumMembers(con, paramMdl.getBbs030ParentForumSid(),
                                GSConstBulletin.MODE_MEMBER_GROUP);

                //選択された編集メンバーを取得
                List<GroupModel> checkedMemList = new ArrayList<GroupModel>();
                for (GroupModel mdl : groupModelList) {
                    if (parentGroupMember.contains(
                            GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR
                            + String.valueOf(mdl.getGroupSid()))) {
                        checkedMemList.add(mdl);
                    }
                }
                groupModelList = checkedMemList;
            }

            //選択しているメンバーを除く
            List<GroupModel> rightGroupList = new ArrayList<GroupModel>();
            for (GroupModel mdl : groupModelList) {
                String groupSid = String.valueOf(mdl.getGroupSid());
                if (!memberGroupList.contains(groupSid)) {
                    rightGroupList.add(mdl);
                }
            }

            //グループ一覧を作成する
            for (GroupModel bean : rightGroupList) {
                labelListAdd.add(new UsrLabelValueBean(
                        bean.getGroupName(),
                        String.valueOf(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR
                                + bean.getGroupSid())));
            }

        } else {
            //グループ選択時（ユーザ一覧表示）

            ArrayList<Integer> excludeList = new ArrayList<Integer>();
            //メンバーに選択しているユーザを除外する
            for (String sid : memberUserList) {
                excludeList.add(NullDefault.getInt(sid, -1));
            }

            //グループに所属するユーザ情報一覧を取得
            UserBiz userBiz = new UserBiz();
            List<CmnUsrmInfModel> usList =
                    userBiz.getBelongUserList(con, paramMdl.getBbs030groupSid(), excludeList);

            //親フォーラムのメンバーグループの所属ユーザを取得
            List<String> parentMemberUser =
                    __getForumMembers(con, paramMdl.getBbs030ParentForumSid(),
                            GSConstBulletin.MODE_MEMBER_USER);

            List<CmnUsrmInfModel> rightUserList = new ArrayList<CmnUsrmInfModel>();
            for (CmnUsrmInfModel mdl : usList) {
                String sid = String.valueOf(mdl.getUsrSid());

                //親フォーラムに含まれないユーザを除外する
                if (parentMemberUser.contains(sid)) {
                    rightUserList.add(mdl);
                }
            }

            //ユーザ一覧表示を作成する
            for (CmnUsrmInfModel mdl : rightUserList) {
                UsrLabelValueBean usrLabel =  new UsrLabelValueBean(
                        mdl.getUsiSei() + mdl.getUsiMei(),
                                String.valueOf(mdl.getUsrSid()));
                usrLabel.setUsrUkoFlg(mdl.getUsrUkoFlg());
                labelListAdd.add(usrLabel);
            }
        }

        //追加用ユーザ一覧を設定する
        paramMdl.setBbs030RightUserList(labelListAdd);
    }

    /**
     * <br>[機  能] フォーラム管理者メンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param admMemberList 管理者メンバー一覧
     * @param memberGroupList メンバーのグループリスト
     * @param memberUserList メンバーのユーザリスト
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getForumAdmLabel(
            Connection con,
            Bbs030ParamModel paramMdl,
            List<String> admMemberList,
            List<String> memberGroupList,
            List<String> memberUserList)
                    throws SQLException {

        //管理者メンバー(ユーザ)SID
        String[] memberSidAdm = paramMdl.getBbs030UserListAdmReg();

        //メンバーのユーザではないユーザのリスト
        List<String> otherUserList = new ArrayList<String>();
        for (String sid : memberSidAdm) {
            if (memberUserList.contains(sid)) {
                //メンバーのユーザをフォーラム管理者メンバーに設定
                admMemberList.add(sid);

            } else if (!sid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)) {
                //メンバーのユーザではないユーザ
                otherUserList.add(sid);
            }
        }

        if (memberGroupList.size() > 0) {
            //メンバーのグループに所属するユーザをフォーラム管理者メンバーに設定する
            CmnBelongmDao cbDao = new CmnBelongmDao(con);
            String[] gsid = (String[]) memberGroupList.toArray(new String[memberGroupList.size()]);
            List<String> belongedUserList = cbDao.select(gsid);
            for (String sid : otherUserList) {
                if (belongedUserList.contains(sid)) {
                    admMemberList.add(sid);
                }
            }
        }

        //登録用の管理者メンバーを設定
        paramMdl.setBbs030UserListAdmReg(
                (String[]) admMemberList.toArray(new String[admMemberList.size()]));

        return __getForumLabel(admMemberList, con);
    }

    /**
     * <br>[機  能] フォーラム管理者メンバーのグループコンボを設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @param memberGroupList メンバーのグループリスト
     * @throws SQLException SQL実行時例外
     */
    private void __setAdmMemberGroupCombo(
            Connection con,
            RequestModel reqMdl,
            Bbs030ParamModel paramMdl,
            List<String> memberGroupList)
                    throws SQLException {

        //メンバーユーザの所属グループを取得
        List<GroupModel> userBelongToGroup = new ArrayList<GroupModel>();

        //グループを取得する
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        GroupDao dao = new GroupDao(con);
        List<GroupModel> groupModelList = dao.getGroupTree(sortMdl);
        for (GroupModel mdl : groupModelList) {
            String sid = String.valueOf(mdl.getGroupSid());
            if (memberGroupList.contains(sid)) {
                userBelongToGroup.add(mdl);
            }
        }

        //管理者グループコンボを設定する。
        boolean properFlg = false;
        for (GroupModel mdl : userBelongToGroup) {
            int groupSid = mdl.getGroupSid();
            if (groupSid == paramMdl.getBbs030groupSidAdm()) {
                properFlg = true;
            }
        }

        if (!properFlg) {
            //メンバーユーザの所属グループではないグループを選択時、「ユーザ指定」選択にする
            paramMdl.setBbs030groupSidAdm(
                    Integer.parseInt(GSConstBulletin.GROUP_COMBO_VALUE_USER));
        }

        paramMdl.setBbs030GroupListAdm(__getGroupLabelList(con, reqMdl, userBelongToGroup, true));
    }

    /**
     * <br>[機  能] フォーラム管理者メンバーの選択コンボを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータモデル
     * @param admMemberList 管理者ユーザリスト
     * @param memberUserList メンバーのユーザリスト
     * @throws SQLException SQL実行時例外
     */
    private void __setAdmMemberSelectCombo(
            Connection con,
            RequestModel reqMdl,
            Bbs030ParamModel paramMdl,
            List<String> admMemberList,
            List<String> memberUserList)
                    throws SQLException {
        UserBiz userBiz = new UserBiz();

        //管理者 追加用ユーザを取得する
        List<UsrLabelValueBean> labelListAddAdm = new ArrayList<UsrLabelValueBean>();

        if (paramMdl.getBbs030groupSidAdm()
                == Integer.parseInt(GSConstBulletin.GROUP_COMBO_VALUE_USER)) {
            //「ユーザ指定」選択時

            //メンバーユーザから管理者に選択しているユーザを除いてリストを作成
            List<Integer> userList = new ArrayList<Integer>();
            for (String sid : memberUserList) {
                if (!admMemberList.contains(sid)) {
                    userList.add(Integer.parseInt(sid));
                }
            }

            //コンボを作成
            List<CmnUsrmInfModel> usrList = userBiz.getUserList(con, userList);
            for (CmnUsrmInfModel model : usrList) {
                UsrLabelValueBean usrLabel = new UsrLabelValueBean(
                        model.getUsiSei() + model.getUsiMei(),
                        String.valueOf(model.getUsrSid()));
                usrLabel.setUsrUkoFlg(model.getUsrUkoFlg());
                labelListAddAdm.add(usrLabel);
            }

        } else {
            //グループ選択時

            ArrayList<Integer> excludeList = new ArrayList<Integer>();
            //管理者に選択しているユーザを除外
            for (String sid : admMemberList) {
                excludeList.add(NullDefault.getInt(sid, -1));
            }

            List<CmnUsrmInfModel> usList =
                    userBiz.getBelongUserList(con, paramMdl.getBbs030groupSidAdm(), excludeList);
            for (CmnUsrmInfModel cuiMdl : usList) {
                UsrLabelValueBean usrLabel = new UsrLabelValueBean(
                        cuiMdl.getUsiSei() + cuiMdl.getUsiMei(),
                        String.valueOf(cuiMdl.getUsrSid()));
                usrLabel.setUsrUkoFlg(cuiMdl.getUsrUkoFlg());
                labelListAddAdm.add(usrLabel);
            }
        }

        //追加用ユーザ(管理者)一覧を設定する
        paramMdl.setBbs030RightUserListAdm(labelListAddAdm);
    }

    /**
     * <br>[機  能] フォーラムメンバー一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param leftList 取得するユーザSID・グループSID
     * @param con コネクション
     * @return グループ一覧
     * @throws SQLException SQL実行時例外
     */
    private ArrayList<UsrLabelValueBean> __getForumLabel(
            List<String> leftList, Connection con)
                    throws SQLException {

        ArrayList<UsrLabelValueBean> ret = new ArrayList<UsrLabelValueBean>();

        ArrayList<Integer> grpSids = new ArrayList<Integer>();
        ArrayList<String> usrSids = new ArrayList<String>();

        //ユーザSIDとグループSIDを分離
        if (leftList != null) {
            for (String sid : leftList) {
                String str = NullDefault.getString(sid, "-1");
                if (str.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)) {
                    //グループ
                    grpSids.add(Integer.valueOf(str.substring(1, str.length())));
                } else {
                    //ユーザ
                    usrSids.add(str);
                }
            }
        }

        UsrLabelValueBean labelBean = null;
        if (grpSids.size() > 0) {
            //グループ情報取得
            UsidSelectGrpNameDao gdao = new UsidSelectGrpNameDao(con);
            ArrayList<GroupModel> glist = gdao.selectGroupNmListOrderbyConf(grpSids);
            for (GroupModel gmodel : glist) {
                labelBean = new UsrLabelValueBean();
                labelBean.setLabel(gmodel.getGroupName());
                labelBean.setValue(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR
                        + String.valueOf(gmodel.getGroupSid()));
                ret.add(labelBean);
            }

        }
        //ユーザ情報取得
        CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
        CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con);
        List <CmnUsrmInfModel> ulist =
                usiDao.getUsersInfList(
                        (String[]) usrSids.toArray(new String[usrSids.size()]), sortMdl);

        for (CmnUsrmInfModel umodel : ulist) {
            labelBean = new UsrLabelValueBean();
            labelBean.setLabel(umodel.getUsiSei() + " " + umodel.getUsiMei());
            labelBean.setValue(String.valueOf(umodel.getUsrSid()));
            labelBean.setUsrUkoFlg(umodel.getUsrUkoFlg());
            ret.add(labelBean);
        }
        return ret;
    }

    /**
     * 表示グループ用のグループリストを取得する
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param groupList グループリスト
     * @param admComboFlg フォーラム管理者コンボか true:フォーラム管理者のコンボ false:メンバーコンボ
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    private ArrayList <LabelValueBean> __getGroupLabelList(
            Connection con,  RequestModel reqMdl, List<GroupModel> groupList, boolean admComboFlg)
                    throws SQLException {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();
        LabelValueBean lavelBean = new LabelValueBean();

        GsMessage gsMsg = new GsMessage(reqMdl);
        if (admComboFlg) {
            String textUserSitei = gsMsg.getMessage("cmn.user.specified");
            lavelBean.setLabel(textUserSitei);
            lavelBean.setValue(GSConstBulletin.GROUP_COMBO_VALUE_USER);
        } else {
            String textGroupList = gsMsg.getMessage("cmn.grouplist");
            lavelBean.setLabel(textGroupList);
            lavelBean.setValue(GSConstBulletin.GROUP_COMBO_VALUE);
        }
        labelList.add(lavelBean);

        for (GroupModel gpMdl : groupList) {
            labelList.add(new LabelValueBean(
                    gpMdl.getGroupName(), String.valueOf(gpMdl.getGroupSid())));
        }
        log__.debug("labelList.size()=>" + labelList.size());
        return labelList;
    }

    /**
     * <br>[機  能] フォーラムSIDからフォーラムの階層レベルを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param forumSid フォーラムSID
     * @throws SQLException SQL実行時例外
     * @return 選択中のフォーラムの階層レベル
     */
    public int getForumLevel(
            Connection con, int forumSid)
                    throws SQLException {
        BbsForInfDao bfiDao = new BbsForInfDao(con);
        int ret = GSConstBulletin.BBS_DEFAULT_PFORUM_SID;

        //フォーラム情報を取得
        BbsForInfModel bbsInfMdl = new BbsForInfModel();
        bbsInfMdl.setBfiSid(forumSid);
        BbsForInfModel targetModel =  bfiDao.select(bbsInfMdl);

        if (targetModel != null) {
            ret = targetModel.getBfiLevel();
        }

        return ret;
    }

    /**
     * <br>[機  能] すべての下位フォーラムを取得します(管理者設定画面用)
     * <br>[解  説]
     * <br>[備  考] 管理者設定画面で利用する
     * @param con コネクション
     * @param childList 下位フォーラムのリスト
     * @param forumSid 起点のフォーラムSID
     * @throws SQLException SQL実行時例外
     */
    public void getChildForum(
            Connection con, List<BbsForInfModel> childList, int forumSid)
                    throws SQLException {
        getChildForum(con, childList, forumSid, true, -1);
    }

    /**
     * <br>[機  能] すべての下位フォーラムを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param childList 下位フォーラムのリスト
     * @param forumSid 起点のフォーラムSID
     * @param adminDisp 管理者設定画面用の表示か否か true:管理者設定用画面 false:通常画面
     * @param userSid ユーザSID
     * @throws SQLException SQL実行時例外
     */
    public void getChildForum(
            Connection con, List<BbsForInfModel> childList, int forumSid,
            boolean adminDisp, int userSid)
                    throws SQLException {
        //直下のフォーラムを取得
        BbsBiz bbsBiz = new BbsBiz();
        List<BbsForInfModel> bfiMdlList =
                bbsBiz.getSortedChildForum(con, forumSid, adminDisp, userSid);

        if (bfiMdlList.size() < 1) {
            return;
        }

        //フォーラムがある場合
        for (BbsForInfModel bbsInfMdl : bfiMdlList) {
            childList.add(bbsInfMdl);

            //更に下位のフォーラムSIDを取得する
            getChildForum(con, childList, bbsInfMdl.getBfiSid(), adminDisp, userSid);
        }
    }

    /**
     * <br>[機  能] モードに合わせてフォーラムのメンバーを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param forumSid フォーラムSID
     * @param mode 0:グループ 1:ユーザ 2:ユーザの所属グループ 3:ユーザとグループに所属するユーザ
     * @return メンバーSIDリスト
     * @throws SQLException SQL実行時例外
     */
    private List<String> __getForumMembers(
            Connection con, int forumSid, int mode)
                    throws SQLException {
        List<String> ret = new ArrayList<String>();

        //メンバーSIDリスト
        List<String> memberGroupList = new ArrayList<String>();
        List<String> memberUserList = new ArrayList<String>();

        //フォーラムが未設定の場合、モードに合わせて全ユーザまたは全グループを返します
        if (forumSid == GSConstBulletin.BBS_DEFAULT_PFORUM_SID
                && (mode == GSConstBulletin.MODE_MEMBER_USER
                || mode == GSConstBulletin.MODE_MEMBER_RELATIVE_USER)) {
            //全ユーザを返します
            CmnUsrmDao cuDao = new CmnUsrmDao(con);
            List<CmnUsrmInfModel> usrAllList = cuDao.getUsrAll();
            for (CmnUsrmInfModel mdl : usrAllList) {
                String userSid = String.valueOf(mdl.getUsrSid());
                memberUserList.add(userSid);
            }
            return memberUserList;

        } else if (forumSid == GSConstBulletin.BBS_DEFAULT_PFORUM_SID
                && (mode == GSConstBulletin.MODE_MEMBER_GROUP
                || mode == GSConstBulletin.MODE_MEMBER_USERS_GROUP)) {
            //全グループを返します
            CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con);
            CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
            GroupDao dao = new GroupDao(con);
            List<GroupModel> allGroupList = dao.getGroupTree(sortMdl);
            for (GroupModel mdl : allGroupList) {
                String groupSid = GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR
                        + String.valueOf(mdl.getGroupSid());
                memberGroupList.add(groupSid);
            }
            return memberGroupList;
        }

        //グループSID(ヘッダーの"G"抜き)リスト
        List<String> memberGroupSidList = new ArrayList<String>();

        //フォーラムのメンバーを取得
        BbsBiz bbsBiz = new BbsBiz();
        forumSid = bbsBiz.getBfiSidForMemberInfo(con, forumSid);
        BbsForMemDao bbsMemDao = new BbsForMemDao(con);
        String[] memberSid = bbsMemDao.getForumMemberId(forumSid);
        for (String sid : memberSid) {
            if (sid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)) {
                memberGroupSidList.add(sid.substring(1));
                memberGroupList.add(sid);
            } else {
                memberUserList.add(sid);
            }
        }

        if (mode == GSConstBulletin.MODE_MEMBER_GROUP) {
            //グループ
            ret = memberGroupList;
        } else if (mode == GSConstBulletin.MODE_MEMBER_USER) {
            //ユーザ
            ret = memberUserList;
        }

        if (mode == GSConstBulletin.MODE_MEMBER_RELATIVE_USER) {
            //ユーザ＋グループに所属するユーザ

            //編集メンバー、閲覧メンバーのグループに所属するユーザを取得
            CmnBelongmDao cbDao = new CmnBelongmDao(con);
            String[] gsid =
                    (String[]) memberGroupSidList.toArray(new String[memberGroupSidList.size()]);
            List<String> belongedUserList = cbDao.select(gsid);

            for (String sid : belongedUserList) {
                if (!memberUserList.contains(sid)) {
                    memberUserList.add(sid);
                }
            }

            ret = memberUserList;
        }

        if (mode == GSConstBulletin.MODE_MEMBER_USERS_GROUP) {
            //ユーザの所属グループ
            List<String> memberUsersGroupList = new ArrayList<String>();

            //ユーザーが所属するグループのSIDリストを取得
            CmnBelongmDao cbDao = new CmnBelongmDao(con);
            List<Integer> userBelongList =
                    cbDao.selectUserBelongGroupSid(memberUserList);

            for (Integer groupSid : userBelongList) {
                String strGroupSid = GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR
                        + String.valueOf(groupSid);
                if (!memberUsersGroupList.contains(strGroupSid)) {
                    memberUsersGroupList.add(strGroupSid);
                }
            }

            ret = memberUsersGroupList;
        }

        return ret;
    }


    /**
     * <br>[機  能] グループとユーザのSIDリストからグループ名とユーザ名のリストを作成します
     * <br>[解  説]
     * <br>[備  考] グループSIDは頭に
     * @param con コネクション
     * @param memberList グループとユーザのSIDリスト
     * @return グループ名とユーザ名のリスト
     * @throws SQLException SQL実行時例外
     */
    public List<String> getGroupUserNameList(
            Connection con, List<String> memberList)
                    throws SQLException {

        List<Integer> groupSidList = new ArrayList<Integer>();
        List<String> userSidList = new ArrayList<String>();
        for (String sid : memberList) {
            if (sid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)) {
                //グループSID
                int groupSid = Integer.parseInt(sid.substring(1));
                groupSidList.add(groupSid);

            } else {
                //ユーザSID
                userSidList.add(sid);
            }
        }

        List<String> memberNameList = new ArrayList<String>();

        if (groupSidList.size() > 0) {
            //グループ情報取得
            int[] groupSidArray = new int[groupSidList.size()];
            int count = 0;
            for (int sid : groupSidList) {
                groupSidArray[count] = sid;
                ++count;
            }
            GroupDao groupDao = new GroupDao(con);
            List<CmnGroupmModel> groupMdlList = groupDao.getGroups(groupSidArray);
            for (CmnGroupmModel mdl : groupMdlList) {
                memberNameList.add(mdl.getGrpName());
            }
        }

        if (userSidList.size() > 0) {
            //ユーザ情報取得
            CmnUsrmDao usrmDao = new CmnUsrmDao(con);
            String[] userSidArray = (String[]) userSidList.toArray(new String[userSidList.size()]);
            List<BaseUserModel> userMdlList = usrmDao.getSelectedUserList(userSidArray);
            for (BaseUserModel mdl : userMdlList) {
                memberNameList.add(mdl.getUsisei() + mdl.getUsimei());
            }
        }

        return memberNameList;
    }

    /**
     * <br>[機  能] 親フォーラムのメンバーに一致する、または一致しないメンバーを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param prntForumSid 親フォーラムSID
     * @param memSid チェック対象のメンバーSID
     * @param properFlg true:一致するメンバー false:一致しないメンバー
     * @return メンバーSIDリスト
     * @throws SQLException SQL実行時例外
     */
    public List<String> checkParentMember(
            Connection con, int prntForumSid, String[] memSid, boolean properFlg)
                    throws SQLException {
        //親フォーラムなしの場合、メンバーの一致をチェックしない
        if (prntForumSid == GSConstBulletin.BBS_DEFAULT_PFORUM_SID
                || memSid == null
                || memSid.length < 1) {
            if (properFlg) {
                //そのまま返す
                return Arrays.asList(memSid);
            } else {
                //空のリストを返す
                return new ArrayList<String>();
            }
        }

        //一致したメンバーリスト
        List<String> matchList = new ArrayList<String>();
        //一致しないメンバーリスト
        List<String> mismatchList = new ArrayList<String>();

        //親フォーラムのメンバーを取得
        BbsBiz bbsBiz = new BbsBiz();
        prntForumSid = bbsBiz.getBfiSidForMemberInfo(con, prntForumSid);
        BbsForMemDao bbsMemDao = new BbsForMemDao(con);
        String[] prntMemSid = bbsMemDao.getForumMemberId(prntForumSid);

        //チェック対象のメンバーSIDリスト
        List<String> prntMemSidList = Arrays.asList(prntMemSid);

        for (String sid : memSid) {
            if (prntMemSidList.contains(sid)) {
                matchList.add(sid);
            } else {
                mismatchList.add(sid);
            }
        }

        if (!properFlg) {
            return mismatchList;
        }
        return matchList;
    }

    /**
     * <br>[機  能] 管理者メンバーに適切なユーザ、または不適切なユーザを返します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param bbs030UserListAdmReg 管理者ユーザ一覧
     * @param bbs030UserListWriteReg 編集ユーザ一覧
     * @param bbs030UserListReadReg 閲覧ユーザ一覧
     * @param properFlg true:一致するメンバー false:一致しないメンバー
     * @return メンバーSIDリスト
     * @throws SQLException SQL実行時例外
     */
    public List<String> checkMemberAdm(
            Connection con,
            String[] bbs030UserListAdmReg,
            String[] bbs030UserListWriteReg,
            String[] bbs030UserListReadReg,
            boolean properFlg)
                    throws SQLException {

        //ユーザなしの場合、空のリストを返す
        if (bbs030UserListAdmReg == null || bbs030UserListAdmReg.length < 1) {
            return new ArrayList<String>();
        }

        //一致するメンバーリスト
        List<String> matchList = new ArrayList<String>();
        //一致しないメンバーリスト
        List<String> mismatchList = new ArrayList<String>();

        //チェック対象のメンバーSIDリスト
        List<String> memberSidList = Arrays.asList(bbs030UserListAdmReg);

        //グループSID(ヘッダーの"G"抜き)リスト
        List<String> memberGroupSidList = new ArrayList<String>();
        //グループSIDリスト
        List<String> memberGroupList = new ArrayList<String>();
        //ユーザSIDリスト
        List<String> memberUserList = new ArrayList<String>();
        //編集メンバーをグループとユーザに振り分ける
        if (bbs030UserListWriteReg != null) {
            for (String sid : bbs030UserListWriteReg) {
                if (sid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)
                        && !memberGroupList.contains(sid)) {
                    memberGroupSidList.add(sid.substring(1));
                    memberGroupList.add(sid);
                } else if (!sid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)
                        && !memberUserList.contains(sid)) {
                    memberUserList.add(sid);
                }
            }
        }
        //閲覧メンバーをグループとユーザに振り分ける
        if (bbs030UserListReadReg != null) {
            for (String sid : bbs030UserListReadReg) {
                if (sid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)
                        && !memberGroupList.contains(sid)) {
                    memberGroupSidList.add(sid.substring(1));
                    memberGroupList.add(sid);
                } else if (!sid.startsWith(GSConstBulletin.FORUM_MEMBER_GROUP_HEADSTR)
                        && !memberUserList.contains(sid)) {
                    memberUserList.add(sid);
                }
            }
        }

        //編集メンバー、閲覧メンバーのグループに所属するユーザを取得
        CmnBelongmDao cbDao = new CmnBelongmDao(con);
        String[] gsid =
                (String[]) memberGroupSidList.toArray(new String[memberGroupSidList.size()]);

        List<String> belongedUserList = new ArrayList<String>();
        if (gsid.length != 0) {
            belongedUserList = cbDao.select(gsid);
        }

        for (String sid : memberSidList) {
            if (belongedUserList.contains(sid)
                    || memberUserList.contains(sid)) {
                matchList.add(sid);
            } else {
                mismatchList.add(sid);
            }
        }

        if (!properFlg) {
            return mismatchList;
        }
        return matchList;
    }

    /**
     * <br>[機  能] 選択されたリスト内のユーザが存在するかのチェックを行います
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userList ユーザ一覧
     * @return 存在しないユーザがいる:true 正規ユーザ:false
     * @throws SQLException SQL実行時例外
     */
    public boolean checkUnknownUser(
            Connection con,
            String[] userList)
                    throws SQLException {

        boolean bRet = false;
        if (userList == null) {
            return bRet;
        }
        int userCount = 0;
        int groupCount = 0;
        List<String> usrList = new ArrayList<String>();
        List<String> groupList = new ArrayList<String>();
        for (int idx = 0; idx < userList.length; idx++) {
            if (userList[idx].substring(0, 1).equals("G")) {
                groupCount += 1;
                groupList.add(userList[idx].substring(1));
            } else {
                userCount += 1;
                usrList.add(userList[idx]);
            }
        }
        //ユーザ
        if (userCount > 0) {
            UserBiz ubiz = new UserBiz();
            ArrayList <CmnUsrmInfModel> uList =
                    ubiz.getUserList(con, usrList.toArray(new String[usrList.size()]), -1);
            if (uList.size() != userCount) {
                bRet = true;
            }
        }
        //グループ
        if (groupCount > 0) {
            BulletinDao bDao = new BulletinDao(con);
            int gCnt = bDao.getUnknownGroup(groupList);
            if (gCnt != groupCount) {
                bRet = true;
            }
        }
        return bRet;
    }
    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir
     * @return
     */
    public Cmn110FileModel getFileInfo(String tempDir) {
        File file = new File(tempDir);
        if (file.listFiles() != null) {
            for (File child : file.listFiles()) {
                String fileName = child.getName();
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                try {
                    Object fObj = objFile.load();
                    if (fObj == null) {
                        continue;
                    }
                    Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                    return fMdl;

                } catch (IOToolsException e) {
                    continue;
                }
            }
        }
        return null;
    }
}
