package jp.groupsession.v2.wml.syspro.wml1010kn;

import java.util.List;

import jp.groupsession.v2.wml.syspro.wml1010.Wml1010Form;

/**
 * <br>[機  能] WEBメール メールインポート確認画面のフォーム
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Wml1010knForm extends Wml1010Form {

    /** アカウント文字列 */
    private String wml1010knAccountString__ = null;
    /** ラベル文字列 */
    private String wml1010knLabelString__ = null;
    /** メール一覧 */
    private List<Wml1010knMailModel> wml1010knMailList__ = null;

	public String getWml1010knAccountString() {
		return wml1010knAccountString__;
	}

	public void setWml1010knAccountString(String wml1010knAccountString) {
		this.wml1010knAccountString__ = wml1010knAccountString;
	}

	public String getWml1010knLabelString() {
		return wml1010knLabelString__;
	}

	public void setWml1010knLabelString(String wml1010knLabelString) {
		this.wml1010knLabelString__ = wml1010knLabelString;
	}

	public List<Wml1010knMailModel> getWml1010knMailList() {
		return wml1010knMailList__;
	}

	public void setWml1010knMailList(List<Wml1010knMailModel> wml1010knMailList) {
		this.wml1010knMailList__ = wml1010knMailList;
	}

    public Wml1010knMailModel getWml1010knMailModel(int idx) {
    	if (wml1010knMailList__ == null) {
    		return null;
    	}
        while (wml1010knMailList__.size() <= idx) {
		      this.wml1010knMailList__.add(new Wml1010knMailModel());
        }
        return (Wml1010knMailModel) wml1010knMailList__.get(idx);
    }
}