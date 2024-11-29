create table RNG_FORMDATA
(
  RNG_SID integer not null,
  RFD_SID integer  not null,
  RFD_ROWNO integer not null,
  RFD_ID varchar(100),
  RFD_VALUE text ,
  RFD_AUID integer not null,
  RFD_ADATE timestamp not null,
  RFD_EUID integer not null,
  RFD_EDATE timestamp not null
);