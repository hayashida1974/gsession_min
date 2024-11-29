create table ADR_PERSONCHARGE
(
  ADR_SID     integer         not null,
  USR_SID     integer         not null,
  APC_AUID    integer         not null,
  APC_ADATE   timestamp      not null,
  APC_EUID    integer         not null,
  APC_EDATE   timestamp      not null,
  primary key (ADR_SID, USR_SID)
);