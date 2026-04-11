/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;

import java.util.Scanner;

/**
 *
 * @author Student
 */
//clas was created with 2 methods that being registeruser & userlogin 
class Login{
    
    
    Scanner scanner=new Scanner(System.in);
    String userName;
    boolean checkUserName(String username){
        if(userName.contains("_")&&userName.length()<=5){
            return true;
        }else{
         System.out.println("Username is not corrctly form,please ensure your username contains a underscore and is no more than 5 characters");
         return false;
        }
      
    }
    
    
    String userPassword;
    boolean checkPasswordComplexity(String userpassword){
        if(userPassword.length()>=8 && userPassword.matches (".*[A-Z].*") && userPassword.matches(".*\\d.*") && userPassword.matches(".*[!@#$%^&*()].*")&&userPassword.matches (".*[a-z].*")){
           return true;
        }else{
         System.out.println("Password not correctly formted,please ensure password has atleast 8 characters,a capital letter,a number,and a special character");
         return false;
        }
    }
    
    
    String countryCode;
    String userPhoneNumber;
    boolean CheckCellPhoneNumber (){
        if(userPhoneNumber.contains(countryCode)&&userPhoneNumber.length()==10 && userPhoneNumber.matches("//d+")&& !(userPhoneNumber.matches("00"))){
        return true;
        }else{
           System.out.println("Cell Phone number is incorrectly formted or numberndoes have Country Code");
           return false;
        }
    } 
    
    
    boolean registeruser(){
     System.out.println("=====REGISTER=====");
     System.out.println("Please enter a username");
     userName=scanner.nextLine();
     System.out.println("Please enter a Password");
     userPassword=scanner.nextLine();
     System.out.println("Please enter a CellPhone Number WITH a countrycode");
     countryCode=scanner.nextLine();
     userPhoneNumber=scanner.nextLine();
     if(checkUserName(userName)&&checkPasswordComplexity(userPassword)){
         System.out.println("Registraion Successful");
         System.out.println("Please enter your name");
         String name=scanner.nextLine();
         System.out.println("Please enter your last name");
         String lastname=scanner.nextLine();
         return true;
     }else{
         System.out.println("Registraion failed");   
     }
     return false;
    }
    //userlogin method
    String loginUserName;
    String loginUserPassword;
    
    boolean login (){
     System.out.println("=====LOGIN=====");
     System.out.println("Please enter a username");
     loginUserName=scanner.nextLine();
     System.out.println("Please enter a Password");
     loginUserPassword=scanner.nextLine();
        return true;
     
     
     }
    
    
    }




public class QuickChat {

    public static void main(String[] args) {
    Login c = new Login();
    if(c.registeruser()){
    c.login();
    System.out.println("WELCOME BACK");
    }else{
        System.out.println("Registration failed. Login failed");
    }
}
}
