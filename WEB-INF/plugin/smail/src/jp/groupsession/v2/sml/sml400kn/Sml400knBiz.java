package jp.groupsession.v2.sml.sml400kn;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.dao.SmlAdminDao;
import jp.groupsession.v2.sml.model.SmlAdminModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] ショートメール 管理者設定 表示設定確認画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Sml400knBiz {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Sml400knBiz.class);
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <br>[機  能] デフォルトコンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     *@param reqMdl リクエスト情報
     */
    public Sml400knBiz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] 初期表示情報を画面にセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws SQLException SQL実行例外
     */
    public void setInitData(
            Sml400knParamModel paramMdl,
            Connection con)
                    throws SQLException {
        GsMessage gsMsg = new GsMessage();

        //表示件数
        if (paramMdl.getSml400MaxDspStype()
                == GSConstSmail.DISP_CONF_ADMIN) {
            //「管理者が設定する」場合
            StringBuilder maxDspSb = new StringBuilder();
            int maxDsp = paramMdl.getSml400MaxDsp();
            maxDspSb.append(String.valueOf(maxDsp));
            maxDspSb.append(gsMsg.getMessage("cmn.number"));
            paramMdl.setSml400knMaxDsp(maxDspSb.toString());
        }

        //自動リロード時間
        if (paramMdl.getSml400ReloadTimeStype()
                == GSConstSmail.DISP_CONF_ADMIN) {
            //「管理者が設定する」場合
            String reloadTime = paramMdl.getSml400ReloadTime();
            paramMdl.setSml400knReloadTime(__reloadTimeToDisplay(reloadTime));
        }

        //写真表示設定
        if (paramMdl.getSml400PhotoDspStype()
                == GSConstSmail.DISP_CONF_ADMIN) {
            //「管理者が設定する」場合
            String photoDspStr = "";
            int photoDsp = paramMdl.getSml400PhotoDsp();
            if (photoDsp == GSConstSmail.SML_PHOTO_DSP_DSP) {
                //表示
                photoDspStr = gsMsg.getMessage("cmn.show");
            } else {
                //非表示
                photoDspStr = gsMsg.getMessage("cmn.hide");
            }
            paramMdl.setSml400knPhotoDsp(photoDspStr);
        }

        //添付画像表示設定
        if (paramMdl.getSml400AttachImgDspStype()
                == GSConstSmail.DISP_CONF_ADMIN) {
            //「管理者が設定する」場合
            String attachImgDspStr = "";
            int attachImgDsp = paramMdl.getSml400AttachImgDsp();
            if (attachImgDsp == GSConstSmail.SML_IMAGE_TEMP_DSP) {
                //表示
                attachImgDspStr = gsMsg.getMessage("cmn.show");
            } else {
                //非表示
                attachImgDspStr = gsMsg.getMessage("cmn.hide");
            }
            paramMdl.setSml400knAttachImgDsp(attachImgDspStr);
        }

    }

    /**
     * <br>[機  能] 自動リロード時間を表示用に置き換える
     * <br>[解  説]
     * <br>[備  考]
     * @param reloadTime 自動リロード時間
     * @return 表示用の自動リロード時間
     */
    private String __reloadTimeToDisplay(String reloadTime) {
        String ret = "";
        GsMessage gsMsg = new GsMessage();

        if (reloadTime.equals("60000")) {
            //1分
            ret = gsMsg.getMessage("cmn.1minute");

        } else if (reloadTime.equals("180000")) {
            //3分
            ret = "3" + gsMsg.getMessage("cmn.minute");

        } else if (reloadTime.equals("300000")) {
            //5分
            ret = "5" + gsMsg.getMessage("cmn.minute");

        } else if (reloadTime.equals("600000")) {
            //10分
            ret = "10" + gsMsg.getMessage("cmn.minute");

        } else if (reloadTime.equals("1200000")) {
            //20分
            ret = "20" + gsMsg.getMessage("cmn.minute");

        } else if (reloadTime.equals("1800000")) {
            //30分
            ret = "30" + gsMsg.getMessage("cmn.minute");

        } else if (reloadTime.equals("2400000")) {
            //40分
            ret = "40" + gsMsg.getMessage("cmn.minute");

        } else if (reloadTime.equals("3000000")) {
            //50分
            ret = "50" + gsMsg.getMessage("cmn.minute");

        } else if (reloadTime.equals("3600000")) {
            //60分
            ret = "60" + gsMsg.getMessage("cmn.minute");

        } else {
            //リロードしない
            ret = gsMsg.getMessage("cmn.without.reloading");
        }
        return ret;
    }

    /**
     * <br>[機  能] 更新処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @throws Exception 実行例外
     */
    public void setData(
            Sml400knParamModel paramMdl,
            Connection con)
                    throws Exception {

        boolean commitFlg = false;

        try {
            //セッション情報を取得
            BaseUserModel usModel = reqMdl__.getSmodel();
            int sessionUsrSid = usModel.getUsrsid();
            UDate nowDate = new UDate();

            SmlAdminModel admMdl = new SmlAdminModel();
            admMdl.setSmaEuid(sessionUsrSid);
            admMdl.setSmaEdate(nowDate);

            admMdl.setSmaMaxDspStype(paramMdl.getSml400MaxDspStype());
            admMdl.setSmaMaxDsp(paramMdl.getSml400MaxDsp());
            admMdl.setSmaReloadDspStype(paramMdl.getSml400ReloadTimeStype());
            admMdl.setSmaReloadDsp(NullDefault.getInt(paramMdl.getSml400ReloadTime(), 0));
            admMdl.setSmaPhotoDspStype(paramMdl.getSml400PhotoDspStype());
            admMdl.setSmaPhotoDsp(paramMdl.getSml400PhotoDsp());
            admMdl.setSmaAttachDspStype(paramMdl.getSml400AttachImgDspStype());
            admMdl.setSmaAttachDsp(paramMdl.getSml400AttachImgDsp());

            SmlAdminDao admDao = new SmlAdminDao(con);

            //テーブルにデータが存在するかチェックする
            int count = admDao.selectCount();

            if (count > 0) {
                //更新処理
                admDao.updateDisplaySetting(admMdl);
            } else {
                //追加処理
                admMdl.setSmaAuid(sessionUsrSid);
                admMdl.setSmaAdate(nowDate);
                admDao.insert(admMdl);
            }

            commitFlg = true;

        } catch (SQLException e) {
            log__.error("SQLException", e);
            throw e;

        } finally {
            if (commitFlg) {
                con.commit();
            } else {
                JDBCUtil.rollback(con);
            }
        }
    }

    /**
     * <br>[機  能]オペレーションログ出力用設定内容を取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ格納モデル
     * @return オペレーションログ表示内容
     */
    public String getOpLog(Sml400knParamModel paramMdl) {

        GsMessage gsMsg = new GsMessage(reqMdl__);
        StringBuilder opLog = new StringBuilder();
        //表示
        String display = gsMsg.getMessage("api.cmn.view");
        //非表示
        String nodispaly = gsMsg.getMessage("cmn.hide");

        //*表示件数*//
        opLog.append(__opLogValue(gsMsg.getMessage("cmn.number.display")));
        if (paramMdl.getSml400MaxDspStype() == GSConstSmail.DISP_CONF_ADMIN) {
            opLog.append(gsMsg.getMessage("cmn.set.the.admin") + "\r\n");
            opLog.append(paramMdl.getSml400MaxDsp());
            opLog.append(gsMsg.getMessage("cmn.number"));
        } else if (paramMdl.getSml400MaxDspStype() == GSConstSmail.DISP_CONF_USER) {
            opLog.append(gsMsg.getMessage("cmn.set.eachuser"));
        }
        opLog.append("\n");

        //*自動リロード時間*//
        String minute = gsMsg.getMessage("cmn.minute");
        opLog.append(__opLogValue(gsMsg.getMessage("cmn.auto.reload.time")));
        if (paramMdl.getSml400ReloadTimeStype() == GSConstSmail.DISP_CONF_ADMIN) {
            opLog.append(gsMsg.getMessage("cmn.set.the.admin") + "\r\n");

            //自動リロード時間の変換
            switch (NullDefault.getInt(paramMdl.getSml400ReloadTime(), 0)) {
            case 60000:
                opLog.append(gsMsg.getMessage("cmn.1minute"));
                break;
            case 180000:
                opLog.append("3");
                opLog.append(minute);
                break;
            case 300000:
                opLog.append("5");
                opLog.append(minute);
                break;
            case 600000:
                opLog.append("10");
                opLog.append(minute);
                break;
            case 1200000:
                opLog.append("20");
                opLog.append(minute);
                break;
            case 1800000:
                opLog.append("30");
                opLog.append(minute);
                break;
            case 2400000:
                opLog.append("40");
                opLog.append(minute);
                break;
            case 3000000:
                opLog.append("50");
                opLog.append(minute);
                break;
            case 3600000:
                opLog.append("60");
                opLog.append(minute);
                break;
            default:
                opLog.append(gsMsg.getMessage("cmn.without.reloading"));
                break;
            }
        } else if (paramMdl.getSml400ReloadTimeStype() == GSConstSmail.DISP_CONF_USER) {
            opLog.append(gsMsg.getMessage("cmn.set.eachuser"));
        }
        opLog.append("\n");

        //*写真の表示設定*//
        int picture = paramMdl.getSml400PhotoDsp();
        opLog.append(__opLogValue(gsMsg.getMessage("sml.sml040.05")));
        if (paramMdl.getSml400PhotoDspStype() == GSConstSmail.DISP_CONF_ADMIN) {
            opLog.append(gsMsg.getMessage("cmn.set.the.admin") + "\r\n");
            if (picture == GSConstSmail.SML_PHOTO_DSP_DSP) {
                opLog.append(display);
            } else if (picture != GSConstSmail.SML_PHOTO_DSP_DSP) {
                opLog.append(nodispaly);
            }
        } else if (paramMdl.getSml400PhotoDspStype() == GSConstSmail.DISP_CONF_USER) {
            opLog.append(gsMsg.getMessage("cmn.set.eachuser"));
        }
        opLog.append("\n");

        //*添付画像表示設定*//
        int image = paramMdl.getSml400AttachImgDsp();
        opLog.append(__opLogValue(gsMsg.getMessage("sml.sml040.07")));
        if (paramMdl.getSml400AttachImgDspStype() == GSConstSmail.DISP_CONF_ADMIN) {
            opLog.append(gsMsg.getMessage("cmn.set.the.admin") + "\r\n");
            if (image == GSConstSmail.SML_IMAGE_TEMP_DSP) {
                opLog.append(display);
            } else if (image == GSConstSmail.SML_IMAGE_TEMP_NOT_DSP) {
                opLog.append(nodispaly);
            }
        } else if (paramMdl.getSml400AttachImgDspStype() == GSConstSmail.DISP_CONF_USER) {
            opLog.append(gsMsg.getMessage("cmn.set.eachuser"));
        }
        return opLog.toString();
    }

    /**
     * <br>[機  能]オペレーションログ出力項目まとめロジック
     * <br>[解  説]
     * <br>[備  考]
     * @param value 設定項目
     * @return [設定項目名]
     */
    private String __opLogValue(String value) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(value);
        sb.append("] ");
        return sb.toString();
    }
}