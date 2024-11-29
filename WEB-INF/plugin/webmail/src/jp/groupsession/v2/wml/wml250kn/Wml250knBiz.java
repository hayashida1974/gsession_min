package jp.groupsession.v2.wml.wml250kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.wml.biz.WmlUsedDataBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateFileDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlMailTemplateFileModel;
import jp.groupsession.v2.wml.model.base.WmlMailTemplateModel;
import jp.groupsession.v2.wml.wml250.Wml250Biz;

/**
 * <br>[機  能] WEBメール メールテンプレート登録確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml250knBiz extends Wml250Biz {

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException 添付ファイル情報の設定に失敗
     */
    public void setInitData(Connection con,  Wml250knParamModel paramMdl,
                                    RequestModel reqMdl)
    throws SQLException, IOToolsException {
        //アカウント名取得
        if (paramMdl.getWmlMailTemplateKbn() != GSConstWebmail.MAILTEMPLATE_COMMON) {
            WmlAccountDao wacDao = new WmlAccountDao(con);
            WmlAccountModel wacMdl = wacDao.select(paramMdl.getWmlAccountSid());
            paramMdl.setWml240accountName(wacMdl.getWacName());
        }

        //内容を設定
        paramMdl.setWml250knViewContent(
                StringUtilHtml.transToHTmlPlusAmparsant(
                        NullDefault.getString(paramMdl.getWml250Content(), "")));

        //添付ファイル情報を設定
        String tempDir = getTempDir(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        paramMdl.setFileList(cmnBiz.getTempFileLabelList(tempDir));
    }

    /**
     * <br>[機  能] メールテンプレートの登録を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @param cntCon 採番コントローラ
     * @throws SQLException SQL実行例外
     * @throws IOToolsException IOエラー
     * @throws TempFileException 添付ファイルの登録に失敗
     */
    public void entryTemplate(
            Connection con,
            Wml250knParamModel paramMdl,
            RequestModel reqMdl,
            String appRootPath,
            MlCountMtController cntCon) throws SQLException, IOToolsException, TempFileException {

        int userSid = reqMdl.getSmodel().getUsrsid();
        UDate now = new UDate();

        WmlMailTemplateModel templateMdl = new WmlMailTemplateModel();
        int wtpType = paramMdl.getWmlMailTemplateKbn();
        int wacSid = paramMdl.getWmlAccountSid();
        if (wtpType == GSConstWebmail.MAILTEMPLATE_COMMON) {
            wtpType = GSConstWebmail.WTP_TYPE_COMMON;
            wacSid = 0;
        } else {
            wtpType = GSConstWebmail.WTP_TYPE_ACCOUNT;
        }
        templateMdl.setWtpType(wtpType);
        templateMdl.setWacSid(wacSid);

        templateMdl.setWtpName(paramMdl.getWml250TemplateName());
        templateMdl.setWtpTitle(paramMdl.getWml250Title());
        templateMdl.setWtpBody(paramMdl.getWml250Content());
        templateMdl.setWtpEuid(userSid);
        templateMdl.setWtpEdate(now);

        int wtpSid = paramMdl.getWmlEditTemplateId();
        WmlMailTemplateDao templateDao = new WmlMailTemplateDao(con);
        int order = templateDao.getMaxOrder(wtpType, wacSid);

        WmlUsedDataBiz usedDataBiz = new WmlUsedDataBiz(con);
        WmlMailTemplateFileDao templateFileDao = new WmlMailTemplateFileDao(con);
        if (paramMdl.getWmlTemplateCmdMode() == GSConstWebmail.CMDMODE_EDIT) {
            //メールテンプレート情報のデータ使用量を登録(削除対象のデータ使用量を減算)
            usedDataBiz.insertTemplateSize(wtpSid, false);

            //メールテンプレート情報を更新
            templateMdl.setWtpSid(wtpSid);
            templateDao.update(templateMdl);

            //メールテンプレート_ファイル情報を削除
            templateFileDao.removeTemplateBinData(wtpSid, userSid, now);
            templateFileDao.delete(wtpSid);

        } else {
            //メールテンプレート情報を新規登録
            wtpSid = (int) cntCon.getSaibanNumber(
                    GSConstWebmail.SBNSID_WEBMAIL,
                    GSConstWebmail.SBNSID_SUB_TEMPLATE_M,
                    userSid);
            templateMdl.setWtpSid(wtpSid);
            templateMdl.setWtpOrder(order + 1);
            templateMdl.setWtpAuid(userSid);
            templateMdl.setWtpAdate(now);

            templateDao.insert(templateMdl);
        }

        //メールテンプレート_ファイル情報を登録
        String tempDir = getTempDir(reqMdl);
        CommonBiz cmnBiz = new CommonBiz();
        List<String> binSidList
            = cmnBiz.insertBinInfo(con, tempDir, appRootPath, cntCon, userSid, now);
        if (!binSidList.isEmpty()) {
            WmlMailTemplateFileModel templateFileMdl = new WmlMailTemplateFileModel();
            templateFileMdl.setWtpSid(wtpSid);
            for (String binSid : binSidList) {
                templateFileMdl.setBinSid(Long.parseLong(binSid));
                templateFileDao.insert(templateFileMdl);
            }
        }

        //メールテンプレート情報のデータ使用量を登録
        usedDataBiz.insertTemplateSize(wtpSid, true);
    }
}
