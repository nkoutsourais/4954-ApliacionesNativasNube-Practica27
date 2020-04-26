package es.urjc.code.web.dtos;

import java.math.BigDecimal;

public class IncreaseCreditDto {

    BigDecimal increaseCredit;

    public BigDecimal getIncreaseCredit() {
        return increaseCredit;
    }

    public void setIncreaseCredit(BigDecimal increaseCredit) {
        this.increaseCredit = increaseCredit;
    }
}