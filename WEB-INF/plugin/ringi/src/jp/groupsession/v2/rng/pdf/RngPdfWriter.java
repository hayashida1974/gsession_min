package jp.groupsession.v2.rng.pdf;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.StringUtilHtml;
import jp.groupsession.v2.cmn.dao.GroupDao;
import jp.groupsession.v2.cmn.dao.base.CmnCmbsortConfDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.formmodel.Calc;
import jp.groupsession.v2.cmn.formmodel.NumberBox;
import jp.groupsession.v2.cmn.formmodel.Sum;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnCmbsortConfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.dao.RngFormdataDao;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RngFormdataModel;
import jp.groupsession.v2.rng.rng030.Rng030KeiroParam;
import jp.groupsession.v2.rng.rng030.Rng030SingiParam;
import jp.groupsession.v2.rng.rng130.Rng130Biz;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 稟議情報一覧のCSV出力を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngPdfWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngPdfWriter.class);

    /** コネクション */
    private Connection con__ = null;

    /** リクエスト */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set HttpServletRequest
     * @param con コネクション
     * @param reqMdl リクエストモデル
     */
    public RngPdfWriter(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] スケジュール単票PDF出力用データモデルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSid 稟議SID
     * @param procMode 処理モード
     * @param apprMode 申請モード
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return スケジュール単票PDFモデル
     */
    public RngTanPdfModel getRngPdfDataList(
            int rngSid,
            int procMode,
            int apprMode,
            int userSid) throws SQLException {
        ArrayList<Integer> rngSidList = new ArrayList<Integer>();
        rngSidList.add(rngSid);
        ArrayList<RngTanPdfModel> rngTanPdfModelList =
                getRngPdfDataList(rngSidList, procMode, apprMode, userSid);

        return rngTanPdfModelList.get(0);
    }

    /**
     * <br>[機  能] スケジュール単票PDF出力用データモデルを取得する。
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSidList 稟議SIDリスト
     * @param procMode 処理モード
     * @param apprMode 申請モード
     * @param userSid ユーザSID
     * @throws SQLException SQL実行例外
     * @return スケジュール単票PDFモデル
     */
    public ArrayList<RngTanPdfModel> getRngPdfDataList(
            ArrayList<Integer> rngSidList,
            int procMode,
            int apprMode,
            int userSid) throws SQLException {

        ArrayList<RngTanPdfModel> ret = new ArrayList<RngTanPdfModel>();
        RngTanPdfModel pdfModel = new RngTanPdfModel();
        GsMessage gsMsg = new GsMessage(reqMdl__);

        Rng130Biz rngBiz = new Rng130Biz(con__, reqMdl__);
        ArrayList<Integer> rksSidList = rngBiz.viewKeiroSid(rngSidList, userSid, procMode);

        //稟議情報を取得
        RingiDao ringiDao = new RingiDao(con__);
        ArrayList<RingiDataModel> modelList =
                ringiDao.getRingiData(rngSidList, userSid, rksSidList);
        //稟議情報をソート
        ArrayList<RingiDataModel> sortList =
                new ArrayList<RingiDataModel>();
        for (int rngSid : rngSidList) {
            for (RingiDataModel model : modelList) {
                if (rngSid == model.getRngSid()) {
                    sortList.add(model);
                }
            }
        }
        modelList = sortList;

        ArrayList<Integer> tplKeyList = new ArrayList<Integer>();
        ArrayList<Integer> tplVerList = new ArrayList<Integer>();
        for (RingiDataModel model : modelList) {
            tplKeyList.add(model.getRtpSid());
            tplVerList.add(model.getRtpVer());
        }

        //テンプレート情報取得
        RngTanPdfDao    rngTanPdfDao  = new RngTanPdfDao(con__);
        HashMap<String, ArrayList<RngTanPdfFormModel>> tmpFormMap =
                rngTanPdfDao.selects(reqMdl__, tplKeyList, tplVerList);

        //フォーム情報取得
        RngFormdataDao formdataDao = new RngFormdataDao(con__);
        HashMap<Integer, ArrayList<RngFormdataModel>> formMap =
                                                        formdataDao.selectPdf(rngSidList);

        //表要素内ボディ部分の行数取得
        HashMap<Integer, HashMap<Integer, Integer>> bodyMap =
                rngTanPdfDao.getBodyMap(rngSidList);

        // 出力に使用するユーザ、グループ、添付ファイル情報取得
        HashMap<String, String> userNameMap = new HashMap<String, String>();
        HashMap<String, String> groupNameMap = new HashMap<String, String>();
        HashMap<String, String> fileNameMap = new HashMap<String, String>();
        ArrayList<Integer> tmpSidList = new ArrayList<Integer>();
        for (RingiDataModel model : modelList) {
            int apprMode__ = -1;
            //再申請フラグ
            if (apprMode != RngConst.RNG_APPRMODE_DISCUSSING
                    && apprMode != RngConst.RNG_APPRMODE_COMPLETE) {
                if (model.getRngApplicate() == userSid) {
                    if (model.getRssStatus() == RngConst.RNG_RNCSTATUS_CONFIRM
                            && model.getRncType() == RngConst.RNG_RNCTYPE_APPL) {
                        apprMode__ = RngConst.RNG_APPRMODE_APPL;
                    }
                }
            }

            //処理モード=再申請以外の場合は添付情報を設定
            if (apprMode__ != RngConst.RNG_APPRMODE_APPL) {
                tmpSidList.add(model.getRngSid());
            }

            String tmpFormKey = rngTanPdfDao.getRngTemplateKey(model.getRtpSid(),
                    model.getRtpVer());

            //テンプレートが存在しない稟議は処理をしない
            if (!tmpFormMap.containsKey(tmpFormKey)) {
                continue;
            }

            ArrayList<RngTanPdfFormModel> templateList = tmpFormMap.get(tmpFormKey);
            //稟議入力フォーム情報
            ArrayList<RngFormdataModel> formList = formMap.get(model.getRngSid());
            if (formList != null) {
                //使用するユーザ名、グループ名、添付ファイル名の取得
                List<String> userSidList = new ArrayList<String>();
                List<Integer> groupSidList = new ArrayList<Integer>();
                List<Integer> fileBinList = new ArrayList<Integer>();
                for (RngTanPdfFormModel template : templateList) {
                    for (RngFormdataModel form : formList) {
                        if (form.getRfdValue() != null) {

                            if (form.getRfdSid() == template.getRtfSid()
                                    && form.getRfdValue().length() != 0) {
                                if (template.getRtfType() == 11) {
                                    userSidList.add(form.getRfdValue());
                                } else if (template.getRtfType() == 12) {
                                    groupSidList.add(Integer.parseInt(form.getRfdValue()));
                                } else if (template.getRtfType() == 15) {
                                    fileBinList.add(Integer.parseInt(form.getRfdValue()));
                                }
                            }

                        }
                    }
                }
                if (userSidList.size() > 0) {
                    CmnCmbsortConfDao sortDao = new CmnCmbsortConfDao(con__);
                    CmnCmbsortConfModel sortMdl = sortDao.getCmbSortData();
                    CmnUsrmInfDao usiDao = new CmnUsrmInfDao(con__);
                    String[] userSidArray = new String[userSidList.size()];
                    for (int i = 0; i < userSidList.size(); i++) {
                        userSidArray[i] = userSidList.get(i);
                    }
                    List < CmnUsrmInfModel > userList =
                            usiDao.getUsersInfList(
                                    userSidArray, sortMdl, GSConstUser.USER_JTKBN_ALL);
                    for (CmnUsrmInfModel userModel : userList) {
                        userNameMap.put(String.valueOf(userModel.getUsrSid()),
                                userModel.getUsiSei() + " " + userModel.getUsiMei());
                    }
                }

                if (groupSidList.size() > 0) {
                    GroupDao gDao = new GroupDao(con__);
                    int[] groupSidArray = new int[groupSidList.size()];
                    for (int i = 0; i < groupSidList.size(); i++) {
                        groupSidArray[i] = groupSidList.get(i);
                    }
                    if (groupSidArray.length == 1) {
                        CmnGroupmModel group = gDao.getGroupAll(groupSidArray[0]);
                        groupNameMap.put(String.valueOf(group.getGrpSid()),
                                group.getGrpName());
                    } else {
                        List<CmnGroupmModel> groupList = gDao.getGroupsAll(groupSidArray);
                        for (CmnGroupmModel groupModel : groupList) {
                            groupNameMap.put(String.valueOf(groupModel.getGrpSid()),
                                    groupModel.getGrpName());
                        }
                    }
                }

                if (fileBinList.size() > 0) {
                    List<Integer> fileBinSidArray = new ArrayList<Integer>();
                    for (int fileBin : fileBinList) {
                        fileBinSidArray.add(fileBin);
                    }
                    RngTanPdfDao dao = new RngTanPdfDao(con__);
                    HashMap<Integer, String> tempFileNameMap = dao.getFileNameList(fileBinSidArray);
                    for (int fileBinSid : fileBinSidArray) {
                        fileNameMap.put(String.valueOf(fileBinSid),
                                tempFileNameMap.get(fileBinSid));
                    }
                }
            }
        }

        //添付情報取得
        HashMap <Integer, List<CmnBinfModel>> map = new HashMap <Integer, List<CmnBinfModel>>();
        if (tmpSidList.size() > 0) {
            map = ringiDao.getRingiTmpFileList(tmpSidList, 0);
        }

        //稟議経路情報を設定
        RingiDao rngDao = new RingiDao(con__);
        HashMap<Integer, List<Rng030KeiroParam>> keiroListMap
                    = getKerio(rngDao, rngSidList);

        //出力用Modelの作成
        for (RingiDataModel model : modelList) {
            pdfModel = new RngTanPdfModel();
            pdfModel.setPdfRtpSid(model.getRtpSid());
            int apprMode__ = -1;
            //再申請フラグ
            if (apprMode != RngConst.RNG_APPRMODE_DISCUSSING
                    && apprMode != RngConst.RNG_APPRMODE_COMPLETE) {
                if (model.getRngApplicate() == userSid) {
                    if (model.getRssStatus() == RngConst.RNG_RNCSTATUS_CONFIRM
                            && model.getRncType() == RngConst.RNG_RNCTYPE_APPL) {
                        apprMode__ = RngConst.RNG_APPRMODE_APPL;
                    }
                }
            }

            //状態
            String status;
            switch (model.getRngStatus()) {
              case RngConst.RNG_STATUS_REQUEST :
                  status = gsMsg.getMessage("rng.48");
                  break;

              case RngConst.RNG_STATUS_SETTLED :
                  status = gsMsg.getMessage("rng.29");
                  break;

              case RngConst.RNG_STATUS_REJECT :
                  status = gsMsg.getMessage("cmn.rejection");
                  break;

              case RngConst.RNG_STATUS_DONE :
                  status = gsMsg.getMessage("rng.rng030.06");
                  break;

              case RngConst.RNG_STATUS_TORISAGE :
                  status = gsMsg.getMessage("rng.rng030.15");
                  break;

              default :
                  status = gsMsg.getMessage("cmn.draft");
                  break;
            }
            pdfModel.setPdfStatus(status);
            pdfModel.setPdfTitle(model.getRngTitle());
            pdfModel.setPdfApprUser(model.getApprUser());
            if (model.getStrRngAppldate() == null) {
                pdfModel.setPdfMakeDate(model.getStrMakeDate());
            } else {
                pdfModel.setPdfMakeDate(model.getStrRngAppldate());
            }
            pdfModel.setRngId(model.getRngId());

            //処理モード=再申請以外の場合は添付情報を設定
            if (apprMode__ != RngConst.RNG_APPRMODE_APPL) {
                pdfModel.setPdfTmpFileList(map.get(model.getRngSid()));
            }

         // 稟議テンプレートキーから該当する稟議テンプレートフォーム情報を取得
            String tmpFormKey = rngTanPdfDao.getRngTemplateKey(model.getRtpSid(),
                                                             model.getRtpVer());
            ArrayList<RngTanPdfFormModel> templateFormModel = tmpFormMap.get(tmpFormKey);
            //テンプレートが存在しない稟議は処理をしない
            if (!tmpFormMap.containsKey(tmpFormKey)) {
                continue;
            }

         // テンプレートフォーム一覧を並び替える条件
            Comparator<RngTanPdfFormModel> comparator = new Comparator<RngTanPdfFormModel>() {
                @Override
                public int compare(RngTanPdfFormModel o1, RngTanPdfFormModel o2) {
                    // ソート番号順
                    if (o1.getSort() > o2.getSort()) {
                        return 1;
                    } else if (o1.getSort() < o2.getSort()) {
                        return -1;
                    }
                    // 同じ要素内の場合 親要素 -> 行番号 -> ブロック内インデックスの順でまとめる
                    if (o1.getParentSid() > o2.getParentSid()) {
                        return 1;
                    } else if (o1.getParentSid() < o2.getParentSid()) {
                        return -1;
                    }
                    if (o1.getTableRow() > o2.getTableRow()) {
                        return 1;
                    } else if (o1.getTableRow() < o2.getTableRow()) {
                        return -1;
                    }
                    if (o1.getBlockIdx() > o2.getBlockIdx()) {
                        return 1;
                    } else if (o1.getBlockIdx() < o2.getBlockIdx()) {
                        return -1;
                    }
                    return 0;
                }
            };
            Collections.sort(templateFormModel, comparator); // 指定された順番に並び替える

            // 稟議SIDから該当する入力フォーム情報を取得
            //稟議入力フォーム情報
            ArrayList<RngFormdataModel> formList = formMap.get(model.getRngSid());

            List<RngTanPdfTemplateModel> pdfTemplate =  new ArrayList<RngTanPdfTemplateModel>();
            List<RngTanPdfTemplateModel> tableTemplate =  new ArrayList<RngTanPdfTemplateModel>();
            RngTanPdfTemplateModel escModel = new RngTanPdfTemplateModel();
            Map<Integer, ArrayList<RngTanPdfBodyModel>> tableBodyMap =
                    new HashMap<Integer, ArrayList<RngTanPdfBodyModel>>();
            int tableSid = -1;
            boolean headerFlg = false;
            boolean bodyAddFlg = false;
            boolean footerFlg = false;
            boolean tableFlg = false;

            for (RngTanPdfFormModel template : templateFormModel) {
                String param = "";
                int rfdSid = -1;

                //表 ボディ要素が空かどうかチェックを行う
                if (bodyAddFlg) {
                    boolean bodySpaceFlg = true;
                    for (int index = 0; index < tableBodyMap.size(); index++) {
                        if (tableBodyMap.get(index) != null) {
                            ArrayList<RngTanPdfBodyModel> tableBodyList = tableBodyMap.get(index);
                            if (tableBodyList.size() != 0) {
                                for (RngTanPdfBodyModel tableBody : tableBodyList) {
                                    if (tableBody.getParam() != null
                                            && tableBody.getTitle() != null) {
                                        if (tableBody.getTitle().length() != 0
                                                || tableBody.getParam().length() != 0) {
                                            bodySpaceFlg = false;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (bodySpaceFlg) {
                        bodyAddFlg = false;
                    }
                }
                if (tableSid != template.getParentSid() && tableSid != -1) {
                    if (tableTemplate.size() > 0 || bodyAddFlg) {
                        //表追加処理
                        int hitCount = 0;
                        RngTanPdfTemplateModel escTemplateModel = new RngTanPdfTemplateModel();
                        if (footerFlg) {
                            //footerが存在する場合、bodyを追加する為に入替を行う
                            escTemplateModel = tableTemplate.get(tableTemplate.size() - 1);
                            tableTemplate.remove(tableTemplate.size() - 1);
                        } else {
                            escTemplateModel = escModel;
                        }
                        if (headerFlg) {
                            hitCount++;
                        }
                        int bodyCount = 0;
                        for (int index = 0; bodyCount < tableBodyMap.size(); index++) {
                            if (tableBodyMap.get(index) != null) {
                                ArrayList<RngTanPdfBodyModel> tableBodyList
                                                        = tableBodyMap.get(index);
                                if (tableBodyList.size() != 0) {
                                    for (RngTanPdfBodyModel tableBody : tableBodyList) {
                                        RngTanPdfTemplateModel templateModel
                                                        = new RngTanPdfTemplateModel();
                                        templateModel.setTitle(tableBody.getTitle());
                                        templateModel.setParam(tableBody.getParam());
                                        templateModel.setParentSid(escTemplateModel.getParentSid());
                                        templateModel.setParentType(
                                                escTemplateModel.getParentType());
                                        templateModel.setParentTitle(
                                                escTemplateModel.getParentTitle());
                                        templateModel.setListSize(0);
                                        templateModel.setTableRow(bodyCount + hitCount);
                                        tableTemplate.add(templateModel);
                                    }
                                    bodyCount++;
                                }
                            }
                        }
                        hitCount += bodyCount;
                        if (footerFlg) {
                            hitCount++;
                            escTemplateModel.setTableRow(hitCount);
                            tableTemplate.add(escTemplateModel);
                        }
                        //PDF書き出し用listに追加
                        for (RngTanPdfTemplateModel tablePdfTemplateModel : tableTemplate) {
                            RngTanPdfTemplateModel tableTemplateModel
                                    = new RngTanPdfTemplateModel();
                            tableTemplateModel.setTitle(tablePdfTemplateModel.getTitle());
                            tableTemplateModel.setParam(tablePdfTemplateModel.getParam());
                            tableTemplateModel.setParentSid(escTemplateModel.getParentSid());
                            tableTemplateModel.setParentType(14);
                            tableTemplateModel.setParentTitle(escTemplateModel.getParentTitle());
                            tableTemplateModel.setListSize(hitCount); //max tableRow + 1
                            tableTemplateModel.setTableRow(tablePdfTemplateModel.getTableRow());
                            pdfTemplate.add(tableTemplateModel);
                        }
                        //表追加に使用する値の初期化
                        if (tableTemplate.size() > 0) {
                            tableFlg = false;
                        }
                        tableBodyMap =
                                new HashMap<Integer, ArrayList<RngTanPdfBodyModel>>();
                        tableTemplate = new ArrayList<RngTanPdfTemplateModel>();
                        headerFlg = false;
                        bodyAddFlg = false;
                        footerFlg = false;
                        tableSid = -1;
                    } else if (!headerFlg && !footerFlg && tableBodyMap.size() == 0) {
                        //表要素内が空だった場合、空の1行を追加
                        tableFlg = false;
                        escModel.setTitle("");
                        escModel.setParam("");
                        escModel.setListSize(1);
                        pdfTemplate.add(escModel);
                        //表追加に使用する値の初期化
                        tableBodyMap =
                                new HashMap<Integer, ArrayList<RngTanPdfBodyModel>>();
                        headerFlg = false;
                        footerFlg = false;
                        tableSid = -1;
                    }
                }

                if (template.getParentType() == 14) {
                    tableFlg = true;
                }

                RngTanPdfTemplateModel escTemplateModel = new RngTanPdfTemplateModel();
                escTemplateModel.setParentSid(template.getParentSid());
                escTemplateModel.setParentTitle(template.getParentTitle());
                escTemplateModel.setParentType(template.getParentType());
                escModel = escTemplateModel;

                if (formList != null) {
                    for (RngFormdataModel form : formList) {
                        if (form.getRfdSid() == template.getRtfSid()) {
                            String setParam = form.getRfdValue();
                            if (setParam == null) {
                                setParam = "";
                            }
                            //ユーザ選択orグループ選択or添付ファイル時、SIDから名前に変更
                             if (template.getRtfType() == 11) {
                                 if (setParam.length() != 0) {
                                     setParam = userNameMap.get(setParam);
                                 }
                             } else if (template.getRtfType() == 12) {
                                 if (setParam.length() != 0) {
                                     setParam = groupNameMap.get(setParam);
                                 }
                             } else if (template.getRtfType() == 15) {
                                 if (setParam.length() != 0) {
                                     setParam = fileNameMap.get(setParam);
                                 }
                             }
                            //数値or自動計算(合計)or自動計算(その他)時、数値用フォーマットを行う
                             if (template.getRtfType() == 5) {
                                 if (setParam.length() != 0) {
                                     NumberBox formatNumberBox = new NumberBox();
                                     formatNumberBox.setValue(form.getRfdValue());
                                     setParam = formatNumberBox.dspValueHTML();
                                     if (template.getRtfBody().length() != 0) {
                                         setParam += " " + template.getRtfBody();
                                     }
                                 }
                             } else if (template.getRtfType() == 9) {
                                 if (setParam.length() != 0) {
                                     Sum formatSum = new Sum();
                                     formatSum.setValue(form.getRfdValue());
                                     setParam = formatSum.dspValueHTML();
                                     if (template.getRtfBody().length() != 0) {
                                         setParam += " " + template.getRtfBody();
                                     }
                                 }
                             } else if (template.getRtfType() == 10) {
                                 if (setParam.length() != 0) {
                                     Calc formatCalc = new Calc();
                                     formatCalc.setValue(form.getRfdValue());
                                     setParam = formatCalc.dspValueHTML();
                                     if (template.getRtfBody().length() != 0) {
                                         setParam += " " + template.getRtfBody();
                                     }
                                 }
                             }
                             if (template.isListBodyFlg() && setParam.length() != 0) {
                                 ArrayList<RngTanPdfBodyModel> tableBodyList =
                                         new ArrayList<RngTanPdfBodyModel>();
                                 RngTanPdfBodyModel tableBody = new RngTanPdfBodyModel();
                                 if (tableBodyMap.containsKey(form.getRfdRowno())) {
                                     tableBodyList = tableBodyMap.get(form.getRfdRowno());
                                 }
                                 if (tableBodyList.size() > 0) {
                                     if (tableBodyList.get(tableBodyList.size() - 1).getFormSid()
                                             == form.getRfdSid()) {
                                         tableBody = tableBodyList.get(tableBodyList.size() - 1);
                                         tableBodyList.remove(tableBodyList.size() - 1);
                                         tableBody.setParam(tableBody.getParam()
                                                 + "\n" + setParam);
                                         tableBodyList.add(tableBody);
                                         tableBodyMap.put(form.getRfdRowno(), tableBodyList);
                                     } else {
                                         tableBody.setTitle(template.getRtfTitle());
                                         tableBody.setParam(setParam);
                                         tableBody.setFormSid(form.getRfdSid());
                                         tableBody.setTableRow(form.getRfdRowno());
                                         tableBodyList.add(tableBody);
                                         tableBodyMap.put(form.getRfdRowno(), tableBodyList);
                                     }
                                 } else {
                                     tableBody.setTitle(template.getRtfTitle());
                                     tableBody.setParam(setParam);
                                     tableBody.setFormSid(form.getRfdSid());
                                     tableBody.setTableRow(form.getRfdRowno());
                                     tableBodyList.add(tableBody);
                                     tableBodyMap.put(form.getRfdRowno(), tableBodyList);
                                 }
                             } else {
                                 if (setParam.length() != 0) {
                                     if (form.getRfdSid() == rfdSid) {
                                         param += "\n" + setParam;
                                     } else {
                                         rfdSid = form.getRfdSid();
                                         param = setParam;
                                     }
                                 }
                             }
                        }
                    }
                }

                if (template.getParentType() == 14) {
                    if (tableSid == -1) {
                        tableSid = template.getParentSid();
                    }
                }

                if (template.isListBodyFlg() && template.getRtfTitle().length() != 0
                        && tableSid == template.getParentSid()) {
                    ArrayList<RngTanPdfBodyModel> tableBodyList =
                            new ArrayList<RngTanPdfBodyModel>();

                    for (int index = 0; index < tableBodyMap.size(); index++) {

                        RngTanPdfBodyModel tableBody = new RngTanPdfBodyModel();

                        if (tableBodyMap.containsKey(index)) {
                            tableBodyList = tableBodyMap.get(index);
                        }

                        if (tableBodyList.size() > 0) {
                            if (tableBodyList.get(tableBodyList.size() - 1).getTitle()
                                    != template.getRtfTitle()
                                    && tableBodyList.get(tableBodyList.size() - 1)
                                    .getTitle().length() != 0) {
                                RngTanPdfBodyModel tableBodyData
                                            = tableBodyList.get(tableBodyList.size() - 1);
                                tableBody.setFormSid(tableBodyData.getFormSid());
                                tableBody.setTableRow(tableBodyData.getTableRow());
                                tableBody.setTitle(template.getRtfTitle());
                                tableBody.setParam("");
                                tableBodyList.add(tableBody);
                                tableBodyMap.put(index, tableBodyList);
                            }
                        } else {
                            tableBody.setTitle(template.getRtfTitle());
                            tableBody.setParam("");
                            tableBody.setTableRow(index);
                            tableBodyList.add(tableBody);
                            tableBodyMap.put(index, tableBodyList);
                        }
                    }
                    if (tableBodyMap.size() == 0) {
                        //タイトルが存在する場合は1行だけ表示する
                        //ただし、ボディ行数が0だった場合は表示しない
                        if (bodyMap.containsKey(model.getRngSid())) {
                            HashMap<Integer, Integer> bodyDataMap = bodyMap.get(model.getRngSid());
                            if (bodyDataMap.containsKey(template.getParentSid())) {
                                if (bodyDataMap.get(template.getParentSid()) != 0) {
                                    RngTanPdfBodyModel tableBody = new RngTanPdfBodyModel();
                                    tableBody.setTitle(template.getRtfTitle());
                                    tableBody.setParam("");
                                    tableBody.setTableRow(0);
                                    tableBodyList.add(tableBody);
                                    tableBodyMap.put(0, tableBodyList);
                                }
                            }
                        }

                    }
                }

                if (rfdSid == -1) {
                    param = "";
                }

              if (template.getParentType() == 14 && tableSid == template.getParentSid()) {
                  if (!template.isListBodyFlg()) {
                      RngTanPdfTemplateModel templateModel = new RngTanPdfTemplateModel();
                      if (footerFlg) {
                          templateModel = tableTemplate.get(tableTemplate.size() - 1);
                          tableTemplate.remove(tableTemplate.size() - 1);
                          String setParam = "";
                          setParam += templateModel.getParam();
                          if (setParam.length() != 0) {
                              setParam += "\n";
                          }
                          if (template.getRtfTitle().length() != 0) {
                              setParam += template.getRtfTitle();
                          }
                          if (param != null) {
                              if (param.length() != 0) {
                                  if (template.getRtfTitle().length() != 0) {
                                      setParam += " ";
                                  }
                                  setParam += param;
                              }
                          }
                          templateModel.setParam(setParam);
                          tableTemplate.add(templateModel);
                      } else {
                          templateModel = new RngTanPdfTemplateModel();
                          String setParam = "";
                          templateModel.setTitle("");
                          if (template.getRtfTitle().length() != 0) {
                              setParam += template.getRtfTitle();
                          }
                          if (param != null) {
                              if (param.length() != 0) {
                                  if (template.getRtfTitle().length() != 0) {
                                      setParam += " ";
                                  }
                                  setParam += param;
                              }
                          }
                          if (setParam.length() != 0) {
                              templateModel.setParam(setParam);
                              templateModel.setParentSid(template.getParentSid());
                              templateModel.setParentType(14);
                              templateModel.setParentTitle(template.getParentTitle());
                              templateModel.setListSize(0);
                              templateModel.setTableRow(0);
                              tableTemplate.add(templateModel);
                              if (template.isListFooterFlg()) {
                                  footerFlg = true;
                              } else {
                                  headerFlg = true;
                              }
                          }
                      }
                      templateModel = new RngTanPdfTemplateModel();
                      templateModel.setParentSid(template.getParentSid());
                      templateModel.setParentTitle(template.getParentTitle());
                      templateModel.setParentType(template.getParentType());
                      escModel = templateModel;
                  } else {
                      bodyAddFlg = true;
                      RngTanPdfTemplateModel templateModel = new RngTanPdfTemplateModel();
                      templateModel.setParentSid(template.getParentSid());
                      templateModel.setParentTitle(template.getParentTitle());
                      templateModel.setParentType(template.getParentType());
                      escModel = templateModel;
                  }

              } else {
                      RngTanPdfTemplateModel templateModel = new RngTanPdfTemplateModel();
                      templateModel.setTitle(template.getRtfTitle());
                      templateModel.setParam(param);
                      templateModel.setParentSid(template.getParentSid());
                      templateModel.setParentType(template.getParentType());
                      templateModel.setParentTitle(template.getParentTitle());
                      templateModel.setTableRow(template.getTableRow());
                      templateModel.setCommentValign(template.getCommentValign());
                      pdfTemplate.add(templateModel);
              }
            }

            //表 ボディ要素が空かどうかチェックを行う
            if (bodyAddFlg) {
                boolean bodySpaceFlg = true;
                for (int index = 0; index < tableBodyMap.size(); index++) {
                    if (tableBodyMap.get(index) != null) {
                        ArrayList<RngTanPdfBodyModel> tableBodyList = tableBodyMap.get(index);
                        if (tableBodyList.size() != 0) {
                            for (RngTanPdfBodyModel tableBody : tableBodyList) {
                                if (tableBody.getParam() != null
                                        && tableBody.getTitle() != null) {
                                    if (tableBody.getTitle().length() != 0
                                            || tableBody.getParam().length() != 0) {
                                        bodySpaceFlg = false;
                                    }
                                }
                            }
                        }
                    }
                }
                if (bodySpaceFlg) {
                    bodyAddFlg = false;
                }
            }

            //表要素の値が存在する時
            if (tableTemplate.size() > 0 || bodyAddFlg) {
                int hitCount = 0;
                RngTanPdfTemplateModel escTemplateModel = new RngTanPdfTemplateModel();
                if (footerFlg) {
                    escTemplateModel = tableTemplate.get(tableTemplate.size() - 1);
                    tableTemplate.remove(tableTemplate.size() - 1);
                } else {
                    //footerが無かった時、ParentSid,Type,Titleをセットする為に
                    //最後に追加した表要素を使用する
                    escTemplateModel = escModel;
                }
                if (headerFlg) {
                    hitCount++;
                }
                int bodyCount = 0;
                for (int index = 0; bodyCount < tableBodyMap.size(); index++) {
                    if (tableBodyMap.get(index) != null) {
                        ArrayList<RngTanPdfBodyModel> tableBodyList = tableBodyMap.get(index);
                        if (tableBodyList.size() != 0) {
                            for (RngTanPdfBodyModel tableBody : tableBodyList) {
                                RngTanPdfTemplateModel templateModel
                                                = new RngTanPdfTemplateModel();
                                templateModel.setTitle(tableBody.getTitle());
                                templateModel.setParam(tableBody.getParam());
                                templateModel.setParentSid(escTemplateModel.getParentSid());
                                templateModel.setParentType(escTemplateModel.getParentType());
                                templateModel.setParentTitle(escTemplateModel.getParentTitle());
                                templateModel.setListSize(0);
                                templateModel.setTableRow(bodyCount + hitCount);
                                tableTemplate.add(templateModel);
                            }
                            bodyCount++;
                        }
                    }
                }
                hitCount += bodyCount;
                if (footerFlg) {
                    hitCount++;
                    escTemplateModel.setTableRow(hitCount);
                    tableTemplate.add(escTemplateModel);
                }
                //PDF書き出し用listに追加
                for (RngTanPdfTemplateModel tablePdfTemplateModel : tableTemplate) {
                    RngTanPdfTemplateModel tableTemplateModel = new RngTanPdfTemplateModel();
                    tableTemplateModel.setTitle(tablePdfTemplateModel.getTitle());
                    tableTemplateModel.setParam(tablePdfTemplateModel.getParam());
                    tableTemplateModel.setParentSid(escTemplateModel.getParentSid());
                    tableTemplateModel.setParentType(14);
                    tableTemplateModel.setParentTitle(escTemplateModel.getParentTitle());
                    tableTemplateModel.setListSize(hitCount); //max tableRow + 1
                    tableTemplateModel.setTableRow(tablePdfTemplateModel.getTableRow());
                    pdfTemplate.add(tableTemplateModel);
                }
                if (tableTemplate.size() > 0) {
                    tableFlg = false;
                }
            }
            if (tableFlg && !headerFlg && !footerFlg && tableBodyMap.size() == 0) {
                //表要素内が空だった場合、空の1行を追加
                tableFlg = false;
                escModel.setTitle("");
                escModel.setParam("");
                escModel.setListSize(1);
                pdfTemplate.add(escModel);
            }

            pdfModel.setPdfTmpList(pdfTemplate);

            //経路情報
            List<RngTanPdfKeiroModel> setKeiroList = new ArrayList<RngTanPdfKeiroModel>();
            List<Rng030KeiroParam> keiroList = keiroListMap.get(model.getRngSid());
            for (Rng030KeiroParam keiro : keiroList) {
                RngTanPdfKeiroModel keiroModel = new RngTanPdfKeiroModel();
                List<RngTanPdfSingiModel> setSingiList = new ArrayList<RngTanPdfSingiModel>();
                keiroModel.setKeiroStatus(keiro.getKeiroStatus());
                keiroModel.setKeiroName(keiro.getKeiroName());
                keiroModel.setKeiroSort(keiro.getKeiroSort());
                keiroModel.setKeiroSingi(keiro.getKeiroSingi());
                keiroModel.setKeiroKoetuMei(keiro.getKeiroKoetuMei());
                keiroModel.setKeiroMessage(keiro.getKeiroMessage());
                keiroModel.setKeiroLimit(keiro.getKeiroLimit());
                int keiroCount = 0;
                int rowCount = 0;
                if (keiro.getSingiList().size() > 1) {
                    rowCount = rowCount + 2;
                }
                for (Rng030SingiParam singi : keiro.getSingiList()) {
                    RngTanPdfSingiModel singiModel = new RngTanPdfSingiModel();
                    singiModel.setSingiName(singi.getSingiName());
                    singiModel.setDairiSid(singi.getDairiSid());
                    singiModel.setSingiDairi(singi.getSingiDairi());
                    singiModel.setSingiKoetu(singi.getSingiKoetu());
                    singiModel.setSingiComment(singi.getSingiComment());
                    singiModel.setSingiStatus(singi.getSingiStatus());
                    singiModel.setSingiDate(singi.getSingiDate());
                    singiModel.setSingiTime(singi.getSingiTime());
                    singiModel.setSingiPosition(singi.getSingiPosition());
                    singiModel.setTmpFileList(singi.getTmpFileList());
                    setSingiList.add(singiModel);
                    if (singiModel.getDairiSid() > 0) {
                        rowCount++;
                        //審議者が一人、かつ経路名が存在する場合
                        if (keiro.getSingiList().size() == 1 && keiro.getKeiroName() != null) {
                            rowCount++;
                        }
                    }
                    rowCount = rowCount + 2;
                    keiroCount++;
                }
                rowCount = rowCount - 1;
                keiroModel.setSingiList(setSingiList);
                keiroModel.setKeiroRowCount(rowCount);
                keiroModel.setKeiroCount(keiroCount);
                setKeiroList.add(keiroModel);
            }
            pdfModel.setPdfKeiroList(setKeiroList);

            //ファイル名
            StringBuilder strBlr = new StringBuilder();
            strBlr.append(gsMsg.getMessage("rng.62"));
            strBlr.append("_");
            strBlr.append(NullDefault.getString(pdfModel.getPdfTitle(), ""));
            String encFileName = __fileNameCheck(strBlr.toString()) + ".pdf";

            pdfModel.setFileName(encFileName);
            ret.add(pdfModel);
        }

        return ret;
    }

    /**
     *
     * <br>[機  能]経路リスト取得
     * <br>[解  説]
     * <br>[備  考]
     * @param ringiDao RingiDao
     * @param rngSidList 稟議SIDリスト
     * @throws SQLException SQLException
     * @return keiroList List<Rng030KeiroParam>
     */
      public HashMap<Integer, List<Rng030KeiroParam>> getKerio(
              RingiDao ringiDao, ArrayList<Integer> rngSidList)
            throws SQLException {
        //稟議経路情報を設定
        HashMap<Integer, List<Rng030KeiroParam>> ret
                = new HashMap<Integer, List<Rng030KeiroParam>>();
        List<Rng030KeiroParam> keiroList = ringiDao.getKeiroList(rngSidList);
        List<Rng030SingiParam> singiList = ringiDao.getSingiList(rngSidList);

        //審議リストを経路SIDごとのMapを生成
        Map<Integer, List<Rng030SingiParam>> rksSidSingiListMap
          = new HashMap<Integer, List<Rng030SingiParam>>();
        for (Rng030SingiParam sMdl : singiList) {
            Integer rksSid = Integer.valueOf(sMdl.getKeiroSid());
            List<Rng030SingiParam> sList;
            if (rksSidSingiListMap.containsKey(rksSid)) {
                sList = rksSidSingiListMap.get(rksSid);
            } else {
                sList = new ArrayList<Rng030SingiParam>();
                rksSidSingiListMap.put(rksSid, sList);
            }
            sList.add(sMdl);
        }

        for (Rng030KeiroParam kMdl : keiroList) {
            Integer rksSid = Integer.valueOf(kMdl.getKeiroStepSid());
            List<Rng030SingiParam> sList;
            if (rksSidSingiListMap.containsKey(rksSid)) {
                sList = rksSidSingiListMap.get(rksSid);
            } else {
                sList = new ArrayList<Rng030SingiParam>();
            }
            int keiroCnt = 0;
            int delUserCnt = 0;
            String sKoetuUserMei = null;
            for (Rng030SingiParam sMdl : sList) {
                sKoetuUserMei = sMdl.getSingiKoetu();
                keiroCnt += 1;
                if (sMdl.getUserJkbn() != 0) {
                    delUserCnt += 1;
                }
            }
            if (keiroCnt == delUserCnt) {
                kMdl.setKeiroDelFlg(1);
            }

            kMdl.setSingiList(sList);
            kMdl.setKeiroCount(keiroCnt);

            int nPro = kMdl.getKeiroProgress();
            int nMes = 0;
            if (nPro == 0) {
                // 全員の審議
                nMes = RngConst.RNG_OUT_CONDITION_DELIBERATION;
            } else if (nPro == 1) {
                // 全員の承認
                nMes = RngConst.RNG_OUT_CONDITION_APPROVAL;
            } else if (nPro == 2) {
                // 人かの承認
                nMes = RngConst.RNG_OUT_CONDITION_NUMBER;
            } else {
                // 割かの承認
                nMes = RngConst.RNG_OUT_CONDITION_RATE;
            }
            kMdl.setKeiroMessage(nMes);

            if (kMdl.getKeiroStatus() == 6) {
                kMdl.setKeiroKoetuMei(sKoetuUserMei);
            }

            List<Rng030KeiroParam> retModel = new ArrayList<Rng030KeiroParam>();
            if (ret.containsKey(kMdl.getRngSid())) {
                retModel = ret.get(kMdl.getRngSid());
            }
            retModel.add(kMdl);
            ret.put(kMdl.getRngSid(), retModel);
        }
        return ret;
    }

    /**
     * <br>[機  能] ファイル名として使用できるか文字列チェックする。
     * <br>[解  説] /\?*:|"<>. を除去
     *                    255文字超える場合は以降を除去
     * <br>[備  考]
     * @param fileName テンポラリディレクトリ
     * @return 出力したファイルのパス
     */
    private String __fileNameCheck(String fileName) {
        String escName = fileName;

        escName = StringUtilHtml.replaceString(escName, "　", " ");
        escName = StringUtilHtml.replaceString(escName, "/", "");
        escName = StringUtilHtml.replaceString(escName, "\\", ""); //\
        escName = StringUtilHtml.replaceString(escName, "?", "");
        escName = StringUtilHtml.replaceString(escName, "*", "");
        escName = StringUtilHtml.replaceString(escName, ":", "");
        escName = StringUtilHtml.replaceString(escName, "|", "");
        escName = StringUtilHtml.replaceString(escName, "\"", ""); //"
        escName = StringUtilHtml.replaceString(escName, "<", "");
        escName = StringUtilHtml.replaceString(escName, ">", "");
        escName = StringUtilHtml.replaceString(escName, ".", "");
        escName = StringUtil.trimRengeString(escName, 251); //ファイル名＋拡張子=255文字以内

        return escName;
    }
}