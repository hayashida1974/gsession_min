package jp.groupsession.v2.cmn.biz.config.bodylimit;
/**
 * 最大文字数設定モデル インタフェース
 */
public interface IBodyLimitConfig {
    /**
     * @return 最大文字数設定 設定ファイルの値が不正の場合はデフォルト値が返る
    */
    int getMaxLength(String appRootPath);
}
