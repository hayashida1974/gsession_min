 create table CMN_OTP_TOKEN
   (
    COT_TOKEN   varchar(256) not null,
    USR_SID     integer not null,
    COT_PASS    varchar(4),
    COT_LIMIT_DATE  timestamp,
    COT_DATE   timestamp,
    primary key (COT_TOKEN)
   );
