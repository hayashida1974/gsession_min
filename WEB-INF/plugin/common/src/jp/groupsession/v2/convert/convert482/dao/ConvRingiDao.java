package jp.groupsession.v2.convert.convert482.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jp.co.sjts.util.date.UDate;
import jp.co.sjts.util.jdbc.JDBCUtil;
import jp.co.sjts.util.jdbc.SqlBuffer;
import jp.co.sjts.util.json.JSONArray;
import jp.co.sjts.util.json.JSONObject;


/**
 *
 * <br>[機  能] 稟議のコンバートを行う際に使用
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class ConvRingiDao {

    /**
     * テンプレートを区別するためのキー
     */
    static class TplKey {
        /** テンプレートSID */
        private int sid__;
        /** テンプレートバージョン */
        private int ver__;

        /* (非 Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + sid__;
            result = prime * result + ver__;
            return result;
        }

        /* (非 Javadoc)
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (!(obj instanceof TplKey)) {
                return false;
            }
            TplKey other = (TplKey) obj;
            if (sid__ != other.sid__) {
                return false;
            }
            if (ver__ != other.ver__) {
                return false;
            }
            return true;
        }

        /** コンストラクタ
         * @param sid テンプレートSID
         * @param ver テンプレートバージョン
         **/
        public TplKey(int sid, int ver) {
            super();
            sid__ = sid;
            ver__ = ver;
        }

        /**
         * <p>sid を取得します。
         * @return sid
         */
        public int getSid() {
            return sid__;
        }

        /**
         * <p>sid をセットします。
         * @param sid sid
         */
        public void setSid(int sid) {
            sid__ = sid;
        }

        /**
         * <p>ver を取得します。
         * @return ver
         */
        public int getVer() {
            return ver__;
        }

        /**
         * <p>ver をセットします。
         * @param ver ver
         */
        public void setVer(int ver) {
            ver__ = ver;
        }
    }

    /** ログ */
    private static Log log__ = LogFactory.getLog(ConvRingiDao.class);


    /**
    *
    * <br>[機  能] 稟議関連テーブルのコンバート処理
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @throws SQLException SQL実行時例外
    */
   public void convert(Connection con) throws SQLException {

       // 経路複写用のテーブル追加
       __createCopyKeiroStep(con);
       __createCopyKeirostepSelect(con);
       __addKeiroVersion(con);

       // 既存の稟議情報に経路バージョン番号を追加
       __updateRngRndataForKeiroVer(con);

       // ショートメール通知区分から後閲通知を除去し、代理通知を追加
       __alterNoticeKbn(con);

       // どのテンプレートにも添付されていない添付ファイル情報を添付マスタから論理削除
       __delAttachLogical(con);
       // どのテンプレートにも添付されていない添付ファイル情報を削除
       __delAttachNoneEachTemplate(con);
       // テンプレートへの添付情報をJSON文字列へ変換
       __addJsonToTemp(con);
       // テンプレートへの添付情報をテンプレートフォーム情報として登録
       __insertTempToForm(con);
       // 稟議テンプレート添付ファイル情報を稟議フォーム入力値情報に変換する
       __insertFormData(con);

   }

   /**
    * どのテンプレートにも添付されていない添付ファイル情報を削除
    * @param con コネクション
    * @throws SQLException SQL実行例外
    * */
   private void __delAttachNoneEachTemplate(Connection con) throws SQLException {

       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();

       try {
           // SQL文
           sql.addSql(" delete");
           sql.addSql(" from");
           sql.addSql("   RNG_TEMPLATE_BIN");
           sql.addSql(" where");
           sql.addSql("  not exists (");
           sql.addSql("     select");
           sql.addSql("       RNG_TEMPLATE.RTP_SID,");
           sql.addSql("       RNG_TEMPLATE.RTP_VER");
           sql.addSql("     from");
           sql.addSql("       RNG_TEMPLATE");
           sql.addSql("     where");
           sql.addSql("       RNG_TEMPLATE.RTP_SID = RNG_TEMPLATE_BIN.RTP_SID");
           sql.addSql("     and");
           sql.addSql("       RNG_TEMPLATE.RTP_VER = RNG_TEMPLATE_BIN.RTP_VER");
           sql.addSql("   )");

           pstmt = con.prepareStatement(sql.toSqlString());

           log__.info(sql.toLogString());
           pstmt.executeUpdate();

       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }

   /**
    * どのテンプレートにも添付されていない添付ファイル情報を論理削除
    * @param con コネクション
    * @throws SQLException SQL実行例外
    * */
   private void __delAttachLogical(Connection con) throws SQLException {

       PreparedStatement pstmt = null;
       SqlBuffer sql = new SqlBuffer();

       try {
           // SQL文
           sql.addSql(" update ");
           sql.addSql("   CMN_BINF");
           sql.addSql(" set");
           sql.addSql("   BIN_JKBN = 9");
           sql.addSql(" where");
           sql.addSql("   CMN_BINF.BIN_SID in (");
           sql.addSql("     select");
           sql.addSql("       RNG_TEMPLATE_BIN.BIN_SID");
           sql.addSql("     from");
           sql.addSql("       RNG_TEMPLATE_BIN");
           sql.addSql("     where");
           sql.addSql("     not exists (");
           sql.addSql("         select");
           sql.addSql("           RNG_TEMPLATE.RTP_SID,");
           sql.addSql("           RNG_TEMPLATE.RTP_VER");
           sql.addSql("         from");
           sql.addSql("           RNG_TEMPLATE");
           sql.addSql("         where");
           sql.addSql("           RNG_TEMPLATE.RTP_SID = RNG_TEMPLATE_BIN.RTP_SID");
           sql.addSql("         and");
           sql.addSql("           RNG_TEMPLATE.RTP_VER = RNG_TEMPLATE_BIN.RTP_VER");
           sql.addSql("     )");
           sql.addSql("   )");

           pstmt = con.prepareStatement(sql.toSqlString());

           log__.info(sql.toLogString());
           pstmt.executeUpdate();

       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
   }


   /**
    * テンプレートへの添付情報をJSON文字列へ変換
    * @param con コネクション
    * @return 更新件数
    * @throws SQLException SQL実行例外
    * */
   private int __addJsonToTemp(Connection con) throws SQLException {
       List<HashMap<TplKey, String>> jsonList = new ArrayList<HashMap<TplKey, String>>();
       jsonList = __getJsonArray(con);

       // 変換するテンプレートがない場合、終了
       if (jsonList.isEmpty() || jsonList.size() == 0) {
           return 0;
       }

       PreparedStatement pstmt = null;
       int count = 0;
       SqlBuffer sql = null;

       try {
           // SQL文
           sql = new SqlBuffer();
           sql.addSql(" update ");
           sql.addSql("   RNG_TEMPLATE ");
           sql.addSql(" set ");
           sql.addSql("   RTP_FORM=? ");
           sql.addSql(" where ");
           sql.addSql("   RTP_SID=? ");
           sql.addSql(" and ");
           sql.addSql("   RTP_VER=? ");
           sql.addSql(";");

           pstmt = con.prepareStatement(sql.toSqlString());

           for (HashMap<TplKey, String> json : jsonList) {
               for (HashMap.Entry<TplKey, String> entry : json.entrySet()) {
                   sql.addStrValue(entry.getValue());
                   sql.addIntValue(entry.getKey().getSid());
                   sql.addIntValue(entry.getKey().getVer());

                   log__.info(sql.toLogString());
                   sql.setParameter(pstmt);
                   sql.clearValue();
                   count += pstmt.executeUpdate();
               }
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
       return count;
   }

   /**
    * 添付情報を稟議テンプレートフォームへ変換
    * @param con コネクション
    * @return 更新件数
    * @throws SQLException SQL実行例外
    * */
   private int __insertTempToForm(Connection con) throws SQLException {

       List<RngTemplateFormModel> formList = __attachToTemplateForm(con);

       // 変換する添付情報がない場合、終了
       if (formList.isEmpty() || formList.size() == 0) {
           return 0;
       }

       PreparedStatement pstmt = null;
       int count = 0;
       SqlBuffer sql = null;

       try {
           // SQL文
           sql = new SqlBuffer();
           sql.addSql("insert into RNG_TEMPLATE_FORM (");
           sql.addSql("  RTP_SID,");
           sql.addSql("  RTP_VER,");
           sql.addSql("  RTF_SID,");
           sql.addSql("  RTF_ID,");
           sql.addSql("  RTF_TITLE,");
           sql.addSql("  RTF_REQUIRE,");
           sql.addSql("  RTF_TYPE");
           sql.addSql(") values (");
           sql.addSql("  ?,");
           sql.addSql("  ?,");
           sql.addSql("  ?,");
           sql.addSql("  ?,");
           sql.addSql("  ?,");
           sql.addSql("  ?,");
           sql.addSql("  ?");
           sql.addSql(");");

           pstmt = con.prepareStatement(sql.toSqlString());

           for (RngTemplateFormModel form : formList) {
               sql.addIntValue(form.getRtpSid());
               sql.addIntValue(form.getRtpVer());
               sql.addIntValue(form.getRtfSid());
               sql.addStrValue(form.getRtfId());
               sql.addStrValue(form.getRtfTitle());
               sql.addIntValue(form.getRtfRequire());
               sql.addIntValue(form.getRtfType());

               log__.info(sql.toLogString());
               sql.setParameter(pstmt);
               sql.clearValue();
               count += pstmt.executeUpdate();
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
       return count;
   }

   /**
    * 稟議フォーム入力値情報を登録
    * @param con コネクション
    * @return 更新件数
    * @throws SQLException SQL実行例外
    */
   private int __insertFormData(Connection con) throws SQLException {

       List<RngFormdataModel> formDataList = __attachToFormData(con);

       // 登録するデータがない場合、終了
       if (formDataList.isEmpty() || formDataList.size() == 0) {
           return 0;
       }

       PreparedStatement pstmt = null;
       int count = 0;
       SqlBuffer sql = null;

       try {
           // SQL文
           sql = new SqlBuffer();
           sql.addSql("insert into ");
           sql.addSql("  RNG_FORMDATA( ");
           sql.addSql("    RNG_SID,");
           sql.addSql("    RFD_SID,");
           sql.addSql("    RFD_ROWNO,");
           sql.addSql("    RFD_ID,");
           sql.addSql("    RFD_VALUE,");
           sql.addSql("    RFD_AUID,");
           sql.addSql("    RFD_ADATE,");
           sql.addSql("    RFD_EUID,");
           sql.addSql("    RFD_EDATE");
           sql.addSql("  ) values (");
           sql.addSql("    ?,");
           sql.addSql("    ?,");
           sql.addSql("    ?,");
           sql.addSql("    ?,");
           sql.addSql("    ?,");
           sql.addSql("    ?,");
           sql.addSql("    ?,");
           sql.addSql("    ?,");
           sql.addSql("    ?");
           sql.addSql("  );");

           pstmt = con.prepareStatement(sql.toSqlString());

           for (RngFormdataModel formData : formDataList) {
               sql.addIntValue(formData.getRngSid());
               sql.addIntValue(formData.getRfdSid());
               sql.addIntValue(formData.getRfdRowno());
               sql.addStrValue(formData.getRfdId());
               sql.addStrValue(formData.getRfdValue());
               sql.addIntValue(formData.getRfdAuid());
               sql.addDateValue(formData.getRfdAdate());
               sql.addIntValue(formData.getRfdEuid());
               sql.addDateValue(formData.getRfdEdate());

               log__.info(sql.toLogString());
               sql.setParameter(pstmt);
               sql.clearValue();
               count += pstmt.executeUpdate();
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeStatement(pstmt);
       }
       return count;
   }



    /**
     * テンプレートフォームを作成するためのJSON文字列を作成
     * @param con コネクション
     * @return JSON文字列ハッシュマップ
     * @throws SQLException SQL実行例外
     * */
    private List<HashMap<TplKey, String>> __getJsonArray(Connection con)
            throws SQLException {

        // テンプレートの申請内容を取得
        List<TemplateFormWithAttachModel> formOfTemp = __getTemplateFormWithAttach(con);
        HashMap<TplKey, List<TemplateFormWithAttachModel>> tempFormMap =
                new HashMap<TplKey, List<TemplateFormWithAttachModel>>();
        for (TemplateFormWithAttachModel form : formOfTemp) {
            TplKey key = new TplKey(form.getTemplateSid(), form.getTemplateVer());
            if (!tempFormMap.containsKey(key)) {
                List<TemplateFormWithAttachModel> formList =
                        new ArrayList<TemplateFormWithAttachModel>();
                formList.add(form);
                tempFormMap.put(key, formList);
            } else {
                tempFormMap.get(key).add(form);
            }
        }

        // フォームSIDおよびフォームID採番用データを取得
        HashMap<TplKey, List<Integer>> sbnFormSid = new HashMap<TplKey, List<Integer>>();
        HashMap<TplKey, List<String>> sbnFormId = new HashMap<TplKey, List<String>>();
        for (HashMap.Entry<TplKey, List<TemplateFormWithAttachModel>> entry
                : tempFormMap.entrySet()) {
            for (TemplateFormWithAttachModel model : entry.getValue()) {
                // フォームSID
                if (!sbnFormSid.containsKey(entry.getKey())) {
                    List<Integer> sid = new ArrayList<Integer>();
                    sid.add(model.getFormSid());
                    sbnFormSid.put(entry.getKey(), sid);
                } else {
                    sbnFormSid.get(entry.getKey()).add(model.getFormSid());
                }
                // フォームID
                if (!sbnFormId.containsKey(entry.getKey())) {
                    List<String> sid = new ArrayList<String>();
                    sid.add(model.getFormId());
                    sbnFormId.put(entry.getKey(), sid);
                } else {
                    sbnFormId.get(entry.getKey()).add(model.getFormId());
                }
            }
        }


        // テンプレートフォームおよび添付ファイルの情報
        HashMap<TplKey, List<TemplateFormWithAttachModel>> formMap =
                new HashMap<TplKey, List<TemplateFormWithAttachModel>>();

        for (HashMap.Entry<TplKey, List<TemplateFormWithAttachModel>> entry
                : tempFormMap.entrySet()) {
            // 各テンプレートのフォームSID最大値
            int maxFormSid = 0;
            for (TemplateFormWithAttachModel model : entry.getValue()) {
                // 添付ファイルのあるテンプレートを解析

                // フォームSIDの採番
                // 既存フォームSIDの最大値を取得
                int newFormSid = 0;
                for (int formSid : sbnFormSid.get(entry.getKey())) {
                    if (maxFormSid < formSid) {
                        maxFormSid = formSid;
                    }
                }
                // 最大値の更新
                newFormSid = maxFormSid + 1;
                sbnFormSid.get(entry.getKey()).add(newFormSid);
                // フォームIDの取得 フォーマット："file_[連番]"
                // 現在使用されていないフォームIDを使用
                List<String> idList = sbnFormId.get(entry.getKey());
                String newFormId = null;
                // "file_1"が使用されているか
                if (idList.contains(new String("file_1"))) {
                    boolean search = true;
                    int idNum = 2;
                    while (search) {
                        // "file_2"以降、重複しないものが見つかるまで探索
                        if (!idList.contains(new String("file_" + idNum))) {
                            String id = "file_" + idNum;
                            newFormId = id;
                            sbnFormId.get(entry.getKey()).add(newFormId);
                            search = false;
                        } else {
                            idNum++;
                        }
                    }
                } else {
                    newFormId = "file_1";
                    sbnFormId.get(entry.getKey()).add(newFormId);
                }
                // 添付ファイルの取得
                Long binSid = model.getBinSid();
                //テンプレートフォームの作成
                TemplateFormWithAttachModel form = new TemplateFormWithAttachModel();
                form.setTemplateSid(entry.getKey().getSid());
                form.setTemplateVer(entry.getKey().getVer());
                form.setFormSid(newFormSid);
                form.setFormId(newFormId);
                form.setBinSid(binSid);
                if (!formMap.containsKey(entry.getKey())) {
                    List<TemplateFormWithAttachModel> formList =
                            new ArrayList<TemplateFormWithAttachModel>();
                    formList.add(form);
                    formMap.put(entry.getKey(), formList);
                } else {
                    formMap.get(entry.getKey()).add(form);
                }
            }
        }

        // JSON文字列の生成
        List<HashMap<TplKey, String>> jsonOfForm = new ArrayList<HashMap<TplKey, String>>();
        // 添付ファイルがあるテンプレートの情報を取得
        List<RngTemplateModel> templateList = __getTemplate(con);
        for (RngTemplateModel templateMdl : templateList) {
            TplKey key = new TplKey(templateMdl.getRtpSid(), templateMdl.getRtpVer());
            List<TemplateFormWithAttachModel> inputForm = formMap.get(key);
            JSONArray jsonData = JSONArray.fromObject(templateMdl.getRtpForm());
            JSONObject addJson = new JSONObject();
            String[] binSids = null;
            if (templateMdl.getRtbTemp() == 1) {
                //添付ファイルの取得
                List<String> binList = new ArrayList<String>();
                for (int i = 0; i < inputForm.size(); i++) {
                    // バイナリSID
                    String binSid = Long.toString(inputForm.get(i).getBinSid());
                    if (!binList.contains(binSid)) {
                        binList.add(Long.toString(inputForm.get(i).getBinSid()));
                    }
                }
                binSids = binList.toArray(new String[binList.size()]);
            }
            JSONObject body = new JSONObject();
            body.put("sample", binSids);
            body.put("sampleList", "");
            addJson.put("body", body);
            addJson.put("formID", inputForm.get(0).getFormId());
            addJson.put("otherContents", new JSONObject());
            addJson.put("require", 0);
            addJson.put("sid", inputForm.get(0).getFormSid());
            addJson.put("title", "添付");
            addJson.put("titleRequireFlg", 1);
            addJson.put("type", EnumFormModelKbn.file);
            JSONArray jsonArr =
                    JSONArray.fromObject(addJson);
            jsonData.add(jsonArr);
            String json = jsonData.toString();
            HashMap<TplKey, String> bean = new HashMap<TplKey, String>();
            bean.put(key, json);
            jsonOfForm.add(bean);
        }
        return jsonOfForm;
    }


    /**
     * 添付情報を稟議テンプレートフォームへ変換
     * @param con コネクション
     * @return 稟議テンプレートフォームリスト
     * @throws SQLException SQL実行例外
     * */
    private List<RngTemplateFormModel> __attachToTemplateForm(Connection con)
            throws SQLException {

        // ファイルを添付した稟議テンプレートを取得
        List<RngTemplateBinModel> templateBinList = __getTemplateBinList(con);

        // ファイルを添付したテンプレートがない場合、終了
        if (templateBinList.isEmpty() | templateBinList.size() == 0) {
            return new ArrayList<RngTemplateFormModel>();
        }

        // 添付ファイルのあるテンプレートのSIDおよびバージョンを取得
        List<TplKey> attachedTemplate = new ArrayList<TplKey>();
        HashMap<Integer, Integer> checkMap = new HashMap<Integer, Integer>();
        for (RngTemplateBinModel bin : templateBinList) {
            if (checkMap.containsKey(bin.getRtpSid())
                    && checkMap.containsValue(bin.getRtpVer())) {
                continue;
            } else {
                checkMap.put(bin.getRtpSid(), bin.getRtpVer());
                TplKey tpl = new TplKey(bin.getRtpSid(), bin.getRtpVer());
                attachedTemplate.add(tpl);
            }
        }
        // テンプレートの申請内容を取得
        List<RngTemplateFormModel> formOfTemp =
                __getTemplateFormList(con, attachedTemplate);
        HashMap<TplKey, List<RngTemplateFormModel>> tempFormMap =
                new HashMap<TplKey, List<RngTemplateFormModel>>();
        for (RngTemplateFormModel form : formOfTemp) {
            TplKey key = new TplKey(form.getRtpSid(), form.getRtpVer());
            if (!tempFormMap.containsKey(key)) {
                List<RngTemplateFormModel> list =
                        new ArrayList<RngTemplateFormModel>();
                list.add(form);
                tempFormMap.put(key, list);
            } else {
                tempFormMap.get(key).add(form);
            }
        }
        // フォームSIDおよびフォームID採番用データを取得
        HashMap<TplKey, List<Integer>> sbnFormSid = new HashMap<TplKey, List<Integer>>();
        HashMap<TplKey, List<String>> sbnFormId = new HashMap<TplKey, List<String>>();
        for (HashMap.Entry<TplKey, List<RngTemplateFormModel>> entry
                : tempFormMap.entrySet()) {
            for (RngTemplateFormModel model : entry.getValue()) {
                // フォームSID
                if (!sbnFormSid.containsKey(entry.getKey())) {
                    List<Integer> sid = new ArrayList<Integer>();
                    sid.add(model.getRtfSid());
                    sbnFormSid.put(entry.getKey(), sid);
                } else {
                    sbnFormSid.get(entry.getKey()).add(model.getRtfSid());
                }
                // フォームID
                if (!sbnFormId.containsKey(entry.getKey())) {
                    List<String> sid = new ArrayList<String>();
                    sid.add(model.getRtfId());
                    sbnFormId.put(entry.getKey(), sid);
                } else {
                    sbnFormId.get(entry.getKey()).add(model.getRtfId());
                }
            }
        }

        List<RngTemplateFormModel> formList = new ArrayList<RngTemplateFormModel>();
        for (TplKey key : attachedTemplate) {
            // 添付情報を稟議テンプレートフォームへ変換

            // 各テンプレートのフォームSID最大値
            int maxFormSid = 0;

            // フォームSIDの採番
            // 既存フォームSIDの最大値を取得
            int newFormSid = 0;
            for (int formSid : sbnFormSid.get(key)) {
                if (maxFormSid < formSid) {
                    maxFormSid = formSid;
                }
            }
            // 最大値の更新
            newFormSid = maxFormSid + 1;
            sbnFormSid.get(key).add(newFormSid);
            // フォームIDの取得 フォーマット："file_[連番]"
            // 現在使用されていないフォームIDを使用
            List<String> idList = sbnFormId.get(key);
            String newFormId = null;
            // "file_1"が使用されているか
            if (idList.contains(new String("file_1"))) {
                boolean search = true;
                int idNum = 2;
                while (search) {
                    // "file_2"以降、重複しないものが見つかるまで探索
                    if (!idList.contains(new String("file_" + idNum))) {
                        String id = "file_" + idNum;
                        newFormId = id;
                        sbnFormId.get(key).add(newFormId);
                        search = false;
                    } else {
                        idNum++;
                    }
                }
            } else {
                newFormId = "file_1";
                sbnFormId.get(key).add(newFormId);
            }
            //テンプレートフォームの作成
            RngTemplateFormModel form = new RngTemplateFormModel();
            form.setRtpSid(key.getSid());
            form.setRtpVer(key.getVer());
            form.setRtfSid(newFormSid);
            form.setRtfId(newFormId);
            form.setRtfTitle("添付");
            form.setRtfRequire(0);
            form.setRtfType(EnumFormModelKbn.file.getValue());
            formList.add(form);
        }
        return formList;
    }


    /**
     * 添付ファイル情報を稟議フォーム入力値へ変換します
     * @param con コネクション
     * @return 稟議フォーム入力値
     * @throws SQLException SQL実行例外
     * */
    private List<RngFormdataModel> __attachToFormData(Connection con) throws SQLException {

        // 稟議および添付ファイルを取得
        List<RngFormWithAttachModel> attachedList = __getRngFormWithAttach(con);

        // ファイルを添付した稟議がない場合、終了
        if (attachedList.isEmpty() || attachedList.size() == 0) {
            return new ArrayList<RngFormdataModel>();
        }


        HashMap<Integer, List<RngFormWithAttachModel>> everyRingi =
                new HashMap<Integer, List<RngFormWithAttachModel>>();

        for (RngFormWithAttachModel attached : attachedList) {
        // フォーム情報を稟議ごとに取得
            if (!everyRingi.containsKey(attached.getRngSid())) {
                List<RngFormWithAttachModel> form = new ArrayList<RngFormWithAttachModel>();
                form.add(attached);
                everyRingi.put(attached.getRngSid(), form);
            } else {
                everyRingi.get(attached.getRngSid()).add(attached);
            }
        }

        // 添付情報を稟議フォーム入力値情報へ変換
        List<RngFormdataModel> formDataList = new ArrayList<RngFormdataModel>();
        for (HashMap.Entry<Integer, List<RngFormWithAttachModel>> entry : everyRingi.entrySet()) {

            // フォームSIDの採番
            // 既存フォームSIDの最大値を取得
            int maxFormSid = 0;
            for (RngFormWithAttachModel oneForm : entry.getValue()) {
                int sid = oneForm.getRfdSid();
                if (sid > maxFormSid) {
                    maxFormSid = sid;
                }
            }
            int newFormSid = maxFormSid + 1;

            // フォームIDの取得 フォーマット："file_[連番]"
            // 現在使用されていないフォームIDを使用
            List<String> idList = new ArrayList<String>();
            String newFormId = null;
            // 汎用稟議テンプレートもしくは個人テンプレートの場合
            if (entry.getValue().get(0).getRtpSid() == 0
                    || entry.getValue().get(0).getRtpType() == 2) {
                newFormId = "汎用稟議＿添付";

            // 共有テンプレートの場合
            } else if (entry.getValue().get(0).getRtpType() == 1) {
                for (RngFormWithAttachModel oneForm : entry.getValue()) {
                    idList.add(oneForm.getRfdId());
                }
                // "file_1"が使用されているか
                if (idList.contains(new String("file_1"))) {
                    boolean search = true;
                    int idNum = 2;
                    while (search) {
                        // "file_2"以降、重複しないものが見つかるまで探索
                        if (!idList.contains(new String("file_" + idNum))) {
                            String id = "file_" + idNum;
                            newFormId = id;
                            search = false;
                        } else {
                            idNum++;
                        }
                    }
                } else {
                    newFormId = "file_1";
                }
            }

            // 添付ファイルをフォームの内容として登録
            for (RngFormWithAttachModel oneForm : entry.getValue()) {
                RngFormdataModel form = new RngFormdataModel();
                form.setRngSid(entry.getKey());
                form.setRfdSid(newFormSid);
                form.setRfdId(newFormId);
                form.setRfdValue(Long.toString(oneForm.getBinSid()));
                form.setRfdAuid(oneForm.getRfdAuid());
                form.setRfdAdate(oneForm.getRfdAdate());
                form.setRfdEuid(oneForm.getRfdEuid());
                form.setRfdEdate(oneForm.getRfdEdate());
                formDataList.add(form);
            }
        }
        return formDataList;
    }


    /**
     * <p>Select RNG_TEMPLATE_BIN All Data
     * @param con コネクション
     * @return List in RNG_TEMPLATE_BINModel
     * @throws SQLException SQL実行例外
     */
    private List<RngTemplateBinModel> __getTemplateBinList(Connection con)
            throws SQLException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<RngTemplateBinModel> ret = new ArrayList<RngTemplateBinModel>();

        try {
            //SQL文
            SqlBuffer sql = new SqlBuffer();
            sql.addSql(" select ");
            sql.addSql("   RNG_TEMPLATE_BIN.RTP_SID,");
            sql.addSql("   RNG_TEMPLATE_BIN.BIN_SID,");
            sql.addSql("   RNG_TEMPLATE_BIN.RTP_VER");
            sql.addSql(" from ");
            sql.addSql("   RNG_TEMPLATE_BIN");

            pstmt = con.prepareStatement(sql.toSqlString());
            log__.info(sql.toLogString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                RngTemplateBinModel bean = new RngTemplateBinModel();
                bean.setRtpSid(rs.getInt("RTP_SID"));
                bean.setBinSid(rs.getLong("BIN_SID"));
                bean.setRtpVer(rs.getInt("RTP_VER"));
                ret.add(bean);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeResultSet(rs);
            JDBCUtil.closeStatement(pstmt);
        }
        return ret;
    }


    /**
    *
    * <br>[機  能] 添付ファイルがあるテンプレートを返す
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @return List in RNG_TEMPLATE_FORMModel
    * @throws SQLException SQL実行例外
    */
   private List<TemplateFormWithAttachModel> __getTemplateFormWithAttach(
           Connection con) throws SQLException {

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       ArrayList<TemplateFormWithAttachModel> ret = new ArrayList<TemplateFormWithAttachModel>();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select ");
           sql.addSql("   RNG_TEMPLATE_FORM.RTP_SID,");
           sql.addSql("   RNG_TEMPLATE_FORM.RTP_VER,");
           sql.addSql("   RNG_TEMPLATE_FORM.RTF_SID,");
           sql.addSql("   RNG_TEMPLATE_FORM.RTF_ID,");
           sql.addSql("   RNG_TEMPLATE_BIN.BIN_SID");
           sql.addSql(" from ");
           sql.addSql("   RNG_TEMPLATE_FORM");
           sql.addSql(" left join ");
           sql.addSql("   RNG_TEMPLATE_BIN ");
           sql.addSql("   on ");
           sql.addSql("     RNG_TEMPLATE_FORM.RTP_SID = RNG_TEMPLATE_BIN.RTP_SID ");
           sql.addSql("   and ");
           sql.addSql("     RNG_TEMPLATE_FORM.RTP_VER = RNG_TEMPLATE_BIN.RTP_VER ");

           pstmt = con.prepareStatement(sql.toSqlString());

           log__.info(sql.toLogString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();
           while (rs.next()) {
               TemplateFormWithAttachModel bean = new TemplateFormWithAttachModel();
               bean.setTemplateSid(rs.getInt("RTP_SID"));
               bean.setTemplateVer(rs.getInt("RTP_VER"));
               bean.setFormSid(rs.getInt("RTF_SID"));
               bean.setFormId(rs.getString("RTF_ID"));
               bean.setBinSid(rs.getLong("BIN_SID"));
               ret.add(bean);
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeResultSet(rs);
           JDBCUtil.closeStatement(pstmt);
       }
       return ret;
   }


    /**
    *
    * <br>[機  能] 最新テンプレートで使用されているRngTemplateFormModelを返す
    * <br>[解  説]
    * <br>[備  考]
    * @param con コネクション
    * @param tplKeyList テンプレートSIDおよびバージョン
    * @return List in RNG_TEMPLATE_FORMModel
    * @throws SQLException SQL実行例外
    */
   private List<RngTemplateFormModel> __getTemplateFormList(
           Connection con,
           List<TplKey> tplKeyList) throws SQLException {

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       ArrayList<RngTemplateFormModel> ret = new ArrayList<RngTemplateFormModel>();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select ");
           sql.addSql("   RNG_TEMPLATE_FORM.RTP_SID,");
           sql.addSql("   RNG_TEMPLATE_FORM.RTP_VER,");
           sql.addSql("   RNG_TEMPLATE_FORM.RTF_SID,");
           sql.addSql("   RNG_TEMPLATE_FORM.RTF_ID,");
           sql.addSql("   RNG_TEMPLATE_FORM.RTF_TITLE,");
           sql.addSql("   RNG_TEMPLATE_FORM.RTF_REQUIRE,");
           sql.addSql("   RNG_TEMPLATE_FORM.RTF_TYPE,");
           sql.addSql("   RNG_TEMPLATE_FORM.RTF_BODY");
           sql.addSql(" from ");
           sql.addSql("   RNG_TEMPLATE_FORM");
           sql.addSql(" where ");
           for (int idx = 0; idx < tplKeyList.size(); idx++) {
               if (idx > 0) {
                   sql.addSql(" or ");
               }
               sql.addSql(" (");
               sql.addSql("   RNG_TEMPLATE_FORM.RTP_SID= ?");
               sql.addIntValue(tplKeyList.get(idx).getSid());
               sql.addSql(" and ");
               sql.addSql("   RNG_TEMPLATE_FORM.RTP_VER = ? ");
               sql.addIntValue(tplKeyList.get(idx).getVer());
               sql.addSql(" )");
           }

           pstmt = con.prepareStatement(sql.toSqlString());

           log__.info(sql.toLogString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();
           while (rs.next()) {
               RngTemplateFormModel bean = new RngTemplateFormModel();
               bean.setRtpSid(rs.getInt("RTP_SID"));
               bean.setRtpVer(rs.getInt("RTP_VER"));
               bean.setRtfSid(rs.getInt("RTF_SID"));
               bean.setRtfId(rs.getString("RTF_ID"));
               bean.setRtfTitle(rs.getString("RTF_TITLE"));
               bean.setRtfRequire(rs.getInt("RTF_REQUIRE"));
               bean.setRtfType(rs.getInt("RTF_TYPE"));
               bean.setRtfBody(rs.getString("RTF_BODY"));
               ret.add(bean);
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeResultSet(rs);
           JDBCUtil.closeStatement(pstmt);
       }
       return ret;
   }


   /**
    * <p>Select RNG_TEMPLATE
    * @param con コネクション
    * @return RNG_TEMPLATEModel
    * @throws SQLException SQL実行例外
    */
   private List<RngTemplateModel> __getTemplate(
           Connection con) throws SQLException {

       PreparedStatement pstmt = null;
       ResultSet rs = null;
       List<RngTemplateModel> ret = new ArrayList<RngTemplateModel>();

       try {
           //SQL文
           SqlBuffer sql = new SqlBuffer();
           sql.addSql(" select");
           sql.addSql("   RNG_TEMPLATE.RTP_SID,");
           sql.addSql("   RNG_TEMPLATE.RTP_TYPE,");
           sql.addSql("   RNG_TEMPLATE.USR_SID,");
           sql.addSql("   RNG_TEMPLATE.RTP_TITLE,");
           sql.addSql("   RNG_TEMPLATE.RTP_RNG_TITLE,");
           sql.addSql("   RNG_TEMPLATE.RTP_SORT,");
           sql.addSql("   RNG_TEMPLATE.RTP_AUID,");
           sql.addSql("   RNG_TEMPLATE.RTP_ADATE,");
           sql.addSql("   RNG_TEMPLATE.RTP_EUID,");
           sql.addSql("   RNG_TEMPLATE.RTP_EDATE,");
           sql.addSql("   RNG_TEMPLATE.RTC_SID,");
           sql.addSql("   RNG_TEMPLATE.RTP_VER, ");
           sql.addSql("   RNG_TEMPLATE.RTP_MAXVER_KBN, ");
           sql.addSql("   RNG_TEMPLATE.RTP_JKBN, ");
           sql.addSql("   RNG_TEMPLATE.RTP_IDFORMAT_SID, ");
           sql.addSql("   RNG_TEMPLATE.RTP_FORM, ");
           sql.addSql("   RNG_TEMPLATE.RCT_SID, ");
           sql.addSql("   RNG_TEMPLATE.RCT_USR_SID, ");
           sql.addSql("   RNG_TEMPLATE.RTP_BIKO,");
           sql.addSql("   RNG_TEMPLATE.RTP_IDMANUAL, ");
           sql.addSql("   RNG_TEMPLATE.RTP_SPEC_VER,");
           sql.addSql("   case when RNG_TEMPLATE_BIN.BIN_SID IS NULL THEN 0");
           sql.addSql("   else 1");
           sql.addSql("   end as RTB_TEMP");
           sql.addSql(" from");
           sql.addSql("   RNG_TEMPLATE");
           sql.addSql(" left join");
           sql.addSql("   RNG_TEMPLATE_BIN");
           sql.addSql(" on");
           sql.addSql("   RNG_TEMPLATE.RTP_SID = RNG_TEMPLATE_BIN.RTP_SID");
           sql.addSql(" and");
           sql.addSql("   RNG_TEMPLATE.RTP_VER = RNG_TEMPLATE_BIN.RTP_VER");
           sql.addSql(" order by RNG_TEMPLATE.RTP_VER desc");

           pstmt = con.prepareStatement(sql.toSqlString());

           log__.info(sql.toLogString());
           sql.setParameter(pstmt);
           rs = pstmt.executeQuery();
           while (rs.next()) {
               RngTemplateModel bean = new RngTemplateModel();
               bean.setRtpSid(rs.getInt("RTP_SID"));
               bean.setRtpType(rs.getInt("RTP_TYPE"));
               bean.setUsrSid(rs.getInt("USR_SID"));
               bean.setRtpTitle(rs.getString("RTP_TITLE"));
               bean.setRtpRngTitle(rs.getString("RTP_RNG_TITLE"));
               bean.setRtpSort(rs.getInt("RTP_SORT"));
               bean.setRtpAuid(rs.getInt("RTP_AUID"));
               bean.setRtpAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RTP_ADATE")));
               bean.setRtpEuid(rs.getInt("RTP_EUID"));
               bean.setRtpEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RTP_EDATE")));
               bean.setRtcSid(rs.getInt("RTC_SID"));
               bean.setRtpVer(rs.getInt("RTP_VER"));
               bean.setRtpMaxverKbn(rs.getInt("RTP_MAXVER_KBN"));
               bean.setRtpJkbn(rs.getInt("RTP_JKBN"));
               bean.setRtpIdformatSid(rs.getInt("RTP_IDFORMAT_SID"));
               bean.setRtpForm(rs.getString("RTP_FORM"));
               bean.setRctSid(rs.getInt("RCT_SID"));
               bean.setRctUsrSid(rs.getInt("RCT_USR_SID"));
               bean.setRtpBiko(rs.getString("RTP_BIKO"));
               bean.setRtpIdmanual(rs.getInt("RTP_IDMANUAL"));
               bean.setRtpSpecVer(rs.getInt("RTP_SPEC_VER"));
               bean.setRtbTemp(rs.getInt("RTB_TEMP"));
               ret.add(bean);
           }
       } catch (SQLException e) {
           throw e;
       } finally {
           JDBCUtil.closeResultSet(rs);
           JDBCUtil.closeStatement(pstmt);
       }
       return ret;
   }

   /**
   *
   * <br>[機  能] 申請済み稟議で使用されているテンプレートのRngTemplateFormModelを返す
   * <br>[解  説] ファイルが添付されている稟議のみ返す
   * <br>[備  考]
   * @param con コネクション
   * @return List in RNG_TEMPLATE_FORMModel
   * @throws SQLException SQL実行例外
   */
  private List<RngFormWithAttachModel> __getRngFormWithAttach(
          Connection con) throws SQLException {

      PreparedStatement pstmt = null;
      ResultSet rs = null;
      ArrayList<RngFormWithAttachModel> ret = new ArrayList<RngFormWithAttachModel>();

      try {
          //SQL文
          SqlBuffer sql = new SqlBuffer();
          sql.addSql(" select ");
          sql.addSql("   RNG_RNDATA.RNG_SID,");
          sql.addSql("   RNG_RNDATA.RTP_SID,");
          sql.addSql("   RNG_FORMDATA.RFD_SID,");
          sql.addSql("   RNG_FORMDATA.RFD_ID,");
          sql.addSql("   RNG_FORMDATA.RFD_VALUE,");
          sql.addSql("   RNG_FORMDATA.RFD_AUID,");
          sql.addSql("   RNG_FORMDATA.RFD_ADATE,");
          sql.addSql("   RNG_FORMDATA.RFD_EUID,");
          sql.addSql("   RNG_FORMDATA.RFD_EDATE,");
          sql.addSql("   RNG_BIN.BIN_SID,");
          sql.addSql("   RNG_TEMPLATE.RTP_TYPE");
          sql.addSql(" from ");
          sql.addSql("   RNG_FORMDATA");
          sql.addSql(" left join");
          sql.addSql("   RNG_RNDATA");
          sql.addSql(" on");
          sql.addSql("   RNG_FORMDATA.RNG_SID = RNG_RNDATA.RNG_SID");
          sql.addSql(" left join ");
          sql.addSql("   RNG_BIN");
          sql.addSql(" on");
          sql.addSql("   RNG_RNDATA.RNG_SID = RNG_BIN.RNG_SID ");
          sql.addSql(" left join");
          sql.addSql("   RNG_TEMPLATE");
          sql.addSql(" on(");
          sql.addSql("     RNG_RNDATA.RTP_SID = RNG_TEMPLATE.RTP_SID");
          sql.addSql("   and");
          sql.addSql("     RNG_RNDATA.RTP_VER = RNG_TEMPLATE.RTP_VER");
          sql.addSql(" )");
          sql.addSql("");
          sql.addSql(" where ");
          sql.addSql("   RNG_BIN.BIN_SID is not null");
          sql.addSql(" and ");
          sql.addSql("   RNG_BIN.USR_SID = 0");

          pstmt = con.prepareStatement(sql.toSqlString());

          log__.info(sql.toLogString());
          sql.setParameter(pstmt);
          rs = pstmt.executeQuery();
          while (rs.next()) {
              RngFormWithAttachModel bean = new RngFormWithAttachModel();
              bean.setRngSid(rs.getInt("RNG_SID"));
              bean.setRtpSid(rs.getInt("RTP_SID"));
              bean.setRfdSid(rs.getInt("RFD_SID"));
              bean.setRfdId(rs.getString("RFD_ID"));
              bean.setRfdValue(rs.getString("RFD_VALUE"));
              bean.setRfdAuid(rs.getInt("RFD_AUID"));
              bean.setRfdAdate(UDate.getInstanceTimestamp(rs.getTimestamp("RFD_ADATE")));
              bean.setRfdEuid(rs.getInt("RFD_EUID"));
              bean.setRfdEdate(UDate.getInstanceTimestamp(rs.getTimestamp("RFD_EDATE")));
              bean.setBinSid(rs.getLong("BIN_SID"));
              bean.setRtpType(rs.getInt("RTP_TYPE"));
              ret.add(bean);
          }
      } catch (SQLException e) {
          throw e;
      } finally {
          JDBCUtil.closeResultSet(rs);
          JDBCUtil.closeStatement(pstmt);
      }
      return ret;
  }

    /**
     * 複写用経路ステップテーブルを作成
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * */
    private void __createCopyKeiroStep(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();

        try {
            // SQL文
            sql = new SqlBuffer();
            sql.addSql(" create table");
            sql.addSql("   RNG_COPY_KEIRO_STEP");
            sql.addSql("     (");
            sql.addSql("       RKS_SID  integer not null,");
            sql.addSql("       RCK_SORT integer not null,");
            sql.addSql("       RTK_SID  integer not null,");
            sql.addSql("       RKS_BELONG_SID integer not null,");
            sql.addSql("       primary key (RKS_SID, RCK_SORT)");
            sql.addSql("     )");
            sql.addSql("     ;");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * 複写用経路ステップ選択ユーザテーブルを作成
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * */
    private void __createCopyKeirostepSelect(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();

        try {
            // SQL文
            sql = new SqlBuffer();
            sql.addSql(" create table");
            sql.addSql("   RNG_COPY_KEIROSTEP_SELECT");
            sql.addSql("     (");
            sql.addSql("       RKS_SID  integer not null,");
            sql.addSql("       RCK_SORT integer not null,");
            sql.addSql("       USR_SID  integer not null,");
            sql.addSql("       GRP_SID  integer not null,");
            sql.addSql("       POS_SID  integer not null,");
            sql.addSql("       primary key (RKS_SID,RCK_SORT,USR_SID,GRP_SID,POS_SID)");
            sql.addSql("     )");
            sql.addSql("     ;");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * 経路バージョンのフィールドを各テーブルへ追加
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * */
    private void __addKeiroVersion(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();

        try {
            // SQL文
            sql = new SqlBuffer();
            sql.addSql(" alter table RNG_CHANNEL_TEMPLATE add RCT_VER integer not null default 0;");
            sql.addSql(" alter table RNG_TEMPLATE add RCT_VER integer not null default 0;");
            sql.addSql(" alter table RNG_RNDATA add RCT_VER integer not null default 0;");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }

    /**
     * 稟議情報の経路バージョンに -1 を登録(以下に該当する稟議情報は除外)
     *  ・草稿保存された稟議
     *  ・汎用稟議テンプレートを使用している稟議
     *  ・個人テンプレートを使用している稟議
     *  ・v4.8.0以前のテンプレートを使用している稟議
     * ※前バージョンで作成された稟議情報は複写できないので、バージョンエラーにより強制的に経路初期状態を表示させる為)
     * @param con コネクション
     * @throws SQLException SQL実行例外
     * */
    private void __updateRngRndataForKeiroVer(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        SqlBuffer sql = new SqlBuffer();

        try {
            // SQL文
            sql = new SqlBuffer();
            sql.addSql(" update");
            sql.addSql("   RNG_RNDATA");
            sql.addSql(" set");
            sql.addSql("   RCT_VER = -1");
            sql.addSql(" where");
            sql.addSql("   RTP_SID > 0");
            sql.addSql(" and");
            sql.addSql("   RNG_STATUS > 0");
            sql.addSql(" and");
            sql.addSql("   RTP_SID in (");
            sql.addSql("     select");
            sql.addSql("       RTP_SID");
            sql.addSql("     from");
            sql.addSql("       RNG_TEMPLATE");
            sql.addSql("     where");
            sql.addSql("       RTP_TYPE = 1");
            sql.addSql("     and");
            sql.addSql("       RTP_SPEC_VER = 1");
            sql.addSql(" )");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }


    /**
     * ショートメール通知区分の追加および除去を行う
     * @param con コネクション
     * @exception SQLException SQL実行例外
     * */
    private void __alterNoticeKbn(Connection con) throws SQLException {

        PreparedStatement pstmt = null;
        SqlBuffer sql = null;

        try {
            // SQL文
            sql = new SqlBuffer();
            sql.addSql("alter table RNG_ACONF drop column RAR_SML_KOETU_KBN;");
            sql.addSql("alter table RNG_UCONF drop column RUR_SML_KOETU;");
            sql.addSql("alter table RNG_ACONF add column RAR_SML_DAIRI_KBN integer;");
            sql.addSql("alter table RNG_UCONF add column RUR_SML_DAIRI integer;");

            pstmt = con.prepareStatement(sql.toSqlString());

            log__.info(sql.toLogString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw e;
        } finally {
            JDBCUtil.closeStatement(pstmt);
        }
    }
}
