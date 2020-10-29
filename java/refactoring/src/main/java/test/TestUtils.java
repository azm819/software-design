package test;

public class TestUtils {
    public final static String DB_URL = "jdbc:sqlite:test.db";

    public final static String DROP_TABLE = "drop table if exists product";
    public final static String CREATE_TABLE = "create table if not exists product( \nid integer primary key autoincrement not null, \nname text not null, \nprice int not null\n) ";

    public final static String RESULT_SUCCESS = "OK";
    public final static String RESULT_EMPTY = "";
}
