package jp.groupsession.v2.cmn.formmodel;

import jp.groupsession.v2.cmn.cmn999.Cmn999Form;

/**
 *
 * <br>[機  能] 共通メッセージフォームへのパラメータ設定機能の宣言
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface ISetableHiddenParam {
    /**
    *
    * <br>[機  能] 共通メッセージフォームのパラメータ設定
    * <br>[解  説]
    * <br>[備  考]
    * @param msgForm 共通メッセージフォーム
    * @param paramName フォーム要素パラメータ名
    */
   public void setHiddenParam(
           Cmn999Form msgForm, String paramName);

}
