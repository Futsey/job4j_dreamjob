package dreamjob.model;

import dreamjob.service.CityService;

import java.time.LocalDateTime;
import java.util.Objects;

public class Candidate {

    private int id;
    private String name;
    private String description;
    private LocalDateTime created;
    private City city;
    private byte[] photo;

    public Candidate() {
    }

    public Candidate(int id, String name, String description, LocalDateTime created) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
    }

    public Candidate(int id, String name, String description, LocalDateTime created, CityService city, int cityId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.created = created;
        this.city = city.findById(cityId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
