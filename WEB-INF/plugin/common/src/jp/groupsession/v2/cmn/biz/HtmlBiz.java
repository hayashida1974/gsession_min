package jp.groupsession.v2.cmn.biz;

/**
 * <br>[機  能] HTML操作に関する機能を提供するクラス
 * <br>[解  説]
 * <br>[備  考]
 *
 * @author JTS
 */
public class HtmlBiz {

    /**
     * <br>[機  能] HTML Entityを取得する
     * <br>[解  説] HTML解析時に指定するEntityを返す
     * <br>[備  考]
     * @return HTML Entity
     */
    public String getHtmlEntity() {

        StringBuilder sb = new StringBuilder();

        //ISO 8859-1 characters
        sb.append("<!ENTITY nbsp   \"&#160;\">");
        sb.append("<!ENTITY iexcl  \"&#161;\">");
        sb.append("<!ENTITY cent   \"&#162;\">");
        sb.append("<!ENTITY pound  \"&#163;\">");
        sb.append("<!ENTITY curren \"&#164;\">");
        sb.append("<!ENTITY yen    \"&#165;\">");
        sb.append("<!ENTITY brvbar \"&#166;\">");
        sb.append("<!ENTITY sect   \"&#167;\">");
        sb.append("<!ENTITY uml    \"&#168;\">");
        sb.append("<!ENTITY copy   \"&#169;\">");
        sb.append("<!ENTITY ordf   \"&#170;\">");
        sb.append("<!ENTITY laquo  \"&#171;\">");
        sb.append("<!ENTITY not    \"&#172;\">");
        sb.append("<!ENTITY shy    \"&#173;\">");
        sb.append("<!ENTITY reg    \"&#174;\">");
        sb.append("<!ENTITY macr   \"&#175;\">");
        sb.append("<!ENTITY deg    \"&#176;\">");
        sb.append("<!ENTITY plusmn \"&#177;\">");
        sb.append("<!ENTITY sup2   \"&#178;\">");
        sb.append("<!ENTITY sup3   \"&#179;\">");
        sb.append("<!ENTITY acute  \"&#180;\">");
        sb.append("<!ENTITY micro  \"&#181;\">");
        sb.append("<!ENTITY para   \"&#182;\">");
        sb.append("<!ENTITY middot \"&#183;\">");
        sb.append("<!ENTITY cedil  \"&#184;\">");
        sb.append("<!ENTITY sup1   \"&#185;\">");
        sb.append("<!ENTITY ordm   \"&#186;\">");
        sb.append("<!ENTITY raquo  \"&#187;\">");
        sb.append("<!ENTITY frac14 \"&#188;\">");
        sb.append("<!ENTITY frac12 \"&#189;\">");
        sb.append("<!ENTITY frac34 \"&#190;\">");
        sb.append("<!ENTITY iquest \"&#191;\">");
        sb.append("<!ENTITY Agrave \"&#192;\">");
        sb.append("<!ENTITY Aacute \"&#193;\">");
        sb.append("<!ENTITY Acirc  \"&#194;\">");
        sb.append("<!ENTITY Atilde \"&#195;\">");
        sb.append("<!ENTITY Auml   \"&#196;\">");
        sb.append("<!ENTITY Aring  \"&#197;\">");
        sb.append("<!ENTITY AElig  \"&#198;\">");
        sb.append("<!ENTITY Ccedil \"&#199;\">");
        sb.append("<!ENTITY Egrave \"&#200;\">");
        sb.append("<!ENTITY Eacute \"&#201;\">");
        sb.append("<!ENTITY Ecirc  \"&#202;\">");
        sb.append("<!ENTITY Euml   \"&#203;\">");
        sb.append("<!ENTITY Igrave \"&#204;\">");
        sb.append("<!ENTITY Iacute \"&#205;\">");
        sb.append("<!ENTITY Icirc  \"&#206;\">");
        sb.append("<!ENTITY Iuml   \"&#207;\">");
        sb.append("<!ENTITY ETH    \"&#208;\">");
        sb.append("<!ENTITY Ntilde \"&#209;\">");
        sb.append("<!ENTITY Ograve \"&#210;\">");
        sb.append("<!ENTITY Oacute \"&#211;\">");
        sb.append("<!ENTITY Ocirc  \"&#212;\">");
        sb.append("<!ENTITY Otilde \"&#213;\">");
        sb.append("<!ENTITY Ouml   \"&#214;\">");
        sb.append("<!ENTITY times  \"&#215;\">");
        sb.append("<!ENTITY Oslash \"&#216;\">");
        sb.append("<!ENTITY Ugrave \"&#217;\">");
        sb.append("<!ENTITY Uacute \"&#218;\">");
        sb.append("<!ENTITY Ucirc  \"&#219;\">");
        sb.append("<!ENTITY Uuml   \"&#220;\">");
        sb.append("<!ENTITY Yacute \"&#221;\">");
        sb.append("<!ENTITY THORN  \"&#222;\">");
        sb.append("<!ENTITY szlig  \"&#223;\">");
        sb.append("<!ENTITY agrave \"&#224;\">");
        sb.append("<!ENTITY aacute \"&#225;\">");
        sb.append("<!ENTITY acirc  \"&#226;\">");
        sb.append("<!ENTITY atilde \"&#227;\">");
        sb.append("<!ENTITY auml   \"&#228;\">");
        sb.append("<!ENTITY aring  \"&#229;\">");
        sb.append("<!ENTITY aelig  \"&#230;\">");
        sb.append("<!ENTITY ccedil \"&#231;\">");
        sb.append("<!ENTITY egrave \"&#232;\">");
        sb.append("<!ENTITY eacute \"&#233;\">");
        sb.append("<!ENTITY ecirc  \"&#234;\">");
        sb.append("<!ENTITY euml   \"&#235;\">");
        sb.append("<!ENTITY igrave \"&#236;\">");
        sb.append("<!ENTITY iacute \"&#237;\">");
        sb.append("<!ENTITY icirc  \"&#238;\">");
        sb.append("<!ENTITY iuml   \"&#239;\">");
        sb.append("<!ENTITY eth    \"&#240;\">");
        sb.append("<!ENTITY ntilde \"&#241;\">");
        sb.append("<!ENTITY ograve \"&#242;\">");
        sb.append("<!ENTITY oacute \"&#243;\">");
        sb.append("<!ENTITY ocirc  \"&#244;\">");
        sb.append("<!ENTITY otilde \"&#245;\">");
        sb.append("<!ENTITY ouml   \"&#246;\">");
        sb.append("<!ENTITY divide \"&#247;\">");
        sb.append("<!ENTITY oslash \"&#248;\">");
        sb.append("<!ENTITY ugrave \"&#249;\">");
        sb.append("<!ENTITY uacute \"&#250;\">");
        sb.append("<!ENTITY ucirc  \"&#251;\">");
        sb.append("<!ENTITY uuml   \"&#252;\">");
        sb.append("<!ENTITY yacute \"&#253;\">");
        sb.append("<!ENTITY thorn  \"&#254;\">");
        sb.append("<!ENTITY yuml   \"&#255;\">");

        //symbols, mathematical symbols, and Greek letters
        sb.append("<!ENTITY fnof     \"&#402;\">");
        sb.append("<!ENTITY Alpha    \"&#913;\">");
        sb.append("<!ENTITY Beta     \"&#914;\">");
        sb.append("<!ENTITY Gamma    \"&#915;\">");
        sb.append("<!ENTITY Delta    \"&#916;\">");
        sb.append("<!ENTITY Epsilon  \"&#917;\">");
        sb.append("<!ENTITY Zeta     \"&#918;\">");
        sb.append("<!ENTITY Eta      \"&#919;\">");
        sb.append("<!ENTITY Theta    \"&#920;\">");
        sb.append("<!ENTITY Iota     \"&#921;\">");
        sb.append("<!ENTITY Kappa    \"&#922;\">");
        sb.append("<!ENTITY Lambda   \"&#923;\">");
        sb.append("<!ENTITY Mu       \"&#924;\">");
        sb.append("<!ENTITY Nu       \"&#925;\">");
        sb.append("<!ENTITY Xi       \"&#926;\">");
        sb.append("<!ENTITY Omicron  \"&#927;\">");
        sb.append("<!ENTITY Pi       \"&#928;\">");
        sb.append("<!ENTITY Rho      \"&#929;\">");
        sb.append("<!ENTITY Sigma    \"&#931;\">");
        sb.append("<!ENTITY Tau      \"&#932;\">");
        sb.append("<!ENTITY Upsilon  \"&#933;\">");
        sb.append("<!ENTITY Phi      \"&#934;\">");
        sb.append("<!ENTITY Chi      \"&#935;\">");
        sb.append("<!ENTITY Psi      \"&#936;\">");
        sb.append("<!ENTITY Omega    \"&#937;\">");
        sb.append("<!ENTITY alpha    \"&#945;\">");
        sb.append("<!ENTITY beta     \"&#946;\">");
        sb.append("<!ENTITY gamma    \"&#947;\">");
        sb.append("<!ENTITY delta    \"&#948;\">");
        sb.append("<!ENTITY epsilon  \"&#949;\">");
        sb.append("<!ENTITY zeta     \"&#950;\">");
        sb.append("<!ENTITY eta      \"&#951;\">");
        sb.append("<!ENTITY theta    \"&#952;\">");
        sb.append("<!ENTITY iota     \"&#953;\">");
        sb.append("<!ENTITY kappa    \"&#954;\">");
        sb.append("<!ENTITY lambda   \"&#955;\">");
        sb.append("<!ENTITY mu       \"&#956;\">");
        sb.append("<!ENTITY nu       \"&#957;\">");
        sb.append("<!ENTITY xi       \"&#958;\">");
        sb.append("<!ENTITY omicron  \"&#959;\">");
        sb.append("<!ENTITY pi       \"&#960;\">");
        sb.append("<!ENTITY rho      \"&#961;\">");
        sb.append("<!ENTITY sigmaf   \"&#962;\">");
        sb.append("<!ENTITY sigma    \"&#963;\">");
        sb.append("<!ENTITY tau      \"&#964;\">");
        sb.append("<!ENTITY upsilon  \"&#965;\">");
        sb.append("<!ENTITY phi      \"&#966;\">");
        sb.append("<!ENTITY chi      \"&#967;\">");
        sb.append("<!ENTITY psi      \"&#968;\">");
        sb.append("<!ENTITY omega    \"&#969;\">");
        sb.append("<!ENTITY thetasym \"&#977;\">");
        sb.append("<!ENTITY upsih    \"&#978;\">");
        sb.append("<!ENTITY piv      \"&#982;\">");

        sb.append("<!ENTITY bull     \"&#8226;\">");
        sb.append("<!ENTITY hellip   \"&#8230;\">");
        sb.append("<!ENTITY prime    \"&#8242;\">");
        sb.append("<!ENTITY Prime    \"&#8243;\">");
        sb.append("<!ENTITY oline    \"&#8254;\">");
        sb.append("<!ENTITY frasl    \"&#8260;\">");
        sb.append("<!ENTITY weierp   \"&#8472;\">");
        sb.append("<!ENTITY image    \"&#8465;\">");
        sb.append("<!ENTITY real     \"&#8476;\">");
        sb.append("<!ENTITY trade    \"&#8482;\">");
        sb.append("<!ENTITY alefsym  \"&#8501;\">");
        sb.append("<!ENTITY larr     \"&#8592;\">");
        sb.append("<!ENTITY uarr     \"&#8593;\">");
        sb.append("<!ENTITY rarr     \"&#8594;\">");
        sb.append("<!ENTITY darr     \"&#8595;\">");
        sb.append("<!ENTITY harr     \"&#8596;\">");
        sb.append("<!ENTITY crarr    \"&#8629;\">");
        sb.append("<!ENTITY lArr     \"&#8656;\">");
        sb.append("<!ENTITY uArr     \"&#8657;\">");
        sb.append("<!ENTITY rArr     \"&#8658;\">");
        sb.append("<!ENTITY dArr     \"&#8659;\">");
        sb.append("<!ENTITY hArr     \"&#8660;\">");
        sb.append("<!ENTITY forall   \"&#8704;\">");
        sb.append("<!ENTITY part     \"&#8706;\">");
        sb.append("<!ENTITY exist    \"&#8707;\">");
        sb.append("<!ENTITY empty    \"&#8709;\">");
        sb.append("<!ENTITY nabla    \"&#8711;\">");
        sb.append("<!ENTITY isin     \"&#8712;\">");
        sb.append("<!ENTITY notin    \"&#8713;\">");
        sb.append("<!ENTITY ni       \"&#8715;\">");
        sb.append("<!ENTITY prod     \"&#8719;\">");
        sb.append("<!ENTITY sum      \"&#8721;\">");
        sb.append("<!ENTITY minus    \"&#8722;\">");
        sb.append("<!ENTITY lowast   \"&#8727;\">");
        sb.append("<!ENTITY radic    \"&#8730;\">");
        sb.append("<!ENTITY prop     \"&#8733;\">");
        sb.append("<!ENTITY infin    \"&#8734;\">");
        sb.append("<!ENTITY ang      \"&#8736;\">");
        sb.append("<!ENTITY and      \"&#8743;\">");
        sb.append("<!ENTITY or       \"&#8744;\">");
        sb.append("<!ENTITY cap      \"&#8745;\">");
        sb.append("<!ENTITY cup      \"&#8746;\">");
        sb.append("<!ENTITY int      \"&#8747;\">");
        sb.append("<!ENTITY there4   \"&#8756;\">");
        sb.append("<!ENTITY sim      \"&#8764;\">");
        sb.append("<!ENTITY cong     \"&#8773;\">");
        sb.append("<!ENTITY asymp    \"&#8776;\">");
        sb.append("<!ENTITY ne       \"&#8800;\">");
        sb.append("<!ENTITY equiv    \"&#8801;\">");
        sb.append("<!ENTITY le       \"&#8804;\">");
        sb.append("<!ENTITY ge       \"&#8805;\">");
        sb.append("<!ENTITY sub      \"&#8834;\">");
        sb.append("<!ENTITY sup      \"&#8835;\">");
        sb.append("<!ENTITY nsub     \"&#8836;\">");
        sb.append("<!ENTITY sube     \"&#8838;\">");
        sb.append("<!ENTITY supe     \"&#8839;\">");
        sb.append("<!ENTITY oplus    \"&#8853;\">");
        sb.append("<!ENTITY otimes   \"&#8855;\">");
        sb.append("<!ENTITY perp     \"&#8869;\">");
        sb.append("<!ENTITY sdot     \"&#8901;\">");
        sb.append("<!ENTITY lceil    \"&#8968;\">");
        sb.append("<!ENTITY rceil    \"&#8969;\">");
        sb.append("<!ENTITY lfloor   \"&#8970;\">");
        sb.append("<!ENTITY rfloor   \"&#8971;\">");
        sb.append("<!ENTITY lang     \"&#9001;\">");
        sb.append("<!ENTITY rang     \"&#9002;\">");
        sb.append("<!ENTITY loz      \"&#9674;\">");
        sb.append("<!ENTITY spades   \"&#9824;\">");
        sb.append("<!ENTITY clubs    \"&#9827;\">");
        sb.append("<!ENTITY hearts   \"&#9829;\">");
        sb.append("<!ENTITY diams    \"&#9830;\">");

        //markup-significant and internationalization characters
        sb.append("<!ENTITY quot    \"&#34;\"  >");
        sb.append("<!ENTITY amp     \"&#38;\"  >");
        sb.append("<!ENTITY lt      \"&#60;\"  >");
        sb.append("<!ENTITY gt      \"&#62;\"  >");
        sb.append("<!ENTITY OElig   \"&#338;\" >");
        sb.append("<!ENTITY oelig   \"&#339;\" >");
        sb.append("<!ENTITY Scaron  \"&#352;\" >");
        sb.append("<!ENTITY scaron  \"&#353;\" >");
        sb.append("<!ENTITY Yuml    \"&#376;\" >");
        sb.append("<!ENTITY circ    \"&#710;\" >");
        sb.append("<!ENTITY tilde   \"&#732;\" >");
        sb.append("<!ENTITY ensp    \"&#8194;\">");
        sb.append("<!ENTITY emsp    \"&#8195;\">");
        sb.append("<!ENTITY thinsp  \"&#8201;\">");
        sb.append("<!ENTITY zwnj    \"&#8204;\">");
        sb.append("<!ENTITY zwj     \"&#8205;\">");
        sb.append("<!ENTITY lrm     \"&#8206;\">");
        sb.append("<!ENTITY rlm     \"&#8207;\">");
        sb.append("<!ENTITY ndash   \"&#8211;\">");
        sb.append("<!ENTITY mdash   \"&#8212;\">");
        sb.append("<!ENTITY lsquo   \"&#8216;\">");
        sb.append("<!ENTITY rsquo   \"&#8217;\">");
        sb.append("<!ENTITY sbquo   \"&#8218;\">");
        sb.append("<!ENTITY ldquo   \"&#8220;\">");
        sb.append("<!ENTITY rdquo   \"&#8221;\">");
        sb.append("<!ENTITY bdquo   \"&#8222;\">");
        sb.append("<!ENTITY dagger  \"&#8224;\">");
        sb.append("<!ENTITY Dagger  \"&#8225;\">");
        sb.append("<!ENTITY permil  \"&#8240;\">");
        sb.append("<!ENTITY lsaquo  \"&#8249;\">");
        sb.append("<!ENTITY rsaquo  \"&#8250;\">");
        sb.append("<!ENTITY euro    \"&#8364;\">");

        return sb.toString();
    }
}