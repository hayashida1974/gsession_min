create table BBS_ADM_CONF
(
        BAC_ATDEL_FLG   integer    not null,
        BAC_ATDEL_Y     integer            ,
        BAC_ATDEL_M     integer            ,
        BAC_AUID        integer    not null,
        BAC_ADATE       timestamp  not null,
        BAC_EUID        integer    not null,
        BAC_EDATE       timestamp  not null,
        BAC_SML_NTF     integer    not null,
        BAC_SML_NTF_KBN integer,
        BAC_INI_POST_TYPE integer not null,
        BAC_INI_POST_TYPE_KBN integer not null
) ;