package jp.groupsession.v2.sml.biz;

import java.util.ArrayList;
import java.util.List;

import jp.co.sjts.util.StringUtil;
import jp.groupsession.v2.sml.GSConstSmail;
import jp.groupsession.v2.sml.model.AtesakiModel;
import jp.groupsession.v2.sml.model.SmailModel;
import jp.groupsession.v2.usr.GSConstUser;

/**
 * <br>[機  能] 送信先の名前を設定するビジネスロジッククラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class SmlAtesakiNameBiz {

    /**
     * <br>[機  能] 送信済み/草稿 ショートメール情報の「宛先」を設定する
     * <br>[解  説] 通常アカウント(ユーザ情報があるアカウント)の場合、
     * <br>　　　　 「宛先」アカウント名称に「ユーザ名」を設定
     * <br>[備  考]
     * @param smailList ショートメール情報一覧
     * @return ショートメール情報一覧(宛先設定後)
     */
    public List<SmailModel> setSendAtesakiList(List<SmailModel> smailList) {
        AtesakiModel atesakiMdl = null;
        for (int smlIdx = 0; smlIdx < smailList.size(); smlIdx++) {
            ArrayList<AtesakiModel> atesakiList = smailList.get(smlIdx).getAtesakiList();
            if (atesakiList == null || atesakiList.isEmpty()) {
                continue;
            }

            for (int atesakiIdx = 0; atesakiIdx < atesakiList.size(); atesakiIdx++) {
                atesakiMdl = atesakiList.get(atesakiIdx);

                //通常アカウント(ユーザ情報があるアカウント)の場合、
                //アカウント名称に「ユーザ名」を設定
                if (!StringUtil.isNullZeroStringSpace(atesakiMdl.getUsiSei())
                && !StringUtil.isNullZeroStringSpace(atesakiMdl.getUsiMei())) {

                    atesakiList.get(atesakiIdx).setAccountName(
                            atesakiMdl.getUsiSei() + " " + atesakiMdl.getUsiMei());

                    if (atesakiMdl.getUsrJkbn() == GSConstUser.USER_JTKBN_DELETE) {
                        atesakiList.get(atesakiIdx).setAccountJkbn(
                                GSConstSmail.SAC_JKBN_DELETE);
                    } else {
                        atesakiList.get(atesakiIdx).setAccountJkbn(
                                GSConstSmail.SAC_JKBN_NORMAL);
                    }
                }
            }
            smailList.get(smlIdx).setAtesakiList(atesakiList);
        }

        return smailList;
    }
}
