CREATE TABLE chapter6.error
(
    erroreventid serial NOT NULL,
    errormsg text,
    event text,
    created_at time without time zone DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (erroreventid)
);

CREATE TABLE IF NOT EXISTS chapter6.creditrecord
(
    id text,
    months_balance integer,
    status text
);

-- DML for lookup table creditrecord
copy chapter6.creditrecord (id, months_balance, status)
FROM '<repo_home>/Scalable-Data-Architecture-with-Java/Chapter06/datasets/PostgreSQL/credit_record.csv' DELIMITER ',' CSV HEADER QUOTE '"' ESCAPE '''';
