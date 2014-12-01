package com.example.john.debtmania;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by mollyshrestha on 11/18/14.
 */
public class ORMDatabaseHelper extends OrmLiteSqliteOpenHelper {
    static String databaseName = "dbAmount.db";
    static int databaseVersion = 3;

    private Dao<dbAmount, Integer> dbAmountsDao = null;
    public Dao<dbAmount, Integer> getAmountDao() {
        if (dbAmountsDao == null) {
            try {
                dbAmountsDao = getDao(dbAmount.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return  dbAmountsDao;
    }



    public ORMDatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, dbAmount.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,
                          ConnectionSource connectionSource, int oldVersion, int newVersion) {
        switch (newVersion) {
            case 1:
                break;
            case 3:

                try {
                    TableUtils.createTable(connectionSource, dbAmount.class);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                break;
            default:
                break;

        }
    }

}

