package io.github.djunicode.djcomps.Database.Data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@SuppressWarnings("WeakerAccess")
@Entity(tableName = "users",
        foreignKeys = {
            @ForeignKey(
                    entity = Group.class,
                    parentColumns = "group_id",
                    childColumns = "group_id",
                    onDelete = ForeignKey.CASCADE)
            }
        )
public class User {

    @PrimaryKey
    public Long sap_id;

    public String bio;

    public String name;

    public Long group_id;

    public User(Long sap_id, String bio, String name, Long group_id) {
        this.sap_id = sap_id;
        this.bio = bio;
        this.name = name;
        this.group_id = group_id;
    }
}
