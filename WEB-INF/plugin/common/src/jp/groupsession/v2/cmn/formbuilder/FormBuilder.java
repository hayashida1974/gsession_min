package jp.groupsession.v2.cmn.formbuilder;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

import jp.co.sjts.util.EnumUtil;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.json.JSON;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.formmodel.Block;
import jp.groupsession.v2.cmn.formmodel.BlockList;
import jp.groupsession.v2.cmn.formmodel.Calc;
import jp.groupsession.v2.cmn.formmodel.Calc.Format;
import jp.groupsession.v2.cmn.formmodel.Sum;
import jp.groupsession.v2.cmn.formmodel.prefarence.BlockListPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.BlockPrefarence;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
/**
 *
 * <br>[機  能] ドラッグアンドドロップでフォーム画面を作成するためのフォームモデル
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FormBuilder extends BlockPrefarence {
    /** ドラッグ一覧*/
    private List<FormCell> drags__;
    /** フォーム要素Map フォームアクセサクラスをキーとするMap 展開されたフォームをストックしておく*/
    private Map<FormAccesser, FormCellPrefarence> formMap__
      = new HashMap<FormAccesser, FormCellPrefarence>();
    /** テンポラリディレクトリパス*/
    private GSTemporaryPathModel tempPath__;

    /**
     * <p>drags を取得します。
     * @return drags
     */
    public List<FormCell> getDrags() {
        return drags__;
    }

    /**
     * <p>drags をセットします。
     * @param drags drags
     */
    public void setDrags(List<FormCell> drags) {
        drags__ = drags;
    }

    /**
     * <p>inputMap を取得します。
     * @return inputMap
     */
    public Map<FormAccesser, FormCellPrefarence> getFormMap() {
        return formMap__;
    }

    /**
     * <p>tempPath を取得します。
     * @return tempPath
     * @see jp.groupsession.v2.cmn.formbuilder.FormBuilder#tempPath__
     */
    public GSTemporaryPathModel getTempPath() {
        return tempPath__;
    }
    /**
     *
     * <br>[機  能] 配置順にならんだFormCell一覧を取得する
     * <br>[解  説]
     * <br>[備  考] 表フォーム、ブロック要素内のFormCellも含む
     * @return FormCell一覧
     */
    public List<FormCell> getFormCellList() {
        return getFormTable().stream()
        .flatMap(row -> row.stream())
        //ブロック要素、フォーム要素内も展開
        .flatMap(cell -> {
            if (cell.getType() == EnumFormModelKbn.block) {
                return Stream.concat(
                            Stream.of(cell),
                            ((Block) cell.getBody()).getFormTable().stream()
                            .flatMap(row -> row.stream())
                        );
            }
            if (cell.getType() == EnumFormModelKbn.blocklist) {
                return Stream.of(
                            Stream.of(cell),
                            ((BlockList) cell.getBody()).getHeader().getFormTable()
                                .stream()
                                .flatMap(row -> row.stream()),
                            ((BlockList) cell.getBody()).getBodyList().stream()
                                .flatMap(blk -> blk.getFormTable().stream())
                                .flatMap(row -> row.stream()),
                            ((BlockList) cell.getBody()).getFooter().getFormTable()
                                .stream()
                                .flatMap(row -> row.stream())
                            )
                        .flatMap(str -> str);
            }
            return Stream.of(cell);
        })
        .collect(Collectors.toList());
    }

    /**
     *
     * <br>[機  能] 表示初期化処理
     * <br>[解  説]
     * <br>[備  考]
     * @param reqMdl リクエストモデル
     * @param con コネクション
     * @param tempPath
     * @exception SQLException SQL実行時例外
     */
    public void dspInit(RequestModel reqMdl,
            Connection con,
            GSTemporaryPathModel tempPath) throws SQLException {
        //汎用ドラッグ要素リスト初期化
        EnumUtil<EnumFormModelKbn> enumUtil = new EnumUtil<>(EnumFormModelKbn.class);
        GsMessage gsMsg = new GsMessage(reqMdl);
        List<LabelValueBean> kbnList = enumUtil.getSelectNameList(gsMsg);

        drags__ = new ArrayList<FormCell>();
        for (LabelValueBean kbn : kbnList) {
            drags__.add(new FormCell(kbn));
        }

        tempPath__ = tempPath;
    }

    @Override
    public void makeFormMap(Map<FormAccesser, FormCell> target, int rowNo) {
        Map<FormAccesser, FormCell> childMap = getChildMap();
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

    @Override
    public void validateCheck(ActionErrors errors, RequestModel reqMdl,
            ValidateInfo info) {
        //FormBuilderの入力チェックはブロック要素、表リスト内の空チェックを行う
        Collection<FormCell> cells = getChildMap().values();
        ActionMessage msg = null;
        GsMessage gsMsg = new GsMessage(reqMdl);
        if (CollectionUtils.isEmpty(cells)) {
            if (info.getRequire() == 1) {
                //未入力チェック
                    msg = new ActionMessage("error.select2.required.text",
                            info.outputName(gsMsg),
                            gsMsg.getMessage("rng.rng090.04"));
                    errors.add(info.getPath(), msg);
            }
            return;
        }
        HashSet<Integer> rootYousoSids = new HashSet<Integer>();
        for (FormCell formCell : cells) {
            EnumFormModelKbn formKbn = formCell.getType();
            Block chkBlock;
            ValidateInfo child;
            rootYousoSids.add(formCell.getSid());
            if (info == null) {
                child = new ValidateInfo();
            } else {
                child = info.child();
            }
            child.setPath(info.getTitle());
            child.setTitle(formCell.getTitle());
            switch (formKbn) {
            case block:
                chkBlock = (Block) formCell.getBody();
                if (chkBlock.getChildMap().size() == 0) {
                    //未入力チェック
                    msg = new ActionMessage("error.select2.required.text",
                            child.outputName(gsMsg),
                            gsMsg.getMessage("rng.rng090.04"));
                    errors.add(info.getPath(), msg);
                }
                break;
            case blocklist:
                chkBlock = ((BlockList) formCell.getBody()).getBody(0);
                child = child.child();
                child.setPath(info.getTitle());
                child.setTitle(gsMsg.getMessage("rng.rng090.08"));
                if (chkBlock.getChildMap().size() == 0) {
                    //未入力チェック
                    msg = new ActionMessage("error.select2.required.text",
                            child.outputName(gsMsg),
                            gsMsg.getMessage("rng.rng090.04"));
                    errors.add(info.getPath(), msg);
                }
                break;
            default:
                break;
            }
        }
        //フォームID、フォームSIDの重複チェック用
        HashSet<String> formIdSet = new HashSet<String>();
        HashSet<Integer> formSidSet = new HashSet<Integer>();

        for (Entry<FormAccesser, FormCellPrefarence> entry : formMap__.entrySet()) {
            FormCellPrefarence cell = entry.getValue();
            FormAccesser access = entry.getKey();
            int ecnt = errors.size();
            if (formSidSet.contains(access.getFormSid())) {
                //フォームSIDの重複（JSONの直接編集と思われる）
                msg = new ActionMessage("error.select.dup.list", "SID");
                StrutsUtil.addMessage(errors, msg, "error.select.dup.list");
            }
            if (!StringUtil.isNullZeroString(cell.getFormID())
                && formIdSet.contains(cell.getFormID())) {
                //フォームIDの重複
                msg = new ActionMessage("error.select.dup.list", gsMsg.getMessage("rng.rng110.31"));
                StrutsUtil.addMessage(errors, msg, "error.select.dup.list");
            }
            if (errors.size() > ecnt) {
                return;
            }
            formSidSet.add(access.getFormSid());
            if (!StringUtil.isNullZeroString(cell.getFormID())) {
                formIdSet.add(cell.getFormID());
            }
            if (rootYousoSids.contains(access.getFormSid())) {
                cell.setTitleRequireFlg(1);
            } else {
                cell.setTitleRequireFlg(0);
            }
            cell.validate(errors, reqMdl, info);

            //自動計算のみ他要素のformIDが存在するかの確認を行う
            if (cell.getType().equals(EnumFormModelKbn.sum)) {
                Sum sum =  (Sum) cell.getBody();
                for (Format format : sum.getTarget()) {
                    if (format.getType() == -1 || format.getType() == 0) {
                        continue;
                    }
                    int errorNo = __autoCalcJudg(format.getValue());
                    if (errorNo == 1) {
                        msg = new ActionMessage("error.form.select.check.youso", cell.getFormID());
                        StrutsUtil.addMessage(errors, msg, "error.form.select.check.youso");
                    } else if (errorNo == 2) {
                        msg = new ActionMessage("error.form.select.check.id", cell.getFormID());
                        StrutsUtil.addMessage(errors, msg, "error.form.select.check.id");
                    }
                }
            } else if (cell.getType().equals(EnumFormModelKbn.calc)) {
                Calc calc =  (Calc) cell.getBody();
                String siki = calc.getSiki();
                boolean bLoop = true;
                int idx = 0;
                while (bLoop) {
                    int start = siki.indexOf("[", idx);
                    int finish = siki.indexOf("]", idx);
                    if (start == -1 || finish == -1) {
                        bLoop = false;
                        break;
                    }
                    if (start != -1) {
                        String formId = siki.substring(start + 1, finish);
                        int errorNo = __autoCalcJudg(formId);
                        if (errorNo == 1) {
                            msg = new ActionMessage("error.form.select.check.youso",
                                    cell.getFormID());
                            StrutsUtil.addMessage(errors, msg, "error.form.select.check.youso");
                        } else if (errorNo == 2) {
                            msg = new ActionMessage("error.form.select.check.id", cell.getFormID());
                            StrutsUtil.addMessage(errors, msg, "error.form.select.check.id");
                        }
                    }
                    idx  = finish + 1;
                }
            }
        }
    }

    /**
     *
     * <br>[機  能]自動計算要素使用時に選択されているformIdが正しいかの判定
     * <br>[解  説]
     * <br>[備  考]
     * @param formId フォームID
     * @return nRtn 0:正しい 1:要素が数値、自動計算以外 2:存在しないformId
     */
    private int __autoCalcJudg(String formId) {

        int nRtn = 0;
        if (formId == null) {
            return nRtn;
        }
        boolean errorId = false;
        boolean errorYouso = false;
        for (Entry<FormAccesser, FormCellPrefarence> loopEntry : formMap__.entrySet()) {
            FormCellPrefarence loopCell = loopEntry.getValue();
            if (formId.equals(loopCell.getFormID())) {
                if (!(loopCell.getType() == EnumFormModelKbn.number
                        || loopCell.getType() == EnumFormModelKbn.sum
                        || loopCell.getType() == EnumFormModelKbn.calc)) {
                    errorYouso = true;
                    break;
                }
                errorId = true;
            }
        }
        if (errorYouso && formId.length() != 0) {
            nRtn = 1;
        } else if (!errorId || formId.length() == 0) {
            nRtn = 2;
        }
        return nRtn;
    }

    @Override
    public void setFormTable(List<List<FormCell>> formTable) {
        formMap__.clear();
        super.setFormTable(formTable);
        Map<FormAccesser, FormCell> formMap = new HashMap<FormAccesser, FormCell>();
        makeFormMap(formMap, 0);
        for (Entry<FormAccesser, FormCell> entry : formMap.entrySet()) {
            if (entry.getValue() instanceof FormCellPrefarence) {
                formMap__.put(entry.getKey(), (FormCellPrefarence) entry.getValue());
            }
        }
    }

    /**
     * 要素追加時に
     * 要素リストを追加する
     */
    @Override
    public void setFormCell(int row, int col, FormCell cell) {
        if (!(cell instanceof FormCellPrefarence)) {
            FormCellPrefarence prefCell = new  FormCellPrefarence(cell.toJson());
            cell = prefCell;
        }
        super.setFormCell(row, col, cell);
        if (cell.getBody() instanceof BlockPrefarence) {
            BlockPrefarence block = (BlockPrefarence) cell.getBody();
            setAccessIndex(block.getChildMap());
            block.setRoot(this);
        }
        if (cell.getBody() instanceof BlockListPrefarence) {

            BlockListPrefarence block = (BlockListPrefarence) cell.getBody();
            Map<FormAccesser, FormCell> children
              = new HashMap<FormAccesser, FormCell>();
            block.getBody(0).makeFormMap(children, 0);
            setAccessIndex(children);
            //bodyリストを空にしておく
            setAccessIndex(block.getHeader().getChildMap());
            setAccessIndex(block.getFooter().getChildMap());
            block.setRoot(this);
        }
        formMap__.put(new FormAccesser(cell.getSid(), 0), (FormCellPrefarence) cell);
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
       if (cell != null
            && cell instanceof FormCellPrefarence) {
           if (accsess != null) {
               if (formMap__.containsKey(accsess)) {
                   return;
               }
               formMap__.put(accsess, (FormCellPrefarence) cell);
           }
       }
   }
   /**
   *
   * <br>[機  能] json文字列からフォーム情報の入力チェック
   * <br>[解  説]
   * <br>[備  考]
   * @param json JSON文字列
   * @return 入力チェック結果
   */
   public ActionErrors validateJson(JSON json) {
       return new ActionErrors();
   }

}
