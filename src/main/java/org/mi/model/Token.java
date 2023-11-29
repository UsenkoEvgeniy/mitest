package org.mi.model;

import java.util.Date;

public class Token {
    private Date expirationDate;
    public Date getExpirationTime() {
        return expirationDate;
    }

    public void refresh() {

    }
}
