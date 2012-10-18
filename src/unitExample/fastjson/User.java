/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONCreator;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import java.io.Serializable;
import javax.swing.ImageIcon;
 
public class User implements Serializable {
    
    private String userid;
    private String username;
    private ImageIcon icon;
 
    //指定构造函数中的字段
    @JSONCreator
    public User(@JSONField(name = "userid") String d) {
        this.userid = userid;
    }
    
//    //如果构造中的字段名在属性中不存在，将出错
//    public User( String dd) {
//        this.userid = dd;
//    }

    @JSONField(serialize=false) 
    private  UserDetail userDetail;
    
     private  UserDetail ss;

    public String getUserid() {
       return userid;
    }

    public void setUserid(String userid) {
       this.userid = userid;
    }

    public String getUsername() {
       return username;
    }

    public void setUsername(String username) {
       this. username = username;
    }

    public UserDetail getUserDetail() {
       return userDetail;
    }

    public void setUserDetail (UserDetail userDetail) {
       this.userDetail = userDetail;
    }

    public UserDetail getSs() {
        return ss;
    }

    public void setSs(UserDetail ss) {
        this.ss = ss;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
    
    
    public static void main(String[] args) {
        User u=new User("zhang");
        u.setUsername("张三");
        UserDetail ud=new  UserDetail();
        ud.setAddress("中国");

        ImageIcon icn=new ImageIcon("1.jpg");
        u.setIcon(icn);
        SerializeWriter out = new SerializeWriter();
        JSONSerializer serializer = new JSONSerializer(out);
        ValueFilter filter = new ValueFilter() 
        {//该过滤器，让value的ImageIcon值变成了  表达文件地址的字符串值。
            public Object process(Object source, String name, Object value) {
                if (name.equals("icon")) {
                    ImageIcon i= (ImageIcon) value;
                    return i.getDescription();
                }
                return value;
            }

        };
        String s=JSON.toJSONString(u, filter,SerializerFeature.PrettyFormat,
                SerializerFeature.WriteClassName);
       
        System.out.println(s);        
        System.out.println();
    }
}
