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
        parentColumns = {"group_id"},
        childColumns = {"group_id"},
        onDelete = CASCADE))


public class User {
    @PrimaryKey public BigInteger sapid;
    public char bio;
    public char name;
    public BigInteger group_id;

    public User(BigInteger sapid, char bio, char name,BigInteger group_id) {
        this.sapid = sapid;
        this.bio=bio;
        this.name=name;
        this.group_id=group_id;
    }

}
