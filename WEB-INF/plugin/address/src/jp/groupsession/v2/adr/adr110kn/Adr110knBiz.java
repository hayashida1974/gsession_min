package jp.groupsession.v2.adr.adr110kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.adr.adr110.Adr110BaseForm;
import jp.groupsession.v2.adr.adr110.Adr110Biz;
import jp.groupsession.v2.adr.adr110kn.dao.Adr110knDao;
import jp.groupsession.v2.adr.adr110kn.model.Adr110knModel;
import jp.groupsession.v2.adr.dao.AdrTypeindustryDao;
import jp.groupsession.v2.adr.model.AdrTypeindustryModel;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;

/**
 * <br>[機  能] アドレス帳 会社登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr110knBiz extends Adr110Biz {

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr110knBiz(RequestModel reqMdl) {
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr110knParamModel
     * @param sessionUsrSid ユーザSID
     * @throws SQLException SQL実行例外
     */
    public void getInitData(Connection con, Adr110knParamModel paramMdl, int sessionUsrSid)
    throws SQLException {
        //遷移元画面がアドレス帳一覧画面の場合、会社情報をDBから読み込む
        if (paramMdl.getAdr100backFlg() == 2) {
            _readCompanyData(con, paramMdl);
        }

        //会社情報 都道府県名を取得する
        int tdfkCode = paramMdl.getAdr110coTdfk();
        if (tdfkCode > 0) {
            CmnTdfkDao tdfkDao = new CmnTdfkDao(con);
            CmnTdfkModel tdfkMdl = tdfkDao.select(tdfkCode);
            if (tdfkMdl != null) {
                paramMdl.setAdr110knViewCoTdfkName(tdfkMdl.getTdfName());
            }
        }

        //所属業種名称を取得する
        String[] atiSidList = paramMdl.getAdr110atiList();
        if (atiSidList != null) {
            ArrayList<String> atiNameList = new ArrayList<String>();
            AdrTypeindustryDao industryDao = new AdrTypeindustryDao(con);
            ArrayList<String> sidList = new ArrayList<String>(Arrays.asList(atiSidList));
            
            for (int index = 0; index < atiSidList.length; index++) {
                AdrTypeindustryModel industryMdl
                    = industryDao.select(Integer.parseInt(atiSidList[index]));
                if (industryMdl != null) {
                    atiNameList.add(industryMdl.getAtiName());
                } else {
                    sidList.remove(atiSidList[index]);
                }
            }
            String[] atiName = new String[atiNameList.size()];
            atiName = atiNameList.toArray(atiName);
            paramMdl.setAdr110knViewAtiList(atiName);
            paramMdl.setAdr110atiList(sidList.toArray(new String[0]));
        }

        //備考の設定を行う
        paramMdl.setAdr110knViewBiko(
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(paramMdl.getAdr110biko(), "")));

        //会社拠点情報の都道府県名を設定する
        List<Adr110BaseForm> baseList = paramMdl.getAbaListToList();
        if (baseList != null) {
            CmnTdfkDao tdfkDao = new CmnTdfkDao(con);

            for (Adr110BaseForm baseForm : baseList) {
                int tdfkSid = baseForm.getAdr110abaTdfk();
                CmnTdfkModel tdfkMdl = tdfkDao.select(tdfkSid);
                if (tdfkMdl != null) {
                    baseForm.setAdr110abaTdfkName(tdfkMdl.getTdfName());
                }
            }
        }

        //アドレス情報を取得する。
        Adr110knDao adr110knDao = new Adr110knDao(con);
        List<Adr110knModel> adrInfList
                =  adr110knDao.getUserListBelongCompany(
                        paramMdl.getAdr110editAcoSid(), sessionUsrSid);

        paramMdl.setAdr110knAdrInfList(adrInfList);
        paramMdl.setAdr110knAdrCount(String.valueOf(adrInfList.size()));

    }

}
