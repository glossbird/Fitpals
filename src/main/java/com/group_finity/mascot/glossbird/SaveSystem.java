package com.group_finity.mascot.glossbird;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.group_finity.mascot.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SaveSystem {
    private static SaveSystem instance = new SaveSystem();

    public static final Path DATA_DIRECTORY = Paths.get("data");

    public static final Path SAVE_FILE = Main.SAVE_FILE;
    public static SaveSystem getInstance() {
        return instance;
    }
    JsonFactory factory;
    public SaveData data;
    public SaveSystem() {


        factory = JsonFactory.builder()
// configure, if necessary:
                .enable(JsonReadFeature.ALLOW_JAVA_COMMENTS)
                .build();
        JsonGenerator generator;
       /* try {
            generator = factory.createGenerator(new File("save.json"), JsonEncoding.UTF8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/




    }

    public void Load()
    {

    }

    public AlarmData Load(AlarmData typedata) throws FileNotFoundException {
        ObjectMapper mapper = new ObjectMapper();
        Reader reader;
        try {
             reader = Files.newBufferedReader(SAVE_FILE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            SaveData data1 = mapper.readValue(reader, SaveData.class);
            this.data = data1;
            typedata = data1.alarmData;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.data.alarmData = typedata;
        return this.data.alarmData;

    }



    public void Save()
    {
        data = new SaveData();
        data.alarmData = Main.getInstance().getAlarmManager().getData();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String dataJson;
        try {
            dataJson = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("data/save.json");
            mapper.writeValue(fileOutputStream, data);
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (StreamWriteException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
