package jp.groupsession.v2.cmn.ui.parts.select;

public interface IParamForm<T extends ISelector> {

    /**
        *
        * <br>[機  能] ビルドパラメータモデルを生成する
        * <br>[解  説]
        * <br>[備  考]
        * @return 生成したUIモデル
        */
    T build();

}