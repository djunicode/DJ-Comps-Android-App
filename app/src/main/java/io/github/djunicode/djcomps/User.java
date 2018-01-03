package io.github.djunicode.djcomps;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.math.BigInteger;
import java.security.acl.Group;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Jai on 30-12-2017.
 */

@Entity(foreignKeys = @ForeignKey(entity = Group.class,
        parentColumns = {"group_id"},
        childColumns = {"group_id"},
        onDelete = CASCADE))


public class User {
    @PrimaryKey
    public BigInteger sapid;
    public String bio;
    public String name;
    public BigInteger group_id;

    public User(BigInteger sapid, String bio, String name, BigInteger group_id) {
        this.sapid = sapid;
        this.bio = bio;
        this.name = name;
        this.group_id = group_id;
    }

}
