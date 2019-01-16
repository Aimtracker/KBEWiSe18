package de.htw.ai.kbe.songsrx.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.List;

@Entity
@Table(name = "songs")
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull
    private String title;
    private String artist;
    private String album;
    private Integer released;


    @ManyToMany(mappedBy = "songs", fetch = FetchType.EAGER)
    @XmlTransient
    private List<SongList> lists;

    public Song() {
    }

    public Song(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.artist = builder.artist;
        this.album = builder.album;
        this.released = builder.released;
    }

    public static class Builder {
        private Integer id;
        private String title;
        private String artist;
        private String album;
        private Integer released;

        public Builder(String title) {
            this.title = title;
        }

        public Builder id(int val) {
            this.id = val;
            return this;
        }

        public Builder artist(String val) {
            this.artist = val;
            return this;
        }

        public Builder album(String val) {
            this.album = val;
            return this;
        }

        public Builder released(int val) {
            this.released = val;
            return this;
        }

        public Song build() {
            return new Song(this);
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getReleased() {
        return released;
    }

    public void setReleased(Integer released) {
        this.released = released;
    }

    @JsonbTransient
    public List<SongList> getLists() {
        return lists;
    }

    public void setLists(List<SongList> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", released=" + released +
                '}';
    }
}
