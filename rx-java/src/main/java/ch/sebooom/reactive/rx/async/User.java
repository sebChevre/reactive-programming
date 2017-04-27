package ch.sebooom.reactive.rx.async;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by seb on 18.05.16.
 */
public class User {

    private static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String toString() {

        return gson.toJson(this);

    }

    public String login() {
        return login;
    }

    public int age() {
        return age;
    }

    enum Sexe{
      MASCULIN, FEMININ
    };

    private boolean isActive = true;
    private String login;
    private String pass;
    private int age;
    private Sexe sexe;

    public User(String login, String pass, int age, Sexe sexe, boolean isActive) {
        this.login = login;
        this.pass = pass;
        this.age = age;
        this.sexe = sexe;
        this.isActive = isActive;
    }
}
