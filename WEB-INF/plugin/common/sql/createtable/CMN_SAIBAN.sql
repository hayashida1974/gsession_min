create table CMN_SAIBAN
(
        SBN_SID         varchar(10)     not null,
        SBN_SID_SUB     varchar(20)     not null,
        SBN_NUMBER      bigint           not null,
        SBN_STRING      varchar(20)     not null,
        SBN_AID         integer         not null,
        SBN_ADATE       timestamp       not null,
        SBN_EID         integer         not null,
        SBN_EDATE       timestamp       not null,
        primary key (SBN_SID,SBN_SID_SUB)
) ;