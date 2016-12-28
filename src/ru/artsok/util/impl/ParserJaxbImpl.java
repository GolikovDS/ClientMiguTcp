package ru.artsok.util.impl;


import ru.artsok.entity.Migu;
import ru.artsok.util.ParserJaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class ParserJaxbImpl implements ParserJaxb {
    @Override
    public void setMigu(Migu migu) {
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(Migu.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(migu,new File("src/ru/artsok/resources/test.xml"));

        } catch (JAXBException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Migu> getMigu() {
        JAXBContext context = null;
        List<Migu> object = null;
        try {
            context = JAXBContext.newInstance(Migu.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            object = (List<Migu>) unmarshaller.unmarshal(new File("ru/artsok/resources/test.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return object;
    }
}
