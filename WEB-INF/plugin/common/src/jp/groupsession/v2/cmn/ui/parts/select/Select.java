package jp.groupsession.v2.cmn.ui.parts.select;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.CloneableUtil;
import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.configs.GsMessageReq;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 *
 * <br>[機  能] ユーザ選択選択先設定モデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Select {
    /** パラメータ*/
    private final ParamForm param__;
    /** 選択済み要素リスト*/
    private List<? extends IChild> list__ = new ArrayList<>();

    public static class ParamForm {
        /** ラベル名称 メッセージキー */
        private GsMessageReq label__;
        /** ラベル名称  */
        private String labelText__;
        /** 登録対象名*/
        private String parameterName__;
        /**
         * <p>label をセットします。
         * @param label label
         * @see jp.groupsession.v2.cmn.ui.parts.select.Select#data__.getLabel()
         */
        public void setLabel(GsMessageReq label) {
            label__ = label;
        }
        /**
         * <p>labelText をセットします。
         * @param labelText labelText
         * @see jp.groupsession.v2.cmn.ui.parts.select.Select#data__.getLabelText()
         */
        public void setLabelText(String labelText) {
            labelText__ = labelText;
        }
        /**
         * <p>parameterName をセットします。
         * @param parameterName parameterName
         * @see jp.groupsession.v2.cmn.ui.parts.select.Select#parameterName__
         */
        public void setParameterName(String parameterName) {
            parameterName__ = parameterName;
        }

        /**
         * <p>label をセットします。
         * @param msgReq GsMessageReq
         * @return this
         * @see #label
         */
        public ParamForm chainLabel(GsMessageReq msgReq) {
            label__ = msgReq;
            return this;
        }
        public ParamForm chainLabelText(String labelName) {
            labelText__ = labelName;
            return this;

        }
        public ParamForm chainParameterName(String parameterName) {
            parameterName__ = parameterName;
            return this;

        }

        public Select build() {
            ParamForm param = new ParamForm();
            CloneableUtil.copyField(param, this);
            return new Select(param);
        }

    }
    /**
     *
     * コンストラクタ
     * @param param パラメータ
     */
    private Select(ParamForm param) {
        param__ = param;
    }

    public static ParamForm builder() {
        return new ParamForm();
    }

    /**
     * <p>labelText を取得します。
     * @return labelText
     * @see jp.groupsession.v2.cmn.ui.parts.select.Select#data__.getLabelText()
     */
    public String getLabelText() {
        return param__.labelText__;
    }
    /**
     * <p>label を取得します。
     * @return labelText
     * @see jp.groupsession.v2.cmn.ui.parts.select.Select#data__.getLabelText()
     */
    public GsMessageReq getLabel() {
        return param__.label__;
    }

    /**
     * <p>parameterName を取得します。
     * @return parameterName
     * @see jp.groupsession.v2.cmn.ui.parts.select.Select#parameterName__
     */
    public String getParameterName() {
        return param__.parameterName__;
    }
    /**
     * <p>list を取得します。
     * @return list
     * @see jp.groupsession.v2.cmn.ui.parts.select.Select#list__
     */
    public List<? extends IChild> getList() {
        return list__;
    }
    /**
     *
     * <br>[機  能] 描画設定
     * <br>[解  説]
     * <br>[備  考]
     * @param paramModel
     * @param reqMdl
     * @param con
     * @param list 選択済み要素リスト
     */
    public void dspInit(ParameterObject paramModel,
            RequestModel reqMdl,
            Connection con,
            List<? extends IChild> list) {

        if (StringUtil.isNullZeroString(getLabelText()) && param__.label__ != null) {
            param__.setLabelText(param__.label__.getMessage(new GsMessage()));
        }
        list__ = list;
    }

}
