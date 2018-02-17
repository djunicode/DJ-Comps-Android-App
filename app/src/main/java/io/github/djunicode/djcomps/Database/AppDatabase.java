package io.github.djunicode.djcomps.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import io.github.djunicode.djcomps.Database.Data.File;
import io.github.djunicode.djcomps.Database.Data.Group;
import io.github.djunicode.djcomps.Database.Data.Star;
import io.github.djunicode.djcomps.Database.Data.User;
import io.github.djunicode.djcomps.Database.DataDao.FileDao;
import io.github.djunicode.djcomps.Database.DataDao.GroupDao;
import io.github.djunicode.djcomps.Database.DataDao.StarDao;
import io.github.djunicode.djcomps.Database.DataDao.UserDao;

@SuppressWarnings("unused")
@Database(entities = {User.class, Group.class, File.class, Star.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDao userModel();

    public abstract GroupDao groupModel();

    public abstract FileDao fileModel();

    public abstract StarDao starModel();


    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class).build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
