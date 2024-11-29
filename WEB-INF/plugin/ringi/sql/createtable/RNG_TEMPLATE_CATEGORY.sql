CREATE TABLE RNG_TEMPLATE_CATEGORY
(
    RTC_SID                     INTEGER NOT NULL,
    RTC_TYPE                    INTEGER NOT NULL,
    USR_SID                     INTEGER,
    RTC_SORT                    INTEGER NOT NULL,
    RTC_NAME                    VARCHAR(20) NOT NULL,
    RTC_AUID                    INTEGER NOT NULL,
    RTC_ADATE                   TIMESTAMP NOT NULL,
    RTC_EUID                    INTEGER NOT NULL,
    RTC_EDATE                   TIMESTAMP NOT NULL,
    RTC_USE_LIMIT               INTEGER NOT NULL,
    RTC_LIMIT_TYPE              INTEGER NOT NULL,
    PRIMARY KEY (RTC_SID)
);
