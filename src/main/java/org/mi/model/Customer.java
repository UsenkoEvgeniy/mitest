package org.mi.model;

import java.util.HashSet;
import java.util.Set;

public class Customer {
    private String customerId;
    private String col_1;
    private String col_2;
    private String t1_col_1;
    private String t2_col_1;
    private String t3_col_1;
    private Set<Integer> groups = new HashSet<>();

    public Customer(String customerId, String col_1, String col_2, String t1_col_1, String t2_col_1, String t3_col_1) {
        this.customerId = customerId;
        this.col_1 = col_1;
        this.col_2 = col_2;
        this.t1_col_1 = t1_col_1;
        this.t2_col_1 = t2_col_1;
        this.t3_col_1 = t3_col_1;
    }

    public Set<Integer> getGroups() {
        return groups;
    }
}
