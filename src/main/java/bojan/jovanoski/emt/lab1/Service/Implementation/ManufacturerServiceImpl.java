package bojan.jovanoski.emt.lab1.Service.Implementation;

import bojan.jovanoski.emt.lab1.Models.Manufacturer;
import bojan.jovanoski.emt.lab1.Repositories.Persistance.PersistentManufacturerRepository;
import bojan.jovanoski.emt.lab1.Service.ManufacturerService;
import bojan.jovanoski.emt.lab1.exceptions.ManufacturerAlreadyExistsException;
import bojan.jovanoski.emt.lab1.exceptions.ManufacturerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {
    private PersistentManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(PersistentManufacturerRepository manufacturerRepository){
        this.manufacturerRepository = manufacturerRepository;
    }

    public Manufacturer addNewManufacturer(Manufacturer manufacturer) throws ManufacturerAlreadyExistsException {
        if(manufacturerRepository.getManufacturerList().stream().anyMatch(v -> {
            return v.equals(manufacturer);
        }))
            throw new ManufacturerAlreadyExistsException();

        manufacturerRepository.addManufacturer(manufacturer.getName());
        return manufacturer;
    }

    public Manufacturer addNewManufacturer(String manufacturerName) throws ManufacturerAlreadyExistsException{
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName(manufacturerName);

        if(manufacturerRepository.getManufacturerList().stream().anyMatch(v -> {
            return v.equals(manufacturer);
        }))
            throw new ManufacturerAlreadyExistsException();

        manufacturerRepository.addManufacturer(manufacturer.getName());
        return manufacturer;
    }

    public List<Manufacturer> getAllManufacturers(){
        return manufacturerRepository.getManufacturerList();
    }

    public Manufacturer update(Manufacturer manufacturer){
        return null;
    }

    public void delete(Manufacturer manufacturer) throws ManufacturerNotFoundException {
        if(manufacturerRepository.getManufacturerList().stream().noneMatch(v -> {
            return v.equals(manufacturer);
        }))
            throw new ManufacturerNotFoundException();

        manufacturerRepository.removeManufacturer(manufacturer.getID());
    }

    public Manufacturer getById(Long manufacturerID) throws ManufacturerNotFoundException{
        Optional<Manufacturer> manufacturer = manufacturerRepository.findById(manufacturerID);
        if(!manufacturer.isPresent())
            throw new ManufacturerNotFoundException();
        return manufacturer.get();
    }

    public Manufacturer getByName(String name) throws ManufacturerNotFoundException{
        Optional<Manufacturer> manufacturer = manufacturerRepository.getByName(name);
        if(!manufacturer.isPresent())
            throw new ManufacturerNotFoundException();
        return manufacturer.get();
    }
}
