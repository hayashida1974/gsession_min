package jp.groupsession.v2.cmn.cmn170;

import java.io.Serializable;

import jp.co.sjts.util.NullDefault;

/**
 * <br>[機  能] テーマ情報(画面表示用)を格納するModelクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn170Model implements Serializable {

    /** CTM_SID mapping */
    private int ctmSid__;
    /** CTM_PATH_IMG mapping */
    private String ctmPathImg__;
    /** CTM_NAME mapping */
    private String ctmName__;
    /** 登録人数 */
    private int ctmPerCount__;


    /**
     * <p>Default Constructor
     */
    public Cmn170Model() {
    }

    /**
     * <p>get CTM_SID value
     * @return CTM_SID value
     */
    public int getCtmSid() {
        return ctmSid__;
    }

    /**
     * <p>set CTM_SID value
     * @param ctmSid CTM_SID value
     */
    public void setCtmSid(int ctmSid) {
        ctmSid__ = ctmSid;
    }

    /**
     * <p>get CTM_PATH_IMG value
     * @return CTM_PATH_IMG value
     */
    public String getCtmPathImg() {
        return ctmPathImg__;
    }

    /**
     * <p>set CTM_PATH_IMG value
     * @param ctmPathImg CTM_PATH_IMG value
     */
    public void setCtmPathImg(String ctmPathImg) {
        ctmPathImg__ = ctmPathImg;
    }

    /**
     * <p>get CTM_NAME value
     * @return CTM_NAME value
     */
    public String getCtmName() {
        return ctmName__;
    }

    /**
     * <p>set CTM_NAME value
     * @param ctmName CTM_NAME value
     */
    public void setCtmName(String ctmName) {
        ctmName__ = ctmName;
    }

    /**
     * <p>get ctmPerCount value
     * @return ctmPerCount value
     */
    public int getCtmPerCount() {
        return ctmPerCount__;
    }

    /**
     * <p>set ctmPerCount value
     * @param ctmPerCount ctmPerCount value
     */
    public void setCtmPerCount(int ctmPerCount) {
        ctmPerCount__ = ctmPerCount;
    }


    /**
     * <p>to Csv String
     * @return Csv String
     */
    public String toCsvString() {
        StringBuilder buf = new StringBuilder();
        buf.append(ctmSid__);
        buf.append(",");
        buf.append(NullDefault.getString(ctmPathImg__, ""));
        buf.append(",");
        buf.append(NullDefault.getString(ctmName__, ""));
        return buf.toString();
    }

}
