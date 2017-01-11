package ru.artsok.util.impl;


import ru.artsok.entity.Migu;
import ru.artsok.entity.MiguMap;
import ru.artsok.entity.interfaces.MiguHandle;
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
            context = JAXBContext.newInstance(MiguMap.class);
            Marshaller marshaller = context.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            MiguMap miguMap = new MiguMap();
            miguMap.setMap(MiguHandle.miguMap.getMap());
            marshaller.marshal(miguMap, new File("src/ru/artsok/resources/xml_state/test.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Migu> getMigu() {
        JAXBContext context = null;
        MiguMap miguMap = null;
        try {
            context = JAXBContext.newInstance(MiguMap.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            miguMap = (MiguMap) unmarshaller.unmarshal(new File("src/ru/artsok/resources/xml_state/test.xml"));
            MiguHandle.miguMap.getMap().putAll(miguMap.getMap());
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
