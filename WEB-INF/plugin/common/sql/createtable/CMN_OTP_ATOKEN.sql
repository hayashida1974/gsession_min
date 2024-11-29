 create table CMN_OTP_ATOKEN
   (
    COA_TOKEN   varchar(256) not null,
    USR_SID     integer not null,
    COA_PASS    varchar(4),
    COA_ADDRESS varchar(256),
    COA_LIMIT_DATE  timestamp,
    COA_DATE    timestamp,
    primary key (COA_TOKEN)
   );
