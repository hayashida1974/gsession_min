package jp.co.sjts.util.qrcode;

/**
 * <br>[機  能] Qrコード用アドレス帳に関するユーティリティ
 * <br>[解  説]
 * <br>[備  考]
 * @author JTS
 */
public class QrUtil {

    /**
     * <br>[機  能] DocomoQRコード用アドレス文字列を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param model アドレスモデル
     * @return DocomoQRコード用アドレス文字列
     */
    public static String toDocomoAddressString(QrAddressModel model) {

        StringBuilder buf = new StringBuilder();
        //アドレス帳データ識別子
        buf.append("MECARD:");
        //氏名
        if (model.getNameSei() != null) {
            buf.append("N:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(",");
                buf.append(model.getNameMei());
            }
            buf.append(";");
        } else if (model.getNameMei() != null)  {
            buf.append("N:");
            buf.append(model.getNameMei());
            buf.append(";");
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameSeiKn());
            if (model.getNameMeiKn() != null) {
                buf.append(",");
                buf.append(model.getNameMeiKn());
            }
            buf.append(";");
        } else if (model.getNameMeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameMeiKn());
            buf.append(";");
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL:");
            buf.append(model.getTel1());
            buf.append(";");
        }
        //TEL2
        if (model.getTel2() != null) {
            buf.append("TEL:");
            buf.append(model.getTel2());
            buf.append(";");
        }

        //TEL3
        if (model.getTel3() != null) {
            buf.append("TEL:");
            buf.append(model.getTel3());
            buf.append(";");
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail1());
            buf.append(";");
        }

        //メール2
        if (model.getMail2() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail2());
            buf.append(";");
        }

        //メール3
        if (model.getMail3() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail3());
            buf.append(";");
        }

        buf.append(";");

        return buf.toString();
    }

    /**
     * <br>[機  能] DocomoQRコード用アドレス文字列を返す(ルール１)
     * <br>[解  説] ルール１ カナを1文字にする
     * <br>[備  考]
     * @param model アドレスモデル
     * @return DocomoQRコード用アドレス文字列
     */
    public static String toDocomoAddressStringRule1(QrAddressModel model) {

        StringBuilder buf = new StringBuilder();
        //アドレス帳データ識別子
        buf.append("MECARD:");
        //氏名
        if (model.getNameSei() != null) {
            buf.append("N:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(",");
                buf.append(model.getNameMei());
            }
            buf.append(";");
        } else if (model.getNameMei() != null)  {
            buf.append("N:");
            buf.append(model.getNameMei());
            buf.append(";");
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            if (model.getNameMeiKn() != null) {
                buf.append(",");
                buf.append(model.getNameMeiKn().substring(0, 1));
            }
            buf.append(";");
        } else if (model.getNameMeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(";");
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL:");
            buf.append(model.getTel1());
            buf.append(";");
        }
        //TEL2
        if (model.getTel2() != null) {
            buf.append("TEL:");
            buf.append(model.getTel2());
            buf.append(";");
        }

        //TEL3
        if (model.getTel3() != null) {
            buf.append("TEL:");
            buf.append(model.getTel3());
            buf.append(";");
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail1());
            buf.append(";");
        }

        //メール2
        if (model.getMail2() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail2());
            buf.append(";");
        }

        //メール3
        if (model.getMail3() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail3());
            buf.append(";");
        }

        buf.append(";");

        return buf.toString();
    }

    /**
     * <br>[機  能] DocomoQRコード用アドレス文字列を返す(ルール２)
     * <br>[解  説] ルール２ メールアドレス3を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return DocomoQRコード用アドレス文字列
     */
    public static String toDocomoAddressStringRule2(QrAddressModel model) {

        StringBuilder buf = new StringBuilder();
        //アドレス帳データ識別子
        buf.append("MECARD:");
        //氏名
        if (model.getNameSei() != null) {
            buf.append("N:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(",");
                buf.append(model.getNameMei());
            }
            buf.append(";");
        } else if (model.getNameMei() != null)  {
            buf.append("N:");
            buf.append(model.getNameMei());
            buf.append(";");
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            if (model.getNameMeiKn() != null) {
                buf.append(",");
                buf.append(model.getNameMeiKn().substring(0, 1));
            }
            buf.append(";");
        } else if (model.getNameMeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(";");
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL:");
            buf.append(model.getTel1());
            buf.append(";");
        }
        //TEL2
        if (model.getTel2() != null) {
            buf.append("TEL:");
            buf.append(model.getTel2());
            buf.append(";");
        }

        //TEL3
        if (model.getTel3() != null) {
            buf.append("TEL:");
            buf.append(model.getTel3());
            buf.append(";");
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail1());
            buf.append(";");
        }

        //メール2
        if (model.getMail2() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail2());
            buf.append(";");
        }

        buf.append(";");

        return buf.toString();
    }

    /**
     * <br>[機  能] DocomoQRコード用アドレス文字列を返す(ルール３)
     * <br>[解  説] ルール３ メールアドレス2を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return DocomoQRコード用アドレス文字列
     */
    public static String toDocomoAddressStringRule3(QrAddressModel model) {

        StringBuilder buf = new StringBuilder();
        //アドレス帳データ識別子
        buf.append("MECARD:");
        //氏名
        if (model.getNameSei() != null) {
            buf.append("N:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(",");
                buf.append(model.getNameMei());
            }
            buf.append(";");
        } else if (model.getNameMei() != null)  {
            buf.append("N:");
            buf.append(model.getNameMei());
            buf.append(";");
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            if (model.getNameMeiKn() != null) {
                buf.append(",");
                buf.append(model.getNameMeiKn().substring(0, 1));
            }
            buf.append(";");
        } else if (model.getNameMeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(";");
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL:");
            buf.append(model.getTel1());
            buf.append(";");
        }
        //TEL2
        if (model.getTel2() != null) {
            buf.append("TEL:");
            buf.append(model.getTel2());
            buf.append(";");
        }

        //TEL3
        if (model.getTel3() != null) {
            buf.append("TEL:");
            buf.append(model.getTel3());
            buf.append(";");
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail1());
            buf.append(";");
        }

        buf.append(";");

        return buf.toString();
    }

    /**
     * <br>[機  能] DocomoQRコード用アドレス文字列を返す(ルール４)
     * <br>[解  説] ルール４ 電話番号3を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return DocomoQRコード用アドレス文字列
     */
    public static String toDocomoAddressStringRule4(QrAddressModel model) {

        StringBuilder buf = new StringBuilder();
        //アドレス帳データ識別子
        buf.append("MECARD:");
        //氏名
        if (model.getNameSei() != null) {
            buf.append("N:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(",");
                buf.append(model.getNameMei());
            }
            buf.append(";");
        } else if (model.getNameMei() != null)  {
            buf.append("N:");
            buf.append(model.getNameMei());
            buf.append(";");
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            if (model.getNameMeiKn() != null) {
                buf.append(",");
                buf.append(model.getNameMeiKn().substring(0, 1));
            }
            buf.append(";");
        } else if (model.getNameMeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(";");
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL:");
            buf.append(model.getTel1());
            buf.append(";");
        }
        //TEL2
        if (model.getTel2() != null) {
            buf.append("TEL:");
            buf.append(model.getTel2());
            buf.append(";");
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail1());
            buf.append(";");
        }

        buf.append(";");

        return buf.toString();
    }

    /**
     * <br>[機  能] DocomoQRコード用アドレス文字列を返す(ルール５)
     * <br>[解  説] ルール５ 電話番号2を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return DocomoQRコード用アドレス文字列
     */
    public static String toDocomoAddressStringRule5(QrAddressModel model) {

        StringBuilder buf = new StringBuilder();
        //アドレス帳データ識別子
        buf.append("MECARD:");
        //氏名
        if (model.getNameSei() != null) {
            buf.append("N:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(",");
                buf.append(model.getNameMei());
            }
            buf.append(";");
        } else if (model.getNameMei() != null)  {
            buf.append("N:");
            buf.append(model.getNameMei());
            buf.append(";");
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            if (model.getNameMeiKn() != null) {
                buf.append(",");
                buf.append(model.getNameMeiKn().substring(0, 1));
            }
            buf.append(";");
        } else if (model.getNameMeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(";");
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL:");
            buf.append(model.getTel1());
            buf.append(";");
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail1());
            buf.append(";");
        }

        buf.append(";");

        return buf.toString();
    }

    /**
     * <br>[機  能] DocomoQRコード用アドレス文字列を返す(ルール６)
     * <br>[解  説] ルール６ 電話番号2を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return DocomoQRコード用アドレス文字列
     */
    public static String toDocomoAddressStringRule6(QrAddressModel model) {

        StringBuilder buf = new StringBuilder();
        //アドレス帳データ識別子
        buf.append("MECARD:");
        //氏名
        if (model.getNameSei() != null) {
            buf.append("N:");
            buf.append(model.getNameSei());
            buf.append(";");
        } else if (model.getNameMei() != null)  {
            buf.append("N:");
            buf.append(model.getNameMei());
            buf.append(";");
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            buf.append(";");
        } else if (model.getNameMeiKn() != null) {
            buf.append("SOUND:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(";");
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL:");
            buf.append(model.getTel1());
            buf.append(";");
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("EMAIL:");
            buf.append(model.getMail1());
            buf.append(";");
        }

        buf.append(";");

        return buf.toString();
    }

    /**
     * <br>[機  能] AuQRコード用アドレス文字列を返す
     * <br>[解  説]
     * <br>[備  考]
     * @param model アドレスモデル
     * @return Au QRコード用アドレス文字列
     */
    public static String toAuAddressString(QrAddressModel model) {
        //区切り文字
        String sep = "\r\n";
        StringBuilder buf = new StringBuilder();


        //アドレス帳識別子
        buf.append("MEMORY:");
        buf.append(sep);

        //氏名
        if (model.getNameSei() != null) {
            buf.append("NAME1:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(model.getNameMei());
            }
            buf.append(sep);
        } else if (model.getNameMei() != null)  {
            buf.append("NAME1:");
            buf.append(model.getNameMei());
            buf.append(sep);
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameSeiKn());
            if (model.getNameMeiKn() != null) {
                buf.append(model.getNameMeiKn());
            }
            buf.append(sep);
        } else if (model.getNameMeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameMeiKn());
            buf.append(sep);
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL1:");
            buf.append(model.getTel1());
            buf.append(sep);
        }
        //TEL2
        if (model.getTel2() != null) {
            buf.append("TEL2:");
            buf.append(model.getTel2());
            buf.append(sep);
        }

        //TEL3
        if (model.getTel3() != null) {
            buf.append("TEL3:");
            buf.append(model.getTel3());
            buf.append(sep);
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("MAIL1:");
            buf.append(model.getMail1());
            buf.append(sep);
        }

        //メール2
        if (model.getMail2() != null) {
            buf.append("MAIL2:");
            buf.append(model.getMail2());
            buf.append(sep);
        }

        //メール3
        if (model.getMail3() != null) {
            buf.append("MAIL3:");
            buf.append(model.getMail3());
            buf.append(sep);
        }

        return buf.toString();
    }

    /**
     * <br>[機  能] AuQRコード用アドレス文字列を返す(ルール１)
     * <br>[解  説] ルール１ カナを1文字にする
     * <br>[備  考]
     * @param model アドレスモデル
     * @return Au QRコード用アドレス文字列
     */
    public static String toAuAddressStringRule1(QrAddressModel model) {
        //区切り文字
        String sep = "\r\n";
        StringBuilder buf = new StringBuilder();


        //アドレス帳識別子
        buf.append("MEMORY:");
        buf.append(sep);

        //氏名
        if (model.getNameSei() != null) {
            buf.append("NAME1:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(model.getNameMei());
            }
            buf.append(sep);
        } else if (model.getNameMei() != null)  {
            buf.append("NAME1:");
            buf.append(model.getNameMei());
            buf.append(sep);
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            if (model.getNameMeiKn() != null) {
                buf.append(model.getNameMeiKn().substring(0, 1));
            }
            buf.append(sep);
        } else if (model.getNameMeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(sep);
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL1:");
            buf.append(model.getTel1());
            buf.append(sep);
        }
        //TEL2
        if (model.getTel2() != null) {
            buf.append("TEL2:");
            buf.append(model.getTel2());
            buf.append(sep);
        }

        //TEL3
        if (model.getTel3() != null) {
            buf.append("TEL3:");
            buf.append(model.getTel3());
            buf.append(sep);
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("MAIL1:");
            buf.append(model.getMail1());
            buf.append(sep);
        }

        //メール2
        if (model.getMail2() != null) {
            buf.append("MAIL2:");
            buf.append(model.getMail2());
            buf.append(sep);
        }

        //メール3
        if (model.getMail3() != null) {
            buf.append("MAIL3:");
            buf.append(model.getMail3());
            buf.append(sep);
        }

        return buf.toString();
    }

    /**
     * <br>[機  能] AuQRコード用アドレス文字列を返す(ルール２)
     * <br>[解  説] ルール２ メールアドレス3を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return Au QRコード用アドレス文字列
     */
    public static String toAuAddressStringRule2(QrAddressModel model) {
        //区切り文字
        String sep = "\r\n";
        StringBuilder buf = new StringBuilder();


        //アドレス帳識別子
        buf.append("MEMORY:");
        buf.append(sep);

        //氏名
        if (model.getNameSei() != null) {
            buf.append("NAME1:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(model.getNameMei());
            }
            buf.append(sep);
        } else if (model.getNameMei() != null)  {
            buf.append("NAME1:");
            buf.append(model.getNameMei());
            buf.append(sep);
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            if (model.getNameMeiKn() != null) {
                buf.append(model.getNameMeiKn().substring(0, 1));
            }
            buf.append(sep);
        } else if (model.getNameMeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(sep);
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL1:");
            buf.append(model.getTel1());
            buf.append(sep);
        }
        //TEL2
        if (model.getTel2() != null) {
            buf.append("TEL2:");
            buf.append(model.getTel2());
            buf.append(sep);
        }

        //TEL3
        if (model.getTel3() != null) {
            buf.append("TEL3:");
            buf.append(model.getTel3());
            buf.append(sep);
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("MAIL1:");
            buf.append(model.getMail1());
            buf.append(sep);
        }

        //メール2
        if (model.getMail2() != null) {
            buf.append("MAIL2:");
            buf.append(model.getMail2());
            buf.append(sep);
        }

        return buf.toString();
    }


    /**
     * <br>[機  能] AuQRコード用アドレス文字列を返す(ルール３)
     * <br>[解  説] ルール３ メールアドレス2を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return Au QRコード用アドレス文字列
     */
    public static String toAuAddressStringRule3(QrAddressModel model) {
        //区切り文字
        String sep = "\r\n";
        StringBuilder buf = new StringBuilder();


        //アドレス帳識別子
        buf.append("MEMORY:");
        buf.append(sep);

        //氏名
        if (model.getNameSei() != null) {
            buf.append("NAME1:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(model.getNameMei());
            }
            buf.append(sep);
        } else if (model.getNameMei() != null)  {
            buf.append("NAME1:");
            buf.append(model.getNameMei());
            buf.append(sep);
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            if (model.getNameMeiKn() != null) {
                buf.append(model.getNameMeiKn().substring(0, 1));
            }
            buf.append(sep);
        } else if (model.getNameMeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(sep);
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL1:");
            buf.append(model.getTel1());
            buf.append(sep);
        }
        //TEL2
        if (model.getTel2() != null) {
            buf.append("TEL2:");
            buf.append(model.getTel2());
            buf.append(sep);
        }

        //TEL3
        if (model.getTel3() != null) {
            buf.append("TEL3:");
            buf.append(model.getTel3());
            buf.append(sep);
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("MAIL1:");
            buf.append(model.getMail1());
            buf.append(sep);
        }

        return buf.toString();
    }

    /**
     * <br>[機  能] AuQRコード用アドレス文字列を返す(ルール４)
     * <br>[解  説] ルール４ 電話番号3を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return Au QRコード用アドレス文字列
     */
    public static String toAuAddressStringRule4(QrAddressModel model) {
        //区切り文字
        String sep = "\r\n";
        StringBuilder buf = new StringBuilder();


        //アドレス帳識別子
        buf.append("MEMORY:");
        buf.append(sep);

        //氏名
        if (model.getNameSei() != null) {
            buf.append("NAME1:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(model.getNameMei());
            }
            buf.append(sep);
        } else if (model.getNameMei() != null)  {
            buf.append("NAME1:");
            buf.append(model.getNameMei());
            buf.append(sep);
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            if (model.getNameMeiKn() != null) {
                buf.append(model.getNameMeiKn().substring(0, 1));
            }
            buf.append(sep);
        } else if (model.getNameMeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(sep);
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL1:");
            buf.append(model.getTel1());
            buf.append(sep);
        }
        //TEL2
        if (model.getTel2() != null) {
            buf.append("TEL2:");
            buf.append(model.getTel2());
            buf.append(sep);
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("MAIL1:");
            buf.append(model.getMail1());
            buf.append(sep);
        }

        return buf.toString();
    }

    /**
     * <br>[機  能] AuQRコード用アドレス文字列を返す(ルール５)
     * <br>[解  説] ルール５ 電話番号2を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return Au QRコード用アドレス文字列
     */
    public static String toAuAddressStringRule5(QrAddressModel model) {
        //区切り文字
        String sep = "\r\n";
        StringBuilder buf = new StringBuilder();


        //アドレス帳識別子
        buf.append("MEMORY:");
        buf.append(sep);

        //氏名
        if (model.getNameSei() != null) {
            buf.append("NAME1:");
            buf.append(model.getNameSei());
            if (model.getNameMei() != null) {
                buf.append(model.getNameMei());
            }
            buf.append(sep);
        } else if (model.getNameMei() != null)  {
            buf.append("NAME1:");
            buf.append(model.getNameMei());
            buf.append(sep);
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            if (model.getNameMeiKn() != null) {
                buf.append(model.getNameMeiKn().substring(0, 1));
            }
            buf.append(sep);
        } else if (model.getNameMeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(sep);
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL1:");
            buf.append(model.getTel1());
            buf.append(sep);
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("MAIL1:");
            buf.append(model.getMail1());
            buf.append(sep);
        }

        return buf.toString();
    }

    /**
     * <br>[機  能] AuQRコード用アドレス文字列を返す(ルール６)
     * <br>[解  説] ルール６ 名を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return Au QRコード用アドレス文字列
     */
    public static String toAuAddressStringRule6(QrAddressModel model) {
        //区切り文字
        String sep = "\r\n";
        StringBuilder buf = new StringBuilder();


        //アドレス帳識別子
        buf.append("MEMORY:");
        buf.append(sep);

        //氏名
        if (model.getNameSei() != null) {
            buf.append("NAME1:");
            buf.append(model.getNameSei());
            buf.append(sep);
        } else if (model.getNameMei() != null)  {
            buf.append("NAME1:");
            buf.append(model.getNameMei());
            buf.append(sep);
        }

        //氏名カナ
        if (model.getNameSeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameSeiKn().substring(0, 1));
            buf.append(sep);
        } else if (model.getNameMeiKn() != null) {
            buf.append("NAME2:");
            buf.append(model.getNameMeiKn().substring(0, 1));
            buf.append(sep);
        }

        //TEL1
        if (model.getTel1() != null) {
            buf.append("TEL1:");
            buf.append(model.getTel1());
            buf.append(sep);
        }

        //メール1
        if (model.getMail1() != null) {
            buf.append("MAIL1:");
            buf.append(model.getMail1());
            buf.append(sep);
        }

        return buf.toString();
    }

    /**
     * <br>[機  能] SoftBank QRコード用アドレス文字列を返す
     * <br>[解  説] (Auと同じ仕様)
     * <br>[備  考]
     * @param model アドレスモデル
     * @return SoftBank QRコード用アドレス文字列
     */
    public static String toSoftBankAddressString(QrAddressModel model) {
        return toAuAddressString(model);
    }

    /**
     * <br>[機  能] SoftBank QRコード用アドレス文字列を返す（ルール１）
     * <br>[解  説] ルール１ カナを1文字にする
     * <br>[備  考]
     * @param model アドレスモデル
     * @return SoftBank QRコード用アドレス文字列
     */
    public static String toSoftBankAddressStringRule1(QrAddressModel model) {
        return toAuAddressStringRule1(model);
    }

    /**
     * <br>[機  能] SoftBank QRコード用アドレス文字列を返す（ルール２）
     * <br>[解  説] ルール２ メールアドレス3を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return SoftBank QRコード用アドレス文字列
     */
    public static String toSoftBankAddressStringRule2(QrAddressModel model) {
        return toAuAddressStringRule2(model);
    }

    /**
     * <br>[機  能] SoftBank QRコード用アドレス文字列を返す（ルール３）
     * <br>[解  説] ルール３ メールアドレス2を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return SoftBank QRコード用アドレス文字列
     */
    public static String toSoftBankAddressStringRule3(QrAddressModel model) {
        return toAuAddressStringRule3(model);
    }

    /**
     * <br>[機  能] SoftBank QRコード用アドレス文字列を返す（ルール４）
     * <br>[解  説] ルール４ 電話番号3を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return SoftBank QRコード用アドレス文字列
     */
    public static String toSoftBankAddressStringRule4(QrAddressModel model) {
        return toAuAddressStringRule4(model);
    }

    /**
     * <br>[機  能] SoftBank QRコード用アドレス文字列を返す（ルール５）
     * <br>[解  説] ルール５ 電話番号2を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return SoftBank QRコード用アドレス文字列
     */
    public static String toSoftBankAddressStringRule5(QrAddressModel model) {
        return toAuAddressStringRule5(model);
    }

    /**
     * <br>[機  能] SoftBank QRコード用アドレス文字列を返す（ルール６）
     * <br>[解  説] ルール６ 名を削る
     * <br>[備  考]
     * @param model アドレスモデル
     * @return SoftBank QRコード用アドレス文字列
     */
    public static String toSoftBankAddressStringRule6(QrAddressModel model) {
        return toAuAddressStringRule6(model);
    }


    /**
     * <br>[機  能] 訂正レベルMの場合にバージョン1から10の間で最小のバージョンを返す
     * <br>[解  説] バージョン10で収まらない場合-1を返します。
     * <br>[備  考]
     * @param str 対象文字列
     * @return 最適version
     */
    public static int getBestVersionM10(String str) {
        int version = -1;
        byte[] b = str.getBytes();
        int size = b.length;

        if (size <= 14) {
            version = 1;
        } else if (size <= 26) {
            version = 2;
        } else if (size <= 42) {
            version = 3;
        } else if (size <= 62) {
            version = 4;
        } else if (size <= 84) {
            version = 5;
        } else if (size <= 106) {
            version = 6;
        } else if (size <= 122) {
            version = 7;
        } else if (size <= 152) {
            version = 8;
        } else if (size <= 180) {
            version = 9;
        } else if (size <= 213) {
            version = 10;
        } else {
            version = -1;
        }
        return version;
    }

    /**
     * <br>[機  能] バージョン1～10内での判定で指定バージョンの最大バイト数内をオーバーしていないか判定を行う。
     * <br>[解  説] true:収まっている, false:オーバーしている
     * <br>[備  考]
     * @param version バージョン
     * @param str 文字
     * @return boolean true:収まっている, false:オーバーしている
     */
    public static boolean isVersionOkM10(int version, String str) {
        byte[] b = str.getBytes();
        int size = b.length;

        switch (version) {
        case 1:
            if (size > 14) {
                return false;
            }
            break;
        case 2:
            if (size > 26) {
                return false;
            }
            break;
        case 3:
            if (size > 42) {
                return false;
            }
            break;
        case 4:
            if (size > 62) {
                return false;
            }
            break;
        case 5:
            if (size > 84) {
                return false;
            }
            break;
        case 6:
            if (size > 106) {
                return false;
            }
            break;
        case 7:
            if (size > 122) {
                return false;
            }
            break;
        case 8:
            if (size > 152) {
                return false;
            }
            break;
        case 9:
            if (size > 180) {
                return false;
            }
            break;
        case 10:
            if (size > 213) {
                return false;
            }
            break;
        default:
            break;
        }
        return true;
    }
}
