package ru.artsok.util;


import ru.artsok.entity.Migu;

import java.util.List;

public interface ParserJaxb {
    public void setMigu(Migu migu);
    public String getMiguToString();
    public List<Migu> getMigu();
}
