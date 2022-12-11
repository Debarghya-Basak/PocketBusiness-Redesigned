package com.dbtapps.pocketbusiness;

public class UserData {

    public String name;
    public int phone_number;

    public String business_address, business_city, business_description, business_name;
    public int business_phone_number, business_pincode;

    public String email_address, password;

    public UserData(String name, int phone_number, String  business_address, String business_city, String business_description, String business_name, int business_phone_number, int business_pincode, String email_address, String password){
        this.name=name;
        this.phone_number=phone_number;
        this.email_address = email_address;
        this.password = password;
        this.business_address=business_address;
        this.business_city=business_city;
        this.business_description=business_description;
        this.business_name=business_name;
        this.business_phone_number=business_phone_number;
        this.business_pincode=business_pincode;
    }

}
