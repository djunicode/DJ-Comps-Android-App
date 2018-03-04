package io.github.djunicode.djcomps.database.datadao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.github.djunicode.djcomps.database.data.File;
import io.github.djunicode.djcomps.database.data.User;

@SuppressWarnings("unused")
@Dao
public interface FileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFile(File file);

    @Query("select * from files where file_id = :file_id")
    File getFileById(Long file_id);

    @Delete
    void  deleteFile(File file);

    @Query("delete from files where file_id = :file_id")
    void deleteFile(Long file_id);

    @Query("select * from users, files where files.sap_id = users.sap_id and file_id = :file_id")
    User getUploader(Long file_id);

    @Query("select * from files")
    List<File> getAllFiles();

    @Query("select * from files where sap_id = :sap_id")
    List<File> getFilesByUser(Long sap_id);

    @Query("select * from files where lower(name) like lower('%'||:name||'%')")
    List<File> getFilesByName(String name);

    @Query("select * from files where type = :type")
    List<File> getFilesByType(String type);

    @Query("select * from files inner join users on files.sap_id = users.sap_id " +
            "where group_id in (:group_ids)")
    List<File> getFilesByGroups(List<Long> group_ids);

}