  create
  table
    CMN_HOLIDAY_TEMPLATE (
      HLT_SID            integer         not null,
      HLT_DATE_MONTH     integer         not null,
      HLT_DATE_DAY       integer,
      HLT_NAME           varchar(20),
      HLT_EX_MONTH       integer,
      HLT_EX_WEEK_MONTH  integer,
      HLT_EX_DAY_WEEK    integer,
      HLT_EX_FURIKAE     integer,
      HLT_EXFLG          integer         not null,
      HLT_ADUSER         integer         not null,
      HLT_ADDATE         timestamp       not null,
      HLT_UPUSER         integer         not null,
      HLT_UPDATE         timestamp       not null,
      primary key (HLT_SID)
    ) ;