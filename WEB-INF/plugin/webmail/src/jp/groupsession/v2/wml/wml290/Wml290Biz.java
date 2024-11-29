package jp.groupsession.v2.wml.wml290;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlGetDestListInfoBiz;
import jp.groupsession.v2.wml.dao.WebmailDao;
import jp.groupsession.v2.wml.model.WmlAddressParamModel;
import jp.groupsession.v2.wml.model.WmlDestListInfoModel;
import jp.groupsession.v2.wml.model.base.WmlDestlistModel;
import jp.groupsession.v2.wml.wml280.Wml280Biz;

import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] WEBメール 送信先リスト選択画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml290Biz extends Wml280Biz {

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con, Wml290ParamModel paramMdl, RequestModel reqMdl)
    throws SQLException {

        //送信先リスト一覧を取得する
        List<LabelValueBean> destlistCombo = new ArrayList<LabelValueBean>();
        WebmailDao webmailDao = new WebmailDao(con);
        List<WmlDestlistModel> destList = webmailDao.getDestList(reqMdl.getSmodel().getUsrsid());
        for (WmlDestlistModel destlistData : destList) {
            LabelValueBean destLabel
                    = new LabelValueBean(destlistData.getWdlName(),
                                                    String.valueOf(destlistData.getWdlSid()));
            destlistCombo.add(destLabel);
        }
        paramMdl.setWml290destlistCombo(destlistCombo);

        if (paramMdl.getWmlEditDestList() > 0) {

            WmlGetDestListInfoBiz biz = new WmlGetDestListInfoBiz();

            //送信先リスト情報を取得
            WmlDestListInfoModel mdl = new WmlDestListInfoModel();
            biz.getDestlistInfo(con, paramMdl.getWmlEditDestList(), mdl);
            _setDestListInfo(paramMdl, mdl);

            //送信先を設定
            _setParam(paramMdl, mdl);
            biz.setDestListParam(con, mdl);
            paramMdl.setDestUserList(mdl.getDestUserList());
            //初期表示の場合、送信先を全選択状態とする
            if (paramMdl.getWml290initFlg() != 1) {
                String[] destSidList = new String[paramMdl.getDestUserList().size()];
                WmlAddressParamModel addressParam = null;
                for (int idx = 0; idx < destSidList.length; idx++) {
                    addressParam = paramMdl.getDestUserList().get(idx);
                    destSidList[idx] = _createDestAddressId(addressParam.getType(),
                                                                        addressParam.getSid(),
                                                                        addressParam.getMailNo());
                }

                paramMdl.setWml280destUserSelect(destSidList);
            }
        }

        paramMdl.setWml290initFlg(1);
    }

    /**
     * <br>[機  能] 設定アドレスを設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     */
    public void setDestAddressList(Wml290ParamModel paramMdl) {
        List<String> addressList = new ArrayList<String>();
        List<WmlAddressParamModel> destAddressDataList = paramMdl.getDestUserList();
        String[] selectUserList = paramMdl.getWml280destUserSelect();
        if (selectUserList != null) {
            for (WmlAddressParamModel destAddressData : destAddressDataList) {
                for (String selectDestId : selectUserList) {
                    if (destAddressData.getDestId().equals(selectDestId)) {
                        addressList.add(destAddressData.getSendMailAddress());
                        break;
                    }
                }
            }
        }
        paramMdl.setWml290destAddressList(addressList);
    }
}
