package jp.groupsession.v2.wml.restapi.destination_lists;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.wml.biz.WmlGetDestListInfoBiz;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.model.WmlAddressParamModel;
import jp.groupsession.v2.wml.model.WmlDestListInfoModel;
import jp.groupsession.v2.wml.model.base.WmlDestlistModel;
import jp.groupsession.v2.wml.restapi.destination_lists.WmlDestinationListsResultModel.AddressInfo;
import jp.groupsession.v2.wml.restapi.destination_lists.WmlDestinationListsResultModel.UserInfo;
public class WmlDestinationListsGetBiz {

    /** 実行結果*/
    private final List<WmlDestinationListsResultModel> result__ = new ArrayList<>();
    /** コンテキスト */
    private final RestApiContext ctx__;
    /** コネクション */
    private final Connection con__;

    /**
     * 
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public WmlDestinationListsGetBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] 送信先リストの取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    public void execute() throws SQLException {

        //送信先リストの取得
        __getWmlDestList();

    }

    /**
     *
     * <br>[機  能] 送信先リストの取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    private void __getWmlDestList() throws SQLException {

        //送信先リストを取得
        WebmailDao wmlDao = new WebmailDao(con__);
        List<WmlDestlistModel> wdlMdlList =  wmlDao.getDestList(ctx__.getRequestUserSid());

        WmlGetDestListInfoBiz biz = new WmlGetDestListInfoBiz();

        //送信先リストに登録されているユーザ情報/アドレス情報を取得
        for (WmlDestlistModel wdlMdl : wdlMdlList) {
            WmlDestinationListsResultModel resultModel = new WmlDestinationListsResultModel();
            resultModel.setSid(wdlMdl.getWdlSid());
            resultModel.setName(wdlMdl.getWdlName());
            WmlDestListInfoModel wdlInfMdl = new WmlDestListInfoModel();
            biz.getDestlistInfo(con__, wdlMdl.getWdlSid(), wdlInfMdl);
            biz.setDestListParam(con__, wdlInfMdl);

            List<UserInfo> userArray = new ArrayList<UserInfo>();
            List<AddressInfo> addressArray = new ArrayList<AddressInfo>();

            for (WmlAddressParamModel paramMdl : wdlInfMdl.getDestUserList()) {
                if (paramMdl.getType() == WmlAddressParamModel.TYPE_USER) {
                    UserInfo usrInf = new UserInfo();
                    usrInf.setUserId(paramMdl.getSid());
                    usrInf.setSeiText(paramMdl.getSei());
                    usrInf.setMeiText(paramMdl.getMei());
                    usrInf.setSeiKanaText(paramMdl.getSeiKn());
                    usrInf.setMeiKanaText(paramMdl.getMeiKn());
                    usrInf.setMailAddressText(paramMdl.getMailAddressText());
                    usrInf.setLoginStopFlg(paramMdl.getUsrUkoFlg());
                    usrInf.setYakusyokuId(paramMdl.getYakusyokuId());
                    usrInf.setYakusyokuName(paramMdl.getYakusyoku());
                    userArray.add(usrInf);
                } else if (paramMdl.getType() == WmlAddressParamModel.TYPE_ADDRESS) {
                    AddressInfo adrInf =  new AddressInfo();
                    adrInf.setAddressSid(paramMdl.getSid());
                    adrInf.setSeiText(paramMdl.getSei());
                    adrInf.setMeiText(paramMdl.getMei());
                    adrInf.setSeiKanaText(paramMdl.getSeiKn());
                    adrInf.setMeiKanaText(paramMdl.getMeiKn());
                    adrInf.setMailAddressText(paramMdl.getMailAddressText());
                    adrInf.setYakusyokuSid(paramMdl.getYakusyokuSid());
                    adrInf.setYakusyokuName(paramMdl.getYakusyoku());
                    adrInf.setCompanyId(paramMdl.getAcoId());
                    adrInf.setCompanyName(paramMdl.getAcoName());
                    adrInf.setBaseId(paramMdl.getAbaId());
                    adrInf.setBaseName(paramMdl.getAbaName());
                    adrInf.setBaseTypeText(paramMdl.getAbaType());
                    addressArray.add(adrInf);
                }
            }
            resultModel.setUserArray(userArray);
            resultModel.setAddressArray(addressArray);
            result__.add(resultModel);
        }
    }

    /**
     *
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public List<WmlDestinationListsResultModel> getResult() {
        return result__;
    }
}