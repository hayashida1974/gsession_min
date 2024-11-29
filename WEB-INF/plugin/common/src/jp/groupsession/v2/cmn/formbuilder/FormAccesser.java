package jp.groupsession.v2.cmn.formbuilder;

/**
    *
    * <br>[機  能] フォーム入力値アクセス用クラス
    * <br>[解  説]
    * <br>[備  考] SIDで内部の要素にアクセスするMapのキーとして使用する
    *
    * @author JTS
    */
   public class FormAccesser {
       /**フォームSID*/
       private int formSid__;
       /**行番号*/
       private int rowNo__;


        /**
         * @param formSid formSid
         * @param rowNo 行番号
         */
        public FormAccesser(int formSid, int rowNo) {
            super();
            formSid__ = formSid;
            rowNo__ = rowNo;
        }
        /**
         * <p>formSid を取得します。
         * @return formSid
         */
        public int getFormSid() {
            return formSid__;
        }
        /**
         * <p>formSid をセットします。
         * @param formSid formSid
         */
        public void setFormSid(int formSid) {
            formSid__ = formSid;
        }

        /**
         * <p>rowNo を取得します。
         * @return rowNo
         */
        public int getRowNo() {
            return rowNo__;
        }
        /**
         * <p>rowNo をセットします。
         * @param rowNo rowNo
         */
        public void setRowNo(int rowNo) {
            rowNo__ = rowNo;
        }
        /* (非 Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + formSid__;
            result = prime * result + rowNo__;
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
            if (!(obj instanceof FormAccesser)) {
                return false;
            }
            FormAccesser other = (FormAccesser) obj;
            if (formSid__ != other.formSid__) {
                return false;
            }
            if (rowNo__ != other.rowNo__) {
                return false;
            }
            return true;
        }
   }