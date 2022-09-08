-- DDL statements

CREATE TABLE IF NOT EXISTS chapter4.device_dimension
(
    deviceid serial NOT NULL,
    deviceserialno text COLLATE pg_catalog."default" NOT NULL,
    devicename text COLLATE pg_catalog."default",
    devicedesc text COLLATE pg_catalog."default",
    devicetype text COLLATE pg_catalog."default",
    mfdate date,
    saledate date,
    inwarranty boolean,
    CONSTRAINT device_dimension_pkey PRIMARY KEY (deviceid),
    CONSTRAINT device_dimension_uk UNIQUE (deviceserialno)
)

CREATE TABLE IF NOT EXISTS chapter4.event_dimension
(
    eventid serial NOT NULL,
    eventcd text COLLATE pg_catalog."default" NOT NULL,
    eventname text COLLATE pg_catalog."default",
    eventdesc text COLLATE pg_catalog."default",
    eventtype text COLLATE pg_catalog."default",
    CONSTRAINT event_dimension_pk PRIMARY KEY (eventid),
    CONSTRAINT event_dimension_uk UNIQUE (eventcd)
)

CREATE TABLE IF NOT EXISTS chapter4.hour_dimension
(
    hourid serial NOT NULL,
    hour_of_day integer NOT NULL,
    am_or_pm text COLLATE pg_catalog."default",
    CONSTRAINT hour_dimension_pkey PRIMARY KEY (hourid),
    CONSTRAINT hour_dimension_ampm_ck CHECK (am_or_pm = 'am'::text OR am_or_pm = 'pm'::text)
)

CREATE TABLE IF NOT EXISTS chapter4.month_dimension
(
    monthid integer NOT NULL,
    name text COLLATE pg_catalog."default",
    monthcd text COLLATE pg_catalog."default",
    number_of_days integer,
    CONSTRAINT month_dimension_pkey PRIMARY KEY (monthid)
)

CREATE TABLE IF NOT EXISTS chapter4.quarter_dimension
(
    quarterid integer NOT NULL,
    name text COLLATE pg_catalog."default",
    quatercd text COLLATE pg_catalog."default",
    CONSTRAINT quarter_dimension_pkey PRIMARY KEY (quarterid)
)

CREATE TABLE IF NOT EXISTS chapter4.device_event_log_fact
(
    eventlogid text COLLATE pg_catalog."default" NOT NULL,
    deviceid integer,
    eventid integer,
    hourid integer,
    monthid integer,
    quarterid integer,
    eventtimestamp timestamp without time zone,
    closurestatus boolean,
    closureduration bigint,
    CONSTRAINT device_event_log_fact_pkey PRIMARY KEY (eventlogid),
    CONSTRAINT device_eventlog_devicedim_fkeys FOREIGN KEY (deviceid)
        REFERENCES chapter4.device_dimension (deviceid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION,
    CONSTRAINT device_eventlog_eventdim_fkeys FOREIGN KEY (eventid)
        REFERENCES chapter4.event_dimension (eventid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION,
    CONSTRAINT device_eventlog_hourdim_fkeys FOREIGN KEY (hourid)
        REFERENCES chapter4.hour_dimension (hourid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION,
    CONSTRAINT device_eventlog_monthdim_fkeys FOREIGN KEY (monthid)
        REFERENCES chapter4.month_dimension (monthid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION,
    CONSTRAINT device_eventlog_quarterdim_fkeys FOREIGN KEY (quarterid)
        REFERENCES chapter4.quarter_dimension (quarterid) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE NO ACTION
)


-- DML for static dimension tables

COPY chapter4.quarter_dimension (quarterid, name, quatercd) FROM stdin;
1	First Quarter	Q1
2	Second Quarter	Q2
3	Third Quarter	Q3
4	Last Quarter	Q4
\.

COPY chapter4.month_dimension (monthid, name, monthcd, number_of_days) FROM stdin;
1	January	JAN	31
2	February	FEB	28
3	March	MAR	31
4	April	APR	30
5	May	MAY	31
6	June	JUN	30
7	July	JUL	31
8	August	AUG	31
9	September	SEPT	30
10	October	OCT	31
11	November	NOV	30
12	December	DEC	31
\.

COPY chapter4.hour_dimension (hourid, hour_of_day, am_or_pm) FROM stdin;
1	0	am
2	1	am
3	2	am
4	3	am
5	4	am
6	5	am
7	6	am
8	7	am
9	8	am
10	9	am
11	10	am
12	11	am
13	12	pm
14	13	pm
15	14	pm
16	15	pm
17	16	pm
18	17	pm
19	18	pm
20	19	pm
21	20	pm
22	21	pm
23	22	pm
24	23	pm
\.
