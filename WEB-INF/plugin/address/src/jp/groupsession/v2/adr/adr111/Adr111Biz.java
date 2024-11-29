package jp.groupsession.v2.adr.adr111;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.adr.GSConstAddress;
import jp.groupsession.v2.adr.adr110.Adr110BaseForm;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] アドレス帳 拠点登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr111Biz {

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr111Biz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr111ParamModel
     * @throws SQLException SQL実行例外
     */
    public void getInitData(Connection con, Adr111ParamModel paramMdl)
    throws SQLException {

        //都道府県コンボを設定
        CommonBiz cmnBiz = new CommonBiz();
        GsMessage gsMsg = new GsMessage(reqMdl_);
        paramMdl.setTdfkCmbList(cmnBiz.getTdfkLabelList(con, gsMsg));

        //拠点種別コンボを設定
        ArrayList<LabelValueBean> abaTypeList = new ArrayList<LabelValueBean>();
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("cmn.select.plz"), "-1"));
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("address.122"),
                                        String.valueOf(GSConstAddress.ABATYPE_HEADOFFICE)));
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("address.123"),
                                        String.valueOf(GSConstAddress.ABATYPE_BRANCH)));
        abaTypeList.add(new LabelValueBean(gsMsg.getMessage("address.124"),
                                        String.valueOf(GSConstAddress.ABATYPE_BUSINESSOFFICE)));
        paramMdl.setAbaTypeList(abaTypeList);

        if (paramMdl.getAdr111initFlg() == 0) {
            int editIndex = paramMdl.getAdr111editCompanyBaseIndex();
            for (Adr110BaseForm baseForm : paramMdl.getAbaList()) {
                if (baseForm.getAdr110abaIndex() == editIndex) {
                    //会社拠点種別
                    paramMdl.setAdr111abaType(baseForm.getAdr110abaTypeDetail());
                    //支店・営業所名
                    paramMdl.setAdr111abaName(baseForm.getAdr110abaName());
                    //郵便番号上3桁
                    paramMdl.setAdr111abaPostno1(baseForm.getAdr110abaPostno1());
                    //郵便番号下4桁
                    paramMdl.setAdr111abaPostno2(baseForm.getAdr110abaPostno2());
                    //都道府県
                    int tdfkSid = baseForm.getAdr110abaTdfk();
                    paramMdl.setAdr111abaTdfk(tdfkSid);
                    //住所１
                    paramMdl.setAdr111abaAddress1(baseForm.getAdr110abaAddress1());
                    //住所２
                    paramMdl.setAdr111abaAddress2(baseForm.getAdr110abaAddress2());
                    //備考
                    paramMdl.setAdr111abaBiko(baseForm.getAdr110abaBiko());
                }
            }

            paramMdl.setAdr111initFlg(1);
        }

    }
}
