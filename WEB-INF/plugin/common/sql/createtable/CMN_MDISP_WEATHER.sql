create table CMN_MDISP_WEATHER
(
    USR_SID         integer         not null,
    MDW_AREA        integer         not null,
    MDW_SORT        integer         not null,
    MDW_AUID        integer         not null,
    MDW_ADATE       timestamp       not null,
    MDW_EUID        integer         not null,
    MDW_EDATE       timestamp       not null,
    primary key (USR_SID, MDW_AREA) 
) ;