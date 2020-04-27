//Pokemon
//Sheng Fang

import java.util.*;
import java.io.*;

class Attack{
  String name,special;
  int cost, dmg;
  
  public Attack(String n, int c, int d, String s){
    name = n;
    cost = c;
    dmg = d;
    special = s;
  }
  
  public String toString(){
    return name + " " + cost + " " + dmg + " " + special;
  }
}