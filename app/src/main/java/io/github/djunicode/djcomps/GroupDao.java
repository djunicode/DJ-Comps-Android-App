package io.github.djunicode.djcomps;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@SuppressWarnings("unused")
@Dao
public interface GroupDao {
    @Query("select * from groups")
    List<Group> loadAllGroups();

    @Query("select * from groups where group_id = :groupid")
    User loadGroupByGroupId(Long groupid);

    @Insert(onConflict = IGNORE)
    void insertGroup(Group group);

    @Delete
    void deleteGroup(Group group);

    @Query("delete from groups where group_id like :badGroupId")
    int deleteGroupsByGroupId(Long badGroupId);

    @Query("DELETE FROM groups")
    void deleteAll();
}