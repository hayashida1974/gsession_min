create table ADR_PERMIT_EDIT
(
  ADR_SID         integer      not null,
  ADR_PERMIT_EDIT integer      not null,
  GRP_SID         integer      not null,
  USR_SID         integer      not null,
  APE_AUID        integer      not null,
  APE_ADATE       timestamp   not null,
  APE_EUID        integer      not null,
  APE_EDATE       timestamp   not null,
  primary key (ADR_SID, GRP_SID, USR_SID)
);