/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

/**
 *
 * @author user
 */
public class Information {
    public String username;
    public NetworkConnection netConnection;    
    
    public Information(String user, NetworkConnection nConnection){
          username=user;
          netConnection=nConnection;
    }
    
}
