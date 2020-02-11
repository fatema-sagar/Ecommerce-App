package com.ecommerce.ecommApp.products.composite;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
@Embeddable
@NoArgsConstructor
public class CartIdentity implements Serializable {

    @JsonProperty("customer_id")
    @NotNull
    private Long customerId;

    @JsonProperty("product_id")
    @NotNull
    private Long productid;


    public CartIdentity(@NotNull Long customerId, @NotNull Long productid) {
        this.customerId = customerId;
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
