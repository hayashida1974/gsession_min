package jp.groupsession.v2.adr.adr020kn;

import static jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_GROUP;
import static jp.groupsession.v2.adr.GSConstAddress.EDITPERMIT_USER;
import static jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_GROUP;
import static jp.groupsession.v2.cmn.GSConst.ADR_VIEWPERMIT_USER;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.adr.adr020.Adr020Biz;
import jp.groupsession.v2.adr.biz.AddressBiz;
import jp.groupsession.v2.adr.dao.AdrPositionDao;
import jp.groupsession.v2.adr.model.AdrPositionModel;
import jp.groupsession.v2.cmn.biz.UserBiz;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnTdfkDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnTdfkModel;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] アドレス帳登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Adr020knBiz extends Adr020Biz {

    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Adr020knBiz.class);

    /** リクエスト */
    protected RequestModel reqMdl_ = null;

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl RequestModel
     */
    public Adr020knBiz(RequestModel reqMdl) {
        super(reqMdl);
        reqMdl_ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl Adr020knParamModel
     * @param userMdl セッションユーザ情報
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     */
    public void setInitData(Connection con, Adr020knParamModel paramMdl,
                            BaseUserModel userMdl)
    throws IOException, IOToolsException, SQLException {

        log__.debug("START");

        //アドレス帳確認フラグ = 1:確認のみ の場合、DBからアドレス帳情報を読み込む
        if (paramMdl.getAdr020viewFlg() == 1) {
            _setAddressData(con, paramMdl);
        }

        //役職名称を設定
        if (paramMdl.getAdr020position() > 0) {
            AdrPositionDao positionDao = new AdrPositionDao(con);
            AdrPositionModel positionModel = positionDao.select(paramMdl.getAdr020position());
            if (positionModel != null) {
                paramMdl.setAdr020knPositionName(positionModel.getApsName());
            } else {
                paramMdl.setAdr020position(0);
            }
        }

        //都道府県名称を設定
        if (paramMdl.getAdr020tdfk() > 0) {
            CmnTdfkDao tdfkDao = new CmnTdfkDao(con);
            CmnTdfkModel tdfkModel = tdfkDao.select(paramMdl.getAdr020tdfk());
            paramMdl.setAdr020knTdfkName(tdfkModel.getTdfName());
        }

        //備考(表示用)を設定
        if (!StringUtil.isNullZeroString(paramMdl.getAdr020biko())) {
            paramMdl.setAdr020knViewBiko(
                    StringUtilHtml.transToHTmlPlusAmparsant(
                            paramMdl.getAdr020biko()));
        }

        AddressBiz addressBiz = new AddressBiz(reqMdl_);

        //会社名を設定
        _setCompanyData(con, paramMdl);

        //担当者を設定
        UserBiz userBiz = new UserBiz();
        paramMdl.setAdr020knTantoNameList(
                    addressBiz.getUserNameList(con, paramMdl.getAdr020tantoList()));
        List<UsrLabelValueBean> userLabelList =
                userBiz.getUserLabelList(con, paramMdl.getAdr020tantoList());
        paramMdl.setSelectTantoCombo(userLabelList);
        ArrayList<String> sidList = new ArrayList<String>();
        for (LabelValueBean bean : userLabelList) {
            sidList.add(bean.getValue());
        }
        paramMdl.setAdr020tantoList(sidList.toArray(new String[0]));

        //ラベル情報一覧を設定
        _setLabelList(con, paramMdl);



        //閲覧グループを設定
        int permitView = paramMdl.getAdr020permitView();
        if (permitView == ADR_VIEWPERMIT_GROUP) {
            List<LabelValueBean> labelList =
                    addressBiz.getGroupLabelList(con, paramMdl.getAdr020permitViewGroup());
            List<String> nameList = new ArrayList<String>();
            sidList = new ArrayList<String>();
            for (LabelValueBean bean : labelList) {
                nameList.add(bean.getLabel());
                sidList.add(bean.getValue());
            }
            paramMdl.setAdr020knPermitViewList(nameList);
            paramMdl.setAdr020permitViewGroup(sidList.toArray(new String[0]));

        }

        //閲覧ユーザを設定
        if (permitView == ADR_VIEWPERMIT_USER) {
            userLabelList =
                    userBiz.getUserLabelList(con, paramMdl.getAdr020permitViewUser());
            sidList = new ArrayList<String>();
            for (LabelValueBean bean : userLabelList) {
                sidList.add(bean.getValue());
            }
            paramMdl.setSelectPermitViewUser(userLabelList);

            paramMdl.setAdr020permitViewUser(sidList.toArray(new String[0]));
        }

        //編集グループを設定
        int permitEdit = paramMdl.getAdr020permitEdit();
        if (permitView == ADR_VIEWPERMIT_GROUP || permitEdit == EDITPERMIT_GROUP) {

            List<LabelValueBean> labelList =
                    addressBiz.getGroupLabelList(con, paramMdl.getAdr020permitEditGroup());
            List<String> nameList = new ArrayList<String>();
            sidList = new ArrayList<String>();
            for (LabelValueBean bean : labelList) {
                nameList.add(bean.getLabel());
                sidList.add(bean.getValue());
            }
            paramMdl.setAdr020knPermitEditList(nameList);
            paramMdl.setAdr020permitEditGroup(sidList.toArray(new String[0]));

        }

        //編集ユーザを設定
        if (permitView == ADR_VIEWPERMIT_USER || permitEdit == EDITPERMIT_USER) {
            userLabelList =
                    userBiz.getUserLabelList(con, paramMdl.getAdr020permitEditUser());
            sidList = new ArrayList<String>();
            for (LabelValueBean bean : userLabelList) {
                sidList.add(bean.getValue());
            }
            paramMdl.setSelectPermitEditUser(userLabelList);
            paramMdl.setAdr020permitEditUser(sidList.toArray(new String[0]));

        }

        log__.debug("End");
    }
}
