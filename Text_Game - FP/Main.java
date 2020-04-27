//Pokemon
//Sheng Fang

import java.util.*;
import java.io.*;

public class Main{
  
  private static ArrayList <Pokemon>pList;
  private static ArrayList <Pokemon>team;
  private static ArrayList <Pokemon>enemies;
  
  private static int ATTK = 1, PASS = 2, RE = 3, action;
  private static Pokemon p, e;  //current pokemon and enemy
  
  public static Pokemon chooseEnemies(ArrayList <Pokemon> enemies){
    int ranP = (int)(Math.random()*(enemies.size())); //Chooses random enemy from list
    e = enemies.get(ranP);        
    
    System.out.println("\n<< Your opponent is " + e.name + "! >>");
    System.out.println("\nHP: " + e.hp);
    System.out.println("ATTACKS: " + e.getAttacks());
    
    return e;
  }
  
  public static Pokemon chooseBattle(ArrayList <Pokemon> team){
    System.out.println("\n" + "<< Who Will You Choose to Fight? >>\n");
    System.out.println("> Enter Pokemon Number: ");
    
    for(int i = 0; i < team.size(); i++){
      System.out.println(i + ". " + (team.get(i)).name);
    }
    
    Scanner input = new Scanner(System.in);
    int pNum = input.nextInt();
    p = team.get(pNum);
    
    //Prevents choosing defeated pokemon
    while(p.defeat(p) == true){
      System.out.println("\n> " + p.name + " has fainted!");
      System.out.println("\n> Choose another Pokemon!");
      
      pNum = input.nextInt();
      p = team.get(pNum);
      
    }
    System.out.println("\n> " + p.name + ", I Choose You!");
    
    return p;
  }
  
  public static boolean lose(){     //checks if all team pkm has been defeated
    int KO = 0;
      for(Pokemon tm : team){
        if(tm.defeat(tm) == true){
          KO += 1;
        }
      }
      if(KO == 4){
        return true;
      }
      else{
        return false;
      }
  }
  
  public static void battle(ArrayList <Pokemon> enemies, int action){
    
    int t = (int)(Math.random()*2);     //Chooses random starter 
                                        //even num is player, odd is enemy
    while(e.defeat(e) == false){ 
      
      if(t % 2 == 0){
        Scanner inputAct = new Scanner(System.in);
        System.out.println("\n" + "<< What will you do? >>");
        System.out.println("| ATTACK = 1 | PASS = 2 | RETREAT = 3 |");
        action = inputAct.nextInt();
        
        Scanner input = new Scanner(System.in);
        
        //Stunned
        if(p.isStunned() == true){
          action = PASS;     
          p.deStun();   //unstun after one turn
        }
        
        if(action == ATTK){
          
          //Display attacks
          System.out.println("\n" + "< " + "ATTACKS" + " >" + "\n");
          for(int i = 0; i < p.numAttk; i++){   
            System.out.println(i + ". " + String.valueOf((p.getAttacks()).get(i)) + "\n");
          }
          
          //Get player attack
          System.out.println("<< What should " + p.name + " use? >>");
          int attkNum = input.nextInt();
          Attack attk = (p.getAttacks()).get(attkNum);
          
          //Checks energy values - attacks if enough, new action if not
          if(p.checkEn(p, attk) == true){
            System.out.println("\n> " + p.name + " used " + attk.name + "!"); 
            p.attack(e, p, attk);
          }
          
          else if(p.checkEn(p, attk) == false){
            System.out.println("\n> " + p.name + " doesn't have enough energy!");
            System.out.println("\n" + "<< What will you do? >>");
            System.out.println("| ATTACK = 1 | PASS = 2 | RETREAT = 3 |");
            action = input.nextInt();
            
          }
          
        }
        
        else if(action == PASS){
          System.out.println("> " + p.name + " passes!");
        }
        
        else if(action == RE){
          System.out.println("> " + p.name + " retreated!");
          
          System.out.println("\n<< Who will you choose? >>\n");
          for(int i = 0; i < team.size(); i++){     //Display pokemon
            System.out.println(i + ". " + (team.get(i)).name);
          }
          
          int pNum = input.nextInt();
          p = team.get(pNum);
          
          //Prevents choosing defeated pokemon
          while(p.defeat(p) == true){
            System.out.println("\n> " + p.name + " has fainted!");
            System.out.println("\n> Choose another Pokemon!");
            
            pNum = input.nextInt();
            p = team.get(pNum);
            
            }
          
          System.out.println("\n> Go! " + p.name + ", I Choose You!");
        }
        
        else{
          System.out.println("\n> " + p.name + " can't do that!");
        }
        
      }
      //Enemy turn
      else if(t % 2 != 0){
        System.out.println("\n" + "<< Enemy >>");
        
        //Stun
        if(e.isStunned() == true){
          action = PASS;     
          e.deStun();                           //unstun enemy after 1 turn
        }
        
        int [] enActions = {1,2};
        int enAct = (int)(Math.random()*2);     //chooses random action
        action = enActions[enAct];
        
        if(action == ATTK){
          
          int ranNum = (int)(Math.random()*(e.numAttk));
          Attack attk = (e.getAttacks()).get(ranNum);
          
          //Checks energy values
          if(e.checkEn(e, attk) == true){
            System.out.println(e.name + " used " + attk.name + "!");
            e.attack(p, e, attk); 
          }
          
          else if(e.checkEn(e, attk) == false){
            System.out.println("\n> " + e.name + "doesn't have enough energy!");
          }
        }
        
        else if(action == PASS){
          System.out.println("> " + e.name + " passes!");
        }
      }
      
      //Energy 
      for(Pokemon tm : team){      //adds 10 energy to each team member
        tm.rest(tm);
      }  
      e.rest(e);                   //adds 10 energy to enemy 
      
      //Show stats
      System.out.println("\n| " + p.name + " HP: " + p.hp + " ENERGY: " + p.energy + " |");
      System.out.println("| " + e.name + " HP: " + e.hp + " ENERGY: " + e.energy + " |");
      
      //change turn
      t += 1;
      
      //Lose Game
      if(lose() == true){
        System.out.println("\n> Oh no! All your Pokemon have fainted. Better luck next time!"+ "\n| You Lose... |");
        
        break;
      }
      
      //Pokemon faints
      if(p.defeat(p) == true){
        System.out.println("\n> " + p.name + " fainted!");
        System.out.println(e.name + " wins this round! \n");
        
        chooseBattle(team);
        battle(enemies, action);
      }
      
    }
    
    //Battle continues
    if(lose() == false){
      
      System.out.println("\n> " + e.name + " fainted!");
      System.out.println(p.name + " wins this round! \n");
      
      for(Pokemon tm : team){                 //Heal pokemon who are still awake          
        if(tm.defeat(tm) == false){
          tm.hp = Math.min(tm.hp + 20, 50);   //Restores 20 HP, cannot be above max hp
        }
      }
      
      enemies.remove(e);                      //Remove defeated enemy
      chooseEnemies(enemies);
      battle(enemies, action);
    }
    
    //Defeat all enemies
    if(enemies.size() == 0){
      System.out.println("\n> Congratulations! You have defeated all the enemy Pokemon! You are now the new Trainer Supreme!" + "\n| You Win! |");
    }
  }
  
  public static void loadPokemon(){
    pList = new ArrayList <Pokemon>();
    
    try{
      Scanner inFile = new Scanner(new File("pokemon.txt"));
      int n = inFile.nextInt();
      inFile.nextLine();  
      
      for(int i = 0; i < n; i++){
        String stats = inFile.nextLine();
        pList.add(new Pokemon(stats));
      }
    }
    
    catch(IOException ex){
      System.out.println("File not found");
    }
  }
  
  public static void choosePokemon(){
    team = new ArrayList <Pokemon>();
    enemies = new ArrayList <Pokemon>();
    
    Scanner input = new Scanner(System.in);
    
    //Menu
    System.out.println("<< WELCOME TO THE POKEMON ARENA! >>");
    System.out.println("\n> Today you and your Pokémon will face an exciting and daring challenge!\n> Your goal is to defeat all the enemy Pokémon\n> Will YOU be crowned Trainer Supreme?");
    
    System.out.println("\n> Are you Ready? Let's begin!");
    System.out.println("\n> [Press enter to start]");
    String enter = input.nextLine();
    
    System.out.println("\n------------------ << POKEMON >> -----------------------");
    System.out.printf("%-15s%3s%12s%10s%10s%6s", "Name", "HP", "Type", "Resist", "Weak", "Attk#");
    System.out.println("\n--------------------------------------------------------");
    
    for(int i = 0; i < pList.size(); i++){
      System.out.printf("%2d: %s\n",i, pList.get(i));
      enemies.add(pList.get(i));          //add pokemon to enemy list
    }
    
    //Choose pokemon team
    System.out.println("\n<< Choose Your Pokemon Team! >>\n");
    while(team.size() < 4){
      System.out.println("> Enter Pokemon number: ");
      int pNum = input.nextInt();
      if(!team.contains(pList.get(pNum))){
        team.add(pList.get(pNum));
        enemies.remove(pList.get(pNum));  //removes team pokemon from enemylist
      }
      else{
        System.out.println("--Already chosen--\n");
      }
    }
  }
  
  public static void main(String[] args){
    loadPokemon();
    choosePokemon();
    
    chooseEnemies(enemies);
    chooseBattle(team);
    battle(enemies, action);
  }
  
    
}











