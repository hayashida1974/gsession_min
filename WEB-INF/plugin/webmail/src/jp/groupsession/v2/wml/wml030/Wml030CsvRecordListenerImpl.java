package jp.groupsession.v2.wml.wml030;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.csv.CSVRecordListener;
import jp.co.sjts.util.csv.CsvEncode;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.model.AbstractModel;
import jp.groupsession.v2.cmn.model.base.CmnOauthModel;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.wml040.Wml040Action;
import jp.groupsession.v2.wml.wml040.Wml040Form;
import jp.groupsession.v2.wml.wml160.WebmailCsvModel;

/**
 * <br>[機  能] ユーザ情報一覧のCSV出力について1レコード分の処理を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml030CsvRecordListenerImpl implements CSVRecordListener {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml040Action.class);

    /** PrintWriter */
    private PrintWriter pw__ = null;
    /** DBコネクション */
    private Connection con__ = null;
    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con Connection
     * @param pw PrintWriter
     */
    public Wml030CsvRecordListenerImpl(PrintWriter pw, Connection con) {
        pw__ = pw;
        con__ = con;
    }

    /**
     * <br>[機  能] DBから取得したModelをセットし、CSVに出力する
     * <br>[解  説]
     * <br>[備  考]
     * @param model DBから取得したModel
     * @throws CSVException CSV出力時例外
     */
    public void setRecord(AbstractModel model) throws CSVException {
        Wml030ExportModel exportData = (Wml030ExportModel) model;
        WmlAccountModel accountData = exportData.getAccountData();
        int wacSid = accountData.getWacSid();

        //1行分出力
        StringBuilder sb = new StringBuilder();

        //アカウントID
        __addValue(sb, accountData.getWacAccountId());
        //アカウント名
        __addValue(sb, accountData.getWacName());
        //認証方式
        __addValue(sb, accountData.getWacAuthType());
        //プロバイダ
        if (accountData.getWacAuthType() == GSConstWebmail.WAC_AUTH_TYPE_NORMAL) {
            __addValue(sb, "");
        } else if (accountData.getWacAuthType() == GSConstWebmail.WAC_AUTH_TYPE_OAUTH) {

            CmnOauthDao couDao = new CmnOauthDao(con__);
            try {
                CmnOauthModel oauthData = couDao.getAuthData(accountData.getCotSid());
                __addValue(sb, oauthData.getCouProvider());
            } catch (SQLException e) {
                log__.error("認証情報の取得に失敗", e);
                throw new CSVException("認証情報の取得に失敗", e);
            }
        }
        //メールアドレス
        __addValue(sb, accountData.getWacAddress());
        //メール受信サーバ
        __addValue(sb, accountData.getWacReceiveHost());
        //受信サーバ　ポート番号
        __addValue(sb, accountData.getWacReceivePort());
        //受信サーバ　SSLの使用
        __addValue(sb, accountData.getWacReceiveSsl());
        //受信サーバ　ユーザ名
        __addValue(sb, accountData.getWacReceiveUser());
        //受信サーバ　パスワード
        __addValue(sb, accountData.getWacReceivePass());
        //メール自動受信
        __addValue(sb, accountData.getWacAutoreceive());

        //メール自動受信間隔
        if (accountData.getWacAutoreceive() == GSConstWebmail.MAIL_AUTO_RSV_ON) {
            switch (accountData.getWacAutoReceiveTime()) {
            case GSConstWebmail.AUTO_RECEIVE_5:
                __addValue(sb, WebmailCsvModel.AUTO_RSVTIME_5);
                break;
            case GSConstWebmail.AUTO_RECEIVE_10:
                __addValue(sb, WebmailCsvModel.AUTO_RSVTIME_10);
                break;
            case GSConstWebmail.AUTO_RECEIVE_30:
                __addValue(sb, WebmailCsvModel.AUTO_RSVTIME_30);
                break;
            case GSConstWebmail.AUTO_RECEIVE_60:
                __addValue(sb, WebmailCsvModel.AUTO_RSVTIME_60);
                break;
            default:
                __addValue(sb, WebmailCsvModel.AUTO_RSVTIME_5);
                break;
            }
        } else {
            __addValue(sb, "");
        }

        //メール送信サーバ
        __addValue(sb, accountData.getWacSendHost());
        //メール送信サーバ　ポート番号
        __addValue(sb, accountData.getWacSendPort());
        //メール送信サーバ　SSLの使用
        __addValue(sb, accountData.getWacSendSsl());
        //SMTP認証 ON/OFF
        __addValue(sb, accountData.getWacSmtpAuth());
        //メール送信サーバ　ユーザ名
        __addValue(sb, accountData.getWacSendUser());
        //メール送信サーバ　パスワード
        __addValue(sb, accountData.getWacSendPass());
        //ディスク容量
        __addValue(sb, accountData.getWacDisk());
        //ディスク容量　サイズ
        __addValue(sb, accountData.getWacDiskSize());
        //ディスク容量　特例修正
        __addValue(sb, accountData.getWacDiskSps());
        //備考
        __addValue(sb, accountData.getWacBiko());
        //組織名
        __addValue(sb, accountData.getWacOrganization());

        //署名
        try {
            WmlBiz wmlBiz = new WmlBiz();
            __addValue(sb, wmlBiz.getAccountSign(con__, accountData.getWacSid()));
        } catch (SQLException e) {
            log__.error("署名の取得に失敗", e);
            throw new CSVException("署名の取得に失敗", e);
        }
        //署名自動挿入
        __addValue(sb, accountData.getWacSignAuto());
        //返信時署名位置
        __addValue(sb, accountData.getWacSignPointKbn());

        //返信時署名表示
        if (accountData.getWacSignDspKbn() == GSConstWebmail.WAC_SIGN_DSP_KBN_DSP) {
            __addValue(sb, WebmailCsvModel.SIGN_DSP_KBN_DSP);
        } else {
            __addValue(sb, WebmailCsvModel.SIGN_DSP_KBN_NODSP);
        }

        //自動TO
        __addValue(sb, accountData.getWacAutoto());
        //自動CC
        __addValue(sb, accountData.getWacAutocc());
        //自動BCC
        __addValue(sb, accountData.getWacAutobcc());
        //受信時削除
        __addValue(sb, accountData.getWacDelreceive());
        //受信済みでも受信
        __addValue(sb, accountData.getWacRereceive());
        //APOPのON/OFF
        __addValue(sb, accountData.getWacApop());
        //TOPコマンドの使用
        __addValue(sb, accountData.getWacTopCmd());
        //送信前POP認証
        __addValue(sb, accountData.getWacPopbsmtp());
        //送信文字コード
        __addValue(sb, accountData.getWacEncodeSend());
        //送信メール形式
        __addValue(sb, accountData.getWacSendMailtype());
        //宛先の確認
        __addValue(sb, accountData.getWacCheckAddress());
        //添付ファイルの確認
        __addValue(sb, accountData.getWacCheckFile());
        //添付ファイル自動圧縮
        __addValue(sb, accountData.getWacCompressFile());
        if (accountData.getWacCompressFile() == GSConstWebmail.WAC_COMPRESS_FILE_INPUT) {
            //添付ファイル自動圧縮 初期値
            if (accountData.getWacCompressFileDef()
                    == GSConstWebmail.WAC_COMPRESS_FILE_DEF_COMPRESS) {
                __addValue(sb, Wml040Form.COMPRESS_FILE_DEF_YES);
            } else {
                __addValue(sb, Wml040Form.COMPRESS_FILE_DEF_NO);
            }
        } else {
            __addValue(sb, "");
        }
        //時間差送信
        __addValue(sb, accountData.getWacTimesent());
        if (accountData.getWacTimesent() == GSConstWebmail.WAC_TIMESENT_INPUT) {
            //時間差送信 初期値
            if (accountData.getWacTimesentDef() == GSConstWebmail.WAC_TIMESENT_DEF_LATER) {
                __addValue(sb, Wml040Form.TIMESENT_DEF_AFTER);
            } else {
                __addValue(sb, Wml040Form.TIMESENT_DEF_IMM);
            }
        } else {
            __addValue(sb, "");
        }
        //テーマ
        __addValue(sb, accountData.getWacTheme());
        //引用符
        __addValue(sb, accountData.getWacQuotes());
        //自動保存
        if (accountData.getWacAutoSaveMinute()
                == Integer.parseInt(GSConstWebmail.MINUTE_VALUE[6])) {
            __addValue(sb, GSConstWebmail.CSV_MINUTE_VALUE[6]);
        } else {
            __addValue(sb, accountData.getWacAutoSaveMinute());
        }

        try {
            //使用ユーザ/グループ区分、使用ユーザ/グループ1,2,3,4,5
            Wml030Dao dao = new Wml030Dao(con__);
            List<String> accountUserList = null;
            if (accountData.getWacType() == GSConstWebmail.WAC_TYPE_GROUP) {
                __addValue(sb, WebmailCsvModel.USERKBN_GROUP);
                accountUserList = dao.getAccountUseGroup(wacSid);
            } else {
                __addValue(sb, WebmailCsvModel.USERKBN_USER);
                accountUserList = dao.getAccountUseUser(wacSid);
            }
            __addUserList(sb, accountUserList, true);

            //代理人1,2,3,4,5
            List<String> accountProxyUserList = dao.getAccountProxyUser(wacSid);
            __addUserList(sb, accountProxyUserList, false);

        } catch (SQLException e) {
            log__.error("SQLの実行に失敗", e);
            throw new CSVException("SQLの実行に失敗", e);
        }

        pw__.println(sb.toString());
    }

    /**
     * <br>[機  能] CSV項目を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sb StringBuilder
     * @param value 項目
     */
    private void __addValue(StringBuilder sb, String value) {
        __addValue(sb, value, true);
    }

    /**
     * <br>[機  能] CSV項目を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sb StringBuilder
     * @param value 項目
     */
    private void __addValue(StringBuilder sb, int value) {
        __addValue(sb, Integer.toString(value), true);
    }

    /**
     * <br>[機  能] CSV項目を追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sb StringBuilder
     * @param value 項目
     * @param delim true: 区切り文字を追加 false: 通常
     */
    private void __addValue(StringBuilder sb, String value, boolean delim) {
        sb.append(CsvEncode.encString(value));
        if (delim) {
            sb.append(",");
        }
    }

    /**
     * <br>[機  能] アカウント使用者、またはアカウント代理人をCSV項目に追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sb Stringbuilder
     * @param userList アカウント使用者 or アカウント代理人
     * @param lastDelim true: 最後に区切り文字を追加 false: 通常
     */
    private void __addUserList(StringBuilder sb, List<String> userList, boolean lastDelim) {
        __addUser(sb, userList, 1, true);
        __addUser(sb, userList, 2, true);
        __addUser(sb, userList, 3, true);
        __addUser(sb, userList, 4, true);
        __addUser(sb, userList, 5, lastDelim);
    }

    /**
     * <br>[機  能] アカウント使用者、またはアカウント代理人をCSV項目に追加する
     * <br>[解  説]
     * <br>[備  考]
     * @param sb Stringbuilder
     * @param userList アカウント使用者 or アカウント代理人
     * @param userNo ユーザ番号(1～5)
     * @param delim true: 区切り文字を追加 false: 通常
     */
    private void __addUser(StringBuilder sb, List<String> userList, int userNo, boolean delim) {
        if (userList.size() >= userNo) {
            __addValue(sb, userList.get(userNo - 1), delim);
        } else {
            __addValue(sb, "", delim);
        }
    }
}