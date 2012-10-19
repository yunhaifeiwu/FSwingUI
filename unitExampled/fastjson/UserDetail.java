/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.fastjson;

import java.io.Serializable;

public class UserDetail implements Serializable {
    private String address;
    public String getAddress() {
       return address;
    }

    public void setAddress (String address) {
       this. address = address;
    }

}
