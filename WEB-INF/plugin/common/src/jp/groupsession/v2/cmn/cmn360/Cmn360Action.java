package jp.groupsession.v2.cmn.cmn360;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.select.ISelector;
import jp.groupsession.v2.struts.AbstractGsAction;

/**
 * <br>[機  能] 全グループから選択ポップアップのアクションクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn360Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn360Action.class);

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

    /**
     * <br>[機  能] アクション実行
     * <br>[解  説]
     * <br>[備  考]
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @return ActionForward
     * @throws Exception 実行例外
     */
    public ActionForward executeAction(ActionMapping map,
                                         ActionForm form,
                                         HttpServletRequest req,
                                         HttpServletResponse res,
                                         Connection con)
        throws Exception {

        ActionForward forward = null;
        Cmn360Form thisForm = (Cmn360Form) form;

        if (thisForm.getSelectorDispCmd().equals("filter_post")) {
            //役職取得
            forward = __doFilterPosition(map, thisForm, req, res, con);
        } else if (thisForm.getSelectorDispCmd().equals("filter_label")) {
            //ラベル取得
            forward = __doFilterLabel(map, thisForm, req, res, con);
        } else {
            //初期表示
            forward = __doInit(map, thisForm, req, res, con);
        }

        return forward;
    }

    /**
     * <p>グループリスト取得
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     * @throws SQLException
     */
    public ActionForward __doInit(ActionMapping map, Cmn360Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {
        con.setAutoCommit(true);

        ActionForward forward = null;
        ParameterObject objParam = null;
        try {
            //フォーム指定の妥当性チェック
            boolean isSelectForm = false;
            String firstParam = form.getTargetSelector();
            if (firstParam.indexOf(".") >= 0) {
                firstParam = firstParam.substring(0, firstParam.indexOf("."));
            }
            if (firstParam.indexOf("(") >= 0) {
                firstParam = firstParam.substring(0, firstParam.indexOf("("));
            }
            if (firstParam.indexOf("[") >= 0) {
                firstParam = firstParam.substring(0, firstParam.indexOf("("));
            }


            //指定の選択UIのゲッターがなければ不正
            String methodName = "get"
                    + firstParam.substring(0, 1).toUpperCase()
                    + firstParam.substring(1);
            isSelectForm =
                    (
                        Stream.of(Class.forName(form.getFormClsName())
                        .getMethods())
                        .filter(m -> Objects.equals(m.getName(), methodName))
                        .findAny()
                        .isEmpty() == false);

            if (Optional.ofNullable(form.getFormClsName())
                    .orElse("")
                    .startsWith("jp.groupsession")
                && isSelectForm) {
                //ユーザ選択使用画面Formを復元
                try {
                    Object baseForm =
                            Class.forName(form.getFormClsName())
                            .getDeclaredConstructor()
                            .newInstance();
                    BeanUtils.populate(baseForm, req.getParameterMap());
                    objParam = new ParameterObject(
                            baseForm
                            );

                    //呼び出し元パラメータ情報から対象の選択情報を取得
                        form.setSelector(
                                (ISelector) PropertyUtils.getProperty(
                                        baseForm,
                                        form.getTargetSelector()
                                        ));
                        //詳細検索用 フィルターが設定されていた場合、セレクターに追加
                        if (form.getMultiselectorFilter() != null) {
                            ISelector selector = form.getSelector();
                            selector.setMultiselectorFilter(form.getMultiselectorFilter());
                            form.setSelector(selector);
                        }
                //呼び出し元パラメータで対象UIの初期化がされない場合
                } catch (IllegalAccessException
                        | IllegalArgumentException
                        | InvocationTargetException
                        | NoSuchMethodException
                        | SecurityException e) {
                    objParam = null;
                }
                form.getSelector().dspInit(objParam,
                        getRequestModel(req),
                        con);

            }


            con.setAutoCommit(false);

            forward = map.getInputForward();

            log__.debug("END_Cmn360");
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalArgumentException
                | SecurityException e) {
            throw new RuntimeException(e);
        }
        return forward;
    }
    /**
     * <p>フィルター情報設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     * @throws SQLException
     */
    public ActionForward __doFilterPosition(ActionMapping map, Cmn360Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {

        Cmn360Biz biz = new Cmn360Biz(con);

        form.setFilterPositionList(biz.getFilterPositionList(
                form.getMultiselectorFilter()));

        return map.getInputForward();
    }

    /**
     * <p>フィルター情報設定
     * @param map アクションマッピング
     * @param form アクションフォーム
     * @param req リクエスト
     * @param res レスポンス
     * @param con コネクション
     * @throws Exception 実行例外
     * @return アクションフォーム
     * @throws SQLException
     */
    public ActionForward __doFilterLabel(ActionMapping map, Cmn360Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException {

        Cmn360Biz biz = new Cmn360Biz(con);

        form.setFilterLabelList(biz.getFilterLabelList(
                form.getMultiselectorFilter()));

        return map.getInputForward();
    }

}