 create table WML_LOG_COUNT
 (
     WAC_SID        integer         not null,
     WLC_KBN        integer         not null,
     WLC_CNT_TO        integer         not null,
     WLC_CNT_CC        integer         not null,
     WLC_CNT_BCC        integer         not null,
     WLC_DATE        timestamp       not null
 ) ;