package es.urjc.code.proxies;

import java.math.BigDecimal;

public class CreditDto {

    BigDecimal credit;

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public CreditDto(BigDecimal credit) {
        this.credit = credit;
    }
}