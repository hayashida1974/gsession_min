create table ADR_UCONF
(
  USR_SID         integer      not null,
  AUC_ADRCOUNT    integer      not null,
  AUC_COMCOUNT    integer      not null,
  AUC_AUID        integer      not null,
  AUC_ADATE       timestamp   not null,
  AUC_EUID        integer      not null,
  AUC_EDATE       timestamp   not null,
  AUC_PERMIT_VIEW integer     not null,
  AUC_PERMIT_EDIT integer     not null,
  primary key (USR_SID)
);