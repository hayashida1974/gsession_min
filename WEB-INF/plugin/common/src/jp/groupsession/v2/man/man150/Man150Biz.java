package jp.groupsession.v2.man.man150;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.io.ObjectFile;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GroupSession;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.lic.GSConstLicese;
import jp.groupsession.v2.lic.LicenseModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] メイン 管理者設定 ライセンスファイル登録・更新画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man150Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Man150Biz.class);

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     *
     * @param paramMdl パラメータ情報
     * @param reqMdl リクエスト情報
     * @param tempDir テンポラリディレクトリパス
     * @param domain ドメイン
     * @throws SQLException SQL実行例外
     * @throws IOToolsException ファイルアクセス時例外
     */
    public void setInitData(Man150ParamModel paramMdl, RequestModel reqMdl,
                            String tempDir, String domain)
        throws SQLException, IOToolsException {

        //現在有効なライセンス情報
        String licenseId = "";
        String licenseCom = "";
        ArrayList<LicenseModel> pluginList = new ArrayList<LicenseModel>();
        LicenseModel pmdl = null;
        GsMessage gsMsg = new GsMessage(reqMdl);


        LicenseModel lmdl
               = (LicenseModel) GroupSession.getResourceManager().getLicenseMdl(domain);
        if (lmdl != null) {

            if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseId())) {
                licenseId = String.valueOf(lmdl.getLicenseId());
            }

            if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseCom())) {
                licenseCom = String.valueOf(lmdl.getLicenseCom());
            }

            //プラグイン情報 サポート
            if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitSupport())) {
                pmdl = new LicenseModel();
                pmdl.setPluginName(gsMsg.getMessage("cmn.support"));
                pmdl.setLicenseLimit(lmdl.getLicenseLimitSupport());
                pluginList.add(pmdl);
            }

            //プラグイン情報 モバイル
            if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitMobile())) {
                pmdl = new LicenseModel();
                pmdl.setPluginName(gsMsg.getMessage("mobile.4"));
                pmdl.setLicenseLimit(lmdl.getLicenseLimitMobile());
                pluginList.add(pmdl);
            }

            //プラグイン情報 CrossRide
            if (!StringUtil.isNullZeroStringSpace(lmdl.getLicenseLimitCrossRide())) {
                pmdl = new LicenseModel();
                pmdl.setPluginName(GSConstLicese.PLUGIN_NAME_CROSSRIDE);
                pmdl.setLicenseLimit(lmdl.getLicenseLimitCrossRide());
                pluginList.add(pmdl);
            }
        }

        paramMdl.setMan150LicenseId(licenseId);
        paramMdl.setMan150LicenseCom(licenseCom);
        paramMdl.setMan150PluginList(pluginList);

        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);

        //画面に表示するファイルのリストを作成
        ArrayList<LabelValueBean> fileLblList = new ArrayList<LabelValueBean>();

        if (fileList != null) {

            log__.debug("ファイルの数×２(オブジェクトと本体) = " + fileList.size());

            for (int i = 0; i < fileList.size(); i++) {

                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }

                String[] value = fileName.split(GSConstCommon.ENDSTR_OBJFILE);

                //表示用リストへ追加
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                fileLblList.add(new LabelValueBean(fMdl.getFileName(), value[0]));
                log__.debug("ファイル名 = " + fMdl.getFileName());
                log__.debug("保存ファイル名 = " + fMdl.getSaveFileName());
            }
        }
        paramMdl.setMan150FileLabelList(fileLblList);
    }
}