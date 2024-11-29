package jp.groupsession.v2.rng.csv;

import java.util.ArrayList;

/**
 * <p>RNG_SINGI Data Bindding JavaBean
 *
 * @author JTS DaoGenerator version 0.1
 */
public class RngCsvPositionMemberModel extends RngCsvUserModel {

    /** ユーザ情報一覧 */
    private ArrayList<RngCsvUserModel> usrList__ = new ArrayList<RngCsvUserModel>();

    /**
     * <p>Default Constructor
     */
    public RngCsvPositionMemberModel() {
    }

    /**
     * <p>usrList を取得します。
     * @return usrList
     */
    public ArrayList<RngCsvUserModel> getUsrList() {
        return usrList__;
    }

    /**
     * <p>usrList へユーザ情報を追加します。
     * @param usrMdl ユーザ情報
     */
    public void addUsrList(RngCsvUserModel usrMdl) {
        usrList__.add(usrMdl);
    }
}
