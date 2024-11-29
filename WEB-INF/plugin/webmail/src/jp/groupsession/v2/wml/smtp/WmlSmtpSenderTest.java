package jp.groupsession.v2.wml.smtp;

import jp.groupsession.v2.wml.smtp.model.SmtpModel;
import jp.groupsession.v2.wml.smtp.model.SmtpSendModel;

/**
 * <br>[機  能] jp.groupsession.v2.wml.smtp.WmlSmtpSenderのテストクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class WmlSmtpSenderTest {

    /**
     * <br>[機  能] mainメソッド
     * <br>[解  説]
     * <br>[備  考]
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        WmlSmtpSender sender = null;
        try {
            sender = new WmlSmtpSender();

            SmtpModel smtpData = new SmtpModel();
            smtpData.setSendServer("mail.example.com");
            smtpData.setSendPort(587);
            smtpData.setSmtpAuth(true);
            smtpData.setSendUser("user1");
            smtpData.setSendPassword("pass1");
            smtpData.setSendEncrypt(0);
            smtpData.setEncode("UTF-8");
            smtpData.setPopBeforeSmtp(false);
            smtpData.setPopServer("mail.example.com");
            smtpData.setPopServerPort(110);
            smtpData.setPopServerUser("user1");
            smtpData.setPopServerPassword("pass1");
            smtpData.setPopServerEncrypt(0);

            sender.connect(smtpData);

            SmtpSendModel sendMailData = new SmtpSendModel();
            sendMailData.setSubject("Mail send test ");
            sendMailData.setFrom("user1@example.com");
            sendMailData.setTo("user2@example.com");
            sendMailData.setCc(null);
            sendMailData.setBcc(null);
            //sendMailData.setBody(null);
            sendMailData.setBody("");
            sendMailData.setTempFileList(null);
            sendMailData.setHtmlMail(false);

            sender.send(sendMailData);

            System.out.println("送信完了");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            sender.disConnect();
        }
    }

}
