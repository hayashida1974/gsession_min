package jp.groupsession.v2.wml.syspro.wml1010;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import jp.co.sjts.util.StringUtil;
import jp.co.sjts.util.io.IOTools;
import jp.co.sjts.util.io.ObjectFile;
import jp.co.sjts.util.struts.StrutsUtil;
import jp.groupsession.v2.cmn.GSConstCommon;
import jp.groupsession.v2.cmn.GSConstWebmail;
import jp.groupsession.v2.cmn.cmn110.Cmn110FileModel;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.struts.msg.GsMessage;
import jp.groupsession.v2.wml.model.base.LabelDataModel;
import jp.groupsession.v2.wml.wml090.Wml090Form;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;

/**
 * <br>[機  能] WEBメール メールインポート画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml1010Form extends Wml090Form {

    /** アカウントリスト */
    private List<LabelValueBean> acntList__ = null;
    /** 選択 アカウントSID */
    private int wml1010accountSid__ = -1;
    /** 選択 ディレクトリSID */
    private int wml1010saveWdrSid__ = GSConstWebmail.DIR_TYPE_RECEIVE;
    /** ラベルリスト */
    private List<LabelValueBean> lbnList__ = null;
    /** 選択 ラベルSID */
    private int wml1010labelSid__ = -1;

    //非表示項目
    /** 初期表示フラグ */
    private int wml1010initFlg__ = 0;

	public List<LabelValueBean> getAcntList() {
		return acntList__;
	}

	public void setAcntList(List<LabelValueBean> acntList) {
		this.acntList__ = acntList;
	}

	public int getWml1010accountSid() {
		return wml1010accountSid__;
	}

	public void setWml1010accountSid(int wml1010accountSid) {
		this.wml1010accountSid__ = wml1010accountSid;
	}

	public String getWml1010accountString() {
		String ret = null;
		if (this.acntList__ != null && this.wml1010accountSid__ != -1) {
			for(LabelValueBean bean : this.acntList__){
				if (bean.getValue().equals(String.valueOf(wml1010accountSid__))) {
					ret = bean.getLabel();
					break;
				}
			}
		}
		return ret;
	}

	public int getWml1010initFlg() {
		return wml1010initFlg__;
	}

	public void setWml1010initFlg(int wml1010initFlg) {
		this.wml1010initFlg__ = wml1010initFlg;
	}

    public int getWml1010saveWdrSid() {
		return wml1010saveWdrSid__;
	}

	public void setWml1010saveWdrSid(int wml1010saveWdrSid) {
		this.wml1010saveWdrSid__ = wml1010saveWdrSid;
	}

	public List<LabelValueBean> getLbnList() {
		return lbnList__;
	}

	public void setLbnList(List<LabelValueBean> lbnList) {
		this.lbnList__ = lbnList;
	}

	public int getWml1010labelSid() {
		return wml1010labelSid__;
	}

	public void setWml1010labelSid(int wml1010labelSid) {
		this.wml1010labelSid__ = wml1010labelSid;
	}

	public String getWml1010labelString() {
		String ret = null;
		if (this.lbnList__ != null && this.wml1010labelSid__ != -1) {
			for(LabelValueBean bean : this.lbnList__){
				if (bean.getValue().equals(String.valueOf(this.wml1010labelSid__))) {
					ret = bean.getLabel();
					break;
				}
			}
		}
		return ret;
	}

	/**
     * <br>[機  能] アカウント選択チェックを行う
     * <br>[解  説]
     * <br>[備  考]
     * @param req リクエスト
     * @throws Exception  実行例外
     * @return エラー
     */
    public ActionErrors validateCheck(
    		HttpServletRequest req,
    		RequestModel reqMdl,
    		String tempDir) throws Exception {
        ActionErrors errors = new ActionErrors();

        GsMessage gsMsg = new GsMessage(reqMdl);
        String msgKey = "error.select.required.text";

        //アカウント選択チェック
        if (wml1010accountSid__ == -1) {
            String fieldfix = "wml0110accountSid.";
            //[インポートするアカウント]を選択してください。
            ActionMessage msg = new ActionMessage(msgKey, "インポートするアカウント");
            StrutsUtil.addMessage(
                    errors, msg, fieldfix + msgKey);
        }

        //インポートファイルチェック
        //テンポラリディレクトリにあるファイル名称を取得
        List<String> fileList = IOTools.getFileNames(tempDir);
        //取込みファイル
        String eprefix = "inputFile.";
        if (fileList == null || fileList.size() == 0) {
        	//[取込みファイル]を選択してください。
            ActionMessage msg = new ActionMessage(msgKey, gsMsg.getMessage("cmn.capture.file"));
            StrutsUtil.addMessage(errors, msg, eprefix + msgKey);
        } else {
        	boolean extError = false;
            for (int i = 0; i < fileList.size(); i++) {
                //ファイル名を取得
                String fileName = fileList.get(i);
                if (!fileName.endsWith(GSConstCommon.ENDSTR_OBJFILE)) {
                    continue;
                }

                //オブジェクトファイルを取得
                ObjectFile objFile = new ObjectFile(tempDir, fileName);
                Object fObj = objFile.load();
                if (fObj == null) {
                    continue;
                }
                Cmn110FileModel fMdl = (Cmn110FileModel) fObj;
                String baseFileName = fMdl.getFileName();

                //拡張子チェック
                String strExt = StringUtil.getExtension(baseFileName);
                if (strExt == null || !strExt.toUpperCase().equals(".EML")) {
                	extError = true;
                }
            }
            if (extError) {
            	//[Eml形式のファイル]を選択してください。
                ActionMessage msg = new ActionMessage(msgKey, "Eml形式のファイル");
                StrutsUtil.addMessage(errors, msg, eprefix + msgKey);
            }
        }
        return errors;
    }
}
