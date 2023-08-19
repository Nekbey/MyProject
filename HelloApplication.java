package com.example.project1;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.io.File;
import java.sql.Array;
import java.sql.Driver;
import java.util.ArrayList;
import java.util.Scanner;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {




        class Filter implements FileFilter
        {
            String[] ext;

            Filter(String ext)
            {
                this.ext = ext.split(",");
            }
            private String getExtension(File pathname)
            {
                String filename = pathname.getPath();
                int i = filename.lastIndexOf('.');
                if ((i > 0) && (i < filename.length()-1)) {
                    return filename.substring(i+1).toLowerCase();
                }
                return "";
            }
            public boolean accept(File pathname)
            {
                if (!pathname.isFile())
                    return false;
                String extension = getExtension(pathname);
                for (String e : ext) {
                    if (e.equalsIgnoreCase(extension))
                        return true;
                }
                return false;
            }
        }
        // Определение директории
        File dir = new File("src\\main\\resources\\com\\example\\project1\\Фотки");
        // Чтение полного списка файлов каталога

        // Чтение списка файлов каталога
        // с расширениями "png" и "jpg"
        File[] lst = dir.listFiles(new Filter("png,jpg"));
        System.out.println ("lst2.length = " + lst.length);
        int rndm =  (int) Math.floor(getRandomDoubleBetweenRange(0, lst.length-1));

        System.out.println("Rand " + lst[rndm]);
        String st = lst[rndm].toString();
        Image img = new Image(lst[rndm].toURI().toURL().toExternalForm());
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("alpha.fxml"));
        Scene scene = new Scene( fxmlLoader.load(), Math.min(600,img.getWidth()), Math.min(600,img.getHeight()) + 70);

        stage.setScene(scene);

        stage.show();
        stage.sizeToScene();
        System.out.println("img "  + img.toString());
        ((ImageView) scene.lookup("#image_v")).setImage(img);

        //FILMS
        java.util.List<String> film = new ArrayList<String>();
        File file = new File(
                "src\\main\\resources\\com\\example\\project1\\film.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine())
           film.add(sc.nextLine());
        int filmid = (int) Math.floor(getRandomDoubleBetweenRange(0, film.size()-1));
        String[] simpleArray = new String[ film.size() ];
        film.toArray( simpleArray );
        var movie = simpleArray[filmid];
        System.out.println("movie "  + movie);

        //ACTION
        //Кнопка посмотреть фильм
        Button btn = (Button) scene.lookup("#display_film");
        btn.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        final Stage dialog = new Stage();

                        File file = new File(
                                "src\\main\\resources\\com\\example\\project1\\film.txt");
                        Scanner sc = null;
                        try {
                            sc = new Scanner(file);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        while (sc.hasNextLine())
                            film.add(sc.nextLine());

                        int filmid = (int) Math.floor(getRandomDoubleBetweenRange(0, film.size()-1));
                        String[] simpleArray = new String[ film.size() ];
                        film.toArray( simpleArray );
                        var movie = simpleArray[filmid];

                        FXMLLoader fxmlLoaderfilms = new FXMLLoader(HelloApplication.class.getResource("text_film.fxml"));

                        try {
                            Scene scene_fs = new Scene(fxmlLoaderfilms.load(), Math.min(600, img.getWidth()), Math.min(600, img.getHeight()));
                            dialog.setScene(scene_fs);
                            Text txt = (Text) scene_fs.lookup("#text_film");
                            txt.setText(movie);
                            System.out.println("txt "  + txt);

                            System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\com\\example\\project1\\chromedriver.exe");
                            //create chrome instance
                             WebDriver driver = new ChromeDriver();
                            driver.get("https://ya.ru/");
                            WebElement element = driver.findElement(By.xpath("//input[@name='text']"));
                            element.sendKeys("смотреть " + movie);
                            WebElement button = driver.findElement(By.xpath("//button[@type='submit']"));
                            button.click();
                        } catch (IOException ex)
                        {
                            ex.printStackTrace();
                        }

                        dialog.show();

                        //Вызов браузера

//                        WebDriver driver = new ChromeDriver();
//                        driver.get("https://ya.ru/");
//                        WebElement email= driver.findElement(By.id("text"));
//                        Actions builder = new Actions(driver);
//                        Actions seriesOfActions = builder.moveToElement(email).click().sendKeys(email, "gati.naveen@gmail.com");
//                        seriesOfActions.perform();


                    }
                });
        //Кнопка добавить фильм
        Button btn2 = (Button) scene.lookup("#add_film");
        btn2.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        final Stage dialog = new Stage();

                        FXMLLoader fxmlLoaderfilm = new FXMLLoader(HelloApplication.class.getResource("add_films.fxml"));
                        try {
                            Scene scene_f = new Scene( fxmlLoaderfilm.load(), Math.min(600,img.getWidth()), Math.min(600,img.getHeight()) + 70);
                            dialog.setScene(scene_f);

                            //Кнопка добавление фильма в массив
                            Button btn3 = (Button) scene_f.lookup("#add_to_massive");
                            btn3.setOnAction(
                                    new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            TextField tf = (TextField) scene_f.lookup("#field");
                                            try
                                            {
                                                String filename= "src\\main\\resources\\com\\example\\project1\\film.txt";
                                                FileWriter fw = new FileWriter(filename,true); //the true will append the new data
                                                fw.write("\n" +tf.getText());//appends the string to the file
                                                fw.close();
                                                dialog.close();
                                            }
                                            catch(IOException ioe)
                                            {
                                                System.err.println("IOException: " + ioe.getMessage());
                                            }
                                            System.out.println("new movie "  + tf.getText());
                                        }
                                    });

                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        //dialog.setScene(dialogScene);
                        dialog.show();

                    }
                });




        stage.setMinHeight(Math.min(600,img.getHeight()) + 110);
        stage.setMinWidth(Math.min(600,img.getWidth()));

    }


    public static double getRandomDoubleBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }

    //Начало
    public static void main(String[] args) {
        Application.launch(args);
    }
}