package io.github.djunicode.djcomps.Database.DataDao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.github.djunicode.djcomps.Database.Data.Group;

@Dao
public interface GroupDao {
    @Insert
    void insertGroup(Group group);

    @Delete
    void deleteGroup(Group group);

    @Query("delete from groups where group_id = :group_id")
    void deleteGroup(Long group_id);

    @Update
    void updateGroup(Group group);

    @Query("select * from groups where group_id = :group_id")
    Group getGroupById(Long group_id);

    @Query("select * from groups where group_id in (:group_ids)")
    List<Group> getGroupsByIds(List<Long> group_ids);

    @Query("select * from groups")
    List<Group> getAllGroups();

}
