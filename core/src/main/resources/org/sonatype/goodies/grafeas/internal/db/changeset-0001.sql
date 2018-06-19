--liquibase formatted sql

--changeset jdillon:grafeas-initial-schema

--SEE: https://github.com/grafeas/grafeas/blob/master/samples/server/go-server/api/server/storage/queries.go

--FIXME: notes, occurrences and operations probably should have a more direct relationship to project?

CREATE SEQUENCE projects_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE projects (
  id INTEGER PRIMARY KEY,
  name VARCHAR NOT NULL UNIQUE
);

CREATE SEQUENCE notes_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE notes (
  id INTEGER PRIMARY KEY,
  project_name VARCHAR NOT NULL,
  note_name VARCHAR NOT NULL,
  data CLOB,
  UNIQUE (project_name, note_name)
);

CREATE SEQUENCE occurrences_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE occurrences (
  id INTEGER PRIMARY KEY,
  project_name VARCHAR NOT NULL,
  occurrence_name VARCHAR NOT NULL,
  note_id BIGINT NOT NULL,
  data CLOB,
  UNIQUE (project_name, occurrence_name),
  FOREIGN KEY (note_id) REFERENCES notes(id)
);

CREATE SEQUENCE operations_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE operations (
  id INTEGER PRIMARY KEY,
  project_name VARCHAR NOT NULL,
  operation_name VARCHAR NOT NULL,
  data CLOB,
  UNIQUE (project_name, operation_name)
);

--rollback DROP TABLE IF EXISTS operations
--rollback DROP SEQUENCE IF EXISTS operations_sequence

--rollback DROP TABLE IF EXISTS occurrences
--rollback DROP SEQUENCE IF EXISTS occurrences_sequence

--rollback DROP TABLE IF EXISTS notes
--rollback DROP SEQUENCE IF EXISTS notes_sequence

--rollback DROP TABLE IF EXISTS projects
--rollback DROP SEQUENCE IF EXISTS projects_sequence
