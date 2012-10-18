/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.config;

/**
 *
 * @author Administrator
 */
public class ParameterConfig {
    private String type;
    private String value;
    private String defaultValue;
    private String allowNull="false";
    private String BaseParameterCodingType;
  
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getAllowNull() {
        return allowNull;
    }

    public void setAllowNull(String allowNull) {
        this.allowNull = allowNull;
    }

    
    public String getBaseParameterCodingType() {
        return BaseParameterCodingType;
    }

    public void setBaseParameterCodingType(String BaseParameterCodingType) {
        this.BaseParameterCodingType = BaseParameterCodingType;
    }
    
}
