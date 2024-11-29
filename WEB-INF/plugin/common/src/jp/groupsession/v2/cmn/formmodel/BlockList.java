package jp.groupsession.v2.cmn.formmodel;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.struts.action.ActionErrors;

import jp.co.sjts.util.NullDefault;
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
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] 表要素
 * <br>[解  説] ヘッダー、ボディ、 フッター に複数の入力要素を配置できる。
 * <br>[備  考] 表要素、ブロック要素を子に持つことはできない
 *
 * @author JTS
 */
public class BlockList
extends AbstractFormModel
implements IFormAccessMapMakable {


    /** header:フォーム構造テーブル */
    private Block header__ = new ListBlock(this, false);
    /** body:フォーム構造テーブル */
    private List<Block> body__ = new ArrayList<Block>();
    /** footer:フォーム構造テーブル */
    private Block footer__ = new ListBlock(this, false);
    /** defLength:デフォルトボディ行数 */
    private int defLength__ = -1;
    /** body雛形 json*/
    private JSON bodyTempJson__;
    /** 挿入先行番号*/
    private String[] addRow__;
    /** 削除行番号*/
    private String[] deleteRow__;
    /** 削除行番号 モバイル用*/
    private Map<String, String[]> deleteRowMbh__ = new DeleteRowMbhListenMap();

    /** 管理用ブロック識別番号 */
    private int mstBlockIdx__ = 1;

    /**
     *
     * <br>[機  能] ブロックリスト内ボディ用ブロック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private class ListBlock extends Block implements IListBlock {
        /** 親への参照*/
        private BlockList parent__;
        /** ボディ要素かどうか*/
        private boolean isBody__;
        /**
         *
         * コンストラクタ
         * @param parent
         * @param isBody
         */
        public ListBlock(BlockList parent, boolean isBody) {
            parent__ = parent;
            isBody__ = isBody;
        }
        @Override
        public BlockList getParentList() {
            return parent__;
        }
        @Override
        public boolean isBody() {
            return isBody__;
        }
    }

    /**
     *
     * <br>[機  能] モバイル用行削除 イベント用マップクラス
     * <br>[解  説] put時にBlockListのdeleteRowに値をセットする
     * <br>[備  考]
     *
     * @author JTS
     */
    private class DeleteRowMbhListenMap extends HashMap<String, String[]> {
        @Override
        public String[] put(String key, String[] value) {
            String[] deleteRow = new String[] {String.valueOf(key)};
            setDeleteRow(deleteRow);
            return super.put(key, value);
        }
    }
    /**
     * <p>header を取得します。
     * @return header
     */
    public Block getHeader() {
        return header__;
    }

    /**
     * <p>header をセットします。
     * @param header header
     */
    public void setHeader(Block header) {
        header__ = header;
    }

    /**
     * <p>body を取得します。
     * @return body
     */
    public List<Block> getBodyList() {
        return body__;
    }

    /**
     * <p>body をセットします。
     * @param body body
     */
    public void setBodyList(List<Block> body) {
        body__ = body;
    }
    /**
     * <p>body を取得します。
     * 指定
     * @param index インデックス
     * @return body
     */
    public Block getBody(int index) {
        while (body__.size() <= index) {
            Block block = __createNewBlock(body__.size());
            body__.add(block);
        }
        return body__.get(index);
    }
    /**
     *
     * <br>[機  能] 行追加用 Blockインスタンス生成
     * <br>[解  説]
     * <br>[備  考]
     * @param index 行番号
     * @return Block
     */
    private Block __createNewBlock(int index) {
        Block body = new ListBlock(this, true);
        body.setRoot(_getRoot());
        body.setRowNo(index);
        if (bodyTempJson__ != null) {
            body.mergeJson(bodyTempJson__, KBN_JSON_MERGE.all);
        }
        return body;
    }
    /**
     * <p>body をセットします。
     * @param index インデックス
     * @param body body
     */
    public void setBody(int index, Block body) {
        while (body__.size() <= index) {
            Block block = __createNewBlock(body__.size());
            body__.add(block);
        }
        body__.set(index, body);
    }

    /**
     * <p>footer を取得します。
     * @return footer
     */
    public Block getFooter() {
        return footer__;
    }

    /**
     * <p>footer をセットします。
     * @param footer footer
     */
    public void setFooter(Block footer) {
        footer__ = footer;
    }

    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {
        msgForm.addHiddenParam(paramName + ".length", getLength());
        msgForm.addHiddenParam(paramName + ".mstBlockIdx", getMstBlockIdx());
    }

    @Override
    public void mergeJson(JSON json, KBN_JSON_MERGE mergeKbn) {
        JSONObject jsonObj = null;
        if (json instanceof JSONObject) {
            jsonObj = (JSONObject) json;
        }
        if (jsonObj == null) {
            return;
        }
        try {
            JSON tableJson = ((JSONObject) json).getJSONObject("header");
            header__.mergeJson(tableJson, KBN_JSON_MERGE.all);
        } catch (JSONException e) {

        }
        try {
            JSONArray arr = ((JSONObject) json).getJSONArray("body");
            for (int i = 0; i < arr.size(); i++) {
                JSON tableJson = arr.getJSONObject(i);
                getBody(i).mergeJson(tableJson, KBN_JSON_MERGE.all);
                //雛形JSONを保管
                if (i == 0) {
                    bodyTempJson__ = tableJson;
                }
            }
        } catch (JSONException e) {

        }
        try {
            JSON tableJson = ((JSONObject) json).getJSONObject("footer");
            footer__.mergeJson(tableJson, KBN_JSON_MERGE.all);
        } catch (JSONException e) {

        }
        try {
            defLength__ = ((JSONObject) json).getInt("defLength");
        } catch (JSONException e) {

        }
    }

    /**
     * <p>defLength を取得します。
     * @return defLength
     */
    public int getDefLength() {
        return defLength__;
    }

    /**
     * <p>defLength をセットします。
     * @param defLength defLength
     */
    public void setDefLength(int defLength) {
        defLength__ = defLength;
    }
    /* (非 Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    /* (非 Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((body__ == null) ? 0 : body__.hashCode());
        result = prime * result + defLength__;
        result = prime * result
                + ((footer__ == null) ? 0 : footer__.hashCode());
        result = prime * result
                + ((header__ == null) ? 0 : header__.hashCode());
        return result;
    }

    /**
     * <p>mstBlockIdx を取得します。
     * @return mstBlockIdx
     */
    public int getMstBlockIdx() {
        return mstBlockIdx__;
    }
    /**
     * <p>mstBlockIdx をセットします。
     * @param mstBlockIdx mstBlockIdx
     */
    public void setMstBlockIdx(int mstBlockIdx) {
        mstBlockIdx__ = mstBlockIdx;
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
        if (!(obj instanceof BlockList)) {
            return false;
        }
        BlockList other = (BlockList) obj;
        if (!Objects.equals(getBodyList(), other.getBodyList())) {
            return false;
        }
        if (getDefLength() != other.getDefLength()) {
            return false;
        }
        if (!Objects.equals(getFooter(), other.getFooter())) {
            return false;
        }
        if (!Objects.equals(getHeader(), other.getHeader())) {
            return false;
        }
        return true;
    }
    @Override
    public EnumFormModelKbn formKbn() {
        return EnumFormModelKbn.blocklist;
    }
    @Override
    public void makeFormMap(Map<FormAccesser, FormCell> target, int rowNo) {
            getHeader().makeFormMap(target, 0);
            for (int i = 0; i < getBodyList().size(); i++) {
                Block block = getBodyList().get(i);
                block.makeFormMap(target, i);
            }
            getFooter().makeFormMap(target, 0);
    }
    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException, IOToolsException {
        if (getHeader() != null) {
            getHeader().dspInit(reqMdl, con, true);
        }
        if (getBodyList() == null) {
            setBodyList(new ArrayList<Block>());
        }
        List<Block> bodyList = getBodyList();
        List<Block> newList = new ArrayList<Block>();
        List<String> delRowList = new ArrayList<String>();
        if (!ArrayUtils.isEmpty(getDeleteRow())) {
            delRowList =
                new ArrayList<String>(Arrays.asList(getDeleteRow()));
        }

        mstBlockIdx__ = 0;
        for (int i = 0; i < bodyList.size(); i++) {
            if (delRowList.contains(String.valueOf(i))) {
                bodyList.get(i).removeRow(); // ブロック要素削除時に呼ばれる
                delRowList.remove(String.valueOf(i));
                continue;
            }
            newList.add(bodyList.get(i));

            // 既存の要素にあるブロックIndexの内、最大値を取得
            if (bodyList.get(i).getChildMap() != null) {
                int blockIdx = -1;
                Map<FormAccesser, FormCell> bodyBlockMap = bodyList.get(i).getChildMap();
                for (Entry<FormAccesser, FormCell> entryBody : bodyBlockMap.entrySet()) {
                    if (entryBody.getValue().getBody() != null) {
                        blockIdx = entryBody.getValue().getBody().getBlockIdx();
                        break;
                    }
                }
                if (blockIdx >= mstBlockIdx__) {
                    mstBlockIdx__ = blockIdx + 1;
                }
            }
        }
        if (!ArrayUtils.isEmpty(getAddRow())) {
            for (String rowNoStr : getAddRow()) {
                int rowNo = Integer.parseInt(rowNoStr);
                Block block = null;
                if (newList.size() > rowNo) {
                    block =  __createNewBlock(rowNo);
                    newList.add(rowNo, block);
                } else {
                    block = __createNewBlock(newList.size());
                    newList.add(block);
                }
                block.setBlockIdx(mstBlockIdx__); // ブロックに紐付く要素にブロックIndexを設定
                block.defaultInit();
                mstBlockIdx__++; // 次の行要素の為に加算
            }
        }
        //root要素へアクセスマップを更新（行の追加削除により参照行番号が変わるため）
        for (int i = 0; i < newList.size(); i++) {
            Block block = newList.get(i);
            block.makeFormMap(getRootAccessMap(), i);
        }
        setBodyList(newList);
        for (Block block : getBodyList()) {
            block.dspInit(reqMdl, con, true);
        }
        if (getFooter() != null) {
            getFooter().dspInit(reqMdl, con, true);
        }
    }
    @Override
    public void defaultInit() {
        if (getHeader() != null) {
            getHeader().defaultInit();
        }
        for (int i = 0; i < defLength__; i++) {
            Block block = getBody(i);
            block.defaultInit();
        }
        if (getFooter() != null) {
            getFooter().defaultInit();
        }

    }
    @Override
    public void merge(String[] values) {
        //表の行数をマージ
        //ロードデータのマージの場合は追加された行にデフォルトをセットしない
        setLength(NullDefault.getInt(values[0], 0));
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
        if (!(model instanceof BlockList)) {
            return;
        }
        BlockList other = (BlockList) model;

        if (getHeader() == null) {
            setHeader(other.getHeader());
        } else {
            getHeader().merge(other.getHeader());
        }
        if (bodyTempJson__ == null) {
            bodyTempJson__ = other.bodyTempJson__;
        }
        if (CollectionUtils.size(getBodyList())
                < CollectionUtils.size(other.getBodyList())) {
            setLength(other.getLength());
        }
        if (getFooter() == null) {
            setFooter(other.getFooter());
        } else {
            getFooter().merge(other.getFooter());
        }
        if (ArrayUtils.isEmpty(getAddRow())
                && !ArrayUtils.isEmpty(other.getAddRow())) {
            setAddRow(other.getAddRow());
        }
        if (ArrayUtils.isEmpty(getDeleteRow())
                && !ArrayUtils.isEmpty(other.getDeleteRow())) {
            setDeleteRow(other.getDeleteRow());
        }
        int scrollY, otherScY;
        scrollY = getScrollY();
        otherScY = other.getScrollY();
        if (scrollY == 0 && otherScY != 0) {
            setScrollY(otherScY);
        }

        if (scrollY == 0 && otherScY != 0) {
            setScrollY(otherScY);
        }
    }

    @Override
    public String toJSONString() {
        JSONObject obj = new JSONObject();
        obj.put("header", getHeader());
        obj.put("body", getBodyList());
        obj.put("footer", getFooter());
        obj.put("defLength", getDefLength());
        return obj.toString();
    }
    @Override
    protected void _setRoot(IFormAccessMapMakable root) {
        super._setRoot(root);
        getHeader().setRoot(root);
        getFooter().setRoot(root);
        for (Block block__ : getBodyList()) {
            block__.setRoot(root);
        }
    }
    @Override
    public void setRoot(IFormAccessMapMakable root) {
        _setRoot(root);
    }
    @Override
    public void setAccessIndex(FormAccesser accsess, FormCell cell) {
        if (_getRoot() != null) {
            _getRoot().setAccessIndex(accsess, cell);
        }
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
    public FormInputInitPrefarence getInitPref() {
        if (_getRoot() != null) {
            return _getRoot().getInitPref();
        }
        return null;
    }
    /**
     * <p>addRow を取得します。
     * @return addRow
     */
    public String[] getAddRow() {
        return addRow__;
    }

    /**
     * <p>addRow をセットします。
     * @param addRow addRow
     */
    public void setAddRow(String[] addRow) {
        addRow__ = addRow;
    }

    /**
     * <p>deleteRow を取得します。
     * @return deleteRow
     */
    public String[] getDeleteRow() {
        return deleteRow__;
    }

    /**
     * <p>deleteRow をセットします。
     * @param deleteRow deleteRow
     */
    public void setDeleteRow(String[] deleteRow) {
        deleteRow__ = deleteRow;
    }
    /**
    *
    * <br>[機  能] 行追加スクリプトを出力
    * <br>[解  説]
    * <br>[備  考]
    * @param name 要素へのフォーム参照名
    * @return script
    */
    public String outputAddRowSclipt(String name) {
       StringBuilder sb = new StringBuilder();
       sb.append("$('<input>', {");
       sb.append("    type: 'hidden', ");
       sb.append("    name: '" + name + ".addRow', ");
       sb.append("    value:"); sb.append(getBodyList().size());
       sb.append("    }).appendTo($(this).parent()); ");
       return sb.toString();

    }
    /**
    *
    * <br>[機  能] 行削除スクリプトを出力
    * <br>[解  説]
    * <br>[備  考]
    * @param name 要素へのフォーム参照名
    * @param rowNo 削除対象行番号
    * @return script
    */
    public String outputDeleteRowSclipt(String name, int rowNo) {
      StringBuilder sb = new StringBuilder();
      sb.append("$('<input>', {");
      sb.append("    type: 'hidden', ");
      sb.append("    name: '" + name + ".deleteRow', ");
      sb.append("    value:"); sb.append(rowNo);
      sb.append("    }).appendTo($(this).parent()); ");
      return sb.toString();
    }
    /**
     *
     * <br>[機  能] ボディ部に定義された要素がないか返す
     * <br>[解  説]
     * <br>[備  考]
     * @return ボディ要素が空の場合true
     */
    public boolean isBodyEmpty() {
        if (bodyTempJson__ == null) {
            return true;
        }
        if (bodyTempJson__.isEmpty()) {
            return true;
        }
        return false;
    }
    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (getHeader() != null) {
            ValidateInfo child = info.child();
            child.setTitle(gsMsg.getMessage("cmn.header"));
            getHeader().validateCheck(errors, reqMdl, child);
        }
        if (!CollectionUtils.isEmpty(getBodyList())) {
            List<Block> blockList = getBodyList();
            for (int i = 0; i < blockList.size(); i++) {
                Block block = blockList.get(i);
                ValidateInfo child = info.child();
                child.addPathRowNo(i + 1,  gsMsg);
                block.validateCheck(errors, reqMdl, child);
            }
        }
        if (getFooter() != null) {
            ValidateInfo child = info.child();
            child.setTitle(gsMsg.getMessage("cmn.footer"));
            getFooter().validateCheck(errors, reqMdl, child);
        }


    }

    @Override
    public void chkUnuseableInput(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (getHeader() != null) {
            ValidateInfo child = info.child();
            child.setTitle(gsMsg.getMessage("cmn.header"));
            getHeader().chkUnuseableInput(errors, reqMdl, child);
        }
        if (!CollectionUtils.isEmpty(getBodyList())) {
            List<Block> blockList = getBodyList();
            for (int i = 0; i < blockList.size(); i++) {
                Block block = blockList.get(i);
                ValidateInfo child = info.child();
                child.addPathRowNo(i + 1,  gsMsg);
                block.chkUnuseableInput(errors, reqMdl, child);
            }
        }
        if (getFooter() != null) {
            ValidateInfo child = info.child();
            child.setTitle(gsMsg.getMessage("cmn.footer"));
            getFooter().chkUnuseableInput(errors, reqMdl, child);
        }
    }

    /**
     * <p>length を取得します。
     * @return length
     * @see jp.groupsession.v2.cmn.formmodel.BlockList#length__
     */
    public int getLength() {
        return body__.size();
    }

    /**
     * <p>length をセットします。
     * @param length length
     * @see jp.groupsession.v2.cmn.formmodel.BlockList#length__
     */
    public void setLength(int length) {
        if (length > 0) {
            getBody(length - 1);
        }
    }


    /**
     * <p>挿入先行番号 ガラケー用 をセットします。
     * @param addRowMbh addRowMbh
     * @see jp.groupsession.v2.cmn.formmodel.BlockList#addRowMbh__
     */
    public void setAddRowMbh(String[] addRowMbh) {
        String[] addRow = new String[] {String.valueOf(getLength() + 1)};
        setAddRow(addRow);
    }

    /**
     * <p>deleteRowMbh を取得します。
     * @return deleteRowMbh
     * @see jp.groupsession.v2.cmn.formmodel.BlockList#deleteRowMbh__
     */
    public Map<String, String[]> getDeleteRowMbh() {
        return deleteRowMbh__;
    }

    /**
     * <p>deleteRowMbh をセットします。
     * @param deleteRowMbh deleteRowMbh
     * @see jp.groupsession.v2.cmn.formmodel.BlockList#deleteRowMbh__
     */
    public void setDeleteRowMbh(Map<String, String[]> deleteRowMbh) {
        deleteRowMbh__ = deleteRowMbh;
    }

    /**
     *
     * <br>[機  能] cellが表要素のボディ内のものか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param cell
     * @return 判定結果
     */
    public static boolean isBodyCell(FormCell cell) {
        IFormAccessMapMakable blk = cell.getBody()._getRoot();
        if (blk instanceof IListBlock) {
            return ((IListBlock) blk).isBody();
        }
        return false;
    }
    /**
     *
     * <br>[機  能] cellの親リストのSIDを返す
     * <br>[解  説]
     * <br>[備  考]
     * @param cell
     * @return sid 表要素でない場合 -1
     */
    public static int getParentSid(FormCell cell) {
        IFormAccessMapMakable blk = cell.getBody()._getRoot();
        if (blk instanceof IListBlock) {
            return ((IListBlock) blk).getParentList()._getCellInfo().getSid();
        }
        return -1;
    }
}
