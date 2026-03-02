package proje;

public class CustomerData {
    private String name;
    private String surname;
    private String country;
    private String city;
    private String occupation;

    //Non parameter Constructor
    public CustomerData(){
    }
    
    //Using the all veriables Constructor
    public CustomerData(String name, String surname, String country, String city, String occupation) {
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.city = city;
        this.occupation = occupation;   

    }

    @Override
    public String toString() {
        return "CustomerData{" + "name=" + name + ", surname=" + surname +
            ", country=" + country + ", city=" + city + ", occupation=" + occupation + '}';
    }

  
   
    
    //Copy Constructor
    public CustomerData(CustomerData other){
       this.name = other.name;
        this.surname = other.surname;
        this.country = other.country;
        this.city = other.city;
        this.occupation = other.occupation;  
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

   
}