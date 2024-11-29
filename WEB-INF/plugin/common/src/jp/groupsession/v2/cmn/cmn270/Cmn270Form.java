package jp.groupsession.v2.cmn.cmn270;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.struts.AbstractForm;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.dao.base.CmnOauthDao;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] OAuth認証画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn270Form extends AbstractForm {
    /** 呼び出し元プラグイン */
    private String cmn270PluginId__ = null;
    /** 呼び出し元画面ID */
    private String cmn270ParentId__ = null;
    /** 認証情報SID */
    private int cmn270AuthSid__ = 0;
    /** メールアドレス */
    private String cmn270MailAddress__ = null;
    /** 認証終了フラグ */
    private int cmn270AuthComplete__ = 0;
    /** code */
    private String code__ = null;
    /** state */
    private String state__ = null;

    /** OAuth認証情報トークンSID */
    private int cotSid__ = 0;
    /** OAuth認証情報トークンSID 設定先パラメータ名(親画面) */
    private String cmn270cotSidParamName__ = null;

    /**
     * <br>[機  能] 親画面から表示された際のパラメータチェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @return エラーメッセージリスト
     * @throws SQLException SQL実行時例外
     */
    public List<String> validateCheck(RequestModel reqMdl, Connection con)
    throws SQLException {
        List<String> errMsgList = new ArrayList<String>();
        GsMessage gsMsg = new GsMessage(reqMdl);

        //メールアドレス
        if (StringUtil.isNullZeroString(cmn270MailAddress__)) {
            //未入力チェック
            errMsgList.add(gsMsg.getMessage("cmn.cmn270.01"));
        } else if (cmn270MailAddress__.length() > GSConstCommon.MAXLEN_AUTH_ADDRESS) {
            //文字数チェック
            errMsgList.add(gsMsg.getMessage("cmn.cmn270.02"));
        }

        //プロバイダ
        CmnOauthDao authDao = new CmnOauthDao(con);
        if (cmn270AuthSid__ <= 0) {
            //未選択チェック
            errMsgList.add(gsMsg.getMessage("cmn.cmn270.03"));
        } else  if (authDao.select(cmn270AuthSid__) == null) {
            //存在チェック
            errMsgList.add(gsMsg.getMessage("cmn.cmn270.04"));
        }

        return errMsgList;
    }

    /** 画面表示メッセージ */
    private String cmn270Message__ = null;
    /**
     * <p>cmn270PluginId を取得します。
     * @return cmn270PluginId
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270Form#cmn270PluginId__
     */
    public String getCmn270PluginId() {
        return cmn270PluginId__;
    }
    /**
     * <p>cmn270PluginId をセットします。
     * @param cmn270PluginId cmn270PluginId
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270Form#cmn270PluginId__
     */
    public void setCmn270PluginId(String cmn270PluginId) {
        cmn270PluginId__ = cmn270PluginId;
    }
    /**
     * <p>cmn270ParentId を取得します。
     * @return cmn270ParentId
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270ParentId__
     */
    public String getCmn270ParentId() {
        return cmn270ParentId__;
    }
    /**
     * <p>cmn270ParentId をセットします。
     * @param cmn270ParentId cmn270ParentId
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270ParentId__
     */
    public void setCmn270ParentId(String cmn270ParentId) {
        cmn270ParentId__ = cmn270ParentId;
    }
    /**
     * <p>cmn270AuthSid を取得します。
     * @return cmn270AuthSid
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270AuthSid__
     */
    public int getCmn270AuthSid() {
        return cmn270AuthSid__;
    }
    /**
     * <p>cmn270AuthSid をセットします。
     * @param cmn270AuthSid cmn270AuthSid
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270AuthSid__
     */
    public void setCmn270AuthSid(int cmn270AuthSid) {
        cmn270AuthSid__ = cmn270AuthSid;
    }
    /**
     * <p>cmn270MailAddress を取得します。
     * @return cmn270MailAddress
     * @see jp.groupsession.v2.wml.cmn270.Cmn270Form#cmn270MailAddress__
     */
    public String getCmn270MailAddress() {
        return cmn270MailAddress__;
    }
    /**
     * <p>cmn270MailAddress をセットします。
     * @param cmn270MailAddress cmn270MailAddress
     * @see jp.groupsession.v2.wml.cmn270.Cmn270Form#cmn270MailAddress__
     */
    public void setCmn270MailAddress(String cmn270MailAddress) {
        cmn270MailAddress__ = cmn270MailAddress;
    }
    /**
     * <p>cmn270AuthComplete を取得します。
     * @return cmn270AuthComplete
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270AuthComplete__
     */
    public int getCmn270AuthComplete() {
        return cmn270AuthComplete__;
    }
    /**
     * <p>cmn270AuthComplete をセットします。
     * @param cmn270AuthComplete cmn270AuthComplete
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#cmn270AuthComplete__
     */
    public void setCmn270AuthComplete(int cmn270AuthComplete) {
        cmn270AuthComplete__ = cmn270AuthComplete;
    }
    /**
     * <p>code を取得します。
     * @return code
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#code__
     */
    public String getCode() {
        return code__;
    }
    /**
     * <p>code をセットします。
     * @param code code
     * @see jp.groupsession.v2.wml.cmn270.Cmn270ParamModel#code__
     */
    public void setCode(String code) {
        code__ = code;
    }
    /**
     * <p>state を取得します。
     * @return state
     * @see jp.groupsession.v2.wml.cmn270.Cmn270Form#state__
     */
    public String getState() {
        return state__;
    }
    /**
     * <p>state をセットします。
     * @param state state
     * @see jp.groupsession.v2.wml.cmn270.Cmn270Form#state__
     */
    public void setState(String state) {
        state__ = state;
    }
    /**
     * <p>cmn270Message を取得します。
     * @return cmn270Message
     * @see jp.groupsession.v2.wml.cmn270.Cmn270Form#cmn270Message__
     */
    public String getCmn270Message() {
        return cmn270Message__;
    }
    /**
     * <p>cmn270Message をセットします。
     * @param cmn270Message cmn270Message
     * @see jp.groupsession.v2.wml.cmn270.Cmn270Form#cmn270Message__
     */
    public void setCmn270Message(String cmn270Message) {
        cmn270Message__ = cmn270Message;
    }
    /**
     * <p>cotSid を取得します。
     * @return cotSid
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270Form#cotSid__
     */
    public int getCotSid() {
        return cotSid__;
    }
    /**
     * <p>cotSid をセットします。
     * @param cotSid cotSid
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270Form#cotSid__
     */
    public void setCotSid(int cotSid) {
        cotSid__ = cotSid;
    }
    /**
     * <p>cmn270cotSidParamName を取得します。
     * @return cmn270cotSidParamName
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270Form#cmn270cotSidParamName__
     */
    public String getCmn270cotSidParamName() {
        return cmn270cotSidParamName__;
    }
    /**
     * <p>cmn270cotSidParamName をセットします。
     * @param cmn270cotSidParamName cmn270cotSidParamName
     * @see jp.groupsession.v2.cmn.cmn270.Cmn270Form#cmn270cotSidParamName__
     */
    public void setCmn270cotSidParamName(String cmn270cotSidParamName) {
        cmn270cotSidParamName__ = cmn270cotSidParamName;
    }

}
