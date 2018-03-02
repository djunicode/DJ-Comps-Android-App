package io.github.djunicode.djcomps.database.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "users",
        foreignKeys = {
            @ForeignKey(
                    entity = Group.class,
                    parentColumns = "group_id",
                    childColumns = "group_id",
                    onDelete = ForeignKey.CASCADE)
            }
        )
public class User implements Parcelable {

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

    @Ignore
    public User(Parcel parcel){
        this.sap_id = parcel.readLong();
        this.bio = parcel.readString();
        this.name = parcel.readString();
        this.group_id = parcel.readLong();
        this.profile_image_url = parcel.readString();
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(sap_id);
        parcel.writeString(bio);
        parcel.writeString(name);
        parcel.writeLong(group_id);
        parcel.writeString(profile_image_url);
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
