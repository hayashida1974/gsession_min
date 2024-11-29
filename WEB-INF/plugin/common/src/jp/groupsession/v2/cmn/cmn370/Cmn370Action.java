package jp.groupsession.v2.cmn.cmn370;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

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
public class Cmn370Action extends AbstractGsAction {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(Cmn370Action.class);

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
        Cmn370Form thisForm = (Cmn370Form) form;

        //初期表示
        forward = __doInit(map, thisForm, req, res, con);

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
    public ActionForward __doInit(ActionMapping map, Cmn370Form form,
            HttpServletRequest req, HttpServletResponse res, Connection con) throws SQLException
            {
        con.setAutoCommit(true);

        ActionForward forward = null;
        ParameterObject objParam = null;
        try {
            //フォーム指定の妥当性チェック
            boolean isSelectForm = false;
            try {
                //指定の選択UIのゲッターがなければ不正
                new PropertyDescriptor(
                        form.getTargetSelector(),
                        Class.forName(form.getFormClsName())
                        );
                isSelectForm = true;
            } catch (IntrospectionException e) {
                isSelectForm = false;
            }

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

            log__.debug("END_Cmn370");
        } catch (ClassNotFoundException
                | InstantiationException
                | IllegalArgumentException
                | SecurityException e) {
            throw new RuntimeException(e);
        }
        return forward;
    }

}