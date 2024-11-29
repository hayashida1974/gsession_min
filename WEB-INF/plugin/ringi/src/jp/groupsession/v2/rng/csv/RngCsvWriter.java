package jp.groupsession.v2.rng.csv;

import java.io.File;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.csv.AbstractCSVWriter;
import jp.co.sjts.util.csv.CSVException;
import jp.co.sjts.util.io.IOTools;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.dao.base.CmnGroupmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.dao.RingiDao;
import jp.groupsession.v2.rng.dao.RngFormdataDao;
import jp.groupsession.v2.rng.model.RingiDataModel;
import jp.groupsession.v2.rng.model.RingiSearchModel;
import jp.groupsession.v2.rng.model.RngFormdataModel;
import jp.groupsession.v2.struts.msg.GsMessage;

/**
 * <br>[機  能] 稟議情報一覧のCSV出力を行う
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngCsvWriter extends AbstractCSVWriter {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(RngCsvWriter.class);

    /** コネクション */
    private Connection con__ = null;

    /** 稟議情報一覧ファイル名 */
    public static final String FILE_NAME = "ringiList.csv";

    /** 検索条件 */
    private RingiSearchModel searchModel__ = null;

    /** 実行者SID */
    private int sessionUserSid__;
    /** リクエストモデル */
    private RequestModel reqMdl__ = null;

    /**
     * <p>Set HttpServletRequest
     * @param reqMdl リクエストモデル
     * @param searchModel 検索条件
     */
    public RngCsvWriter(RequestModel reqMdl, RingiSearchModel searchModel) {
        reqMdl__ = reqMdl;
        searchModel__ = searchModel;
    }

    /**
     * <br>[機  能] CSVファイルの作成
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param csvPath 出力先
     * @throws CSVException CSV出力時例外
     */
    public void outputCsv(Connection con, String csvPath)
    throws CSVException {

        setCon(con);

        //ディレクトリの作成
        File tmpDir = new File(csvPath);
        tmpDir.mkdirs();

        //セットファイル名とフルパス
        String fileName = FILE_NAME;
        String fileFullPath = IOTools.replaceFileSep(csvPath + File.separator + fileName);
        log__.debug("CSVファイルのパス = " + fileFullPath);

        //出力初期セット
        setCsvPath(fileFullPath);

        log__.debug("開始");
        write();
        log__.debug("終了");
    }

    /**
     * <br>[機  能] CSV生成 値をセットする
     * <br>[解  説]
     * <br>[備  考]
     * @param pw PrintWriter
     * @throws CSVException CSV出力時例外
     */
    public void create(PrintWriter pw) throws CSVException {

        RngCsvRecordListenerIppanImpl rl = new RngCsvRecordListenerIppanImpl(pw);

        ArrayList<RingiCsvModel> itemList = new ArrayList<RingiCsvModel>();
        Connection con = getCon();

        try {
            int procMode = searchModel__.getRngType();

            ArrayList<String>  rtpKeyList = new ArrayList<String>();  // 稟議テンプレートキー一覧
            ArrayList<Integer> rngSidList = new ArrayList<Integer>(); // 稟議SID一覧

            //詳細
            RingiDao ringiDao = new RingiDao(con);
            List<RingiDataModel> rngDataList = ringiDao.getRingiDataList(searchModel__, procMode);
            if (rngDataList != null && !rngDataList.isEmpty()) {
                ArrayList<Integer> sinsSidList = new ArrayList<Integer>(); // 申請中稟議SID一覧
                ArrayList<Integer> compSidList = new ArrayList<Integer>(); // 完了済み稟議SID一覧

                RngFormdataDao formdataDao = new RngFormdataDao(con);
                RingiCsvDao    rngCsvDao  = new RingiCsvDao(con);
                for (RingiDataModel model : rngDataList) {
                    Integer rngKey = Integer.valueOf(model.getRngSid());
                    if (model.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE) {
                        compSidList.add(rngKey);
                    } else {
                        sinsSidList.add(rngKey);
                    }

                    // 稟議テンプレートキー → 稟議テンプレートSID ＋ 区切り文字 ＋ バージョン番号
                    String key = rngCsvDao.getRngTemplateKey(model.getRtpSid(),
                                                              model.getRtpVer());
                    rtpKeyList.add(key);
                }
                rngSidList.addAll(sinsSidList);
                rngSidList.addAll(compSidList);

                // 稟議SIDから該当する入力フォーム情報を取得
                HashMap<Integer, ArrayList<RngFormdataModel>> formMap =
                                                                formdataDao.select(rngSidList);

                // 稟議テンプレートキーから該当する稟議テンプレートフォーム情報を取得
                HashMap<String, ArrayList<RingiCsvFormModel>> tmpFormMap =
                                                           rngCsvDao.select(reqMdl__, rtpKeyList);

                // 稟議SIDから該当する経路情報を取得(申請中, 完了済み)
                HashMap<Integer, ArrayList<LabelValueBean>> sinsKeiroMap =
                                                              __getKeiroMember(sinsSidList, false);
                HashMap<Integer, ArrayList<LabelValueBean>> compKeiroMap =
                                                              __getKeiroMember(compSidList, true);

                // テンプレートフォーム一覧を並び替える条件
                Comparator<RingiCsvFormModel> comparator = new Comparator<RingiCsvFormModel>() {
                    @Override
                    public int compare(RingiCsvFormModel o1, RingiCsvFormModel o2) {
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

                for (RingiDataModel model : rngDataList) {
                    Integer key = Integer.valueOf(model.getRngSid());

                    RingiCsvModel csvMdl = new RingiCsvModel();
                    csvMdl.setRngSid(model.getRngSid());                 // 稟議SID
                    csvMdl.setRngId(model.getRngId());                 // 申請ID
                    csvMdl.setRngTitle(model.getRngTitle());             // タイトル
                    csvMdl.setApprUser(model.getApprUser());             // 申請者 ユーザ名
                    csvMdl.setStrRngAppldate(model.getStrRngAppldate()); // 作成日時(文字列)
                    csvMdl.setRngContent(model.getRngContent());         // 内容
                    csvMdl.setRngStatus(model.getRngStatus());           // 状態
                    csvMdl.setStrLastManageDate(model.getStrLastManageDate()); // 最終更新日時

                    String tmpFormKey = rngCsvDao.getRngTemplateKey(model.getRtpSid(),
                                                                     model.getRtpVer());
                    ArrayList<LabelValueBean> formDataList = new ArrayList<LabelValueBean>();
                    if (formMap    != null && formMap.containsKey(key)
                     && tmpFormMap != null && tmpFormMap.containsKey(tmpFormKey)) {

                        int parentSid = -1;
                        int rowIdx    = -1;
                        int maxRow    = 0;

                        //逆ループ
                        ArrayList<RngFormdataModel> formList = formMap.get(key);

                        ArrayList<RingiCsvFormModel> templateList = tmpFormMap.get(tmpFormKey);
                        Collections.sort(templateList, comparator); // 指定された順番に並び替える

                        LinkedHashMap<Integer, ArrayList<LabelValueBean>> blockMap =
                                new LinkedHashMap<Integer, ArrayList<LabelValueBean>>();

                        //テンプレート情報を取得している
                        for (RingiCsvFormModel template : templateList) {

                            if (parentSid != template.getParentSid()
                             || rowIdx != template.getTableRow()) {
                                // 別ブロックへ移動した場合
                                if (blockMap.size() > 0) {
                                    // 前回のブロック要素を書き込む
                                    maxRow = __setMatrixList(formDataList, blockMap, maxRow);
                                    blockMap.clear();
                                }

                                // ブロック要素情報を更新
                                if (template.getParentSid() <= 0
                                 || parentSid != template.getParentSid()) {
                                    maxRow = 0;  // 別のブロック or 表データの場合には初期化
                                }
                                parentSid = template.getParentSid();
                                rowIdx    = template.getTableRow();
                            }

                            // Iterator を使用することで途中削除しても配列を破壊しない
                            Iterator<RngFormdataModel> it = formList.iterator();

                            if (template.getParentSid() >= 0
                             && template.getParentType() == EnumFormModelKbn.blocklist.getValue()) {
                                // 表のブロック要素の場合、一旦はマップデータへ出力情報を格納する
                                ArrayList<LabelValueBean> blockList =
                                                                new ArrayList<LabelValueBean>();
                                //入力フォームデータ検索
                                while (it.hasNext()) {
                                    RngFormdataModel formMdl = it.next();
                                    if (formMdl.getRfdSid() == template.getRtfSid()) {
                                        LabelValueBean labelVal =
                                                            __convertFormBean(formMdl, template);
                                        if (labelVal != null) {
                                            // データ一覧の行番号位置へ出力情報を保存
                                            int row = formMdl.getRfdRowno();
                                            if (row >= blockList.size()) {
                                                // 配列に行番号位置が存在しない場合は、間を空データで埋める
                                                for (int i = blockList.size(); i < row; i++) {
                                                    blockList.add(new LabelValueBean("", ""));
                                                }
                                                blockList.add(labelVal); // 最後に目的のデータを追加
                                            } else {
                                                blockList.set(row, labelVal); // 指定位置に目的のデータを追加
                                            }
                                        }
                                        it.remove(); // 使用済みのフォーム情報は削除
                                    }
                                }
                                // ブロック内の要素ごとにデータ一覧を格納する
                                Integer blockKey = Integer.valueOf(template.getRtfSid());
                                blockMap.put(blockKey, blockList);
                            } else {
                                // ブロック以外の要素
                                while (it.hasNext()) {
                                    RngFormdataModel formMdl = it.next();
                                    if (formMdl.getRfdSid() == template.getRtfSid()) {
                                        LabelValueBean labelVal =
                                                            __convertFormBean(formMdl, template);
                                        if (labelVal != null) {
                                            formDataList.add(labelVal);
                                        }
                                        it.remove(); // 使用済みのフォーム情報は削除
                                    }
                                }
                            }
                        }

                        // まだブロック要素が残っている場合は、残り分を書き込む
                        if (blockMap.size() > 0) {
                            __setMatrixList(formDataList, blockMap, maxRow);
                            blockMap.clear();
                        }
                    }

                    csvMdl.setFormDataList(formDataList);

                    if (model.getRngCompflg() == RngConst.RNG_COMPFLG_COMPLETE) {
                        csvMdl.setKeiroList(compKeiroMap.get(key));
                    } else {
                        csvMdl.setKeiroList(sinsKeiroMap.get(key));
                    }
                    itemList.add(csvMdl);
                }
            }

        } catch (SQLException e) {
            log__.error("SQLの実行に失敗", e);
            StackTraceElement[] stackTrace = e.getStackTrace();
            CSVException ex = new CSVException("SQLの実行に失敗", e);
            ex.setStackTrace(stackTrace);
            throw ex;
        }

        int maxSize = 0;
        if (!itemList.isEmpty()) {
            for (RingiCsvModel model : itemList) {
                int size = 0;
                if (model.getFormDataList() != null) {
                    size += model.getFormDataList().size();
                }
                if (model.getKeiroList() != null) {
                    size += model.getKeiroList().size();
                }
                if (size > maxSize) {
                    maxSize = size;
                }
            }

            for (RingiCsvModel model : itemList) {
                model.setMaxSize(maxSize); // 項目数を最大数に合わせる
                rl.setRecord(model);
            }
        }
    }

    /**
     * <br>[機  能] 入力フォーム一覧に行列情報をセット
     * <br>[解  説]
     * <br>[備  考]
     * @param formList 入力フォーム一覧
     * @param blockMap ブロック情報マップ
     * @param row 開始行番号
     * @return 次の行番号
     */
    private int __setMatrixList(ArrayList<LabelValueBean> formList,
                                LinkedHashMap<Integer, ArrayList<LabelValueBean>> blockMap,
                                int row) {
        int maxSize  = 0;
        if (formList != null) {
            for (Entry<Integer, ArrayList<LabelValueBean>> entry : blockMap.entrySet()) {
                // 格納されているブロック要素郡を追加
                ArrayList<LabelValueBean> block = entry.getValue();
                if (block.size() > maxSize) {
                    maxSize = block.size(); // 最大行数
                }
            }

            for (int i = 0; i < maxSize; i++) {
                // 1行ごとにブロック要素をチェック
                int blockIdx = 0;
                for (Entry<Integer, ArrayList<LabelValueBean>> entry : blockMap.entrySet()) {
                    ArrayList<LabelValueBean> block = entry.getValue();
                    if (block.size() > i) {
                        LabelValueBean bean = block.get(i);
                        String label = bean.getLabel(); // 要素名に位置情報を追加
                        bean.setLabel("matrix(" + blockIdx + "-" + (row + i) + ")" + label);
                        formList.add(bean);
                    }
                    blockIdx++;
                }
            }
        }
        return (row + maxSize);
    }

    /**
     * <br>[機  能] 経路情報一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param rngSidList 稟議SID一覧
     * @param isComp     稟議完了済みフラグ
     * @return 経路情報一覧
     * @throws SQLException SQL実行例外
     */
    private HashMap<Integer, ArrayList<LabelValueBean>> __getKeiroMember(List<Integer> rngSidList,
                                                                         boolean isComp)
            throws SQLException {

        HashMap<Integer, ArrayList<LabelValueBean>> ret =
                                                new HashMap<Integer, ArrayList<LabelValueBean>>();

        if (rngSidList != null) {
            Connection  con    = getCon();
            RingiCsvDao csvDao = new RingiCsvDao(con);

            ArrayList<RngCsvKeiroStepModel> list = csvDao.keiroSelect(rngSidList);

            // グループSID + ユーザSID → グループ名のみ
            // 役職SID + ユーザSID     → 役職名のみ
            // ユーザSID               → ユーザ名のみ
            ArrayList<Integer> grpSids = new ArrayList<Integer>(); // グループSID一覧
            ArrayList<Integer> posSids = new ArrayList<Integer>(); // 役職SID一覧
            ArrayList<Integer> usrSids = new ArrayList<Integer>(); // ユーザSID一覧
            HashMap<Integer, Integer> statusMap = new HashMap<Integer, Integer>();

            for (RngCsvKeiroStepModel mdl: list) {
                if (mdl.getRksType() > 0) {
                    // 審議データ(承認・確認したユーザ)
                    if (!usrSids.contains(Integer.valueOf(mdl.getUsrSid()))) {
                        usrSids.add(Integer.valueOf(mdl.getUsrSid()));
                    }
                    if (mdl.getKouetuUsrSid() > 0
                     && !usrSids.contains(Integer.valueOf(mdl.getKouetuUsrSid()))) {
                        usrSids.add(Integer.valueOf(mdl.getKouetuUsrSid()));
                    }
                    if (mdl.getDairiUsrSid() > 0
                     && !usrSids.contains(Integer.valueOf(mdl.getDairiUsrSid()))) {
                        usrSids.add(Integer.valueOf(mdl.getDairiUsrSid()));
                    }
                } else {
                    // 経路ステップ選択ユーザデータ(経路にあるユーザorグループor役職)
                    int grpSid    = mdl.getGrpSid();
                    int posSid    = mdl.getPosSid();
                    if (grpSid >= 0 && !grpSids.contains(Integer.valueOf(grpSid))) {
                        grpSids.add(Integer.valueOf(grpSid));
                    }
                    if (posSid >= 0 && !posSids.contains(Integer.valueOf(posSid))) {
                        posSids.add(Integer.valueOf(posSid));
                    }
                    statusMap.put(Integer.valueOf(mdl.getRksSid()), mdl.getRksStatus());
                }
            }

            RingiDao rngDao = new RingiDao(con);
            HashMap<Integer, RngCsvUserModel>           usrMap = rngDao.getKeiroUserList(usrSids);
            HashMap<Integer, String>                    grpMap = csvDao.getGroupNameMap(grpSids);
            HashMap<Integer, RngCsvPositionMemberModel> posMap = csvDao.getPositionMember(posSids);

            GsMessage gsMsg = new GsMessage(reqMdl__);

            //役職SID=0(未設定)の場合、役職名を「役職なし」に差し替える
            RngCsvPositionMemberModel defPosMdl = posMap.get(Integer.valueOf(0));
            if (defPosMdl != null) {
                defPosMdl.setName(gsMsg.getMessage("cmn.nopost"));
            }

            // 選択ユーザマップデータ
            HashMap<Integer, ArrayList<String>> selectMap
                                = new HashMap<Integer, ArrayList<String>>();
            ArrayList<RngCsvKeiroStepModel> singiList = new ArrayList<RngCsvKeiroStepModel>();

            for (RngCsvKeiroStepModel mdl : list) {
                if (mdl.getRksType() == 0) {
                    Integer rksKey = Integer.valueOf(mdl.getRksSid());
                    ArrayList<String> beanList = selectMap.get(rksKey);
                    if (beanList == null) {
                        beanList = new ArrayList<String>();
                    }

                    String title = "";

                    // グループ指定
                    if (mdl.getGrpSid() >= 0) {
                        Integer key = Integer.valueOf(mdl.getGrpSid());
                        if (grpMap.containsKey(key)) {
                            title += grpMap.get(key);
                        }
                    }

                    // 役職指定
                    if (mdl.getPosSid() >= 0) {
                        Integer key = Integer.valueOf(mdl.getPosSid());
                        if (posMap.containsKey(key)) {
                            RngCsvPositionMemberModel posMdl = posMap.get(key);
                            if (title.length() > 0) {
                                title += "/";
                            }
                            title += posMdl.getName();
                        }
                    }

                    if (title.length() > 0) {
                        title = "[" + title + "] "; // 枠を付ける
                    }

                    beanList.add(title);
                    selectMap.put(rksKey, beanList);
                } else {
                    if (mdl.getRksRollType() != RngConst.RNG_RNCTYPE_APPL) {
                        // 審議ユーザ一覧から表示するラベルを生成
                        Integer key    = Integer.valueOf(mdl.getUsrSid());
                        if (usrMap.containsKey(key)) {
                            RngCsvUserModel usrMdl = usrMap.get(key);
                            mdl.setSort(usrMdl.getSort());
                            singiList.add(mdl);
                        }
                    }
                }
            }

            String koetuMsg = gsMsg.getMessage("rng.109");       // 後閲
            String dairiMsg = gsMsg.getMessage("cmn.proxyuser"); // 代理人

            // 経路一覧を並び替える条件
            Comparator<RngCsvKeiroStepModel> comparator = new Comparator<RngCsvKeiroStepModel>() {
                @Override
                public int compare(RngCsvKeiroStepModel o1, RngCsvKeiroStepModel o2) {
                    // 稟議SID
                    if (o1.getRngSid() > o2.getRngSid()) {
                        return 1;
                    } else if (o1.getRngSid() < o2.getRngSid()) {
                        return -1;
                    }

                    // 経路種別
                    if (o1.getRksRollType() > o2.getRksRollType()) {
                        return 1;
                    } else if (o1.getRksRollType() < o2.getRksRollType()) {
                        return -1;
                    }

                    // 経路順番
                    if (o1.getRksSort() > o2.getRksSort()) {
                        return 1;
                    } else if (o1.getRksSort() < o2.getRksSort()) {
                        return -1;
                    }

                    // ユーザ順
                    if (o1.getSort() > o2.getSort()) {
                        return 1;
                    } else if (o1.getSort() < o2.getSort()) {
                        return -1;
                    }
                    return 0;
                }
            };

            Collections.sort(singiList, comparator); // 指定された順番に並び替える

            for (RngCsvKeiroStepModel mdl : singiList) {
                Integer rngKey = Integer.valueOf(mdl.getRngSid());
                ArrayList<LabelValueBean> beanList = ret.get(rngKey);
                if (beanList == null) {
                    beanList = new ArrayList<LabelValueBean>();
                }

                // 審議ユーザ一覧から表示するラベルを生成
                Integer key    = Integer.valueOf(mdl.getUsrSid());
                if (usrMap.containsKey(key)) {
                    // 経路ステップ選択ユーザで指定されたグループ名or役職名を取得
                    Integer rksKey = Integer.valueOf(mdl.getRksSid());
                    String title = "";
                    if (selectMap.containsKey(rksKey)) {
                        ArrayList<String> titleList = selectMap.get(rksKey);
                        if (titleList.size() == 1) { // 複数ある場合、グループ名や役職名は付けない
                            title = titleList.get(0);
                        }
                    }

                    // 経路ステータスをチェック
                    if (statusMap.containsKey(rksKey)) {
                        int rksStatus = statusMap.get(rksKey).intValue();
                        if (rksStatus == RngConst.RNG_RNCSTATUS_SKIP) {
                            // スキップの場合のみ審議データのステータスを書き換える
                            mdl.setRksStatus(rksStatus);
                        }
                    }

                    RngCsvUserModel usrMdl = usrMap.get(key);
                    String userName = usrMdl.getName();
                    String status   = mdl.getStatusTitle(gsMsg, isComp);
                    if (mdl.getKouetuUsrSid() > 0) {
                        // 後閲ユーザ
                        Integer subKey = Integer.valueOf(mdl.getKouetuUsrSid());
                        if (usrMap.containsKey(subKey)) {
                            RngCsvUserModel subUsrMdl = usrMap.get(subKey);
                            userName = userName + "(" + koetuMsg + ": "
                                                + subUsrMdl.getName() + ")";
                        }
                    } else if (mdl.getDairiUsrSid() > 0) {
                        // 代理人ユーザ
                        Integer subKey = Integer.valueOf(mdl.getDairiUsrSid());
                        if (usrMap.containsKey(subKey)) {
                            RngCsvUserModel subUsrMdl = usrMap.get(subKey);
                            userName = userName + "(" + dairiMsg + ": "
                                                + subUsrMdl.getName() + ")";
                        }
                    }
                    if (!StringUtil.isNullZeroString(title)) {
                        userName = title + " " + userName;
                    }
                    LabelValueBean bean = new LabelValueBean(userName, status);
                    beanList.add(bean);
                }
                ret.put(rngKey, beanList);
            }
        }
        return ret;
    }

    /**
     * <p>con を取得します。
     * @return con
     */
    public Connection getCon() {
        return con__;
    }
    /**
     * <p>con をセットします。
     * @param con con
     */
    public void setCon(Connection con) {
        con__ = con;
    }

    /**
     * <p>searchModel を取得します。
     * @return searchModel
     */
    public RingiSearchModel getSearchModel() {
        return searchModel__;
    }

    /**
     * <p>searchModel をセットします。
     * @param searchModel searchModel
     */
    public void setSearchModel(RingiSearchModel searchModel) {
        searchModel__ = searchModel;
    }

    /**
     * <p>sessionUserSid を取得します。
     * @return sessionUserSid
     */
    public int getSessionUserSid() {
        return sessionUserSid__;
    }

    /**
     * <p>sessionUserSid をセットします。
     * @param sessionUserSid sessionUserSid
     */
    public void setSessionUserSid(int sessionUserSid) {
        sessionUserSid__ = sessionUserSid;
    }

    /**
     * <p> データを出力する文字列へ変換
     * @param data フォームデータ
     * @param template テンプレート
     * @return CSV出力用のデータ型(KeyValue)で変換された一覧
     */
    private LabelValueBean __convertFormBean(RngFormdataModel data, RingiCsvFormModel template) {
        String key   = "";
        String value = "";

        if (data != null && template != null) {
            key = NullDefault.getString(template.getRtfTitle(), "");

            if (data.getRfdValue() != null) {
                int rngType = template.getRtfType();
                if (rngType == EnumFormModelKbn.textbox.getValue()   // テキスト入力
                 || rngType == EnumFormModelKbn.textarea.getValue()  // テキスト入力(複数行)
                 || rngType == EnumFormModelKbn.date.getValue()      // 日付入力
                 || rngType == EnumFormModelKbn.time.getValue()      // 時間入力
                 || rngType == EnumFormModelKbn.radio.getValue()     // ラジオボタン
                 || rngType == EnumFormModelKbn.combo.getValue()     // コンボボックス
                 || rngType == EnumFormModelKbn.check.getValue()) {  // チェックボックス
                    value = data.getRfdValue();
                } else if (rngType == EnumFormModelKbn.label.getValue()) {     // コメント
                    return null;
                } else if (rngType == EnumFormModelKbn.number.getValue()  // 数値入力
                        || rngType == EnumFormModelKbn.sum.getValue()     // 自動計算（合計）
                        || rngType == EnumFormModelKbn.calc.getValue()) { // 自動計算（その他）
                    value = data.getRfdValue();
                    if (template.getRtfBody() != null) {
                        value += " " + template.getRtfBody(); // 単位
                    }

                } else if (rngType == EnumFormModelKbn.user.getValue()) {
                    // ユーザ選択
                    if (NumberUtils.isNumber(data.getRfdValue())) { // データ型チェック
                        int usid = Integer.valueOf(data.getRfdValue()).intValue();

                        Connection con = getCon();
                        CmnUsrmInfDao userDao = new CmnUsrmInfDao(con);
                        CmnUsrmInfModel usrMdl = null;

                        try {
                            usrMdl = userDao.select(usid);
                        } catch (SQLException e) {
                        }
                        if (usrMdl != null
                         && usrMdl.getUsiSei() != null
                         && usrMdl.getUsiMei() != null) {
                            value = usrMdl.getUsiSei() + " " + usrMdl.getUsiMei();
                        }
                    }
                } else if (rngType == EnumFormModelKbn.group.getValue()) {
                    // グループ選択
                    if (NumberUtils.isNumber(data.getRfdValue())) { // データ型チェック
                        int gsid = Integer.valueOf(data.getRfdValue()).intValue();

                        Connection con = getCon();
                        CmnGroupmDao groupDao = new CmnGroupmDao(con);
                        CmnGroupmModel grpMdl = null;
                        try {
                            grpMdl = groupDao.select(gsid);
                        } catch (SQLException e) {
                        }
                        if (grpMdl != null && grpMdl.getGrpName() != null) {
                            value = grpMdl.getGrpName();
                        }
                    }
                } else if (rngType == EnumFormModelKbn.file.getValue()) {
                    // 添付
                    if (NumberUtils.isNumber(data.getRfdValue())) { // データ型チェック
                        long binSid = Long.valueOf(data.getRfdValue()).longValue();

                        Connection con = getCon();
                        CmnBinfDao binDao = new CmnBinfDao(con);
                        CmnBinfModel binMdl = null;
                        try {
                            binMdl = binDao.getBinInfo(binSid);
                        } catch (TempFileException e) {
                        }
                        if (binMdl != null && binMdl.getBinFileName() != null) {
                            value = binMdl.getBinFileName();
                        }
                    }
                }
            }
        }

        return new LabelValueBean(key, value);
    }
}