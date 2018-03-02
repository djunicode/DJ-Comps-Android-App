package io.github.djunicode.djcomps.database.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

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

    public String profile_image_url;

    public User(Long sap_id, String bio, String name, Long group_id, String profile_image_url) {
        this.sap_id = sap_id;
        this.bio = bio;
        this.name = name;
        this.group_id = group_id;
        this.profile_image_url = profile_image_url;
    }
    
}
