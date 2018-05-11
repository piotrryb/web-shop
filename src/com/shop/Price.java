package com.shop;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.math.BigDecimal;

@Embeddable
public class Price {

    private BigDecimal netPrice;
    private BigDecimal grossPrice;

    @Transient
    BigDecimal vatPrice;

    public Price() {
    }

    public Price(BigDecimal netPrice, BigDecimal grossPrice) {
        this.netPrice = netPrice;
        this.grossPrice = grossPrice;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public BigDecimal getGrossPrice() {
        return grossPrice;
    }

    public void setGrossPrice(BigDecimal grossPrice) {
        this.grossPrice = grossPrice;
    }

    public BigDecimal getVatPrice() {
        return grossPrice.subtract(netPrice);
    }

}
