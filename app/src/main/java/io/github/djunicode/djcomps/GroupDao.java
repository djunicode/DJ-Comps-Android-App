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
public interface GroupDao {
    @Query("select * from groups")
    List<Group> loadAllGroups();

    @Query("select * from groups where groupid = :groupid")
    User loadGroupByGroupId(BigInteger groupid);

    @Insert(onConflict = IGNORE)
    void insertGroup(Group group);

    @Delete
    void deleteGroup(Group group);

    @Query("delete from groups where sapid like :badGroupId")
    int deleteGroupsByGroupId(BigInteger badGroupId);

    @Query("DELETE FROM groups")
    void deleteAll();
}
