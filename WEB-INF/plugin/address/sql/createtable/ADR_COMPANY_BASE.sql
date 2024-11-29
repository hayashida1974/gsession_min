create table ADR_COMPANY_BASE
(
  ABA_SID         integer  not null,
  ACO_SID	      integer  not null,
  ABA_TYPE        integer  not null,
  ABA_NAME        varchar(50)  not null,
  ABA_POSTNO1     varchar(3),
  ABA_POSTNO2     varchar(4),
  TDF_SID         integer,
  ABA_ADDR1       varchar(100),
  ABA_ADDR2       varchar(100),
  ABA_BIKO        varchar(1000),
  ABA_AUID        integer  not null,
  ABA_ADATE       timestamp  not null,
  ABA_EUID        integer  not null,
  ABA_EDATE       timestamp  not null,
  primary key(ABA_SID)
);