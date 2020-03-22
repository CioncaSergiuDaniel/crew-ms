-- TODO: Migration library should be used for migration
CREATE KEYSPACE IF NOT EXISTS crew
    WITH REPLICATION = {
    'class' : 'SimpleStrategy',
    'replication_factor' : 1
    };