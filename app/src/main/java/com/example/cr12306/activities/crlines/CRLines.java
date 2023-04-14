package com.example.cr12306.activities.crlines;

import androidx.annotation.NonNull;

public enum CRLines {
    Beijing_Guangzhou {
        @NonNull
        @Override
        public String toString(){
            return "京广线";
        }
    },
    Beijing_HKWKowloog {
        @NonNull
        @Override
        public String toString() {
            return "京九线";
        }
    },
    LanZhou_LianYun {
        @NonNull
        @Override
        public String toString() {
            return "陇海线";
        }
    },
    BaoTou_LanZhou {
        @NonNull
        @Override
        public String toString() {
            return "包兰线";
        }
    },
    LanZhou_Xinjiang {
        @NonNull
        @Override
        public String toString() {
            return "兰新线";
        }
    },
    Harbin_Dalian {
      @NonNull
      @Override
      public String toString() {
          return "哈大线";
      }
    },
    Guangzhou_Shenzhen {
        @NonNull
        @Override
        public String toString() {
            return "广深线";
        }
    }
}
