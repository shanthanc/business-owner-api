CREATE TYPE "JSONB" AS json;
CREATE USER admin WITH PASSWORD 'pass';
GRANT ALL PRIVILEGES ON DATABASE "*" TO admin;