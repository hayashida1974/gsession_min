 create table CMN_OTP_CONF
   (
    COC_USE_OTP         integer not null,
    COC_OTP_USER        integer not null,
    COC_OTP_USER_TYPE   integer not null,
    COC_OTP_IPCOND      integer not null,
    COC_OTP_IP          text,
    COC_SMTP_URL        varchar(200),
    COC_SMTP_PORT       varchar(5),
    COC_SMTP_SSLUSE     integer not null,
    COC_SMTP_FROM       varchar(200),
    COC_SMTP_USER       varchar(100),
    COC_SMTP_PASS       varchar(140),
    COC_SMTP_AUTH_TYPE  integer not null,
    COT_SID             integer
   );
