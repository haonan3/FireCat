package edu.illinois.firecat;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("edu.illinois.firecat", appContext.getPackageName());
    }

    @Test
    public void deployCats() throws Exception {
        Gson gson = new Gson();
        Cat[] cats = gson.fromJson(Cat.CATS_JSON, Cat[].class);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference catsRef = database.getReference("cats");

        for (Cat cat : cats) {
            catsRef.push().setValue(cat);
        }

        // wait for 2 seconds
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await(2, TimeUnit.SECONDS);
    }
}
