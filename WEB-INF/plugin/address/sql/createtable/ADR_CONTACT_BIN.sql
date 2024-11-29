create table ADR_CONTACT_BIN
(
  ADC_SID         integer  not null,
  BIN_SID         bigint  not null,
  ACB_AUID        integer  not null,
  ACB_ADATE       timestamp  not null,
  ACB_EUID        integer  not null,
  ACB_EDATE       timestamp  not null,
  primary key(ADC_SID, BIN_SID)
);