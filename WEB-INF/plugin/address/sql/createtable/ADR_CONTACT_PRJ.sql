create table ADR_CONTACT_PRJ
(
  ADC_SID         integer  not null,
  PRJ_SID         integer  not null,
  ADC_AUID        integer  not null,
  ADC_ADATE       timestamp  not null,
  ADC_EUID        integer  not null,
  ADC_EDATE       timestamp  not null,
  primary key(ADC_SID, PRJ_SID)
);