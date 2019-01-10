package de.htw.ai.kbe.songsrx.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.List;

@Entity
@Table(name = "songlists")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SongList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "public")
    private boolean isPublic;

    @ManyToOne
    private User owner;

    @ManyToMany
    @JoinTable(name = "list_song", joinColumns = {@JoinColumn(name = "list_id")}, inverseJoinColumns = {@JoinColumn(name = "song_id")})
    @XmlElementWrapper(name = "songs")
    @XmlElement(name = "song")
    @JsonProperty(value = "songs")
    private List<Song> songs;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
