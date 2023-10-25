package com.example.springboot.forms;

import java.util.*;
import java.io.*;
import org.springframework.web.multipart.*;

public class Publish {
    public class Story {
        private String title;
        private String FilePath;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFilePath() {
            return this.FilePath;
        }

        public void setFilePath(String FilePath) {
            this.FilePath = FilePath;
        }
    }

    private UUID id;
    private String title;
    private List<Story> listPost = new ArrayList<>();

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setListPost(List<Story> list) {
        listPost.addAll(list);
    }

    public void setListStory(Story story) {
        listPost.add(story);
    }

    public List<Story> getListPost() {
        return this.listPost;
    }
}