package com.proshine.base.common.utils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

@SuppressWarnings("unchecked")
public class ActivationCount {
    // private static final String ACTIVATION_COUNT_FILE = "forrad";
    public static final long START_EPOCH_OF_DAY = LocalDate.of(2017, 1, 1).toEpochDay();
    public static final Integer UNREGISTERED = 0;
    public static final Integer REGISTERED = 1;
    public static final Integer DEMO = 2;
    private static final String ACTIVATION_COUNT = "ACTIVATION_COUNT";
    // User node is used to keep compatibility with version < 1.9.8.
    private static final Preferences legacy_prefs = Preferences.userNodeForPackage(Preferences.class);
    // User node is used to keep compatibility with 1.9.8 <= version < 1.9.8.
    private static final Preferences legacy_prefs_2 = Preferences.systemNodeForPackage(Preferences.class);

    public static String getSerialNumber(String code) {
        List<Long> macs = MacAddresses.getMacs();
        if (macs.size() < 1) {
            return null;
        }
        Long encrpt = macs.get(0);
        final Map count = getSerialNumberCount(code);
        if (count.get("status").equals(REGISTERED) && count.get("value") != null && (Integer) count.get("value") > 0) {
            encrpt += (Integer) count.get("value") << 16;
        }
        Huffman.setCharacterSet(Huffman.CharacterSet.UpperLetterPlusNumber);
        return Huffman.encode(encrpt, 64);
    }

    public static Map getSerialNumberCount(String code) {
        Map map = new HashMap<String, Integer>();
        if (code == null) {
            // This is to count for compatibility with 1.9.8 <= version < 1.9.8.1.
            code = legacy_prefs_2.get(ACTIVATION_COUNT, null);
        }

        if (code == null) {
            // This is to count for compatibility with version < 1.9.8.
            code = legacy_prefs.get(ACTIVATION_COUNT, null);
        }

        if (code == null) {
            map.put("status", UNREGISTERED);
            map.put("value", null);
            return map;
        } else {
            return getCount(code);
        }

    }

    public static Map getCount(String code) {
        Map unregistered = new HashMap();
        unregistered.put("status", UNREGISTERED);
        unregistered.put("value", null);
        final Long decoded = decode(code);
        if (decoded == null) {
            return unregistered;
        }

        for (Long mac : MacAddresses.getMacs()) {
            String info = match(decoded, mac);
            if (info == null) {
                continue;
            }

            if (info.equals("")) {
                // To keep compatibility with early versions.
                Map m = new HashMap();
                m.put("status", REGISTERED);
                return m;
            }

            int count;
            try {
                count = Integer.parseInt(info);
            } catch (NumberFormatException e) {
                return unregistered;
            }

            if (count < 0) {
                // Trial mode
                final int endDay = -count;
                final LocalDate today = LocalDate.now();
                final long remainingDays = START_EPOCH_OF_DAY + endDay - today.toEpochDay() + 1;
                Map map = new HashMap();
                map.put("status", DEMO);
                map.put("value", (int) remainingDays);

                return remainingDays > 0 ? map : unregistered;
            } else {
                Map mm = new HashMap();
                mm.put("status", REGISTERED);
                mm.put("value", count);
                return mm;
            }
        }
        return unregistered;
    }

    private static Long decode(String code) {
        Huffman.setCharacterSet(Huffman.CharacterSet.LetterPlusNumber);
        return Huffman.decode(code, 64);
    }

    private static String match(Long code, Long mac) {
        String secret = Long.toString(functional(mac));
        Huffman.setCharacterSet(Huffman.CharacterSet.LetterPlusNumber);
        String codeString = Long.toString(code);
        if (!codeString.endsWith(secret)) {
            return null;
        }
        return codeString.substring(0, codeString.length() - secret.length());
    }

    public static long functional(long orig) {
        return Math.round(Math.pow(orig, Math.pow(2.0, 1.0 / 12.0)));
    }

    //UNREGISTERED   0
    // REGISTERED    1
    // DEMO
    public enum Mode {

    }
}
