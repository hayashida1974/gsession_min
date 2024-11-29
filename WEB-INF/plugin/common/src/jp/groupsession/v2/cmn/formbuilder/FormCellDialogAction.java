package jp.groupsession.v2.cmn.formbuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.co.sjts.util.NullDefault;
import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.IOToolsException;
import jp.co.sjts.util.json.JSONException;
import jp.co.sjts.util.json.JSONObject;
import jp.groupsession.v2.cmn.GSTemporaryPathUtil;
import jp.groupsession.v2.cmn.biz.CommonBiz;
import jp.groupsession.v2.cmn.config.PluginConfig;
import jp.groupsession.v2.cmn.formmodel.Temp;
import jp.groupsession.v2.cmn.formmodel.prefarence.GroupComboModelPrefarence;
import jp.groupsession.v2.cmn.formmodel.prefarence.SimpleUserSelectPrefarence;
import jp.groupsession.v2.cmn.model.GSTemporaryPathModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.AbstractGsAction;
/**
 *
 * <br>[機  能] フォームビルダーフォーム設定ダイアログ アクション
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class FormCellDialogAction extends AbstractGsAction {
    /** */
    public static final String EDIT = "edit";
    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(FormCellDialogAction.class);

    /**
     * <br>[機  能] adminユーザのアクセスを許可するのか判定を行う。
     * <br>[解  説]
     * <br>[備  考]
     * @return true:許可する,false:許可しない
     */
    @Override
    public boolean canNotAdminUserAccess() {
        return true;
    }

    @Override
    public ActionForward executeAction(ActionMapping map, ActionForm form,
            HttpServletRequest req, HttpServletResponse res, Connection con)
            throws Exception {
        //コマンドパラメータ取得
        String cmd = NullDefault.getString(req.getParameter("CMD"), "");
        log__.debug("CMD = " + cmd);
        FormCellDialogForm thisForm = (FormCellDialogForm) form;
        JSONObject json = JSONObject.fromObject(thisForm.getJson());
        FormCellPrefarence cell = new FormCellPrefarence(json);
        cell.setTitleRequireFlg(thisForm.getTitleRequireFlg());
        thisForm.setCell(cell);
        if (cmd.equals("dsp")) {
            //テンプレートボディ描画設定処理
            //設定画面ではなく設定結果のためFormCellクラスを使用する
            thisForm.setCell(new FormCell(json));
        }

        if (cell.getType().equals(EnumFormModelKbn.file)) {
            // サーバで仕様可能なプラグイン一覧と指定されたプラグイン名を比較
            PluginConfig pconfig = getPluginConfig(req);
            if (StringUtil.isNullZeroString(thisForm.getPluginId())
                 || (pconfig.getPlugin(thisForm.getPluginId()) == null)) {
                // 使用可能なプラグインが指定されていない為、ここで終了
                return map.findForward(cmd);
            }

            __doInitFileDialog(cmd, thisForm, req, con);
        }

        if (cmd.equals("dsp")) {
            //テンプレートボディ描画設定処理
            //設定画面ではなく
            thisForm.getCell().dspInit(getRequestModel(req), con);

            try {
                String parentJquery = json.getString("parentJquery");
                thisForm.setParentJquery(parentJquery);
            } catch (JSONException e) {

            }
            return map.findForward(cmd);
        }

        if (cmd.equals("dialogSubmit")) {
            //入力チェックを行う
            ActionErrors errors = new ActionErrors();
            cell.validate(errors,
                    getRequestModel(req));
            if (errors != null && !errors.isEmpty()) {
                addErrors(req, errors);
            }

            if (cell.getType() == EnumFormModelKbn.file && errors.isEmpty()) {
                __doSubmitFileDialog(thisForm, req, con);
            }
        }
        //描画設定処理
        thisForm.getCell().dspInit(getRequestModel(req), con);
        if (thisForm.getCell().getType() == EnumFormModelKbn.user) {
            thisForm.setSelectorTypeText(EnumFormModelKbn.user.name());
        }
        if (thisForm.getCell().getType() == EnumFormModelKbn.group) {
            thisForm.setSelectorTypeText(EnumFormModelKbn.group.name());
        }
        EnumFormModelKbn kbn = thisForm.getSelectorType();
        if (kbn == EnumFormModelKbn.user) {
            thisForm.setSelectParamUser(
                    ((SimpleUserSelectPrefarence) thisForm.getCell().getBody()).getSelect()
                    );
        }
        if (kbn == EnumFormModelKbn.group) {
            thisForm.setSelectParamGroup(
                    ((GroupComboModelPrefarence) thisForm.getCell().getBody())
                        .getSelect()
                    );
        }
        return map.getInputForward();
    }

    private void __doSubmitFileDialog(IFormCellPrefarenceParamModel thisForm,
            HttpServletRequest req, Connection con) throws IOToolsException {
        RequestModel reqMdl = getRequestModel(req);
        FormCell cell = thisForm.getCell();

        GSTemporaryPathModel tempPathMdlSave = GSTemporaryPathModel.getInstance(reqMdl,
                thisForm.getPluginId(), thisForm.getDirectoryId(), String.valueOf(cell.getSid()));
        GSTemporaryPathModel tempPathMdlEdit = new GSTemporaryPathModel(tempPathMdlSave, EDIT);

        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();


        pathUtil.clearTempPath(tempPathMdlSave);

        //一時フォルダであるeditからsaveへ移動する
        File baseDir = new File(tempPathMdlEdit.getTempPath());
        if (IOTools.isDirCheck(tempPathMdlEdit.getTempPath(), false)) {
            for (File eachFile : baseDir.listFiles()) {
                if (!eachFile.isFile()) {
                    continue;
                }
                try {
                    IOTools.moveBinFile(eachFile,
                            new File(Path.of(tempPathMdlSave.getTempPath(),
                                    eachFile.getName()).toString())
                            );
                } catch (IOException e) {
                    log__.warn("ファイルのコピーに失敗", e);
                }
            }
        }

    }
    /**
     *
     * <br>[機  能] ファイル用設定ダイアログ初期化処理
     * <br>[解  説]
     * <br>[備  考]
     * @param cmd
     * @param thisForm
     * @param req
     * @param con
     * @throws IOToolsException
     */
    private void __doInitFileDialog(String cmd, IFormCellPrefarenceParamModel thisForm,
            HttpServletRequest req, Connection con) throws IOToolsException {
        FormCell cell = thisForm.getCell();
        Temp temp = (Temp) cell.getBody();

        RequestModel reqMdl = getRequestModel(req);
        GSTemporaryPathModel tempPathMdlSave = GSTemporaryPathModel.getInstance(reqMdl,
                thisForm.getPluginId(), thisForm.getDirectoryId(), String.valueOf(cell.getSid()));
        GSTemporaryPathModel tempPathMdlEdit = new GSTemporaryPathModel(tempPathMdlSave, EDIT);


        if (Objects.equals(cmd, "dsp")) {
            temp.setTempPath(tempPathMdlSave);

        } else {
            temp.setTempPath(tempPathMdlEdit);

        }
        GSTemporaryPathUtil pathUtil = GSTemporaryPathUtil.getInstance();

        if (cmd.equals("dialog")) {
            //一時フォルダ内の添付ファイルを一度削除
            pathUtil.deleteTempPath(tempPathMdlEdit);
            pathUtil.createTempDir(tempPathMdlEdit);

            File baseDir = new File(tempPathMdlSave.getTempPath());
            if (IOTools.isDirCheck(tempPathMdlSave.getTempPath(), false)) {

                //一時フォルダであるeditへコピーする
                for (File eachFile : baseDir.listFiles()) {
                    if (!eachFile.isFile()) {
                        continue;
                    }
                    try {
                        IOTools.copyBinFile(eachFile,
                                new File(Path.of(tempPathMdlEdit.getTempPath(),
                                        eachFile.getName()).toString())
                                );
                    } catch (IOException e) {
                        log__.warn("ファイルのコピーに失敗", e);
                    }
                }
            }
        }
        CommonBiz cmnBiz = new CommonBiz();
        temp.setSampleList(cmnBiz.getTempFileLabelList(temp.getTempPath().getTempPath()));

    }

}
