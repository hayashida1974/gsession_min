package jp.groupsession.v2.wml.pop3;

import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.internet.MimePart;
import javax.mail.internet.MimePartDataSource;

/**
 * <br>[機  能] メール受信(POP)に関する機能を提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Pop3Utility {

    /**
     * <br>[機  能] 指定したPartのコンテンツを取得する
     * <br>[解  説] コンテンツ取得の際、DataHandlerの変更を行う
     * <br>[備  考]
     * @param part Part
     * @return Part#getContent()の結果
     * @throws MessagingException コンテンツ取得に失敗
     */
    public static Object getContent(Part part) throws MessagingException {
        Object content = null;
        try {
            DataHandler handler = getDataHandler(part);
            content = handler.getContent();
        } catch (IOException ioe) {
            throw new MessagingException(ioe.toString(), ioe);
        } catch (NullPointerException ne) {
            try {
                content = part.getContent();
            } catch (IOException e) {
            }
        }
        return content;
    }

    /**
     * <br>[機  能] 指定したPartからDataHandlerを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param part Part
     * @return DataHandler
     * @throws MessagingException DataHandlerの取得に失敗
     */
    public static DataHandler getDataHandler(Part part) throws MessagingException {
        DataSource dataSource = part.getDataHandler().getDataSource();
        if (dataSource == null) {
            dataSource = new Pop3DataSource(
                    new MimePartDataSource((MimePart) part));
        } else {
            dataSource = new Pop3DataSource(dataSource);
        }
        DataHandler handler = new DataHandler(dataSource);
        return handler;
    }
}
