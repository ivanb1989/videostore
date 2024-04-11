package it.cgmconsulting.boccia.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerStoreResponse {

    private String storeName;
    private Long totalCustomers;

}
