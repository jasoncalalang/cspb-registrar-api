/*═══════════════════════════════════════════════════════════════════════════
  SCHEMA  : school    — MVP: Student Data Entry
  AUTHOR  : DBA Team
  NOTES   : Run with psql -f V1__core_mvp.sql  (PostgreSQL ≥ 14)
═══════════════════════════════════════════════════════════════════════════*/

-------------------------------------------------------------------------------
-- 0.  Bootstrap – schema & extensions
-------------------------------------------------------------------------------
CREATE SCHEMA IF NOT EXISTS school;
SET search_path TO school, public;

-- Case-insensitive text & trigram search
CREATE EXTENSION IF NOT EXISTS citext;
CREATE EXTENSION IF NOT EXISTS pg_trgm;

-------------------------------------------------------------------------------
-- 1.  Simple look-ups
-------------------------------------------------------------------------------
CREATE TABLE requirement_types (
    id   SMALLSERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE
);

INSERT INTO requirement_types (name) VALUES
  ('Form 137'),
  ('Report Card'),
  ('PSA'),
  ('Good Moral'),
  ('2x2 Picture'),
  ('Baptismal');

-------------------------------------------------------------------------------
-- 2.  Philippine geographic reference (PSGC codes)
--     Load data later via ETL or COPY FROM PROGRAM.
-------------------------------------------------------------------------------
CREATE TABLE provinces (
    province_code TEXT PRIMARY KEY,
    name          TEXT NOT NULL
);

CREATE TABLE cities (
    city_code     TEXT PRIMARY KEY,
    province_code TEXT NOT NULL REFERENCES provinces
                  ON UPDATE CASCADE ON DELETE RESTRICT,
    name          TEXT NOT NULL,
    UNIQUE (province_code, name)
);

CREATE TABLE barangays (
    bgy_code      TEXT PRIMARY KEY,
    city_code     TEXT NOT NULL REFERENCES cities
                  ON UPDATE CASCADE ON DELETE RESTRICT,
    name          TEXT NOT NULL,
    UNIQUE (city_code, name)
);

-------------------------------------------------------------------------------
-- 3.  Core STUDENT table
-------------------------------------------------------------------------------
CREATE TABLE students (
    id               BIGSERIAL PRIMARY KEY,
    lrn              TEXT UNIQUE
                       CHECK (lrn ~ '^[0-9]{12}$'),      -- 12-digit DepEd LRN
    last_name        CITEXT NOT NULL,
    first_name       CITEXT NOT NULL,
    middle_name      CITEXT,
    extension_name   CITEXT,
    birth_date       DATE NOT NULL,
    birth_place      TEXT,
    gender           TEXT CHECK (gender IN ('M','F','X','Prefer_not_to_say')),
    nationality      TEXT NOT NULL DEFAULT 'Filipino',
    religion         TEXT,
    num_siblings     SMALLINT CHECK (num_siblings >= 0),
    sibling_names    TEXT,                               -- can refactor later
    img_path         TEXT,                               -- 2×2 photo URL/path
    created_at       TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

--  Fast searches & fuzzy matches
CREATE INDEX idx_students_last_name_btree  ON students (last_name);
CREATE INDEX idx_students_last_name_trgm   ON students USING GIN (last_name gin_trgm_ops);

--  Auto-maintain updated_at
CREATE OR REPLACE FUNCTION trg_set_updated_at()
RETURNS TRIGGER LANGUAGE plpgsql AS $$
BEGIN
  NEW.updated_at := NOW();
  RETURN NEW;
END $$;
CREATE TRIGGER set_updated_at
BEFORE UPDATE ON students
FOR EACH ROW EXECUTE FUNCTION trg_set_updated_at();

-------------------------------------------------------------------------------
-- 4.  One current address per student (authoritative barangay FK only)
-------------------------------------------------------------------------------
CREATE TABLE addresses (
    student_id  BIGINT PRIMARY KEY REFERENCES students
                ON UPDATE CASCADE ON DELETE CASCADE,
    house_no    TEXT,
    street_subd TEXT,
    bgy_code    TEXT NOT NULL REFERENCES barangays
                ON UPDATE CASCADE ON DELETE RESTRICT,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

--  Geo look-ups: who lives in this barangay?
CREATE INDEX idx_addresses_bgy ON addresses (bgy_code);

-------------------------------------------------------------------------------
-- 5.  Admission requirements fulfilled (M:N bridge)
-------------------------------------------------------------------------------
CREATE TABLE requirements (
    student_id        BIGINT     NOT NULL REFERENCES students
                      ON UPDATE CASCADE ON DELETE CASCADE,
    requirement_type  SMALLINT   NOT NULL REFERENCES requirement_types
                      ON UPDATE CASCADE ON DELETE RESTRICT,
    submitted         BOOLEAN    NOT NULL DEFAULT FALSE,
    submitted_date    DATE,
    PRIMARY KEY (student_id, requirement_type),
    CHECK (submitted OR submitted_date IS NULL)   -- no date without TRUE flag
);

--  Quick “who still owes X?” list
CREATE INDEX idx_requirements_missing
  ON requirements (requirement_type)
  WHERE submitted = FALSE;
