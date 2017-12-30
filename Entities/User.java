package io.github.djunicode.djcomps.Entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.math.BigInteger;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Jai on 30-12-2017.
 */

@Entity(foreignKeys = @ForeignKey(entity = Group.class,
        parentColumns = {"group_id", "student", "year", "division"},
        childColumns = {"group_id", "student", "year", "division"},
        onDelete = CASCADE))


public class User {
    @PrimaryKey public BigInteger sapid;
    public char password;
    public char bio;
    public char name;
    public float diskUtilization;
    public BigInteger group_id;
    public boolean student;
    public int year;
    public char division;

    public User(BigInteger sapid, char password, char bio, char name, float diskUtilization, BigInteger group_id, boolean student, int year, char division) {
        this.sapid = sapid;
        this.password=password;
        this.bio=bio;
        this.name=name;
        this.diskUtilization=diskUtilization;
        this.group_id=group_id;
        this.student=student;
        this.year=year;
        this.division=division;
    }

}
