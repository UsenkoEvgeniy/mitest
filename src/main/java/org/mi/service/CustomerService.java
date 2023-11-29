package org.mi.service;

import org.mi.dao.CustomerDao;
import org.mi.model.Customer;

import java.util.Collection;

public class CustomerService {
    private static CustomerDao dao = new CustomerDao();

    public Collection<Customer> getCustomers(Integer offset) {
        return dao.getCustomers(offset);
    }
}
