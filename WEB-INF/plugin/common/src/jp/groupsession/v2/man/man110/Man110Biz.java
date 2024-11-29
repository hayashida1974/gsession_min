package jp.groupsession.v2.man.man110;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.dao.base.CmnPositionDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnPositionModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.biz.MainCommonBiz;
import jp.groupsession.v2.man.man100.Man100Biz;
import jp.groupsession.v2.struts.msg.GsMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.MessageResources;

/**
 * <br>[機  能] メイン 管理者設定 役職登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man110Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man110Biz.class);

    /**
     * <br>[機  能] 初期表示情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void getInitData(Connection con, Man110ParamModel paramMdl) throws SQLException {

        if (paramMdl.getMan100ProcMode() == Man100Biz.MODE_ADD) {

            //自動コード生成
            MainCommonBiz biz = new MainCommonBiz();
            String code = biz.getAutoPosCode(con);
            paramMdl.setMan110posCode(code);

            return;
        }

        //編集の場合、役職情報取得
        int editPosSid = paramMdl.getMan100EditPosSid();
        CmnPositionDao cpDao = new CmnPositionDao(con);
        CmnPositionModel cpMdl = cpDao.getPosInfo(editPosSid);
        if (cpMdl == null) {
            return;
        }

        //役職情報を画面にセット
        paramMdl.setMan110posCode(cpMdl.getPosCode());
        paramMdl.setMan110posName(cpMdl.getPosName());
        paramMdl.setMan110bikou(cpMdl.getPosBiko());
    }

    /**
     * <br>[機  能] 役職SIDから役職情報を取得し、削除確認メッセージを返す
     * <br>[解  説] 役職に所属するユーザが存在する場合は、その人数を表示
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param posSid 役職SID
     * @param msgRes MessageResources
     * @return String 削除確認メッセージ
     * @throws SQLException SQL実行例外
     */
    public String getDeletePosMsg(Connection con,
            RequestModel reqMdl, int posSid, MessageResources msgRes)
    throws SQLException {

        String posMsg = "";

        //役職名称を取得
        PosBiz pBiz = new PosBiz();
        String posName = pBiz.getPosName(con, posSid);

        //所属しているユーザの件数を取得する
        CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
        int count = cuiDao.getPosCount(posSid);

        GsMessage gsMsg = new GsMessage(reqMdl);
        if (count > 0) {
            //所属ユーザあり
            posMsg = msgRes.getMessage(
                    "sakujo.kakunin.list.user",
                    gsMsg.getMessage(GSConstMain.POS_MSG),
                    StringUtilHtml.transToHTmlPlusAmparsant(posName),
                    count);
        } else {
            //所属ユーザなし
            posMsg = msgRes.getMessage("sakujo.kakunin.list",
                                   gsMsg.getMessage(GSConstMain.POS_MSG),
                                   StringUtilHtml.transToHTmlPlusAmparsant(posName));
        }

        return posMsg;
    }

    /**
     * <br>[機  能] 役職を削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行例外
     */
    public void deletePos(Connection con, Man110ParamModel paramMdl) throws SQLException {

        int editPosSid = paramMdl.getMan100EditPosSid();
        boolean commitFlg = false;

        try {

            //役職情報を物理削除する
            CmnPositionDao cpDao = new CmnPositionDao(con);
            cpDao.deletePos(editPosSid);

            //ユーザ情報を更新
            CmnUsrmInfDao cuiDao = new CmnUsrmInfDao(con);
            cuiDao.crearPosSid(editPosSid);

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;
        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }
}
