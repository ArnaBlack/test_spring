package com.example.springboot.forms;

import java.util.*;
import org.springframework.web.multipart.*;

public class Form {
    public static class Post {
        private String title;
        private MultipartFile file;
        private String fileName;

        public String getTitle() {
            return this.title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFileName() {
            return this.fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public MultipartFile getFile() {
            return this.file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }
    }

    private String title;
    private List<Post> listPost = new ArrayList<>();

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setListPost(List<Post> list) {
        listPost.addAll(list);
    }

    public List<Post> getListPost() {
        return this.listPost;
    }
}