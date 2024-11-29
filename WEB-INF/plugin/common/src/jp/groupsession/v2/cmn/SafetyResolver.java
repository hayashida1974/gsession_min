package jp.groupsession.v2.cmn;

import org.apache.commons.beanutils.expression.DefaultResolver;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <br>[機  能] プロパティに"class"または"classLoader"が含まれる場合、存在しないプロパティとして扱うようBeanUtilsへ指定する
 * <br>[解  説]
 * <br>[備  考] Struts1 脆弱性対応
 *
 * @author JTS
 */
public class SafetyResolver extends DefaultResolver {

    /** Logging インスタンス */
    private static Log log__ = LogFactory.getLog(SafetyResolver.class);

    @Override
    public String next(String expression) {
        String next = super.next(expression);
        if (next.equalsIgnoreCase("class") || next.equalsIgnoreCase("classLoader")) {
            log__.debug("\"class\" or \"classLoader\" Load");
            return "";
        }

        return next;
    }
}
