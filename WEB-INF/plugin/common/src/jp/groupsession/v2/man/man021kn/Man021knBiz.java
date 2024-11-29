package jp.groupsession.v2.man.man021kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnHolidayDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnHolidayModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 休日設定/追加確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man021knBiz {

    /** リクエスト情報 */
    protected RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Man021knBiz() { }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Man021knBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示画面情報を設定します
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Man021knParamModel paramMdl)
    throws SQLException {

        //日付の表示用文字列を作成
        GsMessage gsMsg = new GsMessage(reqMdl__);
        StringBuilder viewDate = new StringBuilder("");
        viewDate.append(StringUtil.getZenkaku(String.valueOf(paramMdl.getMan021HolMonth())));
        viewDate.append(gsMsg.getMessage("cmn.month"));
        viewDate.append(" ");
        viewDate.append(StringUtil.getZenkaku(String.valueOf(paramMdl.getMan021HolDay())));
        viewDate.append(gsMsg.getMessage("cmn.day"));
        paramMdl.setMan021knHolDate(viewDate.toString());
    }

    /**
     * <br>[機  能] 休日情報の新規登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void insertHoliday(Man021knParamModel paramMdl, Connection con)
    throws SQLException {

        CmnHolidayModel holMdl = createHolidayData(paramMdl);
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        holDao.insertHoliday(holMdl);

    }

    /**
     * <br>[機  能] 休日情報の更新を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行時例外
     */
    public void updateHoliday(Man021knParamModel paramMdl, Connection con)
    throws SQLException {

        CmnHolidayModel holMdl = createHolidayData(paramMdl);
        CmnHolidayDao holDao = new CmnHolidayDao(con);
        UDate targetHolDate = new UDate();
        targetHolDate.setDate(paramMdl.getEditHolDate());

        holDao.updateHoliday(holMdl, targetHolDate);
    }

    /**
     * <br>[機  能] パラメータを元に休日情報を作成する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @return CmnHolidayModel 休日情報
     */
    public CmnHolidayModel createHolidayData(Man021knParamModel paramMdl) {

        //休日日付を作成
        UDate holDate = new UDate();
        holDate.setMilliSecond(0);
        holDate.setYear(Integer.parseInt(paramMdl.getMan020DspYear()));
        holDate.setMonth(paramMdl.getMan021HolMonth());
        holDate.setDay(paramMdl.getMan021HolDay());

        UDate nowDate = new UDate();

        //セッション情報を取得
        BaseUserModel usModel = reqMdl__.getSmodel();
        int sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID

        CmnHolidayModel holMdl = new CmnHolidayModel();
        holMdl.setHolDate(holDate);
        holMdl.setHolYear(holDate.getYear());
        holMdl.setHolName(paramMdl.getMan021HolName());
        holMdl.setHolAduser(sessionUsrSid);
        holMdl.setHolAddate(nowDate);
        holMdl.setHolUpuser(sessionUsrSid);
        holMdl.setHolUpdate(nowDate);

        return holMdl;
    }
}
