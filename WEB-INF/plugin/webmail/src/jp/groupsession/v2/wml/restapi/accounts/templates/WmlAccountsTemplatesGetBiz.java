package jp.groupsession.v2.wml.restapi.accounts.templates;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.restapi.controller.RestApiContext;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateDao;
import jp.groupsession.v2.wml.dao.base.WmlMailTemplateFileDao;
import jp.groupsession.v2.wml.model.base.WmlMailTemplateModel;
import jp.groupsession.v2.wml.restapi.biz.WmlRestAccountBiz;
import jp.groupsession.v2.wml.restapi.model.TmpFileInfo;

/**
 * <br>[機  能] WEBメール テンプレート一覧取得API ビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 */
public class WmlAccountsTemplatesGetBiz {

    /** 実行結果*/
    private final List<WmlAccountsTemplatesResultModel> result__ = new ArrayList<>();
    /** コンテキスト */
    private final RestApiContext ctx__;
    /** コネクション */
    private final Connection con__;

    /**
     * 
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public WmlAccountsTemplatesGetBiz(RestApiContext ctx) {
        ctx__ = ctx;
        con__ = ctx__.getCon();
    }

    /**
     *
     * <br>[機  能] 送信先リストの取得
     * <br>[解  説]
     * <br>[備  考]
     * @throws SQLException SQL実行時例外
     */
    public void execute(WmlAccountsTemplatesGetParamModel paramMdl) throws SQLException {

        //アカウントSIDを取得
        WmlRestAccountBiz wraBiz = new WmlRestAccountBiz();
        int wacSid = wraBiz.getWmlAccountSid(con__, paramMdl.getAccountId());

        //テンプレート一覧を取得
        WmlMailTemplateDao wmlTmpDao = new WmlMailTemplateDao(con__);
        List<WmlMailTemplateModel> tmpMdlList = wmlTmpDao.getMailTemplateList(wacSid);
        for (WmlMailTemplateModel tmpMdl : tmpMdlList) {
            WmlAccountsTemplatesResultModel resultMdl = new WmlAccountsTemplatesResultModel();
            resultMdl.setSid(tmpMdl.getWtpSid());
            resultMdl.setType(tmpMdl.getWtpType());
            resultMdl.setName(tmpMdl.getWtpName());
            resultMdl.setSubjectText(tmpMdl.getWtpTitle());
            resultMdl.setBodyText(tmpMdl.getWtpBody());
            //バイナリSIDを取得
            WmlMailTemplateFileDao wmlTmpFileDao = new WmlMailTemplateFileDao(con__);
            String[] binSids = wmlTmpFileDao.getBinSid(tmpMdl.getWtpSid());
            //添付ファイル情報を取得
            CmnBinfDao cmnBinfDao = new CmnBinfDao(con__);
            List<CmnBinfModel> cmnBinfMdlList = cmnBinfDao.select(binSids);
            List<TmpFileInfo> tmpFileArray = new ArrayList<TmpFileInfo>();
            for (CmnBinfModel cmnBinfMdl : cmnBinfMdlList) {
                TmpFileInfo tmpFileInf = new TmpFileInfo();
                tmpFileInf.setBinSid(cmnBinfMdl.getBinSid());
                tmpFileInf.setFileName(cmnBinfMdl.getBinFileName());
                tmpFileInf.setFileSizeByteNum(cmnBinfMdl.getBinFileSize());
                tmpFileArray.add(tmpFileInf);
            }
            resultMdl.setTmpFileArray(tmpFileArray);
            result__.add(resultMdl);
        }
    }

    /**
     *
     * <br>[機  能] 実行結果の取得
     * <br>[解  説]
     * <br>[備  考]
     * @return 実行結果
     */
    public List<WmlAccountsTemplatesResultModel> getResult() {
        return result__;
    }
}
