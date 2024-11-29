  create
  table
    CMN_TDFK (
      TDF_SID           integer            not null,
      TDF_NAME          varchar (5)        not null,
      TDF_AUID          integer         not null,
      TDF_ADATE         timestamp       not null,
      TDF_EUID          integer         not null,
      TDF_EDATE         timestamp       not null,
      primary key (TDF_SID)
    ) ;
