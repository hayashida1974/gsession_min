create table ADR_BELONG_INDUSTRY
(
  ACO_SID     integer         not null,
  ATI_SID     integer         not null,
  ABI_AUID    integer         not null,
  ABI_ADATE   timestamp      not null,
  ABI_EUID    integer         not null,
  ABI_EDATE   timestamp      not null,
  primary key (ACO_SID, ATI_SID)
);