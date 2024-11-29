package jp.groupsession.v2.bbs.bbs070;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.bbs.BbsBiz;
import jp.groupsession.v2.bbs.GSConstBulletin;
import jp.groupsession.v2.bbs.bbs220.Bbs220Biz;
import jp.groupsession.v2.bbs.dao.BbsBodyBinDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouBinDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouBodyBinDao;
import jp.groupsession.v2.bbs.dao.BbsSoukouDao;
import jp.groupsession.v2.bbs.dao.BbsThreInfDao;
import jp.groupsession.v2.bbs.dao.BbsWriteInfDao;
import jp.groupsession.v2.bbs.model.BbsBodyBinModel;
import jp.groupsession.v2.bbs.model.BbsSoukouBinModel;
import jp.groupsession.v2.bbs.model.BbsSoukouBodyBinModel;
import jp.groupsession.v2.bbs.model.BbsSoukouModel;
import jp.groupsession.v2.bbs.model.BbsThreInfModel;
import jp.groupsession.v2.bbs.model.BulletinDspModel;
import jp.groupsession.v2.bbs.model.BulletinSoukouModel;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.DateTimePickerBiz;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 掲示板 スレッド登録画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Bbs070Biz {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Bbs070Biz.class);

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、スレッド情報を設定する
     * <br>[備  考]
     * @param cmd CMDパラメータ
     * @param reqMdl リクエスト情報
     * @param paramMdl パラメータ情報
     * @param con コネクション
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @throws Exception 実行時例外
     */
    @SuppressWarnings("unchecked")
    public void setInitData(String cmd, RequestModel reqMdl, Bbs070ParamModel paramMdl,
            Connection con, String appRoot, String tempDir)
                    throws Exception {
        log__.debug("START");

        CommonBiz cmnBiz = new CommonBiz();
        BbsBiz bbsBiz = new BbsBiz();
        GsMessage gsMsg = new GsMessage(reqMdl);

        int userSid = reqMdl.getSmodel().getUsrsid();

        //フォーラム名を設定
        BulletinDspModel btDspMdl = bbsBiz.getForumData(con, paramMdl.getBbs010forumSid());
        paramMdl.setBbs070forumName(btDspMdl.getBfiName());
        //フォーラム掲示期間有無
        paramMdl.setBbs070limitDisable(btDspMdl.getBfiLimitOn());
        //無期限設定
        paramMdl.setBbs070Unlimited(btDspMdl.getBfiUnlimited());

        //開始年月日
        int frYear = 0;
        //投稿一覧からの遷移、かつ処理モード = 編集の場合、または投稿一覧からの複写新規はスレッド情報を取得する
        //
        DateTimePickerBiz dateBiz = new DateTimePickerBiz();
        if (((cmd.equals("editThreOrPost")
                || cmd.equals("moveResult"))
                && paramMdl.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT)
            || (cmd.equals("copyThread")
                    && paramMdl.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_COPY)) {
            con.setAutoCommit(false);
            //スレッド情報を設定
            BulletinDspModel bbsMdl = bbsBiz.getThreadData(con, paramMdl.getThreadSid());
            paramMdl.setBbs070title(bbsMdl.getBtiTitle());
            paramMdl.setBbs070limit(bbsMdl.getBtiLimit());
            
            if (paramMdl.getBbs070limit() == GSConstBulletin.THREAD_LIMIT_YES) {
                UDate limitFrDate = bbsMdl.getBtiLimitFrDate();
                frYear = limitFrDate.getYear();
                paramMdl.setBbs070limitFrYear(limitFrDate.getYear());
                paramMdl.setBbs070limitFrMonth(limitFrDate.getMonth());
                paramMdl.setBbs070limitFrDay(limitFrDate.getIntDay());
                paramMdl.setBbs070limitFrHour(limitFrDate.getIntHour());
                paramMdl.setBbs070limitFrMinute(limitFrDate.getIntMinute());
                
                if (paramMdl.getBbs070limitFrDate() == null) {
                    dateBiz.setDateParam(paramMdl, "bbs070limitFrDate",
                            "bbs070limitFrYear", "bbs070limitFrMonth",
                            "bbs070limitFrDay", gsMsg.getMessage("bbs.bbs070.5"));
                }
                
                if (paramMdl.getBbs070limitFrTime() == null) {
                    dateBiz.setTimeParam(paramMdl, "bbs070limitFrTime",
                            "bbs070limitFrHour", "bbs070limitFrMinute",
                            gsMsg.getMessage("bbs.bbs070.5"));
                }

                UDate limitToDate = bbsMdl.getBtiLimitDate();
                paramMdl.setBbs070limitYear(limitToDate.getYear());
                paramMdl.setBbs070limitMonth(limitToDate.getMonth());
                paramMdl.setBbs070limitDay(limitToDate.getIntDay());
                paramMdl.setBbs070limitHour(limitToDate.getIntHour());
                paramMdl.setBbs070limitMinute(limitToDate.getIntMinute());
                
                if (paramMdl.getBbs070limitToDate() == null) {
                    dateBiz.setDateParam(paramMdl, "bbs070limitToDate",
                            "bbs070limitYear", "bbs070limitMonth",
                            "bbs070limitDay", gsMsg.getMessage("bbs.bbs070.6"));
                }
                if (paramMdl.getBbs070limitToTime() == null) {
                    dateBiz.setTimeParam(paramMdl, "bbs070limitToTime",
                            "bbs070limitHour", "bbs070limitMinute",
                            gsMsg.getMessage("bbs.bbs070.6"));
                }
            } else {
                UDate now = new UDate();
                paramMdl.setBbs070limitFrYear(now.getYear());
                paramMdl.setBbs070limitFrMonth(now.getMonth());
                paramMdl.setBbs070limitFrDay(now.getIntDay());
                paramMdl.setBbs070limitFrHour(now.getIntHour());
                paramMdl.setBbs070limitFrMinute(now.getIntMinute());
                
                if (paramMdl.getBbs070limitFrDate() == null) {
                    dateBiz.setDateParam(paramMdl, "bbs070limitFrDate",
                            "bbs070limitFrYear", "bbs070limitFrMonth",
                            "bbs070limitFrDay", gsMsg.getMessage("bbs.bbs070.5"));
                }
                
                if (paramMdl.getBbs070limitFrTime() == null) {
                    dateBiz.setTimeParam(paramMdl, "bbs070limitFrTime",
                            "bbs070limitFrHour", "bbs070limitFrMinute",
                            gsMsg.getMessage("bbs.bbs070.5"));
                }

                //掲示期間の初期値設定（フォーラムの設定を参照する）
                if (btDspMdl.getBfiLimit() == GSConstBulletin.THREAD_LIMIT_YES) {
                    //日数を加算
                    now.addDay(btDspMdl.getBfiLimitDate());
                    paramMdl.setBbs070limitYear(now.getYear());
                    paramMdl.setBbs070limitMonth(now.getMonth());
                    paramMdl.setBbs070limitDay(now.getIntDay());
                    paramMdl.setBbs070limitHour(now.getIntHour());
                    paramMdl.setBbs070limitMinute(now.getIntMinute());
                } else {
                    paramMdl.setBbs070limitYear(now.getYear());
                    paramMdl.setBbs070limitMonth(now.getMonth());
                    paramMdl.setBbs070limitDay(now.getIntDay());
                    paramMdl.setBbs070limitHour(now.getIntHour());
                    paramMdl.setBbs070limitMinute(now.getIntMinute());
                }
                
                if (paramMdl.getBbs070limitToDate() == null) {
                    dateBiz.setDateParam(paramMdl, "bbs070limitToDate",
                            "bbs070limitYear", "bbs070limitMonth",
                            "bbs070limitDay", gsMsg.getMessage("bbs.bbs070.6"));
                }
                if (paramMdl.getBbs070limitToTime() == null) {
                    dateBiz.setTimeParam(paramMdl, "bbs070limitToTime",
                            "bbs070limitHour", "bbs070limitMinute",
                            gsMsg.getMessage("bbs.bbs070.6"));
                }
            }

            //例外条件(掲示期間 無効 + スレッド期限設定あり)
            if (paramMdl.getBbs070limitDisable() == GSConstBulletin.THREAD_DISABLE
                    && paramMdl.getBbs070limit() == GSConstBulletin.THREAD_LIMIT_YES) {
                paramMdl.setBbs070limitException(GSConstBulletin.THREAD_EXCEPTION);
            }

            //スレッドの投稿情報を設定
            BulletinDspModel bbsWriteMdl = bbsBiz.getWriteData(con, paramMdl.getBbs060postSid());
            if (bbsWriteMdl == null) {
                bbsWriteMdl = new BulletinDspModel();
            }

            //投稿本文タイプ
            int valueType = bbsWriteMdl.getBwiType();
            paramMdl.setBbs070valueType(valueType);

            //本文
            String body = NullDefault.getString(bbsWriteMdl.getBwiValue(), "");
            if (valueType == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
                //テキスト形式の場合
                paramMdl.setBbs070valueHtml("");
                paramMdl.setBbs070value(body);
            } else {
                //HTML形式の場合、本文内のファイルタグを編集用に修正
                body = getBbsBodyToEdit(paramMdl.getTempDirId(), body, false);
                paramMdl.setBbs070valueHtml(body);
                paramMdl.setBbs070value("");
            }

            //本文内添付ファイルをテンポラリディレクトリへ移動する
            bbsCopyBodyFileToTemp(reqMdl, paramMdl.getBbs060postSid(), con, appRoot, tempDir);

            if (bbsWriteMdl.getGrpSid() > 0) {
                paramMdl.setBbs070contributor(bbsWriteMdl.getGrpSid());
            }

            //重要度
            paramMdl.setBbs070Importance(bbsMdl.getBfiThreImportance());

            //添付ファイルをテンポラリディレクトリへ移動する
            String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)
            if (bbsWriteMdl.getTmpFileList() != null && bbsWriteMdl.getTmpFileList().size() > 0) {
                List < CmnBinfModel > tmpFileList = bbsWriteMdl.getTmpFileList();
                String[] binSids = new String[tmpFileList.size()];

                //バイナリSIDの取得
                for (int i = 0; i < tmpFileList.size(); i++) {
                    binSids[i] = String.valueOf(tmpFileList.get(i).getBinSid());
                }

                //取得したバイナリSIDからバイナリ情報を取得
                List<CmnBinfModel> binList = cmnBiz.getBinInfo(con, binSids, reqMdl.getDomain());

                int fileNum = 1;
                for (CmnBinfModel binData : binList) {
                    cmnBiz.saveTempFile(dateStr, binData, appRoot, tempDir, fileNum);
                    fileNum++;
                }
            }
        }

        // 草稿処理
        if ((cmd.equals("soukouEdit")
                && paramMdl.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_SOUKOU)) {

            con.setAutoCommit(false);
            //スレッド情報を設定
            BulletinSoukouModel bbsSoukouMdl
            = bbsBiz.getSoukouData(con, paramMdl.getSoukouSid(), userSid);
            paramMdl.setBbs070title(bbsSoukouMdl.getBskTitle());
            paramMdl.setBbs070limit(bbsSoukouMdl.getBskLimit());
            if (paramMdl.getBbs070limit() == GSConstBulletin.THREAD_LIMIT_YES) {
                UDate limitFrDate = bbsSoukouMdl.getBskLimitFrDate();
                frYear = limitFrDate.getYear();
                paramMdl.setBbs070limitFrYear(limitFrDate.getYear());
                paramMdl.setBbs070limitFrMonth(limitFrDate.getMonth());
                paramMdl.setBbs070limitFrDay(limitFrDate.getIntDay());
                paramMdl.setBbs070limitFrHour(limitFrDate.getIntHour());
                paramMdl.setBbs070limitFrMinute(limitFrDate.getIntMinute());
                
                if (paramMdl.getBbs070limitFrDate() == null) {
                    dateBiz.setDateParam(paramMdl, "bbs070limitFrDate",
                            "bbs070limitFrYear", "bbs070limitFrMonth",
                            "bbs070limitFrDay", gsMsg.getMessage("bbs.bbs070.5"));
                }
                if (paramMdl.getBbs070limitFrTime() == null) {
                    dateBiz.setTimeParam(paramMdl, "bbs070limitFrTime",
                            "bbs070limitFrHour", "bbs070limitFrMinute",
                            gsMsg.getMessage("bbs.bbs070.5"));
                }

                UDate limitToDate = bbsSoukouMdl.getBskLimitDate();
                paramMdl.setBbs070limitYear(limitToDate.getYear());
                paramMdl.setBbs070limitMonth(limitToDate.getMonth());
                paramMdl.setBbs070limitDay(limitToDate.getIntDay());
                paramMdl.setBbs070limitHour(limitToDate.getIntHour());
                paramMdl.setBbs070limitMinute(limitToDate.getIntMinute());
                
                if (paramMdl.getBbs070limitToDate() == null) {
                    dateBiz.setDateParam(paramMdl, "bbs070limitToDate",
                            "bbs070limitYear", "bbs070limitMonth",
                            "bbs070limitDay", gsMsg.getMessage("bbs.bbs070.6"));
                }
                if (paramMdl.getBbs070limitToTime() == null) {
                    dateBiz.setTimeParam(paramMdl, "bbs070limitToTime",
                            "bbs070limitHour", "bbs070limitMinute",
                            gsMsg.getMessage("bbs.bbs070.6"));
                }
            } else {
                UDate now = new UDate();
                paramMdl.setBbs070limitFrYear(now.getYear());
                paramMdl.setBbs070limitFrMonth(now.getMonth());
                paramMdl.setBbs070limitFrDay(now.getIntDay());
                paramMdl.setBbs070limitFrHour(now.getIntHour());
                paramMdl.setBbs070limitFrMinute(now.getIntMinute());
                
                if (paramMdl.getBbs070limitFrDate() == null) {
                    dateBiz.setDateParam(paramMdl, "bbs070limitFrDate",
                            "bbs070limitFrYear", "bbs070limitFrMonth",
                            "bbs070limitFrDay", gsMsg.getMessage("bbs.bbs070.5"));
                }
                if (paramMdl.getBbs070limitFrTime() == null) {
                    dateBiz.setTimeParam(paramMdl, "bbs070limitFrTime",
                            "bbs070limitFrHour", "bbs070limitFrMinute",
                            gsMsg.getMessage("bbs.bbs070.5"));
                }
                
                //掲示期間の初期値設定（フォーラムの設定を参照する）
                if (btDspMdl.getBfiLimit() == GSConstBulletin.THREAD_LIMIT_YES) {
                    //日数を加算
                    now.addDay(btDspMdl.getBfiLimitDate());
                    paramMdl.setBbs070limitYear(now.getYear());
                    paramMdl.setBbs070limitMonth(now.getMonth());
                    paramMdl.setBbs070limitDay(now.getIntDay());
                    paramMdl.setBbs070limitHour(now.getIntHour());
                    paramMdl.setBbs070limitMinute(now.getIntMinute());
                } else {
                    paramMdl.setBbs070limitYear(now.getYear());
                    paramMdl.setBbs070limitMonth(now.getMonth());
                    paramMdl.setBbs070limitDay(now.getIntDay());
                    paramMdl.setBbs070limitHour(now.getIntHour());
                    paramMdl.setBbs070limitMinute(now.getIntMinute());
                }
                
                if (paramMdl.getBbs070limitToDate() == null) {
                    dateBiz.setDateParam(paramMdl, "bbs070limitToDate",
                            "bbs070limitYear", "bbs070limitMonth",
                            "bbs070limitDay", gsMsg.getMessage("bbs.bbs070.6"));
                }
                if (paramMdl.getBbs070limitToTime() == null) {
                    dateBiz.setTimeParam(paramMdl, "bbs070limitToTime",
                            "bbs070limitHour", "bbs070limitMinute",
                            gsMsg.getMessage("bbs.bbs070.6"));
                }
            }
            
            //例外条件(掲示期間 無効 + スレッド期限設定あり)
            if (paramMdl.getBbs070limitDisable() == GSConstBulletin.THREAD_DISABLE
                    && paramMdl.getBbs070limit() == GSConstBulletin.THREAD_LIMIT_YES) {
                paramMdl.setBbs070limitException(GSConstBulletin.THREAD_EXCEPTION);
            }

            //投稿本文タイプ
            int valueType = bbsSoukouMdl.getBskType();
            paramMdl.setBbs070valueType(valueType);

            //本文
            String body = NullDefault.getString(bbsSoukouMdl.getBskValue(), "");
            if (valueType == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
                //テキスト形式の場合
                paramMdl.setBbs070valueHtml("");
                paramMdl.setBbs070value(body);
            } else {
                //HTML形式の場合、本文内のファイルタグを編集用に修正
                body = getBbsBodyToEdit(paramMdl.getTempDirId(), body, true);
                paramMdl.setBbs070valueHtml(body);
                paramMdl.setBbs070value("");
            }

            Bbs220Biz soukouBiz = new Bbs220Biz();
            //本文内添付ファイルをテンポラリディレクトリへ移動する
            soukouBiz.bbsCopyBodyFileToTemp(
                    reqMdl, bbsSoukouMdl.getBskSid(), con, appRoot, tempDir);

            if (bbsSoukouMdl.getBskAgid() > 0) {
                paramMdl.setBbs070contributor(bbsSoukouMdl.getBskAgid());
            }

            //重要度
            paramMdl.setBbs070Importance(bbsSoukouMdl.getBskImportance());

            //添付ファイルをテンポラリディレクトリへ移動する
            String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)
            if (bbsSoukouMdl.getFilesInfo() != null && bbsSoukouMdl.getFilesInfo().size() > 0) {
                List < CmnBinfModel > tmpFileList = bbsSoukouMdl.getFilesInfo();
                String[] binSids = new String[tmpFileList.size()];
                //バイナリSIDの取得
                for (int i = 0; i < tmpFileList.size(); i++) {
                    binSids[i] = String.valueOf(tmpFileList.get(i).getBinSid());
                }
                //取得したバイナリSIDからバイナリ情報を取得
                List<CmnBinfModel> binList = cmnBiz.getBinInfo(con, binSids, reqMdl.getDomain());
                int fileNum = 1;
                for (CmnBinfModel binData : binList) {
                    cmnBiz.saveTempFile(dateStr, binData, appRoot, tempDir, fileNum);
                    fileNum++;
                }
            }
        }
        if (cmd.equals("addThread")
                && paramMdl.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_ADD) {

            UDate now = new UDate();
            paramMdl.setBbs070limitFrYear(now.getYear());
            paramMdl.setBbs070limitFrMonth(now.getMonth());
            paramMdl.setBbs070limitFrDay(now.getIntDay());
            
            dateBiz.setDateParam(paramMdl, "bbs070limitFrDate",
                    "bbs070limitFrYear", "bbs070limitFrMonth",
                    "bbs070limitFrDay", gsMsg.getMessage("bbs.bbs070.5"));
            
            paramMdl.setBbs070limitFrHour(0);
            paramMdl.setBbs070limitFrMinute(0);
            dateBiz.setTimeParam(paramMdl, "bbs070limitFrTime",
                    "bbs070limitFrHour", "bbs070limitFrMinute",
                    gsMsg.getMessage("bbs.bbs070.5"));

            //投稿本文タイプ
            int valueType = bbsBiz.getInitPostType(
                    con, paramMdl.getBbs010forumSid(), userSid);
            paramMdl.setBbs070valueType(valueType);

            //掲示期間の初期値設定（フォーラムの設定を参照する）
            if (btDspMdl.getBfiLimit() == GSConstBulletin.THREAD_LIMIT_YES) {

                paramMdl.setBbs070limit(GSConstBulletin.THREAD_LIMIT_YES);

                //日数を加算
                now.addDay(btDspMdl.getBfiLimitDate());
                paramMdl.setBbs070limitYear(now.getYear());
                paramMdl.setBbs070limitMonth(now.getMonth());
                paramMdl.setBbs070limitDay(now.getIntDay());
            } else {
                paramMdl.setBbs070limit(GSConstBulletin.THREAD_LIMIT_NO);
                now.addDay(1);
                paramMdl.setBbs070limitYear(now.getYear());
                paramMdl.setBbs070limitMonth(now.getMonth());
                paramMdl.setBbs070limitDay(now.getIntDay());
            }
            
            dateBiz.setDateParam(paramMdl, "bbs070limitToDate",
                    "bbs070limitYear", "bbs070limitMonth",
                    "bbs070limitDay", gsMsg.getMessage("bbs.bbs070.6"));
            paramMdl.setBbs070limitHour(0);
            paramMdl.setBbs070limitMinute(0);
            dateBiz.setTimeParam(paramMdl, "bbs070limitToTime",
                    "bbs070limitHour", "bbs070limitMinute",
                    gsMsg.getMessage("bbs.bbs070.6"));

            //スレッドテンプレートを適用する。
            if (btDspMdl.getBfiTemplateKbn() == GSConstBulletin.BBS_THRE_TEMPLATE_YES) {
                String template = NullDefault.getString(btDspMdl.getBfiTemplate(), "");
                if (btDspMdl.getBfiTemplateType() == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
                    paramMdl.setBbs070value(template);
                } else {
                    paramMdl.setBbs070valueHtml(template);
                }
            }
        }
        
        if (cmd.equals("backToInput")) {
            
            dateBiz.setDateParam(paramMdl, "bbs070limitFrDate",
                    "bbs070limitFrYear", "bbs070limitFrMonth",
                    "bbs070limitFrDay", gsMsg.getMessage("bbs.bbs070.5"));
            dateBiz.setTimeParam(paramMdl, "bbs070limitFrTime",
                    "bbs070limitFrHour", "bbs070limitFrMinute",
                    gsMsg.getMessage("bbs.bbs070.5"));
            
            dateBiz.setDateParam(paramMdl, "bbs070limitToDate",
                    "bbs070limitYear", "bbs070limitMonth",
                    "bbs070limitDay", gsMsg.getMessage("bbs.bbs070.6"));
            
            dateBiz.setTimeParam(paramMdl, "bbs070limitToTime",
                    "bbs070limitHour", "bbs070limitMinute",
                    gsMsg.getMessage("bbs.bbs070.6"));
            
        }

        //添付ファイル一覧を設定
        List<LabelValueBean> sortList = cmnBiz.getTempFileLabelList(tempDir);
        Collections.sort(sortList);
        paramMdl.setBbs070FileLabelList(sortList);

        //年月日、時間コンボを設定
        UDate now = new UDate();
        int nowYear = now.getYear();
        int yearStart = nowYear;
        if (frYear != 0 && nowYear > frYear) {
            yearStart = frYear;
        }
        paramMdl.setBbs070oldYear(nowYear - yearStart);
        paramMdl.setBbs070hourDivision(btDspMdl.getMinDiv());

        //投稿者コンボを設定
        int registUser = userSid;
        if (paramMdl.getBbs070cmdMode() == GSConstBulletin.BBSCMDMODE_EDIT) {
            //編集の場合、新規登録時の登録ユーザを投稿者の基準とする
            BbsWriteInfDao writeDao = new BbsWriteInfDao(con);
            registUser = writeDao.getWriteAuid(paramMdl.getBbs060postSid());
        }
        CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con);
        CmnUsrmInfModel usiMdl = usiDao.selectUserNameAndJtkbn(registUser);

        int contributor = paramMdl.getBbs070contributor();
        if (contributor > 0) {
            GroupDao grpDao = new GroupDao(con);
            if (!grpDao.isBelong(registUser, contributor)
                    && paramMdl.getBbs070cmdMode() != GSConstBulletin.BBSCMDMODE_COPY
                    && paramMdl.getBbs070cmdMode() != GSConstBulletin.BBSCMDMODE_SOUKOU) {
                //投稿者が投稿グループに所属していない場合には投稿者を編集不可にする。
                paramMdl.setBbs070contributorEditKbn(0);
                CmnGroupmDao cmnGrpDao = new CmnGroupmDao(con);
                CmnGroupmModel grpMdl = cmnGrpDao.selectGroup(contributor);
                if (grpMdl != null) {
                    paramMdl.setBbs070contributorJKbn(grpMdl.getGrpJkbn());
                    UsrLabelValueBean label;
                    List<UsrLabelValueBean> labelList = new ArrayList<UsrLabelValueBean>();
                    label = new UsrLabelValueBean(grpMdl.getGrpName(), String
                            .valueOf(contributor));
                    labelList.add(label);
                    paramMdl.setBbs070contributorList(labelList);
                }
            }
        } else if (usiMdl.getUsrJkbn() == GSConstUser.USER_JTKBN_DELETE) {
            //投稿者が削除済みユーザの場合には投稿者を編集不可にする。
            paramMdl.setBbs070contributorEditKbn(0);

            UsrLabelValueBean label;
            List<UsrLabelValueBean> labelList = new ArrayList<UsrLabelValueBean>();
            String name = NullDefault.getString(usiMdl.getUsiSei(), "")
                    + "　" + NullDefault.getString(usiMdl.getUsiMei(), "");
            paramMdl.setBbs070contributorJKbn(usiMdl.getUsrJkbn());

            label = new UsrLabelValueBean(name, String
                    .valueOf(contributor));
            labelList.add(label);
            paramMdl.setBbs070contributorList(labelList);
        }

        if (paramMdl.getBbs070contributorEditKbn() == 1) {
                //投稿者コンボを設定
            paramMdl.setBbs070contributorList(
                    cmnBiz.getMARegistrantCombo(con, registUser, reqMdl));
        }
        log__.debug("End");
    }

    /**
     * <br>[機  能] 本文データを取得します
     * <br>[解  説]
     * <br>[備  考] 表示用の本文内添付ファイルタグをTempディレクトリ内ファイルを指定するタグに置き換えます
     * @param tempDirId テンポラリディレクトリID
     * @param bodyValue 本文
     * @param soukouFlg 草稿モードからの遷移
     * @return 本文内添付ファイルタグをTempディレクトリ内ファイルを指定するタグに置き換えた本文
     * @throws Exception 実行時例外
     */
    public String getBbsBodyToEdit(
            String tempDirId, String bodyValue, boolean soukouFlg)
                    throws Exception {
        String ret = "";

        if (bodyValue.length() < 1) {
            return ret;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html[");

        //Entityを設定
        __setEntity(sb);

        sb.append("]>");

        String bodyHeader = "<root>";
        String bodyFooter = "</root>";

        sb.append(bodyHeader);
        sb.append(bodyValue);
        sb.append(bodyFooter);
        bodyValue = sb.toString();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        ByteArrayInputStream baiStream = null;
        StringWriter strWriter = null;

        try {
            baiStream = new ByteArrayInputStream(bodyValue.getBytes("UTF-8"));
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(baiStream);

            //<img>タグのsrcを取得
            NodeList imgNodeList =  doc.getElementsByTagName("img");

            if (imgNodeList != null && imgNodeList.getLength() > 0) {
                String startKey = null;
                String idAttrName = null;
                if (soukouFlg) {
                    startKey = "../bulletin/bbs220.do?CMD=getBodyFile";
                    idAttrName = "&bbs220BodyFileId=";
                } else {
                   startKey = "../bulletin/bbs060.do?CMD=getBodyFile";
                   idAttrName = "&bodyFileId=";
                }

                for (int i = 0; i < imgNodeList.getLength(); ++i) {
                    Node imgNode = imgNodeList.item(i);
                    if (imgNode.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    NamedNodeMap imgNodeAttrMap = imgNode.getAttributes();
                    Node srcAttr = imgNodeAttrMap.getNamedItem("src");
                    if (srcAttr == null) {
                        continue;
                    }
                    
                    String srcStr = srcAttr.getNodeValue();
                    if (srcStr == null || srcStr.length() < 1) {
                        continue;
                    }

                    int idxOfIdStart = srcStr.indexOf(idAttrName);
                    if (!srcStr.startsWith(startKey) || idxOfIdStart == 0) {
                        continue;
                    }

                    //bodyFileId = bbs060TempSaveId
                    String idStr = srcStr.substring(idxOfIdStart + idAttrName.length());
                    if (!StringUtil.isNullZeroString(idStr)) {
                        //bodyの該当部を変換
                        srcAttr.setNodeValue("bbs070.do?CMD=getBodyFile"
                                + "&bbs070TempSaveId=" + idStr
                                + "&tempDirId=" + tempDirId);
                    }
                }
            }

            //解析・変更した文を本文に戻して登録
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            strWriter = new StringWriter();
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, new StreamResult(strWriter));
            String strDoc = strWriter.toString();

            int valueStart = strDoc.indexOf(bodyHeader);
            int valueEnd = strDoc.lastIndexOf(bodyFooter);
            String formatedBody = strDoc.substring(valueStart + bodyHeader.length(), valueEnd);

            //解析・変更した文を返す
            ret = formatedBody;
        } catch (Exception e) {
            log__.error("HTMLの読み取りに失敗しました。", e);
            throw e;
        } finally {
            if (strWriter != null) {
                strWriter.close();
            }
            if (baiStream != null) {
                baiStream.close();
            }
        }

        return ret;
    }

    /**
     * <br>[機  能] Entityの記述を追加します。
     * <br>[解  説]
     * <br>[備  考]
     * @param sb 文字列
     * @return Entityを記述した文字列
     */
    private Object __setEntity(StringBuilder sb) {

        //ISO 8859-1 characters
        sb.append("<!ENTITY nbsp   \"&#160;\">");
        sb.append("<!ENTITY iexcl  \"&#161;\">");
        sb.append("<!ENTITY cent   \"&#162;\">");
        sb.append("<!ENTITY pound  \"&#163;\">");
        sb.append("<!ENTITY curren \"&#164;\">");
        sb.append("<!ENTITY yen    \"&#165;\">");
        sb.append("<!ENTITY brvbar \"&#166;\">");
        sb.append("<!ENTITY sect   \"&#167;\">");
        sb.append("<!ENTITY uml    \"&#168;\">");
        sb.append("<!ENTITY copy   \"&#169;\">");
        sb.append("<!ENTITY ordf   \"&#170;\">");
        sb.append("<!ENTITY laquo  \"&#171;\">");
        sb.append("<!ENTITY not    \"&#172;\">");
        sb.append("<!ENTITY shy    \"&#173;\">");
        sb.append("<!ENTITY reg    \"&#174;\">");
        sb.append("<!ENTITY macr   \"&#175;\">");
        sb.append("<!ENTITY deg    \"&#176;\">");
        sb.append("<!ENTITY plusmn \"&#177;\">");
        sb.append("<!ENTITY sup2   \"&#178;\">");
        sb.append("<!ENTITY sup3   \"&#179;\">");
        sb.append("<!ENTITY acute  \"&#180;\">");
        sb.append("<!ENTITY micro  \"&#181;\">");
        sb.append("<!ENTITY para   \"&#182;\">");
        sb.append("<!ENTITY middot \"&#183;\">");
        sb.append("<!ENTITY cedil  \"&#184;\">");
        sb.append("<!ENTITY sup1   \"&#185;\">");
        sb.append("<!ENTITY ordm   \"&#186;\">");
        sb.append("<!ENTITY raquo  \"&#187;\">");
        sb.append("<!ENTITY frac14 \"&#188;\">");
        sb.append("<!ENTITY frac12 \"&#189;\">");
        sb.append("<!ENTITY frac34 \"&#190;\">");
        sb.append("<!ENTITY iquest \"&#191;\">");
        sb.append("<!ENTITY Agrave \"&#192;\">");
        sb.append("<!ENTITY Aacute \"&#193;\">");
        sb.append("<!ENTITY Acirc  \"&#194;\">");
        sb.append("<!ENTITY Atilde \"&#195;\">");
        sb.append("<!ENTITY Auml   \"&#196;\">");
        sb.append("<!ENTITY Aring  \"&#197;\">");
        sb.append("<!ENTITY AElig  \"&#198;\">");
        sb.append("<!ENTITY Ccedil \"&#199;\">");
        sb.append("<!ENTITY Egrave \"&#200;\">");
        sb.append("<!ENTITY Eacute \"&#201;\">");
        sb.append("<!ENTITY Ecirc  \"&#202;\">");
        sb.append("<!ENTITY Euml   \"&#203;\">");
        sb.append("<!ENTITY Igrave \"&#204;\">");
        sb.append("<!ENTITY Iacute \"&#205;\">");
        sb.append("<!ENTITY Icirc  \"&#206;\">");
        sb.append("<!ENTITY Iuml   \"&#207;\">");
        sb.append("<!ENTITY ETH    \"&#208;\">");
        sb.append("<!ENTITY Ntilde \"&#209;\">");
        sb.append("<!ENTITY Ograve \"&#210;\">");
        sb.append("<!ENTITY Oacute \"&#211;\">");
        sb.append("<!ENTITY Ocirc  \"&#212;\">");
        sb.append("<!ENTITY Otilde \"&#213;\">");
        sb.append("<!ENTITY Ouml   \"&#214;\">");
        sb.append("<!ENTITY times  \"&#215;\">");
        sb.append("<!ENTITY Oslash \"&#216;\">");
        sb.append("<!ENTITY Ugrave \"&#217;\">");
        sb.append("<!ENTITY Uacute \"&#218;\">");
        sb.append("<!ENTITY Ucirc  \"&#219;\">");
        sb.append("<!ENTITY Uuml   \"&#220;\">");
        sb.append("<!ENTITY Yacute \"&#221;\">");
        sb.append("<!ENTITY THORN  \"&#222;\">");
        sb.append("<!ENTITY szlig  \"&#223;\">");
        sb.append("<!ENTITY agrave \"&#224;\">");
        sb.append("<!ENTITY aacute \"&#225;\">");
        sb.append("<!ENTITY acirc  \"&#226;\">");
        sb.append("<!ENTITY atilde \"&#227;\">");
        sb.append("<!ENTITY auml   \"&#228;\">");
        sb.append("<!ENTITY aring  \"&#229;\">");
        sb.append("<!ENTITY aelig  \"&#230;\">");
        sb.append("<!ENTITY ccedil \"&#231;\">");
        sb.append("<!ENTITY egrave \"&#232;\">");
        sb.append("<!ENTITY eacute \"&#233;\">");
        sb.append("<!ENTITY ecirc  \"&#234;\">");
        sb.append("<!ENTITY euml   \"&#235;\">");
        sb.append("<!ENTITY igrave \"&#236;\">");
        sb.append("<!ENTITY iacute \"&#237;\">");
        sb.append("<!ENTITY icirc  \"&#238;\">");
        sb.append("<!ENTITY iuml   \"&#239;\">");
        sb.append("<!ENTITY eth    \"&#240;\">");
        sb.append("<!ENTITY ntilde \"&#241;\">");
        sb.append("<!ENTITY ograve \"&#242;\">");
        sb.append("<!ENTITY oacute \"&#243;\">");
        sb.append("<!ENTITY ocirc  \"&#244;\">");
        sb.append("<!ENTITY otilde \"&#245;\">");
        sb.append("<!ENTITY ouml   \"&#246;\">");
        sb.append("<!ENTITY divide \"&#247;\">");
        sb.append("<!ENTITY oslash \"&#248;\">");
        sb.append("<!ENTITY ugrave \"&#249;\">");
        sb.append("<!ENTITY uacute \"&#250;\">");
        sb.append("<!ENTITY ucirc  \"&#251;\">");
        sb.append("<!ENTITY uuml   \"&#252;\">");
        sb.append("<!ENTITY yacute \"&#253;\">");
        sb.append("<!ENTITY thorn  \"&#254;\">");
        sb.append("<!ENTITY yuml   \"&#255;\">");

        //symbols, mathematical symbols, and Greek letters
        sb.append("<!ENTITY fnof     \"&#402;\">");
        sb.append("<!ENTITY Alpha    \"&#913;\">");
        sb.append("<!ENTITY Beta     \"&#914;\">");
        sb.append("<!ENTITY Gamma    \"&#915;\">");
        sb.append("<!ENTITY Delta    \"&#916;\">");
        sb.append("<!ENTITY Epsilon  \"&#917;\">");
        sb.append("<!ENTITY Zeta     \"&#918;\">");
        sb.append("<!ENTITY Eta      \"&#919;\">");
        sb.append("<!ENTITY Theta    \"&#920;\">");
        sb.append("<!ENTITY Iota     \"&#921;\">");
        sb.append("<!ENTITY Kappa    \"&#922;\">");
        sb.append("<!ENTITY Lambda   \"&#923;\">");
        sb.append("<!ENTITY Mu       \"&#924;\">");
        sb.append("<!ENTITY Nu       \"&#925;\">");
        sb.append("<!ENTITY Xi       \"&#926;\">");
        sb.append("<!ENTITY Omicron  \"&#927;\">");
        sb.append("<!ENTITY Pi       \"&#928;\">");
        sb.append("<!ENTITY Rho      \"&#929;\">");
        sb.append("<!ENTITY Sigma    \"&#931;\">");
        sb.append("<!ENTITY Tau      \"&#932;\">");
        sb.append("<!ENTITY Upsilon  \"&#933;\">");
        sb.append("<!ENTITY Phi      \"&#934;\">");
        sb.append("<!ENTITY Chi      \"&#935;\">");
        sb.append("<!ENTITY Psi      \"&#936;\">");
        sb.append("<!ENTITY Omega    \"&#937;\">");
        sb.append("<!ENTITY alpha    \"&#945;\">");
        sb.append("<!ENTITY beta     \"&#946;\">");
        sb.append("<!ENTITY gamma    \"&#947;\">");
        sb.append("<!ENTITY delta    \"&#948;\">");
        sb.append("<!ENTITY epsilon  \"&#949;\">");
        sb.append("<!ENTITY zeta     \"&#950;\">");
        sb.append("<!ENTITY eta      \"&#951;\">");
        sb.append("<!ENTITY theta    \"&#952;\">");
        sb.append("<!ENTITY iota     \"&#953;\">");
        sb.append("<!ENTITY kappa    \"&#954;\">");
        sb.append("<!ENTITY lambda   \"&#955;\">");
        sb.append("<!ENTITY mu       \"&#956;\">");
        sb.append("<!ENTITY nu       \"&#957;\">");
        sb.append("<!ENTITY xi       \"&#958;\">");
        sb.append("<!ENTITY omicron  \"&#959;\">");
        sb.append("<!ENTITY pi       \"&#960;\">");
        sb.append("<!ENTITY rho      \"&#961;\">");
        sb.append("<!ENTITY sigmaf   \"&#962;\">");
        sb.append("<!ENTITY sigma    \"&#963;\">");
        sb.append("<!ENTITY tau      \"&#964;\">");
        sb.append("<!ENTITY upsilon  \"&#965;\">");
        sb.append("<!ENTITY phi      \"&#966;\">");
        sb.append("<!ENTITY chi      \"&#967;\">");
        sb.append("<!ENTITY psi      \"&#968;\">");
        sb.append("<!ENTITY omega    \"&#969;\">");
        sb.append("<!ENTITY thetasym \"&#977;\">");
        sb.append("<!ENTITY upsih    \"&#978;\">");
        sb.append("<!ENTITY piv      \"&#982;\">");

        sb.append("<!ENTITY bull     \"&#8226;\">");
        sb.append("<!ENTITY hellip   \"&#8230;\">");
        sb.append("<!ENTITY prime    \"&#8242;\">");
        sb.append("<!ENTITY Prime    \"&#8243;\">");
        sb.append("<!ENTITY oline    \"&#8254;\">");
        sb.append("<!ENTITY frasl    \"&#8260;\">");
        sb.append("<!ENTITY weierp   \"&#8472;\">");
        sb.append("<!ENTITY image    \"&#8465;\">");
        sb.append("<!ENTITY real     \"&#8476;\">");
        sb.append("<!ENTITY trade    \"&#8482;\">");
        sb.append("<!ENTITY alefsym  \"&#8501;\">");
        sb.append("<!ENTITY larr     \"&#8592;\">");
        sb.append("<!ENTITY uarr     \"&#8593;\">");
        sb.append("<!ENTITY rarr     \"&#8594;\">");
        sb.append("<!ENTITY darr     \"&#8595;\">");
        sb.append("<!ENTITY harr     \"&#8596;\">");
        sb.append("<!ENTITY crarr    \"&#8629;\">");
        sb.append("<!ENTITY lArr     \"&#8656;\">");
        sb.append("<!ENTITY uArr     \"&#8657;\">");
        sb.append("<!ENTITY rArr     \"&#8658;\">");
        sb.append("<!ENTITY dArr     \"&#8659;\">");
        sb.append("<!ENTITY hArr     \"&#8660;\">");
        sb.append("<!ENTITY forall   \"&#8704;\">");
        sb.append("<!ENTITY part     \"&#8706;\">");
        sb.append("<!ENTITY exist    \"&#8707;\">");
        sb.append("<!ENTITY empty    \"&#8709;\">");
        sb.append("<!ENTITY nabla    \"&#8711;\">");
        sb.append("<!ENTITY isin     \"&#8712;\">");
        sb.append("<!ENTITY notin    \"&#8713;\">");
        sb.append("<!ENTITY ni       \"&#8715;\">");
        sb.append("<!ENTITY prod     \"&#8719;\">");
        sb.append("<!ENTITY sum      \"&#8721;\">");
        sb.append("<!ENTITY minus    \"&#8722;\">");
        sb.append("<!ENTITY lowast   \"&#8727;\">");
        sb.append("<!ENTITY radic    \"&#8730;\">");
        sb.append("<!ENTITY prop     \"&#8733;\">");
        sb.append("<!ENTITY infin    \"&#8734;\">");
        sb.append("<!ENTITY ang      \"&#8736;\">");
        sb.append("<!ENTITY and      \"&#8743;\">");
        sb.append("<!ENTITY or       \"&#8744;\">");
        sb.append("<!ENTITY cap      \"&#8745;\">");
        sb.append("<!ENTITY cup      \"&#8746;\">");
        sb.append("<!ENTITY int      \"&#8747;\">");
        sb.append("<!ENTITY there4   \"&#8756;\">");
        sb.append("<!ENTITY sim      \"&#8764;\">");
        sb.append("<!ENTITY cong     \"&#8773;\">");
        sb.append("<!ENTITY asymp    \"&#8776;\">");
        sb.append("<!ENTITY ne       \"&#8800;\">");
        sb.append("<!ENTITY equiv    \"&#8801;\">");
        sb.append("<!ENTITY le       \"&#8804;\">");
        sb.append("<!ENTITY ge       \"&#8805;\">");
        sb.append("<!ENTITY sub      \"&#8834;\">");
        sb.append("<!ENTITY sup      \"&#8835;\">");
        sb.append("<!ENTITY nsub     \"&#8836;\">");
        sb.append("<!ENTITY sube     \"&#8838;\">");
        sb.append("<!ENTITY supe     \"&#8839;\">");
        sb.append("<!ENTITY oplus    \"&#8853;\">");
        sb.append("<!ENTITY otimes   \"&#8855;\">");
        sb.append("<!ENTITY perp     \"&#8869;\">");
        sb.append("<!ENTITY sdot     \"&#8901;\">");
        sb.append("<!ENTITY lceil    \"&#8968;\">");
        sb.append("<!ENTITY rceil    \"&#8969;\">");
        sb.append("<!ENTITY lfloor   \"&#8970;\">");
        sb.append("<!ENTITY rfloor   \"&#8971;\">");
        sb.append("<!ENTITY lang     \"&#9001;\">");
        sb.append("<!ENTITY rang     \"&#9002;\">");
        sb.append("<!ENTITY loz      \"&#9674;\">");
        sb.append("<!ENTITY spades   \"&#9824;\">");
        sb.append("<!ENTITY clubs    \"&#9827;\">");
        sb.append("<!ENTITY hearts   \"&#9829;\">");
        sb.append("<!ENTITY diams    \"&#9830;\">");

        //markup-significant and internationalization characters
        sb.append("<!ENTITY quot    \"&#34;\"  >");
        sb.append("<!ENTITY amp     \"&#38;\"  >");
        sb.append("<!ENTITY lt      \"&#60;\"  >");
        sb.append("<!ENTITY gt      \"&#62;\"  >");
        sb.append("<!ENTITY OElig   \"&#338;\" >");
        sb.append("<!ENTITY oelig   \"&#339;\" >");
        sb.append("<!ENTITY Scaron  \"&#352;\" >");
        sb.append("<!ENTITY scaron  \"&#353;\" >");
        sb.append("<!ENTITY Yuml    \"&#376;\" >");
        sb.append("<!ENTITY circ    \"&#710;\" >");
        sb.append("<!ENTITY tilde   \"&#732;\" >");
        sb.append("<!ENTITY ensp    \"&#8194;\">");
        sb.append("<!ENTITY emsp    \"&#8195;\">");
        sb.append("<!ENTITY thinsp  \"&#8201;\">");
        sb.append("<!ENTITY zwnj    \"&#8204;\">");
        sb.append("<!ENTITY zwj     \"&#8205;\">");
        sb.append("<!ENTITY lrm     \"&#8206;\">");
        sb.append("<!ENTITY rlm     \"&#8207;\">");
        sb.append("<!ENTITY ndash   \"&#8211;\">");
        sb.append("<!ENTITY mdash   \"&#8212;\">");
        sb.append("<!ENTITY lsquo   \"&#8216;\">");
        sb.append("<!ENTITY rsquo   \"&#8217;\">");
        sb.append("<!ENTITY sbquo   \"&#8218;\">");
        sb.append("<!ENTITY ldquo   \"&#8220;\">");
        sb.append("<!ENTITY rdquo   \"&#8221;\">");
        sb.append("<!ENTITY bdquo   \"&#8222;\">");
        sb.append("<!ENTITY dagger  \"&#8224;\">");
        sb.append("<!ENTITY Dagger  \"&#8225;\">");
        sb.append("<!ENTITY permil  \"&#8240;\">");
        sb.append("<!ENTITY lsaquo  \"&#8249;\">");
        sb.append("<!ENTITY rsaquo  \"&#8250;\">");
        sb.append("<!ENTITY euro    \"&#8364;\">");

        return sb;
    }

    /**
     * <br>[機  能] 登録済みの本文ファイルをTempディレクトリにコピーします
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param postSid 投稿SID
     * @param con コネクション
     * @param appRoot アプリケーションルートパス
     * @param tempDir テンポラリディレクトリパス
     * @throws SQLException SQL実行時例外
     * @throws TempFileException 一時ファイル例外
     * @throws IOToolsException IOツール例外
     * @throws IOException IO実行時例外
     */
    public void bbsCopyBodyFileToTemp(
            RequestModel reqMdl, int postSid,
            Connection con, String appRoot, String tempDir)
                    throws SQLException, TempFileException, IOException, IOToolsException {

        //本文ファイルの取得
        BbsBodyBinDao bbbDao = new BbsBodyBinDao(con);
        List<BbsBodyBinModel> bbbModelList =  bbbDao.select(postSid);

        //「bodyFile」の「(ファイルID)」

        //Tempディレクトリへのコピー
        if (bbbModelList != null && bbbModelList.size() > 0) {
            //添付ファイルをテンポラリディレクトリへ移動する

            CommonBiz cmnBiz = new CommonBiz();
            String dateStr = (new UDate()).getDateString(); //現在日付の文字列(YYYYMMDD)
            int fileNum = 0;
            String tempDirBodyFile = null;

            for (BbsBodyBinModel bbbMdl : bbbModelList) {
                int fileSid = bbbMdl.getBbbFileSid();
                Long binSid = bbbMdl.getBinSid();

                tempDirBodyFile = tempDir + GSConstCommon.EDITOR_BODY_FILE
                        + File.separator + fileSid + File.separator;

                //取得したバイナリSIDからバイナリ情報を取得
                CmnBinfModel binData = cmnBiz.getBinInfoToDomain(con, binSid, reqMdl.getDomain());

                //Tempディレクトリの該当箇所に配置
                cmnBiz.saveTempFile(dateStr, binData, appRoot, tempDirBodyFile, fileNum);
            }
        }

    }

    /**
     * <br>[機  能] 投稿の本文添付ファイルサイズを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param postSid 投稿SID
     * @param bodyValue 投稿本文
     * @param tempDir テンポラリディレクトリ
     * @param soukouFlg 草稿モード
     * @return 添付ファイルサイズ
     * @throws Exception 実行時例外
     */
    public long getBodyFileSize(int postSid, String bodyValue, String tempDir, boolean soukouFlg)
            throws Exception {
        long fileSize = 0;

        //本文のエスケープを行う
        String escapeBodyValue = NullDefault.getString(bodyValue, "");
        escapeBodyValue = StringUtilHtml.replaceString(escapeBodyValue, "&", "&amp;");
        escapeBodyValue = StringUtilHtml.replaceString(escapeBodyValue, "&amp;amp;", "&amp;");
        escapeBodyValue = StringUtilHtml.replaceString(escapeBodyValue, "&amp;lt;", "&lt;");
        escapeBodyValue = StringUtilHtml.replaceString(escapeBodyValue, "&amp;gt;", "&gt;");
        escapeBodyValue = StringUtilHtml.replaceString(escapeBodyValue, "&amp;quot;", "&quot;");

        //本文内ファイルのIDを取得し、表示用のタグに変換。
        List <String> bodyFileList = new ArrayList<String>();
        getBbsBodyFileList(postSid, escapeBodyValue, bodyFileList, soukouFlg);

        //本文から投稿本文添付情報を取得し、バイナリー情報に登録
        String bodyFileTempDir = null;
        for (String bodyFileId : bodyFileList) {
            bodyFileTempDir =
                    tempDir + GSConstCommon.EDITOR_BODY_FILE
                    + File.separator + bodyFileId + File.separator;

            CommonBiz cmnBiz = new CommonBiz();
            List<LabelValueBean> fileList = cmnBiz.getTempFileLabelList(bodyFileTempDir);
            for (LabelValueBean fileData : fileList) {
                String saveFilePath = IOTools.setEndPathChar(bodyFileTempDir);
                saveFilePath += fileData.getValue().concat(GSConstCommon.ENDSTR_SAVEFILE);
                fileSize += new File(saveFilePath).length();
            }
        }

        return fileSize;
    }

    /**
     * <br>[機  能] 本文中に記述されているユーザのアップロードファイルを取得します
     * <br>[解  説]
     * <br>[備  考]
     * @param postSid 投稿(草稿)SID
     * @param bodyValue 本文
     * @param bodyFileList 本文内ファイルのID
     * @param soukouFlg 草稿モード
     * @return 本文中で指定されているユーザのアップロードファイルのリスト
     * @throws Exception 実行時例外
     */
    public String getBbsBodyFileList(
            int postSid, String bodyValue, List <String> bodyFileList, boolean soukouFlg)
                    throws Exception {
        String ret = "";

        if (bodyValue == null
                || bodyValue.length() < 1) {
            return ret;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html[");

        //Entityを設定
        __setEntity(sb);

        sb.append("]>");

        String bodyHeader = "<root>";
        String bodyFooter = "</root>";
        
        if (!StringUtil.isNullZeroString(bodyValue)) {
            StringBuffer bodySb = new StringBuffer();
            for (int index = 0; index < bodyValue.length(); index++) {
                char c = bodyValue.charAt(index);
                if ((c >= 0x00 && c <= 0x08)
                        || (c >= 0x0B && c <= 0x0C)
                        || (c >= 0x0E && c <= 0x1F)) {
                    
                    bodySb.append("");
                } else {
                    bodySb.append(c);
                }
            }
            bodyValue = bodySb.toString();
        }

        sb.append(bodyHeader);
        sb.append(bodyValue);
        sb.append(bodyFooter);
        bodyValue = sb.toString();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        ByteArrayInputStream baiStream = null;
        StringWriter strWriter = null;

        try {
            baiStream = new ByteArrayInputStream(bodyValue.getBytes("UTF-8"));
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(baiStream);

            //<img>タグのsrcを取得
            NodeList imgNodeList =  doc.getElementsByTagName("img");

            if (imgNodeList != null && imgNodeList.getLength() > 0) {

                String startKey = "bbs070.do?CMD=getBodyFile";
                String idAttrName = "bbs070TempSaveId=";

                for (int i = 0; i < imgNodeList.getLength(); ++i) {
                    Node imgNode = imgNodeList.item(i);
                    if (imgNode.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    NamedNodeMap imgNodeAttrMap = imgNode.getAttributes();
                    Node srcAttr = imgNodeAttrMap.getNamedItem("src");
                    if (srcAttr == null) {
                        continue;
                    }
                    
                    String srcStr = srcAttr.getNodeValue();
                    if (srcStr == null || srcStr.length() < 1) {
                        continue;
                    }

                    int idxOfIdStart = srcStr.indexOf(idAttrName);
                    int idxOfIdEnd = srcStr.indexOf("&", idxOfIdStart);
                    if (!srcStr.startsWith(startKey) || idxOfIdStart >= idxOfIdEnd) {
                        continue;
                    }

                    String idStr = srcStr.substring(
                            idxOfIdStart + idAttrName.length(), idxOfIdEnd);
                    if (!StringUtil.isNullZeroString(idStr)) {
                        bodyFileList.add(idStr);
                        //bodyの該当部を変換
                        if (soukouFlg) {
                            srcAttr.setNodeValue("../bulletin/bbs220.do?"
                                    + "CMD=getBodyFile"
                                    + "&soukouSid=" + String.valueOf(postSid)
                                    + "&bbs220BodyFileId=" + idStr);
                        } else {
                            srcAttr.setNodeValue("../bulletin/bbs060.do?"
                                    + "CMD=getBodyFile"
                                    + "&bbs060postSid=" + String.valueOf(postSid)
                                    + "&bodyFileId=" + idStr);
                        }
                    }
                }
            }

            //解析・変更した文を本文に戻して登録
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            strWriter = new StringWriter();
            DOMSource source = new DOMSource(doc);
            transformer.transform(source, new StreamResult(strWriter));
            String strDoc = strWriter.toString();

            int valueStart = strDoc.indexOf(bodyHeader);
            int valueEnd = strDoc.lastIndexOf(bodyFooter);
            strDoc = strDoc.substring(valueStart + bodyHeader.length(), valueEnd);

            ret = strDoc;

        } catch (Exception e) {
            log__.error("HTMLの読み取りに失敗しました。", e);
            throw e;
        } finally {
            if (strWriter != null) {
                strWriter.close();
            }
            if (baiStream != null) {
                baiStream.close();
            }
        }

        return ret;
    }




    /**
     * <br>[機  能] 草稿情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param con Connection
     * @param userSid セッションユーザSID
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param reqMdl リクエストモデル
     * @param mode 登録編集モード
     * @return 草稿情報
     * @throws Exception 実行例外
     */
    public BbsSoukouModel addEditSoukouData(
            Bbs070ParamModel paramMdl,
            MlCountMtController cntCon,
            Connection con,
            int userSid,
            String appRootPath,
            String tempDir,
            RequestModel reqMdl,
            int mode)
                    throws Exception {
        log__.debug("START");
        int bfiSid = paramMdl.getBbs010forumSid();
        UDate now = new UDate();
        //草稿SID採番
        int bskSid = -1;
        if (mode == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            bskSid = paramMdl.getSoukouSid();
        } else {
            bskSid = (int) cntCon.getSaibanNumber(GSConstBulletin.SBNSID_BULLETIN,
                    GSConstBulletin.SBNSID_SUB_BULLETIN_SOUKOU,
                    userSid);
        }
        //草稿情報の登録
        BbsSoukouModel bbsSoukouMdl = new BbsSoukouModel();
        bbsSoukouMdl.setBskSid(bskSid);
        bbsSoukouMdl.setBfiSid(bfiSid);
        bbsSoukouMdl.setBtiSid(-1);
        bbsSoukouMdl.setBskSoukouType(GSConstBulletin.SOUKOU_TYPE_THREAD);
        bbsSoukouMdl.setBskTitle(paramMdl.getBbs070title());
        bbsSoukouMdl.setBskImportance(paramMdl.getBbs070Importance());
        //本文のタイプ
        int valueType = paramMdl.getBbs070valueType();
        bbsSoukouMdl.setBskType(valueType);

        //本文
        String postValue = null;

        CommonBiz cmnBiz = new CommonBiz();

        if (valueType == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            //本文がプレーンテキストの場合
            postValue = paramMdl.getBbs070value();

        } else {
            //本文がHTMLテキストの場合
            postValue = paramMdl.getBbs070valueHtml();
            //本文内ファイルのIDを取得し、表示用のタグに変換。
            List <String> bodyFileList = new ArrayList<String>();
            Bbs070Biz bbs070Biz = new Bbs070Biz();
            postValue = bbs070Biz.getBbsBodyFileList(
                    bskSid, postValue, bodyFileList, true);

            //本文から投稿本文添付情報を取得し、バイナリー情報に登録
            String bodyFileTempDir = null;
            for (String bodyFileId : bodyFileList) {
                long binSid = -1;
                int fileSid = NullDefault.getInt(bodyFileId, -1);
                bodyFileTempDir =
                        tempDir + GSConstCommon.EDITOR_BODY_FILE
                        + File.separator + bodyFileId + File.separator;
                List <String> bodyBinSid = cmnBiz.insertBinInfo(
                        con, bodyFileTempDir, appRootPath, cntCon, userSid, now);
                if (bodyBinSid != null && bodyBinSid.size() > 0) {
                    binSid = NullDefault.getLong(bodyBinSid.get(0), -1);
                    if (fileSid > 0 && binSid > 0) {
                        BbsSoukouBodyBinDao bbDao = new BbsSoukouBodyBinDao(con);
                        BbsSoukouBodyBinModel inMdl = new BbsSoukouBodyBinModel();
                        inMdl.setBskSid(bskSid);
                        inMdl.setBinSid(binSid);
                        inMdl.setBsbFileSid(fileSid);
                        bbDao.insert(inMdl);
                    }
                }
            }
        }

        if (valueType == GSConstBulletin.CONTENT_TYPE_TEXT_PLAIN) {
            //本文がプレーンテキストの場合
            bbsSoukouMdl.setBskValue(postValue);
            bbsSoukouMdl.setBskValuePlain("");

        } else {
            //本文がHTMLテキストの場合
            bbsSoukouMdl.setBskValue(postValue);
            //BRタグを\r\nに変換後、タグを除去し、プレーンテキストとして登録
            String postValuePlain = StringUtilHtml.replaceString(postValue, "<br/>", "<BR>");
            postValuePlain = StringUtilHtml.transBRtoCRLF(postValuePlain);
            postValuePlain = StringUtilHtml.deleteHtmlTagAndScriptStyleBlock(postValuePlain);
            postValuePlain = StringEscapeUtils.unescapeHtml(postValuePlain);
            postValuePlain = postValuePlain.replaceAll("\uu00A0", "\uu0020");
            bbsSoukouMdl.setBskValuePlain(postValuePlain);
        }

        bbsSoukouMdl.setBskLimit(paramMdl.getBbs070limit());

        UDate limitFrDate = now.cloneUDate();
        if (paramMdl.getBbs070limit() == GSConstBulletin.THREAD_LIMIT_YES) {
            limitFrDate.setHour(paramMdl.getBbs070limitFrHour());
            limitFrDate.setMinute(paramMdl.getBbs070limitFrMinute());
            limitFrDate.setSecond(0);
            limitFrDate.setDate(paramMdl.getBbs070limitFrYear(),
                    paramMdl.getBbs070limitFrMonth(),
                    paramMdl.getBbs070limitFrDay());
            bbsSoukouMdl.setBskLimitFrDate(limitFrDate);

            UDate limitToDate = now.cloneUDate();
            limitToDate.setHour(paramMdl.getBbs070limitHour());
            limitToDate.setMinute(paramMdl.getBbs070limitMinute());
            limitToDate.setSecond(0);
            limitToDate.setDate(paramMdl.getBbs070limitYear(),
                            paramMdl.getBbs070limitMonth(),
                            paramMdl.getBbs070limitDay());
            bbsSoukouMdl.setBskLimitDate(limitToDate);
        }
        int contributor = 0;
        if (paramMdl.getBbs070contributor() > 0) {
            contributor = paramMdl.getBbs070contributor();
        }
        bbsSoukouMdl.setBskAgid(contributor);

        bbsSoukouMdl.setBskAuid(userSid);
        bbsSoukouMdl.setBskAdate(now);
        bbsSoukouMdl.setBskEuid(userSid);
        bbsSoukouMdl.setBskEdate(now);


        BbsSoukouDao bbsSoukouInfDao = new BbsSoukouDao(con);
        if (mode == GSConstBulletin.BBSCMDMODE_SOUKOU) {
            bbsSoukouInfDao.update(bbsSoukouMdl);
        } else {
            bbsSoukouInfDao.insert(bbsSoukouMdl);

        }
        //投稿添付情報の登録
        //添付ファイルをバイナリー情報に登録
        List < String > binSidList = cmnBiz.insertBinInfo(con, tempDir, appRootPath,
                                                    cntCon, userSid, now);
        if (binSidList != null && !binSidList.isEmpty()) {
            for (String strBinSid: binSidList) {
                long binSid = NullDefault.getLong(strBinSid, -1);
                BbsSoukouBinDao bbsBinDao = new BbsSoukouBinDao(con);
                BbsSoukouBinModel bbsBinMdl = new BbsSoukouBinModel();
                bbsBinMdl.setBskSid(bskSid);
                bbsBinMdl.setBinSid(binSid);
                if (binSid > 0) {
                    bbsBinDao.insert(bbsBinMdl);
                }
            }
        }
        log__.debug("End");
        // ログ出力用処理
        if (bbsSoukouMdl.getBskType() == GSConstBulletin.CONTENT_TYPE_TEXT_HTML
                && StringUtil.isNullZeroStringSpace(bbsSoukouMdl.getBskValuePlain())
                && !StringUtil.isNullZeroStringSpace(bbsSoukouMdl.getBskValue())) {
            BbsBiz bbsBiz = new BbsBiz(con);
            String fileId = getBbsBodytoFileId(bbsSoukouMdl.getBskValue());
            int fileSid = NullDefault.getInt(fileId, -1);
            if (fileSid > 0) {
                bbsSoukouMdl.setBskValuePlain(bbsBiz.getBodyImgFilename(bskSid, fileSid));
            }
        }
        return bbsSoukouMdl;
    }

    /**
     * <br>[機  能] 本文データからファイル番号を取得する
     * <br>[解  説]
     * <br>[備  考] 表示用の本文内添付ファイルタグをTempディレクトリ内ファイルを指定するタグに置き換えます
     * @param bodyValue 本文
     * @return 本文内添付ファイルタグをTempディレクトリ内ファイルを指定するタグに置き換えた本文
     * @throws Exception 実行時例外
     */
    public String getBbsBodytoFileId(String bodyValue)
                    throws Exception {
        String ret = "";

        if (bodyValue == null
                || bodyValue.length() < 1) {
            return ret;
        }


        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html[");

        //Entityを設定
        __setEntity(sb);

        sb.append("]>");

        String bodyHeader = "<root>";
        String bodyFooter = "</root>";

        sb.append(bodyHeader);
        sb.append(bodyValue);
        sb.append(bodyFooter);
        bodyValue = sb.toString();

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        ByteArrayInputStream baiStream = null;
        StringWriter strWriter = null;

        try {
            baiStream = new ByteArrayInputStream(bodyValue.getBytes("UTF-8"));
            dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(baiStream);

            //<img>タグのsrcを取得
            NodeList imgNodeList =  doc.getElementsByTagName("img");

            if (imgNodeList != null && imgNodeList.getLength() > 0) {
                String startKey =  "../bulletin/bbs220.do?CMD=getBodyFile";
                String idAttrName = "&bbs220BodyFileId=";

                for (int i = 0; i < imgNodeList.getLength(); ++i) {
                    Node imgNode = imgNodeList.item(i);
                    if (imgNode.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    NamedNodeMap imgNodeAttrMap = imgNode.getAttributes();
                    Node srcAttr = imgNodeAttrMap.getNamedItem("src");
                    if (srcAttr == null) {
                        continue;
                    }
                    
                    String srcStr = srcAttr.getNodeValue();
                    if (srcStr == null || srcStr.length() < 1) {
                        continue;
                    }

                    int idxOfIdStart = srcStr.indexOf(idAttrName);
                    if (!srcStr.startsWith(startKey) || idxOfIdStart == 0) {
                        continue;
                    }

                    String idStr = srcStr.substring(idxOfIdStart + idAttrName.length());
                    if (!StringUtil.isNullZeroString(idStr)) {
                        ret = idStr;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log__.error("HTMLの読み取りに失敗しました。", e);
            throw e;
        } finally {
            if (strWriter != null) {
                strWriter.close();
            }
            if (baiStream != null) {
                baiStream.close();
            }
        }
        return ret;
    }


    /**
     * <br>[機  能]ダイアログの表示判定を行う
     * <br>[解  説]判定;true⇒表示,false⇒非表示
     * <br>[備  考]
     * @param paramMdl パラムモデル
     * @param con コネクション
     * @return result 判定
     * @throws SQLException SQL例外発生
     */
    public boolean dialogDspCheck(Bbs070ParamModel paramMdl, Connection con) throws SQLException {

        boolean result = false;

        BbsThreInfModel mdl = new BbsThreInfModel();
        BbsThreInfDao dao = new BbsThreInfDao(con);
        UDate now = new UDate();
        UDate date = new UDate();

        mdl = dao.select(paramMdl.getThreadSid());

        long genzaiDate = Long.parseLong(now.getTimeStamp());

        //パラメータの掲示開始日時のセット
        date.setTimeStamp(paramMdl.getBbs070limitFrYear(),
                paramMdl.getBbs070limitFrMonth(),
                paramMdl.getBbs070limitFrDay(),
                paramMdl.getBbs070limitFrHour(),
                paramMdl.getBbs070limitFrMinute(),
                0);

        //掲示期間が無制限の時はダイアログを表示しない
        if (paramMdl.getBbs070limit() == GSConstBulletin.THREAD_LIMIT_NO) {
            return false;
        }

        //パラメータの掲示開始日時
        long paramDate = Long.parseLong(date.getTimeStamp());

        //期間設定が無制限⇒期間設定有（未来）になったとき用
        if (mdl.getBtiLimitFrDate() == null && paramDate > genzaiDate) {
            return true;
        }

        //期間設定が無制限⇒期間設定有（過去）になったとき用
        if (mdl.getBtiLimitFrDate() == null && paramDate <= genzaiDate) {
            return false;
        }

        //DB[スレッド情報].掲示開始日時
        long kaisiDate = Long.parseLong(mdl.getBtiLimitFrDate().getTimeStamp());

        //判定:現在か未来かを比較
        if (kaisiDate <= genzaiDate && paramDate > genzaiDate) {
            result = true;
        }
        return result;
    }
}
