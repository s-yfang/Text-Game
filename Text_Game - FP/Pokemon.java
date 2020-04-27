//Pokemon
//Sheng Fang

import java.util.*;
import java.io.*;

class Pokemon{
  String name, type, resist, weakness, attkName, enCost, dmg, special;
  int hp, energy, numAttk;
  
  ArrayList <Attack> attacks;
  Attack attk;  
  
  boolean en, faint, stun, dis;
  
  public Pokemon(String st){
    
    String [] stats = st.split(",");
    name = stats[0];
    hp = Integer.parseInt(stats[1]);
    type = stats[2];
    resist = stats[3];
    weakness = stats[4];
    numAttk = Integer.parseInt(stats[5]);
    
    energy = 50;
    
    //pokemon attacks
    attacks = new ArrayList<Attack>();
    
    for(int i = 0; i < numAttk; i++){
      String n = stats[6 + i*4];
      int c = Integer.parseInt(stats[7 + i*4]);
      int d = Integer.parseInt(stats[8 + i*4]);
      String s = stats[9 + i*4];
      
      attacks.add(new Attack(n, c, d, s));
    }
  }
  
  public String toString(){
    return String.format("%-12s%3d%12s%10s%10s%3s", name, hp, type, resist, weakness, numAttk);
  }
  
  public boolean isStunned(){
    return stun;
  }
  
  public void stunned(){
    stun = true;
  }
  
  public void deStun(){
    stun = false;
  }
  
  public void disable(){
    for(Attack a : attacks){
      a.dmg = Math.max(0, a.dmg - 10);
    }
  }
  
  public boolean isDisabled(){
    return dis;
  }
  
  public void attack(Pokemon foe, Pokemon player, Attack attk){
    //energy
    player.energy = Math.max(0, player.energy - attk.cost);
    
    //resistance 
    if((foe.resist).equals(player.type)){
      System.out.println("\n> It's not very effective...");
      attk.dmg /= 2;
    }
    
    //weakness
    if((foe.weakness).equals(player.type)){
      System.out.println("\n> It's super effective!");
      attk.dmg *= 2;
    }
    
    //special attacks
    int ranSp = (int)(Math.random()*2);     //0 - works, 1 - failed
    
    if((attk.special).equals("stun") && ranSp == 0){
      foe.stunned();
      System.out.println("\n > " + foe.name + " is stunned!");
      System.out.println("\n> " + foe.name + " can't attack or retreat!");
    }
    
    else if((attk.special).equals("disable") && dis == false){
      dis = true;
      foe.disable();
      System.out.println("\n> " + foe.name + " is disabled!");
      System.out.println("\n> All " + foe.name + "'s attacks have decreased by 10!");
    }
    
    else if((attk.special).equals("wild card") && ranSp == 1){
      attk.dmg = 0;
      System.out.println(attk.name + " failed!");
    }
    
    else if((attk.special).equals("wild card") && ranSp == 0){
      foe.hp = Math.max(0, foe.hp - attk.dmg);
      System.out.println(attk.name + " was successful!");
    }
    
    else if((attk.special).equals("wild storm") && ranSp == 0){
      while(ranSp == 0){
        System.out.println("\n" + player.name + " used " + attk.name + "!");
        foe.hp = Math.max(0, foe.hp - attk.dmg);
        ranSp = (int)(Math.random()*2);
      }
    }
    
    else if((attk.special).equals("recharge")){
      player.energy = Math.min(player.energy + 20, 50);
      System.out.println(player.name + " gained +20 energy!");
    }
    
    foe.hp = Math.max(0, foe.hp - attk.dmg);
  }
  
  public ArrayList <Attack> getAttacks(){
    return attacks;
  }
  
  //Checks if player HP is above 0
  public boolean defeat(Pokemon player){
    if(player.hp <= 0){
      faint = true;
    }
    else{
      faint = false;
    }
    return faint;
  }
  
  //Checks if player energy is above attack energy cost
  public boolean checkEn(Pokemon player, Attack a){
    if(player.energy < a.cost){
      en = false;
    }
    if(player.energy > a.cost){
      en = true;
    }
    return en;
  }
  
  //Adds 10 energy, cannot be above 50
  public void rest(Pokemon player){
    player.energy = Math.min(player.energy + 10, 50); 
  }
  
}




