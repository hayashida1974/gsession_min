package jp.groupsession.v2.cmn.biz;

/**
 * <br>[機  能] 呼出し時にスレッド名を設定しcloseで呼出し前のスレッド名に戻す
 * <br>[解  説]
 * <br>[備  考] try-with-resourcesブロックで使用すれば自動close
 *
 * @author JTS
 */
public class CloseableThreadName implements AutoCloseable {
    /** 呼出し元スレッド名*/
    String parent__;
    /**
     *
     * コンストラクタ
     */
    private CloseableThreadName() {
    }
    /**
     *
     * <br>[機  能] ブロック内のスレッド名を設定する
     * <br>[解  説]
     * <br>[備  考]
     * @param name ブロック用スレッド名
     * @return AutoCloseableインスタンス
     */
    public static CloseableThreadName setName(String name) {
        CloseableThreadName ret = new CloseableThreadName();
        ret.parent__ = Thread.currentThread().getName();
        Thread.currentThread().setName(name);
        return ret;

    }
    @Override
    public void close() {
        if (parent__ != null) {
            //呼出し元スレッド名を設定
            Thread.currentThread().setName(parent__);

        } else {
            //スレッド名に"END-"を設定する
            Thread.currentThread().setName(
                    "END-" + Thread.currentThread().getName());

        }
    }

}
