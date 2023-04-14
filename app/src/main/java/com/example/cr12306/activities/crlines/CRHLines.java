package com.example.cr12306.activities.crlines;

import androidx.annotation.NonNull;

public enum CRHLines {
    DanDong_FangChengGang {
        @NonNull
        @Override
        public String toString(){
            return "沿海通道";
        }
    },
    BeijingNan_SHHongQiao {
        @NonNull
        @Override
        public String toString() {
            return "京沪通道";
        }
    },
    BeijingWest_TaiWan {
        @NonNull
        @Override
        public String toString() {
            return "京台通道";
        }
    },
    BeijingWest_HongKong {
        @NonNull
        @Override
        public String toString() {
            return "京港通道";
        }
    },
    BeijingChaoYang_Harbin {
        @NonNull
        @Override
        public String toString() {
            return "京哈通道";
        }
    },
    HuHeHaoTe_NanNing {
        @NonNull
        @Override
        public String toString() {
            return "呼南通道";
        }
    },
    BeijingXi_KunMing {
        @NonNull
        @Override
        public String toString() {
            return "京昆通道";
        }
    },
    BaoTou_HaiKou {
        @NonNull
        @Override
        public String toString() {
            return "包(银)海通道";
        }
    },
    LanZhouWest_GuangzhouSouth {
        @NonNull
        @Override
        public String toString() {
            return "兰(西)广通道";
        }
    }
}
