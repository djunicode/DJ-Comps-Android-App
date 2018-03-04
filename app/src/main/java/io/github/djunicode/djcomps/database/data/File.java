package io.github.djunicode.djcomps.database.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import io.github.djunicode.djcomps.database.DateTypeConverter;

@Entity(tableName = "files",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "sap_id",
                        childColumns = "sap_id",
                        onDelete = ForeignKey.CASCADE)
        }
)
public class File implements Parcelable{

    @PrimaryKey
    public Long file_id;

    @TypeConverters(DateTypeConverter.class)
    public Date time_added;

    public Long sap_id;

    public Long size;

    public int no_of_stars;

    public int no_of_downloads;

    public String type;

    public String name;

    public String description;

    public boolean is_star;

    public boolean is_downloaded;


    public File(Long file_id, Long sap_id, Long size, int no_of_downloads, int no_of_stars, String type, String name, Date time_added, String description, boolean is_star, boolean is_downloaded) {
        this.file_id=file_id;
        this.sap_id=sap_id;
        this.size=size;
        this.no_of_downloads=no_of_downloads;
        this.no_of_stars=no_of_stars;
        this.type=type;
        this.name=name;
        this.time_added=time_added;
        this.description = description;
        this.is_star = is_star;
        this.is_downloaded = is_downloaded;
    }


    @Ignore
    public File(Parcel parcel){
        this.file_id = parcel.readLong();
        this.time_added = DateTypeConverter.toDate(parcel.readLong());
        this.sap_id = parcel.readLong();
        this.size = parcel.readLong();
        this.no_of_stars = parcel.readInt();
        this.no_of_downloads = parcel.readInt();
        this.type = parcel.readString();
        this.name = parcel.readString();
        this.description = parcel.readString();
        this.is_star = parcel.readByte() != 0;
        this.is_downloaded = parcel.readByte() != 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(file_id);
        parcel.writeLong(DateTypeConverter.toLong(time_added));
        parcel.writeLong(sap_id);
        parcel.writeLong(size);
        parcel.writeInt(no_of_stars);
        parcel.writeInt(no_of_downloads);
        parcel.writeString(type);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeByte((byte) (is_star ? 1 : 0));
        parcel.writeByte((byte) (is_downloaded ? 1 : 0));
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public File createFromParcel(Parcel in) {
            return new File(in);
        }

        public File[] newArray(int size) {
            return new File[size];
        }
    };
}
