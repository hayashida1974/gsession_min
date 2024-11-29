package jp.groupsession.v2.rng.rng020;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.formmodel.SingleSelectModel;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.model.RngKeiroStepModel;
import jp.groupsession.v2.rng.model.RngKeirostepSelectModel;
import jp.groupsession.v2.rng.rng110keiro.EnumKeiroKbn;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm.TargetPosSel;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogParamModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] 画面内の経路モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng020Keiro {
    /** 経路種別*/
    private int keiroKbn__ = EnumKeiroKbn.FREESET_VAL;
    /**経路ステップ情報*/
    private RngKeiroStepModel step__;
    /**経路ステップ選択情報 初期表示のload時に格納 再描画後はnull*/
    private List<RngKeirostepSelectModel> initSelect__;

    /**選択用 ユーザとグループ同時選択用モデル(任意設定で使用)*/
    private UserGroupSelectModel withGroup__ = new UserGroupSelectModel();
    /**選択用 ユーザのみの選択モデル(ユーザ指定, ユーザ選択で使用)*/
    private UserGroupSelectModel onlyUser__ = new UserGroupSelectModel();

    /**選択用 グループ選択モデル*/
    private GroupComboModel grpSel__ = new GroupComboModel();
    /**選択用 役職選択モデル*/
    private SingleSelectModel posSel__ = new SingleSelectModel();

    /** 経路ステップ 設定*/
    private Rng110KeiroDialogParamModel pref__ = new Rng110KeiroDialogParamModel();
    /** 非表示フラグ*/
    private int hidden__ = 0;
    /**前回描画時の非表示設定 */
    private int prevHidden__ = 0;

    /** 表示用 送信予定審議者リスト*/
    private List<Rng020KeiroKakuninDsp> dspSingiList__;

    /** 稟議テンプレート経路SID */
    private int rtkSid__ = 0;
    /** スキップ可能フラグ 0:スキップ不可 1:スキップ可能 */
    private int skipKyoka__ = RngConst.RNG_DISABLE_SKIP;

    /**
     * コンストラクタ
     */
    public Rng020Keiro() {
        withGroup__.setSelecterWithGroup(true);
    }

    /**
     *
     * <br>[機  能] 経路上長選択用GroupClass
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    protected static class Rng020BossTargetModel extends GroupModel {
        /** グループSIDとグループ管理者数の関連Map*/
        private Map<Integer, Integer> bossCntMap__;
        /** 上位グループのgrpSid配列（自分を含む）*/
        private List<Integer> higherClassGrp__;

        /**
         * <p>bossCntMap を取得します。
         * @return bossCntMap
         * @see jp.groupsession.v2.rng.rng020.Rng020Keiro.Rng020BossTargetModel#bossCntMap__
         */
        public Map<Integer, Integer> getBossCntMap() {
            return bossCntMap__;
        }
        /**
         * <p>bossCntMap をセットします。
         * @param bossCntMap bossCntMap
         * @see jp.groupsession.v2.rng.rng020.Rng020Keiro.Rng020BossTargetModel#bossCntMap__
         */
        public void setBossCntMap(Map<Integer, Integer> bossCntMap) {
            bossCntMap__ = bossCntMap;
        }
        /**
         * <p>higherClassGrp を取得します。
         * @return higherClassGrp
         * @see jp.groupsession.v2.rng.rng020.Rng020BossTargetModel#higherClassGrp__
         */
        public List<Integer> getHigherClassGrp() {
            return higherClassGrp__;
        }
        /**
         * <p>higherClassGrp をセットします。
         * @param higherClassGrp higherClassGrp
         * @see jp.groupsession.v2.rng.rng020.Rng020BossTargetModel#higherClassGrp__
         */
        public void setHigherClassGrp(List<Integer> higherClassGrp) {
            higherClassGrp__ = higherClassGrp;
        }
        /**
         *
         * <br>[機  能] 指定数の階層上に管理者不在のグループがないか確認します
         * <br>[解  説]
         * <br>[備  考]
         * @param needStep 必須階層
         * @param stepCnt 確認階層数
         * @return 指定数の階層上に管理者不在のグループがある場合 false
         */
        public boolean chkLessBossStep(int needStep, int stepCnt) {
            if (bossCntMap__ == null) {
                return true;
            }
            if (needStep > higherClassGrp__.size()) {
                return false;
            }
            int roop = stepCnt;
            if (stepCnt > higherClassGrp__.size()) {
                roop = higherClassGrp__.size();
            }
            for (int i = 0; i < roop; i++) {
                int grpSid = higherClassGrp__.get(i);
                if (bossCntMap__.containsKey(grpSid)
                     && bossCntMap__.get(grpSid) > 0) {
                    return true;
                }
            }
            return false;
        }

        /**
        * <br>[機  能] 承認者数を各上長指定が達成するかチェックします
        * <br>[解  説]
        * <br>[備  考]
        * @param threshould 承認者数
        * @param stepCnt 自動上長
        * @return 指定数の階層上に管理者不在のグループがある場合 false
        */
       public boolean threshouldCheck(int threshould, int stepCnt) {
           int roop = stepCnt;
           if (stepCnt > higherClassGrp__.size()) {
               roop = higherClassGrp__.size();
           }
           for (int i = 0; i < roop; i++) {
               int grpSid = higherClassGrp__.get(i);
               if (bossCntMap__.containsKey(grpSid)) {
                   if (bossCntMap__.get(grpSid) >= threshould) {
                       return true;
                   }
               }
           }
           return false;
       }
    }
    /**
     * <p>step を取得します。
     * @return step
     */
    public RngKeiroStepModel getStep() {
        return step__;
    }
    /**
     * <p>step をセットします。
     * @param step step
     */
    public void setStep(RngKeiroStepModel step) {
        step__ = step;
    }
    /**
     * <p>select を取得します。
     * @return select
     */
    public List<RngKeirostepSelectModel> getInitSelect() {
        return initSelect__;
    }
    /**
     * <p>select をセットします。
     * @param select select
     */
    public void setInitSelect(List<RngKeirostepSelectModel> select) {
        initSelect__ = select;
    }
    /**
     * <p>keiroKbn を取得します。
     * @return keiroKbn
     */
    public int getKeiroKbn() {
        return keiroKbn__;
    }
    /**
     * <p>keiroKbn をセットします。
     * @param keiroKbn keiroKbn
     */
    public void setKeiroKbn(int keiroKbn) {
        keiroKbn__ = keiroKbn;
    }
    /**
     * <p>usrgrpSel を取得します。
     * @return usrgrpSel
     */
    public UserGroupSelectModel getUsrgrpSel() {

        if (keiroKbn__ == EnumKeiroKbn.FREESET_VAL) {
            return withGroup__;
        } else {
            return onlyUser__;
        }
    }
    /**
     * @return the withGroup
     */
    public UserGroupSelectModel getWithGroup() {
        return withGroup__;
    }
    /**
     * @param withGroup the withGroup to set
     */
    public void setWithGroup(UserGroupSelectModel withGroup) {
        withGroup__ = withGroup;
    }
    /**
     * @return the onlyUser
     */
    public UserGroupSelectModel getOnlyUser() {
        return onlyUser__;
    }
    /**
     * @param onlyUser the onlyUser to set
     */
    public void setOnlyUser(UserGroupSelectModel onlyUser) {
        onlyUser__ = onlyUser;
    }

    /**
     * <p>grpSel を取得します。
     * @return grpSel
     */
    public GroupComboModel getGrpSel() {
        return grpSel__;
    }
    /**
     * <p>grpSel をセットします。
     * @param grpSel grpSel
     */
    public void setGrpSel(GroupComboModel grpSel) {
        grpSel__ = grpSel;
    }
    /**
     * <p>pref を取得します。
     * @return pref
     */
    public Rng110KeiroDialogParamModel getPref() {
        return pref__;
    }
    /**
     * <p>pref をセットします。
     * @param pref pref
     */
    public void setPref(Rng110KeiroDialogParamModel pref) {
        pref__ = pref;
        keiroKbn__ = pref.getKeiroKbn();
    }
    /**
     *
     * <br>[機  能] 経路ステップの選択初期値をセット
     * <br>[解  説]
     * <br>[備  考]
     */
    public void initDefault() {
        String[] defSelected = null;
        List<RngKeirostepSelectModel> selectList = new ArrayList<>();
        try {
            switch (EnumKeiroKbn.valueOf(keiroKbn__)) {
            case FREESET:
                defSelected = pref__.getUsrgroupselect().getSelected("target");
                break;
            case USERTARGET:
                defSelected = pref__.getUsrgrouptarget().getSelected("target");
                break;
            case BOSSTARGET:
                break;
            case POSTARGET:
                break;
            default:
            }
        } catch (EnumOutRangeException e) {
        }
        if (defSelected == null) {
            return;
        }
        for (String select : defSelected) {
            RngKeirostepSelectModel selModel = new RngKeirostepSelectModel();
            if (select.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                String gsidStr = select.substring(UserGroupSelectBiz.GROUP_PREFIX.length());
                if (!ValidateUtil.isNumber(gsidStr)) {
                    continue;
                }
                selModel.setGrpSid(Integer.parseInt(gsidStr));
                selectList.add(selModel);
            } else if (ValidateUtil.isNumberHaifun(select)) {
                selModel.setUsrSid(Integer.parseInt(select));
                selectList.add(selModel);
            }
        }
        setInitSelect(selectList);

    }
    /**
     *
     * <br>[機  能] 描画設定
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエストモデル
     * @param defGroupSid デフォルトグループSID
     * @param grplist グループ一覧
     * @param belongGrpList 申請者所属グループ一覧
     * @param posLabelMap 役職ラベル一覧
     * @param block 親ブロック
     * @param preview プレビューフラグ
     * @param rollType 0:承認経路 1:最終確認経路
     * @throws SQLException SQL実行時例外
     */
    public void dspInit(Connection con, RequestModel reqMdl,
            String defGroupSid, List<UsrLabelValueBean> grplist,
            List<GroupModel> belongGrpList,
            Map<Integer, LabelValueBean> posLabelMap,
            Rng020KeiroBlock block, boolean preview,
            int rollType) throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        RngBiz rngBiz = new RngBiz(con);
        try {
            List<String> banSidList = new ArrayList<String>();
            List<UsrLabelValueBean> grplist4usrSel = null;
            int threshould = 1;
            Rng110KeiroDialogParamModel pref = block.getPref();
            if (pref.getOutcondition() == RngConst.RNG_OUT_CONDITION_NUMBER) {
                threshould = NullDefault.getInt(pref.getOutcond_threshold(), 1);
            }
            switch (EnumKeiroKbn.valueOf(keiroKbn__)) {
            case FREESET:
                if (initSelect__ != null) {
                    List<String> selected = new ArrayList<>();
                    for (RngKeirostepSelectModel select : initSelect__) {
                        if (select.getUsrSid() >= 0
                            && select.getUsrSid() != reqMdl.getSmodel().getUsrsid()) {
                            selected.add(String.valueOf(select.getUsrSid()));
                        } else if (select.getGrpSid() >= 0) {
                            selected.add(UserGroupSelectBiz.GROUP_PREFIX
                                    + String.valueOf(select.getGrpSid()));
                        }
                    }
                    withGroup__.setSelectedSimple(selected.toArray(new String[selected.size()]));
                }
                if (block.getPref().getOwn() == RngConst.RNG_OWNSINGI_NO) {
                    banSidList.add(String.valueOf(reqMdl.getSmodel().getUsrsid()));
                }
                withGroup__.setUsePluginSeigen(true, RngConst.PLUGIN_ID_RINGI);
                withGroup__.init(con, reqMdl, defGroupSid,
                        grplist, banSidList.toArray(new String[banSidList.size()]));
                break;

            case GROUPSEL:
                if (initSelect__ != null) {
                    List<String> selected = new ArrayList<>();
                    for (RngKeirostepSelectModel select : initSelect__) {
                        if (select.getGrpSid() >= 0) {
                            selected.add(String.valueOf(select.getGrpSid()));
                        }
                    }
                    grpSel__.setSelected(selected.toArray(new String[selected.size()]));
                }
                if (!ArrayUtils.isEmpty(pref__.getGroupSel().getSelected())) {
                    grpSel__.setUseSeigen(UserGroupSelectModel.FLG_SEIGEN_SELECTABLE);
                    grpSel__.setSelectable(
                            new ArrayList<>(Arrays.asList(pref__.getGroupSel().getSelected())));
                }
                grpSel__.setMultiFlg(getPref().getMultisel());
                grplist4usrSel = new ArrayList<UsrLabelValueBean>(grplist);
                grplist4usrSel.remove(0);

                //条件を満たさないグループに関しては表示しない
                if (block.getKeiroKbn() == keiroKbn__) {
                    List<String> inputList = new ArrayList<String>();
                    ArrayList<String> grpUsrList = (ArrayList<String>) grpSel__.getSelectable();
                    Map<String, Integer> grpMap =  rngBiz.getUserListCnt(grpUsrList);
                    int multiUserCnt = 0;

                    if (grpUsrList != null && grpUsrList.size() > 0) {
                        ArrayList<Integer> nGrpUsrList = new ArrayList<Integer>();
                        for (String str : grpUsrList) {
                            nGrpUsrList.add(Integer.parseInt(str));
                        }
                        if (rngBiz.getUserList(nGrpUsrList, null).size() == 0
                                && pref.getNonuser() == 0) {
                            block.setSkipNonUser(1);
                            block.setHidden(1);
                        }
                    }
                    for (Map.Entry<String, Integer> entry : grpMap.entrySet()) {
                        if (pref.getMultisel() == 0) {
                            //単体選択
                            //ユーザ選択承認人数エラーチェック
                            if (entry.getValue() >= threshould) {
                                inputList.add(entry.getKey());
                            }
                        } else {
                            //複数選択
                            if (entry.getValue() > 0) {
                                multiUserCnt += entry.getValue();
                                inputList.add(entry.getKey());
                            }
                        }
                    }
                    grpSel__.setSelectable(inputList);

                    if (grpUsrList != null && inputList.size() == 0 && pref.getMultisel() == 0) {
                        block.setSelectUserNone(1);
                    } else if (grpUsrList != null
                            && pref.getMultisel() != 0 && multiUserCnt < threshould) {
                        block.setSelectUserNone(1);
                    } else {
                        block.setSelectUserNone(0);
                    }
                }
                grpSel__.init(grplist4usrSel, defGroupSid);
                break;
            case USERSEL:
                if (initSelect__ != null) {
                    List<String> selected = new ArrayList<>();
                    for (RngKeirostepSelectModel select : initSelect__) {
                        if (select.getUsrSid() >= 0) {
                            selected.add(String.valueOf(select.getUsrSid()));
                        } else if (select.getGrpSid() >= 0) {
                            selected.add(
                               UserGroupSelectBiz.GROUP_PREFIX
                               + String.valueOf(select.getGrpSid()));
                        }
                    }
                    onlyUser__.setSelectedSimple(
                        selected.toArray(new String[selected.size()]));
                }
                if (!ArrayUtils.isEmpty(pref__.getUsrgroupselect().getSelected("target"))) {
                    onlyUser__.setUseSeigen(UserGroupSelectModel.FLG_SEIGEN_SELECTABLE);
                    onlyUser__.setSelectable(
                        pref__.getUsrgroupselect().getSelected("target"));
                }
                if (getPref().getOwn() == RngConst.RNG_OWNSINGI_NO
                        && block.getPref().getOwn() == RngConst.RNG_OWNSINGI_NO) {
                    banSidList.add(String.valueOf(reqMdl.getSmodel().getUsrsid()));
                }
                if (getPref().getMultisel() == UserGroupSelectModel.FLG_MULTI_OFF) {
                    //ユーザ選択機能をダイアログ表示方式で固定
                    onlyUser__.setUseDialog(UserGroupSelectModel.FLG_DIALOG_ON);
                }
                onlyUser__.setMultiFlg(getPref().getMultisel());

                grplist4usrSel
                  = new ArrayList<UsrLabelValueBean>(grplist);
                grplist4usrSel.remove(0);

                onlyUser__.setUsePluginSeigen(true, RngConst.PLUGIN_ID_RINGI);

                if (block.getKeiroKbn() == keiroKbn__) {
                    ArrayList<Integer> grpUsrBanList = new ArrayList<Integer>();
                    if (block.getOwnSingi() == RngConst.RNG_OWNSINGI_NO) {
                        grpUsrBanList.add(reqMdl.getSmodel().getUsrsid());
                    }
                    //不要ユーザ・グループ除外処理
                    if (onlyUser__.getSelectable() != null) {
                        ArrayList<String> usrInputSelect = new ArrayList<String>();

                        ArrayList<Integer> glist = new ArrayList<Integer>();
                        ArrayList<Integer> ulist = new ArrayList<Integer>();
                        for (String str : onlyUser__.getSelectable()) {
                            if (str.contains(new String(
                                    UserGroupSelectBiz.GROUP_PREFIX).subSequence(0, 1))) {
                                String grpStr = str.substring(1, str.length());
                                if (ValidateUtil.isNumber(grpStr)) {
                                    glist.add(Integer.parseInt(grpStr));
                                }
                            } else {
                                if (block.getOwnSingi() == RngConst.RNG_OWNSINGI_NO
                                        && Integer.parseInt(str)
                                        == reqMdl.getSmodel().getUsrsid()) {
                                    continue;
                                }
                                ulist.add(Integer.parseInt(str));
                            }
                        }
                        glist = rngBiz.getGroupList(glist, grpUsrBanList);
                        for (int sid : glist) {
                            usrInputSelect.add(UserGroupSelectBiz.GROUP_PREFIX
                                    + String.valueOf(sid));
                        }
                        ulist = rngBiz.getUserList(ulist);
                        for (int sid : ulist) {
                            usrInputSelect.add(String.valueOf(sid));
                        }

                        if (usrInputSelect.size() == 0 && pref.getNonuser() == 0) {
                            block.setSkipNonUser(1);
                            block.setHidden(1);
                        }
                        onlyUser__.setSelectable(
                                usrInputSelect.toArray(new String[usrInputSelect.size()]));
                        onlyUser__.setUseSeigen(1);
                    }
                }

                onlyUser__.init(con, reqMdl, defGroupSid,
                       grplist4usrSel, banSidList.toArray(new String[banSidList.size()]));
                break;
            case USERTARGET:
                if (preview) {
                    if (initSelect__ != null) {
                        List<String> selected = new ArrayList<>();
                        for (RngKeirostepSelectModel select : initSelect__) {
                            if (select.getUsrSid() >= 0) {
                                selected.add(String.valueOf(select.getUsrSid()));
                            } else if (select.getGrpSid() >= 0) {
                                selected.add(UserGroupSelectBiz.GROUP_PREFIX
                                        + String.valueOf(select.getGrpSid()));
                            }
                        }
                        onlyUser__.setSelectedSimple(
                                selected.toArray(new String[selected.size()]));
                    }
                }
                if (block.getKeiroKbn() == keiroKbn__) {
                  //不要ユーザ・グループ除外処理
                    ArrayList<Integer> grpList = new ArrayList<Integer>();
                    ArrayList<Integer> usrList = new ArrayList<Integer>();
                    ArrayList<String> mixList = new ArrayList<String>();
                    for (String sid : onlyUser__.getSelectedSimple()) {
                        if (StringUtil.isNullZeroString(sid)) {
                            continue;
                        }
                        if (sid.startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                            //グループ指定されている場合、そのグループに所属するユーザを取得する
                            grpList.add(NullDefault.getInt(
                                    sid.substring(UserGroupSelectBiz.GROUP_PREFIX.length()), -1));
                        } else {
                            usrList.add(NullDefault.getInt(sid, -1));
                        }
                    }
                    grpList = rngBiz.getGroupList(grpList, null);
                    for (int idx = 0; idx < grpList.size(); idx++) {
                        mixList.add(UserGroupSelectBiz.GROUP_PREFIX
                                + String.valueOf(grpList.get(idx)));
                    }
                    usrList = rngBiz.getUserList(usrList);
                    for (int idx = 0; idx < usrList.size(); idx++) {
                        mixList.add(String.valueOf(usrList.get(idx)));
                    }
                    if (mixList.size() == 0 && pref.getNonuser() == 0) {
                        block.setSkipNonUser(1);
                        block.setHidden(1);
                    }
                    onlyUser__.setSelectedSimple(mixList.toArray(new String[mixList.size()]));
                }
                onlyUser__.init(con, reqMdl, defGroupSid, grplist, null);
                break;
            case BOSSTARGET:
                if (initSelect__ != null) {
                    List<String> selected = new ArrayList<>();
                    for (RngKeirostepSelectModel select : initSelect__) {
                        if (select.getUsrSid() >= 0) {
                            selected.add(String.valueOf(select.getUsrSid()));
                        } else if (select.getGrpSid() >= 0) {
                            selected.add(String.valueOf(select.getGrpSid()));
                        }
                    }
                    grpSel__.setSelected(selected.toArray(new String[selected.size()]));
                }

                List<UsrLabelValueBean> bossTargetList = getBossTargetList(gsMsg,
                        belongGrpList, getPref().getBossStepCntMin(),
                        getPref().getBossStepCnt(), threshould);

                if (block.getKeiroKbn() == keiroKbn__) {
                    if (rollType == RngConst.RNG_RNCTYPE_CONFIRM
                            && (bossTargetList == null
                            || bossTargetList.size() == 1)) {
                        block.setSkipNonUser(1);
                        block.setHidden(1);
                    }
                }

                grpSel__.init(bossTargetList, defGroupSid);
                break;
            case POSTARGET:
                ArrayList<String> postList = new ArrayList<String>();
                for (Entry<String, TargetPosSel> entry
                        : pref__.getTargetposMap().entrySet()) {
                    TargetPosSel target = entry.getValue();
                    if (!StringUtil.isNullZeroString(target.getPosSel().getSelected())) {
                        postList.add(target.getPosSel().getSelected());
                    }
                }
                CmnPositionDao cPos = new CmnPositionDao(con);
                List<CmnPositionModel>cPosMdl =
                        cPos.getPosListSort(postList.toArray(new String[postList.size()]));

                List<String> delList = new ArrayList<String>();
                CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con);
                int usrCnt = 0;
                int setPosCnt = 0;
                if (pref__.getTargetposMap().entrySet() != null) {
                    setPosCnt = pref__.getTargetposMap().entrySet().size();
                }
                int idx = 1;
                for (Entry<String, TargetPosSel> entry
                        : pref__.getTargetposMap().entrySet()) {
                    TargetPosSel target = entry.getValue();
                    LabelValueBean posBean = null;
                    //選択済み要素が存在する場合のみ表示設定
                    String posKey = target.getPosSel().getSelected();
                    if (posKey.equals("0") && idx == setPosCnt && preview) {
                        posKey = "";
                    }
                    boolean sonzaiChk = false;
                    if (!StringUtil.isNullZeroString(posKey)) {
                        int grpValue = -1;
                        if (!StringUtil.isNullZeroString(target.getGrpSel().getSelected())) {
                            grpValue = Integer.parseInt(target.getGrpSel().getSelected());
                        }
                        ArrayList<Integer> usrSids =
                                usrDao.getBelongUsrsFromPosition(grpValue,
                                        Integer.parseInt(posKey));
                        usrCnt = rngBiz.getUserList(usrSids).size() + usrCnt;
                        posBean = posLabelMap.get(Integer.valueOf(posKey));
                        for (CmnPositionModel mdl : cPosMdl) {
                            if (mdl.getPosSid() == Integer.parseInt(posKey)) {
                                sonzaiChk = true;
                                break;
                            }
                        }
                    }
                    if (!sonzaiChk) {
                        delList.add(entry.getKey());
                    } else {
                        if (posBean != null) {
                            target.getPosSel().setDspSelectData(Arrays.asList(posBean));
                        }
                        if (!StringUtil.isNullZeroString(target.getGrpSel().getSelected())) {
                            String key = target.getGrpSel().getSelected();
                            for (UsrLabelValueBean label : grplist) {
                                if (label.getValue().equals(key)) {
                                    target.getGrpSel().setDspSelectData(grplist);
                                    break;
                                }
                            }
                        }
                    }
                    idx++;
                }
                if (block.getKeiroKbn() == keiroKbn__) {
                    for (String delPos : delList) {
                        pref__.getTargetposMap().remove(delPos);
                    }
                    if (usrCnt == 0 && pref.getNonuser() == 0) {
                        block.setSkipNonUser(1);
                        block.setHidden(1);
                    }
                }
                break;
            default:
            }
        } catch (EnumOutRangeException e) {
        }
    }
    /**
     * <p>posSel を取得します。
     * @return posSel
     */
    public SingleSelectModel getPosSel() {
        return posSel__;
    }
    /**
     * <p>posSel をセットします。
     * @param posSel posSel
     */
    public void setPosSel(SingleSelectModel posSel) {
        posSel__ = posSel;
    }
    /**
     * <p>hidden を取得します。
     * @return hidden
     */
    public int getHidden() {
        return hidden__;
    }
    /**
     * <p>hidden をセットします。
     * @param hidden hidden
     */
    public void setHidden(int hidden) {
        hidden__ = hidden;
    }
    /**
     * <p>prevHidden を取得します。
     * @return prevHidden
     */
    public int getPrevHidden() {
        return prevHidden__;
    }
    /**
     * <p>prevHidden をセットします。
     * @param prevHidden prevHidden
     */
    public void setPrevHidden(int prevHidden) {
        prevHidden__ = prevHidden;
    }
    /**
     * <p>rtkSid を取得します。
     * @return rtkSid
     */
    public int getRtkSid() {
        return rtkSid__;
    }
    /**
     * <p>rtkSid をセットします。
     * @param rtkSid rtkSid
     */
    public void setRtkSid(int rtkSid) {
        rtkSid__ = rtkSid;
    }
    /**
     * <p>skipKyoka を取得します。
     * @return skipKyoka
     */
    public int getSkipKyoka() {
        return skipKyoka__;
    }
    /**
     * <p>skipKyoka をセットします。
     * @param skipKyoka skipKyoka
     */
    public void setSkipKyoka(int skipKyoka) {
        skipKyoka__ = skipKyoka;
    }
    /**
     *
     * <br>[機  能] 経路ステップ選択情報を作成します
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param rksSid 経路ステップSID
     * @return 経路ステップ選択情報 List
     * @throws EnumOutRangeException 経路区分不正例外
     * @throws SQLException SQL実行例外
     */
    public List<RngKeirostepSelectModel> createRngKeiroSelect(
            Connection con,
            int rksSid)
            throws EnumOutRangeException, SQLException {

        //経路ステップ選択情報の登録
        RngKeirostepSelectModel selectMdl = new RngKeirostepSelectModel();
        List<RngKeirostepSelectModel> selectList = new ArrayList<RngKeirostepSelectModel>();

            switch (EnumKeiroKbn.valueOf(getKeiroKbn())) {
            // 任意選択
            case FREESET:
                Map<String, String[]> select = getUsrgrpSel().getSelected();
                for (Map.Entry<String, String[]> entry : select.entrySet()) {
                    String[] sids = entry.getValue();
                    for (String sid : sids) {
                        if (sid.startsWith("G")) {
                            selectMdl = __inputSelectMdl(rksSid, "-1", sid.substring(1), "-1");
                        } else {
                            selectMdl = __inputSelectMdl(rksSid, sid, "-1", "-1");
                        }
                        selectList.add(selectMdl);
                    }
                }
                break;
            // グループ選択
            case GROUPSEL:
                String[] grpSids = getGrpSel().getMultiselect().getSelected();
                if (!ArrayUtils.isEmpty(grpSids)) {
                    for (String grpSid : grpSids) {
                        selectMdl = __inputSelectMdl(rksSid, "-1", grpSid, "-1");
                        selectList.add(selectMdl);
                    }
                }
                break;
            //ユーザ選択
            case USERSEL:
                Map<String, String[]> usrs = getUsrgrpSel().getSelected();
                for (Map.Entry<String, String[]> entry : usrs.entrySet()) {
                    String[] usrSids = entry.getValue();
                    for (String usrSid : usrSids) {
                        selectMdl = __inputSelectMdl(rksSid, usrSid, "-1", "-1");
                        selectList.add(selectMdl);
                    }
                }
                break;
            //ユーザ指定
            case USERTARGET:
                Map<String, String[]> usrTargets = getUsrgrpSel().getSelected();
                for (Map.Entry<String, String[]> entry : usrTargets.entrySet()) {
                    String[] usrSids = entry.getValue();
                    for (String usrSid : usrSids) {
                        if (usrSid.startsWith("G")) {
                            selectMdl = __inputSelectMdl(rksSid, "-1", usrSid.substring(1), "-1");
                        } else {
                            selectMdl = __inputSelectMdl(rksSid, usrSid, "-1", "-1");
                        }
                        selectList.add(selectMdl);
                    }
                }
                break;
            // 上長指定
            case BOSSTARGET:
                String[] bossSids = getGrpSel().getMultiselect().getSelected();
                for (String grpSid : bossSids) {
                    selectMdl = __inputSelectMdl(rksSid, "-1", grpSid, "-1");
                    selectList.add(selectMdl);
                }
                break;
            //役職指定
            case POSTARGET:
                for (Entry<String, TargetPosSel> entry
                        : getPref().getTargetposMap().entrySet()) {
                    String posSid = entry.getValue().getPosSel().getSelected();
                    String grpSid = entry.getValue().getGrpSel().getSelected();
                    selectMdl = __inputSelectMdl(rksSid, "-1", grpSid, posSid);
                    selectList.add(selectMdl);
                }
                break;
            default:
                break;
            }
        return selectList;
    }

    /**
     * 経路選択情報モデルに値を入力します。
     * @param rksSid 経路テンプレートSID
     * @param usrSid ユーザSID
     * @param grpSid グループSID
     * @param posSid 役職SID
     * @return 経路選択情報モデル
     * */
    private RngKeirostepSelectModel __inputSelectMdl(
            int rksSid, String usrSid, String grpSid, String posSid) {
        RngKeirostepSelectModel ret = new RngKeirostepSelectModel();
        ret.setRksSid(rksSid);
        ret.setUsrSid(NullDefault.getInt(usrSid, -1));
        ret.setGrpSid(NullDefault.getInt(grpSid, -1));
        ret.setPosSid(NullDefault.getInt(posSid, -1));

        return ret;
    }
    /**
     *
     * <br>[機  能] 所属グループから階層数条件を満たした表示グループ用のグループリストを作成する
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param gsMsg GSメッセージ
     * @param belongGpList 申請者所属グループ一覧
     * @param needStep 必須階層数
     * @param autoStep 自動上長階層数
     * @param threshould 承認者数
     * @return グループラベルのArrayList
     * @throws SQLException SQL実行時例外
     */

    private List<UsrLabelValueBean> getBossTargetList(
            GsMessage gsMsg,
            List<GroupModel> belongGpList,
            int needStep,
            int autoStep,
            int threshould) throws SQLException {

        //選択してください
        String textSelect = gsMsg.getMessage("cmn.select.plz");

        ArrayList<UsrLabelValueBean> labelList = new ArrayList<UsrLabelValueBean>();
        labelList.add(new UsrLabelValueBean(textSelect, String.valueOf(-1)));
        for (GroupModel gmodel : belongGpList) {
            boolean addSelect = false;
            if (gmodel.getClassLevel() >= needStep) {
                //必須上長チェック
                addSelect = true;
            }
            if (gmodel instanceof Rng020BossTargetModel && addSelect) {
                //自動上長ユーザ存在チェック
                addSelect = ((Rng020BossTargetModel) gmodel)
                        .chkLessBossStep(needStep, autoStep);
            }
            if (addSelect) {
                //承認数達成チェック
                addSelect = ((Rng020BossTargetModel) gmodel)
                        .threshouldCheck(threshould, autoStep);
            }
            if (addSelect) {
                labelList.add(new UsrLabelValueBean(gmodel.getGroupName(), String
                        .valueOf(gmodel.getGroupSid())));
            }
        }
        return labelList;
    }
    /**
     * <p>dspSingiList を取得します。
     * @return dspSingiList
     * @see jp.groupsession.v2.rng.rng020.Rng020Keiro#dspSingiList__
     */
    public List<Rng020KeiroKakuninDsp> getDspSingiList() {
        return dspSingiList__;
    }
    /**
     * <p>dspSingiList をセットします。
     * @param dspSingiList dspSingiList
     * @see jp.groupsession.v2.rng.rng020.Rng020Keiro#dspSingiList__
     */
    public void setDspSingiList(List<Rng020KeiroKakuninDsp> dspSingiList) {
        dspSingiList__ = dspSingiList;
    }
    /**
     * <br>[機  能] 指定されたユーザから対象プラグインを使用可能なユーザを選択して返す
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param userSidList ユーザSID
     * @return ユーザSID
     * @throws SQLException SQL実行時例外
     */
    private List<Integer> __getCanUsePluginUser(Connection con, Collection<Integer> userSidList)
    throws SQLException {
        List<Integer> iUserSidList = new ArrayList<Integer>(userSidList);
        if (userSidList == null || userSidList.size() == 0) {
            return iUserSidList;
        }
        CommonBiz cmnBiz = new CommonBiz();
        List<Integer> cantUseSidList =
                cmnBiz.getCantUsePluginUser(con,
                        RngConst.PLUGIN_ID_RINGI,
                        iUserSidList);

        //ユーザSIDの順序
        List<Integer> canUseUserList = new ArrayList<Integer>(userSidList);
        canUseUserList.removeAll(cantUseSidList);
        return canUseUserList;
    }
    /**
     * 指定したグループの上位階層グループを取得する
     * @param con コネクション
     * @param grpSid グループSID
     * @param bossStepCnt 上位階層数 -1 指定時は最大数
     * @param selfAdd 指定グループを結果に含めるか
     *@return 上位階層グループのSID
     * @throws SQLException SQL実行例外
     * */
    private ArrayList<GroupModel> __getHigherRankGrp(Connection con, String grpSid,
            int bossStepCnt,
            boolean selfAdd)
            throws SQLException {
        GroupDao grpDao = new GroupDao(con);
        CmnGroupClassModel gclMdl
            = grpDao.getGroupClassModel(Integer.parseInt(grpSid));
        ArrayList<GroupModel> ret = new ArrayList<GroupModel>();
        if (gclMdl == null) {
            return ret;
        }
        // 指定されたグループが何階層目にいるか
        int classNum = 0;
        if (gclMdl.getGclSid1() == Integer.parseInt(grpSid)) {
            classNum = 1;
        } else if (gclMdl.getGclSid2() == Integer.parseInt(grpSid)) {
            classNum = 2;
        } else if (gclMdl.getGclSid3() == Integer.parseInt(grpSid)) {
            classNum = 3;
        } else if (gclMdl.getGclSid4() == Integer.parseInt(grpSid)) {
            classNum = 4;
        } else if (gclMdl.getGclSid5() == Integer.parseInt(grpSid)) {
            classNum = 5;
        } else if (gclMdl.getGclSid6() == Integer.parseInt(grpSid)) {
            classNum = 6;
        } else if (gclMdl.getGclSid7() == Integer.parseInt(grpSid)) {
            classNum = 7;
        } else if (gclMdl.getGclSid8() == Integer.parseInt(grpSid)) {
            classNum = 8;
        } else if (gclMdl.getGclSid9() == Integer.parseInt(grpSid)) {
            classNum = 9;
        } else if (gclMdl.getGclSid10() == Integer.parseInt(grpSid)) {
            classNum = 10;
        }
        int maxRank = 0;
        if (bossStepCnt >= 0) {
            maxRank = classNum - bossStepCnt;
        }
        if (maxRank <= 0) {
            maxRank = 0;
        }
        if (selfAdd) {
            classNum++;
        }
        GroupModel addGrp;
        for (classNum--; (classNum > maxRank && classNum > 0); classNum--) {
            switch (classNum) {
            case 1 :
                addGrp = new GroupModel();
                ret.add(addGrp);
                addGrp.setClassLevel(classNum);
                addGrp.setGroupName(gclMdl.getGclName1());
                addGrp.setGroupSid(gclMdl.getGclSid1());
                break;
            case 2 :
                addGrp = new GroupModel();
                ret.add(addGrp);
                addGrp.setClassLevel(classNum);
                addGrp.setGroupName(gclMdl.getGclName2());
                addGrp.setGroupSid(gclMdl.getGclSid2());
                break;
            case 3 :
                addGrp = new GroupModel();
                ret.add(addGrp);
                addGrp.setClassLevel(classNum);
                addGrp.setGroupName(gclMdl.getGclName3());
                addGrp.setGroupSid(gclMdl.getGclSid3());
                break;
            case 4 :
                addGrp = new GroupModel();
                ret.add(addGrp);
                addGrp.setClassLevel(classNum);
                addGrp.setGroupName(gclMdl.getGclName4());
                addGrp.setGroupSid(gclMdl.getGclSid4());
                break;
            case 5 :
                addGrp = new GroupModel();
                ret.add(addGrp);
                addGrp.setClassLevel(classNum);
                addGrp.setGroupName(gclMdl.getGclName5());
                addGrp.setGroupSid(gclMdl.getGclSid5());
                break;
            case 6 :
                addGrp = new GroupModel();
                ret.add(addGrp);
                addGrp.setClassLevel(classNum);
                addGrp.setGroupName(gclMdl.getGclName6());
                addGrp.setGroupSid(gclMdl.getGclSid6());
                break;
            case 7 :
                addGrp = new GroupModel();
                ret.add(addGrp);
                addGrp.setClassLevel(classNum);
                addGrp.setGroupName(gclMdl.getGclName7());
                addGrp.setGroupSid(gclMdl.getGclSid7());
                break;
            case 8 :
                addGrp = new GroupModel();
                ret.add(addGrp);
                addGrp.setClassLevel(classNum);
                addGrp.setGroupName(gclMdl.getGclName8());
                addGrp.setGroupSid(gclMdl.getGclSid8());
                break;
            case 9 :
                addGrp = new GroupModel();
                ret.add(addGrp);
                addGrp.setClassLevel(classNum);
                addGrp.setGroupName(gclMdl.getGclName9());
                addGrp.setGroupSid(gclMdl.getGclSid9());
                break;
            case 10 :
                addGrp = new GroupModel();
                ret.add(addGrp);
                addGrp.setClassLevel(classNum);
                addGrp.setGroupName(gclMdl.getGclName10());
                addGrp.setGroupSid(gclMdl.getGclSid10());
                break;
            default :
                break;
            }
        }
        return ret;
    }

    /**
     * 表示用審議情報の設定を行います。
     * @param con コネクション
     * @param sessionUsrSid セッションユーザSID
     * @param block 経路ブロック
     * @throws SQLException SQL実行例外
     * @throws EnumOutRangeException 経路区分範囲外例外
     * */
    public void dspInitSingiList(
            int sessionUsrSid,
            Rng020KeiroBlock block,
            Connection con)
    throws SQLException, EnumOutRangeException {
        List<RngKeirostepSelectModel> selectList = createRngKeiroSelect(con, 0);
        String name = "";
        if (selectList.size() == 1) {
            switch (EnumKeiroKbn.valueOf(getKeiroKbn())) {
            // 任意選択
            case FREESET:
                UsrLabelValueBean user = getUsrgrpSel().getSelectedUserSimple();
                if (user != null && user.getValue().startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                    name = user.getLabel();
                }
                break;
            // グループ選択
            case GROUPSEL:
                for (UsrLabelValueBean label : getGrpSel().getSelectedLabel()) {
                    name = label.getLabel();
                    break;
                }
                break;
            //ユーザ選択
            case USERSEL:
                break;
            //ユーザ指定
            case USERTARGET:
                user = getUsrgrpSel().getSelectedUserSimple();
                if (user != null && user.getValue().startsWith(UserGroupSelectBiz.GROUP_PREFIX)) {
                    name = user.getLabel();
                }
                break;
            // 上長指定
            case BOSSTARGET:
                for (UsrLabelValueBean label : getGrpSel().getSelectedLabel()) {
                    name = label.getLabel();
                    break;
                }
                break;
            //役職指定
            case POSTARGET:
                break;
            default:
                break;
            }
        }

        __subLogicEntryKeiroStep(con, name, sessionUsrSid, selectList, block);
        //上長指定経路は指定グループ次の階層の経路ステップも追加登録する
        if (EnumKeiroKbn.valueOf(getKeiroKbn()) == EnumKeiroKbn.BOSSTARGET) {
            String bossSid = getGrpSel().getSelectedSingle();
            int bossStep = getPref().getBossStepCnt();

            List<GroupModel> hiGrpSids =
                    __getHigherRankGrp(con, bossSid, bossStep, false);
            // 上長階層数分経路登録
            for (GroupModel grp : hiGrpSids) {
                List<RngKeirostepSelectModel> nextBossList
                = new ArrayList<RngKeirostepSelectModel>();
                name = grp.getGroupName();
                nextBossList.add(__inputSelectMdl(0, "-1",
                        String.valueOf(grp.getGroupSid()), "-1"));
                __subLogicEntryKeiroStep(con, name, sessionUsrSid, nextBossList, block);
            }
        }

    }
    /**
     * 表示用審議情報の設定を行います。
     * @param con コネクション
     * @param name 審議者名
     * @param sessionUsrSid セッションユーザSID
     * @param selectList 選択情報
     * @param block
     * @throws SQLException SQL実行例外
     * @throws EnumOutRangeException 経路区分範囲外例外
     * */
    private void __subLogicEntryKeiroStep(Connection con,
            String name,
            int sessionUsrSid,
            List<RngKeirostepSelectModel> selectList, Rng020KeiroBlock block)
    throws SQLException, EnumOutRangeException {
        //上長指定の場合 選択グループの管理者ユーザを選択情報から取り出す
        if (EnumKeiroKbn.valueOf(getKeiroKbn()) == EnumKeiroKbn.BOSSTARGET) {
            List<RngKeirostepSelectModel> selectBossList = new ArrayList<RngKeirostepSelectModel>();
            // グループ管理者ユーザを取得
            CmnBelongmDao begDao = new CmnBelongmDao(con);
            List<CmnBelongmModel> bossList = new ArrayList<CmnBelongmModel>();
            for (RngKeirostepSelectModel selectMdl : selectList) {
                if (selectMdl.getGrpSid() >= 0) {
                    bossList.addAll(begDao.selectBossModel(selectMdl.getGrpSid()));
                }
            }
            for (CmnBelongmModel bossMdl : bossList) {
                RngKeirostepSelectModel selectMdl = __inputSelectMdl(
                        0, String.valueOf(bossMdl.getUsrSid()), "-1", "-1");
                selectBossList.add(selectMdl);
            }
            selectList = selectBossList;
        }

        //ユーザSIDセット （重複させないためSet型を使用）
        Set<Integer> userSidSet = new HashSet<Integer>();
        //経路ステップ選択情報モデルからユーザSID一覧を生成
        for (RngKeirostepSelectModel selectMdl : selectList) {
            // ユーザの取得
            if (selectMdl.getUsrSid() > 1) {
                userSidSet.add(selectMdl.getUsrSid());
            // グループ指定の場合
            } else if (selectMdl.getGrpSid() >= 0) {
                ArrayList<Integer> usrList = null;
                // 役職も指定されている場合
                if (selectMdl.getPosSid() >= 0) {
                    CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con);
                    usrList = usrDao.getBelongUsrsFromPosition(
                            selectMdl.getGrpSid(), selectMdl.getPosSid());
                } else {
                    CmnBelongmDao belongDao = new CmnBelongmDao(con);
                    usrList = belongDao.selectBelongLiveUserSid(selectMdl.getGrpSid());
                }
                userSidSet.addAll(usrList);
            // 役職指定の場合
            } else if (selectMdl.getPosSid() >= 0) {
                ArrayList<Integer> usrList = null;
                CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con);
                usrList = usrDao.getBelongUsrsFromPosition(
                        selectMdl.getGrpSid(), selectMdl.getPosSid());
                userSidSet.addAll(usrList);
            }
        }
        //任意経路設定の場合、自己審議を不許可かどうかを判定
        if (EnumKeiroKbn.valueOf(block.getKeiroKbn()) == EnumKeiroKbn.FREESET
             && block.getOwnSingi() == RngConst.RNG_OWNSINGI_NO) {
            userSidSet.remove(Integer.valueOf(sessionUsrSid));
        }

        //審議者からプラグイン使用不可ユーザを除外
        List<Integer> userSidList = __getCanUsePluginUser(con, userSidSet);
        String[] usrSidStr = new String[userSidList.size()];
        for (int i = 0; i < userSidList.size(); i++) {
            usrSidStr[i] = String.valueOf(userSidList.get(i));
        }
        //描画用リストを設定
        List<Rng020KeiroKakuninDsp> dspList = getDspSingiList();
        if (dspList == null) {
            dspList = new ArrayList<Rng020KeiroKakuninDsp>();
            setDspSingiList(dspList);
        }
        Rng020KeiroKakuninDsp dsp = new Rng020KeiroKakuninDsp();
        dspList.add(dsp);

        dsp.setName(name);
        UserGroupSelectBiz biz = new UserGroupSelectBiz();
        List<UsrLabelValueBean> selectedList = biz.getSelectedLabel(
                usrSidStr, sessionUsrSid, con, GSConstUser.USER_JTKBN_ACTIVE);
        dsp.setSingi(selectedList);

    }
}