package com.pallasathenagroup.querydsl;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.jadira.usertype.moneyandcurrency.moneta.PersistentMoneyAmountAndCurrency;

@Entity
@TypeDef(name = "money", typeClass = PersistentMoneyAmountAndCurrency.class, defaultForType = MonetaryAmount.class)
public class MonetaryAmountEntity {

    @Id
    Long id;

    @Type(type = "money")
    @Columns(columns = {
            @Column(name = "currency"),
            @Column(name = "amount"),
    })
    private MonetaryAmount monetaryAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonetaryAmount getMonetaryAmount() {
        return monetaryAmount;
    }

    public void setMonetaryAmount(MonetaryAmount monetaryAmount) {
        this.monetaryAmount = monetaryAmount;
    }

}
