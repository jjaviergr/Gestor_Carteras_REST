/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BD;

import POJOS.Usuarios;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pc
 */

@XmlRootElement(name = "responseList")
public class ResposeList {

    private List<Object> list=new ArrayList();
    
    @XmlElement
    public List<Object> getList() {
        return list;
    }

    

    void setList(List lista) {
        for(int i=0;i<lista.size();i++)
        {
            list.add(lista.get(i).toString());
        }
         
    }

}