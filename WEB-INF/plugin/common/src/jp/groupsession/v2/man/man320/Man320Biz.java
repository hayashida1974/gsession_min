package jp.groupsession.v2.man.man320;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.PageUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.information.CmnInfoMsgBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.base.CmnInfoMsgDao;
import jp.groupsession.v2.cmn.dao.base.CmnInfoUserDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnInfoMsgModel;
import jp.groupsession.v2.man.GSConstMain;
import jp.groupsession.v2.man.MaintenanceUtil;
import jp.groupsession.v2.man.man320.model.Man320DspModel;
import jp.groupsession.v2.struts.msg.GsMessage;


/**
 * <br>[機  能] メイン インフォメーション一覧画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man320Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man320Biz.class);

    /** ソート メッセージ */
    public static final int SORT_KEY_MSG = 0;
    /** ソート 開始日時 */
    public static final int SORT_KEY_FRDATE = 1;
    /** ソート 終了日時 */
    public static final int SORT_KEY_TODATE = 2;
    /** ソート 実行ユーザ */
    public static final int SORT_KEY_USR_NAME = 3;
    /** 警告フラグ 期限内 */
    public static final int ALERT_FLG_KIGEN_OK = 0;
    /** 警告フラグ 期限切れ */
    public static final int ALERT_FLG_KIGEN_NG = 1;

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param pconfig プラグイン設定情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時エラー
     */
    public void setInitData(Man320ParamModel paramMdl, Connection con, PluginConfig pconfig,
                            RequestModel reqMdl)
    throws SQLException {

        CmnInfoMsgDao msgDao = new CmnInfoMsgDao(con);
        int maxCount = msgDao.getAllCount();
        int limit = 20;
        //現在ページ、スタート行
        int nowPage = paramMdl.getMan320PageNum();
        int start = PageUtil.getRowNumber(nowPage, limit);

        //ページあふれ制御
        int maxPageNum = PageUtil.getPageCount(maxCount, limit);
        int maxPageStartRow = PageUtil.getRowNumber(maxPageNum, limit);
        if (maxPageStartRow < start) {
            nowPage = maxPageNum;
            start = maxPageStartRow;
            paramMdl.setMan320PageNum(nowPage);
        }

        //メッセージ一覧を取得
        ArrayList<Man320DspModel> msgList = msgDao.select(
                start, limit, paramMdl.getMan320SortKey(), paramMdl.getMan320OrderKey(), reqMdl);
        paramMdl.setMan320DspList(msgList);
        //ページコンボ
        paramMdl.setMan320SltPage1(nowPage);
        paramMdl.setMan320SltPage2(nowPage);
        paramMdl.setMan320PageLabel(PageUtil.createPageOptions(maxCount, limit));

    }

    /**
     * <br>[機  能] インフォメーションの拡張設定文字を生成する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param bean Man320DspModel
     * @return String 拡張設定文字
     */
    public static String getInfoExString(RequestModel reqMdl, Man320DspModel bean) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        StringBuilder buf = new StringBuilder();
        if (bean.getImsKbn() == GSConstMain.INFO_KBN_DAY) {
            buf.append(gsMsg.getMessage(GSConstMain.INFO_KBN_DAYSTRING));

        } else if (bean.getImsKbn() == GSConstMain.INFO_KBN_WEEK) {
            buf.append(gsMsg.getMessage(GSConstMain.INFO_KBN_WEEKSTRING));
            buf.append(" ");
            if (bean.getImsDweek1() == 1) {
                buf.append(gsMsg.getMessage("cmn.sunday2") + " ");
            }
            if (bean.getImsDweek2() == 1) {
                buf.append(gsMsg.getMessage("cmn.monday2") + " ");
            }
            if (bean.getImsDweek3() == 1) {
                buf.append(gsMsg.getMessage("cmn.tuesday2") + " ");
            }
            if (bean.getImsDweek4() == 1) {
                buf.append(gsMsg.getMessage("cmn.wednesday2") + " ");
            }
            if (bean.getImsDweek5() == 1) {
                buf.append(gsMsg.getMessage("cmn.thursday2") + " ");
            }
            if (bean.getImsDweek6() == 1) {
                buf.append(gsMsg.getMessage("main.src.man290kn.7") + " ");
            }
            if (bean.getImsDweek7() == 1) {
                buf.append(gsMsg.getMessage("cmn.saturday2") + " ");
            }
        } else if (bean.getImsKbn() == GSConstMain.INFO_KBN_MONTH) {
            buf.append(gsMsg.getMessage(GSConstMain.INFO_KBN_MONTHSTRING));
            buf.append(" ");
            if (bean.getImsDay() > 0) {
                 if (bean.getImsDay() == GSConstCommon.LAST_DAY_OF_MONTH) {
                    buf.append(gsMsg.getMessage("tcd.tcd050kn.01"));
                } else {
                    buf.append(bean.getImsDay() + gsMsg.getMessage("cmn.day"));
                }
            } else {
                buf.append(MaintenanceUtil.getWeek(bean.getImsWeek(), reqMdl));

                buf.append(" ");
                if (bean.getImsDweek1() == 1) {
                    buf.append(gsMsg.getMessage("cmn.sunday2") + " ");
                }
                if (bean.getImsDweek2() == 1) {
                    buf.append(gsMsg.getMessage("cmn.monday2") + " ");
                }
                if (bean.getImsDweek3() == 1) {
                    buf.append(gsMsg.getMessage("cmn.tuesday2") + " ");
                }
                if (bean.getImsDweek4() == 1) {
                    buf.append(gsMsg.getMessage("cmn.wednesday2") + " ");
                }
                if (bean.getImsDweek5() == 1) {
                    buf.append(gsMsg.getMessage("cmn.thursday2") + " ");
                }
                if (bean.getImsDweek6() == 1) {
                    buf.append(gsMsg.getMessage("main.src.man290kn.7") + " ");
                }
                if (bean.getImsDweek7() == 1) {
                    buf.append(gsMsg.getMessage("cmn.saturday2") + " ");
                }
            }

        }

        return buf.toString();
    }

    /**
     * <br>[機  能] インフォメーション登録設定を行う権限があるか判定する
     * <br>[解  説] システム管理グループに所属又は、管理者設定で許可されたユーザ又はグループに所属
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return true:権限あり false:権限なし
     * @throws SQLException SQL実行時例外
     */
    public boolean isInfoAdminAuth(RequestModel reqMdl, Connection con) throws SQLException {
        //セッション情報を取得
        BaseUserModel usModel = reqMdl.getSmodel();
        int sessionUsrSid = -1;
        if (usModel != null) {
            sessionUsrSid = usModel.getUsrsid(); //セッションユーザSID
            //システム管理者
            if (usModel.isAdmin()) {
                return true;
            }
            //登録許可を受けたユーザ
            CmnInfoUserDao usrDao = new CmnInfoUserDao(con);
            if (usrDao.isExistUser(sessionUsrSid)) {
                //許可済み
                return true;
            }
            //登録許可を受けたグループに所属
            if (usrDao.isBelongGroupSid(sessionUsrSid)) {
                //許可済み
                return true;
            }
        }

        return false;
    }

    /**
     * <br>[機  能] 指定したメッセージが閲覧可能なものか判定します。
     * <br>[解  説]
     * <br>[備  考]
     * @param userSid ユーザSID
     * @param date 日時
     * @param imssid インフォメーションSID
     * @param con コネクション
     * @return true:可能 false:不可能
     * @throws SQLException SQL実行時例外
     */
    public boolean isDspAuthMsg(int userSid, UDate date, int imssid, Connection con)
    throws SQLException {
        CmnInfoMsgBiz biz = new CmnInfoMsgBiz();
        ArrayList<CmnInfoMsgModel> list = biz.getActiveInformationMsg(userSid, date, con);
        for (CmnInfoMsgModel bean : list) {
            log__.debug("インフォメーション==>" + bean.getImsSid() + ":" + bean.getImsMsg());
            if (bean.getImsSid() == imssid) {
                return true;
            }
        }
        return false;
    }

    /**
     * <br>[機  能] メッセージに表示するインフォメーションのタイトルを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param infoSidList インフォメーションSID
     * @return メッセージ表示用のTODOタイトル
     * @throws SQLException SQL実行時例外
     */
    public String getMsgInfoTitle(Connection con, String[] infoSidList)
    throws SQLException {

        CmnInfoMsgDao cimDao = new CmnInfoMsgDao(con);
        List<CmnInfoMsgModel> titleList = cimDao.selectInfoSids(infoSidList);

        String msgTitle = "";
        for (int idx = 0; idx < titleList.size(); idx++) {

            //最初の要素以外は改行を挿入
            if (idx > 0) {
                msgTitle += "<br>";
            }

            msgTitle += "・ " + StringUtilHtml.transToHTmlPlusAmparsant(
                            NullDefault.getString(titleList.get(idx).getImsMsg(), ""));
        }

        return msgTitle;
    }

}