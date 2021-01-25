package com.fbmoll.badges.persistence.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.Serializable;

public class FileDataSingleton {
    private Logger log = LoggerFactory.getLogger(FileDataSingleton.class);
    private static FileDataSingleton instance = null;

    public static FileDataSingleton getInstance() {
        if (instance == null) {
            instance = new FileDataSingleton();
        }
        return instance;
    }

    private FileDataSingleton() {
    }

    private ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME);
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(mapper.getTypeFactory());
        mapper.setAnnotationIntrospector(introspector);
        // Don't close the output stream
        mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        // Don't include NULL properties.
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

    public <T extends Serializable> String marshall2JSON(T item) {
        try {
            return getMapper().writeValueAsString(item);
        } catch (Exception e) {
            log.error("Marshalling to string failure", e);
            return StringUtils.EMPTY;
        }
    }

    public <T extends Serializable> File marshallContent(String path, T catalog) {
        File file = new File(path);
        try {
            JAXBContext context = JAXBContext.newInstance(catalog.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(catalog, file);
        } catch (Exception e) {
            log.error("Marshalling file", e);
        }
        return file;
    }

    public <T extends Serializable> T unmarshallContent(String path, Class<T> typeArgumentClass) {
        File file = new File(path);
        try {
            T catalog = typeArgumentClass.newInstance();
            JAXBContext context = JAXBContext.newInstance(catalog.getClass());
            Unmarshaller um = context.createUnmarshaller();
            if (file.exists() && !file.isDirectory()) {
                catalog = (T) um.unmarshal(file);
            }
            return catalog;
        } catch (Exception e) {
            log.error("Marshalling file", e);
        }
        return null;
    }

}
