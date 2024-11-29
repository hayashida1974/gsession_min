create table ADR_CONTACT
(
  ADC_SID         integer  not null,
  ADC_GRP_SID     integer  not null,
  ADR_SID         integer  not null,
  ADC_TITLE       varchar(100)  not null,
  ADC_TYPE        integer  not null,
  ADC_CTTIME      timestamp,
  ADC_CTTIME_TO   timestamp,
  PRJ_SID         integer,
  ADC_BIKO        text,
  ADC_AUID        integer  not null,
  ADC_ADATE       timestamp  not null,
  ADC_EUID        integer  not null,
  ADC_EDATE       timestamp  not null,
  primary key(ADC_SID)
);