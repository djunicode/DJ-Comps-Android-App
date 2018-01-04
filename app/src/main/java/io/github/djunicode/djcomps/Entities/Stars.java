package io.github.djunicode.djcomps.Entities;

/**
 * Created by Hello on 02-01-2018.
 */

import android.arch.persistence.room.Entity;

import java.math.BigInteger;

@Entity
public class Stars {
    public BigInteger star_id;
    public File file_id;
    public User sap_id;

    public Stars(User sap_id, BigInteger star_id, File file_id) {
        this.sap_id=sap_id;
        this.star_id=star_id;
        this.file_id=file_id;
    }
}
