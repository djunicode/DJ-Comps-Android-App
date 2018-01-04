package io.github.djunicode.djcomps.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Timestamp;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Jai on 02-01-2018.
 */

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = {"sap_id"},
        childColumns = {"sap_id"},
        onDelete = CASCADE))


public class File {
    @PrimaryKey
    public BigInteger file_id;
    public BigInteger sap_id;
    public int size;
    public int no_of_stars;
    public int no_of_downloads;
    public char type;
    public char name;
    public Timestamp time_added;
    public Blob file_data;

    public File(BigInteger file_id, BigInteger sap_id, int size, int no_of_downloads, int no_of_stars, char type, char name, Timestamp time_added, Blob file_data) {
        this.file_id=file_id;
        this.sap_id=sap_id;
        this.size=size;
        this.no_of_downloads=no_of_downloads;
        this.no_of_stars=no_of_stars;
        this.type=type;
        this.name=name;
        this.time_added=time_added;
        this.file_data=file_data;
    }
}
