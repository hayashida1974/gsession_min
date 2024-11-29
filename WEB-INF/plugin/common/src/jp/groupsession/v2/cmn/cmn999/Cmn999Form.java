package jp.groupsession.v2.cmn.cmn999;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.struts.AbstractForm;
import jp.groupsession.v2.cmn.GSConst;

/**
 * <br>[機  能] 共通メッセージ画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn999Form extends AbstractForm {

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説] 
     * <br>[備  考] 
     * @param closeScript 閉じるボタン押下時のjavascriptメソッド名
     */
    public Cmn999Form(String closeScript) {
        closeScript__ = closeScript;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     */
    public Cmn999Form() {
        
    }

    /** OKボタンタイプ */
    public static final int TYPE_OK = 0;
    /** OK,Cancelボタンタイプ */
    public static final int TYPE_OKCANCEL = 1;
    /** OK,戻るボタンタイプ */
    public static final int TYPE_OKBACK = 2;
    /** エラー送信ボタンタイプ */
    public static final int TYPE_ERROR_REPORT = 3;
    /** 警告画面 */
    public static final int ICON_WARN = 0;
    /** インフォメーション画面 */
    public static final int ICON_INFO = 1;
    /** ポップアップ画面 ではない */
    public static final int POPUP_FALSE = 0;
    /** ポップアップ画面 である */
    public static final int POPUP_TRUE = 1;
    /** WINDOWターゲット (SELF) */
    public static final String WTARGET_SELF = "_self";
    /** WINDOWターゲット (TOP) */
    public static final String WTARGET_TOP = "_top";
    /** WINDOWターゲット (PARENT) */
    public static final String WTARGET_PARENT = "_parent";
    /** WINDOWターゲット (BLANK) */
    public static final String WTARGET_BLANK = "_blank";
    /** WINDOWターゲット (MENU) */
    public static final String WTARGET_MENU = "menu";
    /** WINDOWターゲット (BODY) */
    public static final String WTARGET_BODY = "body";
    /** OutOfMemory */
    public static final int NOT_OUT_OF_MEMORY = 0;
    /** OutOfMemory以外　*/
    public static final int OUT_OF_MEMORY = 1;

    /** ポップアップ画面からの呼び出し判定 */
    private int type_popup__ = 0;
    /** エラー画面後の遷移先 */
    private String directURL__;
    /** ボタンタイプ */
    private int type__;
    /** アイコンイメージ */
    private int icon__;
    /** OKボタンURL */
    private String urlOK__;
    /** CANCELボタンURL */
    private String urlCancel__;
    /** エラー報告ボタンURL */
    private String urlReport__;
    /** GS設定ドキュメントURL */
    private String urlGsSetting__;
    /** OKボタンに表示する文字 */
    private String okBtnValue__ = "OK";
    /** キャンセルボタンに表示する文字 */
    private String cancelBtnValue__ = null;
    /** 画面タイトル */
    private String title__;
    /** 画面メッセージ */
    private String message__;
    /** 呼出元機能 */
    private int origin__;
    /** hiddenリスト */
    private ArrayList < Cmn999Model > hiddenList__;
    /** Windowターゲット */
    private String wtarget__ = WTARGET_BODY;
    /** 閉じるボタンクリック時に実行するscript */
    private String closeScript__ = null;

    /** エラーログ（動作環境付加） */
    private String errorLog__;
    /** エラーログ */
    private String errorLogOnly__;

    /** 動作環境 */
    private String detailInfo__;
    /** OutOfMemory判定 */
    private int outOfMemory__ = NOT_OUT_OF_MEMORY;

    /** GSバージョン情報 */
    private String gsVersion__ = GSConst.VERSION;
    
    /** メッセージ用表示変数 */
    private List<String> messageList__;
    /** フリーライセンス用メッセージ表示判定フラグ */
    private int freeLicenseMessage__ = GSConst.DSP_NOT;

    /**
     * <p>gsVersionを取得します。
     * @return gsVersion__を戻します。
     */
    public String getGsVersion() {
        return gsVersion__;
    }
    /**
     * <p>gsVersionをセットします。
     * @param gsVersion gsVersionを設定。
     */
    public void setGsVersion(String gsVersion) {
        gsVersion__ = gsVersion;
    }
    /**
     * <p>directURLを取得します。
     * @return directURL__を戻します。
     */
    public String getDirectURL() {
        return directURL__;
    }
    /**
     * <p>directURLをセットします。
     * @param directURL directURLを設定。
     */
    public void setDirectURL(String directURL) {
        directURL__ = directURL;
    }
    /**
     * <p>整数パラメータをhiddenListに追加セットします。
     * @param key キー名
     * @param value 値
     */
    public void addHiddenParam(String key, int value) {
        addHiddenParam(key, Integer.toString(value));
    }
    /**
     * <p>整数パラメータをhiddenListに追加セットします。
     * @param key キー名
     * @param value 値
     */
    public void addHiddenParam(String key, long value) {
        addHiddenParam(key, Long.toString(value));
    }
    /**
     * <p>文字列パラメータをhiddenListに追加セットします。
     * @param key キー名
     * @param value 値
     */
    public void addHiddenParam(String key, String value) {
        if (hiddenList__ == null) {
            hiddenList__ = new ArrayList<Cmn999Model>();
        }
        Cmn999Model hdMdl = new Cmn999Model();
        hdMdl.setKey(key);
        hdMdl.setValue(value);
        hiddenList__.add(hdMdl);
    }
    /**
     * <p>整数配列パラメータをhiddenListに追加セットします。
     * @param key キー名
     * @param value 値
     */
    public void addHiddenParam(String key, int[] value) {
        if (value != null) {
            for (int i = 0; i < value.length; i++) {
                addHiddenParam(key, value[i]);
            }
        }
    }
    /**
     * <p>文字列配列パラメータをhiddenListに追加セットします。
     * @param key キー名
     * @param value 値
     */
    public void addHiddenParam(String key, String[] value) {
        if (value != null) {
            for (int i = 0; i < value.length; i++) {
                addHiddenParam(key, value[i]);
            }
        }
    }
    /**
     * <p>文字列リストパラメータをhiddenListに追加セットします。
     * @param key キー名
     * @param value 値
     */
    public void addHiddenParam(String key, ArrayList<String> value) {
        if (value != null) {
            for (int i = 0; i < value.size(); i++) {
                addHiddenParam(key, (String) value.get(i));
            }
        }
    }
    /**
     *
     * <br>[機  能] javaBean の プロパティをHiddenParamとしてセットします
     * <br>[解  説]
     * <br>[備  考] 配列以外のオブジェクト要素は保管されません
     * @param bean javaBean
     * @param beanClass 格納したいプロパティが宣言されたクラス
     * @param startStr プロパティ名の接頭語
     * @throws IntrospectionException IntrospectorクラスのgetBeanInfo()メソッドで例外が発生した場合
     * @throws NoSuchMethodException beanのMap変換時例外
     * @throws InvocationTargetException beanのMap変換時例外
     * @throws IllegalAccessException  beanのMap変換時例外
     */
    public void addHiddenAll(Object bean,
            @SuppressWarnings("rawtypes") Class beanClass,
            String startStr)
            throws IntrospectionException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        BeanInfo beanInfo = null;
        PropertyDescriptor[] propertyDescriptors = null;
        List<String> properties = new ArrayList<String>();
        try {
            // Beanクラスの情報を取得します。
            beanInfo = Introspector.getBeanInfo(beanClass);
            // Beanクラスのプロパティ全てを取得します。
            propertyDescriptors = beanInfo.getPropertyDescriptors();

            // プロパティそれぞれの、getter/setterを出力します。
            for (int iF1 = 0; iF1 < propertyDescriptors.length; ++iF1) {
                if (propertyDescriptors[iF1].getReadMethod() != null
                     && propertyDescriptors[iF1].getWriteMethod() != null
                        ) {
                    properties.add(propertyDescriptors[iF1].getName());
                }
            }
        } catch (IntrospectionException e) {
            // IntrospectorクラスのgetBeanInfo()メソッドで例外が発生した場合
            // （JavaBeansじゃない場合とか）にはIntrospectionException例外が
            // 投げられます。
            throw e;
        }

        if (propertyDescriptors != null && propertyDescriptors.length > 0) {
            for (PropertyDescriptor pd : propertyDescriptors) {
                 String propName = "";
                    if (startStr != null) {
                        propName = startStr;
                    }
                    propName += pd.getName();

                 if (pd.getReadMethod() != null && pd.getWriteMethod() != null) {
                     Object propValue = PropertyUtils.getProperty(bean, pd.getName());
                     if (propValue == null) {
                         continue;
                     }
                     if (pd.getClass().isAssignableFrom(Collection.class)) {
                         continue;
                     }
                     if (pd.getPropertyType().isArray()) {
                         Object[] values = (Object[]) propValue;
                         ArrayList<String> valueList = new ArrayList<String>();
                         for (Object value : values) {
                            valueList.add(value.toString());
                         }
                         addHiddenParam(propName, valueList);
                         continue;
                     }
                     if (pd.getPropertyType().equals(int.class)
                             || pd.getPropertyType().equals(long.class)
                             || pd.getPropertyType().equals(String.class)
                             || pd.getPropertyType().equals(boolean.class)) {
                         addHiddenParam(propName,
                                 propValue.toString());
                         continue;
                     }
                 }
             }
         }

    }
    
    /**
     * <br>[機  能] フィールド設定からトースト表示かどうかを判定する
     * <br>[解  説] typeがOKのみ アイコンがINFO ポップアップでなければトースト表示とする
     * <br>[備  考]
     * @return true:上記の条件に合致, false:上記の条件に合致しない 
     */
    public boolean isInfoToast() {

        if (type__ == TYPE_OKCANCEL) {
            return false;
        }
        if (icon__ == ICON_WARN) {
            return false;
        }
        
        if (type_popup__ == POPUP_TRUE) {
            return false;
        }
        
        return true;
    }
    
    /**
     * <p>origin__を取得します。
     * @return origin__ を戻す。
     */
    public int getOrigin() {
        return origin__;
    }

    /**
     * <p>origin__をセットします。
     * @param origin originを設定。
     */
    public void setOrigin(int origin) {
        origin__ = origin;
    }

    /**
     * <p>hiddenKeyList__を取得します。
     * @return hiddenList__を戻します。
     */
    public ArrayList<Cmn999Model> getHiddenList() {
        return hiddenList__;
    }
    /**
     * <p>hiddenKeyList__をセットします。
     * @param hiddenList hiddenKeyList__を設定。
     */
    public void setHiddenList(ArrayList<Cmn999Model> hiddenList) {
        hiddenList__ = hiddenList;
    }
    /**
     * <p>icon__を取得します。
     * @return icon__を戻します。
     */
    public int getIcon() {
        return icon__;
    }
    /**
     * <p>icon__をセットします。
     * @param icon icon__を設定。
     */
    public void setIcon(int icon) {
        icon__ = icon;
    }
    /**
     * <p>message__を取得します。
     * @return message__を戻します。
     */
    public String getMessage() {
        return message__;
    }
    /**
     * <p>message__をセットします。
     * @param message message__を設定。
     */
    public void setMessage(String message) {
        message__ = message;

        String[] msgBrList = message__.split("<br>|<BR>|<br/>|<br />");
        List<String> mgsList = new ArrayList<String>();
        for (int i = 0; i < msgBrList.length; i++) {
            mgsList.add(StringUtilHtml.transToTextBrFirst(msgBrList[i]));
        }
        messageList__ = mgsList;
    }
    /**
     * <p>title__を取得します。
     * @return title__を戻します。
     */
    public String getTitle() {
        return title__;
    }
    /**
     * <p>title__をセットします。
     * @param title title__を設定。
     */
    public void setTitle(String title) {
        title__ = title;
    }
    /**
     * <p>type__を取得します。
     * @return type__を戻します。
     */
    public int getType() {
        return type__;
    }
    /**
     * <p>type__をセットします。
     * @param type type__を設定。
     */
    public void setType(int type) {
        type__ = type;
    }
    /**
     * <p>urlCancel__を取得します。
     * @return urlCancel__を戻します。
     */
    public String getUrlCancel() {
        return urlCancel__;
    }
    /**
     * <p>urlCancel__をセットします。
     * @param urlCancel urlCancel__を設定。
     */
    public void setUrlCancel(String urlCancel) {
        urlCancel__ = urlCancel;
    }
    /**
     * <p>urlOK__を取得します。
     * @return urlOK__を戻します。
     */
    public String getUrlOK() {
        return urlOK__;
    }
    /**
     * <p>urlOK__をセットします。
     * @param urlOK urlOK__を設定。
     */
    public void setUrlOK(String urlOK) {
        urlOK__ = urlOK;
    }
    /**
     * @return okBtnValue を戻します。
     */
    public String getOkBtnValue() {
        return okBtnValue__;
    }
    /**
     * @param okBtnValue okBtnValue を設定。
     */
    public void setOkBtnValue(String okBtnValue) {
        okBtnValue__ = okBtnValue;
    }
    /**
     * <p>cancelBtnValue を取得します。
     * @return cancelBtnValue
     * @see jp.groupsession.v2.cmn.cmn999.Cmn999Form#cancelBtnValue__
     */
    public String getCancelBtnValue() {
        return cancelBtnValue__;
    }
    /**
     * <p>cancelBtnValue をセットします。
     * @param cancelBtnValue cancelBtnValue
     * @see jp.groupsession.v2.cmn.cmn999.Cmn999Form#cancelBtnValue__
     */
    public void setCancelBtnValue(String cancelBtnValue) {
        cancelBtnValue__ = cancelBtnValue;
    }
    /**
     * @return wtarget を戻します。
     */
    public String getWtarget() {
        return wtarget__;
    }
    /**
     * @param wtarget 設定する wtarget。
     */
    public void setWtarget(String wtarget) {
        wtarget__ = wtarget;
    }
    /**
     * @return type_popup を戻します。
     */
    public int getType_popup() {
        return type_popup__;
    }
    /**
     * @param typePopup 設定する type_popup。
     */
    public void setType_popup(int typePopup) {
        type_popup__ = typePopup;
    }
    /**
     * <p>errorLog を取得します。
     * @return errorLog
     */
    public String getErrorLog() {
        return errorLog__;
    }
    /**
     * <p>errorLog をセットします。
     * @param errorLog errorLog
     */
    public void setErrorLog(String errorLog) {
        errorLog__ = errorLog;
    }
    /**
     * <p>urlReport を取得します。
     * @return urlReport
     */
    public String getUrlReport() {
        return urlReport__;
    }
    /**
     * <p>urlReport をセットします。
     * @param urlReport urlReport
     */
    public void setUrlReport(String urlReport) {
        urlReport__ = urlReport;
    }
    /**
     * @return detailInfo
     */
    public String getDetailInfo() {
        return detailInfo__;
    }
    /**
     * @param detailInfo 設定する detailInfo
     */
    public void setDetailInfo(String detailInfo) {
        detailInfo__ = detailInfo;
    }
    /**
     * @return errorLogOnly
     */
    public String getErrorLogOnly() {
        return errorLogOnly__;
    }
    /**
     * @param errorLogOnly 設定する errorLogOnly
     */
    public void setErrorLogOnly(String errorLogOnly) {
        errorLogOnly__ = errorLogOnly;
    }
    /**
     * <p>outOfMemory を取得します。
     * @return outOfMemory
     */
    public int getOutOfMemory() {
        return outOfMemory__;
    }
    /**
     * <p>outOfMemory をセットします。
     * @param outOfMemory outOfMemory
     */
    public void setOutOfMemory(int outOfMemory) {
        outOfMemory__ = outOfMemory;
    }
    /**
     * <p>urlGsSetting を取得します。
     * @return urlGsSetting
     */
    public String getUrlGsSetting() {
        return urlGsSetting__;
    }
    /**
     * <p>urlGsSetting をセットします。
     * @param urlGsSetting urlGsSetting
     */
    public void setUrlGsSetting(String urlGsSetting) {
        urlGsSetting__ = urlGsSetting;
    }
    /**
     * <p>closeScript を取得します。
     * @return closeScript
     */
    public String getCloseScript() {
        return closeScript__;
    }
    
    /**
     * <p>messageList を取得します。
     * @return messageList
     * @see jp.groupsession.v2.cmn.cmn999.Cmn999Form#messageList__
     */
    public List<String> getMessageList() {
        return messageList__;
    }
    /**
     * <p>messageList をセットします。
     * @param messageList messageList
     * @see jp.groupsession.v2.cmn.cmn999.Cmn999Form#messageList__
     */
    public void setMessageList(List<String> messageList) {
        messageList__ = messageList;
    }
    /**
     * <p>freeLicenseMessage を取得します。
     * @return freeLicenseMessage
     * @see jp.groupsession.v2.cmn.cmn999.Cmn999Form#freeLicenseMessage__
     */
    public int getFreeLicenseMessage() {
        return freeLicenseMessage__;
    }
    /**
     * <p>freeLicenseMessage をセットします。
     * @param freeLicenseMessage freeLicenseMessage
     * @see jp.groupsession.v2.cmn.cmn999.Cmn999Form#freeLicenseMessage__
     */
    public void setFreeLicenseMessage(int freeLicenseMessage) {
        this.freeLicenseMessage__ = freeLicenseMessage;
    }
}