package com.ecommerce.ecommApp.products.composite;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
public class CartIdentity implements Serializable {
    @NotNull
    private Long customerId;

    @NotNull
    private Long productid;


    public CartIdentity(@NotNull Long customerId, @NotNull Long productid) {
        this.customerId = customerId;
        this.productid = productid;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductid() {
        return productid;
    }

    public void setProductid(Long productid) {
        this.productid = productid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartIdentity)) return false;
        CartIdentity that = (CartIdentity) o;
        return getCustomerId().equals(that.getCustomerId()) &&
                getProductid().equals(that.getProductid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCustomerId(), getProductid());
    }
}
