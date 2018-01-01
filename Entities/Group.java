package io.github.djunicode.djcomps.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.math.BigInteger;

/**
 * Created by Jai on 30-12-2017.
 */

@Entity
public class Group {
    @PrimaryKey public BigInteger group_id;
    public boolean student;
    public int year;
    public char division;
    public float total_disk_available;

    public Group(BigInteger group_id, boolean student, int year, char division, float total_disk_available) {
        this.group_id=group_id;
        this.student=student;
        this.year=year;
        this.division=division;
        this.total_disk_available=total_disk_available;
    }
}


