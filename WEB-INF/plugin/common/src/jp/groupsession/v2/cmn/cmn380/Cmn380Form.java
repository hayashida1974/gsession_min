package jp.groupsession.v2.cmn.cmn380;

import java.util.ArrayList;

import org.apache.struts.upload.FormFile;
import org.apache.struts.util.LabelValueBean;

import jp.groupsession.v2.struts.AbstractGsForm;

/**
 * <br>[機  能] ファイルプレビュー画面のフォームクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Cmn380Form extends AbstractGsForm {
    /** プレビュー対象ファイルのダウンロードURL */
    private String cmn380PreviewURL__ = null;
    /** プレビュー対象ファイルの拡張子 */
    private String cmn380PreviewFileExtension__ = null;
    /** プレビュー対象ファイルのダウンロードURL(PDF表示用) */
    private String cmn380PreviewPdfURL__ = null;
    /** プレビュー対象ファイルの内容(txt, js, css, html用) */
    private String cmn380FileContent__ = null;
    /** プレビュー対象ファイル(txt, js, css, html用) */
    private FormFile cmn380File__ = null;
    /** 文字コードリスト */
    private ArrayList<LabelValueBean> cmn380EncordingList__;
    /** 選択文字コード */
    private int cmn380SelectEncording__ = 0;
    /** ファイル名 */
    private String cmn380FileName__ = null;
    /**
     * <p>cmn380PreviewURL を取得します。
     * @return cmn380PreviewURL
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewURL__
     */
    public String getCmn380PreviewURL() {
        return cmn380PreviewURL__;
    }
    /**
     * <p>cmn380PreviewURL をセットします。
     * @param cmn380PreviewURL cmn380PreviewURL
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewURL__
     */
    public void setCmn380PreviewURL(String cmn380PreviewURL) {
        cmn380PreviewURL__ = cmn380PreviewURL;
    }
    /**
     * <p>cmn380PreviewFileExtension を取得します。
     * @return cmn380PreviewFileExtension
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewFileExtension__
     */
    public String getCmn380PreviewFileExtension() {
        return cmn380PreviewFileExtension__;
    }
    /**
     * <p>cmn380PreviewFileExtension をセットします。
     * @param cmn380PreviewFileExtension cmn380PreviewFileExtension
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewFileExtension__
     */
    public void setCmn380PreviewFileExtension(String cmn380PreviewFileExtension) {
        cmn380PreviewFileExtension__ = cmn380PreviewFileExtension;
    }
    /**
     * <p>cmn380PreviewPdfURL を取得します。
     * @return cmn380PreviewPdfURL
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewPdfURL__
     */
    public String getCmn380PreviewPdfURL() {
        return cmn380PreviewPdfURL__;
    }
    /**
     * <p>cmn380PreviewPdfURL をセットします。
     * @param cmn380PreviewPdfURL cmn380PreviewPdfURL
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380PreviewPdfURL__
     */
    public void setCmn380PreviewPdfURL(String cmn380PreviewPdfURL) {
        cmn380PreviewPdfURL__ = cmn380PreviewPdfURL;
    }
    /**
     * <p>cmn380FileContent を取得します。
     * @return cmn380FileContent
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380FileContent__
     */
    public String getCmn380FileContent() {
        return cmn380FileContent__;
    }
    /**
     * <p>cmn380FileContent をセットします。
     * @param cmn380FileContent cmn380FileContent
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380FileContent__
     */
    public void setCmn380FileContent(String cmn380FileContent) {
        cmn380FileContent__ = cmn380FileContent;
    }
    /**
     * <p>cmn380File を取得します。
     * @return cmn380File
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380File__
     */
    public FormFile getCmn380File() {
        return cmn380File__;
    }
    /**
     * <p>cmn380File をセットします。
     * @param cmn380File cmn380File
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380File__
     */
    public void setCmn380File(FormFile cmn380File) {
        cmn380File__ = cmn380File;
    }
    /**
     * <p>cmn380EncordingList を取得します。
     * @return cmn380EncordingList
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380EncordingList__
     */
    public ArrayList<LabelValueBean> getCmn380EncordingList() {
        return cmn380EncordingList__;
    }
    /**
     * <p>cmn380EncordingList をセットします。
     * @param cmn380EncordingList cmn380EncordingList
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380EncordingList__
     */
    public void setCmn380EncordingList(
            ArrayList<LabelValueBean> cmn380EncordingList) {
        cmn380EncordingList__ = cmn380EncordingList;
    }
    /**
     * <p>cmn380SelectEncording を取得します。
     * @return cmn380SelectEncording
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380SelectEncording__
     */
    public int getCmn380SelectEncording() {
        return cmn380SelectEncording__;
    }
    /**
     * <p>cmn380SelectEncording をセットします。
     * @param cmn380SelectEncording cmn380SelectEncording
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380SelectEncording__
     */
    public void setCmn380SelectEncording(int cmn380SelectEncording) {
        cmn380SelectEncording__ = cmn380SelectEncording;
    }
    /**
     * <p>cmn380FileName を取得します。
     * @return cmn380FileName
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380FileName__
     */
    public String getCmn380FileName() {
        return cmn380FileName__;
    }
    /**
     * <p>cmn380FileName をセットします。
     * @param cmn380FileName cmn380FileName
     * @see jp.groupsession.v2.cmn.cmn380.Cmn380Form#cmn380FileName__
     */
    public void setCmn380FileName(String cmn380FileName) {
        cmn380FileName__ = cmn380FileName;
    }
}