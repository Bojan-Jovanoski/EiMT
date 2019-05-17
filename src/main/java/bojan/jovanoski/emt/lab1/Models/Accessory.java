package bojan.jovanoski.emt.lab1.Models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Accessory")
public class Accessory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "accessoryID")
    Long ID;

    @Column(name = "accessoryName")
    String name;

    public Accessory(Long ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public Long getID() {
        return ID;
    }
    public void setID(Long ID) {
        this.ID = ID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Accessory accessory = (Accessory) o;
        return getID().equals(accessory.getID()) &&
                getName().equals(accessory.getName());
    }

    @Override
    public String toString() {
        return "Accessory{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(getID(), getName());
    }
}