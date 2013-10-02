/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial.model;

import grandroid.database.Identifiable;
import grandroid.database.Table;
import org.json.JSONArray;

/**
 * 會話紀錄 包含 account[ name, url] message[who, content, time]
 *
 * @author Grasea
 */
@Table("Conversation")
public class Conversation implements Identifiable {

    protected Integer _id;
//    protected JSONArray account;
    protected String account;
    protected String name;//姓名	{ String }
    protected String url;//照片url	{ String }
    protected JSONArray message;
    // String message_account // 發出訊息的使用者
//    protected String message;
//    protected String time;  //(yyyy.MM.dd HH.mm)

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

//    public JSONArray getAccount() {
//        return account;
//    }
//
//    public void setAccount(JSONArray account) {
//        this.account = account;
//    }
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public String getTime() {
//        return time;
//    }
//
//    public void setTime(String time) {
//        this.time = time;
//    }
    
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

    public JSONArray getMessage() {
        return message;
    }

    public void setMessage(JSONArray message) {
        this.message = message;
    }
}
