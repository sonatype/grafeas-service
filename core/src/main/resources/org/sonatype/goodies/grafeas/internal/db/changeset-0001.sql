--liquibase formatted sql

--changeset jdillon:grafeas-initial-schema

--SEE: https://github.com/grafeas/grafeas/blob/master/samples/server/go-server/api/server/storage/queries.go

CREATE TABLE projects (
  id IDENTITY PRIMARY KEY,
  name VARCHAR NOT NULL UNIQUE
);

--FIXME: notes, occurrences and operations probably should have a more direct relationship to project?

CREATE TABLE notes (
  id IDENTITY PRIMARY KEY,
  project_name VARCHAR NOT NULL,
  note_name VARCHAR NOT NULL,
  data CLOB,
  UNIQUE (project_name, note_name)
);

CREATE TABLE occurrences (
  id IDENTITY PRIMARY KEY,
  project_name VARCHAR NOT NULL,
  occurrence_name VARCHAR NOT NULL,
  note_id BIGINT NOT NULL,
  data CLOB,
  UNIQUE (project_name, occurrence_name),
  FOREIGN KEY (note_id) REFERENCES notes(id)
);

CREATE TABLE operations (
  id IDENTITY PRIMARY KEY,
  project_name VARCHAR NOT NULL,
  operation_name VARCHAR NOT NULL,
  data CLOB,
  UNIQUE (project_name, operation_name)
);

--rollback DROP TABLE IF EXISTS operations
--rollback DROP TABLE IF EXISTS occurrences
--rollback DROP TABLE IF EXISTS notes
--rollback DROP TABLE IF EXISTS projects
