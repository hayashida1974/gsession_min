package jp.groupsession.v2.convert.convert202.user.position;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.model.base.SaibanModel;
import jp.groupsession.v2.convert.ConvertConst;
import jp.groupsession.v2.convert.convert202.dao.ConvCmnUsrmInfDao;
import jp.groupsession.v2.convert.convert202.dao.ConvPositionDao;
import jp.groupsession.v2.convert.convert202.model.ConvPositionModel;
import jp.groupsession.v2.convert.convert202.model.ConvUsrmInfModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] 役職データのコンバータ
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PositionConverter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PositionConverter.class);

    /**
     * デフォルトコンストラクタ
     */
    public PositionConverter() {
    }

    /**
     * <br>[機  能] 役職情報のコンバートを実行
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban 採番オブジェクト
     * @throws SQLException 例外
     */
    public void convert(Connection con, MlCountMtController saiban)
    throws SQLException {

        log__.debug("-- 役職データコンバート開始 --");

        //ユーザ情報から全ての役職名称を取得する
        ConvCmnUsrmInfDao cuiDao = new ConvCmnUsrmInfDao(con);
        List<ConvUsrmInfModel> cuiList = cuiDao.getAllPos();

        for (ConvUsrmInfModel cuiMdl : cuiList) {
            //役職情報を登録する
            __insertPos(con, saiban, cuiMdl);
        }

        //ユーザ情報の役職に対応する役職SIDを登録する
        cuiDao.updatePos();

        //ユーザ情報の役職(文字列)を削除する
        cuiDao.updateClearPos();

        log__.debug("-- 役職データコンバート終了 --");
    }

    /**
     * <br>[機  能] 役職情報を登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param saiban 採番オブジェクト
     * @param cuiMdl ConvUsrmInfModel
     * @throws SQLException 例外
     */
    private void __insertPos(
        Connection con,
        MlCountMtController saiban,
        ConvUsrmInfModel cuiMdl) throws SQLException {

        //役職名称
        String posName = cuiMdl.getUsiYakusyoku();

        //役職が空白の場合は登録しない
        if (NullDefault.getString(posName, "").equals("")) {
            return;
        }

        //役職マスタに存在する場合は登録しない
        ConvPositionDao cpDao = new ConvPositionDao(con);
        int count = cpDao.getPosCount(posName);
        if (count > 0) {
            return;
        }

        //役職SID採番
        int posSid = (int) saiban.getSaibanNumber(SaibanModel.SBNSID_USER,
                                                   SaibanModel.SBNSID_SUB_POS,
                                                   ConvertConst.CONV_USER_SID);
        //役職登録
        ConvPositionModel cpMdl = __getUpdateModel(posSid, posName);
        cpDao.insertPos(cpMdl);
    }

    /**
     * <br>[機  能] 登録・更新用Modelを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param posSid 役職SID
     * @param posName 役職名称
     * @return ConvPositionModel 登録・更新用Model
     */
    private ConvPositionModel __getUpdateModel(int posSid, String posName) {

        UDate now = new UDate();

        ConvPositionModel cpMdl = new ConvPositionModel();
        cpMdl.setPosSid(posSid);
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
