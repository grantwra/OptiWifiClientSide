package com.example.optiwificlient;

import android.net.wifi.ScanResult;

import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Vighnesh on 8/7/2016.
 */
public class unitTesttingTest {

    /*
    * Note that the ScanResult class in android is not public, hence creating a dummy ScanResult was not possible.
    *In the ScanresultReplica class, I've tried to create a replica of the ScanResult class and I'll be trying to testing all
    * the methods we've created in the connection class, note that, a connection object won't be created but I'll using the same methods
    * written in that class but slightly modifying their credentials so that they can have objects of the unitTestingTest
     * & ScanresultReplica class.
    * The core logic will remain unchanged.
    */


    public void commonTestcode(ScanresultReplica q, int expected){
    ConnectReplica cr = new ConnectReplica();
        cr.populating_list();
        int x = cr.list.size();
        //System.out.println(cr.list.size());
        cr.time_map(cr.list, cr.list.size());
        cr.list.add(q);
        int y = cr.list.size();
        cr.time_map(cr.list, y);
        int actual = cr.map.size();
        System.out.println("Expected is "+expected);
        System.out.println("Actual is "+actual);
       // System.out.println(cr.map.get(q.Bssid));
        assertTrue(expected == actual);
    }// Tests for checking time_map

   @Test
    public void time_map_test1(){
        ScanresultReplica q = new ScanresultReplica(8);
        commonTestcode(q,6);
    }// end test

    @Test
    public void time_map_test2(){
        ScanresultReplica q = new ScanresultReplica("Misty",7);
        commonTestcode(q,5);
    }// end test


    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

     @Test
    public void map_is_working() throws Exception {
        MainActivity ma = new MainActivity();

         int size = MainActivity.size;
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