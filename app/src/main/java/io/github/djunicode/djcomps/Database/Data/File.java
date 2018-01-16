package io.github.djunicode.djcomps.Database.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import java.util.Date;

import io.github.djunicode.djcomps.Database.DateTypeConverter;

@SuppressWarnings("WeakerAccess")
@Entity(tableName = "files",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "sap_id",
                        childColumns = "sap_id",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class File {

    @PrimaryKey
    public Long file_id;

    public Long sap_id;

    public int size;

    public int no_of_stars;

    public int no_of_downloads;

    public String type;

    public String name;

    @TypeConverters(DateTypeConverter.class)
    public Date time_added;

    public File(Long file_id, Long sap_id, int size, int no_of_downloads, int no_of_stars, String type, String name, Date time_added) {
        this.file_id=file_id;
        this.sap_id=sap_id;
        this.size=size;
        this.no_of_downloads=no_of_downloads;
        this.no_of_stars=no_of_stars;
        this.type=type;
        this.name=name;
        this.time_added=time_added;
    }


}
