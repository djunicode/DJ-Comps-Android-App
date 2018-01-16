package io.github.djunicode.djcomps.Database.DataDao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.github.djunicode.djcomps.Database.Data.User;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@SuppressWarnings("unused")
@Dao
public interface UserDao {
    @Query("select * from users")
    List<User> loadAllUsers();

    @Query("select * from users where sap_id = :sap_id")
    User loadUserBySapId(Long sap_id);

    @Query("select * from users where name = :name")
    List<User> findUserByName(String name);

    @Insert(onConflict = IGNORE)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("delete from users where name like :badName")
    int deleteUsersByName(String badName);

    @Query("delete from users where sap_id like :badSapId")
    int deleteUsersBySapId(Long badSapId);

    @Query("DELETE FROM users")
    void deleteAll();
}
