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
import java.io.StringWriter;
import java.util.List;
import static ru.artsok.SettingMainController.*;

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
            marshaller.marshal(miguMap, new File(patchResource + "xml_state/test.xml"));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getMiguToString() {
        StringWriter sw = new StringWriter();
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(MiguMap.class);
            Marshaller marshaller = context.createMarshaller();

//            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            MiguMap miguMap = new MiguMap();
            miguMap.setMap(MiguHandle.miguMap.getMap());
            marshaller.marshal(miguMap,sw);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    @Override
    public List<Migu> getMigu() {
        JAXBContext context = null;
        MiguMap miguMap = null;
        try {
            context = JAXBContext.newInstance(MiguMap.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            miguMap = (MiguMap) unmarshaller.unmarshal(new File(patchResource + "xml_state/test.xml"));
            for (Migu migu : miguMap.getMap().values()) {
                MiguHandle.miguMap.getMap().put(migu.getNumber(), migu.setViewItem());
//                MiguHandle.miguMap.getMap().putAll(miguMap.getMap());
            }
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
