package ru.artsok.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@XmlRootElement(name = "MIGU")
@XmlAccessorType(XmlAccessType.FIELD)
public class MiguMap {
    private Map<Integer, Migu> map = new HashMap<Integer, Migu>();

    public void setState(Integer number, MiguState state) {
        map.get(number).setStates(state);
    }

    public void setStateByAddress(byte address, MiguState state) {
        for (Migu migu : getMap().values())
            if (migu.getAddress() == address)
                setState(migu.getNumber(), state);
    }


    public MiguState getState(Integer address) {
        return map.get(address).getStates();
    }

    public Map<Integer, Migu> getMap() {
        return map;
    }

    public void setMap(Map<Integer, Migu> map) {
        this.map = map;
    }
}
