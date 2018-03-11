package io.github.djunicode.djcomps.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import io.github.djunicode.djcomps.database.data.File;
import io.github.djunicode.djcomps.database.data.Group;
import io.github.djunicode.djcomps.database.data.User;
import io.github.djunicode.djcomps.database.datadao.FileDao;
import io.github.djunicode.djcomps.database.datadao.GroupDao;
import io.github.djunicode.djcomps.database.datadao.UserDao;

@SuppressWarnings("unused")
@Database(entities = {User.class, Group.class, File.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDao userModel();

    public abstract GroupDao groupModel();

    public abstract FileDao fileModel();


    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "dj_comps").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
