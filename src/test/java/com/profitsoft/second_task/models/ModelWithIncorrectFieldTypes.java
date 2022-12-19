package com.profitsoft.second_task.models;

import com.profitsoft.second_task.annotations.Property;

import java.time.Instant;
import java.util.Date;

public class ModelWithIncorrectFieldTypes {
    private int stringProperty;

    @Property(name = "numberProperty")
    private boolean myNumber;

    @Property(format = "dd.MM.yyyy HH:mm")
    private Date timeProperty;

    public int getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(int stringProperty) {
        this.stringProperty = stringProperty;
    }

    public boolean isMyNumber() {
        return myNumber;
    }

    public void setMyNumber(boolean myNumber) {
        this.myNumber = myNumber;
    }

    public Date getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Date timeProperty) {
        this.timeProperty = timeProperty;
    }
}
