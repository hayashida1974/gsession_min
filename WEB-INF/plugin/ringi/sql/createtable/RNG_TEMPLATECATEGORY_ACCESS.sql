create table RNG_TEMPLATECATEGORY_ACCESS
(
  RTC_SID integer not null,
  USR_SID integer not null,
  GRP_SID integer not null,
  RTA_KBN integer not null,
  primary key (RTC_SID, USR_SID, GRP_SID)
);