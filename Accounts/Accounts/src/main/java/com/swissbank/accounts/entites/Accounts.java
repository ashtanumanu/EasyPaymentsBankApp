package com.swissbank.accounts.entites;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Accounts extends BaseEntity {
    @Id
    private Long accountNumber;
    private Long customerId;
    private String accountType;
    private String branchAddress;

}
