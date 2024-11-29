create table CMN_PSWD_CONF
(
      PWC_DIGIT         integer         not null,
      PWC_COE           integer         not null,
      PWC_UPPERCASE     integer         not null,
      PWC_LIMIT_DAY     integer         not null,
      PWC_UIDPSWD       integer         not null,
      PWC_OLDPSWD       integer         not null,
      PWC_AUID          integer         not null,
      PWC_ADATE         timestamp       not null,
      PWC_EUID          integer         not null,
      PWC_EDATE         timestamp       not null
) ;