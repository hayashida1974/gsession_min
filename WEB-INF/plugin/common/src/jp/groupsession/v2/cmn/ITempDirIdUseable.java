package jp.groupsession.v2.cmn;
/**
 *
 * <br>[機  能] インタフェース テンポラリディレクトリ利用画面 フォーム、パラメータモデル用
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public interface ITempDirIdUseable {
    /**
     * <p>tempDirId をセットします。
     * @param tempDirId tempDirId
     */
    public abstract void setTempDirId(String tempDirId);
    /**
     * <p>tempDirId を取得します。
     * @return tempDirId
     */
    public abstract String getTempDirId();

}
