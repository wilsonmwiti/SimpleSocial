/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ivn.peng.simplesocial.model;

import grandroid.database.Identifiable;
import grandroid.database.Table;

/**
 *
 * @author Grasea
 */
@Table("Friend")
public class Friend implements Identifiable {

    protected Integer _id;
    protected String account;//	朋友id	{ String }
    protected String name;//姓名	{ String }
    protected String url;//照片url	{ String }
    protected String is_best;   // Y | N

    public Friend() {
        super();
    }

    public Friend(String account, String name, String url, String is_best) {
        this();
        this.account = account;
        this.name = name;
        this.url = url;
        this.is_best = is_best;
    }

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

    public String getIs_best() {
        return is_best;
    }

    public void setIs_best(String is_best) {
        this.is_best = is_best;
    }
}
