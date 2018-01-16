package io.github.djunicode.djcomps.Database.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "stars",
        foreignKeys = {
            @ForeignKey(
                    entity = File.class,
                    parentColumns = "file_id",
                    childColumns = "file_id",
                    onDelete = ForeignKey.CASCADE)
        }
)
public class Star {

    @PrimaryKey
    public Long star_id;

    public Long file_id;

    public Star(Long star_id, Long file_id) {
        this.star_id=star_id;
        this.file_id=file_id;
    }
}
