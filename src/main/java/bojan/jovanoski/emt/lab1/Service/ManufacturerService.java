package bojan.jovanoski.emt.lab1.Service;

import bojan.jovanoski.emt.lab1.Models.Manufacturer;
import bojan.jovanoski.emt.lab1.exceptions.ManufacturerAlreadyExistsException;
import bojan.jovanoski.emt.lab1.exceptions.ManufacturerNotFoundException;

import java.util.List;

public interface ManufacturerService {
    Manufacturer addNewManufacturer(Manufacturer manufacturer) throws ManufacturerAlreadyExistsException;
    Manufacturer addNewManufacturer(String manufacturerName) throws ManufacturerAlreadyExistsException;
    List<Manufacturer> getAllManufacturers();
    Manufacturer update(Manufacturer manufacturer);
    void delete(Manufacturer manufacturer) throws ManufacturerNotFoundException;
    Manufacturer getById(Long manufacturerID) throws ManufacturerNotFoundException;
    Manufacturer getByName(String name) throws ManufacturerNotFoundException;
}
