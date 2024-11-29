package jp.groupsession.v2.cmn.formbuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSConst;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.cmn999.Cmn999Form;
import jp.groupsession.v2.cmn.dao.base.CmnBinfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.formmodel.AbstractFormModel;
import jp.groupsession.v2.cmn.formmodel.Block;
import jp.groupsession.v2.cmn.formmodel.BlockList;
import jp.groupsession.v2.cmn.formmodel.IAutoCalc;
import jp.groupsession.v2.cmn.formmodel.Temp;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBinfModel;

/**
 * <br>[機  能] フォームビルダーで作成した申請要素クラス
 * <br>[解  説]
 * <br>[備  考] モバイルで使用する画面でユーザ選択からの戻りの画面遷移のためstrutsconfigにMbhCmn120、MbhCmn210画面からのforwardを設定すること
 *
 * @author JTS
 */
public final class FormInputBuilder extends Block {
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FormInputBuilder.class);

    /**初期化モード 入力目的*/
    public static final int INITMODE_INPUT = 0;
    /**初期化モード 表示目的*/
    public static final int INITMODE_DSP = 1;
    /**初期化モード プレビュー(入力状態で表示のみ)*/
    public static final int INITMODE_PREVIEW = 2;

    /**申請要素画面 設定モデル*/
    private FormInputInitPrefarence initPrefarence__;


    /**
      *  ブロック要素の管理Index
      **/
    private int mstBlockIdx__ = 0;

    /** フォーム入力要素 フォームアクセサクラスをキーとするMap
     *  HTMLリクエストから初期化させ、画面の入力値を保管する
     *  DB保管された入力値もこちらに展開する
     **/
    private Map<FormAccesser, FormCell> formMap__
        = new HashMap<FormAccesser, FormCell>();

    /** フォーム要素Map フォームアクセサクラスをキーとするMap
     *  ロードしたテンプレートから展開されたフォームをストックしておく
     **/
    private Map<FormAccesser, FormCell> formInputMap__
        = new HashMap<FormAccesser, FormCell>();


    /** ブロックリスト（表）のボディの子要素のフォームSIDをキーに親ブロックリストへの参照を保持するMap*/
    private Map<Integer, FormCell> blockListBelongMap__
        = new HashMap<Integer, FormCell>();

    /** フォームIDをキーに要素リストを保持するMap*/
    private Map<String, List<FormCell>> idFormListMap__
        = new HashMap<String, List<FormCell>>();

    /** フォームIDをキーに要素リストを保持するMap*/
    private Map<String, List<FormAccesser>> idAccessListMap__
        = new HashMap<String, List<FormAccesser>>();

    /** ロードによる初期化済みSIDリスト*/
    private Set<Integer> defaultInitedSids__ = new HashSet<Integer>();

    /** フォームの値計算用ビジネスロジッククラス dspInit実行以前はnull */
    FormCalcBiz calcBiz__ = null;

    /** 入力要素内に自動計算要素を持つかどうか */
    boolean haveAutoCalc__ = false;

    /**
     * <p>inputMap を取得します。
     * @return inputMap
     */
    public Map<FormAccesser, FormCell> getFormMap() {
        return formMap__;
    }

    /**
     * <p>inputMap をセットします。
     * @param inputMap inputMap
     */
    public void setFormMap(Map<FormAccesser, FormCell> inputMap) {
        formMap__ = inputMap;
    }


    /**
     * <p>mstBlockIdx をセットします。
     * @param mstBlockIdx mstBlockIdx
     */
    public void setMstBlockIdx(int mstBlockIdx) {
        mstBlockIdx__ = mstBlockIdx;
    }

    /**
     * <p>mstBlockIdx を取得します。
     * @return mstBlockIdx
     */
    public int getMstBlockIdx() {
        return mstBlockIdx__;
    }

    /**
     * <p>inputMap を取得します。
     * @param accessStr アクセスキー文字列
     * @return inputMap
     */
    public AbstractFormModel getForm(String accessStr) {
        String[] accessKey = accessStr.split("_");
        if (accessKey.length != 3) {
            return null;
        }
        FormAccesser access = new FormAccesser(
                Integer.parseInt(accessKey[0]),
                Integer.parseInt(accessKey[1]));
        FormCell cell = getFormBody(access);
        return cell.getBody(accessKey[2]);
    }
    /**
     * <p>inputMap を取得します。
     * @param access アクセサクラス
     * @return inputMap
     */
    public FormCell getFormBody(FormAccesser access) {
        FormCell body = null;
        if (formMap__.containsKey(access)) {
            body =  formMap__.get(access);
        } else {
            body = new FormCell();
            body.setSid(access.getFormSid());
            formMap__.put(access, body);
        }
        return body;
    }
    @Override
    public void setFormTable(List<List<FormCell>> formTable) {
        super.setFormTable(formTable);
        formInputMap__ = new HashMap<FormAccesser, FormCell>();
        makeFormMap(formInputMap__, 0);
    }
    /**
     * 要素追加時に
     * 要素リストを追加する
     */
    @Override
    public void setFormCell(int row, int col, FormCell cell) {
        super.setFormCell(row, col, cell);
        if (cell.getBody() instanceof Block) {
            Block block = (Block) cell.getBody();
            setAccessIndex(block.getChildMap());
        }
        if (cell.getBody() instanceof BlockList) {

            BlockList block = (BlockList) cell.getBody();
            Map<FormAccesser, FormCell> children
                = new HashMap<FormAccesser, FormCell>();
            block.getBody(0).makeFormMap(children, 0);
            for (Entry<FormAccesser, FormCell> entry : children.entrySet()) {
                FormAccesser access = entry.getKey();
                blockListBelongMap__.put(access.getFormSid(), cell);
            }
            //bodyリストを空にしておく
            block.getBodyList().clear();
            setAccessIndex(block.getHeader().getChildMap());
            setAccessIndex(block.getFooter().getChildMap());
        }
        formInputMap__.put(new FormAccesser(cell.getSid(), 0), cell);
    }
    @Override
    public void dspInit(RequestModel reqMdl, Connection con) throws SQLException, IOToolsException {

        if (initPrefarence__ == null) {
            return;
        }
        boolean isLoad = initPrefarence__.isLoad();


        __mergeInput(isLoad);

        calcBiz__ = FormCalcBiz.getInstance(getFormIDAccessMap());
        super.dspInit(reqMdl, con);

        __dspInitTempFile(reqMdl, con);

        __dspInitTempSample(reqMdl, con);
    }
    /**
     *
     * <br>[機  能] 添付ファイル サンプルリスト生成
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @throws SQLException
     */
    private void __dspInitTempSample(RequestModel reqMdl, Connection con) throws SQLException {
        int mode = initPrefarence__.getMode();

        if (mode == INITMODE_PREVIEW) {
            return;
        }

        CommonBiz cmnBiz = new CommonBiz();

        // テンプレートで登録されたサンプルファイル一覧を取得
        HashMap<Long, FormAccesser> sampleLoadMap = new HashMap<Long, FormAccesser>();
        for (Entry<FormAccesser, FormCell> entry : formInputMap__.entrySet()) {
            FormCell cell = entry.getValue();
            FormAccesser access = entry.getKey();
            if (cell.getType() != null
                && cell.getType() == EnumFormModelKbn.file) {
                // サンプルダウンロード用のバイナリSIDをセットする
                Temp temp = (Temp) cell.getBody();
                temp.getSampleList().clear(); // 重複しないよう配列を初期化
                if (temp.getSample() != null && access.getRowNo() == 0) {
                    // サンプルは表の場合、1行目のみ
                    for (String binSid : temp.getSample()) {
                        sampleLoadMap.put(Long.valueOf(binSid), access);
                    }
                }
            }
        }

        // サンプルファイル一覧のファイル情報取得(実データなし)
        List<CmnBinfModel> sampleMdlList =
                __getBinaryList(reqMdl, con, sampleLoadMap, false);
        if (sampleMdlList != null) {
            for (CmnBinfModel binMdl : sampleMdlList) {
                if (binMdl.getBinJkbn() != GSConst.JTKBN_TOROKU) {
                    continue;
                }
                FormAccesser access = sampleLoadMap.get(binMdl.getBinSid());
                FormCell cell = formInputMap__.get(access);
                if (cell != null && cell.getType() != null
                     && cell.getType() == EnumFormModelKbn.file) {
                    Temp temp = (Temp) cell.getBody();
                    LabelValueBean bean = new LabelValueBean(
                            binMdl.getBinFileName() + cmnBiz.getByteSizeString(binMdl.getBinFileSize()),
                            binMdl.getBinSid().toString());
                    temp.getSampleList().add(bean);
                }
            }
        }
    }

    /**
     *
     * <br>[機  能] 添付ファイル ファイルリスト生成
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl
     * @param con
     * @throws SQLException
     */
    private void __dspInitTempFile(RequestModel reqMdl, Connection con) throws SQLException {
        HashMap<Long, FormAccesser> binLoadMap    = new HashMap<Long, FormAccesser>();
        boolean isLoad = initPrefarence__.isLoad();
        int mode = initPrefarence__.getMode();
        String appRoot = initPrefarence__.getAppRoot();

        for (Entry<FormAccesser, FormCell> entry:formInputMap__.entrySet()) {
            FormCell cell = entry.getValue();
            FormAccesser access = entry.getKey();
            if (cell.getType() == null) {
                continue;
            }
            if (cell.getType() == EnumFormModelKbn.file) {
                Temp temp = (Temp) cell.getBody();
                // 添付ファイル読込み時のみ配列へバイナリSID一覧をセットする
                if (temp.getBinSids() != null) {
                    for (Long binSid : temp.getBinSids()) {
                        binLoadMap.put(binSid, access);
                    }
                }
            }
        }
        if (mode == INITMODE_INPUT && isLoad) {
            CommonBiz cmnBiz = new CommonBiz();
            // 読み込むファイルのバイナリSID一覧からファイル情報取得(実データあり)
            List<CmnBinfModel> binMdlList = __getBinaryList(reqMdl, con, binLoadMap, true);

            if (binMdlList == null) {
                return;
            }
            // ファイル情報から実ファイルを指定された一時保存フォルダへ格納する
            int    fileNum = 1;           // テンポラリディレクトリ内で割り振るファイル番号(連番)
            String dateStr = (new UDate()).getDateString(); // ファイル名に試用する現在日付の文字列

            for (CmnBinfModel binMdl : binMdlList) {
                if (binMdl.getBinJkbn() != GSConst.JTKBN_TOROKU) {
                    continue;
                }
                FormAccesser access = binLoadMap.get(binMdl.getBinSid());
                FormCell cell = formInputMap__.get(access);
                if (cell != null && cell.getType() != null
                   && cell.getType() == EnumFormModelKbn.file) {
                    Temp temp = (Temp) cell.getBody();

                    try {
                        GSTemporaryPathModel tempPath = temp.getTempPath();
                        if (tempPath == null) {
                            continue;
                        }

                        cmnBiz.saveTempFile(dateStr, binMdl, appRoot,
                                            tempPath.getTempPath(), fileNum);
                        String fileName = binMdl.getBinFileName();
                        LabelValueBean bean = new LabelValueBean(
                                fileName,
                                dateStr + StringUtil.toDecFormat(fileNum, "000"));
                        temp.getFileList().add(bean);

                        fileNum++;
                    } catch (TempFileException | IOException | IOToolsException e) {
                        log__.error("添付ファイル生成に失敗", e);
                    }
                }
            }
        } else {
         // 読み込むファイルのバイナリSID一覧からファイル情報取得(実データなし)
            List<CmnBinfModel> binMdlList = __getBinaryList(reqMdl, con, binLoadMap, false);
            if (binMdlList == null) {
                return;
            }
            for (CmnBinfModel binMdl : binMdlList) {
                if (binMdl.getBinJkbn() != GSConst.JTKBN_TOROKU) {
                    continue;
                }
                FormAccesser access = binLoadMap.get(binMdl.getBinSid());
                FormCell cell = formInputMap__.get(access);
                if (cell != null && cell.getType() != null
                     && cell.getType() == EnumFormModelKbn.file) {
                    Temp temp = (Temp) cell.getBody();
                    String fileName = binMdl.getBinFileName()
                            + "("
                            + CommonBiz.formatByteSizeString(binMdl.getBinFileSize())
                            + ")";
                    LabelValueBean bean = new LabelValueBean(
                            fileName,
                            binMdl.getBinSid().toString());
                    temp.getFileList().add(bean);
                }
            }
        }
    }

    /**
     *
     * <br>[機  能] 添付ファイルのバイナリ情報一覧取得
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param loadMap 読み込むファイルのバイナリキー一覧
     * @param isData ファイルのバイナリデータ読込みフラグ
     * @throws SQLException SQL実行時例外
     * @return バイナリ情報一覧
     */
    private List<CmnBinfModel> __getBinaryList(RequestModel reqMdl, Connection con,
                                                HashMap<Long, FormAccesser> loadMap, boolean isData)
                                                throws SQLException {
        // 読み込むファイルのバイナリSID一覧を取得
        Set<Long> binSidSet = loadMap.keySet();
        String[] binSids = new String[binSidSet.size()];
        int i = 0;
        for (Long binSid : binSidSet) {
            binSids[i] = binSid.toString();
            i++;
        }

        // バイナリSID一覧からファイル情報取得
        List<CmnBinfModel> binMdlList    = null;
        if (binSids.length > 0) {
            CmnBinfDao binDao = new CmnBinfDao(con);
            if (isData) {
                try {
                    CommonBiz cmnBiz = new CommonBiz();
                    binMdlList = cmnBiz.getBinInfo(con, binSids, reqMdl.getDomain());
                } catch (TempFileException e) {
                }
            } else {
                //取得したバイナリSIDからバイナリ情報を取得
                binMdlList = binDao.select(binSids);
            }
        }
        return binMdlList;
    }

    /**
     *
     * <br>[機  能] フォーム入力値を反映
     * <br>[解  説] テンプレートを展開したフォーム情報に、入力情報をマージする
     * <br>[備  考]
     * @param isDefault 初期化フラグ
     */
    private void __mergeInput(boolean isDefault) {
        Map<FormAccesser, FormCell> formInputMap = formInputMap__;
        /** 入力情報に含まれる表要素を格納 */
        Map<Integer, BlockList> blockMap = new HashMap<Integer, BlockList>();

        for (Entry<FormAccesser, FormCell> entry
                : formMap__.entrySet()) {
            FormAccesser access = entry.getKey();
            FormCell cell = formInputMap.get(access);
            if (blockListBelongMap__.containsKey(access.getFormSid())) {
                //表の要素内の場合

                BlockList parent = (BlockList) blockListBelongMap__
                        .get(access.getFormSid()).getBody();
                blockMap.put(blockListBelongMap__
                        .get(access.getFormSid()).getSid(), parent);

                //BlockList.getBody(行番号)でアクセス時に初期化される
                Block row = parent.getBody(access.getRowNo());
                cell = row.getFormCell(access);
                if (cell == null) {
                    continue;
                }
                if (entry.getValue() != null) {
                    cell.merge(entry.getValue(), row.getRowNo());
                }
                formInputMap.put(access, cell);

            } else {
                if (cell == null) {
                    continue;
                }
                cell.merge(entry.getValue(), 0); // 表ボディ以外では常に行番号=0
                formInputMap.put(access, cell);
            }
        }

        if (isDefault) {
            //ブロックリスト、ボディ部初期化処理
            for (Entry<Integer, BlockList> entry
                    : blockMap.entrySet()) {
                BlockList bl = entry.getValue();
                for (int i = 0; i < bl.getLength(); i++) {
                    Block block = bl.getBody(i);

                    Map<FormAccesser, FormCell> map = block.getChildMap();

                    //フォームの初期値設定
                    for (Entry<FormAccesser, FormCell> entryBody
                            : new HashSet<Entry<FormAccesser, FormCell>>(map.entrySet())) {
                        FormCell cell = entryBody.getValue();
                        cell.getBody().setBlockIdx(i);
                        if (!defaultInitedSids__.contains(entryBody.getKey().getFormSid())) {
                            AbstractFormModel form = cell.getBody();
                            form.defaultInit();
                        }
                    }
                }
                bl.setMstBlockIdx(bl.getLength());
            }
        }
        formMap__ = formInputMap;

    }
    @Override
    public void makeFormMap(Map<FormAccesser, FormCell> target, int rowNo) {
        Map<FormAccesser, FormCell> childMap = getChildMap();
        if (target == null) {
            return;
        }
        for (Entry<FormAccesser, FormCell> entry : childMap.entrySet()) {
            FormAccesser access = entry.getKey();
            if (target.containsKey(access)) {
                continue;
            }
            FormCell cell = entry.getValue();
            target.put(access,
                    cell);
            if (cell.getBody() instanceof IFormAccessMapMakable) {
                IFormAccessMapMakable block = (IFormAccessMapMakable) cell.getBody();
                block.makeFormMap(target, 0);
            }
        }
    }
    /**
     *
     * <br>[機  能] root要素へ子要素の参照を保管する
     * <br>[解  説]
     * <br>[備  考]
     * @param map 子要素マップ
     */
    public void setAccessIndex(Map<FormAccesser, FormCell> map) {
        if (map == null) {
            return;
        }
        for (Entry<FormAccesser, FormCell> entry : map.entrySet()) {
            setAccessIndex(entry.getKey(),
                    entry.getValue());
        }
    }

    @Override
    public void setAccessIndex(FormAccesser accsess, FormCell cell) {
        if (cell != null) {
            if (accsess != null) {
                if (formInputMap__.containsKey(accsess)) {
                    return;
                }
                formInputMap__.put(accsess, cell);
            }
            String formId = cell.getFormID();
            if (!StringUtil.isNullZeroString(formId)) {
                List<FormCell> list;
                if (!idFormListMap__.containsKey(formId)) {
                    list = new ArrayList<FormCell>();
                    idFormListMap__.put(formId, list);
                } else {
                    list = idFormListMap__.get(formId);
                }
                list.add(cell);
                List<FormAccesser> acslist;
                if (!idAccessListMap__.containsKey(formId)) {
                    acslist = new ArrayList<FormAccesser>();
                    idAccessListMap__.put(formId, acslist);
                } else {
                    acslist = idAccessListMap__.get(formId);
                }
                acslist.add(accsess);
            }
            //要素が自動計算要素を含む
            if (cell.getBody() instanceof IAutoCalc) {
                haveAutoCalc__ = true;
            }
        }
    }
    /**
     *
     * <br>[機  能] フォームIDがキーの要素への参照マップを返します
     * <br>[解  説]
     * <br>[備  考]
     * @return フォームIDがキーの要素への参照マップ
     */
    public Map<String, List<FormCell>> getFormIDAccessMap() {

        return idFormListMap__;
    }
    @Override
    public Map<FormAccesser, FormCell> getRootAccessMap() {
        return formInputMap__;
    }
    @Override
    public FormCalcBiz getFormCalcBiz() {
        return calcBiz__;
    }
    /**
     * <p>initPrefarence を取得します。
     * @return initPrefarence
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputBuilder#initPrefarence__
     */
    public FormInputInitPrefarence getInitPrefarence() {
        return initPrefarence__;
    }

    /**
     * <p>initPrefarence をセットします。
     * @param initPrefarence initPrefarence
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputBuilder#initPrefarence__
     */
    public void setInitPrefarence(FormInputInitPrefarence initPrefarence) {
        initPrefarence__ = initPrefarence;

    }
    @Override
    protected IFormAccessMapMakable _getRoot() {
        return this;
    }
    @Override
    public FormInputInitPrefarence getInitPref() {
        return initPrefarence__;
    }
    /**
     *
     * <br>[機  能] 指定フォームIDの集計結果を返す
     * <br>[解  説]
     * <br>[備  考] 数字入力、自動計算フォームの場合は合計、それ以外の場合は最初の一要素の入力値
     * @param formID フォームID
     * @return 計算結果
     */
    public String calcedResult(String formID) {

        if (calcBiz__ == null || !idFormListMap__.containsKey(formID)) {
            return "";
        }
        List<FormCell> list = idFormListMap__.get(formID);
        EnumFormModelKbn type = null;
        BigDecimal bd = BigDecimal.ZERO;
        for (FormCell cell : list) {
            if (type == null) {
                type = cell.getType();
            }
            if (type == null) {
                continue;
            }
            String ret = cell.getBody().getCalced();
            switch (type) {
                case number:
                case calc:
                case sum:
                    bd = bd.add(new BigDecimal(ret));
                    break;
                default:
                    return ret;
            }
        }
        return bd.toPlainString();
    }
    /**
    *
    * <br>[機  能] 指定フォームIDの集計結果との比較結果を返す
    * <br>[解  説]
    * <br>[備  考] 数字入力、自動計算フォームの場合は合計、それ以外の場合は最初の一要素の入力値
    * @param formID フォームID
    * @param targetValue 比較対象値
    * @param opr 比較子
    * @return 計算結果
    */
    public boolean oprResult(String formID, String targetValue, EnumCompOpr opr) {
        if (calcBiz__ == null) {
            return false;
        }
        if (!idFormListMap__.containsKey(formID)) {
            return false;
        }
        List<FormCell> list = idFormListMap__.get(formID);
        EnumFormModelKbn type = null;
        BigDecimal bd = BigDecimal.ZERO;
        for (FormCell cell : list) {
            if (type == null) {
                type = cell.getType();
            }
            if (type == null) {
                continue;
            }
            String ret = cell.getBody().getCalced();
            switch (type) {
                case number:
                case calc:
                case sum:
                    try {
                        bd = bd.add(new BigDecimal(ret));
                    } catch (NumberFormatException e) {
                    }
                    break;
                case user:
                case group:
                    return false;
                case check:
                    return false;
                default:
                    return __oprResultString(ret, targetValue, opr);
            }
        }
        BigDecimal val2 =  BigDecimal.ZERO;
        try {
            val2 = new BigDecimal(targetValue);
        } catch (NumberFormatException e) {
            return false;
        }
        return __oprResultDecimal(bd, val2, opr);
    }
    /**
     *
     * <br>[機  能] 比較子にもとづいて比較する
     * <br>[解  説]
     * <br>[備  考]
     * @param val1 左辺値
     * @param val2 右辺値
     * @param opr 比較子
     * @return 結果
     */
    private boolean __oprResultString(String val1, String val2, EnumCompOpr opr) {
        if (opr == null) {
            return false;
        }
        switch (opr) {
        case EQ:
            return Objects.equals(val1, val2);
        case LE:
            return false;
        case LO:
            return false;
        case OE:
            return false;
        case OV:
            return false;
        default:
            return false;
        }
    }
    /**
     *
     * <br>[機  能] 比較子にもとづいて比較する
     * <br>[解  説]
     * <br>[備  考]
     * @param val1 左辺値
     * @param val2 右辺値
     * @param opr 比較子
     * @return 結果
     */
    private boolean __oprResultDecimal(BigDecimal val1, BigDecimal val2, EnumCompOpr opr) {
        if (opr == null) {
            return false;
        }
        switch (opr) {
        case EQ:
            return val1.equals(val2);
        case LE:
            if (val1.compareTo(val2) <= 0) {
                return true;
            }
            return false;
        case LO:
            if (val1.compareTo(val2) < 0) {
                return true;
            }
            return false;
        case OE:
            if (val1.compareTo(val2) >= 0) {
                return true;
            }
            return false;
        case OV:
            if (val1.compareTo(val2) > 0) {
                return true;
            }
            return false;
        default:
            return false;
        }
    }
    /**
     *
     * <br>[機  能] フォームIDリストから一致するフォームアクセサリストを返す
     * <br>[解  説] IDで絞り込んだフォームMapを返す
     * <br>[備  考]
     * @param formIds フォームIDリスト
     * @return フォームアクセサリスト
     */
    public Map<FormAccesser, FormCell> getFormMapFromID(List<String> formIds) {
        List<FormAccesser> acsList = new ArrayList<FormAccesser>();
        for (String formId : formIds) {
            if (idAccessListMap__.containsKey(formId)) {
                acsList.addAll(idAccessListMap__.get(formId));
            }
        }
        HashMap<FormAccesser, FormCell> ret = new HashMap<FormAccesser, FormCell>();
        for (ListIterator<FormAccesser> itr = acsList.listIterator();
                itr.hasNext();) {
            FormAccesser acs = itr.next();
            if (formInputMap__.containsKey(acs)) {
                ret.put(acs, formInputMap__.get(acs));
            }
        }
        return ret;
    }
    @Override
    public void setHiddenParam(Cmn999Form msgForm, String paramName) {
        for (Entry<FormAccesser, FormCell> entry:formInputMap__.entrySet()) {
            FormCell cell = entry.getValue();
            FormAccesser access = entry.getKey();
            StringBuilder sb = new StringBuilder();
            sb.append(paramName);
            sb.append(".form(");
            sb.append(access.getFormSid());
            sb.append("_");
            sb.append(access.getRowNo());
            sb.append("_");
            sb.append(cell.getType());
            sb.append(")");
            String prefix = sb.toString();

            cell.getBody().setHiddenParam(msgForm, prefix);
        }
    }

    /**
     * <p>defaultInitedSids を取得します。
     * @return defaultInitedSids
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputBuilder#defaultInitedSids__
     */
    public Set<Integer> getDefaultInitedSids() {
        return defaultInitedSids__;
    }

    /**
     * <p>defaultInitedSids をセットします。
     * @param defaultInitedSids defaultInitedSids
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputBuilder#defaultInitedSids__
     */
    public void setDefaultInitedSids(Set<Integer> defaultInitedSids) {
        defaultInitedSids__ = defaultInitedSids;
    }
    @Override
    public void defaultInit() {
        List<Integer> defInitedBlockSidList = new ArrayList<Integer>();
        Map<Integer, BlockList> blockMap = new HashMap<Integer, BlockList>();
        for (Entry<FormAccesser, FormCell> entry
                : formInputMap__.entrySet()) {
            FormAccesser access = entry.getKey();
            FormCell cell = entry.getValue();
            //ブロックリスト要素は、ボディ内要素の初期化の後、ボディ内要素の初期化がないもののみ行数を初期化する
            if (cell.getType() == EnumFormModelKbn.blocklist) {
                blockMap.put(access.getFormSid(), (BlockList) cell.getBody());
                continue;
            }
            //ブロック要素は、ブロック内要素ごとに初期化判定があるので、
            //ブロック要素そのものの初期化は不要
            if (cell.getType() == EnumFormModelKbn.block) {
                continue;
            }
            if (!defaultInitedSids__.contains(access.getFormSid())) {
                cell.getBody().defaultInit();
            }
        }
        for (Integer formSid : defaultInitedSids__) {
            if (blockListBelongMap__.containsKey(formSid)) {
                FormCell cell = blockListBelongMap__.get(formSid);
                if (blockMap.containsKey(cell.getSid())) {
                    blockMap.remove(cell.getSid());
                }
            }
        }
        //ブロックリスト、ボディ部初期化処理
        for (Entry<Integer, BlockList> entry
                : blockMap.entrySet()) {
            int sid = entry.getKey();
            BlockList bl = entry.getValue();
            if (!defInitedBlockSidList.contains(sid)) {
                for (int i = 0; i < bl.getDefLength(); i++) {
                    Block block = bl.getBody(i);
                    block.defaultInit();
                    block.setBlockIdx(i);
                }
            }
        }
    }
    /**
     * <p>haveAutoCalc を取得します。
     * @return haveAutoCalc
     * @see jp.groupsession.v2.cmn.formbuilder.FormInputBuilder#haveAutoCalc__
     */
    public boolean isHaveAutoCalc() {
        return haveAutoCalc__;
    }
    /**
     *
     * <br>[機  能] 利用時に必須入力条件を満たすことができない申請内容がないことをチェックします
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @return Errors 入力できない申請内容がある場合
     */
    public ActionErrors chkUnuseableInput(RequestModel reqMdl) {
        ActionErrors errors = new ActionErrors();
        ValidateInfo info = new ValidateInfo();

        Map<FormAccesser, FormCell> map = getChildMap();
        for (Entry<FormAccesser, FormCell> entry
                : new HashSet<Entry<FormAccesser, FormCell>>(map.entrySet())) {
            FormCell cell = entry.getValue();
            cell.chkUnuseableInput(errors, reqMdl, info);
        }
        return errors;

    }

}
