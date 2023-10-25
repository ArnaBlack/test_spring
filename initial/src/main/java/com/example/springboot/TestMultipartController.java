package com.example.springboot;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import com.example.springboot.forms.*;
import com.example.springboot.forms.Form.Post;
import com.example.springboot.forms.Publish.Story;
import java.util.*;
import org.springframework.web.multipart.*;
import java.io.*;
import com.google.gson.Gson;

@RestController
public class TestMultipartController {

    @PostMapping(value="/multipart", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String multipart(Form form) throws java.io.IOException  {

        List<Post> list = form.getListPost();
        String title = form.getTitle();

        Publish publish = new Publish();
        publish.setTitle(title);
        publish.setId(UUID.randomUUID());

        list.forEach((Post post) -> {
            MultipartFile multipartFile = post.getFile();

            String fileName = multipartFile.getName();

            String typeContent = multipartFile.getContentType();
            String[] words = typeContent.split("/");
            String ext = words[1];
            String uid = UUID.randomUUID().toString();
            String newNsne = "src/main/resources/" + uid + "." + ext;

            Story story = publish.new Story();
            story.setTitle(post.getTitle());

            File file = new File(newNsne);

            try (OutputStream os = new FileOutputStream(file)) {
                os.write(multipartFile.getBytes());
                os.close();
                story.setFilePath(newNsne);
            } catch (java.io.IOException e) {

            }

            publish.setListStory(story);

        });



        String test = new Gson().toJson(publish).toString();


        String publishName =  publish.getId().toString() + ".json";
        String filePath = "src/main/data/" + publishName;

        try(FileWriter writer = new FileWriter(filePath)) {
            writer.append(test);
            System.out.println("Successfully serialized operators!");
        }catch (IOException ex) {
            System.err.format("An IO Exception was occurred: %s%n", ex);
            System.exit(-1);
        }

        return test;
    }

    @PutMapping(value="/multipart/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String multipart(Form form, @PathVariable("id") String id) throws java.io.IOException  {

        List<Post> list = form.getListPost();
        String title = form.getTitle();

        List<String> fileNamesToRemove = new ArrayList<>();
        List<Story> storyList = new ArrayList<>();

        String strFilePath = "src/main/data/" + id + ".json";
        try (BufferedReader reader = new BufferedReader(new FileReader(strFilePath))) {
            //create JsonObject from the JSON file content
            Publish jsonObject = new Gson().fromJson(reader, Publish.class);

            storyList.addAll(jsonObject.getListPost());

            storyList.forEach((Story story) -> {
                String[] words = story.getFilePath().split("/");
                String ext = words[words.length - 1];
                fileNamesToRemove.add(ext);
            });
        }

        Publish publish = new Publish();
        publish.setTitle(title);

        list.forEach((Post post) -> {
            Story story = publish.new Story();

            String fileNameStory = post.getFileName();

            if (fileNameStory != null) {
                String name = "src/main/resources/" + fileNameStory;
                story.setFilePath(name);

                int index = -1;

                for (int i = 0; i < fileNamesToRemove.size(); i++) {
                    System.out.println(fileNamesToRemove.get(i));
                    System.out.println(name);
                    if (fileNamesToRemove.get(i).equals(fileNameStory)) {
                        index = i;
                        System.out.println(index);
                        break;
                    }
                }

                fileNamesToRemove.remove(index);

            } else {

                MultipartFile multipartFile = post.getFile();

                String fileName = multipartFile.getName();

                String typeContent = multipartFile.getContentType();
                String[] words = typeContent.split("/");
                String ext = words[1];
                String uid = UUID.randomUUID().toString();

                String newFileName = uid + "." + ext;

                String newNsne = "src/main/resources/" + newFileName;
                story.setTitle(post.getTitle());

                File file = new File(newNsne);

                try (OutputStream os = new FileOutputStream(file)) {
                    os.write(multipartFile.getBytes());
                    os.close();
                    story.setFilePath(newNsne);
                } catch (java.io.IOException e) {

                }
            }

            publish.setListStory(story);

        });

        fileNamesToRemove.forEach((String name) -> {
            String deletFilePath = "src/main/resources/" + name;
            File f = new File(deletFilePath);
            f.delete();
        });



        String test = new Gson().toJson(publish).toString();


        String publishName =  id + ".json";
        String filePath = "src/main/data/" + publishName;

        try(FileWriter writer = new FileWriter(filePath)) {
            writer.append(test);
            System.out.println("Successfully serialized operators!");
        }catch (IOException ex) {
            System.err.format("An IO Exception was occurred: %s%n", ex);
            System.exit(-1);
        }

        return test;
    }


}
