package com.toolrental.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Tool {
    private String toolType;
    private String brand;
    private String toolCode;
    private double dailyCharge;
    private boolean weekdayCharge;
    private boolean weekendCharge;
    private boolean holidayCharge;

    public Tool(String toolCode) {
        this.toolCode = toolCode;
        makeTool(toolCode);
        setCharges(toolType);
    }

    private void makeTool(String toolCode) {
        if(Objects.equals(toolCode, "LADW")) {
            this.toolType = "Ladder";
            this.brand = "Werner";
            this.dailyCharge = 1.99;
        } else if(Objects.equals(toolCode, "CHNS")) {
            this.toolType = "Chainsaw";
            this.brand = "Stihl";
        } else if(Objects.equals(toolCode, "JAKR")) {
            this.toolType = "Jackhammer";
            this.brand = "Ridgid";
            this.dailyCharge = 2.99;
        } else if(Objects.equals(toolCode, "JAKD")) {
            this.toolType = "Jackhammer";
            this.brand = "DeWalt";
            this.dailyCharge = 2.99;
        }
    }

    private void setCharges(String toolType) {
        if(toolType.equals("Ladder")) {
            this.dailyCharge = 1.99;
            this.weekdayCharge = true;
            this.weekendCharge = true;
            this.holidayCharge = false;
        } else if(toolType.equals("Chainsaw")) {
            this.dailyCharge = 1.49;
            this.weekdayCharge = true;
            this.weekendCharge = false;
            this.holidayCharge = true;
        } else if(toolType.equals("Jackhammer")) {
            this.dailyCharge = 2.99;
            this.weekdayCharge = true;
            this.weekendCharge = false;
            this.holidayCharge = false;
        }
    }

    public String toString() {
        return "Tool{" +
                "toolType='" + toolType + '\'' +
                ", brand='" + brand + '\'' +
                ", toolCode='" + toolCode + '\'' +
                ", dailyCharge=" + dailyCharge +
                ", weekdayCharge=" + weekdayCharge +
                ", weekendCharge=" + weekendCharge +
                ", holidayCharge=" + holidayCharge +
                '}';
    }
}
