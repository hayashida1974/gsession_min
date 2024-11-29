package jp.groupsession.v2.rng.ui.parts.dairinin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import jp.co.sjts.util.NullDefault;
import jp.groupsession.v2.cmn.model.RequestModel;
import jp.groupsession.v2.cmn.ui.parameter.ParameterObject;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ExclusionConf;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.ICustomUserGroupSelector;
import jp.groupsession.v2.cmn.ui.parts.usergroupselect.UserGroupSelector;

public class DairininSelector extends UserGroupSelector implements ICustomUserGroupSelector {
    /** オプションパラメータキー 登録対象SID*/
    public static final String OPTIONPARAMKEY_TARGETUSRSID = "targetUserSid";
    public static final String OPTIONPARAM_SESSIONSID = "sessionSid";

    protected DairininSelector(ParamForm<? extends UserGroupSelector> param) {
        super(param);
    }
    /**
    *
    * <br>[機  能] ビルダークラスの取得
    * <br>[解  説]
    * <br>[備  考]
    * @return ビルダー
    */
   public static ParamForm<DairininSelector> builder() {
       ParamForm<DairininSelector> ret = new ParamForm<>(DairininSelector.class);
       return ret;
   }
   @Override
   public ExclusionConf answerGroupExclusion(RequestModel reqMdl,
           Connection con, ParameterObject paramModel) throws SQLException {
       return new ExclusionConf(false, List.of());
   }

   @Override
   public ExclusionConf answerUserExclusion(RequestModel reqMdl,
           Connection con, ParameterObject paramModel) throws SQLException {
         //登録対象ユーザを除外
         String targetParamName = getOptionParameter(OPTIONPARAMKEY_TARGETUSRSID);
         String targetSid;
         //個人設定用
         if (Objects.equals(OPTIONPARAM_SESSIONSID, targetParamName)) {
             targetSid = String.valueOf(reqMdl.getSmodel().getUsrsid());
         //管理者設定用
         } else {
             targetSid = paramModel.get(
                     NullDefault.getString(
                          targetParamName,
                          ""), String.class);

         }


         ExclusionConf ret = new ExclusionConf(false);
         ret.add(targetSid);
         return ret;
   }

}
