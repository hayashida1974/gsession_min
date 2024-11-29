package jp.groupsession.v2.cmn.formmodel.prefarence;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSValidateCommon;
import jp.groupsession.v2.cmn.formbuilder.IFormAccessMapMakable;
import jp.groupsession.v2.cmn.formbuilder.ValidateInfo;
import jp.groupsession.v2.cmn.formmodel.Block;
import jp.groupsession.v2.cmn.formmodel.BlockList;
import jp.groupsession.v2.cmn.formmodel.IListBlock;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] 表要素設定用オブジェクト
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class BlockListPrefarence extends BlockList {
    /** header:フォーム構造テーブル */
    private BlockPrefarence header__ = new ListBlock(this, false);
    /** body:フォーム構造テーブル */
    private List<Block> body__ = new ArrayList<Block>();
    /** footer:フォーム構造テーブル */
    private BlockPrefarence footer__ = new ListBlock(this, false);
    /** root要素を保持*/
    private IFormAccessMapMakable root__;

    /**
     *
     * <br>[機  能] ブロックリスト内ボディ用ブロック
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private class ListBlock extends BlockPrefarence implements IListBlock {
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
            if (arr.size() > 0) {
                JSON tableJson = arr.getJSONObject(0);
                ListBlock block = new ListBlock(this, true);
                block.mergeJson(tableJson, KBN_JSON_MERGE.all);
                body__.add(block);
            }
        } catch (JSONException e) {

        }
        try {
            JSON tableJson = ((JSONObject) json).getJSONObject("footer");
            footer__.mergeJson(tableJson, KBN_JSON_MERGE.all);
        } catch (JSONException e) {

        }
        try {
            setDefLength(((JSONObject) json).getInt("defLength"));
        } catch (JSONException e) {

        }
    }

    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        GsMessage gsMsg = new GsMessage(reqMdl);
        String teisuName = gsMsg.getMessage("cmn.form.pref.rowcnt.default");
        int defLength = getDefLength();
        GSValidateCommon.validateNumberInt(errors,
                String.valueOf(defLength),
                teisuName, 2);
        if (defLength < 0 || defLength > 20) {
            ActionMessage msg = new ActionMessage("error.input.addhani.text", teisuName, 0, 20);
            StrutsUtil.addMessage(errors, msg, teisuName);
        }
    }
    @Override
    public void dspInit(RequestModel reqMdl, Connection con)
            throws SQLException {
        if (getDefLength() < 0) {
            setDefLength(1);
        }
        return;
    }




    /**
     * <p>header を取得します。
     * @return header
     * @see jp.groupsession.v2.cmn.formmodel.prefarence.BlockListPrefarence#header__
     */
    public BlockPrefarence getHeader() {
        return header__;
    }


    /**
     * <p>header をセットします。
     * @param header header
     * @see jp.groupsession.v2.cmn.formmodel.prefarence.BlockListPrefarence#header__
     */
    public void setHeader(BlockPrefarence header) {
        header__ = header;
    }


    /**
     * <p>footer を取得します。
     * @return footer
     * @see jp.groupsession.v2.cmn.formmodel.prefarence.BlockListPrefarence#footer__
     */
    public BlockPrefarence getFooter() {
        return footer__;
    }


    /**
     * <p>footer をセットします。
     * @param footer footer
     * @see jp.groupsession.v2.cmn.formmodel.prefarence.BlockListPrefarence#footer__
     */
    public void setFooter(BlockPrefarence footer) {
        footer__ = footer;
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
            BlockPrefarence body = new BlockPrefarence();
            body.setRoot(root__);
            body__.add(body);
        }
        return body__.get(index);
    }
    /**
     * <p>body をセットします。
     * @param index インデックス
     * @param body body
     */
    public void setBody(int index, Block body) {
        while (body__.size() <= index) {
            BlockPrefarence block = new BlockPrefarence();
            block.setRoot(root__);
            body__.add(body);
        }
        body__.set(index, body);
    }

}
