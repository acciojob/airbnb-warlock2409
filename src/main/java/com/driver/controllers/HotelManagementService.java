package com.driver.controllers;

import com.driver.model.Booking;
import com.driver.model.Facility;
import com.driver.model.Hotel;
import com.driver.model.User;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HotelManagementService {
    HashMap<String, Hotel> hm  = new HashMap<>();
    HashMap<Integer, User> hu  = new HashMap<>();

    HashMap<String,Booking> hb = new HashMap<>();

    String addHotel(Hotel newHotel){
        if(hm.containsKey(newHotel.getHotelName())){
            return "FAILURE";
        }else{
            hm.put(newHotel.getHotelName(),newHotel);
        }
        return "SUCCESS";
    }

    String mostNumber(){
        int count=1;
        String name="";
        for(String Hotelname : hm.keySet()){
            if(count < hm.get(Hotelname).getFacilities().size()){
                count=hm.get(Hotelname).getFacilities().size();
                name=Hotelname;
            }else if(count == hm.get(Hotelname).getFacilities().size() && count!=0){
                if(Hotelname.compareTo(name)<0){
                    name = Hotelname;
                }
            }
        }
        return name;
    }

    int AddUser(User user){
        if(!hu.containsKey(user.getaadharCardNo())){
            hu.put(user.getaadharCardNo(),user);
        }
        return user.getaadharCardNo();

    }
    Hotel updateFacilities(List<Facility> f, String name){

        Set<Facility> hf = new HashSet<>();
        for(int i=0;i<f.size();i++){
            if(!hf.contains(f.get(i))){
               hf.add(f.get(i));
            }
        }
        System.out.println(hf+" "+hm);

        for(String hotelName : hm.keySet()){
            System.out.println(hotelName);

            if(hotelName.equals(name)){

                List<Facility> hotelF = new ArrayList<>(hm.get(hotelName).getFacilities());
                for (int i = 0; i < hotelF.size(); i++) {
                    if (hf.contains(hotelF.get(i))) {
                        hf.remove(hotelF.get(i));
                    }
                }
                hotelF.addAll(hf);
                hm.get(hotelName).setFacilities(hotelF);
                return hm.get(hotelName);
            }
        }
        return null;
    }
    int bookRoom(Booking book){
        String uuid = UUID.randomUUID().toString();
        int rooms = book.getNoOfRooms();
        int price = -1;
        String name = book.getHotelName();
        for(String hotelName : hm.keySet()){
            if(hotelName.equals(name)){
                if(hm.get(hotelName).getAvailableRooms()>=rooms){
                    hm.get(hotelName).setAvailableRooms(hm.get(hotelName).getAvailableRooms()-rooms);
                    price = hm.get(hotelName).getPricePerNight()*rooms;
//                    Booking newBooking = new Booking(uuid,book.getBookingAadharCard(),book.getNoOfRooms(),book.getBookingPersonName(),book.getHotelName());
                    book.setBookingId(uuid);
                    book.setAmountToBePaid(price);
                    hb.put(uuid,book);
                }
                break;
            }
        }

        return price;
    }
    int getNoOfBookings(int aadhaar){
        int count =0;
        for(String uuid : hb.keySet()){
            if(aadhaar==hb.get(uuid).getBookingAadharCard()){
                count++;
            }
        }
        return count;
    }

}
