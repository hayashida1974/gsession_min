package jp.groupsession.v2.usr.usr032kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.convert.ConvertConst;
import jp.groupsession.v2.man.biz.MainCommonBiz;


/**
 * <br>[機  能] メイン 管理者設定 ユーザインポート確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr032knBiz {

    /**
     * <br>[機  能] 役職情報の編集を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban 採番オブジェクト
     * @param userSid ログインユーザSID
     * @throws SQLException 例外
     */
    public void posEdit(Connection con, MlCountMtController saiban, int userSid)
    throws SQLException {

        //ユーザ情報から全ての役職名称を取得する
        CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
        List<CmnUsrmInfModel> cuiList = cuiDao.getAllPos();

        for (CmnUsrmInfModel cuiMdl : cuiList) {
            //役職情報を登録する
            __insertPos(con, saiban, cuiMdl, userSid);
        }

        //ユーザ情報の役職に対応する役職SIDを登録する
        cuiDao.updatePos();

        //ユーザ情報の役職(文字列)を削除する
        cuiDao.updateClearPos();
    }

    /**
     * <br>[機  能] 役職情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban 採番オブジェクト
     * @param cuiMdl CmnUsrmInfModel
     * @param userSid ログインユーザSID
     * @throws SQLException 例外
     */
    private void __insertPos(
        Connection con,
        MlCountMtController saiban,
        CmnUsrmInfModel cuiMdl,
        int userSid) throws SQLException {

        //役職名称
        String posName = cuiMdl.getUsiYakusyoku();

        //役職が空白の場合は登録しない
        if (NullDefault.getString(posName, "").equals("")) {
            return;
        }

        //役職マスタに存在する場合は登録しない
        CmnPositionDao cpDao = new CmnPositionDao(con);
        int count = cpDao.getPosCount(posName);
        if (count > 0) {
            return;
        }

        //役職SID採番
        int posSid = (int) saiban.getSaibanNumber(SaibanModel.SBNSID_USER,
                                                   SaibanModel.SBNSID_SUB_POS,
                                                   userSid);

        MainCommonBiz biz = new MainCommonBiz();
        String posCode = biz.getAutoPosCode(con, posSid);

        //役職登録
        CmnPositionModel cpMdl = __getUpdateModel(posSid, posCode, posName);
        cpDao.insertPos(cpMdl);
    }

    /**
     * <br>[機  能] 登録・更新用Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posSid 役職SID
     * @param posCode 役職コード
     * @param posName 役職名称
     * @return CmnPositionModel 登録・更新用Model
     */
    private CmnPositionModel __getUpdateModel(int posSid, String posCode, String posName) {

        UDate now = new UDate();

        CmnPositionModel cpMdl = new CmnPositionModel();
        cpMdl.setPosSid(posSid);
        cpMdl.setPosCode(posCode);
        cpMdl.setPosName(posName);
        cpMdl.setPosBiko("");
        cpMdl.setPosSort(posSid);
        cpMdl.setPosAuid(ConvertConst.CONV_USER_SID);
        cpMdl.setPosAdate(now);
        cpMdl.setPosEuid(ConvertConst.CONV_USER_SID);
        cpMdl.setPosEdate(now);

        return cpMdl;
    }

}