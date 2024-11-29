package jp.groupsession.v2.cmn.formmodel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormAccesser;
import jp.groupsession.v2.cmn.formbuilder.FormCalcBiz;
import jp.groupsession.v2.cmn.formbuilder.FormCell;
import jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence;
import jp.groupsession.v2.cmn.formbuilder.IFormAccessMapMakable;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.model.RequestModel;
/**
 *
 * <br>[機  能] ブロック要素
 * <br>[解  説] 子として入力要素を配置できる。
 * <br>[備  考] 表要素、ブロック要素を子に持つことはできない
 *
 * @author JTS
 */
public class Block
extends AbstractFormModel
implements IFormAccessMapMakable {
    /**
     *
     * <br>[機  能] テーブル上の位置情報を扱う
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    protected static class TblIndex {
        /** 行番号 */
        private int row__;
        /** 列番号 */
        private int col__;
        /**
         * @param row 行番号
         * @param col 列番号
         */
        public TblIndex(int row, int col) {
            super();
            row__ = row;
            col__ = col;
        }
        /* (非 Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + col__;
            result = prime * result + row__;
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
            TblIndex other = (TblIndex) obj;
            if (col__ != other.col__) {
                return false;
            }
            if (row__ != other.row__) {
                return false;
            }
            return true;
        }
        /**
         * <p>row を取得します。
         * @return row
         * @see jp.groupsession.v2.cmn.formmodel.Block.TblIndex#row__
         */
        public int getRow() {
            return row__;
        }
        /**
         * <p>row をセットします。
         * @param row row
         * @see jp.groupsession.v2.cmn.formmodel.Block.TblIndex#row__
         */
        public void setRow(int row) {
            row__ = row;
        }
        /**
         * <p>col を取得します。
         * @return col
         * @see jp.groupsession.v2.cmn.formmodel.Block.TblIndex#col__
         */
        public int getCol() {
            return col__;
        }
        /**
         * <p>col をセットします。
         * @param col col
         * @see jp.groupsession.v2.cmn.formmodel.Block.TblIndex#col__
         */
        public void setCol(int col) {
            col__ = col;
        }
    }
    /** フォームテーブル設定*/
    private List<List<FormCell>> formTable__ = new ArrayList<>();

    /** 子要素Mapを保持*/
    private Map<FormAccesser, FormCell> childMap__
      = new HashMap<FormAccesser, FormCell>();

    /** BlockList内の行番号*/
    private int rowNo__ = 0;

    /**
     * <p>rowNo を取得します。
     * @return rowNo
     */
    public int getRowNo() {
        return rowNo__;
    }

    /**
     * <p>rowNo をセットします。
     * @param rowNo rowNo
     */
    protected void setRowNo(int rowNo) {
        rowNo__ = rowNo;
    }

    /**
     * <p>formTable を取得します。
     * @return formTable
     */
    public List<List<FormCell>> getFormTable() {
        return formTable__;
    }

    /**
     * <p>formTable をセットします。
     * @param formTable formTable
     */
    public void setFormTable(List<List<FormCell>> formTable) {
        formTable__ = formTable;
        childMap__ = null;
        makeFormMap(null, 0);
    }

    /**
     * <p>formRow[i] を取得します。
     * @param index 行番号
     * @return formTable
     */
    public List<FormCell> getFormRow(int index) {
        while (formTable__.size() <= index) {
            formTable__.add(new ArrayList<FormCell>());
        }
        return formTable__.get(index);
    }

    /**
     * <p>formCell[row][col] を取得します。
     * @param row 行番号
     * @param col 列番号
     * @return formTable
     */
    public FormCell getFormCell(int row, int col) {
        while (getFormRow(row).size() <= col) {
            getFormRow(row).add(new FormCell());
        }
        return getFormRow(row).get(col);
    }
    /**
     *
     * <br>[機  能] アクセサクラスでformCellを取得します
     * <br>[解  説]
     * <br>[備  考] 存在しないformを指定するとnullが帰る
     * @param access アクセサクラス
     * @return formTable
     */
    public FormCell getFormCell(FormAccesser access) {
        return childMap__.get(access);
    }

    /**
     * <p>formCell[row][col] を取得します。
     * @param rowCol 文字列 行番号_列番号
     * @return formTable
     */
    public FormCell getFormCell(String  rowCol) {
        String[] path = rowCol.split("_");
        if (path.length != 2) {
            return null;
        }
        int row = Integer.parseInt(path[0]);
        int col = Integer.parseInt(path[1]);
        return getFormCell(row, col);
    }
    /**
     * <p>formCell[row][col] を設定します。
     * @param row 行番号
     * @param col 列番号
     * @param cell FormCell
     */
    public void setFormCell(int row, int col, FormCell cell) {
        getFormCell(row, col);
        getFormRow(row).set(col, cell);
        FormAccesser accsess = new FormAccesser(cell.getSid(), rowNo__);
        getChildMap().put(accsess, cell);
        setAccessIndex(accsess, cell);
        cell.getBody()._setRoot(this);

    }

    /**
     *
     * <br>[機  能] json文字列からフォーム情報を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param json JSON文字列
     */
    public void setFormTable(String json) {
        JSONArray jsonArr = null;
        try {
            jsonArr = JSONArray.fromObject(json);
        } catch (JSONException je) {
        }
        if (jsonArr == null) {
            return;
        }
        setFormTable((JSON) jsonArr);
    }
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
            FormCell cell = new FormCell(entry.getValue());
            setFormCell(idx.row__, idx.col__, cell);
        }
    }


    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {

    }

    @Override
    public void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn) {
        if (json instanceof JSONObject) {
            try {
                JSON tableJson = ((JSONObject) json).getJSONArray("formTable");
                setFormTable(tableJson);
            } catch (JSONException e) {

            }
        }
    }


    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((formTable__ == null) ? 0 : formTable__.hashCode());
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
        if (!(obj instanceof Block)) {
            return false;
        }
        Block other = (Block) obj;

        if (!Objects.equals(getFormTable(), other.getFormTable())) {
            return false;
        }
        return true;
    }

    @Override
    public EnumFormModelKbn formKbn() {

        return EnumFormModelKbn.block;
    }
    /**
     * <p>childMap を取得します。
     * 要素の直下の要素のMapを取得します
     * @return childMap
     */
    public Map<FormAccesser, FormCell> getChildMap() {
        if (childMap__ == null) {
            childMap__ = new HashMap<FormAccesser, FormCell>();
            for (List<FormCell> list__ : formTable__) {
                for (FormCell formCell__ : list__) {
                    int formSid = formCell__.getSid();
                    FormAccesser access = new FormAccesser(formSid, getRowNo());
                    if (childMap__.containsKey(access)) {
                        continue;
                    }
                    childMap__.put(access,
                            formCell__);
                }
            }
        }
        return childMap__;
    }


    @Override
    public void makeFormMap(Map<FormAccesser, FormCell> target, int rowNo) {
        Map<FormAccesser, FormCell> childMap = getChildMap();
        if (target == null) {
            return;
        }
        for (Entry<FormAccesser, FormCell> entry : childMap.entrySet()) {
            FormAccesser key = entry.getKey();
            key.setRowNo(rowNo);
            target.put(key, entry.getValue());
        }
    }

    @Override
    public void merge(AbstractFormModel model) {
        super.merge(model);

        if (this == model) {
            return;
        }
        if (model == null) {
            return;
        }
        if (!(model instanceof Block)) {
            return;
        }
        List<List<FormCell>> table = ((Block) model).getFormTable();
        if (formTable__ == null || formTable__.size() == 0) {
            setFormTable(table);
        }
    }

    @Override
    public void defaultInit() {
        Map<FormAccesser, FormCell> map = getChildMap();
        //フォームの初期値設定
        for (Entry<FormAccesser, FormCell> entry
                : new HashSet<Entry<FormAccesser, FormCell>>(map.entrySet())) {
            FormCell cell = entry.getValue();
            AbstractFormModel form = cell.getBody();
            form.defaultInit();
        }
    }

    @Override
    public void setBlockIdx(int blockIdx) {
        super.setBlockIdx(blockIdx);

        Map<FormAccesser, FormCell> map = getChildMap();
        for (Entry<FormAccesser, FormCell> entry
                : new HashSet<Entry<FormAccesser, FormCell>>(map.entrySet())) {
            FormCell cell = entry.getValue();
            if (cell.getBody() != null) {
                cell.getBody().setBlockIdx(blockIdx);
            }
        }
    }

    @Override
    public void setAccessIndex(FormAccesser accsess, FormCell cell) {
        if (_getRoot() != null) {
            _getRoot().setAccessIndex(accsess, cell);
        }
    }
    @Override
    public FormInputInitPrefarence getInitPref() {
        if (_getRoot() != null) {
            return _getRoot().getInitPref();
        }
        return null;
    }

    @Override
    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("formTable", getFormTable());
        return obj.toString();
    }
    /**
     *
     * <br>[機  能]  表示設定
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param isInBlockList ブロックリスト内かどうか
     * @throws SQLException sql実行時例外
     * @throws IOToolsException ディレクトリ作成時例外
     */
    public void dspInit(RequestModel reqMdl, Connection con, boolean isInBlockList)
            throws SQLException, IOToolsException {
        super.dspInit(reqMdl, con);
        Map<FormAccesser, FormCell> map = getChildMap();
        //フォームの表示初期化
        for (Entry<FormAccesser, FormCell> entry
                : new HashSet<Entry<FormAccesser, FormCell>>(map.entrySet())) {
            FormCell cell = entry.getValue();
            AbstractFormModel form = cell.getBody();
            FormCalcBiz calcBiz = getFormCalcBiz();
            if (calcBiz != null) {
                calcBiz.calc(entry.getKey(), cell, getRowNo());
            }
            if (form instanceof SimpleUserSelect) {
                ((SimpleUserSelect) form).dspInit(reqMdl, con, isInBlockList);
            } else if (form instanceof GroupComboModel) {
                if (cell.getRequire() == 0) {
                    ((GroupComboModel) form).setUseNosel(true);
                }
                ((GroupComboModel) form).dspInit(reqMdl, con, isInBlockList);
            } else {
                form.dspInit(reqMdl, con);
            }
        }
    }

    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException, IOToolsException {
        this.dspInit(reqMdl, con, false);
    }
    @Override
    public Map<FormAccesser, FormCell> getRootAccessMap() {
        if (_getRoot() != null) {
            return _getRoot().getRootAccessMap();
        }
        return null;
    }
    @Override
    public FormCalcBiz getFormCalcBiz() {
        if (_getRoot() != null) {
            return _getRoot().getFormCalcBiz();
        }
        return null;
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        Map<FormAccesser, FormCell> map = getChildMap();
        //フォームの表示初期化
        for (Entry<FormAccesser, FormCell> entry
                : new HashSet<Entry<FormAccesser, FormCell>>(map.entrySet())) {
            FormCell cell = entry.getValue();
            cell.validate(errors, reqMdl, info);
        }

    }

    /**
     *
     * <br>[機  能]  表ボディから行ブロック削除イベント
     * <br>[解  説]
     * <br>[備  考]
     */
    public void removeRow() {
        Map<FormAccesser, FormCell> map = getChildMap();
        for (Entry<FormAccesser, FormCell> entry
                : new HashSet<Entry<FormAccesser, FormCell>>(map.entrySet())) {
            FormCell cell = entry.getValue();
            if (cell.getBody() != null) {
                // 各要素ごとに削除イベント実行
                cell.getBody().removeRow(entry.getKey().getFormSid(), entry.getKey().getRowNo());
            }
        }
    }
    @Override
    public void chkUnuseableInput(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        Map<FormAccesser, FormCell> map = getChildMap();
        //フォームの表示初期化
        for (Entry<FormAccesser, FormCell> entry
                : new HashSet<Entry<FormAccesser, FormCell>>(map.entrySet())) {
            FormCell cell = entry.getValue();
            cell.chkUnuseableInput(errors, reqMdl, info);
        }
    }

    @Override
    public void setRoot(IFormAccessMapMakable root) {
        _setRoot(root);
    }
}