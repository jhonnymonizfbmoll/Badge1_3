package com.fbmoll.badges.persistence.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement(name = "dataContainer")
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class MarshallingWrapper implements Serializable {

}