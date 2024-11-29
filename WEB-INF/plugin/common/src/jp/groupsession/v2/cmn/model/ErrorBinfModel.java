package jp.groupsession.v2.cmn.model;

import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

public class ErrorBinfModel extends CmnBinfModel {
    /** ロギングクラス */
    private static Log log__ = LogFactory.getLog(ErrorBinfModel.class);

    /** 添付ファイル保管エラー 代理ファイル名*/
    public static final String FILENANE_ATTACHERROR = "添付ファイル保管エラー.txt";
    /** 添付ファイル保管エラー 代理ファイル本文*/
    public static final String FILEBODY_ATTACHERROR = "$FILENAMEを保管できませんでした。";

    /** 書き込み失敗した添付ファイル名*/
    private List<String> errorFilenamList__ = new ArrayList<String>();
    /** エラーメッセージ createErrorMessageまではnull*/
    private String errorMsgBody__;
    {
        setFinalizeRmFilekbn(GSConstCommon.TEMPFILE_FINALIZE_NOTDEL);
    }
    /**
     *
     * コンストラクタ
     */
    public ErrorBinfModel() {
        super();
    }
    /**
     *
     * コンストラクタ
     * @param bean 複製元
     */
    public ErrorBinfModel(CmnBinfModel bean) {
        super();
        try {
            BeanUtils.copyProperties(this, bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
        }

        this.addErrorFileName(bean.getBinFileName());
    }

    /**
     * <p>errorMsgBody を取得します。
     * @return errorMsgBody
     * @see jp.groupsession.v2.cmn.model.ErrorBinfModel#errorMsgBody__
     */
    public String getErrorMsgBody() {
        return errorMsgBody__;
    }

    @Override
    public String getBinFileExtension() {
        return "txt";
    }

    @Override
    public String getBinFileName() {
        return FILENANE_ATTACHERROR;
    }

    @Override
    public long getBinFileSize() {
        if (errorMsgBody__ == null) {
            return 0L;
        }

        return errorMsgBody__.getBytes().length;
    }

    @Override
    public void setFinalizeRmFilekbn(int finalizeRmFilekbn) {
        super.setFinalizeRmFilekbn(GSConstCommon.TEMPFILE_FINALIZE_NOTDEL);
    }
    @Override
    public int getFinalizeRmFilekbn() {
        return GSConstCommon.TEMPFILE_FINALIZE_NOTDEL;
    }

    /**
     *
     * <br>[機  能] 書き込み失敗した添付ファイル名追加
     * <br>[解  説]
     * <br>[備  考]
     * @param filename
     */
    public void addErrorFileName(String filename) {
        errorFilenamList__.add(filename);
    }
    /**
     *
     * <br>[機  能] エラーメッセージを生成する
     * <br>[解  説]
     * <br>[備  考]
     * @return 生成されたメッセージ
     */
    public String createErrorMessage() {
        StringBuilder sb = new StringBuilder();

        for (String filename : errorFilenamList__) {
            String confText = FILEBODY_ATTACHERROR;
            HashMap<Object, Object> pattern = new HashMap<Object, Object>();
            pattern.put("FILENAME", filename);
            try {
                sb.append(
                        StringUtil.merge(confText, pattern));
                sb.append("\r\n");
            } catch (Exception e) {
                log__.error("", e);
            }

        }
        errorMsgBody__ = sb.toString();
        return errorMsgBody__;

    }
    /**
     *
     * <br>[機  能] エラーメッセージのストリームの作成
     * <br>[解  説]
     * <br>[備  考]
     * @return 入力ストリーム
     */
    public ByteArrayInputStream openStream() {
        String outTxt = "";
        if (errorMsgBody__ != null) {
            outTxt = errorMsgBody__;
        }
        ByteArrayInputStream stream = new ByteArrayInputStream(outTxt.getBytes());
        return stream;
    }
}
