package com.example.tom.mineclicker;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserContract {
    public static final String DB_NAME = "mine_db";
    public static final int DB_VER = 1;

    public static final class userStats implements BaseColumns {
        public static final String TABLE_NAME = "user_stats";
        public static final String COL_FLOOR = "floor";
        public static final String COL_CLICKS = "clicks";
        public static final String COL_GOLD = "gold";
        public static final String COL_USERNAME = "username";
        public static final String COL_COUNTRY = "country";
        public static final String COL_CLICK_VALUE = "click_value";
        public static final String COL_MINER_VALUE = "miner_value";
        public static final String COL_PASSWORD = "password";
    }

   // public static final String AUTHORITY = "be.carmans.niels.mininggame.contentprovider";
   // public static final String BASE_PATH = "MiningGame";
   // public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);
}
