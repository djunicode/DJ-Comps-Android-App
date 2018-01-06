package io.github.djunicode.djcomps.Database.DataDao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.github.djunicode.djcomps.Database.Data.Group;
import io.github.djunicode.djcomps.Database.Data.User;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("delete from users where sap_id = :sap_id")
    void deleteUser(Long sap_id);

    @Query("select * from users where sap_id = :sap_id")
    User getUser(Long sap_id);

    @Query("select * from users")
    List<User> getAllUsers();

    @Query("select * from groups, users where groups.group_id = users.group_id and sap_id = :sap_id")
    Group getUserGroup(Long sap_id);

}
