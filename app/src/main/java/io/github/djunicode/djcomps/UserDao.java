package io.github.djunicode.djcomps;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.math.BigInteger;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by aamir on 3/1/18.
 */

@Dao
public interface UserDao {
    @Query("select * from users")
    List<User> loadAllUsers();

    @Query("select * from users where sapid = :sapid")
    User loadUserBySapId(BigInteger sapid);

    @Query("select * from users where name = :name")
    List<User> findUserByName(String name);

    @Insert(onConflict = IGNORE)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("delete from users where name like :badName")
    int deleteUsersByName(String badName);

    @Query("delete from users where sapid like :badSapId")
    int deleteUsersBySapId(String badSapId);

    @Query("DELETE FROM users")
    void deleteAll();
}
