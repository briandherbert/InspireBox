package com.meetmelabs.inspirebox;

import android.content.Context;
import android.support.annotation.RawRes;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * Created by bherbert on 3/9/17.
 */

public class RandomWord {
    static final String TAG = RandomWord.class.getSimpleName();

    enum WORDFILE {
        A(R.raw.awords, 4448),
        B(R.raw.bwords, 4702),
        C(R.raw.cwords, 7628),
        D(R.raw.dwords, 4958),
        E(R.raw.ewords, 3212),
        F(R.raw.fwords, 3504),
        G(R.raw.gwords, 2629),
        H(R.raw.hwords, 2891),
        I(R.raw.iwords, 3175),
        JK(R.raw.jkwords, 1300),
        L(R.raw.lwords, 2444),
        M(R.raw.mwords, 4159),
        N(R.raw.nwords, 1631),
        O(R.raw.owords, 2008),
        PQ(R.raw.pqwords, 6643),
        RR(R.raw.rwords, 4889),
        S(R.raw.swords, 9144),
        T(R.raw.twords, 4001),
        U(R.raw.uwords, 2088),
        VW(R.raw.vwwords, 3328),
        XYZ(R.raw.xyzwords, 406);

        public final int count;
        public final int resId;

        WORDFILE(@RawRes int resId, int count) {
            this.resId = resId;
            this.count = count;
        }
    }

    static int LETTER_TOTAL = 0;

    static {
        for (WORDFILE wf : WORDFILE.values()) {
            LETTER_TOTAL += wf.count;
        }
    }

    public static String getRandomWord(Context context) {
        int idx = (new Random()).nextInt(LETTER_TOTAL);
        Log.v(TAG, "rand idx is " + idx + " total count " + LETTER_TOTAL);
        String word = "RANDOM";

        int count = 0;
        WORDFILE targetWF = null;
        for (WORDFILE wf : WORDFILE.values()) {
            targetWF = wf;

            if (count + targetWF.count > idx) {
                break;
            }

            count += wf.count;
        }

        Log.v(TAG, "Target WF is " + targetWF + " which size is " + targetWF.count + " and we're at " + count);

        try {
            InputStream istream = context.getResources().openRawResource(targetWF.resId);
            DataInputStream in = new DataInputStream(istream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine = "";

            while ((strLine = br.readLine()) != null) {
                if (idx == count) {
                    word = strLine;
                    break;
                }
                count++;
            }
            istream.close();
            br.close();

        } catch (Exception e) {
            Log.v(TAG, "Error getting random word", e);
        }

        return word;
    }

    public static final int[] WORDFILE_RES_IDS = {
            R.raw.awords,
            R.raw.bwords,
            R.raw.cwords,
            R.raw.dwords,
            R.raw.ewords,
            R.raw.fwords,
            R.raw.gwords,
            R.raw.hwords,
            R.raw.iwords,
            R.raw.jkwords,
            R.raw.lwords,
            R.raw.mwords,
            R.raw.nwords,
            R.raw.owords,
            R.raw.pqwords,
            R.raw.rwords,
            R.raw.swords,
            R.raw.twords,
            R.raw.uwords,
            R.raw.vwwords,
            R.raw.xyzwords};

    public static void analyze(Context context) {
        try {
            for (int i = 0; i < WORDFILE_RES_IDS.length; i++) {
                InputStream istream = context.getResources().openRawResource(WORDFILE_RES_IDS[i]);
                DataInputStream in = new DataInputStream(istream);
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine = "";
                String letters = "";

                int count = 0;
                while ((strLine = br.readLine()) != null) {
                    count++;
                }
                istream.close();
                br.close();

                Log.v("blarg", "index " + i + " count " + count);
            }
        } catch (Exception e) {

        }

    }


}
