package ru.artsok.entity;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "state")
@XmlType(propOrder = {"miguIsRespond"})

public class MiguState {
    private boolean miguIsRespond;

    public MiguState() {
    }


    public MiguState(boolean miguIsRespond) {
        this.miguIsRespond = miguIsRespond;
    }

    public boolean isMiguIsRespond() {
        return miguIsRespond;
    }

    @XmlElement
    public void setMiguIsRespond(boolean miguIsRespond) {
        this.miguIsRespond = miguIsRespond;
    }
}
