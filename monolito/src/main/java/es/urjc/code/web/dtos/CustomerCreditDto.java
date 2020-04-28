package es.urjc.code.web.dtos;

import java.math.BigDecimal;

public class CustomerCreditDto {

    BigDecimal credit;

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }
}