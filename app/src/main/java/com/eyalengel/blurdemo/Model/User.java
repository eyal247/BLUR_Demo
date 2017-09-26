package com.eyalengel.blurdemo.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eyal on 9/18/17.
 */

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String currQuote;
    private int age;
    private String nickname;
    private ArrayList<User> friends;
    private ArrayList<Conversation> messages;

    public User(){

    }

    public int getID(){
        return this.id;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public String getCurrQuote(){
        return this.currQuote;
    }

    public int getAge(){
        return this.age;
    }

    public String getNickname(){
        return this.nickname;
    }

    public ArrayList<User> getFriends(){
        return this.friends;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setCurrQuote(String currQuote){
        this.currQuote = currQuote;
    }

    public void setAge(int age){
        this.age = age;
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public void setFriendsList(ArrayList<User> friendsList){
        this.friends = (ArrayList<User>)friendsList.clone();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
