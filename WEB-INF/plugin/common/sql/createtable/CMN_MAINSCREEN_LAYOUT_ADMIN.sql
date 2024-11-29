create table CMN_MAINSCREEN_LAYOUT_ADMIN
(
        MLC_ADM_LAYOUT_KBN smallint        not null,
        MLC_DEFAULT_KBN    smallint        not null,
        MLC_AUID           integer         not null,
        MLC_ADATE          timestamp       not null,
        MLC_EUID           integer         not null,
        MLC_EDATE          timestamp       not null
) ;