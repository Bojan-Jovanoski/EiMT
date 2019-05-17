package bojan.jovanoski.emt.lab1.Repositories;

import bojan.jovanoski.emt.lab1.Models.Manufacturer;
import bojan.jovanoski.emt.lab1.exceptions.ManufacturerAlreadyExistsException;
import bojan.jovanoski.emt.lab1.exceptions.ManufacturerNotFoundException;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ManufacturerRepo {
    private Long counterID;
    private List<Manufacturer> manufacturerList;

    @PostConstruct
    public void init(){
        counterID = 1l;
        manufacturerList = new ArrayList<>();
        Manufacturer m1 = new Manufacturer();
        m1.setID(getNextManId());
        m1.setName("Nike");

        Manufacturer m2 = new Manufacturer();
        m2.setID(getNextManId());
        m2.setName("Li-Ning");

        Manufacturer m3 = new Manufacturer();
        m3.setID(getNextManId());
        m3.setName("Adidas");

        Manufacturer m4 = new Manufacturer();
        m4.setName("Salomon");
        m4.setID(getNextManId());

        manufacturerList.add(m1);
        manufacturerList.add(m2);
        manufacturerList.add(m3);
        manufacturerList.add(m4);
    }

    public List<Manufacturer> getManufacturerList(){
        return manufacturerList;
    }

    public void addManufacturer(Manufacturer manufacturer){
        if(manufacturerList.stream().anyMatch(manufacturer1 -> {
            return manufacturer1.getName().equals(manufacturer.getName());
        }))
            throw new ManufacturerAlreadyExistsException();

        manufacturer.setID(getNextManId());
        manufacturerList.add(manufacturer);
    }

    public void removeManufacturer(Manufacturer manufacturer){
        if(manufacturerList.stream().noneMatch(manufacturer1 -> {
            return manufacturer1.getID()==manufacturer.getID();
        }))
            throw new ManufacturerNotFoundException();

        manufacturerList.remove(manufacturer);
    }

    public Optional<Manufacturer> findById(Long ID){
        return manufacturerList.stream()
                .filter(manufacturer -> {
                    return manufacturer.getID()==ID;
                }).findAny();
    }

    public Optional<Manufacturer> findByName(String name){
        return manufacturerList.stream()
                .filter(manufacturer -> {
                    return manufacturer.getName().equals(name);
                }).findAny();
    }

    private long getNextManId() {return counterID++;}
}
