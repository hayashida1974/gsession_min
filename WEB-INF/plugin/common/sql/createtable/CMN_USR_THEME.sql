create table CMN_USR_THEME
(
        USR_SID        integer         not null,
        CTM_SID        integer         not null,
        UTM_AUID       integer         not null,
        UTM_ADATE      timestamp       not null,
        UTM_EUID       integer         not null,
        UTM_EDATE      timestamp       not null,
        primary key (USR_SID,CTM_SID)
) ;
