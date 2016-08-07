package easy2win.com.testing_2;

import android.net.wifi.ScanResult;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void map_is_working() throws Exception {
        MainActivity ma = new MainActivity();

         int size = ma.size;
         List<ScanResult> result = ma.results;
        //boolean b = ma.results.isEmpty();
       // return b;
        //System.out.println(b);
        assertNull(result);
     //  ma.time_map((ArrayList<ScanResult>) result, size);

        HashMap<String, Long> Map = ma.map;
        assertNull(Map);
    }


}