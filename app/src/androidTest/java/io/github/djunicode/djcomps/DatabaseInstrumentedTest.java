package io.github.djunicode.djcomps;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import io.github.djunicode.djcomps.database.AppDatabase;
import io.github.djunicode.djcomps.database.data.Group;
import io.github.djunicode.djcomps.database.data.User;
import io.github.djunicode.djcomps.database.datadao.GroupDao;
import io.github.djunicode.djcomps.database.datadao.UserDao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {

    @Test
    public void testGroupDatabase() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        AppDatabase database = AppDatabase.getInMemoryDatabase(appContext);

        GroupDao db = database.groupModel();

        Group seA = new Group(0L, true, 2, 'A', 15.0f);
        Group seB = new Group(1L, true, 2, 'B', 15.0f);
        Group teA = new Group(2L, true, 3, 'A', 15.0f);
        Group teB = new Group(3L, true, 3, 'B', 15.0f);

        db.insertGroup(seB);
        db.insertGroup(teA);
        db.insertGroup(teB);
        db.insertGroup(seA);
        assertEquals(4, db.getAllGroups().size());

        db.deleteGroup(teA);
        db.deleteGroup(0L);
        assertEquals(2, db.getAllGroups().size());
        assertNotNull(db.getGroupById(3L));

        List<Long> gids = new ArrayList<>(2);
        gids.add(3L); gids.add(1L);
        assertEquals(2, db.getGroupsByIds(gids).size());

        gids.remove(3L);
        assertEquals(1, db.getGroupsByIds(gids).size());

        seB.total_disk_available = 14.0f;
        db.updateGroup(seB);
        assertEquals(14.0f, db.getGroupById(seB.group_id).total_disk_available, 0);

        db.deleteGroup(seB);
        db.deleteGroup(3L);
        assertEquals(0, db.getAllGroups().size());

    }

    @Test
    public void testUserDatabase() {
        Context appContext = InstrumentationRegistry.getTargetContext();

        AppDatabase database = AppDatabase.getInMemoryDatabase(appContext);

        UserDao db = database.userModel();

        Group seA = new Group(0L, true, 2, 'A', 15.0f);
        Group seB = new Group(1L, true, 2, 'B', 15.0f);

        database.groupModel().insertGroup(seA);
        database.groupModel().insertGroup(seB);

        User u1 = new User(1L, "A student", "ABC", seA.group_id, "http://image_url");
        User u2 = new User(2L, "A seB student", "XYZ", seB.group_id, "http://image_url1");
        User u3 = new User(3L, "A seA student", "PQR", seA.group_id, "http://image_url1");

        db.insertUser(u1);
        db.insertUser(u2);
        db.insertUser(u3);
        db.insertUser(u3);
        assertEquals(3, db.getAllUsers().size());

        u2.bio = "I love unicode";
        db.updateUser(u2);
        assertEquals(u2.bio, db.getUser(u2.sap_id).bio);

        db.deleteUser(u1);
        assertEquals(2, db.getAllUsers().size());

        assertNull(db.getUser(u1.sap_id));
        assertNotNull(db.getUser(u3.sap_id));

        Group u2Grp = db.getUserGroup(u2.sap_id);
        assertEquals(seB.group_id, u2Grp.group_id);
        assertEquals(seB.division, u2Grp.division);
        assertEquals(seB.student, u2Grp.student);
        assertEquals(seB.year, u2Grp.year);
        assertEquals(seB.total_disk_available, u2Grp.total_disk_available, 0);

        db.deleteUser(u1);
        db.deleteUser(u2);
        db.deleteUser(u3);
        db.deleteUser(u3.sap_id);
        assertEquals(0, db.getAllUsers().size());

        database.groupModel().deleteGroup(seA);
        database.groupModel().deleteGroup(1L);
        assertEquals(0, database.groupModel().getAllGroups().size());

    }

}
