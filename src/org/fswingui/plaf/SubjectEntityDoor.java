/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf;

/**
 *当自定义组件支持SubjectModel时，使用该接口。以便皮肤机制调用。
 */
public interface SubjectEntityDoor {
    public void setSubjectEntity(SubjectEntity sm);
    public SubjectEntity getSubjectEntity();
}
