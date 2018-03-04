package io.github.djunicode.djcomps.database.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "groups")
public class Group {

    @PrimaryKey
    public Long group_id;

    public boolean student;

    public int year;

    public char division;

    public float total_disk_available;

    public Group(Long group_id, boolean student, int year, char division, float total_disk_available) {
        this.group_id=group_id;
        this.student=student;
        this.year=year;
        this.division=division;
        this.total_disk_available=total_disk_available;
    }
}
