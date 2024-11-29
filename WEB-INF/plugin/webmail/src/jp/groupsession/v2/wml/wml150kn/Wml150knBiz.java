package jp.groupsession.v2.wml.wml150kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.dao.base.WmlFilterFwaddressDao;
import jp.groupsession.v2.wml.dao.base.WmlFwlimitDao;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.model.base.WmlFwlimitModel;
import jp.groupsession.v2.wml.wml150.Wml150Biz;
import jp.groupsession.v2.wml.wml150.Wml150Dao;
import jp.groupsession.v2.wml.wml150.Wml150Form;
import jp.groupsession.v2.wml.wml150.Wml150ParamModel;

/**
 * <br>[機  能] WEBメール アカウント基本設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml150knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml150knBiz.class);
    /** DBコネクション */
    private Connection con__ = null;

    /**
     * <p>デフォルトコンストラクター
     */
    public Wml150knBiz() {
    }

    /**
     * <p>Set Connection
     * @param con Connection
     */
    public Wml150knBiz(Connection con) {
        con__ = con;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl  RequestModel
     * @param paramMdl パラメータ情報
     */
    public void setInitData(Wml150knParamModel paramMdl, RequestModel reqMdl) {
        Wml150Biz biz = new Wml150Biz();
        paramMdl.setWml150knFwLimitText(
                biz.getFwLimitTextArray(paramMdl.getWml150FwLimitText()));
        //受信、送信暗号化表示用設定
        MailEncryptBiz proBiz = new MailEncryptBiz(reqMdl);
        paramMdl.setWml150knReceiveEncrypt(
                proBiz.getProtocolName(paramMdl.getWml150receiveServerEncrypt()));
        paramMdl.setWml150knSendEncrypt(
                proBiz.getProtocolName(paramMdl.getWml150sendServerEncrypt()));
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid セッションユーザSID
     * @throws Exception 実行例外
     */
    public void setData(Wml150knParamModel paramMdl, int userSid) throws Exception {

        boolean commitFlg = false;
        try {

            con__.setAutoCommit(false);
            WmlAdmConfDao wadDao = new WmlAdmConfDao(con__);
            WmlAdmConfModel wadMdl = new WmlAdmConfModel();

            //アカウント基本設定
            //アカウント作成区分設定
            wadMdl.setWadAcntMake(paramMdl.getWml150acntMakeKbn());
            wadMdl.setWadAcctSendformat(paramMdl.getWml150acntSendFormat());
            wadMdl.setWadAcctLogRegist(paramMdl.getWml150acntLogRegist());

            //アカウント詳細設定
            //権限設定
            wadMdl.setWadPermitKbn(paramMdl.getWml150permitKbn());
            //受信間隔
            wadMdl.setWadAutoReceiveTime(paramMdl.getWml150AutoReceiveTime());
            //ディスク容量
            wadMdl.setWadDisk(paramMdl.getWml150diskSize());
            //ディスク容量の制限が存在する場合
            if (paramMdl.getWml150diskSize() == GSConstWebmail.WAC_DISK_LIMIT) {
                wadMdl.setWadDiskSize(Integer.parseInt(paramMdl.getWml150diskSizeLimit()));
                int diskComp = 0;
                if (paramMdl.isWml150diskSizeComp()) {
                    diskComp = 1;
                }
                wadMdl.setWadDiskComp(diskComp);
            }
            //受信時削除
            wadMdl.setWadDelreceive(paramMdl.getWml150delReceive());
            //自動受信
            wadMdl.setWadAutoreceive(paramMdl.getWml150autoResv());

            //受信サーバ
            wadMdl.setWadReceiveHost(NullDefault.getString(paramMdl.getWml150receiveServer(), ""));
            //受信ポート
            wadMdl.setWadReceivePort(
                    Integer.parseInt(NullDefault.getStringZeroLength(
                                                paramMdl.getWml150receiveServerPort(),
                                                String.valueOf(GSConstWebmail.SERVER_DEFO_RESV))));
            //受信暗号化
            wadMdl.setWadReceiveSsl(paramMdl.getWml150receiveServerEncrypt());
            //送信サーバ
            wadMdl.setWadSendHost(NullDefault.getString(paramMdl.getWml150sendServer(), ""));
            //送信ポート
            wadMdl.setWadSendPort(
                    Integer.parseInt(NullDefault.getStringZeroLength(
                                    paramMdl.getWml150sendServerPort(),
                                    String.valueOf(GSConstWebmail.SERVER_DEFO_SEND))));
            //送信暗号化
            wadMdl.setWadSendSsl(paramMdl.getWml150sendServerEncrypt());

            //宛先の確認
            wadMdl.setWadCheckAddress(paramMdl.getWml150checkAddress());
            //添付ファイルの確認
            wadMdl.setWadCheckFile(paramMdl.getWml150checkFile());
            //添付ファイル自動圧縮
            wadMdl.setWadCompressFile(paramMdl.getWml150compressFile());
            //時間差送信
            wadMdl.setWadTimesent(paramMdl.getWml150timeSent());

            //添付ファイル自動圧縮 初期値
            if (paramMdl.getWml150compressFile() == GSConstWebmail.WAD_COMPRESS_FILE_INPUT) {
                if (paramMdl.getWml150compressFileDef() == Wml150Form.COMPRESS_FILE_DEF_YES) {
                    wadMdl.setWadCompressFileDef(GSConstWebmail.WAD_COMPRESS_FILE_DEF_COMPRESS);
                } else {
                    wadMdl.setWadCompressFileDef(
                            GSConstWebmail.WAD_COMPRESS_FILE_DEF_NOTCOMPRESS);
                }
            } else {
                wadMdl.setWadCompressFileDef(GSConstWebmail.WAD_COMPRESS_FILE_DEF_NOSET);
            }
            //時間差送信 初期値
            if (paramMdl.getWml150timeSent() == GSConstWebmail.WAD_TIMESENT_INPUT) {
                if (paramMdl.getWml150timeSentDef() == Wml150Form.TIMESENT_DEF_AFTER) {
                    wadMdl.setWadTimesentDef(GSConstWebmail.WAD_TIMESENT_DEF_LATER);
                } else {
                    wadMdl.setWadTimesentDef(GSConstWebmail.WAD_TIMESENT_DEF_IMM);
                }
            } else {
                wadMdl.setWadTimesentDef(GSConstWebmail.WAD_TIMESENT_DEF_NOSET);
            }

            //送信メールサイズの制限
            if (paramMdl.getWml150sendMaxSizeKbn() == Wml150knForm.SEND_LIMIT_LIMITED) {
                wadMdl.setWadSendLimit(GSConstWebmail.WAD_SEND_LIMIT_LIMITED);
                wadMdl.setWadSendLimitSize(Integer.parseInt(paramMdl.getWml150sendMaxSize()));
            } else {
                wadMdl.setWadSendLimit(GSConstWebmail.WAD_SEND_LIMIT_UNLIMITED);
                wadMdl.setWadSendLimitSize(0);
            }

            //メール転送制限
            if (paramMdl.getWml150FwLimit() == Wml150knForm.FWLIMIT_LIMITED) {
                wadMdl.setWadFwlimit(GSConstWebmail.WAD_FWLIMIT_LIMITED);
                __entryFwlimit(paramMdl, userSid);
            } else if (paramMdl.getWml150FwLimit() == Wml150knForm.FWLIMIT_UNLIMITED) {
                wadMdl.setWadFwlimit(GSConstWebmail.WAD_FWLIMIT_UNLIMITED);
            } else if (paramMdl.getWml150FwLimit() == Wml150knForm.FWLIMIT_PROHIBITED) {
                wadMdl.setWadFwlimit(GSConstWebmail.WAD_FWLIMIT_PROHIBITED);
            }

            //BCC強制変換
            if (paramMdl.getWml150bcc() == GSConstWebmail.WAD_BCC_CONVERSION) {
                wadMdl.setWadBcc(GSConstWebmail.WAD_BCC_CONVERSION);
                wadMdl.setWadBccTh(paramMdl.getWml150bccThreshold());
            } else {
                wadMdl.setWadBcc(GSConstWebmail.WAD_BCC_UNLIMITED);
                wadMdl.setWadBccTh(0);
            }

            //ディスク容量警告
            if (paramMdl.getWml150warnDisk() == GSConstWebmail.WAD_WARN_DISK_YES) {
                wadMdl.setWadWarnDisk(GSConstWebmail.WAD_WARN_DISK_YES);
                wadMdl.setWadWarnDiskTh(paramMdl.getWml150warnDiskThreshold());
            } else {
                wadMdl.setWadWarnDisk(GSConstWebmail.WAD_WARN_DISK_NO);
                wadMdl.setWadWarnDiskTh(0);
            }

            //サーバー情報の設定
            wadMdl.setWadSettingServer(GSConstWebmail.WAD_SETTING_SERVER_NO);
            if (paramMdl.getWml150settingServer() == GSConstWebmail.WAD_SETTING_SERVER_YES) {
                wadMdl.setWadSettingServer(GSConstWebmail.WAD_SETTING_SERVER_YES);
            }

            //代理人
            wadMdl.setWadProxyUser(GSConstWebmail.WAD_PROXY_USER_NO);
            if (paramMdl.getWml150proxyUser() == GSConstWebmail.WAD_PROXY_USER_YES) {
                wadMdl.setWadProxyUser(GSConstWebmail.WAD_PROXY_USER_YES);
            }

            //リンク設定
            wadMdl.setWadlinkLimit(GSConstWebmail.WAD_LINK_UNLIMITED);
            if (paramMdl.getWml150linkLimit() == GSConstWebmail.WAD_LINK_LIMITED) {
                wadMdl.setWadlinkLimit(GSConstWebmail.WAD_LINK_LIMITED);
            }

            //トップレベルドメイン制限設定
            wadMdl.setWadTldLimit(GSConstWebmail.WAD_TLD_LIMITED);
            if (paramMdl.getWml150TldLimit() == GSConstWebmail.WAD_TLD_UNLIMITED) {
                wadMdl.setWadTldLimit(GSConstWebmail.WAD_TLD_UNLIMITED);
            }
            wadMdl.setWadTldLimitText(paramMdl.getWml150TldLimitText());

            //更新処理(db_init で初期データが必ずあるので常に更新のみ)
            wadDao.updateAdmConf(wadMdl);

            //フィルターの転送設定をすべて削除する
            if (paramMdl.getWml150FwLimit() == Wml150Form.FWLIMIT_PROHIBITED
            && paramMdl.getWml150FwLimitDelete() == Wml150Form.FWLIMIT_DELETE_DEL) {
                WmlFilterFwaddressDao filterAddressDao = new WmlFilterFwaddressDao(con__);
                filterAddressDao.deleteAll();

                Wml150Dao dao150 = new Wml150Dao(con__);
                dao150.clearFilterActonForward();
            }

            con__.commit();
            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;

        } finally {
            if (!commitFlg) {
                JDBCUtil.rollback(con__);
            }
        }
    }

    /**
     * <br>[機  能] WEBメール転送制限情報をDBに登録する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param userSid セッションユーザSID
     * @throws SQLException SQL実行エラー
     */
    private void __entryFwlimit(Wml150ParamModel paramMdl, int userSid)
    throws SQLException {

        //転先制限情報を削除
        WmlFwlimitDao dao = new WmlFwlimitDao(con__);
        dao.delete();

        //転先制限情報の登録
        int wflNo = 1;
        WmlFwlimitModel model = new WmlFwlimitModel();
        Wml150Biz biz150 = new Wml150Biz();
        String[] fwLimitText = biz150.getFwLimitTextArray(paramMdl.getWml150FwLimitText());
        for (String text : fwLimitText) {
            model.setWflText(text);
            model.setWflNo(wflNo++);
            dao.insert(model);
        }
    }
}