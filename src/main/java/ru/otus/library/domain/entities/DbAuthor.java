package ru.otus.library.domain.entities;

public class DbAuthor {

    private Integer id;
    private String firstName;
    private String lastName;

    public DbAuthor() { }

    public DbAuthor(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public DbAuthor(Integer id, String firstName, String lastName) {
        this (firstName, lastName);
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        DbAuthor other = (DbAuthor) obj;
        if (id == null)
            return other.getId() == null;
        else
            return id.equals(other.id);
    }
}
