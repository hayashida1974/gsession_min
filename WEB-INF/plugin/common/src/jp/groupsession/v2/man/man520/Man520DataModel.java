package jp.groupsession.v2.man.man520;

import java.util.List;
import java.util.Map;

import jp.groupsession.v2.cmn.biz.DataUsedSizeBiz;

/**
 * <br>[機  能] データ使用量一覧画面の画面表示用情報を保持するModel
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Man520DataModel {
    /** プラグインID */
    private String pluginId__ = null;
    /** プラグイン名称 */
    private String pluginName__ = null;
    /** データ使用量明細 名称一覧 */
    private List<String> nameList__ = null;
    /** データ使用量明細 名称 - データ使用量Mapping */
    private Map<String, Long> sizeMap__ = null;

    /** 合計 */
    private long totalSize__ = 0;
    /** プラグインアイコン オリジナルテーマ */
    private String iconOriginal__ = null;
    /** プラグインアイコン クラシックテーマ */
    private String iconClassic__ = null;

    /** プラグイン独自の集計マップHTMLフラグ */
    private boolean senyoMapFlg__ = false;
    /** プラグイン独自の集計マップHTML */
    private String senyoMapHtml__ = null;

    /**
     * <p>pluginId を取得します。
     * @return pluginId
     * @see jp.groupsession.v2.man.man520.Man520DataModel#pluginId__
     */
    public String getPluginId() {
        return pluginId__;
    }
    /**
     * <p>pluginId をセットします。
     * @param pluginId pluginId
     * @see jp.groupsession.v2.man.man520.Man520DataModel#pluginId__
     */
    public void setPluginId(String pluginId) {
        pluginId__ = pluginId;
    }
    /**
     * <p>pluginName を取得します。
     * @return pluginName
     * @see jp.groupsession.v2.man.man520.Man520DataModel#pluginName__
     */
    public String getPluginName() {
        return pluginName__;
    }
    /**
     * <p>pluginName をセットします。
     * @param pluginName pluginName
     * @see jp.groupsession.v2.man.man520.Man520DataModel#pluginName__
     */
    public void setPluginName(String pluginName) {
        pluginName__ = pluginName;
    }
    /**
     * <p>nameList を取得します。
     * @return nameList
     * @see jp.groupsession.v2.man.man520.Man520DataModel#nameList__
     */
    public List<String> getNameList() {
        return nameList__;
    }
    /**
     * <p>totalSize を取得します。
     * @return totalSize
     * @see jp.groupsession.v2.man.man520.Man520DataModel#totalSize__
     */
    public long getTotalSize() {
        return totalSize__;
    }
    /**
     * <p>totalSize をセットします。
     * @param totalSize totalSize
     * @see jp.groupsession.v2.man.man520.Man520DataModel#totalSize__
     */
    public void setTotalSize(long totalSize) {
        totalSize__ = totalSize;
    }
    /**
     * <p>nameList をセットします。
     * @param nameList nameList
     * @see jp.groupsession.v2.man.man520.Man520DataModel#nameList__
     */
    public void setNameList(List<String> nameList) {
        nameList__ = nameList;
    }
    /**
     * <p>sizeMap を取得します。
     * @return sizeMap
     * @see jp.groupsession.v2.man.man520.Man520DataModel#sizeMap__
     */
    public Map<String, Long> getSizeMap() {
        return sizeMap__;
    }
    /**
     * <p>sizeMap をセットします。
     * @param sizeMap sizeMap
     * @see jp.groupsession.v2.man.man520.Man520DataModel#sizeMap__
     */
    public void setSizeMap(Map<String, Long> sizeMap) {
        sizeMap__ = sizeMap;
    }
    /**
     * <p>iconOriginal を取得します。
     * @return iconOriginal
     * @see jp.groupsession.v2.man.man520.Man520DataModel#iconOriginal__
     */
    public String getIconOriginal() {
        return iconOriginal__;
    }
    /**
     * <p>iconOriginal をセットします。
     * @param iconOriginal iconOriginal
     * @see jp.groupsession.v2.man.man520.Man520DataModel#iconOriginal__
     */
    public void setIconOriginal(String iconOriginal) {
        iconOriginal__ = iconOriginal;
    }
    /**
     * <p>iconClassic を取得します。
     * @return iconClassic
     * @see jp.groupsession.v2.man.man520.Man520DataModel#iconClassic__
     */
    public String getIconClassic() {
        return iconClassic__;
    }
    /**
     * <p>iconClassic をセットします。
     * @param iconClassic iconClassic
     * @see jp.groupsession.v2.man.man520.Man520DataModel#iconClassic__
     */
    public void setIconClassic(String iconClassic) {
        iconClassic__ = iconClassic;
    }

    /**
     * <p>senyoMapFlg を取得します。
     * @return senyoMapFlg
     */
    public boolean isSenyoMapFlg() {
        return senyoMapFlg__;
    }
    /**
     * <p>senyoMapFlg をセットします。
     * @param senyoMapFlg senyoMapFlg
     */
    public void setSenyoMapFlg(boolean senyoMapFlg) {
        senyoMapFlg__ = senyoMapFlg;
    }
    /**
     * <p>senyoMapHtml を取得します。
     * @return senyoMapHtml
     */
    public String getSenyoMapHtml() {
        return senyoMapHtml__;
    }
    /**
     * <p>senyoMapHtml をセットします。
     * @param senyoMapHtml senyoMapHtml
     */
    public void setSenyoMapHtml(String senyoMapHtml) {
        senyoMapHtml__ = senyoMapHtml;
    }

    /**
     * <br>[機  能] 指定した明細のデータ使用量を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param name 明細の名称
     * @return データ使用量
     */
    public String getDetailSize(String name) {
        if (name == null || sizeMap__ == null || !sizeMap__.containsKey(name)) {
            return "0";
        }

        return DataUsedSizeBiz.changeSizeString(sizeMap__.get(name));
    }

    /**
     * <br>[機  能] 指定した明細のデータ使用量の単位を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @param name 明細の名称
     * @return データ使用量の単位
     */
    public String getDetailSizeUnit(String name) {
        if (name == null || sizeMap__ == null || !sizeMap__.containsKey(name)) {
            return "";
        }

        return DataUsedSizeBiz.sizeUnit(sizeMap__.get(name));
    }

    /**
     * <br>[機  能] データ使用量の合計を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return データ使用量
     */
    public String getStrTotalSize() {
        return DataUsedSizeBiz.changeSizeString(totalSize__);
    }

    /**
     * <br>[機  能] データ使用量合計の単位を取得する
     * <br>[解  説]
     * <br>[備  考]
     * @return データ使用量合計の単位
     * go
     */
    public String getTotalSizeUnit() {
        return DataUsedSizeBiz.sizeUnit(totalSize__);
    }


}
