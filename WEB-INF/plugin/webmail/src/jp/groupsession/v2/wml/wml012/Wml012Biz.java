package jp.groupsession.v2.wml.wml012;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlAdmConfDao;
import jp.groupsession.v2.wml.model.MailTempFileModel;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlAdmConfModel;
import jp.groupsession.v2.wml.wml010.Wml010Biz;
import jp.groupsession.v2.wml.wml010.Wml010Const;
import jp.groupsession.v2.wml.wml010.Wml010Form;

/**
 * <br>[機  能] WEBメール 送信メール確認(ポップアップ)画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml012Biz extends Wml010Biz {

    /** ドメイン種別 */
    private int domainType__  =  0;

    /**
     * <br>[機  能] 送信メール情報を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param tempDirId テンポラリディレクトリID
     * @return パラメータ情報
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 送信メール情報の読み込みに失敗
     */
    public Wml012ParamModel getInitData(Connection con, RequestModel reqMdl, String tempDirId)
        throws SQLException, IOToolsException {

        //テンポラリディレクトリIDのチェック
        checkDirId(tempDirId);

        Wml010Biz wml010Biz = new Wml010Biz();
        String tempDir = wml010Biz.getSendTempDir(reqMdl, tempDirId);

        File sendMailDataPath = __getSaveFilePath(tempDir);
        Wml012ParamModel paramMdl  = null;
        if (sendMailDataPath != null && sendMailDataPath.exists()) {
            ObjectFile objFile
                = new ObjectFile(sendMailDataPath.getParent(), sendMailDataPath.getName());

            paramMdl = (Wml012ParamModel) objFile.load();
            Map<String, String> domainMap = new HashMap<String, String>();
            domainType__ = 0;
            paramMdl.setWml012ToList(
                    __createAddressList(paramMdl.getWml010sendAddressTo(), domainMap));
            paramMdl.setWml012CcList(
                    __createAddressList(paramMdl.getWml010sendAddressCc(), domainMap));
            paramMdl.setWml012BccList(
                    __createAddressList(paramMdl.getWml010sendAddressBcc(), domainMap));

            paramMdl.setWml012ViewBody(paramMdl.getWml010svSendContent());
            if (paramMdl.getWml010sendMailHtml() != GSConstWebmail.SEND_HTMLMAIL_HTML) {

                paramMdl.setWml012ViewBody(
                        StringUtilHtml.transToHTmlPlusAmparsant(paramMdl.getWml012ViewBody()));
            }

            GsMessage gsMsg = new GsMessage(reqMdl);
            String sendPlanDate = null;

            int sendAccountSid = paramMdl.getWml010sendAccount();
            WmlAdmConfDao admConfDao = new WmlAdmConfDao(con);
            WmlAdmConfModel wmlAdmConf = admConfDao.selectAdmData();

            if (paramMdl.getSendMailPlanType() == Wml010Form.SENDMAILPLAN_TYPE_LATER) {
                    sendPlanDate = gsMsg.getMessage("cmn.view.date", new String[] {
                        paramMdl.getWml010sendMailPlanDateYear(),
                        paramMdl.getWml010sendMailPlanDateMonth(),
                        paramMdl.getWml010sendMailPlanDateDay(),
                        paramMdl.getWml010sendMailPlanDateHour(),
                        paramMdl.getWml010sendMailPlanDateMinute()
                });
            } else {
                int timeSendMinute = 0;

                boolean timsentImm
                    = paramMdl.getWml010sendMailPlanImm() == Wml010Const.SENDPLAN_IMM;

                if (wmlAdmConf.getWadPermitKbn() == GSConstWebmail.PERMIT_ADMIN) {
                    //管理者設定 時間差送信 = 有効 に設定されている
                    //かつ、画面上で"即時送信"が選択されていない場合
                    if (wmlAdmConf.getWadTimesent() == GSConstWebmail.WAC_TIMESENT_LATER
                    || (wmlAdmConf.getWadTimesent() == GSConstWebmail.WAC_TIMESENT_INPUT
                        && !timsentImm)) {
                        timeSendMinute = 5;
                    }
                } else {
                    WmlAccountDao wacDao = new WmlAccountDao(con);
                    WmlAccountModel wacMdl = wacDao.select(sendAccountSid);
                    if (wacMdl.getWacTimesent() == GSConstWebmail.WAC_TIMESENT_INPUT) {
                        if (!timsentImm) {
                            timeSendMinute = 5;
                        }
                    } else {
                        WmlBiz wmlBiz = new WmlBiz();
                        timeSendMinute
                            = wmlBiz.getTimeSentMinute(con, sendAccountSid);
                    }
                }

                if (timeSendMinute > 0) {
                    sendPlanDate
                        = gsMsg.getMessage("wml.242",
                                                new String[] {Integer.toString(timeSendMinute)});
                } else {
                    sendPlanDate = gsMsg.getMessage("wml.wml012.01");
                }
            }
            paramMdl.setWml012SendPlanDate(sendPlanDate);

            paramMdl.setWml012TempfileList(__getSendMailFileData(tempDir));

            //[宛先、添付ファイルの確認]を設定
            setAccountSendConf(con, paramMdl, sendAccountSid);

            //添付ファイル自動圧縮
            if (paramMdl.getWml012TempfileList() != null
            && !paramMdl.getWml012TempfileList().isEmpty()) {

                int compressTempfile = 0;
                boolean compressFileInput = false;
                if (wmlAdmConf != null
                && wmlAdmConf.getWadPermitKbn() == GSConstWebmail.PERMIT_ADMIN) {
                    compressFileInput
                        = wmlAdmConf.getWadCompressFile() == GSConstWebmail.WAD_COMPRESS_FILE_INPUT;

                    //管理者設定で「自動圧縮する」に設定されている場合
                    if (!compressFileInput) {
                        if (wmlAdmConf.getWadCompressFile()
                            == GSConstWebmail.WAD_COMPRESS_FILE_COMPRESS) {
                            compressTempfile = Wml012Form.COMPRESS_TEMPFILE;
                        }
                    }
                } else {
                    WmlAccountDao accountDao = new WmlAccountDao(con);
                    WmlAccountModel accountData = accountDao.select(sendAccountSid);
                    compressFileInput
                        = accountData.getWacCompressFile()
                            == GSConstWebmail.WAD_COMPRESS_FILE_INPUT;

                    //アカウント情報で「自動圧縮する」に設定されている場合
                    if (!compressFileInput) {
                        if (accountData.getWacCompressFile()
                            == GSConstWebmail.WAC_COMPRESS_FILE_COMPRESS) {
                            compressTempfile = Wml012Form.COMPRESS_TEMPFILE;
                        }
                    }
                }
                //画面からの指定に従う場合
                if (compressFileInput
                && paramMdl.getWml010sendTempfileCompress() == 1) {
                    compressTempfile = Wml012Form.COMPRESS_TEMPFILE;
                }

                paramMdl.setWml012compressTempfile(compressTempfile);
            }
        }

        //アカウントのテーマを設定
        String viewAccount = paramMdl.getWmlViewAccount();
        if (!StringUtil.isNullZeroString(viewAccount)
        && ValidateUtil.isNumber(viewAccount)) {
            int viewWacSid = Integer.parseInt(viewAccount);
            if (viewWacSid > 0) {
                Wml010Biz biz010 = new Wml010Biz();
                biz010.setAccountTheme(con, paramMdl, viewWacSid);
            }
        }

        return paramMdl;
    }

    /**
     * <br>[機  能] 送信メール情報を保存する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param tempDirId テンポラリディレクトリID
     * @throws IOToolsException 送信メール情報の読み込みに失敗
     */
    public void saveSendMailData(Wml012ParamModel paramMdl,
                                    RequestModel reqMdl, String tempDirId)
        throws IOToolsException {

        //テンポラリディレクトリIDのチェック
        checkDirId(tempDirId);

        Wml010Biz wml010Biz = new Wml010Biz();
        String tempDir = wml010Biz.getSendTempDir(reqMdl, tempDirId);

        File sendMailDataPath = __getSaveFilePath(tempDir);
        if (sendMailDataPath != null) {
            ObjectFile objFile
                = new ObjectFile(sendMailDataPath.getParent(), sendMailDataPath.getName());
            objFile.save(paramMdl);
        }
    }

    /**
     * <br>[機  能] JSON形式で使用できる文字列へ変換する
     * <br>[解  説]
     * <br>[備  考]
     * @param text 文字列
     * @return 文字列
     */
    public static String escapeText(String text) {
        return _escapeText(text, true, true, true);
    }

    /**
     * <br>[機  能] 送信メール情報ファイルのファイルパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return ファイルパス
     * @throws IOToolsException 送信メール情報の読み込みに失敗
     */
    private File __getSaveFilePath(String tempDir)
    throws IOToolsException {

        File sendMailDataPath = null;
        if (tempDir != null && !tempDir.isEmpty()) {
            String sendTempPath = IOTools.setEndPathChar(tempDir);
            sendTempPath += "confirm/sendMailData";
            sendMailDataPath = new File(sendTempPath);
            IOTools.isDirCheck(sendMailDataPath.getParent(), true);
        }
        return sendMailDataPath;
    }


    /**
     * <br>[機  能] 送信メール情報の添付ファイル一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return 添付ファイル情報
     * @throws IOToolsException 添付ファイル情報取得時に例外発生
     */
    private List<MailTempFileModel> __getSendMailFileData(String tempDir)
    throws IOToolsException {

        List<MailTempFileModel> fileDataList = new ArrayList<MailTempFileModel>();

        List<String> fileList = null;
        if (tempDir != null && !tempDir.isEmpty()) {
            fileList = IOTools.getFileNames(tempDir);
        }
        if (fileList != null && !fileList.isEmpty()) {

            Cmn110FileModel tempFileModel = null;
            for (String fileName : fileList) {
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                CommonBiz cmnBiz = new CommonBiz();

                tempFileModel = (Cmn110FileModel) fObj;
                MailTempFileModel sendFileData = new MailTempFileModel();
                sendFileData.setFileName(tempFileModel.getFileName());
                sendFileData.setFileSizeDsp(
                        cmnBiz.getByteSizeString(
                                new File(tempDir + tempFileModel.getSaveFileName()).length()));
                fileDataList.add(sendFileData);
            }
        }

        return fileDataList;
    }
    /**
     * <br>[機  能] 送信先一覧を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param address 送信先メールアドレス(宛先 or CC or BCC)
     * @param domainMap ドメインとドメイン種別のMapping
     * @return 送信先一覧
     */
    private List<Wml012AddressModel> __createAddressList(String address,
                                                        Map<String, String>domainMap) {
        List<Wml012AddressModel> addressList = new ArrayList<Wml012AddressModel>();
        if (StringUtil.isNullZeroString(address)) {
            return addressList;
        }
        InternetAddress[] sendAddressArr = null;
        try {
            sendAddressArr = WmlBiz.parseAddress(address);
        } catch (AddressException e) {
        }
        if (sendAddressArr == null) {
            return addressList;
        }

        for (InternetAddress iAdress : sendAddressArr) {
            Wml012AddressModel addressData = new Wml012AddressModel();
            String add = iAdress.getAddress();
            String pre = iAdress.getPersonal();
            int preIdx = 0;
            StringBuilder iAdd = new StringBuilder();
            StringBuilder addPrev = new StringBuilder();
            

            if (pre != null) {
                preIdx = address.indexOf(pre);

                if (preIdx != 0 && preIdx != -1) {
                    addPrev.append(address.substring(0, preIdx));
                }
                if (preIdx != -1) {
                    addPrev.append(pre);
                }
            }
            if (pre != null && preIdx == -1) {
                boolean startFlg = false;
                for (int i = 0; i < address.length(); i++) {
                    if (address.substring(i, i + 1).equals("\\")
                            && address.substring(i + 1, i + 2).equals("\"")) {
                        addPrev.append(address.substring(i, i + 2));
                        i = i + 1;
                        continue;
                    }
                    if (address.substring(i, i + 1).equals("\"")) {
                        if (!startFlg) {
                            startFlg = true;
                            addPrev.append(address.substring(i, i + 1));
                            continue;
                        } else {
                            startFlg = false;
                            if (address.substring(i + 1, i + 2).equals("<")) {
                                addPrev.append(address.substring(i, i + 2));
                                break;
                            }
                        }
                    }
                    if (!startFlg && address.substring(i, i + 1).equals("<")) {
                        addPrev.append(address.substring(i, i + 1));
                        break;
                    }
                    addPrev.append(address.substring(i, i + 1));
                    continue;
                }
                addressData.setAddressPrev(addPrev.toString());
            }

            int prevLen = addPrev.length();
            int adrIdx = address.indexOf(add, prevLen);
            if (prevLen < adrIdx && preIdx != -1) {
                addPrev.append(address.substring(prevLen, adrIdx));
                addressData.setAddressPrev(addPrev.toString());
            }
            addressData.setAddress(add);
            addressData.setUser(add.substring(0, add.indexOf("@")));

            if (addressData.getAddressPrev() != null) {
                iAdd.append(addressData.getAddressPrev() + addressData.getAddress());
            } else {
                iAdd.append(addressData.getAddress());
            }

            String domain = add.substring(add.indexOf("@") + 1);
            addressData.setDomain(domain);

            int startIdx = iAdd.length();
            int endIdx = iAdd.length();
            for (int i = startIdx; i < address.length(); i++) {
                endIdx = i + 1;
                if (!address.substring(i, i + 1).equals("\\")
                        && !address.substring(i, i + 1).equals("\"")
                        && !address.substring(i, i + 1).equals("(")
                        && !address.substring(i, i + 1).equals("[")
                        && !address.substring(i, i + 1).equals(",")) {
                    continue;
                }
                if (address.substring(i, i + 1).equals("\\")) {
                    continue;
                }
                if (address.substring(i, i + 1).equals(",")) {
                    endIdx = i;
                    break;
                }
                if (address.substring(i, i + 1).equals("\"")) {
                    int index = address.indexOf("\"", i + 1);
                    if (index != -1 && !address.substring(index - 1, index).equals("\\")) {
                        i = index;
                        endIdx = index + 1;
                        continue;
                    }
                    index = address.indexOf(",", i + 1);
                    if (index != -1) {
                        endIdx = index;
                        break;
                    }
                    continue;
                }
                if (address.substring(i, i + 1).equals("(")) {
                    int index = address.indexOf("]", i + 1);
                    if (index != -1 && !address.substring(index - 1, index).equals("\\")) {
                        i = index;
                        endIdx = index + 1;
                        continue;
                    }
                    index = address.indexOf(",", i + 1);
                    if (index != -1) {
                        endIdx = index;
                        break;
                    }
                    continue;
                }
                if (address.substring(i, i + 1).equals("[")) {
                    int index = address.indexOf("]", i + 1);
                    if (index != -1 && !address.substring(index - 1, index).equals("\\")) {
                        i = index;
                        endIdx = index + 1;
                        continue;
                    }
                    index = address.indexOf(",", i + 1);
                    if (index != -1) {
                        endIdx = index;
                        break;
                    }
                    continue;
                }
            }
            if (startIdx < endIdx) {
                addressData.setAddressAfter(address.substring(startIdx, endIdx));
            }
            int adrLength = address.indexOf(",", endIdx);
            address = address.substring(adrLength + 1).trim();

            String type = domainMap.get(domain);
            if (type != null) {
                addressData.setDomainType(type);
            } else {
                domainType__++;
                if (domainType__ > Wml012AddressModel.MAX_DOMAINTYPE) {
                    domainType__ = Wml012AddressModel.MIN_DOMAINTYPE;
                }
                addressData.setDomainType(Integer.toString(domainType__));
                domainMap.put(domain, addressData.getDomainType());
            }
            addressList.add(addressData);
        }
        return addressList;
    }

    /**
     * <br>[機  能] アドレス一覧から送信先情報一覧を取得する
     * <br>[解  説] WEB APIで使用
     * <br>[備  考]
     * @param address 送信先メールアドレス一覧(カンマ区切り)
     * @param domainMap ドメインとドメイン種別のMapping
     * @return 送信先情報一覧
     */
    public List<Wml012AddressModel> getAddressList(String address, Map<String, String> domainMap) {
        List<Wml012AddressModel> ret = __createAddressList(address, domainMap);
        if (ret != null) {
            for (Wml012AddressModel mdl : ret) {
                if (mdl.getAddress() == null) {
                    mdl.setAddress(NullDefault.getString(mdl.getUser(), "")
                                   + "@"
                                   + NullDefault.getString(mdl.getDomain(), "")
                                   + mdl.getDomainEnd());
                }
                if (mdl.getDomainEnd().length() > 0 && mdl.getUser() != null) {
                    String userStr = mdl.getUser();
                    int atIndex = userStr.lastIndexOf("<");
                    if (atIndex > 0) {
                        mdl.setUser(userStr.substring(atIndex + 1, userStr.length()));
                    }
                }
            }
        }
        return ret;
    }
}
