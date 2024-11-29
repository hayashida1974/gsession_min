package jp.groupsession.v2.usr.usr031kn;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;

import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.model.base.CmnGroupmModel;
import jp.groupsession.v2.cmn.model.base.CmnUsrmInfModel;
import jp.groupsession.v2.usr.usr031.Usr031Form;

/**
 * <br>[機  能] メイン 管理者設定 ユーザマネージャー（追加）確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Usr031knForm extends Usr031Form {

    /** 選択グループ(複数) */
    private List<CmnGroupmModel> usr031knSltgps__ = null;
    /** デフォルトグループ  */
    private CmnGroupmModel usr031knDefgp__ = null;
    /** 都道府県名  */
    private String usr031kntdfkName__ = null;
    /** 役職名  */
    private String usr031knposName__ = null;
    /** 性別  */
    private String usr031seibetuName__ = null;
    /** 備考(html表示形式) */
    private String usr031bikouHtml__ = null;
    /** 削除ユーザ一覧 */
    private List<CmnUsrmInfModel> usr031delUsrList__ = null;
    /** 削除ユ-ザ複数区分 複数:1 1ユーザ:0 */
    private int usr031delPluralKbn__ = 0;

    /** メール１*/
    private String usr031knMail1__ = null;
    /** メール１*/
    private String usr031knMail2__ = null;
    /** メール１*/
    private String usr031knMail3__ = null;
    
    /** 自ユーザ削除フラグ */
    private boolean usr031knDelSelfFlg__ = false;

    /**
     * @return usr031bikouHtml__ を戻します。
     */
    public String getUsr031bikouHtml() {
        return usr031bikouHtml__;
    }
    /**
     * @param usr031bikouHtml 設定する usr031bikouHtml__。
     */
    public void setUsr031bikouHtml(String usr031bikouHtml) {
        usr031bikouHtml__ = usr031bikouHtml;
    }
    /**
     * @return usr031kntdfkName を戻します。
     */
    public String getUsr031kntdfkName() {
        return usr031kntdfkName__;
    }
    /**
     * @param usr031kntdfkName 設定する usr031kntdfkName。
     */
    public void setUsr031kntdfkName(String usr031kntdfkName) {
        usr031kntdfkName__ = usr031kntdfkName;
    }
    /**
     * @return usr030knSltgps を戻します。
     */
    public List<CmnGroupmModel> getUsr031knSltgps() {
        return usr031knSltgps__;
    }
    /**
     * @param usr030knSltgps 設定する usr030knSltgps。
     */
    public void setUsr031knSltgps(List<CmnGroupmModel> usr030knSltgps) {
        usr031knSltgps__ = usr030knSltgps;
    }
    /**
     * @return usr031knDefgp を戻します。
     */
    public CmnGroupmModel getUsr031knDefgp() {
        return usr031knDefgp__;
    }
    /**
     * @param usr031knDefgp 設定する usr031knDefgp。
     */
    public void setUsr031knDefgp(CmnGroupmModel usr031knDefgp) {
        usr031knDefgp__ = usr031knDefgp;
    }
    /**
     * <p>usr031knposName を取得します。
     * @return usr031knposName
     */
    public String getUsr031knposName() {
        return usr031knposName__;
    }
    /**
     * <p>usr031knposName をセットします。
     * @param usr031knposName usr031knposName
     */
    public void setUsr031knposName(String usr031knposName) {
        usr031knposName__ = usr031knposName;
    }
    /**
     * <p>usr031delUsrList を取得します。
     * @return usr031delUsrList
     */
    public List<CmnUsrmInfModel> getUsr031delUsrList() {
        return usr031delUsrList__;
    }
    /**
     * <p>usr031delUsrList をセットします。
     * @param usr031delUsrList usr031delUsrList
     */
    public void setUsr031delUsrList(List<CmnUsrmInfModel> usr031delUsrList) {
        usr031delUsrList__ = usr031delUsrList;
    }
    /**
     * <p>usr031delPluralKbn を取得します。
     * @return usr031delPluralKbn
     */
    public int getUsr031delPluralKbn() {
        return usr031delPluralKbn__;
    }
    /**
     * <p>usr031delPluralKbn をセットします。
     * @param usr031delPluralKbn usr031delPluralKbn
     */
    public void setUsr031delPluralKbn(int usr031delPluralKbn) {
        usr031delPluralKbn__ = usr031delPluralKbn;
    }
    /**
     * @return usr031knMail1
     */
    public String getUsr031knMail1() {
        return usr031knMail1__;
    }
    /**
     * @param usr031knMail1 セットする usr031knMail1
     */
    public void setUsr031knMail1(String usr031knMail1) {
        usr031knMail1__ = usr031knMail1;
    }
    /**
     * @return usr031knMail2
     */
    public String getUsr031knMail2() {
        return usr031knMail2__;
    }
    /**
     * @param usr031knMail2 セットする usr031knMail2
     */
    public void setUsr031knMail2(String usr031knMail2) {
        usr031knMail2__ = usr031knMail2;
    }
    /**
     * @return usr031knMail3
     */
    public String getUsr031knMail3() {
        return usr031knMail3__;
    }
    /**
     * @param usr031knMail3 セットする usr031knMail3
     */
    public void setUsr031knMail3(String usr031knMail3) {
        usr031knMail3__ = usr031knMail3;
    }
    /**
     * <p>usr031knDelSelfFlg を取得します。
     * @return usr031knDelSelfFlg
     * @see jp.groupsession.v2.usr.usr031kn.Usr031knParamModel#usr031knDelSelfFlg__
     */
    public boolean isUsr031knDelSelfFlg() {
        return usr031knDelSelfFlg__;
    }
    /**
     * <p>usr031knDelSelfFlg をセットします。
     * @param usr031knDelSelfFlg usr031knDelSelfFlg
     * @see jp.groupsession.v2.usr.usr031kn.Usr031knParamModel#usr031knDelSelfFlg__
     */
    public void setUsr031knDelSelfFlg(boolean usr031knDelSelfFlg) {
        usr031knDelSelfFlg__ = usr031knDelSelfFlg;
    }
    /**
     * <p>usr031seibetuName を取得します。
     * @return usr031seibetuName
     */
    public String getUsr031seibetuName() {
        return usr031seibetuName__;
    }
    /**
     * <p>usr031seibetuName をセットします。
     * @param usr031seibetuName usr031seibetuName
     */
    public void setUsr031seibetuName(String usr031seibetuName) {
        usr031seibetuName__ = usr031seibetuName;
    }


    /**
     * <br>[機  能] 確認画面のsidの存在チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @param con コネクション
     * @param form アクションフォーム
     * @param processMode プロセスモード
     * @param reqMdl リクエストモデル
     * @return エラー
     * @throws SQLException SQL実行時例外
     */
    public ActionErrors validateKnSidCheck(
            HttpServletRequest req,
            Connection con,
            Usr031knForm form,
            String processMode,
            RequestModel reqMdl) throws SQLException {
        return form.validateTargetSidCheck(req, con, processMode, reqMdl);
    }

}