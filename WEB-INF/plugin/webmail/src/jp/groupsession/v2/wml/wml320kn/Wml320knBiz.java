package jp.groupsession.v2.wml.wml320kn;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.WmlDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlDelmailListDao;
import jp.groupsession.v2.wml.dao.base.WmlDelmailTempDao;
import jp.groupsession.v2.wml.dao.base.WmlLabelDao;
import jp.groupsession.v2.wml.wml010.Wml010Const;
import jp.groupsession.v2.wml.wml320.Wml320Biz;
import jp.groupsession.v2.wml.wml320.Wml320Form;

/**
 * <br>[機  能] WEBメール 個人設定 メール情報一括削除確認画面のビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 */
public class Wml320knBiz {

    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクター
     */
    public Wml320knBiz() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Wml320knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param buMdl セッションユーザーモデル
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    protected void _setInitData(
            Wml320knParamModel paramMdl, BaseUserModel buMdl,
            RequestModel reqMdl) throws SQLException {

        //アカウント名取得
        int wacSid = paramMdl.getWml320svAccount();
        WmlBiz wmlBiz = new WmlBiz();
        String account = wmlBiz.getAccountName(con__, wacSid);
        paramMdl.setWml320knAccount(account);

        //ラベル名取得
        int wlbSid = paramMdl.getWml320svLabel();
        if (wlbSid > 0) {
            WmlLabelDao dao = new WmlLabelDao(con__);
            String labelName = dao.getLabelName(wlbSid);
            paramMdl.setWml320knLabel(labelName);
        }

        //選択アカウントの一括削除実行中メール件数を設定
        Wml320Biz biz320 = new Wml320Biz(con__, reqMdl);
        paramMdl.setDeleteMailCount(
                biz320.getDeleteMailCount(wacSid));
    }

    /**
     * <br>[機  能] パラメータチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param buMdl セッションユーザーモデル
     * @param reqMdl リクエストモデル
     * @return true: 正常, false: パラメータエラー
     * @throws SQLException SQL実行時例外
     */
    public boolean checkParameter(Wml320knParamModel paramMdl,
            BaseUserModel buMdl,
            RequestModel reqMdl) throws SQLException {

        //アカウント使用可能チェック
        int wacSid = paramMdl.getWml320svAccount();
        WmlDao wmlDao = new WmlDao(con__);
        boolean existFlg = wmlDao.canUseAccount(wacSid, buMdl.getUsrsid());
        if (!existFlg) {
            return false;
        }

        //ラベル存在チェック
        int wlbSid = paramMdl.getWml320svLabel();
        if (wlbSid > 0) {
            WmlLabelDao dao = new WmlLabelDao(con__);
            if (dao.checkLabel(wacSid, wlbSid) <= 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * <br>[機  能] 一時保管情報をメール情報一括削除情報に登録する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータ情報
     * @param buMdl セッションユーザーモデル
     * @throws SQLException SQL実行時例外
     */
    public void entryDelmailList(RequestModel reqMdl, Wml320knParamModel paramMdl,
                                    BaseUserModel buMdl)
    throws SQLException {
        //一時保管情報をメール情報一括削除情報に登録
        WmlDelmailListDao delListDao = new WmlDelmailListDao(con__);
        delListDao.insertTempData(buMdl.getUsrsid());

        //一時保管情報を削除する
        WmlDelmailTempDao delTempDao = new WmlDelmailTempDao(con__);
        delTempDao.delete(buMdl.getUsrsid());
    }


    /**
     * <br>[機  能] ログメッセージ作成
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param paramMdl パラメータ情報
     * @param buMdl セッションユーザーモデル
     * @throws SQLException SQL実行時例外
     * @return ログメッセージ
     */
    protected String _getLogMessage(RequestModel reqMdl, Wml320knParamModel paramMdl,
                                    BaseUserModel buMdl)
    throws SQLException {

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msg = "";
        //アカウント名取得
        WmlBiz wmlBiz = new WmlBiz();
        String accountName = wmlBiz.getAccountName(con__, paramMdl.getWml320svAccount());
        //アカウント
        msg += "[" + gsMsg.getMessage("wml.96") + "]" + accountName;
        //ディレクトリ
        msg += "\r\n[" + gsMsg.getMessage("cmn.folder") + "]";
        int dirAddFlg = 0;
        for (int dir : paramMdl.getWml320svDirectory()) {
            if (dirAddFlg > 0) {
                msg += gsMsg.getMessage("wml.231");
            }
            if (dir == GSConstWebmail.DIR_TYPE_RECEIVE) {
                msg += gsMsg.getMessage("cmn.receive");
                dirAddFlg++;
            } else if (dir == GSConstWebmail.DIR_TYPE_SENDED) {
                msg += gsMsg.getMessage("wml.19");
                dirAddFlg++;
            } else if (dir == GSConstWebmail.DIR_TYPE_NOSEND) {
                msg += gsMsg.getMessage("wml.211");
                dirAddFlg++;
            } else if (dir == GSConstWebmail.DIR_TYPE_DRAFT) {
                msg += gsMsg.getMessage("cmn.draft");
                dirAddFlg++;
            } else if (dir == GSConstWebmail.DIR_TYPE_DUST) {
                msg += gsMsg.getMessage("cmn.trash");
                dirAddFlg++;
            } else if (dir == GSConstWebmail.DIR_TYPE_STORAGE) {
                msg += gsMsg.getMessage("cmn.strage");
                dirAddFlg++;
            }
        }
        //キーワード
        if (!StringUtil.isNullZeroString(paramMdl.getWml320svKeyword())) {
            msg += "\r\n[" + gsMsg.getMessage("cmn.keyword") + "]" + paramMdl.getWml320svKeyword();
        }
        //From
        if (!StringUtil.isNullZeroString(paramMdl.getWml320svFrom())) {
            msg += "\r\n[" + gsMsg.getMessage("cmn.sendfrom") + "]" + paramMdl.getWml320svFrom();
        }
        //未読・既読
        if (paramMdl.getWml320svReaded() > 0) {
            msg += "\r\n[" + gsMsg.getMessage("wml.wml010.01") + "]";
            if (paramMdl.getWml320svReaded() == Wml010Const.SEARCH_READKBN_NOREAD) {
                msg += gsMsg.getMessage("cmn.read.yet");
            } else if (paramMdl.getWml320svReaded() == Wml010Const.SEARCH_READKBN_READED) {
                msg += gsMsg.getMessage("cmn.read.already");
            }
        }
        //送信先
        if (!StringUtil.isNullZeroString(paramMdl.getWml320svDest())) {
            msg += "\r\n[" + gsMsg.getMessage("wml.send.dest") + "]\r\n";
            int destAddFlg = 0;
            msg += gsMsg.getMessage("cmn.target") + gsMsg.getMessage("wml.215");
            if (paramMdl.getWml320svDestTypeTo() == 1) {
                msg += gsMsg.getMessage("cmn.from");
                destAddFlg++;
            }
            if (paramMdl.getWml320svDestTypeCc() == 1) {
                if (destAddFlg > 0) {
                    msg += gsMsg.getMessage("wml.231");
                }
                msg += gsMsg.getMessage("cmn.cc");
                destAddFlg++;
            }
            if (paramMdl.getWml320svDestTypeCc() == 1) {
                if (destAddFlg > 0) {
                    msg += gsMsg.getMessage("wml.231");
                }
                msg += gsMsg.getMessage("cmn.bcc");
            }
            msg += "\r\n" + paramMdl.getWml320svDest();
        }

        //添付ファイル
        if (paramMdl.getWml320svAttach() > 0) {
            msg += "\r\n[" + gsMsg.getMessage("cmn.attach.file") + "]";
            if (paramMdl.getWml320svAttach() == 2) {
                msg += gsMsg.getMessage("cmn.no3");
            } else if (paramMdl.getWml320svAttach() == GSConstWebmail.TEMPFLG_EXIST) {
                msg += gsMsg.getMessage("cmn.exist");
            }
        }
        //日付
        if (paramMdl.getWml320svDateType() == Wml320Form.WML320_DECIDE) {
            //日付From
            msg += "\r\n[" + gsMsg.getMessage("main.src.man050.5") + "]";
            msg += gsMsg.getMessage("cmn.date4",
                    new String[] {
                            String.valueOf(paramMdl.getWml320svDateYearFr()),
                            String.valueOf(paramMdl.getWml320svDateMonthFr()),
                            String.valueOf(paramMdl.getWml320svDateDayFr())});
            //日付To
            msg += "\r\n[" + gsMsg.getMessage("main.src.man050.6") + "]";
            msg += gsMsg.getMessage("cmn.date4",
                    new String[] {
                            String.valueOf(paramMdl.getWml320svDateYearTo()),
                            String.valueOf(paramMdl.getWml320svDateMonthTo()),
                            String.valueOf(paramMdl.getWml320svDateDayTo())});
        }

        //ラベル
        if (paramMdl.getWml320svLabel() > 0) {
            msg += "\r\n[" + gsMsg.getMessage("cmn.label") + "]";
            WmlLabelDao dao = new WmlLabelDao(con__);
            //ラベル名取得
            String labelName = dao.getLabelName(paramMdl.getWml320svLabel());
            msg += labelName;
        }
        //サイズ
        if (!StringUtil.isNullZeroString(paramMdl.getWml320svSize())) {
            msg += "\r\n[" + gsMsg.getMessage("cmn.size") + "]";
            if (!StringUtil.isNullZeroString(paramMdl.getWml320svSize())) {
                msg += paramMdl.getWml320svSize() + gsMsg.getMessage("wml.wml320.1")
                    + gsMsg.getMessage("cmn.comp.oe");
            }
        }

        //ソート順
        msg += "\r\n[" + gsMsg.getMessage("cmn.sort.order") + "]";
        if (paramMdl.getWml320svSortKey() == Wml320Form.WML320_SKEY_FROM) {
            msg += gsMsg.getMessage("cmn.sendfrom");
        } else if (paramMdl.getWml320svSortKey() == Wml320Form.WML320_SKEY_SUBJECT) {
            msg += gsMsg.getMessage("cmn.subject");
        } else {
            msg += gsMsg.getMessage("cmn.date");
        }
        msg += " ";
        if (paramMdl.getWml320svOrder() == Wml320Form.WML320_ORDER_DESC) {
            msg += gsMsg.getMessage("cmn.order.desc");
        } else {
            msg += gsMsg.getMessage("cmn.order.asc");
        }

        //削除件数
        WmlDelmailTempDao delDao = new WmlDelmailTempDao(con__);
        int delMailCnt = delDao.getDataCount(buMdl.getUsrsid());
        msg += "\r\n" + gsMsg.getMessage("wml.wml320.3")
            + gsMsg.getMessage("cmn.colon") + delMailCnt
            + gsMsg.getMessage("cmn.number");

        return msg;
    }
}