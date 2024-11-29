package jp.groupsession.v2.cmn.formmodel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.json.JSONString;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormInputBuilder;
import jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 添付ファイル要素モデル
 * <br>[解  説] 稟議フォームで使用
 * <br>[備  考]
 *
 * @author JTS
 */
public class Temp
extends AbstractFormModel implements JSONString {
    /**ファイル*/
    private String[] files__;
    /** 添付一覧 */
    private List<LabelValueBean> fileList__ = new ArrayList<LabelValueBean>();
    /** テンポラリパス*/
    private GSTemporaryPathModel tempPath__;
    /** サンプル*/
    private String[] sample__;
    /** サンプル一覧 */
    private List<LabelValueBean> sampleList__ = new ArrayList<LabelValueBean>();
    /** ダウンロードURL */
    private String downloadUrl__ = null;
    /** バイナリSID一覧 */
    private List<Long> binSids__ = new ArrayList<Long>();

    /** ガラケー用 選択削除ファイルIDマップ keyに選択選択削除ファイルIDが格納されます*/
    private Map<String, String> selectDelFileMbh__ = new HashMap<>();

    /**
     * <p>files を取得します。
     * @return files
     * @see jp.groupsession.v2.cmn.formmodel.Temp#files__
     */
    public String[] getFiles() {
        return files__;
    }

    /**
     * <p>files をセットします。
     * @param files files
     * @see jp.groupsession.v2.cmn.formmodel.Temp#files__
     */
    public void setFiles(String[] files) {
        files__ = files;
    }

    /**
     * <p>fileList を取得します。
     * @return fileList
     * @see jp.groupsession.v2.cmn.formmodel.Temp#fileList__
     */
    public List<LabelValueBean> getFileList() {
        return fileList__;
    }

    /**
     * <p>fileList をセットします。
     * @param fileList fileList
     * @see jp.groupsession.v2.cmn.formmodel.Temp#fileList__
     */
    public void setFileList(List<LabelValueBean> fileList) {
        fileList__ = fileList;
    }

    /**
     * <p>tempPath を取得します。
     * @return tempPath
     * @see jp.groupsession.v2.cmn.formmodel.Temp#tempPath__
     */
    public GSTemporaryPathModel getTempPath() {
        return tempPath__;
    }

    /**
     * <p>tempPath をセットします。
     * @param tempPath tempPath
     * @see jp.groupsession.v2.cmn.formmodel.Temp#tempPath__
     */
    public void setTempPath(GSTemporaryPathModel tempPath) {
        tempPath__ = tempPath;
    }
    /**
     *
     * <br>[機  能] 添付ファイル用のフォルダ名生成用
     * <br>[解  説]
     * <br>[備  考] {formSid}_{blockSid}
     * @param formSid フォームSID
     * @param blockSid ブロックSID
     * @return 添付ファイルフォルダ名
     */
    public String createTempFolderName(int formSid, int blockSid) {
        String cellPath = formSid + "_" + blockSid;
        return cellPath;
    }
    /**
     * <p>sample を取得します。
     * @return sample
     * @see jp.groupsession.v2.cmn.formmodel.Temp#sample__
     */
    public String[] getSample() {
        return sample__;
    }

    /**
     * <p>sample をセットします。
     * @param sample sample
     * @see jp.groupsession.v2.cmn.formmodel.Temp#sample__
     */
    public void setSample(String[] sample) {
        sample__ = sample;
    }

    /**
     * <p>sampleList を取得します。
     * @return sampleList
     * @see jp.groupsession.v2.cmn.formmodel.Temp#sampleList__
     */
    public List<LabelValueBean> getSampleList() {
        return sampleList__;
    }

    /**
     * <p>fileList を取得します。
     * @return fileList
     * @see jp.groupsession.v2.cmn.formmodel.Temp#fileList__
     */
    public List<String> getSampleListLabel() {
        List<String> labelList = new ArrayList<String>();
        for (LabelValueBean lvb : sampleList__) {
            labelList.add(lvb.getLabel());
        }
        return labelList;
    }



    /**
     * <p>sampleList をセットします。
     * @param sampleList sampleList
     * @see jp.groupsession.v2.cmn.formmodel.Temp#sampleList__
     */
    public void setSampleList(List<LabelValueBean> sampleList) {
        sampleList__ = sampleList;
    }

    /**
     * <p>downloadUrl を取得します。
     * @return downloadUrl
     * @see jp.groupsession.v2.cmn.formmodel.Temp#downloadUrl
     */
    public String getDownloadUrl() {
        return downloadUrl__;
    }

    /**
     * <p>downloadUrl をセットします。
     * @param downloadUrl downloadUrl
     * @see jp.groupsession.v2.cmn.formmodel.Temp#downloadUrl
     */
    public void setDownloadUrl(String downloadUrl) {
        downloadUrl__ = downloadUrl;
    }

    /**
     * <p>binSids を取得します。
     * @return binSids
     * @see jp.groupsession.v2.cmn.formmodel.Temp#binSids__
     */
    public List<Long> getBinSids() {
        return binSids__;
    }

    /**
     * <p>binSids をセットします。
     * @param binSids binSids
     * @see jp.groupsession.v2.cmn.formmodel.Temp#binSids__
     */
    public void setBinSids(List<Long> binSids) {
        binSids__ = binSids;
    }

    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {
        msgForm.addHiddenParam(paramName + ".blockIdx", getBlockIdx());
    }

    @Override
    public void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn) {
        JSONObject jsonObj = null;
        if (json instanceof JSONObject) {
            jsonObj = (JSONObject) json;
            try {
                if (jsonObj.containsKey("sampleList")) {
                    JSONArray fileListObj = jsonObj.getJSONArray("sampleList");
                    LabelValueBean[] fileList =
                            (LabelValueBean[]) JSONArray.toArray(fileListObj, LabelValueBean.class);
                    List<LabelValueBean> list = new ArrayList<LabelValueBean>();
                    for (LabelValueBean lvb : fileList) {
                        list.add(lvb);
                    }
                    setSampleList(list);
                }
            } catch (JSONException e) {
            }
            try {
                if (jsonObj.containsKey("sample")) {
                    JSONArray sampleObj = jsonObj.getJSONArray("sample");
                    String[] sample = (String[]) JSONArray.toArray(sampleObj, String.class);
                    setSample(sample);
                }
            } catch (JSONException e) {
            }
        }
    }

    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.file;
    }
    @Override
    public void merge(AbstractFormModel obj) {
        super.merge(obj);

        if (this == obj) {
            return;
        }
        if (obj == null) {
            return;
        }
        if (!(obj instanceof Temp)) {
            return;
        }

        Temp other = (Temp) obj;

        String[] files = getFiles();
        if (ArrayUtils.isEmpty(files)) {
            setFiles(other.getFiles());
        }

        Map<String, String> delFileMbh = getSelectDelFileMbh();
        Map<String, String> delFileMbhOth = other.getSelectDelFileMbh();
        if (!delFileMbhOth.isEmpty()) {
            for (Entry<String, String> entry:delFileMbhOth.entrySet()) {
                if (!delFileMbh.containsKey(entry.getKey())) {
                    getSelectDelFileMbh().put(entry.getKey(), entry.getValue());
                }
            }
        }

    }

    @Override
    public void merge(String[] values) {
        if (values != null) {
            if (binSids__ == null) {
                binSids__ = new ArrayList<Long>();
            } else {
                binSids__.clear();
            }
            for (int i = 0; i < values.length; i++) {
                binSids__.add(Long.valueOf(values[i]));
            }
        }
    }

    @Override
    public void defaultInit() {

    }

    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((fileList__ == null) ? 0 : fileList__.hashCode());
        result = prime * result + Arrays.hashCode(files__);
        result = prime * result + Arrays.hashCode(sample__);
        result = prime * result
                + ((tempPath__ == null) ? 0 : tempPath__.hashCode());
        return result;
    }

    /* (非 Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Temp other = (Temp) obj;
        if (fileList__ == null) {
            if (other.fileList__ != null) {
                return false;
            }
        } else if (!fileList__.equals(other.fileList__)) {
            return false;
        }
        if (!Arrays.equals(files__, other.files__)) {
            return false;
        }
        if (!Arrays.equals(sample__, other.sample__)) {
            return false;
        }
        if (tempPath__ == null) {
            if (other.tempPath__ != null) {
                return false;
            }
        } else if (!tempPath__.equals(other.tempPath__)) {
            return false;
        }
        return true;
    }

    @Override
    public void dspInit(RequestModel reqMdl, Connection con) throws SQLException, IOToolsException {

        FormInputInitPrefarence initPref = _getInitPref();
        if (initPref != null) {
            __dspInputInit(reqMdl, con, initPref);
        }

        if (tempPath__ != null) {
            try {
                Map<String, String> delFileMbh = getSelectDelFileMbh();



                if (!delFileMbh.isEmpty()) {
                    //テンポラリディレクトリパスを取得
                    for (Entry<String, String> entry:delFileMbh.entrySet()) {
                        String key = entry.getKey();
                        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
                        tempPathUtil.deleteFile(new String[] { key }, tempPath__);
                    }

                }


                CommonBiz cmnBiz = new CommonBiz();
                setFileList(cmnBiz.getTempFileLabelList(tempPath__.getTempPath()));
            } catch (IOToolsException ie) {
            }
        }
    }


    private void __dspInputInit(RequestModel reqMdl, Connection con,
            FormInputInitPrefarence initPref) throws IOToolsException {
        String url = initPref.getUrl();
        GSTemporaryPathModel tempDir = initPref.getTempDir();
        setDownloadUrl(url);
        setTempPath(
                new GSTemporaryPathModel(tempDir,
                        createTempFolderName(_getCellInfo().getSid(), getBlockIdx())));
        if (initPref.getMode() == FormInputBuilder.INITMODE_INPUT) {
            GSTemporaryPathUtil tempUtil = GSTemporaryPathUtil.getInstance();

            tempUtil.createTempDir(getTempPath());
        }

    }

    /**
     * <br>[機  能] ダウンロードURLを生成する
     * <br>[解  説] URL文字列にある「{binSid}」「 {dirId} 」を置換
     * <br>[備  考] 基本となるダウンロードURLは事前にセットしておく
     * @param key    バイナリSID or ファイル識別キー(「{binSid}」と置換する文字列)
     * @param dirId  ディレクトリ識別ID(「{dirId}」と置換する文字列)
     * @return ダウンロードURL文字列
     */
    public String getBinDownloadUrl(String key, String dirId) {
        String url = null;
        if (!StringUtil.isNullZeroString(downloadUrl__)) {
            url = new String(downloadUrl__);
            url = url.replaceAll("\\{binSid\\}", NullDefault.getString(key, ""));
            url = url.replaceAll("\\{dirId\\}", NullDefault.getString(dirId,""));
        }
        return url;
    }

    @Override
    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("sample", getSample());
        obj.put("sampleList", getSampleList());
        return obj.toString();
    }

    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (info.chkRequire()) {
            //未入力チェック
            List <String> fileList = this.getBinFiles();
            if (fileList.isEmpty()) {
                msg = new ActionMessage("error.select.required.text",
                        info.outputName(gsMsg));
                errors.add(info.getPath(), msg);
            }
        }
    }

    @Override
    public void removeRow(int formSid, int rowId) {

        FormInputInitPrefarence initPref = _getInitPref();
        if (initPref == null) {
            return;
        }
        GSTemporaryPathModel tempPath = new GSTemporaryPathModel(initPref.getTempDir(),
                createTempFolderName(formSid, getBlockIdx()));
        GSTemporaryPathUtil tempPathUtil = GSTemporaryPathUtil.getInstance();
        tempPathUtil.deleteTempPath(tempPath);

    }

    /**
     * <br>[機  能] テンポラリディレクトリにあるファイル名一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @return ファイル名一覧取得
     */
    public List<String> getBinFiles() {
        List<String> binFiles = new ArrayList<String>();
        if (tempPath__ != null) {
            List <String> fileList = IOTools.getFileNames(tempPath__.getTempPath());
            if (fileList != null) {
                binFiles.addAll(fileList);
            }
        }
        return binFiles;
    }

    /**
     * <p>selectDelFileMbh を取得します。
     * @return selectDelFileMbh
     * @see jp.groupsession.v2.cmn.formmodel.Temp#selectDelFileMbh__
     */
    public Map<String, String> getSelectDelFileMbh() {
        return selectDelFileMbh__;
    }

    /**
     * <p>selectDelFileMbh をセットします。
     * @param selectDelFileMbh selectDelFileMbh
     * @see jp.groupsession.v2.cmn.formmodel.Temp#selectDelFileMbh__
     */
    public void setSelectDelFileMbh(Map<String, String> selectDelFileMbh) {
        selectDelFileMbh__ = selectDelFileMbh;
    }
}
