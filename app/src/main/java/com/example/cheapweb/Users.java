package com.example.cheapweb;

import java.util.ArrayList;
import java.util.List;

public class Users {
  String name1, name2, email, password;

  public Users(String name1, String name2, String email, String password) {
    this.name1 = name1;
    this.name2 = name2;
    this.email = email;
    this.password = password;
  }

  public String getName1() {
    return name1;
  }

  public void setName1(String name1) {
    this.name1 = name1;
  }

  public String getName2() {
    return name2;
  }

  public void setName2(String name2) {
    this.name2 = name2;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
