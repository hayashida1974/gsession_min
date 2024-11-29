package jp.groupsession.v2.rng.rng020;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.EnumUtil.EnumOutRangeException;
import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.ValidateUtil;
import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.date.UDateUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.biz.GroupBiz;
import jp.groupsession.v2.cmn.biz.PosBiz;
import jp.groupsession.v2.cmn.biz.UserGroupSelectBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.dao.BaseUserModel;
import jp.groupsession.v2.cmn.dao.GroupModel;
import jp.groupsession.v2.cmn.dao.MlCountMtController;
import jp.groupsession.v2.cmn.dao.UsidSelectGrpNameDao;
import jp.groupsession.v2.cmn.dao.base.CmnBelongmDao;
import jp.groupsession.v2.cmn.dao.base.CmnUsrmInfDao;
import jp.groupsession.v2.cmn.exception.TempFileException;
import jp.groupsession.v2.cmn.formbuilder.EnumCompOpr;
import jp.groupsession.v2.cmn.formbuilder.FormAccesser;
import jp.groupsession.v2.cmn.formbuilder.FormCell;
import jp.groupsession.v2.cmn.formbuilder.FormInputBuilder;
import jp.groupsession.v2.cmn.formbuilder.FormInputInitPrefarence;
import jp.groupsession.v2.cmn.formmodel.UserGroupSelectModel;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnBelongmModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupClassModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.rng.RngConst;
import jp.groupsession.v2.rng.RtpNotfoundException;
import jp.groupsession.v2.rng.biz.RngBiz;
import jp.groupsession.v2.rng.biz.RngFormBuildBiz;
import jp.groupsession.v2.rng.biz.RngUsedDataBiz;
import jp.groupsession.v2.rng.dao.RngCopyKeiroStepDao;
import jp.groupsession.v2.rng.dao.RngCopyKeirostepSelectDao;
import jp.groupsession.v2.rng.dao.RngKeiroStepDao;
import jp.groupsession.v2.rng.dao.RngKeiroStepDao.SearchModel;
import jp.groupsession.v2.rng.dao.RngKeirostepSelectDao;
import jp.groupsession.v2.rng.dao.RngRndataDao;
import jp.groupsession.v2.rng.dao.RngTemplateBinDao;
import jp.groupsession.v2.rng.dao.RngTemplateCategoryDao;
import jp.groupsession.v2.rng.dao.RngTemplateDao;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroDao.SearchParamForRTP;
import jp.groupsession.v2.rng.dao.RngTemplateKeiroUserDao;
import jp.groupsession.v2.rng.dao.RngTemplatecategoryAdmDao;
import jp.groupsession.v2.rng.dao.RngTemplatecategoryUseDao;
import jp.groupsession.v2.rng.model.RingiIdModel;
import jp.groupsession.v2.rng.model.RingiRequestModel;
import jp.groupsession.v2.rng.model.RngAconfModel;
import jp.groupsession.v2.rng.model.RngCopyKeiroStepModel;
import jp.groupsession.v2.rng.model.RngCopyKeirostepSelectModel;
import jp.groupsession.v2.rng.model.RngKeiroStepModel;
import jp.groupsession.v2.rng.model.RngKeirostepSelectModel;
import jp.groupsession.v2.rng.model.RngRndataModel;
import jp.groupsession.v2.rng.model.RngTemplateCategoryModel;
import jp.groupsession.v2.rng.model.RngTemplateKeiroModel;
import jp.groupsession.v2.rng.model.RngTemplateKeiroUserModel;
import jp.groupsession.v2.rng.model.RngTemplateModel;
import jp.groupsession.v2.rng.rng030.Rng030Biz;
import jp.groupsession.v2.rng.rng110keiro.EnumKeiroInConditionKbn;
import jp.groupsession.v2.rng.rng110keiro.EnumKeiroKbn;
import jp.groupsession.v2.rng.rng110keiro.KeiroInCondition;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogForm.TargetPosSel;
import jp.groupsession.v2.rng.rng110keiro.Rng110KeiroDialogParamModel;
import jp.groupsession.v2.rng.rng110keiro.RngTemplateKeiroSave;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 * <br>[機  能] 稟議作成画面のビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng020Biz {
    /** Loggingインスタンス */
    private static Log log__ = LogFactory.getLog(Rng020Biz.class);

    /** サンプル用添付ファイルダウンロードURL */
    private static final String TEMPFILE_DOWNLOAD_URL =
    "../ringi/rng020.do?CMD=templateFileDownload&rng020TemplateFileId={binSid}&rng020rtpSid=";
    /** コネクション */
    protected Connection con__ = null;
    /** リクエスト情報 */
    protected RequestModel reqMdl__ = null;
    /** セッションユーザSID */
    private int usrSid__;
    /** 稟議フォーム展開ビジネスロジッククラス*/
    private RngFormBuildBiz formBiz__;



    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエスト情報
     */
    public Rng020Biz(RequestModel reqMdl) {
        reqMdl__ = reqMdl;
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     */
    public Rng020Biz(Connection con, RequestModel reqMdl) {
        con__ = con;
        reqMdl__ = reqMdl;
        usrSid__ = reqMdl.getSmodel().getUsrsid();
    }

    /**
     * <br>[機  能] コンストラクタ
     * <br>[解  説]
     * <br>[備  考]
     * @param con コネクション
     * @param reqMdl リクエスト情報
     * @param usrSid ユーザSID
     */
    public Rng020Biz(Connection con, RequestModel reqMdl, int usrSid) {
        con__ = con;
        reqMdl__ = reqMdl;
        usrSid__ = usrSid;
    }

    /**
     * <br>[機  能] 初期表示情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、稟議情報を設定する
     * <br>[備  考]
     * @param req リクエスト
     * @param paramMdl パラメータ情報
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param userMdl セッションユーザ情報
     * @param isAccessMbh true:モバイルからのアクセス false:PCからのアクセス
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws RtpNotfoundException テンプレート削除済み例外
     * @throws Exception
     */
    public void setInitData(
            HttpServletRequest req,
            IRng020PeronalParam paramMdl,
            String appRoot,
            GSTemporaryPathModel tempDir,
            BaseUserModel userMdl,
            boolean isAccessMbh)
    throws IOException, IOToolsException, SQLException,
    TempFileException, RtpNotfoundException, Exception {

        UDate now = new UDate();

        RngFormBuildBiz formBiz = __getFormBiz();

        //作成日を設定する
        paramMdl.setRng020createDate(UDateUtil.getSlashYYMD(now)
                                + " "
                                + UDateUtil.getSeparateHMS(now));

        if (paramMdl.getRngSid() >= 0) {
            //稟議一覧からの遷移、かつ処理モード = 編集 の場合、または複写して申請する場合
            __load(paramMdl);
        } else {
            // 新規登録時(稟議情報なし)
            paramMdl.setRng020rtpSid(paramMdl.getRngSelectTplSid());
        }
        //テンプレート選択済みの場合テンプレートからタイトル情報の取得
        if (paramMdl.getRng020rtpSid() > 0) {
            if (paramMdl.getRngSid() < 0) {
                RngTemplateModel model = formBiz.getRtpModelMaxVer(con__,
                        paramMdl.getRng020rtpSid());

                paramMdl.setRng020rtpVer(model.getRtpVer());
                paramMdl.setRng020Title(model.getRtpRngTitle());
            }

        }
        String url = TEMPFILE_DOWNLOAD_URL;
        url += paramMdl.getRng020rtpSid();
        setDspData(paramMdl, appRoot, tempDir, userMdl, true, url);

        log__.debug("End");
    }
    /**
    *
    * <br>[機  能] 追加選択したテンプレートを任意経路内に追加
    * <br>[解  説] 汎用稟議テンプレートのみ使用可能
    * <br>[備  考] 任意経路ブロックを指定するキーが汎用稟議テンプレートを想定し固定されているため
    * @param paramMdl パラメータモデル
    * @throws SQLException SQL実行時例外
    */
    private void __addKeiroTpl(IRng020PeronalParam paramMdl) throws SQLException {
        if (paramMdl.getLoadRctSid() <= 0) {
            return;
        }
        RngTemplateKeiroSave rng110keiroSave = null;
        rng110keiroSave = RngTemplateKeiroSave.createInstanceForRCT(
                paramMdl.getLoadRctSid(),
                reqMdl__.getSmodel().getUsrsid(),
                reqMdl__, con__);
        rng110keiroSave.load();
        __addKeiroTpl(paramMdl.getRng020keiro("0"), rng110keiroSave.getKeiro());
        __addKeiroTpl(paramMdl.getRng020kakuninKeiro("0"), rng110keiroSave.getFinalKeiro());
    }

    /**
     *
     * <br>[機  能] 追加選択したテンプレートを任意経路内に追加
     * <br>[解  説]
     * <br>[備  考]
     * @param keiroBlock 任意経路ブロック
     * @param keiroTplMap ロードした経路配列
     * @throws SQLException SQL実行時例外
     */
    private void __addKeiroTpl(Rng020KeiroBlock keiroBlock,
            Map<Integer, Rng110KeiroDialogParamModel> keiroTplMap) throws SQLException {
        List<Integer> keyset = new ArrayList<Integer>(keiroBlock.getKeiroMap().keySet());
        int rowNo = keyset.size();
        //空の経路ステップは除去する
        for (ListIterator<Integer> it = keyset.listIterator(keyset.size());
                it.hasPrevious();) {
            int key = it.previous();
            Rng020Keiro keiro = keiroBlock.getKeiro(String.valueOf(key));
            if (keiro.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL
                    && ArrayUtils.isEmpty(keiro.getUsrgrpSel().getSelectedSimple())) {
                keiroBlock.getKeiroMap().remove(key);
            } else {
                break;
            }
        }
        rowNo = keiroBlock.getKeiroMap().keySet().size();

        for (Entry<Integer, Rng110KeiroDialogParamModel> entry : keiroTplMap.entrySet()) {
            Rng110KeiroDialogParamModel keiroPref = entry.getValue();
            if (keiroPref.getKeiroKbn() == EnumKeiroKbn.USERTARGET_VAL
                    && keiroBlock.getOwnSingi() == RngConst.RNG_OWNSINGI_NO) {
                //ユーザ指定経路が自己審議を許可しない任意経路にインサートすることを許可しない
                String[] selected = keiroPref.getUsrgrouptarget().getSelected("target");
                ArrayList<String> newSelected = new ArrayList<String>();
                for (String sid : selected) {
                    if (sid.equals(String.valueOf(reqMdl__.getSmodel().getUsrsid()))) {
                        continue;
                    }
                    newSelected.add(sid);
                }
                if (newSelected.size() == 0) {
                    //追加対象がない場合、経路追加自体を行わない
                    continue;
                }
                keiroPref.getUsrgrouptarget().setSelected("target",
                        newSelected.toArray(new String[newSelected.size()]));
            }
            // 任意経路
            if (keiroPref.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL
                    && keiroBlock.getOwnSingi() == RngConst.RNG_OWNSINGI_NO
                    && (keiroTplMap.size() > 1
                            || keiroPref.getKeiroRootType() == RngConst.RNG_RNCTYPE_CONFIRM)) {
                //ユーザ指定経路が自己審議を許可しない任意経路にインサートすることを許可しない
                String[] selected = keiroPref.getUsrgroupselect().getSelected("target");
                ArrayList<String> newSelected = new ArrayList<String>();
                for (String sid : selected) {
                    if (sid.equals(String.valueOf(reqMdl__.getSmodel().getUsrsid()))) {
                        continue;
                    }
                    newSelected.add(sid);
                }
                if (newSelected.size() == 0) {
                    //追加対象がない場合、経路追加自体を行わない
                    continue;
                }
            }
            Rng020Keiro keiro = keiroBlock.getKeiro(String.valueOf(rowNo));
            keiro.setPref(keiroPref);
            keiro.setRtkSid(keiroPref.getRtkSid());
            keiro.initDefault();
            rowNo++;
        }
    }
    /**
     *
     * <br>[機  能] 経路テンプレート情報一覧を取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @return 経路テンプレート情報
     * @throws SQLException SQL実行例外
     * @throws RtpNotfoundException テンプレート削除済み例外
     */
    public RngTemplateKeiroSave __getTemplateKeiro(IRng020PeronalParam paramMdl)
            throws SQLException, RtpNotfoundException {
        RngTemplateKeiroSave ret = null;

        if (paramMdl.getRng020rtpSid() > 0) {
            RngFormBuildBiz formBiz = __getFormBiz();
            RngTemplateModel model = formBiz.getRtpModel(con__,
                    paramMdl.getRng020rtpSid(),
                    paramMdl.getRng020rtpVer());

            if (model.getRctSid() > 0) {
                ret = RngTemplateKeiroSave.createInstanceForRCT(model.getRctSid(),
                        model.getRctUsrSid(),
                        reqMdl__, con__);
            } else if (model.getRtpSid() > 0) {
                ret = RngTemplateKeiroSave.createInstanceForRTP(model.getRtpSid(),
                        model.getRtpVer(),
                        reqMdl__, con__);
            }
        }
        return ret;
    }

    /**
     *
     * <br>[機  能] テンプレート上の経路が申請可能な経路か判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param keiroMap 承認経路MAP
     * @param rollType 経路役割(0:承認経路 1：最終確認)
     * @return アクションエラー 申請不可能な場合不可能な理由を格納して返す
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors chkRTKuseable(Map<Integer, Rng020KeiroBlock> keiroMap,
            int rollType) throws SQLException {
        ActionErrors errors = new ActionErrors();
        //入力チェック種別 = 申請の場合は承認経路のチェックを行う
        Rng020EntryKeiroBiz keiroBiz = null;
        //承認経路申請可能チェック
        if (rollType == RngConst.RNG_RNCTYPE_APPR) {
            keiroBiz = new Rng020EntryKeiroBiz(con__, reqMdl__, 2);
        } else {
            keiroBiz = new Rng020EntryKeiroBiz(con__, reqMdl__, 1);
        }

        GsMessage gsMsg = new GsMessage(reqMdl__);

        String keiro = "";
        if (rollType == RngConst.RNG_RNCTYPE_APPR) {
            keiro = gsMsg.getMessage("rng.42");
        }
        if (rollType == RngConst.RNG_RNCTYPE_CONFIRM) {
            keiro = gsMsg.getMessage("rng.35");
        }
        int keiroCnt = 0;
        int dispKeiroBlockCnt = 0;
        int freeChk = 0;
        for (Entry<Integer, Rng020KeiroBlock> entry : keiroMap.entrySet()) {
            dispKeiroBlockCnt = dispKeiroBlockCnt
                    + keiroBiz.validateKeiro(errors, entry, keiro, keiroCnt);
            if (entry.getValue().getKeiroKbn() == EnumKeiroKbn.FREESET_VAL) {
                freeChk += 1;
            }
            keiroCnt++;
        }
        if (dispKeiroBlockCnt == 0 && rollType == RngConst.RNG_RNCTYPE_APPR && freeChk == 0) {
            ActionMessage msg = new ActionMessage("error.keiro.unuseable.allskip");
            errors.add("rng020apprUser", msg);
        }
        return errors;
    }
    /**
     *
     * <br>[機  能] 経路テンプレート情報取得 サブロジック
     * <br>[解  説] 経路と最終確認経路の共通処理を抽出
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param keiroTplMap 経路テンプレート情報
     * @param first 初期化時かどうか
     * @param isLoaded 読込みデータフラグ
     * @param isKakuninKeiro 最終確認経路かどうか
     * @param keiroZyogaiKbn 経路除外区分 0:しない 1:する
     * @throws SQLException SQLException
     */
    private void  __initKeiroTpl(IRng020PeronalParam paramMdl,
            Map<Integer, Rng110KeiroDialogParamModel> keiroTplMap,
            boolean first, boolean isLoaded, boolean isKakuninKeiro, int keiroZyogaiKbn)
                    throws SQLException {

        if (keiroTplMap == null) {
            return;
        }
        int sortNo = 0;

        boolean isLoad = (first && !isLoaded); // テンプレートからの初期データ読込み判定

        Map<Integer, List<Rng020KeiroBlock>> appBlockMap =
                new HashMap<Integer, List<Rng020KeiroBlock>>();

        //経路設定が任意経路かつ設定が共通が連続する場合は経路ブロックをまとめる
        Rng020KeiroBlock prev = null;
        Rng110KeiroDialogParamModel  prevPref = null;
        int keiroKey = 0;
        for (Entry<Integer, Rng110KeiroDialogParamModel> entry : keiroTplMap.entrySet()) {
            Rng110KeiroDialogParamModel keiroPref = entry.getValue();
            Rng020Keiro keiro = null;
            Rng020KeiroBlock keiroBlock = null;
            if (prev != null && keiroPref.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL
                && prevPref != null && prevPref.equalsKeiroPrefLessDefaultSelect(keiroPref)) {
                if (!isLoad) {
                    //初回表示時以降は2行目以降の初期化は不要
                    continue;
                }
                keiroKey++;
                keiro = prev.getKeiro(String.valueOf(keiroKey));
                keiroBlock = prev;
            } else {
                if (isKakuninKeiro) {
                    keiroBlock = paramMdl.getRng020kakuninKeiro(String.valueOf(sortNo));
                } else {
                    keiroBlock = paramMdl.getRng020keiro(String.valueOf(sortNo));
                }
                if (first && keiroBlock.getRtkSid() > 0
                 && keiroBlock.getRtkSid() != keiroPref.getRtkSid()) {
                    // テンプレートにある＋元となる稟議情報にない経路ブロック ⇒ テンプレートの経路ブロック情報を追加
                    prev     = null;
                    prevPref = null;

                    // 追加される経路ブロック情報を作成
                    keiroBlock = new Rng020KeiroBlock();
                    keiroBlock.setRtkSid(keiroPref.getRtkSid());
                    keiroBlock.setPref(keiroPref);
                    keiroBlock.setKeiroKbn(keiroPref.getKeiroKbn());
                    keiroBlock.setInCond(keiroPref.getInCondMap());
                    keiroKey = 0;

                    if (keiroPref.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL) {
                        // 任意経路の場合、空の枠を追加
                        prev     = keiroBlock;
                        prevPref = keiroPref;
                    } else {
                        // 任意経路以外の場合、テンプレートから初期状態の経路を追加
                        keiroBlock.getKeiroSingle(); //経路情報の1行目を生成
                        keiro = keiroBlock.getKeiro(String.valueOf(keiroKey));
                        keiro.setRtkSid(keiroPref.getRtkSid());
                        keiro.setPref(keiroPref);
                        keiro.initDefault();
                    }

                    // 追加される経路ブロック情報を一時的に配列へセット
                    List<Rng020KeiroBlock> blockList = appBlockMap.get(sortNo);
                    if (blockList == null) {
                        blockList = new ArrayList<Rng020KeiroBlock>();
                    }
                    blockList.add(keiroBlock);
                    appBlockMap.put(sortNo, blockList);
                    continue;
                }

                sortNo++;
                prev = keiroBlock;
                prevPref = keiroPref;
                keiroBlock.setKeiroKbn(keiroPref.getKeiroKbn());
                keiroBlock.setInCond(keiroPref.getInCondMap());
                keiroBlock.setRtkSid(keiroPref.getRtkSid());
                keiroBlock.setPref(keiroPref);
                keiroKey = 0;
                //初回表示時以降は任意経路の初期化は不要
                if (keiroPref.getKeiroKbn() != EnumKeiroKbn.FREESET_VAL || isLoad) {
                    //経路情報の1行目を生成
                    keiroBlock.getKeiroSingle();
                }
            }

            Map<Integer, Rng020Keiro> keiroMap = keiroBlock.getKeiroMap();
            if (keiroMap.containsKey(keiroKey)) {
                keiro = keiroBlock.getKeiro(String.valueOf(keiroKey));
            }
            if (keiro == null) {
                continue;
            }
            if (keiroPref.getKeiroKbn() != EnumKeiroKbn.FREESET_VAL || isLoad) {
                keiro.setRtkSid(keiroPref.getRtkSid());
                keiro.setPref(keiroPref);
                if (isLoad) {
                    keiro.initDefault();
                }
            }
        }

        // 追加経路(空の経路ブロック)がある場合、ソート番号を再セット
        Map<Integer, Rng020KeiroBlock> keiroBlockMap = null;
        if (appBlockMap.size() > 0) {
            if (isKakuninKeiro) {
                keiroBlockMap = paramMdl.getRng020kakuninKeiroMap();
            } else {
                keiroBlockMap = paramMdl.getRng020keiroMap();
            }
        }
        if (keiroBlockMap != null && keiroBlockMap.size() > 0) {
            // 既存の経路データをソート番号を再セットした配列に入れ替える
            Map<Integer, Rng020KeiroBlock> checkBlockMap = new HashMap<Integer, Rng020KeiroBlock>();
            checkBlockMap.putAll(keiroBlockMap); // 既存の経路データをコピーする
            List<Integer> checkKeys = new ArrayList<Integer>(checkBlockMap.keySet());
            Collections.sort(checkKeys);         // ソート番号順に並び替える
            keiroBlockMap.clear();               // 経路データをソート番号を再セットする為、一旦初期化
            sortNo = 0;
            for (Integer key : checkKeys) {
                List<Rng020KeiroBlock> blockList = appBlockMap.get(key);
                if (blockList != null) {
                    for (Rng020KeiroBlock block : blockList) {
                        keiroBlockMap.put(Integer.valueOf(sortNo), block);
                        sortNo++;
                    }
                }
                Rng020KeiroBlock keiroBlock = checkBlockMap.get(key);
                if (keiroBlock != null) {
                    keiroBlockMap.put(Integer.valueOf(sortNo), keiroBlock);
                    sortNo++;
                }
            }
        }
        return;
    }

    /**
     *
     * <br>[機  能] 経路テンプレート情報取得
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param keiroTplMap 経路一覧
     * @param finalKeiroTplMap 最終確認一覧
     * @param first 初回アクセス
     * @param keiroZyogaiKbn 0:しない 1:する
     * @throws SQLException SQL実行例外
     * @throws RtpNotfoundException テンプレート削除済み例外
     */
    public void initKeiroTpl(IRng020PeronalParam paramMdl, Map<Integer,
            Rng110KeiroDialogParamModel> keiroTplMap,
            Map<Integer, Rng110KeiroDialogParamModel> finalKeiroTplMap,
            boolean first,
            int keiroZyogaiKbn) throws SQLException, RtpNotfoundException {

        //既存稟議から読み込みフラグ
        boolean isLoaded = false;
        if (paramMdl.getRngSid() >= 0           // 草稿 or 複写か判定
         && paramMdl.getRtpVerUpdated() <= 1) { // 経路バージョンによる相違がない(相違がある場合、前データからは読み込まない)
            isLoaded = true;
        }
        //初回アクセスじゃない場合も経路テンプレートバージョンアップ時は初回アクセス同様の初期化処理
        if (paramMdl.getRtpVerUpdated() > 1) {
            first = true;
        }
        __initKeiroTpl(paramMdl, keiroTplMap, first, isLoaded, false, keiroZyogaiKbn);

        __initKeiroTpl(paramMdl, finalKeiroTplMap, first, isLoaded, true, keiroZyogaiKbn);

        if (paramMdl.getRng020keiroMap().size() == 0 && first) {
            Rng110KeiroDialogParamModel pref = new Rng110KeiroDialogParamModel();
            Rng020KeiroBlock keiroBlock = paramMdl.getRng020keiro(String.valueOf(0));
            Rng020Keiro keiro = keiroBlock.getKeiroSingle();
            keiroBlock.setPref(pref);
            keiro.setPref(pref);
            keiro.setKeiroKbn(EnumKeiroKbn.FREESET_VAL);
            keiroBlock.setKeiroKbn(EnumKeiroKbn.FREESET_VAL);
        }
        if (paramMdl.getRng020kakuninKeiroMap().size() == 0  && first) {
            Rng110KeiroDialogParamModel pref = new Rng110KeiroDialogParamModel();
            Rng020KeiroBlock keiroBlock
              = paramMdl.getRng020kakuninKeiro(String.valueOf(0));
            keiroBlock.setKeiroKbn(EnumKeiroKbn.FREESET_VAL);
            keiroBlock.setPref(pref);
        }
        //個人経路テンプレート取り込み使用判定
        RngBiz rngBiz = new RngBiz(con__);
        RngAconfModel aconfMdl = rngBiz.getRngAconf(con__);

        paramMdl.setUseKeiroTemplateFlg(RngConst.RAR_KEIRO_PERSONAL_FLG_NO);
        if (__isCanUsePersonalKeiro(paramMdl, aconfMdl)) {
            paramMdl.setUseKeiroTemplateFlg(RngConst.RAR_KEIRO_PERSONAL_FLG_YES);
        }


    }

    /**
     * <br>[機  能] 描画情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、稟議情報を設定する
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param userMdl セッションユーザ情報
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws RtpNotfoundException テンプレート削除済み例外
     * @throws Exception 申請ID採番時例外
     */
    public void setDspData(IRng020PeronalParam paramMdl,
                            String appRoot, GSTemporaryPathModel tempDir, BaseUserModel userMdl
                            )
    throws IOException, IOToolsException, SQLException,
    TempFileException, RtpNotfoundException, Exception {



        setDspData(paramMdl, appRoot, tempDir, userMdl, false,
                TEMPFILE_DOWNLOAD_URL + paramMdl.getRng020rtpSid());
    }
    /**
     * <br>[機  能] 描画情報を設定する
     * <br>[解  説] 処理モード = 編集の場合、稟議情報を設定する
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param appRoot アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリパス
     * @param userMdl セッションユーザ情報
     * @param first 初回アクセスフラグ
     * @param url   ダウンロードURL
     * @throws SQLException SQL実行例外
     * @throws IOException 添付ファイルの操作に失敗
     * @throws IOToolsException 添付ファイルの操作に失敗
     * @throws TempFileException 添付ファイルUtil内での例外
     * @throws RtpNotfoundException テンプレート削除済み例外
     */
    public void setDspData(IRng020PeronalParam paramMdl,
                            String appRoot, GSTemporaryPathModel tempDir, BaseUserModel userMdl,
                            boolean first, String url)
    throws IOException, IOToolsException, SQLException,
    TempFileException, RtpNotfoundException {
        //申請者を設定する
        paramMdl.setRng020requestUser(userMdl.getUsiseimei());
        paramMdl.setRng020requestUserId(String.valueOf(userMdl.getUsrsid()));

        RngFormBuildBiz formBiz = __getFormBiz();

        RngBiz rBiz = new RngBiz(con__);
        int idEditable = RngConst.RAR_SINSEI_NOT_KYOKA;
        //テンプレートから現在のフォーム情報を展開
        formBiz.loadTenplateData(con__, paramMdl.getRng020input(),
                paramMdl.getRng020rtpSid(),
                paramMdl.getRng020rtpVer());

        //テンプレート情報を取得
        RngTemplateModel rtpModel = formBiz.getRtpModel(con__, paramMdl.getRng020rtpSid(),
                paramMdl.getRng020rtpVer());
        //テンプレート種類取得
        paramMdl.setRng020rtpVer(rtpModel.getRtpVer());

        paramMdl.setRng020rtpType(rtpModel.getRtpType());

        paramMdl.setRng020rtpKeiroVersion(rtpModel.getRctVer());

        paramMdl.setRng020useCopyKeiro(__isUseCopyKeiro(rtpModel));

        paramMdl.setIdUseFlg(0);

        paramMdl.setRng020UseApiConnect((rtpModel.getRtpUseApiconnect()));
        paramMdl.setRng020ApiComment((rtpModel.getRtpApiconnectComment()));

        RingiIdModel idModel = null;
        idModel = rBiz.getRngidModel(rtpModel.getRtpIdformatSid());
        //稟議ID情報がnullの場合（稟議IDを使用しない場合のみnullが帰る）、稟議IDを使用しない
        if (idModel != null) {
            paramMdl.setIdUseFlg(1);
            idEditable = idModel.getRngManual();
            if (idEditable == RngConst.RAR_SINSEI_MANUAL_TEMPLATE) {
                idEditable = rtpModel.getRtpIdmanual();
            }
        }
        paramMdl.setIdPrefManualEditable(idEditable);

        if (idModel != null) {
            paramMdl.setRng020IdTitle(idModel.getRngTitle());
        } else {
            paramMdl.setRng020IdTitle("");
        }
        // 発行予定の申請ID
        String planId = rBiz.getNewRngid(
                idModel, null, false, paramMdl.getRngSid(), reqMdl__);
        paramMdl.setRng020PlanID(planId);
        //草稿から読み込みフラグ
        boolean isLoaded = false;
        if (paramMdl.getRngSid() >= 0
        && !paramMdl.isRng020copyApply()) {
            isLoaded = true;
        }
        if (first && isLoaded && !StringUtil.isNullZeroString(paramMdl.getRng020ID())) {
            paramMdl.setRng020IdPrefManual(1);
        }

        if (first && paramMdl.getRng020IdPrefManual() == 0) {
            paramMdl.setRng020ID(planId);
        }
        //初回アクセスじゃない場合も経路テンプレートバージョンアップ時は初回アクセス同様の初期化処理
        boolean rtpVerUp = false;
        if (paramMdl.getRtpVerUpdated() == 1 || paramMdl.getRtpVerUpdated() == 3) {
            rtpVerUp = true;
        }
        FormInputInitPrefarence pref = new FormInputInitPrefarence();
        pref.setAppRoot(appRoot);
        pref.setMode(FormInputBuilder.INITMODE_INPUT);
        pref.setUrl(url);
        pref.setTempDir(tempDir);
        if (first || rtpVerUp) {
            pref.setLoad(true);
            paramMdl.getRng020input().setInitPrefarence(pref);
            paramMdl.getRng020input().defaultInit();
            paramMdl.getRng020input().dspInit(reqMdl__, con__);
        } else {
            pref.setLoad(false);
            paramMdl.getRng020input().setInitPrefarence(pref);

            paramMdl.getRng020input().dspInit(reqMdl__, con__);
        }


        RngTemplateKeiroSave rng110keiroSave = __getTemplateKeiro(paramMdl);

        Map<Integer, Rng110KeiroDialogParamModel> keiroTplMap      = null;
        Map<Integer, Rng110KeiroDialogParamModel> finalKeiroTplMap = null;
        if (rng110keiroSave != null) {
            rng110keiroSave.load();
            keiroTplMap      = rng110keiroSave.getKeiro();
            finalKeiroTplMap = rng110keiroSave.getFinalKeiro();
        }

        this.initKeiroTpl(paramMdl, keiroTplMap, finalKeiroTplMap, first, 1);
        this.dspKeiroTpl(paramMdl, first, false);

        // 経路のスキップ設定
        keiroSkip(paramMdl);
    }
    /**
     *
     * <br>[機  能] 個人経路テンプレートの使用条件を満たすか判定する
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ
     * @param aconfMdl 稟議管理者設定
     * @return 使用可 true
     */
    private boolean __isCanUsePersonalKeiro(IRng020PeronalParam paramMdl,
            RngAconfModel aconfMdl) {
        //条件1：管理者設定で使用可能か
        if (aconfMdl.getRarKeiroPersonalFlg() == RngConst.RAR_KEIRO_PERSONAL_FLG_NO) {
            return false;
        }

        //条件2：経路と最終確認経路それぞれに任意経路が一つ設定されているか
        boolean keiroOk = false;
        for (Entry<Integer, Rng020KeiroBlock> entry
            : paramMdl.getRng020keiroMap().entrySet()) {
            Rng020KeiroBlock block = entry.getValue();
            keiroOk = (block.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL
                    && paramMdl.getRng020keiroMap().size() == 1);
            break;
        }
        boolean kakuninKeiroOk = false;
        for (Entry<Integer, Rng020KeiroBlock> entry
            : paramMdl.getRng020kakuninKeiroMap().entrySet()) {
            Rng020KeiroBlock block = entry.getValue();
            kakuninKeiroOk = (block.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL
                    && paramMdl.getRng020kakuninKeiroMap().size() == 1);
            break;
        }
        return (keiroOk && kakuninKeiroOk);
    }


    /**
     *
     * <br>[機  能] 経路テンプレートをディスプレイ情報へセット
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param first 初回アクセス
     * @param preview プレビューフラグ
     * @throws SQLException SQL実行例外
     * @throws RtpNotfoundException テンプレート削除済み例外
     */
    protected void dspKeiroTpl(IRng020PeronalParam paramMdl, boolean first, boolean preview)
                    throws SQLException, RtpNotfoundException {

        //経路情報の展開
        //テンプレートから経路を初期化
        int sessionUsrSid = reqMdl__.getSmodel().getUsrsid();
        GsMessage gsMsg = new GsMessage(reqMdl__);
        UserGroupSelectBiz usrgrpBiz = new UserGroupSelectBiz();
        GroupBiz grpBiz = new GroupBiz();
        String defGrpSid = String.valueOf(
                grpBiz.getDefaultGroupSid(sessionUsrSid,
                        con__));
        List<UsrLabelValueBean> grplist =
                usrgrpBiz.getGroupLabel(reqMdl__, con__);
        PosBiz posBiz = new PosBiz();
        //役職選択
        List<LabelValueBean> allPosLabelList = posBiz.getPosLabelList(con__, false);
        //役職のないユーザ指定用に「役職なし」を追加
        allPosLabelList.add(0, new LabelValueBean(gsMsg.getMessage("cmn.nopost"), "0"));
        Map<Integer, LabelValueBean> posLabelMap = new HashMap<>();
        for (LabelValueBean label : allPosLabelList) {
            posLabelMap.put(Integer.valueOf(label.getValue()), label);
        }
        if (paramMdl.getUseKeiroTemplateFlg() == RngConst.RAR_KEIRO_PERSONAL_FLG_YES
                 ) {
            __addKeiroTpl(paramMdl);
        }
        boolean isKeiroHiddenChanged = false;
        KeiroMapDspIniter keiroIniter = new KeiroMapDspIniter(paramMdl.getRng020input(),
                defGrpSid, grplist,  posLabelMap);
        for (Entry<Integer, Rng020KeiroBlock> entry : paramMdl.getRng020keiroMap().entrySet()) {
            int oldHidden = entry.getValue().getHidden();
            keiroIniter.dspInit(entry, RngConst.RNG_RNCTYPE_APPR, first, preview);
            if (oldHidden != entry.getValue().getHidden()) {
                isKeiroHiddenChanged = true;
            }
        }
        int kakuninCnt = 0;
        for (Entry<Integer, Rng020KeiroBlock> entry
                : paramMdl.getRng020kakuninKeiroMap().entrySet()) {
            int oldHidden = entry.getValue().getHidden();
            keiroIniter.dspInit(entry, RngConst.RNG_RNCTYPE_CONFIRM, first, preview);
            if (oldHidden != entry.getValue().getHidden()) {
                isKeiroHiddenChanged = true;
            }
            if (entry.getValue().getHidden() == 0) {
                kakuninCnt += entry.getValue().getKeiroMap().size();
            }
        }
        //最終確認の表示経路が1件以上ある場合、最終確認表示フラグを立てる
        if (kakuninCnt > 0) {
            paramMdl.setKakuninKeiroDspFlg(1);
        }
        //経路が入力の変更により変化したことを表示する
        if (isKeiroHiddenChanged && !first) {
            paramMdl.setKeiroAutoChanged(1);
        }
        //経路が入力変更で経路が変化するFormIDリストを取得する
        ArrayList<String> belongFormID = new ArrayList<String>();
        for (Entry<Integer, Rng020KeiroBlock> entry : paramMdl.getRng020keiroMap().entrySet()) {
            belongFormID.addAll(
                    __getFormIdForKeiroChange(entry.getValue().getInCond()));
        }
        for (Entry<Integer, Rng020KeiroBlock> entry
                : paramMdl.getRng020kakuninKeiroMap().entrySet()) {
            belongFormID.addAll(
                    __getFormIdForKeiroChange(entry.getValue().getInCond()));
        }
        //要素の値変更確定時に実行されるスクリプトを設定
        Map<FormAccesser, FormCell> formMap
        = paramMdl.getRng020input().getFormMapFromID(belongFormID);
        for (Entry<FormAccesser, FormCell> entry : formMap.entrySet()) {
            entry.getValue().getBody().addValueChangedEv(
                    "if (typeof dspUpdateKeiro === 'function') { dspUpdateKeiro() }"
                    );
        }

    }
    /**
     *
     * <br>[機  能] 経路マップの表示初期化用クラス
     * <br>[解  説] 内部処理のカプセル化のため胃内部クラスとして実装
     * <br>[備  考]
     *
     * @author JTS
     */
    private class KeiroMapDspIniter {
        /** フォーム入力値管理クラス */
        FormInputBuilder fb__;
        /** デフォルトグループSID */
        String defGrpSid__;
        /** グループ一覧 */
        List<UsrLabelValueBean> grplist__;
        /** 役職ラベル一覧 */
        Map<Integer, LabelValueBean> posLabelMap__;
        /** 経路ロードクラス*/
        RngTemplateKeiroSave save__;
        /** 追加読み込みの経路情報キャッシュ*/
        Map<Integer, Rng110KeiroDialogParamModel> keiroCash__;
        /** 所属グループリスト*/
        ArrayList<GroupModel> belongGpList__;
        /** グループSIDとグループ管理者数の関連Map*/
        HashMap<Integer, Integer> grpSidBossCntMap__ = new HashMap<Integer, Integer>();

        /**
         * コンストラクタ
         * @param fb フォーム入力値管理クラス
         * @param defGrpSid デフォルトグループSID
         * @param grplist グループ一覧
         * @param posLabelMap 役職ラベル一覧
         * @throws SQLException SQL実行時例外
         */
        public KeiroMapDspIniter(FormInputBuilder fb,
                String defGrpSid,
                List<UsrLabelValueBean> grplist,
                Map<Integer, LabelValueBean> posLabelMap
                ) throws SQLException {
            super();
            fb__ = fb;
            defGrpSid__ = defGrpSid;
            grplist__ = grplist;
            posLabelMap__ = posLabelMap;
            save__ = RngTemplateKeiroSave.createInstanceForConvert(reqMdl__, con__);
            keiroCash__ = new HashMap<Integer, Rng110KeiroDialogParamModel>();
            __createBelongGrp(reqMdl__.getSmodel().getUsrsid());


        }
        /**
         *
         * <br>[機  能] ユーザの所属するグループ一覧と所属グループの上位階層グループの管理者数を設定する
         * <br>[解  説] 途中の階層に管理者不在のグループは除外する
         * <br>[備  考]
         * @param usrSid ユーザSID
         * @throws SQLException SQL実行時例外
         */
        private void __createBelongGrp(int usrSid) throws SQLException {
            //所属グループリスト取得
            UsidSelectGrpNameDao gpDao = new UsidSelectGrpNameDao(con__);
//            ArrayList<GroupModel> gpList = gpDao.selectGroupNmList(usrSid);
            List<CmnGroupClassModel> belongGrpClsList = gpDao.selectBelongGroupClass(usrSid);
            belongGpList__ = new ArrayList<GroupModel>();
            CommonBiz cmnBiz = new CommonBiz();

            for (CmnGroupClassModel cls : belongGrpClsList) {
                Rng020Keiro.Rng020BossTargetModel grpMdl = __createGroupModel(cls);
                List<Integer> stepSids = grpMdl.getHigherClassGrp();
                boolean addOk = true;
                for (int grpSid:stepSids) {
                    if (grpSid < 0) {
                        addOk = false;
                        break;
                    }
                    if (!grpSidBossCntMap__.containsKey(grpSid)) {
                        // グループ管理者ユーザを取得
                        CmnBelongmDao begDao = new CmnBelongmDao(con__);
                        List<CmnBelongmModel> bossList = begDao.selectBossModel(grpSid);
                        ArrayList<Integer> usrList = new ArrayList<Integer>();
                        for (CmnBelongmModel boss : bossList) {
                            usrList.add(boss.getUsrSid());
                        }
                        //プラグイン使用禁止ユーザを除外
                        List<Integer> cantUseSidList =
                                cmnBiz.getCantUsePluginUser(
                                        con__, RngConst.PLUGIN_ID_RINGI, usrList);
                        usrList.removeAll(cantUseSidList);
                        int bossCnt = usrList.size();
                        grpSidBossCntMap__.put(grpSid, bossCnt);
                    }
                }
                if (addOk) {
                    belongGpList__.add(grpMdl);
                }

            }
        }
        /**
         *
         * <br>[機  能]  グループ階層モデルからグループモデルを生成
         * <br>[解  説]
         * <br>[備  考]
         * @param gclMdl グループ階層モデル
         * @return グループモデル
         */
        private Rng020Keiro.Rng020BossTargetModel __createGroupModel(CmnGroupClassModel gclMdl) {
            int classNum = -1;
            Rng020Keiro.Rng020BossTargetModel grpMdl = new Rng020Keiro.Rng020BossTargetModel();
            List<Integer> higherClassGrp = new ArrayList<Integer>();
            grpMdl.setBossCntMap(grpSidBossCntMap__);
            grpMdl.setHigherClassGrp(higherClassGrp);
            if (gclMdl.getGclSid10() > 0) {
                if (classNum == -1) {
                    classNum = 10;
                    grpMdl.setClassLevel(classNum);
                    grpMdl.setGroupSid(gclMdl.getGclSid10());
                    grpMdl.setGroupId(gclMdl.getGclId10());
                    grpMdl.setGroupName(gclMdl.getGclName10());
                }
                higherClassGrp.add(gclMdl.getGclSid10());
            }
            if (gclMdl.getGclSid9() > 0) {
                if (classNum == -1) {
                    classNum = 9;
                    grpMdl.setClassLevel(classNum);
                    grpMdl.setGroupSid(gclMdl.getGclSid9());
                    grpMdl.setGroupId(gclMdl.getGclId9());
                    grpMdl.setGroupName(gclMdl.getGclName9());
                }
                higherClassGrp.add(gclMdl.getGclSid9());
            }
            if (gclMdl.getGclSid8() > 0) {
                if (classNum == -1) {
                    classNum = 8;
                    grpMdl.setClassLevel(classNum);
                    grpMdl.setGroupSid(gclMdl.getGclSid8());
                    grpMdl.setGroupId(gclMdl.getGclId8());
                    grpMdl.setGroupName(gclMdl.getGclName8());
                }
                higherClassGrp.add(gclMdl.getGclSid8());
            }
            if (gclMdl.getGclSid7() > 0) {
                if (classNum == -1) {
                    classNum = 7;
                    grpMdl.setClassLevel(classNum);
                    grpMdl.setGroupSid(gclMdl.getGclSid7());
                    grpMdl.setGroupId(gclMdl.getGclId7());
                    grpMdl.setGroupName(gclMdl.getGclName7());
                }
                higherClassGrp.add(gclMdl.getGclSid7());
            }
            if (gclMdl.getGclSid6() > 0) {
                if (classNum == -1) {
                    classNum = 6;
                    grpMdl.setClassLevel(classNum);
                    grpMdl.setGroupSid(gclMdl.getGclSid6());
                    grpMdl.setGroupId(gclMdl.getGclId6());
                    grpMdl.setGroupName(gclMdl.getGclName6());
                }
                higherClassGrp.add(gclMdl.getGclSid6());
            }
            if (gclMdl.getGclSid5() > 0) {
                if (classNum == -1) {
                    classNum = 5;
                    grpMdl.setClassLevel(classNum);
                    grpMdl.setGroupSid(gclMdl.getGclSid5());
                    grpMdl.setGroupId(gclMdl.getGclId5());
                    grpMdl.setGroupName(gclMdl.getGclName5());
                }
                higherClassGrp.add(gclMdl.getGclSid5());
            }
            if (gclMdl.getGclSid4() > 0) {
                if (classNum == -1) {
                    classNum = 4;
                    grpMdl.setClassLevel(classNum);
                    grpMdl.setGroupSid(gclMdl.getGclSid4());
                    grpMdl.setGroupId(gclMdl.getGclId4());
                    grpMdl.setGroupName(gclMdl.getGclName4());
                }
                higherClassGrp.add(gclMdl.getGclSid4());
            }
            if (gclMdl.getGclSid3() > 0) {
                if (classNum == -1) {
                    classNum = 3;
                    grpMdl.setClassLevel(classNum);
                    grpMdl.setGroupSid(gclMdl.getGclSid3());
                    grpMdl.setGroupId(gclMdl.getGclId3());
                    grpMdl.setGroupName(gclMdl.getGclName3());
                }
                higherClassGrp.add(gclMdl.getGclSid3());
            }
            if (gclMdl.getGclSid2() > 0) {
                if (classNum == -1) {
                    classNum = 2;
                    grpMdl.setClassLevel(classNum);
                    grpMdl.setGroupSid(gclMdl.getGclSid2());
                    grpMdl.setGroupId(gclMdl.getGclId2());
                    grpMdl.setGroupName(gclMdl.getGclName2());
                }
                higherClassGrp.add(gclMdl.getGclSid2());
            }
            if (gclMdl.getGclSid1() > 0) {
                if (classNum == -1) {
                    classNum = 1;
                    grpMdl.setClassLevel(classNum);
                    grpMdl.setGroupSid(gclMdl.getGclSid1());
                    grpMdl.setGroupId(gclMdl.getGclId1());
                    grpMdl.setGroupName(gclMdl.getGclName1());
                }
                higherClassGrp.add(gclMdl.getGclSid1());
            }
            return grpMdl;
        }
        /**
        *
        * <br>[機  能] 経路描画設定
        * <br>[解  説]
        * <br>[備  考]
        * @param entry 経路要素ブロック
        * @param rollType 経路役割区分
        * @param first 初期化時かどうか
        * @param preview プレビューフラグ
        * @throws SQLException SQL実行時例外
        */
       public void dspInit(
               Entry<Integer, Rng020KeiroBlock> entry,
               int rollType, boolean first, boolean preview
               ) throws SQLException {
           Rng020KeiroBlock block = entry.getValue();

           if (!ArrayUtils.isEmpty(block.getDeleteRow())) {
               //経路の削除
               Map<Integer, Rng020Keiro> keiroMap = new HashMap<Integer, Rng020Keiro>();
               for (String rowNo : block.getDeleteRow()) {
                   block.getKeiroMap().remove(Integer.valueOf(rowNo));
               }
               //削除した経路を詰める
               Collection<Rng020Keiro> keiroSet = entry.getValue().getKeiroMap().values();
               int rowNo = 0;
               for (Rng020Keiro keiro : keiroSet) {
                   keiroMap.put(rowNo, keiro);
                   rowNo++;
               }
               entry.getValue().setKeiroMap(keiroMap);
           }
           if (!ArrayUtils.isEmpty(block.getAddRow())) {
               //経路の追加
               for (Iterator<String> it = Arrays.asList(block.getAddRow()).iterator();
                       it.hasNext();) {
                   Rng020Keiro keiro = new Rng020Keiro();
                   block.setKeiro(block.getKeiroMap().size(), keiro);
                   it.next();
               }
           }

           if (!ArrayUtils.isEmpty(block.getUpRow())) {
               //経路の移動（上）

               for (String rowNo : block.getUpRow()) {
                   int row = NullDefault.getInt(rowNo, -1);
                   //現在位置が一番上の場合は処理を行わない
                   if (row <= 0) {
                       continue;
                   }

                   //Mapを入れ替える
                   Rng020Keiro upTar = block.getKeiroMap().get(row);
                   Rng020Keiro downTar = block.getKeiroMap().get(row - 1);
                   block.getKeiroMap().replace(row - 1, upTar);
                   block.getKeiroMap().replace(row, downTar);
               }
           }

           if (!ArrayUtils.isEmpty(block.getDownRow())) {
               //経路の移動（下）
               for (String rowNo : block.getDownRow()) {
                   int row = NullDefault.getInt(rowNo, -1);
                   //現在位置が一番下の場合は処理を行わない
                   if (row == -1 || row == block.getKeiroMap().size() - 1) {
                       continue;
                   }

                   //Mapを入れ替える
                   Rng020Keiro upTar = block.getKeiroMap().get(row + 1);
                   Rng020Keiro downTar = block.getKeiroMap().get(row);
                   block.getKeiroMap().replace(row, upTar);
                   block.getKeiroMap().replace(row + 1, downTar);
               }
           }

           Map<String, KeiroInCondition> inCondMap = block.getInCond();
           if (inCondMap != null && inCondMap.size() > 0) {
               if (!isUseableKeiroStep(inCondMap, fb__)) {
                   block.setHidden(1);
               } else {
                   block.setHidden(0);
               }
           }
           Collection<Rng020Keiro> keiroSet = entry.getValue().getKeiroMap().values();
           int num = 0;
           List<Integer> delRow = new ArrayList<Integer>();
           for (Rng020Keiro keiro : keiroSet) {
               //経路テンプレートで追加された経路設定の読み込み（再描画時用）
               if (block.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL
                       && keiro.getRtkSid() > 0) {
                   Rng110KeiroDialogParamModel pref;
                   if (keiroCash__.containsKey(keiro.getRtkSid())) {
                       pref = keiroCash__.get(keiro.getRtkSid());
                   } else {
                       RngTemplateKeiroDao rtkDao = new RngTemplateKeiroDao(con__);
                       RngTemplateKeiroModel rtkMdl = rtkDao.select(keiro.getRtkSid());
                       if (rtkMdl != null) {
                           pref = save__.convertParamModel(rtkMdl);
                       } else {
                           pref = null;
                       }
                   }
                   if (pref != null) {
                       keiro.setPref(pref);
                   }
               }
               // ユーザ選択情報を取得
               String defaultkey = UserGroupSelectModel.KEY_DEFAULT;

               // 描画設定
               if (!preview) {
                   if (keiro.getKeiroKbn() == EnumKeiroKbn.USERTARGET_VAL) {
                       RngTemplateKeiroUserDao rtkuDao = new RngTemplateKeiroUserDao(con__);
                       List<RngTemplateKeiroUserModel> rtkuList = rtkuDao.select(keiro.getRtkSid());
                       ArrayList<String> selList = new ArrayList<String>();
                       for (RngTemplateKeiroUserModel rtkuMdl : rtkuList) {
                           if (rtkuMdl.getGrpSid() > -1) {
                               selList.add(UserGroupSelectBiz.GROUP_PREFIX
                                       + String.valueOf(rtkuMdl.getGrpSid()));
                           } else if (rtkuMdl.getUsrSid() > -1) {
                               selList.add(String.valueOf(rtkuMdl.getUsrSid()));
                           }
                       }
                       if (selList != null && selList.size() > 0) {
                           keiro.getUsrgrpSel().setSelectedSimple(
                                   selList.toArray(new String[selList.size()]));
                       }
                   }
                   if (keiro.getKeiroKbn() == EnumKeiroKbn.POSTARGET_VAL) {
                       RngTemplateKeiroUserDao rtkuDao = new RngTemplateKeiroUserDao(con__);
                       List<RngTemplateKeiroUserModel> rtkuList = rtkuDao.select(keiro.getRtkSid());
                       TreeMap<String, TargetPosSel> posMap = new TreeMap<String, TargetPosSel>();
                       int idx = 0;
                       for (RngTemplateKeiroUserModel rtkuMdl : rtkuList) {
                           TargetPosSel target = new TargetPosSel();
                           target.getPosSel().setSelected(String.valueOf(rtkuMdl.getPosSid()));
                           target.getGrpSel().setSelected(String.valueOf(rtkuMdl.getGrpSid()));
                           posMap.put(String.valueOf(idx), target);
                           idx++;
                       }
                       keiro.getPref().setTargetposMap(posMap);
                   }
               }

               keiro.dspInit(con__, reqMdl__, defGrpSid__,
                       grplist__, belongGpList__, posLabelMap__, block, preview, rollType);

               // 任意経路内の選択ユーザが申請者のみならその経路を削除するリストに追加
               if (first
                   && keiro.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL
                   && block.getKeiroKbn() == EnumKeiroKbn.FREESET_VAL
                   && block.getPref().getOwn() == RngConst.RNG_OWNSINGI_NO) {
                   List<RngKeirostepSelectModel> selectList = keiro.getInitSelect();
                   List<UsrLabelValueBean> usrSelected =
                           keiro.getUsrgrpSel().getSelectedList(defaultkey);
                   if (selectList != null
                           && !selectList.isEmpty()
                           && usrSelected.size() == 0) {
                       delRow.add(num);
                   }
               }
               num++;
           }

           // 任意経路設定で削除経路が存在するならその経路を削除する
           if (!delRow.isEmpty()) {
               //経路の削除
               Map<Integer, Rng020Keiro> keiroMap = new HashMap<Integer, Rng020Keiro>();
               for (int rowNo : delRow) {
                   if (rollType == RngConst.RNG_RNCTYPE_CONFIRM || block.getKeiroMap().size() > 1) {
                       block.getKeiroMap().remove(Integer.valueOf(rowNo));
                   }
               }
               //削除した経路を詰める
               keiroSet = entry.getValue().getKeiroMap().values();
               int rowNo = 0;
               for (Rng020Keiro keiro : keiroSet) {
                   keiroMap.put(rowNo, keiro);
                   rowNo++;
               }
               entry.getValue().setKeiroMap(keiroMap);
           }


       }
   }

    /**
     *
     * <br>[機  能] 文字列型配列とIntegerList間で要素比較をする
     * <br>[解  説] 1件でも一致すればtrue
     * <br>[備  考]
     * @param strArr 文字列配列
     * @param intList IntegerList
     * @return 比較結果
     */
    private boolean __containsAnyStrArrIntegerList(
            String[] strArr, List<Integer> intList) {
        if (strArr.length < intList.size()) {
            for (Iterator<String> it = Arrays.asList(strArr).iterator(); it.hasNext();) {
                try {
                    if (intList.contains(Integer.valueOf(it.next()))) {
                        return true;
                    }
                } catch (NumberFormatException e) {
                }
            }
        } else {
            for (Iterator<Integer> it = intList.iterator(); it.hasNext();) {
                if (ArrayUtils.contains(strArr, String.valueOf(it.next()))) {
                    return true;
                }
            }
        }
        return false;
    }
    /**比較用クラス*/
    private OprOption oprOption__ = null;
    /**
     *
     * <br>[機  能] 経路条件判定用 比較データ管理クラス
     * <br>[解  説]
     * <br>[備  考]
     *
     * @author JTS
     */
    private static class OprOption {
        /**ユーザ所属GRPSID*/
        List<Integer> belongGrpSidList;
        /**ユーザ情報*/
        CmnUsrmInfModel usrInf;
        /**
         * コンストラクタ
         * @param con コネクション
         * @param usrSid ユーザSID
         * @throws SQLException SQL実行時例外
         */
        OprOption(Connection con, int usrSid) throws SQLException {
            CmnBelongmDao belongDao = new CmnBelongmDao(con);
            belongGrpSidList = belongDao.selectUserBelongGroupSid(usrSid);
            CmnUsrmInfDao usrInfDao = new CmnUsrmInfDao(con);
            usrInf = usrInfDao.select(usrSid);
        }
    }
    /**
     *
     * <br>[機  能] 比較用クラスを取得
     * <br>[解  説] キャッシュ機能あり
     * <br>[備  考]
     * @return 比較用クラス
     * @throws SQLException SQL実行時例外
     */
    private OprOption getOprOption() throws SQLException {
        if (oprOption__ == null) {
            oprOption__ = new OprOption(con__, usrSid__);
        }
        return oprOption__;
    }
    /**
     *
     * <br>[機  能] 経路の表示条件に関連するフォームIDを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param inCondMap 経路使用条件Map
     * @return 経路の表示条件に関連するフォームID
     * @throws SQLException SQL実行時例外
     */
    private List<String> __getFormIdForKeiroChange(
            Map<String, KeiroInCondition> inCondMap) throws SQLException {
        ArrayList<String> ret = new ArrayList<String>();
        if (inCondMap == null || inCondMap.size() == 0) {
            return ret;
        }
        for (KeiroInCondition inCond : inCondMap.values()) {
            EnumKeiroInConditionKbn inCondKbn = null;
            try {
                inCondKbn = EnumKeiroInConditionKbn.valueOf(
                        Integer.valueOf(inCond.getCondKbn().getSelected()));
                switch (inCondKbn) {
                case CONDKBN_INPUT:
                    String formId = inCond.getFormId();
                    ret.add(formId);
                default:
                    break;
                }
            } catch (EnumOutRangeException e) {
            }
        }
        return ret;
    }
    /**
     *
     * <br>[機  能] テンプレートが申請目的でアクセス可能か判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpSid テンプレートSID
     * @return 判定結果
     * @throws SQLException SQL実行時例外
     */
    public boolean isUseableTemplate(int rtpSid) throws SQLException {
        // テンプレート情報取得
        RngTemplateModel rtpMdl = getTemplateMaxVer(rtpSid);
        return this.isUseableTemplate(rtpMdl);
    }

    /**
    *
    * <br>[機  能] テンプレートの使用許可確認
    * <br>[解  説]
    * <br>[備  考]
    *
    * @param aconfMdl 管理者設定モデル
    * @param rtpSid テンプレートSID
    * @param rtpType テンプレート種別 個人or共有
    * @return true 使用可能
    */

    public boolean isAcceptTemplate(
            RngAconfModel aconfMdl,
            int rtpSid, int rtpType) {
        // 汎用テンプレートが使用可能か
        if (rtpSid == 0) {
            if (aconfMdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_NO) {
                    return false;
            }
        }
        // 個人テンプレートが使用可能か
        if (rtpType == RngConst.RNG_TEMPLATE_PRIVATE) {
            // 汎用稟議が使用できない場合、個人テンプレートも使用できない
            if (aconfMdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_NO) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * <br>[機  能] テンプレートが申請目的でアクセス可能か判定します
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpMdl テンプレート情報
     * @return 判定結果
     * @throws SQLException SQL実行時例外
     */
    public boolean isUseableTemplate(RngTemplateModel rtpMdl) throws SQLException {

        //テンプレート存在確認
        if (rtpMdl == null) {
            return false; // 指定されたテンプレートが見つからないので権限なし
        }

        if (rtpMdl.getRtpSid() <= 0
                || rtpMdl.getUsrSid() != 0) {
            // 個人テンプレートの場合 or
            // 汎用稟議テンプレートの場合 → 管理者設定 汎用稟議テンプレート使用フラグにより判定
            RngBiz biz = new RngBiz(con__);
            RngAconfModel aconfMdl = biz.getRngAconf(con__);
            if (aconfMdl.getRarHanyoFlg() == RngConst.RAR_HANYO_FLG_NO) {
                return false;
            }
            return true;
        }
        //テンプレート削除確認
        if (rtpMdl.getRtpJkbn() == RngConst.JKBN_DELETE) {
            return false;
        }
        int usrSid = reqMdl__.getSmodel().getUsrsid();

        // 共有テンプレートカテゴリのみ使用権限チェックを行う
        int rtcSid = rtpMdl.getRtcSid();
        RngTemplateCategoryDao rtcDao = new RngTemplateCategoryDao(con__);
        RngTemplateCategoryModel rtcMdl = rtcDao.select(rtcSid);
        if (rtcMdl == null) {
            return false;
        }

        // アクセス権限設定の使用判定
        if (rtcMdl.getRtcUseLimit() != RngConst.LIMIT_USE) {
            return true; // アクセス権限設定を使用しない場合、権限チェックなし
        }

        // カテゴリ管理者権限判定
        RngTemplatecategoryAdmDao rtcAdmDao = new RngTemplatecategoryAdmDao(con__);
        boolean isAdmSid = rtcAdmDao.isAdmin(rtpMdl.getRtcSid(), usrSid);
        if (isAdmSid) {
            return true; // カテゴリ管理者は権限あり
        }

        // 許可or制限のあるカテゴリSID一覧を取得
        RngTemplatecategoryUseDao rtcUseDao = new RngTemplatecategoryUseDao(con__);
        boolean isUseSid = rtcUseDao.isSelectedSid(rtcSid, usrSid);
        if (rtcMdl.getRtcLimitType() == RngConst.LIMIT_TYPE_LIMIT) {
            return !isUseSid; // 制限設定＋指定ユーザ一覧にない場合、権限あり
        } else if (rtcMdl.getRtcLimitType() == RngConst.LIMIT_TYPE_ACCEPT) {
            return isUseSid;  // 許可設定＋指定ユーザ一覧にある場合、権限あり
        }
        return false;
    }

    /**
     *
     * <br>[機  能] 指定された稟議テンプレートのバイナリSIDがアクセス可能か判定
     * <br>[解  説]
     * <br>[備  考]
     * @param rtpSid テンプレートSID
     * @param binSid バイナリSID
     * @return 判定結果
     * @throws SQLException SQL実行時例外
     */
    public boolean isCheckDLSampleTemp(int rtpSid, Long binSid)
            throws SQLException {

        if (rtpSid <= 0) {
            return false; // 汎用稟議テンプレートの場合、サンプルファイルは存在しないのでエラー
        }

        // テンプレート情報取得
        RngTemplateModel rtpMdl = getTemplateMaxVer(rtpSid);

        // テンプレート使用権限があるか判定
        if (!isUseableTemplate(rtpMdl)) {
            return false;
        }

        // 指定の稟議テンプレートのバイナリSIDかチェック
        RngTemplateBinDao dao = new RngTemplateBinDao(con__);
        if (!dao.isCheckRngTemplateBin(rtpSid, binSid)) {
            return false;
        }

        return true;
     }

    /**
     *
     * <br>[機  能]
     * <br>[解  説]
     * <br>[備  考]
     * @param inCondMap 条件MAP
     * @param fb フォームビルダー
     * @return 条件MAPを満たすかどうか
     * @throws SQLException SQL実行時例外
     */
    public boolean isUseableKeiroStep(
            Map<String, KeiroInCondition> inCondMap,
            FormInputBuilder fb) throws SQLException {
        if (inCondMap == null || inCondMap.size() == 0) {
            return true;
        }
        OprOption opt = getOprOption();
        for (KeiroInCondition inCond : inCondMap.values()) {
            EnumKeiroInConditionKbn inCondKbn = null;
            try {
                inCondKbn = EnumKeiroInConditionKbn.valueOf(
                        Integer.valueOf(inCond.getCondKbn().getSelected()));
                switch (inCondKbn) {
                case CONDKBN_GROUP:
                    //グループリスト取得
                    String[] selectGpSid = inCond.getSelGrp().getSelected();
                    if (!__containsAnyStrArrIntegerList(selectGpSid, opt.belongGrpSidList)) {
                        return false;
                    }
                    break;
                case CONDKBN_POS:
                    String[] selectPosSid = inCond.getSelPos().getSelected();
                    if (!__containsAnyStrArrIntegerList(
                            selectPosSid, Arrays.asList(opt.usrInf.getPosSid()))) {
                        return false;
                    }
                    break;
                case CONDKBN_INPUT:
                    String formId = inCond.getFormId();
                    EnumCompOpr oprKbn = EnumCompOpr.valueOf(
                            Integer.parseInt(inCond.getComp().getSelected()));
                    if (!fb.oprResult(formId, inCond.getFormValue(), oprKbn)) {
                        return false;
                    }
                default:
                    break;
                }
            } catch (EnumOutRangeException e) {
            }
        }
        return true;
    }

    /**
     * <br>[機  能] 稟議情報の登録処理を行う
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl パラメータ情報
     * @param cntCon MlCountMtController
     * @param appRootPath アプリケーションのルートパス
     * @param tempDir テンポラリディレクトリ
     * @param mode 登録モード 0:申請 1:草稿
     * @param pluginConfig プラグイン情報
     * @param smailPluginUseFlg ショートメールプラグイン有効フラグ
     * @param reqMdl リクエスト情報
     * @throws Exception 実行例外
     * @return 稟議リクエスト情報
     */
    public RingiRequestModel entryRingiData(IRng020PeronalParam paramMdl,
                                MlCountMtController cntCon,
                                String appRootPath,
                                String tempDir,
                                int mode,
                                PluginConfig pluginConfig,
                                boolean smailPluginUseFlg,
                                RequestModel reqMdl)
    throws Exception {
        log__.debug("START");

        RngUsedDataBiz usedDataBiz = new RngUsedDataBiz(con__);
        if (paramMdl.getRngCmdMode() == RngConst.RNG_CMDMODE_EDIT) {
            //稟議申請情報のデータ使用量を登録(変更前情報のデータ使用量を減算)
            usedDataBiz.insertSinseiDataSize(paramMdl.getRngSid(), false);
        }

        RingiRequestModel model = new RingiRequestModel();
        model.setRngSid(paramMdl.getRngSid());
        model.setRngTitle(paramMdl.getRng020Title());
        model.setRngContent("");
        model.setAppRootPath(appRootPath);
        model.setTempDir(tempDir);
        model.setUserSid(usrSid__);
        model.setDate(new UDate());
        model.setRtpSid(paramMdl.getRng020rtpSid());
        model.setRtpVer(paramMdl.getRng020rtpVer());

        if (paramMdl.getRng020rtpKeiroVersion() >= 0) {
            model.setRctVer(paramMdl.getRng020rtpKeiroVersion());
        } else {
            model.setRctVer(0);
        }

        RngBiz rngBiz = new RngBiz(con__, cntCon);

        //申請IDの発行
        if (paramMdl.getIdUseFlg() == 1) {
            // 申請IDを手入力していない場合、自動採番した申請IDを使用
            if (paramMdl.getIdPrefManualEditable() == RngConst.RAR_SINSEI_MANUAL_KYOKA
                    && paramMdl.getRng020IdPrefManual() == RngConst.RAR_SINSEI_MANUAL_KYOKA) {
                model.setRngId(paramMdl.getRng020ID());
            } else if (mode == 0) {
                String planId = rngBiz.getNewRngid(
                        paramMdl.getRng020rtpSid(), null, true, paramMdl.getRngSid(), reqMdl);
                model.setRngId(planId);
            }
        }
        //稟議データの登録
        int rngSid = rngBiz.entryRingiData(model, mode,
                paramMdl.getRngCmdMode(), pluginConfig, smailPluginUseFlg, reqMdl);
        model.setRngSid(rngSid);

        //フォームの登録
        rngBiz.entryFormData(model, paramMdl.getRng020input());

        //経路ステップの登録
        entryKeiroStep(paramMdl, rngSid, mode, model,
                cntCon, appRootPath, pluginConfig, smailPluginUseFlg);

        //稟議申請情報のデータ使用量を登録
        usedDataBiz.insertSinseiDataSize(rngSid, true);

        log__.debug("End");

        paramMdl.setRngSid(rngSid);

        return model;
    }

    /**
     * 経路ステップの情報を登録します。
     * @param paramMdl パラメータモデル
     * @param rngSid 稟議SID
     * @param mode 処理モード 0:申請 1:草稿
     * @param model 稟議リクエストモデル
     * @param cntCon 採番コントローラ
     * @param appRootPath ルートパス
     * @param pluginConfig プラグインコンフィグ
     * @param smailPluginUseFlg ショートメールしようフラグ
     * @throws Exception Exception
     * */
    public void entryKeiroStep(IRng020PeronalParam paramMdl, int rngSid,
            int mode,
            RingiRequestModel model,
            MlCountMtController cntCon,
            String appRootPath,
            PluginConfig pluginConfig,
            boolean smailPluginUseFlg)
                    throws Exception {

        Rng020EntryKeiroBiz keiroBiz =
                new Rng020EntryKeiroBiz(con__, reqMdl__, mode,
                        appRootPath, pluginConfig, smailPluginUseFlg);
        keiroBiz.entry(paramMdl, rngSid, cntCon, model);
    }

    /**
     * 申請者と審議者が同じ場合、可能であれば下位経路をスキップする
     *@param paramMdl パラメータモデル
     *@throws SQLException SQL実行例外
     * */
    public void keiroSkip(IRng020PeronalParam paramMdl) throws SQLException {

        // スキップ先経路
        int targetKeiro = -1;
        // スキップ実行の有無 false:スキップしない true:スキップを行う
        boolean isSkip = false;
        Map<Integer, Rng020KeiroBlock> keiroBlock = paramMdl.getRng020keiroMap();
        // 上位経路から順番に、経路をスキップできるか判定
        ListIterator<Integer> keiroKeyItr = new ArrayList<Integer>(keiroBlock.keySet()
                ).listIterator(keiroBlock.size());
        while (keiroKeyItr.hasPrevious()) {
            int key = keiroKeyItr.previous();
            Rng020KeiroBlock block = keiroBlock.get(key);
            //hiddenの経路に対して審議者かどうかの判定を行わない
            if (block.getHidden() == 1) {
                continue;
            }
            int rtkSid = block.getRtkSid();
                    RngTemplateKeiroDao rtkDao = new RngTemplateKeiroDao(con__);
                    RngTemplateKeiroModel rtkMdl = rtkDao.select(rtkSid);
            // 汎用稟議は経路テンプレートを使用しないので、後閲および経路スキップは許可しない
            if (rtkMdl == null) {
                // 後閲許可設定、経路スキップ設定
            } else {
                String usrSid = paramMdl.getRng020requestUserId();
                List<String> usrList = new ArrayList<String>();
                // 各経路をスキップできるのか判定
                for (Map.Entry<Integer, Rng020Keiro> entry : block.getKeiroMap().entrySet()) {
                    Rng020Keiro step = entry.getValue();
                    Map<String, String[]> usrMap = step.getUsrgrpSel().getSelected();
                    String[] grpSids = step.getGrpSel().getMultiselect().getSelected();
                    Map<String, TargetPosSel> posMap = step.getPref().getTargetposMap();
                    // ユーザを選択した経路ステップの場合
                    if (usrMap.size() > 0 && !usrMap.isEmpty()) {
                        // グループSID
                        List<String> grpList = new ArrayList<String>();
                        for (Map.Entry<String, String[]> usrs : usrMap.entrySet()) {
                            for (String usr : usrs.getValue()) {
                                if (usr.startsWith("G")
                                        && ValidateUtil.isNumberHaifun(usr.substring(1))) {
                                    grpList.add(usr.substring(1));
                                } else if (NullDefault.getInt(usr, -1) >= 0) {
                                    usrList.add(usr);
                                }
                                // グループを選択していた場合
                                if (!grpList.isEmpty() && grpList.size() > 0) {
                                    String[] grps = grpList.toArray(new String[grpList.size()]);
                                    CmnBelongmDao belongDao = new CmnBelongmDao(con__);
                                    usrList.addAll(belongDao.select(grps));
                                }
                            }
                        }
                    // グループを選択した経路ステップの場合
                    } else if (!ArrayUtils.isEmpty(grpSids)) {
                        // 単一選択＋グループなしの場合はコンボボックスに空データが入る為、ここで除外
                        List<String> grpList = new ArrayList<String>();
                        for (int i = 0;  i < grpSids.length; i++) {
                            if (NullDefault.getInt(grpSids[i], -1) >= 0) { // 空文字か判定
                                grpList.add(grpSids[i]);
                            }
                        }

                        if (grpList.size() > 0) {
                            String[] grps = grpList.toArray(new String[grpList.size()]);
                            CmnBelongmDao belongDao = new CmnBelongmDao(con__);
                            usrList = belongDao.select(grps);
                        }
                    // 役職指定
                    } else if (posMap.size() > 0 && !posMap.isEmpty()) {
                        List<Integer> blgPosUsrs = new ArrayList<Integer>();
                        for (Entry<String, TargetPosSel> pos : posMap.entrySet()) {
                            int posSid = NullDefault.getInt(
                                    pos.getValue().getPosSel().getSelected(), 0);
                            int grpSid = NullDefault.getInt(
                                    pos.getValue().getGrpSel().getSelected(), -1);
                            CmnUsrmInfDao usrDao = new CmnUsrmInfDao(con__);
                            blgPosUsrs.addAll(usrDao.getBelongUsrsFromPosition(grpSid, posSid));
                        }
                        for (int blgposSid : blgPosUsrs) {
                            usrList.add(String.valueOf(blgposSid));
                        }
                        usrList = new ArrayList<String>(new HashSet<String>(usrList));
                    }
                }
                //申請者が審議者となっている経路の場合、経路のスキップが許可されていればスキップ処理を行う状態にする
                if (usrList.contains(usrSid) && isSkip == false
                        && block.getKeiroSkip() == RngConst.RNG_KEIROSKIP_YES) {
                            targetKeiro = key;
                            isSkip = true;
                //後閲を許可しない経路の場合、スキップ処理を行わない状態にする
                } else if (block.getKoetuKyoka() == RngConst.RNG_KOETU_NO) {
                    isSkip = false;
                }
            }
        }
        //経路のスキップ
        if (isSkip == true) {
            for (Map.Entry<Integer, Rng020KeiroBlock> entry : keiroBlock.entrySet()) {
                Rng020KeiroBlock block = entry.getValue();
                // 申請者を含む経路まで達した場合に処理を抜ける
                if (entry.getKey().equals(targetKeiro)) {
                    block.setApprFlg(RngConst.RNG_RNCSTATUS_APPR);
                    break;
                }
                block.setSkipKyoka(RngConst.RNG_ABLE_SKIP);
                for (Map.Entry<Integer, Rng020Keiro> keiro : block.getKeiroMap().entrySet()) {
                    keiro.getValue().setSkipKyoka(RngConst.RNG_ABLE_SKIP);
                }
            }
        }
    }

    /**
     * <br>[機  能] 稟議テンプレート反映の際、添付ファイルの保存先として使用するディレクトリのパスを取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param tempDir テンポラリディレクトリ
     * @return 添付ファイルの保存先として使用するディレクトリのパス
     */
    public String getTemplateFileDir(String tempDir) {
        String templateDir = IOTools.replaceSlashFileSep(tempDir).toString();
        if (!templateDir.endsWith("/")) {
            templateDir += "/";
        }
        return templateDir + "template" + "/";
    }

    /**
     *
     * <br>[機  能] 稟議データのログ出力
     * <br>[解  説]
     * <br>[備  考]
     * @param model 稟議データ
     * @param paramMdl 展開先パラムモデル
     * @param reqMdl リクエスト情報
     * @param con コネクション
     * @return 出力される稟議データのログ
     * @throws SQLException SQL例外
     */
    public String outputRingiData(RingiRequestModel model, IRng020PeronalParam paramMdl,
                                  RequestModel reqMdl, Connection con) throws SQLException {
        GsMessage gsMsg = new GsMessage(reqMdl);

        String msg = "";
        msg += "[" + gsMsg.getMessage("cmn.title") + "] ";
        msg += model.getRngTitle();
        if (model.getRngId() != null) {
            msg += "\r\n[" + gsMsg.getMessage("rng.rng180.04") + "] ";
            msg += model.getRngId();
        }
        RngTemplateDao tempDao = new RngTemplateDao(con);
        RngTemplateModel tempMdl = tempDao.select(model.getRtpSid(), model.getRtpVer());
        if (tempMdl != null && tempMdl.getRtpType() == RngConst.RNG_TEMPLATE_SHARE) {
            msg += "\r\n[" + gsMsg.getMessage("cmn.share")
                           + gsMsg.getMessage("rng.10") + "] " + tempMdl.getRtpTitle();
        }
        return msg;
    }

    /**
     *
     * <br>[機  能] 入力情報のロード
     * <br>[解  説] DBから読み込み時点ではデータを設定する要素タイプが未定のため
     * <br>        一時データとして格納
     * <br>[備  考]
     * @param paramMdl 展開先パラムモデル
     * @throws SQLException SQL実行時例外
     * @throws RtpNotfoundException 稟議情報が見つからない例外
     */
    private void __load(IRng020PeronalParam paramMdl)
                                throws SQLException, RtpNotfoundException {

        boolean isCopy = paramMdl.isRng020copyApply();

        // 稟議情報を取得する
        RngRndataDao rngDao = new RngRndataDao(con__);
        RngRndataModel rngData = rngDao.select(paramMdl.getRngSid());

        // 稟議情報を設定
        paramMdl.setRng020Title(rngData.getRngTitle());
        paramMdl.setRng020rtpSid(rngData.getRtpSid());
        paramMdl.setRng020rtpVer(rngData.getRtpVer());

        boolean tplChanged = false;

        if (!isCopy) {
            paramMdl.setRng020ID(rngData.getRngId());
        }

        RngTemplateModel model = null;
        int maxVer = 0;
        int rctSid = -1;
        int rctVer = 0;
        int oldVer = -1;
        boolean isUseCopy   = false; // 経路複写情報使用判定
        int     flgRtpVerUp = RngConst.CODE_TPVERCHK_EQ;     // テンプレートバージョンアップ判定フラグ
        if (rngData.getRtpSid() > 0) {
            // 最新バージョン(バージョン番号 = 0)のテンプレート情報取得
            RngFormBuildBiz formBiz = __getFormBiz();
            model = formBiz.getRtpModelMaxVer(con__, paramMdl.getRng020rtpSid());

            isUseCopy = __isUseCopyKeiro(model);
            // テンプレート情報から取得
            maxVer = model.getRtpVer(); // テンプレートの最新バージョン番号
            rctSid = model.getRctSid(); // テンプレートで使用している経路テンプレートSID
            rctVer = model.getRctVer();

            // 旧バージョンのテンプレートを取得
            RngTemplateModel oldModel = formBiz.getRtpModel(con__, paramMdl.getRng020rtpSid(),
                                                                   paramMdl.getRng020rtpVer());

            // 複写時のテンプレート経路変更判定(共有テンプレートのみ)
            boolean isKeiroChange = false;
            if (isUseCopy) {
                if (oldModel == null
                 || rctSid != oldModel.getRctSid()      // 使用する経路テンプレート変更あり
                 || rctVer != rngData.getRctVer()) {    // 経路バージョン変更あり
                    isKeiroChange = true;
                }
            }

            if (maxVer != paramMdl.getRng020rtpVer() || oldModel == null) {
                // 稟議のテンプレートバージョンと最新のテンプレートバージョンで相違あり → 警告あり
                tplChanged = true;
                paramMdl.setRng020rtpVer(maxVer); // テンプレートバージョンを更新
                flgRtpVerUp = RngConst.CODE_TPVERCHK_RTP;
                if (isKeiroChange) {
                    flgRtpVerUp = RngConst.CODE_TPVERCHK_BOTH;
                }
                if (oldModel != null) {
                    oldVer = oldModel.getRtpVer();
                }
            } else if (isKeiroChange) {
                // 稟議の経路バージョンと最新の経路バージョンで相違あり → 警告あり
                flgRtpVerUp = RngConst.CODE_TPVERCHK_RCT;
            }
        }
        paramMdl.setRtpVerUpdated(flgRtpVerUp);      // バージョンアップ判定フラグ
        paramMdl.setRng020useCopyKeiro(isUseCopy);   // 経路複写使用フラグ
        paramMdl.setRng020rtpKeiroVersion(rctVer);
        //フォーム入力ビルダーに保存データをロード
        RngFormBuildBiz rngFormBiz = __getFormBiz();
        rngFormBiz.loadInputData(con__, paramMdl.getRng020input(),
                rngData.getRngSid(), rngData.getRtpVer(), tplChanged,
                paramMdl.getRng020rtpSid(),
                paramMdl.getRng020rtpVer());

        // 使用中の経路バージョンに変更が無い場合 → 稟議に紐付く経路を読み込む
        if (paramMdl.getRtpVerUpdated() <= 1) {
            //稟議経路情報を設定
            __loadKeiro(paramMdl, oldVer);
        }

    }
    /**
     *
     * <br>[機  能] 経路部のロード処理
     * <br>[解  説]
     * <br>[備  考]
     * @param paramMdl 展開先パラムモデル
     * @param oldVer   旧テンプレートのバージョン番号(テンプレート更新がない場合 = -1)
     * @throws SQLException SQL実行時例外
     */
    private void __loadKeiro(IRng020PeronalParam paramMdl, int oldVer) throws SQLException {
        int rngSid = paramMdl.getRngSid();
        boolean isUseCopy = (paramMdl.isRng020copyApply() && paramMdl.isRng020useCopyKeiro());

        Map<Integer, Rng020Keiro> rksSidMap = new HashMap<Integer, Rng020Keiro>();
        RngKeiroStepDao rksDao = new RngKeiroStepDao(con__);
        RngKeiroStepDao.SearchModel search = new SearchModel();
        search.setRngSid(rngSid);
        /**関連経路マップ*/
        Map<Integer, Rng020KeiroBlock> belongMapAppr = new HashMap<Integer, Rng020KeiroBlock>();
        Map<Integer, Rng020KeiroBlock> belongMapConfirm = new HashMap<Integer, Rng020KeiroBlock>();
        List<RngKeiroStepModel> rksList = rksDao.select(search);

        Map<Integer, RngKeiroStepModel> confirmMap = new HashMap<Integer, RngKeiroStepModel>();
        List<Integer> checkList = new ArrayList<Integer>();

        // 旧バージョンがある場合、取得した経路ステップ一覧を最新バージョンへ書き換える
        HashMap<Integer, Integer> convtRtkSidMap = new HashMap<Integer, Integer>();
        if (oldVer >= 0) {
            // 新バージョンと旧バージョンのテンプレート経路SID
            SearchParamForRTP tplSerach = new SearchParamForRTP(paramMdl.getRng020rtpSid(), oldVer);
            RngTemplateKeiroDao rtkDao = new RngTemplateKeiroDao(con__);
            List<RngTemplateKeiroModel> oldRtkList = rtkDao.select(tplSerach, RngConst.JKBN_ALL);

            tplSerach.setRtpVer(paramMdl.getRng020rtpVer());
            List<RngTemplateKeiroModel> newRtkList = rtkDao.select(tplSerach, RngConst.JKBN_ALL);

            for (RngTemplateKeiroModel newRtk : newRtkList) {
                for (RngTemplateKeiroModel oldRtk : oldRtkList) {
                    // 経路役割とソート番号が一致している場合、同一の経路として判定
                    if (newRtk.getRtkRollType() == oldRtk.getRtkRollType()
                     && newRtk.getRtkSort()     == oldRtk.getRtkSort()) {
                        convtRtkSidMap.put(oldRtk.getRtkSid(), newRtk.getRtkSid());
                        oldRtkList.remove(oldRtk); // 使用済みのデータは除去
                        break;
                    }
                }
            }
        }

        for (RngKeiroStepModel rksMdl : rksList) {

            // テンプレート更新があった場合にテンプレート経路SIDを更新
            if (convtRtkSidMap.size() > 0) {
                int rtkSid    = rksMdl.getRtkSid();
                int belongSid = rksMdl.getRksBelongSid();

                if (rtkSid > 0 && convtRtkSidMap.containsKey(rtkSid)) {
                    rksMdl.setRtkSid(convtRtkSidMap.get(rtkSid));
                }
                if (belongSid > 0 && convtRtkSidMap.containsKey(belongSid)) {
                    rksMdl.setRksBelongSid(convtRtkSidMap.get(belongSid));
                }
            }

            if (rksMdl.getRtkSid() > 0) {
                if (checkList.contains(rksMdl.getRtkSid())) {
                    continue; // 重複する稟議テンプレートステップSIDはスキップする
                }
                checkList.add(rksMdl.getRtkSid()); // 重複チェック用に追加
            }

            Rng020Keiro keiro = null;
            Rng020KeiroBlock keiroBlock = new Rng020KeiroBlock();
            keiro = keiroBlock.getKeiroSingle();
            if (keiro != null) {
                rksSidMap.put(rksMdl.getRksSid(), keiro);
                keiro.setStep(rksMdl);
                keiro.setRtkSid(rksMdl.getRtkSid());
                keiroBlock.setRtkSid(rksMdl.getRtkSid());
            }

            //経路が任意経路内経路ではない場合
            if (rksMdl.getRksBelongSid() < 0) {
                if (rksMdl.getRksRollType() == RngConst.RNG_RNCTYPE_APPR) {
                    paramMdl.putRng020keiro(keiroBlock);
                }
                if (rksMdl.getRksRollType() == RngConst.RNG_RNCTYPE_CONFIRM) {
                    if (isUseCopy) {
                        // 複写用経路データ使用する場合、一旦配列へ格納する
                        confirmMap.put(rksMdl.getRksSid(), rksMdl);
                    } else {
                        // 複写用経路データ使用しない場合、そのまま取得する
                        paramMdl.putRng020kakuninKeiro(keiroBlock);
                    }
                }
            } else {
                keiroBlock.setRtkSid(rksMdl.getRksBelongSid()); // 経路に親経路が存在する場合、親経路SIDをセット

                Map<Integer, Rng020KeiroBlock> belongMap;
                if (rksMdl.getRksRollType() == RngConst.RNG_RNCTYPE_APPR) {
                    belongMap = belongMapAppr;
                } else  {
                    belongMap = belongMapConfirm;
                }
                if (!belongMap.containsKey(rksMdl.getRksBelongSid())) {
                    belongMap.put(rksMdl.getRksBelongSid(), keiroBlock);
                    if (rksMdl.getRksRollType() == RngConst.RNG_RNCTYPE_APPR) {
                        paramMdl.putRng020keiro(keiroBlock);
                    }
                    if (rksMdl.getRksRollType() == RngConst.RNG_RNCTYPE_CONFIRM) {
                        if (isUseCopy) {
                            // 複写用経路データ使用する場合、一旦配列へ格納する
                            confirmMap.put(rksMdl.getRksSid(), rksMdl);
                        } else {
                            // 複写用経路データ使用しない場合、そのまま取得する
                            paramMdl.putRng020kakuninKeiro(keiroBlock);
                        }
                    }
                } else {
                    //任意設定経路の先頭以降の場合、関連経路マップの経路ブロックに経路ステップを追加
                    keiroBlock = belongMap.get(rksMdl.getRksBelongSid());
                    keiroBlock.setKeiro(keiroBlock.getKeiroMap().size(), keiro);
                }
            }
        }
        RngKeirostepSelectDao rssDao = new RngKeirostepSelectDao(con__);

        List<RngKeirostepSelectModel> rssList = rssDao.select(rngSid);
        for (RngKeirostepSelectModel rssMdl : rssList) {
            Rng020Keiro keiro = rksSidMap.get(rssMdl.getRksSid());
            if (keiro != null) {
                List<RngKeirostepSelectModel> select = keiro.getInitSelect();
                if (select == null) {
                    select = new ArrayList<RngKeirostepSelectModel>();
                    keiro.setInitSelect(select);
                }
                select.add(rssMdl);
            }
        }

        // -----------------------------------------------------
        //  複写用の経路データから画面に表示する経路情報作成
        // -----------------------------------------------------
        if (confirmMap.size() > 0) {
            rksSidMap = new HashMap<Integer, Rng020Keiro>();
            List<Integer> sidList = new ArrayList<Integer>(confirmMap.keySet());

            // 経路ステップ選択情報を作成
            RngCopyKeirostepSelectDao rcsDao = new RngCopyKeirostepSelectDao(con__);
            ArrayList<RngCopyKeirostepSelectModel> rcsList = rcsDao.selectByRksSid(sidList);
            Map<String, List<RngKeirostepSelectModel>> rcsMap =
                    new HashMap<String, List<RngKeirostepSelectModel>>();
            for (RngCopyKeirostepSelectModel rcsMdl : rcsList) {
                Integer rksSid  = Integer.valueOf(rcsMdl.getRksSid());
                Integer rckSort = Integer.valueOf(rcsMdl.getRckSort());
                String key = rksSid + "-" + rckSort;
                List<RngKeirostepSelectModel> select = rcsMap.get(key);
                if (select == null) {
                    select = new ArrayList<RngKeirostepSelectModel>();
                    rcsMap.put(key, select);
                }
                select.add(rcsMdl.getRssMdl());
            }

            // 経路ステップ情報を作成
            RngCopyKeiroStepDao rckDao = new RngCopyKeiroStepDao(con__);
            ArrayList<RngCopyKeiroStepModel> rckList = rckDao.select(sidList);
            for (RngCopyKeiroStepModel rckMdl : rckList) {
                Rng020KeiroBlock keiroBlock = new Rng020KeiroBlock();
                Rng020Keiro keiro = keiroBlock.getKeiroSingle();
                Integer rksSid  = Integer.valueOf(rckMdl.getRksSid());
                RngKeiroStepModel rksMdl = null;

                // テンプレート更新があった場合にテンプレート経路SIDを更新
                if (convtRtkSidMap.size() > 0) {
                    int rtkSid    = rckMdl.getRtkSid();
                    int belongSid = rckMdl.getRksBelongSid();

                    if (rtkSid > 0 && convtRtkSidMap.containsKey(rtkSid)) {
                        rckMdl.setRtkSid(convtRtkSidMap.get(rtkSid));
                    }
                    if (belongSid > 0 && convtRtkSidMap.containsKey(belongSid)) {
                        rckMdl.setRksBelongSid(convtRtkSidMap.get(belongSid));
                    }
                }

                if (rckMdl.getRtkSid() > 0) {
                    if (checkList.contains(rckMdl.getRtkSid())) {
                        continue; // 重複する稟議テンプレートステップSIDはスキップする
                    }
                    checkList.add(rckMdl.getRtkSid()); // 重複チェック用に追加
                }

                if (keiro != null) {
                    rksMdl = rckMdl.margeRksMdl(confirmMap.get(rksSid));
                }

                if (rksMdl != null) {
                    keiro.setStep(rksMdl);
                    keiro.setRtkSid(rckMdl.getRtkSid());
                    keiroBlock.setRtkSid(rksMdl.getRtkSid());

                    Integer rckSort = Integer.valueOf(rckMdl.getRckSort());
                    String key = rksSid + "-" + rckSort;
                    List<RngKeirostepSelectModel> select = rcsMap.get(key);
                    if (select != null) {
                        keiro.setInitSelect(select);
                    }

                    if (rksMdl.getRksBelongSid() >= 0) {
                        // 経路に親経路が存在する場合、親経路SIDをセット
                        keiroBlock.setRtkSid(rksMdl.getRksBelongSid());

                        if (!belongMapConfirm.containsKey(rksMdl.getRksBelongSid())) {
                            belongMapConfirm.put(rksMdl.getRksBelongSid(), keiroBlock);
                        } else {
                            //任意設定経路の先頭以降の場合、関連経路マップの経路ブロックに経路ステップを追加
                            keiroBlock = belongMapConfirm.get(rksMdl.getRksBelongSid());
                            keiroBlock.setKeiro(keiroBlock.getKeiroMap().size(), keiro);
                            continue;
                        }
                    }
                }
                // 複写データから作成した経路情報を最終確認経路へ追加
                paramMdl.putRng020kakuninKeiro(keiroBlock);
            }
        }
    }

    /**
     * <p>formBiz を取得します。
     * @return formBiz
     */
    private RngFormBuildBiz __getFormBiz() {
        if (formBiz__ == null) {
            formBiz__ = new RngFormBuildBiz(reqMdl__);
        }
        return formBiz__;
    }

    /**
     * <p>テンプレート情報(最新バージョン)を取得します。
     * @param rtpSid テンプレートSID
     * @return テンプレート情報
     * @throws SQLException SQL実行時例外
     */
    public RngTemplateModel getTemplateMaxVer(int rtpSid)
            throws SQLException {
        RngTemplateModel rtpMdl = null;
        try {
            RngFormBuildBiz formBiz = __getFormBiz(); // テンプレート情報を取得する為に使用
            rtpMdl = formBiz.getRtpModelMaxVer(con__, rtpSid);
        } catch (RtpNotfoundException e) {
        }
        return rtpMdl;
    }

    /**
     * <p>パラメータから複写用データを使用するか判定します。
     * @param rtpMdl   テンプレート情報
     * @return 使用判定
     */
    private boolean __isUseCopyKeiro(RngTemplateModel rtpMdl) {
        if (rtpMdl != null && rtpMdl.getRtpSid() > 0                  // 汎用テンプレート以外
         && rtpMdl.getRtpSpecVer() == RngConst.RNG_RTP_SPEC_VER_A480  // v4.8.0以降のテンプレート
         && rtpMdl.getRtpType() == RngConst.RNG_TEMPLATE_SHARE) {     // 共有テンプレート
            return true;
        }
        return false;
    }

    /**
     *
     * <br>[機  能] 稟議の閲覧権限チェック
     * <br>[解  説] 指定のアカウントで稟議が閲覧できるかを返す
     * @param userSid アクセスするアカウント
     * @param rngSid 稟議SID
     * @param mode 0:新規作成 1:草稿編集
     * @return false:稟議へのアクセス権限が不正
     * @throws SQLException SQL実行時例外
     */
    public boolean chkViewRingi(
            int userSid, int rngSid, int mode
            ) throws SQLException {
        //草稿の稟議の閲覧権限チェック
        // 稟議情報を取得する
        RngRndataDao rngDao = new RngRndataDao(con__);
        RngRndataModel rngMdl = rngDao.select(rngSid);

        if (rngMdl == null) {
            return false;
        }
        //草稿編集時
        if (mode == RngConst.RNG_CMDMODE_EDIT) {
            //自分の稟議の場合
            if (rngMdl.getRngAuid() != userSid) {
                return false;
            }
            //草稿ではない稟議
            if (rngMdl.getRngStatus() != RngConst.RNG_STATUS_DRAFT) {
                return false;
            }
            //
            return true;
        }

        //草稿の稟議
        if (rngMdl.getRngStatus() == RngConst.RNG_STATUS_DRAFT) {
            return false;
        }
        //自分の稟議の場合
        if (rngMdl.getRngAuid() == userSid) {
            return true;
        }
        //複写登録元の稟議は内容確認画面と同じ判定で取得する
        Rng030Biz rng030Biz = new Rng030Biz(con__, reqMdl__);
        return rng030Biz.chkViewRingi(userSid, rngSid);
    }


    /**
     *
     * <br>[機  能] テンプレートバージョン比較
     * <br>[解  説] テンプレートバージョン変更の検証結果をコード定数で返す
     * <br>[備  考]
     * @param rtpMdl 最新テンプレート
     * @param prevMdl 前回テンプレート
     * @param rngRctVer 申請時の経路バージョン
     * @return コード定数 RngConst.CODE_TPVERCHK_{*}
     * @throws RtpNotfoundException テンプレートが存在しない
     * @throws SQLException SQL実行時例外
     */
    public int chkRtpUpdated(
            RngTemplateModel rtpMdl, RngTemplateModel prevMdl, int rngRctVer
            ) throws SQLException, RtpNotfoundException {

        if (rtpMdl.getRtpSid() != prevMdl.getRtpSid()) {
            return RngConst.CODE_TPVERCHK_BOTH;
        }

        //バージョン違いのテンプレートの復元にコンバート処理が必要なテンプレートかどうか
        if (rtpMdl.getRtpSpecVer() < RngConst.RNG_RTP_SPEC_VER_A480) {
            return RngConst.CODE_TPVERCHK_EQ;
        }

             // テンプレートバージョンアップ判定フラグ
        int rctVer = rtpMdl.getRctVer();
        boolean isKeiroChange = false;
        if (rtpMdl.getRctSid() != prevMdl.getRctSid()      // 使用する経路テンプレート変更あり
            || rctVer != rngRctVer) {    // 経路バージョン変更あり
               isKeiroChange = true;
        }

        if (rtpMdl.getRtpVer() == prevMdl.getRtpVer()) {
            //テンプレートバージョンの更新がない
            if (isKeiroChange) {
                //かつ経路テンプレートバージョンの更新
                return RngConst.CODE_TPVERCHK_RCT;
            }
            return RngConst.CODE_TPVERCHK_EQ;
        } else {
            if (isKeiroChange) {
                //かつ経路テンプレートバージョンの更新
                return RngConst.CODE_TPVERCHK_BOTH;
            }
            return RngConst.CODE_TPVERCHK_RTP;
        }

    }
    /**
     *
     * <br>[機  能] 再描画時に元の画面からの入力値の引継ぎを設定する
     * <br>[解  説] 再描画時にテンプレートのバージョン更新に対応して値引継ぎを制御する
     * <br>[備  考]
     * @param paramMdl パラメータモデル
     * @param tempDir テンポラリディレクトリ
     * @throws RtpNotfoundException 不正なテンプレート参照例外
     * @throws SQLException SQL実行時例外
     * @throws IOToolsException IO例外
     * @throws IOException  IO例外
     */
    public void setRedspData(
            IRng020PeronalParam paramMdl, GSTemporaryPathModel tempDir
            ) throws SQLException, RtpNotfoundException, IOException, IOToolsException {
        RngFormBuildBiz formBiz = __getFormBiz();


        RngTemplateModel prevMdl = formBiz.getRtpModel(con__, paramMdl.getRng020rtpSid(),
                paramMdl.getRng020rtpVer());
        RngTemplateModel rtpMdl = formBiz.getRtpModelMaxVer(con__, paramMdl.getRng020rtpSid());
        /** テンプレートバージョンアップ判定フラグ */
        int flgRtpVerUp = chkRtpUpdated(rtpMdl, prevMdl, paramMdl.getRng020rtpKeiroVersion());


        //メッセージ表示用にテンプレートバージョンアップ判定結果を保管
        paramMdl.setRtpVerUpdated(flgRtpVerUp);
        paramMdl.setRng020rtpVer(rtpMdl.getRtpVer());

        boolean isKeiroChange = false;
        boolean isNaiyoChange = false;

        switch (flgRtpVerUp) {
        case RngConst.CODE_TPVERCHK_EQ:
            return;
        case RngConst.CODE_TPVERCHK_RTP:
            isNaiyoChange = true;
            break;
        case RngConst.CODE_TPVERCHK_RCT:
            isKeiroChange = true;
            break;
        default:
            isKeiroChange = true;
            isNaiyoChange = true;
            break;
        }

        if (isNaiyoChange) {
            FormInputBuilder old = paramMdl.getRng020input();

            FormInputBuilder reset = formBiz.convertInputData(
                    con__, old, rtpMdl, prevMdl,
                    tempDir);

            paramMdl.setRng020input(reset);
        }

        if (isKeiroChange) {
            // 使用中の経路バージョンに変更がある場合、経路情報をリセット
            paramMdl.setRng020keiroMap(new HashMap<Integer, Rng020KeiroBlock>());
            paramMdl.setRng020kakuninKeiroMap(new HashMap<Integer, Rng020KeiroBlock>());
            paramMdl.setLoadRctSid(0);
        }
    }

}
