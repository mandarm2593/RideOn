package com.example.mandar.rideon;

import java.io.Serializable;

/**
 * Created by Nita on 4/26/2017.
 */

public class RideOnDatabase implements Serializable {

   public  String capacity, cost, date , time,name,email,phone,key,from,to;



public RideOnDatabase()
{}
    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity=capacity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date=date;
    }

    public String getCost() {
        return cost;
    }
    public void setCost(String cost) {
        this.cost=cost;
    }


   /* public String getFrom_To() {
        return from_To;
    }

    public void setFrom_To(String from_To) {
        this.from_To=from_To;
    }
*/
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time=time;
    }

    public String getName()
    {
        return name;
    }
     public void setName(String name)
     {
         this.name=name;
     }

     public String getPhone()
     {
         return phone;
     }
    public void setPhone(String phone)
    {
        this.phone=phone;
    }

    public String getEmail()
    {
        return email;

    }
 public void setEmail(String email)
 {
     this.email=email;
 }


 public String getKey()
 {
  return key;
 }

 public void setKey(String key)
 {
     this.key=key;
 }
 public String getFrom()
 {
     return from;
 }
   public void setFrom(String from)
   {
       this.from=from;
   }

 public  String getTo()
 {
     return to;
 }
 public void setTo(String to)
 {
     this.to=to;
 }


}




