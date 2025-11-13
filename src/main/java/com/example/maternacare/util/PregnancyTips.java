package com.example.maternacare.util;

import java.util.ArrayList;
import java.util.List;

public class PregnancyTips {

    public static List<String> getTipsForWeek(int week) {
        List<String> tips = new ArrayList<>();
        if (week <= 0) {
            tips.add("Set your profile due date to get personalized tips.");
            return tips;
        }
        if (week <= 4) {
            tips.add("Start or continue taking folic acid (400mcg daily).");
            tips.add("Hydrate well and rest.");
        } else if (week <= 12) {
            tips.add("Schedule your first antenatal appointment.");
            tips.add("Small, frequent meals can ease nausea.");
        } else if (week <= 20) {
            tips.add("Time for an anatomy scan around week 20.");
            tips.add("Incorporate light exercise like walking.");
        } else if (week <= 28) {
            tips.add("Be mindful of posture; consider a maternity pillow.");
            tips.add("Track fetal movements.");
        } else if (week <= 36) {
            tips.add("Discuss birth plan and hospital preferences.");
            tips.add("Prepare your hospital bag.");
        } else {
            tips.add("Watch for labor signs and keep your phone charged.");
            tips.add("Line up support for after delivery.");
        }
        return tips;
    }
}
