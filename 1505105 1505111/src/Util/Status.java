package Util;

import java.io.Serializable;

/**
 * Created by Utshaw on 12/15/2016.
 */
public class Status implements Serializable,Cloneable {
    public String status;
    public int likes;
    public  Status()
    {
        likes=0;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
