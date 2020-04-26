package es.urjc.code.web.dtos;

import java.math.BigDecimal;

public class CustomerDto {

    private Long id;
    private String name;
    private BigDecimal credit;

    public CustomerDto() {
    }

    public CustomerDto(Long id, String name, BigDecimal credit) {
        this.id = id;
        this.name = name;
        this.credit = credit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }
}