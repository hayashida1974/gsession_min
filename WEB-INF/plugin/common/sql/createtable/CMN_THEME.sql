create table CMN_THEME
(
        CTM_SID        integer         not null,
        CTM_ID         varchar(20)     not null,
        CTM_NAME       varchar(50)     not null,
        CTM_PATH       varchar(100)    not null,
        CTM_PATH_IMG   varchar(150)    not null,
        CTM_AUID       integer         not null,
        CTM_ADATE      timestamp       not null,
        CTM_EUID       integer         not null,
        CTM_EDATE      timestamp       not null,
        primary key (CTM_SID)
) ;