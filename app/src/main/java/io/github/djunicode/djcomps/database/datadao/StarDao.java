package io.github.djunicode.djcomps.database.datadao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.github.djunicode.djcomps.database.data.File;
import io.github.djunicode.djcomps.database.data.Star;

@SuppressWarnings("unused")
@Dao
public interface StarDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addStar(Star star);

    @Delete
    void deleteStar(Star star);

    @Query("delete from stars where star_id = :star_id")
    void deleteStar(Long star_id);

    @Query("delete from stars where file_id = :file_id")
    void deleteStarForFile(Long file_id);

    @Query("select count(*) from stars where file_id = :file_id")
    boolean isFileStarred(Long file_id);

    @Query("select * from files, stars where files.file_id = stars.file_id and star_id = :star_id")
    File getFileFromStar(Long star_id);

    @Query("select * from stars, files where files.file_id = stars.file_id and stars.file_id = :file_id")
    Star getStarFromFile(Long file_id);

}
