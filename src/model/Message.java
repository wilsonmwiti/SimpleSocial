/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import grandroid.database.Identifiable;
import grandroid.database.Table;

/**
 *
 * @author Grasea
 */
@Table("Friend")
public class Message implements Identifiable {

    protected Integer _id;
    protected String account;
    protected String name;//姓名	{ String }
    protected String url;//照片url	{ String }
    protected String message;
    protected String time;  //(yyyy.MM.dd HH.mm)

    public Integer get_id() {
        return this._id;
    }

    public void set_id(Integer _id) {
        this._id = _id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
}
