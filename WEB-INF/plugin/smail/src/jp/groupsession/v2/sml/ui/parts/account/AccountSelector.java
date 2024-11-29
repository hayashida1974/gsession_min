package jp.groupsession.v2.sml.ui.parts.account;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.groupsession.v2.cmn.dao.base.CmnMyGroupDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnMyGroupModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.EnumModeType;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.IDefaultGroupSelectListner;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumGroupSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.EnumSelectType;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.GroupChild;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.biz.SmlCommonBiz;
import jp.groupsession.v2.sml.dao.SmlAccountDao;
import jp.groupsession.v2.sml.dao.SmlBanDestDao;

/**
 * <br>[機  能] 宛先選択UI
 * <br>[解  説] グループ選択でアカウントを選択する
 * <br>[備  考] 選択元はユーザアカウントのみだが、選択先には他の選択での代表アカウント選択を引き継ぐ
 *
 * @author JTS
 */
public class AccountSelector extends AbstractSelector implements IDefaultGroupSelectListner {
    /** 含有クラス ユーザ選択機能実装*/
    private final UserGroupSelector base__;
    /** 表示区分 1:グループ 2:マイグループ 3:代表アカウント */
    private int dspKbn__ = 1;

    protected AccountSelector(ParamForm param) {
        super(param);
        base__ = UserGroupSelector.builder()
                .chainGrpType(EnumGroupSelectType.GROUPONLY)
                .chainType(EnumSelectType.USER)
                .chainOnlyPluginUser("smail")
                .build();

    }
    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class ParamForm extends SelectorFactory<AccountSelector, ParamForm> {

        public ParamForm(Class<AccountSelector> targetClz) {
            super(targetClz);
        }

    }
    /**
     *
     * <br>[機  能] ビルダークラスの生成
     * <br>[解  説]
     * <br>[備  考]
     * @return ビルダークラス
     */
    public static ParamForm builder() {
        ParamForm ret = new ParamForm(AccountSelector.class);
        return ret;
    }
    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> selectedSidList)
                    throws SQLException {

        //ユーザアカウントを取得
        List<IChild> ret = new ArrayList<IChild>();
        ret.addAll(base__.answerSelectedList(
                reqMdl,
                con,
                paramModel,
                selectedSidList));

        //代表アカウントを取得
        SmlAccountDao sacDao = new SmlAccountDao(con);

        List<Integer> sidList =
                selectedSidList.stream()
                    .filter(sid -> sid.startsWith(GSConstSmail.SML_ACCOUNT_STR))
                    .map(sid -> Integer.valueOf(
                            sid.substring(GSConstSmail.SML_ACCOUNT_STR.length())
                            )
                        )
                    .collect(Collectors.toList());
        if (sidList.size() > 0) {
            ret.addAll(
                sacDao.selectSmlAccount(sidList)
                    .stream()
                    .map(acc -> new Child(
                            acc.getSacName(),
                            String.format("%s%s",
                                    GSConstSmail.SML_ACCOUNT_STR,
                                    acc.getSacSid())))
                    .collect(Collectors.toList())
            );
        }
        return ret;
    }
    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {
        List<IChild> ret = new ArrayList<IChild>();
        //グループ選択
        //マイグループ選択
        if (dspKbn__ == 1 || dspKbn__ == 2) {

            //送信先制限設定
            SmlBanDestDao sbdDao = new SmlBanDestDao(con);
            List<Integer> banUsrList = sbdDao.getBanDestUsrSidList(reqMdl.getSmodel().getUsrsid());

            //ユーザアカウントを取得
            ret.addAll(base__.answerSelectionList(
                    reqMdl,
                    con,
                    paramModel,
                    selectedGrpSid,
                    selectedSidList)
                        .stream()
                        .filter(child -> banUsrList.contains(
                                    Integer.valueOf(child.getValue())
                                    ) == false
                                )
                        .collect(Collectors.toList()));

            return ret;
        }
        //代表アカウント選択
        if (dspKbn__ == 3) {
            //代表アカウントを取得
            SmlCommonBiz smlCommonBiz = new SmlCommonBiz(con, reqMdl);
            SmlAccountDao sacDao = new SmlAccountDao(con);

            List<Integer> sidList = sacDao.getSmlAccountSidList()
                        .stream()
                        .filter(sid -> selectedSidList.contains(
                                    String.format("%s%d",
                                            GSConstSmail.SML_ACCOUNT_STR,
                                            sid)
                                    ) == false
                                )
                        .collect(Collectors.toList());
            //送信先制限されたアカウントを除外する
            sidList = smlCommonBiz.getValiableDestAccSid(
                    con, reqMdl.getSmodel().getUsrsid(), sidList);
            if (sidList.size() > 0) {
                ret.addAll(
                    sacDao.selectSmlAccount(sidList)
                        .stream()
                        .map(acc -> new Child(
                                acc.getSacName(),
                                String.format("%s%s",
                                        GSConstSmail.SML_ACCOUNT_STR,
                                        acc.getSacSid())))
                        .collect(Collectors.toList())
                );
            }

            return ret;
        }
        return ret;
    }
    @Override
    public List<IChild> answerGroupList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel) throws SQLException {
        if (dspKbn__ == 3) {
            return List.of();
        }
        if (dspKbn__ == 2) {
            //ユーザSIDからマイグループ情報を取得する
            int userSid = reqMdl.getSmodel().getUsrsid();
            CmnMyGroupDao cmgDao = new CmnMyGroupDao(con);
            List<CmnMyGroupModel> cmgList = cmgDao.getMyGroupList(userSid);

            return cmgList.stream()
                    .map(cmg -> new GroupChild(cmg))
                    .collect(Collectors.toList());
        }
        return base__.answerGroupList(reqMdl, con, paramModel);
    }
    @Override
    public String answerDefaultGroup(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, List<String> groupSidList)
            throws SQLException {
        return base__.answerDefaultGroup(reqMdl, con, paramModel, groupSidList);
    }
    @Override
    public boolean isUseDetailSearch() {
        if (dspKbn__ == 1) {
            return base__.isUseDetailSearch();
        }
        return false;
    }
    /**
     * <p>dspKbn を取得します。
     * @return dspKbn
     * @see jp.groupsession.v2.sml.ui.parts.account.AccountSelector#dspKbn__
     */
    public int getDspKbn() {
        return dspKbn__;
    }
    /**
     * <p>dspKbn をセットします。
     * @param dspKbn dspKbn
     * @see jp.groupsession.v2.sml.ui.parts.account.AccountSelector#dspKbn__
     */
    public void setDspKbn(int dspKbn) {
        dspKbn__ = dspKbn;
    }
    @Override
    public void setMode(EnumModeType mode) {
        super.setMode(mode);
        base__.setMode(mode);
    }
    @Override
    public void setSelectGroupSid(String selectGroupSid) {
        super.setSelectGroupSid(selectGroupSid);
        base__.setSelectGroupSid(selectGroupSid);
    }
    @Override
    public void setSelectTargetKey(String selectTargetKey) {
        super.setSelectTargetKey(selectTargetKey);
        base__.setSelectTargetKey(selectTargetKey);
    }
    @Override
    public void setModeText(String mode) {
        super.setModeText(mode);
        base__.setModeText(mode);
    }
    @Override
    public void setMultiselectorFilter(String[] multiselectorFilter) {
        super.setMultiselectorFilter(multiselectorFilter);
        base__.setMultiselectorFilter(multiselectorFilter);
    }
}
