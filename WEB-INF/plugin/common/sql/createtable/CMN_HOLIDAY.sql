  create
  table
    CMN_HOLIDAY (
      HOL_DATE           date            not null,
      HOL_YEAR           integer         not null,
      HOL_NAME           varchar (20),
      HOL_EX_MONTH       integer,
      HOL_EX_WEEK_MONTH  integer,
      HOL_EX_DAY_WEEK    integer,
      HOL_EX_FURIKAE     integer,
      HOL_KBN            integer,
      HOL_TCD            integer,
      HOL_ADUSER         integer         not null,
      HOL_ADDATE         timestamp       not null,
      HOL_UPUSER         integer         not null,
      HOL_UPDATE         timestamp       not null,
      primary key (HOL_DATE)
    ) ;