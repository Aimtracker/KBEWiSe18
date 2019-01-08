package de.htw.ai.kbe.songsrx.bean;

import javax.persistence.*;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="userid")
    private String userId;
    @Column(name="lastname")
    private String lastName;
    @Column(name="firstname")
    private String firstName;

    public User(){}

    public User(Builder builder){
        this.id = builder.id;
        this.userId = builder.userId;
        this.lastName = builder.lastName;
        this.firstName = builder.firstName;
    }

    public static class Builder{
        private Integer id;
        private String userId;
        private String lastName;
        private String firstName;
        public Builder(String userId){this.userId = userId;}

        public Builder id(Integer id){
            this.id = id;
            return this;
        }

        public Builder lastName(String lastName){
            this.lastName = lastName;
            return this;
        }

        public Builder firstName(String firstName){
            this.firstName = firstName;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
