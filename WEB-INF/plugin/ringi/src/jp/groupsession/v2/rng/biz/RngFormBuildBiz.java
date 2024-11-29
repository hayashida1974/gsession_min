package jp.groupsession.v2.rng.biz;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.formbuilder.EnumFormModelKbn;
import jp.groupsession.v2.cmn.formbuilder.FormAccesser;
import jp.groupsession.v2.cmn.formbuilder.FormCell;
import jp.groupsession.v2.cmn.formbuilder.FormInputBuilder;
import jp.groupsession.v2.cmn.formmodel.AbstractFormModel;
import jp.groupsession.v2.cmn.formmodel.Block;
import jp.groupsession.v2.cmn.formmodel.CheckBox;
import jp.groupsession.v2.cmn.formmodel.ComboBox;
import jp.groupsession.v2.cmn.formmodel.DateBox;
import jp.groupsession.v2.cmn.formmodel.GroupComboModel;
import jp.groupsession.v2.cmn.formmodel.NumberBox;
import jp.groupsession.v2.cmn.formmodel.RadioButton;
import jp.groupsession.v2.cmn.formmodel.SimpleUserSelect;
import jp.groupsession.v2.cmn.formmodel.Temp;
import jp.groupsession.v2.cmn.formmodel.TextInput;
import jp.groupsession.v2.cmn.formmodel.Textarea;
import jp.groupsession.v2.cmn.formmodel.Textbox;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RtpNotfoundException;
import jp.groupsession.v2.rng.dao.RngChannelTemplateDao;
import jp.groupsession.v2.rng.dao.RngFormdataDao;
import jp.groupsession.v2.rng.dao.RngTemplateDao;
import jp.groupsession.v2.rng.dao.RngTemplateFormDao;
import jp.groupsession.v2.rng.model.RngChannelTemplateModel;
import jp.groupsession.v2.rng.model.RngFormdataModel;
import jp.groupsession.v2.rng.model.RngRndataModel;
import jp.groupsession.v2.rng.model.RngTemplateFormModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;

/**
 * <br>[機  能] 稟議フォーム作成関連汎用ビジネスロジック
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class RngFormBuildBiz {
    /** リクエストモデル */
    RequestModel reqMdl__;

    /**
     * @param reqMdl リクエストモデル
     */
    public RngFormBuildBiz(RequestModel reqMdl) {
        super();
        reqMdl__ = reqMdl;
    }
    /**
     *
     * <br>[機  能] 汎用稟議 の内容要素となるFormCellの生成
     * <br>[解  説]
     * <br>[備  考]
     * @param defValue 初期値
     * @return FormCell
     */
    public FormCell createHanyouRingiNaiyo(String defValue) {
        FormCell cell = new FormCell(
                new LabelValueBean("",
                        EnumFormModelKbn.textarea.toString()));
        cell.setSid(0);
        cell.setFormID(RngConst.RNG_FORMID_HANYOU_NAIYO);
        cell.setTitle("内容");
        cell.setRequire(1);

        TextInput body = (TextInput) cell.getBody();
        body.setDefaultValue(defValue);
        body.setMaxlength(String.valueOf(RngConst.MAX_LENGTH_CONTENT));
        return cell;
    }

    /**
     *
     * <br>[機  能] 汎用稟議 の添付要素となるFormCellの生成
     * <br>[解  説]
     * <br>[備  考]
     * @param samples サンプル用添付ファイル
     * @return FormCell
     */
    public FormCell createHanyouRingiTemp(List<LabelValueBean> samples) {
        FormCell cell = new FormCell(
               new LabelValueBean("",
                       EnumFormModelKbn.file.toString()));
        cell.setSid(1);
        cell.setFormID(RngConst.RNG_FORMID_HANYOU_TEMP);
        cell.setTitle("添付");
        cell.setRequire(0);

        if (samples != null) {
            Temp body = (Temp) cell.getBody();
            body.setSampleList(samples);
        }
       return cell;
   }

    /**
     *
     * <br>[機  能] 汎用稟議でformTableを初期化
     * <br>[解  説]
     * <br>[備  考]
     * @param formBuilder フォームビルダー
     */
    public void setFormHanyouRingi(Block formBuilder) {
        this.setFormHanyouRingi(formBuilder, "", null);
    }

    /**
     *
     * <br>[機  能] 汎用稟議でformTableを初期化
     * <br>[解  説]
     * <br>[備  考]
     * @param formBuilder フォームビルダー
     * @param naiyo   汎用稟議 内容
     * @param samples 汎用稟議 添付ファイルサンプル
     */
    public void setFormHanyouRingi(Block formBuilder, String naiyo, List<LabelValueBean> samples) {
        FormCell cell = createHanyouRingiNaiyo(naiyo);
        FormCell cellTemp = createHanyouRingiTemp(samples);
        Block block = new Block();
        formBuilder.setFormTable(block.getFormTable());
        formBuilder.setFormCell(0, 0, cell);
        formBuilder.setFormCell(1, 0, cellTemp);
    }

    /**
    *
    * <br>[機  能] フォームインプットビルダーに稟議入力情報をロードする
    * <br>[解  説] 稟議モデルのテンプレート情報で表示する
    * <br>[備  考] 編集時、表示時用
    * @param con コネクション
    * @param fb フォームインプットビルダー
    * @param rngMdl 稟議モデル
    * @throws SQLException SQL実行時例外
    */
    public void loadInputData(Connection con, FormInputBuilder fb,
           RngRndataModel rngMdl) throws SQLException {
        loadInputData(con, fb, rngMdl.getRngSid(), rngMdl.getRtpVer(),
                false, rngMdl.getRtpSid(), rngMdl.getRtpVer());
    }
    /**
    *
    * <br>[機  能] フォームインプットビルダーに稟議テンプレート情報をロードする
    * <br>[解  説] 指定したテンプレート情報で表示する
    * <br>[備  考] 複写登録時用
    * @param con コネクション
    * @param fb フォームインプットビルダー
    * @param dspRtpSid 表示するテンプレートSID
    * @param dspRtpVer 表示するテンプレートバージョン
    * @throws SQLException SQL実行時例外
     * @throws RtpNotfoundException テンプレート削除済み例外
    */
    public void loadTenplateData(Connection con, FormInputBuilder fb,
           int dspRtpSid, int dspRtpVer) throws SQLException, RtpNotfoundException {
        if (dspRtpSid > 0) {
            RngTemplateModel model = getRtpModel(con, dspRtpSid,
                    dspRtpVer);
             fb.setFormTable(model.getRtpForm());
        } else {
            setFormHanyouRingi(fb);
        }
    }

    /**
     *
     * <br>[機  能] フォームインプットビルダーに稟議入力情報をロードする
     * <br>[解  説] 指定したテンプレート情報で表示する
     * <br>[備  考] 複写登録時用
     * @param con コネクション
     * @param fb フォームインプットビルダー
     * @param rngSid 稟議SID
     * @param rngRtpVer ロード対象のテンプレートバージョン
     * @param isCopy 複写目的かどうか
     * @param dspRtpSid 表示するテンプレートSID
     * @param dspRtpVer 表示するテンプレートバージョン
     * @throws SQLException SQL実行時例外
     */
    public void loadInputData(Connection con, FormInputBuilder fb,
            int rngSid, int rngRtpVer,
            boolean isCopy,
            int dspRtpSid, int dspRtpVer) throws SQLException {
        //対象テンプレートの最新フォーム情報を読み込み
        Map<String, Integer> rtfIDMap = new HashMap<String, Integer>();
        Map<FormAccesser, List<String>> loadMap =
                new HashMap<FormAccesser, List<String>>();
        //稟議入力情報を読み込み
        RngFormdataDao rfdDao = new RngFormdataDao(con);

        List<RngFormdataModel> allData = rfdDao.select(rngSid, isCopy);
        Set<Integer> initedRfdSids = fb.getDefaultInitedSids();
        if (dspRtpSid == 0) {
            //汎用稟議として出力の場合
            for (RngFormdataModel model : allData) {
                int rfdSid = model.getRfdSid();
                if (RngConst.RNG_FORMID_HANYOU_NAIYO.equals(model.getRfdId())
                        || RngConst.RNG_FORMID_HANYOU_TEMP.equals(model.getRfdId())) {
                    int row = model.getRfdRowno();
                    FormAccesser access = new FormAccesser(rfdSid, row);
                    List<String> list;
                    if (loadMap.containsKey(access)) {
                        list = loadMap.get(access);
                    } else {
                        list = new ArrayList<String>();
                        loadMap.put(access, list);
                    }
                    list.add(model.getRfdValue());
                    initedRfdSids.add(rfdSid);
                }
            }
        } else {

            RngTemplateFormDao rtfDao = new RngTemplateFormDao(con);
            List<RngTemplateFormModel> rtfList
              = rtfDao.select(dspRtpSid, dspRtpVer);
            List<Integer> rtfSidList = new ArrayList<Integer>();
            //対象稟議テンプレートで定義されたIDとSIDのMapを作る
            for (RngTemplateFormModel rtfModel__ : rtfList) {
               rtfSidList.add(rtfModel__.getRtfSid());
               if (!StringUtil.isNullZeroString(rtfModel__.getRtfId())) {
                   rtfIDMap.put(rtfModel__.getRtfId(), rtfModel__.getRtfSid());
               }
            }
            if (isCopy) {
                //旧バージョンからフォームIDで値が引き継ぎ対象となるフォームSIDを取得
                initedRfdSids.addAll(
                        rtfDao.getConvertAbleSidList(dspRtpSid,
                                rngRtpVer,
                                dspRtpVer));
            } else {
                initedRfdSids.addAll(rtfSidList);
            }
            for (RngFormdataModel model : allData) {
                String rfdId = model.getRfdId();
                int rfdSid;
                if (isCopy) {
                    if (StringUtil.isNullZeroString(rfdId)
                            || !rtfIDMap.containsKey(rfdId)) {
                        continue;
                    }
                    rfdSid = rtfIDMap.get(rfdId);
                } else {
                    rfdSid = model.getRfdSid();
                }
                int row = model.getRfdRowno();
                FormAccesser access = new FormAccesser(rfdSid, row);
                List<String> list;
                if (loadMap.containsKey(access)) {
                    list = loadMap.get(access);
                } else {
                    list = new ArrayList<String>();
                    loadMap.put(access, list);
                }
                list.add(model.getRfdValue());
            }
        }
        fb.setDefaultInitedSids(initedRfdSids);
        //読み込んだ稟議入力情報を設定
        for (Entry<FormAccesser, List<String>> entry : loadMap.entrySet()) {
            FormCell cell = fb.getFormBody(entry.getKey());
            List<String> list = entry.getValue();
            cell.setInputedValue(list.toArray(new String[list.size()]));
        }

    }
    /** 稟議テンプレートモデル*/
    private RngTemplateModel rtpMdl__;
    /**
     *
     * <br>[機  能] 稟議テンプレートを取得します。
     * <br>[解  説] キャッシュ機能あり
     * <br>[備  考] 稟議経路テンプレート使用時経路バージョンは経路テンプレートのバージョンが使用されます
     *
     * @param con コネクション
     * @param rtpSid テンプレートSID
     * @param rtpVer テンプレートバージョン
     * @return model
     * @throws SQLException SQL実行時例外
     * @throws RtpNotfoundException テンプレート削除済み例外
     */
    public RngTemplateModel getRtpModel(
            Connection con,
            int rtpSid,
            int rtpVer) throws SQLException, RtpNotfoundException {
        if (rtpSid > 0) {
            if (rtpMdl__ == null
             || rtpMdl__.getRtpSid() != rtpSid
             || rtpMdl__.getRtpVer() != rtpVer) {

                RngTemplateDao rtpDao = new RngTemplateDao(con);
                rtpMdl__ = rtpDao.select(rtpSid, rtpVer);
            }
            if (rtpMdl__ == null) {
                throw new RtpNotfoundException();
            }
            if (rtpMdl__.getRctSid() > 0) {
                // 経路テンプレート使用時 → 経路テンプレートのバージョン取得
                RngChannelTemplateDao rctDao = new RngChannelTemplateDao(con);
                RngChannelTemplateModel rctMdl = rctDao.select(rtpMdl__.getRctSid(), 0);
                if (rctMdl != null) {
                    rtpMdl__.setRctVer(rctMdl.getRctVer());
                }
            }
        } else {
            return new RngTemplateModel();
        }
        return rtpMdl__;
    }
    /**
     *
     * <br>[機  能] 最新バージョンの稟議テンプレートを取得します。
     * <br>[解  説] キャッシュ機能あり（getRtpModelと共通）
     * <br>[備  考] 稟議経路テンプレート使用時経路バージョンは経路テンプレートのバージョンが使用されます
     * @param con コネクション
     * @param rtpSid テンプレートSID
     * @return model
     * @throws SQLException SQL実行時例外
     * @throws RtpNotfoundException テンプレート削除済み例外
     */
    public RngTemplateModel getRtpModelMaxVer(
           Connection con,
           int rtpSid) throws SQLException, RtpNotfoundException {
        if (rtpSid > 0) {
            if (rtpMdl__ != null
                    && rtpMdl__.getRtpSid() == rtpSid
                    && rtpMdl__.getRtpMaxverKbn() == RngTemplateDao.MAXVER_KBN_ON) {
                return rtpMdl__;
            }
            RngTemplateDao rtpDao = new RngTemplateDao(con);
            rtpMdl__ = rtpDao.select(rtpSid, -1);
            if (rtpMdl__ == null) {
                throw new RtpNotfoundException();
            }
            if (rtpMdl__.getRctSid() > 0) {
                // 経路テンプレート使用時 → 経路テンプレートのバージョン取得
                RngChannelTemplateDao rctDao = new RngChannelTemplateDao(con);
                RngChannelTemplateModel rctMdl = rctDao.select(rtpMdl__.getRctSid(), 0);
                if (rctMdl != null) {
                    rtpMdl__.setRctVer(rctMdl.getRctVer());
                }
            }
        } else {
            return new RngTemplateModel();
        }
        return rtpMdl__;
    }

    /**
     *
     * <br>[機  能] テンプレートの異なるフォームビルダー間で入力をコンバートする
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param old コンバート元フォームビルダー
     * @param rtpMdl コンバート先テンプレート情報
     * @param oldMdl コンバート元テンプレート情報
     * @param tempDir 添付ファイルフォルダ
     * @return コンバート後のフォームビルダー
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IO例外
     * @throws IOException IO例外
     */
    public FormInputBuilder convertInputData(Connection con,
            FormInputBuilder old, RngTemplateModel rtpMdl,
            RngTemplateModel oldMdl, GSTemporaryPathModel tempDir)
                    throws SQLException, IOException, IOToolsException {
        //汎用稟議テンプレートにバージョン違いはない
        if (rtpMdl.getRtpSid() <= 0) {
            return old;
        }
        //コンバート元にテンプレート設定をロード
        old.setFormTable(oldMdl.getRtpForm());

        FormInputBuilder desc = new FormInputBuilder();

        Map<String, Integer> rtfIDMap = new HashMap<String, Integer>();
        Map<FormAccesser, List<String>> loadMap =
                new HashMap<FormAccesser, List<String>>();

        RngTemplateFormDao rtfDao = new RngTemplateFormDao(con);
        List<RngTemplateFormModel> rtfList
          = rtfDao.select(rtpMdl.getRtpSid(), rtpMdl.getRtpVer());

        Set<Integer> initedRfdSids = desc.getDefaultInitedSids();

        //対象稟議テンプレートで定義されたIDとSIDのMapを作る
        for (RngTemplateFormModel rtfModel__ : rtfList) {
           if (!StringUtil.isNullZeroString(rtfModel__.getRtfId())) {
               rtfIDMap.put(rtfModel__.getRtfId(), rtfModel__.getRtfSid());
           }
        }

        List<RngTemplateFormModel> oldList
        = rtfDao.select(oldMdl.getRtpSid(), oldMdl.getRtpVer());
        Map<Integer, String > oldSidMap = new HashMap<Integer, String >();
        //対象稟議テンプレートで定義されたSIDのMapを作る
        for (RngTemplateFormModel rtfModel__ : oldList) {
            if (StringUtil.isNullZeroString(rtfModel__.getRtfId())) {
                continue;
            }
            oldSidMap.put(rtfModel__.getRtfSid(), rtfModel__.getRtfId());
        }


        //旧バージョンからフォームIDで値が引き継ぎ対象となるフォームSIDを取得
        initedRfdSids.addAll(
                rtfDao.getConvertAbleSidList(rtpMdl.getRtpSid(),
                        oldMdl.getRtpVer(),
                        rtpMdl.getRtpVer()));
        desc.setDefaultInitedSids(initedRfdSids);


        //稟議添付ファイルの退避
        GSTemporaryPathModel oldTemp = new GSTemporaryPathModel(tempDir, "old");
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();
        List<File> fileList = IOTools.getDirs(tempDir.getTempPath());
        if (fileList != null && fileList.size() > 0) {
            for (File oldFol : fileList) {
                IOTools.copyDir(oldFol,
                        new File(oldTemp.getTempPath() + oldFol.getName()));
                GSTemporaryPathModel delTemp = new GSTemporaryPathModel(tempDir, oldFol.getName());
                pathUtil.deleteTempPath(delTemp);
            }

        }
        //旧テンプレートでの入力値を新テンプレートへ複写
        Map<FormAccesser, FormCell > srcMap = old.getFormMap();
        for (Entry<FormAccesser, FormCell> entry : srcMap.entrySet()) {
            FormAccesser oldAcc = entry.getKey();


            String rfdId = oldSidMap.get(oldAcc.getFormSid());
            if (StringUtil.isNullZeroString(rfdId)
                    || !rtfIDMap.containsKey(rfdId)) {
                continue;
            }
            int row = oldAcc.getRowNo();
            int rfdSid = rtfIDMap.get(rfdId);
            FormAccesser access = new FormAccesser(rfdSid, row);
            List<String> list;
            if (loadMap.containsKey(access)) {
                list = loadMap.get(access);
            } else {
                list = new ArrayList<String>();
                loadMap.put(access, list);
            }

            FormCell cell = entry.getValue();

            //入力内容
            if (cell.getType() != null) {
                EnumFormModelKbn type = cell.getType();
                AbstractFormModel instance = cell.getBody();
                String value = null;

                switch (type) {
                case label:
                    break;
                case textbox:
                    Textbox textbox = (Textbox) instance;
                    value = textbox.getValue();
                    list.add(value);
                    break;
                case textarea:
                    Textarea textarea = (Textarea) instance;
                    value = textarea.getValue();
                    list.add(value);
                    break;
                case date:
                    DateBox dateBox = (DateBox) instance;
                    value = dateBox.getValue();
                    list.add(value);
                    break;
                case number:
                    NumberBox numberBox = (NumberBox) instance;
                    value = numberBox.getValue();
                    list.add(value);
                    break;
                case radio:
                    RadioButton radioButton = (RadioButton) instance;
                    value = radioButton.getSelected();
                    list.add(value);
                    break;
                case combo:
                    ComboBox comboBox = (ComboBox) instance;
                    value = comboBox.getSelected();
                    list.add(value);
                    break;
                case check:
                    CheckBox checkBox = (CheckBox) instance;
                    if (checkBox.getSelected() != null) {
                        list.addAll(Arrays.asList(checkBox.getSelected()));
                    }
                    break;
                case sum:
                    break;
                case calc:
                    break;
                case user:
                    SimpleUserSelect simple = (SimpleUserSelect) instance;
                    if (simple.getSelected() != null) {
                        list.addAll(Arrays.asList(simple.getSelected()));
                    }
                    break;
                case group:
                    GroupComboModel group = (GroupComboModel) instance;
                    if (group.getSelected() != null) {
                        list.addAll(Arrays.asList(group.getSelectedNoselisVoid()));
                    }
                    break;
                case file:
                    Temp temp = (Temp) instance;
                    GSTemporaryPathModel srcPath = new GSTemporaryPathModel(oldTemp,
                            temp.createTempFolderName(oldAcc.getFormSid(), temp.getBlockIdx()));

                    GSTemporaryPathModel destPath = new GSTemporaryPathModel(tempDir
                            , temp.createTempFolderName(rfdSid, row));

                    File destFile = new File(destPath.getTempPath());
                    pathUtil.createTempDir(destPath);
                    IOTools.copyDir(new File(srcPath.getTempPath()),
                            destFile);
                    pathUtil.deleteTempPath(srcPath);

                    break;
                default:
                  break;
                }
            }
        }
        //稟議添付ファイル退避フォルダの削除
        pathUtil.deleteTempPath(oldTemp);

        //読み込んだ稟議入力情報を設定
        for (Entry<FormAccesser, List<String>> entry : loadMap.entrySet()) {
            FormCell cell = desc.getFormBody(entry.getKey());
            List<String> list = entry.getValue();
            cell.setInputedValue(list.toArray(new String[list.size()]));

        }
        return desc;
    }




}
