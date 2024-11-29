package jp.groupsession.v2.cmn.formmodel.prefarence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.formmodel.Temp;
import jp.groupsession.v2.cmn.model.RequestModel;

/**
 * <br>[機  能]
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class TempPrefarence extends Temp {

    @Override
    public void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn) {
        JSONObject jsonObj = null;
        if (json instanceof JSONObject) {
            jsonObj = (JSONObject) json;
            try {
                __sampleListMergeJson(jsonObj);

                __sampleMerageJson(jsonObj);
            } catch (JSONException e) {
            }
        }
    }
    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException , IOToolsException{
        super.dspInit(reqMdl, con);
        setSampleList(getFileList());
    }
    /**
     *
     * <br>[機  能]sampleList用
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonObj jsonObj
     */
    private void __sampleListMergeJson(JSONObject jsonObj) {
        try {
            JSONArray fileListObj = jsonObj.getJSONArray("sampleList");
            LabelValueBean[] fileList =
                    (LabelValueBean[]) JSONArray.toArray(fileListObj, LabelValueBean.class);
            List<LabelValueBean> list = new ArrayList<LabelValueBean>();
            for (LabelValueBean lvb : fileList) {
                list.add(lvb);
            }
            setSampleList(list);
        } catch (JSONException e) {
        }
    }

    /**
     *
     * <br>[機  能]sample用
     * <br>[解  説]
     * <br>[備  考]
     * @param jsonObj jsonObj
     */
    private void __sampleMerageJson(JSONObject jsonObj) {
        try {
            JSONArray sampleObj = jsonObj.getJSONArray("sample");
            String[] sample = (String[]) JSONArray.toArray(sampleObj, String.class);
            setSample(sample);
        } catch (JSONException e) {
        }
    }



    @Override
    public String toJSONString() {
        return super.toJSONString();
    }


}
