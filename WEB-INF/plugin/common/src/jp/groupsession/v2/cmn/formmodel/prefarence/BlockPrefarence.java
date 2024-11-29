package jp.groupsession.v2.cmn.formmodel.prefarence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.formbuilder.FormCell;
import jp.groupsession.v2.cmn.formbuilder.FormCellPrefarence;
/**
 *
 * <br>[機  能] 設定用ブロック要素
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BlockPrefarence extends jp.groupsession.v2.cmn.formmodel.Block {
    /**
    *
    * <br>[機  能] json文字列からフォーム情報を設定する
    * <br>[解  説]
    * <br>[備  考]
    * @param json JSON文字列
    */
   @SuppressWarnings("rawtypes")
   public void setFormTable(JSON json) {
        setFormTable(new ArrayList<List<FormCell>>());

        JSONArray jsonArr = null;
        if (json instanceof JSONArray) {
            jsonArr = (JSONArray) json;
        }
        if (jsonArr == null) {
            return;
        }
        Map<TblIndex, JSONObject> cellJsonMap = new HashMap<TblIndex, JSONObject>();
        int row = 0;
        for (Object rowobj : jsonArr) {
            if (rowobj instanceof List) {
                int col = 0;
                for (Object colobj : (List) rowobj) {
                    if (colobj instanceof JSONObject) {
                        cellJsonMap.put(new TblIndex(row, col),
                                (JSONObject) colobj);
                        col++;
                    }
                }
                row++;
            }
        }
        for (Entry<TblIndex, JSONObject> entry:cellJsonMap.entrySet()) {
            TblIndex idx = entry.getKey();
            FormCellPrefarence cell = new FormCellPrefarence(entry.getValue());
            setFormCell(idx.getRow(), idx.getCol(), cell);
        }
    }

}
