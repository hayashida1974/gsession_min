package jp.groupsession.v2.rng.rng020;

import java.util.List;

import jp.groupsession.v2.usr.model.UsrLabelValueBean;

/**
 *
 * <br>[機  能] 経路確認表示用 審議者一覧情報
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class Rng020KeiroKakuninDsp {
    /** 表示名 (グループ名)*/
    private String name__;
    /** 表示名 (グループ名)*/
    private List<UsrLabelValueBean> singi__;
    /**
     * <p>name を取得します。
     * @return name
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroKakuninDsp#name__
     */
    public String getName() {
        return name__;
    }
    /**
     * <p>name をセットします。
     * @param name name
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroKakuninDsp#name__
     */
    public void setName(String name) {
        name__ = name;
    }
    /**
     * <p>singi を取得します。
     * @return singi
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroKakuninDsp#singi__
     */
    public List<UsrLabelValueBean> getSingi() {
        return singi__;
    }
    /**
     * <p>singi をセットします。
     * @param singi singi
     * @see jp.groupsession.v2.rng.rng020.Rng020KeiroKakuninDsp#singi__
     */
    public void setSingi(List<UsrLabelValueBean> singi) {
        singi__ = singi;
    }

}
