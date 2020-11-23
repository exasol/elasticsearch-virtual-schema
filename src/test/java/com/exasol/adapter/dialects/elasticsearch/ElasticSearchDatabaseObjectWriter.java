package com.exasol.adapter.dialects.elasticsearch;

import java.nio.file.Path;

import com.exasol.dbbuilder.dialects.*;

public class ElasticSearchDatabaseObjectWriter implements DatabaseObjectWriter {

    @Override
    public void write(final Table table) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drop(final Table table) {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(final Table table, final Object... values) {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(final User user) {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(final User user, final GlobalPrivilege... privileges) {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(final User user, final DatabaseObject object, final ObjectPrivilege... objectPrivileges) {
        // TODO Auto-generated method stub

    }

    @Override
    public void executeSqlFile(final Path... sqlFiles) {
        // TODO Auto-generated method stub

    }

    @Override
    public void drop(final User user) {
        // TODO Auto-generated method stub

    }

    @Override
    public void write(final Schema schema) {
        // elastic search does not support schemas
    }

    @Override
    public void drop(final Schema schema) {
        // elastic search does not support schemas
    }

}
