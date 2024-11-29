package jp.groupsession.v2.adr.ui.parts.address;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrAddressDao;
import jp.groupsession.v2.adr.dao.AdrCompanyDao;
import jp.groupsession.v2.adr.model.AdrAddressModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.AbstractSelector;
import jp.groupsession.v2.cmn.ui.parts.select.Child;
import jp.groupsession.v2.cmn.ui.parts.select.IChild;
import jp.groupsession.v2.cmn.ui.parts.select.SelectorFactory;
import jp.groupsession.v2.struts.msg.GsMessage;

public class AdrAddressSelector extends AbstractSelector {
    /**
     *
     * コンストラクタ
     * @param param
     */
    protected AdrAddressSelector(ParamForm param) {
       super(param);
    }

    /**
     *
     * <br>[機  能] ビルダークラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    public static class ParamForm extends SelectorFactory<AdrAddressSelector, ParamForm> {
        public ParamForm(Class<AdrAddressSelector> targetClz) {
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
        ParamForm ret = new ParamForm(AdrAddressSelector.class);
        return ret;
    }

    @Override
    public List<IChild> answerGroupList(RequestModel reqMdl, Connection con,
                                        ParameterObject paramModel) throws SQLException {

        //会社情報一覧取得
        AdrCompanyDao comDao = new AdrCompanyDao(con);

        List<IChild> grpList = new ArrayList<>();
        grpList.addAll(
                comDao.getSelectComList()
                    .stream()
                    .map(grp -> new Child(
                            grp.getAcoName(),
                            String.valueOf(grp.getAcoSid())
                            )
                         )
                    .collect(Collectors.toList())
                );


        //先頭に「未所属」を追加
        GsMessage gsMsg = new GsMessage(reqMdl);
        grpList.add(0, new Child(gsMsg.getMessage("address.src.34"), "0"));

        return grpList;
    }

    @Override
    public List<IChild> answerSelectedList(RequestModel reqMdl, Connection con,
           ParameterObject paramModel, List<String> selectedSidList) throws SQLException {

        int sessionUsrSid = reqMdl.getSmodel().getUsrsid();
        AddressBiz adrBiz = new AddressBiz(reqMdl);
        boolean admFlg = adrBiz.isAdmin(con, sessionUsrSid);

        AdrAddressDao adrDao = new AdrAddressDao(con);
        ArrayList<AdrAddressModel> selectUserList =
            adrDao.selectViewAdrList(selectedSidList.toArray(new String[0]),
                                    sessionUsrSid, admFlg);

        List<IChild> ret = new ArrayList<>();
        ret.addAll(selectUserList
                    .stream()
                    .map(mdl -> __createAddressChild(mdl))
                    .collect(Collectors.toList())
               );
        return ret;
    }

    @Override
    public List<IChild> answerSelectionList(RequestModel reqMdl, Connection con,
            ParameterObject paramModel, String selectedGrpSid,
            List<String> selectedSidList) throws SQLException {

        //会社SID
        int selectCom = (Integer) paramModel.get("adr170SelectedComComboSid");

        //未選択ユーザ
        int myAdrSid = (Integer) paramModel.get("adr010EditAdrSid");

        AdrAddressDao adrDao = new AdrAddressDao(con);
        ArrayList<AdrAddressModel> userList =
            adrDao.selectAcoUsrList(selectedSidList.toArray(new String[0]),
                                    myAdrSid, selectCom,
                                    reqMdl.getSmodel().getUsrsid());

        List<IChild> ret = new ArrayList<>();
        ret.addAll(userList
                    .stream()
                    .map(mdl -> __createAddressChild(mdl))
                    .collect(Collectors.toList())
                );
        return ret;
    }

    /**
     * <br>[機  能] アドレス帳情報から選択UIの子要素を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param mdl アドレス帳情報
     * @return 選択UIの子要素
     */
    private Child __createAddressChild(AdrAddressModel mdl) {
        return new Child(
                mdl.getAdrSei() + mdl.getAdrMei(),
                String.valueOf(mdl.getAdrSid())
                );
    }
}
