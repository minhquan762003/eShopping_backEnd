package com.example.E_Shopping.model;

import java.util.Date;

public interface Auditable {
    void setCreatedAt(Date createdAt);
    void setUpdatedAt(Date updatedAt);
}
