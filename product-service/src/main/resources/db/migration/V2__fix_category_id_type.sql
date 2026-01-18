-- Fix category_id column type from INTEGER to BIGINT to match the foreign key reference
ALTER TABLE product
ALTER COLUMN category_id TYPE BIGINT;

