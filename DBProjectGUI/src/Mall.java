/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Stefanos
 */
public class Mall {
    
    private int id;
    private String address;
    private String name;
    
    
    public Mall(int id, String address, String name){
        this.id=id;
        this.name=name;
        this.address=address;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void ShowAddressAndName(){
        
    }
}
