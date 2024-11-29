package jp.groupsession.v2.wml.wml250;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.wml.biz.WmlBiz;
import jp.groupsession.v2.wml.dao.base.WmlAccountDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateFileDao;
import jp.groupsession.v2.wml.model.base.WmlAccountModel;
import jp.groupsession.v2.wml.model.base.WmlMailTemplateModel;

/**
 * <br>[機  能] WEBメール メールテンプレート登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml250Biz {
    /** 画面ID */
    public static final String SCR_ID = "wml250";

    /**
     * <br>[機  能] 初期表示設定を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param appRootPath アプリケーションルートパス
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 添付ファイル情報の取得に失敗
     * @throws IOException 添付ファイル情報の複製に失敗
     * @throws IOToolsException 添付ファイル情報の複製に失敗
     */
    public void setInitData(Connection con,
                          Wml250ParamModel paramMdl,
                          RequestModel reqMdl,
                          String appRootPath)
    throws SQLException, TempFileException, IOException, IOToolsException {

        //アカウント名取得
        if (paramMdl.getWmlMailTemplateKbn() != GSConstWebmail.MAILTEMPLATE_COMMON) {
            WmlAccountDao wacDao = new WmlAccountDao(con);
            WmlAccountModel wacMdl = wacDao.select(paramMdl.getWmlAccountSid());
            paramMdl.setWml240accountName(wacMdl.getWacName());
        }

        CommonBiz cmnBiz = new CommonBiz();
        String tempDir = getTempDir(reqMdl);

        //初期表示　編集
        if (paramMdl.getWml250initKbn() == GSConstWebmail.DSP_FIRST) {
            //テンポラリディレクトリを削除(初期化)
            WmlBiz wmlBiz = new WmlBiz();
            wmlBiz.clearTempDir(reqMdl, SCR_ID);

            if (paramMdl.getWmlTemplateCmdMode() == GSConstWebmail.CMDMODE_EDIT) {

                //メールテンプレート情報設定
                int wtpSid = paramMdl.getWmlEditTemplateId();
                WmlMailTemplateDao templateDao = new WmlMailTemplateDao(con);
                WmlMailTemplateModel templateData = templateDao.select(wtpSid);
                paramMdl.setWml250TemplateName(templateData.getWtpName());
                paramMdl.setWml250Title(templateData.getWtpTitle());
                paramMdl.setWml250Content(templateData.getWtpBody());

                WmlMailTemplateFileDao templateFileDao = new WmlMailTemplateFileDao(con);
                String[] binSid = templateFileDao.getBinSid(wtpSid);
                if (binSid != null && binSid.length > 0) {

                    String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)

                    //添付ファイル情報を取得する
                    List<CmnBinfModel> cmList = cmnBiz.getBinInfo(con, binSid, reqMdl.getDomain());

                    int fileNum = 1;
                    for (CmnBinfModel cbMdl : cmList) {
                        if (cbMdl.getBinJkbn() == GSConst.JTKBN_DELETE) {
                            continue;
                        }

                        //添付ファイルをテンポラリディレクトリにコピーする。
                        cmnBiz.saveTempFile(dateStr, cbMdl, appRootPath, tempDir, fileNum);

                        fileNum++;
                    }
                }
            }
        }

        //本文の最大長を設定
        int bodyLimit = WmlBiz.getBodyLimitLength(appRootPath);
        if (bodyLimit == GSConstWebmail.MAILBODY_LIMIT_NOLIMIT) {
            bodyLimit = -1;
        }
        paramMdl.setContentMaxLen(bodyLimit);

        //添付ファイル情報を設定
        paramMdl.setFileList(cmnBiz.getTempFileLabelList(tempDir));

        //初期表示完了
        paramMdl.setWml250initKbn(GSConstWebmail.DSP_ALREADY);
    }

    /**
     * <br>[機  能] テンポラリディレクトリパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     * @return テンポラリディレクトリパス
     */
    public String getTempDir(RequestModel reqMdl) {
        WmlBiz wmlBiz = new WmlBiz();
        return wmlBiz.getTempDir(reqMdl, SCR_ID);
    }

    /**
     * <br>[機  能] テンポラリディレクトリを削除する
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public void deleteTempDir(RequestModel reqMdl) {
        WmlBiz wmlBiz = new WmlBiz();
        wmlBiz.deleteTempDir(reqMdl, SCR_ID);
    }
}
