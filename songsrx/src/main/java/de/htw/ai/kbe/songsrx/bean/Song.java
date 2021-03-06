package de.htw.ai.kbe.songsrx.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Song {
    private Integer id;
    private String title;
    private String artist;
    private String album;
    private Integer released;

    public Song(){
    }

    public Song(Builder builder){
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

        public Builder(String title){
            this.title = title;
        }

        public Builder id(int val){
            this.id = val;
            return this;
        }

        public Builder artist(String val){
            this.artist = val;
            return this;
        }

        public Builder album(String val){
            this.album = val;
            return this;
        }

        public Builder released(int val){
            this.released = val;
            return this;
        }

        public Song build(){
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
