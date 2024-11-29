package jp.groupsession.v2.wml.pop3;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

import jp.co.sjts.util.StringUtil;

/**
 * <br>[機  能] Pop3Utilityで使用するDataSource
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Pop3DataSource implements DataSource {
    /** Content-Type */
    private String contentType__ = null;
    /** DataSource */
    private DataSource dataSource__ = null;

    /**
     * <p>コンストラクタ
     * @param dataSource DataSource
     */
    public Pop3DataSource(DataSource dataSource) {
        dataSource__ = dataSource;
    }

    /**
     * <p>Content-Type を取得する
     * @return Content-Type
     */
    public String getContentType() {
        if (contentType__ == null) {
            contentType__ = dataSource__.getContentType();
        }

        if (!StringUtil.isNullZeroString(contentType__)
        && contentType__.indexOf("boundary") >= 0) {
            int isoIndex = contentType__.toLowerCase().indexOf(";iso-2022-jp");
            if (isoIndex > 0) {
                contentType__ = contentType__.substring(0, isoIndex);
            }
        }

        return contentType__;
    }

    /**
     * <p>InputStreamの取得
     * @return InputStream
     * @throws IOException InputStreamの取得に失敗
     */
    public InputStream getInputStream() throws IOException {
        return dataSource__.getInputStream();
    }

    /**
     * <p>OutputStreamの取得
     * @return OutputStream
     * @throws IOException OutputStreamの取得に失敗
     */
    public OutputStream getOutputStream() throws IOException {
        return dataSource__.getOutputStream();
    }

    /**
     * <p>DataSource#getName() の結果を返す
     * @return DataSource#getName() の結果
     */
    public String getName() {
        return dataSource__.getName();
    }

  }