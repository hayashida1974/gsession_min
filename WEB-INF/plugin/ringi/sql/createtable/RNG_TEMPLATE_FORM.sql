create table RNG_TEMPLATE_FORM
(
  RTP_SID     integer        not null,
  RTP_VER     integer        not null,
  RTF_SID     integer        not null,
  RTF_ID      varchar(100),
  RTF_TITLE   varchar(100),
  RTF_REQUIRE integer        not null,
  RTF_TYPE    integer        not null,
  RTF_BODY    text,
  primary key (RTP_SID, RTP_VER, RTF_SID)
) ;