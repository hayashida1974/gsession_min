package jp.groupsession.v2.cmn.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] 役職関連で使用するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class PosBiz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(PosBiz.class);

    /**
     * <br>[機  能] 役職SIDから役職名称を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param posSid 役職SID
     * @return String 役職名称
     * @throws SQLException SQL実行例外
     */
    public String getPosName(Connection con, int posSid) throws SQLException {

        CmnPositionDao cpDao = new CmnPositionDao(con);
        CmnPositionModel cpMdl = cpDao.getPosInfo(posSid);
        if (cpMdl == null) {
            return null;
        }
        return cpMdl.getPosName();
    }

    /**
     * <br>[機  能] 役職SIDから役職が存在するかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param posSid 役職SID
     * @return boolean true=存在する、false=存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existPos(Connection con, int posSid) throws SQLException {

        CmnPositionDao cpDao = new CmnPositionDao(con);
        CmnPositionModel cpMdl = cpDao.getPosInfo(posSid);
        if (cpMdl == null) {
            return false;
        }
        return true;
    }

    /**
     * <br>[機  能] 役職名称から役職が存在するかチェック
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param posName 役職名称
     * @return boolean true=存在する、false=存在しない
     * @throws SQLException SQL実行例外
     */
    public boolean existPosName(Connection con, String posName) throws SQLException {

        CmnPositionDao cpDao = new CmnPositionDao(con);
        int count = cpDao.getPosCount(posName);
        if (count < 1) {
            return false;
        }
        return true;
    }
    /**
     * <br>[機  能] 役職コンボ用のリストを取得する
     * <br>[解  説] 未設定ラベルを含む
     * <br>[備  考]
     * @param con コネクション
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getPosLabelList(Connection con) throws SQLException {
        return getPosLabelList(con, true);
    }
    /**
     * <br>[機  能] 役職コンボ用のリストを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param hasMisettei 未設定ラベルを含む
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getPosLabelList(
            Connection con, boolean hasMisettei) throws SQLException {

        ArrayList<LabelValueBean> labelList = new ArrayList<LabelValueBean>();

        //役職リスト取得
        CmnPositionDao cpDao = new CmnPositionDao(con);
        List<CmnPositionModel> cpList = cpDao.getPosList(hasMisettei);

        for (CmnPositionModel cpMdl : cpList) {
            labelList.add(
                    new LabelValueBean(cpMdl.getPosName(), String.valueOf(cpMdl.getPosSid())));
        }
        log__.debug("labelList.size()=>" + labelList.size());
        return labelList;
    }

    /**
     * <br>[機  能] 役職コンボ用のリストを取得する(検索用)
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param gsMsg GSメッセージインスタンス
     * @return ArrayList
     * @throws SQLException SQL実行時例外
     */
    public ArrayList<LabelValueBean> getPosSearchLabelList(
            Connection con, GsMessage gsMsg)
    throws SQLException {

        //選択してください
        String textSelect = gsMsg.getMessage("cmn.select.plz");

        ArrayList<LabelValueBean> labelList = getPosLabelList(con);
        labelList.add(0, new LabelValueBean(
                textSelect, String.valueOf(GSConstCommon.NUM_INIT)));

        return labelList;
    }

}
