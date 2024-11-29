create table ADR_PERMIT_VIEW
(
  ADR_SID         integer      not null,
  ADR_PERMIT_VIEW integer      not null,
  GRP_SID         integer      not null,
  USR_SID         integer      not null,
  APV_AUID        integer      not null,
  APV_ADATE       timestamp   not null,
  APV_EUID        integer      not null,
  APV_EDATE       timestamp   not null,
  primary key (ADR_SID, GRP_SID, USR_SID)
);