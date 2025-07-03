/*═══════════════════════════════════════════════════════════════════════════
  PATCH : core MVP  ➟  tighten requirements check & add trigram index
═══════════════════════════════════════════════════════════════════════════*/
SET search_path TO school, ibm_extension, public;

-- 1 ▶ replace permissive CHECK on 'requirements'
ALTER TABLE requirements
  DROP CONSTRAINT IF EXISTS requirements_submitted_check,
  ADD  CONSTRAINT requirements_submitted_chk
       CHECK (
         (submitted = TRUE  AND submitted_date IS NOT NULL) OR
         (submitted = FALSE AND submitted_date IS NULL)
       );

-- 2 ▶ fuzzy search on given names
CREATE EXTENSION IF NOT EXISTS pg_trgm  WITH SCHEMA ibm_extension;
CREATE INDEX IF NOT EXISTS idx_students_first_name_trgm
  ON students USING GIN (first_name gin_trgm_ops);
