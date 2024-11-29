package jp.groupsession.v2.wml.wml150;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.MailEncryptBiz;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlFwlimitDao;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.model.base.WmlFwlimitModel;

/**
 * <br>[機  能] WEBメール アカウント基本設定画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml150Biz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Wml150Biz.class);

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     */
    public void setInitData(Connection con,
                              Wml150ParamModel paramMdl,
                              RequestModel reqMdl) throws SQLException {
        log__.debug("START");

        //自動受信時間ラベル
        paramMdl.setWml150AutoRsvKeyLabel(createAutoRsvTimeCombo(reqMdl));

        //初期表示かどうか
        if (paramMdl.getWml150initFlg() == GSConstWebmail.DSP_FIRST) {

            //WmlAdmConfDao wadDao = new WmlAdmConfDao(con);
            //WmlAdmConfModel wadMdl = new WmlAdmConfModel();
            //wadMdl = wadDao.selectAdmData();

            WmlBiz biz = new WmlBiz();
            WmlAdmConfModel wadMdl = biz.getAdminConf(con);

            //データをセットする
            paramMdl.setWml150acntMakeKbn(wadMdl.getWadAcntMake());
            paramMdl.setWml150acntSendFormat(wadMdl.getWadAcctSendformat());
            paramMdl.setWml150acntLogRegist(wadMdl.getWadAcctLogRegist());

            //権限区分
            paramMdl.setWml150permitKbn(wadMdl.getWadPermitKbn());
            //ディスク制限
            paramMdl.setWml150diskSize(wadMdl.getWadDisk());
            if (paramMdl.getWml150diskSize() == GSConstWebmail.WAC_DISK_LIMIT) {
                //ディスク制限あり
                paramMdl.setWml150diskSizeLimit(String.valueOf(wadMdl.getWadDiskSize()));
                if (wadMdl.getWadDiskComp() == GSConstWebmail.WAC_DISK_ADM_COMP) {
                    paramMdl.setWml150diskSizeComp(true);
                }
            }
            //受信時削除
            paramMdl.setWml150delReceive(wadMdl.getWadDelreceive());
            //自動受信設定
            paramMdl.setWml150autoResv(wadMdl.getWadAutoreceive());
            //自動受信間隔
            paramMdl.setWml150AutoReceiveTime(wadMdl.getWadAutoReceiveTime());

            //受信サーバ
            paramMdl.setWml150receiveServer(wadMdl.getWadReceiveHost());
            //受信ポート
            paramMdl.setWml150receiveServerPort(String.valueOf(wadMdl.getWadReceivePort()));
            //受信暗号化
            paramMdl.setWml150receiveServerEncrypt(wadMdl.getWadReceiveSsl());
            //送信サーバ
            paramMdl.setWml150sendServer(wadMdl.getWadSendHost());
            //送信ポート
            paramMdl.setWml150sendServerPort(String.valueOf(wadMdl.getWadSendPort()));
            //送信暗号化
            paramMdl.setWml150sendServerEncrypt(wadMdl.getWadSendSsl());
            // 送信メールサイズの制限
            if (wadMdl.getWadSendLimit() == GSConstWebmail.WAD_SEND_LIMIT_LIMITED) {
                paramMdl.setWml150sendMaxSizeKbn(Wml150Form.SEND_LIMIT_LIMITED);
                paramMdl.setWml150sendMaxSize(String.valueOf(wadMdl.getWadSendLimitSize()));
            }

            //メール転送制限
            if (wadMdl.getWadFwlimit() == GSConstWebmail.WAD_FWLIMIT_LIMITED) {
                paramMdl.setWml150FwLimit(Wml150Form.FWLIMIT_LIMITED);

                WmlFwlimitDao fwLimitDao = new WmlFwlimitDao(con);
                List<WmlFwlimitModel> fwLimitList = fwLimitDao.select();

                String fwLimitText = "";
                for (WmlFwlimitModel fwData : fwLimitList) {
                    fwLimitText += fwData.getWflText() + "\n";
                }
                paramMdl.setWml150FwLimitText(fwLimitText);
            } else if (wadMdl.getWadFwlimit() == GSConstWebmail.WAD_FWLIMIT_UNLIMITED) {
                paramMdl.setWml150FwLimit(Wml150Form.FWLIMIT_UNLIMITED);
            } else if (wadMdl.getWadFwlimit() == GSConstWebmail.WAD_FWLIMIT_PROHIBITED) {
                paramMdl.setWml150FwLimit(Wml150Form.FWLIMIT_PROHIBITED);
            }

            //宛先の確認
            paramMdl.setWml150checkAddress(wadMdl.getWadCheckAddress());
            //添付ファイルの確認
            paramMdl.setWml150checkFile(wadMdl.getWadCheckFile());
            //添付ファイル自動圧縮
            paramMdl.setWml150compressFile(wadMdl.getWadCompressFile());
            //添付ファイル自動圧縮 初期値
            if (wadMdl.getWadCompressFileDef()
                    == GSConstWebmail.WAC_COMPRESS_FILE_DEF_COMPRESS) {
                paramMdl.setWml150compressFileDef(Wml150Form.COMPRESS_FILE_DEF_YES);
            } else {
                paramMdl.setWml150compressFileDef(Wml150Form.COMPRESS_FILE_DEF_NO);
            }
            //時間差送信
            paramMdl.setWml150timeSent(wadMdl.getWadTimesent());
            //時間差送信 初期値
            if (wadMdl.getWadTimesentDef() == GSConstWebmail.WAC_TIMESENT_DEF_LATER) {
                paramMdl.setWml150timeSentDef(Wml150Form.TIMESENT_DEF_AFTER);
            } else {
                paramMdl.setWml150timeSentDef(Wml150Form.TIMESENT_DEF_IMM);
            }
            //BCC強制変換
            paramMdl.setWml150bcc(wadMdl.getWadBcc());
            paramMdl.setWml150bccThreshold(wadMdl.getWadBccTh());

            //ディスク容量警告
            paramMdl.setWml150warnDisk(wadMdl.getWadWarnDisk());
            paramMdl.setWml150warnDiskThreshold(wadMdl.getWadWarnDiskTh());

            //サーバー情報の設定
            paramMdl.setWml150settingServer(wadMdl.getWadSettingServer());
            //代理人
            paramMdl.setWml150proxyUser(wadMdl.getWadProxyUser());
            //リンク制限
            paramMdl.setWml150linkLimit(wadMdl.getWadlinkLimit());

            //TLD制限
            paramMdl.setWml150TldLimit(wadMdl.getWadTldLimit());

            //TLD制限テキスト
            paramMdl.setWml150TldLimitText(wadMdl.getWadTldLimitText());

            paramMdl.setWml150initFlg(GSConstWebmail.DSP_ALREADY);
        }

        //不正なフィルター一覧を取得
        String fwLimitText = paramMdl.getWml150svFwLimitText();
        if (!StringUtil.isNullZeroString(fwLimitText)) {
            String[] fwTextArray = getFwLimitTextArray(fwLimitText);
            Wml150Dao dao150 = new Wml150Dao(con);
            paramMdl.setFwCheckList(dao150.getFwCheckList(fwTextArray));
        }

        //BCC強制変換 選択値を設定
        String[] bccThresholdValues = {"5", "7", "10", "15", "20", "30"};
        List<LabelValueBean> bccThresholdList = new ArrayList<LabelValueBean>();
        for (String bccThresholdValue : bccThresholdValues) {
            bccThresholdList.add(new LabelValueBean(bccThresholdValue,
                                                                            bccThresholdValue));
        }
        paramMdl.setBccThresholdList(bccThresholdList);

        //ディスク容量警告 選択値を設定
        List<LabelValueBean> warnDiskThresholdList = new ArrayList<LabelValueBean>();
        for (int warnValue = 10; warnValue <= 90; warnValue += 10) {
            String strWarnValue = Integer.toString(warnValue);
            warnDiskThresholdList.add(new LabelValueBean(strWarnValue,
                                                                                    strWarnValue));
        }
        paramMdl.setWarnDiskThresholdList(warnDiskThresholdList);

        //暗号化プロトコルコンボを設定
        MailEncryptBiz protcolBiz = new MailEncryptBiz(reqMdl);
        paramMdl.setWml150AngoProtocolCombo(
                protcolBiz.setDspProtocolLabels());

        log__.debug("END");
    }

    /**
     * <br>[機  能] 自動受信時間コンボを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return 年コンボ
     */
    public static List<LabelValueBean> createAutoRsvTimeCombo(RequestModel reqMdl) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        //自動受信時間ラベル
        ArrayList<LabelValueBean> autoRsvTimeCombo = new ArrayList<LabelValueBean>();

        String minute = gsMsg.getMessage("cmn.minute");
        String time5 = "5" + minute;
        String time10 = "10" + minute;
        String time30 = "30" + minute;
        String time60 = "60" + minute;

        String[] timeKeyAllText = new String[] { time5,
                time10, time30, time60 };

        for (int i = 0; i < GSConstWebmail.LIST_AUTO_REV_KEY_ALL.length; i++) {
            String label = timeKeyAllText[i];
            String value = String.valueOf(GSConstWebmail.LIST_AUTO_REV_KEY_ALL[i]);
            autoRsvTimeCombo.add(new LabelValueBean(label, value));
        }

        return autoRsvTimeCombo;
    }

    /**
     * <br>[機  能] 転送先制限 許可する文字列を文字列配列へ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param fwLimitText 転送先制限 許可する文字列
     * @return 文字列配列
     */
    public String[] getFwLimitTextArray(String fwLimitText) {
        if (StringUtil.isNullZeroString(fwLimitText)) {
            return null;
        }

        return fwLimitText.replaceAll("\r\n", "\n").split("\n");
    }

}
